/*  1:   */ package fr.cryptohash;
/*  2:   */ 
/*  3:   */ public class SHAvite224
/*  4:   */   extends SHAviteSmallCore
/*  5:   */ {
/*  6:52 */   private static final int[] initVal = { 1735717660, -1727340016, -931315084, -917216399, 1655877288, 1264058840, 460335200, -2077282281 };
/*  7:   */   
/*  8:   */   int[] getInitVal()
/*  9:   */   {
/* 10:60 */     return initVal;
/* 11:   */   }
/* 12:   */   
/* 13:   */   public int getDigestLength()
/* 14:   */   {
/* 15:66 */     return 28;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public Digest copy()
/* 19:   */   {
/* 20:72 */     return copyState(new SHAvite224());
/* 21:   */   }
/* 22:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     fr.cryptohash.SHAvite224
 * JD-Core Version:    0.7.1
 */