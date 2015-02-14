/*  1:   */ package nxt.http;
/*  2:   */ 
/*  3:   */ import javax.servlet.http.HttpServletRequest;
/*  4:   */ import nxt.Asset;
/*  5:   */ import nxt.NxtException;
/*  6:   */ import nxt.Order.Ask;
/*  7:   */ import nxt.db.DbIterator;
/*  8:   */ import nxt.util.Convert;
/*  9:   */ import org.json.simple.JSONArray;
/* 10:   */ import org.json.simple.JSONObject;
/* 11:   */ import org.json.simple.JSONStreamAware;
/* 12:   */ 
/* 13:   */ public final class GetAskOrderIds
/* 14:   */   extends APIServlet.APIRequestHandler
/* 15:   */ {
/* 16:15 */   static final GetAskOrderIds instance = new GetAskOrderIds();
/* 17:   */   
/* 18:   */   private GetAskOrderIds()
/* 19:   */   {
/* 20:18 */     super(new APITag[] { APITag.AE }, new String[] { "asset", "firstIndex", "lastIndex" });
/* 21:   */   }
/* 22:   */   
/* 23:   */   JSONStreamAware processRequest(HttpServletRequest paramHttpServletRequest)
/* 24:   */     throws NxtException
/* 25:   */   {
/* 26:24 */     long l = ParameterParser.getAsset(paramHttpServletRequest).getId();
/* 27:25 */     int i = ParameterParser.getFirstIndex(paramHttpServletRequest);
/* 28:26 */     int j = ParameterParser.getLastIndex(paramHttpServletRequest);
/* 29:   */     
/* 30:28 */     JSONArray localJSONArray = new JSONArray();
/* 31:29 */     Object localObject1 = Order.Ask.getSortedOrders(l, i, j);Object localObject2 = null;
/* 32:   */     try
/* 33:   */     {
/* 34:30 */       while (((DbIterator)localObject1).hasNext()) {
/* 35:31 */         localJSONArray.add(Convert.toUnsignedLong(((Order.Ask)((DbIterator)localObject1).next()).getId()));
/* 36:   */       }
/* 37:   */     }
/* 38:   */     catch (Throwable localThrowable2)
/* 39:   */     {
/* 40:29 */       localObject2 = localThrowable2;throw localThrowable2;
/* 41:   */     }
/* 42:   */     finally
/* 43:   */     {
/* 44:33 */       if (localObject1 != null) {
/* 45:33 */         if (localObject2 != null) {
/* 46:   */           try
/* 47:   */           {
/* 48:33 */             ((DbIterator)localObject1).close();
/* 49:   */           }
/* 50:   */           catch (Throwable localThrowable3)
/* 51:   */           {
/* 52:33 */             ((Throwable)localObject2).addSuppressed(localThrowable3);
/* 53:   */           }
/* 54:   */         } else {
/* 55:33 */           ((DbIterator)localObject1).close();
/* 56:   */         }
/* 57:   */       }
/* 58:   */     }
/* 59:35 */     localObject1 = new JSONObject();
/* 60:36 */     ((JSONObject)localObject1).put("askOrderIds", localJSONArray);
/* 61:37 */     return (JSONStreamAware)localObject1;
/* 62:   */   }
/* 63:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.http.GetAskOrderIds
 * JD-Core Version:    0.7.1
 */