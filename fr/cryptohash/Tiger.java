/*  1:   */ package fr.cryptohash;
/*  2:   */ 
/*  3:   */ public class Tiger
/*  4:   */   extends TigerCore
/*  5:   */ {
/*  6:   */   public Tiger()
/*  7:   */   {
/*  8:47 */     super((byte)1);
/*  9:   */   }
/* 10:   */   
/* 11:   */   public Digest copy()
/* 12:   */   {
/* 13:53 */     return copyState(new Tiger());
/* 14:   */   }
/* 15:   */   
/* 16:   */   public String toString()
/* 17:   */   {
/* 18:59 */     return "Tiger";
/* 19:   */   }
/* 20:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     fr.cryptohash.Tiger
 * JD-Core Version:    0.7.1
 */