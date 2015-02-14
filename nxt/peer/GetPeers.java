/*  1:   */ package nxt.peer;
/*  2:   */ 
/*  3:   */ import org.json.simple.JSONArray;
/*  4:   */ import org.json.simple.JSONObject;
/*  5:   */ import org.json.simple.JSONStreamAware;
/*  6:   */ 
/*  7:   */ final class GetPeers
/*  8:   */   extends PeerServlet.PeerRequestHandler
/*  9:   */ {
/* 10: 9 */   static final GetPeers instance = new GetPeers();
/* 11:   */   
/* 12:   */   JSONStreamAware processRequest(JSONObject paramJSONObject, Peer paramPeer)
/* 13:   */   {
/* 14:17 */     JSONObject localJSONObject = new JSONObject();
/* 15:   */     
/* 16:19 */     JSONArray localJSONArray = new JSONArray();
/* 17:20 */     for (Peer localPeer : Peers.getAllPeers()) {
/* 18:22 */       if ((!localPeer.isBlacklisted()) && (localPeer.getAnnouncedAddress() != null) && (localPeer.getState() == Peer.State.CONNECTED) && (localPeer.shareAddress())) {
/* 19:25 */         localJSONArray.add(localPeer.getAnnouncedAddress());
/* 20:   */       }
/* 21:   */     }
/* 22:30 */     localJSONObject.put("peers", localJSONArray);
/* 23:   */     
/* 24:32 */     return localJSONObject;
/* 25:   */   }
/* 26:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.peer.GetPeers
 * JD-Core Version:    0.7.1
 */