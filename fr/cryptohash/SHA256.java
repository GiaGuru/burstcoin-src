/*  1:   */ package fr.cryptohash;
/*  2:   */ 
/*  3:   */ public class SHA256
/*  4:   */   extends SHA2Core
/*  5:   */ {
/*  6:51 */   private static final int[] initVal = { 1779033703, -1150833019, 1013904242, -1521486534, 1359893119, -1694144372, 528734635, 1541459225 };
/*  7:   */   
/*  8:   */   int[] getInitVal()
/*  9:   */   {
/* 10:59 */     return initVal;
/* 11:   */   }
/* 12:   */   
/* 13:   */   public int getDigestLength()
/* 14:   */   {
/* 15:65 */     return 32;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public Digest copy()
/* 19:   */   {
/* 20:71 */     return copyState(new SHA256());
/* 21:   */   }
/* 22:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     fr.cryptohash.SHA256
 * JD-Core Version:    0.7.1
 */