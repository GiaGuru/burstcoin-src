/*  1:   */ package nxt.http;
/*  2:   */ 
/*  3:   */ import javax.servlet.http.HttpServletRequest;
/*  4:   */ import nxt.Poll;
/*  5:   */ import nxt.util.Convert;
/*  6:   */ import org.json.simple.JSONStreamAware;
/*  7:   */ 
/*  8:   */ public final class GetPoll
/*  9:   */   extends APIServlet.APIRequestHandler
/* 10:   */ {
/* 11:15 */   static final GetPoll instance = new GetPoll();
/* 12:   */   
/* 13:   */   private GetPoll()
/* 14:   */   {
/* 15:18 */     super(new APITag[] { APITag.VS }, new String[] { "poll" });
/* 16:   */   }
/* 17:   */   
/* 18:   */   JSONStreamAware processRequest(HttpServletRequest paramHttpServletRequest)
/* 19:   */   {
/* 20:24 */     String str = paramHttpServletRequest.getParameter("poll");
/* 21:25 */     if (str == null) {
/* 22:26 */       return JSONResponses.MISSING_POLL;
/* 23:   */     }
/* 24:   */     Poll localPoll;
/* 25:   */     try
/* 26:   */     {
/* 27:31 */       localPoll = Poll.getPoll(Convert.parseUnsignedLong(str));
/* 28:32 */       if (localPoll == null) {
/* 29:33 */         return JSONResponses.UNKNOWN_POLL;
/* 30:   */       }
/* 31:   */     }
/* 32:   */     catch (RuntimeException localRuntimeException)
/* 33:   */     {
/* 34:36 */       return JSONResponses.INCORRECT_POLL;
/* 35:   */     }
/* 36:39 */     return JSONData.poll(localPoll);
/* 37:   */   }
/* 38:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.http.GetPoll
 * JD-Core Version:    0.7.1
 */