/*  1:   */ package nxt.http;
/*  2:   */ 
/*  3:   */ import javax.servlet.http.HttpServletRequest;
/*  4:   */ import nxt.NxtException;
/*  5:   */ import org.json.simple.JSONStreamAware;
/*  6:   */ 
/*  7:   */ public final class GetDGSPurchase
/*  8:   */   extends APIServlet.APIRequestHandler
/*  9:   */ {
/* 10:10 */   static final GetDGSPurchase instance = new GetDGSPurchase();
/* 11:   */   
/* 12:   */   private GetDGSPurchase()
/* 13:   */   {
/* 14:13 */     super(new APITag[] { APITag.DGS }, new String[] { "purchase" });
/* 15:   */   }
/* 16:   */   
/* 17:   */   JSONStreamAware processRequest(HttpServletRequest paramHttpServletRequest)
/* 18:   */     throws NxtException
/* 19:   */   {
/* 20:18 */     return JSONData.purchase(ParameterParser.getPurchase(paramHttpServletRequest));
/* 21:   */   }
/* 22:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.http.GetDGSPurchase
 * JD-Core Version:    0.7.1
 */