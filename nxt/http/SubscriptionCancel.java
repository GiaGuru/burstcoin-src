/*  1:   */ package nxt.http;
/*  2:   */ 
/*  3:   */ import javax.servlet.http.HttpServletRequest;
/*  4:   */ import nxt.Account;
/*  5:   */ import nxt.Attachment;
/*  6:   */ import nxt.Attachment.AdvancedPaymentSubscriptionCancel;
/*  7:   */ import nxt.NxtException;
/*  8:   */ import nxt.Subscription;
/*  9:   */ import nxt.util.Convert;
/* 10:   */ import org.json.simple.JSONObject;
/* 11:   */ import org.json.simple.JSONStreamAware;
/* 12:   */ 
/* 13:   */ public final class SubscriptionCancel
/* 14:   */   extends CreateTransaction
/* 15:   */ {
/* 16:17 */   static SubscriptionCancel instance = new SubscriptionCancel();
/* 17:   */   
/* 18:   */   private SubscriptionCancel()
/* 19:   */   {
/* 20:20 */     super(new APITag[] { APITag.TRANSACTIONS, APITag.CREATE_TRANSACTION }, new String[] { "subscription" });
/* 21:   */   }
/* 22:   */   
/* 23:   */   JSONStreamAware processRequest(HttpServletRequest paramHttpServletRequest)
/* 24:   */     throws NxtException
/* 25:   */   {
/* 26:26 */     Account localAccount = ParameterParser.getSenderAccount(paramHttpServletRequest);
/* 27:   */     
/* 28:28 */     String str = Convert.emptyToNull(paramHttpServletRequest.getParameter("subscription"));
/* 29:   */     Object localObject1;
/* 30:29 */     if (str == null)
/* 31:   */     {
/* 32:30 */       localObject1 = new JSONObject();
/* 33:31 */       ((JSONObject)localObject1).put("errorCode", Integer.valueOf(3));
/* 34:32 */       ((JSONObject)localObject1).put("errorDescription", "Subscription Id not specified");
/* 35:33 */       return (JSONStreamAware)localObject1;
/* 36:   */     }
/* 37:   */     try
/* 38:   */     {
/* 39:38 */       localObject1 = Long.valueOf(Convert.parseUnsignedLong(str));
/* 40:   */     }
/* 41:   */     catch (Exception localException)
/* 42:   */     {
/* 43:41 */       localObject2 = new JSONObject();
/* 44:42 */       ((JSONObject)localObject2).put("errorCode", Integer.valueOf(4));
/* 45:43 */       ((JSONObject)localObject2).put("errorDescription", "Failed to parse subscription id");
/* 46:44 */       return (JSONStreamAware)localObject2;
/* 47:   */     }
/* 48:47 */     Subscription localSubscription = Subscription.getSubscription((Long)localObject1);
/* 49:48 */     if (localSubscription == null)
/* 50:   */     {
/* 51:49 */       localObject2 = new JSONObject();
/* 52:50 */       ((JSONObject)localObject2).put("errorCode", Integer.valueOf(5));
/* 53:51 */       ((JSONObject)localObject2).put("errorDescription", "Subscription not found");
/* 54:52 */       return (JSONStreamAware)localObject2;
/* 55:   */     }
/* 56:55 */     if ((localAccount.getId() != localSubscription.getSenderId().longValue()) && (localAccount.getId() != localSubscription.getRecipientId().longValue()))
/* 57:   */     {
/* 58:57 */       localObject2 = new JSONObject();
/* 59:58 */       ((JSONObject)localObject2).put("errorCode", Integer.valueOf(7));
/* 60:59 */       ((JSONObject)localObject2).put("errorDescription", "Must be sender or recipient to cancel subscription");
/* 61:60 */       return (JSONStreamAware)localObject2;
/* 62:   */     }
/* 63:63 */     Object localObject2 = new Attachment.AdvancedPaymentSubscriptionCancel(localSubscription.getId());
/* 64:   */     
/* 65:65 */     return createTransaction(paramHttpServletRequest, localAccount, null, 0L, (Attachment)localObject2);
/* 66:   */   }
/* 67:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.http.SubscriptionCancel
 * JD-Core Version:    0.7.1
 */