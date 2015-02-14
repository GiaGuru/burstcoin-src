/*  1:   */ package fr.cryptohash;
/*  2:   */ 
/*  3:   */ public class BMW384
/*  4:   */   extends BMWBigCore
/*  5:   */ {
/*  6:51 */   private static final long[] initVal = { 283686952306183L, 579005069656919567L, 1157726452361532951L, 1736447835066146335L, 2315169217770759719L, 2893890600475373103L, 3472611983179986487L, 4051333365884599871L, 4630054748589213255L, 5208776131293826639L, 5787497513998440023L, 6366218896703053407L, 6944940279407666791L, 7523661662112280175L, 8102383044816893559L, 8681104427521506943L };
/*  7:   */   
/*  8:   */   long[] getInitVal()
/*  9:   */   {
/* 10:65 */     return initVal;
/* 11:   */   }
/* 12:   */   
/* 13:   */   public int getDigestLength()
/* 14:   */   {
/* 15:71 */     return 48;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public Digest copy()
/* 19:   */   {
/* 20:77 */     return copyState(new BMW384());
/* 21:   */   }
/* 22:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     fr.cryptohash.BMW384
 * JD-Core Version:    0.7.1
 */