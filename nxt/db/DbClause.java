/*  1:   */ package nxt.db;
/*  2:   */ 
/*  3:   */ import java.sql.PreparedStatement;
/*  4:   */ import java.sql.SQLException;
/*  5:   */ 
/*  6:   */ public abstract class DbClause
/*  7:   */ {
/*  8:   */   private final String clause;
/*  9:   */   
/* 10:   */   protected DbClause(String paramString)
/* 11:   */   {
/* 12:11 */     this.clause = paramString;
/* 13:   */   }
/* 14:   */   
/* 15:   */   final String getClause()
/* 16:   */   {
/* 17:15 */     return this.clause;
/* 18:   */   }
/* 19:   */   
/* 20:   */   protected abstract int set(PreparedStatement paramPreparedStatement, int paramInt)
/* 21:   */     throws SQLException;
/* 22:   */   
/* 23:   */   public static final class StringClause
/* 24:   */     extends DbClause
/* 25:   */   {
/* 26:   */     private final String value;
/* 27:   */     
/* 28:   */     public StringClause(String paramString1, String paramString2)
/* 29:   */     {
/* 30:26 */       super();
/* 31:27 */       this.value = paramString2;
/* 32:   */     }
/* 33:   */     
/* 34:   */     protected int set(PreparedStatement paramPreparedStatement, int paramInt)
/* 35:   */       throws SQLException
/* 36:   */     {
/* 37:31 */       paramPreparedStatement.setString(paramInt, this.value);
/* 38:32 */       return paramInt + 1;
/* 39:   */     }
/* 40:   */   }
/* 41:   */   
/* 42:   */   public static final class LongClause
/* 43:   */     extends DbClause
/* 44:   */   {
/* 45:   */     private final long value;
/* 46:   */     
/* 47:   */     public LongClause(String paramString, long paramLong)
/* 48:   */     {
/* 49:42 */       super();
/* 50:43 */       this.value = paramLong;
/* 51:   */     }
/* 52:   */     
/* 53:   */     protected int set(PreparedStatement paramPreparedStatement, int paramInt)
/* 54:   */       throws SQLException
/* 55:   */     {
/* 56:47 */       paramPreparedStatement.setLong(paramInt, this.value);
/* 57:48 */       return paramInt + 1;
/* 58:   */     }
/* 59:   */   }
/* 60:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.db.DbClause
 * JD-Core Version:    0.7.1
 */