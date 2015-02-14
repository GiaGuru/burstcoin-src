/*  1:   */ package nxt.db;
/*  2:   */ 
/*  3:   */ import java.sql.PreparedStatement;
/*  4:   */ import java.sql.SQLException;
/*  5:   */ 
/*  6:   */ public final class DbUtils
/*  7:   */ {
/*  8:   */   public static void close(AutoCloseable... paramVarArgs)
/*  9:   */   {
/* 10:10 */     for (AutoCloseable localAutoCloseable : paramVarArgs) {
/* 11:11 */       if (localAutoCloseable != null) {
/* 12:   */         try
/* 13:   */         {
/* 14:13 */           localAutoCloseable.close();
/* 15:   */         }
/* 16:   */         catch (Exception localException) {}
/* 17:   */       }
/* 18:   */     }
/* 19:   */   }
/* 20:   */   
/* 21:   */   public static void setBytes(PreparedStatement paramPreparedStatement, int paramInt, byte[] paramArrayOfByte)
/* 22:   */     throws SQLException
/* 23:   */   {
/* 24:20 */     if (paramArrayOfByte != null) {
/* 25:21 */       paramPreparedStatement.setBytes(paramInt, paramArrayOfByte);
/* 26:   */     } else {
/* 27:23 */       paramPreparedStatement.setNull(paramInt, -2);
/* 28:   */     }
/* 29:   */   }
/* 30:   */   
/* 31:   */   public static void setString(PreparedStatement paramPreparedStatement, int paramInt, String paramString)
/* 32:   */     throws SQLException
/* 33:   */   {
/* 34:28 */     if (paramString != null) {
/* 35:29 */       paramPreparedStatement.setString(paramInt, paramString);
/* 36:   */     } else {
/* 37:31 */       paramPreparedStatement.setNull(paramInt, 12);
/* 38:   */     }
/* 39:   */   }
/* 40:   */   
/* 41:   */   public static void setIntZeroToNull(PreparedStatement paramPreparedStatement, int paramInt1, int paramInt2)
/* 42:   */     throws SQLException
/* 43:   */   {
/* 44:36 */     if (paramInt2 != 0) {
/* 45:37 */       paramPreparedStatement.setInt(paramInt1, paramInt2);
/* 46:   */     } else {
/* 47:39 */       paramPreparedStatement.setNull(paramInt1, 4);
/* 48:   */     }
/* 49:   */   }
/* 50:   */   
/* 51:   */   public static void setLongZeroToNull(PreparedStatement paramPreparedStatement, int paramInt, long paramLong)
/* 52:   */     throws SQLException
/* 53:   */   {
/* 54:44 */     if (paramLong != 0L) {
/* 55:45 */       paramPreparedStatement.setLong(paramInt, paramLong);
/* 56:   */     } else {
/* 57:47 */       paramPreparedStatement.setNull(paramInt, -5);
/* 58:   */     }
/* 59:   */   }
/* 60:   */   
/* 61:   */   public static String limitsClause(int paramInt1, int paramInt2)
/* 62:   */   {
/* 63:52 */     int i = (paramInt2 >= 0) && (paramInt2 >= paramInt1) && (paramInt2 < Integer.MAX_VALUE) ? paramInt2 - paramInt1 + 1 : 0;
/* 64:53 */     if ((i > 0) && (paramInt1 > 0)) {
/* 65:54 */       return " LIMIT ? OFFSET ? ";
/* 66:   */     }
/* 67:55 */     if (i > 0) {
/* 68:56 */       return " LIMIT ? ";
/* 69:   */     }
/* 70:57 */     if (paramInt1 > 0) {
/* 71:58 */       return " OFFSET ? ";
/* 72:   */     }
/* 73:60 */     return "";
/* 74:   */   }
/* 75:   */   
/* 76:   */   public static int setLimits(int paramInt1, PreparedStatement paramPreparedStatement, int paramInt2, int paramInt3)
/* 77:   */     throws SQLException
/* 78:   */   {
/* 79:65 */     int i = (paramInt3 >= 0) && (paramInt3 >= paramInt2) && (paramInt3 < Integer.MAX_VALUE) ? paramInt3 - paramInt2 + 1 : 0;
/* 80:66 */     if (i > 0) {
/* 81:67 */       paramPreparedStatement.setInt(paramInt1++, i);
/* 82:   */     }
/* 83:69 */     if (paramInt2 > 0) {
/* 84:70 */       paramPreparedStatement.setInt(paramInt1++, paramInt2);
/* 85:   */     }
/* 86:72 */     return paramInt1;
/* 87:   */   }
/* 88:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.db.DbUtils
 * JD-Core Version:    0.7.1
 */