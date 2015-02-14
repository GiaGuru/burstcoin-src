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
/* 14:   */ public final class GetAccountBlockIds
/* 15:   */   extends APIServlet.APIRequestHandler
/* 16:   */ {
/* 17:16 */   static final GetAccountBlockIds instance = new GetAccountBlockIds();
/* 18:   */   
/* 19:   */   private GetAccountBlockIds()
/* 20:   */   {
/* 21:19 */     super(new APITag[] { APITag.ACCOUNTS }, new String[] { "account", "timestamp", "firstIndex", "lastIndex" });
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
/* 32:30 */     JSONArray localJSONArray = new JSONArray();
/* 33:31 */     Object localObject1 = Nxt.getBlockchain().getBlocks(localAccount, i, j, k);Object localObject2 = null;
/* 34:   */     try
/* 35:   */     {
/* 36:32 */       while (((DbIterator)localObject1).hasNext())
/* 37:   */       {
/* 38:33 */         Block localBlock = (Block)((DbIterator)localObject1).next();
/* 39:34 */         localJSONArray.add(localBlock.getStringId());
/* 40:   */       }
/* 41:   */     }
/* 42:   */     catch (Throwable localThrowable2)
/* 43:   */     {
/* 44:31 */       localObject2 = localThrowable2;throw localThrowable2;
/* 45:   */     }
/* 46:   */     finally
/* 47:   */     {
/* 48:36 */       if (localObject1 != null) {
/* 49:36 */         if (localObject2 != null) {
/* 50:   */           try
/* 51:   */           {
/* 52:36 */             ((DbIterator)localObject1).close();
/* 53:   */           }
/* 54:   */           catch (Throwable localThrowable3)
/* 55:   */           {
/* 56:36 */             ((Throwable)localObject2).addSuppressed(localThrowable3);
/* 57:   */           }
/* 58:   */         } else {
/* 59:36 */           ((DbIterator)localObject1).close();
/* 60:   */         }
/* 61:   */       }
/* 62:   */     }
/* 63:38 */     localObject1 = new JSONObject();
/* 64:39 */     ((JSONObject)localObject1).put("blockIds", localJSONArray);
/* 65:   */     
/* 66:41 */     return (JSONStreamAware)localObject1;
/* 67:   */   }
/* 68:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.http.GetAccountBlockIds
 * JD-Core Version:    0.7.1
 */