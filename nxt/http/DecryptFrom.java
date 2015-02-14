/*  1:   */ package nxt.http;
/*  2:   */ 
/*  3:   */ import javax.servlet.http.HttpServletRequest;
/*  4:   */ import nxt.Account;
/*  5:   */ import nxt.NxtException;
/*  6:   */ import nxt.crypto.EncryptedData;
/*  7:   */ import nxt.util.Convert;
/*  8:   */ import nxt.util.Logger;
/*  9:   */ import org.json.simple.JSONObject;
/* 10:   */ import org.json.simple.JSONStreamAware;
/* 11:   */ 
/* 12:   */ public final class DecryptFrom
/* 13:   */   extends APIServlet.APIRequestHandler
/* 14:   */ {
/* 15:18 */   static final DecryptFrom instance = new DecryptFrom();
/* 16:   */   
/* 17:   */   private DecryptFrom()
/* 18:   */   {
/* 19:21 */     super(new APITag[] { APITag.MESSAGES }, new String[] { "account", "data", "nonce", "decryptedMessageIsText", "secretPhrase" });
/* 20:   */   }
/* 21:   */   
/* 22:   */   JSONStreamAware processRequest(HttpServletRequest paramHttpServletRequest)
/* 23:   */     throws NxtException
/* 24:   */   {
/* 25:27 */     Account localAccount = ParameterParser.getAccount(paramHttpServletRequest);
/* 26:28 */     if (localAccount.getPublicKey() == null) {
/* 27:29 */       return JSONResponses.INCORRECT_ACCOUNT;
/* 28:   */     }
/* 29:31 */     String str = ParameterParser.getSecretPhrase(paramHttpServletRequest);
/* 30:32 */     byte[] arrayOfByte1 = Convert.parseHexString(Convert.nullToEmpty(paramHttpServletRequest.getParameter("data")));
/* 31:33 */     byte[] arrayOfByte2 = Convert.parseHexString(Convert.nullToEmpty(paramHttpServletRequest.getParameter("nonce")));
/* 32:34 */     EncryptedData localEncryptedData = new EncryptedData(arrayOfByte1, arrayOfByte2);
/* 33:35 */     int i = !"false".equalsIgnoreCase(paramHttpServletRequest.getParameter("decryptedMessageIsText")) ? 1 : 0;
/* 34:   */     try
/* 35:   */     {
/* 36:37 */       byte[] arrayOfByte3 = localAccount.decryptFrom(localEncryptedData, str);
/* 37:38 */       JSONObject localJSONObject = new JSONObject();
/* 38:39 */       localJSONObject.put("decryptedMessage", i != 0 ? Convert.toString(arrayOfByte3) : Convert.toHexString(arrayOfByte3));
/* 39:40 */       return localJSONObject;
/* 40:   */     }
/* 41:   */     catch (RuntimeException localRuntimeException)
/* 42:   */     {
/* 43:42 */       Logger.logDebugMessage(localRuntimeException.toString());
/* 44:   */     }
/* 45:43 */     return JSONResponses.DECRYPTION_FAILED;
/* 46:   */   }
/* 47:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.http.DecryptFrom
 * JD-Core Version:    0.7.1
 */