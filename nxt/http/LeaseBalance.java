/*  1:   */ package nxt.http;
/*  2:   */ 
/*  3:   */ import javax.servlet.http.HttpServletRequest;
/*  4:   */ import nxt.Account;
/*  5:   */ import nxt.Attachment;
/*  6:   */ import nxt.Attachment.AccountControlEffectiveBalanceLeasing;
/*  7:   */ import nxt.NxtException;
/*  8:   */ import nxt.util.Convert;
/*  9:   */ import org.json.simple.JSONObject;
/* 10:   */ import org.json.simple.JSONStreamAware;
/* 11:   */ 
/* 12:   */ public final class LeaseBalance
/* 13:   */   extends CreateTransaction
/* 14:   */ {
/* 15:17 */   static final LeaseBalance instance = new LeaseBalance();
/* 16:   */   
/* 17:   */   private LeaseBalance()
/* 18:   */   {
/* 19:20 */     super(new APITag[] { APITag.FORGING }, new String[] { "period", "recipient" });
/* 20:   */   }
/* 21:   */   
/* 22:   */   JSONStreamAware processRequest(HttpServletRequest paramHttpServletRequest)
/* 23:   */     throws NxtException
/* 24:   */   {
/* 25:26 */     String str = Convert.emptyToNull(paramHttpServletRequest.getParameter("period"));
/* 26:27 */     if (str == null) {
/* 27:28 */       return JSONResponses.MISSING_PERIOD;
/* 28:   */     }
/* 29:   */     short s;
/* 30:   */     try
/* 31:   */     {
/* 32:32 */       s = Short.parseShort(str);
/* 33:33 */       if (s < 1440) {
/* 34:34 */         return JSONResponses.INCORRECT_PERIOD;
/* 35:   */       }
/* 36:   */     }
/* 37:   */     catch (NumberFormatException localNumberFormatException)
/* 38:   */     {
/* 39:37 */       return JSONResponses.INCORRECT_PERIOD;
/* 40:   */     }
/* 41:40 */     Account localAccount1 = ParameterParser.getSenderAccount(paramHttpServletRequest);
/* 42:41 */     long l = ParameterParser.getRecipientId(paramHttpServletRequest);
/* 43:42 */     Account localAccount2 = Account.getAccount(l);
/* 44:43 */     if ((localAccount2 == null) || (localAccount2.getPublicKey() == null))
/* 45:   */     {
/* 46:44 */       localObject = new JSONObject();
/* 47:45 */       ((JSONObject)localObject).put("errorCode", Integer.valueOf(8));
/* 48:46 */       ((JSONObject)localObject).put("errorDescription", "recipient account does not have public key");
/* 49:47 */       return (JSONStreamAware)localObject;
/* 50:   */     }
/* 51:49 */     Object localObject = new Attachment.AccountControlEffectiveBalanceLeasing(s);
/* 52:50 */     return createTransaction(paramHttpServletRequest, localAccount1, Long.valueOf(l), 0L, (Attachment)localObject);
/* 53:   */   }
/* 54:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.http.LeaseBalance
 * JD-Core Version:    0.7.1
 */