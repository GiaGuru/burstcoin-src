/*  1:   */ package nxt.http;
/*  2:   */ 
/*  3:   */ import javax.servlet.http.HttpServletRequest;
/*  4:   */ import nxt.DigitalGoodsStore;
/*  5:   */ import nxt.DigitalGoodsStore.Purchase;
/*  6:   */ import nxt.NxtException;
/*  7:   */ import nxt.db.DbIterator;
/*  8:   */ import org.json.simple.JSONArray;
/*  9:   */ import org.json.simple.JSONObject;
/* 10:   */ import org.json.simple.JSONStreamAware;
/* 11:   */ 
/* 12:   */ public final class GetDGSPendingPurchases
/* 13:   */   extends APIServlet.APIRequestHandler
/* 14:   */ {
/* 15:16 */   static final GetDGSPendingPurchases instance = new GetDGSPendingPurchases();
/* 16:   */   
/* 17:   */   private GetDGSPendingPurchases()
/* 18:   */   {
/* 19:19 */     super(new APITag[] { APITag.DGS }, new String[] { "seller", "firstIndex", "lastIndex" });
/* 20:   */   }
/* 21:   */   
/* 22:   */   JSONStreamAware processRequest(HttpServletRequest paramHttpServletRequest)
/* 23:   */     throws NxtException
/* 24:   */   {
/* 25:25 */     long l = ParameterParser.getSellerId(paramHttpServletRequest);
/* 26:26 */     if (l == 0L) {
/* 27:27 */       return JSONResponses.MISSING_SELLER;
/* 28:   */     }
/* 29:29 */     int i = ParameterParser.getFirstIndex(paramHttpServletRequest);
/* 30:30 */     int j = ParameterParser.getLastIndex(paramHttpServletRequest);
/* 31:   */     
/* 32:32 */     JSONObject localJSONObject = new JSONObject();
/* 33:33 */     JSONArray localJSONArray = new JSONArray();
/* 34:   */     
/* 35:35 */     DbIterator localDbIterator = DigitalGoodsStore.getPendingSellerPurchases(l, i, j);Object localObject1 = null;
/* 36:   */     try
/* 37:   */     {
/* 38:36 */       while (localDbIterator.hasNext()) {
/* 39:37 */         localJSONArray.add(JSONData.purchase((DigitalGoodsStore.Purchase)localDbIterator.next()));
/* 40:   */       }
/* 41:   */     }
/* 42:   */     catch (Throwable localThrowable2)
/* 43:   */     {
/* 44:35 */       localObject1 = localThrowable2;throw localThrowable2;
/* 45:   */     }
/* 46:   */     finally
/* 47:   */     {
/* 48:39 */       if (localDbIterator != null) {
/* 49:39 */         if (localObject1 != null) {
/* 50:   */           try
/* 51:   */           {
/* 52:39 */             localDbIterator.close();
/* 53:   */           }
/* 54:   */           catch (Throwable localThrowable3)
/* 55:   */           {
/* 56:39 */             ((Throwable)localObject1).addSuppressed(localThrowable3);
/* 57:   */           }
/* 58:   */         } else {
/* 59:39 */           localDbIterator.close();
/* 60:   */         }
/* 61:   */       }
/* 62:   */     }
/* 63:41 */     localJSONObject.put("purchases", localJSONArray);
/* 64:42 */     return localJSONObject;
/* 65:   */   }
/* 66:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.http.GetDGSPendingPurchases
 * JD-Core Version:    0.7.1
 */