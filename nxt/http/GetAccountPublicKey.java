/*  1:   */ package nxt.http;
/*  2:   */ 
/*  3:   */ import javax.servlet.http.HttpServletRequest;
/*  4:   */ import nxt.Account;
/*  5:   */ import nxt.NxtException;
/*  6:   */ import nxt.util.Convert;
/*  7:   */ import nxt.util.JSON;
/*  8:   */ import org.json.simple.JSONObject;
/*  9:   */ import org.json.simple.JSONStreamAware;
/* 10:   */ 
/* 11:   */ public final class GetAccountPublicKey
/* 12:   */   extends APIServlet.APIRequestHandler
/* 13:   */ {
/* 14:14 */   static final GetAccountPublicKey instance = new GetAccountPublicKey();
/* 15:   */   
/* 16:   */   private GetAccountPublicKey()
/* 17:   */   {
/* 18:17 */     super(new APITag[] { APITag.ACCOUNTS }, new String[] { "account" });
/* 19:   */   }
/* 20:   */   
/* 21:   */   JSONStreamAware processRequest(HttpServletRequest paramHttpServletRequest)
/* 22:   */     throws NxtException
/* 23:   */   {
/* 24:23 */     Account localAccount = ParameterParser.getAccount(paramHttpServletRequest);
/* 25:25 */     if (localAccount.getPublicKey() != null)
/* 26:   */     {
/* 27:26 */       JSONObject localJSONObject = new JSONObject();
/* 28:27 */       localJSONObject.put("publicKey", Convert.toHexString(localAccount.getPublicKey()));
/* 29:28 */       return localJSONObject;
/* 30:   */     }
/* 31:30 */     return JSON.emptyJSON;
/* 32:   */   }
/* 33:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.http.GetAccountPublicKey
 * JD-Core Version:    0.7.1
 */