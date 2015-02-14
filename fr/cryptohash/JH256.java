/*  1:   */ package fr.cryptohash;
/*  2:   */ 
/*  3:   */ public class JH256
/*  4:   */   extends JHCore
/*  5:   */ {
/*  6:42 */   private static final long[] IV = { -1470245778027916309L, -7868423535827204671L, 2059079139475113978L, 2738332431195081219L, -6619273139259524795L, -2235163889960840073L, -3623802145412016262L, 6264546947827183528L, 8188554064778120565L, 2095380029494911586L, 2843625104802615188L, -8658814222571918996L, 6655800376422174060L, 5873908626791692815L, 8927492802571670250L, -8510770769165494423L };
/*  7:   */   
/*  8:   */   public Digest copy()
/*  9:   */   {
/* 10:63 */     return copyState(new JH256());
/* 11:   */   }
/* 12:   */   
/* 13:   */   public int getDigestLength()
/* 14:   */   {
/* 15:69 */     return 32;
/* 16:   */   }
/* 17:   */   
/* 18:   */   long[] getIV()
/* 19:   */   {
/* 20:75 */     return IV;
/* 21:   */   }
/* 22:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     fr.cryptohash.JH256
 * JD-Core Version:    0.7.1
 */