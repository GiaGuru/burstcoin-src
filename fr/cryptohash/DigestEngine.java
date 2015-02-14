/*   1:    */ package fr.cryptohash;
/*   2:    */ 
/*   3:    */ public abstract class DigestEngine
/*   4:    */   implements Digest
/*   5:    */ {
/*   6:    */   private int digestLen;
/*   7:    */   private int blockLen;
/*   8:    */   private int inputLen;
/*   9:    */   private byte[] inputBuf;
/*  10:    */   private byte[] outputBuf;
/*  11:    */   private long blockCount;
/*  12:    */   
/*  13:    */   protected abstract void engineReset();
/*  14:    */   
/*  15:    */   protected abstract void processBlock(byte[] paramArrayOfByte);
/*  16:    */   
/*  17:    */   protected abstract void doPadding(byte[] paramArrayOfByte, int paramInt);
/*  18:    */   
/*  19:    */   protected abstract void doInit();
/*  20:    */   
/*  21:    */   public DigestEngine()
/*  22:    */   {
/*  23: 93 */     doInit();
/*  24: 94 */     this.digestLen = getDigestLength();
/*  25: 95 */     this.blockLen = getInternalBlockLength();
/*  26: 96 */     this.inputBuf = new byte[this.blockLen];
/*  27: 97 */     this.outputBuf = new byte[this.digestLen];
/*  28: 98 */     this.inputLen = 0;
/*  29: 99 */     this.blockCount = 0L;
/*  30:    */   }
/*  31:    */   
/*  32:    */   private void adjustDigestLen()
/*  33:    */   {
/*  34:104 */     if (this.digestLen == 0)
/*  35:    */     {
/*  36:105 */       this.digestLen = getDigestLength();
/*  37:106 */       this.outputBuf = new byte[this.digestLen];
/*  38:    */     }
/*  39:    */   }
/*  40:    */   
/*  41:    */   public byte[] digest()
/*  42:    */   {
/*  43:113 */     adjustDigestLen();
/*  44:114 */     byte[] arrayOfByte = new byte[this.digestLen];
/*  45:115 */     digest(arrayOfByte, 0, this.digestLen);
/*  46:116 */     return arrayOfByte;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public byte[] digest(byte[] paramArrayOfByte)
/*  50:    */   {
/*  51:122 */     update(paramArrayOfByte, 0, paramArrayOfByte.length);
/*  52:123 */     return digest();
/*  53:    */   }
/*  54:    */   
/*  55:    */   public int digest(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*  56:    */   {
/*  57:129 */     adjustDigestLen();
/*  58:130 */     if (paramInt2 >= this.digestLen)
/*  59:    */     {
/*  60:131 */       doPadding(paramArrayOfByte, paramInt1);
/*  61:132 */       reset();
/*  62:133 */       return this.digestLen;
/*  63:    */     }
/*  64:135 */     doPadding(this.outputBuf, 0);
/*  65:136 */     System.arraycopy(this.outputBuf, 0, paramArrayOfByte, paramInt1, paramInt2);
/*  66:137 */     reset();
/*  67:138 */     return paramInt2;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public void reset()
/*  71:    */   {
/*  72:145 */     engineReset();
/*  73:146 */     this.inputLen = 0;
/*  74:147 */     this.blockCount = 0L;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public void update(byte paramByte)
/*  78:    */   {
/*  79:153 */     this.inputBuf[(this.inputLen++)] = paramByte;
/*  80:154 */     if (this.inputLen == this.blockLen)
/*  81:    */     {
/*  82:155 */       processBlock(this.inputBuf);
/*  83:156 */       this.blockCount += 1L;
/*  84:157 */       this.inputLen = 0;
/*  85:    */     }
/*  86:    */   }
/*  87:    */   
/*  88:    */   public void update(byte[] paramArrayOfByte)
/*  89:    */   {
/*  90:164 */     update(paramArrayOfByte, 0, paramArrayOfByte.length);
/*  91:    */   }
/*  92:    */   
/*  93:    */   public void update(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*  94:    */   {
/*  95:170 */     while (paramInt2 > 0)
/*  96:    */     {
/*  97:171 */       int i = this.blockLen - this.inputLen;
/*  98:172 */       if (i > paramInt2) {
/*  99:173 */         i = paramInt2;
/* 100:    */       }
/* 101:174 */       System.arraycopy(paramArrayOfByte, paramInt1, this.inputBuf, this.inputLen, i);
/* 102:    */       
/* 103:176 */       paramInt1 += i;
/* 104:177 */       this.inputLen += i;
/* 105:178 */       paramInt2 -= i;
/* 106:179 */       if (this.inputLen == this.blockLen)
/* 107:    */       {
/* 108:180 */         processBlock(this.inputBuf);
/* 109:181 */         this.blockCount += 1L;
/* 110:182 */         this.inputLen = 0;
/* 111:    */       }
/* 112:    */     }
/* 113:    */   }
/* 114:    */   
/* 115:    */   protected int getInternalBlockLength()
/* 116:    */   {
/* 117:200 */     return getBlockLength();
/* 118:    */   }
/* 119:    */   
/* 120:    */   protected final int flush()
/* 121:    */   {
/* 122:211 */     return this.inputLen;
/* 123:    */   }
/* 124:    */   
/* 125:    */   protected final byte[] getBlockBuffer()
/* 126:    */   {
/* 127:228 */     return this.inputBuf;
/* 128:    */   }
/* 129:    */   
/* 130:    */   protected long getBlockCount()
/* 131:    */   {
/* 132:241 */     return this.blockCount;
/* 133:    */   }
/* 134:    */   
/* 135:    */   protected Digest copyState(DigestEngine paramDigestEngine)
/* 136:    */   {
/* 137:256 */     paramDigestEngine.inputLen = this.inputLen;
/* 138:257 */     paramDigestEngine.blockCount = this.blockCount;
/* 139:258 */     System.arraycopy(this.inputBuf, 0, paramDigestEngine.inputBuf, 0, this.inputBuf.length);
/* 140:    */     
/* 141:260 */     adjustDigestLen();
/* 142:261 */     paramDigestEngine.adjustDigestLen();
/* 143:262 */     System.arraycopy(this.outputBuf, 0, paramDigestEngine.outputBuf, 0, this.outputBuf.length);
/* 144:    */     
/* 145:264 */     return paramDigestEngine;
/* 146:    */   }
/* 147:    */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     fr.cryptohash.DigestEngine
 * JD-Core Version:    0.7.1
 */