/*  1:   */ package nxt.peer;
/*  2:   */ 
/*  3:   */ import nxt.util.JSON;
/*  4:   */ import org.json.simple.JSONObject;
/*  5:   */ import org.json.simple.JSONStreamAware;
/*  6:   */ 
/*  7:   */ final class ProcessBlock
/*  8:   */   extends PeerServlet.PeerRequestHandler
/*  9:   */ {
/* 10:11 */   static final ProcessBlock instance = new ProcessBlock();
/* 11:   */   private static final JSONStreamAware ACCEPTED;
/* 12:   */   private static final JSONStreamAware NOT_ACCEPTED;
/* 13:   */   
/* 14:   */   /* Error */
/* 15:   */   JSONStreamAware processRequest(JSONObject paramJSONObject, Peer paramPeer)
/* 16:   */   {
/* 17:   */     // Byte code:
/* 18:   */     //   0: invokestatic 2	nxt/Nxt:getBlockchain	()Lnxt/Blockchain;
/* 19:   */     //   3: invokeinterface 3 1 0
/* 20:   */     //   8: invokeinterface 4 1 0
/* 21:   */     //   13: aload_1
/* 22:   */     //   14: ldc 5
/* 23:   */     //   16: invokevirtual 6	org/json/simple/JSONObject:get	(Ljava/lang/Object;)Ljava/lang/Object;
/* 24:   */     //   19: invokevirtual 7	java/lang/String:equals	(Ljava/lang/Object;)Z
/* 25:   */     //   22: ifne +7 -> 29
/* 26:   */     //   25: getstatic 8	nxt/peer/ProcessBlock:NOT_ACCEPTED	Lorg/json/simple/JSONStreamAware;
/* 27:   */     //   28: areturn
/* 28:   */     //   29: invokestatic 9	nxt/Nxt:getBlockchainProcessor	()Lnxt/BlockchainProcessor;
/* 29:   */     //   32: aload_1
/* 30:   */     //   33: invokeinterface 10 2 0
/* 31:   */     //   38: getstatic 11	nxt/peer/ProcessBlock:ACCEPTED	Lorg/json/simple/JSONStreamAware;
/* 32:   */     //   41: areturn
/* 33:   */     //   42: astore_3
/* 34:   */     //   43: aload_2
/* 35:   */     //   44: ifnull +10 -> 54
/* 36:   */     //   47: aload_2
/* 37:   */     //   48: aload_3
/* 38:   */     //   49: invokeinterface 14 2 0
/* 39:   */     //   54: getstatic 8	nxt/peer/ProcessBlock:NOT_ACCEPTED	Lorg/json/simple/JSONStreamAware;
/* 40:   */     //   57: areturn
/* 41:   */     // Line number table:
/* 42:   */     //   Java source line #34	-> byte code offset #0
/* 43:   */     //   Java source line #37	-> byte code offset #25
/* 44:   */     //   Java source line #39	-> byte code offset #29
/* 45:   */     //   Java source line #40	-> byte code offset #38
/* 46:   */     //   Java source line #42	-> byte code offset #42
/* 47:   */     //   Java source line #43	-> byte code offset #43
/* 48:   */     //   Java source line #44	-> byte code offset #47
/* 49:   */     //   Java source line #46	-> byte code offset #54
/* 50:   */     // Local variable table:
/* 51:   */     //   start	length	slot	name	signature
/* 52:   */     //   0	58	0	this	ProcessBlock
/* 53:   */     //   0	58	1	paramJSONObject	JSONObject
/* 54:   */     //   0	58	2	paramPeer	Peer
/* 55:   */     //   42	7	3	localNxtException	nxt.NxtException
/* 56:   */     // Exception table:
/* 57:   */     //   from	to	target	type
/* 58:   */     //   0	28	42	nxt/NxtException
/* 59:   */     //   0	28	42	java/lang/RuntimeException
/* 60:   */     //   29	41	42	nxt/NxtException
/* 61:   */     //   29	41	42	java/lang/RuntimeException
/* 62:   */   }
/* 63:   */   
/* 64:   */   static
/* 65:   */   {
/* 66:17 */     JSONObject localJSONObject = new JSONObject();
/* 67:18 */     localJSONObject.put("accepted", Boolean.valueOf(true));
/* 68:19 */     ACCEPTED = JSON.prepare(localJSONObject);
/* 69:   */     
/* 70:   */ 
/* 71:   */ 
/* 72:   */ 
/* 73:24 */     localJSONObject = new JSONObject();
/* 74:25 */     localJSONObject.put("accepted", Boolean.valueOf(false));
/* 75:26 */     NOT_ACCEPTED = JSON.prepare(localJSONObject);
/* 76:   */   }
/* 77:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.peer.ProcessBlock
 * JD-Core Version:    0.7.1
 */