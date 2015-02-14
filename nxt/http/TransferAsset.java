/*  1:   */ package nxt.http;
/*  2:   */ 
/*  3:   */ import javax.servlet.http.HttpServletRequest;
/*  4:   */ import nxt.Account;
/*  5:   */ import nxt.Asset;
/*  6:   */ import nxt.Attachment.ColoredCoinsAssetTransfer;
/*  7:   */ import nxt.NxtException;
/*  8:   */ import org.json.simple.JSONStreamAware;
/*  9:   */ 
/* 10:   */ public final class TransferAsset
/* 11:   */   extends CreateTransaction
/* 12:   */ {
/* 13:15 */   static final TransferAsset instance = new TransferAsset();
/* 14:   */   
/* 15:   */   private TransferAsset()
/* 16:   */   {
/* 17:18 */     super(new APITag[] { APITag.AE, APITag.CREATE_TRANSACTION }, new String[] { "recipient", "asset", "quantityQNT" });
/* 18:   */   }
/* 19:   */   
/* 20:   */   JSONStreamAware processRequest(HttpServletRequest paramHttpServletRequest)
/* 21:   */     throws NxtException
/* 22:   */   {
/* 23:24 */     long l1 = ParameterParser.getRecipientId(paramHttpServletRequest);
/* 24:   */     
/* 25:26 */     Asset localAsset = ParameterParser.getAsset(paramHttpServletRequest);
/* 26:27 */     long l2 = ParameterParser.getQuantityQNT(paramHttpServletRequest);
/* 27:28 */     Account localAccount = ParameterParser.getSenderAccount(paramHttpServletRequest);
/* 28:   */     
/* 29:30 */     long l3 = localAccount.getUnconfirmedAssetBalanceQNT(localAsset.getId());
/* 30:31 */     if ((l3 < 0L) || (l2 > l3)) {
/* 31:32 */       return JSONResponses.NOT_ENOUGH_ASSETS;
/* 32:   */     }
/* 33:35 */     Attachment.ColoredCoinsAssetTransfer localColoredCoinsAssetTransfer = new Attachment.ColoredCoinsAssetTransfer(localAsset.getId(), l2);
/* 34:36 */     return createTransaction(paramHttpServletRequest, localAccount, Long.valueOf(l1), 0L, localColoredCoinsAssetTransfer);
/* 35:   */   }
/* 36:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.http.TransferAsset
 * JD-Core Version:    0.7.1
 */