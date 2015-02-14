/*  1:   */ package nxt.http;
/*  2:   */ 
/*  3:   */ import java.util.Collection;
/*  4:   */ import javax.servlet.http.HttpServletRequest;
/*  5:   */ import nxt.Account;
/*  6:   */ import nxt.Escrow;
/*  7:   */ import nxt.NxtException;
/*  8:   */ import org.json.simple.JSONArray;
/*  9:   */ import org.json.simple.JSONObject;
/* 10:   */ import org.json.simple.JSONStreamAware;
/* 11:   */ 
/* 12:   */ public final class GetAccountEscrowTransactions
/* 13:   */   extends APIServlet.APIRequestHandler
/* 14:   */ {
/* 15:18 */   static final GetAccountEscrowTransactions instance = new GetAccountEscrowTransactions();
/* 16:   */   
/* 17:   */   private GetAccountEscrowTransactions()
/* 18:   */   {
/* 19:21 */     super(new APITag[] { APITag.ACCOUNTS }, new String[] { "account" });
/* 20:   */   }
/* 21:   */   
/* 22:   */   JSONStreamAware processRequest(HttpServletRequest paramHttpServletRequest)
/* 23:   */     throws NxtException
/* 24:   */   {
/* 25:27 */     Account localAccount = ParameterParser.getAccount(paramHttpServletRequest);
/* 26:   */     
/* 27:29 */     Collection localCollection = Escrow.getEscrowTransactionsByParticipent(Long.valueOf(localAccount.getId()));
/* 28:   */     
/* 29:31 */     JSONObject localJSONObject = new JSONObject();
/* 30:   */     
/* 31:33 */     JSONArray localJSONArray = new JSONArray();
/* 32:35 */     for (Escrow localEscrow : localCollection) {
/* 33:36 */       localJSONArray.add(JSONData.escrowTransaction(localEscrow));
/* 34:   */     }
/* 35:39 */     localJSONObject.put("escrows", localJSONArray);
/* 36:40 */     return localJSONObject;
/* 37:   */   }
/* 38:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.http.GetAccountEscrowTransactions
 * JD-Core Version:    0.7.1
 */