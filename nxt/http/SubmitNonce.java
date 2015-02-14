/*   1:    */ package nxt.http;
/*   2:    */ 
/*   3:    */ import javax.servlet.http.HttpServletRequest;
/*   4:    */ import nxt.Account;
/*   5:    */ import nxt.Account.RewardRecipientAssignment;
/*   6:    */ import nxt.Block;
/*   7:    */ import nxt.Blockchain;
/*   8:    */ import nxt.Generator;
/*   9:    */ import nxt.Nxt;
/*  10:    */ import nxt.crypto.Crypto;
/*  11:    */ import nxt.util.Convert;
/*  12:    */ import org.json.simple.JSONObject;
/*  13:    */ import org.json.simple.JSONStreamAware;
/*  14:    */ 
/*  15:    */ public final class SubmitNonce
/*  16:    */   extends APIServlet.APIRequestHandler
/*  17:    */ {
/*  18: 20 */   static final SubmitNonce instance = new SubmitNonce();
/*  19:    */   
/*  20:    */   private SubmitNonce()
/*  21:    */   {
/*  22: 23 */     super(new APITag[] { APITag.MINING }, new String[] { "secretPhrase", "nonce", "accountId" });
/*  23:    */   }
/*  24:    */   
/*  25:    */   JSONStreamAware processRequest(HttpServletRequest paramHttpServletRequest)
/*  26:    */   {
/*  27: 28 */     String str1 = paramHttpServletRequest.getParameter("secretPhrase");
/*  28: 29 */     Long localLong = Long.valueOf(Convert.parseUnsignedLong(paramHttpServletRequest.getParameter("nonce")));
/*  29:    */     
/*  30: 31 */     String str2 = paramHttpServletRequest.getParameter("accountId");
/*  31:    */     
/*  32: 33 */     JSONObject localJSONObject = new JSONObject();
/*  33: 35 */     if (str1 == null)
/*  34:    */     {
/*  35: 36 */       localJSONObject.put("result", "Missing Passphrase");
/*  36: 37 */       return localJSONObject;
/*  37:    */     }
/*  38: 40 */     if (localLong == null)
/*  39:    */     {
/*  40: 41 */       localJSONObject.put("result", "Missing Nonce");
/*  41: 42 */       return localJSONObject;
/*  42:    */     }
/*  43: 45 */     byte[] arrayOfByte = Crypto.getPublicKey(str1);
/*  44: 46 */     Account localAccount = Account.getAccount(arrayOfByte);
/*  45:    */     Object localObject1;
/*  46:    */     Object localObject2;
/*  47:    */     Object localObject3;
/*  48: 47 */     if (localAccount != null)
/*  49:    */     {
/*  50: 49 */       if (str2 != null) {
/*  51: 50 */         localObject1 = Account.getAccount(Convert.parseAccountId(str2));
/*  52:    */       } else {
/*  53: 53 */         localObject1 = localAccount;
/*  54:    */       }
/*  55: 56 */       if (localObject1 != null)
/*  56:    */       {
/*  57: 57 */         localObject2 = ((Account)localObject1).getRewardRecipientAssignment();
/*  58: 59 */         if (localObject2 == null) {
/*  59: 60 */           localObject3 = Long.valueOf(((Account)localObject1).getId());
/*  60: 62 */         } else if (((Account.RewardRecipientAssignment)localObject2).getFromHeight() > Nxt.getBlockchain().getLastBlock().getHeight() + 1) {
/*  61: 63 */           localObject3 = Long.valueOf(((Account.RewardRecipientAssignment)localObject2).getPrevRecipientId());
/*  62:    */         } else {
/*  63: 66 */           localObject3 = Long.valueOf(((Account.RewardRecipientAssignment)localObject2).getRecipientId());
/*  64:    */         }
/*  65: 68 */         if (((Long)localObject3).longValue() != localAccount.getId())
/*  66:    */         {
/*  67: 69 */           localJSONObject.put("result", "Passphrase does not match reward recipient");
/*  68: 70 */           return localJSONObject;
/*  69:    */         }
/*  70:    */       }
/*  71:    */       else
/*  72:    */       {
/*  73: 74 */         localJSONObject.put("result", "Passphrase is for a different account");
/*  74: 75 */         return localJSONObject;
/*  75:    */       }
/*  76:    */     }
/*  77: 80 */     if ((str2 == null) || (localAccount == null))
/*  78:    */     {
/*  79: 81 */       localObject1 = Generator.addNonce(str1, localLong);
/*  80:    */     }
/*  81:    */     else
/*  82:    */     {
/*  83: 84 */       localObject2 = Account.getAccount(Convert.parseUnsignedLong(str2));
/*  84: 85 */       if ((localObject2 == null) || (((Account)localObject2).getPublicKey() == null)) {
/*  85: 87 */         localJSONObject.put("result", "Passthrough mining requires public key in blockchain");
/*  86:    */       }
/*  87: 89 */       localObject3 = ((Account)localObject2).getPublicKey();
/*  88: 90 */       localObject1 = Generator.addNonce(str1, localLong, (byte[])localObject3);
/*  89:    */     }
/*  90: 93 */     if (localObject1 == null)
/*  91:    */     {
/*  92: 94 */       localJSONObject.put("result", "failed to create generator");
/*  93: 95 */       return localJSONObject;
/*  94:    */     }
/*  95: 99 */     localJSONObject.put("result", "success");
/*  96:100 */     localJSONObject.put("deadline", ((Generator)localObject1).getDeadline());
/*  97:    */     
/*  98:102 */     return localJSONObject;
/*  99:    */   }
/* 100:    */   
/* 101:    */   boolean requirePost()
/* 102:    */   {
/* 103:107 */     return true;
/* 104:    */   }
/* 105:    */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.http.SubmitNonce
 * JD-Core Version:    0.7.1
 */