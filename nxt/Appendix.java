/*   1:    */ package nxt;
/*   2:    */ 
/*   3:    */ import java.nio.ByteBuffer;
/*   4:    */ import java.util.Arrays;
/*   5:    */ import nxt.crypto.EncryptedData;
/*   6:    */ import nxt.util.Convert;
/*   7:    */ import org.json.simple.JSONObject;
/*   8:    */ 
/*   9:    */ public abstract interface Appendix
/*  10:    */ {
/*  11:    */   public abstract int getSize();
/*  12:    */   
/*  13:    */   public abstract void putBytes(ByteBuffer paramByteBuffer);
/*  14:    */   
/*  15:    */   public abstract JSONObject getJSONObject();
/*  16:    */   
/*  17:    */   public abstract byte getVersion();
/*  18:    */   
/*  19:    */   public static abstract class AbstractAppendix
/*  20:    */     implements Appendix
/*  21:    */   {
/*  22:    */     private final byte version;
/*  23:    */     
/*  24:    */     AbstractAppendix(JSONObject paramJSONObject)
/*  25:    */     {
/*  26: 22 */       Long localLong = (Long)paramJSONObject.get("version." + getAppendixName());
/*  27: 23 */       this.version = ((byte)(int)(localLong == null ? 0L : localLong.longValue()));
/*  28:    */     }
/*  29:    */     
/*  30:    */     AbstractAppendix(ByteBuffer paramByteBuffer, byte paramByte)
/*  31:    */     {
/*  32: 27 */       if (paramByte == 0) {
/*  33: 28 */         this.version = 0;
/*  34:    */       } else {
/*  35: 30 */         this.version = paramByteBuffer.get();
/*  36:    */       }
/*  37:    */     }
/*  38:    */     
/*  39:    */     AbstractAppendix(int paramInt)
/*  40:    */     {
/*  41: 35 */       this.version = ((byte)paramInt);
/*  42:    */     }
/*  43:    */     
/*  44:    */     AbstractAppendix()
/*  45:    */     {
/*  46: 39 */       this.version = ((byte)(Nxt.getBlockchain().getHeight() < 11800 ? 0 : 1));
/*  47:    */     }
/*  48:    */     
/*  49:    */     abstract String getAppendixName();
/*  50:    */     
/*  51:    */     public final int getSize()
/*  52:    */     {
/*  53: 46 */       return getMySize() + (this.version > 0 ? 1 : 0);
/*  54:    */     }
/*  55:    */     
/*  56:    */     abstract int getMySize();
/*  57:    */     
/*  58:    */     public final void putBytes(ByteBuffer paramByteBuffer)
/*  59:    */     {
/*  60: 53 */       if (this.version > 0) {
/*  61: 54 */         paramByteBuffer.put(this.version);
/*  62:    */       }
/*  63: 56 */       putMyBytes(paramByteBuffer);
/*  64:    */     }
/*  65:    */     
/*  66:    */     abstract void putMyBytes(ByteBuffer paramByteBuffer);
/*  67:    */     
/*  68:    */     public final JSONObject getJSONObject()
/*  69:    */     {
/*  70: 63 */       JSONObject localJSONObject = new JSONObject();
/*  71: 64 */       if (this.version > 0) {
/*  72: 65 */         localJSONObject.put("version." + getAppendixName(), Byte.valueOf(this.version));
/*  73:    */       }
/*  74: 67 */       putMyJSON(localJSONObject);
/*  75: 68 */       return localJSONObject;
/*  76:    */     }
/*  77:    */     
/*  78:    */     abstract void putMyJSON(JSONObject paramJSONObject);
/*  79:    */     
/*  80:    */     public final byte getVersion()
/*  81:    */     {
/*  82: 75 */       return this.version;
/*  83:    */     }
/*  84:    */     
/*  85:    */     boolean verifyVersion(byte paramByte)
/*  86:    */     {
/*  87: 79 */       return this.version == 0;
/*  88:    */     }
/*  89:    */     
/*  90:    */     abstract void validate(Transaction paramTransaction)
/*  91:    */       throws NxtException.ValidationException;
/*  92:    */     
/*  93:    */     abstract void apply(Transaction paramTransaction, Account paramAccount1, Account paramAccount2);
/*  94:    */   }
/*  95:    */   
/*  96:    */   public static class Message
/*  97:    */     extends Appendix.AbstractAppendix
/*  98:    */   {
/*  99:    */     private final byte[] message;
/* 100:    */     private final boolean isText;
/* 101:    */     
/* 102:    */     static Message parse(JSONObject paramJSONObject)
/* 103:    */       throws NxtException.NotValidException
/* 104:    */     {
/* 105: 91 */       if (paramJSONObject.get("message") == null) {
/* 106: 92 */         return null;
/* 107:    */       }
/* 108: 94 */       return new Message(paramJSONObject);
/* 109:    */     }
/* 110:    */     
/* 111:    */     Message(ByteBuffer paramByteBuffer, byte paramByte)
/* 112:    */       throws NxtException.NotValidException
/* 113:    */     {
/* 114:101 */       super(paramByte);
/* 115:102 */       int i = paramByteBuffer.getInt();
/* 116:103 */       this.isText = (i < 0);
/* 117:104 */       if (i < 0) {
/* 118:105 */         i &= 0x7FFFFFFF;
/* 119:    */       }
/* 120:107 */       if (i > 1000) {
/* 121:108 */         throw new NxtException.NotValidException("Invalid arbitrary message length: " + i);
/* 122:    */       }
/* 123:110 */       this.message = new byte[i];
/* 124:111 */       paramByteBuffer.get(this.message);
/* 125:    */     }
/* 126:    */     
/* 127:    */     Message(JSONObject paramJSONObject)
/* 128:    */     {
/* 129:115 */       super();
/* 130:116 */       String str = (String)paramJSONObject.get("message");
/* 131:117 */       this.isText = Boolean.TRUE.equals(paramJSONObject.get("messageIsText"));
/* 132:118 */       this.message = (this.isText ? Convert.toBytes(str) : Convert.parseHexString(str));
/* 133:    */     }
/* 134:    */     
/* 135:    */     public Message(byte[] paramArrayOfByte)
/* 136:    */     {
/* 137:122 */       this.message = paramArrayOfByte;
/* 138:123 */       this.isText = false;
/* 139:    */     }
/* 140:    */     
/* 141:    */     public Message(String paramString)
/* 142:    */     {
/* 143:127 */       this.message = Convert.toBytes(paramString);
/* 144:128 */       this.isText = true;
/* 145:    */     }
/* 146:    */     
/* 147:    */     String getAppendixName()
/* 148:    */     {
/* 149:133 */       return "Message";
/* 150:    */     }
/* 151:    */     
/* 152:    */     int getMySize()
/* 153:    */     {
/* 154:138 */       return 4 + this.message.length;
/* 155:    */     }
/* 156:    */     
/* 157:    */     void putMyBytes(ByteBuffer paramByteBuffer)
/* 158:    */     {
/* 159:143 */       paramByteBuffer.putInt(this.isText ? this.message.length | 0x80000000 : this.message.length);
/* 160:144 */       paramByteBuffer.put(this.message);
/* 161:    */     }
/* 162:    */     
/* 163:    */     void putMyJSON(JSONObject paramJSONObject)
/* 164:    */     {
/* 165:149 */       paramJSONObject.put("message", this.isText ? Convert.toString(this.message) : Convert.toHexString(this.message));
/* 166:150 */       paramJSONObject.put("messageIsText", Boolean.valueOf(this.isText));
/* 167:    */     }
/* 168:    */     
/* 169:    */     void validate(Transaction paramTransaction)
/* 170:    */       throws NxtException.ValidationException
/* 171:    */     {
/* 172:155 */       if ((this.isText) && (paramTransaction.getVersion() == 0)) {
/* 173:156 */         throw new NxtException.NotValidException("Text messages not yet enabled");
/* 174:    */       }
/* 175:158 */       if ((paramTransaction.getVersion() == 0) && (paramTransaction.getAttachment() != Attachment.ARBITRARY_MESSAGE)) {
/* 176:159 */         throw new NxtException.NotValidException("Message attachments not enabled for version 0 transactions");
/* 177:    */       }
/* 178:161 */       if (this.message.length > 1000) {
/* 179:162 */         throw new NxtException.NotValidException("Invalid arbitrary message length: " + this.message.length);
/* 180:    */       }
/* 181:    */     }
/* 182:    */     
/* 183:    */     void apply(Transaction paramTransaction, Account paramAccount1, Account paramAccount2) {}
/* 184:    */     
/* 185:    */     public byte[] getMessage()
/* 186:    */     {
/* 187:170 */       return this.message;
/* 188:    */     }
/* 189:    */     
/* 190:    */     public boolean isText()
/* 191:    */     {
/* 192:174 */       return this.isText;
/* 193:    */     }
/* 194:    */   }
/* 195:    */   
/* 196:    */   public static abstract class AbstractEncryptedMessage
/* 197:    */     extends Appendix.AbstractAppendix
/* 198:    */   {
/* 199:    */     private final EncryptedData encryptedData;
/* 200:    */     private final boolean isText;
/* 201:    */     
/* 202:    */     private AbstractEncryptedMessage(ByteBuffer paramByteBuffer, byte paramByte)
/* 203:    */       throws NxtException.NotValidException
/* 204:    */     {
/* 205:184 */       super(paramByte);
/* 206:185 */       int i = paramByteBuffer.getInt();
/* 207:186 */       this.isText = (i < 0);
/* 208:187 */       if (i < 0) {
/* 209:188 */         i &= 0x7FFFFFFF;
/* 210:    */       }
/* 211:190 */       this.encryptedData = EncryptedData.readEncryptedData(paramByteBuffer, i, 1000);
/* 212:    */     }
/* 213:    */     
/* 214:    */     private AbstractEncryptedMessage(JSONObject paramJSONObject1, JSONObject paramJSONObject2)
/* 215:    */     {
/* 216:194 */       super();
/* 217:195 */       byte[] arrayOfByte1 = Convert.parseHexString((String)paramJSONObject2.get("data"));
/* 218:196 */       byte[] arrayOfByte2 = Convert.parseHexString((String)paramJSONObject2.get("nonce"));
/* 219:197 */       this.encryptedData = new EncryptedData(arrayOfByte1, arrayOfByte2);
/* 220:198 */       this.isText = Boolean.TRUE.equals(paramJSONObject2.get("isText"));
/* 221:    */     }
/* 222:    */     
/* 223:    */     private AbstractEncryptedMessage(EncryptedData paramEncryptedData, boolean paramBoolean)
/* 224:    */     {
/* 225:202 */       this.encryptedData = paramEncryptedData;
/* 226:203 */       this.isText = paramBoolean;
/* 227:    */     }
/* 228:    */     
/* 229:    */     int getMySize()
/* 230:    */     {
/* 231:208 */       return 4 + this.encryptedData.getSize();
/* 232:    */     }
/* 233:    */     
/* 234:    */     void putMyBytes(ByteBuffer paramByteBuffer)
/* 235:    */     {
/* 236:213 */       paramByteBuffer.putInt(this.isText ? this.encryptedData.getData().length | 0x80000000 : this.encryptedData.getData().length);
/* 237:214 */       paramByteBuffer.put(this.encryptedData.getData());
/* 238:215 */       paramByteBuffer.put(this.encryptedData.getNonce());
/* 239:    */     }
/* 240:    */     
/* 241:    */     void putMyJSON(JSONObject paramJSONObject)
/* 242:    */     {
/* 243:220 */       paramJSONObject.put("data", Convert.toHexString(this.encryptedData.getData()));
/* 244:221 */       paramJSONObject.put("nonce", Convert.toHexString(this.encryptedData.getNonce()));
/* 245:222 */       paramJSONObject.put("isText", Boolean.valueOf(this.isText));
/* 246:    */     }
/* 247:    */     
/* 248:    */     void validate(Transaction paramTransaction)
/* 249:    */       throws NxtException.ValidationException
/* 250:    */     {
/* 251:227 */       if (this.encryptedData.getData().length > 1000) {
/* 252:228 */         throw new NxtException.NotValidException("Max encrypted message length exceeded");
/* 253:    */       }
/* 254:230 */       if (((this.encryptedData.getNonce().length != 32) && (this.encryptedData.getData().length > 0)) || ((this.encryptedData.getNonce().length != 0) && (this.encryptedData.getData().length == 0))) {
/* 255:232 */         throw new NxtException.NotValidException("Invalid nonce length " + this.encryptedData.getNonce().length);
/* 256:    */       }
/* 257:    */     }
/* 258:    */     
/* 259:    */     void apply(Transaction paramTransaction, Account paramAccount1, Account paramAccount2) {}
/* 260:    */     
/* 261:    */     public final EncryptedData getEncryptedData()
/* 262:    */     {
/* 263:239 */       return this.encryptedData;
/* 264:    */     }
/* 265:    */     
/* 266:    */     public final boolean isText()
/* 267:    */     {
/* 268:243 */       return this.isText;
/* 269:    */     }
/* 270:    */   }
/* 271:    */   
/* 272:    */   public static class EncryptedMessage
/* 273:    */     extends Appendix.AbstractEncryptedMessage
/* 274:    */   {
/* 275:    */     static EncryptedMessage parse(JSONObject paramJSONObject)
/* 276:    */       throws NxtException.NotValidException
/* 277:    */     {
/* 278:251 */       if (paramJSONObject.get("encryptedMessage") == null) {
/* 279:252 */         return null;
/* 280:    */       }
/* 281:254 */       return new EncryptedMessage(paramJSONObject);
/* 282:    */     }
/* 283:    */     
/* 284:    */     EncryptedMessage(ByteBuffer paramByteBuffer, byte paramByte)
/* 285:    */       throws NxtException.ValidationException
/* 286:    */     {
/* 287:258 */       super(paramByte, null);
/* 288:    */     }
/* 289:    */     
/* 290:    */     EncryptedMessage(JSONObject paramJSONObject)
/* 291:    */       throws NxtException.NotValidException
/* 292:    */     {
/* 293:262 */       super((JSONObject)paramJSONObject.get("encryptedMessage"), null);
/* 294:    */     }
/* 295:    */     
/* 296:    */     public EncryptedMessage(EncryptedData paramEncryptedData, boolean paramBoolean)
/* 297:    */     {
/* 298:266 */       super(paramBoolean, null);
/* 299:    */     }
/* 300:    */     
/* 301:    */     String getAppendixName()
/* 302:    */     {
/* 303:271 */       return "EncryptedMessage";
/* 304:    */     }
/* 305:    */     
/* 306:    */     void putMyJSON(JSONObject paramJSONObject)
/* 307:    */     {
/* 308:276 */       JSONObject localJSONObject = new JSONObject();
/* 309:277 */       super.putMyJSON(localJSONObject);
/* 310:278 */       paramJSONObject.put("encryptedMessage", localJSONObject);
/* 311:    */     }
/* 312:    */     
/* 313:    */     void validate(Transaction paramTransaction)
/* 314:    */       throws NxtException.ValidationException
/* 315:    */     {
/* 316:283 */       super.validate(paramTransaction);
/* 317:284 */       if (!paramTransaction.getType().hasRecipient()) {
/* 318:285 */         throw new NxtException.NotValidException("Encrypted messages cannot be attached to transactions with no recipient");
/* 319:    */       }
/* 320:287 */       if (paramTransaction.getVersion() == 0) {
/* 321:288 */         throw new NxtException.NotValidException("Encrypted message attachments not enabled for version 0 transactions");
/* 322:    */       }
/* 323:    */     }
/* 324:    */   }
/* 325:    */   
/* 326:    */   public static class EncryptToSelfMessage
/* 327:    */     extends Appendix.AbstractEncryptedMessage
/* 328:    */   {
/* 329:    */     static EncryptToSelfMessage parse(JSONObject paramJSONObject)
/* 330:    */       throws NxtException.NotValidException
/* 331:    */     {
/* 332:297 */       if (paramJSONObject.get("encryptToSelfMessage") == null) {
/* 333:298 */         return null;
/* 334:    */       }
/* 335:300 */       return new EncryptToSelfMessage(paramJSONObject);
/* 336:    */     }
/* 337:    */     
/* 338:    */     EncryptToSelfMessage(ByteBuffer paramByteBuffer, byte paramByte)
/* 339:    */       throws NxtException.ValidationException
/* 340:    */     {
/* 341:304 */       super(paramByte, null);
/* 342:    */     }
/* 343:    */     
/* 344:    */     EncryptToSelfMessage(JSONObject paramJSONObject)
/* 345:    */       throws NxtException.NotValidException
/* 346:    */     {
/* 347:308 */       super((JSONObject)paramJSONObject.get("encryptToSelfMessage"), null);
/* 348:    */     }
/* 349:    */     
/* 350:    */     public EncryptToSelfMessage(EncryptedData paramEncryptedData, boolean paramBoolean)
/* 351:    */     {
/* 352:312 */       super(paramBoolean, null);
/* 353:    */     }
/* 354:    */     
/* 355:    */     String getAppendixName()
/* 356:    */     {
/* 357:317 */       return "EncryptToSelfMessage";
/* 358:    */     }
/* 359:    */     
/* 360:    */     void putMyJSON(JSONObject paramJSONObject)
/* 361:    */     {
/* 362:322 */       JSONObject localJSONObject = new JSONObject();
/* 363:323 */       super.putMyJSON(localJSONObject);
/* 364:324 */       paramJSONObject.put("encryptToSelfMessage", localJSONObject);
/* 365:    */     }
/* 366:    */     
/* 367:    */     void validate(Transaction paramTransaction)
/* 368:    */       throws NxtException.ValidationException
/* 369:    */     {
/* 370:329 */       super.validate(paramTransaction);
/* 371:330 */       if (paramTransaction.getVersion() == 0) {
/* 372:331 */         throw new NxtException.NotValidException("Encrypt-to-self message attachments not enabled for version 0 transactions");
/* 373:    */       }
/* 374:    */     }
/* 375:    */   }
/* 376:    */   
/* 377:    */   public static class PublicKeyAnnouncement
/* 378:    */     extends Appendix.AbstractAppendix
/* 379:    */   {
/* 380:    */     private final byte[] publicKey;
/* 381:    */     
/* 382:    */     static PublicKeyAnnouncement parse(JSONObject paramJSONObject)
/* 383:    */       throws NxtException.NotValidException
/* 384:    */     {
/* 385:340 */       if (paramJSONObject.get("recipientPublicKey") == null) {
/* 386:341 */         return null;
/* 387:    */       }
/* 388:343 */       return new PublicKeyAnnouncement(paramJSONObject);
/* 389:    */     }
/* 390:    */     
/* 391:    */     PublicKeyAnnouncement(ByteBuffer paramByteBuffer, byte paramByte)
/* 392:    */     {
/* 393:349 */       super(paramByte);
/* 394:350 */       this.publicKey = new byte[32];
/* 395:351 */       paramByteBuffer.get(this.publicKey);
/* 396:    */     }
/* 397:    */     
/* 398:    */     PublicKeyAnnouncement(JSONObject paramJSONObject)
/* 399:    */     {
/* 400:355 */       super();
/* 401:356 */       this.publicKey = Convert.parseHexString((String)paramJSONObject.get("recipientPublicKey"));
/* 402:    */     }
/* 403:    */     
/* 404:    */     public PublicKeyAnnouncement(byte[] paramArrayOfByte)
/* 405:    */     {
/* 406:360 */       this.publicKey = paramArrayOfByte;
/* 407:    */     }
/* 408:    */     
/* 409:    */     String getAppendixName()
/* 410:    */     {
/* 411:365 */       return "PublicKeyAnnouncement";
/* 412:    */     }
/* 413:    */     
/* 414:    */     int getMySize()
/* 415:    */     {
/* 416:370 */       return 32;
/* 417:    */     }
/* 418:    */     
/* 419:    */     void putMyBytes(ByteBuffer paramByteBuffer)
/* 420:    */     {
/* 421:375 */       paramByteBuffer.put(this.publicKey);
/* 422:    */     }
/* 423:    */     
/* 424:    */     void putMyJSON(JSONObject paramJSONObject)
/* 425:    */     {
/* 426:380 */       paramJSONObject.put("recipientPublicKey", Convert.toHexString(this.publicKey));
/* 427:    */     }
/* 428:    */     
/* 429:    */     void validate(Transaction paramTransaction)
/* 430:    */       throws NxtException.ValidationException
/* 431:    */     {
/* 432:385 */       if (!paramTransaction.getType().hasRecipient()) {
/* 433:386 */         throw new NxtException.NotValidException("PublicKeyAnnouncement cannot be attached to transactions with no recipient");
/* 434:    */       }
/* 435:388 */       if (this.publicKey.length != 32) {
/* 436:389 */         throw new NxtException.NotValidException("Invalid recipient public key length: " + Convert.toHexString(this.publicKey));
/* 437:    */       }
/* 438:391 */       long l = paramTransaction.getRecipientId();
/* 439:392 */       if (Account.getId(this.publicKey) != l) {
/* 440:393 */         throw new NxtException.NotValidException("Announced public key does not match recipient accountId");
/* 441:    */       }
/* 442:395 */       if (paramTransaction.getVersion() == 0) {
/* 443:396 */         throw new NxtException.NotValidException("Public key announcements not enabled for version 0 transactions");
/* 444:    */       }
/* 445:398 */       Account localAccount = Account.getAccount(l);
/* 446:399 */       if ((localAccount != null) && (localAccount.getPublicKey() != null) && (!Arrays.equals(this.publicKey, localAccount.getPublicKey()))) {
/* 447:400 */         throw new NxtException.NotCurrentlyValidException("A different public key for this account has already been announced");
/* 448:    */       }
/* 449:    */     }
/* 450:    */     
/* 451:    */     void apply(Transaction paramTransaction, Account paramAccount1, Account paramAccount2)
/* 452:    */     {
/* 453:406 */       if (paramAccount2.setOrVerify(this.publicKey, paramTransaction.getHeight())) {
/* 454:407 */         paramAccount2.apply(this.publicKey, paramTransaction.getHeight());
/* 455:    */       }
/* 456:    */     }
/* 457:    */     
/* 458:    */     public byte[] getPublicKey()
/* 459:    */     {
/* 460:412 */       return this.publicKey;
/* 461:    */     }
/* 462:    */   }
/* 463:    */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.Appendix
 * JD-Core Version:    0.7.1
 */