/*  1:   */ package nxt.http;
/*  2:   */ 
/*  3:   */ import javax.servlet.http.HttpServletRequest;
/*  4:   */ import nxt.Account;
/*  5:   */ import nxt.Attachment.DigitalGoodsFeedback;
/*  6:   */ import nxt.DigitalGoodsStore.Purchase;
/*  7:   */ import nxt.NxtException;
/*  8:   */ import org.json.simple.JSONStreamAware;
/*  9:   */ 
/* 10:   */ public final class DGSFeedback
/* 11:   */   extends CreateTransaction
/* 12:   */ {
/* 13:16 */   static final DGSFeedback instance = new DGSFeedback();
/* 14:   */   
/* 15:   */   private DGSFeedback()
/* 16:   */   {
/* 17:19 */     super(new APITag[] { APITag.DGS, APITag.CREATE_TRANSACTION }, new String[] { "purchase" });
/* 18:   */   }
/* 19:   */   
/* 20:   */   JSONStreamAware processRequest(HttpServletRequest paramHttpServletRequest)
/* 21:   */     throws NxtException
/* 22:   */   {
/* 23:26 */     DigitalGoodsStore.Purchase localPurchase = ParameterParser.getPurchase(paramHttpServletRequest);
/* 24:   */     
/* 25:28 */     Account localAccount1 = ParameterParser.getSenderAccount(paramHttpServletRequest);
/* 26:29 */     if (localAccount1.getId() != localPurchase.getBuyerId()) {
/* 27:30 */       return JSONResponses.INCORRECT_PURCHASE;
/* 28:   */     }
/* 29:32 */     if (localPurchase.getEncryptedGoods() == null) {
/* 30:33 */       return JSONResponses.GOODS_NOT_DELIVERED;
/* 31:   */     }
/* 32:36 */     Account localAccount2 = Account.getAccount(localPurchase.getSellerId());
/* 33:37 */     Attachment.DigitalGoodsFeedback localDigitalGoodsFeedback = new Attachment.DigitalGoodsFeedback(localPurchase.getId());
/* 34:38 */     return createTransaction(paramHttpServletRequest, localAccount1, Long.valueOf(localAccount2.getId()), 0L, localDigitalGoodsFeedback);
/* 35:   */   }
/* 36:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.http.DGSFeedback
 * JD-Core Version:    0.7.1
 */