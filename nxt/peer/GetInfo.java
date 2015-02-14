/*  1:   */ package nxt.peer;
/*  2:   */ 
/*  3:   */ import nxt.Nxt;
/*  4:   */ import org.json.simple.JSONObject;
/*  5:   */ import org.json.simple.JSONStreamAware;
/*  6:   */ 
/*  7:   */ final class GetInfo
/*  8:   */   extends PeerServlet.PeerRequestHandler
/*  9:   */ {
/* 10: 9 */   static final GetInfo instance = new GetInfo();
/* 11:   */   
/* 12:   */   JSONStreamAware processRequest(JSONObject paramJSONObject, Peer paramPeer)
/* 13:   */   {
/* 14:16 */     PeerImpl localPeerImpl = (PeerImpl)paramPeer;
/* 15:17 */     String str1 = (String)paramJSONObject.get("announcedAddress");
/* 16:18 */     if ((str1 != null) && ((str1 = str1.trim()).length() > 0))
/* 17:   */     {
/* 18:19 */       if ((localPeerImpl.getAnnouncedAddress() != null) && (!str1.equals(localPeerImpl.getAnnouncedAddress()))) {
/* 19:21 */         localPeerImpl.setState(Peer.State.NON_CONNECTED);
/* 20:   */       }
/* 21:23 */       localPeerImpl.setAnnouncedAddress(str1);
/* 22:   */     }
/* 23:25 */     String str2 = (String)paramJSONObject.get("application");
/* 24:26 */     if (str2 == null) {
/* 25:27 */       str2 = "?";
/* 26:   */     }
/* 27:29 */     localPeerImpl.setApplication(str2.trim());
/* 28:   */     
/* 29:31 */     String str3 = (String)paramJSONObject.get("version");
/* 30:32 */     if (str3 == null) {
/* 31:33 */       str3 = "?";
/* 32:   */     }
/* 33:35 */     localPeerImpl.setVersion(str3.trim());
/* 34:   */     
/* 35:37 */     String str4 = (String)paramJSONObject.get("platform");
/* 36:38 */     if (str4 == null) {
/* 37:39 */       str4 = "?";
/* 38:   */     }
/* 39:41 */     localPeerImpl.setPlatform(str4.trim());
/* 40:   */     
/* 41:43 */     localPeerImpl.setShareAddress(Boolean.TRUE.equals(paramJSONObject.get("shareAddress")));
/* 42:44 */     localPeerImpl.setLastUpdated(Nxt.getEpochTime());
/* 43:   */     
/* 44:   */ 
/* 45:47 */     Peers.notifyListeners(localPeerImpl, Peers.Event.ADDED_ACTIVE_PEER);
/* 46:   */     
/* 47:49 */     return Peers.myPeerInfoResponse;
/* 48:   */   }
/* 49:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.peer.GetInfo
 * JD-Core Version:    0.7.1
 */