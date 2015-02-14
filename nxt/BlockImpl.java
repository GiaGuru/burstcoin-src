/*   1:    */ package nxt;
/*   2:    */ 
/*   3:    */ import fr.cryptohash.Shabal256;
/*   4:    */ import java.math.BigInteger;
/*   5:    */ import java.nio.ByteBuffer;
/*   6:    */ import java.nio.ByteOrder;
/*   7:    */ import java.security.MessageDigest;
/*   8:    */ import java.util.Arrays;
/*   9:    */ import java.util.Collections;
/*  10:    */ import java.util.Iterator;
/*  11:    */ import java.util.List;
/*  12:    */ import nxt.crypto.Crypto;
/*  13:    */ import nxt.util.Convert;
/*  14:    */ import nxt.util.Logger;
/*  15:    */ import nxt.util.MiningPlot;
/*  16:    */ import org.json.simple.JSONArray;
/*  17:    */ import org.json.simple.JSONObject;
/*  18:    */ 
/*  19:    */ final class BlockImpl
/*  20:    */   implements Block
/*  21:    */ {
/*  22:    */   private final int version;
/*  23:    */   private final int timestamp;
/*  24:    */   private final long previousBlockId;
/*  25:    */   private final byte[] generatorPublicKey;
/*  26:    */   private final byte[] previousBlockHash;
/*  27:    */   private final long totalAmountNQT;
/*  28:    */   private final long totalFeeNQT;
/*  29:    */   private final int payloadLength;
/*  30:    */   private final byte[] generationSignature;
/*  31:    */   private final byte[] payloadHash;
/*  32:    */   private volatile List<TransactionImpl> blockTransactions;
/*  33:    */   private byte[] blockSignature;
/*  34: 39 */   private BigInteger cumulativeDifficulty = BigInteger.ZERO;
/*  35: 40 */   private long baseTarget = 18325193796L;
/*  36:    */   private volatile long nextBlockId;
/*  37: 42 */   private int height = -1;
/*  38:    */   private volatile long id;
/*  39: 44 */   private volatile String stringId = null;
/*  40:    */   private volatile long generatorId;
/*  41:    */   private long nonce;
/*  42:    */   private final byte[] blockATs;
/*  43:    */   
/*  44:    */   BlockImpl(int paramInt1, int paramInt2, long paramLong1, long paramLong2, long paramLong3, int paramInt3, byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, byte[] paramArrayOfByte3, byte[] paramArrayOfByte4, byte[] paramArrayOfByte5, List<TransactionImpl> paramList, long paramLong4, byte[] paramArrayOfByte6)
/*  45:    */     throws NxtException.ValidationException
/*  46:    */   {
/*  47: 54 */     if ((paramInt3 > 44880) || (paramInt3 < 0)) {
/*  48: 55 */       throw new NxtException.NotValidException("attempted to create a block with payloadLength " + paramInt3);
/*  49:    */     }
/*  50: 58 */     this.version = paramInt1;
/*  51: 59 */     this.timestamp = paramInt2;
/*  52: 60 */     this.previousBlockId = paramLong1;
/*  53: 61 */     this.totalAmountNQT = paramLong2;
/*  54: 62 */     this.totalFeeNQT = paramLong3;
/*  55: 63 */     this.payloadLength = paramInt3;
/*  56: 64 */     this.payloadHash = paramArrayOfByte1;
/*  57: 65 */     this.generatorPublicKey = paramArrayOfByte2;
/*  58: 66 */     this.generationSignature = paramArrayOfByte3;
/*  59: 67 */     this.blockSignature = paramArrayOfByte4;
/*  60:    */     
/*  61: 69 */     this.previousBlockHash = paramArrayOfByte5;
/*  62:    */     long l;
/*  63: 70 */     if (paramList != null)
/*  64:    */     {
/*  65: 71 */       this.blockTransactions = Collections.unmodifiableList(paramList);
/*  66: 72 */       if (this.blockTransactions.size() > 255) {
/*  67: 73 */         throw new NxtException.NotValidException("attempted to create a block with " + this.blockTransactions.size() + " transactions");
/*  68:    */       }
/*  69: 75 */       l = 0L;
/*  70: 76 */       for (Transaction localTransaction : this.blockTransactions)
/*  71:    */       {
/*  72: 77 */         if ((localTransaction.getId() <= l) && (l != 0L)) {
/*  73: 78 */           throw new NxtException.NotValidException("Block transactions are not sorted!");
/*  74:    */         }
/*  75: 80 */         l = localTransaction.getId();
/*  76:    */       }
/*  77:    */     }
/*  78: 83 */     this.nonce = paramLong4;
/*  79: 84 */     this.blockATs = paramArrayOfByte6;
/*  80:    */   }
/*  81:    */   
/*  82:    */   BlockImpl(int paramInt1, int paramInt2, long paramLong1, long paramLong2, long paramLong3, int paramInt3, byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, byte[] paramArrayOfByte3, byte[] paramArrayOfByte4, byte[] paramArrayOfByte5, BigInteger paramBigInteger, long paramLong4, long paramLong5, int paramInt4, Long paramLong, long paramLong6, byte[] paramArrayOfByte6)
/*  83:    */     throws NxtException.ValidationException
/*  84:    */   {
/*  85: 91 */     this(paramInt1, paramInt2, paramLong1, paramLong2, paramLong3, paramInt3, paramArrayOfByte1, paramArrayOfByte2, paramArrayOfByte3, paramArrayOfByte4, paramArrayOfByte5, null, paramLong6, paramArrayOfByte6);
/*  86:    */     
/*  87: 93 */     this.cumulativeDifficulty = paramBigInteger;
/*  88: 94 */     this.baseTarget = paramLong4;
/*  89: 95 */     this.nextBlockId = paramLong5;
/*  90: 96 */     this.height = paramInt4;
/*  91: 97 */     this.id = paramLong.longValue();
/*  92:    */   }
/*  93:    */   
/*  94:    */   public int getVersion()
/*  95:    */   {
/*  96:102 */     return this.version;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public int getTimestamp()
/* 100:    */   {
/* 101:107 */     return this.timestamp;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public long getPreviousBlockId()
/* 105:    */   {
/* 106:112 */     return this.previousBlockId;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public byte[] getGeneratorPublicKey()
/* 110:    */   {
/* 111:117 */     return this.generatorPublicKey;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public byte[] getBlockHash()
/* 115:    */   {
/* 116:122 */     return Crypto.sha256().digest(getBytes());
/* 117:    */   }
/* 118:    */   
/* 119:    */   public byte[] getPreviousBlockHash()
/* 120:    */   {
/* 121:127 */     return this.previousBlockHash;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public long getTotalAmountNQT()
/* 125:    */   {
/* 126:132 */     return this.totalAmountNQT;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public long getTotalFeeNQT()
/* 130:    */   {
/* 131:137 */     return this.totalFeeNQT;
/* 132:    */   }
/* 133:    */   
/* 134:    */   public int getPayloadLength()
/* 135:    */   {
/* 136:142 */     return this.payloadLength;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public byte[] getPayloadHash()
/* 140:    */   {
/* 141:147 */     return this.payloadHash;
/* 142:    */   }
/* 143:    */   
/* 144:    */   public byte[] getGenerationSignature()
/* 145:    */   {
/* 146:152 */     return this.generationSignature;
/* 147:    */   }
/* 148:    */   
/* 149:    */   public byte[] getBlockSignature()
/* 150:    */   {
/* 151:157 */     return this.blockSignature;
/* 152:    */   }
/* 153:    */   
/* 154:    */   public List<TransactionImpl> getTransactions()
/* 155:    */   {
/* 156:162 */     if (this.blockTransactions == null)
/* 157:    */     {
/* 158:163 */       this.blockTransactions = Collections.unmodifiableList(TransactionDb.findBlockTransactions(getId()));
/* 159:164 */       for (TransactionImpl localTransactionImpl : this.blockTransactions) {
/* 160:165 */         localTransactionImpl.setBlock(this);
/* 161:    */       }
/* 162:    */     }
/* 163:168 */     return this.blockTransactions;
/* 164:    */   }
/* 165:    */   
/* 166:    */   public long getBaseTarget()
/* 167:    */   {
/* 168:173 */     return this.baseTarget;
/* 169:    */   }
/* 170:    */   
/* 171:    */   public BigInteger getCumulativeDifficulty()
/* 172:    */   {
/* 173:178 */     return this.cumulativeDifficulty;
/* 174:    */   }
/* 175:    */   
/* 176:    */   public long getNextBlockId()
/* 177:    */   {
/* 178:183 */     return this.nextBlockId;
/* 179:    */   }
/* 180:    */   
/* 181:    */   public int getHeight()
/* 182:    */   {
/* 183:188 */     if (this.height == -1) {
/* 184:189 */       throw new IllegalStateException("Block height not yet set");
/* 185:    */     }
/* 186:191 */     return this.height;
/* 187:    */   }
/* 188:    */   
/* 189:    */   public long getId()
/* 190:    */   {
/* 191:196 */     if (this.id == 0L)
/* 192:    */     {
/* 193:197 */       if (this.blockSignature == null) {
/* 194:198 */         throw new IllegalStateException("Block is not signed yet");
/* 195:    */       }
/* 196:200 */       byte[] arrayOfByte = Crypto.sha256().digest(getBytes());
/* 197:201 */       BigInteger localBigInteger = new BigInteger(1, new byte[] { arrayOfByte[7], arrayOfByte[6], arrayOfByte[5], arrayOfByte[4], arrayOfByte[3], arrayOfByte[2], arrayOfByte[1], arrayOfByte[0] });
/* 198:202 */       this.id = localBigInteger.longValue();
/* 199:203 */       this.stringId = localBigInteger.toString();
/* 200:    */     }
/* 201:205 */     return this.id;
/* 202:    */   }
/* 203:    */   
/* 204:    */   public String getStringId()
/* 205:    */   {
/* 206:210 */     if (this.stringId == null)
/* 207:    */     {
/* 208:211 */       getId();
/* 209:212 */       if (this.stringId == null) {
/* 210:213 */         this.stringId = Convert.toUnsignedLong(this.id);
/* 211:    */       }
/* 212:    */     }
/* 213:216 */     return this.stringId;
/* 214:    */   }
/* 215:    */   
/* 216:    */   public long getGeneratorId()
/* 217:    */   {
/* 218:221 */     if (this.generatorId == 0L) {
/* 219:222 */       this.generatorId = Account.getId(this.generatorPublicKey);
/* 220:    */     }
/* 221:224 */     return this.generatorId;
/* 222:    */   }
/* 223:    */   
/* 224:    */   public Long getNonce()
/* 225:    */   {
/* 226:229 */     return Long.valueOf(this.nonce);
/* 227:    */   }
/* 228:    */   
/* 229:    */   public int getScoopNum()
/* 230:    */   {
/* 231:234 */     ByteBuffer localByteBuffer = ByteBuffer.allocate(40);
/* 232:235 */     localByteBuffer.put(this.generationSignature);
/* 233:236 */     localByteBuffer.putLong(getHeight());
/* 234:237 */     Shabal256 localShabal256 = new Shabal256();
/* 235:238 */     localShabal256.update(localByteBuffer.array());
/* 236:239 */     BigInteger localBigInteger = new BigInteger(1, localShabal256.digest());
/* 237:240 */     int i = localBigInteger.mod(BigInteger.valueOf(MiningPlot.SCOOPS_PER_PLOT)).intValue();
/* 238:241 */     return i;
/* 239:    */   }
/* 240:    */   
/* 241:    */   public boolean equals(Object paramObject)
/* 242:    */   {
/* 243:246 */     return ((paramObject instanceof BlockImpl)) && (getId() == ((BlockImpl)paramObject).getId());
/* 244:    */   }
/* 245:    */   
/* 246:    */   public int hashCode()
/* 247:    */   {
/* 248:251 */     return (int)(getId() ^ getId() >>> 32);
/* 249:    */   }
/* 250:    */   
/* 251:    */   public JSONObject getJSONObject()
/* 252:    */   {
/* 253:256 */     JSONObject localJSONObject = new JSONObject();
/* 254:257 */     localJSONObject.put("version", Integer.valueOf(this.version));
/* 255:258 */     localJSONObject.put("timestamp", Integer.valueOf(this.timestamp));
/* 256:259 */     localJSONObject.put("previousBlock", Convert.toUnsignedLong(this.previousBlockId));
/* 257:260 */     localJSONObject.put("totalAmountNQT", Long.valueOf(this.totalAmountNQT));
/* 258:261 */     localJSONObject.put("totalFeeNQT", Long.valueOf(this.totalFeeNQT));
/* 259:262 */     localJSONObject.put("payloadLength", Integer.valueOf(this.payloadLength));
/* 260:263 */     localJSONObject.put("payloadHash", Convert.toHexString(this.payloadHash));
/* 261:264 */     localJSONObject.put("generatorPublicKey", Convert.toHexString(this.generatorPublicKey));
/* 262:265 */     localJSONObject.put("generationSignature", Convert.toHexString(this.generationSignature));
/* 263:266 */     if (this.version > 1) {
/* 264:267 */       localJSONObject.put("previousBlockHash", Convert.toHexString(this.previousBlockHash));
/* 265:    */     }
/* 266:269 */     localJSONObject.put("blockSignature", Convert.toHexString(this.blockSignature));
/* 267:270 */     JSONArray localJSONArray = new JSONArray();
/* 268:271 */     for (Transaction localTransaction : getTransactions()) {
/* 269:272 */       localJSONArray.add(localTransaction.getJSONObject());
/* 270:    */     }
/* 271:274 */     localJSONObject.put("transactions", localJSONArray);
/* 272:275 */     localJSONObject.put("nonce", Convert.toUnsignedLong(this.nonce));
/* 273:276 */     localJSONObject.put("blockATs", Convert.toHexString(this.blockATs));
/* 274:277 */     return localJSONObject;
/* 275:    */   }
/* 276:    */   
/* 277:    */   /* Error */
/* 278:    */   static BlockImpl parseBlock(JSONObject paramJSONObject)
/* 279:    */     throws NxtException.ValidationException
/* 280:    */   {
/* 281:    */     // Byte code:
/* 282:    */     //   0: aload_0
/* 283:    */     //   1: ldc 81
/* 284:    */     //   3: invokevirtual 103	org/json/simple/JSONObject:get	(Ljava/lang/Object;)Ljava/lang/Object;
/* 285:    */     //   6: checkcast 104	java/lang/Long
/* 286:    */     //   9: invokevirtual 105	java/lang/Long:intValue	()I
/* 287:    */     //   12: istore_1
/* 288:    */     //   13: aload_0
/* 289:    */     //   14: ldc 84
/* 290:    */     //   16: invokevirtual 103	org/json/simple/JSONObject:get	(Ljava/lang/Object;)Ljava/lang/Object;
/* 291:    */     //   19: checkcast 104	java/lang/Long
/* 292:    */     //   22: invokevirtual 105	java/lang/Long:intValue	()I
/* 293:    */     //   25: istore_2
/* 294:    */     //   26: aload_0
/* 295:    */     //   27: ldc 85
/* 296:    */     //   29: invokevirtual 103	org/json/simple/JSONObject:get	(Ljava/lang/Object;)Ljava/lang/Object;
/* 297:    */     //   32: checkcast 106	java/lang/String
/* 298:    */     //   35: invokestatic 107	nxt/util/Convert:parseUnsignedLong	(Ljava/lang/String;)J
/* 299:    */     //   38: invokestatic 64	java/lang/Long:valueOf	(J)Ljava/lang/Long;
/* 300:    */     //   41: astore_3
/* 301:    */     //   42: aload_0
/* 302:    */     //   43: ldc 86
/* 303:    */     //   45: invokevirtual 103	org/json/simple/JSONObject:get	(Ljava/lang/Object;)Ljava/lang/Object;
/* 304:    */     //   48: invokestatic 108	nxt/util/Convert:parseLong	(Ljava/lang/Object;)J
/* 305:    */     //   51: lstore 4
/* 306:    */     //   53: aload_0
/* 307:    */     //   54: ldc 87
/* 308:    */     //   56: invokevirtual 103	org/json/simple/JSONObject:get	(Ljava/lang/Object;)Ljava/lang/Object;
/* 309:    */     //   59: invokestatic 108	nxt/util/Convert:parseLong	(Ljava/lang/Object;)J
/* 310:    */     //   62: lstore 6
/* 311:    */     //   64: aload_0
/* 312:    */     //   65: ldc 88
/* 313:    */     //   67: invokevirtual 103	org/json/simple/JSONObject:get	(Ljava/lang/Object;)Ljava/lang/Object;
/* 314:    */     //   70: checkcast 104	java/lang/Long
/* 315:    */     //   73: invokevirtual 105	java/lang/Long:intValue	()I
/* 316:    */     //   76: istore 8
/* 317:    */     //   78: aload_0
/* 318:    */     //   79: ldc 89
/* 319:    */     //   81: invokevirtual 103	org/json/simple/JSONObject:get	(Ljava/lang/Object;)Ljava/lang/Object;
/* 320:    */     //   84: checkcast 106	java/lang/String
/* 321:    */     //   87: invokestatic 109	nxt/util/Convert:parseHexString	(Ljava/lang/String;)[B
/* 322:    */     //   90: astore 9
/* 323:    */     //   92: aload_0
/* 324:    */     //   93: ldc 91
/* 325:    */     //   95: invokevirtual 103	org/json/simple/JSONObject:get	(Ljava/lang/Object;)Ljava/lang/Object;
/* 326:    */     //   98: checkcast 106	java/lang/String
/* 327:    */     //   101: invokestatic 109	nxt/util/Convert:parseHexString	(Ljava/lang/String;)[B
/* 328:    */     //   104: astore 10
/* 329:    */     //   106: aload_0
/* 330:    */     //   107: ldc 92
/* 331:    */     //   109: invokevirtual 103	org/json/simple/JSONObject:get	(Ljava/lang/Object;)Ljava/lang/Object;
/* 332:    */     //   112: checkcast 106	java/lang/String
/* 333:    */     //   115: invokestatic 109	nxt/util/Convert:parseHexString	(Ljava/lang/String;)[B
/* 334:    */     //   118: astore 11
/* 335:    */     //   120: aload_0
/* 336:    */     //   121: ldc 94
/* 337:    */     //   123: invokevirtual 103	org/json/simple/JSONObject:get	(Ljava/lang/Object;)Ljava/lang/Object;
/* 338:    */     //   126: checkcast 106	java/lang/String
/* 339:    */     //   129: invokestatic 109	nxt/util/Convert:parseHexString	(Ljava/lang/String;)[B
/* 340:    */     //   132: astore 12
/* 341:    */     //   134: iload_1
/* 342:    */     //   135: iconst_1
/* 343:    */     //   136: if_icmpne +7 -> 143
/* 344:    */     //   139: aconst_null
/* 345:    */     //   140: goto +15 -> 155
/* 346:    */     //   143: aload_0
/* 347:    */     //   144: ldc 93
/* 348:    */     //   146: invokevirtual 103	org/json/simple/JSONObject:get	(Ljava/lang/Object;)Ljava/lang/Object;
/* 349:    */     //   149: checkcast 106	java/lang/String
/* 350:    */     //   152: invokestatic 109	nxt/util/Convert:parseHexString	(Ljava/lang/String;)[B
/* 351:    */     //   155: astore 13
/* 352:    */     //   157: aload_0
/* 353:    */     //   158: ldc 101
/* 354:    */     //   160: invokevirtual 103	org/json/simple/JSONObject:get	(Ljava/lang/Object;)Ljava/lang/Object;
/* 355:    */     //   163: checkcast 106	java/lang/String
/* 356:    */     //   166: invokestatic 107	nxt/util/Convert:parseUnsignedLong	(Ljava/lang/String;)J
/* 357:    */     //   169: invokestatic 64	java/lang/Long:valueOf	(J)Ljava/lang/Long;
/* 358:    */     //   172: astore 14
/* 359:    */     //   174: new 110	java/util/TreeMap
/* 360:    */     //   177: dup
/* 361:    */     //   178: invokespecial 111	java/util/TreeMap:<init>	()V
/* 362:    */     //   181: astore 15
/* 363:    */     //   183: aload_0
/* 364:    */     //   184: ldc 100
/* 365:    */     //   186: invokevirtual 103	org/json/simple/JSONObject:get	(Ljava/lang/Object;)Ljava/lang/Object;
/* 366:    */     //   189: checkcast 95	org/json/simple/JSONArray
/* 367:    */     //   192: astore 16
/* 368:    */     //   194: aload 16
/* 369:    */     //   196: invokevirtual 112	org/json/simple/JSONArray:iterator	()Ljava/util/Iterator;
/* 370:    */     //   199: astore 17
/* 371:    */     //   201: aload 17
/* 372:    */     //   203: invokeinterface 35 1 0
/* 373:    */     //   208: ifeq +76 -> 284
/* 374:    */     //   211: aload 17
/* 375:    */     //   213: invokeinterface 36 1 0
/* 376:    */     //   218: astore 18
/* 377:    */     //   220: aload 18
/* 378:    */     //   222: checkcast 79	org/json/simple/JSONObject
/* 379:    */     //   225: invokestatic 113	nxt/TransactionImpl:parseTransaction	(Lorg/json/simple/JSONObject;)Lnxt/TransactionImpl;
/* 380:    */     //   228: astore 19
/* 381:    */     //   230: aload 15
/* 382:    */     //   232: aload 19
/* 383:    */     //   234: invokevirtual 114	nxt/TransactionImpl:getId	()J
/* 384:    */     //   237: invokestatic 64	java/lang/Long:valueOf	(J)Ljava/lang/Long;
/* 385:    */     //   240: aload 19
/* 386:    */     //   242: invokeinterface 115 3 0
/* 387:    */     //   247: ifnull +34 -> 281
/* 388:    */     //   250: new 10	nxt/NxtException$NotValidException
/* 389:    */     //   253: dup
/* 390:    */     //   254: new 11	java/lang/StringBuilder
/* 391:    */     //   257: dup
/* 392:    */     //   258: invokespecial 12	java/lang/StringBuilder:<init>	()V
/* 393:    */     //   261: ldc 116
/* 394:    */     //   263: invokevirtual 14	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 395:    */     //   266: aload 19
/* 396:    */     //   268: invokevirtual 117	nxt/TransactionImpl:getStringId	()Ljava/lang/String;
/* 397:    */     //   271: invokevirtual 14	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 398:    */     //   274: invokevirtual 16	java/lang/StringBuilder:toString	()Ljava/lang/String;
/* 399:    */     //   277: invokespecial 17	nxt/NxtException$NotValidException:<init>	(Ljava/lang/String;)V
/* 400:    */     //   280: athrow
/* 401:    */     //   281: goto -80 -> 201
/* 402:    */     //   284: aload_0
/* 403:    */     //   285: ldc 102
/* 404:    */     //   287: invokevirtual 103	org/json/simple/JSONObject:get	(Ljava/lang/Object;)Ljava/lang/Object;
/* 405:    */     //   290: checkcast 106	java/lang/String
/* 406:    */     //   293: invokestatic 109	nxt/util/Convert:parseHexString	(Ljava/lang/String;)[B
/* 407:    */     //   296: astore 17
/* 408:    */     //   298: new 78	nxt/BlockImpl
/* 409:    */     //   301: dup
/* 410:    */     //   302: iload_1
/* 411:    */     //   303: iload_2
/* 412:    */     //   304: aload_3
/* 413:    */     //   305: invokevirtual 44	java/lang/Long:longValue	()J
/* 414:    */     //   308: lload 4
/* 415:    */     //   310: lload 6
/* 416:    */     //   312: iload 8
/* 417:    */     //   314: aload 9
/* 418:    */     //   316: aload 10
/* 419:    */     //   318: aload 11
/* 420:    */     //   320: aload 12
/* 421:    */     //   322: aload 13
/* 422:    */     //   324: new 118	java/util/ArrayList
/* 423:    */     //   327: dup
/* 424:    */     //   328: aload 15
/* 425:    */     //   330: invokeinterface 119 1 0
/* 426:    */     //   335: invokespecial 120	java/util/ArrayList:<init>	(Ljava/util/Collection;)V
/* 427:    */     //   338: aload 14
/* 428:    */     //   340: invokevirtual 44	java/lang/Long:longValue	()J
/* 429:    */     //   343: aload 17
/* 430:    */     //   345: invokespecial 42	nxt/BlockImpl:<init>	(IIJJJI[B[B[B[B[BLjava/util/List;J[B)V
/* 431:    */     //   348: areturn
/* 432:    */     //   349: astore_1
/* 433:    */     //   350: new 11	java/lang/StringBuilder
/* 434:    */     //   353: dup
/* 435:    */     //   354: invokespecial 12	java/lang/StringBuilder:<init>	()V
/* 436:    */     //   357: ldc 123
/* 437:    */     //   359: invokevirtual 14	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 438:    */     //   362: aload_0
/* 439:    */     //   363: invokevirtual 124	org/json/simple/JSONObject:toJSONString	()Ljava/lang/String;
/* 440:    */     //   366: invokevirtual 14	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 441:    */     //   369: invokevirtual 16	java/lang/StringBuilder:toString	()Ljava/lang/String;
/* 442:    */     //   372: invokestatic 125	nxt/util/Logger:logDebugMessage	(Ljava/lang/String;)V
/* 443:    */     //   375: aload_1
/* 444:    */     //   376: athrow
/* 445:    */     // Line number table:
/* 446:    */     //   Java source line #282	-> byte code offset #0
/* 447:    */     //   Java source line #283	-> byte code offset #13
/* 448:    */     //   Java source line #284	-> byte code offset #26
/* 449:    */     //   Java source line #285	-> byte code offset #42
/* 450:    */     //   Java source line #286	-> byte code offset #53
/* 451:    */     //   Java source line #287	-> byte code offset #64
/* 452:    */     //   Java source line #288	-> byte code offset #78
/* 453:    */     //   Java source line #289	-> byte code offset #92
/* 454:    */     //   Java source line #290	-> byte code offset #106
/* 455:    */     //   Java source line #291	-> byte code offset #120
/* 456:    */     //   Java source line #292	-> byte code offset #134
/* 457:    */     //   Java source line #293	-> byte code offset #157
/* 458:    */     //   Java source line #295	-> byte code offset #174
/* 459:    */     //   Java source line #296	-> byte code offset #183
/* 460:    */     //   Java source line #297	-> byte code offset #194
/* 461:    */     //   Java source line #298	-> byte code offset #220
/* 462:    */     //   Java source line #299	-> byte code offset #230
/* 463:    */     //   Java source line #300	-> byte code offset #250
/* 464:    */     //   Java source line #302	-> byte code offset #281
/* 465:    */     //   Java source line #303	-> byte code offset #284
/* 466:    */     //   Java source line #304	-> byte code offset #298
/* 467:    */     //   Java source line #306	-> byte code offset #349
/* 468:    */     //   Java source line #307	-> byte code offset #350
/* 469:    */     //   Java source line #308	-> byte code offset #375
/* 470:    */     // Local variable table:
/* 471:    */     //   start	length	slot	name	signature
/* 472:    */     //   0	377	0	paramJSONObject	JSONObject
/* 473:    */     //   12	291	1	i	int
/* 474:    */     //   349	27	1	localValidationException	NxtException.ValidationException
/* 475:    */     //   25	279	2	j	int
/* 476:    */     //   41	264	3	localLong1	Long
/* 477:    */     //   51	258	4	l1	long
/* 478:    */     //   62	249	6	l2	long
/* 479:    */     //   76	237	8	k	int
/* 480:    */     //   90	225	9	arrayOfByte1	byte[]
/* 481:    */     //   104	213	10	arrayOfByte2	byte[]
/* 482:    */     //   118	201	11	arrayOfByte3	byte[]
/* 483:    */     //   132	189	12	arrayOfByte4	byte[]
/* 484:    */     //   155	168	13	arrayOfByte5	byte[]
/* 485:    */     //   172	167	14	localLong2	Long
/* 486:    */     //   181	148	15	localTreeMap	java.util.TreeMap
/* 487:    */     //   192	3	16	localJSONArray	JSONArray
/* 488:    */     //   199	145	17	localObject1	Object
/* 489:    */     //   218	3	18	localObject2	Object
/* 490:    */     //   228	39	19	localTransactionImpl	TransactionImpl
/* 491:    */     // Exception table:
/* 492:    */     //   from	to	target	type
/* 493:    */     //   0	348	349	nxt/NxtException$ValidationException
/* 494:    */     //   0	348	349	java/lang/RuntimeException
/* 495:    */   }
/* 496:    */   
/* 497:    */   byte[] getBytes()
/* 498:    */   {
/* 499:313 */     ByteBuffer localByteBuffer = ByteBuffer.allocate(20 + (this.version < 3 ? 8 : 16) + 4 + 32 + 32 + 64 + 8 + (this.blockATs != null ? this.blockATs.length : 0) + 64);
/* 500:314 */     localByteBuffer.order(ByteOrder.LITTLE_ENDIAN);
/* 501:315 */     localByteBuffer.putInt(this.version);
/* 502:316 */     localByteBuffer.putInt(this.timestamp);
/* 503:317 */     localByteBuffer.putLong(this.previousBlockId);
/* 504:318 */     localByteBuffer.putInt(getTransactions().size());
/* 505:319 */     if (this.version < 3)
/* 506:    */     {
/* 507:320 */       localByteBuffer.putInt((int)(this.totalAmountNQT / 100000000L));
/* 508:321 */       localByteBuffer.putInt((int)(this.totalFeeNQT / 100000000L));
/* 509:    */     }
/* 510:    */     else
/* 511:    */     {
/* 512:323 */       localByteBuffer.putLong(this.totalAmountNQT);
/* 513:324 */       localByteBuffer.putLong(this.totalFeeNQT);
/* 514:    */     }
/* 515:326 */     localByteBuffer.putInt(this.payloadLength);
/* 516:327 */     localByteBuffer.put(this.payloadHash);
/* 517:328 */     localByteBuffer.put(this.generatorPublicKey);
/* 518:329 */     localByteBuffer.put(this.generationSignature);
/* 519:330 */     if (this.version > 1) {
/* 520:331 */       localByteBuffer.put(this.previousBlockHash);
/* 521:    */     }
/* 522:333 */     localByteBuffer.putLong(this.nonce);
/* 523:334 */     if (this.blockATs != null) {
/* 524:335 */       localByteBuffer.put(this.blockATs);
/* 525:    */     }
/* 526:336 */     localByteBuffer.put(this.blockSignature);
/* 527:337 */     return localByteBuffer.array();
/* 528:    */   }
/* 529:    */   
/* 530:    */   void sign(String paramString)
/* 531:    */   {
/* 532:341 */     if (this.blockSignature != null) {
/* 533:342 */       throw new IllegalStateException("Block already signed");
/* 534:    */     }
/* 535:344 */     this.blockSignature = new byte[64];
/* 536:345 */     byte[] arrayOfByte1 = getBytes();
/* 537:346 */     byte[] arrayOfByte2 = new byte[arrayOfByte1.length - 64];
/* 538:347 */     System.arraycopy(arrayOfByte1, 0, arrayOfByte2, 0, arrayOfByte2.length);
/* 539:348 */     this.blockSignature = Crypto.sign(arrayOfByte2, paramString);
/* 540:    */   }
/* 541:    */   
/* 542:    */   boolean verifyBlockSignature()
/* 543:    */     throws BlockchainProcessor.BlockOutOfOrderException
/* 544:    */   {
/* 545:    */     try
/* 546:    */     {
/* 547:355 */       BlockImpl localBlockImpl = (BlockImpl)Nxt.getBlockchain().getBlock(this.previousBlockId);
/* 548:356 */       if (localBlockImpl == null) {
/* 549:357 */         throw new BlockchainProcessor.BlockOutOfOrderException("Can't verify signature because previous block is missing");
/* 550:    */       }
/* 551:360 */       byte[] arrayOfByte1 = getBytes();
/* 552:361 */       byte[] arrayOfByte2 = new byte[arrayOfByte1.length - 64];
/* 553:362 */       System.arraycopy(arrayOfByte1, 0, arrayOfByte2, 0, arrayOfByte2.length);
/* 554:    */       
/* 555:    */ 
/* 556:365 */       Account localAccount = Account.getAccount(this.generatorPublicKey);
/* 557:    */       
/* 558:367 */       Account.RewardRecipientAssignment localRewardRecipientAssignment = localAccount == null ? null : localAccount.getRewardRecipientAssignment();
/* 559:    */       byte[] arrayOfByte3;
/* 560:368 */       if ((localAccount == null) || (localRewardRecipientAssignment == null) || (localBlockImpl.getHeight() + 1 < Constants.BURST_REWARD_RECIPIENT_ASSIGNMENT_START_BLOCK)) {
/* 561:371 */         arrayOfByte3 = this.generatorPublicKey;
/* 562:374 */       } else if (localBlockImpl.getHeight() + 1 >= localRewardRecipientAssignment.getFromHeight()) {
/* 563:375 */         arrayOfByte3 = Account.getAccount(localRewardRecipientAssignment.getRecipientId()).getPublicKey();
/* 564:    */       } else {
/* 565:378 */         arrayOfByte3 = Account.getAccount(localRewardRecipientAssignment.getPrevRecipientId()).getPublicKey();
/* 566:    */       }
/* 567:382 */       return Crypto.verify(this.blockSignature, arrayOfByte2, arrayOfByte3, this.version >= 3);
/* 568:    */     }
/* 569:    */     catch (RuntimeException localRuntimeException)
/* 570:    */     {
/* 571:386 */       Logger.logMessage("Error verifying block signature", localRuntimeException);
/* 572:    */     }
/* 573:387 */     return false;
/* 574:    */   }
/* 575:    */   
/* 576:    */   boolean verifyGenerationSignature()
/* 577:    */     throws BlockchainProcessor.BlockOutOfOrderException
/* 578:    */   {
/* 579:    */     try
/* 580:    */     {
/* 581:397 */       BlockImpl localBlockImpl = (BlockImpl)Nxt.getBlockchain().getBlock(this.previousBlockId);
/* 582:398 */       if (localBlockImpl == null) {
/* 583:399 */         throw new BlockchainProcessor.BlockOutOfOrderException("Can't verify generation signature because previous block is missing");
/* 584:    */       }
/* 585:404 */       ByteBuffer localByteBuffer1 = ByteBuffer.allocate(40);
/* 586:405 */       localByteBuffer1.put(localBlockImpl.getGenerationSignature());
/* 587:406 */       localByteBuffer1.putLong(localBlockImpl.getGeneratorId());
/* 588:    */       
/* 589:408 */       Shabal256 localShabal256 = new Shabal256();
/* 590:409 */       localShabal256.update(localByteBuffer1.array());
/* 591:410 */       byte[] arrayOfByte1 = localShabal256.digest();
/* 592:411 */       if (!Arrays.equals(this.generationSignature, arrayOfByte1)) {
/* 593:412 */         return false;
/* 594:    */       }
/* 595:416 */       MiningPlot localMiningPlot = new MiningPlot(getGeneratorId(), this.nonce);
/* 596:    */       
/* 597:418 */       ByteBuffer localByteBuffer2 = ByteBuffer.allocate(40);
/* 598:419 */       localByteBuffer2.put(arrayOfByte1);
/* 599:420 */       localByteBuffer2.putLong(localBlockImpl.getHeight() + 1);
/* 600:421 */       localShabal256.reset();
/* 601:422 */       localShabal256.update(localByteBuffer2.array());
/* 602:423 */       BigInteger localBigInteger1 = new BigInteger(1, localShabal256.digest());
/* 603:424 */       int i = localBigInteger1.mod(BigInteger.valueOf(MiningPlot.SCOOPS_PER_PLOT)).intValue();
/* 604:    */       
/* 605:426 */       localShabal256.reset();
/* 606:427 */       localShabal256.update(arrayOfByte1);
/* 607:428 */       localMiningPlot.hashScoop(localShabal256, i);
/* 608:429 */       byte[] arrayOfByte2 = localShabal256.digest();
/* 609:430 */       BigInteger localBigInteger2 = new BigInteger(1, new byte[] { arrayOfByte2[7], arrayOfByte2[6], arrayOfByte2[5], arrayOfByte2[4], arrayOfByte2[3], arrayOfByte2[2], arrayOfByte2[1], arrayOfByte2[0] });
/* 610:431 */       BigInteger localBigInteger3 = localBigInteger2.divide(BigInteger.valueOf(localBlockImpl.getBaseTarget()));
/* 611:    */       
/* 612:433 */       int j = this.timestamp - localBlockImpl.timestamp;
/* 613:    */       
/* 614:435 */       return BigInteger.valueOf(j).compareTo(localBigInteger3) > 0;
/* 615:    */     }
/* 616:    */     catch (RuntimeException localRuntimeException)
/* 617:    */     {
/* 618:439 */       Logger.logMessage("Error verifying block generation signature", localRuntimeException);
/* 619:    */     }
/* 620:440 */     return false;
/* 621:    */   }
/* 622:    */   
/* 623:    */   void apply()
/* 624:    */   {
/* 625:447 */     Account localAccount = Account.addOrGetAccount(getGeneratorId());
/* 626:448 */     localAccount.apply(this.generatorPublicKey, this.height);
/* 627:    */     Object localObject2;
/* 628:449 */     if (this.height < Constants.BURST_REWARD_RECIPIENT_ASSIGNMENT_START_BLOCK)
/* 629:    */     {
/* 630:450 */       localAccount.addToBalanceAndUnconfirmedBalanceNQT(this.totalFeeNQT + getBlockReward());
/* 631:451 */       localAccount.addToForgedBalanceNQT(this.totalFeeNQT + getBlockReward());
/* 632:    */     }
/* 633:    */     else
/* 634:    */     {
/* 635:455 */       localObject2 = localAccount.getRewardRecipientAssignment();
/* 636:456 */       if (localObject2 == null) {
/* 637:457 */         localObject1 = localAccount;
/* 638:459 */       } else if (this.height >= ((Account.RewardRecipientAssignment)localObject2).getFromHeight()) {
/* 639:460 */         localObject1 = Account.getAccount(((Account.RewardRecipientAssignment)localObject2).getRecipientId());
/* 640:    */       } else {
/* 641:463 */         localObject1 = Account.getAccount(((Account.RewardRecipientAssignment)localObject2).getPrevRecipientId());
/* 642:    */       }
/* 643:465 */       ((Account)localObject1).addToBalanceAndUnconfirmedBalanceNQT(this.totalFeeNQT + getBlockReward());
/* 644:466 */       ((Account)localObject1).addToForgedBalanceNQT(this.totalFeeNQT + getBlockReward());
/* 645:    */     }
/* 646:468 */     for (Object localObject1 = getTransactions().iterator(); ((Iterator)localObject1).hasNext();)
/* 647:    */     {
/* 648:468 */       localObject2 = (TransactionImpl)((Iterator)localObject1).next();
/* 649:469 */       ((TransactionImpl)localObject2).apply();
/* 650:    */     }
/* 651:    */   }
/* 652:    */   
/* 653:    */   public long getBlockReward()
/* 654:    */   {
/* 655:475 */     if ((this.height == 0) || (this.height >= 1944000)) {
/* 656:476 */       return 0L;
/* 657:    */     }
/* 658:478 */     int i = this.height / 10800;
/* 659:479 */     long l = BigInteger.valueOf(10000L).multiply(BigInteger.valueOf(95L).pow(i)).divide(BigInteger.valueOf(100L).pow(i)).longValue() * 100000000L;
/* 660:    */     
/* 661:    */ 
/* 662:    */ 
/* 663:483 */     return l;
/* 664:    */   }
/* 665:    */   
/* 666:    */   void setPrevious(BlockImpl paramBlockImpl)
/* 667:    */   {
/* 668:487 */     if (paramBlockImpl != null)
/* 669:    */     {
/* 670:488 */       if (paramBlockImpl.getId() != getPreviousBlockId()) {
/* 671:490 */         throw new IllegalStateException("Previous block id doesn't match");
/* 672:    */       }
/* 673:492 */       this.height = (paramBlockImpl.getHeight() + 1);
/* 674:493 */       calculateBaseTarget(paramBlockImpl);
/* 675:    */     }
/* 676:    */     else
/* 677:    */     {
/* 678:495 */       this.height = 0;
/* 679:    */     }
/* 680:497 */     for (TransactionImpl localTransactionImpl : getTransactions()) {
/* 681:498 */       localTransactionImpl.setBlock(this);
/* 682:    */     }
/* 683:    */   }
/* 684:    */   
/* 685:    */   private void calculateBaseTarget(BlockImpl paramBlockImpl)
/* 686:    */   {
/* 687:504 */     if ((getId() == 3444294670862540038L) && (this.previousBlockId == 0L))
/* 688:    */     {
/* 689:505 */       this.baseTarget = 18325193796L;
/* 690:506 */       this.cumulativeDifficulty = BigInteger.ZERO;
/* 691:    */     }
/* 692:507 */     else if (this.height < 4)
/* 693:    */     {
/* 694:508 */       this.baseTarget = 18325193796L;
/* 695:509 */       this.cumulativeDifficulty = paramBlockImpl.cumulativeDifficulty.add(Convert.two64.divide(BigInteger.valueOf(18325193796L)));
/* 696:    */     }
/* 697:    */     else
/* 698:    */     {
/* 699:    */       Object localObject;
/* 700:    */       BigInteger localBigInteger;
/* 701:510 */       if (this.height < Constants.BURST_DIFF_ADJUST_CHANGE_BLOCK)
/* 702:    */       {
/* 703:511 */         localObject = paramBlockImpl;
/* 704:512 */         localBigInteger = BigInteger.valueOf(((Block)localObject).getBaseTarget());
/* 705:    */         do
/* 706:    */         {
/* 707:514 */           localObject = Nxt.getBlockchain().getBlock(((Block)localObject).getPreviousBlockId());
/* 708:515 */           localBigInteger = localBigInteger.add(BigInteger.valueOf(((Block)localObject).getBaseTarget()));
/* 709:516 */         } while (((Block)localObject).getHeight() > this.height - 4);
/* 710:517 */         localBigInteger = localBigInteger.divide(BigInteger.valueOf(4L));
/* 711:518 */         long l1 = this.timestamp - ((Block)localObject).getTimestamp();
/* 712:    */         
/* 713:520 */         long l3 = localBigInteger.longValue();
/* 714:521 */         long l5 = BigInteger.valueOf(l3).multiply(BigInteger.valueOf(l1)).divide(BigInteger.valueOf(960L)).longValue();
/* 715:524 */         if ((l5 < 0L) || (l5 > 18325193796L)) {
/* 716:525 */           l5 = 18325193796L;
/* 717:    */         }
/* 718:527 */         if (l5 < l3 * 9L / 10L) {
/* 719:528 */           l5 = l3 * 9L / 10L;
/* 720:    */         }
/* 721:530 */         if (l5 == 0L) {
/* 722:531 */           l5 = 1L;
/* 723:    */         }
/* 724:533 */         long l7 = l3 * 11L / 10L;
/* 725:534 */         if (l7 < 0L) {
/* 726:535 */           l7 = 18325193796L;
/* 727:    */         }
/* 728:537 */         if (l5 > l7) {
/* 729:538 */           l5 = l7;
/* 730:    */         }
/* 731:540 */         this.baseTarget = l5;
/* 732:541 */         this.cumulativeDifficulty = paramBlockImpl.cumulativeDifficulty.add(Convert.two64.divide(BigInteger.valueOf(this.baseTarget)));
/* 733:    */       }
/* 734:    */       else
/* 735:    */       {
/* 736:544 */         localObject = paramBlockImpl;
/* 737:545 */         localBigInteger = BigInteger.valueOf(((Block)localObject).getBaseTarget());
/* 738:546 */         int i = 1;
/* 739:    */         do
/* 740:    */         {
/* 741:548 */           localObject = Nxt.getBlockchain().getBlock(((Block)localObject).getPreviousBlockId());
/* 742:549 */           i++;
/* 743:550 */           localBigInteger = localBigInteger.multiply(BigInteger.valueOf(i)).add(BigInteger.valueOf(((Block)localObject).getBaseTarget())).divide(BigInteger.valueOf(i + 1));
/* 744:553 */         } while (i < 24);
/* 745:554 */         long l2 = this.timestamp - ((Block)localObject).getTimestamp();
/* 746:555 */         long l4 = 5760L;
/* 747:557 */         if (l2 < l4 / 2L) {
/* 748:558 */           l2 = l4 / 2L;
/* 749:    */         }
/* 750:561 */         if (l2 > l4 * 2L) {
/* 751:562 */           l2 = l4 * 2L;
/* 752:    */         }
/* 753:565 */         long l6 = paramBlockImpl.getBaseTarget();
/* 754:566 */         long l8 = localBigInteger.multiply(BigInteger.valueOf(l2)).divide(BigInteger.valueOf(l4)).longValue();
/* 755:570 */         if ((l8 < 0L) || (l8 > 18325193796L)) {
/* 756:571 */           l8 = 18325193796L;
/* 757:    */         }
/* 758:574 */         if (l8 == 0L) {
/* 759:575 */           l8 = 1L;
/* 760:    */         }
/* 761:578 */         if (l8 < l6 * 8L / 10L) {
/* 762:579 */           l8 = l6 * 8L / 10L;
/* 763:    */         }
/* 764:582 */         if (l8 > l6 * 12L / 10L) {
/* 765:583 */           l8 = l6 * 12L / 10L;
/* 766:    */         }
/* 767:586 */         this.baseTarget = l8;
/* 768:587 */         this.cumulativeDifficulty = paramBlockImpl.cumulativeDifficulty.add(Convert.two64.divide(BigInteger.valueOf(this.baseTarget)));
/* 769:    */       }
/* 770:    */     }
/* 771:    */   }
/* 772:    */   
/* 773:    */   public byte[] getBlockATs()
/* 774:    */   {
/* 775:593 */     return this.blockATs;
/* 776:    */   }
/* 777:    */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.BlockImpl
 * JD-Core Version:    0.7.1
 */