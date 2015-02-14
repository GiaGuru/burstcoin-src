/*  1:   */ package nxt.http;
/*  2:   */ 
/*  3:   */ import javax.servlet.http.HttpServletRequest;
/*  4:   */ import nxt.Account;
/*  5:   */ import nxt.Attachment.DigitalGoodsDelisting;
/*  6:   */ import nxt.DigitalGoodsStore.Goods;
/*  7:   */ import nxt.NxtException;
/*  8:   */ import org.json.simple.JSONStreamAware;
/*  9:   */ 
/* 10:   */ public final class DGSDelisting
/* 11:   */   extends CreateTransaction
/* 12:   */ {
/* 13:15 */   static final DGSDelisting instance = new DGSDelisting();
/* 14:   */   
/* 15:   */   private DGSDelisting()
/* 16:   */   {
/* 17:18 */     super(new APITag[] { APITag.DGS, APITag.CREATE_TRANSACTION }, new String[] { "goods" });
/* 18:   */   }
/* 19:   */   
/* 20:   */   JSONStreamAware processRequest(HttpServletRequest paramHttpServletRequest)
/* 21:   */     throws NxtException
/* 22:   */   {
/* 23:23 */     Account localAccount = ParameterParser.getSenderAccount(paramHttpServletRequest);
/* 24:24 */     DigitalGoodsStore.Goods localGoods = ParameterParser.getGoods(paramHttpServletRequest);
/* 25:25 */     if ((localGoods.isDelisted()) || (localGoods.getSellerId() != localAccount.getId())) {
/* 26:26 */       return JSONResponses.UNKNOWN_GOODS;
/* 27:   */     }
/* 28:28 */     Attachment.DigitalGoodsDelisting localDigitalGoodsDelisting = new Attachment.DigitalGoodsDelisting(localGoods.getId());
/* 29:29 */     return createTransaction(paramHttpServletRequest, localAccount, localDigitalGoodsDelisting);
/* 30:   */   }
/* 31:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.http.DGSDelisting
 * JD-Core Version:    0.7.1
 */