/*  1:   */ package fr.cryptohash;
/*  2:   */ 
/*  3:   */ public class Keccak256
/*  4:   */   extends KeccakCore
/*  5:   */ {
/*  6:   */   public Digest copy()
/*  7:   */   {
/*  8:52 */     return copyState(new Keccak256());
/*  9:   */   }
/* 10:   */   
/* 11:   */   public int getDigestLength()
/* 12:   */   {
/* 13:58 */     return 32;
/* 14:   */   }
/* 15:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     fr.cryptohash.Keccak256
 * JD-Core Version:    0.7.1
 */