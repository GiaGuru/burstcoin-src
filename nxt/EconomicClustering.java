/*  1:   */ package nxt;
/*  2:   */ 
/*  3:   */ public final class EconomicClustering
/*  4:   */ {
/*  5:19 */   private static final Blockchain blockchain = ;
/*  6:   */   
/*  7:   */   public static Block getECBlock(int paramInt)
/*  8:   */   {
/*  9:22 */     Block localBlock = blockchain.getLastBlock();
/* 10:23 */     if (paramInt < localBlock.getTimestamp() - 15) {
/* 11:24 */       throw new IllegalArgumentException("Timestamp cannot be more than 15 s earlier than last block timestamp: " + localBlock.getTimestamp());
/* 12:   */     }
/* 13:26 */     int i = 0;
/* 14:27 */     while ((localBlock.getTimestamp() > paramInt - 2400) && (i < 60))
/* 15:   */     {
/* 16:28 */       localBlock = blockchain.getBlock(localBlock.getPreviousBlockId());
/* 17:29 */       i++;
/* 18:   */     }
/* 19:31 */     return localBlock;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public static boolean verifyFork(Transaction paramTransaction)
/* 23:   */   {
/* 24:35 */     if (blockchain.getHeight() < 11800) {
/* 25:36 */       return true;
/* 26:   */     }
/* 27:38 */     if (paramTransaction.getReferencedTransactionFullHash() != null) {
/* 28:39 */       return true;
/* 29:   */     }
/* 30:41 */     if ((blockchain.getHeight() < 67000) && 
/* 31:42 */       (blockchain.getHeight() - paramTransaction.getECBlockHeight() > 60)) {
/* 32:43 */       return false;
/* 33:   */     }
/* 34:46 */     Block localBlock = blockchain.getBlock(paramTransaction.getECBlockId());
/* 35:47 */     return (localBlock != null) && (localBlock.getHeight() == paramTransaction.getECBlockHeight());
/* 36:   */   }
/* 37:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.EconomicClustering
 * JD-Core Version:    0.7.1
 */