/*  1:   */ package fr.cryptohash;
/*  2:   */ 
/*  3:   */ public class Tiger2
/*  4:   */   extends TigerCore
/*  5:   */ {
/*  6:   */   public Tiger2()
/*  7:   */   {
/*  8:48 */     super((byte)Byte.MIN_VALUE);
/*  9:   */   }
/* 10:   */   
/* 11:   */   public Digest copy()
/* 12:   */   {
/* 13:54 */     return copyState(new Tiger2());
/* 14:   */   }
/* 15:   */   
/* 16:   */   public String toString()
/* 17:   */   {
/* 18:60 */     return "Tiger2";
/* 19:   */   }
/* 20:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     fr.cryptohash.Tiger2
 * JD-Core Version:    0.7.1
 */