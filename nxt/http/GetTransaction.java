/*  1:   */ package nxt.http;
/*  2:   */ 
/*  3:   */ import javax.servlet.http.HttpServletRequest;
/*  4:   */ import nxt.Blockchain;
/*  5:   */ import nxt.Nxt;
/*  6:   */ import nxt.Transaction;
/*  7:   */ import nxt.TransactionProcessor;
/*  8:   */ import nxt.util.Convert;
/*  9:   */ import org.json.simple.JSONStreamAware;
/* 10:   */ 
/* 11:   */ public final class GetTransaction
/* 12:   */   extends APIServlet.APIRequestHandler
/* 13:   */ {
/* 14:16 */   static final GetTransaction instance = new GetTransaction();
/* 15:   */   
/* 16:   */   private GetTransaction()
/* 17:   */   {
/* 18:19 */     super(new APITag[] { APITag.TRANSACTIONS }, new String[] { "transaction", "fullHash" });
/* 19:   */   }
/* 20:   */   
/* 21:   */   JSONStreamAware processRequest(HttpServletRequest paramHttpServletRequest)
/* 22:   */   {
/* 23:25 */     String str1 = Convert.emptyToNull(paramHttpServletRequest.getParameter("transaction"));
/* 24:26 */     String str2 = Convert.emptyToNull(paramHttpServletRequest.getParameter("fullHash"));
/* 25:27 */     if ((str1 == null) && (str2 == null)) {
/* 26:28 */       return JSONResponses.MISSING_TRANSACTION;
/* 27:   */     }
/* 28:31 */     long l = 0L;
/* 29:   */     Transaction localTransaction;
/* 30:   */     try
/* 31:   */     {
/* 32:34 */       if (str1 != null)
/* 33:   */       {
/* 34:35 */         l = Convert.parseUnsignedLong(str1);
/* 35:36 */         localTransaction = Nxt.getBlockchain().getTransaction(l);
/* 36:   */       }
/* 37:   */       else
/* 38:   */       {
/* 39:38 */         localTransaction = Nxt.getBlockchain().getTransactionByFullHash(str2);
/* 40:39 */         if (localTransaction == null) {
/* 41:40 */           return JSONResponses.UNKNOWN_TRANSACTION;
/* 42:   */         }
/* 43:   */       }
/* 44:   */     }
/* 45:   */     catch (RuntimeException localRuntimeException)
/* 46:   */     {
/* 47:44 */       return JSONResponses.INCORRECT_TRANSACTION;
/* 48:   */     }
/* 49:47 */     if (localTransaction == null)
/* 50:   */     {
/* 51:48 */       localTransaction = Nxt.getTransactionProcessor().getUnconfirmedTransaction(l);
/* 52:49 */       if (localTransaction == null) {
/* 53:50 */         return JSONResponses.UNKNOWN_TRANSACTION;
/* 54:   */       }
/* 55:52 */       return JSONData.unconfirmedTransaction(localTransaction);
/* 56:   */     }
/* 57:54 */     return JSONData.transaction(localTransaction);
/* 58:   */   }
/* 59:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.http.GetTransaction
 * JD-Core Version:    0.7.1
 */