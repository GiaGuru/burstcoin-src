/*  1:   */ package fr.cryptohash;
/*  2:   */ 
/*  3:   */ public class CubeHash224
/*  4:   */   extends CubeHashCore
/*  5:   */ {
/*  6:42 */   private static final int[] IV = { -1325628905, 468589200, -2103567838, 1667416898, 618208304, 61319716, -1506336312, -2052005137, -212003853, 1104838781, 564595878, 525244276, -1277048526, -351635032, -841307546, -487134038, 175190882, -1439661856, -655214030, -823042520, -620207340, 2138086903, -1395513180, 1884122830, -1438725793, -477642220, 979650047, -1666481725, -952284264, -763908418, -48229092, 13326142 };
/*  7:   */   
/*  8:   */   public Digest copy()
/*  9:   */   {
/* 10:66 */     return copyState(new CubeHash224());
/* 11:   */   }
/* 12:   */   
/* 13:   */   public int getDigestLength()
/* 14:   */   {
/* 15:72 */     return 28;
/* 16:   */   }
/* 17:   */   
/* 18:   */   int[] getIV()
/* 19:   */   {
/* 20:78 */     return IV;
/* 21:   */   }
/* 22:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     fr.cryptohash.CubeHash224
 * JD-Core Version:    0.7.1
 */