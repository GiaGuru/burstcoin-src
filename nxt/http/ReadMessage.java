/*  1:   */ package nxt.http;
/*  2:   */ 
/*  3:   */ import javax.servlet.http.HttpServletRequest;
/*  4:   */ import nxt.Account;
/*  5:   */ import nxt.Appendix.EncryptToSelfMessage;
/*  6:   */ import nxt.Appendix.EncryptedMessage;
/*  7:   */ import nxt.Appendix.Message;
/*  8:   */ import nxt.Blockchain;
/*  9:   */ import nxt.Nxt;
/* 10:   */ import nxt.Transaction;
/* 11:   */ import nxt.crypto.Crypto;
/* 12:   */ import nxt.util.Convert;
/* 13:   */ import nxt.util.Logger;
/* 14:   */ import org.json.simple.JSONObject;
/* 15:   */ import org.json.simple.JSONStreamAware;
/* 16:   */ 
/* 17:   */ public final class ReadMessage
/* 18:   */   extends APIServlet.APIRequestHandler
/* 19:   */ {
/* 20:22 */   static final ReadMessage instance = new ReadMessage();
/* 21:   */   
/* 22:   */   private ReadMessage()
/* 23:   */   {
/* 24:25 */     super(new APITag[] { APITag.MESSAGES }, new String[] { "transaction", "secretPhrase" });
/* 25:   */   }
/* 26:   */   
/* 27:   */   JSONStreamAware processRequest(HttpServletRequest paramHttpServletRequest)
/* 28:   */     throws ParameterException
/* 29:   */   {
/* 30:31 */     String str1 = Convert.emptyToNull(paramHttpServletRequest.getParameter("transaction"));
/* 31:32 */     if (str1 == null) {
/* 32:33 */       return JSONResponses.MISSING_TRANSACTION;
/* 33:   */     }
/* 34:   */     Transaction localTransaction;
/* 35:   */     try
/* 36:   */     {
/* 37:38 */       localTransaction = Nxt.getBlockchain().getTransaction(Convert.parseUnsignedLong(str1));
/* 38:39 */       if (localTransaction == null) {
/* 39:40 */         return JSONResponses.UNKNOWN_TRANSACTION;
/* 40:   */       }
/* 41:   */     }
/* 42:   */     catch (RuntimeException localRuntimeException1)
/* 43:   */     {
/* 44:43 */       return JSONResponses.INCORRECT_TRANSACTION;
/* 45:   */     }
/* 46:46 */     JSONObject localJSONObject = new JSONObject();
/* 47:47 */     Account localAccount1 = Account.getAccount(localTransaction.getSenderId());
/* 48:48 */     Appendix.Message localMessage = localTransaction.getMessage();
/* 49:49 */     Appendix.EncryptedMessage localEncryptedMessage = localTransaction.getEncryptedMessage();
/* 50:50 */     Appendix.EncryptToSelfMessage localEncryptToSelfMessage = localTransaction.getEncryptToSelfMessage();
/* 51:51 */     if ((localMessage == null) && (localEncryptedMessage == null) && (localEncryptToSelfMessage == null)) {
/* 52:52 */       return JSONResponses.NO_MESSAGE;
/* 53:   */     }
/* 54:54 */     if (localMessage != null) {
/* 55:55 */       localJSONObject.put("message", localMessage.isText() ? Convert.toString(localMessage.getMessage()) : Convert.toHexString(localMessage.getMessage()));
/* 56:   */     }
/* 57:57 */     String str2 = Convert.emptyToNull(paramHttpServletRequest.getParameter("secretPhrase"));
/* 58:58 */     if (str2 != null)
/* 59:   */     {
/* 60:59 */       if (localEncryptedMessage != null)
/* 61:   */       {
/* 62:60 */         long l = Account.getId(Crypto.getPublicKey(str2));
/* 63:61 */         Account localAccount3 = localAccount1.getId() == l ? Account.getAccount(localTransaction.getRecipientId()) : localAccount1;
/* 64:62 */         if (localAccount3 != null) {
/* 65:   */           try
/* 66:   */           {
/* 67:64 */             byte[] arrayOfByte2 = localAccount3.decryptFrom(localEncryptedMessage.getEncryptedData(), str2);
/* 68:65 */             localJSONObject.put("decryptedMessage", localEncryptedMessage.isText() ? Convert.toString(arrayOfByte2) : Convert.toHexString(arrayOfByte2));
/* 69:   */           }
/* 70:   */           catch (RuntimeException localRuntimeException3)
/* 71:   */           {
/* 72:67 */             Logger.logDebugMessage("Decryption of message to recipient failed: " + localRuntimeException3.toString());
/* 73:   */           }
/* 74:   */         }
/* 75:   */       }
/* 76:71 */       if (localEncryptToSelfMessage != null)
/* 77:   */       {
/* 78:72 */         Account localAccount2 = Account.getAccount(Crypto.getPublicKey(str2));
/* 79:73 */         if (localAccount2 != null) {
/* 80:   */           try
/* 81:   */           {
/* 82:75 */             byte[] arrayOfByte1 = localAccount2.decryptFrom(localEncryptToSelfMessage.getEncryptedData(), str2);
/* 83:76 */             localJSONObject.put("decryptedMessageToSelf", localEncryptToSelfMessage.isText() ? Convert.toString(arrayOfByte1) : Convert.toHexString(arrayOfByte1));
/* 84:   */           }
/* 85:   */           catch (RuntimeException localRuntimeException2)
/* 86:   */           {
/* 87:78 */             Logger.logDebugMessage("Decryption of message to self failed: " + localRuntimeException2.toString());
/* 88:   */           }
/* 89:   */         }
/* 90:   */       }
/* 91:   */     }
/* 92:83 */     return localJSONObject;
/* 93:   */   }
/* 94:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.http.ReadMessage
 * JD-Core Version:    0.7.1
 */