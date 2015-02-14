/*   1:    */ package nxt;
/*   2:    */ 
/*   3:    */ import java.sql.Connection;
/*   4:    */ import java.sql.PreparedStatement;
/*   5:    */ import java.sql.ResultSet;
/*   6:    */ import java.sql.SQLException;
/*   7:    */ import java.util.concurrent.atomic.AtomicReference;
/*   8:    */ import nxt.db.Db;
/*   9:    */ import nxt.db.DbIterator;
/*  10:    */ import nxt.db.DbIterator.ResultSetReader;
/*  11:    */ import nxt.db.DbUtils;
/*  12:    */ 
/*  13:    */ final class BlockchainImpl
/*  14:    */   implements Blockchain
/*  15:    */ {
/*  16: 17 */   private static final BlockchainImpl instance = new BlockchainImpl();
/*  17:    */   
/*  18:    */   static BlockchainImpl getInstance()
/*  19:    */   {
/*  20: 20 */     return instance;
/*  21:    */   }
/*  22:    */   
/*  23: 25 */   private final AtomicReference<BlockImpl> lastBlock = new AtomicReference();
/*  24:    */   
/*  25:    */   public BlockImpl getLastBlock()
/*  26:    */   {
/*  27: 29 */     return (BlockImpl)this.lastBlock.get();
/*  28:    */   }
/*  29:    */   
/*  30:    */   void setLastBlock(BlockImpl paramBlockImpl)
/*  31:    */   {
/*  32: 33 */     this.lastBlock.set(paramBlockImpl);
/*  33:    */   }
/*  34:    */   
/*  35:    */   void setLastBlock(BlockImpl paramBlockImpl1, BlockImpl paramBlockImpl2)
/*  36:    */   {
/*  37: 37 */     if (!this.lastBlock.compareAndSet(paramBlockImpl1, paramBlockImpl2)) {
/*  38: 38 */       throw new IllegalStateException("Last block is no longer previous block");
/*  39:    */     }
/*  40:    */   }
/*  41:    */   
/*  42:    */   public int getHeight()
/*  43:    */   {
/*  44: 44 */     BlockImpl localBlockImpl = (BlockImpl)this.lastBlock.get();
/*  45: 45 */     return localBlockImpl == null ? 0 : localBlockImpl.getHeight();
/*  46:    */   }
/*  47:    */   
/*  48:    */   public BlockImpl getLastBlock(int paramInt)
/*  49:    */   {
/*  50: 50 */     BlockImpl localBlockImpl = (BlockImpl)this.lastBlock.get();
/*  51: 51 */     if (paramInt >= localBlockImpl.getTimestamp()) {
/*  52: 52 */       return localBlockImpl;
/*  53:    */     }
/*  54: 54 */     return BlockDb.findLastBlock(paramInt);
/*  55:    */   }
/*  56:    */   
/*  57:    */   public BlockImpl getBlock(long paramLong)
/*  58:    */   {
/*  59: 59 */     BlockImpl localBlockImpl = (BlockImpl)this.lastBlock.get();
/*  60: 60 */     if (localBlockImpl.getId() == paramLong) {
/*  61: 61 */       return localBlockImpl;
/*  62:    */     }
/*  63: 63 */     return BlockDb.findBlock(paramLong);
/*  64:    */   }
/*  65:    */   
/*  66:    */   public boolean hasBlock(long paramLong)
/*  67:    */   {
/*  68: 68 */     return (((BlockImpl)this.lastBlock.get()).getId() == paramLong) || (BlockDb.hasBlock(paramLong));
/*  69:    */   }
/*  70:    */   
/*  71:    */   public DbIterator<BlockImpl> getAllBlocks()
/*  72:    */   {
/*  73: 73 */     Connection localConnection = null;
/*  74:    */     try
/*  75:    */     {
/*  76: 75 */       localConnection = Db.getConnection();
/*  77: 76 */       PreparedStatement localPreparedStatement = localConnection.prepareStatement("SELECT * FROM block ORDER BY db_id ASC");
/*  78: 77 */       return getBlocks(localConnection, localPreparedStatement);
/*  79:    */     }
/*  80:    */     catch (SQLException localSQLException)
/*  81:    */     {
/*  82: 79 */       DbUtils.close(new AutoCloseable[] { localConnection });
/*  83: 80 */       throw new RuntimeException(localSQLException.toString(), localSQLException);
/*  84:    */     }
/*  85:    */   }
/*  86:    */   
/*  87:    */   public DbIterator<BlockImpl> getBlocks(int paramInt1, int paramInt2)
/*  88:    */   {
/*  89: 86 */     Connection localConnection = null;
/*  90:    */     try
/*  91:    */     {
/*  92: 88 */       localConnection = Db.getConnection();
/*  93: 89 */       PreparedStatement localPreparedStatement = localConnection.prepareStatement("SELECT * FROM block WHERE height <= ? AND height >= ? ORDER BY height DESC");
/*  94: 90 */       int i = getHeight();
/*  95: 91 */       localPreparedStatement.setInt(1, i - Math.max(paramInt1, 0));
/*  96: 92 */       localPreparedStatement.setInt(2, paramInt2 > 0 ? i - paramInt2 : 0);
/*  97: 93 */       return getBlocks(localConnection, localPreparedStatement);
/*  98:    */     }
/*  99:    */     catch (SQLException localSQLException)
/* 100:    */     {
/* 101: 95 */       DbUtils.close(new AutoCloseable[] { localConnection });
/* 102: 96 */       throw new RuntimeException(localSQLException.toString(), localSQLException);
/* 103:    */     }
/* 104:    */   }
/* 105:    */   
/* 106:    */   public DbIterator<BlockImpl> getBlocks(Account paramAccount, int paramInt)
/* 107:    */   {
/* 108:102 */     return getBlocks(paramAccount, paramInt, 0, -1);
/* 109:    */   }
/* 110:    */   
/* 111:    */   public DbIterator<BlockImpl> getBlocks(Account paramAccount, int paramInt1, int paramInt2, int paramInt3)
/* 112:    */   {
/* 113:107 */     Connection localConnection = null;
/* 114:    */     try
/* 115:    */     {
/* 116:109 */       localConnection = Db.getConnection();
/* 117:110 */       PreparedStatement localPreparedStatement = localConnection.prepareStatement("SELECT * FROM block WHERE generator_id = ? " + (paramInt1 > 0 ? " AND timestamp >= ? " : " ") + "ORDER BY db_id DESC" + DbUtils.limitsClause(paramInt2, paramInt3));
/* 118:    */       
/* 119:    */ 
/* 120:113 */       int i = 0;
/* 121:114 */       localPreparedStatement.setLong(++i, paramAccount.getId());
/* 122:115 */       if (paramInt1 > 0) {
/* 123:116 */         localPreparedStatement.setInt(++i, paramInt1);
/* 124:    */       }
/* 125:118 */       i++;DbUtils.setLimits(i, localPreparedStatement, paramInt2, paramInt3);
/* 126:119 */       return getBlocks(localConnection, localPreparedStatement);
/* 127:    */     }
/* 128:    */     catch (SQLException localSQLException)
/* 129:    */     {
/* 130:121 */       DbUtils.close(new AutoCloseable[] { localConnection });
/* 131:122 */       throw new RuntimeException(localSQLException.toString(), localSQLException);
/* 132:    */     }
/* 133:    */   }
/* 134:    */   
/* 135:    */   public DbIterator<BlockImpl> getBlocks(Connection paramConnection, PreparedStatement paramPreparedStatement)
/* 136:    */   {
/* 137:128 */     new DbIterator(paramConnection, paramPreparedStatement, new DbIterator.ResultSetReader()
/* 138:    */     {
/* 139:    */       public BlockImpl get(Connection paramAnonymousConnection, ResultSet paramAnonymousResultSet)
/* 140:    */         throws NxtException.ValidationException
/* 141:    */       {
/* 142:131 */         return BlockDb.loadBlock(paramAnonymousConnection, paramAnonymousResultSet);
/* 143:    */       }
/* 144:    */     });
/* 145:    */   }
/* 146:    */   
/* 147:    */   /* Error */
/* 148:    */   public java.util.List<Long> getBlockIdsAfter(long paramLong, int paramInt)
/* 149:    */   {
/* 150:    */     // Byte code:
/* 151:    */     //   0: iload_3
/* 152:    */     //   1: sipush 1440
/* 153:    */     //   4: if_icmple +13 -> 17
/* 154:    */     //   7: new 50	java/lang/IllegalArgumentException
/* 155:    */     //   10: dup
/* 156:    */     //   11: ldc 51
/* 157:    */     //   13: invokespecial 52	java/lang/IllegalArgumentException:<init>	(Ljava/lang/String;)V
/* 158:    */     //   16: athrow
/* 159:    */     //   17: invokestatic 19	nxt/db/Db:getConnection	()Ljava/sql/Connection;
/* 160:    */     //   20: astore 4
/* 161:    */     //   22: aconst_null
/* 162:    */     //   23: astore 5
/* 163:    */     //   25: aload 4
/* 164:    */     //   27: ldc 53
/* 165:    */     //   29: invokeinterface 21 2 0
/* 166:    */     //   34: astore 6
/* 167:    */     //   36: aconst_null
/* 168:    */     //   37: astore 7
/* 169:    */     //   39: new 54	java/util/ArrayList
/* 170:    */     //   42: dup
/* 171:    */     //   43: invokespecial 55	java/util/ArrayList:<init>	()V
/* 172:    */     //   46: astore 8
/* 173:    */     //   48: aload 6
/* 174:    */     //   50: iconst_1
/* 175:    */     //   51: lload_1
/* 176:    */     //   52: invokeinterface 44 4 0
/* 177:    */     //   57: aload 6
/* 178:    */     //   59: iconst_2
/* 179:    */     //   60: iload_3
/* 180:    */     //   61: invokeinterface 32 3 0
/* 181:    */     //   66: aload 6
/* 182:    */     //   68: invokeinterface 56 1 0
/* 183:    */     //   73: astore 9
/* 184:    */     //   75: aconst_null
/* 185:    */     //   76: astore 10
/* 186:    */     //   78: aload 9
/* 187:    */     //   80: invokeinterface 57 1 0
/* 188:    */     //   85: ifeq +26 -> 111
/* 189:    */     //   88: aload 8
/* 190:    */     //   90: aload 9
/* 191:    */     //   92: ldc 58
/* 192:    */     //   94: invokeinterface 59 2 0
/* 193:    */     //   99: invokestatic 60	java/lang/Long:valueOf	(J)Ljava/lang/Long;
/* 194:    */     //   102: invokeinterface 61 2 0
/* 195:    */     //   107: pop
/* 196:    */     //   108: goto -30 -> 78
/* 197:    */     //   111: aload 9
/* 198:    */     //   113: ifnull +93 -> 206
/* 199:    */     //   116: aload 10
/* 200:    */     //   118: ifnull +25 -> 143
/* 201:    */     //   121: aload 9
/* 202:    */     //   123: invokeinterface 62 1 0
/* 203:    */     //   128: goto +78 -> 206
/* 204:    */     //   131: astore 11
/* 205:    */     //   133: aload 10
/* 206:    */     //   135: aload 11
/* 207:    */     //   137: invokevirtual 64	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 208:    */     //   140: goto +66 -> 206
/* 209:    */     //   143: aload 9
/* 210:    */     //   145: invokeinterface 62 1 0
/* 211:    */     //   150: goto +56 -> 206
/* 212:    */     //   153: astore 11
/* 213:    */     //   155: aload 11
/* 214:    */     //   157: astore 10
/* 215:    */     //   159: aload 11
/* 216:    */     //   161: athrow
/* 217:    */     //   162: astore 12
/* 218:    */     //   164: aload 9
/* 219:    */     //   166: ifnull +37 -> 203
/* 220:    */     //   169: aload 10
/* 221:    */     //   171: ifnull +25 -> 196
/* 222:    */     //   174: aload 9
/* 223:    */     //   176: invokeinterface 62 1 0
/* 224:    */     //   181: goto +22 -> 203
/* 225:    */     //   184: astore 13
/* 226:    */     //   186: aload 10
/* 227:    */     //   188: aload 13
/* 228:    */     //   190: invokevirtual 64	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 229:    */     //   193: goto +10 -> 203
/* 230:    */     //   196: aload 9
/* 231:    */     //   198: invokeinterface 62 1 0
/* 232:    */     //   203: aload 12
/* 233:    */     //   205: athrow
/* 234:    */     //   206: aload 8
/* 235:    */     //   208: astore 9
/* 236:    */     //   210: aload 6
/* 237:    */     //   212: ifnull +37 -> 249
/* 238:    */     //   215: aload 7
/* 239:    */     //   217: ifnull +25 -> 242
/* 240:    */     //   220: aload 6
/* 241:    */     //   222: invokeinterface 65 1 0
/* 242:    */     //   227: goto +22 -> 249
/* 243:    */     //   230: astore 10
/* 244:    */     //   232: aload 7
/* 245:    */     //   234: aload 10
/* 246:    */     //   236: invokevirtual 64	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 247:    */     //   239: goto +10 -> 249
/* 248:    */     //   242: aload 6
/* 249:    */     //   244: invokeinterface 65 1 0
/* 250:    */     //   249: aload 4
/* 251:    */     //   251: ifnull +37 -> 288
/* 252:    */     //   254: aload 5
/* 253:    */     //   256: ifnull +25 -> 281
/* 254:    */     //   259: aload 4
/* 255:    */     //   261: invokeinterface 66 1 0
/* 256:    */     //   266: goto +22 -> 288
/* 257:    */     //   269: astore 10
/* 258:    */     //   271: aload 5
/* 259:    */     //   273: aload 10
/* 260:    */     //   275: invokevirtual 64	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 261:    */     //   278: goto +10 -> 288
/* 262:    */     //   281: aload 4
/* 263:    */     //   283: invokeinterface 66 1 0
/* 264:    */     //   288: aload 9
/* 265:    */     //   290: areturn
/* 266:    */     //   291: astore 8
/* 267:    */     //   293: aload 8
/* 268:    */     //   295: astore 7
/* 269:    */     //   297: aload 8
/* 270:    */     //   299: athrow
/* 271:    */     //   300: astore 14
/* 272:    */     //   302: aload 6
/* 273:    */     //   304: ifnull +37 -> 341
/* 274:    */     //   307: aload 7
/* 275:    */     //   309: ifnull +25 -> 334
/* 276:    */     //   312: aload 6
/* 277:    */     //   314: invokeinterface 65 1 0
/* 278:    */     //   319: goto +22 -> 341
/* 279:    */     //   322: astore 15
/* 280:    */     //   324: aload 7
/* 281:    */     //   326: aload 15
/* 282:    */     //   328: invokevirtual 64	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 283:    */     //   331: goto +10 -> 341
/* 284:    */     //   334: aload 6
/* 285:    */     //   336: invokeinterface 65 1 0
/* 286:    */     //   341: aload 14
/* 287:    */     //   343: athrow
/* 288:    */     //   344: astore 6
/* 289:    */     //   346: aload 6
/* 290:    */     //   348: astore 5
/* 291:    */     //   350: aload 6
/* 292:    */     //   352: athrow
/* 293:    */     //   353: astore 16
/* 294:    */     //   355: aload 4
/* 295:    */     //   357: ifnull +37 -> 394
/* 296:    */     //   360: aload 5
/* 297:    */     //   362: ifnull +25 -> 387
/* 298:    */     //   365: aload 4
/* 299:    */     //   367: invokeinterface 66 1 0
/* 300:    */     //   372: goto +22 -> 394
/* 301:    */     //   375: astore 17
/* 302:    */     //   377: aload 5
/* 303:    */     //   379: aload 17
/* 304:    */     //   381: invokevirtual 64	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 305:    */     //   384: goto +10 -> 394
/* 306:    */     //   387: aload 4
/* 307:    */     //   389: invokeinterface 66 1 0
/* 308:    */     //   394: aload 16
/* 309:    */     //   396: athrow
/* 310:    */     //   397: astore 4
/* 311:    */     //   399: new 26	java/lang/RuntimeException
/* 312:    */     //   402: dup
/* 313:    */     //   403: aload 4
/* 314:    */     //   405: invokevirtual 27	java/sql/SQLException:toString	()Ljava/lang/String;
/* 315:    */     //   408: aload 4
/* 316:    */     //   410: invokespecial 28	java/lang/RuntimeException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
/* 317:    */     //   413: athrow
/* 318:    */     // Line number table:
/* 319:    */     //   Java source line #138	-> byte code offset #0
/* 320:    */     //   Java source line #139	-> byte code offset #7
/* 321:    */     //   Java source line #141	-> byte code offset #17
/* 322:    */     //   Java source line #142	-> byte code offset #25
/* 323:    */     //   Java source line #141	-> byte code offset #36
/* 324:    */     //   Java source line #143	-> byte code offset #39
/* 325:    */     //   Java source line #144	-> byte code offset #48
/* 326:    */     //   Java source line #145	-> byte code offset #57
/* 327:    */     //   Java source line #146	-> byte code offset #66
/* 328:    */     //   Java source line #147	-> byte code offset #78
/* 329:    */     //   Java source line #148	-> byte code offset #88
/* 330:    */     //   Java source line #150	-> byte code offset #111
/* 331:    */     //   Java source line #146	-> byte code offset #153
/* 332:    */     //   Java source line #150	-> byte code offset #162
/* 333:    */     //   Java source line #151	-> byte code offset #206
/* 334:    */     //   Java source line #152	-> byte code offset #210
/* 335:    */     //   Java source line #141	-> byte code offset #291
/* 336:    */     //   Java source line #152	-> byte code offset #300
/* 337:    */     //   Java source line #141	-> byte code offset #344
/* 338:    */     //   Java source line #152	-> byte code offset #353
/* 339:    */     //   Java source line #153	-> byte code offset #399
/* 340:    */     // Local variable table:
/* 341:    */     //   start	length	slot	name	signature
/* 342:    */     //   0	414	0	this	BlockchainImpl
/* 343:    */     //   0	414	1	paramLong	long
/* 344:    */     //   0	414	3	paramInt	int
/* 345:    */     //   20	368	4	localConnection	Connection
/* 346:    */     //   397	12	4	localSQLException	SQLException
/* 347:    */     //   23	355	5	localObject1	Object
/* 348:    */     //   34	301	6	localPreparedStatement	PreparedStatement
/* 349:    */     //   344	7	6	localThrowable1	Throwable
/* 350:    */     //   37	288	7	localObject2	Object
/* 351:    */     //   46	161	8	localArrayList	java.util.ArrayList
/* 352:    */     //   291	7	8	localThrowable2	Throwable
/* 353:    */     //   73	216	9	localObject3	Object
/* 354:    */     //   76	111	10	localObject4	Object
/* 355:    */     //   230	5	10	localThrowable3	Throwable
/* 356:    */     //   269	5	10	localThrowable4	Throwable
/* 357:    */     //   131	5	11	localThrowable5	Throwable
/* 358:    */     //   153	7	11	localThrowable6	Throwable
/* 359:    */     //   162	42	12	localObject5	Object
/* 360:    */     //   184	5	13	localThrowable7	Throwable
/* 361:    */     //   300	42	14	localObject6	Object
/* 362:    */     //   322	5	15	localThrowable8	Throwable
/* 363:    */     //   353	42	16	localObject7	Object
/* 364:    */     //   375	5	17	localThrowable9	Throwable
/* 365:    */     // Exception table:
/* 366:    */     //   from	to	target	type
/* 367:    */     //   121	128	131	java/lang/Throwable
/* 368:    */     //   78	111	153	java/lang/Throwable
/* 369:    */     //   78	111	162	finally
/* 370:    */     //   153	164	162	finally
/* 371:    */     //   174	181	184	java/lang/Throwable
/* 372:    */     //   220	227	230	java/lang/Throwable
/* 373:    */     //   259	266	269	java/lang/Throwable
/* 374:    */     //   39	210	291	java/lang/Throwable
/* 375:    */     //   39	210	300	finally
/* 376:    */     //   291	302	300	finally
/* 377:    */     //   312	319	322	java/lang/Throwable
/* 378:    */     //   25	249	344	java/lang/Throwable
/* 379:    */     //   291	344	344	java/lang/Throwable
/* 380:    */     //   25	249	353	finally
/* 381:    */     //   291	355	353	finally
/* 382:    */     //   365	372	375	java/lang/Throwable
/* 383:    */     //   17	288	397	java/sql/SQLException
/* 384:    */     //   291	397	397	java/sql/SQLException
/* 385:    */   }
/* 386:    */   
/* 387:    */   /* Error */
/* 388:    */   public java.util.List<BlockImpl> getBlocksAfter(long paramLong, int paramInt)
/* 389:    */   {
/* 390:    */     // Byte code:
/* 391:    */     //   0: iload_3
/* 392:    */     //   1: sipush 1440
/* 393:    */     //   4: if_icmple +13 -> 17
/* 394:    */     //   7: new 50	java/lang/IllegalArgumentException
/* 395:    */     //   10: dup
/* 396:    */     //   11: ldc 51
/* 397:    */     //   13: invokespecial 52	java/lang/IllegalArgumentException:<init>	(Ljava/lang/String;)V
/* 398:    */     //   16: athrow
/* 399:    */     //   17: invokestatic 19	nxt/db/Db:getConnection	()Ljava/sql/Connection;
/* 400:    */     //   20: astore 4
/* 401:    */     //   22: aconst_null
/* 402:    */     //   23: astore 5
/* 403:    */     //   25: aload 4
/* 404:    */     //   27: ldc 67
/* 405:    */     //   29: invokeinterface 21 2 0
/* 406:    */     //   34: astore 6
/* 407:    */     //   36: aconst_null
/* 408:    */     //   37: astore 7
/* 409:    */     //   39: new 54	java/util/ArrayList
/* 410:    */     //   42: dup
/* 411:    */     //   43: invokespecial 55	java/util/ArrayList:<init>	()V
/* 412:    */     //   46: astore 8
/* 413:    */     //   48: aload 6
/* 414:    */     //   50: iconst_1
/* 415:    */     //   51: lload_1
/* 416:    */     //   52: invokeinterface 44 4 0
/* 417:    */     //   57: aload 6
/* 418:    */     //   59: iconst_2
/* 419:    */     //   60: iload_3
/* 420:    */     //   61: invokeinterface 32 3 0
/* 421:    */     //   66: aload 6
/* 422:    */     //   68: invokeinterface 56 1 0
/* 423:    */     //   73: astore 9
/* 424:    */     //   75: aconst_null
/* 425:    */     //   76: astore 10
/* 426:    */     //   78: aload 9
/* 427:    */     //   80: invokeinterface 57 1 0
/* 428:    */     //   85: ifeq +21 -> 106
/* 429:    */     //   88: aload 8
/* 430:    */     //   90: aload 4
/* 431:    */     //   92: aload 9
/* 432:    */     //   94: invokestatic 68	nxt/BlockDb:loadBlock	(Ljava/sql/Connection;Ljava/sql/ResultSet;)Lnxt/BlockImpl;
/* 433:    */     //   97: invokeinterface 61 2 0
/* 434:    */     //   102: pop
/* 435:    */     //   103: goto -25 -> 78
/* 436:    */     //   106: aload 9
/* 437:    */     //   108: ifnull +93 -> 201
/* 438:    */     //   111: aload 10
/* 439:    */     //   113: ifnull +25 -> 138
/* 440:    */     //   116: aload 9
/* 441:    */     //   118: invokeinterface 62 1 0
/* 442:    */     //   123: goto +78 -> 201
/* 443:    */     //   126: astore 11
/* 444:    */     //   128: aload 10
/* 445:    */     //   130: aload 11
/* 446:    */     //   132: invokevirtual 64	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 447:    */     //   135: goto +66 -> 201
/* 448:    */     //   138: aload 9
/* 449:    */     //   140: invokeinterface 62 1 0
/* 450:    */     //   145: goto +56 -> 201
/* 451:    */     //   148: astore 11
/* 452:    */     //   150: aload 11
/* 453:    */     //   152: astore 10
/* 454:    */     //   154: aload 11
/* 455:    */     //   156: athrow
/* 456:    */     //   157: astore 12
/* 457:    */     //   159: aload 9
/* 458:    */     //   161: ifnull +37 -> 198
/* 459:    */     //   164: aload 10
/* 460:    */     //   166: ifnull +25 -> 191
/* 461:    */     //   169: aload 9
/* 462:    */     //   171: invokeinterface 62 1 0
/* 463:    */     //   176: goto +22 -> 198
/* 464:    */     //   179: astore 13
/* 465:    */     //   181: aload 10
/* 466:    */     //   183: aload 13
/* 467:    */     //   185: invokevirtual 64	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 468:    */     //   188: goto +10 -> 198
/* 469:    */     //   191: aload 9
/* 470:    */     //   193: invokeinterface 62 1 0
/* 471:    */     //   198: aload 12
/* 472:    */     //   200: athrow
/* 473:    */     //   201: aload 8
/* 474:    */     //   203: astore 9
/* 475:    */     //   205: aload 6
/* 476:    */     //   207: ifnull +37 -> 244
/* 477:    */     //   210: aload 7
/* 478:    */     //   212: ifnull +25 -> 237
/* 479:    */     //   215: aload 6
/* 480:    */     //   217: invokeinterface 65 1 0
/* 481:    */     //   222: goto +22 -> 244
/* 482:    */     //   225: astore 10
/* 483:    */     //   227: aload 7
/* 484:    */     //   229: aload 10
/* 485:    */     //   231: invokevirtual 64	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 486:    */     //   234: goto +10 -> 244
/* 487:    */     //   237: aload 6
/* 488:    */     //   239: invokeinterface 65 1 0
/* 489:    */     //   244: aload 4
/* 490:    */     //   246: ifnull +37 -> 283
/* 491:    */     //   249: aload 5
/* 492:    */     //   251: ifnull +25 -> 276
/* 493:    */     //   254: aload 4
/* 494:    */     //   256: invokeinterface 66 1 0
/* 495:    */     //   261: goto +22 -> 283
/* 496:    */     //   264: astore 10
/* 497:    */     //   266: aload 5
/* 498:    */     //   268: aload 10
/* 499:    */     //   270: invokevirtual 64	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 500:    */     //   273: goto +10 -> 283
/* 501:    */     //   276: aload 4
/* 502:    */     //   278: invokeinterface 66 1 0
/* 503:    */     //   283: aload 9
/* 504:    */     //   285: areturn
/* 505:    */     //   286: astore 8
/* 506:    */     //   288: aload 8
/* 507:    */     //   290: astore 7
/* 508:    */     //   292: aload 8
/* 509:    */     //   294: athrow
/* 510:    */     //   295: astore 14
/* 511:    */     //   297: aload 6
/* 512:    */     //   299: ifnull +37 -> 336
/* 513:    */     //   302: aload 7
/* 514:    */     //   304: ifnull +25 -> 329
/* 515:    */     //   307: aload 6
/* 516:    */     //   309: invokeinterface 65 1 0
/* 517:    */     //   314: goto +22 -> 336
/* 518:    */     //   317: astore 15
/* 519:    */     //   319: aload 7
/* 520:    */     //   321: aload 15
/* 521:    */     //   323: invokevirtual 64	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 522:    */     //   326: goto +10 -> 336
/* 523:    */     //   329: aload 6
/* 524:    */     //   331: invokeinterface 65 1 0
/* 525:    */     //   336: aload 14
/* 526:    */     //   338: athrow
/* 527:    */     //   339: astore 6
/* 528:    */     //   341: aload 6
/* 529:    */     //   343: astore 5
/* 530:    */     //   345: aload 6
/* 531:    */     //   347: athrow
/* 532:    */     //   348: astore 16
/* 533:    */     //   350: aload 4
/* 534:    */     //   352: ifnull +37 -> 389
/* 535:    */     //   355: aload 5
/* 536:    */     //   357: ifnull +25 -> 382
/* 537:    */     //   360: aload 4
/* 538:    */     //   362: invokeinterface 66 1 0
/* 539:    */     //   367: goto +22 -> 389
/* 540:    */     //   370: astore 17
/* 541:    */     //   372: aload 5
/* 542:    */     //   374: aload 17
/* 543:    */     //   376: invokevirtual 64	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 544:    */     //   379: goto +10 -> 389
/* 545:    */     //   382: aload 4
/* 546:    */     //   384: invokeinterface 66 1 0
/* 547:    */     //   389: aload 16
/* 548:    */     //   391: athrow
/* 549:    */     //   392: astore 4
/* 550:    */     //   394: new 26	java/lang/RuntimeException
/* 551:    */     //   397: dup
/* 552:    */     //   398: aload 4
/* 553:    */     //   400: invokevirtual 70	java/lang/Exception:toString	()Ljava/lang/String;
/* 554:    */     //   403: aload 4
/* 555:    */     //   405: invokespecial 28	java/lang/RuntimeException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
/* 556:    */     //   408: athrow
/* 557:    */     // Line number table:
/* 558:    */     //   Java source line #159	-> byte code offset #0
/* 559:    */     //   Java source line #160	-> byte code offset #7
/* 560:    */     //   Java source line #162	-> byte code offset #17
/* 561:    */     //   Java source line #163	-> byte code offset #25
/* 562:    */     //   Java source line #162	-> byte code offset #36
/* 563:    */     //   Java source line #164	-> byte code offset #39
/* 564:    */     //   Java source line #165	-> byte code offset #48
/* 565:    */     //   Java source line #166	-> byte code offset #57
/* 566:    */     //   Java source line #167	-> byte code offset #66
/* 567:    */     //   Java source line #168	-> byte code offset #78
/* 568:    */     //   Java source line #169	-> byte code offset #88
/* 569:    */     //   Java source line #171	-> byte code offset #106
/* 570:    */     //   Java source line #167	-> byte code offset #148
/* 571:    */     //   Java source line #171	-> byte code offset #157
/* 572:    */     //   Java source line #172	-> byte code offset #201
/* 573:    */     //   Java source line #173	-> byte code offset #205
/* 574:    */     //   Java source line #162	-> byte code offset #286
/* 575:    */     //   Java source line #173	-> byte code offset #295
/* 576:    */     //   Java source line #162	-> byte code offset #339
/* 577:    */     //   Java source line #173	-> byte code offset #348
/* 578:    */     //   Java source line #174	-> byte code offset #394
/* 579:    */     // Local variable table:
/* 580:    */     //   start	length	slot	name	signature
/* 581:    */     //   0	409	0	this	BlockchainImpl
/* 582:    */     //   0	409	1	paramLong	long
/* 583:    */     //   0	409	3	paramInt	int
/* 584:    */     //   20	363	4	localConnection	Connection
/* 585:    */     //   392	12	4	localValidationException	NxtException.ValidationException
/* 586:    */     //   23	350	5	localObject1	Object
/* 587:    */     //   34	296	6	localPreparedStatement	PreparedStatement
/* 588:    */     //   339	7	6	localThrowable1	Throwable
/* 589:    */     //   37	283	7	localObject2	Object
/* 590:    */     //   46	156	8	localArrayList	java.util.ArrayList
/* 591:    */     //   286	7	8	localThrowable2	Throwable
/* 592:    */     //   73	211	9	localObject3	Object
/* 593:    */     //   76	106	10	localObject4	Object
/* 594:    */     //   225	5	10	localThrowable3	Throwable
/* 595:    */     //   264	5	10	localThrowable4	Throwable
/* 596:    */     //   126	5	11	localThrowable5	Throwable
/* 597:    */     //   148	7	11	localThrowable6	Throwable
/* 598:    */     //   157	42	12	localObject5	Object
/* 599:    */     //   179	5	13	localThrowable7	Throwable
/* 600:    */     //   295	42	14	localObject6	Object
/* 601:    */     //   317	5	15	localThrowable8	Throwable
/* 602:    */     //   348	42	16	localObject7	Object
/* 603:    */     //   370	5	17	localThrowable9	Throwable
/* 604:    */     // Exception table:
/* 605:    */     //   from	to	target	type
/* 606:    */     //   116	123	126	java/lang/Throwable
/* 607:    */     //   78	106	148	java/lang/Throwable
/* 608:    */     //   78	106	157	finally
/* 609:    */     //   148	159	157	finally
/* 610:    */     //   169	176	179	java/lang/Throwable
/* 611:    */     //   215	222	225	java/lang/Throwable
/* 612:    */     //   254	261	264	java/lang/Throwable
/* 613:    */     //   39	205	286	java/lang/Throwable
/* 614:    */     //   39	205	295	finally
/* 615:    */     //   286	297	295	finally
/* 616:    */     //   307	314	317	java/lang/Throwable
/* 617:    */     //   25	244	339	java/lang/Throwable
/* 618:    */     //   286	339	339	java/lang/Throwable
/* 619:    */     //   25	244	348	finally
/* 620:    */     //   286	350	348	finally
/* 621:    */     //   360	367	370	java/lang/Throwable
/* 622:    */     //   17	283	392	nxt/NxtException$ValidationException
/* 623:    */     //   17	283	392	java/sql/SQLException
/* 624:    */     //   286	392	392	nxt/NxtException$ValidationException
/* 625:    */     //   286	392	392	java/sql/SQLException
/* 626:    */   }
/* 627:    */   
/* 628:    */   public long getBlockIdAtHeight(int paramInt)
/* 629:    */   {
/* 630:180 */     Block localBlock = (Block)this.lastBlock.get();
/* 631:181 */     if (paramInt > localBlock.getHeight()) {
/* 632:182 */       throw new IllegalArgumentException("Invalid height " + paramInt + ", current blockchain is at " + localBlock.getHeight());
/* 633:    */     }
/* 634:184 */     if (paramInt == localBlock.getHeight()) {
/* 635:185 */       return localBlock.getId();
/* 636:    */     }
/* 637:187 */     return BlockDb.findBlockIdAtHeight(paramInt);
/* 638:    */   }
/* 639:    */   
/* 640:    */   public BlockImpl getBlockAtHeight(int paramInt)
/* 641:    */   {
/* 642:192 */     BlockImpl localBlockImpl = (BlockImpl)this.lastBlock.get();
/* 643:193 */     if (paramInt > localBlockImpl.getHeight()) {
/* 644:194 */       throw new IllegalArgumentException("Invalid height " + paramInt + ", current blockchain is at " + localBlockImpl.getHeight());
/* 645:    */     }
/* 646:196 */     if (paramInt == localBlockImpl.getHeight()) {
/* 647:197 */       return localBlockImpl;
/* 648:    */     }
/* 649:199 */     return BlockDb.findBlockAtHeight(paramInt);
/* 650:    */   }
/* 651:    */   
/* 652:    */   public Transaction getTransaction(long paramLong)
/* 653:    */   {
/* 654:204 */     return TransactionDb.findTransaction(paramLong);
/* 655:    */   }
/* 656:    */   
/* 657:    */   public Transaction getTransactionByFullHash(String paramString)
/* 658:    */   {
/* 659:209 */     return TransactionDb.findTransactionByFullHash(paramString);
/* 660:    */   }
/* 661:    */   
/* 662:    */   public boolean hasTransaction(long paramLong)
/* 663:    */   {
/* 664:214 */     return TransactionDb.hasTransaction(paramLong);
/* 665:    */   }
/* 666:    */   
/* 667:    */   public boolean hasTransactionByFullHash(String paramString)
/* 668:    */   {
/* 669:219 */     return TransactionDb.hasTransactionByFullHash(paramString);
/* 670:    */   }
/* 671:    */   
/* 672:    */   /* Error */
/* 673:    */   public int getTransactionCount()
/* 674:    */   {
/* 675:    */     // Byte code:
/* 676:    */     //   0: invokestatic 19	nxt/db/Db:getConnection	()Ljava/sql/Connection;
/* 677:    */     //   3: astore_1
/* 678:    */     //   4: aconst_null
/* 679:    */     //   5: astore_2
/* 680:    */     //   6: aload_1
/* 681:    */     //   7: ldc 83
/* 682:    */     //   9: invokeinterface 21 2 0
/* 683:    */     //   14: astore_3
/* 684:    */     //   15: aconst_null
/* 685:    */     //   16: astore 4
/* 686:    */     //   18: aload_3
/* 687:    */     //   19: invokeinterface 56 1 0
/* 688:    */     //   24: astore 5
/* 689:    */     //   26: aconst_null
/* 690:    */     //   27: astore 6
/* 691:    */     //   29: aload 5
/* 692:    */     //   31: invokeinterface 57 1 0
/* 693:    */     //   36: pop
/* 694:    */     //   37: aload 5
/* 695:    */     //   39: iconst_1
/* 696:    */     //   40: invokeinterface 84 2 0
/* 697:    */     //   45: istore 7
/* 698:    */     //   47: aload 5
/* 699:    */     //   49: ifnull +37 -> 86
/* 700:    */     //   52: aload 6
/* 701:    */     //   54: ifnull +25 -> 79
/* 702:    */     //   57: aload 5
/* 703:    */     //   59: invokeinterface 62 1 0
/* 704:    */     //   64: goto +22 -> 86
/* 705:    */     //   67: astore 8
/* 706:    */     //   69: aload 6
/* 707:    */     //   71: aload 8
/* 708:    */     //   73: invokevirtual 64	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 709:    */     //   76: goto +10 -> 86
/* 710:    */     //   79: aload 5
/* 711:    */     //   81: invokeinterface 62 1 0
/* 712:    */     //   86: aload_3
/* 713:    */     //   87: ifnull +35 -> 122
/* 714:    */     //   90: aload 4
/* 715:    */     //   92: ifnull +24 -> 116
/* 716:    */     //   95: aload_3
/* 717:    */     //   96: invokeinterface 65 1 0
/* 718:    */     //   101: goto +21 -> 122
/* 719:    */     //   104: astore 8
/* 720:    */     //   106: aload 4
/* 721:    */     //   108: aload 8
/* 722:    */     //   110: invokevirtual 64	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 723:    */     //   113: goto +9 -> 122
/* 724:    */     //   116: aload_3
/* 725:    */     //   117: invokeinterface 65 1 0
/* 726:    */     //   122: aload_1
/* 727:    */     //   123: ifnull +33 -> 156
/* 728:    */     //   126: aload_2
/* 729:    */     //   127: ifnull +23 -> 150
/* 730:    */     //   130: aload_1
/* 731:    */     //   131: invokeinterface 66 1 0
/* 732:    */     //   136: goto +20 -> 156
/* 733:    */     //   139: astore 8
/* 734:    */     //   141: aload_2
/* 735:    */     //   142: aload 8
/* 736:    */     //   144: invokevirtual 64	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 737:    */     //   147: goto +9 -> 156
/* 738:    */     //   150: aload_1
/* 739:    */     //   151: invokeinterface 66 1 0
/* 740:    */     //   156: iload 7
/* 741:    */     //   158: ireturn
/* 742:    */     //   159: astore 7
/* 743:    */     //   161: aload 7
/* 744:    */     //   163: astore 6
/* 745:    */     //   165: aload 7
/* 746:    */     //   167: athrow
/* 747:    */     //   168: astore 9
/* 748:    */     //   170: aload 5
/* 749:    */     //   172: ifnull +37 -> 209
/* 750:    */     //   175: aload 6
/* 751:    */     //   177: ifnull +25 -> 202
/* 752:    */     //   180: aload 5
/* 753:    */     //   182: invokeinterface 62 1 0
/* 754:    */     //   187: goto +22 -> 209
/* 755:    */     //   190: astore 10
/* 756:    */     //   192: aload 6
/* 757:    */     //   194: aload 10
/* 758:    */     //   196: invokevirtual 64	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 759:    */     //   199: goto +10 -> 209
/* 760:    */     //   202: aload 5
/* 761:    */     //   204: invokeinterface 62 1 0
/* 762:    */     //   209: aload 9
/* 763:    */     //   211: athrow
/* 764:    */     //   212: astore 5
/* 765:    */     //   214: aload 5
/* 766:    */     //   216: astore 4
/* 767:    */     //   218: aload 5
/* 768:    */     //   220: athrow
/* 769:    */     //   221: astore 11
/* 770:    */     //   223: aload_3
/* 771:    */     //   224: ifnull +35 -> 259
/* 772:    */     //   227: aload 4
/* 773:    */     //   229: ifnull +24 -> 253
/* 774:    */     //   232: aload_3
/* 775:    */     //   233: invokeinterface 65 1 0
/* 776:    */     //   238: goto +21 -> 259
/* 777:    */     //   241: astore 12
/* 778:    */     //   243: aload 4
/* 779:    */     //   245: aload 12
/* 780:    */     //   247: invokevirtual 64	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 781:    */     //   250: goto +9 -> 259
/* 782:    */     //   253: aload_3
/* 783:    */     //   254: invokeinterface 65 1 0
/* 784:    */     //   259: aload 11
/* 785:    */     //   261: athrow
/* 786:    */     //   262: astore_3
/* 787:    */     //   263: aload_3
/* 788:    */     //   264: astore_2
/* 789:    */     //   265: aload_3
/* 790:    */     //   266: athrow
/* 791:    */     //   267: astore 13
/* 792:    */     //   269: aload_1
/* 793:    */     //   270: ifnull +33 -> 303
/* 794:    */     //   273: aload_2
/* 795:    */     //   274: ifnull +23 -> 297
/* 796:    */     //   277: aload_1
/* 797:    */     //   278: invokeinterface 66 1 0
/* 798:    */     //   283: goto +20 -> 303
/* 799:    */     //   286: astore 14
/* 800:    */     //   288: aload_2
/* 801:    */     //   289: aload 14
/* 802:    */     //   291: invokevirtual 64	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 803:    */     //   294: goto +9 -> 303
/* 804:    */     //   297: aload_1
/* 805:    */     //   298: invokeinterface 66 1 0
/* 806:    */     //   303: aload 13
/* 807:    */     //   305: athrow
/* 808:    */     //   306: astore_1
/* 809:    */     //   307: new 26	java/lang/RuntimeException
/* 810:    */     //   310: dup
/* 811:    */     //   311: aload_1
/* 812:    */     //   312: invokevirtual 27	java/sql/SQLException:toString	()Ljava/lang/String;
/* 813:    */     //   315: aload_1
/* 814:    */     //   316: invokespecial 28	java/lang/RuntimeException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
/* 815:    */     //   319: athrow
/* 816:    */     // Line number table:
/* 817:    */     //   Java source line #224	-> byte code offset #0
/* 818:    */     //   Java source line #225	-> byte code offset #18
/* 819:    */     //   Java source line #224	-> byte code offset #26
/* 820:    */     //   Java source line #226	-> byte code offset #29
/* 821:    */     //   Java source line #227	-> byte code offset #37
/* 822:    */     //   Java source line #228	-> byte code offset #47
/* 823:    */     //   Java source line #224	-> byte code offset #159
/* 824:    */     //   Java source line #228	-> byte code offset #168
/* 825:    */     //   Java source line #224	-> byte code offset #212
/* 826:    */     //   Java source line #228	-> byte code offset #221
/* 827:    */     //   Java source line #224	-> byte code offset #262
/* 828:    */     //   Java source line #228	-> byte code offset #267
/* 829:    */     //   Java source line #229	-> byte code offset #307
/* 830:    */     // Local variable table:
/* 831:    */     //   start	length	slot	name	signature
/* 832:    */     //   0	320	0	this	BlockchainImpl
/* 833:    */     //   3	295	1	localConnection	Connection
/* 834:    */     //   306	10	1	localSQLException	SQLException
/* 835:    */     //   5	284	2	localObject1	Object
/* 836:    */     //   14	240	3	localPreparedStatement	PreparedStatement
/* 837:    */     //   262	4	3	localThrowable1	Throwable
/* 838:    */     //   16	228	4	localObject2	Object
/* 839:    */     //   24	179	5	localResultSet	ResultSet
/* 840:    */     //   212	7	5	localThrowable2	Throwable
/* 841:    */     //   27	166	6	localObject3	Object
/* 842:    */     //   45	112	7	i	int
/* 843:    */     //   159	7	7	localThrowable3	Throwable
/* 844:    */     //   67	5	8	localThrowable4	Throwable
/* 845:    */     //   104	5	8	localThrowable5	Throwable
/* 846:    */     //   139	4	8	localThrowable6	Throwable
/* 847:    */     //   168	42	9	localObject4	Object
/* 848:    */     //   190	5	10	localThrowable7	Throwable
/* 849:    */     //   221	39	11	localObject5	Object
/* 850:    */     //   241	5	12	localThrowable8	Throwable
/* 851:    */     //   267	37	13	localObject6	Object
/* 852:    */     //   286	4	14	localThrowable9	Throwable
/* 853:    */     // Exception table:
/* 854:    */     //   from	to	target	type
/* 855:    */     //   57	64	67	java/lang/Throwable
/* 856:    */     //   95	101	104	java/lang/Throwable
/* 857:    */     //   130	136	139	java/lang/Throwable
/* 858:    */     //   29	47	159	java/lang/Throwable
/* 859:    */     //   29	47	168	finally
/* 860:    */     //   159	170	168	finally
/* 861:    */     //   180	187	190	java/lang/Throwable
/* 862:    */     //   18	86	212	java/lang/Throwable
/* 863:    */     //   159	212	212	java/lang/Throwable
/* 864:    */     //   18	86	221	finally
/* 865:    */     //   159	223	221	finally
/* 866:    */     //   232	238	241	java/lang/Throwable
/* 867:    */     //   6	122	262	java/lang/Throwable
/* 868:    */     //   159	262	262	java/lang/Throwable
/* 869:    */     //   6	122	267	finally
/* 870:    */     //   159	269	267	finally
/* 871:    */     //   277	283	286	java/lang/Throwable
/* 872:    */     //   0	156	306	java/sql/SQLException
/* 873:    */     //   159	306	306	java/sql/SQLException
/* 874:    */   }
/* 875:    */   
/* 876:    */   public DbIterator<TransactionImpl> getAllTransactions()
/* 877:    */   {
/* 878:235 */     Connection localConnection = null;
/* 879:    */     try
/* 880:    */     {
/* 881:237 */       localConnection = Db.getConnection();
/* 882:238 */       PreparedStatement localPreparedStatement = localConnection.prepareStatement("SELECT * FROM transaction ORDER BY db_id ASC");
/* 883:239 */       return getTransactions(localConnection, localPreparedStatement);
/* 884:    */     }
/* 885:    */     catch (SQLException localSQLException)
/* 886:    */     {
/* 887:241 */       DbUtils.close(new AutoCloseable[] { localConnection });
/* 888:242 */       throw new RuntimeException(localSQLException.toString(), localSQLException);
/* 889:    */     }
/* 890:    */   }
/* 891:    */   
/* 892:    */   public DbIterator<TransactionImpl> getTransactions(Account paramAccount, byte paramByte1, byte paramByte2, int paramInt)
/* 893:    */   {
/* 894:248 */     return getTransactions(paramAccount, 0, paramByte1, paramByte2, paramInt, 0, -1);
/* 895:    */   }
/* 896:    */   
/* 897:    */   public DbIterator<TransactionImpl> getTransactions(Account paramAccount, int paramInt1, byte paramByte1, byte paramByte2, int paramInt2, int paramInt3, int paramInt4)
/* 898:    */   {
/* 899:254 */     int i = paramInt1 > 0 ? getHeight() - paramInt1 : Integer.MAX_VALUE;
/* 900:255 */     if (i < 0) {
/* 901:256 */       throw new IllegalArgumentException("Number of confirmations required " + paramInt1 + " exceeds current blockchain height " + getHeight());
/* 902:    */     }
/* 903:259 */     Connection localConnection = null;
/* 904:    */     try
/* 905:    */     {
/* 906:261 */       StringBuilder localStringBuilder = new StringBuilder();
/* 907:262 */       localStringBuilder.append("SELECT * FROM transaction WHERE recipient_id = ? AND sender_id <> ? ");
/* 908:263 */       if (paramInt2 > 0) {
/* 909:264 */         localStringBuilder.append("AND block_timestamp >= ? ");
/* 910:    */       }
/* 911:266 */       if (paramByte1 >= 0)
/* 912:    */       {
/* 913:267 */         localStringBuilder.append("AND type = ? ");
/* 914:268 */         if (paramByte2 >= 0) {
/* 915:269 */           localStringBuilder.append("AND subtype = ? ");
/* 916:    */         }
/* 917:    */       }
/* 918:272 */       if (i < Integer.MAX_VALUE) {
/* 919:273 */         localStringBuilder.append("AND height <= ? ");
/* 920:    */       }
/* 921:275 */       localStringBuilder.append("UNION ALL SELECT * FROM transaction WHERE sender_id = ? ");
/* 922:276 */       if (paramInt2 > 0) {
/* 923:277 */         localStringBuilder.append("AND block_timestamp >= ? ");
/* 924:    */       }
/* 925:279 */       if (paramByte1 >= 0)
/* 926:    */       {
/* 927:280 */         localStringBuilder.append("AND type = ? ");
/* 928:281 */         if (paramByte2 >= 0) {
/* 929:282 */           localStringBuilder.append("AND subtype = ? ");
/* 930:    */         }
/* 931:    */       }
/* 932:285 */       if (i < Integer.MAX_VALUE) {
/* 933:286 */         localStringBuilder.append("AND height <= ? ");
/* 934:    */       }
/* 935:288 */       localStringBuilder.append("ORDER BY block_timestamp DESC, id DESC");
/* 936:289 */       localStringBuilder.append(DbUtils.limitsClause(paramInt3, paramInt4));
/* 937:290 */       localConnection = Db.getConnection();
/* 938:    */       
/* 939:292 */       int j = 0;
/* 940:293 */       PreparedStatement localPreparedStatement = localConnection.prepareStatement(localStringBuilder.toString());
/* 941:294 */       localPreparedStatement.setLong(++j, paramAccount.getId());
/* 942:295 */       localPreparedStatement.setLong(++j, paramAccount.getId());
/* 943:296 */       if (paramInt2 > 0) {
/* 944:297 */         localPreparedStatement.setInt(++j, paramInt2);
/* 945:    */       }
/* 946:299 */       if (paramByte1 >= 0)
/* 947:    */       {
/* 948:300 */         localPreparedStatement.setByte(++j, paramByte1);
/* 949:301 */         if (paramByte2 >= 0) {
/* 950:302 */           localPreparedStatement.setByte(++j, paramByte2);
/* 951:    */         }
/* 952:    */       }
/* 953:305 */       if (i < Integer.MAX_VALUE) {
/* 954:306 */         localPreparedStatement.setInt(++j, i);
/* 955:    */       }
/* 956:308 */       localPreparedStatement.setLong(++j, paramAccount.getId());
/* 957:309 */       if (paramInt2 > 0) {
/* 958:310 */         localPreparedStatement.setInt(++j, paramInt2);
/* 959:    */       }
/* 960:312 */       if (paramByte1 >= 0)
/* 961:    */       {
/* 962:313 */         localPreparedStatement.setByte(++j, paramByte1);
/* 963:314 */         if (paramByte2 >= 0) {
/* 964:315 */           localPreparedStatement.setByte(++j, paramByte2);
/* 965:    */         }
/* 966:    */       }
/* 967:318 */       if (i < Integer.MAX_VALUE) {
/* 968:319 */         localPreparedStatement.setInt(++j, i);
/* 969:    */       }
/* 970:321 */       j++;DbUtils.setLimits(j, localPreparedStatement, paramInt3, paramInt4);
/* 971:322 */       return getTransactions(localConnection, localPreparedStatement);
/* 972:    */     }
/* 973:    */     catch (SQLException localSQLException)
/* 974:    */     {
/* 975:324 */       DbUtils.close(new AutoCloseable[] { localConnection });
/* 976:325 */       throw new RuntimeException(localSQLException.toString(), localSQLException);
/* 977:    */     }
/* 978:    */   }
/* 979:    */   
/* 980:    */   public DbIterator<TransactionImpl> getTransactions(Connection paramConnection, PreparedStatement paramPreparedStatement)
/* 981:    */   {
/* 982:331 */     new DbIterator(paramConnection, paramPreparedStatement, new DbIterator.ResultSetReader()
/* 983:    */     {
/* 984:    */       public TransactionImpl get(Connection paramAnonymousConnection, ResultSet paramAnonymousResultSet)
/* 985:    */         throws NxtException.ValidationException
/* 986:    */       {
/* 987:334 */         return TransactionDb.loadTransaction(paramAnonymousConnection, paramAnonymousResultSet);
/* 988:    */       }
/* 989:    */     });
/* 990:    */   }
/* 991:    */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.BlockchainImpl
 * JD-Core Version:    0.7.1
 */