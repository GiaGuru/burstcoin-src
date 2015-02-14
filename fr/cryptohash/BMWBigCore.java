/*   1:    */ package fr.cryptohash;
/*   2:    */ 
/*   3:    */ abstract class BMWBigCore
/*   4:    */   extends DigestEngine
/*   5:    */ {
/*   6:    */   private long[] M;
/*   7:    */   private long[] H;
/*   8:    */   private long[] H2;
/*   9:    */   private long[] Q;
/*  10:    */   private long[] W;
/*  11:    */   
/*  12:    */   public int getBlockLength()
/*  13:    */   {
/*  14: 53 */     return 128;
/*  15:    */   }
/*  16:    */   
/*  17:    */   protected Digest copyState(BMWBigCore paramBMWBigCore)
/*  18:    */   {
/*  19: 59 */     System.arraycopy(this.H, 0, paramBMWBigCore.H, 0, this.H.length);
/*  20: 60 */     return super.copyState(paramBMWBigCore);
/*  21:    */   }
/*  22:    */   
/*  23:    */   protected void engineReset()
/*  24:    */   {
/*  25: 66 */     long[] arrayOfLong = getInitVal();
/*  26: 67 */     System.arraycopy(arrayOfLong, 0, this.H, 0, arrayOfLong.length);
/*  27:    */   }
/*  28:    */   
/*  29: 72 */   private static final long[] FINAL = { -6148914691236517216L, -6148914691236517215L, -6148914691236517214L, -6148914691236517213L, -6148914691236517212L, -6148914691236517211L, -6148914691236517210L, -6148914691236517209L, -6148914691236517208L, -6148914691236517207L, -6148914691236517206L, -6148914691236517205L, -6148914691236517204L, -6148914691236517203L, -6148914691236517202L, -6148914691236517201L };
/*  30: 83 */   private static final long[] K = { 6148914691236517200L, 6533221859438799525L, 6917529027641081850L, 7301836195843364175L, 7686143364045646500L, 8070450532247928825L, 8454757700450211150L, 8839064868652493475L, 9223372036854775800L, -8839064868652493491L, -8454757700450211166L, -8070450532247928841L, -7686143364045646516L, -7301836195843364191L, -6917529027641081866L, -6533221859438799541L };
/*  31:    */   
/*  32:    */   abstract long[] getInitVal();
/*  33:    */   
/*  34:    */   private void compress(long[] paramArrayOfLong)
/*  35:    */   {
/*  36: 96 */     long[] arrayOfLong1 = this.H;
/*  37: 97 */     long[] arrayOfLong2 = this.Q;
/*  38: 98 */     long[] arrayOfLong3 = this.W;
/*  39: 99 */     arrayOfLong3[0] = ((paramArrayOfLong[5] ^ arrayOfLong1[5]) - (paramArrayOfLong[7] ^ arrayOfLong1[7]) + (paramArrayOfLong[10] ^ arrayOfLong1[10]) + (paramArrayOfLong[13] ^ arrayOfLong1[13]) + (paramArrayOfLong[14] ^ arrayOfLong1[14]));
/*  40:    */     
/*  41:101 */     arrayOfLong3[1] = ((paramArrayOfLong[6] ^ arrayOfLong1[6]) - (paramArrayOfLong[8] ^ arrayOfLong1[8]) + (paramArrayOfLong[11] ^ arrayOfLong1[11]) + (paramArrayOfLong[14] ^ arrayOfLong1[14]) - (paramArrayOfLong[15] ^ arrayOfLong1[15]));
/*  42:    */     
/*  43:103 */     arrayOfLong3[2] = ((paramArrayOfLong[0] ^ arrayOfLong1[0]) + (paramArrayOfLong[7] ^ arrayOfLong1[7]) + (paramArrayOfLong[9] ^ arrayOfLong1[9]) - (paramArrayOfLong[12] ^ arrayOfLong1[12]) + (paramArrayOfLong[15] ^ arrayOfLong1[15]));
/*  44:    */     
/*  45:105 */     arrayOfLong3[3] = ((paramArrayOfLong[0] ^ arrayOfLong1[0]) - (paramArrayOfLong[1] ^ arrayOfLong1[1]) + (paramArrayOfLong[8] ^ arrayOfLong1[8]) - (paramArrayOfLong[10] ^ arrayOfLong1[10]) + (paramArrayOfLong[13] ^ arrayOfLong1[13]));
/*  46:    */     
/*  47:107 */     arrayOfLong3[4] = ((paramArrayOfLong[1] ^ arrayOfLong1[1]) + (paramArrayOfLong[2] ^ arrayOfLong1[2]) + (paramArrayOfLong[9] ^ arrayOfLong1[9]) - (paramArrayOfLong[11] ^ arrayOfLong1[11]) - (paramArrayOfLong[14] ^ arrayOfLong1[14]));
/*  48:    */     
/*  49:109 */     arrayOfLong3[5] = ((paramArrayOfLong[3] ^ arrayOfLong1[3]) - (paramArrayOfLong[2] ^ arrayOfLong1[2]) + (paramArrayOfLong[10] ^ arrayOfLong1[10]) - (paramArrayOfLong[12] ^ arrayOfLong1[12]) + (paramArrayOfLong[15] ^ arrayOfLong1[15]));
/*  50:    */     
/*  51:111 */     arrayOfLong3[6] = ((paramArrayOfLong[4] ^ arrayOfLong1[4]) - (paramArrayOfLong[0] ^ arrayOfLong1[0]) - (paramArrayOfLong[3] ^ arrayOfLong1[3]) - (paramArrayOfLong[11] ^ arrayOfLong1[11]) + (paramArrayOfLong[13] ^ arrayOfLong1[13]));
/*  52:    */     
/*  53:113 */     arrayOfLong3[7] = ((paramArrayOfLong[1] ^ arrayOfLong1[1]) - (paramArrayOfLong[4] ^ arrayOfLong1[4]) - (paramArrayOfLong[5] ^ arrayOfLong1[5]) - (paramArrayOfLong[12] ^ arrayOfLong1[12]) - (paramArrayOfLong[14] ^ arrayOfLong1[14]));
/*  54:    */     
/*  55:115 */     arrayOfLong3[8] = ((paramArrayOfLong[2] ^ arrayOfLong1[2]) - (paramArrayOfLong[5] ^ arrayOfLong1[5]) - (paramArrayOfLong[6] ^ arrayOfLong1[6]) + (paramArrayOfLong[13] ^ arrayOfLong1[13]) - (paramArrayOfLong[15] ^ arrayOfLong1[15]));
/*  56:    */     
/*  57:117 */     arrayOfLong3[9] = ((paramArrayOfLong[0] ^ arrayOfLong1[0]) - (paramArrayOfLong[3] ^ arrayOfLong1[3]) + (paramArrayOfLong[6] ^ arrayOfLong1[6]) - (paramArrayOfLong[7] ^ arrayOfLong1[7]) + (paramArrayOfLong[14] ^ arrayOfLong1[14]));
/*  58:    */     
/*  59:119 */     arrayOfLong3[10] = ((paramArrayOfLong[8] ^ arrayOfLong1[8]) - (paramArrayOfLong[1] ^ arrayOfLong1[1]) - (paramArrayOfLong[4] ^ arrayOfLong1[4]) - (paramArrayOfLong[7] ^ arrayOfLong1[7]) + (paramArrayOfLong[15] ^ arrayOfLong1[15]));
/*  60:    */     
/*  61:121 */     arrayOfLong3[11] = ((paramArrayOfLong[8] ^ arrayOfLong1[8]) - (paramArrayOfLong[0] ^ arrayOfLong1[0]) - (paramArrayOfLong[2] ^ arrayOfLong1[2]) - (paramArrayOfLong[5] ^ arrayOfLong1[5]) + (paramArrayOfLong[9] ^ arrayOfLong1[9]));
/*  62:    */     
/*  63:123 */     arrayOfLong3[12] = ((paramArrayOfLong[1] ^ arrayOfLong1[1]) + (paramArrayOfLong[3] ^ arrayOfLong1[3]) - (paramArrayOfLong[6] ^ arrayOfLong1[6]) - (paramArrayOfLong[9] ^ arrayOfLong1[9]) + (paramArrayOfLong[10] ^ arrayOfLong1[10]));
/*  64:    */     
/*  65:125 */     arrayOfLong3[13] = ((paramArrayOfLong[2] ^ arrayOfLong1[2]) + (paramArrayOfLong[4] ^ arrayOfLong1[4]) + (paramArrayOfLong[7] ^ arrayOfLong1[7]) + (paramArrayOfLong[10] ^ arrayOfLong1[10]) + (paramArrayOfLong[11] ^ arrayOfLong1[11]));
/*  66:    */     
/*  67:127 */     arrayOfLong3[14] = ((paramArrayOfLong[3] ^ arrayOfLong1[3]) - (paramArrayOfLong[5] ^ arrayOfLong1[5]) + (paramArrayOfLong[8] ^ arrayOfLong1[8]) - (paramArrayOfLong[11] ^ arrayOfLong1[11]) - (paramArrayOfLong[12] ^ arrayOfLong1[12]));
/*  68:    */     
/*  69:129 */     arrayOfLong3[15] = ((paramArrayOfLong[12] ^ arrayOfLong1[12]) - (paramArrayOfLong[4] ^ arrayOfLong1[4]) - (paramArrayOfLong[6] ^ arrayOfLong1[6]) - (paramArrayOfLong[9] ^ arrayOfLong1[9]) + (paramArrayOfLong[13] ^ arrayOfLong1[13]));
/*  70:131 */     for (int i = 0; i < 15; i += 5)
/*  71:    */     {
/*  72:132 */       arrayOfLong2[(i + 0)] = ((arrayOfLong3[(i + 0)] >>> 1 ^ arrayOfLong3[(i + 0)] << 3 ^ circularLeft(arrayOfLong3[(i + 0)], 4) ^ circularLeft(arrayOfLong3[(i + 0)], 37)) + arrayOfLong1[(i + 1)]);
/*  73:    */       
/*  74:    */ 
/*  75:135 */       arrayOfLong2[(i + 1)] = ((arrayOfLong3[(i + 1)] >>> 1 ^ arrayOfLong3[(i + 1)] << 2 ^ circularLeft(arrayOfLong3[(i + 1)], 13) ^ circularLeft(arrayOfLong3[(i + 1)], 43)) + arrayOfLong1[(i + 2)]);
/*  76:    */       
/*  77:    */ 
/*  78:138 */       arrayOfLong2[(i + 2)] = ((arrayOfLong3[(i + 2)] >>> 2 ^ arrayOfLong3[(i + 2)] << 1 ^ circularLeft(arrayOfLong3[(i + 2)], 19) ^ circularLeft(arrayOfLong3[(i + 2)], 53)) + arrayOfLong1[(i + 3)]);
/*  79:    */       
/*  80:    */ 
/*  81:141 */       arrayOfLong2[(i + 3)] = ((arrayOfLong3[(i + 3)] >>> 2 ^ arrayOfLong3[(i + 3)] << 2 ^ circularLeft(arrayOfLong3[(i + 3)], 28) ^ circularLeft(arrayOfLong3[(i + 3)], 59)) + arrayOfLong1[(i + 4)]);
/*  82:    */       
/*  83:    */ 
/*  84:144 */       arrayOfLong2[(i + 4)] = ((arrayOfLong3[(i + 4)] >>> 1 ^ arrayOfLong3[(i + 4)]) + arrayOfLong1[(i + 5)]);
/*  85:    */     }
/*  86:146 */     arrayOfLong2[15] = ((arrayOfLong3[15] >>> 1 ^ arrayOfLong3[15] << 3 ^ circularLeft(arrayOfLong3[15], 4) ^ circularLeft(arrayOfLong3[15], 37)) + arrayOfLong1[0]);
/*  87:150 */     for (i = 16; i < 18; i++) {
/*  88:151 */       arrayOfLong2[i] = ((arrayOfLong2[(i - 16)] >>> 1 ^ arrayOfLong2[(i - 16)] << 2 ^ circularLeft(arrayOfLong2[(i - 16)], 13) ^ circularLeft(arrayOfLong2[(i - 16)], 43)) + (arrayOfLong2[(i - 15)] >>> 2 ^ arrayOfLong2[(i - 15)] << 1 ^ circularLeft(arrayOfLong2[(i - 15)], 19) ^ circularLeft(arrayOfLong2[(i - 15)], 53)) + (arrayOfLong2[(i - 14)] >>> 2 ^ arrayOfLong2[(i - 14)] << 2 ^ circularLeft(arrayOfLong2[(i - 14)], 28) ^ circularLeft(arrayOfLong2[(i - 14)], 59)) + (arrayOfLong2[(i - 13)] >>> 1 ^ arrayOfLong2[(i - 13)] << 3 ^ circularLeft(arrayOfLong2[(i - 13)], 4) ^ circularLeft(arrayOfLong2[(i - 13)], 37)) + (arrayOfLong2[(i - 12)] >>> 1 ^ arrayOfLong2[(i - 12)] << 2 ^ circularLeft(arrayOfLong2[(i - 12)], 13) ^ circularLeft(arrayOfLong2[(i - 12)], 43)) + (arrayOfLong2[(i - 11)] >>> 2 ^ arrayOfLong2[(i - 11)] << 1 ^ circularLeft(arrayOfLong2[(i - 11)], 19) ^ circularLeft(arrayOfLong2[(i - 11)], 53)) + (arrayOfLong2[(i - 10)] >>> 2 ^ arrayOfLong2[(i - 10)] << 2 ^ circularLeft(arrayOfLong2[(i - 10)], 28) ^ circularLeft(arrayOfLong2[(i - 10)], 59)) + (arrayOfLong2[(i - 9)] >>> 1 ^ arrayOfLong2[(i - 9)] << 3 ^ circularLeft(arrayOfLong2[(i - 9)], 4) ^ circularLeft(arrayOfLong2[(i - 9)], 37)) + (arrayOfLong2[(i - 8)] >>> 1 ^ arrayOfLong2[(i - 8)] << 2 ^ circularLeft(arrayOfLong2[(i - 8)], 13) ^ circularLeft(arrayOfLong2[(i - 8)], 43)) + (arrayOfLong2[(i - 7)] >>> 2 ^ arrayOfLong2[(i - 7)] << 1 ^ circularLeft(arrayOfLong2[(i - 7)], 19) ^ circularLeft(arrayOfLong2[(i - 7)], 53)) + (arrayOfLong2[(i - 6)] >>> 2 ^ arrayOfLong2[(i - 6)] << 2 ^ circularLeft(arrayOfLong2[(i - 6)], 28) ^ circularLeft(arrayOfLong2[(i - 6)], 59)) + (arrayOfLong2[(i - 5)] >>> 1 ^ arrayOfLong2[(i - 5)] << 3 ^ circularLeft(arrayOfLong2[(i - 5)], 4) ^ circularLeft(arrayOfLong2[(i - 5)], 37)) + (arrayOfLong2[(i - 4)] >>> 1 ^ arrayOfLong2[(i - 4)] << 2 ^ circularLeft(arrayOfLong2[(i - 4)], 13) ^ circularLeft(arrayOfLong2[(i - 4)], 43)) + (arrayOfLong2[(i - 3)] >>> 2 ^ arrayOfLong2[(i - 3)] << 1 ^ circularLeft(arrayOfLong2[(i - 3)], 19) ^ circularLeft(arrayOfLong2[(i - 3)], 53)) + (arrayOfLong2[(i - 2)] >>> 2 ^ arrayOfLong2[(i - 2)] << 2 ^ circularLeft(arrayOfLong2[(i - 2)], 28) ^ circularLeft(arrayOfLong2[(i - 2)], 59)) + (arrayOfLong2[(i - 1)] >>> 1 ^ arrayOfLong2[(i - 1)] << 3 ^ circularLeft(arrayOfLong2[(i - 1)], 4) ^ circularLeft(arrayOfLong2[(i - 1)], 37)) + (circularLeft(paramArrayOfLong[(i - 16 + 0 & 0xF)], (i - 16 + 0 & 0xF) + 1) + circularLeft(paramArrayOfLong[(i - 16 + 3 & 0xF)], (i - 16 + 3 & 0xF) + 1) - circularLeft(paramArrayOfLong[(i - 16 + 10 & 0xF)], (i - 16 + 10 & 0xF) + 1) + K[(i - 16)] ^ arrayOfLong1[(i - 16 + 7 & 0xF)]));
/*  89:    */     }
/*  90:207 */     for (i = 18; i < 32; i++) {
/*  91:208 */       arrayOfLong2[i] = (arrayOfLong2[(i - 16)] + circularLeft(arrayOfLong2[(i - 15)], 5) + arrayOfLong2[(i - 14)] + circularLeft(arrayOfLong2[(i - 13)], 11) + arrayOfLong2[(i - 12)] + circularLeft(arrayOfLong2[(i - 11)], 27) + arrayOfLong2[(i - 10)] + circularLeft(arrayOfLong2[(i - 9)], 32) + arrayOfLong2[(i - 8)] + circularLeft(arrayOfLong2[(i - 7)], 37) + arrayOfLong2[(i - 6)] + circularLeft(arrayOfLong2[(i - 5)], 43) + arrayOfLong2[(i - 4)] + circularLeft(arrayOfLong2[(i - 3)], 53) + (arrayOfLong2[(i - 2)] >>> 1 ^ arrayOfLong2[(i - 2)]) + (arrayOfLong2[(i - 1)] >>> 2 ^ arrayOfLong2[(i - 1)]) + (circularLeft(paramArrayOfLong[(i - 16 + 0 & 0xF)], (i - 16 + 0 & 0xF) + 1) + circularLeft(paramArrayOfLong[(i - 16 + 3 & 0xF)], (i - 16 + 3 & 0xF) + 1) - circularLeft(paramArrayOfLong[(i - 16 + 10 & 0xF)], (i - 16 + 10 & 0xF) + 1) + K[(i - 16)] ^ arrayOfLong1[(i - 16 + 7 & 0xF)]));
/*  92:    */     }
/*  93:226 */     long l1 = arrayOfLong2[16] ^ arrayOfLong2[17] ^ arrayOfLong2[18] ^ arrayOfLong2[19] ^ arrayOfLong2[20] ^ arrayOfLong2[21] ^ arrayOfLong2[22] ^ arrayOfLong2[23];
/*  94:    */     
/*  95:228 */     long l2 = l1 ^ arrayOfLong2[24] ^ arrayOfLong2[25] ^ arrayOfLong2[26] ^ arrayOfLong2[27] ^ arrayOfLong2[28] ^ arrayOfLong2[29] ^ arrayOfLong2[30] ^ arrayOfLong2[31];
/*  96:    */     
/*  97:230 */     arrayOfLong1[0] = ((l2 << 5 ^ arrayOfLong2[16] >>> 5 ^ paramArrayOfLong[0]) + (l1 ^ arrayOfLong2[24] ^ arrayOfLong2[0]));
/*  98:231 */     arrayOfLong1[1] = ((l2 >>> 7 ^ arrayOfLong2[17] << 8 ^ paramArrayOfLong[1]) + (l1 ^ arrayOfLong2[25] ^ arrayOfLong2[1]));
/*  99:232 */     arrayOfLong1[2] = ((l2 >>> 5 ^ arrayOfLong2[18] << 5 ^ paramArrayOfLong[2]) + (l1 ^ arrayOfLong2[26] ^ arrayOfLong2[2]));
/* 100:233 */     arrayOfLong1[3] = ((l2 >>> 1 ^ arrayOfLong2[19] << 5 ^ paramArrayOfLong[3]) + (l1 ^ arrayOfLong2[27] ^ arrayOfLong2[3]));
/* 101:234 */     arrayOfLong1[4] = ((l2 >>> 3 ^ arrayOfLong2[20] << 0 ^ paramArrayOfLong[4]) + (l1 ^ arrayOfLong2[28] ^ arrayOfLong2[4]));
/* 102:235 */     arrayOfLong1[5] = ((l2 << 6 ^ arrayOfLong2[21] >>> 6 ^ paramArrayOfLong[5]) + (l1 ^ arrayOfLong2[29] ^ arrayOfLong2[5]));
/* 103:236 */     arrayOfLong1[6] = ((l2 >>> 4 ^ arrayOfLong2[22] << 6 ^ paramArrayOfLong[6]) + (l1 ^ arrayOfLong2[30] ^ arrayOfLong2[6]));
/* 104:237 */     arrayOfLong1[7] = ((l2 >>> 11 ^ arrayOfLong2[23] << 2 ^ paramArrayOfLong[7]) + (l1 ^ arrayOfLong2[31] ^ arrayOfLong2[7]));
/* 105:    */     
/* 106:239 */     arrayOfLong1[8] = (circularLeft(arrayOfLong1[4], 9) + (l2 ^ arrayOfLong2[24] ^ paramArrayOfLong[8]) + (l1 << 8 ^ arrayOfLong2[23] ^ arrayOfLong2[8]));
/* 107:    */     
/* 108:241 */     arrayOfLong1[9] = (circularLeft(arrayOfLong1[5], 10) + (l2 ^ arrayOfLong2[25] ^ paramArrayOfLong[9]) + (l1 >>> 6 ^ arrayOfLong2[16] ^ arrayOfLong2[9]));
/* 109:    */     
/* 110:243 */     arrayOfLong1[10] = (circularLeft(arrayOfLong1[6], 11) + (l2 ^ arrayOfLong2[26] ^ paramArrayOfLong[10]) + (l1 << 6 ^ arrayOfLong2[17] ^ arrayOfLong2[10]));
/* 111:    */     
/* 112:245 */     arrayOfLong1[11] = (circularLeft(arrayOfLong1[7], 12) + (l2 ^ arrayOfLong2[27] ^ paramArrayOfLong[11]) + (l1 << 4 ^ arrayOfLong2[18] ^ arrayOfLong2[11]));
/* 113:    */     
/* 114:247 */     arrayOfLong1[12] = (circularLeft(arrayOfLong1[0], 13) + (l2 ^ arrayOfLong2[28] ^ paramArrayOfLong[12]) + (l1 >>> 3 ^ arrayOfLong2[19] ^ arrayOfLong2[12]));
/* 115:    */     
/* 116:249 */     arrayOfLong1[13] = (circularLeft(arrayOfLong1[1], 14) + (l2 ^ arrayOfLong2[29] ^ paramArrayOfLong[13]) + (l1 >>> 4 ^ arrayOfLong2[20] ^ arrayOfLong2[13]));
/* 117:    */     
/* 118:251 */     arrayOfLong1[14] = (circularLeft(arrayOfLong1[2], 15) + (l2 ^ arrayOfLong2[30] ^ paramArrayOfLong[14]) + (l1 >>> 7 ^ arrayOfLong2[21] ^ arrayOfLong2[14]));
/* 119:    */     
/* 120:253 */     arrayOfLong1[15] = (circularLeft(arrayOfLong1[3], 16) + (l2 ^ arrayOfLong2[31] ^ paramArrayOfLong[15]) + (l1 >>> 2 ^ arrayOfLong2[22] ^ arrayOfLong2[15]));
/* 121:    */   }
/* 122:    */   
/* 123:    */   protected void doPadding(byte[] paramArrayOfByte, int paramInt)
/* 124:    */   {
/* 125:260 */     byte[] arrayOfByte = getBlockBuffer();
/* 126:261 */     int i = flush();
/* 127:262 */     long l = (getBlockCount() << 10) + (i << 3);
/* 128:263 */     arrayOfByte[(i++)] = Byte.MIN_VALUE;
/* 129:264 */     if (i > 120)
/* 130:    */     {
/* 131:265 */       for (j = i; j < 128; j++) {
/* 132:266 */         arrayOfByte[j] = 0;
/* 133:    */       }
/* 134:267 */       processBlock(arrayOfByte);
/* 135:268 */       i = 0;
/* 136:    */     }
/* 137:270 */     for (int j = i; j < 120; j++) {
/* 138:271 */       arrayOfByte[j] = 0;
/* 139:    */     }
/* 140:272 */     encodeLELong(l, arrayOfByte, 120);
/* 141:273 */     processBlock(arrayOfByte);
/* 142:274 */     long[] arrayOfLong = this.H;
/* 143:275 */     this.H = this.H2;
/* 144:276 */     this.H2 = arrayOfLong;
/* 145:277 */     System.arraycopy(FINAL, 0, this.H, 0, 16);
/* 146:278 */     compress(this.H2);
/* 147:279 */     int k = getDigestLength() >>> 3;
/* 148:280 */     int m = 0;
/* 149:280 */     for (int n = 16 - k; m < k; n++)
/* 150:    */     {
/* 151:281 */       encodeLELong(this.H[n], paramArrayOfByte, paramInt + 8 * m);m++;
/* 152:    */     }
/* 153:    */   }
/* 154:    */   
/* 155:    */   protected void doInit()
/* 156:    */   {
/* 157:287 */     this.M = new long[16];
/* 158:288 */     this.H = new long[16];
/* 159:289 */     this.H2 = new long[16];
/* 160:290 */     this.W = new long[16];
/* 161:291 */     this.Q = new long[32];
/* 162:292 */     engineReset();
/* 163:    */   }
/* 164:    */   
/* 165:    */   private static final void encodeLELong(long paramLong, byte[] paramArrayOfByte, int paramInt)
/* 166:    */   {
/* 167:306 */     paramArrayOfByte[(paramInt + 0)] = ((byte)(int)paramLong);
/* 168:307 */     paramArrayOfByte[(paramInt + 1)] = ((byte)(int)(paramLong >>> 8));
/* 169:308 */     paramArrayOfByte[(paramInt + 2)] = ((byte)(int)(paramLong >>> 16));
/* 170:309 */     paramArrayOfByte[(paramInt + 3)] = ((byte)(int)(paramLong >>> 24));
/* 171:310 */     paramArrayOfByte[(paramInt + 4)] = ((byte)(int)(paramLong >>> 32));
/* 172:311 */     paramArrayOfByte[(paramInt + 5)] = ((byte)(int)(paramLong >>> 40));
/* 173:312 */     paramArrayOfByte[(paramInt + 6)] = ((byte)(int)(paramLong >>> 48));
/* 174:313 */     paramArrayOfByte[(paramInt + 7)] = ((byte)(int)(paramLong >>> 56));
/* 175:    */   }
/* 176:    */   
/* 177:    */   private static final long decodeLELong(byte[] paramArrayOfByte, int paramInt)
/* 178:    */   {
/* 179:326 */     return paramArrayOfByte[(paramInt + 0)] & 0xFF | (paramArrayOfByte[(paramInt + 1)] & 0xFF) << 8 | (paramArrayOfByte[(paramInt + 2)] & 0xFF) << 16 | (paramArrayOfByte[(paramInt + 3)] & 0xFF) << 24 | (paramArrayOfByte[(paramInt + 4)] & 0xFF) << 32 | (paramArrayOfByte[(paramInt + 5)] & 0xFF) << 40 | (paramArrayOfByte[(paramInt + 6)] & 0xFF) << 48 | (paramArrayOfByte[(paramInt + 7)] & 0xFF) << 56;
/* 180:    */   }
/* 181:    */   
/* 182:    */   private static final long circularLeft(long paramLong, int paramInt)
/* 183:    */   {
/* 184:347 */     return paramLong << paramInt | paramLong >>> 64 - paramInt;
/* 185:    */   }
/* 186:    */   
/* 187:    */   protected void processBlock(byte[] paramArrayOfByte)
/* 188:    */   {
/* 189:353 */     for (int i = 0; i < 16; i++) {
/* 190:354 */       this.M[i] = decodeLELong(paramArrayOfByte, i * 8);
/* 191:    */     }
/* 192:355 */     compress(this.M);
/* 193:    */   }
/* 194:    */   
/* 195:    */   public String toString()
/* 196:    */   {
/* 197:361 */     return "BMW-" + (getDigestLength() << 3);
/* 198:    */   }
/* 199:    */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     fr.cryptohash.BMWBigCore
 * JD-Core Version:    0.7.1
 */