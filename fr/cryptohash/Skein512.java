/*  1:   */ package fr.cryptohash;
/*  2:   */ 
/*  3:   */ public class Skein512
/*  4:   */   extends SkeinBigCore
/*  5:   */ {
/*  6:52 */   private static final long[] initVal = { 5261240102383538638L, 978932832955457283L, -8083517948103779378L, -7339365279355032399L, 6752626034097301424L, -1531723821829733388L, -7417126464950782685L, -5901786942805128141L };
/*  7:   */   
/*  8:   */   long[] getInitVal()
/*  9:   */   {
/* 10:62 */     return initVal;
/* 11:   */   }
/* 12:   */   
/* 13:   */   public int getDigestLength()
/* 14:   */   {
/* 15:68 */     return 64;
/* 16:   */   }
/* 17:   */   
/* 18:   */   SkeinBigCore dup()
/* 19:   */   {
/* 20:74 */     return new Skein512();
/* 21:   */   }
/* 22:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     fr.cryptohash.Skein512
 * JD-Core Version:    0.7.1
 */