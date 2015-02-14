/*  1:   */ package fr.cryptohash;
/*  2:   */ 
/*  3:   */ public class JH384
/*  4:   */   extends JHCore
/*  5:   */ {
/*  6:42 */   private static final long[] IV = { 5196656745246833034L, 7870988717519701915L, 7204328289895230766L, 3687545538712928871L, -7444792898534328024L, -3146351271677443511L, 3960165672788969452L, -4096856656410559263L, 6240721940002850124L, -6990504398684705010L, 6386036639410061517L, -9169175405265211686L, -8468594786686388422L, -848533058334131599L, -6218112374030347372L, -7535578005846723713L };
/*  7:   */   
/*  8:   */   public Digest copy()
/*  9:   */   {
/* 10:63 */     return copyState(new JH384());
/* 11:   */   }
/* 12:   */   
/* 13:   */   public int getDigestLength()
/* 14:   */   {
/* 15:69 */     return 48;
/* 16:   */   }
/* 17:   */   
/* 18:   */   long[] getIV()
/* 19:   */   {
/* 20:75 */     return IV;
/* 21:   */   }
/* 22:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     fr.cryptohash.JH384
 * JD-Core Version:    0.7.1
 */