/*  1:   */ package nxt.http;
/*  2:   */ 
/*  3:   */ import javax.servlet.http.HttpServletRequest;
/*  4:   */ import nxt.Nxt;
/*  5:   */ import nxt.TransactionProcessor;
/*  6:   */ import org.json.simple.JSONObject;
/*  7:   */ import org.json.simple.JSONStreamAware;
/*  8:   */ 
/*  9:   */ public final class ClearUnconfirmedTransactions
/* 10:   */   extends APIServlet.APIRequestHandler
/* 11:   */ {
/* 12:11 */   static final ClearUnconfirmedTransactions instance = new ClearUnconfirmedTransactions();
/* 13:   */   
/* 14:   */   private ClearUnconfirmedTransactions()
/* 15:   */   {
/* 16:14 */     super(new APITag[] { APITag.DEBUG }, new String[0]);
/* 17:   */   }
/* 18:   */   
/* 19:   */   JSONStreamAware processRequest(HttpServletRequest paramHttpServletRequest)
/* 20:   */   {
/* 21:19 */     JSONObject localJSONObject = new JSONObject();
/* 22:   */     try
/* 23:   */     {
/* 24:21 */       Nxt.getTransactionProcessor().clearUnconfirmedTransactions();
/* 25:22 */       localJSONObject.put("done", Boolean.valueOf(true));
/* 26:   */     }
/* 27:   */     catch (RuntimeException localRuntimeException)
/* 28:   */     {
/* 29:24 */       localJSONObject.put("error", localRuntimeException.toString());
/* 30:   */     }
/* 31:26 */     return localJSONObject;
/* 32:   */   }
/* 33:   */   
/* 34:   */   final boolean requirePost()
/* 35:   */   {
/* 36:31 */     return true;
/* 37:   */   }
/* 38:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.http.ClearUnconfirmedTransactions
 * JD-Core Version:    0.7.1
 */