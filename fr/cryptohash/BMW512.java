/*  1:   */ package fr.cryptohash;
/*  2:   */ 
/*  3:   */ public class BMW512
/*  4:   */   extends BMWBigCore
/*  5:   */ {
/*  6:51 */   private static final long[] initVal = { -9186918263483431289L, -8608196880778817905L, -8029475498074204521L, -7450754115369591137L, -6872032732664977753L, -6293311349960364369L, -5714589967255750985L, -5135868584551137601L, -4557147201846524217L, -3978425819141910833L, -3399704436437297449L, -2820983053732684065L, -2242261671028070681L, -1663540288323457297L, -1084818905618843913L, -506097522914230529L };
/*  7:   */   
/*  8:   */   long[] getInitVal()
/*  9:   */   {
/* 10:65 */     return initVal;
/* 11:   */   }
/* 12:   */   
/* 13:   */   public int getDigestLength()
/* 14:   */   {
/* 15:71 */     return 64;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public Digest copy()
/* 19:   */   {
/* 20:77 */     return copyState(new BMW512());
/* 21:   */   }
/* 22:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     fr.cryptohash.BMW512
 * JD-Core Version:    0.7.1
 */