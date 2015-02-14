/*  1:   */ package fr.cryptohash;
/*  2:   */ 
/*  3:   */ public class SHAvite512
/*  4:   */   extends SHAviteBigCore
/*  5:   */ {
/*  6:52 */   private static final int[] initVal = { 1929170392, 2043299623, 311035771, 1087724268, -779085306, 1124786951, -1298178863, -553124868, -1908025539, 1746580792, -1108843144, -581468601, -495588642, 1345167309, -1187679880, 36326298 };
/*  7:   */   
/*  8:   */   int[] getInitVal()
/*  9:   */   {
/* 10:62 */     return initVal;
/* 11:   */   }
/* 12:   */   
/* 13:   */   public int getDigestLength()
/* 14:   */   {
/* 15:68 */     return 64;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public Digest copy()
/* 19:   */   {
/* 20:74 */     return copyState(new SHAvite512());
/* 21:   */   }
/* 22:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     fr.cryptohash.SHAvite512
 * JD-Core Version:    0.7.1
 */