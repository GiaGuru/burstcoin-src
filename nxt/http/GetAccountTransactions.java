/*  1:   */ package nxt.http;
/*  2:   */ 
/*  3:   */ import javax.servlet.http.HttpServletRequest;
/*  4:   */ import nxt.Account;
/*  5:   */ import nxt.Blockchain;
/*  6:   */ import nxt.Nxt;
/*  7:   */ import nxt.NxtException;
/*  8:   */ import nxt.Transaction;
/*  9:   */ import nxt.db.DbIterator;
/* 10:   */ import org.json.simple.JSONArray;
/* 11:   */ import org.json.simple.JSONObject;
/* 12:   */ import org.json.simple.JSONStreamAware;
/* 13:   */ 
/* 14:   */ public final class GetAccountTransactions
/* 15:   */   extends APIServlet.APIRequestHandler
/* 16:   */ {
/* 17:16 */   static final GetAccountTransactions instance = new GetAccountTransactions();
/* 18:   */   
/* 19:   */   private GetAccountTransactions()
/* 20:   */   {
/* 21:19 */     super(new APITag[] { APITag.ACCOUNTS }, new String[] { "account", "timestamp", "type", "subtype", "firstIndex", "lastIndex", "numberOfConfirmations" });
/* 22:   */   }
/* 23:   */   
/* 24:   */   JSONStreamAware processRequest(HttpServletRequest paramHttpServletRequest)
/* 25:   */     throws NxtException
/* 26:   */   {
/* 27:25 */     Account localAccount = ParameterParser.getAccount(paramHttpServletRequest);
/* 28:26 */     int i = ParameterParser.getTimestamp(paramHttpServletRequest);
/* 29:27 */     int j = ParameterParser.getNumberOfConfirmations(paramHttpServletRequest);
/* 30:   */     byte b1;
/* 31:   */     try
/* 32:   */     {
/* 33:32 */       b1 = Byte.parseByte(paramHttpServletRequest.getParameter("type"));
/* 34:   */     }
/* 35:   */     catch (NumberFormatException localNumberFormatException1)
/* 36:   */     {
/* 37:34 */       b1 = -1;
/* 38:   */     }
/* 39:   */     byte b2;
/* 40:   */     try
/* 41:   */     {
/* 42:37 */       b2 = Byte.parseByte(paramHttpServletRequest.getParameter("subtype"));
/* 43:   */     }
/* 44:   */     catch (NumberFormatException localNumberFormatException2)
/* 45:   */     {
/* 46:39 */       b2 = -1;
/* 47:   */     }
/* 48:42 */     int k = ParameterParser.getFirstIndex(paramHttpServletRequest);
/* 49:43 */     int m = ParameterParser.getLastIndex(paramHttpServletRequest);
/* 50:   */     
/* 51:45 */     JSONArray localJSONArray = new JSONArray();
/* 52:46 */     Object localObject1 = Nxt.getBlockchain().getTransactions(localAccount, j, b1, b2, i, k, m);Object localObject2 = null;
/* 53:   */     try
/* 54:   */     {
/* 55:48 */       while (((DbIterator)localObject1).hasNext())
/* 56:   */       {
/* 57:49 */         Transaction localTransaction = (Transaction)((DbIterator)localObject1).next();
/* 58:50 */         localJSONArray.add(JSONData.transaction(localTransaction));
/* 59:   */       }
/* 60:   */     }
/* 61:   */     catch (Throwable localThrowable2)
/* 62:   */     {
/* 63:46 */       localObject2 = localThrowable2;throw localThrowable2;
/* 64:   */     }
/* 65:   */     finally
/* 66:   */     {
/* 67:52 */       if (localObject1 != null) {
/* 68:52 */         if (localObject2 != null) {
/* 69:   */           try
/* 70:   */           {
/* 71:52 */             ((DbIterator)localObject1).close();
/* 72:   */           }
/* 73:   */           catch (Throwable localThrowable3)
/* 74:   */           {
/* 75:52 */             ((Throwable)localObject2).addSuppressed(localThrowable3);
/* 76:   */           }
/* 77:   */         } else {
/* 78:52 */           ((DbIterator)localObject1).close();
/* 79:   */         }
/* 80:   */       }
/* 81:   */     }
/* 82:54 */     localObject1 = new JSONObject();
/* 83:55 */     ((JSONObject)localObject1).put("transactions", localJSONArray);
/* 84:56 */     return (JSONStreamAware)localObject1;
/* 85:   */   }
/* 86:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.http.GetAccountTransactions
 * JD-Core Version:    0.7.1
 */