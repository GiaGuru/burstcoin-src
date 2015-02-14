/*  1:   */ package nxt.http;
/*  2:   */ 
/*  3:   */ import javax.servlet.http.HttpServletRequest;
/*  4:   */ import nxt.Block;
/*  5:   */ import nxt.Blockchain;
/*  6:   */ import nxt.EconomicClustering;
/*  7:   */ import nxt.Nxt;
/*  8:   */ import nxt.NxtException;
/*  9:   */ import org.json.simple.JSONObject;
/* 10:   */ import org.json.simple.JSONStreamAware;
/* 11:   */ 
/* 12:   */ public final class GetECBlock
/* 13:   */   extends APIServlet.APIRequestHandler
/* 14:   */ {
/* 15:14 */   static final GetECBlock instance = new GetECBlock();
/* 16:   */   
/* 17:   */   private GetECBlock()
/* 18:   */   {
/* 19:17 */     super(new APITag[] { APITag.BLOCKS }, new String[] { "timestamp" });
/* 20:   */   }
/* 21:   */   
/* 22:   */   JSONStreamAware processRequest(HttpServletRequest paramHttpServletRequest)
/* 23:   */     throws NxtException
/* 24:   */   {
/* 25:22 */     int i = ParameterParser.getTimestamp(paramHttpServletRequest);
/* 26:23 */     if (i == 0) {
/* 27:24 */       i = Nxt.getEpochTime();
/* 28:   */     }
/* 29:26 */     if (i < Nxt.getBlockchain().getLastBlock().getTimestamp() - 15) {
/* 30:27 */       return JSONResponses.INCORRECT_TIMESTAMP;
/* 31:   */     }
/* 32:29 */     Block localBlock = EconomicClustering.getECBlock(i);
/* 33:30 */     JSONObject localJSONObject = new JSONObject();
/* 34:31 */     localJSONObject.put("ecBlockId", localBlock.getStringId());
/* 35:32 */     localJSONObject.put("ecBlockHeight", Integer.valueOf(localBlock.getHeight()));
/* 36:33 */     localJSONObject.put("timestamp", Integer.valueOf(i));
/* 37:34 */     return localJSONObject;
/* 38:   */   }
/* 39:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.http.GetECBlock
 * JD-Core Version:    0.7.1
 */