/*  1:   */ package nxt.http;
/*  2:   */ 
/*  3:   */ import javax.servlet.http.HttpServletRequest;
/*  4:   */ import nxt.Asset;
/*  5:   */ import nxt.NxtException;
/*  6:   */ import nxt.Order.Ask;
/*  7:   */ import nxt.db.DbIterator;
/*  8:   */ import org.json.simple.JSONArray;
/*  9:   */ import org.json.simple.JSONObject;
/* 10:   */ import org.json.simple.JSONStreamAware;
/* 11:   */ 
/* 12:   */ public final class GetAskOrders
/* 13:   */   extends APIServlet.APIRequestHandler
/* 14:   */ {
/* 15:14 */   static final GetAskOrders instance = new GetAskOrders();
/* 16:   */   
/* 17:   */   private GetAskOrders()
/* 18:   */   {
/* 19:17 */     super(new APITag[] { APITag.AE }, new String[] { "asset", "firstIndex", "lastIndex" });
/* 20:   */   }
/* 21:   */   
/* 22:   */   JSONStreamAware processRequest(HttpServletRequest paramHttpServletRequest)
/* 23:   */     throws NxtException
/* 24:   */   {
/* 25:23 */     long l = ParameterParser.getAsset(paramHttpServletRequest).getId();
/* 26:24 */     int i = ParameterParser.getFirstIndex(paramHttpServletRequest);
/* 27:25 */     int j = ParameterParser.getLastIndex(paramHttpServletRequest);
/* 28:   */     
/* 29:27 */     JSONArray localJSONArray = new JSONArray();
/* 30:28 */     Object localObject1 = Order.Ask.getSortedOrders(l, i, j);Object localObject2 = null;
/* 31:   */     try
/* 32:   */     {
/* 33:29 */       while (((DbIterator)localObject1).hasNext()) {
/* 34:30 */         localJSONArray.add(JSONData.askOrder((Order.Ask)((DbIterator)localObject1).next()));
/* 35:   */       }
/* 36:   */     }
/* 37:   */     catch (Throwable localThrowable2)
/* 38:   */     {
/* 39:28 */       localObject2 = localThrowable2;throw localThrowable2;
/* 40:   */     }
/* 41:   */     finally
/* 42:   */     {
/* 43:32 */       if (localObject1 != null) {
/* 44:32 */         if (localObject2 != null) {
/* 45:   */           try
/* 46:   */           {
/* 47:32 */             ((DbIterator)localObject1).close();
/* 48:   */           }
/* 49:   */           catch (Throwable localThrowable3)
/* 50:   */           {
/* 51:32 */             ((Throwable)localObject2).addSuppressed(localThrowable3);
/* 52:   */           }
/* 53:   */         } else {
/* 54:32 */           ((DbIterator)localObject1).close();
/* 55:   */         }
/* 56:   */       }
/* 57:   */     }
/* 58:34 */     localObject1 = new JSONObject();
/* 59:35 */     ((JSONObject)localObject1).put("askOrders", localJSONArray);
/* 60:36 */     return (JSONStreamAware)localObject1;
/* 61:   */   }
/* 62:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.http.GetAskOrders
 * JD-Core Version:    0.7.1
 */