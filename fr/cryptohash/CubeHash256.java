/*  1:   */ package fr.cryptohash;
/*  2:   */ 
/*  3:   */ public class CubeHash256
/*  4:   */   extends CubeHashCore
/*  5:   */ {
/*  6:42 */   private static final int[] IV = { -366226252, -858328417, 1662090865, 893918894, 575745371, -438743453, 2120368433, -187952450, -1026509162, 1118773360, -797832139, 862050956, 684518564, -1896305277, 1182837760, 1088813995, -661634621, 1627913173, 1820695873, -256743815, 154740041, 1604474371, 1707643645, -1815387515, 720549294, -1639231904, 2001387485, -2061154523, 360798955, 1253485270, -1663371089, -704435190 };
/*  7:   */   
/*  8:   */   public Digest copy()
/*  9:   */   {
/* 10:66 */     return copyState(new CubeHash256());
/* 11:   */   }
/* 12:   */   
/* 13:   */   public int getDigestLength()
/* 14:   */   {
/* 15:72 */     return 32;
/* 16:   */   }
/* 17:   */   
/* 18:   */   int[] getIV()
/* 19:   */   {
/* 20:78 */     return IV;
/* 21:   */   }
/* 22:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     fr.cryptohash.CubeHash256
 * JD-Core Version:    0.7.1
 */