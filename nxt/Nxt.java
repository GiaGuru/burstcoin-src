/*   1:    */ package nxt;
/*   2:    */ 
/*   3:    */ import java.io.FileInputStream;
/*   4:    */ import java.io.IOException;
/*   5:    */ import java.io.InputStream;
/*   6:    */ import java.io.PrintStream;
/*   7:    */ import java.util.ArrayList;
/*   8:    */ import java.util.Collections;
/*   9:    */ import java.util.List;
/*  10:    */ import java.util.Properties;
/*  11:    */ import nxt.db.Db;
/*  12:    */ import nxt.http.API;
/*  13:    */ import nxt.peer.Peers;
/*  14:    */ import nxt.user.Users;
/*  15:    */ import nxt.util.Logger;
/*  16:    */ import nxt.util.ThreadPool;
/*  17:    */ import nxt.util.Time;
/*  18:    */ import nxt.util.Time.EpochTime;
/*  19:    */ import nxt.util.Time.FasterTime;
/*  20:    */ 
/*  21:    */ public final class Nxt
/*  22:    */ {
/*  23:    */   public static final String VERSION = "1.2.2";
/*  24:    */   public static final String APPLICATION = "NRS";
/*  25: 24 */   private static volatile Time time = new Time.EpochTime();
/*  26: 26 */   private static final Properties defaultProperties = new Properties();
/*  27:    */   private static final Properties properties;
/*  28:    */   
/*  29:    */   static
/*  30:    */   {
/*  31: 28 */     System.out.println("Initializing Burst server version 1.2.2");
/*  32:    */     Object localObject1;
/*  33:    */     try
/*  34:    */     {
/*  35: 29 */       InputStream localInputStream1 = ClassLoader.getSystemResourceAsStream("nxt-default.properties");localObject1 = null;
/*  36:    */       try
/*  37:    */       {
/*  38: 30 */         if (localInputStream1 != null)
/*  39:    */         {
/*  40: 31 */           defaultProperties.load(localInputStream1);
/*  41:    */         }
/*  42:    */         else
/*  43:    */         {
/*  44: 33 */           String str = System.getProperty("nxt-default.properties");
/*  45: 34 */           if (str != null) {
/*  46:    */             try
/*  47:    */             {
/*  48: 35 */               FileInputStream localFileInputStream = new FileInputStream(str);Object localObject2 = null;
/*  49:    */               try
/*  50:    */               {
/*  51: 36 */                 defaultProperties.load(localFileInputStream);
/*  52:    */               }
/*  53:    */               catch (Throwable localThrowable6)
/*  54:    */               {
/*  55: 35 */                 localObject2 = localThrowable6;throw localThrowable6;
/*  56:    */               }
/*  57:    */               finally {}
/*  58:    */             }
/*  59:    */             catch (IOException localIOException3)
/*  60:    */             {
/*  61: 38 */               throw new RuntimeException("Error loading nxt-default.properties from " + str);
/*  62:    */             }
/*  63:    */           } else {
/*  64: 41 */             throw new RuntimeException("nxt-default.properties not in classpath and system property nxt-default.properties not defined either");
/*  65:    */           }
/*  66:    */         }
/*  67:    */       }
/*  68:    */       catch (Throwable localThrowable2)
/*  69:    */       {
/*  70: 29 */         localObject1 = localThrowable2;throw localThrowable2;
/*  71:    */       }
/*  72:    */       finally
/*  73:    */       {
/*  74: 44 */         if (localInputStream1 != null) {
/*  75: 44 */           if (localObject1 != null) {
/*  76:    */             try
/*  77:    */             {
/*  78: 44 */               localInputStream1.close();
/*  79:    */             }
/*  80:    */             catch (Throwable localThrowable8)
/*  81:    */             {
/*  82: 44 */               ((Throwable)localObject1).addSuppressed(localThrowable8);
/*  83:    */             }
/*  84:    */           } else {
/*  85: 44 */             localInputStream1.close();
/*  86:    */           }
/*  87:    */         }
/*  88:    */       }
/*  89:    */     }
/*  90:    */     catch (IOException localIOException1)
/*  91:    */     {
/*  92: 45 */       throw new RuntimeException("Error loading nxt-default.properties", localIOException1);
/*  93:    */     }
/*  94: 48 */     properties = new Properties(defaultProperties);
/*  95:    */     try
/*  96:    */     {
/*  97: 50 */       InputStream localInputStream2 = ClassLoader.getSystemResourceAsStream("nxt.properties");localObject1 = null;
/*  98:    */       try
/*  99:    */       {
/* 100: 51 */         if (localInputStream2 != null) {
/* 101: 52 */           properties.load(localInputStream2);
/* 102:    */         }
/* 103:    */       }
/* 104:    */       catch (Throwable localThrowable4)
/* 105:    */       {
/* 106: 50 */         localObject1 = localThrowable4;throw localThrowable4;
/* 107:    */       }
/* 108:    */       finally
/* 109:    */       {
/* 110: 54 */         if (localInputStream2 != null) {
/* 111: 54 */           if (localObject1 != null) {
/* 112:    */             try
/* 113:    */             {
/* 114: 54 */               localInputStream2.close();
/* 115:    */             }
/* 116:    */             catch (Throwable localThrowable9)
/* 117:    */             {
/* 118: 54 */               ((Throwable)localObject1).addSuppressed(localThrowable9);
/* 119:    */             }
/* 120:    */           } else {
/* 121: 54 */             localInputStream2.close();
/* 122:    */           }
/* 123:    */         }
/* 124:    */       }
/* 125:    */     }
/* 126:    */     catch (IOException localIOException2)
/* 127:    */     {
/* 128: 55 */       throw new RuntimeException("Error loading nxt.properties", localIOException2);
/* 129:    */     }
/* 130:    */   }
/* 131:    */   
/* 132:    */   public static int getIntProperty(String paramString)
/* 133:    */   {
/* 134:    */     try
/* 135:    */     {
/* 136: 61 */       int i = Integer.parseInt(properties.getProperty(paramString));
/* 137: 62 */       Logger.logMessage(paramString + " = \"" + i + "\"");
/* 138: 63 */       return i;
/* 139:    */     }
/* 140:    */     catch (NumberFormatException localNumberFormatException)
/* 141:    */     {
/* 142: 65 */       Logger.logMessage(paramString + " not defined, assuming 0");
/* 143:    */     }
/* 144: 66 */     return 0;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public static String getStringProperty(String paramString)
/* 148:    */   {
/* 149: 71 */     return getStringProperty(paramString, null);
/* 150:    */   }
/* 151:    */   
/* 152:    */   public static String getStringProperty(String paramString1, String paramString2)
/* 153:    */   {
/* 154: 75 */     String str = properties.getProperty(paramString1);
/* 155: 76 */     if ((str != null) && (!"".equals(str)))
/* 156:    */     {
/* 157: 77 */       Logger.logMessage(paramString1 + " = \"" + str + "\"");
/* 158: 78 */       return str;
/* 159:    */     }
/* 160: 80 */     Logger.logMessage(paramString1 + " not defined");
/* 161: 81 */     return paramString2;
/* 162:    */   }
/* 163:    */   
/* 164:    */   public static List<String> getStringListProperty(String paramString)
/* 165:    */   {
/* 166: 86 */     String str1 = getStringProperty(paramString);
/* 167: 87 */     if ((str1 == null) || (str1.length() == 0)) {
/* 168: 88 */       return Collections.emptyList();
/* 169:    */     }
/* 170: 90 */     ArrayList localArrayList = new ArrayList();
/* 171: 91 */     for (String str2 : str1.split(";"))
/* 172:    */     {
/* 173: 92 */       str2 = str2.trim();
/* 174: 93 */       if (str2.length() > 0) {
/* 175: 94 */         localArrayList.add(str2);
/* 176:    */       }
/* 177:    */     }
/* 178: 97 */     return localArrayList;
/* 179:    */   }
/* 180:    */   
/* 181:    */   public static Boolean getBooleanProperty(String paramString)
/* 182:    */   {
/* 183:101 */     String str = properties.getProperty(paramString);
/* 184:102 */     if (Boolean.TRUE.toString().equals(str))
/* 185:    */     {
/* 186:103 */       Logger.logMessage(paramString + " = \"true\"");
/* 187:104 */       return Boolean.valueOf(true);
/* 188:    */     }
/* 189:105 */     if (Boolean.FALSE.toString().equals(str))
/* 190:    */     {
/* 191:106 */       Logger.logMessage(paramString + " = \"false\"");
/* 192:107 */       return Boolean.valueOf(false);
/* 193:    */     }
/* 194:109 */     Logger.logMessage(paramString + " not defined, assuming false");
/* 195:110 */     return Boolean.valueOf(false);
/* 196:    */   }
/* 197:    */   
/* 198:    */   public static Blockchain getBlockchain()
/* 199:    */   {
/* 200:114 */     return BlockchainImpl.getInstance();
/* 201:    */   }
/* 202:    */   
/* 203:    */   public static BlockchainProcessor getBlockchainProcessor()
/* 204:    */   {
/* 205:118 */     return BlockchainProcessorImpl.getInstance();
/* 206:    */   }
/* 207:    */   
/* 208:    */   public static TransactionProcessor getTransactionProcessor()
/* 209:    */   {
/* 210:122 */     return TransactionProcessorImpl.getInstance();
/* 211:    */   }
/* 212:    */   
/* 213:    */   public static int getEpochTime()
/* 214:    */   {
/* 215:126 */     return time.getTime();
/* 216:    */   }
/* 217:    */   
/* 218:    */   static void setTime(Time paramTime)
/* 219:    */   {
/* 220:130 */     time = paramTime;
/* 221:    */   }
/* 222:    */   
/* 223:    */   public static void main(String[] paramArrayOfString)
/* 224:    */   {
/* 225:134 */     Runtime.getRuntime().addShutdownHook(new Thread(new Runnable()
/* 226:    */     {
/* 227:    */       public void run() {}
/* 228:139 */     }));
/* 229:140 */     init();
/* 230:    */   }
/* 231:    */   
/* 232:    */   public static void init(Properties paramProperties)
/* 233:    */   {
/* 234:144 */     properties.putAll(paramProperties);
/* 235:145 */     init();
/* 236:    */   }
/* 237:    */   
/* 238:    */   public static void shutdown()
/* 239:    */   {
/* 240:153 */     Logger.logShutdownMessage("Shutting down...");
/* 241:154 */     API.shutdown();
/* 242:155 */     Users.shutdown();
/* 243:156 */     Peers.shutdown();
/* 244:157 */     ThreadPool.shutdown();
/* 245:158 */     Db.shutdown();
/* 246:159 */     Logger.logShutdownMessage("Burst server 1.2.2 stopped.");
/* 247:160 */     Logger.shutdown();
/* 248:    */   }
/* 249:    */   
/* 250:    */   public static void init() {}
/* 251:    */   
/* 252:    */   private static class Init
/* 253:    */   {
/* 254:    */     private static void init() {}
/* 255:    */     
/* 256:    */     static
/* 257:    */     {
/* 258:    */       try
/* 259:    */       {
/* 260:167 */         long l1 = System.currentTimeMillis();
/* 261:168 */         Logger.init();
/* 262:169 */         Db.init();
/* 263:170 */         TransactionProcessorImpl.getInstance();
/* 264:171 */         BlockchainProcessorImpl.getInstance();
/* 265:172 */         DbVersion.init();
/* 266:173 */         Account.init();
/* 267:174 */         Alias.init();
/* 268:175 */         Asset.init();
/* 269:176 */         DigitalGoodsStore.init();
/* 270:177 */         Hub.init();
/* 271:178 */         Order.init();
/* 272:179 */         Poll.init();
/* 273:180 */         Trade.init();
/* 274:181 */         AssetTransfer.init();
/* 275:182 */         Vote.init();
/* 276:183 */         AT.init();
/* 277:184 */         Peers.init();
/* 278:185 */         Generator.init();
/* 279:186 */         API.init();
/* 280:187 */         Users.init();
/* 281:188 */         DebugTrace.init();
/* 282:189 */         int i = (Constants.isTestnet) && (Constants.isOffline) ? Math.max(Nxt.getIntProperty("nxt.timeMultiplier"), 1) : 1;
/* 283:190 */         ThreadPool.start(i);
/* 284:191 */         if (i > 1)
/* 285:    */         {
/* 286:192 */           Nxt.setTime(new Time.FasterTime(Math.max(Nxt.getEpochTime(), Nxt.getBlockchain().getLastBlock().getTimestamp()), i));
/* 287:193 */           Logger.logMessage("TIME WILL FLOW " + i + " TIMES FASTER!");
/* 288:    */         }
/* 289:196 */         long l2 = System.currentTimeMillis();
/* 290:197 */         Logger.logMessage("Initialization took " + (l2 - l1) / 1000L + " seconds");
/* 291:198 */         Logger.logMessage("Burst server 1.2.2 started successfully.");
/* 292:199 */         if (Constants.isTestnet) {
/* 293:200 */           Logger.logMessage("RUNNING ON TESTNET - DO NOT USE REAL ACCOUNTS!");
/* 294:    */         }
/* 295:    */       }
/* 296:    */       catch (Exception localException)
/* 297:    */       {
/* 298:203 */         Logger.logErrorMessage(localException.getMessage(), localException);
/* 299:204 */         System.exit(1);
/* 300:    */       }
/* 301:    */     }
/* 302:    */   }
/* 303:    */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.Nxt
 * JD-Core Version:    0.7.1
 */