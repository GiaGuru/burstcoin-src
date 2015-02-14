/*  1:   */ package nxt.http;
/*  2:   */ 
/*  3:   */ import javax.servlet.http.HttpServletRequest;
/*  4:   */ import nxt.Account;
/*  5:   */ import nxt.NxtException;
/*  6:   */ import nxt.Order.Ask;
/*  7:   */ import nxt.db.DbIterator;
/*  8:   */ import nxt.util.Convert;
/*  9:   */ import org.json.simple.JSONArray;
/* 10:   */ import org.json.simple.JSONObject;
/* 11:   */ import org.json.simple.JSONStreamAware;
/* 12:   */ 
/* 13:   */ public final class GetAccountCurrentAskOrderIds
/* 14:   */   extends APIServlet.APIRequestHandler
/* 15:   */ {
/* 16:15 */   static final GetAccountCurrentAskOrderIds instance = new GetAccountCurrentAskOrderIds();
/* 17:   */   
/* 18:   */   private GetAccountCurrentAskOrderIds()
/* 19:   */   {
/* 20:18 */     super(new APITag[] { APITag.ACCOUNTS, APITag.AE }, new String[] { "account", "asset", "firstIndex", "lastIndex" });
/* 21:   */   }
/* 22:   */   
/* 23:   */   JSONStreamAware processRequest(HttpServletRequest paramHttpServletRequest)
/* 24:   */     throws NxtException
/* 25:   */   {
/* 26:24 */     long l1 = ParameterParser.getAccount(paramHttpServletRequest).getId();
/* 27:25 */     long l2 = 0L;
/* 28:   */     try
/* 29:   */     {
/* 30:27 */       l2 = Convert.parseUnsignedLong(paramHttpServletRequest.getParameter("asset"));
/* 31:   */     }
/* 32:   */     catch (RuntimeException localRuntimeException) {}
/* 33:31 */     int i = ParameterParser.getFirstIndex(paramHttpServletRequest);
/* 34:32 */     int j = ParameterParser.getLastIndex(paramHttpServletRequest);
/* 35:   */     DbIterator localDbIterator;
/* 36:35 */     if (l2 == 0L) {
/* 37:36 */       localDbIterator = Order.Ask.getAskOrdersByAccount(l1, i, j);
/* 38:   */     } else {
/* 39:38 */       localDbIterator = Order.Ask.getAskOrdersByAccountAsset(l1, l2, i, j);
/* 40:   */     }
/* 41:40 */     JSONArray localJSONArray = new JSONArray();
/* 42:   */     try
/* 43:   */     {
/* 44:42 */       while (localDbIterator.hasNext()) {
/* 45:43 */         localJSONArray.add(Convert.toUnsignedLong(((Order.Ask)localDbIterator.next()).getId()));
/* 46:   */       }
/* 47:   */     }
/* 48:   */     finally
/* 49:   */     {
/* 50:46 */       localDbIterator.close();
/* 51:   */     }
/* 52:48 */     JSONObject localJSONObject = new JSONObject();
/* 53:49 */     localJSONObject.put("askOrderIds", localJSONArray);
/* 54:50 */     return localJSONObject;
/* 55:   */   }
/* 56:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.http.GetAccountCurrentAskOrderIds
 * JD-Core Version:    0.7.1
 */