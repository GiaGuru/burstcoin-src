/*   1:    */ package nxt.user;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.util.Arrays;
/*   5:    */ import java.util.Comparator;
/*   6:    */ import java.util.Iterator;
/*   7:    */ import java.util.SortedSet;
/*   8:    */ import java.util.TreeSet;
/*   9:    */ import javax.servlet.http.HttpServletRequest;
/*  10:    */ import nxt.Account;
/*  11:    */ import nxt.Block;
/*  12:    */ import nxt.Blockchain;
/*  13:    */ import nxt.Nxt;
/*  14:    */ import nxt.Transaction;
/*  15:    */ import nxt.TransactionProcessor;
/*  16:    */ import nxt.db.DbIterator;
/*  17:    */ import nxt.util.Convert;
/*  18:    */ import org.json.simple.JSONArray;
/*  19:    */ import org.json.simple.JSONObject;
/*  20:    */ import org.json.simple.JSONStreamAware;
/*  21:    */ 
/*  22:    */ public final class UnlockAccount
/*  23:    */   extends UserServlet.UserRequestHandler
/*  24:    */ {
/*  25: 25 */   static final UnlockAccount instance = new UnlockAccount();
/*  26: 29 */   private static final Comparator<JSONObject> myTransactionsComparator = new Comparator()
/*  27:    */   {
/*  28:    */     public int compare(JSONObject paramAnonymousJSONObject1, JSONObject paramAnonymousJSONObject2)
/*  29:    */     {
/*  30: 32 */       int i = ((Number)paramAnonymousJSONObject1.get("timestamp")).intValue();
/*  31: 33 */       int j = ((Number)paramAnonymousJSONObject2.get("timestamp")).intValue();
/*  32: 34 */       if (i < j) {
/*  33: 35 */         return 1;
/*  34:    */       }
/*  35: 37 */       if (i > j) {
/*  36: 38 */         return -1;
/*  37:    */       }
/*  38: 40 */       String str1 = (String)paramAnonymousJSONObject1.get("id");
/*  39: 41 */       String str2 = (String)paramAnonymousJSONObject2.get("id");
/*  40: 42 */       return str2.compareTo(str1);
/*  41:    */     }
/*  42:    */   };
/*  43:    */   
/*  44:    */   JSONStreamAware processRequest(HttpServletRequest paramHttpServletRequest, User paramUser)
/*  45:    */     throws IOException
/*  46:    */   {
/*  47: 48 */     String str = paramHttpServletRequest.getParameter("secretPhrase");
/*  48: 50 */     for (User localUser : Users.getAllUsers()) {
/*  49: 51 */       if (str.equals(localUser.getSecretPhrase()))
/*  50:    */       {
/*  51: 52 */         localUser.lockAccount();
/*  52: 53 */         if (!localUser.isInactive()) {
/*  53: 54 */           localUser.enqueue(JSONResponses.LOCK_ACCOUNT);
/*  54:    */         }
/*  55:    */       }
/*  56:    */     }
/*  57: 59 */     long l = paramUser.unlockAccount(str);
/*  58:    */     
/*  59: 61 */     JSONObject localJSONObject1 = new JSONObject();
/*  60: 62 */     localJSONObject1.put("response", "unlockAccount");
/*  61: 63 */     localJSONObject1.put("account", Convert.toUnsignedLong(l));
/*  62: 65 */     if (str.length() < 30) {
/*  63: 67 */       localJSONObject1.put("secretPhraseStrength", Integer.valueOf(1));
/*  64:    */     } else {
/*  65: 71 */       localJSONObject1.put("secretPhraseStrength", Integer.valueOf(5));
/*  66:    */     }
/*  67: 75 */     Account localAccount = Account.getAccount(l);
/*  68: 76 */     if (localAccount == null)
/*  69:    */     {
/*  70: 78 */       localJSONObject1.put("balanceNQT", Integer.valueOf(0));
/*  71:    */     }
/*  72:    */     else
/*  73:    */     {
/*  74: 82 */       localJSONObject1.put("balanceNQT", Long.valueOf(localAccount.getUnconfirmedBalanceNQT()));
/*  75:    */       
/*  76: 84 */       JSONArray localJSONArray = new JSONArray();
/*  77: 85 */       byte[] arrayOfByte = localAccount.getPublicKey();
/*  78: 86 */       Object localObject1 = Nxt.getTransactionProcessor().getAllUnconfirmedTransactions();Object localObject2 = null;
/*  79:    */       try
/*  80:    */       {
/*  81: 87 */         while (((DbIterator)localObject1).hasNext())
/*  82:    */         {
/*  83: 88 */           Transaction localTransaction1 = (Transaction)((DbIterator)localObject1).next();
/*  84: 89 */           if (Arrays.equals(localTransaction1.getSenderPublicKey(), arrayOfByte))
/*  85:    */           {
/*  86: 91 */             localObject4 = new JSONObject();
/*  87: 92 */             ((JSONObject)localObject4).put("index", Integer.valueOf(Users.getIndex(localTransaction1)));
/*  88: 93 */             ((JSONObject)localObject4).put("transactionTimestamp", Integer.valueOf(localTransaction1.getTimestamp()));
/*  89: 94 */             ((JSONObject)localObject4).put("deadline", Short.valueOf(localTransaction1.getDeadline()));
/*  90: 95 */             ((JSONObject)localObject4).put("account", Convert.toUnsignedLong(localTransaction1.getRecipientId()));
/*  91: 96 */             ((JSONObject)localObject4).put("sentAmountNQT", Long.valueOf(localTransaction1.getAmountNQT()));
/*  92: 97 */             if (l == localTransaction1.getRecipientId()) {
/*  93: 98 */               ((JSONObject)localObject4).put("receivedAmountNQT", Long.valueOf(localTransaction1.getAmountNQT()));
/*  94:    */             }
/*  95:100 */             ((JSONObject)localObject4).put("feeNQT", Long.valueOf(localTransaction1.getFeeNQT()));
/*  96:101 */             ((JSONObject)localObject4).put("numberOfConfirmations", Integer.valueOf(-1));
/*  97:102 */             ((JSONObject)localObject4).put("id", localTransaction1.getStringId());
/*  98:    */             
/*  99:104 */             localJSONArray.add(localObject4);
/* 100:    */           }
/* 101:106 */           else if (l == localTransaction1.getRecipientId())
/* 102:    */           {
/* 103:108 */             localObject4 = new JSONObject();
/* 104:109 */             ((JSONObject)localObject4).put("index", Integer.valueOf(Users.getIndex(localTransaction1)));
/* 105:110 */             ((JSONObject)localObject4).put("transactionTimestamp", Integer.valueOf(localTransaction1.getTimestamp()));
/* 106:111 */             ((JSONObject)localObject4).put("deadline", Short.valueOf(localTransaction1.getDeadline()));
/* 107:112 */             ((JSONObject)localObject4).put("account", Convert.toUnsignedLong(localTransaction1.getSenderId()));
/* 108:113 */             ((JSONObject)localObject4).put("receivedAmountNQT", Long.valueOf(localTransaction1.getAmountNQT()));
/* 109:114 */             ((JSONObject)localObject4).put("feeNQT", Long.valueOf(localTransaction1.getFeeNQT()));
/* 110:115 */             ((JSONObject)localObject4).put("numberOfConfirmations", Integer.valueOf(-1));
/* 111:116 */             ((JSONObject)localObject4).put("id", localTransaction1.getStringId());
/* 112:    */             
/* 113:118 */             localJSONArray.add(localObject4);
/* 114:    */           }
/* 115:    */         }
/* 116:    */       }
/* 117:    */       catch (Throwable localThrowable2)
/* 118:    */       {
/* 119: 86 */         localObject2 = localThrowable2;throw localThrowable2;
/* 120:    */       }
/* 121:    */       finally
/* 122:    */       {
/* 123:122 */         if (localObject1 != null) {
/* 124:122 */           if (localObject2 != null) {
/* 125:    */             try
/* 126:    */             {
/* 127:122 */               ((DbIterator)localObject1).close();
/* 128:    */             }
/* 129:    */             catch (Throwable localThrowable7)
/* 130:    */             {
/* 131:122 */               ((Throwable)localObject2).addSuppressed(localThrowable7);
/* 132:    */             }
/* 133:    */           } else {
/* 134:122 */             ((DbIterator)localObject1).close();
/* 135:    */           }
/* 136:    */         }
/* 137:    */       }
/* 138:124 */       localObject1 = new TreeSet(myTransactionsComparator);
/* 139:    */       
/* 140:126 */       int i = Nxt.getBlockchain().getLastBlock().getHeight();
/* 141:127 */       Object localObject3 = Nxt.getBlockchain().getBlocks(localAccount, 0);Object localObject4 = null;
/* 142:    */       JSONObject localJSONObject2;
/* 143:    */       try
/* 144:    */       {
/* 145:128 */         while (((DbIterator)localObject3).hasNext())
/* 146:    */         {
/* 147:129 */           Block localBlock = (Block)((DbIterator)localObject3).next();
/* 148:130 */           if (localBlock.getTotalFeeNQT() > 0L)
/* 149:    */           {
/* 150:131 */             localJSONObject2 = new JSONObject();
/* 151:132 */             localJSONObject2.put("index", "block" + Users.getIndex(localBlock));
/* 152:133 */             localJSONObject2.put("blockTimestamp", Integer.valueOf(localBlock.getTimestamp()));
/* 153:134 */             localJSONObject2.put("block", localBlock.getStringId());
/* 154:135 */             localJSONObject2.put("earnedAmountNQT", Long.valueOf(localBlock.getTotalFeeNQT()));
/* 155:136 */             localJSONObject2.put("numberOfConfirmations", Integer.valueOf(i - localBlock.getHeight()));
/* 156:137 */             localJSONObject2.put("id", "-");
/* 157:138 */             localJSONObject2.put("timestamp", Integer.valueOf(localBlock.getTimestamp()));
/* 158:139 */             ((SortedSet)localObject1).add(localJSONObject2);
/* 159:    */           }
/* 160:    */         }
/* 161:    */       }
/* 162:    */       catch (Throwable localThrowable4)
/* 163:    */       {
/* 164:127 */         localObject4 = localThrowable4;throw localThrowable4;
/* 165:    */       }
/* 166:    */       finally
/* 167:    */       {
/* 168:142 */         if (localObject3 != null) {
/* 169:142 */           if (localObject4 != null) {
/* 170:    */             try
/* 171:    */             {
/* 172:142 */               ((DbIterator)localObject3).close();
/* 173:    */             }
/* 174:    */             catch (Throwable localThrowable8)
/* 175:    */             {
/* 176:142 */               ((Throwable)localObject4).addSuppressed(localThrowable8);
/* 177:    */             }
/* 178:    */           } else {
/* 179:142 */             ((DbIterator)localObject3).close();
/* 180:    */           }
/* 181:    */         }
/* 182:    */       }
/* 183:144 */       localObject3 = Nxt.getBlockchain().getTransactions(localAccount, (byte)-1, (byte)-1, 0);localObject4 = null;
/* 184:    */       try
/* 185:    */       {
/* 186:145 */         while (((DbIterator)localObject3).hasNext())
/* 187:    */         {
/* 188:146 */           Transaction localTransaction2 = (Transaction)((DbIterator)localObject3).next();
/* 189:147 */           if (localTransaction2.getSenderId() == l)
/* 190:    */           {
/* 191:148 */             localJSONObject2 = new JSONObject();
/* 192:149 */             localJSONObject2.put("index", Integer.valueOf(Users.getIndex(localTransaction2)));
/* 193:150 */             localJSONObject2.put("blockTimestamp", Integer.valueOf(localTransaction2.getBlockTimestamp()));
/* 194:151 */             localJSONObject2.put("transactionTimestamp", Integer.valueOf(localTransaction2.getTimestamp()));
/* 195:152 */             localJSONObject2.put("account", Convert.toUnsignedLong(localTransaction2.getRecipientId()));
/* 196:153 */             localJSONObject2.put("sentAmountNQT", Long.valueOf(localTransaction2.getAmountNQT()));
/* 197:154 */             if (l == localTransaction2.getRecipientId()) {
/* 198:155 */               localJSONObject2.put("receivedAmountNQT", Long.valueOf(localTransaction2.getAmountNQT()));
/* 199:    */             }
/* 200:157 */             localJSONObject2.put("feeNQT", Long.valueOf(localTransaction2.getFeeNQT()));
/* 201:158 */             localJSONObject2.put("numberOfConfirmations", Integer.valueOf(i - localTransaction2.getHeight()));
/* 202:159 */             localJSONObject2.put("id", localTransaction2.getStringId());
/* 203:160 */             localJSONObject2.put("timestamp", Integer.valueOf(localTransaction2.getTimestamp()));
/* 204:161 */             ((SortedSet)localObject1).add(localJSONObject2);
/* 205:    */           }
/* 206:162 */           else if (localTransaction2.getRecipientId() == l)
/* 207:    */           {
/* 208:163 */             localJSONObject2 = new JSONObject();
/* 209:164 */             localJSONObject2.put("index", Integer.valueOf(Users.getIndex(localTransaction2)));
/* 210:165 */             localJSONObject2.put("blockTimestamp", Integer.valueOf(localTransaction2.getBlockTimestamp()));
/* 211:166 */             localJSONObject2.put("transactionTimestamp", Integer.valueOf(localTransaction2.getTimestamp()));
/* 212:167 */             localJSONObject2.put("account", Convert.toUnsignedLong(localTransaction2.getSenderId()));
/* 213:168 */             localJSONObject2.put("receivedAmountNQT", Long.valueOf(localTransaction2.getAmountNQT()));
/* 214:169 */             localJSONObject2.put("feeNQT", Long.valueOf(localTransaction2.getFeeNQT()));
/* 215:170 */             localJSONObject2.put("numberOfConfirmations", Integer.valueOf(i - localTransaction2.getHeight()));
/* 216:171 */             localJSONObject2.put("id", localTransaction2.getStringId());
/* 217:172 */             localJSONObject2.put("timestamp", Integer.valueOf(localTransaction2.getTimestamp()));
/* 218:173 */             ((SortedSet)localObject1).add(localJSONObject2);
/* 219:    */           }
/* 220:    */         }
/* 221:    */       }
/* 222:    */       catch (Throwable localThrowable6)
/* 223:    */       {
/* 224:144 */         localObject4 = localThrowable6;throw localThrowable6;
/* 225:    */       }
/* 226:    */       finally
/* 227:    */       {
/* 228:176 */         if (localObject3 != null) {
/* 229:176 */           if (localObject4 != null) {
/* 230:    */             try
/* 231:    */             {
/* 232:176 */               ((DbIterator)localObject3).close();
/* 233:    */             }
/* 234:    */             catch (Throwable localThrowable9)
/* 235:    */             {
/* 236:176 */               ((Throwable)localObject4).addSuppressed(localThrowable9);
/* 237:    */             }
/* 238:    */           } else {
/* 239:176 */             ((DbIterator)localObject3).close();
/* 240:    */           }
/* 241:    */         }
/* 242:    */       }
/* 243:178 */       localObject3 = ((SortedSet)localObject1).iterator();
/* 244:179 */       while ((localJSONArray.size() < 1000) && (((Iterator)localObject3).hasNext())) {
/* 245:180 */         localJSONArray.add(((Iterator)localObject3).next());
/* 246:    */       }
/* 247:183 */       if (localJSONArray.size() > 0)
/* 248:    */       {
/* 249:184 */         localObject4 = new JSONObject();
/* 250:185 */         ((JSONObject)localObject4).put("response", "processNewData");
/* 251:186 */         ((JSONObject)localObject4).put("addedMyTransactions", localJSONArray);
/* 252:187 */         paramUser.enqueue((JSONStreamAware)localObject4);
/* 253:    */       }
/* 254:    */     }
/* 255:190 */     return localJSONObject1;
/* 256:    */   }
/* 257:    */   
/* 258:    */   boolean requirePost()
/* 259:    */   {
/* 260:195 */     return true;
/* 261:    */   }
/* 262:    */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.user.UnlockAccount
 * JD-Core Version:    0.7.1
 */