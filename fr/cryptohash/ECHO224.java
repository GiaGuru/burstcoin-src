/*  1:   */ package fr.cryptohash;
/*  2:   */ 
/*  3:   */ public class ECHO224
/*  4:   */   extends ECHOSmallCore
/*  5:   */ {
/*  6:   */   public int getDigestLength()
/*  7:   */   {
/*  8:53 */     return 28;
/*  9:   */   }
/* 10:   */   
/* 11:   */   public Digest copy()
/* 12:   */   {
/* 13:59 */     return copyState(new ECHO224());
/* 14:   */   }
/* 15:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     fr.cryptohash.ECHO224
 * JD-Core Version:    0.7.1
 */