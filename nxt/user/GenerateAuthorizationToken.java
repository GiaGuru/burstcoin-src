/*  1:   */ package nxt.user;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import javax.servlet.http.HttpServletRequest;
/*  5:   */ import nxt.Token;
/*  6:   */ import org.json.simple.JSONObject;
/*  7:   */ import org.json.simple.JSONStreamAware;
/*  8:   */ 
/*  9:   */ public final class GenerateAuthorizationToken
/* 10:   */   extends UserServlet.UserRequestHandler
/* 11:   */ {
/* 12:14 */   static final GenerateAuthorizationToken instance = new GenerateAuthorizationToken();
/* 13:   */   
/* 14:   */   JSONStreamAware processRequest(HttpServletRequest paramHttpServletRequest, User paramUser)
/* 15:   */     throws IOException
/* 16:   */   {
/* 17:20 */     String str1 = paramHttpServletRequest.getParameter("secretPhrase");
/* 18:21 */     if (!paramUser.getSecretPhrase().equals(str1)) {
/* 19:22 */       return JSONResponses.INVALID_SECRET_PHRASE;
/* 20:   */     }
/* 21:25 */     String str2 = Token.generateToken(str1, paramHttpServletRequest.getParameter("website").trim());
/* 22:   */     
/* 23:27 */     JSONObject localJSONObject = new JSONObject();
/* 24:28 */     localJSONObject.put("response", "showAuthorizationToken");
/* 25:29 */     localJSONObject.put("token", str2);
/* 26:   */     
/* 27:31 */     return localJSONObject;
/* 28:   */   }
/* 29:   */   
/* 30:   */   boolean requirePost()
/* 31:   */   {
/* 32:36 */     return true;
/* 33:   */   }
/* 34:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.user.GenerateAuthorizationToken
 * JD-Core Version:    0.7.1
 */