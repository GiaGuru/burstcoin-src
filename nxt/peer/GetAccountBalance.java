/*  1:   */ package nxt.peer;
/*  2:   */ 
/*  3:   */ import nxt.Account;
/*  4:   */ import nxt.util.Convert;
/*  5:   */ import org.json.simple.JSONObject;
/*  6:   */ import org.json.simple.JSONStreamAware;
/*  7:   */ 
/*  8:   */ public class GetAccountBalance
/*  9:   */   extends PeerServlet.PeerRequestHandler
/* 10:   */ {
/* 11:10 */   static final GetAccountBalance instance = new GetAccountBalance();
/* 12:   */   
/* 13:   */   JSONStreamAware processRequest(JSONObject paramJSONObject, Peer paramPeer)
/* 14:   */   {
/* 15:17 */     JSONObject localJSONObject = new JSONObject();
/* 16:   */     try
/* 17:   */     {
/* 18:20 */       Long localLong = Long.valueOf(Convert.parseAccountId((String)paramJSONObject.get("account")));
/* 19:21 */       Account localAccount = Account.getAccount(localLong.longValue());
/* 20:22 */       if (localAccount != null) {
/* 21:23 */         localJSONObject.put("balanceNQT", Convert.toUnsignedLong(localAccount.getBalanceNQT()));
/* 22:   */       } else {
/* 23:26 */         localJSONObject.put("balanceNQT", "0");
/* 24:   */       }
/* 25:   */     }
/* 26:   */     catch (Exception localException) {}
/* 27:32 */     return localJSONObject;
/* 28:   */   }
/* 29:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.peer.GetAccountBalance
 * JD-Core Version:    0.7.1
 */