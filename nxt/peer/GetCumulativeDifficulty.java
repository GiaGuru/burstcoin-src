/*  1:   */ package nxt.peer;
/*  2:   */ 
/*  3:   */ import java.math.BigInteger;
/*  4:   */ import nxt.Block;
/*  5:   */ import nxt.Blockchain;
/*  6:   */ import nxt.Nxt;
/*  7:   */ import org.json.simple.JSONObject;
/*  8:   */ import org.json.simple.JSONStreamAware;
/*  9:   */ 
/* 10:   */ final class GetCumulativeDifficulty
/* 11:   */   extends PeerServlet.PeerRequestHandler
/* 12:   */ {
/* 13:10 */   static final GetCumulativeDifficulty instance = new GetCumulativeDifficulty();
/* 14:   */   
/* 15:   */   JSONStreamAware processRequest(JSONObject paramJSONObject, Peer paramPeer)
/* 16:   */   {
/* 17:18 */     JSONObject localJSONObject = new JSONObject();
/* 18:   */     
/* 19:20 */     Block localBlock = Nxt.getBlockchain().getLastBlock();
/* 20:21 */     localJSONObject.put("cumulativeDifficulty", localBlock.getCumulativeDifficulty().toString());
/* 21:22 */     localJSONObject.put("blockchainHeight", Integer.valueOf(localBlock.getHeight()));
/* 22:23 */     return localJSONObject;
/* 23:   */   }
/* 24:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.peer.GetCumulativeDifficulty
 * JD-Core Version:    0.7.1
 */