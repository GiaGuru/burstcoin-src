/*   1:    */ package nxt;
/*   2:    */ 
/*   3:    */ import java.sql.Connection;
/*   4:    */ import java.sql.PreparedStatement;
/*   5:    */ import java.sql.ResultSet;
/*   6:    */ import java.sql.SQLException;
/*   7:    */ import java.util.ArrayList;
/*   8:    */ import java.util.Collection;
/*   9:    */ import java.util.Iterator;
/*  10:    */ import java.util.List;
/*  11:    */ import java.util.concurrent.ConcurrentSkipListSet;
/*  12:    */ import nxt.db.Db;
/*  13:    */ import nxt.db.DbClause;
/*  14:    */ import nxt.db.DbClause.LongClause;
/*  15:    */ import nxt.db.DbIterator;
/*  16:    */ import nxt.db.DbKey;
/*  17:    */ import nxt.db.DbKey.Factory;
/*  18:    */ import nxt.db.DbKey.LinkKeyFactory;
/*  19:    */ import nxt.db.DbKey.LongKeyFactory;
/*  20:    */ import nxt.db.VersionedEntityDbTable;
/*  21:    */ 
/*  22:    */ public class Escrow
/*  23:    */ {
/*  24:    */   public static enum DecisionType
/*  25:    */   {
/*  26: 33 */     UNDECIDED,  RELEASE,  REFUND,  SPLIT;
/*  27:    */     
/*  28:    */     private DecisionType() {}
/*  29:    */   }
/*  30:    */   
/*  31:    */   public static String decisionToString(DecisionType paramDecisionType)
/*  32:    */   {
/*  33: 40 */     switch (paramDecisionType)
/*  34:    */     {
/*  35:    */     case UNDECIDED: 
/*  36: 42 */       return "undecided";
/*  37:    */     case RELEASE: 
/*  38: 44 */       return "release";
/*  39:    */     case REFUND: 
/*  40: 46 */       return "refund";
/*  41:    */     case SPLIT: 
/*  42: 48 */       return "split";
/*  43:    */     }
/*  44: 51 */     return null;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public static DecisionType stringToDecision(String paramString)
/*  48:    */   {
/*  49: 55 */     switch (paramString)
/*  50:    */     {
/*  51:    */     case "undecided": 
/*  52: 57 */       return DecisionType.UNDECIDED;
/*  53:    */     case "release": 
/*  54: 59 */       return DecisionType.RELEASE;
/*  55:    */     case "refund": 
/*  56: 61 */       return DecisionType.REFUND;
/*  57:    */     case "split": 
/*  58: 63 */       return DecisionType.SPLIT;
/*  59:    */     }
/*  60: 66 */     return null;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public static Byte decisionToByte(DecisionType paramDecisionType)
/*  64:    */   {
/*  65: 70 */     switch (paramDecisionType)
/*  66:    */     {
/*  67:    */     case UNDECIDED: 
/*  68: 72 */       return Byte.valueOf((byte)0);
/*  69:    */     case RELEASE: 
/*  70: 74 */       return Byte.valueOf((byte)1);
/*  71:    */     case REFUND: 
/*  72: 76 */       return Byte.valueOf((byte)2);
/*  73:    */     case SPLIT: 
/*  74: 78 */       return Byte.valueOf((byte)3);
/*  75:    */     }
/*  76: 81 */     return null;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public static DecisionType byteToDecision(Byte paramByte)
/*  80:    */   {
/*  81: 85 */     switch (paramByte.byteValue())
/*  82:    */     {
/*  83:    */     case 0: 
/*  84: 87 */       return DecisionType.UNDECIDED;
/*  85:    */     case 1: 
/*  86: 89 */       return DecisionType.RELEASE;
/*  87:    */     case 2: 
/*  88: 91 */       return DecisionType.REFUND;
/*  89:    */     case 3: 
/*  90: 93 */       return DecisionType.SPLIT;
/*  91:    */     }
/*  92: 96 */     return null;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public static boolean isEnabled()
/*  96:    */   {
/*  97:100 */     if (Nxt.getBlockchain().getLastBlock().getHeight() >= Constants.BURST_ESCROW_START_BLOCK) {
/*  98:101 */       return true;
/*  99:    */     }
/* 100:104 */     Alias localAlias = Alias.getAlias("featureescrow");
/* 101:105 */     if ((localAlias != null) && (localAlias.getAliasURI().equals("enabled"))) {
/* 102:106 */       return true;
/* 103:    */     }
/* 104:108 */     return false;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public static class Decision
/* 108:    */   {
/* 109:    */     private final Long escrowId;
/* 110:    */     private final Long accountId;
/* 111:    */     private final DbKey dbKey;
/* 112:    */     private Escrow.DecisionType decision;
/* 113:    */     
/* 114:    */     private Decision(Long paramLong1, Long paramLong2, Escrow.DecisionType paramDecisionType)
/* 115:    */     {
/* 116:119 */       this.escrowId = paramLong1;
/* 117:120 */       this.accountId = paramLong2;
/* 118:121 */       this.dbKey = Escrow.decisionDbKeyFactory.newKey(this.escrowId.longValue(), this.accountId.longValue());
/* 119:122 */       this.decision = paramDecisionType;
/* 120:    */     }
/* 121:    */     
/* 122:    */     private Decision(ResultSet paramResultSet)
/* 123:    */       throws SQLException
/* 124:    */     {
/* 125:126 */       this.escrowId = Long.valueOf(paramResultSet.getLong("escrow_id"));
/* 126:127 */       this.accountId = Long.valueOf(paramResultSet.getLong("account_id"));
/* 127:128 */       this.dbKey = Escrow.decisionDbKeyFactory.newKey(this.escrowId.longValue(), this.accountId.longValue());
/* 128:129 */       this.decision = Escrow.byteToDecision(Byte.valueOf((byte)paramResultSet.getInt("decision")));
/* 129:    */     }
/* 130:    */     
/* 131:    */     private void save(Connection paramConnection)
/* 132:    */       throws SQLException
/* 133:    */     {
/* 134:133 */       PreparedStatement localPreparedStatement = paramConnection.prepareStatement("MERGE INTO escrow_decision (escrow_id, account_id, decision, height, latest) KEY (escrow_id, account_id, height) VALUES (?, ?, ?, ?, TRUE)");Object localObject1 = null;
/* 135:    */       try
/* 136:    */       {
/* 137:135 */         int i = 0;
/* 138:136 */         localPreparedStatement.setLong(++i, this.escrowId.longValue());
/* 139:137 */         localPreparedStatement.setLong(++i, this.accountId.longValue());
/* 140:138 */         localPreparedStatement.setInt(++i, Escrow.decisionToByte(this.decision).byteValue());
/* 141:139 */         localPreparedStatement.setInt(++i, Nxt.getBlockchain().getHeight());
/* 142:140 */         localPreparedStatement.executeUpdate();
/* 143:    */       }
/* 144:    */       catch (Throwable localThrowable2)
/* 145:    */       {
/* 146:133 */         localObject1 = localThrowable2;throw localThrowable2;
/* 147:    */       }
/* 148:    */       finally
/* 149:    */       {
/* 150:141 */         if (localPreparedStatement != null) {
/* 151:141 */           if (localObject1 != null) {
/* 152:    */             try
/* 153:    */             {
/* 154:141 */               localPreparedStatement.close();
/* 155:    */             }
/* 156:    */             catch (Throwable localThrowable3)
/* 157:    */             {
/* 158:141 */               ((Throwable)localObject1).addSuppressed(localThrowable3);
/* 159:    */             }
/* 160:    */           } else {
/* 161:141 */             localPreparedStatement.close();
/* 162:    */           }
/* 163:    */         }
/* 164:    */       }
/* 165:    */     }
/* 166:    */     
/* 167:    */     public Long getEscrowId()
/* 168:    */     {
/* 169:145 */       return this.escrowId;
/* 170:    */     }
/* 171:    */     
/* 172:    */     public Long getAccountId()
/* 173:    */     {
/* 174:149 */       return this.accountId;
/* 175:    */     }
/* 176:    */     
/* 177:    */     public Escrow.DecisionType getDecision()
/* 178:    */     {
/* 179:153 */       return this.decision;
/* 180:    */     }
/* 181:    */     
/* 182:    */     public void setDecision(Escrow.DecisionType paramDecisionType)
/* 183:    */     {
/* 184:157 */       this.decision = paramDecisionType;
/* 185:    */     }
/* 186:    */   }
/* 187:    */   
/* 188:161 */   private static final DbKey.LongKeyFactory<Escrow> escrowDbKeyFactory = new DbKey.LongKeyFactory("id")
/* 189:    */   {
/* 190:    */     public DbKey newKey(Escrow paramAnonymousEscrow)
/* 191:    */     {
/* 192:164 */       return paramAnonymousEscrow.dbKey;
/* 193:    */     }
/* 194:    */   };
/* 195:168 */   private static final VersionedEntityDbTable<Escrow> escrowTable = new VersionedEntityDbTable("escrow", escrowDbKeyFactory)
/* 196:    */   {
/* 197:    */     protected Escrow load(Connection paramAnonymousConnection, ResultSet paramAnonymousResultSet)
/* 198:    */       throws SQLException
/* 199:    */     {
/* 200:171 */       return new Escrow(paramAnonymousResultSet, null);
/* 201:    */     }
/* 202:    */     
/* 203:    */     protected void save(Connection paramAnonymousConnection, Escrow paramAnonymousEscrow)
/* 204:    */       throws SQLException
/* 205:    */     {
/* 206:175 */       paramAnonymousEscrow.save(paramAnonymousConnection);
/* 207:    */     }
/* 208:    */   };
/* 209:179 */   private static final DbKey.LinkKeyFactory<Decision> decisionDbKeyFactory = new DbKey.LinkKeyFactory("escrow_id", "account_id")
/* 210:    */   {
/* 211:    */     public DbKey newKey(Escrow.Decision paramAnonymousDecision)
/* 212:    */     {
/* 213:182 */       return paramAnonymousDecision.dbKey;
/* 214:    */     }
/* 215:    */   };
/* 216:186 */   private static final VersionedEntityDbTable<Decision> decisionTable = new VersionedEntityDbTable("escrow_decision", decisionDbKeyFactory)
/* 217:    */   {
/* 218:    */     protected Escrow.Decision load(Connection paramAnonymousConnection, ResultSet paramAnonymousResultSet)
/* 219:    */       throws SQLException
/* 220:    */     {
/* 221:189 */       return new Escrow.Decision(paramAnonymousResultSet, null);
/* 222:    */     }
/* 223:    */     
/* 224:    */     protected void save(Connection paramAnonymousConnection, Escrow.Decision paramAnonymousDecision)
/* 225:    */       throws SQLException
/* 226:    */     {
/* 227:193 */       paramAnonymousDecision.save(paramAnonymousConnection);
/* 228:    */     }
/* 229:    */   };
/* 230:197 */   private static final ConcurrentSkipListSet<Long> updatedEscrowIds = new ConcurrentSkipListSet();
/* 231:198 */   private static final List<TransactionImpl> resultTransactions = new ArrayList();
/* 232:    */   private final Long senderId;
/* 233:    */   private final Long recipientId;
/* 234:    */   private final Long id;
/* 235:    */   private final DbKey dbKey;
/* 236:    */   private final Long amountNQT;
/* 237:    */   private final int requiredSigners;
/* 238:    */   private final int deadline;
/* 239:    */   private final DecisionType deadlineAction;
/* 240:    */   
/* 241:    */   public static DbIterator<Escrow> getAllEscrowTransactions()
/* 242:    */   {
/* 243:201 */     return escrowTable.getAll(0, -1);
/* 244:    */   }
/* 245:    */   
/* 246:    */   private static DbClause getEscrowParticipentClause(final long paramLong)
/* 247:    */   {
/* 248:205 */     new DbClause(" (sender_id = ? OR recipient_id = ?) ")
/* 249:    */     {
/* 250:    */       public int set(PreparedStatement paramAnonymousPreparedStatement, int paramAnonymousInt)
/* 251:    */         throws SQLException
/* 252:    */       {
/* 253:208 */         paramAnonymousPreparedStatement.setLong(paramAnonymousInt++, paramLong);
/* 254:209 */         paramAnonymousPreparedStatement.setLong(paramAnonymousInt++, paramLong);
/* 255:210 */         return paramAnonymousInt;
/* 256:    */       }
/* 257:    */     };
/* 258:    */   }
/* 259:    */   
/* 260:    */   public static Collection<Escrow> getEscrowTransactionsByParticipent(Long paramLong)
/* 261:    */   {
/* 262:216 */     ArrayList localArrayList = new ArrayList();
/* 263:217 */     DbIterator localDbIterator = decisionTable.getManyBy(new DbClause.LongClause("account_id", paramLong.longValue()), 0, -1);
/* 264:218 */     while (localDbIterator.hasNext())
/* 265:    */     {
/* 266:219 */       Decision localDecision = (Decision)localDbIterator.next();
/* 267:220 */       Escrow localEscrow = (Escrow)escrowTable.get(escrowDbKeyFactory.newKey(localDecision.escrowId.longValue()));
/* 268:221 */       if (localEscrow != null) {
/* 269:222 */         localArrayList.add(localEscrow);
/* 270:    */       }
/* 271:    */     }
/* 272:225 */     return localArrayList;
/* 273:    */   }
/* 274:    */   
/* 275:    */   public static Escrow getEscrowTransaction(Long paramLong)
/* 276:    */   {
/* 277:229 */     return (Escrow)escrowTable.get(escrowDbKeyFactory.newKey(paramLong.longValue()));
/* 278:    */   }
/* 279:    */   
/* 280:    */   public static void addEscrowTransaction(Account paramAccount1, Account paramAccount2, Long paramLong1, Long paramLong2, int paramInt1, Collection<Long> paramCollection, int paramInt2, DecisionType paramDecisionType)
/* 281:    */   {
/* 282:240 */     Escrow localEscrow = new Escrow(paramAccount1, paramAccount2, paramLong1, paramLong2, paramInt1, paramInt2, paramDecisionType);
/* 283:    */     
/* 284:    */ 
/* 285:    */ 
/* 286:    */ 
/* 287:    */ 
/* 288:    */ 
/* 289:247 */     escrowTable.insert(localEscrow);
/* 290:    */     
/* 291:249 */     Decision localDecision1 = new Decision(paramLong1, Long.valueOf(paramAccount1.getId()), DecisionType.UNDECIDED, null);
/* 292:250 */     decisionTable.insert(localDecision1);
/* 293:251 */     Decision localDecision2 = new Decision(paramLong1, Long.valueOf(paramAccount2.getId()), DecisionType.UNDECIDED, null);
/* 294:252 */     decisionTable.insert(localDecision2);
/* 295:253 */     for (Long localLong : paramCollection)
/* 296:    */     {
/* 297:254 */       Decision localDecision3 = new Decision(paramLong1, localLong, DecisionType.UNDECIDED, null);
/* 298:255 */       decisionTable.insert(localDecision3);
/* 299:    */     }
/* 300:    */   }
/* 301:    */   
/* 302:    */   public static void removeEscrowTransaction(Long paramLong)
/* 303:    */   {
/* 304:260 */     Escrow localEscrow = (Escrow)escrowTable.get(escrowDbKeyFactory.newKey(paramLong.longValue()));
/* 305:261 */     if (localEscrow == null) {
/* 306:262 */       return;
/* 307:    */     }
/* 308:264 */     DbIterator localDbIterator = localEscrow.getDecisions();
/* 309:    */     
/* 310:266 */     ArrayList localArrayList = new ArrayList();
/* 311:267 */     while (localDbIterator.hasNext())
/* 312:    */     {
/* 313:268 */       localObject = (Decision)localDbIterator.next();
/* 314:269 */       localArrayList.add(localObject);
/* 315:    */     }
/* 316:272 */     for (Object localObject = localArrayList.iterator(); ((Iterator)localObject).hasNext();)
/* 317:    */     {
/* 318:272 */       Decision localDecision = (Decision)((Iterator)localObject).next();
/* 319:273 */       decisionTable.delete(localDecision);
/* 320:    */     }
/* 321:275 */     escrowTable.delete(localEscrow);
/* 322:    */   }
/* 323:    */   
/* 324:    */   private static DbClause getUpdateOnBlockClause(final int paramInt)
/* 325:    */   {
/* 326:279 */     new DbClause(" deadline < ? ")
/* 327:    */     {
/* 328:    */       public int set(PreparedStatement paramAnonymousPreparedStatement, int paramAnonymousInt)
/* 329:    */         throws SQLException
/* 330:    */       {
/* 331:282 */         paramAnonymousPreparedStatement.setInt(paramAnonymousInt++, paramInt);
/* 332:283 */         return paramAnonymousInt;
/* 333:    */       }
/* 334:    */     };
/* 335:    */   }
/* 336:    */   
/* 337:    */   public static void updateOnBlock(Block paramBlock)
/* 338:    */   {
/* 339:289 */     resultTransactions.clear();
/* 340:    */     
/* 341:291 */     DbIterator localDbIterator = escrowTable.getManyBy(getUpdateOnBlockClause(paramBlock.getTimestamp()), 0, -1);
/* 342:292 */     for (Object localObject1 = localDbIterator.iterator(); ((Iterator)localObject1).hasNext();)
/* 343:    */     {
/* 344:292 */       localObject2 = (Escrow)((Iterator)localObject1).next();
/* 345:293 */       updatedEscrowIds.add(((Escrow)localObject2).getId());
/* 346:    */     }
/* 347:    */     Object localObject2;
/* 348:296 */     if (updatedEscrowIds.size() > 0)
/* 349:    */     {
/* 350:297 */       for (localObject1 = updatedEscrowIds.iterator(); ((Iterator)localObject1).hasNext();)
/* 351:    */       {
/* 352:297 */         localObject2 = (Long)((Iterator)localObject1).next();
/* 353:298 */         Escrow localEscrow = (Escrow)escrowTable.get(escrowDbKeyFactory.newKey(((Long)localObject2).longValue()));
/* 354:299 */         DecisionType localDecisionType = localEscrow.checkComplete();
/* 355:300 */         if ((localDecisionType != DecisionType.UNDECIDED) || (localEscrow.getDeadline() < paramBlock.getTimestamp()))
/* 356:    */         {
/* 357:301 */           if (localDecisionType == DecisionType.UNDECIDED) {
/* 358:302 */             localDecisionType = localEscrow.getDeadlineAction();
/* 359:    */           }
/* 360:304 */           localEscrow.doPayout(localDecisionType, paramBlock);
/* 361:    */           
/* 362:306 */           removeEscrowTransaction((Long)localObject2);
/* 363:    */         }
/* 364:    */       }
/* 365:309 */       if (resultTransactions.size() > 0) {
/* 366:    */         try
/* 367:    */         {
/* 368:310 */           localObject1 = Db.getConnection();localObject2 = null;
/* 369:    */           try
/* 370:    */           {
/* 371:311 */             TransactionDb.saveTransactions((Connection)localObject1, resultTransactions);
/* 372:    */           }
/* 373:    */           catch (Throwable localThrowable2)
/* 374:    */           {
/* 375:310 */             localObject2 = localThrowable2;throw localThrowable2;
/* 376:    */           }
/* 377:    */           finally
/* 378:    */           {
/* 379:312 */             if (localObject1 != null) {
/* 380:312 */               if (localObject2 != null) {
/* 381:    */                 try
/* 382:    */                 {
/* 383:312 */                   ((Connection)localObject1).close();
/* 384:    */                 }
/* 385:    */                 catch (Throwable localThrowable3)
/* 386:    */                 {
/* 387:312 */                   ((Throwable)localObject2).addSuppressed(localThrowable3);
/* 388:    */                 }
/* 389:    */               } else {
/* 390:312 */                 ((Connection)localObject1).close();
/* 391:    */               }
/* 392:    */             }
/* 393:    */           }
/* 394:    */         }
/* 395:    */         catch (SQLException localSQLException)
/* 396:    */         {
/* 397:314 */           throw new RuntimeException(localSQLException.toString(), localSQLException);
/* 398:    */         }
/* 399:    */       }
/* 400:317 */       updatedEscrowIds.clear();
/* 401:    */     }
/* 402:    */   }
/* 403:    */   
/* 404:    */   private Escrow(Account paramAccount1, Account paramAccount2, Long paramLong1, Long paramLong2, int paramInt1, int paramInt2, DecisionType paramDecisionType)
/* 405:    */   {
/* 406:337 */     this.senderId = Long.valueOf(paramAccount1.getId());
/* 407:338 */     this.recipientId = Long.valueOf(paramAccount2.getId());
/* 408:339 */     this.id = paramLong1;
/* 409:340 */     this.dbKey = escrowDbKeyFactory.newKey(this.id.longValue());
/* 410:341 */     this.amountNQT = paramLong2;
/* 411:342 */     this.requiredSigners = paramInt1;
/* 412:343 */     this.deadline = paramInt2;
/* 413:344 */     this.deadlineAction = paramDecisionType;
/* 414:    */   }
/* 415:    */   
/* 416:    */   private Escrow(ResultSet paramResultSet)
/* 417:    */     throws SQLException
/* 418:    */   {
/* 419:348 */     this.id = Long.valueOf(paramResultSet.getLong("id"));
/* 420:349 */     this.dbKey = escrowDbKeyFactory.newKey(this.id.longValue());
/* 421:350 */     this.senderId = Long.valueOf(paramResultSet.getLong("sender_id"));
/* 422:351 */     this.recipientId = Long.valueOf(paramResultSet.getLong("recipient_id"));
/* 423:352 */     this.amountNQT = Long.valueOf(paramResultSet.getLong("amount"));
/* 424:353 */     this.requiredSigners = paramResultSet.getInt("required_signers");
/* 425:354 */     this.deadline = paramResultSet.getInt("deadline");
/* 426:355 */     this.deadlineAction = byteToDecision(Byte.valueOf((byte)paramResultSet.getInt("deadline_action")));
/* 427:    */   }
/* 428:    */   
/* 429:    */   private void save(Connection paramConnection)
/* 430:    */     throws SQLException
/* 431:    */   {
/* 432:359 */     PreparedStatement localPreparedStatement = paramConnection.prepareStatement("MERGE INTO escrow (id, sender_id, recipient_id, amount, required_signers, deadline, deadline_action, height, latest) KEY (id, height) VALUES (?, ?, ?, ?, ?, ?, ?, ?, TRUE)");Object localObject1 = null;
/* 433:    */     try
/* 434:    */     {
/* 435:362 */       int i = 0;
/* 436:363 */       localPreparedStatement.setLong(++i, this.id.longValue());
/* 437:364 */       localPreparedStatement.setLong(++i, this.senderId.longValue());
/* 438:365 */       localPreparedStatement.setLong(++i, this.recipientId.longValue());
/* 439:366 */       localPreparedStatement.setLong(++i, this.amountNQT.longValue());
/* 440:367 */       localPreparedStatement.setInt(++i, this.requiredSigners);
/* 441:368 */       localPreparedStatement.setInt(++i, this.deadline);
/* 442:369 */       localPreparedStatement.setInt(++i, decisionToByte(this.deadlineAction).byteValue());
/* 443:370 */       localPreparedStatement.setInt(++i, Nxt.getBlockchain().getHeight());
/* 444:371 */       localPreparedStatement.executeUpdate();
/* 445:    */     }
/* 446:    */     catch (Throwable localThrowable2)
/* 447:    */     {
/* 448:359 */       localObject1 = localThrowable2;throw localThrowable2;
/* 449:    */     }
/* 450:    */     finally
/* 451:    */     {
/* 452:372 */       if (localPreparedStatement != null) {
/* 453:372 */         if (localObject1 != null) {
/* 454:    */           try
/* 455:    */           {
/* 456:372 */             localPreparedStatement.close();
/* 457:    */           }
/* 458:    */           catch (Throwable localThrowable3)
/* 459:    */           {
/* 460:372 */             ((Throwable)localObject1).addSuppressed(localThrowable3);
/* 461:    */           }
/* 462:    */         } else {
/* 463:372 */           localPreparedStatement.close();
/* 464:    */         }
/* 465:    */       }
/* 466:    */     }
/* 467:    */   }
/* 468:    */   
/* 469:    */   public Long getSenderId()
/* 470:    */   {
/* 471:376 */     return this.senderId;
/* 472:    */   }
/* 473:    */   
/* 474:    */   public Long getAmountNQT()
/* 475:    */   {
/* 476:380 */     return this.amountNQT;
/* 477:    */   }
/* 478:    */   
/* 479:    */   public Long getRecipientId()
/* 480:    */   {
/* 481:384 */     return this.recipientId;
/* 482:    */   }
/* 483:    */   
/* 484:    */   public Long getId()
/* 485:    */   {
/* 486:388 */     return this.id;
/* 487:    */   }
/* 488:    */   
/* 489:    */   public int getRequiredSigners()
/* 490:    */   {
/* 491:392 */     return this.requiredSigners;
/* 492:    */   }
/* 493:    */   
/* 494:    */   public DbIterator<Decision> getDecisions()
/* 495:    */   {
/* 496:396 */     return decisionTable.getManyBy(new DbClause.LongClause("escrow_id", this.id.longValue()), 0, -1);
/* 497:    */   }
/* 498:    */   
/* 499:    */   public int getDeadline()
/* 500:    */   {
/* 501:400 */     return this.deadline;
/* 502:    */   }
/* 503:    */   
/* 504:    */   public DecisionType getDeadlineAction()
/* 505:    */   {
/* 506:404 */     return this.deadlineAction;
/* 507:    */   }
/* 508:    */   
/* 509:    */   public boolean isIdSigner(Long paramLong)
/* 510:    */   {
/* 511:408 */     return decisionTable.get(decisionDbKeyFactory.newKey(this.id.longValue(), paramLong.longValue())) != null;
/* 512:    */   }
/* 513:    */   
/* 514:    */   public Decision getIdDecision(Long paramLong)
/* 515:    */   {
/* 516:412 */     return (Decision)decisionTable.get(decisionDbKeyFactory.newKey(this.id.longValue(), paramLong.longValue()));
/* 517:    */   }
/* 518:    */   
/* 519:    */   public synchronized void sign(Long paramLong, DecisionType paramDecisionType)
/* 520:    */   {
/* 521:416 */     if ((paramLong.equals(this.senderId)) && (paramDecisionType != DecisionType.RELEASE)) {
/* 522:417 */       return;
/* 523:    */     }
/* 524:420 */     if ((paramLong.equals(this.recipientId)) && (paramDecisionType != DecisionType.REFUND)) {
/* 525:421 */       return;
/* 526:    */     }
/* 527:424 */     Decision localDecision = (Decision)decisionTable.get(decisionDbKeyFactory.newKey(this.id.longValue(), paramLong.longValue()));
/* 528:425 */     if (localDecision == null) {
/* 529:426 */       return;
/* 530:    */     }
/* 531:428 */     localDecision.setDecision(paramDecisionType);
/* 532:    */     
/* 533:430 */     decisionTable.insert(localDecision);
/* 534:432 */     if (!updatedEscrowIds.contains(this.id)) {
/* 535:433 */       updatedEscrowIds.add(this.id);
/* 536:    */     }
/* 537:    */   }
/* 538:    */   
/* 539:    */   private DecisionType checkComplete()
/* 540:    */   {
/* 541:438 */     Decision localDecision1 = (Decision)decisionTable.get(decisionDbKeyFactory.newKey(this.id.longValue(), this.senderId.longValue()));
/* 542:439 */     if (localDecision1.getDecision() == DecisionType.RELEASE) {
/* 543:440 */       return DecisionType.RELEASE;
/* 544:    */     }
/* 545:442 */     Decision localDecision2 = (Decision)decisionTable.get(decisionDbKeyFactory.newKey(this.id.longValue(), this.recipientId.longValue()));
/* 546:443 */     if (localDecision2.getDecision() == DecisionType.REFUND) {
/* 547:444 */       return DecisionType.REFUND;
/* 548:    */     }
/* 549:447 */     int i = 0;
/* 550:448 */     int j = 0;
/* 551:449 */     int k = 0;
/* 552:    */     
/* 553:451 */     DbIterator localDbIterator = decisionTable.getManyBy(new DbClause.LongClause("escrow_id", this.id.longValue()), 0, -1);
/* 554:452 */     while (localDbIterator.hasNext())
/* 555:    */     {
/* 556:453 */       Decision localDecision3 = (Decision)localDbIterator.next();
/* 557:454 */       if ((!localDecision3.getAccountId().equals(this.senderId)) && (!localDecision3.getAccountId().equals(this.recipientId))) {
/* 558:458 */         switch (localDecision3.getDecision())
/* 559:    */         {
/* 560:    */         case RELEASE: 
/* 561:460 */           i++;
/* 562:461 */           break;
/* 563:    */         case REFUND: 
/* 564:463 */           j++;
/* 565:464 */           break;
/* 566:    */         case SPLIT: 
/* 567:466 */           k++;
/* 568:    */         }
/* 569:    */       }
/* 570:    */     }
/* 571:473 */     if (i >= this.requiredSigners) {
/* 572:474 */       return DecisionType.RELEASE;
/* 573:    */     }
/* 574:476 */     if (j >= this.requiredSigners) {
/* 575:477 */       return DecisionType.REFUND;
/* 576:    */     }
/* 577:479 */     if (k >= this.requiredSigners) {
/* 578:480 */       return DecisionType.SPLIT;
/* 579:    */     }
/* 580:483 */     return DecisionType.UNDECIDED;
/* 581:    */   }
/* 582:    */   
/* 583:    */   private synchronized void doPayout(DecisionType paramDecisionType, Block paramBlock)
/* 584:    */   {
/* 585:487 */     switch (paramDecisionType)
/* 586:    */     {
/* 587:    */     case RELEASE: 
/* 588:489 */       Account.getAccount(this.recipientId.longValue()).addToBalanceAndUnconfirmedBalanceNQT(this.amountNQT.longValue());
/* 589:490 */       saveResultTransaction(paramBlock, this.id, this.recipientId, this.amountNQT, DecisionType.RELEASE);
/* 590:491 */       break;
/* 591:    */     case REFUND: 
/* 592:493 */       Account.getAccount(this.senderId.longValue()).addToBalanceAndUnconfirmedBalanceNQT(this.amountNQT.longValue());
/* 593:494 */       saveResultTransaction(paramBlock, this.id, this.senderId, this.amountNQT, DecisionType.REFUND);
/* 594:495 */       break;
/* 595:    */     case SPLIT: 
/* 596:497 */       Long localLong = Long.valueOf(this.amountNQT.longValue() / 2L);
/* 597:498 */       Account.getAccount(this.recipientId.longValue()).addToBalanceAndUnconfirmedBalanceNQT(localLong.longValue());
/* 598:499 */       Account.getAccount(this.senderId.longValue()).addToBalanceAndUnconfirmedBalanceNQT(this.amountNQT.longValue() - localLong.longValue());
/* 599:500 */       saveResultTransaction(paramBlock, this.id, this.recipientId, localLong, DecisionType.SPLIT);
/* 600:501 */       saveResultTransaction(paramBlock, this.id, this.senderId, Long.valueOf(this.amountNQT.longValue() - localLong.longValue()), DecisionType.SPLIT);
/* 601:502 */       break;
/* 602:    */     }
/* 603:    */   }
/* 604:    */   
/* 605:    */   private static void saveResultTransaction(Block paramBlock, Long paramLong1, Long paramLong2, Long paramLong3, DecisionType paramDecisionType)
/* 606:    */   {
/* 607:509 */     Attachment.AdvancedPaymentEscrowResult localAdvancedPaymentEscrowResult = new Attachment.AdvancedPaymentEscrowResult(paramLong1, paramDecisionType);
/* 608:510 */     TransactionImpl.BuilderImpl localBuilderImpl = new TransactionImpl.BuilderImpl((byte)1, Genesis.CREATOR_PUBLIC_KEY, paramLong3.longValue(), 0L, paramBlock.getTimestamp(), (short)1440, localAdvancedPaymentEscrowResult);
/* 609:    */     
/* 610:512 */     localBuilderImpl.senderId(0L).recipientId(paramLong2.longValue()).blockId(paramBlock.getId()).height(paramBlock.getHeight()).blockTimestamp(paramBlock.getTimestamp()).ecBlockHeight(0).ecBlockId(0L);
/* 611:    */     
/* 612:    */ 
/* 613:    */ 
/* 614:    */ 
/* 615:    */ 
/* 616:    */ 
/* 617:    */ 
/* 618:520 */     TransactionImpl localTransactionImpl = null;
/* 619:    */     try
/* 620:    */     {
/* 621:522 */       localTransactionImpl = localBuilderImpl.build();
/* 622:    */     }
/* 623:    */     catch (NxtException.NotValidException localNotValidException)
/* 624:    */     {
/* 625:525 */       throw new RuntimeException(localNotValidException.toString(), localNotValidException);
/* 626:    */     }
/* 627:528 */     if (!TransactionDb.hasTransaction(localTransactionImpl.getId())) {
/* 628:529 */       resultTransactions.add(localTransactionImpl);
/* 629:    */     }
/* 630:    */   }
/* 631:    */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.Escrow
 * JD-Core Version:    0.7.1
 */