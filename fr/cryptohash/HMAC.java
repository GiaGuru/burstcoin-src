/*   1:    */ package fr.cryptohash;
/*   2:    */ 
/*   3:    */ public class HMAC
/*   4:    */   extends DigestEngine
/*   5:    */ {
/*   6:    */   private Digest dig;
/*   7:    */   private byte[] kipad;
/*   8:    */   private byte[] kopad;
/*   9:    */   private int outputLength;
/*  10:    */   private byte[] tmpOut;
/*  11:    */   
/*  12:    */   public HMAC(Digest paramDigest, byte[] paramArrayOfByte)
/*  13:    */   {
/*  14: 57 */     paramDigest.reset();
/*  15: 58 */     this.dig = paramDigest;
/*  16: 59 */     int i = paramDigest.getBlockLength();
/*  17: 60 */     if (i < 0)
/*  18:    */     {
/*  19: 65 */       int j = -i;
/*  20: 66 */       i = j * ((paramArrayOfByte.length + (j - 1)) / j);
/*  21:    */     }
/*  22: 68 */     byte[] arrayOfByte = new byte[i];
/*  23: 69 */     int k = paramArrayOfByte.length;
/*  24: 70 */     if (k > i)
/*  25:    */     {
/*  26: 71 */       paramArrayOfByte = paramDigest.digest(paramArrayOfByte);
/*  27: 72 */       k = paramArrayOfByte.length;
/*  28: 73 */       if (k > i) {
/*  29: 74 */         k = i;
/*  30:    */       }
/*  31:    */     }
/*  32: 76 */     System.arraycopy(paramArrayOfByte, 0, arrayOfByte, 0, k);
/*  33:    */     
/*  34:    */ 
/*  35:    */ 
/*  36:    */ 
/*  37: 81 */     processKey(arrayOfByte);
/*  38:    */     
/*  39: 83 */     this.outputLength = -1;
/*  40: 84 */     this.tmpOut = new byte[paramDigest.getDigestLength()];
/*  41: 85 */     reset();
/*  42:    */   }
/*  43:    */   
/*  44:    */   public HMAC(Digest paramDigest, byte[] paramArrayOfByte, int paramInt)
/*  45:    */   {
/*  46:103 */     this(paramDigest, paramArrayOfByte);
/*  47:104 */     if (paramInt < paramDigest.getDigestLength()) {
/*  48:105 */       this.outputLength = paramInt;
/*  49:    */     }
/*  50:    */   }
/*  51:    */   
/*  52:    */   private HMAC(Digest paramDigest, byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, int paramInt)
/*  53:    */   {
/*  54:119 */     this.dig = paramDigest;
/*  55:120 */     this.kipad = paramArrayOfByte1;
/*  56:121 */     this.kopad = paramArrayOfByte2;
/*  57:122 */     this.outputLength = paramInt;
/*  58:123 */     this.tmpOut = new byte[paramDigest.getDigestLength()];
/*  59:    */   }
/*  60:    */   
/*  61:    */   private void processKey(byte[] paramArrayOfByte)
/*  62:    */   {
/*  63:133 */     int i = paramArrayOfByte.length;
/*  64:134 */     this.kipad = new byte[i];
/*  65:135 */     this.kopad = new byte[i];
/*  66:136 */     for (int j = 0; j < i; j++)
/*  67:    */     {
/*  68:137 */       int k = paramArrayOfByte[j];
/*  69:138 */       this.kipad[j] = ((byte)(k ^ 0x36));
/*  70:139 */       this.kopad[j] = ((byte)(k ^ 0x5C));
/*  71:    */     }
/*  72:    */   }
/*  73:    */   
/*  74:    */   public Digest copy()
/*  75:    */   {
/*  76:146 */     HMAC localHMAC = new HMAC(this.dig.copy(), this.kipad, this.kopad, this.outputLength);
/*  77:147 */     return copyState(localHMAC);
/*  78:    */   }
/*  79:    */   
/*  80:    */   public int getDigestLength()
/*  81:    */   {
/*  82:159 */     return this.outputLength < 0 ? this.dig.getDigestLength() : this.outputLength;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public int getBlockLength()
/*  86:    */   {
/*  87:171 */     return 64;
/*  88:    */   }
/*  89:    */   
/*  90:    */   protected void engineReset()
/*  91:    */   {
/*  92:177 */     this.dig.reset();
/*  93:178 */     this.dig.update(this.kipad);
/*  94:    */   }
/*  95:    */   
/*  96:181 */   private int onlyThis = 0;
/*  97:182 */   private static final byte[] zeroPad = new byte[64];
/*  98:    */   
/*  99:    */   protected void processBlock(byte[] paramArrayOfByte)
/* 100:    */   {
/* 101:187 */     if (this.onlyThis > 0)
/* 102:    */     {
/* 103:188 */       this.dig.update(paramArrayOfByte, 0, this.onlyThis);
/* 104:189 */       this.onlyThis = 0;
/* 105:    */     }
/* 106:    */     else
/* 107:    */     {
/* 108:191 */       this.dig.update(paramArrayOfByte);
/* 109:    */     }
/* 110:    */   }
/* 111:    */   
/* 112:    */   protected void doPadding(byte[] paramArrayOfByte, int paramInt)
/* 113:    */   {
/* 114:206 */     this.onlyThis = flush();
/* 115:207 */     if (this.onlyThis > 0) {
/* 116:208 */       update(zeroPad, 0, 64 - this.onlyThis);
/* 117:    */     }
/* 118:210 */     int i = this.tmpOut.length;
/* 119:211 */     this.dig.digest(this.tmpOut, 0, i);
/* 120:212 */     this.dig.update(this.kopad);
/* 121:213 */     this.dig.update(this.tmpOut);
/* 122:214 */     this.dig.digest(this.tmpOut, 0, i);
/* 123:215 */     if (this.outputLength >= 0) {
/* 124:216 */       i = this.outputLength;
/* 125:    */     }
/* 126:217 */     System.arraycopy(this.tmpOut, 0, paramArrayOfByte, paramInt, i);
/* 127:    */   }
/* 128:    */   
/* 129:    */   protected void doInit() {}
/* 130:    */   
/* 131:    */   public String toString()
/* 132:    */   {
/* 133:233 */     return "HMAC/" + this.dig.toString();
/* 134:    */   }
/* 135:    */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     fr.cryptohash.HMAC
 * JD-Core Version:    0.7.1
 */