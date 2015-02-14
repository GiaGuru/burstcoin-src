/*   1:    */ package nxt;
/*   2:    */ 
/*   3:    */ import java.math.BigInteger;
/*   4:    */ import java.nio.ByteBuffer;
/*   5:    */ import java.nio.ByteOrder;
/*   6:    */ import java.security.MessageDigest;
/*   7:    */ import java.util.ArrayList;
/*   8:    */ import java.util.Collections;
/*   9:    */ import java.util.List;
/*  10:    */ import java.util.Map;
/*  11:    */ import java.util.Set;
/*  12:    */ import nxt.crypto.Crypto;
/*  13:    */ import nxt.db.DbKey;
/*  14:    */ import nxt.db.DbKey.LongKeyFactory;
/*  15:    */ import nxt.util.Convert;
/*  16:    */ import nxt.util.Logger;
/*  17:    */ import org.json.simple.JSONObject;
/*  18:    */ 
/*  19:    */ final class TransactionImpl
/*  20:    */   implements Transaction
/*  21:    */ {
/*  22:    */   private final short deadline;
/*  23:    */   private final byte[] senderPublicKey;
/*  24:    */   private final long recipientId;
/*  25:    */   private final long amountNQT;
/*  26:    */   private final long feeNQT;
/*  27:    */   private final String referencedTransactionFullHash;
/*  28:    */   private final TransactionType type;
/*  29:    */   private final int ecBlockHeight;
/*  30:    */   private final long ecBlockId;
/*  31:    */   private final byte version;
/*  32:    */   private final int timestamp;
/*  33:    */   private final Attachment.AbstractAttachment attachment;
/*  34:    */   private final Appendix.Message message;
/*  35:    */   private final Appendix.EncryptedMessage encryptedMessage;
/*  36:    */   private final Appendix.EncryptToSelfMessage encryptToSelfMessage;
/*  37:    */   private final Appendix.PublicKeyAnnouncement publicKeyAnnouncement;
/*  38:    */   private final List<? extends Appendix.AbstractAppendix> appendages;
/*  39:    */   private final int appendagesSize;
/*  40:    */   
/*  41:    */   static final class BuilderImpl
/*  42:    */     implements Transaction.Builder
/*  43:    */   {
/*  44:    */     private final short deadline;
/*  45:    */     private final byte[] senderPublicKey;
/*  46:    */     private final long amountNQT;
/*  47:    */     private final long feeNQT;
/*  48:    */     private final TransactionType type;
/*  49:    */     private final byte version;
/*  50:    */     private final int timestamp;
/*  51:    */     private final Attachment.AbstractAttachment attachment;
/*  52:    */     private long recipientId;
/*  53:    */     private String referencedTransactionFullHash;
/*  54:    */     private byte[] signature;
/*  55:    */     private Appendix.Message message;
/*  56:    */     private Appendix.EncryptedMessage encryptedMessage;
/*  57:    */     private Appendix.EncryptToSelfMessage encryptToSelfMessage;
/*  58:    */     private Appendix.PublicKeyAnnouncement publicKeyAnnouncement;
/*  59:    */     private long blockId;
/*  60: 41 */     private int height = Integer.MAX_VALUE;
/*  61:    */     private long id;
/*  62:    */     private long senderId;
/*  63: 44 */     private int blockTimestamp = -1;
/*  64:    */     private String fullHash;
/*  65:    */     private int ecBlockHeight;
/*  66:    */     private long ecBlockId;
/*  67:    */     
/*  68:    */     BuilderImpl(byte paramByte, byte[] paramArrayOfByte, long paramLong1, long paramLong2, int paramInt, short paramShort, Attachment.AbstractAttachment paramAbstractAttachment)
/*  69:    */     {
/*  70: 51 */       this.version = paramByte;
/*  71: 52 */       this.timestamp = paramInt;
/*  72: 53 */       this.deadline = paramShort;
/*  73: 54 */       this.senderPublicKey = paramArrayOfByte;
/*  74: 55 */       this.amountNQT = paramLong1;
/*  75: 56 */       this.feeNQT = paramLong2;
/*  76: 57 */       this.attachment = paramAbstractAttachment;
/*  77: 58 */       this.type = paramAbstractAttachment.getTransactionType();
/*  78:    */     }
/*  79:    */     
/*  80:    */     public TransactionImpl build()
/*  81:    */       throws NxtException.NotValidException
/*  82:    */     {
/*  83: 63 */       return new TransactionImpl(this, null);
/*  84:    */     }
/*  85:    */     
/*  86:    */     public BuilderImpl recipientId(long paramLong)
/*  87:    */     {
/*  88: 68 */       this.recipientId = paramLong;
/*  89: 69 */       return this;
/*  90:    */     }
/*  91:    */     
/*  92:    */     public BuilderImpl referencedTransactionFullHash(String paramString)
/*  93:    */     {
/*  94: 74 */       this.referencedTransactionFullHash = paramString;
/*  95: 75 */       return this;
/*  96:    */     }
/*  97:    */     
/*  98:    */     BuilderImpl referencedTransactionFullHash(byte[] paramArrayOfByte)
/*  99:    */     {
/* 100: 79 */       if (paramArrayOfByte != null) {
/* 101: 80 */         this.referencedTransactionFullHash = Convert.toHexString(paramArrayOfByte);
/* 102:    */       }
/* 103: 82 */       return this;
/* 104:    */     }
/* 105:    */     
/* 106:    */     public BuilderImpl message(Appendix.Message paramMessage)
/* 107:    */     {
/* 108: 87 */       this.message = paramMessage;
/* 109: 88 */       return this;
/* 110:    */     }
/* 111:    */     
/* 112:    */     public BuilderImpl encryptedMessage(Appendix.EncryptedMessage paramEncryptedMessage)
/* 113:    */     {
/* 114: 93 */       this.encryptedMessage = paramEncryptedMessage;
/* 115: 94 */       return this;
/* 116:    */     }
/* 117:    */     
/* 118:    */     public BuilderImpl encryptToSelfMessage(Appendix.EncryptToSelfMessage paramEncryptToSelfMessage)
/* 119:    */     {
/* 120: 99 */       this.encryptToSelfMessage = paramEncryptToSelfMessage;
/* 121:100 */       return this;
/* 122:    */     }
/* 123:    */     
/* 124:    */     public BuilderImpl publicKeyAnnouncement(Appendix.PublicKeyAnnouncement paramPublicKeyAnnouncement)
/* 125:    */     {
/* 126:105 */       this.publicKeyAnnouncement = paramPublicKeyAnnouncement;
/* 127:106 */       return this;
/* 128:    */     }
/* 129:    */     
/* 130:    */     BuilderImpl id(long paramLong)
/* 131:    */     {
/* 132:110 */       this.id = paramLong;
/* 133:111 */       return this;
/* 134:    */     }
/* 135:    */     
/* 136:    */     BuilderImpl signature(byte[] paramArrayOfByte)
/* 137:    */     {
/* 138:115 */       this.signature = paramArrayOfByte;
/* 139:116 */       return this;
/* 140:    */     }
/* 141:    */     
/* 142:    */     BuilderImpl blockId(long paramLong)
/* 143:    */     {
/* 144:120 */       this.blockId = paramLong;
/* 145:121 */       return this;
/* 146:    */     }
/* 147:    */     
/* 148:    */     BuilderImpl height(int paramInt)
/* 149:    */     {
/* 150:125 */       this.height = paramInt;
/* 151:126 */       return this;
/* 152:    */     }
/* 153:    */     
/* 154:    */     BuilderImpl senderId(long paramLong)
/* 155:    */     {
/* 156:130 */       this.senderId = paramLong;
/* 157:131 */       return this;
/* 158:    */     }
/* 159:    */     
/* 160:    */     BuilderImpl fullHash(String paramString)
/* 161:    */     {
/* 162:135 */       this.fullHash = paramString;
/* 163:136 */       return this;
/* 164:    */     }
/* 165:    */     
/* 166:    */     BuilderImpl fullHash(byte[] paramArrayOfByte)
/* 167:    */     {
/* 168:140 */       if (paramArrayOfByte != null) {
/* 169:141 */         this.fullHash = Convert.toHexString(paramArrayOfByte);
/* 170:    */       }
/* 171:143 */       return this;
/* 172:    */     }
/* 173:    */     
/* 174:    */     BuilderImpl blockTimestamp(int paramInt)
/* 175:    */     {
/* 176:147 */       this.blockTimestamp = paramInt;
/* 177:148 */       return this;
/* 178:    */     }
/* 179:    */     
/* 180:    */     BuilderImpl ecBlockHeight(int paramInt)
/* 181:    */     {
/* 182:152 */       this.ecBlockHeight = paramInt;
/* 183:153 */       return this;
/* 184:    */     }
/* 185:    */     
/* 186:    */     BuilderImpl ecBlockId(long paramLong)
/* 187:    */     {
/* 188:157 */       this.ecBlockId = paramLong;
/* 189:158 */       return this;
/* 190:    */     }
/* 191:    */   }
/* 192:    */   
/* 193:183 */   private volatile int height = Integer.MAX_VALUE;
/* 194:    */   private volatile long blockId;
/* 195:    */   private volatile Block block;
/* 196:    */   private volatile byte[] signature;
/* 197:187 */   private volatile int blockTimestamp = -1;
/* 198:    */   private volatile long id;
/* 199:    */   private volatile String stringId;
/* 200:    */   private volatile long senderId;
/* 201:    */   private volatile String fullHash;
/* 202:    */   private volatile DbKey dbKey;
/* 203:    */   
/* 204:    */   private TransactionImpl(BuilderImpl paramBuilderImpl)
/* 205:    */     throws NxtException.NotValidException
/* 206:    */   {
/* 207:196 */     this.timestamp = paramBuilderImpl.timestamp;
/* 208:197 */     this.deadline = paramBuilderImpl.deadline;
/* 209:198 */     this.senderPublicKey = paramBuilderImpl.senderPublicKey;
/* 210:199 */     this.recipientId = paramBuilderImpl.recipientId;
/* 211:200 */     this.amountNQT = paramBuilderImpl.amountNQT;
/* 212:201 */     this.referencedTransactionFullHash = paramBuilderImpl.referencedTransactionFullHash;
/* 213:202 */     this.signature = paramBuilderImpl.signature;
/* 214:203 */     this.type = paramBuilderImpl.type;
/* 215:204 */     this.version = paramBuilderImpl.version;
/* 216:205 */     this.blockId = paramBuilderImpl.blockId;
/* 217:206 */     this.height = paramBuilderImpl.height;
/* 218:207 */     this.id = paramBuilderImpl.id;
/* 219:208 */     this.senderId = paramBuilderImpl.senderId;
/* 220:209 */     this.blockTimestamp = paramBuilderImpl.blockTimestamp;
/* 221:210 */     this.fullHash = paramBuilderImpl.fullHash;
/* 222:211 */     this.ecBlockHeight = paramBuilderImpl.ecBlockHeight;
/* 223:212 */     this.ecBlockId = paramBuilderImpl.ecBlockId;
/* 224:    */     
/* 225:214 */     ArrayList localArrayList = new ArrayList();
/* 226:215 */     if ((this.attachment = paramBuilderImpl.attachment) != null) {
/* 227:216 */       localArrayList.add(this.attachment);
/* 228:    */     }
/* 229:218 */     if ((this.message = paramBuilderImpl.message) != null) {
/* 230:219 */       localArrayList.add(this.message);
/* 231:    */     }
/* 232:221 */     if ((this.encryptedMessage = paramBuilderImpl.encryptedMessage) != null) {
/* 233:222 */       localArrayList.add(this.encryptedMessage);
/* 234:    */     }
/* 235:224 */     if ((this.publicKeyAnnouncement = paramBuilderImpl.publicKeyAnnouncement) != null) {
/* 236:225 */       localArrayList.add(this.publicKeyAnnouncement);
/* 237:    */     }
/* 238:227 */     if ((this.encryptToSelfMessage = paramBuilderImpl.encryptToSelfMessage) != null) {
/* 239:228 */       localArrayList.add(this.encryptToSelfMessage);
/* 240:    */     }
/* 241:230 */     this.appendages = Collections.unmodifiableList(localArrayList);
/* 242:231 */     int i = 0;
/* 243:232 */     for (Appendix localAppendix : this.appendages) {
/* 244:233 */       i += localAppendix.getSize();
/* 245:    */     }
/* 246:235 */     this.appendagesSize = i;
/* 247:236 */     int j = this.height < Integer.MAX_VALUE ? this.height : Nxt.getBlockchain().getHeight();
/* 248:237 */     long l = this.type.minimumFeeNQT(j, i);
/* 249:238 */     if ((this.type == null) || (this.type.isSigned()))
/* 250:    */     {
/* 251:239 */       if ((paramBuilderImpl.feeNQT > 0L) && (paramBuilderImpl.feeNQT < l)) {
/* 252:240 */         throw new NxtException.NotValidException(String.format("Requested fee %d less than the minimum fee %d", new Object[] { Long.valueOf(paramBuilderImpl.feeNQT), Long.valueOf(l) }));
/* 253:    */       }
/* 254:243 */       if (paramBuilderImpl.feeNQT <= 0L) {
/* 255:244 */         this.feeNQT = l;
/* 256:    */       } else {
/* 257:246 */         this.feeNQT = paramBuilderImpl.feeNQT;
/* 258:    */       }
/* 259:    */     }
/* 260:    */     else
/* 261:    */     {
/* 262:250 */       this.feeNQT = paramBuilderImpl.feeNQT;
/* 263:    */     }
/* 264:253 */     if (((this.type == null) || (this.type.isSigned())) && (
/* 265:254 */       (this.deadline < 1) || (this.feeNQT > 215881280000000000L) || (this.amountNQT < 0L) || (this.amountNQT > 215881280000000000L) || (this.type == null))) {
/* 266:259 */       throw new NxtException.NotValidException("Invalid transaction parameters:\n type: " + this.type + ", timestamp: " + this.timestamp + ", deadline: " + this.deadline + ", fee: " + this.feeNQT + ", amount: " + this.amountNQT);
/* 267:    */     }
/* 268:264 */     if ((this.attachment == null) || (this.type != this.attachment.getTransactionType())) {
/* 269:265 */       throw new NxtException.NotValidException("Invalid attachment " + this.attachment + " for transaction of type " + this.type);
/* 270:    */     }
/* 271:268 */     if ((!this.type.hasRecipient()) && (
/* 272:269 */       (this.recipientId != 0L) || (getAmountNQT() != 0L))) {
/* 273:270 */       throw new NxtException.NotValidException("Transactions of this type must have recipient == Genesis, amount == 0");
/* 274:    */     }
/* 275:274 */     for (Appendix.AbstractAppendix localAbstractAppendix : this.appendages) {
/* 276:275 */       if (!localAbstractAppendix.verifyVersion(this.version)) {
/* 277:276 */         throw new NxtException.NotValidException("Invalid attachment version " + localAbstractAppendix.getVersion() + " for transaction version " + this.version);
/* 278:    */       }
/* 279:    */     }
/* 280:    */   }
/* 281:    */   
/* 282:    */   public short getDeadline()
/* 283:    */   {
/* 284:285 */     return this.deadline;
/* 285:    */   }
/* 286:    */   
/* 287:    */   public byte[] getSenderPublicKey()
/* 288:    */   {
/* 289:290 */     return this.senderPublicKey;
/* 290:    */   }
/* 291:    */   
/* 292:    */   public long getRecipientId()
/* 293:    */   {
/* 294:295 */     return this.recipientId;
/* 295:    */   }
/* 296:    */   
/* 297:    */   public long getAmountNQT()
/* 298:    */   {
/* 299:300 */     return this.amountNQT;
/* 300:    */   }
/* 301:    */   
/* 302:    */   public long getFeeNQT()
/* 303:    */   {
/* 304:305 */     return this.feeNQT;
/* 305:    */   }
/* 306:    */   
/* 307:    */   public String getReferencedTransactionFullHash()
/* 308:    */   {
/* 309:310 */     return this.referencedTransactionFullHash;
/* 310:    */   }
/* 311:    */   
/* 312:    */   public int getHeight()
/* 313:    */   {
/* 314:315 */     return this.height;
/* 315:    */   }
/* 316:    */   
/* 317:    */   void setHeight(int paramInt)
/* 318:    */   {
/* 319:319 */     this.height = paramInt;
/* 320:    */   }
/* 321:    */   
/* 322:    */   public byte[] getSignature()
/* 323:    */   {
/* 324:324 */     return this.signature;
/* 325:    */   }
/* 326:    */   
/* 327:    */   public TransactionType getType()
/* 328:    */   {
/* 329:329 */     return this.type;
/* 330:    */   }
/* 331:    */   
/* 332:    */   public byte getVersion()
/* 333:    */   {
/* 334:334 */     return this.version;
/* 335:    */   }
/* 336:    */   
/* 337:    */   public long getBlockId()
/* 338:    */   {
/* 339:339 */     return this.blockId;
/* 340:    */   }
/* 341:    */   
/* 342:    */   public Block getBlock()
/* 343:    */   {
/* 344:344 */     if ((this.block == null) && (this.blockId != 0L)) {
/* 345:345 */       this.block = Nxt.getBlockchain().getBlock(this.blockId);
/* 346:    */     }
/* 347:347 */     return this.block;
/* 348:    */   }
/* 349:    */   
/* 350:    */   void setBlock(Block paramBlock)
/* 351:    */   {
/* 352:351 */     this.block = paramBlock;
/* 353:352 */     this.blockId = paramBlock.getId();
/* 354:353 */     this.height = paramBlock.getHeight();
/* 355:354 */     this.blockTimestamp = paramBlock.getTimestamp();
/* 356:    */   }
/* 357:    */   
/* 358:    */   void unsetBlock()
/* 359:    */   {
/* 360:358 */     this.block = null;
/* 361:359 */     this.blockId = 0L;
/* 362:360 */     this.blockTimestamp = -1;
/* 363:    */   }
/* 364:    */   
/* 365:    */   public int getTimestamp()
/* 366:    */   {
/* 367:367 */     return this.timestamp;
/* 368:    */   }
/* 369:    */   
/* 370:    */   public int getBlockTimestamp()
/* 371:    */   {
/* 372:372 */     return this.blockTimestamp;
/* 373:    */   }
/* 374:    */   
/* 375:    */   public int getExpiration()
/* 376:    */   {
/* 377:377 */     return this.timestamp + this.deadline * 60;
/* 378:    */   }
/* 379:    */   
/* 380:    */   public Attachment getAttachment()
/* 381:    */   {
/* 382:382 */     return this.attachment;
/* 383:    */   }
/* 384:    */   
/* 385:    */   public List<? extends Appendix> getAppendages()
/* 386:    */   {
/* 387:387 */     return this.appendages;
/* 388:    */   }
/* 389:    */   
/* 390:    */   public long getId()
/* 391:    */   {
/* 392:392 */     if (this.id == 0L)
/* 393:    */     {
/* 394:393 */       if ((this.signature == null) && (this.type.isSigned())) {
/* 395:394 */         throw new IllegalStateException("Transaction is not signed yet");
/* 396:    */       }
/* 397:    */       byte[] arrayOfByte1;
/* 398:397 */       if (useNQT())
/* 399:    */       {
/* 400:398 */         localObject = zeroSignature(getBytes());
/* 401:399 */         byte[] arrayOfByte2 = Crypto.sha256().digest(this.signature != null ? this.signature : new byte[64]);
/* 402:400 */         MessageDigest localMessageDigest = Crypto.sha256();
/* 403:401 */         localMessageDigest.update((byte[])localObject);
/* 404:402 */         arrayOfByte1 = localMessageDigest.digest(arrayOfByte2);
/* 405:    */       }
/* 406:    */       else
/* 407:    */       {
/* 408:404 */         arrayOfByte1 = Crypto.sha256().digest(getBytes());
/* 409:    */       }
/* 410:406 */       Object localObject = new BigInteger(1, new byte[] { arrayOfByte1[7], arrayOfByte1[6], arrayOfByte1[5], arrayOfByte1[4], arrayOfByte1[3], arrayOfByte1[2], arrayOfByte1[1], arrayOfByte1[0] });
/* 411:407 */       this.id = ((BigInteger)localObject).longValue();
/* 412:408 */       this.stringId = ((BigInteger)localObject).toString();
/* 413:409 */       this.fullHash = Convert.toHexString(arrayOfByte1);
/* 414:    */     }
/* 415:411 */     return this.id;
/* 416:    */   }
/* 417:    */   
/* 418:    */   public String getStringId()
/* 419:    */   {
/* 420:416 */     if (this.stringId == null)
/* 421:    */     {
/* 422:417 */       getId();
/* 423:418 */       if (this.stringId == null) {
/* 424:419 */         this.stringId = Convert.toUnsignedLong(this.id);
/* 425:    */       }
/* 426:    */     }
/* 427:422 */     return this.stringId;
/* 428:    */   }
/* 429:    */   
/* 430:    */   public String getFullHash()
/* 431:    */   {
/* 432:427 */     if (this.fullHash == null) {
/* 433:428 */       getId();
/* 434:    */     }
/* 435:430 */     return this.fullHash;
/* 436:    */   }
/* 437:    */   
/* 438:    */   public long getSenderId()
/* 439:    */   {
/* 440:435 */     if ((this.senderId == 0L) && ((this.type == null) || (this.type.isSigned()))) {
/* 441:436 */       this.senderId = Account.getId(this.senderPublicKey);
/* 442:    */     }
/* 443:438 */     return this.senderId;
/* 444:    */   }
/* 445:    */   
/* 446:    */   DbKey getDbKey()
/* 447:    */   {
/* 448:442 */     if (this.dbKey == null) {
/* 449:443 */       this.dbKey = TransactionProcessorImpl.getInstance().unconfirmedTransactionDbKeyFactory.newKey(getId());
/* 450:    */     }
/* 451:445 */     return this.dbKey;
/* 452:    */   }
/* 453:    */   
/* 454:    */   public Appendix.Message getMessage()
/* 455:    */   {
/* 456:450 */     return this.message;
/* 457:    */   }
/* 458:    */   
/* 459:    */   public Appendix.EncryptedMessage getEncryptedMessage()
/* 460:    */   {
/* 461:455 */     return this.encryptedMessage;
/* 462:    */   }
/* 463:    */   
/* 464:    */   public Appendix.EncryptToSelfMessage getEncryptToSelfMessage()
/* 465:    */   {
/* 466:460 */     return this.encryptToSelfMessage;
/* 467:    */   }
/* 468:    */   
/* 469:    */   Appendix.PublicKeyAnnouncement getPublicKeyAnnouncement()
/* 470:    */   {
/* 471:464 */     return this.publicKeyAnnouncement;
/* 472:    */   }
/* 473:    */   
/* 474:    */   public byte[] getBytes()
/* 475:    */   {
/* 476:    */     try
/* 477:    */     {
/* 478:470 */       ByteBuffer localByteBuffer = ByteBuffer.allocate(getSize());
/* 479:471 */       localByteBuffer.order(ByteOrder.LITTLE_ENDIAN);
/* 480:472 */       localByteBuffer.put(this.type.getType());
/* 481:473 */       localByteBuffer.put((byte)(this.version << 4 | this.type.getSubtype()));
/* 482:474 */       localByteBuffer.putInt(this.timestamp);
/* 483:475 */       localByteBuffer.putShort(this.deadline);
/* 484:476 */       localByteBuffer.put(this.senderPublicKey);
/* 485:477 */       localByteBuffer.putLong(this.type.hasRecipient() ? this.recipientId : 0L);
/* 486:478 */       if (useNQT())
/* 487:    */       {
/* 488:479 */         localByteBuffer.putLong(this.amountNQT);
/* 489:480 */         localByteBuffer.putLong(this.feeNQT);
/* 490:481 */         if (this.referencedTransactionFullHash != null) {
/* 491:482 */           localByteBuffer.put(Convert.parseHexString(this.referencedTransactionFullHash));
/* 492:    */         } else {
/* 493:484 */           localByteBuffer.put(new byte[32]);
/* 494:    */         }
/* 495:    */       }
/* 496:    */       else
/* 497:    */       {
/* 498:487 */         localByteBuffer.putInt((int)(this.amountNQT / 100000000L));
/* 499:488 */         localByteBuffer.putInt((int)(this.feeNQT / 100000000L));
/* 500:489 */         if (this.referencedTransactionFullHash != null) {
/* 501:490 */           localByteBuffer.putLong(Convert.fullHashToId(Convert.parseHexString(this.referencedTransactionFullHash)));
/* 502:    */         } else {
/* 503:492 */           localByteBuffer.putLong(0L);
/* 504:    */         }
/* 505:    */       }
/* 506:495 */       localByteBuffer.put(this.signature != null ? this.signature : new byte[64]);
/* 507:496 */       if (this.version > 0)
/* 508:    */       {
/* 509:497 */         localByteBuffer.putInt(getFlags());
/* 510:498 */         localByteBuffer.putInt(this.ecBlockHeight);
/* 511:499 */         localByteBuffer.putLong(this.ecBlockId);
/* 512:    */       }
/* 513:501 */       for (Appendix localAppendix : this.appendages) {
/* 514:502 */         localAppendix.putBytes(localByteBuffer);
/* 515:    */       }
/* 516:504 */       return localByteBuffer.array();
/* 517:    */     }
/* 518:    */     catch (RuntimeException localRuntimeException)
/* 519:    */     {
/* 520:506 */       Logger.logDebugMessage("Failed to get transaction bytes for transaction: " + getJSONObject().toJSONString());
/* 521:507 */       throw localRuntimeException;
/* 522:    */     }
/* 523:    */   }
/* 524:    */   
/* 525:    */   /* Error */
/* 526:    */   static TransactionImpl parseTransaction(byte[] paramArrayOfByte)
/* 527:    */     throws NxtException.ValidationException
/* 528:    */   {
/* 529:    */     // Byte code:
/* 530:    */     //   0: aload_0
/* 531:    */     //   1: invokestatic 146	java/nio/ByteBuffer:wrap	([B)Ljava/nio/ByteBuffer;
/* 532:    */     //   4: astore_1
/* 533:    */     //   5: aload_1
/* 534:    */     //   6: getstatic 125	java/nio/ByteOrder:LITTLE_ENDIAN	Ljava/nio/ByteOrder;
/* 535:    */     //   9: invokevirtual 126	java/nio/ByteBuffer:order	(Ljava/nio/ByteOrder;)Ljava/nio/ByteBuffer;
/* 536:    */     //   12: pop
/* 537:    */     //   13: aload_1
/* 538:    */     //   14: invokevirtual 147	java/nio/ByteBuffer:get	()B
/* 539:    */     //   17: istore_2
/* 540:    */     //   18: aload_1
/* 541:    */     //   19: invokevirtual 147	java/nio/ByteBuffer:get	()B
/* 542:    */     //   22: istore_3
/* 543:    */     //   23: iload_3
/* 544:    */     //   24: sipush 240
/* 545:    */     //   27: iand
/* 546:    */     //   28: iconst_4
/* 547:    */     //   29: ishr
/* 548:    */     //   30: i2b
/* 549:    */     //   31: istore 4
/* 550:    */     //   33: iload_3
/* 551:    */     //   34: bipush 15
/* 552:    */     //   36: iand
/* 553:    */     //   37: i2b
/* 554:    */     //   38: istore_3
/* 555:    */     //   39: aload_1
/* 556:    */     //   40: invokevirtual 148	java/nio/ByteBuffer:getInt	()I
/* 557:    */     //   43: istore 5
/* 558:    */     //   45: aload_1
/* 559:    */     //   46: invokevirtual 149	java/nio/ByteBuffer:getShort	()S
/* 560:    */     //   49: istore 6
/* 561:    */     //   51: bipush 32
/* 562:    */     //   53: newarray 慢⁤污潬慣楴湯
/* 563:    */     //   56: iconst_4
/* 564:    */     //   57: aload_1
/* 565:    */     //   58: aload 7
/* 566:    */     //   60: invokevirtual 150	java/nio/ByteBuffer:get	([B)Ljava/nio/ByteBuffer;
/* 567:    */     //   63: pop
/* 568:    */     //   64: aload_1
/* 569:    */     //   65: invokevirtual 151	java/nio/ByteBuffer:getLong	()J
/* 570:    */     //   68: lstore 8
/* 571:    */     //   70: aload_1
/* 572:    */     //   71: invokevirtual 151	java/nio/ByteBuffer:getLong	()J
/* 573:    */     //   74: lstore 10
/* 574:    */     //   76: aload_1
/* 575:    */     //   77: invokevirtual 151	java/nio/ByteBuffer:getLong	()J
/* 576:    */     //   80: lstore 12
/* 577:    */     //   82: aconst_null
/* 578:    */     //   83: astore 14
/* 579:    */     //   85: bipush 32
/* 580:    */     //   87: newarray 慢⁤污潬慣楴湯
/* 581:    */     //   90: dconst_1
/* 582:    */     //   91: aload_1
/* 583:    */     //   92: aload 15
/* 584:    */     //   94: invokevirtual 150	java/nio/ByteBuffer:get	([B)Ljava/nio/ByteBuffer;
/* 585:    */     //   97: pop
/* 586:    */     //   98: aload 15
/* 587:    */     //   100: invokestatic 152	nxt/util/Convert:emptyToNull	([B)[B
/* 588:    */     //   103: ifnull +10 -> 113
/* 589:    */     //   106: aload 15
/* 590:    */     //   108: invokestatic 115	nxt/util/Convert:toHexString	([B)Ljava/lang/String;
/* 591:    */     //   111: astore 14
/* 592:    */     //   113: bipush 64
/* 593:    */     //   115: newarray 慢⁤污潬慣楴湯
/* 594:    */     //   118: bipush 43
/* 595:    */     //   120: aload 16
/* 596:    */     //   122: invokevirtual 150	java/nio/ByteBuffer:get	([B)Ljava/nio/ByteBuffer;
/* 597:    */     //   125: pop
/* 598:    */     //   126: aload 16
/* 599:    */     //   128: invokestatic 152	nxt/util/Convert:emptyToNull	([B)[B
/* 600:    */     //   131: astore 16
/* 601:    */     //   133: iconst_0
/* 602:    */     //   134: istore 17
/* 603:    */     //   136: iconst_0
/* 604:    */     //   137: istore 18
/* 605:    */     //   139: lconst_0
/* 606:    */     //   140: lstore 19
/* 607:    */     //   142: iload 4
/* 608:    */     //   144: ifle +21 -> 165
/* 609:    */     //   147: aload_1
/* 610:    */     //   148: invokevirtual 148	java/nio/ByteBuffer:getInt	()I
/* 611:    */     //   151: istore 17
/* 612:    */     //   153: aload_1
/* 613:    */     //   154: invokevirtual 148	java/nio/ByteBuffer:getInt	()I
/* 614:    */     //   157: istore 18
/* 615:    */     //   159: aload_1
/* 616:    */     //   160: invokevirtual 151	java/nio/ByteBuffer:getLong	()J
/* 617:    */     //   163: lstore 19
/* 618:    */     //   165: iload_2
/* 619:    */     //   166: iload_3
/* 620:    */     //   167: invokestatic 153	nxt/TransactionType:findTransactionType	(BB)Lnxt/TransactionType;
/* 621:    */     //   170: astore 21
/* 622:    */     //   172: new 154	nxt/TransactionImpl$BuilderImpl
/* 623:    */     //   175: dup
/* 624:    */     //   176: iload 4
/* 625:    */     //   178: aload 7
/* 626:    */     //   180: lload 10
/* 627:    */     //   182: lload 12
/* 628:    */     //   184: iload 5
/* 629:    */     //   186: iload 6
/* 630:    */     //   188: aload 21
/* 631:    */     //   190: aload_1
/* 632:    */     //   191: iload 4
/* 633:    */     //   193: invokevirtual 155	nxt/TransactionType:parseAttachment	(Ljava/nio/ByteBuffer;B)Lnxt/Attachment$AbstractAttachment;
/* 634:    */     //   196: invokespecial 156	nxt/TransactionImpl$BuilderImpl:<init>	(B[BJJISLnxt/Attachment$AbstractAttachment;)V
/* 635:    */     //   199: aload 14
/* 636:    */     //   201: invokevirtual 157	nxt/TransactionImpl$BuilderImpl:referencedTransactionFullHash	(Ljava/lang/String;)Lnxt/TransactionImpl$BuilderImpl;
/* 637:    */     //   204: aload 16
/* 638:    */     //   206: invokevirtual 158	nxt/TransactionImpl$BuilderImpl:signature	([B)Lnxt/TransactionImpl$BuilderImpl;
/* 639:    */     //   209: iload 18
/* 640:    */     //   211: invokevirtual 159	nxt/TransactionImpl$BuilderImpl:ecBlockHeight	(I)Lnxt/TransactionImpl$BuilderImpl;
/* 641:    */     //   214: lload 19
/* 642:    */     //   216: invokevirtual 160	nxt/TransactionImpl$BuilderImpl:ecBlockId	(J)Lnxt/TransactionImpl$BuilderImpl;
/* 643:    */     //   219: astore 22
/* 644:    */     //   221: aload 21
/* 645:    */     //   223: invokevirtual 88	nxt/TransactionType:hasRecipient	()Z
/* 646:    */     //   226: ifeq +11 -> 237
/* 647:    */     //   229: aload 22
/* 648:    */     //   231: lload 8
/* 649:    */     //   233: invokevirtual 161	nxt/TransactionImpl$BuilderImpl:recipientId	(J)Lnxt/TransactionImpl$BuilderImpl;
/* 650:    */     //   236: pop
/* 651:    */     //   237: iconst_1
/* 652:    */     //   238: istore 23
/* 653:    */     //   240: iload 17
/* 654:    */     //   242: iload 23
/* 655:    */     //   244: iand
/* 656:    */     //   245: ifne +16 -> 261
/* 657:    */     //   248: iload 4
/* 658:    */     //   250: ifne +27 -> 277
/* 659:    */     //   253: aload 21
/* 660:    */     //   255: getstatic 162	nxt/TransactionType$Messaging:ARBITRARY_MESSAGE	Lnxt/TransactionType;
/* 661:    */     //   258: if_acmpne +19 -> 277
/* 662:    */     //   261: aload 22
/* 663:    */     //   263: new 163	nxt/Appendix$Message
/* 664:    */     //   266: dup
/* 665:    */     //   267: aload_1
/* 666:    */     //   268: iload 4
/* 667:    */     //   270: invokespecial 164	nxt/Appendix$Message:<init>	(Ljava/nio/ByteBuffer;B)V
/* 668:    */     //   273: invokevirtual 165	nxt/TransactionImpl$BuilderImpl:message	(Lnxt/Appendix$Message;)Lnxt/TransactionImpl$BuilderImpl;
/* 669:    */     //   276: pop
/* 670:    */     //   277: iload 23
/* 671:    */     //   279: iconst_1
/* 672:    */     //   280: ishl
/* 673:    */     //   281: istore 23
/* 674:    */     //   283: iload 17
/* 675:    */     //   285: iload 23
/* 676:    */     //   287: iand
/* 677:    */     //   288: ifeq +19 -> 307
/* 678:    */     //   291: aload 22
/* 679:    */     //   293: new 166	nxt/Appendix$EncryptedMessage
/* 680:    */     //   296: dup
/* 681:    */     //   297: aload_1
/* 682:    */     //   298: iload 4
/* 683:    */     //   300: invokespecial 167	nxt/Appendix$EncryptedMessage:<init>	(Ljava/nio/ByteBuffer;B)V
/* 684:    */     //   303: invokevirtual 168	nxt/TransactionImpl$BuilderImpl:encryptedMessage	(Lnxt/Appendix$EncryptedMessage;)Lnxt/TransactionImpl$BuilderImpl;
/* 685:    */     //   306: pop
/* 686:    */     //   307: iload 23
/* 687:    */     //   309: iconst_1
/* 688:    */     //   310: ishl
/* 689:    */     //   311: istore 23
/* 690:    */     //   313: iload 17
/* 691:    */     //   315: iload 23
/* 692:    */     //   317: iand
/* 693:    */     //   318: ifeq +19 -> 337
/* 694:    */     //   321: aload 22
/* 695:    */     //   323: new 169	nxt/Appendix$PublicKeyAnnouncement
/* 696:    */     //   326: dup
/* 697:    */     //   327: aload_1
/* 698:    */     //   328: iload 4
/* 699:    */     //   330: invokespecial 170	nxt/Appendix$PublicKeyAnnouncement:<init>	(Ljava/nio/ByteBuffer;B)V
/* 700:    */     //   333: invokevirtual 171	nxt/TransactionImpl$BuilderImpl:publicKeyAnnouncement	(Lnxt/Appendix$PublicKeyAnnouncement;)Lnxt/TransactionImpl$BuilderImpl;
/* 701:    */     //   336: pop
/* 702:    */     //   337: iload 23
/* 703:    */     //   339: iconst_1
/* 704:    */     //   340: ishl
/* 705:    */     //   341: istore 23
/* 706:    */     //   343: iload 17
/* 707:    */     //   345: iload 23
/* 708:    */     //   347: iand
/* 709:    */     //   348: ifeq +19 -> 367
/* 710:    */     //   351: aload 22
/* 711:    */     //   353: new 172	nxt/Appendix$EncryptToSelfMessage
/* 712:    */     //   356: dup
/* 713:    */     //   357: aload_1
/* 714:    */     //   358: iload 4
/* 715:    */     //   360: invokespecial 173	nxt/Appendix$EncryptToSelfMessage:<init>	(Ljava/nio/ByteBuffer;B)V
/* 716:    */     //   363: invokevirtual 174	nxt/TransactionImpl$BuilderImpl:encryptToSelfMessage	(Lnxt/Appendix$EncryptToSelfMessage;)Lnxt/TransactionImpl$BuilderImpl;
/* 717:    */     //   366: pop
/* 718:    */     //   367: aload 22
/* 719:    */     //   369: invokevirtual 175	nxt/TransactionImpl$BuilderImpl:build	()Lnxt/TransactionImpl;
/* 720:    */     //   372: areturn
/* 721:    */     //   373: astore_1
/* 722:    */     //   374: new 73	java/lang/StringBuilder
/* 723:    */     //   377: dup
/* 724:    */     //   378: invokespecial 74	java/lang/StringBuilder:<init>	()V
/* 725:    */     //   381: ldc 176
/* 726:    */     //   383: invokevirtual 76	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 727:    */     //   386: aload_0
/* 728:    */     //   387: invokestatic 115	nxt/util/Convert:toHexString	([B)Ljava/lang/String;
/* 729:    */     //   390: invokevirtual 76	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 730:    */     //   393: invokevirtual 84	java/lang/StringBuilder:toString	()Ljava/lang/String;
/* 731:    */     //   396: invokestatic 145	nxt/util/Logger:logDebugMessage	(Ljava/lang/String;)V
/* 732:    */     //   399: aload_1
/* 733:    */     //   400: athrow
/* 734:    */     // Line number table:
/* 735:    */     //   Java source line #513	-> byte code offset #0
/* 736:    */     //   Java source line #514	-> byte code offset #5
/* 737:    */     //   Java source line #515	-> byte code offset #13
/* 738:    */     //   Java source line #516	-> byte code offset #18
/* 739:    */     //   Java source line #517	-> byte code offset #23
/* 740:    */     //   Java source line #518	-> byte code offset #33
/* 741:    */     //   Java source line #519	-> byte code offset #39
/* 742:    */     //   Java source line #520	-> byte code offset #45
/* 743:    */     //   Java source line #521	-> byte code offset #51
/* 744:    */     //   Java source line #522	-> byte code offset #57
/* 745:    */     //   Java source line #523	-> byte code offset #64
/* 746:    */     //   Java source line #524	-> byte code offset #70
/* 747:    */     //   Java source line #525	-> byte code offset #76
/* 748:    */     //   Java source line #526	-> byte code offset #82
/* 749:    */     //   Java source line #527	-> byte code offset #85
/* 750:    */     //   Java source line #528	-> byte code offset #91
/* 751:    */     //   Java source line #529	-> byte code offset #98
/* 752:    */     //   Java source line #530	-> byte code offset #106
/* 753:    */     //   Java source line #532	-> byte code offset #113
/* 754:    */     //   Java source line #533	-> byte code offset #119
/* 755:    */     //   Java source line #534	-> byte code offset #126
/* 756:    */     //   Java source line #535	-> byte code offset #133
/* 757:    */     //   Java source line #536	-> byte code offset #136
/* 758:    */     //   Java source line #537	-> byte code offset #139
/* 759:    */     //   Java source line #538	-> byte code offset #142
/* 760:    */     //   Java source line #539	-> byte code offset #147
/* 761:    */     //   Java source line #540	-> byte code offset #153
/* 762:    */     //   Java source line #541	-> byte code offset #159
/* 763:    */     //   Java source line #543	-> byte code offset #165
/* 764:    */     //   Java source line #544	-> byte code offset #172
/* 765:    */     //   Java source line #550	-> byte code offset #221
/* 766:    */     //   Java source line #551	-> byte code offset #229
/* 767:    */     //   Java source line #553	-> byte code offset #237
/* 768:    */     //   Java source line #554	-> byte code offset #240
/* 769:    */     //   Java source line #555	-> byte code offset #261
/* 770:    */     //   Java source line #557	-> byte code offset #277
/* 771:    */     //   Java source line #558	-> byte code offset #283
/* 772:    */     //   Java source line #559	-> byte code offset #291
/* 773:    */     //   Java source line #561	-> byte code offset #307
/* 774:    */     //   Java source line #562	-> byte code offset #313
/* 775:    */     //   Java source line #563	-> byte code offset #321
/* 776:    */     //   Java source line #565	-> byte code offset #337
/* 777:    */     //   Java source line #566	-> byte code offset #343
/* 778:    */     //   Java source line #567	-> byte code offset #351
/* 779:    */     //   Java source line #569	-> byte code offset #367
/* 780:    */     //   Java source line #570	-> byte code offset #373
/* 781:    */     //   Java source line #571	-> byte code offset #374
/* 782:    */     //   Java source line #572	-> byte code offset #399
/* 783:    */     // Local variable table:
/* 784:    */     //   start	length	slot	name	signature
/* 785:    */     //   0	401	0	paramArrayOfByte	byte[]
/* 786:    */     //   4	354	1	localByteBuffer	ByteBuffer
/* 787:    */     //   373	27	1	localNotValidException	NxtException.NotValidException
/* 788:    */     //   17	149	2	b1	byte
/* 789:    */     //   22	145	3	i	int
/* 790:    */     //   31	328	4	b2	byte
/* 791:    */     //   43	142	5	j	int
/* 792:    */     //   49	138	6	s	short
/* 793:    */     //   55	124	7	arrayOfByte1	byte[]
/* 794:    */     //   68	164	8	l1	long
/* 795:    */     //   74	107	10	l2	long
/* 796:    */     //   80	103	12	l3	long
/* 797:    */     //   83	117	14	str	String
/* 798:    */     //   89	18	15	arrayOfByte2	byte[]
/* 799:    */     //   117	88	16	arrayOfByte3	byte[]
/* 800:    */     //   134	214	17	k	int
/* 801:    */     //   137	73	18	m	int
/* 802:    */     //   140	75	19	l4	long
/* 803:    */     //   170	84	21	localTransactionType	TransactionType
/* 804:    */     //   219	149	22	localBuilderImpl	BuilderImpl
/* 805:    */     //   238	110	23	n	int
/* 806:    */     // Exception table:
/* 807:    */     //   from	to	target	type
/* 808:    */     //   0	372	373	nxt/NxtException$NotValidException
/* 809:    */     //   0	372	373	java/lang/RuntimeException
/* 810:    */   }
/* 811:    */   
/* 812:    */   public byte[] getUnsignedBytes()
/* 813:    */   {
/* 814:578 */     return zeroSignature(getBytes());
/* 815:    */   }
/* 816:    */   
/* 817:    */   public JSONObject getJSONObject()
/* 818:    */   {
/* 819:595 */     JSONObject localJSONObject1 = new JSONObject();
/* 820:596 */     localJSONObject1.put("type", Byte.valueOf(this.type.getType()));
/* 821:597 */     localJSONObject1.put("subtype", Byte.valueOf(this.type.getSubtype()));
/* 822:598 */     localJSONObject1.put("timestamp", Integer.valueOf(this.timestamp));
/* 823:599 */     localJSONObject1.put("deadline", Short.valueOf(this.deadline));
/* 824:600 */     localJSONObject1.put("senderPublicKey", Convert.toHexString(this.senderPublicKey));
/* 825:601 */     if (this.type.hasRecipient()) {
/* 826:602 */       localJSONObject1.put("recipient", Convert.toUnsignedLong(this.recipientId));
/* 827:    */     }
/* 828:604 */     localJSONObject1.put("amountNQT", Long.valueOf(this.amountNQT));
/* 829:605 */     localJSONObject1.put("feeNQT", Long.valueOf(this.feeNQT));
/* 830:606 */     if (this.referencedTransactionFullHash != null) {
/* 831:607 */       localJSONObject1.put("referencedTransactionFullHash", this.referencedTransactionFullHash);
/* 832:    */     }
/* 833:609 */     localJSONObject1.put("ecBlockHeight", Integer.valueOf(this.ecBlockHeight));
/* 834:610 */     localJSONObject1.put("ecBlockId", Convert.toUnsignedLong(this.ecBlockId));
/* 835:611 */     localJSONObject1.put("signature", Convert.toHexString(this.signature));
/* 836:612 */     JSONObject localJSONObject2 = new JSONObject();
/* 837:613 */     for (Appendix localAppendix : this.appendages) {
/* 838:614 */       localJSONObject2.putAll(localAppendix.getJSONObject());
/* 839:    */     }
/* 840:617 */     localJSONObject1.put("attachment", localJSONObject2);
/* 841:    */     
/* 842:619 */     localJSONObject1.put("version", Byte.valueOf(this.version));
/* 843:620 */     return localJSONObject1;
/* 844:    */   }
/* 845:    */   
/* 846:    */   /* Error */
/* 847:    */   static TransactionImpl parseTransaction(JSONObject paramJSONObject)
/* 848:    */     throws NxtException.NotValidException
/* 849:    */   {
/* 850:    */     // Byte code:
/* 851:    */     //   0: aload_0
/* 852:    */     //   1: ldc 179
/* 853:    */     //   3: invokevirtual 199	org/json/simple/JSONObject:get	(Ljava/lang/Object;)Ljava/lang/Object;
/* 854:    */     //   6: checkcast 200	java/lang/Long
/* 855:    */     //   9: invokevirtual 201	java/lang/Long:byteValue	()B
/* 856:    */     //   12: istore_1
/* 857:    */     //   13: aload_0
/* 858:    */     //   14: ldc 182
/* 859:    */     //   16: invokevirtual 199	org/json/simple/JSONObject:get	(Ljava/lang/Object;)Ljava/lang/Object;
/* 860:    */     //   19: checkcast 200	java/lang/Long
/* 861:    */     //   22: invokevirtual 201	java/lang/Long:byteValue	()B
/* 862:    */     //   25: istore_2
/* 863:    */     //   26: aload_0
/* 864:    */     //   27: ldc 183
/* 865:    */     //   29: invokevirtual 199	org/json/simple/JSONObject:get	(Ljava/lang/Object;)Ljava/lang/Object;
/* 866:    */     //   32: checkcast 200	java/lang/Long
/* 867:    */     //   35: invokevirtual 202	java/lang/Long:intValue	()I
/* 868:    */     //   38: istore_3
/* 869:    */     //   39: aload_0
/* 870:    */     //   40: ldc 185
/* 871:    */     //   42: invokevirtual 199	org/json/simple/JSONObject:get	(Ljava/lang/Object;)Ljava/lang/Object;
/* 872:    */     //   45: checkcast 200	java/lang/Long
/* 873:    */     //   48: invokevirtual 203	java/lang/Long:shortValue	()S
/* 874:    */     //   51: istore 4
/* 875:    */     //   53: aload_0
/* 876:    */     //   54: ldc 187
/* 877:    */     //   56: invokevirtual 199	org/json/simple/JSONObject:get	(Ljava/lang/Object;)Ljava/lang/Object;
/* 878:    */     //   59: checkcast 204	java/lang/String
/* 879:    */     //   62: invokestatic 134	nxt/util/Convert:parseHexString	(Ljava/lang/String;)[B
/* 880:    */     //   65: astore 5
/* 881:    */     //   67: aload_0
/* 882:    */     //   68: ldc 189
/* 883:    */     //   70: invokevirtual 199	org/json/simple/JSONObject:get	(Ljava/lang/Object;)Ljava/lang/Object;
/* 884:    */     //   73: invokestatic 205	nxt/util/Convert:parseLong	(Ljava/lang/Object;)J
/* 885:    */     //   76: lstore 6
/* 886:    */     //   78: aload_0
/* 887:    */     //   79: ldc 190
/* 888:    */     //   81: invokevirtual 199	org/json/simple/JSONObject:get	(Ljava/lang/Object;)Ljava/lang/Object;
/* 889:    */     //   84: invokestatic 205	nxt/util/Convert:parseLong	(Ljava/lang/Object;)J
/* 890:    */     //   87: lstore 8
/* 891:    */     //   89: aload_0
/* 892:    */     //   90: ldc 191
/* 893:    */     //   92: invokevirtual 199	org/json/simple/JSONObject:get	(Ljava/lang/Object;)Ljava/lang/Object;
/* 894:    */     //   95: checkcast 204	java/lang/String
/* 895:    */     //   98: astore 10
/* 896:    */     //   100: aload_0
/* 897:    */     //   101: ldc 194
/* 898:    */     //   103: invokevirtual 199	org/json/simple/JSONObject:get	(Ljava/lang/Object;)Ljava/lang/Object;
/* 899:    */     //   106: checkcast 204	java/lang/String
/* 900:    */     //   109: invokestatic 134	nxt/util/Convert:parseHexString	(Ljava/lang/String;)[B
/* 901:    */     //   112: astore 11
/* 902:    */     //   114: aload_0
/* 903:    */     //   115: ldc 198
/* 904:    */     //   117: invokevirtual 199	org/json/simple/JSONObject:get	(Ljava/lang/Object;)Ljava/lang/Object;
/* 905:    */     //   120: checkcast 200	java/lang/Long
/* 906:    */     //   123: astore 12
/* 907:    */     //   125: aload 12
/* 908:    */     //   127: ifnonnull +7 -> 134
/* 909:    */     //   130: iconst_0
/* 910:    */     //   131: goto +8 -> 139
/* 911:    */     //   134: aload 12
/* 912:    */     //   136: invokevirtual 201	java/lang/Long:byteValue	()B
/* 913:    */     //   139: istore 13
/* 914:    */     //   141: aload_0
/* 915:    */     //   142: ldc 197
/* 916:    */     //   144: invokevirtual 199	org/json/simple/JSONObject:get	(Ljava/lang/Object;)Ljava/lang/Object;
/* 917:    */     //   147: checkcast 177	org/json/simple/JSONObject
/* 918:    */     //   150: astore 14
/* 919:    */     //   152: aload 14
/* 920:    */     //   154: ifnonnull +12 -> 166
/* 921:    */     //   157: new 177	org/json/simple/JSONObject
/* 922:    */     //   160: dup
/* 923:    */     //   161: invokespecial 178	org/json/simple/JSONObject:<init>	()V
/* 924:    */     //   164: astore 14
/* 925:    */     //   166: iload_1
/* 926:    */     //   167: iload_2
/* 927:    */     //   168: invokestatic 153	nxt/TransactionType:findTransactionType	(BB)Lnxt/TransactionType;
/* 928:    */     //   171: astore 15
/* 929:    */     //   173: aload 15
/* 930:    */     //   175: ifnonnull +39 -> 214
/* 931:    */     //   178: new 64	nxt/NxtException$NotValidException
/* 932:    */     //   181: dup
/* 933:    */     //   182: new 73	java/lang/StringBuilder
/* 934:    */     //   185: dup
/* 935:    */     //   186: invokespecial 74	java/lang/StringBuilder:<init>	()V
/* 936:    */     //   189: ldc 206
/* 937:    */     //   191: invokevirtual 76	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 938:    */     //   194: iload_1
/* 939:    */     //   195: invokevirtual 79	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
/* 940:    */     //   198: ldc 207
/* 941:    */     //   200: invokevirtual 76	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 942:    */     //   203: iload_2
/* 943:    */     //   204: invokevirtual 79	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
/* 944:    */     //   207: invokevirtual 84	java/lang/StringBuilder:toString	()Ljava/lang/String;
/* 945:    */     //   210: invokespecial 69	nxt/NxtException$NotValidException:<init>	(Ljava/lang/String;)V
/* 946:    */     //   213: athrow
/* 947:    */     //   214: new 154	nxt/TransactionImpl$BuilderImpl
/* 948:    */     //   217: dup
/* 949:    */     //   218: iload 13
/* 950:    */     //   220: aload 5
/* 951:    */     //   222: lload 6
/* 952:    */     //   224: lload 8
/* 953:    */     //   226: iload_3
/* 954:    */     //   227: iload 4
/* 955:    */     //   229: aload 15
/* 956:    */     //   231: aload 14
/* 957:    */     //   233: invokevirtual 208	nxt/TransactionType:parseAttachment	(Lorg/json/simple/JSONObject;)Lnxt/Attachment$AbstractAttachment;
/* 958:    */     //   236: invokespecial 156	nxt/TransactionImpl$BuilderImpl:<init>	(B[BJJISLnxt/Attachment$AbstractAttachment;)V
/* 959:    */     //   239: aload 10
/* 960:    */     //   241: invokevirtual 157	nxt/TransactionImpl$BuilderImpl:referencedTransactionFullHash	(Ljava/lang/String;)Lnxt/TransactionImpl$BuilderImpl;
/* 961:    */     //   244: aload 11
/* 962:    */     //   246: invokevirtual 158	nxt/TransactionImpl$BuilderImpl:signature	([B)Lnxt/TransactionImpl$BuilderImpl;
/* 963:    */     //   249: astore 16
/* 964:    */     //   251: aload 15
/* 965:    */     //   253: invokevirtual 88	nxt/TransactionType:hasRecipient	()Z
/* 966:    */     //   256: ifeq +25 -> 281
/* 967:    */     //   259: aload_0
/* 968:    */     //   260: ldc 188
/* 969:    */     //   262: invokevirtual 199	org/json/simple/JSONObject:get	(Ljava/lang/Object;)Ljava/lang/Object;
/* 970:    */     //   265: checkcast 204	java/lang/String
/* 971:    */     //   268: invokestatic 209	nxt/util/Convert:parseUnsignedLong	(Ljava/lang/String;)J
/* 972:    */     //   271: lstore 17
/* 973:    */     //   273: aload 16
/* 974:    */     //   275: lload 17
/* 975:    */     //   277: invokevirtual 161	nxt/TransactionImpl$BuilderImpl:recipientId	(J)Lnxt/TransactionImpl$BuilderImpl;
/* 976:    */     //   280: pop
/* 977:    */     //   281: aload 14
/* 978:    */     //   283: ifnull +47 -> 330
/* 979:    */     //   286: aload 16
/* 980:    */     //   288: aload 14
/* 981:    */     //   290: invokestatic 210	nxt/Appendix$Message:parse	(Lorg/json/simple/JSONObject;)Lnxt/Appendix$Message;
/* 982:    */     //   293: invokevirtual 165	nxt/TransactionImpl$BuilderImpl:message	(Lnxt/Appendix$Message;)Lnxt/TransactionImpl$BuilderImpl;
/* 983:    */     //   296: pop
/* 984:    */     //   297: aload 16
/* 985:    */     //   299: aload 14
/* 986:    */     //   301: invokestatic 211	nxt/Appendix$EncryptedMessage:parse	(Lorg/json/simple/JSONObject;)Lnxt/Appendix$EncryptedMessage;
/* 987:    */     //   304: invokevirtual 168	nxt/TransactionImpl$BuilderImpl:encryptedMessage	(Lnxt/Appendix$EncryptedMessage;)Lnxt/TransactionImpl$BuilderImpl;
/* 988:    */     //   307: pop
/* 989:    */     //   308: aload 16
/* 990:    */     //   310: aload 14
/* 991:    */     //   312: invokestatic 212	nxt/Appendix$PublicKeyAnnouncement:parse	(Lorg/json/simple/JSONObject;)Lnxt/Appendix$PublicKeyAnnouncement;
/* 992:    */     //   315: invokevirtual 171	nxt/TransactionImpl$BuilderImpl:publicKeyAnnouncement	(Lnxt/Appendix$PublicKeyAnnouncement;)Lnxt/TransactionImpl$BuilderImpl;
/* 993:    */     //   318: pop
/* 994:    */     //   319: aload 16
/* 995:    */     //   321: aload 14
/* 996:    */     //   323: invokestatic 213	nxt/Appendix$EncryptToSelfMessage:parse	(Lorg/json/simple/JSONObject;)Lnxt/Appendix$EncryptToSelfMessage;
/* 997:    */     //   326: invokevirtual 174	nxt/TransactionImpl$BuilderImpl:encryptToSelfMessage	(Lnxt/Appendix$EncryptToSelfMessage;)Lnxt/TransactionImpl$BuilderImpl;
/* 998:    */     //   329: pop
/* 999:    */     //   330: iload 13
/* :00:    */     //   332: ifle +39 -> 371
/* :01:    */     //   335: aload 16
/* :02:    */     //   337: aload_0
/* :03:    */     //   338: ldc 192
/* :04:    */     //   340: invokevirtual 199	org/json/simple/JSONObject:get	(Ljava/lang/Object;)Ljava/lang/Object;
/* :05:    */     //   343: checkcast 200	java/lang/Long
/* :06:    */     //   346: invokevirtual 202	java/lang/Long:intValue	()I
/* :07:    */     //   349: invokevirtual 159	nxt/TransactionImpl$BuilderImpl:ecBlockHeight	(I)Lnxt/TransactionImpl$BuilderImpl;
/* :08:    */     //   352: pop
/* :09:    */     //   353: aload 16
/* :10:    */     //   355: aload_0
/* :11:    */     //   356: ldc 193
/* :12:    */     //   358: invokevirtual 199	org/json/simple/JSONObject:get	(Ljava/lang/Object;)Ljava/lang/Object;
/* :13:    */     //   361: checkcast 204	java/lang/String
/* :14:    */     //   364: invokestatic 209	nxt/util/Convert:parseUnsignedLong	(Ljava/lang/String;)J
/* :15:    */     //   367: invokevirtual 160	nxt/TransactionImpl$BuilderImpl:ecBlockId	(J)Lnxt/TransactionImpl$BuilderImpl;
/* :16:    */     //   370: pop
/* :17:    */     //   371: aload 16
/* :18:    */     //   373: invokevirtual 175	nxt/TransactionImpl$BuilderImpl:build	()Lnxt/TransactionImpl;
/* :19:    */     //   376: areturn
/* :20:    */     //   377: astore_1
/* :21:    */     //   378: new 73	java/lang/StringBuilder
/* :22:    */     //   381: dup
/* :23:    */     //   382: invokespecial 74	java/lang/StringBuilder:<init>	()V
/* :24:    */     //   385: ldc 214
/* :25:    */     //   387: invokevirtual 76	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* :26:    */     //   390: aload_0
/* :27:    */     //   391: invokevirtual 144	org/json/simple/JSONObject:toJSONString	()Ljava/lang/String;
/* :28:    */     //   394: invokevirtual 76	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* :29:    */     //   397: invokevirtual 84	java/lang/StringBuilder:toString	()Ljava/lang/String;
/* :30:    */     //   400: invokestatic 145	nxt/util/Logger:logDebugMessage	(Ljava/lang/String;)V
/* :31:    */     //   403: aload_1
/* :32:    */     //   404: athrow
/* :33:    */     // Line number table:
/* :34:    */     //   Java source line #625	-> byte code offset #0
/* :35:    */     //   Java source line #626	-> byte code offset #13
/* :36:    */     //   Java source line #627	-> byte code offset #26
/* :37:    */     //   Java source line #628	-> byte code offset #39
/* :38:    */     //   Java source line #629	-> byte code offset #53
/* :39:    */     //   Java source line #630	-> byte code offset #67
/* :40:    */     //   Java source line #631	-> byte code offset #78
/* :41:    */     //   Java source line #632	-> byte code offset #89
/* :42:    */     //   Java source line #633	-> byte code offset #100
/* :43:    */     //   Java source line #634	-> byte code offset #114
/* :44:    */     //   Java source line #635	-> byte code offset #125
/* :45:    */     //   Java source line #636	-> byte code offset #141
/* :46:    */     //   Java source line #637	-> byte code offset #152
/* :47:    */     //   Java source line #638	-> byte code offset #157
/* :48:    */     //   Java source line #641	-> byte code offset #166
/* :49:    */     //   Java source line #642	-> byte code offset #173
/* :50:    */     //   Java source line #643	-> byte code offset #178
/* :51:    */     //   Java source line #645	-> byte code offset #214
/* :52:    */     //   Java source line #650	-> byte code offset #251
/* :53:    */     //   Java source line #651	-> byte code offset #259
/* :54:    */     //   Java source line #652	-> byte code offset #273
/* :55:    */     //   Java source line #654	-> byte code offset #281
/* :56:    */     //   Java source line #655	-> byte code offset #286
/* :57:    */     //   Java source line #656	-> byte code offset #297
/* :58:    */     //   Java source line #657	-> byte code offset #308
/* :59:    */     //   Java source line #658	-> byte code offset #319
/* :60:    */     //   Java source line #660	-> byte code offset #330
/* :61:    */     //   Java source line #661	-> byte code offset #335
/* :62:    */     //   Java source line #662	-> byte code offset #353
/* :63:    */     //   Java source line #664	-> byte code offset #371
/* :64:    */     //   Java source line #665	-> byte code offset #377
/* :65:    */     //   Java source line #666	-> byte code offset #378
/* :66:    */     //   Java source line #667	-> byte code offset #403
/* :67:    */     // Local variable table:
/* :68:    */     //   start	length	slot	name	signature
/* :69:    */     //   0	405	0	paramJSONObject	JSONObject
/* :70:    */     //   12	183	1	b1	byte
/* :71:    */     //   377	27	1	localNotValidException	NxtException.NotValidException
/* :72:    */     //   25	179	2	b2	byte
/* :73:    */     //   38	189	3	i	int
/* :74:    */     //   51	177	4	s	short
/* :75:    */     //   65	156	5	arrayOfByte1	byte[]
/* :76:    */     //   76	147	6	l1	long
/* :77:    */     //   87	138	8	l2	long
/* :78:    */     //   98	142	10	str	String
/* :79:    */     //   112	133	11	arrayOfByte2	byte[]
/* :80:    */     //   123	12	12	localLong	Long
/* :81:    */     //   139	192	13	b3	byte
/* :82:    */     //   150	172	14	localJSONObject	JSONObject
/* :83:    */     //   171	81	15	localTransactionType	TransactionType
/* :84:    */     //   249	123	16	localBuilderImpl	BuilderImpl
/* :85:    */     //   271	5	17	l3	long
/* :86:    */     // Exception table:
/* :87:    */     //   from	to	target	type
/* :88:    */     //   0	376	377	nxt/NxtException$NotValidException
/* :89:    */     //   0	376	377	java/lang/RuntimeException
/* :90:    */   }
/* :91:    */   
/* :92:    */   public int getECBlockHeight()
/* :93:    */   {
/* :94:674 */     return this.ecBlockHeight;
/* :95:    */   }
/* :96:    */   
/* :97:    */   public long getECBlockId()
/* :98:    */   {
/* :99:679 */     return this.ecBlockId;
/* ;00:    */   }
/* ;01:    */   
/* ;02:    */   public void sign(String paramString)
/* ;03:    */   {
/* ;04:684 */     if (this.signature != null) {
/* ;05:685 */       throw new IllegalStateException("Transaction already signed");
/* ;06:    */     }
/* ;07:687 */     this.signature = Crypto.sign(getBytes(), paramString);
/* ;08:    */   }
/* ;09:    */   
/* ;10:    */   public boolean equals(Object paramObject)
/* ;11:    */   {
/* ;12:692 */     return ((paramObject instanceof TransactionImpl)) && (getId() == ((Transaction)paramObject).getId());
/* ;13:    */   }
/* ;14:    */   
/* ;15:    */   public int hashCode()
/* ;16:    */   {
/* ;17:697 */     return (int)(getId() ^ getId() >>> 32);
/* ;18:    */   }
/* ;19:    */   
/* ;20:    */   public int compareTo(Transaction paramTransaction)
/* ;21:    */   {
/* ;22:702 */     return Long.compare(getId(), paramTransaction.getId());
/* ;23:    */   }
/* ;24:    */   
/* ;25:    */   public boolean verifySignature()
/* ;26:    */   {
/* ;27:706 */     Account localAccount = Account.getAccount(getSenderId());
/* ;28:707 */     if (localAccount == null) {
/* ;29:708 */       return false;
/* ;30:    */     }
/* ;31:710 */     if (this.signature == null) {
/* ;32:711 */       return false;
/* ;33:    */     }
/* ;34:713 */     byte[] arrayOfByte = zeroSignature(getBytes());
/* ;35:714 */     return (Crypto.verify(this.signature, arrayOfByte, this.senderPublicKey, useNQT())) && (localAccount.setOrVerify(this.senderPublicKey, getHeight()));
/* ;36:    */   }
/* ;37:    */   
/* ;38:    */   int getSize()
/* ;39:    */   {
/* ;40:718 */     return signatureOffset() + 64 + (this.version > 0 ? 16 : 0) + this.appendagesSize;
/* ;41:    */   }
/* ;42:    */   
/* ;43:    */   private int signatureOffset()
/* ;44:    */   {
/* ;45:722 */     return 48 + (useNQT() ? 48 : 16);
/* ;46:    */   }
/* ;47:    */   
/* ;48:    */   private boolean useNQT()
/* ;49:    */   {
/* ;50:726 */     return (this.height > 0) && ((this.height < Integer.MAX_VALUE) || (Nxt.getBlockchain().getHeight() >= 0));
/* ;51:    */   }
/* ;52:    */   
/* ;53:    */   private byte[] zeroSignature(byte[] paramArrayOfByte)
/* ;54:    */   {
/* ;55:732 */     int i = signatureOffset();
/* ;56:733 */     for (int j = i; j < i + 64; j++) {
/* ;57:734 */       paramArrayOfByte[j] = 0;
/* ;58:    */     }
/* ;59:736 */     return paramArrayOfByte;
/* ;60:    */   }
/* ;61:    */   
/* ;62:    */   private int getFlags()
/* ;63:    */   {
/* ;64:740 */     int i = 0;
/* ;65:741 */     int j = 1;
/* ;66:742 */     if (this.message != null) {
/* ;67:743 */       i |= j;
/* ;68:    */     }
/* ;69:745 */     j <<= 1;
/* ;70:746 */     if (this.encryptedMessage != null) {
/* ;71:747 */       i |= j;
/* ;72:    */     }
/* ;73:749 */     j <<= 1;
/* ;74:750 */     if (this.publicKeyAnnouncement != null) {
/* ;75:751 */       i |= j;
/* ;76:    */     }
/* ;77:753 */     j <<= 1;
/* ;78:754 */     if (this.encryptToSelfMessage != null) {
/* ;79:755 */       i |= j;
/* ;80:    */     }
/* ;81:757 */     return i;
/* ;82:    */   }
/* ;83:    */   
/* ;84:    */   public void validate()
/* ;85:    */     throws NxtException.ValidationException
/* ;86:    */   {
/* ;87:762 */     for (Appendix.AbstractAppendix localAbstractAppendix : this.appendages) {
/* ;88:763 */       localAbstractAppendix.validate(this);
/* ;89:    */     }
/* ;90:765 */     long l = this.type.minimumFeeNQT(Nxt.getBlockchain().getHeight(), this.appendagesSize);
/* ;91:766 */     if (this.feeNQT < l) {
/* ;92:767 */       throw new NxtException.NotCurrentlyValidException(String.format("Transaction fee %d less than minimum fee %d at height %d", new Object[] { Long.valueOf(this.feeNQT), Long.valueOf(l), Integer.valueOf(Nxt.getBlockchain().getHeight()) }));
/* ;93:    */     }
/* ;94:770 */     if ((Nxt.getBlockchain().getHeight() >= Integer.MAX_VALUE) && 
/* ;95:771 */       (this.type.hasRecipient()) && (this.recipientId != 0L))
/* ;96:    */     {
/* ;97:772 */       Account localAccount = Account.getAccount(this.recipientId);
/* ;98:773 */       if (((localAccount == null) || (localAccount.getPublicKey() == null)) && (this.publicKeyAnnouncement == null)) {
/* ;99:774 */         throw new NxtException.NotCurrentlyValidException("Recipient account does not have a public key, must attach a public key announcement");
/* <00:    */       }
/* <01:    */     }
/* <02:    */   }
/* <03:    */   
/* <04:    */   boolean applyUnconfirmed()
/* <05:    */   {
/* <06:782 */     Account localAccount = Account.getAccount(getSenderId());
/* <07:783 */     return (localAccount != null) && (this.type.applyUnconfirmed(this, localAccount));
/* <08:    */   }
/* <09:    */   
/* <10:    */   void apply()
/* <11:    */   {
/* <12:787 */     Account localAccount1 = Account.getAccount(getSenderId());
/* <13:788 */     localAccount1.apply(this.senderPublicKey, getHeight());
/* <14:789 */     Account localAccount2 = Account.getAccount(this.recipientId);
/* <15:790 */     if ((localAccount2 == null) && (this.recipientId != 0L)) {
/* <16:791 */       localAccount2 = Account.addOrGetAccount(this.recipientId);
/* <17:    */     }
/* <18:793 */     for (Appendix.AbstractAppendix localAbstractAppendix : this.appendages) {
/* <19:794 */       localAbstractAppendix.apply(this, localAccount1, localAccount2);
/* <20:    */     }
/* <21:    */   }
/* <22:    */   
/* <23:    */   void undoUnconfirmed()
/* <24:    */   {
/* <25:799 */     Account localAccount = Account.getAccount(getSenderId());
/* <26:800 */     this.type.undoUnconfirmed(this, localAccount);
/* <27:    */   }
/* <28:    */   
/* <29:    */   boolean isDuplicate(Map<TransactionType, Set<String>> paramMap)
/* <30:    */   {
/* <31:804 */     return this.type.isDuplicate(this, paramMap);
/* <32:    */   }
/* <33:    */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.TransactionImpl
 * JD-Core Version:    0.7.1
 */