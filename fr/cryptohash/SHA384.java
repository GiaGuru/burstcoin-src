/*  1:   */ package fr.cryptohash;
/*  2:   */ 
/*  3:   */ public class SHA384
/*  4:   */   extends SHA2BigCore
/*  5:   */ {
/*  6:51 */   private static final long[] initVal = { -3766243637369397544L, 7105036623409894663L, -7973340178411365097L, 1526699215303891257L, 7436329637833083697L, -8163818279084223215L, -2662702644619276377L, 5167115440072839076L };
/*  7:   */   
/*  8:   */   long[] getInitVal()
/*  9:   */   {
/* 10:61 */     return initVal;
/* 11:   */   }
/* 12:   */   
/* 13:   */   public int getDigestLength()
/* 14:   */   {
/* 15:67 */     return 48;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public Digest copy()
/* 19:   */   {
/* 20:73 */     return copyState(new SHA384());
/* 21:   */   }
/* 22:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     fr.cryptohash.SHA384
 * JD-Core Version:    0.7.1
 */