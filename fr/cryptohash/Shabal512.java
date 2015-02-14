/*  1:   */ package fr.cryptohash;
/*  2:   */ 
/*  3:   */ public class Shabal512
/*  4:   */   extends ShabalGeneric
/*  5:   */ {
/*  6:   */   public Shabal512()
/*  7:   */   {
/*  8:47 */     super(512);
/*  9:   */   }
/* 10:   */   
/* 11:   */   ShabalGeneric dup()
/* 12:   */   {
/* 13:53 */     return new Shabal512();
/* 14:   */   }
/* 15:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     fr.cryptohash.Shabal512
 * JD-Core Version:    0.7.1
 */