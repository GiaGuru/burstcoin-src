/*   1:    */ package fr.cryptohash;
/*   2:    */ 
/*   3:    */ public class Luffa384
/*   4:    */   extends DigestEngine
/*   5:    */ {
/*   6: 42 */   private static final int[] IV = { 1831149161, 1152405984, 1319792564, -604535707, 1848188945, -1877660172, -301629127, -554299205, -1011594347, -640486826, 1894705568, -569794653, 1570440535, -1882635085, -820195570, 1953289601, -135280483, 1572493185, 67202277, -1385849851, 50731343, 1718425654, 615129866, -1960424729, -2055178795, 920100046, -445515817, 541794151, 898042986, 1474947363, 347912200, 2094953166 };
/*   7: 53 */   private static final int[] RC00 = { 809079974, -1058647399, 1824733714, -598304706, 503320719, 2013282877, -1889830782, -1763583214 };
/*   8: 58 */   private static final int[] RC04 = { -533497832, 1142663437, 2134168642, -1819729537, -441926426, 1383381748, 646486951, -1709019491 };
/*   9: 63 */   private static final int[] RC10 = { -1226960659, 1895070382, 117941204, 471764817, 1887059269, -1364032158, -1161161335, 1084518206 };
/*  10: 68 */   private static final int[] RC14 = { 23617341, 94469364, -1123431734, -198759640, 340452812, -89674197, 776532417, -1188837628 };
/*  11: 73 */   private static final int[] RC20 = { -64955950, 877997605, 2061009295, -2076674486, -1150427086, -306741048, -645631146, -1563982796 };
/*  12: 78 */   private static final int[] RC24 = { -497126719, -433865870, 1549313188, 507044583, 2028178333, 660104985, 921544063, 1882893543 };
/*  13: 83 */   private static final int[] RC30 = { -1307332699, -934363499, 1314949666, 1457019134, 876286863, -789819843, 753617026, -1280499192 };
/*  14: 88 */   private static final int[] RC34 = { -534197825, 1148546961, 2123353650, -1788524354, -31908894, 1018308325, 1497670286, -1580940459 };
/*  15:    */   private int V00;
/*  16:    */   private int V01;
/*  17:    */   private int V02;
/*  18:    */   private int V03;
/*  19:    */   private int V04;
/*  20:    */   private int V05;
/*  21:    */   private int V06;
/*  22:    */   private int V07;
/*  23:    */   private int V10;
/*  24:    */   private int V11;
/*  25:    */   private int V12;
/*  26:    */   private int V13;
/*  27:    */   private int V14;
/*  28:    */   private int V15;
/*  29:    */   private int V16;
/*  30:    */   private int V17;
/*  31:    */   private int V20;
/*  32:    */   private int V21;
/*  33:    */   private int V22;
/*  34:    */   private int V23;
/*  35:    */   private int V24;
/*  36:    */   private int V25;
/*  37:    */   private int V26;
/*  38:    */   private int V27;
/*  39:    */   private int V30;
/*  40:    */   private int V31;
/*  41:    */   private int V32;
/*  42:    */   private int V33;
/*  43:    */   private int V34;
/*  44:    */   private int V35;
/*  45:    */   private int V36;
/*  46:    */   private int V37;
/*  47:    */   private byte[] tmpBuf;
/*  48:    */   
/*  49:    */   public int getInternalBlockLength()
/*  50:    */   {
/*  51:110 */     return 32;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public int getBlockLength()
/*  55:    */   {
/*  56:122 */     return -32;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public int getDigestLength()
/*  60:    */   {
/*  61:128 */     return 48;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public Digest copy()
/*  65:    */   {
/*  66:134 */     return copyState(new Luffa384());
/*  67:    */   }
/*  68:    */   
/*  69:    */   protected Digest copyState(Luffa384 paramLuffa384)
/*  70:    */   {
/*  71:140 */     paramLuffa384.V00 = this.V00;
/*  72:141 */     paramLuffa384.V01 = this.V01;
/*  73:142 */     paramLuffa384.V02 = this.V02;
/*  74:143 */     paramLuffa384.V03 = this.V03;
/*  75:144 */     paramLuffa384.V04 = this.V04;
/*  76:145 */     paramLuffa384.V05 = this.V05;
/*  77:146 */     paramLuffa384.V06 = this.V06;
/*  78:147 */     paramLuffa384.V07 = this.V07;
/*  79:148 */     paramLuffa384.V10 = this.V10;
/*  80:149 */     paramLuffa384.V11 = this.V11;
/*  81:150 */     paramLuffa384.V12 = this.V12;
/*  82:151 */     paramLuffa384.V13 = this.V13;
/*  83:152 */     paramLuffa384.V14 = this.V14;
/*  84:153 */     paramLuffa384.V15 = this.V15;
/*  85:154 */     paramLuffa384.V16 = this.V16;
/*  86:155 */     paramLuffa384.V17 = this.V17;
/*  87:156 */     paramLuffa384.V20 = this.V20;
/*  88:157 */     paramLuffa384.V21 = this.V21;
/*  89:158 */     paramLuffa384.V22 = this.V22;
/*  90:159 */     paramLuffa384.V23 = this.V23;
/*  91:160 */     paramLuffa384.V24 = this.V24;
/*  92:161 */     paramLuffa384.V25 = this.V25;
/*  93:162 */     paramLuffa384.V26 = this.V26;
/*  94:163 */     paramLuffa384.V27 = this.V27;
/*  95:164 */     paramLuffa384.V30 = this.V30;
/*  96:165 */     paramLuffa384.V31 = this.V31;
/*  97:166 */     paramLuffa384.V32 = this.V32;
/*  98:167 */     paramLuffa384.V33 = this.V33;
/*  99:168 */     paramLuffa384.V34 = this.V34;
/* 100:169 */     paramLuffa384.V35 = this.V35;
/* 101:170 */     paramLuffa384.V36 = this.V36;
/* 102:171 */     paramLuffa384.V37 = this.V37;
/* 103:172 */     return super.copyState(paramLuffa384);
/* 104:    */   }
/* 105:    */   
/* 106:    */   protected void engineReset()
/* 107:    */   {
/* 108:178 */     this.V00 = IV[0];
/* 109:179 */     this.V01 = IV[1];
/* 110:180 */     this.V02 = IV[2];
/* 111:181 */     this.V03 = IV[3];
/* 112:182 */     this.V04 = IV[4];
/* 113:183 */     this.V05 = IV[5];
/* 114:184 */     this.V06 = IV[6];
/* 115:185 */     this.V07 = IV[7];
/* 116:186 */     this.V10 = IV[8];
/* 117:187 */     this.V11 = IV[9];
/* 118:188 */     this.V12 = IV[10];
/* 119:189 */     this.V13 = IV[11];
/* 120:190 */     this.V14 = IV[12];
/* 121:191 */     this.V15 = IV[13];
/* 122:192 */     this.V16 = IV[14];
/* 123:193 */     this.V17 = IV[15];
/* 124:194 */     this.V20 = IV[16];
/* 125:195 */     this.V21 = IV[17];
/* 126:196 */     this.V22 = IV[18];
/* 127:197 */     this.V23 = IV[19];
/* 128:198 */     this.V24 = IV[20];
/* 129:199 */     this.V25 = IV[21];
/* 130:200 */     this.V26 = IV[22];
/* 131:201 */     this.V27 = IV[23];
/* 132:202 */     this.V30 = IV[24];
/* 133:203 */     this.V31 = IV[25];
/* 134:204 */     this.V32 = IV[26];
/* 135:205 */     this.V33 = IV[27];
/* 136:206 */     this.V34 = IV[28];
/* 137:207 */     this.V35 = IV[29];
/* 138:208 */     this.V36 = IV[30];
/* 139:209 */     this.V37 = IV[31];
/* 140:    */   }
/* 141:    */   
/* 142:    */   protected void doPadding(byte[] paramArrayOfByte, int paramInt)
/* 143:    */   {
/* 144:215 */     int i = flush();
/* 145:216 */     this.tmpBuf[i] = Byte.MIN_VALUE;
/* 146:217 */     for (int j = i + 1; j < 32; j++) {
/* 147:218 */       this.tmpBuf[j] = 0;
/* 148:    */     }
/* 149:219 */     update(this.tmpBuf, i, 32 - i);
/* 150:220 */     for (j = 0; j < i + 1; j++) {
/* 151:221 */       this.tmpBuf[j] = 0;
/* 152:    */     }
/* 153:222 */     update(this.tmpBuf, 0, 32);
/* 154:223 */     encodeBEInt(this.V00 ^ this.V10 ^ this.V20 ^ this.V30, paramArrayOfByte, paramInt + 0);
/* 155:224 */     encodeBEInt(this.V01 ^ this.V11 ^ this.V21 ^ this.V31, paramArrayOfByte, paramInt + 4);
/* 156:225 */     encodeBEInt(this.V02 ^ this.V12 ^ this.V22 ^ this.V32, paramArrayOfByte, paramInt + 8);
/* 157:226 */     encodeBEInt(this.V03 ^ this.V13 ^ this.V23 ^ this.V33, paramArrayOfByte, paramInt + 12);
/* 158:227 */     encodeBEInt(this.V04 ^ this.V14 ^ this.V24 ^ this.V34, paramArrayOfByte, paramInt + 16);
/* 159:228 */     encodeBEInt(this.V05 ^ this.V15 ^ this.V25 ^ this.V35, paramArrayOfByte, paramInt + 20);
/* 160:229 */     encodeBEInt(this.V06 ^ this.V16 ^ this.V26 ^ this.V36, paramArrayOfByte, paramInt + 24);
/* 161:230 */     encodeBEInt(this.V07 ^ this.V17 ^ this.V27 ^ this.V37, paramArrayOfByte, paramInt + 28);
/* 162:231 */     update(this.tmpBuf, 0, 32);
/* 163:232 */     encodeBEInt(this.V00 ^ this.V10 ^ this.V20 ^ this.V30, paramArrayOfByte, paramInt + 32);
/* 164:233 */     encodeBEInt(this.V01 ^ this.V11 ^ this.V21 ^ this.V31, paramArrayOfByte, paramInt + 36);
/* 165:234 */     encodeBEInt(this.V02 ^ this.V12 ^ this.V22 ^ this.V32, paramArrayOfByte, paramInt + 40);
/* 166:235 */     encodeBEInt(this.V03 ^ this.V13 ^ this.V23 ^ this.V33, paramArrayOfByte, paramInt + 44);
/* 167:    */   }
/* 168:    */   
/* 169:    */   protected void doInit()
/* 170:    */   {
/* 171:241 */     this.tmpBuf = new byte[32];
/* 172:242 */     engineReset();
/* 173:    */   }
/* 174:    */   
/* 175:    */   private static final void encodeBEInt(int paramInt1, byte[] paramArrayOfByte, int paramInt2)
/* 176:    */   {
/* 177:256 */     paramArrayOfByte[(paramInt2 + 0)] = ((byte)(paramInt1 >>> 24));
/* 178:257 */     paramArrayOfByte[(paramInt2 + 1)] = ((byte)(paramInt1 >>> 16));
/* 179:258 */     paramArrayOfByte[(paramInt2 + 2)] = ((byte)(paramInt1 >>> 8));
/* 180:259 */     paramArrayOfByte[(paramInt2 + 3)] = ((byte)paramInt1);
/* 181:    */   }
/* 182:    */   
/* 183:    */   private static final int decodeBEInt(byte[] paramArrayOfByte, int paramInt)
/* 184:    */   {
/* 185:272 */     return (paramArrayOfByte[paramInt] & 0xFF) << 24 | (paramArrayOfByte[(paramInt + 1)] & 0xFF) << 16 | (paramArrayOfByte[(paramInt + 2)] & 0xFF) << 8 | paramArrayOfByte[(paramInt + 3)] & 0xFF;
/* 186:    */   }
/* 187:    */   
/* 188:    */   protected void processBlock(byte[] paramArrayOfByte)
/* 189:    */   {
/* 190:284 */     int i13 = decodeBEInt(paramArrayOfByte, 0);
/* 191:285 */     int i14 = decodeBEInt(paramArrayOfByte, 4);
/* 192:286 */     int i15 = decodeBEInt(paramArrayOfByte, 8);
/* 193:287 */     int i16 = decodeBEInt(paramArrayOfByte, 12);
/* 194:288 */     int i17 = decodeBEInt(paramArrayOfByte, 16);
/* 195:289 */     int i18 = decodeBEInt(paramArrayOfByte, 20);
/* 196:290 */     int i19 = decodeBEInt(paramArrayOfByte, 24);
/* 197:291 */     int i20 = decodeBEInt(paramArrayOfByte, 28);
/* 198:292 */     int j = this.V00 ^ this.V10;
/* 199:293 */     int k = this.V01 ^ this.V11;
/* 200:294 */     int m = this.V02 ^ this.V12;
/* 201:295 */     int n = this.V03 ^ this.V13;
/* 202:296 */     int i1 = this.V04 ^ this.V14;
/* 203:297 */     int i2 = this.V05 ^ this.V15;
/* 204:298 */     int i3 = this.V06 ^ this.V16;
/* 205:299 */     int i4 = this.V07 ^ this.V17;
/* 206:300 */     int i5 = this.V20 ^ this.V30;
/* 207:301 */     int i6 = this.V21 ^ this.V31;
/* 208:302 */     int i7 = this.V22 ^ this.V32;
/* 209:303 */     int i8 = this.V23 ^ this.V33;
/* 210:304 */     int i9 = this.V24 ^ this.V34;
/* 211:305 */     int i10 = this.V25 ^ this.V35;
/* 212:306 */     int i11 = this.V26 ^ this.V36;
/* 213:307 */     int i12 = this.V27 ^ this.V37;
/* 214:308 */     j ^= i5;
/* 215:309 */     k ^= i6;
/* 216:310 */     m ^= i7;
/* 217:311 */     n ^= i8;
/* 218:312 */     i1 ^= i9;
/* 219:313 */     i2 ^= i10;
/* 220:314 */     i3 ^= i11;
/* 221:315 */     i4 ^= i12;
/* 222:316 */     int i = i4;
/* 223:317 */     i4 = i3;
/* 224:318 */     i3 = i2;
/* 225:319 */     i2 = i1;
/* 226:320 */     i1 = n ^ i;
/* 227:321 */     n = m ^ i;
/* 228:322 */     m = k;
/* 229:323 */     k = j ^ i;
/* 230:324 */     j = i;
/* 231:325 */     this.V00 = (j ^ this.V00);
/* 232:326 */     this.V01 = (k ^ this.V01);
/* 233:327 */     this.V02 = (m ^ this.V02);
/* 234:328 */     this.V03 = (n ^ this.V03);
/* 235:329 */     this.V04 = (i1 ^ this.V04);
/* 236:330 */     this.V05 = (i2 ^ this.V05);
/* 237:331 */     this.V06 = (i3 ^ this.V06);
/* 238:332 */     this.V07 = (i4 ^ this.V07);
/* 239:333 */     this.V10 = (j ^ this.V10);
/* 240:334 */     this.V11 = (k ^ this.V11);
/* 241:335 */     this.V12 = (m ^ this.V12);
/* 242:336 */     this.V13 = (n ^ this.V13);
/* 243:337 */     this.V14 = (i1 ^ this.V14);
/* 244:338 */     this.V15 = (i2 ^ this.V15);
/* 245:339 */     this.V16 = (i3 ^ this.V16);
/* 246:340 */     this.V17 = (i4 ^ this.V17);
/* 247:341 */     this.V20 = (j ^ this.V20);
/* 248:342 */     this.V21 = (k ^ this.V21);
/* 249:343 */     this.V22 = (m ^ this.V22);
/* 250:344 */     this.V23 = (n ^ this.V23);
/* 251:345 */     this.V24 = (i1 ^ this.V24);
/* 252:346 */     this.V25 = (i2 ^ this.V25);
/* 253:347 */     this.V26 = (i3 ^ this.V26);
/* 254:348 */     this.V27 = (i4 ^ this.V27);
/* 255:349 */     this.V30 = (j ^ this.V30);
/* 256:350 */     this.V31 = (k ^ this.V31);
/* 257:351 */     this.V32 = (m ^ this.V32);
/* 258:352 */     this.V33 = (n ^ this.V33);
/* 259:353 */     this.V34 = (i1 ^ this.V34);
/* 260:354 */     this.V35 = (i2 ^ this.V35);
/* 261:355 */     this.V36 = (i3 ^ this.V36);
/* 262:356 */     this.V37 = (i4 ^ this.V37);
/* 263:357 */     i = this.V07;
/* 264:358 */     i12 = this.V06;
/* 265:359 */     i11 = this.V05;
/* 266:360 */     i10 = this.V04;
/* 267:361 */     i9 = this.V03 ^ i;
/* 268:362 */     i8 = this.V02 ^ i;
/* 269:363 */     i7 = this.V01;
/* 270:364 */     i6 = this.V00 ^ i;
/* 271:365 */     i5 = i;
/* 272:366 */     i5 ^= this.V30;
/* 273:367 */     i6 ^= this.V31;
/* 274:368 */     i7 ^= this.V32;
/* 275:369 */     i8 ^= this.V33;
/* 276:370 */     i9 ^= this.V34;
/* 277:371 */     i10 ^= this.V35;
/* 278:372 */     i11 ^= this.V36;
/* 279:373 */     i12 ^= this.V37;
/* 280:374 */     i = this.V37;
/* 281:375 */     this.V37 = this.V36;
/* 282:376 */     this.V36 = this.V35;
/* 283:377 */     this.V35 = this.V34;
/* 284:378 */     this.V34 = (this.V33 ^ i);
/* 285:379 */     this.V33 = (this.V32 ^ i);
/* 286:380 */     this.V32 = this.V31;
/* 287:381 */     this.V31 = (this.V30 ^ i);
/* 288:382 */     this.V30 = i;
/* 289:383 */     this.V30 ^= this.V20;
/* 290:384 */     this.V31 ^= this.V21;
/* 291:385 */     this.V32 ^= this.V22;
/* 292:386 */     this.V33 ^= this.V23;
/* 293:387 */     this.V34 ^= this.V24;
/* 294:388 */     this.V35 ^= this.V25;
/* 295:389 */     this.V36 ^= this.V26;
/* 296:390 */     this.V37 ^= this.V27;
/* 297:391 */     i = this.V27;
/* 298:392 */     this.V27 = this.V26;
/* 299:393 */     this.V26 = this.V25;
/* 300:394 */     this.V25 = this.V24;
/* 301:395 */     this.V24 = (this.V23 ^ i);
/* 302:396 */     this.V23 = (this.V22 ^ i);
/* 303:397 */     this.V22 = this.V21;
/* 304:398 */     this.V21 = (this.V20 ^ i);
/* 305:399 */     this.V20 = i;
/* 306:400 */     this.V20 ^= this.V10;
/* 307:401 */     this.V21 ^= this.V11;
/* 308:402 */     this.V22 ^= this.V12;
/* 309:403 */     this.V23 ^= this.V13;
/* 310:404 */     this.V24 ^= this.V14;
/* 311:405 */     this.V25 ^= this.V15;
/* 312:406 */     this.V26 ^= this.V16;
/* 313:407 */     this.V27 ^= this.V17;
/* 314:408 */     i = this.V17;
/* 315:409 */     this.V17 = this.V16;
/* 316:410 */     this.V16 = this.V15;
/* 317:411 */     this.V15 = this.V14;
/* 318:412 */     this.V14 = (this.V13 ^ i);
/* 319:413 */     this.V13 = (this.V12 ^ i);
/* 320:414 */     this.V12 = this.V11;
/* 321:415 */     this.V11 = (this.V10 ^ i);
/* 322:416 */     this.V10 = i;
/* 323:417 */     this.V10 ^= this.V00;
/* 324:418 */     this.V11 ^= this.V01;
/* 325:419 */     this.V12 ^= this.V02;
/* 326:420 */     this.V13 ^= this.V03;
/* 327:421 */     this.V14 ^= this.V04;
/* 328:422 */     this.V15 ^= this.V05;
/* 329:423 */     this.V16 ^= this.V06;
/* 330:424 */     this.V17 ^= this.V07;
/* 331:425 */     this.V00 = (i5 ^ i13);
/* 332:426 */     this.V01 = (i6 ^ i14);
/* 333:427 */     this.V02 = (i7 ^ i15);
/* 334:428 */     this.V03 = (i8 ^ i16);
/* 335:429 */     this.V04 = (i9 ^ i17);
/* 336:430 */     this.V05 = (i10 ^ i18);
/* 337:431 */     this.V06 = (i11 ^ i19);
/* 338:432 */     this.V07 = (i12 ^ i20);
/* 339:433 */     i = i20;
/* 340:434 */     i20 = i19;
/* 341:435 */     i19 = i18;
/* 342:436 */     i18 = i17;
/* 343:437 */     i17 = i16 ^ i;
/* 344:438 */     i16 = i15 ^ i;
/* 345:439 */     i15 = i14;
/* 346:440 */     i14 = i13 ^ i;
/* 347:441 */     i13 = i;
/* 348:442 */     this.V10 ^= i13;
/* 349:443 */     this.V11 ^= i14;
/* 350:444 */     this.V12 ^= i15;
/* 351:445 */     this.V13 ^= i16;
/* 352:446 */     this.V14 ^= i17;
/* 353:447 */     this.V15 ^= i18;
/* 354:448 */     this.V16 ^= i19;
/* 355:449 */     this.V17 ^= i20;
/* 356:450 */     i = i20;
/* 357:451 */     i20 = i19;
/* 358:452 */     i19 = i18;
/* 359:453 */     i18 = i17;
/* 360:454 */     i17 = i16 ^ i;
/* 361:455 */     i16 = i15 ^ i;
/* 362:456 */     i15 = i14;
/* 363:457 */     i14 = i13 ^ i;
/* 364:458 */     i13 = i;
/* 365:459 */     this.V20 ^= i13;
/* 366:460 */     this.V21 ^= i14;
/* 367:461 */     this.V22 ^= i15;
/* 368:462 */     this.V23 ^= i16;
/* 369:463 */     this.V24 ^= i17;
/* 370:464 */     this.V25 ^= i18;
/* 371:465 */     this.V26 ^= i19;
/* 372:466 */     this.V27 ^= i20;
/* 373:467 */     i = i20;
/* 374:468 */     i20 = i19;
/* 375:469 */     i19 = i18;
/* 376:470 */     i18 = i17;
/* 377:471 */     i17 = i16 ^ i;
/* 378:472 */     i16 = i15 ^ i;
/* 379:473 */     i15 = i14;
/* 380:474 */     i14 = i13 ^ i;
/* 381:475 */     i13 = i;
/* 382:476 */     this.V30 ^= i13;
/* 383:477 */     this.V31 ^= i14;
/* 384:478 */     this.V32 ^= i15;
/* 385:479 */     this.V33 ^= i16;
/* 386:480 */     this.V34 ^= i17;
/* 387:481 */     this.V35 ^= i18;
/* 388:482 */     this.V36 ^= i19;
/* 389:483 */     this.V37 ^= i20;
/* 390:484 */     this.V14 = (this.V14 << 1 | this.V14 >>> 31);
/* 391:485 */     this.V15 = (this.V15 << 1 | this.V15 >>> 31);
/* 392:486 */     this.V16 = (this.V16 << 1 | this.V16 >>> 31);
/* 393:487 */     this.V17 = (this.V17 << 1 | this.V17 >>> 31);
/* 394:488 */     this.V24 = (this.V24 << 2 | this.V24 >>> 30);
/* 395:489 */     this.V25 = (this.V25 << 2 | this.V25 >>> 30);
/* 396:490 */     this.V26 = (this.V26 << 2 | this.V26 >>> 30);
/* 397:491 */     this.V27 = (this.V27 << 2 | this.V27 >>> 30);
/* 398:492 */     this.V34 = (this.V34 << 3 | this.V34 >>> 29);
/* 399:493 */     this.V35 = (this.V35 << 3 | this.V35 >>> 29);
/* 400:494 */     this.V36 = (this.V36 << 3 | this.V36 >>> 29);
/* 401:495 */     this.V37 = (this.V37 << 3 | this.V37 >>> 29);
/* 402:496 */     for (int i21 = 0; i21 < 8; i21++)
/* 403:    */     {
/* 404:497 */       i = this.V00;
/* 405:498 */       this.V00 |= this.V01;
/* 406:499 */       this.V02 ^= this.V03;
/* 407:500 */       this.V01 ^= 0xFFFFFFFF;
/* 408:501 */       this.V00 ^= this.V03;
/* 409:502 */       this.V03 &= i;
/* 410:503 */       this.V01 ^= this.V03;
/* 411:504 */       this.V03 ^= this.V02;
/* 412:505 */       this.V02 &= this.V00;
/* 413:506 */       this.V00 ^= 0xFFFFFFFF;
/* 414:507 */       this.V02 ^= this.V01;
/* 415:508 */       this.V01 |= this.V03;
/* 416:509 */       i ^= this.V01;
/* 417:510 */       this.V03 ^= this.V02;
/* 418:511 */       this.V02 &= this.V01;
/* 419:512 */       this.V01 ^= this.V00;
/* 420:513 */       this.V00 = i;
/* 421:514 */       i = this.V05;
/* 422:515 */       this.V05 |= this.V06;
/* 423:516 */       this.V07 ^= this.V04;
/* 424:517 */       this.V06 ^= 0xFFFFFFFF;
/* 425:518 */       this.V05 ^= this.V04;
/* 426:519 */       this.V04 &= i;
/* 427:520 */       this.V06 ^= this.V04;
/* 428:521 */       this.V04 ^= this.V07;
/* 429:522 */       this.V07 &= this.V05;
/* 430:523 */       this.V05 ^= 0xFFFFFFFF;
/* 431:524 */       this.V07 ^= this.V06;
/* 432:525 */       this.V06 |= this.V04;
/* 433:526 */       i ^= this.V06;
/* 434:527 */       this.V04 ^= this.V07;
/* 435:528 */       this.V07 &= this.V06;
/* 436:529 */       this.V06 ^= this.V05;
/* 437:530 */       this.V05 = i;
/* 438:531 */       this.V04 ^= this.V00;
/* 439:532 */       this.V00 = ((this.V00 << 2 | this.V00 >>> 30) ^ this.V04);
/* 440:533 */       this.V04 = ((this.V04 << 14 | this.V04 >>> 18) ^ this.V00);
/* 441:534 */       this.V00 = ((this.V00 << 10 | this.V00 >>> 22) ^ this.V04);
/* 442:535 */       this.V04 = (this.V04 << 1 | this.V04 >>> 31);
/* 443:536 */       this.V05 ^= this.V01;
/* 444:537 */       this.V01 = ((this.V01 << 2 | this.V01 >>> 30) ^ this.V05);
/* 445:538 */       this.V05 = ((this.V05 << 14 | this.V05 >>> 18) ^ this.V01);
/* 446:539 */       this.V01 = ((this.V01 << 10 | this.V01 >>> 22) ^ this.V05);
/* 447:540 */       this.V05 = (this.V05 << 1 | this.V05 >>> 31);
/* 448:541 */       this.V06 ^= this.V02;
/* 449:542 */       this.V02 = ((this.V02 << 2 | this.V02 >>> 30) ^ this.V06);
/* 450:543 */       this.V06 = ((this.V06 << 14 | this.V06 >>> 18) ^ this.V02);
/* 451:544 */       this.V02 = ((this.V02 << 10 | this.V02 >>> 22) ^ this.V06);
/* 452:545 */       this.V06 = (this.V06 << 1 | this.V06 >>> 31);
/* 453:546 */       this.V07 ^= this.V03;
/* 454:547 */       this.V03 = ((this.V03 << 2 | this.V03 >>> 30) ^ this.V07);
/* 455:548 */       this.V07 = ((this.V07 << 14 | this.V07 >>> 18) ^ this.V03);
/* 456:549 */       this.V03 = ((this.V03 << 10 | this.V03 >>> 22) ^ this.V07);
/* 457:550 */       this.V07 = (this.V07 << 1 | this.V07 >>> 31);
/* 458:551 */       this.V00 ^= RC00[i21];
/* 459:552 */       this.V04 ^= RC04[i21];
/* 460:    */     }
/* 461:554 */     for (i21 = 0; i21 < 8; i21++)
/* 462:    */     {
/* 463:555 */       i = this.V10;
/* 464:556 */       this.V10 |= this.V11;
/* 465:557 */       this.V12 ^= this.V13;
/* 466:558 */       this.V11 ^= 0xFFFFFFFF;
/* 467:559 */       this.V10 ^= this.V13;
/* 468:560 */       this.V13 &= i;
/* 469:561 */       this.V11 ^= this.V13;
/* 470:562 */       this.V13 ^= this.V12;
/* 471:563 */       this.V12 &= this.V10;
/* 472:564 */       this.V10 ^= 0xFFFFFFFF;
/* 473:565 */       this.V12 ^= this.V11;
/* 474:566 */       this.V11 |= this.V13;
/* 475:567 */       i ^= this.V11;
/* 476:568 */       this.V13 ^= this.V12;
/* 477:569 */       this.V12 &= this.V11;
/* 478:570 */       this.V11 ^= this.V10;
/* 479:571 */       this.V10 = i;
/* 480:572 */       i = this.V15;
/* 481:573 */       this.V15 |= this.V16;
/* 482:574 */       this.V17 ^= this.V14;
/* 483:575 */       this.V16 ^= 0xFFFFFFFF;
/* 484:576 */       this.V15 ^= this.V14;
/* 485:577 */       this.V14 &= i;
/* 486:578 */       this.V16 ^= this.V14;
/* 487:579 */       this.V14 ^= this.V17;
/* 488:580 */       this.V17 &= this.V15;
/* 489:581 */       this.V15 ^= 0xFFFFFFFF;
/* 490:582 */       this.V17 ^= this.V16;
/* 491:583 */       this.V16 |= this.V14;
/* 492:584 */       i ^= this.V16;
/* 493:585 */       this.V14 ^= this.V17;
/* 494:586 */       this.V17 &= this.V16;
/* 495:587 */       this.V16 ^= this.V15;
/* 496:588 */       this.V15 = i;
/* 497:589 */       this.V14 ^= this.V10;
/* 498:590 */       this.V10 = ((this.V10 << 2 | this.V10 >>> 30) ^ this.V14);
/* 499:591 */       this.V14 = ((this.V14 << 14 | this.V14 >>> 18) ^ this.V10);
/* 500:592 */       this.V10 = ((this.V10 << 10 | this.V10 >>> 22) ^ this.V14);
/* 501:593 */       this.V14 = (this.V14 << 1 | this.V14 >>> 31);
/* 502:594 */       this.V15 ^= this.V11;
/* 503:595 */       this.V11 = ((this.V11 << 2 | this.V11 >>> 30) ^ this.V15);
/* 504:596 */       this.V15 = ((this.V15 << 14 | this.V15 >>> 18) ^ this.V11);
/* 505:597 */       this.V11 = ((this.V11 << 10 | this.V11 >>> 22) ^ this.V15);
/* 506:598 */       this.V15 = (this.V15 << 1 | this.V15 >>> 31);
/* 507:599 */       this.V16 ^= this.V12;
/* 508:600 */       this.V12 = ((this.V12 << 2 | this.V12 >>> 30) ^ this.V16);
/* 509:601 */       this.V16 = ((this.V16 << 14 | this.V16 >>> 18) ^ this.V12);
/* 510:602 */       this.V12 = ((this.V12 << 10 | this.V12 >>> 22) ^ this.V16);
/* 511:603 */       this.V16 = (this.V16 << 1 | this.V16 >>> 31);
/* 512:604 */       this.V17 ^= this.V13;
/* 513:605 */       this.V13 = ((this.V13 << 2 | this.V13 >>> 30) ^ this.V17);
/* 514:606 */       this.V17 = ((this.V17 << 14 | this.V17 >>> 18) ^ this.V13);
/* 515:607 */       this.V13 = ((this.V13 << 10 | this.V13 >>> 22) ^ this.V17);
/* 516:608 */       this.V17 = (this.V17 << 1 | this.V17 >>> 31);
/* 517:609 */       this.V10 ^= RC10[i21];
/* 518:610 */       this.V14 ^= RC14[i21];
/* 519:    */     }
/* 520:612 */     for (i21 = 0; i21 < 8; i21++)
/* 521:    */     {
/* 522:613 */       i = this.V20;
/* 523:614 */       this.V20 |= this.V21;
/* 524:615 */       this.V22 ^= this.V23;
/* 525:616 */       this.V21 ^= 0xFFFFFFFF;
/* 526:617 */       this.V20 ^= this.V23;
/* 527:618 */       this.V23 &= i;
/* 528:619 */       this.V21 ^= this.V23;
/* 529:620 */       this.V23 ^= this.V22;
/* 530:621 */       this.V22 &= this.V20;
/* 531:622 */       this.V20 ^= 0xFFFFFFFF;
/* 532:623 */       this.V22 ^= this.V21;
/* 533:624 */       this.V21 |= this.V23;
/* 534:625 */       i ^= this.V21;
/* 535:626 */       this.V23 ^= this.V22;
/* 536:627 */       this.V22 &= this.V21;
/* 537:628 */       this.V21 ^= this.V20;
/* 538:629 */       this.V20 = i;
/* 539:630 */       i = this.V25;
/* 540:631 */       this.V25 |= this.V26;
/* 541:632 */       this.V27 ^= this.V24;
/* 542:633 */       this.V26 ^= 0xFFFFFFFF;
/* 543:634 */       this.V25 ^= this.V24;
/* 544:635 */       this.V24 &= i;
/* 545:636 */       this.V26 ^= this.V24;
/* 546:637 */       this.V24 ^= this.V27;
/* 547:638 */       this.V27 &= this.V25;
/* 548:639 */       this.V25 ^= 0xFFFFFFFF;
/* 549:640 */       this.V27 ^= this.V26;
/* 550:641 */       this.V26 |= this.V24;
/* 551:642 */       i ^= this.V26;
/* 552:643 */       this.V24 ^= this.V27;
/* 553:644 */       this.V27 &= this.V26;
/* 554:645 */       this.V26 ^= this.V25;
/* 555:646 */       this.V25 = i;
/* 556:647 */       this.V24 ^= this.V20;
/* 557:648 */       this.V20 = ((this.V20 << 2 | this.V20 >>> 30) ^ this.V24);
/* 558:649 */       this.V24 = ((this.V24 << 14 | this.V24 >>> 18) ^ this.V20);
/* 559:650 */       this.V20 = ((this.V20 << 10 | this.V20 >>> 22) ^ this.V24);
/* 560:651 */       this.V24 = (this.V24 << 1 | this.V24 >>> 31);
/* 561:652 */       this.V25 ^= this.V21;
/* 562:653 */       this.V21 = ((this.V21 << 2 | this.V21 >>> 30) ^ this.V25);
/* 563:654 */       this.V25 = ((this.V25 << 14 | this.V25 >>> 18) ^ this.V21);
/* 564:655 */       this.V21 = ((this.V21 << 10 | this.V21 >>> 22) ^ this.V25);
/* 565:656 */       this.V25 = (this.V25 << 1 | this.V25 >>> 31);
/* 566:657 */       this.V26 ^= this.V22;
/* 567:658 */       this.V22 = ((this.V22 << 2 | this.V22 >>> 30) ^ this.V26);
/* 568:659 */       this.V26 = ((this.V26 << 14 | this.V26 >>> 18) ^ this.V22);
/* 569:660 */       this.V22 = ((this.V22 << 10 | this.V22 >>> 22) ^ this.V26);
/* 570:661 */       this.V26 = (this.V26 << 1 | this.V26 >>> 31);
/* 571:662 */       this.V27 ^= this.V23;
/* 572:663 */       this.V23 = ((this.V23 << 2 | this.V23 >>> 30) ^ this.V27);
/* 573:664 */       this.V27 = ((this.V27 << 14 | this.V27 >>> 18) ^ this.V23);
/* 574:665 */       this.V23 = ((this.V23 << 10 | this.V23 >>> 22) ^ this.V27);
/* 575:666 */       this.V27 = (this.V27 << 1 | this.V27 >>> 31);
/* 576:667 */       this.V20 ^= RC20[i21];
/* 577:668 */       this.V24 ^= RC24[i21];
/* 578:    */     }
/* 579:670 */     for (i21 = 0; i21 < 8; i21++)
/* 580:    */     {
/* 581:671 */       i = this.V30;
/* 582:672 */       this.V30 |= this.V31;
/* 583:673 */       this.V32 ^= this.V33;
/* 584:674 */       this.V31 ^= 0xFFFFFFFF;
/* 585:675 */       this.V30 ^= this.V33;
/* 586:676 */       this.V33 &= i;
/* 587:677 */       this.V31 ^= this.V33;
/* 588:678 */       this.V33 ^= this.V32;
/* 589:679 */       this.V32 &= this.V30;
/* 590:680 */       this.V30 ^= 0xFFFFFFFF;
/* 591:681 */       this.V32 ^= this.V31;
/* 592:682 */       this.V31 |= this.V33;
/* 593:683 */       i ^= this.V31;
/* 594:684 */       this.V33 ^= this.V32;
/* 595:685 */       this.V32 &= this.V31;
/* 596:686 */       this.V31 ^= this.V30;
/* 597:687 */       this.V30 = i;
/* 598:688 */       i = this.V35;
/* 599:689 */       this.V35 |= this.V36;
/* 600:690 */       this.V37 ^= this.V34;
/* 601:691 */       this.V36 ^= 0xFFFFFFFF;
/* 602:692 */       this.V35 ^= this.V34;
/* 603:693 */       this.V34 &= i;
/* 604:694 */       this.V36 ^= this.V34;
/* 605:695 */       this.V34 ^= this.V37;
/* 606:696 */       this.V37 &= this.V35;
/* 607:697 */       this.V35 ^= 0xFFFFFFFF;
/* 608:698 */       this.V37 ^= this.V36;
/* 609:699 */       this.V36 |= this.V34;
/* 610:700 */       i ^= this.V36;
/* 611:701 */       this.V34 ^= this.V37;
/* 612:702 */       this.V37 &= this.V36;
/* 613:703 */       this.V36 ^= this.V35;
/* 614:704 */       this.V35 = i;
/* 615:705 */       this.V34 ^= this.V30;
/* 616:706 */       this.V30 = ((this.V30 << 2 | this.V30 >>> 30) ^ this.V34);
/* 617:707 */       this.V34 = ((this.V34 << 14 | this.V34 >>> 18) ^ this.V30);
/* 618:708 */       this.V30 = ((this.V30 << 10 | this.V30 >>> 22) ^ this.V34);
/* 619:709 */       this.V34 = (this.V34 << 1 | this.V34 >>> 31);
/* 620:710 */       this.V35 ^= this.V31;
/* 621:711 */       this.V31 = ((this.V31 << 2 | this.V31 >>> 30) ^ this.V35);
/* 622:712 */       this.V35 = ((this.V35 << 14 | this.V35 >>> 18) ^ this.V31);
/* 623:713 */       this.V31 = ((this.V31 << 10 | this.V31 >>> 22) ^ this.V35);
/* 624:714 */       this.V35 = (this.V35 << 1 | this.V35 >>> 31);
/* 625:715 */       this.V36 ^= this.V32;
/* 626:716 */       this.V32 = ((this.V32 << 2 | this.V32 >>> 30) ^ this.V36);
/* 627:717 */       this.V36 = ((this.V36 << 14 | this.V36 >>> 18) ^ this.V32);
/* 628:718 */       this.V32 = ((this.V32 << 10 | this.V32 >>> 22) ^ this.V36);
/* 629:719 */       this.V36 = (this.V36 << 1 | this.V36 >>> 31);
/* 630:720 */       this.V37 ^= this.V33;
/* 631:721 */       this.V33 = ((this.V33 << 2 | this.V33 >>> 30) ^ this.V37);
/* 632:722 */       this.V37 = ((this.V37 << 14 | this.V37 >>> 18) ^ this.V33);
/* 633:723 */       this.V33 = ((this.V33 << 10 | this.V33 >>> 22) ^ this.V37);
/* 634:724 */       this.V37 = (this.V37 << 1 | this.V37 >>> 31);
/* 635:725 */       this.V30 ^= RC30[i21];
/* 636:726 */       this.V34 ^= RC34[i21];
/* 637:    */     }
/* 638:    */   }
/* 639:    */   
/* 640:    */   public String toString()
/* 641:    */   {
/* 642:733 */     return "Luffa-384";
/* 643:    */   }
/* 644:    */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     fr.cryptohash.Luffa384
 * JD-Core Version:    0.7.1
 */