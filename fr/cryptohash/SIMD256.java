/*  1:   */ package fr.cryptohash;
/*  2:   */ 
/*  3:   */ public class SIMD256
/*  4:   */   extends SIMDSmallCore
/*  5:   */ {
/*  6:51 */   private static final int[] initVal = { 1297512835, 119081897, -2072750213, 970401513, -1426859739, 1055001347, -1344936111, -916453677, -1027425772, 1236516020, -159600826, 1720067785, -487937838, 536115251, -792305243, 1432960481 };
/*  7:   */   
/*  8:   */   int[] getInitVal()
/*  9:   */   {
/* 10:61 */     return initVal;
/* 11:   */   }
/* 12:   */   
/* 13:   */   public int getDigestLength()
/* 14:   */   {
/* 15:67 */     return 32;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public Digest copy()
/* 19:   */   {
/* 20:73 */     return copyState(new SIMD256());
/* 21:   */   }
/* 22:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     fr.cryptohash.SIMD256
 * JD-Core Version:    0.7.1
 */