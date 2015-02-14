/*  1:   */ package nxt.http;
/*  2:   */ 
/*  3:   */ import javax.servlet.http.HttpServletRequest;
/*  4:   */ import nxt.Account;
/*  5:   */ import nxt.Account.RewardRecipientAssignment;
/*  6:   */ import nxt.Block;
/*  7:   */ import nxt.Blockchain;
/*  8:   */ import nxt.Nxt;
/*  9:   */ import nxt.NxtException;
/* 10:   */ import nxt.db.DbIterator;
/* 11:   */ import nxt.util.Convert;
/* 12:   */ import org.json.simple.JSONArray;
/* 13:   */ import org.json.simple.JSONObject;
/* 14:   */ import org.json.simple.JSONStreamAware;
/* 15:   */ 
/* 16:   */ public final class GetAccountsWithRewardRecipient
/* 17:   */   extends APIServlet.APIRequestHandler
/* 18:   */ {
/* 19:19 */   static final GetAccountsWithRewardRecipient instance = new GetAccountsWithRewardRecipient();
/* 20:   */   
/* 21:   */   private GetAccountsWithRewardRecipient()
/* 22:   */   {
/* 23:22 */     super(new APITag[] { APITag.ACCOUNTS, APITag.MINING, APITag.INFO }, new String[] { "account" });
/* 24:   */   }
/* 25:   */   
/* 26:   */   JSONStreamAware processRequest(HttpServletRequest paramHttpServletRequest)
/* 27:   */     throws NxtException
/* 28:   */   {
/* 29:27 */     JSONObject localJSONObject = new JSONObject();
/* 30:   */     
/* 31:29 */     Account localAccount = ParameterParser.getAccount(paramHttpServletRequest);
/* 32:   */     
/* 33:31 */     long l = Nxt.getBlockchain().getLastBlock().getHeight();
/* 34:   */     
/* 35:33 */     JSONArray localJSONArray = new JSONArray();
/* 36:   */     
/* 37:   */ 
/* 38:   */ 
/* 39:   */ 
/* 40:   */ 
/* 41:   */ 
/* 42:   */ 
/* 43:   */ 
/* 44:   */ 
/* 45:   */ 
/* 46:   */ 
/* 47:   */ 
/* 48:46 */     DbIterator localDbIterator = Account.getAccountsWithRewardRecipient(Long.valueOf(localAccount.getId()));
/* 49:47 */     while (localDbIterator.hasNext())
/* 50:   */     {
/* 51:48 */       Account.RewardRecipientAssignment localRewardRecipientAssignment = (Account.RewardRecipientAssignment)localDbIterator.next();
/* 52:49 */       localJSONArray.add(Convert.toUnsignedLong(localRewardRecipientAssignment.accountId.longValue()));
/* 53:   */     }
/* 54:51 */     if (localAccount.getRewardRecipientAssignment() == null) {
/* 55:52 */       localJSONArray.add(Convert.toUnsignedLong(localAccount.getId()));
/* 56:   */     }
/* 57:55 */     localJSONObject.put("accounts", localJSONArray);
/* 58:   */     
/* 59:57 */     return localJSONObject;
/* 60:   */   }
/* 61:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.http.GetAccountsWithRewardRecipient
 * JD-Core Version:    0.7.1
 */