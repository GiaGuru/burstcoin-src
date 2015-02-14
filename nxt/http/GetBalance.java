/*  1:   */ package nxt.http;
/*  2:   */ 
/*  3:   */ import javax.servlet.http.HttpServletRequest;
/*  4:   */ import nxt.NxtException;
/*  5:   */ import org.json.simple.JSONStreamAware;
/*  6:   */ 
/*  7:   */ public final class GetBalance
/*  8:   */   extends APIServlet.APIRequestHandler
/*  9:   */ {
/* 10:10 */   static final GetBalance instance = new GetBalance();
/* 11:   */   
/* 12:   */   private GetBalance()
/* 13:   */   {
/* 14:13 */     super(new APITag[] { APITag.ACCOUNTS }, new String[] { "account" });
/* 15:   */   }
/* 16:   */   
/* 17:   */   JSONStreamAware processRequest(HttpServletRequest paramHttpServletRequest)
/* 18:   */     throws NxtException
/* 19:   */   {
/* 20:18 */     return JSONData.accountBalance(ParameterParser.getAccount(paramHttpServletRequest));
/* 21:   */   }
/* 22:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.http.GetBalance
 * JD-Core Version:    0.7.1
 */