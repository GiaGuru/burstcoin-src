/*  1:   */ package nxt.http;
/*  2:   */ 
/*  3:   */ public enum APITag
/*  4:   */ {
/*  5: 5 */   ACCOUNTS("Accounts"),  ALIASES("Aliases"),  AE("Asset Exchange"),  CREATE_TRANSACTION("Create Transaction"),  BLOCKS("Blocks"),  DGS("Digital Goods Store"),  FORGING("Forging"),  INFO("Server Info"),  MESSAGES("Messages"),  MINING("Mining"),  TRANSACTIONS("Transactions"),  TOKENS("Tokens"),  VS("Voting System"),  AT("Automated Transaction"),  UTILS("Utils"),  DEBUG("Debug");
/*  6:   */   
/*  7:   */   private final String displayName;
/*  8:   */   
/*  9:   */   private APITag(String paramString)
/* 10:   */   {
/* 11:12 */     this.displayName = paramString;
/* 12:   */   }
/* 13:   */   
/* 14:   */   public String getDisplayName()
/* 15:   */   {
/* 16:16 */     return this.displayName;
/* 17:   */   }
/* 18:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.http.APITag
 * JD-Core Version:    0.7.1
 */