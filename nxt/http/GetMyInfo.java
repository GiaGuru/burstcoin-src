/*  1:   */ package nxt.http;
/*  2:   */ 
/*  3:   */ import javax.servlet.http.HttpServletRequest;
/*  4:   */ import org.json.simple.JSONObject;
/*  5:   */ import org.json.simple.JSONStreamAware;
/*  6:   */ 
/*  7:   */ public final class GetMyInfo
/*  8:   */   extends APIServlet.APIRequestHandler
/*  9:   */ {
/* 10:10 */   static final GetMyInfo instance = new GetMyInfo();
/* 11:   */   
/* 12:   */   private GetMyInfo()
/* 13:   */   {
/* 14:13 */     super(new APITag[] { APITag.INFO }, new String[0]);
/* 15:   */   }
/* 16:   */   
/* 17:   */   JSONStreamAware processRequest(HttpServletRequest paramHttpServletRequest)
/* 18:   */   {
/* 19:19 */     JSONObject localJSONObject = new JSONObject();
/* 20:20 */     localJSONObject.put("host", paramHttpServletRequest.getRemoteHost());
/* 21:21 */     localJSONObject.put("address", paramHttpServletRequest.getRemoteAddr());
/* 22:22 */     return localJSONObject;
/* 23:   */   }
/* 24:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.http.GetMyInfo
 * JD-Core Version:    0.7.1
 */