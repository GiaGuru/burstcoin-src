/*   1:    */ package nxt;
/*   2:    */ 
/*   3:    */ import java.sql.Connection;
/*   4:    */ import java.sql.PreparedStatement;
/*   5:    */ import java.sql.ResultSet;
/*   6:    */ import java.sql.SQLException;
/*   7:    */ import nxt.db.Db;
/*   8:    */ import nxt.db.DbClause.LongClause;
/*   9:    */ import nxt.db.DbIterator;
/*  10:    */ import nxt.db.DbKey;
/*  11:    */ import nxt.db.DbKey.Factory;
/*  12:    */ import nxt.db.DbKey.LinkKeyFactory;
/*  13:    */ import nxt.db.DbUtils;
/*  14:    */ import nxt.db.EntityDbTable;
/*  15:    */ import nxt.util.Convert;
/*  16:    */ import nxt.util.Listener;
/*  17:    */ import nxt.util.Listeners;
/*  18:    */ 
/*  19:    */ public final class Trade
/*  20:    */ {
/*  21:    */   public static enum Event
/*  22:    */   {
/*  23: 21 */     TRADE;
/*  24:    */     
/*  25:    */     private Event() {}
/*  26:    */   }
/*  27:    */   
/*  28: 24 */   private static final Listeners<Trade, Event> listeners = new Listeners();
/*  29: 26 */   private static final DbKey.LinkKeyFactory<Trade> tradeDbKeyFactory = new DbKey.LinkKeyFactory("ask_order_id", "bid_order_id")
/*  30:    */   {
/*  31:    */     public DbKey newKey(Trade paramAnonymousTrade)
/*  32:    */     {
/*  33: 30 */       return paramAnonymousTrade.dbKey;
/*  34:    */     }
/*  35:    */   };
/*  36: 35 */   private static final EntityDbTable<Trade> tradeTable = new EntityDbTable("trade", tradeDbKeyFactory)
/*  37:    */   {
/*  38:    */     protected Trade load(Connection paramAnonymousConnection, ResultSet paramAnonymousResultSet)
/*  39:    */       throws SQLException
/*  40:    */     {
/*  41: 39 */       return new Trade(paramAnonymousResultSet, null);
/*  42:    */     }
/*  43:    */     
/*  44:    */     protected void save(Connection paramAnonymousConnection, Trade paramAnonymousTrade)
/*  45:    */       throws SQLException
/*  46:    */     {
/*  47: 44 */       paramAnonymousTrade.save(paramAnonymousConnection);
/*  48:    */     }
/*  49:    */   };
/*  50:    */   private final int timestamp;
/*  51:    */   private final long assetId;
/*  52:    */   private final long blockId;
/*  53:    */   private final int height;
/*  54:    */   private final long askOrderId;
/*  55:    */   private final long bidOrderId;
/*  56:    */   private final int askOrderHeight;
/*  57:    */   private final int bidOrderHeight;
/*  58:    */   private final long sellerId;
/*  59:    */   private final long buyerId;
/*  60:    */   private final DbKey dbKey;
/*  61:    */   private final long quantityQNT;
/*  62:    */   private final long priceNQT;
/*  63:    */   private final boolean isBuy;
/*  64:    */   
/*  65:    */   public static DbIterator<Trade> getAllTrades(int paramInt1, int paramInt2)
/*  66:    */   {
/*  67: 50 */     return tradeTable.getAll(paramInt1, paramInt2);
/*  68:    */   }
/*  69:    */   
/*  70:    */   public static int getCount()
/*  71:    */   {
/*  72: 54 */     return tradeTable.getCount();
/*  73:    */   }
/*  74:    */   
/*  75:    */   public static boolean addListener(Listener<Trade> paramListener, Event paramEvent)
/*  76:    */   {
/*  77: 58 */     return listeners.addListener(paramListener, paramEvent);
/*  78:    */   }
/*  79:    */   
/*  80:    */   public static boolean removeListener(Listener<Trade> paramListener, Event paramEvent)
/*  81:    */   {
/*  82: 62 */     return listeners.removeListener(paramListener, paramEvent);
/*  83:    */   }
/*  84:    */   
/*  85:    */   public static DbIterator<Trade> getAssetTrades(long paramLong, int paramInt1, int paramInt2)
/*  86:    */   {
/*  87: 66 */     return tradeTable.getManyBy(new DbClause.LongClause("asset_id", paramLong), paramInt1, paramInt2);
/*  88:    */   }
/*  89:    */   
/*  90:    */   public static DbIterator<Trade> getAccountTrades(long paramLong, int paramInt1, int paramInt2)
/*  91:    */   {
/*  92: 70 */     Connection localConnection = null;
/*  93:    */     try
/*  94:    */     {
/*  95: 72 */       localConnection = Db.getConnection();
/*  96: 73 */       PreparedStatement localPreparedStatement = localConnection.prepareStatement("SELECT * FROM trade WHERE seller_id = ? UNION ALL SELECT * FROM trade WHERE buyer_id = ? AND seller_id <> ? ORDER BY height DESC" + DbUtils.limitsClause(paramInt1, paramInt2));
/*  97:    */       
/*  98:    */ 
/*  99: 76 */       int i = 0;
/* 100: 77 */       localPreparedStatement.setLong(++i, paramLong);
/* 101: 78 */       localPreparedStatement.setLong(++i, paramLong);
/* 102: 79 */       localPreparedStatement.setLong(++i, paramLong);
/* 103: 80 */       i++;DbUtils.setLimits(i, localPreparedStatement, paramInt1, paramInt2);
/* 104: 81 */       return tradeTable.getManyBy(localConnection, localPreparedStatement, false);
/* 105:    */     }
/* 106:    */     catch (SQLException localSQLException)
/* 107:    */     {
/* 108: 83 */       DbUtils.close(new AutoCloseable[] { localConnection });
/* 109: 84 */       throw new RuntimeException(localSQLException.toString(), localSQLException);
/* 110:    */     }
/* 111:    */   }
/* 112:    */   
/* 113:    */   public static DbIterator<Trade> getAccountAssetTrades(long paramLong1, long paramLong2, int paramInt1, int paramInt2)
/* 114:    */   {
/* 115: 89 */     Connection localConnection = null;
/* 116:    */     try
/* 117:    */     {
/* 118: 91 */       localConnection = Db.getConnection();
/* 119: 92 */       PreparedStatement localPreparedStatement = localConnection.prepareStatement("SELECT * FROM trade WHERE seller_id = ? AND asset_id = ? UNION ALL SELECT * FROM trade WHERE buyer_id = ? AND seller_id <> ? AND asset_id = ? ORDER BY height DESC" + DbUtils.limitsClause(paramInt1, paramInt2));
/* 120:    */       
/* 121:    */ 
/* 122: 95 */       int i = 0;
/* 123: 96 */       localPreparedStatement.setLong(++i, paramLong1);
/* 124: 97 */       localPreparedStatement.setLong(++i, paramLong2);
/* 125: 98 */       localPreparedStatement.setLong(++i, paramLong1);
/* 126: 99 */       localPreparedStatement.setLong(++i, paramLong1);
/* 127:100 */       localPreparedStatement.setLong(++i, paramLong2);
/* 128:101 */       i++;DbUtils.setLimits(i, localPreparedStatement, paramInt1, paramInt2);
/* 129:102 */       return tradeTable.getManyBy(localConnection, localPreparedStatement, false);
/* 130:    */     }
/* 131:    */     catch (SQLException localSQLException)
/* 132:    */     {
/* 133:104 */       DbUtils.close(new AutoCloseable[] { localConnection });
/* 134:105 */       throw new RuntimeException(localSQLException.toString(), localSQLException);
/* 135:    */     }
/* 136:    */   }
/* 137:    */   
/* 138:    */   /* Error */
/* 139:    */   public static int getTradeCount(long paramLong)
/* 140:    */   {
/* 141:    */     // Byte code:
/* 142:    */     //   0: invokestatic 14	nxt/db/Db:getConnection	()Ljava/sql/Connection;
/* 143:    */     //   3: astore_2
/* 144:    */     //   4: aconst_null
/* 145:    */     //   5: astore_3
/* 146:    */     //   6: aload_2
/* 147:    */     //   7: ldc 32
/* 148:    */     //   9: invokeinterface 21 2 0
/* 149:    */     //   14: astore 4
/* 150:    */     //   16: aconst_null
/* 151:    */     //   17: astore 5
/* 152:    */     //   19: aload 4
/* 153:    */     //   21: iconst_1
/* 154:    */     //   22: lload_0
/* 155:    */     //   23: invokeinterface 22 4 0
/* 156:    */     //   28: aload 4
/* 157:    */     //   30: invokeinterface 33 1 0
/* 158:    */     //   35: astore 6
/* 159:    */     //   37: aconst_null
/* 160:    */     //   38: astore 7
/* 161:    */     //   40: aload 6
/* 162:    */     //   42: invokeinterface 34 1 0
/* 163:    */     //   47: pop
/* 164:    */     //   48: aload 6
/* 165:    */     //   50: iconst_1
/* 166:    */     //   51: invokeinterface 35 2 0
/* 167:    */     //   56: istore 8
/* 168:    */     //   58: aload 6
/* 169:    */     //   60: ifnull +37 -> 97
/* 170:    */     //   63: aload 7
/* 171:    */     //   65: ifnull +25 -> 90
/* 172:    */     //   68: aload 6
/* 173:    */     //   70: invokeinterface 36 1 0
/* 174:    */     //   75: goto +22 -> 97
/* 175:    */     //   78: astore 9
/* 176:    */     //   80: aload 7
/* 177:    */     //   82: aload 9
/* 178:    */     //   84: invokevirtual 38	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 179:    */     //   87: goto +10 -> 97
/* 180:    */     //   90: aload 6
/* 181:    */     //   92: invokeinterface 36 1 0
/* 182:    */     //   97: aload 4
/* 183:    */     //   99: ifnull +37 -> 136
/* 184:    */     //   102: aload 5
/* 185:    */     //   104: ifnull +25 -> 129
/* 186:    */     //   107: aload 4
/* 187:    */     //   109: invokeinterface 39 1 0
/* 188:    */     //   114: goto +22 -> 136
/* 189:    */     //   117: astore 9
/* 190:    */     //   119: aload 5
/* 191:    */     //   121: aload 9
/* 192:    */     //   123: invokevirtual 38	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 193:    */     //   126: goto +10 -> 136
/* 194:    */     //   129: aload 4
/* 195:    */     //   131: invokeinterface 39 1 0
/* 196:    */     //   136: aload_2
/* 197:    */     //   137: ifnull +33 -> 170
/* 198:    */     //   140: aload_3
/* 199:    */     //   141: ifnull +23 -> 164
/* 200:    */     //   144: aload_2
/* 201:    */     //   145: invokeinterface 40 1 0
/* 202:    */     //   150: goto +20 -> 170
/* 203:    */     //   153: astore 9
/* 204:    */     //   155: aload_3
/* 205:    */     //   156: aload 9
/* 206:    */     //   158: invokevirtual 38	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 207:    */     //   161: goto +9 -> 170
/* 208:    */     //   164: aload_2
/* 209:    */     //   165: invokeinterface 40 1 0
/* 210:    */     //   170: iload 8
/* 211:    */     //   172: ireturn
/* 212:    */     //   173: astore 8
/* 213:    */     //   175: aload 8
/* 214:    */     //   177: astore 7
/* 215:    */     //   179: aload 8
/* 216:    */     //   181: athrow
/* 217:    */     //   182: astore 10
/* 218:    */     //   184: aload 6
/* 219:    */     //   186: ifnull +37 -> 223
/* 220:    */     //   189: aload 7
/* 221:    */     //   191: ifnull +25 -> 216
/* 222:    */     //   194: aload 6
/* 223:    */     //   196: invokeinterface 36 1 0
/* 224:    */     //   201: goto +22 -> 223
/* 225:    */     //   204: astore 11
/* 226:    */     //   206: aload 7
/* 227:    */     //   208: aload 11
/* 228:    */     //   210: invokevirtual 38	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 229:    */     //   213: goto +10 -> 223
/* 230:    */     //   216: aload 6
/* 231:    */     //   218: invokeinterface 36 1 0
/* 232:    */     //   223: aload 10
/* 233:    */     //   225: athrow
/* 234:    */     //   226: astore 6
/* 235:    */     //   228: aload 6
/* 236:    */     //   230: astore 5
/* 237:    */     //   232: aload 6
/* 238:    */     //   234: athrow
/* 239:    */     //   235: astore 12
/* 240:    */     //   237: aload 4
/* 241:    */     //   239: ifnull +37 -> 276
/* 242:    */     //   242: aload 5
/* 243:    */     //   244: ifnull +25 -> 269
/* 244:    */     //   247: aload 4
/* 245:    */     //   249: invokeinterface 39 1 0
/* 246:    */     //   254: goto +22 -> 276
/* 247:    */     //   257: astore 13
/* 248:    */     //   259: aload 5
/* 249:    */     //   261: aload 13
/* 250:    */     //   263: invokevirtual 38	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 251:    */     //   266: goto +10 -> 276
/* 252:    */     //   269: aload 4
/* 253:    */     //   271: invokeinterface 39 1 0
/* 254:    */     //   276: aload 12
/* 255:    */     //   278: athrow
/* 256:    */     //   279: astore 4
/* 257:    */     //   281: aload 4
/* 258:    */     //   283: astore_3
/* 259:    */     //   284: aload 4
/* 260:    */     //   286: athrow
/* 261:    */     //   287: astore 14
/* 262:    */     //   289: aload_2
/* 263:    */     //   290: ifnull +33 -> 323
/* 264:    */     //   293: aload_3
/* 265:    */     //   294: ifnull +23 -> 317
/* 266:    */     //   297: aload_2
/* 267:    */     //   298: invokeinterface 40 1 0
/* 268:    */     //   303: goto +20 -> 323
/* 269:    */     //   306: astore 15
/* 270:    */     //   308: aload_3
/* 271:    */     //   309: aload 15
/* 272:    */     //   311: invokevirtual 38	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 273:    */     //   314: goto +9 -> 323
/* 274:    */     //   317: aload_2
/* 275:    */     //   318: invokeinterface 40 1 0
/* 276:    */     //   323: aload 14
/* 277:    */     //   325: athrow
/* 278:    */     //   326: astore_2
/* 279:    */     //   327: new 28	java/lang/RuntimeException
/* 280:    */     //   330: dup
/* 281:    */     //   331: aload_2
/* 282:    */     //   332: invokevirtual 29	java/sql/SQLException:toString	()Ljava/lang/String;
/* 283:    */     //   335: aload_2
/* 284:    */     //   336: invokespecial 30	java/lang/RuntimeException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
/* 285:    */     //   339: athrow
/* 286:    */     // Line number table:
/* 287:    */     //   Java source line #110	-> byte code offset #0
/* 288:    */     //   Java source line #111	-> byte code offset #6
/* 289:    */     //   Java source line #110	-> byte code offset #16
/* 290:    */     //   Java source line #112	-> byte code offset #19
/* 291:    */     //   Java source line #113	-> byte code offset #28
/* 292:    */     //   Java source line #114	-> byte code offset #40
/* 293:    */     //   Java source line #115	-> byte code offset #48
/* 294:    */     //   Java source line #116	-> byte code offset #58
/* 295:    */     //   Java source line #117	-> byte code offset #97
/* 296:    */     //   Java source line #113	-> byte code offset #173
/* 297:    */     //   Java source line #116	-> byte code offset #182
/* 298:    */     //   Java source line #110	-> byte code offset #226
/* 299:    */     //   Java source line #117	-> byte code offset #235
/* 300:    */     //   Java source line #110	-> byte code offset #279
/* 301:    */     //   Java source line #117	-> byte code offset #287
/* 302:    */     //   Java source line #118	-> byte code offset #327
/* 303:    */     // Local variable table:
/* 304:    */     //   start	length	slot	name	signature
/* 305:    */     //   0	340	0	paramLong	long
/* 306:    */     //   3	315	2	localConnection	Connection
/* 307:    */     //   326	10	2	localSQLException	SQLException
/* 308:    */     //   5	304	3	localObject1	Object
/* 309:    */     //   14	256	4	localPreparedStatement	PreparedStatement
/* 310:    */     //   279	6	4	localThrowable1	Throwable
/* 311:    */     //   17	243	5	localObject2	Object
/* 312:    */     //   35	182	6	localResultSet	ResultSet
/* 313:    */     //   226	7	6	localThrowable2	Throwable
/* 314:    */     //   38	169	7	localObject3	Object
/* 315:    */     //   56	115	8	i	int
/* 316:    */     //   173	7	8	localThrowable3	Throwable
/* 317:    */     //   78	5	9	localThrowable4	Throwable
/* 318:    */     //   117	5	9	localThrowable5	Throwable
/* 319:    */     //   153	4	9	localThrowable6	Throwable
/* 320:    */     //   182	42	10	localObject4	Object
/* 321:    */     //   204	5	11	localThrowable7	Throwable
/* 322:    */     //   235	42	12	localObject5	Object
/* 323:    */     //   257	5	13	localThrowable8	Throwable
/* 324:    */     //   287	37	14	localObject6	Object
/* 325:    */     //   306	4	15	localThrowable9	Throwable
/* 326:    */     // Exception table:
/* 327:    */     //   from	to	target	type
/* 328:    */     //   68	75	78	java/lang/Throwable
/* 329:    */     //   107	114	117	java/lang/Throwable
/* 330:    */     //   144	150	153	java/lang/Throwable
/* 331:    */     //   40	58	173	java/lang/Throwable
/* 332:    */     //   40	58	182	finally
/* 333:    */     //   173	184	182	finally
/* 334:    */     //   194	201	204	java/lang/Throwable
/* 335:    */     //   19	97	226	java/lang/Throwable
/* 336:    */     //   173	226	226	java/lang/Throwable
/* 337:    */     //   19	97	235	finally
/* 338:    */     //   173	237	235	finally
/* 339:    */     //   247	254	257	java/lang/Throwable
/* 340:    */     //   6	136	279	java/lang/Throwable
/* 341:    */     //   173	279	279	java/lang/Throwable
/* 342:    */     //   6	136	287	finally
/* 343:    */     //   173	289	287	finally
/* 344:    */     //   297	303	306	java/lang/Throwable
/* 345:    */     //   0	170	326	java/sql/SQLException
/* 346:    */     //   173	326	326	java/sql/SQLException
/* 347:    */   }
/* 348:    */   
/* 349:    */   static Trade addTrade(long paramLong, Block paramBlock, Order.Ask paramAsk, Order.Bid paramBid)
/* 350:    */   {
/* 351:123 */     Trade localTrade = new Trade(paramLong, paramBlock, paramAsk, paramBid);
/* 352:124 */     tradeTable.insert(localTrade);
/* 353:125 */     listeners.notify(localTrade, Event.TRADE);
/* 354:126 */     return localTrade;
/* 355:    */   }
/* 356:    */   
/* 357:    */   static void init() {}
/* 358:    */   
/* 359:    */   private Trade(long paramLong, Block paramBlock, Order.Ask paramAsk, Order.Bid paramBid)
/* 360:    */   {
/* 361:148 */     this.blockId = paramBlock.getId();
/* 362:149 */     this.height = paramBlock.getHeight();
/* 363:150 */     this.assetId = paramLong;
/* 364:151 */     this.timestamp = paramBlock.getTimestamp();
/* 365:152 */     this.askOrderId = paramAsk.getId();
/* 366:153 */     this.bidOrderId = paramBid.getId();
/* 367:154 */     this.askOrderHeight = paramAsk.getHeight();
/* 368:155 */     this.bidOrderHeight = paramBid.getHeight();
/* 369:156 */     this.sellerId = paramAsk.getAccountId();
/* 370:157 */     this.buyerId = paramBid.getAccountId();
/* 371:158 */     this.dbKey = tradeDbKeyFactory.newKey(this.askOrderId, this.bidOrderId);
/* 372:159 */     this.quantityQNT = Math.min(paramAsk.getQuantityQNT(), paramBid.getQuantityQNT());
/* 373:160 */     this.isBuy = ((this.askOrderHeight < this.bidOrderHeight) || ((this.askOrderHeight == this.bidOrderHeight) && (this.askOrderId < this.bidOrderId)));
/* 374:161 */     this.priceNQT = (this.isBuy ? paramAsk.getPriceNQT() : paramBid.getPriceNQT());
/* 375:    */   }
/* 376:    */   
/* 377:    */   private Trade(ResultSet paramResultSet)
/* 378:    */     throws SQLException
/* 379:    */   {
/* 380:165 */     this.assetId = paramResultSet.getLong("asset_id");
/* 381:166 */     this.blockId = paramResultSet.getLong("block_id");
/* 382:167 */     this.askOrderId = paramResultSet.getLong("ask_order_id");
/* 383:168 */     this.bidOrderId = paramResultSet.getLong("bid_order_id");
/* 384:169 */     this.askOrderHeight = paramResultSet.getInt("ask_order_height");
/* 385:170 */     this.bidOrderHeight = paramResultSet.getInt("bid_order_height");
/* 386:171 */     this.sellerId = paramResultSet.getLong("seller_id");
/* 387:172 */     this.buyerId = paramResultSet.getLong("buyer_id");
/* 388:173 */     this.dbKey = tradeDbKeyFactory.newKey(this.askOrderId, this.bidOrderId);
/* 389:174 */     this.quantityQNT = paramResultSet.getLong("quantity");
/* 390:175 */     this.priceNQT = paramResultSet.getLong("price");
/* 391:176 */     this.timestamp = paramResultSet.getInt("timestamp");
/* 392:177 */     this.height = paramResultSet.getInt("height");
/* 393:178 */     this.isBuy = ((this.askOrderHeight < this.bidOrderHeight) || ((this.askOrderHeight == this.bidOrderHeight) && (this.askOrderId < this.bidOrderId)));
/* 394:    */   }
/* 395:    */   
/* 396:    */   private void save(Connection paramConnection)
/* 397:    */     throws SQLException
/* 398:    */   {
/* 399:182 */     PreparedStatement localPreparedStatement = paramConnection.prepareStatement("INSERT INTO trade (asset_id, block_id, ask_order_id, bid_order_id, ask_order_height, bid_order_height, seller_id, buyer_id, quantity, price, timestamp, height) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");Object localObject1 = null;
/* 400:    */     try
/* 401:    */     {
/* 402:185 */       int i = 0;
/* 403:186 */       localPreparedStatement.setLong(++i, getAssetId());
/* 404:187 */       localPreparedStatement.setLong(++i, getBlockId());
/* 405:188 */       localPreparedStatement.setLong(++i, getAskOrderId());
/* 406:189 */       localPreparedStatement.setLong(++i, getBidOrderId());
/* 407:190 */       localPreparedStatement.setInt(++i, getAskOrderHeight());
/* 408:191 */       localPreparedStatement.setInt(++i, getBidOrderHeight());
/* 409:192 */       localPreparedStatement.setLong(++i, getSellerId());
/* 410:193 */       localPreparedStatement.setLong(++i, getBuyerId());
/* 411:194 */       localPreparedStatement.setLong(++i, getQuantityQNT());
/* 412:195 */       localPreparedStatement.setLong(++i, getPriceNQT());
/* 413:196 */       localPreparedStatement.setInt(++i, getTimestamp());
/* 414:197 */       localPreparedStatement.setInt(++i, getHeight());
/* 415:198 */       localPreparedStatement.executeUpdate();
/* 416:    */     }
/* 417:    */     catch (Throwable localThrowable2)
/* 418:    */     {
/* 419:182 */       localObject1 = localThrowable2;throw localThrowable2;
/* 420:    */     }
/* 421:    */     finally
/* 422:    */     {
/* 423:199 */       if (localPreparedStatement != null) {
/* 424:199 */         if (localObject1 != null) {
/* 425:    */           try
/* 426:    */           {
/* 427:199 */             localPreparedStatement.close();
/* 428:    */           }
/* 429:    */           catch (Throwable localThrowable3)
/* 430:    */           {
/* 431:199 */             ((Throwable)localObject1).addSuppressed(localThrowable3);
/* 432:    */           }
/* 433:    */         } else {
/* 434:199 */           localPreparedStatement.close();
/* 435:    */         }
/* 436:    */       }
/* 437:    */     }
/* 438:    */   }
/* 439:    */   
/* 440:    */   public long getBlockId()
/* 441:    */   {
/* 442:202 */     return this.blockId;
/* 443:    */   }
/* 444:    */   
/* 445:    */   public long getAskOrderId()
/* 446:    */   {
/* 447:204 */     return this.askOrderId;
/* 448:    */   }
/* 449:    */   
/* 450:    */   public long getBidOrderId()
/* 451:    */   {
/* 452:206 */     return this.bidOrderId;
/* 453:    */   }
/* 454:    */   
/* 455:    */   public int getAskOrderHeight()
/* 456:    */   {
/* 457:209 */     return this.askOrderHeight;
/* 458:    */   }
/* 459:    */   
/* 460:    */   public int getBidOrderHeight()
/* 461:    */   {
/* 462:213 */     return this.bidOrderHeight;
/* 463:    */   }
/* 464:    */   
/* 465:    */   public long getSellerId()
/* 466:    */   {
/* 467:217 */     return this.sellerId;
/* 468:    */   }
/* 469:    */   
/* 470:    */   public long getBuyerId()
/* 471:    */   {
/* 472:221 */     return this.buyerId;
/* 473:    */   }
/* 474:    */   
/* 475:    */   public long getQuantityQNT()
/* 476:    */   {
/* 477:224 */     return this.quantityQNT;
/* 478:    */   }
/* 479:    */   
/* 480:    */   public long getPriceNQT()
/* 481:    */   {
/* 482:226 */     return this.priceNQT;
/* 483:    */   }
/* 484:    */   
/* 485:    */   public long getAssetId()
/* 486:    */   {
/* 487:228 */     return this.assetId;
/* 488:    */   }
/* 489:    */   
/* 490:    */   public int getTimestamp()
/* 491:    */   {
/* 492:230 */     return this.timestamp;
/* 493:    */   }
/* 494:    */   
/* 495:    */   public int getHeight()
/* 496:    */   {
/* 497:233 */     return this.height;
/* 498:    */   }
/* 499:    */   
/* 500:    */   public boolean isBuy()
/* 501:    */   {
/* 502:237 */     return this.isBuy;
/* 503:    */   }
/* 504:    */   
/* 505:    */   public String toString()
/* 506:    */   {
/* 507:242 */     return "Trade asset: " + Convert.toUnsignedLong(this.assetId) + " ask: " + Convert.toUnsignedLong(this.askOrderId) + " bid: " + Convert.toUnsignedLong(this.bidOrderId) + " price: " + this.priceNQT + " quantity: " + this.quantityQNT + " height: " + this.height;
/* 508:    */   }
/* 509:    */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.Trade
 * JD-Core Version:    0.7.1
 */