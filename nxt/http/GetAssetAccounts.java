/*  1:   */ package nxt.http;
/*  2:   */ 
/*  3:   */ import javax.servlet.http.HttpServletRequest;
/*  4:   */ import nxt.Account.AccountAsset;
/*  5:   */ import nxt.Asset;
/*  6:   */ import nxt.NxtException;
/*  7:   */ import nxt.db.DbIterator;
/*  8:   */ import org.json.simple.JSONArray;
/*  9:   */ import org.json.simple.JSONObject;
/* 10:   */ import org.json.simple.JSONStreamAware;
/* 11:   */ 
/* 12:   */ public final class GetAssetAccounts
/* 13:   */   extends APIServlet.APIRequestHandler
/* 14:   */ {
/* 15:15 */   static final GetAssetAccounts instance = new GetAssetAccounts();
/* 16:   */   
/* 17:   */   private GetAssetAccounts()
/* 18:   */   {
/* 19:18 */     super(new APITag[] { APITag.AE }, new String[] { "asset", "height", "firstIndex", "lastIndex" });
/* 20:   */   }
/* 21:   */   
/* 22:   */   JSONStreamAware processRequest(HttpServletRequest paramHttpServletRequest)
/* 23:   */     throws NxtException
/* 24:   */   {
/* 25:24 */     Asset localAsset = ParameterParser.getAsset(paramHttpServletRequest);
/* 26:25 */     int i = ParameterParser.getFirstIndex(paramHttpServletRequest);
/* 27:26 */     int j = ParameterParser.getLastIndex(paramHttpServletRequest);
/* 28:27 */     int k = ParameterParser.getHeight(paramHttpServletRequest);
/* 29:   */     
/* 30:29 */     JSONArray localJSONArray = new JSONArray();
/* 31:30 */     Object localObject1 = localAsset.getAccounts(k, i, j);Object localObject2 = null;
/* 32:   */     try
/* 33:   */     {
/* 34:31 */       while (((DbIterator)localObject1).hasNext())
/* 35:   */       {
/* 36:32 */         Account.AccountAsset localAccountAsset = (Account.AccountAsset)((DbIterator)localObject1).next();
/* 37:33 */         localJSONArray.add(JSONData.accountAsset(localAccountAsset));
/* 38:   */       }
/* 39:   */     }
/* 40:   */     catch (Throwable localThrowable2)
/* 41:   */     {
/* 42:30 */       localObject2 = localThrowable2;throw localThrowable2;
/* 43:   */     }
/* 44:   */     finally
/* 45:   */     {
/* 46:35 */       if (localObject1 != null) {
/* 47:35 */         if (localObject2 != null) {
/* 48:   */           try
/* 49:   */           {
/* 50:35 */             ((DbIterator)localObject1).close();
/* 51:   */           }
/* 52:   */           catch (Throwable localThrowable3)
/* 53:   */           {
/* 54:35 */             ((Throwable)localObject2).addSuppressed(localThrowable3);
/* 55:   */           }
/* 56:   */         } else {
/* 57:35 */           ((DbIterator)localObject1).close();
/* 58:   */         }
/* 59:   */       }
/* 60:   */     }
/* 61:37 */     localObject1 = new JSONObject();
/* 62:38 */     ((JSONObject)localObject1).put("accountAssets", localJSONArray);
/* 63:39 */     return (JSONStreamAware)localObject1;
/* 64:   */   }
/* 65:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.http.GetAssetAccounts
 * JD-Core Version:    0.7.1
 */