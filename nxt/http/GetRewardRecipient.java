/*  1:   */ package nxt.http;
/*  2:   */ 
/*  3:   */ import javax.servlet.http.HttpServletRequest;
/*  4:   */ import nxt.Account;
/*  5:   */ import nxt.Account.RewardRecipientAssignment;
/*  6:   */ import nxt.Block;
/*  7:   */ import nxt.Blockchain;
/*  8:   */ import nxt.Nxt;
/*  9:   */ import nxt.NxtException;
/* 10:   */ import nxt.util.Convert;
/* 11:   */ import org.json.simple.JSONObject;
/* 12:   */ import org.json.simple.JSONStreamAware;
/* 13:   */ 
/* 14:   */ public final class GetRewardRecipient
/* 15:   */   extends APIServlet.APIRequestHandler
/* 16:   */ {
/* 17:17 */   static final GetRewardRecipient instance = new GetRewardRecipient();
/* 18:   */   
/* 19:   */   private GetRewardRecipient()
/* 20:   */   {
/* 21:20 */     super(new APITag[] { APITag.ACCOUNTS, APITag.MINING, APITag.INFO }, new String[] { "account" });
/* 22:   */   }
/* 23:   */   
/* 24:   */   JSONStreamAware processRequest(HttpServletRequest paramHttpServletRequest)
/* 25:   */     throws NxtException
/* 26:   */   {
/* 27:25 */     JSONObject localJSONObject = new JSONObject();
/* 28:   */     
/* 29:27 */     Account localAccount = ParameterParser.getAccount(paramHttpServletRequest);
/* 30:28 */     Account.RewardRecipientAssignment localRewardRecipientAssignment = localAccount.getRewardRecipientAssignment();
/* 31:29 */     long l = Nxt.getBlockchain().getLastBlock().getHeight();
/* 32:30 */     if ((localAccount == null) || (localRewardRecipientAssignment == null)) {
/* 33:31 */       localJSONObject.put("rewardRecipient", Convert.toUnsignedLong(localAccount.getId()));
/* 34:33 */     } else if (localRewardRecipientAssignment.getFromHeight() > l + 1L) {
/* 35:34 */       localJSONObject.put("rewardRecipient", Convert.toUnsignedLong(localRewardRecipientAssignment.getPrevRecipientId()));
/* 36:   */     } else {
/* 37:37 */       localJSONObject.put("rewardRecipient", Convert.toUnsignedLong(localRewardRecipientAssignment.getRecipientId()));
/* 38:   */     }
/* 39:40 */     return localJSONObject;
/* 40:   */   }
/* 41:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.http.GetRewardRecipient
 * JD-Core Version:    0.7.1
 */