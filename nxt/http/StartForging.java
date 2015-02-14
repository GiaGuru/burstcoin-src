/*  1:   */ package nxt.http;
/*  2:   */ 
/*  3:   */ import javax.servlet.http.HttpServletRequest;
/*  4:   */ import nxt.Generator;
/*  5:   */ import org.json.simple.JSONObject;
/*  6:   */ import org.json.simple.JSONStreamAware;
/*  7:   */ 
/*  8:   */ public final class StartForging
/*  9:   */   extends APIServlet.APIRequestHandler
/* 10:   */ {
/* 11:15 */   static final StartForging instance = new StartForging();
/* 12:   */   
/* 13:   */   private StartForging()
/* 14:   */   {
/* 15:18 */     super(new APITag[] { APITag.FORGING }, new String[] { "secretPhrase" });
/* 16:   */   }
/* 17:   */   
/* 18:   */   JSONStreamAware processRequest(HttpServletRequest paramHttpServletRequest)
/* 19:   */   {
/* 20:24 */     String str = paramHttpServletRequest.getParameter("secretPhrase");
/* 21:25 */     if (str == null) {
/* 22:26 */       return JSONResponses.MISSING_SECRET_PHRASE;
/* 23:   */     }
/* 24:29 */     Generator localGenerator = Generator.startForging(str);
/* 25:30 */     if (localGenerator == null) {
/* 26:31 */       return JSONResponses.UNKNOWN_ACCOUNT;
/* 27:   */     }
/* 28:34 */     JSONObject localJSONObject = new JSONObject();
/* 29:35 */     localJSONObject.put("deadline", localGenerator.getDeadline());
/* 30:36 */     return localJSONObject;
/* 31:   */   }
/* 32:   */   
/* 33:   */   boolean requirePost()
/* 34:   */   {
/* 35:42 */     return true;
/* 36:   */   }
/* 37:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.http.StartForging
 * JD-Core Version:    0.7.1
 */