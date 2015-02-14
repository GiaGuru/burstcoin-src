/*  1:   */ package nxt.http;
/*  2:   */ 
/*  3:   */ import javax.servlet.http.HttpServletRequest;
/*  4:   */ import nxt.Account;
/*  5:   */ import nxt.Attachment;
/*  6:   */ import nxt.Attachment.DigitalGoodsDelivery;
/*  7:   */ import nxt.DigitalGoodsStore.Purchase;
/*  8:   */ import nxt.NxtException;
/*  9:   */ import nxt.crypto.EncryptedData;
/* 10:   */ import nxt.util.Convert;
/* 11:   */ import org.json.simple.JSONStreamAware;
/* 12:   */ 
/* 13:   */ public final class DGSDelivery
/* 14:   */   extends CreateTransaction
/* 15:   */ {
/* 16:21 */   static final DGSDelivery instance = new DGSDelivery();
/* 17:   */   
/* 18:   */   private DGSDelivery()
/* 19:   */   {
/* 20:24 */     super(new APITag[] { APITag.DGS, APITag.CREATE_TRANSACTION }, new String[] { "purchase", "discountNQT", "goodsToEncrypt", "goodsIsText", "goodsData", "goodsNonce" });
/* 21:   */   }
/* 22:   */   
/* 23:   */   JSONStreamAware processRequest(HttpServletRequest paramHttpServletRequest)
/* 24:   */     throws NxtException
/* 25:   */   {
/* 26:31 */     Account localAccount1 = ParameterParser.getSenderAccount(paramHttpServletRequest);
/* 27:32 */     DigitalGoodsStore.Purchase localPurchase = ParameterParser.getPurchase(paramHttpServletRequest);
/* 28:33 */     if (localAccount1.getId() != localPurchase.getSellerId()) {
/* 29:34 */       return JSONResponses.INCORRECT_PURCHASE;
/* 30:   */     }
/* 31:36 */     if (!localPurchase.isPending()) {
/* 32:37 */       return JSONResponses.ALREADY_DELIVERED;
/* 33:   */     }
/* 34:40 */     String str1 = Convert.emptyToNull(paramHttpServletRequest.getParameter("discountNQT"));
/* 35:41 */     long l = 0L;
/* 36:   */     try
/* 37:   */     {
/* 38:43 */       if (str1 != null) {
/* 39:44 */         l = Long.parseLong(str1);
/* 40:   */       }
/* 41:   */     }
/* 42:   */     catch (RuntimeException localRuntimeException1)
/* 43:   */     {
/* 44:47 */       return JSONResponses.INCORRECT_DGS_DISCOUNT;
/* 45:   */     }
/* 46:49 */     if ((l < 0L) || (l > 215881280000000000L) || (l > Convert.safeMultiply(localPurchase.getPriceNQT(), localPurchase.getQuantity()))) {
/* 47:52 */       return JSONResponses.INCORRECT_DGS_DISCOUNT;
/* 48:   */     }
/* 49:55 */     Account localAccount2 = Account.getAccount(localPurchase.getBuyerId());
/* 50:56 */     boolean bool = !"false".equalsIgnoreCase(paramHttpServletRequest.getParameter("goodsIsText"));
/* 51:57 */     EncryptedData localEncryptedData = ParameterParser.getEncryptedGoods(paramHttpServletRequest);
/* 52:59 */     if (localEncryptedData == null)
/* 53:   */     {
/* 54:60 */       localObject = ParameterParser.getSecretPhrase(paramHttpServletRequest);
/* 55:   */       byte[] arrayOfByte;
/* 56:   */       try
/* 57:   */       {
/* 58:63 */         String str2 = Convert.nullToEmpty(paramHttpServletRequest.getParameter("goodsToEncrypt"));
/* 59:64 */         if (str2.length() == 0) {
/* 60:65 */           return JSONResponses.INCORRECT_DGS_GOODS;
/* 61:   */         }
/* 62:67 */         arrayOfByte = bool ? Convert.toBytes(str2) : Convert.parseHexString(str2);
/* 63:   */       }
/* 64:   */       catch (RuntimeException localRuntimeException2)
/* 65:   */       {
/* 66:69 */         return JSONResponses.INCORRECT_DGS_GOODS;
/* 67:   */       }
/* 68:71 */       localEncryptedData = localAccount2.encryptTo(arrayOfByte, (String)localObject);
/* 69:   */     }
/* 70:74 */     Object localObject = new Attachment.DigitalGoodsDelivery(localPurchase.getId(), localEncryptedData, bool, l);
/* 71:75 */     return createTransaction(paramHttpServletRequest, localAccount1, Long.valueOf(localAccount2.getId()), 0L, (Attachment)localObject);
/* 72:   */   }
/* 73:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.http.DGSDelivery
 * JD-Core Version:    0.7.1
 */