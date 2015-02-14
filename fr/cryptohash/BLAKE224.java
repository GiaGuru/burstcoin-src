/*  1:   */ package fr.cryptohash;
/*  2:   */ 
/*  3:   */ public class BLAKE224
/*  4:   */   extends BLAKESmallCore
/*  5:   */ {
/*  6:51 */   private static final int[] initVal = { -1056596264, 914150663, 812702999, -150054599, -4191439, 1750603025, 1694076839, -1090891868 };
/*  7:   */   
/*  8:   */   int[] getInitVal()
/*  9:   */   {
/* 10:59 */     return initVal;
/* 11:   */   }
/* 12:   */   
/* 13:   */   public int getDigestLength()
/* 14:   */   {
/* 15:65 */     return 28;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public Digest copy()
/* 19:   */   {
/* 20:71 */     return copyState(new BLAKE224());
/* 21:   */   }
/* 22:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     fr.cryptohash.BLAKE224
 * JD-Core Version:    0.7.1
 */