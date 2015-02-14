/*   1:    */ package nxt.at;
/*   2:    */ 
/*   3:    */ import java.nio.BufferUnderflowException;
/*   4:    */ import java.nio.ByteBuffer;
/*   5:    */ import java.nio.ByteOrder;
/*   6:    */ import java.security.MessageDigest;
/*   7:    */ import java.security.NoSuchAlgorithmException;
/*   8:    */ import java.util.ArrayList;
/*   9:    */ import java.util.Arrays;
/*  10:    */ import java.util.Iterator;
/*  11:    */ import java.util.LinkedHashMap;
/*  12:    */ import java.util.List;
/*  13:    */ import java.util.TreeSet;
/*  14:    */ import nxt.AT;
/*  15:    */ import nxt.Account;
/*  16:    */ import nxt.util.Convert;
/*  17:    */ import nxt.util.Logger;
/*  18:    */ 
/*  19:    */ public abstract class AT_Controller
/*  20:    */ {
/*  21:    */   public static int runSteps(AT_Machine_State paramAT_Machine_State)
/*  22:    */   {
/*  23: 29 */     paramAT_Machine_State.getMachineState().running = true;
/*  24: 30 */     paramAT_Machine_State.getMachineState().stopped = false;
/*  25: 31 */     paramAT_Machine_State.getMachineState().finished = false;
/*  26: 32 */     paramAT_Machine_State.getMachineState().dead = false;
/*  27: 33 */     paramAT_Machine_State.getMachineState().steps = 0;
/*  28:    */     
/*  29: 35 */     AT_Machine_Processor localAT_Machine_Processor = new AT_Machine_Processor(paramAT_Machine_State);
/*  30:    */     
/*  31:    */ 
/*  32:    */ 
/*  33: 39 */     paramAT_Machine_State.setFreeze(false);
/*  34:    */     
/*  35: 41 */     long l = AT_Constants.getInstance().STEP_FEE(paramAT_Machine_State.getCreationBlockHeight());
/*  36:    */     
/*  37: 43 */     int i = 0;
/*  38: 47 */     while (paramAT_Machine_State.getMachineState().steps + (i = getNumSteps(paramAT_Machine_State.getAp_code().get(paramAT_Machine_State.getMachineState().pc), paramAT_Machine_State.getCreationBlockHeight())) <= AT_Constants.getInstance().MAX_STEPS(paramAT_Machine_State.getHeight()))
/*  39:    */     {
/*  40: 49 */       if (paramAT_Machine_State.getG_balance().longValue() < l * i)
/*  41:    */       {
/*  42: 52 */         paramAT_Machine_State.setFreeze(true);
/*  43: 53 */         return 3;
/*  44:    */       }
/*  45: 56 */       paramAT_Machine_State.setG_balance(Long.valueOf(paramAT_Machine_State.getG_balance().longValue() - l * i));
/*  46: 57 */       paramAT_Machine_State.getMachineState().steps += i;
/*  47: 58 */       int j = localAT_Machine_Processor.processOp(false, false);
/*  48: 60 */       if (j >= 0)
/*  49:    */       {
/*  50: 62 */         if (paramAT_Machine_State.getMachineState().stopped)
/*  51:    */         {
/*  52: 65 */           paramAT_Machine_State.getMachineState().running = false;
/*  53: 66 */           return 2;
/*  54:    */         }
/*  55: 68 */         if (paramAT_Machine_State.getMachineState().finished)
/*  56:    */         {
/*  57: 71 */           paramAT_Machine_State.getMachineState().running = false;
/*  58: 72 */           return 1;
/*  59:    */         }
/*  60:    */       }
/*  61: 84 */       else if (paramAT_Machine_State.getMachineState().jumps.contains(Integer.valueOf(paramAT_Machine_State.getMachineState().err)))
/*  62:    */       {
/*  63: 86 */         paramAT_Machine_State.getMachineState().pc = paramAT_Machine_State.getMachineState().err;
/*  64:    */       }
/*  65:    */       else
/*  66:    */       {
/*  67: 90 */         paramAT_Machine_State.getMachineState().dead = true;
/*  68: 91 */         paramAT_Machine_State.getMachineState().running = false;
/*  69: 92 */         return 0;
/*  70:    */       }
/*  71:    */     }
/*  72: 96 */     return 5;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public static int getNumSteps(byte paramByte, int paramInt)
/*  76:    */   {
/*  77:100 */     if ((paramByte >= 50) && (paramByte < 56)) {
/*  78:101 */       return (int)AT_Constants.getInstance().API_STEP_MULTIPLIER(paramInt);
/*  79:    */     }
/*  80:103 */     return 1;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public static void resetMachine(AT_Machine_State paramAT_Machine_State)
/*  84:    */   {
/*  85:107 */     paramAT_Machine_State.getMachineState().reset();
/*  86:108 */     listCode(paramAT_Machine_State, true, true);
/*  87:    */   }
/*  88:    */   
/*  89:    */   public static void listCode(AT_Machine_State paramAT_Machine_State, boolean paramBoolean1, boolean paramBoolean2)
/*  90:    */   {
/*  91:113 */     AT_Machine_Processor localAT_Machine_Processor = new AT_Machine_Processor(paramAT_Machine_State);
/*  92:    */     
/*  93:115 */     int i = paramAT_Machine_State.getMachineState().pc;
/*  94:116 */     int j = paramAT_Machine_State.getMachineState().steps;
/*  95:    */     
/*  96:118 */     paramAT_Machine_State.getAp_code().order(ByteOrder.LITTLE_ENDIAN);
/*  97:119 */     paramAT_Machine_State.getAp_data().order(ByteOrder.LITTLE_ENDIAN);
/*  98:    */     
/*  99:121 */     paramAT_Machine_State.getMachineState().pc = 0;
/* 100:122 */     paramAT_Machine_State.getMachineState().opc = i;
/* 101:    */     for (;;)
/* 102:    */     {
/* 103:126 */       int k = localAT_Machine_Processor.processOp(paramBoolean1, paramBoolean2);
/* 104:127 */       if (k <= 0) {
/* 105:    */         break;
/* 106:    */       }
/* 107:129 */       paramAT_Machine_State.getMachineState().pc += k;
/* 108:    */     }
/* 109:132 */     paramAT_Machine_State.getMachineState().steps = j;
/* 110:133 */     paramAT_Machine_State.getMachineState().pc = i;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public static int checkCreationBytes(byte[] paramArrayOfByte, int paramInt)
/* 114:    */     throws AT_Exception
/* 115:    */   {
/* 116:137 */     if (paramArrayOfByte == null) {
/* 117:138 */       throw new AT_Exception("Creation bytes cannot be null");
/* 118:    */     }
/* 119:140 */     int i = 0;
/* 120:    */     try
/* 121:    */     {
/* 122:143 */       ByteBuffer localByteBuffer = ByteBuffer.allocate(paramArrayOfByte.length);
/* 123:144 */       localByteBuffer.order(ByteOrder.LITTLE_ENDIAN);
/* 124:    */       
/* 125:146 */       localByteBuffer.put(paramArrayOfByte);
/* 126:147 */       localByteBuffer.clear();
/* 127:    */       
/* 128:149 */       AT_Constants localAT_Constants = AT_Constants.getInstance();
/* 129:    */       
/* 130:151 */       int j = localByteBuffer.getShort();
/* 131:152 */       if (j != localAT_Constants.AT_VERSION(paramInt)) {
/* 132:154 */         throw new AT_Exception(AT_Error.INCORRECT_VERSION.getDescription());
/* 133:    */       }
/* 134:157 */       int k = localByteBuffer.getShort();
/* 135:    */       
/* 136:159 */       int m = localByteBuffer.getShort();
/* 137:160 */       if ((m > localAT_Constants.MAX_MACHINE_CODE_PAGES(paramInt)) || (m < 1)) {
/* 138:162 */         throw new AT_Exception(AT_Error.INCORRECT_CODE_PAGES.getDescription());
/* 139:    */       }
/* 140:165 */       int n = localByteBuffer.getShort();
/* 141:166 */       if ((n > localAT_Constants.MAX_MACHINE_DATA_PAGES(paramInt)) || (n < 0)) {
/* 142:168 */         throw new AT_Exception(AT_Error.INCORRECT_DATA_PAGES.getDescription());
/* 143:    */       }
/* 144:171 */       int i1 = localByteBuffer.getShort();
/* 145:172 */       if ((i1 > localAT_Constants.MAX_MACHINE_CALL_STACK_PAGES(paramInt)) || (i1 < 0)) {
/* 146:174 */         throw new AT_Exception(AT_Error.INCORRECT_CALL_PAGES.getDescription());
/* 147:    */       }
/* 148:177 */       int i2 = localByteBuffer.getShort();
/* 149:178 */       if ((i2 > localAT_Constants.MAX_MACHINE_USER_STACK_PAGES(paramInt)) || (i2 < 0)) {
/* 150:180 */         throw new AT_Exception(AT_Error.INCORRECT_USER_PAGES.getDescription());
/* 151:    */       }
/* 152:183 */       long l = localByteBuffer.getLong();
/* 153:    */       int i3;
/* 154:187 */       if (m * 256 < 257)
/* 155:    */       {
/* 156:189 */         i3 = localByteBuffer.get();
/* 157:190 */         if (i3 < 0) {
/* 158:191 */           i3 += 256;
/* 159:    */         }
/* 160:    */       }
/* 161:193 */       else if (m * 256 < 32768)
/* 162:    */       {
/* 163:195 */         i3 = localByteBuffer.getShort();
/* 164:196 */         if (i3 < 0) {
/* 165:197 */           i3 += 65536;
/* 166:    */         }
/* 167:    */       }
/* 168:199 */       else if (m * 256 <= Integer.MAX_VALUE)
/* 169:    */       {
/* 170:201 */         i3 = localByteBuffer.getInt();
/* 171:    */       }
/* 172:    */       else
/* 173:    */       {
/* 174:205 */         throw new AT_Exception(AT_Error.INCORRECT_CODE_LENGTH.getDescription());
/* 175:    */       }
/* 176:207 */       if ((i3 < 1) || (i3 > m * 256)) {
/* 177:209 */         throw new AT_Exception(AT_Error.INCORRECT_CODE_LENGTH.getDescription());
/* 178:    */       }
/* 179:211 */       byte[] arrayOfByte1 = new byte[i3];
/* 180:212 */       localByteBuffer.get(arrayOfByte1, 0, i3);
/* 181:    */       int i4;
/* 182:216 */       if (n * 256 < 257)
/* 183:    */       {
/* 184:218 */         i4 = localByteBuffer.get();
/* 185:219 */         if (i4 < 0) {
/* 186:220 */           i4 += 256;
/* 187:    */         }
/* 188:    */       }
/* 189:222 */       else if (n * 256 < 32768)
/* 190:    */       {
/* 191:224 */         i4 = localByteBuffer.getShort();
/* 192:225 */         if (i4 < 0) {
/* 193:226 */           i4 += 65536;
/* 194:    */         }
/* 195:    */       }
/* 196:228 */       else if (n * 256 <= Integer.MAX_VALUE)
/* 197:    */       {
/* 198:230 */         i4 = localByteBuffer.getInt();
/* 199:    */       }
/* 200:    */       else
/* 201:    */       {
/* 202:234 */         throw new AT_Exception(AT_Error.INCORRECT_CODE_LENGTH.getDescription());
/* 203:    */       }
/* 204:236 */       if ((i4 < 0) || (i4 > n * 256)) {
/* 205:238 */         throw new AT_Exception(AT_Error.INCORRECT_DATA_LENGTH.getDescription());
/* 206:    */       }
/* 207:240 */       byte[] arrayOfByte2 = new byte[i4];
/* 208:241 */       localByteBuffer.get(arrayOfByte2, 0, i4);
/* 209:    */       
/* 210:243 */       i = m + n + i2 + i1;
/* 211:249 */       if (localByteBuffer.position() != localByteBuffer.capacity()) {
/* 212:251 */         throw new AT_Exception(AT_Error.INCORRECT_CREATION_TX.getDescription());
/* 213:    */       }
/* 214:    */     }
/* 215:    */     catch (BufferUnderflowException localBufferUnderflowException)
/* 216:    */     {
/* 217:258 */       throw new AT_Exception(AT_Error.INCORRECT_CREATION_TX.getDescription());
/* 218:    */     }
/* 219:260 */     return i;
/* 220:    */   }
/* 221:    */   
/* 222:    */   public static AT_Block getCurrentBlockATs(int paramInt1, int paramInt2)
/* 223:    */   {
/* 224:265 */     List localList = AT.getOrderedATs();
/* 225:266 */     Iterator localIterator = localList.iterator();
/* 226:    */     
/* 227:268 */     ArrayList localArrayList = new ArrayList();
/* 228:    */     
/* 229:270 */     int i = getCostOfOneAT();
/* 230:271 */     int j = 0;
/* 231:272 */     long l1 = 0L;
/* 232:273 */     while ((j <= paramInt1 - i) && (localIterator.hasNext()))
/* 233:    */     {
/* 234:275 */       Long localLong = (Long)localIterator.next();
/* 235:276 */       AT localAT1 = AT.getAT(localLong);
/* 236:    */       
/* 237:278 */       long l3 = getATAccountBalance(localLong);
/* 238:279 */       long l4 = localAT1.getG_balance().longValue();
/* 239:281 */       if ((!localAT1.freezeOnSameBalance()) || (l3 - l4 >= localAT1.minActivationAmount())) {
/* 240:288 */         if (l3 >= AT_Constants.getInstance().STEP_FEE(localAT1.getCreationBlockHeight()) * AT_Constants.getInstance().API_STEP_MULTIPLIER(localAT1.getCreationBlockHeight())) {
/* 241:    */           try
/* 242:    */           {
/* 243:292 */             localAT1.setG_balance(Long.valueOf(l3));
/* 244:293 */             localAT1.setHeight(paramInt2);
/* 245:294 */             localAT1.clearTransactions();
/* 246:295 */             localAT1.setWaitForNumberOfBlocks(localAT1.getSleepBetween());
/* 247:296 */             listCode(localAT1, true, true);
/* 248:297 */             runSteps(localAT1);
/* 249:    */             
/* 250:299 */             long l5 = localAT1.getMachineState().steps * AT_Constants.getInstance().STEP_FEE(localAT1.getCreationBlockHeight());
/* 251:300 */             if (localAT1.getMachineState().dead)
/* 252:    */             {
/* 253:302 */               l5 += localAT1.getG_balance().longValue();
/* 254:303 */               localAT1.setG_balance(Long.valueOf(0L));
/* 255:    */             }
/* 256:305 */             l1 += l5;
/* 257:306 */             AT.addPendingFee(localLong.longValue(), l5);
/* 258:    */             
/* 259:308 */             j += i;
/* 260:    */             
/* 261:310 */             localAT1.setP_balance(localAT1.getG_balance());
/* 262:311 */             localArrayList.add(localAT1);
/* 263:    */           }
/* 264:    */           catch (Exception localException)
/* 265:    */           {
/* 266:317 */             localException.printStackTrace(System.out);
/* 267:    */           }
/* 268:    */         }
/* 269:    */       }
/* 270:    */     }
/* 271:322 */     long l2 = 0L;
/* 272:323 */     for (Object localObject = localArrayList.iterator(); ((Iterator)localObject).hasNext();)
/* 273:    */     {
/* 274:323 */       AT localAT2 = (AT)((Iterator)localObject).next();
/* 275:    */       
/* 276:325 */       l2 = makeTransactions(localAT2);
/* 277:    */     }
/* 278:328 */     localObject = null;
/* 279:    */     try
/* 280:    */     {
/* 281:332 */       localObject = getBlockATBytes(localArrayList, j);
/* 282:    */     }
/* 283:    */     catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
/* 284:    */     {
/* 285:337 */       localNoSuchAlgorithmException.printStackTrace();
/* 286:    */     }
/* 287:340 */     AT_Block localAT_Block = new AT_Block(l1, l2, (byte[])localObject);
/* 288:    */     
/* 289:342 */     return localAT_Block;
/* 290:    */   }
/* 291:    */   
/* 292:    */   public static AT_Block validateATs(byte[] paramArrayOfByte, int paramInt)
/* 293:    */     throws NoSuchAlgorithmException, AT_Exception
/* 294:    */   {
/* 295:347 */     if (paramArrayOfByte == null) {
/* 296:349 */       return new AT_Block(0L, 0L, null, true);
/* 297:    */     }
/* 298:352 */     LinkedHashMap localLinkedHashMap = getATsFromBlock(paramArrayOfByte);
/* 299:    */     
/* 300:354 */     ArrayList localArrayList = new ArrayList();
/* 301:    */     
/* 302:356 */     boolean bool = true;
/* 303:357 */     long l1 = 0L;
/* 304:358 */     MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
/* 305:359 */     byte[] arrayOfByte = null;
/* 306:360 */     for (ByteBuffer localByteBuffer : localLinkedHashMap.keySet())
/* 307:    */     {
/* 308:362 */       localObject = localByteBuffer.array();
/* 309:363 */       localAT = AT.getAT((byte[])localObject);
/* 310:    */       try
/* 311:    */       {
/* 312:367 */         localAT.clearTransactions();
/* 313:368 */         localAT.setHeight(paramInt);
/* 314:369 */         localAT.setWaitForNumberOfBlocks(localAT.getSleepBetween());
/* 315:    */         
/* 316:371 */         long l3 = getATAccountBalance(Long.valueOf(AT_API_Helper.getLong((byte[])localObject)));
/* 317:372 */         if (l3 < AT_Constants.getInstance().STEP_FEE(localAT.getCreationBlockHeight()) * AT_Constants.getInstance().API_STEP_MULTIPLIER(localAT.getCreationBlockHeight())) {
/* 318:374 */           throw new AT_Exception("AT has insufficient balance to run");
/* 319:    */         }
/* 320:377 */         if ((localAT.freezeOnSameBalance()) && (l3 - localAT.getG_balance().longValue() < localAT.minActivationAmount())) {
/* 321:379 */           throw new AT_Exception("AT should be frozen due to unchanged balance");
/* 322:    */         }
/* 323:382 */         if (localAT.nextHeight() > paramInt) {
/* 324:384 */           throw new AT_Exception("AT not allowed to run again yet");
/* 325:    */         }
/* 326:387 */         localAT.setG_balance(Long.valueOf(l3));
/* 327:    */         
/* 328:389 */         listCode(localAT, true, true);
/* 329:    */         
/* 330:391 */         runSteps(localAT);
/* 331:    */         
/* 332:393 */         long l4 = localAT.getMachineState().steps * AT_Constants.getInstance().STEP_FEE(localAT.getCreationBlockHeight());
/* 333:394 */         if (localAT.getMachineState().dead)
/* 334:    */         {
/* 335:396 */           l4 += localAT.getG_balance().longValue();
/* 336:397 */           localAT.setG_balance(Long.valueOf(0L));
/* 337:    */         }
/* 338:399 */         l1 += l4;
/* 339:400 */         AT.addPendingFee((byte[])localObject, l4);
/* 340:    */         
/* 341:402 */         localAT.setP_balance(localAT.getG_balance());
/* 342:403 */         localArrayList.add(localAT);
/* 343:    */         
/* 344:405 */         arrayOfByte = localMessageDigest.digest(localAT.getBytes());
/* 345:406 */         if (!Arrays.equals(arrayOfByte, (byte[])localLinkedHashMap.get(localByteBuffer))) {
/* 346:408 */           throw new AT_Exception("Calculated md5 and recieved md5 are not matching");
/* 347:    */         }
/* 348:    */       }
/* 349:    */       catch (Exception localException)
/* 350:    */       {
/* 351:414 */         throw new AT_Exception("ATs error. Block rejected");
/* 352:    */       }
/* 353:    */     }
/* 354:    */     AT localAT;
/* 355:418 */     long l2 = 0L;
/* 356:419 */     for (Object localObject = localArrayList.iterator(); ((Iterator)localObject).hasNext();)
/* 357:    */     {
/* 358:419 */       localAT = (AT)((Iterator)localObject).next();
/* 359:    */       
/* 360:421 */       localAT.saveState();
/* 361:422 */       l2 = makeTransactions(localAT);
/* 362:    */     }
/* 363:424 */     localObject = new AT_Block(l1, l2, new byte[1], bool);
/* 364:    */     
/* 365:426 */     return (AT_Block)localObject;
/* 366:    */   }
/* 367:    */   
/* 368:    */   public static LinkedHashMap<ByteBuffer, byte[]> getATsFromBlock(byte[] paramArrayOfByte)
/* 369:    */     throws AT_Exception
/* 370:    */   {
/* 371:431 */     if (paramArrayOfByte.length > 0) {
/* 372:433 */       if (paramArrayOfByte.length % getCostOfOneAT() != 0) {
/* 373:435 */         throw new AT_Exception("blockATs must be a multiple of cost of one AT ( " + getCostOfOneAT() + " )");
/* 374:    */       }
/* 375:    */     }
/* 376:439 */     ByteBuffer localByteBuffer1 = ByteBuffer.wrap(paramArrayOfByte);
/* 377:440 */     localByteBuffer1.order(ByteOrder.LITTLE_ENDIAN);
/* 378:    */     
/* 379:442 */     byte[] arrayOfByte1 = new byte[8];
/* 380:    */     
/* 381:444 */     LinkedHashMap localLinkedHashMap = new LinkedHashMap();
/* 382:446 */     while (localByteBuffer1.position() < localByteBuffer1.capacity())
/* 383:    */     {
/* 384:448 */       localByteBuffer1.get(arrayOfByte1, 0, arrayOfByte1.length);
/* 385:449 */       byte[] arrayOfByte2 = new byte[16];
/* 386:450 */       localByteBuffer1.get(arrayOfByte2, 0, arrayOfByte2.length);
/* 387:451 */       ByteBuffer localByteBuffer2 = ByteBuffer.allocate(8);
/* 388:452 */       localByteBuffer2.put(arrayOfByte1);
/* 389:453 */       localByteBuffer2.clear();
/* 390:454 */       if (localLinkedHashMap.containsKey(localByteBuffer2)) {
/* 391:455 */         throw new AT_Exception("AT included in block multiple times");
/* 392:    */       }
/* 393:457 */       localLinkedHashMap.put(localByteBuffer2, arrayOfByte2);
/* 394:    */     }
/* 395:460 */     if (localByteBuffer1.position() != localByteBuffer1.capacity()) {
/* 396:462 */       throw new AT_Exception("bytebuffer not matching");
/* 397:    */     }
/* 398:465 */     return localLinkedHashMap;
/* 399:    */   }
/* 400:    */   
/* 401:    */   private static byte[] getBlockATBytes(List<AT> paramList, int paramInt)
/* 402:    */     throws NoSuchAlgorithmException
/* 403:    */   {
/* 404:470 */     if (paramInt <= 0) {
/* 405:472 */       return null;
/* 406:    */     }
/* 407:475 */     ByteBuffer localByteBuffer = ByteBuffer.allocate(paramInt);
/* 408:476 */     localByteBuffer.order(ByteOrder.LITTLE_ENDIAN);
/* 409:    */     
/* 410:478 */     MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
/* 411:479 */     for (AT localAT : paramList)
/* 412:    */     {
/* 413:481 */       localByteBuffer.put(localAT.getId());
/* 414:482 */       localMessageDigest.update(localAT.getBytes());
/* 415:483 */       localByteBuffer.put(localMessageDigest.digest());
/* 416:    */     }
/* 417:486 */     return localByteBuffer.array();
/* 418:    */   }
/* 419:    */   
/* 420:    */   private static int getCostOfOneAT()
/* 421:    */   {
/* 422:490 */     return 24;
/* 423:    */   }
/* 424:    */   
/* 425:    */   private static long makeTransactions(AT paramAT)
/* 426:    */   {
/* 427:496 */     long l = 0L;
/* 428:497 */     for (AT_Transaction localAT_Transaction : paramAT.getTransactions())
/* 429:    */     {
/* 430:499 */       l += localAT_Transaction.getAmount().longValue();
/* 431:500 */       AT.addPendingTransaction(localAT_Transaction);
/* 432:501 */       Logger.logDebugMessage("Transaction to " + Convert.toUnsignedLong(AT_API_Helper.getLong(localAT_Transaction.getRecipientId())) + " amount " + localAT_Transaction.getAmount());
/* 433:    */     }
/* 434:504 */     return l;
/* 435:    */   }
/* 436:    */   
/* 437:    */   private static long getATAccountBalance(Long paramLong)
/* 438:    */   {
/* 439:514 */     Account localAccount = Account.getAccount(paramLong.longValue());
/* 440:516 */     if (localAccount != null) {
/* 441:518 */       return localAccount.getBalanceNQT();
/* 442:    */     }
/* 443:521 */     return 0L;
/* 444:    */   }
/* 445:    */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.at.AT_Controller
 * JD-Core Version:    0.7.1
 */