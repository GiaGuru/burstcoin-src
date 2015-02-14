/*  1:   */ package nxt.peer;
/*  2:   */ 
/*  3:   */ import java.util.List;
/*  4:   */ import nxt.Blockchain;
/*  5:   */ import nxt.Nxt;
/*  6:   */ import nxt.util.Convert;
/*  7:   */ import org.json.simple.JSONArray;
/*  8:   */ import org.json.simple.JSONObject;
/*  9:   */ import org.json.simple.JSONStreamAware;
/* 10:   */ 
/* 11:   */ final class GetNextBlockIds
/* 12:   */   extends PeerServlet.PeerRequestHandler
/* 13:   */ {
/* 14:13 */   static final GetNextBlockIds instance = new GetNextBlockIds();
/* 15:   */   
/* 16:   */   JSONStreamAware processRequest(JSONObject paramJSONObject, Peer paramPeer)
/* 17:   */   {
/* 18:21 */     JSONObject localJSONObject = new JSONObject();
/* 19:   */     
/* 20:23 */     JSONArray localJSONArray = new JSONArray();
/* 21:24 */     long l = Convert.parseUnsignedLong((String)paramJSONObject.get("blockId"));
/* 22:25 */     List localList = Nxt.getBlockchain().getBlockIdsAfter(l, 1440);
/* 23:27 */     for (Long localLong : localList) {
/* 24:28 */       localJSONArray.add(Convert.toUnsignedLong(localLong.longValue()));
/* 25:   */     }
/* 26:31 */     localJSONObject.put("nextBlockIds", localJSONArray);
/* 27:   */     
/* 28:33 */     return localJSONObject;
/* 29:   */   }
/* 30:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.peer.GetNextBlockIds
 * JD-Core Version:    0.7.1
 */