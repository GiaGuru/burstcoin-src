/*   1:    */ package fr.cryptohash;
/*   2:    */ 
/*   3:    */ abstract class SIMDSmallCore
/*   4:    */   extends DigestEngine
/*   5:    */ {
/*   6:    */   private int[] state;
/*   7:    */   private int[] q;
/*   8:    */   private int[] w;
/*   9:    */   private int[] tmpState;
/*  10:    */   private int[] tA;
/*  11:    */   
/*  12:    */   public int getBlockLength()
/*  13:    */   {
/*  14: 54 */     return 64;
/*  15:    */   }
/*  16:    */   
/*  17:    */   protected Digest copyState(SIMDSmallCore paramSIMDSmallCore)
/*  18:    */   {
/*  19: 60 */     System.arraycopy(this.state, 0, paramSIMDSmallCore.state, 0, 16);
/*  20: 61 */     return super.copyState(paramSIMDSmallCore);
/*  21:    */   }
/*  22:    */   
/*  23:    */   protected void engineReset()
/*  24:    */   {
/*  25: 67 */     int[] arrayOfInt = getInitVal();
/*  26: 68 */     System.arraycopy(arrayOfInt, 0, this.state, 0, 16);
/*  27:    */   }
/*  28:    */   
/*  29:    */   abstract int[] getInitVal();
/*  30:    */   
/*  31:    */   protected void doPadding(byte[] paramArrayOfByte, int paramInt)
/*  32:    */   {
/*  33: 81 */     int i = flush();
/*  34: 82 */     byte[] arrayOfByte = getBlockBuffer();
/*  35: 83 */     if (i != 0)
/*  36:    */     {
/*  37: 84 */       for (int j = i; j < 64; j++) {
/*  38: 85 */         arrayOfByte[j] = 0;
/*  39:    */       }
/*  40: 86 */       compress(arrayOfByte, false);
/*  41:    */     }
/*  42: 88 */     long l = (getBlockCount() << 9) + (i << 3);
/*  43: 89 */     encodeLEInt((int)l, arrayOfByte, 0);
/*  44: 90 */     encodeLEInt((int)(l >> 32), arrayOfByte, 4);
/*  45: 91 */     for (int k = 8; k < 64; k++) {
/*  46: 92 */       arrayOfByte[k] = 0;
/*  47:    */     }
/*  48: 93 */     compress(arrayOfByte, true);
/*  49: 94 */     k = getDigestLength() >>> 2;
/*  50: 95 */     for (int m = 0; m < k; m++) {
/*  51: 96 */       encodeLEInt(this.state[m], paramArrayOfByte, paramInt + (m << 2));
/*  52:    */     }
/*  53:    */   }
/*  54:    */   
/*  55:    */   protected void doInit()
/*  56:    */   {
/*  57:102 */     this.state = new int[16];
/*  58:103 */     this.q = new int[''];
/*  59:104 */     this.w = new int[32];
/*  60:105 */     this.tmpState = new int[16];
/*  61:106 */     this.tA = new int[4];
/*  62:107 */     engineReset();
/*  63:    */   }
/*  64:    */   
/*  65:    */   private static final void encodeLEInt(int paramInt1, byte[] paramArrayOfByte, int paramInt2)
/*  66:    */   {
/*  67:121 */     paramArrayOfByte[(paramInt2 + 0)] = ((byte)paramInt1);
/*  68:122 */     paramArrayOfByte[(paramInt2 + 1)] = ((byte)(paramInt1 >>> 8));
/*  69:123 */     paramArrayOfByte[(paramInt2 + 2)] = ((byte)(paramInt1 >>> 16));
/*  70:124 */     paramArrayOfByte[(paramInt2 + 3)] = ((byte)(paramInt1 >>> 24));
/*  71:    */   }
/*  72:    */   
/*  73:    */   private static final int decodeLEInt(byte[] paramArrayOfByte, int paramInt)
/*  74:    */   {
/*  75:137 */     return (paramArrayOfByte[(paramInt + 3)] & 0xFF) << 24 | (paramArrayOfByte[(paramInt + 2)] & 0xFF) << 16 | (paramArrayOfByte[(paramInt + 1)] & 0xFF) << 8 | paramArrayOfByte[paramInt] & 0xFF;
/*  76:    */   }
/*  77:    */   
/*  78:    */   private static int circularLeft(int paramInt1, int paramInt2)
/*  79:    */   {
/*  80:154 */     return paramInt1 >>> 32 - paramInt2 | paramInt1 << paramInt2;
/*  81:    */   }
/*  82:    */   
/*  83:    */   protected void processBlock(byte[] paramArrayOfByte)
/*  84:    */   {
/*  85:160 */     compress(paramArrayOfByte, false);
/*  86:    */   }
/*  87:    */   
/*  88:163 */   private static final int[] alphaTab = { 1, 41, 139, 45, 46, 87, 226, 14, 60, 147, 116, 130, 190, 80, 196, 69, 2, 82, 21, 90, 92, 174, 195, 28, 120, 37, 232, 3, 123, 160, 135, 138, 4, 164, 42, 180, 184, 91, 133, 56, 240, 74, 207, 6, 246, 63, 13, 19, 8, 71, 84, 103, 111, 182, 9, 112, 223, 148, 157, 12, 235, 126, 26, 38, 16, 142, 168, 206, 222, 107, 18, 224, 189, 39, 57, 24, 213, 252, 52, 76, 32, 27, 79, 155, 187, 214, 36, 191, 121, 78, 114, 48, 169, 247, 104, 152, 64, 54, 158, 53, 117, 171, 72, 125, 242, 156, 228, 96, 81, 237, 208, 47, 128, 108, 59, 106, 234, 85, 144, 250, 227, 55, 199, 192, 162, 217, 159, 94, 256, 216, 118, 212, 211, 170, 31, 243, 197, 110, 141, 127, 67, 177, 61, 188, 255, 175, 236, 167, 165, 83, 62, 229, 137, 220, 25, 254, 134, 97, 122, 119, 253, 93, 215, 77, 73, 166, 124, 201, 17, 183, 50, 251, 11, 194, 244, 238, 249, 186, 173, 154, 146, 75, 248, 145, 34, 109, 100, 245, 22, 131, 231, 219, 241, 115, 89, 51, 35, 150, 239, 33, 68, 218, 200, 233, 44, 5, 205, 181, 225, 230, 178, 102, 70, 43, 221, 66, 136, 179, 143, 209, 88, 10, 153, 105, 193, 203, 99, 204, 140, 86, 185, 132, 15, 101, 29, 161, 176, 20, 49, 210, 129, 149, 198, 151, 23, 172, 113, 7, 30, 202, 58, 65, 95, 40, 98, 163 };
/*  89:188 */   private static final int[] yoffN = { 1, 98, 95, 58, 30, 113, 23, 198, 129, 49, 176, 29, 15, 185, 140, 99, 193, 153, 88, 143, 136, 221, 70, 178, 225, 205, 44, 200, 68, 239, 35, 89, 241, 231, 22, 100, 34, 248, 146, 173, 249, 244, 11, 50, 17, 124, 73, 215, 253, 122, 134, 25, 137, 62, 165, 236, 255, 61, 67, 141, 197, 31, 211, 118, 256, 159, 162, 199, 227, 144, 234, 59, 128, 208, 81, 228, 242, 72, 117, 158, 64, 104, 169, 114, 121, 36, 187, 79, 32, 52, 213, 57, 189, 18, 222, 168, 16, 26, 235, 157, 223, 9, 111, 84, 8, 13, 246, 207, 240, 133, 184, 42, 4, 135, 123, 232, 120, 195, 92, 21, 2, 196, 190, 116, 60, 226, 46, 139 };
/*  90:202 */   private static final int[] yoffF = { 2, 156, 118, 107, 45, 212, 111, 162, 97, 249, 211, 3, 49, 101, 151, 223, 189, 178, 253, 204, 76, 82, 232, 65, 96, 176, 161, 47, 189, 61, 248, 107, 0, 131, 133, 113, 17, 33, 12, 111, 251, 103, 57, 148, 47, 65, 249, 143, 189, 8, 204, 230, 205, 151, 187, 227, 247, 111, 140, 6, 77, 10, 21, 149, 255, 101, 139, 150, 212, 45, 146, 95, 160, 8, 46, 254, 208, 156, 106, 34, 68, 79, 4, 53, 181, 175, 25, 192, 161, 81, 96, 210, 68, 196, 9, 150, 0, 126, 124, 144, 240, 224, 245, 146, 6, 154, 200, 109, 210, 192, 8, 114, 68, 249, 53, 27, 52, 106, 70, 30, 10, 146, 117, 251, 180, 247, 236, 108 };
/*  91:    */   
/*  92:    */   private final void fft32(byte[] paramArrayOfByte, int paramInt1, int paramInt2, int paramInt3)
/*  93:    */   {
/*  94:218 */     int i = paramInt2 << 1;
/*  95:    */     
/*  96:    */ 
/*  97:    */ 
/*  98:    */ 
/*  99:223 */     int i13 = paramArrayOfByte[paramInt1] & 0xFF;
/* 100:224 */     int i14 = paramArrayOfByte[(paramInt1 + 2 * i)] & 0xFF;
/* 101:225 */     int i15 = paramArrayOfByte[(paramInt1 + 4 * i)] & 0xFF;
/* 102:226 */     int i16 = paramArrayOfByte[(paramInt1 + 6 * i)] & 0xFF;
/* 103:227 */     int i17 = i13 + i15;
/* 104:228 */     int i18 = i13 + (i15 << 4);
/* 105:229 */     int i19 = i13 - i15;
/* 106:230 */     int i20 = i13 - (i15 << 4);
/* 107:231 */     int i21 = i14 + i16;
/* 108:232 */     int i22 = ((i14 << 2) + (i16 << 6) & 0xFF) - ((i14 << 2) + (i16 << 6) >> 8);
/* 109:    */     
/* 110:234 */     int i23 = (i14 << 4) - (i16 << 4);
/* 111:235 */     int i24 = ((i14 << 6) + (i16 << 2) & 0xFF) - ((i14 << 6) + (i16 << 2) >> 8);
/* 112:    */     
/* 113:237 */     int j = i17 + i21;
/* 114:238 */     int k = i18 + i22;
/* 115:239 */     int m = i19 + i23;
/* 116:240 */     int n = i20 + i24;
/* 117:241 */     int i1 = i17 - i21;
/* 118:242 */     int i2 = i18 - i22;
/* 119:243 */     int i3 = i19 - i23;
/* 120:244 */     int i4 = i20 - i24;
/* 121:    */     
/* 122:    */ 
/* 123:247 */     i13 = paramArrayOfByte[(paramInt1 + i)] & 0xFF;
/* 124:248 */     i14 = paramArrayOfByte[(paramInt1 + 3 * i)] & 0xFF;
/* 125:249 */     i15 = paramArrayOfByte[(paramInt1 + 5 * i)] & 0xFF;
/* 126:250 */     i16 = paramArrayOfByte[(paramInt1 + 7 * i)] & 0xFF;
/* 127:251 */     i17 = i13 + i15;
/* 128:252 */     i18 = i13 + (i15 << 4);
/* 129:253 */     i19 = i13 - i15;
/* 130:254 */     i20 = i13 - (i15 << 4);
/* 131:255 */     i21 = i14 + i16;
/* 132:256 */     i22 = ((i14 << 2) + (i16 << 6) & 0xFF) - ((i14 << 2) + (i16 << 6) >> 8);
/* 133:    */     
/* 134:258 */     i23 = (i14 << 4) - (i16 << 4);
/* 135:259 */     i24 = ((i14 << 6) + (i16 << 2) & 0xFF) - ((i14 << 6) + (i16 << 2) >> 8);
/* 136:    */     
/* 137:261 */     int i5 = i17 + i21;
/* 138:262 */     int i6 = i18 + i22;
/* 139:263 */     int i7 = i19 + i23;
/* 140:264 */     int i8 = i20 + i24;
/* 141:265 */     int i9 = i17 - i21;
/* 142:266 */     int i10 = i18 - i22;
/* 143:267 */     int i11 = i19 - i23;
/* 144:268 */     int i12 = i20 - i24;
/* 145:    */     
/* 146:270 */     this.q[(paramInt3 + 0)] = (j + i5);
/* 147:271 */     this.q[(paramInt3 + 1)] = (k + (i6 << 1));
/* 148:272 */     this.q[(paramInt3 + 2)] = (m + (i7 << 2));
/* 149:273 */     this.q[(paramInt3 + 3)] = (n + (i8 << 3));
/* 150:274 */     this.q[(paramInt3 + 4)] = (i1 + (i9 << 4));
/* 151:275 */     this.q[(paramInt3 + 5)] = (i2 + (i10 << 5));
/* 152:276 */     this.q[(paramInt3 + 6)] = (i3 + (i11 << 6));
/* 153:277 */     this.q[(paramInt3 + 7)] = (i4 + (i12 << 7));
/* 154:278 */     this.q[(paramInt3 + 8)] = (j - i5);
/* 155:279 */     this.q[(paramInt3 + 9)] = (k - (i6 << 1));
/* 156:280 */     this.q[(paramInt3 + 10)] = (m - (i7 << 2));
/* 157:281 */     this.q[(paramInt3 + 11)] = (n - (i8 << 3));
/* 158:282 */     this.q[(paramInt3 + 12)] = (i1 - (i9 << 4));
/* 159:283 */     this.q[(paramInt3 + 13)] = (i2 - (i10 << 5));
/* 160:284 */     this.q[(paramInt3 + 14)] = (i3 - (i11 << 6));
/* 161:285 */     this.q[(paramInt3 + 15)] = (i4 - (i12 << 7));
/* 162:    */     
/* 163:    */ 
/* 164:    */ 
/* 165:    */ 
/* 166:    */ 
/* 167:291 */     i13 = paramArrayOfByte[(paramInt1 + paramInt2)] & 0xFF;
/* 168:292 */     i14 = paramArrayOfByte[(paramInt1 + paramInt2 + 2 * i)] & 0xFF;
/* 169:293 */     i15 = paramArrayOfByte[(paramInt1 + paramInt2 + 4 * i)] & 0xFF;
/* 170:294 */     i16 = paramArrayOfByte[(paramInt1 + paramInt2 + 6 * i)] & 0xFF;
/* 171:295 */     i17 = i13 + i15;
/* 172:296 */     i18 = i13 + (i15 << 4);
/* 173:297 */     i19 = i13 - i15;
/* 174:298 */     i20 = i13 - (i15 << 4);
/* 175:299 */     i21 = i14 + i16;
/* 176:300 */     i22 = ((i14 << 2) + (i16 << 6) & 0xFF) - ((i14 << 2) + (i16 << 6) >> 8);
/* 177:    */     
/* 178:302 */     i23 = (i14 << 4) - (i16 << 4);
/* 179:303 */     i24 = ((i14 << 6) + (i16 << 2) & 0xFF) - ((i14 << 6) + (i16 << 2) >> 8);
/* 180:    */     
/* 181:305 */     j = i17 + i21;
/* 182:306 */     k = i18 + i22;
/* 183:307 */     m = i19 + i23;
/* 184:308 */     n = i20 + i24;
/* 185:309 */     i1 = i17 - i21;
/* 186:310 */     i2 = i18 - i22;
/* 187:311 */     i3 = i19 - i23;
/* 188:312 */     i4 = i20 - i24;
/* 189:    */     
/* 190:    */ 
/* 191:315 */     i13 = paramArrayOfByte[(paramInt1 + paramInt2 + i)] & 0xFF;
/* 192:316 */     i14 = paramArrayOfByte[(paramInt1 + paramInt2 + 3 * i)] & 0xFF;
/* 193:317 */     i15 = paramArrayOfByte[(paramInt1 + paramInt2 + 5 * i)] & 0xFF;
/* 194:318 */     i16 = paramArrayOfByte[(paramInt1 + paramInt2 + 7 * i)] & 0xFF;
/* 195:319 */     i17 = i13 + i15;
/* 196:320 */     i18 = i13 + (i15 << 4);
/* 197:321 */     i19 = i13 - i15;
/* 198:322 */     i20 = i13 - (i15 << 4);
/* 199:323 */     i21 = i14 + i16;
/* 200:324 */     i22 = ((i14 << 2) + (i16 << 6) & 0xFF) - ((i14 << 2) + (i16 << 6) >> 8);
/* 201:    */     
/* 202:326 */     i23 = (i14 << 4) - (i16 << 4);
/* 203:327 */     i24 = ((i14 << 6) + (i16 << 2) & 0xFF) - ((i14 << 6) + (i16 << 2) >> 8);
/* 204:    */     
/* 205:329 */     i5 = i17 + i21;
/* 206:330 */     i6 = i18 + i22;
/* 207:331 */     i7 = i19 + i23;
/* 208:332 */     i8 = i20 + i24;
/* 209:333 */     i9 = i17 - i21;
/* 210:334 */     i10 = i18 - i22;
/* 211:335 */     i11 = i19 - i23;
/* 212:336 */     i12 = i20 - i24;
/* 213:    */     
/* 214:338 */     this.q[(paramInt3 + 16 + 0)] = (j + i5);
/* 215:339 */     this.q[(paramInt3 + 16 + 1)] = (k + (i6 << 1));
/* 216:340 */     this.q[(paramInt3 + 16 + 2)] = (m + (i7 << 2));
/* 217:341 */     this.q[(paramInt3 + 16 + 3)] = (n + (i8 << 3));
/* 218:342 */     this.q[(paramInt3 + 16 + 4)] = (i1 + (i9 << 4));
/* 219:343 */     this.q[(paramInt3 + 16 + 5)] = (i2 + (i10 << 5));
/* 220:344 */     this.q[(paramInt3 + 16 + 6)] = (i3 + (i11 << 6));
/* 221:345 */     this.q[(paramInt3 + 16 + 7)] = (i4 + (i12 << 7));
/* 222:346 */     this.q[(paramInt3 + 16 + 8)] = (j - i5);
/* 223:347 */     this.q[(paramInt3 + 16 + 9)] = (k - (i6 << 1));
/* 224:348 */     this.q[(paramInt3 + 16 + 10)] = (m - (i7 << 2));
/* 225:349 */     this.q[(paramInt3 + 16 + 11)] = (n - (i8 << 3));
/* 226:350 */     this.q[(paramInt3 + 16 + 12)] = (i1 - (i9 << 4));
/* 227:351 */     this.q[(paramInt3 + 16 + 13)] = (i2 - (i10 << 5));
/* 228:352 */     this.q[(paramInt3 + 16 + 14)] = (i3 - (i11 << 6));
/* 229:353 */     this.q[(paramInt3 + 16 + 15)] = (i4 - (i12 << 7));
/* 230:    */     
/* 231:355 */     j = this.q[paramInt3];
/* 232:356 */     k = this.q[(paramInt3 + 16)];
/* 233:357 */     this.q[paramInt3] = (j + k);
/* 234:358 */     this.q[(paramInt3 + 16)] = (j - k);
/* 235:359 */     m = 0;
/* 236:359 */     for (n = 0; m < 16; n += 32)
/* 237:    */     {
/* 238:361 */       if (m != 0)
/* 239:    */       {
/* 240:362 */         j = this.q[(paramInt3 + m + 0)];
/* 241:363 */         k = this.q[(paramInt3 + m + 0 + 16)];
/* 242:364 */         i1 = (k * alphaTab[(n + 0)] & 0xFFFF) + (k * alphaTab[(n + 0)] >> 16);
/* 243:    */         
/* 244:366 */         this.q[(paramInt3 + m + 0)] = (j + i1);
/* 245:367 */         this.q[(paramInt3 + m + 0 + 16)] = (j - i1);
/* 246:    */       }
/* 247:369 */       j = this.q[(paramInt3 + m + 1)];
/* 248:370 */       k = this.q[(paramInt3 + m + 1 + 16)];
/* 249:371 */       i1 = (k * alphaTab[(n + 8)] & 0xFFFF) + (k * alphaTab[(n + 8)] >> 16);
/* 250:    */       
/* 251:373 */       this.q[(paramInt3 + m + 1)] = (j + i1);
/* 252:374 */       this.q[(paramInt3 + m + 1 + 16)] = (j - i1);
/* 253:375 */       j = this.q[(paramInt3 + m + 2)];
/* 254:376 */       k = this.q[(paramInt3 + m + 2 + 16)];
/* 255:377 */       i1 = (k * alphaTab[(n + 16)] & 0xFFFF) + (k * alphaTab[(n + 16)] >> 16);
/* 256:    */       
/* 257:379 */       this.q[(paramInt3 + m + 2)] = (j + i1);
/* 258:380 */       this.q[(paramInt3 + m + 2 + 16)] = (j - i1);
/* 259:381 */       j = this.q[(paramInt3 + m + 3)];
/* 260:382 */       k = this.q[(paramInt3 + m + 3 + 16)];
/* 261:383 */       i1 = (k * alphaTab[(n + 24)] & 0xFFFF) + (k * alphaTab[(n + 24)] >> 16);
/* 262:    */       
/* 263:385 */       this.q[(paramInt3 + m + 3)] = (j + i1);
/* 264:386 */       this.q[(paramInt3 + m + 3 + 16)] = (j - i1);m += 4;
/* 265:    */     }
/* 266:    */   }
/* 267:    */   
/* 268:390 */   private static final int[] pp4k = { 1, 2, 3, 1, 2, 3, 1, 2, 3, 1, 2 };
/* 269:394 */   private static final int[] wsp = { 32, 48, 0, 16, 56, 40, 24, 8, 120, 88, 96, 64, 72, 104, 80, 112, 136, 144, 184, 160, 176, 168, 128, 152, 240, 192, 200, 248, 216, 232, 224, 208 };
/* 270:    */   
/* 271:    */   private final void oneRound(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
/* 272:    */   {
/* 273:408 */     this.tA[0] = circularLeft(this.state[0], paramInt2);
/* 274:409 */     this.tA[1] = circularLeft(this.state[1], paramInt2);
/* 275:410 */     this.tA[2] = circularLeft(this.state[2], paramInt2);
/* 276:411 */     this.tA[3] = circularLeft(this.state[3], paramInt2);
/* 277:412 */     int i = this.state[12] + this.w[0] + ((this.state[4] ^ this.state[8]) & this.state[0] ^ this.state[8]);
/* 278:    */     
/* 279:414 */     this.state[0] = (circularLeft(i, paramInt3) + this.tA[(pp4k[(paramInt1 + 0)] ^ 0x0)]);
/* 280:415 */     this.state[12] = this.state[8];
/* 281:416 */     this.state[8] = this.state[4];
/* 282:417 */     this.state[4] = this.tA[0];
/* 283:418 */     i = this.state[13] + this.w[1] + ((this.state[5] ^ this.state[9]) & this.state[1] ^ this.state[9]);
/* 284:    */     
/* 285:420 */     this.state[1] = (circularLeft(i, paramInt3) + this.tA[(pp4k[(paramInt1 + 0)] ^ 0x1)]);
/* 286:421 */     this.state[13] = this.state[9];
/* 287:422 */     this.state[9] = this.state[5];
/* 288:423 */     this.state[5] = this.tA[1];
/* 289:424 */     i = this.state[14] + this.w[2] + ((this.state[6] ^ this.state[10]) & this.state[2] ^ this.state[10]);
/* 290:    */     
/* 291:426 */     this.state[2] = (circularLeft(i, paramInt3) + this.tA[(pp4k[(paramInt1 + 0)] ^ 0x2)]);
/* 292:427 */     this.state[14] = this.state[10];
/* 293:428 */     this.state[10] = this.state[6];
/* 294:429 */     this.state[6] = this.tA[2];
/* 295:430 */     i = this.state[15] + this.w[3] + ((this.state[7] ^ this.state[11]) & this.state[3] ^ this.state[11]);
/* 296:    */     
/* 297:432 */     this.state[3] = (circularLeft(i, paramInt3) + this.tA[(pp4k[(paramInt1 + 0)] ^ 0x3)]);
/* 298:433 */     this.state[15] = this.state[11];
/* 299:434 */     this.state[11] = this.state[7];
/* 300:435 */     this.state[7] = this.tA[3];
/* 301:436 */     this.tA[0] = circularLeft(this.state[0], paramInt3);
/* 302:437 */     this.tA[1] = circularLeft(this.state[1], paramInt3);
/* 303:438 */     this.tA[2] = circularLeft(this.state[2], paramInt3);
/* 304:439 */     this.tA[3] = circularLeft(this.state[3], paramInt3);
/* 305:440 */     i = this.state[12] + this.w[4] + ((this.state[4] ^ this.state[8]) & this.state[0] ^ this.state[8]);
/* 306:    */     
/* 307:442 */     this.state[0] = (circularLeft(i, paramInt4) + this.tA[(pp4k[(paramInt1 + 1)] ^ 0x0)]);
/* 308:443 */     this.state[12] = this.state[8];
/* 309:444 */     this.state[8] = this.state[4];
/* 310:445 */     this.state[4] = this.tA[0];
/* 311:446 */     i = this.state[13] + this.w[5] + ((this.state[5] ^ this.state[9]) & this.state[1] ^ this.state[9]);
/* 312:    */     
/* 313:448 */     this.state[1] = (circularLeft(i, paramInt4) + this.tA[(pp4k[(paramInt1 + 1)] ^ 0x1)]);
/* 314:449 */     this.state[13] = this.state[9];
/* 315:450 */     this.state[9] = this.state[5];
/* 316:451 */     this.state[5] = this.tA[1];
/* 317:452 */     i = this.state[14] + this.w[6] + ((this.state[6] ^ this.state[10]) & this.state[2] ^ this.state[10]);
/* 318:    */     
/* 319:454 */     this.state[2] = (circularLeft(i, paramInt4) + this.tA[(pp4k[(paramInt1 + 1)] ^ 0x2)]);
/* 320:455 */     this.state[14] = this.state[10];
/* 321:456 */     this.state[10] = this.state[6];
/* 322:457 */     this.state[6] = this.tA[2];
/* 323:458 */     i = this.state[15] + this.w[7] + ((this.state[7] ^ this.state[11]) & this.state[3] ^ this.state[11]);
/* 324:    */     
/* 325:460 */     this.state[3] = (circularLeft(i, paramInt4) + this.tA[(pp4k[(paramInt1 + 1)] ^ 0x3)]);
/* 326:461 */     this.state[15] = this.state[11];
/* 327:462 */     this.state[11] = this.state[7];
/* 328:463 */     this.state[7] = this.tA[3];
/* 329:464 */     this.tA[0] = circularLeft(this.state[0], paramInt4);
/* 330:465 */     this.tA[1] = circularLeft(this.state[1], paramInt4);
/* 331:466 */     this.tA[2] = circularLeft(this.state[2], paramInt4);
/* 332:467 */     this.tA[3] = circularLeft(this.state[3], paramInt4);
/* 333:468 */     i = this.state[12] + this.w[8] + ((this.state[4] ^ this.state[8]) & this.state[0] ^ this.state[8]);
/* 334:    */     
/* 335:470 */     this.state[0] = (circularLeft(i, paramInt5) + this.tA[(pp4k[(paramInt1 + 2)] ^ 0x0)]);
/* 336:471 */     this.state[12] = this.state[8];
/* 337:472 */     this.state[8] = this.state[4];
/* 338:473 */     this.state[4] = this.tA[0];
/* 339:474 */     i = this.state[13] + this.w[9] + ((this.state[5] ^ this.state[9]) & this.state[1] ^ this.state[9]);
/* 340:    */     
/* 341:476 */     this.state[1] = (circularLeft(i, paramInt5) + this.tA[(pp4k[(paramInt1 + 2)] ^ 0x1)]);
/* 342:477 */     this.state[13] = this.state[9];
/* 343:478 */     this.state[9] = this.state[5];
/* 344:479 */     this.state[5] = this.tA[1];
/* 345:480 */     i = this.state[14] + this.w[10] + ((this.state[6] ^ this.state[10]) & this.state[2] ^ this.state[10]);
/* 346:    */     
/* 347:482 */     this.state[2] = (circularLeft(i, paramInt5) + this.tA[(pp4k[(paramInt1 + 2)] ^ 0x2)]);
/* 348:483 */     this.state[14] = this.state[10];
/* 349:484 */     this.state[10] = this.state[6];
/* 350:485 */     this.state[6] = this.tA[2];
/* 351:486 */     i = this.state[15] + this.w[11] + ((this.state[7] ^ this.state[11]) & this.state[3] ^ this.state[11]);
/* 352:    */     
/* 353:488 */     this.state[3] = (circularLeft(i, paramInt5) + this.tA[(pp4k[(paramInt1 + 2)] ^ 0x3)]);
/* 354:489 */     this.state[15] = this.state[11];
/* 355:490 */     this.state[11] = this.state[7];
/* 356:491 */     this.state[7] = this.tA[3];
/* 357:492 */     this.tA[0] = circularLeft(this.state[0], paramInt5);
/* 358:493 */     this.tA[1] = circularLeft(this.state[1], paramInt5);
/* 359:494 */     this.tA[2] = circularLeft(this.state[2], paramInt5);
/* 360:495 */     this.tA[3] = circularLeft(this.state[3], paramInt5);
/* 361:496 */     i = this.state[12] + this.w[12] + ((this.state[4] ^ this.state[8]) & this.state[0] ^ this.state[8]);
/* 362:    */     
/* 363:498 */     this.state[0] = (circularLeft(i, paramInt2) + this.tA[(pp4k[(paramInt1 + 3)] ^ 0x0)]);
/* 364:499 */     this.state[12] = this.state[8];
/* 365:500 */     this.state[8] = this.state[4];
/* 366:501 */     this.state[4] = this.tA[0];
/* 367:502 */     i = this.state[13] + this.w[13] + ((this.state[5] ^ this.state[9]) & this.state[1] ^ this.state[9]);
/* 368:    */     
/* 369:504 */     this.state[1] = (circularLeft(i, paramInt2) + this.tA[(pp4k[(paramInt1 + 3)] ^ 0x1)]);
/* 370:505 */     this.state[13] = this.state[9];
/* 371:506 */     this.state[9] = this.state[5];
/* 372:507 */     this.state[5] = this.tA[1];
/* 373:508 */     i = this.state[14] + this.w[14] + ((this.state[6] ^ this.state[10]) & this.state[2] ^ this.state[10]);
/* 374:    */     
/* 375:510 */     this.state[2] = (circularLeft(i, paramInt2) + this.tA[(pp4k[(paramInt1 + 3)] ^ 0x2)]);
/* 376:511 */     this.state[14] = this.state[10];
/* 377:512 */     this.state[10] = this.state[6];
/* 378:513 */     this.state[6] = this.tA[2];
/* 379:514 */     i = this.state[15] + this.w[15] + ((this.state[7] ^ this.state[11]) & this.state[3] ^ this.state[11]);
/* 380:    */     
/* 381:516 */     this.state[3] = (circularLeft(i, paramInt2) + this.tA[(pp4k[(paramInt1 + 3)] ^ 0x3)]);
/* 382:517 */     this.state[15] = this.state[11];
/* 383:518 */     this.state[11] = this.state[7];
/* 384:519 */     this.state[7] = this.tA[3];
/* 385:520 */     this.tA[0] = circularLeft(this.state[0], paramInt2);
/* 386:521 */     this.tA[1] = circularLeft(this.state[1], paramInt2);
/* 387:522 */     this.tA[2] = circularLeft(this.state[2], paramInt2);
/* 388:523 */     this.tA[3] = circularLeft(this.state[3], paramInt2);
/* 389:524 */     i = this.state[12] + this.w[16] + (this.state[0] & this.state[4] | (this.state[0] | this.state[4]) & this.state[8]);
/* 390:    */     
/* 391:    */ 
/* 392:527 */     this.state[0] = (circularLeft(i, paramInt3) + this.tA[(pp4k[(paramInt1 + 4)] ^ 0x0)]);
/* 393:528 */     this.state[12] = this.state[8];
/* 394:529 */     this.state[8] = this.state[4];
/* 395:530 */     this.state[4] = this.tA[0];
/* 396:531 */     i = this.state[13] + this.w[17] + (this.state[1] & this.state[5] | (this.state[1] | this.state[5]) & this.state[9]);
/* 397:    */     
/* 398:    */ 
/* 399:534 */     this.state[1] = (circularLeft(i, paramInt3) + this.tA[(pp4k[(paramInt1 + 4)] ^ 0x1)]);
/* 400:535 */     this.state[13] = this.state[9];
/* 401:536 */     this.state[9] = this.state[5];
/* 402:537 */     this.state[5] = this.tA[1];
/* 403:538 */     i = this.state[14] + this.w[18] + (this.state[2] & this.state[6] | (this.state[2] | this.state[6]) & this.state[10]);
/* 404:    */     
/* 405:    */ 
/* 406:541 */     this.state[2] = (circularLeft(i, paramInt3) + this.tA[(pp4k[(paramInt1 + 4)] ^ 0x2)]);
/* 407:542 */     this.state[14] = this.state[10];
/* 408:543 */     this.state[10] = this.state[6];
/* 409:544 */     this.state[6] = this.tA[2];
/* 410:545 */     i = this.state[15] + this.w[19] + (this.state[3] & this.state[7] | (this.state[3] | this.state[7]) & this.state[11]);
/* 411:    */     
/* 412:    */ 
/* 413:548 */     this.state[3] = (circularLeft(i, paramInt3) + this.tA[(pp4k[(paramInt1 + 4)] ^ 0x3)]);
/* 414:549 */     this.state[15] = this.state[11];
/* 415:550 */     this.state[11] = this.state[7];
/* 416:551 */     this.state[7] = this.tA[3];
/* 417:552 */     this.tA[0] = circularLeft(this.state[0], paramInt3);
/* 418:553 */     this.tA[1] = circularLeft(this.state[1], paramInt3);
/* 419:554 */     this.tA[2] = circularLeft(this.state[2], paramInt3);
/* 420:555 */     this.tA[3] = circularLeft(this.state[3], paramInt3);
/* 421:556 */     i = this.state[12] + this.w[20] + (this.state[0] & this.state[4] | (this.state[0] | this.state[4]) & this.state[8]);
/* 422:    */     
/* 423:    */ 
/* 424:559 */     this.state[0] = (circularLeft(i, paramInt4) + this.tA[(pp4k[(paramInt1 + 5)] ^ 0x0)]);
/* 425:560 */     this.state[12] = this.state[8];
/* 426:561 */     this.state[8] = this.state[4];
/* 427:562 */     this.state[4] = this.tA[0];
/* 428:563 */     i = this.state[13] + this.w[21] + (this.state[1] & this.state[5] | (this.state[1] | this.state[5]) & this.state[9]);
/* 429:    */     
/* 430:    */ 
/* 431:566 */     this.state[1] = (circularLeft(i, paramInt4) + this.tA[(pp4k[(paramInt1 + 5)] ^ 0x1)]);
/* 432:567 */     this.state[13] = this.state[9];
/* 433:568 */     this.state[9] = this.state[5];
/* 434:569 */     this.state[5] = this.tA[1];
/* 435:570 */     i = this.state[14] + this.w[22] + (this.state[2] & this.state[6] | (this.state[2] | this.state[6]) & this.state[10]);
/* 436:    */     
/* 437:    */ 
/* 438:573 */     this.state[2] = (circularLeft(i, paramInt4) + this.tA[(pp4k[(paramInt1 + 5)] ^ 0x2)]);
/* 439:574 */     this.state[14] = this.state[10];
/* 440:575 */     this.state[10] = this.state[6];
/* 441:576 */     this.state[6] = this.tA[2];
/* 442:577 */     i = this.state[15] + this.w[23] + (this.state[3] & this.state[7] | (this.state[3] | this.state[7]) & this.state[11]);
/* 443:    */     
/* 444:    */ 
/* 445:580 */     this.state[3] = (circularLeft(i, paramInt4) + this.tA[(pp4k[(paramInt1 + 5)] ^ 0x3)]);
/* 446:581 */     this.state[15] = this.state[11];
/* 447:582 */     this.state[11] = this.state[7];
/* 448:583 */     this.state[7] = this.tA[3];
/* 449:584 */     this.tA[0] = circularLeft(this.state[0], paramInt4);
/* 450:585 */     this.tA[1] = circularLeft(this.state[1], paramInt4);
/* 451:586 */     this.tA[2] = circularLeft(this.state[2], paramInt4);
/* 452:587 */     this.tA[3] = circularLeft(this.state[3], paramInt4);
/* 453:588 */     i = this.state[12] + this.w[24] + (this.state[0] & this.state[4] | (this.state[0] | this.state[4]) & this.state[8]);
/* 454:    */     
/* 455:    */ 
/* 456:591 */     this.state[0] = (circularLeft(i, paramInt5) + this.tA[(pp4k[(paramInt1 + 6)] ^ 0x0)]);
/* 457:592 */     this.state[12] = this.state[8];
/* 458:593 */     this.state[8] = this.state[4];
/* 459:594 */     this.state[4] = this.tA[0];
/* 460:595 */     i = this.state[13] + this.w[25] + (this.state[1] & this.state[5] | (this.state[1] | this.state[5]) & this.state[9]);
/* 461:    */     
/* 462:    */ 
/* 463:598 */     this.state[1] = (circularLeft(i, paramInt5) + this.tA[(pp4k[(paramInt1 + 6)] ^ 0x1)]);
/* 464:599 */     this.state[13] = this.state[9];
/* 465:600 */     this.state[9] = this.state[5];
/* 466:601 */     this.state[5] = this.tA[1];
/* 467:602 */     i = this.state[14] + this.w[26] + (this.state[2] & this.state[6] | (this.state[2] | this.state[6]) & this.state[10]);
/* 468:    */     
/* 469:    */ 
/* 470:605 */     this.state[2] = (circularLeft(i, paramInt5) + this.tA[(pp4k[(paramInt1 + 6)] ^ 0x2)]);
/* 471:606 */     this.state[14] = this.state[10];
/* 472:607 */     this.state[10] = this.state[6];
/* 473:608 */     this.state[6] = this.tA[2];
/* 474:609 */     i = this.state[15] + this.w[27] + (this.state[3] & this.state[7] | (this.state[3] | this.state[7]) & this.state[11]);
/* 475:    */     
/* 476:    */ 
/* 477:612 */     this.state[3] = (circularLeft(i, paramInt5) + this.tA[(pp4k[(paramInt1 + 6)] ^ 0x3)]);
/* 478:613 */     this.state[15] = this.state[11];
/* 479:614 */     this.state[11] = this.state[7];
/* 480:615 */     this.state[7] = this.tA[3];
/* 481:616 */     this.tA[0] = circularLeft(this.state[0], paramInt5);
/* 482:617 */     this.tA[1] = circularLeft(this.state[1], paramInt5);
/* 483:618 */     this.tA[2] = circularLeft(this.state[2], paramInt5);
/* 484:619 */     this.tA[3] = circularLeft(this.state[3], paramInt5);
/* 485:620 */     i = this.state[12] + this.w[28] + (this.state[0] & this.state[4] | (this.state[0] | this.state[4]) & this.state[8]);
/* 486:    */     
/* 487:    */ 
/* 488:623 */     this.state[0] = (circularLeft(i, paramInt2) + this.tA[(pp4k[(paramInt1 + 7)] ^ 0x0)]);
/* 489:624 */     this.state[12] = this.state[8];
/* 490:625 */     this.state[8] = this.state[4];
/* 491:626 */     this.state[4] = this.tA[0];
/* 492:627 */     i = this.state[13] + this.w[29] + (this.state[1] & this.state[5] | (this.state[1] | this.state[5]) & this.state[9]);
/* 493:    */     
/* 494:    */ 
/* 495:630 */     this.state[1] = (circularLeft(i, paramInt2) + this.tA[(pp4k[(paramInt1 + 7)] ^ 0x1)]);
/* 496:631 */     this.state[13] = this.state[9];
/* 497:632 */     this.state[9] = this.state[5];
/* 498:633 */     this.state[5] = this.tA[1];
/* 499:634 */     i = this.state[14] + this.w[30] + (this.state[2] & this.state[6] | (this.state[2] | this.state[6]) & this.state[10]);
/* 500:    */     
/* 501:    */ 
/* 502:637 */     this.state[2] = (circularLeft(i, paramInt2) + this.tA[(pp4k[(paramInt1 + 7)] ^ 0x2)]);
/* 503:638 */     this.state[14] = this.state[10];
/* 504:639 */     this.state[10] = this.state[6];
/* 505:640 */     this.state[6] = this.tA[2];
/* 506:641 */     i = this.state[15] + this.w[31] + (this.state[3] & this.state[7] | (this.state[3] | this.state[7]) & this.state[11]);
/* 507:    */     
/* 508:    */ 
/* 509:644 */     this.state[3] = (circularLeft(i, paramInt2) + this.tA[(pp4k[(paramInt1 + 7)] ^ 0x3)]);
/* 510:645 */     this.state[15] = this.state[11];
/* 511:646 */     this.state[11] = this.state[7];
/* 512:647 */     this.state[7] = this.tA[3];
/* 513:    */   }
/* 514:    */   
/* 515:    */   private final void compress(byte[] paramArrayOfByte, boolean paramBoolean)
/* 516:    */   {
/* 517:652 */     fft32(paramArrayOfByte, 0, 4, 0);
/* 518:653 */     fft32(paramArrayOfByte, 2, 4, 32);
/* 519:654 */     int i = this.q[0];
/* 520:655 */     int j = this.q[32];
/* 521:656 */     this.q[0] = (i + j);
/* 522:657 */     this.q[32] = (i - j);
/* 523:658 */     int k = 0;
/* 524:658 */     for (int m = 0; k < 32; m += 16)
/* 525:    */     {
/* 526:660 */       if (k != 0)
/* 527:    */       {
/* 528:661 */         i = this.q[(0 + k + 0)];
/* 529:662 */         j = this.q[(0 + k + 0 + 32)];
/* 530:663 */         n = (j * alphaTab[(m + 0)] & 0xFFFF) + (j * alphaTab[(m + 0)] >> 16);
/* 531:    */         
/* 532:665 */         this.q[(0 + k + 0)] = (i + n);
/* 533:666 */         this.q[(0 + k + 0 + 32)] = (i - n);
/* 534:    */       }
/* 535:668 */       i = this.q[(0 + k + 1)];
/* 536:669 */       j = this.q[(0 + k + 1 + 32)];
/* 537:670 */       n = (j * alphaTab[(m + 4)] & 0xFFFF) + (j * alphaTab[(m + 4)] >> 16);
/* 538:    */       
/* 539:672 */       this.q[(0 + k + 1)] = (i + n);
/* 540:673 */       this.q[(0 + k + 1 + 32)] = (i - n);
/* 541:674 */       i = this.q[(0 + k + 2)];
/* 542:675 */       j = this.q[(0 + k + 2 + 32)];
/* 543:676 */       n = (j * alphaTab[(m + 8)] & 0xFFFF) + (j * alphaTab[(m + 8)] >> 16);
/* 544:    */       
/* 545:678 */       this.q[(0 + k + 2)] = (i + n);
/* 546:679 */       this.q[(0 + k + 2 + 32)] = (i - n);
/* 547:680 */       i = this.q[(0 + k + 3)];
/* 548:681 */       j = this.q[(0 + k + 3 + 32)];
/* 549:682 */       n = (j * alphaTab[(m + 12)] & 0xFFFF) + (j * alphaTab[(m + 12)] >> 16);
/* 550:    */       
/* 551:684 */       this.q[(0 + k + 3)] = (i + n);
/* 552:685 */       this.q[(0 + k + 3 + 32)] = (i - n);k += 4;
/* 553:    */     }
/* 554:687 */     fft32(paramArrayOfByte, 1, 4, 64);
/* 555:688 */     fft32(paramArrayOfByte, 3, 4, 96);
/* 556:689 */     i = this.q[64];
/* 557:690 */     j = this.q[96];
/* 558:691 */     this.q[64] = (i + j);
/* 559:692 */     this.q[96] = (i - j);
/* 560:693 */     k = 0;
/* 561:693 */     for (m = 0; k < 32; m += 16)
/* 562:    */     {
/* 563:695 */       if (k != 0)
/* 564:    */       {
/* 565:696 */         i = this.q[(64 + k + 0)];
/* 566:697 */         j = this.q[(64 + k + 0 + 32)];
/* 567:698 */         n = (j * alphaTab[(m + 0)] & 0xFFFF) + (j * alphaTab[(m + 0)] >> 16);
/* 568:    */         
/* 569:700 */         this.q[(64 + k + 0)] = (i + n);
/* 570:701 */         this.q[(64 + k + 0 + 32)] = (i - n);
/* 571:    */       }
/* 572:703 */       i = this.q[(64 + k + 1)];
/* 573:704 */       j = this.q[(64 + k + 1 + 32)];
/* 574:705 */       n = (j * alphaTab[(m + 4)] & 0xFFFF) + (j * alphaTab[(m + 4)] >> 16);
/* 575:    */       
/* 576:707 */       this.q[(64 + k + 1)] = (i + n);
/* 577:708 */       this.q[(64 + k + 1 + 32)] = (i - n);
/* 578:709 */       i = this.q[(64 + k + 2)];
/* 579:710 */       j = this.q[(64 + k + 2 + 32)];
/* 580:711 */       n = (j * alphaTab[(m + 8)] & 0xFFFF) + (j * alphaTab[(m + 8)] >> 16);
/* 581:    */       
/* 582:713 */       this.q[(64 + k + 2)] = (i + n);
/* 583:714 */       this.q[(64 + k + 2 + 32)] = (i - n);
/* 584:715 */       i = this.q[(64 + k + 3)];
/* 585:716 */       j = this.q[(64 + k + 3 + 32)];
/* 586:717 */       n = (j * alphaTab[(m + 12)] & 0xFFFF) + (j * alphaTab[(m + 12)] >> 16);
/* 587:    */       
/* 588:719 */       this.q[(64 + k + 3)] = (i + n);
/* 589:720 */       this.q[(64 + k + 3 + 32)] = (i - n);k += 4;
/* 590:    */     }
/* 591:722 */     i = this.q[0];
/* 592:723 */     j = this.q[64];
/* 593:724 */     this.q[0] = (i + j);
/* 594:725 */     this.q[64] = (i - j);
/* 595:726 */     k = 0;
/* 596:726 */     for (m = 0; k < 64; m += 8)
/* 597:    */     {
/* 598:728 */       if (k != 0)
/* 599:    */       {
/* 600:729 */         i = this.q[(0 + k + 0)];
/* 601:730 */         j = this.q[(0 + k + 0 + 64)];
/* 602:731 */         n = (j * alphaTab[(m + 0)] & 0xFFFF) + (j * alphaTab[(m + 0)] >> 16);
/* 603:    */         
/* 604:733 */         this.q[(0 + k + 0)] = (i + n);
/* 605:734 */         this.q[(0 + k + 0 + 64)] = (i - n);
/* 606:    */       }
/* 607:736 */       i = this.q[(0 + k + 1)];
/* 608:737 */       j = this.q[(0 + k + 1 + 64)];
/* 609:738 */       n = (j * alphaTab[(m + 2)] & 0xFFFF) + (j * alphaTab[(m + 2)] >> 16);
/* 610:    */       
/* 611:740 */       this.q[(0 + k + 1)] = (i + n);
/* 612:741 */       this.q[(0 + k + 1 + 64)] = (i - n);
/* 613:742 */       i = this.q[(0 + k + 2)];
/* 614:743 */       j = this.q[(0 + k + 2 + 64)];
/* 615:744 */       n = (j * alphaTab[(m + 4)] & 0xFFFF) + (j * alphaTab[(m + 4)] >> 16);
/* 616:    */       
/* 617:746 */       this.q[(0 + k + 2)] = (i + n);
/* 618:747 */       this.q[(0 + k + 2 + 64)] = (i - n);
/* 619:748 */       i = this.q[(0 + k + 3)];
/* 620:749 */       j = this.q[(0 + k + 3 + 64)];
/* 621:750 */       n = (j * alphaTab[(m + 6)] & 0xFFFF) + (j * alphaTab[(m + 6)] >> 16);
/* 622:    */       
/* 623:752 */       this.q[(0 + k + 3)] = (i + n);
/* 624:753 */       this.q[(0 + k + 3 + 64)] = (i - n);k += 4;
/* 625:    */     }
/* 626:755 */     if (paramBoolean) {
/* 627:756 */       for (k = 0; k < 128; k++)
/* 628:    */       {
/* 629:759 */         m = this.q[k] + yoffF[k];
/* 630:760 */         m = (m & 0xFFFF) + (m >> 16);
/* 631:761 */         m = (m & 0xFF) - (m >> 8);
/* 632:762 */         m = (m & 0xFF) - (m >> 8);
/* 633:763 */         this.q[k] = (m <= 128 ? m : m - 257);
/* 634:    */       }
/* 635:    */     } else {
/* 636:766 */       for (k = 0; k < 128; k++)
/* 637:    */       {
/* 638:769 */         m = this.q[k] + yoffN[k];
/* 639:770 */         m = (m & 0xFFFF) + (m >> 16);
/* 640:771 */         m = (m & 0xFF) - (m >> 8);
/* 641:772 */         m = (m & 0xFF) - (m >> 8);
/* 642:773 */         this.q[k] = (m <= 128 ? m : m - 257);
/* 643:    */       }
/* 644:    */     }
/* 645:777 */     System.arraycopy(this.state, 0, this.tmpState, 0, 16);
/* 646:779 */     for (k = 0; k < 16; k += 4)
/* 647:    */     {
/* 648:780 */       this.state[(k + 0)] ^= decodeLEInt(paramArrayOfByte, 4 * (k + 0));
/* 649:781 */       this.state[(k + 1)] ^= decodeLEInt(paramArrayOfByte, 4 * (k + 1));
/* 650:782 */       this.state[(k + 2)] ^= decodeLEInt(paramArrayOfByte, 4 * (k + 2));
/* 651:783 */       this.state[(k + 3)] ^= decodeLEInt(paramArrayOfByte, 4 * (k + 3));
/* 652:    */     }
/* 653:786 */     for (k = 0; k < 32; k += 4)
/* 654:    */     {
/* 655:787 */       m = wsp[((k >> 2) + 0)];
/* 656:788 */       this.w[(k + 0)] = ((this.q[(m + 0 + 0)] * 185 & 0xFFFF) + (this.q[(m + 0 + 1)] * 185 << 16));
/* 657:    */       
/* 658:790 */       this.w[(k + 1)] = ((this.q[(m + 2 + 0)] * 185 & 0xFFFF) + (this.q[(m + 2 + 1)] * 185 << 16));
/* 659:    */       
/* 660:792 */       this.w[(k + 2)] = ((this.q[(m + 4 + 0)] * 185 & 0xFFFF) + (this.q[(m + 4 + 1)] * 185 << 16));
/* 661:    */       
/* 662:794 */       this.w[(k + 3)] = ((this.q[(m + 6 + 0)] * 185 & 0xFFFF) + (this.q[(m + 6 + 1)] * 185 << 16));
/* 663:    */     }
/* 664:797 */     oneRound(0, 3, 23, 17, 27);
/* 665:798 */     for (k = 0; k < 32; k += 4)
/* 666:    */     {
/* 667:799 */       m = wsp[((k >> 2) + 8)];
/* 668:800 */       this.w[(k + 0)] = ((this.q[(m + 0 + 0)] * 185 & 0xFFFF) + (this.q[(m + 0 + 1)] * 185 << 16));
/* 669:    */       
/* 670:802 */       this.w[(k + 1)] = ((this.q[(m + 2 + 0)] * 185 & 0xFFFF) + (this.q[(m + 2 + 1)] * 185 << 16));
/* 671:    */       
/* 672:804 */       this.w[(k + 2)] = ((this.q[(m + 4 + 0)] * 185 & 0xFFFF) + (this.q[(m + 4 + 1)] * 185 << 16));
/* 673:    */       
/* 674:806 */       this.w[(k + 3)] = ((this.q[(m + 6 + 0)] * 185 & 0xFFFF) + (this.q[(m + 6 + 1)] * 185 << 16));
/* 675:    */     }
/* 676:809 */     oneRound(2, 28, 19, 22, 7);
/* 677:810 */     for (k = 0; k < 32; k += 4)
/* 678:    */     {
/* 679:811 */       m = wsp[((k >> 2) + 16)];
/* 680:812 */       this.w[(k + 0)] = ((this.q[(m + 0 + -128)] * 233 & 0xFFFF) + (this.q[(m + 0 + -64)] * 233 << 16));
/* 681:    */       
/* 682:814 */       this.w[(k + 1)] = ((this.q[(m + 2 + -128)] * 233 & 0xFFFF) + (this.q[(m + 2 + -64)] * 233 << 16));
/* 683:    */       
/* 684:816 */       this.w[(k + 2)] = ((this.q[(m + 4 + -128)] * 233 & 0xFFFF) + (this.q[(m + 4 + -64)] * 233 << 16));
/* 685:    */       
/* 686:818 */       this.w[(k + 3)] = ((this.q[(m + 6 + -128)] * 233 & 0xFFFF) + (this.q[(m + 6 + -64)] * 233 << 16));
/* 687:    */     }
/* 688:821 */     oneRound(1, 29, 9, 15, 5);
/* 689:822 */     for (k = 0; k < 32; k += 4)
/* 690:    */     {
/* 691:823 */       m = wsp[((k >> 2) + 24)];
/* 692:824 */       this.w[(k + 0)] = ((this.q[(m + 0 + -191)] * 233 & 0xFFFF) + (this.q[(m + 0 + -127)] * 233 << 16));
/* 693:    */       
/* 694:826 */       this.w[(k + 1)] = ((this.q[(m + 2 + -191)] * 233 & 0xFFFF) + (this.q[(m + 2 + -127)] * 233 << 16));
/* 695:    */       
/* 696:828 */       this.w[(k + 2)] = ((this.q[(m + 4 + -191)] * 233 & 0xFFFF) + (this.q[(m + 4 + -127)] * 233 << 16));
/* 697:    */       
/* 698:830 */       this.w[(k + 3)] = ((this.q[(m + 6 + -191)] * 233 & 0xFFFF) + (this.q[(m + 6 + -127)] * 233 << 16));
/* 699:    */     }
/* 700:833 */     oneRound(0, 4, 13, 10, 25);
/* 701:    */     
/* 702:    */ 
/* 703:836 */     k = circularLeft(this.state[0], 4);
/* 704:837 */     m = circularLeft(this.state[1], 4);
/* 705:838 */     int n = circularLeft(this.state[2], 4);
/* 706:839 */     int i1 = circularLeft(this.state[3], 4);
/* 707:    */     
/* 708:841 */     int i2 = this.state[12] + this.tmpState[0] + ((this.state[4] ^ this.state[8]) & this.state[0] ^ this.state[8]);
/* 709:    */     
/* 710:843 */     this.state[0] = (circularLeft(i2, 13) + i1);
/* 711:844 */     this.state[12] = this.state[8];
/* 712:845 */     this.state[8] = this.state[4];
/* 713:846 */     this.state[4] = k;
/* 714:847 */     i2 = this.state[13] + this.tmpState[1] + ((this.state[5] ^ this.state[9]) & this.state[1] ^ this.state[9]);
/* 715:    */     
/* 716:849 */     this.state[1] = (circularLeft(i2, 13) + n);
/* 717:850 */     this.state[13] = this.state[9];
/* 718:851 */     this.state[9] = this.state[5];
/* 719:852 */     this.state[5] = m;
/* 720:853 */     i2 = this.state[14] + this.tmpState[2] + ((this.state[6] ^ this.state[10]) & this.state[2] ^ this.state[10]);
/* 721:    */     
/* 722:855 */     this.state[2] = (circularLeft(i2, 13) + m);
/* 723:856 */     this.state[14] = this.state[10];
/* 724:857 */     this.state[10] = this.state[6];
/* 725:858 */     this.state[6] = n;
/* 726:859 */     i2 = this.state[15] + this.tmpState[3] + ((this.state[7] ^ this.state[11]) & this.state[3] ^ this.state[11]);
/* 727:    */     
/* 728:861 */     this.state[3] = (circularLeft(i2, 13) + k);
/* 729:862 */     this.state[15] = this.state[11];
/* 730:863 */     this.state[11] = this.state[7];
/* 731:864 */     this.state[7] = i1;
/* 732:    */     
/* 733:    */ 
/* 734:867 */     k = circularLeft(this.state[0], 13);
/* 735:868 */     m = circularLeft(this.state[1], 13);
/* 736:869 */     n = circularLeft(this.state[2], 13);
/* 737:870 */     i1 = circularLeft(this.state[3], 13);
/* 738:    */     
/* 739:872 */     i2 = this.state[12] + this.tmpState[4] + ((this.state[4] ^ this.state[8]) & this.state[0] ^ this.state[8]);
/* 740:    */     
/* 741:874 */     this.state[0] = (circularLeft(i2, 10) + m);
/* 742:875 */     this.state[12] = this.state[8];
/* 743:876 */     this.state[8] = this.state[4];
/* 744:877 */     this.state[4] = k;
/* 745:878 */     i2 = this.state[13] + this.tmpState[5] + ((this.state[5] ^ this.state[9]) & this.state[1] ^ this.state[9]);
/* 746:    */     
/* 747:880 */     this.state[1] = (circularLeft(i2, 10) + k);
/* 748:881 */     this.state[13] = this.state[9];
/* 749:882 */     this.state[9] = this.state[5];
/* 750:883 */     this.state[5] = m;
/* 751:884 */     i2 = this.state[14] + this.tmpState[6] + ((this.state[6] ^ this.state[10]) & this.state[2] ^ this.state[10]);
/* 752:    */     
/* 753:886 */     this.state[2] = (circularLeft(i2, 10) + i1);
/* 754:887 */     this.state[14] = this.state[10];
/* 755:888 */     this.state[10] = this.state[6];
/* 756:889 */     this.state[6] = n;
/* 757:890 */     i2 = this.state[15] + this.tmpState[7] + ((this.state[7] ^ this.state[11]) & this.state[3] ^ this.state[11]);
/* 758:    */     
/* 759:892 */     this.state[3] = (circularLeft(i2, 10) + n);
/* 760:893 */     this.state[15] = this.state[11];
/* 761:894 */     this.state[11] = this.state[7];
/* 762:895 */     this.state[7] = i1;
/* 763:    */     
/* 764:    */ 
/* 765:898 */     k = circularLeft(this.state[0], 10);
/* 766:899 */     m = circularLeft(this.state[1], 10);
/* 767:900 */     n = circularLeft(this.state[2], 10);
/* 768:901 */     i1 = circularLeft(this.state[3], 10);
/* 769:    */     
/* 770:903 */     i2 = this.state[12] + this.tmpState[8] + ((this.state[4] ^ this.state[8]) & this.state[0] ^ this.state[8]);
/* 771:    */     
/* 772:905 */     this.state[0] = (circularLeft(i2, 25) + n);
/* 773:906 */     this.state[12] = this.state[8];
/* 774:907 */     this.state[8] = this.state[4];
/* 775:908 */     this.state[4] = k;
/* 776:909 */     i2 = this.state[13] + this.tmpState[9] + ((this.state[5] ^ this.state[9]) & this.state[1] ^ this.state[9]);
/* 777:    */     
/* 778:911 */     this.state[1] = (circularLeft(i2, 25) + i1);
/* 779:912 */     this.state[13] = this.state[9];
/* 780:913 */     this.state[9] = this.state[5];
/* 781:914 */     this.state[5] = m;
/* 782:915 */     i2 = this.state[14] + this.tmpState[10] + ((this.state[6] ^ this.state[10]) & this.state[2] ^ this.state[10]);
/* 783:    */     
/* 784:917 */     this.state[2] = (circularLeft(i2, 25) + k);
/* 785:918 */     this.state[14] = this.state[10];
/* 786:919 */     this.state[10] = this.state[6];
/* 787:920 */     this.state[6] = n;
/* 788:921 */     i2 = this.state[15] + this.tmpState[11] + ((this.state[7] ^ this.state[11]) & this.state[3] ^ this.state[11]);
/* 789:    */     
/* 790:923 */     this.state[3] = (circularLeft(i2, 25) + m);
/* 791:924 */     this.state[15] = this.state[11];
/* 792:925 */     this.state[11] = this.state[7];
/* 793:926 */     this.state[7] = i1;
/* 794:    */     
/* 795:    */ 
/* 796:929 */     k = circularLeft(this.state[0], 25);
/* 797:930 */     m = circularLeft(this.state[1], 25);
/* 798:931 */     n = circularLeft(this.state[2], 25);
/* 799:932 */     i1 = circularLeft(this.state[3], 25);
/* 800:    */     
/* 801:934 */     i2 = this.state[12] + this.tmpState[12] + ((this.state[4] ^ this.state[8]) & this.state[0] ^ this.state[8]);
/* 802:    */     
/* 803:936 */     this.state[0] = (circularLeft(i2, 4) + i1);
/* 804:937 */     this.state[12] = this.state[8];
/* 805:938 */     this.state[8] = this.state[4];
/* 806:939 */     this.state[4] = k;
/* 807:940 */     i2 = this.state[13] + this.tmpState[13] + ((this.state[5] ^ this.state[9]) & this.state[1] ^ this.state[9]);
/* 808:    */     
/* 809:942 */     this.state[1] = (circularLeft(i2, 4) + n);
/* 810:943 */     this.state[13] = this.state[9];
/* 811:944 */     this.state[9] = this.state[5];
/* 812:945 */     this.state[5] = m;
/* 813:946 */     i2 = this.state[14] + this.tmpState[14] + ((this.state[6] ^ this.state[10]) & this.state[2] ^ this.state[10]);
/* 814:    */     
/* 815:948 */     this.state[2] = (circularLeft(i2, 4) + m);
/* 816:949 */     this.state[14] = this.state[10];
/* 817:950 */     this.state[10] = this.state[6];
/* 818:951 */     this.state[6] = n;
/* 819:952 */     i2 = this.state[15] + this.tmpState[15] + ((this.state[7] ^ this.state[11]) & this.state[3] ^ this.state[11]);
/* 820:    */     
/* 821:954 */     this.state[3] = (circularLeft(i2, 4) + k);
/* 822:955 */     this.state[15] = this.state[11];
/* 823:956 */     this.state[11] = this.state[7];
/* 824:957 */     this.state[7] = i1;
/* 825:    */   }
/* 826:    */   
/* 827:    */   public String toString()
/* 828:    */   {
/* 829:964 */     return "SIMD-" + (getDigestLength() << 3);
/* 830:    */   }
/* 831:    */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     fr.cryptohash.SIMDSmallCore
 * JD-Core Version:    0.7.1
 */