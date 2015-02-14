/*  1:   */ package nxt.http;
/*  2:   */ 
/*  3:   */ import java.util.Collection;
/*  4:   */ import java.util.Iterator;
/*  5:   */ import javax.servlet.http.HttpServletRequest;
/*  6:   */ import nxt.peer.Peer;
/*  7:   */ import nxt.peer.Peer.State;
/*  8:   */ import nxt.peer.Peers;
/*  9:   */ import nxt.util.Convert;
/* 10:   */ import org.json.simple.JSONArray;
/* 11:   */ import org.json.simple.JSONObject;
/* 12:   */ import org.json.simple.JSONStreamAware;
/* 13:   */ 
/* 14:   */ public final class GetPeers
/* 15:   */   extends APIServlet.APIRequestHandler
/* 16:   */ {
/* 17:14 */   static final GetPeers instance = new GetPeers();
/* 18:   */   
/* 19:   */   private GetPeers()
/* 20:   */   {
/* 21:17 */     super(new APITag[] { APITag.INFO }, new String[] { "active", "state" });
/* 22:   */   }
/* 23:   */   
/* 24:   */   JSONStreamAware processRequest(HttpServletRequest paramHttpServletRequest)
/* 25:   */   {
/* 26:23 */     boolean bool = "true".equalsIgnoreCase(paramHttpServletRequest.getParameter("active"));
/* 27:24 */     String str = Convert.emptyToNull(paramHttpServletRequest.getParameter("state"));
/* 28:   */     
/* 29:26 */     JSONArray localJSONArray = new JSONArray();
/* 30:27 */     for (Object localObject = (str != null ? Peers.getPeers(Peer.State.valueOf(str)) : bool ? Peers.getActivePeers() : Peers.getAllPeers()).iterator(); ((Iterator)localObject).hasNext();)
/* 31:   */     {
/* 32:27 */       Peer localPeer = (Peer)((Iterator)localObject).next();
/* 33:28 */       localJSONArray.add(localPeer.getPeerAddress());
/* 34:   */     }
/* 35:31 */     localObject = new JSONObject();
/* 36:32 */     ((JSONObject)localObject).put("peers", localJSONArray);
/* 37:33 */     return (JSONStreamAware)localObject;
/* 38:   */   }
/* 39:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.http.GetPeers
 * JD-Core Version:    0.7.1
 */