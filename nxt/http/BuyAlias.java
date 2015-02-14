/*  1:   */ package nxt.http;
/*  2:   */ 
/*  3:   */ import javax.servlet.http.HttpServletRequest;
/*  4:   */ import nxt.Account;
/*  5:   */ import nxt.Alias;
/*  6:   */ import nxt.Attachment.MessagingAliasBuy;
/*  7:   */ import nxt.NxtException;
/*  8:   */ import org.json.simple.JSONStreamAware;
/*  9:   */ 
/* 10:   */ public final class BuyAlias
/* 11:   */   extends CreateTransaction
/* 12:   */ {
/* 13:16 */   static final BuyAlias instance = new BuyAlias();
/* 14:   */   
/* 15:   */   private BuyAlias()
/* 16:   */   {
/* 17:19 */     super(new APITag[] { APITag.ALIASES, APITag.CREATE_TRANSACTION }, new String[] { "alias", "aliasName" });
/* 18:   */   }
/* 19:   */   
/* 20:   */   JSONStreamAware processRequest(HttpServletRequest paramHttpServletRequest)
/* 21:   */     throws NxtException
/* 22:   */   {
/* 23:24 */     Account localAccount = ParameterParser.getSenderAccount(paramHttpServletRequest);
/* 24:25 */     Alias localAlias = ParameterParser.getAlias(paramHttpServletRequest);
/* 25:26 */     long l1 = ParameterParser.getAmountNQT(paramHttpServletRequest);
/* 26:27 */     if (Alias.getOffer(localAlias) == null) {
/* 27:28 */       return JSONResponses.INCORRECT_ALIAS_NOTFORSALE;
/* 28:   */     }
/* 29:30 */     long l2 = localAlias.getAccountId();
/* 30:31 */     Attachment.MessagingAliasBuy localMessagingAliasBuy = new Attachment.MessagingAliasBuy(localAlias.getAliasName());
/* 31:32 */     return createTransaction(paramHttpServletRequest, localAccount, Long.valueOf(l2), l1, localMessagingAliasBuy);
/* 32:   */   }
/* 33:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.http.BuyAlias
 * JD-Core Version:    0.7.1
 */