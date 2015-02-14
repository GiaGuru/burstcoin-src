/*   1:    */ package nxt;
/*   2:    */ 
/*   3:    */ import java.math.BigInteger;
/*   4:    */ import java.sql.Connection;
/*   5:    */ import java.sql.PreparedStatement;
/*   6:    */ import java.sql.ResultSet;
/*   7:    */ import java.sql.SQLException;
/*   8:    */ import java.sql.Statement;
/*   9:    */ import nxt.db.Db;
/*  10:    */ import nxt.db.DbUtils;
/*  11:    */ import nxt.util.Logger;
/*  12:    */ 
/*  13:    */ final class BlockDb
/*  14:    */ {
/*  15:    */   /* Error */
/*  16:    */   static BlockImpl findBlock(long paramLong)
/*  17:    */   {
/*  18:    */     // Byte code:
/*  19:    */     //   0: invokestatic 2	nxt/db/Db:getConnection	()Ljava/sql/Connection;
/*  20:    */     //   3: astore_2
/*  21:    */     //   4: aconst_null
/*  22:    */     //   5: astore_3
/*  23:    */     //   6: aload_2
/*  24:    */     //   7: ldc 3
/*  25:    */     //   9: invokeinterface 4 2 0
/*  26:    */     //   14: astore 4
/*  27:    */     //   16: aconst_null
/*  28:    */     //   17: astore 5
/*  29:    */     //   19: aload 4
/*  30:    */     //   21: iconst_1
/*  31:    */     //   22: lload_0
/*  32:    */     //   23: invokeinterface 5 4 0
/*  33:    */     //   28: aload 4
/*  34:    */     //   30: invokeinterface 6 1 0
/*  35:    */     //   35: astore 6
/*  36:    */     //   37: aconst_null
/*  37:    */     //   38: astore 7
/*  38:    */     //   40: aconst_null
/*  39:    */     //   41: astore 8
/*  40:    */     //   43: aload 6
/*  41:    */     //   45: invokeinterface 7 1 0
/*  42:    */     //   50: ifeq +11 -> 61
/*  43:    */     //   53: aload_2
/*  44:    */     //   54: aload 6
/*  45:    */     //   56: invokestatic 8	nxt/BlockDb:loadBlock	(Ljava/sql/Connection;Ljava/sql/ResultSet;)Lnxt/BlockImpl;
/*  46:    */     //   59: astore 8
/*  47:    */     //   61: aload 8
/*  48:    */     //   63: astore 9
/*  49:    */     //   65: aload 6
/*  50:    */     //   67: ifnull +37 -> 104
/*  51:    */     //   70: aload 7
/*  52:    */     //   72: ifnull +25 -> 97
/*  53:    */     //   75: aload 6
/*  54:    */     //   77: invokeinterface 9 1 0
/*  55:    */     //   82: goto +22 -> 104
/*  56:    */     //   85: astore 10
/*  57:    */     //   87: aload 7
/*  58:    */     //   89: aload 10
/*  59:    */     //   91: invokevirtual 11	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/*  60:    */     //   94: goto +10 -> 104
/*  61:    */     //   97: aload 6
/*  62:    */     //   99: invokeinterface 9 1 0
/*  63:    */     //   104: aload 4
/*  64:    */     //   106: ifnull +37 -> 143
/*  65:    */     //   109: aload 5
/*  66:    */     //   111: ifnull +25 -> 136
/*  67:    */     //   114: aload 4
/*  68:    */     //   116: invokeinterface 12 1 0
/*  69:    */     //   121: goto +22 -> 143
/*  70:    */     //   124: astore 10
/*  71:    */     //   126: aload 5
/*  72:    */     //   128: aload 10
/*  73:    */     //   130: invokevirtual 11	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/*  74:    */     //   133: goto +10 -> 143
/*  75:    */     //   136: aload 4
/*  76:    */     //   138: invokeinterface 12 1 0
/*  77:    */     //   143: aload_2
/*  78:    */     //   144: ifnull +33 -> 177
/*  79:    */     //   147: aload_3
/*  80:    */     //   148: ifnull +23 -> 171
/*  81:    */     //   151: aload_2
/*  82:    */     //   152: invokeinterface 13 1 0
/*  83:    */     //   157: goto +20 -> 177
/*  84:    */     //   160: astore 10
/*  85:    */     //   162: aload_3
/*  86:    */     //   163: aload 10
/*  87:    */     //   165: invokevirtual 11	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/*  88:    */     //   168: goto +9 -> 177
/*  89:    */     //   171: aload_2
/*  90:    */     //   172: invokeinterface 13 1 0
/*  91:    */     //   177: aload 9
/*  92:    */     //   179: areturn
/*  93:    */     //   180: astore 8
/*  94:    */     //   182: aload 8
/*  95:    */     //   184: astore 7
/*  96:    */     //   186: aload 8
/*  97:    */     //   188: athrow
/*  98:    */     //   189: astore 11
/*  99:    */     //   191: aload 6
/* 100:    */     //   193: ifnull +37 -> 230
/* 101:    */     //   196: aload 7
/* 102:    */     //   198: ifnull +25 -> 223
/* 103:    */     //   201: aload 6
/* 104:    */     //   203: invokeinterface 9 1 0
/* 105:    */     //   208: goto +22 -> 230
/* 106:    */     //   211: astore 12
/* 107:    */     //   213: aload 7
/* 108:    */     //   215: aload 12
/* 109:    */     //   217: invokevirtual 11	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 110:    */     //   220: goto +10 -> 230
/* 111:    */     //   223: aload 6
/* 112:    */     //   225: invokeinterface 9 1 0
/* 113:    */     //   230: aload 11
/* 114:    */     //   232: athrow
/* 115:    */     //   233: astore 6
/* 116:    */     //   235: aload 6
/* 117:    */     //   237: astore 5
/* 118:    */     //   239: aload 6
/* 119:    */     //   241: athrow
/* 120:    */     //   242: astore 13
/* 121:    */     //   244: aload 4
/* 122:    */     //   246: ifnull +37 -> 283
/* 123:    */     //   249: aload 5
/* 124:    */     //   251: ifnull +25 -> 276
/* 125:    */     //   254: aload 4
/* 126:    */     //   256: invokeinterface 12 1 0
/* 127:    */     //   261: goto +22 -> 283
/* 128:    */     //   264: astore 14
/* 129:    */     //   266: aload 5
/* 130:    */     //   268: aload 14
/* 131:    */     //   270: invokevirtual 11	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 132:    */     //   273: goto +10 -> 283
/* 133:    */     //   276: aload 4
/* 134:    */     //   278: invokeinterface 12 1 0
/* 135:    */     //   283: aload 13
/* 136:    */     //   285: athrow
/* 137:    */     //   286: astore 4
/* 138:    */     //   288: aload 4
/* 139:    */     //   290: astore_3
/* 140:    */     //   291: aload 4
/* 141:    */     //   293: athrow
/* 142:    */     //   294: astore 15
/* 143:    */     //   296: aload_2
/* 144:    */     //   297: ifnull +33 -> 330
/* 145:    */     //   300: aload_3
/* 146:    */     //   301: ifnull +23 -> 324
/* 147:    */     //   304: aload_2
/* 148:    */     //   305: invokeinterface 13 1 0
/* 149:    */     //   310: goto +20 -> 330
/* 150:    */     //   313: astore 16
/* 151:    */     //   315: aload_3
/* 152:    */     //   316: aload 16
/* 153:    */     //   318: invokevirtual 11	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 154:    */     //   321: goto +9 -> 330
/* 155:    */     //   324: aload_2
/* 156:    */     //   325: invokeinterface 13 1 0
/* 157:    */     //   330: aload 15
/* 158:    */     //   332: athrow
/* 159:    */     //   333: astore_2
/* 160:    */     //   334: new 15	java/lang/RuntimeException
/* 161:    */     //   337: dup
/* 162:    */     //   338: aload_2
/* 163:    */     //   339: invokevirtual 16	java/sql/SQLException:toString	()Ljava/lang/String;
/* 164:    */     //   342: aload_2
/* 165:    */     //   343: invokespecial 17	java/lang/RuntimeException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
/* 166:    */     //   346: athrow
/* 167:    */     //   347: astore_2
/* 168:    */     //   348: new 15	java/lang/RuntimeException
/* 169:    */     //   351: dup
/* 170:    */     //   352: new 19	java/lang/StringBuilder
/* 171:    */     //   355: dup
/* 172:    */     //   356: invokespecial 20	java/lang/StringBuilder:<init>	()V
/* 173:    */     //   359: ldc 21
/* 174:    */     //   361: invokevirtual 22	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 175:    */     //   364: lload_0
/* 176:    */     //   365: invokevirtual 23	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
/* 177:    */     //   368: ldc 24
/* 178:    */     //   370: invokevirtual 22	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 179:    */     //   373: invokevirtual 25	java/lang/StringBuilder:toString	()Ljava/lang/String;
/* 180:    */     //   376: aload_2
/* 181:    */     //   377: invokespecial 17	java/lang/RuntimeException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
/* 182:    */     //   380: athrow
/* 183:    */     // Line number table:
/* 184:    */     //   Java source line #18	-> byte code offset #0
/* 185:    */     //   Java source line #19	-> byte code offset #6
/* 186:    */     //   Java source line #18	-> byte code offset #16
/* 187:    */     //   Java source line #20	-> byte code offset #19
/* 188:    */     //   Java source line #21	-> byte code offset #28
/* 189:    */     //   Java source line #22	-> byte code offset #40
/* 190:    */     //   Java source line #23	-> byte code offset #43
/* 191:    */     //   Java source line #24	-> byte code offset #53
/* 192:    */     //   Java source line #26	-> byte code offset #61
/* 193:    */     //   Java source line #27	-> byte code offset #65
/* 194:    */     //   Java source line #28	-> byte code offset #104
/* 195:    */     //   Java source line #21	-> byte code offset #180
/* 196:    */     //   Java source line #27	-> byte code offset #189
/* 197:    */     //   Java source line #18	-> byte code offset #233
/* 198:    */     //   Java source line #28	-> byte code offset #242
/* 199:    */     //   Java source line #18	-> byte code offset #286
/* 200:    */     //   Java source line #28	-> byte code offset #294
/* 201:    */     //   Java source line #29	-> byte code offset #334
/* 202:    */     //   Java source line #30	-> byte code offset #347
/* 203:    */     //   Java source line #31	-> byte code offset #348
/* 204:    */     // Local variable table:
/* 205:    */     //   start	length	slot	name	signature
/* 206:    */     //   0	381	0	paramLong	long
/* 207:    */     //   3	322	2	localConnection	Connection
/* 208:    */     //   333	10	2	localSQLException	SQLException
/* 209:    */     //   347	30	2	localValidationException	NxtException.ValidationException
/* 210:    */     //   5	311	3	localObject1	Object
/* 211:    */     //   14	263	4	localPreparedStatement	PreparedStatement
/* 212:    */     //   286	6	4	localThrowable1	Throwable
/* 213:    */     //   17	250	5	localObject2	Object
/* 214:    */     //   35	189	6	localResultSet	ResultSet
/* 215:    */     //   233	7	6	localThrowable2	Throwable
/* 216:    */     //   38	176	7	localObject3	Object
/* 217:    */     //   41	21	8	localBlockImpl1	BlockImpl
/* 218:    */     //   180	7	8	localThrowable3	Throwable
/* 219:    */     //   63	115	9	localBlockImpl2	BlockImpl
/* 220:    */     //   85	5	10	localThrowable4	Throwable
/* 221:    */     //   124	5	10	localThrowable5	Throwable
/* 222:    */     //   160	4	10	localThrowable6	Throwable
/* 223:    */     //   189	42	11	localObject4	Object
/* 224:    */     //   211	5	12	localThrowable7	Throwable
/* 225:    */     //   242	42	13	localObject5	Object
/* 226:    */     //   264	5	14	localThrowable8	Throwable
/* 227:    */     //   294	37	15	localObject6	Object
/* 228:    */     //   313	4	16	localThrowable9	Throwable
/* 229:    */     // Exception table:
/* 230:    */     //   from	to	target	type
/* 231:    */     //   75	82	85	java/lang/Throwable
/* 232:    */     //   114	121	124	java/lang/Throwable
/* 233:    */     //   151	157	160	java/lang/Throwable
/* 234:    */     //   40	65	180	java/lang/Throwable
/* 235:    */     //   40	65	189	finally
/* 236:    */     //   180	191	189	finally
/* 237:    */     //   201	208	211	java/lang/Throwable
/* 238:    */     //   19	104	233	java/lang/Throwable
/* 239:    */     //   180	233	233	java/lang/Throwable
/* 240:    */     //   19	104	242	finally
/* 241:    */     //   180	244	242	finally
/* 242:    */     //   254	261	264	java/lang/Throwable
/* 243:    */     //   6	143	286	java/lang/Throwable
/* 244:    */     //   180	286	286	java/lang/Throwable
/* 245:    */     //   6	143	294	finally
/* 246:    */     //   180	296	294	finally
/* 247:    */     //   304	310	313	java/lang/Throwable
/* 248:    */     //   0	177	333	java/sql/SQLException
/* 249:    */     //   180	333	333	java/sql/SQLException
/* 250:    */     //   0	177	347	nxt/NxtException$ValidationException
/* 251:    */     //   180	333	347	nxt/NxtException$ValidationException
/* 252:    */   }
/* 253:    */   
/* 254:    */   /* Error */
/* 255:    */   static boolean hasBlock(long paramLong)
/* 256:    */   {
/* 257:    */     // Byte code:
/* 258:    */     //   0: invokestatic 2	nxt/db/Db:getConnection	()Ljava/sql/Connection;
/* 259:    */     //   3: astore_2
/* 260:    */     //   4: aconst_null
/* 261:    */     //   5: astore_3
/* 262:    */     //   6: aload_2
/* 263:    */     //   7: ldc 26
/* 264:    */     //   9: invokeinterface 4 2 0
/* 265:    */     //   14: astore 4
/* 266:    */     //   16: aconst_null
/* 267:    */     //   17: astore 5
/* 268:    */     //   19: aload 4
/* 269:    */     //   21: iconst_1
/* 270:    */     //   22: lload_0
/* 271:    */     //   23: invokeinterface 5 4 0
/* 272:    */     //   28: aload 4
/* 273:    */     //   30: invokeinterface 6 1 0
/* 274:    */     //   35: astore 6
/* 275:    */     //   37: aconst_null
/* 276:    */     //   38: astore 7
/* 277:    */     //   40: aload 6
/* 278:    */     //   42: invokeinterface 7 1 0
/* 279:    */     //   47: istore 8
/* 280:    */     //   49: aload 6
/* 281:    */     //   51: ifnull +37 -> 88
/* 282:    */     //   54: aload 7
/* 283:    */     //   56: ifnull +25 -> 81
/* 284:    */     //   59: aload 6
/* 285:    */     //   61: invokeinterface 9 1 0
/* 286:    */     //   66: goto +22 -> 88
/* 287:    */     //   69: astore 9
/* 288:    */     //   71: aload 7
/* 289:    */     //   73: aload 9
/* 290:    */     //   75: invokevirtual 11	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 291:    */     //   78: goto +10 -> 88
/* 292:    */     //   81: aload 6
/* 293:    */     //   83: invokeinterface 9 1 0
/* 294:    */     //   88: aload 4
/* 295:    */     //   90: ifnull +37 -> 127
/* 296:    */     //   93: aload 5
/* 297:    */     //   95: ifnull +25 -> 120
/* 298:    */     //   98: aload 4
/* 299:    */     //   100: invokeinterface 12 1 0
/* 300:    */     //   105: goto +22 -> 127
/* 301:    */     //   108: astore 9
/* 302:    */     //   110: aload 5
/* 303:    */     //   112: aload 9
/* 304:    */     //   114: invokevirtual 11	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 305:    */     //   117: goto +10 -> 127
/* 306:    */     //   120: aload 4
/* 307:    */     //   122: invokeinterface 12 1 0
/* 308:    */     //   127: aload_2
/* 309:    */     //   128: ifnull +33 -> 161
/* 310:    */     //   131: aload_3
/* 311:    */     //   132: ifnull +23 -> 155
/* 312:    */     //   135: aload_2
/* 313:    */     //   136: invokeinterface 13 1 0
/* 314:    */     //   141: goto +20 -> 161
/* 315:    */     //   144: astore 9
/* 316:    */     //   146: aload_3
/* 317:    */     //   147: aload 9
/* 318:    */     //   149: invokevirtual 11	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 319:    */     //   152: goto +9 -> 161
/* 320:    */     //   155: aload_2
/* 321:    */     //   156: invokeinterface 13 1 0
/* 322:    */     //   161: iload 8
/* 323:    */     //   163: ireturn
/* 324:    */     //   164: astore 8
/* 325:    */     //   166: aload 8
/* 326:    */     //   168: astore 7
/* 327:    */     //   170: aload 8
/* 328:    */     //   172: athrow
/* 329:    */     //   173: astore 10
/* 330:    */     //   175: aload 6
/* 331:    */     //   177: ifnull +37 -> 214
/* 332:    */     //   180: aload 7
/* 333:    */     //   182: ifnull +25 -> 207
/* 334:    */     //   185: aload 6
/* 335:    */     //   187: invokeinterface 9 1 0
/* 336:    */     //   192: goto +22 -> 214
/* 337:    */     //   195: astore 11
/* 338:    */     //   197: aload 7
/* 339:    */     //   199: aload 11
/* 340:    */     //   201: invokevirtual 11	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 341:    */     //   204: goto +10 -> 214
/* 342:    */     //   207: aload 6
/* 343:    */     //   209: invokeinterface 9 1 0
/* 344:    */     //   214: aload 10
/* 345:    */     //   216: athrow
/* 346:    */     //   217: astore 6
/* 347:    */     //   219: aload 6
/* 348:    */     //   221: astore 5
/* 349:    */     //   223: aload 6
/* 350:    */     //   225: athrow
/* 351:    */     //   226: astore 12
/* 352:    */     //   228: aload 4
/* 353:    */     //   230: ifnull +37 -> 267
/* 354:    */     //   233: aload 5
/* 355:    */     //   235: ifnull +25 -> 260
/* 356:    */     //   238: aload 4
/* 357:    */     //   240: invokeinterface 12 1 0
/* 358:    */     //   245: goto +22 -> 267
/* 359:    */     //   248: astore 13
/* 360:    */     //   250: aload 5
/* 361:    */     //   252: aload 13
/* 362:    */     //   254: invokevirtual 11	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 363:    */     //   257: goto +10 -> 267
/* 364:    */     //   260: aload 4
/* 365:    */     //   262: invokeinterface 12 1 0
/* 366:    */     //   267: aload 12
/* 367:    */     //   269: athrow
/* 368:    */     //   270: astore 4
/* 369:    */     //   272: aload 4
/* 370:    */     //   274: astore_3
/* 371:    */     //   275: aload 4
/* 372:    */     //   277: athrow
/* 373:    */     //   278: astore 14
/* 374:    */     //   280: aload_2
/* 375:    */     //   281: ifnull +33 -> 314
/* 376:    */     //   284: aload_3
/* 377:    */     //   285: ifnull +23 -> 308
/* 378:    */     //   288: aload_2
/* 379:    */     //   289: invokeinterface 13 1 0
/* 380:    */     //   294: goto +20 -> 314
/* 381:    */     //   297: astore 15
/* 382:    */     //   299: aload_3
/* 383:    */     //   300: aload 15
/* 384:    */     //   302: invokevirtual 11	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 385:    */     //   305: goto +9 -> 314
/* 386:    */     //   308: aload_2
/* 387:    */     //   309: invokeinterface 13 1 0
/* 388:    */     //   314: aload 14
/* 389:    */     //   316: athrow
/* 390:    */     //   317: astore_2
/* 391:    */     //   318: new 15	java/lang/RuntimeException
/* 392:    */     //   321: dup
/* 393:    */     //   322: aload_2
/* 394:    */     //   323: invokevirtual 16	java/sql/SQLException:toString	()Ljava/lang/String;
/* 395:    */     //   326: aload_2
/* 396:    */     //   327: invokespecial 17	java/lang/RuntimeException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
/* 397:    */     //   330: athrow
/* 398:    */     // Line number table:
/* 399:    */     //   Java source line #36	-> byte code offset #0
/* 400:    */     //   Java source line #37	-> byte code offset #6
/* 401:    */     //   Java source line #36	-> byte code offset #16
/* 402:    */     //   Java source line #38	-> byte code offset #19
/* 403:    */     //   Java source line #39	-> byte code offset #28
/* 404:    */     //   Java source line #40	-> byte code offset #40
/* 405:    */     //   Java source line #41	-> byte code offset #49
/* 406:    */     //   Java source line #42	-> byte code offset #88
/* 407:    */     //   Java source line #39	-> byte code offset #164
/* 408:    */     //   Java source line #41	-> byte code offset #173
/* 409:    */     //   Java source line #36	-> byte code offset #217
/* 410:    */     //   Java source line #42	-> byte code offset #226
/* 411:    */     //   Java source line #36	-> byte code offset #270
/* 412:    */     //   Java source line #42	-> byte code offset #278
/* 413:    */     //   Java source line #43	-> byte code offset #318
/* 414:    */     // Local variable table:
/* 415:    */     //   start	length	slot	name	signature
/* 416:    */     //   0	331	0	paramLong	long
/* 417:    */     //   3	306	2	localConnection	Connection
/* 418:    */     //   317	10	2	localSQLException	SQLException
/* 419:    */     //   5	295	3	localObject1	Object
/* 420:    */     //   14	247	4	localPreparedStatement	PreparedStatement
/* 421:    */     //   270	6	4	localThrowable1	Throwable
/* 422:    */     //   17	234	5	localObject2	Object
/* 423:    */     //   35	173	6	localResultSet	ResultSet
/* 424:    */     //   217	7	6	localThrowable2	Throwable
/* 425:    */     //   38	160	7	localObject3	Object
/* 426:    */     //   47	115	8	bool	boolean
/* 427:    */     //   164	7	8	localThrowable3	Throwable
/* 428:    */     //   69	5	9	localThrowable4	Throwable
/* 429:    */     //   108	5	9	localThrowable5	Throwable
/* 430:    */     //   144	4	9	localThrowable6	Throwable
/* 431:    */     //   173	42	10	localObject4	Object
/* 432:    */     //   195	5	11	localThrowable7	Throwable
/* 433:    */     //   226	42	12	localObject5	Object
/* 434:    */     //   248	5	13	localThrowable8	Throwable
/* 435:    */     //   278	37	14	localObject6	Object
/* 436:    */     //   297	4	15	localThrowable9	Throwable
/* 437:    */     // Exception table:
/* 438:    */     //   from	to	target	type
/* 439:    */     //   59	66	69	java/lang/Throwable
/* 440:    */     //   98	105	108	java/lang/Throwable
/* 441:    */     //   135	141	144	java/lang/Throwable
/* 442:    */     //   40	49	164	java/lang/Throwable
/* 443:    */     //   40	49	173	finally
/* 444:    */     //   164	175	173	finally
/* 445:    */     //   185	192	195	java/lang/Throwable
/* 446:    */     //   19	88	217	java/lang/Throwable
/* 447:    */     //   164	217	217	java/lang/Throwable
/* 448:    */     //   19	88	226	finally
/* 449:    */     //   164	228	226	finally
/* 450:    */     //   238	245	248	java/lang/Throwable
/* 451:    */     //   6	127	270	java/lang/Throwable
/* 452:    */     //   164	270	270	java/lang/Throwable
/* 453:    */     //   6	127	278	finally
/* 454:    */     //   164	280	278	finally
/* 455:    */     //   288	294	297	java/lang/Throwable
/* 456:    */     //   0	161	317	java/sql/SQLException
/* 457:    */     //   164	317	317	java/sql/SQLException
/* 458:    */   }
/* 459:    */   
/* 460:    */   /* Error */
/* 461:    */   static long findBlockIdAtHeight(int paramInt)
/* 462:    */   {
/* 463:    */     // Byte code:
/* 464:    */     //   0: invokestatic 2	nxt/db/Db:getConnection	()Ljava/sql/Connection;
/* 465:    */     //   3: astore_1
/* 466:    */     //   4: aconst_null
/* 467:    */     //   5: astore_2
/* 468:    */     //   6: aload_1
/* 469:    */     //   7: ldc 27
/* 470:    */     //   9: invokeinterface 4 2 0
/* 471:    */     //   14: astore_3
/* 472:    */     //   15: aconst_null
/* 473:    */     //   16: astore 4
/* 474:    */     //   18: aload_3
/* 475:    */     //   19: iconst_1
/* 476:    */     //   20: iload_0
/* 477:    */     //   21: invokeinterface 28 3 0
/* 478:    */     //   26: aload_3
/* 479:    */     //   27: invokeinterface 6 1 0
/* 480:    */     //   32: astore 5
/* 481:    */     //   34: aconst_null
/* 482:    */     //   35: astore 6
/* 483:    */     //   37: aload 5
/* 484:    */     //   39: invokeinterface 7 1 0
/* 485:    */     //   44: ifne +35 -> 79
/* 486:    */     //   47: new 15	java/lang/RuntimeException
/* 487:    */     //   50: dup
/* 488:    */     //   51: new 19	java/lang/StringBuilder
/* 489:    */     //   54: dup
/* 490:    */     //   55: invokespecial 20	java/lang/StringBuilder:<init>	()V
/* 491:    */     //   58: ldc 29
/* 492:    */     //   60: invokevirtual 22	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 493:    */     //   63: iload_0
/* 494:    */     //   64: invokevirtual 30	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
/* 495:    */     //   67: ldc 31
/* 496:    */     //   69: invokevirtual 22	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 497:    */     //   72: invokevirtual 25	java/lang/StringBuilder:toString	()Ljava/lang/String;
/* 498:    */     //   75: invokespecial 32	java/lang/RuntimeException:<init>	(Ljava/lang/String;)V
/* 499:    */     //   78: athrow
/* 500:    */     //   79: aload 5
/* 501:    */     //   81: ldc 33
/* 502:    */     //   83: invokeinterface 34 2 0
/* 503:    */     //   88: lstore 7
/* 504:    */     //   90: aload 5
/* 505:    */     //   92: ifnull +37 -> 129
/* 506:    */     //   95: aload 6
/* 507:    */     //   97: ifnull +25 -> 122
/* 508:    */     //   100: aload 5
/* 509:    */     //   102: invokeinterface 9 1 0
/* 510:    */     //   107: goto +22 -> 129
/* 511:    */     //   110: astore 9
/* 512:    */     //   112: aload 6
/* 513:    */     //   114: aload 9
/* 514:    */     //   116: invokevirtual 11	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 515:    */     //   119: goto +10 -> 129
/* 516:    */     //   122: aload 5
/* 517:    */     //   124: invokeinterface 9 1 0
/* 518:    */     //   129: aload_3
/* 519:    */     //   130: ifnull +35 -> 165
/* 520:    */     //   133: aload 4
/* 521:    */     //   135: ifnull +24 -> 159
/* 522:    */     //   138: aload_3
/* 523:    */     //   139: invokeinterface 12 1 0
/* 524:    */     //   144: goto +21 -> 165
/* 525:    */     //   147: astore 9
/* 526:    */     //   149: aload 4
/* 527:    */     //   151: aload 9
/* 528:    */     //   153: invokevirtual 11	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 529:    */     //   156: goto +9 -> 165
/* 530:    */     //   159: aload_3
/* 531:    */     //   160: invokeinterface 12 1 0
/* 532:    */     //   165: aload_1
/* 533:    */     //   166: ifnull +33 -> 199
/* 534:    */     //   169: aload_2
/* 535:    */     //   170: ifnull +23 -> 193
/* 536:    */     //   173: aload_1
/* 537:    */     //   174: invokeinterface 13 1 0
/* 538:    */     //   179: goto +20 -> 199
/* 539:    */     //   182: astore 9
/* 540:    */     //   184: aload_2
/* 541:    */     //   185: aload 9
/* 542:    */     //   187: invokevirtual 11	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 543:    */     //   190: goto +9 -> 199
/* 544:    */     //   193: aload_1
/* 545:    */     //   194: invokeinterface 13 1 0
/* 546:    */     //   199: lload 7
/* 547:    */     //   201: lreturn
/* 548:    */     //   202: astore 7
/* 549:    */     //   204: aload 7
/* 550:    */     //   206: astore 6
/* 551:    */     //   208: aload 7
/* 552:    */     //   210: athrow
/* 553:    */     //   211: astore 10
/* 554:    */     //   213: aload 5
/* 555:    */     //   215: ifnull +37 -> 252
/* 556:    */     //   218: aload 6
/* 557:    */     //   220: ifnull +25 -> 245
/* 558:    */     //   223: aload 5
/* 559:    */     //   225: invokeinterface 9 1 0
/* 560:    */     //   230: goto +22 -> 252
/* 561:    */     //   233: astore 11
/* 562:    */     //   235: aload 6
/* 563:    */     //   237: aload 11
/* 564:    */     //   239: invokevirtual 11	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 565:    */     //   242: goto +10 -> 252
/* 566:    */     //   245: aload 5
/* 567:    */     //   247: invokeinterface 9 1 0
/* 568:    */     //   252: aload 10
/* 569:    */     //   254: athrow
/* 570:    */     //   255: astore 5
/* 571:    */     //   257: aload 5
/* 572:    */     //   259: astore 4
/* 573:    */     //   261: aload 5
/* 574:    */     //   263: athrow
/* 575:    */     //   264: astore 12
/* 576:    */     //   266: aload_3
/* 577:    */     //   267: ifnull +35 -> 302
/* 578:    */     //   270: aload 4
/* 579:    */     //   272: ifnull +24 -> 296
/* 580:    */     //   275: aload_3
/* 581:    */     //   276: invokeinterface 12 1 0
/* 582:    */     //   281: goto +21 -> 302
/* 583:    */     //   284: astore 13
/* 584:    */     //   286: aload 4
/* 585:    */     //   288: aload 13
/* 586:    */     //   290: invokevirtual 11	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 587:    */     //   293: goto +9 -> 302
/* 588:    */     //   296: aload_3
/* 589:    */     //   297: invokeinterface 12 1 0
/* 590:    */     //   302: aload 12
/* 591:    */     //   304: athrow
/* 592:    */     //   305: astore_3
/* 593:    */     //   306: aload_3
/* 594:    */     //   307: astore_2
/* 595:    */     //   308: aload_3
/* 596:    */     //   309: athrow
/* 597:    */     //   310: astore 14
/* 598:    */     //   312: aload_1
/* 599:    */     //   313: ifnull +33 -> 346
/* 600:    */     //   316: aload_2
/* 601:    */     //   317: ifnull +23 -> 340
/* 602:    */     //   320: aload_1
/* 603:    */     //   321: invokeinterface 13 1 0
/* 604:    */     //   326: goto +20 -> 346
/* 605:    */     //   329: astore 15
/* 606:    */     //   331: aload_2
/* 607:    */     //   332: aload 15
/* 608:    */     //   334: invokevirtual 11	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 609:    */     //   337: goto +9 -> 346
/* 610:    */     //   340: aload_1
/* 611:    */     //   341: invokeinterface 13 1 0
/* 612:    */     //   346: aload 14
/* 613:    */     //   348: athrow
/* 614:    */     //   349: astore_1
/* 615:    */     //   350: new 15	java/lang/RuntimeException
/* 616:    */     //   353: dup
/* 617:    */     //   354: aload_1
/* 618:    */     //   355: invokevirtual 16	java/sql/SQLException:toString	()Ljava/lang/String;
/* 619:    */     //   358: aload_1
/* 620:    */     //   359: invokespecial 17	java/lang/RuntimeException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
/* 621:    */     //   362: athrow
/* 622:    */     // Line number table:
/* 623:    */     //   Java source line #48	-> byte code offset #0
/* 624:    */     //   Java source line #49	-> byte code offset #6
/* 625:    */     //   Java source line #48	-> byte code offset #15
/* 626:    */     //   Java source line #50	-> byte code offset #18
/* 627:    */     //   Java source line #51	-> byte code offset #26
/* 628:    */     //   Java source line #52	-> byte code offset #37
/* 629:    */     //   Java source line #53	-> byte code offset #47
/* 630:    */     //   Java source line #55	-> byte code offset #79
/* 631:    */     //   Java source line #56	-> byte code offset #90
/* 632:    */     //   Java source line #57	-> byte code offset #129
/* 633:    */     //   Java source line #51	-> byte code offset #202
/* 634:    */     //   Java source line #56	-> byte code offset #211
/* 635:    */     //   Java source line #48	-> byte code offset #255
/* 636:    */     //   Java source line #57	-> byte code offset #264
/* 637:    */     //   Java source line #48	-> byte code offset #305
/* 638:    */     //   Java source line #57	-> byte code offset #310
/* 639:    */     //   Java source line #58	-> byte code offset #350
/* 640:    */     // Local variable table:
/* 641:    */     //   start	length	slot	name	signature
/* 642:    */     //   0	363	0	paramInt	int
/* 643:    */     //   3	338	1	localConnection	Connection
/* 644:    */     //   349	10	1	localSQLException	SQLException
/* 645:    */     //   5	327	2	localObject1	Object
/* 646:    */     //   14	283	3	localPreparedStatement	PreparedStatement
/* 647:    */     //   305	4	3	localThrowable1	Throwable
/* 648:    */     //   16	271	4	localObject2	Object
/* 649:    */     //   32	214	5	localResultSet	ResultSet
/* 650:    */     //   255	7	5	localThrowable2	Throwable
/* 651:    */     //   35	201	6	localObject3	Object
/* 652:    */     //   88	112	7	l	long
/* 653:    */     //   202	7	7	localThrowable3	Throwable
/* 654:    */     //   110	5	9	localThrowable4	Throwable
/* 655:    */     //   147	5	9	localThrowable5	Throwable
/* 656:    */     //   182	4	9	localThrowable6	Throwable
/* 657:    */     //   211	42	10	localObject4	Object
/* 658:    */     //   233	5	11	localThrowable7	Throwable
/* 659:    */     //   264	39	12	localObject5	Object
/* 660:    */     //   284	5	13	localThrowable8	Throwable
/* 661:    */     //   310	37	14	localObject6	Object
/* 662:    */     //   329	4	15	localThrowable9	Throwable
/* 663:    */     // Exception table:
/* 664:    */     //   from	to	target	type
/* 665:    */     //   100	107	110	java/lang/Throwable
/* 666:    */     //   138	144	147	java/lang/Throwable
/* 667:    */     //   173	179	182	java/lang/Throwable
/* 668:    */     //   37	90	202	java/lang/Throwable
/* 669:    */     //   37	90	211	finally
/* 670:    */     //   202	213	211	finally
/* 671:    */     //   223	230	233	java/lang/Throwable
/* 672:    */     //   18	129	255	java/lang/Throwable
/* 673:    */     //   202	255	255	java/lang/Throwable
/* 674:    */     //   18	129	264	finally
/* 675:    */     //   202	266	264	finally
/* 676:    */     //   275	281	284	java/lang/Throwable
/* 677:    */     //   6	165	305	java/lang/Throwable
/* 678:    */     //   202	305	305	java/lang/Throwable
/* 679:    */     //   6	165	310	finally
/* 680:    */     //   202	312	310	finally
/* 681:    */     //   320	326	329	java/lang/Throwable
/* 682:    */     //   0	199	349	java/sql/SQLException
/* 683:    */     //   202	349	349	java/sql/SQLException
/* 684:    */   }
/* 685:    */   
/* 686:    */   /* Error */
/* 687:    */   static BlockImpl findBlockAtHeight(int paramInt)
/* 688:    */   {
/* 689:    */     // Byte code:
/* 690:    */     //   0: invokestatic 2	nxt/db/Db:getConnection	()Ljava/sql/Connection;
/* 691:    */     //   3: astore_1
/* 692:    */     //   4: aconst_null
/* 693:    */     //   5: astore_2
/* 694:    */     //   6: aload_1
/* 695:    */     //   7: ldc 35
/* 696:    */     //   9: invokeinterface 4 2 0
/* 697:    */     //   14: astore_3
/* 698:    */     //   15: aconst_null
/* 699:    */     //   16: astore 4
/* 700:    */     //   18: aload_3
/* 701:    */     //   19: iconst_1
/* 702:    */     //   20: iload_0
/* 703:    */     //   21: invokeinterface 28 3 0
/* 704:    */     //   26: aload_3
/* 705:    */     //   27: invokeinterface 6 1 0
/* 706:    */     //   32: astore 5
/* 707:    */     //   34: aconst_null
/* 708:    */     //   35: astore 6
/* 709:    */     //   37: aload 5
/* 710:    */     //   39: invokeinterface 7 1 0
/* 711:    */     //   44: ifeq +14 -> 58
/* 712:    */     //   47: aload_1
/* 713:    */     //   48: aload 5
/* 714:    */     //   50: invokestatic 8	nxt/BlockDb:loadBlock	(Ljava/sql/Connection;Ljava/sql/ResultSet;)Lnxt/BlockImpl;
/* 715:    */     //   53: astore 7
/* 716:    */     //   55: goto +35 -> 90
/* 717:    */     //   58: new 15	java/lang/RuntimeException
/* 718:    */     //   61: dup
/* 719:    */     //   62: new 19	java/lang/StringBuilder
/* 720:    */     //   65: dup
/* 721:    */     //   66: invokespecial 20	java/lang/StringBuilder:<init>	()V
/* 722:    */     //   69: ldc 29
/* 723:    */     //   71: invokevirtual 22	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 724:    */     //   74: iload_0
/* 725:    */     //   75: invokevirtual 30	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
/* 726:    */     //   78: ldc 31
/* 727:    */     //   80: invokevirtual 22	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 728:    */     //   83: invokevirtual 25	java/lang/StringBuilder:toString	()Ljava/lang/String;
/* 729:    */     //   86: invokespecial 32	java/lang/RuntimeException:<init>	(Ljava/lang/String;)V
/* 730:    */     //   89: athrow
/* 731:    */     //   90: aload 7
/* 732:    */     //   92: astore 8
/* 733:    */     //   94: aload 5
/* 734:    */     //   96: ifnull +37 -> 133
/* 735:    */     //   99: aload 6
/* 736:    */     //   101: ifnull +25 -> 126
/* 737:    */     //   104: aload 5
/* 738:    */     //   106: invokeinterface 9 1 0
/* 739:    */     //   111: goto +22 -> 133
/* 740:    */     //   114: astore 9
/* 741:    */     //   116: aload 6
/* 742:    */     //   118: aload 9
/* 743:    */     //   120: invokevirtual 11	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 744:    */     //   123: goto +10 -> 133
/* 745:    */     //   126: aload 5
/* 746:    */     //   128: invokeinterface 9 1 0
/* 747:    */     //   133: aload_3
/* 748:    */     //   134: ifnull +35 -> 169
/* 749:    */     //   137: aload 4
/* 750:    */     //   139: ifnull +24 -> 163
/* 751:    */     //   142: aload_3
/* 752:    */     //   143: invokeinterface 12 1 0
/* 753:    */     //   148: goto +21 -> 169
/* 754:    */     //   151: astore 9
/* 755:    */     //   153: aload 4
/* 756:    */     //   155: aload 9
/* 757:    */     //   157: invokevirtual 11	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 758:    */     //   160: goto +9 -> 169
/* 759:    */     //   163: aload_3
/* 760:    */     //   164: invokeinterface 12 1 0
/* 761:    */     //   169: aload_1
/* 762:    */     //   170: ifnull +33 -> 203
/* 763:    */     //   173: aload_2
/* 764:    */     //   174: ifnull +23 -> 197
/* 765:    */     //   177: aload_1
/* 766:    */     //   178: invokeinterface 13 1 0
/* 767:    */     //   183: goto +20 -> 203
/* 768:    */     //   186: astore 9
/* 769:    */     //   188: aload_2
/* 770:    */     //   189: aload 9
/* 771:    */     //   191: invokevirtual 11	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 772:    */     //   194: goto +9 -> 203
/* 773:    */     //   197: aload_1
/* 774:    */     //   198: invokeinterface 13 1 0
/* 775:    */     //   203: aload 8
/* 776:    */     //   205: areturn
/* 777:    */     //   206: astore 7
/* 778:    */     //   208: aload 7
/* 779:    */     //   210: astore 6
/* 780:    */     //   212: aload 7
/* 781:    */     //   214: athrow
/* 782:    */     //   215: astore 10
/* 783:    */     //   217: aload 5
/* 784:    */     //   219: ifnull +37 -> 256
/* 785:    */     //   222: aload 6
/* 786:    */     //   224: ifnull +25 -> 249
/* 787:    */     //   227: aload 5
/* 788:    */     //   229: invokeinterface 9 1 0
/* 789:    */     //   234: goto +22 -> 256
/* 790:    */     //   237: astore 11
/* 791:    */     //   239: aload 6
/* 792:    */     //   241: aload 11
/* 793:    */     //   243: invokevirtual 11	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 794:    */     //   246: goto +10 -> 256
/* 795:    */     //   249: aload 5
/* 796:    */     //   251: invokeinterface 9 1 0
/* 797:    */     //   256: aload 10
/* 798:    */     //   258: athrow
/* 799:    */     //   259: astore 5
/* 800:    */     //   261: aload 5
/* 801:    */     //   263: astore 4
/* 802:    */     //   265: aload 5
/* 803:    */     //   267: athrow
/* 804:    */     //   268: astore 12
/* 805:    */     //   270: aload_3
/* 806:    */     //   271: ifnull +35 -> 306
/* 807:    */     //   274: aload 4
/* 808:    */     //   276: ifnull +24 -> 300
/* 809:    */     //   279: aload_3
/* 810:    */     //   280: invokeinterface 12 1 0
/* 811:    */     //   285: goto +21 -> 306
/* 812:    */     //   288: astore 13
/* 813:    */     //   290: aload 4
/* 814:    */     //   292: aload 13
/* 815:    */     //   294: invokevirtual 11	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 816:    */     //   297: goto +9 -> 306
/* 817:    */     //   300: aload_3
/* 818:    */     //   301: invokeinterface 12 1 0
/* 819:    */     //   306: aload 12
/* 820:    */     //   308: athrow
/* 821:    */     //   309: astore_3
/* 822:    */     //   310: aload_3
/* 823:    */     //   311: astore_2
/* 824:    */     //   312: aload_3
/* 825:    */     //   313: athrow
/* 826:    */     //   314: astore 14
/* 827:    */     //   316: aload_1
/* 828:    */     //   317: ifnull +33 -> 350
/* 829:    */     //   320: aload_2
/* 830:    */     //   321: ifnull +23 -> 344
/* 831:    */     //   324: aload_1
/* 832:    */     //   325: invokeinterface 13 1 0
/* 833:    */     //   330: goto +20 -> 350
/* 834:    */     //   333: astore 15
/* 835:    */     //   335: aload_2
/* 836:    */     //   336: aload 15
/* 837:    */     //   338: invokevirtual 11	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 838:    */     //   341: goto +9 -> 350
/* 839:    */     //   344: aload_1
/* 840:    */     //   345: invokeinterface 13 1 0
/* 841:    */     //   350: aload 14
/* 842:    */     //   352: athrow
/* 843:    */     //   353: astore_1
/* 844:    */     //   354: new 15	java/lang/RuntimeException
/* 845:    */     //   357: dup
/* 846:    */     //   358: aload_1
/* 847:    */     //   359: invokevirtual 16	java/sql/SQLException:toString	()Ljava/lang/String;
/* 848:    */     //   362: aload_1
/* 849:    */     //   363: invokespecial 17	java/lang/RuntimeException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
/* 850:    */     //   366: athrow
/* 851:    */     //   367: astore_1
/* 852:    */     //   368: new 15	java/lang/RuntimeException
/* 853:    */     //   371: dup
/* 854:    */     //   372: new 19	java/lang/StringBuilder
/* 855:    */     //   375: dup
/* 856:    */     //   376: invokespecial 20	java/lang/StringBuilder:<init>	()V
/* 857:    */     //   379: ldc 36
/* 858:    */     //   381: invokevirtual 22	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 859:    */     //   384: iload_0
/* 860:    */     //   385: invokevirtual 30	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
/* 861:    */     //   388: ldc 24
/* 862:    */     //   390: invokevirtual 22	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 863:    */     //   393: invokevirtual 25	java/lang/StringBuilder:toString	()Ljava/lang/String;
/* 864:    */     //   396: aload_1
/* 865:    */     //   397: invokespecial 17	java/lang/RuntimeException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
/* 866:    */     //   400: athrow
/* 867:    */     // Line number table:
/* 868:    */     //   Java source line #63	-> byte code offset #0
/* 869:    */     //   Java source line #64	-> byte code offset #6
/* 870:    */     //   Java source line #63	-> byte code offset #15
/* 871:    */     //   Java source line #65	-> byte code offset #18
/* 872:    */     //   Java source line #66	-> byte code offset #26
/* 873:    */     //   Java source line #68	-> byte code offset #37
/* 874:    */     //   Java source line #69	-> byte code offset #47
/* 875:    */     //   Java source line #71	-> byte code offset #58
/* 876:    */     //   Java source line #73	-> byte code offset #90
/* 877:    */     //   Java source line #74	-> byte code offset #94
/* 878:    */     //   Java source line #75	-> byte code offset #133
/* 879:    */     //   Java source line #66	-> byte code offset #206
/* 880:    */     //   Java source line #74	-> byte code offset #215
/* 881:    */     //   Java source line #63	-> byte code offset #259
/* 882:    */     //   Java source line #75	-> byte code offset #268
/* 883:    */     //   Java source line #63	-> byte code offset #309
/* 884:    */     //   Java source line #75	-> byte code offset #314
/* 885:    */     //   Java source line #76	-> byte code offset #354
/* 886:    */     //   Java source line #77	-> byte code offset #367
/* 887:    */     //   Java source line #78	-> byte code offset #368
/* 888:    */     // Local variable table:
/* 889:    */     //   start	length	slot	name	signature
/* 890:    */     //   0	401	0	paramInt	int
/* 891:    */     //   3	342	1	localConnection	Connection
/* 892:    */     //   353	10	1	localSQLException	SQLException
/* 893:    */     //   367	30	1	localValidationException	NxtException.ValidationException
/* 894:    */     //   5	331	2	localObject1	Object
/* 895:    */     //   14	287	3	localPreparedStatement	PreparedStatement
/* 896:    */     //   309	4	3	localThrowable1	Throwable
/* 897:    */     //   16	275	4	localObject2	Object
/* 898:    */     //   32	218	5	localResultSet	ResultSet
/* 899:    */     //   259	7	5	localThrowable2	Throwable
/* 900:    */     //   35	205	6	localObject3	Object
/* 901:    */     //   53	38	7	localBlockImpl1	BlockImpl
/* 902:    */     //   206	7	7	localThrowable3	Throwable
/* 903:    */     //   92	112	8	localBlockImpl2	BlockImpl
/* 904:    */     //   114	5	9	localThrowable4	Throwable
/* 905:    */     //   151	5	9	localThrowable5	Throwable
/* 906:    */     //   186	4	9	localThrowable6	Throwable
/* 907:    */     //   215	42	10	localObject4	Object
/* 908:    */     //   237	5	11	localThrowable7	Throwable
/* 909:    */     //   268	39	12	localObject5	Object
/* 910:    */     //   288	5	13	localThrowable8	Throwable
/* 911:    */     //   314	37	14	localObject6	Object
/* 912:    */     //   333	4	15	localThrowable9	Throwable
/* 913:    */     // Exception table:
/* 914:    */     //   from	to	target	type
/* 915:    */     //   104	111	114	java/lang/Throwable
/* 916:    */     //   142	148	151	java/lang/Throwable
/* 917:    */     //   177	183	186	java/lang/Throwable
/* 918:    */     //   37	94	206	java/lang/Throwable
/* 919:    */     //   37	94	215	finally
/* 920:    */     //   206	217	215	finally
/* 921:    */     //   227	234	237	java/lang/Throwable
/* 922:    */     //   18	133	259	java/lang/Throwable
/* 923:    */     //   206	259	259	java/lang/Throwable
/* 924:    */     //   18	133	268	finally
/* 925:    */     //   206	270	268	finally
/* 926:    */     //   279	285	288	java/lang/Throwable
/* 927:    */     //   6	169	309	java/lang/Throwable
/* 928:    */     //   206	309	309	java/lang/Throwable
/* 929:    */     //   6	169	314	finally
/* 930:    */     //   206	316	314	finally
/* 931:    */     //   324	330	333	java/lang/Throwable
/* 932:    */     //   0	203	353	java/sql/SQLException
/* 933:    */     //   206	353	353	java/sql/SQLException
/* 934:    */     //   0	203	367	nxt/NxtException$ValidationException
/* 935:    */     //   206	353	367	nxt/NxtException$ValidationException
/* 936:    */   }
/* 937:    */   
/* 938:    */   /* Error */
/* 939:    */   static BlockImpl findLastBlock()
/* 940:    */   {
/* 941:    */     // Byte code:
/* 942:    */     //   0: invokestatic 2	nxt/db/Db:getConnection	()Ljava/sql/Connection;
/* 943:    */     //   3: astore_0
/* 944:    */     //   4: aconst_null
/* 945:    */     //   5: astore_1
/* 946:    */     //   6: aload_0
/* 947:    */     //   7: ldc 37
/* 948:    */     //   9: invokeinterface 4 2 0
/* 949:    */     //   14: astore_2
/* 950:    */     //   15: aconst_null
/* 951:    */     //   16: astore_3
/* 952:    */     //   17: aconst_null
/* 953:    */     //   18: astore 4
/* 954:    */     //   20: aload_2
/* 955:    */     //   21: invokeinterface 6 1 0
/* 956:    */     //   26: astore 5
/* 957:    */     //   28: aconst_null
/* 958:    */     //   29: astore 6
/* 959:    */     //   31: aload 5
/* 960:    */     //   33: invokeinterface 7 1 0
/* 961:    */     //   38: ifeq +11 -> 49
/* 962:    */     //   41: aload_0
/* 963:    */     //   42: aload 5
/* 964:    */     //   44: invokestatic 8	nxt/BlockDb:loadBlock	(Ljava/sql/Connection;Ljava/sql/ResultSet;)Lnxt/BlockImpl;
/* 965:    */     //   47: astore 4
/* 966:    */     //   49: aload 5
/* 967:    */     //   51: ifnull +93 -> 144
/* 968:    */     //   54: aload 6
/* 969:    */     //   56: ifnull +25 -> 81
/* 970:    */     //   59: aload 5
/* 971:    */     //   61: invokeinterface 9 1 0
/* 972:    */     //   66: goto +78 -> 144
/* 973:    */     //   69: astore 7
/* 974:    */     //   71: aload 6
/* 975:    */     //   73: aload 7
/* 976:    */     //   75: invokevirtual 11	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 977:    */     //   78: goto +66 -> 144
/* 978:    */     //   81: aload 5
/* 979:    */     //   83: invokeinterface 9 1 0
/* 980:    */     //   88: goto +56 -> 144
/* 981:    */     //   91: astore 7
/* 982:    */     //   93: aload 7
/* 983:    */     //   95: astore 6
/* 984:    */     //   97: aload 7
/* 985:    */     //   99: athrow
/* 986:    */     //   100: astore 8
/* 987:    */     //   102: aload 5
/* 988:    */     //   104: ifnull +37 -> 141
/* 989:    */     //   107: aload 6
/* 990:    */     //   109: ifnull +25 -> 134
/* 991:    */     //   112: aload 5
/* 992:    */     //   114: invokeinterface 9 1 0
/* 993:    */     //   119: goto +22 -> 141
/* 994:    */     //   122: astore 9
/* 995:    */     //   124: aload 6
/* 996:    */     //   126: aload 9
/* 997:    */     //   128: invokevirtual 11	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 998:    */     //   131: goto +10 -> 141
/* 999:    */     //   134: aload 5
/* :00:    */     //   136: invokeinterface 9 1 0
/* :01:    */     //   141: aload 8
/* :02:    */     //   143: athrow
/* :03:    */     //   144: aload 4
/* :04:    */     //   146: astore 5
/* :05:    */     //   148: aload_2
/* :06:    */     //   149: ifnull +33 -> 182
/* :07:    */     //   152: aload_3
/* :08:    */     //   153: ifnull +23 -> 176
/* :09:    */     //   156: aload_2
/* :10:    */     //   157: invokeinterface 12 1 0
/* :11:    */     //   162: goto +20 -> 182
/* :12:    */     //   165: astore 6
/* :13:    */     //   167: aload_3
/* :14:    */     //   168: aload 6
/* :15:    */     //   170: invokevirtual 11	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* :16:    */     //   173: goto +9 -> 182
/* :17:    */     //   176: aload_2
/* :18:    */     //   177: invokeinterface 12 1 0
/* :19:    */     //   182: aload_0
/* :20:    */     //   183: ifnull +33 -> 216
/* :21:    */     //   186: aload_1
/* :22:    */     //   187: ifnull +23 -> 210
/* :23:    */     //   190: aload_0
/* :24:    */     //   191: invokeinterface 13 1 0
/* :25:    */     //   196: goto +20 -> 216
/* :26:    */     //   199: astore 6
/* :27:    */     //   201: aload_1
/* :28:    */     //   202: aload 6
/* :29:    */     //   204: invokevirtual 11	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* :30:    */     //   207: goto +9 -> 216
/* :31:    */     //   210: aload_0
/* :32:    */     //   211: invokeinterface 13 1 0
/* :33:    */     //   216: aload 5
/* :34:    */     //   218: areturn
/* :35:    */     //   219: astore 4
/* :36:    */     //   221: aload 4
/* :37:    */     //   223: astore_3
/* :38:    */     //   224: aload 4
/* :39:    */     //   226: athrow
/* :40:    */     //   227: astore 10
/* :41:    */     //   229: aload_2
/* :42:    */     //   230: ifnull +33 -> 263
/* :43:    */     //   233: aload_3
/* :44:    */     //   234: ifnull +23 -> 257
/* :45:    */     //   237: aload_2
/* :46:    */     //   238: invokeinterface 12 1 0
/* :47:    */     //   243: goto +20 -> 263
/* :48:    */     //   246: astore 11
/* :49:    */     //   248: aload_3
/* :50:    */     //   249: aload 11
/* :51:    */     //   251: invokevirtual 11	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* :52:    */     //   254: goto +9 -> 263
/* :53:    */     //   257: aload_2
/* :54:    */     //   258: invokeinterface 12 1 0
/* :55:    */     //   263: aload 10
/* :56:    */     //   265: athrow
/* :57:    */     //   266: astore_2
/* :58:    */     //   267: aload_2
/* :59:    */     //   268: astore_1
/* :60:    */     //   269: aload_2
/* :61:    */     //   270: athrow
/* :62:    */     //   271: astore 12
/* :63:    */     //   273: aload_0
/* :64:    */     //   274: ifnull +33 -> 307
/* :65:    */     //   277: aload_1
/* :66:    */     //   278: ifnull +23 -> 301
/* :67:    */     //   281: aload_0
/* :68:    */     //   282: invokeinterface 13 1 0
/* :69:    */     //   287: goto +20 -> 307
/* :70:    */     //   290: astore 13
/* :71:    */     //   292: aload_1
/* :72:    */     //   293: aload 13
/* :73:    */     //   295: invokevirtual 11	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* :74:    */     //   298: goto +9 -> 307
/* :75:    */     //   301: aload_0
/* :76:    */     //   302: invokeinterface 13 1 0
/* :77:    */     //   307: aload 12
/* :78:    */     //   309: athrow
/* :79:    */     //   310: astore_0
/* :80:    */     //   311: new 15	java/lang/RuntimeException
/* :81:    */     //   314: dup
/* :82:    */     //   315: aload_0
/* :83:    */     //   316: invokevirtual 16	java/sql/SQLException:toString	()Ljava/lang/String;
/* :84:    */     //   319: aload_0
/* :85:    */     //   320: invokespecial 17	java/lang/RuntimeException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
/* :86:    */     //   323: athrow
/* :87:    */     //   324: astore_0
/* :88:    */     //   325: new 15	java/lang/RuntimeException
/* :89:    */     //   328: dup
/* :90:    */     //   329: ldc 38
/* :91:    */     //   331: aload_0
/* :92:    */     //   332: invokespecial 17	java/lang/RuntimeException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
/* :93:    */     //   335: athrow
/* :94:    */     // Line number table:
/* :95:    */     //   Java source line #83	-> byte code offset #0
/* :96:    */     //   Java source line #84	-> byte code offset #6
/* :97:    */     //   Java source line #83	-> byte code offset #15
/* :98:    */     //   Java source line #85	-> byte code offset #17
/* :99:    */     //   Java source line #86	-> byte code offset #20
/* ;00:    */     //   Java source line #87	-> byte code offset #31
/* ;01:    */     //   Java source line #88	-> byte code offset #41
/* ;02:    */     //   Java source line #90	-> byte code offset #49
/* ;03:    */     //   Java source line #86	-> byte code offset #91
/* ;04:    */     //   Java source line #90	-> byte code offset #100
/* ;05:    */     //   Java source line #91	-> byte code offset #144
/* ;06:    */     //   Java source line #92	-> byte code offset #148
/* ;07:    */     //   Java source line #83	-> byte code offset #219
/* ;08:    */     //   Java source line #92	-> byte code offset #227
/* ;09:    */     //   Java source line #83	-> byte code offset #266
/* ;10:    */     //   Java source line #92	-> byte code offset #271
/* ;11:    */     //   Java source line #93	-> byte code offset #311
/* ;12:    */     //   Java source line #94	-> byte code offset #324
/* ;13:    */     //   Java source line #95	-> byte code offset #325
/* ;14:    */     // Local variable table:
/* ;15:    */     //   start	length	slot	name	signature
/* ;16:    */     //   3	299	0	localConnection	Connection
/* ;17:    */     //   310	10	0	localSQLException	SQLException
/* ;18:    */     //   324	8	0	localValidationException	NxtException.ValidationException
/* ;19:    */     //   5	288	1	localObject1	Object
/* ;20:    */     //   14	244	2	localPreparedStatement	PreparedStatement
/* ;21:    */     //   266	4	2	localThrowable1	Throwable
/* ;22:    */     //   16	233	3	localObject2	Object
/* ;23:    */     //   18	127	4	localBlockImpl	BlockImpl
/* ;24:    */     //   219	6	4	localThrowable2	Throwable
/* ;25:    */     //   26	191	5	localObject3	Object
/* ;26:    */     //   29	96	6	localObject4	Object
/* ;27:    */     //   165	4	6	localThrowable3	Throwable
/* ;28:    */     //   199	4	6	localThrowable4	Throwable
/* ;29:    */     //   69	5	7	localThrowable5	Throwable
/* ;30:    */     //   91	7	7	localThrowable6	Throwable
/* ;31:    */     //   100	42	8	localObject5	Object
/* ;32:    */     //   122	5	9	localThrowable7	Throwable
/* ;33:    */     //   227	37	10	localObject6	Object
/* ;34:    */     //   246	4	11	localThrowable8	Throwable
/* ;35:    */     //   271	37	12	localObject7	Object
/* ;36:    */     //   290	4	13	localThrowable9	Throwable
/* ;37:    */     // Exception table:
/* ;38:    */     //   from	to	target	type
/* ;39:    */     //   59	66	69	java/lang/Throwable
/* ;40:    */     //   31	49	91	java/lang/Throwable
/* ;41:    */     //   31	49	100	finally
/* ;42:    */     //   91	102	100	finally
/* ;43:    */     //   112	119	122	java/lang/Throwable
/* ;44:    */     //   156	162	165	java/lang/Throwable
/* ;45:    */     //   190	196	199	java/lang/Throwable
/* ;46:    */     //   17	148	219	java/lang/Throwable
/* ;47:    */     //   17	148	227	finally
/* ;48:    */     //   219	229	227	finally
/* ;49:    */     //   237	243	246	java/lang/Throwable
/* ;50:    */     //   6	182	266	java/lang/Throwable
/* ;51:    */     //   219	266	266	java/lang/Throwable
/* ;52:    */     //   6	182	271	finally
/* ;53:    */     //   219	273	271	finally
/* ;54:    */     //   281	287	290	java/lang/Throwable
/* ;55:    */     //   0	216	310	java/sql/SQLException
/* ;56:    */     //   219	310	310	java/sql/SQLException
/* ;57:    */     //   0	216	324	nxt/NxtException$ValidationException
/* ;58:    */     //   219	310	324	nxt/NxtException$ValidationException
/* ;59:    */   }
/* ;60:    */   
/* ;61:    */   /* Error */
/* ;62:    */   static BlockImpl findLastBlock(int paramInt)
/* ;63:    */   {
/* ;64:    */     // Byte code:
/* ;65:    */     //   0: invokestatic 2	nxt/db/Db:getConnection	()Ljava/sql/Connection;
/* ;66:    */     //   3: astore_1
/* ;67:    */     //   4: aconst_null
/* ;68:    */     //   5: astore_2
/* ;69:    */     //   6: aload_1
/* ;70:    */     //   7: ldc 39
/* ;71:    */     //   9: invokeinterface 4 2 0
/* ;72:    */     //   14: astore_3
/* ;73:    */     //   15: aconst_null
/* ;74:    */     //   16: astore 4
/* ;75:    */     //   18: aload_3
/* ;76:    */     //   19: iconst_1
/* ;77:    */     //   20: iload_0
/* ;78:    */     //   21: invokeinterface 28 3 0
/* ;79:    */     //   26: aconst_null
/* ;80:    */     //   27: astore 5
/* ;81:    */     //   29: aload_3
/* ;82:    */     //   30: invokeinterface 6 1 0
/* ;83:    */     //   35: astore 6
/* ;84:    */     //   37: aconst_null
/* ;85:    */     //   38: astore 7
/* ;86:    */     //   40: aload 6
/* ;87:    */     //   42: invokeinterface 7 1 0
/* ;88:    */     //   47: ifeq +11 -> 58
/* ;89:    */     //   50: aload_1
/* ;90:    */     //   51: aload 6
/* ;91:    */     //   53: invokestatic 8	nxt/BlockDb:loadBlock	(Ljava/sql/Connection;Ljava/sql/ResultSet;)Lnxt/BlockImpl;
/* ;92:    */     //   56: astore 5
/* ;93:    */     //   58: aload 6
/* ;94:    */     //   60: ifnull +93 -> 153
/* ;95:    */     //   63: aload 7
/* ;96:    */     //   65: ifnull +25 -> 90
/* ;97:    */     //   68: aload 6
/* ;98:    */     //   70: invokeinterface 9 1 0
/* ;99:    */     //   75: goto +78 -> 153
/* <00:    */     //   78: astore 8
/* <01:    */     //   80: aload 7
/* <02:    */     //   82: aload 8
/* <03:    */     //   84: invokevirtual 11	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* <04:    */     //   87: goto +66 -> 153
/* <05:    */     //   90: aload 6
/* <06:    */     //   92: invokeinterface 9 1 0
/* <07:    */     //   97: goto +56 -> 153
/* <08:    */     //   100: astore 8
/* <09:    */     //   102: aload 8
/* <10:    */     //   104: astore 7
/* <11:    */     //   106: aload 8
/* <12:    */     //   108: athrow
/* <13:    */     //   109: astore 9
/* <14:    */     //   111: aload 6
/* <15:    */     //   113: ifnull +37 -> 150
/* <16:    */     //   116: aload 7
/* <17:    */     //   118: ifnull +25 -> 143
/* <18:    */     //   121: aload 6
/* <19:    */     //   123: invokeinterface 9 1 0
/* <20:    */     //   128: goto +22 -> 150
/* <21:    */     //   131: astore 10
/* <22:    */     //   133: aload 7
/* <23:    */     //   135: aload 10
/* <24:    */     //   137: invokevirtual 11	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* <25:    */     //   140: goto +10 -> 150
/* <26:    */     //   143: aload 6
/* <27:    */     //   145: invokeinterface 9 1 0
/* <28:    */     //   150: aload 9
/* <29:    */     //   152: athrow
/* <30:    */     //   153: aload 5
/* <31:    */     //   155: astore 6
/* <32:    */     //   157: aload_3
/* <33:    */     //   158: ifnull +35 -> 193
/* <34:    */     //   161: aload 4
/* <35:    */     //   163: ifnull +24 -> 187
/* <36:    */     //   166: aload_3
/* <37:    */     //   167: invokeinterface 12 1 0
/* <38:    */     //   172: goto +21 -> 193
/* <39:    */     //   175: astore 7
/* <40:    */     //   177: aload 4
/* <41:    */     //   179: aload 7
/* <42:    */     //   181: invokevirtual 11	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* <43:    */     //   184: goto +9 -> 193
/* <44:    */     //   187: aload_3
/* <45:    */     //   188: invokeinterface 12 1 0
/* <46:    */     //   193: aload_1
/* <47:    */     //   194: ifnull +33 -> 227
/* <48:    */     //   197: aload_2
/* <49:    */     //   198: ifnull +23 -> 221
/* <50:    */     //   201: aload_1
/* <51:    */     //   202: invokeinterface 13 1 0
/* <52:    */     //   207: goto +20 -> 227
/* <53:    */     //   210: astore 7
/* <54:    */     //   212: aload_2
/* <55:    */     //   213: aload 7
/* <56:    */     //   215: invokevirtual 11	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* <57:    */     //   218: goto +9 -> 227
/* <58:    */     //   221: aload_1
/* <59:    */     //   222: invokeinterface 13 1 0
/* <60:    */     //   227: aload 6
/* <61:    */     //   229: areturn
/* <62:    */     //   230: astore 5
/* <63:    */     //   232: aload 5
/* <64:    */     //   234: astore 4
/* <65:    */     //   236: aload 5
/* <66:    */     //   238: athrow
/* <67:    */     //   239: astore 11
/* <68:    */     //   241: aload_3
/* <69:    */     //   242: ifnull +35 -> 277
/* <70:    */     //   245: aload 4
/* <71:    */     //   247: ifnull +24 -> 271
/* <72:    */     //   250: aload_3
/* <73:    */     //   251: invokeinterface 12 1 0
/* <74:    */     //   256: goto +21 -> 277
/* <75:    */     //   259: astore 12
/* <76:    */     //   261: aload 4
/* <77:    */     //   263: aload 12
/* <78:    */     //   265: invokevirtual 11	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* <79:    */     //   268: goto +9 -> 277
/* <80:    */     //   271: aload_3
/* <81:    */     //   272: invokeinterface 12 1 0
/* <82:    */     //   277: aload 11
/* <83:    */     //   279: athrow
/* <84:    */     //   280: astore_3
/* <85:    */     //   281: aload_3
/* <86:    */     //   282: astore_2
/* <87:    */     //   283: aload_3
/* <88:    */     //   284: athrow
/* <89:    */     //   285: astore 13
/* <90:    */     //   287: aload_1
/* <91:    */     //   288: ifnull +33 -> 321
/* <92:    */     //   291: aload_2
/* <93:    */     //   292: ifnull +23 -> 315
/* <94:    */     //   295: aload_1
/* <95:    */     //   296: invokeinterface 13 1 0
/* <96:    */     //   301: goto +20 -> 321
/* <97:    */     //   304: astore 14
/* <98:    */     //   306: aload_2
/* <99:    */     //   307: aload 14
/* =00:    */     //   309: invokevirtual 11	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* =01:    */     //   312: goto +9 -> 321
/* =02:    */     //   315: aload_1
/* =03:    */     //   316: invokeinterface 13 1 0
/* =04:    */     //   321: aload 13
/* =05:    */     //   323: athrow
/* =06:    */     //   324: astore_1
/* =07:    */     //   325: new 15	java/lang/RuntimeException
/* =08:    */     //   328: dup
/* =09:    */     //   329: aload_1
/* =10:    */     //   330: invokevirtual 16	java/sql/SQLException:toString	()Ljava/lang/String;
/* =11:    */     //   333: aload_1
/* =12:    */     //   334: invokespecial 17	java/lang/RuntimeException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
/* =13:    */     //   337: athrow
/* =14:    */     //   338: astore_1
/* =15:    */     //   339: new 15	java/lang/RuntimeException
/* =16:    */     //   342: dup
/* =17:    */     //   343: new 19	java/lang/StringBuilder
/* =18:    */     //   346: dup
/* =19:    */     //   347: invokespecial 20	java/lang/StringBuilder:<init>	()V
/* =20:    */     //   350: ldc 40
/* =21:    */     //   352: invokevirtual 22	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* =22:    */     //   355: iload_0
/* =23:    */     //   356: invokevirtual 30	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
/* =24:    */     //   359: ldc 41
/* =25:    */     //   361: invokevirtual 22	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* =26:    */     //   364: invokevirtual 25	java/lang/StringBuilder:toString	()Ljava/lang/String;
/* =27:    */     //   367: aload_1
/* =28:    */     //   368: invokespecial 17	java/lang/RuntimeException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
/* =29:    */     //   371: athrow
/* =30:    */     // Line number table:
/* =31:    */     //   Java source line #100	-> byte code offset #0
/* =32:    */     //   Java source line #101	-> byte code offset #6
/* =33:    */     //   Java source line #100	-> byte code offset #15
/* =34:    */     //   Java source line #102	-> byte code offset #18
/* =35:    */     //   Java source line #103	-> byte code offset #26
/* =36:    */     //   Java source line #104	-> byte code offset #29
/* =37:    */     //   Java source line #105	-> byte code offset #40
/* =38:    */     //   Java source line #106	-> byte code offset #50
/* =39:    */     //   Java source line #108	-> byte code offset #58
/* =40:    */     //   Java source line #104	-> byte code offset #100
/* =41:    */     //   Java source line #108	-> byte code offset #109
/* =42:    */     //   Java source line #109	-> byte code offset #153
/* =43:    */     //   Java source line #110	-> byte code offset #157
/* =44:    */     //   Java source line #100	-> byte code offset #230
/* =45:    */     //   Java source line #110	-> byte code offset #239
/* =46:    */     //   Java source line #100	-> byte code offset #280
/* =47:    */     //   Java source line #110	-> byte code offset #285
/* =48:    */     //   Java source line #111	-> byte code offset #325
/* =49:    */     //   Java source line #112	-> byte code offset #338
/* =50:    */     //   Java source line #113	-> byte code offset #339
/* =51:    */     // Local variable table:
/* =52:    */     //   start	length	slot	name	signature
/* =53:    */     //   0	372	0	paramInt	int
/* =54:    */     //   3	313	1	localConnection	Connection
/* =55:    */     //   324	10	1	localSQLException	SQLException
/* =56:    */     //   338	30	1	localValidationException	NxtException.ValidationException
/* =57:    */     //   5	302	2	localObject1	Object
/* =58:    */     //   14	258	3	localPreparedStatement	PreparedStatement
/* =59:    */     //   280	4	3	localThrowable1	Throwable
/* =60:    */     //   16	246	4	localObject2	Object
/* =61:    */     //   27	127	5	localBlockImpl	BlockImpl
/* =62:    */     //   230	7	5	localThrowable2	Throwable
/* =63:    */     //   35	193	6	localObject3	Object
/* =64:    */     //   38	96	7	localObject4	Object
/* =65:    */     //   175	5	7	localThrowable3	Throwable
/* =66:    */     //   210	4	7	localThrowable4	Throwable
/* =67:    */     //   78	5	8	localThrowable5	Throwable
/* =68:    */     //   100	7	8	localThrowable6	Throwable
/* =69:    */     //   109	42	9	localObject5	Object
/* =70:    */     //   131	5	10	localThrowable7	Throwable
/* =71:    */     //   239	39	11	localObject6	Object
/* =72:    */     //   259	5	12	localThrowable8	Throwable
/* =73:    */     //   285	37	13	localObject7	Object
/* =74:    */     //   304	4	14	localThrowable9	Throwable
/* =75:    */     // Exception table:
/* =76:    */     //   from	to	target	type
/* =77:    */     //   68	75	78	java/lang/Throwable
/* =78:    */     //   40	58	100	java/lang/Throwable
/* =79:    */     //   40	58	109	finally
/* =80:    */     //   100	111	109	finally
/* =81:    */     //   121	128	131	java/lang/Throwable
/* =82:    */     //   166	172	175	java/lang/Throwable
/* =83:    */     //   201	207	210	java/lang/Throwable
/* =84:    */     //   18	157	230	java/lang/Throwable
/* =85:    */     //   18	157	239	finally
/* =86:    */     //   230	241	239	finally
/* =87:    */     //   250	256	259	java/lang/Throwable
/* =88:    */     //   6	193	280	java/lang/Throwable
/* =89:    */     //   230	280	280	java/lang/Throwable
/* =90:    */     //   6	193	285	finally
/* =91:    */     //   230	287	285	finally
/* =92:    */     //   295	301	304	java/lang/Throwable
/* =93:    */     //   0	227	324	java/sql/SQLException
/* =94:    */     //   230	324	324	java/sql/SQLException
/* =95:    */     //   0	227	338	nxt/NxtException$ValidationException
/* =96:    */     //   230	324	338	nxt/NxtException$ValidationException
/* =97:    */   }
/* =98:    */   
/* =99:    */   static BlockImpl loadBlock(Connection paramConnection, ResultSet paramResultSet)
/* >00:    */     throws NxtException.ValidationException
/* >01:    */   {
/* >02:    */     try
/* >03:    */     {
/* >04:119 */       int i = paramResultSet.getInt("version");
/* >05:120 */       int j = paramResultSet.getInt("timestamp");
/* >06:121 */       long l1 = paramResultSet.getLong("previous_block_id");
/* >07:122 */       long l2 = paramResultSet.getLong("total_amount");
/* >08:123 */       long l3 = paramResultSet.getLong("total_fee");
/* >09:124 */       int k = paramResultSet.getInt("payload_length");
/* >10:125 */       byte[] arrayOfByte1 = paramResultSet.getBytes("generator_public_key");
/* >11:126 */       byte[] arrayOfByte2 = paramResultSet.getBytes("previous_block_hash");
/* >12:127 */       BigInteger localBigInteger = new BigInteger(paramResultSet.getBytes("cumulative_difficulty"));
/* >13:128 */       long l4 = paramResultSet.getLong("base_target");
/* >14:129 */       long l5 = paramResultSet.getLong("next_block_id");
/* >15:130 */       int m = paramResultSet.getInt("height");
/* >16:131 */       byte[] arrayOfByte3 = paramResultSet.getBytes("generation_signature");
/* >17:132 */       byte[] arrayOfByte4 = paramResultSet.getBytes("block_signature");
/* >18:133 */       byte[] arrayOfByte5 = paramResultSet.getBytes("payload_hash");
/* >19:    */       
/* >20:135 */       long l6 = paramResultSet.getLong("id");
/* >21:136 */       long l7 = paramResultSet.getLong("nonce");
/* >22:    */       
/* >23:138 */       byte[] arrayOfByte6 = paramResultSet.getBytes("ats");
/* >24:    */       
/* >25:140 */       return new BlockImpl(i, j, l1, l2, l3, k, arrayOfByte5, arrayOfByte1, arrayOfByte3, arrayOfByte4, arrayOfByte2, localBigInteger, l4, l5, m, Long.valueOf(l6), l7, arrayOfByte6);
/* >26:    */     }
/* >27:    */     catch (SQLException localSQLException)
/* >28:    */     {
/* >29:144 */       throw new RuntimeException(localSQLException.toString(), localSQLException);
/* >30:    */     }
/* >31:    */   }
/* >32:    */   
/* >33:    */   static void saveBlock(Connection paramConnection, BlockImpl paramBlockImpl)
/* >34:    */   {
/* >35:    */     try
/* >36:    */     {
/* >37:150 */       PreparedStatement localPreparedStatement = paramConnection.prepareStatement("INSERT INTO block (id, version, timestamp, previous_block_id, total_amount, total_fee, payload_length, generator_public_key, previous_block_hash, cumulative_difficulty, base_target, height, generation_signature, block_signature, payload_hash, generator_id, nonce , ats)  VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");Object localObject1 = null;
/* >38:    */       try
/* >39:    */       {
/* >40:154 */         int i = 0;
/* >41:155 */         localPreparedStatement.setLong(++i, paramBlockImpl.getId());
/* >42:156 */         localPreparedStatement.setInt(++i, paramBlockImpl.getVersion());
/* >43:157 */         localPreparedStatement.setInt(++i, paramBlockImpl.getTimestamp());
/* >44:158 */         DbUtils.setLongZeroToNull(localPreparedStatement, ++i, paramBlockImpl.getPreviousBlockId());
/* >45:159 */         localPreparedStatement.setLong(++i, paramBlockImpl.getTotalAmountNQT());
/* >46:160 */         localPreparedStatement.setLong(++i, paramBlockImpl.getTotalFeeNQT());
/* >47:161 */         localPreparedStatement.setInt(++i, paramBlockImpl.getPayloadLength());
/* >48:162 */         localPreparedStatement.setBytes(++i, paramBlockImpl.getGeneratorPublicKey());
/* >49:163 */         localPreparedStatement.setBytes(++i, paramBlockImpl.getPreviousBlockHash());
/* >50:164 */         localPreparedStatement.setBytes(++i, paramBlockImpl.getCumulativeDifficulty().toByteArray());
/* >51:165 */         localPreparedStatement.setLong(++i, paramBlockImpl.getBaseTarget());
/* >52:166 */         localPreparedStatement.setInt(++i, paramBlockImpl.getHeight());
/* >53:167 */         localPreparedStatement.setBytes(++i, paramBlockImpl.getGenerationSignature());
/* >54:168 */         localPreparedStatement.setBytes(++i, paramBlockImpl.getBlockSignature());
/* >55:169 */         localPreparedStatement.setBytes(++i, paramBlockImpl.getPayloadHash());
/* >56:170 */         localPreparedStatement.setLong(++i, paramBlockImpl.getGeneratorId());
/* >57:171 */         localPreparedStatement.setLong(++i, paramBlockImpl.getNonce().longValue());
/* >58:172 */         DbUtils.setBytes(localPreparedStatement, ++i, paramBlockImpl.getBlockATs());
/* >59:173 */         localPreparedStatement.executeUpdate();
/* >60:174 */         TransactionDb.saveTransactions(paramConnection, paramBlockImpl.getTransactions());
/* >61:    */       }
/* >62:    */       catch (Throwable localThrowable2)
/* >63:    */       {
/* >64:150 */         localObject1 = localThrowable2;throw localThrowable2;
/* >65:    */       }
/* >66:    */       finally
/* >67:    */       {
/* >68:175 */         if (localPreparedStatement != null) {
/* >69:175 */           if (localObject1 != null) {
/* >70:    */             try
/* >71:    */             {
/* >72:175 */               localPreparedStatement.close();
/* >73:    */             }
/* >74:    */             catch (Throwable localThrowable5)
/* >75:    */             {
/* >76:175 */               ((Throwable)localObject1).addSuppressed(localThrowable5);
/* >77:    */             }
/* >78:    */           } else {
/* >79:175 */             localPreparedStatement.close();
/* >80:    */           }
/* >81:    */         }
/* >82:    */       }
/* >83:176 */       if (paramBlockImpl.getPreviousBlockId() != 0L)
/* >84:    */       {
/* >85:177 */         localPreparedStatement = paramConnection.prepareStatement("UPDATE block SET next_block_id = ? WHERE id = ?");localObject1 = null;
/* >86:    */         try
/* >87:    */         {
/* >88:178 */           localPreparedStatement.setLong(1, paramBlockImpl.getId());
/* >89:179 */           localPreparedStatement.setLong(2, paramBlockImpl.getPreviousBlockId());
/* >90:180 */           localPreparedStatement.executeUpdate();
/* >91:    */         }
/* >92:    */         catch (Throwable localThrowable4)
/* >93:    */         {
/* >94:177 */           localObject1 = localThrowable4;throw localThrowable4;
/* >95:    */         }
/* >96:    */         finally
/* >97:    */         {
/* >98:181 */           if (localPreparedStatement != null) {
/* >99:181 */             if (localObject1 != null) {
/* ?00:    */               try
/* ?01:    */               {
/* ?02:181 */                 localPreparedStatement.close();
/* ?03:    */               }
/* ?04:    */               catch (Throwable localThrowable6)
/* ?05:    */               {
/* ?06:181 */                 ((Throwable)localObject1).addSuppressed(localThrowable6);
/* ?07:    */               }
/* ?08:    */             } else {
/* ?09:181 */               localPreparedStatement.close();
/* ?10:    */             }
/* ?11:    */           }
/* ?12:    */         }
/* ?13:    */       }
/* ?14:    */     }
/* ?15:    */     catch (SQLException localSQLException)
/* ?16:    */     {
/* ?17:184 */       throw new RuntimeException(localSQLException.toString(), localSQLException);
/* ?18:    */     }
/* ?19:    */   }
/* ?20:    */   
/* ?21:    */   static void deleteBlocksFrom(long paramLong)
/* ?22:    */   {
/* ?23:190 */     if (!Db.isInTransaction())
/* ?24:    */     {
/* ?25:    */       try
/* ?26:    */       {
/* ?27:192 */         Db.beginTransaction();
/* ?28:193 */         deleteBlocksFrom(paramLong);
/* ?29:194 */         Db.commitTransaction();
/* ?30:    */       }
/* ?31:    */       catch (Exception localException)
/* ?32:    */       {
/* ?33:197 */         throw localException;
/* ?34:    */       }
/* ?35:    */       finally
/* ?36:    */       {
/* ?37:199 */         Db.endTransaction();
/* ?38:    */       }
/* ?39:201 */       return;
/* ?40:    */     }
/* ?41:    */     try
/* ?42:    */     {
/* ?43:203 */       Connection localConnection = Db.getConnection();Object localObject2 = null;
/* ?44:    */       try
/* ?45:    */       {
/* ?46:204 */         PreparedStatement localPreparedStatement1 = localConnection.prepareStatement("SELECT db_id FROM block WHERE db_id >= (SELECT db_id FROM block WHERE id = ?) ORDER BY db_id DESC");Object localObject3 = null;
/* ?47:    */         try
/* ?48:    */         {
/* ?49:206 */           PreparedStatement localPreparedStatement2 = localConnection.prepareStatement("DELETE FROM block WHERE db_id = ?");Object localObject4 = null;
/* ?50:    */           try
/* ?51:    */           {
/* ?52:    */             try
/* ?53:    */             {
/* ?54:208 */               localPreparedStatement1.setLong(1, paramLong);
/* ?55:209 */               ResultSet localResultSet = localPreparedStatement1.executeQuery();Object localObject5 = null;
/* ?56:    */               try
/* ?57:    */               {
/* ?58:210 */                 Db.commitTransaction();
/* ?59:211 */                 while (localResultSet.next())
/* ?60:    */                 {
/* ?61:212 */                   localPreparedStatement2.setInt(1, localResultSet.getInt("db_id"));
/* ?62:213 */                   localPreparedStatement2.executeUpdate();
/* ?63:214 */                   Db.commitTransaction();
/* ?64:    */                 }
/* ?65:    */               }
/* ?66:    */               catch (Throwable localThrowable8)
/* ?67:    */               {
/* ?68:209 */                 localObject5 = localThrowable8;throw localThrowable8;
/* ?69:    */               }
/* ?70:    */               finally {}
/* ?71:    */             }
/* ?72:    */             catch (SQLException localSQLException2)
/* ?73:    */             {
/* ?74:218 */               Db.rollbackTransaction();
/* ?75:219 */               throw localSQLException2;
/* ?76:    */             }
/* ?77:    */           }
/* ?78:    */           catch (Throwable localThrowable6)
/* ?79:    */           {
/* ?80:203 */             localObject4 = localThrowable6;throw localThrowable6;
/* ?81:    */           }
/* ?82:    */           finally {}
/* ?83:    */         }
/* ?84:    */         catch (Throwable localThrowable4)
/* ?85:    */         {
/* ?86:203 */           localObject3 = localThrowable4;throw localThrowable4;
/* ?87:    */         }
/* ?88:    */         finally {}
/* ?89:    */       }
/* ?90:    */       catch (Throwable localThrowable2)
/* ?91:    */       {
/* ?92:203 */         localObject2 = localThrowable2;throw localThrowable2;
/* ?93:    */       }
/* ?94:    */       finally
/* ?95:    */       {
/* ?96:221 */         if (localConnection != null) {
/* ?97:221 */           if (localObject2 != null) {
/* ?98:    */             try
/* ?99:    */             {
/* @00:221 */               localConnection.close();
/* @01:    */             }
/* @02:    */             catch (Throwable localThrowable12)
/* @03:    */             {
/* @04:221 */               ((Throwable)localObject2).addSuppressed(localThrowable12);
/* @05:    */             }
/* @06:    */           } else {
/* @07:221 */             localConnection.close();
/* @08:    */           }
/* @09:    */         }
/* @10:    */       }
/* @11:    */     }
/* @12:    */     catch (SQLException localSQLException1)
/* @13:    */     {
/* @14:222 */       throw new RuntimeException(localSQLException1.toString(), localSQLException1);
/* @15:    */     }
/* @16:    */   }
/* @17:    */   
/* @18:    */   static void deleteAll()
/* @19:    */   {
/* @20:227 */     if (!Db.isInTransaction())
/* @21:    */     {
/* @22:    */       try
/* @23:    */       {
/* @24:229 */         Db.beginTransaction();
/* @25:230 */         deleteAll();
/* @26:231 */         Db.commitTransaction();
/* @27:    */       }
/* @28:    */       catch (Exception localException)
/* @29:    */       {
/* @30:234 */         throw localException;
/* @31:    */       }
/* @32:    */       finally
/* @33:    */       {
/* @34:236 */         Db.endTransaction();
/* @35:    */       }
/* @36:238 */       return;
/* @37:    */     }
/* @38:240 */     Logger.logMessage("Deleting blockchain...");
/* @39:    */     try
/* @40:    */     {
/* @41:241 */       Connection localConnection = Db.getConnection();Object localObject2 = null;
/* @42:    */       try
/* @43:    */       {
/* @44:242 */         Statement localStatement = localConnection.createStatement();Object localObject3 = null;
/* @45:    */         try
/* @46:    */         {
/* @47:    */           try
/* @48:    */           {
/* @49:244 */             localStatement.executeUpdate("SET REFERENTIAL_INTEGRITY FALSE");
/* @50:245 */             localStatement.executeUpdate("TRUNCATE TABLE transaction");
/* @51:246 */             localStatement.executeUpdate("TRUNCATE TABLE block");
/* @52:247 */             localStatement.executeUpdate("SET REFERENTIAL_INTEGRITY TRUE");
/* @53:248 */             Db.commitTransaction();
/* @54:    */           }
/* @55:    */           catch (SQLException localSQLException2)
/* @56:    */           {
/* @57:250 */             Db.rollbackTransaction();
/* @58:251 */             throw localSQLException2;
/* @59:    */           }
/* @60:    */         }
/* @61:    */         catch (Throwable localThrowable4)
/* @62:    */         {
/* @63:241 */           localObject3 = localThrowable4;throw localThrowable4;
/* @64:    */         }
/* @65:    */         finally {}
/* @66:    */       }
/* @67:    */       catch (Throwable localThrowable2)
/* @68:    */       {
/* @69:241 */         localObject2 = localThrowable2;throw localThrowable2;
/* @70:    */       }
/* @71:    */       finally
/* @72:    */       {
/* @73:253 */         if (localConnection != null) {
/* @74:253 */           if (localObject2 != null) {
/* @75:    */             try
/* @76:    */             {
/* @77:253 */               localConnection.close();
/* @78:    */             }
/* @79:    */             catch (Throwable localThrowable6)
/* @80:    */             {
/* @81:253 */               ((Throwable)localObject2).addSuppressed(localThrowable6);
/* @82:    */             }
/* @83:    */           } else {
/* @84:253 */             localConnection.close();
/* @85:    */           }
/* @86:    */         }
/* @87:    */       }
/* @88:    */     }
/* @89:    */     catch (SQLException localSQLException1)
/* @90:    */     {
/* @91:254 */       throw new RuntimeException(localSQLException1.toString(), localSQLException1);
/* @92:    */     }
/* @93:    */   }
/* @94:    */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.BlockDb
 * JD-Core Version:    0.7.1
 */