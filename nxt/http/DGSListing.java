/*  1:   */ package nxt.http;
/*  2:   */ 
/*  3:   */ import javax.servlet.http.HttpServletRequest;
/*  4:   */ import nxt.Account;
/*  5:   */ import nxt.Attachment.DigitalGoodsListing;
/*  6:   */ import nxt.NxtException;
/*  7:   */ import nxt.util.Convert;
/*  8:   */ import org.json.simple.JSONStreamAware;
/*  9:   */ 
/* 10:   */ public final class DGSListing
/* 11:   */   extends CreateTransaction
/* 12:   */ {
/* 13:19 */   static final DGSListing instance = new DGSListing();
/* 14:   */   
/* 15:   */   private DGSListing()
/* 16:   */   {
/* 17:22 */     super(new APITag[] { APITag.DGS, APITag.CREATE_TRANSACTION }, new String[] { "name", "description", "tags", "quantity", "priceNQT" });
/* 18:   */   }
/* 19:   */   
/* 20:   */   JSONStreamAware processRequest(HttpServletRequest paramHttpServletRequest)
/* 21:   */     throws NxtException
/* 22:   */   {
/* 23:29 */     String str1 = Convert.emptyToNull(paramHttpServletRequest.getParameter("name"));
/* 24:30 */     String str2 = Convert.nullToEmpty(paramHttpServletRequest.getParameter("description"));
/* 25:31 */     String str3 = Convert.nullToEmpty(paramHttpServletRequest.getParameter("tags"));
/* 26:32 */     long l = ParameterParser.getPriceNQT(paramHttpServletRequest);
/* 27:33 */     int i = ParameterParser.getGoodsQuantity(paramHttpServletRequest);
/* 28:35 */     if (str1 == null) {
/* 29:36 */       return JSONResponses.MISSING_NAME;
/* 30:   */     }
/* 31:38 */     str1 = str1.trim();
/* 32:39 */     if (str1.length() > 100) {
/* 33:40 */       return JSONResponses.INCORRECT_DGS_LISTING_NAME;
/* 34:   */     }
/* 35:43 */     if (str2.length() > 1000) {
/* 36:44 */       return JSONResponses.INCORRECT_DGS_LISTING_DESCRIPTION;
/* 37:   */     }
/* 38:47 */     if (str3.length() > 100) {
/* 39:48 */       return JSONResponses.INCORRECT_DGS_LISTING_TAGS;
/* 40:   */     }
/* 41:51 */     Account localAccount = ParameterParser.getSenderAccount(paramHttpServletRequest);
/* 42:52 */     Attachment.DigitalGoodsListing localDigitalGoodsListing = new Attachment.DigitalGoodsListing(str1, str2, str3, i, l);
/* 43:53 */     return createTransaction(paramHttpServletRequest, localAccount, localDigitalGoodsListing);
/* 44:   */   }
/* 45:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.http.DGSListing
 * JD-Core Version:    0.7.1
 */