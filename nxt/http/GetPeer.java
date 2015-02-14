/*  1:   */ package nxt.http;
/*  2:   */ 
/*  3:   */ import javax.servlet.http.HttpServletRequest;
/*  4:   */ import nxt.peer.Peer;
/*  5:   */ import nxt.peer.Peers;
/*  6:   */ import org.json.simple.JSONStreamAware;
/*  7:   */ 
/*  8:   */ public final class GetPeer
/*  9:   */   extends APIServlet.APIRequestHandler
/* 10:   */ {
/* 11:14 */   static final GetPeer instance = new GetPeer();
/* 12:   */   
/* 13:   */   private GetPeer()
/* 14:   */   {
/* 15:17 */     super(new APITag[] { APITag.INFO }, new String[] { "peer" });
/* 16:   */   }
/* 17:   */   
/* 18:   */   JSONStreamAware processRequest(HttpServletRequest paramHttpServletRequest)
/* 19:   */   {
/* 20:23 */     String str = paramHttpServletRequest.getParameter("peer");
/* 21:24 */     if (str == null) {
/* 22:25 */       return JSONResponses.MISSING_PEER;
/* 23:   */     }
/* 24:28 */     Peer localPeer = Peers.getPeer(str);
/* 25:29 */     if (localPeer == null) {
/* 26:30 */       return JSONResponses.UNKNOWN_PEER;
/* 27:   */     }
/* 28:33 */     return JSONData.peer(localPeer);
/* 29:   */   }
/* 30:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.http.GetPeer
 * JD-Core Version:    0.7.1
 */