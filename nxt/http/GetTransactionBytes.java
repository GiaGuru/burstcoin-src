/*  1:   */ package nxt.http;
/*  2:   */ 
/*  3:   */ import javax.servlet.http.HttpServletRequest;
/*  4:   */ import nxt.Blockchain;
/*  5:   */ import nxt.Nxt;
/*  6:   */ import nxt.Transaction;
/*  7:   */ import nxt.TransactionProcessor;
/*  8:   */ import nxt.util.Convert;
/*  9:   */ import org.json.simple.JSONObject;
/* 10:   */ import org.json.simple.JSONStreamAware;
/* 11:   */ 
/* 12:   */ public final class GetTransactionBytes
/* 13:   */   extends APIServlet.APIRequestHandler
/* 14:   */ {
/* 15:17 */   static final GetTransactionBytes instance = new GetTransactionBytes();
/* 16:   */   
/* 17:   */   private GetTransactionBytes()
/* 18:   */   {
/* 19:20 */     super(new APITag[] { APITag.TRANSACTIONS }, new String[] { "transaction" });
/* 20:   */   }
/* 21:   */   
/* 22:   */   JSONStreamAware processRequest(HttpServletRequest paramHttpServletRequest)
/* 23:   */   {
/* 24:26 */     String str = paramHttpServletRequest.getParameter("transaction");
/* 25:27 */     if (str == null) {
/* 26:28 */       return JSONResponses.MISSING_TRANSACTION;
/* 27:   */     }
/* 28:   */     long l;
/* 29:   */     try
/* 30:   */     {
/* 31:34 */       l = Convert.parseUnsignedLong(str);
/* 32:   */     }
/* 33:   */     catch (RuntimeException localRuntimeException)
/* 34:   */     {
/* 35:36 */       return JSONResponses.INCORRECT_TRANSACTION;
/* 36:   */     }
/* 37:39 */     Transaction localTransaction = Nxt.getBlockchain().getTransaction(l);
/* 38:40 */     JSONObject localJSONObject = new JSONObject();
/* 39:41 */     if (localTransaction == null)
/* 40:   */     {
/* 41:42 */       localTransaction = Nxt.getTransactionProcessor().getUnconfirmedTransaction(l);
/* 42:43 */       if (localTransaction == null) {
/* 43:44 */         return JSONResponses.UNKNOWN_TRANSACTION;
/* 44:   */       }
/* 45:   */     }
/* 46:   */     else
/* 47:   */     {
/* 48:47 */       localJSONObject.put("confirmations", Integer.valueOf(Nxt.getBlockchain().getHeight() - localTransaction.getHeight()));
/* 49:   */     }
/* 50:49 */     localJSONObject.put("transactionBytes", Convert.toHexString(localTransaction.getBytes()));
/* 51:50 */     localJSONObject.put("unsignedTransactionBytes", Convert.toHexString(localTransaction.getUnsignedBytes()));
/* 52:51 */     return localJSONObject;
/* 53:   */   }
/* 54:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.http.GetTransactionBytes
 * JD-Core Version:    0.7.1
 */