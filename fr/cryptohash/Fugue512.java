/*   1:    */ package fr.cryptohash;
/*   2:    */ 
/*   3:    */ public class Fugue512
/*   4:    */   extends FugueCore
/*   5:    */ {
/*   6: 50 */   private static final int[] initVal = { -2012764802, -434720907, -975969061, -1399148505, -652873449, -1225864108, 115868171, 1251143633, -1429806391, -575532136, -890873800, 1132404799, 636123367, -1793073706, -630271715, -516016793 };
/*   7:    */   
/*   8:    */   int[] getIV()
/*   9:    */   {
/*  10: 60 */     return initVal;
/*  11:    */   }
/*  12:    */   
/*  13:    */   public int getDigestLength()
/*  14:    */   {
/*  15: 66 */     return 64;
/*  16:    */   }
/*  17:    */   
/*  18:    */   FugueCore dup()
/*  19:    */   {
/*  20: 72 */     return new Fugue512();
/*  21:    */   }
/*  22:    */   
/*  23:    */   void process(int paramInt1, byte[] paramArrayOfByte, int paramInt2, int paramInt3)
/*  24:    */   {
/*  25: 78 */     int[] arrayOfInt = this.S;
/*  26: 79 */     switch (this.rshift)
/*  27:    */     {
/*  28:    */     case 1: 
/*  29: 81 */       arrayOfInt[10] ^= arrayOfInt[24];
/*  30: 82 */       arrayOfInt[24] = paramInt1;
/*  31: 83 */       arrayOfInt[32] ^= arrayOfInt[24];
/*  32: 84 */       arrayOfInt[25] ^= arrayOfInt[12];
/*  33: 85 */       arrayOfInt[28] ^= arrayOfInt[15];
/*  34: 86 */       arrayOfInt[31] ^= arrayOfInt[18];
/*  35: 87 */       arrayOfInt[21] ^= arrayOfInt[25];
/*  36: 88 */       arrayOfInt[22] ^= arrayOfInt[26];
/*  37: 89 */       arrayOfInt[23] ^= arrayOfInt[27];
/*  38: 90 */       arrayOfInt[3] ^= arrayOfInt[25];
/*  39: 91 */       arrayOfInt[4] ^= arrayOfInt[26];
/*  40: 92 */       arrayOfInt[5] ^= arrayOfInt[27];
/*  41: 93 */       smix(21, 22, 23, 24);
/*  42: 94 */       arrayOfInt[18] ^= arrayOfInt[22];
/*  43: 95 */       arrayOfInt[19] ^= arrayOfInt[23];
/*  44: 96 */       arrayOfInt[20] ^= arrayOfInt[24];
/*  45: 97 */       arrayOfInt[0] ^= arrayOfInt[22];
/*  46: 98 */       arrayOfInt[1] ^= arrayOfInt[23];
/*  47: 99 */       arrayOfInt[2] ^= arrayOfInt[24];
/*  48:100 */       smix(18, 19, 20, 21);
/*  49:101 */       arrayOfInt[15] ^= arrayOfInt[19];
/*  50:102 */       arrayOfInt[16] ^= arrayOfInt[20];
/*  51:103 */       arrayOfInt[17] ^= arrayOfInt[21];
/*  52:104 */       arrayOfInt[33] ^= arrayOfInt[19];
/*  53:105 */       arrayOfInt[34] ^= arrayOfInt[20];
/*  54:106 */       arrayOfInt[35] ^= arrayOfInt[21];
/*  55:107 */       smix(15, 16, 17, 18);
/*  56:108 */       arrayOfInt[12] ^= arrayOfInt[16];
/*  57:109 */       arrayOfInt[13] ^= arrayOfInt[17];
/*  58:110 */       arrayOfInt[14] ^= arrayOfInt[18];
/*  59:111 */       arrayOfInt[30] ^= arrayOfInt[16];
/*  60:112 */       arrayOfInt[31] ^= arrayOfInt[17];
/*  61:113 */       arrayOfInt[32] ^= arrayOfInt[18];
/*  62:114 */       smix(12, 13, 14, 15);
/*  63:115 */       if (paramInt3-- <= 0)
/*  64:    */       {
/*  65:116 */         this.rshift = 2;
/*  66:117 */         return;
/*  67:    */       }
/*  68:119 */       paramInt1 = paramArrayOfByte[paramInt2] << 24 | (paramArrayOfByte[(paramInt2 + 1)] & 0xFF) << 16 | (paramArrayOfByte[(paramInt2 + 2)] & 0xFF) << 8 | paramArrayOfByte[(paramInt2 + 3)] & 0xFF;
/*  69:    */       
/*  70:    */ 
/*  71:    */ 
/*  72:123 */       paramInt2 += 4;
/*  73:    */     case 2: 
/*  74:126 */       arrayOfInt[34] ^= arrayOfInt[12];
/*  75:127 */       arrayOfInt[12] = paramInt1;
/*  76:128 */       arrayOfInt[20] ^= arrayOfInt[12];
/*  77:129 */       arrayOfInt[13] ^= arrayOfInt[0];
/*  78:130 */       arrayOfInt[16] ^= arrayOfInt[3];
/*  79:131 */       arrayOfInt[19] ^= arrayOfInt[6];
/*  80:132 */       arrayOfInt[9] ^= arrayOfInt[13];
/*  81:133 */       arrayOfInt[10] ^= arrayOfInt[14];
/*  82:134 */       arrayOfInt[11] ^= arrayOfInt[15];
/*  83:135 */       arrayOfInt[27] ^= arrayOfInt[13];
/*  84:136 */       arrayOfInt[28] ^= arrayOfInt[14];
/*  85:137 */       arrayOfInt[29] ^= arrayOfInt[15];
/*  86:138 */       smix(9, 10, 11, 12);
/*  87:139 */       arrayOfInt[6] ^= arrayOfInt[10];
/*  88:140 */       arrayOfInt[7] ^= arrayOfInt[11];
/*  89:141 */       arrayOfInt[8] ^= arrayOfInt[12];
/*  90:142 */       arrayOfInt[24] ^= arrayOfInt[10];
/*  91:143 */       arrayOfInt[25] ^= arrayOfInt[11];
/*  92:144 */       arrayOfInt[26] ^= arrayOfInt[12];
/*  93:145 */       smix(6, 7, 8, 9);
/*  94:146 */       arrayOfInt[3] ^= arrayOfInt[7];
/*  95:147 */       arrayOfInt[4] ^= arrayOfInt[8];
/*  96:148 */       arrayOfInt[5] ^= arrayOfInt[9];
/*  97:149 */       arrayOfInt[21] ^= arrayOfInt[7];
/*  98:150 */       arrayOfInt[22] ^= arrayOfInt[8];
/*  99:151 */       arrayOfInt[23] ^= arrayOfInt[9];
/* 100:152 */       smix(3, 4, 5, 6);
/* 101:153 */       arrayOfInt[0] ^= arrayOfInt[4];
/* 102:154 */       arrayOfInt[1] ^= arrayOfInt[5];
/* 103:155 */       arrayOfInt[2] ^= arrayOfInt[6];
/* 104:156 */       arrayOfInt[18] ^= arrayOfInt[4];
/* 105:157 */       arrayOfInt[19] ^= arrayOfInt[5];
/* 106:158 */       arrayOfInt[20] ^= arrayOfInt[6];
/* 107:159 */       smix(0, 1, 2, 3);
/* 108:160 */       if (paramInt3-- <= 0)
/* 109:    */       {
/* 110:161 */         this.rshift = 0;
/* 111:162 */         return;
/* 112:    */       }
/* 113:164 */       paramInt1 = paramArrayOfByte[paramInt2] << 24 | (paramArrayOfByte[(paramInt2 + 1)] & 0xFF) << 16 | (paramArrayOfByte[(paramInt2 + 2)] & 0xFF) << 8 | paramArrayOfByte[(paramInt2 + 3)] & 0xFF;
/* 114:    */       
/* 115:    */ 
/* 116:    */ 
/* 117:168 */       paramInt2 += 4;
/* 118:    */     }
/* 119:    */     for (;;)
/* 120:    */     {
/* 121:172 */       arrayOfInt[22] ^= arrayOfInt[0];
/* 122:173 */       arrayOfInt[0] = paramInt1;
/* 123:174 */       arrayOfInt[8] ^= arrayOfInt[0];
/* 124:175 */       arrayOfInt[1] ^= arrayOfInt[24];
/* 125:176 */       arrayOfInt[4] ^= arrayOfInt[27];
/* 126:177 */       arrayOfInt[7] ^= arrayOfInt[30];
/* 127:178 */       arrayOfInt[33] ^= arrayOfInt[1];
/* 128:179 */       arrayOfInt[34] ^= arrayOfInt[2];
/* 129:180 */       arrayOfInt[35] ^= arrayOfInt[3];
/* 130:181 */       arrayOfInt[15] ^= arrayOfInt[1];
/* 131:182 */       arrayOfInt[16] ^= arrayOfInt[2];
/* 132:183 */       arrayOfInt[17] ^= arrayOfInt[3];
/* 133:184 */       smix(33, 34, 35, 0);
/* 134:185 */       arrayOfInt[30] ^= arrayOfInt[34];
/* 135:186 */       arrayOfInt[31] ^= arrayOfInt[35];
/* 136:187 */       arrayOfInt[32] ^= arrayOfInt[0];
/* 137:188 */       arrayOfInt[12] ^= arrayOfInt[34];
/* 138:189 */       arrayOfInt[13] ^= arrayOfInt[35];
/* 139:190 */       arrayOfInt[14] ^= arrayOfInt[0];
/* 140:191 */       smix(30, 31, 32, 33);
/* 141:192 */       arrayOfInt[27] ^= arrayOfInt[31];
/* 142:193 */       arrayOfInt[28] ^= arrayOfInt[32];
/* 143:194 */       arrayOfInt[29] ^= arrayOfInt[33];
/* 144:195 */       arrayOfInt[9] ^= arrayOfInt[31];
/* 145:196 */       arrayOfInt[10] ^= arrayOfInt[32];
/* 146:197 */       arrayOfInt[11] ^= arrayOfInt[33];
/* 147:198 */       smix(27, 28, 29, 30);
/* 148:199 */       arrayOfInt[24] ^= arrayOfInt[28];
/* 149:200 */       arrayOfInt[25] ^= arrayOfInt[29];
/* 150:201 */       arrayOfInt[26] ^= arrayOfInt[30];
/* 151:202 */       arrayOfInt[6] ^= arrayOfInt[28];
/* 152:203 */       arrayOfInt[7] ^= arrayOfInt[29];
/* 153:204 */       arrayOfInt[8] ^= arrayOfInt[30];
/* 154:205 */       smix(24, 25, 26, 27);
/* 155:206 */       if (paramInt3-- <= 0)
/* 156:    */       {
/* 157:207 */         this.rshift = 1;
/* 158:208 */         return;
/* 159:    */       }
/* 160:210 */       paramInt1 = paramArrayOfByte[paramInt2] << 24 | (paramArrayOfByte[(paramInt2 + 1)] & 0xFF) << 16 | (paramArrayOfByte[(paramInt2 + 2)] & 0xFF) << 8 | paramArrayOfByte[(paramInt2 + 3)] & 0xFF;
/* 161:    */       
/* 162:    */ 
/* 163:    */ 
/* 164:214 */       paramInt2 += 4;
/* 165:    */       
/* 166:216 */       arrayOfInt[10] ^= arrayOfInt[24];
/* 167:217 */       arrayOfInt[24] = paramInt1;
/* 168:218 */       arrayOfInt[32] ^= arrayOfInt[24];
/* 169:219 */       arrayOfInt[25] ^= arrayOfInt[12];
/* 170:220 */       arrayOfInt[28] ^= arrayOfInt[15];
/* 171:221 */       arrayOfInt[31] ^= arrayOfInt[18];
/* 172:222 */       arrayOfInt[21] ^= arrayOfInt[25];
/* 173:223 */       arrayOfInt[22] ^= arrayOfInt[26];
/* 174:224 */       arrayOfInt[23] ^= arrayOfInt[27];
/* 175:225 */       arrayOfInt[3] ^= arrayOfInt[25];
/* 176:226 */       arrayOfInt[4] ^= arrayOfInt[26];
/* 177:227 */       arrayOfInt[5] ^= arrayOfInt[27];
/* 178:228 */       smix(21, 22, 23, 24);
/* 179:229 */       arrayOfInt[18] ^= arrayOfInt[22];
/* 180:230 */       arrayOfInt[19] ^= arrayOfInt[23];
/* 181:231 */       arrayOfInt[20] ^= arrayOfInt[24];
/* 182:232 */       arrayOfInt[0] ^= arrayOfInt[22];
/* 183:233 */       arrayOfInt[1] ^= arrayOfInt[23];
/* 184:234 */       arrayOfInt[2] ^= arrayOfInt[24];
/* 185:235 */       smix(18, 19, 20, 21);
/* 186:236 */       arrayOfInt[15] ^= arrayOfInt[19];
/* 187:237 */       arrayOfInt[16] ^= arrayOfInt[20];
/* 188:238 */       arrayOfInt[17] ^= arrayOfInt[21];
/* 189:239 */       arrayOfInt[33] ^= arrayOfInt[19];
/* 190:240 */       arrayOfInt[34] ^= arrayOfInt[20];
/* 191:241 */       arrayOfInt[35] ^= arrayOfInt[21];
/* 192:242 */       smix(15, 16, 17, 18);
/* 193:243 */       arrayOfInt[12] ^= arrayOfInt[16];
/* 194:244 */       arrayOfInt[13] ^= arrayOfInt[17];
/* 195:245 */       arrayOfInt[14] ^= arrayOfInt[18];
/* 196:246 */       arrayOfInt[30] ^= arrayOfInt[16];
/* 197:247 */       arrayOfInt[31] ^= arrayOfInt[17];
/* 198:248 */       arrayOfInt[32] ^= arrayOfInt[18];
/* 199:249 */       smix(12, 13, 14, 15);
/* 200:250 */       if (paramInt3-- <= 0)
/* 201:    */       {
/* 202:251 */         this.rshift = 2;
/* 203:252 */         return;
/* 204:    */       }
/* 205:254 */       paramInt1 = paramArrayOfByte[paramInt2] << 24 | (paramArrayOfByte[(paramInt2 + 1)] & 0xFF) << 16 | (paramArrayOfByte[(paramInt2 + 2)] & 0xFF) << 8 | paramArrayOfByte[(paramInt2 + 3)] & 0xFF;
/* 206:    */       
/* 207:    */ 
/* 208:    */ 
/* 209:258 */       paramInt2 += 4;
/* 210:    */       
/* 211:260 */       arrayOfInt[34] ^= arrayOfInt[12];
/* 212:261 */       arrayOfInt[12] = paramInt1;
/* 213:262 */       arrayOfInt[20] ^= arrayOfInt[12];
/* 214:263 */       arrayOfInt[13] ^= arrayOfInt[0];
/* 215:264 */       arrayOfInt[16] ^= arrayOfInt[3];
/* 216:265 */       arrayOfInt[19] ^= arrayOfInt[6];
/* 217:266 */       arrayOfInt[9] ^= arrayOfInt[13];
/* 218:267 */       arrayOfInt[10] ^= arrayOfInt[14];
/* 219:268 */       arrayOfInt[11] ^= arrayOfInt[15];
/* 220:269 */       arrayOfInt[27] ^= arrayOfInt[13];
/* 221:270 */       arrayOfInt[28] ^= arrayOfInt[14];
/* 222:271 */       arrayOfInt[29] ^= arrayOfInt[15];
/* 223:272 */       smix(9, 10, 11, 12);
/* 224:273 */       arrayOfInt[6] ^= arrayOfInt[10];
/* 225:274 */       arrayOfInt[7] ^= arrayOfInt[11];
/* 226:275 */       arrayOfInt[8] ^= arrayOfInt[12];
/* 227:276 */       arrayOfInt[24] ^= arrayOfInt[10];
/* 228:277 */       arrayOfInt[25] ^= arrayOfInt[11];
/* 229:278 */       arrayOfInt[26] ^= arrayOfInt[12];
/* 230:279 */       smix(6, 7, 8, 9);
/* 231:280 */       arrayOfInt[3] ^= arrayOfInt[7];
/* 232:281 */       arrayOfInt[4] ^= arrayOfInt[8];
/* 233:282 */       arrayOfInt[5] ^= arrayOfInt[9];
/* 234:283 */       arrayOfInt[21] ^= arrayOfInt[7];
/* 235:284 */       arrayOfInt[22] ^= arrayOfInt[8];
/* 236:285 */       arrayOfInt[23] ^= arrayOfInt[9];
/* 237:286 */       smix(3, 4, 5, 6);
/* 238:287 */       arrayOfInt[0] ^= arrayOfInt[4];
/* 239:288 */       arrayOfInt[1] ^= arrayOfInt[5];
/* 240:289 */       arrayOfInt[2] ^= arrayOfInt[6];
/* 241:290 */       arrayOfInt[18] ^= arrayOfInt[4];
/* 242:291 */       arrayOfInt[19] ^= arrayOfInt[5];
/* 243:292 */       arrayOfInt[20] ^= arrayOfInt[6];
/* 244:293 */       smix(0, 1, 2, 3);
/* 245:294 */       if (paramInt3-- <= 0)
/* 246:    */       {
/* 247:295 */         this.rshift = 0;
/* 248:296 */         return;
/* 249:    */       }
/* 250:298 */       paramInt1 = paramArrayOfByte[paramInt2] << 24 | (paramArrayOfByte[(paramInt2 + 1)] & 0xFF) << 16 | (paramArrayOfByte[(paramInt2 + 2)] & 0xFF) << 8 | paramArrayOfByte[(paramInt2 + 3)] & 0xFF;
/* 251:    */       
/* 252:    */ 
/* 253:    */ 
/* 254:302 */       paramInt2 += 4;
/* 255:    */     }
/* 256:    */   }
/* 257:    */   
/* 258:    */   void processFinal(byte[] paramArrayOfByte)
/* 259:    */   {
/* 260:309 */     int[] arrayOfInt = this.S;
/* 261:310 */     ror(12 * this.rshift, 36);
/* 262:311 */     for (int i = 0; i < 32; i++)
/* 263:    */     {
/* 264:312 */       ror(3, 36);
/* 265:313 */       cmix36();
/* 266:314 */       smix(0, 1, 2, 3);
/* 267:    */     }
/* 268:316 */     for (i = 0; i < 13; i++)
/* 269:    */     {
/* 270:317 */       arrayOfInt[4] ^= arrayOfInt[0];
/* 271:318 */       arrayOfInt[9] ^= arrayOfInt[0];
/* 272:319 */       arrayOfInt[18] ^= arrayOfInt[0];
/* 273:320 */       arrayOfInt[27] ^= arrayOfInt[0];
/* 274:321 */       ror(9, 36);
/* 275:322 */       smix(0, 1, 2, 3);
/* 276:323 */       arrayOfInt[4] ^= arrayOfInt[0];
/* 277:324 */       arrayOfInt[10] ^= arrayOfInt[0];
/* 278:325 */       arrayOfInt[18] ^= arrayOfInt[0];
/* 279:326 */       arrayOfInt[27] ^= arrayOfInt[0];
/* 280:327 */       ror(9, 36);
/* 281:328 */       smix(0, 1, 2, 3);
/* 282:329 */       arrayOfInt[4] ^= arrayOfInt[0];
/* 283:330 */       arrayOfInt[10] ^= arrayOfInt[0];
/* 284:331 */       arrayOfInt[19] ^= arrayOfInt[0];
/* 285:332 */       arrayOfInt[27] ^= arrayOfInt[0];
/* 286:333 */       ror(9, 36);
/* 287:334 */       smix(0, 1, 2, 3);
/* 288:335 */       arrayOfInt[4] ^= arrayOfInt[0];
/* 289:336 */       arrayOfInt[10] ^= arrayOfInt[0];
/* 290:337 */       arrayOfInt[19] ^= arrayOfInt[0];
/* 291:338 */       arrayOfInt[28] ^= arrayOfInt[0];
/* 292:339 */       ror(8, 36);
/* 293:340 */       smix(0, 1, 2, 3);
/* 294:    */     }
/* 295:342 */     arrayOfInt[4] ^= arrayOfInt[0];
/* 296:343 */     arrayOfInt[9] ^= arrayOfInt[0];
/* 297:344 */     arrayOfInt[18] ^= arrayOfInt[0];
/* 298:345 */     arrayOfInt[27] ^= arrayOfInt[0];
/* 299:346 */     encodeBEInt(arrayOfInt[1], paramArrayOfByte, 0);
/* 300:347 */     encodeBEInt(arrayOfInt[2], paramArrayOfByte, 4);
/* 301:348 */     encodeBEInt(arrayOfInt[3], paramArrayOfByte, 8);
/* 302:349 */     encodeBEInt(arrayOfInt[4], paramArrayOfByte, 12);
/* 303:350 */     encodeBEInt(arrayOfInt[9], paramArrayOfByte, 16);
/* 304:351 */     encodeBEInt(arrayOfInt[10], paramArrayOfByte, 20);
/* 305:352 */     encodeBEInt(arrayOfInt[11], paramArrayOfByte, 24);
/* 306:353 */     encodeBEInt(arrayOfInt[12], paramArrayOfByte, 28);
/* 307:354 */     encodeBEInt(arrayOfInt[18], paramArrayOfByte, 32);
/* 308:355 */     encodeBEInt(arrayOfInt[19], paramArrayOfByte, 36);
/* 309:356 */     encodeBEInt(arrayOfInt[20], paramArrayOfByte, 40);
/* 310:357 */     encodeBEInt(arrayOfInt[21], paramArrayOfByte, 44);
/* 311:358 */     encodeBEInt(arrayOfInt[27], paramArrayOfByte, 48);
/* 312:359 */     encodeBEInt(arrayOfInt[28], paramArrayOfByte, 52);
/* 313:360 */     encodeBEInt(arrayOfInt[29], paramArrayOfByte, 56);
/* 314:361 */     encodeBEInt(arrayOfInt[30], paramArrayOfByte, 60);
/* 315:    */   }
/* 316:    */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     fr.cryptohash.Fugue512
 * JD-Core Version:    0.7.1
 */