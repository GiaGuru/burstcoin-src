/*  1:   */ package fr.cryptohash;
/*  2:   */ 
/*  3:   */ public class BMW224
/*  4:   */   extends BMWSmallCore
/*  5:   */ {
/*  6:51 */   private static final int[] initVal = { 66051, 67438087, 134810123, 202182159, 269554195, 336926231, 404298267, 471670303, 539042339, 606414375, 673786411, 741158447, 808530483, 875902519, 943274555, 1010646591 };
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
/* 20:73 */     return copyState(new BMW224());
/* 21:   */   }
/* 22:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     fr.cryptohash.BMW224
 * JD-Core Version:    0.7.1
 */