/*   1:    */ package nxt.db;
/*   2:    */ 
/*   3:    */ import java.sql.Connection;
/*   4:    */ import java.sql.PreparedStatement;
/*   5:    */ import java.sql.ResultSet;
/*   6:    */ import java.sql.SQLException;
/*   7:    */ import java.util.ArrayList;
/*   8:    */ import java.util.Iterator;
/*   9:    */ import java.util.List;
/*  10:    */ import java.util.Map;
/*  11:    */ 
/*  12:    */ public abstract class VersionedEntityDbTable<T>
/*  13:    */   extends EntityDbTable<T>
/*  14:    */ {
/*  15:    */   protected VersionedEntityDbTable(String paramString, DbKey.Factory<T> paramFactory)
/*  16:    */   {
/*  17: 15 */     super(paramString, paramFactory, true);
/*  18:    */   }
/*  19:    */   
/*  20:    */   public void rollback(int paramInt)
/*  21:    */   {
/*  22: 20 */     rollback(this.table, paramInt, this.dbKeyFactory);
/*  23:    */   }
/*  24:    */   
/*  25:    */   /* Error */
/*  26:    */   public final boolean delete(T paramT)
/*  27:    */   {
/*  28:    */     // Byte code:
/*  29:    */     //   0: aload_1
/*  30:    */     //   1: ifnonnull +5 -> 6
/*  31:    */     //   4: iconst_0
/*  32:    */     //   5: ireturn
/*  33:    */     //   6: invokestatic 5	nxt/db/Db:isInTransaction	()Z
/*  34:    */     //   9: ifne +13 -> 22
/*  35:    */     //   12: new 6	java/lang/IllegalStateException
/*  36:    */     //   15: dup
/*  37:    */     //   16: ldc 7
/*  38:    */     //   18: invokespecial 8	java/lang/IllegalStateException:<init>	(Ljava/lang/String;)V
/*  39:    */     //   21: athrow
/*  40:    */     //   22: aload_0
/*  41:    */     //   23: getfield 3	nxt/db/VersionedEntityDbTable:dbKeyFactory	Lnxt/db/DbKey$Factory;
/*  42:    */     //   26: aload_1
/*  43:    */     //   27: invokevirtual 9	nxt/db/DbKey$Factory:newKey	(Ljava/lang/Object;)Lnxt/db/DbKey;
/*  44:    */     //   30: astore_2
/*  45:    */     //   31: invokestatic 10	nxt/db/Db:getConnection	()Ljava/sql/Connection;
/*  46:    */     //   34: astore_3
/*  47:    */     //   35: aconst_null
/*  48:    */     //   36: astore 4
/*  49:    */     //   38: aload_3
/*  50:    */     //   39: new 11	java/lang/StringBuilder
/*  51:    */     //   42: dup
/*  52:    */     //   43: invokespecial 12	java/lang/StringBuilder:<init>	()V
/*  53:    */     //   46: ldc 13
/*  54:    */     //   48: invokevirtual 14	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*  55:    */     //   51: aload_0
/*  56:    */     //   52: getfield 2	nxt/db/VersionedEntityDbTable:table	Ljava/lang/String;
/*  57:    */     //   55: invokevirtual 14	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*  58:    */     //   58: aload_0
/*  59:    */     //   59: getfield 3	nxt/db/VersionedEntityDbTable:dbKeyFactory	Lnxt/db/DbKey$Factory;
/*  60:    */     //   62: invokevirtual 15	nxt/db/DbKey$Factory:getPKClause	()Ljava/lang/String;
/*  61:    */     //   65: invokevirtual 14	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*  62:    */     //   68: ldc 16
/*  63:    */     //   70: invokevirtual 14	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*  64:    */     //   73: invokevirtual 17	java/lang/StringBuilder:toString	()Ljava/lang/String;
/*  65:    */     //   76: invokeinterface 18 2 0
/*  66:    */     //   81: astore 5
/*  67:    */     //   83: aconst_null
/*  68:    */     //   84: astore 6
/*  69:    */     //   86: aload_2
/*  70:    */     //   87: aload 5
/*  71:    */     //   89: invokeinterface 19 2 0
/*  72:    */     //   94: istore 7
/*  73:    */     //   96: aload 5
/*  74:    */     //   98: iload 7
/*  75:    */     //   100: invokestatic 20	nxt/Nxt:getBlockchain	()Lnxt/Blockchain;
/*  76:    */     //   103: invokeinterface 21 1 0
/*  77:    */     //   108: invokeinterface 22 3 0
/*  78:    */     //   113: aload 5
/*  79:    */     //   115: invokeinterface 23 1 0
/*  80:    */     //   120: astore 8
/*  81:    */     //   122: aconst_null
/*  82:    */     //   123: astore 9
/*  83:    */     //   125: aload 8
/*  84:    */     //   127: invokeinterface 24 1 0
/*  85:    */     //   132: pop
/*  86:    */     //   133: aload 8
/*  87:    */     //   135: ldc 25
/*  88:    */     //   137: invokeinterface 26 2 0
/*  89:    */     //   142: ifle +316 -> 458
/*  90:    */     //   145: aload_3
/*  91:    */     //   146: new 11	java/lang/StringBuilder
/*  92:    */     //   149: dup
/*  93:    */     //   150: invokespecial 12	java/lang/StringBuilder:<init>	()V
/*  94:    */     //   153: ldc 27
/*  95:    */     //   155: invokevirtual 14	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*  96:    */     //   158: aload_0
/*  97:    */     //   159: getfield 2	nxt/db/VersionedEntityDbTable:table	Ljava/lang/String;
/*  98:    */     //   162: invokevirtual 14	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*  99:    */     //   165: ldc 28
/* 100:    */     //   167: invokevirtual 14	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 101:    */     //   170: aload_0
/* 102:    */     //   171: getfield 3	nxt/db/VersionedEntityDbTable:dbKeyFactory	Lnxt/db/DbKey$Factory;
/* 103:    */     //   174: invokevirtual 15	nxt/db/DbKey$Factory:getPKClause	()Ljava/lang/String;
/* 104:    */     //   177: invokevirtual 14	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 105:    */     //   180: ldc 29
/* 106:    */     //   182: invokevirtual 14	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 107:    */     //   185: invokevirtual 17	java/lang/StringBuilder:toString	()Ljava/lang/String;
/* 108:    */     //   188: invokeinterface 18 2 0
/* 109:    */     //   193: astore 10
/* 110:    */     //   195: aconst_null
/* 111:    */     //   196: astore 11
/* 112:    */     //   198: aload_2
/* 113:    */     //   199: aload 10
/* 114:    */     //   201: invokeinterface 19 2 0
/* 115:    */     //   206: pop
/* 116:    */     //   207: aload 10
/* 117:    */     //   209: invokeinterface 30 1 0
/* 118:    */     //   214: pop
/* 119:    */     //   215: aload_0
/* 120:    */     //   216: aload_3
/* 121:    */     //   217: aload_1
/* 122:    */     //   218: invokevirtual 31	nxt/db/VersionedEntityDbTable:save	(Ljava/sql/Connection;Ljava/lang/Object;)V
/* 123:    */     //   221: aload 10
/* 124:    */     //   223: invokeinterface 30 1 0
/* 125:    */     //   228: pop
/* 126:    */     //   229: aload 10
/* 127:    */     //   231: ifnull +93 -> 324
/* 128:    */     //   234: aload 11
/* 129:    */     //   236: ifnull +25 -> 261
/* 130:    */     //   239: aload 10
/* 131:    */     //   241: invokeinterface 32 1 0
/* 132:    */     //   246: goto +78 -> 324
/* 133:    */     //   249: astore 12
/* 134:    */     //   251: aload 11
/* 135:    */     //   253: aload 12
/* 136:    */     //   255: invokevirtual 34	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 137:    */     //   258: goto +66 -> 324
/* 138:    */     //   261: aload 10
/* 139:    */     //   263: invokeinterface 32 1 0
/* 140:    */     //   268: goto +56 -> 324
/* 141:    */     //   271: astore 12
/* 142:    */     //   273: aload 12
/* 143:    */     //   275: astore 11
/* 144:    */     //   277: aload 12
/* 145:    */     //   279: athrow
/* 146:    */     //   280: astore 13
/* 147:    */     //   282: aload 10
/* 148:    */     //   284: ifnull +37 -> 321
/* 149:    */     //   287: aload 11
/* 150:    */     //   289: ifnull +25 -> 314
/* 151:    */     //   292: aload 10
/* 152:    */     //   294: invokeinterface 32 1 0
/* 153:    */     //   299: goto +22 -> 321
/* 154:    */     //   302: astore 14
/* 155:    */     //   304: aload 11
/* 156:    */     //   306: aload 14
/* 157:    */     //   308: invokevirtual 34	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 158:    */     //   311: goto +10 -> 321
/* 159:    */     //   314: aload 10
/* 160:    */     //   316: invokeinterface 32 1 0
/* 161:    */     //   321: aload 13
/* 162:    */     //   323: athrow
/* 163:    */     //   324: iconst_1
/* 164:    */     //   325: istore 10
/* 165:    */     //   327: aload 8
/* 166:    */     //   329: ifnull +37 -> 366
/* 167:    */     //   332: aload 9
/* 168:    */     //   334: ifnull +25 -> 359
/* 169:    */     //   337: aload 8
/* 170:    */     //   339: invokeinterface 35 1 0
/* 171:    */     //   344: goto +22 -> 366
/* 172:    */     //   347: astore 11
/* 173:    */     //   349: aload 9
/* 174:    */     //   351: aload 11
/* 175:    */     //   353: invokevirtual 34	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 176:    */     //   356: goto +10 -> 366
/* 177:    */     //   359: aload 8
/* 178:    */     //   361: invokeinterface 35 1 0
/* 179:    */     //   366: aload 5
/* 180:    */     //   368: ifnull +37 -> 405
/* 181:    */     //   371: aload 6
/* 182:    */     //   373: ifnull +25 -> 398
/* 183:    */     //   376: aload 5
/* 184:    */     //   378: invokeinterface 32 1 0
/* 185:    */     //   383: goto +22 -> 405
/* 186:    */     //   386: astore 11
/* 187:    */     //   388: aload 6
/* 188:    */     //   390: aload 11
/* 189:    */     //   392: invokevirtual 34	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 190:    */     //   395: goto +10 -> 405
/* 191:    */     //   398: aload 5
/* 192:    */     //   400: invokeinterface 32 1 0
/* 193:    */     //   405: aload_3
/* 194:    */     //   406: ifnull +35 -> 441
/* 195:    */     //   409: aload 4
/* 196:    */     //   411: ifnull +24 -> 435
/* 197:    */     //   414: aload_3
/* 198:    */     //   415: invokeinterface 36 1 0
/* 199:    */     //   420: goto +21 -> 441
/* 200:    */     //   423: astore 11
/* 201:    */     //   425: aload 4
/* 202:    */     //   427: aload 11
/* 203:    */     //   429: invokevirtual 34	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 204:    */     //   432: goto +9 -> 441
/* 205:    */     //   435: aload_3
/* 206:    */     //   436: invokeinterface 36 1 0
/* 207:    */     //   441: aload_0
/* 208:    */     //   442: getfield 2	nxt/db/VersionedEntityDbTable:table	Ljava/lang/String;
/* 209:    */     //   445: invokestatic 37	nxt/db/Db:getCache	(Ljava/lang/String;)Ljava/util/Map;
/* 210:    */     //   448: aload_2
/* 211:    */     //   449: invokeinterface 38 2 0
/* 212:    */     //   454: pop
/* 213:    */     //   455: iload 10
/* 214:    */     //   457: ireturn
/* 215:    */     //   458: aload_3
/* 216:    */     //   459: new 11	java/lang/StringBuilder
/* 217:    */     //   462: dup
/* 218:    */     //   463: invokespecial 12	java/lang/StringBuilder:<init>	()V
/* 219:    */     //   466: ldc 39
/* 220:    */     //   468: invokevirtual 14	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 221:    */     //   471: aload_0
/* 222:    */     //   472: getfield 2	nxt/db/VersionedEntityDbTable:table	Ljava/lang/String;
/* 223:    */     //   475: invokevirtual 14	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 224:    */     //   478: aload_0
/* 225:    */     //   479: getfield 3	nxt/db/VersionedEntityDbTable:dbKeyFactory	Lnxt/db/DbKey$Factory;
/* 226:    */     //   482: invokevirtual 15	nxt/db/DbKey$Factory:getPKClause	()Ljava/lang/String;
/* 227:    */     //   485: invokevirtual 14	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 228:    */     //   488: invokevirtual 17	java/lang/StringBuilder:toString	()Ljava/lang/String;
/* 229:    */     //   491: invokeinterface 18 2 0
/* 230:    */     //   496: astore 10
/* 231:    */     //   498: aconst_null
/* 232:    */     //   499: astore 11
/* 233:    */     //   501: aload_2
/* 234:    */     //   502: aload 10
/* 235:    */     //   504: invokeinterface 19 2 0
/* 236:    */     //   509: pop
/* 237:    */     //   510: aload 10
/* 238:    */     //   512: invokeinterface 30 1 0
/* 239:    */     //   517: ifle +7 -> 524
/* 240:    */     //   520: iconst_1
/* 241:    */     //   521: goto +4 -> 525
/* 242:    */     //   524: iconst_0
/* 243:    */     //   525: istore 12
/* 244:    */     //   527: aload 10
/* 245:    */     //   529: ifnull +37 -> 566
/* 246:    */     //   532: aload 11
/* 247:    */     //   534: ifnull +25 -> 559
/* 248:    */     //   537: aload 10
/* 249:    */     //   539: invokeinterface 32 1 0
/* 250:    */     //   544: goto +22 -> 566
/* 251:    */     //   547: astore 13
/* 252:    */     //   549: aload 11
/* 253:    */     //   551: aload 13
/* 254:    */     //   553: invokevirtual 34	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 255:    */     //   556: goto +10 -> 566
/* 256:    */     //   559: aload 10
/* 257:    */     //   561: invokeinterface 32 1 0
/* 258:    */     //   566: aload 8
/* 259:    */     //   568: ifnull +37 -> 605
/* 260:    */     //   571: aload 9
/* 261:    */     //   573: ifnull +25 -> 598
/* 262:    */     //   576: aload 8
/* 263:    */     //   578: invokeinterface 35 1 0
/* 264:    */     //   583: goto +22 -> 605
/* 265:    */     //   586: astore 13
/* 266:    */     //   588: aload 9
/* 267:    */     //   590: aload 13
/* 268:    */     //   592: invokevirtual 34	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 269:    */     //   595: goto +10 -> 605
/* 270:    */     //   598: aload 8
/* 271:    */     //   600: invokeinterface 35 1 0
/* 272:    */     //   605: aload 5
/* 273:    */     //   607: ifnull +37 -> 644
/* 274:    */     //   610: aload 6
/* 275:    */     //   612: ifnull +25 -> 637
/* 276:    */     //   615: aload 5
/* 277:    */     //   617: invokeinterface 32 1 0
/* 278:    */     //   622: goto +22 -> 644
/* 279:    */     //   625: astore 13
/* 280:    */     //   627: aload 6
/* 281:    */     //   629: aload 13
/* 282:    */     //   631: invokevirtual 34	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 283:    */     //   634: goto +10 -> 644
/* 284:    */     //   637: aload 5
/* 285:    */     //   639: invokeinterface 32 1 0
/* 286:    */     //   644: aload_3
/* 287:    */     //   645: ifnull +35 -> 680
/* 288:    */     //   648: aload 4
/* 289:    */     //   650: ifnull +24 -> 674
/* 290:    */     //   653: aload_3
/* 291:    */     //   654: invokeinterface 36 1 0
/* 292:    */     //   659: goto +21 -> 680
/* 293:    */     //   662: astore 13
/* 294:    */     //   664: aload 4
/* 295:    */     //   666: aload 13
/* 296:    */     //   668: invokevirtual 34	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 297:    */     //   671: goto +9 -> 680
/* 298:    */     //   674: aload_3
/* 299:    */     //   675: invokeinterface 36 1 0
/* 300:    */     //   680: aload_0
/* 301:    */     //   681: getfield 2	nxt/db/VersionedEntityDbTable:table	Ljava/lang/String;
/* 302:    */     //   684: invokestatic 37	nxt/db/Db:getCache	(Ljava/lang/String;)Ljava/util/Map;
/* 303:    */     //   687: aload_2
/* 304:    */     //   688: invokeinterface 38 2 0
/* 305:    */     //   693: pop
/* 306:    */     //   694: iload 12
/* 307:    */     //   696: ireturn
/* 308:    */     //   697: astore 12
/* 309:    */     //   699: aload 12
/* 310:    */     //   701: astore 11
/* 311:    */     //   703: aload 12
/* 312:    */     //   705: athrow
/* 313:    */     //   706: astore 15
/* 314:    */     //   708: aload 10
/* 315:    */     //   710: ifnull +37 -> 747
/* 316:    */     //   713: aload 11
/* 317:    */     //   715: ifnull +25 -> 740
/* 318:    */     //   718: aload 10
/* 319:    */     //   720: invokeinterface 32 1 0
/* 320:    */     //   725: goto +22 -> 747
/* 321:    */     //   728: astore 16
/* 322:    */     //   730: aload 11
/* 323:    */     //   732: aload 16
/* 324:    */     //   734: invokevirtual 34	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 325:    */     //   737: goto +10 -> 747
/* 326:    */     //   740: aload 10
/* 327:    */     //   742: invokeinterface 32 1 0
/* 328:    */     //   747: aload 15
/* 329:    */     //   749: athrow
/* 330:    */     //   750: astore 10
/* 331:    */     //   752: aload 10
/* 332:    */     //   754: astore 9
/* 333:    */     //   756: aload 10
/* 334:    */     //   758: athrow
/* 335:    */     //   759: astore 17
/* 336:    */     //   761: aload 8
/* 337:    */     //   763: ifnull +37 -> 800
/* 338:    */     //   766: aload 9
/* 339:    */     //   768: ifnull +25 -> 793
/* 340:    */     //   771: aload 8
/* 341:    */     //   773: invokeinterface 35 1 0
/* 342:    */     //   778: goto +22 -> 800
/* 343:    */     //   781: astore 18
/* 344:    */     //   783: aload 9
/* 345:    */     //   785: aload 18
/* 346:    */     //   787: invokevirtual 34	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 347:    */     //   790: goto +10 -> 800
/* 348:    */     //   793: aload 8
/* 349:    */     //   795: invokeinterface 35 1 0
/* 350:    */     //   800: aload 17
/* 351:    */     //   802: athrow
/* 352:    */     //   803: astore 7
/* 353:    */     //   805: aload 7
/* 354:    */     //   807: astore 6
/* 355:    */     //   809: aload 7
/* 356:    */     //   811: athrow
/* 357:    */     //   812: astore 19
/* 358:    */     //   814: aload 5
/* 359:    */     //   816: ifnull +37 -> 853
/* 360:    */     //   819: aload 6
/* 361:    */     //   821: ifnull +25 -> 846
/* 362:    */     //   824: aload 5
/* 363:    */     //   826: invokeinterface 32 1 0
/* 364:    */     //   831: goto +22 -> 853
/* 365:    */     //   834: astore 20
/* 366:    */     //   836: aload 6
/* 367:    */     //   838: aload 20
/* 368:    */     //   840: invokevirtual 34	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 369:    */     //   843: goto +10 -> 853
/* 370:    */     //   846: aload 5
/* 371:    */     //   848: invokeinterface 32 1 0
/* 372:    */     //   853: aload 19
/* 373:    */     //   855: athrow
/* 374:    */     //   856: astore 5
/* 375:    */     //   858: aload 5
/* 376:    */     //   860: astore 4
/* 377:    */     //   862: aload 5
/* 378:    */     //   864: athrow
/* 379:    */     //   865: astore 21
/* 380:    */     //   867: aload_3
/* 381:    */     //   868: ifnull +35 -> 903
/* 382:    */     //   871: aload 4
/* 383:    */     //   873: ifnull +24 -> 897
/* 384:    */     //   876: aload_3
/* 385:    */     //   877: invokeinterface 36 1 0
/* 386:    */     //   882: goto +21 -> 903
/* 387:    */     //   885: astore 22
/* 388:    */     //   887: aload 4
/* 389:    */     //   889: aload 22
/* 390:    */     //   891: invokevirtual 34	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 391:    */     //   894: goto +9 -> 903
/* 392:    */     //   897: aload_3
/* 393:    */     //   898: invokeinterface 36 1 0
/* 394:    */     //   903: aload 21
/* 395:    */     //   905: athrow
/* 396:    */     //   906: astore_3
/* 397:    */     //   907: new 41	java/lang/RuntimeException
/* 398:    */     //   910: dup
/* 399:    */     //   911: aload_3
/* 400:    */     //   912: invokevirtual 42	java/sql/SQLException:toString	()Ljava/lang/String;
/* 401:    */     //   915: aload_3
/* 402:    */     //   916: invokespecial 43	java/lang/RuntimeException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
/* 403:    */     //   919: athrow
/* 404:    */     //   920: astore 23
/* 405:    */     //   922: aload_0
/* 406:    */     //   923: getfield 2	nxt/db/VersionedEntityDbTable:table	Ljava/lang/String;
/* 407:    */     //   926: invokestatic 37	nxt/db/Db:getCache	(Ljava/lang/String;)Ljava/util/Map;
/* 408:    */     //   929: aload_2
/* 409:    */     //   930: invokeinterface 38 2 0
/* 410:    */     //   935: pop
/* 411:    */     //   936: aload 23
/* 412:    */     //   938: athrow
/* 413:    */     // Line number table:
/* 414:    */     //   Java source line #24	-> byte code offset #0
/* 415:    */     //   Java source line #25	-> byte code offset #4
/* 416:    */     //   Java source line #27	-> byte code offset #6
/* 417:    */     //   Java source line #28	-> byte code offset #12
/* 418:    */     //   Java source line #30	-> byte code offset #22
/* 419:    */     //   Java source line #31	-> byte code offset #31
/* 420:    */     //   Java source line #32	-> byte code offset #38
/* 421:    */     //   Java source line #31	-> byte code offset #83
/* 422:    */     //   Java source line #34	-> byte code offset #86
/* 423:    */     //   Java source line #35	-> byte code offset #96
/* 424:    */     //   Java source line #36	-> byte code offset #113
/* 425:    */     //   Java source line #37	-> byte code offset #125
/* 426:    */     //   Java source line #38	-> byte code offset #133
/* 427:    */     //   Java source line #39	-> byte code offset #145
/* 428:    */     //   Java source line #41	-> byte code offset #198
/* 429:    */     //   Java source line #42	-> byte code offset #207
/* 430:    */     //   Java source line #43	-> byte code offset #215
/* 431:    */     //   Java source line #44	-> byte code offset #221
/* 432:    */     //   Java source line #45	-> byte code offset #229
/* 433:    */     //   Java source line #39	-> byte code offset #271
/* 434:    */     //   Java source line #45	-> byte code offset #280
/* 435:    */     //   Java source line #46	-> byte code offset #324
/* 436:    */     //   Java source line #53	-> byte code offset #327
/* 437:    */     //   Java source line #54	-> byte code offset #366
/* 438:    */     //   Java source line #57	-> byte code offset #441
/* 439:    */     //   Java source line #48	-> byte code offset #458
/* 440:    */     //   Java source line #49	-> byte code offset #501
/* 441:    */     //   Java source line #50	-> byte code offset #510
/* 442:    */     //   Java source line #51	-> byte code offset #527
/* 443:    */     //   Java source line #53	-> byte code offset #566
/* 444:    */     //   Java source line #54	-> byte code offset #605
/* 445:    */     //   Java source line #57	-> byte code offset #680
/* 446:    */     //   Java source line #48	-> byte code offset #697
/* 447:    */     //   Java source line #51	-> byte code offset #706
/* 448:    */     //   Java source line #36	-> byte code offset #750
/* 449:    */     //   Java source line #53	-> byte code offset #759
/* 450:    */     //   Java source line #31	-> byte code offset #803
/* 451:    */     //   Java source line #54	-> byte code offset #812
/* 452:    */     //   Java source line #31	-> byte code offset #856
/* 453:    */     //   Java source line #54	-> byte code offset #865
/* 454:    */     //   Java source line #55	-> byte code offset #907
/* 455:    */     //   Java source line #57	-> byte code offset #920
/* 456:    */     // Local variable table:
/* 457:    */     //   start	length	slot	name	signature
/* 458:    */     //   0	939	0	this	VersionedEntityDbTable
/* 459:    */     //   0	939	1	paramT	T
/* 460:    */     //   30	900	2	localDbKey	DbKey
/* 461:    */     //   34	864	3	localConnection	Connection
/* 462:    */     //   906	10	3	localSQLException	SQLException
/* 463:    */     //   36	852	4	localObject1	Object
/* 464:    */     //   81	766	5	localPreparedStatement1	PreparedStatement
/* 465:    */     //   856	7	5	localThrowable1	Throwable
/* 466:    */     //   84	753	6	localObject2	Object
/* 467:    */     //   94	5	7	i	int
/* 468:    */     //   803	7	7	localThrowable2	Throwable
/* 469:    */     //   120	674	8	localResultSet	ResultSet
/* 470:    */     //   123	661	9	localObject3	Object
/* 471:    */     //   193	122	10	localPreparedStatement2	PreparedStatement
/* 472:    */     //   325	131	10	bool1	boolean
/* 473:    */     //   496	245	10	localPreparedStatement3	PreparedStatement
/* 474:    */     //   750	7	10	localThrowable3	Throwable
/* 475:    */     //   196	109	11	localObject4	Object
/* 476:    */     //   347	5	11	localThrowable4	Throwable
/* 477:    */     //   386	5	11	localThrowable5	Throwable
/* 478:    */     //   423	5	11	localThrowable6	Throwable
/* 479:    */     //   499	232	11	localObject5	Object
/* 480:    */     //   249	5	12	localThrowable7	Throwable
/* 481:    */     //   271	7	12	localThrowable8	Throwable
/* 482:    */     //   525	170	12	bool2	boolean
/* 483:    */     //   697	7	12	localThrowable9	Throwable
/* 484:    */     //   280	42	13	localObject6	Object
/* 485:    */     //   547	5	13	localThrowable10	Throwable
/* 486:    */     //   586	5	13	localThrowable11	Throwable
/* 487:    */     //   625	5	13	localThrowable12	Throwable
/* 488:    */     //   662	5	13	localThrowable13	Throwable
/* 489:    */     //   302	5	14	localThrowable14	Throwable
/* 490:    */     //   706	42	15	localObject7	Object
/* 491:    */     //   728	5	16	localThrowable15	Throwable
/* 492:    */     //   759	42	17	localObject8	Object
/* 493:    */     //   781	5	18	localThrowable16	Throwable
/* 494:    */     //   812	42	19	localObject9	Object
/* 495:    */     //   834	5	20	localThrowable17	Throwable
/* 496:    */     //   865	39	21	localObject10	Object
/* 497:    */     //   885	5	22	localThrowable18	Throwable
/* 498:    */     //   920	17	23	localObject11	Object
/* 499:    */     // Exception table:
/* 500:    */     //   from	to	target	type
/* 501:    */     //   239	246	249	java/lang/Throwable
/* 502:    */     //   198	229	271	java/lang/Throwable
/* 503:    */     //   198	229	280	finally
/* 504:    */     //   271	282	280	finally
/* 505:    */     //   292	299	302	java/lang/Throwable
/* 506:    */     //   337	344	347	java/lang/Throwable
/* 507:    */     //   376	383	386	java/lang/Throwable
/* 508:    */     //   414	420	423	java/lang/Throwable
/* 509:    */     //   537	544	547	java/lang/Throwable
/* 510:    */     //   576	583	586	java/lang/Throwable
/* 511:    */     //   615	622	625	java/lang/Throwable
/* 512:    */     //   653	659	662	java/lang/Throwable
/* 513:    */     //   501	527	697	java/lang/Throwable
/* 514:    */     //   501	527	706	finally
/* 515:    */     //   697	708	706	finally
/* 516:    */     //   718	725	728	java/lang/Throwable
/* 517:    */     //   125	327	750	java/lang/Throwable
/* 518:    */     //   458	566	750	java/lang/Throwable
/* 519:    */     //   697	750	750	java/lang/Throwable
/* 520:    */     //   125	327	759	finally
/* 521:    */     //   458	566	759	finally
/* 522:    */     //   697	761	759	finally
/* 523:    */     //   771	778	781	java/lang/Throwable
/* 524:    */     //   86	366	803	java/lang/Throwable
/* 525:    */     //   458	605	803	java/lang/Throwable
/* 526:    */     //   697	803	803	java/lang/Throwable
/* 527:    */     //   86	366	812	finally
/* 528:    */     //   458	605	812	finally
/* 529:    */     //   697	814	812	finally
/* 530:    */     //   824	831	834	java/lang/Throwable
/* 531:    */     //   38	405	856	java/lang/Throwable
/* 532:    */     //   458	644	856	java/lang/Throwable
/* 533:    */     //   697	856	856	java/lang/Throwable
/* 534:    */     //   38	405	865	finally
/* 535:    */     //   458	644	865	finally
/* 536:    */     //   697	867	865	finally
/* 537:    */     //   876	882	885	java/lang/Throwable
/* 538:    */     //   31	441	906	java/sql/SQLException
/* 539:    */     //   458	680	906	java/sql/SQLException
/* 540:    */     //   697	906	906	java/sql/SQLException
/* 541:    */     //   31	441	920	finally
/* 542:    */     //   458	680	920	finally
/* 543:    */     //   697	922	920	finally
/* 544:    */   }
/* 545:    */   
/* 546:    */   public final void trim(int paramInt)
/* 547:    */   {
/* 548: 63 */     trim(this.table, paramInt, this.dbKeyFactory);
/* 549:    */   }
/* 550:    */   
/* 551:    */   static void rollback(String paramString, int paramInt, DbKey.Factory paramFactory)
/* 552:    */   {
/* 553: 67 */     if (!Db.isInTransaction()) {
/* 554: 68 */       throw new IllegalStateException("Not in transaction");
/* 555:    */     }
/* 556:    */     try
/* 557:    */     {
/* 558: 70 */       Connection localConnection = Db.getConnection();Object localObject1 = null;
/* 559:    */       try
/* 560:    */       {
/* 561: 71 */         PreparedStatement localPreparedStatement1 = localConnection.prepareStatement("SELECT DISTINCT " + paramFactory.getPKColumns() + " FROM " + paramString + " WHERE height > ?");Object localObject2 = null;
/* 562:    */         try
/* 563:    */         {
/* 564: 73 */           PreparedStatement localPreparedStatement2 = localConnection.prepareStatement("DELETE FROM " + paramString + " WHERE height > ?");Object localObject3 = null;
/* 565:    */           try
/* 566:    */           {
/* 567: 75 */             PreparedStatement localPreparedStatement3 = localConnection.prepareStatement("UPDATE " + paramString + " SET latest = TRUE " + paramFactory.getPKClause() + " AND height =" + " (SELECT MAX(height) FROM " + paramString + paramFactory.getPKClause() + ")");Object localObject4 = null;
/* 568:    */             try
/* 569:    */             {
/* 570: 78 */               localPreparedStatement1.setInt(1, paramInt);
/* 571: 79 */               ArrayList localArrayList = new ArrayList();
/* 572: 80 */               localObject5 = localPreparedStatement1.executeQuery();localObject6 = null;
/* 573:    */               try
/* 574:    */               {
/* 575: 81 */                 while (((ResultSet)localObject5).next()) {
/* 576: 82 */                   localArrayList.add(paramFactory.newKey((ResultSet)localObject5));
/* 577:    */                 }
/* 578:    */               }
/* 579:    */               catch (Throwable localThrowable10)
/* 580:    */               {
/* 581: 80 */                 localObject6 = localThrowable10;throw localThrowable10;
/* 582:    */               }
/* 583:    */               finally
/* 584:    */               {
/* 585: 84 */                 if (localObject5 != null) {
/* 586: 84 */                   if (localObject6 != null) {
/* 587:    */                     try
/* 588:    */                     {
/* 589: 84 */                       ((ResultSet)localObject5).close();
/* 590:    */                     }
/* 591:    */                     catch (Throwable localThrowable11)
/* 592:    */                     {
/* 593: 84 */                       ((Throwable)localObject6).addSuppressed(localThrowable11);
/* 594:    */                     }
/* 595:    */                   } else {
/* 596: 84 */                     ((ResultSet)localObject5).close();
/* 597:    */                   }
/* 598:    */                 }
/* 599:    */               }
/* 600: 85 */               localPreparedStatement2.setInt(1, paramInt);
/* 601: 86 */               localPreparedStatement2.executeUpdate();
/* 602: 87 */               for (localObject5 = localArrayList.iterator(); ((Iterator)localObject5).hasNext();)
/* 603:    */               {
/* 604: 87 */                 localObject6 = (DbKey)((Iterator)localObject5).next();
/* 605: 88 */                 int i = 1;
/* 606: 89 */                 i = ((DbKey)localObject6).setPK(localPreparedStatement3, i);
/* 607: 90 */                 i = ((DbKey)localObject6).setPK(localPreparedStatement3, i);
/* 608: 91 */                 localPreparedStatement3.executeUpdate();
/* 609:    */               }
/* 610:    */             }
/* 611:    */             catch (Throwable localThrowable8)
/* 612:    */             {
/* 613:    */               Object localObject5;
/* 614:    */               Object localObject6;
/* 615: 70 */               localObject4 = localThrowable8;throw localThrowable8;
/* 616:    */             }
/* 617:    */             finally {}
/* 618:    */           }
/* 619:    */           catch (Throwable localThrowable6)
/* 620:    */           {
/* 621: 70 */             localObject3 = localThrowable6;throw localThrowable6;
/* 622:    */           }
/* 623:    */           finally {}
/* 624:    */         }
/* 625:    */         catch (Throwable localThrowable4)
/* 626:    */         {
/* 627: 70 */           localObject2 = localThrowable4;throw localThrowable4;
/* 628:    */         }
/* 629:    */         finally {}
/* 630:    */       }
/* 631:    */       catch (Throwable localThrowable2)
/* 632:    */       {
/* 633: 70 */         localObject1 = localThrowable2;throw localThrowable2;
/* 634:    */       }
/* 635:    */       finally
/* 636:    */       {
/* 637: 94 */         if (localConnection != null) {
/* 638: 94 */           if (localObject1 != null) {
/* 639:    */             try
/* 640:    */             {
/* 641: 94 */               localConnection.close();
/* 642:    */             }
/* 643:    */             catch (Throwable localThrowable15)
/* 644:    */             {
/* 645: 94 */               ((Throwable)localObject1).addSuppressed(localThrowable15);
/* 646:    */             }
/* 647:    */           } else {
/* 648: 94 */             localConnection.close();
/* 649:    */           }
/* 650:    */         }
/* 651:    */       }
/* 652:    */     }
/* 653:    */     catch (SQLException localSQLException)
/* 654:    */     {
/* 655: 95 */       throw new RuntimeException(localSQLException.toString(), localSQLException);
/* 656:    */     }
/* 657: 97 */     Db.getCache(paramString).clear();
/* 658:    */   }
/* 659:    */   
/* 660:    */   static void trim(String paramString, int paramInt, DbKey.Factory paramFactory)
/* 661:    */   {
/* 662:101 */     if (!Db.isInTransaction()) {
/* 663:102 */       throw new IllegalStateException("Not in transaction");
/* 664:    */     }
/* 665:    */     try
/* 666:    */     {
/* 667:104 */       Connection localConnection = Db.getConnection();Object localObject1 = null;
/* 668:    */       try
/* 669:    */       {
/* 670:105 */         PreparedStatement localPreparedStatement1 = localConnection.prepareStatement("SELECT " + paramFactory.getPKColumns() + ", MAX(height) AS max_height" + " FROM " + paramString + " WHERE height < ? GROUP BY " + paramFactory.getPKColumns() + " HAVING COUNT(DISTINCT height) > 1");Object localObject2 = null;
/* 671:    */         try
/* 672:    */         {
/* 673:107 */           PreparedStatement localPreparedStatement2 = localConnection.prepareStatement("DELETE FROM " + paramString + paramFactory.getPKClause() + " AND height < ?");Object localObject3 = null;
/* 674:    */           try
/* 675:    */           {
/* 676:109 */             PreparedStatement localPreparedStatement3 = localConnection.prepareStatement("DELETE FROM " + paramString + " WHERE height < ? AND latest = FALSE " + " AND (" + paramFactory.getPKColumns() + ") NOT IN (SELECT (" + paramFactory.getPKColumns() + ") FROM " + paramString + " WHERE height >= ?)");Object localObject4 = null;
/* 677:    */             try
/* 678:    */             {
/* 679:112 */               localPreparedStatement1.setInt(1, paramInt);
/* 680:113 */               ResultSet localResultSet = localPreparedStatement1.executeQuery();Object localObject5 = null;
/* 681:    */               try
/* 682:    */               {
/* 683:114 */                 while (localResultSet.next())
/* 684:    */                 {
/* 685:115 */                   DbKey localDbKey = paramFactory.newKey(localResultSet);
/* 686:116 */                   int i = localResultSet.getInt("max_height");
/* 687:117 */                   int j = 1;
/* 688:118 */                   j = localDbKey.setPK(localPreparedStatement2, j);
/* 689:119 */                   localPreparedStatement2.setInt(j, i);
/* 690:120 */                   localPreparedStatement2.executeUpdate();
/* 691:    */                 }
/* 692:122 */                 localPreparedStatement3.setInt(1, paramInt);
/* 693:123 */                 localPreparedStatement3.setInt(2, paramInt);
/* 694:124 */                 localPreparedStatement3.executeUpdate();
/* 695:    */               }
/* 696:    */               catch (Throwable localThrowable10)
/* 697:    */               {
/* 698:113 */                 localObject5 = localThrowable10;throw localThrowable10;
/* 699:    */               }
/* 700:    */               finally {}
/* 701:    */             }
/* 702:    */             catch (Throwable localThrowable8)
/* 703:    */             {
/* 704:104 */               localObject4 = localThrowable8;throw localThrowable8;
/* 705:    */             }
/* 706:    */             finally {}
/* 707:    */           }
/* 708:    */           catch (Throwable localThrowable6)
/* 709:    */           {
/* 710:104 */             localObject3 = localThrowable6;throw localThrowable6;
/* 711:    */           }
/* 712:    */           finally {}
/* 713:    */         }
/* 714:    */         catch (Throwable localThrowable4)
/* 715:    */         {
/* 716:104 */           localObject2 = localThrowable4;throw localThrowable4;
/* 717:    */         }
/* 718:    */         finally {}
/* 719:    */       }
/* 720:    */       catch (Throwable localThrowable2)
/* 721:    */       {
/* 722:104 */         localObject1 = localThrowable2;throw localThrowable2;
/* 723:    */       }
/* 724:    */       finally
/* 725:    */       {
/* 726:126 */         if (localConnection != null) {
/* 727:126 */           if (localObject1 != null) {
/* 728:    */             try
/* 729:    */             {
/* 730:126 */               localConnection.close();
/* 731:    */             }
/* 732:    */             catch (Throwable localThrowable15)
/* 733:    */             {
/* 734:126 */               ((Throwable)localObject1).addSuppressed(localThrowable15);
/* 735:    */             }
/* 736:    */           } else {
/* 737:126 */             localConnection.close();
/* 738:    */           }
/* 739:    */         }
/* 740:    */       }
/* 741:    */     }
/* 742:    */     catch (SQLException localSQLException)
/* 743:    */     {
/* 744:127 */       throw new RuntimeException(localSQLException.toString(), localSQLException);
/* 745:    */     }
/* 746:    */   }
/* 747:    */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.db.VersionedEntityDbTable
 * JD-Core Version:    0.7.1
 */