/*  1:   */ package nxt.http;
/*  2:   */ 
/*  3:   */ import javax.servlet.http.HttpServletRequest;
/*  4:   */ import nxt.NxtException;
/*  5:   */ import org.json.simple.JSONStreamAware;
/*  6:   */ 
/*  7:   */ public class GetATDetails
/*  8:   */   extends APIServlet.APIRequestHandler
/*  9:   */ {
/* 10: 9 */   static final GetATDetails instance = new GetATDetails();
/* 11:   */   
/* 12:   */   private GetATDetails()
/* 13:   */   {
/* 14:12 */     super(new APITag[] { APITag.AT }, new String[] { "at" });
/* 15:   */   }
/* 16:   */   
/* 17:   */   JSONStreamAware processRequest(HttpServletRequest paramHttpServletRequest)
/* 18:   */     throws NxtException
/* 19:   */   {
/* 20:17 */     return JSONData.at(ParameterParser.getAT(paramHttpServletRequest));
/* 21:   */   }
/* 22:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.http.GetATDetails
 * JD-Core Version:    0.7.1
 */