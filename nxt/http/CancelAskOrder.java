/*  1:   */ package nxt.http;
/*  2:   */ 
/*  3:   */ import javax.servlet.http.HttpServletRequest;
/*  4:   */ import nxt.Account;
/*  5:   */ import nxt.Attachment.ColoredCoinsAskOrderCancellation;
/*  6:   */ import nxt.NxtException;
/*  7:   */ import nxt.Order.Ask;
/*  8:   */ import org.json.simple.JSONStreamAware;
/*  9:   */ 
/* 10:   */ public final class CancelAskOrder
/* 11:   */   extends CreateTransaction
/* 12:   */ {
/* 13:15 */   static final CancelAskOrder instance = new CancelAskOrder();
/* 14:   */   
/* 15:   */   private CancelAskOrder()
/* 16:   */   {
/* 17:18 */     super(new APITag[] { APITag.AE, APITag.CREATE_TRANSACTION }, new String[] { "order" });
/* 18:   */   }
/* 19:   */   
/* 20:   */   JSONStreamAware processRequest(HttpServletRequest paramHttpServletRequest)
/* 21:   */     throws NxtException
/* 22:   */   {
/* 23:23 */     long l = ParameterParser.getOrderId(paramHttpServletRequest);
/* 24:24 */     Account localAccount = ParameterParser.getSenderAccount(paramHttpServletRequest);
/* 25:25 */     Order.Ask localAsk = Order.Ask.getAskOrder(l);
/* 26:26 */     if ((localAsk == null) || (localAsk.getAccountId() != localAccount.getId())) {
/* 27:27 */       return JSONResponses.UNKNOWN_ORDER;
/* 28:   */     }
/* 29:29 */     Attachment.ColoredCoinsAskOrderCancellation localColoredCoinsAskOrderCancellation = new Attachment.ColoredCoinsAskOrderCancellation(l);
/* 30:30 */     return createTransaction(paramHttpServletRequest, localAccount, localColoredCoinsAskOrderCancellation);
/* 31:   */   }
/* 32:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.http.CancelAskOrder
 * JD-Core Version:    0.7.1
 */