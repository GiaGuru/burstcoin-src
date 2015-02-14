/*  1:   */ package nxt.http;
/*  2:   */ 
/*  3:   */ import javax.servlet.http.HttpServletRequest;
/*  4:   */ import nxt.Account;
/*  5:   */ import nxt.Order.Bid;
/*  6:   */ import nxt.db.DbIterator;
/*  7:   */ import nxt.util.Convert;
/*  8:   */ import org.json.simple.JSONArray;
/*  9:   */ import org.json.simple.JSONObject;
/* 10:   */ import org.json.simple.JSONStreamAware;
/* 11:   */ 
/* 12:   */ public final class GetAccountCurrentBidOrderIds
/* 13:   */   extends APIServlet.APIRequestHandler
/* 14:   */ {
/* 15:14 */   static final GetAccountCurrentBidOrderIds instance = new GetAccountCurrentBidOrderIds();
/* 16:   */   
/* 17:   */   private GetAccountCurrentBidOrderIds()
/* 18:   */   {
/* 19:17 */     super(new APITag[] { APITag.ACCOUNTS, APITag.AE }, new String[] { "account", "asset", "firstIndex", "lastIndex" });
/* 20:   */   }
/* 21:   */   
/* 22:   */   JSONStreamAware processRequest(HttpServletRequest paramHttpServletRequest)
/* 23:   */     throws ParameterException
/* 24:   */   {
/* 25:23 */     long l1 = ParameterParser.getAccount(paramHttpServletRequest).getId();
/* 26:24 */     long l2 = 0L;
/* 27:   */     try
/* 28:   */     {
/* 29:26 */       l2 = Convert.parseUnsignedLong(paramHttpServletRequest.getParameter("asset"));
/* 30:   */     }
/* 31:   */     catch (RuntimeException localRuntimeException) {}
/* 32:30 */     int i = ParameterParser.getFirstIndex(paramHttpServletRequest);
/* 33:31 */     int j = ParameterParser.getLastIndex(paramHttpServletRequest);
/* 34:   */     DbIterator localDbIterator;
/* 35:34 */     if (l2 == 0L) {
/* 36:35 */       localDbIterator = Order.Bid.getBidOrdersByAccount(l1, i, j);
/* 37:   */     } else {
/* 38:37 */       localDbIterator = Order.Bid.getBidOrdersByAccountAsset(l1, l2, i, j);
/* 39:   */     }
/* 40:39 */     JSONArray localJSONArray = new JSONArray();
/* 41:   */     try
/* 42:   */     {
/* 43:41 */       while (localDbIterator.hasNext()) {
/* 44:42 */         localJSONArray.add(Convert.toUnsignedLong(((Order.Bid)localDbIterator.next()).getId()));
/* 45:   */       }
/* 46:   */     }
/* 47:   */     finally
/* 48:   */     {
/* 49:45 */       localDbIterator.close();
/* 50:   */     }
/* 51:47 */     JSONObject localJSONObject = new JSONObject();
/* 52:48 */     localJSONObject.put("bidOrderIds", localJSONArray);
/* 53:49 */     return localJSONObject;
/* 54:   */   }
/* 55:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.http.GetAccountCurrentBidOrderIds
 * JD-Core Version:    0.7.1
 */