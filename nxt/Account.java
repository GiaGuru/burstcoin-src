/*   1:    */ package nxt;
/*   2:    */ 
/*   3:    */ import java.security.MessageDigest;
/*   4:    */ import java.sql.Connection;
/*   5:    */ import java.sql.PreparedStatement;
/*   6:    */ import java.sql.ResultSet;
/*   7:    */ import java.sql.SQLException;
/*   8:    */ import java.util.Arrays;
/*   9:    */ import nxt.crypto.Crypto;
/*  10:    */ import nxt.crypto.EncryptedData;
/*  11:    */ import nxt.db.Db;
/*  12:    */ import nxt.db.DbClause;
/*  13:    */ import nxt.db.DbClause.LongClause;
/*  14:    */ import nxt.db.DbIterator;
/*  15:    */ import nxt.db.DbKey;
/*  16:    */ import nxt.db.DbKey.Factory;
/*  17:    */ import nxt.db.DbKey.LinkKeyFactory;
/*  18:    */ import nxt.db.DbKey.LongKeyFactory;
/*  19:    */ import nxt.db.DbUtils;
/*  20:    */ import nxt.db.DerivedDbTable;
/*  21:    */ import nxt.db.VersionedEntityDbTable;
/*  22:    */ import nxt.util.Convert;
/*  23:    */ import nxt.util.Listener;
/*  24:    */ import nxt.util.Listeners;
/*  25:    */ import nxt.util.Logger;
/*  26:    */ 
/*  27:    */ public final class Account
/*  28:    */ {
/*  29:    */   public static enum Event
/*  30:    */   {
/*  31: 26 */     BALANCE,  UNCONFIRMED_BALANCE,  ASSET_BALANCE,  UNCONFIRMED_ASSET_BALANCE,  LEASE_SCHEDULED,  LEASE_STARTED,  LEASE_ENDED;
/*  32:    */     
/*  33:    */     private Event() {}
/*  34:    */   }
/*  35:    */   
/*  36:    */   public static class AccountAsset
/*  37:    */   {
/*  38:    */     private final long accountId;
/*  39:    */     private final long assetId;
/*  40:    */     private final DbKey dbKey;
/*  41:    */     private long quantityQNT;
/*  42:    */     private long unconfirmedQuantityQNT;
/*  43:    */     
/*  44:    */     private AccountAsset(long paramLong1, long paramLong2, long paramLong3, long paramLong4)
/*  45:    */     {
/*  46: 39 */       this.accountId = paramLong1;
/*  47: 40 */       this.assetId = paramLong2;
/*  48: 41 */       this.dbKey = Account.accountAssetDbKeyFactory.newKey(this.accountId, this.assetId);
/*  49: 42 */       this.quantityQNT = paramLong3;
/*  50: 43 */       this.unconfirmedQuantityQNT = paramLong4;
/*  51:    */     }
/*  52:    */     
/*  53:    */     private AccountAsset(ResultSet paramResultSet)
/*  54:    */       throws SQLException
/*  55:    */     {
/*  56: 47 */       this.accountId = paramResultSet.getLong("account_id");
/*  57: 48 */       this.assetId = paramResultSet.getLong("asset_id");
/*  58: 49 */       this.dbKey = Account.accountAssetDbKeyFactory.newKey(this.accountId, this.assetId);
/*  59: 50 */       this.quantityQNT = paramResultSet.getLong("quantity");
/*  60: 51 */       this.unconfirmedQuantityQNT = paramResultSet.getLong("unconfirmed_quantity");
/*  61:    */     }
/*  62:    */     
/*  63:    */     private void save(Connection paramConnection)
/*  64:    */       throws SQLException
/*  65:    */     {
/*  66: 55 */       PreparedStatement localPreparedStatement = paramConnection.prepareStatement("MERGE INTO account_asset (account_id, asset_id, quantity, unconfirmed_quantity, height, latest) KEY (account_id, asset_id, height) VALUES (?, ?, ?, ?, ?, TRUE)");Object localObject1 = null;
/*  67:    */       try
/*  68:    */       {
/*  69: 58 */         int i = 0;
/*  70: 59 */         localPreparedStatement.setLong(++i, this.accountId);
/*  71: 60 */         localPreparedStatement.setLong(++i, this.assetId);
/*  72: 61 */         localPreparedStatement.setLong(++i, this.quantityQNT);
/*  73: 62 */         localPreparedStatement.setLong(++i, this.unconfirmedQuantityQNT);
/*  74: 63 */         localPreparedStatement.setInt(++i, Nxt.getBlockchain().getHeight());
/*  75: 64 */         localPreparedStatement.executeUpdate();
/*  76:    */       }
/*  77:    */       catch (Throwable localThrowable2)
/*  78:    */       {
/*  79: 55 */         localObject1 = localThrowable2;throw localThrowable2;
/*  80:    */       }
/*  81:    */       finally
/*  82:    */       {
/*  83: 65 */         if (localPreparedStatement != null) {
/*  84: 65 */           if (localObject1 != null) {
/*  85:    */             try
/*  86:    */             {
/*  87: 65 */               localPreparedStatement.close();
/*  88:    */             }
/*  89:    */             catch (Throwable localThrowable3)
/*  90:    */             {
/*  91: 65 */               ((Throwable)localObject1).addSuppressed(localThrowable3);
/*  92:    */             }
/*  93:    */           } else {
/*  94: 65 */             localPreparedStatement.close();
/*  95:    */           }
/*  96:    */         }
/*  97:    */       }
/*  98:    */     }
/*  99:    */     
/* 100:    */     public long getAccountId()
/* 101:    */     {
/* 102: 69 */       return this.accountId;
/* 103:    */     }
/* 104:    */     
/* 105:    */     public long getAssetId()
/* 106:    */     {
/* 107: 73 */       return this.assetId;
/* 108:    */     }
/* 109:    */     
/* 110:    */     public long getQuantityQNT()
/* 111:    */     {
/* 112: 77 */       return this.quantityQNT;
/* 113:    */     }
/* 114:    */     
/* 115:    */     public long getUnconfirmedQuantityQNT()
/* 116:    */     {
/* 117: 81 */       return this.unconfirmedQuantityQNT;
/* 118:    */     }
/* 119:    */     
/* 120:    */     private void save()
/* 121:    */     {
/* 122: 85 */       Account.checkBalance(this.accountId, this.quantityQNT, this.unconfirmedQuantityQNT);
/* 123: 86 */       if ((this.quantityQNT > 0L) || (this.unconfirmedQuantityQNT > 0L)) {
/* 124: 87 */         Account.accountAssetTable.insert(this);
/* 125:    */       } else {
/* 126: 89 */         Account.accountAssetTable.delete(this);
/* 127:    */       }
/* 128:    */     }
/* 129:    */     
/* 130:    */     public String toString()
/* 131:    */     {
/* 132: 95 */       return "AccountAsset account_id: " + Convert.toUnsignedLong(this.accountId) + " asset_id: " + Convert.toUnsignedLong(this.assetId) + " quantity: " + this.quantityQNT + " unconfirmedQuantity: " + this.unconfirmedQuantityQNT;
/* 133:    */     }
/* 134:    */   }
/* 135:    */   
/* 136:    */   public static class AccountLease
/* 137:    */   {
/* 138:    */     public final long lessorId;
/* 139:    */     public final long lesseeId;
/* 140:    */     public final int fromHeight;
/* 141:    */     public final int toHeight;
/* 142:    */     
/* 143:    */     private AccountLease(long paramLong1, long paramLong2, int paramInt1, int paramInt2)
/* 144:    */     {
/* 145:109 */       this.lessorId = paramLong1;
/* 146:110 */       this.lesseeId = paramLong2;
/* 147:111 */       this.fromHeight = paramInt1;
/* 148:112 */       this.toHeight = paramInt2;
/* 149:    */     }
/* 150:    */   }
/* 151:    */   
/* 152:    */   public static class RewardRecipientAssignment
/* 153:    */   {
/* 154:    */     public final Long accountId;
/* 155:    */     private Long prevRecipientId;
/* 156:    */     private Long recipientId;
/* 157:    */     private int fromHeight;
/* 158:    */     private final DbKey dbKey;
/* 159:    */     
/* 160:    */     private RewardRecipientAssignment(Long paramLong1, Long paramLong2, Long paramLong3, int paramInt)
/* 161:    */     {
/* 162:126 */       this.accountId = paramLong1;
/* 163:127 */       this.prevRecipientId = paramLong2;
/* 164:128 */       this.recipientId = paramLong3;
/* 165:129 */       this.fromHeight = paramInt;
/* 166:130 */       this.dbKey = Account.rewardRecipientAssignmentDbKeyFactory.newKey(this.accountId.longValue());
/* 167:    */     }
/* 168:    */     
/* 169:    */     private RewardRecipientAssignment(ResultSet paramResultSet)
/* 170:    */       throws SQLException
/* 171:    */     {
/* 172:134 */       this.accountId = Long.valueOf(paramResultSet.getLong("account_id"));
/* 173:135 */       this.dbKey = Account.rewardRecipientAssignmentDbKeyFactory.newKey(this.accountId.longValue());
/* 174:136 */       this.prevRecipientId = Long.valueOf(paramResultSet.getLong("prev_recip_id"));
/* 175:137 */       this.recipientId = Long.valueOf(paramResultSet.getLong("recip_id"));
/* 176:138 */       this.fromHeight = ((int)paramResultSet.getLong("from_height"));
/* 177:    */     }
/* 178:    */     
/* 179:    */     private void save(Connection paramConnection)
/* 180:    */       throws SQLException
/* 181:    */     {
/* 182:142 */       PreparedStatement localPreparedStatement = paramConnection.prepareStatement("MERGE INTO reward_recip_assign (account_id, prev_recip_id, recip_id, from_height, height, latest) KEY (account_id, height) VALUES (?, ?, ?, ?, ?, TRUE)");Object localObject1 = null;
/* 183:    */       try
/* 184:    */       {
/* 185:144 */         int i = 0;
/* 186:145 */         localPreparedStatement.setLong(++i, this.accountId.longValue());
/* 187:146 */         localPreparedStatement.setLong(++i, this.prevRecipientId.longValue());
/* 188:147 */         localPreparedStatement.setLong(++i, this.recipientId.longValue());
/* 189:148 */         localPreparedStatement.setInt(++i, this.fromHeight);
/* 190:149 */         localPreparedStatement.setInt(++i, Nxt.getBlockchain().getHeight());
/* 191:150 */         localPreparedStatement.executeUpdate();
/* 192:    */       }
/* 193:    */       catch (Throwable localThrowable2)
/* 194:    */       {
/* 195:142 */         localObject1 = localThrowable2;throw localThrowable2;
/* 196:    */       }
/* 197:    */       finally
/* 198:    */       {
/* 199:151 */         if (localPreparedStatement != null) {
/* 200:151 */           if (localObject1 != null) {
/* 201:    */             try
/* 202:    */             {
/* 203:151 */               localPreparedStatement.close();
/* 204:    */             }
/* 205:    */             catch (Throwable localThrowable3)
/* 206:    */             {
/* 207:151 */               ((Throwable)localObject1).addSuppressed(localThrowable3);
/* 208:    */             }
/* 209:    */           } else {
/* 210:151 */             localPreparedStatement.close();
/* 211:    */           }
/* 212:    */         }
/* 213:    */       }
/* 214:    */     }
/* 215:    */     
/* 216:    */     public long getAccountId()
/* 217:    */     {
/* 218:155 */       return this.accountId.longValue();
/* 219:    */     }
/* 220:    */     
/* 221:    */     public long getPrevRecipientId()
/* 222:    */     {
/* 223:159 */       return this.prevRecipientId.longValue();
/* 224:    */     }
/* 225:    */     
/* 226:    */     public long getRecipientId()
/* 227:    */     {
/* 228:163 */       return this.recipientId.longValue();
/* 229:    */     }
/* 230:    */     
/* 231:    */     public int getFromHeight()
/* 232:    */     {
/* 233:167 */       return this.fromHeight;
/* 234:    */     }
/* 235:    */     
/* 236:    */     public void setRecipient(long paramLong, int paramInt)
/* 237:    */     {
/* 238:171 */       this.prevRecipientId = this.recipientId;
/* 239:172 */       this.recipientId = Long.valueOf(paramLong);
/* 240:173 */       this.fromHeight = paramInt;
/* 241:    */     }
/* 242:    */   }
/* 243:    */   
/* 244:    */   static class DoubleSpendingException
/* 245:    */     extends RuntimeException
/* 246:    */   {
/* 247:    */     DoubleSpendingException(String paramString)
/* 248:    */     {
/* 249:180 */       super();
/* 250:    */     }
/* 251:    */   }
/* 252:    */   
/* 253:    */   static
/* 254:    */   {
/* 255:187 */     Nxt.getBlockchainProcessor().addListener(new Listener()
/* 256:    */     {
/* 257:    */       public void notify(Block paramAnonymousBlock)
/* 258:    */       {
/* 259:190 */         int i = paramAnonymousBlock.getHeight();
/* 260:191 */         DbIterator localDbIterator = Account.getLeasingAccounts();Object localObject1 = null;
/* 261:    */         try
/* 262:    */         {
/* 263:192 */           while (localDbIterator.hasNext())
/* 264:    */           {
/* 265:193 */             Account localAccount = (Account)localDbIterator.next();
/* 266:194 */             if (i == localAccount.currentLeasingHeightFrom)
/* 267:    */             {
/* 268:195 */               Account.leaseListeners.notify(new Account.AccountLease(localAccount.getId(), localAccount.currentLesseeId, i, localAccount.currentLeasingHeightTo, null), Account.Event.LEASE_STARTED);
/* 269:    */             }
/* 270:198 */             else if (i == localAccount.currentLeasingHeightTo)
/* 271:    */             {
/* 272:199 */               Account.leaseListeners.notify(new Account.AccountLease(localAccount.getId(), localAccount.currentLesseeId, localAccount.currentLeasingHeightFrom, i, null), Account.Event.LEASE_ENDED);
/* 273:202 */               if (localAccount.nextLeasingHeightFrom == Integer.MAX_VALUE)
/* 274:    */               {
/* 275:203 */                 localAccount.currentLeasingHeightFrom = Integer.MAX_VALUE;
/* 276:204 */                 localAccount.currentLesseeId = 0L;
/* 277:205 */                 Account.accountTable.insert(localAccount);
/* 278:    */               }
/* 279:    */               else
/* 280:    */               {
/* 281:207 */                 localAccount.currentLeasingHeightFrom = localAccount.nextLeasingHeightFrom;
/* 282:208 */                 localAccount.currentLeasingHeightTo = localAccount.nextLeasingHeightTo;
/* 283:209 */                 localAccount.currentLesseeId = localAccount.nextLesseeId;
/* 284:210 */                 localAccount.nextLeasingHeightFrom = Integer.MAX_VALUE;
/* 285:211 */                 localAccount.nextLesseeId = 0L;
/* 286:212 */                 Account.accountTable.insert(localAccount);
/* 287:213 */                 if (i == localAccount.currentLeasingHeightFrom) {
/* 288:214 */                   Account.leaseListeners.notify(new Account.AccountLease(localAccount.getId(), localAccount.currentLesseeId, i, localAccount.currentLeasingHeightTo, null), Account.Event.LEASE_STARTED);
/* 289:    */                 }
/* 290:    */               }
/* 291:    */             }
/* 292:    */           }
/* 293:    */         }
/* 294:    */         catch (Throwable localThrowable2)
/* 295:    */         {
/* 296:191 */           localObject1 = localThrowable2;throw localThrowable2;
/* 297:    */         }
/* 298:    */         finally
/* 299:    */         {
/* 300:221 */           if (localDbIterator != null) {
/* 301:221 */             if (localObject1 != null) {
/* 302:    */               try
/* 303:    */               {
/* 304:221 */                 localDbIterator.close();
/* 305:    */               }
/* 306:    */               catch (Throwable localThrowable3)
/* 307:    */               {
/* 308:221 */                 ((Throwable)localObject1).addSuppressed(localThrowable3);
/* 309:    */               }
/* 310:    */             } else {
/* 311:221 */               localDbIterator.close();
/* 312:    */             }
/* 313:    */           }
/* 314:    */         }
/* 315:    */       }
/* 316:221 */     }, BlockchainProcessor.Event.AFTER_BLOCK_APPLY);
/* 317:    */   }
/* 318:    */   
/* 319:227 */   private static final DbKey.LongKeyFactory<Account> accountDbKeyFactory = new DbKey.LongKeyFactory("id")
/* 320:    */   {
/* 321:    */     public DbKey newKey(Account paramAnonymousAccount)
/* 322:    */     {
/* 323:231 */       return paramAnonymousAccount.dbKey;
/* 324:    */     }
/* 325:    */   };
/* 326:236 */   private static final VersionedEntityDbTable<Account> accountTable = new VersionedEntityDbTable("account", accountDbKeyFactory)
/* 327:    */   {
/* 328:    */     protected Account load(Connection paramAnonymousConnection, ResultSet paramAnonymousResultSet)
/* 329:    */       throws SQLException
/* 330:    */     {
/* 331:240 */       return new Account(paramAnonymousResultSet, null);
/* 332:    */     }
/* 333:    */     
/* 334:    */     protected void save(Connection paramAnonymousConnection, Account paramAnonymousAccount)
/* 335:    */       throws SQLException
/* 336:    */     {
/* 337:245 */       paramAnonymousAccount.save(paramAnonymousConnection);
/* 338:    */     }
/* 339:    */   };
/* 340:250 */   private static final DbKey.LinkKeyFactory<AccountAsset> accountAssetDbKeyFactory = new DbKey.LinkKeyFactory("account_id", "asset_id")
/* 341:    */   {
/* 342:    */     public DbKey newKey(Account.AccountAsset paramAnonymousAccountAsset)
/* 343:    */     {
/* 344:254 */       return paramAnonymousAccountAsset.dbKey;
/* 345:    */     }
/* 346:    */   };
/* 347:259 */   private static final VersionedEntityDbTable<AccountAsset> accountAssetTable = new VersionedEntityDbTable("account_asset", accountAssetDbKeyFactory)
/* 348:    */   {
/* 349:    */     protected Account.AccountAsset load(Connection paramAnonymousConnection, ResultSet paramAnonymousResultSet)
/* 350:    */       throws SQLException
/* 351:    */     {
/* 352:263 */       return new Account.AccountAsset(paramAnonymousResultSet, null);
/* 353:    */     }
/* 354:    */     
/* 355:    */     protected void save(Connection paramAnonymousConnection, Account.AccountAsset paramAnonymousAccountAsset)
/* 356:    */       throws SQLException
/* 357:    */     {
/* 358:268 */       paramAnonymousAccountAsset.save(paramAnonymousConnection);
/* 359:    */     }
/* 360:    */     
/* 361:    */     protected String defaultSort()
/* 362:    */     {
/* 363:273 */       return " ORDER BY quantity DESC, account_id, asset_id ";
/* 364:    */     }
/* 365:    */   };
/* 366:278 */   private static final DerivedDbTable accountGuaranteedBalanceTable = new DerivedDbTable("account_guaranteed_balance")
/* 367:    */   {
/* 368:    */     public void trim(int paramAnonymousInt)
/* 369:    */     {
/* 370:    */       try
/* 371:    */       {
/* 372:282 */         Connection localConnection = Db.getConnection();Object localObject1 = null;
/* 373:    */         try
/* 374:    */         {
/* 375:283 */           PreparedStatement localPreparedStatement = localConnection.prepareStatement("DELETE FROM account_guaranteed_balance WHERE height < ?");Object localObject2 = null;
/* 376:    */           try
/* 377:    */           {
/* 378:285 */             localPreparedStatement.setInt(1, paramAnonymousInt - 1440);
/* 379:286 */             localPreparedStatement.executeUpdate();
/* 380:    */           }
/* 381:    */           catch (Throwable localThrowable4)
/* 382:    */           {
/* 383:282 */             localObject2 = localThrowable4;throw localThrowable4;
/* 384:    */           }
/* 385:    */           finally {}
/* 386:    */         }
/* 387:    */         catch (Throwable localThrowable2)
/* 388:    */         {
/* 389:282 */           localObject1 = localThrowable2;throw localThrowable2;
/* 390:    */         }
/* 391:    */         finally
/* 392:    */         {
/* 393:287 */           if (localConnection != null) {
/* 394:287 */             if (localObject1 != null) {
/* 395:    */               try
/* 396:    */               {
/* 397:287 */                 localConnection.close();
/* 398:    */               }
/* 399:    */               catch (Throwable localThrowable6)
/* 400:    */               {
/* 401:287 */                 ((Throwable)localObject1).addSuppressed(localThrowable6);
/* 402:    */               }
/* 403:    */             } else {
/* 404:287 */               localConnection.close();
/* 405:    */             }
/* 406:    */           }
/* 407:    */         }
/* 408:    */       }
/* 409:    */       catch (SQLException localSQLException)
/* 410:    */       {
/* 411:288 */         throw new RuntimeException(localSQLException.toString(), localSQLException);
/* 412:    */       }
/* 413:    */     }
/* 414:    */   };
/* 415:294 */   private static final DbKey.LongKeyFactory<RewardRecipientAssignment> rewardRecipientAssignmentDbKeyFactory = new DbKey.LongKeyFactory("account_id")
/* 416:    */   {
/* 417:    */     public DbKey newKey(Account.RewardRecipientAssignment paramAnonymousRewardRecipientAssignment)
/* 418:    */     {
/* 419:298 */       return paramAnonymousRewardRecipientAssignment.dbKey;
/* 420:    */     }
/* 421:    */   };
/* 422:302 */   private static final VersionedEntityDbTable<RewardRecipientAssignment> rewardRecipientAssignmentTable = new VersionedEntityDbTable("reward_recip_assign", rewardRecipientAssignmentDbKeyFactory)
/* 423:    */   {
/* 424:    */     protected Account.RewardRecipientAssignment load(Connection paramAnonymousConnection, ResultSet paramAnonymousResultSet)
/* 425:    */       throws SQLException
/* 426:    */     {
/* 427:306 */       return new Account.RewardRecipientAssignment(paramAnonymousResultSet, null);
/* 428:    */     }
/* 429:    */     
/* 430:    */     protected void save(Connection paramAnonymousConnection, Account.RewardRecipientAssignment paramAnonymousRewardRecipientAssignment)
/* 431:    */       throws SQLException
/* 432:    */     {
/* 433:311 */       paramAnonymousRewardRecipientAssignment.save(paramAnonymousConnection);
/* 434:    */     }
/* 435:    */   };
/* 436:315 */   private static final Listeners<Account, Event> listeners = new Listeners();
/* 437:317 */   private static final Listeners<AccountAsset, Event> assetListeners = new Listeners();
/* 438:319 */   private static final Listeners<AccountLease, Event> leaseListeners = new Listeners();
/* 439:    */   
/* 440:    */   public static boolean addListener(Listener<Account> paramListener, Event paramEvent)
/* 441:    */   {
/* 442:322 */     return listeners.addListener(paramListener, paramEvent);
/* 443:    */   }
/* 444:    */   
/* 445:    */   public static boolean removeListener(Listener<Account> paramListener, Event paramEvent)
/* 446:    */   {
/* 447:326 */     return listeners.removeListener(paramListener, paramEvent);
/* 448:    */   }
/* 449:    */   
/* 450:    */   public static boolean addAssetListener(Listener<AccountAsset> paramListener, Event paramEvent)
/* 451:    */   {
/* 452:330 */     return assetListeners.addListener(paramListener, paramEvent);
/* 453:    */   }
/* 454:    */   
/* 455:    */   public static boolean removeAssetListener(Listener<AccountAsset> paramListener, Event paramEvent)
/* 456:    */   {
/* 457:334 */     return assetListeners.removeListener(paramListener, paramEvent);
/* 458:    */   }
/* 459:    */   
/* 460:    */   public static boolean addLeaseListener(Listener<AccountLease> paramListener, Event paramEvent)
/* 461:    */   {
/* 462:338 */     return leaseListeners.addListener(paramListener, paramEvent);
/* 463:    */   }
/* 464:    */   
/* 465:    */   public static boolean removeLeaseListener(Listener<AccountLease> paramListener, Event paramEvent)
/* 466:    */   {
/* 467:342 */     return leaseListeners.removeListener(paramListener, paramEvent);
/* 468:    */   }
/* 469:    */   
/* 470:    */   public static DbIterator<Account> getAllAccounts(int paramInt1, int paramInt2)
/* 471:    */   {
/* 472:346 */     return accountTable.getAll(paramInt1, paramInt2);
/* 473:    */   }
/* 474:    */   
/* 475:    */   public static int getCount()
/* 476:    */   {
/* 477:350 */     return accountTable.getCount();
/* 478:    */   }
/* 479:    */   
/* 480:    */   public static Account getAccount(long paramLong)
/* 481:    */   {
/* 482:367 */     return paramLong == 0L ? null : (Account)accountTable.get(accountDbKeyFactory.newKey(paramLong));
/* 483:    */   }
/* 484:    */   
/* 485:    */   public static Account getAccount(long paramLong, int paramInt)
/* 486:    */   {
/* 487:371 */     return paramLong == 0L ? null : (Account)accountTable.get(accountDbKeyFactory.newKey(paramLong), paramInt);
/* 488:    */   }
/* 489:    */   
/* 490:    */   public static Account getAccount(byte[] paramArrayOfByte)
/* 491:    */   {
/* 492:375 */     Account localAccount = (Account)accountTable.get(accountDbKeyFactory.newKey(getId(paramArrayOfByte)));
/* 493:376 */     if (localAccount == null) {
/* 494:377 */       return null;
/* 495:    */     }
/* 496:379 */     if ((localAccount.getPublicKey() == null) || (Arrays.equals(localAccount.getPublicKey(), paramArrayOfByte))) {
/* 497:380 */       return localAccount;
/* 498:    */     }
/* 499:382 */     throw new RuntimeException("DUPLICATE KEY for account " + Convert.toUnsignedLong(localAccount.getId()) + " existing key " + Convert.toHexString(localAccount.getPublicKey()) + " new key " + Convert.toHexString(paramArrayOfByte));
/* 500:    */   }
/* 501:    */   
/* 502:    */   public static long getId(byte[] paramArrayOfByte)
/* 503:    */   {
/* 504:387 */     byte[] arrayOfByte = Crypto.sha256().digest(paramArrayOfByte);
/* 505:388 */     return Convert.fullHashToId(arrayOfByte);
/* 506:    */   }
/* 507:    */   
/* 508:    */   static Account addOrGetAccount(long paramLong)
/* 509:    */   {
/* 510:392 */     Account localAccount = (Account)accountTable.get(accountDbKeyFactory.newKey(paramLong));
/* 511:393 */     if (localAccount == null)
/* 512:    */     {
/* 513:394 */       localAccount = new Account(paramLong);
/* 514:395 */       accountTable.insert(localAccount);
/* 515:    */     }
/* 516:397 */     return localAccount;
/* 517:    */   }
/* 518:    */   
/* 519:400 */   private static final DbClause leasingAccountsClause = new DbClause(" current_lessee_id >= ? ")
/* 520:    */   {
/* 521:    */     public int set(PreparedStatement paramAnonymousPreparedStatement, int paramAnonymousInt)
/* 522:    */       throws SQLException
/* 523:    */     {
/* 524:403 */       paramAnonymousPreparedStatement.setLong(paramAnonymousInt++, Long.MIN_VALUE);
/* 525:404 */       return paramAnonymousInt;
/* 526:    */     }
/* 527:    */   };
/* 528:    */   private final long id;
/* 529:    */   private final DbKey dbKey;
/* 530:    */   private final int creationHeight;
/* 531:    */   private byte[] publicKey;
/* 532:    */   private int keyHeight;
/* 533:    */   private long balanceNQT;
/* 534:    */   private long unconfirmedBalanceNQT;
/* 535:    */   private long forgedBalanceNQT;
/* 536:    */   private int currentLeasingHeightFrom;
/* 537:    */   private int currentLeasingHeightTo;
/* 538:    */   private long currentLesseeId;
/* 539:    */   private int nextLeasingHeightFrom;
/* 540:    */   private int nextLeasingHeightTo;
/* 541:    */   private long nextLesseeId;
/* 542:    */   private String name;
/* 543:    */   private String description;
/* 544:    */   
/* 545:    */   public static DbIterator<Account> getLeasingAccounts()
/* 546:    */   {
/* 547:409 */     return accountTable.getManyBy(leasingAccountsClause, 0, -1);
/* 548:    */   }
/* 549:    */   
/* 550:    */   public static DbIterator<AccountAsset> getAssetAccounts(long paramLong, int paramInt1, int paramInt2)
/* 551:    */   {
/* 552:413 */     return accountAssetTable.getManyBy(new DbClause.LongClause("asset_id", paramLong), paramInt1, paramInt2, " ORDER BY quantity DESC, account_id ");
/* 553:    */   }
/* 554:    */   
/* 555:    */   public static DbIterator<AccountAsset> getAssetAccounts(long paramLong, int paramInt1, int paramInt2, int paramInt3)
/* 556:    */   {
/* 557:417 */     if (paramInt1 < 0) {
/* 558:418 */       return getAssetAccounts(paramLong, paramInt2, paramInt3);
/* 559:    */     }
/* 560:420 */     return accountAssetTable.getManyBy(new DbClause.LongClause("asset_id", paramLong), paramInt1, paramInt2, paramInt3, " ORDER BY quantity DESC, account_id ");
/* 561:    */   }
/* 562:    */   
/* 563:    */   private Account(long paramLong)
/* 564:    */   {
/* 565:445 */     if (paramLong != Crypto.rsDecode(Crypto.rsEncode(paramLong))) {
/* 566:446 */       Logger.logMessage("CRITICAL ERROR: Reed-Solomon encoding fails for " + paramLong);
/* 567:    */     }
/* 568:448 */     this.id = paramLong;
/* 569:449 */     this.dbKey = accountDbKeyFactory.newKey(this.id);
/* 570:450 */     this.creationHeight = Nxt.getBlockchain().getHeight();
/* 571:451 */     this.currentLeasingHeightFrom = Integer.MAX_VALUE;
/* 572:    */   }
/* 573:    */   
/* 574:    */   private Account(ResultSet paramResultSet)
/* 575:    */     throws SQLException
/* 576:    */   {
/* 577:455 */     this.id = paramResultSet.getLong("id");
/* 578:456 */     this.dbKey = accountDbKeyFactory.newKey(this.id);
/* 579:457 */     this.creationHeight = paramResultSet.getInt("creation_height");
/* 580:458 */     this.publicKey = paramResultSet.getBytes("public_key");
/* 581:459 */     this.keyHeight = paramResultSet.getInt("key_height");
/* 582:460 */     this.balanceNQT = paramResultSet.getLong("balance");
/* 583:461 */     this.unconfirmedBalanceNQT = paramResultSet.getLong("unconfirmed_balance");
/* 584:462 */     this.forgedBalanceNQT = paramResultSet.getLong("forged_balance");
/* 585:463 */     this.name = paramResultSet.getString("name");
/* 586:464 */     this.description = paramResultSet.getString("description");
/* 587:465 */     this.currentLeasingHeightFrom = paramResultSet.getInt("current_leasing_height_from");
/* 588:466 */     this.currentLeasingHeightTo = paramResultSet.getInt("current_leasing_height_to");
/* 589:467 */     this.currentLesseeId = paramResultSet.getLong("current_lessee_id");
/* 590:468 */     this.nextLeasingHeightFrom = paramResultSet.getInt("next_leasing_height_from");
/* 591:469 */     this.nextLeasingHeightTo = paramResultSet.getInt("next_leasing_height_to");
/* 592:470 */     this.nextLesseeId = paramResultSet.getLong("next_lessee_id");
/* 593:    */   }
/* 594:    */   
/* 595:    */   private void save(Connection paramConnection)
/* 596:    */     throws SQLException
/* 597:    */   {
/* 598:474 */     PreparedStatement localPreparedStatement = paramConnection.prepareStatement("MERGE INTO account (id, creation_height, public_key, key_height, balance, unconfirmed_balance, forged_balance, name, description, current_leasing_height_from, current_leasing_height_to, current_lessee_id, next_leasing_height_from, next_leasing_height_to, next_lessee_id, height, latest) KEY (id, height) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, TRUE)");Object localObject1 = null;
/* 599:    */     try
/* 600:    */     {
/* 601:480 */       int i = 0;
/* 602:481 */       localPreparedStatement.setLong(++i, getId());
/* 603:482 */       localPreparedStatement.setInt(++i, getCreationHeight());
/* 604:483 */       DbUtils.setBytes(localPreparedStatement, ++i, getPublicKey());
/* 605:484 */       localPreparedStatement.setInt(++i, getKeyHeight());
/* 606:485 */       localPreparedStatement.setLong(++i, getBalanceNQT());
/* 607:486 */       localPreparedStatement.setLong(++i, getUnconfirmedBalanceNQT());
/* 608:487 */       localPreparedStatement.setLong(++i, getForgedBalanceNQT());
/* 609:488 */       DbUtils.setString(localPreparedStatement, ++i, getName());
/* 610:489 */       DbUtils.setString(localPreparedStatement, ++i, getDescription());
/* 611:490 */       DbUtils.setIntZeroToNull(localPreparedStatement, ++i, getCurrentLeasingHeightFrom());
/* 612:491 */       DbUtils.setIntZeroToNull(localPreparedStatement, ++i, getCurrentLeasingHeightTo());
/* 613:492 */       DbUtils.setLongZeroToNull(localPreparedStatement, ++i, getCurrentLesseeId());
/* 614:493 */       DbUtils.setIntZeroToNull(localPreparedStatement, ++i, getNextLeasingHeightFrom());
/* 615:494 */       DbUtils.setIntZeroToNull(localPreparedStatement, ++i, getNextLeasingHeightTo());
/* 616:495 */       DbUtils.setLongZeroToNull(localPreparedStatement, ++i, getNextLesseeId());
/* 617:496 */       localPreparedStatement.setInt(++i, Nxt.getBlockchain().getHeight());
/* 618:497 */       localPreparedStatement.executeUpdate();
/* 619:    */     }
/* 620:    */     catch (Throwable localThrowable2)
/* 621:    */     {
/* 622:474 */       localObject1 = localThrowable2;throw localThrowable2;
/* 623:    */     }
/* 624:    */     finally
/* 625:    */     {
/* 626:498 */       if (localPreparedStatement != null) {
/* 627:498 */         if (localObject1 != null) {
/* 628:    */           try
/* 629:    */           {
/* 630:498 */             localPreparedStatement.close();
/* 631:    */           }
/* 632:    */           catch (Throwable localThrowable3)
/* 633:    */           {
/* 634:498 */             ((Throwable)localObject1).addSuppressed(localThrowable3);
/* 635:    */           }
/* 636:    */         } else {
/* 637:498 */           localPreparedStatement.close();
/* 638:    */         }
/* 639:    */       }
/* 640:    */     }
/* 641:    */   }
/* 642:    */   
/* 643:    */   public long getId()
/* 644:    */   {
/* 645:502 */     return this.id;
/* 646:    */   }
/* 647:    */   
/* 648:    */   public String getName()
/* 649:    */   {
/* 650:506 */     return this.name;
/* 651:    */   }
/* 652:    */   
/* 653:    */   public String getDescription()
/* 654:    */   {
/* 655:510 */     return this.description;
/* 656:    */   }
/* 657:    */   
/* 658:    */   void setAccountInfo(String paramString1, String paramString2)
/* 659:    */   {
/* 660:514 */     this.name = Convert.emptyToNull(paramString1.trim());
/* 661:515 */     this.description = Convert.emptyToNull(paramString2.trim());
/* 662:516 */     accountTable.insert(this);
/* 663:    */   }
/* 664:    */   
/* 665:    */   public byte[] getPublicKey()
/* 666:    */   {
/* 667:520 */     if (this.keyHeight == -1) {
/* 668:521 */       return null;
/* 669:    */     }
/* 670:523 */     return this.publicKey;
/* 671:    */   }
/* 672:    */   
/* 673:    */   private int getCreationHeight()
/* 674:    */   {
/* 675:527 */     return this.creationHeight;
/* 676:    */   }
/* 677:    */   
/* 678:    */   private int getKeyHeight()
/* 679:    */   {
/* 680:531 */     return this.keyHeight;
/* 681:    */   }
/* 682:    */   
/* 683:    */   public EncryptedData encryptTo(byte[] paramArrayOfByte, String paramString)
/* 684:    */   {
/* 685:535 */     if (getPublicKey() == null) {
/* 686:536 */       throw new IllegalArgumentException("Recipient account doesn't have a public key set");
/* 687:    */     }
/* 688:538 */     return EncryptedData.encrypt(paramArrayOfByte, Crypto.getPrivateKey(paramString), this.publicKey);
/* 689:    */   }
/* 690:    */   
/* 691:    */   public byte[] decryptFrom(EncryptedData paramEncryptedData, String paramString)
/* 692:    */   {
/* 693:542 */     if (getPublicKey() == null) {
/* 694:543 */       throw new IllegalArgumentException("Sender account doesn't have a public key set");
/* 695:    */     }
/* 696:545 */     return paramEncryptedData.decrypt(Crypto.getPrivateKey(paramString), this.publicKey);
/* 697:    */   }
/* 698:    */   
/* 699:    */   public long getBalanceNQT()
/* 700:    */   {
/* 701:549 */     return this.balanceNQT;
/* 702:    */   }
/* 703:    */   
/* 704:    */   public long getUnconfirmedBalanceNQT()
/* 705:    */   {
/* 706:553 */     return this.unconfirmedBalanceNQT;
/* 707:    */   }
/* 708:    */   
/* 709:    */   public long getForgedBalanceNQT()
/* 710:    */   {
/* 711:557 */     return this.forgedBalanceNQT;
/* 712:    */   }
/* 713:    */   
/* 714:    */   public long getEffectiveBalanceNXT()
/* 715:    */   {
/* 716:562 */     Block localBlock = Nxt.getBlockchain().getLastBlock();
/* 717:563 */     if ((localBlock.getHeight() >= 0) && ((getPublicKey() == null) || (localBlock.getHeight() - this.keyHeight <= 1440))) {
/* 718:565 */       return 0L;
/* 719:    */     }
/* 720:567 */     if ((localBlock.getHeight() < 0) && (this.creationHeight < 0))
/* 721:    */     {
/* 722:569 */       if (this.creationHeight == 0) {
/* 723:570 */         return getBalanceNQT() / 100000000L;
/* 724:    */       }
/* 725:572 */       if (localBlock.getHeight() - this.creationHeight < 1440) {
/* 726:573 */         return 0L;
/* 727:    */       }
/* 728:575 */       long l = 0L;
/* 729:576 */       for (Transaction localTransaction : localBlock.getTransactions()) {
/* 730:577 */         if (this.id == localTransaction.getRecipientId()) {
/* 731:578 */           l += localTransaction.getAmountNQT();
/* 732:    */         }
/* 733:    */       }
/* 734:581 */       return (getBalanceNQT() - l) / 100000000L;
/* 735:    */     }
/* 736:583 */     if (localBlock.getHeight() < this.currentLeasingHeightFrom) {
/* 737:584 */       return (getGuaranteedBalanceNQT(1440) + getLessorsGuaranteedBalanceNQT()) / 100000000L;
/* 738:    */     }
/* 739:586 */     return getLessorsGuaranteedBalanceNQT() / 100000000L;
/* 740:    */   }
/* 741:    */   
/* 742:    */   private long getLessorsGuaranteedBalanceNQT()
/* 743:    */   {
/* 744:590 */     long l = 0L;
/* 745:591 */     DbIterator localDbIterator = getLessors();Object localObject1 = null;
/* 746:    */     try
/* 747:    */     {
/* 748:592 */       while (localDbIterator.hasNext()) {
/* 749:593 */         l += ((Account)localDbIterator.next()).getGuaranteedBalanceNQT(1440);
/* 750:    */       }
/* 751:    */     }
/* 752:    */     catch (Throwable localThrowable2)
/* 753:    */     {
/* 754:591 */       localObject1 = localThrowable2;throw localThrowable2;
/* 755:    */     }
/* 756:    */     finally
/* 757:    */     {
/* 758:595 */       if (localDbIterator != null) {
/* 759:595 */         if (localObject1 != null) {
/* 760:    */           try
/* 761:    */           {
/* 762:595 */             localDbIterator.close();
/* 763:    */           }
/* 764:    */           catch (Throwable localThrowable3)
/* 765:    */           {
/* 766:595 */             ((Throwable)localObject1).addSuppressed(localThrowable3);
/* 767:    */           }
/* 768:    */         } else {
/* 769:595 */           localDbIterator.close();
/* 770:    */         }
/* 771:    */       }
/* 772:    */     }
/* 773:596 */     return l;
/* 774:    */   }
/* 775:    */   
/* 776:    */   private DbClause getLessorsClause(final int paramInt)
/* 777:    */   {
/* 778:600 */     new DbClause(" current_lessee_id = ? AND current_leasing_height_from <= ? AND current_leasing_height_to > ? ")
/* 779:    */     {
/* 780:    */       public int set(PreparedStatement paramAnonymousPreparedStatement, int paramAnonymousInt)
/* 781:    */         throws SQLException
/* 782:    */       {
/* 783:603 */         paramAnonymousPreparedStatement.setLong(paramAnonymousInt++, Account.this.getId());
/* 784:604 */         paramAnonymousPreparedStatement.setInt(paramAnonymousInt++, paramInt);
/* 785:605 */         paramAnonymousPreparedStatement.setInt(paramAnonymousInt++, paramInt);
/* 786:606 */         return paramAnonymousInt;
/* 787:    */       }
/* 788:    */     };
/* 789:    */   }
/* 790:    */   
/* 791:    */   public DbIterator<Account> getLessors()
/* 792:    */   {
/* 793:612 */     return accountTable.getManyBy(getLessorsClause(Nxt.getBlockchain().getHeight()), 0, -1);
/* 794:    */   }
/* 795:    */   
/* 796:    */   public DbIterator<Account> getLessors(int paramInt)
/* 797:    */   {
/* 798:616 */     if (paramInt < 0) {
/* 799:617 */       return getLessors();
/* 800:    */     }
/* 801:619 */     return accountTable.getManyBy(getLessorsClause(paramInt), paramInt, 0, -1);
/* 802:    */   }
/* 803:    */   
/* 804:    */   public long getGuaranteedBalanceNQT(int paramInt)
/* 805:    */   {
/* 806:623 */     return getGuaranteedBalanceNQT(paramInt, Nxt.getBlockchain().getHeight());
/* 807:    */   }
/* 808:    */   
/* 809:    */   public DbIterator<AccountAsset> getAssets(int paramInt1, int paramInt2)
/* 810:    */   {
/* 811:652 */     return accountAssetTable.getManyBy(new DbClause.LongClause("account_id", this.id), paramInt1, paramInt2);
/* 812:    */   }
/* 813:    */   
/* 814:    */   public DbIterator<Trade> getTrades(int paramInt1, int paramInt2)
/* 815:    */   {
/* 816:656 */     return Trade.getAccountTrades(this.id, paramInt1, paramInt2);
/* 817:    */   }
/* 818:    */   
/* 819:    */   public DbIterator<AssetTransfer> getAssetTransfers(int paramInt1, int paramInt2)
/* 820:    */   {
/* 821:660 */     return AssetTransfer.getAccountAssetTransfers(this.id, paramInt1, paramInt2);
/* 822:    */   }
/* 823:    */   
/* 824:    */   public long getAssetBalanceQNT(long paramLong)
/* 825:    */   {
/* 826:664 */     AccountAsset localAccountAsset = (AccountAsset)accountAssetTable.get(accountAssetDbKeyFactory.newKey(this.id, paramLong));
/* 827:665 */     return localAccountAsset == null ? 0L : localAccountAsset.quantityQNT;
/* 828:    */   }
/* 829:    */   
/* 830:    */   public long getUnconfirmedAssetBalanceQNT(long paramLong)
/* 831:    */   {
/* 832:669 */     AccountAsset localAccountAsset = (AccountAsset)accountAssetTable.get(accountAssetDbKeyFactory.newKey(this.id, paramLong));
/* 833:670 */     return localAccountAsset == null ? 0L : localAccountAsset.unconfirmedQuantityQNT;
/* 834:    */   }
/* 835:    */   
/* 836:    */   public RewardRecipientAssignment getRewardRecipientAssignment()
/* 837:    */   {
/* 838:674 */     return getRewardRecipientAssignment(Long.valueOf(this.id));
/* 839:    */   }
/* 840:    */   
/* 841:    */   public static RewardRecipientAssignment getRewardRecipientAssignment(Long paramLong)
/* 842:    */   {
/* 843:678 */     return (RewardRecipientAssignment)rewardRecipientAssignmentTable.get(rewardRecipientAssignmentDbKeyFactory.newKey(paramLong.longValue()));
/* 844:    */   }
/* 845:    */   
/* 846:    */   public void setRewardRecipientAssignment(Long paramLong)
/* 847:    */   {
/* 848:682 */     setRewardRecipientAssignment(Long.valueOf(this.id), paramLong);
/* 849:    */   }
/* 850:    */   
/* 851:    */   public static void setRewardRecipientAssignment(Long paramLong1, Long paramLong2)
/* 852:    */   {
/* 853:686 */     int i = Nxt.getBlockchain().getLastBlock().getHeight();
/* 854:687 */     RewardRecipientAssignment localRewardRecipientAssignment = getRewardRecipientAssignment(paramLong1);
/* 855:688 */     if (localRewardRecipientAssignment == null) {
/* 856:689 */       localRewardRecipientAssignment = new RewardRecipientAssignment(paramLong1, paramLong1, paramLong2, (int)(i + Constants.BURST_REWARD_RECIPIENT_ASSIGNMENT_WAIT_TIME), null);
/* 857:    */     } else {
/* 858:692 */       localRewardRecipientAssignment.setRecipient(paramLong2.longValue(), (int)(i + Constants.BURST_REWARD_RECIPIENT_ASSIGNMENT_WAIT_TIME));
/* 859:    */     }
/* 860:694 */     rewardRecipientAssignmentTable.insert(localRewardRecipientAssignment);
/* 861:    */   }
/* 862:    */   
/* 863:    */   private static DbClause getAccountsWithRewardRecipientClause(final long paramLong, int paramInt)
/* 864:    */   {
/* 865:698 */     new DbClause(" recip_id = ? AND from_height <= ? ")
/* 866:    */     {
/* 867:    */       public int set(PreparedStatement paramAnonymousPreparedStatement, int paramAnonymousInt)
/* 868:    */         throws SQLException
/* 869:    */       {
/* 870:701 */         paramAnonymousPreparedStatement.setLong(paramAnonymousInt++, paramLong);
/* 871:702 */         paramAnonymousPreparedStatement.setInt(paramAnonymousInt++, this.val$height);
/* 872:703 */         return paramAnonymousInt;
/* 873:    */       }
/* 874:    */     };
/* 875:    */   }
/* 876:    */   
/* 877:    */   public static DbIterator<RewardRecipientAssignment> getAccountsWithRewardRecipient(Long paramLong)
/* 878:    */   {
/* 879:709 */     return rewardRecipientAssignmentTable.getManyBy(getAccountsWithRewardRecipientClause(paramLong.longValue(), Nxt.getBlockchain().getHeight() + 1), 0, -1);
/* 880:    */   }
/* 881:    */   
/* 882:    */   public long getCurrentLesseeId()
/* 883:    */   {
/* 884:713 */     return this.currentLesseeId;
/* 885:    */   }
/* 886:    */   
/* 887:    */   public long getNextLesseeId()
/* 888:    */   {
/* 889:717 */     return this.nextLesseeId;
/* 890:    */   }
/* 891:    */   
/* 892:    */   public int getCurrentLeasingHeightFrom()
/* 893:    */   {
/* 894:721 */     return this.currentLeasingHeightFrom;
/* 895:    */   }
/* 896:    */   
/* 897:    */   public int getCurrentLeasingHeightTo()
/* 898:    */   {
/* 899:725 */     return this.currentLeasingHeightTo;
/* 900:    */   }
/* 901:    */   
/* 902:    */   public int getNextLeasingHeightFrom()
/* 903:    */   {
/* 904:729 */     return this.nextLeasingHeightFrom;
/* 905:    */   }
/* 906:    */   
/* 907:    */   public int getNextLeasingHeightTo()
/* 908:    */   {
/* 909:733 */     return this.nextLeasingHeightTo;
/* 910:    */   }
/* 911:    */   
/* 912:    */   void leaseEffectiveBalance(long paramLong, short paramShort)
/* 913:    */   {
/* 914:737 */     Account localAccount = getAccount(paramLong);
/* 915:738 */     if ((localAccount != null) && (localAccount.getPublicKey() != null))
/* 916:    */     {
/* 917:739 */       int i = Nxt.getBlockchain().getHeight();
/* 918:740 */       if (this.currentLeasingHeightFrom == Integer.MAX_VALUE)
/* 919:    */       {
/* 920:741 */         this.currentLeasingHeightFrom = (i + 1440);
/* 921:742 */         this.currentLeasingHeightTo = (this.currentLeasingHeightFrom + paramShort);
/* 922:743 */         this.currentLesseeId = paramLong;
/* 923:744 */         this.nextLeasingHeightFrom = Integer.MAX_VALUE;
/* 924:745 */         accountTable.insert(this);
/* 925:746 */         leaseListeners.notify(new AccountLease(getId(), paramLong, this.currentLeasingHeightFrom, this.currentLeasingHeightTo, null), Event.LEASE_SCHEDULED);
/* 926:    */       }
/* 927:    */       else
/* 928:    */       {
/* 929:750 */         this.nextLeasingHeightFrom = (i + 1440);
/* 930:751 */         if (this.nextLeasingHeightFrom < this.currentLeasingHeightTo) {
/* 931:752 */           this.nextLeasingHeightFrom = this.currentLeasingHeightTo;
/* 932:    */         }
/* 933:754 */         this.nextLeasingHeightTo = (this.nextLeasingHeightFrom + paramShort);
/* 934:755 */         this.nextLesseeId = paramLong;
/* 935:756 */         accountTable.insert(this);
/* 936:757 */         leaseListeners.notify(new AccountLease(getId(), paramLong, this.nextLeasingHeightFrom, this.nextLeasingHeightTo, null), Event.LEASE_SCHEDULED);
/* 937:    */       }
/* 938:    */     }
/* 939:    */   }
/* 940:    */   
/* 941:    */   boolean setOrVerify(byte[] paramArrayOfByte, int paramInt)
/* 942:    */   {
/* 943:770 */     if (this.publicKey == null)
/* 944:    */     {
/* 945:771 */       if (Db.isInTransaction())
/* 946:    */       {
/* 947:772 */         this.publicKey = paramArrayOfByte;
/* 948:773 */         this.keyHeight = -1;
/* 949:774 */         accountTable.insert(this);
/* 950:    */       }
/* 951:776 */       return true;
/* 952:    */     }
/* 953:777 */     if (Arrays.equals(this.publicKey, paramArrayOfByte)) {
/* 954:778 */       return true;
/* 955:    */     }
/* 956:779 */     if (this.keyHeight == -1)
/* 957:    */     {
/* 958:780 */       Logger.logMessage("DUPLICATE KEY!!!");
/* 959:781 */       Logger.logMessage("Account key for " + Convert.toUnsignedLong(this.id) + " was already set to a different one at the same height " + ", current height is " + paramInt + ", rejecting new key");
/* 960:    */       
/* 961:783 */       return false;
/* 962:    */     }
/* 963:784 */     if (this.keyHeight >= paramInt)
/* 964:    */     {
/* 965:785 */       Logger.logMessage("DUPLICATE KEY!!!");
/* 966:786 */       if (Db.isInTransaction())
/* 967:    */       {
/* 968:787 */         Logger.logMessage("Changing key for account " + Convert.toUnsignedLong(this.id) + " at height " + paramInt + ", was previously set to a different one at height " + this.keyHeight);
/* 969:    */         
/* 970:789 */         this.publicKey = paramArrayOfByte;
/* 971:790 */         this.keyHeight = paramInt;
/* 972:791 */         accountTable.insert(this);
/* 973:    */       }
/* 974:793 */       return true;
/* 975:    */     }
/* 976:795 */     Logger.logMessage("DUPLICATE KEY!!!");
/* 977:796 */     Logger.logMessage("Invalid key for account " + Convert.toUnsignedLong(this.id) + " at height " + paramInt + ", was already set to a different one at height " + this.keyHeight);
/* 978:    */     
/* 979:798 */     return false;
/* 980:    */   }
/* 981:    */   
/* 982:    */   void apply(byte[] paramArrayOfByte, int paramInt)
/* 983:    */   {
/* 984:802 */     if (!setOrVerify(paramArrayOfByte, this.creationHeight)) {
/* 985:803 */       throw new IllegalStateException("Public key mismatch");
/* 986:    */     }
/* 987:805 */     if (this.publicKey == null) {
/* 988:806 */       throw new IllegalStateException("Public key has not been set for account " + Convert.toUnsignedLong(this.id) + " at height " + paramInt + ", key height is " + this.keyHeight);
/* 989:    */     }
/* 990:809 */     if ((this.keyHeight == -1) || (this.keyHeight > paramInt))
/* 991:    */     {
/* 992:810 */       this.keyHeight = paramInt;
/* 993:811 */       accountTable.insert(this);
/* 994:    */     }
/* 995:    */   }
/* 996:    */   
/* 997:    */   void addToAssetBalanceQNT(long paramLong1, long paramLong2)
/* 998:    */   {
/* 999:816 */     if (paramLong2 == 0L) {
/* :00:817 */       return;
/* :01:    */     }
/* :02:820 */     AccountAsset localAccountAsset = (AccountAsset)accountAssetTable.get(accountAssetDbKeyFactory.newKey(this.id, paramLong1));
/* :03:821 */     long l = localAccountAsset == null ? 0L : localAccountAsset.quantityQNT;
/* :04:822 */     l = Convert.safeAdd(l, paramLong2);
/* :05:823 */     if (localAccountAsset == null) {
/* :06:824 */       localAccountAsset = new AccountAsset(this.id, paramLong1, l, 0L, null);
/* :07:    */     } else {
/* :08:826 */       localAccountAsset.quantityQNT = l;
/* :09:    */     }
/* :10:828 */     localAccountAsset.save();
/* :11:829 */     listeners.notify(this, Event.ASSET_BALANCE);
/* :12:830 */     assetListeners.notify(localAccountAsset, Event.ASSET_BALANCE);
/* :13:    */   }
/* :14:    */   
/* :15:    */   void addToUnconfirmedAssetBalanceQNT(long paramLong1, long paramLong2)
/* :16:    */   {
/* :17:834 */     if (paramLong2 == 0L) {
/* :18:835 */       return;
/* :19:    */     }
/* :20:838 */     AccountAsset localAccountAsset = (AccountAsset)accountAssetTable.get(accountAssetDbKeyFactory.newKey(this.id, paramLong1));
/* :21:839 */     long l = localAccountAsset == null ? 0L : localAccountAsset.unconfirmedQuantityQNT;
/* :22:840 */     l = Convert.safeAdd(l, paramLong2);
/* :23:841 */     if (localAccountAsset == null) {
/* :24:842 */       localAccountAsset = new AccountAsset(this.id, paramLong1, 0L, l, null);
/* :25:    */     } else {
/* :26:844 */       localAccountAsset.unconfirmedQuantityQNT = l;
/* :27:    */     }
/* :28:846 */     localAccountAsset.save();
/* :29:847 */     listeners.notify(this, Event.UNCONFIRMED_ASSET_BALANCE);
/* :30:848 */     assetListeners.notify(localAccountAsset, Event.UNCONFIRMED_ASSET_BALANCE);
/* :31:    */   }
/* :32:    */   
/* :33:    */   void addToAssetAndUnconfirmedAssetBalanceQNT(long paramLong1, long paramLong2)
/* :34:    */   {
/* :35:852 */     if (paramLong2 == 0L) {
/* :36:853 */       return;
/* :37:    */     }
/* :38:856 */     AccountAsset localAccountAsset = (AccountAsset)accountAssetTable.get(accountAssetDbKeyFactory.newKey(this.id, paramLong1));
/* :39:857 */     long l1 = localAccountAsset == null ? 0L : localAccountAsset.quantityQNT;
/* :40:858 */     l1 = Convert.safeAdd(l1, paramLong2);
/* :41:859 */     long l2 = localAccountAsset == null ? 0L : localAccountAsset.unconfirmedQuantityQNT;
/* :42:860 */     l2 = Convert.safeAdd(l2, paramLong2);
/* :43:861 */     if (localAccountAsset == null)
/* :44:    */     {
/* :45:862 */       localAccountAsset = new AccountAsset(this.id, paramLong1, l1, l2, null);
/* :46:    */     }
/* :47:    */     else
/* :48:    */     {
/* :49:864 */       localAccountAsset.quantityQNT = l1;
/* :50:865 */       localAccountAsset.unconfirmedQuantityQNT = l2;
/* :51:    */     }
/* :52:867 */     localAccountAsset.save();
/* :53:868 */     listeners.notify(this, Event.ASSET_BALANCE);
/* :54:869 */     listeners.notify(this, Event.UNCONFIRMED_ASSET_BALANCE);
/* :55:870 */     assetListeners.notify(localAccountAsset, Event.ASSET_BALANCE);
/* :56:871 */     assetListeners.notify(localAccountAsset, Event.UNCONFIRMED_ASSET_BALANCE);
/* :57:    */   }
/* :58:    */   
/* :59:    */   void addToBalanceNQT(long paramLong)
/* :60:    */   {
/* :61:875 */     if (paramLong == 0L) {
/* :62:876 */       return;
/* :63:    */     }
/* :64:878 */     this.balanceNQT = Convert.safeAdd(this.balanceNQT, paramLong);
/* :65:879 */     addToGuaranteedBalanceNQT(paramLong);
/* :66:880 */     checkBalance(this.id, this.balanceNQT, this.unconfirmedBalanceNQT);
/* :67:881 */     accountTable.insert(this);
/* :68:882 */     listeners.notify(this, Event.BALANCE);
/* :69:    */   }
/* :70:    */   
/* :71:    */   void addToUnconfirmedBalanceNQT(long paramLong)
/* :72:    */   {
/* :73:886 */     if (paramLong == 0L) {
/* :74:887 */       return;
/* :75:    */     }
/* :76:889 */     this.unconfirmedBalanceNQT = Convert.safeAdd(this.unconfirmedBalanceNQT, paramLong);
/* :77:890 */     checkBalance(this.id, this.balanceNQT, this.unconfirmedBalanceNQT);
/* :78:891 */     accountTable.insert(this);
/* :79:892 */     listeners.notify(this, Event.UNCONFIRMED_BALANCE);
/* :80:    */   }
/* :81:    */   
/* :82:    */   void addToBalanceAndUnconfirmedBalanceNQT(long paramLong)
/* :83:    */   {
/* :84:896 */     if (paramLong == 0L) {
/* :85:897 */       return;
/* :86:    */     }
/* :87:899 */     this.balanceNQT = Convert.safeAdd(this.balanceNQT, paramLong);
/* :88:900 */     this.unconfirmedBalanceNQT = Convert.safeAdd(this.unconfirmedBalanceNQT, paramLong);
/* :89:901 */     addToGuaranteedBalanceNQT(paramLong);
/* :90:902 */     checkBalance(this.id, this.balanceNQT, this.unconfirmedBalanceNQT);
/* :91:903 */     accountTable.insert(this);
/* :92:904 */     listeners.notify(this, Event.BALANCE);
/* :93:905 */     listeners.notify(this, Event.UNCONFIRMED_BALANCE);
/* :94:    */   }
/* :95:    */   
/* :96:    */   void addToForgedBalanceNQT(long paramLong)
/* :97:    */   {
/* :98:909 */     if (paramLong == 0L) {
/* :99:910 */       return;
/* ;00:    */     }
/* ;01:912 */     this.forgedBalanceNQT = Convert.safeAdd(this.forgedBalanceNQT, paramLong);
/* ;02:913 */     accountTable.insert(this);
/* ;03:    */   }
/* ;04:    */   
/* ;05:    */   private static void checkBalance(long paramLong1, long paramLong2, long paramLong3)
/* ;06:    */   {
/* ;07:917 */     if (paramLong2 < 0L) {
/* ;08:918 */       throw new DoubleSpendingException("Negative balance or quantity for account " + Convert.toUnsignedLong(paramLong1));
/* ;09:    */     }
/* ;10:920 */     if (paramLong3 < 0L) {
/* ;11:921 */       throw new DoubleSpendingException("Negative unconfirmed balance or quantity for account " + Convert.toUnsignedLong(paramLong1));
/* ;12:    */     }
/* ;13:923 */     if (paramLong3 > paramLong2) {
/* ;14:924 */       throw new DoubleSpendingException("Unconfirmed exceeds confirmed balance or quantity for account " + Convert.toUnsignedLong(paramLong1));
/* ;15:    */     }
/* ;16:    */   }
/* ;17:    */   
/* ;18:    */   private void addToGuaranteedBalanceNQT(long paramLong)
/* ;19:    */   {
/* ;20:929 */     if (paramLong <= 0L) {
/* ;21:930 */       return;
/* ;22:    */     }
/* ;23:932 */     int i = Nxt.getBlockchain().getHeight();
/* ;24:    */     try
/* ;25:    */     {
/* ;26:933 */       Connection localConnection = Db.getConnection();Object localObject1 = null;
/* ;27:    */       try
/* ;28:    */       {
/* ;29:934 */         PreparedStatement localPreparedStatement1 = localConnection.prepareStatement("SELECT additions FROM account_guaranteed_balance WHERE account_id = ? and height = ?");Object localObject2 = null;
/* ;30:    */         try
/* ;31:    */         {
/* ;32:936 */           PreparedStatement localPreparedStatement2 = localConnection.prepareStatement("MERGE INTO account_guaranteed_balance (account_id,  additions, height) KEY (account_id, height) VALUES(?, ?, ?)");Object localObject3 = null;
/* ;33:    */           try
/* ;34:    */           {
/* ;35:938 */             localPreparedStatement1.setLong(1, this.id);
/* ;36:939 */             localPreparedStatement1.setInt(2, i);
/* ;37:940 */             ResultSet localResultSet = localPreparedStatement1.executeQuery();Object localObject4 = null;
/* ;38:    */             try
/* ;39:    */             {
/* ;40:941 */               long l = paramLong;
/* ;41:942 */               if (localResultSet.next()) {
/* ;42:943 */                 l = Convert.safeAdd(l, localResultSet.getLong("additions"));
/* ;43:    */               }
/* ;44:945 */               localPreparedStatement2.setLong(1, this.id);
/* ;45:946 */               localPreparedStatement2.setLong(2, l);
/* ;46:947 */               localPreparedStatement2.setInt(3, i);
/* ;47:948 */               localPreparedStatement2.executeUpdate();
/* ;48:    */             }
/* ;49:    */             catch (Throwable localThrowable8)
/* ;50:    */             {
/* ;51:940 */               localObject4 = localThrowable8;throw localThrowable8;
/* ;52:    */             }
/* ;53:    */             finally {}
/* ;54:    */           }
/* ;55:    */           catch (Throwable localThrowable6)
/* ;56:    */           {
/* ;57:933 */             localObject3 = localThrowable6;throw localThrowable6;
/* ;58:    */           }
/* ;59:    */           finally {}
/* ;60:    */         }
/* ;61:    */         catch (Throwable localThrowable4)
/* ;62:    */         {
/* ;63:933 */           localObject2 = localThrowable4;throw localThrowable4;
/* ;64:    */         }
/* ;65:    */         finally {}
/* ;66:    */       }
/* ;67:    */       catch (Throwable localThrowable2)
/* ;68:    */       {
/* ;69:933 */         localObject1 = localThrowable2;throw localThrowable2;
/* ;70:    */       }
/* ;71:    */       finally
/* ;72:    */       {
/* ;73:950 */         if (localConnection != null) {
/* ;74:950 */           if (localObject1 != null) {
/* ;75:    */             try
/* ;76:    */             {
/* ;77:950 */               localConnection.close();
/* ;78:    */             }
/* ;79:    */             catch (Throwable localThrowable12)
/* ;80:    */             {
/* ;81:950 */               ((Throwable)localObject1).addSuppressed(localThrowable12);
/* ;82:    */             }
/* ;83:    */           } else {
/* ;84:950 */             localConnection.close();
/* ;85:    */           }
/* ;86:    */         }
/* ;87:    */       }
/* ;88:    */     }
/* ;89:    */     catch (SQLException localSQLException)
/* ;90:    */     {
/* ;91:951 */       throw new RuntimeException(localSQLException.toString(), localSQLException);
/* ;92:    */     }
/* ;93:    */   }
/* ;94:    */   
/* ;95:    */   /* Error */
/* ;96:    */   public static int getAssetAccountsCount(long paramLong)
/* ;97:    */   {
/* ;98:    */     // Byte code:
/* ;99:    */     //   0: invokestatic 22	nxt/db/Db:getConnection	()Ljava/sql/Connection;
/* <00:    */     //   3: astore_2
/* <01:    */     //   4: aconst_null
/* <02:    */     //   5: astore_3
/* <03:    */     //   6: aload_2
/* <04:    */     //   7: ldc 23
/* <05:    */     //   9: invokeinterface 24 2 0
/* <06:    */     //   14: astore 4
/* <07:    */     //   16: aconst_null
/* <08:    */     //   17: astore 5
/* <09:    */     //   19: aload 4
/* <10:    */     //   21: iconst_1
/* <11:    */     //   22: lload_0
/* <12:    */     //   23: invokeinterface 25 4 0
/* <13:    */     //   28: aload 4
/* <14:    */     //   30: invokeinterface 26 1 0
/* <15:    */     //   35: astore 6
/* <16:    */     //   37: aconst_null
/* <17:    */     //   38: astore 7
/* <18:    */     //   40: aload 6
/* <19:    */     //   42: invokeinterface 27 1 0
/* <20:    */     //   47: pop
/* <21:    */     //   48: aload 6
/* <22:    */     //   50: iconst_1
/* <23:    */     //   51: invokeinterface 28 2 0
/* <24:    */     //   56: istore 8
/* <25:    */     //   58: aload 6
/* <26:    */     //   60: ifnull +37 -> 97
/* <27:    */     //   63: aload 7
/* <28:    */     //   65: ifnull +25 -> 90
/* <29:    */     //   68: aload 6
/* <30:    */     //   70: invokeinterface 29 1 0
/* <31:    */     //   75: goto +22 -> 97
/* <32:    */     //   78: astore 9
/* <33:    */     //   80: aload 7
/* <34:    */     //   82: aload 9
/* <35:    */     //   84: invokevirtual 31	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* <36:    */     //   87: goto +10 -> 97
/* <37:    */     //   90: aload 6
/* <38:    */     //   92: invokeinterface 29 1 0
/* <39:    */     //   97: aload 4
/* <40:    */     //   99: ifnull +37 -> 136
/* <41:    */     //   102: aload 5
/* <42:    */     //   104: ifnull +25 -> 129
/* <43:    */     //   107: aload 4
/* <44:    */     //   109: invokeinterface 32 1 0
/* <45:    */     //   114: goto +22 -> 136
/* <46:    */     //   117: astore 9
/* <47:    */     //   119: aload 5
/* <48:    */     //   121: aload 9
/* <49:    */     //   123: invokevirtual 31	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* <50:    */     //   126: goto +10 -> 136
/* <51:    */     //   129: aload 4
/* <52:    */     //   131: invokeinterface 32 1 0
/* <53:    */     //   136: aload_2
/* <54:    */     //   137: ifnull +33 -> 170
/* <55:    */     //   140: aload_3
/* <56:    */     //   141: ifnull +23 -> 164
/* <57:    */     //   144: aload_2
/* <58:    */     //   145: invokeinterface 33 1 0
/* <59:    */     //   150: goto +20 -> 170
/* <60:    */     //   153: astore 9
/* <61:    */     //   155: aload_3
/* <62:    */     //   156: aload 9
/* <63:    */     //   158: invokevirtual 31	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* <64:    */     //   161: goto +9 -> 170
/* <65:    */     //   164: aload_2
/* <66:    */     //   165: invokeinterface 33 1 0
/* <67:    */     //   170: iload 8
/* <68:    */     //   172: ireturn
/* <69:    */     //   173: astore 8
/* <70:    */     //   175: aload 8
/* <71:    */     //   177: astore 7
/* <72:    */     //   179: aload 8
/* <73:    */     //   181: athrow
/* <74:    */     //   182: astore 10
/* <75:    */     //   184: aload 6
/* <76:    */     //   186: ifnull +37 -> 223
/* <77:    */     //   189: aload 7
/* <78:    */     //   191: ifnull +25 -> 216
/* <79:    */     //   194: aload 6
/* <80:    */     //   196: invokeinterface 29 1 0
/* <81:    */     //   201: goto +22 -> 223
/* <82:    */     //   204: astore 11
/* <83:    */     //   206: aload 7
/* <84:    */     //   208: aload 11
/* <85:    */     //   210: invokevirtual 31	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* <86:    */     //   213: goto +10 -> 223
/* <87:    */     //   216: aload 6
/* <88:    */     //   218: invokeinterface 29 1 0
/* <89:    */     //   223: aload 10
/* <90:    */     //   225: athrow
/* <91:    */     //   226: astore 6
/* <92:    */     //   228: aload 6
/* <93:    */     //   230: astore 5
/* <94:    */     //   232: aload 6
/* <95:    */     //   234: athrow
/* <96:    */     //   235: astore 12
/* <97:    */     //   237: aload 4
/* <98:    */     //   239: ifnull +37 -> 276
/* <99:    */     //   242: aload 5
/* =00:    */     //   244: ifnull +25 -> 269
/* =01:    */     //   247: aload 4
/* =02:    */     //   249: invokeinterface 32 1 0
/* =03:    */     //   254: goto +22 -> 276
/* =04:    */     //   257: astore 13
/* =05:    */     //   259: aload 5
/* =06:    */     //   261: aload 13
/* =07:    */     //   263: invokevirtual 31	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* =08:    */     //   266: goto +10 -> 276
/* =09:    */     //   269: aload 4
/* =10:    */     //   271: invokeinterface 32 1 0
/* =11:    */     //   276: aload 12
/* =12:    */     //   278: athrow
/* =13:    */     //   279: astore 4
/* =14:    */     //   281: aload 4
/* =15:    */     //   283: astore_3
/* =16:    */     //   284: aload 4
/* =17:    */     //   286: athrow
/* =18:    */     //   287: astore 14
/* =19:    */     //   289: aload_2
/* =20:    */     //   290: ifnull +33 -> 323
/* =21:    */     //   293: aload_3
/* =22:    */     //   294: ifnull +23 -> 317
/* =23:    */     //   297: aload_2
/* =24:    */     //   298: invokeinterface 33 1 0
/* =25:    */     //   303: goto +20 -> 323
/* =26:    */     //   306: astore 15
/* =27:    */     //   308: aload_3
/* =28:    */     //   309: aload 15
/* =29:    */     //   311: invokevirtual 31	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* =30:    */     //   314: goto +9 -> 323
/* =31:    */     //   317: aload_2
/* =32:    */     //   318: invokeinterface 33 1 0
/* =33:    */     //   323: aload 14
/* =34:    */     //   325: athrow
/* =35:    */     //   326: astore_2
/* =36:    */     //   327: new 35	java/lang/RuntimeException
/* =37:    */     //   330: dup
/* =38:    */     //   331: aload_2
/* =39:    */     //   332: invokevirtual 36	java/sql/SQLException:toString	()Ljava/lang/String;
/* =40:    */     //   335: aload_2
/* =41:    */     //   336: invokespecial 37	java/lang/RuntimeException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
/* =42:    */     //   339: athrow
/* =43:    */     // Line number table:
/* =44:    */     //   Java source line #354	-> byte code offset #0
/* =45:    */     //   Java source line #355	-> byte code offset #6
/* =46:    */     //   Java source line #354	-> byte code offset #16
/* =47:    */     //   Java source line #356	-> byte code offset #19
/* =48:    */     //   Java source line #357	-> byte code offset #28
/* =49:    */     //   Java source line #358	-> byte code offset #40
/* =50:    */     //   Java source line #359	-> byte code offset #48
/* =51:    */     //   Java source line #360	-> byte code offset #58
/* =52:    */     //   Java source line #361	-> byte code offset #97
/* =53:    */     //   Java source line #357	-> byte code offset #173
/* =54:    */     //   Java source line #360	-> byte code offset #182
/* =55:    */     //   Java source line #354	-> byte code offset #226
/* =56:    */     //   Java source line #361	-> byte code offset #235
/* =57:    */     //   Java source line #354	-> byte code offset #279
/* =58:    */     //   Java source line #361	-> byte code offset #287
/* =59:    */     //   Java source line #362	-> byte code offset #327
/* =60:    */     // Local variable table:
/* =61:    */     //   start	length	slot	name	signature
/* =62:    */     //   0	340	0	paramLong	long
/* =63:    */     //   3	315	2	localConnection	Connection
/* =64:    */     //   326	10	2	localSQLException	SQLException
/* =65:    */     //   5	304	3	localObject1	Object
/* =66:    */     //   14	256	4	localPreparedStatement	PreparedStatement
/* =67:    */     //   279	6	4	localThrowable1	Throwable
/* =68:    */     //   17	243	5	localObject2	Object
/* =69:    */     //   35	182	6	localResultSet	ResultSet
/* =70:    */     //   226	7	6	localThrowable2	Throwable
/* =71:    */     //   38	169	7	localObject3	Object
/* =72:    */     //   56	115	8	i	int
/* =73:    */     //   173	7	8	localThrowable3	Throwable
/* =74:    */     //   78	5	9	localThrowable4	Throwable
/* =75:    */     //   117	5	9	localThrowable5	Throwable
/* =76:    */     //   153	4	9	localThrowable6	Throwable
/* =77:    */     //   182	42	10	localObject4	Object
/* =78:    */     //   204	5	11	localThrowable7	Throwable
/* =79:    */     //   235	42	12	localObject5	Object
/* =80:    */     //   257	5	13	localThrowable8	Throwable
/* =81:    */     //   287	37	14	localObject6	Object
/* =82:    */     //   306	4	15	localThrowable9	Throwable
/* =83:    */     // Exception table:
/* =84:    */     //   from	to	target	type
/* =85:    */     //   68	75	78	java/lang/Throwable
/* =86:    */     //   107	114	117	java/lang/Throwable
/* =87:    */     //   144	150	153	java/lang/Throwable
/* =88:    */     //   40	58	173	java/lang/Throwable
/* =89:    */     //   40	58	182	finally
/* =90:    */     //   173	184	182	finally
/* =91:    */     //   194	201	204	java/lang/Throwable
/* =92:    */     //   19	97	226	java/lang/Throwable
/* =93:    */     //   173	226	226	java/lang/Throwable
/* =94:    */     //   19	97	235	finally
/* =95:    */     //   173	237	235	finally
/* =96:    */     //   247	254	257	java/lang/Throwable
/* =97:    */     //   6	136	279	java/lang/Throwable
/* =98:    */     //   173	279	279	java/lang/Throwable
/* =99:    */     //   6	136	287	finally
/* >00:    */     //   173	289	287	finally
/* >01:    */     //   297	303	306	java/lang/Throwable
/* >02:    */     //   0	170	326	java/sql/SQLException
/* >03:    */     //   173	326	326	java/sql/SQLException
/* >04:    */   }
/* >05:    */   
/* >06:    */   static void init() {}
/* >07:    */   
/* >08:    */   /* Error */
/* >09:    */   public long getGuaranteedBalanceNQT(int paramInt1, int paramInt2)
/* >10:    */   {
/* >11:    */     // Byte code:
/* >12:    */     //   0: iload_1
/* >13:    */     //   1: invokestatic 78	nxt/Nxt:getBlockchain	()Lnxt/Blockchain;
/* >14:    */     //   4: invokeinterface 79 1 0
/* >15:    */     //   9: if_icmplt +5 -> 14
/* >16:    */     //   12: lconst_0
/* >17:    */     //   13: lreturn
/* >18:    */     //   14: iload_1
/* >19:    */     //   15: sipush 2880
/* >20:    */     //   18: if_icmpgt +7 -> 25
/* >21:    */     //   21: iload_1
/* >22:    */     //   22: ifge +13 -> 35
/* >23:    */     //   25: new 130	java/lang/IllegalArgumentException
/* >24:    */     //   28: dup
/* >25:    */     //   29: ldc 160
/* >26:    */     //   31: invokespecial 132	java/lang/IllegalArgumentException:<init>	(Ljava/lang/String;)V
/* >27:    */     //   34: athrow
/* >28:    */     //   35: iload_2
/* >29:    */     //   36: iload_1
/* >30:    */     //   37: isub
/* >31:    */     //   38: istore_3
/* >32:    */     //   39: invokestatic 22	nxt/db/Db:getConnection	()Ljava/sql/Connection;
/* >33:    */     //   42: astore 4
/* >34:    */     //   44: aconst_null
/* >35:    */     //   45: astore 5
/* >36:    */     //   47: aload 4
/* >37:    */     //   49: ldc 161
/* >38:    */     //   51: invokeinterface 24 2 0
/* >39:    */     //   56: astore 6
/* >40:    */     //   58: aconst_null
/* >41:    */     //   59: astore 7
/* >42:    */     //   61: aload 6
/* >43:    */     //   63: iconst_1
/* >44:    */     //   64: aload_0
/* >45:    */     //   65: getfield 77	nxt/Account:id	J
/* >46:    */     //   68: invokeinterface 25 4 0
/* >47:    */     //   73: aload 6
/* >48:    */     //   75: iconst_2
/* >49:    */     //   76: iload_3
/* >50:    */     //   77: invokeinterface 110 3 0
/* >51:    */     //   82: aload 6
/* >52:    */     //   84: iconst_3
/* >53:    */     //   85: iload_2
/* >54:    */     //   86: invokeinterface 110 3 0
/* >55:    */     //   91: aload 6
/* >56:    */     //   93: invokeinterface 26 1 0
/* >57:    */     //   98: astore 8
/* >58:    */     //   100: aconst_null
/* >59:    */     //   101: astore 9
/* >60:    */     //   103: aload 8
/* >61:    */     //   105: invokeinterface 27 1 0
/* >62:    */     //   110: ifne +129 -> 239
/* >63:    */     //   113: aload_0
/* >64:    */     //   114: getfield 92	nxt/Account:balanceNQT	J
/* >65:    */     //   117: lstore 10
/* >66:    */     //   119: aload 8
/* >67:    */     //   121: ifnull +37 -> 158
/* >68:    */     //   124: aload 9
/* >69:    */     //   126: ifnull +25 -> 151
/* >70:    */     //   129: aload 8
/* >71:    */     //   131: invokeinterface 29 1 0
/* >72:    */     //   136: goto +22 -> 158
/* >73:    */     //   139: astore 12
/* >74:    */     //   141: aload 9
/* >75:    */     //   143: aload 12
/* >76:    */     //   145: invokevirtual 31	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* >77:    */     //   148: goto +10 -> 158
/* >78:    */     //   151: aload 8
/* >79:    */     //   153: invokeinterface 29 1 0
/* >80:    */     //   158: aload 6
/* >81:    */     //   160: ifnull +37 -> 197
/* >82:    */     //   163: aload 7
/* >83:    */     //   165: ifnull +25 -> 190
/* >84:    */     //   168: aload 6
/* >85:    */     //   170: invokeinterface 32 1 0
/* >86:    */     //   175: goto +22 -> 197
/* >87:    */     //   178: astore 12
/* >88:    */     //   180: aload 7
/* >89:    */     //   182: aload 12
/* >90:    */     //   184: invokevirtual 31	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* >91:    */     //   187: goto +10 -> 197
/* >92:    */     //   190: aload 6
/* >93:    */     //   192: invokeinterface 32 1 0
/* >94:    */     //   197: aload 4
/* >95:    */     //   199: ifnull +37 -> 236
/* >96:    */     //   202: aload 5
/* >97:    */     //   204: ifnull +25 -> 229
/* >98:    */     //   207: aload 4
/* >99:    */     //   209: invokeinterface 33 1 0
/* ?00:    */     //   214: goto +22 -> 236
/* ?01:    */     //   217: astore 12
/* ?02:    */     //   219: aload 5
/* ?03:    */     //   221: aload 12
/* ?04:    */     //   223: invokevirtual 31	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* ?05:    */     //   226: goto +10 -> 236
/* ?06:    */     //   229: aload 4
/* ?07:    */     //   231: invokeinterface 33 1 0
/* ?08:    */     //   236: lload 10
/* ?09:    */     //   238: lreturn
/* ?10:    */     //   239: aload_0
/* ?11:    */     //   240: getfield 92	nxt/Account:balanceNQT	J
/* ?12:    */     //   243: aload 8
/* ?13:    */     //   245: ldc 162
/* ?14:    */     //   247: invokeinterface 83 2 0
/* ?15:    */     //   252: invokestatic 163	nxt/util/Convert:safeSubtract	(JJ)J
/* ?16:    */     //   255: lconst_0
/* ?17:    */     //   256: invokestatic 164	java/lang/Math:max	(JJ)J
/* ?18:    */     //   259: lstore 10
/* ?19:    */     //   261: aload 8
/* ?20:    */     //   263: ifnull +37 -> 300
/* ?21:    */     //   266: aload 9
/* ?22:    */     //   268: ifnull +25 -> 293
/* ?23:    */     //   271: aload 8
/* ?24:    */     //   273: invokeinterface 29 1 0
/* ?25:    */     //   278: goto +22 -> 300
/* ?26:    */     //   281: astore 12
/* ?27:    */     //   283: aload 9
/* ?28:    */     //   285: aload 12
/* ?29:    */     //   287: invokevirtual 31	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* ?30:    */     //   290: goto +10 -> 300
/* ?31:    */     //   293: aload 8
/* ?32:    */     //   295: invokeinterface 29 1 0
/* ?33:    */     //   300: aload 6
/* ?34:    */     //   302: ifnull +37 -> 339
/* ?35:    */     //   305: aload 7
/* ?36:    */     //   307: ifnull +25 -> 332
/* ?37:    */     //   310: aload 6
/* ?38:    */     //   312: invokeinterface 32 1 0
/* ?39:    */     //   317: goto +22 -> 339
/* ?40:    */     //   320: astore 12
/* ?41:    */     //   322: aload 7
/* ?42:    */     //   324: aload 12
/* ?43:    */     //   326: invokevirtual 31	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* ?44:    */     //   329: goto +10 -> 339
/* ?45:    */     //   332: aload 6
/* ?46:    */     //   334: invokeinterface 32 1 0
/* ?47:    */     //   339: aload 4
/* ?48:    */     //   341: ifnull +37 -> 378
/* ?49:    */     //   344: aload 5
/* ?50:    */     //   346: ifnull +25 -> 371
/* ?51:    */     //   349: aload 4
/* ?52:    */     //   351: invokeinterface 33 1 0
/* ?53:    */     //   356: goto +22 -> 378
/* ?54:    */     //   359: astore 12
/* ?55:    */     //   361: aload 5
/* ?56:    */     //   363: aload 12
/* ?57:    */     //   365: invokevirtual 31	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* ?58:    */     //   368: goto +10 -> 378
/* ?59:    */     //   371: aload 4
/* ?60:    */     //   373: invokeinterface 33 1 0
/* ?61:    */     //   378: lload 10
/* ?62:    */     //   380: lreturn
/* ?63:    */     //   381: astore 10
/* ?64:    */     //   383: aload 10
/* ?65:    */     //   385: astore 9
/* ?66:    */     //   387: aload 10
/* ?67:    */     //   389: athrow
/* ?68:    */     //   390: astore 13
/* ?69:    */     //   392: aload 8
/* ?70:    */     //   394: ifnull +37 -> 431
/* ?71:    */     //   397: aload 9
/* ?72:    */     //   399: ifnull +25 -> 424
/* ?73:    */     //   402: aload 8
/* ?74:    */     //   404: invokeinterface 29 1 0
/* ?75:    */     //   409: goto +22 -> 431
/* ?76:    */     //   412: astore 14
/* ?77:    */     //   414: aload 9
/* ?78:    */     //   416: aload 14
/* ?79:    */     //   418: invokevirtual 31	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* ?80:    */     //   421: goto +10 -> 431
/* ?81:    */     //   424: aload 8
/* ?82:    */     //   426: invokeinterface 29 1 0
/* ?83:    */     //   431: aload 13
/* ?84:    */     //   433: athrow
/* ?85:    */     //   434: astore 8
/* ?86:    */     //   436: aload 8
/* ?87:    */     //   438: astore 7
/* ?88:    */     //   440: aload 8
/* ?89:    */     //   442: athrow
/* ?90:    */     //   443: astore 15
/* ?91:    */     //   445: aload 6
/* ?92:    */     //   447: ifnull +37 -> 484
/* ?93:    */     //   450: aload 7
/* ?94:    */     //   452: ifnull +25 -> 477
/* ?95:    */     //   455: aload 6
/* ?96:    */     //   457: invokeinterface 32 1 0
/* ?97:    */     //   462: goto +22 -> 484
/* ?98:    */     //   465: astore 16
/* ?99:    */     //   467: aload 7
/* @00:    */     //   469: aload 16
/* @01:    */     //   471: invokevirtual 31	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* @02:    */     //   474: goto +10 -> 484
/* @03:    */     //   477: aload 6
/* @04:    */     //   479: invokeinterface 32 1 0
/* @05:    */     //   484: aload 15
/* @06:    */     //   486: athrow
/* @07:    */     //   487: astore 6
/* @08:    */     //   489: aload 6
/* @09:    */     //   491: astore 5
/* @10:    */     //   493: aload 6
/* @11:    */     //   495: athrow
/* @12:    */     //   496: astore 17
/* @13:    */     //   498: aload 4
/* @14:    */     //   500: ifnull +37 -> 537
/* @15:    */     //   503: aload 5
/* @16:    */     //   505: ifnull +25 -> 530
/* @17:    */     //   508: aload 4
/* @18:    */     //   510: invokeinterface 33 1 0
/* @19:    */     //   515: goto +22 -> 537
/* @20:    */     //   518: astore 18
/* @21:    */     //   520: aload 5
/* @22:    */     //   522: aload 18
/* @23:    */     //   524: invokevirtual 31	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* @24:    */     //   527: goto +10 -> 537
/* @25:    */     //   530: aload 4
/* @26:    */     //   532: invokeinterface 33 1 0
/* @27:    */     //   537: aload 17
/* @28:    */     //   539: athrow
/* @29:    */     //   540: astore 4
/* @30:    */     //   542: new 35	java/lang/RuntimeException
/* @31:    */     //   545: dup
/* @32:    */     //   546: aload 4
/* @33:    */     //   548: invokevirtual 36	java/sql/SQLException:toString	()Ljava/lang/String;
/* @34:    */     //   551: aload 4
/* @35:    */     //   553: invokespecial 37	java/lang/RuntimeException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
/* @36:    */     //   556: athrow
/* @37:    */     // Line number table:
/* @38:    */     //   Java source line #627	-> byte code offset #0
/* @39:    */     //   Java source line #628	-> byte code offset #12
/* @40:    */     //   Java source line #630	-> byte code offset #14
/* @41:    */     //   Java source line #631	-> byte code offset #25
/* @42:    */     //   Java source line #633	-> byte code offset #35
/* @43:    */     //   Java source line #634	-> byte code offset #39
/* @44:    */     //   Java source line #635	-> byte code offset #47
/* @45:    */     //   Java source line #634	-> byte code offset #58
/* @46:    */     //   Java source line #637	-> byte code offset #61
/* @47:    */     //   Java source line #638	-> byte code offset #73
/* @48:    */     //   Java source line #639	-> byte code offset #82
/* @49:    */     //   Java source line #640	-> byte code offset #91
/* @50:    */     //   Java source line #641	-> byte code offset #103
/* @51:    */     //   Java source line #642	-> byte code offset #113
/* @52:    */     //   Java source line #645	-> byte code offset #119
/* @53:    */     //   Java source line #646	-> byte code offset #158
/* @54:    */     //   Java source line #644	-> byte code offset #239
/* @55:    */     //   Java source line #645	-> byte code offset #261
/* @56:    */     //   Java source line #646	-> byte code offset #300
/* @57:    */     //   Java source line #640	-> byte code offset #381
/* @58:    */     //   Java source line #645	-> byte code offset #390
/* @59:    */     //   Java source line #634	-> byte code offset #434
/* @60:    */     //   Java source line #646	-> byte code offset #443
/* @61:    */     //   Java source line #634	-> byte code offset #487
/* @62:    */     //   Java source line #646	-> byte code offset #496
/* @63:    */     //   Java source line #647	-> byte code offset #542
/* @64:    */     // Local variable table:
/* @65:    */     //   start	length	slot	name	signature
/* @66:    */     //   0	557	0	this	Account
/* @67:    */     //   0	557	1	paramInt1	int
/* @68:    */     //   0	557	2	paramInt2	int
/* @69:    */     //   38	39	3	i	int
/* @70:    */     //   42	489	4	localConnection	Connection
/* @71:    */     //   540	12	4	localSQLException	SQLException
/* @72:    */     //   45	476	5	localObject1	Object
/* @73:    */     //   56	422	6	localPreparedStatement	PreparedStatement
/* @74:    */     //   487	7	6	localThrowable1	Throwable
/* @75:    */     //   59	409	7	localObject2	Object
/* @76:    */     //   98	327	8	localResultSet	ResultSet
/* @77:    */     //   434	7	8	localThrowable2	Throwable
/* @78:    */     //   101	314	9	localObject3	Object
/* @79:    */     //   117	262	10	l	long
/* @80:    */     //   381	7	10	localThrowable3	Throwable
/* @81:    */     //   139	5	12	localThrowable4	Throwable
/* @82:    */     //   178	5	12	localThrowable5	Throwable
/* @83:    */     //   217	5	12	localThrowable6	Throwable
/* @84:    */     //   281	5	12	localThrowable7	Throwable
/* @85:    */     //   320	5	12	localThrowable8	Throwable
/* @86:    */     //   359	5	12	localThrowable9	Throwable
/* @87:    */     //   390	42	13	localObject4	Object
/* @88:    */     //   412	5	14	localThrowable10	Throwable
/* @89:    */     //   443	42	15	localObject5	Object
/* @90:    */     //   465	5	16	localThrowable11	Throwable
/* @91:    */     //   496	42	17	localObject6	Object
/* @92:    */     //   518	5	18	localThrowable12	Throwable
/* @93:    */     // Exception table:
/* @94:    */     //   from	to	target	type
/* @95:    */     //   129	136	139	java/lang/Throwable
/* @96:    */     //   168	175	178	java/lang/Throwable
/* @97:    */     //   207	214	217	java/lang/Throwable
/* @98:    */     //   271	278	281	java/lang/Throwable
/* @99:    */     //   310	317	320	java/lang/Throwable
/* A00:    */     //   349	356	359	java/lang/Throwable
/* A01:    */     //   103	119	381	java/lang/Throwable
/* A02:    */     //   239	261	381	java/lang/Throwable
/* A03:    */     //   103	119	390	finally
/* A04:    */     //   239	261	390	finally
/* A05:    */     //   381	392	390	finally
/* A06:    */     //   402	409	412	java/lang/Throwable
/* A07:    */     //   61	158	434	java/lang/Throwable
/* A08:    */     //   239	300	434	java/lang/Throwable
/* A09:    */     //   381	434	434	java/lang/Throwable
/* A10:    */     //   61	158	443	finally
/* A11:    */     //   239	300	443	finally
/* A12:    */     //   381	445	443	finally
/* A13:    */     //   455	462	465	java/lang/Throwable
/* A14:    */     //   47	197	487	java/lang/Throwable
/* A15:    */     //   239	339	487	java/lang/Throwable
/* A16:    */     //   381	487	487	java/lang/Throwable
/* A17:    */     //   47	197	496	finally
/* A18:    */     //   239	339	496	finally
/* A19:    */     //   381	498	496	finally
/* A20:    */     //   508	515	518	java/lang/Throwable
/* A21:    */     //   39	236	540	java/sql/SQLException
/* A22:    */     //   239	378	540	java/sql/SQLException
/* A23:    */     //   381	540	540	java/sql/SQLException
/* A24:    */   }
/* A25:    */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.Account
 * JD-Core Version:    0.7.1
 */