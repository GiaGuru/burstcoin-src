/*  1:   */ package fr.cryptohash;
/*  2:   */ 
/*  3:   */ public class HAVAL224_5
/*  4:   */   extends HAVALCore
/*  5:   */ {
/*  6:   */   public HAVAL224_5()
/*  7:   */   {
/*  8:46 */     super(224, 5);
/*  9:   */   }
/* 10:   */   
/* 11:   */   public int getDigestLength()
/* 12:   */   {
/* 13:52 */     return 28;
/* 14:   */   }
/* 15:   */   
/* 16:   */   public Digest copy()
/* 17:   */   {
/* 18:58 */     return copyState(new HAVAL224_5());
/* 19:   */   }
/* 20:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     fr.cryptohash.HAVAL224_5
 * JD-Core Version:    0.7.1
 */