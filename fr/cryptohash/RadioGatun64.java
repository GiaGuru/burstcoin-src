/*   1:    */ package fr.cryptohash;
/*   2:    */ 
/*   3:    */ public class RadioGatun64
/*   4:    */   extends DigestEngine
/*   5:    */ {
/*   6:    */   private long[] a;
/*   7:    */   private long[] b;
/*   8:    */   
/*   9:    */   public Digest copy()
/*  10:    */   {
/*  11: 55 */     RadioGatun64 localRadioGatun64 = new RadioGatun64();
/*  12: 56 */     System.arraycopy(this.a, 0, localRadioGatun64.a, 0, this.a.length);
/*  13: 57 */     System.arraycopy(this.b, 0, localRadioGatun64.b, 0, this.b.length);
/*  14: 58 */     return copyState(localRadioGatun64);
/*  15:    */   }
/*  16:    */   
/*  17:    */   public int getDigestLength()
/*  18:    */   {
/*  19: 64 */     return 32;
/*  20:    */   }
/*  21:    */   
/*  22:    */   protected int getInternalBlockLength()
/*  23:    */   {
/*  24: 70 */     return 312;
/*  25:    */   }
/*  26:    */   
/*  27:    */   public int getBlockLength()
/*  28:    */   {
/*  29: 76 */     return -24;
/*  30:    */   }
/*  31:    */   
/*  32:    */   protected void engineReset()
/*  33:    */   {
/*  34: 82 */     for (int i = 0; i < this.a.length; i++) {
/*  35: 83 */       this.a[i] = 0L;
/*  36:    */     }
/*  37: 84 */     for (i = 0; i < this.b.length; i++) {
/*  38: 85 */       this.b[i] = 0L;
/*  39:    */     }
/*  40:    */   }
/*  41:    */   
/*  42:    */   protected void doPadding(byte[] paramArrayOfByte, int paramInt)
/*  43:    */   {
/*  44: 91 */     int i = flush();
/*  45: 92 */     byte[] arrayOfByte = getBlockBuffer();
/*  46: 93 */     arrayOfByte[(i++)] = 1;
/*  47: 94 */     for (int j = i; j < 312; j++) {
/*  48: 95 */       arrayOfByte[j] = 0;
/*  49:    */     }
/*  50: 96 */     processBlock(arrayOfByte);
/*  51: 97 */     j = 18;
/*  52:    */     for (;;)
/*  53:    */     {
/*  54: 99 */       i += 24;
/*  55:100 */       if (i > 312) {
/*  56:    */         break;
/*  57:    */       }
/*  58:102 */       j--;
/*  59:    */     }
/*  60:104 */     blank(j, paramArrayOfByte, paramInt);
/*  61:    */   }
/*  62:    */   
/*  63:    */   protected void doInit()
/*  64:    */   {
/*  65:110 */     this.a = new long[19];
/*  66:111 */     this.b = new long[39];
/*  67:112 */     engineReset();
/*  68:    */   }
/*  69:    */   
/*  70:    */   private static final void encodeLELong(long paramLong, byte[] paramArrayOfByte, int paramInt)
/*  71:    */   {
/*  72:126 */     paramArrayOfByte[(paramInt + 0)] = ((byte)(int)paramLong);
/*  73:127 */     paramArrayOfByte[(paramInt + 1)] = ((byte)(int)(paramLong >>> 8));
/*  74:128 */     paramArrayOfByte[(paramInt + 2)] = ((byte)(int)(paramLong >>> 16));
/*  75:129 */     paramArrayOfByte[(paramInt + 3)] = ((byte)(int)(paramLong >>> 24));
/*  76:130 */     paramArrayOfByte[(paramInt + 4)] = ((byte)(int)(paramLong >>> 32));
/*  77:131 */     paramArrayOfByte[(paramInt + 5)] = ((byte)(int)(paramLong >>> 40));
/*  78:132 */     paramArrayOfByte[(paramInt + 6)] = ((byte)(int)(paramLong >>> 48));
/*  79:133 */     paramArrayOfByte[(paramInt + 7)] = ((byte)(int)(paramLong >>> 56));
/*  80:    */   }
/*  81:    */   
/*  82:    */   private static final long decodeLELong(byte[] paramArrayOfByte, int paramInt)
/*  83:    */   {
/*  84:146 */     return (paramArrayOfByte[(paramInt + 7)] & 0xFF) << 56 | (paramArrayOfByte[(paramInt + 6)] & 0xFF) << 48 | (paramArrayOfByte[(paramInt + 5)] & 0xFF) << 40 | (paramArrayOfByte[(paramInt + 4)] & 0xFF) << 32 | (paramArrayOfByte[(paramInt + 3)] & 0xFF) << 24 | (paramArrayOfByte[(paramInt + 2)] & 0xFF) << 16 | (paramArrayOfByte[(paramInt + 1)] & 0xFF) << 8 | paramArrayOfByte[paramInt] & 0xFF;
/*  85:    */   }
/*  86:    */   
/*  87:    */   protected void processBlock(byte[] paramArrayOfByte)
/*  88:    */   {
/*  89:159 */     long l1 = this.a[0];
/*  90:160 */     long l2 = this.a[1];
/*  91:161 */     long l3 = this.a[2];
/*  92:162 */     long l4 = this.a[3];
/*  93:163 */     long l5 = this.a[4];
/*  94:164 */     long l6 = this.a[5];
/*  95:165 */     long l7 = this.a[6];
/*  96:166 */     long l8 = this.a[7];
/*  97:167 */     long l9 = this.a[8];
/*  98:168 */     long l10 = this.a[9];
/*  99:169 */     long l11 = this.a[10];
/* 100:170 */     long l12 = this.a[11];
/* 101:171 */     long l13 = this.a[12];
/* 102:172 */     long l14 = this.a[13];
/* 103:173 */     long l15 = this.a[14];
/* 104:174 */     long l16 = this.a[15];
/* 105:175 */     long l17 = this.a[16];
/* 106:176 */     long l18 = this.a[17];
/* 107:177 */     long l19 = this.a[18];
/* 108:    */     
/* 109:179 */     int i = 0;
/* 110:180 */     for (int j = 12; j >= 0; j--)
/* 111:    */     {
/* 112:181 */       long l20 = decodeLELong(paramArrayOfByte, i + 0);
/* 113:182 */       long l21 = decodeLELong(paramArrayOfByte, i + 8);
/* 114:183 */       long l22 = decodeLELong(paramArrayOfByte, i + 16);
/* 115:184 */       i += 24;
/* 116:185 */       int k = j == 12 ? 0 : 3 * (j + 1);
/* 117:186 */       this.b[(k + 0)] ^= l20;
/* 118:187 */       this.b[(k + 1)] ^= l21;
/* 119:188 */       this.b[(k + 2)] ^= l22;
/* 120:189 */       l17 ^= l20;
/* 121:190 */       l18 ^= l21;
/* 122:191 */       l19 ^= l22;
/* 123:    */       
/* 124:193 */       k = j * 3;
/* 125:194 */       k += 3;
/* 126:194 */       if (k == 39) {
/* 127:195 */         k = 0;
/* 128:    */       }
/* 129:196 */       this.b[(k + 0)] ^= l2;
/* 130:197 */       k += 3;
/* 131:197 */       if (k == 39) {
/* 132:198 */         k = 0;
/* 133:    */       }
/* 134:199 */       this.b[(k + 1)] ^= l3;
/* 135:200 */       k += 3;
/* 136:200 */       if (k == 39) {
/* 137:201 */         k = 0;
/* 138:    */       }
/* 139:202 */       this.b[(k + 2)] ^= l4;
/* 140:203 */       k += 3;
/* 141:203 */       if (k == 39) {
/* 142:204 */         k = 0;
/* 143:    */       }
/* 144:205 */       this.b[(k + 0)] ^= l5;
/* 145:206 */       k += 3;
/* 146:206 */       if (k == 39) {
/* 147:207 */         k = 0;
/* 148:    */       }
/* 149:208 */       this.b[(k + 1)] ^= l6;
/* 150:209 */       k += 3;
/* 151:209 */       if (k == 39) {
/* 152:210 */         k = 0;
/* 153:    */       }
/* 154:211 */       this.b[(k + 2)] ^= l7;
/* 155:212 */       k += 3;
/* 156:212 */       if (k == 39) {
/* 157:213 */         k = 0;
/* 158:    */       }
/* 159:214 */       this.b[(k + 0)] ^= l8;
/* 160:215 */       k += 3;
/* 161:215 */       if (k == 39) {
/* 162:216 */         k = 0;
/* 163:    */       }
/* 164:217 */       this.b[(k + 1)] ^= l9;
/* 165:218 */       k += 3;
/* 166:218 */       if (k == 39) {
/* 167:219 */         k = 0;
/* 168:    */       }
/* 169:220 */       this.b[(k + 2)] ^= l10;
/* 170:221 */       k += 3;
/* 171:221 */       if (k == 39) {
/* 172:222 */         k = 0;
/* 173:    */       }
/* 174:223 */       this.b[(k + 0)] ^= l11;
/* 175:224 */       k += 3;
/* 176:224 */       if (k == 39) {
/* 177:225 */         k = 0;
/* 178:    */       }
/* 179:226 */       this.b[(k + 1)] ^= l12;
/* 180:227 */       k += 3;
/* 181:227 */       if (k == 39) {
/* 182:228 */         k = 0;
/* 183:    */       }
/* 184:229 */       this.b[(k + 2)] ^= l13;
/* 185:    */       
/* 186:231 */       long l23 = l1 ^ (l2 | l3 ^ 0xFFFFFFFF);
/* 187:232 */       long l24 = l2 ^ (l3 | l4 ^ 0xFFFFFFFF);
/* 188:233 */       long l25 = l3 ^ (l4 | l5 ^ 0xFFFFFFFF);
/* 189:234 */       long l26 = l4 ^ (l5 | l6 ^ 0xFFFFFFFF);
/* 190:235 */       long l27 = l5 ^ (l6 | l7 ^ 0xFFFFFFFF);
/* 191:236 */       long l28 = l6 ^ (l7 | l8 ^ 0xFFFFFFFF);
/* 192:237 */       long l29 = l7 ^ (l8 | l9 ^ 0xFFFFFFFF);
/* 193:238 */       long l30 = l8 ^ (l9 | l10 ^ 0xFFFFFFFF);
/* 194:239 */       long l31 = l9 ^ (l10 | l11 ^ 0xFFFFFFFF);
/* 195:240 */       long l32 = l10 ^ (l11 | l12 ^ 0xFFFFFFFF);
/* 196:241 */       long l33 = l11 ^ (l12 | l13 ^ 0xFFFFFFFF);
/* 197:242 */       long l34 = l12 ^ (l13 | l14 ^ 0xFFFFFFFF);
/* 198:243 */       long l35 = l13 ^ (l14 | l15 ^ 0xFFFFFFFF);
/* 199:244 */       long l36 = l14 ^ (l15 | l16 ^ 0xFFFFFFFF);
/* 200:245 */       long l37 = l15 ^ (l16 | l17 ^ 0xFFFFFFFF);
/* 201:246 */       long l38 = l16 ^ (l17 | l18 ^ 0xFFFFFFFF);
/* 202:247 */       long l39 = l17 ^ (l18 | l19 ^ 0xFFFFFFFF);
/* 203:248 */       long l40 = l18 ^ (l19 | l1 ^ 0xFFFFFFFF);
/* 204:249 */       long l41 = l19 ^ (l1 | l2 ^ 0xFFFFFFFF);
/* 205:    */       
/* 206:251 */       l1 = l23;
/* 207:252 */       l2 = l30 << 63 | l30 >>> 1;
/* 208:253 */       l3 = l37 << 61 | l37 >>> 3;
/* 209:254 */       l4 = l25 << 58 | l25 >>> 6;
/* 210:255 */       l5 = l32 << 54 | l32 >>> 10;
/* 211:256 */       l6 = l39 << 49 | l39 >>> 15;
/* 212:257 */       l7 = l27 << 43 | l27 >>> 21;
/* 213:258 */       l8 = l34 << 36 | l34 >>> 28;
/* 214:259 */       l9 = l41 << 28 | l41 >>> 36;
/* 215:260 */       l10 = l29 << 19 | l29 >>> 45;
/* 216:261 */       l11 = l36 << 9 | l36 >>> 55;
/* 217:262 */       l12 = l24 << 62 | l24 >>> 2;
/* 218:263 */       l13 = l31 << 50 | l31 >>> 14;
/* 219:264 */       l14 = l38 << 37 | l38 >>> 27;
/* 220:265 */       l15 = l26 << 23 | l26 >>> 41;
/* 221:266 */       l16 = l33 << 8 | l33 >>> 56;
/* 222:267 */       l17 = l40 << 56 | l40 >>> 8;
/* 223:268 */       l18 = l28 << 39 | l28 >>> 25;
/* 224:269 */       l19 = l35 << 21 | l35 >>> 43;
/* 225:    */       
/* 226:271 */       l23 = l1 ^ l2 ^ l5;
/* 227:272 */       l24 = l2 ^ l3 ^ l6;
/* 228:273 */       l25 = l3 ^ l4 ^ l7;
/* 229:274 */       l26 = l4 ^ l5 ^ l8;
/* 230:275 */       l27 = l5 ^ l6 ^ l9;
/* 231:276 */       l28 = l6 ^ l7 ^ l10;
/* 232:277 */       l29 = l7 ^ l8 ^ l11;
/* 233:278 */       l30 = l8 ^ l9 ^ l12;
/* 234:279 */       l31 = l9 ^ l10 ^ l13;
/* 235:280 */       l32 = l10 ^ l11 ^ l14;
/* 236:281 */       l33 = l11 ^ l12 ^ l15;
/* 237:282 */       l34 = l12 ^ l13 ^ l16;
/* 238:283 */       l35 = l13 ^ l14 ^ l17;
/* 239:284 */       l36 = l14 ^ l15 ^ l18;
/* 240:285 */       l37 = l15 ^ l16 ^ l19;
/* 241:286 */       l38 = l16 ^ l17 ^ l1;
/* 242:287 */       l39 = l17 ^ l18 ^ l2;
/* 243:288 */       l40 = l18 ^ l19 ^ l3;
/* 244:289 */       l41 = l19 ^ l1 ^ l4;
/* 245:    */       
/* 246:291 */       l1 = l23 ^ 1L;
/* 247:292 */       l2 = l24;
/* 248:293 */       l3 = l25;
/* 249:294 */       l4 = l26;
/* 250:295 */       l5 = l27;
/* 251:296 */       l6 = l28;
/* 252:297 */       l7 = l29;
/* 253:298 */       l8 = l30;
/* 254:299 */       l9 = l31;
/* 255:300 */       l10 = l32;
/* 256:301 */       l11 = l33;
/* 257:302 */       l12 = l34;
/* 258:303 */       l13 = l35;
/* 259:304 */       l14 = l36;
/* 260:305 */       l15 = l37;
/* 261:306 */       l16 = l38;
/* 262:307 */       l17 = l39;
/* 263:308 */       l18 = l40;
/* 264:309 */       l19 = l41;
/* 265:    */       
/* 266:311 */       k = j * 3;
/* 267:312 */       l14 ^= this.b[(k + 0)];
/* 268:313 */       l15 ^= this.b[(k + 1)];
/* 269:314 */       l16 ^= this.b[(k + 2)];
/* 270:    */     }
/* 271:317 */     this.a[0] = l1;
/* 272:318 */     this.a[1] = l2;
/* 273:319 */     this.a[2] = l3;
/* 274:320 */     this.a[3] = l4;
/* 275:321 */     this.a[4] = l5;
/* 276:322 */     this.a[5] = l6;
/* 277:323 */     this.a[6] = l7;
/* 278:324 */     this.a[7] = l8;
/* 279:325 */     this.a[8] = l9;
/* 280:326 */     this.a[9] = l10;
/* 281:327 */     this.a[10] = l11;
/* 282:328 */     this.a[11] = l12;
/* 283:329 */     this.a[12] = l13;
/* 284:330 */     this.a[13] = l14;
/* 285:331 */     this.a[14] = l15;
/* 286:332 */     this.a[15] = l16;
/* 287:333 */     this.a[16] = l17;
/* 288:334 */     this.a[17] = l18;
/* 289:335 */     this.a[18] = l19;
/* 290:    */   }
/* 291:    */   
/* 292:    */   private void blank(int paramInt1, byte[] paramArrayOfByte, int paramInt2)
/* 293:    */   {
/* 294:351 */     long l1 = this.a[0];
/* 295:352 */     long l2 = this.a[1];
/* 296:353 */     long l3 = this.a[2];
/* 297:354 */     long l4 = this.a[3];
/* 298:355 */     long l5 = this.a[4];
/* 299:356 */     long l6 = this.a[5];
/* 300:357 */     long l7 = this.a[6];
/* 301:358 */     long l8 = this.a[7];
/* 302:359 */     long l9 = this.a[8];
/* 303:360 */     long l10 = this.a[9];
/* 304:361 */     long l11 = this.a[10];
/* 305:362 */     long l12 = this.a[11];
/* 306:363 */     long l13 = this.a[12];
/* 307:364 */     long l14 = this.a[13];
/* 308:365 */     long l15 = this.a[14];
/* 309:366 */     long l16 = this.a[15];
/* 310:367 */     long l17 = this.a[16];
/* 311:368 */     long l18 = this.a[17];
/* 312:369 */     long l19 = this.a[18];
/* 313:371 */     while (paramInt1-- > 0)
/* 314:    */     {
/* 315:372 */       this.b[0] ^= l2;
/* 316:373 */       this.b[4] ^= l3;
/* 317:374 */       this.b[8] ^= l4;
/* 318:375 */       this.b[9] ^= l5;
/* 319:376 */       this.b[13] ^= l6;
/* 320:377 */       this.b[17] ^= l7;
/* 321:378 */       this.b[18] ^= l8;
/* 322:379 */       this.b[22] ^= l9;
/* 323:380 */       this.b[26] ^= l10;
/* 324:381 */       this.b[27] ^= l11;
/* 325:382 */       this.b[31] ^= l12;
/* 326:383 */       this.b[35] ^= l13;
/* 327:    */       
/* 328:385 */       long l20 = l1 ^ (l2 | l3 ^ 0xFFFFFFFF);
/* 329:386 */       long l21 = l2 ^ (l3 | l4 ^ 0xFFFFFFFF);
/* 330:387 */       long l22 = l3 ^ (l4 | l5 ^ 0xFFFFFFFF);
/* 331:388 */       long l23 = l4 ^ (l5 | l6 ^ 0xFFFFFFFF);
/* 332:389 */       long l24 = l5 ^ (l6 | l7 ^ 0xFFFFFFFF);
/* 333:390 */       long l25 = l6 ^ (l7 | l8 ^ 0xFFFFFFFF);
/* 334:391 */       long l26 = l7 ^ (l8 | l9 ^ 0xFFFFFFFF);
/* 335:392 */       long l27 = l8 ^ (l9 | l10 ^ 0xFFFFFFFF);
/* 336:393 */       long l28 = l9 ^ (l10 | l11 ^ 0xFFFFFFFF);
/* 337:394 */       long l29 = l10 ^ (l11 | l12 ^ 0xFFFFFFFF);
/* 338:395 */       long l30 = l11 ^ (l12 | l13 ^ 0xFFFFFFFF);
/* 339:396 */       long l31 = l12 ^ (l13 | l14 ^ 0xFFFFFFFF);
/* 340:397 */       long l32 = l13 ^ (l14 | l15 ^ 0xFFFFFFFF);
/* 341:398 */       long l33 = l14 ^ (l15 | l16 ^ 0xFFFFFFFF);
/* 342:399 */       long l34 = l15 ^ (l16 | l17 ^ 0xFFFFFFFF);
/* 343:400 */       long l35 = l16 ^ (l17 | l18 ^ 0xFFFFFFFF);
/* 344:401 */       long l36 = l17 ^ (l18 | l19 ^ 0xFFFFFFFF);
/* 345:402 */       long l37 = l18 ^ (l19 | l1 ^ 0xFFFFFFFF);
/* 346:403 */       long l38 = l19 ^ (l1 | l2 ^ 0xFFFFFFFF);
/* 347:    */       
/* 348:405 */       l1 = l20;
/* 349:406 */       l2 = l27 << 63 | l27 >>> 1;
/* 350:407 */       l3 = l34 << 61 | l34 >>> 3;
/* 351:408 */       l4 = l22 << 58 | l22 >>> 6;
/* 352:409 */       l5 = l29 << 54 | l29 >>> 10;
/* 353:410 */       l6 = l36 << 49 | l36 >>> 15;
/* 354:411 */       l7 = l24 << 43 | l24 >>> 21;
/* 355:412 */       l8 = l31 << 36 | l31 >>> 28;
/* 356:413 */       l9 = l38 << 28 | l38 >>> 36;
/* 357:414 */       l10 = l26 << 19 | l26 >>> 45;
/* 358:415 */       l11 = l33 << 9 | l33 >>> 55;
/* 359:416 */       l12 = l21 << 62 | l21 >>> 2;
/* 360:417 */       l13 = l28 << 50 | l28 >>> 14;
/* 361:418 */       l14 = l35 << 37 | l35 >>> 27;
/* 362:419 */       l15 = l23 << 23 | l23 >>> 41;
/* 363:420 */       l16 = l30 << 8 | l30 >>> 56;
/* 364:421 */       l17 = l37 << 56 | l37 >>> 8;
/* 365:422 */       l18 = l25 << 39 | l25 >>> 25;
/* 366:423 */       l19 = l32 << 21 | l32 >>> 43;
/* 367:    */       
/* 368:425 */       l20 = l1 ^ l2 ^ l5;
/* 369:426 */       l21 = l2 ^ l3 ^ l6;
/* 370:427 */       l22 = l3 ^ l4 ^ l7;
/* 371:428 */       l23 = l4 ^ l5 ^ l8;
/* 372:429 */       l24 = l5 ^ l6 ^ l9;
/* 373:430 */       l25 = l6 ^ l7 ^ l10;
/* 374:431 */       l26 = l7 ^ l8 ^ l11;
/* 375:432 */       l27 = l8 ^ l9 ^ l12;
/* 376:433 */       l28 = l9 ^ l10 ^ l13;
/* 377:434 */       l29 = l10 ^ l11 ^ l14;
/* 378:435 */       l30 = l11 ^ l12 ^ l15;
/* 379:436 */       l31 = l12 ^ l13 ^ l16;
/* 380:437 */       l32 = l13 ^ l14 ^ l17;
/* 381:438 */       l33 = l14 ^ l15 ^ l18;
/* 382:439 */       l34 = l15 ^ l16 ^ l19;
/* 383:440 */       l35 = l16 ^ l17 ^ l1;
/* 384:441 */       l36 = l17 ^ l18 ^ l2;
/* 385:442 */       l37 = l18 ^ l19 ^ l3;
/* 386:443 */       l38 = l19 ^ l1 ^ l4;
/* 387:    */       
/* 388:445 */       l1 = l20 ^ 1L;
/* 389:446 */       l2 = l21;
/* 390:447 */       l3 = l22;
/* 391:448 */       l4 = l23;
/* 392:449 */       l5 = l24;
/* 393:450 */       l6 = l25;
/* 394:451 */       l7 = l26;
/* 395:452 */       l8 = l27;
/* 396:453 */       l9 = l28;
/* 397:454 */       l10 = l29;
/* 398:455 */       l11 = l30;
/* 399:456 */       l12 = l31;
/* 400:457 */       l13 = l32;
/* 401:458 */       l14 = l33;
/* 402:459 */       l15 = l34;
/* 403:460 */       l16 = l35;
/* 404:461 */       l17 = l36;
/* 405:462 */       l18 = l37;
/* 406:463 */       l19 = l38;
/* 407:    */       
/* 408:465 */       long l39 = this.b[36];
/* 409:466 */       long l40 = this.b[37];
/* 410:467 */       long l41 = this.b[38];
/* 411:468 */       l14 ^= l39;
/* 412:469 */       l15 ^= l40;
/* 413:470 */       l16 ^= l41;
/* 414:471 */       System.arraycopy(this.b, 0, this.b, 3, 36);
/* 415:472 */       this.b[0] = l39;
/* 416:473 */       this.b[1] = l40;
/* 417:474 */       this.b[2] = l41;
/* 418:475 */       if (paramInt1 < 2)
/* 419:    */       {
/* 420:476 */         encodeLELong(l2, paramArrayOfByte, paramInt2 + 0);
/* 421:477 */         encodeLELong(l3, paramArrayOfByte, paramInt2 + 8);
/* 422:478 */         paramInt2 += 16;
/* 423:    */       }
/* 424:    */     }
/* 425:    */   }
/* 426:    */   
/* 427:    */   public String toString()
/* 428:    */   {
/* 429:508 */     return "RadioGatun[64]";
/* 430:    */   }
/* 431:    */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     fr.cryptohash.RadioGatun64
 * JD-Core Version:    0.7.1
 */