/*   1:    */ package nxt.db;
/*   2:    */ 
/*   3:    */ import java.sql.Connection;
/*   4:    */ import java.sql.PreparedStatement;
/*   5:    */ import java.sql.ResultSet;
/*   6:    */ import java.sql.SQLException;
/*   7:    */ import java.util.Map;
/*   8:    */ import nxt.BlockchainProcessor;
/*   9:    */ import nxt.Nxt;
/*  10:    */ 
/*  11:    */ public abstract class EntityDbTable<T>
/*  12:    */   extends DerivedDbTable
/*  13:    */ {
/*  14:    */   private final boolean multiversion;
/*  15:    */   protected final DbKey.Factory<T> dbKeyFactory;
/*  16:    */   private final String defaultSort;
/*  17:    */   
/*  18:    */   protected EntityDbTable(String paramString, DbKey.Factory<T> paramFactory)
/*  19:    */   {
/*  20: 17 */     this(paramString, paramFactory, false);
/*  21:    */   }
/*  22:    */   
/*  23:    */   EntityDbTable(String paramString, DbKey.Factory<T> paramFactory, boolean paramBoolean)
/*  24:    */   {
/*  25: 21 */     super(paramString);
/*  26: 22 */     this.dbKeyFactory = paramFactory;
/*  27: 23 */     this.multiversion = paramBoolean;
/*  28: 24 */     this.defaultSort = (" ORDER BY " + (paramBoolean ? paramFactory.getPKColumns() : " height DESC "));
/*  29:    */   }
/*  30:    */   
/*  31:    */   protected abstract T load(Connection paramConnection, ResultSet paramResultSet)
/*  32:    */     throws SQLException;
/*  33:    */   
/*  34:    */   protected abstract void save(Connection paramConnection, T paramT)
/*  35:    */     throws SQLException;
/*  36:    */   
/*  37:    */   protected String defaultSort()
/*  38:    */   {
/*  39: 32 */     return this.defaultSort;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public final void checkAvailable(int paramInt)
/*  43:    */   {
/*  44: 36 */     if ((this.multiversion) && (paramInt < Nxt.getBlockchainProcessor().getMinRollbackHeight())) {
/*  45: 37 */       throw new IllegalArgumentException("Historical data as of height " + paramInt + " not available, set nxt.trimDerivedTables=false and re-scan");
/*  46:    */     }
/*  47:    */   }
/*  48:    */   
/*  49:    */   /* Error */
/*  50:    */   public final T get(DbKey paramDbKey)
/*  51:    */   {
/*  52:    */     // Byte code:
/*  53:    */     //   0: invokestatic 20	nxt/db/Db:isInTransaction	()Z
/*  54:    */     //   3: ifeq +23 -> 26
/*  55:    */     //   6: aload_0
/*  56:    */     //   7: getfield 21	nxt/db/EntityDbTable:table	Ljava/lang/String;
/*  57:    */     //   10: invokestatic 22	nxt/db/Db:getCache	(Ljava/lang/String;)Ljava/util/Map;
/*  58:    */     //   13: aload_1
/*  59:    */     //   14: invokeinterface 23 2 0
/*  60:    */     //   19: astore_2
/*  61:    */     //   20: aload_2
/*  62:    */     //   21: ifnull +5 -> 26
/*  63:    */     //   24: aload_2
/*  64:    */     //   25: areturn
/*  65:    */     //   26: invokestatic 24	nxt/db/Db:getConnection	()Ljava/sql/Connection;
/*  66:    */     //   29: astore_2
/*  67:    */     //   30: aconst_null
/*  68:    */     //   31: astore_3
/*  69:    */     //   32: aload_2
/*  70:    */     //   33: new 5	java/lang/StringBuilder
/*  71:    */     //   36: dup
/*  72:    */     //   37: invokespecial 6	java/lang/StringBuilder:<init>	()V
/*  73:    */     //   40: ldc 25
/*  74:    */     //   42: invokevirtual 8	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*  75:    */     //   45: aload_0
/*  76:    */     //   46: getfield 21	nxt/db/EntityDbTable:table	Ljava/lang/String;
/*  77:    */     //   49: invokevirtual 8	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*  78:    */     //   52: aload_0
/*  79:    */     //   53: getfield 3	nxt/db/EntityDbTable:dbKeyFactory	Lnxt/db/DbKey$Factory;
/*  80:    */     //   56: invokevirtual 26	nxt/db/DbKey$Factory:getPKClause	()Ljava/lang/String;
/*  81:    */     //   59: invokevirtual 8	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*  82:    */     //   62: aload_0
/*  83:    */     //   63: getfield 4	nxt/db/EntityDbTable:multiversion	Z
/*  84:    */     //   66: ifeq +8 -> 74
/*  85:    */     //   69: ldc 27
/*  86:    */     //   71: goto +5 -> 76
/*  87:    */     //   74: ldc 28
/*  88:    */     //   76: invokevirtual 8	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*  89:    */     //   79: invokevirtual 11	java/lang/StringBuilder:toString	()Ljava/lang/String;
/*  90:    */     //   82: invokeinterface 29 2 0
/*  91:    */     //   87: astore 4
/*  92:    */     //   89: aconst_null
/*  93:    */     //   90: astore 5
/*  94:    */     //   92: aload_1
/*  95:    */     //   93: aload 4
/*  96:    */     //   95: invokeinterface 30 2 0
/*  97:    */     //   100: pop
/*  98:    */     //   101: aload_0
/*  99:    */     //   102: aload_2
/* 100:    */     //   103: aload 4
/* 101:    */     //   105: iconst_1
/* 102:    */     //   106: invokespecial 31	nxt/db/EntityDbTable:get	(Ljava/sql/Connection;Ljava/sql/PreparedStatement;Z)Ljava/lang/Object;
/* 103:    */     //   109: astore 6
/* 104:    */     //   111: aload 4
/* 105:    */     //   113: ifnull +37 -> 150
/* 106:    */     //   116: aload 5
/* 107:    */     //   118: ifnull +25 -> 143
/* 108:    */     //   121: aload 4
/* 109:    */     //   123: invokeinterface 32 1 0
/* 110:    */     //   128: goto +22 -> 150
/* 111:    */     //   131: astore 7
/* 112:    */     //   133: aload 5
/* 113:    */     //   135: aload 7
/* 114:    */     //   137: invokevirtual 34	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 115:    */     //   140: goto +10 -> 150
/* 116:    */     //   143: aload 4
/* 117:    */     //   145: invokeinterface 32 1 0
/* 118:    */     //   150: aload_2
/* 119:    */     //   151: ifnull +33 -> 184
/* 120:    */     //   154: aload_3
/* 121:    */     //   155: ifnull +23 -> 178
/* 122:    */     //   158: aload_2
/* 123:    */     //   159: invokeinterface 35 1 0
/* 124:    */     //   164: goto +20 -> 184
/* 125:    */     //   167: astore 7
/* 126:    */     //   169: aload_3
/* 127:    */     //   170: aload 7
/* 128:    */     //   172: invokevirtual 34	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 129:    */     //   175: goto +9 -> 184
/* 130:    */     //   178: aload_2
/* 131:    */     //   179: invokeinterface 35 1 0
/* 132:    */     //   184: aload 6
/* 133:    */     //   186: areturn
/* 134:    */     //   187: astore 6
/* 135:    */     //   189: aload 6
/* 136:    */     //   191: astore 5
/* 137:    */     //   193: aload 6
/* 138:    */     //   195: athrow
/* 139:    */     //   196: astore 8
/* 140:    */     //   198: aload 4
/* 141:    */     //   200: ifnull +37 -> 237
/* 142:    */     //   203: aload 5
/* 143:    */     //   205: ifnull +25 -> 230
/* 144:    */     //   208: aload 4
/* 145:    */     //   210: invokeinterface 32 1 0
/* 146:    */     //   215: goto +22 -> 237
/* 147:    */     //   218: astore 9
/* 148:    */     //   220: aload 5
/* 149:    */     //   222: aload 9
/* 150:    */     //   224: invokevirtual 34	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 151:    */     //   227: goto +10 -> 237
/* 152:    */     //   230: aload 4
/* 153:    */     //   232: invokeinterface 32 1 0
/* 154:    */     //   237: aload 8
/* 155:    */     //   239: athrow
/* 156:    */     //   240: astore 4
/* 157:    */     //   242: aload 4
/* 158:    */     //   244: astore_3
/* 159:    */     //   245: aload 4
/* 160:    */     //   247: athrow
/* 161:    */     //   248: astore 10
/* 162:    */     //   250: aload_2
/* 163:    */     //   251: ifnull +33 -> 284
/* 164:    */     //   254: aload_3
/* 165:    */     //   255: ifnull +23 -> 278
/* 166:    */     //   258: aload_2
/* 167:    */     //   259: invokeinterface 35 1 0
/* 168:    */     //   264: goto +20 -> 284
/* 169:    */     //   267: astore 11
/* 170:    */     //   269: aload_3
/* 171:    */     //   270: aload 11
/* 172:    */     //   272: invokevirtual 34	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 173:    */     //   275: goto +9 -> 284
/* 174:    */     //   278: aload_2
/* 175:    */     //   279: invokeinterface 35 1 0
/* 176:    */     //   284: aload 10
/* 177:    */     //   286: athrow
/* 178:    */     //   287: astore_2
/* 179:    */     //   288: new 37	java/lang/RuntimeException
/* 180:    */     //   291: dup
/* 181:    */     //   292: aload_2
/* 182:    */     //   293: invokevirtual 38	java/sql/SQLException:toString	()Ljava/lang/String;
/* 183:    */     //   296: aload_2
/* 184:    */     //   297: invokespecial 39	java/lang/RuntimeException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
/* 185:    */     //   300: athrow
/* 186:    */     // Line number table:
/* 187:    */     //   Java source line #42	-> byte code offset #0
/* 188:    */     //   Java source line #43	-> byte code offset #6
/* 189:    */     //   Java source line #44	-> byte code offset #20
/* 190:    */     //   Java source line #45	-> byte code offset #24
/* 191:    */     //   Java source line #48	-> byte code offset #26
/* 192:    */     //   Java source line #49	-> byte code offset #32
/* 193:    */     //   Java source line #48	-> byte code offset #89
/* 194:    */     //   Java source line #51	-> byte code offset #92
/* 195:    */     //   Java source line #52	-> byte code offset #101
/* 196:    */     //   Java source line #53	-> byte code offset #111
/* 197:    */     //   Java source line #48	-> byte code offset #187
/* 198:    */     //   Java source line #53	-> byte code offset #196
/* 199:    */     //   Java source line #48	-> byte code offset #240
/* 200:    */     //   Java source line #53	-> byte code offset #248
/* 201:    */     //   Java source line #54	-> byte code offset #288
/* 202:    */     // Local variable table:
/* 203:    */     //   start	length	slot	name	signature
/* 204:    */     //   0	301	0	this	EntityDbTable
/* 205:    */     //   0	301	1	paramDbKey	DbKey
/* 206:    */     //   19	260	2	localObject1	Object
/* 207:    */     //   287	10	2	localSQLException	SQLException
/* 208:    */     //   31	239	3	localObject2	Object
/* 209:    */     //   87	144	4	localPreparedStatement	PreparedStatement
/* 210:    */     //   240	6	4	localThrowable1	Throwable
/* 211:    */     //   90	131	5	localObject3	Object
/* 212:    */     //   109	76	6	localObject4	Object
/* 213:    */     //   187	7	6	localThrowable2	Throwable
/* 214:    */     //   131	5	7	localThrowable3	Throwable
/* 215:    */     //   167	4	7	localThrowable4	Throwable
/* 216:    */     //   196	42	8	localObject5	Object
/* 217:    */     //   218	5	9	localThrowable5	Throwable
/* 218:    */     //   248	37	10	localObject6	Object
/* 219:    */     //   267	4	11	localThrowable6	Throwable
/* 220:    */     // Exception table:
/* 221:    */     //   from	to	target	type
/* 222:    */     //   121	128	131	java/lang/Throwable
/* 223:    */     //   158	164	167	java/lang/Throwable
/* 224:    */     //   92	111	187	java/lang/Throwable
/* 225:    */     //   92	111	196	finally
/* 226:    */     //   187	198	196	finally
/* 227:    */     //   208	215	218	java/lang/Throwable
/* 228:    */     //   32	150	240	java/lang/Throwable
/* 229:    */     //   187	240	240	java/lang/Throwable
/* 230:    */     //   32	150	248	finally
/* 231:    */     //   187	250	248	finally
/* 232:    */     //   258	264	267	java/lang/Throwable
/* 233:    */     //   26	184	287	java/sql/SQLException
/* 234:    */     //   187	287	287	java/sql/SQLException
/* 235:    */   }
/* 236:    */   
/* 237:    */   /* Error */
/* 238:    */   public final T get(DbKey paramDbKey, int paramInt)
/* 239:    */   {
/* 240:    */     // Byte code:
/* 241:    */     //   0: aload_0
/* 242:    */     //   1: iload_2
/* 243:    */     //   2: invokevirtual 40	nxt/db/EntityDbTable:checkAvailable	(I)V
/* 244:    */     //   5: invokestatic 24	nxt/db/Db:getConnection	()Ljava/sql/Connection;
/* 245:    */     //   8: astore_3
/* 246:    */     //   9: aconst_null
/* 247:    */     //   10: astore 4
/* 248:    */     //   12: aload_3
/* 249:    */     //   13: new 5	java/lang/StringBuilder
/* 250:    */     //   16: dup
/* 251:    */     //   17: invokespecial 6	java/lang/StringBuilder:<init>	()V
/* 252:    */     //   20: ldc 25
/* 253:    */     //   22: invokevirtual 8	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 254:    */     //   25: aload_0
/* 255:    */     //   26: getfield 21	nxt/db/EntityDbTable:table	Ljava/lang/String;
/* 256:    */     //   29: invokevirtual 8	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 257:    */     //   32: aload_0
/* 258:    */     //   33: getfield 3	nxt/db/EntityDbTable:dbKeyFactory	Lnxt/db/DbKey$Factory;
/* 259:    */     //   36: invokevirtual 26	nxt/db/DbKey$Factory:getPKClause	()Ljava/lang/String;
/* 260:    */     //   39: invokevirtual 8	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 261:    */     //   42: ldc 41
/* 262:    */     //   44: invokevirtual 8	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 263:    */     //   47: aload_0
/* 264:    */     //   48: getfield 4	nxt/db/EntityDbTable:multiversion	Z
/* 265:    */     //   51: ifeq +43 -> 94
/* 266:    */     //   54: new 5	java/lang/StringBuilder
/* 267:    */     //   57: dup
/* 268:    */     //   58: invokespecial 6	java/lang/StringBuilder:<init>	()V
/* 269:    */     //   61: ldc 42
/* 270:    */     //   63: invokevirtual 8	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 271:    */     //   66: aload_0
/* 272:    */     //   67: getfield 21	nxt/db/EntityDbTable:table	Ljava/lang/String;
/* 273:    */     //   70: invokevirtual 8	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 274:    */     //   73: aload_0
/* 275:    */     //   74: getfield 3	nxt/db/EntityDbTable:dbKeyFactory	Lnxt/db/DbKey$Factory;
/* 276:    */     //   77: invokevirtual 26	nxt/db/DbKey$Factory:getPKClause	()Ljava/lang/String;
/* 277:    */     //   80: invokevirtual 8	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 278:    */     //   83: ldc 43
/* 279:    */     //   85: invokevirtual 8	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 280:    */     //   88: invokevirtual 11	java/lang/StringBuilder:toString	()Ljava/lang/String;
/* 281:    */     //   91: goto +5 -> 96
/* 282:    */     //   94: ldc 28
/* 283:    */     //   96: invokevirtual 8	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 284:    */     //   99: invokevirtual 11	java/lang/StringBuilder:toString	()Ljava/lang/String;
/* 285:    */     //   102: invokeinterface 29 2 0
/* 286:    */     //   107: astore 5
/* 287:    */     //   109: aconst_null
/* 288:    */     //   110: astore 6
/* 289:    */     //   112: aload_1
/* 290:    */     //   113: aload 5
/* 291:    */     //   115: invokeinterface 30 2 0
/* 292:    */     //   120: istore 7
/* 293:    */     //   122: aload 5
/* 294:    */     //   124: iload 7
/* 295:    */     //   126: iload_2
/* 296:    */     //   127: invokeinterface 44 3 0
/* 297:    */     //   132: aload_0
/* 298:    */     //   133: getfield 4	nxt/db/EntityDbTable:multiversion	Z
/* 299:    */     //   136: ifeq +28 -> 164
/* 300:    */     //   139: aload_1
/* 301:    */     //   140: aload 5
/* 302:    */     //   142: iinc 7 1
/* 303:    */     //   145: iload 7
/* 304:    */     //   147: invokeinterface 45 3 0
/* 305:    */     //   152: istore 7
/* 306:    */     //   154: aload 5
/* 307:    */     //   156: iload 7
/* 308:    */     //   158: iload_2
/* 309:    */     //   159: invokeinterface 44 3 0
/* 310:    */     //   164: aload_0
/* 311:    */     //   165: aload_3
/* 312:    */     //   166: aload 5
/* 313:    */     //   168: iconst_0
/* 314:    */     //   169: invokespecial 31	nxt/db/EntityDbTable:get	(Ljava/sql/Connection;Ljava/sql/PreparedStatement;Z)Ljava/lang/Object;
/* 315:    */     //   172: astore 8
/* 316:    */     //   174: aload 5
/* 317:    */     //   176: ifnull +37 -> 213
/* 318:    */     //   179: aload 6
/* 319:    */     //   181: ifnull +25 -> 206
/* 320:    */     //   184: aload 5
/* 321:    */     //   186: invokeinterface 32 1 0
/* 322:    */     //   191: goto +22 -> 213
/* 323:    */     //   194: astore 9
/* 324:    */     //   196: aload 6
/* 325:    */     //   198: aload 9
/* 326:    */     //   200: invokevirtual 34	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 327:    */     //   203: goto +10 -> 213
/* 328:    */     //   206: aload 5
/* 329:    */     //   208: invokeinterface 32 1 0
/* 330:    */     //   213: aload_3
/* 331:    */     //   214: ifnull +35 -> 249
/* 332:    */     //   217: aload 4
/* 333:    */     //   219: ifnull +24 -> 243
/* 334:    */     //   222: aload_3
/* 335:    */     //   223: invokeinterface 35 1 0
/* 336:    */     //   228: goto +21 -> 249
/* 337:    */     //   231: astore 9
/* 338:    */     //   233: aload 4
/* 339:    */     //   235: aload 9
/* 340:    */     //   237: invokevirtual 34	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 341:    */     //   240: goto +9 -> 249
/* 342:    */     //   243: aload_3
/* 343:    */     //   244: invokeinterface 35 1 0
/* 344:    */     //   249: aload 8
/* 345:    */     //   251: areturn
/* 346:    */     //   252: astore 7
/* 347:    */     //   254: aload 7
/* 348:    */     //   256: astore 6
/* 349:    */     //   258: aload 7
/* 350:    */     //   260: athrow
/* 351:    */     //   261: astore 10
/* 352:    */     //   263: aload 5
/* 353:    */     //   265: ifnull +37 -> 302
/* 354:    */     //   268: aload 6
/* 355:    */     //   270: ifnull +25 -> 295
/* 356:    */     //   273: aload 5
/* 357:    */     //   275: invokeinterface 32 1 0
/* 358:    */     //   280: goto +22 -> 302
/* 359:    */     //   283: astore 11
/* 360:    */     //   285: aload 6
/* 361:    */     //   287: aload 11
/* 362:    */     //   289: invokevirtual 34	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 363:    */     //   292: goto +10 -> 302
/* 364:    */     //   295: aload 5
/* 365:    */     //   297: invokeinterface 32 1 0
/* 366:    */     //   302: aload 10
/* 367:    */     //   304: athrow
/* 368:    */     //   305: astore 5
/* 369:    */     //   307: aload 5
/* 370:    */     //   309: astore 4
/* 371:    */     //   311: aload 5
/* 372:    */     //   313: athrow
/* 373:    */     //   314: astore 12
/* 374:    */     //   316: aload_3
/* 375:    */     //   317: ifnull +35 -> 352
/* 376:    */     //   320: aload 4
/* 377:    */     //   322: ifnull +24 -> 346
/* 378:    */     //   325: aload_3
/* 379:    */     //   326: invokeinterface 35 1 0
/* 380:    */     //   331: goto +21 -> 352
/* 381:    */     //   334: astore 13
/* 382:    */     //   336: aload 4
/* 383:    */     //   338: aload 13
/* 384:    */     //   340: invokevirtual 34	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 385:    */     //   343: goto +9 -> 352
/* 386:    */     //   346: aload_3
/* 387:    */     //   347: invokeinterface 35 1 0
/* 388:    */     //   352: aload 12
/* 389:    */     //   354: athrow
/* 390:    */     //   355: astore_3
/* 391:    */     //   356: new 37	java/lang/RuntimeException
/* 392:    */     //   359: dup
/* 393:    */     //   360: aload_3
/* 394:    */     //   361: invokevirtual 38	java/sql/SQLException:toString	()Ljava/lang/String;
/* 395:    */     //   364: aload_3
/* 396:    */     //   365: invokespecial 39	java/lang/RuntimeException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
/* 397:    */     //   368: athrow
/* 398:    */     // Line number table:
/* 399:    */     //   Java source line #59	-> byte code offset #0
/* 400:    */     //   Java source line #60	-> byte code offset #5
/* 401:    */     //   Java source line #61	-> byte code offset #12
/* 402:    */     //   Java source line #60	-> byte code offset #109
/* 403:    */     //   Java source line #64	-> byte code offset #112
/* 404:    */     //   Java source line #65	-> byte code offset #122
/* 405:    */     //   Java source line #66	-> byte code offset #132
/* 406:    */     //   Java source line #67	-> byte code offset #139
/* 407:    */     //   Java source line #68	-> byte code offset #154
/* 408:    */     //   Java source line #70	-> byte code offset #164
/* 409:    */     //   Java source line #71	-> byte code offset #174
/* 410:    */     //   Java source line #60	-> byte code offset #252
/* 411:    */     //   Java source line #71	-> byte code offset #261
/* 412:    */     //   Java source line #60	-> byte code offset #305
/* 413:    */     //   Java source line #71	-> byte code offset #314
/* 414:    */     //   Java source line #72	-> byte code offset #356
/* 415:    */     // Local variable table:
/* 416:    */     //   start	length	slot	name	signature
/* 417:    */     //   0	369	0	this	EntityDbTable
/* 418:    */     //   0	369	1	paramDbKey	DbKey
/* 419:    */     //   0	369	2	paramInt	int
/* 420:    */     //   8	339	3	localConnection	Connection
/* 421:    */     //   355	10	3	localSQLException	SQLException
/* 422:    */     //   10	327	4	localObject1	Object
/* 423:    */     //   107	189	5	localPreparedStatement	PreparedStatement
/* 424:    */     //   305	7	5	localThrowable1	Throwable
/* 425:    */     //   110	176	6	localObject2	Object
/* 426:    */     //   120	37	7	i	int
/* 427:    */     //   252	7	7	localThrowable2	Throwable
/* 428:    */     //   194	5	9	localThrowable3	Throwable
/* 429:    */     //   231	5	9	localThrowable4	Throwable
/* 430:    */     //   261	42	10	localObject4	Object
/* 431:    */     //   283	5	11	localThrowable5	Throwable
/* 432:    */     //   314	39	12	localObject5	Object
/* 433:    */     //   334	5	13	localThrowable6	Throwable
/* 434:    */     // Exception table:
/* 435:    */     //   from	to	target	type
/* 436:    */     //   184	191	194	java/lang/Throwable
/* 437:    */     //   222	228	231	java/lang/Throwable
/* 438:    */     //   112	174	252	java/lang/Throwable
/* 439:    */     //   112	174	261	finally
/* 440:    */     //   252	263	261	finally
/* 441:    */     //   273	280	283	java/lang/Throwable
/* 442:    */     //   12	213	305	java/lang/Throwable
/* 443:    */     //   252	305	305	java/lang/Throwable
/* 444:    */     //   12	213	314	finally
/* 445:    */     //   252	316	314	finally
/* 446:    */     //   325	331	334	java/lang/Throwable
/* 447:    */     //   5	249	355	java/sql/SQLException
/* 448:    */     //   252	355	355	java/sql/SQLException
/* 449:    */   }
/* 450:    */   
/* 451:    */   /* Error */
/* 452:    */   public final T getBy(DbClause paramDbClause)
/* 453:    */   {
/* 454:    */     // Byte code:
/* 455:    */     //   0: invokestatic 24	nxt/db/Db:getConnection	()Ljava/sql/Connection;
/* 456:    */     //   3: astore_2
/* 457:    */     //   4: aconst_null
/* 458:    */     //   5: astore_3
/* 459:    */     //   6: aload_2
/* 460:    */     //   7: new 5	java/lang/StringBuilder
/* 461:    */     //   10: dup
/* 462:    */     //   11: invokespecial 6	java/lang/StringBuilder:<init>	()V
/* 463:    */     //   14: ldc 25
/* 464:    */     //   16: invokevirtual 8	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 465:    */     //   19: aload_0
/* 466:    */     //   20: getfield 21	nxt/db/EntityDbTable:table	Ljava/lang/String;
/* 467:    */     //   23: invokevirtual 8	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 468:    */     //   26: ldc 46
/* 469:    */     //   28: invokevirtual 8	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 470:    */     //   31: aload_1
/* 471:    */     //   32: invokevirtual 47	nxt/db/DbClause:getClause	()Ljava/lang/String;
/* 472:    */     //   35: invokevirtual 8	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 473:    */     //   38: aload_0
/* 474:    */     //   39: getfield 4	nxt/db/EntityDbTable:multiversion	Z
/* 475:    */     //   42: ifeq +8 -> 50
/* 476:    */     //   45: ldc 27
/* 477:    */     //   47: goto +5 -> 52
/* 478:    */     //   50: ldc 28
/* 479:    */     //   52: invokevirtual 8	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 480:    */     //   55: invokevirtual 11	java/lang/StringBuilder:toString	()Ljava/lang/String;
/* 481:    */     //   58: invokeinterface 29 2 0
/* 482:    */     //   63: astore 4
/* 483:    */     //   65: aconst_null
/* 484:    */     //   66: astore 5
/* 485:    */     //   68: aload_1
/* 486:    */     //   69: aload 4
/* 487:    */     //   71: iconst_1
/* 488:    */     //   72: invokevirtual 48	nxt/db/DbClause:set	(Ljava/sql/PreparedStatement;I)I
/* 489:    */     //   75: pop
/* 490:    */     //   76: aload_0
/* 491:    */     //   77: aload_2
/* 492:    */     //   78: aload 4
/* 493:    */     //   80: iconst_1
/* 494:    */     //   81: invokespecial 31	nxt/db/EntityDbTable:get	(Ljava/sql/Connection;Ljava/sql/PreparedStatement;Z)Ljava/lang/Object;
/* 495:    */     //   84: astore 6
/* 496:    */     //   86: aload 4
/* 497:    */     //   88: ifnull +37 -> 125
/* 498:    */     //   91: aload 5
/* 499:    */     //   93: ifnull +25 -> 118
/* 500:    */     //   96: aload 4
/* 501:    */     //   98: invokeinterface 32 1 0
/* 502:    */     //   103: goto +22 -> 125
/* 503:    */     //   106: astore 7
/* 504:    */     //   108: aload 5
/* 505:    */     //   110: aload 7
/* 506:    */     //   112: invokevirtual 34	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 507:    */     //   115: goto +10 -> 125
/* 508:    */     //   118: aload 4
/* 509:    */     //   120: invokeinterface 32 1 0
/* 510:    */     //   125: aload_2
/* 511:    */     //   126: ifnull +33 -> 159
/* 512:    */     //   129: aload_3
/* 513:    */     //   130: ifnull +23 -> 153
/* 514:    */     //   133: aload_2
/* 515:    */     //   134: invokeinterface 35 1 0
/* 516:    */     //   139: goto +20 -> 159
/* 517:    */     //   142: astore 7
/* 518:    */     //   144: aload_3
/* 519:    */     //   145: aload 7
/* 520:    */     //   147: invokevirtual 34	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 521:    */     //   150: goto +9 -> 159
/* 522:    */     //   153: aload_2
/* 523:    */     //   154: invokeinterface 35 1 0
/* 524:    */     //   159: aload 6
/* 525:    */     //   161: areturn
/* 526:    */     //   162: astore 6
/* 527:    */     //   164: aload 6
/* 528:    */     //   166: astore 5
/* 529:    */     //   168: aload 6
/* 530:    */     //   170: athrow
/* 531:    */     //   171: astore 8
/* 532:    */     //   173: aload 4
/* 533:    */     //   175: ifnull +37 -> 212
/* 534:    */     //   178: aload 5
/* 535:    */     //   180: ifnull +25 -> 205
/* 536:    */     //   183: aload 4
/* 537:    */     //   185: invokeinterface 32 1 0
/* 538:    */     //   190: goto +22 -> 212
/* 539:    */     //   193: astore 9
/* 540:    */     //   195: aload 5
/* 541:    */     //   197: aload 9
/* 542:    */     //   199: invokevirtual 34	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 543:    */     //   202: goto +10 -> 212
/* 544:    */     //   205: aload 4
/* 545:    */     //   207: invokeinterface 32 1 0
/* 546:    */     //   212: aload 8
/* 547:    */     //   214: athrow
/* 548:    */     //   215: astore 4
/* 549:    */     //   217: aload 4
/* 550:    */     //   219: astore_3
/* 551:    */     //   220: aload 4
/* 552:    */     //   222: athrow
/* 553:    */     //   223: astore 10
/* 554:    */     //   225: aload_2
/* 555:    */     //   226: ifnull +33 -> 259
/* 556:    */     //   229: aload_3
/* 557:    */     //   230: ifnull +23 -> 253
/* 558:    */     //   233: aload_2
/* 559:    */     //   234: invokeinterface 35 1 0
/* 560:    */     //   239: goto +20 -> 259
/* 561:    */     //   242: astore 11
/* 562:    */     //   244: aload_3
/* 563:    */     //   245: aload 11
/* 564:    */     //   247: invokevirtual 34	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 565:    */     //   250: goto +9 -> 259
/* 566:    */     //   253: aload_2
/* 567:    */     //   254: invokeinterface 35 1 0
/* 568:    */     //   259: aload 10
/* 569:    */     //   261: athrow
/* 570:    */     //   262: astore_2
/* 571:    */     //   263: new 37	java/lang/RuntimeException
/* 572:    */     //   266: dup
/* 573:    */     //   267: aload_2
/* 574:    */     //   268: invokevirtual 38	java/sql/SQLException:toString	()Ljava/lang/String;
/* 575:    */     //   271: aload_2
/* 576:    */     //   272: invokespecial 39	java/lang/RuntimeException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
/* 577:    */     //   275: athrow
/* 578:    */     // Line number table:
/* 579:    */     //   Java source line #77	-> byte code offset #0
/* 580:    */     //   Java source line #78	-> byte code offset #6
/* 581:    */     //   Java source line #77	-> byte code offset #65
/* 582:    */     //   Java source line #80	-> byte code offset #68
/* 583:    */     //   Java source line #81	-> byte code offset #76
/* 584:    */     //   Java source line #82	-> byte code offset #86
/* 585:    */     //   Java source line #77	-> byte code offset #162
/* 586:    */     //   Java source line #82	-> byte code offset #171
/* 587:    */     //   Java source line #77	-> byte code offset #215
/* 588:    */     //   Java source line #82	-> byte code offset #223
/* 589:    */     //   Java source line #83	-> byte code offset #263
/* 590:    */     // Local variable table:
/* 591:    */     //   start	length	slot	name	signature
/* 592:    */     //   0	276	0	this	EntityDbTable
/* 593:    */     //   0	276	1	paramDbClause	DbClause
/* 594:    */     //   3	251	2	localConnection	Connection
/* 595:    */     //   262	10	2	localSQLException	SQLException
/* 596:    */     //   5	240	3	localObject1	Object
/* 597:    */     //   63	143	4	localPreparedStatement	PreparedStatement
/* 598:    */     //   215	6	4	localThrowable1	Throwable
/* 599:    */     //   66	130	5	localObject2	Object
/* 600:    */     //   162	7	6	localThrowable2	Throwable
/* 601:    */     //   106	5	7	localThrowable3	Throwable
/* 602:    */     //   142	4	7	localThrowable4	Throwable
/* 603:    */     //   171	42	8	localObject4	Object
/* 604:    */     //   193	5	9	localThrowable5	Throwable
/* 605:    */     //   223	37	10	localObject5	Object
/* 606:    */     //   242	4	11	localThrowable6	Throwable
/* 607:    */     // Exception table:
/* 608:    */     //   from	to	target	type
/* 609:    */     //   96	103	106	java/lang/Throwable
/* 610:    */     //   133	139	142	java/lang/Throwable
/* 611:    */     //   68	86	162	java/lang/Throwable
/* 612:    */     //   68	86	171	finally
/* 613:    */     //   162	173	171	finally
/* 614:    */     //   183	190	193	java/lang/Throwable
/* 615:    */     //   6	125	215	java/lang/Throwable
/* 616:    */     //   162	215	215	java/lang/Throwable
/* 617:    */     //   6	125	223	finally
/* 618:    */     //   162	225	223	finally
/* 619:    */     //   233	239	242	java/lang/Throwable
/* 620:    */     //   0	159	262	java/sql/SQLException
/* 621:    */     //   162	262	262	java/sql/SQLException
/* 622:    */   }
/* 623:    */   
/* 624:    */   /* Error */
/* 625:    */   public final T getBy(DbClause paramDbClause, int paramInt)
/* 626:    */   {
/* 627:    */     // Byte code:
/* 628:    */     //   0: aload_0
/* 629:    */     //   1: iload_2
/* 630:    */     //   2: invokevirtual 40	nxt/db/EntityDbTable:checkAvailable	(I)V
/* 631:    */     //   5: invokestatic 24	nxt/db/Db:getConnection	()Ljava/sql/Connection;
/* 632:    */     //   8: astore_3
/* 633:    */     //   9: aconst_null
/* 634:    */     //   10: astore 4
/* 635:    */     //   12: aload_3
/* 636:    */     //   13: new 5	java/lang/StringBuilder
/* 637:    */     //   16: dup
/* 638:    */     //   17: invokespecial 6	java/lang/StringBuilder:<init>	()V
/* 639:    */     //   20: ldc 25
/* 640:    */     //   22: invokevirtual 8	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 641:    */     //   25: aload_0
/* 642:    */     //   26: getfield 21	nxt/db/EntityDbTable:table	Ljava/lang/String;
/* 643:    */     //   29: invokevirtual 8	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 644:    */     //   32: ldc 49
/* 645:    */     //   34: invokevirtual 8	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 646:    */     //   37: aload_1
/* 647:    */     //   38: invokevirtual 47	nxt/db/DbClause:getClause	()Ljava/lang/String;
/* 648:    */     //   41: invokevirtual 8	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 649:    */     //   44: ldc 41
/* 650:    */     //   46: invokevirtual 8	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 651:    */     //   49: aload_0
/* 652:    */     //   50: getfield 4	nxt/db/EntityDbTable:multiversion	Z
/* 653:    */     //   53: ifeq +48 -> 101
/* 654:    */     //   56: new 5	java/lang/StringBuilder
/* 655:    */     //   59: dup
/* 656:    */     //   60: invokespecial 6	java/lang/StringBuilder:<init>	()V
/* 657:    */     //   63: ldc 42
/* 658:    */     //   65: invokevirtual 8	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 659:    */     //   68: aload_0
/* 660:    */     //   69: getfield 21	nxt/db/EntityDbTable:table	Ljava/lang/String;
/* 661:    */     //   72: invokevirtual 8	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 662:    */     //   75: ldc 50
/* 663:    */     //   77: invokevirtual 8	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 664:    */     //   80: aload_0
/* 665:    */     //   81: getfield 3	nxt/db/EntityDbTable:dbKeyFactory	Lnxt/db/DbKey$Factory;
/* 666:    */     //   84: invokevirtual 51	nxt/db/DbKey$Factory:getSelfJoinClause	()Ljava/lang/String;
/* 667:    */     //   87: invokevirtual 8	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 668:    */     //   90: ldc 52
/* 669:    */     //   92: invokevirtual 8	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 670:    */     //   95: invokevirtual 11	java/lang/StringBuilder:toString	()Ljava/lang/String;
/* 671:    */     //   98: goto +5 -> 103
/* 672:    */     //   101: ldc 28
/* 673:    */     //   103: invokevirtual 8	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 674:    */     //   106: invokevirtual 11	java/lang/StringBuilder:toString	()Ljava/lang/String;
/* 675:    */     //   109: invokeinterface 29 2 0
/* 676:    */     //   114: astore 5
/* 677:    */     //   116: aconst_null
/* 678:    */     //   117: astore 6
/* 679:    */     //   119: iconst_0
/* 680:    */     //   120: istore 7
/* 681:    */     //   122: aload_1
/* 682:    */     //   123: aload 5
/* 683:    */     //   125: iinc 7 1
/* 684:    */     //   128: iload 7
/* 685:    */     //   130: invokevirtual 48	nxt/db/DbClause:set	(Ljava/sql/PreparedStatement;I)I
/* 686:    */     //   133: istore 7
/* 687:    */     //   135: aload 5
/* 688:    */     //   137: iload 7
/* 689:    */     //   139: iload_2
/* 690:    */     //   140: invokeinterface 44 3 0
/* 691:    */     //   145: aload_0
/* 692:    */     //   146: getfield 4	nxt/db/EntityDbTable:multiversion	Z
/* 693:    */     //   149: ifeq +16 -> 165
/* 694:    */     //   152: aload 5
/* 695:    */     //   154: iinc 7 1
/* 696:    */     //   157: iload 7
/* 697:    */     //   159: iload_2
/* 698:    */     //   160: invokeinterface 44 3 0
/* 699:    */     //   165: aload_0
/* 700:    */     //   166: aload_3
/* 701:    */     //   167: aload 5
/* 702:    */     //   169: iconst_0
/* 703:    */     //   170: invokespecial 31	nxt/db/EntityDbTable:get	(Ljava/sql/Connection;Ljava/sql/PreparedStatement;Z)Ljava/lang/Object;
/* 704:    */     //   173: astore 8
/* 705:    */     //   175: aload 5
/* 706:    */     //   177: ifnull +37 -> 214
/* 707:    */     //   180: aload 6
/* 708:    */     //   182: ifnull +25 -> 207
/* 709:    */     //   185: aload 5
/* 710:    */     //   187: invokeinterface 32 1 0
/* 711:    */     //   192: goto +22 -> 214
/* 712:    */     //   195: astore 9
/* 713:    */     //   197: aload 6
/* 714:    */     //   199: aload 9
/* 715:    */     //   201: invokevirtual 34	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 716:    */     //   204: goto +10 -> 214
/* 717:    */     //   207: aload 5
/* 718:    */     //   209: invokeinterface 32 1 0
/* 719:    */     //   214: aload_3
/* 720:    */     //   215: ifnull +35 -> 250
/* 721:    */     //   218: aload 4
/* 722:    */     //   220: ifnull +24 -> 244
/* 723:    */     //   223: aload_3
/* 724:    */     //   224: invokeinterface 35 1 0
/* 725:    */     //   229: goto +21 -> 250
/* 726:    */     //   232: astore 9
/* 727:    */     //   234: aload 4
/* 728:    */     //   236: aload 9
/* 729:    */     //   238: invokevirtual 34	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 730:    */     //   241: goto +9 -> 250
/* 731:    */     //   244: aload_3
/* 732:    */     //   245: invokeinterface 35 1 0
/* 733:    */     //   250: aload 8
/* 734:    */     //   252: areturn
/* 735:    */     //   253: astore 7
/* 736:    */     //   255: aload 7
/* 737:    */     //   257: astore 6
/* 738:    */     //   259: aload 7
/* 739:    */     //   261: athrow
/* 740:    */     //   262: astore 10
/* 741:    */     //   264: aload 5
/* 742:    */     //   266: ifnull +37 -> 303
/* 743:    */     //   269: aload 6
/* 744:    */     //   271: ifnull +25 -> 296
/* 745:    */     //   274: aload 5
/* 746:    */     //   276: invokeinterface 32 1 0
/* 747:    */     //   281: goto +22 -> 303
/* 748:    */     //   284: astore 11
/* 749:    */     //   286: aload 6
/* 750:    */     //   288: aload 11
/* 751:    */     //   290: invokevirtual 34	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 752:    */     //   293: goto +10 -> 303
/* 753:    */     //   296: aload 5
/* 754:    */     //   298: invokeinterface 32 1 0
/* 755:    */     //   303: aload 10
/* 756:    */     //   305: athrow
/* 757:    */     //   306: astore 5
/* 758:    */     //   308: aload 5
/* 759:    */     //   310: astore 4
/* 760:    */     //   312: aload 5
/* 761:    */     //   314: athrow
/* 762:    */     //   315: astore 12
/* 763:    */     //   317: aload_3
/* 764:    */     //   318: ifnull +35 -> 353
/* 765:    */     //   321: aload 4
/* 766:    */     //   323: ifnull +24 -> 347
/* 767:    */     //   326: aload_3
/* 768:    */     //   327: invokeinterface 35 1 0
/* 769:    */     //   332: goto +21 -> 353
/* 770:    */     //   335: astore 13
/* 771:    */     //   337: aload 4
/* 772:    */     //   339: aload 13
/* 773:    */     //   341: invokevirtual 34	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 774:    */     //   344: goto +9 -> 353
/* 775:    */     //   347: aload_3
/* 776:    */     //   348: invokeinterface 35 1 0
/* 777:    */     //   353: aload 12
/* 778:    */     //   355: athrow
/* 779:    */     //   356: astore_3
/* 780:    */     //   357: new 37	java/lang/RuntimeException
/* 781:    */     //   360: dup
/* 782:    */     //   361: aload_3
/* 783:    */     //   362: invokevirtual 38	java/sql/SQLException:toString	()Ljava/lang/String;
/* 784:    */     //   365: aload_3
/* 785:    */     //   366: invokespecial 39	java/lang/RuntimeException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
/* 786:    */     //   369: athrow
/* 787:    */     // Line number table:
/* 788:    */     //   Java source line #88	-> byte code offset #0
/* 789:    */     //   Java source line #89	-> byte code offset #5
/* 790:    */     //   Java source line #90	-> byte code offset #12
/* 791:    */     //   Java source line #89	-> byte code offset #116
/* 792:    */     //   Java source line #94	-> byte code offset #119
/* 793:    */     //   Java source line #95	-> byte code offset #122
/* 794:    */     //   Java source line #96	-> byte code offset #135
/* 795:    */     //   Java source line #97	-> byte code offset #145
/* 796:    */     //   Java source line #98	-> byte code offset #152
/* 797:    */     //   Java source line #100	-> byte code offset #165
/* 798:    */     //   Java source line #101	-> byte code offset #175
/* 799:    */     //   Java source line #89	-> byte code offset #253
/* 800:    */     //   Java source line #101	-> byte code offset #262
/* 801:    */     //   Java source line #89	-> byte code offset #306
/* 802:    */     //   Java source line #101	-> byte code offset #315
/* 803:    */     //   Java source line #102	-> byte code offset #357
/* 804:    */     // Local variable table:
/* 805:    */     //   start	length	slot	name	signature
/* 806:    */     //   0	370	0	this	EntityDbTable
/* 807:    */     //   0	370	1	paramDbClause	DbClause
/* 808:    */     //   0	370	2	paramInt	int
/* 809:    */     //   8	340	3	localConnection	Connection
/* 810:    */     //   356	10	3	localSQLException	SQLException
/* 811:    */     //   10	328	4	localObject1	Object
/* 812:    */     //   114	183	5	localPreparedStatement	PreparedStatement
/* 813:    */     //   306	7	5	localThrowable1	Throwable
/* 814:    */     //   117	170	6	localObject2	Object
/* 815:    */     //   120	38	7	i	int
/* 816:    */     //   253	7	7	localThrowable2	Throwable
/* 817:    */     //   195	5	9	localThrowable3	Throwable
/* 818:    */     //   232	5	9	localThrowable4	Throwable
/* 819:    */     //   262	42	10	localObject4	Object
/* 820:    */     //   284	5	11	localThrowable5	Throwable
/* 821:    */     //   315	39	12	localObject5	Object
/* 822:    */     //   335	5	13	localThrowable6	Throwable
/* 823:    */     // Exception table:
/* 824:    */     //   from	to	target	type
/* 825:    */     //   185	192	195	java/lang/Throwable
/* 826:    */     //   223	229	232	java/lang/Throwable
/* 827:    */     //   119	175	253	java/lang/Throwable
/* 828:    */     //   119	175	262	finally
/* 829:    */     //   253	264	262	finally
/* 830:    */     //   274	281	284	java/lang/Throwable
/* 831:    */     //   12	214	306	java/lang/Throwable
/* 832:    */     //   253	306	306	java/lang/Throwable
/* 833:    */     //   12	214	315	finally
/* 834:    */     //   253	317	315	finally
/* 835:    */     //   326	332	335	java/lang/Throwable
/* 836:    */     //   5	250	356	java/sql/SQLException
/* 837:    */     //   253	356	356	java/sql/SQLException
/* 838:    */   }
/* 839:    */   
/* 840:    */   private T get(Connection paramConnection, PreparedStatement paramPreparedStatement, boolean paramBoolean)
/* 841:    */     throws SQLException
/* 842:    */   {
/* 843:107 */     int i = (paramBoolean) && (Db.isInTransaction()) ? 1 : 0;
/* 844:108 */     ResultSet localResultSet = paramPreparedStatement.executeQuery();Object localObject1 = null;
/* 845:    */     try
/* 846:    */     {
/* 847:109 */       if (!localResultSet.next()) {
/* 848:110 */         return null;
/* 849:    */       }
/* 850:112 */       Object localObject2 = null;
/* 851:113 */       DbKey localDbKey = null;
/* 852:114 */       if (i != 0)
/* 853:    */       {
/* 854:115 */         localDbKey = this.dbKeyFactory.newKey(localResultSet);
/* 855:116 */         localObject2 = Db.getCache(this.table).get(localDbKey);
/* 856:    */       }
/* 857:118 */       if (localObject2 == null)
/* 858:    */       {
/* 859:119 */         localObject2 = load(paramConnection, localResultSet);
/* 860:120 */         if (i != 0) {
/* 861:121 */           Db.getCache(this.table).put(localDbKey, localObject2);
/* 862:    */         }
/* 863:    */       }
/* 864:124 */       if (localResultSet.next()) {
/* 865:125 */         throw new RuntimeException("Multiple records found");
/* 866:    */       }
/* 867:127 */       return (T)localObject2;
/* 868:    */     }
/* 869:    */     catch (Throwable localThrowable1)
/* 870:    */     {
/* 871:108 */       localObject1 = localThrowable1;throw localThrowable1;
/* 872:    */     }
/* 873:    */     finally
/* 874:    */     {
/* 875:128 */       if (localResultSet != null) {
/* 876:128 */         if (localObject1 != null) {
/* 877:    */           try
/* 878:    */           {
/* 879:128 */             localResultSet.close();
/* 880:    */           }
/* 881:    */           catch (Throwable localThrowable4)
/* 882:    */           {
/* 883:128 */             ((Throwable)localObject1).addSuppressed(localThrowable4);
/* 884:    */           }
/* 885:    */         } else {
/* 886:128 */           localResultSet.close();
/* 887:    */         }
/* 888:    */       }
/* 889:    */     }
/* 890:    */   }
/* 891:    */   
/* 892:    */   public final DbIterator<T> getManyBy(DbClause paramDbClause, int paramInt1, int paramInt2)
/* 893:    */   {
/* 894:132 */     return getManyBy(paramDbClause, paramInt1, paramInt2, defaultSort());
/* 895:    */   }
/* 896:    */   
/* 897:    */   public final DbIterator<T> getManyBy(DbClause paramDbClause, int paramInt1, int paramInt2, String paramString)
/* 898:    */   {
/* 899:136 */     Connection localConnection = null;
/* 900:    */     try
/* 901:    */     {
/* 902:138 */       localConnection = Db.getConnection();
/* 903:139 */       PreparedStatement localPreparedStatement = localConnection.prepareStatement("SELECT * FROM " + this.table + " WHERE " + paramDbClause.getClause() + (this.multiversion ? " AND latest = TRUE " : " ") + paramString + DbUtils.limitsClause(paramInt1, paramInt2));
/* 904:    */       
/* 905:    */ 
/* 906:142 */       int i = 0;
/* 907:143 */       i = paramDbClause.set(localPreparedStatement, ++i);
/* 908:144 */       i = DbUtils.setLimits(i, localPreparedStatement, paramInt1, paramInt2);
/* 909:145 */       return getManyBy(localConnection, localPreparedStatement, true);
/* 910:    */     }
/* 911:    */     catch (SQLException localSQLException)
/* 912:    */     {
/* 913:147 */       DbUtils.close(new AutoCloseable[] { localConnection });
/* 914:148 */       throw new RuntimeException(localSQLException.toString(), localSQLException);
/* 915:    */     }
/* 916:    */   }
/* 917:    */   
/* 918:    */   public final DbIterator<T> getManyBy(DbClause paramDbClause, int paramInt1, int paramInt2, int paramInt3)
/* 919:    */   {
/* 920:153 */     return getManyBy(paramDbClause, paramInt1, paramInt2, paramInt3, defaultSort());
/* 921:    */   }
/* 922:    */   
/* 923:    */   public final DbIterator<T> getManyBy(DbClause paramDbClause, int paramInt1, int paramInt2, int paramInt3, String paramString)
/* 924:    */   {
/* 925:157 */     checkAvailable(paramInt1);
/* 926:158 */     Connection localConnection = null;
/* 927:    */     try
/* 928:    */     {
/* 929:160 */       localConnection = Db.getConnection();
/* 930:161 */       PreparedStatement localPreparedStatement = localConnection.prepareStatement("SELECT * FROM " + this.table + " AS a WHERE " + paramDbClause.getClause() + "AND a.height <= ?" + (this.multiversion ? " AND (a.latest = TRUE OR (a.latest = FALSE AND EXISTS (SELECT 1 FROM " + this.table + " AS b WHERE " + this.dbKeyFactory.getSelfJoinClause() + " AND b.height > ?) " + "AND NOT EXISTS (SELECT 1 FROM " + this.table + " AS b WHERE " + this.dbKeyFactory.getSelfJoinClause() + " AND b.height <= ? AND b.height > a.height))) " : " ") + paramString + DbUtils.limitsClause(paramInt2, paramInt3));
/* 931:    */       
/* 932:    */ 
/* 933:    */ 
/* 934:    */ 
/* 935:    */ 
/* 936:    */ 
/* 937:168 */       int i = 0;
/* 938:169 */       i = paramDbClause.set(localPreparedStatement, ++i);
/* 939:170 */       localPreparedStatement.setInt(i, paramInt1);
/* 940:171 */       if (this.multiversion)
/* 941:    */       {
/* 942:172 */         localPreparedStatement.setInt(++i, paramInt1);
/* 943:173 */         localPreparedStatement.setInt(++i, paramInt1);
/* 944:    */       }
/* 945:175 */       i++;i = DbUtils.setLimits(i, localPreparedStatement, paramInt2, paramInt3);
/* 946:176 */       return getManyBy(localConnection, localPreparedStatement, false);
/* 947:    */     }
/* 948:    */     catch (SQLException localSQLException)
/* 949:    */     {
/* 950:178 */       DbUtils.close(new AutoCloseable[] { localConnection });
/* 951:179 */       throw new RuntimeException(localSQLException.toString(), localSQLException);
/* 952:    */     }
/* 953:    */   }
/* 954:    */   
/* 955:    */   public final DbIterator<T> getManyBy(Connection paramConnection, PreparedStatement paramPreparedStatement, boolean paramBoolean)
/* 956:    */   {
/* 957:184 */     final boolean bool = (paramBoolean) && (Db.isInTransaction());
/* 958:185 */     new DbIterator(paramConnection, paramPreparedStatement, new DbIterator.ResultSetReader()
/* 959:    */     {
/* 960:    */       public T get(Connection paramAnonymousConnection, ResultSet paramAnonymousResultSet)
/* 961:    */         throws Exception
/* 962:    */       {
/* 963:188 */         Object localObject = null;
/* 964:189 */         DbKey localDbKey = null;
/* 965:190 */         if (bool)
/* 966:    */         {
/* 967:191 */           localDbKey = EntityDbTable.this.dbKeyFactory.newKey(paramAnonymousResultSet);
/* 968:192 */           localObject = Db.getCache(EntityDbTable.this.table).get(localDbKey);
/* 969:    */         }
/* 970:194 */         if (localObject == null)
/* 971:    */         {
/* 972:195 */           localObject = EntityDbTable.this.load(paramAnonymousConnection, paramAnonymousResultSet);
/* 973:196 */           if (bool) {
/* 974:197 */             Db.getCache(EntityDbTable.this.table).put(localDbKey, localObject);
/* 975:    */           }
/* 976:    */         }
/* 977:200 */         return (T)localObject;
/* 978:    */       }
/* 979:    */     });
/* 980:    */   }
/* 981:    */   
/* 982:    */   public final DbIterator<T> getAll(int paramInt1, int paramInt2)
/* 983:    */   {
/* 984:206 */     return getAll(paramInt1, paramInt2, defaultSort());
/* 985:    */   }
/* 986:    */   
/* 987:    */   public final DbIterator<T> getAll(int paramInt1, int paramInt2, String paramString)
/* 988:    */   {
/* 989:210 */     Connection localConnection = null;
/* 990:    */     try
/* 991:    */     {
/* 992:212 */       localConnection = Db.getConnection();
/* 993:213 */       PreparedStatement localPreparedStatement = localConnection.prepareStatement("SELECT * FROM " + this.table + (this.multiversion ? " WHERE latest = TRUE " : " ") + paramString + DbUtils.limitsClause(paramInt1, paramInt2));
/* 994:    */       
/* 995:    */ 
/* 996:216 */       DbUtils.setLimits(1, localPreparedStatement, paramInt1, paramInt2);
/* 997:217 */       return getManyBy(localConnection, localPreparedStatement, true);
/* 998:    */     }
/* 999:    */     catch (SQLException localSQLException)
/* :00:    */     {
/* :01:219 */       DbUtils.close(new AutoCloseable[] { localConnection });
/* :02:220 */       throw new RuntimeException(localSQLException.toString(), localSQLException);
/* :03:    */     }
/* :04:    */   }
/* :05:    */   
/* :06:    */   public final DbIterator<T> getAll(int paramInt1, int paramInt2, int paramInt3)
/* :07:    */   {
/* :08:225 */     return getAll(paramInt1, paramInt2, paramInt3, defaultSort());
/* :09:    */   }
/* :10:    */   
/* :11:    */   public final DbIterator<T> getAll(int paramInt1, int paramInt2, int paramInt3, String paramString)
/* :12:    */   {
/* :13:229 */     checkAvailable(paramInt1);
/* :14:230 */     Connection localConnection = null;
/* :15:    */     try
/* :16:    */     {
/* :17:232 */       localConnection = Db.getConnection();
/* :18:233 */       PreparedStatement localPreparedStatement = localConnection.prepareStatement("SELECT * FROM " + this.table + " AS a WHERE height <= ?" + (this.multiversion ? " AND (latest = TRUE OR (latest = FALSE AND EXISTS (SELECT 1 FROM " + this.table + " AS b WHERE b.height > ? AND " + this.dbKeyFactory.getSelfJoinClause() + ") AND NOT EXISTS (SELECT 1 FROM " + this.table + " AS b WHERE b.height <= ? AND " + this.dbKeyFactory.getSelfJoinClause() + " AND b.height > a.height))) " : " ") + paramString + DbUtils.limitsClause(paramInt2, paramInt3));
/* :19:    */       
/* :20:    */ 
/* :21:    */ 
/* :22:    */ 
/* :23:    */ 
/* :24:239 */       int i = 0;
/* :25:240 */       localPreparedStatement.setInt(++i, paramInt1);
/* :26:241 */       if (this.multiversion)
/* :27:    */       {
/* :28:242 */         localPreparedStatement.setInt(++i, paramInt1);
/* :29:243 */         localPreparedStatement.setInt(++i, paramInt1);
/* :30:    */       }
/* :31:245 */       i++;i = DbUtils.setLimits(i, localPreparedStatement, paramInt2, paramInt3);
/* :32:246 */       return getManyBy(localConnection, localPreparedStatement, false);
/* :33:    */     }
/* :34:    */     catch (SQLException localSQLException)
/* :35:    */     {
/* :36:248 */       DbUtils.close(new AutoCloseable[] { localConnection });
/* :37:249 */       throw new RuntimeException(localSQLException.toString(), localSQLException);
/* :38:    */     }
/* :39:    */   }
/* :40:    */   
/* :41:    */   /* Error */
/* :42:    */   public final int getCount()
/* :43:    */   {
/* :44:    */     // Byte code:
/* :45:    */     //   0: invokestatic 24	nxt/db/Db:getConnection	()Ljava/sql/Connection;
/* :46:    */     //   3: astore_1
/* :47:    */     //   4: aconst_null
/* :48:    */     //   5: astore_2
/* :49:    */     //   6: aload_1
/* :50:    */     //   7: new 5	java/lang/StringBuilder
/* :51:    */     //   10: dup
/* :52:    */     //   11: invokespecial 6	java/lang/StringBuilder:<init>	()V
/* :53:    */     //   14: ldc 89
/* :54:    */     //   16: invokevirtual 8	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* :55:    */     //   19: aload_0
/* :56:    */     //   20: getfield 21	nxt/db/EntityDbTable:table	Ljava/lang/String;
/* :57:    */     //   23: invokevirtual 8	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* :58:    */     //   26: aload_0
/* :59:    */     //   27: getfield 4	nxt/db/EntityDbTable:multiversion	Z
/* :60:    */     //   30: ifeq +8 -> 38
/* :61:    */     //   33: ldc 90
/* :62:    */     //   35: goto +5 -> 40
/* :63:    */     //   38: ldc 28
/* :64:    */     //   40: invokevirtual 8	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* :65:    */     //   43: invokevirtual 11	java/lang/StringBuilder:toString	()Ljava/lang/String;
/* :66:    */     //   46: invokeinterface 29 2 0
/* :67:    */     //   51: astore_3
/* :68:    */     //   52: aconst_null
/* :69:    */     //   53: astore 4
/* :70:    */     //   55: aload_3
/* :71:    */     //   56: invokeinterface 53 1 0
/* :72:    */     //   61: astore 5
/* :73:    */     //   63: aconst_null
/* :74:    */     //   64: astore 6
/* :75:    */     //   66: aload 5
/* :76:    */     //   68: invokeinterface 54 1 0
/* :77:    */     //   73: pop
/* :78:    */     //   74: aload 5
/* :79:    */     //   76: iconst_1
/* :80:    */     //   77: invokeinterface 91 2 0
/* :81:    */     //   82: istore 7
/* :82:    */     //   84: aload 5
/* :83:    */     //   86: ifnull +37 -> 123
/* :84:    */     //   89: aload 6
/* :85:    */     //   91: ifnull +25 -> 116
/* :86:    */     //   94: aload 5
/* :87:    */     //   96: invokeinterface 55 1 0
/* :88:    */     //   101: goto +22 -> 123
/* :89:    */     //   104: astore 8
/* :90:    */     //   106: aload 6
/* :91:    */     //   108: aload 8
/* :92:    */     //   110: invokevirtual 34	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* :93:    */     //   113: goto +10 -> 123
/* :94:    */     //   116: aload 5
/* :95:    */     //   118: invokeinterface 55 1 0
/* :96:    */     //   123: aload_3
/* :97:    */     //   124: ifnull +35 -> 159
/* :98:    */     //   127: aload 4
/* :99:    */     //   129: ifnull +24 -> 153
/* ;00:    */     //   132: aload_3
/* ;01:    */     //   133: invokeinterface 32 1 0
/* ;02:    */     //   138: goto +21 -> 159
/* ;03:    */     //   141: astore 8
/* ;04:    */     //   143: aload 4
/* ;05:    */     //   145: aload 8
/* ;06:    */     //   147: invokevirtual 34	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* ;07:    */     //   150: goto +9 -> 159
/* ;08:    */     //   153: aload_3
/* ;09:    */     //   154: invokeinterface 32 1 0
/* ;10:    */     //   159: aload_1
/* ;11:    */     //   160: ifnull +33 -> 193
/* ;12:    */     //   163: aload_2
/* ;13:    */     //   164: ifnull +23 -> 187
/* ;14:    */     //   167: aload_1
/* ;15:    */     //   168: invokeinterface 35 1 0
/* ;16:    */     //   173: goto +20 -> 193
/* ;17:    */     //   176: astore 8
/* ;18:    */     //   178: aload_2
/* ;19:    */     //   179: aload 8
/* ;20:    */     //   181: invokevirtual 34	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* ;21:    */     //   184: goto +9 -> 193
/* ;22:    */     //   187: aload_1
/* ;23:    */     //   188: invokeinterface 35 1 0
/* ;24:    */     //   193: iload 7
/* ;25:    */     //   195: ireturn
/* ;26:    */     //   196: astore 7
/* ;27:    */     //   198: aload 7
/* ;28:    */     //   200: astore 6
/* ;29:    */     //   202: aload 7
/* ;30:    */     //   204: athrow
/* ;31:    */     //   205: astore 9
/* ;32:    */     //   207: aload 5
/* ;33:    */     //   209: ifnull +37 -> 246
/* ;34:    */     //   212: aload 6
/* ;35:    */     //   214: ifnull +25 -> 239
/* ;36:    */     //   217: aload 5
/* ;37:    */     //   219: invokeinterface 55 1 0
/* ;38:    */     //   224: goto +22 -> 246
/* ;39:    */     //   227: astore 10
/* ;40:    */     //   229: aload 6
/* ;41:    */     //   231: aload 10
/* ;42:    */     //   233: invokevirtual 34	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* ;43:    */     //   236: goto +10 -> 246
/* ;44:    */     //   239: aload 5
/* ;45:    */     //   241: invokeinterface 55 1 0
/* ;46:    */     //   246: aload 9
/* ;47:    */     //   248: athrow
/* ;48:    */     //   249: astore 5
/* ;49:    */     //   251: aload 5
/* ;50:    */     //   253: astore 4
/* ;51:    */     //   255: aload 5
/* ;52:    */     //   257: athrow
/* ;53:    */     //   258: astore 11
/* ;54:    */     //   260: aload_3
/* ;55:    */     //   261: ifnull +35 -> 296
/* ;56:    */     //   264: aload 4
/* ;57:    */     //   266: ifnull +24 -> 290
/* ;58:    */     //   269: aload_3
/* ;59:    */     //   270: invokeinterface 32 1 0
/* ;60:    */     //   275: goto +21 -> 296
/* ;61:    */     //   278: astore 12
/* ;62:    */     //   280: aload 4
/* ;63:    */     //   282: aload 12
/* ;64:    */     //   284: invokevirtual 34	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* ;65:    */     //   287: goto +9 -> 296
/* ;66:    */     //   290: aload_3
/* ;67:    */     //   291: invokeinterface 32 1 0
/* ;68:    */     //   296: aload 11
/* ;69:    */     //   298: athrow
/* ;70:    */     //   299: astore_3
/* ;71:    */     //   300: aload_3
/* ;72:    */     //   301: astore_2
/* ;73:    */     //   302: aload_3
/* ;74:    */     //   303: athrow
/* ;75:    */     //   304: astore 13
/* ;76:    */     //   306: aload_1
/* ;77:    */     //   307: ifnull +33 -> 340
/* ;78:    */     //   310: aload_2
/* ;79:    */     //   311: ifnull +23 -> 334
/* ;80:    */     //   314: aload_1
/* ;81:    */     //   315: invokeinterface 35 1 0
/* ;82:    */     //   320: goto +20 -> 340
/* ;83:    */     //   323: astore 14
/* ;84:    */     //   325: aload_2
/* ;85:    */     //   326: aload 14
/* ;86:    */     //   328: invokevirtual 34	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* ;87:    */     //   331: goto +9 -> 340
/* ;88:    */     //   334: aload_1
/* ;89:    */     //   335: invokeinterface 35 1 0
/* ;90:    */     //   340: aload 13
/* ;91:    */     //   342: athrow
/* ;92:    */     //   343: astore_1
/* ;93:    */     //   344: new 37	java/lang/RuntimeException
/* ;94:    */     //   347: dup
/* ;95:    */     //   348: aload_1
/* ;96:    */     //   349: invokevirtual 38	java/sql/SQLException:toString	()Ljava/lang/String;
/* ;97:    */     //   352: aload_1
/* ;98:    */     //   353: invokespecial 39	java/lang/RuntimeException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
/* ;99:    */     //   356: athrow
/* <00:    */     // Line number table:
/* <01:    */     //   Java source line #254	-> byte code offset #0
/* <02:    */     //   Java source line #255	-> byte code offset #6
/* <03:    */     //   Java source line #254	-> byte code offset #52
/* <04:    */     //   Java source line #257	-> byte code offset #55
/* <05:    */     //   Java source line #254	-> byte code offset #63
/* <06:    */     //   Java source line #258	-> byte code offset #66
/* <07:    */     //   Java source line #259	-> byte code offset #74
/* <08:    */     //   Java source line #260	-> byte code offset #84
/* <09:    */     //   Java source line #254	-> byte code offset #196
/* <10:    */     //   Java source line #260	-> byte code offset #205
/* <11:    */     //   Java source line #254	-> byte code offset #249
/* <12:    */     //   Java source line #260	-> byte code offset #258
/* <13:    */     //   Java source line #254	-> byte code offset #299
/* <14:    */     //   Java source line #260	-> byte code offset #304
/* <15:    */     //   Java source line #261	-> byte code offset #344
/* <16:    */     // Local variable table:
/* <17:    */     //   start	length	slot	name	signature
/* <18:    */     //   0	357	0	this	EntityDbTable
/* <19:    */     //   3	332	1	localConnection	Connection
/* <20:    */     //   343	10	1	localSQLException	SQLException
/* <21:    */     //   5	321	2	localObject1	Object
/* <22:    */     //   51	240	3	localPreparedStatement	PreparedStatement
/* <23:    */     //   299	4	3	localThrowable1	Throwable
/* <24:    */     //   53	228	4	localObject2	Object
/* <25:    */     //   61	179	5	localResultSet	ResultSet
/* <26:    */     //   249	7	5	localThrowable2	Throwable
/* <27:    */     //   64	166	6	localObject3	Object
/* <28:    */     //   82	112	7	i	int
/* <29:    */     //   196	7	7	localThrowable3	Throwable
/* <30:    */     //   104	5	8	localThrowable4	Throwable
/* <31:    */     //   141	5	8	localThrowable5	Throwable
/* <32:    */     //   176	4	8	localThrowable6	Throwable
/* <33:    */     //   205	42	9	localObject4	Object
/* <34:    */     //   227	5	10	localThrowable7	Throwable
/* <35:    */     //   258	39	11	localObject5	Object
/* <36:    */     //   278	5	12	localThrowable8	Throwable
/* <37:    */     //   304	37	13	localObject6	Object
/* <38:    */     //   323	4	14	localThrowable9	Throwable
/* <39:    */     // Exception table:
/* <40:    */     //   from	to	target	type
/* <41:    */     //   94	101	104	java/lang/Throwable
/* <42:    */     //   132	138	141	java/lang/Throwable
/* <43:    */     //   167	173	176	java/lang/Throwable
/* <44:    */     //   66	84	196	java/lang/Throwable
/* <45:    */     //   66	84	205	finally
/* <46:    */     //   196	207	205	finally
/* <47:    */     //   217	224	227	java/lang/Throwable
/* <48:    */     //   55	123	249	java/lang/Throwable
/* <49:    */     //   196	249	249	java/lang/Throwable
/* <50:    */     //   55	123	258	finally
/* <51:    */     //   196	260	258	finally
/* <52:    */     //   269	275	278	java/lang/Throwable
/* <53:    */     //   6	159	299	java/lang/Throwable
/* <54:    */     //   196	299	299	java/lang/Throwable
/* <55:    */     //   6	159	304	finally
/* <56:    */     //   196	306	304	finally
/* <57:    */     //   314	320	323	java/lang/Throwable
/* <58:    */     //   0	193	343	java/sql/SQLException
/* <59:    */     //   196	343	343	java/sql/SQLException
/* <60:    */   }
/* <61:    */   
/* <62:    */   /* Error */
/* <63:    */   public final int getRowCount()
/* <64:    */   {
/* <65:    */     // Byte code:
/* <66:    */     //   0: invokestatic 24	nxt/db/Db:getConnection	()Ljava/sql/Connection;
/* <67:    */     //   3: astore_1
/* <68:    */     //   4: aconst_null
/* <69:    */     //   5: astore_2
/* <70:    */     //   6: aload_1
/* <71:    */     //   7: new 5	java/lang/StringBuilder
/* <72:    */     //   10: dup
/* <73:    */     //   11: invokespecial 6	java/lang/StringBuilder:<init>	()V
/* <74:    */     //   14: ldc 89
/* <75:    */     //   16: invokevirtual 8	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* <76:    */     //   19: aload_0
/* <77:    */     //   20: getfield 21	nxt/db/EntityDbTable:table	Ljava/lang/String;
/* <78:    */     //   23: invokevirtual 8	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* <79:    */     //   26: invokevirtual 11	java/lang/StringBuilder:toString	()Ljava/lang/String;
/* <80:    */     //   29: invokeinterface 29 2 0
/* <81:    */     //   34: astore_3
/* <82:    */     //   35: aconst_null
/* <83:    */     //   36: astore 4
/* <84:    */     //   38: aload_3
/* <85:    */     //   39: invokeinterface 53 1 0
/* <86:    */     //   44: astore 5
/* <87:    */     //   46: aconst_null
/* <88:    */     //   47: astore 6
/* <89:    */     //   49: aload 5
/* <90:    */     //   51: invokeinterface 54 1 0
/* <91:    */     //   56: pop
/* <92:    */     //   57: aload 5
/* <93:    */     //   59: iconst_1
/* <94:    */     //   60: invokeinterface 91 2 0
/* <95:    */     //   65: istore 7
/* <96:    */     //   67: aload 5
/* <97:    */     //   69: ifnull +37 -> 106
/* <98:    */     //   72: aload 6
/* <99:    */     //   74: ifnull +25 -> 99
/* =00:    */     //   77: aload 5
/* =01:    */     //   79: invokeinterface 55 1 0
/* =02:    */     //   84: goto +22 -> 106
/* =03:    */     //   87: astore 8
/* =04:    */     //   89: aload 6
/* =05:    */     //   91: aload 8
/* =06:    */     //   93: invokevirtual 34	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* =07:    */     //   96: goto +10 -> 106
/* =08:    */     //   99: aload 5
/* =09:    */     //   101: invokeinterface 55 1 0
/* =10:    */     //   106: aload_3
/* =11:    */     //   107: ifnull +35 -> 142
/* =12:    */     //   110: aload 4
/* =13:    */     //   112: ifnull +24 -> 136
/* =14:    */     //   115: aload_3
/* =15:    */     //   116: invokeinterface 32 1 0
/* =16:    */     //   121: goto +21 -> 142
/* =17:    */     //   124: astore 8
/* =18:    */     //   126: aload 4
/* =19:    */     //   128: aload 8
/* =20:    */     //   130: invokevirtual 34	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* =21:    */     //   133: goto +9 -> 142
/* =22:    */     //   136: aload_3
/* =23:    */     //   137: invokeinterface 32 1 0
/* =24:    */     //   142: aload_1
/* =25:    */     //   143: ifnull +33 -> 176
/* =26:    */     //   146: aload_2
/* =27:    */     //   147: ifnull +23 -> 170
/* =28:    */     //   150: aload_1
/* =29:    */     //   151: invokeinterface 35 1 0
/* =30:    */     //   156: goto +20 -> 176
/* =31:    */     //   159: astore 8
/* =32:    */     //   161: aload_2
/* =33:    */     //   162: aload 8
/* =34:    */     //   164: invokevirtual 34	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* =35:    */     //   167: goto +9 -> 176
/* =36:    */     //   170: aload_1
/* =37:    */     //   171: invokeinterface 35 1 0
/* =38:    */     //   176: iload 7
/* =39:    */     //   178: ireturn
/* =40:    */     //   179: astore 7
/* =41:    */     //   181: aload 7
/* =42:    */     //   183: astore 6
/* =43:    */     //   185: aload 7
/* =44:    */     //   187: athrow
/* =45:    */     //   188: astore 9
/* =46:    */     //   190: aload 5
/* =47:    */     //   192: ifnull +37 -> 229
/* =48:    */     //   195: aload 6
/* =49:    */     //   197: ifnull +25 -> 222
/* =50:    */     //   200: aload 5
/* =51:    */     //   202: invokeinterface 55 1 0
/* =52:    */     //   207: goto +22 -> 229
/* =53:    */     //   210: astore 10
/* =54:    */     //   212: aload 6
/* =55:    */     //   214: aload 10
/* =56:    */     //   216: invokevirtual 34	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* =57:    */     //   219: goto +10 -> 229
/* =58:    */     //   222: aload 5
/* =59:    */     //   224: invokeinterface 55 1 0
/* =60:    */     //   229: aload 9
/* =61:    */     //   231: athrow
/* =62:    */     //   232: astore 5
/* =63:    */     //   234: aload 5
/* =64:    */     //   236: astore 4
/* =65:    */     //   238: aload 5
/* =66:    */     //   240: athrow
/* =67:    */     //   241: astore 11
/* =68:    */     //   243: aload_3
/* =69:    */     //   244: ifnull +35 -> 279
/* =70:    */     //   247: aload 4
/* =71:    */     //   249: ifnull +24 -> 273
/* =72:    */     //   252: aload_3
/* =73:    */     //   253: invokeinterface 32 1 0
/* =74:    */     //   258: goto +21 -> 279
/* =75:    */     //   261: astore 12
/* =76:    */     //   263: aload 4
/* =77:    */     //   265: aload 12
/* =78:    */     //   267: invokevirtual 34	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* =79:    */     //   270: goto +9 -> 279
/* =80:    */     //   273: aload_3
/* =81:    */     //   274: invokeinterface 32 1 0
/* =82:    */     //   279: aload 11
/* =83:    */     //   281: athrow
/* =84:    */     //   282: astore_3
/* =85:    */     //   283: aload_3
/* =86:    */     //   284: astore_2
/* =87:    */     //   285: aload_3
/* =88:    */     //   286: athrow
/* =89:    */     //   287: astore 13
/* =90:    */     //   289: aload_1
/* =91:    */     //   290: ifnull +33 -> 323
/* =92:    */     //   293: aload_2
/* =93:    */     //   294: ifnull +23 -> 317
/* =94:    */     //   297: aload_1
/* =95:    */     //   298: invokeinterface 35 1 0
/* =96:    */     //   303: goto +20 -> 323
/* =97:    */     //   306: astore 14
/* =98:    */     //   308: aload_2
/* =99:    */     //   309: aload 14
/* >00:    */     //   311: invokevirtual 34	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* >01:    */     //   314: goto +9 -> 323
/* >02:    */     //   317: aload_1
/* >03:    */     //   318: invokeinterface 35 1 0
/* >04:    */     //   323: aload 13
/* >05:    */     //   325: athrow
/* >06:    */     //   326: astore_1
/* >07:    */     //   327: new 37	java/lang/RuntimeException
/* >08:    */     //   330: dup
/* >09:    */     //   331: aload_1
/* >10:    */     //   332: invokevirtual 38	java/sql/SQLException:toString	()Ljava/lang/String;
/* >11:    */     //   335: aload_1
/* >12:    */     //   336: invokespecial 39	java/lang/RuntimeException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
/* >13:    */     //   339: athrow
/* >14:    */     // Line number table:
/* >15:    */     //   Java source line #266	-> byte code offset #0
/* >16:    */     //   Java source line #267	-> byte code offset #6
/* >17:    */     //   Java source line #266	-> byte code offset #35
/* >18:    */     //   Java source line #268	-> byte code offset #38
/* >19:    */     //   Java source line #266	-> byte code offset #46
/* >20:    */     //   Java source line #269	-> byte code offset #49
/* >21:    */     //   Java source line #270	-> byte code offset #57
/* >22:    */     //   Java source line #271	-> byte code offset #67
/* >23:    */     //   Java source line #266	-> byte code offset #179
/* >24:    */     //   Java source line #271	-> byte code offset #188
/* >25:    */     //   Java source line #266	-> byte code offset #232
/* >26:    */     //   Java source line #271	-> byte code offset #241
/* >27:    */     //   Java source line #266	-> byte code offset #282
/* >28:    */     //   Java source line #271	-> byte code offset #287
/* >29:    */     //   Java source line #272	-> byte code offset #327
/* >30:    */     // Local variable table:
/* >31:    */     //   start	length	slot	name	signature
/* >32:    */     //   0	340	0	this	EntityDbTable
/* >33:    */     //   3	315	1	localConnection	Connection
/* >34:    */     //   326	10	1	localSQLException	SQLException
/* >35:    */     //   5	304	2	localObject1	Object
/* >36:    */     //   34	240	3	localPreparedStatement	PreparedStatement
/* >37:    */     //   282	4	3	localThrowable1	Throwable
/* >38:    */     //   36	228	4	localObject2	Object
/* >39:    */     //   44	179	5	localResultSet	ResultSet
/* >40:    */     //   232	7	5	localThrowable2	Throwable
/* >41:    */     //   47	166	6	localObject3	Object
/* >42:    */     //   65	112	7	i	int
/* >43:    */     //   179	7	7	localThrowable3	Throwable
/* >44:    */     //   87	5	8	localThrowable4	Throwable
/* >45:    */     //   124	5	8	localThrowable5	Throwable
/* >46:    */     //   159	4	8	localThrowable6	Throwable
/* >47:    */     //   188	42	9	localObject4	Object
/* >48:    */     //   210	5	10	localThrowable7	Throwable
/* >49:    */     //   241	39	11	localObject5	Object
/* >50:    */     //   261	5	12	localThrowable8	Throwable
/* >51:    */     //   287	37	13	localObject6	Object
/* >52:    */     //   306	4	14	localThrowable9	Throwable
/* >53:    */     // Exception table:
/* >54:    */     //   from	to	target	type
/* >55:    */     //   77	84	87	java/lang/Throwable
/* >56:    */     //   115	121	124	java/lang/Throwable
/* >57:    */     //   150	156	159	java/lang/Throwable
/* >58:    */     //   49	67	179	java/lang/Throwable
/* >59:    */     //   49	67	188	finally
/* >60:    */     //   179	190	188	finally
/* >61:    */     //   200	207	210	java/lang/Throwable
/* >62:    */     //   38	106	232	java/lang/Throwable
/* >63:    */     //   179	232	232	java/lang/Throwable
/* >64:    */     //   38	106	241	finally
/* >65:    */     //   179	243	241	finally
/* >66:    */     //   252	258	261	java/lang/Throwable
/* >67:    */     //   6	142	282	java/lang/Throwable
/* >68:    */     //   179	282	282	java/lang/Throwable
/* >69:    */     //   6	142	287	finally
/* >70:    */     //   179	289	287	finally
/* >71:    */     //   297	303	306	java/lang/Throwable
/* >72:    */     //   0	176	326	java/sql/SQLException
/* >73:    */     //   179	326	326	java/sql/SQLException
/* >74:    */   }
/* >75:    */   
/* >76:    */   public final void insert(T paramT)
/* >77:    */   {
/* >78:277 */     if (!Db.isInTransaction()) {
/* >79:278 */       throw new IllegalStateException("Not in transaction");
/* >80:    */     }
/* >81:280 */     DbKey localDbKey = this.dbKeyFactory.newKey(paramT);
/* >82:281 */     Object localObject1 = Db.getCache(this.table).get(localDbKey);
/* >83:282 */     if (localObject1 == null) {
/* >84:283 */       Db.getCache(this.table).put(localDbKey, paramT);
/* >85:284 */     } else if (paramT != localObject1) {
/* >86:285 */       throw new IllegalStateException("Different instance found in Db cache, perhaps trying to save an object that was read outside the current transaction");
/* >87:    */     }
/* >88:    */     try
/* >89:    */     {
/* >90:288 */       Connection localConnection = Db.getConnection();Object localObject2 = null;
/* >91:    */       try
/* >92:    */       {
/* >93:289 */         if (this.multiversion)
/* >94:    */         {
/* >95:290 */           PreparedStatement localPreparedStatement = localConnection.prepareStatement("UPDATE " + this.table + " SET latest = FALSE " + this.dbKeyFactory.getPKClause() + " AND latest = TRUE LIMIT 1");Object localObject3 = null;
/* >96:    */           try
/* >97:    */           {
/* >98:292 */             localDbKey.setPK(localPreparedStatement);
/* >99:293 */             localPreparedStatement.executeUpdate();
/* ?00:    */           }
/* ?01:    */           catch (Throwable localThrowable4)
/* ?02:    */           {
/* ?03:290 */             localObject3 = localThrowable4;throw localThrowable4;
/* ?04:    */           }
/* ?05:    */           finally {}
/* ?06:    */         }
/* ?07:296 */         save(localConnection, paramT);
/* ?08:    */       }
/* ?09:    */       catch (Throwable localThrowable2)
/* ?10:    */       {
/* ?11:288 */         localObject2 = localThrowable2;throw localThrowable2;
/* ?12:    */       }
/* ?13:    */       finally
/* ?14:    */       {
/* ?15:297 */         if (localConnection != null) {
/* ?16:297 */           if (localObject2 != null) {
/* ?17:    */             try
/* ?18:    */             {
/* ?19:297 */               localConnection.close();
/* ?20:    */             }
/* ?21:    */             catch (Throwable localThrowable6)
/* ?22:    */             {
/* ?23:297 */               ((Throwable)localObject2).addSuppressed(localThrowable6);
/* ?24:    */             }
/* ?25:    */           } else {
/* ?26:297 */             localConnection.close();
/* ?27:    */           }
/* ?28:    */         }
/* ?29:    */       }
/* ?30:    */     }
/* ?31:    */     catch (SQLException localSQLException)
/* ?32:    */     {
/* ?33:298 */       throw new RuntimeException(localSQLException.toString(), localSQLException);
/* ?34:    */     }
/* ?35:    */   }
/* ?36:    */   
/* ?37:    */   public void rollback(int paramInt)
/* ?38:    */   {
/* ?39:304 */     super.rollback(paramInt);
/* ?40:305 */     Db.getCache(this.table).clear();
/* ?41:    */   }
/* ?42:    */   
/* ?43:    */   public final void truncate()
/* ?44:    */   {
/* ?45:310 */     super.truncate();
/* ?46:311 */     Db.getCache(this.table).clear();
/* ?47:    */   }
/* ?48:    */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.db.EntityDbTable
 * JD-Core Version:    0.7.1
 */