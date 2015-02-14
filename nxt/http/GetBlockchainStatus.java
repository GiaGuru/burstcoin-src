/*  1:   */ package nxt.http;
/*  2:   */ 
/*  3:   */ import java.math.BigInteger;
/*  4:   */ import javax.servlet.http.HttpServletRequest;
/*  5:   */ import nxt.Block;
/*  6:   */ import nxt.Blockchain;
/*  7:   */ import nxt.BlockchainProcessor;
/*  8:   */ import nxt.Nxt;
/*  9:   */ import nxt.peer.Peer;
/* 10:   */ import org.json.simple.JSONObject;
/* 11:   */ import org.json.simple.JSONStreamAware;
/* 12:   */ 
/* 13:   */ public final class GetBlockchainStatus
/* 14:   */   extends APIServlet.APIRequestHandler
/* 15:   */ {
/* 16:14 */   static final GetBlockchainStatus instance = new GetBlockchainStatus();
/* 17:   */   
/* 18:   */   private GetBlockchainStatus()
/* 19:   */   {
/* 20:17 */     super(new APITag[] { APITag.BLOCKS, APITag.INFO }, new String[0]);
/* 21:   */   }
/* 22:   */   
/* 23:   */   JSONStreamAware processRequest(HttpServletRequest paramHttpServletRequest)
/* 24:   */   {
/* 25:22 */     JSONObject localJSONObject = new JSONObject();
/* 26:23 */     localJSONObject.put("application", "NRS");
/* 27:24 */     localJSONObject.put("version", "1.2.2");
/* 28:25 */     localJSONObject.put("time", Integer.valueOf(Nxt.getEpochTime()));
/* 29:26 */     Block localBlock = Nxt.getBlockchain().getLastBlock();
/* 30:27 */     localJSONObject.put("lastBlock", localBlock.getStringId());
/* 31:28 */     localJSONObject.put("cumulativeDifficulty", localBlock.getCumulativeDifficulty().toString());
/* 32:29 */     localJSONObject.put("numberOfBlocks", Integer.valueOf(localBlock.getHeight() + 1));
/* 33:30 */     BlockchainProcessor localBlockchainProcessor = Nxt.getBlockchainProcessor();
/* 34:31 */     Peer localPeer = localBlockchainProcessor.getLastBlockchainFeeder();
/* 35:32 */     localJSONObject.put("lastBlockchainFeeder", localPeer == null ? null : localPeer.getAnnouncedAddress());
/* 36:33 */     localJSONObject.put("lastBlockchainFeederHeight", Integer.valueOf(localBlockchainProcessor.getLastBlockchainFeederHeight()));
/* 37:34 */     localJSONObject.put("isScanning", Boolean.valueOf(localBlockchainProcessor.isScanning()));
/* 38:35 */     return localJSONObject;
/* 39:   */   }
/* 40:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.http.GetBlockchainStatus
 * JD-Core Version:    0.7.1
 */