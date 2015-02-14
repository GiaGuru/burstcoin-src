/*  1:   */ package nxt.http;
/*  2:   */ 
/*  3:   */ import javax.servlet.http.HttpServletRequest;
/*  4:   */ import nxt.Account;
/*  5:   */ import nxt.Alias;
/*  6:   */ import nxt.NxtException;
/*  7:   */ import nxt.db.FilteringIterator;
/*  8:   */ import nxt.db.FilteringIterator.Filter;
/*  9:   */ import org.json.simple.JSONArray;
/* 10:   */ import org.json.simple.JSONObject;
/* 11:   */ import org.json.simple.JSONStreamAware;
/* 12:   */ 
/* 13:   */ public final class GetAliases
/* 14:   */   extends APIServlet.APIRequestHandler
/* 15:   */ {
/* 16:14 */   static final GetAliases instance = new GetAliases();
/* 17:   */   
/* 18:   */   private GetAliases()
/* 19:   */   {
/* 20:17 */     super(new APITag[] { APITag.ALIASES }, new String[] { "timestamp", "account", "firstIndex", "lastIndex" });
/* 21:   */   }
/* 22:   */   
/* 23:   */   JSONStreamAware processRequest(HttpServletRequest paramHttpServletRequest)
/* 24:   */     throws NxtException
/* 25:   */   {
/* 26:22 */     final int i = ParameterParser.getTimestamp(paramHttpServletRequest);
/* 27:23 */     long l = ParameterParser.getAccount(paramHttpServletRequest).getId();
/* 28:24 */     int j = ParameterParser.getFirstIndex(paramHttpServletRequest);
/* 29:25 */     int k = ParameterParser.getLastIndex(paramHttpServletRequest);
/* 30:   */     
/* 31:27 */     JSONArray localJSONArray = new JSONArray();
/* 32:28 */     Object localObject1 = new FilteringIterator(Alias.getAliasesByOwner(l, 0, -1), new FilteringIterator.Filter()
/* 33:   */     {
/* 34:   */       public boolean ok(Alias paramAnonymousAlias)
/* 35:   */       {
/* 36:32 */         return paramAnonymousAlias.getTimestamp() >= i;
/* 37:   */       }
/* 38:32 */     }, j, k);Object localObject2 = null;
/* 39:   */     try
/* 40:   */     {
/* 41:35 */       while (((FilteringIterator)localObject1).hasNext()) {
/* 42:36 */         localJSONArray.add(JSONData.alias((Alias)((FilteringIterator)localObject1).next()));
/* 43:   */       }
/* 44:   */     }
/* 45:   */     catch (Throwable localThrowable2)
/* 46:   */     {
/* 47:28 */       localObject2 = localThrowable2;throw localThrowable2;
/* 48:   */     }
/* 49:   */     finally
/* 50:   */     {
/* 51:38 */       if (localObject1 != null) {
/* 52:38 */         if (localObject2 != null) {
/* 53:   */           try
/* 54:   */           {
/* 55:38 */             ((FilteringIterator)localObject1).close();
/* 56:   */           }
/* 57:   */           catch (Throwable localThrowable3)
/* 58:   */           {
/* 59:38 */             ((Throwable)localObject2).addSuppressed(localThrowable3);
/* 60:   */           }
/* 61:   */         } else {
/* 62:38 */           ((FilteringIterator)localObject1).close();
/* 63:   */         }
/* 64:   */       }
/* 65:   */     }
/* 66:40 */     localObject1 = new JSONObject();
/* 67:41 */     ((JSONObject)localObject1).put("aliases", localJSONArray);
/* 68:42 */     return (JSONStreamAware)localObject1;
/* 69:   */   }
/* 70:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.http.GetAliases
 * JD-Core Version:    0.7.1
 */