/*  1:   */ package nxt.http;
/*  2:   */ 
/*  3:   */ import javax.servlet.http.HttpServletRequest;
/*  4:   */ import nxt.Account;
/*  5:   */ import nxt.Attachment.DigitalGoodsPurchase;
/*  6:   */ import nxt.DigitalGoodsStore.Goods;
/*  7:   */ import nxt.Nxt;
/*  8:   */ import nxt.NxtException;
/*  9:   */ import nxt.util.Convert;
/* 10:   */ import org.json.simple.JSONStreamAware;
/* 11:   */ 
/* 12:   */ public final class DGSPurchase
/* 13:   */   extends CreateTransaction
/* 14:   */ {
/* 15:21 */   static final DGSPurchase instance = new DGSPurchase();
/* 16:   */   
/* 17:   */   private DGSPurchase()
/* 18:   */   {
/* 19:24 */     super(new APITag[] { APITag.DGS, APITag.CREATE_TRANSACTION }, new String[] { "goods", "priceNQT", "quantity", "deliveryDeadlineTimestamp" });
/* 20:   */   }
/* 21:   */   
/* 22:   */   JSONStreamAware processRequest(HttpServletRequest paramHttpServletRequest)
/* 23:   */     throws NxtException
/* 24:   */   {
/* 25:31 */     DigitalGoodsStore.Goods localGoods = ParameterParser.getGoods(paramHttpServletRequest);
/* 26:32 */     if (localGoods.isDelisted()) {
/* 27:33 */       return JSONResponses.UNKNOWN_GOODS;
/* 28:   */     }
/* 29:36 */     int i = ParameterParser.getGoodsQuantity(paramHttpServletRequest);
/* 30:37 */     if (i > localGoods.getQuantity()) {
/* 31:38 */       return JSONResponses.INCORRECT_PURCHASE_QUANTITY;
/* 32:   */     }
/* 33:41 */     long l = ParameterParser.getPriceNQT(paramHttpServletRequest);
/* 34:42 */     if (l != localGoods.getPriceNQT()) {
/* 35:43 */       return JSONResponses.INCORRECT_PURCHASE_PRICE;
/* 36:   */     }
/* 37:46 */     String str = Convert.emptyToNull(paramHttpServletRequest.getParameter("deliveryDeadlineTimestamp"));
/* 38:47 */     if (str == null) {
/* 39:48 */       return JSONResponses.MISSING_DELIVERY_DEADLINE_TIMESTAMP;
/* 40:   */     }
/* 41:   */     int j;
/* 42:   */     try
/* 43:   */     {
/* 44:52 */       j = Integer.parseInt(str);
/* 45:53 */       if (j <= Nxt.getEpochTime()) {
/* 46:54 */         return JSONResponses.INCORRECT_DELIVERY_DEADLINE_TIMESTAMP;
/* 47:   */       }
/* 48:   */     }
/* 49:   */     catch (NumberFormatException localNumberFormatException)
/* 50:   */     {
/* 51:57 */       return JSONResponses.INCORRECT_DELIVERY_DEADLINE_TIMESTAMP;
/* 52:   */     }
/* 53:60 */     Account localAccount1 = ParameterParser.getSenderAccount(paramHttpServletRequest);
/* 54:61 */     Account localAccount2 = Account.getAccount(localGoods.getSellerId());
/* 55:   */     
/* 56:63 */     Attachment.DigitalGoodsPurchase localDigitalGoodsPurchase = new Attachment.DigitalGoodsPurchase(localGoods.getId(), i, l, j);
/* 57:   */     
/* 58:65 */     return createTransaction(paramHttpServletRequest, localAccount1, Long.valueOf(localAccount2.getId()), 0L, localDigitalGoodsPurchase);
/* 59:   */   }
/* 60:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.http.DGSPurchase
 * JD-Core Version:    0.7.1
 */