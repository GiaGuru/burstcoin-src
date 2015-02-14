/*  1:   */ package nxt.peer;
/*  2:   */ 
/*  3:   */ import nxt.Account;
/*  4:   */ import nxt.Blockchain;
/*  5:   */ import nxt.Nxt;
/*  6:   */ import nxt.Transaction;
/*  7:   */ import nxt.db.DbIterator;
/*  8:   */ import nxt.http.JSONData;
/*  9:   */ import nxt.util.Convert;
/* 10:   */ import org.json.simple.JSONArray;
/* 11:   */ import org.json.simple.JSONObject;
/* 12:   */ import org.json.simple.JSONStreamAware;
/* 13:   */ 
/* 14:   */ public class GetAccountRecentTransactions
/* 15:   */   extends PeerServlet.PeerRequestHandler
/* 16:   */ {
/* 17:15 */   static final GetAccountRecentTransactions instance = new GetAccountRecentTransactions();
/* 18:   */   
/* 19:   */   JSONStreamAware processRequest(JSONObject paramJSONObject, Peer paramPeer)
/* 20:   */   {
/* 21:22 */     JSONObject localJSONObject = new JSONObject();
/* 22:   */     try
/* 23:   */     {
/* 24:25 */       Long localLong = Long.valueOf(Convert.parseAccountId((String)paramJSONObject.get("account")));
/* 25:26 */       Account localAccount = Account.getAccount(localLong.longValue());
/* 26:27 */       JSONArray localJSONArray = new JSONArray();
/* 27:28 */       if (localAccount != null)
/* 28:   */       {
/* 29:29 */         DbIterator localDbIterator = Nxt.getBlockchain().getTransactions(localAccount, 0, (byte)-1, (byte)0, 0, 0, 9);
/* 30:30 */         while (localDbIterator.hasNext())
/* 31:   */         {
/* 32:31 */           Transaction localTransaction = (Transaction)localDbIterator.next();
/* 33:32 */           localJSONArray.add(JSONData.transaction(localTransaction));
/* 34:   */         }
/* 35:   */       }
/* 36:35 */       localJSONObject.put("transactions", localJSONArray);
/* 37:   */     }
/* 38:   */     catch (Exception localException) {}
/* 39:40 */     return localJSONObject;
/* 40:   */   }
/* 41:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.peer.GetAccountRecentTransactions
 * JD-Core Version:    0.7.1
 */