/*  1:   */ package fr.cryptohash;
/*  2:   */ 
/*  3:   */ public class SIMD224
/*  4:   */   extends SIMDSmallCore
/*  5:   */ {
/*  6:51 */   private static final int[] initVal = { 861433503, 318763059, -1294338483, 1871702611, -560713466, 658695225, 1337636268, 1656356758, 585609391, -933055576, 870321372, 594249382, -162863236, -97043594, 2109861467, 2142412008 };
/*  7:   */   
/*  8:   */   int[] getInitVal()
/*  9:   */   {
/* 10:61 */     return initVal;
/* 11:   */   }
/* 12:   */   
/* 13:   */   public int getDigestLength()
/* 14:   */   {
/* 15:67 */     return 28;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public Digest copy()
/* 19:   */   {
/* 20:73 */     return copyState(new SIMD224());
/* 21:   */   }
/* 22:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     fr.cryptohash.SIMD224
 * JD-Core Version:    0.7.1
 */