/*  1:   */ package nxt.http;
/*  2:   */ 
/*  3:   */ import javax.servlet.http.HttpServletRequest;
/*  4:   */ import nxt.NxtException;
/*  5:   */ import org.json.simple.JSONStreamAware;
/*  6:   */ 
/*  7:   */ public final class GetATLong
/*  8:   */   extends APIServlet.APIRequestHandler
/*  9:   */ {
/* 10:11 */   static final GetATLong instance = new GetATLong();
/* 11:   */   
/* 12:   */   private GetATLong()
/* 13:   */   {
/* 14:14 */     super(new APITag[] { APITag.AT }, new String[] { "hexString" });
/* 15:   */   }
/* 16:   */   
/* 17:   */   JSONStreamAware processRequest(HttpServletRequest paramHttpServletRequest)
/* 18:   */     throws NxtException
/* 19:   */   {
/* 20:19 */     return JSONData.hex2long(ParameterParser.getATLong(paramHttpServletRequest));
/* 21:   */   }
/* 22:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.http.GetATLong
 * JD-Core Version:    0.7.1
 */