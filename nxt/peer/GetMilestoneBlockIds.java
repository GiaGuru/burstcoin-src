/*  1:   */ package nxt.peer;
/*  2:   */ 
/*  3:   */ import nxt.Block;
/*  4:   */ import nxt.Blockchain;
/*  5:   */ import nxt.Nxt;
/*  6:   */ import nxt.util.Convert;
/*  7:   */ import nxt.util.Logger;
/*  8:   */ import org.json.simple.JSONArray;
/*  9:   */ import org.json.simple.JSONObject;
/* 10:   */ import org.json.simple.JSONStreamAware;
/* 11:   */ 
/* 12:   */ final class GetMilestoneBlockIds
/* 13:   */   extends PeerServlet.PeerRequestHandler
/* 14:   */ {
/* 15:13 */   static final GetMilestoneBlockIds instance = new GetMilestoneBlockIds();
/* 16:   */   
/* 17:   */   JSONStreamAware processRequest(JSONObject paramJSONObject, Peer paramPeer)
/* 18:   */   {
/* 19:21 */     JSONObject localJSONObject = new JSONObject();
/* 20:   */     try
/* 21:   */     {
/* 22:24 */       JSONArray localJSONArray = new JSONArray();
/* 23:   */       
/* 24:26 */       String str1 = (String)paramJSONObject.get("lastBlockId");
/* 25:27 */       if (str1 != null)
/* 26:   */       {
/* 27:28 */         l1 = Convert.parseUnsignedLong(str1);
/* 28:29 */         long l2 = Nxt.getBlockchain().getLastBlock().getId();
/* 29:30 */         if ((l2 == l1) || (Nxt.getBlockchain().hasBlock(l1)))
/* 30:   */         {
/* 31:31 */           localJSONArray.add(str1);
/* 32:32 */           localJSONObject.put("milestoneBlockIds", localJSONArray);
/* 33:33 */           if (l2 == l1) {
/* 34:34 */             localJSONObject.put("last", Boolean.TRUE);
/* 35:   */           }
/* 36:36 */           return localJSONObject;
/* 37:   */         }
/* 38:   */       }
/* 39:43 */       int k = 10;
/* 40:44 */       int m = Nxt.getBlockchain().getHeight();
/* 41:45 */       String str2 = (String)paramJSONObject.get("lastMilestoneBlockId");
/* 42:   */       int i;
/* 43:   */       int j;
/* 44:46 */       if (str2 != null)
/* 45:   */       {
/* 46:47 */         Block localBlock = Nxt.getBlockchain().getBlock(Convert.parseUnsignedLong(str2));
/* 47:48 */         if (localBlock == null) {
/* 48:49 */           throw new IllegalStateException("Don't have block " + str2);
/* 49:   */         }
/* 50:51 */         i = localBlock.getHeight();
/* 51:52 */         j = Math.min(1440, Math.max(m - i, 1));
/* 52:53 */         i = Math.max(i - j, 0);
/* 53:   */       }
/* 54:54 */       else if (str1 != null)
/* 55:   */       {
/* 56:55 */         i = m;
/* 57:56 */         j = 10;
/* 58:   */       }
/* 59:   */       else
/* 60:   */       {
/* 61:58 */         paramPeer.blacklist();
/* 62:59 */         localJSONObject.put("error", "Old getMilestoneBlockIds protocol not supported, please upgrade");
/* 63:60 */         return localJSONObject;
/* 64:   */       }
/* 65:62 */       long l1 = Nxt.getBlockchain().getBlockIdAtHeight(i);
/* 66:64 */       while ((i > 0) && (k-- > 0))
/* 67:   */       {
/* 68:65 */         localJSONArray.add(Convert.toUnsignedLong(l1));
/* 69:66 */         l1 = Nxt.getBlockchain().getBlockIdAtHeight(i);
/* 70:67 */         i -= j;
/* 71:   */       }
/* 72:69 */       localJSONObject.put("milestoneBlockIds", localJSONArray);
/* 73:   */     }
/* 74:   */     catch (RuntimeException localRuntimeException)
/* 75:   */     {
/* 76:72 */       Logger.logDebugMessage(localRuntimeException.toString());
/* 77:73 */       localJSONObject.put("error", localRuntimeException.toString());
/* 78:   */     }
/* 79:76 */     return localJSONObject;
/* 80:   */   }
/* 81:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.peer.GetMilestoneBlockIds
 * JD-Core Version:    0.7.1
 */