/*    1:     */ package fr.cryptohash;
/*    2:     */ 
/*    3:     */ abstract class SIMDBigCore
/*    4:     */   extends DigestEngine
/*    5:     */ {
/*    6:     */   private int[] state;
/*    7:     */   private int[] q;
/*    8:     */   private int[] w;
/*    9:     */   private int[] tmpState;
/*   10:     */   private int[] tA;
/*   11:     */   
/*   12:     */   public int getBlockLength()
/*   13:     */   {
/*   14:  54 */     return 128;
/*   15:     */   }
/*   16:     */   
/*   17:     */   protected Digest copyState(SIMDBigCore paramSIMDBigCore)
/*   18:     */   {
/*   19:  60 */     System.arraycopy(this.state, 0, paramSIMDBigCore.state, 0, 32);
/*   20:  61 */     return super.copyState(paramSIMDBigCore);
/*   21:     */   }
/*   22:     */   
/*   23:     */   protected void engineReset()
/*   24:     */   {
/*   25:  67 */     int[] arrayOfInt = getInitVal();
/*   26:  68 */     System.arraycopy(arrayOfInt, 0, this.state, 0, 32);
/*   27:     */   }
/*   28:     */   
/*   29:     */   abstract int[] getInitVal();
/*   30:     */   
/*   31:     */   protected void doPadding(byte[] paramArrayOfByte, int paramInt)
/*   32:     */   {
/*   33:  81 */     int i = flush();
/*   34:  82 */     byte[] arrayOfByte = getBlockBuffer();
/*   35:  83 */     if (i != 0)
/*   36:     */     {
/*   37:  84 */       for (int j = i; j < 128; j++) {
/*   38:  85 */         arrayOfByte[j] = 0;
/*   39:     */       }
/*   40:  86 */       compress(arrayOfByte, false);
/*   41:     */     }
/*   42:  88 */     long l = (getBlockCount() << 10) + (i << 3);
/*   43:  89 */     encodeLEInt((int)l, arrayOfByte, 0);
/*   44:  90 */     encodeLEInt((int)(l >> 32), arrayOfByte, 4);
/*   45:  91 */     for (int k = 8; k < 128; k++) {
/*   46:  92 */       arrayOfByte[k] = 0;
/*   47:     */     }
/*   48:  93 */     compress(arrayOfByte, true);
/*   49:  94 */     k = getDigestLength() >>> 2;
/*   50:  95 */     for (int m = 0; m < k; m++) {
/*   51:  96 */       encodeLEInt(this.state[m], paramArrayOfByte, paramInt + (m << 2));
/*   52:     */     }
/*   53:     */   }
/*   54:     */   
/*   55:     */   protected void doInit()
/*   56:     */   {
/*   57: 102 */     this.state = new int[32];
/*   58: 103 */     this.q = new int[256];
/*   59: 104 */     this.w = new int[64];
/*   60: 105 */     this.tmpState = new int[32];
/*   61: 106 */     this.tA = new int[8];
/*   62: 107 */     engineReset();
/*   63:     */   }
/*   64:     */   
/*   65:     */   private static final void encodeLEInt(int paramInt1, byte[] paramArrayOfByte, int paramInt2)
/*   66:     */   {
/*   67: 121 */     paramArrayOfByte[(paramInt2 + 0)] = ((byte)paramInt1);
/*   68: 122 */     paramArrayOfByte[(paramInt2 + 1)] = ((byte)(paramInt1 >>> 8));
/*   69: 123 */     paramArrayOfByte[(paramInt2 + 2)] = ((byte)(paramInt1 >>> 16));
/*   70: 124 */     paramArrayOfByte[(paramInt2 + 3)] = ((byte)(paramInt1 >>> 24));
/*   71:     */   }
/*   72:     */   
/*   73:     */   private static final int decodeLEInt(byte[] paramArrayOfByte, int paramInt)
/*   74:     */   {
/*   75: 137 */     return (paramArrayOfByte[(paramInt + 3)] & 0xFF) << 24 | (paramArrayOfByte[(paramInt + 2)] & 0xFF) << 16 | (paramArrayOfByte[(paramInt + 1)] & 0xFF) << 8 | paramArrayOfByte[paramInt] & 0xFF;
/*   76:     */   }
/*   77:     */   
/*   78:     */   private static int circularLeft(int paramInt1, int paramInt2)
/*   79:     */   {
/*   80: 154 */     return paramInt1 >>> 32 - paramInt2 | paramInt1 << paramInt2;
/*   81:     */   }
/*   82:     */   
/*   83:     */   protected void processBlock(byte[] paramArrayOfByte)
/*   84:     */   {
/*   85: 160 */     compress(paramArrayOfByte, false);
/*   86:     */   }
/*   87:     */   
/*   88: 163 */   private static final int[] alphaTab = { 1, 41, 139, 45, 46, 87, 226, 14, 60, 147, 116, 130, 190, 80, 196, 69, 2, 82, 21, 90, 92, 174, 195, 28, 120, 37, 232, 3, 123, 160, 135, 138, 4, 164, 42, 180, 184, 91, 133, 56, 240, 74, 207, 6, 246, 63, 13, 19, 8, 71, 84, 103, 111, 182, 9, 112, 223, 148, 157, 12, 235, 126, 26, 38, 16, 142, 168, 206, 222, 107, 18, 224, 189, 39, 57, 24, 213, 252, 52, 76, 32, 27, 79, 155, 187, 214, 36, 191, 121, 78, 114, 48, 169, 247, 104, 152, 64, 54, 158, 53, 117, 171, 72, 125, 242, 156, 228, 96, 81, 237, 208, 47, 128, 108, 59, 106, 234, 85, 144, 250, 227, 55, 199, 192, 162, 217, 159, 94, 256, 216, 118, 212, 211, 170, 31, 243, 197, 110, 141, 127, 67, 177, 61, 188, 255, 175, 236, 167, 165, 83, 62, 229, 137, 220, 25, 254, 134, 97, 122, 119, 253, 93, 215, 77, 73, 166, 124, 201, 17, 183, 50, 251, 11, 194, 244, 238, 249, 186, 173, 154, 146, 75, 248, 145, 34, 109, 100, 245, 22, 131, 231, 219, 241, 115, 89, 51, 35, 150, 239, 33, 68, 218, 200, 233, 44, 5, 205, 181, 225, 230, 178, 102, 70, 43, 221, 66, 136, 179, 143, 209, 88, 10, 153, 105, 193, 203, 99, 204, 140, 86, 185, 132, 15, 101, 29, 161, 176, 20, 49, 210, 129, 149, 198, 151, 23, 172, 113, 7, 30, 202, 58, 65, 95, 40, 98, 163 };
/*   89: 188 */   private static final int[] yoffN = { 1, 163, 98, 40, 95, 65, 58, 202, 30, 7, 113, 172, 23, 151, 198, 149, 129, 210, 49, 20, 176, 161, 29, 101, 15, 132, 185, 86, 140, 204, 99, 203, 193, 105, 153, 10, 88, 209, 143, 179, 136, 66, 221, 43, 70, 102, 178, 230, 225, 181, 205, 5, 44, 233, 200, 218, 68, 33, 239, 150, 35, 51, 89, 115, 241, 219, 231, 131, 22, 245, 100, 109, 34, 145, 248, 75, 146, 154, 173, 186, 249, 238, 244, 194, 11, 251, 50, 183, 17, 201, 124, 166, 73, 77, 215, 93, 253, 119, 122, 97, 134, 254, 25, 220, 137, 229, 62, 83, 165, 167, 236, 175, 255, 188, 61, 177, 67, 127, 141, 110, 197, 243, 31, 170, 211, 212, 118, 216, 256, 94, 159, 217, 162, 192, 199, 55, 227, 250, 144, 85, 234, 106, 59, 108, 128, 47, 208, 237, 81, 96, 228, 156, 242, 125, 72, 171, 117, 53, 158, 54, 64, 152, 104, 247, 169, 48, 114, 78, 121, 191, 36, 214, 187, 155, 79, 27, 32, 76, 52, 252, 213, 24, 57, 39, 189, 224, 18, 107, 222, 206, 168, 142, 16, 38, 26, 126, 235, 12, 157, 148, 223, 112, 9, 182, 111, 103, 84, 71, 8, 19, 13, 63, 246, 6, 207, 74, 240, 56, 133, 91, 184, 180, 42, 164, 4, 138, 135, 160, 123, 3, 232, 37, 120, 28, 195, 174, 92, 90, 21, 82, 2, 69, 196, 80, 190, 130, 116, 147, 60, 14, 226, 87, 46, 45, 139, 41 };
/*   90: 213 */   private static final int[] yoffF = { 2, 203, 156, 47, 118, 214, 107, 106, 45, 93, 212, 20, 111, 73, 162, 251, 97, 215, 249, 53, 211, 19, 3, 89, 49, 207, 101, 67, 151, 130, 223, 23, 189, 202, 178, 239, 253, 127, 204, 49, 76, 236, 82, 137, 232, 157, 65, 79, 96, 161, 176, 130, 161, 30, 47, 9, 189, 247, 61, 226, 248, 90, 107, 64, 0, 88, 131, 243, 133, 59, 113, 115, 17, 236, 33, 213, 12, 191, 111, 19, 251, 61, 103, 208, 57, 35, 148, 248, 47, 116, 65, 119, 249, 178, 143, 40, 189, 129, 8, 163, 204, 227, 230, 196, 205, 122, 151, 45, 187, 19, 227, 72, 247, 125, 111, 121, 140, 220, 6, 107, 77, 69, 10, 101, 21, 65, 149, 171, 255, 54, 101, 210, 139, 43, 150, 151, 212, 164, 45, 237, 146, 184, 95, 6, 160, 42, 8, 204, 46, 238, 254, 168, 208, 50, 156, 190, 106, 127, 34, 234, 68, 55, 79, 18, 4, 130, 53, 208, 181, 21, 175, 120, 25, 100, 192, 178, 161, 96, 81, 127, 96, 227, 210, 248, 68, 10, 196, 31, 9, 167, 150, 193, 0, 169, 126, 14, 124, 198, 144, 142, 240, 21, 224, 44, 245, 66, 146, 238, 6, 196, 154, 49, 200, 222, 109, 9, 210, 141, 192, 138, 8, 79, 114, 217, 68, 128, 249, 94, 53, 30, 27, 61, 52, 135, 106, 212, 70, 238, 30, 185, 10, 132, 146, 136, 117, 37, 251, 150, 180, 188, 247, 156, 236, 192, 108, 86 };
/*   91:     */   
/*   92:     */   private final void fft64(byte[] paramArrayOfByte, int paramInt1, int paramInt2, int paramInt3)
/*   93:     */   {
/*   94: 240 */     int i = paramInt2 << 1;
/*   95:     */     
/*   96:     */ 
/*   97:     */ 
/*   98:     */ 
/*   99: 245 */     int i13 = paramArrayOfByte[(paramInt1 + 0 * i)] & 0xFF;
/*  100: 246 */     int i14 = paramArrayOfByte[(paramInt1 + 4 * i)] & 0xFF;
/*  101: 247 */     int i15 = paramArrayOfByte[(paramInt1 + 8 * i)] & 0xFF;
/*  102: 248 */     int i16 = paramArrayOfByte[(paramInt1 + 12 * i)] & 0xFF;
/*  103: 249 */     int i17 = i13 + i15;
/*  104: 250 */     int i18 = i13 + (i15 << 4);
/*  105: 251 */     int i19 = i13 - i15;
/*  106: 252 */     int i20 = i13 - (i15 << 4);
/*  107: 253 */     int i21 = i14 + i16;
/*  108: 254 */     int i22 = ((i14 << 2) + (i16 << 6) & 0xFF) - ((i14 << 2) + (i16 << 6) >> 8);
/*  109:     */     
/*  110: 256 */     int i23 = (i14 << 4) - (i16 << 4);
/*  111: 257 */     int i24 = ((i14 << 6) + (i16 << 2) & 0xFF) - ((i14 << 6) + (i16 << 2) >> 8);
/*  112:     */     
/*  113: 259 */     int j = i17 + i21;
/*  114: 260 */     int k = i18 + i22;
/*  115: 261 */     int m = i19 + i23;
/*  116: 262 */     int n = i20 + i24;
/*  117: 263 */     int i1 = i17 - i21;
/*  118: 264 */     int i2 = i18 - i22;
/*  119: 265 */     int i3 = i19 - i23;
/*  120: 266 */     int i4 = i20 - i24;
/*  121:     */     
/*  122:     */ 
/*  123: 269 */     i13 = paramArrayOfByte[(paramInt1 + 2 * i)] & 0xFF;
/*  124: 270 */     i14 = paramArrayOfByte[(paramInt1 + 6 * i)] & 0xFF;
/*  125: 271 */     i15 = paramArrayOfByte[(paramInt1 + 10 * i)] & 0xFF;
/*  126: 272 */     i16 = paramArrayOfByte[(paramInt1 + 14 * i)] & 0xFF;
/*  127: 273 */     i17 = i13 + i15;
/*  128: 274 */     i18 = i13 + (i15 << 4);
/*  129: 275 */     i19 = i13 - i15;
/*  130: 276 */     i20 = i13 - (i15 << 4);
/*  131: 277 */     i21 = i14 + i16;
/*  132: 278 */     i22 = ((i14 << 2) + (i16 << 6) & 0xFF) - ((i14 << 2) + (i16 << 6) >> 8);
/*  133:     */     
/*  134: 280 */     i23 = (i14 << 4) - (i16 << 4);
/*  135: 281 */     i24 = ((i14 << 6) + (i16 << 2) & 0xFF) - ((i14 << 6) + (i16 << 2) >> 8);
/*  136:     */     
/*  137: 283 */     int i5 = i17 + i21;
/*  138: 284 */     int i6 = i18 + i22;
/*  139: 285 */     int i7 = i19 + i23;
/*  140: 286 */     int i8 = i20 + i24;
/*  141: 287 */     int i9 = i17 - i21;
/*  142: 288 */     int i10 = i18 - i22;
/*  143: 289 */     int i11 = i19 - i23;
/*  144: 290 */     int i12 = i20 - i24;
/*  145:     */     
/*  146: 292 */     this.q[(paramInt3 + 0)] = (j + i5);
/*  147: 293 */     this.q[(paramInt3 + 1)] = (k + (i6 << 1));
/*  148: 294 */     this.q[(paramInt3 + 2)] = (m + (i7 << 2));
/*  149: 295 */     this.q[(paramInt3 + 3)] = (n + (i8 << 3));
/*  150: 296 */     this.q[(paramInt3 + 4)] = (i1 + (i9 << 4));
/*  151: 297 */     this.q[(paramInt3 + 5)] = (i2 + (i10 << 5));
/*  152: 298 */     this.q[(paramInt3 + 6)] = (i3 + (i11 << 6));
/*  153: 299 */     this.q[(paramInt3 + 7)] = (i4 + (i12 << 7));
/*  154: 300 */     this.q[(paramInt3 + 8)] = (j - i5);
/*  155: 301 */     this.q[(paramInt3 + 9)] = (k - (i6 << 1));
/*  156: 302 */     this.q[(paramInt3 + 10)] = (m - (i7 << 2));
/*  157: 303 */     this.q[(paramInt3 + 11)] = (n - (i8 << 3));
/*  158: 304 */     this.q[(paramInt3 + 12)] = (i1 - (i9 << 4));
/*  159: 305 */     this.q[(paramInt3 + 13)] = (i2 - (i10 << 5));
/*  160: 306 */     this.q[(paramInt3 + 14)] = (i3 - (i11 << 6));
/*  161: 307 */     this.q[(paramInt3 + 15)] = (i4 - (i12 << 7));
/*  162:     */     
/*  163:     */ 
/*  164:     */ 
/*  165:     */ 
/*  166:     */ 
/*  167: 313 */     i13 = paramArrayOfByte[(paramInt1 + 1 * i)] & 0xFF;
/*  168: 314 */     i14 = paramArrayOfByte[(paramInt1 + 5 * i)] & 0xFF;
/*  169: 315 */     i15 = paramArrayOfByte[(paramInt1 + 9 * i)] & 0xFF;
/*  170: 316 */     i16 = paramArrayOfByte[(paramInt1 + 13 * i)] & 0xFF;
/*  171: 317 */     i17 = i13 + i15;
/*  172: 318 */     i18 = i13 + (i15 << 4);
/*  173: 319 */     i19 = i13 - i15;
/*  174: 320 */     i20 = i13 - (i15 << 4);
/*  175: 321 */     i21 = i14 + i16;
/*  176: 322 */     i22 = ((i14 << 2) + (i16 << 6) & 0xFF) - ((i14 << 2) + (i16 << 6) >> 8);
/*  177:     */     
/*  178: 324 */     i23 = (i14 << 4) - (i16 << 4);
/*  179: 325 */     i24 = ((i14 << 6) + (i16 << 2) & 0xFF) - ((i14 << 6) + (i16 << 2) >> 8);
/*  180:     */     
/*  181: 327 */     j = i17 + i21;
/*  182: 328 */     k = i18 + i22;
/*  183: 329 */     m = i19 + i23;
/*  184: 330 */     n = i20 + i24;
/*  185: 331 */     i1 = i17 - i21;
/*  186: 332 */     i2 = i18 - i22;
/*  187: 333 */     i3 = i19 - i23;
/*  188: 334 */     i4 = i20 - i24;
/*  189:     */     
/*  190:     */ 
/*  191: 337 */     i13 = paramArrayOfByte[(paramInt1 + 3 * i)] & 0xFF;
/*  192: 338 */     i14 = paramArrayOfByte[(paramInt1 + 7 * i)] & 0xFF;
/*  193: 339 */     i15 = paramArrayOfByte[(paramInt1 + 11 * i)] & 0xFF;
/*  194: 340 */     i16 = paramArrayOfByte[(paramInt1 + 15 * i)] & 0xFF;
/*  195: 341 */     i17 = i13 + i15;
/*  196: 342 */     i18 = i13 + (i15 << 4);
/*  197: 343 */     i19 = i13 - i15;
/*  198: 344 */     i20 = i13 - (i15 << 4);
/*  199: 345 */     i21 = i14 + i16;
/*  200: 346 */     i22 = ((i14 << 2) + (i16 << 6) & 0xFF) - ((i14 << 2) + (i16 << 6) >> 8);
/*  201:     */     
/*  202: 348 */     i23 = (i14 << 4) - (i16 << 4);
/*  203: 349 */     i24 = ((i14 << 6) + (i16 << 2) & 0xFF) - ((i14 << 6) + (i16 << 2) >> 8);
/*  204:     */     
/*  205: 351 */     i5 = i17 + i21;
/*  206: 352 */     i6 = i18 + i22;
/*  207: 353 */     i7 = i19 + i23;
/*  208: 354 */     i8 = i20 + i24;
/*  209: 355 */     i9 = i17 - i21;
/*  210: 356 */     i10 = i18 - i22;
/*  211: 357 */     i11 = i19 - i23;
/*  212: 358 */     i12 = i20 - i24;
/*  213:     */     
/*  214: 360 */     this.q[(paramInt3 + 16 + 0)] = (j + i5);
/*  215: 361 */     this.q[(paramInt3 + 16 + 1)] = (k + (i6 << 1));
/*  216: 362 */     this.q[(paramInt3 + 16 + 2)] = (m + (i7 << 2));
/*  217: 363 */     this.q[(paramInt3 + 16 + 3)] = (n + (i8 << 3));
/*  218: 364 */     this.q[(paramInt3 + 16 + 4)] = (i1 + (i9 << 4));
/*  219: 365 */     this.q[(paramInt3 + 16 + 5)] = (i2 + (i10 << 5));
/*  220: 366 */     this.q[(paramInt3 + 16 + 6)] = (i3 + (i11 << 6));
/*  221: 367 */     this.q[(paramInt3 + 16 + 7)] = (i4 + (i12 << 7));
/*  222: 368 */     this.q[(paramInt3 + 16 + 8)] = (j - i5);
/*  223: 369 */     this.q[(paramInt3 + 16 + 9)] = (k - (i6 << 1));
/*  224: 370 */     this.q[(paramInt3 + 16 + 10)] = (m - (i7 << 2));
/*  225: 371 */     this.q[(paramInt3 + 16 + 11)] = (n - (i8 << 3));
/*  226: 372 */     this.q[(paramInt3 + 16 + 12)] = (i1 - (i9 << 4));
/*  227: 373 */     this.q[(paramInt3 + 16 + 13)] = (i2 - (i10 << 5));
/*  228: 374 */     this.q[(paramInt3 + 16 + 14)] = (i3 - (i11 << 6));
/*  229: 375 */     this.q[(paramInt3 + 16 + 15)] = (i4 - (i12 << 7));
/*  230:     */     
/*  231: 377 */     j = this.q[paramInt3];
/*  232: 378 */     k = this.q[(paramInt3 + 16)];
/*  233: 379 */     this.q[paramInt3] = (j + k);
/*  234: 380 */     this.q[(paramInt3 + 16)] = (j - k);
/*  235: 381 */     m = 0;
/*  236: 381 */     for (n = 0; m < 16; n += 32)
/*  237:     */     {
/*  238: 383 */       if (m != 0)
/*  239:     */       {
/*  240: 384 */         j = this.q[(paramInt3 + m + 0)];
/*  241: 385 */         k = this.q[(paramInt3 + m + 0 + 16)];
/*  242: 386 */         i1 = (k * alphaTab[(n + 0)] & 0xFFFF) + (k * alphaTab[(n + 0)] >> 16);
/*  243:     */         
/*  244: 388 */         this.q[(paramInt3 + m + 0)] = (j + i1);
/*  245: 389 */         this.q[(paramInt3 + m + 0 + 16)] = (j - i1);
/*  246:     */       }
/*  247: 391 */       j = this.q[(paramInt3 + m + 1)];
/*  248: 392 */       k = this.q[(paramInt3 + m + 1 + 16)];
/*  249: 393 */       i1 = (k * alphaTab[(n + 8)] & 0xFFFF) + (k * alphaTab[(n + 8)] >> 16);
/*  250:     */       
/*  251: 395 */       this.q[(paramInt3 + m + 1)] = (j + i1);
/*  252: 396 */       this.q[(paramInt3 + m + 1 + 16)] = (j - i1);
/*  253: 397 */       j = this.q[(paramInt3 + m + 2)];
/*  254: 398 */       k = this.q[(paramInt3 + m + 2 + 16)];
/*  255: 399 */       i1 = (k * alphaTab[(n + 16)] & 0xFFFF) + (k * alphaTab[(n + 16)] >> 16);
/*  256:     */       
/*  257: 401 */       this.q[(paramInt3 + m + 2)] = (j + i1);
/*  258: 402 */       this.q[(paramInt3 + m + 2 + 16)] = (j - i1);
/*  259: 403 */       j = this.q[(paramInt3 + m + 3)];
/*  260: 404 */       k = this.q[(paramInt3 + m + 3 + 16)];
/*  261: 405 */       i1 = (k * alphaTab[(n + 24)] & 0xFFFF) + (k * alphaTab[(n + 24)] >> 16);
/*  262:     */       
/*  263: 407 */       this.q[(paramInt3 + m + 3)] = (j + i1);
/*  264: 408 */       this.q[(paramInt3 + m + 3 + 16)] = (j - i1);m += 4;
/*  265:     */     }
/*  266: 414 */     i15 = paramArrayOfByte[(paramInt1 + paramInt2 + 0 * i)] & 0xFF;
/*  267: 415 */     i16 = paramArrayOfByte[(paramInt1 + paramInt2 + 4 * i)] & 0xFF;
/*  268: 416 */     i17 = paramArrayOfByte[(paramInt1 + paramInt2 + 8 * i)] & 0xFF;
/*  269: 417 */     i18 = paramArrayOfByte[(paramInt1 + paramInt2 + 12 * i)] & 0xFF;
/*  270: 418 */     i19 = i15 + i17;
/*  271: 419 */     i20 = i15 + (i17 << 4);
/*  272: 420 */     i21 = i15 - i17;
/*  273: 421 */     i22 = i15 - (i17 << 4);
/*  274: 422 */     i23 = i16 + i18;
/*  275: 423 */     i24 = ((i16 << 2) + (i18 << 6) & 0xFF) - ((i16 << 2) + (i18 << 6) >> 8);
/*  276:     */     
/*  277: 425 */     int i25 = (i16 << 4) - (i18 << 4);
/*  278: 426 */     int i26 = ((i16 << 6) + (i18 << 2) & 0xFF) - ((i16 << 6) + (i18 << 2) >> 8);
/*  279:     */     
/*  280: 428 */     m = i19 + i23;
/*  281: 429 */     n = i20 + i24;
/*  282: 430 */     i1 = i21 + i25;
/*  283: 431 */     i2 = i22 + i26;
/*  284: 432 */     i3 = i19 - i23;
/*  285: 433 */     i4 = i20 - i24;
/*  286: 434 */     i5 = i21 - i25;
/*  287: 435 */     i6 = i22 - i26;
/*  288:     */     
/*  289:     */ 
/*  290: 438 */     i15 = paramArrayOfByte[(paramInt1 + paramInt2 + 2 * i)] & 0xFF;
/*  291: 439 */     i16 = paramArrayOfByte[(paramInt1 + paramInt2 + 6 * i)] & 0xFF;
/*  292: 440 */     i17 = paramArrayOfByte[(paramInt1 + paramInt2 + 10 * i)] & 0xFF;
/*  293: 441 */     i18 = paramArrayOfByte[(paramInt1 + paramInt2 + 14 * i)] & 0xFF;
/*  294: 442 */     i19 = i15 + i17;
/*  295: 443 */     i20 = i15 + (i17 << 4);
/*  296: 444 */     i21 = i15 - i17;
/*  297: 445 */     i22 = i15 - (i17 << 4);
/*  298: 446 */     i23 = i16 + i18;
/*  299: 447 */     i24 = ((i16 << 2) + (i18 << 6) & 0xFF) - ((i16 << 2) + (i18 << 6) >> 8);
/*  300:     */     
/*  301: 449 */     i25 = (i16 << 4) - (i18 << 4);
/*  302: 450 */     i26 = ((i16 << 6) + (i18 << 2) & 0xFF) - ((i16 << 6) + (i18 << 2) >> 8);
/*  303:     */     
/*  304: 452 */     i7 = i19 + i23;
/*  305: 453 */     i8 = i20 + i24;
/*  306: 454 */     i9 = i21 + i25;
/*  307: 455 */     i10 = i22 + i26;
/*  308: 456 */     i11 = i19 - i23;
/*  309: 457 */     i12 = i20 - i24;
/*  310: 458 */     i13 = i21 - i25;
/*  311: 459 */     i14 = i22 - i26;
/*  312:     */     
/*  313: 461 */     this.q[(paramInt3 + 32 + 0)] = (m + i7);
/*  314: 462 */     this.q[(paramInt3 + 32 + 1)] = (n + (i8 << 1));
/*  315: 463 */     this.q[(paramInt3 + 32 + 2)] = (i1 + (i9 << 2));
/*  316: 464 */     this.q[(paramInt3 + 32 + 3)] = (i2 + (i10 << 3));
/*  317: 465 */     this.q[(paramInt3 + 32 + 4)] = (i3 + (i11 << 4));
/*  318: 466 */     this.q[(paramInt3 + 32 + 5)] = (i4 + (i12 << 5));
/*  319: 467 */     this.q[(paramInt3 + 32 + 6)] = (i5 + (i13 << 6));
/*  320: 468 */     this.q[(paramInt3 + 32 + 7)] = (i6 + (i14 << 7));
/*  321: 469 */     this.q[(paramInt3 + 32 + 8)] = (m - i7);
/*  322: 470 */     this.q[(paramInt3 + 32 + 9)] = (n - (i8 << 1));
/*  323: 471 */     this.q[(paramInt3 + 32 + 10)] = (i1 - (i9 << 2));
/*  324: 472 */     this.q[(paramInt3 + 32 + 11)] = (i2 - (i10 << 3));
/*  325: 473 */     this.q[(paramInt3 + 32 + 12)] = (i3 - (i11 << 4));
/*  326: 474 */     this.q[(paramInt3 + 32 + 13)] = (i4 - (i12 << 5));
/*  327: 475 */     this.q[(paramInt3 + 32 + 14)] = (i5 - (i13 << 6));
/*  328: 476 */     this.q[(paramInt3 + 32 + 15)] = (i6 - (i14 << 7));
/*  329:     */     
/*  330:     */ 
/*  331:     */ 
/*  332:     */ 
/*  333:     */ 
/*  334: 482 */     i15 = paramArrayOfByte[(paramInt1 + paramInt2 + 1 * i)] & 0xFF;
/*  335: 483 */     i16 = paramArrayOfByte[(paramInt1 + paramInt2 + 5 * i)] & 0xFF;
/*  336: 484 */     i17 = paramArrayOfByte[(paramInt1 + paramInt2 + 9 * i)] & 0xFF;
/*  337: 485 */     i18 = paramArrayOfByte[(paramInt1 + paramInt2 + 13 * i)] & 0xFF;
/*  338: 486 */     i19 = i15 + i17;
/*  339: 487 */     i20 = i15 + (i17 << 4);
/*  340: 488 */     i21 = i15 - i17;
/*  341: 489 */     i22 = i15 - (i17 << 4);
/*  342: 490 */     i23 = i16 + i18;
/*  343: 491 */     i24 = ((i16 << 2) + (i18 << 6) & 0xFF) - ((i16 << 2) + (i18 << 6) >> 8);
/*  344:     */     
/*  345: 493 */     i25 = (i16 << 4) - (i18 << 4);
/*  346: 494 */     i26 = ((i16 << 6) + (i18 << 2) & 0xFF) - ((i16 << 6) + (i18 << 2) >> 8);
/*  347:     */     
/*  348: 496 */     m = i19 + i23;
/*  349: 497 */     n = i20 + i24;
/*  350: 498 */     i1 = i21 + i25;
/*  351: 499 */     i2 = i22 + i26;
/*  352: 500 */     i3 = i19 - i23;
/*  353: 501 */     i4 = i20 - i24;
/*  354: 502 */     i5 = i21 - i25;
/*  355: 503 */     i6 = i22 - i26;
/*  356:     */     
/*  357:     */ 
/*  358: 506 */     i15 = paramArrayOfByte[(paramInt1 + paramInt2 + 3 * i)] & 0xFF;
/*  359: 507 */     i16 = paramArrayOfByte[(paramInt1 + paramInt2 + 7 * i)] & 0xFF;
/*  360: 508 */     i17 = paramArrayOfByte[(paramInt1 + paramInt2 + 11 * i)] & 0xFF;
/*  361: 509 */     i18 = paramArrayOfByte[(paramInt1 + paramInt2 + 15 * i)] & 0xFF;
/*  362: 510 */     i19 = i15 + i17;
/*  363: 511 */     i20 = i15 + (i17 << 4);
/*  364: 512 */     i21 = i15 - i17;
/*  365: 513 */     i22 = i15 - (i17 << 4);
/*  366: 514 */     i23 = i16 + i18;
/*  367: 515 */     i24 = ((i16 << 2) + (i18 << 6) & 0xFF) - ((i16 << 2) + (i18 << 6) >> 8);
/*  368:     */     
/*  369: 517 */     i25 = (i16 << 4) - (i18 << 4);
/*  370: 518 */     i26 = ((i16 << 6) + (i18 << 2) & 0xFF) - ((i16 << 6) + (i18 << 2) >> 8);
/*  371:     */     
/*  372: 520 */     i7 = i19 + i23;
/*  373: 521 */     i8 = i20 + i24;
/*  374: 522 */     i9 = i21 + i25;
/*  375: 523 */     i10 = i22 + i26;
/*  376: 524 */     i11 = i19 - i23;
/*  377: 525 */     i12 = i20 - i24;
/*  378: 526 */     i13 = i21 - i25;
/*  379: 527 */     i14 = i22 - i26;
/*  380:     */     
/*  381: 529 */     this.q[(paramInt3 + 32 + 16 + 0)] = (m + i7);
/*  382: 530 */     this.q[(paramInt3 + 32 + 16 + 1)] = (n + (i8 << 1));
/*  383: 531 */     this.q[(paramInt3 + 32 + 16 + 2)] = (i1 + (i9 << 2));
/*  384: 532 */     this.q[(paramInt3 + 32 + 16 + 3)] = (i2 + (i10 << 3));
/*  385: 533 */     this.q[(paramInt3 + 32 + 16 + 4)] = (i3 + (i11 << 4));
/*  386: 534 */     this.q[(paramInt3 + 32 + 16 + 5)] = (i4 + (i12 << 5));
/*  387: 535 */     this.q[(paramInt3 + 32 + 16 + 6)] = (i5 + (i13 << 6));
/*  388: 536 */     this.q[(paramInt3 + 32 + 16 + 7)] = (i6 + (i14 << 7));
/*  389: 537 */     this.q[(paramInt3 + 32 + 16 + 8)] = (m - i7);
/*  390: 538 */     this.q[(paramInt3 + 32 + 16 + 9)] = (n - (i8 << 1));
/*  391: 539 */     this.q[(paramInt3 + 32 + 16 + 10)] = (i1 - (i9 << 2));
/*  392: 540 */     this.q[(paramInt3 + 32 + 16 + 11)] = (i2 - (i10 << 3));
/*  393: 541 */     this.q[(paramInt3 + 32 + 16 + 12)] = (i3 - (i11 << 4));
/*  394: 542 */     this.q[(paramInt3 + 32 + 16 + 13)] = (i4 - (i12 << 5));
/*  395: 543 */     this.q[(paramInt3 + 32 + 16 + 14)] = (i5 - (i13 << 6));
/*  396: 544 */     this.q[(paramInt3 + 32 + 16 + 15)] = (i6 - (i14 << 7));
/*  397:     */     
/*  398: 546 */     j = this.q[(paramInt3 + 32)];
/*  399: 547 */     k = this.q[(paramInt3 + 32 + 16)];
/*  400: 548 */     this.q[(paramInt3 + 32)] = (j + k);
/*  401: 549 */     this.q[(paramInt3 + 32 + 16)] = (j - k);
/*  402: 550 */     m = 0;
/*  403: 550 */     for (n = 0; m < 16; n += 32)
/*  404:     */     {
/*  405: 552 */       if (m != 0)
/*  406:     */       {
/*  407: 553 */         j = this.q[(paramInt3 + 32 + m + 0)];
/*  408: 554 */         k = this.q[(paramInt3 + 32 + m + 0 + 16)];
/*  409: 555 */         i1 = (k * alphaTab[(n + 0)] & 0xFFFF) + (k * alphaTab[(n + 0)] >> 16);
/*  410:     */         
/*  411: 557 */         this.q[(paramInt3 + 32 + m + 0)] = (j + i1);
/*  412: 558 */         this.q[(paramInt3 + 32 + m + 0 + 16)] = (j - i1);
/*  413:     */       }
/*  414: 560 */       j = this.q[(paramInt3 + 32 + m + 1)];
/*  415: 561 */       k = this.q[(paramInt3 + 32 + m + 1 + 16)];
/*  416: 562 */       i1 = (k * alphaTab[(n + 8)] & 0xFFFF) + (k * alphaTab[(n + 8)] >> 16);
/*  417:     */       
/*  418: 564 */       this.q[(paramInt3 + 32 + m + 1)] = (j + i1);
/*  419: 565 */       this.q[(paramInt3 + 32 + m + 1 + 16)] = (j - i1);
/*  420: 566 */       j = this.q[(paramInt3 + 32 + m + 2)];
/*  421: 567 */       k = this.q[(paramInt3 + 32 + m + 2 + 16)];
/*  422: 568 */       i1 = (k * alphaTab[(n + 16)] & 0xFFFF) + (k * alphaTab[(n + 16)] >> 16);
/*  423:     */       
/*  424: 570 */       this.q[(paramInt3 + 32 + m + 2)] = (j + i1);
/*  425: 571 */       this.q[(paramInt3 + 32 + m + 2 + 16)] = (j - i1);
/*  426: 572 */       j = this.q[(paramInt3 + 32 + m + 3)];
/*  427: 573 */       k = this.q[(paramInt3 + 32 + m + 3 + 16)];
/*  428: 574 */       i1 = (k * alphaTab[(n + 24)] & 0xFFFF) + (k * alphaTab[(n + 24)] >> 16);
/*  429:     */       
/*  430: 576 */       this.q[(paramInt3 + 32 + m + 3)] = (j + i1);
/*  431: 577 */       this.q[(paramInt3 + 32 + m + 3 + 16)] = (j - i1);m += 4;
/*  432:     */     }
/*  433: 579 */     j = this.q[paramInt3];
/*  434: 580 */     k = this.q[(paramInt3 + 32)];
/*  435: 581 */     this.q[paramInt3] = (j + k);
/*  436: 582 */     this.q[(paramInt3 + 32)] = (j - k);
/*  437: 583 */     m = 0;
/*  438: 583 */     for (n = 0; m < 32; n += 16)
/*  439:     */     {
/*  440: 585 */       if (m != 0)
/*  441:     */       {
/*  442: 586 */         j = this.q[(paramInt3 + m + 0)];
/*  443: 587 */         k = this.q[(paramInt3 + m + 0 + 32)];
/*  444: 588 */         i1 = (k * alphaTab[(n + 0)] & 0xFFFF) + (k * alphaTab[(n + 0)] >> 16);
/*  445:     */         
/*  446: 590 */         this.q[(paramInt3 + m + 0)] = (j + i1);
/*  447: 591 */         this.q[(paramInt3 + m + 0 + 32)] = (j - i1);
/*  448:     */       }
/*  449: 593 */       j = this.q[(paramInt3 + m + 1)];
/*  450: 594 */       k = this.q[(paramInt3 + m + 1 + 32)];
/*  451: 595 */       i1 = (k * alphaTab[(n + 4)] & 0xFFFF) + (k * alphaTab[(n + 4)] >> 16);
/*  452:     */       
/*  453: 597 */       this.q[(paramInt3 + m + 1)] = (j + i1);
/*  454: 598 */       this.q[(paramInt3 + m + 1 + 32)] = (j - i1);
/*  455: 599 */       j = this.q[(paramInt3 + m + 2)];
/*  456: 600 */       k = this.q[(paramInt3 + m + 2 + 32)];
/*  457: 601 */       i1 = (k * alphaTab[(n + 8)] & 0xFFFF) + (k * alphaTab[(n + 8)] >> 16);
/*  458:     */       
/*  459: 603 */       this.q[(paramInt3 + m + 2)] = (j + i1);
/*  460: 604 */       this.q[(paramInt3 + m + 2 + 32)] = (j - i1);
/*  461: 605 */       j = this.q[(paramInt3 + m + 3)];
/*  462: 606 */       k = this.q[(paramInt3 + m + 3 + 32)];
/*  463: 607 */       i1 = (k * alphaTab[(n + 12)] & 0xFFFF) + (k * alphaTab[(n + 12)] >> 16);
/*  464:     */       
/*  465: 609 */       this.q[(paramInt3 + m + 3)] = (j + i1);
/*  466: 610 */       this.q[(paramInt3 + m + 3 + 32)] = (j - i1);m += 4;
/*  467:     */     }
/*  468:     */   }
/*  469:     */   
/*  470: 614 */   private static final int[] pp8k = { 1, 6, 2, 3, 5, 7, 4, 1, 6, 2, 3 };
/*  471: 618 */   private static final int[] wbp = { 64, 96, 0, 32, 112, 80, 48, 16, 240, 176, 192, 128, 144, 208, 160, 224, 272, 288, 368, 320, 352, 336, 256, 304, 480, 384, 400, 496, 432, 464, 448, 416 };
/*  472:     */   
/*  473:     */   private final void oneRound(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
/*  474:     */   {
/*  475: 632 */     this.tA[0] = circularLeft(this.state[0], paramInt2);
/*  476: 633 */     this.tA[1] = circularLeft(this.state[1], paramInt2);
/*  477: 634 */     this.tA[2] = circularLeft(this.state[2], paramInt2);
/*  478: 635 */     this.tA[3] = circularLeft(this.state[3], paramInt2);
/*  479: 636 */     this.tA[4] = circularLeft(this.state[4], paramInt2);
/*  480: 637 */     this.tA[5] = circularLeft(this.state[5], paramInt2);
/*  481: 638 */     this.tA[6] = circularLeft(this.state[6], paramInt2);
/*  482: 639 */     this.tA[7] = circularLeft(this.state[7], paramInt2);
/*  483: 640 */     int i = this.state[24] + this.w[0] + ((this.state[8] ^ this.state[16]) & this.state[0] ^ this.state[16]);
/*  484:     */     
/*  485: 642 */     this.state[0] = (circularLeft(i, paramInt3) + this.tA[(pp8k[(paramInt1 + 0)] ^ 0x0)]);
/*  486: 643 */     this.state[24] = this.state[16];
/*  487: 644 */     this.state[16] = this.state[8];
/*  488: 645 */     this.state[8] = this.tA[0];
/*  489: 646 */     i = this.state[25] + this.w[1] + ((this.state[9] ^ this.state[17]) & this.state[1] ^ this.state[17]);
/*  490:     */     
/*  491: 648 */     this.state[1] = (circularLeft(i, paramInt3) + this.tA[(pp8k[(paramInt1 + 0)] ^ 0x1)]);
/*  492: 649 */     this.state[25] = this.state[17];
/*  493: 650 */     this.state[17] = this.state[9];
/*  494: 651 */     this.state[9] = this.tA[1];
/*  495: 652 */     i = this.state[26] + this.w[2] + ((this.state[10] ^ this.state[18]) & this.state[2] ^ this.state[18]);
/*  496:     */     
/*  497: 654 */     this.state[2] = (circularLeft(i, paramInt3) + this.tA[(pp8k[(paramInt1 + 0)] ^ 0x2)]);
/*  498: 655 */     this.state[26] = this.state[18];
/*  499: 656 */     this.state[18] = this.state[10];
/*  500: 657 */     this.state[10] = this.tA[2];
/*  501: 658 */     i = this.state[27] + this.w[3] + ((this.state[11] ^ this.state[19]) & this.state[3] ^ this.state[19]);
/*  502:     */     
/*  503: 660 */     this.state[3] = (circularLeft(i, paramInt3) + this.tA[(pp8k[(paramInt1 + 0)] ^ 0x3)]);
/*  504: 661 */     this.state[27] = this.state[19];
/*  505: 662 */     this.state[19] = this.state[11];
/*  506: 663 */     this.state[11] = this.tA[3];
/*  507: 664 */     i = this.state[28] + this.w[4] + ((this.state[12] ^ this.state[20]) & this.state[4] ^ this.state[20]);
/*  508:     */     
/*  509: 666 */     this.state[4] = (circularLeft(i, paramInt3) + this.tA[(pp8k[(paramInt1 + 0)] ^ 0x4)]);
/*  510: 667 */     this.state[28] = this.state[20];
/*  511: 668 */     this.state[20] = this.state[12];
/*  512: 669 */     this.state[12] = this.tA[4];
/*  513: 670 */     i = this.state[29] + this.w[5] + ((this.state[13] ^ this.state[21]) & this.state[5] ^ this.state[21]);
/*  514:     */     
/*  515: 672 */     this.state[5] = (circularLeft(i, paramInt3) + this.tA[(pp8k[(paramInt1 + 0)] ^ 0x5)]);
/*  516: 673 */     this.state[29] = this.state[21];
/*  517: 674 */     this.state[21] = this.state[13];
/*  518: 675 */     this.state[13] = this.tA[5];
/*  519: 676 */     i = this.state[30] + this.w[6] + ((this.state[14] ^ this.state[22]) & this.state[6] ^ this.state[22]);
/*  520:     */     
/*  521: 678 */     this.state[6] = (circularLeft(i, paramInt3) + this.tA[(pp8k[(paramInt1 + 0)] ^ 0x6)]);
/*  522: 679 */     this.state[30] = this.state[22];
/*  523: 680 */     this.state[22] = this.state[14];
/*  524: 681 */     this.state[14] = this.tA[6];
/*  525: 682 */     i = this.state[31] + this.w[7] + ((this.state[15] ^ this.state[23]) & this.state[7] ^ this.state[23]);
/*  526:     */     
/*  527: 684 */     this.state[7] = (circularLeft(i, paramInt3) + this.tA[(pp8k[(paramInt1 + 0)] ^ 0x7)]);
/*  528: 685 */     this.state[31] = this.state[23];
/*  529: 686 */     this.state[23] = this.state[15];
/*  530: 687 */     this.state[15] = this.tA[7];
/*  531:     */     
/*  532: 689 */     this.tA[0] = circularLeft(this.state[0], paramInt3);
/*  533: 690 */     this.tA[1] = circularLeft(this.state[1], paramInt3);
/*  534: 691 */     this.tA[2] = circularLeft(this.state[2], paramInt3);
/*  535: 692 */     this.tA[3] = circularLeft(this.state[3], paramInt3);
/*  536: 693 */     this.tA[4] = circularLeft(this.state[4], paramInt3);
/*  537: 694 */     this.tA[5] = circularLeft(this.state[5], paramInt3);
/*  538: 695 */     this.tA[6] = circularLeft(this.state[6], paramInt3);
/*  539: 696 */     this.tA[7] = circularLeft(this.state[7], paramInt3);
/*  540: 697 */     i = this.state[24] + this.w[8] + ((this.state[8] ^ this.state[16]) & this.state[0] ^ this.state[16]);
/*  541:     */     
/*  542: 699 */     this.state[0] = (circularLeft(i, paramInt4) + this.tA[(pp8k[(paramInt1 + 1)] ^ 0x0)]);
/*  543: 700 */     this.state[24] = this.state[16];
/*  544: 701 */     this.state[16] = this.state[8];
/*  545: 702 */     this.state[8] = this.tA[0];
/*  546: 703 */     i = this.state[25] + this.w[9] + ((this.state[9] ^ this.state[17]) & this.state[1] ^ this.state[17]);
/*  547:     */     
/*  548: 705 */     this.state[1] = (circularLeft(i, paramInt4) + this.tA[(pp8k[(paramInt1 + 1)] ^ 0x1)]);
/*  549: 706 */     this.state[25] = this.state[17];
/*  550: 707 */     this.state[17] = this.state[9];
/*  551: 708 */     this.state[9] = this.tA[1];
/*  552: 709 */     i = this.state[26] + this.w[10] + ((this.state[10] ^ this.state[18]) & this.state[2] ^ this.state[18]);
/*  553:     */     
/*  554: 711 */     this.state[2] = (circularLeft(i, paramInt4) + this.tA[(pp8k[(paramInt1 + 1)] ^ 0x2)]);
/*  555: 712 */     this.state[26] = this.state[18];
/*  556: 713 */     this.state[18] = this.state[10];
/*  557: 714 */     this.state[10] = this.tA[2];
/*  558: 715 */     i = this.state[27] + this.w[11] + ((this.state[11] ^ this.state[19]) & this.state[3] ^ this.state[19]);
/*  559:     */     
/*  560: 717 */     this.state[3] = (circularLeft(i, paramInt4) + this.tA[(pp8k[(paramInt1 + 1)] ^ 0x3)]);
/*  561: 718 */     this.state[27] = this.state[19];
/*  562: 719 */     this.state[19] = this.state[11];
/*  563: 720 */     this.state[11] = this.tA[3];
/*  564: 721 */     i = this.state[28] + this.w[12] + ((this.state[12] ^ this.state[20]) & this.state[4] ^ this.state[20]);
/*  565:     */     
/*  566: 723 */     this.state[4] = (circularLeft(i, paramInt4) + this.tA[(pp8k[(paramInt1 + 1)] ^ 0x4)]);
/*  567: 724 */     this.state[28] = this.state[20];
/*  568: 725 */     this.state[20] = this.state[12];
/*  569: 726 */     this.state[12] = this.tA[4];
/*  570: 727 */     i = this.state[29] + this.w[13] + ((this.state[13] ^ this.state[21]) & this.state[5] ^ this.state[21]);
/*  571:     */     
/*  572: 729 */     this.state[5] = (circularLeft(i, paramInt4) + this.tA[(pp8k[(paramInt1 + 1)] ^ 0x5)]);
/*  573: 730 */     this.state[29] = this.state[21];
/*  574: 731 */     this.state[21] = this.state[13];
/*  575: 732 */     this.state[13] = this.tA[5];
/*  576: 733 */     i = this.state[30] + this.w[14] + ((this.state[14] ^ this.state[22]) & this.state[6] ^ this.state[22]);
/*  577:     */     
/*  578: 735 */     this.state[6] = (circularLeft(i, paramInt4) + this.tA[(pp8k[(paramInt1 + 1)] ^ 0x6)]);
/*  579: 736 */     this.state[30] = this.state[22];
/*  580: 737 */     this.state[22] = this.state[14];
/*  581: 738 */     this.state[14] = this.tA[6];
/*  582: 739 */     i = this.state[31] + this.w[15] + ((this.state[15] ^ this.state[23]) & this.state[7] ^ this.state[23]);
/*  583:     */     
/*  584: 741 */     this.state[7] = (circularLeft(i, paramInt4) + this.tA[(pp8k[(paramInt1 + 1)] ^ 0x7)]);
/*  585: 742 */     this.state[31] = this.state[23];
/*  586: 743 */     this.state[23] = this.state[15];
/*  587: 744 */     this.state[15] = this.tA[7];
/*  588:     */     
/*  589: 746 */     this.tA[0] = circularLeft(this.state[0], paramInt4);
/*  590: 747 */     this.tA[1] = circularLeft(this.state[1], paramInt4);
/*  591: 748 */     this.tA[2] = circularLeft(this.state[2], paramInt4);
/*  592: 749 */     this.tA[3] = circularLeft(this.state[3], paramInt4);
/*  593: 750 */     this.tA[4] = circularLeft(this.state[4], paramInt4);
/*  594: 751 */     this.tA[5] = circularLeft(this.state[5], paramInt4);
/*  595: 752 */     this.tA[6] = circularLeft(this.state[6], paramInt4);
/*  596: 753 */     this.tA[7] = circularLeft(this.state[7], paramInt4);
/*  597: 754 */     i = this.state[24] + this.w[16] + ((this.state[8] ^ this.state[16]) & this.state[0] ^ this.state[16]);
/*  598:     */     
/*  599: 756 */     this.state[0] = (circularLeft(i, paramInt5) + this.tA[(pp8k[(paramInt1 + 2)] ^ 0x0)]);
/*  600: 757 */     this.state[24] = this.state[16];
/*  601: 758 */     this.state[16] = this.state[8];
/*  602: 759 */     this.state[8] = this.tA[0];
/*  603: 760 */     i = this.state[25] + this.w[17] + ((this.state[9] ^ this.state[17]) & this.state[1] ^ this.state[17]);
/*  604:     */     
/*  605: 762 */     this.state[1] = (circularLeft(i, paramInt5) + this.tA[(pp8k[(paramInt1 + 2)] ^ 0x1)]);
/*  606: 763 */     this.state[25] = this.state[17];
/*  607: 764 */     this.state[17] = this.state[9];
/*  608: 765 */     this.state[9] = this.tA[1];
/*  609: 766 */     i = this.state[26] + this.w[18] + ((this.state[10] ^ this.state[18]) & this.state[2] ^ this.state[18]);
/*  610:     */     
/*  611: 768 */     this.state[2] = (circularLeft(i, paramInt5) + this.tA[(pp8k[(paramInt1 + 2)] ^ 0x2)]);
/*  612: 769 */     this.state[26] = this.state[18];
/*  613: 770 */     this.state[18] = this.state[10];
/*  614: 771 */     this.state[10] = this.tA[2];
/*  615: 772 */     i = this.state[27] + this.w[19] + ((this.state[11] ^ this.state[19]) & this.state[3] ^ this.state[19]);
/*  616:     */     
/*  617: 774 */     this.state[3] = (circularLeft(i, paramInt5) + this.tA[(pp8k[(paramInt1 + 2)] ^ 0x3)]);
/*  618: 775 */     this.state[27] = this.state[19];
/*  619: 776 */     this.state[19] = this.state[11];
/*  620: 777 */     this.state[11] = this.tA[3];
/*  621: 778 */     i = this.state[28] + this.w[20] + ((this.state[12] ^ this.state[20]) & this.state[4] ^ this.state[20]);
/*  622:     */     
/*  623: 780 */     this.state[4] = (circularLeft(i, paramInt5) + this.tA[(pp8k[(paramInt1 + 2)] ^ 0x4)]);
/*  624: 781 */     this.state[28] = this.state[20];
/*  625: 782 */     this.state[20] = this.state[12];
/*  626: 783 */     this.state[12] = this.tA[4];
/*  627: 784 */     i = this.state[29] + this.w[21] + ((this.state[13] ^ this.state[21]) & this.state[5] ^ this.state[21]);
/*  628:     */     
/*  629: 786 */     this.state[5] = (circularLeft(i, paramInt5) + this.tA[(pp8k[(paramInt1 + 2)] ^ 0x5)]);
/*  630: 787 */     this.state[29] = this.state[21];
/*  631: 788 */     this.state[21] = this.state[13];
/*  632: 789 */     this.state[13] = this.tA[5];
/*  633: 790 */     i = this.state[30] + this.w[22] + ((this.state[14] ^ this.state[22]) & this.state[6] ^ this.state[22]);
/*  634:     */     
/*  635: 792 */     this.state[6] = (circularLeft(i, paramInt5) + this.tA[(pp8k[(paramInt1 + 2)] ^ 0x6)]);
/*  636: 793 */     this.state[30] = this.state[22];
/*  637: 794 */     this.state[22] = this.state[14];
/*  638: 795 */     this.state[14] = this.tA[6];
/*  639: 796 */     i = this.state[31] + this.w[23] + ((this.state[15] ^ this.state[23]) & this.state[7] ^ this.state[23]);
/*  640:     */     
/*  641: 798 */     this.state[7] = (circularLeft(i, paramInt5) + this.tA[(pp8k[(paramInt1 + 2)] ^ 0x7)]);
/*  642: 799 */     this.state[31] = this.state[23];
/*  643: 800 */     this.state[23] = this.state[15];
/*  644: 801 */     this.state[15] = this.tA[7];
/*  645:     */     
/*  646: 803 */     this.tA[0] = circularLeft(this.state[0], paramInt5);
/*  647: 804 */     this.tA[1] = circularLeft(this.state[1], paramInt5);
/*  648: 805 */     this.tA[2] = circularLeft(this.state[2], paramInt5);
/*  649: 806 */     this.tA[3] = circularLeft(this.state[3], paramInt5);
/*  650: 807 */     this.tA[4] = circularLeft(this.state[4], paramInt5);
/*  651: 808 */     this.tA[5] = circularLeft(this.state[5], paramInt5);
/*  652: 809 */     this.tA[6] = circularLeft(this.state[6], paramInt5);
/*  653: 810 */     this.tA[7] = circularLeft(this.state[7], paramInt5);
/*  654: 811 */     i = this.state[24] + this.w[24] + ((this.state[8] ^ this.state[16]) & this.state[0] ^ this.state[16]);
/*  655:     */     
/*  656: 813 */     this.state[0] = (circularLeft(i, paramInt2) + this.tA[(pp8k[(paramInt1 + 3)] ^ 0x0)]);
/*  657: 814 */     this.state[24] = this.state[16];
/*  658: 815 */     this.state[16] = this.state[8];
/*  659: 816 */     this.state[8] = this.tA[0];
/*  660: 817 */     i = this.state[25] + this.w[25] + ((this.state[9] ^ this.state[17]) & this.state[1] ^ this.state[17]);
/*  661:     */     
/*  662: 819 */     this.state[1] = (circularLeft(i, paramInt2) + this.tA[(pp8k[(paramInt1 + 3)] ^ 0x1)]);
/*  663: 820 */     this.state[25] = this.state[17];
/*  664: 821 */     this.state[17] = this.state[9];
/*  665: 822 */     this.state[9] = this.tA[1];
/*  666: 823 */     i = this.state[26] + this.w[26] + ((this.state[10] ^ this.state[18]) & this.state[2] ^ this.state[18]);
/*  667:     */     
/*  668: 825 */     this.state[2] = (circularLeft(i, paramInt2) + this.tA[(pp8k[(paramInt1 + 3)] ^ 0x2)]);
/*  669: 826 */     this.state[26] = this.state[18];
/*  670: 827 */     this.state[18] = this.state[10];
/*  671: 828 */     this.state[10] = this.tA[2];
/*  672: 829 */     i = this.state[27] + this.w[27] + ((this.state[11] ^ this.state[19]) & this.state[3] ^ this.state[19]);
/*  673:     */     
/*  674: 831 */     this.state[3] = (circularLeft(i, paramInt2) + this.tA[(pp8k[(paramInt1 + 3)] ^ 0x3)]);
/*  675: 832 */     this.state[27] = this.state[19];
/*  676: 833 */     this.state[19] = this.state[11];
/*  677: 834 */     this.state[11] = this.tA[3];
/*  678: 835 */     i = this.state[28] + this.w[28] + ((this.state[12] ^ this.state[20]) & this.state[4] ^ this.state[20]);
/*  679:     */     
/*  680: 837 */     this.state[4] = (circularLeft(i, paramInt2) + this.tA[(pp8k[(paramInt1 + 3)] ^ 0x4)]);
/*  681: 838 */     this.state[28] = this.state[20];
/*  682: 839 */     this.state[20] = this.state[12];
/*  683: 840 */     this.state[12] = this.tA[4];
/*  684: 841 */     i = this.state[29] + this.w[29] + ((this.state[13] ^ this.state[21]) & this.state[5] ^ this.state[21]);
/*  685:     */     
/*  686: 843 */     this.state[5] = (circularLeft(i, paramInt2) + this.tA[(pp8k[(paramInt1 + 3)] ^ 0x5)]);
/*  687: 844 */     this.state[29] = this.state[21];
/*  688: 845 */     this.state[21] = this.state[13];
/*  689: 846 */     this.state[13] = this.tA[5];
/*  690: 847 */     i = this.state[30] + this.w[30] + ((this.state[14] ^ this.state[22]) & this.state[6] ^ this.state[22]);
/*  691:     */     
/*  692: 849 */     this.state[6] = (circularLeft(i, paramInt2) + this.tA[(pp8k[(paramInt1 + 3)] ^ 0x6)]);
/*  693: 850 */     this.state[30] = this.state[22];
/*  694: 851 */     this.state[22] = this.state[14];
/*  695: 852 */     this.state[14] = this.tA[6];
/*  696: 853 */     i = this.state[31] + this.w[31] + ((this.state[15] ^ this.state[23]) & this.state[7] ^ this.state[23]);
/*  697:     */     
/*  698: 855 */     this.state[7] = (circularLeft(i, paramInt2) + this.tA[(pp8k[(paramInt1 + 3)] ^ 0x7)]);
/*  699: 856 */     this.state[31] = this.state[23];
/*  700: 857 */     this.state[23] = this.state[15];
/*  701: 858 */     this.state[15] = this.tA[7];
/*  702:     */     
/*  703: 860 */     this.tA[0] = circularLeft(this.state[0], paramInt2);
/*  704: 861 */     this.tA[1] = circularLeft(this.state[1], paramInt2);
/*  705: 862 */     this.tA[2] = circularLeft(this.state[2], paramInt2);
/*  706: 863 */     this.tA[3] = circularLeft(this.state[3], paramInt2);
/*  707: 864 */     this.tA[4] = circularLeft(this.state[4], paramInt2);
/*  708: 865 */     this.tA[5] = circularLeft(this.state[5], paramInt2);
/*  709: 866 */     this.tA[6] = circularLeft(this.state[6], paramInt2);
/*  710: 867 */     this.tA[7] = circularLeft(this.state[7], paramInt2);
/*  711: 868 */     i = this.state[24] + this.w[32] + (this.state[0] & this.state[8] | (this.state[0] | this.state[8]) & this.state[16]);
/*  712:     */     
/*  713:     */ 
/*  714: 871 */     this.state[0] = (circularLeft(i, paramInt3) + this.tA[(pp8k[(paramInt1 + 4)] ^ 0x0)]);
/*  715: 872 */     this.state[24] = this.state[16];
/*  716: 873 */     this.state[16] = this.state[8];
/*  717: 874 */     this.state[8] = this.tA[0];
/*  718: 875 */     i = this.state[25] + this.w[33] + (this.state[1] & this.state[9] | (this.state[1] | this.state[9]) & this.state[17]);
/*  719:     */     
/*  720:     */ 
/*  721: 878 */     this.state[1] = (circularLeft(i, paramInt3) + this.tA[(pp8k[(paramInt1 + 4)] ^ 0x1)]);
/*  722: 879 */     this.state[25] = this.state[17];
/*  723: 880 */     this.state[17] = this.state[9];
/*  724: 881 */     this.state[9] = this.tA[1];
/*  725: 882 */     i = this.state[26] + this.w[34] + (this.state[2] & this.state[10] | (this.state[2] | this.state[10]) & this.state[18]);
/*  726:     */     
/*  727:     */ 
/*  728: 885 */     this.state[2] = (circularLeft(i, paramInt3) + this.tA[(pp8k[(paramInt1 + 4)] ^ 0x2)]);
/*  729: 886 */     this.state[26] = this.state[18];
/*  730: 887 */     this.state[18] = this.state[10];
/*  731: 888 */     this.state[10] = this.tA[2];
/*  732: 889 */     i = this.state[27] + this.w[35] + (this.state[3] & this.state[11] | (this.state[3] | this.state[11]) & this.state[19]);
/*  733:     */     
/*  734:     */ 
/*  735: 892 */     this.state[3] = (circularLeft(i, paramInt3) + this.tA[(pp8k[(paramInt1 + 4)] ^ 0x3)]);
/*  736: 893 */     this.state[27] = this.state[19];
/*  737: 894 */     this.state[19] = this.state[11];
/*  738: 895 */     this.state[11] = this.tA[3];
/*  739: 896 */     i = this.state[28] + this.w[36] + (this.state[4] & this.state[12] | (this.state[4] | this.state[12]) & this.state[20]);
/*  740:     */     
/*  741:     */ 
/*  742: 899 */     this.state[4] = (circularLeft(i, paramInt3) + this.tA[(pp8k[(paramInt1 + 4)] ^ 0x4)]);
/*  743: 900 */     this.state[28] = this.state[20];
/*  744: 901 */     this.state[20] = this.state[12];
/*  745: 902 */     this.state[12] = this.tA[4];
/*  746: 903 */     i = this.state[29] + this.w[37] + (this.state[5] & this.state[13] | (this.state[5] | this.state[13]) & this.state[21]);
/*  747:     */     
/*  748:     */ 
/*  749: 906 */     this.state[5] = (circularLeft(i, paramInt3) + this.tA[(pp8k[(paramInt1 + 4)] ^ 0x5)]);
/*  750: 907 */     this.state[29] = this.state[21];
/*  751: 908 */     this.state[21] = this.state[13];
/*  752: 909 */     this.state[13] = this.tA[5];
/*  753: 910 */     i = this.state[30] + this.w[38] + (this.state[6] & this.state[14] | (this.state[6] | this.state[14]) & this.state[22]);
/*  754:     */     
/*  755:     */ 
/*  756: 913 */     this.state[6] = (circularLeft(i, paramInt3) + this.tA[(pp8k[(paramInt1 + 4)] ^ 0x6)]);
/*  757: 914 */     this.state[30] = this.state[22];
/*  758: 915 */     this.state[22] = this.state[14];
/*  759: 916 */     this.state[14] = this.tA[6];
/*  760: 917 */     i = this.state[31] + this.w[39] + (this.state[7] & this.state[15] | (this.state[7] | this.state[15]) & this.state[23]);
/*  761:     */     
/*  762:     */ 
/*  763: 920 */     this.state[7] = (circularLeft(i, paramInt3) + this.tA[(pp8k[(paramInt1 + 4)] ^ 0x7)]);
/*  764: 921 */     this.state[31] = this.state[23];
/*  765: 922 */     this.state[23] = this.state[15];
/*  766: 923 */     this.state[15] = this.tA[7];
/*  767:     */     
/*  768: 925 */     this.tA[0] = circularLeft(this.state[0], paramInt3);
/*  769: 926 */     this.tA[1] = circularLeft(this.state[1], paramInt3);
/*  770: 927 */     this.tA[2] = circularLeft(this.state[2], paramInt3);
/*  771: 928 */     this.tA[3] = circularLeft(this.state[3], paramInt3);
/*  772: 929 */     this.tA[4] = circularLeft(this.state[4], paramInt3);
/*  773: 930 */     this.tA[5] = circularLeft(this.state[5], paramInt3);
/*  774: 931 */     this.tA[6] = circularLeft(this.state[6], paramInt3);
/*  775: 932 */     this.tA[7] = circularLeft(this.state[7], paramInt3);
/*  776: 933 */     i = this.state[24] + this.w[40] + (this.state[0] & this.state[8] | (this.state[0] | this.state[8]) & this.state[16]);
/*  777:     */     
/*  778:     */ 
/*  779: 936 */     this.state[0] = (circularLeft(i, paramInt4) + this.tA[(pp8k[(paramInt1 + 5)] ^ 0x0)]);
/*  780: 937 */     this.state[24] = this.state[16];
/*  781: 938 */     this.state[16] = this.state[8];
/*  782: 939 */     this.state[8] = this.tA[0];
/*  783: 940 */     i = this.state[25] + this.w[41] + (this.state[1] & this.state[9] | (this.state[1] | this.state[9]) & this.state[17]);
/*  784:     */     
/*  785:     */ 
/*  786: 943 */     this.state[1] = (circularLeft(i, paramInt4) + this.tA[(pp8k[(paramInt1 + 5)] ^ 0x1)]);
/*  787: 944 */     this.state[25] = this.state[17];
/*  788: 945 */     this.state[17] = this.state[9];
/*  789: 946 */     this.state[9] = this.tA[1];
/*  790: 947 */     i = this.state[26] + this.w[42] + (this.state[2] & this.state[10] | (this.state[2] | this.state[10]) & this.state[18]);
/*  791:     */     
/*  792:     */ 
/*  793: 950 */     this.state[2] = (circularLeft(i, paramInt4) + this.tA[(pp8k[(paramInt1 + 5)] ^ 0x2)]);
/*  794: 951 */     this.state[26] = this.state[18];
/*  795: 952 */     this.state[18] = this.state[10];
/*  796: 953 */     this.state[10] = this.tA[2];
/*  797: 954 */     i = this.state[27] + this.w[43] + (this.state[3] & this.state[11] | (this.state[3] | this.state[11]) & this.state[19]);
/*  798:     */     
/*  799:     */ 
/*  800: 957 */     this.state[3] = (circularLeft(i, paramInt4) + this.tA[(pp8k[(paramInt1 + 5)] ^ 0x3)]);
/*  801: 958 */     this.state[27] = this.state[19];
/*  802: 959 */     this.state[19] = this.state[11];
/*  803: 960 */     this.state[11] = this.tA[3];
/*  804: 961 */     i = this.state[28] + this.w[44] + (this.state[4] & this.state[12] | (this.state[4] | this.state[12]) & this.state[20]);
/*  805:     */     
/*  806:     */ 
/*  807: 964 */     this.state[4] = (circularLeft(i, paramInt4) + this.tA[(pp8k[(paramInt1 + 5)] ^ 0x4)]);
/*  808: 965 */     this.state[28] = this.state[20];
/*  809: 966 */     this.state[20] = this.state[12];
/*  810: 967 */     this.state[12] = this.tA[4];
/*  811: 968 */     i = this.state[29] + this.w[45] + (this.state[5] & this.state[13] | (this.state[5] | this.state[13]) & this.state[21]);
/*  812:     */     
/*  813:     */ 
/*  814: 971 */     this.state[5] = (circularLeft(i, paramInt4) + this.tA[(pp8k[(paramInt1 + 5)] ^ 0x5)]);
/*  815: 972 */     this.state[29] = this.state[21];
/*  816: 973 */     this.state[21] = this.state[13];
/*  817: 974 */     this.state[13] = this.tA[5];
/*  818: 975 */     i = this.state[30] + this.w[46] + (this.state[6] & this.state[14] | (this.state[6] | this.state[14]) & this.state[22]);
/*  819:     */     
/*  820:     */ 
/*  821: 978 */     this.state[6] = (circularLeft(i, paramInt4) + this.tA[(pp8k[(paramInt1 + 5)] ^ 0x6)]);
/*  822: 979 */     this.state[30] = this.state[22];
/*  823: 980 */     this.state[22] = this.state[14];
/*  824: 981 */     this.state[14] = this.tA[6];
/*  825: 982 */     i = this.state[31] + this.w[47] + (this.state[7] & this.state[15] | (this.state[7] | this.state[15]) & this.state[23]);
/*  826:     */     
/*  827:     */ 
/*  828: 985 */     this.state[7] = (circularLeft(i, paramInt4) + this.tA[(pp8k[(paramInt1 + 5)] ^ 0x7)]);
/*  829: 986 */     this.state[31] = this.state[23];
/*  830: 987 */     this.state[23] = this.state[15];
/*  831: 988 */     this.state[15] = this.tA[7];
/*  832:     */     
/*  833: 990 */     this.tA[0] = circularLeft(this.state[0], paramInt4);
/*  834: 991 */     this.tA[1] = circularLeft(this.state[1], paramInt4);
/*  835: 992 */     this.tA[2] = circularLeft(this.state[2], paramInt4);
/*  836: 993 */     this.tA[3] = circularLeft(this.state[3], paramInt4);
/*  837: 994 */     this.tA[4] = circularLeft(this.state[4], paramInt4);
/*  838: 995 */     this.tA[5] = circularLeft(this.state[5], paramInt4);
/*  839: 996 */     this.tA[6] = circularLeft(this.state[6], paramInt4);
/*  840: 997 */     this.tA[7] = circularLeft(this.state[7], paramInt4);
/*  841: 998 */     i = this.state[24] + this.w[48] + (this.state[0] & this.state[8] | (this.state[0] | this.state[8]) & this.state[16]);
/*  842:     */     
/*  843:     */ 
/*  844:1001 */     this.state[0] = (circularLeft(i, paramInt5) + this.tA[(pp8k[(paramInt1 + 6)] ^ 0x0)]);
/*  845:1002 */     this.state[24] = this.state[16];
/*  846:1003 */     this.state[16] = this.state[8];
/*  847:1004 */     this.state[8] = this.tA[0];
/*  848:1005 */     i = this.state[25] + this.w[49] + (this.state[1] & this.state[9] | (this.state[1] | this.state[9]) & this.state[17]);
/*  849:     */     
/*  850:     */ 
/*  851:1008 */     this.state[1] = (circularLeft(i, paramInt5) + this.tA[(pp8k[(paramInt1 + 6)] ^ 0x1)]);
/*  852:1009 */     this.state[25] = this.state[17];
/*  853:1010 */     this.state[17] = this.state[9];
/*  854:1011 */     this.state[9] = this.tA[1];
/*  855:1012 */     i = this.state[26] + this.w[50] + (this.state[2] & this.state[10] | (this.state[2] | this.state[10]) & this.state[18]);
/*  856:     */     
/*  857:     */ 
/*  858:1015 */     this.state[2] = (circularLeft(i, paramInt5) + this.tA[(pp8k[(paramInt1 + 6)] ^ 0x2)]);
/*  859:1016 */     this.state[26] = this.state[18];
/*  860:1017 */     this.state[18] = this.state[10];
/*  861:1018 */     this.state[10] = this.tA[2];
/*  862:1019 */     i = this.state[27] + this.w[51] + (this.state[3] & this.state[11] | (this.state[3] | this.state[11]) & this.state[19]);
/*  863:     */     
/*  864:     */ 
/*  865:1022 */     this.state[3] = (circularLeft(i, paramInt5) + this.tA[(pp8k[(paramInt1 + 6)] ^ 0x3)]);
/*  866:1023 */     this.state[27] = this.state[19];
/*  867:1024 */     this.state[19] = this.state[11];
/*  868:1025 */     this.state[11] = this.tA[3];
/*  869:1026 */     i = this.state[28] + this.w[52] + (this.state[4] & this.state[12] | (this.state[4] | this.state[12]) & this.state[20]);
/*  870:     */     
/*  871:     */ 
/*  872:1029 */     this.state[4] = (circularLeft(i, paramInt5) + this.tA[(pp8k[(paramInt1 + 6)] ^ 0x4)]);
/*  873:1030 */     this.state[28] = this.state[20];
/*  874:1031 */     this.state[20] = this.state[12];
/*  875:1032 */     this.state[12] = this.tA[4];
/*  876:1033 */     i = this.state[29] + this.w[53] + (this.state[5] & this.state[13] | (this.state[5] | this.state[13]) & this.state[21]);
/*  877:     */     
/*  878:     */ 
/*  879:1036 */     this.state[5] = (circularLeft(i, paramInt5) + this.tA[(pp8k[(paramInt1 + 6)] ^ 0x5)]);
/*  880:1037 */     this.state[29] = this.state[21];
/*  881:1038 */     this.state[21] = this.state[13];
/*  882:1039 */     this.state[13] = this.tA[5];
/*  883:1040 */     i = this.state[30] + this.w[54] + (this.state[6] & this.state[14] | (this.state[6] | this.state[14]) & this.state[22]);
/*  884:     */     
/*  885:     */ 
/*  886:1043 */     this.state[6] = (circularLeft(i, paramInt5) + this.tA[(pp8k[(paramInt1 + 6)] ^ 0x6)]);
/*  887:1044 */     this.state[30] = this.state[22];
/*  888:1045 */     this.state[22] = this.state[14];
/*  889:1046 */     this.state[14] = this.tA[6];
/*  890:1047 */     i = this.state[31] + this.w[55] + (this.state[7] & this.state[15] | (this.state[7] | this.state[15]) & this.state[23]);
/*  891:     */     
/*  892:     */ 
/*  893:1050 */     this.state[7] = (circularLeft(i, paramInt5) + this.tA[(pp8k[(paramInt1 + 6)] ^ 0x7)]);
/*  894:1051 */     this.state[31] = this.state[23];
/*  895:1052 */     this.state[23] = this.state[15];
/*  896:1053 */     this.state[15] = this.tA[7];
/*  897:     */     
/*  898:1055 */     this.tA[0] = circularLeft(this.state[0], paramInt5);
/*  899:1056 */     this.tA[1] = circularLeft(this.state[1], paramInt5);
/*  900:1057 */     this.tA[2] = circularLeft(this.state[2], paramInt5);
/*  901:1058 */     this.tA[3] = circularLeft(this.state[3], paramInt5);
/*  902:1059 */     this.tA[4] = circularLeft(this.state[4], paramInt5);
/*  903:1060 */     this.tA[5] = circularLeft(this.state[5], paramInt5);
/*  904:1061 */     this.tA[6] = circularLeft(this.state[6], paramInt5);
/*  905:1062 */     this.tA[7] = circularLeft(this.state[7], paramInt5);
/*  906:1063 */     i = this.state[24] + this.w[56] + (this.state[0] & this.state[8] | (this.state[0] | this.state[8]) & this.state[16]);
/*  907:     */     
/*  908:     */ 
/*  909:1066 */     this.state[0] = (circularLeft(i, paramInt2) + this.tA[(pp8k[(paramInt1 + 7)] ^ 0x0)]);
/*  910:1067 */     this.state[24] = this.state[16];
/*  911:1068 */     this.state[16] = this.state[8];
/*  912:1069 */     this.state[8] = this.tA[0];
/*  913:1070 */     i = this.state[25] + this.w[57] + (this.state[1] & this.state[9] | (this.state[1] | this.state[9]) & this.state[17]);
/*  914:     */     
/*  915:     */ 
/*  916:1073 */     this.state[1] = (circularLeft(i, paramInt2) + this.tA[(pp8k[(paramInt1 + 7)] ^ 0x1)]);
/*  917:1074 */     this.state[25] = this.state[17];
/*  918:1075 */     this.state[17] = this.state[9];
/*  919:1076 */     this.state[9] = this.tA[1];
/*  920:1077 */     i = this.state[26] + this.w[58] + (this.state[2] & this.state[10] | (this.state[2] | this.state[10]) & this.state[18]);
/*  921:     */     
/*  922:     */ 
/*  923:1080 */     this.state[2] = (circularLeft(i, paramInt2) + this.tA[(pp8k[(paramInt1 + 7)] ^ 0x2)]);
/*  924:1081 */     this.state[26] = this.state[18];
/*  925:1082 */     this.state[18] = this.state[10];
/*  926:1083 */     this.state[10] = this.tA[2];
/*  927:1084 */     i = this.state[27] + this.w[59] + (this.state[3] & this.state[11] | (this.state[3] | this.state[11]) & this.state[19]);
/*  928:     */     
/*  929:     */ 
/*  930:1087 */     this.state[3] = (circularLeft(i, paramInt2) + this.tA[(pp8k[(paramInt1 + 7)] ^ 0x3)]);
/*  931:1088 */     this.state[27] = this.state[19];
/*  932:1089 */     this.state[19] = this.state[11];
/*  933:1090 */     this.state[11] = this.tA[3];
/*  934:1091 */     i = this.state[28] + this.w[60] + (this.state[4] & this.state[12] | (this.state[4] | this.state[12]) & this.state[20]);
/*  935:     */     
/*  936:     */ 
/*  937:1094 */     this.state[4] = (circularLeft(i, paramInt2) + this.tA[(pp8k[(paramInt1 + 7)] ^ 0x4)]);
/*  938:1095 */     this.state[28] = this.state[20];
/*  939:1096 */     this.state[20] = this.state[12];
/*  940:1097 */     this.state[12] = this.tA[4];
/*  941:1098 */     i = this.state[29] + this.w[61] + (this.state[5] & this.state[13] | (this.state[5] | this.state[13]) & this.state[21]);
/*  942:     */     
/*  943:     */ 
/*  944:1101 */     this.state[5] = (circularLeft(i, paramInt2) + this.tA[(pp8k[(paramInt1 + 7)] ^ 0x5)]);
/*  945:1102 */     this.state[29] = this.state[21];
/*  946:1103 */     this.state[21] = this.state[13];
/*  947:1104 */     this.state[13] = this.tA[5];
/*  948:1105 */     i = this.state[30] + this.w[62] + (this.state[6] & this.state[14] | (this.state[6] | this.state[14]) & this.state[22]);
/*  949:     */     
/*  950:     */ 
/*  951:1108 */     this.state[6] = (circularLeft(i, paramInt2) + this.tA[(pp8k[(paramInt1 + 7)] ^ 0x6)]);
/*  952:1109 */     this.state[30] = this.state[22];
/*  953:1110 */     this.state[22] = this.state[14];
/*  954:1111 */     this.state[14] = this.tA[6];
/*  955:1112 */     i = this.state[31] + this.w[63] + (this.state[7] & this.state[15] | (this.state[7] | this.state[15]) & this.state[23]);
/*  956:     */     
/*  957:     */ 
/*  958:1115 */     this.state[7] = (circularLeft(i, paramInt2) + this.tA[(pp8k[(paramInt1 + 7)] ^ 0x7)]);
/*  959:1116 */     this.state[31] = this.state[23];
/*  960:1117 */     this.state[23] = this.state[15];
/*  961:1118 */     this.state[15] = this.tA[7];
/*  962:     */   }
/*  963:     */   
/*  964:     */   private final void compress(byte[] paramArrayOfByte, boolean paramBoolean)
/*  965:     */   {
/*  966:1124 */     fft64(paramArrayOfByte, 0, 4, 0);
/*  967:1125 */     fft64(paramArrayOfByte, 2, 4, 64);
/*  968:1126 */     int j = this.q[0];
/*  969:1127 */     int k = this.q[64];
/*  970:1128 */     this.q[0] = (j + k);
/*  971:1129 */     this.q[64] = (j - k);
/*  972:1130 */     int m = 0;
/*  973:1130 */     for (int n = 0; m < 64; n += 8)
/*  974:     */     {
/*  975:1132 */       if (m != 0)
/*  976:     */       {
/*  977:1133 */         j = this.q[(0 + m + 0)];
/*  978:1134 */         k = this.q[(0 + m + 0 + 64)];
/*  979:1135 */         i1 = (k * alphaTab[(n + 0)] & 0xFFFF) + (k * alphaTab[(n + 0)] >> 16);
/*  980:     */         
/*  981:1137 */         this.q[(0 + m + 0)] = (j + i1);
/*  982:1138 */         this.q[(0 + m + 0 + 64)] = (j - i1);
/*  983:     */       }
/*  984:1140 */       j = this.q[(0 + m + 1)];
/*  985:1141 */       k = this.q[(0 + m + 1 + 64)];
/*  986:1142 */       i1 = (k * alphaTab[(n + 2)] & 0xFFFF) + (k * alphaTab[(n + 2)] >> 16);
/*  987:     */       
/*  988:1144 */       this.q[(0 + m + 1)] = (j + i1);
/*  989:1145 */       this.q[(0 + m + 1 + 64)] = (j - i1);
/*  990:1146 */       j = this.q[(0 + m + 2)];
/*  991:1147 */       k = this.q[(0 + m + 2 + 64)];
/*  992:1148 */       i1 = (k * alphaTab[(n + 4)] & 0xFFFF) + (k * alphaTab[(n + 4)] >> 16);
/*  993:     */       
/*  994:1150 */       this.q[(0 + m + 2)] = (j + i1);
/*  995:1151 */       this.q[(0 + m + 2 + 64)] = (j - i1);
/*  996:1152 */       j = this.q[(0 + m + 3)];
/*  997:1153 */       k = this.q[(0 + m + 3 + 64)];
/*  998:1154 */       i1 = (k * alphaTab[(n + 6)] & 0xFFFF) + (k * alphaTab[(n + 6)] >> 16);
/*  999:     */       
/* 1000:1156 */       this.q[(0 + m + 3)] = (j + i1);
/* 1001:1157 */       this.q[(0 + m + 3 + 64)] = (j - i1);m += 4;
/* 1002:     */     }
/* 1003:1159 */     fft64(paramArrayOfByte, 1, 4, 128);
/* 1004:1160 */     fft64(paramArrayOfByte, 3, 4, 192);
/* 1005:1161 */     j = this.q[''];
/* 1006:1162 */     k = this.q['À'];
/* 1007:1163 */     this.q[''] = (j + k);
/* 1008:1164 */     this.q['À'] = (j - k);
/* 1009:1165 */     m = 0;
/* 1010:1165 */     for (n = 0; m < 64; n += 8)
/* 1011:     */     {
/* 1012:1167 */       if (m != 0)
/* 1013:     */       {
/* 1014:1168 */         j = this.q[(128 + m + 0)];
/* 1015:1169 */         k = this.q[(128 + m + 0 + 64)];
/* 1016:1170 */         i1 = (k * alphaTab[(n + 0)] & 0xFFFF) + (k * alphaTab[(n + 0)] >> 16);
/* 1017:     */         
/* 1018:1172 */         this.q[(128 + m + 0)] = (j + i1);
/* 1019:1173 */         this.q[(128 + m + 0 + 64)] = (j - i1);
/* 1020:     */       }
/* 1021:1175 */       j = this.q[(128 + m + 1)];
/* 1022:1176 */       k = this.q[(128 + m + 1 + 64)];
/* 1023:1177 */       i1 = (k * alphaTab[(n + 2)] & 0xFFFF) + (k * alphaTab[(n + 2)] >> 16);
/* 1024:     */       
/* 1025:1179 */       this.q[(128 + m + 1)] = (j + i1);
/* 1026:1180 */       this.q[(128 + m + 1 + 64)] = (j - i1);
/* 1027:1181 */       j = this.q[(128 + m + 2)];
/* 1028:1182 */       k = this.q[(128 + m + 2 + 64)];
/* 1029:1183 */       i1 = (k * alphaTab[(n + 4)] & 0xFFFF) + (k * alphaTab[(n + 4)] >> 16);
/* 1030:     */       
/* 1031:1185 */       this.q[(128 + m + 2)] = (j + i1);
/* 1032:1186 */       this.q[(128 + m + 2 + 64)] = (j - i1);
/* 1033:1187 */       j = this.q[(128 + m + 3)];
/* 1034:1188 */       k = this.q[(128 + m + 3 + 64)];
/* 1035:1189 */       i1 = (k * alphaTab[(n + 6)] & 0xFFFF) + (k * alphaTab[(n + 6)] >> 16);
/* 1036:     */       
/* 1037:1191 */       this.q[(128 + m + 3)] = (j + i1);
/* 1038:1192 */       this.q[(128 + m + 3 + 64)] = (j - i1);m += 4;
/* 1039:     */     }
/* 1040:1194 */     j = this.q[0];
/* 1041:1195 */     k = this.q[''];
/* 1042:1196 */     this.q[0] = (j + k);
/* 1043:1197 */     this.q[''] = (j - k);
/* 1044:1198 */     m = 0;
/* 1045:1198 */     for (n = 0; m < 128; n += 4)
/* 1046:     */     {
/* 1047:1200 */       if (m != 0)
/* 1048:     */       {
/* 1049:1201 */         j = this.q[(0 + m + 0)];
/* 1050:1202 */         k = this.q[(0 + m + 0 + 128)];
/* 1051:1203 */         i1 = (k * alphaTab[(n + 0)] & 0xFFFF) + (k * alphaTab[(n + 0)] >> 16);
/* 1052:     */         
/* 1053:1205 */         this.q[(0 + m + 0)] = (j + i1);
/* 1054:1206 */         this.q[(0 + m + 0 + 128)] = (j - i1);
/* 1055:     */       }
/* 1056:1208 */       j = this.q[(0 + m + 1)];
/* 1057:1209 */       k = this.q[(0 + m + 1 + 128)];
/* 1058:1210 */       i1 = (k * alphaTab[(n + 1)] & 0xFFFF) + (k * alphaTab[(n + 1)] >> 16);
/* 1059:     */       
/* 1060:1212 */       this.q[(0 + m + 1)] = (j + i1);
/* 1061:1213 */       this.q[(0 + m + 1 + 128)] = (j - i1);
/* 1062:1214 */       j = this.q[(0 + m + 2)];
/* 1063:1215 */       k = this.q[(0 + m + 2 + 128)];
/* 1064:1216 */       i1 = (k * alphaTab[(n + 2)] & 0xFFFF) + (k * alphaTab[(n + 2)] >> 16);
/* 1065:     */       
/* 1066:1218 */       this.q[(0 + m + 2)] = (j + i1);
/* 1067:1219 */       this.q[(0 + m + 2 + 128)] = (j - i1);
/* 1068:1220 */       j = this.q[(0 + m + 3)];
/* 1069:1221 */       k = this.q[(0 + m + 3 + 128)];
/* 1070:1222 */       i1 = (k * alphaTab[(n + 3)] & 0xFFFF) + (k * alphaTab[(n + 3)] >> 16);
/* 1071:     */       
/* 1072:1224 */       this.q[(0 + m + 3)] = (j + i1);
/* 1073:1225 */       this.q[(0 + m + 3 + 128)] = (j - i1);m += 4;
/* 1074:     */     }
/* 1075:1227 */     if (paramBoolean) {
/* 1076:1228 */       for (m = 0; m < 256; m++)
/* 1077:     */       {
/* 1078:1229 */         n = this.q[m] + yoffF[m];
/* 1079:1230 */         n = (n & 0xFFFF) + (n >> 16);
/* 1080:1231 */         n = (n & 0xFF) - (n >> 8);
/* 1081:1232 */         n = (n & 0xFF) - (n >> 8);
/* 1082:1233 */         this.q[m] = (n <= 128 ? n : n - 257);
/* 1083:     */       }
/* 1084:     */     } else {
/* 1085:1236 */       for (m = 0; m < 256; m++)
/* 1086:     */       {
/* 1087:1237 */         n = this.q[m] + yoffN[m];
/* 1088:1238 */         n = (n & 0xFFFF) + (n >> 16);
/* 1089:1239 */         n = (n & 0xFF) - (n >> 8);
/* 1090:1240 */         n = (n & 0xFF) - (n >> 8);
/* 1091:1241 */         this.q[m] = (n <= 128 ? n : n - 257);
/* 1092:     */       }
/* 1093:     */     }
/* 1094:1245 */     System.arraycopy(this.state, 0, this.tmpState, 0, 32);
/* 1095:1247 */     for (m = 0; m < 32; m += 8)
/* 1096:     */     {
/* 1097:1248 */       this.state[(m + 0)] ^= decodeLEInt(paramArrayOfByte, 4 * (m + 0));
/* 1098:1249 */       this.state[(m + 1)] ^= decodeLEInt(paramArrayOfByte, 4 * (m + 1));
/* 1099:1250 */       this.state[(m + 2)] ^= decodeLEInt(paramArrayOfByte, 4 * (m + 2));
/* 1100:1251 */       this.state[(m + 3)] ^= decodeLEInt(paramArrayOfByte, 4 * (m + 3));
/* 1101:1252 */       this.state[(m + 4)] ^= decodeLEInt(paramArrayOfByte, 4 * (m + 4));
/* 1102:1253 */       this.state[(m + 5)] ^= decodeLEInt(paramArrayOfByte, 4 * (m + 5));
/* 1103:1254 */       this.state[(m + 6)] ^= decodeLEInt(paramArrayOfByte, 4 * (m + 6));
/* 1104:1255 */       this.state[(m + 7)] ^= decodeLEInt(paramArrayOfByte, 4 * (m + 7));
/* 1105:     */     }
/* 1106:1257 */     for (m = 0; m < 64; m += 8)
/* 1107:     */     {
/* 1108:1258 */       n = wbp[((m >> 3) + 0)];
/* 1109:1259 */       this.w[(m + 0)] = ((this.q[(n + 0 + 0)] * 185 & 0xFFFF) + (this.q[(n + 0 + 1)] * 185 << 16));
/* 1110:     */       
/* 1111:1261 */       this.w[(m + 1)] = ((this.q[(n + 2 + 0)] * 185 & 0xFFFF) + (this.q[(n + 2 + 1)] * 185 << 16));
/* 1112:     */       
/* 1113:1263 */       this.w[(m + 2)] = ((this.q[(n + 4 + 0)] * 185 & 0xFFFF) + (this.q[(n + 4 + 1)] * 185 << 16));
/* 1114:     */       
/* 1115:1265 */       this.w[(m + 3)] = ((this.q[(n + 6 + 0)] * 185 & 0xFFFF) + (this.q[(n + 6 + 1)] * 185 << 16));
/* 1116:     */       
/* 1117:1267 */       this.w[(m + 4)] = ((this.q[(n + 8 + 0)] * 185 & 0xFFFF) + (this.q[(n + 8 + 1)] * 185 << 16));
/* 1118:     */       
/* 1119:1269 */       this.w[(m + 5)] = ((this.q[(n + 10 + 0)] * 185 & 0xFFFF) + (this.q[(n + 10 + 1)] * 185 << 16));
/* 1120:     */       
/* 1121:1271 */       this.w[(m + 6)] = ((this.q[(n + 12 + 0)] * 185 & 0xFFFF) + (this.q[(n + 12 + 1)] * 185 << 16));
/* 1122:     */       
/* 1123:1273 */       this.w[(m + 7)] = ((this.q[(n + 14 + 0)] * 185 & 0xFFFF) + (this.q[(n + 14 + 1)] * 185 << 16));
/* 1124:     */     }
/* 1125:1276 */     oneRound(0, 3, 23, 17, 27);
/* 1126:1277 */     for (m = 0; m < 64; m += 8)
/* 1127:     */     {
/* 1128:1278 */       n = wbp[((m >> 3) + 8)];
/* 1129:1279 */       this.w[(m + 0)] = ((this.q[(n + 0 + 0)] * 185 & 0xFFFF) + (this.q[(n + 0 + 1)] * 185 << 16));
/* 1130:     */       
/* 1131:1281 */       this.w[(m + 1)] = ((this.q[(n + 2 + 0)] * 185 & 0xFFFF) + (this.q[(n + 2 + 1)] * 185 << 16));
/* 1132:     */       
/* 1133:1283 */       this.w[(m + 2)] = ((this.q[(n + 4 + 0)] * 185 & 0xFFFF) + (this.q[(n + 4 + 1)] * 185 << 16));
/* 1134:     */       
/* 1135:1285 */       this.w[(m + 3)] = ((this.q[(n + 6 + 0)] * 185 & 0xFFFF) + (this.q[(n + 6 + 1)] * 185 << 16));
/* 1136:     */       
/* 1137:1287 */       this.w[(m + 4)] = ((this.q[(n + 8 + 0)] * 185 & 0xFFFF) + (this.q[(n + 8 + 1)] * 185 << 16));
/* 1138:     */       
/* 1139:1289 */       this.w[(m + 5)] = ((this.q[(n + 10 + 0)] * 185 & 0xFFFF) + (this.q[(n + 10 + 1)] * 185 << 16));
/* 1140:     */       
/* 1141:1291 */       this.w[(m + 6)] = ((this.q[(n + 12 + 0)] * 185 & 0xFFFF) + (this.q[(n + 12 + 1)] * 185 << 16));
/* 1142:     */       
/* 1143:1293 */       this.w[(m + 7)] = ((this.q[(n + 14 + 0)] * 185 & 0xFFFF) + (this.q[(n + 14 + 1)] * 185 << 16));
/* 1144:     */     }
/* 1145:1296 */     oneRound(1, 28, 19, 22, 7);
/* 1146:1297 */     for (m = 0; m < 64; m += 8)
/* 1147:     */     {
/* 1148:1298 */       n = wbp[((m >> 3) + 16)];
/* 1149:1299 */       this.w[(m + 0)] = ((this.q[(n + 0 + -256)] * 233 & 0xFFFF) + (this.q[(n + 0 + -128)] * 233 << 16));
/* 1150:     */       
/* 1151:1301 */       this.w[(m + 1)] = ((this.q[(n + 2 + -256)] * 233 & 0xFFFF) + (this.q[(n + 2 + -128)] * 233 << 16));
/* 1152:     */       
/* 1153:1303 */       this.w[(m + 2)] = ((this.q[(n + 4 + -256)] * 233 & 0xFFFF) + (this.q[(n + 4 + -128)] * 233 << 16));
/* 1154:     */       
/* 1155:1305 */       this.w[(m + 3)] = ((this.q[(n + 6 + -256)] * 233 & 0xFFFF) + (this.q[(n + 6 + -128)] * 233 << 16));
/* 1156:     */       
/* 1157:1307 */       this.w[(m + 4)] = ((this.q[(n + 8 + -256)] * 233 & 0xFFFF) + (this.q[(n + 8 + -128)] * 233 << 16));
/* 1158:     */       
/* 1159:1309 */       this.w[(m + 5)] = ((this.q[(n + 10 + -256)] * 233 & 0xFFFF) + (this.q[(n + 10 + -128)] * 233 << 16));
/* 1160:     */       
/* 1161:1311 */       this.w[(m + 6)] = ((this.q[(n + 12 + -256)] * 233 & 0xFFFF) + (this.q[(n + 12 + -128)] * 233 << 16));
/* 1162:     */       
/* 1163:1313 */       this.w[(m + 7)] = ((this.q[(n + 14 + -256)] * 233 & 0xFFFF) + (this.q[(n + 14 + -128)] * 233 << 16));
/* 1164:     */     }
/* 1165:1316 */     oneRound(2, 29, 9, 15, 5);
/* 1166:1317 */     for (m = 0; m < 64; m += 8)
/* 1167:     */     {
/* 1168:1318 */       n = wbp[((m >> 3) + 24)];
/* 1169:1319 */       this.w[(m + 0)] = ((this.q[(n + 0 + -383)] * 233 & 0xFFFF) + (this.q[(n + 0 + -255)] * 233 << 16));
/* 1170:     */       
/* 1171:1321 */       this.w[(m + 1)] = ((this.q[(n + 2 + -383)] * 233 & 0xFFFF) + (this.q[(n + 2 + -255)] * 233 << 16));
/* 1172:     */       
/* 1173:1323 */       this.w[(m + 2)] = ((this.q[(n + 4 + -383)] * 233 & 0xFFFF) + (this.q[(n + 4 + -255)] * 233 << 16));
/* 1174:     */       
/* 1175:1325 */       this.w[(m + 3)] = ((this.q[(n + 6 + -383)] * 233 & 0xFFFF) + (this.q[(n + 6 + -255)] * 233 << 16));
/* 1176:     */       
/* 1177:1327 */       this.w[(m + 4)] = ((this.q[(n + 8 + -383)] * 233 & 0xFFFF) + (this.q[(n + 8 + -255)] * 233 << 16));
/* 1178:     */       
/* 1179:1329 */       this.w[(m + 5)] = ((this.q[(n + 10 + -383)] * 233 & 0xFFFF) + (this.q[(n + 10 + -255)] * 233 << 16));
/* 1180:     */       
/* 1181:1331 */       this.w[(m + 6)] = ((this.q[(n + 12 + -383)] * 233 & 0xFFFF) + (this.q[(n + 12 + -255)] * 233 << 16));
/* 1182:     */       
/* 1183:1333 */       this.w[(m + 7)] = ((this.q[(n + 14 + -383)] * 233 & 0xFFFF) + (this.q[(n + 14 + -255)] * 233 << 16));
/* 1184:     */     }
/* 1185:1336 */     oneRound(3, 4, 13, 10, 25);
/* 1186:     */     
/* 1187:     */ 
/* 1188:1339 */     m = circularLeft(this.state[0], 4);
/* 1189:1340 */     n = circularLeft(this.state[1], 4);
/* 1190:1341 */     int i1 = circularLeft(this.state[2], 4);
/* 1191:1342 */     int i2 = circularLeft(this.state[3], 4);
/* 1192:1343 */     int i3 = circularLeft(this.state[4], 4);
/* 1193:1344 */     int i4 = circularLeft(this.state[5], 4);
/* 1194:1345 */     int i5 = circularLeft(this.state[6], 4);
/* 1195:1346 */     int i6 = circularLeft(this.state[7], 4);
/* 1196:1347 */     int i = this.state[24] + this.tmpState[0] + ((this.state[8] ^ this.state[16]) & this.state[0] ^ this.state[16]);
/* 1197:     */     
/* 1198:1349 */     this.state[0] = (circularLeft(i, 13) + i4);
/* 1199:1350 */     this.state[24] = this.state[16];
/* 1200:1351 */     this.state[16] = this.state[8];
/* 1201:1352 */     this.state[8] = m;
/* 1202:1353 */     i = this.state[25] + this.tmpState[1] + ((this.state[9] ^ this.state[17]) & this.state[1] ^ this.state[17]);
/* 1203:     */     
/* 1204:1355 */     this.state[1] = (circularLeft(i, 13) + i3);
/* 1205:1356 */     this.state[25] = this.state[17];
/* 1206:1357 */     this.state[17] = this.state[9];
/* 1207:1358 */     this.state[9] = n;
/* 1208:1359 */     i = this.state[26] + this.tmpState[2] + ((this.state[10] ^ this.state[18]) & this.state[2] ^ this.state[18]);
/* 1209:     */     
/* 1210:1361 */     this.state[2] = (circularLeft(i, 13) + i6);
/* 1211:1362 */     this.state[26] = this.state[18];
/* 1212:1363 */     this.state[18] = this.state[10];
/* 1213:1364 */     this.state[10] = i1;
/* 1214:1365 */     i = this.state[27] + this.tmpState[3] + ((this.state[11] ^ this.state[19]) & this.state[3] ^ this.state[19]);
/* 1215:     */     
/* 1216:1367 */     this.state[3] = (circularLeft(i, 13) + i5);
/* 1217:1368 */     this.state[27] = this.state[19];
/* 1218:1369 */     this.state[19] = this.state[11];
/* 1219:1370 */     this.state[11] = i2;
/* 1220:1371 */     i = this.state[28] + this.tmpState[4] + ((this.state[12] ^ this.state[20]) & this.state[4] ^ this.state[20]);
/* 1221:     */     
/* 1222:1373 */     this.state[4] = (circularLeft(i, 13) + n);
/* 1223:1374 */     this.state[28] = this.state[20];
/* 1224:1375 */     this.state[20] = this.state[12];
/* 1225:1376 */     this.state[12] = i3;
/* 1226:1377 */     i = this.state[29] + this.tmpState[5] + ((this.state[13] ^ this.state[21]) & this.state[5] ^ this.state[21]);
/* 1227:     */     
/* 1228:1379 */     this.state[5] = (circularLeft(i, 13) + m);
/* 1229:1380 */     this.state[29] = this.state[21];
/* 1230:1381 */     this.state[21] = this.state[13];
/* 1231:1382 */     this.state[13] = i4;
/* 1232:1383 */     i = this.state[30] + this.tmpState[6] + ((this.state[14] ^ this.state[22]) & this.state[6] ^ this.state[22]);
/* 1233:     */     
/* 1234:1385 */     this.state[6] = (circularLeft(i, 13) + i2);
/* 1235:1386 */     this.state[30] = this.state[22];
/* 1236:1387 */     this.state[22] = this.state[14];
/* 1237:1388 */     this.state[14] = i5;
/* 1238:1389 */     i = this.state[31] + this.tmpState[7] + ((this.state[15] ^ this.state[23]) & this.state[7] ^ this.state[23]);
/* 1239:     */     
/* 1240:1391 */     this.state[7] = (circularLeft(i, 13) + i1);
/* 1241:1392 */     this.state[31] = this.state[23];
/* 1242:1393 */     this.state[23] = this.state[15];
/* 1243:1394 */     this.state[15] = i6;
/* 1244:     */     
/* 1245:     */ 
/* 1246:1397 */     m = circularLeft(this.state[0], 13);
/* 1247:1398 */     n = circularLeft(this.state[1], 13);
/* 1248:1399 */     i1 = circularLeft(this.state[2], 13);
/* 1249:1400 */     i2 = circularLeft(this.state[3], 13);
/* 1250:1401 */     i3 = circularLeft(this.state[4], 13);
/* 1251:1402 */     i4 = circularLeft(this.state[5], 13);
/* 1252:1403 */     i5 = circularLeft(this.state[6], 13);
/* 1253:1404 */     i6 = circularLeft(this.state[7], 13);
/* 1254:1405 */     i = this.state[24] + this.tmpState[8] + ((this.state[8] ^ this.state[16]) & this.state[0] ^ this.state[16]);
/* 1255:     */     
/* 1256:1407 */     this.state[0] = (circularLeft(i, 10) + i6);
/* 1257:1408 */     this.state[24] = this.state[16];
/* 1258:1409 */     this.state[16] = this.state[8];
/* 1259:1410 */     this.state[8] = m;
/* 1260:1411 */     i = this.state[25] + this.tmpState[9] + ((this.state[9] ^ this.state[17]) & this.state[1] ^ this.state[17]);
/* 1261:     */     
/* 1262:1413 */     this.state[1] = (circularLeft(i, 10) + i5);
/* 1263:1414 */     this.state[25] = this.state[17];
/* 1264:1415 */     this.state[17] = this.state[9];
/* 1265:1416 */     this.state[9] = n;
/* 1266:1417 */     i = this.state[26] + this.tmpState[10] + ((this.state[10] ^ this.state[18]) & this.state[2] ^ this.state[18]);
/* 1267:     */     
/* 1268:1419 */     this.state[2] = (circularLeft(i, 10) + i4);
/* 1269:1420 */     this.state[26] = this.state[18];
/* 1270:1421 */     this.state[18] = this.state[10];
/* 1271:1422 */     this.state[10] = i1;
/* 1272:1423 */     i = this.state[27] + this.tmpState[11] + ((this.state[11] ^ this.state[19]) & this.state[3] ^ this.state[19]);
/* 1273:     */     
/* 1274:1425 */     this.state[3] = (circularLeft(i, 10) + i3);
/* 1275:1426 */     this.state[27] = this.state[19];
/* 1276:1427 */     this.state[19] = this.state[11];
/* 1277:1428 */     this.state[11] = i2;
/* 1278:1429 */     i = this.state[28] + this.tmpState[12] + ((this.state[12] ^ this.state[20]) & this.state[4] ^ this.state[20]);
/* 1279:     */     
/* 1280:1431 */     this.state[4] = (circularLeft(i, 10) + i2);
/* 1281:1432 */     this.state[28] = this.state[20];
/* 1282:1433 */     this.state[20] = this.state[12];
/* 1283:1434 */     this.state[12] = i3;
/* 1284:1435 */     i = this.state[29] + this.tmpState[13] + ((this.state[13] ^ this.state[21]) & this.state[5] ^ this.state[21]);
/* 1285:     */     
/* 1286:1437 */     this.state[5] = (circularLeft(i, 10) + i1);
/* 1287:1438 */     this.state[29] = this.state[21];
/* 1288:1439 */     this.state[21] = this.state[13];
/* 1289:1440 */     this.state[13] = i4;
/* 1290:1441 */     i = this.state[30] + this.tmpState[14] + ((this.state[14] ^ this.state[22]) & this.state[6] ^ this.state[22]);
/* 1291:     */     
/* 1292:1443 */     this.state[6] = (circularLeft(i, 10) + n);
/* 1293:1444 */     this.state[30] = this.state[22];
/* 1294:1445 */     this.state[22] = this.state[14];
/* 1295:1446 */     this.state[14] = i5;
/* 1296:1447 */     i = this.state[31] + this.tmpState[15] + ((this.state[15] ^ this.state[23]) & this.state[7] ^ this.state[23]);
/* 1297:     */     
/* 1298:1449 */     this.state[7] = (circularLeft(i, 10) + m);
/* 1299:1450 */     this.state[31] = this.state[23];
/* 1300:1451 */     this.state[23] = this.state[15];
/* 1301:1452 */     this.state[15] = i6;
/* 1302:     */     
/* 1303:     */ 
/* 1304:1455 */     m = circularLeft(this.state[0], 10);
/* 1305:1456 */     n = circularLeft(this.state[1], 10);
/* 1306:1457 */     i1 = circularLeft(this.state[2], 10);
/* 1307:1458 */     i2 = circularLeft(this.state[3], 10);
/* 1308:1459 */     i3 = circularLeft(this.state[4], 10);
/* 1309:1460 */     i4 = circularLeft(this.state[5], 10);
/* 1310:1461 */     i5 = circularLeft(this.state[6], 10);
/* 1311:1462 */     i6 = circularLeft(this.state[7], 10);
/* 1312:1463 */     i = this.state[24] + this.tmpState[16] + ((this.state[8] ^ this.state[16]) & this.state[0] ^ this.state[16]);
/* 1313:     */     
/* 1314:1465 */     this.state[0] = (circularLeft(i, 25) + i3);
/* 1315:1466 */     this.state[24] = this.state[16];
/* 1316:1467 */     this.state[16] = this.state[8];
/* 1317:1468 */     this.state[8] = m;
/* 1318:1469 */     i = this.state[25] + this.tmpState[17] + ((this.state[9] ^ this.state[17]) & this.state[1] ^ this.state[17]);
/* 1319:     */     
/* 1320:1471 */     this.state[1] = (circularLeft(i, 25) + i4);
/* 1321:1472 */     this.state[25] = this.state[17];
/* 1322:1473 */     this.state[17] = this.state[9];
/* 1323:1474 */     this.state[9] = n;
/* 1324:1475 */     i = this.state[26] + this.tmpState[18] + ((this.state[10] ^ this.state[18]) & this.state[2] ^ this.state[18]);
/* 1325:     */     
/* 1326:1477 */     this.state[2] = (circularLeft(i, 25) + i5);
/* 1327:1478 */     this.state[26] = this.state[18];
/* 1328:1479 */     this.state[18] = this.state[10];
/* 1329:1480 */     this.state[10] = i1;
/* 1330:1481 */     i = this.state[27] + this.tmpState[19] + ((this.state[11] ^ this.state[19]) & this.state[3] ^ this.state[19]);
/* 1331:     */     
/* 1332:1483 */     this.state[3] = (circularLeft(i, 25) + i6);
/* 1333:1484 */     this.state[27] = this.state[19];
/* 1334:1485 */     this.state[19] = this.state[11];
/* 1335:1486 */     this.state[11] = i2;
/* 1336:1487 */     i = this.state[28] + this.tmpState[20] + ((this.state[12] ^ this.state[20]) & this.state[4] ^ this.state[20]);
/* 1337:     */     
/* 1338:1489 */     this.state[4] = (circularLeft(i, 25) + m);
/* 1339:1490 */     this.state[28] = this.state[20];
/* 1340:1491 */     this.state[20] = this.state[12];
/* 1341:1492 */     this.state[12] = i3;
/* 1342:1493 */     i = this.state[29] + this.tmpState[21] + ((this.state[13] ^ this.state[21]) & this.state[5] ^ this.state[21]);
/* 1343:     */     
/* 1344:1495 */     this.state[5] = (circularLeft(i, 25) + n);
/* 1345:1496 */     this.state[29] = this.state[21];
/* 1346:1497 */     this.state[21] = this.state[13];
/* 1347:1498 */     this.state[13] = i4;
/* 1348:1499 */     i = this.state[30] + this.tmpState[22] + ((this.state[14] ^ this.state[22]) & this.state[6] ^ this.state[22]);
/* 1349:     */     
/* 1350:1501 */     this.state[6] = (circularLeft(i, 25) + i1);
/* 1351:1502 */     this.state[30] = this.state[22];
/* 1352:1503 */     this.state[22] = this.state[14];
/* 1353:1504 */     this.state[14] = i5;
/* 1354:1505 */     i = this.state[31] + this.tmpState[23] + ((this.state[15] ^ this.state[23]) & this.state[7] ^ this.state[23]);
/* 1355:     */     
/* 1356:1507 */     this.state[7] = (circularLeft(i, 25) + i2);
/* 1357:1508 */     this.state[31] = this.state[23];
/* 1358:1509 */     this.state[23] = this.state[15];
/* 1359:1510 */     this.state[15] = i6;
/* 1360:     */     
/* 1361:     */ 
/* 1362:1513 */     m = circularLeft(this.state[0], 25);
/* 1363:1514 */     n = circularLeft(this.state[1], 25);
/* 1364:1515 */     i1 = circularLeft(this.state[2], 25);
/* 1365:1516 */     i2 = circularLeft(this.state[3], 25);
/* 1366:1517 */     i3 = circularLeft(this.state[4], 25);
/* 1367:1518 */     i4 = circularLeft(this.state[5], 25);
/* 1368:1519 */     i5 = circularLeft(this.state[6], 25);
/* 1369:1520 */     i6 = circularLeft(this.state[7], 25);
/* 1370:1521 */     i = this.state[24] + this.tmpState[24] + ((this.state[8] ^ this.state[16]) & this.state[0] ^ this.state[16]);
/* 1371:     */     
/* 1372:1523 */     this.state[0] = (circularLeft(i, 4) + n);
/* 1373:1524 */     this.state[24] = this.state[16];
/* 1374:1525 */     this.state[16] = this.state[8];
/* 1375:1526 */     this.state[8] = m;
/* 1376:1527 */     i = this.state[25] + this.tmpState[25] + ((this.state[9] ^ this.state[17]) & this.state[1] ^ this.state[17]);
/* 1377:     */     
/* 1378:1529 */     this.state[1] = (circularLeft(i, 4) + m);
/* 1379:1530 */     this.state[25] = this.state[17];
/* 1380:1531 */     this.state[17] = this.state[9];
/* 1381:1532 */     this.state[9] = n;
/* 1382:1533 */     i = this.state[26] + this.tmpState[26] + ((this.state[10] ^ this.state[18]) & this.state[2] ^ this.state[18]);
/* 1383:     */     
/* 1384:1535 */     this.state[2] = (circularLeft(i, 4) + i2);
/* 1385:1536 */     this.state[26] = this.state[18];
/* 1386:1537 */     this.state[18] = this.state[10];
/* 1387:1538 */     this.state[10] = i1;
/* 1388:1539 */     i = this.state[27] + this.tmpState[27] + ((this.state[11] ^ this.state[19]) & this.state[3] ^ this.state[19]);
/* 1389:     */     
/* 1390:1541 */     this.state[3] = (circularLeft(i, 4) + i1);
/* 1391:1542 */     this.state[27] = this.state[19];
/* 1392:1543 */     this.state[19] = this.state[11];
/* 1393:1544 */     this.state[11] = i2;
/* 1394:1545 */     i = this.state[28] + this.tmpState[28] + ((this.state[12] ^ this.state[20]) & this.state[4] ^ this.state[20]);
/* 1395:     */     
/* 1396:1547 */     this.state[4] = (circularLeft(i, 4) + i4);
/* 1397:1548 */     this.state[28] = this.state[20];
/* 1398:1549 */     this.state[20] = this.state[12];
/* 1399:1550 */     this.state[12] = i3;
/* 1400:1551 */     i = this.state[29] + this.tmpState[29] + ((this.state[13] ^ this.state[21]) & this.state[5] ^ this.state[21]);
/* 1401:     */     
/* 1402:1553 */     this.state[5] = (circularLeft(i, 4) + i3);
/* 1403:1554 */     this.state[29] = this.state[21];
/* 1404:1555 */     this.state[21] = this.state[13];
/* 1405:1556 */     this.state[13] = i4;
/* 1406:1557 */     i = this.state[30] + this.tmpState[30] + ((this.state[14] ^ this.state[22]) & this.state[6] ^ this.state[22]);
/* 1407:     */     
/* 1408:1559 */     this.state[6] = (circularLeft(i, 4) + i6);
/* 1409:1560 */     this.state[30] = this.state[22];
/* 1410:1561 */     this.state[22] = this.state[14];
/* 1411:1562 */     this.state[14] = i5;
/* 1412:1563 */     i = this.state[31] + this.tmpState[31] + ((this.state[15] ^ this.state[23]) & this.state[7] ^ this.state[23]);
/* 1413:     */     
/* 1414:1565 */     this.state[7] = (circularLeft(i, 4) + i5);
/* 1415:1566 */     this.state[31] = this.state[23];
/* 1416:1567 */     this.state[23] = this.state[15];
/* 1417:1568 */     this.state[15] = i6;
/* 1418:     */   }
/* 1419:     */   
/* 1420:     */   public String toString()
/* 1421:     */   {
/* 1422:1575 */     return "SIMD-" + (getDigestLength() << 3);
/* 1423:     */   }
/* 1424:     */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     fr.cryptohash.SIMDBigCore
 * JD-Core Version:    0.7.1
 */