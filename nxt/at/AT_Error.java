/*  1:   */ package nxt.at;
/*  2:   */ 
/*  3:   */ public enum AT_Error
/*  4:   */ {
/*  5: 5 */   INCORRECT_VERSION(1, "The current AT version is not supported."),  INCORRECT_CODE_PAGES(2, "Maximum number of code pages exceeded."),  INCORRECT_DATA_PAGES(3, "Maximum number of data pages exceeded."),  INCORRECT_CALL_PAGES(4, "Maximum number of call stack pages exceeded."),  INCORRECT_USER_PAGES(5, "Maximum number of user stack pages exceeded."),  INCORRECT_CODE_LENGTH(6, "Code length is incorrect"),  INCORRECT_CODE(7, "Invalid code."),  INCORRECT_DATA_LENGTH(8, "Data length is incorrect."),  INCORRECT_CREATION_TX(9, "Incorrect AT creation tx."),  INCORRECT_CREATION_FEE(10, "Incorrect creation fee for given number of pages");
/*  6:   */   
/*  7:   */   private final int code;
/*  8:   */   private final String description;
/*  9:   */   
/* 10:   */   private AT_Error(int paramInt, String paramString)
/* 11:   */   {
/* 12:21 */     this.code = paramInt;
/* 13:22 */     this.description = paramString;
/* 14:   */   }
/* 15:   */   
/* 16:   */   public String getDescription()
/* 17:   */   {
/* 18:26 */     return this.description;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public int getCode()
/* 22:   */   {
/* 23:30 */     return this.code;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public String toString()
/* 27:   */   {
/* 28:35 */     return "error code: " + this.code + " , description : " + this.description;
/* 29:   */   }
/* 30:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.at.AT_Error
 * JD-Core Version:    0.7.1
 */