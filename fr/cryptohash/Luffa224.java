/*  1:   */ package fr.cryptohash;
/*  2:   */ 
/*  3:   */ public class Luffa224
/*  4:   */   extends LuffaSmallCore
/*  5:   */ {
/*  6:   */   public int getDigestLength()
/*  7:   */   {
/*  8:53 */     return 28;
/*  9:   */   }
/* 10:   */   
/* 11:   */   public Digest copy()
/* 12:   */   {
/* 13:59 */     return copyState(new Luffa224());
/* 14:   */   }
/* 15:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     fr.cryptohash.Luffa224
 * JD-Core Version:    0.7.1
 */