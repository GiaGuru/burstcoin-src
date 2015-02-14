/*  1:   */ package fr.cryptohash;
/*  2:   */ 
/*  3:   */ public class SHAvite256
/*  4:   */   extends SHAviteSmallCore
/*  5:   */ {
/*  6:52 */   private static final int[] initVal = { 1237007943, 645170701, -1464626516, 35308774, 1083343823, 1645108614, 1838325195, -1765004661 };
/*  7:   */   
/*  8:   */   int[] getInitVal()
/*  9:   */   {
/* 10:60 */     return initVal;
/* 11:   */   }
/* 12:   */   
/* 13:   */   public int getDigestLength()
/* 14:   */   {
/* 15:66 */     return 32;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public Digest copy()
/* 19:   */   {
/* 20:72 */     return copyState(new SHAvite256());
/* 21:   */   }
/* 22:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     fr.cryptohash.SHAvite256
 * JD-Core Version:    0.7.1
 */