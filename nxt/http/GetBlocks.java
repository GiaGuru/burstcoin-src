/*  1:   */ package nxt.http;
/*  2:   */ 
/*  3:   */ import javax.servlet.http.HttpServletRequest;
/*  4:   */ import nxt.Block;
/*  5:   */ import nxt.Blockchain;
/*  6:   */ import nxt.Nxt;
/*  7:   */ import nxt.NxtException;
/*  8:   */ import nxt.db.DbIterator;
/*  9:   */ import org.json.simple.JSONArray;
/* 10:   */ import org.json.simple.JSONObject;
/* 11:   */ import org.json.simple.JSONStreamAware;
/* 12:   */ 
/* 13:   */ public final class GetBlocks
/* 14:   */   extends APIServlet.APIRequestHandler
/* 15:   */ {
/* 16:15 */   static final GetBlocks instance = new GetBlocks();
/* 17:   */   
/* 18:   */   private GetBlocks()
/* 19:   */   {
/* 20:18 */     super(new APITag[] { APITag.BLOCKS }, new String[] { "firstIndex", "lastIndex", "includeTransactions" });
/* 21:   */   }
/* 22:   */   
/* 23:   */   JSONStreamAware processRequest(HttpServletRequest paramHttpServletRequest)
/* 24:   */     throws NxtException
/* 25:   */   {
/* 26:24 */     int i = ParameterParser.getFirstIndex(paramHttpServletRequest);
/* 27:25 */     int j = ParameterParser.getLastIndex(paramHttpServletRequest);
/* 28:26 */     if ((j < 0) || (j - i > 99)) {
/* 29:27 */       j = i + 99;
/* 30:   */     }
/* 31:30 */     boolean bool = "true".equalsIgnoreCase(paramHttpServletRequest.getParameter("includeTransactions"));
/* 32:   */     
/* 33:32 */     JSONArray localJSONArray = new JSONArray();
/* 34:33 */     Object localObject1 = Nxt.getBlockchain().getBlocks(i, j);Object localObject2 = null;
/* 35:   */     try
/* 36:   */     {
/* 37:34 */       while (((DbIterator)localObject1).hasNext())
/* 38:   */       {
/* 39:35 */         Block localBlock = (Block)((DbIterator)localObject1).next();
/* 40:36 */         localJSONArray.add(JSONData.block(localBlock, bool));
/* 41:   */       }
/* 42:   */     }
/* 43:   */     catch (Throwable localThrowable2)
/* 44:   */     {
/* 45:33 */       localObject2 = localThrowable2;throw localThrowable2;
/* 46:   */     }
/* 47:   */     finally
/* 48:   */     {
/* 49:38 */       if (localObject1 != null) {
/* 50:38 */         if (localObject2 != null) {
/* 51:   */           try
/* 52:   */           {
/* 53:38 */             ((DbIterator)localObject1).close();
/* 54:   */           }
/* 55:   */           catch (Throwable localThrowable3)
/* 56:   */           {
/* 57:38 */             ((Throwable)localObject2).addSuppressed(localThrowable3);
/* 58:   */           }
/* 59:   */         } else {
/* 60:38 */           ((DbIterator)localObject1).close();
/* 61:   */         }
/* 62:   */       }
/* 63:   */     }
/* 64:40 */     localObject1 = new JSONObject();
/* 65:41 */     ((JSONObject)localObject1).put("blocks", localJSONArray);
/* 66:   */     
/* 67:43 */     return (JSONStreamAware)localObject1;
/* 68:   */   }
/* 69:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.http.GetBlocks
 * JD-Core Version:    0.7.1
 */