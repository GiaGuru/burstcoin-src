/*  1:   */ package nxt.http;
/*  2:   */ 
/*  3:   */ import javax.servlet.http.HttpServletRequest;
/*  4:   */ import nxt.Account;
/*  5:   */ import nxt.NxtException;
/*  6:   */ import org.json.simple.JSONObject;
/*  7:   */ import org.json.simple.JSONStreamAware;
/*  8:   */ 
/*  9:   */ public final class GetGuaranteedBalance
/* 10:   */   extends APIServlet.APIRequestHandler
/* 11:   */ {
/* 12:12 */   static final GetGuaranteedBalance instance = new GetGuaranteedBalance();
/* 13:   */   
/* 14:   */   private GetGuaranteedBalance()
/* 15:   */   {
/* 16:15 */     super(new APITag[] { APITag.ACCOUNTS, APITag.FORGING }, new String[] { "account", "numberOfConfirmations" });
/* 17:   */   }
/* 18:   */   
/* 19:   */   JSONStreamAware processRequest(HttpServletRequest paramHttpServletRequest)
/* 20:   */     throws NxtException
/* 21:   */   {
/* 22:21 */     Account localAccount = ParameterParser.getAccount(paramHttpServletRequest);
/* 23:22 */     int i = ParameterParser.getNumberOfConfirmations(paramHttpServletRequest);
/* 24:   */     
/* 25:24 */     JSONObject localJSONObject = new JSONObject();
/* 26:25 */     if (localAccount == null) {
/* 27:26 */       localJSONObject.put("guaranteedBalanceNQT", "0");
/* 28:   */     } else {
/* 29:28 */       localJSONObject.put("guaranteedBalanceNQT", String.valueOf(localAccount.getGuaranteedBalanceNQT(i)));
/* 30:   */     }
/* 31:31 */     return localJSONObject;
/* 32:   */   }
/* 33:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.http.GetGuaranteedBalance
 * JD-Core Version:    0.7.1
 */