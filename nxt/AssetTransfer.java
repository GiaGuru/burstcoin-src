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
/*  12:    */ import nxt.db.DbKey.LongKeyFactory;
/*  13:    */ import nxt.db.DbUtils;
/*  14:    */ import nxt.db.EntityDbTable;
/*  15:    */ import nxt.util.Listener;
/*  16:    */ import nxt.util.Listeners;
/*  17:    */ 
/*  18:    */ public final class AssetTransfer
/*  19:    */ {
/*  20:    */   public static enum Event
/*  21:    */   {
/*  22: 20 */     ASSET_TRANSFER;
/*  23:    */     
/*  24:    */     private Event() {}
/*  25:    */   }
/*  26:    */   
/*  27: 23 */   private static final Listeners<AssetTransfer, Event> listeners = new Listeners();
/*  28: 25 */   private static final DbKey.LongKeyFactory<AssetTransfer> transferDbKeyFactory = new DbKey.LongKeyFactory("id")
/*  29:    */   {
/*  30:    */     public DbKey newKey(AssetTransfer paramAnonymousAssetTransfer)
/*  31:    */     {
/*  32: 29 */       return paramAnonymousAssetTransfer.dbKey;
/*  33:    */     }
/*  34:    */   };
/*  35: 34 */   private static final EntityDbTable<AssetTransfer> assetTransferTable = new EntityDbTable("asset_transfer", transferDbKeyFactory)
/*  36:    */   {
/*  37:    */     protected AssetTransfer load(Connection paramAnonymousConnection, ResultSet paramAnonymousResultSet)
/*  38:    */       throws SQLException
/*  39:    */     {
/*  40: 38 */       return new AssetTransfer(paramAnonymousResultSet, null);
/*  41:    */     }
/*  42:    */     
/*  43:    */     protected void save(Connection paramAnonymousConnection, AssetTransfer paramAnonymousAssetTransfer)
/*  44:    */       throws SQLException
/*  45:    */     {
/*  46: 43 */       paramAnonymousAssetTransfer.save(paramAnonymousConnection);
/*  47:    */     }
/*  48:    */   };
/*  49:    */   private final long id;
/*  50:    */   private final DbKey dbKey;
/*  51:    */   private final long assetId;
/*  52:    */   private final int height;
/*  53:    */   private final long senderId;
/*  54:    */   private final long recipientId;
/*  55:    */   private final long quantityQNT;
/*  56:    */   private final int timestamp;
/*  57:    */   
/*  58:    */   public static DbIterator<AssetTransfer> getAllTransfers(int paramInt1, int paramInt2)
/*  59:    */   {
/*  60: 49 */     return assetTransferTable.getAll(paramInt1, paramInt2);
/*  61:    */   }
/*  62:    */   
/*  63:    */   public static int getCount()
/*  64:    */   {
/*  65: 53 */     return assetTransferTable.getCount();
/*  66:    */   }
/*  67:    */   
/*  68:    */   public static boolean addListener(Listener<AssetTransfer> paramListener, Event paramEvent)
/*  69:    */   {
/*  70: 57 */     return listeners.addListener(paramListener, paramEvent);
/*  71:    */   }
/*  72:    */   
/*  73:    */   public static boolean removeListener(Listener<AssetTransfer> paramListener, Event paramEvent)
/*  74:    */   {
/*  75: 61 */     return listeners.removeListener(paramListener, paramEvent);
/*  76:    */   }
/*  77:    */   
/*  78:    */   public static DbIterator<AssetTransfer> getAssetTransfers(long paramLong, int paramInt1, int paramInt2)
/*  79:    */   {
/*  80: 65 */     return assetTransferTable.getManyBy(new DbClause.LongClause("asset_id", paramLong), paramInt1, paramInt2);
/*  81:    */   }
/*  82:    */   
/*  83:    */   public static DbIterator<AssetTransfer> getAccountAssetTransfers(long paramLong, int paramInt1, int paramInt2)
/*  84:    */   {
/*  85: 69 */     Connection localConnection = null;
/*  86:    */     try
/*  87:    */     {
/*  88: 71 */       localConnection = Db.getConnection();
/*  89: 72 */       PreparedStatement localPreparedStatement = localConnection.prepareStatement("SELECT * FROM asset_transfer WHERE sender_id = ? UNION ALL SELECT * FROM asset_transfer WHERE recipient_id = ? AND sender_id <> ? ORDER BY height DESC" + DbUtils.limitsClause(paramInt1, paramInt2));
/*  90:    */       
/*  91:    */ 
/*  92: 75 */       int i = 0;
/*  93: 76 */       localPreparedStatement.setLong(++i, paramLong);
/*  94: 77 */       localPreparedStatement.setLong(++i, paramLong);
/*  95: 78 */       localPreparedStatement.setLong(++i, paramLong);
/*  96: 79 */       i++;DbUtils.setLimits(i, localPreparedStatement, paramInt1, paramInt2);
/*  97: 80 */       return assetTransferTable.getManyBy(localConnection, localPreparedStatement, false);
/*  98:    */     }
/*  99:    */     catch (SQLException localSQLException)
/* 100:    */     {
/* 101: 82 */       DbUtils.close(new AutoCloseable[] { localConnection });
/* 102: 83 */       throw new RuntimeException(localSQLException.toString(), localSQLException);
/* 103:    */     }
/* 104:    */   }
/* 105:    */   
/* 106:    */   public static DbIterator<AssetTransfer> getAccountAssetTransfers(long paramLong1, long paramLong2, int paramInt1, int paramInt2)
/* 107:    */   {
/* 108: 88 */     Connection localConnection = null;
/* 109:    */     try
/* 110:    */     {
/* 111: 90 */       localConnection = Db.getConnection();
/* 112: 91 */       PreparedStatement localPreparedStatement = localConnection.prepareStatement("SELECT * FROM asset_transfer WHERE sender_id = ? AND asset_id = ? UNION ALL SELECT * FROM asset_transfer WHERE recipient_id = ? AND sender_id <> ? AND asset_id = ? ORDER BY height DESC" + DbUtils.limitsClause(paramInt1, paramInt2));
/* 113:    */       
/* 114:    */ 
/* 115: 94 */       int i = 0;
/* 116: 95 */       localPreparedStatement.setLong(++i, paramLong1);
/* 117: 96 */       localPreparedStatement.setLong(++i, paramLong2);
/* 118: 97 */       localPreparedStatement.setLong(++i, paramLong1);
/* 119: 98 */       localPreparedStatement.setLong(++i, paramLong1);
/* 120: 99 */       localPreparedStatement.setLong(++i, paramLong2);
/* 121:100 */       i++;DbUtils.setLimits(i, localPreparedStatement, paramInt1, paramInt2);
/* 122:101 */       return assetTransferTable.getManyBy(localConnection, localPreparedStatement, false);
/* 123:    */     }
/* 124:    */     catch (SQLException localSQLException)
/* 125:    */     {
/* 126:103 */       DbUtils.close(new AutoCloseable[] { localConnection });
/* 127:104 */       throw new RuntimeException(localSQLException.toString(), localSQLException);
/* 128:    */     }
/* 129:    */   }
/* 130:    */   
/* 131:    */   /* Error */
/* 132:    */   public static int getTransferCount(long paramLong)
/* 133:    */   {
/* 134:    */     // Byte code:
/* 135:    */     //   0: invokestatic 14	nxt/db/Db:getConnection	()Ljava/sql/Connection;
/* 136:    */     //   3: astore_2
/* 137:    */     //   4: aconst_null
/* 138:    */     //   5: astore_3
/* 139:    */     //   6: aload_2
/* 140:    */     //   7: ldc 32
/* 141:    */     //   9: invokeinterface 21 2 0
/* 142:    */     //   14: astore 4
/* 143:    */     //   16: aconst_null
/* 144:    */     //   17: astore 5
/* 145:    */     //   19: aload 4
/* 146:    */     //   21: iconst_1
/* 147:    */     //   22: lload_0
/* 148:    */     //   23: invokeinterface 22 4 0
/* 149:    */     //   28: aload 4
/* 150:    */     //   30: invokeinterface 33 1 0
/* 151:    */     //   35: astore 6
/* 152:    */     //   37: aconst_null
/* 153:    */     //   38: astore 7
/* 154:    */     //   40: aload 6
/* 155:    */     //   42: invokeinterface 34 1 0
/* 156:    */     //   47: pop
/* 157:    */     //   48: aload 6
/* 158:    */     //   50: iconst_1
/* 159:    */     //   51: invokeinterface 35 2 0
/* 160:    */     //   56: istore 8
/* 161:    */     //   58: aload 6
/* 162:    */     //   60: ifnull +37 -> 97
/* 163:    */     //   63: aload 7
/* 164:    */     //   65: ifnull +25 -> 90
/* 165:    */     //   68: aload 6
/* 166:    */     //   70: invokeinterface 36 1 0
/* 167:    */     //   75: goto +22 -> 97
/* 168:    */     //   78: astore 9
/* 169:    */     //   80: aload 7
/* 170:    */     //   82: aload 9
/* 171:    */     //   84: invokevirtual 38	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 172:    */     //   87: goto +10 -> 97
/* 173:    */     //   90: aload 6
/* 174:    */     //   92: invokeinterface 36 1 0
/* 175:    */     //   97: aload 4
/* 176:    */     //   99: ifnull +37 -> 136
/* 177:    */     //   102: aload 5
/* 178:    */     //   104: ifnull +25 -> 129
/* 179:    */     //   107: aload 4
/* 180:    */     //   109: invokeinterface 39 1 0
/* 181:    */     //   114: goto +22 -> 136
/* 182:    */     //   117: astore 9
/* 183:    */     //   119: aload 5
/* 184:    */     //   121: aload 9
/* 185:    */     //   123: invokevirtual 38	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 186:    */     //   126: goto +10 -> 136
/* 187:    */     //   129: aload 4
/* 188:    */     //   131: invokeinterface 39 1 0
/* 189:    */     //   136: aload_2
/* 190:    */     //   137: ifnull +33 -> 170
/* 191:    */     //   140: aload_3
/* 192:    */     //   141: ifnull +23 -> 164
/* 193:    */     //   144: aload_2
/* 194:    */     //   145: invokeinterface 40 1 0
/* 195:    */     //   150: goto +20 -> 170
/* 196:    */     //   153: astore 9
/* 197:    */     //   155: aload_3
/* 198:    */     //   156: aload 9
/* 199:    */     //   158: invokevirtual 38	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 200:    */     //   161: goto +9 -> 170
/* 201:    */     //   164: aload_2
/* 202:    */     //   165: invokeinterface 40 1 0
/* 203:    */     //   170: iload 8
/* 204:    */     //   172: ireturn
/* 205:    */     //   173: astore 8
/* 206:    */     //   175: aload 8
/* 207:    */     //   177: astore 7
/* 208:    */     //   179: aload 8
/* 209:    */     //   181: athrow
/* 210:    */     //   182: astore 10
/* 211:    */     //   184: aload 6
/* 212:    */     //   186: ifnull +37 -> 223
/* 213:    */     //   189: aload 7
/* 214:    */     //   191: ifnull +25 -> 216
/* 215:    */     //   194: aload 6
/* 216:    */     //   196: invokeinterface 36 1 0
/* 217:    */     //   201: goto +22 -> 223
/* 218:    */     //   204: astore 11
/* 219:    */     //   206: aload 7
/* 220:    */     //   208: aload 11
/* 221:    */     //   210: invokevirtual 38	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 222:    */     //   213: goto +10 -> 223
/* 223:    */     //   216: aload 6
/* 224:    */     //   218: invokeinterface 36 1 0
/* 225:    */     //   223: aload 10
/* 226:    */     //   225: athrow
/* 227:    */     //   226: astore 6
/* 228:    */     //   228: aload 6
/* 229:    */     //   230: astore 5
/* 230:    */     //   232: aload 6
/* 231:    */     //   234: athrow
/* 232:    */     //   235: astore 12
/* 233:    */     //   237: aload 4
/* 234:    */     //   239: ifnull +37 -> 276
/* 235:    */     //   242: aload 5
/* 236:    */     //   244: ifnull +25 -> 269
/* 237:    */     //   247: aload 4
/* 238:    */     //   249: invokeinterface 39 1 0
/* 239:    */     //   254: goto +22 -> 276
/* 240:    */     //   257: astore 13
/* 241:    */     //   259: aload 5
/* 242:    */     //   261: aload 13
/* 243:    */     //   263: invokevirtual 38	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 244:    */     //   266: goto +10 -> 276
/* 245:    */     //   269: aload 4
/* 246:    */     //   271: invokeinterface 39 1 0
/* 247:    */     //   276: aload 12
/* 248:    */     //   278: athrow
/* 249:    */     //   279: astore 4
/* 250:    */     //   281: aload 4
/* 251:    */     //   283: astore_3
/* 252:    */     //   284: aload 4
/* 253:    */     //   286: athrow
/* 254:    */     //   287: astore 14
/* 255:    */     //   289: aload_2
/* 256:    */     //   290: ifnull +33 -> 323
/* 257:    */     //   293: aload_3
/* 258:    */     //   294: ifnull +23 -> 317
/* 259:    */     //   297: aload_2
/* 260:    */     //   298: invokeinterface 40 1 0
/* 261:    */     //   303: goto +20 -> 323
/* 262:    */     //   306: astore 15
/* 263:    */     //   308: aload_3
/* 264:    */     //   309: aload 15
/* 265:    */     //   311: invokevirtual 38	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 266:    */     //   314: goto +9 -> 323
/* 267:    */     //   317: aload_2
/* 268:    */     //   318: invokeinterface 40 1 0
/* 269:    */     //   323: aload 14
/* 270:    */     //   325: athrow
/* 271:    */     //   326: astore_2
/* 272:    */     //   327: new 28	java/lang/RuntimeException
/* 273:    */     //   330: dup
/* 274:    */     //   331: aload_2
/* 275:    */     //   332: invokevirtual 29	java/sql/SQLException:toString	()Ljava/lang/String;
/* 276:    */     //   335: aload_2
/* 277:    */     //   336: invokespecial 30	java/lang/RuntimeException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
/* 278:    */     //   339: athrow
/* 279:    */     // Line number table:
/* 280:    */     //   Java source line #109	-> byte code offset #0
/* 281:    */     //   Java source line #110	-> byte code offset #6
/* 282:    */     //   Java source line #109	-> byte code offset #16
/* 283:    */     //   Java source line #111	-> byte code offset #19
/* 284:    */     //   Java source line #112	-> byte code offset #28
/* 285:    */     //   Java source line #113	-> byte code offset #40
/* 286:    */     //   Java source line #114	-> byte code offset #48
/* 287:    */     //   Java source line #115	-> byte code offset #58
/* 288:    */     //   Java source line #116	-> byte code offset #97
/* 289:    */     //   Java source line #112	-> byte code offset #173
/* 290:    */     //   Java source line #115	-> byte code offset #182
/* 291:    */     //   Java source line #109	-> byte code offset #226
/* 292:    */     //   Java source line #116	-> byte code offset #235
/* 293:    */     //   Java source line #109	-> byte code offset #279
/* 294:    */     //   Java source line #116	-> byte code offset #287
/* 295:    */     //   Java source line #117	-> byte code offset #327
/* 296:    */     // Local variable table:
/* 297:    */     //   start	length	slot	name	signature
/* 298:    */     //   0	340	0	paramLong	long
/* 299:    */     //   3	315	2	localConnection	Connection
/* 300:    */     //   326	10	2	localSQLException	SQLException
/* 301:    */     //   5	304	3	localObject1	Object
/* 302:    */     //   14	256	4	localPreparedStatement	PreparedStatement
/* 303:    */     //   279	6	4	localThrowable1	Throwable
/* 304:    */     //   17	243	5	localObject2	Object
/* 305:    */     //   35	182	6	localResultSet	ResultSet
/* 306:    */     //   226	7	6	localThrowable2	Throwable
/* 307:    */     //   38	169	7	localObject3	Object
/* 308:    */     //   56	115	8	i	int
/* 309:    */     //   173	7	8	localThrowable3	Throwable
/* 310:    */     //   78	5	9	localThrowable4	Throwable
/* 311:    */     //   117	5	9	localThrowable5	Throwable
/* 312:    */     //   153	4	9	localThrowable6	Throwable
/* 313:    */     //   182	42	10	localObject4	Object
/* 314:    */     //   204	5	11	localThrowable7	Throwable
/* 315:    */     //   235	42	12	localObject5	Object
/* 316:    */     //   257	5	13	localThrowable8	Throwable
/* 317:    */     //   287	37	14	localObject6	Object
/* 318:    */     //   306	4	15	localThrowable9	Throwable
/* 319:    */     // Exception table:
/* 320:    */     //   from	to	target	type
/* 321:    */     //   68	75	78	java/lang/Throwable
/* 322:    */     //   107	114	117	java/lang/Throwable
/* 323:    */     //   144	150	153	java/lang/Throwable
/* 324:    */     //   40	58	173	java/lang/Throwable
/* 325:    */     //   40	58	182	finally
/* 326:    */     //   173	184	182	finally
/* 327:    */     //   194	201	204	java/lang/Throwable
/* 328:    */     //   19	97	226	java/lang/Throwable
/* 329:    */     //   173	226	226	java/lang/Throwable
/* 330:    */     //   19	97	235	finally
/* 331:    */     //   173	237	235	finally
/* 332:    */     //   247	254	257	java/lang/Throwable
/* 333:    */     //   6	136	279	java/lang/Throwable
/* 334:    */     //   173	279	279	java/lang/Throwable
/* 335:    */     //   6	136	287	finally
/* 336:    */     //   173	289	287	finally
/* 337:    */     //   297	303	306	java/lang/Throwable
/* 338:    */     //   0	170	326	java/sql/SQLException
/* 339:    */     //   173	326	326	java/sql/SQLException
/* 340:    */   }
/* 341:    */   
/* 342:    */   static AssetTransfer addAssetTransfer(Transaction paramTransaction, Attachment.ColoredCoinsAssetTransfer paramColoredCoinsAssetTransfer)
/* 343:    */   {
/* 344:122 */     AssetTransfer localAssetTransfer = new AssetTransfer(paramTransaction, paramColoredCoinsAssetTransfer);
/* 345:123 */     assetTransferTable.insert(localAssetTransfer);
/* 346:124 */     listeners.notify(localAssetTransfer, Event.ASSET_TRANSFER);
/* 347:125 */     return localAssetTransfer;
/* 348:    */   }
/* 349:    */   
/* 350:    */   static void init() {}
/* 351:    */   
/* 352:    */   private AssetTransfer(Transaction paramTransaction, Attachment.ColoredCoinsAssetTransfer paramColoredCoinsAssetTransfer)
/* 353:    */   {
/* 354:141 */     this.id = paramTransaction.getId();
/* 355:142 */     this.dbKey = transferDbKeyFactory.newKey(this.id);
/* 356:143 */     this.height = paramTransaction.getHeight();
/* 357:144 */     this.assetId = paramColoredCoinsAssetTransfer.getAssetId();
/* 358:145 */     this.senderId = paramTransaction.getSenderId();
/* 359:146 */     this.recipientId = paramTransaction.getRecipientId();
/* 360:147 */     this.quantityQNT = paramColoredCoinsAssetTransfer.getQuantityQNT();
/* 361:148 */     this.timestamp = paramTransaction.getBlockTimestamp();
/* 362:    */   }
/* 363:    */   
/* 364:    */   private AssetTransfer(ResultSet paramResultSet)
/* 365:    */     throws SQLException
/* 366:    */   {
/* 367:152 */     this.id = paramResultSet.getLong("id");
/* 368:153 */     this.dbKey = transferDbKeyFactory.newKey(this.id);
/* 369:154 */     this.assetId = paramResultSet.getLong("asset_id");
/* 370:155 */     this.senderId = paramResultSet.getLong("sender_id");
/* 371:156 */     this.recipientId = paramResultSet.getLong("recipient_id");
/* 372:157 */     this.quantityQNT = paramResultSet.getLong("quantity");
/* 373:158 */     this.timestamp = paramResultSet.getInt("timestamp");
/* 374:159 */     this.height = paramResultSet.getInt("height");
/* 375:    */   }
/* 376:    */   
/* 377:    */   private void save(Connection paramConnection)
/* 378:    */     throws SQLException
/* 379:    */   {
/* 380:163 */     PreparedStatement localPreparedStatement = paramConnection.prepareStatement("INSERT INTO asset_transfer (id, asset_id, sender_id, recipient_id, quantity, timestamp, height) VALUES (?, ?, ?, ?, ?, ?, ?)");Object localObject1 = null;
/* 381:    */     try
/* 382:    */     {
/* 383:166 */       int i = 0;
/* 384:167 */       localPreparedStatement.setLong(++i, getId());
/* 385:168 */       localPreparedStatement.setLong(++i, getAssetId());
/* 386:169 */       localPreparedStatement.setLong(++i, getSenderId());
/* 387:170 */       localPreparedStatement.setLong(++i, getRecipientId());
/* 388:171 */       localPreparedStatement.setLong(++i, getQuantityQNT());
/* 389:172 */       localPreparedStatement.setInt(++i, getTimestamp());
/* 390:173 */       localPreparedStatement.setInt(++i, getHeight());
/* 391:174 */       localPreparedStatement.executeUpdate();
/* 392:    */     }
/* 393:    */     catch (Throwable localThrowable2)
/* 394:    */     {
/* 395:163 */       localObject1 = localThrowable2;throw localThrowable2;
/* 396:    */     }
/* 397:    */     finally
/* 398:    */     {
/* 399:175 */       if (localPreparedStatement != null) {
/* 400:175 */         if (localObject1 != null) {
/* 401:    */           try
/* 402:    */           {
/* 403:175 */             localPreparedStatement.close();
/* 404:    */           }
/* 405:    */           catch (Throwable localThrowable3)
/* 406:    */           {
/* 407:175 */             ((Throwable)localObject1).addSuppressed(localThrowable3);
/* 408:    */           }
/* 409:    */         } else {
/* 410:175 */           localPreparedStatement.close();
/* 411:    */         }
/* 412:    */       }
/* 413:    */     }
/* 414:    */   }
/* 415:    */   
/* 416:    */   public long getId()
/* 417:    */   {
/* 418:179 */     return this.id;
/* 419:    */   }
/* 420:    */   
/* 421:    */   public long getAssetId()
/* 422:    */   {
/* 423:182 */     return this.assetId;
/* 424:    */   }
/* 425:    */   
/* 426:    */   public long getSenderId()
/* 427:    */   {
/* 428:185 */     return this.senderId;
/* 429:    */   }
/* 430:    */   
/* 431:    */   public long getRecipientId()
/* 432:    */   {
/* 433:189 */     return this.recipientId;
/* 434:    */   }
/* 435:    */   
/* 436:    */   public long getQuantityQNT()
/* 437:    */   {
/* 438:192 */     return this.quantityQNT;
/* 439:    */   }
/* 440:    */   
/* 441:    */   public int getTimestamp()
/* 442:    */   {
/* 443:195 */     return this.timestamp;
/* 444:    */   }
/* 445:    */   
/* 446:    */   public int getHeight()
/* 447:    */   {
/* 448:199 */     return this.height;
/* 449:    */   }
/* 450:    */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.AssetTransfer
 * JD-Core Version:    0.7.1
 */