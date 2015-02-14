/*  1:   */ package fr.cryptohash;
/*  2:   */ 
/*  3:   */ public class Fugue256
/*  4:   */   extends Fugue2Core
/*  5:   */ {
/*  6:51 */   private static final int[] initVal = { -380453410, 1718686559, -522914200, -760171116, -110337507, -67556898, -1857427303, 888717896 };
/*  7:   */   
/*  8:   */   int[] getIV()
/*  9:   */   {
/* 10:59 */     return initVal;
/* 11:   */   }
/* 12:   */   
/* 13:   */   public int getDigestLength()
/* 14:   */   {
/* 15:65 */     return 32;
/* 16:   */   }
/* 17:   */   
/* 18:   */   FugueCore dup()
/* 19:   */   {
/* 20:71 */     return new Fugue256();
/* 21:   */   }
/* 22:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     fr.cryptohash.Fugue256
 * JD-Core Version:    0.7.1
 */