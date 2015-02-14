/*  1:   */ package nxt.http;
/*  2:   */ 
/*  3:   */ import javax.servlet.http.HttpServletRequest;
/*  4:   */ import nxt.peer.Hallmark;
/*  5:   */ import org.json.simple.JSONStreamAware;
/*  6:   */ 
/*  7:   */ public final class DecodeHallmark
/*  8:   */   extends APIServlet.APIRequestHandler
/*  9:   */ {
/* 10:13 */   static final DecodeHallmark instance = new DecodeHallmark();
/* 11:   */   
/* 12:   */   private DecodeHallmark()
/* 13:   */   {
/* 14:16 */     super(new APITag[] { APITag.TOKENS }, new String[] { "hallmark" });
/* 15:   */   }
/* 16:   */   
/* 17:   */   JSONStreamAware processRequest(HttpServletRequest paramHttpServletRequest)
/* 18:   */   {
/* 19:22 */     String str = paramHttpServletRequest.getParameter("hallmark");
/* 20:23 */     if (str == null) {
/* 21:24 */       return JSONResponses.MISSING_HALLMARK;
/* 22:   */     }
/* 23:   */     try
/* 24:   */     {
/* 25:29 */       Hallmark localHallmark = Hallmark.parseHallmark(str);
/* 26:   */       
/* 27:31 */       return JSONData.hallmark(localHallmark);
/* 28:   */     }
/* 29:   */     catch (RuntimeException localRuntimeException) {}
/* 30:34 */     return JSONResponses.INCORRECT_HALLMARK;
/* 31:   */   }
/* 32:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.http.DecodeHallmark
 * JD-Core Version:    0.7.1
 */