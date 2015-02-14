/*  1:   */ package nxt.user;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import java.net.InetAddress;
/*  5:   */ import javax.servlet.http.HttpServletRequest;
/*  6:   */ import nxt.peer.Peer;
/*  7:   */ import org.json.simple.JSONStreamAware;
/*  8:   */ 
/*  9:   */ public final class RemoveKnownPeer
/* 10:   */   extends UserServlet.UserRequestHandler
/* 11:   */ {
/* 12:14 */   static final RemoveKnownPeer instance = new RemoveKnownPeer();
/* 13:   */   
/* 14:   */   JSONStreamAware processRequest(HttpServletRequest paramHttpServletRequest, User paramUser)
/* 15:   */     throws IOException
/* 16:   */   {
/* 17:20 */     if ((Users.allowedUserHosts == null) && (!InetAddress.getByName(paramHttpServletRequest.getRemoteAddr()).isLoopbackAddress())) {
/* 18:21 */       return JSONResponses.LOCAL_USERS_ONLY;
/* 19:   */     }
/* 20:23 */     int i = Integer.parseInt(paramHttpServletRequest.getParameter("peer"));
/* 21:24 */     Peer localPeer = Users.getPeer(i);
/* 22:25 */     if (localPeer != null) {
/* 23:26 */       localPeer.remove();
/* 24:   */     }
/* 25:29 */     return null;
/* 26:   */   }
/* 27:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.user.RemoveKnownPeer
 * JD-Core Version:    0.7.1
 */