/*  1:   */ package fr.cryptohash;
/*  2:   */ 
/*  3:   */ public class Hamsi256
/*  4:   */   extends HamsiSmallCore
/*  5:   */ {
/*  6:   */   public int getDigestLength()
/*  7:   */   {
/*  8:53 */     return 32;
/*  9:   */   }
/* 10:   */   
/* 11:56 */   private static final int[] IV = { 1986359923, 1769235817, 1948273765, 1970693486, 740312165, 1885434484, 1701668206, 1948271980 };
/* 12:   */   
/* 13:   */   int[] getIV()
/* 14:   */   {
/* 15:64 */     return IV;
/* 16:   */   }
/* 17:   */   
/* 18:   */   HamsiSmallCore dup()
/* 19:   */   {
/* 20:70 */     return new Hamsi256();
/* 21:   */   }
/* 22:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     fr.cryptohash.Hamsi256
 * JD-Core Version:    0.7.1
 */