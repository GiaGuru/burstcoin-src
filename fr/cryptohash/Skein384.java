/*  1:   */ package fr.cryptohash;
/*  2:   */ 
/*  3:   */ public class Skein384
/*  4:   */   extends SkeinBigCore
/*  5:   */ {
/*  6:52 */   private static final long[] initVal = { -6631894876634615969L, -5692838220127733084L, -7099962856338682626L, -2911352911530754598L, 2000907093792408677L, 9140007292425499655L, 6093301768906360022L, 2769176472213098488L };
/*  7:   */   
/*  8:   */   long[] getInitVal()
/*  9:   */   {
/* 10:62 */     return initVal;
/* 11:   */   }
/* 12:   */   
/* 13:   */   public int getDigestLength()
/* 14:   */   {
/* 15:68 */     return 48;
/* 16:   */   }
/* 17:   */   
/* 18:   */   SkeinBigCore dup()
/* 19:   */   {
/* 20:74 */     return new Skein384();
/* 21:   */   }
/* 22:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     fr.cryptohash.Skein384
 * JD-Core Version:    0.7.1
 */