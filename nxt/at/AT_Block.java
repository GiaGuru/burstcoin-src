/*  1:   */ package nxt.at;
/*  2:   */ 
/*  3:   */ public class AT_Block
/*  4:   */ {
/*  5:   */   long totalFees;
/*  6:   */   long totalAmount;
/*  7:   */   byte[] bytesForBlock;
/*  8:   */   boolean validated;
/*  9:   */   
/* 10:   */   AT_Block(long paramLong1, long paramLong2, byte[] paramArrayOfByte)
/* 11:   */   {
/* 12:23 */     this.totalFees = paramLong1;
/* 13:24 */     this.totalAmount = paramLong2;
/* 14:25 */     this.bytesForBlock = paramArrayOfByte;
/* 15:26 */     this.validated = true;
/* 16:   */   }
/* 17:   */   
/* 18:   */   AT_Block(long paramLong1, long paramLong2, byte[] paramArrayOfByte, boolean paramBoolean)
/* 19:   */   {
/* 20:30 */     this.totalFees = paramLong1;
/* 21:31 */     this.totalAmount = paramLong2;
/* 22:32 */     this.bytesForBlock = paramArrayOfByte;
/* 23:33 */     this.validated = paramBoolean;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public long getTotalFees()
/* 27:   */   {
/* 28:38 */     return this.totalFees;
/* 29:   */   }
/* 30:   */   
/* 31:   */   public long getTotalAmount()
/* 32:   */   {
/* 33:42 */     return this.totalAmount;
/* 34:   */   }
/* 35:   */   
/* 36:   */   public byte[] getBytesForBlock()
/* 37:   */   {
/* 38:46 */     return this.bytesForBlock;
/* 39:   */   }
/* 40:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.at.AT_Block
 * JD-Core Version:    0.7.1
 */