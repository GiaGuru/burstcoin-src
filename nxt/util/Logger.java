/*   1:    */ package nxt.util;
/*   2:    */ 
/*   3:    */ import java.io.ByteArrayInputStream;
/*   4:    */ import java.io.ByteArrayOutputStream;
/*   5:    */ import java.io.IOException;
/*   6:    */ import java.io.InputStream;
/*   7:    */ import java.io.OutputStream;
/*   8:    */ import java.io.PrintStream;
/*   9:    */ import java.util.Properties;
/*  10:    */ import java.util.logging.LogManager;
/*  11:    */ import nxt.Nxt;
/*  12:    */ import org.slf4j.LoggerFactory;
/*  13:    */ 
/*  14:    */ public final class Logger
/*  15:    */ {
/*  16:    */   public static enum Event
/*  17:    */   {
/*  18: 19 */     MESSAGE,  EXCEPTION;
/*  19:    */     
/*  20:    */     private Event() {}
/*  21:    */   }
/*  22:    */   
/*  23:    */   public static enum Level
/*  24:    */   {
/*  25: 24 */     DEBUG,  INFO,  WARN,  ERROR;
/*  26:    */     
/*  27:    */     private Level() {}
/*  28:    */   }
/*  29:    */   
/*  30: 28 */   private static final Listeners<String, Event> messageListeners = new Listeners();
/*  31: 31 */   private static final Listeners<Exception, Event> exceptionListeners = new Listeners();
/*  32:    */   private static final org.slf4j.Logger log;
/*  33:    */   private static final boolean enableStackTraces;
/*  34:    */   private static final boolean enableLogTraceback;
/*  35:    */   
/*  36:    */   static
/*  37:    */   {
/*  38: 57 */     String str = System.getProperty("java.util.logging.manager");
/*  39: 58 */     System.setProperty("java.util.logging.manager", "nxt.util.NxtLogManager");
/*  40: 59 */     if (!(LogManager.getLogManager() instanceof NxtLogManager)) {
/*  41: 60 */       System.setProperty("java.util.logging.manager", str != null ? str : "java.util.logging.LogManager");
/*  42:    */     }
/*  43: 63 */     if (!Boolean.getBoolean("nxt.doNotConfigureLogging")) {
/*  44:    */       try
/*  45:    */       {
/*  46: 65 */         int i = 0;
/*  47: 66 */         Properties localProperties = new Properties();
/*  48: 67 */         Object localObject1 = ClassLoader.getSystemResourceAsStream("logging-default.properties");Object localObject2 = null;
/*  49:    */         try
/*  50:    */         {
/*  51: 68 */           if (localObject1 != null)
/*  52:    */           {
/*  53: 69 */             localProperties.load((InputStream)localObject1);
/*  54: 70 */             i = 1;
/*  55:    */           }
/*  56:    */         }
/*  57:    */         catch (Throwable localThrowable2)
/*  58:    */         {
/*  59: 67 */           localObject2 = localThrowable2;throw localThrowable2;
/*  60:    */         }
/*  61:    */         finally
/*  62:    */         {
/*  63: 72 */           if (localObject1 != null) {
/*  64: 72 */             if (localObject2 != null) {
/*  65:    */               try
/*  66:    */               {
/*  67: 72 */                 ((InputStream)localObject1).close();
/*  68:    */               }
/*  69:    */               catch (Throwable localThrowable5)
/*  70:    */               {
/*  71: 72 */                 ((Throwable)localObject2).addSuppressed(localThrowable5);
/*  72:    */               }
/*  73:    */             } else {
/*  74: 72 */               ((InputStream)localObject1).close();
/*  75:    */             }
/*  76:    */           }
/*  77:    */         }
/*  78: 73 */         localObject1 = ClassLoader.getSystemResourceAsStream("logging.properties");localObject2 = null;
/*  79:    */         try
/*  80:    */         {
/*  81: 74 */           if (localObject1 != null)
/*  82:    */           {
/*  83: 75 */             localProperties.load((InputStream)localObject1);
/*  84: 76 */             i = 1;
/*  85:    */           }
/*  86:    */         }
/*  87:    */         catch (Throwable localThrowable4)
/*  88:    */         {
/*  89: 73 */           localObject2 = localThrowable4;throw localThrowable4;
/*  90:    */         }
/*  91:    */         finally
/*  92:    */         {
/*  93: 78 */           if (localObject1 != null) {
/*  94: 78 */             if (localObject2 != null) {
/*  95:    */               try
/*  96:    */               {
/*  97: 78 */                 ((InputStream)localObject1).close();
/*  98:    */               }
/*  99:    */               catch (Throwable localThrowable6)
/* 100:    */               {
/* 101: 78 */                 ((Throwable)localObject2).addSuppressed(localThrowable6);
/* 102:    */               }
/* 103:    */             } else {
/* 104: 78 */               ((InputStream)localObject1).close();
/* 105:    */             }
/* 106:    */           }
/* 107:    */         }
/* 108: 79 */         if (i != 0)
/* 109:    */         {
/* 110: 80 */           localObject1 = new ByteArrayOutputStream();
/* 111: 81 */           localProperties.store((OutputStream)localObject1, "logging properties");
/* 112: 82 */           localObject2 = new ByteArrayInputStream(((ByteArrayOutputStream)localObject1).toByteArray());
/* 113: 83 */           LogManager.getLogManager().readConfiguration((InputStream)localObject2);
/* 114: 84 */           ((ByteArrayInputStream)localObject2).close();
/* 115: 85 */           ((ByteArrayOutputStream)localObject1).close();
/* 116:    */         }
/* 117: 87 */         BriefLogFormatter.init();
/* 118:    */       }
/* 119:    */       catch (IOException localIOException)
/* 120:    */       {
/* 121: 89 */         throw new RuntimeException("Error loading logging properties", localIOException);
/* 122:    */       }
/* 123:    */     }
/* 124: 92 */     log = LoggerFactory.getLogger(Nxt.class);
/* 125: 93 */     enableStackTraces = Nxt.getBooleanProperty("nxt.enableStackTraces").booleanValue();
/* 126: 94 */     enableLogTraceback = Nxt.getBooleanProperty("nxt.enableLogTraceback").booleanValue();
/* 127: 95 */     logInfoMessage("logging enabled");
/* 128:    */   }
/* 129:    */   
/* 130:    */   public static void shutdown()
/* 131:    */   {
/* 132:104 */     if ((LogManager.getLogManager() instanceof NxtLogManager)) {
/* 133:105 */       ((NxtLogManager)LogManager.getLogManager()).nxtShutdown();
/* 134:    */     }
/* 135:    */   }
/* 136:    */   
/* 137:    */   public static boolean addMessageListener(Listener<String> paramListener, Event paramEvent)
/* 138:    */   {
/* 139:117 */     return messageListeners.addListener(paramListener, paramEvent);
/* 140:    */   }
/* 141:    */   
/* 142:    */   public static boolean addExceptionListener(Listener<Exception> paramListener, Event paramEvent)
/* 143:    */   {
/* 144:128 */     return exceptionListeners.addListener(paramListener, paramEvent);
/* 145:    */   }
/* 146:    */   
/* 147:    */   public static boolean removeMessageListener(Listener<String> paramListener, Event paramEvent)
/* 148:    */   {
/* 149:139 */     return messageListeners.removeListener(paramListener, paramEvent);
/* 150:    */   }
/* 151:    */   
/* 152:    */   public static boolean removeExceptionListener(Listener<Exception> paramListener, Event paramEvent)
/* 153:    */   {
/* 154:150 */     return exceptionListeners.removeListener(paramListener, paramEvent);
/* 155:    */   }
/* 156:    */   
/* 157:    */   public static void logMessage(String paramString)
/* 158:    */   {
/* 159:159 */     doLog(Level.INFO, paramString, null);
/* 160:    */   }
/* 161:    */   
/* 162:    */   public static void logMessage(String paramString, Exception paramException)
/* 163:    */   {
/* 164:169 */     doLog(Level.ERROR, paramString, paramException);
/* 165:    */   }
/* 166:    */   
/* 167:    */   public static void logShutdownMessage(String paramString)
/* 168:    */   {
/* 169:173 */     if ((LogManager.getLogManager() instanceof NxtLogManager)) {
/* 170:174 */       logMessage(paramString);
/* 171:    */     } else {
/* 172:176 */       System.out.println(paramString);
/* 173:    */     }
/* 174:    */   }
/* 175:    */   
/* 176:    */   public static void logShutdownMessage(String paramString, Exception paramException)
/* 177:    */   {
/* 178:181 */     if ((LogManager.getLogManager() instanceof NxtLogManager))
/* 179:    */     {
/* 180:182 */       logMessage(paramString, paramException);
/* 181:    */     }
/* 182:    */     else
/* 183:    */     {
/* 184:184 */       System.out.println(paramString);
/* 185:185 */       System.out.println(paramException.toString());
/* 186:    */     }
/* 187:    */   }
/* 188:    */   
/* 189:    */   public static void logErrorMessage(String paramString)
/* 190:    */   {
/* 191:194 */     doLog(Level.ERROR, paramString, null);
/* 192:    */   }
/* 193:    */   
/* 194:    */   public static void logErrorMessage(String paramString, Exception paramException)
/* 195:    */   {
/* 196:204 */     doLog(Level.ERROR, paramString, paramException);
/* 197:    */   }
/* 198:    */   
/* 199:    */   public static void logWarningMessage(String paramString)
/* 200:    */   {
/* 201:213 */     doLog(Level.WARN, paramString, null);
/* 202:    */   }
/* 203:    */   
/* 204:    */   public static void logWarningMessage(String paramString, Exception paramException)
/* 205:    */   {
/* 206:223 */     doLog(Level.WARN, paramString, paramException);
/* 207:    */   }
/* 208:    */   
/* 209:    */   public static void logInfoMessage(String paramString)
/* 210:    */   {
/* 211:232 */     doLog(Level.INFO, paramString, null);
/* 212:    */   }
/* 213:    */   
/* 214:    */   public static void logInfoMessage(String paramString, Exception paramException)
/* 215:    */   {
/* 216:242 */     doLog(Level.INFO, paramString, paramException);
/* 217:    */   }
/* 218:    */   
/* 219:    */   public static void logDebugMessage(String paramString)
/* 220:    */   {
/* 221:251 */     doLog(Level.DEBUG, paramString, null);
/* 222:    */   }
/* 223:    */   
/* 224:    */   public static void logDebugMessage(String paramString, Exception paramException)
/* 225:    */   {
/* 226:261 */     doLog(Level.DEBUG, paramString, paramException);
/* 227:    */   }
/* 228:    */   
/* 229:    */   private static void doLog(Level paramLevel, String paramString, Exception paramException)
/* 230:    */   {
/* 231:272 */     String str1 = paramString;
/* 232:273 */     Exception localException = paramException;
/* 233:277 */     if (enableLogTraceback)
/* 234:    */     {
/* 235:278 */       StackTraceElement localStackTraceElement = Thread.currentThread().getStackTrace()[3];
/* 236:279 */       String str2 = localStackTraceElement.getClassName();
/* 237:280 */       int i = str2.lastIndexOf('.');
/* 238:281 */       if (i != -1) {
/* 239:282 */         str2 = str2.substring(i + 1);
/* 240:    */       }
/* 241:283 */       str1 = str2 + "." + localStackTraceElement.getMethodName() + ": " + str1;
/* 242:    */     }
/* 243:288 */     if ((localException != null) && 
/* 244:289 */       (!enableStackTraces))
/* 245:    */     {
/* 246:290 */       str1 = str1 + "\n" + paramException.toString();
/* 247:291 */       localException = null;
/* 248:    */     }
/* 249:297 */     switch (paramLevel)
/* 250:    */     {
/* 251:    */     case DEBUG: 
/* 252:299 */       log.debug(str1, localException);
/* 253:300 */       break;
/* 254:    */     case INFO: 
/* 255:302 */       log.info(str1, localException);
/* 256:303 */       break;
/* 257:    */     case WARN: 
/* 258:305 */       log.warn(str1, localException);
/* 259:306 */       break;
/* 260:    */     case ERROR: 
/* 261:308 */       log.error(str1, localException);
/* 262:    */     }
/* 263:314 */     if (paramException != null) {
/* 264:315 */       exceptionListeners.notify(paramException, Event.EXCEPTION);
/* 265:    */     } else {
/* 266:317 */       messageListeners.notify(paramString, Event.MESSAGE);
/* 267:    */     }
/* 268:    */   }
/* 269:    */   
/* 270:    */   public static void init() {}
/* 271:    */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.util.Logger
 * JD-Core Version:    0.7.1
 */