/*  1:   */ package nxt.http;
/*  2:   */ 
/*  3:   */ import javax.servlet.http.HttpServletRequest;
/*  4:   */ import nxt.Account;
/*  5:   */ import nxt.Attachment;
/*  6:   */ import nxt.Attachment.AdvancedPaymentEscrowSign;
/*  7:   */ import nxt.Escrow;
/*  8:   */ import nxt.Escrow.DecisionType;
/*  9:   */ import nxt.NxtException;
/* 10:   */ import nxt.util.Convert;
/* 11:   */ import org.json.simple.JSONObject;
/* 12:   */ import org.json.simple.JSONStreamAware;
/* 13:   */ 
/* 14:   */ public final class EscrowSign
/* 15:   */   extends CreateTransaction
/* 16:   */ {
/* 17:17 */   static final EscrowSign instance = new EscrowSign();
/* 18:   */   
/* 19:   */   private EscrowSign()
/* 20:   */   {
/* 21:20 */     super(new APITag[] { APITag.TRANSACTIONS, APITag.CREATE_TRANSACTION }, new String[] { "escrow", "decision" });
/* 22:   */   }
/* 23:   */   
/* 24:   */   JSONStreamAware processRequest(HttpServletRequest paramHttpServletRequest)
/* 25:   */     throws NxtException
/* 26:   */   {
/* 27:   */     Long localLong;
/* 28:   */     try
/* 29:   */     {
/* 30:29 */       localLong = Long.valueOf(Convert.parseUnsignedLong(Convert.emptyToNull(paramHttpServletRequest.getParameter("escrow"))));
/* 31:   */     }
/* 32:   */     catch (Exception localException)
/* 33:   */     {
/* 34:32 */       localObject1 = new JSONObject();
/* 35:33 */       ((JSONObject)localObject1).put("errorCode", Integer.valueOf(3));
/* 36:34 */       ((JSONObject)localObject1).put("errorDescription", "Invalid or not specified escrow");
/* 37:35 */       return (JSONStreamAware)localObject1;
/* 38:   */     }
/* 39:38 */     Escrow localEscrow = Escrow.getEscrowTransaction(localLong);
/* 40:39 */     if (localEscrow == null)
/* 41:   */     {
/* 42:40 */       localObject1 = new JSONObject();
/* 43:41 */       ((JSONObject)localObject1).put("errorCode", Integer.valueOf(5));
/* 44:42 */       ((JSONObject)localObject1).put("errorDescription", "Escrow transaction not found");
/* 45:43 */       return (JSONStreamAware)localObject1;
/* 46:   */     }
/* 47:46 */     Object localObject1 = Escrow.stringToDecision(paramHttpServletRequest.getParameter("decision"));
/* 48:47 */     if (localObject1 == null)
/* 49:   */     {
/* 50:48 */       localObject2 = new JSONObject();
/* 51:49 */       ((JSONObject)localObject2).put("errorCode", Integer.valueOf(5));
/* 52:50 */       ((JSONObject)localObject2).put("errorDescription", "Invalid or not specified action");
/* 53:51 */       return (JSONStreamAware)localObject2;
/* 54:   */     }
/* 55:54 */     Object localObject2 = ParameterParser.getSenderAccount(paramHttpServletRequest);
/* 56:55 */     if ((!localEscrow.getSenderId().equals(Long.valueOf(((Account)localObject2).getId()))) && (!localEscrow.getRecipientId().equals(Long.valueOf(((Account)localObject2).getId()))) && (!localEscrow.isIdSigner(Long.valueOf(((Account)localObject2).getId()))))
/* 57:   */     {
/* 58:58 */       localObject3 = new JSONObject();
/* 59:59 */       ((JSONObject)localObject3).put("errorCode", Integer.valueOf(5));
/* 60:60 */       ((JSONObject)localObject3).put("errorDescription", "Invalid or not specified action");
/* 61:61 */       return (JSONStreamAware)localObject3;
/* 62:   */     }
/* 63:64 */     if ((localEscrow.getSenderId().equals(Long.valueOf(((Account)localObject2).getId()))) && (localObject1 != Escrow.DecisionType.RELEASE))
/* 64:   */     {
/* 65:65 */       localObject3 = new JSONObject();
/* 66:66 */       ((JSONObject)localObject3).put("errorCode", Integer.valueOf(4));
/* 67:67 */       ((JSONObject)localObject3).put("errorDescription", "Sender can only release");
/* 68:68 */       return (JSONStreamAware)localObject3;
/* 69:   */     }
/* 70:71 */     if ((localEscrow.getRecipientId().equals(Long.valueOf(((Account)localObject2).getId()))) && (localObject1 != Escrow.DecisionType.REFUND))
/* 71:   */     {
/* 72:72 */       localObject3 = new JSONObject();
/* 73:73 */       ((JSONObject)localObject3).put("errorCode", Integer.valueOf(4));
/* 74:74 */       ((JSONObject)localObject3).put("errorDescription", "Recipient can only refund");
/* 75:75 */       return (JSONStreamAware)localObject3;
/* 76:   */     }
/* 77:78 */     Object localObject3 = new Attachment.AdvancedPaymentEscrowSign(localEscrow.getId(), (Escrow.DecisionType)localObject1);
/* 78:   */     
/* 79:80 */     return createTransaction(paramHttpServletRequest, (Account)localObject2, null, 0L, (Attachment)localObject3);
/* 80:   */   }
/* 81:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.http.EscrowSign
 * JD-Core Version:    0.7.1
 */