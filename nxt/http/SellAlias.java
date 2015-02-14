/*  1:   */ package nxt.http;
/*  2:   */ 
/*  3:   */ import javax.servlet.http.HttpServletRequest;
/*  4:   */ import nxt.Account;
/*  5:   */ import nxt.Alias;
/*  6:   */ import nxt.Attachment.MessagingAliasSell;
/*  7:   */ import nxt.NxtException;
/*  8:   */ import nxt.util.Convert;
/*  9:   */ import org.json.simple.JSONStreamAware;
/* 10:   */ 
/* 11:   */ public final class SellAlias
/* 12:   */   extends CreateTransaction
/* 13:   */ {
/* 14:21 */   static final SellAlias instance = new SellAlias();
/* 15:   */   
/* 16:   */   private SellAlias()
/* 17:   */   {
/* 18:24 */     super(new APITag[] { APITag.ALIASES, APITag.CREATE_TRANSACTION }, new String[] { "alias", "aliasName", "recipient", "priceNQT" });
/* 19:   */   }
/* 20:   */   
/* 21:   */   JSONStreamAware processRequest(HttpServletRequest paramHttpServletRequest)
/* 22:   */     throws NxtException
/* 23:   */   {
/* 24:29 */     Alias localAlias = ParameterParser.getAlias(paramHttpServletRequest);
/* 25:30 */     Account localAccount = ParameterParser.getSenderAccount(paramHttpServletRequest);
/* 26:   */     
/* 27:32 */     String str1 = Convert.emptyToNull(paramHttpServletRequest.getParameter("priceNQT"));
/* 28:33 */     if (str1 == null) {
/* 29:34 */       return JSONResponses.MISSING_PRICE;
/* 30:   */     }
/* 31:   */     long l1;
/* 32:   */     try
/* 33:   */     {
/* 34:38 */       l1 = Long.parseLong(str1);
/* 35:   */     }
/* 36:   */     catch (RuntimeException localRuntimeException1)
/* 37:   */     {
/* 38:40 */       return JSONResponses.INCORRECT_PRICE;
/* 39:   */     }
/* 40:42 */     if ((l1 < 0L) || (l1 > 215881280000000000L)) {
/* 41:43 */       throw new ParameterException(JSONResponses.INCORRECT_PRICE);
/* 42:   */     }
/* 43:46 */     String str2 = Convert.emptyToNull(paramHttpServletRequest.getParameter("recipient"));
/* 44:47 */     long l2 = 0L;
/* 45:48 */     if (str2 != null)
/* 46:   */     {
/* 47:   */       try
/* 48:   */       {
/* 49:50 */         l2 = Convert.parseAccountId(str2);
/* 50:   */       }
/* 51:   */       catch (RuntimeException localRuntimeException2)
/* 52:   */       {
/* 53:52 */         return JSONResponses.INCORRECT_RECIPIENT;
/* 54:   */       }
/* 55:54 */       if (l2 == 0L) {
/* 56:55 */         return JSONResponses.INCORRECT_RECIPIENT;
/* 57:   */       }
/* 58:   */     }
/* 59:59 */     if (localAlias.getAccountId() != localAccount.getId()) {
/* 60:60 */       return JSONResponses.INCORRECT_ALIAS_OWNER;
/* 61:   */     }
/* 62:63 */     Attachment.MessagingAliasSell localMessagingAliasSell = new Attachment.MessagingAliasSell(localAlias.getAliasName(), l1);
/* 63:64 */     return createTransaction(paramHttpServletRequest, localAccount, Long.valueOf(l2), 0L, localMessagingAliasSell);
/* 64:   */   }
/* 65:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.http.SellAlias
 * JD-Core Version:    0.7.1
 */