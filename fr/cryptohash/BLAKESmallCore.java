/*   1:    */ package fr.cryptohash;
/*   2:    */ 
/*   3:    */ abstract class BLAKESmallCore
/*   4:    */   extends DigestEngine
/*   5:    */ {
/*   6: 42 */   private static final int[] SIGMA = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 14, 10, 4, 8, 9, 15, 13, 6, 1, 12, 0, 2, 11, 7, 5, 3, 11, 8, 12, 0, 5, 2, 15, 13, 10, 14, 3, 6, 7, 1, 9, 4, 7, 9, 3, 1, 13, 12, 11, 14, 2, 6, 5, 10, 4, 0, 15, 8, 9, 0, 5, 7, 2, 4, 10, 15, 14, 1, 11, 12, 6, 8, 3, 13, 2, 12, 6, 10, 0, 11, 8, 3, 4, 13, 7, 5, 15, 14, 1, 9, 12, 5, 1, 15, 14, 13, 4, 10, 0, 7, 6, 3, 9, 2, 8, 11, 13, 11, 7, 14, 12, 1, 3, 9, 5, 0, 15, 4, 8, 6, 2, 10, 6, 15, 14, 9, 11, 3, 0, 8, 12, 2, 13, 7, 1, 4, 10, 5, 10, 2, 8, 4, 7, 6, 1, 5, 15, 11, 9, 14, 3, 12, 13, 0, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 14, 10, 4, 8, 9, 15, 13, 6, 1, 12, 0, 2, 11, 7, 5, 3, 11, 8, 12, 0, 5, 2, 15, 13, 10, 14, 3, 6, 7, 1, 9, 4, 7, 9, 3, 1, 13, 12, 11, 14, 2, 6, 5, 10, 4, 0, 15, 8 };
/*   7: 59 */   private static final int[] CS = { 608135816, -2052912941, 320440878, 57701188, -1542899678, 698298832, 137296536, -330404727, 1160258022, 953160567, -1101764913, 887688300, -1062458953, -914599715, 1065670069, -1253635817 };
/*   8:    */   private int h0;
/*   9:    */   private int h1;
/*  10:    */   private int h2;
/*  11:    */   private int h3;
/*  12:    */   private int h4;
/*  13:    */   private int h5;
/*  14:    */   private int h6;
/*  15:    */   private int h7;
/*  16:    */   private int s0;
/*  17:    */   private int s1;
/*  18:    */   private int s2;
/*  19:    */   private int s3;
/*  20:    */   private int t0;
/*  21:    */   private int t1;
/*  22:    */   private int[] tmpM;
/*  23:    */   private byte[] tmpBuf;
/*  24:    */   
/*  25:    */   public int getBlockLength()
/*  26:    */   {
/*  27: 82 */     return 64;
/*  28:    */   }
/*  29:    */   
/*  30:    */   protected Digest copyState(BLAKESmallCore paramBLAKESmallCore)
/*  31:    */   {
/*  32: 88 */     paramBLAKESmallCore.h0 = this.h0;
/*  33: 89 */     paramBLAKESmallCore.h1 = this.h1;
/*  34: 90 */     paramBLAKESmallCore.h2 = this.h2;
/*  35: 91 */     paramBLAKESmallCore.h3 = this.h3;
/*  36: 92 */     paramBLAKESmallCore.h4 = this.h4;
/*  37: 93 */     paramBLAKESmallCore.h5 = this.h5;
/*  38: 94 */     paramBLAKESmallCore.h6 = this.h6;
/*  39: 95 */     paramBLAKESmallCore.h7 = this.h7;
/*  40: 96 */     paramBLAKESmallCore.s0 = this.s0;
/*  41: 97 */     paramBLAKESmallCore.s1 = this.s1;
/*  42: 98 */     paramBLAKESmallCore.s2 = this.s2;
/*  43: 99 */     paramBLAKESmallCore.s3 = this.s3;
/*  44:100 */     paramBLAKESmallCore.t0 = this.t0;
/*  45:101 */     paramBLAKESmallCore.t1 = this.t1;
/*  46:102 */     return super.copyState(paramBLAKESmallCore);
/*  47:    */   }
/*  48:    */   
/*  49:    */   protected void engineReset()
/*  50:    */   {
/*  51:108 */     int[] arrayOfInt = getInitVal();
/*  52:109 */     this.h0 = arrayOfInt[0];
/*  53:110 */     this.h1 = arrayOfInt[1];
/*  54:111 */     this.h2 = arrayOfInt[2];
/*  55:112 */     this.h3 = arrayOfInt[3];
/*  56:113 */     this.h4 = arrayOfInt[4];
/*  57:114 */     this.h5 = arrayOfInt[5];
/*  58:115 */     this.h6 = arrayOfInt[6];
/*  59:116 */     this.h7 = arrayOfInt[7];
/*  60:117 */     this.s0 = (this.s1 = this.s2 = this.s3 = 0);
/*  61:118 */     this.t0 = (this.t1 = 0);
/*  62:    */   }
/*  63:    */   
/*  64:    */   abstract int[] getInitVal();
/*  65:    */   
/*  66:    */   protected void doPadding(byte[] paramArrayOfByte, int paramInt)
/*  67:    */   {
/*  68:131 */     int i = flush();
/*  69:132 */     int j = i << 3;
/*  70:133 */     int k = this.t1;
/*  71:134 */     int m = this.t0 + j;
/*  72:135 */     this.tmpBuf[i] = Byte.MIN_VALUE;
/*  73:136 */     if (i == 0)
/*  74:    */     {
/*  75:137 */       this.t0 = -512;
/*  76:138 */       this.t1 = -1;
/*  77:    */     }
/*  78:139 */     else if (this.t0 == 0)
/*  79:    */     {
/*  80:140 */       this.t0 = (-512 + j);
/*  81:141 */       this.t1 -= 1;
/*  82:    */     }
/*  83:    */     else
/*  84:    */     {
/*  85:143 */       this.t0 -= 512 - j;
/*  86:    */     }
/*  87:    */     int n;
/*  88:145 */     if (i < 56)
/*  89:    */     {
/*  90:146 */       for (n = i + 1; n < 56; n++) {
/*  91:147 */         this.tmpBuf[n] = 0;
/*  92:    */       }
/*  93:148 */       if (getDigestLength() == 32)
/*  94:    */       {
/*  95:149 */         byte[] tmp144_139 = this.tmpBuf;tmp144_139[55] = ((byte)(tmp144_139[55] | 0x1));
/*  96:    */       }
/*  97:150 */       encodeBEInt(k, this.tmpBuf, 56);
/*  98:151 */       encodeBEInt(m, this.tmpBuf, 60);
/*  99:152 */       update(this.tmpBuf, i, 64 - i);
/* 100:    */     }
/* 101:    */     else
/* 102:    */     {
/* 103:154 */       for (n = i + 1; n < 64; n++) {
/* 104:155 */         this.tmpBuf[n] = 0;
/* 105:    */       }
/* 106:156 */       update(this.tmpBuf, i, 64 - i);
/* 107:157 */       this.t0 = -512;
/* 108:158 */       this.t1 = -1;
/* 109:159 */       for (n = 0; n < 56; n++) {
/* 110:160 */         this.tmpBuf[n] = 0;
/* 111:    */       }
/* 112:161 */       if (getDigestLength() == 32) {
/* 113:162 */         this.tmpBuf[55] = 1;
/* 114:    */       }
/* 115:163 */       encodeBEInt(k, this.tmpBuf, 56);
/* 116:164 */       encodeBEInt(m, this.tmpBuf, 60);
/* 117:165 */       update(this.tmpBuf, 0, 64);
/* 118:    */     }
/* 119:167 */     encodeBEInt(this.h0, paramArrayOfByte, paramInt + 0);
/* 120:168 */     encodeBEInt(this.h1, paramArrayOfByte, paramInt + 4);
/* 121:169 */     encodeBEInt(this.h2, paramArrayOfByte, paramInt + 8);
/* 122:170 */     encodeBEInt(this.h3, paramArrayOfByte, paramInt + 12);
/* 123:171 */     encodeBEInt(this.h4, paramArrayOfByte, paramInt + 16);
/* 124:172 */     encodeBEInt(this.h5, paramArrayOfByte, paramInt + 20);
/* 125:173 */     encodeBEInt(this.h6, paramArrayOfByte, paramInt + 24);
/* 126:174 */     if (getDigestLength() == 32) {
/* 127:175 */       encodeBEInt(this.h7, paramArrayOfByte, paramInt + 28);
/* 128:    */     }
/* 129:    */   }
/* 130:    */   
/* 131:    */   protected void doInit()
/* 132:    */   {
/* 133:181 */     this.tmpM = new int[16];
/* 134:182 */     this.tmpBuf = new byte[64];
/* 135:183 */     engineReset();
/* 136:    */   }
/* 137:    */   
/* 138:    */   private static final void encodeBEInt(int paramInt1, byte[] paramArrayOfByte, int paramInt2)
/* 139:    */   {
/* 140:197 */     paramArrayOfByte[(paramInt2 + 0)] = ((byte)(paramInt1 >>> 24));
/* 141:198 */     paramArrayOfByte[(paramInt2 + 1)] = ((byte)(paramInt1 >>> 16));
/* 142:199 */     paramArrayOfByte[(paramInt2 + 2)] = ((byte)(paramInt1 >>> 8));
/* 143:200 */     paramArrayOfByte[(paramInt2 + 3)] = ((byte)paramInt1);
/* 144:    */   }
/* 145:    */   
/* 146:    */   private static final int decodeBEInt(byte[] paramArrayOfByte, int paramInt)
/* 147:    */   {
/* 148:213 */     return (paramArrayOfByte[paramInt] & 0xFF) << 24 | (paramArrayOfByte[(paramInt + 1)] & 0xFF) << 16 | (paramArrayOfByte[(paramInt + 2)] & 0xFF) << 8 | paramArrayOfByte[(paramInt + 3)] & 0xFF;
/* 149:    */   }
/* 150:    */   
/* 151:    */   private static int circularRight(int paramInt1, int paramInt2)
/* 152:    */   {
/* 153:230 */     return paramInt1 >>> paramInt2 | paramInt1 << 32 - paramInt2;
/* 154:    */   }
/* 155:    */   
/* 156:    */   protected void processBlock(byte[] paramArrayOfByte)
/* 157:    */   {
/* 158:236 */     this.t0 += 512;
/* 159:237 */     if ((this.t0 & 0xFFFFFE00) == 0) {
/* 160:238 */       this.t1 += 1;
/* 161:    */     }
/* 162:239 */     int i = this.h0;
/* 163:240 */     int j = this.h1;
/* 164:241 */     int k = this.h2;
/* 165:242 */     int m = this.h3;
/* 166:243 */     int n = this.h4;
/* 167:244 */     int i1 = this.h5;
/* 168:245 */     int i2 = this.h6;
/* 169:246 */     int i3 = this.h7;
/* 170:247 */     int i4 = this.s0 ^ 0x243F6A88;
/* 171:248 */     int i5 = this.s1 ^ 0x85A308D3;
/* 172:249 */     int i6 = this.s2 ^ 0x13198A2E;
/* 173:250 */     int i7 = this.s3 ^ 0x3707344;
/* 174:251 */     int i8 = this.t0 ^ 0xA4093822;
/* 175:252 */     int i9 = this.t0 ^ 0x299F31D0;
/* 176:253 */     int i10 = this.t1 ^ 0x82EFA98;
/* 177:254 */     int i11 = this.t1 ^ 0xEC4E6C89;
/* 178:255 */     int[] arrayOfInt = this.tmpM;
/* 179:256 */     for (int i12 = 0; i12 < 16; i12++) {
/* 180:257 */       arrayOfInt[i12] = decodeBEInt(paramArrayOfByte, 4 * i12);
/* 181:    */     }
/* 182:258 */     for (i12 = 0; i12 < 14; i12++)
/* 183:    */     {
/* 184:259 */       int i13 = SIGMA[((i12 << 4) + 0)];
/* 185:260 */       int i14 = SIGMA[((i12 << 4) + 1)];
/* 186:261 */       i += n + (arrayOfInt[i13] ^ CS[i14]);
/* 187:262 */       i8 = circularRight(i8 ^ i, 16);
/* 188:263 */       i4 += i8;
/* 189:264 */       n = circularRight(n ^ i4, 12);
/* 190:265 */       i += n + (arrayOfInt[i14] ^ CS[i13]);
/* 191:266 */       i8 = circularRight(i8 ^ i, 8);
/* 192:267 */       i4 += i8;
/* 193:268 */       n = circularRight(n ^ i4, 7);
/* 194:269 */       i13 = SIGMA[((i12 << 4) + 2)];
/* 195:270 */       i14 = SIGMA[((i12 << 4) + 3)];
/* 196:271 */       j += i1 + (arrayOfInt[i13] ^ CS[i14]);
/* 197:272 */       i9 = circularRight(i9 ^ j, 16);
/* 198:273 */       i5 += i9;
/* 199:274 */       i1 = circularRight(i1 ^ i5, 12);
/* 200:275 */       j += i1 + (arrayOfInt[i14] ^ CS[i13]);
/* 201:276 */       i9 = circularRight(i9 ^ j, 8);
/* 202:277 */       i5 += i9;
/* 203:278 */       i1 = circularRight(i1 ^ i5, 7);
/* 204:279 */       i13 = SIGMA[((i12 << 4) + 4)];
/* 205:280 */       i14 = SIGMA[((i12 << 4) + 5)];
/* 206:281 */       k += i2 + (arrayOfInt[i13] ^ CS[i14]);
/* 207:282 */       i10 = circularRight(i10 ^ k, 16);
/* 208:283 */       i6 += i10;
/* 209:284 */       i2 = circularRight(i2 ^ i6, 12);
/* 210:285 */       k += i2 + (arrayOfInt[i14] ^ CS[i13]);
/* 211:286 */       i10 = circularRight(i10 ^ k, 8);
/* 212:287 */       i6 += i10;
/* 213:288 */       i2 = circularRight(i2 ^ i6, 7);
/* 214:289 */       i13 = SIGMA[((i12 << 4) + 6)];
/* 215:290 */       i14 = SIGMA[((i12 << 4) + 7)];
/* 216:291 */       m += i3 + (arrayOfInt[i13] ^ CS[i14]);
/* 217:292 */       i11 = circularRight(i11 ^ m, 16);
/* 218:293 */       i7 += i11;
/* 219:294 */       i3 = circularRight(i3 ^ i7, 12);
/* 220:295 */       m += i3 + (arrayOfInt[i14] ^ CS[i13]);
/* 221:296 */       i11 = circularRight(i11 ^ m, 8);
/* 222:297 */       i7 += i11;
/* 223:298 */       i3 = circularRight(i3 ^ i7, 7);
/* 224:299 */       i13 = SIGMA[((i12 << 4) + 8)];
/* 225:300 */       i14 = SIGMA[((i12 << 4) + 9)];
/* 226:301 */       i += i1 + (arrayOfInt[i13] ^ CS[i14]);
/* 227:302 */       i11 = circularRight(i11 ^ i, 16);
/* 228:303 */       i6 += i11;
/* 229:304 */       i1 = circularRight(i1 ^ i6, 12);
/* 230:305 */       i += i1 + (arrayOfInt[i14] ^ CS[i13]);
/* 231:306 */       i11 = circularRight(i11 ^ i, 8);
/* 232:307 */       i6 += i11;
/* 233:308 */       i1 = circularRight(i1 ^ i6, 7);
/* 234:309 */       i13 = SIGMA[((i12 << 4) + 10)];
/* 235:310 */       i14 = SIGMA[((i12 << 4) + 11)];
/* 236:311 */       j += i2 + (arrayOfInt[i13] ^ CS[i14]);
/* 237:312 */       i8 = circularRight(i8 ^ j, 16);
/* 238:313 */       i7 += i8;
/* 239:314 */       i2 = circularRight(i2 ^ i7, 12);
/* 240:315 */       j += i2 + (arrayOfInt[i14] ^ CS[i13]);
/* 241:316 */       i8 = circularRight(i8 ^ j, 8);
/* 242:317 */       i7 += i8;
/* 243:318 */       i2 = circularRight(i2 ^ i7, 7);
/* 244:319 */       i13 = SIGMA[((i12 << 4) + 12)];
/* 245:320 */       i14 = SIGMA[((i12 << 4) + 13)];
/* 246:321 */       k += i3 + (arrayOfInt[i13] ^ CS[i14]);
/* 247:322 */       i9 = circularRight(i9 ^ k, 16);
/* 248:323 */       i4 += i9;
/* 249:324 */       i3 = circularRight(i3 ^ i4, 12);
/* 250:325 */       k += i3 + (arrayOfInt[i14] ^ CS[i13]);
/* 251:326 */       i9 = circularRight(i9 ^ k, 8);
/* 252:327 */       i4 += i9;
/* 253:328 */       i3 = circularRight(i3 ^ i4, 7);
/* 254:329 */       i13 = SIGMA[((i12 << 4) + 14)];
/* 255:330 */       i14 = SIGMA[((i12 << 4) + 15)];
/* 256:331 */       m += n + (arrayOfInt[i13] ^ CS[i14]);
/* 257:332 */       i10 = circularRight(i10 ^ m, 16);
/* 258:333 */       i5 += i10;
/* 259:334 */       n = circularRight(n ^ i5, 12);
/* 260:335 */       m += n + (arrayOfInt[i14] ^ CS[i13]);
/* 261:336 */       i10 = circularRight(i10 ^ m, 8);
/* 262:337 */       i5 += i10;
/* 263:338 */       n = circularRight(n ^ i5, 7);
/* 264:    */     }
/* 265:340 */     this.h0 ^= this.s0 ^ i ^ i4;
/* 266:341 */     this.h1 ^= this.s1 ^ j ^ i5;
/* 267:342 */     this.h2 ^= this.s2 ^ k ^ i6;
/* 268:343 */     this.h3 ^= this.s3 ^ m ^ i7;
/* 269:344 */     this.h4 ^= this.s0 ^ n ^ i8;
/* 270:345 */     this.h5 ^= this.s1 ^ i1 ^ i9;
/* 271:346 */     this.h6 ^= this.s2 ^ i2 ^ i10;
/* 272:347 */     this.h7 ^= this.s3 ^ i3 ^ i11;
/* 273:    */   }
/* 274:    */   
/* 275:    */   public String toString()
/* 276:    */   {
/* 277:353 */     return "BLAKE-" + (getDigestLength() << 3);
/* 278:    */   }
/* 279:    */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     fr.cryptohash.BLAKESmallCore
 * JD-Core Version:    0.7.1
 */