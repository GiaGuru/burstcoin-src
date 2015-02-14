/*  1:   */ package fr.cryptohash;
/*  2:   */ 
/*  3:   */ public class Skein256
/*  4:   */   extends SkeinBigCore
/*  5:   */ {
/*  6:52 */   private static final long[] initVal = { -3688372635733115373L, -1714305546867594773L, 6174048478977683055L, 3037510430686418139L, -1439460426187442557L, -1755217697375493551L, -4364063934328942203L, 4534485012945173523L };
/*  7:   */   
/*  8:   */   long[] getInitVal()
/*  9:   */   {
/* 10:62 */     return initVal;
/* 11:   */   }
/* 12:   */   
/* 13:   */   public int getDigestLength()
/* 14:   */   {
/* 15:68 */     return 32;
/* 16:   */   }
/* 17:   */   
/* 18:   */   SkeinBigCore dup()
/* 19:   */   {
/* 20:74 */     return new Skein256();
/* 21:   */   }
/* 22:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     fr.cryptohash.Skein256
 * JD-Core Version:    0.7.1
 */