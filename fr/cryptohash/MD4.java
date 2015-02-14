/*   1:    */ package fr.cryptohash;
/*   2:    */ 
/*   3:    */ public class MD4
/*   4:    */   extends MDHelper
/*   5:    */ {
/*   6:    */   private int[] currentVal;
/*   7:    */   
/*   8:    */   public MD4()
/*   9:    */   {
/*  10: 48 */     super(true, 8);
/*  11:    */   }
/*  12:    */   
/*  13:    */   public Digest copy()
/*  14:    */   {
/*  15: 56 */     MD4 localMD4 = new MD4();
/*  16: 57 */     System.arraycopy(this.currentVal, 0, localMD4.currentVal, 0, this.currentVal.length);
/*  17:    */     
/*  18: 59 */     return copyState(localMD4);
/*  19:    */   }
/*  20:    */   
/*  21:    */   public int getDigestLength()
/*  22:    */   {
/*  23: 65 */     return 16;
/*  24:    */   }
/*  25:    */   
/*  26:    */   public int getBlockLength()
/*  27:    */   {
/*  28: 71 */     return 64;
/*  29:    */   }
/*  30:    */   
/*  31:    */   protected void engineReset()
/*  32:    */   {
/*  33: 77 */     this.currentVal[0] = 1732584193;
/*  34: 78 */     this.currentVal[1] = -271733879;
/*  35: 79 */     this.currentVal[2] = -1732584194;
/*  36: 80 */     this.currentVal[3] = 271733878;
/*  37:    */   }
/*  38:    */   
/*  39:    */   protected void doPadding(byte[] paramArrayOfByte, int paramInt)
/*  40:    */   {
/*  41: 86 */     makeMDPadding();
/*  42: 87 */     for (int i = 0; i < 4; i++) {
/*  43: 88 */       encodeLEInt(this.currentVal[i], paramArrayOfByte, paramInt + 4 * i);
/*  44:    */     }
/*  45:    */   }
/*  46:    */   
/*  47:    */   protected void doInit()
/*  48:    */   {
/*  49: 95 */     this.currentVal = new int[4];
/*  50: 96 */     engineReset();
/*  51:    */   }
/*  52:    */   
/*  53:    */   private static final void encodeLEInt(int paramInt1, byte[] paramArrayOfByte, int paramInt2)
/*  54:    */   {
/*  55:110 */     paramArrayOfByte[(paramInt2 + 3)] = ((byte)(paramInt1 >> 24 & 0xFF));
/*  56:111 */     paramArrayOfByte[(paramInt2 + 2)] = ((byte)(paramInt1 >> 16 & 0xFF));
/*  57:112 */     paramArrayOfByte[(paramInt2 + 1)] = ((byte)(paramInt1 >> 8 & 0xFF));
/*  58:113 */     paramArrayOfByte[(paramInt2 + 0)] = ((byte)(paramInt1 & 0xFF));
/*  59:    */   }
/*  60:    */   
/*  61:    */   protected void processBlock(byte[] paramArrayOfByte)
/*  62:    */   {
/*  63:128 */     int i = this.currentVal[0];int j = this.currentVal[1];
/*  64:129 */     int k = this.currentVal[2];int m = this.currentVal[3];
/*  65:    */     
/*  66:131 */     int n = paramArrayOfByte[0] & 0xFF | (paramArrayOfByte[1] & 0xFF) << 8 | (paramArrayOfByte[2] & 0xFF) << 16 | (paramArrayOfByte[3] & 0xFF) << 24;
/*  67:    */     
/*  68:    */ 
/*  69:    */ 
/*  70:135 */     int i1 = paramArrayOfByte[4] & 0xFF | (paramArrayOfByte[5] & 0xFF) << 8 | (paramArrayOfByte[6] & 0xFF) << 16 | (paramArrayOfByte[7] & 0xFF) << 24;
/*  71:    */     
/*  72:    */ 
/*  73:    */ 
/*  74:139 */     int i2 = paramArrayOfByte[8] & 0xFF | (paramArrayOfByte[9] & 0xFF) << 8 | (paramArrayOfByte[10] & 0xFF) << 16 | (paramArrayOfByte[11] & 0xFF) << 24;
/*  75:    */     
/*  76:    */ 
/*  77:    */ 
/*  78:143 */     int i3 = paramArrayOfByte[12] & 0xFF | (paramArrayOfByte[13] & 0xFF) << 8 | (paramArrayOfByte[14] & 0xFF) << 16 | (paramArrayOfByte[15] & 0xFF) << 24;
/*  79:    */     
/*  80:    */ 
/*  81:    */ 
/*  82:147 */     int i4 = paramArrayOfByte[16] & 0xFF | (paramArrayOfByte[17] & 0xFF) << 8 | (paramArrayOfByte[18] & 0xFF) << 16 | (paramArrayOfByte[19] & 0xFF) << 24;
/*  83:    */     
/*  84:    */ 
/*  85:    */ 
/*  86:151 */     int i5 = paramArrayOfByte[20] & 0xFF | (paramArrayOfByte[21] & 0xFF) << 8 | (paramArrayOfByte[22] & 0xFF) << 16 | (paramArrayOfByte[23] & 0xFF) << 24;
/*  87:    */     
/*  88:    */ 
/*  89:    */ 
/*  90:155 */     int i6 = paramArrayOfByte[24] & 0xFF | (paramArrayOfByte[25] & 0xFF) << 8 | (paramArrayOfByte[26] & 0xFF) << 16 | (paramArrayOfByte[27] & 0xFF) << 24;
/*  91:    */     
/*  92:    */ 
/*  93:    */ 
/*  94:159 */     int i7 = paramArrayOfByte[28] & 0xFF | (paramArrayOfByte[29] & 0xFF) << 8 | (paramArrayOfByte[30] & 0xFF) << 16 | (paramArrayOfByte[31] & 0xFF) << 24;
/*  95:    */     
/*  96:    */ 
/*  97:    */ 
/*  98:163 */     int i8 = paramArrayOfByte[32] & 0xFF | (paramArrayOfByte[33] & 0xFF) << 8 | (paramArrayOfByte[34] & 0xFF) << 16 | (paramArrayOfByte[35] & 0xFF) << 24;
/*  99:    */     
/* 100:    */ 
/* 101:    */ 
/* 102:167 */     int i9 = paramArrayOfByte[36] & 0xFF | (paramArrayOfByte[37] & 0xFF) << 8 | (paramArrayOfByte[38] & 0xFF) << 16 | (paramArrayOfByte[39] & 0xFF) << 24;
/* 103:    */     
/* 104:    */ 
/* 105:    */ 
/* 106:171 */     int i10 = paramArrayOfByte[40] & 0xFF | (paramArrayOfByte[41] & 0xFF) << 8 | (paramArrayOfByte[42] & 0xFF) << 16 | (paramArrayOfByte[43] & 0xFF) << 24;
/* 107:    */     
/* 108:    */ 
/* 109:    */ 
/* 110:175 */     int i11 = paramArrayOfByte[44] & 0xFF | (paramArrayOfByte[45] & 0xFF) << 8 | (paramArrayOfByte[46] & 0xFF) << 16 | (paramArrayOfByte[47] & 0xFF) << 24;
/* 111:    */     
/* 112:    */ 
/* 113:    */ 
/* 114:179 */     int i12 = paramArrayOfByte[48] & 0xFF | (paramArrayOfByte[49] & 0xFF) << 8 | (paramArrayOfByte[50] & 0xFF) << 16 | (paramArrayOfByte[51] & 0xFF) << 24;
/* 115:    */     
/* 116:    */ 
/* 117:    */ 
/* 118:183 */     int i13 = paramArrayOfByte[52] & 0xFF | (paramArrayOfByte[53] & 0xFF) << 8 | (paramArrayOfByte[54] & 0xFF) << 16 | (paramArrayOfByte[55] & 0xFF) << 24;
/* 119:    */     
/* 120:    */ 
/* 121:    */ 
/* 122:187 */     int i14 = paramArrayOfByte[56] & 0xFF | (paramArrayOfByte[57] & 0xFF) << 8 | (paramArrayOfByte[58] & 0xFF) << 16 | (paramArrayOfByte[59] & 0xFF) << 24;
/* 123:    */     
/* 124:    */ 
/* 125:    */ 
/* 126:191 */     int i15 = paramArrayOfByte[60] & 0xFF | (paramArrayOfByte[61] & 0xFF) << 8 | (paramArrayOfByte[62] & 0xFF) << 16 | (paramArrayOfByte[63] & 0xFF) << 24;
/* 127:    */     
/* 128:    */ 
/* 129:    */ 
/* 130:    */ 
/* 131:    */ 
/* 132:197 */     int i16 = i + ((k ^ m) & j ^ m) + n;
/* 133:198 */     i = i16 << 3 | i16 >>> 29;
/* 134:199 */     i16 = m + ((j ^ k) & i ^ k) + i1;
/* 135:200 */     m = i16 << 7 | i16 >>> 25;
/* 136:201 */     i16 = k + ((i ^ j) & m ^ j) + i2;
/* 137:202 */     k = i16 << 11 | i16 >>> 21;
/* 138:203 */     i16 = j + ((m ^ i) & k ^ i) + i3;
/* 139:204 */     j = i16 << 19 | i16 >>> 13;
/* 140:205 */     i16 = i + ((k ^ m) & j ^ m) + i4;
/* 141:206 */     i = i16 << 3 | i16 >>> 29;
/* 142:207 */     i16 = m + ((j ^ k) & i ^ k) + i5;
/* 143:208 */     m = i16 << 7 | i16 >>> 25;
/* 144:209 */     i16 = k + ((i ^ j) & m ^ j) + i6;
/* 145:210 */     k = i16 << 11 | i16 >>> 21;
/* 146:211 */     i16 = j + ((m ^ i) & k ^ i) + i7;
/* 147:212 */     j = i16 << 19 | i16 >>> 13;
/* 148:213 */     i16 = i + ((k ^ m) & j ^ m) + i8;
/* 149:214 */     i = i16 << 3 | i16 >>> 29;
/* 150:215 */     i16 = m + ((j ^ k) & i ^ k) + i9;
/* 151:216 */     m = i16 << 7 | i16 >>> 25;
/* 152:217 */     i16 = k + ((i ^ j) & m ^ j) + i10;
/* 153:218 */     k = i16 << 11 | i16 >>> 21;
/* 154:219 */     i16 = j + ((m ^ i) & k ^ i) + i11;
/* 155:220 */     j = i16 << 19 | i16 >>> 13;
/* 156:221 */     i16 = i + ((k ^ m) & j ^ m) + i12;
/* 157:222 */     i = i16 << 3 | i16 >>> 29;
/* 158:223 */     i16 = m + ((j ^ k) & i ^ k) + i13;
/* 159:224 */     m = i16 << 7 | i16 >>> 25;
/* 160:225 */     i16 = k + ((i ^ j) & m ^ j) + i14;
/* 161:226 */     k = i16 << 11 | i16 >>> 21;
/* 162:227 */     i16 = j + ((m ^ i) & k ^ i) + i15;
/* 163:228 */     j = i16 << 19 | i16 >>> 13;
/* 164:    */     
/* 165:230 */     i16 = i + (m & k | (m | k) & j) + n + 1518500249;
/* 166:231 */     i = i16 << 3 | i16 >>> 29;
/* 167:232 */     i16 = m + (k & j | (k | j) & i) + i4 + 1518500249;
/* 168:233 */     m = i16 << 5 | i16 >>> 27;
/* 169:234 */     i16 = k + (j & i | (j | i) & m) + i8 + 1518500249;
/* 170:235 */     k = i16 << 9 | i16 >>> 23;
/* 171:236 */     i16 = j + (i & m | (i | m) & k) + i12 + 1518500249;
/* 172:237 */     j = i16 << 13 | i16 >>> 19;
/* 173:238 */     i16 = i + (m & k | (m | k) & j) + i1 + 1518500249;
/* 174:239 */     i = i16 << 3 | i16 >>> 29;
/* 175:240 */     i16 = m + (k & j | (k | j) & i) + i5 + 1518500249;
/* 176:241 */     m = i16 << 5 | i16 >>> 27;
/* 177:242 */     i16 = k + (j & i | (j | i) & m) + i9 + 1518500249;
/* 178:243 */     k = i16 << 9 | i16 >>> 23;
/* 179:244 */     i16 = j + (i & m | (i | m) & k) + i13 + 1518500249;
/* 180:245 */     j = i16 << 13 | i16 >>> 19;
/* 181:246 */     i16 = i + (m & k | (m | k) & j) + i2 + 1518500249;
/* 182:247 */     i = i16 << 3 | i16 >>> 29;
/* 183:248 */     i16 = m + (k & j | (k | j) & i) + i6 + 1518500249;
/* 184:249 */     m = i16 << 5 | i16 >>> 27;
/* 185:250 */     i16 = k + (j & i | (j | i) & m) + i10 + 1518500249;
/* 186:251 */     k = i16 << 9 | i16 >>> 23;
/* 187:252 */     i16 = j + (i & m | (i | m) & k) + i14 + 1518500249;
/* 188:253 */     j = i16 << 13 | i16 >>> 19;
/* 189:254 */     i16 = i + (m & k | (m | k) & j) + i3 + 1518500249;
/* 190:255 */     i = i16 << 3 | i16 >>> 29;
/* 191:256 */     i16 = m + (k & j | (k | j) & i) + i7 + 1518500249;
/* 192:257 */     m = i16 << 5 | i16 >>> 27;
/* 193:258 */     i16 = k + (j & i | (j | i) & m) + i11 + 1518500249;
/* 194:259 */     k = i16 << 9 | i16 >>> 23;
/* 195:260 */     i16 = j + (i & m | (i | m) & k) + i15 + 1518500249;
/* 196:261 */     j = i16 << 13 | i16 >>> 19;
/* 197:    */     
/* 198:263 */     i16 = i + (j ^ k ^ m) + n + 1859775393;
/* 199:264 */     i = i16 << 3 | i16 >>> 29;
/* 200:265 */     i16 = m + (i ^ j ^ k) + i8 + 1859775393;
/* 201:266 */     m = i16 << 9 | i16 >>> 23;
/* 202:267 */     i16 = k + (m ^ i ^ j) + i4 + 1859775393;
/* 203:268 */     k = i16 << 11 | i16 >>> 21;
/* 204:269 */     i16 = j + (k ^ m ^ i) + i12 + 1859775393;
/* 205:270 */     j = i16 << 15 | i16 >>> 17;
/* 206:271 */     i16 = i + (j ^ k ^ m) + i2 + 1859775393;
/* 207:272 */     i = i16 << 3 | i16 >>> 29;
/* 208:273 */     i16 = m + (i ^ j ^ k) + i10 + 1859775393;
/* 209:274 */     m = i16 << 9 | i16 >>> 23;
/* 210:275 */     i16 = k + (m ^ i ^ j) + i6 + 1859775393;
/* 211:276 */     k = i16 << 11 | i16 >>> 21;
/* 212:277 */     i16 = j + (k ^ m ^ i) + i14 + 1859775393;
/* 213:278 */     j = i16 << 15 | i16 >>> 17;
/* 214:279 */     i16 = i + (j ^ k ^ m) + i1 + 1859775393;
/* 215:280 */     i = i16 << 3 | i16 >>> 29;
/* 216:281 */     i16 = m + (i ^ j ^ k) + i9 + 1859775393;
/* 217:282 */     m = i16 << 9 | i16 >>> 23;
/* 218:283 */     i16 = k + (m ^ i ^ j) + i5 + 1859775393;
/* 219:284 */     k = i16 << 11 | i16 >>> 21;
/* 220:285 */     i16 = j + (k ^ m ^ i) + i13 + 1859775393;
/* 221:286 */     j = i16 << 15 | i16 >>> 17;
/* 222:287 */     i16 = i + (j ^ k ^ m) + i3 + 1859775393;
/* 223:288 */     i = i16 << 3 | i16 >>> 29;
/* 224:289 */     i16 = m + (i ^ j ^ k) + i11 + 1859775393;
/* 225:290 */     m = i16 << 9 | i16 >>> 23;
/* 226:291 */     i16 = k + (m ^ i ^ j) + i7 + 1859775393;
/* 227:292 */     k = i16 << 11 | i16 >>> 21;
/* 228:293 */     i16 = j + (k ^ m ^ i) + i15 + 1859775393;
/* 229:294 */     j = i16 << 15 | i16 >>> 17;
/* 230:    */     
/* 231:296 */     this.currentVal[0] += i;
/* 232:297 */     this.currentVal[1] += j;
/* 233:298 */     this.currentVal[2] += k;
/* 234:299 */     this.currentVal[3] += m;
/* 235:    */   }
/* 236:    */   
/* 237:    */   public String toString()
/* 238:    */   {
/* 239:305 */     return "MD4";
/* 240:    */   }
/* 241:    */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     fr.cryptohash.MD4
 * JD-Core Version:    0.7.1
 */