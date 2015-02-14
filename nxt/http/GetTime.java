/*  1:   */ package nxt.http;
/*  2:   */ 
/*  3:   */ import javax.servlet.http.HttpServletRequest;
/*  4:   */ import nxt.Nxt;
/*  5:   */ import org.json.simple.JSONObject;
/*  6:   */ import org.json.simple.JSONStreamAware;
/*  7:   */ 
/*  8:   */ public final class GetTime
/*  9:   */   extends APIServlet.APIRequestHandler
/* 10:   */ {
/* 11:11 */   static final GetTime instance = new GetTime();
/* 12:   */   
/* 13:   */   private GetTime()
/* 14:   */   {
/* 15:14 */     super(new APITag[] { APITag.INFO }, new String[0]);
/* 16:   */   }
/* 17:   */   
/* 18:   */   JSONStreamAware processRequest(HttpServletRequest paramHttpServletRequest)
/* 19:   */   {
/* 20:20 */     JSONObject localJSONObject = new JSONObject();
/* 21:21 */     localJSONObject.put("time", Integer.valueOf(Nxt.getEpochTime()));
/* 22:   */     
/* 23:23 */     return localJSONObject;
/* 24:   */   }
/* 25:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.http.GetTime
 * JD-Core Version:    0.7.1
 */