/*  1:   */ package fr.cryptohash;
/*  2:   */ 
/*  3:   */ public class Hamsi224
/*  4:   */   extends HamsiSmallCore
/*  5:   */ {
/*  6:   */   public int getDigestLength()
/*  7:   */   {
/*  8:53 */     return 28;
/*  9:   */   }
/* 10:   */   
/* 11:56 */   private static final int[] IV = { -1013548441, -1011061728, 1271119043, -1480344469, 740313953, 1953001324, 1768254309, 542469737 };
/* 12:   */   
/* 13:   */   int[] getIV()
/* 14:   */   {
/* 15:73 */     return IV;
/* 16:   */   }
/* 17:   */   
/* 18:   */   HamsiSmallCore dup()
/* 19:   */   {
/* 20:79 */     return new Hamsi224();
/* 21:   */   }
/* 22:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     fr.cryptohash.Hamsi224
 * JD-Core Version:    0.7.1
 */