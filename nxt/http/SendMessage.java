/*  1:   */ package nxt.http;
/*  2:   */ 
/*  3:   */ import javax.servlet.http.HttpServletRequest;
/*  4:   */ import nxt.Account;
/*  5:   */ import nxt.Attachment;
/*  6:   */ import nxt.NxtException;
/*  7:   */ import org.json.simple.JSONStreamAware;
/*  8:   */ 
/*  9:   */ public final class SendMessage
/* 10:   */   extends CreateTransaction
/* 11:   */ {
/* 12:12 */   static final SendMessage instance = new SendMessage();
/* 13:   */   
/* 14:   */   private SendMessage()
/* 15:   */   {
/* 16:15 */     super(new APITag[] { APITag.MESSAGES, APITag.CREATE_TRANSACTION }, new String[] { "recipient" });
/* 17:   */   }
/* 18:   */   
/* 19:   */   JSONStreamAware processRequest(HttpServletRequest paramHttpServletRequest)
/* 20:   */     throws NxtException
/* 21:   */   {
/* 22:20 */     long l = ParameterParser.getRecipientId(paramHttpServletRequest);
/* 23:21 */     Account localAccount = ParameterParser.getSenderAccount(paramHttpServletRequest);
/* 24:22 */     return createTransaction(paramHttpServletRequest, localAccount, Long.valueOf(l), 0L, Attachment.ARBITRARY_MESSAGE);
/* 25:   */   }
/* 26:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.http.SendMessage
 * JD-Core Version:    0.7.1
 */