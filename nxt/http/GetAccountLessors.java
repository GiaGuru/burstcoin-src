/*  1:   */ package nxt.http;
/*  2:   */ 
/*  3:   */ import javax.servlet.http.HttpServletRequest;
/*  4:   */ import nxt.Account;
/*  5:   */ import nxt.Blockchain;
/*  6:   */ import nxt.Nxt;
/*  7:   */ import nxt.NxtException;
/*  8:   */ import nxt.db.DbIterator;
/*  9:   */ import org.json.simple.JSONArray;
/* 10:   */ import org.json.simple.JSONObject;
/* 11:   */ import org.json.simple.JSONStreamAware;
/* 12:   */ 
/* 13:   */ public final class GetAccountLessors
/* 14:   */   extends APIServlet.APIRequestHandler
/* 15:   */ {
/* 16:15 */   static final GetAccountLessors instance = new GetAccountLessors();
/* 17:   */   
/* 18:   */   private GetAccountLessors()
/* 19:   */   {
/* 20:18 */     super(new APITag[] { APITag.ACCOUNTS }, new String[] { "account", "height" });
/* 21:   */   }
/* 22:   */   
/* 23:   */   JSONStreamAware processRequest(HttpServletRequest paramHttpServletRequest)
/* 24:   */     throws NxtException
/* 25:   */   {
/* 26:24 */     Account localAccount1 = ParameterParser.getAccount(paramHttpServletRequest);
/* 27:25 */     int i = ParameterParser.getHeight(paramHttpServletRequest);
/* 28:26 */     if (i < 0) {
/* 29:27 */       i = Nxt.getBlockchain().getHeight();
/* 30:   */     }
/* 31:30 */     JSONObject localJSONObject1 = new JSONObject();
/* 32:31 */     JSONData.putAccount(localJSONObject1, "account", localAccount1.getId());
/* 33:32 */     localJSONObject1.put("height", Integer.valueOf(i));
/* 34:33 */     JSONArray localJSONArray = new JSONArray();
/* 35:   */     
/* 36:35 */     DbIterator localDbIterator = localAccount1.getLessors(i);Object localObject1 = null;
/* 37:   */     try
/* 38:   */     {
/* 39:36 */       if (localDbIterator.hasNext()) {
/* 40:37 */         while (localDbIterator.hasNext())
/* 41:   */         {
/* 42:38 */           Account localAccount2 = (Account)localDbIterator.next();
/* 43:39 */           JSONObject localJSONObject2 = new JSONObject();
/* 44:40 */           JSONData.putAccount(localJSONObject2, "lessor", localAccount2.getId());
/* 45:41 */           localJSONObject2.put("guaranteedBalanceNQT", String.valueOf(localAccount1.getGuaranteedBalanceNQT(1440, i)));
/* 46:42 */           localJSONArray.add(localJSONObject2);
/* 47:   */         }
/* 48:   */       }
/* 49:   */     }
/* 50:   */     catch (Throwable localThrowable2)
/* 51:   */     {
/* 52:35 */       localObject1 = localThrowable2;throw localThrowable2;
/* 53:   */     }
/* 54:   */     finally
/* 55:   */     {
/* 56:45 */       if (localDbIterator != null) {
/* 57:45 */         if (localObject1 != null) {
/* 58:   */           try
/* 59:   */           {
/* 60:45 */             localDbIterator.close();
/* 61:   */           }
/* 62:   */           catch (Throwable localThrowable3)
/* 63:   */           {
/* 64:45 */             ((Throwable)localObject1).addSuppressed(localThrowable3);
/* 65:   */           }
/* 66:   */         } else {
/* 67:45 */           localDbIterator.close();
/* 68:   */         }
/* 69:   */       }
/* 70:   */     }
/* 71:46 */     localJSONObject1.put("lessors", localJSONArray);
/* 72:47 */     return localJSONObject1;
/* 73:   */   }
/* 74:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.http.GetAccountLessors
 * JD-Core Version:    0.7.1
 */