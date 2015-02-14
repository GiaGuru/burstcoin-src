/*   1:    */ package fr.cryptohash;
/*   2:    */ 
/*   3:    */ public class RadioGatun32
/*   4:    */   extends DigestEngine
/*   5:    */ {
/*   6:    */   private int[] a;
/*   7:    */   private int[] b;
/*   8:    */   
/*   9:    */   public Digest copy()
/*  10:    */   {
/*  11: 55 */     RadioGatun32 localRadioGatun32 = new RadioGatun32();
/*  12: 56 */     System.arraycopy(this.a, 0, localRadioGatun32.a, 0, this.a.length);
/*  13: 57 */     System.arraycopy(this.b, 0, localRadioGatun32.b, 0, this.b.length);
/*  14: 58 */     return copyState(localRadioGatun32);
/*  15:    */   }
/*  16:    */   
/*  17:    */   public int getDigestLength()
/*  18:    */   {
/*  19: 64 */     return 32;
/*  20:    */   }
/*  21:    */   
/*  22:    */   protected int getInternalBlockLength()
/*  23:    */   {
/*  24: 70 */     return 156;
/*  25:    */   }
/*  26:    */   
/*  27:    */   public int getBlockLength()
/*  28:    */   {
/*  29: 76 */     return -12;
/*  30:    */   }
/*  31:    */   
/*  32:    */   protected void engineReset()
/*  33:    */   {
/*  34: 82 */     for (int i = 0; i < this.a.length; i++) {
/*  35: 83 */       this.a[i] = 0;
/*  36:    */     }
/*  37: 84 */     for (i = 0; i < this.b.length; i++) {
/*  38: 85 */       this.b[i] = 0;
/*  39:    */     }
/*  40:    */   }
/*  41:    */   
/*  42:    */   protected void doPadding(byte[] paramArrayOfByte, int paramInt)
/*  43:    */   {
/*  44: 91 */     int i = flush();
/*  45: 92 */     byte[] arrayOfByte = getBlockBuffer();
/*  46: 93 */     arrayOfByte[(i++)] = 1;
/*  47: 94 */     for (int j = i; j < 156; j++) {
/*  48: 95 */       arrayOfByte[j] = 0;
/*  49:    */     }
/*  50: 96 */     processBlock(arrayOfByte);
/*  51: 97 */     j = 20;
/*  52:    */     for (;;)
/*  53:    */     {
/*  54: 99 */       i += 12;
/*  55:100 */       if (i > 156) {
/*  56:    */         break;
/*  57:    */       }
/*  58:102 */       j--;
/*  59:    */     }
/*  60:104 */     blank(j, paramArrayOfByte, paramInt);
/*  61:    */   }
/*  62:    */   
/*  63:    */   protected void doInit()
/*  64:    */   {
/*  65:110 */     this.a = new int[19];
/*  66:111 */     this.b = new int[39];
/*  67:112 */     engineReset();
/*  68:    */   }
/*  69:    */   
/*  70:    */   private static final void encodeLEInt(int paramInt1, byte[] paramArrayOfByte, int paramInt2)
/*  71:    */   {
/*  72:126 */     paramArrayOfByte[(paramInt2 + 0)] = ((byte)paramInt1);
/*  73:127 */     paramArrayOfByte[(paramInt2 + 1)] = ((byte)(paramInt1 >>> 8));
/*  74:128 */     paramArrayOfByte[(paramInt2 + 2)] = ((byte)(paramInt1 >>> 16));
/*  75:129 */     paramArrayOfByte[(paramInt2 + 3)] = ((byte)(paramInt1 >>> 24));
/*  76:    */   }
/*  77:    */   
/*  78:    */   private static final int decodeLEInt(byte[] paramArrayOfByte, int paramInt)
/*  79:    */   {
/*  80:142 */     return (paramArrayOfByte[(paramInt + 3)] & 0xFF) << 24 | (paramArrayOfByte[(paramInt + 2)] & 0xFF) << 16 | (paramArrayOfByte[(paramInt + 1)] & 0xFF) << 8 | paramArrayOfByte[paramInt] & 0xFF;
/*  81:    */   }
/*  82:    */   
/*  83:    */   protected void processBlock(byte[] paramArrayOfByte)
/*  84:    */   {
/*  85:151 */     int i = this.a[0];
/*  86:152 */     int j = this.a[1];
/*  87:153 */     int k = this.a[2];
/*  88:154 */     int m = this.a[3];
/*  89:155 */     int n = this.a[4];
/*  90:156 */     int i1 = this.a[5];
/*  91:157 */     int i2 = this.a[6];
/*  92:158 */     int i3 = this.a[7];
/*  93:159 */     int i4 = this.a[8];
/*  94:160 */     int i5 = this.a[9];
/*  95:161 */     int i6 = this.a[10];
/*  96:162 */     int i7 = this.a[11];
/*  97:163 */     int i8 = this.a[12];
/*  98:164 */     int i9 = this.a[13];
/*  99:165 */     int i10 = this.a[14];
/* 100:166 */     int i11 = this.a[15];
/* 101:167 */     int i12 = this.a[16];
/* 102:168 */     int i13 = this.a[17];
/* 103:169 */     int i14 = this.a[18];
/* 104:    */     
/* 105:171 */     int i15 = 0;
/* 106:172 */     for (int i16 = 12; i16 >= 0; i16--)
/* 107:    */     {
/* 108:173 */       int i17 = decodeLEInt(paramArrayOfByte, i15 + 0);
/* 109:174 */       int i18 = decodeLEInt(paramArrayOfByte, i15 + 4);
/* 110:175 */       int i19 = decodeLEInt(paramArrayOfByte, i15 + 8);
/* 111:176 */       i15 += 12;
/* 112:177 */       int i20 = i16 == 12 ? 0 : 3 * (i16 + 1);
/* 113:178 */       this.b[(i20 + 0)] ^= i17;
/* 114:179 */       this.b[(i20 + 1)] ^= i18;
/* 115:180 */       this.b[(i20 + 2)] ^= i19;
/* 116:181 */       i12 ^= i17;
/* 117:182 */       i13 ^= i18;
/* 118:183 */       i14 ^= i19;
/* 119:    */       
/* 120:185 */       i20 = i16 * 3;
/* 121:186 */       i20 += 3;
/* 122:186 */       if (i20 == 39) {
/* 123:187 */         i20 = 0;
/* 124:    */       }
/* 125:188 */       this.b[(i20 + 0)] ^= j;
/* 126:189 */       i20 += 3;
/* 127:189 */       if (i20 == 39) {
/* 128:190 */         i20 = 0;
/* 129:    */       }
/* 130:191 */       this.b[(i20 + 1)] ^= k;
/* 131:192 */       i20 += 3;
/* 132:192 */       if (i20 == 39) {
/* 133:193 */         i20 = 0;
/* 134:    */       }
/* 135:194 */       this.b[(i20 + 2)] ^= m;
/* 136:195 */       i20 += 3;
/* 137:195 */       if (i20 == 39) {
/* 138:196 */         i20 = 0;
/* 139:    */       }
/* 140:197 */       this.b[(i20 + 0)] ^= n;
/* 141:198 */       i20 += 3;
/* 142:198 */       if (i20 == 39) {
/* 143:199 */         i20 = 0;
/* 144:    */       }
/* 145:200 */       this.b[(i20 + 1)] ^= i1;
/* 146:201 */       i20 += 3;
/* 147:201 */       if (i20 == 39) {
/* 148:202 */         i20 = 0;
/* 149:    */       }
/* 150:203 */       this.b[(i20 + 2)] ^= i2;
/* 151:204 */       i20 += 3;
/* 152:204 */       if (i20 == 39) {
/* 153:205 */         i20 = 0;
/* 154:    */       }
/* 155:206 */       this.b[(i20 + 0)] ^= i3;
/* 156:207 */       i20 += 3;
/* 157:207 */       if (i20 == 39) {
/* 158:208 */         i20 = 0;
/* 159:    */       }
/* 160:209 */       this.b[(i20 + 1)] ^= i4;
/* 161:210 */       i20 += 3;
/* 162:210 */       if (i20 == 39) {
/* 163:211 */         i20 = 0;
/* 164:    */       }
/* 165:212 */       this.b[(i20 + 2)] ^= i5;
/* 166:213 */       i20 += 3;
/* 167:213 */       if (i20 == 39) {
/* 168:214 */         i20 = 0;
/* 169:    */       }
/* 170:215 */       this.b[(i20 + 0)] ^= i6;
/* 171:216 */       i20 += 3;
/* 172:216 */       if (i20 == 39) {
/* 173:217 */         i20 = 0;
/* 174:    */       }
/* 175:218 */       this.b[(i20 + 1)] ^= i7;
/* 176:219 */       i20 += 3;
/* 177:219 */       if (i20 == 39) {
/* 178:220 */         i20 = 0;
/* 179:    */       }
/* 180:221 */       this.b[(i20 + 2)] ^= i8;
/* 181:    */       
/* 182:223 */       int i21 = i ^ (j | k ^ 0xFFFFFFFF);
/* 183:224 */       int i22 = j ^ (k | m ^ 0xFFFFFFFF);
/* 184:225 */       int i23 = k ^ (m | n ^ 0xFFFFFFFF);
/* 185:226 */       int i24 = m ^ (n | i1 ^ 0xFFFFFFFF);
/* 186:227 */       int i25 = n ^ (i1 | i2 ^ 0xFFFFFFFF);
/* 187:228 */       int i26 = i1 ^ (i2 | i3 ^ 0xFFFFFFFF);
/* 188:229 */       int i27 = i2 ^ (i3 | i4 ^ 0xFFFFFFFF);
/* 189:230 */       int i28 = i3 ^ (i4 | i5 ^ 0xFFFFFFFF);
/* 190:231 */       int i29 = i4 ^ (i5 | i6 ^ 0xFFFFFFFF);
/* 191:232 */       int i30 = i5 ^ (i6 | i7 ^ 0xFFFFFFFF);
/* 192:233 */       int i31 = i6 ^ (i7 | i8 ^ 0xFFFFFFFF);
/* 193:234 */       int i32 = i7 ^ (i8 | i9 ^ 0xFFFFFFFF);
/* 194:235 */       int i33 = i8 ^ (i9 | i10 ^ 0xFFFFFFFF);
/* 195:236 */       int i34 = i9 ^ (i10 | i11 ^ 0xFFFFFFFF);
/* 196:237 */       int i35 = i10 ^ (i11 | i12 ^ 0xFFFFFFFF);
/* 197:238 */       int i36 = i11 ^ (i12 | i13 ^ 0xFFFFFFFF);
/* 198:239 */       int i37 = i12 ^ (i13 | i14 ^ 0xFFFFFFFF);
/* 199:240 */       int i38 = i13 ^ (i14 | i ^ 0xFFFFFFFF);
/* 200:241 */       int i39 = i14 ^ (i | j ^ 0xFFFFFFFF);
/* 201:    */       
/* 202:243 */       i = i21;
/* 203:244 */       j = i28 << 31 | i28 >>> 1;
/* 204:245 */       k = i35 << 29 | i35 >>> 3;
/* 205:246 */       m = i23 << 26 | i23 >>> 6;
/* 206:247 */       n = i30 << 22 | i30 >>> 10;
/* 207:248 */       i1 = i37 << 17 | i37 >>> 15;
/* 208:249 */       i2 = i25 << 11 | i25 >>> 21;
/* 209:250 */       i3 = i32 << 4 | i32 >>> 28;
/* 210:251 */       i4 = i39 << 28 | i39 >>> 4;
/* 211:252 */       i5 = i27 << 19 | i27 >>> 13;
/* 212:253 */       i6 = i34 << 9 | i34 >>> 23;
/* 213:254 */       i7 = i22 << 30 | i22 >>> 2;
/* 214:255 */       i8 = i29 << 18 | i29 >>> 14;
/* 215:256 */       i9 = i36 << 5 | i36 >>> 27;
/* 216:257 */       i10 = i24 << 23 | i24 >>> 9;
/* 217:258 */       i11 = i31 << 8 | i31 >>> 24;
/* 218:259 */       i12 = i38 << 24 | i38 >>> 8;
/* 219:260 */       i13 = i26 << 7 | i26 >>> 25;
/* 220:261 */       i14 = i33 << 21 | i33 >>> 11;
/* 221:    */       
/* 222:263 */       i21 = i ^ j ^ n;
/* 223:264 */       i22 = j ^ k ^ i1;
/* 224:265 */       i23 = k ^ m ^ i2;
/* 225:266 */       i24 = m ^ n ^ i3;
/* 226:267 */       i25 = n ^ i1 ^ i4;
/* 227:268 */       i26 = i1 ^ i2 ^ i5;
/* 228:269 */       i27 = i2 ^ i3 ^ i6;
/* 229:270 */       i28 = i3 ^ i4 ^ i7;
/* 230:271 */       i29 = i4 ^ i5 ^ i8;
/* 231:272 */       i30 = i5 ^ i6 ^ i9;
/* 232:273 */       i31 = i6 ^ i7 ^ i10;
/* 233:274 */       i32 = i7 ^ i8 ^ i11;
/* 234:275 */       i33 = i8 ^ i9 ^ i12;
/* 235:276 */       i34 = i9 ^ i10 ^ i13;
/* 236:277 */       i35 = i10 ^ i11 ^ i14;
/* 237:278 */       i36 = i11 ^ i12 ^ i;
/* 238:279 */       i37 = i12 ^ i13 ^ j;
/* 239:280 */       i38 = i13 ^ i14 ^ k;
/* 240:281 */       i39 = i14 ^ i ^ m;
/* 241:    */       
/* 242:283 */       i = i21 ^ 0x1;
/* 243:284 */       j = i22;
/* 244:285 */       k = i23;
/* 245:286 */       m = i24;
/* 246:287 */       n = i25;
/* 247:288 */       i1 = i26;
/* 248:289 */       i2 = i27;
/* 249:290 */       i3 = i28;
/* 250:291 */       i4 = i29;
/* 251:292 */       i5 = i30;
/* 252:293 */       i6 = i31;
/* 253:294 */       i7 = i32;
/* 254:295 */       i8 = i33;
/* 255:296 */       i9 = i34;
/* 256:297 */       i10 = i35;
/* 257:298 */       i11 = i36;
/* 258:299 */       i12 = i37;
/* 259:300 */       i13 = i38;
/* 260:301 */       i14 = i39;
/* 261:    */       
/* 262:303 */       i20 = i16 * 3;
/* 263:304 */       i9 ^= this.b[(i20 + 0)];
/* 264:305 */       i10 ^= this.b[(i20 + 1)];
/* 265:306 */       i11 ^= this.b[(i20 + 2)];
/* 266:    */     }
/* 267:309 */     this.a[0] = i;
/* 268:310 */     this.a[1] = j;
/* 269:311 */     this.a[2] = k;
/* 270:312 */     this.a[3] = m;
/* 271:313 */     this.a[4] = n;
/* 272:314 */     this.a[5] = i1;
/* 273:315 */     this.a[6] = i2;
/* 274:316 */     this.a[7] = i3;
/* 275:317 */     this.a[8] = i4;
/* 276:318 */     this.a[9] = i5;
/* 277:319 */     this.a[10] = i6;
/* 278:320 */     this.a[11] = i7;
/* 279:321 */     this.a[12] = i8;
/* 280:322 */     this.a[13] = i9;
/* 281:323 */     this.a[14] = i10;
/* 282:324 */     this.a[15] = i11;
/* 283:325 */     this.a[16] = i12;
/* 284:326 */     this.a[17] = i13;
/* 285:327 */     this.a[18] = i14;
/* 286:    */   }
/* 287:    */   
/* 288:    */   private void blank(int paramInt1, byte[] paramArrayOfByte, int paramInt2)
/* 289:    */   {
/* 290:343 */     int i = this.a[0];
/* 291:344 */     int j = this.a[1];
/* 292:345 */     int k = this.a[2];
/* 293:346 */     int m = this.a[3];
/* 294:347 */     int n = this.a[4];
/* 295:348 */     int i1 = this.a[5];
/* 296:349 */     int i2 = this.a[6];
/* 297:350 */     int i3 = this.a[7];
/* 298:351 */     int i4 = this.a[8];
/* 299:352 */     int i5 = this.a[9];
/* 300:353 */     int i6 = this.a[10];
/* 301:354 */     int i7 = this.a[11];
/* 302:355 */     int i8 = this.a[12];
/* 303:356 */     int i9 = this.a[13];
/* 304:357 */     int i10 = this.a[14];
/* 305:358 */     int i11 = this.a[15];
/* 306:359 */     int i12 = this.a[16];
/* 307:360 */     int i13 = this.a[17];
/* 308:361 */     int i14 = this.a[18];
/* 309:363 */     while (paramInt1-- > 0)
/* 310:    */     {
/* 311:364 */       this.b[0] ^= j;
/* 312:365 */       this.b[4] ^= k;
/* 313:366 */       this.b[8] ^= m;
/* 314:367 */       this.b[9] ^= n;
/* 315:368 */       this.b[13] ^= i1;
/* 316:369 */       this.b[17] ^= i2;
/* 317:370 */       this.b[18] ^= i3;
/* 318:371 */       this.b[22] ^= i4;
/* 319:372 */       this.b[26] ^= i5;
/* 320:373 */       this.b[27] ^= i6;
/* 321:374 */       this.b[31] ^= i7;
/* 322:375 */       this.b[35] ^= i8;
/* 323:    */       
/* 324:377 */       int i15 = i ^ (j | k ^ 0xFFFFFFFF);
/* 325:378 */       int i16 = j ^ (k | m ^ 0xFFFFFFFF);
/* 326:379 */       int i17 = k ^ (m | n ^ 0xFFFFFFFF);
/* 327:380 */       int i18 = m ^ (n | i1 ^ 0xFFFFFFFF);
/* 328:381 */       int i19 = n ^ (i1 | i2 ^ 0xFFFFFFFF);
/* 329:382 */       int i20 = i1 ^ (i2 | i3 ^ 0xFFFFFFFF);
/* 330:383 */       int i21 = i2 ^ (i3 | i4 ^ 0xFFFFFFFF);
/* 331:384 */       int i22 = i3 ^ (i4 | i5 ^ 0xFFFFFFFF);
/* 332:385 */       int i23 = i4 ^ (i5 | i6 ^ 0xFFFFFFFF);
/* 333:386 */       int i24 = i5 ^ (i6 | i7 ^ 0xFFFFFFFF);
/* 334:387 */       int i25 = i6 ^ (i7 | i8 ^ 0xFFFFFFFF);
/* 335:388 */       int i26 = i7 ^ (i8 | i9 ^ 0xFFFFFFFF);
/* 336:389 */       int i27 = i8 ^ (i9 | i10 ^ 0xFFFFFFFF);
/* 337:390 */       int i28 = i9 ^ (i10 | i11 ^ 0xFFFFFFFF);
/* 338:391 */       int i29 = i10 ^ (i11 | i12 ^ 0xFFFFFFFF);
/* 339:392 */       int i30 = i11 ^ (i12 | i13 ^ 0xFFFFFFFF);
/* 340:393 */       int i31 = i12 ^ (i13 | i14 ^ 0xFFFFFFFF);
/* 341:394 */       int i32 = i13 ^ (i14 | i ^ 0xFFFFFFFF);
/* 342:395 */       int i33 = i14 ^ (i | j ^ 0xFFFFFFFF);
/* 343:    */       
/* 344:397 */       i = i15;
/* 345:398 */       j = i22 << 31 | i22 >>> 1;
/* 346:399 */       k = i29 << 29 | i29 >>> 3;
/* 347:400 */       m = i17 << 26 | i17 >>> 6;
/* 348:401 */       n = i24 << 22 | i24 >>> 10;
/* 349:402 */       i1 = i31 << 17 | i31 >>> 15;
/* 350:403 */       i2 = i19 << 11 | i19 >>> 21;
/* 351:404 */       i3 = i26 << 4 | i26 >>> 28;
/* 352:405 */       i4 = i33 << 28 | i33 >>> 4;
/* 353:406 */       i5 = i21 << 19 | i21 >>> 13;
/* 354:407 */       i6 = i28 << 9 | i28 >>> 23;
/* 355:408 */       i7 = i16 << 30 | i16 >>> 2;
/* 356:409 */       i8 = i23 << 18 | i23 >>> 14;
/* 357:410 */       i9 = i30 << 5 | i30 >>> 27;
/* 358:411 */       i10 = i18 << 23 | i18 >>> 9;
/* 359:412 */       i11 = i25 << 8 | i25 >>> 24;
/* 360:413 */       i12 = i32 << 24 | i32 >>> 8;
/* 361:414 */       i13 = i20 << 7 | i20 >>> 25;
/* 362:415 */       i14 = i27 << 21 | i27 >>> 11;
/* 363:    */       
/* 364:417 */       i15 = i ^ j ^ n;
/* 365:418 */       i16 = j ^ k ^ i1;
/* 366:419 */       i17 = k ^ m ^ i2;
/* 367:420 */       i18 = m ^ n ^ i3;
/* 368:421 */       i19 = n ^ i1 ^ i4;
/* 369:422 */       i20 = i1 ^ i2 ^ i5;
/* 370:423 */       i21 = i2 ^ i3 ^ i6;
/* 371:424 */       i22 = i3 ^ i4 ^ i7;
/* 372:425 */       i23 = i4 ^ i5 ^ i8;
/* 373:426 */       i24 = i5 ^ i6 ^ i9;
/* 374:427 */       i25 = i6 ^ i7 ^ i10;
/* 375:428 */       i26 = i7 ^ i8 ^ i11;
/* 376:429 */       i27 = i8 ^ i9 ^ i12;
/* 377:430 */       i28 = i9 ^ i10 ^ i13;
/* 378:431 */       i29 = i10 ^ i11 ^ i14;
/* 379:432 */       i30 = i11 ^ i12 ^ i;
/* 380:433 */       i31 = i12 ^ i13 ^ j;
/* 381:434 */       i32 = i13 ^ i14 ^ k;
/* 382:435 */       i33 = i14 ^ i ^ m;
/* 383:    */       
/* 384:437 */       i = i15 ^ 0x1;
/* 385:438 */       j = i16;
/* 386:439 */       k = i17;
/* 387:440 */       m = i18;
/* 388:441 */       n = i19;
/* 389:442 */       i1 = i20;
/* 390:443 */       i2 = i21;
/* 391:444 */       i3 = i22;
/* 392:445 */       i4 = i23;
/* 393:446 */       i5 = i24;
/* 394:447 */       i6 = i25;
/* 395:448 */       i7 = i26;
/* 396:449 */       i8 = i27;
/* 397:450 */       i9 = i28;
/* 398:451 */       i10 = i29;
/* 399:452 */       i11 = i30;
/* 400:453 */       i12 = i31;
/* 401:454 */       i13 = i32;
/* 402:455 */       i14 = i33;
/* 403:    */       
/* 404:457 */       int i34 = this.b[36];
/* 405:458 */       int i35 = this.b[37];
/* 406:459 */       int i36 = this.b[38];
/* 407:460 */       i9 ^= i34;
/* 408:461 */       i10 ^= i35;
/* 409:462 */       i11 ^= i36;
/* 410:463 */       System.arraycopy(this.b, 0, this.b, 3, 36);
/* 411:464 */       this.b[0] = i34;
/* 412:465 */       this.b[1] = i35;
/* 413:466 */       this.b[2] = i36;
/* 414:467 */       if (paramInt1 < 4)
/* 415:    */       {
/* 416:468 */         encodeLEInt(j, paramArrayOfByte, paramInt2 + 0);
/* 417:469 */         encodeLEInt(k, paramArrayOfByte, paramInt2 + 4);
/* 418:470 */         paramInt2 += 8;
/* 419:    */       }
/* 420:    */     }
/* 421:    */   }
/* 422:    */   
/* 423:    */   public String toString()
/* 424:    */   {
/* 425:500 */     return "RadioGatun[32]";
/* 426:    */   }
/* 427:    */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     fr.cryptohash.RadioGatun32
 * JD-Core Version:    0.7.1
 */