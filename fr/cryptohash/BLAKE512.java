/*  1:   */ package fr.cryptohash;
/*  2:   */ 
/*  3:   */ public class BLAKE512
/*  4:   */   extends BLAKEBigCore
/*  5:   */ {
/*  6:51 */   private static final long[] initVal = { 7640891576956012808L, -4942790177534073029L, 4354685564936845355L, -6534734903238641935L, 5840696475078001361L, -7276294671716946913L, 2270897969802886507L, 6620516959819538809L };
/*  7:   */   
/*  8:   */   long[] getInitVal()
/*  9:   */   {
/* 10:61 */     return initVal;
/* 11:   */   }
/* 12:   */   
/* 13:   */   public int getDigestLength()
/* 14:   */   {
/* 15:67 */     return 64;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public Digest copy()
/* 19:   */   {
/* 20:73 */     return copyState(new BLAKE512());
/* 21:   */   }
/* 22:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     fr.cryptohash.BLAKE512
 * JD-Core Version:    0.7.1
 */