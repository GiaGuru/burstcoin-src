/*  1:   */ package nxt.http;
/*  2:   */ 
/*  3:   */ import javax.servlet.http.HttpServletRequest;
/*  4:   */ import nxt.NxtException;
/*  5:   */ import nxt.Subscription;
/*  6:   */ import nxt.util.Convert;
/*  7:   */ import org.json.simple.JSONObject;
/*  8:   */ import org.json.simple.JSONStreamAware;
/*  9:   */ 
/* 10:   */ public final class GetSubscription
/* 11:   */   extends APIServlet.APIRequestHandler
/* 12:   */ {
/* 13:15 */   static final GetSubscription instance = new GetSubscription();
/* 14:   */   
/* 15:   */   private GetSubscription()
/* 16:   */   {
/* 17:18 */     super(new APITag[] { APITag.ACCOUNTS }, new String[] { "subscription" });
/* 18:   */   }
/* 19:   */   
/* 20:   */   JSONStreamAware processRequest(HttpServletRequest paramHttpServletRequest)
/* 21:   */     throws NxtException
/* 22:   */   {
/* 23:   */     Long localLong;
/* 24:   */     JSONObject localJSONObject;
/* 25:   */     try
/* 26:   */     {
/* 27:25 */       localLong = Long.valueOf(Convert.parseUnsignedLong(Convert.emptyToNull(paramHttpServletRequest.getParameter("subscription"))));
/* 28:   */     }
/* 29:   */     catch (Exception localException)
/* 30:   */     {
/* 31:28 */       localJSONObject = new JSONObject();
/* 32:29 */       localJSONObject.put("errorCode", Integer.valueOf(3));
/* 33:30 */       localJSONObject.put("errorDescription", "Invalid or not specified subscription");
/* 34:31 */       return localJSONObject;
/* 35:   */     }
/* 36:34 */     Subscription localSubscription = Subscription.getSubscription(localLong);
/* 37:35 */     if (localSubscription == null)
/* 38:   */     {
/* 39:36 */       localJSONObject = new JSONObject();
/* 40:37 */       localJSONObject.put("errorCode", Integer.valueOf(5));
/* 41:38 */       localJSONObject.put("errorDescription", "Subscription not found");
/* 42:39 */       return localJSONObject;
/* 43:   */     }
/* 44:42 */     return JSONData.subscription(localSubscription);
/* 45:   */   }
/* 46:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.http.GetSubscription
 * JD-Core Version:    0.7.1
 */