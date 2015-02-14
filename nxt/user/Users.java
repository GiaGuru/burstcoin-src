/*   1:    */ package nxt.user;
/*   2:    */ 
/*   3:    */ import java.math.BigInteger;
/*   4:    */ import java.util.Arrays;
/*   5:    */ import java.util.Collection;
/*   6:    */ import java.util.Collections;
/*   7:    */ import java.util.HashSet;
/*   8:    */ import java.util.List;
/*   9:    */ import java.util.Set;
/*  10:    */ import java.util.concurrent.ConcurrentHashMap;
/*  11:    */ import java.util.concurrent.ConcurrentMap;
/*  12:    */ import java.util.concurrent.atomic.AtomicInteger;
/*  13:    */ import nxt.Account;
/*  14:    */ import nxt.Account.Event;
/*  15:    */ import nxt.Block;
/*  16:    */ import nxt.BlockchainProcessor;
/*  17:    */ import nxt.BlockchainProcessor.Event;
/*  18:    */ import nxt.Constants;
/*  19:    */ import nxt.Generator;
/*  20:    */ import nxt.Generator.Event;
/*  21:    */ import nxt.Nxt;
/*  22:    */ import nxt.Transaction;
/*  23:    */ import nxt.TransactionProcessor;
/*  24:    */ import nxt.TransactionProcessor.Event;
/*  25:    */ import nxt.peer.Peer;
/*  26:    */ import nxt.peer.Peer.State;
/*  27:    */ import nxt.peer.Peers;
/*  28:    */ import nxt.peer.Peers.Event;
/*  29:    */ import nxt.util.Convert;
/*  30:    */ import nxt.util.Listener;
/*  31:    */ import nxt.util.Logger;
/*  32:    */ import nxt.util.ThreadPool;
/*  33:    */ import org.eclipse.jetty.server.ConnectionFactory;
/*  34:    */ import org.eclipse.jetty.server.Handler;
/*  35:    */ import org.eclipse.jetty.server.HttpConfiguration;
/*  36:    */ import org.eclipse.jetty.server.HttpConnectionFactory;
/*  37:    */ import org.eclipse.jetty.server.SecureRequestCustomizer;
/*  38:    */ import org.eclipse.jetty.server.Server;
/*  39:    */ import org.eclipse.jetty.server.ServerConnector;
/*  40:    */ import org.eclipse.jetty.server.SslConnectionFactory;
/*  41:    */ import org.eclipse.jetty.server.handler.ContextHandler;
/*  42:    */ import org.eclipse.jetty.server.handler.DefaultHandler;
/*  43:    */ import org.eclipse.jetty.server.handler.HandlerList;
/*  44:    */ import org.eclipse.jetty.server.handler.ResourceHandler;
/*  45:    */ import org.eclipse.jetty.servlet.FilterHolder;
/*  46:    */ import org.eclipse.jetty.servlet.ServletHandler;
/*  47:    */ import org.eclipse.jetty.servlet.ServletHolder;
/*  48:    */ import org.eclipse.jetty.servlets.CrossOriginFilter;
/*  49:    */ import org.eclipse.jetty.util.ssl.SslContextFactory;
/*  50:    */ import org.json.simple.JSONArray;
/*  51:    */ import org.json.simple.JSONObject;
/*  52:    */ import org.json.simple.JSONStreamAware;
/*  53:    */ 
/*  54:    */ public final class Users
/*  55:    */ {
/*  56:    */   private static final int TESTNET_UI_PORT = 6875;
/*  57: 52 */   private static final ConcurrentMap<String, User> users = new ConcurrentHashMap();
/*  58: 53 */   private static final Collection<User> allUsers = Collections.unmodifiableCollection(users.values());
/*  59: 55 */   private static final AtomicInteger peerCounter = new AtomicInteger();
/*  60: 56 */   private static final ConcurrentMap<String, Integer> peerIndexMap = new ConcurrentHashMap();
/*  61: 57 */   private static final ConcurrentMap<Integer, String> peerAddressMap = new ConcurrentHashMap();
/*  62: 59 */   private static final AtomicInteger blockCounter = new AtomicInteger();
/*  63: 60 */   private static final ConcurrentMap<Long, Integer> blockIndexMap = new ConcurrentHashMap();
/*  64: 62 */   private static final AtomicInteger transactionCounter = new AtomicInteger();
/*  65: 63 */   private static final ConcurrentMap<Long, Integer> transactionIndexMap = new ConcurrentHashMap();
/*  66:    */   static final Set<String> allowedUserHosts;
/*  67:    */   private static final Server userServer;
/*  68:    */   
/*  69:    */   static
/*  70:    */   {
/*  71: 71 */     List localList = Nxt.getStringListProperty("nxt.allowedUserHosts");
/*  72: 72 */     if (!localList.contains("*")) {
/*  73: 73 */       allowedUserHosts = Collections.unmodifiableSet(new HashSet(localList));
/*  74:    */     } else {
/*  75: 75 */       allowedUserHosts = null;
/*  76:    */     }
/*  77: 78 */     boolean bool1 = Nxt.getBooleanProperty("nxt.enableUIServer").booleanValue();
/*  78: 79 */     if (bool1)
/*  79:    */     {
/*  80: 80 */       final int i = Constants.isTestnet ? 6875 : Nxt.getIntProperty("nxt.uiServerPort");
/*  81: 81 */       String str1 = Nxt.getStringProperty("nxt.uiServerHost");
/*  82: 82 */       userServer = new Server();
/*  83:    */       
/*  84:    */ 
/*  85: 85 */       boolean bool2 = Nxt.getBooleanProperty("nxt.uiSSL").booleanValue();
/*  86:    */       ServerConnector localServerConnector;
/*  87: 86 */       if (bool2)
/*  88:    */       {
/*  89: 87 */         Logger.logMessage("Using SSL (https) for the user interface server");
/*  90: 88 */         localObject1 = new HttpConfiguration();
/*  91: 89 */         ((HttpConfiguration)localObject1).setSecureScheme("https");
/*  92: 90 */         ((HttpConfiguration)localObject1).setSecurePort(i);
/*  93: 91 */         ((HttpConfiguration)localObject1).addCustomizer(new SecureRequestCustomizer());
/*  94: 92 */         localObject2 = new SslContextFactory();
/*  95: 93 */         ((SslContextFactory)localObject2).setKeyStorePath(Nxt.getStringProperty("nxt.keyStorePath"));
/*  96: 94 */         ((SslContextFactory)localObject2).setKeyStorePassword(Nxt.getStringProperty("nxt.keyStorePassword"));
/*  97: 95 */         ((SslContextFactory)localObject2).setExcludeCipherSuites(new String[] { "SSL_RSA_WITH_DES_CBC_SHA", "SSL_DHE_RSA_WITH_DES_CBC_SHA", "SSL_DHE_DSS_WITH_DES_CBC_SHA", "SSL_RSA_EXPORT_WITH_RC4_40_MD5", "SSL_RSA_EXPORT_WITH_DES40_CBC_SHA", "SSL_DHE_RSA_EXPORT_WITH_DES40_CBC_SHA", "SSL_DHE_DSS_EXPORT_WITH_DES40_CBC_SHA" });
/*  98:    */         
/*  99:    */ 
/* 100: 98 */         ((SslContextFactory)localObject2).setExcludeProtocols(new String[] { "SSLv3" });
/* 101: 99 */         localServerConnector = new ServerConnector(userServer, new ConnectionFactory[] { new SslConnectionFactory((SslContextFactory)localObject2, "http/1.1"), new HttpConnectionFactory((HttpConfiguration)localObject1) });
/* 102:    */       }
/* 103:    */       else
/* 104:    */       {
/* 105:102 */         localServerConnector = new ServerConnector(userServer);
/* 106:    */       }
/* 107:105 */       localServerConnector.setPort(i);
/* 108:106 */       localServerConnector.setHost(str1);
/* 109:107 */       localServerConnector.setIdleTimeout(Nxt.getIntProperty("nxt.uiServerIdleTimeout"));
/* 110:108 */       localServerConnector.setReuseAddress(true);
/* 111:109 */       userServer.addConnector(localServerConnector);
/* 112:    */       
/* 113:    */ 
/* 114:112 */       Object localObject1 = new HandlerList();
/* 115:    */       
/* 116:114 */       Object localObject2 = new ResourceHandler();
/* 117:115 */       ((ResourceHandler)localObject2).setDirectoriesListed(false);
/* 118:116 */       ((ResourceHandler)localObject2).setWelcomeFiles(new String[] { "index.html" });
/* 119:117 */       ((ResourceHandler)localObject2).setResourceBase(Nxt.getStringProperty("nxt.uiResourceBase"));
/* 120:    */       
/* 121:119 */       ((HandlerList)localObject1).addHandler((Handler)localObject2);
/* 122:    */       
/* 123:121 */       String str2 = Nxt.getStringProperty("nxt.javadocResourceBase");
/* 124:122 */       if (str2 != null)
/* 125:    */       {
/* 126:123 */         localObject3 = new ContextHandler("/doc");
/* 127:124 */         localObject4 = new ResourceHandler();
/* 128:125 */         ((ResourceHandler)localObject4).setDirectoriesListed(false);
/* 129:126 */         ((ResourceHandler)localObject4).setWelcomeFiles(new String[] { "index.html" });
/* 130:127 */         ((ResourceHandler)localObject4).setResourceBase(str2);
/* 131:128 */         ((ContextHandler)localObject3).setHandler((Handler)localObject4);
/* 132:129 */         ((HandlerList)localObject1).addHandler((Handler)localObject3);
/* 133:    */       }
/* 134:132 */       Object localObject3 = new ServletHandler();
/* 135:133 */       Object localObject4 = ((ServletHandler)localObject3).addServletWithMapping(UserServlet.class, "/burst");
/* 136:134 */       ((ServletHolder)localObject4).setAsyncSupported(true);
/* 137:136 */       if (Nxt.getBooleanProperty("nxt.uiServerCORS").booleanValue())
/* 138:    */       {
/* 139:137 */         FilterHolder localFilterHolder = ((ServletHandler)localObject3).addFilterWithMapping(CrossOriginFilter.class, "/*", 0);
/* 140:138 */         localFilterHolder.setInitParameter("allowedHeaders", "*");
/* 141:139 */         localFilterHolder.setAsyncSupported(true);
/* 142:    */       }
/* 143:142 */       ((HandlerList)localObject1).addHandler((Handler)localObject3);
/* 144:    */       
/* 145:144 */       ((HandlerList)localObject1).addHandler(new DefaultHandler());
/* 146:    */       
/* 147:146 */       userServer.setHandler((Handler)localObject1);
/* 148:147 */       userServer.setStopAtShutdown(true);
/* 149:    */       
/* 150:149 */       ThreadPool.runBeforeStart(new Runnable()
/* 151:    */       {
/* 152:    */         public void run()
/* 153:    */         {
/* 154:    */           try
/* 155:    */           {
/* 156:153 */             Users.userServer.start();
/* 157:154 */             Logger.logMessage("Started user interface server at " + this.val$host + ":" + i);
/* 158:    */           }
/* 159:    */           catch (Exception localException)
/* 160:    */           {
/* 161:156 */             Logger.logErrorMessage("Failed to start user interface server", localException);
/* 162:157 */             throw new RuntimeException(localException.toString(), localException);
/* 163:    */           }
/* 164:    */         }
/* 165:157 */       }, true);
/* 166:    */     }
/* 167:    */     else
/* 168:    */     {
/* 169:163 */       userServer = null;
/* 170:164 */       Logger.logMessage("User interface server not enabled");
/* 171:    */     }
/* 172:170 */     if (userServer != null)
/* 173:    */     {
/* 174:171 */       Account.addListener(new Listener()
/* 175:    */       {
/* 176:    */         public void notify(Account paramAnonymousAccount)
/* 177:    */         {
/* 178:174 */           JSONObject localJSONObject = new JSONObject();
/* 179:175 */           localJSONObject.put("response", "setBalance");
/* 180:176 */           localJSONObject.put("balanceNQT", Long.valueOf(paramAnonymousAccount.getUnconfirmedBalanceNQT()));
/* 181:177 */           byte[] arrayOfByte = paramAnonymousAccount.getPublicKey();
/* 182:178 */           for (User localUser : Users.users.values()) {
/* 183:179 */             if ((localUser.getSecretPhrase() != null) && (Arrays.equals(localUser.getPublicKey(), arrayOfByte))) {
/* 184:180 */               localUser.send(localJSONObject);
/* 185:    */             }
/* 186:    */           }
/* 187:    */         }
/* 188:180 */       }, Account.Event.UNCONFIRMED_BALANCE);
/* 189:    */       
/* 190:    */ 
/* 191:    */ 
/* 192:    */ 
/* 193:    */ 
/* 194:186 */       Peers.addListener(new Listener()
/* 195:    */       {
/* 196:    */         public void notify(Peer paramAnonymousPeer)
/* 197:    */         {
/* 198:189 */           JSONObject localJSONObject1 = new JSONObject();
/* 199:190 */           JSONArray localJSONArray1 = new JSONArray();
/* 200:191 */           JSONObject localJSONObject2 = new JSONObject();
/* 201:192 */           localJSONObject2.put("index", Integer.valueOf(Users.getIndex(paramAnonymousPeer)));
/* 202:193 */           localJSONArray1.add(localJSONObject2);
/* 203:194 */           localJSONObject1.put("removedActivePeers", localJSONArray1);
/* 204:195 */           JSONArray localJSONArray2 = new JSONArray();
/* 205:196 */           JSONObject localJSONObject3 = new JSONObject();
/* 206:197 */           localJSONObject3.put("index", Integer.valueOf(Users.getIndex(paramAnonymousPeer)));
/* 207:198 */           localJSONArray2.add(localJSONObject3);
/* 208:199 */           localJSONObject1.put("removedKnownPeers", localJSONArray2);
/* 209:200 */           JSONArray localJSONArray3 = new JSONArray();
/* 210:201 */           JSONObject localJSONObject4 = new JSONObject();
/* 211:202 */           localJSONObject4.put("index", Integer.valueOf(Users.getIndex(paramAnonymousPeer)));
/* 212:203 */           localJSONObject4.put("address", paramAnonymousPeer.getPeerAddress());
/* 213:204 */           localJSONObject4.put("announcedAddress", Convert.truncate(paramAnonymousPeer.getAnnouncedAddress(), "-", 25, true));
/* 214:205 */           if (paramAnonymousPeer.isWellKnown()) {
/* 215:206 */             localJSONObject4.put("wellKnown", Boolean.valueOf(true));
/* 216:    */           }
/* 217:208 */           localJSONObject4.put("software", paramAnonymousPeer.getSoftware());
/* 218:209 */           localJSONArray3.add(localJSONObject4);
/* 219:210 */           localJSONObject1.put("addedBlacklistedPeers", localJSONArray3);
/* 220:211 */           Users.sendNewDataToAll(localJSONObject1);
/* 221:    */         }
/* 222:211 */       }, Peers.Event.BLACKLIST);
/* 223:    */       
/* 224:    */ 
/* 225:    */ 
/* 226:215 */       Peers.addListener(new Listener()
/* 227:    */       {
/* 228:    */         public void notify(Peer paramAnonymousPeer)
/* 229:    */         {
/* 230:218 */           JSONObject localJSONObject1 = new JSONObject();
/* 231:219 */           JSONArray localJSONArray1 = new JSONArray();
/* 232:220 */           JSONObject localJSONObject2 = new JSONObject();
/* 233:221 */           localJSONObject2.put("index", Integer.valueOf(Users.getIndex(paramAnonymousPeer)));
/* 234:222 */           localJSONArray1.add(localJSONObject2);
/* 235:223 */           localJSONObject1.put("removedActivePeers", localJSONArray1);
/* 236:224 */           JSONArray localJSONArray2 = new JSONArray();
/* 237:225 */           JSONObject localJSONObject3 = new JSONObject();
/* 238:226 */           localJSONObject3.put("index", Integer.valueOf(Users.getIndex(paramAnonymousPeer)));
/* 239:227 */           localJSONObject3.put("address", paramAnonymousPeer.getPeerAddress());
/* 240:228 */           localJSONObject3.put("announcedAddress", Convert.truncate(paramAnonymousPeer.getAnnouncedAddress(), "-", 25, true));
/* 241:229 */           if (paramAnonymousPeer.isWellKnown()) {
/* 242:230 */             localJSONObject3.put("wellKnown", Boolean.valueOf(true));
/* 243:    */           }
/* 244:232 */           localJSONObject3.put("software", paramAnonymousPeer.getSoftware());
/* 245:233 */           localJSONArray2.add(localJSONObject3);
/* 246:234 */           localJSONObject1.put("addedKnownPeers", localJSONArray2);
/* 247:235 */           Users.sendNewDataToAll(localJSONObject1);
/* 248:    */         }
/* 249:235 */       }, Peers.Event.DEACTIVATE);
/* 250:    */       
/* 251:    */ 
/* 252:    */ 
/* 253:239 */       Peers.addListener(new Listener()
/* 254:    */       {
/* 255:    */         public void notify(Peer paramAnonymousPeer)
/* 256:    */         {
/* 257:242 */           JSONObject localJSONObject1 = new JSONObject();
/* 258:243 */           JSONArray localJSONArray1 = new JSONArray();
/* 259:244 */           JSONObject localJSONObject2 = new JSONObject();
/* 260:245 */           localJSONObject2.put("index", Integer.valueOf(Users.getIndex(paramAnonymousPeer)));
/* 261:246 */           localJSONArray1.add(localJSONObject2);
/* 262:247 */           localJSONObject1.put("removedBlacklistedPeers", localJSONArray1);
/* 263:248 */           JSONArray localJSONArray2 = new JSONArray();
/* 264:249 */           JSONObject localJSONObject3 = new JSONObject();
/* 265:250 */           localJSONObject3.put("index", Integer.valueOf(Users.getIndex(paramAnonymousPeer)));
/* 266:251 */           localJSONObject3.put("address", paramAnonymousPeer.getPeerAddress());
/* 267:252 */           localJSONObject3.put("announcedAddress", Convert.truncate(paramAnonymousPeer.getAnnouncedAddress(), "-", 25, true));
/* 268:253 */           if (paramAnonymousPeer.isWellKnown()) {
/* 269:254 */             localJSONObject3.put("wellKnown", Boolean.valueOf(true));
/* 270:    */           }
/* 271:256 */           localJSONObject3.put("software", paramAnonymousPeer.getSoftware());
/* 272:257 */           localJSONArray2.add(localJSONObject3);
/* 273:258 */           localJSONObject1.put("addedKnownPeers", localJSONArray2);
/* 274:259 */           Users.sendNewDataToAll(localJSONObject1);
/* 275:    */         }
/* 276:259 */       }, Peers.Event.UNBLACKLIST);
/* 277:    */       
/* 278:    */ 
/* 279:    */ 
/* 280:263 */       Peers.addListener(new Listener()
/* 281:    */       {
/* 282:    */         public void notify(Peer paramAnonymousPeer)
/* 283:    */         {
/* 284:266 */           JSONObject localJSONObject1 = new JSONObject();
/* 285:267 */           JSONArray localJSONArray = new JSONArray();
/* 286:268 */           JSONObject localJSONObject2 = new JSONObject();
/* 287:269 */           localJSONObject2.put("index", Integer.valueOf(Users.getIndex(paramAnonymousPeer)));
/* 288:270 */           localJSONArray.add(localJSONObject2);
/* 289:271 */           localJSONObject1.put("removedKnownPeers", localJSONArray);
/* 290:272 */           Users.sendNewDataToAll(localJSONObject1);
/* 291:    */         }
/* 292:272 */       }, Peers.Event.REMOVE);
/* 293:    */       
/* 294:    */ 
/* 295:    */ 
/* 296:276 */       Peers.addListener(new Listener()
/* 297:    */       {
/* 298:    */         public void notify(Peer paramAnonymousPeer)
/* 299:    */         {
/* 300:279 */           JSONObject localJSONObject1 = new JSONObject();
/* 301:280 */           JSONArray localJSONArray = new JSONArray();
/* 302:281 */           JSONObject localJSONObject2 = new JSONObject();
/* 303:282 */           localJSONObject2.put("index", Integer.valueOf(Users.getIndex(paramAnonymousPeer)));
/* 304:283 */           localJSONObject2.put("downloaded", Long.valueOf(paramAnonymousPeer.getDownloadedVolume()));
/* 305:284 */           localJSONArray.add(localJSONObject2);
/* 306:285 */           localJSONObject1.put("changedActivePeers", localJSONArray);
/* 307:286 */           Users.sendNewDataToAll(localJSONObject1);
/* 308:    */         }
/* 309:286 */       }, Peers.Event.DOWNLOADED_VOLUME);
/* 310:    */       
/* 311:    */ 
/* 312:    */ 
/* 313:290 */       Peers.addListener(new Listener()
/* 314:    */       {
/* 315:    */         public void notify(Peer paramAnonymousPeer)
/* 316:    */         {
/* 317:293 */           JSONObject localJSONObject1 = new JSONObject();
/* 318:294 */           JSONArray localJSONArray = new JSONArray();
/* 319:295 */           JSONObject localJSONObject2 = new JSONObject();
/* 320:296 */           localJSONObject2.put("index", Integer.valueOf(Users.getIndex(paramAnonymousPeer)));
/* 321:297 */           localJSONObject2.put("uploaded", Long.valueOf(paramAnonymousPeer.getUploadedVolume()));
/* 322:298 */           localJSONArray.add(localJSONObject2);
/* 323:299 */           localJSONObject1.put("changedActivePeers", localJSONArray);
/* 324:300 */           Users.sendNewDataToAll(localJSONObject1);
/* 325:    */         }
/* 326:300 */       }, Peers.Event.UPLOADED_VOLUME);
/* 327:    */       
/* 328:    */ 
/* 329:    */ 
/* 330:304 */       Peers.addListener(new Listener()
/* 331:    */       {
/* 332:    */         public void notify(Peer paramAnonymousPeer)
/* 333:    */         {
/* 334:307 */           JSONObject localJSONObject1 = new JSONObject();
/* 335:308 */           JSONArray localJSONArray = new JSONArray();
/* 336:309 */           JSONObject localJSONObject2 = new JSONObject();
/* 337:310 */           localJSONObject2.put("index", Integer.valueOf(Users.getIndex(paramAnonymousPeer)));
/* 338:311 */           localJSONObject2.put("weight", Integer.valueOf(paramAnonymousPeer.getWeight()));
/* 339:312 */           localJSONArray.add(localJSONObject2);
/* 340:313 */           localJSONObject1.put("changedActivePeers", localJSONArray);
/* 341:314 */           Users.sendNewDataToAll(localJSONObject1);
/* 342:    */         }
/* 343:314 */       }, Peers.Event.WEIGHT);
/* 344:    */       
/* 345:    */ 
/* 346:    */ 
/* 347:318 */       Peers.addListener(new Listener()
/* 348:    */       {
/* 349:    */         public void notify(Peer paramAnonymousPeer)
/* 350:    */         {
/* 351:321 */           JSONObject localJSONObject1 = new JSONObject();
/* 352:322 */           JSONArray localJSONArray1 = new JSONArray();
/* 353:323 */           JSONObject localJSONObject2 = new JSONObject();
/* 354:324 */           localJSONObject2.put("index", Integer.valueOf(Users.getIndex(paramAnonymousPeer)));
/* 355:325 */           localJSONArray1.add(localJSONObject2);
/* 356:326 */           localJSONObject1.put("removedKnownPeers", localJSONArray1);
/* 357:327 */           JSONArray localJSONArray2 = new JSONArray();
/* 358:328 */           JSONObject localJSONObject3 = new JSONObject();
/* 359:329 */           localJSONObject3.put("index", Integer.valueOf(Users.getIndex(paramAnonymousPeer)));
/* 360:330 */           if (paramAnonymousPeer.getState() != Peer.State.CONNECTED) {
/* 361:331 */             localJSONObject3.put("disconnected", Boolean.valueOf(true));
/* 362:    */           }
/* 363:333 */           localJSONObject3.put("address", paramAnonymousPeer.getPeerAddress());
/* 364:334 */           localJSONObject3.put("announcedAddress", Convert.truncate(paramAnonymousPeer.getAnnouncedAddress(), "-", 25, true));
/* 365:335 */           if (paramAnonymousPeer.isWellKnown()) {
/* 366:336 */             localJSONObject3.put("wellKnown", Boolean.valueOf(true));
/* 367:    */           }
/* 368:338 */           localJSONObject3.put("weight", Integer.valueOf(paramAnonymousPeer.getWeight()));
/* 369:339 */           localJSONObject3.put("downloaded", Long.valueOf(paramAnonymousPeer.getDownloadedVolume()));
/* 370:340 */           localJSONObject3.put("uploaded", Long.valueOf(paramAnonymousPeer.getUploadedVolume()));
/* 371:341 */           localJSONObject3.put("software", paramAnonymousPeer.getSoftware());
/* 372:342 */           localJSONArray2.add(localJSONObject3);
/* 373:343 */           localJSONObject1.put("addedActivePeers", localJSONArray2);
/* 374:344 */           Users.sendNewDataToAll(localJSONObject1);
/* 375:    */         }
/* 376:344 */       }, Peers.Event.ADDED_ACTIVE_PEER);
/* 377:    */       
/* 378:    */ 
/* 379:    */ 
/* 380:348 */       Peers.addListener(new Listener()
/* 381:    */       {
/* 382:    */         public void notify(Peer paramAnonymousPeer)
/* 383:    */         {
/* 384:351 */           JSONObject localJSONObject1 = new JSONObject();
/* 385:352 */           JSONArray localJSONArray = new JSONArray();
/* 386:353 */           JSONObject localJSONObject2 = new JSONObject();
/* 387:354 */           localJSONObject2.put("index", Integer.valueOf(Users.getIndex(paramAnonymousPeer)));
/* 388:355 */           localJSONObject2.put(paramAnonymousPeer.getState() == Peer.State.CONNECTED ? "connected" : "disconnected", Boolean.valueOf(true));
/* 389:356 */           localJSONObject2.put("announcedAddress", Convert.truncate(paramAnonymousPeer.getAnnouncedAddress(), "-", 25, true));
/* 390:357 */           if (paramAnonymousPeer.isWellKnown()) {
/* 391:358 */             localJSONObject2.put("wellKnown", Boolean.valueOf(true));
/* 392:    */           }
/* 393:360 */           localJSONArray.add(localJSONObject2);
/* 394:361 */           localJSONObject1.put("changedActivePeers", localJSONArray);
/* 395:362 */           Users.sendNewDataToAll(localJSONObject1);
/* 396:    */         }
/* 397:362 */       }, Peers.Event.CHANGED_ACTIVE_PEER);
/* 398:    */       
/* 399:    */ 
/* 400:    */ 
/* 401:366 */       Peers.addListener(new Listener()
/* 402:    */       {
/* 403:    */         public void notify(Peer paramAnonymousPeer)
/* 404:    */         {
/* 405:369 */           JSONObject localJSONObject1 = new JSONObject();
/* 406:370 */           JSONArray localJSONArray = new JSONArray();
/* 407:371 */           JSONObject localJSONObject2 = new JSONObject();
/* 408:372 */           localJSONObject2.put("index", Integer.valueOf(Users.getIndex(paramAnonymousPeer)));
/* 409:373 */           localJSONObject2.put("address", paramAnonymousPeer.getPeerAddress());
/* 410:374 */           localJSONObject2.put("announcedAddress", Convert.truncate(paramAnonymousPeer.getAnnouncedAddress(), "-", 25, true));
/* 411:375 */           if (paramAnonymousPeer.isWellKnown()) {
/* 412:376 */             localJSONObject2.put("wellKnown", Boolean.valueOf(true));
/* 413:    */           }
/* 414:378 */           localJSONObject2.put("software", paramAnonymousPeer.getSoftware());
/* 415:379 */           localJSONArray.add(localJSONObject2);
/* 416:380 */           localJSONObject1.put("addedKnownPeers", localJSONArray);
/* 417:381 */           Users.sendNewDataToAll(localJSONObject1);
/* 418:    */         }
/* 419:381 */       }, Peers.Event.NEW_PEER);
/* 420:    */       
/* 421:    */ 
/* 422:    */ 
/* 423:385 */       Nxt.getTransactionProcessor().addListener(new Listener()
/* 424:    */       {
/* 425:    */         public void notify(List<? extends Transaction> paramAnonymousList)
/* 426:    */         {
/* 427:388 */           JSONObject localJSONObject1 = new JSONObject();
/* 428:389 */           JSONArray localJSONArray = new JSONArray();
/* 429:390 */           for (Transaction localTransaction : paramAnonymousList)
/* 430:    */           {
/* 431:391 */             JSONObject localJSONObject2 = new JSONObject();
/* 432:392 */             localJSONObject2.put("index", Integer.valueOf(Users.getIndex(localTransaction)));
/* 433:393 */             localJSONArray.add(localJSONObject2);
/* 434:    */           }
/* 435:395 */           localJSONObject1.put("removedUnconfirmedTransactions", localJSONArray);
/* 436:396 */           Users.sendNewDataToAll(localJSONObject1);
/* 437:    */         }
/* 438:396 */       }, TransactionProcessor.Event.REMOVED_UNCONFIRMED_TRANSACTIONS);
/* 439:    */       
/* 440:    */ 
/* 441:    */ 
/* 442:400 */       Nxt.getTransactionProcessor().addListener(new Listener()
/* 443:    */       {
/* 444:    */         public void notify(List<? extends Transaction> paramAnonymousList)
/* 445:    */         {
/* 446:403 */           JSONObject localJSONObject1 = new JSONObject();
/* 447:404 */           JSONArray localJSONArray = new JSONArray();
/* 448:405 */           for (Transaction localTransaction : paramAnonymousList)
/* 449:    */           {
/* 450:406 */             JSONObject localJSONObject2 = new JSONObject();
/* 451:407 */             localJSONObject2.put("index", Integer.valueOf(Users.getIndex(localTransaction)));
/* 452:408 */             localJSONObject2.put("timestamp", Integer.valueOf(localTransaction.getTimestamp()));
/* 453:409 */             localJSONObject2.put("deadline", Short.valueOf(localTransaction.getDeadline()));
/* 454:410 */             localJSONObject2.put("recipient", Convert.toUnsignedLong(localTransaction.getRecipientId()));
/* 455:411 */             localJSONObject2.put("amountNQT", Long.valueOf(localTransaction.getAmountNQT()));
/* 456:412 */             localJSONObject2.put("feeNQT", Long.valueOf(localTransaction.getFeeNQT()));
/* 457:413 */             localJSONObject2.put("sender", Convert.toUnsignedLong(localTransaction.getSenderId()));
/* 458:414 */             localJSONObject2.put("id", localTransaction.getStringId());
/* 459:415 */             localJSONArray.add(localJSONObject2);
/* 460:    */           }
/* 461:417 */           localJSONObject1.put("addedUnconfirmedTransactions", localJSONArray);
/* 462:418 */           Users.sendNewDataToAll(localJSONObject1);
/* 463:    */         }
/* 464:418 */       }, TransactionProcessor.Event.ADDED_UNCONFIRMED_TRANSACTIONS);
/* 465:    */       
/* 466:    */ 
/* 467:    */ 
/* 468:422 */       Nxt.getTransactionProcessor().addListener(new Listener()
/* 469:    */       {
/* 470:    */         public void notify(List<? extends Transaction> paramAnonymousList)
/* 471:    */         {
/* 472:425 */           JSONObject localJSONObject1 = new JSONObject();
/* 473:426 */           JSONArray localJSONArray = new JSONArray();
/* 474:427 */           for (Transaction localTransaction : paramAnonymousList)
/* 475:    */           {
/* 476:428 */             JSONObject localJSONObject2 = new JSONObject();
/* 477:429 */             localJSONObject2.put("index", Integer.valueOf(Users.getIndex(localTransaction)));
/* 478:430 */             localJSONObject2.put("blockTimestamp", Integer.valueOf(localTransaction.getBlockTimestamp()));
/* 479:431 */             localJSONObject2.put("transactionTimestamp", Integer.valueOf(localTransaction.getTimestamp()));
/* 480:432 */             localJSONObject2.put("sender", Convert.toUnsignedLong(localTransaction.getSenderId()));
/* 481:433 */             localJSONObject2.put("recipient", Convert.toUnsignedLong(localTransaction.getRecipientId()));
/* 482:434 */             localJSONObject2.put("amountNQT", Long.valueOf(localTransaction.getAmountNQT()));
/* 483:435 */             localJSONObject2.put("feeNQT", Long.valueOf(localTransaction.getFeeNQT()));
/* 484:436 */             localJSONObject2.put("id", localTransaction.getStringId());
/* 485:437 */             localJSONArray.add(localJSONObject2);
/* 486:    */           }
/* 487:439 */           localJSONObject1.put("addedConfirmedTransactions", localJSONArray);
/* 488:440 */           Users.sendNewDataToAll(localJSONObject1);
/* 489:    */         }
/* 490:440 */       }, TransactionProcessor.Event.ADDED_CONFIRMED_TRANSACTIONS);
/* 491:    */       
/* 492:    */ 
/* 493:    */ 
/* 494:444 */       Nxt.getTransactionProcessor().addListener(new Listener()
/* 495:    */       {
/* 496:    */         public void notify(List<? extends Transaction> paramAnonymousList)
/* 497:    */         {
/* 498:447 */           JSONObject localJSONObject1 = new JSONObject();
/* 499:448 */           JSONArray localJSONArray = new JSONArray();
/* 500:449 */           for (Transaction localTransaction : paramAnonymousList)
/* 501:    */           {
/* 502:450 */             JSONObject localJSONObject2 = new JSONObject();
/* 503:451 */             localJSONObject2.put("index", Integer.valueOf(Users.getIndex(localTransaction)));
/* 504:452 */             localJSONObject2.put("timestamp", Integer.valueOf(localTransaction.getTimestamp()));
/* 505:453 */             localJSONObject2.put("deadline", Short.valueOf(localTransaction.getDeadline()));
/* 506:454 */             localJSONObject2.put("recipient", Convert.toUnsignedLong(localTransaction.getRecipientId()));
/* 507:455 */             localJSONObject2.put("amountNQT", Long.valueOf(localTransaction.getAmountNQT()));
/* 508:456 */             localJSONObject2.put("feeNQT", Long.valueOf(localTransaction.getFeeNQT()));
/* 509:457 */             localJSONObject2.put("sender", Convert.toUnsignedLong(localTransaction.getSenderId()));
/* 510:458 */             localJSONObject2.put("id", localTransaction.getStringId());
/* 511:459 */             localJSONArray.add(localJSONObject2);
/* 512:    */           }
/* 513:461 */           localJSONObject1.put("addedDoubleSpendingTransactions", localJSONArray);
/* 514:462 */           Users.sendNewDataToAll(localJSONObject1);
/* 515:    */         }
/* 516:462 */       }, TransactionProcessor.Event.ADDED_DOUBLESPENDING_TRANSACTIONS);
/* 517:    */       
/* 518:    */ 
/* 519:    */ 
/* 520:466 */       Nxt.getBlockchainProcessor().addListener(new Listener()
/* 521:    */       {
/* 522:    */         public void notify(Block paramAnonymousBlock)
/* 523:    */         {
/* 524:469 */           JSONObject localJSONObject1 = new JSONObject();
/* 525:470 */           JSONArray localJSONArray = new JSONArray();
/* 526:471 */           JSONObject localJSONObject2 = new JSONObject();
/* 527:472 */           localJSONObject2.put("index", Integer.valueOf(Users.getIndex(paramAnonymousBlock)));
/* 528:473 */           localJSONObject2.put("timestamp", Integer.valueOf(paramAnonymousBlock.getTimestamp()));
/* 529:474 */           localJSONObject2.put("numberOfTransactions", Integer.valueOf(paramAnonymousBlock.getTransactions().size()));
/* 530:475 */           localJSONObject2.put("totalAmountNQT", Long.valueOf(paramAnonymousBlock.getTotalAmountNQT()));
/* 531:476 */           localJSONObject2.put("totalFeeNQT", Long.valueOf(paramAnonymousBlock.getTotalFeeNQT()));
/* 532:477 */           localJSONObject2.put("payloadLength", Integer.valueOf(paramAnonymousBlock.getPayloadLength()));
/* 533:478 */           localJSONObject2.put("generator", Convert.toUnsignedLong(paramAnonymousBlock.getGeneratorId()));
/* 534:479 */           localJSONObject2.put("height", Integer.valueOf(paramAnonymousBlock.getHeight()));
/* 535:480 */           localJSONObject2.put("version", Integer.valueOf(paramAnonymousBlock.getVersion()));
/* 536:481 */           localJSONObject2.put("block", paramAnonymousBlock.getStringId());
/* 537:482 */           localJSONObject2.put("baseTarget", BigInteger.valueOf(paramAnonymousBlock.getBaseTarget()).multiply(BigInteger.valueOf(100000L)).divide(BigInteger.valueOf(18325193796L)));
/* 538:483 */           localJSONArray.add(localJSONObject2);
/* 539:484 */           localJSONObject1.put("addedOrphanedBlocks", localJSONArray);
/* 540:485 */           Users.sendNewDataToAll(localJSONObject1);
/* 541:    */         }
/* 542:485 */       }, BlockchainProcessor.Event.BLOCK_POPPED);
/* 543:    */       
/* 544:    */ 
/* 545:    */ 
/* 546:489 */       Nxt.getBlockchainProcessor().addListener(new Listener()
/* 547:    */       {
/* 548:    */         public void notify(Block paramAnonymousBlock)
/* 549:    */         {
/* 550:492 */           JSONObject localJSONObject1 = new JSONObject();
/* 551:493 */           JSONArray localJSONArray = new JSONArray();
/* 552:494 */           JSONObject localJSONObject2 = new JSONObject();
/* 553:495 */           localJSONObject2.put("index", Integer.valueOf(Users.getIndex(paramAnonymousBlock)));
/* 554:496 */           localJSONObject2.put("timestamp", Integer.valueOf(paramAnonymousBlock.getTimestamp()));
/* 555:497 */           localJSONObject2.put("numberOfTransactions", Integer.valueOf(paramAnonymousBlock.getTransactions().size()));
/* 556:498 */           localJSONObject2.put("totalAmountNQT", Long.valueOf(paramAnonymousBlock.getTotalAmountNQT()));
/* 557:499 */           localJSONObject2.put("totalFeeNQT", Long.valueOf(paramAnonymousBlock.getTotalFeeNQT()));
/* 558:500 */           localJSONObject2.put("payloadLength", Integer.valueOf(paramAnonymousBlock.getPayloadLength()));
/* 559:501 */           localJSONObject2.put("generator", Convert.toUnsignedLong(paramAnonymousBlock.getGeneratorId()));
/* 560:502 */           localJSONObject2.put("height", Integer.valueOf(paramAnonymousBlock.getHeight()));
/* 561:503 */           localJSONObject2.put("version", Integer.valueOf(paramAnonymousBlock.getVersion()));
/* 562:504 */           localJSONObject2.put("block", paramAnonymousBlock.getStringId());
/* 563:505 */           localJSONObject2.put("baseTarget", BigInteger.valueOf(paramAnonymousBlock.getBaseTarget()).multiply(BigInteger.valueOf(100000L)).divide(BigInteger.valueOf(18325193796L)));
/* 564:506 */           localJSONArray.add(localJSONObject2);
/* 565:507 */           localJSONObject1.put("addedRecentBlocks", localJSONArray);
/* 566:508 */           Users.sendNewDataToAll(localJSONObject1);
/* 567:    */         }
/* 568:508 */       }, BlockchainProcessor.Event.BLOCK_PUSHED);
/* 569:    */       
/* 570:    */ 
/* 571:    */ 
/* 572:512 */       Generator.addListener(new Listener()
/* 573:    */       {
/* 574:    */         public void notify(Generator paramAnonymousGenerator)
/* 575:    */         {
/* 576:515 */           JSONObject localJSONObject = new JSONObject();
/* 577:516 */           localJSONObject.put("response", "setBlockGenerationDeadline");
/* 578:517 */           localJSONObject.put("deadline", paramAnonymousGenerator.getDeadline());
/* 579:518 */           for (User localUser : Users.users.values()) {
/* 580:519 */             if (Arrays.equals(paramAnonymousGenerator.getPublicKey(), localUser.getPublicKey())) {
/* 581:520 */               localUser.send(localJSONObject);
/* 582:    */             }
/* 583:    */           }
/* 584:    */         }
/* 585:520 */       }, Generator.Event.GENERATION_DEADLINE);
/* 586:    */     }
/* 587:    */   }
/* 588:    */   
/* 589:    */   static Collection<User> getAllUsers()
/* 590:    */   {
/* 591:531 */     return allUsers;
/* 592:    */   }
/* 593:    */   
/* 594:    */   static User getUser(String paramString)
/* 595:    */   {
/* 596:535 */     Object localObject = (User)users.get(paramString);
/* 597:536 */     if (localObject == null)
/* 598:    */     {
/* 599:537 */       localObject = new User(paramString);
/* 600:538 */       User localUser = (User)users.putIfAbsent(paramString, localObject);
/* 601:539 */       if (localUser != null)
/* 602:    */       {
/* 603:540 */         localObject = localUser;
/* 604:541 */         ((User)localObject).setInactive(false);
/* 605:    */       }
/* 606:    */     }
/* 607:    */     else
/* 608:    */     {
/* 609:544 */       ((User)localObject).setInactive(false);
/* 610:    */     }
/* 611:546 */     return (User)localObject;
/* 612:    */   }
/* 613:    */   
/* 614:    */   static User remove(User paramUser)
/* 615:    */   {
/* 616:550 */     return (User)users.remove(paramUser.getUserId());
/* 617:    */   }
/* 618:    */   
/* 619:    */   private static void sendNewDataToAll(JSONObject paramJSONObject)
/* 620:    */   {
/* 621:554 */     paramJSONObject.put("response", "processNewData");
/* 622:555 */     sendToAll(paramJSONObject);
/* 623:    */   }
/* 624:    */   
/* 625:    */   private static void sendToAll(JSONStreamAware paramJSONStreamAware)
/* 626:    */   {
/* 627:559 */     for (User localUser : users.values()) {
/* 628:560 */       localUser.send(paramJSONStreamAware);
/* 629:    */     }
/* 630:    */   }
/* 631:    */   
/* 632:    */   static int getIndex(Peer paramPeer)
/* 633:    */   {
/* 634:565 */     Integer localInteger = (Integer)peerIndexMap.get(paramPeer.getPeerAddress());
/* 635:566 */     if (localInteger == null)
/* 636:    */     {
/* 637:567 */       localInteger = Integer.valueOf(peerCounter.incrementAndGet());
/* 638:568 */       peerIndexMap.put(paramPeer.getPeerAddress(), localInteger);
/* 639:569 */       peerAddressMap.put(localInteger, paramPeer.getPeerAddress());
/* 640:    */     }
/* 641:571 */     return localInteger.intValue();
/* 642:    */   }
/* 643:    */   
/* 644:    */   static Peer getPeer(int paramInt)
/* 645:    */   {
/* 646:575 */     String str = (String)peerAddressMap.get(Integer.valueOf(paramInt));
/* 647:576 */     if (str == null) {
/* 648:577 */       return null;
/* 649:    */     }
/* 650:579 */     return Peers.getPeer(str);
/* 651:    */   }
/* 652:    */   
/* 653:    */   static int getIndex(Block paramBlock)
/* 654:    */   {
/* 655:583 */     Integer localInteger = (Integer)blockIndexMap.get(Long.valueOf(paramBlock.getId()));
/* 656:584 */     if (localInteger == null)
/* 657:    */     {
/* 658:585 */       localInteger = Integer.valueOf(blockCounter.incrementAndGet());
/* 659:586 */       blockIndexMap.put(Long.valueOf(paramBlock.getId()), localInteger);
/* 660:    */     }
/* 661:588 */     return localInteger.intValue();
/* 662:    */   }
/* 663:    */   
/* 664:    */   static int getIndex(Transaction paramTransaction)
/* 665:    */   {
/* 666:592 */     Integer localInteger = (Integer)transactionIndexMap.get(Long.valueOf(paramTransaction.getId()));
/* 667:593 */     if (localInteger == null)
/* 668:    */     {
/* 669:594 */       localInteger = Integer.valueOf(transactionCounter.incrementAndGet());
/* 670:595 */       transactionIndexMap.put(Long.valueOf(paramTransaction.getId()), localInteger);
/* 671:    */     }
/* 672:597 */     return localInteger.intValue();
/* 673:    */   }
/* 674:    */   
/* 675:    */   public static void shutdown()
/* 676:    */   {
/* 677:603 */     if (userServer != null) {
/* 678:    */       try
/* 679:    */       {
/* 680:605 */         userServer.stop();
/* 681:    */       }
/* 682:    */       catch (Exception localException)
/* 683:    */       {
/* 684:607 */         Logger.logShutdownMessage("Failed to stop user interface server", localException);
/* 685:    */       }
/* 686:    */     }
/* 687:    */   }
/* 688:    */   
/* 689:    */   public static void init() {}
/* 690:    */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.user.Users
 * JD-Core Version:    0.7.1
 */