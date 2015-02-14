/*  1:   */ package nxt.http;
/*  2:   */ 
/*  3:   */ import javax.servlet.http.HttpServletRequest;
/*  4:   */ import nxt.DigitalGoodsStore;
/*  5:   */ import nxt.DigitalGoodsStore.Goods;
/*  6:   */ import nxt.NxtException;
/*  7:   */ import nxt.db.DbIterator;
/*  8:   */ import nxt.db.DbUtils;
/*  9:   */ import org.json.simple.JSONArray;
/* 10:   */ import org.json.simple.JSONObject;
/* 11:   */ import org.json.simple.JSONStreamAware;
/* 12:   */ 
/* 13:   */ public final class GetDGSGoods
/* 14:   */   extends APIServlet.APIRequestHandler
/* 15:   */ {
/* 16:15 */   static final GetDGSGoods instance = new GetDGSGoods();
/* 17:   */   
/* 18:   */   private GetDGSGoods()
/* 19:   */   {
/* 20:18 */     super(new APITag[] { APITag.DGS }, new String[] { "seller", "firstIndex", "lastIndex", "inStockOnly" });
/* 21:   */   }
/* 22:   */   
/* 23:   */   JSONStreamAware processRequest(HttpServletRequest paramHttpServletRequest)
/* 24:   */     throws NxtException
/* 25:   */   {
/* 26:23 */     long l = ParameterParser.getSellerId(paramHttpServletRequest);
/* 27:24 */     int i = ParameterParser.getFirstIndex(paramHttpServletRequest);
/* 28:25 */     int j = ParameterParser.getLastIndex(paramHttpServletRequest);
/* 29:26 */     boolean bool = !"false".equalsIgnoreCase(paramHttpServletRequest.getParameter("inStockOnly"));
/* 30:   */     
/* 31:28 */     JSONObject localJSONObject = new JSONObject();
/* 32:29 */     JSONArray localJSONArray = new JSONArray();
/* 33:30 */     localJSONObject.put("goods", localJSONArray);
/* 34:   */     
/* 35:32 */     DbIterator localDbIterator = null;
/* 36:   */     try
/* 37:   */     {
/* 38:34 */       if (l == 0L)
/* 39:   */       {
/* 40:35 */         if (bool) {
/* 41:36 */           localDbIterator = DigitalGoodsStore.getGoodsInStock(i, j);
/* 42:   */         } else {
/* 43:38 */           localDbIterator = DigitalGoodsStore.getAllGoods(i, j);
/* 44:   */         }
/* 45:   */       }
/* 46:   */       else {
/* 47:41 */         localDbIterator = DigitalGoodsStore.getSellerGoods(l, bool, i, j);
/* 48:   */       }
/* 49:43 */       while (localDbIterator.hasNext())
/* 50:   */       {
/* 51:44 */         DigitalGoodsStore.Goods localGoods = (DigitalGoodsStore.Goods)localDbIterator.next();
/* 52:45 */         localJSONArray.add(JSONData.goods(localGoods));
/* 53:   */       }
/* 54:   */     }
/* 55:   */     finally
/* 56:   */     {
/* 57:48 */       DbUtils.close(new AutoCloseable[] { localDbIterator });
/* 58:   */     }
/* 59:51 */     return localJSONObject;
/* 60:   */   }
/* 61:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.http.GetDGSGoods
 * JD-Core Version:    0.7.1
 */