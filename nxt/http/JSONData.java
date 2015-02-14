/*   1:    */ package nxt.http;
/*   2:    */ 
/*   3:    */ import java.nio.ByteBuffer;
/*   4:    */ import java.nio.ByteOrder;
/*   5:    */ import java.security.MessageDigest;
/*   6:    */ import java.util.Collections;
/*   7:    */ import java.util.Iterator;
/*   8:    */ import java.util.List;
/*   9:    */ import java.util.Map;
/*  10:    */ import nxt.AT;
/*  11:    */ import nxt.Account;
/*  12:    */ import nxt.Account.AccountAsset;
/*  13:    */ import nxt.Alias;
/*  14:    */ import nxt.Alias.Offer;
/*  15:    */ import nxt.Appendix;
/*  16:    */ import nxt.Asset;
/*  17:    */ import nxt.AssetTransfer;
/*  18:    */ import nxt.Block;
/*  19:    */ import nxt.Blockchain;
/*  20:    */ import nxt.DigitalGoodsStore.Goods;
/*  21:    */ import nxt.DigitalGoodsStore.Purchase;
/*  22:    */ import nxt.Escrow;
/*  23:    */ import nxt.Escrow.Decision;
/*  24:    */ import nxt.Nxt;
/*  25:    */ import nxt.Order;
/*  26:    */ import nxt.Order.Ask;
/*  27:    */ import nxt.Order.Bid;
/*  28:    */ import nxt.Poll;
/*  29:    */ import nxt.Subscription;
/*  30:    */ import nxt.Token;
/*  31:    */ import nxt.Trade;
/*  32:    */ import nxt.Transaction;
/*  33:    */ import nxt.TransactionType;
/*  34:    */ import nxt.at.AT_API_Helper;
/*  35:    */ import nxt.at.AT_Machine_State.Machine_State;
/*  36:    */ import nxt.crypto.Crypto;
/*  37:    */ import nxt.crypto.EncryptedData;
/*  38:    */ import nxt.peer.Hallmark;
/*  39:    */ import nxt.peer.Peer;
/*  40:    */ import nxt.peer.Peer.State;
/*  41:    */ import nxt.util.Convert;
/*  42:    */ import org.json.simple.JSONArray;
/*  43:    */ import org.json.simple.JSONObject;
/*  44:    */ 
/*  45:    */ public final class JSONData
/*  46:    */ {
/*  47:    */   static JSONObject alias(Alias paramAlias)
/*  48:    */   {
/*  49: 37 */     JSONObject localJSONObject = new JSONObject();
/*  50: 38 */     putAccount(localJSONObject, "account", paramAlias.getAccountId());
/*  51: 39 */     localJSONObject.put("aliasName", paramAlias.getAliasName());
/*  52: 40 */     localJSONObject.put("aliasURI", paramAlias.getAliasURI());
/*  53: 41 */     localJSONObject.put("timestamp", Integer.valueOf(paramAlias.getTimestamp()));
/*  54: 42 */     localJSONObject.put("alias", Convert.toUnsignedLong(paramAlias.getId()));
/*  55: 43 */     Alias.Offer localOffer = Alias.getOffer(paramAlias);
/*  56: 44 */     if (localOffer != null)
/*  57:    */     {
/*  58: 45 */       localJSONObject.put("priceNQT", String.valueOf(localOffer.getPriceNQT()));
/*  59: 46 */       if (localOffer.getBuyerId() != 0L) {
/*  60: 47 */         localJSONObject.put("buyer", Convert.toUnsignedLong(localOffer.getBuyerId()));
/*  61:    */       }
/*  62:    */     }
/*  63: 50 */     return localJSONObject;
/*  64:    */   }
/*  65:    */   
/*  66:    */   static JSONObject accountBalance(Account paramAccount)
/*  67:    */   {
/*  68: 54 */     JSONObject localJSONObject = new JSONObject();
/*  69: 55 */     if (paramAccount == null)
/*  70:    */     {
/*  71: 56 */       localJSONObject.put("balanceNQT", "0");
/*  72: 57 */       localJSONObject.put("unconfirmedBalanceNQT", "0");
/*  73: 58 */       localJSONObject.put("effectiveBalanceNXT", "0");
/*  74: 59 */       localJSONObject.put("forgedBalanceNQT", "0");
/*  75: 60 */       localJSONObject.put("guaranteedBalanceNQT", "0");
/*  76:    */     }
/*  77:    */     else
/*  78:    */     {
/*  79: 62 */       localJSONObject.put("balanceNQT", String.valueOf(paramAccount.getBalanceNQT()));
/*  80: 63 */       localJSONObject.put("unconfirmedBalanceNQT", String.valueOf(paramAccount.getUnconfirmedBalanceNQT()));
/*  81: 64 */       localJSONObject.put("effectiveBalanceNXT", Long.valueOf(paramAccount.getEffectiveBalanceNXT()));
/*  82: 65 */       localJSONObject.put("forgedBalanceNQT", String.valueOf(paramAccount.getForgedBalanceNQT()));
/*  83: 66 */       localJSONObject.put("guaranteedBalanceNQT", String.valueOf(paramAccount.getGuaranteedBalanceNQT(1440)));
/*  84:    */     }
/*  85: 68 */     return localJSONObject;
/*  86:    */   }
/*  87:    */   
/*  88:    */   static JSONObject asset(Asset paramAsset)
/*  89:    */   {
/*  90: 72 */     JSONObject localJSONObject = new JSONObject();
/*  91: 73 */     putAccount(localJSONObject, "account", paramAsset.getAccountId());
/*  92: 74 */     localJSONObject.put("name", paramAsset.getName());
/*  93: 75 */     localJSONObject.put("description", paramAsset.getDescription());
/*  94: 76 */     localJSONObject.put("decimals", Byte.valueOf(paramAsset.getDecimals()));
/*  95: 77 */     localJSONObject.put("quantityQNT", String.valueOf(paramAsset.getQuantityQNT()));
/*  96: 78 */     localJSONObject.put("asset", Convert.toUnsignedLong(paramAsset.getId()));
/*  97: 79 */     localJSONObject.put("numberOfTrades", Integer.valueOf(Trade.getTradeCount(paramAsset.getId())));
/*  98: 80 */     localJSONObject.put("numberOfTransfers", Integer.valueOf(AssetTransfer.getTransferCount(paramAsset.getId())));
/*  99: 81 */     localJSONObject.put("numberOfAccounts", Integer.valueOf(Account.getAssetAccountsCount(paramAsset.getId())));
/* 100: 82 */     return localJSONObject;
/* 101:    */   }
/* 102:    */   
/* 103:    */   static JSONObject accountAsset(Account.AccountAsset paramAccountAsset)
/* 104:    */   {
/* 105: 86 */     JSONObject localJSONObject = new JSONObject();
/* 106: 87 */     putAccount(localJSONObject, "account", paramAccountAsset.getAccountId());
/* 107: 88 */     localJSONObject.put("asset", Convert.toUnsignedLong(paramAccountAsset.getAssetId()));
/* 108: 89 */     localJSONObject.put("quantityQNT", String.valueOf(paramAccountAsset.getQuantityQNT()));
/* 109: 90 */     localJSONObject.put("unconfirmedQuantityQNT", String.valueOf(paramAccountAsset.getUnconfirmedQuantityQNT()));
/* 110: 91 */     return localJSONObject;
/* 111:    */   }
/* 112:    */   
/* 113:    */   static JSONObject askOrder(Order.Ask paramAsk)
/* 114:    */   {
/* 115: 95 */     JSONObject localJSONObject = order(paramAsk);
/* 116: 96 */     localJSONObject.put("type", "ask");
/* 117: 97 */     return localJSONObject;
/* 118:    */   }
/* 119:    */   
/* 120:    */   static JSONObject bidOrder(Order.Bid paramBid)
/* 121:    */   {
/* 122:101 */     JSONObject localJSONObject = order(paramBid);
/* 123:102 */     localJSONObject.put("type", "bid");
/* 124:103 */     return localJSONObject;
/* 125:    */   }
/* 126:    */   
/* 127:    */   static JSONObject order(Order paramOrder)
/* 128:    */   {
/* 129:107 */     JSONObject localJSONObject = new JSONObject();
/* 130:108 */     localJSONObject.put("order", Convert.toUnsignedLong(paramOrder.getId()));
/* 131:109 */     localJSONObject.put("asset", Convert.toUnsignedLong(paramOrder.getAssetId()));
/* 132:110 */     putAccount(localJSONObject, "account", paramOrder.getAccountId());
/* 133:111 */     localJSONObject.put("quantityQNT", String.valueOf(paramOrder.getQuantityQNT()));
/* 134:112 */     localJSONObject.put("priceNQT", String.valueOf(paramOrder.getPriceNQT()));
/* 135:113 */     localJSONObject.put("height", Integer.valueOf(paramOrder.getHeight()));
/* 136:114 */     return localJSONObject;
/* 137:    */   }
/* 138:    */   
/* 139:    */   static JSONObject block(Block paramBlock, boolean paramBoolean)
/* 140:    */   {
/* 141:118 */     JSONObject localJSONObject = new JSONObject();
/* 142:119 */     localJSONObject.put("block", paramBlock.getStringId());
/* 143:120 */     localJSONObject.put("height", Integer.valueOf(paramBlock.getHeight()));
/* 144:121 */     putAccount(localJSONObject, "generator", paramBlock.getGeneratorId());
/* 145:122 */     localJSONObject.put("generatorPublicKey", Convert.toHexString(paramBlock.getGeneratorPublicKey()));
/* 146:123 */     localJSONObject.put("nonce", Convert.toUnsignedLong(paramBlock.getNonce().longValue()));
/* 147:124 */     localJSONObject.put("scoopNum", Integer.valueOf(paramBlock.getScoopNum()));
/* 148:125 */     localJSONObject.put("timestamp", Integer.valueOf(paramBlock.getTimestamp()));
/* 149:126 */     localJSONObject.put("numberOfTransactions", Integer.valueOf(paramBlock.getTransactions().size()));
/* 150:127 */     localJSONObject.put("totalAmountNQT", String.valueOf(paramBlock.getTotalAmountNQT()));
/* 151:128 */     localJSONObject.put("totalFeeNQT", String.valueOf(paramBlock.getTotalFeeNQT()));
/* 152:129 */     localJSONObject.put("blockReward", Convert.toUnsignedLong(paramBlock.getBlockReward() / 100000000L));
/* 153:130 */     localJSONObject.put("payloadLength", Integer.valueOf(paramBlock.getPayloadLength()));
/* 154:131 */     localJSONObject.put("version", Integer.valueOf(paramBlock.getVersion()));
/* 155:132 */     localJSONObject.put("baseTarget", Convert.toUnsignedLong(paramBlock.getBaseTarget()));
/* 156:133 */     if (paramBlock.getPreviousBlockId() != 0L) {
/* 157:134 */       localJSONObject.put("previousBlock", Convert.toUnsignedLong(paramBlock.getPreviousBlockId()));
/* 158:    */     }
/* 159:136 */     if (paramBlock.getNextBlockId() != 0L) {
/* 160:137 */       localJSONObject.put("nextBlock", Convert.toUnsignedLong(paramBlock.getNextBlockId()));
/* 161:    */     }
/* 162:139 */     localJSONObject.put("payloadHash", Convert.toHexString(paramBlock.getPayloadHash()));
/* 163:140 */     localJSONObject.put("generationSignature", Convert.toHexString(paramBlock.getGenerationSignature()));
/* 164:141 */     if (paramBlock.getVersion() > 1) {
/* 165:142 */       localJSONObject.put("previousBlockHash", Convert.toHexString(paramBlock.getPreviousBlockHash()));
/* 166:    */     }
/* 167:144 */     localJSONObject.put("blockSignature", Convert.toHexString(paramBlock.getBlockSignature()));
/* 168:145 */     JSONArray localJSONArray = new JSONArray();
/* 169:146 */     for (Transaction localTransaction : paramBlock.getTransactions()) {
/* 170:147 */       localJSONArray.add(paramBoolean ? transaction(localTransaction) : Convert.toUnsignedLong(localTransaction.getId()));
/* 171:    */     }
/* 172:149 */     localJSONObject.put("transactions", localJSONArray);
/* 173:150 */     return localJSONObject;
/* 174:    */   }
/* 175:    */   
/* 176:    */   static JSONObject encryptedData(EncryptedData paramEncryptedData)
/* 177:    */   {
/* 178:154 */     JSONObject localJSONObject = new JSONObject();
/* 179:155 */     localJSONObject.put("data", Convert.toHexString(paramEncryptedData.getData()));
/* 180:156 */     localJSONObject.put("nonce", Convert.toHexString(paramEncryptedData.getNonce()));
/* 181:157 */     return localJSONObject;
/* 182:    */   }
/* 183:    */   
/* 184:    */   static JSONObject escrowTransaction(Escrow paramEscrow)
/* 185:    */   {
/* 186:161 */     JSONObject localJSONObject1 = new JSONObject();
/* 187:162 */     localJSONObject1.put("id", Convert.toUnsignedLong(paramEscrow.getId().longValue()));
/* 188:163 */     localJSONObject1.put("sender", Convert.toUnsignedLong(paramEscrow.getSenderId().longValue()));
/* 189:164 */     localJSONObject1.put("senderRS", Convert.rsAccount(paramEscrow.getSenderId().longValue()));
/* 190:165 */     localJSONObject1.put("recipient", Convert.toUnsignedLong(paramEscrow.getRecipientId().longValue()));
/* 191:166 */     localJSONObject1.put("recipientRS", Convert.rsAccount(paramEscrow.getRecipientId().longValue()));
/* 192:167 */     localJSONObject1.put("amountNQT", Convert.toUnsignedLong(Escrow.getEscrowTransaction(paramEscrow.getId()).getAmountNQT().longValue()));
/* 193:168 */     localJSONObject1.put("requiredSigners", Integer.valueOf(paramEscrow.getRequiredSigners()));
/* 194:169 */     localJSONObject1.put("deadline", Integer.valueOf(paramEscrow.getDeadline()));
/* 195:170 */     localJSONObject1.put("deadlineAction", Escrow.decisionToString(paramEscrow.getDeadlineAction()));
/* 196:    */     
/* 197:172 */     JSONArray localJSONArray = new JSONArray();
/* 198:173 */     for (Escrow.Decision localDecision : paramEscrow.getDecisions()) {
/* 199:174 */       if ((!localDecision.getAccountId().equals(paramEscrow.getSenderId())) && (!localDecision.getAccountId().equals(paramEscrow.getRecipientId())))
/* 200:    */       {
/* 201:178 */         JSONObject localJSONObject2 = new JSONObject();
/* 202:179 */         localJSONObject2.put("id", Convert.toUnsignedLong(localDecision.getAccountId().longValue()));
/* 203:180 */         localJSONObject2.put("idRS", Convert.rsAccount(localDecision.getAccountId().longValue()));
/* 204:181 */         localJSONObject2.put("decision", Escrow.decisionToString(localDecision.getDecision()));
/* 205:182 */         localJSONArray.add(localJSONObject2);
/* 206:    */       }
/* 207:    */     }
/* 208:184 */     localJSONObject1.put("signers", localJSONArray);
/* 209:185 */     return localJSONObject1;
/* 210:    */   }
/* 211:    */   
/* 212:    */   static JSONObject goods(DigitalGoodsStore.Goods paramGoods)
/* 213:    */   {
/* 214:189 */     JSONObject localJSONObject = new JSONObject();
/* 215:190 */     localJSONObject.put("goods", Convert.toUnsignedLong(paramGoods.getId()));
/* 216:191 */     localJSONObject.put("name", paramGoods.getName());
/* 217:192 */     localJSONObject.put("description", paramGoods.getDescription());
/* 218:193 */     localJSONObject.put("quantity", Integer.valueOf(paramGoods.getQuantity()));
/* 219:194 */     localJSONObject.put("priceNQT", String.valueOf(paramGoods.getPriceNQT()));
/* 220:195 */     putAccount(localJSONObject, "seller", paramGoods.getSellerId());
/* 221:196 */     localJSONObject.put("tags", paramGoods.getTags());
/* 222:197 */     localJSONObject.put("delisted", Boolean.valueOf(paramGoods.isDelisted()));
/* 223:198 */     localJSONObject.put("timestamp", Integer.valueOf(paramGoods.getTimestamp()));
/* 224:199 */     return localJSONObject;
/* 225:    */   }
/* 226:    */   
/* 227:    */   static JSONObject hallmark(Hallmark paramHallmark)
/* 228:    */   {
/* 229:203 */     JSONObject localJSONObject = new JSONObject();
/* 230:204 */     putAccount(localJSONObject, "account", Account.getId(paramHallmark.getPublicKey()));
/* 231:205 */     localJSONObject.put("host", paramHallmark.getHost());
/* 232:206 */     localJSONObject.put("weight", Integer.valueOf(paramHallmark.getWeight()));
/* 233:207 */     String str = Hallmark.formatDate(paramHallmark.getDate());
/* 234:208 */     localJSONObject.put("date", str);
/* 235:209 */     localJSONObject.put("valid", Boolean.valueOf(paramHallmark.isValid()));
/* 236:210 */     return localJSONObject;
/* 237:    */   }
/* 238:    */   
/* 239:    */   static JSONObject token(Token paramToken)
/* 240:    */   {
/* 241:214 */     JSONObject localJSONObject = new JSONObject();
/* 242:215 */     putAccount(localJSONObject, "account", Account.getId(paramToken.getPublicKey()));
/* 243:216 */     localJSONObject.put("timestamp", Integer.valueOf(paramToken.getTimestamp()));
/* 244:217 */     localJSONObject.put("valid", Boolean.valueOf(paramToken.isValid()));
/* 245:218 */     return localJSONObject;
/* 246:    */   }
/* 247:    */   
/* 248:    */   static JSONObject peer(Peer paramPeer)
/* 249:    */   {
/* 250:222 */     JSONObject localJSONObject = new JSONObject();
/* 251:223 */     localJSONObject.put("state", Integer.valueOf(paramPeer.getState().ordinal()));
/* 252:224 */     localJSONObject.put("announcedAddress", paramPeer.getAnnouncedAddress());
/* 253:225 */     localJSONObject.put("shareAddress", Boolean.valueOf(paramPeer.shareAddress()));
/* 254:226 */     if (paramPeer.getHallmark() != null) {
/* 255:227 */       localJSONObject.put("hallmark", paramPeer.getHallmark().getHallmarkString());
/* 256:    */     }
/* 257:229 */     localJSONObject.put("weight", Integer.valueOf(paramPeer.getWeight()));
/* 258:230 */     localJSONObject.put("downloadedVolume", Long.valueOf(paramPeer.getDownloadedVolume()));
/* 259:231 */     localJSONObject.put("uploadedVolume", Long.valueOf(paramPeer.getUploadedVolume()));
/* 260:232 */     localJSONObject.put("application", paramPeer.getApplication());
/* 261:233 */     localJSONObject.put("version", paramPeer.getVersion());
/* 262:234 */     localJSONObject.put("platform", paramPeer.getPlatform());
/* 263:235 */     localJSONObject.put("blacklisted", Boolean.valueOf(paramPeer.isBlacklisted()));
/* 264:236 */     localJSONObject.put("lastUpdated", Integer.valueOf(paramPeer.getLastUpdated()));
/* 265:237 */     return localJSONObject;
/* 266:    */   }
/* 267:    */   
/* 268:    */   static JSONObject poll(Poll paramPoll)
/* 269:    */   {
/* 270:241 */     JSONObject localJSONObject = new JSONObject();
/* 271:242 */     localJSONObject.put("name", paramPoll.getName());
/* 272:243 */     localJSONObject.put("description", paramPoll.getDescription());
/* 273:244 */     JSONArray localJSONArray1 = new JSONArray();
/* 274:245 */     Collections.addAll(localJSONArray1, paramPoll.getOptions());
/* 275:246 */     localJSONObject.put("options", localJSONArray1);
/* 276:247 */     localJSONObject.put("minNumberOfOptions", Byte.valueOf(paramPoll.getMinNumberOfOptions()));
/* 277:248 */     localJSONObject.put("maxNumberOfOptions", Byte.valueOf(paramPoll.getMaxNumberOfOptions()));
/* 278:249 */     localJSONObject.put("optionsAreBinary", Boolean.valueOf(paramPoll.isOptionsAreBinary()));
/* 279:250 */     JSONArray localJSONArray2 = new JSONArray();
/* 280:251 */     for (Long localLong : paramPoll.getVoters().keySet()) {
/* 281:252 */       localJSONArray2.add(Convert.toUnsignedLong(localLong.longValue()));
/* 282:    */     }
/* 283:254 */     localJSONObject.put("voters", localJSONArray2);
/* 284:255 */     return localJSONObject;
/* 285:    */   }
/* 286:    */   
/* 287:    */   static JSONObject purchase(DigitalGoodsStore.Purchase paramPurchase)
/* 288:    */   {
/* 289:259 */     JSONObject localJSONObject = new JSONObject();
/* 290:260 */     localJSONObject.put("purchase", Convert.toUnsignedLong(paramPurchase.getId()));
/* 291:261 */     localJSONObject.put("goods", Convert.toUnsignedLong(paramPurchase.getGoodsId()));
/* 292:262 */     localJSONObject.put("name", paramPurchase.getName());
/* 293:263 */     putAccount(localJSONObject, "seller", paramPurchase.getSellerId());
/* 294:264 */     localJSONObject.put("priceNQT", String.valueOf(paramPurchase.getPriceNQT()));
/* 295:265 */     localJSONObject.put("quantity", Integer.valueOf(paramPurchase.getQuantity()));
/* 296:266 */     putAccount(localJSONObject, "buyer", paramPurchase.getBuyerId());
/* 297:267 */     localJSONObject.put("timestamp", Integer.valueOf(paramPurchase.getTimestamp()));
/* 298:268 */     localJSONObject.put("deliveryDeadlineTimestamp", Integer.valueOf(paramPurchase.getDeliveryDeadlineTimestamp()));
/* 299:269 */     if (paramPurchase.getNote() != null) {
/* 300:270 */       localJSONObject.put("note", encryptedData(paramPurchase.getNote()));
/* 301:    */     }
/* 302:272 */     localJSONObject.put("pending", Boolean.valueOf(paramPurchase.isPending()));
/* 303:273 */     if (paramPurchase.getEncryptedGoods() != null)
/* 304:    */     {
/* 305:274 */       localJSONObject.put("goodsData", encryptedData(paramPurchase.getEncryptedGoods()));
/* 306:275 */       localJSONObject.put("goodsIsText", Boolean.valueOf(paramPurchase.goodsIsText()));
/* 307:    */     }
/* 308:    */     JSONArray localJSONArray;
/* 309:    */     Iterator localIterator;
/* 310:    */     Object localObject;
/* 311:277 */     if (paramPurchase.getFeedbackNotes() != null)
/* 312:    */     {
/* 313:278 */       localJSONArray = new JSONArray();
/* 314:279 */       for (localIterator = paramPurchase.getFeedbackNotes().iterator(); localIterator.hasNext();)
/* 315:    */       {
/* 316:279 */         localObject = (EncryptedData)localIterator.next();
/* 317:280 */         localJSONArray.add(encryptedData((EncryptedData)localObject));
/* 318:    */       }
/* 319:282 */       localJSONObject.put("feedbackNotes", localJSONArray);
/* 320:    */     }
/* 321:284 */     if (paramPurchase.getPublicFeedback() != null)
/* 322:    */     {
/* 323:285 */       localJSONArray = new JSONArray();
/* 324:286 */       for (localIterator = paramPurchase.getPublicFeedback().iterator(); localIterator.hasNext();)
/* 325:    */       {
/* 326:286 */         localObject = (String)localIterator.next();
/* 327:287 */         localJSONArray.add(localObject);
/* 328:    */       }
/* 329:289 */       localJSONObject.put("publicFeedbacks", localJSONArray);
/* 330:    */     }
/* 331:291 */     if (paramPurchase.getRefundNote() != null) {
/* 332:292 */       localJSONObject.put("refundNote", encryptedData(paramPurchase.getRefundNote()));
/* 333:    */     }
/* 334:294 */     if (paramPurchase.getDiscountNQT() > 0L) {
/* 335:295 */       localJSONObject.put("discountNQT", String.valueOf(paramPurchase.getDiscountNQT()));
/* 336:    */     }
/* 337:297 */     if (paramPurchase.getRefundNQT() > 0L) {
/* 338:298 */       localJSONObject.put("refundNQT", String.valueOf(paramPurchase.getRefundNQT()));
/* 339:    */     }
/* 340:300 */     return localJSONObject;
/* 341:    */   }
/* 342:    */   
/* 343:    */   static JSONObject subscription(Subscription paramSubscription)
/* 344:    */   {
/* 345:304 */     JSONObject localJSONObject = new JSONObject();
/* 346:305 */     localJSONObject.put("id", Convert.toUnsignedLong(paramSubscription.getId().longValue()));
/* 347:306 */     putAccount(localJSONObject, "sender", paramSubscription.getSenderId().longValue());
/* 348:307 */     putAccount(localJSONObject, "recipient", paramSubscription.getRecipientId().longValue());
/* 349:308 */     localJSONObject.put("amountNQT", Convert.toUnsignedLong(paramSubscription.getAmountNQT().longValue()));
/* 350:309 */     localJSONObject.put("frequency", Integer.valueOf(paramSubscription.getFrequency()));
/* 351:310 */     localJSONObject.put("timeNext", Integer.valueOf(paramSubscription.getTimeNext()));
/* 352:311 */     return localJSONObject;
/* 353:    */   }
/* 354:    */   
/* 355:    */   static JSONObject trade(Trade paramTrade, boolean paramBoolean)
/* 356:    */   {
/* 357:315 */     JSONObject localJSONObject = new JSONObject();
/* 358:316 */     localJSONObject.put("timestamp", Integer.valueOf(paramTrade.getTimestamp()));
/* 359:317 */     localJSONObject.put("quantityQNT", String.valueOf(paramTrade.getQuantityQNT()));
/* 360:318 */     localJSONObject.put("priceNQT", String.valueOf(paramTrade.getPriceNQT()));
/* 361:319 */     localJSONObject.put("asset", Convert.toUnsignedLong(paramTrade.getAssetId()));
/* 362:320 */     localJSONObject.put("askOrder", Convert.toUnsignedLong(paramTrade.getAskOrderId()));
/* 363:321 */     localJSONObject.put("bidOrder", Convert.toUnsignedLong(paramTrade.getBidOrderId()));
/* 364:322 */     localJSONObject.put("askOrderHeight", Integer.valueOf(paramTrade.getAskOrderHeight()));
/* 365:323 */     localJSONObject.put("bidOrderHeight", Integer.valueOf(paramTrade.getBidOrderHeight()));
/* 366:324 */     putAccount(localJSONObject, "seller", paramTrade.getSellerId());
/* 367:325 */     putAccount(localJSONObject, "buyer", paramTrade.getBuyerId());
/* 368:326 */     localJSONObject.put("block", Convert.toUnsignedLong(paramTrade.getBlockId()));
/* 369:327 */     localJSONObject.put("height", Integer.valueOf(paramTrade.getHeight()));
/* 370:328 */     localJSONObject.put("tradeType", paramTrade.isBuy() ? "buy" : "sell");
/* 371:329 */     if (paramBoolean)
/* 372:    */     {
/* 373:330 */       Asset localAsset = Asset.getAsset(paramTrade.getAssetId());
/* 374:331 */       localJSONObject.put("name", localAsset.getName());
/* 375:332 */       localJSONObject.put("decimals", Byte.valueOf(localAsset.getDecimals()));
/* 376:    */     }
/* 377:334 */     return localJSONObject;
/* 378:    */   }
/* 379:    */   
/* 380:    */   static JSONObject assetTransfer(AssetTransfer paramAssetTransfer, boolean paramBoolean)
/* 381:    */   {
/* 382:338 */     JSONObject localJSONObject = new JSONObject();
/* 383:339 */     localJSONObject.put("assetTransfer", Convert.toUnsignedLong(paramAssetTransfer.getId()));
/* 384:340 */     localJSONObject.put("asset", Convert.toUnsignedLong(paramAssetTransfer.getAssetId()));
/* 385:341 */     putAccount(localJSONObject, "sender", paramAssetTransfer.getSenderId());
/* 386:342 */     putAccount(localJSONObject, "recipient", paramAssetTransfer.getRecipientId());
/* 387:343 */     localJSONObject.put("quantityQNT", String.valueOf(paramAssetTransfer.getQuantityQNT()));
/* 388:344 */     localJSONObject.put("height", Integer.valueOf(paramAssetTransfer.getHeight()));
/* 389:345 */     localJSONObject.put("timestamp", Integer.valueOf(paramAssetTransfer.getTimestamp()));
/* 390:346 */     if (paramBoolean)
/* 391:    */     {
/* 392:347 */       Asset localAsset = Asset.getAsset(paramAssetTransfer.getAssetId());
/* 393:348 */       localJSONObject.put("name", localAsset.getName());
/* 394:349 */       localJSONObject.put("decimals", Byte.valueOf(localAsset.getDecimals()));
/* 395:    */     }
/* 396:351 */     return localJSONObject;
/* 397:    */   }
/* 398:    */   
/* 399:    */   static JSONObject unconfirmedTransaction(Transaction paramTransaction)
/* 400:    */   {
/* 401:355 */     JSONObject localJSONObject1 = new JSONObject();
/* 402:356 */     localJSONObject1.put("type", Byte.valueOf(paramTransaction.getType().getType()));
/* 403:357 */     localJSONObject1.put("subtype", Byte.valueOf(paramTransaction.getType().getSubtype()));
/* 404:358 */     localJSONObject1.put("timestamp", Integer.valueOf(paramTransaction.getTimestamp()));
/* 405:359 */     localJSONObject1.put("deadline", Short.valueOf(paramTransaction.getDeadline()));
/* 406:360 */     localJSONObject1.put("senderPublicKey", Convert.toHexString(paramTransaction.getSenderPublicKey()));
/* 407:361 */     if (paramTransaction.getRecipientId() != 0L) {
/* 408:362 */       putAccount(localJSONObject1, "recipient", paramTransaction.getRecipientId());
/* 409:    */     }
/* 410:364 */     localJSONObject1.put("amountNQT", String.valueOf(paramTransaction.getAmountNQT()));
/* 411:365 */     localJSONObject1.put("feeNQT", String.valueOf(paramTransaction.getFeeNQT()));
/* 412:366 */     if (paramTransaction.getReferencedTransactionFullHash() != null) {
/* 413:367 */       localJSONObject1.put("referencedTransactionFullHash", paramTransaction.getReferencedTransactionFullHash());
/* 414:    */     }
/* 415:369 */     byte[] arrayOfByte = Convert.emptyToNull(paramTransaction.getSignature());
/* 416:370 */     if (arrayOfByte != null)
/* 417:    */     {
/* 418:371 */       localJSONObject1.put("signature", Convert.toHexString(arrayOfByte));
/* 419:372 */       localJSONObject1.put("signatureHash", Convert.toHexString(Crypto.sha256().digest(arrayOfByte)));
/* 420:373 */       localJSONObject1.put("fullHash", paramTransaction.getFullHash());
/* 421:374 */       localJSONObject1.put("transaction", paramTransaction.getStringId());
/* 422:    */     }
/* 423:376 */     else if (!paramTransaction.getType().isSigned())
/* 424:    */     {
/* 425:377 */       localJSONObject1.put("fullHash", paramTransaction.getFullHash());
/* 426:378 */       localJSONObject1.put("transaction", paramTransaction.getStringId());
/* 427:    */     }
/* 428:380 */     JSONObject localJSONObject2 = new JSONObject();
/* 429:381 */     for (Appendix localAppendix : paramTransaction.getAppendages()) {
/* 430:382 */       localJSONObject2.putAll(localAppendix.getJSONObject());
/* 431:    */     }
/* 432:384 */     if (!localJSONObject2.isEmpty())
/* 433:    */     {
/* 434:385 */       modifyAttachmentJSON(localJSONObject2);
/* 435:386 */       localJSONObject1.put("attachment", localJSONObject2);
/* 436:    */     }
/* 437:388 */     putAccount(localJSONObject1, "sender", paramTransaction.getSenderId());
/* 438:389 */     localJSONObject1.put("height", Integer.valueOf(paramTransaction.getHeight()));
/* 439:390 */     localJSONObject1.put("version", Byte.valueOf(paramTransaction.getVersion()));
/* 440:391 */     if (paramTransaction.getVersion() > 0)
/* 441:    */     {
/* 442:392 */       localJSONObject1.put("ecBlockId", Convert.toUnsignedLong(paramTransaction.getECBlockId()));
/* 443:393 */       localJSONObject1.put("ecBlockHeight", Integer.valueOf(paramTransaction.getECBlockHeight()));
/* 444:    */     }
/* 445:396 */     return localJSONObject1;
/* 446:    */   }
/* 447:    */   
/* 448:    */   public static JSONObject transaction(Transaction paramTransaction)
/* 449:    */   {
/* 450:400 */     JSONObject localJSONObject = unconfirmedTransaction(paramTransaction);
/* 451:401 */     localJSONObject.put("block", Convert.toUnsignedLong(paramTransaction.getBlockId()));
/* 452:402 */     localJSONObject.put("confirmations", Integer.valueOf(Nxt.getBlockchain().getHeight() - paramTransaction.getHeight()));
/* 453:403 */     localJSONObject.put("blockTimestamp", Integer.valueOf(paramTransaction.getBlockTimestamp()));
/* 454:404 */     return localJSONObject;
/* 455:    */   }
/* 456:    */   
/* 457:    */   private static void modifyAttachmentJSON(JSONObject paramJSONObject)
/* 458:    */   {
/* 459:409 */     Long localLong1 = (Long)paramJSONObject.remove("quantityQNT");
/* 460:410 */     if (localLong1 != null) {
/* 461:411 */       paramJSONObject.put("quantityQNT", String.valueOf(localLong1));
/* 462:    */     }
/* 463:413 */     Long localLong2 = (Long)paramJSONObject.remove("priceNQT");
/* 464:414 */     if (localLong2 != null) {
/* 465:415 */       paramJSONObject.put("priceNQT", String.valueOf(localLong2));
/* 466:    */     }
/* 467:417 */     Long localLong3 = (Long)paramJSONObject.remove("discountNQT");
/* 468:418 */     if (localLong3 != null) {
/* 469:419 */       paramJSONObject.put("discountNQT", String.valueOf(localLong3));
/* 470:    */     }
/* 471:421 */     Long localLong4 = (Long)paramJSONObject.remove("refundNQT");
/* 472:422 */     if (localLong4 != null) {
/* 473:423 */       paramJSONObject.put("refundNQT", String.valueOf(localLong4));
/* 474:    */     }
/* 475:    */   }
/* 476:    */   
/* 477:    */   static void putAccount(JSONObject paramJSONObject, String paramString, long paramLong)
/* 478:    */   {
/* 479:428 */     paramJSONObject.put(paramString, Convert.toUnsignedLong(paramLong));
/* 480:429 */     paramJSONObject.put(paramString + "RS", Convert.rsAccount(paramLong));
/* 481:    */   }
/* 482:    */   
/* 483:    */   static JSONObject at(AT paramAT)
/* 484:    */   {
/* 485:433 */     JSONObject localJSONObject = new JSONObject();
/* 486:434 */     ByteBuffer localByteBuffer = ByteBuffer.allocate(8);
/* 487:435 */     localByteBuffer.order(ByteOrder.LITTLE_ENDIAN);
/* 488:    */     
/* 489:437 */     localByteBuffer.put(paramAT.getCreator());
/* 490:438 */     localByteBuffer.clear();
/* 491:439 */     putAccount(localJSONObject, "creator", localByteBuffer.getLong());
/* 492:440 */     localByteBuffer.clear();
/* 493:441 */     localByteBuffer.put(paramAT.getId(), 0, 8);
/* 494:442 */     long l = localByteBuffer.getLong(0);
/* 495:443 */     localJSONObject.put("at", Convert.toUnsignedLong(l));
/* 496:444 */     localJSONObject.put("atRS", Convert.rsAccount(l));
/* 497:445 */     localJSONObject.put("atVersion", Short.valueOf(paramAT.getVersion()));
/* 498:446 */     localJSONObject.put("name", paramAT.getName());
/* 499:447 */     localJSONObject.put("description", paramAT.getDescription());
/* 500:448 */     localJSONObject.put("creator", Convert.toUnsignedLong(AT_API_Helper.getLong(paramAT.getCreator())));
/* 501:449 */     localJSONObject.put("creatorRS", Convert.rsAccount(AT_API_Helper.getLong(paramAT.getCreator())));
/* 502:450 */     localJSONObject.put("machineCode", Convert.toHexString(paramAT.getApCode()));
/* 503:451 */     localJSONObject.put("machineData", Convert.toHexString(paramAT.getApData()));
/* 504:452 */     localJSONObject.put("balanceNQT", Convert.toUnsignedLong(Account.getAccount(l).getBalanceNQT()));
/* 505:453 */     localJSONObject.put("prevBalanceNQT", Convert.toUnsignedLong(paramAT.getP_balance().longValue()));
/* 506:454 */     localJSONObject.put("nextBlock", Integer.valueOf(paramAT.nextHeight()));
/* 507:455 */     localJSONObject.put("frozen", Boolean.valueOf(paramAT.freezeOnSameBalance()));
/* 508:456 */     localJSONObject.put("running", Boolean.valueOf(paramAT.getMachineState().isRunning()));
/* 509:457 */     localJSONObject.put("stopped", Boolean.valueOf(paramAT.getMachineState().isStopped()));
/* 510:458 */     localJSONObject.put("finished", Boolean.valueOf(paramAT.getMachineState().isFinished()));
/* 511:459 */     localJSONObject.put("dead", Boolean.valueOf(paramAT.getMachineState().isDead()));
/* 512:460 */     localJSONObject.put("minActivation", Convert.toUnsignedLong(paramAT.minActivationAmount()));
/* 513:461 */     localJSONObject.put("creationBlock", Integer.valueOf(paramAT.getCreationBlockHeight()));
/* 514:462 */     return localJSONObject;
/* 515:    */   }
/* 516:    */   
/* 517:    */   static JSONObject hex2long(String paramString)
/* 518:    */   {
/* 519:466 */     JSONObject localJSONObject = new JSONObject();
/* 520:467 */     localJSONObject.put("hex2long", paramString);
/* 521:468 */     return localJSONObject;
/* 522:    */   }
/* 523:    */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.http.JSONData
 * JD-Core Version:    0.7.1
 */