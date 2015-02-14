/*   1:    */ package nxt.db;
/*   2:    */ 
/*   3:    */ import java.sql.Connection;
/*   4:    */ import java.sql.SQLException;
/*   5:    */ import java.sql.Statement;
/*   6:    */ import java.util.HashMap;
/*   7:    */ import java.util.Map;
/*   8:    */ import nxt.Constants;
/*   9:    */ import nxt.Nxt;
/*  10:    */ import nxt.util.Logger;
/*  11:    */ import org.h2.jdbcx.JdbcConnectionPool;
/*  12:    */ 
/*  13:    */ public final class Db
/*  14:    */ {
/*  15:    */   private static final JdbcConnectionPool cp;
/*  16:    */   private static volatile int maxActiveConnections;
/*  17: 19 */   private static final ThreadLocal<DbConnection> localConnection = new ThreadLocal();
/*  18: 20 */   private static final ThreadLocal<Map<String, Map<DbKey, Object>>> transactionCaches = new ThreadLocal();
/*  19:    */   
/*  20:    */   private static final class DbConnection
/*  21:    */     extends FilteredConnection
/*  22:    */   {
/*  23:    */     private DbConnection(Connection paramConnection)
/*  24:    */     {
/*  25: 25 */       super();
/*  26:    */     }
/*  27:    */     
/*  28:    */     public void setAutoCommit(boolean paramBoolean)
/*  29:    */       throws SQLException
/*  30:    */     {
/*  31: 30 */       throw new UnsupportedOperationException("Use Db.beginTransaction() to start a new transaction");
/*  32:    */     }
/*  33:    */     
/*  34:    */     public void commit()
/*  35:    */       throws SQLException
/*  36:    */     {
/*  37: 35 */       if (Db.localConnection.get() == null)
/*  38:    */       {
/*  39: 36 */         super.commit();
/*  40:    */       }
/*  41:    */       else
/*  42:    */       {
/*  43: 37 */         if (!equals(Db.localConnection.get())) {
/*  44: 38 */           throw new IllegalStateException("Previous connection not committed");
/*  45:    */         }
/*  46: 40 */         throw new UnsupportedOperationException("Use Db.commitTransaction() to commit the transaction");
/*  47:    */       }
/*  48:    */     }
/*  49:    */     
/*  50:    */     private void doCommit()
/*  51:    */       throws SQLException
/*  52:    */     {
/*  53: 45 */       super.commit();
/*  54:    */     }
/*  55:    */     
/*  56:    */     public void rollback()
/*  57:    */       throws SQLException
/*  58:    */     {
/*  59: 50 */       if (Db.localConnection.get() == null)
/*  60:    */       {
/*  61: 51 */         super.rollback();
/*  62:    */       }
/*  63:    */       else
/*  64:    */       {
/*  65: 52 */         if (!equals(Db.localConnection.get())) {
/*  66: 53 */           throw new IllegalStateException("Previous connection not committed");
/*  67:    */         }
/*  68: 55 */         throw new UnsupportedOperationException("Use Db.rollbackTransaction() to rollback the transaction");
/*  69:    */       }
/*  70:    */     }
/*  71:    */     
/*  72:    */     private void doRollback()
/*  73:    */       throws SQLException
/*  74:    */     {
/*  75: 60 */       super.rollback();
/*  76:    */     }
/*  77:    */     
/*  78:    */     public void close()
/*  79:    */       throws SQLException
/*  80:    */     {
/*  81: 65 */       if (Db.localConnection.get() == null) {
/*  82: 66 */         super.close();
/*  83: 67 */       } else if (!equals(Db.localConnection.get())) {
/*  84: 68 */         throw new IllegalStateException("Previous connection not committed");
/*  85:    */       }
/*  86:    */     }
/*  87:    */   }
/*  88:    */   
/*  89:    */   static
/*  90:    */   {
/*  91: 77 */     long l = Nxt.getIntProperty("nxt.dbCacheKB");
/*  92: 78 */     if (l == 0L) {
/*  93: 79 */       l = Math.min(256L, Math.max(16L, (Runtime.getRuntime().maxMemory() / 1048576L - 128L) / 2L)) * 1024L;
/*  94:    */     }
/*  95: 81 */     String str = Constants.isTestnet ? Nxt.getStringProperty("nxt.testDbUrl") : Nxt.getStringProperty("nxt.dbUrl");
/*  96: 82 */     if (!str.contains("CACHE_SIZE=")) {
/*  97: 83 */       str = str + ";CACHE_SIZE=" + l;
/*  98:    */     }
/*  99: 85 */     Logger.logDebugMessage("Database jdbc url set to: " + str);
/* 100: 86 */     cp = JdbcConnectionPool.create(str, "sa", "sa");
/* 101: 87 */     cp.setMaxConnections(Nxt.getIntProperty("nxt.maxDbConnections"));
/* 102: 88 */     cp.setLoginTimeout(Nxt.getIntProperty("nxt.dbLoginTimeout"));
/* 103: 89 */     int i = Nxt.getIntProperty("nxt.dbDefaultLockTimeout") * 1000;
/* 104:    */     try
/* 105:    */     {
/* 106: 90 */       Connection localConnection1 = cp.getConnection();Object localObject1 = null;
/* 107:    */       try
/* 108:    */       {
/* 109: 91 */         Statement localStatement = localConnection1.createStatement();Object localObject2 = null;
/* 110:    */         try
/* 111:    */         {
/* 112: 92 */           localStatement.executeUpdate("SET DEFAULT_LOCK_TIMEOUT " + i);
/* 113:    */         }
/* 114:    */         catch (Throwable localThrowable4)
/* 115:    */         {
/* 116: 90 */           localObject2 = localThrowable4;throw localThrowable4;
/* 117:    */         }
/* 118:    */         finally {}
/* 119:    */       }
/* 120:    */       catch (Throwable localThrowable2)
/* 121:    */       {
/* 122: 90 */         localObject1 = localThrowable2;throw localThrowable2;
/* 123:    */       }
/* 124:    */       finally
/* 125:    */       {
/* 126: 93 */         if (localConnection1 != null) {
/* 127: 93 */           if (localObject1 != null) {
/* 128:    */             try
/* 129:    */             {
/* 130: 93 */               localConnection1.close();
/* 131:    */             }
/* 132:    */             catch (Throwable localThrowable6)
/* 133:    */             {
/* 134: 93 */               ((Throwable)localObject1).addSuppressed(localThrowable6);
/* 135:    */             }
/* 136:    */           } else {
/* 137: 93 */             localConnection1.close();
/* 138:    */           }
/* 139:    */         }
/* 140:    */       }
/* 141:    */     }
/* 142:    */     catch (SQLException localSQLException)
/* 143:    */     {
/* 144: 94 */       throw new RuntimeException(localSQLException.toString(), localSQLException);
/* 145:    */     }
/* 146:    */   }
/* 147:    */   
/* 148:    */   public static void analyzeTables()
/* 149:    */   {
/* 150:    */     try
/* 151:    */     {
/* 152: 99 */       Connection localConnection1 = cp.getConnection();Object localObject1 = null;
/* 153:    */       try
/* 154:    */       {
/* 155:100 */         Statement localStatement = localConnection1.createStatement();Object localObject2 = null;
/* 156:    */         try
/* 157:    */         {
/* 158:101 */           localStatement.execute("ANALYZE SAMPLE_SIZE 0");
/* 159:    */         }
/* 160:    */         catch (Throwable localThrowable4)
/* 161:    */         {
/* 162: 99 */           localObject2 = localThrowable4;throw localThrowable4;
/* 163:    */         }
/* 164:    */         finally {}
/* 165:    */       }
/* 166:    */       catch (Throwable localThrowable2)
/* 167:    */       {
/* 168: 99 */         localObject1 = localThrowable2;throw localThrowable2;
/* 169:    */       }
/* 170:    */       finally
/* 171:    */       {
/* 172:102 */         if (localConnection1 != null) {
/* 173:102 */           if (localObject1 != null) {
/* 174:    */             try
/* 175:    */             {
/* 176:102 */               localConnection1.close();
/* 177:    */             }
/* 178:    */             catch (Throwable localThrowable6)
/* 179:    */             {
/* 180:102 */               ((Throwable)localObject1).addSuppressed(localThrowable6);
/* 181:    */             }
/* 182:    */           } else {
/* 183:102 */             localConnection1.close();
/* 184:    */           }
/* 185:    */         }
/* 186:    */       }
/* 187:    */     }
/* 188:    */     catch (SQLException localSQLException)
/* 189:    */     {
/* 190:103 */       throw new RuntimeException(localSQLException.toString(), localSQLException);
/* 191:    */     }
/* 192:    */   }
/* 193:    */   
/* 194:    */   public static void shutdown()
/* 195:    */   {
/* 196:    */     try
/* 197:    */     {
/* 198:109 */       Connection localConnection1 = cp.getConnection();
/* 199:110 */       Statement localStatement = localConnection1.createStatement();
/* 200:111 */       localStatement.execute("SHUTDOWN COMPACT");
/* 201:112 */       Logger.logShutdownMessage("Database shutdown completed");
/* 202:    */     }
/* 203:    */     catch (SQLException localSQLException)
/* 204:    */     {
/* 205:114 */       Logger.logShutdownMessage(localSQLException.toString(), localSQLException);
/* 206:    */     }
/* 207:    */   }
/* 208:    */   
/* 209:    */   private static Connection getPooledConnection()
/* 210:    */     throws SQLException
/* 211:    */   {
/* 212:119 */     Connection localConnection1 = cp.getConnection();
/* 213:120 */     int i = cp.getActiveConnections();
/* 214:121 */     if (i > maxActiveConnections)
/* 215:    */     {
/* 216:122 */       maxActiveConnections = i;
/* 217:123 */       Logger.logDebugMessage("Database connection pool current size: " + i);
/* 218:    */     }
/* 219:125 */     return localConnection1;
/* 220:    */   }
/* 221:    */   
/* 222:    */   public static Connection getConnection()
/* 223:    */     throws SQLException
/* 224:    */   {
/* 225:129 */     Connection localConnection1 = (Connection)localConnection.get();
/* 226:130 */     if (localConnection1 != null) {
/* 227:131 */       return localConnection1;
/* 228:    */     }
/* 229:133 */     localConnection1 = getPooledConnection();
/* 230:134 */     localConnection1.setAutoCommit(true);
/* 231:135 */     return new DbConnection(localConnection1, null);
/* 232:    */   }
/* 233:    */   
/* 234:    */   static Map<DbKey, Object> getCache(String paramString)
/* 235:    */   {
/* 236:139 */     if (!isInTransaction()) {
/* 237:140 */       throw new IllegalStateException("Not in transaction");
/* 238:    */     }
/* 239:142 */     Object localObject = (Map)((Map)transactionCaches.get()).get(paramString);
/* 240:143 */     if (localObject == null)
/* 241:    */     {
/* 242:144 */       localObject = new HashMap();
/* 243:145 */       ((Map)transactionCaches.get()).put(paramString, localObject);
/* 244:    */     }
/* 245:147 */     return (Map<DbKey, Object>)localObject;
/* 246:    */   }
/* 247:    */   
/* 248:    */   public static boolean isInTransaction()
/* 249:    */   {
/* 250:151 */     return localConnection.get() != null;
/* 251:    */   }
/* 252:    */   
/* 253:    */   public static Connection beginTransaction()
/* 254:    */   {
/* 255:155 */     if (localConnection.get() != null) {
/* 256:156 */       throw new IllegalStateException("Transaction already in progress");
/* 257:    */     }
/* 258:    */     try
/* 259:    */     {
/* 260:159 */       Object localObject = getPooledConnection();
/* 261:160 */       ((Connection)localObject).setAutoCommit(false);
/* 262:161 */       localObject = new DbConnection((Connection)localObject, null);
/* 263:162 */       localConnection.set((DbConnection)localObject);
/* 264:163 */       transactionCaches.set(new HashMap());
/* 265:164 */       return (Connection)localObject;
/* 266:    */     }
/* 267:    */     catch (SQLException localSQLException)
/* 268:    */     {
/* 269:166 */       throw new RuntimeException(localSQLException.toString(), localSQLException);
/* 270:    */     }
/* 271:    */   }
/* 272:    */   
/* 273:    */   public static void commitTransaction()
/* 274:    */   {
/* 275:171 */     DbConnection localDbConnection = (DbConnection)localConnection.get();
/* 276:172 */     if (localDbConnection == null) {
/* 277:173 */       throw new IllegalStateException("Not in transaction");
/* 278:    */     }
/* 279:    */     try
/* 280:    */     {
/* 281:176 */       localDbConnection.doCommit();
/* 282:    */     }
/* 283:    */     catch (SQLException localSQLException)
/* 284:    */     {
/* 285:178 */       throw new RuntimeException(localSQLException.toString(), localSQLException);
/* 286:    */     }
/* 287:    */   }
/* 288:    */   
/* 289:    */   public static void rollbackTransaction()
/* 290:    */   {
/* 291:183 */     DbConnection localDbConnection = (DbConnection)localConnection.get();
/* 292:184 */     if (localDbConnection == null) {
/* 293:185 */       throw new IllegalStateException("Not in transaction");
/* 294:    */     }
/* 295:    */     try
/* 296:    */     {
/* 297:188 */       localDbConnection.doRollback();
/* 298:    */     }
/* 299:    */     catch (SQLException localSQLException)
/* 300:    */     {
/* 301:190 */       throw new RuntimeException(localSQLException.toString(), localSQLException);
/* 302:    */     }
/* 303:192 */     ((Map)transactionCaches.get()).clear();
/* 304:    */   }
/* 305:    */   
/* 306:    */   public static void endTransaction()
/* 307:    */   {
/* 308:196 */     Connection localConnection1 = (Connection)localConnection.get();
/* 309:197 */     if (localConnection1 == null) {
/* 310:198 */       throw new IllegalStateException("Not in transaction");
/* 311:    */     }
/* 312:200 */     localConnection.set(null);
/* 313:201 */     ((Map)transactionCaches.get()).clear();
/* 314:202 */     transactionCaches.set(null);
/* 315:203 */     DbUtils.close(new AutoCloseable[] { localConnection1 });
/* 316:    */   }
/* 317:    */   
/* 318:    */   public static void init() {}
/* 319:    */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.db.Db
 * JD-Core Version:    0.7.1
 */