/*  1:   */ package nxt.http;
/*  2:   */ 
/*  3:   */ import javax.servlet.http.HttpServletRequest;
/*  4:   */ import nxt.Account;
/*  5:   */ import nxt.Alias;
/*  6:   */ import nxt.Attachment;
/*  7:   */ import nxt.Attachment.MessagingAliasAssignment;
/*  8:   */ import nxt.NxtException;
/*  9:   */ import nxt.util.Convert;
/* 10:   */ import org.json.simple.JSONObject;
/* 11:   */ import org.json.simple.JSONStreamAware;
/* 12:   */ 
/* 13:   */ public final class SetAlias
/* 14:   */   extends CreateTransaction
/* 15:   */ {
/* 16:22 */   static final SetAlias instance = new SetAlias();
/* 17:   */   
/* 18:   */   private SetAlias()
/* 19:   */   {
/* 20:25 */     super(new APITag[] { APITag.ALIASES, APITag.CREATE_TRANSACTION }, new String[] { "aliasName", "aliasURI" });
/* 21:   */   }
/* 22:   */   
/* 23:   */   JSONStreamAware processRequest(HttpServletRequest paramHttpServletRequest)
/* 24:   */     throws NxtException
/* 25:   */   {
/* 26:30 */     String str1 = Convert.emptyToNull(paramHttpServletRequest.getParameter("aliasName"));
/* 27:31 */     String str2 = Convert.nullToEmpty(paramHttpServletRequest.getParameter("aliasURI"));
/* 28:33 */     if (str1 == null) {
/* 29:34 */       return JSONResponses.MISSING_ALIAS_NAME;
/* 30:   */     }
/* 31:37 */     str1 = str1.trim();
/* 32:38 */     if ((str1.length() == 0) || (str1.length() > 100)) {
/* 33:39 */       return JSONResponses.INCORRECT_ALIAS_LENGTH;
/* 34:   */     }
/* 35:42 */     String str3 = str1.toLowerCase();
/* 36:43 */     for (int i = 0; i < str3.length(); i++) {
/* 37:44 */       if ("0123456789abcdefghijklmnopqrstuvwxyz".indexOf(str3.charAt(i)) < 0) {
/* 38:45 */         return JSONResponses.INCORRECT_ALIAS_NAME;
/* 39:   */       }
/* 40:   */     }
/* 41:49 */     str2 = str2.trim();
/* 42:50 */     if (str2.length() > 1000) {
/* 43:51 */       return JSONResponses.INCORRECT_URI_LENGTH;
/* 44:   */     }
/* 45:54 */     Account localAccount = ParameterParser.getSenderAccount(paramHttpServletRequest);
/* 46:   */     
/* 47:56 */     Alias localAlias = Alias.getAlias(str3);
/* 48:57 */     if ((localAlias != null) && (localAlias.getAccountId() != localAccount.getId()))
/* 49:   */     {
/* 50:58 */       localObject = new JSONObject();
/* 51:59 */       ((JSONObject)localObject).put("errorCode", Integer.valueOf(8));
/* 52:60 */       ((JSONObject)localObject).put("errorDescription", "\"" + str1 + "\" is already used");
/* 53:61 */       return (JSONStreamAware)localObject;
/* 54:   */     }
/* 55:64 */     Object localObject = new Attachment.MessagingAliasAssignment(str1, str2);
/* 56:65 */     return createTransaction(paramHttpServletRequest, localAccount, (Attachment)localObject);
/* 57:   */   }
/* 58:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.http.SetAlias
 * JD-Core Version:    0.7.1
 */