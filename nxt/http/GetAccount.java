/*  1:   */ package nxt.http;
/*  2:   */ 
/*  3:   */ import javax.servlet.http.HttpServletRequest;
/*  4:   */ import nxt.Account;
/*  5:   */ import nxt.Account.AccountAsset;
/*  6:   */ import nxt.NxtException;
/*  7:   */ import nxt.db.DbIterator;
/*  8:   */ import nxt.util.Convert;
/*  9:   */ import org.json.simple.JSONArray;
/* 10:   */ import org.json.simple.JSONObject;
/* 11:   */ import org.json.simple.JSONStreamAware;
/* 12:   */ 
/* 13:   */ public final class GetAccount
/* 14:   */   extends APIServlet.APIRequestHandler
/* 15:   */ {
/* 16:15 */   static final GetAccount instance = new GetAccount();
/* 17:   */   
/* 18:   */   private GetAccount()
/* 19:   */   {
/* 20:18 */     super(new APITag[] { APITag.ACCOUNTS }, new String[] { "account" });
/* 21:   */   }
/* 22:   */   
/* 23:   */   JSONStreamAware processRequest(HttpServletRequest paramHttpServletRequest)
/* 24:   */     throws NxtException
/* 25:   */   {
/* 26:24 */     Account localAccount = ParameterParser.getAccount(paramHttpServletRequest);
/* 27:   */     
/* 28:26 */     JSONObject localJSONObject1 = JSONData.accountBalance(localAccount);
/* 29:27 */     JSONData.putAccount(localJSONObject1, "account", localAccount.getId());
/* 30:29 */     if (localAccount.getPublicKey() != null) {
/* 31:30 */       localJSONObject1.put("publicKey", Convert.toHexString(localAccount.getPublicKey()));
/* 32:   */     }
/* 33:32 */     if (localAccount.getName() != null) {
/* 34:33 */       localJSONObject1.put("name", localAccount.getName());
/* 35:   */     }
/* 36:35 */     if (localAccount.getDescription() != null) {
/* 37:36 */       localJSONObject1.put("description", localAccount.getDescription());
/* 38:   */     }
/* 39:38 */     if (localAccount.getCurrentLesseeId() != 0L)
/* 40:   */     {
/* 41:39 */       JSONData.putAccount(localJSONObject1, "currentLessee", localAccount.getCurrentLesseeId());
/* 42:40 */       localJSONObject1.put("currentLeasingHeightFrom", Integer.valueOf(localAccount.getCurrentLeasingHeightFrom()));
/* 43:41 */       localJSONObject1.put("currentLeasingHeightTo", Integer.valueOf(localAccount.getCurrentLeasingHeightTo()));
/* 44:42 */       if (localAccount.getNextLesseeId() != 0L)
/* 45:   */       {
/* 46:43 */         JSONData.putAccount(localJSONObject1, "nextLessee", localAccount.getNextLesseeId());
/* 47:44 */         localJSONObject1.put("nextLeasingHeightFrom", Integer.valueOf(localAccount.getNextLeasingHeightFrom()));
/* 48:45 */         localJSONObject1.put("nextLeasingHeightTo", Integer.valueOf(localAccount.getNextLeasingHeightTo()));
/* 49:   */       }
/* 50:   */     }
/* 51:48 */     DbIterator localDbIterator = localAccount.getLessors();Object localObject1 = null;
/* 52:   */     JSONArray localJSONArray3;
/* 53:   */     Object localObject2;
/* 54:   */     try
/* 55:   */     {
/* 56:49 */       if (localDbIterator.hasNext())
/* 57:   */       {
/* 58:50 */         JSONArray localJSONArray1 = new JSONArray();
/* 59:51 */         localJSONArray3 = new JSONArray();
/* 60:52 */         while (localDbIterator.hasNext())
/* 61:   */         {
/* 62:53 */           localObject2 = (Account)localDbIterator.next();
/* 63:54 */           localJSONArray1.add(Convert.toUnsignedLong(((Account)localObject2).getId()));
/* 64:55 */           localJSONArray3.add(Convert.rsAccount(((Account)localObject2).getId()));
/* 65:   */         }
/* 66:57 */         localJSONObject1.put("lessors", localJSONArray1);
/* 67:58 */         localJSONObject1.put("lessorsRS", localJSONArray3);
/* 68:   */       }
/* 69:   */     }
/* 70:   */     catch (Throwable localThrowable2)
/* 71:   */     {
/* 72:48 */       localObject1 = localThrowable2;throw localThrowable2;
/* 73:   */     }
/* 74:   */     finally
/* 75:   */     {
/* 76:60 */       if (localDbIterator != null) {
/* 77:60 */         if (localObject1 != null) {
/* 78:   */           try
/* 79:   */           {
/* 80:60 */             localDbIterator.close();
/* 81:   */           }
/* 82:   */           catch (Throwable localThrowable5)
/* 83:   */           {
/* 84:60 */             ((Throwable)localObject1).addSuppressed(localThrowable5);
/* 85:   */           }
/* 86:   */         } else {
/* 87:60 */           localDbIterator.close();
/* 88:   */         }
/* 89:   */       }
/* 90:   */     }
/* 91:62 */     localDbIterator = localAccount.getAssets(0, -1);localObject1 = null;
/* 92:   */     try
/* 93:   */     {
/* 94:63 */       JSONArray localJSONArray2 = new JSONArray();
/* 95:64 */       localJSONArray3 = new JSONArray();
/* 96:65 */       while (localDbIterator.hasNext())
/* 97:   */       {
/* 98:66 */         localObject2 = (Account.AccountAsset)localDbIterator.next();
/* 99:67 */         JSONObject localJSONObject2 = new JSONObject();
/* :0:68 */         localJSONObject2.put("asset", Convert.toUnsignedLong(((Account.AccountAsset)localObject2).getAssetId()));
/* :1:69 */         localJSONObject2.put("balanceQNT", String.valueOf(((Account.AccountAsset)localObject2).getQuantityQNT()));
/* :2:70 */         localJSONArray2.add(localJSONObject2);
/* :3:71 */         JSONObject localJSONObject3 = new JSONObject();
/* :4:72 */         localJSONObject3.put("asset", Convert.toUnsignedLong(((Account.AccountAsset)localObject2).getAssetId()));
/* :5:73 */         localJSONObject3.put("unconfirmedBalanceQNT", String.valueOf(((Account.AccountAsset)localObject2).getUnconfirmedQuantityQNT()));
/* :6:74 */         localJSONArray3.add(localJSONObject3);
/* :7:   */       }
/* :8:76 */       if (localJSONArray2.size() > 0) {
/* :9:77 */         localJSONObject1.put("assetBalances", localJSONArray2);
/* ;0:   */       }
/* ;1:79 */       if (localJSONArray3.size() > 0) {
/* ;2:80 */         localJSONObject1.put("unconfirmedAssetBalances", localJSONArray3);
/* ;3:   */       }
/* ;4:   */     }
/* ;5:   */     catch (Throwable localThrowable4)
/* ;6:   */     {
/* ;7:62 */       localObject1 = localThrowable4;throw localThrowable4;
/* ;8:   */     }
/* ;9:   */     finally
/* <0:   */     {
/* <1:82 */       if (localDbIterator != null) {
/* <2:82 */         if (localObject1 != null) {
/* <3:   */           try
/* <4:   */           {
/* <5:82 */             localDbIterator.close();
/* <6:   */           }
/* <7:   */           catch (Throwable localThrowable6)
/* <8:   */           {
/* <9:82 */             ((Throwable)localObject1).addSuppressed(localThrowable6);
/* =0:   */           }
/* =1:   */         } else {
/* =2:82 */           localDbIterator.close();
/* =3:   */         }
/* =4:   */       }
/* =5:   */     }
/* =6:83 */     return localJSONObject1;
/* =7:   */   }
/* =8:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.http.GetAccount
 * JD-Core Version:    0.7.1
 */