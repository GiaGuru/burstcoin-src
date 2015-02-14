/*   1:    */ package fr.cryptohash;
/*   2:    */ 
/*   3:    */ public class Fugue384
/*   4:    */   extends FugueCore
/*   5:    */ {
/*   6: 50 */   private static final int[] initVal = { -1436423155, 824520223, -1608665913, 6293893, 559871050, 1947950748, -93765990, 1195290688, -452809078, -1449384480, -1131064964, 1544590753 };
/*   7:    */   
/*   8:    */   int[] getIV()
/*   9:    */   {
/*  10: 59 */     return initVal;
/*  11:    */   }
/*  12:    */   
/*  13:    */   public int getDigestLength()
/*  14:    */   {
/*  15: 65 */     return 48;
/*  16:    */   }
/*  17:    */   
/*  18:    */   FugueCore dup()
/*  19:    */   {
/*  20: 71 */     return new Fugue384();
/*  21:    */   }
/*  22:    */   
/*  23:    */   void process(int paramInt1, byte[] paramArrayOfByte, int paramInt2, int paramInt3)
/*  24:    */   {
/*  25: 77 */     int[] arrayOfInt = this.S;
/*  26: 78 */     switch (this.rshift)
/*  27:    */     {
/*  28:    */     case 1: 
/*  29: 80 */       arrayOfInt[7] ^= arrayOfInt[27];
/*  30: 81 */       arrayOfInt[27] = paramInt1;
/*  31: 82 */       arrayOfInt[35] ^= arrayOfInt[27];
/*  32: 83 */       arrayOfInt[28] ^= arrayOfInt[18];
/*  33: 84 */       arrayOfInt[31] ^= arrayOfInt[21];
/*  34: 85 */       arrayOfInt[24] ^= arrayOfInt[28];
/*  35: 86 */       arrayOfInt[25] ^= arrayOfInt[29];
/*  36: 87 */       arrayOfInt[26] ^= arrayOfInt[30];
/*  37: 88 */       arrayOfInt[6] ^= arrayOfInt[28];
/*  38: 89 */       arrayOfInt[7] ^= arrayOfInt[29];
/*  39: 90 */       arrayOfInt[8] ^= arrayOfInt[30];
/*  40: 91 */       smix(24, 25, 26, 27);
/*  41: 92 */       arrayOfInt[21] ^= arrayOfInt[25];
/*  42: 93 */       arrayOfInt[22] ^= arrayOfInt[26];
/*  43: 94 */       arrayOfInt[23] ^= arrayOfInt[27];
/*  44: 95 */       arrayOfInt[3] ^= arrayOfInt[25];
/*  45: 96 */       arrayOfInt[4] ^= arrayOfInt[26];
/*  46: 97 */       arrayOfInt[5] ^= arrayOfInt[27];
/*  47: 98 */       smix(21, 22, 23, 24);
/*  48: 99 */       arrayOfInt[18] ^= arrayOfInt[22];
/*  49:100 */       arrayOfInt[19] ^= arrayOfInt[23];
/*  50:101 */       arrayOfInt[20] ^= arrayOfInt[24];
/*  51:102 */       arrayOfInt[0] ^= arrayOfInt[22];
/*  52:103 */       arrayOfInt[1] ^= arrayOfInt[23];
/*  53:104 */       arrayOfInt[2] ^= arrayOfInt[24];
/*  54:105 */       smix(18, 19, 20, 21);
/*  55:106 */       if (paramInt3-- <= 0)
/*  56:    */       {
/*  57:107 */         this.rshift = 2;
/*  58:108 */         return;
/*  59:    */       }
/*  60:110 */       paramInt1 = paramArrayOfByte[paramInt2] << 24 | (paramArrayOfByte[(paramInt2 + 1)] & 0xFF) << 16 | (paramArrayOfByte[(paramInt2 + 2)] & 0xFF) << 8 | paramArrayOfByte[(paramInt2 + 3)] & 0xFF;
/*  61:    */       
/*  62:    */ 
/*  63:    */ 
/*  64:114 */       paramInt2 += 4;
/*  65:    */     case 2: 
/*  66:117 */       arrayOfInt[34] ^= arrayOfInt[18];
/*  67:118 */       arrayOfInt[18] = paramInt1;
/*  68:119 */       arrayOfInt[26] ^= arrayOfInt[18];
/*  69:120 */       arrayOfInt[19] ^= arrayOfInt[9];
/*  70:121 */       arrayOfInt[22] ^= arrayOfInt[12];
/*  71:122 */       arrayOfInt[15] ^= arrayOfInt[19];
/*  72:123 */       arrayOfInt[16] ^= arrayOfInt[20];
/*  73:124 */       arrayOfInt[17] ^= arrayOfInt[21];
/*  74:125 */       arrayOfInt[33] ^= arrayOfInt[19];
/*  75:126 */       arrayOfInt[34] ^= arrayOfInt[20];
/*  76:127 */       arrayOfInt[35] ^= arrayOfInt[21];
/*  77:128 */       smix(15, 16, 17, 18);
/*  78:129 */       arrayOfInt[12] ^= arrayOfInt[16];
/*  79:130 */       arrayOfInt[13] ^= arrayOfInt[17];
/*  80:131 */       arrayOfInt[14] ^= arrayOfInt[18];
/*  81:132 */       arrayOfInt[30] ^= arrayOfInt[16];
/*  82:133 */       arrayOfInt[31] ^= arrayOfInt[17];
/*  83:134 */       arrayOfInt[32] ^= arrayOfInt[18];
/*  84:135 */       smix(12, 13, 14, 15);
/*  85:136 */       arrayOfInt[9] ^= arrayOfInt[13];
/*  86:137 */       arrayOfInt[10] ^= arrayOfInt[14];
/*  87:138 */       arrayOfInt[11] ^= arrayOfInt[15];
/*  88:139 */       arrayOfInt[27] ^= arrayOfInt[13];
/*  89:140 */       arrayOfInt[28] ^= arrayOfInt[14];
/*  90:141 */       arrayOfInt[29] ^= arrayOfInt[15];
/*  91:142 */       smix(9, 10, 11, 12);
/*  92:143 */       if (paramInt3-- <= 0)
/*  93:    */       {
/*  94:144 */         this.rshift = 3;
/*  95:145 */         return;
/*  96:    */       }
/*  97:147 */       paramInt1 = paramArrayOfByte[paramInt2] << 24 | (paramArrayOfByte[(paramInt2 + 1)] & 0xFF) << 16 | (paramArrayOfByte[(paramInt2 + 2)] & 0xFF) << 8 | paramArrayOfByte[(paramInt2 + 3)] & 0xFF;
/*  98:    */       
/*  99:    */ 
/* 100:    */ 
/* 101:151 */       paramInt2 += 4;
/* 102:    */     case 3: 
/* 103:154 */       arrayOfInt[25] ^= arrayOfInt[9];
/* 104:155 */       arrayOfInt[9] = paramInt1;
/* 105:156 */       arrayOfInt[17] ^= arrayOfInt[9];
/* 106:157 */       arrayOfInt[10] ^= arrayOfInt[0];
/* 107:158 */       arrayOfInt[13] ^= arrayOfInt[3];
/* 108:159 */       arrayOfInt[6] ^= arrayOfInt[10];
/* 109:160 */       arrayOfInt[7] ^= arrayOfInt[11];
/* 110:161 */       arrayOfInt[8] ^= arrayOfInt[12];
/* 111:162 */       arrayOfInt[24] ^= arrayOfInt[10];
/* 112:163 */       arrayOfInt[25] ^= arrayOfInt[11];
/* 113:164 */       arrayOfInt[26] ^= arrayOfInt[12];
/* 114:165 */       smix(6, 7, 8, 9);
/* 115:166 */       arrayOfInt[3] ^= arrayOfInt[7];
/* 116:167 */       arrayOfInt[4] ^= arrayOfInt[8];
/* 117:168 */       arrayOfInt[5] ^= arrayOfInt[9];
/* 118:169 */       arrayOfInt[21] ^= arrayOfInt[7];
/* 119:170 */       arrayOfInt[22] ^= arrayOfInt[8];
/* 120:171 */       arrayOfInt[23] ^= arrayOfInt[9];
/* 121:172 */       smix(3, 4, 5, 6);
/* 122:173 */       arrayOfInt[0] ^= arrayOfInt[4];
/* 123:174 */       arrayOfInt[1] ^= arrayOfInt[5];
/* 124:175 */       arrayOfInt[2] ^= arrayOfInt[6];
/* 125:176 */       arrayOfInt[18] ^= arrayOfInt[4];
/* 126:177 */       arrayOfInt[19] ^= arrayOfInt[5];
/* 127:178 */       arrayOfInt[20] ^= arrayOfInt[6];
/* 128:179 */       smix(0, 1, 2, 3);
/* 129:180 */       if (paramInt3-- <= 0)
/* 130:    */       {
/* 131:181 */         this.rshift = 0;
/* 132:182 */         return;
/* 133:    */       }
/* 134:184 */       paramInt1 = paramArrayOfByte[paramInt2] << 24 | (paramArrayOfByte[(paramInt2 + 1)] & 0xFF) << 16 | (paramArrayOfByte[(paramInt2 + 2)] & 0xFF) << 8 | paramArrayOfByte[(paramInt2 + 3)] & 0xFF;
/* 135:    */       
/* 136:    */ 
/* 137:    */ 
/* 138:188 */       paramInt2 += 4;
/* 139:    */     }
/* 140:    */     for (;;)
/* 141:    */     {
/* 142:192 */       arrayOfInt[16] ^= arrayOfInt[0];
/* 143:193 */       arrayOfInt[0] = paramInt1;
/* 144:194 */       arrayOfInt[8] ^= arrayOfInt[0];
/* 145:195 */       arrayOfInt[1] ^= arrayOfInt[27];
/* 146:196 */       arrayOfInt[4] ^= arrayOfInt[30];
/* 147:197 */       arrayOfInt[33] ^= arrayOfInt[1];
/* 148:198 */       arrayOfInt[34] ^= arrayOfInt[2];
/* 149:199 */       arrayOfInt[35] ^= arrayOfInt[3];
/* 150:200 */       arrayOfInt[15] ^= arrayOfInt[1];
/* 151:201 */       arrayOfInt[16] ^= arrayOfInt[2];
/* 152:202 */       arrayOfInt[17] ^= arrayOfInt[3];
/* 153:203 */       smix(33, 34, 35, 0);
/* 154:204 */       arrayOfInt[30] ^= arrayOfInt[34];
/* 155:205 */       arrayOfInt[31] ^= arrayOfInt[35];
/* 156:206 */       arrayOfInt[32] ^= arrayOfInt[0];
/* 157:207 */       arrayOfInt[12] ^= arrayOfInt[34];
/* 158:208 */       arrayOfInt[13] ^= arrayOfInt[35];
/* 159:209 */       arrayOfInt[14] ^= arrayOfInt[0];
/* 160:210 */       smix(30, 31, 32, 33);
/* 161:211 */       arrayOfInt[27] ^= arrayOfInt[31];
/* 162:212 */       arrayOfInt[28] ^= arrayOfInt[32];
/* 163:213 */       arrayOfInt[29] ^= arrayOfInt[33];
/* 164:214 */       arrayOfInt[9] ^= arrayOfInt[31];
/* 165:215 */       arrayOfInt[10] ^= arrayOfInt[32];
/* 166:216 */       arrayOfInt[11] ^= arrayOfInt[33];
/* 167:217 */       smix(27, 28, 29, 30);
/* 168:218 */       if (paramInt3-- <= 0)
/* 169:    */       {
/* 170:219 */         this.rshift = 1;
/* 171:220 */         return;
/* 172:    */       }
/* 173:222 */       paramInt1 = paramArrayOfByte[paramInt2] << 24 | (paramArrayOfByte[(paramInt2 + 1)] & 0xFF) << 16 | (paramArrayOfByte[(paramInt2 + 2)] & 0xFF) << 8 | paramArrayOfByte[(paramInt2 + 3)] & 0xFF;
/* 174:    */       
/* 175:    */ 
/* 176:    */ 
/* 177:226 */       paramInt2 += 4;
/* 178:    */       
/* 179:228 */       arrayOfInt[7] ^= arrayOfInt[27];
/* 180:229 */       arrayOfInt[27] = paramInt1;
/* 181:230 */       arrayOfInt[35] ^= arrayOfInt[27];
/* 182:231 */       arrayOfInt[28] ^= arrayOfInt[18];
/* 183:232 */       arrayOfInt[31] ^= arrayOfInt[21];
/* 184:233 */       arrayOfInt[24] ^= arrayOfInt[28];
/* 185:234 */       arrayOfInt[25] ^= arrayOfInt[29];
/* 186:235 */       arrayOfInt[26] ^= arrayOfInt[30];
/* 187:236 */       arrayOfInt[6] ^= arrayOfInt[28];
/* 188:237 */       arrayOfInt[7] ^= arrayOfInt[29];
/* 189:238 */       arrayOfInt[8] ^= arrayOfInt[30];
/* 190:239 */       smix(24, 25, 26, 27);
/* 191:240 */       arrayOfInt[21] ^= arrayOfInt[25];
/* 192:241 */       arrayOfInt[22] ^= arrayOfInt[26];
/* 193:242 */       arrayOfInt[23] ^= arrayOfInt[27];
/* 194:243 */       arrayOfInt[3] ^= arrayOfInt[25];
/* 195:244 */       arrayOfInt[4] ^= arrayOfInt[26];
/* 196:245 */       arrayOfInt[5] ^= arrayOfInt[27];
/* 197:246 */       smix(21, 22, 23, 24);
/* 198:247 */       arrayOfInt[18] ^= arrayOfInt[22];
/* 199:248 */       arrayOfInt[19] ^= arrayOfInt[23];
/* 200:249 */       arrayOfInt[20] ^= arrayOfInt[24];
/* 201:250 */       arrayOfInt[0] ^= arrayOfInt[22];
/* 202:251 */       arrayOfInt[1] ^= arrayOfInt[23];
/* 203:252 */       arrayOfInt[2] ^= arrayOfInt[24];
/* 204:253 */       smix(18, 19, 20, 21);
/* 205:254 */       if (paramInt3-- <= 0)
/* 206:    */       {
/* 207:255 */         this.rshift = 2;
/* 208:256 */         return;
/* 209:    */       }
/* 210:258 */       paramInt1 = paramArrayOfByte[paramInt2] << 24 | (paramArrayOfByte[(paramInt2 + 1)] & 0xFF) << 16 | (paramArrayOfByte[(paramInt2 + 2)] & 0xFF) << 8 | paramArrayOfByte[(paramInt2 + 3)] & 0xFF;
/* 211:    */       
/* 212:    */ 
/* 213:    */ 
/* 214:262 */       paramInt2 += 4;
/* 215:    */       
/* 216:264 */       arrayOfInt[34] ^= arrayOfInt[18];
/* 217:265 */       arrayOfInt[18] = paramInt1;
/* 218:266 */       arrayOfInt[26] ^= arrayOfInt[18];
/* 219:267 */       arrayOfInt[19] ^= arrayOfInt[9];
/* 220:268 */       arrayOfInt[22] ^= arrayOfInt[12];
/* 221:269 */       arrayOfInt[15] ^= arrayOfInt[19];
/* 222:270 */       arrayOfInt[16] ^= arrayOfInt[20];
/* 223:271 */       arrayOfInt[17] ^= arrayOfInt[21];
/* 224:272 */       arrayOfInt[33] ^= arrayOfInt[19];
/* 225:273 */       arrayOfInt[34] ^= arrayOfInt[20];
/* 226:274 */       arrayOfInt[35] ^= arrayOfInt[21];
/* 227:275 */       smix(15, 16, 17, 18);
/* 228:276 */       arrayOfInt[12] ^= arrayOfInt[16];
/* 229:277 */       arrayOfInt[13] ^= arrayOfInt[17];
/* 230:278 */       arrayOfInt[14] ^= arrayOfInt[18];
/* 231:279 */       arrayOfInt[30] ^= arrayOfInt[16];
/* 232:280 */       arrayOfInt[31] ^= arrayOfInt[17];
/* 233:281 */       arrayOfInt[32] ^= arrayOfInt[18];
/* 234:282 */       smix(12, 13, 14, 15);
/* 235:283 */       arrayOfInt[9] ^= arrayOfInt[13];
/* 236:284 */       arrayOfInt[10] ^= arrayOfInt[14];
/* 237:285 */       arrayOfInt[11] ^= arrayOfInt[15];
/* 238:286 */       arrayOfInt[27] ^= arrayOfInt[13];
/* 239:287 */       arrayOfInt[28] ^= arrayOfInt[14];
/* 240:288 */       arrayOfInt[29] ^= arrayOfInt[15];
/* 241:289 */       smix(9, 10, 11, 12);
/* 242:290 */       if (paramInt3-- <= 0)
/* 243:    */       {
/* 244:291 */         this.rshift = 3;
/* 245:292 */         return;
/* 246:    */       }
/* 247:294 */       paramInt1 = paramArrayOfByte[paramInt2] << 24 | (paramArrayOfByte[(paramInt2 + 1)] & 0xFF) << 16 | (paramArrayOfByte[(paramInt2 + 2)] & 0xFF) << 8 | paramArrayOfByte[(paramInt2 + 3)] & 0xFF;
/* 248:    */       
/* 249:    */ 
/* 250:    */ 
/* 251:298 */       paramInt2 += 4;
/* 252:    */       
/* 253:300 */       arrayOfInt[25] ^= arrayOfInt[9];
/* 254:301 */       arrayOfInt[9] = paramInt1;
/* 255:302 */       arrayOfInt[17] ^= arrayOfInt[9];
/* 256:303 */       arrayOfInt[10] ^= arrayOfInt[0];
/* 257:304 */       arrayOfInt[13] ^= arrayOfInt[3];
/* 258:305 */       arrayOfInt[6] ^= arrayOfInt[10];
/* 259:306 */       arrayOfInt[7] ^= arrayOfInt[11];
/* 260:307 */       arrayOfInt[8] ^= arrayOfInt[12];
/* 261:308 */       arrayOfInt[24] ^= arrayOfInt[10];
/* 262:309 */       arrayOfInt[25] ^= arrayOfInt[11];
/* 263:310 */       arrayOfInt[26] ^= arrayOfInt[12];
/* 264:311 */       smix(6, 7, 8, 9);
/* 265:312 */       arrayOfInt[3] ^= arrayOfInt[7];
/* 266:313 */       arrayOfInt[4] ^= arrayOfInt[8];
/* 267:314 */       arrayOfInt[5] ^= arrayOfInt[9];
/* 268:315 */       arrayOfInt[21] ^= arrayOfInt[7];
/* 269:316 */       arrayOfInt[22] ^= arrayOfInt[8];
/* 270:317 */       arrayOfInt[23] ^= arrayOfInt[9];
/* 271:318 */       smix(3, 4, 5, 6);
/* 272:319 */       arrayOfInt[0] ^= arrayOfInt[4];
/* 273:320 */       arrayOfInt[1] ^= arrayOfInt[5];
/* 274:321 */       arrayOfInt[2] ^= arrayOfInt[6];
/* 275:322 */       arrayOfInt[18] ^= arrayOfInt[4];
/* 276:323 */       arrayOfInt[19] ^= arrayOfInt[5];
/* 277:324 */       arrayOfInt[20] ^= arrayOfInt[6];
/* 278:325 */       smix(0, 1, 2, 3);
/* 279:326 */       if (paramInt3-- <= 0)
/* 280:    */       {
/* 281:327 */         this.rshift = 0;
/* 282:328 */         return;
/* 283:    */       }
/* 284:330 */       paramInt1 = paramArrayOfByte[paramInt2] << 24 | (paramArrayOfByte[(paramInt2 + 1)] & 0xFF) << 16 | (paramArrayOfByte[(paramInt2 + 2)] & 0xFF) << 8 | paramArrayOfByte[(paramInt2 + 3)] & 0xFF;
/* 285:    */       
/* 286:    */ 
/* 287:    */ 
/* 288:334 */       paramInt2 += 4;
/* 289:    */     }
/* 290:    */   }
/* 291:    */   
/* 292:    */   void processFinal(byte[] paramArrayOfByte)
/* 293:    */   {
/* 294:341 */     int[] arrayOfInt = this.S;
/* 295:342 */     ror(9 * this.rshift, 36);
/* 296:343 */     for (int i = 0; i < 18; i++)
/* 297:    */     {
/* 298:344 */       ror(3, 36);
/* 299:345 */       cmix36();
/* 300:346 */       smix(0, 1, 2, 3);
/* 301:    */     }
/* 302:348 */     for (i = 0; i < 13; i++)
/* 303:    */     {
/* 304:349 */       arrayOfInt[4] ^= arrayOfInt[0];
/* 305:350 */       arrayOfInt[12] ^= arrayOfInt[0];
/* 306:351 */       arrayOfInt[24] ^= arrayOfInt[0];
/* 307:352 */       ror(12, 36);
/* 308:353 */       smix(0, 1, 2, 3);
/* 309:354 */       arrayOfInt[4] ^= arrayOfInt[0];
/* 310:355 */       arrayOfInt[13] ^= arrayOfInt[0];
/* 311:356 */       arrayOfInt[24] ^= arrayOfInt[0];
/* 312:357 */       ror(12, 36);
/* 313:358 */       smix(0, 1, 2, 3);
/* 314:359 */       arrayOfInt[4] ^= arrayOfInt[0];
/* 315:360 */       arrayOfInt[13] ^= arrayOfInt[0];
/* 316:361 */       arrayOfInt[25] ^= arrayOfInt[0];
/* 317:362 */       ror(11, 36);
/* 318:363 */       smix(0, 1, 2, 3);
/* 319:    */     }
/* 320:365 */     arrayOfInt[4] ^= arrayOfInt[0];
/* 321:366 */     arrayOfInt[12] ^= arrayOfInt[0];
/* 322:367 */     arrayOfInt[24] ^= arrayOfInt[0];
/* 323:368 */     encodeBEInt(arrayOfInt[1], paramArrayOfByte, 0);
/* 324:369 */     encodeBEInt(arrayOfInt[2], paramArrayOfByte, 4);
/* 325:370 */     encodeBEInt(arrayOfInt[3], paramArrayOfByte, 8);
/* 326:371 */     encodeBEInt(arrayOfInt[4], paramArrayOfByte, 12);
/* 327:372 */     encodeBEInt(arrayOfInt[12], paramArrayOfByte, 16);
/* 328:373 */     encodeBEInt(arrayOfInt[13], paramArrayOfByte, 20);
/* 329:374 */     encodeBEInt(arrayOfInt[14], paramArrayOfByte, 24);
/* 330:375 */     encodeBEInt(arrayOfInt[15], paramArrayOfByte, 28);
/* 331:376 */     encodeBEInt(arrayOfInt[24], paramArrayOfByte, 32);
/* 332:377 */     encodeBEInt(arrayOfInt[25], paramArrayOfByte, 36);
/* 333:378 */     encodeBEInt(arrayOfInt[26], paramArrayOfByte, 40);
/* 334:379 */     encodeBEInt(arrayOfInt[27], paramArrayOfByte, 44);
/* 335:    */   }
/* 336:    */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     fr.cryptohash.Fugue384
 * JD-Core Version:    0.7.1
 */