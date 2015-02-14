/*  1:   */ package nxt.http;
/*  2:   */ 
/*  3:   */ import javax.servlet.http.HttpServletRequest;
/*  4:   */ import nxt.Account;
/*  5:   */ import nxt.Attachment.DigitalGoodsQuantityChange;
/*  6:   */ import nxt.DigitalGoodsStore.Goods;
/*  7:   */ import nxt.NxtException;
/*  8:   */ import nxt.util.Convert;
/*  9:   */ import org.json.simple.JSONStreamAware;
/* 10:   */ 
/* 11:   */ public final class DGSQuantityChange
/* 12:   */   extends CreateTransaction
/* 13:   */ {
/* 14:19 */   static final DGSQuantityChange instance = new DGSQuantityChange();
/* 15:   */   
/* 16:   */   private DGSQuantityChange()
/* 17:   */   {
/* 18:22 */     super(new APITag[] { APITag.DGS, APITag.CREATE_TRANSACTION }, new String[] { "goods", "deltaQuantity" });
/* 19:   */   }
/* 20:   */   
/* 21:   */   JSONStreamAware processRequest(HttpServletRequest paramHttpServletRequest)
/* 22:   */     throws NxtException
/* 23:   */   {
/* 24:29 */     Account localAccount = ParameterParser.getSenderAccount(paramHttpServletRequest);
/* 25:30 */     DigitalGoodsStore.Goods localGoods = ParameterParser.getGoods(paramHttpServletRequest);
/* 26:31 */     if ((localGoods.isDelisted()) || (localGoods.getSellerId() != localAccount.getId())) {
/* 27:32 */       return JSONResponses.UNKNOWN_GOODS;
/* 28:   */     }
/* 29:   */     int i;
/* 30:   */     try
/* 31:   */     {
/* 32:37 */       String str = Convert.emptyToNull(paramHttpServletRequest.getParameter("deltaQuantity"));
/* 33:38 */       if (str == null) {
/* 34:39 */         return JSONResponses.MISSING_DELTA_QUANTITY;
/* 35:   */       }
/* 36:41 */       i = Integer.parseInt(str);
/* 37:42 */       if ((i > 1000000000) || (i < -1000000000)) {
/* 38:43 */         return JSONResponses.INCORRECT_DELTA_QUANTITY;
/* 39:   */       }
/* 40:   */     }
/* 41:   */     catch (NumberFormatException localNumberFormatException)
/* 42:   */     {
/* 43:46 */       return JSONResponses.INCORRECT_DELTA_QUANTITY;
/* 44:   */     }
/* 45:49 */     Attachment.DigitalGoodsQuantityChange localDigitalGoodsQuantityChange = new Attachment.DigitalGoodsQuantityChange(localGoods.getId(), i);
/* 46:50 */     return createTransaction(paramHttpServletRequest, localAccount, localDigitalGoodsQuantityChange);
/* 47:   */   }
/* 48:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.http.DGSQuantityChange
 * JD-Core Version:    0.7.1
 */