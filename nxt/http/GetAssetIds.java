/*  1:   */ package nxt.http;
/*  2:   */ 
/*  3:   */ import javax.servlet.http.HttpServletRequest;
/*  4:   */ import nxt.Asset;
/*  5:   */ import nxt.db.DbIterator;
/*  6:   */ import nxt.util.Convert;
/*  7:   */ import org.json.simple.JSONArray;
/*  8:   */ import org.json.simple.JSONObject;
/*  9:   */ import org.json.simple.JSONStreamAware;
/* 10:   */ 
/* 11:   */ public final class GetAssetIds
/* 12:   */   extends APIServlet.APIRequestHandler
/* 13:   */ {
/* 14:14 */   static final GetAssetIds instance = new GetAssetIds();
/* 15:   */   
/* 16:   */   private GetAssetIds()
/* 17:   */   {
/* 18:17 */     super(new APITag[] { APITag.AE }, new String[] { "firstIndex", "lastIndex" });
/* 19:   */   }
/* 20:   */   
/* 21:   */   JSONStreamAware processRequest(HttpServletRequest paramHttpServletRequest)
/* 22:   */   {
/* 23:23 */     int i = ParameterParser.getFirstIndex(paramHttpServletRequest);
/* 24:24 */     int j = ParameterParser.getLastIndex(paramHttpServletRequest);
/* 25:   */     
/* 26:26 */     JSONArray localJSONArray = new JSONArray();
/* 27:27 */     Object localObject1 = Asset.getAllAssets(i, j);Object localObject2 = null;
/* 28:   */     try
/* 29:   */     {
/* 30:28 */       while (((DbIterator)localObject1).hasNext()) {
/* 31:29 */         localJSONArray.add(Convert.toUnsignedLong(((Asset)((DbIterator)localObject1).next()).getId()));
/* 32:   */       }
/* 33:   */     }
/* 34:   */     catch (Throwable localThrowable2)
/* 35:   */     {
/* 36:27 */       localObject2 = localThrowable2;throw localThrowable2;
/* 37:   */     }
/* 38:   */     finally
/* 39:   */     {
/* 40:31 */       if (localObject1 != null) {
/* 41:31 */         if (localObject2 != null) {
/* 42:   */           try
/* 43:   */           {
/* 44:31 */             ((DbIterator)localObject1).close();
/* 45:   */           }
/* 46:   */           catch (Throwable localThrowable3)
/* 47:   */           {
/* 48:31 */             ((Throwable)localObject2).addSuppressed(localThrowable3);
/* 49:   */           }
/* 50:   */         } else {
/* 51:31 */           ((DbIterator)localObject1).close();
/* 52:   */         }
/* 53:   */       }
/* 54:   */     }
/* 55:32 */     localObject1 = new JSONObject();
/* 56:33 */     ((JSONObject)localObject1).put("assetIds", localJSONArray);
/* 57:34 */     return (JSONStreamAware)localObject1;
/* 58:   */   }
/* 59:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.http.GetAssetIds
 * JD-Core Version:    0.7.1
 */