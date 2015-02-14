/*   1:    */ package fr.cryptohash;
/*   2:    */ 
/*   3:    */ abstract class Fugue2Core
/*   4:    */   extends FugueCore
/*   5:    */ {
/*   6:    */   void process(int paramInt1, byte[] paramArrayOfByte, int paramInt2, int paramInt3)
/*   7:    */   {
/*   8: 44 */     int[] arrayOfInt = this.S;
/*   9: 45 */     switch (this.rshift)
/*  10:    */     {
/*  11:    */     case 1: 
/*  12: 47 */       arrayOfInt[4] ^= arrayOfInt[24];
/*  13: 48 */       arrayOfInt[24] = paramInt1;
/*  14: 49 */       arrayOfInt[2] ^= arrayOfInt[24];
/*  15: 50 */       arrayOfInt[25] ^= arrayOfInt[18];
/*  16: 51 */       arrayOfInt[21] ^= arrayOfInt[25];
/*  17: 52 */       arrayOfInt[22] ^= arrayOfInt[26];
/*  18: 53 */       arrayOfInt[23] ^= arrayOfInt[27];
/*  19: 54 */       arrayOfInt[6] ^= arrayOfInt[25];
/*  20: 55 */       arrayOfInt[7] ^= arrayOfInt[26];
/*  21: 56 */       arrayOfInt[8] ^= arrayOfInt[27];
/*  22: 57 */       smix(21, 22, 23, 24);
/*  23: 58 */       arrayOfInt[18] ^= arrayOfInt[22];
/*  24: 59 */       arrayOfInt[19] ^= arrayOfInt[23];
/*  25: 60 */       arrayOfInt[20] ^= arrayOfInt[24];
/*  26: 61 */       arrayOfInt[3] ^= arrayOfInt[22];
/*  27: 62 */       arrayOfInt[4] ^= arrayOfInt[23];
/*  28: 63 */       arrayOfInt[5] ^= arrayOfInt[24];
/*  29: 64 */       smix(18, 19, 20, 21);
/*  30: 65 */       if (paramInt3-- <= 0)
/*  31:    */       {
/*  32: 66 */         this.rshift = 2;
/*  33: 67 */         return;
/*  34:    */       }
/*  35: 69 */       paramInt1 = paramArrayOfByte[paramInt2] << 24 | (paramArrayOfByte[(paramInt2 + 1)] & 0xFF) << 16 | (paramArrayOfByte[(paramInt2 + 2)] & 0xFF) << 8 | paramArrayOfByte[(paramInt2 + 3)] & 0xFF;
/*  36:    */       
/*  37:    */ 
/*  38:    */ 
/*  39: 73 */       paramInt2 += 4;
/*  40:    */     case 2: 
/*  41: 76 */       arrayOfInt[28] ^= arrayOfInt[18];
/*  42: 77 */       arrayOfInt[18] = paramInt1;
/*  43: 78 */       arrayOfInt[26] ^= arrayOfInt[18];
/*  44: 79 */       arrayOfInt[19] ^= arrayOfInt[12];
/*  45: 80 */       arrayOfInt[15] ^= arrayOfInt[19];
/*  46: 81 */       arrayOfInt[16] ^= arrayOfInt[20];
/*  47: 82 */       arrayOfInt[17] ^= arrayOfInt[21];
/*  48: 83 */       arrayOfInt[0] ^= arrayOfInt[19];
/*  49: 84 */       arrayOfInt[1] ^= arrayOfInt[20];
/*  50: 85 */       arrayOfInt[2] ^= arrayOfInt[21];
/*  51: 86 */       smix(15, 16, 17, 18);
/*  52: 87 */       arrayOfInt[12] ^= arrayOfInt[16];
/*  53: 88 */       arrayOfInt[13] ^= arrayOfInt[17];
/*  54: 89 */       arrayOfInt[14] ^= arrayOfInt[18];
/*  55: 90 */       arrayOfInt[27] ^= arrayOfInt[16];
/*  56: 91 */       arrayOfInt[28] ^= arrayOfInt[17];
/*  57: 92 */       arrayOfInt[29] ^= arrayOfInt[18];
/*  58: 93 */       smix(12, 13, 14, 15);
/*  59: 94 */       if (paramInt3-- <= 0)
/*  60:    */       {
/*  61: 95 */         this.rshift = 3;
/*  62: 96 */         return;
/*  63:    */       }
/*  64: 98 */       paramInt1 = paramArrayOfByte[paramInt2] << 24 | (paramArrayOfByte[(paramInt2 + 1)] & 0xFF) << 16 | (paramArrayOfByte[(paramInt2 + 2)] & 0xFF) << 8 | paramArrayOfByte[(paramInt2 + 3)] & 0xFF;
/*  65:    */       
/*  66:    */ 
/*  67:    */ 
/*  68:102 */       paramInt2 += 4;
/*  69:    */     case 3: 
/*  70:105 */       arrayOfInt[22] ^= arrayOfInt[12];
/*  71:106 */       arrayOfInt[12] = paramInt1;
/*  72:107 */       arrayOfInt[20] ^= arrayOfInt[12];
/*  73:108 */       arrayOfInt[13] ^= arrayOfInt[6];
/*  74:109 */       arrayOfInt[9] ^= arrayOfInt[13];
/*  75:110 */       arrayOfInt[10] ^= arrayOfInt[14];
/*  76:111 */       arrayOfInt[11] ^= arrayOfInt[15];
/*  77:112 */       arrayOfInt[24] ^= arrayOfInt[13];
/*  78:113 */       arrayOfInt[25] ^= arrayOfInt[14];
/*  79:114 */       arrayOfInt[26] ^= arrayOfInt[15];
/*  80:115 */       smix(9, 10, 11, 12);
/*  81:116 */       arrayOfInt[6] ^= arrayOfInt[10];
/*  82:117 */       arrayOfInt[7] ^= arrayOfInt[11];
/*  83:118 */       arrayOfInt[8] ^= arrayOfInt[12];
/*  84:119 */       arrayOfInt[21] ^= arrayOfInt[10];
/*  85:120 */       arrayOfInt[22] ^= arrayOfInt[11];
/*  86:121 */       arrayOfInt[23] ^= arrayOfInt[12];
/*  87:122 */       smix(6, 7, 8, 9);
/*  88:123 */       if (paramInt3-- <= 0)
/*  89:    */       {
/*  90:124 */         this.rshift = 4;
/*  91:125 */         return;
/*  92:    */       }
/*  93:127 */       paramInt1 = paramArrayOfByte[paramInt2] << 24 | (paramArrayOfByte[(paramInt2 + 1)] & 0xFF) << 16 | (paramArrayOfByte[(paramInt2 + 2)] & 0xFF) << 8 | paramArrayOfByte[(paramInt2 + 3)] & 0xFF;
/*  94:    */       
/*  95:    */ 
/*  96:    */ 
/*  97:131 */       paramInt2 += 4;
/*  98:    */     case 4: 
/*  99:134 */       arrayOfInt[16] ^= arrayOfInt[6];
/* 100:135 */       arrayOfInt[6] = paramInt1;
/* 101:136 */       arrayOfInt[14] ^= arrayOfInt[6];
/* 102:137 */       arrayOfInt[7] ^= arrayOfInt[0];
/* 103:138 */       arrayOfInt[3] ^= arrayOfInt[7];
/* 104:139 */       arrayOfInt[4] ^= arrayOfInt[8];
/* 105:140 */       arrayOfInt[5] ^= arrayOfInt[9];
/* 106:141 */       arrayOfInt[18] ^= arrayOfInt[7];
/* 107:142 */       arrayOfInt[19] ^= arrayOfInt[8];
/* 108:143 */       arrayOfInt[20] ^= arrayOfInt[9];
/* 109:144 */       smix(3, 4, 5, 6);
/* 110:145 */       arrayOfInt[0] ^= arrayOfInt[4];
/* 111:146 */       arrayOfInt[1] ^= arrayOfInt[5];
/* 112:147 */       arrayOfInt[2] ^= arrayOfInt[6];
/* 113:148 */       arrayOfInt[15] ^= arrayOfInt[4];
/* 114:149 */       arrayOfInt[16] ^= arrayOfInt[5];
/* 115:150 */       arrayOfInt[17] ^= arrayOfInt[6];
/* 116:151 */       smix(0, 1, 2, 3);
/* 117:152 */       if (paramInt3-- <= 0)
/* 118:    */       {
/* 119:153 */         this.rshift = 0;
/* 120:154 */         return;
/* 121:    */       }
/* 122:156 */       paramInt1 = paramArrayOfByte[paramInt2] << 24 | (paramArrayOfByte[(paramInt2 + 1)] & 0xFF) << 16 | (paramArrayOfByte[(paramInt2 + 2)] & 0xFF) << 8 | paramArrayOfByte[(paramInt2 + 3)] & 0xFF;
/* 123:    */       
/* 124:    */ 
/* 125:    */ 
/* 126:160 */       paramInt2 += 4;
/* 127:    */     }
/* 128:    */     for (;;)
/* 129:    */     {
/* 130:164 */       arrayOfInt[10] ^= arrayOfInt[0];
/* 131:165 */       arrayOfInt[0] = paramInt1;
/* 132:166 */       arrayOfInt[8] ^= arrayOfInt[0];
/* 133:167 */       arrayOfInt[1] ^= arrayOfInt[24];
/* 134:168 */       arrayOfInt[27] ^= arrayOfInt[1];
/* 135:169 */       arrayOfInt[28] ^= arrayOfInt[2];
/* 136:170 */       arrayOfInt[29] ^= arrayOfInt[3];
/* 137:171 */       arrayOfInt[12] ^= arrayOfInt[1];
/* 138:172 */       arrayOfInt[13] ^= arrayOfInt[2];
/* 139:173 */       arrayOfInt[14] ^= arrayOfInt[3];
/* 140:174 */       smix(27, 28, 29, 0);
/* 141:175 */       arrayOfInt[24] ^= arrayOfInt[28];
/* 142:176 */       arrayOfInt[25] ^= arrayOfInt[29];
/* 143:177 */       arrayOfInt[26] ^= arrayOfInt[0];
/* 144:178 */       arrayOfInt[9] ^= arrayOfInt[28];
/* 145:179 */       arrayOfInt[10] ^= arrayOfInt[29];
/* 146:180 */       arrayOfInt[11] ^= arrayOfInt[0];
/* 147:181 */       smix(24, 25, 26, 27);
/* 148:182 */       if (paramInt3-- <= 0)
/* 149:    */       {
/* 150:183 */         this.rshift = 1;
/* 151:184 */         return;
/* 152:    */       }
/* 153:186 */       paramInt1 = paramArrayOfByte[paramInt2] << 24 | (paramArrayOfByte[(paramInt2 + 1)] & 0xFF) << 16 | (paramArrayOfByte[(paramInt2 + 2)] & 0xFF) << 8 | paramArrayOfByte[(paramInt2 + 3)] & 0xFF;
/* 154:    */       
/* 155:    */ 
/* 156:    */ 
/* 157:190 */       paramInt2 += 4;
/* 158:    */       
/* 159:192 */       arrayOfInt[4] ^= arrayOfInt[24];
/* 160:193 */       arrayOfInt[24] = paramInt1;
/* 161:194 */       arrayOfInt[2] ^= arrayOfInt[24];
/* 162:195 */       arrayOfInt[25] ^= arrayOfInt[18];
/* 163:196 */       arrayOfInt[21] ^= arrayOfInt[25];
/* 164:197 */       arrayOfInt[22] ^= arrayOfInt[26];
/* 165:198 */       arrayOfInt[23] ^= arrayOfInt[27];
/* 166:199 */       arrayOfInt[6] ^= arrayOfInt[25];
/* 167:200 */       arrayOfInt[7] ^= arrayOfInt[26];
/* 168:201 */       arrayOfInt[8] ^= arrayOfInt[27];
/* 169:202 */       smix(21, 22, 23, 24);
/* 170:203 */       arrayOfInt[18] ^= arrayOfInt[22];
/* 171:204 */       arrayOfInt[19] ^= arrayOfInt[23];
/* 172:205 */       arrayOfInt[20] ^= arrayOfInt[24];
/* 173:206 */       arrayOfInt[3] ^= arrayOfInt[22];
/* 174:207 */       arrayOfInt[4] ^= arrayOfInt[23];
/* 175:208 */       arrayOfInt[5] ^= arrayOfInt[24];
/* 176:209 */       smix(18, 19, 20, 21);
/* 177:210 */       if (paramInt3-- <= 0)
/* 178:    */       {
/* 179:211 */         this.rshift = 2;
/* 180:212 */         return;
/* 181:    */       }
/* 182:214 */       paramInt1 = paramArrayOfByte[paramInt2] << 24 | (paramArrayOfByte[(paramInt2 + 1)] & 0xFF) << 16 | (paramArrayOfByte[(paramInt2 + 2)] & 0xFF) << 8 | paramArrayOfByte[(paramInt2 + 3)] & 0xFF;
/* 183:    */       
/* 184:    */ 
/* 185:    */ 
/* 186:218 */       paramInt2 += 4;
/* 187:    */       
/* 188:220 */       arrayOfInt[28] ^= arrayOfInt[18];
/* 189:221 */       arrayOfInt[18] = paramInt1;
/* 190:222 */       arrayOfInt[26] ^= arrayOfInt[18];
/* 191:223 */       arrayOfInt[19] ^= arrayOfInt[12];
/* 192:224 */       arrayOfInt[15] ^= arrayOfInt[19];
/* 193:225 */       arrayOfInt[16] ^= arrayOfInt[20];
/* 194:226 */       arrayOfInt[17] ^= arrayOfInt[21];
/* 195:227 */       arrayOfInt[0] ^= arrayOfInt[19];
/* 196:228 */       arrayOfInt[1] ^= arrayOfInt[20];
/* 197:229 */       arrayOfInt[2] ^= arrayOfInt[21];
/* 198:230 */       smix(15, 16, 17, 18);
/* 199:231 */       arrayOfInt[12] ^= arrayOfInt[16];
/* 200:232 */       arrayOfInt[13] ^= arrayOfInt[17];
/* 201:233 */       arrayOfInt[14] ^= arrayOfInt[18];
/* 202:234 */       arrayOfInt[27] ^= arrayOfInt[16];
/* 203:235 */       arrayOfInt[28] ^= arrayOfInt[17];
/* 204:236 */       arrayOfInt[29] ^= arrayOfInt[18];
/* 205:237 */       smix(12, 13, 14, 15);
/* 206:238 */       if (paramInt3-- <= 0)
/* 207:    */       {
/* 208:239 */         this.rshift = 3;
/* 209:240 */         return;
/* 210:    */       }
/* 211:242 */       paramInt1 = paramArrayOfByte[paramInt2] << 24 | (paramArrayOfByte[(paramInt2 + 1)] & 0xFF) << 16 | (paramArrayOfByte[(paramInt2 + 2)] & 0xFF) << 8 | paramArrayOfByte[(paramInt2 + 3)] & 0xFF;
/* 212:    */       
/* 213:    */ 
/* 214:    */ 
/* 215:246 */       paramInt2 += 4;
/* 216:    */       
/* 217:248 */       arrayOfInt[22] ^= arrayOfInt[12];
/* 218:249 */       arrayOfInt[12] = paramInt1;
/* 219:250 */       arrayOfInt[20] ^= arrayOfInt[12];
/* 220:251 */       arrayOfInt[13] ^= arrayOfInt[6];
/* 221:252 */       arrayOfInt[9] ^= arrayOfInt[13];
/* 222:253 */       arrayOfInt[10] ^= arrayOfInt[14];
/* 223:254 */       arrayOfInt[11] ^= arrayOfInt[15];
/* 224:255 */       arrayOfInt[24] ^= arrayOfInt[13];
/* 225:256 */       arrayOfInt[25] ^= arrayOfInt[14];
/* 226:257 */       arrayOfInt[26] ^= arrayOfInt[15];
/* 227:258 */       smix(9, 10, 11, 12);
/* 228:259 */       arrayOfInt[6] ^= arrayOfInt[10];
/* 229:260 */       arrayOfInt[7] ^= arrayOfInt[11];
/* 230:261 */       arrayOfInt[8] ^= arrayOfInt[12];
/* 231:262 */       arrayOfInt[21] ^= arrayOfInt[10];
/* 232:263 */       arrayOfInt[22] ^= arrayOfInt[11];
/* 233:264 */       arrayOfInt[23] ^= arrayOfInt[12];
/* 234:265 */       smix(6, 7, 8, 9);
/* 235:266 */       if (paramInt3-- <= 0)
/* 236:    */       {
/* 237:267 */         this.rshift = 4;
/* 238:268 */         return;
/* 239:    */       }
/* 240:270 */       paramInt1 = paramArrayOfByte[paramInt2] << 24 | (paramArrayOfByte[(paramInt2 + 1)] & 0xFF) << 16 | (paramArrayOfByte[(paramInt2 + 2)] & 0xFF) << 8 | paramArrayOfByte[(paramInt2 + 3)] & 0xFF;
/* 241:    */       
/* 242:    */ 
/* 243:    */ 
/* 244:274 */       paramInt2 += 4;
/* 245:    */       
/* 246:276 */       arrayOfInt[16] ^= arrayOfInt[6];
/* 247:277 */       arrayOfInt[6] = paramInt1;
/* 248:278 */       arrayOfInt[14] ^= arrayOfInt[6];
/* 249:279 */       arrayOfInt[7] ^= arrayOfInt[0];
/* 250:280 */       arrayOfInt[3] ^= arrayOfInt[7];
/* 251:281 */       arrayOfInt[4] ^= arrayOfInt[8];
/* 252:282 */       arrayOfInt[5] ^= arrayOfInt[9];
/* 253:283 */       arrayOfInt[18] ^= arrayOfInt[7];
/* 254:284 */       arrayOfInt[19] ^= arrayOfInt[8];
/* 255:285 */       arrayOfInt[20] ^= arrayOfInt[9];
/* 256:286 */       smix(3, 4, 5, 6);
/* 257:287 */       arrayOfInt[0] ^= arrayOfInt[4];
/* 258:288 */       arrayOfInt[1] ^= arrayOfInt[5];
/* 259:289 */       arrayOfInt[2] ^= arrayOfInt[6];
/* 260:290 */       arrayOfInt[15] ^= arrayOfInt[4];
/* 261:291 */       arrayOfInt[16] ^= arrayOfInt[5];
/* 262:292 */       arrayOfInt[17] ^= arrayOfInt[6];
/* 263:293 */       smix(0, 1, 2, 3);
/* 264:294 */       if (paramInt3-- <= 0)
/* 265:    */       {
/* 266:295 */         this.rshift = 0;
/* 267:296 */         return;
/* 268:    */       }
/* 269:298 */       paramInt1 = paramArrayOfByte[paramInt2] << 24 | (paramArrayOfByte[(paramInt2 + 1)] & 0xFF) << 16 | (paramArrayOfByte[(paramInt2 + 2)] & 0xFF) << 8 | paramArrayOfByte[(paramInt2 + 3)] & 0xFF;
/* 270:    */       
/* 271:    */ 
/* 272:    */ 
/* 273:302 */       paramInt2 += 4;
/* 274:    */     }
/* 275:    */   }
/* 276:    */   
/* 277:    */   void processFinal(byte[] paramArrayOfByte)
/* 278:    */   {
/* 279:309 */     int[] arrayOfInt = this.S;
/* 280:310 */     ror(6 * this.rshift, 30);
/* 281:311 */     for (int i = 0; i < 10; i++)
/* 282:    */     {
/* 283:312 */       ror(3, 30);
/* 284:313 */       cmix30();
/* 285:314 */       smix(0, 1, 2, 3);
/* 286:    */     }
/* 287:316 */     for (i = 0; i < 13; i++)
/* 288:    */     {
/* 289:317 */       arrayOfInt[4] ^= arrayOfInt[0];
/* 290:318 */       arrayOfInt[15] ^= arrayOfInt[0];
/* 291:319 */       ror(15, 30);
/* 292:320 */       smix(0, 1, 2, 3);
/* 293:321 */       arrayOfInt[4] ^= arrayOfInt[0];
/* 294:322 */       arrayOfInt[16] ^= arrayOfInt[0];
/* 295:323 */       ror(14, 30);
/* 296:324 */       smix(0, 1, 2, 3);
/* 297:    */     }
/* 298:326 */     arrayOfInt[4] ^= arrayOfInt[0];
/* 299:327 */     arrayOfInt[15] ^= arrayOfInt[0];
/* 300:328 */     encodeBEInt(arrayOfInt[1], paramArrayOfByte, 0);
/* 301:329 */     encodeBEInt(arrayOfInt[2], paramArrayOfByte, 4);
/* 302:330 */     encodeBEInt(arrayOfInt[3], paramArrayOfByte, 8);
/* 303:331 */     encodeBEInt(arrayOfInt[4], paramArrayOfByte, 12);
/* 304:332 */     encodeBEInt(arrayOfInt[15], paramArrayOfByte, 16);
/* 305:333 */     encodeBEInt(arrayOfInt[16], paramArrayOfByte, 20);
/* 306:334 */     encodeBEInt(arrayOfInt[17], paramArrayOfByte, 24);
/* 307:335 */     if (paramArrayOfByte.length >= 32) {
/* 308:336 */       encodeBEInt(arrayOfInt[18], paramArrayOfByte, 28);
/* 309:    */     }
/* 310:    */   }
/* 311:    */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     fr.cryptohash.Fugue2Core
 * JD-Core Version:    0.7.1
 */