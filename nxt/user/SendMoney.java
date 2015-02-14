/*   1:    */ package nxt.user;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import javax.servlet.http.HttpServletRequest;
/*   5:    */ import nxt.Account;
/*   6:    */ import nxt.Attachment;
/*   7:    */ import nxt.Nxt;
/*   8:    */ import nxt.NxtException.ValidationException;
/*   9:    */ import nxt.Transaction;
/*  10:    */ import nxt.Transaction.Builder;
/*  11:    */ import nxt.TransactionProcessor;
/*  12:    */ import nxt.util.Convert;
/*  13:    */ import org.json.simple.JSONObject;
/*  14:    */ import org.json.simple.JSONStreamAware;
/*  15:    */ 
/*  16:    */ public final class SendMoney
/*  17:    */   extends UserServlet.UserRequestHandler
/*  18:    */ {
/*  19: 20 */   static final SendMoney instance = new SendMoney();
/*  20:    */   
/*  21:    */   JSONStreamAware processRequest(HttpServletRequest paramHttpServletRequest, User paramUser)
/*  22:    */     throws NxtException.ValidationException, IOException
/*  23:    */   {
/*  24: 26 */     if (paramUser.getSecretPhrase() == null) {
/*  25: 27 */       return null;
/*  26:    */     }
/*  27: 30 */     String str1 = paramHttpServletRequest.getParameter("recipient");
/*  28: 31 */     String str2 = paramHttpServletRequest.getParameter("amountNXT");
/*  29: 32 */     String str3 = paramHttpServletRequest.getParameter("feeNXT");
/*  30: 33 */     String str4 = paramHttpServletRequest.getParameter("deadline");
/*  31: 34 */     String str5 = paramHttpServletRequest.getParameter("secretPhrase");
/*  32:    */     
/*  33:    */ 
/*  34: 37 */     long l2 = 0L;
/*  35: 38 */     long l3 = 0L;
/*  36: 39 */     short s = 0;
/*  37:    */     long l1;
/*  38:    */     try
/*  39:    */     {
/*  40: 43 */       l1 = Convert.parseUnsignedLong(str1);
/*  41: 44 */       if (l1 == 0L) {
/*  42: 44 */         throw new IllegalArgumentException("invalid recipient");
/*  43:    */       }
/*  44: 45 */       l2 = Convert.parseNXT(str2.trim());
/*  45: 46 */       l3 = Convert.parseNXT(str3.trim());
/*  46: 47 */       s = (short)(int)(Double.parseDouble(str4) * 60.0D);
/*  47:    */     }
/*  48:    */     catch (RuntimeException localRuntimeException)
/*  49:    */     {
/*  50: 51 */       localObject2 = new JSONObject();
/*  51: 52 */       ((JSONObject)localObject2).put("response", "notifyOfIncorrectTransaction");
/*  52: 53 */       ((JSONObject)localObject2).put("message", "One of the fields is filled incorrectly!");
/*  53: 54 */       ((JSONObject)localObject2).put("recipient", str1);
/*  54: 55 */       ((JSONObject)localObject2).put("amountNXT", str2);
/*  55: 56 */       ((JSONObject)localObject2).put("feeNXT", str3);
/*  56: 57 */       ((JSONObject)localObject2).put("deadline", str4);
/*  57:    */       
/*  58: 59 */       return (JSONStreamAware)localObject2;
/*  59:    */     }
/*  60: 62 */     if (!paramUser.getSecretPhrase().equals(str5))
/*  61:    */     {
/*  62: 64 */       localObject1 = new JSONObject();
/*  63: 65 */       ((JSONObject)localObject1).put("response", "notifyOfIncorrectTransaction");
/*  64: 66 */       ((JSONObject)localObject1).put("message", "Wrong secret phrase!");
/*  65: 67 */       ((JSONObject)localObject1).put("recipient", str1);
/*  66: 68 */       ((JSONObject)localObject1).put("amountNXT", str2);
/*  67: 69 */       ((JSONObject)localObject1).put("feeNXT", str3);
/*  68: 70 */       ((JSONObject)localObject1).put("deadline", str4);
/*  69:    */       
/*  70: 72 */       return (JSONStreamAware)localObject1;
/*  71:    */     }
/*  72: 74 */     if ((l2 <= 0L) || (l2 > 215881280000000000L))
/*  73:    */     {
/*  74: 76 */       localObject1 = new JSONObject();
/*  75: 77 */       ((JSONObject)localObject1).put("response", "notifyOfIncorrectTransaction");
/*  76: 78 */       ((JSONObject)localObject1).put("message", "\"Amount\" must be greater than 0!");
/*  77: 79 */       ((JSONObject)localObject1).put("recipient", str1);
/*  78: 80 */       ((JSONObject)localObject1).put("amountNXT", str2);
/*  79: 81 */       ((JSONObject)localObject1).put("feeNXT", str3);
/*  80: 82 */       ((JSONObject)localObject1).put("deadline", str4);
/*  81:    */       
/*  82: 84 */       return (JSONStreamAware)localObject1;
/*  83:    */     }
/*  84: 86 */     if ((l3 < 100000000L) || (l3 > 215881280000000000L))
/*  85:    */     {
/*  86: 88 */       localObject1 = new JSONObject();
/*  87: 89 */       ((JSONObject)localObject1).put("response", "notifyOfIncorrectTransaction");
/*  88: 90 */       ((JSONObject)localObject1).put("message", "\"Fee\" must be at least 1 NXT!");
/*  89: 91 */       ((JSONObject)localObject1).put("recipient", str1);
/*  90: 92 */       ((JSONObject)localObject1).put("amountNXT", str2);
/*  91: 93 */       ((JSONObject)localObject1).put("feeNXT", str3);
/*  92: 94 */       ((JSONObject)localObject1).put("deadline", str4);
/*  93:    */       
/*  94: 96 */       return (JSONStreamAware)localObject1;
/*  95:    */     }
/*  96: 98 */     if ((s < 1) || (s > 1440))
/*  97:    */     {
/*  98:100 */       localObject1 = new JSONObject();
/*  99:101 */       ((JSONObject)localObject1).put("response", "notifyOfIncorrectTransaction");
/* 100:102 */       ((JSONObject)localObject1).put("message", "\"Deadline\" must be greater or equal to 1 minute and less than 24 hours!");
/* 101:103 */       ((JSONObject)localObject1).put("recipient", str1);
/* 102:104 */       ((JSONObject)localObject1).put("amountNXT", str2);
/* 103:105 */       ((JSONObject)localObject1).put("feeNXT", str3);
/* 104:106 */       ((JSONObject)localObject1).put("deadline", str4);
/* 105:    */       
/* 106:108 */       return (JSONStreamAware)localObject1;
/* 107:    */     }
/* 108:112 */     Object localObject1 = Account.getAccount(paramUser.getPublicKey());
/* 109:113 */     if ((localObject1 == null) || (Convert.safeAdd(l2, l3) > ((Account)localObject1).getUnconfirmedBalanceNQT()))
/* 110:    */     {
/* 111:115 */       localObject2 = new JSONObject();
/* 112:116 */       ((JSONObject)localObject2).put("response", "notifyOfIncorrectTransaction");
/* 113:117 */       ((JSONObject)localObject2).put("message", "Not enough funds!");
/* 114:118 */       ((JSONObject)localObject2).put("recipient", str1);
/* 115:119 */       ((JSONObject)localObject2).put("amountNXT", str2);
/* 116:120 */       ((JSONObject)localObject2).put("feeNXT", str3);
/* 117:121 */       ((JSONObject)localObject2).put("deadline", str4);
/* 118:    */       
/* 119:123 */       return (JSONStreamAware)localObject2;
/* 120:    */     }
/* 121:127 */     Object localObject2 = Nxt.getTransactionProcessor().newTransactionBuilder(paramUser.getPublicKey(), l2, l3, s, Attachment.ORDINARY_PAYMENT).recipientId(l1).build();
/* 122:    */     
/* 123:129 */     ((Transaction)localObject2).validate();
/* 124:130 */     ((Transaction)localObject2).sign(paramUser.getSecretPhrase());
/* 125:    */     
/* 126:132 */     Nxt.getTransactionProcessor().broadcast((Transaction)localObject2);
/* 127:    */     
/* 128:134 */     return JSONResponses.NOTIFY_OF_ACCEPTED_TRANSACTION;
/* 129:    */   }
/* 130:    */   
/* 131:    */   boolean requirePost()
/* 132:    */   {
/* 133:141 */     return true;
/* 134:    */   }
/* 135:    */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.user.SendMoney
 * JD-Core Version:    0.7.1
 */