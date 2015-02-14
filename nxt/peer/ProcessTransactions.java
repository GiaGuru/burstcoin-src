/*  1:   */ package nxt.peer;
/*  2:   */ 
/*  3:   */ final class ProcessTransactions
/*  4:   */   extends PeerServlet.PeerRequestHandler
/*  5:   */ {
/*  6:11 */   static final ProcessTransactions instance = new ProcessTransactions();
/*  7:   */   
/*  8:   */   /* Error */
/*  9:   */   org.json.simple.JSONStreamAware processRequest(org.json.simple.JSONObject paramJSONObject, Peer paramPeer)
/* 10:   */   {
/* 11:   */     // Byte code:
/* 12:   */     //   0: invokestatic 2	nxt/Nxt:getTransactionProcessor	()Lnxt/TransactionProcessor;
/* 13:   */     //   3: aload_1
/* 14:   */     //   4: invokeinterface 3 2 0
/* 15:   */     //   9: getstatic 4	nxt/util/JSON:emptyJSON	Lorg/json/simple/JSONStreamAware;
/* 16:   */     //   12: areturn
/* 17:   */     //   13: astore_3
/* 18:   */     //   14: aload_2
/* 19:   */     //   15: aload_3
/* 20:   */     //   16: invokeinterface 7 2 0
/* 21:   */     //   21: new 8	org/json/simple/JSONObject
/* 22:   */     //   24: dup
/* 23:   */     //   25: invokespecial 9	org/json/simple/JSONObject:<init>	()V
/* 24:   */     //   28: astore 4
/* 25:   */     //   30: aload 4
/* 26:   */     //   32: ldc 10
/* 27:   */     //   34: aload_3
/* 28:   */     //   35: invokevirtual 11	java/lang/Exception:toString	()Ljava/lang/String;
/* 29:   */     //   38: invokevirtual 12	org/json/simple/JSONObject:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
/* 30:   */     //   41: pop
/* 31:   */     //   42: aload 4
/* 32:   */     //   44: areturn
/* 33:   */     // Line number table:
/* 34:   */     //   Java source line #20	-> byte code offset #0
/* 35:   */     //   Java source line #21	-> byte code offset #9
/* 36:   */     //   Java source line #22	-> byte code offset #13
/* 37:   */     //   Java source line #24	-> byte code offset #14
/* 38:   */     //   Java source line #25	-> byte code offset #21
/* 39:   */     //   Java source line #26	-> byte code offset #30
/* 40:   */     //   Java source line #27	-> byte code offset #42
/* 41:   */     // Local variable table:
/* 42:   */     //   start	length	slot	name	signature
/* 43:   */     //   0	45	0	this	ProcessTransactions
/* 44:   */     //   0	45	1	paramJSONObject	org.json.simple.JSONObject
/* 45:   */     //   0	45	2	paramPeer	Peer
/* 46:   */     //   13	22	3	localRuntimeException	RuntimeException
/* 47:   */     //   28	15	4	localJSONObject	org.json.simple.JSONObject
/* 48:   */     // Exception table:
/* 49:   */     //   from	to	target	type
/* 50:   */     //   0	12	13	java/lang/RuntimeException
/* 51:   */     //   0	12	13	nxt/NxtException$ValidationException
/* 52:   */   }
/* 53:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.peer.ProcessTransactions
 * JD-Core Version:    0.7.1
 */