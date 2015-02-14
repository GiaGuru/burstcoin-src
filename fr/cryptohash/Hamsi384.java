/*  1:   */ package fr.cryptohash;
/*  2:   */ 
/*  3:   */ public class Hamsi384
/*  4:   */   extends HamsiBigCore
/*  5:   */ {
/*  6:   */   public int getDigestLength()
/*  7:   */   {
/*  8:53 */     return 48;
/*  9:   */   }
/* 10:   */   
/* 11:56 */   private static final int[] IV = { 1701540978, 1869899107, 1752066405, 1798053955, 1869443189, 1952805408, 1399153525, 1919513721, 543256164, 541683300, 1970500722, 1767992352, 1131575664, 1953458034, 1634756729, 740313953 };
/* 12:   */   
/* 13:   */   int[] getIV()
/* 14:   */   {
/* 15:66 */     return IV;
/* 16:   */   }
/* 17:   */   
/* 18:   */   HamsiBigCore dup()
/* 19:   */   {
/* 20:72 */     return new Hamsi384();
/* 21:   */   }
/* 22:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     fr.cryptohash.Hamsi384
 * JD-Core Version:    0.7.1
 */