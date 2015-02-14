/*  1:   */ package nxt.http;
/*  2:   */ 
/*  3:   */ import javax.servlet.http.HttpServletRequest;
/*  4:   */ import nxt.Account;
/*  5:   */ import nxt.Asset;
/*  6:   */ import nxt.Attachment.ColoredCoinsBidOrderPlacement;
/*  7:   */ import nxt.NxtException;
/*  8:   */ import nxt.util.Convert;
/*  9:   */ import org.json.simple.JSONStreamAware;
/* 10:   */ 
/* 11:   */ public final class PlaceBidOrder
/* 12:   */   extends CreateTransaction
/* 13:   */ {
/* 14:16 */   static final PlaceBidOrder instance = new PlaceBidOrder();
/* 15:   */   
/* 16:   */   private PlaceBidOrder()
/* 17:   */   {
/* 18:19 */     super(new APITag[] { APITag.AE, APITag.CREATE_TRANSACTION }, new String[] { "asset", "quantityQNT", "priceNQT" });
/* 19:   */   }
/* 20:   */   
/* 21:   */   JSONStreamAware processRequest(HttpServletRequest paramHttpServletRequest)
/* 22:   */     throws NxtException
/* 23:   */   {
/* 24:25 */     Asset localAsset = ParameterParser.getAsset(paramHttpServletRequest);
/* 25:26 */     long l1 = ParameterParser.getPriceNQT(paramHttpServletRequest);
/* 26:27 */     long l2 = ParameterParser.getQuantityQNT(paramHttpServletRequest);
/* 27:28 */     long l3 = ParameterParser.getFeeNQT(paramHttpServletRequest);
/* 28:29 */     Account localAccount = ParameterParser.getSenderAccount(paramHttpServletRequest);
/* 29:   */     try
/* 30:   */     {
/* 31:32 */       if (Convert.safeAdd(l3, Convert.safeMultiply(l1, l2)) > localAccount.getUnconfirmedBalanceNQT()) {
/* 32:33 */         return JSONResponses.NOT_ENOUGH_FUNDS;
/* 33:   */       }
/* 34:   */     }
/* 35:   */     catch (ArithmeticException localArithmeticException)
/* 36:   */     {
/* 37:36 */       return JSONResponses.NOT_ENOUGH_FUNDS;
/* 38:   */     }
/* 39:39 */     Attachment.ColoredCoinsBidOrderPlacement localColoredCoinsBidOrderPlacement = new Attachment.ColoredCoinsBidOrderPlacement(localAsset.getId(), l2, l1);
/* 40:40 */     return createTransaction(paramHttpServletRequest, localAccount, localColoredCoinsBidOrderPlacement);
/* 41:   */   }
/* 42:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.http.PlaceBidOrder
 * JD-Core Version:    0.7.1
 */