/*  1:   */ package fr.cryptohash;
/*  2:   */ 
/*  3:   */ public class Keccak512
/*  4:   */   extends KeccakCore
/*  5:   */ {
/*  6:   */   public Digest copy()
/*  7:   */   {
/*  8:52 */     return copyState(new Keccak512());
/*  9:   */   }
/* 10:   */   
/* 11:   */   public int getDigestLength()
/* 12:   */   {
/* 13:58 */     return 64;
/* 14:   */   }
/* 15:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     fr.cryptohash.Keccak512
 * JD-Core Version:    0.7.1
 */