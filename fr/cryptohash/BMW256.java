/*  1:   */ package fr.cryptohash;
/*  2:   */ 
/*  3:   */ public class BMW256
/*  4:   */   extends BMWSmallCore
/*  5:   */ {
/*  6:51 */   private static final int[] initVal = { 1078018627, 1145390663, 1212762699, 1280134735, 1347506771, 1414878807, 1482250843, 1549622879, 1616994915, 1684366951, 1751738987, 1819111023, 1886483059, 1953855095, 2021227131, 2088599167 };
/*  7:   */   
/*  8:   */   int[] getInitVal()
/*  9:   */   {
/* 10:61 */     return initVal;
/* 11:   */   }
/* 12:   */   
/* 13:   */   public int getDigestLength()
/* 14:   */   {
/* 15:67 */     return 32;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public Digest copy()
/* 19:   */   {
/* 20:73 */     return copyState(new BMW256());
/* 21:   */   }
/* 22:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     fr.cryptohash.BMW256
 * JD-Core Version:    0.7.1
 */