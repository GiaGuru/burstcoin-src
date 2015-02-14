/*   1:    */ package nxt;
/*   2:    */ 
/*   3:    */ import java.io.BufferedWriter;
/*   4:    */ import java.io.FileOutputStream;
/*   5:    */ import java.io.IOException;
/*   6:    */ import java.io.OutputStreamWriter;
/*   7:    */ import java.io.PrintWriter;
/*   8:    */ import java.math.BigInteger;
/*   9:    */ import java.util.Collections;
/*  10:    */ import java.util.HashMap;
/*  11:    */ import java.util.HashSet;
/*  12:    */ import java.util.Iterator;
/*  13:    */ import java.util.List;
/*  14:    */ import java.util.Map;
/*  15:    */ import java.util.Set;
/*  16:    */ import nxt.db.DbIterator;
/*  17:    */ import nxt.util.Convert;
/*  18:    */ import nxt.util.Listener;
/*  19:    */ import nxt.util.Logger;
/*  20:    */ 
/*  21:    */ public final class DebugTrace
/*  22:    */ {
/*  23: 23 */   static final String QUOTE = Nxt.getStringProperty("nxt.debugTraceQuote", "");
/*  24: 24 */   static final String SEPARATOR = Nxt.getStringProperty("nxt.debugTraceSeparator", "\t");
/*  25: 25 */   static final boolean LOG_UNCONFIRMED = Nxt.getBooleanProperty("nxt.debugLogUnconfirmed").booleanValue();
/*  26:    */   
/*  27:    */   static void init()
/*  28:    */   {
/*  29: 28 */     List localList = Nxt.getStringListProperty("nxt.debugTraceAccounts");
/*  30: 29 */     String str1 = Nxt.getStringProperty("nxt.debugTraceLog");
/*  31: 30 */     if ((localList.isEmpty()) || (str1 == null)) {
/*  32: 31 */       return;
/*  33:    */     }
/*  34: 33 */     HashSet localHashSet = new HashSet();
/*  35: 34 */     for (Object localObject = localList.iterator(); ((Iterator)localObject).hasNext();)
/*  36:    */     {
/*  37: 34 */       String str2 = (String)((Iterator)localObject).next();
/*  38: 35 */       if ("*".equals(str2))
/*  39:    */       {
/*  40: 36 */         localHashSet.clear();
/*  41: 37 */         break;
/*  42:    */       }
/*  43: 39 */       localHashSet.add(Long.valueOf(Convert.parseUnsignedLong(str2)));
/*  44:    */     }
/*  45: 41 */     localObject = addDebugTrace(localHashSet, str1);
/*  46: 42 */     Nxt.getBlockchainProcessor().addListener(new Listener()
/*  47:    */     {
/*  48:    */       public void notify(Block paramAnonymousBlock)
/*  49:    */       {
/*  50: 45 */         this.val$debugTrace.resetLog();
/*  51:    */       }
/*  52: 45 */     }, BlockchainProcessor.Event.RESCAN_BEGIN);
/*  53:    */     
/*  54:    */ 
/*  55: 48 */     Logger.logDebugMessage("Debug tracing of " + (localList.contains("*") ? "ALL" : String.valueOf(localHashSet.size())) + " accounts enabled");
/*  56:    */   }
/*  57:    */   
/*  58:    */   public static DebugTrace addDebugTrace(Set<Long> paramSet, String paramString)
/*  59:    */   {
/*  60: 53 */     DebugTrace localDebugTrace = new DebugTrace(paramSet, paramString);
/*  61: 54 */     Trade.addListener(new Listener()
/*  62:    */     {
/*  63:    */       public void notify(Trade paramAnonymousTrade)
/*  64:    */       {
/*  65: 57 */         this.val$debugTrace.trace(paramAnonymousTrade);
/*  66:    */       }
/*  67: 57 */     }, Trade.Event.TRADE);
/*  68:    */     
/*  69:    */ 
/*  70: 60 */     Account.addListener(new Listener()
/*  71:    */     {
/*  72:    */       public void notify(Account paramAnonymousAccount)
/*  73:    */       {
/*  74: 63 */         this.val$debugTrace.trace(paramAnonymousAccount, false);
/*  75:    */       }
/*  76: 63 */     }, Account.Event.BALANCE);
/*  77: 66 */     if (LOG_UNCONFIRMED) {
/*  78: 67 */       Account.addListener(new Listener()
/*  79:    */       {
/*  80:    */         public void notify(Account paramAnonymousAccount)
/*  81:    */         {
/*  82: 70 */           this.val$debugTrace.trace(paramAnonymousAccount, true);
/*  83:    */         }
/*  84: 70 */       }, Account.Event.UNCONFIRMED_BALANCE);
/*  85:    */     }
/*  86: 74 */     Account.addAssetListener(new Listener()
/*  87:    */     {
/*  88:    */       public void notify(Account.AccountAsset paramAnonymousAccountAsset)
/*  89:    */       {
/*  90: 77 */         this.val$debugTrace.trace(paramAnonymousAccountAsset, false);
/*  91:    */       }
/*  92: 77 */     }, Account.Event.ASSET_BALANCE);
/*  93: 80 */     if (LOG_UNCONFIRMED) {
/*  94: 81 */       Account.addAssetListener(new Listener()
/*  95:    */       {
/*  96:    */         public void notify(Account.AccountAsset paramAnonymousAccountAsset)
/*  97:    */         {
/*  98: 84 */           this.val$debugTrace.trace(paramAnonymousAccountAsset, true);
/*  99:    */         }
/* 100: 84 */       }, Account.Event.UNCONFIRMED_ASSET_BALANCE);
/* 101:    */     }
/* 102: 88 */     Account.addLeaseListener(new Listener()
/* 103:    */     {
/* 104:    */       public void notify(Account.AccountLease paramAnonymousAccountLease)
/* 105:    */       {
/* 106: 91 */         this.val$debugTrace.trace(paramAnonymousAccountLease, true);
/* 107:    */       }
/* 108: 91 */     }, Account.Event.LEASE_STARTED);
/* 109:    */     
/* 110:    */ 
/* 111: 94 */     Account.addLeaseListener(new Listener()
/* 112:    */     {
/* 113:    */       public void notify(Account.AccountLease paramAnonymousAccountLease)
/* 114:    */       {
/* 115: 97 */         this.val$debugTrace.trace(paramAnonymousAccountLease, false);
/* 116:    */       }
/* 117: 97 */     }, Account.Event.LEASE_ENDED);
/* 118:    */     
/* 119:    */ 
/* 120:100 */     Nxt.getBlockchainProcessor().addListener(new Listener()
/* 121:    */     {
/* 122:    */       public void notify(Block paramAnonymousBlock)
/* 123:    */       {
/* 124:103 */         this.val$debugTrace.traceBeforeAccept(paramAnonymousBlock);
/* 125:    */       }
/* 126:103 */     }, BlockchainProcessor.Event.BEFORE_BLOCK_ACCEPT);
/* 127:    */     
/* 128:    */ 
/* 129:106 */     Nxt.getBlockchainProcessor().addListener(new Listener()
/* 130:    */     {
/* 131:    */       public void notify(Block paramAnonymousBlock)
/* 132:    */       {
/* 133:109 */         this.val$debugTrace.trace(paramAnonymousBlock);
/* 134:    */       }
/* 135:109 */     }, BlockchainProcessor.Event.BEFORE_BLOCK_APPLY);
/* 136:    */     
/* 137:    */ 
/* 138:112 */     return localDebugTrace;
/* 139:    */   }
/* 140:    */   
/* 141:115 */   private static final String[] columns = { "height", "event", "account", "asset", "balance", "unconfirmed balance", "asset balance", "unconfirmed asset balance", "transaction amount", "transaction fee", "generation fee", "effective balance", "order", "order price", "order quantity", "order cost", "trade price", "trade quantity", "trade cost", "asset quantity", "transaction", "lessee", "lessor guaranteed balance", "purchase", "purchase price", "purchase quantity", "purchase cost", "discount", "refund", "sender", "recipient", "block", "timestamp" };
/* 142:124 */   private static final Map<String, String> headers = new HashMap();
/* 143:    */   private final Set<Long> accountIds;
/* 144:    */   private final String logName;
/* 145:    */   private PrintWriter log;
/* 146:    */   
/* 147:    */   static
/* 148:    */   {
/* 149:126 */     for (String str : columns) {
/* 150:127 */       headers.put(str, str);
/* 151:    */     }
/* 152:    */   }
/* 153:    */   
/* 154:    */   private DebugTrace(Set<Long> paramSet, String paramString)
/* 155:    */   {
/* 156:136 */     this.accountIds = paramSet;
/* 157:137 */     this.logName = paramString;
/* 158:138 */     resetLog();
/* 159:    */   }
/* 160:    */   
/* 161:    */   void resetLog()
/* 162:    */   {
/* 163:142 */     if (this.log != null) {
/* 164:143 */       this.log.close();
/* 165:    */     }
/* 166:    */     try
/* 167:    */     {
/* 168:146 */       this.log = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(this.logName))), true);
/* 169:    */     }
/* 170:    */     catch (IOException localIOException)
/* 171:    */     {
/* 172:148 */       Logger.logDebugMessage("Debug tracing to " + this.logName + " not possible", localIOException);
/* 173:149 */       throw new RuntimeException(localIOException);
/* 174:    */     }
/* 175:151 */     log(headers);
/* 176:    */   }
/* 177:    */   
/* 178:    */   private boolean include(long paramLong)
/* 179:    */   {
/* 180:155 */     return (paramLong != 0L) && ((this.accountIds.isEmpty()) || (this.accountIds.contains(Long.valueOf(paramLong))));
/* 181:    */   }
/* 182:    */   
/* 183:    */   private boolean include(Attachment paramAttachment)
/* 184:    */   {
/* 185:    */     long l;
/* 186:159 */     if ((paramAttachment instanceof Attachment.DigitalGoodsPurchase))
/* 187:    */     {
/* 188:160 */       l = DigitalGoodsStore.getGoods(((Attachment.DigitalGoodsPurchase)paramAttachment).getGoodsId()).getSellerId();
/* 189:161 */       return include(l);
/* 190:    */     }
/* 191:162 */     if ((paramAttachment instanceof Attachment.DigitalGoodsDelivery))
/* 192:    */     {
/* 193:163 */       l = DigitalGoodsStore.getPurchase(((Attachment.DigitalGoodsDelivery)paramAttachment).getPurchaseId()).getBuyerId();
/* 194:164 */       return include(l);
/* 195:    */     }
/* 196:165 */     if ((paramAttachment instanceof Attachment.DigitalGoodsRefund))
/* 197:    */     {
/* 198:166 */       l = DigitalGoodsStore.getPurchase(((Attachment.DigitalGoodsRefund)paramAttachment).getPurchaseId()).getBuyerId();
/* 199:167 */       return include(l);
/* 200:    */     }
/* 201:169 */     return false;
/* 202:    */   }
/* 203:    */   
/* 204:    */   private void trace(Trade paramTrade)
/* 205:    */   {
/* 206:174 */     long l1 = Order.Ask.getAskOrder(paramTrade.getAskOrderId()).getAccountId();
/* 207:175 */     long l2 = Order.Bid.getBidOrder(paramTrade.getBidOrderId()).getAccountId();
/* 208:176 */     if (include(l1)) {
/* 209:177 */       log(getValues(l1, paramTrade, true));
/* 210:    */     }
/* 211:179 */     if (include(l2)) {
/* 212:180 */       log(getValues(l2, paramTrade, false));
/* 213:    */     }
/* 214:    */   }
/* 215:    */   
/* 216:    */   private void trace(Account paramAccount, boolean paramBoolean)
/* 217:    */   {
/* 218:185 */     if (include(paramAccount.getId())) {
/* 219:186 */       log(getValues(paramAccount.getId(), paramBoolean));
/* 220:    */     }
/* 221:    */   }
/* 222:    */   
/* 223:    */   private void trace(Account.AccountAsset paramAccountAsset, boolean paramBoolean)
/* 224:    */   {
/* 225:191 */     if (!include(paramAccountAsset.getAccountId())) {
/* 226:192 */       return;
/* 227:    */     }
/* 228:194 */     log(getValues(paramAccountAsset.getAccountId(), paramAccountAsset, paramBoolean));
/* 229:    */   }
/* 230:    */   
/* 231:    */   private void trace(Account.AccountLease paramAccountLease, boolean paramBoolean)
/* 232:    */   {
/* 233:198 */     if ((!include(paramAccountLease.lesseeId)) && (!include(paramAccountLease.lessorId))) {
/* 234:199 */       return;
/* 235:    */     }
/* 236:201 */     log(getValues(paramAccountLease.lessorId, paramAccountLease, paramBoolean));
/* 237:    */   }
/* 238:    */   
/* 239:    */   private void traceBeforeAccept(Block paramBlock)
/* 240:    */   {
/* 241:205 */     long l1 = paramBlock.getGeneratorId();
/* 242:206 */     if (include(l1)) {
/* 243:207 */       log(getValues(l1, paramBlock));
/* 244:    */     }
/* 245:209 */     for (Iterator localIterator = this.accountIds.iterator(); localIterator.hasNext();)
/* 246:    */     {
/* 247:209 */       long l2 = ((Long)localIterator.next()).longValue();
/* 248:210 */       Account localAccount = Account.getAccount(l2);
/* 249:211 */       if (localAccount != null)
/* 250:    */       {
/* 251:212 */         DbIterator localDbIterator = localAccount.getLessors();Object localObject1 = null;
/* 252:    */         try
/* 253:    */         {
/* 254:213 */           while (localDbIterator.hasNext()) {
/* 255:214 */             log(lessorGuaranteedBalance((Account)localDbIterator.next(), l2));
/* 256:    */           }
/* 257:    */         }
/* 258:    */         catch (Throwable localThrowable2)
/* 259:    */         {
/* 260:212 */           localObject1 = localThrowable2;throw localThrowable2;
/* 261:    */         }
/* 262:    */         finally
/* 263:    */         {
/* 264:216 */           if (localDbIterator != null) {
/* 265:216 */             if (localObject1 != null) {
/* 266:    */               try
/* 267:    */               {
/* 268:216 */                 localDbIterator.close();
/* 269:    */               }
/* 270:    */               catch (Throwable localThrowable3)
/* 271:    */               {
/* 272:216 */                 ((Throwable)localObject1).addSuppressed(localThrowable3);
/* 273:    */               }
/* 274:    */             } else {
/* 275:216 */               localDbIterator.close();
/* 276:    */             }
/* 277:    */           }
/* 278:    */         }
/* 279:    */       }
/* 280:    */     }
/* 281:    */   }
/* 282:    */   
/* 283:    */   private void trace(Block paramBlock)
/* 284:    */   {
/* 285:222 */     for (Transaction localTransaction : paramBlock.getTransactions())
/* 286:    */     {
/* 287:223 */       long l1 = localTransaction.getSenderId();
/* 288:224 */       if (include(l1))
/* 289:    */       {
/* 290:225 */         log(getValues(l1, localTransaction, false));
/* 291:226 */         log(getValues(l1, localTransaction, localTransaction.getAttachment(), false));
/* 292:    */       }
/* 293:228 */       long l2 = localTransaction.getRecipientId();
/* 294:229 */       if (include(l2))
/* 295:    */       {
/* 296:230 */         log(getValues(l2, localTransaction, true));
/* 297:231 */         log(getValues(l2, localTransaction, localTransaction.getAttachment(), true));
/* 298:    */       }
/* 299:    */       else
/* 300:    */       {
/* 301:233 */         Attachment localAttachment = localTransaction.getAttachment();
/* 302:234 */         if (include(localAttachment)) {
/* 303:235 */           log(getValues(l2, localTransaction, localTransaction.getAttachment(), true));
/* 304:    */         }
/* 305:    */       }
/* 306:    */     }
/* 307:    */   }
/* 308:    */   
/* 309:    */   private Map<String, String> lessorGuaranteedBalance(Account paramAccount, long paramLong)
/* 310:    */   {
/* 311:242 */     HashMap localHashMap = new HashMap();
/* 312:243 */     localHashMap.put("account", Convert.toUnsignedLong(paramAccount.getId()));
/* 313:244 */     localHashMap.put("lessor guaranteed balance", String.valueOf(paramAccount.getGuaranteedBalanceNQT(1440)));
/* 314:245 */     localHashMap.put("lessee", Convert.toUnsignedLong(paramLong));
/* 315:246 */     localHashMap.put("timestamp", String.valueOf(Nxt.getBlockchain().getLastBlock().getTimestamp()));
/* 316:247 */     localHashMap.put("height", String.valueOf(Nxt.getBlockchain().getHeight()));
/* 317:248 */     localHashMap.put("event", "lessor guaranteed balance");
/* 318:249 */     return localHashMap;
/* 319:    */   }
/* 320:    */   
/* 321:    */   private Map<String, String> getValues(long paramLong, boolean paramBoolean)
/* 322:    */   {
/* 323:253 */     HashMap localHashMap = new HashMap();
/* 324:254 */     localHashMap.put("account", Convert.toUnsignedLong(paramLong));
/* 325:255 */     Account localAccount = Account.getAccount(paramLong);
/* 326:256 */     localHashMap.put("balance", String.valueOf(localAccount != null ? localAccount.getBalanceNQT() : 0L));
/* 327:257 */     localHashMap.put("unconfirmed balance", String.valueOf(localAccount != null ? localAccount.getUnconfirmedBalanceNQT() : 0L));
/* 328:258 */     localHashMap.put("timestamp", String.valueOf(Nxt.getBlockchain().getLastBlock().getTimestamp()));
/* 329:259 */     localHashMap.put("height", String.valueOf(Nxt.getBlockchain().getHeight()));
/* 330:260 */     localHashMap.put("event", paramBoolean ? "unconfirmed balance" : "balance");
/* 331:261 */     return localHashMap;
/* 332:    */   }
/* 333:    */   
/* 334:    */   private Map<String, String> getValues(long paramLong, Trade paramTrade, boolean paramBoolean)
/* 335:    */   {
/* 336:265 */     Map localMap = getValues(paramLong, false);
/* 337:266 */     localMap.put("asset", Convert.toUnsignedLong(paramTrade.getAssetId()));
/* 338:267 */     localMap.put("trade quantity", String.valueOf(paramBoolean ? -paramTrade.getQuantityQNT() : paramTrade.getQuantityQNT()));
/* 339:268 */     localMap.put("trade price", String.valueOf(paramTrade.getPriceNQT()));
/* 340:269 */     long l = Convert.safeMultiply(paramTrade.getQuantityQNT(), paramTrade.getPriceNQT());
/* 341:270 */     localMap.put("trade cost", String.valueOf(paramBoolean ? l : -l));
/* 342:271 */     localMap.put("event", "trade");
/* 343:272 */     return localMap;
/* 344:    */   }
/* 345:    */   
/* 346:    */   private Map<String, String> getValues(long paramLong, Transaction paramTransaction, boolean paramBoolean)
/* 347:    */   {
/* 348:276 */     long l1 = paramTransaction.getAmountNQT();
/* 349:277 */     long l2 = paramTransaction.getFeeNQT();
/* 350:278 */     if (paramBoolean)
/* 351:    */     {
/* 352:279 */       l2 = 0L;
/* 353:    */     }
/* 354:    */     else
/* 355:    */     {
/* 356:282 */       l1 = -l1;
/* 357:283 */       l2 = -l2;
/* 358:    */     }
/* 359:285 */     if ((l2 == 0L) && (l1 == 0L)) {
/* 360:286 */       return Collections.emptyMap();
/* 361:    */     }
/* 362:288 */     Map localMap = getValues(paramLong, false);
/* 363:289 */     localMap.put("transaction amount", String.valueOf(l1));
/* 364:290 */     localMap.put("transaction fee", String.valueOf(l2));
/* 365:291 */     localMap.put("transaction", paramTransaction.getStringId());
/* 366:292 */     if (paramBoolean) {
/* 367:293 */       localMap.put("sender", Convert.toUnsignedLong(paramTransaction.getSenderId()));
/* 368:    */     } else {
/* 369:295 */       localMap.put("recipient", Convert.toUnsignedLong(paramTransaction.getRecipientId()));
/* 370:    */     }
/* 371:297 */     localMap.put("event", "transaction");
/* 372:298 */     return localMap;
/* 373:    */   }
/* 374:    */   
/* 375:    */   private Map<String, String> getValues(long paramLong, Block paramBlock)
/* 376:    */   {
/* 377:302 */     long l = paramBlock.getTotalFeeNQT();
/* 378:303 */     if (l == 0L) {
/* 379:304 */       return Collections.emptyMap();
/* 380:    */     }
/* 381:306 */     Map localMap = getValues(paramLong, false);
/* 382:307 */     localMap.put("generation fee", String.valueOf(l));
/* 383:308 */     localMap.put("block", paramBlock.getStringId());
/* 384:309 */     localMap.put("event", "block");
/* 385:310 */     localMap.put("effective balance", String.valueOf(Account.getAccount(paramLong).getEffectiveBalanceNXT()));
/* 386:311 */     localMap.put("timestamp", String.valueOf(paramBlock.getTimestamp()));
/* 387:312 */     localMap.put("height", String.valueOf(paramBlock.getHeight()));
/* 388:313 */     return localMap;
/* 389:    */   }
/* 390:    */   
/* 391:    */   private Map<String, String> getValues(long paramLong, Account.AccountAsset paramAccountAsset, boolean paramBoolean)
/* 392:    */   {
/* 393:317 */     HashMap localHashMap = new HashMap();
/* 394:318 */     localHashMap.put("account", Convert.toUnsignedLong(paramLong));
/* 395:319 */     localHashMap.put("asset", Convert.toUnsignedLong(paramAccountAsset.getAssetId()));
/* 396:320 */     if (paramBoolean) {
/* 397:321 */       localHashMap.put("unconfirmed asset balance", String.valueOf(paramAccountAsset.getUnconfirmedQuantityQNT()));
/* 398:    */     } else {
/* 399:323 */       localHashMap.put("asset balance", String.valueOf(paramAccountAsset.getQuantityQNT()));
/* 400:    */     }
/* 401:325 */     localHashMap.put("timestamp", String.valueOf(Nxt.getBlockchain().getLastBlock().getTimestamp()));
/* 402:326 */     localHashMap.put("height", String.valueOf(Nxt.getBlockchain().getHeight()));
/* 403:327 */     localHashMap.put("event", "asset balance");
/* 404:328 */     return localHashMap;
/* 405:    */   }
/* 406:    */   
/* 407:    */   private Map<String, String> getValues(long paramLong, Account.AccountLease paramAccountLease, boolean paramBoolean)
/* 408:    */   {
/* 409:332 */     HashMap localHashMap = new HashMap();
/* 410:333 */     localHashMap.put("account", Convert.toUnsignedLong(paramLong));
/* 411:334 */     localHashMap.put("event", paramBoolean ? "lease begin" : "lease end");
/* 412:335 */     localHashMap.put("timestamp", String.valueOf(Nxt.getBlockchain().getLastBlock().getTimestamp()));
/* 413:336 */     localHashMap.put("height", String.valueOf(Nxt.getBlockchain().getHeight()));
/* 414:337 */     localHashMap.put("lessee", Convert.toUnsignedLong(paramAccountLease.lesseeId));
/* 415:338 */     return localHashMap;
/* 416:    */   }
/* 417:    */   
/* 418:    */   private Map<String, String> getValues(long paramLong, Transaction paramTransaction, Attachment paramAttachment, boolean paramBoolean)
/* 419:    */   {
/* 420:342 */     Object localObject1 = getValues(paramLong, false);
/* 421:    */     Object localObject2;
/* 422:    */     long l3;
/* 423:343 */     if ((paramAttachment instanceof Attachment.ColoredCoinsOrderPlacement))
/* 424:    */     {
/* 425:344 */       if (paramBoolean) {
/* 426:345 */         return Collections.emptyMap();
/* 427:    */       }
/* 428:347 */       localObject2 = (Attachment.ColoredCoinsOrderPlacement)paramAttachment;
/* 429:348 */       boolean bool = localObject2 instanceof Attachment.ColoredCoinsAskOrderPlacement;
/* 430:349 */       ((Map)localObject1).put("asset", Convert.toUnsignedLong(((Attachment.ColoredCoinsOrderPlacement)localObject2).getAssetId()));
/* 431:350 */       ((Map)localObject1).put("order", paramTransaction.getStringId());
/* 432:351 */       ((Map)localObject1).put("order price", String.valueOf(((Attachment.ColoredCoinsOrderPlacement)localObject2).getPriceNQT()));
/* 433:352 */       l3 = ((Attachment.ColoredCoinsOrderPlacement)localObject2).getQuantityQNT();
/* 434:353 */       if (bool) {
/* 435:354 */         l3 = -l3;
/* 436:    */       }
/* 437:356 */       ((Map)localObject1).put("order quantity", String.valueOf(l3));
/* 438:357 */       BigInteger localBigInteger = BigInteger.valueOf(((Attachment.ColoredCoinsOrderPlacement)localObject2).getPriceNQT()).multiply(BigInteger.valueOf(((Attachment.ColoredCoinsOrderPlacement)localObject2).getQuantityQNT()));
/* 439:358 */       if (!bool) {
/* 440:359 */         localBigInteger = localBigInteger.negate();
/* 441:    */       }
/* 442:361 */       ((Map)localObject1).put("order cost", localBigInteger.toString());
/* 443:362 */       String str = (bool ? "ask" : "bid") + " order";
/* 444:363 */       ((Map)localObject1).put("event", str);
/* 445:    */     }
/* 446:364 */     else if ((paramAttachment instanceof Attachment.ColoredCoinsAssetIssuance))
/* 447:    */     {
/* 448:365 */       if (paramBoolean) {
/* 449:366 */         return Collections.emptyMap();
/* 450:    */       }
/* 451:368 */       localObject2 = (Attachment.ColoredCoinsAssetIssuance)paramAttachment;
/* 452:369 */       ((Map)localObject1).put("asset", paramTransaction.getStringId());
/* 453:370 */       ((Map)localObject1).put("asset quantity", String.valueOf(((Attachment.ColoredCoinsAssetIssuance)localObject2).getQuantityQNT()));
/* 454:371 */       ((Map)localObject1).put("event", "asset issuance");
/* 455:    */     }
/* 456:372 */     else if ((paramAttachment instanceof Attachment.ColoredCoinsAssetTransfer))
/* 457:    */     {
/* 458:373 */       localObject2 = (Attachment.ColoredCoinsAssetTransfer)paramAttachment;
/* 459:374 */       ((Map)localObject1).put("asset", Convert.toUnsignedLong(((Attachment.ColoredCoinsAssetTransfer)localObject2).getAssetId()));
/* 460:375 */       long l1 = ((Attachment.ColoredCoinsAssetTransfer)localObject2).getQuantityQNT();
/* 461:376 */       if (!paramBoolean) {
/* 462:377 */         l1 = -l1;
/* 463:    */       }
/* 464:379 */       ((Map)localObject1).put("asset quantity", String.valueOf(l1));
/* 465:380 */       ((Map)localObject1).put("event", "asset transfer");
/* 466:    */     }
/* 467:381 */     else if ((paramAttachment instanceof Attachment.ColoredCoinsOrderCancellation))
/* 468:    */     {
/* 469:382 */       localObject2 = (Attachment.ColoredCoinsOrderCancellation)paramAttachment;
/* 470:383 */       ((Map)localObject1).put("order", Convert.toUnsignedLong(((Attachment.ColoredCoinsOrderCancellation)localObject2).getOrderId()));
/* 471:384 */       ((Map)localObject1).put("event", "order cancel");
/* 472:    */     }
/* 473:385 */     else if ((paramAttachment instanceof Attachment.DigitalGoodsPurchase))
/* 474:    */     {
/* 475:386 */       localObject2 = (Attachment.DigitalGoodsPurchase)paramTransaction.getAttachment();
/* 476:387 */       if (paramBoolean) {
/* 477:388 */         localObject1 = getValues(DigitalGoodsStore.getGoods(((Attachment.DigitalGoodsPurchase)localObject2).getGoodsId()).getSellerId(), false);
/* 478:    */       }
/* 479:390 */       ((Map)localObject1).put("event", "purchase");
/* 480:391 */       ((Map)localObject1).put("purchase", paramTransaction.getStringId());
/* 481:    */     }
/* 482:392 */     else if ((paramAttachment instanceof Attachment.DigitalGoodsDelivery))
/* 483:    */     {
/* 484:393 */       localObject2 = (Attachment.DigitalGoodsDelivery)paramTransaction.getAttachment();
/* 485:394 */       DigitalGoodsStore.Purchase localPurchase = DigitalGoodsStore.getPurchase(((Attachment.DigitalGoodsDelivery)localObject2).getPurchaseId());
/* 486:395 */       if (paramBoolean) {
/* 487:396 */         localObject1 = getValues(localPurchase.getBuyerId(), false);
/* 488:    */       }
/* 489:398 */       ((Map)localObject1).put("event", "delivery");
/* 490:399 */       ((Map)localObject1).put("purchase", Convert.toUnsignedLong(((Attachment.DigitalGoodsDelivery)localObject2).getPurchaseId()));
/* 491:400 */       l3 = ((Attachment.DigitalGoodsDelivery)localObject2).getDiscountNQT();
/* 492:401 */       ((Map)localObject1).put("purchase price", String.valueOf(localPurchase.getPriceNQT()));
/* 493:402 */       ((Map)localObject1).put("purchase quantity", String.valueOf(localPurchase.getQuantity()));
/* 494:403 */       long l4 = Convert.safeMultiply(localPurchase.getPriceNQT(), localPurchase.getQuantity());
/* 495:404 */       if (paramBoolean) {
/* 496:405 */         l4 = -l4;
/* 497:    */       }
/* 498:407 */       ((Map)localObject1).put("purchase cost", String.valueOf(l4));
/* 499:408 */       if (!paramBoolean) {
/* 500:409 */         l3 = -l3;
/* 501:    */       }
/* 502:411 */       ((Map)localObject1).put("discount", String.valueOf(l3));
/* 503:    */     }
/* 504:412 */     else if ((paramAttachment instanceof Attachment.DigitalGoodsRefund))
/* 505:    */     {
/* 506:413 */       localObject2 = (Attachment.DigitalGoodsRefund)paramTransaction.getAttachment();
/* 507:414 */       if (paramBoolean) {
/* 508:415 */         localObject1 = getValues(DigitalGoodsStore.getPurchase(((Attachment.DigitalGoodsRefund)localObject2).getPurchaseId()).getBuyerId(), false);
/* 509:    */       }
/* 510:417 */       ((Map)localObject1).put("event", "refund");
/* 511:418 */       ((Map)localObject1).put("purchase", Convert.toUnsignedLong(((Attachment.DigitalGoodsRefund)localObject2).getPurchaseId()));
/* 512:419 */       long l2 = ((Attachment.DigitalGoodsRefund)localObject2).getRefundNQT();
/* 513:420 */       if (!paramBoolean) {
/* 514:421 */         l2 = -l2;
/* 515:    */       }
/* 516:423 */       ((Map)localObject1).put("refund", String.valueOf(l2));
/* 517:    */     }
/* 518:424 */     else if (paramAttachment == Attachment.ARBITRARY_MESSAGE)
/* 519:    */     {
/* 520:425 */       localObject1 = new HashMap();
/* 521:426 */       ((Map)localObject1).put("account", Convert.toUnsignedLong(paramLong));
/* 522:427 */       ((Map)localObject1).put("timestamp", String.valueOf(Nxt.getBlockchain().getLastBlock().getTimestamp()));
/* 523:428 */       ((Map)localObject1).put("height", String.valueOf(Nxt.getBlockchain().getHeight()));
/* 524:429 */       ((Map)localObject1).put("event", paramAttachment == Attachment.ARBITRARY_MESSAGE ? "message" : "encrypted message");
/* 525:430 */       if (paramBoolean) {
/* 526:431 */         ((Map)localObject1).put("sender", Convert.toUnsignedLong(paramTransaction.getSenderId()));
/* 527:    */       } else {
/* 528:433 */         ((Map)localObject1).put("recipient", Convert.toUnsignedLong(paramTransaction.getRecipientId()));
/* 529:    */       }
/* 530:    */     }
/* 531:    */     else
/* 532:    */     {
/* 533:436 */       return Collections.emptyMap();
/* 534:    */     }
/* 535:438 */     return (Map<String, String>)localObject1;
/* 536:    */   }
/* 537:    */   
/* 538:    */   private void log(Map<String, String> paramMap)
/* 539:    */   {
/* 540:442 */     if (paramMap.isEmpty()) {
/* 541:443 */       return;
/* 542:    */     }
/* 543:445 */     StringBuilder localStringBuilder = new StringBuilder();
/* 544:446 */     for (String str1 : columns) {
/* 545:447 */       if ((LOG_UNCONFIRMED) || (!str1.startsWith("unconfirmed")))
/* 546:    */       {
/* 547:450 */         String str2 = (String)paramMap.get(str1);
/* 548:451 */         if (str2 != null) {
/* 549:452 */           localStringBuilder.append(QUOTE).append(str2).append(QUOTE);
/* 550:    */         }
/* 551:454 */         localStringBuilder.append(SEPARATOR);
/* 552:    */       }
/* 553:    */     }
/* 554:456 */     this.log.println(localStringBuilder.toString());
/* 555:    */   }
/* 556:    */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.DebugTrace
 * JD-Core Version:    0.7.1
 */