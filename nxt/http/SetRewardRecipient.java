/*  1:   */ package nxt.http;
/*  2:   */ 
/*  3:   */ import javax.servlet.http.HttpServletRequest;
/*  4:   */ import nxt.Account;
/*  5:   */ import nxt.Attachment;
/*  6:   */ import nxt.Attachment.BurstMiningRewardRecipientAssignment;
/*  7:   */ import nxt.NxtException;
/*  8:   */ import org.json.simple.JSONObject;
/*  9:   */ import org.json.simple.JSONStreamAware;
/* 10:   */ 
/* 11:   */ public final class SetRewardRecipient
/* 12:   */   extends CreateTransaction
/* 13:   */ {
/* 14:16 */   static final SetRewardRecipient instance = new SetRewardRecipient();
/* 15:   */   
/* 16:   */   private SetRewardRecipient()
/* 17:   */   {
/* 18:19 */     super(new APITag[] { APITag.ACCOUNTS, APITag.MINING, APITag.CREATE_TRANSACTION }, new String[] { "recipient" });
/* 19:   */   }
/* 20:   */   
/* 21:   */   JSONStreamAware processRequest(HttpServletRequest paramHttpServletRequest)
/* 22:   */     throws NxtException
/* 23:   */   {
/* 24:24 */     Account localAccount1 = ParameterParser.getSenderAccount(paramHttpServletRequest);
/* 25:25 */     Long localLong = Long.valueOf(ParameterParser.getRecipientId(paramHttpServletRequest));
/* 26:26 */     Account localAccount2 = Account.getAccount(localLong.longValue());
/* 27:27 */     if ((localAccount2 == null) || (localAccount2.getPublicKey() == null))
/* 28:   */     {
/* 29:28 */       localObject = new JSONObject();
/* 30:29 */       ((JSONObject)localObject).put("errorCode", Integer.valueOf(8));
/* 31:30 */       ((JSONObject)localObject).put("errorDescription", "recipient account does not have public key");
/* 32:31 */       return (JSONStreamAware)localObject;
/* 33:   */     }
/* 34:33 */     Object localObject = new Attachment.BurstMiningRewardRecipientAssignment();
/* 35:34 */     return createTransaction(paramHttpServletRequest, localAccount1, localLong, 0L, (Attachment)localObject);
/* 36:   */   }
/* 37:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.http.SetRewardRecipient
 * JD-Core Version:    0.7.1
 */