/*  1:   */ package fr.cryptohash;
/*  2:   */ 
/*  3:   */ public class Hamsi512
/*  4:   */   extends HamsiBigCore
/*  5:   */ {
/*  6:   */   public int getDigestLength()
/*  7:   */   {
/*  8:53 */     return 64;
/*  9:   */   }
/* 10:   */   
/* 11:56 */   private static final int[] IV = { 1937007973, 1819304306, 1797276018, 1701732965, 1919361073, 808198242, 1970479154, 875836972, 541207859, 808464672, 1281717622, 1701719368, 1702258034, 1818584364, 541222252, 1734964589 };
/* 12:   */   
/* 13:   */   int[] getIV()
/* 14:   */   {
/* 15:66 */     return IV;
/* 16:   */   }
/* 17:   */   
/* 18:   */   HamsiBigCore dup()
/* 19:   */   {
/* 20:72 */     return new Hamsi512();
/* 21:   */   }
/* 22:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     fr.cryptohash.Hamsi512
 * JD-Core Version:    0.7.1
 */