/*   1:    */ package nxt.util;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.HashMap;
/*   5:    */ import java.util.Iterator;
/*   6:    */ import java.util.List;
/*   7:    */ import java.util.Map;
/*   8:    */ import java.util.Map.Entry;
/*   9:    */ import java.util.Set;
/*  10:    */ import java.util.concurrent.ExecutorService;
/*  11:    */ import java.util.concurrent.Executors;
/*  12:    */ import java.util.concurrent.ScheduledExecutorService;
/*  13:    */ import java.util.concurrent.TimeUnit;
/*  14:    */ import nxt.Nxt;
/*  15:    */ 
/*  16:    */ public final class ThreadPool
/*  17:    */ {
/*  18:    */   private static ScheduledExecutorService scheduledThreadPool;
/*  19: 17 */   private static Map<Runnable, Long> backgroundJobs = new HashMap();
/*  20: 18 */   private static List<Runnable> beforeStartJobs = new ArrayList();
/*  21: 19 */   private static List<Runnable> lastBeforeStartJobs = new ArrayList();
/*  22: 20 */   private static List<Runnable> afterStartJobs = new ArrayList();
/*  23:    */   
/*  24:    */   public static synchronized void runBeforeStart(Runnable paramRunnable, boolean paramBoolean)
/*  25:    */   {
/*  26: 23 */     if (scheduledThreadPool != null) {
/*  27: 24 */       throw new IllegalStateException("Executor service already started");
/*  28:    */     }
/*  29: 26 */     if (paramBoolean) {
/*  30: 27 */       lastBeforeStartJobs.add(paramRunnable);
/*  31:    */     } else {
/*  32: 29 */       beforeStartJobs.add(paramRunnable);
/*  33:    */     }
/*  34:    */   }
/*  35:    */   
/*  36:    */   public static synchronized void runAfterStart(Runnable paramRunnable)
/*  37:    */   {
/*  38: 34 */     afterStartJobs.add(paramRunnable);
/*  39:    */   }
/*  40:    */   
/*  41:    */   public static synchronized void scheduleThread(String paramString, Runnable paramRunnable, int paramInt)
/*  42:    */   {
/*  43: 38 */     scheduleThread(paramString, paramRunnable, paramInt, TimeUnit.SECONDS);
/*  44:    */   }
/*  45:    */   
/*  46:    */   public static synchronized void scheduleThread(String paramString, Runnable paramRunnable, int paramInt, TimeUnit paramTimeUnit)
/*  47:    */   {
/*  48: 42 */     if (scheduledThreadPool != null) {
/*  49: 43 */       throw new IllegalStateException("Executor service already started, no new jobs accepted");
/*  50:    */     }
/*  51: 45 */     if (!Nxt.getBooleanProperty("nxt.disable" + paramString + "Thread").booleanValue()) {
/*  52: 46 */       backgroundJobs.put(paramRunnable, Long.valueOf(paramTimeUnit.toMillis(paramInt)));
/*  53:    */     } else {
/*  54: 48 */       Logger.logMessage("Will not run " + paramString + " thread");
/*  55:    */     }
/*  56:    */   }
/*  57:    */   
/*  58:    */   public static synchronized void start(int paramInt)
/*  59:    */   {
/*  60: 53 */     if (scheduledThreadPool != null) {
/*  61: 54 */       throw new IllegalStateException("Executor service already started");
/*  62:    */     }
/*  63: 57 */     Logger.logDebugMessage("Running " + beforeStartJobs.size() + " tasks...");
/*  64: 58 */     runAll(beforeStartJobs);
/*  65: 59 */     beforeStartJobs = null;
/*  66:    */     
/*  67: 61 */     Logger.logDebugMessage("Running " + lastBeforeStartJobs.size() + " final tasks...");
/*  68: 62 */     runAll(lastBeforeStartJobs);
/*  69: 63 */     lastBeforeStartJobs = null;
/*  70:    */     
/*  71: 65 */     Logger.logDebugMessage("Starting " + backgroundJobs.size() + " background jobs");
/*  72: 66 */     scheduledThreadPool = Executors.newScheduledThreadPool(backgroundJobs.size());
/*  73: 67 */     for (Object localObject = backgroundJobs.entrySet().iterator(); ((Iterator)localObject).hasNext();)
/*  74:    */     {
/*  75: 67 */       Map.Entry localEntry = (Map.Entry)((Iterator)localObject).next();
/*  76: 68 */       scheduledThreadPool.scheduleWithFixedDelay((Runnable)localEntry.getKey(), 0L, Math.max(((Long)localEntry.getValue()).longValue() / paramInt, 1L), TimeUnit.MILLISECONDS);
/*  77:    */     }
/*  78: 70 */     backgroundJobs = null;
/*  79:    */     
/*  80: 72 */     Logger.logDebugMessage("Starting " + afterStartJobs.size() + " delayed tasks");
/*  81: 73 */     localObject = new Thread()
/*  82:    */     {
/*  83:    */       public void run()
/*  84:    */       {
/*  85: 76 */         ThreadPool.runAll(ThreadPool.afterStartJobs);
/*  86: 77 */         ThreadPool.access$002(null);
/*  87:    */       }
/*  88: 79 */     };
/*  89: 80 */     ((Thread)localObject).setDaemon(true);
/*  90: 81 */     ((Thread)localObject).start();
/*  91:    */   }
/*  92:    */   
/*  93:    */   public static synchronized void shutdown()
/*  94:    */   {
/*  95: 85 */     if (scheduledThreadPool != null)
/*  96:    */     {
/*  97: 86 */       Logger.logShutdownMessage("Stopping background jobs...");
/*  98: 87 */       shutdownExecutor(scheduledThreadPool);
/*  99: 88 */       scheduledThreadPool = null;
/* 100: 89 */       Logger.logShutdownMessage("...Done");
/* 101:    */     }
/* 102:    */   }
/* 103:    */   
/* 104:    */   public static void shutdownExecutor(ExecutorService paramExecutorService)
/* 105:    */   {
/* 106: 94 */     paramExecutorService.shutdown();
/* 107:    */     try
/* 108:    */     {
/* 109: 96 */       paramExecutorService.awaitTermination(10L, TimeUnit.SECONDS);
/* 110:    */     }
/* 111:    */     catch (InterruptedException localInterruptedException)
/* 112:    */     {
/* 113: 98 */       Thread.currentThread().interrupt();
/* 114:    */     }
/* 115:100 */     if (!paramExecutorService.isTerminated())
/* 116:    */     {
/* 117:101 */       Logger.logShutdownMessage("some threads didn't terminate, forcing shutdown");
/* 118:102 */       paramExecutorService.shutdownNow();
/* 119:    */     }
/* 120:    */   }
/* 121:    */   
/* 122:    */   private static void runAll(List<Runnable> paramList)
/* 123:    */   {
/* 124:107 */     ArrayList localArrayList = new ArrayList();
/* 125:108 */     final StringBuffer localStringBuffer = new StringBuffer();
/* 126:109 */     for (Iterator localIterator = paramList.iterator(); localIterator.hasNext();)
/* 127:    */     {
/* 128:109 */       localObject = (Runnable)localIterator.next();
/* 129:110 */       Thread local2 = new Thread()
/* 130:    */       {
/* 131:    */         public void run()
/* 132:    */         {
/* 133:    */           try
/* 134:    */           {
/* 135:114 */             this.val$runnable.run();
/* 136:    */           }
/* 137:    */           catch (Throwable localThrowable)
/* 138:    */           {
/* 139:116 */             localStringBuffer.append(localThrowable.getMessage()).append('\n');
/* 140:117 */             throw localThrowable;
/* 141:    */           }
/* 142:    */         }
/* 143:120 */       };
/* 144:121 */       local2.setDaemon(true);
/* 145:122 */       local2.start();
/* 146:123 */       localArrayList.add(local2);
/* 147:    */     }
/* 148:    */     Object localObject;
/* 149:125 */     for (localIterator = localArrayList.iterator(); localIterator.hasNext();)
/* 150:    */     {
/* 151:125 */       localObject = (Thread)localIterator.next();
/* 152:    */       try
/* 153:    */       {
/* 154:127 */         ((Thread)localObject).join();
/* 155:    */       }
/* 156:    */       catch (InterruptedException localInterruptedException)
/* 157:    */       {
/* 158:129 */         Thread.currentThread().interrupt();
/* 159:    */       }
/* 160:    */     }
/* 161:132 */     if (localStringBuffer.length() > 0) {
/* 162:133 */       throw new RuntimeException("Errors running startup tasks:\n" + localStringBuffer.toString());
/* 163:    */     }
/* 164:    */   }
/* 165:    */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.util.ThreadPool
 * JD-Core Version:    0.7.1
 */