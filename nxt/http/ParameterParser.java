/*   1:    */ package nxt.http;
/*   2:    */ 
/*   3:    */ import java.nio.ByteBuffer;
/*   4:    */ import java.nio.ByteOrder;
/*   5:    */ import java.util.ArrayList;
/*   6:    */ import java.util.List;
/*   7:    */ import javax.servlet.http.HttpServletRequest;
/*   8:    */ import nxt.AT;
/*   9:    */ import nxt.Account;
/*  10:    */ import nxt.Alias;
/*  11:    */ import nxt.Asset;
/*  12:    */ import nxt.Blockchain;
/*  13:    */ import nxt.BlockchainProcessor;
/*  14:    */ import nxt.DigitalGoodsStore;
/*  15:    */ import nxt.DigitalGoodsStore.Goods;
/*  16:    */ import nxt.DigitalGoodsStore.Purchase;
/*  17:    */ import nxt.Nxt;
/*  18:    */ import nxt.crypto.Crypto;
/*  19:    */ import nxt.crypto.EncryptedData;
/*  20:    */ import nxt.util.Convert;
/*  21:    */ 
/*  22:    */ final class ParameterParser
/*  23:    */ {
/*  24:    */   static Alias getAlias(HttpServletRequest paramHttpServletRequest)
/*  25:    */     throws ParameterException
/*  26:    */   {
/*  27:    */     long l;
/*  28:    */     try
/*  29:    */     {
/*  30: 35 */       l = Convert.parseUnsignedLong(Convert.emptyToNull(paramHttpServletRequest.getParameter("alias")));
/*  31:    */     }
/*  32:    */     catch (RuntimeException localRuntimeException)
/*  33:    */     {
/*  34: 37 */       throw new ParameterException(JSONResponses.INCORRECT_ALIAS);
/*  35:    */     }
/*  36: 39 */     String str = Convert.emptyToNull(paramHttpServletRequest.getParameter("aliasName"));
/*  37:    */     Alias localAlias;
/*  38: 41 */     if (l != 0L) {
/*  39: 42 */       localAlias = Alias.getAlias(l);
/*  40: 43 */     } else if (str != null) {
/*  41: 44 */       localAlias = Alias.getAlias(str);
/*  42:    */     } else {
/*  43: 46 */       throw new ParameterException(JSONResponses.MISSING_ALIAS_OR_ALIAS_NAME);
/*  44:    */     }
/*  45: 48 */     if (localAlias == null) {
/*  46: 49 */       throw new ParameterException(JSONResponses.UNKNOWN_ALIAS);
/*  47:    */     }
/*  48: 51 */     return localAlias;
/*  49:    */   }
/*  50:    */   
/*  51:    */   static long getAmountNQT(HttpServletRequest paramHttpServletRequest)
/*  52:    */     throws ParameterException
/*  53:    */   {
/*  54: 55 */     String str = Convert.emptyToNull(paramHttpServletRequest.getParameter("amountNQT"));
/*  55: 56 */     if (str == null) {
/*  56: 57 */       throw new ParameterException(JSONResponses.MISSING_AMOUNT);
/*  57:    */     }
/*  58:    */     long l;
/*  59:    */     try
/*  60:    */     {
/*  61: 61 */       l = Long.parseLong(str);
/*  62:    */     }
/*  63:    */     catch (RuntimeException localRuntimeException)
/*  64:    */     {
/*  65: 63 */       throw new ParameterException(JSONResponses.INCORRECT_AMOUNT);
/*  66:    */     }
/*  67: 65 */     if ((l <= 0L) || (l >= 215881280000000000L)) {
/*  68: 66 */       throw new ParameterException(JSONResponses.INCORRECT_AMOUNT);
/*  69:    */     }
/*  70: 68 */     return l;
/*  71:    */   }
/*  72:    */   
/*  73:    */   static long getFeeNQT(HttpServletRequest paramHttpServletRequest)
/*  74:    */     throws ParameterException
/*  75:    */   {
/*  76: 72 */     String str = Convert.emptyToNull(paramHttpServletRequest.getParameter("feeNQT"));
/*  77: 73 */     if (str == null) {
/*  78: 74 */       throw new ParameterException(JSONResponses.MISSING_FEE);
/*  79:    */     }
/*  80:    */     long l;
/*  81:    */     try
/*  82:    */     {
/*  83: 78 */       l = Long.parseLong(str);
/*  84:    */     }
/*  85:    */     catch (RuntimeException localRuntimeException)
/*  86:    */     {
/*  87: 80 */       throw new ParameterException(JSONResponses.INCORRECT_FEE);
/*  88:    */     }
/*  89: 82 */     if ((l < 0L) || (l >= 215881280000000000L)) {
/*  90: 83 */       throw new ParameterException(JSONResponses.INCORRECT_FEE);
/*  91:    */     }
/*  92: 85 */     return l;
/*  93:    */   }
/*  94:    */   
/*  95:    */   static long getPriceNQT(HttpServletRequest paramHttpServletRequest)
/*  96:    */     throws ParameterException
/*  97:    */   {
/*  98: 89 */     String str = Convert.emptyToNull(paramHttpServletRequest.getParameter("priceNQT"));
/*  99: 90 */     if (str == null) {
/* 100: 91 */       throw new ParameterException(JSONResponses.MISSING_PRICE);
/* 101:    */     }
/* 102:    */     long l;
/* 103:    */     try
/* 104:    */     {
/* 105: 95 */       l = Long.parseLong(str);
/* 106:    */     }
/* 107:    */     catch (RuntimeException localRuntimeException)
/* 108:    */     {
/* 109: 97 */       throw new ParameterException(JSONResponses.INCORRECT_PRICE);
/* 110:    */     }
/* 111: 99 */     if ((l <= 0L) || (l > 215881280000000000L)) {
/* 112:100 */       throw new ParameterException(JSONResponses.INCORRECT_PRICE);
/* 113:    */     }
/* 114:102 */     return l;
/* 115:    */   }
/* 116:    */   
/* 117:    */   static Asset getAsset(HttpServletRequest paramHttpServletRequest)
/* 118:    */     throws ParameterException
/* 119:    */   {
/* 120:106 */     String str = Convert.emptyToNull(paramHttpServletRequest.getParameter("asset"));
/* 121:107 */     if (str == null) {
/* 122:108 */       throw new ParameterException(JSONResponses.MISSING_ASSET);
/* 123:    */     }
/* 124:    */     Asset localAsset;
/* 125:    */     try
/* 126:    */     {
/* 127:112 */       long l = Convert.parseUnsignedLong(str);
/* 128:113 */       localAsset = Asset.getAsset(l);
/* 129:    */     }
/* 130:    */     catch (RuntimeException localRuntimeException)
/* 131:    */     {
/* 132:115 */       throw new ParameterException(JSONResponses.INCORRECT_ASSET);
/* 133:    */     }
/* 134:117 */     if (localAsset == null) {
/* 135:118 */       throw new ParameterException(JSONResponses.UNKNOWN_ASSET);
/* 136:    */     }
/* 137:120 */     return localAsset;
/* 138:    */   }
/* 139:    */   
/* 140:    */   static long getQuantityQNT(HttpServletRequest paramHttpServletRequest)
/* 141:    */     throws ParameterException
/* 142:    */   {
/* 143:124 */     String str = Convert.emptyToNull(paramHttpServletRequest.getParameter("quantityQNT"));
/* 144:125 */     if (str == null) {
/* 145:126 */       throw new ParameterException(JSONResponses.MISSING_QUANTITY);
/* 146:    */     }
/* 147:    */     long l;
/* 148:    */     try
/* 149:    */     {
/* 150:130 */       l = Long.parseLong(str);
/* 151:    */     }
/* 152:    */     catch (RuntimeException localRuntimeException)
/* 153:    */     {
/* 154:132 */       throw new ParameterException(JSONResponses.INCORRECT_QUANTITY);
/* 155:    */     }
/* 156:134 */     if ((l <= 0L) || (l > 100000000000000000L)) {
/* 157:135 */       throw new ParameterException(JSONResponses.INCORRECT_ASSET_QUANTITY);
/* 158:    */     }
/* 159:137 */     return l;
/* 160:    */   }
/* 161:    */   
/* 162:    */   static long getOrderId(HttpServletRequest paramHttpServletRequest)
/* 163:    */     throws ParameterException
/* 164:    */   {
/* 165:141 */     String str = Convert.emptyToNull(paramHttpServletRequest.getParameter("order"));
/* 166:142 */     if (str == null) {
/* 167:143 */       throw new ParameterException(JSONResponses.MISSING_ORDER);
/* 168:    */     }
/* 169:    */     try
/* 170:    */     {
/* 171:146 */       return Convert.parseUnsignedLong(str);
/* 172:    */     }
/* 173:    */     catch (RuntimeException localRuntimeException)
/* 174:    */     {
/* 175:148 */       throw new ParameterException(JSONResponses.INCORRECT_ORDER);
/* 176:    */     }
/* 177:    */   }
/* 178:    */   
/* 179:    */   static DigitalGoodsStore.Goods getGoods(HttpServletRequest paramHttpServletRequest)
/* 180:    */     throws ParameterException
/* 181:    */   {
/* 182:153 */     String str = Convert.emptyToNull(paramHttpServletRequest.getParameter("goods"));
/* 183:154 */     if (str == null) {
/* 184:155 */       throw new ParameterException(JSONResponses.MISSING_GOODS);
/* 185:    */     }
/* 186:    */     try
/* 187:    */     {
/* 188:159 */       long l = Convert.parseUnsignedLong(str);
/* 189:160 */       DigitalGoodsStore.Goods localGoods = DigitalGoodsStore.getGoods(l);
/* 190:161 */       if (localGoods == null) {
/* 191:162 */         throw new ParameterException(JSONResponses.UNKNOWN_GOODS);
/* 192:    */       }
/* 193:164 */       return localGoods;
/* 194:    */     }
/* 195:    */     catch (RuntimeException localRuntimeException)
/* 196:    */     {
/* 197:166 */       throw new ParameterException(JSONResponses.INCORRECT_GOODS);
/* 198:    */     }
/* 199:    */   }
/* 200:    */   
/* 201:    */   static int getGoodsQuantity(HttpServletRequest paramHttpServletRequest)
/* 202:    */     throws ParameterException
/* 203:    */   {
/* 204:171 */     String str = Convert.emptyToNull(paramHttpServletRequest.getParameter("quantity"));
/* 205:    */     try
/* 206:    */     {
/* 207:173 */       int i = Integer.parseInt(str);
/* 208:174 */       if ((i < 0) || (i > 1000000000)) {
/* 209:175 */         throw new ParameterException(JSONResponses.INCORRECT_QUANTITY);
/* 210:    */       }
/* 211:177 */       return i;
/* 212:    */     }
/* 213:    */     catch (NumberFormatException localNumberFormatException)
/* 214:    */     {
/* 215:179 */       throw new ParameterException(JSONResponses.INCORRECT_QUANTITY);
/* 216:    */     }
/* 217:    */   }
/* 218:    */   
/* 219:    */   static EncryptedData getEncryptedMessage(HttpServletRequest paramHttpServletRequest, Account paramAccount)
/* 220:    */     throws ParameterException
/* 221:    */   {
/* 222:184 */     String str1 = Convert.emptyToNull(paramHttpServletRequest.getParameter("encryptedMessageData"));
/* 223:185 */     String str2 = Convert.emptyToNull(paramHttpServletRequest.getParameter("encryptedMessageNonce"));
/* 224:186 */     if ((str1 != null) && (str2 != null)) {
/* 225:    */       try
/* 226:    */       {
/* 227:188 */         return new EncryptedData(Convert.parseHexString(str1), Convert.parseHexString(str2));
/* 228:    */       }
/* 229:    */       catch (RuntimeException localRuntimeException1)
/* 230:    */       {
/* 231:190 */         throw new ParameterException(JSONResponses.INCORRECT_ENCRYPTED_MESSAGE);
/* 232:    */       }
/* 233:    */     }
/* 234:193 */     String str3 = Convert.emptyToNull(paramHttpServletRequest.getParameter("messageToEncrypt"));
/* 235:194 */     if (str3 == null) {
/* 236:195 */       return null;
/* 237:    */     }
/* 238:197 */     if (paramAccount == null) {
/* 239:198 */       throw new ParameterException(JSONResponses.INCORRECT_RECIPIENT);
/* 240:    */     }
/* 241:200 */     String str4 = getSecretPhrase(paramHttpServletRequest);
/* 242:201 */     int i = !"false".equalsIgnoreCase(paramHttpServletRequest.getParameter("messageToEncryptIsText")) ? 1 : 0;
/* 243:    */     try
/* 244:    */     {
/* 245:203 */       byte[] arrayOfByte = i != 0 ? Convert.toBytes(str3) : Convert.parseHexString(str3);
/* 246:204 */       return paramAccount.encryptTo(arrayOfByte, str4);
/* 247:    */     }
/* 248:    */     catch (RuntimeException localRuntimeException2)
/* 249:    */     {
/* 250:206 */       throw new ParameterException(JSONResponses.INCORRECT_PLAIN_MESSAGE);
/* 251:    */     }
/* 252:    */   }
/* 253:    */   
/* 254:    */   static EncryptedData getEncryptToSelfMessage(HttpServletRequest paramHttpServletRequest)
/* 255:    */     throws ParameterException
/* 256:    */   {
/* 257:211 */     String str1 = Convert.emptyToNull(paramHttpServletRequest.getParameter("encryptToSelfMessageData"));
/* 258:212 */     String str2 = Convert.emptyToNull(paramHttpServletRequest.getParameter("encryptToSelfMessageNonce"));
/* 259:213 */     if ((str1 != null) && (str2 != null)) {
/* 260:    */       try
/* 261:    */       {
/* 262:215 */         return new EncryptedData(Convert.parseHexString(str1), Convert.parseHexString(str2));
/* 263:    */       }
/* 264:    */       catch (RuntimeException localRuntimeException1)
/* 265:    */       {
/* 266:217 */         throw new ParameterException(JSONResponses.INCORRECT_ENCRYPTED_MESSAGE);
/* 267:    */       }
/* 268:    */     }
/* 269:220 */     String str3 = Convert.emptyToNull(paramHttpServletRequest.getParameter("messageToEncryptToSelf"));
/* 270:221 */     if (str3 == null) {
/* 271:222 */       return null;
/* 272:    */     }
/* 273:224 */     String str4 = getSecretPhrase(paramHttpServletRequest);
/* 274:225 */     Account localAccount = Account.getAccount(Crypto.getPublicKey(str4));
/* 275:226 */     int i = !"false".equalsIgnoreCase(paramHttpServletRequest.getParameter("messageToEncryptToSelfIsText")) ? 1 : 0;
/* 276:    */     try
/* 277:    */     {
/* 278:228 */       byte[] arrayOfByte = i != 0 ? Convert.toBytes(str3) : Convert.parseHexString(str3);
/* 279:229 */       return localAccount.encryptTo(arrayOfByte, str4);
/* 280:    */     }
/* 281:    */     catch (RuntimeException localRuntimeException2)
/* 282:    */     {
/* 283:231 */       throw new ParameterException(JSONResponses.INCORRECT_PLAIN_MESSAGE);
/* 284:    */     }
/* 285:    */   }
/* 286:    */   
/* 287:    */   static EncryptedData getEncryptedGoods(HttpServletRequest paramHttpServletRequest)
/* 288:    */     throws ParameterException
/* 289:    */   {
/* 290:236 */     String str1 = Convert.emptyToNull(paramHttpServletRequest.getParameter("goodsData"));
/* 291:237 */     String str2 = Convert.emptyToNull(paramHttpServletRequest.getParameter("goodsNonce"));
/* 292:238 */     if ((str1 != null) && (str2 != null)) {
/* 293:    */       try
/* 294:    */       {
/* 295:240 */         return new EncryptedData(Convert.parseHexString(str1), Convert.parseHexString(str2));
/* 296:    */       }
/* 297:    */       catch (RuntimeException localRuntimeException)
/* 298:    */       {
/* 299:242 */         throw new ParameterException(JSONResponses.INCORRECT_DGS_ENCRYPTED_GOODS);
/* 300:    */       }
/* 301:    */     }
/* 302:245 */     return null;
/* 303:    */   }
/* 304:    */   
/* 305:    */   static DigitalGoodsStore.Purchase getPurchase(HttpServletRequest paramHttpServletRequest)
/* 306:    */     throws ParameterException
/* 307:    */   {
/* 308:249 */     String str = Convert.emptyToNull(paramHttpServletRequest.getParameter("purchase"));
/* 309:250 */     if (str == null) {
/* 310:251 */       throw new ParameterException(JSONResponses.MISSING_PURCHASE);
/* 311:    */     }
/* 312:    */     try
/* 313:    */     {
/* 314:254 */       DigitalGoodsStore.Purchase localPurchase = DigitalGoodsStore.getPurchase(Convert.parseUnsignedLong(str));
/* 315:255 */       if (localPurchase == null) {
/* 316:256 */         throw new ParameterException(JSONResponses.INCORRECT_PURCHASE);
/* 317:    */       }
/* 318:258 */       return localPurchase;
/* 319:    */     }
/* 320:    */     catch (RuntimeException localRuntimeException)
/* 321:    */     {
/* 322:260 */       throw new ParameterException(JSONResponses.INCORRECT_PURCHASE);
/* 323:    */     }
/* 324:    */   }
/* 325:    */   
/* 326:    */   static String getSecretPhrase(HttpServletRequest paramHttpServletRequest)
/* 327:    */     throws ParameterException
/* 328:    */   {
/* 329:265 */     String str = Convert.emptyToNull(paramHttpServletRequest.getParameter("secretPhrase"));
/* 330:266 */     if (str == null) {
/* 331:267 */       throw new ParameterException(JSONResponses.MISSING_SECRET_PHRASE);
/* 332:    */     }
/* 333:269 */     return str;
/* 334:    */   }
/* 335:    */   
/* 336:    */   static Account getSenderAccount(HttpServletRequest paramHttpServletRequest)
/* 337:    */     throws ParameterException
/* 338:    */   {
/* 339:274 */     String str1 = Convert.emptyToNull(paramHttpServletRequest.getParameter("secretPhrase"));
/* 340:275 */     String str2 = Convert.emptyToNull(paramHttpServletRequest.getParameter("publicKey"));
/* 341:    */     Account localAccount;
/* 342:276 */     if (str1 != null) {
/* 343:277 */       localAccount = Account.getAccount(Crypto.getPublicKey(str1));
/* 344:278 */     } else if (str2 != null) {
/* 345:    */       try
/* 346:    */       {
/* 347:280 */         localAccount = Account.getAccount(Convert.parseHexString(str2));
/* 348:    */       }
/* 349:    */       catch (RuntimeException localRuntimeException)
/* 350:    */       {
/* 351:282 */         throw new ParameterException(JSONResponses.INCORRECT_PUBLIC_KEY);
/* 352:    */       }
/* 353:    */     } else {
/* 354:285 */       throw new ParameterException(JSONResponses.MISSING_SECRET_PHRASE_OR_PUBLIC_KEY);
/* 355:    */     }
/* 356:287 */     if (localAccount == null) {
/* 357:288 */       throw new ParameterException(JSONResponses.UNKNOWN_ACCOUNT);
/* 358:    */     }
/* 359:290 */     return localAccount;
/* 360:    */   }
/* 361:    */   
/* 362:    */   static Account getAccount(HttpServletRequest paramHttpServletRequest)
/* 363:    */     throws ParameterException
/* 364:    */   {
/* 365:294 */     String str = Convert.emptyToNull(paramHttpServletRequest.getParameter("account"));
/* 366:295 */     if (str == null) {
/* 367:296 */       throw new ParameterException(JSONResponses.MISSING_ACCOUNT);
/* 368:    */     }
/* 369:    */     try
/* 370:    */     {
/* 371:299 */       Account localAccount = Account.getAccount(Convert.parseAccountId(str));
/* 372:300 */       if (localAccount == null) {
/* 373:301 */         throw new ParameterException(JSONResponses.UNKNOWN_ACCOUNT);
/* 374:    */       }
/* 375:303 */       return localAccount;
/* 376:    */     }
/* 377:    */     catch (RuntimeException localRuntimeException)
/* 378:    */     {
/* 379:305 */       throw new ParameterException(JSONResponses.INCORRECT_ACCOUNT);
/* 380:    */     }
/* 381:    */   }
/* 382:    */   
/* 383:    */   static List<Account> getAccounts(HttpServletRequest paramHttpServletRequest)
/* 384:    */     throws ParameterException
/* 385:    */   {
/* 386:310 */     String[] arrayOfString1 = paramHttpServletRequest.getParameterValues("account");
/* 387:311 */     if ((arrayOfString1 == null) || (arrayOfString1.length == 0)) {
/* 388:312 */       throw new ParameterException(JSONResponses.MISSING_ACCOUNT);
/* 389:    */     }
/* 390:314 */     ArrayList localArrayList = new ArrayList();
/* 391:315 */     for (String str : arrayOfString1) {
/* 392:316 */       if ((str != null) && (!str.equals(""))) {
/* 393:    */         try
/* 394:    */         {
/* 395:320 */           Account localAccount = Account.getAccount(Convert.parseAccountId(str));
/* 396:321 */           if (localAccount == null) {
/* 397:322 */             throw new ParameterException(JSONResponses.UNKNOWN_ACCOUNT);
/* 398:    */           }
/* 399:324 */           localArrayList.add(localAccount);
/* 400:    */         }
/* 401:    */         catch (RuntimeException localRuntimeException)
/* 402:    */         {
/* 403:326 */           throw new ParameterException(JSONResponses.INCORRECT_ACCOUNT);
/* 404:    */         }
/* 405:    */       }
/* 406:    */     }
/* 407:329 */     return localArrayList;
/* 408:    */   }
/* 409:    */   
/* 410:    */   static int getTimestamp(HttpServletRequest paramHttpServletRequest)
/* 411:    */     throws ParameterException
/* 412:    */   {
/* 413:333 */     String str = Convert.emptyToNull(paramHttpServletRequest.getParameter("timestamp"));
/* 414:334 */     if (str == null) {
/* 415:335 */       return 0;
/* 416:    */     }
/* 417:    */     int i;
/* 418:    */     try
/* 419:    */     {
/* 420:339 */       i = Integer.parseInt(str);
/* 421:    */     }
/* 422:    */     catch (NumberFormatException localNumberFormatException)
/* 423:    */     {
/* 424:341 */       throw new ParameterException(JSONResponses.INCORRECT_TIMESTAMP);
/* 425:    */     }
/* 426:343 */     if (i < 0) {
/* 427:344 */       throw new ParameterException(JSONResponses.INCORRECT_TIMESTAMP);
/* 428:    */     }
/* 429:346 */     return i;
/* 430:    */   }
/* 431:    */   
/* 432:    */   static long getRecipientId(HttpServletRequest paramHttpServletRequest)
/* 433:    */     throws ParameterException
/* 434:    */   {
/* 435:350 */     String str = Convert.emptyToNull(paramHttpServletRequest.getParameter("recipient"));
/* 436:351 */     if ((str == null) || ("0".equals(str))) {
/* 437:352 */       throw new ParameterException(JSONResponses.MISSING_RECIPIENT);
/* 438:    */     }
/* 439:    */     long l;
/* 440:    */     try
/* 441:    */     {
/* 442:356 */       l = Convert.parseAccountId(str);
/* 443:    */     }
/* 444:    */     catch (RuntimeException localRuntimeException)
/* 445:    */     {
/* 446:358 */       throw new ParameterException(JSONResponses.INCORRECT_RECIPIENT);
/* 447:    */     }
/* 448:360 */     if (l == 0L) {
/* 449:361 */       throw new ParameterException(JSONResponses.INCORRECT_RECIPIENT);
/* 450:    */     }
/* 451:363 */     return l;
/* 452:    */   }
/* 453:    */   
/* 454:    */   static long getSellerId(HttpServletRequest paramHttpServletRequest)
/* 455:    */     throws ParameterException
/* 456:    */   {
/* 457:367 */     String str = Convert.emptyToNull(paramHttpServletRequest.getParameter("seller"));
/* 458:    */     try
/* 459:    */     {
/* 460:369 */       return Convert.parseAccountId(str);
/* 461:    */     }
/* 462:    */     catch (RuntimeException localRuntimeException)
/* 463:    */     {
/* 464:371 */       throw new ParameterException(JSONResponses.INCORRECT_RECIPIENT);
/* 465:    */     }
/* 466:    */   }
/* 467:    */   
/* 468:    */   static long getBuyerId(HttpServletRequest paramHttpServletRequest)
/* 469:    */     throws ParameterException
/* 470:    */   {
/* 471:376 */     String str = Convert.emptyToNull(paramHttpServletRequest.getParameter("buyer"));
/* 472:    */     try
/* 473:    */     {
/* 474:378 */       return Convert.parseAccountId(str);
/* 475:    */     }
/* 476:    */     catch (RuntimeException localRuntimeException)
/* 477:    */     {
/* 478:380 */       throw new ParameterException(JSONResponses.INCORRECT_RECIPIENT);
/* 479:    */     }
/* 480:    */   }
/* 481:    */   
/* 482:    */   static int getFirstIndex(HttpServletRequest paramHttpServletRequest)
/* 483:    */   {
/* 484:    */     int i;
/* 485:    */     try
/* 486:    */     {
/* 487:387 */       i = Integer.parseInt(paramHttpServletRequest.getParameter("firstIndex"));
/* 488:388 */       if (i < 0) {
/* 489:389 */         return 0;
/* 490:    */       }
/* 491:    */     }
/* 492:    */     catch (NumberFormatException localNumberFormatException)
/* 493:    */     {
/* 494:392 */       return 0;
/* 495:    */     }
/* 496:394 */     return i;
/* 497:    */   }
/* 498:    */   
/* 499:    */   static int getLastIndex(HttpServletRequest paramHttpServletRequest)
/* 500:    */   {
/* 501:    */     int i;
/* 502:    */     try
/* 503:    */     {
/* 504:400 */       i = Integer.parseInt(paramHttpServletRequest.getParameter("lastIndex"));
/* 505:401 */       if (i < 0) {
/* 506:402 */         return Integer.MAX_VALUE;
/* 507:    */       }
/* 508:    */     }
/* 509:    */     catch (NumberFormatException localNumberFormatException)
/* 510:    */     {
/* 511:405 */       return Integer.MAX_VALUE;
/* 512:    */     }
/* 513:407 */     return i;
/* 514:    */   }
/* 515:    */   
/* 516:    */   static int getNumberOfConfirmations(HttpServletRequest paramHttpServletRequest)
/* 517:    */     throws ParameterException
/* 518:    */   {
/* 519:411 */     String str = Convert.emptyToNull(paramHttpServletRequest.getParameter("numberOfConfirmations"));
/* 520:412 */     if (str != null) {
/* 521:    */       try
/* 522:    */       {
/* 523:414 */         int i = Integer.parseInt(str);
/* 524:415 */         if (i <= Nxt.getBlockchain().getHeight()) {
/* 525:416 */           return i;
/* 526:    */         }
/* 527:418 */         throw new ParameterException(JSONResponses.INCORRECT_NUMBER_OF_CONFIRMATIONS);
/* 528:    */       }
/* 529:    */       catch (NumberFormatException localNumberFormatException)
/* 530:    */       {
/* 531:420 */         throw new ParameterException(JSONResponses.INCORRECT_NUMBER_OF_CONFIRMATIONS);
/* 532:    */       }
/* 533:    */     }
/* 534:423 */     return 0;
/* 535:    */   }
/* 536:    */   
/* 537:    */   static int getHeight(HttpServletRequest paramHttpServletRequest)
/* 538:    */     throws ParameterException
/* 539:    */   {
/* 540:427 */     String str = Convert.emptyToNull(paramHttpServletRequest.getParameter("height"));
/* 541:428 */     if (str != null) {
/* 542:    */       try
/* 543:    */       {
/* 544:430 */         int i = Integer.parseInt(str);
/* 545:431 */         if ((i < 0) || (i > Nxt.getBlockchain().getHeight())) {
/* 546:432 */           throw new ParameterException(JSONResponses.INCORRECT_HEIGHT);
/* 547:    */         }
/* 548:434 */         if (i < Nxt.getBlockchainProcessor().getMinRollbackHeight()) {
/* 549:435 */           throw new ParameterException(JSONResponses.HEIGHT_NOT_AVAILABLE);
/* 550:    */         }
/* 551:437 */         return i;
/* 552:    */       }
/* 553:    */       catch (NumberFormatException localNumberFormatException)
/* 554:    */       {
/* 555:439 */         throw new ParameterException(JSONResponses.INCORRECT_HEIGHT);
/* 556:    */       }
/* 557:    */     }
/* 558:442 */     return -1;
/* 559:    */   }
/* 560:    */   
/* 561:    */   /* Error */
/* 562:    */   static nxt.Transaction parseTransaction(String paramString1, String paramString2)
/* 563:    */     throws ParameterException
/* 564:    */   {
/* 565:    */     // Byte code:
/* 566:    */     //   0: aload_0
/* 567:    */     //   1: ifnonnull +18 -> 19
/* 568:    */     //   4: aload_1
/* 569:    */     //   5: ifnonnull +14 -> 19
/* 570:    */     //   8: new 6	nxt/http/ParameterException
/* 571:    */     //   11: dup
/* 572:    */     //   12: getstatic 113	nxt/http/JSONResponses:MISSING_TRANSACTION_BYTES_OR_JSON	Lorg/json/simple/JSONStreamAware;
/* 573:    */     //   15: invokespecial 8	nxt/http/ParameterException:<init>	(Lorg/json/simple/JSONStreamAware;)V
/* 574:    */     //   18: athrow
/* 575:    */     //   19: aload_0
/* 576:    */     //   20: ifnull +84 -> 104
/* 577:    */     //   23: aload_0
/* 578:    */     //   24: invokestatic 52	nxt/util/Convert:parseHexString	(Ljava/lang/String;)[B
/* 579:    */     //   27: astore_2
/* 580:    */     //   28: invokestatic 114	nxt/Nxt:getTransactionProcessor	()Lnxt/TransactionProcessor;
/* 581:    */     //   31: aload_2
/* 582:    */     //   32: invokeinterface 115 2 0
/* 583:    */     //   37: areturn
/* 584:    */     //   38: astore_2
/* 585:    */     //   39: aload_2
/* 586:    */     //   40: invokevirtual 117	java/lang/Exception:getMessage	()Ljava/lang/String;
/* 587:    */     //   43: aload_2
/* 588:    */     //   44: invokestatic 118	nxt/util/Logger:logDebugMessage	(Ljava/lang/String;Ljava/lang/Exception;)V
/* 589:    */     //   47: new 119	org/json/simple/JSONObject
/* 590:    */     //   50: dup
/* 591:    */     //   51: invokespecial 120	org/json/simple/JSONObject:<init>	()V
/* 592:    */     //   54: astore_3
/* 593:    */     //   55: aload_3
/* 594:    */     //   56: ldc 121
/* 595:    */     //   58: iconst_4
/* 596:    */     //   59: invokestatic 122	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
/* 597:    */     //   62: invokevirtual 123	org/json/simple/JSONObject:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
/* 598:    */     //   65: pop
/* 599:    */     //   66: aload_3
/* 600:    */     //   67: ldc 124
/* 601:    */     //   69: new 125	java/lang/StringBuilder
/* 602:    */     //   72: dup
/* 603:    */     //   73: invokespecial 126	java/lang/StringBuilder:<init>	()V
/* 604:    */     //   76: ldc 127
/* 605:    */     //   78: invokevirtual 128	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 606:    */     //   81: aload_2
/* 607:    */     //   82: invokevirtual 129	java/lang/Exception:toString	()Ljava/lang/String;
/* 608:    */     //   85: invokevirtual 128	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 609:    */     //   88: invokevirtual 130	java/lang/StringBuilder:toString	()Ljava/lang/String;
/* 610:    */     //   91: invokevirtual 123	org/json/simple/JSONObject:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
/* 611:    */     //   94: pop
/* 612:    */     //   95: new 6	nxt/http/ParameterException
/* 613:    */     //   98: dup
/* 614:    */     //   99: aload_3
/* 615:    */     //   100: invokespecial 8	nxt/http/ParameterException:<init>	(Lorg/json/simple/JSONStreamAware;)V
/* 616:    */     //   103: athrow
/* 617:    */     //   104: aload_1
/* 618:    */     //   105: invokestatic 131	org/json/simple/JSONValue:parseWithException	(Ljava/lang/String;)Ljava/lang/Object;
/* 619:    */     //   108: checkcast 119	org/json/simple/JSONObject
/* 620:    */     //   111: astore_2
/* 621:    */     //   112: invokestatic 114	nxt/Nxt:getTransactionProcessor	()Lnxt/TransactionProcessor;
/* 622:    */     //   115: aload_2
/* 623:    */     //   116: invokeinterface 132 2 0
/* 624:    */     //   121: areturn
/* 625:    */     //   122: astore_2
/* 626:    */     //   123: aload_2
/* 627:    */     //   124: invokevirtual 117	java/lang/Exception:getMessage	()Ljava/lang/String;
/* 628:    */     //   127: aload_2
/* 629:    */     //   128: invokestatic 118	nxt/util/Logger:logDebugMessage	(Ljava/lang/String;Ljava/lang/Exception;)V
/* 630:    */     //   131: new 119	org/json/simple/JSONObject
/* 631:    */     //   134: dup
/* 632:    */     //   135: invokespecial 120	org/json/simple/JSONObject:<init>	()V
/* 633:    */     //   138: astore_3
/* 634:    */     //   139: aload_3
/* 635:    */     //   140: ldc 121
/* 636:    */     //   142: iconst_4
/* 637:    */     //   143: invokestatic 122	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
/* 638:    */     //   146: invokevirtual 123	org/json/simple/JSONObject:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
/* 639:    */     //   149: pop
/* 640:    */     //   150: aload_3
/* 641:    */     //   151: ldc 124
/* 642:    */     //   153: new 125	java/lang/StringBuilder
/* 643:    */     //   156: dup
/* 644:    */     //   157: invokespecial 126	java/lang/StringBuilder:<init>	()V
/* 645:    */     //   160: ldc 134
/* 646:    */     //   162: invokevirtual 128	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 647:    */     //   165: aload_2
/* 648:    */     //   166: invokevirtual 129	java/lang/Exception:toString	()Ljava/lang/String;
/* 649:    */     //   169: invokevirtual 128	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 650:    */     //   172: invokevirtual 130	java/lang/StringBuilder:toString	()Ljava/lang/String;
/* 651:    */     //   175: invokevirtual 123	org/json/simple/JSONObject:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
/* 652:    */     //   178: pop
/* 653:    */     //   179: new 6	nxt/http/ParameterException
/* 654:    */     //   182: dup
/* 655:    */     //   183: aload_3
/* 656:    */     //   184: invokespecial 8	nxt/http/ParameterException:<init>	(Lorg/json/simple/JSONStreamAware;)V
/* 657:    */     //   187: athrow
/* 658:    */     // Line number table:
/* 659:    */     //   Java source line #446	-> byte code offset #0
/* 660:    */     //   Java source line #447	-> byte code offset #8
/* 661:    */     //   Java source line #449	-> byte code offset #19
/* 662:    */     //   Java source line #451	-> byte code offset #23
/* 663:    */     //   Java source line #452	-> byte code offset #28
/* 664:    */     //   Java source line #453	-> byte code offset #38
/* 665:    */     //   Java source line #454	-> byte code offset #39
/* 666:    */     //   Java source line #455	-> byte code offset #47
/* 667:    */     //   Java source line #456	-> byte code offset #55
/* 668:    */     //   Java source line #457	-> byte code offset #66
/* 669:    */     //   Java source line #458	-> byte code offset #95
/* 670:    */     //   Java source line #462	-> byte code offset #104
/* 671:    */     //   Java source line #463	-> byte code offset #112
/* 672:    */     //   Java source line #464	-> byte code offset #122
/* 673:    */     //   Java source line #465	-> byte code offset #123
/* 674:    */     //   Java source line #466	-> byte code offset #131
/* 675:    */     //   Java source line #467	-> byte code offset #139
/* 676:    */     //   Java source line #468	-> byte code offset #150
/* 677:    */     //   Java source line #469	-> byte code offset #179
/* 678:    */     // Local variable table:
/* 679:    */     //   start	length	slot	name	signature
/* 680:    */     //   0	188	0	paramString1	String
/* 681:    */     //   0	188	1	paramString2	String
/* 682:    */     //   27	5	2	arrayOfByte	byte[]
/* 683:    */     //   38	44	2	localValidationException1	nxt.NxtException.ValidationException
/* 684:    */     //   111	5	2	localJSONObject1	org.json.simple.JSONObject
/* 685:    */     //   122	44	2	localValidationException2	nxt.NxtException.ValidationException
/* 686:    */     //   54	130	3	localJSONObject2	org.json.simple.JSONObject
/* 687:    */     // Exception table:
/* 688:    */     //   from	to	target	type
/* 689:    */     //   23	37	38	nxt/NxtException$ValidationException
/* 690:    */     //   23	37	38	java/lang/RuntimeException
/* 691:    */     //   104	121	122	nxt/NxtException$ValidationException
/* 692:    */     //   104	121	122	java/lang/RuntimeException
/* 693:    */     //   104	121	122	org/json/simple/parser/ParseException
/* 694:    */   }
/* 695:    */   
/* 696:    */   static AT getAT(HttpServletRequest paramHttpServletRequest)
/* 697:    */     throws ParameterException
/* 698:    */   {
/* 699:480 */     String str = Convert.emptyToNull(paramHttpServletRequest.getParameter("at"));
/* 700:481 */     if (str == null) {
/* 701:482 */       throw new ParameterException(JSONResponses.MISSING_AT);
/* 702:    */     }
/* 703:    */     AT localAT;
/* 704:    */     try
/* 705:    */     {
/* 706:486 */       Long localLong = Long.valueOf(Convert.parseUnsignedLong(str));
/* 707:487 */       localAT = AT.getAT(localLong);
/* 708:    */     }
/* 709:    */     catch (RuntimeException localRuntimeException)
/* 710:    */     {
/* 711:489 */       throw new ParameterException(JSONResponses.INCORRECT_AT);
/* 712:    */     }
/* 713:491 */     if (localAT == null) {
/* 714:492 */       throw new ParameterException(JSONResponses.UNKNOWN_AT);
/* 715:    */     }
/* 716:494 */     return localAT;
/* 717:    */   }
/* 718:    */   
/* 719:    */   public static byte[] getCreationBytes(HttpServletRequest paramHttpServletRequest)
/* 720:    */     throws ParameterException
/* 721:    */   {
/* 722:    */     try
/* 723:    */     {
/* 724:499 */       return Convert.parseHexString(paramHttpServletRequest.getParameter("creationBytes"));
/* 725:    */     }
/* 726:    */     catch (RuntimeException localRuntimeException)
/* 727:    */     {
/* 728:501 */       throw new ParameterException(JSONResponses.INCORRECT_CREATION_BYTES);
/* 729:    */     }
/* 730:    */   }
/* 731:    */   
/* 732:    */   public static String getATLong(HttpServletRequest paramHttpServletRequest)
/* 733:    */   {
/* 734:509 */     String str1 = paramHttpServletRequest.getParameter("hexString");
/* 735:510 */     ByteBuffer localByteBuffer = ByteBuffer.allocate(8);
/* 736:511 */     localByteBuffer.order(ByteOrder.LITTLE_ENDIAN);
/* 737:512 */     localByteBuffer.put(Convert.parseHexString(str1));
/* 738:    */     
/* 739:514 */     String str2 = Convert.toUnsignedLong(localByteBuffer.getLong(0));
/* 740:515 */     return str2;
/* 741:    */   }
/* 742:    */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.http.ParameterParser
 * JD-Core Version:    0.7.1
 */