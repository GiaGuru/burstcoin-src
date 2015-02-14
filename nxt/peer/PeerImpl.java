/*   1:    */ package nxt.peer;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.net.MalformedURLException;
/*   5:    */ import java.net.URL;
/*   6:    */ import java.util.Set;
/*   7:    */ import java.util.concurrent.ConcurrentHashMap;
/*   8:    */ import java.util.concurrent.ConcurrentMap;
/*   9:    */ import nxt.Account;
/*  10:    */ import nxt.Block;
/*  11:    */ import nxt.BlockchainProcessor;
/*  12:    */ import nxt.BlockchainProcessor.BlockOutOfOrderException;
/*  13:    */ import nxt.BlockchainProcessor.Event;
/*  14:    */ import nxt.Nxt;
/*  15:    */ import nxt.NxtException.NotCurrentlyValidException;
/*  16:    */ import nxt.util.Convert;
/*  17:    */ import nxt.util.Listener;
/*  18:    */ import nxt.util.Logger;
/*  19:    */ import org.json.simple.JSONObject;
/*  20:    */ 
/*  21:    */ final class PeerImpl
/*  22:    */   implements Peer
/*  23:    */ {
/*  24: 45 */   private static final ConcurrentMap<Long, Long> hallmarkBalances = new ConcurrentHashMap();
/*  25:    */   private final String peerAddress;
/*  26:    */   private volatile String announcedAddress;
/*  27:    */   private volatile int port;
/*  28:    */   private volatile boolean shareAddress;
/*  29:    */   private volatile Hallmark hallmark;
/*  30:    */   private volatile String platform;
/*  31:    */   private volatile String application;
/*  32:    */   private volatile String version;
/*  33:    */   private volatile long adjustedWeight;
/*  34:    */   private volatile long blacklistingTime;
/*  35:    */   private volatile Peer.State state;
/*  36:    */   private volatile long downloadedVolume;
/*  37:    */   private volatile long uploadedVolume;
/*  38:    */   private volatile int lastUpdated;
/*  39:    */   
/*  40:    */   static
/*  41:    */   {
/*  42: 48 */     Nxt.getBlockchainProcessor().addListener(new Listener()
/*  43:    */     {
/*  44:    */       public void notify(Block paramAnonymousBlock)
/*  45:    */       {
/*  46: 51 */         PeerImpl.hallmarkBalances.clear();
/*  47:    */       }
/*  48: 51 */     }, BlockchainProcessor.Event.AFTER_BLOCK_APPLY);
/*  49:    */   }
/*  50:    */   
/*  51:    */   PeerImpl(String paramString1, String paramString2)
/*  52:    */   {
/*  53: 72 */     this.peerAddress = paramString1;
/*  54: 73 */     this.announcedAddress = paramString2;
/*  55:    */     try
/*  56:    */     {
/*  57: 75 */       this.port = new URL("http://" + paramString2).getPort();
/*  58:    */     }
/*  59:    */     catch (MalformedURLException localMalformedURLException) {}
/*  60: 77 */     this.state = Peer.State.NON_CONNECTED;
/*  61: 78 */     this.shareAddress = true;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public String getPeerAddress()
/*  65:    */   {
/*  66: 83 */     return this.peerAddress;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public Peer.State getState()
/*  70:    */   {
/*  71: 88 */     return this.state;
/*  72:    */   }
/*  73:    */   
/*  74:    */   void setState(Peer.State paramState)
/*  75:    */   {
/*  76: 92 */     if (this.state == paramState) {
/*  77: 93 */       return;
/*  78:    */     }
/*  79: 95 */     if (this.state == Peer.State.NON_CONNECTED)
/*  80:    */     {
/*  81: 96 */       this.state = paramState;
/*  82: 97 */       Peers.notifyListeners(this, Peers.Event.ADDED_ACTIVE_PEER);
/*  83:    */     }
/*  84: 98 */     else if (paramState != Peer.State.NON_CONNECTED)
/*  85:    */     {
/*  86: 99 */       this.state = paramState;
/*  87:100 */       Peers.notifyListeners(this, Peers.Event.CHANGED_ACTIVE_PEER);
/*  88:    */     }
/*  89:    */   }
/*  90:    */   
/*  91:    */   public long getDownloadedVolume()
/*  92:    */   {
/*  93:106 */     return this.downloadedVolume;
/*  94:    */   }
/*  95:    */   
/*  96:    */   void updateDownloadedVolume(long paramLong)
/*  97:    */   {
/*  98:110 */     synchronized (this)
/*  99:    */     {
/* 100:111 */       this.downloadedVolume += paramLong;
/* 101:    */     }
/* 102:113 */     Peers.notifyListeners(this, Peers.Event.DOWNLOADED_VOLUME);
/* 103:    */   }
/* 104:    */   
/* 105:    */   public long getUploadedVolume()
/* 106:    */   {
/* 107:118 */     return this.uploadedVolume;
/* 108:    */   }
/* 109:    */   
/* 110:    */   void updateUploadedVolume(long paramLong)
/* 111:    */   {
/* 112:122 */     synchronized (this)
/* 113:    */     {
/* 114:123 */       this.uploadedVolume += paramLong;
/* 115:    */     }
/* 116:125 */     Peers.notifyListeners(this, Peers.Event.UPLOADED_VOLUME);
/* 117:    */   }
/* 118:    */   
/* 119:    */   public String getVersion()
/* 120:    */   {
/* 121:130 */     return this.version;
/* 122:    */   }
/* 123:    */   
/* 124:    */   void setVersion(String paramString)
/* 125:    */   {
/* 126:134 */     this.version = paramString;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public String getApplication()
/* 130:    */   {
/* 131:139 */     return this.application;
/* 132:    */   }
/* 133:    */   
/* 134:    */   void setApplication(String paramString)
/* 135:    */   {
/* 136:143 */     this.application = paramString;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public String getPlatform()
/* 140:    */   {
/* 141:148 */     return this.platform;
/* 142:    */   }
/* 143:    */   
/* 144:    */   void setPlatform(String paramString)
/* 145:    */   {
/* 146:152 */     this.platform = paramString;
/* 147:    */   }
/* 148:    */   
/* 149:    */   public String getSoftware()
/* 150:    */   {
/* 151:157 */     return Convert.truncate(this.application, "?", 10, false) + " (" + Convert.truncate(this.version, "?", 10, false) + ")" + " @ " + Convert.truncate(this.platform, "?", 10, false);
/* 152:    */   }
/* 153:    */   
/* 154:    */   public boolean shareAddress()
/* 155:    */   {
/* 156:164 */     return this.shareAddress;
/* 157:    */   }
/* 158:    */   
/* 159:    */   void setShareAddress(boolean paramBoolean)
/* 160:    */   {
/* 161:168 */     this.shareAddress = paramBoolean;
/* 162:    */   }
/* 163:    */   
/* 164:    */   public String getAnnouncedAddress()
/* 165:    */   {
/* 166:173 */     return this.announcedAddress;
/* 167:    */   }
/* 168:    */   
/* 169:    */   void setAnnouncedAddress(String paramString)
/* 170:    */   {
/* 171:177 */     String str = Peers.normalizeHostAndPort(paramString);
/* 172:178 */     if (str != null)
/* 173:    */     {
/* 174:179 */       this.announcedAddress = str;
/* 175:    */       try
/* 176:    */       {
/* 177:181 */         this.port = new URL("http://" + str).getPort();
/* 178:    */       }
/* 179:    */       catch (MalformedURLException localMalformedURLException) {}
/* 180:    */     }
/* 181:    */   }
/* 182:    */   
/* 183:    */   int getPort()
/* 184:    */   {
/* 185:187 */     return this.port;
/* 186:    */   }
/* 187:    */   
/* 188:    */   public boolean isWellKnown()
/* 189:    */   {
/* 190:192 */     return (this.announcedAddress != null) && (Peers.wellKnownPeers.contains(this.announcedAddress));
/* 191:    */   }
/* 192:    */   
/* 193:    */   public Hallmark getHallmark()
/* 194:    */   {
/* 195:197 */     return this.hallmark;
/* 196:    */   }
/* 197:    */   
/* 198:    */   public int getWeight()
/* 199:    */   {
/* 200:202 */     if (this.hallmark == null) {
/* 201:203 */       return 0;
/* 202:    */     }
/* 203:205 */     long l = this.hallmark.getAccountId();
/* 204:206 */     Long localLong = (Long)hallmarkBalances.get(Long.valueOf(l));
/* 205:207 */     if (localLong == null)
/* 206:    */     {
/* 207:208 */       Account localAccount = Account.getAccount(l);
/* 208:209 */       localLong = Long.valueOf(localAccount == null ? 0L : localAccount.getBalanceNQT());
/* 209:210 */       hallmarkBalances.put(Long.valueOf(l), localLong);
/* 210:    */     }
/* 211:212 */     return (int)(this.adjustedWeight * (localLong.longValue() / 100000000L) / 2158812800L);
/* 212:    */   }
/* 213:    */   
/* 214:    */   public boolean isBlacklisted()
/* 215:    */   {
/* 216:217 */     return (this.blacklistingTime > 0L) || (Peers.knownBlacklistedPeers.contains(this.peerAddress));
/* 217:    */   }
/* 218:    */   
/* 219:    */   public void blacklist(Exception paramException)
/* 220:    */   {
/* 221:222 */     if (((paramException instanceof NxtException.NotCurrentlyValidException)) || ((paramException instanceof BlockchainProcessor.BlockOutOfOrderException))) {
/* 222:225 */       return;
/* 223:    */     }
/* 224:227 */     if ((!isBlacklisted()) && (!(paramException instanceof IOException))) {
/* 225:228 */       Logger.logDebugMessage("Blacklisting " + this.peerAddress + " because of: " + paramException.toString(), paramException);
/* 226:    */     }
/* 227:230 */     blacklist();
/* 228:    */   }
/* 229:    */   
/* 230:    */   public void blacklist()
/* 231:    */   {
/* 232:235 */     this.blacklistingTime = System.currentTimeMillis();
/* 233:236 */     setState(Peer.State.NON_CONNECTED);
/* 234:237 */     Peers.notifyListeners(this, Peers.Event.BLACKLIST);
/* 235:    */   }
/* 236:    */   
/* 237:    */   public void unBlacklist()
/* 238:    */   {
/* 239:242 */     setState(Peer.State.NON_CONNECTED);
/* 240:243 */     this.blacklistingTime = 0L;
/* 241:244 */     Peers.notifyListeners(this, Peers.Event.UNBLACKLIST);
/* 242:    */   }
/* 243:    */   
/* 244:    */   void updateBlacklistedStatus(long paramLong)
/* 245:    */   {
/* 246:248 */     if ((this.blacklistingTime > 0L) && (this.blacklistingTime + Peers.blacklistingPeriod <= paramLong)) {
/* 247:249 */       unBlacklist();
/* 248:    */     }
/* 249:    */   }
/* 250:    */   
/* 251:    */   public void deactivate()
/* 252:    */   {
/* 253:255 */     setState(Peer.State.NON_CONNECTED);
/* 254:256 */     Peers.notifyListeners(this, Peers.Event.DEACTIVATE);
/* 255:    */   }
/* 256:    */   
/* 257:    */   public void remove()
/* 258:    */   {
/* 259:261 */     Peers.removePeer(this);
/* 260:262 */     Peers.notifyListeners(this, Peers.Event.REMOVE);
/* 261:    */   }
/* 262:    */   
/* 263:    */   public int getLastUpdated()
/* 264:    */   {
/* 265:267 */     return this.lastUpdated;
/* 266:    */   }
/* 267:    */   
/* 268:    */   void setLastUpdated(int paramInt)
/* 269:    */   {
/* 270:271 */     this.lastUpdated = paramInt;
/* 271:    */   }
/* 272:    */   
/* 273:    */   public int compareTo(Peer paramPeer)
/* 274:    */   {
/* 275:385 */     if (getWeight() > paramPeer.getWeight()) {
/* 276:386 */       return -1;
/* 277:    */     }
/* 278:387 */     if (getWeight() < paramPeer.getWeight()) {
/* 279:388 */       return 1;
/* 280:    */     }
/* 281:390 */     return 0;
/* 282:    */   }
/* 283:    */   
/* 284:    */   void connect()
/* 285:    */   {
/* 286:394 */     JSONObject localJSONObject = send(Peers.myPeerInfoRequest);
/* 287:395 */     if (localJSONObject != null)
/* 288:    */     {
/* 289:396 */       this.application = ((String)localJSONObject.get("application"));
/* 290:397 */       this.version = ((String)localJSONObject.get("version"));
/* 291:398 */       this.platform = ((String)localJSONObject.get("platform"));
/* 292:399 */       this.shareAddress = Boolean.TRUE.equals(localJSONObject.get("shareAddress"));
/* 293:400 */       String str = Convert.emptyToNull((String)localJSONObject.get("announcedAddress"));
/* 294:401 */       if ((str != null) && (!str.equals(this.announcedAddress)))
/* 295:    */       {
/* 296:403 */         setState(Peer.State.NON_CONNECTED);
/* 297:404 */         setAnnouncedAddress(str);
/* 298:405 */         return;
/* 299:    */       }
/* 300:407 */       if (this.announcedAddress == null) {
/* 301:408 */         setAnnouncedAddress(this.peerAddress);
/* 302:    */       }
/* 303:411 */       if (analyzeHallmark(this.announcedAddress, (String)localJSONObject.get("hallmark")))
/* 304:    */       {
/* 305:412 */         setState(Peer.State.CONNECTED);
/* 306:413 */         Peers.updateAddress(this);
/* 307:    */       }
/* 308:    */       else
/* 309:    */       {
/* 310:415 */         blacklist();
/* 311:    */       }
/* 312:417 */       this.lastUpdated = Nxt.getEpochTime();
/* 313:    */     }
/* 314:    */     else
/* 315:    */     {
/* 316:419 */       setState(Peer.State.NON_CONNECTED);
/* 317:    */     }
/* 318:    */   }
/* 319:    */   
/* 320:    */   private int getHallmarkWeight(int paramInt)
/* 321:    */   {
/* 322:484 */     if ((this.hallmark == null) || (!this.hallmark.isValid()) || (this.hallmark.getDate() != paramInt)) {
/* 323:485 */       return 0;
/* 324:    */     }
/* 325:487 */     return this.hallmark.getWeight();
/* 326:    */   }
/* 327:    */   
/* 328:    */   /* Error */
/* 329:    */   public JSONObject send(org.json.simple.JSONStreamAware paramJSONStreamAware)
/* 330:    */   {
/* 331:    */     // Byte code:
/* 332:    */     //   0: aconst_null
/* 333:    */     //   1: astore_3
/* 334:    */     //   2: iconst_0
/* 335:    */     //   3: istore 4
/* 336:    */     //   5: aconst_null
/* 337:    */     //   6: astore 5
/* 338:    */     //   8: aload_0
/* 339:    */     //   9: getfield 4	nxt/peer/PeerImpl:announcedAddress	Ljava/lang/String;
/* 340:    */     //   12: ifnull +10 -> 22
/* 341:    */     //   15: aload_0
/* 342:    */     //   16: getfield 4	nxt/peer/PeerImpl:announcedAddress	Ljava/lang/String;
/* 343:    */     //   19: goto +7 -> 26
/* 344:    */     //   22: aload_0
/* 345:    */     //   23: getfield 3	nxt/peer/PeerImpl:peerAddress	Ljava/lang/String;
/* 346:    */     //   26: astore 6
/* 347:    */     //   28: new 6	java/lang/StringBuilder
/* 348:    */     //   31: dup
/* 349:    */     //   32: ldc 8
/* 350:    */     //   34: invokespecial 71	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
/* 351:    */     //   37: astore 7
/* 352:    */     //   39: aload 7
/* 353:    */     //   41: aload 6
/* 354:    */     //   43: invokevirtual 9	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 355:    */     //   46: pop
/* 356:    */     //   47: aload_0
/* 357:    */     //   48: getfield 13	nxt/peer/PeerImpl:port	I
/* 358:    */     //   51: ifgt +32 -> 83
/* 359:    */     //   54: aload 7
/* 360:    */     //   56: bipush 58
/* 361:    */     //   58: invokevirtual 72	java/lang/StringBuilder:append	(C)Ljava/lang/StringBuilder;
/* 362:    */     //   61: pop
/* 363:    */     //   62: aload 7
/* 364:    */     //   64: getstatic 73	nxt/Constants:isTestnet	Z
/* 365:    */     //   67: ifeq +9 -> 76
/* 366:    */     //   70: sipush 7123
/* 367:    */     //   73: goto +6 -> 79
/* 368:    */     //   76: sipush 8123
/* 369:    */     //   79: invokevirtual 74	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
/* 370:    */     //   82: pop
/* 371:    */     //   83: aload 7
/* 372:    */     //   85: ldc 75
/* 373:    */     //   87: invokevirtual 9	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 374:    */     //   90: pop
/* 375:    */     //   91: new 5	java/net/URL
/* 376:    */     //   94: dup
/* 377:    */     //   95: aload 7
/* 378:    */     //   97: invokevirtual 10	java/lang/StringBuilder:toString	()Ljava/lang/String;
/* 379:    */     //   100: invokespecial 11	java/net/URL:<init>	(Ljava/lang/String;)V
/* 380:    */     //   103: astore 8
/* 381:    */     //   105: getstatic 76	nxt/peer/Peers:communicationLoggingMask	I
/* 382:    */     //   108: ifeq +57 -> 165
/* 383:    */     //   111: new 77	java/io/StringWriter
/* 384:    */     //   114: dup
/* 385:    */     //   115: invokespecial 78	java/io/StringWriter:<init>	()V
/* 386:    */     //   118: astore 9
/* 387:    */     //   120: aload_1
/* 388:    */     //   121: aload 9
/* 389:    */     //   123: invokeinterface 79 2 0
/* 390:    */     //   128: new 6	java/lang/StringBuilder
/* 391:    */     //   131: dup
/* 392:    */     //   132: invokespecial 7	java/lang/StringBuilder:<init>	()V
/* 393:    */     //   135: ldc 80
/* 394:    */     //   137: invokevirtual 9	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 395:    */     //   140: aload 8
/* 396:    */     //   142: invokevirtual 81	java/net/URL:toString	()Ljava/lang/String;
/* 397:    */     //   145: invokevirtual 9	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 398:    */     //   148: ldc 82
/* 399:    */     //   150: invokevirtual 9	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 400:    */     //   153: aload 9
/* 401:    */     //   155: invokevirtual 83	java/io/StringWriter:toString	()Ljava/lang/String;
/* 402:    */     //   158: invokevirtual 9	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 403:    */     //   161: invokevirtual 10	java/lang/StringBuilder:toString	()Ljava/lang/String;
/* 404:    */     //   164: astore_3
/* 405:    */     //   165: aload 8
/* 406:    */     //   167: invokevirtual 84	java/net/URL:openConnection	()Ljava/net/URLConnection;
/* 407:    */     //   170: checkcast 85	java/net/HttpURLConnection
/* 408:    */     //   173: astore 5
/* 409:    */     //   175: aload 5
/* 410:    */     //   177: ldc 86
/* 411:    */     //   179: invokevirtual 87	java/net/HttpURLConnection:setRequestMethod	(Ljava/lang/String;)V
/* 412:    */     //   182: aload 5
/* 413:    */     //   184: iconst_1
/* 414:    */     //   185: invokevirtual 88	java/net/HttpURLConnection:setDoOutput	(Z)V
/* 415:    */     //   188: aload 5
/* 416:    */     //   190: getstatic 89	nxt/peer/Peers:connectTimeout	I
/* 417:    */     //   193: invokevirtual 90	java/net/HttpURLConnection:setConnectTimeout	(I)V
/* 418:    */     //   196: aload 5
/* 419:    */     //   198: getstatic 91	nxt/peer/Peers:readTimeout	I
/* 420:    */     //   201: invokevirtual 92	java/net/HttpURLConnection:setReadTimeout	(I)V
/* 421:    */     //   204: aload 5
/* 422:    */     //   206: ldc 93
/* 423:    */     //   208: ldc 94
/* 424:    */     //   210: invokevirtual 95	java/net/HttpURLConnection:setRequestProperty	(Ljava/lang/String;Ljava/lang/String;)V
/* 425:    */     //   213: new 96	nxt/util/CountingOutputStream
/* 426:    */     //   216: dup
/* 427:    */     //   217: aload 5
/* 428:    */     //   219: invokevirtual 97	java/net/HttpURLConnection:getOutputStream	()Ljava/io/OutputStream;
/* 429:    */     //   222: invokespecial 98	nxt/util/CountingOutputStream:<init>	(Ljava/io/OutputStream;)V
/* 430:    */     //   225: astore 9
/* 431:    */     //   227: new 99	java/io/BufferedWriter
/* 432:    */     //   230: dup
/* 433:    */     //   231: new 100	java/io/OutputStreamWriter
/* 434:    */     //   234: dup
/* 435:    */     //   235: aload 9
/* 436:    */     //   237: ldc 101
/* 437:    */     //   239: invokespecial 102	java/io/OutputStreamWriter:<init>	(Ljava/io/OutputStream;Ljava/lang/String;)V
/* 438:    */     //   242: invokespecial 103	java/io/BufferedWriter:<init>	(Ljava/io/Writer;)V
/* 439:    */     //   245: astore 10
/* 440:    */     //   247: aconst_null
/* 441:    */     //   248: astore 11
/* 442:    */     //   250: aload_1
/* 443:    */     //   251: aload 10
/* 444:    */     //   253: invokeinterface 79 2 0
/* 445:    */     //   258: aload 10
/* 446:    */     //   260: ifnull +85 -> 345
/* 447:    */     //   263: aload 11
/* 448:    */     //   265: ifnull +23 -> 288
/* 449:    */     //   268: aload 10
/* 450:    */     //   270: invokevirtual 104	java/io/Writer:close	()V
/* 451:    */     //   273: goto +72 -> 345
/* 452:    */     //   276: astore 12
/* 453:    */     //   278: aload 11
/* 454:    */     //   280: aload 12
/* 455:    */     //   282: invokevirtual 106	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 456:    */     //   285: goto +60 -> 345
/* 457:    */     //   288: aload 10
/* 458:    */     //   290: invokevirtual 104	java/io/Writer:close	()V
/* 459:    */     //   293: goto +52 -> 345
/* 460:    */     //   296: astore 12
/* 461:    */     //   298: aload 12
/* 462:    */     //   300: astore 11
/* 463:    */     //   302: aload 12
/* 464:    */     //   304: athrow
/* 465:    */     //   305: astore 13
/* 466:    */     //   307: aload 10
/* 467:    */     //   309: ifnull +33 -> 342
/* 468:    */     //   312: aload 11
/* 469:    */     //   314: ifnull +23 -> 337
/* 470:    */     //   317: aload 10
/* 471:    */     //   319: invokevirtual 104	java/io/Writer:close	()V
/* 472:    */     //   322: goto +20 -> 342
/* 473:    */     //   325: astore 14
/* 474:    */     //   327: aload 11
/* 475:    */     //   329: aload 14
/* 476:    */     //   331: invokevirtual 106	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 477:    */     //   334: goto +8 -> 342
/* 478:    */     //   337: aload 10
/* 479:    */     //   339: invokevirtual 104	java/io/Writer:close	()V
/* 480:    */     //   342: aload 13
/* 481:    */     //   344: athrow
/* 482:    */     //   345: aload_0
/* 483:    */     //   346: aload 9
/* 484:    */     //   348: invokevirtual 107	nxt/util/CountingOutputStream:getCount	()J
/* 485:    */     //   351: invokevirtual 108	nxt/peer/PeerImpl:updateUploadedVolume	(J)V
/* 486:    */     //   354: aload 5
/* 487:    */     //   356: invokevirtual 109	java/net/HttpURLConnection:getResponseCode	()I
/* 488:    */     //   359: sipush 200
/* 489:    */     //   362: if_icmpne +448 -> 810
/* 490:    */     //   365: new 110	nxt/util/CountingInputStream
/* 491:    */     //   368: dup
/* 492:    */     //   369: aload 5
/* 493:    */     //   371: invokevirtual 111	java/net/HttpURLConnection:getInputStream	()Ljava/io/InputStream;
/* 494:    */     //   374: invokespecial 112	nxt/util/CountingInputStream:<init>	(Ljava/io/InputStream;)V
/* 495:    */     //   377: astore 10
/* 496:    */     //   379: aload 10
/* 497:    */     //   381: astore 11
/* 498:    */     //   383: ldc 94
/* 499:    */     //   385: aload 5
/* 500:    */     //   387: ldc 113
/* 501:    */     //   389: invokevirtual 114	java/net/HttpURLConnection:getHeaderField	(Ljava/lang/String;)Ljava/lang/String;
/* 502:    */     //   392: invokevirtual 115	java/lang/String:equals	(Ljava/lang/Object;)Z
/* 503:    */     //   395: ifeq +14 -> 409
/* 504:    */     //   398: new 116	java/util/zip/GZIPInputStream
/* 505:    */     //   401: dup
/* 506:    */     //   402: aload 10
/* 507:    */     //   404: invokespecial 117	java/util/zip/GZIPInputStream:<init>	(Ljava/io/InputStream;)V
/* 508:    */     //   407: astore 11
/* 509:    */     //   409: getstatic 76	nxt/peer/Peers:communicationLoggingMask	I
/* 510:    */     //   412: iconst_4
/* 511:    */     //   413: iand
/* 512:    */     //   414: ifeq +265 -> 679
/* 513:    */     //   417: new 118	java/io/ByteArrayOutputStream
/* 514:    */     //   420: dup
/* 515:    */     //   421: invokespecial 119	java/io/ByteArrayOutputStream:<init>	()V
/* 516:    */     //   424: astore 12
/* 517:    */     //   426: sipush 1024
/* 518:    */     //   429: newarray 慢⁤污潬慣楴湯
/* 519:    */     //   432: fconst_2
/* 520:    */     //   433: aload 11
/* 521:    */     //   435: astore 15
/* 522:    */     //   437: aconst_null
/* 523:    */     //   438: astore 16
/* 524:    */     //   440: aload 15
/* 525:    */     //   442: aload 13
/* 526:    */     //   444: iconst_0
/* 527:    */     //   445: aload 13
/* 528:    */     //   447: arraylength
/* 529:    */     //   448: invokevirtual 120	java/io/InputStream:read	([BII)I
/* 530:    */     //   451: dup
/* 531:    */     //   452: istore 14
/* 532:    */     //   454: ifle +16 -> 470
/* 533:    */     //   457: aload 12
/* 534:    */     //   459: aload 13
/* 535:    */     //   461: iconst_0
/* 536:    */     //   462: iload 14
/* 537:    */     //   464: invokevirtual 121	java/io/ByteArrayOutputStream:write	([BII)V
/* 538:    */     //   467: goto -27 -> 440
/* 539:    */     //   470: aload 15
/* 540:    */     //   472: ifnull +85 -> 557
/* 541:    */     //   475: aload 16
/* 542:    */     //   477: ifnull +23 -> 500
/* 543:    */     //   480: aload 15
/* 544:    */     //   482: invokevirtual 122	java/io/InputStream:close	()V
/* 545:    */     //   485: goto +72 -> 557
/* 546:    */     //   488: astore 17
/* 547:    */     //   490: aload 16
/* 548:    */     //   492: aload 17
/* 549:    */     //   494: invokevirtual 106	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 550:    */     //   497: goto +60 -> 557
/* 551:    */     //   500: aload 15
/* 552:    */     //   502: invokevirtual 122	java/io/InputStream:close	()V
/* 553:    */     //   505: goto +52 -> 557
/* 554:    */     //   508: astore 17
/* 555:    */     //   510: aload 17
/* 556:    */     //   512: astore 16
/* 557:    */     //   514: aload 17
/* 558:    */     //   516: athrow
/* 559:    */     //   517: astore 18
/* 560:    */     //   519: aload 15
/* 561:    */     //   521: ifnull +33 -> 554
/* 562:    */     //   524: aload 16
/* 563:    */     //   526: ifnull +23 -> 549
/* 564:    */     //   529: aload 15
/* 565:    */     //   531: invokevirtual 122	java/io/InputStream:close	()V
/* 566:    */     //   534: goto +20 -> 554
/* 567:    */     //   537: astore 19
/* 568:    */     //   539: aload 16
/* 569:    */     //   541: aload 19
/* 570:    */     //   543: invokevirtual 106	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 571:    */     //   546: goto +8 -> 554
/* 572:    */     //   549: aload 15
/* 573:    */     //   551: invokevirtual 122	java/io/InputStream:close	()V
/* 574:    */     //   554: aload 18
/* 575:    */     //   556: athrow
/* 576:    */     //   557: aload 12
/* 577:    */     //   559: ldc 101
/* 578:    */     //   561: invokevirtual 123	java/io/ByteArrayOutputStream:toString	(Ljava/lang/String;)Ljava/lang/String;
/* 579:    */     //   564: astore 15
/* 580:    */     //   566: aload 15
/* 581:    */     //   568: invokevirtual 124	java/lang/String:length	()I
/* 582:    */     //   571: ifle +68 -> 639
/* 583:    */     //   574: aload 11
/* 584:    */     //   576: instanceof 116
/* 585:    */     //   579: ifeq +60 -> 639
/* 586:    */     //   582: new 6	java/lang/StringBuilder
/* 587:    */     //   585: dup
/* 588:    */     //   586: invokespecial 7	java/lang/StringBuilder:<init>	()V
/* 589:    */     //   589: aload_3
/* 590:    */     //   590: invokevirtual 9	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 591:    */     //   593: ldc 125
/* 592:    */     //   595: iconst_2
/* 593:    */     //   596: anewarray 126	java/lang/Object
/* 594:    */     //   599: dup
/* 595:    */     //   600: iconst_0
/* 596:    */     //   601: aload 10
/* 597:    */     //   603: invokevirtual 127	nxt/util/CountingInputStream:getCount	()J
/* 598:    */     //   606: invokestatic 38	java/lang/Long:valueOf	(J)Ljava/lang/Long;
/* 599:    */     //   609: aastore
/* 600:    */     //   610: dup
/* 601:    */     //   611: iconst_1
/* 602:    */     //   612: aload 10
/* 603:    */     //   614: invokevirtual 127	nxt/util/CountingInputStream:getCount	()J
/* 604:    */     //   617: l2d
/* 605:    */     //   618: aload 15
/* 606:    */     //   620: invokevirtual 124	java/lang/String:length	()I
/* 607:    */     //   623: i2d
/* 608:    */     //   624: ddiv
/* 609:    */     //   625: invokestatic 128	java/lang/Double:valueOf	(D)Ljava/lang/Double;
/* 610:    */     //   628: aastore
/* 611:    */     //   629: invokestatic 129	java/lang/String:format	(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
/* 612:    */     //   632: invokevirtual 9	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 613:    */     //   635: invokevirtual 10	java/lang/StringBuilder:toString	()Ljava/lang/String;
/* 614:    */     //   638: astore_3
/* 615:    */     //   639: new 6	java/lang/StringBuilder
/* 616:    */     //   642: dup
/* 617:    */     //   643: invokespecial 7	java/lang/StringBuilder:<init>	()V
/* 618:    */     //   646: aload_3
/* 619:    */     //   647: invokevirtual 9	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 620:    */     //   650: ldc 130
/* 621:    */     //   652: invokevirtual 9	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 622:    */     //   655: aload 15
/* 623:    */     //   657: invokevirtual 9	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 624:    */     //   660: invokevirtual 10	java/lang/StringBuilder:toString	()Ljava/lang/String;
/* 625:    */     //   663: astore_3
/* 626:    */     //   664: iconst_1
/* 627:    */     //   665: istore 4
/* 628:    */     //   667: aload 15
/* 629:    */     //   669: invokestatic 131	org/json/simple/JSONValue:parse	(Ljava/lang/String;)Ljava/lang/Object;
/* 630:    */     //   672: checkcast 132	org/json/simple/JSONObject
/* 631:    */     //   675: astore_2
/* 632:    */     //   676: goto +122 -> 798
/* 633:    */     //   679: new 133	java/io/BufferedReader
/* 634:    */     //   682: dup
/* 635:    */     //   683: new 134	java/io/InputStreamReader
/* 636:    */     //   686: dup
/* 637:    */     //   687: aload 11
/* 638:    */     //   689: ldc 101
/* 639:    */     //   691: invokespecial 135	java/io/InputStreamReader:<init>	(Ljava/io/InputStream;Ljava/lang/String;)V
/* 640:    */     //   694: invokespecial 136	java/io/BufferedReader:<init>	(Ljava/io/Reader;)V
/* 641:    */     //   697: astore 12
/* 642:    */     //   699: aconst_null
/* 643:    */     //   700: astore 13
/* 644:    */     //   702: aload 12
/* 645:    */     //   704: invokestatic 137	org/json/simple/JSONValue:parse	(Ljava/io/Reader;)Ljava/lang/Object;
/* 646:    */     //   707: checkcast 132	org/json/simple/JSONObject
/* 647:    */     //   710: astore_2
/* 648:    */     //   711: aload 12
/* 649:    */     //   713: ifnull +85 -> 798
/* 650:    */     //   716: aload 13
/* 651:    */     //   718: ifnull +23 -> 741
/* 652:    */     //   721: aload 12
/* 653:    */     //   723: invokevirtual 138	java/io/Reader:close	()V
/* 654:    */     //   726: goto +72 -> 798
/* 655:    */     //   729: astore 14
/* 656:    */     //   731: aload 13
/* 657:    */     //   733: aload 14
/* 658:    */     //   735: invokevirtual 106	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 659:    */     //   738: goto +60 -> 798
/* 660:    */     //   741: aload 12
/* 661:    */     //   743: invokevirtual 138	java/io/Reader:close	()V
/* 662:    */     //   746: goto +52 -> 798
/* 663:    */     //   749: astore 14
/* 664:    */     //   751: aload 14
/* 665:    */     //   753: astore 13
/* 666:    */     //   755: aload 14
/* 667:    */     //   757: athrow
/* 668:    */     //   758: astore 20
/* 669:    */     //   760: aload 12
/* 670:    */     //   762: ifnull +33 -> 795
/* 671:    */     //   765: aload 13
/* 672:    */     //   767: ifnull +23 -> 790
/* 673:    */     //   770: aload 12
/* 674:    */     //   772: invokevirtual 138	java/io/Reader:close	()V
/* 675:    */     //   775: goto +20 -> 795
/* 676:    */     //   778: astore 21
/* 677:    */     //   780: aload 13
/* 678:    */     //   782: aload 21
/* 679:    */     //   784: invokevirtual 106	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 680:    */     //   787: goto +8 -> 795
/* 681:    */     //   790: aload 12
/* 682:    */     //   792: invokevirtual 138	java/io/Reader:close	()V
/* 683:    */     //   795: aload 20
/* 684:    */     //   797: athrow
/* 685:    */     //   798: aload_0
/* 686:    */     //   799: aload 10
/* 687:    */     //   801: invokevirtual 127	nxt/util/CountingInputStream:getCount	()J
/* 688:    */     //   804: invokevirtual 139	nxt/peer/PeerImpl:updateDownloadedVolume	(J)V
/* 689:    */     //   807: goto +76 -> 883
/* 690:    */     //   810: getstatic 76	nxt/peer/Peers:communicationLoggingMask	I
/* 691:    */     //   813: iconst_2
/* 692:    */     //   814: iand
/* 693:    */     //   815: ifeq +39 -> 854
/* 694:    */     //   818: new 6	java/lang/StringBuilder
/* 695:    */     //   821: dup
/* 696:    */     //   822: invokespecial 7	java/lang/StringBuilder:<init>	()V
/* 697:    */     //   825: aload_3
/* 698:    */     //   826: invokevirtual 9	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 699:    */     //   829: ldc 140
/* 700:    */     //   831: invokevirtual 9	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 701:    */     //   834: aload 5
/* 702:    */     //   836: invokevirtual 109	java/net/HttpURLConnection:getResponseCode	()I
/* 703:    */     //   839: invokevirtual 74	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
/* 704:    */     //   842: ldc 141
/* 705:    */     //   844: invokevirtual 9	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 706:    */     //   847: invokevirtual 10	java/lang/StringBuilder:toString	()Ljava/lang/String;
/* 707:    */     //   850: astore_3
/* 708:    */     //   851: iconst_1
/* 709:    */     //   852: istore 4
/* 710:    */     //   854: aload_0
/* 711:    */     //   855: getfield 16	nxt/peer/PeerImpl:state	Lnxt/peer/Peer$State;
/* 712:    */     //   858: getstatic 142	nxt/peer/Peer$State:CONNECTED	Lnxt/peer/Peer$State;
/* 713:    */     //   861: if_acmpne +13 -> 874
/* 714:    */     //   864: aload_0
/* 715:    */     //   865: getstatic 143	nxt/peer/Peer$State:DISCONNECTED	Lnxt/peer/Peer$State;
/* 716:    */     //   868: invokevirtual 62	nxt/peer/PeerImpl:setState	(Lnxt/peer/Peer$State;)V
/* 717:    */     //   871: goto +10 -> 881
/* 718:    */     //   874: aload_0
/* 719:    */     //   875: getstatic 15	nxt/peer/Peer$State:NON_CONNECTED	Lnxt/peer/Peer$State;
/* 720:    */     //   878: invokevirtual 62	nxt/peer/PeerImpl:setState	(Lnxt/peer/Peer$State;)V
/* 721:    */     //   881: aconst_null
/* 722:    */     //   882: astore_2
/* 723:    */     //   883: goto +94 -> 977
/* 724:    */     //   886: astore 6
/* 725:    */     //   888: aload 6
/* 726:    */     //   890: instanceof 145
/* 727:    */     //   893: ifne +26 -> 919
/* 728:    */     //   896: aload 6
/* 729:    */     //   898: instanceof 146
/* 730:    */     //   901: ifne +18 -> 919
/* 731:    */     //   904: aload 6
/* 732:    */     //   906: instanceof 147
/* 733:    */     //   909: ifne +10 -> 919
/* 734:    */     //   912: ldc 148
/* 735:    */     //   914: aload 6
/* 736:    */     //   916: invokestatic 59	nxt/util/Logger:logDebugMessage	(Ljava/lang/String;Ljava/lang/Exception;)V
/* 737:    */     //   919: getstatic 76	nxt/peer/Peers:communicationLoggingMask	I
/* 738:    */     //   922: iconst_1
/* 739:    */     //   923: iand
/* 740:    */     //   924: ifeq +34 -> 958
/* 741:    */     //   927: new 6	java/lang/StringBuilder
/* 742:    */     //   930: dup
/* 743:    */     //   931: invokespecial 7	java/lang/StringBuilder:<init>	()V
/* 744:    */     //   934: aload_3
/* 745:    */     //   935: invokevirtual 9	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 746:    */     //   938: ldc 130
/* 747:    */     //   940: invokevirtual 9	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 748:    */     //   943: aload 6
/* 749:    */     //   945: invokevirtual 58	java/lang/Exception:toString	()Ljava/lang/String;
/* 750:    */     //   948: invokevirtual 9	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 751:    */     //   951: invokevirtual 10	java/lang/StringBuilder:toString	()Ljava/lang/String;
/* 752:    */     //   954: astore_3
/* 753:    */     //   955: iconst_1
/* 754:    */     //   956: istore 4
/* 755:    */     //   958: aload_0
/* 756:    */     //   959: getfield 16	nxt/peer/PeerImpl:state	Lnxt/peer/Peer$State;
/* 757:    */     //   962: getstatic 142	nxt/peer/Peer$State:CONNECTED	Lnxt/peer/Peer$State;
/* 758:    */     //   965: if_acmpne +10 -> 975
/* 759:    */     //   968: aload_0
/* 760:    */     //   969: getstatic 143	nxt/peer/Peer$State:DISCONNECTED	Lnxt/peer/Peer$State;
/* 761:    */     //   972: invokevirtual 62	nxt/peer/PeerImpl:setState	(Lnxt/peer/Peer$State;)V
/* 762:    */     //   975: aconst_null
/* 763:    */     //   976: astore_2
/* 764:    */     //   977: iload 4
/* 765:    */     //   979: ifeq +25 -> 1004
/* 766:    */     //   982: new 6	java/lang/StringBuilder
/* 767:    */     //   985: dup
/* 768:    */     //   986: invokespecial 7	java/lang/StringBuilder:<init>	()V
/* 769:    */     //   989: aload_3
/* 770:    */     //   990: invokevirtual 9	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 771:    */     //   993: ldc 149
/* 772:    */     //   995: invokevirtual 9	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 773:    */     //   998: invokevirtual 10	java/lang/StringBuilder:toString	()Ljava/lang/String;
/* 774:    */     //   1001: invokestatic 150	nxt/util/Logger:logMessage	(Ljava/lang/String;)V
/* 775:    */     //   1004: aload 5
/* 776:    */     //   1006: ifnull +8 -> 1014
/* 777:    */     //   1009: aload 5
/* 778:    */     //   1011: invokevirtual 151	java/net/HttpURLConnection:disconnect	()V
/* 779:    */     //   1014: aload_2
/* 780:    */     //   1015: areturn
/* 781:    */     // Line number table:
/* 782:    */     //   Java source line #279	-> byte code offset #0
/* 783:    */     //   Java source line #280	-> byte code offset #2
/* 784:    */     //   Java source line #281	-> byte code offset #5
/* 785:    */     //   Java source line #285	-> byte code offset #8
/* 786:    */     //   Java source line #286	-> byte code offset #28
/* 787:    */     //   Java source line #287	-> byte code offset #39
/* 788:    */     //   Java source line #288	-> byte code offset #47
/* 789:    */     //   Java source line #289	-> byte code offset #54
/* 790:    */     //   Java source line #290	-> byte code offset #62
/* 791:    */     //   Java source line #292	-> byte code offset #83
/* 792:    */     //   Java source line #293	-> byte code offset #91
/* 793:    */     //   Java source line #295	-> byte code offset #105
/* 794:    */     //   Java source line #296	-> byte code offset #111
/* 795:    */     //   Java source line #297	-> byte code offset #120
/* 796:    */     //   Java source line #298	-> byte code offset #128
/* 797:    */     //   Java source line #301	-> byte code offset #165
/* 798:    */     //   Java source line #302	-> byte code offset #175
/* 799:    */     //   Java source line #303	-> byte code offset #182
/* 800:    */     //   Java source line #304	-> byte code offset #188
/* 801:    */     //   Java source line #305	-> byte code offset #196
/* 802:    */     //   Java source line #306	-> byte code offset #204
/* 803:    */     //   Java source line #308	-> byte code offset #213
/* 804:    */     //   Java source line #309	-> byte code offset #227
/* 805:    */     //   Java source line #310	-> byte code offset #250
/* 806:    */     //   Java source line #311	-> byte code offset #258
/* 807:    */     //   Java source line #309	-> byte code offset #296
/* 808:    */     //   Java source line #311	-> byte code offset #305
/* 809:    */     //   Java source line #312	-> byte code offset #345
/* 810:    */     //   Java source line #314	-> byte code offset #354
/* 811:    */     //   Java source line #315	-> byte code offset #365
/* 812:    */     //   Java source line #316	-> byte code offset #379
/* 813:    */     //   Java source line #317	-> byte code offset #383
/* 814:    */     //   Java source line #318	-> byte code offset #398
/* 815:    */     //   Java source line #320	-> byte code offset #409
/* 816:    */     //   Java source line #321	-> byte code offset #417
/* 817:    */     //   Java source line #322	-> byte code offset #426
/* 818:    */     //   Java source line #324	-> byte code offset #433
/* 819:    */     //   Java source line #325	-> byte code offset #440
/* 820:    */     //   Java source line #326	-> byte code offset #457
/* 821:    */     //   Java source line #328	-> byte code offset #470
/* 822:    */     //   Java source line #324	-> byte code offset #508
/* 823:    */     //   Java source line #328	-> byte code offset #517
/* 824:    */     //   Java source line #329	-> byte code offset #557
/* 825:    */     //   Java source line #330	-> byte code offset #566
/* 826:    */     //   Java source line #331	-> byte code offset #582
/* 827:    */     //   Java source line #333	-> byte code offset #639
/* 828:    */     //   Java source line #334	-> byte code offset #664
/* 829:    */     //   Java source line #335	-> byte code offset #667
/* 830:    */     //   Java source line #336	-> byte code offset #676
/* 831:    */     //   Java source line #337	-> byte code offset #679
/* 832:    */     //   Java source line #338	-> byte code offset #702
/* 833:    */     //   Java source line #339	-> byte code offset #711
/* 834:    */     //   Java source line #337	-> byte code offset #749
/* 835:    */     //   Java source line #339	-> byte code offset #758
/* 836:    */     //   Java source line #341	-> byte code offset #798
/* 837:    */     //   Java source line #342	-> byte code offset #807
/* 838:    */     //   Java source line #344	-> byte code offset #810
/* 839:    */     //   Java source line #345	-> byte code offset #818
/* 840:    */     //   Java source line #346	-> byte code offset #851
/* 841:    */     //   Java source line #348	-> byte code offset #854
/* 842:    */     //   Java source line #349	-> byte code offset #864
/* 843:    */     //   Java source line #351	-> byte code offset #874
/* 844:    */     //   Java source line #353	-> byte code offset #881
/* 845:    */     //   Java source line #369	-> byte code offset #883
/* 846:    */     //   Java source line #357	-> byte code offset #886
/* 847:    */     //   Java source line #358	-> byte code offset #888
/* 848:    */     //   Java source line #359	-> byte code offset #912
/* 849:    */     //   Java source line #361	-> byte code offset #919
/* 850:    */     //   Java source line #362	-> byte code offset #927
/* 851:    */     //   Java source line #363	-> byte code offset #955
/* 852:    */     //   Java source line #365	-> byte code offset #958
/* 853:    */     //   Java source line #366	-> byte code offset #968
/* 854:    */     //   Java source line #368	-> byte code offset #975
/* 855:    */     //   Java source line #371	-> byte code offset #977
/* 856:    */     //   Java source line #372	-> byte code offset #982
/* 857:    */     //   Java source line #375	-> byte code offset #1004
/* 858:    */     //   Java source line #376	-> byte code offset #1009
/* 859:    */     //   Java source line #379	-> byte code offset #1014
/* 860:    */     // Local variable table:
/* 861:    */     //   start	length	slot	name	signature
/* 862:    */     //   0	1016	0	this	PeerImpl
/* 863:    */     //   0	1016	1	paramJSONStreamAware	org.json.simple.JSONStreamAware
/* 864:    */     //   675	340	2	localJSONObject	JSONObject
/* 865:    */     //   1	989	3	str1	String
/* 866:    */     //   3	975	4	i	int
/* 867:    */     //   6	1004	5	localHttpURLConnection	java.net.HttpURLConnection
/* 868:    */     //   26	16	6	str2	String
/* 869:    */     //   886	58	6	localRuntimeException	RuntimeException
/* 870:    */     //   37	59	7	localStringBuilder	StringBuilder
/* 871:    */     //   103	63	8	localURL	URL
/* 872:    */     //   118	229	9	localObject1	Object
/* 873:    */     //   245	555	10	localObject2	Object
/* 874:    */     //   248	440	11	localObject3	Object
/* 875:    */     //   276	5	12	localThrowable1	Throwable
/* 876:    */     //   296	7	12	localThrowable2	Throwable
/* 877:    */     //   424	367	12	localObject4	Object
/* 878:    */     //   305	38	13	localObject5	Object
/* 879:    */     //   431	350	13	localObject6	Object
/* 880:    */     //   325	5	14	localThrowable3	Throwable
/* 881:    */     //   452	11	14	j	int
/* 882:    */     //   729	5	14	localThrowable4	Throwable
/* 883:    */     //   749	7	14	localThrowable5	Throwable
/* 884:    */     //   435	233	15	localObject7	Object
/* 885:    */     //   438	102	16	localObject8	Object
/* 886:    */     //   488	5	17	localThrowable6	Throwable
/* 887:    */     //   508	7	17	localThrowable7	Throwable
/* 888:    */     //   517	38	18	localObject9	Object
/* 889:    */     //   537	5	19	localThrowable8	Throwable
/* 890:    */     //   758	38	20	localObject10	Object
/* 891:    */     //   778	5	21	localThrowable9	Throwable
/* 892:    */     // Exception table:
/* 893:    */     //   from	to	target	type
/* 894:    */     //   268	273	276	java/lang/Throwable
/* 895:    */     //   250	258	296	java/lang/Throwable
/* 896:    */     //   250	258	305	finally
/* 897:    */     //   296	307	305	finally
/* 898:    */     //   317	322	325	java/lang/Throwable
/* 899:    */     //   480	485	488	java/lang/Throwable
/* 900:    */     //   440	470	508	java/lang/Throwable
/* 901:    */     //   440	470	517	finally
/* 902:    */     //   508	519	517	finally
/* 903:    */     //   529	534	537	java/lang/Throwable
/* 904:    */     //   721	726	729	java/lang/Throwable
/* 905:    */     //   702	711	749	java/lang/Throwable
/* 906:    */     //   702	711	758	finally
/* 907:    */     //   749	760	758	finally
/* 908:    */     //   770	775	778	java/lang/Throwable
/* 909:    */     //   8	883	886	java/lang/RuntimeException
/* 910:    */     //   8	883	886	java/io/IOException
/* 911:    */   }
/* 912:    */   
/* 913:    */   /* Error */
/* 914:    */   boolean analyzeHallmark(String paramString1, String paramString2)
/* 915:    */   {
/* 916:    */     // Byte code:
/* 917:    */     //   0: aload_2
/* 918:    */     //   1: ifnonnull +12 -> 13
/* 919:    */     //   4: aload_0
/* 920:    */     //   5: getfield 36	nxt/peer/PeerImpl:hallmark	Lnxt/peer/Hallmark;
/* 921:    */     //   8: ifnonnull +5 -> 13
/* 922:    */     //   11: iconst_1
/* 923:    */     //   12: ireturn
/* 924:    */     //   13: aload_0
/* 925:    */     //   14: getfield 36	nxt/peer/PeerImpl:hallmark	Lnxt/peer/Hallmark;
/* 926:    */     //   17: ifnull +19 -> 36
/* 927:    */     //   20: aload_0
/* 928:    */     //   21: getfield 36	nxt/peer/PeerImpl:hallmark	Lnxt/peer/Hallmark;
/* 929:    */     //   24: invokevirtual 171	nxt/peer/Hallmark:getHallmarkString	()Ljava/lang/String;
/* 930:    */     //   27: aload_2
/* 931:    */     //   28: invokevirtual 115	java/lang/String:equals	(Ljava/lang/Object;)Z
/* 932:    */     //   31: ifeq +5 -> 36
/* 933:    */     //   34: iconst_1
/* 934:    */     //   35: ireturn
/* 935:    */     //   36: aload_2
/* 936:    */     //   37: ifnonnull +10 -> 47
/* 937:    */     //   40: aload_0
/* 938:    */     //   41: aconst_null
/* 939:    */     //   42: putfield 36	nxt/peer/PeerImpl:hallmark	Lnxt/peer/Hallmark;
/* 940:    */     //   45: iconst_1
/* 941:    */     //   46: ireturn
/* 942:    */     //   47: new 172	java/net/URI
/* 943:    */     //   50: dup
/* 944:    */     //   51: new 6	java/lang/StringBuilder
/* 945:    */     //   54: dup
/* 946:    */     //   55: invokespecial 7	java/lang/StringBuilder:<init>	()V
/* 947:    */     //   58: ldc 8
/* 948:    */     //   60: invokevirtual 9	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 949:    */     //   63: aload_1
/* 950:    */     //   64: invokevirtual 173	java/lang/String:trim	()Ljava/lang/String;
/* 951:    */     //   67: invokevirtual 9	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 952:    */     //   70: invokevirtual 10	java/lang/StringBuilder:toString	()Ljava/lang/String;
/* 953:    */     //   73: invokespecial 174	java/net/URI:<init>	(Ljava/lang/String;)V
/* 954:    */     //   76: astore_3
/* 955:    */     //   77: aload_3
/* 956:    */     //   78: invokevirtual 175	java/net/URI:getHost	()Ljava/lang/String;
/* 957:    */     //   81: astore 4
/* 958:    */     //   83: aload_2
/* 959:    */     //   84: invokestatic 176	nxt/peer/Hallmark:parseHallmark	(Ljava/lang/String;)Lnxt/peer/Hallmark;
/* 960:    */     //   87: astore 5
/* 961:    */     //   89: aload 5
/* 962:    */     //   91: invokevirtual 177	nxt/peer/Hallmark:isValid	()Z
/* 963:    */     //   94: ifeq +35 -> 129
/* 964:    */     //   97: aload 5
/* 965:    */     //   99: invokevirtual 178	nxt/peer/Hallmark:getHost	()Ljava/lang/String;
/* 966:    */     //   102: aload 4
/* 967:    */     //   104: invokevirtual 115	java/lang/String:equals	(Ljava/lang/Object;)Z
/* 968:    */     //   107: ifne +24 -> 131
/* 969:    */     //   110: aload 4
/* 970:    */     //   112: invokestatic 179	java/net/InetAddress:getByName	(Ljava/lang/String;)Ljava/net/InetAddress;
/* 971:    */     //   115: aload 5
/* 972:    */     //   117: invokevirtual 178	nxt/peer/Hallmark:getHost	()Ljava/lang/String;
/* 973:    */     //   120: invokestatic 179	java/net/InetAddress:getByName	(Ljava/lang/String;)Ljava/net/InetAddress;
/* 974:    */     //   123: invokevirtual 180	java/net/InetAddress:equals	(Ljava/lang/Object;)Z
/* 975:    */     //   126: ifne +5 -> 131
/* 976:    */     //   129: iconst_0
/* 977:    */     //   130: ireturn
/* 978:    */     //   131: aload_0
/* 979:    */     //   132: aload 5
/* 980:    */     //   134: putfield 36	nxt/peer/PeerImpl:hallmark	Lnxt/peer/Hallmark;
/* 981:    */     //   137: aload 5
/* 982:    */     //   139: invokevirtual 181	nxt/peer/Hallmark:getPublicKey	()[B
/* 983:    */     //   142: invokestatic 182	nxt/Account:getId	([B)J
/* 984:    */     //   145: lstore 6
/* 985:    */     //   147: new 183	java/util/ArrayList
/* 986:    */     //   150: dup
/* 987:    */     //   151: invokespecial 184	java/util/ArrayList:<init>	()V
/* 988:    */     //   154: astore 8
/* 989:    */     //   156: iconst_0
/* 990:    */     //   157: istore 9
/* 991:    */     //   159: lconst_0
/* 992:    */     //   160: lstore 10
/* 993:    */     //   162: getstatic 185	nxt/peer/Peers:allPeers	Ljava/util/Collection;
/* 994:    */     //   165: invokeinterface 186 1 0
/* 995:    */     //   170: astore 12
/* 996:    */     //   172: aload 12
/* 997:    */     //   174: invokeinterface 187 1 0
/* 998:    */     //   179: ifeq +102 -> 281
/* 999:    */     //   182: aload 12
/* :00:    */     //   184: invokeinterface 188 1 0
/* :01:    */     //   189: checkcast 189	nxt/peer/PeerImpl
/* :02:    */     //   192: astore 13
/* :03:    */     //   194: aload 13
/* :04:    */     //   196: getfield 36	nxt/peer/PeerImpl:hallmark	Lnxt/peer/Hallmark;
/* :05:    */     //   199: ifnonnull +6 -> 205
/* :06:    */     //   202: goto -30 -> 172
/* :07:    */     //   205: lload 6
/* :08:    */     //   207: aload 13
/* :09:    */     //   209: getfield 36	nxt/peer/PeerImpl:hallmark	Lnxt/peer/Hallmark;
/* :10:    */     //   212: invokevirtual 37	nxt/peer/Hallmark:getAccountId	()J
/* :11:    */     //   215: lcmp
/* :12:    */     //   216: ifne +62 -> 278
/* :13:    */     //   219: aload 8
/* :14:    */     //   221: aload 13
/* :15:    */     //   223: invokeinterface 190 2 0
/* :16:    */     //   228: pop
/* :17:    */     //   229: aload 13
/* :18:    */     //   231: getfield 36	nxt/peer/PeerImpl:hallmark	Lnxt/peer/Hallmark;
/* :19:    */     //   234: invokevirtual 191	nxt/peer/Hallmark:getDate	()I
/* :20:    */     //   237: iload 9
/* :21:    */     //   239: if_icmple +26 -> 265
/* :22:    */     //   242: aload 13
/* :23:    */     //   244: getfield 36	nxt/peer/PeerImpl:hallmark	Lnxt/peer/Hallmark;
/* :24:    */     //   247: invokevirtual 191	nxt/peer/Hallmark:getDate	()I
/* :25:    */     //   250: istore 9
/* :26:    */     //   252: aload 13
/* :27:    */     //   254: iload 9
/* :28:    */     //   256: invokespecial 192	nxt/peer/PeerImpl:getHallmarkWeight	(I)I
/* :29:    */     //   259: i2l
/* :30:    */     //   260: lstore 10
/* :31:    */     //   262: goto +16 -> 278
/* :32:    */     //   265: lload 10
/* :33:    */     //   267: aload 13
/* :34:    */     //   269: iload 9
/* :35:    */     //   271: invokespecial 192	nxt/peer/PeerImpl:getHallmarkWeight	(I)I
/* :36:    */     //   274: i2l
/* :37:    */     //   275: ladd
/* :38:    */     //   276: lstore 10
/* :39:    */     //   278: goto -106 -> 172
/* :40:    */     //   281: aload 8
/* :41:    */     //   283: invokeinterface 193 1 0
/* :42:    */     //   288: astore 12
/* :43:    */     //   290: aload 12
/* :44:    */     //   292: invokeinterface 187 1 0
/* :45:    */     //   297: ifeq +46 -> 343
/* :46:    */     //   300: aload 12
/* :47:    */     //   302: invokeinterface 188 1 0
/* :48:    */     //   307: checkcast 189	nxt/peer/PeerImpl
/* :49:    */     //   310: astore 13
/* :50:    */     //   312: aload 13
/* :51:    */     //   314: ldc2_w 48
/* :52:    */     //   317: aload 13
/* :53:    */     //   319: iload 9
/* :54:    */     //   321: invokespecial 192	nxt/peer/PeerImpl:getHallmarkWeight	(I)I
/* :55:    */     //   324: i2l
/* :56:    */     //   325: lmul
/* :57:    */     //   326: lload 10
/* :58:    */     //   328: ldiv
/* :59:    */     //   329: putfield 44	nxt/peer/PeerImpl:adjustedWeight	J
/* :60:    */     //   332: aload 13
/* :61:    */     //   334: getstatic 194	nxt/peer/Peers$Event:WEIGHT	Lnxt/peer/Peers$Event;
/* :62:    */     //   337: invokestatic 19	nxt/peer/Peers:notifyListeners	(Lnxt/peer/Peer;Lnxt/peer/Peers$Event;)V
/* :63:    */     //   340: goto -50 -> 290
/* :64:    */     //   343: iconst_1
/* :65:    */     //   344: ireturn
/* :66:    */     //   345: astore_3
/* :67:    */     //   346: goto +39 -> 385
/* :68:    */     //   349: astore_3
/* :69:    */     //   350: new 6	java/lang/StringBuilder
/* :70:    */     //   353: dup
/* :71:    */     //   354: invokespecial 7	java/lang/StringBuilder:<init>	()V
/* :72:    */     //   357: ldc 196
/* :73:    */     //   359: invokevirtual 9	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* :74:    */     //   362: aload_1
/* :75:    */     //   363: invokevirtual 9	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* :76:    */     //   366: ldc 197
/* :77:    */     //   368: invokevirtual 9	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* :78:    */     //   371: aload_3
/* :79:    */     //   372: invokevirtual 58	java/lang/Exception:toString	()Ljava/lang/String;
/* :80:    */     //   375: invokevirtual 9	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* :81:    */     //   378: invokevirtual 10	java/lang/StringBuilder:toString	()Ljava/lang/String;
/* :82:    */     //   381: aload_3
/* :83:    */     //   382: invokestatic 59	nxt/util/Logger:logDebugMessage	(Ljava/lang/String;Ljava/lang/Exception;)V
/* :84:    */     //   385: iconst_0
/* :85:    */     //   386: ireturn
/* :86:    */     // Line number table:
/* :87:    */     //   Java source line #425	-> byte code offset #0
/* :88:    */     //   Java source line #426	-> byte code offset #11
/* :89:    */     //   Java source line #429	-> byte code offset #13
/* :90:    */     //   Java source line #430	-> byte code offset #34
/* :91:    */     //   Java source line #433	-> byte code offset #36
/* :92:    */     //   Java source line #434	-> byte code offset #40
/* :93:    */     //   Java source line #435	-> byte code offset #45
/* :94:    */     //   Java source line #439	-> byte code offset #47
/* :95:    */     //   Java source line #440	-> byte code offset #77
/* :96:    */     //   Java source line #442	-> byte code offset #83
/* :97:    */     //   Java source line #443	-> byte code offset #89
/* :98:    */     //   Java source line #446	-> byte code offset #129
/* :99:    */     //   Java source line #448	-> byte code offset #131
/* ;00:    */     //   Java source line #449	-> byte code offset #137
/* ;01:    */     //   Java source line #450	-> byte code offset #147
/* ;02:    */     //   Java source line #451	-> byte code offset #156
/* ;03:    */     //   Java source line #452	-> byte code offset #159
/* ;04:    */     //   Java source line #453	-> byte code offset #162
/* ;05:    */     //   Java source line #454	-> byte code offset #194
/* ;06:    */     //   Java source line #455	-> byte code offset #202
/* ;07:    */     //   Java source line #457	-> byte code offset #205
/* ;08:    */     //   Java source line #458	-> byte code offset #219
/* ;09:    */     //   Java source line #459	-> byte code offset #229
/* ;10:    */     //   Java source line #460	-> byte code offset #242
/* ;11:    */     //   Java source line #461	-> byte code offset #252
/* ;12:    */     //   Java source line #463	-> byte code offset #265
/* ;13:    */     //   Java source line #466	-> byte code offset #278
/* ;14:    */     //   Java source line #468	-> byte code offset #281
/* ;15:    */     //   Java source line #469	-> byte code offset #312
/* ;16:    */     //   Java source line #470	-> byte code offset #332
/* ;17:    */     //   Java source line #471	-> byte code offset #340
/* ;18:    */     //   Java source line #473	-> byte code offset #343
/* ;19:    */     //   Java source line #475	-> byte code offset #345
/* ;20:    */     //   Java source line #478	-> byte code offset #346
/* ;21:    */     //   Java source line #476	-> byte code offset #349
/* ;22:    */     //   Java source line #477	-> byte code offset #350
/* ;23:    */     //   Java source line #479	-> byte code offset #385
/* ;24:    */     // Local variable table:
/* ;25:    */     //   start	length	slot	name	signature
/* ;26:    */     //   0	387	0	this	PeerImpl
/* ;27:    */     //   0	387	1	paramString1	String
/* ;28:    */     //   0	387	2	paramString2	String
/* ;29:    */     //   76	2	3	localURI	java.net.URI
/* ;30:    */     //   345	1	3	localUnknownHostException	java.net.UnknownHostException
/* ;31:    */     //   349	33	3	localURISyntaxException	java.net.URISyntaxException
/* ;32:    */     //   81	30	4	str	String
/* ;33:    */     //   87	51	5	localHallmark	Hallmark
/* ;34:    */     //   145	61	6	l1	long
/* ;35:    */     //   154	128	8	localArrayList	java.util.ArrayList
/* ;36:    */     //   157	163	9	i	int
/* ;37:    */     //   160	167	10	l2	long
/* ;38:    */     //   170	131	12	localIterator	java.util.Iterator
/* ;39:    */     //   192	141	13	localPeerImpl	PeerImpl
/* ;40:    */     // Exception table:
/* ;41:    */     //   from	to	target	type
/* ;42:    */     //   47	130	345	java/net/UnknownHostException
/* ;43:    */     //   131	344	345	java/net/UnknownHostException
/* ;44:    */     //   47	130	349	java/net/URISyntaxException
/* ;45:    */     //   47	130	349	java/lang/RuntimeException
/* ;46:    */     //   131	344	349	java/net/URISyntaxException
/* ;47:    */     //   131	344	349	java/lang/RuntimeException
/* ;48:    */   }
/* ;49:    */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.peer.PeerImpl
 * JD-Core Version:    0.7.1
 */