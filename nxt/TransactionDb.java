/*   1:    */ package nxt;
/*   2:    */ 
/*   3:    */ import java.nio.ByteBuffer;
/*   4:    */ import java.nio.ByteOrder;
/*   5:    */ import java.sql.Connection;
/*   6:    */ import java.sql.PreparedStatement;
/*   7:    */ import java.sql.ResultSet;
/*   8:    */ import java.sql.SQLException;
/*   9:    */ import java.util.Iterator;
/*  10:    */ import java.util.List;
/*  11:    */ import nxt.db.DbUtils;
/*  12:    */ import nxt.util.Convert;
/*  13:    */ 
/*  14:    */ final class TransactionDb
/*  15:    */ {
/*  16:    */   /* Error */
/*  17:    */   static Transaction findTransaction(long paramLong)
/*  18:    */   {
/*  19:    */     // Byte code:
/*  20:    */     //   0: invokestatic 2	nxt/db/Db:getConnection	()Ljava/sql/Connection;
/*  21:    */     //   3: astore_2
/*  22:    */     //   4: aconst_null
/*  23:    */     //   5: astore_3
/*  24:    */     //   6: aload_2
/*  25:    */     //   7: ldc 3
/*  26:    */     //   9: invokeinterface 4 2 0
/*  27:    */     //   14: astore 4
/*  28:    */     //   16: aconst_null
/*  29:    */     //   17: astore 5
/*  30:    */     //   19: aload 4
/*  31:    */     //   21: iconst_1
/*  32:    */     //   22: lload_0
/*  33:    */     //   23: invokeinterface 5 4 0
/*  34:    */     //   28: aload 4
/*  35:    */     //   30: invokeinterface 6 1 0
/*  36:    */     //   35: astore 6
/*  37:    */     //   37: aconst_null
/*  38:    */     //   38: astore 7
/*  39:    */     //   40: aload 6
/*  40:    */     //   42: invokeinterface 7 1 0
/*  41:    */     //   47: ifeq +126 -> 173
/*  42:    */     //   50: aload_2
/*  43:    */     //   51: aload 6
/*  44:    */     //   53: invokestatic 8	nxt/TransactionDb:loadTransaction	(Ljava/sql/Connection;Ljava/sql/ResultSet;)Lnxt/TransactionImpl;
/*  45:    */     //   56: astore 8
/*  46:    */     //   58: aload 6
/*  47:    */     //   60: ifnull +37 -> 97
/*  48:    */     //   63: aload 7
/*  49:    */     //   65: ifnull +25 -> 90
/*  50:    */     //   68: aload 6
/*  51:    */     //   70: invokeinterface 9 1 0
/*  52:    */     //   75: goto +22 -> 97
/*  53:    */     //   78: astore 9
/*  54:    */     //   80: aload 7
/*  55:    */     //   82: aload 9
/*  56:    */     //   84: invokevirtual 11	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/*  57:    */     //   87: goto +10 -> 97
/*  58:    */     //   90: aload 6
/*  59:    */     //   92: invokeinterface 9 1 0
/*  60:    */     //   97: aload 4
/*  61:    */     //   99: ifnull +37 -> 136
/*  62:    */     //   102: aload 5
/*  63:    */     //   104: ifnull +25 -> 129
/*  64:    */     //   107: aload 4
/*  65:    */     //   109: invokeinterface 12 1 0
/*  66:    */     //   114: goto +22 -> 136
/*  67:    */     //   117: astore 9
/*  68:    */     //   119: aload 5
/*  69:    */     //   121: aload 9
/*  70:    */     //   123: invokevirtual 11	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/*  71:    */     //   126: goto +10 -> 136
/*  72:    */     //   129: aload 4
/*  73:    */     //   131: invokeinterface 12 1 0
/*  74:    */     //   136: aload_2
/*  75:    */     //   137: ifnull +33 -> 170
/*  76:    */     //   140: aload_3
/*  77:    */     //   141: ifnull +23 -> 164
/*  78:    */     //   144: aload_2
/*  79:    */     //   145: invokeinterface 13 1 0
/*  80:    */     //   150: goto +20 -> 170
/*  81:    */     //   153: astore 9
/*  82:    */     //   155: aload_3
/*  83:    */     //   156: aload 9
/*  84:    */     //   158: invokevirtual 11	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/*  85:    */     //   161: goto +9 -> 170
/*  86:    */     //   164: aload_2
/*  87:    */     //   165: invokeinterface 13 1 0
/*  88:    */     //   170: aload 8
/*  89:    */     //   172: areturn
/*  90:    */     //   173: aconst_null
/*  91:    */     //   174: astore 8
/*  92:    */     //   176: aload 6
/*  93:    */     //   178: ifnull +37 -> 215
/*  94:    */     //   181: aload 7
/*  95:    */     //   183: ifnull +25 -> 208
/*  96:    */     //   186: aload 6
/*  97:    */     //   188: invokeinterface 9 1 0
/*  98:    */     //   193: goto +22 -> 215
/*  99:    */     //   196: astore 9
/* 100:    */     //   198: aload 7
/* 101:    */     //   200: aload 9
/* 102:    */     //   202: invokevirtual 11	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 103:    */     //   205: goto +10 -> 215
/* 104:    */     //   208: aload 6
/* 105:    */     //   210: invokeinterface 9 1 0
/* 106:    */     //   215: aload 4
/* 107:    */     //   217: ifnull +37 -> 254
/* 108:    */     //   220: aload 5
/* 109:    */     //   222: ifnull +25 -> 247
/* 110:    */     //   225: aload 4
/* 111:    */     //   227: invokeinterface 12 1 0
/* 112:    */     //   232: goto +22 -> 254
/* 113:    */     //   235: astore 9
/* 114:    */     //   237: aload 5
/* 115:    */     //   239: aload 9
/* 116:    */     //   241: invokevirtual 11	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 117:    */     //   244: goto +10 -> 254
/* 118:    */     //   247: aload 4
/* 119:    */     //   249: invokeinterface 12 1 0
/* 120:    */     //   254: aload_2
/* 121:    */     //   255: ifnull +33 -> 288
/* 122:    */     //   258: aload_3
/* 123:    */     //   259: ifnull +23 -> 282
/* 124:    */     //   262: aload_2
/* 125:    */     //   263: invokeinterface 13 1 0
/* 126:    */     //   268: goto +20 -> 288
/* 127:    */     //   271: astore 9
/* 128:    */     //   273: aload_3
/* 129:    */     //   274: aload 9
/* 130:    */     //   276: invokevirtual 11	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 131:    */     //   279: goto +9 -> 288
/* 132:    */     //   282: aload_2
/* 133:    */     //   283: invokeinterface 13 1 0
/* 134:    */     //   288: aload 8
/* 135:    */     //   290: areturn
/* 136:    */     //   291: astore 8
/* 137:    */     //   293: aload 8
/* 138:    */     //   295: astore 7
/* 139:    */     //   297: aload 8
/* 140:    */     //   299: athrow
/* 141:    */     //   300: astore 10
/* 142:    */     //   302: aload 6
/* 143:    */     //   304: ifnull +37 -> 341
/* 144:    */     //   307: aload 7
/* 145:    */     //   309: ifnull +25 -> 334
/* 146:    */     //   312: aload 6
/* 147:    */     //   314: invokeinterface 9 1 0
/* 148:    */     //   319: goto +22 -> 341
/* 149:    */     //   322: astore 11
/* 150:    */     //   324: aload 7
/* 151:    */     //   326: aload 11
/* 152:    */     //   328: invokevirtual 11	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 153:    */     //   331: goto +10 -> 341
/* 154:    */     //   334: aload 6
/* 155:    */     //   336: invokeinterface 9 1 0
/* 156:    */     //   341: aload 10
/* 157:    */     //   343: athrow
/* 158:    */     //   344: astore 6
/* 159:    */     //   346: aload 6
/* 160:    */     //   348: astore 5
/* 161:    */     //   350: aload 6
/* 162:    */     //   352: athrow
/* 163:    */     //   353: astore 12
/* 164:    */     //   355: aload 4
/* 165:    */     //   357: ifnull +37 -> 394
/* 166:    */     //   360: aload 5
/* 167:    */     //   362: ifnull +25 -> 387
/* 168:    */     //   365: aload 4
/* 169:    */     //   367: invokeinterface 12 1 0
/* 170:    */     //   372: goto +22 -> 394
/* 171:    */     //   375: astore 13
/* 172:    */     //   377: aload 5
/* 173:    */     //   379: aload 13
/* 174:    */     //   381: invokevirtual 11	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 175:    */     //   384: goto +10 -> 394
/* 176:    */     //   387: aload 4
/* 177:    */     //   389: invokeinterface 12 1 0
/* 178:    */     //   394: aload 12
/* 179:    */     //   396: athrow
/* 180:    */     //   397: astore 4
/* 181:    */     //   399: aload 4
/* 182:    */     //   401: astore_3
/* 183:    */     //   402: aload 4
/* 184:    */     //   404: athrow
/* 185:    */     //   405: astore 14
/* 186:    */     //   407: aload_2
/* 187:    */     //   408: ifnull +33 -> 441
/* 188:    */     //   411: aload_3
/* 189:    */     //   412: ifnull +23 -> 435
/* 190:    */     //   415: aload_2
/* 191:    */     //   416: invokeinterface 13 1 0
/* 192:    */     //   421: goto +20 -> 441
/* 193:    */     //   424: astore 15
/* 194:    */     //   426: aload_3
/* 195:    */     //   427: aload 15
/* 196:    */     //   429: invokevirtual 11	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 197:    */     //   432: goto +9 -> 441
/* 198:    */     //   435: aload_2
/* 199:    */     //   436: invokeinterface 13 1 0
/* 200:    */     //   441: aload 14
/* 201:    */     //   443: athrow
/* 202:    */     //   444: astore_2
/* 203:    */     //   445: new 15	java/lang/RuntimeException
/* 204:    */     //   448: dup
/* 205:    */     //   449: aload_2
/* 206:    */     //   450: invokevirtual 16	java/sql/SQLException:toString	()Ljava/lang/String;
/* 207:    */     //   453: aload_2
/* 208:    */     //   454: invokespecial 17	java/lang/RuntimeException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
/* 209:    */     //   457: athrow
/* 210:    */     //   458: astore_2
/* 211:    */     //   459: new 15	java/lang/RuntimeException
/* 212:    */     //   462: dup
/* 213:    */     //   463: new 19	java/lang/StringBuilder
/* 214:    */     //   466: dup
/* 215:    */     //   467: invokespecial 20	java/lang/StringBuilder:<init>	()V
/* 216:    */     //   470: ldc 21
/* 217:    */     //   472: invokevirtual 22	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 218:    */     //   475: lload_0
/* 219:    */     //   476: invokevirtual 23	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
/* 220:    */     //   479: ldc 24
/* 221:    */     //   481: invokevirtual 22	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 222:    */     //   484: invokevirtual 25	java/lang/StringBuilder:toString	()Ljava/lang/String;
/* 223:    */     //   487: aload_2
/* 224:    */     //   488: invokespecial 17	java/lang/RuntimeException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
/* 225:    */     //   491: athrow
/* 226:    */     // Line number table:
/* 227:    */     //   Java source line #25	-> byte code offset #0
/* 228:    */     //   Java source line #26	-> byte code offset #6
/* 229:    */     //   Java source line #25	-> byte code offset #16
/* 230:    */     //   Java source line #27	-> byte code offset #19
/* 231:    */     //   Java source line #28	-> byte code offset #28
/* 232:    */     //   Java source line #29	-> byte code offset #40
/* 233:    */     //   Java source line #30	-> byte code offset #50
/* 234:    */     //   Java source line #33	-> byte code offset #58
/* 235:    */     //   Java source line #34	-> byte code offset #97
/* 236:    */     //   Java source line #32	-> byte code offset #173
/* 237:    */     //   Java source line #33	-> byte code offset #176
/* 238:    */     //   Java source line #34	-> byte code offset #215
/* 239:    */     //   Java source line #28	-> byte code offset #291
/* 240:    */     //   Java source line #33	-> byte code offset #300
/* 241:    */     //   Java source line #25	-> byte code offset #344
/* 242:    */     //   Java source line #34	-> byte code offset #353
/* 243:    */     //   Java source line #25	-> byte code offset #397
/* 244:    */     //   Java source line #34	-> byte code offset #405
/* 245:    */     //   Java source line #35	-> byte code offset #445
/* 246:    */     //   Java source line #36	-> byte code offset #458
/* 247:    */     //   Java source line #37	-> byte code offset #459
/* 248:    */     // Local variable table:
/* 249:    */     //   start	length	slot	name	signature
/* 250:    */     //   0	492	0	paramLong	long
/* 251:    */     //   3	433	2	localConnection	Connection
/* 252:    */     //   444	10	2	localSQLException	SQLException
/* 253:    */     //   458	30	2	localValidationException	NxtException.ValidationException
/* 254:    */     //   5	422	3	localObject1	Object
/* 255:    */     //   14	374	4	localPreparedStatement	PreparedStatement
/* 256:    */     //   397	6	4	localThrowable1	Throwable
/* 257:    */     //   17	361	5	localObject2	Object
/* 258:    */     //   35	300	6	localResultSet	ResultSet
/* 259:    */     //   344	7	6	localThrowable2	Throwable
/* 260:    */     //   38	287	7	localObject3	Object
/* 261:    */     //   56	233	8	localTransactionImpl	TransactionImpl
/* 262:    */     //   291	7	8	localThrowable3	Throwable
/* 263:    */     //   78	5	9	localThrowable4	Throwable
/* 264:    */     //   117	5	9	localThrowable5	Throwable
/* 265:    */     //   153	4	9	localThrowable6	Throwable
/* 266:    */     //   196	5	9	localThrowable7	Throwable
/* 267:    */     //   235	5	9	localThrowable8	Throwable
/* 268:    */     //   271	4	9	localThrowable9	Throwable
/* 269:    */     //   300	42	10	localObject4	Object
/* 270:    */     //   322	5	11	localThrowable10	Throwable
/* 271:    */     //   353	42	12	localObject5	Object
/* 272:    */     //   375	5	13	localThrowable11	Throwable
/* 273:    */     //   405	37	14	localObject6	Object
/* 274:    */     //   424	4	15	localThrowable12	Throwable
/* 275:    */     // Exception table:
/* 276:    */     //   from	to	target	type
/* 277:    */     //   68	75	78	java/lang/Throwable
/* 278:    */     //   107	114	117	java/lang/Throwable
/* 279:    */     //   144	150	153	java/lang/Throwable
/* 280:    */     //   186	193	196	java/lang/Throwable
/* 281:    */     //   225	232	235	java/lang/Throwable
/* 282:    */     //   262	268	271	java/lang/Throwable
/* 283:    */     //   40	58	291	java/lang/Throwable
/* 284:    */     //   173	176	291	java/lang/Throwable
/* 285:    */     //   40	58	300	finally
/* 286:    */     //   173	176	300	finally
/* 287:    */     //   291	302	300	finally
/* 288:    */     //   312	319	322	java/lang/Throwable
/* 289:    */     //   19	97	344	java/lang/Throwable
/* 290:    */     //   173	215	344	java/lang/Throwable
/* 291:    */     //   291	344	344	java/lang/Throwable
/* 292:    */     //   19	97	353	finally
/* 293:    */     //   173	215	353	finally
/* 294:    */     //   291	355	353	finally
/* 295:    */     //   365	372	375	java/lang/Throwable
/* 296:    */     //   6	136	397	java/lang/Throwable
/* 297:    */     //   173	254	397	java/lang/Throwable
/* 298:    */     //   291	397	397	java/lang/Throwable
/* 299:    */     //   6	136	405	finally
/* 300:    */     //   173	254	405	finally
/* 301:    */     //   291	407	405	finally
/* 302:    */     //   415	421	424	java/lang/Throwable
/* 303:    */     //   0	170	444	java/sql/SQLException
/* 304:    */     //   173	288	444	java/sql/SQLException
/* 305:    */     //   291	444	444	java/sql/SQLException
/* 306:    */     //   0	170	458	nxt/NxtException$ValidationException
/* 307:    */     //   173	288	458	nxt/NxtException$ValidationException
/* 308:    */     //   291	444	458	nxt/NxtException$ValidationException
/* 309:    */   }
/* 310:    */   
/* 311:    */   /* Error */
/* 312:    */   static Transaction findTransactionByFullHash(String paramString)
/* 313:    */   {
/* 314:    */     // Byte code:
/* 315:    */     //   0: invokestatic 2	nxt/db/Db:getConnection	()Ljava/sql/Connection;
/* 316:    */     //   3: astore_1
/* 317:    */     //   4: aconst_null
/* 318:    */     //   5: astore_2
/* 319:    */     //   6: aload_1
/* 320:    */     //   7: ldc 26
/* 321:    */     //   9: invokeinterface 4 2 0
/* 322:    */     //   14: astore_3
/* 323:    */     //   15: aconst_null
/* 324:    */     //   16: astore 4
/* 325:    */     //   18: aload_3
/* 326:    */     //   19: iconst_1
/* 327:    */     //   20: aload_0
/* 328:    */     //   21: invokestatic 27	nxt/util/Convert:parseHexString	(Ljava/lang/String;)[B
/* 329:    */     //   24: invokeinterface 28 3 0
/* 330:    */     //   29: aload_3
/* 331:    */     //   30: invokeinterface 6 1 0
/* 332:    */     //   35: astore 5
/* 333:    */     //   37: aconst_null
/* 334:    */     //   38: astore 6
/* 335:    */     //   40: aload 5
/* 336:    */     //   42: invokeinterface 7 1 0
/* 337:    */     //   47: ifeq +123 -> 170
/* 338:    */     //   50: aload_1
/* 339:    */     //   51: aload 5
/* 340:    */     //   53: invokestatic 8	nxt/TransactionDb:loadTransaction	(Ljava/sql/Connection;Ljava/sql/ResultSet;)Lnxt/TransactionImpl;
/* 341:    */     //   56: astore 7
/* 342:    */     //   58: aload 5
/* 343:    */     //   60: ifnull +37 -> 97
/* 344:    */     //   63: aload 6
/* 345:    */     //   65: ifnull +25 -> 90
/* 346:    */     //   68: aload 5
/* 347:    */     //   70: invokeinterface 9 1 0
/* 348:    */     //   75: goto +22 -> 97
/* 349:    */     //   78: astore 8
/* 350:    */     //   80: aload 6
/* 351:    */     //   82: aload 8
/* 352:    */     //   84: invokevirtual 11	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 353:    */     //   87: goto +10 -> 97
/* 354:    */     //   90: aload 5
/* 355:    */     //   92: invokeinterface 9 1 0
/* 356:    */     //   97: aload_3
/* 357:    */     //   98: ifnull +35 -> 133
/* 358:    */     //   101: aload 4
/* 359:    */     //   103: ifnull +24 -> 127
/* 360:    */     //   106: aload_3
/* 361:    */     //   107: invokeinterface 12 1 0
/* 362:    */     //   112: goto +21 -> 133
/* 363:    */     //   115: astore 8
/* 364:    */     //   117: aload 4
/* 365:    */     //   119: aload 8
/* 366:    */     //   121: invokevirtual 11	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 367:    */     //   124: goto +9 -> 133
/* 368:    */     //   127: aload_3
/* 369:    */     //   128: invokeinterface 12 1 0
/* 370:    */     //   133: aload_1
/* 371:    */     //   134: ifnull +33 -> 167
/* 372:    */     //   137: aload_2
/* 373:    */     //   138: ifnull +23 -> 161
/* 374:    */     //   141: aload_1
/* 375:    */     //   142: invokeinterface 13 1 0
/* 376:    */     //   147: goto +20 -> 167
/* 377:    */     //   150: astore 8
/* 378:    */     //   152: aload_2
/* 379:    */     //   153: aload 8
/* 380:    */     //   155: invokevirtual 11	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 381:    */     //   158: goto +9 -> 167
/* 382:    */     //   161: aload_1
/* 383:    */     //   162: invokeinterface 13 1 0
/* 384:    */     //   167: aload 7
/* 385:    */     //   169: areturn
/* 386:    */     //   170: aconst_null
/* 387:    */     //   171: astore 7
/* 388:    */     //   173: aload 5
/* 389:    */     //   175: ifnull +37 -> 212
/* 390:    */     //   178: aload 6
/* 391:    */     //   180: ifnull +25 -> 205
/* 392:    */     //   183: aload 5
/* 393:    */     //   185: invokeinterface 9 1 0
/* 394:    */     //   190: goto +22 -> 212
/* 395:    */     //   193: astore 8
/* 396:    */     //   195: aload 6
/* 397:    */     //   197: aload 8
/* 398:    */     //   199: invokevirtual 11	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 399:    */     //   202: goto +10 -> 212
/* 400:    */     //   205: aload 5
/* 401:    */     //   207: invokeinterface 9 1 0
/* 402:    */     //   212: aload_3
/* 403:    */     //   213: ifnull +35 -> 248
/* 404:    */     //   216: aload 4
/* 405:    */     //   218: ifnull +24 -> 242
/* 406:    */     //   221: aload_3
/* 407:    */     //   222: invokeinterface 12 1 0
/* 408:    */     //   227: goto +21 -> 248
/* 409:    */     //   230: astore 8
/* 410:    */     //   232: aload 4
/* 411:    */     //   234: aload 8
/* 412:    */     //   236: invokevirtual 11	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 413:    */     //   239: goto +9 -> 248
/* 414:    */     //   242: aload_3
/* 415:    */     //   243: invokeinterface 12 1 0
/* 416:    */     //   248: aload_1
/* 417:    */     //   249: ifnull +33 -> 282
/* 418:    */     //   252: aload_2
/* 419:    */     //   253: ifnull +23 -> 276
/* 420:    */     //   256: aload_1
/* 421:    */     //   257: invokeinterface 13 1 0
/* 422:    */     //   262: goto +20 -> 282
/* 423:    */     //   265: astore 8
/* 424:    */     //   267: aload_2
/* 425:    */     //   268: aload 8
/* 426:    */     //   270: invokevirtual 11	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 427:    */     //   273: goto +9 -> 282
/* 428:    */     //   276: aload_1
/* 429:    */     //   277: invokeinterface 13 1 0
/* 430:    */     //   282: aload 7
/* 431:    */     //   284: areturn
/* 432:    */     //   285: astore 7
/* 433:    */     //   287: aload 7
/* 434:    */     //   289: astore 6
/* 435:    */     //   291: aload 7
/* 436:    */     //   293: athrow
/* 437:    */     //   294: astore 9
/* 438:    */     //   296: aload 5
/* 439:    */     //   298: ifnull +37 -> 335
/* 440:    */     //   301: aload 6
/* 441:    */     //   303: ifnull +25 -> 328
/* 442:    */     //   306: aload 5
/* 443:    */     //   308: invokeinterface 9 1 0
/* 444:    */     //   313: goto +22 -> 335
/* 445:    */     //   316: astore 10
/* 446:    */     //   318: aload 6
/* 447:    */     //   320: aload 10
/* 448:    */     //   322: invokevirtual 11	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 449:    */     //   325: goto +10 -> 335
/* 450:    */     //   328: aload 5
/* 451:    */     //   330: invokeinterface 9 1 0
/* 452:    */     //   335: aload 9
/* 453:    */     //   337: athrow
/* 454:    */     //   338: astore 5
/* 455:    */     //   340: aload 5
/* 456:    */     //   342: astore 4
/* 457:    */     //   344: aload 5
/* 458:    */     //   346: athrow
/* 459:    */     //   347: astore 11
/* 460:    */     //   349: aload_3
/* 461:    */     //   350: ifnull +35 -> 385
/* 462:    */     //   353: aload 4
/* 463:    */     //   355: ifnull +24 -> 379
/* 464:    */     //   358: aload_3
/* 465:    */     //   359: invokeinterface 12 1 0
/* 466:    */     //   364: goto +21 -> 385
/* 467:    */     //   367: astore 12
/* 468:    */     //   369: aload 4
/* 469:    */     //   371: aload 12
/* 470:    */     //   373: invokevirtual 11	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 471:    */     //   376: goto +9 -> 385
/* 472:    */     //   379: aload_3
/* 473:    */     //   380: invokeinterface 12 1 0
/* 474:    */     //   385: aload 11
/* 475:    */     //   387: athrow
/* 476:    */     //   388: astore_3
/* 477:    */     //   389: aload_3
/* 478:    */     //   390: astore_2
/* 479:    */     //   391: aload_3
/* 480:    */     //   392: athrow
/* 481:    */     //   393: astore 13
/* 482:    */     //   395: aload_1
/* 483:    */     //   396: ifnull +33 -> 429
/* 484:    */     //   399: aload_2
/* 485:    */     //   400: ifnull +23 -> 423
/* 486:    */     //   403: aload_1
/* 487:    */     //   404: invokeinterface 13 1 0
/* 488:    */     //   409: goto +20 -> 429
/* 489:    */     //   412: astore 14
/* 490:    */     //   414: aload_2
/* 491:    */     //   415: aload 14
/* 492:    */     //   417: invokevirtual 11	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 493:    */     //   420: goto +9 -> 429
/* 494:    */     //   423: aload_1
/* 495:    */     //   424: invokeinterface 13 1 0
/* 496:    */     //   429: aload 13
/* 497:    */     //   431: athrow
/* 498:    */     //   432: astore_1
/* 499:    */     //   433: new 15	java/lang/RuntimeException
/* 500:    */     //   436: dup
/* 501:    */     //   437: aload_1
/* 502:    */     //   438: invokevirtual 16	java/sql/SQLException:toString	()Ljava/lang/String;
/* 503:    */     //   441: aload_1
/* 504:    */     //   442: invokespecial 17	java/lang/RuntimeException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
/* 505:    */     //   445: athrow
/* 506:    */     //   446: astore_1
/* 507:    */     //   447: new 15	java/lang/RuntimeException
/* 508:    */     //   450: dup
/* 509:    */     //   451: new 19	java/lang/StringBuilder
/* 510:    */     //   454: dup
/* 511:    */     //   455: invokespecial 20	java/lang/StringBuilder:<init>	()V
/* 512:    */     //   458: ldc 29
/* 513:    */     //   460: invokevirtual 22	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 514:    */     //   463: aload_0
/* 515:    */     //   464: invokevirtual 22	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 516:    */     //   467: ldc 24
/* 517:    */     //   469: invokevirtual 22	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 518:    */     //   472: invokevirtual 25	java/lang/StringBuilder:toString	()Ljava/lang/String;
/* 519:    */     //   475: aload_1
/* 520:    */     //   476: invokespecial 17	java/lang/RuntimeException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
/* 521:    */     //   479: athrow
/* 522:    */     // Line number table:
/* 523:    */     //   Java source line #42	-> byte code offset #0
/* 524:    */     //   Java source line #43	-> byte code offset #6
/* 525:    */     //   Java source line #42	-> byte code offset #15
/* 526:    */     //   Java source line #44	-> byte code offset #18
/* 527:    */     //   Java source line #45	-> byte code offset #29
/* 528:    */     //   Java source line #46	-> byte code offset #40
/* 529:    */     //   Java source line #47	-> byte code offset #50
/* 530:    */     //   Java source line #50	-> byte code offset #58
/* 531:    */     //   Java source line #51	-> byte code offset #97
/* 532:    */     //   Java source line #49	-> byte code offset #170
/* 533:    */     //   Java source line #50	-> byte code offset #173
/* 534:    */     //   Java source line #51	-> byte code offset #212
/* 535:    */     //   Java source line #45	-> byte code offset #285
/* 536:    */     //   Java source line #50	-> byte code offset #294
/* 537:    */     //   Java source line #42	-> byte code offset #338
/* 538:    */     //   Java source line #51	-> byte code offset #347
/* 539:    */     //   Java source line #42	-> byte code offset #388
/* 540:    */     //   Java source line #51	-> byte code offset #393
/* 541:    */     //   Java source line #52	-> byte code offset #433
/* 542:    */     //   Java source line #53	-> byte code offset #446
/* 543:    */     //   Java source line #54	-> byte code offset #447
/* 544:    */     // Local variable table:
/* 545:    */     //   start	length	slot	name	signature
/* 546:    */     //   0	480	0	paramString	String
/* 547:    */     //   3	421	1	localConnection	Connection
/* 548:    */     //   432	10	1	localSQLException	SQLException
/* 549:    */     //   446	30	1	localValidationException	NxtException.ValidationException
/* 550:    */     //   5	410	2	localObject1	Object
/* 551:    */     //   14	366	3	localPreparedStatement	PreparedStatement
/* 552:    */     //   388	4	3	localThrowable1	Throwable
/* 553:    */     //   16	354	4	localObject2	Object
/* 554:    */     //   35	294	5	localResultSet	ResultSet
/* 555:    */     //   338	7	5	localThrowable2	Throwable
/* 556:    */     //   38	281	6	localObject3	Object
/* 557:    */     //   56	227	7	localTransactionImpl	TransactionImpl
/* 558:    */     //   285	7	7	localThrowable3	Throwable
/* 559:    */     //   78	5	8	localThrowable4	Throwable
/* 560:    */     //   115	5	8	localThrowable5	Throwable
/* 561:    */     //   150	4	8	localThrowable6	Throwable
/* 562:    */     //   193	5	8	localThrowable7	Throwable
/* 563:    */     //   230	5	8	localThrowable8	Throwable
/* 564:    */     //   265	4	8	localThrowable9	Throwable
/* 565:    */     //   294	42	9	localObject4	Object
/* 566:    */     //   316	5	10	localThrowable10	Throwable
/* 567:    */     //   347	39	11	localObject5	Object
/* 568:    */     //   367	5	12	localThrowable11	Throwable
/* 569:    */     //   393	37	13	localObject6	Object
/* 570:    */     //   412	4	14	localThrowable12	Throwable
/* 571:    */     // Exception table:
/* 572:    */     //   from	to	target	type
/* 573:    */     //   68	75	78	java/lang/Throwable
/* 574:    */     //   106	112	115	java/lang/Throwable
/* 575:    */     //   141	147	150	java/lang/Throwable
/* 576:    */     //   183	190	193	java/lang/Throwable
/* 577:    */     //   221	227	230	java/lang/Throwable
/* 578:    */     //   256	262	265	java/lang/Throwable
/* 579:    */     //   40	58	285	java/lang/Throwable
/* 580:    */     //   170	173	285	java/lang/Throwable
/* 581:    */     //   40	58	294	finally
/* 582:    */     //   170	173	294	finally
/* 583:    */     //   285	296	294	finally
/* 584:    */     //   306	313	316	java/lang/Throwable
/* 585:    */     //   18	97	338	java/lang/Throwable
/* 586:    */     //   170	212	338	java/lang/Throwable
/* 587:    */     //   285	338	338	java/lang/Throwable
/* 588:    */     //   18	97	347	finally
/* 589:    */     //   170	212	347	finally
/* 590:    */     //   285	349	347	finally
/* 591:    */     //   358	364	367	java/lang/Throwable
/* 592:    */     //   6	133	388	java/lang/Throwable
/* 593:    */     //   170	248	388	java/lang/Throwable
/* 594:    */     //   285	388	388	java/lang/Throwable
/* 595:    */     //   6	133	393	finally
/* 596:    */     //   170	248	393	finally
/* 597:    */     //   285	395	393	finally
/* 598:    */     //   403	409	412	java/lang/Throwable
/* 599:    */     //   0	167	432	java/sql/SQLException
/* 600:    */     //   170	282	432	java/sql/SQLException
/* 601:    */     //   285	432	432	java/sql/SQLException
/* 602:    */     //   0	167	446	nxt/NxtException$ValidationException
/* 603:    */     //   170	282	446	nxt/NxtException$ValidationException
/* 604:    */     //   285	432	446	nxt/NxtException$ValidationException
/* 605:    */   }
/* 606:    */   
/* 607:    */   /* Error */
/* 608:    */   static boolean hasTransaction(long paramLong)
/* 609:    */   {
/* 610:    */     // Byte code:
/* 611:    */     //   0: invokestatic 2	nxt/db/Db:getConnection	()Ljava/sql/Connection;
/* 612:    */     //   3: astore_2
/* 613:    */     //   4: aconst_null
/* 614:    */     //   5: astore_3
/* 615:    */     //   6: aload_2
/* 616:    */     //   7: ldc 30
/* 617:    */     //   9: invokeinterface 4 2 0
/* 618:    */     //   14: astore 4
/* 619:    */     //   16: aconst_null
/* 620:    */     //   17: astore 5
/* 621:    */     //   19: aload 4
/* 622:    */     //   21: iconst_1
/* 623:    */     //   22: lload_0
/* 624:    */     //   23: invokeinterface 5 4 0
/* 625:    */     //   28: aload 4
/* 626:    */     //   30: invokeinterface 6 1 0
/* 627:    */     //   35: astore 6
/* 628:    */     //   37: aconst_null
/* 629:    */     //   38: astore 7
/* 630:    */     //   40: aload 6
/* 631:    */     //   42: invokeinterface 7 1 0
/* 632:    */     //   47: istore 8
/* 633:    */     //   49: aload 6
/* 634:    */     //   51: ifnull +37 -> 88
/* 635:    */     //   54: aload 7
/* 636:    */     //   56: ifnull +25 -> 81
/* 637:    */     //   59: aload 6
/* 638:    */     //   61: invokeinterface 9 1 0
/* 639:    */     //   66: goto +22 -> 88
/* 640:    */     //   69: astore 9
/* 641:    */     //   71: aload 7
/* 642:    */     //   73: aload 9
/* 643:    */     //   75: invokevirtual 11	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 644:    */     //   78: goto +10 -> 88
/* 645:    */     //   81: aload 6
/* 646:    */     //   83: invokeinterface 9 1 0
/* 647:    */     //   88: aload 4
/* 648:    */     //   90: ifnull +37 -> 127
/* 649:    */     //   93: aload 5
/* 650:    */     //   95: ifnull +25 -> 120
/* 651:    */     //   98: aload 4
/* 652:    */     //   100: invokeinterface 12 1 0
/* 653:    */     //   105: goto +22 -> 127
/* 654:    */     //   108: astore 9
/* 655:    */     //   110: aload 5
/* 656:    */     //   112: aload 9
/* 657:    */     //   114: invokevirtual 11	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 658:    */     //   117: goto +10 -> 127
/* 659:    */     //   120: aload 4
/* 660:    */     //   122: invokeinterface 12 1 0
/* 661:    */     //   127: aload_2
/* 662:    */     //   128: ifnull +33 -> 161
/* 663:    */     //   131: aload_3
/* 664:    */     //   132: ifnull +23 -> 155
/* 665:    */     //   135: aload_2
/* 666:    */     //   136: invokeinterface 13 1 0
/* 667:    */     //   141: goto +20 -> 161
/* 668:    */     //   144: astore 9
/* 669:    */     //   146: aload_3
/* 670:    */     //   147: aload 9
/* 671:    */     //   149: invokevirtual 11	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 672:    */     //   152: goto +9 -> 161
/* 673:    */     //   155: aload_2
/* 674:    */     //   156: invokeinterface 13 1 0
/* 675:    */     //   161: iload 8
/* 676:    */     //   163: ireturn
/* 677:    */     //   164: astore 8
/* 678:    */     //   166: aload 8
/* 679:    */     //   168: astore 7
/* 680:    */     //   170: aload 8
/* 681:    */     //   172: athrow
/* 682:    */     //   173: astore 10
/* 683:    */     //   175: aload 6
/* 684:    */     //   177: ifnull +37 -> 214
/* 685:    */     //   180: aload 7
/* 686:    */     //   182: ifnull +25 -> 207
/* 687:    */     //   185: aload 6
/* 688:    */     //   187: invokeinterface 9 1 0
/* 689:    */     //   192: goto +22 -> 214
/* 690:    */     //   195: astore 11
/* 691:    */     //   197: aload 7
/* 692:    */     //   199: aload 11
/* 693:    */     //   201: invokevirtual 11	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 694:    */     //   204: goto +10 -> 214
/* 695:    */     //   207: aload 6
/* 696:    */     //   209: invokeinterface 9 1 0
/* 697:    */     //   214: aload 10
/* 698:    */     //   216: athrow
/* 699:    */     //   217: astore 6
/* 700:    */     //   219: aload 6
/* 701:    */     //   221: astore 5
/* 702:    */     //   223: aload 6
/* 703:    */     //   225: athrow
/* 704:    */     //   226: astore 12
/* 705:    */     //   228: aload 4
/* 706:    */     //   230: ifnull +37 -> 267
/* 707:    */     //   233: aload 5
/* 708:    */     //   235: ifnull +25 -> 260
/* 709:    */     //   238: aload 4
/* 710:    */     //   240: invokeinterface 12 1 0
/* 711:    */     //   245: goto +22 -> 267
/* 712:    */     //   248: astore 13
/* 713:    */     //   250: aload 5
/* 714:    */     //   252: aload 13
/* 715:    */     //   254: invokevirtual 11	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 716:    */     //   257: goto +10 -> 267
/* 717:    */     //   260: aload 4
/* 718:    */     //   262: invokeinterface 12 1 0
/* 719:    */     //   267: aload 12
/* 720:    */     //   269: athrow
/* 721:    */     //   270: astore 4
/* 722:    */     //   272: aload 4
/* 723:    */     //   274: astore_3
/* 724:    */     //   275: aload 4
/* 725:    */     //   277: athrow
/* 726:    */     //   278: astore 14
/* 727:    */     //   280: aload_2
/* 728:    */     //   281: ifnull +33 -> 314
/* 729:    */     //   284: aload_3
/* 730:    */     //   285: ifnull +23 -> 308
/* 731:    */     //   288: aload_2
/* 732:    */     //   289: invokeinterface 13 1 0
/* 733:    */     //   294: goto +20 -> 314
/* 734:    */     //   297: astore 15
/* 735:    */     //   299: aload_3
/* 736:    */     //   300: aload 15
/* 737:    */     //   302: invokevirtual 11	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 738:    */     //   305: goto +9 -> 314
/* 739:    */     //   308: aload_2
/* 740:    */     //   309: invokeinterface 13 1 0
/* 741:    */     //   314: aload 14
/* 742:    */     //   316: athrow
/* 743:    */     //   317: astore_2
/* 744:    */     //   318: new 15	java/lang/RuntimeException
/* 745:    */     //   321: dup
/* 746:    */     //   322: aload_2
/* 747:    */     //   323: invokevirtual 16	java/sql/SQLException:toString	()Ljava/lang/String;
/* 748:    */     //   326: aload_2
/* 749:    */     //   327: invokespecial 17	java/lang/RuntimeException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
/* 750:    */     //   330: athrow
/* 751:    */     // Line number table:
/* 752:    */     //   Java source line #59	-> byte code offset #0
/* 753:    */     //   Java source line #60	-> byte code offset #6
/* 754:    */     //   Java source line #59	-> byte code offset #16
/* 755:    */     //   Java source line #61	-> byte code offset #19
/* 756:    */     //   Java source line #62	-> byte code offset #28
/* 757:    */     //   Java source line #63	-> byte code offset #40
/* 758:    */     //   Java source line #64	-> byte code offset #49
/* 759:    */     //   Java source line #65	-> byte code offset #88
/* 760:    */     //   Java source line #62	-> byte code offset #164
/* 761:    */     //   Java source line #64	-> byte code offset #173
/* 762:    */     //   Java source line #59	-> byte code offset #217
/* 763:    */     //   Java source line #65	-> byte code offset #226
/* 764:    */     //   Java source line #59	-> byte code offset #270
/* 765:    */     //   Java source line #65	-> byte code offset #278
/* 766:    */     //   Java source line #66	-> byte code offset #318
/* 767:    */     // Local variable table:
/* 768:    */     //   start	length	slot	name	signature
/* 769:    */     //   0	331	0	paramLong	long
/* 770:    */     //   3	306	2	localConnection	Connection
/* 771:    */     //   317	10	2	localSQLException	SQLException
/* 772:    */     //   5	295	3	localObject1	Object
/* 773:    */     //   14	247	4	localPreparedStatement	PreparedStatement
/* 774:    */     //   270	6	4	localThrowable1	Throwable
/* 775:    */     //   17	234	5	localObject2	Object
/* 776:    */     //   35	173	6	localResultSet	ResultSet
/* 777:    */     //   217	7	6	localThrowable2	Throwable
/* 778:    */     //   38	160	7	localObject3	Object
/* 779:    */     //   47	115	8	bool	boolean
/* 780:    */     //   164	7	8	localThrowable3	Throwable
/* 781:    */     //   69	5	9	localThrowable4	Throwable
/* 782:    */     //   108	5	9	localThrowable5	Throwable
/* 783:    */     //   144	4	9	localThrowable6	Throwable
/* 784:    */     //   173	42	10	localObject4	Object
/* 785:    */     //   195	5	11	localThrowable7	Throwable
/* 786:    */     //   226	42	12	localObject5	Object
/* 787:    */     //   248	5	13	localThrowable8	Throwable
/* 788:    */     //   278	37	14	localObject6	Object
/* 789:    */     //   297	4	15	localThrowable9	Throwable
/* 790:    */     // Exception table:
/* 791:    */     //   from	to	target	type
/* 792:    */     //   59	66	69	java/lang/Throwable
/* 793:    */     //   98	105	108	java/lang/Throwable
/* 794:    */     //   135	141	144	java/lang/Throwable
/* 795:    */     //   40	49	164	java/lang/Throwable
/* 796:    */     //   40	49	173	finally
/* 797:    */     //   164	175	173	finally
/* 798:    */     //   185	192	195	java/lang/Throwable
/* 799:    */     //   19	88	217	java/lang/Throwable
/* 800:    */     //   164	217	217	java/lang/Throwable
/* 801:    */     //   19	88	226	finally
/* 802:    */     //   164	228	226	finally
/* 803:    */     //   238	245	248	java/lang/Throwable
/* 804:    */     //   6	127	270	java/lang/Throwable
/* 805:    */     //   164	270	270	java/lang/Throwable
/* 806:    */     //   6	127	278	finally
/* 807:    */     //   164	280	278	finally
/* 808:    */     //   288	294	297	java/lang/Throwable
/* 809:    */     //   0	161	317	java/sql/SQLException
/* 810:    */     //   164	317	317	java/sql/SQLException
/* 811:    */   }
/* 812:    */   
/* 813:    */   /* Error */
/* 814:    */   static boolean hasTransactionByFullHash(String paramString)
/* 815:    */   {
/* 816:    */     // Byte code:
/* 817:    */     //   0: invokestatic 2	nxt/db/Db:getConnection	()Ljava/sql/Connection;
/* 818:    */     //   3: astore_1
/* 819:    */     //   4: aconst_null
/* 820:    */     //   5: astore_2
/* 821:    */     //   6: aload_1
/* 822:    */     //   7: ldc 31
/* 823:    */     //   9: invokeinterface 4 2 0
/* 824:    */     //   14: astore_3
/* 825:    */     //   15: aconst_null
/* 826:    */     //   16: astore 4
/* 827:    */     //   18: aload_3
/* 828:    */     //   19: iconst_1
/* 829:    */     //   20: aload_0
/* 830:    */     //   21: invokestatic 27	nxt/util/Convert:parseHexString	(Ljava/lang/String;)[B
/* 831:    */     //   24: invokeinterface 28 3 0
/* 832:    */     //   29: aload_3
/* 833:    */     //   30: invokeinterface 6 1 0
/* 834:    */     //   35: astore 5
/* 835:    */     //   37: aconst_null
/* 836:    */     //   38: astore 6
/* 837:    */     //   40: aload 5
/* 838:    */     //   42: invokeinterface 7 1 0
/* 839:    */     //   47: istore 7
/* 840:    */     //   49: aload 5
/* 841:    */     //   51: ifnull +37 -> 88
/* 842:    */     //   54: aload 6
/* 843:    */     //   56: ifnull +25 -> 81
/* 844:    */     //   59: aload 5
/* 845:    */     //   61: invokeinterface 9 1 0
/* 846:    */     //   66: goto +22 -> 88
/* 847:    */     //   69: astore 8
/* 848:    */     //   71: aload 6
/* 849:    */     //   73: aload 8
/* 850:    */     //   75: invokevirtual 11	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 851:    */     //   78: goto +10 -> 88
/* 852:    */     //   81: aload 5
/* 853:    */     //   83: invokeinterface 9 1 0
/* 854:    */     //   88: aload_3
/* 855:    */     //   89: ifnull +35 -> 124
/* 856:    */     //   92: aload 4
/* 857:    */     //   94: ifnull +24 -> 118
/* 858:    */     //   97: aload_3
/* 859:    */     //   98: invokeinterface 12 1 0
/* 860:    */     //   103: goto +21 -> 124
/* 861:    */     //   106: astore 8
/* 862:    */     //   108: aload 4
/* 863:    */     //   110: aload 8
/* 864:    */     //   112: invokevirtual 11	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 865:    */     //   115: goto +9 -> 124
/* 866:    */     //   118: aload_3
/* 867:    */     //   119: invokeinterface 12 1 0
/* 868:    */     //   124: aload_1
/* 869:    */     //   125: ifnull +33 -> 158
/* 870:    */     //   128: aload_2
/* 871:    */     //   129: ifnull +23 -> 152
/* 872:    */     //   132: aload_1
/* 873:    */     //   133: invokeinterface 13 1 0
/* 874:    */     //   138: goto +20 -> 158
/* 875:    */     //   141: astore 8
/* 876:    */     //   143: aload_2
/* 877:    */     //   144: aload 8
/* 878:    */     //   146: invokevirtual 11	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 879:    */     //   149: goto +9 -> 158
/* 880:    */     //   152: aload_1
/* 881:    */     //   153: invokeinterface 13 1 0
/* 882:    */     //   158: iload 7
/* 883:    */     //   160: ireturn
/* 884:    */     //   161: astore 7
/* 885:    */     //   163: aload 7
/* 886:    */     //   165: astore 6
/* 887:    */     //   167: aload 7
/* 888:    */     //   169: athrow
/* 889:    */     //   170: astore 9
/* 890:    */     //   172: aload 5
/* 891:    */     //   174: ifnull +37 -> 211
/* 892:    */     //   177: aload 6
/* 893:    */     //   179: ifnull +25 -> 204
/* 894:    */     //   182: aload 5
/* 895:    */     //   184: invokeinterface 9 1 0
/* 896:    */     //   189: goto +22 -> 211
/* 897:    */     //   192: astore 10
/* 898:    */     //   194: aload 6
/* 899:    */     //   196: aload 10
/* 900:    */     //   198: invokevirtual 11	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 901:    */     //   201: goto +10 -> 211
/* 902:    */     //   204: aload 5
/* 903:    */     //   206: invokeinterface 9 1 0
/* 904:    */     //   211: aload 9
/* 905:    */     //   213: athrow
/* 906:    */     //   214: astore 5
/* 907:    */     //   216: aload 5
/* 908:    */     //   218: astore 4
/* 909:    */     //   220: aload 5
/* 910:    */     //   222: athrow
/* 911:    */     //   223: astore 11
/* 912:    */     //   225: aload_3
/* 913:    */     //   226: ifnull +35 -> 261
/* 914:    */     //   229: aload 4
/* 915:    */     //   231: ifnull +24 -> 255
/* 916:    */     //   234: aload_3
/* 917:    */     //   235: invokeinterface 12 1 0
/* 918:    */     //   240: goto +21 -> 261
/* 919:    */     //   243: astore 12
/* 920:    */     //   245: aload 4
/* 921:    */     //   247: aload 12
/* 922:    */     //   249: invokevirtual 11	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 923:    */     //   252: goto +9 -> 261
/* 924:    */     //   255: aload_3
/* 925:    */     //   256: invokeinterface 12 1 0
/* 926:    */     //   261: aload 11
/* 927:    */     //   263: athrow
/* 928:    */     //   264: astore_3
/* 929:    */     //   265: aload_3
/* 930:    */     //   266: astore_2
/* 931:    */     //   267: aload_3
/* 932:    */     //   268: athrow
/* 933:    */     //   269: astore 13
/* 934:    */     //   271: aload_1
/* 935:    */     //   272: ifnull +33 -> 305
/* 936:    */     //   275: aload_2
/* 937:    */     //   276: ifnull +23 -> 299
/* 938:    */     //   279: aload_1
/* 939:    */     //   280: invokeinterface 13 1 0
/* 940:    */     //   285: goto +20 -> 305
/* 941:    */     //   288: astore 14
/* 942:    */     //   290: aload_2
/* 943:    */     //   291: aload 14
/* 944:    */     //   293: invokevirtual 11	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 945:    */     //   296: goto +9 -> 305
/* 946:    */     //   299: aload_1
/* 947:    */     //   300: invokeinterface 13 1 0
/* 948:    */     //   305: aload 13
/* 949:    */     //   307: athrow
/* 950:    */     //   308: astore_1
/* 951:    */     //   309: new 15	java/lang/RuntimeException
/* 952:    */     //   312: dup
/* 953:    */     //   313: aload_1
/* 954:    */     //   314: invokevirtual 16	java/sql/SQLException:toString	()Ljava/lang/String;
/* 955:    */     //   317: aload_1
/* 956:    */     //   318: invokespecial 17	java/lang/RuntimeException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
/* 957:    */     //   321: athrow
/* 958:    */     // Line number table:
/* 959:    */     //   Java source line #71	-> byte code offset #0
/* 960:    */     //   Java source line #72	-> byte code offset #6
/* 961:    */     //   Java source line #71	-> byte code offset #15
/* 962:    */     //   Java source line #73	-> byte code offset #18
/* 963:    */     //   Java source line #74	-> byte code offset #29
/* 964:    */     //   Java source line #75	-> byte code offset #40
/* 965:    */     //   Java source line #76	-> byte code offset #49
/* 966:    */     //   Java source line #77	-> byte code offset #88
/* 967:    */     //   Java source line #74	-> byte code offset #161
/* 968:    */     //   Java source line #76	-> byte code offset #170
/* 969:    */     //   Java source line #71	-> byte code offset #214
/* 970:    */     //   Java source line #77	-> byte code offset #223
/* 971:    */     //   Java source line #71	-> byte code offset #264
/* 972:    */     //   Java source line #77	-> byte code offset #269
/* 973:    */     //   Java source line #78	-> byte code offset #309
/* 974:    */     // Local variable table:
/* 975:    */     //   start	length	slot	name	signature
/* 976:    */     //   0	322	0	paramString	String
/* 977:    */     //   3	297	1	localConnection	Connection
/* 978:    */     //   308	10	1	localSQLException	SQLException
/* 979:    */     //   5	286	2	localObject1	Object
/* 980:    */     //   14	242	3	localPreparedStatement	PreparedStatement
/* 981:    */     //   264	4	3	localThrowable1	Throwable
/* 982:    */     //   16	230	4	localObject2	Object
/* 983:    */     //   35	170	5	localResultSet	ResultSet
/* 984:    */     //   214	7	5	localThrowable2	Throwable
/* 985:    */     //   38	157	6	localObject3	Object
/* 986:    */     //   47	112	7	bool	boolean
/* 987:    */     //   161	7	7	localThrowable3	Throwable
/* 988:    */     //   69	5	8	localThrowable4	Throwable
/* 989:    */     //   106	5	8	localThrowable5	Throwable
/* 990:    */     //   141	4	8	localThrowable6	Throwable
/* 991:    */     //   170	42	9	localObject4	Object
/* 992:    */     //   192	5	10	localThrowable7	Throwable
/* 993:    */     //   223	39	11	localObject5	Object
/* 994:    */     //   243	5	12	localThrowable8	Throwable
/* 995:    */     //   269	37	13	localObject6	Object
/* 996:    */     //   288	4	14	localThrowable9	Throwable
/* 997:    */     // Exception table:
/* 998:    */     //   from	to	target	type
/* 999:    */     //   59	66	69	java/lang/Throwable
/* :00:    */     //   97	103	106	java/lang/Throwable
/* :01:    */     //   132	138	141	java/lang/Throwable
/* :02:    */     //   40	49	161	java/lang/Throwable
/* :03:    */     //   40	49	170	finally
/* :04:    */     //   161	172	170	finally
/* :05:    */     //   182	189	192	java/lang/Throwable
/* :06:    */     //   18	88	214	java/lang/Throwable
/* :07:    */     //   161	214	214	java/lang/Throwable
/* :08:    */     //   18	88	223	finally
/* :09:    */     //   161	225	223	finally
/* :10:    */     //   234	240	243	java/lang/Throwable
/* :11:    */     //   6	124	264	java/lang/Throwable
/* :12:    */     //   161	264	264	java/lang/Throwable
/* :13:    */     //   6	124	269	finally
/* :14:    */     //   161	271	269	finally
/* :15:    */     //   279	285	288	java/lang/Throwable
/* :16:    */     //   0	158	308	java/sql/SQLException
/* :17:    */     //   161	308	308	java/sql/SQLException
/* :18:    */   }
/* :19:    */   
/* :20:    */   static TransactionImpl loadTransaction(Connection paramConnection, ResultSet paramResultSet)
/* :21:    */     throws NxtException.ValidationException
/* :22:    */   {
/* :23:    */     try
/* :24:    */     {
/* :25: 85 */       byte b1 = paramResultSet.getByte("type");
/* :26: 86 */       byte b2 = paramResultSet.getByte("subtype");
/* :27: 87 */       int i = paramResultSet.getInt("timestamp");
/* :28: 88 */       short s = paramResultSet.getShort("deadline");
/* :29: 89 */       byte[] arrayOfByte1 = paramResultSet.getBytes("sender_public_key");
/* :30: 90 */       long l1 = paramResultSet.getLong("amount");
/* :31: 91 */       long l2 = paramResultSet.getLong("fee");
/* :32: 92 */       byte[] arrayOfByte2 = paramResultSet.getBytes("referenced_transaction_full_hash");
/* :33: 93 */       int j = paramResultSet.getInt("ec_block_height");
/* :34: 94 */       long l3 = paramResultSet.getLong("ec_block_id");
/* :35: 95 */       byte[] arrayOfByte3 = paramResultSet.getBytes("signature");
/* :36: 96 */       long l4 = paramResultSet.getLong("block_id");
/* :37: 97 */       int k = paramResultSet.getInt("height");
/* :38: 98 */       long l5 = paramResultSet.getLong("id");
/* :39: 99 */       long l6 = paramResultSet.getLong("sender_id");
/* :40:100 */       byte[] arrayOfByte4 = paramResultSet.getBytes("attachment_bytes");
/* :41:101 */       int m = paramResultSet.getInt("block_timestamp");
/* :42:102 */       byte[] arrayOfByte5 = paramResultSet.getBytes("full_hash");
/* :43:103 */       byte b3 = paramResultSet.getByte("version");
/* :44:    */       
/* :45:105 */       ByteBuffer localByteBuffer = null;
/* :46:106 */       if (arrayOfByte4 != null)
/* :47:    */       {
/* :48:107 */         localByteBuffer = ByteBuffer.wrap(arrayOfByte4);
/* :49:108 */         localByteBuffer.order(ByteOrder.LITTLE_ENDIAN);
/* :50:    */       }
/* :51:111 */       TransactionType localTransactionType = TransactionType.findTransactionType(b1, b2);
/* :52:112 */       TransactionImpl.BuilderImpl localBuilderImpl = new TransactionImpl.BuilderImpl(b3, arrayOfByte1, l1, l2, i, s, localTransactionType.parseAttachment(localByteBuffer, b3)).referencedTransactionFullHash(arrayOfByte2).signature(arrayOfByte3).blockId(l4).height(k).id(l5).senderId(l6).blockTimestamp(m).fullHash(arrayOfByte5);
/* :53:123 */       if (localTransactionType.hasRecipient())
/* :54:    */       {
/* :55:124 */         long l7 = paramResultSet.getLong("recipient_id");
/* :56:125 */         if (!paramResultSet.wasNull()) {
/* :57:126 */           localBuilderImpl.recipientId(l7);
/* :58:    */         }
/* :59:    */       }
/* :60:129 */       if (paramResultSet.getBoolean("has_message")) {
/* :61:130 */         localBuilderImpl.message(new Appendix.Message(localByteBuffer, b3));
/* :62:    */       }
/* :63:132 */       if (paramResultSet.getBoolean("has_encrypted_message")) {
/* :64:133 */         localBuilderImpl.encryptedMessage(new Appendix.EncryptedMessage(localByteBuffer, b3));
/* :65:    */       }
/* :66:135 */       if (paramResultSet.getBoolean("has_public_key_announcement")) {
/* :67:136 */         localBuilderImpl.publicKeyAnnouncement(new Appendix.PublicKeyAnnouncement(localByteBuffer, b3));
/* :68:    */       }
/* :69:138 */       if (paramResultSet.getBoolean("has_encrypttoself_message")) {
/* :70:139 */         localBuilderImpl.encryptToSelfMessage(new Appendix.EncryptToSelfMessage(localByteBuffer, b3));
/* :71:    */       }
/* :72:141 */       if (b3 > 0)
/* :73:    */       {
/* :74:142 */         localBuilderImpl.ecBlockHeight(j);
/* :75:143 */         localBuilderImpl.ecBlockId(l3);
/* :76:    */       }
/* :77:146 */       return localBuilderImpl.build();
/* :78:    */     }
/* :79:    */     catch (SQLException localSQLException)
/* :80:    */     {
/* :81:149 */       throw new RuntimeException(localSQLException.toString(), localSQLException);
/* :82:    */     }
/* :83:    */   }
/* :84:    */   
/* :85:    */   /* Error */
/* :86:    */   static List<TransactionImpl> findBlockTransactions(long paramLong)
/* :87:    */   {
/* :88:    */     // Byte code:
/* :89:    */     //   0: invokestatic 2	nxt/db/Db:getConnection	()Ljava/sql/Connection;
/* :90:    */     //   3: astore_2
/* :91:    */     //   4: aconst_null
/* :92:    */     //   5: astore_3
/* :93:    */     //   6: aload_2
/* :94:    */     //   7: ldc 95
/* :95:    */     //   9: invokeinterface 4 2 0
/* :96:    */     //   14: astore 4
/* :97:    */     //   16: aconst_null
/* :98:    */     //   17: astore 5
/* :99:    */     //   19: aload 4
/* ;00:    */     //   21: iconst_1
/* ;01:    */     //   22: lload_0
/* ;02:    */     //   23: invokeinterface 5 4 0
/* ;03:    */     //   28: aload 4
/* ;04:    */     //   30: invokeinterface 6 1 0
/* ;05:    */     //   35: astore 6
/* ;06:    */     //   37: aconst_null
/* ;07:    */     //   38: astore 7
/* ;08:    */     //   40: new 96	java/util/ArrayList
/* ;09:    */     //   43: dup
/* ;10:    */     //   44: invokespecial 97	java/util/ArrayList:<init>	()V
/* ;11:    */     //   47: astore 8
/* ;12:    */     //   49: aload 6
/* ;13:    */     //   51: invokeinterface 7 1 0
/* ;14:    */     //   56: ifeq +20 -> 76
/* ;15:    */     //   59: aload 8
/* ;16:    */     //   61: aload_2
/* ;17:    */     //   62: aload 6
/* ;18:    */     //   64: invokestatic 8	nxt/TransactionDb:loadTransaction	(Ljava/sql/Connection;Ljava/sql/ResultSet;)Lnxt/TransactionImpl;
/* ;19:    */     //   67: invokeinterface 98 2 0
/* ;20:    */     //   72: pop
/* ;21:    */     //   73: goto -24 -> 49
/* ;22:    */     //   76: aload 8
/* ;23:    */     //   78: astore 9
/* ;24:    */     //   80: aload 6
/* ;25:    */     //   82: ifnull +37 -> 119
/* ;26:    */     //   85: aload 7
/* ;27:    */     //   87: ifnull +25 -> 112
/* ;28:    */     //   90: aload 6
/* ;29:    */     //   92: invokeinterface 9 1 0
/* ;30:    */     //   97: goto +22 -> 119
/* ;31:    */     //   100: astore 10
/* ;32:    */     //   102: aload 7
/* ;33:    */     //   104: aload 10
/* ;34:    */     //   106: invokevirtual 11	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* ;35:    */     //   109: goto +10 -> 119
/* ;36:    */     //   112: aload 6
/* ;37:    */     //   114: invokeinterface 9 1 0
/* ;38:    */     //   119: aload 4
/* ;39:    */     //   121: ifnull +37 -> 158
/* ;40:    */     //   124: aload 5
/* ;41:    */     //   126: ifnull +25 -> 151
/* ;42:    */     //   129: aload 4
/* ;43:    */     //   131: invokeinterface 12 1 0
/* ;44:    */     //   136: goto +22 -> 158
/* ;45:    */     //   139: astore 10
/* ;46:    */     //   141: aload 5
/* ;47:    */     //   143: aload 10
/* ;48:    */     //   145: invokevirtual 11	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* ;49:    */     //   148: goto +10 -> 158
/* ;50:    */     //   151: aload 4
/* ;51:    */     //   153: invokeinterface 12 1 0
/* ;52:    */     //   158: aload_2
/* ;53:    */     //   159: ifnull +33 -> 192
/* ;54:    */     //   162: aload_3
/* ;55:    */     //   163: ifnull +23 -> 186
/* ;56:    */     //   166: aload_2
/* ;57:    */     //   167: invokeinterface 13 1 0
/* ;58:    */     //   172: goto +20 -> 192
/* ;59:    */     //   175: astore 10
/* ;60:    */     //   177: aload_3
/* ;61:    */     //   178: aload 10
/* ;62:    */     //   180: invokevirtual 11	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* ;63:    */     //   183: goto +9 -> 192
/* ;64:    */     //   186: aload_2
/* ;65:    */     //   187: invokeinterface 13 1 0
/* ;66:    */     //   192: aload 9
/* ;67:    */     //   194: areturn
/* ;68:    */     //   195: astore 8
/* ;69:    */     //   197: aload 8
/* ;70:    */     //   199: astore 7
/* ;71:    */     //   201: aload 8
/* ;72:    */     //   203: athrow
/* ;73:    */     //   204: astore 11
/* ;74:    */     //   206: aload 6
/* ;75:    */     //   208: ifnull +37 -> 245
/* ;76:    */     //   211: aload 7
/* ;77:    */     //   213: ifnull +25 -> 238
/* ;78:    */     //   216: aload 6
/* ;79:    */     //   218: invokeinterface 9 1 0
/* ;80:    */     //   223: goto +22 -> 245
/* ;81:    */     //   226: astore 12
/* ;82:    */     //   228: aload 7
/* ;83:    */     //   230: aload 12
/* ;84:    */     //   232: invokevirtual 11	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* ;85:    */     //   235: goto +10 -> 245
/* ;86:    */     //   238: aload 6
/* ;87:    */     //   240: invokeinterface 9 1 0
/* ;88:    */     //   245: aload 11
/* ;89:    */     //   247: athrow
/* ;90:    */     //   248: astore 6
/* ;91:    */     //   250: aload 6
/* ;92:    */     //   252: astore 5
/* ;93:    */     //   254: aload 6
/* ;94:    */     //   256: athrow
/* ;95:    */     //   257: astore 13
/* ;96:    */     //   259: aload 4
/* ;97:    */     //   261: ifnull +37 -> 298
/* ;98:    */     //   264: aload 5
/* ;99:    */     //   266: ifnull +25 -> 291
/* <00:    */     //   269: aload 4
/* <01:    */     //   271: invokeinterface 12 1 0
/* <02:    */     //   276: goto +22 -> 298
/* <03:    */     //   279: astore 14
/* <04:    */     //   281: aload 5
/* <05:    */     //   283: aload 14
/* <06:    */     //   285: invokevirtual 11	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* <07:    */     //   288: goto +10 -> 298
/* <08:    */     //   291: aload 4
/* <09:    */     //   293: invokeinterface 12 1 0
/* <10:    */     //   298: aload 13
/* <11:    */     //   300: athrow
/* <12:    */     //   301: astore 4
/* <13:    */     //   303: aload 4
/* <14:    */     //   305: astore_3
/* <15:    */     //   306: aload 4
/* <16:    */     //   308: athrow
/* <17:    */     //   309: astore 15
/* <18:    */     //   311: aload_2
/* <19:    */     //   312: ifnull +33 -> 345
/* <20:    */     //   315: aload_3
/* <21:    */     //   316: ifnull +23 -> 339
/* <22:    */     //   319: aload_2
/* <23:    */     //   320: invokeinterface 13 1 0
/* <24:    */     //   325: goto +20 -> 345
/* <25:    */     //   328: astore 16
/* <26:    */     //   330: aload_3
/* <27:    */     //   331: aload 16
/* <28:    */     //   333: invokevirtual 11	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* <29:    */     //   336: goto +9 -> 345
/* <30:    */     //   339: aload_2
/* <31:    */     //   340: invokeinterface 13 1 0
/* <32:    */     //   345: aload 15
/* <33:    */     //   347: athrow
/* <34:    */     //   348: astore_2
/* <35:    */     //   349: new 15	java/lang/RuntimeException
/* <36:    */     //   352: dup
/* <37:    */     //   353: aload_2
/* <38:    */     //   354: invokevirtual 16	java/sql/SQLException:toString	()Ljava/lang/String;
/* <39:    */     //   357: aload_2
/* <40:    */     //   358: invokespecial 17	java/lang/RuntimeException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
/* <41:    */     //   361: athrow
/* <42:    */     //   362: astore_2
/* <43:    */     //   363: new 15	java/lang/RuntimeException
/* <44:    */     //   366: dup
/* <45:    */     //   367: new 19	java/lang/StringBuilder
/* <46:    */     //   370: dup
/* <47:    */     //   371: invokespecial 20	java/lang/StringBuilder:<init>	()V
/* <48:    */     //   374: ldc 99
/* <49:    */     //   376: invokevirtual 22	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* <50:    */     //   379: lload_0
/* <51:    */     //   380: invokestatic 100	nxt/util/Convert:toUnsignedLong	(J)Ljava/lang/String;
/* <52:    */     //   383: invokevirtual 22	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* <53:    */     //   386: ldc 101
/* <54:    */     //   388: invokevirtual 22	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* <55:    */     //   391: invokevirtual 25	java/lang/StringBuilder:toString	()Ljava/lang/String;
/* <56:    */     //   394: aload_2
/* <57:    */     //   395: invokespecial 17	java/lang/RuntimeException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
/* <58:    */     //   398: athrow
/* <59:    */     // Line number table:
/* <60:    */     //   Java source line #154	-> byte code offset #0
/* <61:    */     //   Java source line #155	-> byte code offset #6
/* <62:    */     //   Java source line #154	-> byte code offset #16
/* <63:    */     //   Java source line #156	-> byte code offset #19
/* <64:    */     //   Java source line #157	-> byte code offset #28
/* <65:    */     //   Java source line #158	-> byte code offset #40
/* <66:    */     //   Java source line #159	-> byte code offset #49
/* <67:    */     //   Java source line #160	-> byte code offset #59
/* <68:    */     //   Java source line #162	-> byte code offset #76
/* <69:    */     //   Java source line #163	-> byte code offset #80
/* <70:    */     //   Java source line #164	-> byte code offset #119
/* <71:    */     //   Java source line #157	-> byte code offset #195
/* <72:    */     //   Java source line #163	-> byte code offset #204
/* <73:    */     //   Java source line #154	-> byte code offset #248
/* <74:    */     //   Java source line #164	-> byte code offset #257
/* <75:    */     //   Java source line #154	-> byte code offset #301
/* <76:    */     //   Java source line #164	-> byte code offset #309
/* <77:    */     //   Java source line #165	-> byte code offset #349
/* <78:    */     //   Java source line #166	-> byte code offset #362
/* <79:    */     //   Java source line #167	-> byte code offset #363
/* <80:    */     // Local variable table:
/* <81:    */     //   start	length	slot	name	signature
/* <82:    */     //   0	399	0	paramLong	long
/* <83:    */     //   3	337	2	localConnection	Connection
/* <84:    */     //   348	10	2	localSQLException	SQLException
/* <85:    */     //   362	33	2	localValidationException	NxtException.ValidationException
/* <86:    */     //   5	326	3	localObject1	Object
/* <87:    */     //   14	278	4	localPreparedStatement	PreparedStatement
/* <88:    */     //   301	6	4	localThrowable1	Throwable
/* <89:    */     //   17	265	5	localObject2	Object
/* <90:    */     //   35	204	6	localResultSet	ResultSet
/* <91:    */     //   248	7	6	localThrowable2	Throwable
/* <92:    */     //   38	191	7	localObject3	Object
/* <93:    */     //   47	30	8	localArrayList1	java.util.ArrayList
/* <94:    */     //   195	7	8	localThrowable3	Throwable
/* <95:    */     //   78	115	9	localArrayList2	java.util.ArrayList
/* <96:    */     //   100	5	10	localThrowable4	Throwable
/* <97:    */     //   139	5	10	localThrowable5	Throwable
/* <98:    */     //   175	4	10	localThrowable6	Throwable
/* <99:    */     //   204	42	11	localObject4	Object
/* =00:    */     //   226	5	12	localThrowable7	Throwable
/* =01:    */     //   257	42	13	localObject5	Object
/* =02:    */     //   279	5	14	localThrowable8	Throwable
/* =03:    */     //   309	37	15	localObject6	Object
/* =04:    */     //   328	4	16	localThrowable9	Throwable
/* =05:    */     // Exception table:
/* =06:    */     //   from	to	target	type
/* =07:    */     //   90	97	100	java/lang/Throwable
/* =08:    */     //   129	136	139	java/lang/Throwable
/* =09:    */     //   166	172	175	java/lang/Throwable
/* =10:    */     //   40	80	195	java/lang/Throwable
/* =11:    */     //   40	80	204	finally
/* =12:    */     //   195	206	204	finally
/* =13:    */     //   216	223	226	java/lang/Throwable
/* =14:    */     //   19	119	248	java/lang/Throwable
/* =15:    */     //   195	248	248	java/lang/Throwable
/* =16:    */     //   19	119	257	finally
/* =17:    */     //   195	259	257	finally
/* =18:    */     //   269	276	279	java/lang/Throwable
/* =19:    */     //   6	158	301	java/lang/Throwable
/* =20:    */     //   195	301	301	java/lang/Throwable
/* =21:    */     //   6	158	309	finally
/* =22:    */     //   195	311	309	finally
/* =23:    */     //   319	325	328	java/lang/Throwable
/* =24:    */     //   0	192	348	java/sql/SQLException
/* =25:    */     //   195	348	348	java/sql/SQLException
/* =26:    */     //   0	192	362	nxt/NxtException$ValidationException
/* =27:    */     //   195	348	362	nxt/NxtException$ValidationException
/* =28:    */   }
/* =29:    */   
/* =30:    */   static void saveTransactions(Connection paramConnection, List<TransactionImpl> paramList)
/* =31:    */   {
/* =32:    */     try
/* =33:    */     {
/* =34:174 */       for (TransactionImpl localTransactionImpl : paramList)
/* =35:    */       {
/* =36:175 */         PreparedStatement localPreparedStatement = paramConnection.prepareStatement("INSERT INTO transaction (id, deadline, sender_public_key, recipient_id, amount, fee, referenced_transaction_full_hash, height, block_id, signature, timestamp, type, subtype, sender_id, attachment_bytes, block_timestamp, full_hash, version, has_message, has_encrypted_message, has_public_key_announcement, has_encrypttoself_message, ec_block_height, ec_block_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");Object localObject1 = null;
/* =37:    */         try
/* =38:    */         {
/* =39:181 */           int i = 0;
/* =40:182 */           localPreparedStatement.setLong(++i, localTransactionImpl.getId());
/* =41:183 */           localPreparedStatement.setShort(++i, localTransactionImpl.getDeadline());
/* =42:184 */           localPreparedStatement.setBytes(++i, localTransactionImpl.getSenderPublicKey());
/* =43:185 */           DbUtils.setLongZeroToNull(localPreparedStatement, ++i, localTransactionImpl.getRecipientId());
/* =44:186 */           localPreparedStatement.setLong(++i, localTransactionImpl.getAmountNQT());
/* =45:187 */           localPreparedStatement.setLong(++i, localTransactionImpl.getFeeNQT());
/* =46:188 */           DbUtils.setBytes(localPreparedStatement, ++i, Convert.parseHexString(localTransactionImpl.getReferencedTransactionFullHash()));
/* =47:189 */           localPreparedStatement.setInt(++i, localTransactionImpl.getHeight());
/* =48:190 */           localPreparedStatement.setLong(++i, localTransactionImpl.getBlockId());
/* =49:191 */           if (localTransactionImpl.getSignature() != null) {
/* =50:192 */             localPreparedStatement.setBytes(++i, localTransactionImpl.getSignature());
/* =51:    */           } else {
/* =52:194 */             localPreparedStatement.setNull(++i, -2);
/* =53:    */           }
/* =54:196 */           localPreparedStatement.setInt(++i, localTransactionImpl.getTimestamp());
/* =55:197 */           localPreparedStatement.setByte(++i, localTransactionImpl.getType().getType());
/* =56:198 */           localPreparedStatement.setByte(++i, localTransactionImpl.getType().getSubtype());
/* =57:199 */           localPreparedStatement.setLong(++i, localTransactionImpl.getSenderId());
/* =58:200 */           int j = 0;
/* =59:201 */           for (Object localObject2 = localTransactionImpl.getAppendages().iterator(); ((Iterator)localObject2).hasNext();)
/* =60:    */           {
/* =61:201 */             localObject3 = (Appendix)((Iterator)localObject2).next();
/* =62:202 */             j += ((Appendix)localObject3).getSize();
/* =63:    */           }
/* =64:    */           Object localObject3;
/* =65:204 */           if (j == 0)
/* =66:    */           {
/* =67:205 */             localPreparedStatement.setNull(++i, -3);
/* =68:    */           }
/* =69:    */           else
/* =70:    */           {
/* =71:207 */             localObject2 = ByteBuffer.allocate(j);
/* =72:208 */             ((ByteBuffer)localObject2).order(ByteOrder.LITTLE_ENDIAN);
/* =73:209 */             for (localObject3 = localTransactionImpl.getAppendages().iterator(); ((Iterator)localObject3).hasNext();)
/* =74:    */             {
/* =75:209 */               Appendix localAppendix = (Appendix)((Iterator)localObject3).next();
/* =76:210 */               localAppendix.putBytes((ByteBuffer)localObject2);
/* =77:    */             }
/* =78:212 */             localPreparedStatement.setBytes(++i, ((ByteBuffer)localObject2).array());
/* =79:    */           }
/* =80:214 */           localPreparedStatement.setInt(++i, localTransactionImpl.getBlockTimestamp());
/* =81:215 */           localPreparedStatement.setBytes(++i, Convert.parseHexString(localTransactionImpl.getFullHash()));
/* =82:216 */           localPreparedStatement.setByte(++i, localTransactionImpl.getVersion());
/* =83:217 */           localPreparedStatement.setBoolean(++i, localTransactionImpl.getMessage() != null);
/* =84:218 */           localPreparedStatement.setBoolean(++i, localTransactionImpl.getEncryptedMessage() != null);
/* =85:219 */           localPreparedStatement.setBoolean(++i, localTransactionImpl.getPublicKeyAnnouncement() != null);
/* =86:220 */           localPreparedStatement.setBoolean(++i, localTransactionImpl.getEncryptToSelfMessage() != null);
/* =87:221 */           localPreparedStatement.setInt(++i, localTransactionImpl.getECBlockHeight());
/* =88:222 */           DbUtils.setLongZeroToNull(localPreparedStatement, ++i, localTransactionImpl.getECBlockId());
/* =89:223 */           localPreparedStatement.executeUpdate();
/* =90:    */         }
/* =91:    */         catch (Throwable localThrowable2)
/* =92:    */         {
/* =93:175 */           localObject1 = localThrowable2;throw localThrowable2;
/* =94:    */         }
/* =95:    */         finally
/* =96:    */         {
/* =97:224 */           if (localPreparedStatement != null) {
/* =98:224 */             if (localObject1 != null) {
/* =99:    */               try
/* >00:    */               {
/* >01:224 */                 localPreparedStatement.close();
/* >02:    */               }
/* >03:    */               catch (Throwable localThrowable3)
/* >04:    */               {
/* >05:224 */                 ((Throwable)localObject1).addSuppressed(localThrowable3);
/* >06:    */               }
/* >07:    */             } else {
/* >08:224 */               localPreparedStatement.close();
/* >09:    */             }
/* >10:    */           }
/* >11:    */         }
/* >12:    */       }
/* >13:    */     }
/* >14:    */     catch (SQLException localSQLException)
/* >15:    */     {
/* >16:227 */       throw new RuntimeException(localSQLException.toString(), localSQLException);
/* >17:    */     }
/* >18:    */   }
/* >19:    */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.TransactionDb
 * JD-Core Version:    0.7.1
 */