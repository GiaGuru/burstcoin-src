/*  1:   */ package fr.cryptohash;
/*  2:   */ 
/*  3:   */ public class Shabal256
/*  4:   */   extends ShabalGeneric
/*  5:   */ {
/*  6:   */   public Shabal256()
/*  7:   */   {
/*  8:47 */     super(256);
/*  9:   */   }
/* 10:   */   
/* 11:   */   ShabalGeneric dup()
/* 12:   */   {
/* 13:53 */     return new Shabal256();
/* 14:   */   }
/* 15:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     fr.cryptohash.Shabal256
 * JD-Core Version:    0.7.1
 */