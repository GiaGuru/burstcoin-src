/*  1:   */ package nxt.http;
/*  2:   */ 
/*  3:   */ import javax.servlet.http.HttpServletRequest;
/*  4:   */ import nxt.Token;
/*  5:   */ import org.json.simple.JSONObject;
/*  6:   */ import org.json.simple.JSONStreamAware;
/*  7:   */ 
/*  8:   */ public final class GenerateToken
/*  9:   */   extends APIServlet.APIRequestHandler
/* 10:   */ {
/* 11:16 */   static final GenerateToken instance = new GenerateToken();
/* 12:   */   
/* 13:   */   private GenerateToken()
/* 14:   */   {
/* 15:19 */     super(new APITag[] { APITag.TOKENS }, new String[] { "website", "secretPhrase" });
/* 16:   */   }
/* 17:   */   
/* 18:   */   JSONStreamAware processRequest(HttpServletRequest paramHttpServletRequest)
/* 19:   */   {
/* 20:25 */     String str1 = paramHttpServletRequest.getParameter("secretPhrase");
/* 21:26 */     String str2 = paramHttpServletRequest.getParameter("website");
/* 22:27 */     if (str1 == null) {
/* 23:28 */       return JSONResponses.MISSING_SECRET_PHRASE;
/* 24:   */     }
/* 25:29 */     if (str2 == null) {
/* 26:30 */       return JSONResponses.MISSING_WEBSITE;
/* 27:   */     }
/* 28:   */     try
/* 29:   */     {
/* 30:35 */       String str3 = Token.generateToken(str1, str2.trim());
/* 31:   */       
/* 32:37 */       JSONObject localJSONObject = new JSONObject();
/* 33:38 */       localJSONObject.put("token", str3);
/* 34:   */       
/* 35:40 */       return localJSONObject;
/* 36:   */     }
/* 37:   */     catch (RuntimeException localRuntimeException) {}
/* 38:43 */     return JSONResponses.INCORRECT_WEBSITE;
/* 39:   */   }
/* 40:   */   
/* 41:   */   boolean requirePost()
/* 42:   */   {
/* 43:50 */     return true;
/* 44:   */   }
/* 45:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.http.GenerateToken
 * JD-Core Version:    0.7.1
 */