/*  1:   */ package fr.cryptohash;
/*  2:   */ 
/*  3:   */ public class JH224
/*  4:   */   extends JHCore
/*  5:   */ {
/*  6:42 */   private static final long[] IV = { 3314329792955455660L, -5873629778441521945L, -6592407812722126314L, -5161062746532997996L, 7411105978436841583L, -3558227318719964452L, 1290336678912093712L, -462598081155037503L, 1967821647772139699L, -7496626961025630055L, -2531142125762206765L, -6615250334072464792L, 2500810107236295140L, -3065635687750079248L, 1555446023024487359L, -7633076966351639954L };
/*  7:   */   
/*  8:   */   public Digest copy()
/*  9:   */   {
/* 10:63 */     return copyState(new JH224());
/* 11:   */   }
/* 12:   */   
/* 13:   */   public int getDigestLength()
/* 14:   */   {
/* 15:69 */     return 28;
/* 16:   */   }
/* 17:   */   
/* 18:   */   long[] getIV()
/* 19:   */   {
/* 20:75 */     return IV;
/* 21:   */   }
/* 22:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     fr.cryptohash.JH224
 * JD-Core Version:    0.7.1
 */