/*  1:   */ package fr.cryptohash;
/*  2:   */ 
/*  3:   */ public class SHAvite384
/*  4:   */   extends SHAviteBigCore
/*  5:   */ {
/*  6:52 */   private static final int[] initVal = { -2082532027, -106238957, -192922448, 301866823, -630402455, 1330904279, -1794823774, -1752137401, -1331374161, 730935999, 577659005, 1007652220, 1216036623, 70663714, 22399388, -332521261 };
/*  7:   */   
/*  8:   */   int[] getInitVal()
/*  9:   */   {
/* 10:62 */     return initVal;
/* 11:   */   }
/* 12:   */   
/* 13:   */   public int getDigestLength()
/* 14:   */   {
/* 15:68 */     return 48;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public Digest copy()
/* 19:   */   {
/* 20:74 */     return copyState(new SHAvite384());
/* 21:   */   }
/* 22:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     fr.cryptohash.SHAvite384
 * JD-Core Version:    0.7.1
 */