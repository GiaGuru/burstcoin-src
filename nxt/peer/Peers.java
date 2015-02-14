/*   1:    */ package nxt.peer;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.Collection;
/*   5:    */ import java.util.Collections;
/*   6:    */ import java.util.HashSet;
/*   7:    */ import java.util.Iterator;
/*   8:    */ import java.util.List;
/*   9:    */ import java.util.Map.Entry;
/*  10:    */ import java.util.Set;
/*  11:    */ import java.util.concurrent.Callable;
/*  12:    */ import java.util.concurrent.ConcurrentHashMap;
/*  13:    */ import java.util.concurrent.ConcurrentMap;
/*  14:    */ import java.util.concurrent.ExecutionException;
/*  15:    */ import java.util.concurrent.ExecutorService;
/*  16:    */ import java.util.concurrent.Executors;
/*  17:    */ import java.util.concurrent.Future;
/*  18:    */ import java.util.concurrent.ThreadLocalRandom;
/*  19:    */ import java.util.concurrent.TimeUnit;
/*  20:    */ import java.util.concurrent.TimeoutException;
/*  21:    */ import nxt.Account;
/*  22:    */ import nxt.Block;
/*  23:    */ import nxt.Constants;
/*  24:    */ import nxt.Nxt;
/*  25:    */ import nxt.Transaction;
/*  26:    */ import nxt.db.Db;
/*  27:    */ import nxt.util.JSON;
/*  28:    */ import nxt.util.Listener;
/*  29:    */ import nxt.util.Listeners;
/*  30:    */ import nxt.util.Logger;
/*  31:    */ import nxt.util.ThreadPool;
/*  32:    */ import org.eclipse.jetty.server.Server;
/*  33:    */ import org.eclipse.jetty.server.ServerConnector;
/*  34:    */ import org.eclipse.jetty.servlet.FilterHolder;
/*  35:    */ import org.eclipse.jetty.servlet.ServletHandler;
/*  36:    */ import org.eclipse.jetty.servlet.ServletHolder;
/*  37:    */ import org.eclipse.jetty.servlets.DoSFilter;
/*  38:    */ import org.eclipse.jetty.servlets.GzipFilter;
/*  39:    */ import org.json.simple.JSONArray;
/*  40:    */ import org.json.simple.JSONObject;
/*  41:    */ import org.json.simple.JSONStreamAware;
/*  42:    */ 
/*  43:    */ public final class Peers
/*  44:    */ {
/*  45:    */   static final int LOGGING_MASK_EXCEPTIONS = 1;
/*  46:    */   static final int LOGGING_MASK_NON200_RESPONSES = 2;
/*  47:    */   static final int LOGGING_MASK_200_RESPONSES = 4;
/*  48:    */   static final int communicationLoggingMask;
/*  49:    */   static final Set<String> wellKnownPeers;
/*  50:    */   static final Set<String> knownBlacklistedPeers;
/*  51:    */   static final int connectTimeout;
/*  52:    */   static final int readTimeout;
/*  53:    */   static final int blacklistingPeriod;
/*  54:    */   static final boolean getMorePeers;
/*  55:    */   static final int DEFAULT_PEER_PORT = 8123;
/*  56:    */   static final int TESTNET_PEER_PORT = 7123;
/*  57:    */   private static final String myPlatform;
/*  58:    */   private static final String myAddress;
/*  59:    */   private static final int myPeerServerPort;
/*  60:    */   private static final String myHallmark;
/*  61:    */   private static final boolean shareMyAddress;
/*  62:    */   private static final int maxNumberOfConnectedPublicPeers;
/*  63:    */   private static final boolean enableHallmarkProtection;
/*  64:    */   private static final int pushThreshold;
/*  65:    */   private static final int pullThreshold;
/*  66:    */   private static final int sendToPeersLimit;
/*  67:    */   private static final boolean usePeersDb;
/*  68:    */   private static final boolean savePeers;
/*  69:    */   private static final String dumpPeersVersion;
/*  70:    */   static final JSONStreamAware myPeerInfoRequest;
/*  71:    */   static final JSONStreamAware myPeerInfoResponse;
/*  72:    */   public static void init() {}
/*  73:    */   
/*  74:    */   public static enum Event
/*  75:    */   {
/*  76: 51 */     BLACKLIST,  UNBLACKLIST,  DEACTIVATE,  REMOVE,  DOWNLOADED_VOLUME,  UPLOADED_VOLUME,  WEIGHT,  ADDED_ACTIVE_PEER,  CHANGED_ACTIVE_PEER,  NEW_PEER;
/*  77:    */     
/*  78:    */     private Event() {}
/*  79:    */   }
/*  80:    */   
/*  81: 90 */   private static final Listeners<Peer, Event> listeners = new Listeners();
/*  82: 92 */   private static final ConcurrentMap<String, PeerImpl> peers = new ConcurrentHashMap();
/*  83: 93 */   private static final ConcurrentMap<String, String> announcedAddresses = new ConcurrentHashMap();
/*  84: 95 */   static final Collection<PeerImpl> allPeers = Collections.unmodifiableCollection(peers.values());
/*  85: 97 */   private static final ExecutorService sendToPeersService = Executors.newFixedThreadPool(10);
/*  86:    */   private static final Runnable peerUnBlacklistingThread;
/*  87:    */   private static final Runnable peerConnectingThread;
/*  88:    */   private static final Runnable getMorePeersThread;
/*  89:    */   
/*  90:    */   private static class Init
/*  91:    */   {
/*  92:    */     private static final Server peerServer;
/*  93:    */     
/*  94:    */     private static void init() {}
/*  95:    */     
/*  96:    */     static
/*  97:    */     {
/*  98:246 */       if (Peers.shareMyAddress)
/*  99:    */       {
/* 100:247 */         peerServer = new Server();
/* 101:248 */         ServerConnector localServerConnector = new ServerConnector(peerServer);
/* 102:249 */         final int i = Constants.isTestnet ? 7123 : Peers.myPeerServerPort;
/* 103:250 */         localServerConnector.setPort(i);
/* 104:251 */         String str = Nxt.getStringProperty("nxt.peerServerHost");
/* 105:252 */         localServerConnector.setHost(str);
/* 106:253 */         localServerConnector.setIdleTimeout(Nxt.getIntProperty("nxt.peerServerIdleTimeout"));
/* 107:254 */         localServerConnector.setReuseAddress(true);
/* 108:255 */         peerServer.addConnector(localServerConnector);
/* 109:    */         
/* 110:257 */         ServletHolder localServletHolder = new ServletHolder(new PeerServlet());
/* 111:258 */         boolean bool = Nxt.getBooleanProperty("nxt.enablePeerServerGZIPFilter").booleanValue();
/* 112:259 */         localServletHolder.setInitParameter("isGzipEnabled", Boolean.toString(bool));
/* 113:260 */         ServletHandler localServletHandler = new ServletHandler();
/* 114:261 */         localServletHandler.addServletWithMapping(localServletHolder, "/*");
/* 115:    */         FilterHolder localFilterHolder;
/* 116:262 */         if (Nxt.getBooleanProperty("nxt.enablePeerServerDoSFilter").booleanValue())
/* 117:    */         {
/* 118:263 */           localFilterHolder = localServletHandler.addFilterWithMapping(DoSFilter.class, "/*", 0);
/* 119:264 */           localFilterHolder.setInitParameter("maxRequestsPerSec", Nxt.getStringProperty("nxt.peerServerDoSFilter.maxRequestsPerSec"));
/* 120:265 */           localFilterHolder.setInitParameter("delayMs", Nxt.getStringProperty("nxt.peerServerDoSFilter.delayMs"));
/* 121:266 */           localFilterHolder.setInitParameter("maxRequestMs", Nxt.getStringProperty("nxt.peerServerDoSFilter.maxRequestMs"));
/* 122:267 */           localFilterHolder.setInitParameter("trackSessions", "false");
/* 123:268 */           localFilterHolder.setAsyncSupported(true);
/* 124:    */         }
/* 125:270 */         if (bool)
/* 126:    */         {
/* 127:271 */           localFilterHolder = localServletHandler.addFilterWithMapping(GzipFilter.class, "/*", 0);
/* 128:272 */           localFilterHolder.setInitParameter("methods", "GET,POST");
/* 129:273 */           localFilterHolder.setAsyncSupported(true);
/* 130:    */         }
/* 131:276 */         peerServer.setHandler(localServletHandler);
/* 132:277 */         peerServer.setStopAtShutdown(true);
/* 133:278 */         ThreadPool.runBeforeStart(new Runnable()
/* 134:    */         {
/* 135:    */           public void run()
/* 136:    */           {
/* 137:    */             try
/* 138:    */             {
/* 139:282 */               Peers.Init.peerServer.start();
/* 140:283 */               Logger.logMessage("Started peer networking server at " + this.val$host + ":" + i);
/* 141:    */             }
/* 142:    */             catch (Exception localException)
/* 143:    */             {
/* 144:285 */               Logger.logErrorMessage("Failed to start peer networking server", localException);
/* 145:286 */               throw new RuntimeException(localException.toString(), localException);
/* 146:    */             }
/* 147:    */           }
/* 148:286 */         }, true);
/* 149:    */       }
/* 150:    */       else
/* 151:    */       {
/* 152:291 */         peerServer = null;
/* 153:292 */         Logger.logMessage("shareMyAddress is disabled, will not start peer networking server");
/* 154:    */       }
/* 155:    */     }
/* 156:    */   }
/* 157:    */   
/* 158:    */   public static void shutdown()
/* 159:    */   {
/* 160:493 */     if (Init.peerServer != null) {
/* 161:    */       try
/* 162:    */       {
/* 163:495 */         Init.peerServer.stop();
/* 164:    */       }
/* 165:    */       catch (Exception localException)
/* 166:    */       {
/* 167:497 */         Logger.logShutdownMessage("Failed to stop peer server", localException);
/* 168:    */       }
/* 169:    */     }
/* 170:500 */     if (dumpPeersVersion != null)
/* 171:    */     {
/* 172:501 */       StringBuilder localStringBuilder = new StringBuilder();
/* 173:502 */       for (Map.Entry localEntry : announcedAddresses.entrySet())
/* 174:    */       {
/* 175:503 */         Peer localPeer = (Peer)peers.get(localEntry.getValue());
/* 176:504 */         if ((localPeer != null) && (localPeer.getState() == Peer.State.CONNECTED) && (localPeer.shareAddress()) && (!localPeer.isBlacklisted()) && (localPeer.getVersion() != null) && (localPeer.getVersion().startsWith(dumpPeersVersion))) {
/* 177:506 */           localStringBuilder.append("('").append((String)localEntry.getKey()).append("'), ");
/* 178:    */         }
/* 179:    */       }
/* 180:509 */       Logger.logShutdownMessage(localStringBuilder.toString());
/* 181:    */     }
/* 182:511 */     ThreadPool.shutdownExecutor(sendToPeersService);
/* 183:    */   }
/* 184:    */   
/* 185:    */   public static boolean addListener(Listener<Peer> paramListener, Event paramEvent)
/* 186:    */   {
/* 187:516 */     return listeners.addListener(paramListener, paramEvent);
/* 188:    */   }
/* 189:    */   
/* 190:    */   public static boolean removeListener(Listener<Peer> paramListener, Event paramEvent)
/* 191:    */   {
/* 192:520 */     return listeners.removeListener(paramListener, paramEvent);
/* 193:    */   }
/* 194:    */   
/* 195:    */   static void notifyListeners(Peer paramPeer, Event paramEvent)
/* 196:    */   {
/* 197:524 */     listeners.notify(paramPeer, paramEvent);
/* 198:    */   }
/* 199:    */   
/* 200:    */   public static Collection<? extends Peer> getAllPeers()
/* 201:    */   {
/* 202:528 */     return allPeers;
/* 203:    */   }
/* 204:    */   
/* 205:    */   public static Collection<? extends Peer> getActivePeers()
/* 206:    */   {
/* 207:532 */     ArrayList localArrayList = new ArrayList();
/* 208:533 */     for (PeerImpl localPeerImpl : peers.values()) {
/* 209:534 */       if (localPeerImpl.getState() != Peer.State.NON_CONNECTED) {
/* 210:535 */         localArrayList.add(localPeerImpl);
/* 211:    */       }
/* 212:    */     }
/* 213:538 */     return localArrayList;
/* 214:    */   }
/* 215:    */   
/* 216:    */   public static Collection<? extends Peer> getPeers(Peer.State paramState)
/* 217:    */   {
/* 218:542 */     ArrayList localArrayList = new ArrayList();
/* 219:543 */     for (PeerImpl localPeerImpl : peers.values()) {
/* 220:544 */       if (localPeerImpl.getState() == paramState) {
/* 221:545 */         localArrayList.add(localPeerImpl);
/* 222:    */       }
/* 223:    */     }
/* 224:548 */     return localArrayList;
/* 225:    */   }
/* 226:    */   
/* 227:    */   public static Peer getPeer(String paramString)
/* 228:    */   {
/* 229:552 */     return (Peer)peers.get(paramString);
/* 230:    */   }
/* 231:    */   
/* 232:    */   /* Error */
/* 233:    */   public static Peer addPeer(String paramString)
/* 234:    */   {
/* 235:    */     // Byte code:
/* 236:    */     //   0: aload_0
/* 237:    */     //   1: ifnonnull +5 -> 6
/* 238:    */     //   4: aconst_null
/* 239:    */     //   5: areturn
/* 240:    */     //   6: aload_0
/* 241:    */     //   7: invokevirtual 54	java/lang/String:trim	()Ljava/lang/String;
/* 242:    */     //   10: astore_0
/* 243:    */     //   11: getstatic 7	nxt/peer/Peers:peers	Ljava/util/concurrent/ConcurrentMap;
/* 244:    */     //   14: aload_0
/* 245:    */     //   15: invokeinterface 26 2 0
/* 246:    */     //   20: checkcast 27	nxt/peer/Peer
/* 247:    */     //   23: dup
/* 248:    */     //   24: astore_1
/* 249:    */     //   25: ifnull +5 -> 30
/* 250:    */     //   28: aload_1
/* 251:    */     //   29: areturn
/* 252:    */     //   30: getstatic 19	nxt/peer/Peers:announcedAddresses	Ljava/util/concurrent/ConcurrentMap;
/* 253:    */     //   33: aload_0
/* 254:    */     //   34: invokeinterface 26 2 0
/* 255:    */     //   39: checkcast 37	java/lang/String
/* 256:    */     //   42: dup
/* 257:    */     //   43: astore_2
/* 258:    */     //   44: ifnull +22 -> 66
/* 259:    */     //   47: getstatic 7	nxt/peer/Peers:peers	Ljava/util/concurrent/ConcurrentMap;
/* 260:    */     //   50: aload_2
/* 261:    */     //   51: invokeinterface 26 2 0
/* 262:    */     //   56: checkcast 27	nxt/peer/Peer
/* 263:    */     //   59: dup
/* 264:    */     //   60: astore_1
/* 265:    */     //   61: ifnull +5 -> 66
/* 266:    */     //   64: aload_1
/* 267:    */     //   65: areturn
/* 268:    */     //   66: new 55	java/net/URI
/* 269:    */     //   69: dup
/* 270:    */     //   70: new 17	java/lang/StringBuilder
/* 271:    */     //   73: dup
/* 272:    */     //   74: invokespecial 18	java/lang/StringBuilder:<init>	()V
/* 273:    */     //   77: ldc 56
/* 274:    */     //   79: invokevirtual 35	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 275:    */     //   82: aload_0
/* 276:    */     //   83: invokevirtual 35	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 277:    */     //   86: invokevirtual 39	java/lang/StringBuilder:toString	()Ljava/lang/String;
/* 278:    */     //   89: invokespecial 57	java/net/URI:<init>	(Ljava/lang/String;)V
/* 279:    */     //   92: astore_3
/* 280:    */     //   93: aload_3
/* 281:    */     //   94: invokevirtual 58	java/net/URI:getHost	()Ljava/lang/String;
/* 282:    */     //   97: astore 4
/* 283:    */     //   99: getstatic 7	nxt/peer/Peers:peers	Ljava/util/concurrent/ConcurrentMap;
/* 284:    */     //   102: aload 4
/* 285:    */     //   104: invokeinterface 26 2 0
/* 286:    */     //   109: checkcast 27	nxt/peer/Peer
/* 287:    */     //   112: dup
/* 288:    */     //   113: astore_1
/* 289:    */     //   114: ifnull +5 -> 119
/* 290:    */     //   117: aload_1
/* 291:    */     //   118: areturn
/* 292:    */     //   119: aload 4
/* 293:    */     //   121: invokestatic 59	java/net/InetAddress:getByName	(Ljava/lang/String;)Ljava/net/InetAddress;
/* 294:    */     //   124: astore 5
/* 295:    */     //   126: aload 5
/* 296:    */     //   128: invokevirtual 60	java/net/InetAddress:getHostAddress	()Ljava/lang/String;
/* 297:    */     //   131: aload_0
/* 298:    */     //   132: invokestatic 61	nxt/peer/Peers:addPeer	(Ljava/lang/String;Ljava/lang/String;)Lnxt/peer/PeerImpl;
/* 299:    */     //   135: areturn
/* 300:    */     //   136: astore_3
/* 301:    */     //   137: aconst_null
/* 302:    */     //   138: areturn
/* 303:    */     // Line number table:
/* 304:    */     //   Java source line #556	-> byte code offset #0
/* 305:    */     //   Java source line #557	-> byte code offset #4
/* 306:    */     //   Java source line #559	-> byte code offset #6
/* 307:    */     //   Java source line #561	-> byte code offset #11
/* 308:    */     //   Java source line #562	-> byte code offset #28
/* 309:    */     //   Java source line #565	-> byte code offset #30
/* 310:    */     //   Java source line #566	-> byte code offset #64
/* 311:    */     //   Java source line #569	-> byte code offset #66
/* 312:    */     //   Java source line #570	-> byte code offset #93
/* 313:    */     //   Java source line #571	-> byte code offset #99
/* 314:    */     //   Java source line #572	-> byte code offset #117
/* 315:    */     //   Java source line #574	-> byte code offset #119
/* 316:    */     //   Java source line #575	-> byte code offset #126
/* 317:    */     //   Java source line #576	-> byte code offset #136
/* 318:    */     //   Java source line #578	-> byte code offset #137
/* 319:    */     // Local variable table:
/* 320:    */     //   start	length	slot	name	signature
/* 321:    */     //   0	139	0	paramString	String
/* 322:    */     //   24	94	1	localPeer	Peer
/* 323:    */     //   43	8	2	str1	String
/* 324:    */     //   92	2	3	localURI	java.net.URI
/* 325:    */     //   136	1	3	localURISyntaxException	java.net.URISyntaxException
/* 326:    */     //   97	23	4	str2	String
/* 327:    */     //   124	3	5	localInetAddress	java.net.InetAddress
/* 328:    */     // Exception table:
/* 329:    */     //   from	to	target	type
/* 330:    */     //   66	118	136	java/net/URISyntaxException
/* 331:    */     //   66	118	136	java/net/UnknownHostException
/* 332:    */     //   119	135	136	java/net/URISyntaxException
/* 333:    */     //   119	135	136	java/net/UnknownHostException
/* 334:    */   }
/* 335:    */   
/* 336:    */   static PeerImpl addPeer(String paramString1, String paramString2)
/* 337:    */   {
/* 338:585 */     String str1 = paramString1;
/* 339:586 */     if (str1.split(":").length > 2) {
/* 340:587 */       str1 = "[" + str1 + "]";
/* 341:    */     }
/* 342:590 */     if ((localPeerImpl = (PeerImpl)peers.get(str1)) != null) {
/* 343:591 */       return localPeerImpl;
/* 344:    */     }
/* 345:593 */     String str2 = normalizeHostAndPort(str1);
/* 346:594 */     if (str2 == null) {
/* 347:595 */       return null;
/* 348:    */     }
/* 349:597 */     if ((localPeerImpl = (PeerImpl)peers.get(str2)) != null) {
/* 350:598 */       return localPeerImpl;
/* 351:    */     }
/* 352:601 */     String str3 = paramString1.equals(paramString2) ? str2 : normalizeHostAndPort(paramString2);
/* 353:603 */     if ((myAddress != null) && (myAddress.length() > 0) && (myAddress.equalsIgnoreCase(str3))) {
/* 354:604 */       return null;
/* 355:    */     }
/* 356:607 */     PeerImpl localPeerImpl = new PeerImpl(str2, str3);
/* 357:608 */     if ((Constants.isTestnet) && (localPeerImpl.getPort() > 0) && (localPeerImpl.getPort() != 7123))
/* 358:    */     {
/* 359:609 */       Logger.logDebugMessage("Peer " + str2 + " on testnet is not using port " + 7123 + ", ignoring");
/* 360:610 */       return null;
/* 361:    */     }
/* 362:612 */     peers.put(str2, localPeerImpl);
/* 363:613 */     if (paramString2 != null) {
/* 364:614 */       updateAddress(localPeerImpl);
/* 365:    */     }
/* 366:616 */     listeners.notify(localPeerImpl, Event.NEW_PEER);
/* 367:617 */     return localPeerImpl;
/* 368:    */   }
/* 369:    */   
/* 370:    */   static PeerImpl removePeer(PeerImpl paramPeerImpl)
/* 371:    */   {
/* 372:621 */     if (paramPeerImpl.getAnnouncedAddress() != null) {
/* 373:622 */       announcedAddresses.remove(paramPeerImpl.getAnnouncedAddress());
/* 374:    */     }
/* 375:624 */     return (PeerImpl)peers.remove(paramPeerImpl.getPeerAddress());
/* 376:    */   }
/* 377:    */   
/* 378:    */   static void updateAddress(PeerImpl paramPeerImpl)
/* 379:    */   {
/* 380:628 */     String str = (String)announcedAddresses.put(paramPeerImpl.getAnnouncedAddress(), paramPeerImpl.getPeerAddress());
/* 381:629 */     if ((str != null) && (!paramPeerImpl.getPeerAddress().equals(str)))
/* 382:    */     {
/* 383:632 */       Peer localPeer = (Peer)peers.remove(str);
/* 384:633 */       if (localPeer != null) {
/* 385:634 */         notifyListeners(localPeer, Event.REMOVE);
/* 386:    */       }
/* 387:    */     }
/* 388:    */   }
/* 389:    */   
/* 390:    */   public static void sendToSomePeers(Block paramBlock)
/* 391:    */   {
/* 392:640 */     JSONObject localJSONObject = paramBlock.getJSONObject();
/* 393:641 */     localJSONObject.put("requestType", "processBlock");
/* 394:642 */     sendToSomePeers(localJSONObject);
/* 395:    */   }
/* 396:    */   
/* 397:    */   public static void sendToSomePeers(List<Transaction> paramList)
/* 398:    */   {
/* 399:646 */     JSONObject localJSONObject = new JSONObject();
/* 400:647 */     JSONArray localJSONArray = new JSONArray();
/* 401:648 */     for (Transaction localTransaction : paramList) {
/* 402:649 */       localJSONArray.add(localTransaction.getJSONObject());
/* 403:    */     }
/* 404:651 */     localJSONObject.put("requestType", "processTransactions");
/* 405:652 */     localJSONObject.put("transactions", localJSONArray);
/* 406:653 */     sendToSomePeers(localJSONObject);
/* 407:    */   }
/* 408:    */   
/* 409:    */   private static void sendToSomePeers(JSONObject paramJSONObject)
/* 410:    */   {
/* 411:658 */     final JSONStreamAware localJSONStreamAware = JSON.prepareRequest(paramJSONObject);
/* 412:    */     
/* 413:660 */     int i = 0;
/* 414:661 */     ArrayList localArrayList = new ArrayList();
/* 415:662 */     for (Peer localPeer : peers.values()) {
/* 416:664 */       if ((!enableHallmarkProtection) || (localPeer.getWeight() >= pushThreshold))
/* 417:    */       {
/* 418:    */         Object localObject;
/* 419:668 */         if ((!localPeer.isBlacklisted()) && (localPeer.getState() == Peer.State.CONNECTED) && (localPeer.getAnnouncedAddress() != null))
/* 420:    */         {
/* 421:669 */           localObject = sendToPeersService.submit(new Callable()
/* 422:    */           {
/* 423:    */             public JSONObject call()
/* 424:    */             {
/* 425:672 */               return this.val$peer.send(localJSONStreamAware);
/* 426:    */             }
/* 427:674 */           });
/* 428:675 */           localArrayList.add(localObject);
/* 429:    */         }
/* 430:677 */         if (localArrayList.size() >= sendToPeersLimit - i)
/* 431:    */         {
/* 432:678 */           for (localObject = localArrayList.iterator(); ((Iterator)localObject).hasNext();)
/* 433:    */           {
/* 434:678 */             Future localFuture = (Future)((Iterator)localObject).next();
/* 435:    */             try
/* 436:    */             {
/* 437:680 */               JSONObject localJSONObject = (JSONObject)localFuture.get();
/* 438:681 */               if ((localJSONObject != null) && (localJSONObject.get("error") == null)) {
/* 439:682 */                 i++;
/* 440:    */               }
/* 441:    */             }
/* 442:    */             catch (InterruptedException localInterruptedException)
/* 443:    */             {
/* 444:685 */               Thread.currentThread().interrupt();
/* 445:    */             }
/* 446:    */             catch (ExecutionException localExecutionException)
/* 447:    */             {
/* 448:687 */               Logger.logDebugMessage("Error in sendToSomePeers", localExecutionException);
/* 449:    */             }
/* 450:    */           }
/* 451:691 */           localArrayList.clear();
/* 452:    */         }
/* 453:693 */         if (i >= sendToPeersLimit) {
/* 454:694 */           return;
/* 455:    */         }
/* 456:    */       }
/* 457:    */     }
/* 458:    */   }
/* 459:    */   
/* 460:    */   public static Peer getAnyPeer(Peer.State paramState, boolean paramBoolean)
/* 461:    */   {
/* 462:703 */     ArrayList localArrayList = new ArrayList();
/* 463:704 */     for (Peer localPeer1 : peers.values()) {
/* 464:705 */       if ((!localPeer1.isBlacklisted()) && (localPeer1.getState() == paramState) && (localPeer1.shareAddress()) && ((!paramBoolean) || (!enableHallmarkProtection) || (localPeer1.getWeight() >= pullThreshold))) {
/* 465:707 */         localArrayList.add(localPeer1);
/* 466:    */       }
/* 467:    */     }
/* 468:    */     long l2;
/* 469:711 */     if (localArrayList.size() > 0)
/* 470:    */     {
/* 471:712 */       if (!enableHallmarkProtection) {
/* 472:713 */         return (Peer)localArrayList.get(ThreadLocalRandom.current().nextInt(localArrayList.size()));
/* 473:    */       }
/* 474:716 */       long l1 = 0L;
/* 475:717 */       for (Peer localPeer2 : localArrayList)
/* 476:    */       {
/* 477:718 */         long l3 = localPeer2.getWeight();
/* 478:719 */         if (l3 == 0L) {
/* 479:720 */           l3 = 1L;
/* 480:    */         }
/* 481:722 */         l1 += l3;
/* 482:    */       }
/* 483:725 */       l2 = ThreadLocalRandom.current().nextLong(l1);
/* 484:726 */       for (Peer localPeer3 : localArrayList)
/* 485:    */       {
/* 486:727 */         long l4 = localPeer3.getWeight();
/* 487:728 */         if (l4 == 0L) {
/* 488:729 */           l4 = 1L;
/* 489:    */         }
/* 490:731 */         if (l2 -= l4 < 0L) {
/* 491:732 */           return localPeer3;
/* 492:    */         }
/* 493:    */       }
/* 494:    */     }
/* 495:736 */     return null;
/* 496:    */   }
/* 497:    */   
/* 498:    */   /* Error */
/* 499:    */   static String normalizeHostAndPort(String paramString)
/* 500:    */   {
/* 501:    */     // Byte code:
/* 502:    */     //   0: aload_0
/* 503:    */     //   1: ifnonnull +5 -> 6
/* 504:    */     //   4: aconst_null
/* 505:    */     //   5: areturn
/* 506:    */     //   6: new 55	java/net/URI
/* 507:    */     //   9: dup
/* 508:    */     //   10: new 17	java/lang/StringBuilder
/* 509:    */     //   13: dup
/* 510:    */     //   14: invokespecial 18	java/lang/StringBuilder:<init>	()V
/* 511:    */     //   17: ldc 56
/* 512:    */     //   19: invokevirtual 35	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 513:    */     //   22: aload_0
/* 514:    */     //   23: invokevirtual 54	java/lang/String:trim	()Ljava/lang/String;
/* 515:    */     //   26: invokevirtual 35	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 516:    */     //   29: invokevirtual 39	java/lang/StringBuilder:toString	()Ljava/lang/String;
/* 517:    */     //   32: invokespecial 57	java/net/URI:<init>	(Ljava/lang/String;)V
/* 518:    */     //   35: astore_1
/* 519:    */     //   36: aload_1
/* 520:    */     //   37: invokevirtual 58	java/net/URI:getHost	()Ljava/lang/String;
/* 521:    */     //   40: astore_2
/* 522:    */     //   41: aload_2
/* 523:    */     //   42: ifnull +39 -> 81
/* 524:    */     //   45: aload_2
/* 525:    */     //   46: ldc 130
/* 526:    */     //   48: invokevirtual 69	java/lang/String:equals	(Ljava/lang/Object;)Z
/* 527:    */     //   51: ifne +30 -> 81
/* 528:    */     //   54: aload_2
/* 529:    */     //   55: ldc 131
/* 530:    */     //   57: invokevirtual 69	java/lang/String:equals	(Ljava/lang/Object;)Z
/* 531:    */     //   60: ifne +21 -> 81
/* 532:    */     //   63: aload_2
/* 533:    */     //   64: ldc 132
/* 534:    */     //   66: invokevirtual 69	java/lang/String:equals	(Ljava/lang/Object;)Z
/* 535:    */     //   69: ifne +12 -> 81
/* 536:    */     //   72: aload_2
/* 537:    */     //   73: ldc 133
/* 538:    */     //   75: invokevirtual 69	java/lang/String:equals	(Ljava/lang/Object;)Z
/* 539:    */     //   78: ifeq +5 -> 83
/* 540:    */     //   81: aconst_null
/* 541:    */     //   82: areturn
/* 542:    */     //   83: aload_2
/* 543:    */     //   84: invokestatic 59	java/net/InetAddress:getByName	(Ljava/lang/String;)Ljava/net/InetAddress;
/* 544:    */     //   87: astore_3
/* 545:    */     //   88: aload_3
/* 546:    */     //   89: invokevirtual 134	java/net/InetAddress:isAnyLocalAddress	()Z
/* 547:    */     //   92: ifne +17 -> 109
/* 548:    */     //   95: aload_3
/* 549:    */     //   96: invokevirtual 135	java/net/InetAddress:isLoopbackAddress	()Z
/* 550:    */     //   99: ifne +10 -> 109
/* 551:    */     //   102: aload_3
/* 552:    */     //   103: invokevirtual 136	java/net/InetAddress:isLinkLocalAddress	()Z
/* 553:    */     //   106: ifeq +5 -> 111
/* 554:    */     //   109: aconst_null
/* 555:    */     //   110: areturn
/* 556:    */     //   111: aload_1
/* 557:    */     //   112: invokevirtual 137	java/net/URI:getPort	()I
/* 558:    */     //   115: istore 4
/* 559:    */     //   117: iload 4
/* 560:    */     //   119: iconst_m1
/* 561:    */     //   120: if_icmpne +7 -> 127
/* 562:    */     //   123: aload_2
/* 563:    */     //   124: goto +27 -> 151
/* 564:    */     //   127: new 17	java/lang/StringBuilder
/* 565:    */     //   130: dup
/* 566:    */     //   131: invokespecial 18	java/lang/StringBuilder:<init>	()V
/* 567:    */     //   134: aload_2
/* 568:    */     //   135: invokevirtual 35	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 569:    */     //   138: bipush 58
/* 570:    */     //   140: invokevirtual 138	java/lang/StringBuilder:append	(C)Ljava/lang/StringBuilder;
/* 571:    */     //   143: iload 4
/* 572:    */     //   145: invokevirtual 78	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
/* 573:    */     //   148: invokevirtual 39	java/lang/StringBuilder:toString	()Ljava/lang/String;
/* 574:    */     //   151: areturn
/* 575:    */     //   152: astore_1
/* 576:    */     //   153: aconst_null
/* 577:    */     //   154: areturn
/* 578:    */     // Line number table:
/* 579:    */     //   Java source line #741	-> byte code offset #0
/* 580:    */     //   Java source line #742	-> byte code offset #4
/* 581:    */     //   Java source line #744	-> byte code offset #6
/* 582:    */     //   Java source line #745	-> byte code offset #36
/* 583:    */     //   Java source line #746	-> byte code offset #41
/* 584:    */     //   Java source line #748	-> byte code offset #81
/* 585:    */     //   Java source line #750	-> byte code offset #83
/* 586:    */     //   Java source line #751	-> byte code offset #88
/* 587:    */     //   Java source line #753	-> byte code offset #109
/* 588:    */     //   Java source line #755	-> byte code offset #111
/* 589:    */     //   Java source line #756	-> byte code offset #117
/* 590:    */     //   Java source line #757	-> byte code offset #152
/* 591:    */     //   Java source line #758	-> byte code offset #153
/* 592:    */     // Local variable table:
/* 593:    */     //   start	length	slot	name	signature
/* 594:    */     //   0	155	0	paramString	String
/* 595:    */     //   35	77	1	localURI	java.net.URI
/* 596:    */     //   152	1	1	localURISyntaxException	java.net.URISyntaxException
/* 597:    */     //   40	95	2	str	String
/* 598:    */     //   87	16	3	localInetAddress	java.net.InetAddress
/* 599:    */     //   115	29	4	i	int
/* 600:    */     // Exception table:
/* 601:    */     //   from	to	target	type
/* 602:    */     //   0	5	152	java/net/URISyntaxException
/* 603:    */     //   0	5	152	java/net/UnknownHostException
/* 604:    */     //   6	82	152	java/net/URISyntaxException
/* 605:    */     //   6	82	152	java/net/UnknownHostException
/* 606:    */     //   83	110	152	java/net/URISyntaxException
/* 607:    */     //   83	110	152	java/net/UnknownHostException
/* 608:    */     //   111	151	152	java/net/URISyntaxException
/* 609:    */     //   111	151	152	java/net/UnknownHostException
/* 610:    */   }
/* 611:    */   
/* 612:    */   private static int getNumberOfConnectedPublicPeers()
/* 613:    */   {
/* 614:763 */     int i = 0;
/* 615:764 */     for (Peer localPeer : peers.values()) {
/* 616:765 */       if ((localPeer.getState() == Peer.State.CONNECTED) && (localPeer.getAnnouncedAddress() != null) && ((localPeer.getWeight() > 0) || (!enableHallmarkProtection))) {
/* 617:767 */         i++;
/* 618:    */       }
/* 619:    */     }
/* 620:770 */     return i;
/* 621:    */   }
/* 622:    */   
/* 623:    */   /* Error */
/* 624:    */   static
/* 625:    */   {
/* 626:    */     // Byte code:
/* 627:    */     //   0: new 140	nxt/util/Listeners
/* 628:    */     //   3: dup
/* 629:    */     //   4: invokespecial 141	nxt/util/Listeners:<init>	()V
/* 630:    */     //   7: putstatic 1	nxt/peer/Peers:listeners	Lnxt/util/Listeners;
/* 631:    */     //   10: new 142	java/util/concurrent/ConcurrentHashMap
/* 632:    */     //   13: dup
/* 633:    */     //   14: invokespecial 143	java/util/concurrent/ConcurrentHashMap:<init>	()V
/* 634:    */     //   17: putstatic 7	nxt/peer/Peers:peers	Ljava/util/concurrent/ConcurrentMap;
/* 635:    */     //   20: new 142	java/util/concurrent/ConcurrentHashMap
/* 636:    */     //   23: dup
/* 637:    */     //   24: invokespecial 143	java/util/concurrent/ConcurrentHashMap:<init>	()V
/* 638:    */     //   27: putstatic 19	nxt/peer/Peers:announcedAddresses	Ljava/util/concurrent/ConcurrentMap;
/* 639:    */     //   30: getstatic 7	nxt/peer/Peers:peers	Ljava/util/concurrent/ConcurrentMap;
/* 640:    */     //   33: invokeinterface 48 1 0
/* 641:    */     //   38: invokestatic 144	java/util/Collections:unmodifiableCollection	(Ljava/util/Collection;)Ljava/util/Collection;
/* 642:    */     //   41: putstatic 45	nxt/peer/Peers:allPeers	Ljava/util/Collection;
/* 643:    */     //   44: bipush 10
/* 644:    */     //   46: invokestatic 145	java/util/concurrent/Executors:newFixedThreadPool	(I)Ljava/util/concurrent/ExecutorService;
/* 645:    */     //   49: putstatic 9	nxt/peer/Peers:sendToPeersService	Ljava/util/concurrent/ExecutorService;
/* 646:    */     //   52: ldc 146
/* 647:    */     //   54: invokestatic 147	nxt/Nxt:getStringProperty	(Ljava/lang/String;)Ljava/lang/String;
/* 648:    */     //   57: putstatic 148	nxt/peer/Peers:myPlatform	Ljava/lang/String;
/* 649:    */     //   60: ldc 149
/* 650:    */     //   62: invokestatic 147	nxt/Nxt:getStringProperty	(Ljava/lang/String;)Ljava/lang/String;
/* 651:    */     //   65: putstatic 70	nxt/peer/Peers:myAddress	Ljava/lang/String;
/* 652:    */     //   68: getstatic 70	nxt/peer/Peers:myAddress	Ljava/lang/String;
/* 653:    */     //   71: ifnull +30 -> 101
/* 654:    */     //   74: getstatic 70	nxt/peer/Peers:myAddress	Ljava/lang/String;
/* 655:    */     //   77: ldc 150
/* 656:    */     //   79: invokevirtual 151	java/lang/String:endsWith	(Ljava/lang/String;)Z
/* 657:    */     //   82: ifeq +19 -> 101
/* 658:    */     //   85: getstatic 74	nxt/Constants:isTestnet	Z
/* 659:    */     //   88: ifne +13 -> 101
/* 660:    */     //   91: new 152	java/lang/RuntimeException
/* 661:    */     //   94: dup
/* 662:    */     //   95: ldc 153
/* 663:    */     //   97: invokespecial 154	java/lang/RuntimeException:<init>	(Ljava/lang/String;)V
/* 664:    */     //   100: athrow
/* 665:    */     //   101: ldc 155
/* 666:    */     //   103: invokestatic 156	nxt/Nxt:getIntProperty	(Ljava/lang/String;)I
/* 667:    */     //   106: putstatic 5	nxt/peer/Peers:myPeerServerPort	I
/* 668:    */     //   109: getstatic 5	nxt/peer/Peers:myPeerServerPort	I
/* 669:    */     //   112: sipush 7123
/* 670:    */     //   115: if_icmpne +19 -> 134
/* 671:    */     //   118: getstatic 74	nxt/Constants:isTestnet	Z
/* 672:    */     //   121: ifne +13 -> 134
/* 673:    */     //   124: new 152	java/lang/RuntimeException
/* 674:    */     //   127: dup
/* 675:    */     //   128: ldc 153
/* 676:    */     //   130: invokespecial 154	java/lang/RuntimeException:<init>	(Ljava/lang/String;)V
/* 677:    */     //   133: athrow
/* 678:    */     //   134: ldc 157
/* 679:    */     //   136: invokestatic 158	nxt/Nxt:getBooleanProperty	(Ljava/lang/String;)Ljava/lang/Boolean;
/* 680:    */     //   139: invokevirtual 159	java/lang/Boolean:booleanValue	()Z
/* 681:    */     //   142: ifeq +13 -> 155
/* 682:    */     //   145: getstatic 160	nxt/Constants:isOffline	Z
/* 683:    */     //   148: ifne +7 -> 155
/* 684:    */     //   151: iconst_1
/* 685:    */     //   152: goto +4 -> 156
/* 686:    */     //   155: iconst_0
/* 687:    */     //   156: putstatic 6	nxt/peer/Peers:shareMyAddress	Z
/* 688:    */     //   159: ldc 161
/* 689:    */     //   161: invokestatic 147	nxt/Nxt:getStringProperty	(Ljava/lang/String;)Ljava/lang/String;
/* 690:    */     //   164: putstatic 162	nxt/peer/Peers:myHallmark	Ljava/lang/String;
/* 691:    */     //   167: getstatic 162	nxt/peer/Peers:myHallmark	Ljava/lang/String;
/* 692:    */     //   170: ifnull +148 -> 318
/* 693:    */     //   173: getstatic 162	nxt/peer/Peers:myHallmark	Ljava/lang/String;
/* 694:    */     //   176: invokevirtual 71	java/lang/String:length	()I
/* 695:    */     //   179: ifle +139 -> 318
/* 696:    */     //   182: getstatic 162	nxt/peer/Peers:myHallmark	Ljava/lang/String;
/* 697:    */     //   185: invokestatic 163	nxt/peer/Hallmark:parseHallmark	(Ljava/lang/String;)Lnxt/peer/Hallmark;
/* 698:    */     //   188: astore_0
/* 699:    */     //   189: aload_0
/* 700:    */     //   190: invokevirtual 164	nxt/peer/Hallmark:isValid	()Z
/* 701:    */     //   193: ifeq +9 -> 202
/* 702:    */     //   196: getstatic 70	nxt/peer/Peers:myAddress	Ljava/lang/String;
/* 703:    */     //   199: ifnonnull +11 -> 210
/* 704:    */     //   202: new 152	java/lang/RuntimeException
/* 705:    */     //   205: dup
/* 706:    */     //   206: invokespecial 165	java/lang/RuntimeException:<init>	()V
/* 707:    */     //   209: athrow
/* 708:    */     //   210: new 55	java/net/URI
/* 709:    */     //   213: dup
/* 710:    */     //   214: new 17	java/lang/StringBuilder
/* 711:    */     //   217: dup
/* 712:    */     //   218: invokespecial 18	java/lang/StringBuilder:<init>	()V
/* 713:    */     //   221: ldc 56
/* 714:    */     //   223: invokevirtual 35	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 715:    */     //   226: getstatic 70	nxt/peer/Peers:myAddress	Ljava/lang/String;
/* 716:    */     //   229: invokevirtual 54	java/lang/String:trim	()Ljava/lang/String;
/* 717:    */     //   232: invokevirtual 35	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 718:    */     //   235: invokevirtual 39	java/lang/StringBuilder:toString	()Ljava/lang/String;
/* 719:    */     //   238: invokespecial 57	java/net/URI:<init>	(Ljava/lang/String;)V
/* 720:    */     //   241: astore_1
/* 721:    */     //   242: aload_1
/* 722:    */     //   243: invokevirtual 58	java/net/URI:getHost	()Ljava/lang/String;
/* 723:    */     //   246: astore_2
/* 724:    */     //   247: aload_0
/* 725:    */     //   248: invokevirtual 166	nxt/peer/Hallmark:getHost	()Ljava/lang/String;
/* 726:    */     //   251: aload_2
/* 727:    */     //   252: invokevirtual 69	java/lang/String:equals	(Ljava/lang/Object;)Z
/* 728:    */     //   255: ifne +11 -> 266
/* 729:    */     //   258: new 152	java/lang/RuntimeException
/* 730:    */     //   261: dup
/* 731:    */     //   262: invokespecial 165	java/lang/RuntimeException:<init>	()V
/* 732:    */     //   265: athrow
/* 733:    */     //   266: goto +52 -> 318
/* 734:    */     //   269: astore_0
/* 735:    */     //   270: new 17	java/lang/StringBuilder
/* 736:    */     //   273: dup
/* 737:    */     //   274: invokespecial 18	java/lang/StringBuilder:<init>	()V
/* 738:    */     //   277: ldc 167
/* 739:    */     //   279: invokevirtual 35	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 740:    */     //   282: getstatic 162	nxt/peer/Peers:myHallmark	Ljava/lang/String;
/* 741:    */     //   285: invokevirtual 35	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 742:    */     //   288: ldc 168
/* 743:    */     //   290: invokevirtual 35	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 744:    */     //   293: getstatic 70	nxt/peer/Peers:myAddress	Ljava/lang/String;
/* 745:    */     //   296: invokevirtual 35	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 746:    */     //   299: invokevirtual 39	java/lang/StringBuilder:toString	()Ljava/lang/String;
/* 747:    */     //   302: invokestatic 169	nxt/util/Logger:logMessage	(Ljava/lang/String;)V
/* 748:    */     //   305: new 152	java/lang/RuntimeException
/* 749:    */     //   308: dup
/* 750:    */     //   309: aload_0
/* 751:    */     //   310: invokevirtual 170	java/lang/Exception:toString	()Ljava/lang/String;
/* 752:    */     //   313: aload_0
/* 753:    */     //   314: invokespecial 171	java/lang/RuntimeException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
/* 754:    */     //   317: athrow
/* 755:    */     //   318: new 94	org/json/simple/JSONObject
/* 756:    */     //   321: dup
/* 757:    */     //   322: invokespecial 95	org/json/simple/JSONObject:<init>	()V
/* 758:    */     //   325: astore_0
/* 759:    */     //   326: getstatic 70	nxt/peer/Peers:myAddress	Ljava/lang/String;
/* 760:    */     //   329: ifnull +188 -> 517
/* 761:    */     //   332: getstatic 70	nxt/peer/Peers:myAddress	Ljava/lang/String;
/* 762:    */     //   335: invokevirtual 71	java/lang/String:length	()I
/* 763:    */     //   338: ifle +179 -> 517
/* 764:    */     //   341: new 55	java/net/URI
/* 765:    */     //   344: dup
/* 766:    */     //   345: new 17	java/lang/StringBuilder
/* 767:    */     //   348: dup
/* 768:    */     //   349: invokespecial 18	java/lang/StringBuilder:<init>	()V
/* 769:    */     //   352: ldc 56
/* 770:    */     //   354: invokevirtual 35	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 771:    */     //   357: getstatic 70	nxt/peer/Peers:myAddress	Ljava/lang/String;
/* 772:    */     //   360: invokevirtual 54	java/lang/String:trim	()Ljava/lang/String;
/* 773:    */     //   363: invokevirtual 35	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 774:    */     //   366: invokevirtual 39	java/lang/StringBuilder:toString	()Ljava/lang/String;
/* 775:    */     //   369: invokespecial 57	java/net/URI:<init>	(Ljava/lang/String;)V
/* 776:    */     //   372: astore_1
/* 777:    */     //   373: aload_1
/* 778:    */     //   374: invokevirtual 58	java/net/URI:getHost	()Ljava/lang/String;
/* 779:    */     //   377: astore_2
/* 780:    */     //   378: aload_1
/* 781:    */     //   379: invokevirtual 137	java/net/URI:getPort	()I
/* 782:    */     //   382: istore_3
/* 783:    */     //   383: getstatic 74	nxt/Constants:isTestnet	Z
/* 784:    */     //   386: ifne +82 -> 468
/* 785:    */     //   389: iload_3
/* 786:    */     //   390: iflt +16 -> 406
/* 787:    */     //   393: aload_0
/* 788:    */     //   394: ldc 172
/* 789:    */     //   396: getstatic 70	nxt/peer/Peers:myAddress	Ljava/lang/String;
/* 790:    */     //   399: invokevirtual 92	org/json/simple/JSONObject:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
/* 791:    */     //   402: pop
/* 792:    */     //   403: goto +73 -> 476
/* 793:    */     //   406: aload_0
/* 794:    */     //   407: ldc 172
/* 795:    */     //   409: new 17	java/lang/StringBuilder
/* 796:    */     //   412: dup
/* 797:    */     //   413: invokespecial 18	java/lang/StringBuilder:<init>	()V
/* 798:    */     //   416: aload_2
/* 799:    */     //   417: invokevirtual 35	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 800:    */     //   420: getstatic 5	nxt/peer/Peers:myPeerServerPort	I
/* 801:    */     //   423: sipush 8123
/* 802:    */     //   426: if_icmpeq +27 -> 453
/* 803:    */     //   429: new 17	java/lang/StringBuilder
/* 804:    */     //   432: dup
/* 805:    */     //   433: invokespecial 18	java/lang/StringBuilder:<init>	()V
/* 806:    */     //   436: ldc 64
/* 807:    */     //   438: invokevirtual 35	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 808:    */     //   441: getstatic 5	nxt/peer/Peers:myPeerServerPort	I
/* 809:    */     //   444: invokevirtual 78	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
/* 810:    */     //   447: invokevirtual 39	java/lang/StringBuilder:toString	()Ljava/lang/String;
/* 811:    */     //   450: goto +5 -> 455
/* 812:    */     //   453: ldc 130
/* 813:    */     //   455: invokevirtual 35	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 814:    */     //   458: invokevirtual 39	java/lang/StringBuilder:toString	()Ljava/lang/String;
/* 815:    */     //   461: invokevirtual 92	org/json/simple/JSONObject:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
/* 816:    */     //   464: pop
/* 817:    */     //   465: goto +11 -> 476
/* 818:    */     //   468: aload_0
/* 819:    */     //   469: ldc 172
/* 820:    */     //   471: aload_2
/* 821:    */     //   472: invokevirtual 92	org/json/simple/JSONObject:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
/* 822:    */     //   475: pop
/* 823:    */     //   476: goto +41 -> 517
/* 824:    */     //   479: astore_1
/* 825:    */     //   480: new 17	java/lang/StringBuilder
/* 826:    */     //   483: dup
/* 827:    */     //   484: invokespecial 18	java/lang/StringBuilder:<init>	()V
/* 828:    */     //   487: ldc 173
/* 829:    */     //   489: invokevirtual 35	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 830:    */     //   492: getstatic 70	nxt/peer/Peers:myAddress	Ljava/lang/String;
/* 831:    */     //   495: invokevirtual 35	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 832:    */     //   498: invokevirtual 39	java/lang/StringBuilder:toString	()Ljava/lang/String;
/* 833:    */     //   501: invokestatic 169	nxt/util/Logger:logMessage	(Ljava/lang/String;)V
/* 834:    */     //   504: new 152	java/lang/RuntimeException
/* 835:    */     //   507: dup
/* 836:    */     //   508: aload_1
/* 837:    */     //   509: invokevirtual 174	java/net/URISyntaxException:toString	()Ljava/lang/String;
/* 838:    */     //   512: aload_1
/* 839:    */     //   513: invokespecial 171	java/lang/RuntimeException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
/* 840:    */     //   516: athrow
/* 841:    */     //   517: getstatic 162	nxt/peer/Peers:myHallmark	Ljava/lang/String;
/* 842:    */     //   520: ifnull +22 -> 542
/* 843:    */     //   523: getstatic 162	nxt/peer/Peers:myHallmark	Ljava/lang/String;
/* 844:    */     //   526: invokevirtual 71	java/lang/String:length	()I
/* 845:    */     //   529: ifle +13 -> 542
/* 846:    */     //   532: aload_0
/* 847:    */     //   533: ldc 175
/* 848:    */     //   535: getstatic 162	nxt/peer/Peers:myHallmark	Ljava/lang/String;
/* 849:    */     //   538: invokevirtual 92	org/json/simple/JSONObject:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
/* 850:    */     //   541: pop
/* 851:    */     //   542: aload_0
/* 852:    */     //   543: ldc 176
/* 853:    */     //   545: ldc 177
/* 854:    */     //   547: invokevirtual 92	org/json/simple/JSONObject:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
/* 855:    */     //   550: pop
/* 856:    */     //   551: aload_0
/* 857:    */     //   552: ldc 178
/* 858:    */     //   554: ldc 179
/* 859:    */     //   556: invokevirtual 92	org/json/simple/JSONObject:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
/* 860:    */     //   559: pop
/* 861:    */     //   560: aload_0
/* 862:    */     //   561: ldc 180
/* 863:    */     //   563: getstatic 148	nxt/peer/Peers:myPlatform	Ljava/lang/String;
/* 864:    */     //   566: invokevirtual 92	org/json/simple/JSONObject:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
/* 865:    */     //   569: pop
/* 866:    */     //   570: aload_0
/* 867:    */     //   571: ldc 181
/* 868:    */     //   573: getstatic 6	nxt/peer/Peers:shareMyAddress	Z
/* 869:    */     //   576: invokestatic 182	java/lang/Boolean:valueOf	(Z)Ljava/lang/Boolean;
/* 870:    */     //   579: invokevirtual 92	org/json/simple/JSONObject:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
/* 871:    */     //   582: pop
/* 872:    */     //   583: new 17	java/lang/StringBuilder
/* 873:    */     //   586: dup
/* 874:    */     //   587: invokespecial 18	java/lang/StringBuilder:<init>	()V
/* 875:    */     //   590: ldc 183
/* 876:    */     //   592: invokevirtual 35	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 877:    */     //   595: aload_0
/* 878:    */     //   596: invokevirtual 184	org/json/simple/JSONObject:toJSONString	()Ljava/lang/String;
/* 879:    */     //   599: invokevirtual 35	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 880:    */     //   602: invokevirtual 39	java/lang/StringBuilder:toString	()Ljava/lang/String;
/* 881:    */     //   605: invokestatic 80	nxt/util/Logger:logDebugMessage	(Ljava/lang/String;)V
/* 882:    */     //   608: aload_0
/* 883:    */     //   609: invokestatic 185	nxt/util/JSON:prepare	(Lorg/json/simple/JSONObject;)Lorg/json/simple/JSONStreamAware;
/* 884:    */     //   612: putstatic 186	nxt/peer/Peers:myPeerInfoResponse	Lorg/json/simple/JSONStreamAware;
/* 885:    */     //   615: aload_0
/* 886:    */     //   616: ldc 90
/* 887:    */     //   618: ldc 187
/* 888:    */     //   620: invokevirtual 92	org/json/simple/JSONObject:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
/* 889:    */     //   623: pop
/* 890:    */     //   624: aload_0
/* 891:    */     //   625: invokestatic 104	nxt/util/JSON:prepareRequest	(Lorg/json/simple/JSONObject;)Lorg/json/simple/JSONStreamAware;
/* 892:    */     //   628: putstatic 188	nxt/peer/Peers:myPeerInfoRequest	Lorg/json/simple/JSONStreamAware;
/* 893:    */     //   631: getstatic 74	nxt/Constants:isTestnet	Z
/* 894:    */     //   634: ifeq +11 -> 645
/* 895:    */     //   637: ldc 189
/* 896:    */     //   639: invokestatic 190	nxt/Nxt:getStringListProperty	(Ljava/lang/String;)Ljava/util/List;
/* 897:    */     //   642: goto +8 -> 650
/* 898:    */     //   645: ldc 191
/* 899:    */     //   647: invokestatic 190	nxt/Nxt:getStringListProperty	(Ljava/lang/String;)Ljava/util/List;
/* 900:    */     //   650: astore_1
/* 901:    */     //   651: aload_1
/* 902:    */     //   652: invokeinterface 192 1 0
/* 903:    */     //   657: ifne +9 -> 666
/* 904:    */     //   660: getstatic 160	nxt/Constants:isOffline	Z
/* 905:    */     //   663: ifeq +12 -> 675
/* 906:    */     //   666: invokestatic 193	java/util/Collections:emptySet	()Ljava/util/Set;
/* 907:    */     //   669: putstatic 194	nxt/peer/Peers:wellKnownPeers	Ljava/util/Set;
/* 908:    */     //   672: goto +17 -> 689
/* 909:    */     //   675: new 195	java/util/HashSet
/* 910:    */     //   678: dup
/* 911:    */     //   679: aload_1
/* 912:    */     //   680: invokespecial 196	java/util/HashSet:<init>	(Ljava/util/Collection;)V
/* 913:    */     //   683: invokestatic 197	java/util/Collections:unmodifiableSet	(Ljava/util/Set;)Ljava/util/Set;
/* 914:    */     //   686: putstatic 194	nxt/peer/Peers:wellKnownPeers	Ljava/util/Set;
/* 915:    */     //   689: ldc 198
/* 916:    */     //   691: invokestatic 190	nxt/Nxt:getStringListProperty	(Ljava/lang/String;)Ljava/util/List;
/* 917:    */     //   694: astore_2
/* 918:    */     //   695: aload_2
/* 919:    */     //   696: invokeinterface 192 1 0
/* 920:    */     //   701: ifeq +12 -> 713
/* 921:    */     //   704: invokestatic 193	java/util/Collections:emptySet	()Ljava/util/Set;
/* 922:    */     //   707: putstatic 199	nxt/peer/Peers:knownBlacklistedPeers	Ljava/util/Set;
/* 923:    */     //   710: goto +17 -> 727
/* 924:    */     //   713: new 195	java/util/HashSet
/* 925:    */     //   716: dup
/* 926:    */     //   717: aload_2
/* 927:    */     //   718: invokespecial 196	java/util/HashSet:<init>	(Ljava/util/Collection;)V
/* 928:    */     //   721: invokestatic 197	java/util/Collections:unmodifiableSet	(Ljava/util/Set;)Ljava/util/Set;
/* 929:    */     //   724: putstatic 199	nxt/peer/Peers:knownBlacklistedPeers	Ljava/util/Set;
/* 930:    */     //   727: ldc 200
/* 931:    */     //   729: invokestatic 156	nxt/Nxt:getIntProperty	(Ljava/lang/String;)I
/* 932:    */     //   732: putstatic 3	nxt/peer/Peers:maxNumberOfConnectedPublicPeers	I
/* 933:    */     //   735: ldc 201
/* 934:    */     //   737: invokestatic 156	nxt/Nxt:getIntProperty	(Ljava/lang/String;)I
/* 935:    */     //   740: putstatic 202	nxt/peer/Peers:connectTimeout	I
/* 936:    */     //   743: ldc 203
/* 937:    */     //   745: invokestatic 156	nxt/Nxt:getIntProperty	(Ljava/lang/String;)I
/* 938:    */     //   748: putstatic 204	nxt/peer/Peers:readTimeout	I
/* 939:    */     //   751: ldc 205
/* 940:    */     //   753: invokestatic 158	nxt/Nxt:getBooleanProperty	(Ljava/lang/String;)Ljava/lang/Boolean;
/* 941:    */     //   756: invokevirtual 159	java/lang/Boolean:booleanValue	()Z
/* 942:    */     //   759: putstatic 105	nxt/peer/Peers:enableHallmarkProtection	Z
/* 943:    */     //   762: ldc 206
/* 944:    */     //   764: invokestatic 156	nxt/Nxt:getIntProperty	(Ljava/lang/String;)I
/* 945:    */     //   767: putstatic 107	nxt/peer/Peers:pushThreshold	I
/* 946:    */     //   770: ldc 207
/* 947:    */     //   772: invokestatic 156	nxt/Nxt:getIntProperty	(Ljava/lang/String;)I
/* 948:    */     //   775: putstatic 125	nxt/peer/Peers:pullThreshold	I
/* 949:    */     //   778: ldc 208
/* 950:    */     //   780: invokestatic 156	nxt/Nxt:getIntProperty	(Ljava/lang/String;)I
/* 951:    */     //   783: putstatic 209	nxt/peer/Peers:blacklistingPeriod	I
/* 952:    */     //   786: ldc 210
/* 953:    */     //   788: invokestatic 156	nxt/Nxt:getIntProperty	(Ljava/lang/String;)I
/* 954:    */     //   791: putstatic 211	nxt/peer/Peers:communicationLoggingMask	I
/* 955:    */     //   794: ldc 212
/* 956:    */     //   796: invokestatic 156	nxt/Nxt:getIntProperty	(Ljava/lang/String;)I
/* 957:    */     //   799: putstatic 113	nxt/peer/Peers:sendToPeersLimit	I
/* 958:    */     //   802: ldc 213
/* 959:    */     //   804: invokestatic 158	nxt/Nxt:getBooleanProperty	(Ljava/lang/String;)Ljava/lang/Boolean;
/* 960:    */     //   807: invokevirtual 159	java/lang/Boolean:booleanValue	()Z
/* 961:    */     //   810: ifeq +13 -> 823
/* 962:    */     //   813: getstatic 160	nxt/Constants:isOffline	Z
/* 963:    */     //   816: ifne +7 -> 823
/* 964:    */     //   819: iconst_1
/* 965:    */     //   820: goto +4 -> 824
/* 966:    */     //   823: iconst_0
/* 967:    */     //   824: putstatic 8	nxt/peer/Peers:usePeersDb	Z
/* 968:    */     //   827: getstatic 8	nxt/peer/Peers:usePeersDb	Z
/* 969:    */     //   830: ifeq +18 -> 848
/* 970:    */     //   833: ldc 214
/* 971:    */     //   835: invokestatic 158	nxt/Nxt:getBooleanProperty	(Ljava/lang/String;)Ljava/lang/Boolean;
/* 972:    */     //   838: invokevirtual 159	java/lang/Boolean:booleanValue	()Z
/* 973:    */     //   841: ifeq +7 -> 848
/* 974:    */     //   844: iconst_1
/* 975:    */     //   845: goto +4 -> 849
/* 976:    */     //   848: iconst_0
/* 977:    */     //   849: putstatic 2	nxt/peer/Peers:savePeers	Z
/* 978:    */     //   852: ldc 215
/* 979:    */     //   854: invokestatic 158	nxt/Nxt:getBooleanProperty	(Ljava/lang/String;)Ljava/lang/Boolean;
/* 980:    */     //   857: invokevirtual 159	java/lang/Boolean:booleanValue	()Z
/* 981:    */     //   860: putstatic 216	nxt/peer/Peers:getMorePeers	Z
/* 982:    */     //   863: ldc 217
/* 983:    */     //   865: invokestatic 147	nxt/Nxt:getStringProperty	(Ljava/lang/String;)Ljava/lang/String;
/* 984:    */     //   868: putstatic 16	nxt/peer/Peers:dumpPeersVersion	Ljava/lang/String;
/* 985:    */     //   871: new 46	java/util/ArrayList
/* 986:    */     //   874: dup
/* 987:    */     //   875: invokespecial 47	java/util/ArrayList:<init>	()V
/* 988:    */     //   878: invokestatic 218	java/util/Collections:synchronizedList	(Ljava/util/List;)Ljava/util/List;
/* 989:    */     //   881: astore_3
/* 990:    */     //   882: new 219	nxt/peer/Peers$1
/* 991:    */     //   885: dup
/* 992:    */     //   886: aload_3
/* 993:    */     //   887: invokespecial 220	nxt/peer/Peers$1:<init>	(Ljava/util/List;)V
/* 994:    */     //   890: iconst_0
/* 995:    */     //   891: invokestatic 221	nxt/util/ThreadPool:runBeforeStart	(Ljava/lang/Runnable;Z)V
/* 996:    */     //   894: new 222	nxt/peer/Peers$2
/* 997:    */     //   897: dup
/* 998:    */     //   898: aload_3
/* 999:    */     //   899: invokespecial 223	nxt/peer/Peers$2:<init>	(Ljava/util/List;)V
/* :00:    */     //   902: invokestatic 224	nxt/util/ThreadPool:runAfterStart	(Ljava/lang/Runnable;)V
/* :01:    */     //   905: new 225	nxt/peer/Peers$3
/* :02:    */     //   908: dup
/* :03:    */     //   909: invokespecial 226	nxt/peer/Peers$3:<init>	()V
/* :04:    */     //   912: putstatic 227	nxt/peer/Peers:peerUnBlacklistingThread	Ljava/lang/Runnable;
/* :05:    */     //   915: new 228	nxt/peer/Peers$4
/* :06:    */     //   918: dup
/* :07:    */     //   919: invokespecial 229	nxt/peer/Peers$4:<init>	()V
/* :08:    */     //   922: putstatic 230	nxt/peer/Peers:peerConnectingThread	Ljava/lang/Runnable;
/* :09:    */     //   925: new 231	nxt/peer/Peers$5
/* :10:    */     //   928: dup
/* :11:    */     //   929: invokespecial 232	nxt/peer/Peers$5:<init>	()V
/* :12:    */     //   932: putstatic 233	nxt/peer/Peers:getMorePeersThread	Ljava/lang/Runnable;
/* :13:    */     //   935: new 234	nxt/peer/Peers$6
/* :14:    */     //   938: dup
/* :15:    */     //   939: invokespecial 235	nxt/peer/Peers$6:<init>	()V
/* :16:    */     //   942: getstatic 236	nxt/Account$Event:BALANCE	Lnxt/Account$Event;
/* :17:    */     //   945: invokestatic 237	nxt/Account:addListener	(Lnxt/util/Listener;Lnxt/Account$Event;)Z
/* :18:    */     //   948: pop
/* :19:    */     //   949: getstatic 160	nxt/Constants:isOffline	Z
/* :20:    */     //   952: ifne +36 -> 988
/* :21:    */     //   955: ldc 238
/* :22:    */     //   957: getstatic 230	nxt/peer/Peers:peerConnectingThread	Ljava/lang/Runnable;
/* :23:    */     //   960: iconst_5
/* :24:    */     //   961: invokestatic 239	nxt/util/ThreadPool:scheduleThread	(Ljava/lang/String;Ljava/lang/Runnable;I)V
/* :25:    */     //   964: ldc 240
/* :26:    */     //   966: getstatic 227	nxt/peer/Peers:peerUnBlacklistingThread	Ljava/lang/Runnable;
/* :27:    */     //   969: iconst_1
/* :28:    */     //   970: invokestatic 239	nxt/util/ThreadPool:scheduleThread	(Ljava/lang/String;Ljava/lang/Runnable;I)V
/* :29:    */     //   973: getstatic 216	nxt/peer/Peers:getMorePeers	Z
/* :30:    */     //   976: ifeq +12 -> 988
/* :31:    */     //   979: ldc 241
/* :32:    */     //   981: getstatic 233	nxt/peer/Peers:getMorePeersThread	Ljava/lang/Runnable;
/* :33:    */     //   984: iconst_5
/* :34:    */     //   985: invokestatic 239	nxt/util/ThreadPool:scheduleThread	(Ljava/lang/String;Ljava/lang/Runnable;I)V
/* :35:    */     //   988: return
/* :36:    */     // Line number table:
/* :37:    */     //   Java source line #90	-> byte code offset #0
/* :38:    */     //   Java source line #92	-> byte code offset #10
/* :39:    */     //   Java source line #93	-> byte code offset #20
/* :40:    */     //   Java source line #95	-> byte code offset #30
/* :41:    */     //   Java source line #97	-> byte code offset #44
/* :42:    */     //   Java source line #101	-> byte code offset #52
/* :43:    */     //   Java source line #102	-> byte code offset #60
/* :44:    */     //   Java source line #103	-> byte code offset #68
/* :45:    */     //   Java source line #104	-> byte code offset #91
/* :46:    */     //   Java source line #106	-> byte code offset #101
/* :47:    */     //   Java source line #107	-> byte code offset #109
/* :48:    */     //   Java source line #108	-> byte code offset #124
/* :49:    */     //   Java source line #110	-> byte code offset #134
/* :50:    */     //   Java source line #111	-> byte code offset #159
/* :51:    */     //   Java source line #112	-> byte code offset #167
/* :52:    */     //   Java source line #114	-> byte code offset #182
/* :53:    */     //   Java source line #115	-> byte code offset #189
/* :54:    */     //   Java source line #116	-> byte code offset #202
/* :55:    */     //   Java source line #118	-> byte code offset #210
/* :56:    */     //   Java source line #119	-> byte code offset #242
/* :57:    */     //   Java source line #120	-> byte code offset #247
/* :58:    */     //   Java source line #121	-> byte code offset #258
/* :59:    */     //   Java source line #126	-> byte code offset #266
/* :60:    */     //   Java source line #123	-> byte code offset #269
/* :61:    */     //   Java source line #124	-> byte code offset #270
/* :62:    */     //   Java source line #125	-> byte code offset #305
/* :63:    */     //   Java source line #129	-> byte code offset #318
/* :64:    */     //   Java source line #130	-> byte code offset #326
/* :65:    */     //   Java source line #132	-> byte code offset #341
/* :66:    */     //   Java source line #133	-> byte code offset #373
/* :67:    */     //   Java source line #134	-> byte code offset #378
/* :68:    */     //   Java source line #135	-> byte code offset #383
/* :69:    */     //   Java source line #136	-> byte code offset #389
/* :70:    */     //   Java source line #137	-> byte code offset #393
/* :71:    */     //   Java source line #139	-> byte code offset #406
/* :72:    */     //   Java source line #141	-> byte code offset #468
/* :73:    */     //   Java source line #146	-> byte code offset #476
/* :74:    */     //   Java source line #143	-> byte code offset #479
/* :75:    */     //   Java source line #144	-> byte code offset #480
/* :76:    */     //   Java source line #145	-> byte code offset #504
/* :77:    */     //   Java source line #148	-> byte code offset #517
/* :78:    */     //   Java source line #149	-> byte code offset #532
/* :79:    */     //   Java source line #151	-> byte code offset #542
/* :80:    */     //   Java source line #152	-> byte code offset #551
/* :81:    */     //   Java source line #153	-> byte code offset #560
/* :82:    */     //   Java source line #154	-> byte code offset #570
/* :83:    */     //   Java source line #155	-> byte code offset #583
/* :84:    */     //   Java source line #156	-> byte code offset #608
/* :85:    */     //   Java source line #157	-> byte code offset #615
/* :86:    */     //   Java source line #158	-> byte code offset #624
/* :87:    */     //   Java source line #160	-> byte code offset #631
/* :88:    */     //   Java source line #162	-> byte code offset #651
/* :89:    */     //   Java source line #163	-> byte code offset #666
/* :90:    */     //   Java source line #165	-> byte code offset #675
/* :91:    */     //   Java source line #168	-> byte code offset #689
/* :92:    */     //   Java source line #169	-> byte code offset #695
/* :93:    */     //   Java source line #170	-> byte code offset #704
/* :94:    */     //   Java source line #172	-> byte code offset #713
/* :95:    */     //   Java source line #175	-> byte code offset #727
/* :96:    */     //   Java source line #176	-> byte code offset #735
/* :97:    */     //   Java source line #177	-> byte code offset #743
/* :98:    */     //   Java source line #178	-> byte code offset #751
/* :99:    */     //   Java source line #179	-> byte code offset #762
/* ;00:    */     //   Java source line #180	-> byte code offset #770
/* ;01:    */     //   Java source line #182	-> byte code offset #778
/* ;02:    */     //   Java source line #183	-> byte code offset #786
/* ;03:    */     //   Java source line #184	-> byte code offset #794
/* ;04:    */     //   Java source line #185	-> byte code offset #802
/* ;05:    */     //   Java source line #186	-> byte code offset #827
/* ;06:    */     //   Java source line #187	-> byte code offset #852
/* ;07:    */     //   Java source line #188	-> byte code offset #863
/* ;08:    */     //   Java source line #190	-> byte code offset #871
/* ;09:    */     //   Java source line #192	-> byte code offset #882
/* ;10:    */     //   Java source line #219	-> byte code offset #894
/* ;11:    */     //   Java source line #302	-> byte code offset #905
/* ;12:    */     //   Java source line #328	-> byte code offset #915
/* ;13:    */     //   Java source line #363	-> byte code offset #925
/* ;14:    */     //   Java source line #466	-> byte code offset #935
/* ;15:    */     //   Java source line #479	-> byte code offset #949
/* ;16:    */     //   Java source line #480	-> byte code offset #955
/* ;17:    */     //   Java source line #481	-> byte code offset #964
/* ;18:    */     //   Java source line #482	-> byte code offset #973
/* ;19:    */     //   Java source line #483	-> byte code offset #979
/* ;20:    */     //   Java source line #486	-> byte code offset #988
/* ;21:    */     // Local variable table:
/* ;22:    */     //   start	length	slot	name	signature
/* ;23:    */     //   188	60	0	localHallmark	Hallmark
/* ;24:    */     //   269	45	0	localRuntimeException	RuntimeException
/* ;25:    */     //   325	300	0	localJSONObject	JSONObject
/* ;26:    */     //   241	138	1	localURI	java.net.URI
/* ;27:    */     //   479	34	1	localURISyntaxException	java.net.URISyntaxException
/* ;28:    */     //   650	30	1	localList1	List
/* ;29:    */     //   246	472	2	localObject	Object
/* ;30:    */     //   382	8	3	i	int
/* ;31:    */     //   881	18	3	localList2	List
/* ;32:    */     // Exception table:
/* ;33:    */     //   from	to	target	type
/* ;34:    */     //   182	266	269	java/lang/RuntimeException
/* ;35:    */     //   182	266	269	java/net/URISyntaxException
/* ;36:    */     //   341	476	479	java/net/URISyntaxException
/* ;37:    */   }
/* ;38:    */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.peer.Peers
 * JD-Core Version:    0.7.1
 */