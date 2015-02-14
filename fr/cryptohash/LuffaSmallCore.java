/*   1:    */ package fr.cryptohash;
/*   2:    */ 
/*   3:    */ abstract class LuffaSmallCore
/*   4:    */   extends DigestEngine
/*   5:    */ {
/*   6: 41 */   private static final int[] IV = { 1831149161, 1152405984, 1319792564, -604535707, 1848188945, -1877660172, -301629127, -554299205, -1011594347, -640486826, 1894705568, -569794653, 1570440535, -1882635085, -820195570, 1953289601, -135280483, 1572493185, 67202277, -1385849851, 50731343, 1718425654, 615129866, -1960424729 };
/*   7: 50 */   private static final int[] RC00 = { 809079974, -1058647399, 1824733714, -598304706, 503320719, 2013282877, -1889830782, -1763583214 };
/*   8: 55 */   private static final int[] RC04 = { -533497832, 1142663437, 2134168642, -1819729537, -441926426, 1383381748, 646486951, -1709019491 };
/*   9: 60 */   private static final int[] RC10 = { -1226960659, 1895070382, 117941204, 471764817, 1887059269, -1364032158, -1161161335, 1084518206 };
/*  10: 65 */   private static final int[] RC14 = { 23617341, 94469364, -1123431734, -198759640, 340452812, -89674197, 776532417, -1188837628 };
/*  11: 70 */   private static final int[] RC20 = { -64955950, 877997605, 2061009295, -2076674486, -1150427086, -306741048, -645631146, -1563982796 };
/*  12: 75 */   private static final int[] RC24 = { -497126719, -433865870, 1549313188, 507044583, 2028178333, 660104985, 921544063, 1882893543 };
/*  13:    */   private int V00;
/*  14:    */   private int V01;
/*  15:    */   private int V02;
/*  16:    */   private int V03;
/*  17:    */   private int V04;
/*  18:    */   private int V05;
/*  19:    */   private int V06;
/*  20:    */   private int V07;
/*  21:    */   private int V10;
/*  22:    */   private int V11;
/*  23:    */   private int V12;
/*  24:    */   private int V13;
/*  25:    */   private int V14;
/*  26:    */   private int V15;
/*  27:    */   private int V16;
/*  28:    */   private int V17;
/*  29:    */   private int V20;
/*  30:    */   private int V21;
/*  31:    */   private int V22;
/*  32:    */   private int V23;
/*  33:    */   private int V24;
/*  34:    */   private int V25;
/*  35:    */   private int V26;
/*  36:    */   private int V27;
/*  37:    */   private byte[] tmpBuf;
/*  38:    */   
/*  39:    */   public int getInternalBlockLength()
/*  40:    */   {
/*  41: 95 */     return 32;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public int getBlockLength()
/*  45:    */   {
/*  46:107 */     return -32;
/*  47:    */   }
/*  48:    */   
/*  49:    */   protected Digest copyState(LuffaSmallCore paramLuffaSmallCore)
/*  50:    */   {
/*  51:113 */     paramLuffaSmallCore.V00 = this.V00;
/*  52:114 */     paramLuffaSmallCore.V01 = this.V01;
/*  53:115 */     paramLuffaSmallCore.V02 = this.V02;
/*  54:116 */     paramLuffaSmallCore.V03 = this.V03;
/*  55:117 */     paramLuffaSmallCore.V04 = this.V04;
/*  56:118 */     paramLuffaSmallCore.V05 = this.V05;
/*  57:119 */     paramLuffaSmallCore.V06 = this.V06;
/*  58:120 */     paramLuffaSmallCore.V07 = this.V07;
/*  59:121 */     paramLuffaSmallCore.V10 = this.V10;
/*  60:122 */     paramLuffaSmallCore.V11 = this.V11;
/*  61:123 */     paramLuffaSmallCore.V12 = this.V12;
/*  62:124 */     paramLuffaSmallCore.V13 = this.V13;
/*  63:125 */     paramLuffaSmallCore.V14 = this.V14;
/*  64:126 */     paramLuffaSmallCore.V15 = this.V15;
/*  65:127 */     paramLuffaSmallCore.V16 = this.V16;
/*  66:128 */     paramLuffaSmallCore.V17 = this.V17;
/*  67:129 */     paramLuffaSmallCore.V20 = this.V20;
/*  68:130 */     paramLuffaSmallCore.V21 = this.V21;
/*  69:131 */     paramLuffaSmallCore.V22 = this.V22;
/*  70:132 */     paramLuffaSmallCore.V23 = this.V23;
/*  71:133 */     paramLuffaSmallCore.V24 = this.V24;
/*  72:134 */     paramLuffaSmallCore.V25 = this.V25;
/*  73:135 */     paramLuffaSmallCore.V26 = this.V26;
/*  74:136 */     paramLuffaSmallCore.V27 = this.V27;
/*  75:137 */     return super.copyState(paramLuffaSmallCore);
/*  76:    */   }
/*  77:    */   
/*  78:    */   protected void engineReset()
/*  79:    */   {
/*  80:143 */     this.V00 = IV[0];
/*  81:144 */     this.V01 = IV[1];
/*  82:145 */     this.V02 = IV[2];
/*  83:146 */     this.V03 = IV[3];
/*  84:147 */     this.V04 = IV[4];
/*  85:148 */     this.V05 = IV[5];
/*  86:149 */     this.V06 = IV[6];
/*  87:150 */     this.V07 = IV[7];
/*  88:151 */     this.V10 = IV[8];
/*  89:152 */     this.V11 = IV[9];
/*  90:153 */     this.V12 = IV[10];
/*  91:154 */     this.V13 = IV[11];
/*  92:155 */     this.V14 = IV[12];
/*  93:156 */     this.V15 = IV[13];
/*  94:157 */     this.V16 = IV[14];
/*  95:158 */     this.V17 = IV[15];
/*  96:159 */     this.V20 = IV[16];
/*  97:160 */     this.V21 = IV[17];
/*  98:161 */     this.V22 = IV[18];
/*  99:162 */     this.V23 = IV[19];
/* 100:163 */     this.V24 = IV[20];
/* 101:164 */     this.V25 = IV[21];
/* 102:165 */     this.V26 = IV[22];
/* 103:166 */     this.V27 = IV[23];
/* 104:    */   }
/* 105:    */   
/* 106:    */   protected void doPadding(byte[] paramArrayOfByte, int paramInt)
/* 107:    */   {
/* 108:172 */     int i = flush();
/* 109:173 */     this.tmpBuf[i] = Byte.MIN_VALUE;
/* 110:174 */     for (int j = i + 1; j < 32; j++) {
/* 111:175 */       this.tmpBuf[j] = 0;
/* 112:    */     }
/* 113:176 */     update(this.tmpBuf, i, 32 - i);
/* 114:177 */     for (j = 0; j < i + 1; j++) {
/* 115:178 */       this.tmpBuf[j] = 0;
/* 116:    */     }
/* 117:179 */     update(this.tmpBuf, 0, 32);
/* 118:180 */     encodeBEInt(this.V00 ^ this.V10 ^ this.V20, paramArrayOfByte, paramInt + 0);
/* 119:181 */     encodeBEInt(this.V01 ^ this.V11 ^ this.V21, paramArrayOfByte, paramInt + 4);
/* 120:182 */     encodeBEInt(this.V02 ^ this.V12 ^ this.V22, paramArrayOfByte, paramInt + 8);
/* 121:183 */     encodeBEInt(this.V03 ^ this.V13 ^ this.V23, paramArrayOfByte, paramInt + 12);
/* 122:184 */     encodeBEInt(this.V04 ^ this.V14 ^ this.V24, paramArrayOfByte, paramInt + 16);
/* 123:185 */     encodeBEInt(this.V05 ^ this.V15 ^ this.V25, paramArrayOfByte, paramInt + 20);
/* 124:186 */     encodeBEInt(this.V06 ^ this.V16 ^ this.V26, paramArrayOfByte, paramInt + 24);
/* 125:187 */     if (getDigestLength() == 32) {
/* 126:188 */       encodeBEInt(this.V07 ^ this.V17 ^ this.V27, paramArrayOfByte, paramInt + 28);
/* 127:    */     }
/* 128:    */   }
/* 129:    */   
/* 130:    */   protected void doInit()
/* 131:    */   {
/* 132:194 */     this.tmpBuf = new byte[32];
/* 133:195 */     engineReset();
/* 134:    */   }
/* 135:    */   
/* 136:    */   private static final void encodeBEInt(int paramInt1, byte[] paramArrayOfByte, int paramInt2)
/* 137:    */   {
/* 138:209 */     paramArrayOfByte[(paramInt2 + 0)] = ((byte)(paramInt1 >>> 24));
/* 139:210 */     paramArrayOfByte[(paramInt2 + 1)] = ((byte)(paramInt1 >>> 16));
/* 140:211 */     paramArrayOfByte[(paramInt2 + 2)] = ((byte)(paramInt1 >>> 8));
/* 141:212 */     paramArrayOfByte[(paramInt2 + 3)] = ((byte)paramInt1);
/* 142:    */   }
/* 143:    */   
/* 144:    */   private static final int decodeBEInt(byte[] paramArrayOfByte, int paramInt)
/* 145:    */   {
/* 146:225 */     return (paramArrayOfByte[paramInt] & 0xFF) << 24 | (paramArrayOfByte[(paramInt + 1)] & 0xFF) << 16 | (paramArrayOfByte[(paramInt + 2)] & 0xFF) << 8 | paramArrayOfByte[(paramInt + 3)] & 0xFF;
/* 147:    */   }
/* 148:    */   
/* 149:    */   protected void processBlock(byte[] paramArrayOfByte)
/* 150:    */   {
/* 151:236 */     int i5 = decodeBEInt(paramArrayOfByte, 0);
/* 152:237 */     int i6 = decodeBEInt(paramArrayOfByte, 4);
/* 153:238 */     int i7 = decodeBEInt(paramArrayOfByte, 8);
/* 154:239 */     int i8 = decodeBEInt(paramArrayOfByte, 12);
/* 155:240 */     int i9 = decodeBEInt(paramArrayOfByte, 16);
/* 156:241 */     int i10 = decodeBEInt(paramArrayOfByte, 20);
/* 157:242 */     int i11 = decodeBEInt(paramArrayOfByte, 24);
/* 158:243 */     int i12 = decodeBEInt(paramArrayOfByte, 28);
/* 159:244 */     int j = this.V00 ^ this.V10;
/* 160:245 */     int k = this.V01 ^ this.V11;
/* 161:246 */     int m = this.V02 ^ this.V12;
/* 162:247 */     int n = this.V03 ^ this.V13;
/* 163:248 */     int i1 = this.V04 ^ this.V14;
/* 164:249 */     int i2 = this.V05 ^ this.V15;
/* 165:250 */     int i3 = this.V06 ^ this.V16;
/* 166:251 */     int i4 = this.V07 ^ this.V17;
/* 167:252 */     j ^= this.V20;
/* 168:253 */     k ^= this.V21;
/* 169:254 */     m ^= this.V22;
/* 170:255 */     n ^= this.V23;
/* 171:256 */     i1 ^= this.V24;
/* 172:257 */     i2 ^= this.V25;
/* 173:258 */     i3 ^= this.V26;
/* 174:259 */     i4 ^= this.V27;
/* 175:260 */     int i = i4;
/* 176:261 */     i4 = i3;
/* 177:262 */     i3 = i2;
/* 178:263 */     i2 = i1;
/* 179:264 */     i1 = n ^ i;
/* 180:265 */     n = m ^ i;
/* 181:266 */     m = k;
/* 182:267 */     k = j ^ i;
/* 183:268 */     j = i;
/* 184:269 */     this.V00 = (j ^ this.V00);
/* 185:270 */     this.V01 = (k ^ this.V01);
/* 186:271 */     this.V02 = (m ^ this.V02);
/* 187:272 */     this.V03 = (n ^ this.V03);
/* 188:273 */     this.V04 = (i1 ^ this.V04);
/* 189:274 */     this.V05 = (i2 ^ this.V05);
/* 190:275 */     this.V06 = (i3 ^ this.V06);
/* 191:276 */     this.V07 = (i4 ^ this.V07);
/* 192:277 */     this.V00 = (i5 ^ this.V00);
/* 193:278 */     this.V01 = (i6 ^ this.V01);
/* 194:279 */     this.V02 = (i7 ^ this.V02);
/* 195:280 */     this.V03 = (i8 ^ this.V03);
/* 196:281 */     this.V04 = (i9 ^ this.V04);
/* 197:282 */     this.V05 = (i10 ^ this.V05);
/* 198:283 */     this.V06 = (i11 ^ this.V06);
/* 199:284 */     this.V07 = (i12 ^ this.V07);
/* 200:285 */     i = i12;
/* 201:286 */     i12 = i11;
/* 202:287 */     i11 = i10;
/* 203:288 */     i10 = i9;
/* 204:289 */     i9 = i8 ^ i;
/* 205:290 */     i8 = i7 ^ i;
/* 206:291 */     i7 = i6;
/* 207:292 */     i6 = i5 ^ i;
/* 208:293 */     i5 = i;
/* 209:294 */     this.V10 = (j ^ this.V10);
/* 210:295 */     this.V11 = (k ^ this.V11);
/* 211:296 */     this.V12 = (m ^ this.V12);
/* 212:297 */     this.V13 = (n ^ this.V13);
/* 213:298 */     this.V14 = (i1 ^ this.V14);
/* 214:299 */     this.V15 = (i2 ^ this.V15);
/* 215:300 */     this.V16 = (i3 ^ this.V16);
/* 216:301 */     this.V17 = (i4 ^ this.V17);
/* 217:302 */     this.V10 = (i5 ^ this.V10);
/* 218:303 */     this.V11 = (i6 ^ this.V11);
/* 219:304 */     this.V12 = (i7 ^ this.V12);
/* 220:305 */     this.V13 = (i8 ^ this.V13);
/* 221:306 */     this.V14 = (i9 ^ this.V14);
/* 222:307 */     this.V15 = (i10 ^ this.V15);
/* 223:308 */     this.V16 = (i11 ^ this.V16);
/* 224:309 */     this.V17 = (i12 ^ this.V17);
/* 225:310 */     i = i12;
/* 226:311 */     i12 = i11;
/* 227:312 */     i11 = i10;
/* 228:313 */     i10 = i9;
/* 229:314 */     i9 = i8 ^ i;
/* 230:315 */     i8 = i7 ^ i;
/* 231:316 */     i7 = i6;
/* 232:317 */     i6 = i5 ^ i;
/* 233:318 */     i5 = i;
/* 234:319 */     this.V20 = (j ^ this.V20);
/* 235:320 */     this.V21 = (k ^ this.V21);
/* 236:321 */     this.V22 = (m ^ this.V22);
/* 237:322 */     this.V23 = (n ^ this.V23);
/* 238:323 */     this.V24 = (i1 ^ this.V24);
/* 239:324 */     this.V25 = (i2 ^ this.V25);
/* 240:325 */     this.V26 = (i3 ^ this.V26);
/* 241:326 */     this.V27 = (i4 ^ this.V27);
/* 242:327 */     this.V20 = (i5 ^ this.V20);
/* 243:328 */     this.V21 = (i6 ^ this.V21);
/* 244:329 */     this.V22 = (i7 ^ this.V22);
/* 245:330 */     this.V23 = (i8 ^ this.V23);
/* 246:331 */     this.V24 = (i9 ^ this.V24);
/* 247:332 */     this.V25 = (i10 ^ this.V25);
/* 248:333 */     this.V26 = (i11 ^ this.V26);
/* 249:334 */     this.V27 = (i12 ^ this.V27);
/* 250:335 */     this.V14 = (this.V14 << 1 | this.V14 >>> 31);
/* 251:336 */     this.V15 = (this.V15 << 1 | this.V15 >>> 31);
/* 252:337 */     this.V16 = (this.V16 << 1 | this.V16 >>> 31);
/* 253:338 */     this.V17 = (this.V17 << 1 | this.V17 >>> 31);
/* 254:339 */     this.V24 = (this.V24 << 2 | this.V24 >>> 30);
/* 255:340 */     this.V25 = (this.V25 << 2 | this.V25 >>> 30);
/* 256:341 */     this.V26 = (this.V26 << 2 | this.V26 >>> 30);
/* 257:342 */     this.V27 = (this.V27 << 2 | this.V27 >>> 30);
/* 258:343 */     for (int i13 = 0; i13 < 8; i13++)
/* 259:    */     {
/* 260:344 */       i = this.V00;
/* 261:345 */       this.V00 |= this.V01;
/* 262:346 */       this.V02 ^= this.V03;
/* 263:347 */       this.V01 ^= 0xFFFFFFFF;
/* 264:348 */       this.V00 ^= this.V03;
/* 265:349 */       this.V03 &= i;
/* 266:350 */       this.V01 ^= this.V03;
/* 267:351 */       this.V03 ^= this.V02;
/* 268:352 */       this.V02 &= this.V00;
/* 269:353 */       this.V00 ^= 0xFFFFFFFF;
/* 270:354 */       this.V02 ^= this.V01;
/* 271:355 */       this.V01 |= this.V03;
/* 272:356 */       i ^= this.V01;
/* 273:357 */       this.V03 ^= this.V02;
/* 274:358 */       this.V02 &= this.V01;
/* 275:359 */       this.V01 ^= this.V00;
/* 276:360 */       this.V00 = i;
/* 277:361 */       i = this.V05;
/* 278:362 */       this.V05 |= this.V06;
/* 279:363 */       this.V07 ^= this.V04;
/* 280:364 */       this.V06 ^= 0xFFFFFFFF;
/* 281:365 */       this.V05 ^= this.V04;
/* 282:366 */       this.V04 &= i;
/* 283:367 */       this.V06 ^= this.V04;
/* 284:368 */       this.V04 ^= this.V07;
/* 285:369 */       this.V07 &= this.V05;
/* 286:370 */       this.V05 ^= 0xFFFFFFFF;
/* 287:371 */       this.V07 ^= this.V06;
/* 288:372 */       this.V06 |= this.V04;
/* 289:373 */       i ^= this.V06;
/* 290:374 */       this.V04 ^= this.V07;
/* 291:375 */       this.V07 &= this.V06;
/* 292:376 */       this.V06 ^= this.V05;
/* 293:377 */       this.V05 = i;
/* 294:378 */       this.V04 ^= this.V00;
/* 295:379 */       this.V00 = ((this.V00 << 2 | this.V00 >>> 30) ^ this.V04);
/* 296:380 */       this.V04 = ((this.V04 << 14 | this.V04 >>> 18) ^ this.V00);
/* 297:381 */       this.V00 = ((this.V00 << 10 | this.V00 >>> 22) ^ this.V04);
/* 298:382 */       this.V04 = (this.V04 << 1 | this.V04 >>> 31);
/* 299:383 */       this.V05 ^= this.V01;
/* 300:384 */       this.V01 = ((this.V01 << 2 | this.V01 >>> 30) ^ this.V05);
/* 301:385 */       this.V05 = ((this.V05 << 14 | this.V05 >>> 18) ^ this.V01);
/* 302:386 */       this.V01 = ((this.V01 << 10 | this.V01 >>> 22) ^ this.V05);
/* 303:387 */       this.V05 = (this.V05 << 1 | this.V05 >>> 31);
/* 304:388 */       this.V06 ^= this.V02;
/* 305:389 */       this.V02 = ((this.V02 << 2 | this.V02 >>> 30) ^ this.V06);
/* 306:390 */       this.V06 = ((this.V06 << 14 | this.V06 >>> 18) ^ this.V02);
/* 307:391 */       this.V02 = ((this.V02 << 10 | this.V02 >>> 22) ^ this.V06);
/* 308:392 */       this.V06 = (this.V06 << 1 | this.V06 >>> 31);
/* 309:393 */       this.V07 ^= this.V03;
/* 310:394 */       this.V03 = ((this.V03 << 2 | this.V03 >>> 30) ^ this.V07);
/* 311:395 */       this.V07 = ((this.V07 << 14 | this.V07 >>> 18) ^ this.V03);
/* 312:396 */       this.V03 = ((this.V03 << 10 | this.V03 >>> 22) ^ this.V07);
/* 313:397 */       this.V07 = (this.V07 << 1 | this.V07 >>> 31);
/* 314:398 */       this.V00 ^= RC00[i13];
/* 315:399 */       this.V04 ^= RC04[i13];
/* 316:    */     }
/* 317:401 */     for (i13 = 0; i13 < 8; i13++)
/* 318:    */     {
/* 319:402 */       i = this.V10;
/* 320:403 */       this.V10 |= this.V11;
/* 321:404 */       this.V12 ^= this.V13;
/* 322:405 */       this.V11 ^= 0xFFFFFFFF;
/* 323:406 */       this.V10 ^= this.V13;
/* 324:407 */       this.V13 &= i;
/* 325:408 */       this.V11 ^= this.V13;
/* 326:409 */       this.V13 ^= this.V12;
/* 327:410 */       this.V12 &= this.V10;
/* 328:411 */       this.V10 ^= 0xFFFFFFFF;
/* 329:412 */       this.V12 ^= this.V11;
/* 330:413 */       this.V11 |= this.V13;
/* 331:414 */       i ^= this.V11;
/* 332:415 */       this.V13 ^= this.V12;
/* 333:416 */       this.V12 &= this.V11;
/* 334:417 */       this.V11 ^= this.V10;
/* 335:418 */       this.V10 = i;
/* 336:419 */       i = this.V15;
/* 337:420 */       this.V15 |= this.V16;
/* 338:421 */       this.V17 ^= this.V14;
/* 339:422 */       this.V16 ^= 0xFFFFFFFF;
/* 340:423 */       this.V15 ^= this.V14;
/* 341:424 */       this.V14 &= i;
/* 342:425 */       this.V16 ^= this.V14;
/* 343:426 */       this.V14 ^= this.V17;
/* 344:427 */       this.V17 &= this.V15;
/* 345:428 */       this.V15 ^= 0xFFFFFFFF;
/* 346:429 */       this.V17 ^= this.V16;
/* 347:430 */       this.V16 |= this.V14;
/* 348:431 */       i ^= this.V16;
/* 349:432 */       this.V14 ^= this.V17;
/* 350:433 */       this.V17 &= this.V16;
/* 351:434 */       this.V16 ^= this.V15;
/* 352:435 */       this.V15 = i;
/* 353:436 */       this.V14 ^= this.V10;
/* 354:437 */       this.V10 = ((this.V10 << 2 | this.V10 >>> 30) ^ this.V14);
/* 355:438 */       this.V14 = ((this.V14 << 14 | this.V14 >>> 18) ^ this.V10);
/* 356:439 */       this.V10 = ((this.V10 << 10 | this.V10 >>> 22) ^ this.V14);
/* 357:440 */       this.V14 = (this.V14 << 1 | this.V14 >>> 31);
/* 358:441 */       this.V15 ^= this.V11;
/* 359:442 */       this.V11 = ((this.V11 << 2 | this.V11 >>> 30) ^ this.V15);
/* 360:443 */       this.V15 = ((this.V15 << 14 | this.V15 >>> 18) ^ this.V11);
/* 361:444 */       this.V11 = ((this.V11 << 10 | this.V11 >>> 22) ^ this.V15);
/* 362:445 */       this.V15 = (this.V15 << 1 | this.V15 >>> 31);
/* 363:446 */       this.V16 ^= this.V12;
/* 364:447 */       this.V12 = ((this.V12 << 2 | this.V12 >>> 30) ^ this.V16);
/* 365:448 */       this.V16 = ((this.V16 << 14 | this.V16 >>> 18) ^ this.V12);
/* 366:449 */       this.V12 = ((this.V12 << 10 | this.V12 >>> 22) ^ this.V16);
/* 367:450 */       this.V16 = (this.V16 << 1 | this.V16 >>> 31);
/* 368:451 */       this.V17 ^= this.V13;
/* 369:452 */       this.V13 = ((this.V13 << 2 | this.V13 >>> 30) ^ this.V17);
/* 370:453 */       this.V17 = ((this.V17 << 14 | this.V17 >>> 18) ^ this.V13);
/* 371:454 */       this.V13 = ((this.V13 << 10 | this.V13 >>> 22) ^ this.V17);
/* 372:455 */       this.V17 = (this.V17 << 1 | this.V17 >>> 31);
/* 373:456 */       this.V10 ^= RC10[i13];
/* 374:457 */       this.V14 ^= RC14[i13];
/* 375:    */     }
/* 376:459 */     for (i13 = 0; i13 < 8; i13++)
/* 377:    */     {
/* 378:460 */       i = this.V20;
/* 379:461 */       this.V20 |= this.V21;
/* 380:462 */       this.V22 ^= this.V23;
/* 381:463 */       this.V21 ^= 0xFFFFFFFF;
/* 382:464 */       this.V20 ^= this.V23;
/* 383:465 */       this.V23 &= i;
/* 384:466 */       this.V21 ^= this.V23;
/* 385:467 */       this.V23 ^= this.V22;
/* 386:468 */       this.V22 &= this.V20;
/* 387:469 */       this.V20 ^= 0xFFFFFFFF;
/* 388:470 */       this.V22 ^= this.V21;
/* 389:471 */       this.V21 |= this.V23;
/* 390:472 */       i ^= this.V21;
/* 391:473 */       this.V23 ^= this.V22;
/* 392:474 */       this.V22 &= this.V21;
/* 393:475 */       this.V21 ^= this.V20;
/* 394:476 */       this.V20 = i;
/* 395:477 */       i = this.V25;
/* 396:478 */       this.V25 |= this.V26;
/* 397:479 */       this.V27 ^= this.V24;
/* 398:480 */       this.V26 ^= 0xFFFFFFFF;
/* 399:481 */       this.V25 ^= this.V24;
/* 400:482 */       this.V24 &= i;
/* 401:483 */       this.V26 ^= this.V24;
/* 402:484 */       this.V24 ^= this.V27;
/* 403:485 */       this.V27 &= this.V25;
/* 404:486 */       this.V25 ^= 0xFFFFFFFF;
/* 405:487 */       this.V27 ^= this.V26;
/* 406:488 */       this.V26 |= this.V24;
/* 407:489 */       i ^= this.V26;
/* 408:490 */       this.V24 ^= this.V27;
/* 409:491 */       this.V27 &= this.V26;
/* 410:492 */       this.V26 ^= this.V25;
/* 411:493 */       this.V25 = i;
/* 412:494 */       this.V24 ^= this.V20;
/* 413:495 */       this.V20 = ((this.V20 << 2 | this.V20 >>> 30) ^ this.V24);
/* 414:496 */       this.V24 = ((this.V24 << 14 | this.V24 >>> 18) ^ this.V20);
/* 415:497 */       this.V20 = ((this.V20 << 10 | this.V20 >>> 22) ^ this.V24);
/* 416:498 */       this.V24 = (this.V24 << 1 | this.V24 >>> 31);
/* 417:499 */       this.V25 ^= this.V21;
/* 418:500 */       this.V21 = ((this.V21 << 2 | this.V21 >>> 30) ^ this.V25);
/* 419:501 */       this.V25 = ((this.V25 << 14 | this.V25 >>> 18) ^ this.V21);
/* 420:502 */       this.V21 = ((this.V21 << 10 | this.V21 >>> 22) ^ this.V25);
/* 421:503 */       this.V25 = (this.V25 << 1 | this.V25 >>> 31);
/* 422:504 */       this.V26 ^= this.V22;
/* 423:505 */       this.V22 = ((this.V22 << 2 | this.V22 >>> 30) ^ this.V26);
/* 424:506 */       this.V26 = ((this.V26 << 14 | this.V26 >>> 18) ^ this.V22);
/* 425:507 */       this.V22 = ((this.V22 << 10 | this.V22 >>> 22) ^ this.V26);
/* 426:508 */       this.V26 = (this.V26 << 1 | this.V26 >>> 31);
/* 427:509 */       this.V27 ^= this.V23;
/* 428:510 */       this.V23 = ((this.V23 << 2 | this.V23 >>> 30) ^ this.V27);
/* 429:511 */       this.V27 = ((this.V27 << 14 | this.V27 >>> 18) ^ this.V23);
/* 430:512 */       this.V23 = ((this.V23 << 10 | this.V23 >>> 22) ^ this.V27);
/* 431:513 */       this.V27 = (this.V27 << 1 | this.V27 >>> 31);
/* 432:514 */       this.V20 ^= RC20[i13];
/* 433:515 */       this.V24 ^= RC24[i13];
/* 434:    */     }
/* 435:    */   }
/* 436:    */   
/* 437:    */   public String toString()
/* 438:    */   {
/* 439:522 */     return "Luffa-" + (getDigestLength() << 3);
/* 440:    */   }
/* 441:    */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     fr.cryptohash.LuffaSmallCore
 * JD-Core Version:    0.7.1
 */