/*   1:    */ package fr.cryptohash;
/*   2:    */ 
/*   3:    */ public class MD5
/*   4:    */   extends MDHelper
/*   5:    */ {
/*   6:    */   private int[] currentVal;
/*   7:    */   private int[] X;
/*   8:    */   
/*   9:    */   public MD5()
/*  10:    */   {
/*  11: 48 */     super(true, 8);
/*  12:    */   }
/*  13:    */   
/*  14:    */   public Digest copy()
/*  15:    */   {
/*  16: 56 */     MD5 localMD5 = new MD5();
/*  17: 57 */     System.arraycopy(this.currentVal, 0, localMD5.currentVal, 0, this.currentVal.length);
/*  18:    */     
/*  19: 59 */     return copyState(localMD5);
/*  20:    */   }
/*  21:    */   
/*  22:    */   public int getDigestLength()
/*  23:    */   {
/*  24: 65 */     return 16;
/*  25:    */   }
/*  26:    */   
/*  27:    */   public int getBlockLength()
/*  28:    */   {
/*  29: 71 */     return 64;
/*  30:    */   }
/*  31:    */   
/*  32:    */   protected void engineReset()
/*  33:    */   {
/*  34: 77 */     this.currentVal[0] = 1732584193;
/*  35: 78 */     this.currentVal[1] = -271733879;
/*  36: 79 */     this.currentVal[2] = -1732584194;
/*  37: 80 */     this.currentVal[3] = 271733878;
/*  38:    */   }
/*  39:    */   
/*  40:    */   protected void doPadding(byte[] paramArrayOfByte, int paramInt)
/*  41:    */   {
/*  42: 86 */     makeMDPadding();
/*  43: 87 */     for (int i = 0; i < 4; i++) {
/*  44: 88 */       encodeLEInt(this.currentVal[i], paramArrayOfByte, paramInt + 4 * i);
/*  45:    */     }
/*  46:    */   }
/*  47:    */   
/*  48:    */   protected void doInit()
/*  49:    */   {
/*  50: 95 */     this.currentVal = new int[4];
/*  51: 96 */     this.X = new int[16];
/*  52: 97 */     engineReset();
/*  53:    */   }
/*  54:    */   
/*  55:    */   private static final int circularLeft(int paramInt1, int paramInt2)
/*  56:    */   {
/*  57:111 */     return paramInt1 << paramInt2 | paramInt1 >>> 32 - paramInt2;
/*  58:    */   }
/*  59:    */   
/*  60:    */   private static final void encodeLEInt(int paramInt1, byte[] paramArrayOfByte, int paramInt2)
/*  61:    */   {
/*  62:125 */     paramArrayOfByte[(paramInt2 + 0)] = ((byte)paramInt1);
/*  63:126 */     paramArrayOfByte[(paramInt2 + 1)] = ((byte)(paramInt1 >>> 8));
/*  64:127 */     paramArrayOfByte[(paramInt2 + 2)] = ((byte)(paramInt1 >>> 16));
/*  65:128 */     paramArrayOfByte[(paramInt2 + 3)] = ((byte)(paramInt1 >>> 24));
/*  66:    */   }
/*  67:    */   
/*  68:    */   private static final int decodeLEInt(byte[] paramArrayOfByte, int paramInt)
/*  69:    */   {
/*  70:141 */     return paramArrayOfByte[paramInt] & 0xFF | (paramArrayOfByte[(paramInt + 1)] & 0xFF) << 8 | (paramArrayOfByte[(paramInt + 2)] & 0xFF) << 16 | (paramArrayOfByte[(paramInt + 3)] & 0xFF) << 24;
/*  71:    */   }
/*  72:    */   
/*  73:    */   private static final int F(int paramInt1, int paramInt2, int paramInt3)
/*  74:    */   {
/*  75:149 */     return paramInt2 & paramInt1 | paramInt3 & (paramInt1 ^ 0xFFFFFFFF);
/*  76:    */   }
/*  77:    */   
/*  78:    */   private static final int G(int paramInt1, int paramInt2, int paramInt3)
/*  79:    */   {
/*  80:154 */     return paramInt1 & paramInt3 | paramInt2 & (paramInt3 ^ 0xFFFFFFFF);
/*  81:    */   }
/*  82:    */   
/*  83:    */   private static final int H(int paramInt1, int paramInt2, int paramInt3)
/*  84:    */   {
/*  85:159 */     return paramInt1 ^ paramInt2 ^ paramInt3;
/*  86:    */   }
/*  87:    */   
/*  88:    */   private static final int I(int paramInt1, int paramInt2, int paramInt3)
/*  89:    */   {
/*  90:164 */     return paramInt2 ^ (paramInt1 | paramInt3 ^ 0xFFFFFFFF);
/*  91:    */   }
/*  92:    */   
/*  93:    */   protected void processBlock(byte[] paramArrayOfByte)
/*  94:    */   {
/*  95:170 */     int i = this.currentVal[0];int j = this.currentVal[1];
/*  96:171 */     int k = this.currentVal[2];int m = this.currentVal[3];
/*  97:173 */     for (int n = 0; n < 16; n++) {
/*  98:174 */       this.X[n] = decodeLEInt(paramArrayOfByte, 4 * n);
/*  99:    */     }
/* 100:176 */     i = j + circularLeft(i + F(j, k, m) + this.X[0] + -680876936, 7);
/* 101:177 */     m = i + circularLeft(m + F(i, j, k) + this.X[1] + -389564586, 12);
/* 102:178 */     k = m + circularLeft(k + F(m, i, j) + this.X[2] + 606105819, 17);
/* 103:179 */     j = k + circularLeft(j + F(k, m, i) + this.X[3] + -1044525330, 22);
/* 104:180 */     i = j + circularLeft(i + F(j, k, m) + this.X[4] + -176418897, 7);
/* 105:181 */     m = i + circularLeft(m + F(i, j, k) + this.X[5] + 1200080426, 12);
/* 106:182 */     k = m + circularLeft(k + F(m, i, j) + this.X[6] + -1473231341, 17);
/* 107:183 */     j = k + circularLeft(j + F(k, m, i) + this.X[7] + -45705983, 22);
/* 108:184 */     i = j + circularLeft(i + F(j, k, m) + this.X[8] + 1770035416, 7);
/* 109:185 */     m = i + circularLeft(m + F(i, j, k) + this.X[9] + -1958414417, 12);
/* 110:186 */     k = m + circularLeft(k + F(m, i, j) + this.X[10] + -42063, 17);
/* 111:187 */     j = k + circularLeft(j + F(k, m, i) + this.X[11] + -1990404162, 22);
/* 112:188 */     i = j + circularLeft(i + F(j, k, m) + this.X[12] + 1804603682, 7);
/* 113:189 */     m = i + circularLeft(m + F(i, j, k) + this.X[13] + -40341101, 12);
/* 114:190 */     k = m + circularLeft(k + F(m, i, j) + this.X[14] + -1502002290, 17);
/* 115:191 */     j = k + circularLeft(j + F(k, m, i) + this.X[15] + 1236535329, 22);
/* 116:    */     
/* 117:193 */     i = j + circularLeft(i + G(j, k, m) + this.X[1] + -165796510, 5);
/* 118:194 */     m = i + circularLeft(m + G(i, j, k) + this.X[6] + -1069501632, 9);
/* 119:195 */     k = m + circularLeft(k + G(m, i, j) + this.X[11] + 643717713, 14);
/* 120:196 */     j = k + circularLeft(j + G(k, m, i) + this.X[0] + -373897302, 20);
/* 121:197 */     i = j + circularLeft(i + G(j, k, m) + this.X[5] + -701558691, 5);
/* 122:198 */     m = i + circularLeft(m + G(i, j, k) + this.X[10] + 38016083, 9);
/* 123:199 */     k = m + circularLeft(k + G(m, i, j) + this.X[15] + -660478335, 14);
/* 124:200 */     j = k + circularLeft(j + G(k, m, i) + this.X[4] + -405537848, 20);
/* 125:201 */     i = j + circularLeft(i + G(j, k, m) + this.X[9] + 568446438, 5);
/* 126:202 */     m = i + circularLeft(m + G(i, j, k) + this.X[14] + -1019803690, 9);
/* 127:203 */     k = m + circularLeft(k + G(m, i, j) + this.X[3] + -187363961, 14);
/* 128:204 */     j = k + circularLeft(j + G(k, m, i) + this.X[8] + 1163531501, 20);
/* 129:205 */     i = j + circularLeft(i + G(j, k, m) + this.X[13] + -1444681467, 5);
/* 130:206 */     m = i + circularLeft(m + G(i, j, k) + this.X[2] + -51403784, 9);
/* 131:207 */     k = m + circularLeft(k + G(m, i, j) + this.X[7] + 1735328473, 14);
/* 132:208 */     j = k + circularLeft(j + G(k, m, i) + this.X[12] + -1926607734, 20);
/* 133:    */     
/* 134:210 */     i = j + circularLeft(i + H(j, k, m) + this.X[5] + -378558, 4);
/* 135:211 */     m = i + circularLeft(m + H(i, j, k) + this.X[8] + -2022574463, 11);
/* 136:212 */     k = m + circularLeft(k + H(m, i, j) + this.X[11] + 1839030562, 16);
/* 137:213 */     j = k + circularLeft(j + H(k, m, i) + this.X[14] + -35309556, 23);
/* 138:214 */     i = j + circularLeft(i + H(j, k, m) + this.X[1] + -1530992060, 4);
/* 139:215 */     m = i + circularLeft(m + H(i, j, k) + this.X[4] + 1272893353, 11);
/* 140:216 */     k = m + circularLeft(k + H(m, i, j) + this.X[7] + -155497632, 16);
/* 141:217 */     j = k + circularLeft(j + H(k, m, i) + this.X[10] + -1094730640, 23);
/* 142:218 */     i = j + circularLeft(i + H(j, k, m) + this.X[13] + 681279174, 4);
/* 143:219 */     m = i + circularLeft(m + H(i, j, k) + this.X[0] + -358537222, 11);
/* 144:220 */     k = m + circularLeft(k + H(m, i, j) + this.X[3] + -722521979, 16);
/* 145:221 */     j = k + circularLeft(j + H(k, m, i) + this.X[6] + 76029189, 23);
/* 146:222 */     i = j + circularLeft(i + H(j, k, m) + this.X[9] + -640364487, 4);
/* 147:223 */     m = i + circularLeft(m + H(i, j, k) + this.X[12] + -421815835, 11);
/* 148:224 */     k = m + circularLeft(k + H(m, i, j) + this.X[15] + 530742520, 16);
/* 149:225 */     j = k + circularLeft(j + H(k, m, i) + this.X[2] + -995338651, 23);
/* 150:    */     
/* 151:227 */     i = j + circularLeft(i + I(j, k, m) + this.X[0] + -198630844, 6);
/* 152:228 */     m = i + circularLeft(m + I(i, j, k) + this.X[7] + 1126891415, 10);
/* 153:229 */     k = m + circularLeft(k + I(m, i, j) + this.X[14] + -1416354905, 15);
/* 154:230 */     j = k + circularLeft(j + I(k, m, i) + this.X[5] + -57434055, 21);
/* 155:231 */     i = j + circularLeft(i + I(j, k, m) + this.X[12] + 1700485571, 6);
/* 156:232 */     m = i + circularLeft(m + I(i, j, k) + this.X[3] + -1894986606, 10);
/* 157:233 */     k = m + circularLeft(k + I(m, i, j) + this.X[10] + -1051523, 15);
/* 158:234 */     j = k + circularLeft(j + I(k, m, i) + this.X[1] + -2054922799, 21);
/* 159:235 */     i = j + circularLeft(i + I(j, k, m) + this.X[8] + 1873313359, 6);
/* 160:236 */     m = i + circularLeft(m + I(i, j, k) + this.X[15] + -30611744, 10);
/* 161:237 */     k = m + circularLeft(k + I(m, i, j) + this.X[6] + -1560198380, 15);
/* 162:238 */     j = k + circularLeft(j + I(k, m, i) + this.X[13] + 1309151649, 21);
/* 163:239 */     i = j + circularLeft(i + I(j, k, m) + this.X[4] + -145523070, 6);
/* 164:240 */     m = i + circularLeft(m + I(i, j, k) + this.X[11] + -1120210379, 10);
/* 165:241 */     k = m + circularLeft(k + I(m, i, j) + this.X[2] + 718787259, 15);
/* 166:242 */     j = k + circularLeft(j + I(k, m, i) + this.X[9] + -343485551, 21);
/* 167:    */     
/* 168:244 */     this.currentVal[0] += i;
/* 169:245 */     this.currentVal[1] += j;
/* 170:246 */     this.currentVal[2] += k;
/* 171:247 */     this.currentVal[3] += m;
/* 172:    */   }
/* 173:    */   
/* 174:    */   public String toString()
/* 175:    */   {
/* 176:253 */     return "MD5";
/* 177:    */   }
/* 178:    */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     fr.cryptohash.MD5
 * JD-Core Version:    0.7.1
 */