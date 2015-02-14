/*   1:    */ package nxt.http;
/*   2:    */ 
/*   3:    */ import java.security.MessageDigest;
/*   4:    */ import java.util.Arrays;
/*   5:    */ import javax.servlet.http.HttpServletRequest;
/*   6:    */ import nxt.Account;
/*   7:    */ import nxt.Appendix.EncryptToSelfMessage;
/*   8:    */ import nxt.Appendix.EncryptedMessage;
/*   9:    */ import nxt.Appendix.Message;
/*  10:    */ import nxt.Appendix.PublicKeyAnnouncement;
/*  11:    */ import nxt.Attachment;
/*  12:    */ import nxt.Attachment.ColoredCoinsAssetTransfer;
/*  13:    */ import nxt.Blockchain;
/*  14:    */ import nxt.Nxt;
/*  15:    */ import nxt.NxtException;
/*  16:    */ import nxt.NxtException.NotYetEnabledException;
/*  17:    */ import nxt.NxtException.ValidationException;
/*  18:    */ import nxt.Transaction;
/*  19:    */ import nxt.Transaction.Builder;
/*  20:    */ import nxt.TransactionProcessor;
/*  21:    */ import nxt.TransactionType;
/*  22:    */ import nxt.crypto.Crypto;
/*  23:    */ import nxt.crypto.EncryptedData;
/*  24:    */ import nxt.util.Convert;
/*  25:    */ import org.json.simple.JSONObject;
/*  26:    */ import org.json.simple.JSONStreamAware;
/*  27:    */ 
/*  28:    */ abstract class CreateTransaction
/*  29:    */   extends APIServlet.APIRequestHandler
/*  30:    */ {
/*  31: 30 */   private static final String[] commonParameters = { "secretPhrase", "publicKey", "feeNQT", "deadline", "referencedTransactionFullHash", "broadcast", "message", "messageIsText", "messageToEncrypt", "messageToEncryptIsText", "encryptedMessageData", "encryptedMessageNonce", "messageToEncryptToSelf", "messageToEncryptToSelfIsText", "encryptToSelfMessageData", "encryptToSelfMessageNonce", "recipientPublicKey" };
/*  32:    */   
/*  33:    */   private static String[] addCommonParameters(String[] paramArrayOfString)
/*  34:    */   {
/*  35: 38 */     String[] arrayOfString = (String[])Arrays.copyOf(paramArrayOfString, paramArrayOfString.length + commonParameters.length);
/*  36: 39 */     System.arraycopy(commonParameters, 0, arrayOfString, paramArrayOfString.length, commonParameters.length);
/*  37: 40 */     return arrayOfString;
/*  38:    */   }
/*  39:    */   
/*  40:    */   CreateTransaction(APITag[] paramArrayOfAPITag, String... paramVarArgs)
/*  41:    */   {
/*  42: 44 */     super(paramArrayOfAPITag, addCommonParameters(paramVarArgs));
/*  43:    */   }
/*  44:    */   
/*  45:    */   final JSONStreamAware createTransaction(HttpServletRequest paramHttpServletRequest, Account paramAccount, Attachment paramAttachment)
/*  46:    */     throws NxtException
/*  47:    */   {
/*  48: 49 */     return createTransaction(paramHttpServletRequest, paramAccount, null, 0L, paramAttachment);
/*  49:    */   }
/*  50:    */   
/*  51:    */   final JSONStreamAware createTransaction(HttpServletRequest paramHttpServletRequest, Account paramAccount, Long paramLong, long paramLong1)
/*  52:    */     throws NxtException
/*  53:    */   {
/*  54: 54 */     return createTransaction(paramHttpServletRequest, paramAccount, paramLong, paramLong1, Attachment.ORDINARY_PAYMENT);
/*  55:    */   }
/*  56:    */   
/*  57:    */   final JSONStreamAware createTransaction(HttpServletRequest paramHttpServletRequest, Account paramAccount, Long paramLong, long paramLong1, Attachment paramAttachment)
/*  58:    */     throws NxtException
/*  59:    */   {
/*  60: 60 */     int i = Nxt.getBlockchain().getHeight();
/*  61: 61 */     String str1 = paramHttpServletRequest.getParameter("deadline");
/*  62: 62 */     String str2 = Convert.emptyToNull(paramHttpServletRequest.getParameter("referencedTransactionFullHash"));
/*  63: 63 */     String str3 = Convert.emptyToNull(paramHttpServletRequest.getParameter("referencedTransaction"));
/*  64: 64 */     String str4 = Convert.emptyToNull(paramHttpServletRequest.getParameter("secretPhrase"));
/*  65: 65 */     String str5 = Convert.emptyToNull(paramHttpServletRequest.getParameter("publicKey"));
/*  66: 66 */     int j = !"false".equalsIgnoreCase(paramHttpServletRequest.getParameter("broadcast")) ? 1 : 0;
/*  67: 67 */     Appendix.EncryptedMessage localEncryptedMessage = null;
/*  68: 68 */     if (paramAttachment.getTransactionType().hasRecipient())
/*  69:    */     {
/*  70: 69 */       localObject1 = ParameterParser.getEncryptedMessage(paramHttpServletRequest, Account.getAccount(paramLong.longValue()));
/*  71: 70 */       if (localObject1 != null) {
/*  72: 71 */         localEncryptedMessage = new Appendix.EncryptedMessage((EncryptedData)localObject1, !"false".equalsIgnoreCase(paramHttpServletRequest.getParameter("messageToEncryptIsText")));
/*  73:    */       }
/*  74:    */     }
/*  75: 74 */     Object localObject1 = null;
/*  76: 75 */     EncryptedData localEncryptedData = ParameterParser.getEncryptToSelfMessage(paramHttpServletRequest);
/*  77: 76 */     if (localEncryptedData != null) {
/*  78: 77 */       localObject1 = new Appendix.EncryptToSelfMessage(localEncryptedData, !"false".equalsIgnoreCase(paramHttpServletRequest.getParameter("messageToEncryptToSelfIsText")));
/*  79:    */     }
/*  80: 79 */     Appendix.Message localMessage = null;
/*  81: 80 */     String str6 = Convert.emptyToNull(paramHttpServletRequest.getParameter("message"));
/*  82: 81 */     if (str6 != null)
/*  83:    */     {
/*  84: 82 */       int k = (i >= 11800) && (!"false".equalsIgnoreCase(paramHttpServletRequest.getParameter("messageIsText"))) ? 1 : 0;
/*  85:    */       try
/*  86:    */       {
/*  87: 85 */         localMessage = k != 0 ? new Appendix.Message(str6) : new Appendix.Message(Convert.parseHexString(str6));
/*  88:    */       }
/*  89:    */       catch (RuntimeException localRuntimeException)
/*  90:    */       {
/*  91: 87 */         throw new ParameterException(JSONResponses.INCORRECT_ARBITRARY_MESSAGE);
/*  92:    */       }
/*  93:    */     }
/*  94: 89 */     else if (((paramAttachment instanceof Attachment.ColoredCoinsAssetTransfer)) && (i >= 11800))
/*  95:    */     {
/*  96: 90 */       localObject2 = Convert.emptyToNull(paramHttpServletRequest.getParameter("comment"));
/*  97: 91 */       if (localObject2 != null) {
/*  98: 92 */         localMessage = new Appendix.Message((String)localObject2);
/*  99:    */       }
/* 100:    */     }
/* 101: 94 */     else if ((paramAttachment == Attachment.ARBITRARY_MESSAGE) && (i < 11800))
/* 102:    */     {
/* 103: 95 */       localMessage = new Appendix.Message(new byte[0]);
/* 104:    */     }
/* 105: 97 */     Object localObject2 = null;
/* 106: 98 */     String str7 = Convert.emptyToNull(paramHttpServletRequest.getParameter("recipientPublicKey"));
/* 107: 99 */     if ((str7 != null) && (i >= 11800)) {
/* 108:100 */       localObject2 = new Appendix.PublicKeyAnnouncement(Convert.parseHexString(str7));
/* 109:    */     }
/* 110:103 */     if ((str4 == null) && (str5 == null)) {
/* 111:104 */       return JSONResponses.MISSING_SECRET_PHRASE;
/* 112:    */     }
/* 113:105 */     if (str1 == null) {
/* 114:106 */       return JSONResponses.MISSING_DEADLINE;
/* 115:    */     }
/* 116:    */     short s;
/* 117:    */     try
/* 118:    */     {
/* 119:111 */       s = Short.parseShort(str1);
/* 120:112 */       if ((s < 1) || (s > 1440)) {
/* 121:113 */         return JSONResponses.INCORRECT_DEADLINE;
/* 122:    */       }
/* 123:    */     }
/* 124:    */     catch (NumberFormatException localNumberFormatException)
/* 125:    */     {
/* 126:116 */       return JSONResponses.INCORRECT_DEADLINE;
/* 127:    */     }
/* 128:119 */     long l = ParameterParser.getFeeNQT(paramHttpServletRequest);
/* 129:120 */     if (l < minimumFeeNQT()) {
/* 130:121 */       return JSONResponses.INCORRECT_FEE;
/* 131:    */     }
/* 132:    */     try
/* 133:    */     {
/* 134:125 */       if (Convert.safeAdd(paramLong1, l) > paramAccount.getUnconfirmedBalanceNQT()) {
/* 135:126 */         return JSONResponses.NOT_ENOUGH_FUNDS;
/* 136:    */       }
/* 137:    */     }
/* 138:    */     catch (ArithmeticException localArithmeticException)
/* 139:    */     {
/* 140:129 */       return JSONResponses.NOT_ENOUGH_FUNDS;
/* 141:    */     }
/* 142:132 */     if (str3 != null) {
/* 143:133 */       return JSONResponses.INCORRECT_REFERENCED_TRANSACTION;
/* 144:    */     }
/* 145:136 */     JSONObject localJSONObject = new JSONObject();
/* 146:    */     
/* 147:    */ 
/* 148:139 */     byte[] arrayOfByte = str4 != null ? Crypto.getPublicKey(str4) : Convert.parseHexString(str5);
/* 149:    */     try
/* 150:    */     {
/* 151:142 */       Transaction.Builder localBuilder = Nxt.getTransactionProcessor().newTransactionBuilder(arrayOfByte, paramLong1, l, s, paramAttachment).referencedTransactionFullHash(str2);
/* 152:144 */       if (paramAttachment.getTransactionType().hasRecipient()) {
/* 153:145 */         localBuilder.recipientId(paramLong.longValue());
/* 154:    */       }
/* 155:147 */       if (localEncryptedMessage != null) {
/* 156:148 */         localBuilder.encryptedMessage(localEncryptedMessage);
/* 157:    */       }
/* 158:150 */       if (localMessage != null) {
/* 159:151 */         localBuilder.message(localMessage);
/* 160:    */       }
/* 161:153 */       if (localObject2 != null) {
/* 162:154 */         localBuilder.publicKeyAnnouncement((Appendix.PublicKeyAnnouncement)localObject2);
/* 163:    */       }
/* 164:156 */       if (localObject1 != null) {
/* 165:157 */         localBuilder.encryptToSelfMessage((Appendix.EncryptToSelfMessage)localObject1);
/* 166:    */       }
/* 167:159 */       Transaction localTransaction = localBuilder.build();
/* 168:160 */       localTransaction.validate();
/* 169:162 */       if (str4 != null)
/* 170:    */       {
/* 171:163 */         localTransaction.sign(str4);
/* 172:164 */         localTransaction.validate();
/* 173:165 */         localJSONObject.put("transaction", localTransaction.getStringId());
/* 174:166 */         localJSONObject.put("fullHash", localTransaction.getFullHash());
/* 175:167 */         localJSONObject.put("transactionBytes", Convert.toHexString(localTransaction.getBytes()));
/* 176:168 */         localJSONObject.put("signatureHash", Convert.toHexString(Crypto.sha256().digest(localTransaction.getSignature())));
/* 177:169 */         if (j != 0)
/* 178:    */         {
/* 179:170 */           Nxt.getTransactionProcessor().broadcast(localTransaction);
/* 180:171 */           localJSONObject.put("broadcasted", Boolean.valueOf(true));
/* 181:    */         }
/* 182:    */         else
/* 183:    */         {
/* 184:173 */           localJSONObject.put("broadcasted", Boolean.valueOf(false));
/* 185:    */         }
/* 186:    */       }
/* 187:    */       else
/* 188:    */       {
/* 189:176 */         localJSONObject.put("broadcasted", Boolean.valueOf(false));
/* 190:    */       }
/* 191:178 */       localJSONObject.put("unsignedTransactionBytes", Convert.toHexString(localTransaction.getUnsignedBytes()));
/* 192:179 */       localJSONObject.put("transactionJSON", JSONData.unconfirmedTransaction(localTransaction));
/* 193:    */     }
/* 194:    */     catch (NxtException.NotYetEnabledException localNotYetEnabledException)
/* 195:    */     {
/* 196:182 */       return JSONResponses.FEATURE_NOT_AVAILABLE;
/* 197:    */     }
/* 198:    */     catch (NxtException.ValidationException localValidationException)
/* 199:    */     {
/* 200:184 */       localJSONObject.put("error", localValidationException.getMessage());
/* 201:    */     }
/* 202:186 */     return localJSONObject;
/* 203:    */   }
/* 204:    */   
/* 205:    */   final boolean requirePost()
/* 206:    */   {
/* 207:192 */     return true;
/* 208:    */   }
/* 209:    */   
/* 210:    */   long minimumFeeNQT()
/* 211:    */   {
/* 212:196 */     return 100000000L;
/* 213:    */   }
/* 214:    */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.http.CreateTransaction
 * JD-Core Version:    0.7.1
 */