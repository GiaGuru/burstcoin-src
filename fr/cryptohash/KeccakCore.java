/*   1:    */ package fr.cryptohash;
/*   2:    */ 
/*   3:    */ abstract class KeccakCore
/*   4:    */   extends DigestEngine
/*   5:    */ {
/*   6:    */   private long[] A;
/*   7:    */   private byte[] tmpOut;
/*   8: 49 */   private static final long[] RC = { 1L, 32898L, -9223372036854742902L, -9223372034707259392L, 32907L, 2147483649L, -9223372034707259263L, -9223372036854743031L, 138L, 136L, 2147516425L, 2147483658L, 2147516555L, -9223372036854775669L, -9223372036854742903L, -9223372036854743037L, -9223372036854743038L, -9223372036854775680L, 32778L, -9223372034707292150L, -9223372034707259263L, -9223372036854742912L, 2147483649L, -9223372034707259384L };
/*   9:    */   
/*  10:    */   private static final void encodeLELong(long paramLong, byte[] paramArrayOfByte, int paramInt)
/*  11:    */   {
/*  12: 75 */     paramArrayOfByte[(paramInt + 0)] = ((byte)(int)paramLong);
/*  13: 76 */     paramArrayOfByte[(paramInt + 1)] = ((byte)(int)(paramLong >>> 8));
/*  14: 77 */     paramArrayOfByte[(paramInt + 2)] = ((byte)(int)(paramLong >>> 16));
/*  15: 78 */     paramArrayOfByte[(paramInt + 3)] = ((byte)(int)(paramLong >>> 24));
/*  16: 79 */     paramArrayOfByte[(paramInt + 4)] = ((byte)(int)(paramLong >>> 32));
/*  17: 80 */     paramArrayOfByte[(paramInt + 5)] = ((byte)(int)(paramLong >>> 40));
/*  18: 81 */     paramArrayOfByte[(paramInt + 6)] = ((byte)(int)(paramLong >>> 48));
/*  19: 82 */     paramArrayOfByte[(paramInt + 7)] = ((byte)(int)(paramLong >>> 56));
/*  20:    */   }
/*  21:    */   
/*  22:    */   private static final long decodeLELong(byte[] paramArrayOfByte, int paramInt)
/*  23:    */   {
/*  24: 95 */     return paramArrayOfByte[(paramInt + 0)] & 0xFF | (paramArrayOfByte[(paramInt + 1)] & 0xFF) << 8 | (paramArrayOfByte[(paramInt + 2)] & 0xFF) << 16 | (paramArrayOfByte[(paramInt + 3)] & 0xFF) << 24 | (paramArrayOfByte[(paramInt + 4)] & 0xFF) << 32 | (paramArrayOfByte[(paramInt + 5)] & 0xFF) << 40 | (paramArrayOfByte[(paramInt + 6)] & 0xFF) << 48 | (paramArrayOfByte[(paramInt + 7)] & 0xFF) << 56;
/*  25:    */   }
/*  26:    */   
/*  27:    */   protected void engineReset()
/*  28:    */   {
/*  29:108 */     doReset();
/*  30:    */   }
/*  31:    */   
/*  32:    */   protected void processBlock(byte[] paramArrayOfByte)
/*  33:    */   {
/*  34:115 */     for (int i = 0; i < paramArrayOfByte.length; i += 8) {
/*  35:116 */       this.A[(i >>> 3)] ^= decodeLELong(paramArrayOfByte, i);
/*  36:    */     }
/*  37:129 */     for (int j = 0; j < 24; j += 2)
/*  38:    */     {
/*  39:131 */       long l6 = this.A[1] ^ this.A[6];
/*  40:132 */       long l7 = this.A[11] ^ this.A[16];
/*  41:133 */       l6 ^= this.A[21] ^ l7;
/*  42:134 */       l6 = l6 << 1 | l6 >>> 63;
/*  43:135 */       long l8 = this.A[4] ^ this.A[9];
/*  44:136 */       long l9 = this.A[14] ^ this.A[19];
/*  45:137 */       l6 ^= this.A[24];
/*  46:138 */       l8 ^= l9;
/*  47:139 */       long l1 = l6 ^ l8;
/*  48:    */       
/*  49:141 */       l6 = this.A[2] ^ this.A[7];
/*  50:142 */       l7 = this.A[12] ^ this.A[17];
/*  51:143 */       l6 ^= this.A[22] ^ l7;
/*  52:144 */       l6 = l6 << 1 | l6 >>> 63;
/*  53:145 */       l8 = this.A[0] ^ this.A[5];
/*  54:146 */       l9 = this.A[10] ^ this.A[15];
/*  55:147 */       l6 ^= this.A[20];
/*  56:148 */       l8 ^= l9;
/*  57:149 */       long l2 = l6 ^ l8;
/*  58:    */       
/*  59:151 */       l6 = this.A[3] ^ this.A[8];
/*  60:152 */       l7 = this.A[13] ^ this.A[18];
/*  61:153 */       l6 ^= this.A[23] ^ l7;
/*  62:154 */       l6 = l6 << 1 | l6 >>> 63;
/*  63:155 */       l8 = this.A[1] ^ this.A[6];
/*  64:156 */       l9 = this.A[11] ^ this.A[16];
/*  65:157 */       l6 ^= this.A[21];
/*  66:158 */       l8 ^= l9;
/*  67:159 */       long l3 = l6 ^ l8;
/*  68:    */       
/*  69:161 */       l6 = this.A[4] ^ this.A[9];
/*  70:162 */       l7 = this.A[14] ^ this.A[19];
/*  71:163 */       l6 ^= this.A[24] ^ l7;
/*  72:164 */       l6 = l6 << 1 | l6 >>> 63;
/*  73:165 */       l8 = this.A[2] ^ this.A[7];
/*  74:166 */       l9 = this.A[12] ^ this.A[17];
/*  75:167 */       l6 ^= this.A[22];
/*  76:168 */       l8 ^= l9;
/*  77:169 */       long l4 = l6 ^ l8;
/*  78:    */       
/*  79:171 */       l6 = this.A[0] ^ this.A[5];
/*  80:172 */       l7 = this.A[10] ^ this.A[15];
/*  81:173 */       l6 ^= this.A[20] ^ l7;
/*  82:174 */       l6 = l6 << 1 | l6 >>> 63;
/*  83:175 */       l8 = this.A[3] ^ this.A[8];
/*  84:176 */       l9 = this.A[13] ^ this.A[18];
/*  85:177 */       l6 ^= this.A[23];
/*  86:178 */       l8 ^= l9;
/*  87:179 */       long l5 = l6 ^ l8;
/*  88:    */       
/*  89:181 */       this.A[0] ^= l1;
/*  90:182 */       this.A[5] ^= l1;
/*  91:183 */       this.A[10] ^= l1;
/*  92:184 */       this.A[15] ^= l1;
/*  93:185 */       this.A[20] ^= l1;
/*  94:186 */       this.A[1] ^= l2;
/*  95:187 */       this.A[6] ^= l2;
/*  96:188 */       this.A[11] ^= l2;
/*  97:189 */       this.A[16] ^= l2;
/*  98:190 */       this.A[21] ^= l2;
/*  99:191 */       this.A[2] ^= l3;
/* 100:192 */       this.A[7] ^= l3;
/* 101:193 */       this.A[12] ^= l3;
/* 102:194 */       this.A[17] ^= l3;
/* 103:195 */       this.A[22] ^= l3;
/* 104:196 */       this.A[3] ^= l4;
/* 105:197 */       this.A[8] ^= l4;
/* 106:198 */       this.A[13] ^= l4;
/* 107:199 */       this.A[18] ^= l4;
/* 108:200 */       this.A[23] ^= l4;
/* 109:201 */       this.A[4] ^= l5;
/* 110:202 */       this.A[9] ^= l5;
/* 111:203 */       this.A[14] ^= l5;
/* 112:204 */       this.A[19] ^= l5;
/* 113:205 */       this.A[24] ^= l5;
/* 114:206 */       this.A[5] = (this.A[5] << 36 | this.A[5] >>> 28);
/* 115:207 */       this.A[10] = (this.A[10] << 3 | this.A[10] >>> 61);
/* 116:208 */       this.A[15] = (this.A[15] << 41 | this.A[15] >>> 23);
/* 117:209 */       this.A[20] = (this.A[20] << 18 | this.A[20] >>> 46);
/* 118:210 */       this.A[1] = (this.A[1] << 1 | this.A[1] >>> 63);
/* 119:211 */       this.A[6] = (this.A[6] << 44 | this.A[6] >>> 20);
/* 120:212 */       this.A[11] = (this.A[11] << 10 | this.A[11] >>> 54);
/* 121:213 */       this.A[16] = (this.A[16] << 45 | this.A[16] >>> 19);
/* 122:214 */       this.A[21] = (this.A[21] << 2 | this.A[21] >>> 62);
/* 123:215 */       this.A[2] = (this.A[2] << 62 | this.A[2] >>> 2);
/* 124:216 */       this.A[7] = (this.A[7] << 6 | this.A[7] >>> 58);
/* 125:217 */       this.A[12] = (this.A[12] << 43 | this.A[12] >>> 21);
/* 126:218 */       this.A[17] = (this.A[17] << 15 | this.A[17] >>> 49);
/* 127:219 */       this.A[22] = (this.A[22] << 61 | this.A[22] >>> 3);
/* 128:220 */       this.A[3] = (this.A[3] << 28 | this.A[3] >>> 36);
/* 129:221 */       this.A[8] = (this.A[8] << 55 | this.A[8] >>> 9);
/* 130:222 */       this.A[13] = (this.A[13] << 25 | this.A[13] >>> 39);
/* 131:223 */       this.A[18] = (this.A[18] << 21 | this.A[18] >>> 43);
/* 132:224 */       this.A[23] = (this.A[23] << 56 | this.A[23] >>> 8);
/* 133:225 */       this.A[4] = (this.A[4] << 27 | this.A[4] >>> 37);
/* 134:226 */       this.A[9] = (this.A[9] << 20 | this.A[9] >>> 44);
/* 135:227 */       this.A[14] = (this.A[14] << 39 | this.A[14] >>> 25);
/* 136:228 */       this.A[19] = (this.A[19] << 8 | this.A[19] >>> 56);
/* 137:229 */       this.A[24] = (this.A[24] << 14 | this.A[24] >>> 50);
/* 138:230 */       long l17 = this.A[12] ^ 0xFFFFFFFF;
/* 139:231 */       long l11 = this.A[6] | this.A[12];
/* 140:232 */       long l12 = this.A[0] ^ l11;
/* 141:233 */       l11 = l17 | this.A[18];
/* 142:234 */       long l13 = this.A[6] ^ l11;
/* 143:235 */       l11 = this.A[18] & this.A[24];
/* 144:236 */       long l14 = this.A[12] ^ l11;
/* 145:237 */       l11 = this.A[24] | this.A[0];
/* 146:238 */       long l15 = this.A[18] ^ l11;
/* 147:239 */       l11 = this.A[0] & this.A[6];
/* 148:240 */       long l16 = this.A[24] ^ l11;
/* 149:241 */       this.A[0] = l12;
/* 150:242 */       this.A[6] = l13;
/* 151:243 */       this.A[12] = l14;
/* 152:244 */       this.A[18] = l15;
/* 153:245 */       this.A[24] = l16;
/* 154:246 */       l17 = this.A[22] ^ 0xFFFFFFFF;
/* 155:247 */       l11 = this.A[9] | this.A[10];
/* 156:248 */       l12 = this.A[3] ^ l11;
/* 157:249 */       l11 = this.A[10] & this.A[16];
/* 158:250 */       l13 = this.A[9] ^ l11;
/* 159:251 */       l11 = this.A[16] | l17;
/* 160:252 */       l14 = this.A[10] ^ l11;
/* 161:253 */       l11 = this.A[22] | this.A[3];
/* 162:254 */       l15 = this.A[16] ^ l11;
/* 163:255 */       l11 = this.A[3] & this.A[9];
/* 164:256 */       l16 = this.A[22] ^ l11;
/* 165:257 */       this.A[3] = l12;
/* 166:258 */       this.A[9] = l13;
/* 167:259 */       this.A[10] = l14;
/* 168:260 */       this.A[16] = l15;
/* 169:261 */       this.A[22] = l16;
/* 170:262 */       l17 = this.A[19] ^ 0xFFFFFFFF;
/* 171:263 */       l11 = this.A[7] | this.A[13];
/* 172:264 */       l12 = this.A[1] ^ l11;
/* 173:265 */       l11 = this.A[13] & this.A[19];
/* 174:266 */       l13 = this.A[7] ^ l11;
/* 175:267 */       l11 = l17 & this.A[20];
/* 176:268 */       l14 = this.A[13] ^ l11;
/* 177:269 */       l11 = this.A[20] | this.A[1];
/* 178:270 */       l15 = l17 ^ l11;
/* 179:271 */       l11 = this.A[1] & this.A[7];
/* 180:272 */       l16 = this.A[20] ^ l11;
/* 181:273 */       this.A[1] = l12;
/* 182:274 */       this.A[7] = l13;
/* 183:275 */       this.A[13] = l14;
/* 184:276 */       this.A[19] = l15;
/* 185:277 */       this.A[20] = l16;
/* 186:278 */       l17 = this.A[17] ^ 0xFFFFFFFF;
/* 187:279 */       l11 = this.A[5] & this.A[11];
/* 188:280 */       l12 = this.A[4] ^ l11;
/* 189:281 */       l11 = this.A[11] | this.A[17];
/* 190:282 */       l13 = this.A[5] ^ l11;
/* 191:283 */       l11 = l17 | this.A[23];
/* 192:284 */       l14 = this.A[11] ^ l11;
/* 193:285 */       l11 = this.A[23] & this.A[4];
/* 194:286 */       l15 = l17 ^ l11;
/* 195:287 */       l11 = this.A[4] | this.A[5];
/* 196:288 */       l16 = this.A[23] ^ l11;
/* 197:289 */       this.A[4] = l12;
/* 198:290 */       this.A[5] = l13;
/* 199:291 */       this.A[11] = l14;
/* 200:292 */       this.A[17] = l15;
/* 201:293 */       this.A[23] = l16;
/* 202:294 */       l17 = this.A[8] ^ 0xFFFFFFFF;
/* 203:295 */       l11 = l17 & this.A[14];
/* 204:296 */       l12 = this.A[2] ^ l11;
/* 205:297 */       l11 = this.A[14] | this.A[15];
/* 206:298 */       l13 = l17 ^ l11;
/* 207:299 */       l11 = this.A[15] & this.A[21];
/* 208:300 */       l14 = this.A[14] ^ l11;
/* 209:301 */       l11 = this.A[21] | this.A[2];
/* 210:302 */       l15 = this.A[15] ^ l11;
/* 211:303 */       l11 = this.A[2] & this.A[8];
/* 212:304 */       l16 = this.A[21] ^ l11;
/* 213:305 */       this.A[2] = l12;
/* 214:306 */       this.A[8] = l13;
/* 215:307 */       this.A[14] = l14;
/* 216:308 */       this.A[15] = l15;
/* 217:309 */       this.A[21] = l16;
/* 218:310 */       this.A[0] ^= RC[(j + 0)];
/* 219:    */       
/* 220:312 */       l6 = this.A[6] ^ this.A[9];
/* 221:313 */       l7 = this.A[7] ^ this.A[5];
/* 222:314 */       l6 ^= this.A[8] ^ l7;
/* 223:315 */       l6 = l6 << 1 | l6 >>> 63;
/* 224:316 */       l8 = this.A[24] ^ this.A[22];
/* 225:317 */       l9 = this.A[20] ^ this.A[23];
/* 226:318 */       l6 ^= this.A[21];
/* 227:319 */       l8 ^= l9;
/* 228:320 */       l1 = l6 ^ l8;
/* 229:    */       
/* 230:322 */       l6 = this.A[12] ^ this.A[10];
/* 231:323 */       l7 = this.A[13] ^ this.A[11];
/* 232:324 */       l6 ^= this.A[14] ^ l7;
/* 233:325 */       l6 = l6 << 1 | l6 >>> 63;
/* 234:326 */       l8 = this.A[0] ^ this.A[3];
/* 235:327 */       l9 = this.A[1] ^ this.A[4];
/* 236:328 */       l6 ^= this.A[2];
/* 237:329 */       l8 ^= l9;
/* 238:330 */       l2 = l6 ^ l8;
/* 239:    */       
/* 240:332 */       l6 = this.A[18] ^ this.A[16];
/* 241:333 */       l7 = this.A[19] ^ this.A[17];
/* 242:334 */       l6 ^= this.A[15] ^ l7;
/* 243:335 */       l6 = l6 << 1 | l6 >>> 63;
/* 244:336 */       l8 = this.A[6] ^ this.A[9];
/* 245:337 */       l9 = this.A[7] ^ this.A[5];
/* 246:338 */       l6 ^= this.A[8];
/* 247:339 */       l8 ^= l9;
/* 248:340 */       l3 = l6 ^ l8;
/* 249:    */       
/* 250:342 */       l6 = this.A[24] ^ this.A[22];
/* 251:343 */       l7 = this.A[20] ^ this.A[23];
/* 252:344 */       l6 ^= this.A[21] ^ l7;
/* 253:345 */       l6 = l6 << 1 | l6 >>> 63;
/* 254:346 */       l8 = this.A[12] ^ this.A[10];
/* 255:347 */       l9 = this.A[13] ^ this.A[11];
/* 256:348 */       l6 ^= this.A[14];
/* 257:349 */       l8 ^= l9;
/* 258:350 */       l4 = l6 ^ l8;
/* 259:    */       
/* 260:352 */       l6 = this.A[0] ^ this.A[3];
/* 261:353 */       l7 = this.A[1] ^ this.A[4];
/* 262:354 */       l6 ^= this.A[2] ^ l7;
/* 263:355 */       l6 = l6 << 1 | l6 >>> 63;
/* 264:356 */       l8 = this.A[18] ^ this.A[16];
/* 265:357 */       l9 = this.A[19] ^ this.A[17];
/* 266:358 */       l6 ^= this.A[15];
/* 267:359 */       l8 ^= l9;
/* 268:360 */       l5 = l6 ^ l8;
/* 269:    */       
/* 270:362 */       this.A[0] ^= l1;
/* 271:363 */       this.A[3] ^= l1;
/* 272:364 */       this.A[1] ^= l1;
/* 273:365 */       this.A[4] ^= l1;
/* 274:366 */       this.A[2] ^= l1;
/* 275:367 */       this.A[6] ^= l2;
/* 276:368 */       this.A[9] ^= l2;
/* 277:369 */       this.A[7] ^= l2;
/* 278:370 */       this.A[5] ^= l2;
/* 279:371 */       this.A[8] ^= l2;
/* 280:372 */       this.A[12] ^= l3;
/* 281:373 */       this.A[10] ^= l3;
/* 282:374 */       this.A[13] ^= l3;
/* 283:375 */       this.A[11] ^= l3;
/* 284:376 */       this.A[14] ^= l3;
/* 285:377 */       this.A[18] ^= l4;
/* 286:378 */       this.A[16] ^= l4;
/* 287:379 */       this.A[19] ^= l4;
/* 288:380 */       this.A[17] ^= l4;
/* 289:381 */       this.A[15] ^= l4;
/* 290:382 */       this.A[24] ^= l5;
/* 291:383 */       this.A[22] ^= l5;
/* 292:384 */       this.A[20] ^= l5;
/* 293:385 */       this.A[23] ^= l5;
/* 294:386 */       this.A[21] ^= l5;
/* 295:387 */       this.A[3] = (this.A[3] << 36 | this.A[3] >>> 28);
/* 296:388 */       this.A[1] = (this.A[1] << 3 | this.A[1] >>> 61);
/* 297:389 */       this.A[4] = (this.A[4] << 41 | this.A[4] >>> 23);
/* 298:390 */       this.A[2] = (this.A[2] << 18 | this.A[2] >>> 46);
/* 299:391 */       this.A[6] = (this.A[6] << 1 | this.A[6] >>> 63);
/* 300:392 */       this.A[9] = (this.A[9] << 44 | this.A[9] >>> 20);
/* 301:393 */       this.A[7] = (this.A[7] << 10 | this.A[7] >>> 54);
/* 302:394 */       this.A[5] = (this.A[5] << 45 | this.A[5] >>> 19);
/* 303:395 */       this.A[8] = (this.A[8] << 2 | this.A[8] >>> 62);
/* 304:396 */       this.A[12] = (this.A[12] << 62 | this.A[12] >>> 2);
/* 305:397 */       this.A[10] = (this.A[10] << 6 | this.A[10] >>> 58);
/* 306:398 */       this.A[13] = (this.A[13] << 43 | this.A[13] >>> 21);
/* 307:399 */       this.A[11] = (this.A[11] << 15 | this.A[11] >>> 49);
/* 308:400 */       this.A[14] = (this.A[14] << 61 | this.A[14] >>> 3);
/* 309:401 */       this.A[18] = (this.A[18] << 28 | this.A[18] >>> 36);
/* 310:402 */       this.A[16] = (this.A[16] << 55 | this.A[16] >>> 9);
/* 311:403 */       this.A[19] = (this.A[19] << 25 | this.A[19] >>> 39);
/* 312:404 */       this.A[17] = (this.A[17] << 21 | this.A[17] >>> 43);
/* 313:405 */       this.A[15] = (this.A[15] << 56 | this.A[15] >>> 8);
/* 314:406 */       this.A[24] = (this.A[24] << 27 | this.A[24] >>> 37);
/* 315:407 */       this.A[22] = (this.A[22] << 20 | this.A[22] >>> 44);
/* 316:408 */       this.A[20] = (this.A[20] << 39 | this.A[20] >>> 25);
/* 317:409 */       this.A[23] = (this.A[23] << 8 | this.A[23] >>> 56);
/* 318:410 */       this.A[21] = (this.A[21] << 14 | this.A[21] >>> 50);
/* 319:411 */       l17 = this.A[13] ^ 0xFFFFFFFF;
/* 320:412 */       l11 = this.A[9] | this.A[13];
/* 321:413 */       l12 = this.A[0] ^ l11;
/* 322:414 */       l11 = l17 | this.A[17];
/* 323:415 */       l13 = this.A[9] ^ l11;
/* 324:416 */       l11 = this.A[17] & this.A[21];
/* 325:417 */       l14 = this.A[13] ^ l11;
/* 326:418 */       l11 = this.A[21] | this.A[0];
/* 327:419 */       l15 = this.A[17] ^ l11;
/* 328:420 */       l11 = this.A[0] & this.A[9];
/* 329:421 */       l16 = this.A[21] ^ l11;
/* 330:422 */       this.A[0] = l12;
/* 331:423 */       this.A[9] = l13;
/* 332:424 */       this.A[13] = l14;
/* 333:425 */       this.A[17] = l15;
/* 334:426 */       this.A[21] = l16;
/* 335:427 */       l17 = this.A[14] ^ 0xFFFFFFFF;
/* 336:428 */       l11 = this.A[22] | this.A[1];
/* 337:429 */       l12 = this.A[18] ^ l11;
/* 338:430 */       l11 = this.A[1] & this.A[5];
/* 339:431 */       l13 = this.A[22] ^ l11;
/* 340:432 */       l11 = this.A[5] | l17;
/* 341:433 */       l14 = this.A[1] ^ l11;
/* 342:434 */       l11 = this.A[14] | this.A[18];
/* 343:435 */       l15 = this.A[5] ^ l11;
/* 344:436 */       l11 = this.A[18] & this.A[22];
/* 345:437 */       l16 = this.A[14] ^ l11;
/* 346:438 */       this.A[18] = l12;
/* 347:439 */       this.A[22] = l13;
/* 348:440 */       this.A[1] = l14;
/* 349:441 */       this.A[5] = l15;
/* 350:442 */       this.A[14] = l16;
/* 351:443 */       l17 = this.A[23] ^ 0xFFFFFFFF;
/* 352:444 */       l11 = this.A[10] | this.A[19];
/* 353:445 */       l12 = this.A[6] ^ l11;
/* 354:446 */       l11 = this.A[19] & this.A[23];
/* 355:447 */       l13 = this.A[10] ^ l11;
/* 356:448 */       l11 = l17 & this.A[2];
/* 357:449 */       l14 = this.A[19] ^ l11;
/* 358:450 */       l11 = this.A[2] | this.A[6];
/* 359:451 */       l15 = l17 ^ l11;
/* 360:452 */       l11 = this.A[6] & this.A[10];
/* 361:453 */       l16 = this.A[2] ^ l11;
/* 362:454 */       this.A[6] = l12;
/* 363:455 */       this.A[10] = l13;
/* 364:456 */       this.A[19] = l14;
/* 365:457 */       this.A[23] = l15;
/* 366:458 */       this.A[2] = l16;
/* 367:459 */       l17 = this.A[11] ^ 0xFFFFFFFF;
/* 368:460 */       l11 = this.A[3] & this.A[7];
/* 369:461 */       l12 = this.A[24] ^ l11;
/* 370:462 */       l11 = this.A[7] | this.A[11];
/* 371:463 */       l13 = this.A[3] ^ l11;
/* 372:464 */       l11 = l17 | this.A[15];
/* 373:465 */       l14 = this.A[7] ^ l11;
/* 374:466 */       l11 = this.A[15] & this.A[24];
/* 375:467 */       l15 = l17 ^ l11;
/* 376:468 */       l11 = this.A[24] | this.A[3];
/* 377:469 */       l16 = this.A[15] ^ l11;
/* 378:470 */       this.A[24] = l12;
/* 379:471 */       this.A[3] = l13;
/* 380:472 */       this.A[7] = l14;
/* 381:473 */       this.A[11] = l15;
/* 382:474 */       this.A[15] = l16;
/* 383:475 */       l17 = this.A[16] ^ 0xFFFFFFFF;
/* 384:476 */       l11 = l17 & this.A[20];
/* 385:477 */       l12 = this.A[12] ^ l11;
/* 386:478 */       l11 = this.A[20] | this.A[4];
/* 387:479 */       l13 = l17 ^ l11;
/* 388:480 */       l11 = this.A[4] & this.A[8];
/* 389:481 */       l14 = this.A[20] ^ l11;
/* 390:482 */       l11 = this.A[8] | this.A[12];
/* 391:483 */       l15 = this.A[4] ^ l11;
/* 392:484 */       l11 = this.A[12] & this.A[16];
/* 393:485 */       l16 = this.A[8] ^ l11;
/* 394:486 */       this.A[12] = l12;
/* 395:487 */       this.A[16] = l13;
/* 396:488 */       this.A[20] = l14;
/* 397:489 */       this.A[4] = l15;
/* 398:490 */       this.A[8] = l16;
/* 399:491 */       this.A[0] ^= RC[(j + 1)];
/* 400:492 */       long l10 = this.A[5];
/* 401:493 */       this.A[5] = this.A[18];
/* 402:494 */       this.A[18] = this.A[11];
/* 403:495 */       this.A[11] = this.A[10];
/* 404:496 */       this.A[10] = this.A[6];
/* 405:497 */       this.A[6] = this.A[22];
/* 406:498 */       this.A[22] = this.A[20];
/* 407:499 */       this.A[20] = this.A[12];
/* 408:500 */       this.A[12] = this.A[19];
/* 409:501 */       this.A[19] = this.A[15];
/* 410:502 */       this.A[15] = this.A[24];
/* 411:503 */       this.A[24] = this.A[8];
/* 412:504 */       this.A[8] = l10;
/* 413:505 */       l10 = this.A[1];
/* 414:506 */       this.A[1] = this.A[9];
/* 415:507 */       this.A[9] = this.A[14];
/* 416:508 */       this.A[14] = this.A[2];
/* 417:509 */       this.A[2] = this.A[13];
/* 418:510 */       this.A[13] = this.A[23];
/* 419:511 */       this.A[23] = this.A[4];
/* 420:512 */       this.A[4] = this.A[21];
/* 421:513 */       this.A[21] = this.A[16];
/* 422:514 */       this.A[16] = this.A[3];
/* 423:515 */       this.A[3] = this.A[17];
/* 424:516 */       this.A[17] = this.A[7];
/* 425:517 */       this.A[7] = l10;
/* 426:    */     }
/* 427:    */   }
/* 428:    */   
/* 429:    */   protected void doPadding(byte[] paramArrayOfByte, int paramInt)
/* 430:    */   {
/* 431:524 */     int i = flush();
/* 432:525 */     byte[] arrayOfByte = getBlockBuffer();
/* 433:526 */     if (i + 1 == arrayOfByte.length)
/* 434:    */     {
/* 435:527 */       arrayOfByte[i] = -127;
/* 436:    */     }
/* 437:    */     else
/* 438:    */     {
/* 439:529 */       arrayOfByte[i] = 1;
/* 440:530 */       for (j = i + 1; j < arrayOfByte.length - 1; j++) {
/* 441:531 */         arrayOfByte[j] = 0;
/* 442:    */       }
/* 443:532 */       arrayOfByte[(arrayOfByte.length - 1)] = Byte.MIN_VALUE;
/* 444:    */     }
/* 445:534 */     processBlock(arrayOfByte);
/* 446:535 */     this.A[1] ^= 0xFFFFFFFF;
/* 447:536 */     this.A[2] ^= 0xFFFFFFFF;
/* 448:537 */     this.A[8] ^= 0xFFFFFFFF;
/* 449:538 */     this.A[12] ^= 0xFFFFFFFF;
/* 450:539 */     this.A[17] ^= 0xFFFFFFFF;
/* 451:540 */     this.A[20] ^= 0xFFFFFFFF;
/* 452:541 */     int j = getDigestLength();
/* 453:542 */     for (int k = 0; k < j; k += 8) {
/* 454:543 */       encodeLELong(this.A[(k >>> 3)], this.tmpOut, k);
/* 455:    */     }
/* 456:544 */     System.arraycopy(this.tmpOut, 0, paramArrayOfByte, paramInt, j);
/* 457:    */   }
/* 458:    */   
/* 459:    */   protected void doInit()
/* 460:    */   {
/* 461:550 */     this.A = new long[25];
/* 462:551 */     this.tmpOut = new byte[getDigestLength() + 7 & 0xFFFFFFF8];
/* 463:552 */     doReset();
/* 464:    */   }
/* 465:    */   
/* 466:    */   public int getBlockLength()
/* 467:    */   {
/* 468:558 */     return 200 - 2 * getDigestLength();
/* 469:    */   }
/* 470:    */   
/* 471:    */   private final void doReset()
/* 472:    */   {
/* 473:563 */     for (int i = 0; i < 25; i++) {
/* 474:564 */       this.A[i] = 0L;
/* 475:    */     }
/* 476:565 */     this.A[1] = -1L;
/* 477:566 */     this.A[2] = -1L;
/* 478:567 */     this.A[8] = -1L;
/* 479:568 */     this.A[12] = -1L;
/* 480:569 */     this.A[17] = -1L;
/* 481:570 */     this.A[20] = -1L;
/* 482:    */   }
/* 483:    */   
/* 484:    */   protected Digest copyState(KeccakCore paramKeccakCore)
/* 485:    */   {
/* 486:576 */     System.arraycopy(this.A, 0, paramKeccakCore.A, 0, 25);
/* 487:577 */     return super.copyState(paramKeccakCore);
/* 488:    */   }
/* 489:    */   
/* 490:    */   public String toString()
/* 491:    */   {
/* 492:583 */     return "Keccak-" + (getDigestLength() << 3);
/* 493:    */   }
/* 494:    */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     fr.cryptohash.KeccakCore
 * JD-Core Version:    0.7.1
 */