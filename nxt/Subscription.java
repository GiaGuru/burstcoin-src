/*   1:    */ package nxt;
/*   2:    */ 
/*   3:    */ import java.sql.Connection;
/*   4:    */ import java.sql.PreparedStatement;
/*   5:    */ import java.sql.ResultSet;
/*   6:    */ import java.sql.SQLException;
/*   7:    */ import java.util.ArrayList;
/*   8:    */ import java.util.HashSet;
/*   9:    */ import java.util.Iterator;
/*  10:    */ import java.util.List;
/*  11:    */ import java.util.Set;
/*  12:    */ import nxt.db.Db;
/*  13:    */ import nxt.db.DbClause;
/*  14:    */ import nxt.db.DbClause.LongClause;
/*  15:    */ import nxt.db.DbIterator;
/*  16:    */ import nxt.db.DbKey;
/*  17:    */ import nxt.db.DbKey.Factory;
/*  18:    */ import nxt.db.DbKey.LongKeyFactory;
/*  19:    */ import nxt.db.VersionedEntityDbTable;
/*  20:    */ import nxt.util.Convert;
/*  21:    */ 
/*  22:    */ public class Subscription
/*  23:    */ {
/*  24:    */   public static boolean isEnabled()
/*  25:    */   {
/*  26: 27 */     if (Nxt.getBlockchain().getLastBlock().getHeight() >= Constants.BURST_SUBSCRIPTION_START_BLOCK) {
/*  27: 28 */       return true;
/*  28:    */     }
/*  29: 31 */     Alias localAlias = Alias.getAlias("featuresubscription");
/*  30: 32 */     if ((localAlias != null) && (localAlias.getAliasURI().equals("enabled"))) {
/*  31: 33 */       return true;
/*  32:    */     }
/*  33: 36 */     return false;
/*  34:    */   }
/*  35:    */   
/*  36: 39 */   private static final DbKey.LongKeyFactory<Subscription> subscriptionDbKeyFactory = new DbKey.LongKeyFactory("id")
/*  37:    */   {
/*  38:    */     public DbKey newKey(Subscription paramAnonymousSubscription)
/*  39:    */     {
/*  40: 42 */       return paramAnonymousSubscription.dbKey;
/*  41:    */     }
/*  42:    */   };
/*  43: 46 */   private static final VersionedEntityDbTable<Subscription> subscriptionTable = new VersionedEntityDbTable("subscription", subscriptionDbKeyFactory)
/*  44:    */   {
/*  45:    */     protected Subscription load(Connection paramAnonymousConnection, ResultSet paramAnonymousResultSet)
/*  46:    */       throws SQLException
/*  47:    */     {
/*  48: 49 */       return new Subscription(paramAnonymousResultSet, null);
/*  49:    */     }
/*  50:    */     
/*  51:    */     protected void save(Connection paramAnonymousConnection, Subscription paramAnonymousSubscription)
/*  52:    */       throws SQLException
/*  53:    */     {
/*  54: 53 */       paramAnonymousSubscription.save(paramAnonymousConnection);
/*  55:    */     }
/*  56:    */     
/*  57:    */     protected String defaultSort()
/*  58:    */     {
/*  59: 57 */       return " ORDER BY time_next ASC, id ASC ";
/*  60:    */     }
/*  61:    */   };
/*  62: 61 */   private static final List<TransactionImpl> paymentTransactions = new ArrayList();
/*  63: 62 */   private static final List<Subscription> appliedSubscriptions = new ArrayList();
/*  64: 63 */   private static final Set<Long> removeSubscriptions = new HashSet();
/*  65:    */   private final Long senderId;
/*  66:    */   private final Long recipientId;
/*  67:    */   private final Long id;
/*  68:    */   private final DbKey dbKey;
/*  69:    */   private final Long amountNQT;
/*  70:    */   private final int frequency;
/*  71:    */   private volatile int timeNext;
/*  72:    */   
/*  73:    */   public static long getFee()
/*  74:    */   {
/*  75: 66 */     return 100000000L;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public static DbIterator<Subscription> getAllSubscriptions()
/*  79:    */   {
/*  80: 70 */     return subscriptionTable.getAll(0, -1);
/*  81:    */   }
/*  82:    */   
/*  83:    */   private static DbClause getByParticipantClause(final long paramLong)
/*  84:    */   {
/*  85: 74 */     new DbClause(" (sender_id = ? OR recipient_id = ?) ")
/*  86:    */     {
/*  87:    */       public int set(PreparedStatement paramAnonymousPreparedStatement, int paramAnonymousInt)
/*  88:    */         throws SQLException
/*  89:    */       {
/*  90: 77 */         paramAnonymousPreparedStatement.setLong(paramAnonymousInt++, paramLong);
/*  91: 78 */         paramAnonymousPreparedStatement.setLong(paramAnonymousInt++, paramLong);
/*  92: 79 */         return paramAnonymousInt;
/*  93:    */       }
/*  94:    */     };
/*  95:    */   }
/*  96:    */   
/*  97:    */   public static DbIterator<Subscription> getSubscriptionsByParticipant(Long paramLong)
/*  98:    */   {
/*  99: 85 */     return subscriptionTable.getManyBy(getByParticipantClause(paramLong.longValue()), 0, -1);
/* 100:    */   }
/* 101:    */   
/* 102:    */   public static DbIterator<Subscription> getIdSubscriptions(Long paramLong)
/* 103:    */   {
/* 104: 89 */     return subscriptionTable.getManyBy(new DbClause.LongClause("sender_id", paramLong.longValue()), 0, -1);
/* 105:    */   }
/* 106:    */   
/* 107:    */   public static DbIterator<Subscription> getSubscriptionsToId(Long paramLong)
/* 108:    */   {
/* 109: 93 */     return subscriptionTable.getManyBy(new DbClause.LongClause("recipient_id", paramLong.longValue()), 0, -1);
/* 110:    */   }
/* 111:    */   
/* 112:    */   public static Subscription getSubscription(Long paramLong)
/* 113:    */   {
/* 114: 97 */     return (Subscription)subscriptionTable.get(subscriptionDbKeyFactory.newKey(paramLong.longValue()));
/* 115:    */   }
/* 116:    */   
/* 117:    */   public static void addSubscription(Account paramAccount1, Account paramAccount2, Long paramLong1, Long paramLong2, int paramInt1, int paramInt2)
/* 118:    */   {
/* 119:106 */     Subscription localSubscription = new Subscription(Long.valueOf(paramAccount1.getId()), Long.valueOf(paramAccount2.getId()), paramLong1, paramLong2, paramInt2, paramInt1);
/* 120:    */     
/* 121:    */ 
/* 122:    */ 
/* 123:    */ 
/* 124:    */ 
/* 125:    */ 
/* 126:113 */     subscriptionTable.insert(localSubscription);
/* 127:    */   }
/* 128:    */   
/* 129:    */   public static void removeSubscription(Long paramLong)
/* 130:    */   {
/* 131:117 */     Subscription localSubscription = (Subscription)subscriptionTable.get(subscriptionDbKeyFactory.newKey(paramLong.longValue()));
/* 132:118 */     if (localSubscription != null) {
/* 133:119 */       subscriptionTable.delete(localSubscription);
/* 134:    */     }
/* 135:    */   }
/* 136:    */   
/* 137:    */   private static DbClause getUpdateOnBlockClause(final int paramInt)
/* 138:    */   {
/* 139:124 */     new DbClause(" time_next <= ? ")
/* 140:    */     {
/* 141:    */       public int set(PreparedStatement paramAnonymousPreparedStatement, int paramAnonymousInt)
/* 142:    */         throws SQLException
/* 143:    */       {
/* 144:127 */         paramAnonymousPreparedStatement.setInt(paramAnonymousInt++, paramInt);
/* 145:128 */         return paramAnonymousInt;
/* 146:    */       }
/* 147:    */     };
/* 148:    */   }
/* 149:    */   
/* 150:    */   public static long calculateFees(int paramInt)
/* 151:    */   {
/* 152:135 */     long l = 0L;
/* 153:136 */     DbIterator localDbIterator = subscriptionTable.getManyBy(getUpdateOnBlockClause(paramInt), 0, -1);
/* 154:137 */     ArrayList localArrayList = new ArrayList();
/* 155:138 */     for (Iterator localIterator = localDbIterator.iterator(); localIterator.hasNext();)
/* 156:    */     {
/* 157:138 */       localSubscription = (Subscription)localIterator.next();
/* 158:139 */       if (!removeSubscriptions.contains(localSubscription.getId())) {
/* 159:142 */         if (localSubscription.applyUnconfirmed()) {
/* 160:143 */           localArrayList.add(localSubscription);
/* 161:    */         }
/* 162:    */       }
/* 163:    */     }
/* 164:    */     Subscription localSubscription;
/* 165:146 */     if (localArrayList.size() > 0) {
/* 166:147 */       for (localIterator = localArrayList.iterator(); localIterator.hasNext();)
/* 167:    */       {
/* 168:147 */         localSubscription = (Subscription)localIterator.next();
/* 169:148 */         l = Convert.safeAdd(l, getFee());
/* 170:149 */         localSubscription.undoUnconfirmed();
/* 171:    */       }
/* 172:    */     }
/* 173:152 */     return l;
/* 174:    */   }
/* 175:    */   
/* 176:    */   public static void clearRemovals()
/* 177:    */   {
/* 178:156 */     removeSubscriptions.clear();
/* 179:    */   }
/* 180:    */   
/* 181:    */   public static void addRemoval(Long paramLong)
/* 182:    */   {
/* 183:160 */     removeSubscriptions.add(paramLong);
/* 184:    */   }
/* 185:    */   
/* 186:    */   public static long applyUnconfirmed(int paramInt)
/* 187:    */   {
/* 188:165 */     appliedSubscriptions.clear();
/* 189:166 */     long l = 0L;
/* 190:167 */     DbIterator localDbIterator = subscriptionTable.getManyBy(getUpdateOnBlockClause(paramInt), 0, -1);
/* 191:168 */     for (Subscription localSubscription : localDbIterator) {
/* 192:169 */       if (!removeSubscriptions.contains(localSubscription.getId())) {
/* 193:172 */         if (localSubscription.applyUnconfirmed())
/* 194:    */         {
/* 195:173 */           appliedSubscriptions.add(localSubscription);
/* 196:174 */           l += getFee();
/* 197:    */         }
/* 198:    */         else
/* 199:    */         {
/* 200:177 */           removeSubscriptions.add(localSubscription.getId());
/* 201:    */         }
/* 202:    */       }
/* 203:    */     }
/* 204:180 */     return l;
/* 205:    */   }
/* 206:    */   
/* 207:    */   public static void applyConfirmed(Block paramBlock)
/* 208:    */   {
/* 209:190 */     paymentTransactions.clear();
/* 210:191 */     for (Object localObject1 = appliedSubscriptions.iterator(); ((Iterator)localObject1).hasNext();)
/* 211:    */     {
/* 212:191 */       localObject2 = (Subscription)((Iterator)localObject1).next();
/* 213:192 */       ((Subscription)localObject2).apply(paramBlock);
/* 214:193 */       subscriptionTable.insert(localObject2);
/* 215:    */     }
/* 216:    */     Object localObject2;
/* 217:195 */     if (paymentTransactions.size() > 0) {
/* 218:    */       try
/* 219:    */       {
/* 220:196 */         localObject1 = Db.getConnection();localObject2 = null;
/* 221:    */         try
/* 222:    */         {
/* 223:197 */           TransactionDb.saveTransactions((Connection)localObject1, paymentTransactions);
/* 224:    */         }
/* 225:    */         catch (Throwable localThrowable2)
/* 226:    */         {
/* 227:196 */           localObject2 = localThrowable2;throw localThrowable2;
/* 228:    */         }
/* 229:    */         finally
/* 230:    */         {
/* 231:198 */           if (localObject1 != null) {
/* 232:198 */             if (localObject2 != null) {
/* 233:    */               try
/* 234:    */               {
/* 235:198 */                 ((Connection)localObject1).close();
/* 236:    */               }
/* 237:    */               catch (Throwable localThrowable3)
/* 238:    */               {
/* 239:198 */                 ((Throwable)localObject2).addSuppressed(localThrowable3);
/* 240:    */               }
/* 241:    */             } else {
/* 242:198 */               ((Connection)localObject1).close();
/* 243:    */             }
/* 244:    */           }
/* 245:    */         }
/* 246:    */       }
/* 247:    */       catch (SQLException localSQLException)
/* 248:    */       {
/* 249:200 */         throw new RuntimeException(localSQLException.toString(), localSQLException);
/* 250:    */       }
/* 251:    */     }
/* 252:203 */     for (Iterator localIterator = removeSubscriptions.iterator(); localIterator.hasNext();)
/* 253:    */     {
/* 254:203 */       localObject2 = (Long)localIterator.next();
/* 255:204 */       removeSubscription((Long)localObject2);
/* 256:    */     }
/* 257:    */   }
/* 258:    */   
/* 259:    */   private Subscription(Long paramLong1, Long paramLong2, Long paramLong3, Long paramLong4, int paramInt1, int paramInt2)
/* 260:    */   {
/* 261:222 */     this.senderId = paramLong1;
/* 262:223 */     this.recipientId = paramLong2;
/* 263:224 */     this.id = paramLong3;
/* 264:225 */     this.dbKey = subscriptionDbKeyFactory.newKey(this.id.longValue());
/* 265:226 */     this.amountNQT = paramLong4;
/* 266:227 */     this.frequency = paramInt1;
/* 267:228 */     this.timeNext = (paramInt2 + paramInt1);
/* 268:    */   }
/* 269:    */   
/* 270:    */   private Subscription(ResultSet paramResultSet)
/* 271:    */     throws SQLException
/* 272:    */   {
/* 273:232 */     this.id = Long.valueOf(paramResultSet.getLong("id"));
/* 274:233 */     this.dbKey = subscriptionDbKeyFactory.newKey(this.id.longValue());
/* 275:234 */     this.senderId = Long.valueOf(paramResultSet.getLong("sender_id"));
/* 276:235 */     this.recipientId = Long.valueOf(paramResultSet.getLong("recipient_id"));
/* 277:236 */     this.amountNQT = Long.valueOf(paramResultSet.getLong("amount"));
/* 278:237 */     this.frequency = paramResultSet.getInt("frequency");
/* 279:238 */     this.timeNext = paramResultSet.getInt("time_next");
/* 280:    */   }
/* 281:    */   
/* 282:    */   private void save(Connection paramConnection)
/* 283:    */     throws SQLException
/* 284:    */   {
/* 285:242 */     PreparedStatement localPreparedStatement = paramConnection.prepareStatement("MERGE INTO subscription (id, sender_id, recipient_id, amount, frequency, time_next, height, latest) KEY (id, height) VALUES (?, ?, ?, ?, ?, ?, ?, TRUE)");Object localObject1 = null;
/* 286:    */     try
/* 287:    */     {
/* 288:245 */       int i = 0;
/* 289:246 */       localPreparedStatement.setLong(++i, this.id.longValue());
/* 290:247 */       localPreparedStatement.setLong(++i, this.senderId.longValue());
/* 291:248 */       localPreparedStatement.setLong(++i, this.recipientId.longValue());
/* 292:249 */       localPreparedStatement.setLong(++i, this.amountNQT.longValue());
/* 293:250 */       localPreparedStatement.setInt(++i, this.frequency);
/* 294:251 */       localPreparedStatement.setInt(++i, this.timeNext);
/* 295:252 */       localPreparedStatement.setInt(++i, Nxt.getBlockchain().getHeight());
/* 296:253 */       localPreparedStatement.executeUpdate();
/* 297:    */     }
/* 298:    */     catch (Throwable localThrowable2)
/* 299:    */     {
/* 300:242 */       localObject1 = localThrowable2;throw localThrowable2;
/* 301:    */     }
/* 302:    */     finally
/* 303:    */     {
/* 304:254 */       if (localPreparedStatement != null) {
/* 305:254 */         if (localObject1 != null) {
/* 306:    */           try
/* 307:    */           {
/* 308:254 */             localPreparedStatement.close();
/* 309:    */           }
/* 310:    */           catch (Throwable localThrowable3)
/* 311:    */           {
/* 312:254 */             ((Throwable)localObject1).addSuppressed(localThrowable3);
/* 313:    */           }
/* 314:    */         } else {
/* 315:254 */           localPreparedStatement.close();
/* 316:    */         }
/* 317:    */       }
/* 318:    */     }
/* 319:    */   }
/* 320:    */   
/* 321:    */   public Long getSenderId()
/* 322:    */   {
/* 323:258 */     return this.senderId;
/* 324:    */   }
/* 325:    */   
/* 326:    */   public Long getAmountNQT()
/* 327:    */   {
/* 328:262 */     return this.amountNQT;
/* 329:    */   }
/* 330:    */   
/* 331:    */   public Long getRecipientId()
/* 332:    */   {
/* 333:266 */     return this.recipientId;
/* 334:    */   }
/* 335:    */   
/* 336:    */   public Long getId()
/* 337:    */   {
/* 338:270 */     return this.id;
/* 339:    */   }
/* 340:    */   
/* 341:    */   public int getFrequency()
/* 342:    */   {
/* 343:274 */     return this.frequency;
/* 344:    */   }
/* 345:    */   
/* 346:    */   public int getTimeNext()
/* 347:    */   {
/* 348:278 */     return this.timeNext;
/* 349:    */   }
/* 350:    */   
/* 351:    */   private boolean applyUnconfirmed()
/* 352:    */   {
/* 353:282 */     Account localAccount = Account.getAccount(this.senderId.longValue());
/* 354:283 */     long l = Convert.safeAdd(this.amountNQT.longValue(), getFee());
/* 355:285 */     if (localAccount.getUnconfirmedBalanceNQT() < l) {
/* 356:286 */       return false;
/* 357:    */     }
/* 358:289 */     localAccount.addToUnconfirmedBalanceNQT(-l);
/* 359:    */     
/* 360:291 */     return true;
/* 361:    */   }
/* 362:    */   
/* 363:    */   private void undoUnconfirmed()
/* 364:    */   {
/* 365:295 */     Account localAccount = Account.getAccount(this.senderId.longValue());
/* 366:296 */     long l = Convert.safeAdd(this.amountNQT.longValue(), getFee());
/* 367:    */     
/* 368:298 */     localAccount.addToUnconfirmedBalanceNQT(l);
/* 369:    */   }
/* 370:    */   
/* 371:    */   private void apply(Block paramBlock)
/* 372:    */   {
/* 373:302 */     Account localAccount1 = Account.getAccount(this.senderId.longValue());
/* 374:303 */     Account localAccount2 = Account.getAccount(this.recipientId.longValue());
/* 375:    */     
/* 376:305 */     long l = Convert.safeAdd(this.amountNQT.longValue(), getFee());
/* 377:    */     
/* 378:307 */     localAccount1.addToBalanceNQT(-l);
/* 379:308 */     localAccount2.addToBalanceAndUnconfirmedBalanceNQT(this.amountNQT.longValue());
/* 380:    */     
/* 381:310 */     Attachment.AdvancedPaymentSubscriptionPayment localAdvancedPaymentSubscriptionPayment = new Attachment.AdvancedPaymentSubscriptionPayment(this.id);
/* 382:311 */     TransactionImpl.BuilderImpl localBuilderImpl = new TransactionImpl.BuilderImpl((byte)1, localAccount1.getPublicKey(), this.amountNQT.longValue(), getFee(), this.timeNext, (short)1440, localAdvancedPaymentSubscriptionPayment);
/* 383:    */     try
/* 384:    */     {
/* 385:317 */       localBuilderImpl.senderId(this.senderId.longValue()).recipientId(this.recipientId.longValue()).blockId(paramBlock.getId()).height(paramBlock.getHeight()).blockTimestamp(paramBlock.getTimestamp()).ecBlockHeight(0).ecBlockId(0L);
/* 386:    */       
/* 387:    */ 
/* 388:    */ 
/* 389:    */ 
/* 390:    */ 
/* 391:    */ 
/* 392:324 */       TransactionImpl localTransactionImpl = localBuilderImpl.build();
/* 393:325 */       if (!TransactionDb.hasTransaction(localTransactionImpl.getId())) {
/* 394:326 */         paymentTransactions.add(localTransactionImpl);
/* 395:    */       }
/* 396:    */     }
/* 397:    */     catch (NxtException.NotValidException localNotValidException)
/* 398:    */     {
/* 399:329 */       throw new RuntimeException("Failed to build subscription payment transaction");
/* 400:    */     }
/* 401:332 */     this.timeNext += this.frequency;
/* 402:    */   }
/* 403:    */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.Subscription
 * JD-Core Version:    0.7.1
 */