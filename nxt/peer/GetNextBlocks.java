/*  1:   */ package nxt.peer;
/*  2:   */ 
/*  3:   */ import java.util.ArrayList;
/*  4:   */ import java.util.Iterator;
/*  5:   */ import java.util.List;
/*  6:   */ import nxt.Block;
/*  7:   */ import nxt.Blockchain;
/*  8:   */ import nxt.Nxt;
/*  9:   */ import nxt.util.Convert;
/* 10:   */ import org.json.simple.JSONArray;
/* 11:   */ import org.json.simple.JSONObject;
/* 12:   */ import org.json.simple.JSONStreamAware;
/* 13:   */ 
/* 14:   */ final class GetNextBlocks
/* 15:   */   extends PeerServlet.PeerRequestHandler
/* 16:   */ {
/* 17:16 */   static final GetNextBlocks instance = new GetNextBlocks();
/* 18:   */   
/* 19:   */   JSONStreamAware processRequest(JSONObject paramJSONObject, Peer paramPeer)
/* 20:   */   {
/* 21:24 */     JSONObject localJSONObject = new JSONObject();
/* 22:   */     
/* 23:26 */     ArrayList localArrayList = new ArrayList();
/* 24:27 */     int i = 0;
/* 25:28 */     long l = Convert.parseUnsignedLong((String)paramJSONObject.get("blockId"));
/* 26:29 */     List localList = Nxt.getBlockchain().getBlocksAfter(l, 1440);
/* 27:31 */     for (Object localObject1 = localList.iterator(); ((Iterator)localObject1).hasNext();)
/* 28:   */     {
/* 29:31 */       localObject2 = (Block)((Iterator)localObject1).next();
/* 30:32 */       int j = 232 + ((Block)localObject2).getPayloadLength();
/* 31:33 */       if (i + j > 1048576) {
/* 32:   */         break;
/* 33:   */       }
/* 34:36 */       localArrayList.add(localObject2);
/* 35:37 */       i += j;
/* 36:   */     }
/* 37:40 */     localObject1 = new JSONArray();
/* 38:41 */     for (Object localObject2 = localArrayList.iterator(); ((Iterator)localObject2).hasNext();)
/* 39:   */     {
/* 40:41 */       Block localBlock = (Block)((Iterator)localObject2).next();
/* 41:42 */       ((JSONArray)localObject1).add(localBlock.getJSONObject());
/* 42:   */     }
/* 43:44 */     localJSONObject.put("nextBlocks", localObject1);
/* 44:   */     
/* 45:46 */     return localJSONObject;
/* 46:   */   }
/* 47:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.peer.GetNextBlocks
 * JD-Core Version:    0.7.1
 */