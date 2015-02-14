/*  1:   */ package nxt.http;
/*  2:   */ 
/*  3:   */ import javax.servlet.http.HttpServletRequest;
/*  4:   */ import nxt.Asset;
/*  5:   */ import nxt.util.Convert;
/*  6:   */ import org.json.simple.JSONArray;
/*  7:   */ import org.json.simple.JSONObject;
/*  8:   */ import org.json.simple.JSONStreamAware;
/*  9:   */ 
/* 10:   */ public final class GetAssets
/* 11:   */   extends APIServlet.APIRequestHandler
/* 12:   */ {
/* 13:16 */   static final GetAssets instance = new GetAssets();
/* 14:   */   
/* 15:   */   private GetAssets()
/* 16:   */   {
/* 17:19 */     super(new APITag[] { APITag.AE }, new String[] { "assets", "assets", "assets" });
/* 18:   */   }
/* 19:   */   
/* 20:   */   JSONStreamAware processRequest(HttpServletRequest paramHttpServletRequest)
/* 21:   */   {
/* 22:25 */     String[] arrayOfString1 = paramHttpServletRequest.getParameterValues("assets");
/* 23:   */     
/* 24:27 */     JSONObject localJSONObject = new JSONObject();
/* 25:28 */     JSONArray localJSONArray = new JSONArray();
/* 26:29 */     localJSONObject.put("assets", localJSONArray);
/* 27:30 */     for (String str : arrayOfString1) {
/* 28:31 */       if ((str != null) && (!str.equals(""))) {
/* 29:   */         try
/* 30:   */         {
/* 31:35 */           Asset localAsset = Asset.getAsset(Convert.parseUnsignedLong(str));
/* 32:36 */           if (localAsset == null) {
/* 33:37 */             return JSONResponses.UNKNOWN_ASSET;
/* 34:   */           }
/* 35:39 */           localJSONArray.add(JSONData.asset(localAsset));
/* 36:   */         }
/* 37:   */         catch (RuntimeException localRuntimeException)
/* 38:   */         {
/* 39:41 */           return JSONResponses.INCORRECT_ASSET;
/* 40:   */         }
/* 41:   */       }
/* 42:   */     }
/* 43:44 */     return localJSONObject;
/* 44:   */   }
/* 45:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.http.GetAssets
 * JD-Core Version:    0.7.1
 */