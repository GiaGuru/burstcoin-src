/*   1:    */ package fr.cryptohash;
/*   2:    */ 
/*   3:    */ abstract class CubeHashCore
/*   4:    */   extends DigestEngine
/*   5:    */ {
/*   6:    */   private int x0;
/*   7:    */   private int x1;
/*   8:    */   private int x2;
/*   9:    */   private int x3;
/*  10:    */   private int x4;
/*  11:    */   private int x5;
/*  12:    */   private int x6;
/*  13:    */   private int x7;
/*  14:    */   private int x8;
/*  15:    */   private int x9;
/*  16:    */   private int xa;
/*  17:    */   private int xb;
/*  18:    */   private int xc;
/*  19:    */   private int xd;
/*  20:    */   private int xe;
/*  21:    */   private int xf;
/*  22:    */   private int xg;
/*  23:    */   private int xh;
/*  24:    */   private int xi;
/*  25:    */   private int xj;
/*  26:    */   private int xk;
/*  27:    */   private int xl;
/*  28:    */   private int xm;
/*  29:    */   private int xn;
/*  30:    */   private int xo;
/*  31:    */   private int xp;
/*  32:    */   private int xq;
/*  33:    */   private int xr;
/*  34:    */   private int xs;
/*  35:    */   private int xt;
/*  36:    */   private int xu;
/*  37:    */   private int xv;
/*  38:    */   
/*  39:    */   private final void inputBlock(byte[] paramArrayOfByte)
/*  40:    */   {
/*  41: 53 */     this.x0 ^= decodeLEInt(paramArrayOfByte, 0);
/*  42: 54 */     this.x1 ^= decodeLEInt(paramArrayOfByte, 4);
/*  43: 55 */     this.x2 ^= decodeLEInt(paramArrayOfByte, 8);
/*  44: 56 */     this.x3 ^= decodeLEInt(paramArrayOfByte, 12);
/*  45: 57 */     this.x4 ^= decodeLEInt(paramArrayOfByte, 16);
/*  46: 58 */     this.x5 ^= decodeLEInt(paramArrayOfByte, 20);
/*  47: 59 */     this.x6 ^= decodeLEInt(paramArrayOfByte, 24);
/*  48: 60 */     this.x7 ^= decodeLEInt(paramArrayOfByte, 28);
/*  49:    */   }
/*  50:    */   
/*  51:    */   private final void sixteenRounds()
/*  52:    */   {
/*  53: 65 */     for (int i = 0; i < 8; i++)
/*  54:    */     {
/*  55: 66 */       this.xg = (this.x0 + this.xg);
/*  56: 67 */       this.x0 = (this.x0 << 7 | this.x0 >>> 25);
/*  57: 68 */       this.xh = (this.x1 + this.xh);
/*  58: 69 */       this.x1 = (this.x1 << 7 | this.x1 >>> 25);
/*  59: 70 */       this.xi = (this.x2 + this.xi);
/*  60: 71 */       this.x2 = (this.x2 << 7 | this.x2 >>> 25);
/*  61: 72 */       this.xj = (this.x3 + this.xj);
/*  62: 73 */       this.x3 = (this.x3 << 7 | this.x3 >>> 25);
/*  63: 74 */       this.xk = (this.x4 + this.xk);
/*  64: 75 */       this.x4 = (this.x4 << 7 | this.x4 >>> 25);
/*  65: 76 */       this.xl = (this.x5 + this.xl);
/*  66: 77 */       this.x5 = (this.x5 << 7 | this.x5 >>> 25);
/*  67: 78 */       this.xm = (this.x6 + this.xm);
/*  68: 79 */       this.x6 = (this.x6 << 7 | this.x6 >>> 25);
/*  69: 80 */       this.xn = (this.x7 + this.xn);
/*  70: 81 */       this.x7 = (this.x7 << 7 | this.x7 >>> 25);
/*  71: 82 */       this.xo = (this.x8 + this.xo);
/*  72: 83 */       this.x8 = (this.x8 << 7 | this.x8 >>> 25);
/*  73: 84 */       this.xp = (this.x9 + this.xp);
/*  74: 85 */       this.x9 = (this.x9 << 7 | this.x9 >>> 25);
/*  75: 86 */       this.xq = (this.xa + this.xq);
/*  76: 87 */       this.xa = (this.xa << 7 | this.xa >>> 25);
/*  77: 88 */       this.xr = (this.xb + this.xr);
/*  78: 89 */       this.xb = (this.xb << 7 | this.xb >>> 25);
/*  79: 90 */       this.xs = (this.xc + this.xs);
/*  80: 91 */       this.xc = (this.xc << 7 | this.xc >>> 25);
/*  81: 92 */       this.xt = (this.xd + this.xt);
/*  82: 93 */       this.xd = (this.xd << 7 | this.xd >>> 25);
/*  83: 94 */       this.xu = (this.xe + this.xu);
/*  84: 95 */       this.xe = (this.xe << 7 | this.xe >>> 25);
/*  85: 96 */       this.xv = (this.xf + this.xv);
/*  86: 97 */       this.xf = (this.xf << 7 | this.xf >>> 25);
/*  87: 98 */       this.x8 ^= this.xg;
/*  88: 99 */       this.x9 ^= this.xh;
/*  89:100 */       this.xa ^= this.xi;
/*  90:101 */       this.xb ^= this.xj;
/*  91:102 */       this.xc ^= this.xk;
/*  92:103 */       this.xd ^= this.xl;
/*  93:104 */       this.xe ^= this.xm;
/*  94:105 */       this.xf ^= this.xn;
/*  95:106 */       this.x0 ^= this.xo;
/*  96:107 */       this.x1 ^= this.xp;
/*  97:108 */       this.x2 ^= this.xq;
/*  98:109 */       this.x3 ^= this.xr;
/*  99:110 */       this.x4 ^= this.xs;
/* 100:111 */       this.x5 ^= this.xt;
/* 101:112 */       this.x6 ^= this.xu;
/* 102:113 */       this.x7 ^= this.xv;
/* 103:114 */       this.xi = (this.x8 + this.xi);
/* 104:115 */       this.x8 = (this.x8 << 11 | this.x8 >>> 21);
/* 105:116 */       this.xj = (this.x9 + this.xj);
/* 106:117 */       this.x9 = (this.x9 << 11 | this.x9 >>> 21);
/* 107:118 */       this.xg = (this.xa + this.xg);
/* 108:119 */       this.xa = (this.xa << 11 | this.xa >>> 21);
/* 109:120 */       this.xh = (this.xb + this.xh);
/* 110:121 */       this.xb = (this.xb << 11 | this.xb >>> 21);
/* 111:122 */       this.xm = (this.xc + this.xm);
/* 112:123 */       this.xc = (this.xc << 11 | this.xc >>> 21);
/* 113:124 */       this.xn = (this.xd + this.xn);
/* 114:125 */       this.xd = (this.xd << 11 | this.xd >>> 21);
/* 115:126 */       this.xk = (this.xe + this.xk);
/* 116:127 */       this.xe = (this.xe << 11 | this.xe >>> 21);
/* 117:128 */       this.xl = (this.xf + this.xl);
/* 118:129 */       this.xf = (this.xf << 11 | this.xf >>> 21);
/* 119:130 */       this.xq = (this.x0 + this.xq);
/* 120:131 */       this.x0 = (this.x0 << 11 | this.x0 >>> 21);
/* 121:132 */       this.xr = (this.x1 + this.xr);
/* 122:133 */       this.x1 = (this.x1 << 11 | this.x1 >>> 21);
/* 123:134 */       this.xo = (this.x2 + this.xo);
/* 124:135 */       this.x2 = (this.x2 << 11 | this.x2 >>> 21);
/* 125:136 */       this.xp = (this.x3 + this.xp);
/* 126:137 */       this.x3 = (this.x3 << 11 | this.x3 >>> 21);
/* 127:138 */       this.xu = (this.x4 + this.xu);
/* 128:139 */       this.x4 = (this.x4 << 11 | this.x4 >>> 21);
/* 129:140 */       this.xv = (this.x5 + this.xv);
/* 130:141 */       this.x5 = (this.x5 << 11 | this.x5 >>> 21);
/* 131:142 */       this.xs = (this.x6 + this.xs);
/* 132:143 */       this.x6 = (this.x6 << 11 | this.x6 >>> 21);
/* 133:144 */       this.xt = (this.x7 + this.xt);
/* 134:145 */       this.x7 = (this.x7 << 11 | this.x7 >>> 21);
/* 135:146 */       this.xc ^= this.xi;
/* 136:147 */       this.xd ^= this.xj;
/* 137:148 */       this.xe ^= this.xg;
/* 138:149 */       this.xf ^= this.xh;
/* 139:150 */       this.x8 ^= this.xm;
/* 140:151 */       this.x9 ^= this.xn;
/* 141:152 */       this.xa ^= this.xk;
/* 142:153 */       this.xb ^= this.xl;
/* 143:154 */       this.x4 ^= this.xq;
/* 144:155 */       this.x5 ^= this.xr;
/* 145:156 */       this.x6 ^= this.xo;
/* 146:157 */       this.x7 ^= this.xp;
/* 147:158 */       this.x0 ^= this.xu;
/* 148:159 */       this.x1 ^= this.xv;
/* 149:160 */       this.x2 ^= this.xs;
/* 150:161 */       this.x3 ^= this.xt;
/* 151:    */       
/* 152:163 */       this.xj = (this.xc + this.xj);
/* 153:164 */       this.xc = (this.xc << 7 | this.xc >>> 25);
/* 154:165 */       this.xi = (this.xd + this.xi);
/* 155:166 */       this.xd = (this.xd << 7 | this.xd >>> 25);
/* 156:167 */       this.xh = (this.xe + this.xh);
/* 157:168 */       this.xe = (this.xe << 7 | this.xe >>> 25);
/* 158:169 */       this.xg = (this.xf + this.xg);
/* 159:170 */       this.xf = (this.xf << 7 | this.xf >>> 25);
/* 160:171 */       this.xn = (this.x8 + this.xn);
/* 161:172 */       this.x8 = (this.x8 << 7 | this.x8 >>> 25);
/* 162:173 */       this.xm = (this.x9 + this.xm);
/* 163:174 */       this.x9 = (this.x9 << 7 | this.x9 >>> 25);
/* 164:175 */       this.xl = (this.xa + this.xl);
/* 165:176 */       this.xa = (this.xa << 7 | this.xa >>> 25);
/* 166:177 */       this.xk = (this.xb + this.xk);
/* 167:178 */       this.xb = (this.xb << 7 | this.xb >>> 25);
/* 168:179 */       this.xr = (this.x4 + this.xr);
/* 169:180 */       this.x4 = (this.x4 << 7 | this.x4 >>> 25);
/* 170:181 */       this.xq = (this.x5 + this.xq);
/* 171:182 */       this.x5 = (this.x5 << 7 | this.x5 >>> 25);
/* 172:183 */       this.xp = (this.x6 + this.xp);
/* 173:184 */       this.x6 = (this.x6 << 7 | this.x6 >>> 25);
/* 174:185 */       this.xo = (this.x7 + this.xo);
/* 175:186 */       this.x7 = (this.x7 << 7 | this.x7 >>> 25);
/* 176:187 */       this.xv = (this.x0 + this.xv);
/* 177:188 */       this.x0 = (this.x0 << 7 | this.x0 >>> 25);
/* 178:189 */       this.xu = (this.x1 + this.xu);
/* 179:190 */       this.x1 = (this.x1 << 7 | this.x1 >>> 25);
/* 180:191 */       this.xt = (this.x2 + this.xt);
/* 181:192 */       this.x2 = (this.x2 << 7 | this.x2 >>> 25);
/* 182:193 */       this.xs = (this.x3 + this.xs);
/* 183:194 */       this.x3 = (this.x3 << 7 | this.x3 >>> 25);
/* 184:195 */       this.x4 ^= this.xj;
/* 185:196 */       this.x5 ^= this.xi;
/* 186:197 */       this.x6 ^= this.xh;
/* 187:198 */       this.x7 ^= this.xg;
/* 188:199 */       this.x0 ^= this.xn;
/* 189:200 */       this.x1 ^= this.xm;
/* 190:201 */       this.x2 ^= this.xl;
/* 191:202 */       this.x3 ^= this.xk;
/* 192:203 */       this.xc ^= this.xr;
/* 193:204 */       this.xd ^= this.xq;
/* 194:205 */       this.xe ^= this.xp;
/* 195:206 */       this.xf ^= this.xo;
/* 196:207 */       this.x8 ^= this.xv;
/* 197:208 */       this.x9 ^= this.xu;
/* 198:209 */       this.xa ^= this.xt;
/* 199:210 */       this.xb ^= this.xs;
/* 200:211 */       this.xh = (this.x4 + this.xh);
/* 201:212 */       this.x4 = (this.x4 << 11 | this.x4 >>> 21);
/* 202:213 */       this.xg = (this.x5 + this.xg);
/* 203:214 */       this.x5 = (this.x5 << 11 | this.x5 >>> 21);
/* 204:215 */       this.xj = (this.x6 + this.xj);
/* 205:216 */       this.x6 = (this.x6 << 11 | this.x6 >>> 21);
/* 206:217 */       this.xi = (this.x7 + this.xi);
/* 207:218 */       this.x7 = (this.x7 << 11 | this.x7 >>> 21);
/* 208:219 */       this.xl = (this.x0 + this.xl);
/* 209:220 */       this.x0 = (this.x0 << 11 | this.x0 >>> 21);
/* 210:221 */       this.xk = (this.x1 + this.xk);
/* 211:222 */       this.x1 = (this.x1 << 11 | this.x1 >>> 21);
/* 212:223 */       this.xn = (this.x2 + this.xn);
/* 213:224 */       this.x2 = (this.x2 << 11 | this.x2 >>> 21);
/* 214:225 */       this.xm = (this.x3 + this.xm);
/* 215:226 */       this.x3 = (this.x3 << 11 | this.x3 >>> 21);
/* 216:227 */       this.xp = (this.xc + this.xp);
/* 217:228 */       this.xc = (this.xc << 11 | this.xc >>> 21);
/* 218:229 */       this.xo = (this.xd + this.xo);
/* 219:230 */       this.xd = (this.xd << 11 | this.xd >>> 21);
/* 220:231 */       this.xr = (this.xe + this.xr);
/* 221:232 */       this.xe = (this.xe << 11 | this.xe >>> 21);
/* 222:233 */       this.xq = (this.xf + this.xq);
/* 223:234 */       this.xf = (this.xf << 11 | this.xf >>> 21);
/* 224:235 */       this.xt = (this.x8 + this.xt);
/* 225:236 */       this.x8 = (this.x8 << 11 | this.x8 >>> 21);
/* 226:237 */       this.xs = (this.x9 + this.xs);
/* 227:238 */       this.x9 = (this.x9 << 11 | this.x9 >>> 21);
/* 228:239 */       this.xv = (this.xa + this.xv);
/* 229:240 */       this.xa = (this.xa << 11 | this.xa >>> 21);
/* 230:241 */       this.xu = (this.xb + this.xu);
/* 231:242 */       this.xb = (this.xb << 11 | this.xb >>> 21);
/* 232:243 */       this.x0 ^= this.xh;
/* 233:244 */       this.x1 ^= this.xg;
/* 234:245 */       this.x2 ^= this.xj;
/* 235:246 */       this.x3 ^= this.xi;
/* 236:247 */       this.x4 ^= this.xl;
/* 237:248 */       this.x5 ^= this.xk;
/* 238:249 */       this.x6 ^= this.xn;
/* 239:250 */       this.x7 ^= this.xm;
/* 240:251 */       this.x8 ^= this.xp;
/* 241:252 */       this.x9 ^= this.xo;
/* 242:253 */       this.xa ^= this.xr;
/* 243:254 */       this.xb ^= this.xq;
/* 244:255 */       this.xc ^= this.xt;
/* 245:256 */       this.xd ^= this.xs;
/* 246:257 */       this.xe ^= this.xv;
/* 247:258 */       this.xf ^= this.xu;
/* 248:    */     }
/* 249:    */   }
/* 250:    */   
/* 251:    */   private static final void encodeLEInt(int paramInt1, byte[] paramArrayOfByte, int paramInt2)
/* 252:    */   {
/* 253:273 */     paramArrayOfByte[(paramInt2 + 0)] = ((byte)paramInt1);
/* 254:274 */     paramArrayOfByte[(paramInt2 + 1)] = ((byte)(paramInt1 >>> 8));
/* 255:275 */     paramArrayOfByte[(paramInt2 + 2)] = ((byte)(paramInt1 >>> 16));
/* 256:276 */     paramArrayOfByte[(paramInt2 + 3)] = ((byte)(paramInt1 >>> 24));
/* 257:    */   }
/* 258:    */   
/* 259:    */   private static final int decodeLEInt(byte[] paramArrayOfByte, int paramInt)
/* 260:    */   {
/* 261:289 */     return paramArrayOfByte[(paramInt + 0)] & 0xFF | (paramArrayOfByte[(paramInt + 1)] & 0xFF) << 8 | (paramArrayOfByte[(paramInt + 2)] & 0xFF) << 16 | (paramArrayOfByte[(paramInt + 3)] & 0xFF) << 24;
/* 262:    */   }
/* 263:    */   
/* 264:    */   protected void engineReset()
/* 265:    */   {
/* 266:298 */     doReset();
/* 267:    */   }
/* 268:    */   
/* 269:    */   protected void processBlock(byte[] paramArrayOfByte)
/* 270:    */   {
/* 271:304 */     inputBlock(paramArrayOfByte);
/* 272:305 */     sixteenRounds();
/* 273:    */   }
/* 274:    */   
/* 275:    */   protected void doPadding(byte[] paramArrayOfByte, int paramInt)
/* 276:    */   {
/* 277:311 */     int i = flush();
/* 278:312 */     byte[] arrayOfByte = getBlockBuffer();
/* 279:313 */     arrayOfByte[(i++)] = Byte.MIN_VALUE;
/* 280:314 */     while (i < 32) {
/* 281:315 */       arrayOfByte[(i++)] = 0;
/* 282:    */     }
/* 283:316 */     inputBlock(arrayOfByte);
/* 284:317 */     sixteenRounds();
/* 285:318 */     this.xv ^= 0x1;
/* 286:319 */     for (int j = 0; j < 10; j++) {
/* 287:320 */       sixteenRounds();
/* 288:    */     }
/* 289:321 */     j = getDigestLength();
/* 290:322 */     encodeLEInt(this.x0, paramArrayOfByte, paramInt + 0);
/* 291:323 */     encodeLEInt(this.x1, paramArrayOfByte, paramInt + 4);
/* 292:324 */     encodeLEInt(this.x2, paramArrayOfByte, paramInt + 8);
/* 293:325 */     encodeLEInt(this.x3, paramArrayOfByte, paramInt + 12);
/* 294:326 */     encodeLEInt(this.x4, paramArrayOfByte, paramInt + 16);
/* 295:327 */     encodeLEInt(this.x5, paramArrayOfByte, paramInt + 20);
/* 296:328 */     encodeLEInt(this.x6, paramArrayOfByte, paramInt + 24);
/* 297:329 */     if (j == 28) {
/* 298:330 */       return;
/* 299:    */     }
/* 300:331 */     encodeLEInt(this.x7, paramArrayOfByte, paramInt + 28);
/* 301:332 */     if (j == 32) {
/* 302:333 */       return;
/* 303:    */     }
/* 304:334 */     encodeLEInt(this.x8, paramArrayOfByte, paramInt + 32);
/* 305:335 */     encodeLEInt(this.x9, paramArrayOfByte, paramInt + 36);
/* 306:336 */     encodeLEInt(this.xa, paramArrayOfByte, paramInt + 40);
/* 307:337 */     encodeLEInt(this.xb, paramArrayOfByte, paramInt + 44);
/* 308:338 */     if (j == 48) {
/* 309:339 */       return;
/* 310:    */     }
/* 311:340 */     encodeLEInt(this.xc, paramArrayOfByte, paramInt + 48);
/* 312:341 */     encodeLEInt(this.xd, paramArrayOfByte, paramInt + 52);
/* 313:342 */     encodeLEInt(this.xe, paramArrayOfByte, paramInt + 56);
/* 314:343 */     encodeLEInt(this.xf, paramArrayOfByte, paramInt + 60);
/* 315:    */   }
/* 316:    */   
/* 317:    */   protected void doInit()
/* 318:    */   {
/* 319:349 */     doReset();
/* 320:    */   }
/* 321:    */   
/* 322:    */   abstract int[] getIV();
/* 323:    */   
/* 324:    */   public int getInternalBlockLength()
/* 325:    */   {
/* 326:362 */     return 32;
/* 327:    */   }
/* 328:    */   
/* 329:    */   public int getBlockLength()
/* 330:    */   {
/* 331:377 */     return -32;
/* 332:    */   }
/* 333:    */   
/* 334:    */   private final void doReset()
/* 335:    */   {
/* 336:382 */     int[] arrayOfInt = getIV();
/* 337:383 */     this.x0 = arrayOfInt[0];
/* 338:384 */     this.x1 = arrayOfInt[1];
/* 339:385 */     this.x2 = arrayOfInt[2];
/* 340:386 */     this.x3 = arrayOfInt[3];
/* 341:387 */     this.x4 = arrayOfInt[4];
/* 342:388 */     this.x5 = arrayOfInt[5];
/* 343:389 */     this.x6 = arrayOfInt[6];
/* 344:390 */     this.x7 = arrayOfInt[7];
/* 345:391 */     this.x8 = arrayOfInt[8];
/* 346:392 */     this.x9 = arrayOfInt[9];
/* 347:393 */     this.xa = arrayOfInt[10];
/* 348:394 */     this.xb = arrayOfInt[11];
/* 349:395 */     this.xc = arrayOfInt[12];
/* 350:396 */     this.xd = arrayOfInt[13];
/* 351:397 */     this.xe = arrayOfInt[14];
/* 352:398 */     this.xf = arrayOfInt[15];
/* 353:399 */     this.xg = arrayOfInt[16];
/* 354:400 */     this.xh = arrayOfInt[17];
/* 355:401 */     this.xi = arrayOfInt[18];
/* 356:402 */     this.xj = arrayOfInt[19];
/* 357:403 */     this.xk = arrayOfInt[20];
/* 358:404 */     this.xl = arrayOfInt[21];
/* 359:405 */     this.xm = arrayOfInt[22];
/* 360:406 */     this.xn = arrayOfInt[23];
/* 361:407 */     this.xo = arrayOfInt[24];
/* 362:408 */     this.xp = arrayOfInt[25];
/* 363:409 */     this.xq = arrayOfInt[26];
/* 364:410 */     this.xr = arrayOfInt[27];
/* 365:411 */     this.xs = arrayOfInt[28];
/* 366:412 */     this.xt = arrayOfInt[29];
/* 367:413 */     this.xu = arrayOfInt[30];
/* 368:414 */     this.xv = arrayOfInt[31];
/* 369:    */   }
/* 370:    */   
/* 371:    */   protected Digest copyState(CubeHashCore paramCubeHashCore)
/* 372:    */   {
/* 373:420 */     paramCubeHashCore.x0 = this.x0;
/* 374:421 */     paramCubeHashCore.x1 = this.x1;
/* 375:422 */     paramCubeHashCore.x2 = this.x2;
/* 376:423 */     paramCubeHashCore.x3 = this.x3;
/* 377:424 */     paramCubeHashCore.x4 = this.x4;
/* 378:425 */     paramCubeHashCore.x5 = this.x5;
/* 379:426 */     paramCubeHashCore.x6 = this.x6;
/* 380:427 */     paramCubeHashCore.x7 = this.x7;
/* 381:428 */     paramCubeHashCore.x8 = this.x8;
/* 382:429 */     paramCubeHashCore.x9 = this.x9;
/* 383:430 */     paramCubeHashCore.xa = this.xa;
/* 384:431 */     paramCubeHashCore.xb = this.xb;
/* 385:432 */     paramCubeHashCore.xc = this.xc;
/* 386:433 */     paramCubeHashCore.xd = this.xd;
/* 387:434 */     paramCubeHashCore.xe = this.xe;
/* 388:435 */     paramCubeHashCore.xf = this.xf;
/* 389:436 */     paramCubeHashCore.xg = this.xg;
/* 390:437 */     paramCubeHashCore.xh = this.xh;
/* 391:438 */     paramCubeHashCore.xi = this.xi;
/* 392:439 */     paramCubeHashCore.xj = this.xj;
/* 393:440 */     paramCubeHashCore.xk = this.xk;
/* 394:441 */     paramCubeHashCore.xl = this.xl;
/* 395:442 */     paramCubeHashCore.xm = this.xm;
/* 396:443 */     paramCubeHashCore.xn = this.xn;
/* 397:444 */     paramCubeHashCore.xo = this.xo;
/* 398:445 */     paramCubeHashCore.xp = this.xp;
/* 399:446 */     paramCubeHashCore.xq = this.xq;
/* 400:447 */     paramCubeHashCore.xr = this.xr;
/* 401:448 */     paramCubeHashCore.xs = this.xs;
/* 402:449 */     paramCubeHashCore.xt = this.xt;
/* 403:450 */     paramCubeHashCore.xu = this.xu;
/* 404:451 */     paramCubeHashCore.xv = this.xv;
/* 405:452 */     return super.copyState(paramCubeHashCore);
/* 406:    */   }
/* 407:    */   
/* 408:    */   public String toString()
/* 409:    */   {
/* 410:458 */     return "CubeHash-" + (getDigestLength() << 3);
/* 411:    */   }
/* 412:    */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     fr.cryptohash.CubeHashCore
 * JD-Core Version:    0.7.1
 */