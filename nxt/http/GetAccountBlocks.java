/*  1:   */ package nxt.http;
/*  2:   */ 
/*  3:   */ import javax.servlet.http.HttpServletRequest;
/*  4:   */ import nxt.Account;
/*  5:   */ import nxt.Block;
/*  6:   */ import nxt.Blockchain;
/*  7:   */ import nxt.Nxt;
/*  8:   */ import nxt.NxtException;
/*  9:   */ import nxt.db.DbIterator;
/* 10:   */ import org.json.simple.JSONArray;
/* 11:   */ import org.json.simple.JSONObject;
/* 12:   */ import org.json.simple.JSONStreamAware;
/* 13:   */ 
/* 14:   */ public final class GetAccountBlocks
/* 15:   */   extends APIServlet.APIRequestHandler
/* 16:   */ {
/* 17:16 */   static final GetAccountBlocks instance = new GetAccountBlocks();
/* 18:   */   
/* 19:   */   private GetAccountBlocks()
/* 20:   */   {
/* 21:19 */     super(new APITag[] { APITag.ACCOUNTS }, new String[] { "account", "timestamp", "firstIndex", "lastIndex", "includeTransactions" });
/* 22:   */   }
/* 23:   */   
/* 24:   */   JSONStreamAware processRequest(HttpServletRequest paramHttpServletRequest)
/* 25:   */     throws NxtException
/* 26:   */   {
/* 27:25 */     Account localAccount = ParameterParser.getAccount(paramHttpServletRequest);
/* 28:26 */     int i = ParameterParser.getTimestamp(paramHttpServletRequest);
/* 29:27 */     int j = ParameterParser.getFirstIndex(paramHttpServletRequest);
/* 30:28 */     int k = ParameterParser.getLastIndex(paramHttpServletRequest);
/* 31:   */     
/* 32:30 */     boolean bool = "true".equalsIgnoreCase(paramHttpServletRequest.getParameter("includeTransactions"));
/* 33:   */     
/* 34:32 */     JSONArray localJSONArray = new JSONArray();
/* 35:33 */     Object localObject1 = Nxt.getBlockchain().getBlocks(localAccount, i, j, k);Object localObject2 = null;
/* 36:   */     try
/* 37:   */     {
/* 38:34 */       while (((DbIterator)localObject1).hasNext())
/* 39:   */       {
/* 40:35 */         Block localBlock = (Block)((DbIterator)localObject1).next();
/* 41:36 */         localJSONArray.add(JSONData.block(localBlock, bool));
/* 42:   */       }
/* 43:   */     }
/* 44:   */     catch (Throwable localThrowable2)
/* 45:   */     {
/* 46:33 */       localObject2 = localThrowable2;throw localThrowable2;
/* 47:   */     }
/* 48:   */     finally
/* 49:   */     {
/* 50:38 */       if (localObject1 != null) {
/* 51:38 */         if (localObject2 != null) {
/* 52:   */           try
/* 53:   */           {
/* 54:38 */             ((DbIterator)localObject1).close();
/* 55:   */           }
/* 56:   */           catch (Throwable localThrowable3)
/* 57:   */           {
/* 58:38 */             ((Throwable)localObject2).addSuppressed(localThrowable3);
/* 59:   */           }
/* 60:   */         } else {
/* 61:38 */           ((DbIterator)localObject1).close();
/* 62:   */         }
/* 63:   */       }
/* 64:   */     }
/* 65:40 */     localObject1 = new JSONObject();
/* 66:41 */     ((JSONObject)localObject1).put("blocks", localJSONArray);
/* 67:   */     
/* 68:43 */     return (JSONStreamAware)localObject1;
/* 69:   */   }
/* 70:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.http.GetAccountBlocks
 * JD-Core Version:    0.7.1
 */