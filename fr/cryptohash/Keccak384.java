/*  1:   */ package fr.cryptohash;
/*  2:   */ 
/*  3:   */ public class Keccak384
/*  4:   */   extends KeccakCore
/*  5:   */ {
/*  6:   */   public Digest copy()
/*  7:   */   {
/*  8:52 */     return copyState(new Keccak384());
/*  9:   */   }
/* 10:   */   
/* 11:   */   public int getDigestLength()
/* 12:   */   {
/* 13:58 */     return 48;
/* 14:   */   }
/* 15:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     fr.cryptohash.Keccak384
 * JD-Core Version:    0.7.1
 */