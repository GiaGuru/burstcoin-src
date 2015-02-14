/*  1:   */ package fr.cryptohash;
/*  2:   */ 
/*  3:   */ public class Luffa256
/*  4:   */   extends LuffaSmallCore
/*  5:   */ {
/*  6:   */   public int getDigestLength()
/*  7:   */   {
/*  8:53 */     return 32;
/*  9:   */   }
/* 10:   */   
/* 11:   */   public Digest copy()
/* 12:   */   {
/* 13:59 */     return copyState(new Luffa256());
/* 14:   */   }
/* 15:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     fr.cryptohash.Luffa256
 * JD-Core Version:    0.7.1
 */