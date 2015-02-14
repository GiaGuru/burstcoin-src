/*   1:    */ package nxt;
/*   2:    */ 
/*   3:    */ import java.sql.Connection;
/*   4:    */ import java.sql.PreparedStatement;
/*   5:    */ import java.sql.ResultSet;
/*   6:    */ import java.sql.SQLException;
/*   7:    */ import nxt.db.DbClause;
/*   8:    */ import nxt.db.DbClause.LongClause;
/*   9:    */ import nxt.db.DbIterator;
/*  10:    */ import nxt.db.DbKey;
/*  11:    */ import nxt.db.DbKey.Factory;
/*  12:    */ import nxt.db.DbKey.LongKeyFactory;
/*  13:    */ import nxt.db.VersionedEntityDbTable;
/*  14:    */ import nxt.util.Convert;
/*  15:    */ 
/*  16:    */ public abstract class Order
/*  17:    */ {
/*  18:    */   private final long id;
/*  19:    */   private final long accountId;
/*  20:    */   private final long assetId;
/*  21:    */   private final long priceNQT;
/*  22:    */   private final int creationHeight;
/*  23:    */   private long quantityQNT;
/*  24:    */   
/*  25:    */   private static void matchOrders(long paramLong)
/*  26:    */   {
/*  27:    */     Ask localAsk;
/*  28:    */     Bid localBid;
/*  29: 23 */     while (((localAsk = Ask.getNextOrder(paramLong)) != null) && ((localBid = Bid.getNextOrder(paramLong)) != null))
/*  30:    */     {
/*  31: 25 */       if (localAsk.getPriceNQT() > localBid.getPriceNQT()) {
/*  32:    */         break;
/*  33:    */       }
/*  34: 30 */       Trade localTrade = Trade.addTrade(paramLong, Nxt.getBlockchain().getLastBlock(), localAsk, localBid);
/*  35:    */       
/*  36: 32 */       localAsk.updateQuantityQNT(Convert.safeSubtract(localAsk.getQuantityQNT(), localTrade.getQuantityQNT()));
/*  37: 33 */       Account localAccount1 = Account.getAccount(localAsk.getAccountId());
/*  38: 34 */       localAccount1.addToBalanceAndUnconfirmedBalanceNQT(Convert.safeMultiply(localTrade.getQuantityQNT(), localTrade.getPriceNQT()));
/*  39: 35 */       localAccount1.addToAssetBalanceQNT(paramLong, -localTrade.getQuantityQNT());
/*  40:    */       
/*  41: 37 */       localBid.updateQuantityQNT(Convert.safeSubtract(localBid.getQuantityQNT(), localTrade.getQuantityQNT()));
/*  42: 38 */       Account localAccount2 = Account.getAccount(localBid.getAccountId());
/*  43: 39 */       localAccount2.addToAssetAndUnconfirmedAssetBalanceQNT(paramLong, localTrade.getQuantityQNT());
/*  44: 40 */       localAccount2.addToBalanceNQT(-Convert.safeMultiply(localTrade.getQuantityQNT(), localTrade.getPriceNQT()));
/*  45: 41 */       localAccount2.addToUnconfirmedBalanceNQT(Convert.safeMultiply(localTrade.getQuantityQNT(), localBid.getPriceNQT() - localTrade.getPriceNQT()));
/*  46:    */     }
/*  47:    */   }
/*  48:    */   
/*  49:    */   static void init()
/*  50:    */   {
/*  51: 48 */     Ask.init();
/*  52: 49 */     Bid.init();
/*  53:    */   }
/*  54:    */   
/*  55:    */   private Order(Transaction paramTransaction, Attachment.ColoredCoinsOrderPlacement paramColoredCoinsOrderPlacement)
/*  56:    */   {
/*  57: 62 */     this.id = paramTransaction.getId();
/*  58: 63 */     this.accountId = paramTransaction.getSenderId();
/*  59: 64 */     this.assetId = paramColoredCoinsOrderPlacement.getAssetId();
/*  60: 65 */     this.quantityQNT = paramColoredCoinsOrderPlacement.getQuantityQNT();
/*  61: 66 */     this.priceNQT = paramColoredCoinsOrderPlacement.getPriceNQT();
/*  62: 67 */     this.creationHeight = paramTransaction.getHeight();
/*  63:    */   }
/*  64:    */   
/*  65:    */   private Order(ResultSet paramResultSet)
/*  66:    */     throws SQLException
/*  67:    */   {
/*  68: 71 */     this.id = paramResultSet.getLong("id");
/*  69: 72 */     this.accountId = paramResultSet.getLong("account_id");
/*  70: 73 */     this.assetId = paramResultSet.getLong("asset_id");
/*  71: 74 */     this.priceNQT = paramResultSet.getLong("price");
/*  72: 75 */     this.quantityQNT = paramResultSet.getLong("quantity");
/*  73: 76 */     this.creationHeight = paramResultSet.getInt("creation_height");
/*  74:    */   }
/*  75:    */   
/*  76:    */   private void save(Connection paramConnection, String paramString)
/*  77:    */     throws SQLException
/*  78:    */   {
/*  79: 80 */     PreparedStatement localPreparedStatement = paramConnection.prepareStatement("MERGE INTO " + paramString + " (id, account_id, asset_id, " + "price, quantity, creation_height, height, latest) KEY (id, height) VALUES (?, ?, ?, ?, ?, ?, ?, TRUE)");Object localObject1 = null;
/*  80:    */     try
/*  81:    */     {
/*  82: 82 */       int i = 0;
/*  83: 83 */       localPreparedStatement.setLong(++i, getId());
/*  84: 84 */       localPreparedStatement.setLong(++i, getAccountId());
/*  85: 85 */       localPreparedStatement.setLong(++i, getAssetId());
/*  86: 86 */       localPreparedStatement.setLong(++i, getPriceNQT());
/*  87: 87 */       localPreparedStatement.setLong(++i, getQuantityQNT());
/*  88: 88 */       localPreparedStatement.setInt(++i, getHeight());
/*  89: 89 */       localPreparedStatement.setInt(++i, Nxt.getBlockchain().getHeight());
/*  90: 90 */       localPreparedStatement.executeUpdate();
/*  91:    */     }
/*  92:    */     catch (Throwable localThrowable2)
/*  93:    */     {
/*  94: 80 */       localObject1 = localThrowable2;throw localThrowable2;
/*  95:    */     }
/*  96:    */     finally
/*  97:    */     {
/*  98: 91 */       if (localPreparedStatement != null) {
/*  99: 91 */         if (localObject1 != null) {
/* 100:    */           try
/* 101:    */           {
/* 102: 91 */             localPreparedStatement.close();
/* 103:    */           }
/* 104:    */           catch (Throwable localThrowable3)
/* 105:    */           {
/* 106: 91 */             ((Throwable)localObject1).addSuppressed(localThrowable3);
/* 107:    */           }
/* 108:    */         } else {
/* 109: 91 */           localPreparedStatement.close();
/* 110:    */         }
/* 111:    */       }
/* 112:    */     }
/* 113:    */   }
/* 114:    */   
/* 115:    */   public long getId()
/* 116:    */   {
/* 117: 95 */     return this.id;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public long getAccountId()
/* 121:    */   {
/* 122: 99 */     return this.accountId;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public long getAssetId()
/* 126:    */   {
/* 127:103 */     return this.assetId;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public long getPriceNQT()
/* 131:    */   {
/* 132:107 */     return this.priceNQT;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public final long getQuantityQNT()
/* 136:    */   {
/* 137:111 */     return this.quantityQNT;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public int getHeight()
/* 141:    */   {
/* 142:115 */     return this.creationHeight;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public String toString()
/* 146:    */   {
/* 147:120 */     return getClass().getSimpleName() + " id: " + Convert.toUnsignedLong(this.id) + " account: " + Convert.toUnsignedLong(this.accountId) + " asset: " + Convert.toUnsignedLong(this.assetId) + " price: " + this.priceNQT + " quantity: " + this.quantityQNT + " height: " + this.creationHeight;
/* 148:    */   }
/* 149:    */   
/* 150:    */   private void setQuantityQNT(long paramLong)
/* 151:    */   {
/* 152:125 */     this.quantityQNT = paramLong;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public static final class Ask
/* 156:    */     extends Order
/* 157:    */   {
/* 158:149 */     private static final DbKey.LongKeyFactory<Ask> askOrderDbKeyFactory = new DbKey.LongKeyFactory("id")
/* 159:    */     {
/* 160:    */       public DbKey newKey(Order.Ask paramAnonymousAsk)
/* 161:    */       {
/* 162:153 */         return paramAnonymousAsk.dbKey;
/* 163:    */       }
/* 164:    */     };
/* 165:158 */     private static final VersionedEntityDbTable<Ask> askOrderTable = new VersionedEntityDbTable("ask_order", askOrderDbKeyFactory)
/* 166:    */     {
/* 167:    */       protected Order.Ask load(Connection paramAnonymousConnection, ResultSet paramAnonymousResultSet)
/* 168:    */         throws SQLException
/* 169:    */       {
/* 170:161 */         return new Order.Ask(paramAnonymousResultSet, null);
/* 171:    */       }
/* 172:    */       
/* 173:    */       protected void save(Connection paramAnonymousConnection, Order.Ask paramAnonymousAsk)
/* 174:    */         throws SQLException
/* 175:    */       {
/* 176:166 */         paramAnonymousAsk.save(paramAnonymousConnection, this.table);
/* 177:    */       }
/* 178:    */       
/* 179:    */       protected String defaultSort()
/* 180:    */       {
/* 181:171 */         return " ORDER BY creation_height DESC ";
/* 182:    */       }
/* 183:    */     };
/* 184:    */     private final DbKey dbKey;
/* 185:    */     
/* 186:    */     public static int getCount()
/* 187:    */     {
/* 188:177 */       return askOrderTable.getCount();
/* 189:    */     }
/* 190:    */     
/* 191:    */     public static Ask getAskOrder(long paramLong)
/* 192:    */     {
/* 193:181 */       return (Ask)askOrderTable.get(askOrderDbKeyFactory.newKey(paramLong));
/* 194:    */     }
/* 195:    */     
/* 196:    */     public static DbIterator<Ask> getAll(int paramInt1, int paramInt2)
/* 197:    */     {
/* 198:185 */       return askOrderTable.getAll(paramInt1, paramInt2);
/* 199:    */     }
/* 200:    */     
/* 201:    */     public static DbIterator<Ask> getAskOrdersByAccount(long paramLong, int paramInt1, int paramInt2)
/* 202:    */     {
/* 203:189 */       return askOrderTable.getManyBy(new DbClause.LongClause("account_id", paramLong), paramInt1, paramInt2);
/* 204:    */     }
/* 205:    */     
/* 206:    */     public static DbIterator<Ask> getAskOrdersByAsset(long paramLong, int paramInt1, int paramInt2)
/* 207:    */     {
/* 208:193 */       return askOrderTable.getManyBy(new DbClause.LongClause("asset_id", paramLong), paramInt1, paramInt2);
/* 209:    */     }
/* 210:    */     
/* 211:    */     public static DbIterator<Ask> getAskOrdersByAccountAsset(final long paramLong1, long paramLong2, int paramInt1, int paramInt2)
/* 212:    */     {
/* 213:197 */       DbClause local3 = new DbClause(" account_id = ? AND asset_id = ? ")
/* 214:    */       {
/* 215:    */         public int set(PreparedStatement paramAnonymousPreparedStatement, int paramAnonymousInt)
/* 216:    */           throws SQLException
/* 217:    */         {
/* 218:200 */           paramAnonymousPreparedStatement.setLong(paramAnonymousInt++, paramLong1);
/* 219:201 */           paramAnonymousPreparedStatement.setLong(paramAnonymousInt++, this.val$assetId);
/* 220:202 */           return paramAnonymousInt;
/* 221:    */         }
/* 222:204 */       };
/* 223:205 */       return askOrderTable.getManyBy(local3, paramInt1, paramInt2);
/* 224:    */     }
/* 225:    */     
/* 226:    */     public static DbIterator<Ask> getSortedOrders(long paramLong, int paramInt1, int paramInt2)
/* 227:    */     {
/* 228:209 */       return askOrderTable.getManyBy(new DbClause.LongClause("asset_id", paramLong), paramInt1, paramInt2, " ORDER BY price ASC, creation_height ASC, id ASC ");
/* 229:    */     }
/* 230:    */     
/* 231:    */     /* Error */
/* 232:    */     private static Ask getNextOrder(long paramLong)
/* 233:    */     {
/* 234:    */       // Byte code:
/* 235:    */       //   0: invokestatic 23	nxt/db/Db:getConnection	()Ljava/sql/Connection;
/* 236:    */       //   3: astore_2
/* 237:    */       //   4: aconst_null
/* 238:    */       //   5: astore_3
/* 239:    */       //   6: aload_2
/* 240:    */       //   7: ldc 24
/* 241:    */       //   9: invokeinterface 25 2 0
/* 242:    */       //   14: astore 4
/* 243:    */       //   16: aconst_null
/* 244:    */       //   17: astore 5
/* 245:    */       //   19: aload 4
/* 246:    */       //   21: iconst_1
/* 247:    */       //   22: lload_0
/* 248:    */       //   23: invokeinterface 26 4 0
/* 249:    */       //   28: getstatic 6	nxt/Order$Ask:askOrderTable	Lnxt/db/VersionedEntityDbTable;
/* 250:    */       //   31: aload_2
/* 251:    */       //   32: aload 4
/* 252:    */       //   34: iconst_1
/* 253:    */       //   35: invokevirtual 27	nxt/db/VersionedEntityDbTable:getManyBy	(Ljava/sql/Connection;Ljava/sql/PreparedStatement;Z)Lnxt/db/DbIterator;
/* 254:    */       //   38: astore 6
/* 255:    */       //   40: aconst_null
/* 256:    */       //   41: astore 7
/* 257:    */       //   43: aload 6
/* 258:    */       //   45: invokevirtual 28	nxt/db/DbIterator:hasNext	()Z
/* 259:    */       //   48: ifeq +14 -> 62
/* 260:    */       //   51: aload 6
/* 261:    */       //   53: invokevirtual 29	nxt/db/DbIterator:next	()Ljava/lang/Object;
/* 262:    */       //   56: checkcast 11	nxt/Order$Ask
/* 263:    */       //   59: goto +4 -> 63
/* 264:    */       //   62: aconst_null
/* 265:    */       //   63: astore 8
/* 266:    */       //   65: aload 6
/* 267:    */       //   67: ifnull +33 -> 100
/* 268:    */       //   70: aload 7
/* 269:    */       //   72: ifnull +23 -> 95
/* 270:    */       //   75: aload 6
/* 271:    */       //   77: invokevirtual 30	nxt/db/DbIterator:close	()V
/* 272:    */       //   80: goto +20 -> 100
/* 273:    */       //   83: astore 9
/* 274:    */       //   85: aload 7
/* 275:    */       //   87: aload 9
/* 276:    */       //   89: invokevirtual 32	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 277:    */       //   92: goto +8 -> 100
/* 278:    */       //   95: aload 6
/* 279:    */       //   97: invokevirtual 30	nxt/db/DbIterator:close	()V
/* 280:    */       //   100: aload 4
/* 281:    */       //   102: ifnull +37 -> 139
/* 282:    */       //   105: aload 5
/* 283:    */       //   107: ifnull +25 -> 132
/* 284:    */       //   110: aload 4
/* 285:    */       //   112: invokeinterface 33 1 0
/* 286:    */       //   117: goto +22 -> 139
/* 287:    */       //   120: astore 9
/* 288:    */       //   122: aload 5
/* 289:    */       //   124: aload 9
/* 290:    */       //   126: invokevirtual 32	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 291:    */       //   129: goto +10 -> 139
/* 292:    */       //   132: aload 4
/* 293:    */       //   134: invokeinterface 33 1 0
/* 294:    */       //   139: aload_2
/* 295:    */       //   140: ifnull +33 -> 173
/* 296:    */       //   143: aload_3
/* 297:    */       //   144: ifnull +23 -> 167
/* 298:    */       //   147: aload_2
/* 299:    */       //   148: invokeinterface 34 1 0
/* 300:    */       //   153: goto +20 -> 173
/* 301:    */       //   156: astore 9
/* 302:    */       //   158: aload_3
/* 303:    */       //   159: aload 9
/* 304:    */       //   161: invokevirtual 32	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 305:    */       //   164: goto +9 -> 173
/* 306:    */       //   167: aload_2
/* 307:    */       //   168: invokeinterface 34 1 0
/* 308:    */       //   173: aload 8
/* 309:    */       //   175: areturn
/* 310:    */       //   176: astore 8
/* 311:    */       //   178: aload 8
/* 312:    */       //   180: astore 7
/* 313:    */       //   182: aload 8
/* 314:    */       //   184: athrow
/* 315:    */       //   185: astore 10
/* 316:    */       //   187: aload 6
/* 317:    */       //   189: ifnull +33 -> 222
/* 318:    */       //   192: aload 7
/* 319:    */       //   194: ifnull +23 -> 217
/* 320:    */       //   197: aload 6
/* 321:    */       //   199: invokevirtual 30	nxt/db/DbIterator:close	()V
/* 322:    */       //   202: goto +20 -> 222
/* 323:    */       //   205: astore 11
/* 324:    */       //   207: aload 7
/* 325:    */       //   209: aload 11
/* 326:    */       //   211: invokevirtual 32	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 327:    */       //   214: goto +8 -> 222
/* 328:    */       //   217: aload 6
/* 329:    */       //   219: invokevirtual 30	nxt/db/DbIterator:close	()V
/* 330:    */       //   222: aload 10
/* 331:    */       //   224: athrow
/* 332:    */       //   225: astore 6
/* 333:    */       //   227: aload 6
/* 334:    */       //   229: astore 5
/* 335:    */       //   231: aload 6
/* 336:    */       //   233: athrow
/* 337:    */       //   234: astore 12
/* 338:    */       //   236: aload 4
/* 339:    */       //   238: ifnull +37 -> 275
/* 340:    */       //   241: aload 5
/* 341:    */       //   243: ifnull +25 -> 268
/* 342:    */       //   246: aload 4
/* 343:    */       //   248: invokeinterface 33 1 0
/* 344:    */       //   253: goto +22 -> 275
/* 345:    */       //   256: astore 13
/* 346:    */       //   258: aload 5
/* 347:    */       //   260: aload 13
/* 348:    */       //   262: invokevirtual 32	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 349:    */       //   265: goto +10 -> 275
/* 350:    */       //   268: aload 4
/* 351:    */       //   270: invokeinterface 33 1 0
/* 352:    */       //   275: aload 12
/* 353:    */       //   277: athrow
/* 354:    */       //   278: astore 4
/* 355:    */       //   280: aload 4
/* 356:    */       //   282: astore_3
/* 357:    */       //   283: aload 4
/* 358:    */       //   285: athrow
/* 359:    */       //   286: astore 14
/* 360:    */       //   288: aload_2
/* 361:    */       //   289: ifnull +33 -> 322
/* 362:    */       //   292: aload_3
/* 363:    */       //   293: ifnull +23 -> 316
/* 364:    */       //   296: aload_2
/* 365:    */       //   297: invokeinterface 34 1 0
/* 366:    */       //   302: goto +20 -> 322
/* 367:    */       //   305: astore 15
/* 368:    */       //   307: aload_3
/* 369:    */       //   308: aload 15
/* 370:    */       //   310: invokevirtual 32	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 371:    */       //   313: goto +9 -> 322
/* 372:    */       //   316: aload_2
/* 373:    */       //   317: invokeinterface 34 1 0
/* 374:    */       //   322: aload 14
/* 375:    */       //   324: athrow
/* 376:    */       //   325: astore_2
/* 377:    */       //   326: new 36	java/lang/RuntimeException
/* 378:    */       //   329: dup
/* 379:    */       //   330: aload_2
/* 380:    */       //   331: invokevirtual 37	java/sql/SQLException:toString	()Ljava/lang/String;
/* 381:    */       //   334: aload_2
/* 382:    */       //   335: invokespecial 38	java/lang/RuntimeException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
/* 383:    */       //   338: athrow
/* 384:    */       // Line number table:
/* 385:    */       //   Java source line #214	-> byte code offset #0
/* 386:    */       //   Java source line #215	-> byte code offset #6
/* 387:    */       //   Java source line #214	-> byte code offset #16
/* 388:    */       //   Java source line #217	-> byte code offset #19
/* 389:    */       //   Java source line #218	-> byte code offset #28
/* 390:    */       //   Java source line #219	-> byte code offset #43
/* 391:    */       //   Java source line #220	-> byte code offset #65
/* 392:    */       //   Java source line #221	-> byte code offset #100
/* 393:    */       //   Java source line #218	-> byte code offset #176
/* 394:    */       //   Java source line #220	-> byte code offset #185
/* 395:    */       //   Java source line #214	-> byte code offset #225
/* 396:    */       //   Java source line #221	-> byte code offset #234
/* 397:    */       //   Java source line #214	-> byte code offset #278
/* 398:    */       //   Java source line #221	-> byte code offset #286
/* 399:    */       //   Java source line #222	-> byte code offset #326
/* 400:    */       // Local variable table:
/* 401:    */       //   start	length	slot	name	signature
/* 402:    */       //   0	339	0	paramLong	long
/* 403:    */       //   3	314	2	localConnection	Connection
/* 404:    */       //   325	10	2	localSQLException	SQLException
/* 405:    */       //   5	303	3	localObject1	Object
/* 406:    */       //   14	255	4	localPreparedStatement	PreparedStatement
/* 407:    */       //   278	6	4	localThrowable1	Throwable
/* 408:    */       //   17	242	5	localObject2	Object
/* 409:    */       //   38	180	6	localDbIterator	DbIterator
/* 410:    */       //   225	7	6	localThrowable2	Throwable
/* 411:    */       //   41	167	7	localObject3	Object
/* 412:    */       //   63	111	8	localAsk	Ask
/* 413:    */       //   176	7	8	localThrowable3	Throwable
/* 414:    */       //   83	5	9	localThrowable4	Throwable
/* 415:    */       //   120	5	9	localThrowable5	Throwable
/* 416:    */       //   156	4	9	localThrowable6	Throwable
/* 417:    */       //   185	38	10	localObject4	Object
/* 418:    */       //   205	5	11	localThrowable7	Throwable
/* 419:    */       //   234	42	12	localObject5	Object
/* 420:    */       //   256	5	13	localThrowable8	Throwable
/* 421:    */       //   286	37	14	localObject6	Object
/* 422:    */       //   305	4	15	localThrowable9	Throwable
/* 423:    */       // Exception table:
/* 424:    */       //   from	to	target	type
/* 425:    */       //   75	80	83	java/lang/Throwable
/* 426:    */       //   110	117	120	java/lang/Throwable
/* 427:    */       //   147	153	156	java/lang/Throwable
/* 428:    */       //   43	65	176	java/lang/Throwable
/* 429:    */       //   43	65	185	finally
/* 430:    */       //   176	187	185	finally
/* 431:    */       //   197	202	205	java/lang/Throwable
/* 432:    */       //   19	100	225	java/lang/Throwable
/* 433:    */       //   176	225	225	java/lang/Throwable
/* 434:    */       //   19	100	234	finally
/* 435:    */       //   176	236	234	finally
/* 436:    */       //   246	253	256	java/lang/Throwable
/* 437:    */       //   6	139	278	java/lang/Throwable
/* 438:    */       //   176	278	278	java/lang/Throwable
/* 439:    */       //   6	139	286	finally
/* 440:    */       //   176	288	286	finally
/* 441:    */       //   296	302	305	java/lang/Throwable
/* 442:    */       //   0	173	325	java/sql/SQLException
/* 443:    */       //   176	325	325	java/sql/SQLException
/* 444:    */     }
/* 445:    */     
/* 446:    */     static void addOrder(Transaction paramTransaction, Attachment.ColoredCoinsAskOrderPlacement paramColoredCoinsAskOrderPlacement)
/* 447:    */     {
/* 448:227 */       Ask localAsk = new Ask(paramTransaction, paramColoredCoinsAskOrderPlacement);
/* 449:228 */       askOrderTable.insert(localAsk);
/* 450:229 */       Order.matchOrders(paramColoredCoinsAskOrderPlacement.getAssetId());
/* 451:    */     }
/* 452:    */     
/* 453:    */     static void removeOrder(long paramLong)
/* 454:    */     {
/* 455:233 */       askOrderTable.delete(getAskOrder(paramLong));
/* 456:    */     }
/* 457:    */     
/* 458:    */     static void init() {}
/* 459:    */     
/* 460:    */     private Ask(Transaction paramTransaction, Attachment.ColoredCoinsAskOrderPlacement paramColoredCoinsAskOrderPlacement)
/* 461:    */     {
/* 462:242 */       super(paramColoredCoinsAskOrderPlacement, null);
/* 463:243 */       this.dbKey = askOrderDbKeyFactory.newKey(this.id);
/* 464:    */     }
/* 465:    */     
/* 466:    */     private Ask(ResultSet paramResultSet)
/* 467:    */       throws SQLException
/* 468:    */     {
/* 469:247 */       super(null);
/* 470:248 */       this.dbKey = askOrderDbKeyFactory.newKey(this.id);
/* 471:    */     }
/* 472:    */     
/* 473:    */     private void save(Connection paramConnection, String paramString)
/* 474:    */       throws SQLException
/* 475:    */     {
/* 476:252 */       save(paramConnection, paramString);
/* 477:    */     }
/* 478:    */     
/* 479:    */     private void updateQuantityQNT(long paramLong)
/* 480:    */     {
/* 481:256 */       super.setQuantityQNT(paramLong);
/* 482:257 */       if (paramLong > 0L) {
/* 483:258 */         askOrderTable.insert(this);
/* 484:259 */       } else if (paramLong == 0L) {
/* 485:260 */         askOrderTable.delete(this);
/* 486:    */       } else {
/* 487:262 */         throw new IllegalArgumentException("Negative quantity: " + paramLong + " for order: " + Convert.toUnsignedLong(getId()));
/* 488:    */       }
/* 489:    */     }
/* 490:    */   }
/* 491:    */   
/* 492:    */   public static final class Bid
/* 493:    */     extends Order
/* 494:    */   {
/* 495:284 */     private static final DbKey.LongKeyFactory<Bid> bidOrderDbKeyFactory = new DbKey.LongKeyFactory("id")
/* 496:    */     {
/* 497:    */       public DbKey newKey(Order.Bid paramAnonymousBid)
/* 498:    */       {
/* 499:288 */         return paramAnonymousBid.dbKey;
/* 500:    */       }
/* 501:    */     };
/* 502:293 */     private static final VersionedEntityDbTable<Bid> bidOrderTable = new VersionedEntityDbTable("bid_order", bidOrderDbKeyFactory)
/* 503:    */     {
/* 504:    */       protected Order.Bid load(Connection paramAnonymousConnection, ResultSet paramAnonymousResultSet)
/* 505:    */         throws SQLException
/* 506:    */       {
/* 507:297 */         return new Order.Bid(paramAnonymousResultSet, null);
/* 508:    */       }
/* 509:    */       
/* 510:    */       protected void save(Connection paramAnonymousConnection, Order.Bid paramAnonymousBid)
/* 511:    */         throws SQLException
/* 512:    */       {
/* 513:302 */         paramAnonymousBid.save(paramAnonymousConnection, this.table);
/* 514:    */       }
/* 515:    */       
/* 516:    */       protected String defaultSort()
/* 517:    */       {
/* 518:307 */         return " ORDER BY creation_height DESC ";
/* 519:    */       }
/* 520:    */     };
/* 521:    */     private final DbKey dbKey;
/* 522:    */     
/* 523:    */     public static int getCount()
/* 524:    */     {
/* 525:313 */       return bidOrderTable.getCount();
/* 526:    */     }
/* 527:    */     
/* 528:    */     public static Bid getBidOrder(long paramLong)
/* 529:    */     {
/* 530:317 */       return (Bid)bidOrderTable.get(bidOrderDbKeyFactory.newKey(paramLong));
/* 531:    */     }
/* 532:    */     
/* 533:    */     public static DbIterator<Bid> getAll(int paramInt1, int paramInt2)
/* 534:    */     {
/* 535:321 */       return bidOrderTable.getAll(paramInt1, paramInt2);
/* 536:    */     }
/* 537:    */     
/* 538:    */     public static DbIterator<Bid> getBidOrdersByAccount(long paramLong, int paramInt1, int paramInt2)
/* 539:    */     {
/* 540:325 */       return bidOrderTable.getManyBy(new DbClause.LongClause("account_id", paramLong), paramInt1, paramInt2);
/* 541:    */     }
/* 542:    */     
/* 543:    */     public static DbIterator<Bid> getBidOrdersByAsset(long paramLong, int paramInt1, int paramInt2)
/* 544:    */     {
/* 545:329 */       return bidOrderTable.getManyBy(new DbClause.LongClause("asset_id", paramLong), paramInt1, paramInt2);
/* 546:    */     }
/* 547:    */     
/* 548:    */     public static DbIterator<Bid> getBidOrdersByAccountAsset(final long paramLong1, long paramLong2, int paramInt1, int paramInt2)
/* 549:    */     {
/* 550:333 */       DbClause local3 = new DbClause(" account_id = ? AND asset_id = ? ")
/* 551:    */       {
/* 552:    */         public int set(PreparedStatement paramAnonymousPreparedStatement, int paramAnonymousInt)
/* 553:    */           throws SQLException
/* 554:    */         {
/* 555:336 */           paramAnonymousPreparedStatement.setLong(paramAnonymousInt++, paramLong1);
/* 556:337 */           paramAnonymousPreparedStatement.setLong(paramAnonymousInt++, this.val$assetId);
/* 557:338 */           return paramAnonymousInt;
/* 558:    */         }
/* 559:340 */       };
/* 560:341 */       return bidOrderTable.getManyBy(local3, paramInt1, paramInt2);
/* 561:    */     }
/* 562:    */     
/* 563:    */     public static DbIterator<Bid> getSortedOrders(long paramLong, int paramInt1, int paramInt2)
/* 564:    */     {
/* 565:345 */       return bidOrderTable.getManyBy(new DbClause.LongClause("asset_id", paramLong), paramInt1, paramInt2, " ORDER BY price DESC, creation_height ASC, id ASC ");
/* 566:    */     }
/* 567:    */     
/* 568:    */     /* Error */
/* 569:    */     private static Bid getNextOrder(long paramLong)
/* 570:    */     {
/* 571:    */       // Byte code:
/* 572:    */       //   0: invokestatic 23	nxt/db/Db:getConnection	()Ljava/sql/Connection;
/* 573:    */       //   3: astore_2
/* 574:    */       //   4: aconst_null
/* 575:    */       //   5: astore_3
/* 576:    */       //   6: aload_2
/* 577:    */       //   7: ldc 24
/* 578:    */       //   9: invokeinterface 25 2 0
/* 579:    */       //   14: astore 4
/* 580:    */       //   16: aconst_null
/* 581:    */       //   17: astore 5
/* 582:    */       //   19: aload 4
/* 583:    */       //   21: iconst_1
/* 584:    */       //   22: lload_0
/* 585:    */       //   23: invokeinterface 26 4 0
/* 586:    */       //   28: getstatic 6	nxt/Order$Bid:bidOrderTable	Lnxt/db/VersionedEntityDbTable;
/* 587:    */       //   31: aload_2
/* 588:    */       //   32: aload 4
/* 589:    */       //   34: iconst_1
/* 590:    */       //   35: invokevirtual 27	nxt/db/VersionedEntityDbTable:getManyBy	(Ljava/sql/Connection;Ljava/sql/PreparedStatement;Z)Lnxt/db/DbIterator;
/* 591:    */       //   38: astore 6
/* 592:    */       //   40: aconst_null
/* 593:    */       //   41: astore 7
/* 594:    */       //   43: aload 6
/* 595:    */       //   45: invokevirtual 28	nxt/db/DbIterator:hasNext	()Z
/* 596:    */       //   48: ifeq +14 -> 62
/* 597:    */       //   51: aload 6
/* 598:    */       //   53: invokevirtual 29	nxt/db/DbIterator:next	()Ljava/lang/Object;
/* 599:    */       //   56: checkcast 11	nxt/Order$Bid
/* 600:    */       //   59: goto +4 -> 63
/* 601:    */       //   62: aconst_null
/* 602:    */       //   63: astore 8
/* 603:    */       //   65: aload 6
/* 604:    */       //   67: ifnull +33 -> 100
/* 605:    */       //   70: aload 7
/* 606:    */       //   72: ifnull +23 -> 95
/* 607:    */       //   75: aload 6
/* 608:    */       //   77: invokevirtual 30	nxt/db/DbIterator:close	()V
/* 609:    */       //   80: goto +20 -> 100
/* 610:    */       //   83: astore 9
/* 611:    */       //   85: aload 7
/* 612:    */       //   87: aload 9
/* 613:    */       //   89: invokevirtual 32	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 614:    */       //   92: goto +8 -> 100
/* 615:    */       //   95: aload 6
/* 616:    */       //   97: invokevirtual 30	nxt/db/DbIterator:close	()V
/* 617:    */       //   100: aload 4
/* 618:    */       //   102: ifnull +37 -> 139
/* 619:    */       //   105: aload 5
/* 620:    */       //   107: ifnull +25 -> 132
/* 621:    */       //   110: aload 4
/* 622:    */       //   112: invokeinterface 33 1 0
/* 623:    */       //   117: goto +22 -> 139
/* 624:    */       //   120: astore 9
/* 625:    */       //   122: aload 5
/* 626:    */       //   124: aload 9
/* 627:    */       //   126: invokevirtual 32	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 628:    */       //   129: goto +10 -> 139
/* 629:    */       //   132: aload 4
/* 630:    */       //   134: invokeinterface 33 1 0
/* 631:    */       //   139: aload_2
/* 632:    */       //   140: ifnull +33 -> 173
/* 633:    */       //   143: aload_3
/* 634:    */       //   144: ifnull +23 -> 167
/* 635:    */       //   147: aload_2
/* 636:    */       //   148: invokeinterface 34 1 0
/* 637:    */       //   153: goto +20 -> 173
/* 638:    */       //   156: astore 9
/* 639:    */       //   158: aload_3
/* 640:    */       //   159: aload 9
/* 641:    */       //   161: invokevirtual 32	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 642:    */       //   164: goto +9 -> 173
/* 643:    */       //   167: aload_2
/* 644:    */       //   168: invokeinterface 34 1 0
/* 645:    */       //   173: aload 8
/* 646:    */       //   175: areturn
/* 647:    */       //   176: astore 8
/* 648:    */       //   178: aload 8
/* 649:    */       //   180: astore 7
/* 650:    */       //   182: aload 8
/* 651:    */       //   184: athrow
/* 652:    */       //   185: astore 10
/* 653:    */       //   187: aload 6
/* 654:    */       //   189: ifnull +33 -> 222
/* 655:    */       //   192: aload 7
/* 656:    */       //   194: ifnull +23 -> 217
/* 657:    */       //   197: aload 6
/* 658:    */       //   199: invokevirtual 30	nxt/db/DbIterator:close	()V
/* 659:    */       //   202: goto +20 -> 222
/* 660:    */       //   205: astore 11
/* 661:    */       //   207: aload 7
/* 662:    */       //   209: aload 11
/* 663:    */       //   211: invokevirtual 32	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 664:    */       //   214: goto +8 -> 222
/* 665:    */       //   217: aload 6
/* 666:    */       //   219: invokevirtual 30	nxt/db/DbIterator:close	()V
/* 667:    */       //   222: aload 10
/* 668:    */       //   224: athrow
/* 669:    */       //   225: astore 6
/* 670:    */       //   227: aload 6
/* 671:    */       //   229: astore 5
/* 672:    */       //   231: aload 6
/* 673:    */       //   233: athrow
/* 674:    */       //   234: astore 12
/* 675:    */       //   236: aload 4
/* 676:    */       //   238: ifnull +37 -> 275
/* 677:    */       //   241: aload 5
/* 678:    */       //   243: ifnull +25 -> 268
/* 679:    */       //   246: aload 4
/* 680:    */       //   248: invokeinterface 33 1 0
/* 681:    */       //   253: goto +22 -> 275
/* 682:    */       //   256: astore 13
/* 683:    */       //   258: aload 5
/* 684:    */       //   260: aload 13
/* 685:    */       //   262: invokevirtual 32	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 686:    */       //   265: goto +10 -> 275
/* 687:    */       //   268: aload 4
/* 688:    */       //   270: invokeinterface 33 1 0
/* 689:    */       //   275: aload 12
/* 690:    */       //   277: athrow
/* 691:    */       //   278: astore 4
/* 692:    */       //   280: aload 4
/* 693:    */       //   282: astore_3
/* 694:    */       //   283: aload 4
/* 695:    */       //   285: athrow
/* 696:    */       //   286: astore 14
/* 697:    */       //   288: aload_2
/* 698:    */       //   289: ifnull +33 -> 322
/* 699:    */       //   292: aload_3
/* 700:    */       //   293: ifnull +23 -> 316
/* 701:    */       //   296: aload_2
/* 702:    */       //   297: invokeinterface 34 1 0
/* 703:    */       //   302: goto +20 -> 322
/* 704:    */       //   305: astore 15
/* 705:    */       //   307: aload_3
/* 706:    */       //   308: aload 15
/* 707:    */       //   310: invokevirtual 32	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 708:    */       //   313: goto +9 -> 322
/* 709:    */       //   316: aload_2
/* 710:    */       //   317: invokeinterface 34 1 0
/* 711:    */       //   322: aload 14
/* 712:    */       //   324: athrow
/* 713:    */       //   325: astore_2
/* 714:    */       //   326: new 36	java/lang/RuntimeException
/* 715:    */       //   329: dup
/* 716:    */       //   330: aload_2
/* 717:    */       //   331: invokevirtual 37	java/sql/SQLException:toString	()Ljava/lang/String;
/* 718:    */       //   334: aload_2
/* 719:    */       //   335: invokespecial 38	java/lang/RuntimeException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
/* 720:    */       //   338: athrow
/* 721:    */       // Line number table:
/* 722:    */       //   Java source line #350	-> byte code offset #0
/* 723:    */       //   Java source line #351	-> byte code offset #6
/* 724:    */       //   Java source line #350	-> byte code offset #16
/* 725:    */       //   Java source line #353	-> byte code offset #19
/* 726:    */       //   Java source line #354	-> byte code offset #28
/* 727:    */       //   Java source line #355	-> byte code offset #43
/* 728:    */       //   Java source line #356	-> byte code offset #65
/* 729:    */       //   Java source line #357	-> byte code offset #100
/* 730:    */       //   Java source line #354	-> byte code offset #176
/* 731:    */       //   Java source line #356	-> byte code offset #185
/* 732:    */       //   Java source line #350	-> byte code offset #225
/* 733:    */       //   Java source line #357	-> byte code offset #234
/* 734:    */       //   Java source line #350	-> byte code offset #278
/* 735:    */       //   Java source line #357	-> byte code offset #286
/* 736:    */       //   Java source line #358	-> byte code offset #326
/* 737:    */       // Local variable table:
/* 738:    */       //   start	length	slot	name	signature
/* 739:    */       //   0	339	0	paramLong	long
/* 740:    */       //   3	314	2	localConnection	Connection
/* 741:    */       //   325	10	2	localSQLException	SQLException
/* 742:    */       //   5	303	3	localObject1	Object
/* 743:    */       //   14	255	4	localPreparedStatement	PreparedStatement
/* 744:    */       //   278	6	4	localThrowable1	Throwable
/* 745:    */       //   17	242	5	localObject2	Object
/* 746:    */       //   38	180	6	localDbIterator	DbIterator
/* 747:    */       //   225	7	6	localThrowable2	Throwable
/* 748:    */       //   41	167	7	localObject3	Object
/* 749:    */       //   63	111	8	localBid	Bid
/* 750:    */       //   176	7	8	localThrowable3	Throwable
/* 751:    */       //   83	5	9	localThrowable4	Throwable
/* 752:    */       //   120	5	9	localThrowable5	Throwable
/* 753:    */       //   156	4	9	localThrowable6	Throwable
/* 754:    */       //   185	38	10	localObject4	Object
/* 755:    */       //   205	5	11	localThrowable7	Throwable
/* 756:    */       //   234	42	12	localObject5	Object
/* 757:    */       //   256	5	13	localThrowable8	Throwable
/* 758:    */       //   286	37	14	localObject6	Object
/* 759:    */       //   305	4	15	localThrowable9	Throwable
/* 760:    */       // Exception table:
/* 761:    */       //   from	to	target	type
/* 762:    */       //   75	80	83	java/lang/Throwable
/* 763:    */       //   110	117	120	java/lang/Throwable
/* 764:    */       //   147	153	156	java/lang/Throwable
/* 765:    */       //   43	65	176	java/lang/Throwable
/* 766:    */       //   43	65	185	finally
/* 767:    */       //   176	187	185	finally
/* 768:    */       //   197	202	205	java/lang/Throwable
/* 769:    */       //   19	100	225	java/lang/Throwable
/* 770:    */       //   176	225	225	java/lang/Throwable
/* 771:    */       //   19	100	234	finally
/* 772:    */       //   176	236	234	finally
/* 773:    */       //   246	253	256	java/lang/Throwable
/* 774:    */       //   6	139	278	java/lang/Throwable
/* 775:    */       //   176	278	278	java/lang/Throwable
/* 776:    */       //   6	139	286	finally
/* 777:    */       //   176	288	286	finally
/* 778:    */       //   296	302	305	java/lang/Throwable
/* 779:    */       //   0	173	325	java/sql/SQLException
/* 780:    */       //   176	325	325	java/sql/SQLException
/* 781:    */     }
/* 782:    */     
/* 783:    */     static void addOrder(Transaction paramTransaction, Attachment.ColoredCoinsBidOrderPlacement paramColoredCoinsBidOrderPlacement)
/* 784:    */     {
/* 785:363 */       Bid localBid = new Bid(paramTransaction, paramColoredCoinsBidOrderPlacement);
/* 786:364 */       bidOrderTable.insert(localBid);
/* 787:365 */       Order.matchOrders(paramColoredCoinsBidOrderPlacement.getAssetId());
/* 788:    */     }
/* 789:    */     
/* 790:    */     static void removeOrder(long paramLong)
/* 791:    */     {
/* 792:369 */       bidOrderTable.delete(getBidOrder(paramLong));
/* 793:    */     }
/* 794:    */     
/* 795:    */     static void init() {}
/* 796:    */     
/* 797:    */     private Bid(Transaction paramTransaction, Attachment.ColoredCoinsBidOrderPlacement paramColoredCoinsBidOrderPlacement)
/* 798:    */     {
/* 799:378 */       super(paramColoredCoinsBidOrderPlacement, null);
/* 800:379 */       this.dbKey = bidOrderDbKeyFactory.newKey(this.id);
/* 801:    */     }
/* 802:    */     
/* 803:    */     private Bid(ResultSet paramResultSet)
/* 804:    */       throws SQLException
/* 805:    */     {
/* 806:383 */       super(null);
/* 807:384 */       this.dbKey = bidOrderDbKeyFactory.newKey(this.id);
/* 808:    */     }
/* 809:    */     
/* 810:    */     private void save(Connection paramConnection, String paramString)
/* 811:    */       throws SQLException
/* 812:    */     {
/* 813:388 */       save(paramConnection, paramString);
/* 814:    */     }
/* 815:    */     
/* 816:    */     private void updateQuantityQNT(long paramLong)
/* 817:    */     {
/* 818:392 */       super.setQuantityQNT(paramLong);
/* 819:393 */       if (paramLong > 0L) {
/* 820:394 */         bidOrderTable.insert(this);
/* 821:395 */       } else if (paramLong == 0L) {
/* 822:396 */         bidOrderTable.delete(this);
/* 823:    */       } else {
/* 824:398 */         throw new IllegalArgumentException("Negative quantity: " + paramLong + " for order: " + Convert.toUnsignedLong(getId()));
/* 825:    */       }
/* 826:    */     }
/* 827:    */   }
/* 828:    */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.Order
 * JD-Core Version:    0.7.1
 */