/*  1:   */ package nxt.http;
/*  2:   */ 
/*  3:   */ import javax.servlet.http.HttpServletRequest;
/*  4:   */ import nxt.Account;
/*  5:   */ import nxt.Attachment.ColoredCoinsBidOrderCancellation;
/*  6:   */ import nxt.NxtException;
/*  7:   */ import nxt.Order.Bid;
/*  8:   */ import org.json.simple.JSONStreamAware;
/*  9:   */ 
/* 10:   */ public final class CancelBidOrder
/* 11:   */   extends CreateTransaction
/* 12:   */ {
/* 13:15 */   static final CancelBidOrder instance = new CancelBidOrder();
/* 14:   */   
/* 15:   */   private CancelBidOrder()
/* 16:   */   {
/* 17:18 */     super(new APITag[] { APITag.AE, APITag.CREATE_TRANSACTION }, new String[] { "order" });
/* 18:   */   }
/* 19:   */   
/* 20:   */   JSONStreamAware processRequest(HttpServletRequest paramHttpServletRequest)
/* 21:   */     throws NxtException
/* 22:   */   {
/* 23:23 */     long l = ParameterParser.getOrderId(paramHttpServletRequest);
/* 24:24 */     Account localAccount = ParameterParser.getSenderAccount(paramHttpServletRequest);
/* 25:25 */     Order.Bid localBid = Order.Bid.getBidOrder(l);
/* 26:26 */     if ((localBid == null) || (localBid.getAccountId() != localAccount.getId())) {
/* 27:27 */       return JSONResponses.UNKNOWN_ORDER;
/* 28:   */     }
/* 29:29 */     Attachment.ColoredCoinsBidOrderCancellation localColoredCoinsBidOrderCancellation = new Attachment.ColoredCoinsBidOrderCancellation(l);
/* 30:30 */     return createTransaction(paramHttpServletRequest, localAccount, localColoredCoinsBidOrderCancellation);
/* 31:   */   }
/* 32:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.http.CancelBidOrder
 * JD-Core Version:    0.7.1
 */