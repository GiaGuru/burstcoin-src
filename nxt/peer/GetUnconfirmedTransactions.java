/*  1:   */ package nxt.peer;
/*  2:   */ 
/*  3:   */ import nxt.Nxt;
/*  4:   */ import nxt.Transaction;
/*  5:   */ import nxt.TransactionProcessor;
/*  6:   */ import nxt.db.DbIterator;
/*  7:   */ import org.json.simple.JSONArray;
/*  8:   */ import org.json.simple.JSONObject;
/*  9:   */ import org.json.simple.JSONStreamAware;
/* 10:   */ 
/* 11:   */ final class GetUnconfirmedTransactions
/* 12:   */   extends PeerServlet.PeerRequestHandler
/* 13:   */ {
/* 14:12 */   static final GetUnconfirmedTransactions instance = new GetUnconfirmedTransactions();
/* 15:   */   
/* 16:   */   JSONStreamAware processRequest(JSONObject paramJSONObject, Peer paramPeer)
/* 17:   */   {
/* 18:20 */     JSONObject localJSONObject = new JSONObject();
/* 19:   */     
/* 20:22 */     JSONArray localJSONArray = new JSONArray();
/* 21:23 */     DbIterator localDbIterator = Nxt.getTransactionProcessor().getAllUnconfirmedTransactions();Object localObject1 = null;
/* 22:   */     try
/* 23:   */     {
/* 24:24 */       while (localDbIterator.hasNext())
/* 25:   */       {
/* 26:25 */         Transaction localTransaction = (Transaction)localDbIterator.next();
/* 27:26 */         localJSONArray.add(localTransaction.getJSONObject());
/* 28:   */       }
/* 29:   */     }
/* 30:   */     catch (Throwable localThrowable2)
/* 31:   */     {
/* 32:23 */       localObject1 = localThrowable2;throw localThrowable2;
/* 33:   */     }
/* 34:   */     finally
/* 35:   */     {
/* 36:28 */       if (localDbIterator != null) {
/* 37:28 */         if (localObject1 != null) {
/* 38:   */           try
/* 39:   */           {
/* 40:28 */             localDbIterator.close();
/* 41:   */           }
/* 42:   */           catch (Throwable localThrowable3)
/* 43:   */           {
/* 44:28 */             ((Throwable)localObject1).addSuppressed(localThrowable3);
/* 45:   */           }
/* 46:   */         } else {
/* 47:28 */           localDbIterator.close();
/* 48:   */         }
/* 49:   */       }
/* 50:   */     }
/* 51:29 */     localJSONObject.put("unconfirmedTransactions", localJSONArray);
/* 52:   */     
/* 53:   */ 
/* 54:32 */     return localJSONObject;
/* 55:   */   }
/* 56:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.peer.GetUnconfirmedTransactions
 * JD-Core Version:    0.7.1
 */