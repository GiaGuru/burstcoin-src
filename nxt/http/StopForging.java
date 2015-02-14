/*  1:   */ package nxt.http;
/*  2:   */ 
/*  3:   */ import javax.servlet.http.HttpServletRequest;
/*  4:   */ import nxt.Generator;
/*  5:   */ import org.json.simple.JSONObject;
/*  6:   */ import org.json.simple.JSONStreamAware;
/*  7:   */ 
/*  8:   */ public final class StopForging
/*  9:   */   extends APIServlet.APIRequestHandler
/* 10:   */ {
/* 11:14 */   static final StopForging instance = new StopForging();
/* 12:   */   
/* 13:   */   private StopForging()
/* 14:   */   {
/* 15:17 */     super(new APITag[] { APITag.FORGING }, new String[] { "secretPhrase" });
/* 16:   */   }
/* 17:   */   
/* 18:   */   JSONStreamAware processRequest(HttpServletRequest paramHttpServletRequest)
/* 19:   */   {
/* 20:23 */     String str = paramHttpServletRequest.getParameter("secretPhrase");
/* 21:24 */     if (str == null) {
/* 22:25 */       return JSONResponses.MISSING_SECRET_PHRASE;
/* 23:   */     }
/* 24:28 */     Generator localGenerator = Generator.stopForging(str);
/* 25:   */     
/* 26:30 */     JSONObject localJSONObject = new JSONObject();
/* 27:31 */     localJSONObject.put("foundAndStopped", Boolean.valueOf(localGenerator != null));
/* 28:32 */     return localJSONObject;
/* 29:   */   }
/* 30:   */   
/* 31:   */   boolean requirePost()
/* 32:   */   {
/* 33:38 */     return true;
/* 34:   */   }
/* 35:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.http.StopForging
 * JD-Core Version:    0.7.1
 */