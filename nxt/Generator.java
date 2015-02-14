/*   1:    */ package nxt;
/*   2:    */ 
/*   3:    */ import fr.cryptohash.Shabal256;
/*   4:    */ import java.math.BigInteger;
/*   5:    */ import java.nio.ByteBuffer;
/*   6:    */ import java.security.MessageDigest;
/*   7:    */ import java.util.Collection;
/*   8:    */ import java.util.Collections;
/*   9:    */ import java.util.Iterator;
/*  10:    */ import java.util.Map.Entry;
/*  11:    */ import java.util.Set;
/*  12:    */ import java.util.concurrent.ConcurrentHashMap;
/*  13:    */ import java.util.concurrent.ConcurrentMap;
/*  14:    */ import java.util.concurrent.TimeUnit;
/*  15:    */ import nxt.crypto.Crypto;
/*  16:    */ import nxt.util.Convert;
/*  17:    */ import nxt.util.Listener;
/*  18:    */ import nxt.util.Listeners;
/*  19:    */ import nxt.util.Logger;
/*  20:    */ import nxt.util.MiningPlot;
/*  21:    */ import nxt.util.ThreadPool;
/*  22:    */ 
/*  23:    */ public final class Generator
/*  24:    */ {
/*  25:    */   public static enum Event
/*  26:    */   {
/*  27: 28 */     GENERATION_DEADLINE,  START_FORGING,  STOP_FORGING;
/*  28:    */     
/*  29:    */     private Event() {}
/*  30:    */   }
/*  31:    */   
/*  32: 31 */   private static final Listeners<Generator, Event> listeners = new Listeners();
/*  33: 33 */   private static final ConcurrentMap<Long, Generator> generators = new ConcurrentHashMap();
/*  34: 34 */   private static final Collection<Generator> allGenerators = Collections.unmodifiableCollection(generators.values());
/*  35: 36 */   private static final Runnable generateBlockThread = new Runnable()
/*  36:    */   {
/*  37:    */     public void run()
/*  38:    */     {
/*  39:    */       try
/*  40:    */       {
/*  41: 42 */         if (Nxt.getBlockchainProcessor().isScanning()) {
/*  42: 43 */           return;
/*  43:    */         }
/*  44:    */         try
/*  45:    */         {
/*  46: 46 */           long l = Nxt.getBlockchain().getLastBlock().getHeight();
/*  47: 47 */           Iterator localIterator = Generator.generators.entrySet().iterator();
/*  48: 48 */           while (localIterator.hasNext())
/*  49:    */           {
/*  50: 49 */             Map.Entry localEntry = (Map.Entry)localIterator.next();
/*  51: 50 */             if (l < ((Generator)localEntry.getValue()).getBlock()) {
/*  52: 51 */               ((Generator)localEntry.getValue()).forge();
/*  53:    */             } else {
/*  54: 54 */               localIterator.remove();
/*  55:    */             }
/*  56:    */           }
/*  57:    */         }
/*  58:    */         catch (Exception localException)
/*  59:    */         {
/*  60: 58 */           Logger.logDebugMessage("Error in block generation thread", localException);
/*  61:    */         }
/*  62:    */       }
/*  63:    */       catch (Throwable localThrowable)
/*  64:    */       {
/*  65: 61 */         Logger.logMessage("CRITICAL ERROR. PLEASE REPORT TO THE DEVELOPERS.\n" + localThrowable.toString());
/*  66: 62 */         localThrowable.printStackTrace();
/*  67: 63 */         System.exit(1);
/*  68:    */       }
/*  69:    */     }
/*  70:    */   };
/*  71:    */   private final Long accountId;
/*  72:    */   private final String secretPhrase;
/*  73:    */   private final byte[] publicKey;
/*  74:    */   private volatile BigInteger deadline;
/*  75:    */   private final long nonce;
/*  76:    */   private final long block;
/*  77:    */   
/*  78:    */   static
/*  79:    */   {
/*  80: 71 */     ThreadPool.scheduleThread("GenerateBlocks", generateBlockThread, 500, TimeUnit.MILLISECONDS);
/*  81:    */   }
/*  82:    */   
/*  83:    */   public static boolean addListener(Listener<Generator> paramListener, Event paramEvent)
/*  84:    */   {
/*  85: 80 */     return listeners.addListener(paramListener, paramEvent);
/*  86:    */   }
/*  87:    */   
/*  88:    */   public static boolean removeListener(Listener<Generator> paramListener, Event paramEvent)
/*  89:    */   {
/*  90: 84 */     return listeners.removeListener(paramListener, paramEvent);
/*  91:    */   }
/*  92:    */   
/*  93:    */   public static Generator startForging(String paramString)
/*  94:    */   {
/*  95: 88 */     return null;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public static Generator addNonce(String paramString, Long paramLong)
/*  99:    */   {
/* 100: 92 */     byte[] arrayOfByte = Crypto.getPublicKey(paramString);
/* 101: 93 */     return addNonce(paramString, paramLong, arrayOfByte);
/* 102:    */   }
/* 103:    */   
/* 104:    */   public static Generator startForging(String paramString, byte[] paramArrayOfByte)
/* 105:    */   {
/* 106: 97 */     return null;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public static Generator addNonce(String paramString, Long paramLong, byte[] paramArrayOfByte)
/* 110:    */   {
/* 111:101 */     byte[] arrayOfByte = Crypto.sha256().digest(paramArrayOfByte);
/* 112:102 */     Long localLong = Long.valueOf(Convert.fullHashToId(arrayOfByte));
/* 113:    */     
/* 114:104 */     Generator localGenerator1 = new Generator(paramString, paramLong, paramArrayOfByte, localLong);
/* 115:105 */     Generator localGenerator2 = (Generator)generators.get(localLong);
/* 116:106 */     if ((localGenerator2 == null) || (localGenerator1.getBlock() > localGenerator2.getBlock()) || (localGenerator1.getDeadline().compareTo(localGenerator2.getDeadline()) < 0))
/* 117:    */     {
/* 118:107 */       generators.put(localLong, localGenerator1);
/* 119:108 */       listeners.notify(localGenerator1, Event.START_FORGING);
/* 120:109 */       Logger.logDebugMessage("Account " + Convert.toUnsignedLong(localLong.longValue()) + " started mining, deadline " + localGenerator1.getDeadline() + " seconds");
/* 121:    */     }
/* 122:    */     else
/* 123:    */     {
/* 124:113 */       Logger.logDebugMessage("Account " + Convert.toUnsignedLong(localLong.longValue()) + " already has better nonce");
/* 125:    */     }
/* 126:116 */     return localGenerator1;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public static Generator stopForging(String paramString)
/* 130:    */   {
/* 131:120 */     return null;
/* 132:    */   }
/* 133:    */   
/* 134:    */   public static Generator getGenerator(String paramString)
/* 135:    */   {
/* 136:124 */     return null;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public static Collection<Generator> getAllGenerators()
/* 140:    */   {
/* 141:128 */     return allGenerators;
/* 142:    */   }
/* 143:    */   
/* 144:    */   private Generator(String paramString, Long paramLong1, byte[] paramArrayOfByte, Long paramLong2)
/* 145:    */   {
/* 146:139 */     this.secretPhrase = paramString;
/* 147:140 */     this.publicKey = paramArrayOfByte;
/* 148:    */     
/* 149:142 */     this.accountId = paramLong2;
/* 150:143 */     this.nonce = paramLong1.longValue();
/* 151:144 */     this.block = (Nxt.getBlockchain().getLastBlock().getHeight() + 1);
/* 152:    */     
/* 153:    */ 
/* 154:147 */     Block localBlock = Nxt.getBlockchain().getLastBlock();
/* 155:148 */     byte[] arrayOfByte1 = localBlock.getGenerationSignature();
/* 156:149 */     Long localLong = Long.valueOf(localBlock.getGeneratorId());
/* 157:    */     
/* 158:151 */     ByteBuffer localByteBuffer1 = ByteBuffer.allocate(40);
/* 159:152 */     localByteBuffer1.put(arrayOfByte1);
/* 160:153 */     localByteBuffer1.putLong(localLong.longValue());
/* 161:    */     
/* 162:155 */     Shabal256 localShabal256 = new Shabal256();
/* 163:156 */     localShabal256.update(localByteBuffer1.array());
/* 164:157 */     byte[] arrayOfByte2 = localShabal256.digest();
/* 165:    */     
/* 166:    */ 
/* 167:160 */     MiningPlot localMiningPlot = new MiningPlot(this.accountId.longValue(), paramLong1.longValue());
/* 168:    */     
/* 169:162 */     ByteBuffer localByteBuffer2 = ByteBuffer.allocate(40);
/* 170:163 */     localByteBuffer2.put(arrayOfByte2);
/* 171:164 */     localByteBuffer2.putLong(localBlock.getHeight() + 1);
/* 172:165 */     localShabal256.reset();
/* 173:166 */     localShabal256.update(localByteBuffer2.array());
/* 174:167 */     BigInteger localBigInteger1 = new BigInteger(1, localShabal256.digest());
/* 175:168 */     int i = localBigInteger1.mod(BigInteger.valueOf(MiningPlot.SCOOPS_PER_PLOT)).intValue();
/* 176:    */     
/* 177:170 */     localShabal256.reset();
/* 178:171 */     localShabal256.update(arrayOfByte2);
/* 179:172 */     localMiningPlot.hashScoop(localShabal256, i);
/* 180:173 */     byte[] arrayOfByte3 = localShabal256.digest();
/* 181:174 */     BigInteger localBigInteger2 = new BigInteger(1, new byte[] { arrayOfByte3[7], arrayOfByte3[6], arrayOfByte3[5], arrayOfByte3[4], arrayOfByte3[3], arrayOfByte3[2], arrayOfByte3[1], arrayOfByte3[0] });
/* 182:    */     
/* 183:176 */     this.deadline = localBigInteger2.divide(BigInteger.valueOf(localBlock.getBaseTarget()));
/* 184:    */   }
/* 185:    */   
/* 186:    */   public byte[] getPublicKey()
/* 187:    */   {
/* 188:180 */     return this.publicKey;
/* 189:    */   }
/* 190:    */   
/* 191:    */   public Long getAccountId()
/* 192:    */   {
/* 193:184 */     return this.accountId;
/* 194:    */   }
/* 195:    */   
/* 196:    */   public BigInteger getDeadline()
/* 197:    */   {
/* 198:188 */     return this.deadline;
/* 199:    */   }
/* 200:    */   
/* 201:    */   public long getBlock()
/* 202:    */   {
/* 203:192 */     return this.block;
/* 204:    */   }
/* 205:    */   
/* 206:    */   private void forge()
/* 207:    */     throws BlockchainProcessor.BlockNotAcceptedException
/* 208:    */   {
/* 209:196 */     Block localBlock = Nxt.getBlockchain().getLastBlock();
/* 210:    */     
/* 211:198 */     int i = Nxt.getEpochTime() - localBlock.getTimestamp();
/* 212:199 */     if (BigInteger.valueOf(i).compareTo(this.deadline) > 0) {
/* 213:200 */       BlockchainProcessorImpl.getInstance().generateBlock(this.secretPhrase, this.publicKey, Long.valueOf(this.nonce));
/* 214:    */     }
/* 215:    */   }
/* 216:    */   
/* 217:    */   static void init() {}
/* 218:    */   
/* 219:    */   static void clear() {}
/* 220:    */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.Generator
 * JD-Core Version:    0.7.1
 */