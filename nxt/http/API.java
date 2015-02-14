/*   1:    */ package nxt.http;
/*   2:    */ 
/*   3:    */ import java.util.Collections;
/*   4:    */ import java.util.HashSet;
/*   5:    */ import java.util.List;
/*   6:    */ import java.util.Set;
/*   7:    */ import nxt.Constants;
/*   8:    */ import nxt.Nxt;
/*   9:    */ import nxt.util.Logger;
/*  10:    */ import nxt.util.ThreadPool;
/*  11:    */ import org.eclipse.jetty.server.ConnectionFactory;
/*  12:    */ import org.eclipse.jetty.server.Handler;
/*  13:    */ import org.eclipse.jetty.server.HttpConfiguration;
/*  14:    */ import org.eclipse.jetty.server.HttpConnectionFactory;
/*  15:    */ import org.eclipse.jetty.server.SecureRequestCustomizer;
/*  16:    */ import org.eclipse.jetty.server.Server;
/*  17:    */ import org.eclipse.jetty.server.ServerConnector;
/*  18:    */ import org.eclipse.jetty.server.SslConnectionFactory;
/*  19:    */ import org.eclipse.jetty.server.handler.ContextHandler;
/*  20:    */ import org.eclipse.jetty.server.handler.DefaultHandler;
/*  21:    */ import org.eclipse.jetty.server.handler.HandlerList;
/*  22:    */ import org.eclipse.jetty.server.handler.ResourceHandler;
/*  23:    */ import org.eclipse.jetty.servlet.DefaultServlet;
/*  24:    */ import org.eclipse.jetty.servlet.FilterHolder;
/*  25:    */ import org.eclipse.jetty.servlet.ServletContextHandler;
/*  26:    */ import org.eclipse.jetty.servlet.ServletHolder;
/*  27:    */ import org.eclipse.jetty.servlets.CrossOriginFilter;
/*  28:    */ import org.eclipse.jetty.servlets.GzipFilter;
/*  29:    */ import org.eclipse.jetty.util.ssl.SslContextFactory;
/*  30:    */ 
/*  31:    */ public final class API
/*  32:    */ {
/*  33:    */   private static final int TESTNET_API_PORT = 6876;
/*  34:    */   static final Set<String> allowedBotHosts;
/*  35: 35 */   static final boolean enableDebugAPI = Nxt.getBooleanProperty("nxt.enableDebugAPI").booleanValue();
/*  36:    */   private static final Server apiServer;
/*  37:    */   
/*  38:    */   static
/*  39:    */   {
/*  40: 40 */     List localList = Nxt.getStringListProperty("nxt.allowedBotHosts");
/*  41: 41 */     if (!localList.contains("*")) {
/*  42: 42 */       allowedBotHosts = Collections.unmodifiableSet(new HashSet(localList));
/*  43:    */     } else {
/*  44: 44 */       allowedBotHosts = null;
/*  45:    */     }
/*  46: 47 */     boolean bool1 = Nxt.getBooleanProperty("nxt.enableAPIServer").booleanValue();
/*  47: 48 */     if (bool1)
/*  48:    */     {
/*  49: 49 */       final int i = Constants.isTestnet ? 6876 : Nxt.getIntProperty("nxt.apiServerPort");
/*  50: 50 */       String str1 = Nxt.getStringProperty("nxt.apiServerHost");
/*  51: 51 */       apiServer = new Server();
/*  52:    */       
/*  53:    */ 
/*  54: 54 */       boolean bool2 = Nxt.getBooleanProperty("nxt.apiSSL").booleanValue();
/*  55:    */       ServerConnector localServerConnector;
/*  56: 55 */       if (bool2)
/*  57:    */       {
/*  58: 56 */         Logger.logMessage("Using SSL (https) for the API server");
/*  59: 57 */         localObject1 = new HttpConfiguration();
/*  60: 58 */         ((HttpConfiguration)localObject1).setSecureScheme("https");
/*  61: 59 */         ((HttpConfiguration)localObject1).setSecurePort(i);
/*  62: 60 */         ((HttpConfiguration)localObject1).addCustomizer(new SecureRequestCustomizer());
/*  63: 61 */         localObject2 = new SslContextFactory();
/*  64: 62 */         ((SslContextFactory)localObject2).setKeyStorePath(Nxt.getStringProperty("nxt.keyStorePath"));
/*  65: 63 */         ((SslContextFactory)localObject2).setKeyStorePassword(Nxt.getStringProperty("nxt.keyStorePassword"));
/*  66: 64 */         ((SslContextFactory)localObject2).setExcludeCipherSuites(new String[] { "SSL_RSA_WITH_DES_CBC_SHA", "SSL_DHE_RSA_WITH_DES_CBC_SHA", "SSL_DHE_DSS_WITH_DES_CBC_SHA", "SSL_RSA_EXPORT_WITH_RC4_40_MD5", "SSL_RSA_EXPORT_WITH_DES40_CBC_SHA", "SSL_DHE_RSA_EXPORT_WITH_DES40_CBC_SHA", "SSL_DHE_DSS_EXPORT_WITH_DES40_CBC_SHA" });
/*  67:    */         
/*  68:    */ 
/*  69: 67 */         ((SslContextFactory)localObject2).setExcludeProtocols(new String[] { "SSLv3" });
/*  70: 68 */         localServerConnector = new ServerConnector(apiServer, new ConnectionFactory[] { new SslConnectionFactory((SslContextFactory)localObject2, "http/1.1"), new HttpConnectionFactory((HttpConfiguration)localObject1) });
/*  71:    */       }
/*  72:    */       else
/*  73:    */       {
/*  74: 71 */         localServerConnector = new ServerConnector(apiServer);
/*  75:    */       }
/*  76: 74 */       localServerConnector.setPort(i);
/*  77: 75 */       localServerConnector.setHost(str1);
/*  78: 76 */       localServerConnector.setIdleTimeout(Nxt.getIntProperty("nxt.apiServerIdleTimeout"));
/*  79: 77 */       localServerConnector.setReuseAddress(true);
/*  80: 78 */       apiServer.addConnector(localServerConnector);
/*  81:    */       
/*  82: 80 */       Object localObject1 = new HandlerList();
/*  83:    */       
/*  84: 82 */       Object localObject2 = new ServletContextHandler();
/*  85: 83 */       String str2 = Nxt.getStringProperty("nxt.apiResourceBase");
/*  86: 84 */       if (str2 != null)
/*  87:    */       {
/*  88: 85 */         localObject3 = new ServletHolder(new DefaultServlet());
/*  89: 86 */         ((ServletHolder)localObject3).setInitParameter("dirAllowed", "false");
/*  90: 87 */         ((ServletHolder)localObject3).setInitParameter("resourceBase", str2);
/*  91: 88 */         ((ServletHolder)localObject3).setInitParameter("welcomeServlets", "true");
/*  92: 89 */         ((ServletHolder)localObject3).setInitParameter("redirectWelcome", "true");
/*  93: 90 */         ((ServletHolder)localObject3).setInitParameter("gzip", "true");
/*  94: 91 */         ((ServletContextHandler)localObject2).addServlet((ServletHolder)localObject3, "/*");
/*  95: 92 */         ((ServletContextHandler)localObject2).setWelcomeFiles(new String[] { "index.html" });
/*  96:    */       }
/*  97: 95 */       Object localObject3 = Nxt.getStringProperty("nxt.javadocResourceBase");
/*  98:    */       Object localObject4;
/*  99: 96 */       if (localObject3 != null)
/* 100:    */       {
/* 101: 97 */         localObject4 = new ContextHandler("/doc");
/* 102: 98 */         ResourceHandler localResourceHandler = new ResourceHandler();
/* 103: 99 */         localResourceHandler.setDirectoriesListed(false);
/* 104:100 */         localResourceHandler.setWelcomeFiles(new String[] { "index.html" });
/* 105:101 */         localResourceHandler.setResourceBase((String)localObject3);
/* 106:102 */         ((ContextHandler)localObject4).setHandler(localResourceHandler);
/* 107:103 */         ((HandlerList)localObject1).addHandler((Handler)localObject4);
/* 108:    */       }
/* 109:106 */       ((ServletContextHandler)localObject2).addServlet(APIServlet.class, "/burst");
/* 110:107 */       if (Nxt.getBooleanProperty("nxt.enableAPIServerGZIPFilter").booleanValue())
/* 111:    */       {
/* 112:108 */         localObject4 = ((ServletContextHandler)localObject2).addFilter(GzipFilter.class, "/burst", null);
/* 113:109 */         ((FilterHolder)localObject4).setInitParameter("methods", "GET,POST");
/* 114:110 */         ((FilterHolder)localObject4).setAsyncSupported(true);
/* 115:    */       }
/* 116:113 */       ((ServletContextHandler)localObject2).addServlet(APITestServlet.class, "/test");
/* 117:114 */       if (enableDebugAPI) {
/* 118:115 */         ((ServletContextHandler)localObject2).addServlet(DbShellServlet.class, "/dbshell");
/* 119:    */       }
/* 120:118 */       if (Nxt.getBooleanProperty("nxt.apiServerCORS").booleanValue())
/* 121:    */       {
/* 122:119 */         localObject4 = ((ServletContextHandler)localObject2).addFilter(CrossOriginFilter.class, "/*", null);
/* 123:120 */         ((FilterHolder)localObject4).setInitParameter("allowedHeaders", "*");
/* 124:121 */         ((FilterHolder)localObject4).setAsyncSupported(true);
/* 125:    */       }
/* 126:124 */       ((HandlerList)localObject1).addHandler((Handler)localObject2);
/* 127:125 */       ((HandlerList)localObject1).addHandler(new DefaultHandler());
/* 128:    */       
/* 129:127 */       apiServer.setHandler((Handler)localObject1);
/* 130:128 */       apiServer.setStopAtShutdown(true);
/* 131:    */       
/* 132:130 */       ThreadPool.runBeforeStart(new Runnable()
/* 133:    */       {
/* 134:    */         public void run()
/* 135:    */         {
/* 136:    */           try
/* 137:    */           {
/* 138:134 */             API.apiServer.start();
/* 139:135 */             Logger.logMessage("Started API server at " + this.val$host + ":" + i);
/* 140:    */           }
/* 141:    */           catch (Exception localException)
/* 142:    */           {
/* 143:137 */             Logger.logErrorMessage("Failed to start API server", localException);
/* 144:138 */             throw new RuntimeException(localException.toString(), localException);
/* 145:    */           }
/* 146:    */         }
/* 147:138 */       }, true);
/* 148:    */     }
/* 149:    */     else
/* 150:    */     {
/* 151:145 */       apiServer = null;
/* 152:146 */       Logger.logMessage("API server not enabled");
/* 153:    */     }
/* 154:    */   }
/* 155:    */   
/* 156:    */   public static void shutdown()
/* 157:    */   {
/* 158:154 */     if (apiServer != null) {
/* 159:    */       try
/* 160:    */       {
/* 161:156 */         apiServer.stop();
/* 162:    */       }
/* 163:    */       catch (Exception localException)
/* 164:    */       {
/* 165:158 */         Logger.logShutdownMessage("Failed to stop API server", localException);
/* 166:    */       }
/* 167:    */     }
/* 168:    */   }
/* 169:    */   
/* 170:    */   public static void init() {}
/* 171:    */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.http.API
 * JD-Core Version:    0.7.1
 */