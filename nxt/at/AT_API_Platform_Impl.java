/*   1:    */ package nxt.at;
/*   2:    */ 
/*   3:    */ import java.nio.ByteBuffer;
/*   4:    */ import java.nio.ByteOrder;
/*   5:    */ import java.security.MessageDigest;
/*   6:    */ import java.util.Arrays;
/*   7:    */ import nxt.Appendix.Message;
/*   8:    */ import nxt.Block;
/*   9:    */ import nxt.Blockchain;
/*  10:    */ import nxt.Nxt;
/*  11:    */ import nxt.Transaction;
/*  12:    */ import nxt.crypto.Crypto;
/*  13:    */ import nxt.util.Logger;
/*  14:    */ 
/*  15:    */ public class AT_API_Platform_Impl
/*  16:    */   extends AT_API_Impl
/*  17:    */ {
/*  18: 27 */   private static final AT_API_Platform_Impl instance = new AT_API_Platform_Impl();
/*  19:    */   
/*  20:    */   public static AT_API_Platform_Impl getInstance()
/*  21:    */   {
/*  22: 37 */     return instance;
/*  23:    */   }
/*  24:    */   
/*  25:    */   public long get_Block_Timestamp(AT_Machine_State paramAT_Machine_State)
/*  26:    */   {
/*  27: 44 */     int i = paramAT_Machine_State.getHeight();
/*  28: 45 */     return AT_API_Helper.getLongTimestamp(i, 0);
/*  29:    */   }
/*  30:    */   
/*  31:    */   public long get_Creation_Timestamp(AT_Machine_State paramAT_Machine_State)
/*  32:    */   {
/*  33: 51 */     return AT_API_Helper.getLongTimestamp(paramAT_Machine_State.getCreationBlockHeight(), 0);
/*  34:    */   }
/*  35:    */   
/*  36:    */   public long get_Last_Block_Timestamp(AT_Machine_State paramAT_Machine_State)
/*  37:    */   {
/*  38: 58 */     int i = paramAT_Machine_State.getHeight() - 1;
/*  39: 59 */     return AT_API_Helper.getLongTimestamp(i, 0);
/*  40:    */   }
/*  41:    */   
/*  42:    */   public void put_Last_Block_Hash_In_A(AT_Machine_State paramAT_Machine_State)
/*  43:    */   {
/*  44: 64 */     ByteBuffer localByteBuffer = ByteBuffer.allocate(paramAT_Machine_State.get_A1().length * 4);
/*  45: 65 */     localByteBuffer.order(ByteOrder.LITTLE_ENDIAN);
/*  46:    */     
/*  47: 67 */     localByteBuffer.put(Nxt.getBlockchain().getBlockAtHeight(paramAT_Machine_State.getHeight() - 1).getBlockHash());
/*  48:    */     
/*  49: 69 */     localByteBuffer.clear();
/*  50:    */     
/*  51: 71 */     byte[] arrayOfByte = new byte[8];
/*  52:    */     
/*  53: 73 */     localByteBuffer.get(arrayOfByte, 0, 8);
/*  54: 74 */     paramAT_Machine_State.set_A1(arrayOfByte);
/*  55:    */     
/*  56: 76 */     localByteBuffer.get(arrayOfByte, 0, 8);
/*  57: 77 */     paramAT_Machine_State.set_A2(arrayOfByte);
/*  58:    */     
/*  59: 79 */     localByteBuffer.get(arrayOfByte, 0, 8);
/*  60: 80 */     paramAT_Machine_State.set_A3(arrayOfByte);
/*  61:    */     
/*  62: 82 */     localByteBuffer.get(arrayOfByte, 0, 8);
/*  63: 83 */     paramAT_Machine_State.set_A4(arrayOfByte);
/*  64:    */   }
/*  65:    */   
/*  66:    */   public void A_to_Tx_after_Timestamp(long paramLong, AT_Machine_State paramAT_Machine_State)
/*  67:    */   {
/*  68: 91 */     int i = AT_API_Helper.longToHeight(paramLong);
/*  69: 92 */     int j = AT_API_Helper.longToNumOfTx(paramLong);
/*  70:    */     
/*  71: 94 */     byte[] arrayOfByte = paramAT_Machine_State.getId();
/*  72:    */     
/*  73: 96 */     long l = findTransaction(i, paramAT_Machine_State.getHeight(), Long.valueOf(AT_API_Helper.getLong(arrayOfByte)), j, paramAT_Machine_State.minActivationAmount()).longValue();
/*  74: 97 */     Logger.logDebugMessage("tx with id " + l + " found");
/*  75: 98 */     clear_A(paramAT_Machine_State);
/*  76: 99 */     paramAT_Machine_State.set_A1(AT_API_Helper.getByteArray(l));
/*  77:    */   }
/*  78:    */   
/*  79:    */   public long get_Type_for_Tx_in_A(AT_Machine_State paramAT_Machine_State)
/*  80:    */   {
/*  81:105 */     long l = AT_API_Helper.getLong(paramAT_Machine_State.get_A1());
/*  82:    */     
/*  83:107 */     Transaction localTransaction = Nxt.getBlockchain().getTransaction(l);
/*  84:109 */     if ((localTransaction != null) && (localTransaction.getHeight() >= paramAT_Machine_State.getHeight())) {
/*  85:111 */       localTransaction = null;
/*  86:    */     }
/*  87:114 */     if (localTransaction != null)
/*  88:    */     {
/*  89:116 */       if (localTransaction.getMessage() != null) {
/*  90:118 */         return 1L;
/*  91:    */       }
/*  92:122 */       return 0L;
/*  93:    */     }
/*  94:125 */     return -1L;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public long get_Amount_for_Tx_in_A(AT_Machine_State paramAT_Machine_State)
/*  98:    */   {
/*  99:130 */     long l1 = AT_API_Helper.getLong(paramAT_Machine_State.get_A1());
/* 100:    */     
/* 101:132 */     Transaction localTransaction = Nxt.getBlockchain().getTransaction(l1);
/* 102:134 */     if ((localTransaction != null) && (localTransaction.getHeight() >= paramAT_Machine_State.getHeight())) {
/* 103:136 */       localTransaction = null;
/* 104:    */     }
/* 105:139 */     long l2 = -1L;
/* 106:140 */     if (localTransaction != null) {
/* 107:142 */       if (((localTransaction.getMessage() == null) || (paramAT_Machine_State.getHeight() >= 67000)) && (paramAT_Machine_State.minActivationAmount() <= localTransaction.getAmountNQT())) {
/* 108:144 */         l2 = localTransaction.getAmountNQT() - paramAT_Machine_State.minActivationAmount();
/* 109:    */       } else {
/* 110:148 */         l2 = 0L;
/* 111:    */       }
/* 112:    */     }
/* 113:151 */     return l2;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public long get_Timestamp_for_Tx_in_A(AT_Machine_State paramAT_Machine_State)
/* 117:    */   {
/* 118:156 */     long l = AT_API_Helper.getLong(paramAT_Machine_State.get_A1());
/* 119:157 */     Logger.logDebugMessage("get timestamp for tx with id " + l + " found");
/* 120:158 */     Transaction localTransaction = Nxt.getBlockchain().getTransaction(l);
/* 121:160 */     if ((localTransaction != null) && (localTransaction.getHeight() >= paramAT_Machine_State.getHeight())) {
/* 122:162 */       localTransaction = null;
/* 123:    */     }
/* 124:165 */     if (localTransaction != null)
/* 125:    */     {
/* 126:167 */       int i = localTransaction.getHeight();
/* 127:    */       
/* 128:169 */       byte[] arrayOfByte = paramAT_Machine_State.getId();
/* 129:    */       
/* 130:171 */       int j = findTransactionHeight(Long.valueOf(l), i, Long.valueOf(AT_API_Helper.getLong(arrayOfByte)), paramAT_Machine_State.minActivationAmount());
/* 131:    */       
/* 132:173 */       return AT_API_Helper.getLongTimestamp(i, j);
/* 133:    */     }
/* 134:175 */     return -1L;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public long get_Random_Id_for_Tx_in_A(AT_Machine_State paramAT_Machine_State)
/* 138:    */   {
/* 139:180 */     long l1 = AT_API_Helper.getLong(paramAT_Machine_State.get_A1());
/* 140:    */     
/* 141:182 */     Transaction localTransaction = Nxt.getBlockchain().getTransaction(l1);
/* 142:184 */     if ((localTransaction != null) && (localTransaction.getHeight() >= paramAT_Machine_State.getHeight())) {
/* 143:186 */       localTransaction = null;
/* 144:    */     }
/* 145:189 */     if (localTransaction != null)
/* 146:    */     {
/* 147:191 */       int i = localTransaction.getHeight();
/* 148:    */       
/* 149:    */ 
/* 150:194 */       int j = paramAT_Machine_State.getHeight();
/* 151:196 */       if (j - i < AT_Constants.getInstance().BLOCKS_FOR_RANDOM(j))
/* 152:    */       {
/* 153:197 */         paramAT_Machine_State.setWaitForNumberOfBlocks((int)AT_Constants.getInstance().BLOCKS_FOR_RANDOM(j) - (j - i));
/* 154:198 */         paramAT_Machine_State.getMachineState().pc -= 7;
/* 155:199 */         paramAT_Machine_State.getMachineState().stopped = true;
/* 156:200 */         return 0L;
/* 157:    */       }
/* 158:203 */       MessageDigest localMessageDigest = Crypto.sha256();
/* 159:    */       
/* 160:205 */       byte[] arrayOfByte1 = localTransaction.getSenderPublicKey();
/* 161:    */       
/* 162:207 */       ByteBuffer localByteBuffer = ByteBuffer.allocate(96 + arrayOfByte1.length);
/* 163:208 */       localByteBuffer.order(ByteOrder.LITTLE_ENDIAN);
/* 164:209 */       localByteBuffer.put(Nxt.getBlockchain().getBlockAtHeight(j - 1).getGenerationSignature());
/* 165:210 */       localByteBuffer.putLong(localTransaction.getId());
/* 166:211 */       localByteBuffer.put(arrayOfByte1);
/* 167:    */       
/* 168:213 */       localMessageDigest.update(localByteBuffer.array());
/* 169:214 */       byte[] arrayOfByte2 = localMessageDigest.digest();
/* 170:    */       
/* 171:216 */       long l2 = Math.abs(AT_API_Helper.getLong(Arrays.copyOfRange(arrayOfByte2, 0, 8)));
/* 172:    */       
/* 173:    */ 
/* 174:219 */       return l2;
/* 175:    */     }
/* 176:221 */     return -1L;
/* 177:    */   }
/* 178:    */   
/* 179:    */   public void message_from_Tx_in_A_to_B(AT_Machine_State paramAT_Machine_State)
/* 180:    */   {
/* 181:226 */     long l = AT_API_Helper.getLong(paramAT_Machine_State.get_A1());
/* 182:    */     
/* 183:228 */     Transaction localTransaction = Nxt.getBlockchain().getTransaction(l);
/* 184:229 */     if ((localTransaction != null) && (localTransaction.getHeight() >= paramAT_Machine_State.getHeight())) {
/* 185:231 */       localTransaction = null;
/* 186:    */     }
/* 187:233 */     ByteBuffer localByteBuffer = ByteBuffer.allocate(paramAT_Machine_State.get_A1().length * 4);
/* 188:234 */     localByteBuffer.order(ByteOrder.LITTLE_ENDIAN);
/* 189:235 */     if (localTransaction != null)
/* 190:    */     {
/* 191:237 */       localObject = localTransaction.getMessage();
/* 192:238 */       if (localObject != null)
/* 193:    */       {
/* 194:240 */         byte[] arrayOfByte = ((Appendix.Message)localObject).getMessage();
/* 195:241 */         if (arrayOfByte.length <= paramAT_Machine_State.get_A1().length * 4) {
/* 196:243 */           localByteBuffer.put(arrayOfByte);
/* 197:    */         }
/* 198:    */       }
/* 199:    */     }
/* 200:248 */     localByteBuffer.clear();
/* 201:    */     
/* 202:250 */     Object localObject = new byte[8];
/* 203:    */     
/* 204:252 */     localByteBuffer.get((byte[])localObject, 0, 8);
/* 205:253 */     paramAT_Machine_State.set_B1((byte[])localObject);
/* 206:    */     
/* 207:255 */     localByteBuffer.get((byte[])localObject, 0, 8);
/* 208:256 */     paramAT_Machine_State.set_B2((byte[])localObject);
/* 209:    */     
/* 210:258 */     localByteBuffer.get((byte[])localObject, 0, 8);
/* 211:259 */     paramAT_Machine_State.set_B3((byte[])localObject);
/* 212:    */     
/* 213:261 */     localByteBuffer.get((byte[])localObject, 0, 8);
/* 214:262 */     paramAT_Machine_State.set_B4((byte[])localObject);
/* 215:    */   }
/* 216:    */   
/* 217:    */   public void B_to_Address_of_Tx_in_A(AT_Machine_State paramAT_Machine_State)
/* 218:    */   {
/* 219:268 */     long l1 = AT_API_Helper.getLong(paramAT_Machine_State.get_A1());
/* 220:    */     
/* 221:270 */     clear_B(paramAT_Machine_State);
/* 222:    */     
/* 223:272 */     Transaction localTransaction = Nxt.getBlockchain().getTransaction(l1);
/* 224:273 */     if ((localTransaction != null) && (localTransaction.getHeight() >= paramAT_Machine_State.getHeight())) {
/* 225:275 */       localTransaction = null;
/* 226:    */     }
/* 227:277 */     if (localTransaction != null)
/* 228:    */     {
/* 229:279 */       long l2 = localTransaction.getSenderId();
/* 230:280 */       paramAT_Machine_State.set_B1(AT_API_Helper.getByteArray(l2));
/* 231:    */     }
/* 232:    */   }
/* 233:    */   
/* 234:    */   public void B_to_Address_of_Creator(AT_Machine_State paramAT_Machine_State)
/* 235:    */   {
/* 236:286 */     long l = AT_API_Helper.getLong(paramAT_Machine_State.getCreator());
/* 237:    */     
/* 238:288 */     clear_B(paramAT_Machine_State);
/* 239:    */     
/* 240:290 */     paramAT_Machine_State.set_B1(AT_API_Helper.getByteArray(l));
/* 241:    */   }
/* 242:    */   
/* 243:    */   public void put_Last_Block_Generation_Signature_In_A(AT_Machine_State paramAT_Machine_State)
/* 244:    */   {
/* 245:296 */     ByteBuffer localByteBuffer = ByteBuffer.allocate(paramAT_Machine_State.get_A1().length * 4);
/* 246:297 */     localByteBuffer.order(ByteOrder.LITTLE_ENDIAN);
/* 247:    */     
/* 248:299 */     localByteBuffer.put(Nxt.getBlockchain().getBlockAtHeight(paramAT_Machine_State.getHeight() - 1).getGenerationSignature());
/* 249:    */     
/* 250:301 */     byte[] arrayOfByte = new byte[8];
/* 251:    */     
/* 252:303 */     localByteBuffer.get(arrayOfByte, 0, 8);
/* 253:304 */     paramAT_Machine_State.set_A1(arrayOfByte);
/* 254:    */     
/* 255:306 */     localByteBuffer.get(arrayOfByte, 0, 8);
/* 256:307 */     paramAT_Machine_State.set_A2(arrayOfByte);
/* 257:    */     
/* 258:309 */     localByteBuffer.get(arrayOfByte, 0, 8);
/* 259:310 */     paramAT_Machine_State.set_A3(arrayOfByte);
/* 260:    */     
/* 261:312 */     localByteBuffer.get(arrayOfByte, 0, 8);
/* 262:313 */     paramAT_Machine_State.set_A4(arrayOfByte);
/* 263:    */   }
/* 264:    */   
/* 265:    */   public long get_Current_Balance(AT_Machine_State paramAT_Machine_State)
/* 266:    */   {
/* 267:320 */     if (paramAT_Machine_State.getHeight() < 67000) {
/* 268:321 */       return 0L;
/* 269:    */     }
/* 270:324 */     return paramAT_Machine_State.getG_balance().longValue();
/* 271:    */   }
/* 272:    */   
/* 273:    */   public long get_Previous_Balance(AT_Machine_State paramAT_Machine_State)
/* 274:    */   {
/* 275:329 */     if (paramAT_Machine_State.getHeight() < 67000) {
/* 276:330 */       return 0L;
/* 277:    */     }
/* 278:332 */     return paramAT_Machine_State.getP_balance().longValue();
/* 279:    */   }
/* 280:    */   
/* 281:    */   public void send_to_Address_in_B(long paramLong, AT_Machine_State paramAT_Machine_State)
/* 282:    */   {
/* 283:346 */     if (paramLong < 1L) {
/* 284:    */       return;
/* 285:    */     }
/* 286:    */     AT_Transaction localAT_Transaction;
/* 287:348 */     if (paramLong < paramAT_Machine_State.getG_balance().longValue())
/* 288:    */     {
/* 289:351 */       localAT_Transaction = new AT_Transaction(paramAT_Machine_State.getId(), (byte[])paramAT_Machine_State.get_B1().clone(), paramLong, null);
/* 290:352 */       paramAT_Machine_State.addTransaction(localAT_Transaction);
/* 291:    */       
/* 292:354 */       paramAT_Machine_State.setG_balance(Long.valueOf(paramAT_Machine_State.getG_balance().longValue() - paramLong));
/* 293:    */     }
/* 294:    */     else
/* 295:    */     {
/* 296:358 */       localAT_Transaction = new AT_Transaction(paramAT_Machine_State.getId(), (byte[])paramAT_Machine_State.get_B1().clone(), paramAT_Machine_State.getG_balance().longValue(), null);
/* 297:359 */       paramAT_Machine_State.addTransaction(localAT_Transaction);
/* 298:    */       
/* 299:361 */       paramAT_Machine_State.setG_balance(Long.valueOf(0L));
/* 300:    */     }
/* 301:    */   }
/* 302:    */   
/* 303:    */   public void send_All_to_Address_in_B(AT_Machine_State paramAT_Machine_State)
/* 304:    */   {
/* 305:383 */     AT_Transaction localAT_Transaction = new AT_Transaction(paramAT_Machine_State.getId(), (byte[])paramAT_Machine_State.get_B1().clone(), paramAT_Machine_State.getG_balance().longValue(), null);
/* 306:384 */     paramAT_Machine_State.addTransaction(localAT_Transaction);
/* 307:385 */     paramAT_Machine_State.setG_balance(Long.valueOf(0L));
/* 308:    */   }
/* 309:    */   
/* 310:    */   public void send_Old_to_Address_in_B(AT_Machine_State paramAT_Machine_State)
/* 311:    */   {
/* 312:    */     AT_Transaction localAT_Transaction;
/* 313:392 */     if (paramAT_Machine_State.getP_balance().longValue() > paramAT_Machine_State.getG_balance().longValue())
/* 314:    */     {
/* 315:394 */       localAT_Transaction = new AT_Transaction(paramAT_Machine_State.getId(), paramAT_Machine_State.get_B1(), paramAT_Machine_State.getG_balance().longValue(), null);
/* 316:395 */       paramAT_Machine_State.addTransaction(localAT_Transaction);
/* 317:    */       
/* 318:397 */       paramAT_Machine_State.setG_balance(Long.valueOf(0L));
/* 319:398 */       paramAT_Machine_State.setP_balance(Long.valueOf(0L));
/* 320:    */     }
/* 321:    */     else
/* 322:    */     {
/* 323:403 */       localAT_Transaction = new AT_Transaction(paramAT_Machine_State.getId(), paramAT_Machine_State.get_B1(), paramAT_Machine_State.getP_balance().longValue(), null);
/* 324:404 */       paramAT_Machine_State.addTransaction(localAT_Transaction);
/* 325:    */       
/* 326:406 */       paramAT_Machine_State.setG_balance(Long.valueOf(paramAT_Machine_State.getG_balance().longValue() - paramAT_Machine_State.getP_balance().longValue()));
/* 327:407 */       paramAT_Machine_State.setP_balance(Long.valueOf(0L));
/* 328:    */     }
/* 329:    */   }
/* 330:    */   
/* 331:    */   public void send_A_to_Address_in_B(AT_Machine_State paramAT_Machine_State)
/* 332:    */   {
/* 333:416 */     ByteBuffer localByteBuffer = ByteBuffer.allocate(32);
/* 334:417 */     localByteBuffer.order(ByteOrder.LITTLE_ENDIAN);
/* 335:418 */     localByteBuffer.put(paramAT_Machine_State.get_A1());
/* 336:419 */     localByteBuffer.put(paramAT_Machine_State.get_A2());
/* 337:420 */     localByteBuffer.put(paramAT_Machine_State.get_A3());
/* 338:421 */     localByteBuffer.put(paramAT_Machine_State.get_A4());
/* 339:422 */     localByteBuffer.clear();
/* 340:    */     
/* 341:424 */     AT_Transaction localAT_Transaction = new AT_Transaction(paramAT_Machine_State.getId(), paramAT_Machine_State.get_B1(), 0L, localByteBuffer.array());
/* 342:425 */     paramAT_Machine_State.addTransaction(localAT_Transaction);
/* 343:    */   }
/* 344:    */   
/* 345:    */   public long add_Minutes_to_Timestamp(long paramLong1, long paramLong2, AT_Machine_State paramAT_Machine_State)
/* 346:    */   {
/* 347:430 */     int i = AT_API_Helper.longToHeight(paramLong1);
/* 348:431 */     int j = AT_API_Helper.longToNumOfTx(paramLong1);
/* 349:432 */     int k = i + (int)(paramLong2 / AT_Constants.getInstance().AVERAGE_BLOCK_MINUTES(paramAT_Machine_State.getHeight()));
/* 350:    */     
/* 351:434 */     return AT_API_Helper.getLongTimestamp(k, j);
/* 352:    */   }
/* 353:    */   
/* 354:    */   /* Error */
/* 355:    */   protected static Long findTransaction(int paramInt1, int paramInt2, Long paramLong, int paramInt3, long paramLong1)
/* 356:    */   {
/* 357:    */     // Byte code:
/* 358:    */     //   0: invokestatic 87	nxt/db/Db:getConnection	()Ljava/sql/Connection;
/* 359:    */     //   3: astore 6
/* 360:    */     //   5: aconst_null
/* 361:    */     //   6: astore 7
/* 362:    */     //   8: aload 6
/* 363:    */     //   10: ldc 88
/* 364:    */     //   12: invokeinterface 89 2 0
/* 365:    */     //   17: astore 8
/* 366:    */     //   19: aconst_null
/* 367:    */     //   20: astore 9
/* 368:    */     //   22: aload 8
/* 369:    */     //   24: iconst_1
/* 370:    */     //   25: iload_0
/* 371:    */     //   26: invokeinterface 90 3 0
/* 372:    */     //   31: aload 8
/* 373:    */     //   33: iconst_2
/* 374:    */     //   34: iload_1
/* 375:    */     //   35: invokeinterface 90 3 0
/* 376:    */     //   40: aload 8
/* 377:    */     //   42: iconst_3
/* 378:    */     //   43: aload_2
/* 379:    */     //   44: invokevirtual 27	java/lang/Long:longValue	()J
/* 380:    */     //   47: invokeinterface 91 4 0
/* 381:    */     //   52: aload 8
/* 382:    */     //   54: iconst_4
/* 383:    */     //   55: lload 4
/* 384:    */     //   57: invokeinterface 91 4 0
/* 385:    */     //   62: aload 8
/* 386:    */     //   64: iconst_5
/* 387:    */     //   65: iload_3
/* 388:    */     //   66: invokeinterface 90 3 0
/* 389:    */     //   71: aload 8
/* 390:    */     //   73: invokeinterface 92 1 0
/* 391:    */     //   78: astore 10
/* 392:    */     //   80: lconst_0
/* 393:    */     //   81: invokestatic 24	java/lang/Long:valueOf	(J)Ljava/lang/Long;
/* 394:    */     //   84: astore 11
/* 395:    */     //   86: aload 10
/* 396:    */     //   88: invokeinterface 93 1 0
/* 397:    */     //   93: ifeq +17 -> 110
/* 398:    */     //   96: aload 10
/* 399:    */     //   98: ldc 94
/* 400:    */     //   100: invokeinterface 95 2 0
/* 401:    */     //   105: invokestatic 24	java/lang/Long:valueOf	(J)Ljava/lang/Long;
/* 402:    */     //   108: astore 11
/* 403:    */     //   110: aload 10
/* 404:    */     //   112: invokeinterface 96 1 0
/* 405:    */     //   117: aload 11
/* 406:    */     //   119: astore 12
/* 407:    */     //   121: aload 8
/* 408:    */     //   123: ifnull +37 -> 160
/* 409:    */     //   126: aload 9
/* 410:    */     //   128: ifnull +25 -> 153
/* 411:    */     //   131: aload 8
/* 412:    */     //   133: invokeinterface 97 1 0
/* 413:    */     //   138: goto +22 -> 160
/* 414:    */     //   141: astore 13
/* 415:    */     //   143: aload 9
/* 416:    */     //   145: aload 13
/* 417:    */     //   147: invokevirtual 99	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 418:    */     //   150: goto +10 -> 160
/* 419:    */     //   153: aload 8
/* 420:    */     //   155: invokeinterface 97 1 0
/* 421:    */     //   160: aload 6
/* 422:    */     //   162: ifnull +37 -> 199
/* 423:    */     //   165: aload 7
/* 424:    */     //   167: ifnull +25 -> 192
/* 425:    */     //   170: aload 6
/* 426:    */     //   172: invokeinterface 100 1 0
/* 427:    */     //   177: goto +22 -> 199
/* 428:    */     //   180: astore 13
/* 429:    */     //   182: aload 7
/* 430:    */     //   184: aload 13
/* 431:    */     //   186: invokevirtual 99	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 432:    */     //   189: goto +10 -> 199
/* 433:    */     //   192: aload 6
/* 434:    */     //   194: invokeinterface 100 1 0
/* 435:    */     //   199: aload 12
/* 436:    */     //   201: areturn
/* 437:    */     //   202: astore 10
/* 438:    */     //   204: aload 10
/* 439:    */     //   206: astore 9
/* 440:    */     //   208: aload 10
/* 441:    */     //   210: athrow
/* 442:    */     //   211: astore 14
/* 443:    */     //   213: aload 8
/* 444:    */     //   215: ifnull +37 -> 252
/* 445:    */     //   218: aload 9
/* 446:    */     //   220: ifnull +25 -> 245
/* 447:    */     //   223: aload 8
/* 448:    */     //   225: invokeinterface 97 1 0
/* 449:    */     //   230: goto +22 -> 252
/* 450:    */     //   233: astore 15
/* 451:    */     //   235: aload 9
/* 452:    */     //   237: aload 15
/* 453:    */     //   239: invokevirtual 99	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 454:    */     //   242: goto +10 -> 252
/* 455:    */     //   245: aload 8
/* 456:    */     //   247: invokeinterface 97 1 0
/* 457:    */     //   252: aload 14
/* 458:    */     //   254: athrow
/* 459:    */     //   255: astore 8
/* 460:    */     //   257: aload 8
/* 461:    */     //   259: astore 7
/* 462:    */     //   261: aload 8
/* 463:    */     //   263: athrow
/* 464:    */     //   264: astore 16
/* 465:    */     //   266: aload 6
/* 466:    */     //   268: ifnull +37 -> 305
/* 467:    */     //   271: aload 7
/* 468:    */     //   273: ifnull +25 -> 298
/* 469:    */     //   276: aload 6
/* 470:    */     //   278: invokeinterface 100 1 0
/* 471:    */     //   283: goto +22 -> 305
/* 472:    */     //   286: astore 17
/* 473:    */     //   288: aload 7
/* 474:    */     //   290: aload 17
/* 475:    */     //   292: invokevirtual 99	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 476:    */     //   295: goto +10 -> 305
/* 477:    */     //   298: aload 6
/* 478:    */     //   300: invokeinterface 100 1 0
/* 479:    */     //   305: aload 16
/* 480:    */     //   307: athrow
/* 481:    */     //   308: astore 6
/* 482:    */     //   310: new 102	java/lang/RuntimeException
/* 483:    */     //   313: dup
/* 484:    */     //   314: aload 6
/* 485:    */     //   316: invokevirtual 103	java/sql/SQLException:toString	()Ljava/lang/String;
/* 486:    */     //   319: aload 6
/* 487:    */     //   321: invokespecial 104	java/lang/RuntimeException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
/* 488:    */     //   324: athrow
/* 489:    */     // Line number table:
/* 490:    */     //   Java source line #438	-> byte code offset #0
/* 491:    */     //   Java source line #439	-> byte code offset #8
/* 492:    */     //   Java source line #438	-> byte code offset #19
/* 493:    */     //   Java source line #443	-> byte code offset #22
/* 494:    */     //   Java source line #444	-> byte code offset #31
/* 495:    */     //   Java source line #445	-> byte code offset #40
/* 496:    */     //   Java source line #446	-> byte code offset #52
/* 497:    */     //   Java source line #447	-> byte code offset #62
/* 498:    */     //   Java source line #448	-> byte code offset #71
/* 499:    */     //   Java source line #449	-> byte code offset #80
/* 500:    */     //   Java source line #450	-> byte code offset #86
/* 501:    */     //   Java source line #451	-> byte code offset #96
/* 502:    */     //   Java source line #453	-> byte code offset #110
/* 503:    */     //   Java source line #454	-> byte code offset #117
/* 504:    */     //   Java source line #456	-> byte code offset #121
/* 505:    */     //   Java source line #438	-> byte code offset #202
/* 506:    */     //   Java source line #456	-> byte code offset #211
/* 507:    */     //   Java source line #438	-> byte code offset #255
/* 508:    */     //   Java source line #456	-> byte code offset #264
/* 509:    */     //   Java source line #457	-> byte code offset #310
/* 510:    */     // Local variable table:
/* 511:    */     //   start	length	slot	name	signature
/* 512:    */     //   0	325	0	paramInt1	int
/* 513:    */     //   0	325	1	paramInt2	int
/* 514:    */     //   0	325	2	paramLong	Long
/* 515:    */     //   0	325	3	paramInt3	int
/* 516:    */     //   0	325	4	paramLong1	long
/* 517:    */     //   3	296	6	localConnection	java.sql.Connection
/* 518:    */     //   308	12	6	localSQLException	java.sql.SQLException
/* 519:    */     //   6	283	7	localObject1	Object
/* 520:    */     //   17	229	8	localPreparedStatement	java.sql.PreparedStatement
/* 521:    */     //   255	7	8	localThrowable1	Throwable
/* 522:    */     //   20	216	9	localObject2	Object
/* 523:    */     //   78	33	10	localResultSet	java.sql.ResultSet
/* 524:    */     //   202	7	10	localThrowable2	Throwable
/* 525:    */     //   84	34	11	localLong1	Long
/* 526:    */     //   141	5	13	localThrowable3	Throwable
/* 527:    */     //   180	5	13	localThrowable4	Throwable
/* 528:    */     //   211	42	14	localObject3	Object
/* 529:    */     //   233	5	15	localThrowable5	Throwable
/* 530:    */     //   264	42	16	localObject4	Object
/* 531:    */     //   286	5	17	localThrowable6	Throwable
/* 532:    */     // Exception table:
/* 533:    */     //   from	to	target	type
/* 534:    */     //   131	138	141	java/lang/Throwable
/* 535:    */     //   170	177	180	java/lang/Throwable
/* 536:    */     //   22	121	202	java/lang/Throwable
/* 537:    */     //   22	121	211	finally
/* 538:    */     //   202	213	211	finally
/* 539:    */     //   223	230	233	java/lang/Throwable
/* 540:    */     //   8	160	255	java/lang/Throwable
/* 541:    */     //   202	255	255	java/lang/Throwable
/* 542:    */     //   8	160	264	finally
/* 543:    */     //   202	266	264	finally
/* 544:    */     //   276	283	286	java/lang/Throwable
/* 545:    */     //   0	199	308	java/sql/SQLException
/* 546:    */     //   202	308	308	java/sql/SQLException
/* 547:    */   }
/* 548:    */   
/* 549:    */   /* Error */
/* 550:    */   protected static int findTransactionHeight(Long paramLong1, int paramInt, Long paramLong2, long paramLong)
/* 551:    */   {
/* 552:    */     // Byte code:
/* 553:    */     //   0: invokestatic 87	nxt/db/Db:getConnection	()Ljava/sql/Connection;
/* 554:    */     //   3: astore 5
/* 555:    */     //   5: aconst_null
/* 556:    */     //   6: astore 6
/* 557:    */     //   8: aload 5
/* 558:    */     //   10: ldc 105
/* 559:    */     //   12: invokeinterface 89 2 0
/* 560:    */     //   17: astore 7
/* 561:    */     //   19: aconst_null
/* 562:    */     //   20: astore 8
/* 563:    */     //   22: aload 7
/* 564:    */     //   24: iconst_1
/* 565:    */     //   25: iload_1
/* 566:    */     //   26: invokeinterface 90 3 0
/* 567:    */     //   31: aload 7
/* 568:    */     //   33: iconst_2
/* 569:    */     //   34: aload_2
/* 570:    */     //   35: invokevirtual 27	java/lang/Long:longValue	()J
/* 571:    */     //   38: invokeinterface 91 4 0
/* 572:    */     //   43: aload 7
/* 573:    */     //   45: iconst_3
/* 574:    */     //   46: lload_3
/* 575:    */     //   47: invokeinterface 91 4 0
/* 576:    */     //   52: aload 7
/* 577:    */     //   54: invokeinterface 92 1 0
/* 578:    */     //   59: astore 9
/* 579:    */     //   61: iconst_0
/* 580:    */     //   62: istore 10
/* 581:    */     //   64: aload 9
/* 582:    */     //   66: invokeinterface 93 1 0
/* 583:    */     //   71: ifeq +32 -> 103
/* 584:    */     //   74: aload 9
/* 585:    */     //   76: ldc 94
/* 586:    */     //   78: invokeinterface 95 2 0
/* 587:    */     //   83: aload_0
/* 588:    */     //   84: invokevirtual 27	java/lang/Long:longValue	()J
/* 589:    */     //   87: lcmp
/* 590:    */     //   88: ifne +9 -> 97
/* 591:    */     //   91: iinc 10 1
/* 592:    */     //   94: goto +9 -> 103
/* 593:    */     //   97: iinc 10 1
/* 594:    */     //   100: goto -36 -> 64
/* 595:    */     //   103: aload 9
/* 596:    */     //   105: invokeinterface 96 1 0
/* 597:    */     //   110: iload 10
/* 598:    */     //   112: istore 11
/* 599:    */     //   114: aload 7
/* 600:    */     //   116: ifnull +37 -> 153
/* 601:    */     //   119: aload 8
/* 602:    */     //   121: ifnull +25 -> 146
/* 603:    */     //   124: aload 7
/* 604:    */     //   126: invokeinterface 97 1 0
/* 605:    */     //   131: goto +22 -> 153
/* 606:    */     //   134: astore 12
/* 607:    */     //   136: aload 8
/* 608:    */     //   138: aload 12
/* 609:    */     //   140: invokevirtual 99	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 610:    */     //   143: goto +10 -> 153
/* 611:    */     //   146: aload 7
/* 612:    */     //   148: invokeinterface 97 1 0
/* 613:    */     //   153: aload 5
/* 614:    */     //   155: ifnull +37 -> 192
/* 615:    */     //   158: aload 6
/* 616:    */     //   160: ifnull +25 -> 185
/* 617:    */     //   163: aload 5
/* 618:    */     //   165: invokeinterface 100 1 0
/* 619:    */     //   170: goto +22 -> 192
/* 620:    */     //   173: astore 12
/* 621:    */     //   175: aload 6
/* 622:    */     //   177: aload 12
/* 623:    */     //   179: invokevirtual 99	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 624:    */     //   182: goto +10 -> 192
/* 625:    */     //   185: aload 5
/* 626:    */     //   187: invokeinterface 100 1 0
/* 627:    */     //   192: iload 11
/* 628:    */     //   194: ireturn
/* 629:    */     //   195: astore 9
/* 630:    */     //   197: aload 9
/* 631:    */     //   199: astore 8
/* 632:    */     //   201: aload 9
/* 633:    */     //   203: athrow
/* 634:    */     //   204: astore 13
/* 635:    */     //   206: aload 7
/* 636:    */     //   208: ifnull +37 -> 245
/* 637:    */     //   211: aload 8
/* 638:    */     //   213: ifnull +25 -> 238
/* 639:    */     //   216: aload 7
/* 640:    */     //   218: invokeinterface 97 1 0
/* 641:    */     //   223: goto +22 -> 245
/* 642:    */     //   226: astore 14
/* 643:    */     //   228: aload 8
/* 644:    */     //   230: aload 14
/* 645:    */     //   232: invokevirtual 99	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 646:    */     //   235: goto +10 -> 245
/* 647:    */     //   238: aload 7
/* 648:    */     //   240: invokeinterface 97 1 0
/* 649:    */     //   245: aload 13
/* 650:    */     //   247: athrow
/* 651:    */     //   248: astore 7
/* 652:    */     //   250: aload 7
/* 653:    */     //   252: astore 6
/* 654:    */     //   254: aload 7
/* 655:    */     //   256: athrow
/* 656:    */     //   257: astore 15
/* 657:    */     //   259: aload 5
/* 658:    */     //   261: ifnull +37 -> 298
/* 659:    */     //   264: aload 6
/* 660:    */     //   266: ifnull +25 -> 291
/* 661:    */     //   269: aload 5
/* 662:    */     //   271: invokeinterface 100 1 0
/* 663:    */     //   276: goto +22 -> 298
/* 664:    */     //   279: astore 16
/* 665:    */     //   281: aload 6
/* 666:    */     //   283: aload 16
/* 667:    */     //   285: invokevirtual 99	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 668:    */     //   288: goto +10 -> 298
/* 669:    */     //   291: aload 5
/* 670:    */     //   293: invokeinterface 100 1 0
/* 671:    */     //   298: aload 15
/* 672:    */     //   300: athrow
/* 673:    */     //   301: astore 5
/* 674:    */     //   303: new 102	java/lang/RuntimeException
/* 675:    */     //   306: dup
/* 676:    */     //   307: aload 5
/* 677:    */     //   309: invokevirtual 103	java/sql/SQLException:toString	()Ljava/lang/String;
/* 678:    */     //   312: aload 5
/* 679:    */     //   314: invokespecial 104	java/lang/RuntimeException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
/* 680:    */     //   317: athrow
/* 681:    */     // Line number table:
/* 682:    */     //   Java source line #463	-> byte code offset #0
/* 683:    */     //   Java source line #464	-> byte code offset #8
/* 684:    */     //   Java source line #463	-> byte code offset #19
/* 685:    */     //   Java source line #467	-> byte code offset #22
/* 686:    */     //   Java source line #468	-> byte code offset #31
/* 687:    */     //   Java source line #469	-> byte code offset #43
/* 688:    */     //   Java source line #470	-> byte code offset #52
/* 689:    */     //   Java source line #472	-> byte code offset #61
/* 690:    */     //   Java source line #473	-> byte code offset #64
/* 691:    */     //   Java source line #474	-> byte code offset #74
/* 692:    */     //   Java source line #475	-> byte code offset #91
/* 693:    */     //   Java source line #476	-> byte code offset #94
/* 694:    */     //   Java source line #478	-> byte code offset #97
/* 695:    */     //   Java source line #480	-> byte code offset #103
/* 696:    */     //   Java source line #481	-> byte code offset #110
/* 697:    */     //   Java source line #483	-> byte code offset #114
/* 698:    */     //   Java source line #463	-> byte code offset #195
/* 699:    */     //   Java source line #483	-> byte code offset #204
/* 700:    */     //   Java source line #463	-> byte code offset #248
/* 701:    */     //   Java source line #483	-> byte code offset #257
/* 702:    */     //   Java source line #484	-> byte code offset #303
/* 703:    */     // Local variable table:
/* 704:    */     //   start	length	slot	name	signature
/* 705:    */     //   0	318	0	paramLong1	Long
/* 706:    */     //   0	318	1	paramInt	int
/* 707:    */     //   0	318	2	paramLong2	Long
/* 708:    */     //   0	318	3	paramLong	long
/* 709:    */     //   3	289	5	localConnection	java.sql.Connection
/* 710:    */     //   301	12	5	localSQLException	java.sql.SQLException
/* 711:    */     //   6	276	6	localObject1	Object
/* 712:    */     //   17	222	7	localPreparedStatement	java.sql.PreparedStatement
/* 713:    */     //   248	7	7	localThrowable1	Throwable
/* 714:    */     //   20	209	8	localObject2	Object
/* 715:    */     //   59	45	9	localResultSet	java.sql.ResultSet
/* 716:    */     //   195	7	9	localThrowable2	Throwable
/* 717:    */     //   62	49	10	i	int
/* 718:    */     //   134	5	12	localThrowable3	Throwable
/* 719:    */     //   173	5	12	localThrowable4	Throwable
/* 720:    */     //   204	42	13	localObject3	Object
/* 721:    */     //   226	5	14	localThrowable5	Throwable
/* 722:    */     //   257	42	15	localObject4	Object
/* 723:    */     //   279	5	16	localThrowable6	Throwable
/* 724:    */     // Exception table:
/* 725:    */     //   from	to	target	type
/* 726:    */     //   124	131	134	java/lang/Throwable
/* 727:    */     //   163	170	173	java/lang/Throwable
/* 728:    */     //   22	114	195	java/lang/Throwable
/* 729:    */     //   22	114	204	finally
/* 730:    */     //   195	206	204	finally
/* 731:    */     //   216	223	226	java/lang/Throwable
/* 732:    */     //   8	153	248	java/lang/Throwable
/* 733:    */     //   195	248	248	java/lang/Throwable
/* 734:    */     //   8	153	257	finally
/* 735:    */     //   195	259	257	finally
/* 736:    */     //   269	276	279	java/lang/Throwable
/* 737:    */     //   0	192	301	java/sql/SQLException
/* 738:    */     //   195	301	301	java/sql/SQLException
/* 739:    */   }
/* 740:    */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.at.AT_API_Platform_Impl
 * JD-Core Version:    0.7.1
 */