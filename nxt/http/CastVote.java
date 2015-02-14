/*  1:   */ package nxt.http;
/*  2:   */ 
/*  3:   */ import javax.servlet.http.HttpServletRequest;
/*  4:   */ import nxt.Account;
/*  5:   */ import nxt.Attachment;
/*  6:   */ import nxt.Attachment.MessagingVoteCasting;
/*  7:   */ import nxt.NxtException;
/*  8:   */ import nxt.Poll;
/*  9:   */ import nxt.util.Convert;
/* 10:   */ import org.json.simple.JSONStreamAware;
/* 11:   */ 
/* 12:   */ public final class CastVote
/* 13:   */   extends CreateTransaction
/* 14:   */ {
/* 15:18 */   static final CastVote instance = new CastVote();
/* 16:   */   
/* 17:   */   private CastVote()
/* 18:   */   {
/* 19:21 */     super(new APITag[] { APITag.VS, APITag.CREATE_TRANSACTION }, new String[] { "poll", "vote1", "vote2", "vote3" });
/* 20:   */   }
/* 21:   */   
/* 22:   */   JSONStreamAware processRequest(HttpServletRequest paramHttpServletRequest)
/* 23:   */     throws NxtException
/* 24:   */   {
/* 25:27 */     String str = paramHttpServletRequest.getParameter("poll");
/* 26:29 */     if (str == null) {
/* 27:30 */       return JSONResponses.MISSING_POLL;
/* 28:   */     }
/* 29:34 */     int i = 0;
/* 30:   */     Poll localPoll;
/* 31:   */     try
/* 32:   */     {
/* 33:36 */       localPoll = Poll.getPoll(Convert.parseUnsignedLong(str));
/* 34:37 */       if (localPoll != null) {
/* 35:38 */         i = localPoll.getOptions().length;
/* 36:   */       } else {
/* 37:40 */         return JSONResponses.INCORRECT_POLL;
/* 38:   */       }
/* 39:   */     }
/* 40:   */     catch (RuntimeException localRuntimeException)
/* 41:   */     {
/* 42:43 */       return JSONResponses.INCORRECT_POLL;
/* 43:   */     }
/* 44:46 */     byte[] arrayOfByte = new byte[i];
/* 45:   */     try
/* 46:   */     {
/* 47:48 */       for (int j = 0; j < i; j++)
/* 48:   */       {
/* 49:49 */         localObject = paramHttpServletRequest.getParameter("vote" + j);
/* 50:50 */         if (localObject != null) {
/* 51:51 */           arrayOfByte[j] = Byte.parseByte((String)localObject);
/* 52:   */         }
/* 53:   */       }
/* 54:   */     }
/* 55:   */     catch (NumberFormatException localNumberFormatException)
/* 56:   */     {
/* 57:55 */       return JSONResponses.INCORRECT_VOTE;
/* 58:   */     }
/* 59:58 */     Account localAccount = ParameterParser.getSenderAccount(paramHttpServletRequest);
/* 60:   */     
/* 61:60 */     Object localObject = new Attachment.MessagingVoteCasting(localPoll.getId(), arrayOfByte);
/* 62:61 */     return createTransaction(paramHttpServletRequest, localAccount, (Attachment)localObject);
/* 63:   */   }
/* 64:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.http.CastVote
 * JD-Core Version:    0.7.1
 */