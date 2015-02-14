/*   1:    */ package nxt;
/*   2:    */ 
/*   3:    */ import java.sql.Connection;
/*   4:    */ import java.sql.PreparedStatement;
/*   5:    */ import java.sql.ResultSet;
/*   6:    */ import java.sql.SQLException;
/*   7:    */ import java.util.ArrayList;
/*   8:    */ import java.util.List;
/*   9:    */ import nxt.crypto.EncryptedData;
/*  10:    */ import nxt.db.DbClause;
/*  11:    */ import nxt.db.DbClause.LongClause;
/*  12:    */ import nxt.db.DbIterator;
/*  13:    */ import nxt.db.DbKey;
/*  14:    */ import nxt.db.DbKey.Factory;
/*  15:    */ import nxt.db.DbKey.LongKeyFactory;
/*  16:    */ import nxt.db.VersionedEntityDbTable;
/*  17:    */ import nxt.db.VersionedValuesDbTable;
/*  18:    */ import nxt.util.Convert;
/*  19:    */ import nxt.util.Listener;
/*  20:    */ import nxt.util.Listeners;
/*  21:    */ 
/*  22:    */ public final class DigitalGoodsStore
/*  23:    */ {
/*  24:    */   public static enum Event
/*  25:    */   {
/*  26: 24 */     GOODS_LISTED,  GOODS_DELISTED,  GOODS_PRICE_CHANGE,  GOODS_QUANTITY_CHANGE,  PURCHASE,  DELIVERY,  REFUND,  FEEDBACK;
/*  27:    */     
/*  28:    */     private Event() {}
/*  29:    */   }
/*  30:    */   
/*  31:    */   static
/*  32:    */   {
/*  33: 29 */     Nxt.getBlockchainProcessor().addListener(new Listener()
/*  34:    */     {
/*  35:    */       public void notify(Block paramAnonymousBlock)
/*  36:    */       {
/*  37: 32 */         DbIterator localDbIterator = DigitalGoodsStore.getExpiredPendingPurchases(paramAnonymousBlock.getTimestamp());Object localObject1 = null;
/*  38:    */         try
/*  39:    */         {
/*  40: 33 */           while (localDbIterator.hasNext())
/*  41:    */           {
/*  42: 34 */             DigitalGoodsStore.Purchase localPurchase = (DigitalGoodsStore.Purchase)localDbIterator.next();
/*  43: 35 */             Account localAccount = Account.getAccount(localPurchase.getBuyerId());
/*  44: 36 */             localAccount.addToUnconfirmedBalanceNQT(Convert.safeMultiply(localPurchase.getQuantity(), localPurchase.getPriceNQT()));
/*  45: 37 */             DigitalGoodsStore.getGoods(localPurchase.getGoodsId()).changeQuantity(localPurchase.getQuantity());
/*  46: 38 */             localPurchase.setPending(false);
/*  47:    */           }
/*  48:    */         }
/*  49:    */         catch (Throwable localThrowable2)
/*  50:    */         {
/*  51: 32 */           localObject1 = localThrowable2;throw localThrowable2;
/*  52:    */         }
/*  53:    */         finally
/*  54:    */         {
/*  55: 40 */           if (localDbIterator != null) {
/*  56: 40 */             if (localObject1 != null) {
/*  57:    */               try
/*  58:    */               {
/*  59: 40 */                 localDbIterator.close();
/*  60:    */               }
/*  61:    */               catch (Throwable localThrowable3)
/*  62:    */               {
/*  63: 40 */                 ((Throwable)localObject1).addSuppressed(localThrowable3);
/*  64:    */               }
/*  65:    */             } else {
/*  66: 40 */               localDbIterator.close();
/*  67:    */             }
/*  68:    */           }
/*  69:    */         }
/*  70:    */       }
/*  71: 40 */     }, BlockchainProcessor.Event.AFTER_BLOCK_APPLY);
/*  72:    */   }
/*  73:    */   
/*  74: 45 */   private static final Listeners<Goods, Event> goodsListeners = new Listeners();
/*  75: 47 */   private static final Listeners<Purchase, Event> purchaseListeners = new Listeners();
/*  76:    */   
/*  77:    */   public static boolean addGoodsListener(Listener<Goods> paramListener, Event paramEvent)
/*  78:    */   {
/*  79: 50 */     return goodsListeners.addListener(paramListener, paramEvent);
/*  80:    */   }
/*  81:    */   
/*  82:    */   public static boolean removeGoodsListener(Listener<Goods> paramListener, Event paramEvent)
/*  83:    */   {
/*  84: 54 */     return goodsListeners.removeListener(paramListener, paramEvent);
/*  85:    */   }
/*  86:    */   
/*  87:    */   public static boolean addPurchaseListener(Listener<Purchase> paramListener, Event paramEvent)
/*  88:    */   {
/*  89: 58 */     return purchaseListeners.addListener(paramListener, paramEvent);
/*  90:    */   }
/*  91:    */   
/*  92:    */   public static boolean removePurchaseListener(Listener<Purchase> paramListener, Event paramEvent)
/*  93:    */   {
/*  94: 62 */     return purchaseListeners.removeListener(paramListener, paramEvent);
/*  95:    */   }
/*  96:    */   
/*  97:    */   static void init()
/*  98:    */   {
/*  99: 66 */     Goods.init();
/* 100: 67 */     Purchase.init();
/* 101:    */   }
/* 102:    */   
/* 103:    */   public static final class Goods
/* 104:    */   {
/* 105: 72 */     private static final DbKey.LongKeyFactory<Goods> goodsDbKeyFactory = new DbKey.LongKeyFactory("id")
/* 106:    */     {
/* 107:    */       public DbKey newKey(DigitalGoodsStore.Goods paramAnonymousGoods)
/* 108:    */       {
/* 109: 76 */         return paramAnonymousGoods.dbKey;
/* 110:    */       }
/* 111:    */     };
/* 112: 81 */     private static final VersionedEntityDbTable<Goods> goodsTable = new VersionedEntityDbTable("goods", goodsDbKeyFactory)
/* 113:    */     {
/* 114:    */       protected DigitalGoodsStore.Goods load(Connection paramAnonymousConnection, ResultSet paramAnonymousResultSet)
/* 115:    */         throws SQLException
/* 116:    */       {
/* 117: 85 */         return new DigitalGoodsStore.Goods(paramAnonymousResultSet, null);
/* 118:    */       }
/* 119:    */       
/* 120:    */       protected void save(Connection paramAnonymousConnection, DigitalGoodsStore.Goods paramAnonymousGoods)
/* 121:    */         throws SQLException
/* 122:    */       {
/* 123: 90 */         paramAnonymousGoods.save(paramAnonymousConnection);
/* 124:    */       }
/* 125:    */       
/* 126:    */       protected String defaultSort()
/* 127:    */       {
/* 128: 95 */         return " ORDER BY timestamp DESC, id ASC ";
/* 129:    */       }
/* 130:    */     };
/* 131:    */     private final long id;
/* 132:    */     private final DbKey dbKey;
/* 133:    */     private final long sellerId;
/* 134:    */     private final String name;
/* 135:    */     private final String description;
/* 136:    */     private final String tags;
/* 137:    */     private final int timestamp;
/* 138:    */     private int quantity;
/* 139:    */     private long priceNQT;
/* 140:    */     private boolean delisted;
/* 141:    */     
/* 142:    */     static void init() {}
/* 143:    */     
/* 144:    */     private Goods(Transaction paramTransaction, Attachment.DigitalGoodsListing paramDigitalGoodsListing)
/* 145:    */     {
/* 146:115 */       this.id = paramTransaction.getId();
/* 147:116 */       this.dbKey = goodsDbKeyFactory.newKey(this.id);
/* 148:117 */       this.sellerId = paramTransaction.getSenderId();
/* 149:118 */       this.name = paramDigitalGoodsListing.getName();
/* 150:119 */       this.description = paramDigitalGoodsListing.getDescription();
/* 151:120 */       this.tags = paramDigitalGoodsListing.getTags();
/* 152:121 */       this.quantity = paramDigitalGoodsListing.getQuantity();
/* 153:122 */       this.priceNQT = paramDigitalGoodsListing.getPriceNQT();
/* 154:123 */       this.delisted = false;
/* 155:124 */       this.timestamp = paramTransaction.getTimestamp();
/* 156:    */     }
/* 157:    */     
/* 158:    */     private Goods(ResultSet paramResultSet)
/* 159:    */       throws SQLException
/* 160:    */     {
/* 161:128 */       this.id = paramResultSet.getLong("id");
/* 162:129 */       this.dbKey = goodsDbKeyFactory.newKey(this.id);
/* 163:130 */       this.sellerId = paramResultSet.getLong("seller_id");
/* 164:131 */       this.name = paramResultSet.getString("name");
/* 165:132 */       this.description = paramResultSet.getString("description");
/* 166:133 */       this.tags = paramResultSet.getString("tags");
/* 167:134 */       this.quantity = paramResultSet.getInt("quantity");
/* 168:135 */       this.priceNQT = paramResultSet.getLong("price");
/* 169:136 */       this.delisted = paramResultSet.getBoolean("delisted");
/* 170:137 */       this.timestamp = paramResultSet.getInt("timestamp");
/* 171:    */     }
/* 172:    */     
/* 173:    */     private void save(Connection paramConnection)
/* 174:    */       throws SQLException
/* 175:    */     {
/* 176:141 */       PreparedStatement localPreparedStatement = paramConnection.prepareStatement("MERGE INTO goods (id, seller_id, name, description, tags, timestamp, quantity, price, delisted, height, latest) KEY (id, height) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, TRUE)");Object localObject1 = null;
/* 177:    */       try
/* 178:    */       {
/* 179:144 */         int i = 0;
/* 180:145 */         localPreparedStatement.setLong(++i, getId());
/* 181:146 */         localPreparedStatement.setLong(++i, getSellerId());
/* 182:147 */         localPreparedStatement.setString(++i, getName());
/* 183:148 */         localPreparedStatement.setString(++i, getDescription());
/* 184:149 */         localPreparedStatement.setString(++i, getTags());
/* 185:150 */         localPreparedStatement.setInt(++i, getTimestamp());
/* 186:151 */         localPreparedStatement.setInt(++i, getQuantity());
/* 187:152 */         localPreparedStatement.setLong(++i, getPriceNQT());
/* 188:153 */         localPreparedStatement.setBoolean(++i, isDelisted());
/* 189:154 */         localPreparedStatement.setInt(++i, Nxt.getBlockchain().getHeight());
/* 190:155 */         localPreparedStatement.executeUpdate();
/* 191:    */       }
/* 192:    */       catch (Throwable localThrowable2)
/* 193:    */       {
/* 194:141 */         localObject1 = localThrowable2;throw localThrowable2;
/* 195:    */       }
/* 196:    */       finally
/* 197:    */       {
/* 198:156 */         if (localPreparedStatement != null) {
/* 199:156 */           if (localObject1 != null) {
/* 200:    */             try
/* 201:    */             {
/* 202:156 */               localPreparedStatement.close();
/* 203:    */             }
/* 204:    */             catch (Throwable localThrowable3)
/* 205:    */             {
/* 206:156 */               ((Throwable)localObject1).addSuppressed(localThrowable3);
/* 207:    */             }
/* 208:    */           } else {
/* 209:156 */             localPreparedStatement.close();
/* 210:    */           }
/* 211:    */         }
/* 212:    */       }
/* 213:    */     }
/* 214:    */     
/* 215:    */     public long getId()
/* 216:    */     {
/* 217:160 */       return this.id;
/* 218:    */     }
/* 219:    */     
/* 220:    */     public long getSellerId()
/* 221:    */     {
/* 222:164 */       return this.sellerId;
/* 223:    */     }
/* 224:    */     
/* 225:    */     public String getName()
/* 226:    */     {
/* 227:168 */       return this.name;
/* 228:    */     }
/* 229:    */     
/* 230:    */     public String getDescription()
/* 231:    */     {
/* 232:172 */       return this.description;
/* 233:    */     }
/* 234:    */     
/* 235:    */     public String getTags()
/* 236:    */     {
/* 237:176 */       return this.tags;
/* 238:    */     }
/* 239:    */     
/* 240:    */     public int getTimestamp()
/* 241:    */     {
/* 242:180 */       return this.timestamp;
/* 243:    */     }
/* 244:    */     
/* 245:    */     public int getQuantity()
/* 246:    */     {
/* 247:184 */       return this.quantity;
/* 248:    */     }
/* 249:    */     
/* 250:    */     private void changeQuantity(int paramInt)
/* 251:    */     {
/* 252:188 */       this.quantity += paramInt;
/* 253:189 */       if (this.quantity < 0) {
/* 254:190 */         this.quantity = 0;
/* 255:191 */       } else if (this.quantity > 1000000000) {
/* 256:192 */         this.quantity = 1000000000;
/* 257:    */       }
/* 258:194 */       goodsTable.insert(this);
/* 259:    */     }
/* 260:    */     
/* 261:    */     public long getPriceNQT()
/* 262:    */     {
/* 263:198 */       return this.priceNQT;
/* 264:    */     }
/* 265:    */     
/* 266:    */     private void changePrice(long paramLong)
/* 267:    */     {
/* 268:202 */       this.priceNQT = paramLong;
/* 269:203 */       goodsTable.insert(this);
/* 270:    */     }
/* 271:    */     
/* 272:    */     public boolean isDelisted()
/* 273:    */     {
/* 274:207 */       return this.delisted;
/* 275:    */     }
/* 276:    */     
/* 277:    */     private void setDelisted(boolean paramBoolean)
/* 278:    */     {
/* 279:211 */       this.delisted = paramBoolean;
/* 280:212 */       goodsTable.insert(this);
/* 281:    */     }
/* 282:    */   }
/* 283:    */   
/* 284:    */   public static final class Purchase
/* 285:    */   {
/* 286:232 */     private static final DbKey.LongKeyFactory<Purchase> purchaseDbKeyFactory = new DbKey.LongKeyFactory("id")
/* 287:    */     {
/* 288:    */       public DbKey newKey(DigitalGoodsStore.Purchase paramAnonymousPurchase)
/* 289:    */       {
/* 290:236 */         return paramAnonymousPurchase.dbKey;
/* 291:    */       }
/* 292:    */     };
/* 293:241 */     private static final VersionedEntityDbTable<Purchase> purchaseTable = new VersionedEntityDbTable("purchase", purchaseDbKeyFactory)
/* 294:    */     {
/* 295:    */       protected DigitalGoodsStore.Purchase load(Connection paramAnonymousConnection, ResultSet paramAnonymousResultSet)
/* 296:    */         throws SQLException
/* 297:    */       {
/* 298:245 */         return new DigitalGoodsStore.Purchase(paramAnonymousResultSet, null);
/* 299:    */       }
/* 300:    */       
/* 301:    */       protected void save(Connection paramAnonymousConnection, DigitalGoodsStore.Purchase paramAnonymousPurchase)
/* 302:    */         throws SQLException
/* 303:    */       {
/* 304:250 */         paramAnonymousPurchase.save(paramAnonymousConnection);
/* 305:    */       }
/* 306:    */       
/* 307:    */       protected String defaultSort()
/* 308:    */       {
/* 309:255 */         return " ORDER BY timestamp DESC, id ASC ";
/* 310:    */       }
/* 311:    */     };
/* 312:260 */     private static final DbKey.LongKeyFactory<Purchase> feedbackDbKeyFactory = new DbKey.LongKeyFactory("id")
/* 313:    */     {
/* 314:    */       public DbKey newKey(DigitalGoodsStore.Purchase paramAnonymousPurchase)
/* 315:    */       {
/* 316:264 */         return paramAnonymousPurchase.dbKey;
/* 317:    */       }
/* 318:    */     };
/* 319:269 */     private static final VersionedValuesDbTable<Purchase, EncryptedData> feedbackTable = new VersionedValuesDbTable("purchase_feedback", feedbackDbKeyFactory)
/* 320:    */     {
/* 321:    */       protected EncryptedData load(Connection paramAnonymousConnection, ResultSet paramAnonymousResultSet)
/* 322:    */         throws SQLException
/* 323:    */       {
/* 324:273 */         byte[] arrayOfByte1 = paramAnonymousResultSet.getBytes("feedback_data");
/* 325:274 */         byte[] arrayOfByte2 = paramAnonymousResultSet.getBytes("feedback_nonce");
/* 326:275 */         return new EncryptedData(arrayOfByte1, arrayOfByte2);
/* 327:    */       }
/* 328:    */       
/* 329:    */       protected void save(Connection paramAnonymousConnection, DigitalGoodsStore.Purchase paramAnonymousPurchase, EncryptedData paramAnonymousEncryptedData)
/* 330:    */         throws SQLException
/* 331:    */       {
/* 332:280 */         PreparedStatement localPreparedStatement = paramAnonymousConnection.prepareStatement("INSERT INTO purchase_feedback (id, feedback_data, feedback_nonce, height, latest) VALUES (?, ?, ?, ?, TRUE)");Object localObject1 = null;
/* 333:    */         try
/* 334:    */         {
/* 335:282 */           int i = 0;
/* 336:283 */           localPreparedStatement.setLong(++i, paramAnonymousPurchase.getId());
/* 337:284 */           DigitalGoodsStore.setEncryptedData(localPreparedStatement, paramAnonymousEncryptedData, ++i);
/* 338:285 */           i++;
/* 339:286 */           localPreparedStatement.setInt(++i, Nxt.getBlockchain().getHeight());
/* 340:287 */           localPreparedStatement.executeUpdate();
/* 341:    */         }
/* 342:    */         catch (Throwable localThrowable2)
/* 343:    */         {
/* 344:280 */           localObject1 = localThrowable2;throw localThrowable2;
/* 345:    */         }
/* 346:    */         finally
/* 347:    */         {
/* 348:288 */           if (localPreparedStatement != null) {
/* 349:288 */             if (localObject1 != null) {
/* 350:    */               try
/* 351:    */               {
/* 352:288 */                 localPreparedStatement.close();
/* 353:    */               }
/* 354:    */               catch (Throwable localThrowable3)
/* 355:    */               {
/* 356:288 */                 ((Throwable)localObject1).addSuppressed(localThrowable3);
/* 357:    */               }
/* 358:    */             } else {
/* 359:288 */               localPreparedStatement.close();
/* 360:    */             }
/* 361:    */           }
/* 362:    */         }
/* 363:    */       }
/* 364:    */     };
/* 365:293 */     private static final DbKey.LongKeyFactory<Purchase> publicFeedbackDbKeyFactory = new DbKey.LongKeyFactory("id")
/* 366:    */     {
/* 367:    */       public DbKey newKey(DigitalGoodsStore.Purchase paramAnonymousPurchase)
/* 368:    */       {
/* 369:297 */         return paramAnonymousPurchase.dbKey;
/* 370:    */       }
/* 371:    */     };
/* 372:302 */     private static final VersionedValuesDbTable<Purchase, String> publicFeedbackTable = new VersionedValuesDbTable("purchase_public_feedback", publicFeedbackDbKeyFactory)
/* 373:    */     {
/* 374:    */       protected String load(Connection paramAnonymousConnection, ResultSet paramAnonymousResultSet)
/* 375:    */         throws SQLException
/* 376:    */       {
/* 377:306 */         return paramAnonymousResultSet.getString("public_feedback");
/* 378:    */       }
/* 379:    */       
/* 380:    */       protected void save(Connection paramAnonymousConnection, DigitalGoodsStore.Purchase paramAnonymousPurchase, String paramAnonymousString)
/* 381:    */         throws SQLException
/* 382:    */       {
/* 383:311 */         PreparedStatement localPreparedStatement = paramAnonymousConnection.prepareStatement("INSERT INTO purchase_public_feedback (id, public_feedback, height, latest) VALUES (?, ?, ?, TRUE)");Object localObject1 = null;
/* 384:    */         try
/* 385:    */         {
/* 386:313 */           int i = 0;
/* 387:314 */           localPreparedStatement.setLong(++i, paramAnonymousPurchase.getId());
/* 388:315 */           localPreparedStatement.setString(++i, paramAnonymousString);
/* 389:316 */           localPreparedStatement.setInt(++i, Nxt.getBlockchain().getHeight());
/* 390:317 */           localPreparedStatement.executeUpdate();
/* 391:    */         }
/* 392:    */         catch (Throwable localThrowable2)
/* 393:    */         {
/* 394:311 */           localObject1 = localThrowable2;throw localThrowable2;
/* 395:    */         }
/* 396:    */         finally
/* 397:    */         {
/* 398:318 */           if (localPreparedStatement != null) {
/* 399:318 */             if (localObject1 != null) {
/* 400:    */               try
/* 401:    */               {
/* 402:318 */                 localPreparedStatement.close();
/* 403:    */               }
/* 404:    */               catch (Throwable localThrowable3)
/* 405:    */               {
/* 406:318 */                 ((Throwable)localObject1).addSuppressed(localThrowable3);
/* 407:    */               }
/* 408:    */             } else {
/* 409:318 */               localPreparedStatement.close();
/* 410:    */             }
/* 411:    */           }
/* 412:    */         }
/* 413:    */       }
/* 414:    */     };
/* 415:    */     private final long id;
/* 416:    */     private final DbKey dbKey;
/* 417:    */     private final long buyerId;
/* 418:    */     private final long goodsId;
/* 419:    */     private final long sellerId;
/* 420:    */     private final int quantity;
/* 421:    */     private final long priceNQT;
/* 422:    */     private final int deadline;
/* 423:    */     private final EncryptedData note;
/* 424:    */     private final int timestamp;
/* 425:    */     private boolean isPending;
/* 426:    */     private EncryptedData encryptedGoods;
/* 427:    */     private boolean goodsIsText;
/* 428:    */     private EncryptedData refundNote;
/* 429:    */     private boolean hasFeedbackNotes;
/* 430:    */     private List<EncryptedData> feedbackNotes;
/* 431:    */     private boolean hasPublicFeedbacks;
/* 432:    */     private List<String> publicFeedbacks;
/* 433:    */     private long discountNQT;
/* 434:    */     private long refundNQT;
/* 435:    */     
/* 436:    */     static void init() {}
/* 437:    */     
/* 438:    */     private Purchase(Transaction paramTransaction, Attachment.DigitalGoodsPurchase paramDigitalGoodsPurchase, long paramLong)
/* 439:    */     {
/* 440:348 */       this.id = paramTransaction.getId();
/* 441:349 */       this.dbKey = purchaseDbKeyFactory.newKey(this.id);
/* 442:350 */       this.buyerId = paramTransaction.getSenderId();
/* 443:351 */       this.goodsId = paramDigitalGoodsPurchase.getGoodsId();
/* 444:352 */       this.sellerId = paramLong;
/* 445:353 */       this.quantity = paramDigitalGoodsPurchase.getQuantity();
/* 446:354 */       this.priceNQT = paramDigitalGoodsPurchase.getPriceNQT();
/* 447:355 */       this.deadline = paramDigitalGoodsPurchase.getDeliveryDeadlineTimestamp();
/* 448:356 */       this.note = (paramTransaction.getEncryptedMessage() == null ? null : paramTransaction.getEncryptedMessage().getEncryptedData());
/* 449:357 */       this.timestamp = paramTransaction.getTimestamp();
/* 450:358 */       this.isPending = true;
/* 451:    */     }
/* 452:    */     
/* 453:    */     private Purchase(ResultSet paramResultSet)
/* 454:    */       throws SQLException
/* 455:    */     {
/* 456:362 */       this.id = paramResultSet.getLong("id");
/* 457:363 */       this.dbKey = purchaseDbKeyFactory.newKey(this.id);
/* 458:364 */       this.buyerId = paramResultSet.getLong("buyer_id");
/* 459:365 */       this.goodsId = paramResultSet.getLong("goods_id");
/* 460:366 */       this.sellerId = paramResultSet.getLong("seller_id");
/* 461:367 */       this.quantity = paramResultSet.getInt("quantity");
/* 462:368 */       this.priceNQT = paramResultSet.getLong("price");
/* 463:369 */       this.deadline = paramResultSet.getInt("deadline");
/* 464:370 */       this.note = DigitalGoodsStore.loadEncryptedData(paramResultSet, "note", "nonce");
/* 465:371 */       this.timestamp = paramResultSet.getInt("timestamp");
/* 466:372 */       this.isPending = paramResultSet.getBoolean("pending");
/* 467:373 */       this.encryptedGoods = DigitalGoodsStore.loadEncryptedData(paramResultSet, "goods", "goods_nonce");
/* 468:374 */       this.refundNote = DigitalGoodsStore.loadEncryptedData(paramResultSet, "refund_note", "refund_nonce");
/* 469:375 */       this.hasFeedbackNotes = paramResultSet.getBoolean("has_feedback_notes");
/* 470:376 */       this.hasPublicFeedbacks = paramResultSet.getBoolean("has_public_feedbacks");
/* 471:377 */       this.discountNQT = paramResultSet.getLong("discount");
/* 472:378 */       this.refundNQT = paramResultSet.getLong("refund");
/* 473:    */     }
/* 474:    */     
/* 475:    */     private void save(Connection paramConnection)
/* 476:    */       throws SQLException
/* 477:    */     {
/* 478:382 */       PreparedStatement localPreparedStatement = paramConnection.prepareStatement("MERGE INTO purchase (id, buyer_id, goods_id, seller_id, quantity, price, deadline, note, nonce, timestamp, pending, goods, goods_nonce, refund_note, refund_nonce, has_feedback_notes, has_public_feedbacks, discount, refund, height, latest) KEY (id, height) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, TRUE)");Object localObject1 = null;
/* 479:    */       try
/* 480:    */       {
/* 481:386 */         int i = 0;
/* 482:387 */         localPreparedStatement.setLong(++i, getId());
/* 483:388 */         localPreparedStatement.setLong(++i, getBuyerId());
/* 484:389 */         localPreparedStatement.setLong(++i, getGoodsId());
/* 485:390 */         localPreparedStatement.setLong(++i, getSellerId());
/* 486:391 */         localPreparedStatement.setInt(++i, getQuantity());
/* 487:392 */         localPreparedStatement.setLong(++i, getPriceNQT());
/* 488:393 */         localPreparedStatement.setInt(++i, getDeliveryDeadlineTimestamp());
/* 489:394 */         DigitalGoodsStore.setEncryptedData(localPreparedStatement, getNote(), ++i);
/* 490:395 */         i++;
/* 491:396 */         localPreparedStatement.setInt(++i, getTimestamp());
/* 492:397 */         localPreparedStatement.setBoolean(++i, isPending());
/* 493:398 */         DigitalGoodsStore.setEncryptedData(localPreparedStatement, getEncryptedGoods(), ++i);
/* 494:399 */         i++;
/* 495:400 */         DigitalGoodsStore.setEncryptedData(localPreparedStatement, getRefundNote(), ++i);
/* 496:401 */         i++;
/* 497:402 */         localPreparedStatement.setBoolean(++i, (getFeedbackNotes() != null) && (getFeedbackNotes().size() > 0));
/* 498:403 */         localPreparedStatement.setBoolean(++i, (getPublicFeedback() != null) && (getPublicFeedback().size() > 0));
/* 499:404 */         localPreparedStatement.setLong(++i, getDiscountNQT());
/* 500:405 */         localPreparedStatement.setLong(++i, getRefundNQT());
/* 501:406 */         localPreparedStatement.setInt(++i, Nxt.getBlockchain().getHeight());
/* 502:407 */         localPreparedStatement.executeUpdate();
/* 503:    */       }
/* 504:    */       catch (Throwable localThrowable2)
/* 505:    */       {
/* 506:382 */         localObject1 = localThrowable2;throw localThrowable2;
/* 507:    */       }
/* 508:    */       finally
/* 509:    */       {
/* 510:408 */         if (localPreparedStatement != null) {
/* 511:408 */           if (localObject1 != null) {
/* 512:    */             try
/* 513:    */             {
/* 514:408 */               localPreparedStatement.close();
/* 515:    */             }
/* 516:    */             catch (Throwable localThrowable3)
/* 517:    */             {
/* 518:408 */               ((Throwable)localObject1).addSuppressed(localThrowable3);
/* 519:    */             }
/* 520:    */           } else {
/* 521:408 */             localPreparedStatement.close();
/* 522:    */           }
/* 523:    */         }
/* 524:    */       }
/* 525:    */     }
/* 526:    */     
/* 527:    */     public long getId()
/* 528:    */     {
/* 529:412 */       return this.id;
/* 530:    */     }
/* 531:    */     
/* 532:    */     public long getBuyerId()
/* 533:    */     {
/* 534:416 */       return this.buyerId;
/* 535:    */     }
/* 536:    */     
/* 537:    */     public long getGoodsId()
/* 538:    */     {
/* 539:420 */       return this.goodsId;
/* 540:    */     }
/* 541:    */     
/* 542:    */     public long getSellerId()
/* 543:    */     {
/* 544:423 */       return this.sellerId;
/* 545:    */     }
/* 546:    */     
/* 547:    */     public int getQuantity()
/* 548:    */     {
/* 549:426 */       return this.quantity;
/* 550:    */     }
/* 551:    */     
/* 552:    */     public long getPriceNQT()
/* 553:    */     {
/* 554:430 */       return this.priceNQT;
/* 555:    */     }
/* 556:    */     
/* 557:    */     public int getDeliveryDeadlineTimestamp()
/* 558:    */     {
/* 559:434 */       return this.deadline;
/* 560:    */     }
/* 561:    */     
/* 562:    */     public EncryptedData getNote()
/* 563:    */     {
/* 564:438 */       return this.note;
/* 565:    */     }
/* 566:    */     
/* 567:    */     public boolean isPending()
/* 568:    */     {
/* 569:442 */       return this.isPending;
/* 570:    */     }
/* 571:    */     
/* 572:    */     private void setPending(boolean paramBoolean)
/* 573:    */     {
/* 574:446 */       this.isPending = paramBoolean;
/* 575:447 */       purchaseTable.insert(this);
/* 576:    */     }
/* 577:    */     
/* 578:    */     public int getTimestamp()
/* 579:    */     {
/* 580:451 */       return this.timestamp;
/* 581:    */     }
/* 582:    */     
/* 583:    */     public String getName()
/* 584:    */     {
/* 585:455 */       return DigitalGoodsStore.getGoods(this.goodsId).getName();
/* 586:    */     }
/* 587:    */     
/* 588:    */     public EncryptedData getEncryptedGoods()
/* 589:    */     {
/* 590:459 */       return this.encryptedGoods;
/* 591:    */     }
/* 592:    */     
/* 593:    */     public boolean goodsIsText()
/* 594:    */     {
/* 595:463 */       return this.goodsIsText;
/* 596:    */     }
/* 597:    */     
/* 598:    */     private void setEncryptedGoods(EncryptedData paramEncryptedData, boolean paramBoolean)
/* 599:    */     {
/* 600:467 */       this.encryptedGoods = paramEncryptedData;
/* 601:468 */       this.goodsIsText = paramBoolean;
/* 602:469 */       purchaseTable.insert(this);
/* 603:    */     }
/* 604:    */     
/* 605:    */     public EncryptedData getRefundNote()
/* 606:    */     {
/* 607:473 */       return this.refundNote;
/* 608:    */     }
/* 609:    */     
/* 610:    */     private void setRefundNote(EncryptedData paramEncryptedData)
/* 611:    */     {
/* 612:477 */       this.refundNote = paramEncryptedData;
/* 613:478 */       purchaseTable.insert(this);
/* 614:    */     }
/* 615:    */     
/* 616:    */     public List<EncryptedData> getFeedbackNotes()
/* 617:    */     {
/* 618:482 */       if (!this.hasFeedbackNotes) {
/* 619:483 */         return null;
/* 620:    */       }
/* 621:485 */       this.feedbackNotes = feedbackTable.get(feedbackDbKeyFactory.newKey(this));
/* 622:486 */       return this.feedbackNotes;
/* 623:    */     }
/* 624:    */     
/* 625:    */     private void addFeedbackNote(EncryptedData paramEncryptedData)
/* 626:    */     {
/* 627:490 */       if (this.feedbackNotes == null) {
/* 628:491 */         this.feedbackNotes = new ArrayList();
/* 629:    */       }
/* 630:493 */       this.feedbackNotes.add(paramEncryptedData);
/* 631:494 */       this.hasFeedbackNotes = true;
/* 632:495 */       purchaseTable.insert(this);
/* 633:496 */       feedbackTable.insert(this, this.feedbackNotes);
/* 634:    */     }
/* 635:    */     
/* 636:    */     public List<String> getPublicFeedback()
/* 637:    */     {
/* 638:500 */       if (!this.hasPublicFeedbacks) {
/* 639:501 */         return null;
/* 640:    */       }
/* 641:503 */       this.publicFeedbacks = publicFeedbackTable.get(publicFeedbackDbKeyFactory.newKey(this));
/* 642:504 */       return this.publicFeedbacks;
/* 643:    */     }
/* 644:    */     
/* 645:    */     private void addPublicFeedback(String paramString)
/* 646:    */     {
/* 647:508 */       if (this.publicFeedbacks == null) {
/* 648:509 */         this.publicFeedbacks = new ArrayList();
/* 649:    */       }
/* 650:511 */       this.publicFeedbacks.add(paramString);
/* 651:512 */       this.hasPublicFeedbacks = true;
/* 652:513 */       purchaseTable.insert(this);
/* 653:514 */       publicFeedbackTable.insert(this, this.publicFeedbacks);
/* 654:    */     }
/* 655:    */     
/* 656:    */     public long getDiscountNQT()
/* 657:    */     {
/* 658:518 */       return this.discountNQT;
/* 659:    */     }
/* 660:    */     
/* 661:    */     public void setDiscountNQT(long paramLong)
/* 662:    */     {
/* 663:522 */       this.discountNQT = paramLong;
/* 664:523 */       purchaseTable.insert(this);
/* 665:    */     }
/* 666:    */     
/* 667:    */     public long getRefundNQT()
/* 668:    */     {
/* 669:527 */       return this.refundNQT;
/* 670:    */     }
/* 671:    */     
/* 672:    */     public void setRefundNQT(long paramLong)
/* 673:    */     {
/* 674:531 */       this.refundNQT = paramLong;
/* 675:532 */       purchaseTable.insert(this);
/* 676:    */     }
/* 677:    */   }
/* 678:    */   
/* 679:    */   public static Goods getGoods(long paramLong)
/* 680:    */   {
/* 681:551 */     return (Goods)Goods.goodsTable.get(Goods.goodsDbKeyFactory.newKey(paramLong));
/* 682:    */   }
/* 683:    */   
/* 684:    */   public static DbIterator<Goods> getAllGoods(int paramInt1, int paramInt2)
/* 685:    */   {
/* 686:555 */     return Goods.goodsTable.getAll(paramInt1, paramInt2);
/* 687:    */   }
/* 688:    */   
/* 689:    */   public static DbIterator<Goods> getGoodsInStock(int paramInt1, int paramInt2)
/* 690:    */   {
/* 691:559 */     DbClause local2 = new DbClause(" delisted = FALSE AND quantity > 0 ")
/* 692:    */     {
/* 693:    */       public int set(PreparedStatement paramAnonymousPreparedStatement, int paramAnonymousInt)
/* 694:    */         throws SQLException
/* 695:    */       {
/* 696:562 */         return paramAnonymousInt;
/* 697:    */       }
/* 698:564 */     };
/* 699:565 */     return Goods.goodsTable.getManyBy(local2, paramInt1, paramInt2);
/* 700:    */   }
/* 701:    */   
/* 702:    */   public static DbIterator<Goods> getSellerGoods(final long paramLong, boolean paramBoolean, int paramInt1, int paramInt2)
/* 703:    */   {
/* 704:569 */     DbClause local3 = new DbClause(" seller_id = ? " + (paramBoolean ? "AND delisted = FALSE AND quantity > 0" : ""))
/* 705:    */     {
/* 706:    */       public int set(PreparedStatement paramAnonymousPreparedStatement, int paramAnonymousInt)
/* 707:    */         throws SQLException
/* 708:    */       {
/* 709:572 */         paramAnonymousPreparedStatement.setLong(paramAnonymousInt++, paramLong);
/* 710:573 */         return paramAnonymousInt;
/* 711:    */       }
/* 712:575 */     };
/* 713:576 */     return Goods.goodsTable.getManyBy(local3, paramInt1, paramInt2, " ORDER BY name ASC, timestamp DESC, id ASC ");
/* 714:    */   }
/* 715:    */   
/* 716:    */   public static DbIterator<Purchase> getAllPurchases(int paramInt1, int paramInt2)
/* 717:    */   {
/* 718:580 */     return Purchase.purchaseTable.getAll(paramInt1, paramInt2);
/* 719:    */   }
/* 720:    */   
/* 721:    */   public static DbIterator<Purchase> getSellerPurchases(long paramLong, int paramInt1, int paramInt2)
/* 722:    */   {
/* 723:584 */     return Purchase.purchaseTable.getManyBy(new DbClause.LongClause("seller_id", paramLong), paramInt1, paramInt2);
/* 724:    */   }
/* 725:    */   
/* 726:    */   public static DbIterator<Purchase> getBuyerPurchases(long paramLong, int paramInt1, int paramInt2)
/* 727:    */   {
/* 728:588 */     return Purchase.purchaseTable.getManyBy(new DbClause.LongClause("buyer_id", paramLong), paramInt1, paramInt2);
/* 729:    */   }
/* 730:    */   
/* 731:    */   public static DbIterator<Purchase> getSellerBuyerPurchases(final long paramLong1, long paramLong2, int paramInt1, int paramInt2)
/* 732:    */   {
/* 733:592 */     DbClause local4 = new DbClause(" seller_id = ? AND buyer_id = ? ")
/* 734:    */     {
/* 735:    */       public int set(PreparedStatement paramAnonymousPreparedStatement, int paramAnonymousInt)
/* 736:    */         throws SQLException
/* 737:    */       {
/* 738:595 */         paramAnonymousPreparedStatement.setLong(paramAnonymousInt++, paramLong1);
/* 739:596 */         paramAnonymousPreparedStatement.setLong(paramAnonymousInt++, this.val$buyerId);
/* 740:597 */         return paramAnonymousInt;
/* 741:    */       }
/* 742:599 */     };
/* 743:600 */     return Purchase.purchaseTable.getManyBy(local4, paramInt1, paramInt2);
/* 744:    */   }
/* 745:    */   
/* 746:    */   public static Purchase getPurchase(long paramLong)
/* 747:    */   {
/* 748:604 */     return (Purchase)Purchase.purchaseTable.get(Purchase.purchaseDbKeyFactory.newKey(paramLong));
/* 749:    */   }
/* 750:    */   
/* 751:    */   public static DbIterator<Purchase> getPendingSellerPurchases(final long paramLong, int paramInt1, int paramInt2)
/* 752:    */   {
/* 753:608 */     DbClause local5 = new DbClause(" seller_id = ? AND pending = TRUE ")
/* 754:    */     {
/* 755:    */       public int set(PreparedStatement paramAnonymousPreparedStatement, int paramAnonymousInt)
/* 756:    */         throws SQLException
/* 757:    */       {
/* 758:611 */         paramAnonymousPreparedStatement.setLong(paramAnonymousInt++, paramLong);
/* 759:612 */         return paramAnonymousInt;
/* 760:    */       }
/* 761:614 */     };
/* 762:615 */     return Purchase.purchaseTable.getManyBy(local5, paramInt1, paramInt2);
/* 763:    */   }
/* 764:    */   
/* 765:    */   static Purchase getPendingPurchase(long paramLong)
/* 766:    */   {
/* 767:619 */     Purchase localPurchase = getPurchase(paramLong);
/* 768:620 */     return (localPurchase == null) || (!localPurchase.isPending()) ? null : localPurchase;
/* 769:    */   }
/* 770:    */   
/* 771:    */   private static DbIterator<Purchase> getExpiredPendingPurchases(final int paramInt)
/* 772:    */   {
/* 773:624 */     DbClause local6 = new DbClause(" deadline < ? AND pending = TRUE ")
/* 774:    */     {
/* 775:    */       public int set(PreparedStatement paramAnonymousPreparedStatement, int paramAnonymousInt)
/* 776:    */         throws SQLException
/* 777:    */       {
/* 778:627 */         paramAnonymousPreparedStatement.setLong(paramAnonymousInt++, paramInt);
/* 779:628 */         return paramAnonymousInt;
/* 780:    */       }
/* 781:630 */     };
/* 782:631 */     return Purchase.purchaseTable.getManyBy(local6, 0, -1);
/* 783:    */   }
/* 784:    */   
/* 785:    */   private static void addPurchase(Transaction paramTransaction, Attachment.DigitalGoodsPurchase paramDigitalGoodsPurchase, long paramLong)
/* 786:    */   {
/* 787:635 */     Purchase localPurchase = new Purchase(paramTransaction, paramDigitalGoodsPurchase, paramLong, null);
/* 788:636 */     Purchase.purchaseTable.insert(localPurchase);
/* 789:637 */     purchaseListeners.notify(localPurchase, Event.PURCHASE);
/* 790:    */   }
/* 791:    */   
/* 792:    */   static void listGoods(Transaction paramTransaction, Attachment.DigitalGoodsListing paramDigitalGoodsListing)
/* 793:    */   {
/* 794:641 */     Goods localGoods = new Goods(paramTransaction, paramDigitalGoodsListing, null);
/* 795:642 */     Goods.goodsTable.insert(localGoods);
/* 796:643 */     goodsListeners.notify(localGoods, Event.GOODS_LISTED);
/* 797:    */   }
/* 798:    */   
/* 799:    */   static void delistGoods(long paramLong)
/* 800:    */   {
/* 801:647 */     Goods localGoods = (Goods)Goods.goodsTable.get(Goods.goodsDbKeyFactory.newKey(paramLong));
/* 802:648 */     if (!localGoods.isDelisted())
/* 803:    */     {
/* 804:649 */       localGoods.setDelisted(true);
/* 805:650 */       goodsListeners.notify(localGoods, Event.GOODS_DELISTED);
/* 806:    */     }
/* 807:    */     else
/* 808:    */     {
/* 809:652 */       throw new IllegalStateException("Goods already delisted");
/* 810:    */     }
/* 811:    */   }
/* 812:    */   
/* 813:    */   static void changePrice(long paramLong1, long paramLong2)
/* 814:    */   {
/* 815:657 */     Goods localGoods = (Goods)Goods.goodsTable.get(Goods.goodsDbKeyFactory.newKey(paramLong1));
/* 816:658 */     if (!localGoods.isDelisted())
/* 817:    */     {
/* 818:659 */       localGoods.changePrice(paramLong2);
/* 819:660 */       goodsListeners.notify(localGoods, Event.GOODS_PRICE_CHANGE);
/* 820:    */     }
/* 821:    */     else
/* 822:    */     {
/* 823:662 */       throw new IllegalStateException("Can't change price of delisted goods");
/* 824:    */     }
/* 825:    */   }
/* 826:    */   
/* 827:    */   static void changeQuantity(long paramLong, int paramInt)
/* 828:    */   {
/* 829:667 */     Goods localGoods = (Goods)Goods.goodsTable.get(Goods.goodsDbKeyFactory.newKey(paramLong));
/* 830:668 */     if (!localGoods.isDelisted())
/* 831:    */     {
/* 832:669 */       localGoods.changeQuantity(paramInt);
/* 833:670 */       goodsListeners.notify(localGoods, Event.GOODS_QUANTITY_CHANGE);
/* 834:    */     }
/* 835:    */     else
/* 836:    */     {
/* 837:672 */       throw new IllegalStateException("Can't change quantity of delisted goods");
/* 838:    */     }
/* 839:    */   }
/* 840:    */   
/* 841:    */   static void purchase(Transaction paramTransaction, Attachment.DigitalGoodsPurchase paramDigitalGoodsPurchase)
/* 842:    */   {
/* 843:677 */     Goods localGoods = (Goods)Goods.goodsTable.get(Goods.goodsDbKeyFactory.newKey(paramDigitalGoodsPurchase.getGoodsId()));
/* 844:678 */     if ((!localGoods.isDelisted()) && (paramDigitalGoodsPurchase.getQuantity() <= localGoods.getQuantity()) && (paramDigitalGoodsPurchase.getPriceNQT() == localGoods.getPriceNQT()) && (paramDigitalGoodsPurchase.getDeliveryDeadlineTimestamp() > Nxt.getBlockchain().getLastBlock().getTimestamp()))
/* 845:    */     {
/* 846:680 */       localGoods.changeQuantity(-paramDigitalGoodsPurchase.getQuantity());
/* 847:681 */       addPurchase(paramTransaction, paramDigitalGoodsPurchase, localGoods.getSellerId());
/* 848:    */     }
/* 849:    */     else
/* 850:    */     {
/* 851:683 */       Account localAccount = Account.getAccount(paramTransaction.getSenderId());
/* 852:684 */       localAccount.addToUnconfirmedBalanceNQT(Convert.safeMultiply(paramDigitalGoodsPurchase.getQuantity(), paramDigitalGoodsPurchase.getPriceNQT()));
/* 853:    */     }
/* 854:    */   }
/* 855:    */   
/* 856:    */   static void deliver(Transaction paramTransaction, Attachment.DigitalGoodsDelivery paramDigitalGoodsDelivery)
/* 857:    */   {
/* 858:690 */     Purchase localPurchase = getPendingPurchase(paramDigitalGoodsDelivery.getPurchaseId());
/* 859:691 */     localPurchase.setPending(false);
/* 860:692 */     long l = Convert.safeMultiply(localPurchase.getQuantity(), localPurchase.getPriceNQT());
/* 861:693 */     Account localAccount1 = Account.getAccount(localPurchase.getBuyerId());
/* 862:694 */     localAccount1.addToBalanceNQT(Convert.safeSubtract(paramDigitalGoodsDelivery.getDiscountNQT(), l));
/* 863:695 */     localAccount1.addToUnconfirmedBalanceNQT(paramDigitalGoodsDelivery.getDiscountNQT());
/* 864:696 */     Account localAccount2 = Account.getAccount(paramTransaction.getSenderId());
/* 865:697 */     localAccount2.addToBalanceAndUnconfirmedBalanceNQT(Convert.safeSubtract(l, paramDigitalGoodsDelivery.getDiscountNQT()));
/* 866:698 */     localPurchase.setEncryptedGoods(paramDigitalGoodsDelivery.getGoods(), paramDigitalGoodsDelivery.goodsIsText());
/* 867:699 */     localPurchase.setDiscountNQT(paramDigitalGoodsDelivery.getDiscountNQT());
/* 868:700 */     purchaseListeners.notify(localPurchase, Event.DELIVERY);
/* 869:    */   }
/* 870:    */   
/* 871:    */   static void refund(long paramLong1, long paramLong2, long paramLong3, Appendix.EncryptedMessage paramEncryptedMessage)
/* 872:    */   {
/* 873:704 */     Purchase localPurchase = (Purchase)Purchase.purchaseTable.get(Purchase.purchaseDbKeyFactory.newKey(paramLong2));
/* 874:705 */     Account localAccount1 = Account.getAccount(paramLong1);
/* 875:706 */     localAccount1.addToBalanceNQT(-paramLong3);
/* 876:707 */     Account localAccount2 = Account.getAccount(localPurchase.getBuyerId());
/* 877:708 */     localAccount2.addToBalanceAndUnconfirmedBalanceNQT(paramLong3);
/* 878:709 */     if (paramEncryptedMessage != null) {
/* 879:710 */       localPurchase.setRefundNote(paramEncryptedMessage.getEncryptedData());
/* 880:    */     }
/* 881:712 */     localPurchase.setRefundNQT(paramLong3);
/* 882:713 */     purchaseListeners.notify(localPurchase, Event.REFUND);
/* 883:    */   }
/* 884:    */   
/* 885:    */   static void feedback(long paramLong, Appendix.EncryptedMessage paramEncryptedMessage, Appendix.Message paramMessage)
/* 886:    */   {
/* 887:717 */     Purchase localPurchase = (Purchase)Purchase.purchaseTable.get(Purchase.purchaseDbKeyFactory.newKey(paramLong));
/* 888:718 */     if (paramEncryptedMessage != null) {
/* 889:719 */       localPurchase.addFeedbackNote(paramEncryptedMessage.getEncryptedData());
/* 890:    */     }
/* 891:721 */     if (paramMessage != null) {
/* 892:722 */       localPurchase.addPublicFeedback(Convert.toString(paramMessage.getMessage()));
/* 893:    */     }
/* 894:724 */     purchaseListeners.notify(localPurchase, Event.FEEDBACK);
/* 895:    */   }
/* 896:    */   
/* 897:    */   private static EncryptedData loadEncryptedData(ResultSet paramResultSet, String paramString1, String paramString2)
/* 898:    */     throws SQLException
/* 899:    */   {
/* 900:728 */     byte[] arrayOfByte = paramResultSet.getBytes(paramString1);
/* 901:729 */     if (arrayOfByte == null) {
/* 902:730 */       return null;
/* 903:    */     }
/* 904:732 */     return new EncryptedData(arrayOfByte, paramResultSet.getBytes(paramString2));
/* 905:    */   }
/* 906:    */   
/* 907:    */   private static void setEncryptedData(PreparedStatement paramPreparedStatement, EncryptedData paramEncryptedData, int paramInt)
/* 908:    */     throws SQLException
/* 909:    */   {
/* 910:736 */     if (paramEncryptedData == null)
/* 911:    */     {
/* 912:737 */       paramPreparedStatement.setNull(paramInt, -3);
/* 913:738 */       paramPreparedStatement.setNull(paramInt + 1, -3);
/* 914:    */     }
/* 915:    */     else
/* 916:    */     {
/* 917:740 */       paramPreparedStatement.setBytes(paramInt, paramEncryptedData.getData());
/* 918:741 */       paramPreparedStatement.setBytes(paramInt + 1, paramEncryptedData.getNonce());
/* 919:    */     }
/* 920:    */   }
/* 921:    */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.DigitalGoodsStore
 * JD-Core Version:    0.7.1
 */