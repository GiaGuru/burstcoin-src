/*   1:    */ package nxt.db;
/*   2:    */ 
/*   3:    */ import java.sql.Array;
/*   4:    */ import java.sql.Blob;
/*   5:    */ import java.sql.CallableStatement;
/*   6:    */ import java.sql.Clob;
/*   7:    */ import java.sql.Connection;
/*   8:    */ import java.sql.DatabaseMetaData;
/*   9:    */ import java.sql.NClob;
/*  10:    */ import java.sql.PreparedStatement;
/*  11:    */ import java.sql.SQLClientInfoException;
/*  12:    */ import java.sql.SQLException;
/*  13:    */ import java.sql.SQLWarning;
/*  14:    */ import java.sql.SQLXML;
/*  15:    */ import java.sql.Savepoint;
/*  16:    */ import java.sql.Statement;
/*  17:    */ import java.sql.Struct;
/*  18:    */ import java.util.Map;
/*  19:    */ import java.util.Properties;
/*  20:    */ import java.util.concurrent.Executor;
/*  21:    */ 
/*  22:    */ public class FilteredConnection
/*  23:    */   implements Connection
/*  24:    */ {
/*  25:    */   private final Connection con;
/*  26:    */   
/*  27:    */   public FilteredConnection(Connection paramConnection)
/*  28:    */   {
/*  29: 27 */     this.con = paramConnection;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public Statement createStatement()
/*  33:    */     throws SQLException
/*  34:    */   {
/*  35: 32 */     return this.con.createStatement();
/*  36:    */   }
/*  37:    */   
/*  38:    */   public PreparedStatement prepareStatement(String paramString)
/*  39:    */     throws SQLException
/*  40:    */   {
/*  41: 37 */     return this.con.prepareStatement(paramString);
/*  42:    */   }
/*  43:    */   
/*  44:    */   public CallableStatement prepareCall(String paramString)
/*  45:    */     throws SQLException
/*  46:    */   {
/*  47: 42 */     return this.con.prepareCall(paramString);
/*  48:    */   }
/*  49:    */   
/*  50:    */   public String nativeSQL(String paramString)
/*  51:    */     throws SQLException
/*  52:    */   {
/*  53: 47 */     return this.con.nativeSQL(paramString);
/*  54:    */   }
/*  55:    */   
/*  56:    */   public void setAutoCommit(boolean paramBoolean)
/*  57:    */     throws SQLException
/*  58:    */   {
/*  59: 52 */     this.con.setAutoCommit(paramBoolean);
/*  60:    */   }
/*  61:    */   
/*  62:    */   public boolean getAutoCommit()
/*  63:    */     throws SQLException
/*  64:    */   {
/*  65: 57 */     return this.con.getAutoCommit();
/*  66:    */   }
/*  67:    */   
/*  68:    */   public void commit()
/*  69:    */     throws SQLException
/*  70:    */   {
/*  71: 62 */     this.con.commit();
/*  72:    */   }
/*  73:    */   
/*  74:    */   public void rollback()
/*  75:    */     throws SQLException
/*  76:    */   {
/*  77: 67 */     this.con.rollback();
/*  78:    */   }
/*  79:    */   
/*  80:    */   public void close()
/*  81:    */     throws SQLException
/*  82:    */   {
/*  83: 72 */     this.con.close();
/*  84:    */   }
/*  85:    */   
/*  86:    */   public boolean isClosed()
/*  87:    */     throws SQLException
/*  88:    */   {
/*  89: 77 */     return this.con.isClosed();
/*  90:    */   }
/*  91:    */   
/*  92:    */   public DatabaseMetaData getMetaData()
/*  93:    */     throws SQLException
/*  94:    */   {
/*  95: 82 */     return this.con.getMetaData();
/*  96:    */   }
/*  97:    */   
/*  98:    */   public void setReadOnly(boolean paramBoolean)
/*  99:    */     throws SQLException
/* 100:    */   {
/* 101: 87 */     this.con.setReadOnly(paramBoolean);
/* 102:    */   }
/* 103:    */   
/* 104:    */   public boolean isReadOnly()
/* 105:    */     throws SQLException
/* 106:    */   {
/* 107: 92 */     return this.con.isReadOnly();
/* 108:    */   }
/* 109:    */   
/* 110:    */   public void setCatalog(String paramString)
/* 111:    */     throws SQLException
/* 112:    */   {
/* 113: 97 */     this.con.setCatalog(paramString);
/* 114:    */   }
/* 115:    */   
/* 116:    */   public String getCatalog()
/* 117:    */     throws SQLException
/* 118:    */   {
/* 119:102 */     return this.con.getCatalog();
/* 120:    */   }
/* 121:    */   
/* 122:    */   public void setTransactionIsolation(int paramInt)
/* 123:    */     throws SQLException
/* 124:    */   {
/* 125:107 */     this.con.setTransactionIsolation(paramInt);
/* 126:    */   }
/* 127:    */   
/* 128:    */   public int getTransactionIsolation()
/* 129:    */     throws SQLException
/* 130:    */   {
/* 131:112 */     return this.con.getTransactionIsolation();
/* 132:    */   }
/* 133:    */   
/* 134:    */   public SQLWarning getWarnings()
/* 135:    */     throws SQLException
/* 136:    */   {
/* 137:117 */     return this.con.getWarnings();
/* 138:    */   }
/* 139:    */   
/* 140:    */   public void clearWarnings()
/* 141:    */     throws SQLException
/* 142:    */   {
/* 143:122 */     this.con.clearWarnings();
/* 144:    */   }
/* 145:    */   
/* 146:    */   public Statement createStatement(int paramInt1, int paramInt2)
/* 147:    */     throws SQLException
/* 148:    */   {
/* 149:127 */     return this.con.createStatement(paramInt1, paramInt2);
/* 150:    */   }
/* 151:    */   
/* 152:    */   public PreparedStatement prepareStatement(String paramString, int paramInt1, int paramInt2)
/* 153:    */     throws SQLException
/* 154:    */   {
/* 155:132 */     return this.con.prepareStatement(paramString, paramInt1, paramInt2);
/* 156:    */   }
/* 157:    */   
/* 158:    */   public CallableStatement prepareCall(String paramString, int paramInt1, int paramInt2)
/* 159:    */     throws SQLException
/* 160:    */   {
/* 161:137 */     return this.con.prepareCall(paramString, paramInt1, paramInt2);
/* 162:    */   }
/* 163:    */   
/* 164:    */   public Map<String, Class<?>> getTypeMap()
/* 165:    */     throws SQLException
/* 166:    */   {
/* 167:142 */     return this.con.getTypeMap();
/* 168:    */   }
/* 169:    */   
/* 170:    */   public void setTypeMap(Map<String, Class<?>> paramMap)
/* 171:    */     throws SQLException
/* 172:    */   {
/* 173:147 */     this.con.setTypeMap(paramMap);
/* 174:    */   }
/* 175:    */   
/* 176:    */   public void setHoldability(int paramInt)
/* 177:    */     throws SQLException
/* 178:    */   {
/* 179:152 */     this.con.setHoldability(paramInt);
/* 180:    */   }
/* 181:    */   
/* 182:    */   public int getHoldability()
/* 183:    */     throws SQLException
/* 184:    */   {
/* 185:157 */     return this.con.getHoldability();
/* 186:    */   }
/* 187:    */   
/* 188:    */   public Savepoint setSavepoint()
/* 189:    */     throws SQLException
/* 190:    */   {
/* 191:162 */     return this.con.setSavepoint();
/* 192:    */   }
/* 193:    */   
/* 194:    */   public Savepoint setSavepoint(String paramString)
/* 195:    */     throws SQLException
/* 196:    */   {
/* 197:167 */     return this.con.setSavepoint(paramString);
/* 198:    */   }
/* 199:    */   
/* 200:    */   public void rollback(Savepoint paramSavepoint)
/* 201:    */     throws SQLException
/* 202:    */   {
/* 203:172 */     this.con.rollback(paramSavepoint);
/* 204:    */   }
/* 205:    */   
/* 206:    */   public void releaseSavepoint(Savepoint paramSavepoint)
/* 207:    */     throws SQLException
/* 208:    */   {
/* 209:177 */     this.con.releaseSavepoint(paramSavepoint);
/* 210:    */   }
/* 211:    */   
/* 212:    */   public Statement createStatement(int paramInt1, int paramInt2, int paramInt3)
/* 213:    */     throws SQLException
/* 214:    */   {
/* 215:182 */     return this.con.createStatement(paramInt1, paramInt2, paramInt3);
/* 216:    */   }
/* 217:    */   
/* 218:    */   public PreparedStatement prepareStatement(String paramString, int paramInt1, int paramInt2, int paramInt3)
/* 219:    */     throws SQLException
/* 220:    */   {
/* 221:187 */     return this.con.prepareStatement(paramString, paramInt1, paramInt2, paramInt3);
/* 222:    */   }
/* 223:    */   
/* 224:    */   public CallableStatement prepareCall(String paramString, int paramInt1, int paramInt2, int paramInt3)
/* 225:    */     throws SQLException
/* 226:    */   {
/* 227:192 */     return this.con.prepareCall(paramString, paramInt1, paramInt2, paramInt3);
/* 228:    */   }
/* 229:    */   
/* 230:    */   public PreparedStatement prepareStatement(String paramString, int paramInt)
/* 231:    */     throws SQLException
/* 232:    */   {
/* 233:197 */     return this.con.prepareStatement(paramString, paramInt);
/* 234:    */   }
/* 235:    */   
/* 236:    */   public PreparedStatement prepareStatement(String paramString, int[] paramArrayOfInt)
/* 237:    */     throws SQLException
/* 238:    */   {
/* 239:202 */     return this.con.prepareStatement(paramString, paramArrayOfInt);
/* 240:    */   }
/* 241:    */   
/* 242:    */   public PreparedStatement prepareStatement(String paramString, String[] paramArrayOfString)
/* 243:    */     throws SQLException
/* 244:    */   {
/* 245:207 */     return this.con.prepareStatement(paramString, paramArrayOfString);
/* 246:    */   }
/* 247:    */   
/* 248:    */   public Clob createClob()
/* 249:    */     throws SQLException
/* 250:    */   {
/* 251:212 */     return this.con.createClob();
/* 252:    */   }
/* 253:    */   
/* 254:    */   public Blob createBlob()
/* 255:    */     throws SQLException
/* 256:    */   {
/* 257:217 */     return this.con.createBlob();
/* 258:    */   }
/* 259:    */   
/* 260:    */   public NClob createNClob()
/* 261:    */     throws SQLException
/* 262:    */   {
/* 263:222 */     return this.con.createNClob();
/* 264:    */   }
/* 265:    */   
/* 266:    */   public SQLXML createSQLXML()
/* 267:    */     throws SQLException
/* 268:    */   {
/* 269:227 */     return this.con.createSQLXML();
/* 270:    */   }
/* 271:    */   
/* 272:    */   public boolean isValid(int paramInt)
/* 273:    */     throws SQLException
/* 274:    */   {
/* 275:232 */     return this.con.isValid(paramInt);
/* 276:    */   }
/* 277:    */   
/* 278:    */   public void setClientInfo(String paramString1, String paramString2)
/* 279:    */     throws SQLClientInfoException
/* 280:    */   {
/* 281:237 */     this.con.setClientInfo(paramString1, paramString2);
/* 282:    */   }
/* 283:    */   
/* 284:    */   public void setClientInfo(Properties paramProperties)
/* 285:    */     throws SQLClientInfoException
/* 286:    */   {
/* 287:242 */     this.con.setClientInfo(paramProperties);
/* 288:    */   }
/* 289:    */   
/* 290:    */   public String getClientInfo(String paramString)
/* 291:    */     throws SQLException
/* 292:    */   {
/* 293:247 */     return this.con.getClientInfo(paramString);
/* 294:    */   }
/* 295:    */   
/* 296:    */   public Properties getClientInfo()
/* 297:    */     throws SQLException
/* 298:    */   {
/* 299:252 */     return this.con.getClientInfo();
/* 300:    */   }
/* 301:    */   
/* 302:    */   public Array createArrayOf(String paramString, Object[] paramArrayOfObject)
/* 303:    */     throws SQLException
/* 304:    */   {
/* 305:257 */     return this.con.createArrayOf(paramString, paramArrayOfObject);
/* 306:    */   }
/* 307:    */   
/* 308:    */   public Struct createStruct(String paramString, Object[] paramArrayOfObject)
/* 309:    */     throws SQLException
/* 310:    */   {
/* 311:262 */     return this.con.createStruct(paramString, paramArrayOfObject);
/* 312:    */   }
/* 313:    */   
/* 314:    */   public void setSchema(String paramString)
/* 315:    */     throws SQLException
/* 316:    */   {
/* 317:267 */     this.con.setSchema(paramString);
/* 318:    */   }
/* 319:    */   
/* 320:    */   public String getSchema()
/* 321:    */     throws SQLException
/* 322:    */   {
/* 323:272 */     return this.con.getSchema();
/* 324:    */   }
/* 325:    */   
/* 326:    */   public void abort(Executor paramExecutor)
/* 327:    */     throws SQLException
/* 328:    */   {
/* 329:277 */     this.con.abort(paramExecutor);
/* 330:    */   }
/* 331:    */   
/* 332:    */   public void setNetworkTimeout(Executor paramExecutor, int paramInt)
/* 333:    */     throws SQLException
/* 334:    */   {
/* 335:282 */     this.con.setNetworkTimeout(paramExecutor, paramInt);
/* 336:    */   }
/* 337:    */   
/* 338:    */   public int getNetworkTimeout()
/* 339:    */     throws SQLException
/* 340:    */   {
/* 341:287 */     return this.con.getNetworkTimeout();
/* 342:    */   }
/* 343:    */   
/* 344:    */   public <T> T unwrap(Class<T> paramClass)
/* 345:    */     throws SQLException
/* 346:    */   {
/* 347:292 */     return (T)this.con.unwrap(paramClass);
/* 348:    */   }
/* 349:    */   
/* 350:    */   public boolean isWrapperFor(Class<?> paramClass)
/* 351:    */     throws SQLException
/* 352:    */   {
/* 353:297 */     return this.con.isWrapperFor(paramClass);
/* 354:    */   }
/* 355:    */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.db.FilteredConnection
 * JD-Core Version:    0.7.1
 */