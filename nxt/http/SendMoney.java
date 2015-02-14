/*  1:   */ package nxt.http;
/*  2:   */ 
/*  3:   */ import javax.servlet.http.HttpServletRequest;
/*  4:   */ import nxt.Account;
/*  5:   */ import nxt.NxtException;
/*  6:   */ import org.json.simple.JSONStreamAware;
/*  7:   */ 
/*  8:   */ public final class SendMoney
/*  9:   */   extends CreateTransaction
/* 10:   */ {
/* 11:11 */   static final SendMoney instance = new SendMoney();
/* 12:   */   
/* 13:   */   private SendMoney()
/* 14:   */   {
/* 15:14 */     super(new APITag[] { APITag.ACCOUNTS, APITag.CREATE_TRANSACTION }, new String[] { "recipient", "amountNQT" });
/* 16:   */   }
/* 17:   */   
/* 18:   */   JSONStreamAware processRequest(HttpServletRequest paramHttpServletRequest)
/* 19:   */     throws NxtException
/* 20:   */   {
/* 21:19 */     long l1 = ParameterParser.getRecipientId(paramHttpServletRequest);
/* 22:20 */     long l2 = ParameterParser.getAmountNQT(paramHttpServletRequest);
/* 23:21 */     Account localAccount = ParameterParser.getSenderAccount(paramHttpServletRequest);
/* 24:22 */     return createTransaction(paramHttpServletRequest, localAccount, Long.valueOf(l1), l2);
/* 25:   */   }
/* 26:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.http.SendMoney
 * JD-Core Version:    0.7.1
 */