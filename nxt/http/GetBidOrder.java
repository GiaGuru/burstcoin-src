/*  1:   */ package nxt.http;
/*  2:   */ 
/*  3:   */ import javax.servlet.http.HttpServletRequest;
/*  4:   */ import nxt.NxtException;
/*  5:   */ import nxt.Order.Bid;
/*  6:   */ import org.json.simple.JSONStreamAware;
/*  7:   */ 
/*  8:   */ public final class GetBidOrder
/*  9:   */   extends APIServlet.APIRequestHandler
/* 10:   */ {
/* 11:13 */   static final GetBidOrder instance = new GetBidOrder();
/* 12:   */   
/* 13:   */   private GetBidOrder()
/* 14:   */   {
/* 15:16 */     super(new APITag[] { APITag.AE }, new String[] { "order" });
/* 16:   */   }
/* 17:   */   
/* 18:   */   JSONStreamAware processRequest(HttpServletRequest paramHttpServletRequest)
/* 19:   */     throws NxtException
/* 20:   */   {
/* 21:21 */     long l = ParameterParser.getOrderId(paramHttpServletRequest);
/* 22:22 */     Order.Bid localBid = Order.Bid.getBidOrder(l);
/* 23:23 */     if (localBid == null) {
/* 24:24 */       return JSONResponses.UNKNOWN_ORDER;
/* 25:   */     }
/* 26:26 */     return JSONData.bidOrder(localBid);
/* 27:   */   }
/* 28:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.http.GetBidOrder
 * JD-Core Version:    0.7.1
 */