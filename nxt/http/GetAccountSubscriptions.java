/*  1:   */ package nxt.http;
/*  2:   */ 
/*  3:   */ import javax.servlet.http.HttpServletRequest;
/*  4:   */ import nxt.Account;
/*  5:   */ import nxt.NxtException;
/*  6:   */ import nxt.Subscription;
/*  7:   */ import nxt.db.DbIterator;
/*  8:   */ import org.json.simple.JSONArray;
/*  9:   */ import org.json.simple.JSONObject;
/* 10:   */ import org.json.simple.JSONStreamAware;
/* 11:   */ 
/* 12:   */ public final class GetAccountSubscriptions
/* 13:   */   extends APIServlet.APIRequestHandler
/* 14:   */ {
/* 15:19 */   static final GetAccountSubscriptions instance = new GetAccountSubscriptions();
/* 16:   */   
/* 17:   */   private GetAccountSubscriptions()
/* 18:   */   {
/* 19:22 */     super(new APITag[] { APITag.ACCOUNTS }, new String[] { "account" });
/* 20:   */   }
/* 21:   */   
/* 22:   */   JSONStreamAware processRequest(HttpServletRequest paramHttpServletRequest)
/* 23:   */     throws NxtException
/* 24:   */   {
/* 25:28 */     Account localAccount = ParameterParser.getAccount(paramHttpServletRequest);
/* 26:   */     
/* 27:30 */     JSONObject localJSONObject = new JSONObject();
/* 28:   */     
/* 29:32 */     JSONArray localJSONArray = new JSONArray();
/* 30:   */     
/* 31:34 */     DbIterator localDbIterator = Subscription.getSubscriptionsByParticipant(Long.valueOf(localAccount.getId()));
/* 32:36 */     for (Subscription localSubscription : localDbIterator) {
/* 33:37 */       localJSONArray.add(JSONData.subscription(localSubscription));
/* 34:   */     }
/* 35:40 */     localJSONObject.put("subscriptions", localJSONArray);
/* 36:41 */     return localJSONObject;
/* 37:   */   }
/* 38:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.http.GetAccountSubscriptions
 * JD-Core Version:    0.7.1
 */