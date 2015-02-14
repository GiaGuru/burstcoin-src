/*   1:    */ package fr.cryptohash;
/*   2:    */ 
/*   3:    */ abstract class MDHelper
/*   4:    */   extends DigestEngine
/*   5:    */ {
/*   6:    */   private boolean littleEndian;
/*   7:    */   private byte[] countBuf;
/*   8:    */   private byte fbyte;
/*   9:    */   
/*  10:    */   MDHelper(boolean paramBoolean, int paramInt)
/*  11:    */   {
/*  12: 57 */     this(paramBoolean, paramInt, (byte)Byte.MIN_VALUE);
/*  13:    */   }
/*  14:    */   
/*  15:    */   MDHelper(boolean paramBoolean, int paramInt, byte paramByte)
/*  16:    */   {
/*  17: 77 */     this.littleEndian = paramBoolean;
/*  18: 78 */     this.countBuf = new byte[paramInt];
/*  19: 79 */     this.fbyte = paramByte;
/*  20:    */   }
/*  21:    */   
/*  22:    */   protected void makeMDPadding()
/*  23:    */   {
/*  24: 92 */     int i = flush();
/*  25: 93 */     int j = getBlockLength();
/*  26: 94 */     long l = getBlockCount() * j;
/*  27: 95 */     l = (l + i) * 8L;
/*  28: 96 */     int k = this.countBuf.length;
/*  29: 97 */     if (this.littleEndian)
/*  30:    */     {
/*  31: 98 */       encodeLEInt((int)l, this.countBuf, 0);
/*  32: 99 */       encodeLEInt((int)(l >>> 32), this.countBuf, 4);
/*  33:    */     }
/*  34:    */     else
/*  35:    */     {
/*  36:101 */       encodeBEInt((int)(l >>> 32), this.countBuf, k - 8);
/*  37:    */       
/*  38:103 */       encodeBEInt((int)l, this.countBuf, k - 4);
/*  39:    */     }
/*  40:106 */     int m = i + k + j & (j - 1 ^ 0xFFFFFFFF);
/*  41:107 */     update(this.fbyte);
/*  42:108 */     for (int n = i + 1; n < m - k; n++) {
/*  43:109 */       update((byte)0);
/*  44:    */     }
/*  45:110 */     update(this.countBuf);
/*  46:    */   }
/*  47:    */   
/*  48:    */   private static final void encodeLEInt(int paramInt1, byte[] paramArrayOfByte, int paramInt2)
/*  49:    */   {
/*  50:132 */     paramArrayOfByte[(paramInt2 + 0)] = ((byte)paramInt1);
/*  51:133 */     paramArrayOfByte[(paramInt2 + 1)] = ((byte)(paramInt1 >>> 8));
/*  52:134 */     paramArrayOfByte[(paramInt2 + 2)] = ((byte)(paramInt1 >>> 16));
/*  53:135 */     paramArrayOfByte[(paramInt2 + 3)] = ((byte)(paramInt1 >>> 24));
/*  54:    */   }
/*  55:    */   
/*  56:    */   private static final void encodeBEInt(int paramInt1, byte[] paramArrayOfByte, int paramInt2)
/*  57:    */   {
/*  58:149 */     paramArrayOfByte[(paramInt2 + 0)] = ((byte)(paramInt1 >>> 24));
/*  59:150 */     paramArrayOfByte[(paramInt2 + 1)] = ((byte)(paramInt1 >>> 16));
/*  60:151 */     paramArrayOfByte[(paramInt2 + 2)] = ((byte)(paramInt1 >>> 8));
/*  61:152 */     paramArrayOfByte[(paramInt2 + 3)] = ((byte)paramInt1);
/*  62:    */   }
/*  63:    */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     fr.cryptohash.MDHelper
 * JD-Core Version:    0.7.1
 */