/*   1:    */ package nxt;
/*   2:    */ 
/*   3:    */ import java.sql.Connection;
/*   4:    */ import java.sql.PreparedStatement;
/*   5:    */ import java.sql.ResultSet;
/*   6:    */ import java.sql.SQLException;
/*   7:    */ import java.util.ArrayList;
/*   8:    */ import java.util.Collection;
/*   9:    */ import java.util.Collections;
/*  10:    */ import java.util.HashSet;
/*  11:    */ import java.util.Iterator;
/*  12:    */ import java.util.List;
/*  13:    */ import java.util.Set;
/*  14:    */ import java.util.concurrent.ConcurrentHashMap;
/*  15:    */ import nxt.db.Db;
/*  16:    */ import nxt.db.DbClause;
/*  17:    */ import nxt.db.DbIterator;
/*  18:    */ import nxt.db.DbKey;
/*  19:    */ import nxt.db.DbKey.Factory;
/*  20:    */ import nxt.db.DbKey.LongKeyFactory;
/*  21:    */ import nxt.db.EntityDbTable;
/*  22:    */ import nxt.peer.Peers;
/*  23:    */ import nxt.util.JSON;
/*  24:    */ import nxt.util.Listener;
/*  25:    */ import nxt.util.Listeners;
/*  26:    */ import nxt.util.Logger;
/*  27:    */ import nxt.util.ThreadPool;
/*  28:    */ import org.json.simple.JSONArray;
/*  29:    */ import org.json.simple.JSONObject;
/*  30:    */ import org.json.simple.JSONStreamAware;
/*  31:    */ 
/*  32:    */ final class TransactionProcessorImpl
/*  33:    */   implements TransactionProcessor
/*  34:    */ {
/*  35: 33 */   private static final boolean enableTransactionRebroadcasting = Nxt.getBooleanProperty("nxt.enableTransactionRebroadcasting").booleanValue();
/*  36: 34 */   private static final boolean testUnconfirmedTransactions = Nxt.getBooleanProperty("nxt.testUnconfirmedTransactions").booleanValue();
/*  37: 36 */   private static final TransactionProcessorImpl instance = new TransactionProcessorImpl();
/*  38:    */   
/*  39:    */   static TransactionProcessorImpl getInstance()
/*  40:    */   {
/*  41: 39 */     return instance;
/*  42:    */   }
/*  43:    */   
/*  44: 42 */   final DbKey.LongKeyFactory<TransactionImpl> unconfirmedTransactionDbKeyFactory = new DbKey.LongKeyFactory("id")
/*  45:    */   {
/*  46:    */     public DbKey newKey(TransactionImpl paramAnonymousTransactionImpl)
/*  47:    */     {
/*  48: 46 */       return paramAnonymousTransactionImpl.getDbKey();
/*  49:    */     }
/*  50:    */   };
/*  51: 51 */   private final EntityDbTable<TransactionImpl> unconfirmedTransactionTable = new EntityDbTable("unconfirmed_transaction", this.unconfirmedTransactionDbKeyFactory)
/*  52:    */   {
/*  53:    */     protected TransactionImpl load(Connection paramAnonymousConnection, ResultSet paramAnonymousResultSet)
/*  54:    */       throws SQLException
/*  55:    */     {
/*  56: 55 */       byte[] arrayOfByte = paramAnonymousResultSet.getBytes("transaction_bytes");
/*  57:    */       try
/*  58:    */       {
/*  59: 57 */         TransactionImpl localTransactionImpl = TransactionImpl.parseTransaction(arrayOfByte);
/*  60: 58 */         localTransactionImpl.setHeight(paramAnonymousResultSet.getInt("transaction_height"));
/*  61: 59 */         return localTransactionImpl;
/*  62:    */       }
/*  63:    */       catch (NxtException.ValidationException localValidationException)
/*  64:    */       {
/*  65: 61 */         throw new RuntimeException(localValidationException.toString(), localValidationException);
/*  66:    */       }
/*  67:    */     }
/*  68:    */     
/*  69:    */     protected void save(Connection paramAnonymousConnection, TransactionImpl paramAnonymousTransactionImpl)
/*  70:    */       throws SQLException
/*  71:    */     {
/*  72: 67 */       PreparedStatement localPreparedStatement = paramAnonymousConnection.prepareStatement("INSERT INTO unconfirmed_transaction (id, transaction_height, fee_per_byte, timestamp, expiration, transaction_bytes, height) VALUES (?, ?, ?, ?, ?, ?, ?)");Object localObject1 = null;
/*  73:    */       try
/*  74:    */       {
/*  75: 70 */         int i = 0;
/*  76: 71 */         localPreparedStatement.setLong(++i, paramAnonymousTransactionImpl.getId());
/*  77: 72 */         localPreparedStatement.setInt(++i, paramAnonymousTransactionImpl.getHeight());
/*  78: 73 */         localPreparedStatement.setLong(++i, paramAnonymousTransactionImpl.getFeeNQT() / paramAnonymousTransactionImpl.getSize());
/*  79: 74 */         localPreparedStatement.setInt(++i, paramAnonymousTransactionImpl.getTimestamp());
/*  80: 75 */         localPreparedStatement.setInt(++i, paramAnonymousTransactionImpl.getExpiration());
/*  81: 76 */         localPreparedStatement.setBytes(++i, paramAnonymousTransactionImpl.getBytes());
/*  82: 77 */         localPreparedStatement.setInt(++i, Nxt.getBlockchain().getHeight());
/*  83: 78 */         localPreparedStatement.executeUpdate();
/*  84:    */       }
/*  85:    */       catch (Throwable localThrowable2)
/*  86:    */       {
/*  87: 67 */         localObject1 = localThrowable2;throw localThrowable2;
/*  88:    */       }
/*  89:    */       finally
/*  90:    */       {
/*  91: 79 */         if (localPreparedStatement != null) {
/*  92: 79 */           if (localObject1 != null) {
/*  93:    */             try
/*  94:    */             {
/*  95: 79 */               localPreparedStatement.close();
/*  96:    */             }
/*  97:    */             catch (Throwable localThrowable3)
/*  98:    */             {
/*  99: 79 */               ((Throwable)localObject1).addSuppressed(localThrowable3);
/* 100:    */             }
/* 101:    */           } else {
/* 102: 79 */             localPreparedStatement.close();
/* 103:    */           }
/* 104:    */         }
/* 105:    */       }
/* 106:    */     }
/* 107:    */     
/* 108:    */     public void rollback(int paramAnonymousInt)
/* 109:    */     {
/* 110: 84 */       ArrayList localArrayList = new ArrayList();
/* 111:    */       try
/* 112:    */       {
/* 113: 85 */         Connection localConnection = Db.getConnection();Object localObject1 = null;
/* 114:    */         try
/* 115:    */         {
/* 116: 86 */           PreparedStatement localPreparedStatement = localConnection.prepareStatement("SELECT * FROM unconfirmed_transaction WHERE height > ?");Object localObject2 = null;
/* 117:    */           try
/* 118:    */           {
/* 119: 87 */             localPreparedStatement.setInt(1, paramAnonymousInt);
/* 120: 88 */             ResultSet localResultSet = localPreparedStatement.executeQuery();Object localObject3 = null;
/* 121:    */             try
/* 122:    */             {
/* 123: 89 */               while (localResultSet.next()) {
/* 124: 90 */                 localArrayList.add(load(localConnection, localResultSet));
/* 125:    */               }
/* 126:    */             }
/* 127:    */             catch (Throwable localThrowable6)
/* 128:    */             {
/* 129: 88 */               localObject3 = localThrowable6;throw localThrowable6;
/* 130:    */             }
/* 131:    */             finally {}
/* 132:    */           }
/* 133:    */           catch (Throwable localThrowable4)
/* 134:    */           {
/* 135: 85 */             localObject2 = localThrowable4;throw localThrowable4;
/* 136:    */           }
/* 137:    */           finally {}
/* 138:    */         }
/* 139:    */         catch (Throwable localThrowable2)
/* 140:    */         {
/* 141: 85 */           localObject1 = localThrowable2;throw localThrowable2;
/* 142:    */         }
/* 143:    */         finally
/* 144:    */         {
/* 145: 93 */           if (localConnection != null) {
/* 146: 93 */             if (localObject1 != null) {
/* 147:    */               try
/* 148:    */               {
/* 149: 93 */                 localConnection.close();
/* 150:    */               }
/* 151:    */               catch (Throwable localThrowable9)
/* 152:    */               {
/* 153: 93 */                 ((Throwable)localObject1).addSuppressed(localThrowable9);
/* 154:    */               }
/* 155:    */             } else {
/* 156: 93 */               localConnection.close();
/* 157:    */             }
/* 158:    */           }
/* 159:    */         }
/* 160:    */       }
/* 161:    */       catch (SQLException localSQLException)
/* 162:    */       {
/* 163: 94 */         throw new RuntimeException(localSQLException.toString(), localSQLException);
/* 164:    */       }
/* 165: 96 */       super.rollback(paramAnonymousInt);
/* 166: 97 */       TransactionProcessorImpl.this.processLater(localArrayList);
/* 167:    */     }
/* 168:    */     
/* 169:    */     protected String defaultSort()
/* 170:    */     {
/* 171:102 */       return " ORDER BY transaction_height ASC, fee_per_byte DESC, timestamp ASC, id ASC ";
/* 172:    */     }
/* 173:    */   };
/* 174:107 */   private final Set<TransactionImpl> nonBroadcastedTransactions = Collections.newSetFromMap(new ConcurrentHashMap());
/* 175:108 */   private final Listeners<List<? extends Transaction>, TransactionProcessor.Event> transactionListeners = new Listeners();
/* 176:109 */   private final Set<TransactionImpl> lostTransactions = new HashSet();
/* 177:111 */   private final Runnable removeUnconfirmedTransactionsThread = new Runnable()
/* 178:    */   {
/* 179:113 */     private final DbClause expiredClause = new DbClause(" expiration < ? ")
/* 180:    */     {
/* 181:    */       protected int set(PreparedStatement paramAnonymous2PreparedStatement, int paramAnonymous2Int)
/* 182:    */         throws SQLException
/* 183:    */       {
/* 184:116 */         paramAnonymous2PreparedStatement.setInt(paramAnonymous2Int, Nxt.getEpochTime());
/* 185:117 */         return paramAnonymous2Int + 1;
/* 186:    */       }
/* 187:    */     };
/* 188:    */     
/* 189:    */     public void run()
/* 190:    */     {
/* 191:    */       try
/* 192:    */       {
/* 193:    */         try
/* 194:    */         {
/* 195:126 */           ArrayList localArrayList = new ArrayList();
/* 196:127 */           DbIterator localDbIterator = TransactionProcessorImpl.this.unconfirmedTransactionTable.getManyBy(this.expiredClause, 0, -1, "");Object localObject1 = null;
/* 197:    */           try
/* 198:    */           {
/* 199:128 */             while (localDbIterator.hasNext()) {
/* 200:129 */               localArrayList.add(localDbIterator.next());
/* 201:    */             }
/* 202:    */           }
/* 203:    */           catch (Throwable localThrowable3)
/* 204:    */           {
/* 205:127 */             localObject1 = localThrowable3;throw localThrowable3;
/* 206:    */           }
/* 207:    */           finally
/* 208:    */           {
/* 209:131 */             if (localDbIterator != null) {
/* 210:131 */               if (localObject1 != null) {
/* 211:    */                 try
/* 212:    */                 {
/* 213:131 */                   localDbIterator.close();
/* 214:    */                 }
/* 215:    */                 catch (Throwable localThrowable4)
/* 216:    */                 {
/* 217:131 */                   ((Throwable)localObject1).addSuppressed(localThrowable4);
/* 218:    */                 }
/* 219:    */               } else {
/* 220:131 */                 localDbIterator.close();
/* 221:    */               }
/* 222:    */             }
/* 223:    */           }
/* 224:132 */           if (localArrayList.size() > 0) {
/* 225:133 */             synchronized (BlockchainImpl.getInstance())
/* 226:    */             {
/* 227:    */               try
/* 228:    */               {
/* 229:135 */                 Db.beginTransaction();
/* 230:136 */                 for (localObject1 = localArrayList.iterator(); ((Iterator)localObject1).hasNext();)
/* 231:    */                 {
/* 232:136 */                   TransactionImpl localTransactionImpl = (TransactionImpl)((Iterator)localObject1).next();
/* 233:137 */                   TransactionProcessorImpl.this.removeUnconfirmedTransaction(localTransactionImpl);
/* 234:    */                 }
/* 235:139 */                 Db.commitTransaction();
/* 236:    */               }
/* 237:    */               catch (Exception localException2)
/* 238:    */               {
/* 239:141 */                 Logger.logErrorMessage(localException2.toString(), localException2);
/* 240:    */                 
/* 241:143 */                 throw localException2;
/* 242:    */               }
/* 243:    */               finally
/* 244:    */               {
/* 245:145 */                 Db.endTransaction();
/* 246:    */               }
/* 247:    */             }
/* 248:    */           }
/* 249:    */         }
/* 250:    */         catch (Exception localException1)
/* 251:    */         {
/* 252:150 */           Logger.logDebugMessage("Error removing unconfirmed transactions", localException1);
/* 253:    */         }
/* 254:    */       }
/* 255:    */       catch (Throwable localThrowable1)
/* 256:    */       {
/* 257:153 */         Logger.logMessage("CRITICAL ERROR. PLEASE REPORT TO THE DEVELOPERS.\n" + localThrowable1.toString());
/* 258:154 */         localThrowable1.printStackTrace();
/* 259:155 */         System.exit(1);
/* 260:    */       }
/* 261:    */     }
/* 262:    */   };
/* 263:162 */   private final Runnable rebroadcastTransactionsThread = new Runnable()
/* 264:    */   {
/* 265:    */     public void run()
/* 266:    */     {
/* 267:    */       try
/* 268:    */       {
/* 269:    */         try
/* 270:    */         {
/* 271:169 */           ArrayList localArrayList = new ArrayList();
/* 272:170 */           int i = Nxt.getEpochTime();
/* 273:171 */           for (TransactionImpl localTransactionImpl : TransactionProcessorImpl.this.nonBroadcastedTransactions) {
/* 274:172 */             if ((TransactionDb.hasTransaction(localTransactionImpl.getId())) || (localTransactionImpl.getExpiration() < i)) {
/* 275:173 */               TransactionProcessorImpl.this.nonBroadcastedTransactions.remove(localTransactionImpl);
/* 276:174 */             } else if (localTransactionImpl.getTimestamp() < i - 30) {
/* 277:175 */               localArrayList.add(localTransactionImpl);
/* 278:    */             }
/* 279:    */           }
/* 280:179 */           if (localArrayList.size() > 0) {
/* 281:180 */             Peers.sendToSomePeers(localArrayList);
/* 282:    */           }
/* 283:    */         }
/* 284:    */         catch (Exception localException)
/* 285:    */         {
/* 286:184 */           Logger.logDebugMessage("Error in transaction re-broadcasting thread", localException);
/* 287:    */         }
/* 288:    */       }
/* 289:    */       catch (Throwable localThrowable)
/* 290:    */       {
/* 291:187 */         Logger.logMessage("CRITICAL ERROR. PLEASE REPORT TO THE DEVELOPERS.\n" + localThrowable.toString());
/* 292:188 */         localThrowable.printStackTrace();
/* 293:189 */         System.exit(1);
/* 294:    */       }
/* 295:    */     }
/* 296:    */   };
/* 297:196 */   private final Runnable processTransactionsThread = new Runnable()
/* 298:    */   {
/* 299:    */     private final JSONStreamAware getUnconfirmedTransactionsRequest;
/* 300:    */     
/* 301:    */     /* Error */
/* 302:    */     public void run()
/* 303:    */     {
/* 304:    */       // Byte code:
/* 305:    */       //   0: invokestatic 10	nxt/BlockchainImpl:getInstance	()Lnxt/BlockchainImpl;
/* 306:    */       //   3: dup
/* 307:    */       //   4: astore_1
/* 308:    */       //   5: monitorenter
/* 309:    */       //   6: aload_0
/* 310:    */       //   7: getfield 1	nxt/TransactionProcessorImpl$5:this$0	Lnxt/TransactionProcessorImpl;
/* 311:    */       //   10: aload_0
/* 312:    */       //   11: getfield 1	nxt/TransactionProcessorImpl$5:this$0	Lnxt/TransactionProcessorImpl;
/* 313:    */       //   14: invokestatic 11	nxt/TransactionProcessorImpl:access$200	(Lnxt/TransactionProcessorImpl;)Ljava/util/Set;
/* 314:    */       //   17: iconst_0
/* 315:    */       //   18: invokevirtual 12	nxt/TransactionProcessorImpl:processTransactions	(Ljava/util/Collection;Z)Ljava/util/List;
/* 316:    */       //   21: pop
/* 317:    */       //   22: aload_0
/* 318:    */       //   23: getfield 1	nxt/TransactionProcessorImpl$5:this$0	Lnxt/TransactionProcessorImpl;
/* 319:    */       //   26: invokestatic 11	nxt/TransactionProcessorImpl:access$200	(Lnxt/TransactionProcessorImpl;)Ljava/util/Set;
/* 320:    */       //   29: invokeinterface 13 1 0
/* 321:    */       //   34: aload_1
/* 322:    */       //   35: monitorexit
/* 323:    */       //   36: goto +8 -> 44
/* 324:    */       //   39: astore_2
/* 325:    */       //   40: aload_1
/* 326:    */       //   41: monitorexit
/* 327:    */       //   42: aload_2
/* 328:    */       //   43: athrow
/* 329:    */       //   44: getstatic 14	nxt/peer/Peer$State:CONNECTED	Lnxt/peer/Peer$State;
/* 330:    */       //   47: iconst_1
/* 331:    */       //   48: invokestatic 15	nxt/peer/Peers:getAnyPeer	(Lnxt/peer/Peer$State;Z)Lnxt/peer/Peer;
/* 332:    */       //   51: astore_1
/* 333:    */       //   52: aload_1
/* 334:    */       //   53: ifnonnull +4 -> 57
/* 335:    */       //   56: return
/* 336:    */       //   57: aload_1
/* 337:    */       //   58: aload_0
/* 338:    */       //   59: getfield 9	nxt/TransactionProcessorImpl$5:getUnconfirmedTransactionsRequest	Lorg/json/simple/JSONStreamAware;
/* 339:    */       //   62: invokeinterface 16 2 0
/* 340:    */       //   67: astore_2
/* 341:    */       //   68: aload_2
/* 342:    */       //   69: ifnonnull +4 -> 73
/* 343:    */       //   72: return
/* 344:    */       //   73: aload_2
/* 345:    */       //   74: ldc 17
/* 346:    */       //   76: invokevirtual 18	org/json/simple/JSONObject:get	(Ljava/lang/Object;)Ljava/lang/Object;
/* 347:    */       //   79: checkcast 19	org/json/simple/JSONArray
/* 348:    */       //   82: astore_3
/* 349:    */       //   83: aload_3
/* 350:    */       //   84: ifnull +10 -> 94
/* 351:    */       //   87: aload_3
/* 352:    */       //   88: invokevirtual 20	org/json/simple/JSONArray:size	()I
/* 353:    */       //   91: ifne +4 -> 95
/* 354:    */       //   94: return
/* 355:    */       //   95: aload_0
/* 356:    */       //   96: getfield 1	nxt/TransactionProcessorImpl$5:this$0	Lnxt/TransactionProcessorImpl;
/* 357:    */       //   99: aload_3
/* 358:    */       //   100: invokestatic 21	nxt/TransactionProcessorImpl:access$300	(Lnxt/TransactionProcessorImpl;Lorg/json/simple/JSONArray;)V
/* 359:    */       //   103: goto +13 -> 116
/* 360:    */       //   106: astore 4
/* 361:    */       //   108: aload_1
/* 362:    */       //   109: aload 4
/* 363:    */       //   111: invokeinterface 24 2 0
/* 364:    */       //   116: goto +10 -> 126
/* 365:    */       //   119: astore_1
/* 366:    */       //   120: ldc 26
/* 367:    */       //   122: aload_1
/* 368:    */       //   123: invokestatic 27	nxt/util/Logger:logDebugMessage	(Ljava/lang/String;Ljava/lang/Exception;)V
/* 369:    */       //   126: goto +37 -> 163
/* 370:    */       //   129: astore_1
/* 371:    */       //   130: new 29	java/lang/StringBuilder
/* 372:    */       //   133: dup
/* 373:    */       //   134: invokespecial 30	java/lang/StringBuilder:<init>	()V
/* 374:    */       //   137: ldc 31
/* 375:    */       //   139: invokevirtual 32	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 376:    */       //   142: aload_1
/* 377:    */       //   143: invokevirtual 33	java/lang/Throwable:toString	()Ljava/lang/String;
/* 378:    */       //   146: invokevirtual 32	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 379:    */       //   149: invokevirtual 34	java/lang/StringBuilder:toString	()Ljava/lang/String;
/* 380:    */       //   152: invokestatic 35	nxt/util/Logger:logMessage	(Ljava/lang/String;)V
/* 381:    */       //   155: aload_1
/* 382:    */       //   156: invokevirtual 36	java/lang/Throwable:printStackTrace	()V
/* 383:    */       //   159: iconst_1
/* 384:    */       //   160: invokestatic 37	java/lang/System:exit	(I)V
/* 385:    */       //   163: return
/* 386:    */       // Line number table:
/* 387:    */       //   Java source line #209	-> byte code offset #0
/* 388:    */       //   Java source line #210	-> byte code offset #6
/* 389:    */       //   Java source line #211	-> byte code offset #22
/* 390:    */       //   Java source line #212	-> byte code offset #34
/* 391:    */       //   Java source line #213	-> byte code offset #44
/* 392:    */       //   Java source line #214	-> byte code offset #52
/* 393:    */       //   Java source line #215	-> byte code offset #56
/* 394:    */       //   Java source line #217	-> byte code offset #57
/* 395:    */       //   Java source line #218	-> byte code offset #68
/* 396:    */       //   Java source line #219	-> byte code offset #72
/* 397:    */       //   Java source line #221	-> byte code offset #73
/* 398:    */       //   Java source line #222	-> byte code offset #83
/* 399:    */       //   Java source line #223	-> byte code offset #94
/* 400:    */       //   Java source line #226	-> byte code offset #95
/* 401:    */       //   Java source line #229	-> byte code offset #103
/* 402:    */       //   Java source line #227	-> byte code offset #106
/* 403:    */       //   Java source line #228	-> byte code offset #108
/* 404:    */       //   Java source line #232	-> byte code offset #116
/* 405:    */       //   Java source line #230	-> byte code offset #119
/* 406:    */       //   Java source line #231	-> byte code offset #120
/* 407:    */       //   Java source line #237	-> byte code offset #126
/* 408:    */       //   Java source line #233	-> byte code offset #129
/* 409:    */       //   Java source line #234	-> byte code offset #130
/* 410:    */       //   Java source line #235	-> byte code offset #155
/* 411:    */       //   Java source line #236	-> byte code offset #159
/* 412:    */       //   Java source line #238	-> byte code offset #163
/* 413:    */       // Local variable table:
/* 414:    */       //   start	length	slot	name	signature
/* 415:    */       //   0	164	0	this	5
/* 416:    */       //   4	105	1	Ljava/lang/Object;	Object
/* 417:    */       //   119	4	1	localException	Exception
/* 418:    */       //   129	27	1	localThrowable	Throwable
/* 419:    */       //   39	4	2	localObject1	Object
/* 420:    */       //   67	7	2	localJSONObject	JSONObject
/* 421:    */       //   82	18	3	localJSONArray	JSONArray
/* 422:    */       //   106	4	4	localValidationException	NxtException.ValidationException
/* 423:    */       // Exception table:
/* 424:    */       //   from	to	target	type
/* 425:    */       //   6	36	39	finally
/* 426:    */       //   39	42	39	finally
/* 427:    */       //   95	103	106	nxt/NxtException$ValidationException
/* 428:    */       //   95	103	106	java/lang/RuntimeException
/* 429:    */       //   0	56	119	java/lang/Exception
/* 430:    */       //   57	72	119	java/lang/Exception
/* 431:    */       //   73	94	119	java/lang/Exception
/* 432:    */       //   95	116	119	java/lang/Exception
/* 433:    */       //   0	56	129	java/lang/Throwable
/* 434:    */       //   57	72	129	java/lang/Throwable
/* 435:    */       //   73	94	129	java/lang/Throwable
/* 436:    */       //   95	126	129	java/lang/Throwable
/* 437:    */     }
/* 438:    */   };
/* 439:    */   
/* 440:    */   private TransactionProcessorImpl()
/* 441:    */   {
/* 442:243 */     ThreadPool.scheduleThread("ProcessTransactions", this.processTransactionsThread, 5);
/* 443:244 */     ThreadPool.scheduleThread("RemoveUnconfirmedTransactions", this.removeUnconfirmedTransactionsThread, 1);
/* 444:245 */     if (enableTransactionRebroadcasting)
/* 445:    */     {
/* 446:246 */       ThreadPool.scheduleThread("RebroadcastTransactions", this.rebroadcastTransactionsThread, 60);
/* 447:247 */       ThreadPool.runAfterStart(new Runnable()
/* 448:    */       {
/* 449:    */         public void run()
/* 450:    */         {
/* 451:250 */           DbIterator localDbIterator = TransactionProcessorImpl.this.getAllUnconfirmedTransactions();Object localObject1 = null;
/* 452:    */           try
/* 453:    */           {
/* 454:251 */             for (TransactionImpl localTransactionImpl : localDbIterator) {
/* 455:252 */               TransactionProcessorImpl.this.nonBroadcastedTransactions.add(localTransactionImpl);
/* 456:    */             }
/* 457:    */           }
/* 458:    */           catch (Throwable localThrowable2)
/* 459:    */           {
/* 460:250 */             localObject1 = localThrowable2;throw localThrowable2;
/* 461:    */           }
/* 462:    */           finally
/* 463:    */           {
/* 464:254 */             if (localDbIterator != null) {
/* 465:254 */               if (localObject1 != null) {
/* 466:    */                 try
/* 467:    */                 {
/* 468:254 */                   localDbIterator.close();
/* 469:    */                 }
/* 470:    */                 catch (Throwable localThrowable3)
/* 471:    */                 {
/* 472:254 */                   ((Throwable)localObject1).addSuppressed(localThrowable3);
/* 473:    */                 }
/* 474:    */               } else {
/* 475:254 */                 localDbIterator.close();
/* 476:    */               }
/* 477:    */             }
/* 478:    */           }
/* 479:    */         }
/* 480:    */       });
/* 481:    */     }
/* 482:    */   }
/* 483:    */   
/* 484:    */   public boolean addListener(Listener<List<? extends Transaction>> paramListener, TransactionProcessor.Event paramEvent)
/* 485:    */   {
/* 486:262 */     return this.transactionListeners.addListener(paramListener, paramEvent);
/* 487:    */   }
/* 488:    */   
/* 489:    */   public boolean removeListener(Listener<List<? extends Transaction>> paramListener, TransactionProcessor.Event paramEvent)
/* 490:    */   {
/* 491:267 */     return this.transactionListeners.removeListener(paramListener, paramEvent);
/* 492:    */   }
/* 493:    */   
/* 494:    */   void notifyListeners(List<? extends Transaction> paramList, TransactionProcessor.Event paramEvent)
/* 495:    */   {
/* 496:271 */     this.transactionListeners.notify(paramList, paramEvent);
/* 497:    */   }
/* 498:    */   
/* 499:    */   public DbIterator<TransactionImpl> getAllUnconfirmedTransactions()
/* 500:    */   {
/* 501:276 */     return this.unconfirmedTransactionTable.getAll(0, -1);
/* 502:    */   }
/* 503:    */   
/* 504:    */   public Transaction getUnconfirmedTransaction(long paramLong)
/* 505:    */   {
/* 506:281 */     return (Transaction)this.unconfirmedTransactionTable.get(this.unconfirmedTransactionDbKeyFactory.newKey(paramLong));
/* 507:    */   }
/* 508:    */   
/* 509:    */   public Transaction.Builder newTransactionBuilder(byte[] paramArrayOfByte, long paramLong1, long paramLong2, short paramShort, Attachment paramAttachment)
/* 510:    */   {
/* 511:286 */     byte b = (byte)getTransactionVersion(Nxt.getBlockchain().getHeight());
/* 512:287 */     int i = Nxt.getEpochTime();
/* 513:288 */     TransactionImpl.BuilderImpl localBuilderImpl = new TransactionImpl.BuilderImpl(b, paramArrayOfByte, paramLong1, paramLong2, i, paramShort, (Attachment.AbstractAttachment)paramAttachment);
/* 514:290 */     if (b > 0)
/* 515:    */     {
/* 516:291 */       Block localBlock = EconomicClustering.getECBlock(i);
/* 517:292 */       localBuilderImpl.ecBlockHeight(localBlock.getHeight());
/* 518:293 */       localBuilderImpl.ecBlockId(localBlock.getId());
/* 519:    */     }
/* 520:295 */     return localBuilderImpl;
/* 521:    */   }
/* 522:    */   
/* 523:    */   public void broadcast(Transaction paramTransaction)
/* 524:    */     throws NxtException.ValidationException
/* 525:    */   {
/* 526:300 */     if (!paramTransaction.verifySignature()) {
/* 527:301 */       throw new NxtException.NotValidException("Transaction signature verification failed");
/* 528:    */     }
/* 529:    */     List localList;
/* 530:304 */     synchronized (BlockchainImpl.getInstance())
/* 531:    */     {
/* 532:305 */       if (TransactionDb.hasTransaction(paramTransaction.getId()))
/* 533:    */       {
/* 534:306 */         Logger.logMessage("Transaction " + paramTransaction.getStringId() + " already in blockchain, will not broadcast again");
/* 535:307 */         return;
/* 536:    */       }
/* 537:309 */       if (this.unconfirmedTransactionTable.get(((TransactionImpl)paramTransaction).getDbKey()) != null)
/* 538:    */       {
/* 539:310 */         if (enableTransactionRebroadcasting)
/* 540:    */         {
/* 541:311 */           this.nonBroadcastedTransactions.add((TransactionImpl)paramTransaction);
/* 542:312 */           Logger.logMessage("Transaction " + paramTransaction.getStringId() + " already in unconfirmed pool, will re-broadcast");
/* 543:    */         }
/* 544:    */         else
/* 545:    */         {
/* 546:314 */           Logger.logMessage("Transaction " + paramTransaction.getStringId() + " already in unconfirmed pool, will not broadcast again");
/* 547:    */         }
/* 548:316 */         return;
/* 549:    */       }
/* 550:318 */       localList = processTransactions(Collections.singleton((TransactionImpl)paramTransaction), true);
/* 551:    */     }
/* 552:320 */     if (localList.contains(paramTransaction))
/* 553:    */     {
/* 554:321 */       if (enableTransactionRebroadcasting) {
/* 555:322 */         this.nonBroadcastedTransactions.add((TransactionImpl)paramTransaction);
/* 556:    */       }
/* 557:324 */       Logger.logDebugMessage("Accepted new transaction " + paramTransaction.getStringId());
/* 558:    */     }
/* 559:    */     else
/* 560:    */     {
/* 561:326 */       Logger.logDebugMessage("Could not accept new transaction " + paramTransaction.getStringId());
/* 562:327 */       throw new NxtException.NotValidException("Invalid transaction " + paramTransaction.getStringId());
/* 563:    */     }
/* 564:    */   }
/* 565:    */   
/* 566:    */   public void processPeerTransactions(JSONObject paramJSONObject)
/* 567:    */     throws NxtException.ValidationException
/* 568:    */   {
/* 569:333 */     JSONArray localJSONArray = (JSONArray)paramJSONObject.get("transactions");
/* 570:334 */     processPeerTransactions(localJSONArray);
/* 571:    */   }
/* 572:    */   
/* 573:    */   public Transaction parseTransaction(byte[] paramArrayOfByte)
/* 574:    */     throws NxtException.ValidationException
/* 575:    */   {
/* 576:339 */     return TransactionImpl.parseTransaction(paramArrayOfByte);
/* 577:    */   }
/* 578:    */   
/* 579:    */   public TransactionImpl parseTransaction(JSONObject paramJSONObject)
/* 580:    */     throws NxtException.NotValidException
/* 581:    */   {
/* 582:344 */     return TransactionImpl.parseTransaction(paramJSONObject);
/* 583:    */   }
/* 584:    */   
/* 585:    */   public void clearUnconfirmedTransactions()
/* 586:    */   {
/* 587:349 */     synchronized ()
/* 588:    */     {
/* 589:350 */       ArrayList localArrayList = new ArrayList();
/* 590:    */       try
/* 591:    */       {
/* 592:352 */         Db.beginTransaction();
/* 593:353 */         DbIterator localDbIterator = getAllUnconfirmedTransactions();Object localObject1 = null;
/* 594:    */         try
/* 595:    */         {
/* 596:354 */           for (TransactionImpl localTransactionImpl : localDbIterator)
/* 597:    */           {
/* 598:355 */             localTransactionImpl.undoUnconfirmed();
/* 599:356 */             localArrayList.add(localTransactionImpl);
/* 600:    */           }
/* 601:    */         }
/* 602:    */         catch (Throwable localThrowable2)
/* 603:    */         {
/* 604:353 */           localObject1 = localThrowable2;throw localThrowable2;
/* 605:    */         }
/* 606:    */         finally
/* 607:    */         {
/* 608:358 */           if (localDbIterator != null) {
/* 609:358 */             if (localObject1 != null) {
/* 610:    */               try
/* 611:    */               {
/* 612:358 */                 localDbIterator.close();
/* 613:    */               }
/* 614:    */               catch (Throwable localThrowable3)
/* 615:    */               {
/* 616:358 */                 ((Throwable)localObject1).addSuppressed(localThrowable3);
/* 617:    */               }
/* 618:    */             } else {
/* 619:358 */               localDbIterator.close();
/* 620:    */             }
/* 621:    */           }
/* 622:    */         }
/* 623:359 */         this.unconfirmedTransactionTable.truncate();
/* 624:360 */         Db.commitTransaction();
/* 625:    */       }
/* 626:    */       catch (Exception localException)
/* 627:    */       {
/* 628:362 */         Logger.logErrorMessage(localException.toString(), localException);
/* 629:    */         
/* 630:364 */         throw localException;
/* 631:    */       }
/* 632:    */       finally
/* 633:    */       {
/* 634:366 */         Db.endTransaction();
/* 635:    */       }
/* 636:368 */       this.transactionListeners.notify(localArrayList, TransactionProcessor.Event.REMOVED_UNCONFIRMED_TRANSACTIONS);
/* 637:    */     }
/* 638:    */   }
/* 639:    */   
/* 640:    */   void requeueAllUnconfirmedTransactions()
/* 641:    */   {
/* 642:373 */     ArrayList localArrayList = new ArrayList();
/* 643:374 */     DbIterator localDbIterator = getAllUnconfirmedTransactions();Object localObject1 = null;
/* 644:    */     try
/* 645:    */     {
/* 646:375 */       for (TransactionImpl localTransactionImpl : localDbIterator)
/* 647:    */       {
/* 648:376 */         localTransactionImpl.undoUnconfirmed();
/* 649:377 */         localArrayList.add(localTransactionImpl);
/* 650:378 */         this.lostTransactions.add(localTransactionImpl);
/* 651:    */       }
/* 652:    */     }
/* 653:    */     catch (Throwable localThrowable2)
/* 654:    */     {
/* 655:374 */       localObject1 = localThrowable2;throw localThrowable2;
/* 656:    */     }
/* 657:    */     finally
/* 658:    */     {
/* 659:380 */       if (localDbIterator != null) {
/* 660:380 */         if (localObject1 != null) {
/* 661:    */           try
/* 662:    */           {
/* 663:380 */             localDbIterator.close();
/* 664:    */           }
/* 665:    */           catch (Throwable localThrowable3)
/* 666:    */           {
/* 667:380 */             ((Throwable)localObject1).addSuppressed(localThrowable3);
/* 668:    */           }
/* 669:    */         } else {
/* 670:380 */           localDbIterator.close();
/* 671:    */         }
/* 672:    */       }
/* 673:    */     }
/* 674:381 */     this.unconfirmedTransactionTable.truncate();
/* 675:382 */     this.transactionListeners.notify(localArrayList, TransactionProcessor.Event.REMOVED_UNCONFIRMED_TRANSACTIONS);
/* 676:    */   }
/* 677:    */   
/* 678:    */   void removeUnconfirmedTransaction(TransactionImpl paramTransactionImpl)
/* 679:    */   {
/* 680:386 */     if (!Db.isInTransaction())
/* 681:    */     {
/* 682:    */       try
/* 683:    */       {
/* 684:388 */         Db.beginTransaction();
/* 685:389 */         removeUnconfirmedTransaction(paramTransactionImpl);
/* 686:390 */         Db.commitTransaction();
/* 687:    */       }
/* 688:    */       catch (Exception localException)
/* 689:    */       {
/* 690:392 */         Logger.logErrorMessage(localException.toString(), localException);
/* 691:    */         
/* 692:394 */         throw localException;
/* 693:    */       }
/* 694:    */       finally
/* 695:    */       {
/* 696:396 */         Db.endTransaction();
/* 697:    */       }
/* 698:398 */       return;
/* 699:    */     }
/* 700:    */     try
/* 701:    */     {
/* 702:400 */       Connection localConnection = Db.getConnection();Object localObject2 = null;
/* 703:    */       try
/* 704:    */       {
/* 705:401 */         PreparedStatement localPreparedStatement = localConnection.prepareStatement("DELETE FROM unconfirmed_transaction WHERE id = ?");Object localObject3 = null;
/* 706:    */         try
/* 707:    */         {
/* 708:402 */           localPreparedStatement.setLong(1, paramTransactionImpl.getId());
/* 709:403 */           int i = localPreparedStatement.executeUpdate();
/* 710:404 */           if (i > 0)
/* 711:    */           {
/* 712:405 */             paramTransactionImpl.undoUnconfirmed();
/* 713:406 */             this.transactionListeners.notify(Collections.singletonList(paramTransactionImpl), TransactionProcessor.Event.REMOVED_UNCONFIRMED_TRANSACTIONS);
/* 714:    */           }
/* 715:    */         }
/* 716:    */         catch (Throwable localThrowable4)
/* 717:    */         {
/* 718:400 */           localObject3 = localThrowable4;throw localThrowable4;
/* 719:    */         }
/* 720:    */         finally {}
/* 721:    */       }
/* 722:    */       catch (Throwable localThrowable2)
/* 723:    */       {
/* 724:400 */         localObject2 = localThrowable2;throw localThrowable2;
/* 725:    */       }
/* 726:    */       finally
/* 727:    */       {
/* 728:408 */         if (localConnection != null) {
/* 729:408 */           if (localObject2 != null) {
/* 730:    */             try
/* 731:    */             {
/* 732:408 */               localConnection.close();
/* 733:    */             }
/* 734:    */             catch (Throwable localThrowable6)
/* 735:    */             {
/* 736:408 */               ((Throwable)localObject2).addSuppressed(localThrowable6);
/* 737:    */             }
/* 738:    */           } else {
/* 739:408 */             localConnection.close();
/* 740:    */           }
/* 741:    */         }
/* 742:    */       }
/* 743:    */     }
/* 744:    */     catch (SQLException localSQLException)
/* 745:    */     {
/* 746:409 */       Logger.logErrorMessage(localSQLException.toString(), localSQLException);
/* 747:410 */       throw new RuntimeException(localSQLException.toString(), localSQLException);
/* 748:    */     }
/* 749:    */   }
/* 750:    */   
/* 751:    */   int getTransactionVersion(int paramInt)
/* 752:    */   {
/* 753:415 */     return paramInt < 11800 ? 0 : 1;
/* 754:    */   }
/* 755:    */   
/* 756:    */   void processLater(Collection<TransactionImpl> paramCollection)
/* 757:    */   {
/* 758:419 */     synchronized ()
/* 759:    */     {
/* 760:420 */       for (TransactionImpl localTransactionImpl : paramCollection) {
/* 761:421 */         this.lostTransactions.add(localTransactionImpl);
/* 762:    */       }
/* 763:    */     }
/* 764:    */   }
/* 765:    */   
/* 766:    */   private void processPeerTransactions(JSONArray paramJSONArray)
/* 767:    */     throws NxtException.ValidationException
/* 768:    */   {
/* 769:427 */     if ((Nxt.getBlockchain().getLastBlock().getTimestamp() < Nxt.getEpochTime() - 86400) && (!testUnconfirmedTransactions)) {
/* 770:428 */       return;
/* 771:    */     }
/* 772:430 */     if (Nxt.getBlockchain().getHeight() <= 0) {
/* 773:431 */       return;
/* 774:    */     }
/* 775:433 */     ArrayList localArrayList = new ArrayList();
/* 776:434 */     for (Object localObject : paramJSONArray) {
/* 777:    */       try
/* 778:    */       {
/* 779:436 */         TransactionImpl localTransactionImpl = parseTransaction((JSONObject)localObject);
/* 780:437 */         localTransactionImpl.validate();
/* 781:438 */         if (EconomicClustering.verifyFork(localTransactionImpl)) {
/* 782:444 */           localArrayList.add(localTransactionImpl);
/* 783:    */         }
/* 784:    */       }
/* 785:    */       catch (NxtException.NotCurrentlyValidException localNotCurrentlyValidException) {}catch (NxtException.NotValidException localNotValidException)
/* 786:    */       {
/* 787:447 */         Logger.logDebugMessage("Invalid transaction from peer: " + ((JSONObject)localObject).toJSONString());
/* 788:448 */         throw localNotValidException;
/* 789:    */       }
/* 790:    */     }
/* 791:451 */     processTransactions(localArrayList, true);
/* 792:452 */     this.nonBroadcastedTransactions.removeAll(localArrayList);
/* 793:    */   }
/* 794:    */   
/* 795:    */   List<Transaction> processTransactions(Collection<TransactionImpl> paramCollection, boolean paramBoolean)
/* 796:    */   {
/* 797:456 */     if (paramCollection.isEmpty()) {
/* 798:457 */       return Collections.emptyList();
/* 799:    */     }
/* 800:459 */     ArrayList localArrayList1 = new ArrayList();
/* 801:460 */     ArrayList localArrayList2 = new ArrayList();
/* 802:461 */     ArrayList localArrayList3 = new ArrayList();
/* 803:463 */     for (TransactionImpl localTransactionImpl : paramCollection) {
/* 804:    */       try
/* 805:    */       {
/* 806:467 */         int i = Nxt.getEpochTime();
/* 807:468 */         if ((localTransactionImpl.getTimestamp() > i + 15) || (localTransactionImpl.getExpiration() < i) || (localTransactionImpl.getDeadline() <= 1440)) {
/* 808:476 */           synchronized (BlockchainImpl.getInstance())
/* 809:    */           {
/* 810:    */             try
/* 811:    */             {
/* 812:478 */               Db.beginTransaction();
/* 813:479 */               if (Nxt.getBlockchain().getHeight() < 0)
/* 814:    */               {
/* 815:514 */                 Db.endTransaction(); break;
/* 816:    */               }
/* 817:483 */               if ((TransactionDb.hasTransaction(localTransactionImpl.getId())) || (this.unconfirmedTransactionTable.get(localTransactionImpl.getDbKey()) != null))
/* 818:    */               {
/* 819:514 */                 Db.endTransaction();
/* 820:    */               }
/* 821:487 */               else if (!localTransactionImpl.verifySignature())
/* 822:    */               {
/* 823:488 */                 if (Account.getAccount(localTransactionImpl.getSenderId()) != null) {
/* 824:489 */                   Logger.logDebugMessage("Transaction " + localTransactionImpl.getJSONObject().toJSONString() + " failed to verify");
/* 825:    */                 }
/* 826:514 */                 Db.endTransaction();
/* 827:    */               }
/* 828:    */               else
/* 829:    */               {
/* 830:494 */                 if (localTransactionImpl.applyUnconfirmed())
/* 831:    */                 {
/* 832:495 */                   if (paramBoolean) {
/* 833:496 */                     if (this.nonBroadcastedTransactions.contains(localTransactionImpl))
/* 834:    */                     {
/* 835:497 */                       Logger.logDebugMessage("Received back transaction " + localTransactionImpl.getStringId() + " that we generated, will not forward to peers");
/* 836:    */                       
/* 837:499 */                       this.nonBroadcastedTransactions.remove(localTransactionImpl);
/* 838:    */                     }
/* 839:    */                     else
/* 840:    */                     {
/* 841:501 */                       localArrayList1.add(localTransactionImpl);
/* 842:    */                     }
/* 843:    */                   }
/* 844:504 */                   this.unconfirmedTransactionTable.insert(localTransactionImpl);
/* 845:505 */                   localArrayList2.add(localTransactionImpl);
/* 846:    */                 }
/* 847:    */                 else
/* 848:    */                 {
/* 849:507 */                   localArrayList3.add(localTransactionImpl);
/* 850:    */                 }
/* 851:509 */                 Db.commitTransaction();
/* 852:    */               }
/* 853:    */             }
/* 854:    */             catch (Exception localException)
/* 855:    */             {
/* 856:512 */               throw localException;
/* 857:    */             }
/* 858:    */             finally
/* 859:    */             {
/* 860:514 */               Db.endTransaction();
/* 861:    */             }
/* 862:    */           }
/* 863:    */         }
/* 864:    */       }
/* 865:    */       catch (RuntimeException localRuntimeException)
/* 866:    */       {
/* 867:518 */         Logger.logMessage("Error processing transaction", localRuntimeException);
/* 868:    */       }
/* 869:    */     }
/* 870:523 */     if (localArrayList1.size() > 0) {
/* 871:524 */       Peers.sendToSomePeers(localArrayList1);
/* 872:    */     }
/* 873:527 */     if (localArrayList2.size() > 0) {
/* 874:528 */       this.transactionListeners.notify(localArrayList2, TransactionProcessor.Event.ADDED_UNCONFIRMED_TRANSACTIONS);
/* 875:    */     }
/* 876:530 */     if (localArrayList3.size() > 0) {
/* 877:531 */       this.transactionListeners.notify(localArrayList3, TransactionProcessor.Event.ADDED_DOUBLESPENDING_TRANSACTIONS);
/* 878:    */     }
/* 879:533 */     return localArrayList2;
/* 880:    */   }
/* 881:    */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.TransactionProcessorImpl
 * JD-Core Version:    0.7.1
 */