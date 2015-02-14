/*  1:   */ package fr.cryptohash;
/*  2:   */ 
/*  3:   */ public class SIMD384
/*  4:   */   extends SIMDBigCore
/*  5:   */ {
/*  6:51 */   private static final int[] initVal = { -1976111428, -1801208432, -783058045, -1302657269, -194776651, -1225253344, 5620537, -1261353775, 1935723105, 406198787, 400340153, 873776218, -1499878958, -476146076, 1183579767, 1372611320, -1176256536, 1673449002, -1880815943, -120829246, 2061571394, -1314202207, 1689282452, 684913762, -431984148, 501977512, -1966548189, 1064839861, 60513464, 1910364514, -86102806, 337043313 };
/*  7:   */   
/*  8:   */   int[] getInitVal()
/*  9:   */   {
/* 10:65 */     return initVal;
/* 11:   */   }
/* 12:   */   
/* 13:   */   public int getDigestLength()
/* 14:   */   {
/* 15:71 */     return 48;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public Digest copy()
/* 19:   */   {
/* 20:77 */     return copyState(new SIMD384());
/* 21:   */   }
/* 22:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     fr.cryptohash.SIMD384
 * JD-Core Version:    0.7.1
 */