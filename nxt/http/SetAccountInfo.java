/*  1:   */ package nxt.http;
/*  2:   */ 
/*  3:   */ import javax.servlet.http.HttpServletRequest;
/*  4:   */ import nxt.Account;
/*  5:   */ import nxt.Attachment.MessagingAccountInfo;
/*  6:   */ import nxt.NxtException;
/*  7:   */ import nxt.util.Convert;
/*  8:   */ import org.json.simple.JSONStreamAware;
/*  9:   */ 
/* 10:   */ public final class SetAccountInfo
/* 11:   */   extends CreateTransaction
/* 12:   */ {
/* 13:17 */   static final SetAccountInfo instance = new SetAccountInfo();
/* 14:   */   
/* 15:   */   private SetAccountInfo()
/* 16:   */   {
/* 17:20 */     super(new APITag[] { APITag.ACCOUNTS, APITag.CREATE_TRANSACTION }, new String[] { "name", "description" });
/* 18:   */   }
/* 19:   */   
/* 20:   */   JSONStreamAware processRequest(HttpServletRequest paramHttpServletRequest)
/* 21:   */     throws NxtException
/* 22:   */   {
/* 23:26 */     String str1 = Convert.nullToEmpty(paramHttpServletRequest.getParameter("name")).trim();
/* 24:27 */     String str2 = Convert.nullToEmpty(paramHttpServletRequest.getParameter("description")).trim();
/* 25:29 */     if (str1.length() > 100) {
/* 26:30 */       return JSONResponses.INCORRECT_ACCOUNT_NAME_LENGTH;
/* 27:   */     }
/* 28:33 */     if (str2.length() > 1000) {
/* 29:34 */       return JSONResponses.INCORRECT_ACCOUNT_DESCRIPTION_LENGTH;
/* 30:   */     }
/* 31:37 */     Account localAccount = ParameterParser.getSenderAccount(paramHttpServletRequest);
/* 32:38 */     Attachment.MessagingAccountInfo localMessagingAccountInfo = new Attachment.MessagingAccountInfo(str1, str2);
/* 33:39 */     return createTransaction(paramHttpServletRequest, localAccount, localMessagingAccountInfo);
/* 34:   */   }
/* 35:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.http.SetAccountInfo
 * JD-Core Version:    0.7.1
 */