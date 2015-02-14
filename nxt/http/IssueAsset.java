/*  1:   */ package nxt.http;
/*  2:   */ 
/*  3:   */ import javax.servlet.http.HttpServletRequest;
/*  4:   */ import nxt.Account;
/*  5:   */ import nxt.Attachment.ColoredCoinsAssetIssuance;
/*  6:   */ import nxt.NxtException;
/*  7:   */ import nxt.util.Convert;
/*  8:   */ import org.json.simple.JSONStreamAware;
/*  9:   */ 
/* 10:   */ public final class IssueAsset
/* 11:   */   extends CreateTransaction
/* 12:   */ {
/* 13:20 */   static final IssueAsset instance = new IssueAsset();
/* 14:   */   
/* 15:   */   private IssueAsset()
/* 16:   */   {
/* 17:23 */     super(new APITag[] { APITag.AE, APITag.CREATE_TRANSACTION }, new String[] { "name", "description", "quantityQNT", "decimals" });
/* 18:   */   }
/* 19:   */   
/* 20:   */   JSONStreamAware processRequest(HttpServletRequest paramHttpServletRequest)
/* 21:   */     throws NxtException
/* 22:   */   {
/* 23:29 */     String str1 = paramHttpServletRequest.getParameter("name");
/* 24:30 */     String str2 = paramHttpServletRequest.getParameter("description");
/* 25:31 */     String str3 = Convert.emptyToNull(paramHttpServletRequest.getParameter("decimals"));
/* 26:33 */     if (str1 == null) {
/* 27:34 */       return JSONResponses.MISSING_NAME;
/* 28:   */     }
/* 29:37 */     str1 = str1.trim();
/* 30:38 */     if ((str1.length() < 3) || (str1.length() > 10)) {
/* 31:39 */       return JSONResponses.INCORRECT_ASSET_NAME_LENGTH;
/* 32:   */     }
/* 33:41 */     String str4 = str1.toLowerCase();
/* 34:42 */     for (int i = 0; i < str4.length(); i++) {
/* 35:43 */       if ("0123456789abcdefghijklmnopqrstuvwxyz".indexOf(str4.charAt(i)) < 0) {
/* 36:44 */         return JSONResponses.INCORRECT_ASSET_NAME;
/* 37:   */       }
/* 38:   */     }
/* 39:48 */     if ((str2 != null) && (str2.length() > 1000)) {
/* 40:49 */       return JSONResponses.INCORRECT_ASSET_DESCRIPTION;
/* 41:   */     }
/* 42:52 */     i = 0;
/* 43:53 */     if (str3 != null) {
/* 44:   */       try
/* 45:   */       {
/* 46:55 */         i = Byte.parseByte(str3);
/* 47:56 */         if ((i < 0) || (i > 8)) {
/* 48:57 */           return JSONResponses.INCORRECT_DECIMALS;
/* 49:   */         }
/* 50:   */       }
/* 51:   */       catch (NumberFormatException localNumberFormatException)
/* 52:   */       {
/* 53:60 */         return JSONResponses.INCORRECT_DECIMALS;
/* 54:   */       }
/* 55:   */     }
/* 56:64 */     long l = ParameterParser.getQuantityQNT(paramHttpServletRequest);
/* 57:65 */     Account localAccount = ParameterParser.getSenderAccount(paramHttpServletRequest);
/* 58:66 */     Attachment.ColoredCoinsAssetIssuance localColoredCoinsAssetIssuance = new Attachment.ColoredCoinsAssetIssuance(str1, str2, l, i);
/* 59:67 */     return createTransaction(paramHttpServletRequest, localAccount, localColoredCoinsAssetIssuance);
/* 60:   */   }
/* 61:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.http.IssueAsset
 * JD-Core Version:    0.7.1
 */