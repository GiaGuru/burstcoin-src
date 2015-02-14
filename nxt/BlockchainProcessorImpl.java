/*   1:    */ package nxt;
/*   2:    */ 
/*   3:    */ import fr.cryptohash.Shabal256;
/*   4:    */ import java.math.BigInteger;
/*   5:    */ import java.nio.ByteBuffer;
/*   6:    */ import java.nio.ByteOrder;
/*   7:    */ import java.security.MessageDigest;
/*   8:    */ import java.security.NoSuchAlgorithmException;
/*   9:    */ import java.sql.Connection;
/*  10:    */ import java.sql.SQLException;
/*  11:    */ import java.util.ArrayList;
/*  12:    */ import java.util.Arrays;
/*  13:    */ import java.util.Collection;
/*  14:    */ import java.util.Collections;
/*  15:    */ import java.util.HashMap;
/*  16:    */ import java.util.Iterator;
/*  17:    */ import java.util.List;
/*  18:    */ import java.util.SortedSet;
/*  19:    */ import java.util.TreeSet;
/*  20:    */ import java.util.concurrent.CopyOnWriteArrayList;
/*  21:    */ import nxt.at.AT_Block;
/*  22:    */ import nxt.at.AT_Controller;
/*  23:    */ import nxt.at.AT_Exception;
/*  24:    */ import nxt.crypto.Crypto;
/*  25:    */ import nxt.db.Db;
/*  26:    */ import nxt.db.DerivedDbTable;
/*  27:    */ import nxt.db.FilteringIterator;
/*  28:    */ import nxt.db.FilteringIterator.Filter;
/*  29:    */ import nxt.peer.Peer;
/*  30:    */ import nxt.peer.Peers;
/*  31:    */ import nxt.util.Convert;
/*  32:    */ import nxt.util.JSON;
/*  33:    */ import nxt.util.Listener;
/*  34:    */ import nxt.util.Listeners;
/*  35:    */ import nxt.util.Logger;
/*  36:    */ import nxt.util.ThreadPool;
/*  37:    */ import org.json.simple.JSONArray;
/*  38:    */ import org.json.simple.JSONObject;
/*  39:    */ import org.json.simple.JSONStreamAware;
/*  40:    */ 
/*  41:    */ final class BlockchainProcessorImpl
/*  42:    */   implements BlockchainProcessor
/*  43:    */ {
/*  44: 52 */   private static final BlockchainProcessorImpl instance = new BlockchainProcessorImpl();
/*  45:    */   
/*  46:    */   static BlockchainProcessorImpl getInstance()
/*  47:    */   {
/*  48: 55 */     return instance;
/*  49:    */   }
/*  50:    */   
/*  51: 58 */   private final BlockchainImpl blockchain = BlockchainImpl.getInstance();
/*  52: 60 */   private final List<DerivedDbTable> derivedTables = new CopyOnWriteArrayList();
/*  53: 61 */   private final boolean trimDerivedTables = Nxt.getBooleanProperty("nxt.trimDerivedTables").booleanValue();
/*  54:    */   private volatile int lastTrimHeight;
/*  55: 64 */   private final Listeners<Block, BlockchainProcessor.Event> blockListeners = new Listeners();
/*  56:    */   private volatile Peer lastBlockchainFeeder;
/*  57:    */   private volatile int lastBlockchainFeederHeight;
/*  58: 67 */   private volatile boolean getMoreBlocks = true;
/*  59:    */   private volatile boolean isScanning;
/*  60: 70 */   private volatile boolean forceScan = Nxt.getBooleanProperty("nxt.forceScan").booleanValue();
/*  61: 71 */   private volatile boolean validateAtScan = Nxt.getBooleanProperty("nxt.forceValidate").booleanValue();
/*  62: 73 */   private final Runnable getMoreBlocksThread = new Runnable()
/*  63:    */   {
/*  64:    */     private final JSONStreamAware getCumulativeDifficultyRequest;
/*  65:    */     private boolean peerHasMore;
/*  66:    */     
/*  67:    */     /* Error */
/*  68:    */     public void run()
/*  69:    */     {
/*  70:    */       // Byte code:
/*  71:    */       //   0: aload_0
/*  72:    */       //   1: getfield 1	nxt/BlockchainProcessorImpl$1:this$0	Lnxt/BlockchainProcessorImpl;
/*  73:    */       //   4: invokestatic 10	nxt/BlockchainProcessorImpl:access$000	(Lnxt/BlockchainProcessorImpl;)Z
/*  74:    */       //   7: ifne +4 -> 11
/*  75:    */       //   10: return
/*  76:    */       //   11: aload_0
/*  77:    */       //   12: iconst_1
/*  78:    */       //   13: putfield 11	nxt/BlockchainProcessorImpl$1:peerHasMore	Z
/*  79:    */       //   16: getstatic 12	nxt/peer/Peer$State:CONNECTED	Lnxt/peer/Peer$State;
/*  80:    */       //   19: iconst_1
/*  81:    */       //   20: invokestatic 13	nxt/peer/Peers:getAnyPeer	(Lnxt/peer/Peer$State;Z)Lnxt/peer/Peer;
/*  82:    */       //   23: astore_1
/*  83:    */       //   24: aload_1
/*  84:    */       //   25: ifnonnull +4 -> 29
/*  85:    */       //   28: return
/*  86:    */       //   29: aload_1
/*  87:    */       //   30: aload_0
/*  88:    */       //   31: getfield 9	nxt/BlockchainProcessorImpl$1:getCumulativeDifficultyRequest	Lorg/json/simple/JSONStreamAware;
/*  89:    */       //   34: invokeinterface 14 2 0
/*  90:    */       //   39: astore_2
/*  91:    */       //   40: aload_2
/*  92:    */       //   41: ifnonnull +4 -> 45
/*  93:    */       //   44: return
/*  94:    */       //   45: aload_0
/*  95:    */       //   46: getfield 1	nxt/BlockchainProcessorImpl$1:this$0	Lnxt/BlockchainProcessorImpl;
/*  96:    */       //   49: invokestatic 15	nxt/BlockchainProcessorImpl:access$100	(Lnxt/BlockchainProcessorImpl;)Lnxt/BlockchainImpl;
/*  97:    */       //   52: invokevirtual 16	nxt/BlockchainImpl:getLastBlock	()Lnxt/BlockImpl;
/*  98:    */       //   55: invokevirtual 17	nxt/BlockImpl:getCumulativeDifficulty	()Ljava/math/BigInteger;
/*  99:    */       //   58: astore_3
/* 100:    */       //   59: aload_2
/* 101:    */       //   60: ldc 18
/* 102:    */       //   62: invokevirtual 19	org/json/simple/JSONObject:get	(Ljava/lang/Object;)Ljava/lang/Object;
/* 103:    */       //   65: checkcast 20	java/lang/String
/* 104:    */       //   68: astore 4
/* 105:    */       //   70: aload 4
/* 106:    */       //   72: ifnonnull +4 -> 76
/* 107:    */       //   75: return
/* 108:    */       //   76: new 21	java/math/BigInteger
/* 109:    */       //   79: dup
/* 110:    */       //   80: aload 4
/* 111:    */       //   82: invokespecial 22	java/math/BigInteger:<init>	(Ljava/lang/String;)V
/* 112:    */       //   85: astore 5
/* 113:    */       //   87: aload 5
/* 114:    */       //   89: aload_3
/* 115:    */       //   90: invokevirtual 23	java/math/BigInteger:compareTo	(Ljava/math/BigInteger;)I
/* 116:    */       //   93: ifge +4 -> 97
/* 117:    */       //   96: return
/* 118:    */       //   97: aload_2
/* 119:    */       //   98: ldc 24
/* 120:    */       //   100: invokevirtual 19	org/json/simple/JSONObject:get	(Ljava/lang/Object;)Ljava/lang/Object;
/* 121:    */       //   103: ifnull +32 -> 135
/* 122:    */       //   106: aload_0
/* 123:    */       //   107: getfield 1	nxt/BlockchainProcessorImpl$1:this$0	Lnxt/BlockchainProcessorImpl;
/* 124:    */       //   110: aload_1
/* 125:    */       //   111: invokestatic 25	nxt/BlockchainProcessorImpl:access$202	(Lnxt/BlockchainProcessorImpl;Lnxt/peer/Peer;)Lnxt/peer/Peer;
/* 126:    */       //   114: pop
/* 127:    */       //   115: aload_0
/* 128:    */       //   116: getfield 1	nxt/BlockchainProcessorImpl$1:this$0	Lnxt/BlockchainProcessorImpl;
/* 129:    */       //   119: aload_2
/* 130:    */       //   120: ldc 24
/* 131:    */       //   122: invokevirtual 19	org/json/simple/JSONObject:get	(Ljava/lang/Object;)Ljava/lang/Object;
/* 132:    */       //   125: checkcast 26	java/lang/Long
/* 133:    */       //   128: invokevirtual 27	java/lang/Long:intValue	()I
/* 134:    */       //   131: invokestatic 28	nxt/BlockchainProcessorImpl:access$302	(Lnxt/BlockchainProcessorImpl;I)I
/* 135:    */       //   134: pop
/* 136:    */       //   135: aload 5
/* 137:    */       //   137: aload_3
/* 138:    */       //   138: invokevirtual 29	java/math/BigInteger:equals	(Ljava/lang/Object;)Z
/* 139:    */       //   141: ifeq +4 -> 145
/* 140:    */       //   144: return
/* 141:    */       //   145: ldc2_w 30
/* 142:    */       //   148: lstore 6
/* 143:    */       //   150: aload_0
/* 144:    */       //   151: getfield 1	nxt/BlockchainProcessorImpl$1:this$0	Lnxt/BlockchainProcessorImpl;
/* 145:    */       //   154: invokestatic 15	nxt/BlockchainProcessorImpl:access$100	(Lnxt/BlockchainProcessorImpl;)Lnxt/BlockchainImpl;
/* 146:    */       //   157: invokevirtual 16	nxt/BlockchainImpl:getLastBlock	()Lnxt/BlockImpl;
/* 147:    */       //   160: invokevirtual 32	nxt/BlockImpl:getId	()J
/* 148:    */       //   163: ldc2_w 30
/* 149:    */       //   166: lcmp
/* 150:    */       //   167: ifeq +10 -> 177
/* 151:    */       //   170: aload_0
/* 152:    */       //   171: aload_1
/* 153:    */       //   172: invokespecial 33	nxt/BlockchainProcessorImpl$1:getCommonMilestoneBlockId	(Lnxt/peer/Peer;)J
/* 154:    */       //   175: lstore 6
/* 155:    */       //   177: lload 6
/* 156:    */       //   179: lconst_0
/* 157:    */       //   180: lcmp
/* 158:    */       //   181: ifeq +10 -> 191
/* 159:    */       //   184: aload_0
/* 160:    */       //   185: getfield 11	nxt/BlockchainProcessorImpl$1:peerHasMore	Z
/* 161:    */       //   188: ifne +4 -> 192
/* 162:    */       //   191: return
/* 163:    */       //   192: aload_0
/* 164:    */       //   193: aload_1
/* 165:    */       //   194: lload 6
/* 166:    */       //   196: invokespecial 34	nxt/BlockchainProcessorImpl$1:getCommonBlockId	(Lnxt/peer/Peer;J)J
/* 167:    */       //   199: lstore 6
/* 168:    */       //   201: lload 6
/* 169:    */       //   203: lconst_0
/* 170:    */       //   204: lcmp
/* 171:    */       //   205: ifeq +10 -> 215
/* 172:    */       //   208: aload_0
/* 173:    */       //   209: getfield 11	nxt/BlockchainProcessorImpl$1:peerHasMore	Z
/* 174:    */       //   212: ifne +4 -> 216
/* 175:    */       //   215: return
/* 176:    */       //   216: lload 6
/* 177:    */       //   218: invokestatic 35	nxt/BlockDb:findBlock	(J)Lnxt/BlockImpl;
/* 178:    */       //   221: astore 8
/* 179:    */       //   223: aload 8
/* 180:    */       //   225: ifnull +27 -> 252
/* 181:    */       //   228: aload_0
/* 182:    */       //   229: getfield 1	nxt/BlockchainProcessorImpl$1:this$0	Lnxt/BlockchainProcessorImpl;
/* 183:    */       //   232: invokestatic 15	nxt/BlockchainProcessorImpl:access$100	(Lnxt/BlockchainProcessorImpl;)Lnxt/BlockchainImpl;
/* 184:    */       //   235: invokevirtual 36	nxt/BlockchainImpl:getHeight	()I
/* 185:    */       //   238: aload 8
/* 186:    */       //   240: invokeinterface 37 1 0
/* 187:    */       //   245: isub
/* 188:    */       //   246: sipush 720
/* 189:    */       //   249: if_icmplt +4 -> 253
/* 190:    */       //   252: return
/* 191:    */       //   253: lload 6
/* 192:    */       //   255: lstore 9
/* 193:    */       //   257: new 38	java/util/ArrayList
/* 194:    */       //   260: dup
/* 195:    */       //   261: invokespecial 39	java/util/ArrayList:<init>	()V
/* 196:    */       //   264: astore 11
/* 197:    */       //   266: iconst_1
/* 198:    */       //   267: istore 12
/* 199:    */       //   269: iconst_0
/* 200:    */       //   270: istore 13
/* 201:    */       //   272: aload 11
/* 202:    */       //   274: invokeinterface 40 1 0
/* 203:    */       //   279: sipush 1440
/* 204:    */       //   282: if_icmpge +274 -> 556
/* 205:    */       //   285: iload 13
/* 206:    */       //   287: iinc 13 1
/* 207:    */       //   290: bipush 10
/* 208:    */       //   292: if_icmpge +264 -> 556
/* 209:    */       //   295: aload_0
/* 210:    */       //   296: aload_1
/* 211:    */       //   297: lload 9
/* 212:    */       //   299: invokespecial 41	nxt/BlockchainProcessorImpl$1:getNextBlocks	(Lnxt/peer/Peer;J)Lorg/json/simple/JSONArray;
/* 213:    */       //   302: astore 14
/* 214:    */       //   304: aload 14
/* 215:    */       //   306: ifnull +250 -> 556
/* 216:    */       //   309: aload 14
/* 217:    */       //   311: invokevirtual 42	org/json/simple/JSONArray:size	()I
/* 218:    */       //   314: ifne +6 -> 320
/* 219:    */       //   317: goto +239 -> 556
/* 220:    */       //   320: aload_0
/* 221:    */       //   321: getfield 1	nxt/BlockchainProcessorImpl$1:this$0	Lnxt/BlockchainProcessorImpl;
/* 222:    */       //   324: invokestatic 15	nxt/BlockchainProcessorImpl:access$100	(Lnxt/BlockchainProcessorImpl;)Lnxt/BlockchainImpl;
/* 223:    */       //   327: dup
/* 224:    */       //   328: astore 15
/* 225:    */       //   330: monitorenter
/* 226:    */       //   331: aload 14
/* 227:    */       //   333: invokevirtual 43	org/json/simple/JSONArray:iterator	()Ljava/util/Iterator;
/* 228:    */       //   336: astore 16
/* 229:    */       //   338: aload 16
/* 230:    */       //   340: invokeinterface 44 1 0
/* 231:    */       //   345: ifeq +194 -> 539
/* 232:    */       //   348: aload 16
/* 233:    */       //   350: invokeinterface 45 1 0
/* 234:    */       //   355: astore 17
/* 235:    */       //   357: aload 17
/* 236:    */       //   359: checkcast 3	org/json/simple/JSONObject
/* 237:    */       //   362: astore 18
/* 238:    */       //   364: aload 18
/* 239:    */       //   366: invokestatic 46	nxt/BlockImpl:parseBlock	(Lorg/json/simple/JSONObject;)Lnxt/BlockImpl;
/* 240:    */       //   369: astore 19
/* 241:    */       //   371: goto +89 -> 460
/* 242:    */       //   374: astore 20
/* 243:    */       //   376: new 48	java/lang/StringBuilder
/* 244:    */       //   379: dup
/* 245:    */       //   380: invokespecial 49	java/lang/StringBuilder:<init>	()V
/* 246:    */       //   383: ldc 50
/* 247:    */       //   385: invokevirtual 51	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 248:    */       //   388: aload 20
/* 249:    */       //   390: invokevirtual 52	nxt/NxtException$NotCurrentlyValidException:toString	()Ljava/lang/String;
/* 250:    */       //   393: invokevirtual 51	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 251:    */       //   396: ldc 53
/* 252:    */       //   398: invokevirtual 51	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 253:    */       //   401: invokevirtual 54	java/lang/StringBuilder:toString	()Ljava/lang/String;
/* 254:    */       //   404: aload 20
/* 255:    */       //   406: invokestatic 55	nxt/util/Logger:logDebugMessage	(Ljava/lang/String;Ljava/lang/Exception;)V
/* 256:    */       //   409: iconst_0
/* 257:    */       //   410: istore 12
/* 258:    */       //   412: aload 15
/* 259:    */       //   414: monitorexit
/* 260:    */       //   415: goto +141 -> 556
/* 261:    */       //   418: astore 20
/* 262:    */       //   420: new 48	java/lang/StringBuilder
/* 263:    */       //   423: dup
/* 264:    */       //   424: invokespecial 49	java/lang/StringBuilder:<init>	()V
/* 265:    */       //   427: ldc 58
/* 266:    */       //   429: invokevirtual 51	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 267:    */       //   432: aload 20
/* 268:    */       //   434: invokevirtual 59	java/lang/Exception:toString	()Ljava/lang/String;
/* 269:    */       //   437: invokevirtual 51	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 270:    */       //   440: invokevirtual 54	java/lang/StringBuilder:toString	()Ljava/lang/String;
/* 271:    */       //   443: aload 20
/* 272:    */       //   445: invokestatic 55	nxt/util/Logger:logDebugMessage	(Ljava/lang/String;Ljava/lang/Exception;)V
/* 273:    */       //   448: aload_1
/* 274:    */       //   449: aload 20
/* 275:    */       //   451: invokeinterface 60 2 0
/* 276:    */       //   456: aload 15
/* 277:    */       //   458: monitorexit
/* 278:    */       //   459: return
/* 279:    */       //   460: aload 19
/* 280:    */       //   462: invokevirtual 32	nxt/BlockImpl:getId	()J
/* 281:    */       //   465: lstore 9
/* 282:    */       //   467: aload_0
/* 283:    */       //   468: getfield 1	nxt/BlockchainProcessorImpl$1:this$0	Lnxt/BlockchainProcessorImpl;
/* 284:    */       //   471: invokestatic 15	nxt/BlockchainProcessorImpl:access$100	(Lnxt/BlockchainProcessorImpl;)Lnxt/BlockchainImpl;
/* 285:    */       //   474: invokevirtual 16	nxt/BlockchainImpl:getLastBlock	()Lnxt/BlockImpl;
/* 286:    */       //   477: invokevirtual 32	nxt/BlockImpl:getId	()J
/* 287:    */       //   480: aload 19
/* 288:    */       //   482: invokevirtual 61	nxt/BlockImpl:getPreviousBlockId	()J
/* 289:    */       //   485: lcmp
/* 290:    */       //   486: ifne +29 -> 515
/* 291:    */       //   489: aload_0
/* 292:    */       //   490: getfield 1	nxt/BlockchainProcessorImpl$1:this$0	Lnxt/BlockchainProcessorImpl;
/* 293:    */       //   493: aload 19
/* 294:    */       //   495: invokestatic 62	nxt/BlockchainProcessorImpl:access$400	(Lnxt/BlockchainProcessorImpl;Lnxt/BlockImpl;)V
/* 295:    */       //   498: goto +38 -> 536
/* 296:    */       //   501: astore 20
/* 297:    */       //   503: aload_1
/* 298:    */       //   504: aload 20
/* 299:    */       //   506: invokeinterface 60 2 0
/* 300:    */       //   511: aload 15
/* 301:    */       //   513: monitorexit
/* 302:    */       //   514: return
/* 303:    */       //   515: aload 19
/* 304:    */       //   517: invokevirtual 32	nxt/BlockImpl:getId	()J
/* 305:    */       //   520: invokestatic 64	nxt/BlockDb:hasBlock	(J)Z
/* 306:    */       //   523: ifne +13 -> 536
/* 307:    */       //   526: aload 11
/* 308:    */       //   528: aload 19
/* 309:    */       //   530: invokeinterface 65 2 0
/* 310:    */       //   535: pop
/* 311:    */       //   536: goto -198 -> 338
/* 312:    */       //   539: aload 15
/* 313:    */       //   541: monitorexit
/* 314:    */       //   542: goto +11 -> 553
/* 315:    */       //   545: astore 21
/* 316:    */       //   547: aload 15
/* 317:    */       //   549: monitorexit
/* 318:    */       //   550: aload 21
/* 319:    */       //   552: athrow
/* 320:    */       //   553: goto -281 -> 272
/* 321:    */       //   556: aload 11
/* 322:    */       //   558: invokeinterface 40 1 0
/* 323:    */       //   563: ifle +6 -> 569
/* 324:    */       //   566: iconst_0
/* 325:    */       //   567: istore 12
/* 326:    */       //   569: iload 12
/* 327:    */       //   571: ifne +36 -> 607
/* 328:    */       //   574: aload_0
/* 329:    */       //   575: getfield 1	nxt/BlockchainProcessorImpl$1:this$0	Lnxt/BlockchainProcessorImpl;
/* 330:    */       //   578: invokestatic 15	nxt/BlockchainProcessorImpl:access$100	(Lnxt/BlockchainProcessorImpl;)Lnxt/BlockchainImpl;
/* 331:    */       //   581: invokevirtual 36	nxt/BlockchainImpl:getHeight	()I
/* 332:    */       //   584: aload 8
/* 333:    */       //   586: invokeinterface 37 1 0
/* 334:    */       //   591: isub
/* 335:    */       //   592: sipush 720
/* 336:    */       //   595: if_icmpge +12 -> 607
/* 337:    */       //   598: aload_0
/* 338:    */       //   599: aload_1
/* 339:    */       //   600: aload 11
/* 340:    */       //   602: aload 8
/* 341:    */       //   604: invokespecial 66	nxt/BlockchainProcessorImpl$1:processFork	(Lnxt/peer/Peer;Ljava/util/List;Lnxt/Block;)V
/* 342:    */       //   607: goto +39 -> 646
/* 343:    */       //   610: astore_1
/* 344:    */       //   611: new 48	java/lang/StringBuilder
/* 345:    */       //   614: dup
/* 346:    */       //   615: invokespecial 49	java/lang/StringBuilder:<init>	()V
/* 347:    */       //   618: ldc 68
/* 348:    */       //   620: invokevirtual 51	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 349:    */       //   623: aload_1
/* 350:    */       //   624: invokevirtual 69	nxt/NxtException$StopException:getMessage	()Ljava/lang/String;
/* 351:    */       //   627: invokevirtual 51	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 352:    */       //   630: invokevirtual 54	java/lang/StringBuilder:toString	()Ljava/lang/String;
/* 353:    */       //   633: invokestatic 70	nxt/util/Logger:logMessage	(Ljava/lang/String;)V
/* 354:    */       //   636: goto +10 -> 646
/* 355:    */       //   639: astore_1
/* 356:    */       //   640: ldc 72
/* 357:    */       //   642: aload_1
/* 358:    */       //   643: invokestatic 55	nxt/util/Logger:logDebugMessage	(Ljava/lang/String;Ljava/lang/Exception;)V
/* 359:    */       //   646: goto +37 -> 683
/* 360:    */       //   649: astore_1
/* 361:    */       //   650: new 48	java/lang/StringBuilder
/* 362:    */       //   653: dup
/* 363:    */       //   654: invokespecial 49	java/lang/StringBuilder:<init>	()V
/* 364:    */       //   657: ldc 74
/* 365:    */       //   659: invokevirtual 51	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 366:    */       //   662: aload_1
/* 367:    */       //   663: invokevirtual 75	java/lang/Throwable:toString	()Ljava/lang/String;
/* 368:    */       //   666: invokevirtual 51	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 369:    */       //   669: invokevirtual 54	java/lang/StringBuilder:toString	()Ljava/lang/String;
/* 370:    */       //   672: invokestatic 70	nxt/util/Logger:logMessage	(Ljava/lang/String;)V
/* 371:    */       //   675: aload_1
/* 372:    */       //   676: invokevirtual 76	java/lang/Throwable:printStackTrace	()V
/* 373:    */       //   679: iconst_1
/* 374:    */       //   680: invokestatic 77	java/lang/System:exit	(I)V
/* 375:    */       //   683: return
/* 376:    */       // Line number table:
/* 377:    */       //   Java source line #90	-> byte code offset #0
/* 378:    */       //   Java source line #91	-> byte code offset #10
/* 379:    */       //   Java source line #93	-> byte code offset #11
/* 380:    */       //   Java source line #94	-> byte code offset #16
/* 381:    */       //   Java source line #95	-> byte code offset #24
/* 382:    */       //   Java source line #96	-> byte code offset #28
/* 383:    */       //   Java source line #98	-> byte code offset #29
/* 384:    */       //   Java source line #99	-> byte code offset #40
/* 385:    */       //   Java source line #100	-> byte code offset #44
/* 386:    */       //   Java source line #102	-> byte code offset #45
/* 387:    */       //   Java source line #103	-> byte code offset #59
/* 388:    */       //   Java source line #104	-> byte code offset #70
/* 389:    */       //   Java source line #105	-> byte code offset #75
/* 390:    */       //   Java source line #107	-> byte code offset #76
/* 391:    */       //   Java source line #108	-> byte code offset #87
/* 392:    */       //   Java source line #109	-> byte code offset #96
/* 393:    */       //   Java source line #111	-> byte code offset #97
/* 394:    */       //   Java source line #112	-> byte code offset #106
/* 395:    */       //   Java source line #113	-> byte code offset #115
/* 396:    */       //   Java source line #115	-> byte code offset #135
/* 397:    */       //   Java source line #116	-> byte code offset #144
/* 398:    */       //   Java source line #119	-> byte code offset #145
/* 399:    */       //   Java source line #121	-> byte code offset #150
/* 400:    */       //   Java source line #122	-> byte code offset #170
/* 401:    */       //   Java source line #124	-> byte code offset #177
/* 402:    */       //   Java source line #125	-> byte code offset #191
/* 403:    */       //   Java source line #128	-> byte code offset #192
/* 404:    */       //   Java source line #129	-> byte code offset #201
/* 405:    */       //   Java source line #130	-> byte code offset #215
/* 406:    */       //   Java source line #133	-> byte code offset #216
/* 407:    */       //   Java source line #134	-> byte code offset #223
/* 408:    */       //   Java source line #135	-> byte code offset #252
/* 409:    */       //   Java source line #138	-> byte code offset #253
/* 410:    */       //   Java source line #139	-> byte code offset #257
/* 411:    */       //   Java source line #141	-> byte code offset #266
/* 412:    */       //   Java source line #142	-> byte code offset #269
/* 413:    */       //   Java source line #144	-> byte code offset #272
/* 414:    */       //   Java source line #145	-> byte code offset #295
/* 415:    */       //   Java source line #146	-> byte code offset #304
/* 416:    */       //   Java source line #147	-> byte code offset #317
/* 417:    */       //   Java source line #150	-> byte code offset #320
/* 418:    */       //   Java source line #152	-> byte code offset #331
/* 419:    */       //   Java source line #153	-> byte code offset #357
/* 420:    */       //   Java source line #156	-> byte code offset #364
/* 421:    */       //   Java source line #166	-> byte code offset #371
/* 422:    */       //   Java source line #157	-> byte code offset #374
/* 423:    */       //   Java source line #158	-> byte code offset #376
/* 424:    */       //   Java source line #160	-> byte code offset #409
/* 425:    */       //   Java source line #161	-> byte code offset #412
/* 426:    */       //   Java source line #162	-> byte code offset #418
/* 427:    */       //   Java source line #163	-> byte code offset #420
/* 428:    */       //   Java source line #164	-> byte code offset #448
/* 429:    */       //   Java source line #165	-> byte code offset #456
/* 430:    */       //   Java source line #167	-> byte code offset #460
/* 431:    */       //   Java source line #169	-> byte code offset #467
/* 432:    */       //   Java source line #171	-> byte code offset #489
/* 433:    */       //   Java source line #175	-> byte code offset #498
/* 434:    */       //   Java source line #172	-> byte code offset #501
/* 435:    */       //   Java source line #173	-> byte code offset #503
/* 436:    */       //   Java source line #174	-> byte code offset #511
/* 437:    */       //   Java source line #176	-> byte code offset #515
/* 438:    */       //   Java source line #177	-> byte code offset #526
/* 439:    */       //   Java source line #180	-> byte code offset #536
/* 440:    */       //   Java source line #182	-> byte code offset #539
/* 441:    */       //   Java source line #184	-> byte code offset #553
/* 442:    */       //   Java source line #186	-> byte code offset #556
/* 443:    */       //   Java source line #187	-> byte code offset #566
/* 444:    */       //   Java source line #190	-> byte code offset #569
/* 445:    */       //   Java source line #191	-> byte code offset #598
/* 446:    */       //   Java source line #198	-> byte code offset #607
/* 447:    */       //   Java source line #194	-> byte code offset #610
/* 448:    */       //   Java source line #195	-> byte code offset #611
/* 449:    */       //   Java source line #198	-> byte code offset #636
/* 450:    */       //   Java source line #196	-> byte code offset #639
/* 451:    */       //   Java source line #197	-> byte code offset #640
/* 452:    */       //   Java source line #203	-> byte code offset #646
/* 453:    */       //   Java source line #199	-> byte code offset #649
/* 454:    */       //   Java source line #200	-> byte code offset #650
/* 455:    */       //   Java source line #201	-> byte code offset #675
/* 456:    */       //   Java source line #202	-> byte code offset #679
/* 457:    */       //   Java source line #205	-> byte code offset #683
/* 458:    */       // Local variable table:
/* 459:    */       //   start	length	slot	name	signature
/* 460:    */       //   0	684	0	this	1
/* 461:    */       //   23	577	1	localPeer	Peer
/* 462:    */       //   610	14	1	localStopException	NxtException.StopException
/* 463:    */       //   639	4	1	localException	Exception
/* 464:    */       //   649	27	1	localThrowable	Throwable
/* 465:    */       //   39	81	2	localJSONObject1	JSONObject
/* 466:    */       //   58	80	3	localBigInteger1	BigInteger
/* 467:    */       //   68	13	4	str	String
/* 468:    */       //   85	51	5	localBigInteger2	BigInteger
/* 469:    */       //   148	106	6	l1	long
/* 470:    */       //   221	382	8	localBlockImpl1	BlockImpl
/* 471:    */       //   255	211	9	l2	long
/* 472:    */       //   264	337	11	localArrayList	ArrayList
/* 473:    */       //   267	303	12	i	int
/* 474:    */       //   270	16	13	j	int
/* 475:    */       //   302	30	14	localJSONArray	JSONArray
/* 476:    */       //   328	220	15	Ljava/lang/Object;	Object
/* 477:    */       //   336	13	16	localIterator	Iterator
/* 478:    */       //   355	3	17	localObject1	Object
/* 479:    */       //   362	3	18	localJSONObject2	JSONObject
/* 480:    */       //   369	160	19	localBlockImpl2	BlockImpl
/* 481:    */       //   374	31	20	localNotCurrentlyValidException	NxtException.NotCurrentlyValidException
/* 482:    */       //   418	32	20	localRuntimeException	RuntimeException
/* 483:    */       //   501	4	20	localBlockNotAcceptedException	BlockchainProcessor.BlockNotAcceptedException
/* 484:    */       //   545	6	21	localObject2	Object
/* 485:    */       // Exception table:
/* 486:    */       //   from	to	target	type
/* 487:    */       //   364	371	374	nxt/NxtException$NotCurrentlyValidException
/* 488:    */       //   364	371	418	java/lang/RuntimeException
/* 489:    */       //   364	371	418	nxt/NxtException$ValidationException
/* 490:    */       //   489	498	501	nxt/BlockchainProcessor$BlockNotAcceptedException
/* 491:    */       //   331	415	545	finally
/* 492:    */       //   418	459	545	finally
/* 493:    */       //   460	514	545	finally
/* 494:    */       //   515	542	545	finally
/* 495:    */       //   545	550	545	finally
/* 496:    */       //   0	10	610	nxt/NxtException$StopException
/* 497:    */       //   11	28	610	nxt/NxtException$StopException
/* 498:    */       //   29	44	610	nxt/NxtException$StopException
/* 499:    */       //   45	75	610	nxt/NxtException$StopException
/* 500:    */       //   76	96	610	nxt/NxtException$StopException
/* 501:    */       //   97	144	610	nxt/NxtException$StopException
/* 502:    */       //   145	191	610	nxt/NxtException$StopException
/* 503:    */       //   192	215	610	nxt/NxtException$StopException
/* 504:    */       //   216	252	610	nxt/NxtException$StopException
/* 505:    */       //   253	459	610	nxt/NxtException$StopException
/* 506:    */       //   460	514	610	nxt/NxtException$StopException
/* 507:    */       //   515	607	610	nxt/NxtException$StopException
/* 508:    */       //   0	10	639	java/lang/Exception
/* 509:    */       //   11	28	639	java/lang/Exception
/* 510:    */       //   29	44	639	java/lang/Exception
/* 511:    */       //   45	75	639	java/lang/Exception
/* 512:    */       //   76	96	639	java/lang/Exception
/* 513:    */       //   97	144	639	java/lang/Exception
/* 514:    */       //   145	191	639	java/lang/Exception
/* 515:    */       //   192	215	639	java/lang/Exception
/* 516:    */       //   216	252	639	java/lang/Exception
/* 517:    */       //   253	459	639	java/lang/Exception
/* 518:    */       //   460	514	639	java/lang/Exception
/* 519:    */       //   515	607	639	java/lang/Exception
/* 520:    */       //   0	10	649	java/lang/Throwable
/* 521:    */       //   11	28	649	java/lang/Throwable
/* 522:    */       //   29	44	649	java/lang/Throwable
/* 523:    */       //   45	75	649	java/lang/Throwable
/* 524:    */       //   76	96	649	java/lang/Throwable
/* 525:    */       //   97	144	649	java/lang/Throwable
/* 526:    */       //   145	191	649	java/lang/Throwable
/* 527:    */       //   192	215	649	java/lang/Throwable
/* 528:    */       //   216	252	649	java/lang/Throwable
/* 529:    */       //   253	459	649	java/lang/Throwable
/* 530:    */       //   460	514	649	java/lang/Throwable
/* 531:    */       //   515	646	649	java/lang/Throwable
/* 532:    */     }
/* 533:    */     
/* 534:    */     private long getCommonMilestoneBlockId(Peer paramAnonymousPeer)
/* 535:    */     {
/* 536:209 */       Object localObject1 = null;
/* 537:    */       JSONArray localJSONArray;
/* 538:    */       for (;;)
/* 539:    */       {
/* 540:212 */         JSONObject localJSONObject1 = new JSONObject();
/* 541:213 */         localJSONObject1.put("requestType", "getMilestoneBlockIds");
/* 542:214 */         if (localObject1 == null) {
/* 543:215 */           localJSONObject1.put("lastBlockId", BlockchainProcessorImpl.this.blockchain.getLastBlock().getStringId());
/* 544:    */         } else {
/* 545:217 */           localJSONObject1.put("lastMilestoneBlockId", localObject1);
/* 546:    */         }
/* 547:220 */         JSONObject localJSONObject2 = paramAnonymousPeer.send(JSON.prepareRequest(localJSONObject1));
/* 548:221 */         if (localJSONObject2 == null) {
/* 549:222 */           return 0L;
/* 550:    */         }
/* 551:224 */         localJSONArray = (JSONArray)localJSONObject2.get("milestoneBlockIds");
/* 552:225 */         if (localJSONArray == null) {
/* 553:226 */           return 0L;
/* 554:    */         }
/* 555:228 */         if (localJSONArray.isEmpty()) {
/* 556:229 */           return 3444294670862540038L;
/* 557:    */         }
/* 558:232 */         if (localJSONArray.size() > 20)
/* 559:    */         {
/* 560:233 */           Logger.logDebugMessage("Obsolete or rogue peer " + paramAnonymousPeer.getPeerAddress() + " sends too many milestoneBlockIds, blacklisting");
/* 561:234 */           paramAnonymousPeer.blacklist();
/* 562:235 */           return 0L;
/* 563:    */         }
/* 564:237 */         if (Boolean.TRUE.equals(localJSONObject2.get("last"))) {
/* 565:238 */           this.peerHasMore = false;
/* 566:    */         }
/* 567:240 */         for (Object localObject2 : localJSONArray)
/* 568:    */         {
/* 569:241 */           long l = Convert.parseUnsignedLong((String)localObject2);
/* 570:242 */           if (BlockDb.hasBlock(l))
/* 571:    */           {
/* 572:243 */             if ((localObject1 == null) && (localJSONArray.size() > 1)) {
/* 573:244 */               this.peerHasMore = false;
/* 574:    */             }
/* 575:246 */             return l;
/* 576:    */           }
/* 577:248 */           localObject1 = (String)localObject2;
/* 578:    */         }
/* 579:    */       }
/* 580:    */     }
/* 581:    */     
/* 582:    */     private long getCommonBlockId(Peer paramAnonymousPeer, long paramAnonymousLong)
/* 583:    */     {
/* 584:    */       for (;;)
/* 585:    */       {
/* 586:257 */         JSONObject localJSONObject1 = new JSONObject();
/* 587:258 */         localJSONObject1.put("requestType", "getNextBlockIds");
/* 588:259 */         localJSONObject1.put("blockId", Convert.toUnsignedLong(paramAnonymousLong));
/* 589:260 */         JSONObject localJSONObject2 = paramAnonymousPeer.send(JSON.prepareRequest(localJSONObject1));
/* 590:261 */         if (localJSONObject2 == null) {
/* 591:262 */           return 0L;
/* 592:    */         }
/* 593:264 */         JSONArray localJSONArray = (JSONArray)localJSONObject2.get("nextBlockIds");
/* 594:265 */         if ((localJSONArray == null) || (localJSONArray.size() == 0)) {
/* 595:266 */           return 0L;
/* 596:    */         }
/* 597:269 */         if (localJSONArray.size() > 1440)
/* 598:    */         {
/* 599:270 */           Logger.logDebugMessage("Obsolete or rogue peer " + paramAnonymousPeer.getPeerAddress() + " sends too many nextBlockIds, blacklisting");
/* 600:271 */           paramAnonymousPeer.blacklist();
/* 601:272 */           return 0L;
/* 602:    */         }
/* 603:275 */         for (Object localObject : localJSONArray)
/* 604:    */         {
/* 605:276 */           long l = Convert.parseUnsignedLong((String)localObject);
/* 606:277 */           if (!BlockDb.hasBlock(l)) {
/* 607:278 */             return paramAnonymousLong;
/* 608:    */           }
/* 609:280 */           paramAnonymousLong = l;
/* 610:    */         }
/* 611:    */       }
/* 612:    */     }
/* 613:    */     
/* 614:    */     private JSONArray getNextBlocks(Peer paramAnonymousPeer, long paramAnonymousLong)
/* 615:    */     {
/* 616:288 */       JSONObject localJSONObject1 = new JSONObject();
/* 617:289 */       localJSONObject1.put("requestType", "getNextBlocks");
/* 618:290 */       localJSONObject1.put("blockId", Convert.toUnsignedLong(paramAnonymousLong));
/* 619:291 */       JSONObject localJSONObject2 = paramAnonymousPeer.send(JSON.prepareRequest(localJSONObject1));
/* 620:292 */       if (localJSONObject2 == null) {
/* 621:293 */         return null;
/* 622:    */       }
/* 623:296 */       JSONArray localJSONArray = (JSONArray)localJSONObject2.get("nextBlocks");
/* 624:297 */       if (localJSONArray == null) {
/* 625:298 */         return null;
/* 626:    */       }
/* 627:301 */       if (localJSONArray.size() > 1440)
/* 628:    */       {
/* 629:302 */         Logger.logDebugMessage("Obsolete or rogue peer " + paramAnonymousPeer.getPeerAddress() + " sends too many nextBlocks, blacklisting");
/* 630:303 */         paramAnonymousPeer.blacklist();
/* 631:304 */         return null;
/* 632:    */       }
/* 633:307 */       return localJSONArray;
/* 634:    */     }
/* 635:    */     
/* 636:    */     private void processFork(Peer paramAnonymousPeer, List<BlockImpl> paramAnonymousList, Block paramAnonymousBlock)
/* 637:    */     {
/* 638:    */       Object localObject2;
/* 639:    */       Iterator localIterator;
/* 640:313 */       synchronized (BlockchainProcessorImpl.this.blockchain)
/* 641:    */       {
/* 642:314 */         BigInteger localBigInteger = BlockchainProcessorImpl.this.blockchain.getLastBlock().getCumulativeDifficulty();
/* 643:    */         
/* 644:316 */         List localList = BlockchainProcessorImpl.this.popOffTo(paramAnonymousBlock);
/* 645:    */         
/* 646:318 */         int i = 0;
/* 647:    */         Object localObject1;
/* 648:319 */         if (BlockchainProcessorImpl.this.blockchain.getLastBlock().getId() == paramAnonymousBlock.getId()) {
/* 649:320 */           for (localObject1 = paramAnonymousList.iterator(); ((Iterator)localObject1).hasNext();)
/* 650:    */           {
/* 651:320 */             localObject2 = (BlockImpl)((Iterator)localObject1).next();
/* 652:321 */             if (BlockchainProcessorImpl.this.blockchain.getLastBlock().getId() == ((BlockImpl)localObject2).getPreviousBlockId()) {
/* 653:    */               try
/* 654:    */               {
/* 655:323 */                 BlockchainProcessorImpl.this.pushBlock((BlockImpl)localObject2);
/* 656:324 */                 i++;
/* 657:    */               }
/* 658:    */               catch (BlockchainProcessor.BlockNotAcceptedException localBlockNotAcceptedException1)
/* 659:    */               {
/* 660:326 */                 paramAnonymousPeer.blacklist(localBlockNotAcceptedException1);
/* 661:327 */                 break;
/* 662:    */               }
/* 663:    */             }
/* 664:    */           }
/* 665:    */         }
/* 666:333 */         if ((i > 0) && (BlockchainProcessorImpl.this.blockchain.getLastBlock().getCumulativeDifficulty().compareTo(localBigInteger) < 0))
/* 667:    */         {
/* 668:334 */           Logger.logDebugMessage("Pop off caused by peer " + paramAnonymousPeer.getPeerAddress() + ", blacklisting");
/* 669:335 */           paramAnonymousPeer.blacklist();
/* 670:336 */           localObject1 = BlockchainProcessorImpl.this.popOffTo(paramAnonymousBlock);
/* 671:337 */           i = 0;
/* 672:338 */           for (localObject2 = ((List)localObject1).iterator(); ((Iterator)localObject2).hasNext();)
/* 673:    */           {
/* 674:338 */             BlockImpl localBlockImpl = (BlockImpl)((Iterator)localObject2).next();
/* 675:339 */             TransactionProcessorImpl.getInstance().processLater(localBlockImpl.getTransactions());
/* 676:    */           }
/* 677:    */         }
/* 678:343 */         if (i == 0) {
/* 679:344 */           for (int j = localList.size() - 1; j >= 0; j--)
/* 680:    */           {
/* 681:345 */             localObject2 = (BlockImpl)localList.remove(j);
/* 682:    */             try
/* 683:    */             {
/* 684:347 */               BlockchainProcessorImpl.this.pushBlock((BlockImpl)localObject2);
/* 685:    */             }
/* 686:    */             catch (BlockchainProcessor.BlockNotAcceptedException localBlockNotAcceptedException2)
/* 687:    */             {
/* 688:349 */               Logger.logErrorMessage("Popped off block no longer acceptable: " + ((BlockImpl)localObject2).getJSONObject().toJSONString(), localBlockNotAcceptedException2);
/* 689:350 */               break;
/* 690:    */             }
/* 691:    */           }
/* 692:    */         } else {
/* 693:354 */           for (localIterator = localList.iterator(); localIterator.hasNext();)
/* 694:    */           {
/* 695:354 */             localObject2 = (BlockImpl)localIterator.next();
/* 696:355 */             TransactionProcessorImpl.getInstance().processLater(((BlockImpl)localObject2).getTransactions());
/* 697:    */           }
/* 698:    */         }
/* 699:    */       }
/* 700:    */     }
/* 701:    */   };
/* 702:    */   
/* 703:    */   private BlockchainProcessorImpl()
/* 704:    */   {
/* 705:365 */     this.blockListeners.addListener(new Listener()
/* 706:    */     {
/* 707:    */       public void notify(Block paramAnonymousBlock)
/* 708:    */       {
/* 709:368 */         if (paramAnonymousBlock.getHeight() % 5000 == 0) {
/* 710:369 */           Logger.logMessage("processed block " + paramAnonymousBlock.getHeight());
/* 711:    */         }
/* 712:    */       }
/* 713:369 */     }, BlockchainProcessor.Event.BLOCK_SCANNED);
/* 714:    */     
/* 715:    */ 
/* 716:    */ 
/* 717:    */ 
/* 718:374 */     this.blockListeners.addListener(new Listener()
/* 719:    */     {
/* 720:    */       public void notify(Block paramAnonymousBlock)
/* 721:    */       {
/* 722:377 */         if (paramAnonymousBlock.getHeight() % 5000 == 0)
/* 723:    */         {
/* 724:378 */           Logger.logMessage("received block " + paramAnonymousBlock.getHeight());
/* 725:379 */           Db.analyzeTables();
/* 726:    */         }
/* 727:    */       }
/* 728:379 */     }, BlockchainProcessor.Event.BLOCK_PUSHED);
/* 729:384 */     if (this.trimDerivedTables) {
/* 730:385 */       this.blockListeners.addListener(new Listener()
/* 731:    */       {
/* 732:    */         public void notify(Block paramAnonymousBlock)
/* 733:    */         {
/* 734:388 */           if (paramAnonymousBlock.getHeight() % 1440 == 0)
/* 735:    */           {
/* 736:389 */             BlockchainProcessorImpl.this.lastTrimHeight = Math.max(paramAnonymousBlock.getHeight() - Constants.MAX_ROLLBACK, 0);
/* 737:390 */             if (BlockchainProcessorImpl.this.lastTrimHeight > 0) {
/* 738:391 */               for (DerivedDbTable localDerivedDbTable : BlockchainProcessorImpl.this.derivedTables) {
/* 739:392 */                 localDerivedDbTable.trim(BlockchainProcessorImpl.this.lastTrimHeight);
/* 740:    */               }
/* 741:    */             }
/* 742:    */           }
/* 743:    */         }
/* 744:392 */       }, BlockchainProcessor.Event.AFTER_BLOCK_APPLY);
/* 745:    */     }
/* 746:400 */     this.blockListeners.addListener(new Listener()
/* 747:    */     {
/* 748:    */       public void notify(Block paramAnonymousBlock) {}
/* 749:400 */     }, BlockchainProcessor.Event.RESCAN_END);
/* 750:    */     
/* 751:    */ 
/* 752:    */ 
/* 753:    */ 
/* 754:    */ 
/* 755:    */ 
/* 756:407 */     ThreadPool.runBeforeStart(new Runnable()
/* 757:    */     {
/* 758:    */       public void run()
/* 759:    */       {
/* 760:410 */         BlockchainProcessorImpl.this.addGenesisBlock();
/* 761:411 */         if (BlockchainProcessorImpl.this.forceScan) {
/* 762:412 */           BlockchainProcessorImpl.this.scan(0);
/* 763:    */         }
/* 764:    */       }
/* 765:412 */     }, false);
/* 766:    */     
/* 767:    */ 
/* 768:    */ 
/* 769:    */ 
/* 770:417 */     ThreadPool.scheduleThread("GetMoreBlocks", this.getMoreBlocksThread, 1);
/* 771:    */   }
/* 772:    */   
/* 773:    */   public boolean addListener(Listener<Block> paramListener, BlockchainProcessor.Event paramEvent)
/* 774:    */   {
/* 775:423 */     return this.blockListeners.addListener(paramListener, paramEvent);
/* 776:    */   }
/* 777:    */   
/* 778:    */   public boolean removeListener(Listener<Block> paramListener, BlockchainProcessor.Event paramEvent)
/* 779:    */   {
/* 780:428 */     return this.blockListeners.removeListener(paramListener, paramEvent);
/* 781:    */   }
/* 782:    */   
/* 783:    */   public void registerDerivedTable(DerivedDbTable paramDerivedDbTable)
/* 784:    */   {
/* 785:433 */     this.derivedTables.add(paramDerivedDbTable);
/* 786:    */   }
/* 787:    */   
/* 788:    */   public Peer getLastBlockchainFeeder()
/* 789:    */   {
/* 790:438 */     return this.lastBlockchainFeeder;
/* 791:    */   }
/* 792:    */   
/* 793:    */   public int getLastBlockchainFeederHeight()
/* 794:    */   {
/* 795:443 */     return this.lastBlockchainFeederHeight;
/* 796:    */   }
/* 797:    */   
/* 798:    */   public boolean isScanning()
/* 799:    */   {
/* 800:448 */     return this.isScanning;
/* 801:    */   }
/* 802:    */   
/* 803:    */   public int getMinRollbackHeight()
/* 804:    */   {
/* 805:453 */     return this.trimDerivedTables ? Math.max(this.blockchain.getHeight() - Constants.MAX_ROLLBACK, 0) : this.lastTrimHeight > 0 ? this.lastTrimHeight : 0;
/* 806:    */   }
/* 807:    */   
/* 808:    */   public void processPeerBlock(JSONObject paramJSONObject)
/* 809:    */     throws NxtException
/* 810:    */   {
/* 811:458 */     BlockImpl localBlockImpl = BlockImpl.parseBlock(paramJSONObject);
/* 812:459 */     pushBlock(localBlockImpl);
/* 813:    */   }
/* 814:    */   
/* 815:    */   public List<BlockImpl> popOffTo(int paramInt)
/* 816:    */   {
/* 817:464 */     return popOffTo(this.blockchain.getBlockAtHeight(paramInt));
/* 818:    */   }
/* 819:    */   
/* 820:    */   public void fullReset()
/* 821:    */   {
/* 822:469 */     synchronized (this.blockchain)
/* 823:    */     {
/* 824:471 */       BlockDb.deleteAll();
/* 825:472 */       addGenesisBlock();
/* 826:473 */       scan(0);
/* 827:    */     }
/* 828:    */   }
/* 829:    */   
/* 830:    */   public void forceScanAtStart()
/* 831:    */   {
/* 832:479 */     this.forceScan = true;
/* 833:    */   }
/* 834:    */   
/* 835:    */   public void validateAtNextScan()
/* 836:    */   {
/* 837:484 */     this.validateAtScan = true;
/* 838:    */   }
/* 839:    */   
/* 840:    */   void setGetMoreBlocks(boolean paramBoolean)
/* 841:    */   {
/* 842:488 */     this.getMoreBlocks = paramBoolean;
/* 843:    */   }
/* 844:    */   
/* 845:    */   private void addBlock(BlockImpl paramBlockImpl)
/* 846:    */   {
/* 847:    */     try
/* 848:    */     {
/* 849:492 */       Connection localConnection = Db.getConnection();Object localObject1 = null;
/* 850:    */       try
/* 851:    */       {
/* 852:493 */         BlockDb.saveBlock(localConnection, paramBlockImpl);
/* 853:494 */         this.blockchain.setLastBlock(paramBlockImpl);
/* 854:    */       }
/* 855:    */       catch (Throwable localThrowable2)
/* 856:    */       {
/* 857:492 */         localObject1 = localThrowable2;throw localThrowable2;
/* 858:    */       }
/* 859:    */       finally
/* 860:    */       {
/* 861:495 */         if (localConnection != null) {
/* 862:495 */           if (localObject1 != null) {
/* 863:    */             try
/* 864:    */             {
/* 865:495 */               localConnection.close();
/* 866:    */             }
/* 867:    */             catch (Throwable localThrowable3)
/* 868:    */             {
/* 869:495 */               ((Throwable)localObject1).addSuppressed(localThrowable3);
/* 870:    */             }
/* 871:    */           } else {
/* 872:495 */             localConnection.close();
/* 873:    */           }
/* 874:    */         }
/* 875:    */       }
/* 876:    */     }
/* 877:    */     catch (SQLException localSQLException)
/* 878:    */     {
/* 879:496 */       throw new RuntimeException(localSQLException.toString(), localSQLException);
/* 880:    */     }
/* 881:    */   }
/* 882:    */   
/* 883:    */   private void addGenesisBlock()
/* 884:    */   {
/* 885:    */     Object localObject1;
/* 886:501 */     if (BlockDb.hasBlock(3444294670862540038L))
/* 887:    */     {
/* 888:502 */       Logger.logMessage("Genesis block already in database");
/* 889:503 */       localObject1 = BlockDb.findLastBlock();
/* 890:504 */       this.blockchain.setLastBlock((BlockImpl)localObject1);
/* 891:505 */       Logger.logMessage("Last block height: " + ((BlockImpl)localObject1).getHeight());
/* 892:506 */       return;
/* 893:    */     }
/* 894:508 */     Logger.logMessage("Genesis block not in database, starting from scratch");
/* 895:    */     try
/* 896:    */     {
/* 897:510 */       localObject1 = new ArrayList();
/* 898:511 */       MessageDigest localMessageDigest = Crypto.sha256();
/* 899:512 */       for (Object localObject2 = ((List)localObject1).iterator(); ((Iterator)localObject2).hasNext();)
/* 900:    */       {
/* 901:512 */         localObject3 = (Transaction)((Iterator)localObject2).next();
/* 902:513 */         localMessageDigest.update(((Transaction)localObject3).getBytes());
/* 903:    */       }
/* 904:515 */       localObject2 = ByteBuffer.allocate(0);
/* 905:516 */       ((ByteBuffer)localObject2).order(ByteOrder.LITTLE_ENDIAN);
/* 906:517 */       Object localObject3 = ((ByteBuffer)localObject2).array();
/* 907:518 */       BlockImpl localBlockImpl = new BlockImpl(-1, 0, 0L, 0L, 0L, ((List)localObject1).size() * 128, localMessageDigest.digest(), Genesis.CREATOR_PUBLIC_KEY, new byte[32], Genesis.GENESIS_BLOCK_SIGNATURE, null, (List)localObject1, 0L, (byte[])localObject3);
/* 908:    */       
/* 909:520 */       localBlockImpl.setPrevious(null);
/* 910:521 */       addBlock(localBlockImpl);
/* 911:    */     }
/* 912:    */     catch (NxtException.ValidationException localValidationException)
/* 913:    */     {
/* 914:523 */       Logger.logMessage(localValidationException.getMessage());
/* 915:524 */       throw new RuntimeException(localValidationException.toString(), localValidationException);
/* 916:    */     }
/* 917:    */   }
/* 918:    */   
/* 919:    */   private void pushBlock(BlockImpl paramBlockImpl)
/* 920:    */     throws BlockchainProcessor.BlockNotAcceptedException
/* 921:    */   {
/* 922:530 */     int i = Nxt.getEpochTime();
/* 923:532 */     synchronized (this.blockchain)
/* 924:    */     {
/* 925:533 */       TransactionProcessorImpl localTransactionProcessorImpl = TransactionProcessorImpl.getInstance();
/* 926:534 */       BlockImpl localBlockImpl = null;
/* 927:    */       try
/* 928:    */       {
/* 929:536 */         Db.beginTransaction();
/* 930:537 */         localBlockImpl = this.blockchain.getLastBlock();
/* 931:539 */         if (localBlockImpl.getId() != paramBlockImpl.getPreviousBlockId()) {
/* 932:540 */           throw new BlockchainProcessor.BlockOutOfOrderException("Previous block id doesn't match");
/* 933:    */         }
/* 934:543 */         if (paramBlockImpl.getVersion() != getBlockVersion(localBlockImpl.getHeight())) {
/* 935:544 */           throw new BlockchainProcessor.BlockNotAcceptedException("Invalid version " + paramBlockImpl.getVersion());
/* 936:    */         }
/* 937:547 */         if ((paramBlockImpl.getVersion() != 1) && (!Arrays.equals(Crypto.sha256().digest(localBlockImpl.getBytes()), paramBlockImpl.getPreviousBlockHash()))) {
/* 938:548 */           throw new BlockchainProcessor.BlockNotAcceptedException("Previous block hash doesn't match");
/* 939:    */         }
/* 940:550 */         if ((paramBlockImpl.getTimestamp() > i + 15) || (paramBlockImpl.getTimestamp() <= localBlockImpl.getTimestamp())) {
/* 941:551 */           throw new BlockchainProcessor.BlockOutOfOrderException("Invalid timestamp: " + paramBlockImpl.getTimestamp() + " current time is " + i + ", previous block timestamp is " + localBlockImpl.getTimestamp());
/* 942:    */         }
/* 943:554 */         if ((paramBlockImpl.getId() == 0L) || (BlockDb.hasBlock(paramBlockImpl.getId()))) {
/* 944:555 */           throw new BlockchainProcessor.BlockNotAcceptedException("Duplicate block or invalid id");
/* 945:    */         }
/* 946:557 */         if (!paramBlockImpl.verifyGenerationSignature()) {
/* 947:558 */           throw new BlockchainProcessor.BlockNotAcceptedException("Generation signature verification failed");
/* 948:    */         }
/* 949:560 */         if (!paramBlockImpl.verifyBlockSignature()) {
/* 950:561 */           throw new BlockchainProcessor.BlockNotAcceptedException("Block signature verification failed");
/* 951:    */         }
/* 952:564 */         HashMap localHashMap = new HashMap();
/* 953:565 */         long l1 = 0L;
/* 954:566 */         long l2 = 0L;
/* 955:567 */         MessageDigest localMessageDigest = Crypto.sha256();
/* 956:569 */         for (TransactionImpl localTransactionImpl : paramBlockImpl.getTransactions())
/* 957:    */         {
/* 958:571 */           if (localTransactionImpl.getTimestamp() > i + 15) {
/* 959:572 */             throw new BlockchainProcessor.BlockOutOfOrderException("Invalid transaction timestamp: " + localTransactionImpl.getTimestamp() + ", current time is " + i);
/* 960:    */           }
/* 961:575 */           if ((localTransactionImpl.getTimestamp() > paramBlockImpl.getTimestamp() + 15) || (localTransactionImpl.getExpiration() < paramBlockImpl.getTimestamp())) {
/* 962:577 */             throw new BlockchainProcessor.TransactionNotAcceptedException("Invalid transaction timestamp " + localTransactionImpl.getTimestamp() + " for transaction " + localTransactionImpl.getStringId() + ", current time is " + i + ", block timestamp is " + paramBlockImpl.getTimestamp(), localTransactionImpl);
/* 963:    */           }
/* 964:581 */           if (TransactionDb.hasTransaction(localTransactionImpl.getId())) {
/* 965:582 */             throw new BlockchainProcessor.TransactionNotAcceptedException("Transaction " + localTransactionImpl.getStringId() + " is already in the blockchain", localTransactionImpl);
/* 966:    */           }
/* 967:585 */           if ((localTransactionImpl.getReferencedTransactionFullHash() != null) && (
/* 968:586 */             ((localBlockImpl.getHeight() < 0) && (!TransactionDb.hasTransaction(Convert.fullHashToId(localTransactionImpl.getReferencedTransactionFullHash())))) || ((localBlockImpl.getHeight() >= 0) && (!hasAllReferencedTransactions(localTransactionImpl, localTransactionImpl.getTimestamp(), 0))))) {
/* 969:590 */             throw new BlockchainProcessor.TransactionNotAcceptedException("Missing or invalid referenced transaction " + localTransactionImpl.getReferencedTransactionFullHash() + " for transaction " + localTransactionImpl.getStringId(), localTransactionImpl);
/* 970:    */           }
/* 971:595 */           if (localTransactionImpl.getVersion() != localTransactionProcessorImpl.getTransactionVersion(localBlockImpl.getHeight())) {
/* 972:596 */             throw new BlockchainProcessor.TransactionNotAcceptedException("Invalid transaction version " + localTransactionImpl.getVersion() + " at height " + localBlockImpl.getHeight(), localTransactionImpl);
/* 973:    */           }
/* 974:599 */           if (!localTransactionImpl.verifySignature()) {
/* 975:600 */             throw new BlockchainProcessor.TransactionNotAcceptedException("Signature verification failed for transaction " + localTransactionImpl.getStringId() + " at height " + localBlockImpl.getHeight(), localTransactionImpl);
/* 976:    */           }
/* 977:603 */           if ((Nxt.getBlockchain().getHeight() >= 49200) && 
/* 978:604 */             (!EconomicClustering.verifyFork(localTransactionImpl)))
/* 979:    */           {
/* 980:605 */             Logger.logDebugMessage("Block " + paramBlockImpl.getStringId() + " height " + (localBlockImpl.getHeight() + 1) + " contains transaction that was generated on a fork: " + localTransactionImpl.getStringId() + " ecBlockHeight " + localTransactionImpl.getECBlockHeight() + " ecBlockId " + Convert.toUnsignedLong(localTransactionImpl.getECBlockId()));
/* 981:    */             
/* 982:    */ 
/* 983:    */ 
/* 984:609 */             throw new BlockchainProcessor.TransactionNotAcceptedException("Transaction belongs to a different fork", localTransactionImpl);
/* 985:    */           }
/* 986:612 */           if (localTransactionImpl.getId() == 0L) {
/* 987:613 */             throw new BlockchainProcessor.TransactionNotAcceptedException("Invalid transaction id", localTransactionImpl);
/* 988:    */           }
/* 989:615 */           if (localTransactionImpl.isDuplicate(localHashMap)) {
/* 990:616 */             throw new BlockchainProcessor.TransactionNotAcceptedException("Transaction is a duplicate: " + localTransactionImpl.getStringId(), localTransactionImpl);
/* 991:    */           }
/* 992:    */           try
/* 993:    */           {
/* 994:620 */             localTransactionImpl.validate();
/* 995:    */           }
/* 996:    */           catch (NxtException.ValidationException localValidationException)
/* 997:    */           {
/* 998:622 */             throw new BlockchainProcessor.TransactionNotAcceptedException(localValidationException.getMessage(), localTransactionImpl);
/* 999:    */           }
/* :00:625 */           l1 += localTransactionImpl.getAmountNQT();
/* :01:    */           
/* :02:627 */           l2 += localTransactionImpl.getFeeNQT();
/* :03:    */           
/* :04:629 */           localMessageDigest.update(localTransactionImpl.getBytes());
/* :05:    */         }
/* :06:633 */         if ((l1 > paramBlockImpl.getTotalAmountNQT()) || (l2 > paramBlockImpl.getTotalFeeNQT())) {
/* :07:634 */           throw new BlockchainProcessor.BlockNotAcceptedException("Total amount or fee don't match transaction totals");
/* :08:    */         }
/* :09:636 */         if (!Arrays.equals(localMessageDigest.digest(), paramBlockImpl.getPayloadHash())) {
/* :10:637 */           throw new BlockchainProcessor.BlockNotAcceptedException("Payload hash doesn't match");
/* :11:    */         }
/* :12:640 */         long l3 = Convert.safeSubtract(paramBlockImpl.getTotalAmountNQT(), l1);
/* :13:641 */         long l4 = Convert.safeSubtract(paramBlockImpl.getTotalFeeNQT(), l2);
/* :14:    */         
/* :15:643 */         paramBlockImpl.setPrevious(localBlockImpl);
/* :16:644 */         this.blockListeners.notify(paramBlockImpl, BlockchainProcessor.Event.BEFORE_BLOCK_ACCEPT);
/* :17:645 */         localTransactionProcessorImpl.requeueAllUnconfirmedTransactions();
/* :18:646 */         addBlock(paramBlockImpl);
/* :19:647 */         accept(paramBlockImpl, Long.valueOf(l3), Long.valueOf(l4));
/* :20:    */         
/* :21:649 */         Db.commitTransaction();
/* :22:    */       }
/* :23:    */       catch (Exception localException)
/* :24:    */       {
/* :25:651 */         Db.rollbackTransaction();
/* :26:652 */         this.blockchain.setLastBlock(localBlockImpl);
/* :27:653 */         throw localException;
/* :28:    */       }
/* :29:    */       finally
/* :30:    */       {
/* :31:655 */         Db.endTransaction();
/* :32:    */       }
/* :33:    */     }
/* :34:659 */     this.blockListeners.notify(paramBlockImpl, BlockchainProcessor.Event.BLOCK_PUSHED);
/* :35:661 */     if (paramBlockImpl.getTimestamp() >= Nxt.getEpochTime() - 15) {
/* :36:662 */       Peers.sendToSomePeers(paramBlockImpl);
/* :37:    */     }
/* :38:    */   }
/* :39:    */   
/* :40:    */   private void accept(BlockImpl paramBlockImpl, Long paramLong1, Long paramLong2)
/* :41:    */     throws BlockchainProcessor.TransactionNotAcceptedException, BlockchainProcessor.BlockNotAcceptedException
/* :42:    */   {
/* :43:668 */     Subscription.clearRemovals();
/* :44:669 */     TransactionProcessorImpl localTransactionProcessorImpl = TransactionProcessorImpl.getInstance();
/* :45:670 */     for (TransactionImpl localTransactionImpl : paramBlockImpl.getTransactions()) {
/* :46:671 */       if (!localTransactionImpl.applyUnconfirmed()) {
/* :47:672 */         throw new BlockchainProcessor.TransactionNotAcceptedException("Double spending transaction: " + localTransactionImpl.getStringId(), localTransactionImpl);
/* :48:    */       }
/* :49:    */     }
/* :50:675 */     long l1 = 0L;
/* :51:676 */     long l2 = 0L;
/* :52:    */     
/* :53:    */ 
/* :54:679 */     AT.clearPendingFees();
/* :55:680 */     AT.clearPendingTransactions();
/* :56:    */     AT_Block localAT_Block;
/* :57:    */     try
/* :58:    */     {
/* :59:682 */       localAT_Block = AT_Controller.validateATs(paramBlockImpl.getBlockATs(), Nxt.getBlockchain().getHeight());
/* :60:    */     }
/* :61:    */     catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
/* :62:    */     {
/* :63:685 */       throw new BlockchainProcessor.BlockNotAcceptedException("md5 does not exist");
/* :64:    */     }
/* :65:    */     catch (AT_Exception localAT_Exception)
/* :66:    */     {
/* :67:687 */       throw new BlockchainProcessor.BlockNotAcceptedException("ats are not matching at block height " + Nxt.getBlockchain().getHeight());
/* :68:    */     }
/* :69:689 */     l1 += localAT_Block.getTotalAmount();
/* :70:690 */     l2 += localAT_Block.getTotalFees();
/* :71:692 */     if (Subscription.isEnabled()) {
/* :72:693 */       l2 += Subscription.applyUnconfirmed(paramBlockImpl.getTimestamp());
/* :73:    */     }
/* :74:695 */     if ((paramLong1 != null) && (paramLong1.longValue() != l1)) {
/* :75:696 */       throw new BlockchainProcessor.BlockNotAcceptedException("Calculated remaining amount doesn't add up");
/* :76:    */     }
/* :77:698 */     if ((paramLong2 != null) && (paramLong2.longValue() != l2)) {
/* :78:699 */       throw new BlockchainProcessor.BlockNotAcceptedException("Calculated remaining fee doesn't add up");
/* :79:    */     }
/* :80:701 */     this.blockListeners.notify(paramBlockImpl, BlockchainProcessor.Event.BEFORE_BLOCK_APPLY);
/* :81:702 */     paramBlockImpl.apply();
/* :82:703 */     Subscription.applyConfirmed(paramBlockImpl);
/* :83:704 */     if (Escrow.isEnabled()) {
/* :84:705 */       Escrow.updateOnBlock(paramBlockImpl);
/* :85:    */     }
/* :86:707 */     this.blockListeners.notify(paramBlockImpl, BlockchainProcessor.Event.AFTER_BLOCK_APPLY);
/* :87:708 */     if (paramBlockImpl.getTransactions().size() > 0) {
/* :88:709 */       localTransactionProcessorImpl.notifyListeners(paramBlockImpl.getTransactions(), TransactionProcessor.Event.ADDED_CONFIRMED_TRANSACTIONS);
/* :89:    */     }
/* :90:    */   }
/* :91:    */   
/* :92:    */   private List<BlockImpl> popOffTo(Block paramBlock)
/* :93:    */   {
/* :94:714 */     synchronized (this.blockchain)
/* :95:    */     {
/* :96:715 */       if (paramBlock.getHeight() < getMinRollbackHeight()) {
/* :97:716 */         throw new IllegalArgumentException("Rollback to height " + paramBlock.getHeight() + " not suppported, " + "current height " + Nxt.getBlockchain().getHeight());
/* :98:    */       }
/* :99:719 */       if (!this.blockchain.hasBlock(paramBlock.getId()))
/* ;00:    */       {
/* ;01:720 */         Logger.logDebugMessage("Block " + paramBlock.getStringId() + " not found in blockchain, nothing to pop off");
/* ;02:721 */         return Collections.emptyList();
/* ;03:    */       }
/* ;04:723 */       ArrayList localArrayList = new ArrayList();
/* ;05:    */       try
/* ;06:    */       {
/* ;07:725 */         Db.beginTransaction();
/* ;08:726 */         BlockImpl localBlockImpl = this.blockchain.getLastBlock();
/* ;09:727 */         Logger.logDebugMessage("Rollback from " + localBlockImpl.getHeight() + " to " + paramBlock.getHeight());
/* ;10:728 */         while ((localBlockImpl.getId() != paramBlock.getId()) && (localBlockImpl.getId() != 3444294670862540038L))
/* ;11:    */         {
/* ;12:729 */           localArrayList.add(localBlockImpl);
/* ;13:730 */           localBlockImpl = popLastBlock();
/* ;14:    */         }
/* ;15:732 */         for (DerivedDbTable localDerivedDbTable : this.derivedTables) {
/* ;16:733 */           localDerivedDbTable.rollback(paramBlock.getHeight());
/* ;17:    */         }
/* ;18:735 */         Db.commitTransaction();
/* ;19:    */       }
/* ;20:    */       catch (RuntimeException localRuntimeException)
/* ;21:    */       {
/* ;22:737 */         Db.rollbackTransaction();
/* ;23:738 */         Logger.logDebugMessage("Error popping off to " + paramBlock.getHeight(), localRuntimeException);
/* ;24:739 */         throw localRuntimeException;
/* ;25:    */       }
/* ;26:    */       finally
/* ;27:    */       {
/* ;28:741 */         Db.endTransaction();
/* ;29:    */       }
/* ;30:743 */       return localArrayList;
/* ;31:    */     }
/* ;32:    */   }
/* ;33:    */   
/* ;34:    */   private BlockImpl popLastBlock()
/* ;35:    */   {
/* ;36:748 */     BlockImpl localBlockImpl1 = this.blockchain.getLastBlock();
/* ;37:749 */     if (localBlockImpl1.getId() == 3444294670862540038L) {
/* ;38:750 */       throw new RuntimeException("Cannot pop off genesis block");
/* ;39:    */     }
/* ;40:752 */     BlockImpl localBlockImpl2 = BlockDb.findBlock(localBlockImpl1.getPreviousBlockId());
/* ;41:753 */     this.blockchain.setLastBlock(localBlockImpl1, localBlockImpl2);
/* ;42:754 */     for (TransactionImpl localTransactionImpl : localBlockImpl1.getTransactions()) {
/* ;43:755 */       localTransactionImpl.unsetBlock();
/* ;44:    */     }
/* ;45:757 */     BlockDb.deleteBlocksFrom(localBlockImpl1.getId());
/* ;46:758 */     this.blockListeners.notify(localBlockImpl1, BlockchainProcessor.Event.BLOCK_POPPED);
/* ;47:759 */     return localBlockImpl2;
/* ;48:    */   }
/* ;49:    */   
/* ;50:    */   int getBlockVersion(int paramInt)
/* ;51:    */   {
/* ;52:763 */     return paramInt < 0 ? 2 : paramInt < 0 ? 1 : 3;
/* ;53:    */   }
/* ;54:    */   
/* ;55:    */   void generateBlock(String paramString, byte[] paramArrayOfByte, Long paramLong)
/* ;56:    */     throws BlockchainProcessor.BlockNotAcceptedException
/* ;57:    */   {
/* ;58:770 */     TransactionProcessorImpl localTransactionProcessorImpl = TransactionProcessorImpl.getInstance();
/* ;59:771 */     ArrayList localArrayList = new ArrayList();
/* ;60:772 */     Object localObject1 = new FilteringIterator(localTransactionProcessorImpl.getAllUnconfirmedTransactions(), new FilteringIterator.Filter()
/* ;61:    */     {
/* ;62:    */       public boolean ok(TransactionImpl paramAnonymousTransactionImpl)
/* ;63:    */       {
/* ;64:776 */         return BlockchainProcessorImpl.this.hasAllReferencedTransactions(paramAnonymousTransactionImpl, paramAnonymousTransactionImpl.getTimestamp(), 0);
/* ;65:    */       }
/* ;66:771 */     }
/* ;67:772 */       );Object 
/* ;68:    */     
/* ;69:    */ 
/* ;70:    */ 
/* ;71:776 */       localObject2 = null;
/* ;72:    */     try
/* ;73:    */     {
/* ;74:779 */       for (TransactionImpl localTransactionImpl : (FilteringIterator)localObject1) {
/* ;75:780 */         localArrayList.add(localTransactionImpl);
/* ;76:    */       }
/* ;77:    */     }
/* ;78:    */     catch (Throwable localThrowable2)
/* ;79:    */     {
/* ;80:772 */       localObject2 = localThrowable2;throw localThrowable2;
/* ;81:    */     }
/* ;82:    */     finally
/* ;83:    */     {
/* ;84:782 */       if (localObject1 != null) {
/* ;85:782 */         if (localObject2 != null) {
/* ;86:    */           try
/* ;87:    */           {
/* ;88:782 */             ((FilteringIterator)localObject1).close();
/* ;89:    */           }
/* ;90:    */           catch (Throwable localThrowable3)
/* ;91:    */           {
/* ;92:782 */             ((Throwable)localObject2).addSuppressed(localThrowable3);
/* ;93:    */           }
/* ;94:    */         } else {
/* ;95:782 */           ((FilteringIterator)localObject1).close();
/* ;96:    */         }
/* ;97:    */       }
/* ;98:    */     }
/* ;99:784 */     localObject1 = this.blockchain.getLastBlock();
/* <00:    */     
/* <01:786 */     localObject2 = new TreeSet();
/* <02:    */     
/* <03:788 */     HashMap localHashMap = new HashMap();
/* <04:    */     
/* <05:790 */     long l1 = 0L;
/* <06:791 */     long l2 = 0L;
/* <07:792 */     int i = 0;
/* <08:    */     
/* <09:794 */     int j = Nxt.getEpochTime();
/* <10:796 */     while ((i <= 44880) && (((SortedSet)localObject2).size() <= 255))
/* <11:    */     {
/* <12:798 */       int k = ((SortedSet)localObject2).size();
/* <13:800 */       for (localObject4 = localArrayList.iterator(); ((Iterator)localObject4).hasNext();)
/* <14:    */       {
/* <15:800 */         localObject5 = (TransactionImpl)((Iterator)localObject4).next();
/* <16:802 */         if (((SortedSet)localObject2).size() >= 255) {
/* <17:    */           break;
/* <18:    */         }
/* <19:806 */         int m = ((TransactionImpl)localObject5).getSize();
/* <20:807 */         if ((!((SortedSet)localObject2).contains(localObject5)) && (i + m <= 44880) && 
/* <21:    */         
/* <22:    */ 
/* <23:    */ 
/* <24:811 */           (((TransactionImpl)localObject5).getVersion() == localTransactionProcessorImpl.getTransactionVersion(((BlockImpl)localObject1).getHeight())) && 
/* <25:    */           
/* <26:    */ 
/* <27:    */ 
/* <28:815 */           (((TransactionImpl)localObject5).getTimestamp() <= j + 15) && (((TransactionImpl)localObject5).getExpiration() >= j)) {
/* <29:819 */           if ((Nxt.getBlockchain().getHeight() >= 49200) && 
/* <30:820 */             (!EconomicClustering.verifyFork((Transaction)localObject5)))
/* <31:    */           {
/* <32:821 */             Logger.logDebugMessage("Including transaction that was generated on a fork: " + ((TransactionImpl)localObject5).getStringId() + " ecBlockHeight " + ((TransactionImpl)localObject5).getECBlockHeight() + " ecBlockId " + Convert.toUnsignedLong(((TransactionImpl)localObject5).getECBlockId()));
/* <33:    */           }
/* <34:827 */           else if (!((TransactionImpl)localObject5).isDuplicate(localHashMap))
/* <35:    */           {
/* <36:    */             try
/* <37:    */             {
/* <38:832 */               ((TransactionImpl)localObject5).validate();
/* <39:    */             }
/* <40:    */             catch (NxtException.NotCurrentlyValidException localNotCurrentlyValidException)
/* <41:    */             {
/* <42:    */               continue;
/* <43:    */             }
/* <44:    */             catch (NxtException.ValidationException localValidationException1)
/* <45:    */             {
/* <46:836 */               localTransactionProcessorImpl.removeUnconfirmedTransaction((TransactionImpl)localObject5);
/* <47:    */             }
/* <48:837 */             continue;
/* <49:    */             
/* <50:    */ 
/* <51:840 */             ((SortedSet)localObject2).add(localObject5);
/* <52:841 */             i += m;
/* <53:842 */             l1 += ((TransactionImpl)localObject5).getAmountNQT();
/* <54:843 */             l2 += ((TransactionImpl)localObject5).getFeeNQT();
/* <55:    */           }
/* <56:    */         }
/* <57:    */       }
/* <58:847 */       if (((SortedSet)localObject2).size() == k) {
/* <59:    */         break;
/* <60:    */       }
/* <61:    */     }
/* <62:852 */     if (Subscription.isEnabled()) {
/* <63:853 */       synchronized (this.blockchain)
/* <64:    */       {
/* <65:854 */         Subscription.clearRemovals();
/* <66:    */         try
/* <67:    */         {
/* <68:856 */           Db.beginTransaction();
/* <69:857 */           localTransactionProcessorImpl.requeueAllUnconfirmedTransactions();
/* <70:859 */           for (localObject4 = ((SortedSet)localObject2).iterator(); ((Iterator)localObject4).hasNext();)
/* <71:    */           {
/* <72:859 */             localObject5 = (TransactionImpl)((Iterator)localObject4).next();
/* <73:860 */             ((TransactionImpl)localObject5).applyUnconfirmed();
/* <74:    */           }
/* <75:862 */           l2 += Subscription.calculateFees(j);
/* <76:    */         }
/* <77:    */         finally
/* <78:    */         {
/* <79:865 */           Db.rollbackTransaction();
/* <80:866 */           Db.endTransaction();
/* <81:    */         }
/* <82:    */       }
/* <83:    */     }
/* <84:874 */     AT.clearPendingFees();
/* <85:875 */     AT.clearPendingTransactions();
/* <86:876 */     ??? = AT_Controller.getCurrentBlockATs(44880 - i, ((BlockImpl)localObject1).getHeight() + 1);
/* <87:877 */     Object localObject4 = ((AT_Block)???).getBytesForBlock();
/* <88:880 */     if (localObject4 != null)
/* <89:    */     {
/* <90:882 */       i += localObject4.length;
/* <91:883 */       l2 += ((AT_Block)???).getTotalFees();
/* <92:884 */       l1 += ((AT_Block)???).getTotalAmount();
/* <93:    */     }
/* <94:890 */     Object localObject5 = Crypto.sha256();
/* <95:892 */     for (Object localObject6 = ((SortedSet)localObject2).iterator(); ((Iterator)localObject6).hasNext();)
/* <96:    */     {
/* <97:892 */       localObject7 = (Transaction)((Iterator)localObject6).next();
/* <98:893 */       ((MessageDigest)localObject5).update(((Transaction)localObject7).getBytes());
/* <99:    */     }
/* =00:896 */     localObject6 = ((MessageDigest)localObject5).digest();
/* =01:    */     
/* =02:898 */     Object localObject7 = ByteBuffer.allocate(40);
/* =03:899 */     ((ByteBuffer)localObject7).put(((BlockImpl)localObject1).getGenerationSignature());
/* =04:900 */     ((ByteBuffer)localObject7).putLong(((BlockImpl)localObject1).getGeneratorId());
/* =05:    */     
/* =06:902 */     Shabal256 localShabal256 = new Shabal256();
/* =07:903 */     localShabal256.update(((ByteBuffer)localObject7).array());
/* =08:904 */     byte[] arrayOfByte1 = localShabal256.digest();
/* =09:    */     
/* =10:    */ 
/* =11:907 */     byte[] arrayOfByte2 = Crypto.sha256().digest(((BlockImpl)localObject1).getBytes());
/* =12:    */     BlockImpl localBlockImpl;
/* =13:    */     try
/* =14:    */     {
/* =15:911 */       localBlockImpl = new BlockImpl(getBlockVersion(((BlockImpl)localObject1).getHeight()), j, ((BlockImpl)localObject1).getId(), l1, l2, i, (byte[])localObject6, paramArrayOfByte, arrayOfByte1, null, arrayOfByte2, new ArrayList((Collection)localObject2), paramLong.longValue(), (byte[])localObject4);
/* =16:    */     }
/* =17:    */     catch (NxtException.ValidationException localValidationException2)
/* =18:    */     {
/* =19:916 */       Logger.logMessage("Error generating block", localValidationException2);
/* =20:917 */       return;
/* =21:    */     }
/* =22:920 */     localBlockImpl.sign(paramString);
/* =23:    */     
/* =24:922 */     localBlockImpl.setPrevious((BlockImpl)localObject1);
/* =25:    */     try
/* =26:    */     {
/* =27:925 */       pushBlock(localBlockImpl);
/* =28:926 */       this.blockListeners.notify(localBlockImpl, BlockchainProcessor.Event.BLOCK_GENERATED);
/* =29:927 */       Logger.logDebugMessage("Account " + Convert.toUnsignedLong(localBlockImpl.getGeneratorId()) + " generated block " + localBlockImpl.getStringId() + " at height " + localBlockImpl.getHeight());
/* =30:    */     }
/* =31:    */     catch (BlockchainProcessor.TransactionNotAcceptedException localTransactionNotAcceptedException)
/* =32:    */     {
/* =33:930 */       Logger.logDebugMessage("Generate block failed: " + localTransactionNotAcceptedException.getMessage());
/* =34:931 */       Transaction localTransaction = localTransactionNotAcceptedException.getTransaction();
/* =35:932 */       Logger.logDebugMessage("Removing invalid transaction: " + localTransaction.getStringId());
/* =36:933 */       localTransactionProcessorImpl.removeUnconfirmedTransaction((TransactionImpl)localTransaction);
/* =37:934 */       throw localTransactionNotAcceptedException;
/* =38:    */     }
/* =39:    */     catch (BlockchainProcessor.BlockNotAcceptedException localBlockNotAcceptedException)
/* =40:    */     {
/* =41:936 */       Logger.logDebugMessage("Generate block failed: " + localBlockNotAcceptedException.getMessage());
/* =42:937 */       throw localBlockNotAcceptedException;
/* =43:    */     }
/* =44:    */   }
/* =45:    */   
/* =46:    */   private boolean hasAllReferencedTransactions(Transaction paramTransaction, int paramInt1, int paramInt2)
/* =47:    */   {
/* =48:942 */     if (paramTransaction.getReferencedTransactionFullHash() == null) {
/* =49:943 */       return (paramInt1 - paramTransaction.getTimestamp() < 5184000) && (paramInt2 < 10);
/* =50:    */     }
/* =51:945 */     paramTransaction = TransactionDb.findTransactionByFullHash(paramTransaction.getReferencedTransactionFullHash());
/* =52:946 */     if ((!Subscription.isEnabled()) && 
/* =53:947 */       (paramTransaction != null) && (paramTransaction.getSignature() == null)) {
/* =54:948 */       paramTransaction = null;
/* =55:    */     }
/* =56:951 */     return (paramTransaction != null) && (hasAllReferencedTransactions(paramTransaction, paramInt1, paramInt2 + 1));
/* =57:    */   }
/* =58:    */   
/* =59:    */   /* Error */
/* =60:    */   public void scan(int paramInt)
/* =61:    */   {
/* =62:    */     // Byte code:
/* =63:    */     //   0: aload_0
/* =64:    */     //   1: getfield 10	nxt/BlockchainProcessorImpl:blockchain	Lnxt/BlockchainImpl;
/* =65:    */     //   4: dup
/* =66:    */     //   5: astore_2
/* =67:    */     //   6: monitorenter
/* =68:    */     //   7: invokestatic 107	nxt/TransactionProcessorImpl:getInstance	()Lnxt/TransactionProcessorImpl;
/* =69:    */     //   10: astore_3
/* =70:    */     //   11: invokestatic 161	nxt/Nxt:getBlockchain	()Lnxt/Blockchain;
/* =71:    */     //   14: invokeinterface 162 1 0
/* =72:    */     //   19: istore 4
/* =73:    */     //   21: iload_1
/* =74:    */     //   22: iload 4
/* =75:    */     //   24: iconst_1
/* =76:    */     //   25: iadd
/* =77:    */     //   26: if_icmple +44 -> 70
/* =78:    */     //   29: new 226	java/lang/IllegalArgumentException
/* =79:    */     //   32: dup
/* =80:    */     //   33: new 74	java/lang/StringBuilder
/* =81:    */     //   36: dup
/* =82:    */     //   37: invokespecial 75	java/lang/StringBuilder:<init>	()V
/* =83:    */     //   40: ldc_w 297
/* =84:    */     //   43: invokevirtual 77	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* =85:    */     //   46: iload_1
/* =86:    */     //   47: iconst_1
/* =87:    */     //   48: isub
/* =88:    */     //   49: invokevirtual 79	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
/* =89:    */     //   52: ldc_w 298
/* =90:    */     //   55: invokevirtual 77	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* =91:    */     //   58: iload 4
/* =92:    */     //   60: invokevirtual 79	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
/* =93:    */     //   63: invokevirtual 80	java/lang/StringBuilder:toString	()Ljava/lang/String;
/* =94:    */     //   66: invokespecial 230	java/lang/IllegalArgumentException:<init>	(Ljava/lang/String;)V
/* =95:    */     //   69: athrow
/* =96:    */     //   70: iload_1
/* =97:    */     //   71: ifle +44 -> 115
/* =98:    */     //   74: iload_1
/* =99:    */     //   75: aload_0
/* >00:    */     //   76: invokevirtual 225	nxt/BlockchainProcessorImpl:getMinRollbackHeight	()I
/* >01:    */     //   79: if_icmpge +36 -> 115
/* >02:    */     //   82: new 74	java/lang/StringBuilder
/* >03:    */     //   85: dup
/* >04:    */     //   86: invokespecial 75	java/lang/StringBuilder:<init>	()V
/* >05:    */     //   89: ldc_w 299
/* >06:    */     //   92: invokevirtual 77	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* >07:    */     //   95: getstatic 52	nxt/Constants:MAX_ROLLBACK	I
/* >08:    */     //   98: invokevirtual 79	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
/* >09:    */     //   101: ldc_w 300
/* >10:    */     //   104: invokevirtual 77	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* >11:    */     //   107: invokevirtual 80	java/lang/StringBuilder:toString	()Ljava/lang/String;
/* >12:    */     //   110: invokestatic 72	nxt/util/Logger:logMessage	(Ljava/lang/String;)V
/* >13:    */     //   113: iconst_0
/* >14:    */     //   114: istore_1
/* >15:    */     //   115: iload_1
/* >16:    */     //   116: ifge +5 -> 121
/* >17:    */     //   119: iconst_0
/* >18:    */     //   120: istore_1
/* >19:    */     //   121: aload_0
/* >20:    */     //   122: iconst_1
/* >21:    */     //   123: putfield 50	nxt/BlockchainProcessorImpl:isScanning	Z
/* >22:    */     //   126: new 74	java/lang/StringBuilder
/* >23:    */     //   129: dup
/* >24:    */     //   130: invokespecial 75	java/lang/StringBuilder:<init>	()V
/* >25:    */     //   133: ldc_w 301
/* >26:    */     //   136: invokevirtual 77	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* >27:    */     //   139: iload_1
/* >28:    */     //   140: invokevirtual 79	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
/* >29:    */     //   143: ldc_w 302
/* >30:    */     //   146: invokevirtual 77	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* >31:    */     //   149: invokevirtual 80	java/lang/StringBuilder:toString	()Ljava/lang/String;
/* >32:    */     //   152: invokestatic 72	nxt/util/Logger:logMessage	(Ljava/lang/String;)V
/* >33:    */     //   155: aload_0
/* >34:    */     //   156: getfield 26	nxt/BlockchainProcessorImpl:validateAtScan	Z
/* >35:    */     //   159: ifeq +9 -> 168
/* >36:    */     //   162: ldc_w 303
/* >37:    */     //   165: invokestatic 174	nxt/util/Logger:logDebugMessage	(Ljava/lang/String;)V
/* >38:    */     //   168: invokestatic 108	nxt/db/Db:beginTransaction	()Ljava/sql/Connection;
/* >39:    */     //   171: astore 5
/* >40:    */     //   173: aconst_null
/* >41:    */     //   174: astore 6
/* >42:    */     //   176: aload 5
/* >43:    */     //   178: ldc_w 304
/* >44:    */     //   181: invokeinterface 305 2 0
/* >45:    */     //   186: astore 7
/* >46:    */     //   188: aconst_null
/* >47:    */     //   189: astore 8
/* >48:    */     //   191: aload_3
/* >49:    */     //   192: invokevirtual 191	nxt/TransactionProcessorImpl:requeueAllUnconfirmedTransactions	()V
/* >50:    */     //   195: aload_0
/* >51:    */     //   196: getfield 4	nxt/BlockchainProcessorImpl:derivedTables	Ljava/util/List;
/* >52:    */     //   199: invokeinterface 85 1 0
/* >53:    */     //   204: astore 9
/* >54:    */     //   206: aload 9
/* >55:    */     //   208: invokeinterface 86 1 0
/* >56:    */     //   213: ifeq +38 -> 251
/* >57:    */     //   216: aload 9
/* >58:    */     //   218: invokeinterface 87 1 0
/* >59:    */     //   223: checkcast 239	nxt/db/DerivedDbTable
/* >60:    */     //   226: astore 10
/* >61:    */     //   228: iload_1
/* >62:    */     //   229: ifne +11 -> 240
/* >63:    */     //   232: aload 10
/* >64:    */     //   234: invokevirtual 306	nxt/db/DerivedDbTable:truncate	()V
/* >65:    */     //   237: goto +11 -> 248
/* >66:    */     //   240: aload 10
/* >67:    */     //   242: iload_1
/* >68:    */     //   243: iconst_1
/* >69:    */     //   244: isub
/* >70:    */     //   245: invokevirtual 240	nxt/db/DerivedDbTable:rollback	(I)V
/* >71:    */     //   248: goto -42 -> 206
/* >72:    */     //   251: aload 7
/* >73:    */     //   253: iconst_1
/* >74:    */     //   254: iload_1
/* >75:    */     //   255: invokeinterface 307 3 0
/* >76:    */     //   260: aload 7
/* >77:    */     //   262: invokeinterface 308 1 0
/* >78:    */     //   267: astore 9
/* >79:    */     //   269: aconst_null
/* >80:    */     //   270: astore 10
/* >81:    */     //   272: iload_1
/* >82:    */     //   273: invokestatic 309	nxt/BlockDb:findBlockAtHeight	(I)Lnxt/BlockImpl;
/* >83:    */     //   276: astore 11
/* >84:    */     //   278: aload_0
/* >85:    */     //   279: getfield 23	nxt/BlockchainProcessorImpl:blockListeners	Lnxt/util/Listeners;
/* >86:    */     //   282: aload 11
/* >87:    */     //   284: getstatic 310	nxt/BlockchainProcessor$Event:RESCAN_BEGIN	Lnxt/BlockchainProcessor$Event;
/* >88:    */     //   287: invokevirtual 190	nxt/util/Listeners:notify	(Ljava/lang/Object;Ljava/lang/Enum;)V
/* >89:    */     //   290: aload 11
/* >90:    */     //   292: invokevirtual 110	nxt/BlockImpl:getId	()J
/* >91:    */     //   295: lstore 12
/* >92:    */     //   297: iload_1
/* >93:    */     //   298: ifne +15 -> 313
/* >94:    */     //   301: aload_0
/* >95:    */     //   302: getfield 10	nxt/BlockchainProcessorImpl:blockchain	Lnxt/BlockchainImpl;
/* >96:    */     //   305: aload 11
/* >97:    */     //   307: invokevirtual 60	nxt/BlockchainImpl:setLastBlock	(Lnxt/BlockImpl;)V
/* >98:    */     //   310: goto +16 -> 326
/* >99:    */     //   313: aload_0
/* ?00:    */     //   314: getfield 10	nxt/BlockchainProcessorImpl:blockchain	Lnxt/BlockchainImpl;
/* ?01:    */     //   317: iload_1
/* ?02:    */     //   318: iconst_1
/* ?03:    */     //   319: isub
/* ?04:    */     //   320: invokestatic 309	nxt/BlockDb:findBlockAtHeight	(I)Lnxt/BlockImpl;
/* ?05:    */     //   323: invokevirtual 60	nxt/BlockchainImpl:setLastBlock	(Lnxt/BlockImpl;)V
/* ?06:    */     //   326: aload 9
/* ?07:    */     //   328: invokeinterface 311 1 0
/* ?08:    */     //   333: ifeq +579 -> 912
/* ?09:    */     //   336: aload 5
/* ?10:    */     //   338: aload 9
/* ?11:    */     //   340: invokestatic 312	nxt/BlockDb:loadBlock	(Ljava/sql/Connection;Ljava/sql/ResultSet;)Lnxt/BlockImpl;
/* ?12:    */     //   343: astore 11
/* ?13:    */     //   345: aload 11
/* ?14:    */     //   347: invokevirtual 110	nxt/BlockImpl:getId	()J
/* ?15:    */     //   350: lload 12
/* ?16:    */     //   352: lcmp
/* ?17:    */     //   353: ifeq +53 -> 406
/* ?18:    */     //   356: lload 12
/* ?19:    */     //   358: ldc2_w 68
/* ?20:    */     //   361: lcmp
/* ?21:    */     //   362: ifne +33 -> 395
/* ?22:    */     //   365: new 74	java/lang/StringBuilder
/* ?23:    */     //   368: dup
/* ?24:    */     //   369: invokespecial 75	java/lang/StringBuilder:<init>	()V
/* ?25:    */     //   372: ldc_w 313
/* ?26:    */     //   375: invokevirtual 77	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* ?27:    */     //   378: aload 11
/* ?28:    */     //   380: invokevirtual 110	nxt/BlockImpl:getId	()J
/* ?29:    */     //   383: invokestatic 173	nxt/util/Convert:toUnsignedLong	(J)Ljava/lang/String;
/* ?30:    */     //   386: invokevirtual 77	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* ?31:    */     //   389: invokevirtual 80	java/lang/StringBuilder:toString	()Ljava/lang/String;
/* ?32:    */     //   392: invokestatic 174	nxt/util/Logger:logDebugMessage	(Ljava/lang/String;)V
/* ?33:    */     //   395: new 314	nxt/NxtException$NotValidException
/* ?34:    */     //   398: dup
/* ?35:    */     //   399: ldc_w 315
/* ?36:    */     //   402: invokespecial 316	nxt/NxtException$NotValidException:<init>	(Ljava/lang/String;)V
/* ?37:    */     //   405: athrow
/* ?38:    */     //   406: aload_0
/* ?39:    */     //   407: getfield 26	nxt/BlockchainProcessorImpl:validateAtScan	Z
/* ?40:    */     //   410: ifeq +310 -> 720
/* ?41:    */     //   413: lload 12
/* ?42:    */     //   415: ldc2_w 68
/* ?43:    */     //   418: lcmp
/* ?44:    */     //   419: ifeq +301 -> 720
/* ?45:    */     //   422: aload 11
/* ?46:    */     //   424: invokevirtual 132	nxt/BlockImpl:verifyBlockSignature	()Z
/* ?47:    */     //   427: ifne +14 -> 441
/* ?48:    */     //   430: new 314	nxt/NxtException$NotValidException
/* ?49:    */     //   433: dup
/* ?50:    */     //   434: ldc_w 317
/* ?51:    */     //   437: invokespecial 316	nxt/NxtException$NotValidException:<init>	(Ljava/lang/String;)V
/* ?52:    */     //   440: athrow
/* ?53:    */     //   441: aload 11
/* ?54:    */     //   443: invokevirtual 130	nxt/BlockImpl:verifyGenerationSignature	()Z
/* ?55:    */     //   446: ifne +14 -> 460
/* ?56:    */     //   449: new 314	nxt/NxtException$NotValidException
/* ?57:    */     //   452: dup
/* ?58:    */     //   453: ldc_w 318
/* ?59:    */     //   456: invokespecial 316	nxt/NxtException$NotValidException:<init>	(Ljava/lang/String;)V
/* ?60:    */     //   459: athrow
/* ?61:    */     //   460: aload 11
/* ?62:    */     //   462: invokevirtual 115	nxt/BlockImpl:getVersion	()I
/* ?63:    */     //   465: aload_0
/* ?64:    */     //   466: aload_0
/* ?65:    */     //   467: getfield 10	nxt/BlockchainProcessorImpl:blockchain	Lnxt/BlockchainImpl;
/* ?66:    */     //   470: invokevirtual 51	nxt/BlockchainImpl:getHeight	()I
/* ?67:    */     //   473: invokevirtual 116	nxt/BlockchainProcessorImpl:getBlockVersion	(I)I
/* ?68:    */     //   476: if_icmpeq +14 -> 490
/* ?69:    */     //   479: new 314	nxt/NxtException$NotValidException
/* ?70:    */     //   482: dup
/* ?71:    */     //   483: ldc_w 319
/* ?72:    */     //   486: invokespecial 316	nxt/NxtException$NotValidException:<init>	(Ljava/lang/String;)V
/* ?73:    */     //   489: athrow
/* ?74:    */     //   490: aload 11
/* ?75:    */     //   492: invokevirtual 120	nxt/BlockImpl:getBytes	()[B
/* ?76:    */     //   495: astore 14
/* ?77:    */     //   497: aload 11
/* ?78:    */     //   499: invokevirtual 320	nxt/BlockImpl:getJSONObject	()Lorg/json/simple/JSONObject;
/* ?79:    */     //   502: invokevirtual 321	org/json/simple/JSONObject:toJSONString	()Ljava/lang/String;
/* ?80:    */     //   505: invokestatic 322	org/json/simple/JSONValue:parse	(Ljava/lang/String;)Ljava/lang/Object;
/* ?81:    */     //   508: checkcast 323	org/json/simple/JSONObject
/* ?82:    */     //   511: astore 15
/* ?83:    */     //   513: aload 14
/* ?84:    */     //   515: aload 15
/* ?85:    */     //   517: invokestatic 54	nxt/BlockImpl:parseBlock	(Lorg/json/simple/JSONObject;)Lnxt/BlockImpl;
/* ?86:    */     //   520: invokevirtual 120	nxt/BlockImpl:getBytes	()[B
/* ?87:    */     //   523: invokestatic 123	java/util/Arrays:equals	([B[B)Z
/* ?88:    */     //   526: ifne +14 -> 540
/* ?89:    */     //   529: new 314	nxt/NxtException$NotValidException
/* ?90:    */     //   532: dup
/* ?91:    */     //   533: ldc_w 324
/* ?92:    */     //   536: invokespecial 316	nxt/NxtException$NotValidException:<init>	(Ljava/lang/String;)V
/* ?93:    */     //   539: athrow
/* ?94:    */     //   540: aload 11
/* ?95:    */     //   542: invokevirtual 136	nxt/BlockImpl:getTransactions	()Ljava/util/List;
/* ?96:    */     //   545: invokeinterface 85 1 0
/* ?97:    */     //   550: astore 16
/* ?98:    */     //   552: aload 16
/* ?99:    */     //   554: invokeinterface 86 1 0
/* @00:    */     //   559: ifeq +161 -> 720
/* @01:    */     //   562: aload 16
/* @02:    */     //   564: invokeinterface 87 1 0
/* @03:    */     //   569: checkcast 137	nxt/TransactionImpl
/* @04:    */     //   572: astore 17
/* @05:    */     //   574: aload 17
/* @06:    */     //   576: invokevirtual 159	nxt/TransactionImpl:verifySignature	()Z
/* @07:    */     //   579: ifne +14 -> 593
/* @08:    */     //   582: new 314	nxt/NxtException$NotValidException
/* @09:    */     //   585: dup
/* @10:    */     //   586: ldc_w 325
/* @11:    */     //   589: invokespecial 316	nxt/NxtException$NotValidException:<init>	(Ljava/lang/String;)V
/* @12:    */     //   592: athrow
/* @13:    */     //   593: aload 17
/* @14:    */     //   595: invokevirtual 155	nxt/TransactionImpl:getVersion	()B
/* @15:    */     //   598: aload_3
/* @16:    */     //   599: aload_0
/* @17:    */     //   600: getfield 10	nxt/BlockchainProcessorImpl:blockchain	Lnxt/BlockchainImpl;
/* @18:    */     //   603: invokevirtual 51	nxt/BlockchainImpl:getHeight	()I
/* @19:    */     //   606: invokevirtual 156	nxt/TransactionProcessorImpl:getTransactionVersion	(I)I
/* @20:    */     //   609: if_icmpeq +14 -> 623
/* @21:    */     //   612: new 314	nxt/NxtException$NotValidException
/* @22:    */     //   615: dup
/* @23:    */     //   616: ldc_w 326
/* @24:    */     //   619: invokespecial 316	nxt/NxtException$NotValidException:<init>	(Ljava/lang/String;)V
/* @25:    */     //   622: athrow
/* @26:    */     //   623: aload 17
/* @27:    */     //   625: invokevirtual 179	nxt/TransactionImpl:validate	()V
/* @28:    */     //   628: aload 17
/* @29:    */     //   630: invokevirtual 182	nxt/TransactionImpl:getBytes	()[B
/* @30:    */     //   633: astore 18
/* @31:    */     //   635: aload 11
/* @32:    */     //   637: invokevirtual 78	nxt/BlockImpl:getHeight	()I
/* @33:    */     //   640: ifle +33 -> 673
/* @34:    */     //   643: aload 18
/* @35:    */     //   645: aload_3
/* @36:    */     //   646: aload 18
/* @37:    */     //   648: invokevirtual 327	nxt/TransactionProcessorImpl:parseTransaction	([B)Lnxt/Transaction;
/* @38:    */     //   651: invokeinterface 89 1 0
/* @39:    */     //   656: invokestatic 123	java/util/Arrays:equals	([B[B)Z
/* @40:    */     //   659: ifne +14 -> 673
/* @41:    */     //   662: new 314	nxt/NxtException$NotValidException
/* @42:    */     //   665: dup
/* @43:    */     //   666: ldc_w 328
/* @44:    */     //   669: invokespecial 316	nxt/NxtException$NotValidException:<init>	(Ljava/lang/String;)V
/* @45:    */     //   672: athrow
/* @46:    */     //   673: aload 17
/* @47:    */     //   675: invokevirtual 329	nxt/TransactionImpl:getJSONObject	()Lorg/json/simple/JSONObject;
/* @48:    */     //   678: invokevirtual 321	org/json/simple/JSONObject:toJSONString	()Ljava/lang/String;
/* @49:    */     //   681: invokestatic 322	org/json/simple/JSONValue:parse	(Ljava/lang/String;)Ljava/lang/Object;
/* @50:    */     //   684: checkcast 323	org/json/simple/JSONObject
/* @51:    */     //   687: astore 19
/* @52:    */     //   689: aload 18
/* @53:    */     //   691: aload_3
/* @54:    */     //   692: aload 19
/* @55:    */     //   694: invokevirtual 330	nxt/TransactionProcessorImpl:parseTransaction	(Lorg/json/simple/JSONObject;)Lnxt/TransactionImpl;
/* @56:    */     //   697: invokevirtual 182	nxt/TransactionImpl:getBytes	()[B
/* @57:    */     //   700: invokestatic 123	java/util/Arrays:equals	([B[B)Z
/* @58:    */     //   703: ifne +14 -> 717
/* @59:    */     //   706: new 314	nxt/NxtException$NotValidException
/* @60:    */     //   709: dup
/* @61:    */     //   710: ldc_w 331
/* @62:    */     //   713: invokespecial 316	nxt/NxtException$NotValidException:<init>	(Ljava/lang/String;)V
/* @63:    */     //   716: athrow
/* @64:    */     //   717: goto -165 -> 552
/* @65:    */     //   720: aload_0
/* @66:    */     //   721: getfield 23	nxt/BlockchainProcessorImpl:blockListeners	Lnxt/util/Listeners;
/* @67:    */     //   724: aload 11
/* @68:    */     //   726: getstatic 189	nxt/BlockchainProcessor$Event:BEFORE_BLOCK_ACCEPT	Lnxt/BlockchainProcessor$Event;
/* @69:    */     //   729: invokevirtual 190	nxt/util/Listeners:notify	(Ljava/lang/Object;Ljava/lang/Enum;)V
/* @70:    */     //   732: aload_0
/* @71:    */     //   733: getfield 10	nxt/BlockchainProcessorImpl:blockchain	Lnxt/BlockchainImpl;
/* @72:    */     //   736: aload 11
/* @73:    */     //   738: invokevirtual 60	nxt/BlockchainImpl:setLastBlock	(Lnxt/BlockImpl;)V
/* @74:    */     //   741: aload_0
/* @75:    */     //   742: aload 11
/* @76:    */     //   744: aconst_null
/* @77:    */     //   745: aconst_null
/* @78:    */     //   746: invokespecial 193	nxt/BlockchainProcessorImpl:accept	(Lnxt/BlockImpl;Ljava/lang/Long;Ljava/lang/Long;)V
/* @79:    */     //   749: aload 11
/* @80:    */     //   751: invokevirtual 332	nxt/BlockImpl:getNextBlockId	()J
/* @81:    */     //   754: lstore 12
/* @82:    */     //   756: invokestatic 194	nxt/db/Db:commitTransaction	()V
/* @83:    */     //   759: goto +138 -> 897
/* @84:    */     //   762: astore 14
/* @85:    */     //   764: invokestatic 197	nxt/db/Db:rollbackTransaction	()V
/* @86:    */     //   767: aload 14
/* @87:    */     //   769: invokevirtual 334	java/lang/Exception:toString	()Ljava/lang/String;
/* @88:    */     //   772: aload 14
/* @89:    */     //   774: invokestatic 242	nxt/util/Logger:logDebugMessage	(Ljava/lang/String;Ljava/lang/Exception;)V
/* @90:    */     //   777: new 74	java/lang/StringBuilder
/* @91:    */     //   780: dup
/* @92:    */     //   781: invokespecial 75	java/lang/StringBuilder:<init>	()V
/* @93:    */     //   784: ldc_w 335
/* @94:    */     //   787: invokevirtual 77	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* @95:    */     //   790: lload 12
/* @96:    */     //   792: invokestatic 173	nxt/util/Convert:toUnsignedLong	(J)Ljava/lang/String;
/* @97:    */     //   795: invokevirtual 77	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* @98:    */     //   798: ldc 158
/* @99:    */     //   800: invokevirtual 77	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* A00:    */     //   803: aload 11
/* A01:    */     //   805: ifnonnull +7 -> 812
/* A02:    */     //   808: iconst_0
/* A03:    */     //   809: goto +8 -> 817
/* A04:    */     //   812: aload 11
/* A05:    */     //   814: invokevirtual 78	nxt/BlockImpl:getHeight	()I
/* A06:    */     //   817: invokevirtual 79	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
/* A07:    */     //   820: ldc_w 336
/* A08:    */     //   823: invokevirtual 77	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* A09:    */     //   826: invokevirtual 80	java/lang/StringBuilder:toString	()Ljava/lang/String;
/* A10:    */     //   829: invokestatic 174	nxt/util/Logger:logDebugMessage	(Ljava/lang/String;)V
/* A11:    */     //   832: aload 11
/* A12:    */     //   834: ifnull +12 -> 846
/* A13:    */     //   837: aload_3
/* A14:    */     //   838: aload 11
/* A15:    */     //   840: invokevirtual 136	nxt/BlockImpl:getTransactions	()Ljava/util/List;
/* A16:    */     //   843: invokevirtual 337	nxt/TransactionProcessorImpl:processLater	(Ljava/util/Collection;)V
/* A17:    */     //   846: aload 9
/* A18:    */     //   848: invokeinterface 311 1 0
/* A19:    */     //   853: ifeq +29 -> 882
/* A20:    */     //   856: aload 5
/* A21:    */     //   858: aload 9
/* A22:    */     //   860: invokestatic 312	nxt/BlockDb:loadBlock	(Ljava/sql/Connection;Ljava/sql/ResultSet;)Lnxt/BlockImpl;
/* A23:    */     //   863: astore 11
/* A24:    */     //   865: aload_3
/* A25:    */     //   866: aload 11
/* A26:    */     //   868: invokevirtual 136	nxt/BlockImpl:getTransactions	()Ljava/util/List;
/* A27:    */     //   871: invokevirtual 337	nxt/TransactionProcessorImpl:processLater	(Ljava/util/Collection;)V
/* A28:    */     //   874: goto -28 -> 846
/* A29:    */     //   877: astore 15
/* A30:    */     //   879: goto -33 -> 846
/* A31:    */     //   882: lload 12
/* A32:    */     //   884: invokestatic 248	nxt/BlockDb:deleteBlocksFrom	(J)V
/* A33:    */     //   887: aload_0
/* A34:    */     //   888: getfield 10	nxt/BlockchainProcessorImpl:blockchain	Lnxt/BlockchainImpl;
/* A35:    */     //   891: invokestatic 73	nxt/BlockDb:findLastBlock	()Lnxt/BlockImpl;
/* A36:    */     //   894: invokevirtual 60	nxt/BlockchainImpl:setLastBlock	(Lnxt/BlockImpl;)V
/* A37:    */     //   897: aload_0
/* A38:    */     //   898: getfield 23	nxt/BlockchainProcessorImpl:blockListeners	Lnxt/util/Listeners;
/* A39:    */     //   901: aload 11
/* A40:    */     //   903: getstatic 32	nxt/BlockchainProcessor$Event:BLOCK_SCANNED	Lnxt/BlockchainProcessor$Event;
/* A41:    */     //   906: invokevirtual 190	nxt/util/Listeners:notify	(Ljava/lang/Object;Ljava/lang/Enum;)V
/* A42:    */     //   909: goto -583 -> 326
/* A43:    */     //   912: invokestatic 195	nxt/db/Db:endTransaction	()V
/* A44:    */     //   915: aload_0
/* A45:    */     //   916: getfield 23	nxt/BlockchainProcessorImpl:blockListeners	Lnxt/util/Listeners;
/* A46:    */     //   919: aload 11
/* A47:    */     //   921: getstatic 42	nxt/BlockchainProcessor$Event:RESCAN_END	Lnxt/BlockchainProcessor$Event;
/* A48:    */     //   924: invokevirtual 190	nxt/util/Listeners:notify	(Ljava/lang/Object;Ljava/lang/Enum;)V
/* A49:    */     //   927: aload 9
/* A50:    */     //   929: ifnull +93 -> 1022
/* A51:    */     //   932: aload 10
/* A52:    */     //   934: ifnull +25 -> 959
/* A53:    */     //   937: aload 9
/* A54:    */     //   939: invokeinterface 338 1 0
/* A55:    */     //   944: goto +78 -> 1022
/* A56:    */     //   947: astore 11
/* A57:    */     //   949: aload 10
/* A58:    */     //   951: aload 11
/* A59:    */     //   953: invokevirtual 63	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* A60:    */     //   956: goto +66 -> 1022
/* A61:    */     //   959: aload 9
/* A62:    */     //   961: invokeinterface 338 1 0
/* A63:    */     //   966: goto +56 -> 1022
/* A64:    */     //   969: astore 11
/* A65:    */     //   971: aload 11
/* A66:    */     //   973: astore 10
/* A67:    */     //   975: aload 11
/* A68:    */     //   977: athrow
/* A69:    */     //   978: astore 20
/* A70:    */     //   980: aload 9
/* A71:    */     //   982: ifnull +37 -> 1019
/* A72:    */     //   985: aload 10
/* A73:    */     //   987: ifnull +25 -> 1012
/* A74:    */     //   990: aload 9
/* A75:    */     //   992: invokeinterface 338 1 0
/* A76:    */     //   997: goto +22 -> 1019
/* A77:    */     //   1000: astore 21
/* A78:    */     //   1002: aload 10
/* A79:    */     //   1004: aload 21
/* A80:    */     //   1006: invokevirtual 63	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* A81:    */     //   1009: goto +10 -> 1019
/* A82:    */     //   1012: aload 9
/* A83:    */     //   1014: invokeinterface 338 1 0
/* A84:    */     //   1019: aload 20
/* A85:    */     //   1021: athrow
/* A86:    */     //   1022: aload 7
/* A87:    */     //   1024: ifnull +93 -> 1117
/* A88:    */     //   1027: aload 8
/* A89:    */     //   1029: ifnull +25 -> 1054
/* A90:    */     //   1032: aload 7
/* A91:    */     //   1034: invokeinterface 339 1 0
/* A92:    */     //   1039: goto +78 -> 1117
/* A93:    */     //   1042: astore 9
/* A94:    */     //   1044: aload 8
/* A95:    */     //   1046: aload 9
/* A96:    */     //   1048: invokevirtual 63	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* A97:    */     //   1051: goto +66 -> 1117
/* A98:    */     //   1054: aload 7
/* A99:    */     //   1056: invokeinterface 339 1 0
/* B00:    */     //   1061: goto +56 -> 1117
/* B01:    */     //   1064: astore 9
/* B02:    */     //   1066: aload 9
/* B03:    */     //   1068: astore 8
/* B04:    */     //   1070: aload 9
/* B05:    */     //   1072: athrow
/* B06:    */     //   1073: astore 22
/* B07:    */     //   1075: aload 7
/* B08:    */     //   1077: ifnull +37 -> 1114
/* B09:    */     //   1080: aload 8
/* B10:    */     //   1082: ifnull +25 -> 1107
/* B11:    */     //   1085: aload 7
/* B12:    */     //   1087: invokeinterface 339 1 0
/* B13:    */     //   1092: goto +22 -> 1114
/* B14:    */     //   1095: astore 23
/* B15:    */     //   1097: aload 8
/* B16:    */     //   1099: aload 23
/* B17:    */     //   1101: invokevirtual 63	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* B18:    */     //   1104: goto +10 -> 1114
/* B19:    */     //   1107: aload 7
/* B20:    */     //   1109: invokeinterface 339 1 0
/* B21:    */     //   1114: aload 22
/* B22:    */     //   1116: athrow
/* B23:    */     //   1117: aload 5
/* B24:    */     //   1119: ifnull +93 -> 1212
/* B25:    */     //   1122: aload 6
/* B26:    */     //   1124: ifnull +25 -> 1149
/* B27:    */     //   1127: aload 5
/* B28:    */     //   1129: invokeinterface 61 1 0
/* B29:    */     //   1134: goto +78 -> 1212
/* B30:    */     //   1137: astore 7
/* B31:    */     //   1139: aload 6
/* B32:    */     //   1141: aload 7
/* B33:    */     //   1143: invokevirtual 63	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* B34:    */     //   1146: goto +66 -> 1212
/* B35:    */     //   1149: aload 5
/* B36:    */     //   1151: invokeinterface 61 1 0
/* B37:    */     //   1156: goto +56 -> 1212
/* B38:    */     //   1159: astore 7
/* B39:    */     //   1161: aload 7
/* B40:    */     //   1163: astore 6
/* B41:    */     //   1165: aload 7
/* B42:    */     //   1167: athrow
/* B43:    */     //   1168: astore 24
/* B44:    */     //   1170: aload 5
/* B45:    */     //   1172: ifnull +37 -> 1209
/* B46:    */     //   1175: aload 6
/* B47:    */     //   1177: ifnull +25 -> 1202
/* B48:    */     //   1180: aload 5
/* B49:    */     //   1182: invokeinterface 61 1 0
/* B50:    */     //   1187: goto +22 -> 1209
/* B51:    */     //   1190: astore 25
/* B52:    */     //   1192: aload 6
/* B53:    */     //   1194: aload 25
/* B54:    */     //   1196: invokevirtual 63	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* B55:    */     //   1199: goto +10 -> 1209
/* B56:    */     //   1202: aload 5
/* B57:    */     //   1204: invokeinterface 61 1 0
/* B58:    */     //   1209: aload 24
/* B59:    */     //   1211: athrow
/* B60:    */     //   1212: goto +20 -> 1232
/* B61:    */     //   1215: astore 5
/* B62:    */     //   1217: new 65	java/lang/RuntimeException
/* B63:    */     //   1220: dup
/* B64:    */     //   1221: aload 5
/* B65:    */     //   1223: invokevirtual 66	java/sql/SQLException:toString	()Ljava/lang/String;
/* B66:    */     //   1226: aload 5
/* B67:    */     //   1228: invokespecial 67	java/lang/RuntimeException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
/* B68:    */     //   1231: athrow
/* B69:    */     //   1232: aload_0
/* B70:    */     //   1233: iconst_0
/* B71:    */     //   1234: putfield 26	nxt/BlockchainProcessorImpl:validateAtScan	Z
/* B72:    */     //   1237: new 74	java/lang/StringBuilder
/* B73:    */     //   1240: dup
/* B74:    */     //   1241: invokespecial 75	java/lang/StringBuilder:<init>	()V
/* B75:    */     //   1244: ldc_w 340
/* B76:    */     //   1247: invokevirtual 77	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* B77:    */     //   1250: invokestatic 161	nxt/Nxt:getBlockchain	()Lnxt/Blockchain;
/* B78:    */     //   1253: invokeinterface 162 1 0
/* B79:    */     //   1258: invokevirtual 79	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
/* B80:    */     //   1261: invokevirtual 80	java/lang/StringBuilder:toString	()Ljava/lang/String;
/* B81:    */     //   1264: invokestatic 72	nxt/util/Logger:logMessage	(Ljava/lang/String;)V
/* B82:    */     //   1267: aload_0
/* B83:    */     //   1268: iconst_0
/* B84:    */     //   1269: putfield 50	nxt/BlockchainProcessorImpl:isScanning	Z
/* B85:    */     //   1272: aload_2
/* B86:    */     //   1273: monitorexit
/* B87:    */     //   1274: goto +10 -> 1284
/* B88:    */     //   1277: astore 26
/* B89:    */     //   1279: aload_2
/* B90:    */     //   1280: monitorexit
/* B91:    */     //   1281: aload 26
/* B92:    */     //   1283: athrow
/* B93:    */     //   1284: return
/* B94:    */     // Line number table:
/* B95:    */     //   Java source line #956	-> byte code offset #0
/* B96:    */     //   Java source line #957	-> byte code offset #7
/* B97:    */     //   Java source line #958	-> byte code offset #11
/* B98:    */     //   Java source line #959	-> byte code offset #21
/* B99:    */     //   Java source line #960	-> byte code offset #29
/* C00:    */     //   Java source line #962	-> byte code offset #70
/* C01:    */     //   Java source line #963	-> byte code offset #82
/* C02:    */     //   Java source line #964	-> byte code offset #113
/* C03:    */     //   Java source line #966	-> byte code offset #115
/* C04:    */     //   Java source line #967	-> byte code offset #119
/* C05:    */     //   Java source line #969	-> byte code offset #121
/* C06:    */     //   Java source line #970	-> byte code offset #126
/* C07:    */     //   Java source line #971	-> byte code offset #155
/* C08:    */     //   Java source line #972	-> byte code offset #162
/* C09:    */     //   Java source line #974	-> byte code offset #168
/* C10:    */     //   Java source line #975	-> byte code offset #176
/* C11:    */     //   Java source line #974	-> byte code offset #188
/* C12:    */     //   Java source line #976	-> byte code offset #191
/* C13:    */     //   Java source line #977	-> byte code offset #195
/* C14:    */     //   Java source line #978	-> byte code offset #228
/* C15:    */     //   Java source line #979	-> byte code offset #232
/* C16:    */     //   Java source line #981	-> byte code offset #240
/* C17:    */     //   Java source line #983	-> byte code offset #248
/* C18:    */     //   Java source line #984	-> byte code offset #251
/* C19:    */     //   Java source line #985	-> byte code offset #260
/* C20:    */     //   Java source line #986	-> byte code offset #272
/* C21:    */     //   Java source line #987	-> byte code offset #278
/* C22:    */     //   Java source line #988	-> byte code offset #290
/* C23:    */     //   Java source line #989	-> byte code offset #297
/* C24:    */     //   Java source line #990	-> byte code offset #301
/* C25:    */     //   Java source line #993	-> byte code offset #313
/* C26:    */     //   Java source line #995	-> byte code offset #326
/* C27:    */     //   Java source line #997	-> byte code offset #336
/* C28:    */     //   Java source line #998	-> byte code offset #345
/* C29:    */     //   Java source line #999	-> byte code offset #356
/* C30:    */     //   Java source line #1000	-> byte code offset #365
/* C31:    */     //   Java source line #1002	-> byte code offset #395
/* C32:    */     //   Java source line #1004	-> byte code offset #406
/* C33:    */     //   Java source line #1005	-> byte code offset #422
/* C34:    */     //   Java source line #1006	-> byte code offset #430
/* C35:    */     //   Java source line #1008	-> byte code offset #441
/* C36:    */     //   Java source line #1009	-> byte code offset #449
/* C37:    */     //   Java source line #1011	-> byte code offset #460
/* C38:    */     //   Java source line #1012	-> byte code offset #479
/* C39:    */     //   Java source line #1014	-> byte code offset #490
/* C40:    */     //   Java source line #1015	-> byte code offset #497
/* C41:    */     //   Java source line #1016	-> byte code offset #513
/* C42:    */     //   Java source line #1017	-> byte code offset #529
/* C43:    */     //   Java source line #1019	-> byte code offset #540
/* C44:    */     //   Java source line #1020	-> byte code offset #574
/* C45:    */     //   Java source line #1021	-> byte code offset #582
/* C46:    */     //   Java source line #1023	-> byte code offset #593
/* C47:    */     //   Java source line #1024	-> byte code offset #612
/* C48:    */     //   Java source line #1034	-> byte code offset #623
/* C49:    */     //   Java source line #1035	-> byte code offset #628
/* C50:    */     //   Java source line #1036	-> byte code offset #635
/* C51:    */     //   Java source line #1038	-> byte code offset #662
/* C52:    */     //   Java source line #1040	-> byte code offset #673
/* C53:    */     //   Java source line #1041	-> byte code offset #689
/* C54:    */     //   Java source line #1042	-> byte code offset #706
/* C55:    */     //   Java source line #1044	-> byte code offset #717
/* C56:    */     //   Java source line #1046	-> byte code offset #720
/* C57:    */     //   Java source line #1047	-> byte code offset #732
/* C58:    */     //   Java source line #1048	-> byte code offset #741
/* C59:    */     //   Java source line #1049	-> byte code offset #749
/* C60:    */     //   Java source line #1050	-> byte code offset #756
/* C61:    */     //   Java source line #1068	-> byte code offset #759
/* C62:    */     //   Java source line #1051	-> byte code offset #762
/* C63:    */     //   Java source line #1052	-> byte code offset #764
/* C64:    */     //   Java source line #1053	-> byte code offset #767
/* C65:    */     //   Java source line #1054	-> byte code offset #777
/* C66:    */     //   Java source line #1056	-> byte code offset #832
/* C67:    */     //   Java source line #1057	-> byte code offset #837
/* C68:    */     //   Java source line #1059	-> byte code offset #846
/* C69:    */     //   Java source line #1061	-> byte code offset #856
/* C70:    */     //   Java source line #1062	-> byte code offset #865
/* C71:    */     //   Java source line #1064	-> byte code offset #874
/* C72:    */     //   Java source line #1063	-> byte code offset #877
/* C73:    */     //   Java source line #1064	-> byte code offset #879
/* C74:    */     //   Java source line #1066	-> byte code offset #882
/* C75:    */     //   Java source line #1067	-> byte code offset #887
/* C76:    */     //   Java source line #1069	-> byte code offset #897
/* C77:    */     //   Java source line #1071	-> byte code offset #912
/* C78:    */     //   Java source line #1072	-> byte code offset #915
/* C79:    */     //   Java source line #1073	-> byte code offset #927
/* C80:    */     //   Java source line #985	-> byte code offset #969
/* C81:    */     //   Java source line #1073	-> byte code offset #978
/* C82:    */     //   Java source line #1074	-> byte code offset #1022
/* C83:    */     //   Java source line #974	-> byte code offset #1064
/* C84:    */     //   Java source line #1074	-> byte code offset #1073
/* C85:    */     //   Java source line #974	-> byte code offset #1159
/* C86:    */     //   Java source line #1074	-> byte code offset #1168
/* C87:    */     //   Java source line #1076	-> byte code offset #1212
/* C88:    */     //   Java source line #1074	-> byte code offset #1215
/* C89:    */     //   Java source line #1075	-> byte code offset #1217
/* C90:    */     //   Java source line #1077	-> byte code offset #1232
/* C91:    */     //   Java source line #1078	-> byte code offset #1237
/* C92:    */     //   Java source line #1079	-> byte code offset #1267
/* C93:    */     //   Java source line #1080	-> byte code offset #1272
/* C94:    */     //   Java source line #1081	-> byte code offset #1284
/* C95:    */     // Local variable table:
/* C96:    */     //   start	length	slot	name	signature
/* C97:    */     //   0	1285	0	this	BlockchainProcessorImpl
/* C98:    */     //   0	1285	1	paramInt	int
/* C99:    */     //   5	1275	2	Ljava/lang/Object;	Object
/* D00:    */     //   10	856	3	localTransactionProcessorImpl	TransactionProcessorImpl
/* D01:    */     //   19	40	4	i	int
/* D02:    */     //   171	1032	5	localConnection	Connection
/* D03:    */     //   1215	12	5	localSQLException	SQLException
/* D04:    */     //   174	1019	6	localObject1	Object
/* D05:    */     //   186	922	7	localPreparedStatement	java.sql.PreparedStatement
/* D06:    */     //   1137	5	7	localThrowable1	Throwable
/* D07:    */     //   1159	7	7	localThrowable2	Throwable
/* D08:    */     //   189	909	8	localObject2	Object
/* D09:    */     //   204	809	9	localObject3	Object
/* D10:    */     //   1042	5	9	localThrowable3	Throwable
/* D11:    */     //   1064	7	9	localThrowable4	Throwable
/* D12:    */     //   226	777	10	localObject4	Object
/* D13:    */     //   276	644	11	localBlockImpl	BlockImpl
/* D14:    */     //   947	5	11	localThrowable5	Throwable
/* D15:    */     //   969	7	11	localThrowable6	Throwable
/* D16:    */     //   295	588	12	l	long
/* D17:    */     //   495	19	14	arrayOfByte1	byte[]
/* D18:    */     //   762	11	14	localNxtException	NxtException
/* D19:    */     //   511	5	15	localJSONObject1	JSONObject
/* D20:    */     //   877	1	15	localValidationException	NxtException.ValidationException
/* D21:    */     //   550	13	16	localIterator	Iterator
/* D22:    */     //   572	102	17	localTransactionImpl	TransactionImpl
/* D23:    */     //   633	57	18	arrayOfByte2	byte[]
/* D24:    */     //   687	6	19	localJSONObject2	JSONObject
/* D25:    */     //   978	42	20	localObject5	Object
/* D26:    */     //   1000	5	21	localThrowable7	Throwable
/* D27:    */     //   1073	42	22	localObject6	Object
/* D28:    */     //   1095	5	23	localThrowable8	Throwable
/* D29:    */     //   1168	42	24	localObject7	Object
/* D30:    */     //   1190	5	25	localThrowable9	Throwable
/* D31:    */     //   1277	5	26	localObject8	Object
/* D32:    */     // Exception table:
/* D33:    */     //   from	to	target	type
/* D34:    */     //   336	759	762	nxt/NxtException
/* D35:    */     //   336	759	762	java/lang/RuntimeException
/* D36:    */     //   856	874	877	nxt/NxtException$ValidationException
/* D37:    */     //   937	944	947	java/lang/Throwable
/* D38:    */     //   272	927	969	java/lang/Throwable
/* D39:    */     //   272	927	978	finally
/* D40:    */     //   969	980	978	finally
/* D41:    */     //   990	997	1000	java/lang/Throwable
/* D42:    */     //   1032	1039	1042	java/lang/Throwable
/* D43:    */     //   191	1022	1064	java/lang/Throwable
/* D44:    */     //   191	1022	1073	finally
/* D45:    */     //   1064	1075	1073	finally
/* D46:    */     //   1085	1092	1095	java/lang/Throwable
/* D47:    */     //   1127	1134	1137	java/lang/Throwable
/* D48:    */     //   176	1117	1159	java/lang/Throwable
/* D49:    */     //   176	1117	1168	finally
/* D50:    */     //   1159	1170	1168	finally
/* D51:    */     //   1180	1187	1190	java/lang/Throwable
/* D52:    */     //   168	1212	1215	java/sql/SQLException
/* D53:    */     //   7	1274	1277	finally
/* D54:    */     //   1277	1281	1277	finally
/* D55:    */   }
/* D56:    */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.BlockchainProcessorImpl
 * JD-Core Version:    0.7.1
 */