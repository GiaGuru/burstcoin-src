/*  1:   */ package nxt.http;
/*  2:   */ 
/*  3:   */ import javax.servlet.http.HttpServletRequest;
/*  4:   */ import nxt.Account;
/*  5:   */ import nxt.Attachment.DigitalGoodsPriceChange;
/*  6:   */ import nxt.DigitalGoodsStore.Goods;
/*  7:   */ import nxt.NxtException;
/*  8:   */ import org.json.simple.JSONStreamAware;
/*  9:   */ 
/* 10:   */ public final class DGSPriceChange
/* 11:   */   extends CreateTransaction
/* 12:   */ {
/* 13:15 */   static final DGSPriceChange instance = new DGSPriceChange();
/* 14:   */   
/* 15:   */   private DGSPriceChange()
/* 16:   */   {
/* 17:18 */     super(new APITag[] { APITag.DGS, APITag.CREATE_TRANSACTION }, new String[] { "goods", "priceNQT" });
/* 18:   */   }
/* 19:   */   
/* 20:   */   JSONStreamAware processRequest(HttpServletRequest paramHttpServletRequest)
/* 21:   */     throws NxtException
/* 22:   */   {
/* 23:24 */     Account localAccount = ParameterParser.getSenderAccount(paramHttpServletRequest);
/* 24:25 */     DigitalGoodsStore.Goods localGoods = ParameterParser.getGoods(paramHttpServletRequest);
/* 25:26 */     long l = ParameterParser.getPriceNQT(paramHttpServletRequest);
/* 26:27 */     if ((localGoods.isDelisted()) || (localGoods.getSellerId() != localAccount.getId())) {
/* 27:28 */       return JSONResponses.UNKNOWN_GOODS;
/* 28:   */     }
/* 29:30 */     Attachment.DigitalGoodsPriceChange localDigitalGoodsPriceChange = new Attachment.DigitalGoodsPriceChange(localGoods.getId(), l);
/* 30:31 */     return createTransaction(paramHttpServletRequest, localAccount, localDigitalGoodsPriceChange);
/* 31:   */   }
/* 32:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.http.DGSPriceChange
 * JD-Core Version:    0.7.1
 */