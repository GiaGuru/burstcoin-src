/*  1:   */ package fr.cryptohash;
/*  2:   */ 
/*  3:   */ public class HAVAL256_4
/*  4:   */   extends HAVALCore
/*  5:   */ {
/*  6:   */   public HAVAL256_4()
/*  7:   */   {
/*  8:46 */     super(256, 4);
/*  9:   */   }
/* 10:   */   
/* 11:   */   public int getDigestLength()
/* 12:   */   {
/* 13:52 */     return 32;
/* 14:   */   }
/* 15:   */   
/* 16:   */   public Digest copy()
/* 17:   */   {
/* 18:58 */     return copyState(new HAVAL256_4());
/* 19:   */   }
/* 20:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     fr.cryptohash.HAVAL256_4
 * JD-Core Version:    0.7.1
 */