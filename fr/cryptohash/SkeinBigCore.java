/*   1:    */ package fr.cryptohash;
/*   2:    */ 
/*   3:    */ abstract class SkeinBigCore
/*   4:    */   implements Digest
/*   5:    */ {
/*   6:    */   private static final int BLOCK_LEN = 64;
/*   7:    */   private byte[] buf;
/*   8:    */   private byte[] tmpOut;
/*   9:    */   private int ptr;
/*  10:    */   private long[] h;
/*  11:    */   private long bcount;
/*  12:    */   
/*  13:    */   SkeinBigCore()
/*  14:    */   {
/*  15: 56 */     this.buf = new byte[64];
/*  16: 57 */     this.tmpOut = new byte[64];
/*  17: 58 */     this.h = new long[27];
/*  18: 59 */     reset();
/*  19:    */   }
/*  20:    */   
/*  21:    */   public void update(byte paramByte)
/*  22:    */   {
/*  23: 65 */     if (this.ptr == 64)
/*  24:    */     {
/*  25: 66 */       int i = this.bcount == 0L ? 224 : 96;
/*  26: 67 */       this.bcount += 1L;
/*  27: 68 */       ubi(i, 0);
/*  28: 69 */       this.buf[0] = paramByte;
/*  29: 70 */       this.ptr = 1;
/*  30:    */     }
/*  31:    */     else
/*  32:    */     {
/*  33: 72 */       this.buf[(this.ptr++)] = paramByte;
/*  34:    */     }
/*  35:    */   }
/*  36:    */   
/*  37:    */   public void update(byte[] paramArrayOfByte)
/*  38:    */   {
/*  39: 79 */     update(paramArrayOfByte, 0, paramArrayOfByte.length);
/*  40:    */   }
/*  41:    */   
/*  42:    */   public void update(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*  43:    */   {
/*  44: 85 */     if (paramInt2 <= 0) {
/*  45: 86 */       return;
/*  46:    */     }
/*  47: 87 */     int i = 64 - this.ptr;
/*  48: 88 */     if (paramInt2 <= i)
/*  49:    */     {
/*  50: 89 */       System.arraycopy(paramArrayOfByte, paramInt1, this.buf, this.ptr, paramInt2);
/*  51: 90 */       this.ptr += paramInt2;
/*  52: 91 */       return;
/*  53:    */     }
/*  54: 93 */     if (i != 0)
/*  55:    */     {
/*  56: 94 */       System.arraycopy(paramArrayOfByte, paramInt1, this.buf, this.ptr, i);
/*  57: 95 */       paramInt1 += i;
/*  58: 96 */       paramInt2 -= i;
/*  59:    */     }
/*  60:    */     for (;;)
/*  61:    */     {
/*  62:100 */       int j = this.bcount == 0L ? 224 : 96;
/*  63:101 */       this.bcount += 1L;
/*  64:102 */       ubi(j, 0);
/*  65:103 */       if (paramInt2 <= 64) {
/*  66:    */         break;
/*  67:    */       }
/*  68:105 */       System.arraycopy(paramArrayOfByte, paramInt1, this.buf, 0, 64);
/*  69:106 */       paramInt1 += 64;
/*  70:107 */       paramInt2 -= 64;
/*  71:    */     }
/*  72:109 */     System.arraycopy(paramArrayOfByte, paramInt1, this.buf, 0, paramInt2);
/*  73:110 */     this.ptr = paramInt2;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public byte[] digest()
/*  77:    */   {
/*  78:116 */     int i = getDigestLength();
/*  79:117 */     byte[] arrayOfByte = new byte[i];
/*  80:118 */     digest(arrayOfByte, 0, i);
/*  81:119 */     return arrayOfByte;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public byte[] digest(byte[] paramArrayOfByte)
/*  85:    */   {
/*  86:125 */     update(paramArrayOfByte, 0, paramArrayOfByte.length);
/*  87:126 */     return digest();
/*  88:    */   }
/*  89:    */   
/*  90:    */   public int digest(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*  91:    */   {
/*  92:132 */     for (int i = this.ptr; i < 64; i++) {
/*  93:133 */       this.buf[i] = 0;
/*  94:    */     }
/*  95:134 */     ubi(this.bcount == 0L ? 480 : 352, this.ptr);
/*  96:135 */     for (i = 0; i < 64; i++) {
/*  97:136 */       this.buf[i] = 0;
/*  98:    */     }
/*  99:137 */     this.bcount = 0L;
/* 100:138 */     ubi(510, 8);
/* 101:139 */     for (i = 0; i < 8; i++) {
/* 102:140 */       encodeLELong(this.h[i], this.tmpOut, i << 3);
/* 103:    */     }
/* 104:141 */     i = getDigestLength();
/* 105:142 */     if (paramInt2 > i) {
/* 106:143 */       paramInt2 = i;
/* 107:    */     }
/* 108:144 */     System.arraycopy(this.tmpOut, 0, paramArrayOfByte, paramInt1, paramInt2);
/* 109:145 */     reset();
/* 110:146 */     return paramInt2;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public void reset()
/* 114:    */   {
/* 115:152 */     this.ptr = 0;
/* 116:153 */     long[] arrayOfLong = getInitVal();
/* 117:154 */     System.arraycopy(arrayOfLong, 0, this.h, 0, 8);
/* 118:155 */     this.bcount = 0L;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public Digest copy()
/* 122:    */   {
/* 123:161 */     SkeinBigCore localSkeinBigCore = dup();
/* 124:162 */     System.arraycopy(this.buf, 0, localSkeinBigCore.buf, 0, this.ptr);
/* 125:163 */     localSkeinBigCore.ptr = this.ptr;
/* 126:164 */     System.arraycopy(this.h, 0, localSkeinBigCore.h, 0, 8);
/* 127:165 */     localSkeinBigCore.bcount = this.bcount;
/* 128:166 */     return localSkeinBigCore;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public int getBlockLength()
/* 132:    */   {
/* 133:172 */     return 64;
/* 134:    */   }
/* 135:    */   
/* 136:    */   abstract SkeinBigCore dup();
/* 137:    */   
/* 138:    */   abstract long[] getInitVal();
/* 139:    */   
/* 140:    */   private static final void encodeLELong(long paramLong, byte[] paramArrayOfByte, int paramInt)
/* 141:    */   {
/* 142:186 */     paramArrayOfByte[(paramInt + 0)] = ((byte)(int)paramLong);
/* 143:187 */     paramArrayOfByte[(paramInt + 1)] = ((byte)(int)(paramLong >>> 8));
/* 144:188 */     paramArrayOfByte[(paramInt + 2)] = ((byte)(int)(paramLong >>> 16));
/* 145:189 */     paramArrayOfByte[(paramInt + 3)] = ((byte)(int)(paramLong >>> 24));
/* 146:190 */     paramArrayOfByte[(paramInt + 4)] = ((byte)(int)(paramLong >>> 32));
/* 147:191 */     paramArrayOfByte[(paramInt + 5)] = ((byte)(int)(paramLong >>> 40));
/* 148:192 */     paramArrayOfByte[(paramInt + 6)] = ((byte)(int)(paramLong >>> 48));
/* 149:193 */     paramArrayOfByte[(paramInt + 7)] = ((byte)(int)(paramLong >>> 56));
/* 150:    */   }
/* 151:    */   
/* 152:    */   private static final long decodeLELong(byte[] paramArrayOfByte, int paramInt)
/* 153:    */   {
/* 154:198 */     return paramArrayOfByte[paramInt] & 0xFF | (paramArrayOfByte[(paramInt + 1)] & 0xFF) << 8 | (paramArrayOfByte[(paramInt + 2)] & 0xFF) << 16 | (paramArrayOfByte[(paramInt + 3)] & 0xFF) << 24 | (paramArrayOfByte[(paramInt + 4)] & 0xFF) << 32 | (paramArrayOfByte[(paramInt + 5)] & 0xFF) << 40 | (paramArrayOfByte[(paramInt + 6)] & 0xFF) << 48 | (paramArrayOfByte[(paramInt + 7)] & 0xFF) << 56;
/* 155:    */   }
/* 156:    */   
/* 157:    */   private final void ubi(int paramInt1, int paramInt2)
/* 158:    */   {
/* 159:210 */     long l1 = decodeLELong(this.buf, 0);
/* 160:211 */     long l2 = decodeLELong(this.buf, 8);
/* 161:212 */     long l3 = decodeLELong(this.buf, 16);
/* 162:213 */     long l4 = decodeLELong(this.buf, 24);
/* 163:214 */     long l5 = decodeLELong(this.buf, 32);
/* 164:215 */     long l6 = decodeLELong(this.buf, 40);
/* 165:216 */     long l7 = decodeLELong(this.buf, 48);
/* 166:217 */     long l8 = decodeLELong(this.buf, 56);
/* 167:218 */     long l9 = l1;
/* 168:219 */     long l10 = l2;
/* 169:220 */     long l11 = l3;
/* 170:221 */     long l12 = l4;
/* 171:222 */     long l13 = l5;
/* 172:223 */     long l14 = l6;
/* 173:224 */     long l15 = l7;
/* 174:225 */     long l16 = l8;
/* 175:226 */     this.h[8] = (this.h[0] ^ this.h[1] ^ this.h[2] ^ this.h[3] ^ this.h[4] ^ this.h[5] ^ this.h[6] ^ this.h[7] ^ 0xA9FC1A22);
/* 176:    */     
/* 177:228 */     long l17 = (this.bcount << 6) + paramInt2;
/* 178:229 */     long l18 = (this.bcount >>> 58) + (paramInt1 << 55);
/* 179:230 */     long l19 = l17 ^ l18;
/* 180:231 */     for (int i = 0; i <= 15; i += 3)
/* 181:    */     {
/* 182:232 */       this.h[(i + 9)] = this.h[(i + 0)];
/* 183:233 */       this.h[(i + 10)] = this.h[(i + 1)];
/* 184:234 */       this.h[(i + 11)] = this.h[(i + 2)];
/* 185:    */     }
/* 186:236 */     for (i = 0; i < 9; i++)
/* 187:    */     {
/* 188:237 */       int j = i << 1;
/* 189:238 */       l9 += this.h[(j + 0)];
/* 190:239 */       l10 += this.h[(j + 1)];
/* 191:240 */       l11 += this.h[(j + 2)];
/* 192:241 */       l12 += this.h[(j + 3)];
/* 193:242 */       l13 += this.h[(j + 4)];
/* 194:243 */       l14 += this.h[(j + 5)] + l17;
/* 195:244 */       l15 += this.h[(j + 6)] + l18;
/* 196:245 */       l16 += this.h[(j + 7)] + j;
/* 197:246 */       l9 += l10;
/* 198:247 */       l10 = l10 << 46 ^ l10 >>> 18 ^ l9;
/* 199:248 */       l11 += l12;
/* 200:249 */       l12 = l12 << 36 ^ l12 >>> 28 ^ l11;
/* 201:250 */       l13 += l14;
/* 202:251 */       l14 = l14 << 19 ^ l14 >>> 45 ^ l13;
/* 203:252 */       l15 += l16;
/* 204:253 */       l16 = l16 << 37 ^ l16 >>> 27 ^ l15;
/* 205:254 */       l11 += l10;
/* 206:255 */       l10 = l10 << 33 ^ l10 >>> 31 ^ l11;
/* 207:256 */       l13 += l16;
/* 208:257 */       l16 = l16 << 27 ^ l16 >>> 37 ^ l13;
/* 209:258 */       l15 += l14;
/* 210:259 */       l14 = l14 << 14 ^ l14 >>> 50 ^ l15;
/* 211:260 */       l9 += l12;
/* 212:261 */       l12 = l12 << 42 ^ l12 >>> 22 ^ l9;
/* 213:262 */       l13 += l10;
/* 214:263 */       l10 = l10 << 17 ^ l10 >>> 47 ^ l13;
/* 215:264 */       l15 += l12;
/* 216:265 */       l12 = l12 << 49 ^ l12 >>> 15 ^ l15;
/* 217:266 */       l9 += l14;
/* 218:267 */       l14 = l14 << 36 ^ l14 >>> 28 ^ l9;
/* 219:268 */       l11 += l16;
/* 220:269 */       l16 = l16 << 39 ^ l16 >>> 25 ^ l11;
/* 221:270 */       l15 += l10;
/* 222:271 */       l10 = l10 << 44 ^ l10 >>> 20 ^ l15;
/* 223:272 */       l9 += l16;
/* 224:273 */       l16 = l16 << 9 ^ l16 >>> 55 ^ l9;
/* 225:274 */       l11 += l14;
/* 226:275 */       l14 = l14 << 54 ^ l14 >>> 10 ^ l11;
/* 227:276 */       l13 += l12;
/* 228:277 */       l12 = l12 << 56 ^ l12 >>> 8 ^ l13;
/* 229:278 */       l9 += this.h[(j + 1 + 0)];
/* 230:279 */       l10 += this.h[(j + 1 + 1)];
/* 231:280 */       l11 += this.h[(j + 1 + 2)];
/* 232:281 */       l12 += this.h[(j + 1 + 3)];
/* 233:282 */       l13 += this.h[(j + 1 + 4)];
/* 234:283 */       l14 += this.h[(j + 1 + 5)] + l18;
/* 235:284 */       l15 += this.h[(j + 1 + 6)] + l19;
/* 236:285 */       l16 += this.h[(j + 1 + 7)] + j + 1L;
/* 237:286 */       l9 += l10;
/* 238:287 */       l10 = l10 << 39 ^ l10 >>> 25 ^ l9;
/* 239:288 */       l11 += l12;
/* 240:289 */       l12 = l12 << 30 ^ l12 >>> 34 ^ l11;
/* 241:290 */       l13 += l14;
/* 242:291 */       l14 = l14 << 34 ^ l14 >>> 30 ^ l13;
/* 243:292 */       l15 += l16;
/* 244:293 */       l16 = l16 << 24 ^ l16 >>> 40 ^ l15;
/* 245:294 */       l11 += l10;
/* 246:295 */       l10 = l10 << 13 ^ l10 >>> 51 ^ l11;
/* 247:296 */       l13 += l16;
/* 248:297 */       l16 = l16 << 50 ^ l16 >>> 14 ^ l13;
/* 249:298 */       l15 += l14;
/* 250:299 */       l14 = l14 << 10 ^ l14 >>> 54 ^ l15;
/* 251:300 */       l9 += l12;
/* 252:301 */       l12 = l12 << 17 ^ l12 >>> 47 ^ l9;
/* 253:302 */       l13 += l10;
/* 254:303 */       l10 = l10 << 25 ^ l10 >>> 39 ^ l13;
/* 255:304 */       l15 += l12;
/* 256:305 */       l12 = l12 << 29 ^ l12 >>> 35 ^ l15;
/* 257:306 */       l9 += l14;
/* 258:307 */       l14 = l14 << 39 ^ l14 >>> 25 ^ l9;
/* 259:308 */       l11 += l16;
/* 260:309 */       l16 = l16 << 43 ^ l16 >>> 21 ^ l11;
/* 261:310 */       l15 += l10;
/* 262:311 */       l10 = l10 << 8 ^ l10 >>> 56 ^ l15;
/* 263:312 */       l9 += l16;
/* 264:313 */       l16 = l16 << 35 ^ l16 >>> 29 ^ l9;
/* 265:314 */       l11 += l14;
/* 266:315 */       l14 = l14 << 56 ^ l14 >>> 8 ^ l11;
/* 267:316 */       l13 += l12;
/* 268:317 */       l12 = l12 << 22 ^ l12 >>> 42 ^ l13;
/* 269:318 */       long l20 = l19;
/* 270:319 */       l19 = l18;
/* 271:320 */       l18 = l17;
/* 272:321 */       l17 = l20;
/* 273:    */     }
/* 274:323 */     l9 += this.h[18];
/* 275:324 */     l10 += this.h[19];
/* 276:325 */     l11 += this.h[20];
/* 277:326 */     l12 += this.h[21];
/* 278:327 */     l13 += this.h[22];
/* 279:328 */     l14 += this.h[23] + l17;
/* 280:329 */     l15 += this.h[24] + l18;
/* 281:330 */     l16 += this.h[25] + 18L;
/* 282:331 */     this.h[0] = (l1 ^ l9);
/* 283:332 */     this.h[1] = (l2 ^ l10);
/* 284:333 */     this.h[2] = (l3 ^ l11);
/* 285:334 */     this.h[3] = (l4 ^ l12);
/* 286:335 */     this.h[4] = (l5 ^ l13);
/* 287:336 */     this.h[5] = (l6 ^ l14);
/* 288:337 */     this.h[6] = (l7 ^ l15);
/* 289:338 */     this.h[7] = (l8 ^ l16);
/* 290:    */   }
/* 291:    */   
/* 292:    */   public String toString()
/* 293:    */   {
/* 294:344 */     return "Skein-" + (getDigestLength() << 3);
/* 295:    */   }
/* 296:    */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     fr.cryptohash.SkeinBigCore
 * JD-Core Version:    0.7.1
 */