/*  1:   */ package nxt.http;
/*  2:   */ 
/*  3:   */ import javax.servlet.http.HttpServletRequest;
/*  4:   */ import nxt.Account;
/*  5:   */ import nxt.crypto.Crypto;
/*  6:   */ import nxt.util.Convert;
/*  7:   */ import org.json.simple.JSONObject;
/*  8:   */ import org.json.simple.JSONStreamAware;
/*  9:   */ 
/* 10:   */ public final class GetAccountId
/* 11:   */   extends APIServlet.APIRequestHandler
/* 12:   */ {
/* 13:15 */   static final GetAccountId instance = new GetAccountId();
/* 14:   */   
/* 15:   */   private GetAccountId()
/* 16:   */   {
/* 17:18 */     super(new APITag[] { APITag.ACCOUNTS }, new String[] { "secretPhrase", "publicKey" });
/* 18:   */   }
/* 19:   */   
/* 20:   */   JSONStreamAware processRequest(HttpServletRequest paramHttpServletRequest)
/* 21:   */   {
/* 22:25 */     String str1 = Convert.emptyToNull(paramHttpServletRequest.getParameter("secretPhrase"));
/* 23:26 */     String str2 = Convert.emptyToNull(paramHttpServletRequest.getParameter("publicKey"));
/* 24:   */     long l;
/* 25:27 */     if (str1 != null)
/* 26:   */     {
/* 27:28 */       localObject = Crypto.getPublicKey(str1);
/* 28:29 */       l = Account.getId((byte[])localObject);
/* 29:30 */       str2 = Convert.toHexString((byte[])localObject);
/* 30:   */     }
/* 31:31 */     else if (str2 != null)
/* 32:   */     {
/* 33:32 */       l = Account.getId(Convert.parseHexString(str2));
/* 34:   */     }
/* 35:   */     else
/* 36:   */     {
/* 37:34 */       return JSONResponses.MISSING_SECRET_PHRASE_OR_PUBLIC_KEY;
/* 38:   */     }
/* 39:37 */     Object localObject = new JSONObject();
/* 40:38 */     JSONData.putAccount((JSONObject)localObject, "account", l);
/* 41:39 */     ((JSONObject)localObject).put("publicKey", str2);
/* 42:   */     
/* 43:41 */     return (JSONStreamAware)localObject;
/* 44:   */   }
/* 45:   */   
/* 46:   */   boolean requirePost()
/* 47:   */   {
/* 48:46 */     return true;
/* 49:   */   }
/* 50:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.http.GetAccountId
 * JD-Core Version:    0.7.1
 */