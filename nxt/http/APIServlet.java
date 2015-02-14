/*   1:    */ package nxt.http;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.util.Arrays;
/*   5:    */ import java.util.Collections;
/*   6:    */ import java.util.HashMap;
/*   7:    */ import java.util.HashSet;
/*   8:    */ import java.util.List;
/*   9:    */ import java.util.Map;
/*  10:    */ import java.util.Set;
/*  11:    */ import javax.servlet.ServletException;
/*  12:    */ import javax.servlet.http.HttpServlet;
/*  13:    */ import javax.servlet.http.HttpServletRequest;
/*  14:    */ import javax.servlet.http.HttpServletResponse;
/*  15:    */ import nxt.Nxt;
/*  16:    */ import nxt.NxtException;
/*  17:    */ import org.json.simple.JSONStreamAware;
/*  18:    */ 
/*  19:    */ public final class APIServlet
/*  20:    */   extends HttpServlet
/*  21:    */ {
/*  22:    */   static abstract class APIRequestHandler
/*  23:    */   {
/*  24:    */     private final List<String> parameters;
/*  25:    */     private final Set<APITag> apiTags;
/*  26:    */     
/*  27:    */     APIRequestHandler(APITag[] paramArrayOfAPITag, String... paramVarArgs)
/*  28:    */     {
/*  29: 37 */       this.parameters = Collections.unmodifiableList(Arrays.asList(paramVarArgs));
/*  30: 38 */       this.apiTags = Collections.unmodifiableSet(new HashSet(Arrays.asList(paramArrayOfAPITag)));
/*  31:    */     }
/*  32:    */     
/*  33:    */     final List<String> getParameters()
/*  34:    */     {
/*  35: 42 */       return this.parameters;
/*  36:    */     }
/*  37:    */     
/*  38:    */     final Set<APITag> getAPITags()
/*  39:    */     {
/*  40: 46 */       return this.apiTags;
/*  41:    */     }
/*  42:    */     
/*  43:    */     abstract JSONStreamAware processRequest(HttpServletRequest paramHttpServletRequest)
/*  44:    */       throws NxtException;
/*  45:    */     
/*  46:    */     boolean requirePost()
/*  47:    */     {
/*  48: 52 */       return false;
/*  49:    */     }
/*  50:    */     
/*  51:    */     boolean startDbTransaction()
/*  52:    */     {
/*  53: 56 */       return false;
/*  54:    */     }
/*  55:    */   }
/*  56:    */   
/*  57: 61 */   private static final boolean enforcePost = Nxt.getBooleanProperty("nxt.apiServerEnforcePOST").booleanValue();
/*  58:    */   static final Map<String, APIRequestHandler> apiRequestHandlers;
/*  59:    */   
/*  60:    */   static
/*  61:    */   {
/*  62: 67 */     HashMap localHashMap = new HashMap();
/*  63:    */     
/*  64: 69 */     localHashMap.put("broadcastTransaction", BroadcastTransaction.instance);
/*  65: 70 */     localHashMap.put("calculateFullHash", CalculateFullHash.instance);
/*  66: 71 */     localHashMap.put("cancelAskOrder", CancelAskOrder.instance);
/*  67: 72 */     localHashMap.put("cancelBidOrder", CancelBidOrder.instance);
/*  68:    */     
/*  69:    */ 
/*  70: 75 */     localHashMap.put("decryptFrom", DecryptFrom.instance);
/*  71: 76 */     localHashMap.put("dgsListing", DGSListing.instance);
/*  72: 77 */     localHashMap.put("dgsDelisting", DGSDelisting.instance);
/*  73: 78 */     localHashMap.put("dgsDelivery", DGSDelivery.instance);
/*  74: 79 */     localHashMap.put("dgsFeedback", DGSFeedback.instance);
/*  75: 80 */     localHashMap.put("dgsPriceChange", DGSPriceChange.instance);
/*  76: 81 */     localHashMap.put("dgsPurchase", DGSPurchase.instance);
/*  77: 82 */     localHashMap.put("dgsQuantityChange", DGSQuantityChange.instance);
/*  78: 83 */     localHashMap.put("dgsRefund", DGSRefund.instance);
/*  79: 84 */     localHashMap.put("decodeHallmark", DecodeHallmark.instance);
/*  80: 85 */     localHashMap.put("decodeToken", DecodeToken.instance);
/*  81: 86 */     localHashMap.put("encryptTo", EncryptTo.instance);
/*  82: 87 */     localHashMap.put("generateToken", GenerateToken.instance);
/*  83: 88 */     localHashMap.put("getAccount", GetAccount.instance);
/*  84: 89 */     localHashMap.put("getAccountBlockIds", GetAccountBlockIds.instance);
/*  85: 90 */     localHashMap.put("getAccountBlocks", GetAccountBlocks.instance);
/*  86: 91 */     localHashMap.put("getAccountId", GetAccountId.instance);
/*  87: 92 */     localHashMap.put("getAccountPublicKey", GetAccountPublicKey.instance);
/*  88: 93 */     localHashMap.put("getAccountTransactionIds", GetAccountTransactionIds.instance);
/*  89: 94 */     localHashMap.put("getAccountTransactions", GetAccountTransactions.instance);
/*  90: 95 */     localHashMap.put("getAccountLessors", GetAccountLessors.instance);
/*  91: 96 */     localHashMap.put("sellAlias", SellAlias.instance);
/*  92: 97 */     localHashMap.put("buyAlias", BuyAlias.instance);
/*  93: 98 */     localHashMap.put("getAlias", GetAlias.instance);
/*  94: 99 */     localHashMap.put("getAliases", GetAliases.instance);
/*  95:100 */     localHashMap.put("getAllAssets", GetAllAssets.instance);
/*  96:101 */     localHashMap.put("getAsset", GetAsset.instance);
/*  97:102 */     localHashMap.put("getAssets", GetAssets.instance);
/*  98:103 */     localHashMap.put("getAssetIds", GetAssetIds.instance);
/*  99:104 */     localHashMap.put("getAssetsByIssuer", GetAssetsByIssuer.instance);
/* 100:105 */     localHashMap.put("getAssetAccounts", GetAssetAccounts.instance);
/* 101:106 */     localHashMap.put("getBalance", GetBalance.instance);
/* 102:107 */     localHashMap.put("getBlock", GetBlock.instance);
/* 103:108 */     localHashMap.put("getBlockId", GetBlockId.instance);
/* 104:109 */     localHashMap.put("getBlocks", GetBlocks.instance);
/* 105:110 */     localHashMap.put("getBlockchainStatus", GetBlockchainStatus.instance);
/* 106:111 */     localHashMap.put("getConstants", GetConstants.instance);
/* 107:112 */     localHashMap.put("getDGSGoods", GetDGSGoods.instance);
/* 108:113 */     localHashMap.put("getDGSGood", GetDGSGood.instance);
/* 109:114 */     localHashMap.put("getDGSPurchases", GetDGSPurchases.instance);
/* 110:115 */     localHashMap.put("getDGSPurchase", GetDGSPurchase.instance);
/* 111:116 */     localHashMap.put("getDGSPendingPurchases", GetDGSPendingPurchases.instance);
/* 112:117 */     localHashMap.put("getGuaranteedBalance", GetGuaranteedBalance.instance);
/* 113:118 */     localHashMap.put("getECBlock", GetECBlock.instance);
/* 114:119 */     localHashMap.put("getMyInfo", GetMyInfo.instance);
/* 115:    */     
/* 116:121 */     localHashMap.put("getPeer", GetPeer.instance);
/* 117:122 */     localHashMap.put("getPeers", GetPeers.instance);
/* 118:    */     
/* 119:    */ 
/* 120:125 */     localHashMap.put("getState", GetState.instance);
/* 121:126 */     localHashMap.put("getTime", GetTime.instance);
/* 122:127 */     localHashMap.put("getTrades", GetTrades.instance);
/* 123:128 */     localHashMap.put("getAllTrades", GetAllTrades.instance);
/* 124:129 */     localHashMap.put("getAssetTransfers", GetAssetTransfers.instance);
/* 125:130 */     localHashMap.put("getTransaction", GetTransaction.instance);
/* 126:131 */     localHashMap.put("getTransactionBytes", GetTransactionBytes.instance);
/* 127:132 */     localHashMap.put("getUnconfirmedTransactionIds", GetUnconfirmedTransactionIds.instance);
/* 128:133 */     localHashMap.put("getUnconfirmedTransactions", GetUnconfirmedTransactions.instance);
/* 129:134 */     localHashMap.put("getAccountCurrentAskOrderIds", GetAccountCurrentAskOrderIds.instance);
/* 130:135 */     localHashMap.put("getAccountCurrentBidOrderIds", GetAccountCurrentBidOrderIds.instance);
/* 131:136 */     localHashMap.put("getAccountCurrentAskOrders", GetAccountCurrentAskOrders.instance);
/* 132:137 */     localHashMap.put("getAccountCurrentBidOrders", GetAccountCurrentBidOrders.instance);
/* 133:138 */     localHashMap.put("getAllOpenAskOrders", GetAllOpenAskOrders.instance);
/* 134:139 */     localHashMap.put("getAllOpenBidOrders", GetAllOpenBidOrders.instance);
/* 135:140 */     localHashMap.put("getAskOrder", GetAskOrder.instance);
/* 136:141 */     localHashMap.put("getAskOrderIds", GetAskOrderIds.instance);
/* 137:142 */     localHashMap.put("getAskOrders", GetAskOrders.instance);
/* 138:143 */     localHashMap.put("getBidOrder", GetBidOrder.instance);
/* 139:144 */     localHashMap.put("getBidOrderIds", GetBidOrderIds.instance);
/* 140:145 */     localHashMap.put("getBidOrders", GetBidOrders.instance);
/* 141:146 */     localHashMap.put("issueAsset", IssueAsset.instance);
/* 142:147 */     localHashMap.put("leaseBalance", LeaseBalance.instance);
/* 143:148 */     localHashMap.put("longConvert", LongConvert.instance);
/* 144:149 */     localHashMap.put("markHost", MarkHost.instance);
/* 145:150 */     localHashMap.put("parseTransaction", ParseTransaction.instance);
/* 146:151 */     localHashMap.put("placeAskOrder", PlaceAskOrder.instance);
/* 147:152 */     localHashMap.put("placeBidOrder", PlaceBidOrder.instance);
/* 148:153 */     localHashMap.put("rsConvert", RSConvert.instance);
/* 149:154 */     localHashMap.put("readMessage", ReadMessage.instance);
/* 150:155 */     localHashMap.put("sendMessage", SendMessage.instance);
/* 151:156 */     localHashMap.put("sendMoney", SendMoney.instance);
/* 152:157 */     localHashMap.put("setAccountInfo", SetAccountInfo.instance);
/* 153:158 */     localHashMap.put("setAlias", SetAlias.instance);
/* 154:159 */     localHashMap.put("signTransaction", SignTransaction.instance);
/* 155:    */     
/* 156:    */ 
/* 157:    */ 
/* 158:163 */     localHashMap.put("transferAsset", TransferAsset.instance);
/* 159:164 */     localHashMap.put("getMiningInfo", GetMiningInfo.instance);
/* 160:165 */     localHashMap.put("submitNonce", SubmitNonce.instance);
/* 161:166 */     localHashMap.put("getRewardRecipient", GetRewardRecipient.instance);
/* 162:167 */     localHashMap.put("setRewardRecipient", SetRewardRecipient.instance);
/* 163:168 */     localHashMap.put("getAccountsWithRewardRecipient", GetAccountsWithRewardRecipient.instance);
/* 164:169 */     localHashMap.put("sendMoneyEscrow", SendMoneyEscrow.instance);
/* 165:170 */     localHashMap.put("escrowSign", EscrowSign.instance);
/* 166:171 */     localHashMap.put("getEscrowTransaction", GetEscrowTransaction.instance);
/* 167:172 */     localHashMap.put("getAccountEscrowTransactions", GetAccountEscrowTransactions.instance);
/* 168:173 */     localHashMap.put("sendMoneySubscription", SendMoneySubscription.instance);
/* 169:174 */     localHashMap.put("subscriptionCancel", SubscriptionCancel.instance);
/* 170:175 */     localHashMap.put("getSubscription", GetSubscription.instance);
/* 171:176 */     localHashMap.put("getAccountSubscriptions", GetAccountSubscriptions.instance);
/* 172:177 */     localHashMap.put("getSubscriptionsToAccount", GetSubscriptionsToAccount.instance);
/* 173:178 */     localHashMap.put("createATProgram", CreateATProgram.instance);
/* 174:179 */     localHashMap.put("getAT", GetAT.instance);
/* 175:180 */     localHashMap.put("getATDetails", GetATDetails.instance);
/* 176:181 */     localHashMap.put("getATIds", GetATIds.instance);
/* 177:182 */     localHashMap.put("getATLong", GetATLong.instance);
/* 178:183 */     localHashMap.put("getAccountATs", GetAccountATs.instance);
/* 179:185 */     if (API.enableDebugAPI)
/* 180:    */     {
/* 181:186 */       localHashMap.put("clearUnconfirmedTransactions", ClearUnconfirmedTransactions.instance);
/* 182:187 */       localHashMap.put("fullReset", FullReset.instance);
/* 183:188 */       localHashMap.put("popOff", PopOff.instance);
/* 184:189 */       localHashMap.put("scan", Scan.instance);
/* 185:    */     }
/* 186:192 */     apiRequestHandlers = Collections.unmodifiableMap(localHashMap);
/* 187:    */   }
/* 188:    */   
/* 189:    */   protected void doGet(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
/* 190:    */     throws ServletException, IOException
/* 191:    */   {
/* 192:197 */     process(paramHttpServletRequest, paramHttpServletResponse);
/* 193:    */   }
/* 194:    */   
/* 195:    */   protected void doPost(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
/* 196:    */     throws ServletException, IOException
/* 197:    */   {
/* 198:202 */     process(paramHttpServletRequest, paramHttpServletResponse);
/* 199:    */   }
/* 200:    */   
/* 201:    */   /* Error */
/* 202:    */   private void process(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
/* 203:    */     throws IOException
/* 204:    */   {
/* 205:    */     // Byte code:
/* 206:    */     //   0: aload_2
/* 207:    */     //   1: ldc 3
/* 208:    */     //   3: ldc 4
/* 209:    */     //   5: invokeinterface 5 3 0
/* 210:    */     //   10: aload_2
/* 211:    */     //   11: ldc 6
/* 212:    */     //   13: ldc 7
/* 213:    */     //   15: invokeinterface 5 3 0
/* 214:    */     //   20: aload_2
/* 215:    */     //   21: ldc 8
/* 216:    */     //   23: lconst_0
/* 217:    */     //   24: invokeinterface 9 4 0
/* 218:    */     //   29: getstatic 10	nxt/util/JSON:emptyJSON	Lorg/json/simple/JSONStreamAware;
/* 219:    */     //   32: astore_3
/* 220:    */     //   33: invokestatic 11	java/lang/System:currentTimeMillis	()J
/* 221:    */     //   36: lstore 4
/* 222:    */     //   38: getstatic 12	nxt/http/API:allowedBotHosts	Ljava/util/Set;
/* 223:    */     //   41: ifnull +139 -> 180
/* 224:    */     //   44: getstatic 12	nxt/http/API:allowedBotHosts	Ljava/util/Set;
/* 225:    */     //   47: aload_1
/* 226:    */     //   48: invokeinterface 13 1 0
/* 227:    */     //   53: invokeinterface 14 2 0
/* 228:    */     //   58: ifne +122 -> 180
/* 229:    */     //   61: getstatic 15	nxt/http/JSONResponses:ERROR_NOT_ALLOWED	Lorg/json/simple/JSONStreamAware;
/* 230:    */     //   64: astore_3
/* 231:    */     //   65: aload_2
/* 232:    */     //   66: ldc 16
/* 233:    */     //   68: invokeinterface 17 2 0
/* 234:    */     //   73: aload_2
/* 235:    */     //   74: invokeinterface 18 1 0
/* 236:    */     //   79: astore 6
/* 237:    */     //   81: aconst_null
/* 238:    */     //   82: astore 7
/* 239:    */     //   84: aload_3
/* 240:    */     //   85: aload 6
/* 241:    */     //   87: invokeinterface 19 2 0
/* 242:    */     //   92: aload 6
/* 243:    */     //   94: ifnull +85 -> 179
/* 244:    */     //   97: aload 7
/* 245:    */     //   99: ifnull +23 -> 122
/* 246:    */     //   102: aload 6
/* 247:    */     //   104: invokevirtual 20	java/io/Writer:close	()V
/* 248:    */     //   107: goto +72 -> 179
/* 249:    */     //   110: astore 8
/* 250:    */     //   112: aload 7
/* 251:    */     //   114: aload 8
/* 252:    */     //   116: invokevirtual 22	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 253:    */     //   119: goto +60 -> 179
/* 254:    */     //   122: aload 6
/* 255:    */     //   124: invokevirtual 20	java/io/Writer:close	()V
/* 256:    */     //   127: goto +52 -> 179
/* 257:    */     //   130: astore 8
/* 258:    */     //   132: aload 8
/* 259:    */     //   134: astore 7
/* 260:    */     //   136: aload 8
/* 261:    */     //   138: athrow
/* 262:    */     //   139: astore 9
/* 263:    */     //   141: aload 6
/* 264:    */     //   143: ifnull +33 -> 176
/* 265:    */     //   146: aload 7
/* 266:    */     //   148: ifnull +23 -> 171
/* 267:    */     //   151: aload 6
/* 268:    */     //   153: invokevirtual 20	java/io/Writer:close	()V
/* 269:    */     //   156: goto +20 -> 176
/* 270:    */     //   159: astore 10
/* 271:    */     //   161: aload 7
/* 272:    */     //   163: aload 10
/* 273:    */     //   165: invokevirtual 22	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 274:    */     //   168: goto +8 -> 176
/* 275:    */     //   171: aload 6
/* 276:    */     //   173: invokevirtual 20	java/io/Writer:close	()V
/* 277:    */     //   176: aload 9
/* 278:    */     //   178: athrow
/* 279:    */     //   179: return
/* 280:    */     //   180: aload_1
/* 281:    */     //   181: ldc 23
/* 282:    */     //   183: invokeinterface 24 2 0
/* 283:    */     //   188: astore 6
/* 284:    */     //   190: aload 6
/* 285:    */     //   192: ifnonnull +122 -> 314
/* 286:    */     //   195: getstatic 25	nxt/http/JSONResponses:ERROR_INCORRECT_REQUEST	Lorg/json/simple/JSONStreamAware;
/* 287:    */     //   198: astore_3
/* 288:    */     //   199: aload_2
/* 289:    */     //   200: ldc 16
/* 290:    */     //   202: invokeinterface 17 2 0
/* 291:    */     //   207: aload_2
/* 292:    */     //   208: invokeinterface 18 1 0
/* 293:    */     //   213: astore 7
/* 294:    */     //   215: aconst_null
/* 295:    */     //   216: astore 8
/* 296:    */     //   218: aload_3
/* 297:    */     //   219: aload 7
/* 298:    */     //   221: invokeinterface 19 2 0
/* 299:    */     //   226: aload 7
/* 300:    */     //   228: ifnull +85 -> 313
/* 301:    */     //   231: aload 8
/* 302:    */     //   233: ifnull +23 -> 256
/* 303:    */     //   236: aload 7
/* 304:    */     //   238: invokevirtual 20	java/io/Writer:close	()V
/* 305:    */     //   241: goto +72 -> 313
/* 306:    */     //   244: astore 9
/* 307:    */     //   246: aload 8
/* 308:    */     //   248: aload 9
/* 309:    */     //   250: invokevirtual 22	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 310:    */     //   253: goto +60 -> 313
/* 311:    */     //   256: aload 7
/* 312:    */     //   258: invokevirtual 20	java/io/Writer:close	()V
/* 313:    */     //   261: goto +52 -> 313
/* 314:    */     //   264: astore 9
/* 315:    */     //   266: aload 9
/* 316:    */     //   268: astore 8
/* 317:    */     //   270: aload 9
/* 318:    */     //   272: athrow
/* 319:    */     //   273: astore 11
/* 320:    */     //   275: aload 7
/* 321:    */     //   277: ifnull +33 -> 310
/* 322:    */     //   280: aload 8
/* 323:    */     //   282: ifnull +23 -> 305
/* 324:    */     //   285: aload 7
/* 325:    */     //   287: invokevirtual 20	java/io/Writer:close	()V
/* 326:    */     //   290: goto +20 -> 310
/* 327:    */     //   293: astore 12
/* 328:    */     //   295: aload 8
/* 329:    */     //   297: aload 12
/* 330:    */     //   299: invokevirtual 22	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 331:    */     //   302: goto +8 -> 310
/* 332:    */     //   305: aload 7
/* 333:    */     //   307: invokevirtual 20	java/io/Writer:close	()V
/* 334:    */     //   310: aload 11
/* 335:    */     //   312: athrow
/* 336:    */     //   313: return
/* 337:    */     //   314: getstatic 26	nxt/http/APIServlet:apiRequestHandlers	Ljava/util/Map;
/* 338:    */     //   317: aload 6
/* 339:    */     //   319: invokeinterface 27 2 0
/* 340:    */     //   324: checkcast 28	nxt/http/APIServlet$APIRequestHandler
/* 341:    */     //   327: astore 7
/* 342:    */     //   329: aload 7
/* 343:    */     //   331: ifnonnull +122 -> 453
/* 344:    */     //   334: getstatic 25	nxt/http/JSONResponses:ERROR_INCORRECT_REQUEST	Lorg/json/simple/JSONStreamAware;
/* 345:    */     //   337: astore_3
/* 346:    */     //   338: aload_2
/* 347:    */     //   339: ldc 16
/* 348:    */     //   341: invokeinterface 17 2 0
/* 349:    */     //   346: aload_2
/* 350:    */     //   347: invokeinterface 18 1 0
/* 351:    */     //   352: astore 8
/* 352:    */     //   354: aconst_null
/* 353:    */     //   355: astore 9
/* 354:    */     //   357: aload_3
/* 355:    */     //   358: aload 8
/* 356:    */     //   360: invokeinterface 19 2 0
/* 357:    */     //   365: aload 8
/* 358:    */     //   367: ifnull +85 -> 452
/* 359:    */     //   370: aload 9
/* 360:    */     //   372: ifnull +23 -> 395
/* 361:    */     //   375: aload 8
/* 362:    */     //   377: invokevirtual 20	java/io/Writer:close	()V
/* 363:    */     //   380: goto +72 -> 452
/* 364:    */     //   383: astore 10
/* 365:    */     //   385: aload 9
/* 366:    */     //   387: aload 10
/* 367:    */     //   389: invokevirtual 22	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 368:    */     //   392: goto +60 -> 452
/* 369:    */     //   395: aload 8
/* 370:    */     //   397: invokevirtual 20	java/io/Writer:close	()V
/* 371:    */     //   400: goto +52 -> 452
/* 372:    */     //   403: astore 10
/* 373:    */     //   405: aload 10
/* 374:    */     //   407: astore 9
/* 375:    */     //   409: aload 10
/* 376:    */     //   411: athrow
/* 377:    */     //   412: astore 13
/* 378:    */     //   414: aload 8
/* 379:    */     //   416: ifnull +33 -> 449
/* 380:    */     //   419: aload 9
/* 381:    */     //   421: ifnull +23 -> 444
/* 382:    */     //   424: aload 8
/* 383:    */     //   426: invokevirtual 20	java/io/Writer:close	()V
/* 384:    */     //   429: goto +20 -> 449
/* 385:    */     //   432: astore 14
/* 386:    */     //   434: aload 9
/* 387:    */     //   436: aload 14
/* 388:    */     //   438: invokevirtual 22	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 389:    */     //   441: goto +8 -> 449
/* 390:    */     //   444: aload 8
/* 391:    */     //   446: invokevirtual 20	java/io/Writer:close	()V
/* 392:    */     //   449: aload 13
/* 393:    */     //   451: athrow
/* 394:    */     //   452: return
/* 395:    */     //   453: getstatic 29	nxt/http/APIServlet:enforcePost	Z
/* 396:    */     //   456: ifeq +144 -> 600
/* 397:    */     //   459: aload 7
/* 398:    */     //   461: invokevirtual 30	nxt/http/APIServlet$APIRequestHandler:requirePost	()Z
/* 399:    */     //   464: ifeq +136 -> 600
/* 400:    */     //   467: ldc 31
/* 401:    */     //   469: aload_1
/* 402:    */     //   470: invokeinterface 32 1 0
/* 403:    */     //   475: invokevirtual 33	java/lang/String:equals	(Ljava/lang/Object;)Z
/* 404:    */     //   478: ifne +122 -> 600
/* 405:    */     //   481: getstatic 34	nxt/http/JSONResponses:POST_REQUIRED	Lorg/json/simple/JSONStreamAware;
/* 406:    */     //   484: astore_3
/* 407:    */     //   485: aload_2
/* 408:    */     //   486: ldc 16
/* 409:    */     //   488: invokeinterface 17 2 0
/* 410:    */     //   493: aload_2
/* 411:    */     //   494: invokeinterface 18 1 0
/* 412:    */     //   499: astore 8
/* 413:    */     //   501: aconst_null
/* 414:    */     //   502: astore 9
/* 415:    */     //   504: aload_3
/* 416:    */     //   505: aload 8
/* 417:    */     //   507: invokeinterface 19 2 0
/* 418:    */     //   512: aload 8
/* 419:    */     //   514: ifnull +85 -> 599
/* 420:    */     //   517: aload 9
/* 421:    */     //   519: ifnull +23 -> 542
/* 422:    */     //   522: aload 8
/* 423:    */     //   524: invokevirtual 20	java/io/Writer:close	()V
/* 424:    */     //   527: goto +72 -> 599
/* 425:    */     //   530: astore 10
/* 426:    */     //   532: aload 9
/* 427:    */     //   534: aload 10
/* 428:    */     //   536: invokevirtual 22	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 429:    */     //   539: goto +60 -> 599
/* 430:    */     //   542: aload 8
/* 431:    */     //   544: invokevirtual 20	java/io/Writer:close	()V
/* 432:    */     //   547: goto +52 -> 599
/* 433:    */     //   550: astore 10
/* 434:    */     //   552: aload 10
/* 435:    */     //   554: astore 9
/* 436:    */     //   556: aload 10
/* 437:    */     //   558: athrow
/* 438:    */     //   559: astore 15
/* 439:    */     //   561: aload 8
/* 440:    */     //   563: ifnull +33 -> 596
/* 441:    */     //   566: aload 9
/* 442:    */     //   568: ifnull +23 -> 591
/* 443:    */     //   571: aload 8
/* 444:    */     //   573: invokevirtual 20	java/io/Writer:close	()V
/* 445:    */     //   576: goto +20 -> 596
/* 446:    */     //   579: astore 16
/* 447:    */     //   581: aload 9
/* 448:    */     //   583: aload 16
/* 449:    */     //   585: invokevirtual 22	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 450:    */     //   588: goto +8 -> 596
/* 451:    */     //   591: aload 8
/* 452:    */     //   593: invokevirtual 20	java/io/Writer:close	()V
/* 453:    */     //   596: aload 15
/* 454:    */     //   598: athrow
/* 455:    */     //   599: return
/* 456:    */     //   600: aload 7
/* 457:    */     //   602: invokevirtual 35	nxt/http/APIServlet$APIRequestHandler:startDbTransaction	()Z
/* 458:    */     //   605: ifeq +7 -> 612
/* 459:    */     //   608: invokestatic 36	nxt/db/Db:beginTransaction	()Ljava/sql/Connection;
/* 460:    */     //   611: pop
/* 461:    */     //   612: aload 7
/* 462:    */     //   614: aload_1
/* 463:    */     //   615: invokevirtual 37	nxt/http/APIServlet$APIRequestHandler:processRequest	(Ljavax/servlet/http/HttpServletRequest;)Lorg/json/simple/JSONStreamAware;
/* 464:    */     //   618: astore_3
/* 465:    */     //   619: aload 7
/* 466:    */     //   621: invokevirtual 35	nxt/http/APIServlet$APIRequestHandler:startDbTransaction	()Z
/* 467:    */     //   624: ifeq +74 -> 698
/* 468:    */     //   627: invokestatic 38	nxt/db/Db:endTransaction	()V
/* 469:    */     //   630: goto +68 -> 698
/* 470:    */     //   633: astore 8
/* 471:    */     //   635: aload 8
/* 472:    */     //   637: invokevirtual 40	nxt/http/ParameterException:getErrorResponse	()Lorg/json/simple/JSONStreamAware;
/* 473:    */     //   640: astore_3
/* 474:    */     //   641: aload 7
/* 475:    */     //   643: invokevirtual 35	nxt/http/APIServlet$APIRequestHandler:startDbTransaction	()Z
/* 476:    */     //   646: ifeq +52 -> 698
/* 477:    */     //   649: invokestatic 38	nxt/db/Db:endTransaction	()V
/* 478:    */     //   652: goto +46 -> 698
/* 479:    */     //   655: astore 8
/* 480:    */     //   657: ldc 43
/* 481:    */     //   659: aload 8
/* 482:    */     //   661: invokestatic 44	nxt/util/Logger:logDebugMessage	(Ljava/lang/String;Ljava/lang/Exception;)V
/* 483:    */     //   664: getstatic 25	nxt/http/JSONResponses:ERROR_INCORRECT_REQUEST	Lorg/json/simple/JSONStreamAware;
/* 484:    */     //   667: astore_3
/* 485:    */     //   668: aload 7
/* 486:    */     //   670: invokevirtual 35	nxt/http/APIServlet$APIRequestHandler:startDbTransaction	()Z
/* 487:    */     //   673: ifeq +25 -> 698
/* 488:    */     //   676: invokestatic 38	nxt/db/Db:endTransaction	()V
/* 489:    */     //   679: goto +19 -> 698
/* 490:    */     //   682: astore 17
/* 491:    */     //   684: aload 7
/* 492:    */     //   686: invokevirtual 35	nxt/http/APIServlet$APIRequestHandler:startDbTransaction	()Z
/* 493:    */     //   689: ifeq +6 -> 695
/* 494:    */     //   692: invokestatic 38	nxt/db/Db:endTransaction	()V
/* 495:    */     //   695: aload 17
/* 496:    */     //   697: athrow
/* 497:    */     //   698: aload_3
/* 498:    */     //   699: instanceof 45
/* 499:    */     //   702: ifeq +22 -> 724
/* 500:    */     //   705: aload_3
/* 501:    */     //   706: checkcast 45	org/json/simple/JSONObject
/* 502:    */     //   709: ldc 46
/* 503:    */     //   711: invokestatic 11	java/lang/System:currentTimeMillis	()J
/* 504:    */     //   714: lload 4
/* 505:    */     //   716: lsub
/* 506:    */     //   717: invokestatic 47	java/lang/Long:valueOf	(J)Ljava/lang/Long;
/* 507:    */     //   720: invokevirtual 48	org/json/simple/JSONObject:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
/* 508:    */     //   723: pop
/* 509:    */     //   724: aload_2
/* 510:    */     //   725: ldc 16
/* 511:    */     //   727: invokeinterface 17 2 0
/* 512:    */     //   732: aload_2
/* 513:    */     //   733: invokeinterface 18 1 0
/* 514:    */     //   738: astore 4
/* 515:    */     //   740: aconst_null
/* 516:    */     //   741: astore 5
/* 517:    */     //   743: aload_3
/* 518:    */     //   744: aload 4
/* 519:    */     //   746: invokeinterface 19 2 0
/* 520:    */     //   751: aload 4
/* 521:    */     //   753: ifnull +85 -> 838
/* 522:    */     //   756: aload 5
/* 523:    */     //   758: ifnull +23 -> 781
/* 524:    */     //   761: aload 4
/* 525:    */     //   763: invokevirtual 20	java/io/Writer:close	()V
/* 526:    */     //   766: goto +72 -> 838
/* 527:    */     //   769: astore 6
/* 528:    */     //   771: aload 5
/* 529:    */     //   773: aload 6
/* 530:    */     //   775: invokevirtual 22	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 531:    */     //   778: goto +60 -> 838
/* 532:    */     //   781: aload 4
/* 533:    */     //   783: invokevirtual 20	java/io/Writer:close	()V
/* 534:    */     //   786: goto +52 -> 838
/* 535:    */     //   789: astore 6
/* 536:    */     //   791: aload 6
/* 537:    */     //   793: astore 5
/* 538:    */     //   795: aload 6
/* 539:    */     //   797: athrow
/* 540:    */     //   798: astore 18
/* 541:    */     //   800: aload 4
/* 542:    */     //   802: ifnull +33 -> 835
/* 543:    */     //   805: aload 5
/* 544:    */     //   807: ifnull +23 -> 830
/* 545:    */     //   810: aload 4
/* 546:    */     //   812: invokevirtual 20	java/io/Writer:close	()V
/* 547:    */     //   815: goto +20 -> 835
/* 548:    */     //   818: astore 19
/* 549:    */     //   820: aload 5
/* 550:    */     //   822: aload 19
/* 551:    */     //   824: invokevirtual 22	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 552:    */     //   827: goto +8 -> 835
/* 553:    */     //   830: aload 4
/* 554:    */     //   832: invokevirtual 20	java/io/Writer:close	()V
/* 555:    */     //   835: aload 18
/* 556:    */     //   837: athrow
/* 557:    */     //   838: goto +122 -> 960
/* 558:    */     //   841: astore 20
/* 559:    */     //   843: aload_2
/* 560:    */     //   844: ldc 16
/* 561:    */     //   846: invokeinterface 17 2 0
/* 562:    */     //   851: aload_2
/* 563:    */     //   852: invokeinterface 18 1 0
/* 564:    */     //   857: astore 21
/* 565:    */     //   859: aconst_null
/* 566:    */     //   860: astore 22
/* 567:    */     //   862: aload_3
/* 568:    */     //   863: aload 21
/* 569:    */     //   865: invokeinterface 19 2 0
/* 570:    */     //   870: aload 21
/* 571:    */     //   872: ifnull +85 -> 957
/* 572:    */     //   875: aload 22
/* 573:    */     //   877: ifnull +23 -> 900
/* 574:    */     //   880: aload 21
/* 575:    */     //   882: invokevirtual 20	java/io/Writer:close	()V
/* 576:    */     //   885: goto +72 -> 957
/* 577:    */     //   888: astore 23
/* 578:    */     //   890: aload 22
/* 579:    */     //   892: aload 23
/* 580:    */     //   894: invokevirtual 22	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 581:    */     //   897: goto +60 -> 957
/* 582:    */     //   900: aload 21
/* 583:    */     //   902: invokevirtual 20	java/io/Writer:close	()V
/* 584:    */     //   905: goto +52 -> 957
/* 585:    */     //   908: astore 23
/* 586:    */     //   910: aload 23
/* 587:    */     //   912: astore 22
/* 588:    */     //   914: aload 23
/* 589:    */     //   916: athrow
/* 590:    */     //   917: astore 24
/* 591:    */     //   919: aload 21
/* 592:    */     //   921: ifnull +33 -> 954
/* 593:    */     //   924: aload 22
/* 594:    */     //   926: ifnull +23 -> 949
/* 595:    */     //   929: aload 21
/* 596:    */     //   931: invokevirtual 20	java/io/Writer:close	()V
/* 597:    */     //   934: goto +20 -> 954
/* 598:    */     //   937: astore 25
/* 599:    */     //   939: aload 22
/* 600:    */     //   941: aload 25
/* 601:    */     //   943: invokevirtual 22	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 602:    */     //   946: goto +8 -> 954
/* 603:    */     //   949: aload 21
/* 604:    */     //   951: invokevirtual 20	java/io/Writer:close	()V
/* 605:    */     //   954: aload 24
/* 606:    */     //   956: athrow
/* 607:    */     //   957: aload 20
/* 608:    */     //   959: athrow
/* 609:    */     //   960: return
/* 610:    */     // Line number table:
/* 611:    */     //   Java source line #207	-> byte code offset #0
/* 612:    */     //   Java source line #208	-> byte code offset #10
/* 613:    */     //   Java source line #209	-> byte code offset #20
/* 614:    */     //   Java source line #211	-> byte code offset #29
/* 615:    */     //   Java source line #215	-> byte code offset #33
/* 616:    */     //   Java source line #217	-> byte code offset #38
/* 617:    */     //   Java source line #218	-> byte code offset #61
/* 618:    */     //   Java source line #260	-> byte code offset #65
/* 619:    */     //   Java source line #261	-> byte code offset #73
/* 620:    */     //   Java source line #262	-> byte code offset #84
/* 621:    */     //   Java source line #263	-> byte code offset #92
/* 622:    */     //   Java source line #261	-> byte code offset #130
/* 623:    */     //   Java source line #263	-> byte code offset #139
/* 624:    */     //   Java source line #222	-> byte code offset #180
/* 625:    */     //   Java source line #223	-> byte code offset #190
/* 626:    */     //   Java source line #224	-> byte code offset #195
/* 627:    */     //   Java source line #260	-> byte code offset #199
/* 628:    */     //   Java source line #261	-> byte code offset #207
/* 629:    */     //   Java source line #262	-> byte code offset #218
/* 630:    */     //   Java source line #263	-> byte code offset #226
/* 631:    */     //   Java source line #261	-> byte code offset #264
/* 632:    */     //   Java source line #263	-> byte code offset #273
/* 633:    */     //   Java source line #228	-> byte code offset #314
/* 634:    */     //   Java source line #229	-> byte code offset #329
/* 635:    */     //   Java source line #230	-> byte code offset #334
/* 636:    */     //   Java source line #260	-> byte code offset #338
/* 637:    */     //   Java source line #261	-> byte code offset #346
/* 638:    */     //   Java source line #262	-> byte code offset #357
/* 639:    */     //   Java source line #263	-> byte code offset #365
/* 640:    */     //   Java source line #261	-> byte code offset #403
/* 641:    */     //   Java source line #263	-> byte code offset #412
/* 642:    */     //   Java source line #234	-> byte code offset #453
/* 643:    */     //   Java source line #235	-> byte code offset #481
/* 644:    */     //   Java source line #260	-> byte code offset #485
/* 645:    */     //   Java source line #261	-> byte code offset #493
/* 646:    */     //   Java source line #262	-> byte code offset #504
/* 647:    */     //   Java source line #263	-> byte code offset #512
/* 648:    */     //   Java source line #261	-> byte code offset #550
/* 649:    */     //   Java source line #263	-> byte code offset #559
/* 650:    */     //   Java source line #240	-> byte code offset #600
/* 651:    */     //   Java source line #241	-> byte code offset #608
/* 652:    */     //   Java source line #243	-> byte code offset #612
/* 653:    */     //   Java source line #250	-> byte code offset #619
/* 654:    */     //   Java source line #251	-> byte code offset #627
/* 655:    */     //   Java source line #244	-> byte code offset #633
/* 656:    */     //   Java source line #245	-> byte code offset #635
/* 657:    */     //   Java source line #250	-> byte code offset #641
/* 658:    */     //   Java source line #251	-> byte code offset #649
/* 659:    */     //   Java source line #246	-> byte code offset #655
/* 660:    */     //   Java source line #247	-> byte code offset #657
/* 661:    */     //   Java source line #248	-> byte code offset #664
/* 662:    */     //   Java source line #250	-> byte code offset #668
/* 663:    */     //   Java source line #251	-> byte code offset #676
/* 664:    */     //   Java source line #250	-> byte code offset #682
/* 665:    */     //   Java source line #251	-> byte code offset #692
/* 666:    */     //   Java source line #255	-> byte code offset #698
/* 667:    */     //   Java source line #256	-> byte code offset #705
/* 668:    */     //   Java source line #260	-> byte code offset #724
/* 669:    */     //   Java source line #261	-> byte code offset #732
/* 670:    */     //   Java source line #262	-> byte code offset #743
/* 671:    */     //   Java source line #263	-> byte code offset #751
/* 672:    */     //   Java source line #261	-> byte code offset #789
/* 673:    */     //   Java source line #263	-> byte code offset #798
/* 674:    */     //   Java source line #264	-> byte code offset #838
/* 675:    */     //   Java source line #260	-> byte code offset #841
/* 676:    */     //   Java source line #261	-> byte code offset #851
/* 677:    */     //   Java source line #262	-> byte code offset #862
/* 678:    */     //   Java source line #263	-> byte code offset #870
/* 679:    */     //   Java source line #261	-> byte code offset #908
/* 680:    */     //   Java source line #263	-> byte code offset #917
/* 681:    */     //   Java source line #266	-> byte code offset #960
/* 682:    */     // Local variable table:
/* 683:    */     //   start	length	slot	name	signature
/* 684:    */     //   0	961	0	this	APIServlet
/* 685:    */     //   0	961	1	paramHttpServletRequest	HttpServletRequest
/* 686:    */     //   0	961	2	paramHttpServletResponse	HttpServletResponse
/* 687:    */     //   32	831	3	localJSONStreamAware	JSONStreamAware
/* 688:    */     //   36	679	4	l	long
/* 689:    */     //   738	93	4	localPrintWriter1	java.io.PrintWriter
/* 690:    */     //   741	80	5	localThrowable1	Throwable
/* 691:    */     //   79	239	6	localObject1	Object
/* 692:    */     //   769	5	6	localThrowable2	Throwable
/* 693:    */     //   789	7	6	localThrowable3	Throwable
/* 694:    */     //   82	603	7	localObject2	Object
/* 695:    */     //   110	5	8	localThrowable4	Throwable
/* 696:    */     //   130	7	8	localThrowable5	Throwable
/* 697:    */     //   216	376	8	localObject3	Object
/* 698:    */     //   633	3	8	localParameterException	ParameterException
/* 699:    */     //   655	5	8	localNxtException	NxtException
/* 700:    */     //   139	38	9	localObject4	Object
/* 701:    */     //   244	5	9	localThrowable6	Throwable
/* 702:    */     //   264	7	9	localThrowable7	Throwable
/* 703:    */     //   355	227	9	localObject5	Object
/* 704:    */     //   159	5	10	localThrowable8	Throwable
/* 705:    */     //   383	5	10	localThrowable9	Throwable
/* 706:    */     //   403	7	10	localThrowable10	Throwable
/* 707:    */     //   530	5	10	localThrowable11	Throwable
/* 708:    */     //   550	7	10	localThrowable12	Throwable
/* 709:    */     //   273	38	11	localObject6	Object
/* 710:    */     //   293	5	12	localThrowable13	Throwable
/* 711:    */     //   412	38	13	localObject7	Object
/* 712:    */     //   432	5	14	localThrowable14	Throwable
/* 713:    */     //   559	38	15	localObject8	Object
/* 714:    */     //   579	5	16	localThrowable15	Throwable
/* 715:    */     //   682	14	17	localObject9	Object
/* 716:    */     //   798	38	18	localObject10	Object
/* 717:    */     //   818	5	19	localThrowable16	Throwable
/* 718:    */     //   841	117	20	localObject11	Object
/* 719:    */     //   857	93	21	localPrintWriter2	java.io.PrintWriter
/* 720:    */     //   860	80	22	localObject12	Object
/* 721:    */     //   888	5	23	localThrowable17	Throwable
/* 722:    */     //   908	7	23	localThrowable18	Throwable
/* 723:    */     //   917	38	24	localObject13	Object
/* 724:    */     //   937	5	25	localThrowable19	Throwable
/* 725:    */     // Exception table:
/* 726:    */     //   from	to	target	type
/* 727:    */     //   102	107	110	java/lang/Throwable
/* 728:    */     //   84	92	130	java/lang/Throwable
/* 729:    */     //   84	92	139	finally
/* 730:    */     //   130	141	139	finally
/* 731:    */     //   151	156	159	java/lang/Throwable
/* 732:    */     //   236	241	244	java/lang/Throwable
/* 733:    */     //   218	226	264	java/lang/Throwable
/* 734:    */     //   218	226	273	finally
/* 735:    */     //   264	275	273	finally
/* 736:    */     //   285	290	293	java/lang/Throwable
/* 737:    */     //   375	380	383	java/lang/Throwable
/* 738:    */     //   357	365	403	java/lang/Throwable
/* 739:    */     //   357	365	412	finally
/* 740:    */     //   403	414	412	finally
/* 741:    */     //   424	429	432	java/lang/Throwable
/* 742:    */     //   522	527	530	java/lang/Throwable
/* 743:    */     //   504	512	550	java/lang/Throwable
/* 744:    */     //   504	512	559	finally
/* 745:    */     //   550	561	559	finally
/* 746:    */     //   571	576	579	java/lang/Throwable
/* 747:    */     //   600	619	633	nxt/http/ParameterException
/* 748:    */     //   600	619	655	nxt/NxtException
/* 749:    */     //   600	619	655	java/lang/RuntimeException
/* 750:    */     //   600	619	682	finally
/* 751:    */     //   633	641	682	finally
/* 752:    */     //   655	668	682	finally
/* 753:    */     //   682	684	682	finally
/* 754:    */     //   761	766	769	java/lang/Throwable
/* 755:    */     //   743	751	789	java/lang/Throwable
/* 756:    */     //   743	751	798	finally
/* 757:    */     //   789	800	798	finally
/* 758:    */     //   810	815	818	java/lang/Throwable
/* 759:    */     //   33	65	841	finally
/* 760:    */     //   180	199	841	finally
/* 761:    */     //   314	338	841	finally
/* 762:    */     //   453	485	841	finally
/* 763:    */     //   600	724	841	finally
/* 764:    */     //   841	843	841	finally
/* 765:    */     //   880	885	888	java/lang/Throwable
/* 766:    */     //   862	870	908	java/lang/Throwable
/* 767:    */     //   862	870	917	finally
/* 768:    */     //   908	919	917	finally
/* 769:    */     //   929	934	937	java/lang/Throwable
/* 770:    */   }
/* 771:    */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.http.APIServlet
 * JD-Core Version:    0.7.1
 */