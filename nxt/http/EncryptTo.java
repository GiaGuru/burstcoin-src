/*  1:   */ package nxt.http;
/*  2:   */ 
/*  3:   */ import javax.servlet.http.HttpServletRequest;
/*  4:   */ import nxt.Account;
/*  5:   */ import nxt.NxtException;
/*  6:   */ import nxt.crypto.EncryptedData;
/*  7:   */ import org.json.simple.JSONStreamAware;
/*  8:   */ 
/*  9:   */ public final class EncryptTo
/* 10:   */   extends APIServlet.APIRequestHandler
/* 11:   */ {
/* 12:14 */   static final EncryptTo instance = new EncryptTo();
/* 13:   */   
/* 14:   */   private EncryptTo()
/* 15:   */   {
/* 16:17 */     super(new APITag[] { APITag.MESSAGES }, new String[] { "recipient", "messageToEncrypt", "messageToEncryptIsText", "secretPhrase" });
/* 17:   */   }
/* 18:   */   
/* 19:   */   JSONStreamAware processRequest(HttpServletRequest paramHttpServletRequest)
/* 20:   */     throws NxtException
/* 21:   */   {
/* 22:23 */     long l = ParameterParser.getRecipientId(paramHttpServletRequest);
/* 23:24 */     Account localAccount = Account.getAccount(l);
/* 24:25 */     if ((localAccount == null) || (localAccount.getPublicKey() == null)) {
/* 25:26 */       return JSONResponses.INCORRECT_RECIPIENT;
/* 26:   */     }
/* 27:29 */     EncryptedData localEncryptedData = ParameterParser.getEncryptedMessage(paramHttpServletRequest, localAccount);
/* 28:30 */     return JSONData.encryptedData(localEncryptedData);
/* 29:   */   }
/* 30:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.http.EncryptTo
 * JD-Core Version:    0.7.1
 */