/*  1:   */ package fr.cryptohash;
/*  2:   */ 
/*  3:   */ public class JH512
/*  4:   */   extends JHCore
/*  5:   */ {
/*  6:42 */   private static final long[] IV = { 8057304316999936535L, 7163588758344160579L, -8493121987372650741L, -1642667786550131871L, -8566514163999866850L, 3073994181705428352L, 6381262963545062054L, -6264724275031688201L, 101865376847186537L, 5095130896708658778L, 1173090742995008548L, 4282485301459992985L, -3506062288766240682L, -6411286951896895146L, -2034786043042654213L, 6078248131731709259L };
/*  7:   */   
/*  8:   */   public Digest copy()
/*  9:   */   {
/* 10:63 */     return copyState(new JH512());
/* 11:   */   }
/* 12:   */   
/* 13:   */   public int getDigestLength()
/* 14:   */   {
/* 15:69 */     return 64;
/* 16:   */   }
/* 17:   */   
/* 18:   */   long[] getIV()
/* 19:   */   {
/* 20:75 */     return IV;
/* 21:   */   }
/* 22:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     fr.cryptohash.JH512
 * JD-Core Version:    0.7.1
 */