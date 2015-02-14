/*  1:   */ package nxt.peer;
/*  2:   */ 
/*  3:   */ import nxt.util.JSON;
/*  4:   */ import org.json.simple.JSONArray;
/*  5:   */ import org.json.simple.JSONObject;
/*  6:   */ import org.json.simple.JSONStreamAware;
/*  7:   */ 
/*  8:   */ final class AddPeers
/*  9:   */   extends PeerServlet.PeerRequestHandler
/* 10:   */ {
/* 11:10 */   static final AddPeers instance = new AddPeers();
/* 12:   */   
/* 13:   */   JSONStreamAware processRequest(JSONObject paramJSONObject, Peer paramPeer)
/* 14:   */   {
/* 15:16 */     JSONArray localJSONArray = (JSONArray)paramJSONObject.get("peers");
/* 16:17 */     if ((localJSONArray != null) && (Peers.getMorePeers)) {
/* 17:18 */       for (Object localObject : localJSONArray) {
/* 18:19 */         Peers.addPeer((String)localObject);
/* 19:   */       }
/* 20:   */     }
/* 21:22 */     return JSON.emptyJSON;
/* 22:   */   }
/* 23:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.peer.AddPeers
 * JD-Core Version:    0.7.1
 */