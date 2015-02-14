/*   1:    */ package fr.cryptohash;
/*   2:    */ 
/*   3:    */ public class RIPEMD
/*   4:    */   extends MDHelper
/*   5:    */ {
/*   6:    */   private int[] currentVal;
/*   7:    */   private int[] X;
/*   8:    */   
/*   9:    */   public RIPEMD()
/*  10:    */   {
/*  11: 53 */     super(true, 8);
/*  12:    */   }
/*  13:    */   
/*  14:    */   public Digest copy()
/*  15:    */   {
/*  16: 61 */     RIPEMD localRIPEMD = new RIPEMD();
/*  17: 62 */     System.arraycopy(this.currentVal, 0, localRIPEMD.currentVal, 0, this.currentVal.length);
/*  18:    */     
/*  19: 64 */     return copyState(localRIPEMD);
/*  20:    */   }
/*  21:    */   
/*  22:    */   public int getDigestLength()
/*  23:    */   {
/*  24: 70 */     return 16;
/*  25:    */   }
/*  26:    */   
/*  27:    */   public int getBlockLength()
/*  28:    */   {
/*  29: 76 */     return 64;
/*  30:    */   }
/*  31:    */   
/*  32:    */   protected void engineReset()
/*  33:    */   {
/*  34: 82 */     this.currentVal[0] = 1732584193;
/*  35: 83 */     this.currentVal[1] = -271733879;
/*  36: 84 */     this.currentVal[2] = -1732584194;
/*  37: 85 */     this.currentVal[3] = 271733878;
/*  38:    */   }
/*  39:    */   
/*  40:    */   protected void doPadding(byte[] paramArrayOfByte, int paramInt)
/*  41:    */   {
/*  42: 91 */     makeMDPadding();
/*  43: 92 */     for (int i = 0; i < 4; i++) {
/*  44: 93 */       encodeLEInt(this.currentVal[i], paramArrayOfByte, paramInt + 4 * i);
/*  45:    */     }
/*  46:    */   }
/*  47:    */   
/*  48:    */   protected void doInit()
/*  49:    */   {
/*  50:100 */     this.currentVal = new int[4];
/*  51:101 */     this.X = new int[16];
/*  52:102 */     engineReset();
/*  53:    */   }
/*  54:    */   
/*  55:    */   private static final void encodeLEInt(int paramInt1, byte[] paramArrayOfByte, int paramInt2)
/*  56:    */   {
/*  57:116 */     paramArrayOfByte[(paramInt2 + 0)] = ((byte)paramInt1);
/*  58:117 */     paramArrayOfByte[(paramInt2 + 1)] = ((byte)(paramInt1 >>> 8));
/*  59:118 */     paramArrayOfByte[(paramInt2 + 2)] = ((byte)(paramInt1 >>> 16));
/*  60:119 */     paramArrayOfByte[(paramInt2 + 3)] = ((byte)(paramInt1 >>> 24));
/*  61:    */   }
/*  62:    */   
/*  63:    */   private static final int decodeLEInt(byte[] paramArrayOfByte, int paramInt)
/*  64:    */   {
/*  65:132 */     return paramArrayOfByte[(paramInt + 0)] & 0xFF | (paramArrayOfByte[(paramInt + 1)] & 0xFF) << 8 | (paramArrayOfByte[(paramInt + 2)] & 0xFF) << 16 | (paramArrayOfByte[(paramInt + 3)] & 0xFF) << 24;
/*  66:    */   }
/*  67:    */   
/*  68:    */   private static final int circularLeft(int paramInt1, int paramInt2)
/*  69:    */   {
/*  70:149 */     return paramInt1 << paramInt2 | paramInt1 >>> 32 - paramInt2;
/*  71:    */   }
/*  72:    */   
/*  73:152 */   private static final int[] r1 = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 7, 4, 13, 1, 10, 6, 15, 3, 12, 0, 9, 5, 2, 14, 11, 8, 3, 10, 14, 4, 9, 15, 8, 1, 2, 7, 0, 6, 13, 11, 5, 12, 1, 9, 11, 10, 0, 8, 12, 4, 13, 3, 7, 15, 14, 5, 6, 2 };
/*  74:159 */   private static final int[] r2 = { 5, 14, 7, 0, 9, 2, 11, 4, 13, 6, 15, 8, 1, 10, 3, 12, 6, 11, 3, 7, 0, 13, 5, 10, 14, 15, 8, 12, 4, 9, 1, 2, 15, 5, 1, 3, 7, 14, 6, 9, 11, 8, 12, 2, 10, 0, 4, 13, 8, 6, 4, 1, 3, 11, 15, 0, 5, 12, 2, 13, 9, 7, 10, 14 };
/*  75:166 */   private static final int[] s1 = { 11, 14, 15, 12, 5, 8, 7, 9, 11, 13, 14, 15, 6, 7, 9, 8, 7, 6, 8, 13, 11, 9, 7, 15, 7, 12, 15, 9, 11, 7, 13, 12, 11, 13, 6, 7, 14, 9, 13, 15, 14, 8, 13, 6, 5, 12, 7, 5, 11, 12, 14, 15, 14, 15, 9, 8, 9, 14, 5, 6, 8, 6, 5, 12 };
/*  76:173 */   private static final int[] s2 = { 8, 9, 9, 11, 13, 15, 15, 5, 7, 7, 8, 11, 14, 14, 12, 6, 9, 13, 15, 7, 12, 8, 9, 11, 7, 7, 12, 7, 6, 15, 13, 11, 9, 7, 15, 11, 8, 6, 6, 14, 12, 13, 5, 14, 13, 13, 7, 5, 15, 5, 8, 11, 14, 14, 6, 14, 6, 9, 12, 9, 12, 5, 15, 8 };
/*  77:    */   
/*  78:    */   protected void processBlock(byte[] paramArrayOfByte)
/*  79:    */   {
/*  80:188 */     int i = n = i4 = this.currentVal[0];
/*  81:189 */     int j = i1 = i5 = this.currentVal[1];
/*  82:190 */     int k = i2 = i6 = this.currentVal[2];
/*  83:191 */     int m = i3 = i7 = this.currentVal[3];
/*  84:    */     
/*  85:193 */     int i9 = 0;
/*  86:193 */     for (int i10 = 0; i9 < 16; i10 += 4)
/*  87:    */     {
/*  88:194 */       this.X[i9] = decodeLEInt(paramArrayOfByte, i10);i9++;
/*  89:    */     }
/*  90:196 */     int i8 = n + ((i2 ^ i3) & i1 ^ i3) + this.X[0];
/*  91:197 */     int n = i8 << 11 | i8 >>> 21;
/*  92:198 */     i8 = i3 + ((i1 ^ i2) & n ^ i2) + this.X[1];
/*  93:199 */     int i3 = i8 << 14 | i8 >>> 18;
/*  94:200 */     i8 = i2 + ((n ^ i1) & i3 ^ i1) + this.X[2];
/*  95:201 */     int i2 = i8 << 15 | i8 >>> 17;
/*  96:202 */     i8 = i1 + ((i3 ^ n) & i2 ^ n) + this.X[3];
/*  97:203 */     int i1 = i8 << 12 | i8 >>> 20;
/*  98:204 */     i8 = n + ((i2 ^ i3) & i1 ^ i3) + this.X[4];
/*  99:205 */     n = i8 << 5 | i8 >>> 27;
/* 100:206 */     i8 = i3 + ((i1 ^ i2) & n ^ i2) + this.X[5];
/* 101:207 */     i3 = i8 << 8 | i8 >>> 24;
/* 102:208 */     i8 = i2 + ((n ^ i1) & i3 ^ i1) + this.X[6];
/* 103:209 */     i2 = i8 << 7 | i8 >>> 25;
/* 104:210 */     i8 = i1 + ((i3 ^ n) & i2 ^ n) + this.X[7];
/* 105:211 */     i1 = i8 << 9 | i8 >>> 23;
/* 106:212 */     i8 = n + ((i2 ^ i3) & i1 ^ i3) + this.X[8];
/* 107:213 */     n = i8 << 11 | i8 >>> 21;
/* 108:214 */     i8 = i3 + ((i1 ^ i2) & n ^ i2) + this.X[9];
/* 109:215 */     i3 = i8 << 13 | i8 >>> 19;
/* 110:216 */     i8 = i2 + ((n ^ i1) & i3 ^ i1) + this.X[10];
/* 111:217 */     i2 = i8 << 14 | i8 >>> 18;
/* 112:218 */     i8 = i1 + ((i3 ^ n) & i2 ^ n) + this.X[11];
/* 113:219 */     i1 = i8 << 15 | i8 >>> 17;
/* 114:220 */     i8 = n + ((i2 ^ i3) & i1 ^ i3) + this.X[12];
/* 115:221 */     n = i8 << 6 | i8 >>> 26;
/* 116:222 */     i8 = i3 + ((i1 ^ i2) & n ^ i2) + this.X[13];
/* 117:223 */     i3 = i8 << 7 | i8 >>> 25;
/* 118:224 */     i8 = i2 + ((n ^ i1) & i3 ^ i1) + this.X[14];
/* 119:225 */     i2 = i8 << 9 | i8 >>> 23;
/* 120:226 */     i8 = i1 + ((i3 ^ n) & i2 ^ n) + this.X[15];
/* 121:227 */     i1 = i8 << 8 | i8 >>> 24;
/* 122:    */     
/* 123:229 */     i8 = n + (i1 & i2 | (i1 | i2) & i3) + this.X[7] + 1518500249;
/* 124:230 */     n = i8 << 7 | i8 >>> 25;
/* 125:231 */     i8 = i3 + (n & i1 | (n | i1) & i2) + this.X[4] + 1518500249;
/* 126:232 */     i3 = i8 << 6 | i8 >>> 26;
/* 127:233 */     i8 = i2 + (i3 & n | (i3 | n) & i1) + this.X[13] + 1518500249;
/* 128:234 */     i2 = i8 << 8 | i8 >>> 24;
/* 129:235 */     i8 = i1 + (i2 & i3 | (i2 | i3) & n) + this.X[1] + 1518500249;
/* 130:236 */     i1 = i8 << 13 | i8 >>> 19;
/* 131:237 */     i8 = n + (i1 & i2 | (i1 | i2) & i3) + this.X[10] + 1518500249;
/* 132:238 */     n = i8 << 11 | i8 >>> 21;
/* 133:239 */     i8 = i3 + (n & i1 | (n | i1) & i2) + this.X[6] + 1518500249;
/* 134:240 */     i3 = i8 << 9 | i8 >>> 23;
/* 135:241 */     i8 = i2 + (i3 & n | (i3 | n) & i1) + this.X[15] + 1518500249;
/* 136:242 */     i2 = i8 << 7 | i8 >>> 25;
/* 137:243 */     i8 = i1 + (i2 & i3 | (i2 | i3) & n) + this.X[3] + 1518500249;
/* 138:244 */     i1 = i8 << 15 | i8 >>> 17;
/* 139:245 */     i8 = n + (i1 & i2 | (i1 | i2) & i3) + this.X[12] + 1518500249;
/* 140:246 */     n = i8 << 7 | i8 >>> 25;
/* 141:247 */     i8 = i3 + (n & i1 | (n | i1) & i2) + this.X[0] + 1518500249;
/* 142:248 */     i3 = i8 << 12 | i8 >>> 20;
/* 143:249 */     i8 = i2 + (i3 & n | (i3 | n) & i1) + this.X[9] + 1518500249;
/* 144:250 */     i2 = i8 << 15 | i8 >>> 17;
/* 145:251 */     i8 = i1 + (i2 & i3 | (i2 | i3) & n) + this.X[5] + 1518500249;
/* 146:252 */     i1 = i8 << 9 | i8 >>> 23;
/* 147:253 */     i8 = n + (i1 & i2 | (i1 | i2) & i3) + this.X[14] + 1518500249;
/* 148:254 */     n = i8 << 7 | i8 >>> 25;
/* 149:255 */     i8 = i3 + (n & i1 | (n | i1) & i2) + this.X[2] + 1518500249;
/* 150:256 */     i3 = i8 << 11 | i8 >>> 21;
/* 151:257 */     i8 = i2 + (i3 & n | (i3 | n) & i1) + this.X[11] + 1518500249;
/* 152:258 */     i2 = i8 << 13 | i8 >>> 19;
/* 153:259 */     i8 = i1 + (i2 & i3 | (i2 | i3) & n) + this.X[8] + 1518500249;
/* 154:260 */     i1 = i8 << 12 | i8 >>> 20;
/* 155:    */     
/* 156:262 */     i8 = n + (i1 ^ i2 ^ i3) + this.X[3] + 1859775393;
/* 157:263 */     n = i8 << 11 | i8 >>> 21;
/* 158:264 */     i8 = i3 + (n ^ i1 ^ i2) + this.X[10] + 1859775393;
/* 159:265 */     i3 = i8 << 13 | i8 >>> 19;
/* 160:266 */     i8 = i2 + (i3 ^ n ^ i1) + this.X[2] + 1859775393;
/* 161:267 */     i2 = i8 << 14 | i8 >>> 18;
/* 162:268 */     i8 = i1 + (i2 ^ i3 ^ n) + this.X[4] + 1859775393;
/* 163:269 */     i1 = i8 << 7 | i8 >>> 25;
/* 164:270 */     i8 = n + (i1 ^ i2 ^ i3) + this.X[9] + 1859775393;
/* 165:271 */     n = i8 << 14 | i8 >>> 18;
/* 166:272 */     i8 = i3 + (n ^ i1 ^ i2) + this.X[15] + 1859775393;
/* 167:273 */     i3 = i8 << 9 | i8 >>> 23;
/* 168:274 */     i8 = i2 + (i3 ^ n ^ i1) + this.X[8] + 1859775393;
/* 169:275 */     i2 = i8 << 13 | i8 >>> 19;
/* 170:276 */     i8 = i1 + (i2 ^ i3 ^ n) + this.X[1] + 1859775393;
/* 171:277 */     i1 = i8 << 15 | i8 >>> 17;
/* 172:278 */     i8 = n + (i1 ^ i2 ^ i3) + this.X[14] + 1859775393;
/* 173:279 */     n = i8 << 6 | i8 >>> 26;
/* 174:280 */     i8 = i3 + (n ^ i1 ^ i2) + this.X[7] + 1859775393;
/* 175:281 */     i3 = i8 << 8 | i8 >>> 24;
/* 176:282 */     i8 = i2 + (i3 ^ n ^ i1) + this.X[0] + 1859775393;
/* 177:283 */     i2 = i8 << 13 | i8 >>> 19;
/* 178:284 */     i8 = i1 + (i2 ^ i3 ^ n) + this.X[6] + 1859775393;
/* 179:285 */     i1 = i8 << 6 | i8 >>> 26;
/* 180:286 */     i8 = n + (i1 ^ i2 ^ i3) + this.X[11] + 1859775393;
/* 181:287 */     n = i8 << 12 | i8 >>> 20;
/* 182:288 */     i8 = i3 + (n ^ i1 ^ i2) + this.X[13] + 1859775393;
/* 183:289 */     i3 = i8 << 5 | i8 >>> 27;
/* 184:290 */     i8 = i2 + (i3 ^ n ^ i1) + this.X[5] + 1859775393;
/* 185:291 */     i2 = i8 << 7 | i8 >>> 25;
/* 186:292 */     i8 = i1 + (i2 ^ i3 ^ n) + this.X[12] + 1859775393;
/* 187:293 */     i1 = i8 << 5 | i8 >>> 27;
/* 188:    */     
/* 189:295 */     i8 = i4 + ((i6 ^ i7) & i5 ^ i7) + this.X[0] + 1352829926;
/* 190:296 */     int i4 = i8 << 11 | i8 >>> 21;
/* 191:297 */     i8 = i7 + ((i5 ^ i6) & i4 ^ i6) + this.X[1] + 1352829926;
/* 192:298 */     int i7 = i8 << 14 | i8 >>> 18;
/* 193:299 */     i8 = i6 + ((i4 ^ i5) & i7 ^ i5) + this.X[2] + 1352829926;
/* 194:300 */     int i6 = i8 << 15 | i8 >>> 17;
/* 195:301 */     i8 = i5 + ((i7 ^ i4) & i6 ^ i4) + this.X[3] + 1352829926;
/* 196:302 */     int i5 = i8 << 12 | i8 >>> 20;
/* 197:303 */     i8 = i4 + ((i6 ^ i7) & i5 ^ i7) + this.X[4] + 1352829926;
/* 198:304 */     i4 = i8 << 5 | i8 >>> 27;
/* 199:305 */     i8 = i7 + ((i5 ^ i6) & i4 ^ i6) + this.X[5] + 1352829926;
/* 200:306 */     i7 = i8 << 8 | i8 >>> 24;
/* 201:307 */     i8 = i6 + ((i4 ^ i5) & i7 ^ i5) + this.X[6] + 1352829926;
/* 202:308 */     i6 = i8 << 7 | i8 >>> 25;
/* 203:309 */     i8 = i5 + ((i7 ^ i4) & i6 ^ i4) + this.X[7] + 1352829926;
/* 204:310 */     i5 = i8 << 9 | i8 >>> 23;
/* 205:311 */     i8 = i4 + ((i6 ^ i7) & i5 ^ i7) + this.X[8] + 1352829926;
/* 206:312 */     i4 = i8 << 11 | i8 >>> 21;
/* 207:313 */     i8 = i7 + ((i5 ^ i6) & i4 ^ i6) + this.X[9] + 1352829926;
/* 208:314 */     i7 = i8 << 13 | i8 >>> 19;
/* 209:315 */     i8 = i6 + ((i4 ^ i5) & i7 ^ i5) + this.X[10] + 1352829926;
/* 210:316 */     i6 = i8 << 14 | i8 >>> 18;
/* 211:317 */     i8 = i5 + ((i7 ^ i4) & i6 ^ i4) + this.X[11] + 1352829926;
/* 212:318 */     i5 = i8 << 15 | i8 >>> 17;
/* 213:319 */     i8 = i4 + ((i6 ^ i7) & i5 ^ i7) + this.X[12] + 1352829926;
/* 214:320 */     i4 = i8 << 6 | i8 >>> 26;
/* 215:321 */     i8 = i7 + ((i5 ^ i6) & i4 ^ i6) + this.X[13] + 1352829926;
/* 216:322 */     i7 = i8 << 7 | i8 >>> 25;
/* 217:323 */     i8 = i6 + ((i4 ^ i5) & i7 ^ i5) + this.X[14] + 1352829926;
/* 218:324 */     i6 = i8 << 9 | i8 >>> 23;
/* 219:325 */     i8 = i5 + ((i7 ^ i4) & i6 ^ i4) + this.X[15] + 1352829926;
/* 220:326 */     i5 = i8 << 8 | i8 >>> 24;
/* 221:    */     
/* 222:328 */     i8 = i4 + (i5 & i6 | (i5 | i6) & i7) + this.X[7];
/* 223:329 */     i4 = i8 << 7 | i8 >>> 25;
/* 224:330 */     i8 = i7 + (i4 & i5 | (i4 | i5) & i6) + this.X[4];
/* 225:331 */     i7 = i8 << 6 | i8 >>> 26;
/* 226:332 */     i8 = i6 + (i7 & i4 | (i7 | i4) & i5) + this.X[13];
/* 227:333 */     i6 = i8 << 8 | i8 >>> 24;
/* 228:334 */     i8 = i5 + (i6 & i7 | (i6 | i7) & i4) + this.X[1];
/* 229:335 */     i5 = i8 << 13 | i8 >>> 19;
/* 230:336 */     i8 = i4 + (i5 & i6 | (i5 | i6) & i7) + this.X[10];
/* 231:337 */     i4 = i8 << 11 | i8 >>> 21;
/* 232:338 */     i8 = i7 + (i4 & i5 | (i4 | i5) & i6) + this.X[6];
/* 233:339 */     i7 = i8 << 9 | i8 >>> 23;
/* 234:340 */     i8 = i6 + (i7 & i4 | (i7 | i4) & i5) + this.X[15];
/* 235:341 */     i6 = i8 << 7 | i8 >>> 25;
/* 236:342 */     i8 = i5 + (i6 & i7 | (i6 | i7) & i4) + this.X[3];
/* 237:343 */     i5 = i8 << 15 | i8 >>> 17;
/* 238:344 */     i8 = i4 + (i5 & i6 | (i5 | i6) & i7) + this.X[12];
/* 239:345 */     i4 = i8 << 7 | i8 >>> 25;
/* 240:346 */     i8 = i7 + (i4 & i5 | (i4 | i5) & i6) + this.X[0];
/* 241:347 */     i7 = i8 << 12 | i8 >>> 20;
/* 242:348 */     i8 = i6 + (i7 & i4 | (i7 | i4) & i5) + this.X[9];
/* 243:349 */     i6 = i8 << 15 | i8 >>> 17;
/* 244:350 */     i8 = i5 + (i6 & i7 | (i6 | i7) & i4) + this.X[5];
/* 245:351 */     i5 = i8 << 9 | i8 >>> 23;
/* 246:352 */     i8 = i4 + (i5 & i6 | (i5 | i6) & i7) + this.X[14];
/* 247:353 */     i4 = i8 << 7 | i8 >>> 25;
/* 248:354 */     i8 = i7 + (i4 & i5 | (i4 | i5) & i6) + this.X[2];
/* 249:355 */     i7 = i8 << 11 | i8 >>> 21;
/* 250:356 */     i8 = i6 + (i7 & i4 | (i7 | i4) & i5) + this.X[11];
/* 251:357 */     i6 = i8 << 13 | i8 >>> 19;
/* 252:358 */     i8 = i5 + (i6 & i7 | (i6 | i7) & i4) + this.X[8];
/* 253:359 */     i5 = i8 << 12 | i8 >>> 20;
/* 254:    */     
/* 255:361 */     i8 = i4 + (i5 ^ i6 ^ i7) + this.X[3] + 1548603684;
/* 256:362 */     i4 = i8 << 11 | i8 >>> 21;
/* 257:363 */     i8 = i7 + (i4 ^ i5 ^ i6) + this.X[10] + 1548603684;
/* 258:364 */     i7 = i8 << 13 | i8 >>> 19;
/* 259:365 */     i8 = i6 + (i7 ^ i4 ^ i5) + this.X[2] + 1548603684;
/* 260:366 */     i6 = i8 << 14 | i8 >>> 18;
/* 261:367 */     i8 = i5 + (i6 ^ i7 ^ i4) + this.X[4] + 1548603684;
/* 262:368 */     i5 = i8 << 7 | i8 >>> 25;
/* 263:369 */     i8 = i4 + (i5 ^ i6 ^ i7) + this.X[9] + 1548603684;
/* 264:370 */     i4 = i8 << 14 | i8 >>> 18;
/* 265:371 */     i8 = i7 + (i4 ^ i5 ^ i6) + this.X[15] + 1548603684;
/* 266:372 */     i7 = i8 << 9 | i8 >>> 23;
/* 267:373 */     i8 = i6 + (i7 ^ i4 ^ i5) + this.X[8] + 1548603684;
/* 268:374 */     i6 = i8 << 13 | i8 >>> 19;
/* 269:375 */     i8 = i5 + (i6 ^ i7 ^ i4) + this.X[1] + 1548603684;
/* 270:376 */     i5 = i8 << 15 | i8 >>> 17;
/* 271:377 */     i8 = i4 + (i5 ^ i6 ^ i7) + this.X[14] + 1548603684;
/* 272:378 */     i4 = i8 << 6 | i8 >>> 26;
/* 273:379 */     i8 = i7 + (i4 ^ i5 ^ i6) + this.X[7] + 1548603684;
/* 274:380 */     i7 = i8 << 8 | i8 >>> 24;
/* 275:381 */     i8 = i6 + (i7 ^ i4 ^ i5) + this.X[0] + 1548603684;
/* 276:382 */     i6 = i8 << 13 | i8 >>> 19;
/* 277:383 */     i8 = i5 + (i6 ^ i7 ^ i4) + this.X[6] + 1548603684;
/* 278:384 */     i5 = i8 << 6 | i8 >>> 26;
/* 279:385 */     i8 = i4 + (i5 ^ i6 ^ i7) + this.X[11] + 1548603684;
/* 280:386 */     i4 = i8 << 12 | i8 >>> 20;
/* 281:387 */     i8 = i7 + (i4 ^ i5 ^ i6) + this.X[13] + 1548603684;
/* 282:388 */     i7 = i8 << 5 | i8 >>> 27;
/* 283:389 */     i8 = i6 + (i7 ^ i4 ^ i5) + this.X[5] + 1548603684;
/* 284:390 */     i6 = i8 << 7 | i8 >>> 25;
/* 285:391 */     i8 = i5 + (i6 ^ i7 ^ i4) + this.X[12] + 1548603684;
/* 286:392 */     i5 = i8 << 5 | i8 >>> 27;
/* 287:    */     
/* 288:394 */     i9 = j + i2 + i7;
/* 289:395 */     this.currentVal[1] = (k + i3 + i4);
/* 290:396 */     this.currentVal[2] = (m + n + i5);
/* 291:397 */     this.currentVal[3] = (i + i1 + i6);
/* 292:398 */     this.currentVal[0] = i9;
/* 293:    */   }
/* 294:    */   
/* 295:    */   public String toString()
/* 296:    */   {
/* 297:404 */     return "RIPEMD";
/* 298:    */   }
/* 299:    */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     fr.cryptohash.RIPEMD
 * JD-Core Version:    0.7.1
 */