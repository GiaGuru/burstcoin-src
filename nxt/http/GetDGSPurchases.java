/*  1:   */ package nxt.http;
/*  2:   */ 
/*  3:   */ import javax.servlet.http.HttpServletRequest;
/*  4:   */ import nxt.DigitalGoodsStore;
/*  5:   */ import nxt.DigitalGoodsStore.Purchase;
/*  6:   */ import nxt.NxtException;
/*  7:   */ import nxt.db.DbIterator;
/*  8:   */ import nxt.db.FilteringIterator;
/*  9:   */ import nxt.db.FilteringIterator.Filter;
/* 10:   */ import org.json.simple.JSONArray;
/* 11:   */ import org.json.simple.JSONObject;
/* 12:   */ import org.json.simple.JSONStreamAware;
/* 13:   */ 
/* 14:   */ public final class GetDGSPurchases
/* 15:   */   extends APIServlet.APIRequestHandler
/* 16:   */ {
/* 17:15 */   static final GetDGSPurchases instance = new GetDGSPurchases();
/* 18:   */   
/* 19:   */   private GetDGSPurchases()
/* 20:   */   {
/* 21:18 */     super(new APITag[] { APITag.DGS }, new String[] { "seller", "buyer", "firstIndex", "lastIndex", "completed" });
/* 22:   */   }
/* 23:   */   
/* 24:   */   JSONStreamAware processRequest(HttpServletRequest paramHttpServletRequest)
/* 25:   */     throws NxtException
/* 26:   */   {
/* 27:24 */     long l1 = ParameterParser.getSellerId(paramHttpServletRequest);
/* 28:25 */     long l2 = ParameterParser.getBuyerId(paramHttpServletRequest);
/* 29:26 */     int i = ParameterParser.getFirstIndex(paramHttpServletRequest);
/* 30:27 */     int j = ParameterParser.getLastIndex(paramHttpServletRequest);
/* 31:28 */     final boolean bool = "true".equalsIgnoreCase(paramHttpServletRequest.getParameter("completed"));
/* 32:   */     
/* 33:   */ 
/* 34:31 */     JSONObject localJSONObject = new JSONObject();
/* 35:32 */     JSONArray localJSONArray = new JSONArray();
/* 36:33 */     localJSONObject.put("purchases", localJSONArray);
/* 37:   */     Object localObject1;
/* 38:35 */     if ((l1 == 0L) && (l2 == 0L))
/* 39:   */     {
/* 40:36 */       localObject1 = new FilteringIterator(DigitalGoodsStore.getAllPurchases(0, -1), new FilteringIterator.Filter()
/* 41:   */       {
/* 42:   */         public boolean ok(DigitalGoodsStore.Purchase paramAnonymousPurchase)
/* 43:   */         {
/* 44:40 */           return (!bool) || (!paramAnonymousPurchase.isPending());
/* 45:   */         }
/* 46:40 */       }, i, j);localObject2 = null;
/* 47:   */       try
/* 48:   */       {
/* 49:43 */         while (((FilteringIterator)localObject1).hasNext()) {
/* 50:44 */           localJSONArray.add(JSONData.purchase((DigitalGoodsStore.Purchase)((FilteringIterator)localObject1).next()));
/* 51:   */         }
/* 52:   */       }
/* 53:   */       catch (Throwable localThrowable2)
/* 54:   */       {
/* 55:36 */         localObject2 = localThrowable2;throw localThrowable2;
/* 56:   */       }
/* 57:   */       finally
/* 58:   */       {
/* 59:46 */         if (localObject1 != null) {
/* 60:46 */           if (localObject2 != null) {
/* 61:   */             try
/* 62:   */             {
/* 63:46 */               ((FilteringIterator)localObject1).close();
/* 64:   */             }
/* 65:   */             catch (Throwable localThrowable5)
/* 66:   */             {
/* 67:46 */               ((Throwable)localObject2).addSuppressed(localThrowable5);
/* 68:   */             }
/* 69:   */           } else {
/* 70:46 */             ((FilteringIterator)localObject1).close();
/* 71:   */           }
/* 72:   */         }
/* 73:   */       }
/* 74:47 */       return localJSONObject;
/* 75:   */     }
/* 76:51 */     if ((l1 != 0L) && (l2 == 0L)) {
/* 77:52 */       localObject1 = DigitalGoodsStore.getSellerPurchases(l1, 0, -1);
/* 78:53 */     } else if (l1 == 0L) {
/* 79:54 */       localObject1 = DigitalGoodsStore.getBuyerPurchases(l2, 0, -1);
/* 80:   */     } else {
/* 81:56 */       localObject1 = DigitalGoodsStore.getSellerBuyerPurchases(l1, l2, 0, -1);
/* 82:   */     }
/* 83:58 */     Object localObject2 = new FilteringIterator((DbIterator)localObject1, new FilteringIterator.Filter()
/* 84:   */     {
/* 85:   */       public boolean ok(DigitalGoodsStore.Purchase paramAnonymousPurchase)
/* 86:   */       {
/* 87:62 */         return (!bool) || (!paramAnonymousPurchase.isPending());
/* 88:   */       }
/* 89:62 */     }, i, j);Object localObject3 = null;
/* 90:   */     try
/* 91:   */     {
/* 92:65 */       while (((FilteringIterator)localObject2).hasNext()) {
/* 93:66 */         localJSONArray.add(JSONData.purchase((DigitalGoodsStore.Purchase)((FilteringIterator)localObject2).next()));
/* 94:   */       }
/* 95:   */     }
/* 96:   */     catch (Throwable localThrowable4)
/* 97:   */     {
/* 98:58 */       localObject3 = localThrowable4;throw localThrowable4;
/* 99:   */     }
/* :0:   */     finally
/* :1:   */     {
/* :2:68 */       if (localObject2 != null) {
/* :3:68 */         if (localObject3 != null) {
/* :4:   */           try
/* :5:   */           {
/* :6:68 */             ((FilteringIterator)localObject2).close();
/* :7:   */           }
/* :8:   */           catch (Throwable localThrowable6)
/* :9:   */           {
/* ;0:68 */             ((Throwable)localObject3).addSuppressed(localThrowable6);
/* ;1:   */           }
/* ;2:   */         } else {
/* ;3:68 */           ((FilteringIterator)localObject2).close();
/* ;4:   */         }
/* ;5:   */       }
/* ;6:   */     }
/* ;7:69 */     return localJSONObject;
/* ;8:   */   }
/* ;9:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.http.GetDGSPurchases
 * JD-Core Version:    0.7.1
 */