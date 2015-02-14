/*  1:   */ package nxt.http;
/*  2:   */ 
/*  3:   */ import javax.servlet.http.HttpServletRequest;
/*  4:   */ import nxt.Token;
/*  5:   */ import org.json.simple.JSONStreamAware;
/*  6:   */ 
/*  7:   */ public final class DecodeToken
/*  8:   */   extends APIServlet.APIRequestHandler
/*  9:   */ {
/* 10:14 */   static final DecodeToken instance = new DecodeToken();
/* 11:   */   
/* 12:   */   private DecodeToken()
/* 13:   */   {
/* 14:17 */     super(new APITag[] { APITag.TOKENS }, new String[] { "website", "token" });
/* 15:   */   }
/* 16:   */   
/* 17:   */   public JSONStreamAware processRequest(HttpServletRequest paramHttpServletRequest)
/* 18:   */   {
/* 19:23 */     String str1 = paramHttpServletRequest.getParameter("website");
/* 20:24 */     String str2 = paramHttpServletRequest.getParameter("token");
/* 21:25 */     if (str1 == null) {
/* 22:26 */       return JSONResponses.MISSING_WEBSITE;
/* 23:   */     }
/* 24:27 */     if (str2 == null) {
/* 25:28 */       return JSONResponses.MISSING_TOKEN;
/* 26:   */     }
/* 27:   */     try
/* 28:   */     {
/* 29:33 */       Token localToken = Token.parseToken(str2, str1.trim());
/* 30:   */       
/* 31:35 */       return JSONData.token(localToken);
/* 32:   */     }
/* 33:   */     catch (RuntimeException localRuntimeException) {}
/* 34:38 */     return JSONResponses.INCORRECT_WEBSITE;
/* 35:   */   }
/* 36:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.http.DecodeToken
 * JD-Core Version:    0.7.1
 */