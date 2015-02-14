/*   1:    */ package fr.cryptohash;
/*   2:    */ 
/*   3:    */ public class RIPEMD160
/*   4:    */   extends MDHelper
/*   5:    */ {
/*   6:    */   private int[] currentVal;
/*   7:    */   private int[] X;
/*   8:    */   
/*   9:    */   public RIPEMD160()
/*  10:    */   {
/*  11: 47 */     super(true, 8);
/*  12:    */   }
/*  13:    */   
/*  14:    */   public Digest copy()
/*  15:    */   {
/*  16: 55 */     RIPEMD160 localRIPEMD160 = new RIPEMD160();
/*  17: 56 */     System.arraycopy(this.currentVal, 0, localRIPEMD160.currentVal, 0, this.currentVal.length);
/*  18:    */     
/*  19: 58 */     return copyState(localRIPEMD160);
/*  20:    */   }
/*  21:    */   
/*  22:    */   public int getDigestLength()
/*  23:    */   {
/*  24: 64 */     return 20;
/*  25:    */   }
/*  26:    */   
/*  27:    */   public int getBlockLength()
/*  28:    */   {
/*  29: 70 */     return 64;
/*  30:    */   }
/*  31:    */   
/*  32:    */   protected void engineReset()
/*  33:    */   {
/*  34: 76 */     this.currentVal[0] = 1732584193;
/*  35: 77 */     this.currentVal[1] = -271733879;
/*  36: 78 */     this.currentVal[2] = -1732584194;
/*  37: 79 */     this.currentVal[3] = 271733878;
/*  38: 80 */     this.currentVal[4] = -1009589776;
/*  39:    */   }
/*  40:    */   
/*  41:    */   protected void doPadding(byte[] paramArrayOfByte, int paramInt)
/*  42:    */   {
/*  43: 86 */     makeMDPadding();
/*  44: 87 */     for (int i = 0; i < 5; i++) {
/*  45: 88 */       encodeLEInt(this.currentVal[i], paramArrayOfByte, paramInt + 4 * i);
/*  46:    */     }
/*  47:    */   }
/*  48:    */   
/*  49:    */   protected void doInit()
/*  50:    */   {
/*  51: 95 */     this.currentVal = new int[5];
/*  52: 96 */     this.X = new int[16];
/*  53: 97 */     engineReset();
/*  54:    */   }
/*  55:    */   
/*  56:    */   private static final void encodeLEInt(int paramInt1, byte[] paramArrayOfByte, int paramInt2)
/*  57:    */   {
/*  58:111 */     paramArrayOfByte[(paramInt2 + 0)] = ((byte)paramInt1);
/*  59:112 */     paramArrayOfByte[(paramInt2 + 1)] = ((byte)(paramInt1 >>> 8));
/*  60:113 */     paramArrayOfByte[(paramInt2 + 2)] = ((byte)(paramInt1 >>> 16));
/*  61:114 */     paramArrayOfByte[(paramInt2 + 3)] = ((byte)(paramInt1 >>> 24));
/*  62:    */   }
/*  63:    */   
/*  64:    */   private static final int decodeLEInt(byte[] paramArrayOfByte, int paramInt)
/*  65:    */   {
/*  66:127 */     return paramArrayOfByte[(paramInt + 0)] & 0xFF | (paramArrayOfByte[(paramInt + 1)] & 0xFF) << 8 | (paramArrayOfByte[(paramInt + 2)] & 0xFF) << 16 | (paramArrayOfByte[(paramInt + 3)] & 0xFF) << 24;
/*  67:    */   }
/*  68:    */   
/*  69:    */   private static final int circularLeft(int paramInt1, int paramInt2)
/*  70:    */   {
/*  71:144 */     return paramInt1 << paramInt2 | paramInt1 >>> 32 - paramInt2;
/*  72:    */   }
/*  73:    */   
/*  74:147 */   private static final int[] r1 = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 7, 4, 13, 1, 10, 6, 15, 3, 12, 0, 9, 5, 2, 14, 11, 8, 3, 10, 14, 4, 9, 15, 8, 1, 2, 7, 0, 6, 13, 11, 5, 12, 1, 9, 11, 10, 0, 8, 12, 4, 13, 3, 7, 15, 14, 5, 6, 2, 4, 0, 5, 9, 7, 12, 2, 10, 14, 1, 3, 8, 11, 6, 15, 13 };
/*  75:155 */   private static final int[] r2 = { 5, 14, 7, 0, 9, 2, 11, 4, 13, 6, 15, 8, 1, 10, 3, 12, 6, 11, 3, 7, 0, 13, 5, 10, 14, 15, 8, 12, 4, 9, 1, 2, 15, 5, 1, 3, 7, 14, 6, 9, 11, 8, 12, 2, 10, 0, 4, 13, 8, 6, 4, 1, 3, 11, 15, 0, 5, 12, 2, 13, 9, 7, 10, 14, 12, 15, 10, 4, 1, 5, 8, 7, 6, 2, 13, 14, 0, 3, 9, 11 };
/*  76:163 */   private static final int[] s1 = { 11, 14, 15, 12, 5, 8, 7, 9, 11, 13, 14, 15, 6, 7, 9, 8, 7, 6, 8, 13, 11, 9, 7, 15, 7, 12, 15, 9, 11, 7, 13, 12, 11, 13, 6, 7, 14, 9, 13, 15, 14, 8, 13, 6, 5, 12, 7, 5, 11, 12, 14, 15, 14, 15, 9, 8, 9, 14, 5, 6, 8, 6, 5, 12, 9, 15, 5, 11, 6, 8, 13, 12, 5, 12, 13, 14, 11, 8, 5, 6 };
/*  77:171 */   private static final int[] s2 = { 8, 9, 9, 11, 13, 15, 15, 5, 7, 7, 8, 11, 14, 14, 12, 6, 9, 13, 15, 7, 12, 8, 9, 11, 7, 7, 12, 7, 6, 15, 13, 11, 9, 7, 15, 11, 8, 6, 6, 14, 12, 13, 5, 14, 13, 13, 7, 5, 15, 5, 8, 11, 14, 14, 6, 14, 6, 9, 12, 9, 12, 5, 15, 8, 8, 5, 12, 9, 12, 5, 14, 6, 8, 13, 6, 5, 15, 13, 11, 11 };
/*  78:    */   
/*  79:    */   protected void processBlock(byte[] paramArrayOfByte)
/*  80:    */   {
/*  81:    */     int i6;
/*  82:    */     int i1;
/*  83:186 */     int i = i1 = i6 = this.currentVal[0];
/*  84:    */     int i7;
/*  85:    */     int i2;
/*  86:187 */     int j = i2 = i7 = this.currentVal[1];
/*  87:    */     int i8;
/*  88:    */     int i3;
/*  89:188 */     int k = i3 = i8 = this.currentVal[2];
/*  90:    */     int i9;
/*  91:    */     int i4;
/*  92:189 */     int m = i4 = i9 = this.currentVal[3];
/*  93:    */     int i10;
/*  94:    */     int i5;
/*  95:190 */     int n = i5 = i10 = this.currentVal[4];
/*  96:    */     
/*  97:192 */     int i11 = 0;
/*  98:192 */     for (int i12 = 0; i11 < 16; i12 += 4)
/*  99:    */     {
/* 100:193 */       this.X[i11] = decodeLEInt(paramArrayOfByte, i12);i11++;
/* 101:    */     }
/* 102:195 */     for (i11 = 0; i11 < 16; i11++)
/* 103:    */     {
/* 104:196 */       i12 = i1 + (i2 ^ i3 ^ i4) + this.X[i11];
/* 105:    */       
/* 106:198 */       i12 = (i12 << s1[i11] | i12 >>> 32 - s1[i11]) + i5;
/* 107:199 */       i1 = i5;i5 = i4;i4 = i3 << 10 | i3 >>> 22;
/* 108:200 */       i3 = i2;i2 = i12;
/* 109:    */     }
/* 110:202 */     for (i11 = 16; i11 < 32; i11++)
/* 111:    */     {
/* 112:203 */       i12 = i1 + ((i3 ^ i4) & i2 ^ i4) + this.X[r1[i11]] + 1518500249;
/* 113:    */       
/* 114:205 */       i12 = (i12 << s1[i11] | i12 >>> 32 - s1[i11]) + i5;
/* 115:206 */       i1 = i5;i5 = i4;i4 = i3 << 10 | i3 >>> 22;
/* 116:207 */       i3 = i2;i2 = i12;
/* 117:    */     }
/* 118:209 */     for (i11 = 32; i11 < 48; i11++)
/* 119:    */     {
/* 120:210 */       i12 = i1 + ((i2 | i3 ^ 0xFFFFFFFF) ^ i4) + this.X[r1[i11]] + 1859775393;
/* 121:    */       
/* 122:212 */       i12 = (i12 << s1[i11] | i12 >>> 32 - s1[i11]) + i5;
/* 123:213 */       i1 = i5;i5 = i4;i4 = i3 << 10 | i3 >>> 22;
/* 124:214 */       i3 = i2;i2 = i12;
/* 125:    */     }
/* 126:216 */     for (i11 = 48; i11 < 64; i11++)
/* 127:    */     {
/* 128:217 */       i12 = i1 + ((i2 ^ i3) & i4 ^ i3) + this.X[r1[i11]] + -1894007588;
/* 129:    */       
/* 130:219 */       i12 = (i12 << s1[i11] | i12 >>> 32 - s1[i11]) + i5;
/* 131:220 */       i1 = i5;i5 = i4;i4 = i3 << 10 | i3 >>> 22;
/* 132:221 */       i3 = i2;i2 = i12;
/* 133:    */     }
/* 134:223 */     for (i11 = 64; i11 < 80; i11++)
/* 135:    */     {
/* 136:224 */       i12 = i1 + (i2 ^ (i3 | i4 ^ 0xFFFFFFFF)) + this.X[r1[i11]] + -1454113458;
/* 137:    */       
/* 138:226 */       i12 = (i12 << s1[i11] | i12 >>> 32 - s1[i11]) + i5;
/* 139:227 */       i1 = i5;i5 = i4;i4 = i3 << 10 | i3 >>> 22;
/* 140:228 */       i3 = i2;i2 = i12;
/* 141:    */     }
/* 142:231 */     for (i11 = 0; i11 < 16; i11++)
/* 143:    */     {
/* 144:232 */       i12 = i6 + (i7 ^ (i8 | i9 ^ 0xFFFFFFFF)) + this.X[r2[i11]] + 1352829926;
/* 145:    */       
/* 146:234 */       i12 = (i12 << s2[i11] | i12 >>> 32 - s2[i11]) + i10;
/* 147:235 */       i6 = i10;i10 = i9;i9 = i8 << 10 | i8 >>> 22;
/* 148:236 */       i8 = i7;i7 = i12;
/* 149:    */     }
/* 150:238 */     for (i11 = 16; i11 < 32; i11++)
/* 151:    */     {
/* 152:239 */       i12 = i6 + ((i7 ^ i8) & i9 ^ i8) + this.X[r2[i11]] + 1548603684;
/* 153:    */       
/* 154:241 */       i12 = (i12 << s2[i11] | i12 >>> 32 - s2[i11]) + i10;
/* 155:242 */       i6 = i10;i10 = i9;i9 = i8 << 10 | i8 >>> 22;
/* 156:243 */       i8 = i7;i7 = i12;
/* 157:    */     }
/* 158:245 */     for (i11 = 32; i11 < 48; i11++)
/* 159:    */     {
/* 160:246 */       i12 = i6 + ((i7 | i8 ^ 0xFFFFFFFF) ^ i9) + this.X[r2[i11]] + 1836072691;
/* 161:    */       
/* 162:248 */       i12 = (i12 << s2[i11] | i12 >>> 32 - s2[i11]) + i10;
/* 163:249 */       i6 = i10;i10 = i9;i9 = i8 << 10 | i8 >>> 22;
/* 164:250 */       i8 = i7;i7 = i12;
/* 165:    */     }
/* 166:252 */     for (i11 = 48; i11 < 64; i11++)
/* 167:    */     {
/* 168:253 */       i12 = i6 + ((i8 ^ i9) & i7 ^ i9) + this.X[r2[i11]] + 2053994217;
/* 169:    */       
/* 170:255 */       i12 = (i12 << s2[i11] | i12 >>> 32 - s2[i11]) + i10;
/* 171:256 */       i6 = i10;i10 = i9;i9 = i8 << 10 | i8 >>> 22;
/* 172:257 */       i8 = i7;i7 = i12;
/* 173:    */     }
/* 174:259 */     for (i11 = 64; i11 < 80; i11++)
/* 175:    */     {
/* 176:260 */       i12 = i6 + (i7 ^ i8 ^ i9) + this.X[r2[i11]];
/* 177:    */       
/* 178:262 */       i12 = (i12 << s2[i11] | i12 >>> 32 - s2[i11]) + i10;
/* 179:263 */       i6 = i10;i10 = i9;i9 = i8 << 10 | i8 >>> 22;
/* 180:264 */       i8 = i7;i7 = i12;
/* 181:    */     }
/* 182:267 */     i11 = j + i3 + i9;
/* 183:268 */     this.currentVal[1] = (k + i4 + i10);
/* 184:269 */     this.currentVal[2] = (m + i5 + i6);
/* 185:270 */     this.currentVal[3] = (n + i1 + i7);
/* 186:271 */     this.currentVal[4] = (i + i2 + i8);
/* 187:272 */     this.currentVal[0] = i11;
/* 188:    */   }
/* 189:    */   
/* 190:    */   public String toString()
/* 191:    */   {
/* 192:278 */     return "RIPEMD-160";
/* 193:    */   }
/* 194:    */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     fr.cryptohash.RIPEMD160
 * JD-Core Version:    0.7.1
 */