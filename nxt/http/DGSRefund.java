/*  1:   */ package nxt.http;
/*  2:   */ 
/*  3:   */ import javax.servlet.http.HttpServletRequest;
/*  4:   */ import nxt.Account;
/*  5:   */ import nxt.Attachment.DigitalGoodsRefund;
/*  6:   */ import nxt.DigitalGoodsStore.Purchase;
/*  7:   */ import nxt.NxtException;
/*  8:   */ import nxt.util.Convert;
/*  9:   */ import org.json.simple.JSONStreamAware;
/* 10:   */ 
/* 11:   */ public final class DGSRefund
/* 12:   */   extends CreateTransaction
/* 13:   */ {
/* 14:20 */   static final DGSRefund instance = new DGSRefund();
/* 15:   */   
/* 16:   */   private DGSRefund()
/* 17:   */   {
/* 18:23 */     super(new APITag[] { APITag.DGS, APITag.CREATE_TRANSACTION }, new String[] { "purchase", "refundNQT" });
/* 19:   */   }
/* 20:   */   
/* 21:   */   JSONStreamAware processRequest(HttpServletRequest paramHttpServletRequest)
/* 22:   */     throws NxtException
/* 23:   */   {
/* 24:30 */     Account localAccount1 = ParameterParser.getSenderAccount(paramHttpServletRequest);
/* 25:31 */     DigitalGoodsStore.Purchase localPurchase = ParameterParser.getPurchase(paramHttpServletRequest);
/* 26:32 */     if (localAccount1.getId() != localPurchase.getSellerId()) {
/* 27:33 */       return JSONResponses.INCORRECT_PURCHASE;
/* 28:   */     }
/* 29:35 */     if (localPurchase.getRefundNote() != null) {
/* 30:36 */       return JSONResponses.DUPLICATE_REFUND;
/* 31:   */     }
/* 32:38 */     if (localPurchase.getEncryptedGoods() == null) {
/* 33:39 */       return JSONResponses.GOODS_NOT_DELIVERED;
/* 34:   */     }
/* 35:42 */     String str = Convert.emptyToNull(paramHttpServletRequest.getParameter("refundNQT"));
/* 36:43 */     long l = 0L;
/* 37:   */     try
/* 38:   */     {
/* 39:45 */       if (str != null) {
/* 40:46 */         l = Long.parseLong(str);
/* 41:   */       }
/* 42:   */     }
/* 43:   */     catch (RuntimeException localRuntimeException)
/* 44:   */     {
/* 45:49 */       return JSONResponses.INCORRECT_DGS_REFUND;
/* 46:   */     }
/* 47:51 */     if ((l < 0L) || (l > 215881280000000000L)) {
/* 48:52 */       return JSONResponses.INCORRECT_DGS_REFUND;
/* 49:   */     }
/* 50:55 */     Account localAccount2 = Account.getAccount(localPurchase.getBuyerId());
/* 51:   */     
/* 52:57 */     Attachment.DigitalGoodsRefund localDigitalGoodsRefund = new Attachment.DigitalGoodsRefund(localPurchase.getId(), l);
/* 53:58 */     return createTransaction(paramHttpServletRequest, localAccount1, Long.valueOf(localAccount2.getId()), 0L, localDigitalGoodsRefund);
/* 54:   */   }
/* 55:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.http.DGSRefund
 * JD-Core Version:    0.7.1
 */