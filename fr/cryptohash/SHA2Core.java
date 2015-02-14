/*   1:    */ package fr.cryptohash;
/*   2:    */ 
/*   3:    */ abstract class SHA2Core
/*   4:    */   extends MDHelper
/*   5:    */ {
/*   6:    */   SHA2Core()
/*   7:    */   {
/*   8: 47 */     super(false, 8);
/*   9:    */   }
/*  10:    */   
/*  11: 51 */   private static final int[] K = { 1116352408, 1899447441, -1245643825, -373957723, 961987163, 1508970993, -1841331548, -1424204075, -670586216, 310598401, 607225278, 1426881987, 1925078388, -2132889090, -1680079193, -1046744716, -459576895, -272742522, 264347078, 604807628, 770255983, 1249150122, 1555081692, 1996064986, -1740746414, -1473132947, -1341970488, -1084653625, -958395405, -710438585, 113926993, 338241895, 666307205, 773529912, 1294757372, 1396182291, 1695183700, 1986661051, -2117940946, -1838011259, -1564481375, -1474664885, -1035236496, -949202525, -778901479, -694614492, -200395387, 275423344, 430227734, 506948616, 659060556, 883997877, 958139571, 1322822218, 1537002063, 1747873779, 1955562222, 2024104815, -2067236844, -1933114872, -1866530822, -1538233109, -1090935817, -965641998 };
/*  12:    */   private int[] currentVal;
/*  13:    */   private int[] W;
/*  14:    */   
/*  15:    */   protected Digest copyState(SHA2Core paramSHA2Core)
/*  16:    */   {
/*  17: 75 */     System.arraycopy(this.currentVal, 0, paramSHA2Core.currentVal, 0, this.currentVal.length);
/*  18:    */     
/*  19: 77 */     return super.copyState(paramSHA2Core);
/*  20:    */   }
/*  21:    */   
/*  22:    */   public int getBlockLength()
/*  23:    */   {
/*  24: 83 */     return 64;
/*  25:    */   }
/*  26:    */   
/*  27:    */   protected void engineReset()
/*  28:    */   {
/*  29: 89 */     System.arraycopy(getInitVal(), 0, this.currentVal, 0, 8);
/*  30:    */   }
/*  31:    */   
/*  32:    */   abstract int[] getInitVal();
/*  33:    */   
/*  34:    */   protected void doPadding(byte[] paramArrayOfByte, int paramInt)
/*  35:    */   {
/*  36:102 */     makeMDPadding();
/*  37:103 */     int i = getDigestLength();
/*  38:104 */     int j = 0;
/*  39:104 */     for (int k = 0; k < i; k += 4)
/*  40:    */     {
/*  41:105 */       encodeBEInt(this.currentVal[j], paramArrayOfByte, paramInt + k);j++;
/*  42:    */     }
/*  43:    */   }
/*  44:    */   
/*  45:    */   protected void doInit()
/*  46:    */   {
/*  47:111 */     this.currentVal = new int[8];
/*  48:112 */     this.W = new int[64];
/*  49:113 */     engineReset();
/*  50:    */   }
/*  51:    */   
/*  52:    */   private static final void encodeBEInt(int paramInt1, byte[] paramArrayOfByte, int paramInt2)
/*  53:    */   {
/*  54:127 */     paramArrayOfByte[(paramInt2 + 0)] = ((byte)(paramInt1 >>> 24));
/*  55:128 */     paramArrayOfByte[(paramInt2 + 1)] = ((byte)(paramInt1 >>> 16));
/*  56:129 */     paramArrayOfByte[(paramInt2 + 2)] = ((byte)(paramInt1 >>> 8));
/*  57:130 */     paramArrayOfByte[(paramInt2 + 3)] = ((byte)paramInt1);
/*  58:    */   }
/*  59:    */   
/*  60:    */   private static final int decodeBEInt(byte[] paramArrayOfByte, int paramInt)
/*  61:    */   {
/*  62:143 */     return (paramArrayOfByte[paramInt] & 0xFF) << 24 | (paramArrayOfByte[(paramInt + 1)] & 0xFF) << 16 | (paramArrayOfByte[(paramInt + 2)] & 0xFF) << 8 | paramArrayOfByte[(paramInt + 3)] & 0xFF;
/*  63:    */   }
/*  64:    */   
/*  65:    */   private static int circularLeft(int paramInt1, int paramInt2)
/*  66:    */   {
/*  67:160 */     return paramInt1 << paramInt2 | paramInt1 >>> 32 - paramInt2;
/*  68:    */   }
/*  69:    */   
/*  70:    */   protected void processBlock(byte[] paramArrayOfByte)
/*  71:    */   {
/*  72:166 */     int i = this.currentVal[0];
/*  73:167 */     int j = this.currentVal[1];
/*  74:168 */     int k = this.currentVal[2];
/*  75:169 */     int m = this.currentVal[3];
/*  76:170 */     int n = this.currentVal[4];
/*  77:171 */     int i1 = this.currentVal[5];
/*  78:172 */     int i2 = this.currentVal[6];
/*  79:173 */     int i3 = this.currentVal[7];
/*  80:175 */     for (int i4 = 0; i4 < 16; i4++) {
/*  81:176 */       this.W[i4] = decodeBEInt(paramArrayOfByte, 4 * i4);
/*  82:    */     }
/*  83:177 */     for (i4 = 16; i4 < 64; i4++) {
/*  84:178 */       this.W[i4] = ((circularLeft(this.W[(i4 - 2)], 15) ^ circularLeft(this.W[(i4 - 2)], 13) ^ this.W[(i4 - 2)] >>> 10) + this.W[(i4 - 7)] + (circularLeft(this.W[(i4 - 15)], 25) ^ circularLeft(this.W[(i4 - 15)], 14) ^ this.W[(i4 - 15)] >>> 3) + this.W[(i4 - 16)]);
/*  85:    */     }
/*  86:187 */     for (i4 = 0; i4 < 64; i4++)
/*  87:    */     {
/*  88:188 */       int i5 = i3 + (circularLeft(n, 26) ^ circularLeft(n, 21) ^ circularLeft(n, 7)) + (i1 & n ^ i2 & (n ^ 0xFFFFFFFF)) + K[i4] + this.W[i4];
/*  89:    */       
/*  90:    */ 
/*  91:191 */       int i6 = (circularLeft(i, 30) ^ circularLeft(i, 19) ^ circularLeft(i, 10)) + (i & j ^ i & k ^ j & k);
/*  92:    */       
/*  93:    */ 
/*  94:194 */       i3 = i2;i2 = i1;i1 = n;n = m + i5;
/*  95:195 */       m = k;k = j;j = i;i = i5 + i6;
/*  96:    */     }
/*  97:197 */     this.currentVal[0] += i;
/*  98:198 */     this.currentVal[1] += j;
/*  99:199 */     this.currentVal[2] += k;
/* 100:200 */     this.currentVal[3] += m;
/* 101:201 */     this.currentVal[4] += n;
/* 102:202 */     this.currentVal[5] += i1;
/* 103:203 */     this.currentVal[6] += i2;
/* 104:204 */     this.currentVal[7] += i3;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public String toString()
/* 108:    */   {
/* 109:629 */     return "SHA-" + (getDigestLength() << 3);
/* 110:    */   }
/* 111:    */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     fr.cryptohash.SHA2Core
 * JD-Core Version:    0.7.1
 */