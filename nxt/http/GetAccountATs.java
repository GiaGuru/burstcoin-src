/*  1:   */ package nxt.http;
/*  2:   */ 
/*  3:   */ import java.util.Iterator;
/*  4:   */ import java.util.List;
/*  5:   */ import javax.servlet.http.HttpServletRequest;
/*  6:   */ import nxt.AT;
/*  7:   */ import nxt.Account;
/*  8:   */ import nxt.NxtException;
/*  9:   */ import org.json.simple.JSONArray;
/* 10:   */ import org.json.simple.JSONObject;
/* 11:   */ import org.json.simple.JSONStreamAware;
/* 12:   */ 
/* 13:   */ public final class GetAccountATs
/* 14:   */   extends APIServlet.APIRequestHandler
/* 15:   */ {
/* 16:17 */   static GetAccountATs instance = new GetAccountATs();
/* 17:   */   
/* 18:   */   private GetAccountATs()
/* 19:   */   {
/* 20:20 */     super(new APITag[] { APITag.AT, APITag.ACCOUNTS }, new String[] { "account" });
/* 21:   */   }
/* 22:   */   
/* 23:   */   JSONStreamAware processRequest(HttpServletRequest paramHttpServletRequest)
/* 24:   */     throws NxtException
/* 25:   */   {
/* 26:26 */     Account localAccount = ParameterParser.getAccount(paramHttpServletRequest);
/* 27:   */     
/* 28:28 */     List localList = AT.getATsIssuedBy(Long.valueOf(localAccount.getId()));
/* 29:29 */     JSONArray localJSONArray = new JSONArray();
/* 30:30 */     for (Object localObject = localList.iterator(); ((Iterator)localObject).hasNext();)
/* 31:   */     {
/* 32:30 */       long l = ((Long)((Iterator)localObject).next()).longValue();
/* 33:31 */       localJSONArray.add(JSONData.at(AT.getAT(Long.valueOf(l))));
/* 34:   */     }
/* 35:34 */     localObject = new JSONObject();
/* 36:35 */     ((JSONObject)localObject).put("ats", localJSONArray);
/* 37:36 */     return (JSONStreamAware)localObject;
/* 38:   */   }
/* 39:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.http.GetAccountATs
 * JD-Core Version:    0.7.1
 */