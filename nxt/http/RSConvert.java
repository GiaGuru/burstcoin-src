/*  1:   */ package nxt.http;
/*  2:   */ 
/*  3:   */ import javax.servlet.http.HttpServletRequest;
/*  4:   */ import nxt.util.Convert;
/*  5:   */ import org.json.simple.JSONObject;
/*  6:   */ import org.json.simple.JSONStreamAware;
/*  7:   */ 
/*  8:   */ public final class RSConvert
/*  9:   */   extends APIServlet.APIRequestHandler
/* 10:   */ {
/* 11:14 */   static final RSConvert instance = new RSConvert();
/* 12:   */   
/* 13:   */   private RSConvert()
/* 14:   */   {
/* 15:17 */     super(new APITag[] { APITag.ACCOUNTS, APITag.UTILS }, new String[] { "account" });
/* 16:   */   }
/* 17:   */   
/* 18:   */   JSONStreamAware processRequest(HttpServletRequest paramHttpServletRequest)
/* 19:   */   {
/* 20:22 */     String str = Convert.emptyToNull(paramHttpServletRequest.getParameter("account"));
/* 21:23 */     if (str == null) {
/* 22:24 */       return JSONResponses.MISSING_ACCOUNT;
/* 23:   */     }
/* 24:   */     try
/* 25:   */     {
/* 26:27 */       long l = Convert.parseAccountId(str);
/* 27:28 */       if (l == 0L) {
/* 28:29 */         return JSONResponses.INCORRECT_ACCOUNT;
/* 29:   */       }
/* 30:31 */       JSONObject localJSONObject = new JSONObject();
/* 31:32 */       JSONData.putAccount(localJSONObject, "account", l);
/* 32:33 */       return localJSONObject;
/* 33:   */     }
/* 34:   */     catch (RuntimeException localRuntimeException) {}
/* 35:35 */     return JSONResponses.INCORRECT_ACCOUNT;
/* 36:   */   }
/* 37:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.http.RSConvert
 * JD-Core Version:    0.7.1
 */