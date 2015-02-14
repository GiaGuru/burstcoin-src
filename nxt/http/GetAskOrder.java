/*  1:   */ package nxt.http;
/*  2:   */ 
/*  3:   */ import javax.servlet.http.HttpServletRequest;
/*  4:   */ import nxt.NxtException;
/*  5:   */ import nxt.Order.Ask;
/*  6:   */ import org.json.simple.JSONStreamAware;
/*  7:   */ 
/*  8:   */ public final class GetAskOrder
/*  9:   */   extends APIServlet.APIRequestHandler
/* 10:   */ {
/* 11:13 */   static final GetAskOrder instance = new GetAskOrder();
/* 12:   */   
/* 13:   */   private GetAskOrder()
/* 14:   */   {
/* 15:16 */     super(new APITag[] { APITag.AE }, new String[] { "order" });
/* 16:   */   }
/* 17:   */   
/* 18:   */   JSONStreamAware processRequest(HttpServletRequest paramHttpServletRequest)
/* 19:   */     throws NxtException
/* 20:   */   {
/* 21:21 */     long l = ParameterParser.getOrderId(paramHttpServletRequest);
/* 22:22 */     Order.Ask localAsk = Order.Ask.getAskOrder(l);
/* 23:23 */     if (localAsk == null) {
/* 24:24 */       return JSONResponses.UNKNOWN_ORDER;
/* 25:   */     }
/* 26:26 */     return JSONData.askOrder(localAsk);
/* 27:   */   }
/* 28:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.http.GetAskOrder
 * JD-Core Version:    0.7.1
 */