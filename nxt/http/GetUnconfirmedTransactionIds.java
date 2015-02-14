/*  1:   */ package nxt.http;
/*  2:   */ 
/*  3:   */ import javax.servlet.http.HttpServletRequest;
/*  4:   */ import nxt.Nxt;
/*  5:   */ import nxt.Transaction;
/*  6:   */ import nxt.TransactionProcessor;
/*  7:   */ import nxt.db.DbIterator;
/*  8:   */ import nxt.util.Convert;
/*  9:   */ import org.json.simple.JSONArray;
/* 10:   */ import org.json.simple.JSONObject;
/* 11:   */ import org.json.simple.JSONStreamAware;
/* 12:   */ 
/* 13:   */ public final class GetUnconfirmedTransactionIds
/* 14:   */   extends APIServlet.APIRequestHandler
/* 15:   */ {
/* 16:17 */   static final GetUnconfirmedTransactionIds instance = new GetUnconfirmedTransactionIds();
/* 17:   */   
/* 18:   */   private GetUnconfirmedTransactionIds()
/* 19:   */   {
/* 20:20 */     super(new APITag[] { APITag.TRANSACTIONS, APITag.ACCOUNTS }, new String[] { "account" });
/* 21:   */   }
/* 22:   */   
/* 23:   */   JSONStreamAware processRequest(HttpServletRequest paramHttpServletRequest)
/* 24:   */   {
/* 25:26 */     String str = Convert.emptyToNull(paramHttpServletRequest.getParameter("account"));
/* 26:27 */     long l = 0L;
/* 27:29 */     if (str != null) {
/* 28:   */       try
/* 29:   */       {
/* 30:31 */         l = Convert.parseAccountId(str);
/* 31:   */       }
/* 32:   */       catch (RuntimeException localRuntimeException)
/* 33:   */       {
/* 34:33 */         return JSONResponses.INCORRECT_ACCOUNT;
/* 35:   */       }
/* 36:   */     }
/* 37:37 */     JSONArray localJSONArray = new JSONArray();
/* 38:38 */     Object localObject1 = Nxt.getTransactionProcessor().getAllUnconfirmedTransactions();Object localObject2 = null;
/* 39:   */     try
/* 40:   */     {
/* 41:39 */       while (((DbIterator)localObject1).hasNext())
/* 42:   */       {
/* 43:40 */         Transaction localTransaction = (Transaction)((DbIterator)localObject1).next();
/* 44:41 */         if ((l == 0L) || (l == localTransaction.getSenderId()) || (l == localTransaction.getRecipientId())) {
/* 45:44 */           localJSONArray.add(localTransaction.getStringId());
/* 46:   */         }
/* 47:   */       }
/* 48:   */     }
/* 49:   */     catch (Throwable localThrowable2)
/* 50:   */     {
/* 51:38 */       localObject2 = localThrowable2;throw localThrowable2;
/* 52:   */     }
/* 53:   */     finally
/* 54:   */     {
/* 55:46 */       if (localObject1 != null) {
/* 56:46 */         if (localObject2 != null) {
/* 57:   */           try
/* 58:   */           {
/* 59:46 */             ((DbIterator)localObject1).close();
/* 60:   */           }
/* 61:   */           catch (Throwable localThrowable3)
/* 62:   */           {
/* 63:46 */             ((Throwable)localObject2).addSuppressed(localThrowable3);
/* 64:   */           }
/* 65:   */         } else {
/* 66:46 */           ((DbIterator)localObject1).close();
/* 67:   */         }
/* 68:   */       }
/* 69:   */     }
/* 70:48 */     localObject1 = new JSONObject();
/* 71:49 */     ((JSONObject)localObject1).put("unconfirmedTransactionIds", localJSONArray);
/* 72:50 */     return (JSONStreamAware)localObject1;
/* 73:   */   }
/* 74:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.http.GetUnconfirmedTransactionIds
 * JD-Core Version:    0.7.1
 */