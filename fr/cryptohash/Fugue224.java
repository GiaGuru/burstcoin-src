/*  1:   */ package fr.cryptohash;
/*  2:   */ 
/*  3:   */ public class Fugue224
/*  4:   */   extends Fugue2Core
/*  5:   */ {
/*  6:51 */   private static final int[] initVal = { -188149235, 1653012311, -298196964, -529210421, -1592624030, -1706831339, -1114806374 };
/*  7:   */   
/*  8:   */   int[] getIV()
/*  9:   */   {
/* 10:59 */     return initVal;
/* 11:   */   }
/* 12:   */   
/* 13:   */   public int getDigestLength()
/* 14:   */   {
/* 15:65 */     return 28;
/* 16:   */   }
/* 17:   */   
/* 18:   */   FugueCore dup()
/* 19:   */   {
/* 20:71 */     return new Fugue224();
/* 21:   */   }
/* 22:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     fr.cryptohash.Fugue224
 * JD-Core Version:    0.7.1
 */