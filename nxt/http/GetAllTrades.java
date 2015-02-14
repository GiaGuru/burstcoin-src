/*  1:   */ package nxt.http;
/*  2:   */ 
/*  3:   */ import javax.servlet.http.HttpServletRequest;
/*  4:   */ import nxt.NxtException;
/*  5:   */ import nxt.Trade;
/*  6:   */ import nxt.db.FilteringIterator;
/*  7:   */ import nxt.db.FilteringIterator.Filter;
/*  8:   */ import org.json.simple.JSONArray;
/*  9:   */ import org.json.simple.JSONObject;
/* 10:   */ import org.json.simple.JSONStreamAware;
/* 11:   */ 
/* 12:   */ public final class GetAllTrades
/* 13:   */   extends APIServlet.APIRequestHandler
/* 14:   */ {
/* 15:14 */   static final GetAllTrades instance = new GetAllTrades();
/* 16:   */   
/* 17:   */   private GetAllTrades()
/* 18:   */   {
/* 19:17 */     super(new APITag[] { APITag.AE }, new String[] { "timestamp", "firstIndex", "lastIndex", "includeAssetInfo" });
/* 20:   */   }
/* 21:   */   
/* 22:   */   JSONStreamAware processRequest(HttpServletRequest paramHttpServletRequest)
/* 23:   */     throws NxtException
/* 24:   */   {
/* 25:22 */     final int i = ParameterParser.getTimestamp(paramHttpServletRequest);
/* 26:23 */     int j = ParameterParser.getFirstIndex(paramHttpServletRequest);
/* 27:24 */     int k = ParameterParser.getLastIndex(paramHttpServletRequest);
/* 28:25 */     boolean bool = !"false".equalsIgnoreCase(paramHttpServletRequest.getParameter("includeAssetInfo"));
/* 29:   */     
/* 30:27 */     JSONObject localJSONObject = new JSONObject();
/* 31:28 */     JSONArray localJSONArray = new JSONArray();
/* 32:29 */     FilteringIterator localFilteringIterator = new FilteringIterator(Trade.getAllTrades(0, -1), new FilteringIterator.Filter()
/* 33:   */     {
/* 34:   */       public boolean ok(Trade paramAnonymousTrade)
/* 35:   */       {
/* 36:33 */         return paramAnonymousTrade.getTimestamp() >= i;
/* 37:   */       }
/* 38:33 */     }, j, k);Object localObject1 = null;
/* 39:   */     try
/* 40:   */     {
/* 41:36 */       while (localFilteringIterator.hasNext()) {
/* 42:37 */         localJSONArray.add(JSONData.trade((Trade)localFilteringIterator.next(), bool));
/* 43:   */       }
/* 44:   */     }
/* 45:   */     catch (Throwable localThrowable2)
/* 46:   */     {
/* 47:29 */       localObject1 = localThrowable2;throw localThrowable2;
/* 48:   */     }
/* 49:   */     finally
/* 50:   */     {
/* 51:39 */       if (localFilteringIterator != null) {
/* 52:39 */         if (localObject1 != null) {
/* 53:   */           try
/* 54:   */           {
/* 55:39 */             localFilteringIterator.close();
/* 56:   */           }
/* 57:   */           catch (Throwable localThrowable3)
/* 58:   */           {
/* 59:39 */             ((Throwable)localObject1).addSuppressed(localThrowable3);
/* 60:   */           }
/* 61:   */         } else {
/* 62:39 */           localFilteringIterator.close();
/* 63:   */         }
/* 64:   */       }
/* 65:   */     }
/* 66:40 */     localJSONObject.put("trades", localJSONArray);
/* 67:41 */     return localJSONObject;
/* 68:   */   }
/* 69:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.http.GetAllTrades
 * JD-Core Version:    0.7.1
 */