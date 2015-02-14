/*   1:    */ package nxt.http;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.Collection;
/*   5:    */ import javax.servlet.http.HttpServletRequest;
/*   6:    */ import nxt.Account;
/*   7:    */ import nxt.Attachment;
/*   8:    */ import nxt.Attachment.AdvancedPaymentEscrowCreation;
/*   9:    */ import nxt.Escrow;
/*  10:    */ import nxt.Escrow.DecisionType;
/*  11:    */ import nxt.NxtException;
/*  12:    */ import nxt.util.Convert;
/*  13:    */ import org.json.simple.JSONObject;
/*  14:    */ import org.json.simple.JSONStreamAware;
/*  15:    */ 
/*  16:    */ public final class SendMoneyEscrow
/*  17:    */   extends CreateTransaction
/*  18:    */ {
/*  19: 19 */   static final SendMoneyEscrow instance = new SendMoneyEscrow();
/*  20:    */   
/*  21:    */   private SendMoneyEscrow()
/*  22:    */   {
/*  23: 22 */     super(new APITag[] { APITag.TRANSACTIONS, APITag.CREATE_TRANSACTION }, new String[] { "recipient", "amountNQT", "escrowDeadline", "deadlineAction", "requiredSigners", "signers" });
/*  24:    */   }
/*  25:    */   
/*  26:    */   JSONStreamAware processRequest(HttpServletRequest paramHttpServletRequest)
/*  27:    */     throws NxtException
/*  28:    */   {
/*  29: 33 */     Account localAccount = ParameterParser.getSenderAccount(paramHttpServletRequest);
/*  30: 34 */     Long localLong1 = Long.valueOf(ParameterParser.getRecipientId(paramHttpServletRequest));
/*  31: 35 */     Long localLong2 = Long.valueOf(ParameterParser.getAmountNQT(paramHttpServletRequest));
/*  32: 36 */     String str = Convert.emptyToNull(paramHttpServletRequest.getParameter("signers"));
/*  33:    */     Long localLong3;
/*  34:    */     try
/*  35:    */     {
/*  36: 40 */       localLong3 = Long.valueOf(Convert.parseLong(paramHttpServletRequest.getParameter("requiredSigners")));
/*  37: 41 */       if ((localLong3.longValue() < 1L) || (localLong3.longValue() > 10L))
/*  38:    */       {
/*  39: 42 */         JSONObject localJSONObject1 = new JSONObject();
/*  40: 43 */         localJSONObject1.put("errorCode", Integer.valueOf(4));
/*  41: 44 */         localJSONObject1.put("errorDescription", "Invalid number of requiredSigners");
/*  42: 45 */         return localJSONObject1;
/*  43:    */       }
/*  44:    */     }
/*  45:    */     catch (Exception localException1)
/*  46:    */     {
/*  47: 49 */       localObject2 = new JSONObject();
/*  48: 50 */       ((JSONObject)localObject2).put("errorCode", Integer.valueOf(4));
/*  49: 51 */       ((JSONObject)localObject2).put("errorDescription", "Invalid requiredSigners parameter");
/*  50: 52 */       return (JSONStreamAware)localObject2;
/*  51:    */     }
/*  52: 55 */     if (str == null)
/*  53:    */     {
/*  54: 56 */       localObject1 = new JSONObject();
/*  55: 57 */       ((JSONObject)localObject1).put("errorCode", Integer.valueOf(3));
/*  56: 58 */       ((JSONObject)localObject1).put("errorDescription", "Signers not specified");
/*  57: 59 */       return (JSONStreamAware)localObject1;
/*  58:    */     }
/*  59: 62 */     Object localObject1 = str.split(";", 10);
/*  60: 64 */     if ((localObject1.length < 1) || (localObject1.length > 10) || (localObject1.length < localLong3.longValue()))
/*  61:    */     {
/*  62: 65 */       localObject2 = new JSONObject();
/*  63: 66 */       ((JSONObject)localObject2).put("errorCode", Integer.valueOf(4));
/*  64: 67 */       ((JSONObject)localObject2).put("errorDescription", "Invalid number of signers");
/*  65: 68 */       return (JSONStreamAware)localObject2;
/*  66:    */     }
/*  67: 71 */     Object localObject2 = new ArrayList();
/*  68:    */     Object localObject4;
/*  69:    */     try
/*  70:    */     {
/*  71: 74 */       for (localObject5 : localObject1)
/*  72:    */       {
/*  73: 75 */         Long localLong5 = Long.valueOf(Convert.parseAccountId((String)localObject5));
/*  74: 76 */         if (localLong5 == null) {
/*  75: 77 */           throw new Exception("");
/*  76:    */         }
/*  77: 80 */         ((ArrayList)localObject2).add(localLong5);
/*  78:    */       }
/*  79:    */     }
/*  80:    */     catch (Exception localException2)
/*  81:    */     {
/*  82: 84 */       localObject4 = new JSONObject();
/*  83: 85 */       ((JSONObject)localObject4).put("errorCode", Integer.valueOf(4));
/*  84: 86 */       ((JSONObject)localObject4).put("errorDescription", "Invalid signers parameter");
/*  85: 87 */       return (JSONStreamAware)localObject4;
/*  86:    */     }
/*  87: 90 */     Long localLong4 = Long.valueOf(Convert.safeAdd(localLong2.longValue(), ((ArrayList)localObject2).size() * 100000000L));
/*  88: 91 */     if (localAccount.getBalanceNQT() < localLong4.longValue())
/*  89:    */     {
/*  90: 92 */       localObject4 = new JSONObject();
/*  91: 93 */       ((JSONObject)localObject4).put("errorCode", Integer.valueOf(6));
/*  92: 94 */       ((JSONObject)localObject4).put("errorDescription", "Insufficient funds");
/*  93: 95 */       return (JSONStreamAware)localObject4;
/*  94:    */     }
/*  95:    */     try
/*  96:    */     {
/*  97:100 */       localObject4 = Long.valueOf(Convert.parseLong(paramHttpServletRequest.getParameter("escrowDeadline")));
/*  98:101 */       if ((((Long)localObject4).longValue() < 1L) || (((Long)localObject4).longValue() > 7776000L))
/*  99:    */       {
/* 100:102 */         JSONObject localJSONObject2 = new JSONObject();
/* 101:103 */         localJSONObject2.put("errorCode", Integer.valueOf(4));
/* 102:104 */         localJSONObject2.put("errorDescription", "Escrow deadline must be 1 - 7776000");
/* 103:105 */         return localJSONObject2;
/* 104:    */       }
/* 105:    */     }
/* 106:    */     catch (Exception localException3)
/* 107:    */     {
/* 108:109 */       localObject5 = new JSONObject();
/* 109:110 */       ((JSONObject)localObject5).put("errorCode", Integer.valueOf(4));
/* 110:111 */       ((JSONObject)localObject5).put("errorDescription", "Invalid escrowDeadline parameter");
/* 111:112 */       return (JSONStreamAware)localObject5;
/* 112:    */     }
/* 113:115 */     Escrow.DecisionType localDecisionType = Escrow.stringToDecision(paramHttpServletRequest.getParameter("deadlineAction"));
/* 114:116 */     if ((localDecisionType == null) || (localDecisionType == Escrow.DecisionType.UNDECIDED))
/* 115:    */     {
/* 116:117 */       localObject5 = new JSONObject();
/* 117:118 */       ((JSONObject)localObject5).put("errorCode", Integer.valueOf(4));
/* 118:119 */       ((JSONObject)localObject5).put("errorDescription", "Invalid deadlineAction parameter");
/* 119:120 */       return (JSONStreamAware)localObject5;
/* 120:    */     }
/* 121:123 */     Object localObject5 = new Attachment.AdvancedPaymentEscrowCreation(localLong2, ((Long)localObject4).intValue(), localDecisionType, localLong3.intValue(), (Collection)localObject2);
/* 122:    */     
/* 123:125 */     return createTransaction(paramHttpServletRequest, localAccount, localLong1, 0L, (Attachment)localObject5);
/* 124:    */   }
/* 125:    */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.http.SendMoneyEscrow
 * JD-Core Version:    0.7.1
 */