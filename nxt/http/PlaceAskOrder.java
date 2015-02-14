/*  1:   */ package nxt.http;
/*  2:   */ 
/*  3:   */ import javax.servlet.http.HttpServletRequest;
/*  4:   */ import nxt.Account;
/*  5:   */ import nxt.Asset;
/*  6:   */ import nxt.Attachment.ColoredCoinsAskOrderPlacement;
/*  7:   */ import nxt.NxtException;
/*  8:   */ import org.json.simple.JSONStreamAware;
/*  9:   */ 
/* 10:   */ public final class PlaceAskOrder
/* 11:   */   extends CreateTransaction
/* 12:   */ {
/* 13:15 */   static final PlaceAskOrder instance = new PlaceAskOrder();
/* 14:   */   
/* 15:   */   private PlaceAskOrder()
/* 16:   */   {
/* 17:18 */     super(new APITag[] { APITag.AE, APITag.CREATE_TRANSACTION }, new String[] { "asset", "quantityQNT", "priceNQT" });
/* 18:   */   }
/* 19:   */   
/* 20:   */   JSONStreamAware processRequest(HttpServletRequest paramHttpServletRequest)
/* 21:   */     throws NxtException
/* 22:   */   {
/* 23:24 */     Asset localAsset = ParameterParser.getAsset(paramHttpServletRequest);
/* 24:25 */     long l1 = ParameterParser.getPriceNQT(paramHttpServletRequest);
/* 25:26 */     long l2 = ParameterParser.getQuantityQNT(paramHttpServletRequest);
/* 26:27 */     Account localAccount = ParameterParser.getSenderAccount(paramHttpServletRequest);
/* 27:   */     
/* 28:29 */     long l3 = localAccount.getUnconfirmedAssetBalanceQNT(localAsset.getId());
/* 29:30 */     if ((l3 < 0L) || (l2 > l3)) {
/* 30:31 */       return JSONResponses.NOT_ENOUGH_ASSETS;
/* 31:   */     }
/* 32:34 */     Attachment.ColoredCoinsAskOrderPlacement localColoredCoinsAskOrderPlacement = new Attachment.ColoredCoinsAskOrderPlacement(localAsset.getId(), l2, l1);
/* 33:35 */     return createTransaction(paramHttpServletRequest, localAccount, localColoredCoinsAskOrderPlacement);
/* 34:   */   }
/* 35:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.http.PlaceAskOrder
 * JD-Core Version:    0.7.1
 */