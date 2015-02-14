/*  1:   */ package nxt.http;
/*  2:   */ 
/*  3:   */ import javax.servlet.http.HttpServletRequest;
/*  4:   */ import nxt.Asset;
/*  5:   */ import nxt.db.DbIterator;
/*  6:   */ import org.json.simple.JSONArray;
/*  7:   */ import org.json.simple.JSONObject;
/*  8:   */ import org.json.simple.JSONStreamAware;
/*  9:   */ 
/* 10:   */ public final class GetAllAssets
/* 11:   */   extends APIServlet.APIRequestHandler
/* 12:   */ {
/* 13:13 */   static final GetAllAssets instance = new GetAllAssets();
/* 14:   */   
/* 15:   */   private GetAllAssets()
/* 16:   */   {
/* 17:16 */     super(new APITag[] { APITag.AE }, new String[] { "firstIndex", "lastIndex" });
/* 18:   */   }
/* 19:   */   
/* 20:   */   JSONStreamAware processRequest(HttpServletRequest paramHttpServletRequest)
/* 21:   */   {
/* 22:22 */     int i = ParameterParser.getFirstIndex(paramHttpServletRequest);
/* 23:23 */     int j = ParameterParser.getLastIndex(paramHttpServletRequest);
/* 24:   */     
/* 25:25 */     JSONObject localJSONObject = new JSONObject();
/* 26:26 */     JSONArray localJSONArray = new JSONArray();
/* 27:27 */     localJSONObject.put("assets", localJSONArray);
/* 28:28 */     DbIterator localDbIterator = Asset.getAllAssets(i, j);Object localObject1 = null;
/* 29:   */     try
/* 30:   */     {
/* 31:29 */       while (localDbIterator.hasNext()) {
/* 32:30 */         localJSONArray.add(JSONData.asset((Asset)localDbIterator.next()));
/* 33:   */       }
/* 34:   */     }
/* 35:   */     catch (Throwable localThrowable2)
/* 36:   */     {
/* 37:28 */       localObject1 = localThrowable2;throw localThrowable2;
/* 38:   */     }
/* 39:   */     finally
/* 40:   */     {
/* 41:32 */       if (localDbIterator != null) {
/* 42:32 */         if (localObject1 != null) {
/* 43:   */           try
/* 44:   */           {
/* 45:32 */             localDbIterator.close();
/* 46:   */           }
/* 47:   */           catch (Throwable localThrowable3)
/* 48:   */           {
/* 49:32 */             ((Throwable)localObject1).addSuppressed(localThrowable3);
/* 50:   */           }
/* 51:   */         } else {
/* 52:32 */           localDbIterator.close();
/* 53:   */         }
/* 54:   */       }
/* 55:   */     }
/* 56:33 */     return localJSONObject;
/* 57:   */   }
/* 58:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.http.GetAllAssets
 * JD-Core Version:    0.7.1
 */