/*  1:   */ package fr.cryptohash;
/*  2:   */ 
/*  3:   */ public class CubeHash384
/*  4:   */   extends CubeHashCore
/*  5:   */ {
/*  6:42 */   private static final int[] IV = { -433911682, 79694983, 1593074771, 1767000851, 436586409, 891871112, 1809646005, 1347925906, 1789372706, 1687977710, -198823777, -882732974, -57794323, -1287115861, -123838850, -667071004, 1153417485, 1606542909, 553969355, 395677567, -605360272, -1262911970, 851547638, 1636083940, 2037130563, 601757549, -751060244, 202565074, 1320770171, 756595201, 82724703, 533066286 };
/*  7:   */   
/*  8:   */   public Digest copy()
/*  9:   */   {
/* 10:66 */     return copyState(new CubeHash384());
/* 11:   */   }
/* 12:   */   
/* 13:   */   public int getDigestLength()
/* 14:   */   {
/* 15:72 */     return 48;
/* 16:   */   }
/* 17:   */   
/* 18:   */   int[] getIV()
/* 19:   */   {
/* 20:78 */     return IV;
/* 21:   */   }
/* 22:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     fr.cryptohash.CubeHash384
 * JD-Core Version:    0.7.1
 */