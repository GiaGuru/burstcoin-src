/*   1:    */ package fr.cryptohash;
/*   2:    */ 
/*   3:    */ public class ShabalGeneric
/*   4:    */   implements Digest
/*   5:    */ {
/*   6:    */   private int outSizeW32;
/*   7:    */   private byte[] buf;
/*   8:    */   private int ptr;
/*   9:    */   private int[] state;
/*  10:    */   private long W;
/*  11:    */   
/*  12:    */   private ShabalGeneric()
/*  13:    */   {
/*  14: 54 */     this.buf = new byte[64];
/*  15: 55 */     this.state = new int[44];
/*  16:    */   }
/*  17:    */   
/*  18:    */   public ShabalGeneric(int paramInt)
/*  19:    */   {
/*  20: 66 */     this();
/*  21: 67 */     if ((paramInt < 32) || (paramInt > 512) || ((paramInt & 0x1F) != 0)) {
/*  22: 68 */       throw new IllegalArgumentException("invalid Shabal output size: " + paramInt);
/*  23:    */     }
/*  24: 70 */     this.outSizeW32 = (paramInt >>> 5);
/*  25: 71 */     reset();
/*  26:    */   }
/*  27:    */   
/*  28:    */   public void update(byte paramByte)
/*  29:    */   {
/*  30: 77 */     this.buf[(this.ptr++)] = paramByte;
/*  31: 78 */     if (this.ptr == 64)
/*  32:    */     {
/*  33: 79 */       core(this.buf, 0, 1);
/*  34: 80 */       this.ptr = 0;
/*  35:    */     }
/*  36:    */   }
/*  37:    */   
/*  38:    */   public void update(byte[] paramArrayOfByte)
/*  39:    */   {
/*  40: 87 */     update(paramArrayOfByte, 0, paramArrayOfByte.length);
/*  41:    */   }
/*  42:    */   
/*  43:    */   public void update(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*  44:    */   {
/*  45: 93 */     if (this.ptr != 0)
/*  46:    */     {
/*  47: 94 */       i = 64 - this.ptr;
/*  48: 95 */       if (paramInt2 < i)
/*  49:    */       {
/*  50: 96 */         System.arraycopy(paramArrayOfByte, paramInt1, this.buf, this.ptr, paramInt2);
/*  51: 97 */         this.ptr += paramInt2;
/*  52: 98 */         return;
/*  53:    */       }
/*  54:100 */       System.arraycopy(paramArrayOfByte, paramInt1, this.buf, this.ptr, i);
/*  55:101 */       paramInt1 += i;
/*  56:102 */       paramInt2 -= i;
/*  57:103 */       core(this.buf, 0, 1);
/*  58:    */     }
/*  59:106 */     int i = paramInt2 >>> 6;
/*  60:107 */     if (i > 0)
/*  61:    */     {
/*  62:108 */       core(paramArrayOfByte, paramInt1, i);
/*  63:109 */       paramInt1 += (i << 6);
/*  64:110 */       paramInt2 &= 0x3F;
/*  65:    */     }
/*  66:112 */     System.arraycopy(paramArrayOfByte, paramInt1, this.buf, 0, paramInt2);
/*  67:113 */     this.ptr = paramInt2;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public int getDigestLength()
/*  71:    */   {
/*  72:119 */     return this.outSizeW32 << 2;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public byte[] digest()
/*  76:    */   {
/*  77:125 */     int i = getDigestLength();
/*  78:126 */     byte[] arrayOfByte = new byte[i];
/*  79:127 */     digest(arrayOfByte, 0, i);
/*  80:128 */     return arrayOfByte;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public byte[] digest(byte[] paramArrayOfByte)
/*  84:    */   {
/*  85:134 */     update(paramArrayOfByte, 0, paramArrayOfByte.length);
/*  86:135 */     return digest();
/*  87:    */   }
/*  88:    */   
/*  89:    */   public int digest(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*  90:    */   {
/*  91:141 */     int i = getDigestLength();
/*  92:142 */     if (paramInt2 > i) {
/*  93:143 */       paramInt2 = i;
/*  94:    */     }
/*  95:144 */     this.buf[(this.ptr++)] = Byte.MIN_VALUE;
/*  96:145 */     for (int j = this.ptr; j < 64; j++) {
/*  97:146 */       this.buf[j] = 0;
/*  98:    */     }
/*  99:147 */     for (j = 0; j < 4; j++)
/* 100:    */     {
/* 101:148 */       core(this.buf, 0, 1);
/* 102:149 */       this.W -= 1L;
/* 103:    */     }
/* 104:151 */     j = 44 - (i >>> 2);
/* 105:152 */     int k = 0;
/* 106:153 */     for (int m = 0; m < paramInt2; m++)
/* 107:    */     {
/* 108:154 */       if ((m & 0x3) == 0) {
/* 109:155 */         k = this.state[(j++)];
/* 110:    */       }
/* 111:156 */       paramArrayOfByte[(m + paramInt1)] = ((byte)k);
/* 112:157 */       k >>>= 8;
/* 113:    */     }
/* 114:159 */     reset();
/* 115:160 */     return paramInt2;
/* 116:    */   }
/* 117:    */   
/* 118:163 */   private static final int[][] IVs = new int[16][];
/* 119:    */   
/* 120:    */   private static int[] getIV(int paramInt)
/* 121:    */   {
/* 122:167 */     int[] arrayOfInt = IVs[(paramInt - 1)];
/* 123:168 */     if (arrayOfInt == null)
/* 124:    */     {
/* 125:169 */       int i = paramInt << 5;
/* 126:170 */       ShabalGeneric localShabalGeneric = new ShabalGeneric();
/* 127:171 */       for (int j = 0; j < 44; j++) {
/* 128:172 */         localShabalGeneric.state[j] = 0;
/* 129:    */       }
/* 130:173 */       localShabalGeneric.W = -1L;
/* 131:174 */       for (j = 0; j < 16; j++)
/* 132:    */       {
/* 133:175 */         localShabalGeneric.buf[((j << 2) + 0)] = ((byte)(i + j));
/* 134:    */         
/* 135:177 */         localShabalGeneric.buf[((j << 2) + 1)] = ((byte)(i + j >>> 8));
/* 136:    */       }
/* 137:180 */       localShabalGeneric.core(localShabalGeneric.buf, 0, 1);
/* 138:181 */       for (j = 0; j < 16; j++)
/* 139:    */       {
/* 140:182 */         localShabalGeneric.buf[((j << 2) + 0)] = ((byte)(i + j + 16));
/* 141:    */         
/* 142:184 */         localShabalGeneric.buf[((j << 2) + 1)] = ((byte)(i + j + 16 >>> 8));
/* 143:    */       }
/* 144:187 */       localShabalGeneric.core(localShabalGeneric.buf, 0, 1);
/* 145:188 */       arrayOfInt = IVs[(paramInt - 1)] = localShabalGeneric.state;
/* 146:    */     }
/* 147:190 */     return arrayOfInt;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public void reset()
/* 151:    */   {
/* 152:196 */     System.arraycopy(getIV(this.outSizeW32), 0, this.state, 0, 44);
/* 153:197 */     this.W = 1L;
/* 154:198 */     this.ptr = 0;
/* 155:    */   }
/* 156:    */   
/* 157:    */   public Digest copy()
/* 158:    */   {
/* 159:204 */     ShabalGeneric localShabalGeneric = dup();
/* 160:205 */     localShabalGeneric.outSizeW32 = this.outSizeW32;
/* 161:206 */     System.arraycopy(this.buf, 0, localShabalGeneric.buf, 0, this.ptr);
/* 162:207 */     localShabalGeneric.ptr = this.ptr;
/* 163:208 */     System.arraycopy(this.state, 0, localShabalGeneric.state, 0, 44);
/* 164:209 */     localShabalGeneric.W = this.W;
/* 165:210 */     return localShabalGeneric;
/* 166:    */   }
/* 167:    */   
/* 168:    */   ShabalGeneric dup()
/* 169:    */   {
/* 170:221 */     return new ShabalGeneric();
/* 171:    */   }
/* 172:    */   
/* 173:    */   public int getBlockLength()
/* 174:    */   {
/* 175:227 */     return 64;
/* 176:    */   }
/* 177:    */   
/* 178:230 */   private int[] M = new int[16];
/* 179:    */   
/* 180:    */   private static final int decodeLEInt(byte[] paramArrayOfByte, int paramInt)
/* 181:    */   {
/* 182:234 */     return paramArrayOfByte[(paramInt + 0)] & 0xFF | (paramArrayOfByte[(paramInt + 1)] & 0xFF) << 8 | (paramArrayOfByte[(paramInt + 2)] & 0xFF) << 16 | (paramArrayOfByte[(paramInt + 3)] & 0xFF) << 24;
/* 183:    */   }
/* 184:    */   
/* 185:    */   private final void core(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/* 186:    */   {
/* 187:242 */     int i = this.state[0];
/* 188:243 */     int j = this.state[1];
/* 189:244 */     int k = this.state[2];
/* 190:245 */     int m = this.state[3];
/* 191:246 */     int n = this.state[4];
/* 192:247 */     int i1 = this.state[5];
/* 193:248 */     int i2 = this.state[6];
/* 194:249 */     int i3 = this.state[7];
/* 195:250 */     int i4 = this.state[8];
/* 196:251 */     int i5 = this.state[9];
/* 197:252 */     int i6 = this.state[10];
/* 198:253 */     int i7 = this.state[11];
/* 199:    */     
/* 200:255 */     int i8 = this.state[12];
/* 201:256 */     int i9 = this.state[13];
/* 202:257 */     int i10 = this.state[14];
/* 203:258 */     int i11 = this.state[15];
/* 204:259 */     int i12 = this.state[16];
/* 205:260 */     int i13 = this.state[17];
/* 206:261 */     int i14 = this.state[18];
/* 207:262 */     int i15 = this.state[19];
/* 208:263 */     int i16 = this.state[20];
/* 209:264 */     int i17 = this.state[21];
/* 210:265 */     int i18 = this.state[22];
/* 211:266 */     int i19 = this.state[23];
/* 212:267 */     int i20 = this.state[24];
/* 213:268 */     int i21 = this.state[25];
/* 214:269 */     int i22 = this.state[26];
/* 215:270 */     int i23 = this.state[27];
/* 216:    */     
/* 217:272 */     int i24 = this.state[28];
/* 218:273 */     int i25 = this.state[29];
/* 219:274 */     int i26 = this.state[30];
/* 220:275 */     int i27 = this.state[31];
/* 221:276 */     int i28 = this.state[32];
/* 222:277 */     int i29 = this.state[33];
/* 223:278 */     int i30 = this.state[34];
/* 224:279 */     int i31 = this.state[35];
/* 225:280 */     int i32 = this.state[36];
/* 226:281 */     int i33 = this.state[37];
/* 227:282 */     int i34 = this.state[38];
/* 228:283 */     int i35 = this.state[39];
/* 229:284 */     int i36 = this.state[40];
/* 230:285 */     int i37 = this.state[41];
/* 231:286 */     int i38 = this.state[42];
/* 232:    */     int i56;
/* 233:287 */     for (int i39 = this.state[43]; paramInt2-- > 0; i39 = i56)
/* 234:    */     {
/* 235:290 */       int i40 = decodeLEInt(paramArrayOfByte, paramInt1 + 0);
/* 236:291 */       i8 += i40;
/* 237:292 */       i8 = i8 << 17 | i8 >>> 15;
/* 238:293 */       int i41 = decodeLEInt(paramArrayOfByte, paramInt1 + 4);
/* 239:294 */       i9 += i41;
/* 240:295 */       i9 = i9 << 17 | i9 >>> 15;
/* 241:296 */       int i42 = decodeLEInt(paramArrayOfByte, paramInt1 + 8);
/* 242:297 */       i10 += i42;
/* 243:298 */       i10 = i10 << 17 | i10 >>> 15;
/* 244:299 */       int i43 = decodeLEInt(paramArrayOfByte, paramInt1 + 12);
/* 245:300 */       i11 += i43;
/* 246:301 */       i11 = i11 << 17 | i11 >>> 15;
/* 247:302 */       int i44 = decodeLEInt(paramArrayOfByte, paramInt1 + 16);
/* 248:303 */       i12 += i44;
/* 249:304 */       i12 = i12 << 17 | i12 >>> 15;
/* 250:305 */       int i45 = decodeLEInt(paramArrayOfByte, paramInt1 + 20);
/* 251:306 */       i13 += i45;
/* 252:307 */       i13 = i13 << 17 | i13 >>> 15;
/* 253:308 */       int i46 = decodeLEInt(paramArrayOfByte, paramInt1 + 24);
/* 254:309 */       i14 += i46;
/* 255:310 */       i14 = i14 << 17 | i14 >>> 15;
/* 256:311 */       int i47 = decodeLEInt(paramArrayOfByte, paramInt1 + 28);
/* 257:312 */       i15 += i47;
/* 258:313 */       i15 = i15 << 17 | i15 >>> 15;
/* 259:314 */       int i48 = decodeLEInt(paramArrayOfByte, paramInt1 + 32);
/* 260:315 */       i16 += i48;
/* 261:316 */       i16 = i16 << 17 | i16 >>> 15;
/* 262:317 */       int i49 = decodeLEInt(paramArrayOfByte, paramInt1 + 36);
/* 263:318 */       i17 += i49;
/* 264:319 */       i17 = i17 << 17 | i17 >>> 15;
/* 265:320 */       int i50 = decodeLEInt(paramArrayOfByte, paramInt1 + 40);
/* 266:321 */       i18 += i50;
/* 267:322 */       i18 = i18 << 17 | i18 >>> 15;
/* 268:323 */       int i51 = decodeLEInt(paramArrayOfByte, paramInt1 + 44);
/* 269:324 */       i19 += i51;
/* 270:325 */       i19 = i19 << 17 | i19 >>> 15;
/* 271:326 */       int i52 = decodeLEInt(paramArrayOfByte, paramInt1 + 48);
/* 272:327 */       i20 += i52;
/* 273:328 */       i20 = i20 << 17 | i20 >>> 15;
/* 274:329 */       int i53 = decodeLEInt(paramArrayOfByte, paramInt1 + 52);
/* 275:330 */       i21 += i53;
/* 276:331 */       i21 = i21 << 17 | i21 >>> 15;
/* 277:332 */       int i54 = decodeLEInt(paramArrayOfByte, paramInt1 + 56);
/* 278:333 */       i22 += i54;
/* 279:334 */       i22 = i22 << 17 | i22 >>> 15;
/* 280:335 */       int i55 = decodeLEInt(paramArrayOfByte, paramInt1 + 60);
/* 281:336 */       i23 += i55;
/* 282:337 */       i23 = i23 << 17 | i23 >>> 15;
/* 283:    */       
/* 284:339 */       paramInt1 += 64;
/* 285:340 */       i ^= (int)this.W;
/* 286:341 */       j ^= (int)(this.W >>> 32);
/* 287:342 */       this.W += 1L;
/* 288:    */       
/* 289:344 */       i = (i ^ (i7 << 15 | i7 >>> 17) * 5 ^ i32) * 3 ^ i21 ^ i17 & (i14 ^ 0xFFFFFFFF) ^ i40;
/* 290:    */       
/* 291:346 */       i8 = (i8 << 1 | i8 >>> 31) ^ 0xFFFFFFFF ^ i;
/* 292:347 */       j = (j ^ (i << 15 | i >>> 17) * 5 ^ i31) * 3 ^ i22 ^ i18 & (i15 ^ 0xFFFFFFFF) ^ i41;
/* 293:    */       
/* 294:349 */       i9 = (i9 << 1 | i9 >>> 31) ^ 0xFFFFFFFF ^ j;
/* 295:350 */       k = (k ^ (j << 15 | j >>> 17) * 5 ^ i30) * 3 ^ i23 ^ i19 & (i16 ^ 0xFFFFFFFF) ^ i42;
/* 296:    */       
/* 297:352 */       i10 = (i10 << 1 | i10 >>> 31) ^ 0xFFFFFFFF ^ k;
/* 298:353 */       m = (m ^ (k << 15 | k >>> 17) * 5 ^ i29) * 3 ^ i8 ^ i20 & (i17 ^ 0xFFFFFFFF) ^ i43;
/* 299:    */       
/* 300:355 */       i11 = (i11 << 1 | i11 >>> 31) ^ 0xFFFFFFFF ^ m;
/* 301:356 */       n = (n ^ (m << 15 | m >>> 17) * 5 ^ i28) * 3 ^ i9 ^ i21 & (i18 ^ 0xFFFFFFFF) ^ i44;
/* 302:    */       
/* 303:358 */       i12 = (i12 << 1 | i12 >>> 31) ^ 0xFFFFFFFF ^ n;
/* 304:359 */       i1 = (i1 ^ (n << 15 | n >>> 17) * 5 ^ i27) * 3 ^ i10 ^ i22 & (i19 ^ 0xFFFFFFFF) ^ i45;
/* 305:    */       
/* 306:361 */       i13 = (i13 << 1 | i13 >>> 31) ^ 0xFFFFFFFF ^ i1;
/* 307:362 */       i2 = (i2 ^ (i1 << 15 | i1 >>> 17) * 5 ^ i26) * 3 ^ i11 ^ i23 & (i20 ^ 0xFFFFFFFF) ^ i46;
/* 308:    */       
/* 309:364 */       i14 = (i14 << 1 | i14 >>> 31) ^ 0xFFFFFFFF ^ i2;
/* 310:365 */       i3 = (i3 ^ (i2 << 15 | i2 >>> 17) * 5 ^ i25) * 3 ^ i12 ^ i8 & (i21 ^ 0xFFFFFFFF) ^ i47;
/* 311:    */       
/* 312:367 */       i15 = (i15 << 1 | i15 >>> 31) ^ 0xFFFFFFFF ^ i3;
/* 313:368 */       i4 = (i4 ^ (i3 << 15 | i3 >>> 17) * 5 ^ i24) * 3 ^ i13 ^ i9 & (i22 ^ 0xFFFFFFFF) ^ i48;
/* 314:    */       
/* 315:370 */       i16 = (i16 << 1 | i16 >>> 31) ^ 0xFFFFFFFF ^ i4;
/* 316:371 */       i5 = (i5 ^ (i4 << 15 | i4 >>> 17) * 5 ^ i39) * 3 ^ i14 ^ i10 & (i23 ^ 0xFFFFFFFF) ^ i49;
/* 317:    */       
/* 318:373 */       i17 = (i17 << 1 | i17 >>> 31) ^ 0xFFFFFFFF ^ i5;
/* 319:374 */       i6 = (i6 ^ (i5 << 15 | i5 >>> 17) * 5 ^ i38) * 3 ^ i15 ^ i11 & (i8 ^ 0xFFFFFFFF) ^ i50;
/* 320:    */       
/* 321:376 */       i18 = (i18 << 1 | i18 >>> 31) ^ 0xFFFFFFFF ^ i6;
/* 322:377 */       i7 = (i7 ^ (i6 << 15 | i6 >>> 17) * 5 ^ i37) * 3 ^ i16 ^ i12 & (i9 ^ 0xFFFFFFFF) ^ i51;
/* 323:    */       
/* 324:379 */       i19 = (i19 << 1 | i19 >>> 31) ^ 0xFFFFFFFF ^ i7;
/* 325:380 */       i = (i ^ (i7 << 15 | i7 >>> 17) * 5 ^ i36) * 3 ^ i17 ^ i13 & (i10 ^ 0xFFFFFFFF) ^ i52;
/* 326:    */       
/* 327:382 */       i20 = (i20 << 1 | i20 >>> 31) ^ 0xFFFFFFFF ^ i;
/* 328:383 */       j = (j ^ (i << 15 | i >>> 17) * 5 ^ i35) * 3 ^ i18 ^ i14 & (i11 ^ 0xFFFFFFFF) ^ i53;
/* 329:    */       
/* 330:385 */       i21 = (i21 << 1 | i21 >>> 31) ^ 0xFFFFFFFF ^ j;
/* 331:386 */       k = (k ^ (j << 15 | j >>> 17) * 5 ^ i34) * 3 ^ i19 ^ i15 & (i12 ^ 0xFFFFFFFF) ^ i54;
/* 332:    */       
/* 333:388 */       i22 = (i22 << 1 | i22 >>> 31) ^ 0xFFFFFFFF ^ k;
/* 334:389 */       m = (m ^ (k << 15 | k >>> 17) * 5 ^ i33) * 3 ^ i20 ^ i16 & (i13 ^ 0xFFFFFFFF) ^ i55;
/* 335:    */       
/* 336:391 */       i23 = (i23 << 1 | i23 >>> 31) ^ 0xFFFFFFFF ^ m;
/* 337:392 */       n = (n ^ (m << 15 | m >>> 17) * 5 ^ i32) * 3 ^ i21 ^ i17 & (i14 ^ 0xFFFFFFFF) ^ i40;
/* 338:    */       
/* 339:394 */       i8 = (i8 << 1 | i8 >>> 31) ^ 0xFFFFFFFF ^ n;
/* 340:395 */       i1 = (i1 ^ (n << 15 | n >>> 17) * 5 ^ i31) * 3 ^ i22 ^ i18 & (i15 ^ 0xFFFFFFFF) ^ i41;
/* 341:    */       
/* 342:397 */       i9 = (i9 << 1 | i9 >>> 31) ^ 0xFFFFFFFF ^ i1;
/* 343:398 */       i2 = (i2 ^ (i1 << 15 | i1 >>> 17) * 5 ^ i30) * 3 ^ i23 ^ i19 & (i16 ^ 0xFFFFFFFF) ^ i42;
/* 344:    */       
/* 345:400 */       i10 = (i10 << 1 | i10 >>> 31) ^ 0xFFFFFFFF ^ i2;
/* 346:401 */       i3 = (i3 ^ (i2 << 15 | i2 >>> 17) * 5 ^ i29) * 3 ^ i8 ^ i20 & (i17 ^ 0xFFFFFFFF) ^ i43;
/* 347:    */       
/* 348:403 */       i11 = (i11 << 1 | i11 >>> 31) ^ 0xFFFFFFFF ^ i3;
/* 349:404 */       i4 = (i4 ^ (i3 << 15 | i3 >>> 17) * 5 ^ i28) * 3 ^ i9 ^ i21 & (i18 ^ 0xFFFFFFFF) ^ i44;
/* 350:    */       
/* 351:406 */       i12 = (i12 << 1 | i12 >>> 31) ^ 0xFFFFFFFF ^ i4;
/* 352:407 */       i5 = (i5 ^ (i4 << 15 | i4 >>> 17) * 5 ^ i27) * 3 ^ i10 ^ i22 & (i19 ^ 0xFFFFFFFF) ^ i45;
/* 353:    */       
/* 354:409 */       i13 = (i13 << 1 | i13 >>> 31) ^ 0xFFFFFFFF ^ i5;
/* 355:410 */       i6 = (i6 ^ (i5 << 15 | i5 >>> 17) * 5 ^ i26) * 3 ^ i11 ^ i23 & (i20 ^ 0xFFFFFFFF) ^ i46;
/* 356:    */       
/* 357:412 */       i14 = (i14 << 1 | i14 >>> 31) ^ 0xFFFFFFFF ^ i6;
/* 358:413 */       i7 = (i7 ^ (i6 << 15 | i6 >>> 17) * 5 ^ i25) * 3 ^ i12 ^ i8 & (i21 ^ 0xFFFFFFFF) ^ i47;
/* 359:    */       
/* 360:415 */       i15 = (i15 << 1 | i15 >>> 31) ^ 0xFFFFFFFF ^ i7;
/* 361:416 */       i = (i ^ (i7 << 15 | i7 >>> 17) * 5 ^ i24) * 3 ^ i13 ^ i9 & (i22 ^ 0xFFFFFFFF) ^ i48;
/* 362:    */       
/* 363:418 */       i16 = (i16 << 1 | i16 >>> 31) ^ 0xFFFFFFFF ^ i;
/* 364:419 */       j = (j ^ (i << 15 | i >>> 17) * 5 ^ i39) * 3 ^ i14 ^ i10 & (i23 ^ 0xFFFFFFFF) ^ i49;
/* 365:    */       
/* 366:421 */       i17 = (i17 << 1 | i17 >>> 31) ^ 0xFFFFFFFF ^ j;
/* 367:422 */       k = (k ^ (j << 15 | j >>> 17) * 5 ^ i38) * 3 ^ i15 ^ i11 & (i8 ^ 0xFFFFFFFF) ^ i50;
/* 368:    */       
/* 369:424 */       i18 = (i18 << 1 | i18 >>> 31) ^ 0xFFFFFFFF ^ k;
/* 370:425 */       m = (m ^ (k << 15 | k >>> 17) * 5 ^ i37) * 3 ^ i16 ^ i12 & (i9 ^ 0xFFFFFFFF) ^ i51;
/* 371:    */       
/* 372:427 */       i19 = (i19 << 1 | i19 >>> 31) ^ 0xFFFFFFFF ^ m;
/* 373:428 */       n = (n ^ (m << 15 | m >>> 17) * 5 ^ i36) * 3 ^ i17 ^ i13 & (i10 ^ 0xFFFFFFFF) ^ i52;
/* 374:    */       
/* 375:430 */       i20 = (i20 << 1 | i20 >>> 31) ^ 0xFFFFFFFF ^ n;
/* 376:431 */       i1 = (i1 ^ (n << 15 | n >>> 17) * 5 ^ i35) * 3 ^ i18 ^ i14 & (i11 ^ 0xFFFFFFFF) ^ i53;
/* 377:    */       
/* 378:433 */       i21 = (i21 << 1 | i21 >>> 31) ^ 0xFFFFFFFF ^ i1;
/* 379:434 */       i2 = (i2 ^ (i1 << 15 | i1 >>> 17) * 5 ^ i34) * 3 ^ i19 ^ i15 & (i12 ^ 0xFFFFFFFF) ^ i54;
/* 380:    */       
/* 381:436 */       i22 = (i22 << 1 | i22 >>> 31) ^ 0xFFFFFFFF ^ i2;
/* 382:437 */       i3 = (i3 ^ (i2 << 15 | i2 >>> 17) * 5 ^ i33) * 3 ^ i20 ^ i16 & (i13 ^ 0xFFFFFFFF) ^ i55;
/* 383:    */       
/* 384:439 */       i23 = (i23 << 1 | i23 >>> 31) ^ 0xFFFFFFFF ^ i3;
/* 385:440 */       i4 = (i4 ^ (i3 << 15 | i3 >>> 17) * 5 ^ i32) * 3 ^ i21 ^ i17 & (i14 ^ 0xFFFFFFFF) ^ i40;
/* 386:    */       
/* 387:442 */       i8 = (i8 << 1 | i8 >>> 31) ^ 0xFFFFFFFF ^ i4;
/* 388:443 */       i5 = (i5 ^ (i4 << 15 | i4 >>> 17) * 5 ^ i31) * 3 ^ i22 ^ i18 & (i15 ^ 0xFFFFFFFF) ^ i41;
/* 389:    */       
/* 390:445 */       i9 = (i9 << 1 | i9 >>> 31) ^ 0xFFFFFFFF ^ i5;
/* 391:446 */       i6 = (i6 ^ (i5 << 15 | i5 >>> 17) * 5 ^ i30) * 3 ^ i23 ^ i19 & (i16 ^ 0xFFFFFFFF) ^ i42;
/* 392:    */       
/* 393:448 */       i10 = (i10 << 1 | i10 >>> 31) ^ 0xFFFFFFFF ^ i6;
/* 394:449 */       i7 = (i7 ^ (i6 << 15 | i6 >>> 17) * 5 ^ i29) * 3 ^ i8 ^ i20 & (i17 ^ 0xFFFFFFFF) ^ i43;
/* 395:    */       
/* 396:451 */       i11 = (i11 << 1 | i11 >>> 31) ^ 0xFFFFFFFF ^ i7;
/* 397:452 */       i = (i ^ (i7 << 15 | i7 >>> 17) * 5 ^ i28) * 3 ^ i9 ^ i21 & (i18 ^ 0xFFFFFFFF) ^ i44;
/* 398:    */       
/* 399:454 */       i12 = (i12 << 1 | i12 >>> 31) ^ 0xFFFFFFFF ^ i;
/* 400:455 */       j = (j ^ (i << 15 | i >>> 17) * 5 ^ i27) * 3 ^ i10 ^ i22 & (i19 ^ 0xFFFFFFFF) ^ i45;
/* 401:    */       
/* 402:457 */       i13 = (i13 << 1 | i13 >>> 31) ^ 0xFFFFFFFF ^ j;
/* 403:458 */       k = (k ^ (j << 15 | j >>> 17) * 5 ^ i26) * 3 ^ i11 ^ i23 & (i20 ^ 0xFFFFFFFF) ^ i46;
/* 404:    */       
/* 405:460 */       i14 = (i14 << 1 | i14 >>> 31) ^ 0xFFFFFFFF ^ k;
/* 406:461 */       m = (m ^ (k << 15 | k >>> 17) * 5 ^ i25) * 3 ^ i12 ^ i8 & (i21 ^ 0xFFFFFFFF) ^ i47;
/* 407:    */       
/* 408:463 */       i15 = (i15 << 1 | i15 >>> 31) ^ 0xFFFFFFFF ^ m;
/* 409:464 */       n = (n ^ (m << 15 | m >>> 17) * 5 ^ i24) * 3 ^ i13 ^ i9 & (i22 ^ 0xFFFFFFFF) ^ i48;
/* 410:    */       
/* 411:466 */       i16 = (i16 << 1 | i16 >>> 31) ^ 0xFFFFFFFF ^ n;
/* 412:467 */       i1 = (i1 ^ (n << 15 | n >>> 17) * 5 ^ i39) * 3 ^ i14 ^ i10 & (i23 ^ 0xFFFFFFFF) ^ i49;
/* 413:    */       
/* 414:469 */       i17 = (i17 << 1 | i17 >>> 31) ^ 0xFFFFFFFF ^ i1;
/* 415:470 */       i2 = (i2 ^ (i1 << 15 | i1 >>> 17) * 5 ^ i38) * 3 ^ i15 ^ i11 & (i8 ^ 0xFFFFFFFF) ^ i50;
/* 416:    */       
/* 417:472 */       i18 = (i18 << 1 | i18 >>> 31) ^ 0xFFFFFFFF ^ i2;
/* 418:473 */       i3 = (i3 ^ (i2 << 15 | i2 >>> 17) * 5 ^ i37) * 3 ^ i16 ^ i12 & (i9 ^ 0xFFFFFFFF) ^ i51;
/* 419:    */       
/* 420:475 */       i19 = (i19 << 1 | i19 >>> 31) ^ 0xFFFFFFFF ^ i3;
/* 421:476 */       i4 = (i4 ^ (i3 << 15 | i3 >>> 17) * 5 ^ i36) * 3 ^ i17 ^ i13 & (i10 ^ 0xFFFFFFFF) ^ i52;
/* 422:    */       
/* 423:478 */       i20 = (i20 << 1 | i20 >>> 31) ^ 0xFFFFFFFF ^ i4;
/* 424:479 */       i5 = (i5 ^ (i4 << 15 | i4 >>> 17) * 5 ^ i35) * 3 ^ i18 ^ i14 & (i11 ^ 0xFFFFFFFF) ^ i53;
/* 425:    */       
/* 426:481 */       i21 = (i21 << 1 | i21 >>> 31) ^ 0xFFFFFFFF ^ i5;
/* 427:482 */       i6 = (i6 ^ (i5 << 15 | i5 >>> 17) * 5 ^ i34) * 3 ^ i19 ^ i15 & (i12 ^ 0xFFFFFFFF) ^ i54;
/* 428:    */       
/* 429:484 */       i22 = (i22 << 1 | i22 >>> 31) ^ 0xFFFFFFFF ^ i6;
/* 430:485 */       i7 = (i7 ^ (i6 << 15 | i6 >>> 17) * 5 ^ i33) * 3 ^ i20 ^ i16 & (i13 ^ 0xFFFFFFFF) ^ i55;
/* 431:    */       
/* 432:487 */       i23 = (i23 << 1 | i23 >>> 31) ^ 0xFFFFFFFF ^ i7;
/* 433:    */       
/* 434:489 */       i7 += i30 + i34 + i38;
/* 435:490 */       i6 += i29 + i33 + i37;
/* 436:491 */       i5 += i28 + i32 + i36;
/* 437:492 */       i4 += i27 + i31 + i35;
/* 438:493 */       i3 += i26 + i30 + i34;
/* 439:494 */       i2 += i25 + i29 + i33;
/* 440:495 */       i1 += i24 + i28 + i32;
/* 441:496 */       n += i39 + i27 + i31;
/* 442:497 */       m += i38 + i26 + i30;
/* 443:498 */       k += i37 + i25 + i29;
/* 444:499 */       j += i36 + i24 + i28;
/* 445:500 */       i += i35 + i39 + i27;
/* 446:    */       
/* 447:    */ 
/* 448:503 */       i56 = i8;i8 = i24 - i40;i24 = i56;
/* 449:504 */       i56 = i9;i9 = i25 - i41;i25 = i56;
/* 450:505 */       i56 = i10;i10 = i26 - i42;i26 = i56;
/* 451:506 */       i56 = i11;i11 = i27 - i43;i27 = i56;
/* 452:507 */       i56 = i12;i12 = i28 - i44;i28 = i56;
/* 453:508 */       i56 = i13;i13 = i29 - i45;i29 = i56;
/* 454:509 */       i56 = i14;i14 = i30 - i46;i30 = i56;
/* 455:510 */       i56 = i15;i15 = i31 - i47;i31 = i56;
/* 456:511 */       i56 = i16;i16 = i32 - i48;i32 = i56;
/* 457:512 */       i56 = i17;i17 = i33 - i49;i33 = i56;
/* 458:513 */       i56 = i18;i18 = i34 - i50;i34 = i56;
/* 459:514 */       i56 = i19;i19 = i35 - i51;i35 = i56;
/* 460:515 */       i56 = i20;i20 = i36 - i52;i36 = i56;
/* 461:516 */       i56 = i21;i21 = i37 - i53;i37 = i56;
/* 462:517 */       i56 = i22;i22 = i38 - i54;i38 = i56;
/* 463:518 */       i56 = i23;i23 = i39 - i55;
/* 464:    */     }
/* 465:521 */     this.state[0] = i;
/* 466:522 */     this.state[1] = j;
/* 467:523 */     this.state[2] = k;
/* 468:524 */     this.state[3] = m;
/* 469:525 */     this.state[4] = n;
/* 470:526 */     this.state[5] = i1;
/* 471:527 */     this.state[6] = i2;
/* 472:528 */     this.state[7] = i3;
/* 473:529 */     this.state[8] = i4;
/* 474:530 */     this.state[9] = i5;
/* 475:531 */     this.state[10] = i6;
/* 476:532 */     this.state[11] = i7;
/* 477:    */     
/* 478:534 */     this.state[12] = i8;
/* 479:535 */     this.state[13] = i9;
/* 480:536 */     this.state[14] = i10;
/* 481:537 */     this.state[15] = i11;
/* 482:538 */     this.state[16] = i12;
/* 483:539 */     this.state[17] = i13;
/* 484:540 */     this.state[18] = i14;
/* 485:541 */     this.state[19] = i15;
/* 486:542 */     this.state[20] = i16;
/* 487:543 */     this.state[21] = i17;
/* 488:544 */     this.state[22] = i18;
/* 489:545 */     this.state[23] = i19;
/* 490:546 */     this.state[24] = i20;
/* 491:547 */     this.state[25] = i21;
/* 492:548 */     this.state[26] = i22;
/* 493:549 */     this.state[27] = i23;
/* 494:    */     
/* 495:551 */     this.state[28] = i24;
/* 496:552 */     this.state[29] = i25;
/* 497:553 */     this.state[30] = i26;
/* 498:554 */     this.state[31] = i27;
/* 499:555 */     this.state[32] = i28;
/* 500:556 */     this.state[33] = i29;
/* 501:557 */     this.state[34] = i30;
/* 502:558 */     this.state[35] = i31;
/* 503:559 */     this.state[36] = i32;
/* 504:560 */     this.state[37] = i33;
/* 505:561 */     this.state[38] = i34;
/* 506:562 */     this.state[39] = i35;
/* 507:563 */     this.state[40] = i36;
/* 508:564 */     this.state[41] = i37;
/* 509:565 */     this.state[42] = i38;
/* 510:566 */     this.state[43] = i39;
/* 511:    */   }
/* 512:    */   
/* 513:    */   public String toString()
/* 514:    */   {
/* 515:572 */     return "Shabal-" + (getDigestLength() << 3);
/* 516:    */   }
/* 517:    */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     fr.cryptohash.ShabalGeneric
 * JD-Core Version:    0.7.1
 */