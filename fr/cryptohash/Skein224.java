/*  1:   */ package fr.cryptohash;
/*  2:   */ 
/*  3:   */ public class Skein224
/*  4:   */   extends SkeinBigCore
/*  5:   */ {
/*  6:52 */   private static final long[] initVal = { -3688341020067007964L, -3772225436291745297L, -8300862168937575580L, 4146387520469897396L, 1106145742801415120L, 7455425944880474941L, -7351063101234211863L, -7048981346965512457L };
/*  7:   */   
/*  8:   */   long[] getInitVal()
/*  9:   */   {
/* 10:62 */     return initVal;
/* 11:   */   }
/* 12:   */   
/* 13:   */   public int getDigestLength()
/* 14:   */   {
/* 15:68 */     return 28;
/* 16:   */   }
/* 17:   */   
/* 18:   */   SkeinBigCore dup()
/* 19:   */   {
/* 20:74 */     return new Skein224();
/* 21:   */   }
/* 22:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     fr.cryptohash.Skein224
 * JD-Core Version:    0.7.1
 */