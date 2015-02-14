/*  1:   */ package nxt.http;
/*  2:   */ 
/*  3:   */ import javax.servlet.http.HttpServletRequest;
/*  4:   */ import nxt.Account;
/*  5:   */ import nxt.Asset;
/*  6:   */ import nxt.AssetTransfer;
/*  7:   */ import nxt.NxtException;
/*  8:   */ import nxt.db.DbIterator;
/*  9:   */ import nxt.db.DbUtils;
/* 10:   */ import nxt.util.Convert;
/* 11:   */ import org.json.simple.JSONArray;
/* 12:   */ import org.json.simple.JSONObject;
/* 13:   */ import org.json.simple.JSONStreamAware;
/* 14:   */ 
/* 15:   */ public final class GetAssetTransfers
/* 16:   */   extends APIServlet.APIRequestHandler
/* 17:   */ {
/* 18:18 */   static final GetAssetTransfers instance = new GetAssetTransfers();
/* 19:   */   
/* 20:   */   private GetAssetTransfers()
/* 21:   */   {
/* 22:21 */     super(new APITag[] { APITag.AE }, new String[] { "asset", "account", "firstIndex", "lastIndex", "includeAssetInfo" });
/* 23:   */   }
/* 24:   */   
/* 25:   */   JSONStreamAware processRequest(HttpServletRequest paramHttpServletRequest)
/* 26:   */     throws NxtException
/* 27:   */   {
/* 28:27 */     String str1 = Convert.emptyToNull(paramHttpServletRequest.getParameter("asset"));
/* 29:28 */     String str2 = Convert.emptyToNull(paramHttpServletRequest.getParameter("account"));
/* 30:   */     
/* 31:30 */     int i = ParameterParser.getFirstIndex(paramHttpServletRequest);
/* 32:31 */     int j = ParameterParser.getLastIndex(paramHttpServletRequest);
/* 33:32 */     boolean bool = !"false".equalsIgnoreCase(paramHttpServletRequest.getParameter("includeAssetInfo"));
/* 34:   */     
/* 35:34 */     JSONObject localJSONObject = new JSONObject();
/* 36:35 */     JSONArray localJSONArray = new JSONArray();
/* 37:36 */     DbIterator localDbIterator = null;
/* 38:   */     try
/* 39:   */     {
/* 40:   */       Object localObject1;
/* 41:38 */       if (str2 == null)
/* 42:   */       {
/* 43:39 */         localObject1 = ParameterParser.getAsset(paramHttpServletRequest);
/* 44:40 */         localDbIterator = ((Asset)localObject1).getAssetTransfers(i, j);
/* 45:   */       }
/* 46:41 */       else if (str1 == null)
/* 47:   */       {
/* 48:42 */         localObject1 = ParameterParser.getAccount(paramHttpServletRequest);
/* 49:43 */         localDbIterator = ((Account)localObject1).getAssetTransfers(i, j);
/* 50:   */       }
/* 51:   */       else
/* 52:   */       {
/* 53:45 */         localObject1 = ParameterParser.getAsset(paramHttpServletRequest);
/* 54:46 */         Account localAccount = ParameterParser.getAccount(paramHttpServletRequest);
/* 55:47 */         localDbIterator = AssetTransfer.getAccountAssetTransfers(localAccount.getId(), ((Asset)localObject1).getId(), i, j);
/* 56:   */       }
/* 57:49 */       while (localDbIterator.hasNext()) {
/* 58:50 */         localJSONArray.add(JSONData.assetTransfer((AssetTransfer)localDbIterator.next(), bool));
/* 59:   */       }
/* 60:   */     }
/* 61:   */     finally
/* 62:   */     {
/* 63:53 */       DbUtils.close(new AutoCloseable[] { localDbIterator });
/* 64:   */     }
/* 65:55 */     localJSONObject.put("transfers", localJSONArray);
/* 66:   */     
/* 67:57 */     return localJSONObject;
/* 68:   */   }
/* 69:   */   
/* 70:   */   boolean startDbTransaction()
/* 71:   */   {
/* 72:62 */     return true;
/* 73:   */   }
/* 74:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.http.GetAssetTransfers
 * JD-Core Version:    0.7.1
 */