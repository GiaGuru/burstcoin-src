/*  1:   */ package nxt.http;
/*  2:   */ 
/*  3:   */ import javax.servlet.http.HttpServletRequest;
/*  4:   */ import nxt.Account;
/*  5:   */ import nxt.Attachment;
/*  6:   */ import nxt.Attachment.AdvancedPaymentSubscriptionSubscribe;
/*  7:   */ import nxt.Constants;
/*  8:   */ import nxt.NxtException;
/*  9:   */ import org.json.simple.JSONObject;
/* 10:   */ import org.json.simple.JSONStreamAware;
/* 11:   */ 
/* 12:   */ public final class SendMoneySubscription
/* 13:   */   extends CreateTransaction
/* 14:   */ {
/* 15:17 */   static final SendMoneySubscription instance = new SendMoneySubscription();
/* 16:   */   
/* 17:   */   private SendMoneySubscription()
/* 18:   */   {
/* 19:20 */     super(new APITag[] { APITag.TRANSACTIONS, APITag.CREATE_TRANSACTION }, new String[] { "recipient", "amountNQT", "frequency" });
/* 20:   */   }
/* 21:   */   
/* 22:   */   JSONStreamAware processRequest(HttpServletRequest paramHttpServletRequest)
/* 23:   */     throws NxtException
/* 24:   */   {
/* 25:28 */     Account localAccount = ParameterParser.getSenderAccount(paramHttpServletRequest);
/* 26:29 */     Long localLong1 = Long.valueOf(ParameterParser.getRecipientId(paramHttpServletRequest));
/* 27:30 */     Long localLong2 = Long.valueOf(ParameterParser.getAmountNQT(paramHttpServletRequest));
/* 28:   */     int i;
/* 29:   */     try
/* 30:   */     {
/* 31:34 */       i = Integer.parseInt(paramHttpServletRequest.getParameter("frequency"));
/* 32:   */     }
/* 33:   */     catch (Exception localException)
/* 34:   */     {
/* 35:37 */       JSONObject localJSONObject = new JSONObject();
/* 36:38 */       localJSONObject.put("errorCode", Integer.valueOf(4));
/* 37:39 */       localJSONObject.put("errorDescription", "Invalid or missing frequency parameter");
/* 38:40 */       return localJSONObject;
/* 39:   */     }
/* 40:43 */     if ((i < Constants.BURST_SUBSCRIPTION_MIN_FREQ) || (i > Constants.BURST_SUBSCRIPTION_MAX_FREQ))
/* 41:   */     {
/* 42:45 */       localObject = new JSONObject();
/* 43:46 */       ((JSONObject)localObject).put("errorCode", Integer.valueOf(4));
/* 44:47 */       ((JSONObject)localObject).put("errorDescription", "Invalid frequency amount");
/* 45:48 */       return (JSONStreamAware)localObject;
/* 46:   */     }
/* 47:51 */     Object localObject = new Attachment.AdvancedPaymentSubscriptionSubscribe(i);
/* 48:   */     
/* 49:53 */     return createTransaction(paramHttpServletRequest, localAccount, localLong1, localLong2.longValue(), (Attachment)localObject);
/* 50:   */   }
/* 51:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.http.SendMoneySubscription
 * JD-Core Version:    0.7.1
 */