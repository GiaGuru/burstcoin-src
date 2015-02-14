/*  1:   */ package nxt.http;
/*  2:   */ 
/*  3:   */ import java.util.List;
/*  4:   */ import javax.servlet.http.HttpServletRequest;
/*  5:   */ import nxt.Account;
/*  6:   */ import nxt.Asset;
/*  7:   */ import nxt.db.DbIterator;
/*  8:   */ import org.json.simple.JSONArray;
/*  9:   */ import org.json.simple.JSONObject;
/* 10:   */ import org.json.simple.JSONStreamAware;
/* 11:   */ 
/* 12:   */ public final class GetAssetsByIssuer
/* 13:   */   extends APIServlet.APIRequestHandler
/* 14:   */ {
/* 15:15 */   static final GetAssetsByIssuer instance = new GetAssetsByIssuer();
/* 16:   */   
/* 17:   */   private GetAssetsByIssuer()
/* 18:   */   {
/* 19:18 */     super(new APITag[] { APITag.AE, APITag.ACCOUNTS }, new String[] { "account", "account", "account", "firstIndex", "lastIndex" });
/* 20:   */   }
/* 21:   */   
/* 22:   */   JSONStreamAware processRequest(HttpServletRequest paramHttpServletRequest)
/* 23:   */     throws ParameterException
/* 24:   */   {
/* 25:23 */     List localList = ParameterParser.getAccounts(paramHttpServletRequest);
/* 26:24 */     int i = ParameterParser.getFirstIndex(paramHttpServletRequest);
/* 27:25 */     int j = ParameterParser.getLastIndex(paramHttpServletRequest);
/* 28:   */     
/* 29:27 */     JSONObject localJSONObject = new JSONObject();
/* 30:28 */     JSONArray localJSONArray1 = new JSONArray();
/* 31:29 */     localJSONObject.put("assets", localJSONArray1);
/* 32:30 */     for (Account localAccount : localList)
/* 33:   */     {
/* 34:31 */       JSONArray localJSONArray2 = new JSONArray();
/* 35:32 */       DbIterator localDbIterator = Asset.getAssetsIssuedBy(localAccount.getId(), i, j);Object localObject1 = null;
/* 36:   */       try
/* 37:   */       {
/* 38:33 */         while (localDbIterator.hasNext()) {
/* 39:34 */           localJSONArray2.add(JSONData.asset((Asset)localDbIterator.next()));
/* 40:   */         }
/* 41:   */       }
/* 42:   */       catch (Throwable localThrowable2)
/* 43:   */       {
/* 44:32 */         localObject1 = localThrowable2;throw localThrowable2;
/* 45:   */       }
/* 46:   */       finally
/* 47:   */       {
/* 48:36 */         if (localDbIterator != null) {
/* 49:36 */           if (localObject1 != null) {
/* 50:   */             try
/* 51:   */             {
/* 52:36 */               localDbIterator.close();
/* 53:   */             }
/* 54:   */             catch (Throwable localThrowable3)
/* 55:   */             {
/* 56:36 */               ((Throwable)localObject1).addSuppressed(localThrowable3);
/* 57:   */             }
/* 58:   */           } else {
/* 59:36 */             localDbIterator.close();
/* 60:   */           }
/* 61:   */         }
/* 62:   */       }
/* 63:37 */       localJSONArray1.add(localJSONArray2);
/* 64:   */     }
/* 65:39 */     return localJSONObject;
/* 66:   */   }
/* 67:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.http.GetAssetsByIssuer
 * JD-Core Version:    0.7.1
 */