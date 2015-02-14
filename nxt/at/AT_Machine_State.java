/*   1:    */ package nxt.at;
/*   2:    */ 
/*   3:    */ import java.nio.ByteBuffer;
/*   4:    */ import java.nio.ByteOrder;
/*   5:    */ import java.util.Collection;
/*   6:    */ import java.util.LinkedHashMap;
/*   7:    */ import java.util.TreeSet;
/*   8:    */ 
/*   9:    */ public class AT_Machine_State
/*  10:    */ {
/*  11:    */   private short version;
/*  12:    */   private long g_balance;
/*  13:    */   private long p_balance;
/*  14:    */   private Machine_State machineState;
/*  15:    */   private int csize;
/*  16:    */   private int dsize;
/*  17:    */   private int c_user_stack_bytes;
/*  18:    */   private int c_call_stack_bytes;
/*  19:    */   
/*  20:    */   public class Machine_State
/*  21:    */   {
/*  22:    */     transient boolean running;
/*  23:    */     transient boolean stopped;
/*  24:    */     transient boolean finished;
/*  25:    */     transient boolean dead;
/*  26:    */     int pc;
/*  27:    */     int pcs;
/*  28:    */     transient int opc;
/*  29:    */     int cs;
/*  30:    */     int us;
/*  31:    */     int err;
/*  32:    */     int steps;
/*  33: 43 */     private byte[] A1 = new byte[8];
/*  34: 44 */     private byte[] A2 = new byte[8];
/*  35: 45 */     private byte[] A3 = new byte[8];
/*  36: 46 */     private byte[] A4 = new byte[8];
/*  37: 48 */     private byte[] B1 = new byte[8];
/*  38: 49 */     private byte[] B2 = new byte[8];
/*  39: 50 */     private byte[] B3 = new byte[8];
/*  40: 51 */     private byte[] B4 = new byte[8];
/*  41: 53 */     byte[] flags = new byte[2];
/*  42: 55 */     TreeSet<Integer> jumps = new TreeSet();
/*  43:    */     
/*  44:    */     Machine_State()
/*  45:    */     {
/*  46: 59 */       this.pcs = 0;
/*  47: 60 */       reset();
/*  48:    */     }
/*  49:    */     
/*  50:    */     public boolean isRunning()
/*  51:    */     {
/*  52: 65 */       return this.running;
/*  53:    */     }
/*  54:    */     
/*  55:    */     public boolean isStopped()
/*  56:    */     {
/*  57: 70 */       return this.stopped;
/*  58:    */     }
/*  59:    */     
/*  60:    */     public boolean isFinished()
/*  61:    */     {
/*  62: 75 */       return this.finished;
/*  63:    */     }
/*  64:    */     
/*  65:    */     public boolean isDead()
/*  66:    */     {
/*  67: 80 */       return this.dead;
/*  68:    */     }
/*  69:    */     
/*  70:    */     void reset()
/*  71:    */     {
/*  72: 85 */       this.pc = this.pcs;
/*  73: 86 */       this.opc = 0;
/*  74: 87 */       this.cs = 0;
/*  75: 88 */       this.us = 0;
/*  76: 89 */       this.err = -1;
/*  77: 90 */       this.steps = 0;
/*  78: 91 */       if (!this.jumps.isEmpty()) {
/*  79: 92 */         this.jumps.clear();
/*  80:    */       }
/*  81: 93 */       this.flags[0] = 0;
/*  82: 94 */       this.flags[1] = 0;
/*  83: 95 */       this.running = false;
/*  84: 96 */       this.stopped = true;
/*  85: 97 */       this.finished = false;
/*  86: 98 */       this.dead = false;
/*  87:    */     }
/*  88:    */     
/*  89:    */     protected byte[] getMachineStateBytes()
/*  90:    */     {
/*  91:109 */       ByteBuffer localByteBuffer = ByteBuffer.allocate(getSize());
/*  92:110 */       localByteBuffer.order(ByteOrder.LITTLE_ENDIAN);
/*  93:112 */       if (AT_Machine_State.this.getHeight() >= 67000)
/*  94:    */       {
/*  95:113 */         this.flags[0] = ((byte)((this.running ? 1 : 0) | (this.stopped ? 1 : 0) << 1 | (this.finished ? 1 : 0) << 2 | (this.dead ? 1 : 0) << 3));
/*  96:    */         
/*  97:    */ 
/*  98:    */ 
/*  99:117 */         this.flags[1] = 0;
/* 100:    */       }
/* 101:120 */       localByteBuffer.put(this.flags);
/* 102:    */       
/* 103:122 */       localByteBuffer.putInt(AT_Machine_State.this.machineState.pc);
/* 104:123 */       localByteBuffer.putInt(AT_Machine_State.this.machineState.pcs);
/* 105:124 */       localByteBuffer.putInt(AT_Machine_State.this.machineState.cs);
/* 106:125 */       localByteBuffer.putInt(AT_Machine_State.this.machineState.us);
/* 107:126 */       localByteBuffer.putInt(AT_Machine_State.this.machineState.err);
/* 108:    */       
/* 109:128 */       localByteBuffer.put(this.A1);
/* 110:129 */       localByteBuffer.put(this.A2);
/* 111:130 */       localByteBuffer.put(this.A3);
/* 112:131 */       localByteBuffer.put(this.A4);
/* 113:132 */       localByteBuffer.put(this.B1);
/* 114:133 */       localByteBuffer.put(this.B2);
/* 115:134 */       localByteBuffer.put(this.B3);
/* 116:135 */       localByteBuffer.put(this.B4);
/* 117:    */       
/* 118:    */ 
/* 119:138 */       return localByteBuffer.array();
/* 120:    */     }
/* 121:    */     
/* 122:    */     private void setMachineState(byte[] paramArrayOfByte)
/* 123:    */     {
/* 124:143 */       ByteBuffer localByteBuffer = ByteBuffer.allocate(getSize());
/* 125:144 */       localByteBuffer.order(ByteOrder.LITTLE_ENDIAN);
/* 126:145 */       localByteBuffer.put(paramArrayOfByte);
/* 127:146 */       localByteBuffer.flip();
/* 128:    */       
/* 129:148 */       localByteBuffer.get(this.flags, 0, 2);
/* 130:149 */       this.running = ((this.flags[0] & 0x1) == 1);
/* 131:150 */       this.stopped = ((this.flags[0] >>> 1 & 0x1) == 1);
/* 132:151 */       this.finished = ((this.flags[0] >>> 2 & 0x1) == 1);
/* 133:152 */       this.dead = ((this.flags[0] >>> 3 & 0x1) == 1);
/* 134:    */       
/* 135:154 */       this.pc = localByteBuffer.getInt();
/* 136:155 */       this.pcs = localByteBuffer.getInt();
/* 137:156 */       this.cs = localByteBuffer.getInt();
/* 138:157 */       this.us = localByteBuffer.getInt();
/* 139:158 */       this.err = localByteBuffer.getInt();
/* 140:159 */       localByteBuffer.get(this.A1, 0, 8);
/* 141:160 */       localByteBuffer.get(this.A2, 0, 8);
/* 142:161 */       localByteBuffer.get(this.A3, 0, 8);
/* 143:162 */       localByteBuffer.get(this.A4, 0, 8);
/* 144:163 */       localByteBuffer.get(this.B1, 0, 8);
/* 145:164 */       localByteBuffer.get(this.B2, 0, 8);
/* 146:165 */       localByteBuffer.get(this.B3, 0, 8);
/* 147:166 */       localByteBuffer.get(this.B4, 0, 8);
/* 148:    */     }
/* 149:    */     
/* 150:    */     public int getSize()
/* 151:    */     {
/* 152:171 */       return 86;
/* 153:    */     }
/* 154:    */     
/* 155:    */     public long getSteps()
/* 156:    */     {
/* 157:175 */       return this.steps;
/* 158:    */     }
/* 159:    */   }
/* 160:    */   
/* 161:191 */   private byte[] atID = new byte[8];
/* 162:192 */   private byte[] creator = new byte[8];
/* 163:    */   private int creationBlockHeight;
/* 164:    */   private int waitForNumberOfBlocks;
/* 165:    */   private int sleepBetween;
/* 166:    */   private boolean freezeWhenSameBalance;
/* 167:    */   private long minActivationAmount;
/* 168:    */   private transient ByteBuffer ap_data;
/* 169:    */   private transient ByteBuffer ap_code;
/* 170:    */   private int height;
/* 171:    */   private LinkedHashMap<ByteBuffer, AT_Transaction> transactions;
/* 172:    */   
/* 173:    */   public AT_Machine_State(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, short paramShort, byte[] paramArrayOfByte3, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, boolean paramBoolean, long paramLong, byte[] paramArrayOfByte4)
/* 174:    */   {
/* 175:218 */     this.atID = paramArrayOfByte1;
/* 176:219 */     this.creator = paramArrayOfByte2;
/* 177:220 */     this.version = paramShort;
/* 178:221 */     this.machineState = new Machine_State();
/* 179:222 */     setState(paramArrayOfByte3);
/* 180:223 */     this.csize = paramInt1;
/* 181:224 */     this.dsize = paramInt2;
/* 182:225 */     this.c_user_stack_bytes = paramInt3;
/* 183:226 */     this.c_call_stack_bytes = paramInt4;
/* 184:227 */     this.creationBlockHeight = paramInt5;
/* 185:228 */     this.sleepBetween = paramInt6;
/* 186:229 */     this.freezeWhenSameBalance = paramBoolean;
/* 187:230 */     this.minActivationAmount = paramLong;
/* 188:    */     
/* 189:232 */     this.ap_code = ByteBuffer.allocate(paramArrayOfByte4.length);
/* 190:233 */     this.ap_code.order(ByteOrder.LITTLE_ENDIAN);
/* 191:234 */     this.ap_code.put(paramArrayOfByte4);
/* 192:235 */     this.ap_code.clear();
/* 193:    */     
/* 194:237 */     this.transactions = new LinkedHashMap();
/* 195:    */   }
/* 196:    */   
/* 197:    */   public AT_Machine_State(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, byte[] paramArrayOfByte3, int paramInt)
/* 198:    */   {
/* 199:245 */     this.version = AT_Constants.getInstance().AT_VERSION(paramInt);
/* 200:246 */     this.atID = paramArrayOfByte1;
/* 201:247 */     this.creator = paramArrayOfByte2;
/* 202:    */     
/* 203:249 */     ByteBuffer localByteBuffer = ByteBuffer.allocate(paramArrayOfByte3.length);
/* 204:250 */     localByteBuffer.order(ByteOrder.LITTLE_ENDIAN);
/* 205:    */     
/* 206:252 */     localByteBuffer.put(paramArrayOfByte3);
/* 207:253 */     localByteBuffer.clear();
/* 208:    */     
/* 209:255 */     this.version = localByteBuffer.getShort();
/* 210:    */     
/* 211:257 */     localByteBuffer.getShort();
/* 212:    */     
/* 213:259 */     int i = (int)AT_Constants.getInstance().PAGE_SIZE(paramInt);
/* 214:260 */     int j = localByteBuffer.getShort();
/* 215:261 */     int k = localByteBuffer.getShort();
/* 216:262 */     int m = localByteBuffer.getShort();
/* 217:263 */     int n = localByteBuffer.getShort();
/* 218:    */     
/* 219:265 */     this.csize = (j * i);
/* 220:266 */     this.dsize = (k * i);
/* 221:267 */     this.c_call_stack_bytes = (m * i);
/* 222:268 */     this.c_user_stack_bytes = (n * i);
/* 223:    */     
/* 224:270 */     this.minActivationAmount = localByteBuffer.getLong();
/* 225:    */     
/* 226:272 */     int i1 = 0;
/* 227:273 */     if (j * i < i + 1)
/* 228:    */     {
/* 229:275 */       i1 = localByteBuffer.get();
/* 230:276 */       if (i1 < 0) {
/* 231:277 */         i1 += 256;
/* 232:    */       }
/* 233:    */     }
/* 234:279 */     else if (j * i < 32768)
/* 235:    */     {
/* 236:281 */       i1 = localByteBuffer.getShort();
/* 237:282 */       if (i1 < 0) {
/* 238:283 */         i1 += 65536;
/* 239:    */       }
/* 240:    */     }
/* 241:    */     else
/* 242:    */     {
/* 243:287 */       i1 = localByteBuffer.getInt();
/* 244:    */     }
/* 245:289 */     byte[] arrayOfByte1 = new byte[i1];
/* 246:290 */     localByteBuffer.get(arrayOfByte1, 0, i1);
/* 247:    */     
/* 248:292 */     this.ap_code = ByteBuffer.allocate(this.csize);
/* 249:293 */     this.ap_code.order(ByteOrder.LITTLE_ENDIAN);
/* 250:294 */     this.ap_code.put(arrayOfByte1);
/* 251:295 */     this.ap_code.clear();
/* 252:    */     
/* 253:297 */     int i2 = 0;
/* 254:298 */     if (k * i < 257)
/* 255:    */     {
/* 256:300 */       i2 = localByteBuffer.get();
/* 257:301 */       if (i2 < 0) {
/* 258:302 */         i2 += 256;
/* 259:    */       }
/* 260:    */     }
/* 261:304 */     else if (k * i < 32768)
/* 262:    */     {
/* 263:306 */       i2 = localByteBuffer.getShort();
/* 264:307 */       if (i2 < 0) {
/* 265:308 */         i2 += 65536;
/* 266:    */       }
/* 267:    */     }
/* 268:    */     else
/* 269:    */     {
/* 270:312 */       i2 = localByteBuffer.getInt();
/* 271:    */     }
/* 272:314 */     byte[] arrayOfByte2 = new byte[i2];
/* 273:315 */     localByteBuffer.get(arrayOfByte2, 0, i2);
/* 274:    */     
/* 275:317 */     this.ap_data = ByteBuffer.allocate(this.dsize + this.c_call_stack_bytes + this.c_user_stack_bytes);
/* 276:318 */     this.ap_data.order(ByteOrder.LITTLE_ENDIAN);
/* 277:319 */     this.ap_data.put(arrayOfByte2);
/* 278:320 */     this.ap_data.clear();
/* 279:    */     
/* 280:322 */     this.height = paramInt;
/* 281:323 */     this.creationBlockHeight = paramInt;
/* 282:324 */     this.waitForNumberOfBlocks = 0;
/* 283:325 */     this.sleepBetween = 0;
/* 284:326 */     this.freezeWhenSameBalance = false;
/* 285:327 */     this.transactions = new LinkedHashMap();
/* 286:328 */     this.g_balance = 0L;
/* 287:329 */     this.p_balance = 0L;
/* 288:330 */     this.machineState = new Machine_State();
/* 289:    */   }
/* 290:    */   
/* 291:    */   protected byte[] get_A1()
/* 292:    */   {
/* 293:335 */     return this.machineState.A1;
/* 294:    */   }
/* 295:    */   
/* 296:    */   protected byte[] get_A2()
/* 297:    */   {
/* 298:340 */     return this.machineState.A2;
/* 299:    */   }
/* 300:    */   
/* 301:    */   protected byte[] get_A3()
/* 302:    */   {
/* 303:345 */     return this.machineState.A3;
/* 304:    */   }
/* 305:    */   
/* 306:    */   protected byte[] get_A4()
/* 307:    */   {
/* 308:350 */     return this.machineState.A4;
/* 309:    */   }
/* 310:    */   
/* 311:    */   protected byte[] get_B1()
/* 312:    */   {
/* 313:355 */     return this.machineState.B1;
/* 314:    */   }
/* 315:    */   
/* 316:    */   protected byte[] get_B2()
/* 317:    */   {
/* 318:360 */     return this.machineState.B2;
/* 319:    */   }
/* 320:    */   
/* 321:    */   protected byte[] get_B3()
/* 322:    */   {
/* 323:365 */     return this.machineState.B3;
/* 324:    */   }
/* 325:    */   
/* 326:    */   protected byte[] get_B4()
/* 327:    */   {
/* 328:370 */     return this.machineState.B4;
/* 329:    */   }
/* 330:    */   
/* 331:    */   protected void set_A1(byte[] paramArrayOfByte)
/* 332:    */   {
/* 333:375 */     this.machineState.A1 = ((byte[])paramArrayOfByte.clone());
/* 334:    */   }
/* 335:    */   
/* 336:    */   protected void set_A2(byte[] paramArrayOfByte)
/* 337:    */   {
/* 338:379 */     this.machineState.A2 = ((byte[])paramArrayOfByte.clone());
/* 339:    */   }
/* 340:    */   
/* 341:    */   protected void set_A3(byte[] paramArrayOfByte)
/* 342:    */   {
/* 343:384 */     this.machineState.A3 = ((byte[])paramArrayOfByte.clone());
/* 344:    */   }
/* 345:    */   
/* 346:    */   protected void set_A4(byte[] paramArrayOfByte)
/* 347:    */   {
/* 348:389 */     this.machineState.A4 = ((byte[])paramArrayOfByte.clone());
/* 349:    */   }
/* 350:    */   
/* 351:    */   protected void set_B1(byte[] paramArrayOfByte)
/* 352:    */   {
/* 353:394 */     this.machineState.B1 = ((byte[])paramArrayOfByte.clone());
/* 354:    */   }
/* 355:    */   
/* 356:    */   protected void set_B2(byte[] paramArrayOfByte)
/* 357:    */   {
/* 358:399 */     this.machineState.B2 = ((byte[])paramArrayOfByte.clone());
/* 359:    */   }
/* 360:    */   
/* 361:    */   protected void set_B3(byte[] paramArrayOfByte)
/* 362:    */   {
/* 363:404 */     this.machineState.B3 = ((byte[])paramArrayOfByte.clone());
/* 364:    */   }
/* 365:    */   
/* 366:    */   protected void set_B4(byte[] paramArrayOfByte)
/* 367:    */   {
/* 368:409 */     this.machineState.B4 = ((byte[])paramArrayOfByte.clone());
/* 369:    */   }
/* 370:    */   
/* 371:    */   protected void addTransaction(AT_Transaction paramAT_Transaction)
/* 372:    */   {
/* 373:414 */     ByteBuffer localByteBuffer = ByteBuffer.wrap(paramAT_Transaction.getRecipientId());
/* 374:415 */     AT_Transaction localAT_Transaction1 = (AT_Transaction)this.transactions.get(localByteBuffer);
/* 375:416 */     if (localAT_Transaction1 == null)
/* 376:    */     {
/* 377:418 */       this.transactions.put(localByteBuffer, paramAT_Transaction);
/* 378:    */     }
/* 379:    */     else
/* 380:    */     {
/* 381:422 */       AT_Transaction localAT_Transaction2 = new AT_Transaction(paramAT_Transaction.getSenderId(), paramAT_Transaction.getRecipientId(), localAT_Transaction1.getAmount().longValue() + paramAT_Transaction.getAmount().longValue(), paramAT_Transaction.getMessage() != null ? paramAT_Transaction.getMessage() : localAT_Transaction1.getMessage());
/* 382:    */       
/* 383:    */ 
/* 384:    */ 
/* 385:426 */       this.transactions.put(localByteBuffer, localAT_Transaction2);
/* 386:    */     }
/* 387:    */   }
/* 388:    */   
/* 389:    */   protected void clearTransactions()
/* 390:    */   {
/* 391:432 */     this.transactions.clear();
/* 392:    */   }
/* 393:    */   
/* 394:    */   public Collection<AT_Transaction> getTransactions()
/* 395:    */   {
/* 396:437 */     return this.transactions.values();
/* 397:    */   }
/* 398:    */   
/* 399:    */   protected ByteBuffer getAp_code()
/* 400:    */   {
/* 401:442 */     return this.ap_code;
/* 402:    */   }
/* 403:    */   
/* 404:    */   public ByteBuffer getAp_data()
/* 405:    */   {
/* 406:447 */     return this.ap_data;
/* 407:    */   }
/* 408:    */   
/* 409:    */   protected int getC_call_stack_bytes()
/* 410:    */   {
/* 411:452 */     return this.c_call_stack_bytes;
/* 412:    */   }
/* 413:    */   
/* 414:    */   protected int getC_user_stack_bytes()
/* 415:    */   {
/* 416:457 */     return this.c_user_stack_bytes;
/* 417:    */   }
/* 418:    */   
/* 419:    */   protected int getCsize()
/* 420:    */   {
/* 421:462 */     return this.csize;
/* 422:    */   }
/* 423:    */   
/* 424:    */   protected int getDsize()
/* 425:    */   {
/* 426:467 */     return this.dsize;
/* 427:    */   }
/* 428:    */   
/* 429:    */   public Long getG_balance()
/* 430:    */   {
/* 431:472 */     return Long.valueOf(this.g_balance);
/* 432:    */   }
/* 433:    */   
/* 434:    */   public Long getP_balance()
/* 435:    */   {
/* 436:477 */     return Long.valueOf(this.p_balance);
/* 437:    */   }
/* 438:    */   
/* 439:    */   public byte[] getId()
/* 440:    */   {
/* 441:482 */     return this.atID;
/* 442:    */   }
/* 443:    */   
/* 444:    */   public Machine_State getMachineState()
/* 445:    */   {
/* 446:487 */     return this.machineState;
/* 447:    */   }
/* 448:    */   
/* 449:    */   protected void setC_call_stack_bytes(int paramInt)
/* 450:    */   {
/* 451:492 */     this.c_call_stack_bytes = paramInt;
/* 452:    */   }
/* 453:    */   
/* 454:    */   protected void setC_user_stack_bytes(int paramInt)
/* 455:    */   {
/* 456:497 */     this.c_user_stack_bytes = paramInt;
/* 457:    */   }
/* 458:    */   
/* 459:    */   protected void setCsize(int paramInt)
/* 460:    */   {
/* 461:502 */     this.csize = paramInt;
/* 462:    */   }
/* 463:    */   
/* 464:    */   protected void setDsize(int paramInt)
/* 465:    */   {
/* 466:507 */     this.dsize = paramInt;
/* 467:    */   }
/* 468:    */   
/* 469:    */   public void setG_balance(Long paramLong)
/* 470:    */   {
/* 471:512 */     this.g_balance = paramLong.longValue();
/* 472:    */   }
/* 473:    */   
/* 474:    */   public void setP_balance(Long paramLong)
/* 475:    */   {
/* 476:517 */     this.p_balance = paramLong.longValue();
/* 477:    */   }
/* 478:    */   
/* 479:    */   public void setMachineState(Machine_State paramMachine_State)
/* 480:    */   {
/* 481:522 */     this.machineState = paramMachine_State;
/* 482:    */   }
/* 483:    */   
/* 484:    */   public void setWaitForNumberOfBlocks(int paramInt)
/* 485:    */   {
/* 486:527 */     this.waitForNumberOfBlocks = paramInt;
/* 487:    */   }
/* 488:    */   
/* 489:    */   public int getWaitForNumberOfBlocks()
/* 490:    */   {
/* 491:532 */     return this.waitForNumberOfBlocks;
/* 492:    */   }
/* 493:    */   
/* 494:    */   public byte[] getCreator()
/* 495:    */   {
/* 496:537 */     return this.creator;
/* 497:    */   }
/* 498:    */   
/* 499:    */   public int getCreationBlockHeight()
/* 500:    */   {
/* 501:542 */     return this.creationBlockHeight;
/* 502:    */   }
/* 503:    */   
/* 504:    */   public boolean freezeOnSameBalance()
/* 505:    */   {
/* 506:547 */     return this.freezeWhenSameBalance;
/* 507:    */   }
/* 508:    */   
/* 509:    */   public long minActivationAmount()
/* 510:    */   {
/* 511:552 */     return this.minActivationAmount;
/* 512:    */   }
/* 513:    */   
/* 514:    */   public void setMinActivationAmount(long paramLong)
/* 515:    */   {
/* 516:557 */     this.minActivationAmount = paramLong;
/* 517:    */   }
/* 518:    */   
/* 519:    */   public short getVersion()
/* 520:    */   {
/* 521:562 */     return this.version;
/* 522:    */   }
/* 523:    */   
/* 524:    */   public int getSleepBetween()
/* 525:    */   {
/* 526:567 */     return this.sleepBetween;
/* 527:    */   }
/* 528:    */   
/* 529:    */   public int getHeight()
/* 530:    */   {
/* 531:572 */     return this.height;
/* 532:    */   }
/* 533:    */   
/* 534:    */   public void setHeight(int paramInt)
/* 535:    */   {
/* 536:577 */     this.height = paramInt;
/* 537:    */   }
/* 538:    */   
/* 539:    */   public byte[] getTransactionBytes()
/* 540:    */   {
/* 541:582 */     ByteBuffer localByteBuffer = ByteBuffer.allocate((this.creator.length + 8) * this.transactions.size());
/* 542:583 */     localByteBuffer.order(ByteOrder.LITTLE_ENDIAN);
/* 543:584 */     for (AT_Transaction localAT_Transaction : this.transactions.values())
/* 544:    */     {
/* 545:586 */       localByteBuffer.put(localAT_Transaction.getRecipientId());
/* 546:587 */       localByteBuffer.putLong(localAT_Transaction.getAmount().longValue());
/* 547:    */     }
/* 548:589 */     return localByteBuffer.array();
/* 549:    */   }
/* 550:    */   
/* 551:    */   public byte[] getState()
/* 552:    */   {
/* 553:595 */     byte[] arrayOfByte1 = this.machineState.getMachineStateBytes();
/* 554:596 */     byte[] arrayOfByte2 = this.ap_data.array();
/* 555:    */     
/* 556:    */ 
/* 557:    */ 
/* 558:600 */     ByteBuffer localByteBuffer = ByteBuffer.allocate(getStateSize());
/* 559:601 */     localByteBuffer.order(ByteOrder.LITTLE_ENDIAN);
/* 560:    */     
/* 561:603 */     localByteBuffer.put(arrayOfByte1);
/* 562:604 */     localByteBuffer.putLong(this.g_balance);
/* 563:605 */     localByteBuffer.putLong(this.p_balance);
/* 564:606 */     localByteBuffer.putInt(this.waitForNumberOfBlocks);
/* 565:607 */     localByteBuffer.put(arrayOfByte2);
/* 566:    */     
/* 567:609 */     return localByteBuffer.array();
/* 568:    */   }
/* 569:    */   
/* 570:    */   public void setState(byte[] paramArrayOfByte)
/* 571:    */   {
/* 572:614 */     ByteBuffer localByteBuffer = ByteBuffer.allocate(paramArrayOfByte.length);
/* 573:615 */     localByteBuffer.order(ByteOrder.LITTLE_ENDIAN);
/* 574:616 */     localByteBuffer.put(paramArrayOfByte);
/* 575:617 */     localByteBuffer.flip();
/* 576:    */     
/* 577:619 */     int i = this.machineState.getSize();
/* 578:620 */     byte[] arrayOfByte1 = new byte[i];
/* 579:621 */     localByteBuffer.get(arrayOfByte1, 0, i);
/* 580:622 */     this.machineState.setMachineState(arrayOfByte1);
/* 581:    */     
/* 582:624 */     this.g_balance = localByteBuffer.getLong();
/* 583:625 */     this.p_balance = localByteBuffer.getLong();
/* 584:626 */     this.waitForNumberOfBlocks = localByteBuffer.getInt();
/* 585:    */     
/* 586:628 */     byte[] arrayOfByte2 = new byte[localByteBuffer.capacity() - localByteBuffer.position()];
/* 587:629 */     localByteBuffer.get(arrayOfByte2);
/* 588:630 */     this.ap_data = ByteBuffer.allocate(arrayOfByte2.length);
/* 589:631 */     this.ap_data.order(ByteOrder.LITTLE_ENDIAN);
/* 590:632 */     this.ap_data.put(arrayOfByte2);
/* 591:633 */     this.ap_data.clear();
/* 592:    */   }
/* 593:    */   
/* 594:    */   protected int getStateSize()
/* 595:    */   {
/* 596:638 */     return this.machineState.getSize() + 8 + 8 + 4 + this.ap_data.capacity();
/* 597:    */   }
/* 598:    */   
/* 599:    */   public byte[] getBytes()
/* 600:    */   {
/* 601:644 */     byte[] arrayOfByte1 = getTransactionBytes();
/* 602:645 */     byte[] arrayOfByte2 = this.machineState.getMachineStateBytes();
/* 603:646 */     byte[] arrayOfByte3 = this.ap_data.array();
/* 604:    */     
/* 605:648 */     ByteBuffer localByteBuffer = ByteBuffer.allocate(this.atID.length + arrayOfByte1.length + arrayOfByte2.length + arrayOfByte3.length);
/* 606:649 */     localByteBuffer.order(ByteOrder.LITTLE_ENDIAN);
/* 607:    */     
/* 608:651 */     localByteBuffer.put(this.atID);
/* 609:652 */     localByteBuffer.put(arrayOfByte2);
/* 610:653 */     localByteBuffer.put(arrayOfByte3);
/* 611:654 */     localByteBuffer.put(arrayOfByte1);
/* 612:    */     
/* 613:656 */     return localByteBuffer.array();
/* 614:    */   }
/* 615:    */   
/* 616:    */   public void setFreeze(boolean paramBoolean)
/* 617:    */   {
/* 618:661 */     this.freezeWhenSameBalance = paramBoolean;
/* 619:    */   }
/* 620:    */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.at.AT_Machine_State
 * JD-Core Version:    0.7.1
 */