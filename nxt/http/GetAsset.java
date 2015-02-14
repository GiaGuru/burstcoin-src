/*  1:   */ package nxt.http;
/*  2:   */ 
/*  3:   */ import javax.servlet.http.HttpServletRequest;
/*  4:   */ import nxt.NxtException;
/*  5:   */ import org.json.simple.JSONStreamAware;
/*  6:   */ 
/*  7:   */ public final class GetAsset
/*  8:   */   extends APIServlet.APIRequestHandler
/*  9:   */ {
/* 10:10 */   static final GetAsset instance = new GetAsset();
/* 11:   */   
/* 12:   */   private GetAsset()
/* 13:   */   {
/* 14:13 */     super(new APITag[] { APITag.AE }, new String[] { "asset" });
/* 15:   */   }
/* 16:   */   
/* 17:   */   JSONStreamAware processRequest(HttpServletRequest paramHttpServletRequest)
/* 18:   */     throws NxtException
/* 19:   */   {
/* 20:18 */     return JSONData.asset(ParameterParser.getAsset(paramHttpServletRequest));
/* 21:   */   }
/* 22:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.http.GetAsset
 * JD-Core Version:    0.7.1
 */