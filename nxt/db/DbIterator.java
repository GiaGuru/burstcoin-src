/*  1:   */ package nxt.db;
/*  2:   */ 
/*  3:   */ import java.sql.Connection;
/*  4:   */ import java.sql.PreparedStatement;
/*  5:   */ import java.sql.ResultSet;
/*  6:   */ import java.sql.SQLException;
/*  7:   */ import java.util.Iterator;
/*  8:   */ import java.util.NoSuchElementException;
/*  9:   */ 
/* 10:   */ public final class DbIterator<T>
/* 11:   */   implements Iterator<T>, Iterable<T>, AutoCloseable
/* 12:   */ {
/* 13:   */   private final Connection con;
/* 14:   */   private final PreparedStatement pstmt;
/* 15:   */   private final ResultSetReader<T> rsReader;
/* 16:   */   private final ResultSet rs;
/* 17:   */   private boolean hasNext;
/* 18:   */   private boolean iterated;
/* 19:   */   
/* 20:   */   public DbIterator(Connection paramConnection, PreparedStatement paramPreparedStatement, ResultSetReader<T> paramResultSetReader)
/* 21:   */   {
/* 22:25 */     this.con = paramConnection;
/* 23:26 */     this.pstmt = paramPreparedStatement;
/* 24:27 */     this.rsReader = paramResultSetReader;
/* 25:   */     try
/* 26:   */     {
/* 27:29 */       this.rs = paramPreparedStatement.executeQuery();
/* 28:30 */       this.hasNext = this.rs.next();
/* 29:   */     }
/* 30:   */     catch (SQLException localSQLException)
/* 31:   */     {
/* 32:32 */       DbUtils.close(new AutoCloseable[] { paramPreparedStatement, paramConnection });
/* 33:33 */       throw new RuntimeException(localSQLException.toString(), localSQLException);
/* 34:   */     }
/* 35:   */   }
/* 36:   */   
/* 37:   */   public boolean hasNext()
/* 38:   */   {
/* 39:39 */     if (!this.hasNext) {
/* 40:40 */       DbUtils.close(new AutoCloseable[] { this.rs, this.pstmt, this.con });
/* 41:   */     }
/* 42:42 */     return this.hasNext;
/* 43:   */   }
/* 44:   */   
/* 45:   */   public T next()
/* 46:   */   {
/* 47:47 */     if (!this.hasNext)
/* 48:   */     {
/* 49:48 */       DbUtils.close(new AutoCloseable[] { this.rs, this.pstmt, this.con });
/* 50:49 */       throw new NoSuchElementException();
/* 51:   */     }
/* 52:   */     try
/* 53:   */     {
/* 54:52 */       Object localObject = this.rsReader.get(this.con, this.rs);
/* 55:53 */       this.hasNext = this.rs.next();
/* 56:54 */       return (T)localObject;
/* 57:   */     }
/* 58:   */     catch (Exception localException)
/* 59:   */     {
/* 60:56 */       DbUtils.close(new AutoCloseable[] { this.rs, this.pstmt, this.con });
/* 61:57 */       throw new RuntimeException(localException.toString(), localException);
/* 62:   */     }
/* 63:   */   }
/* 64:   */   
/* 65:   */   public void remove()
/* 66:   */   {
/* 67:63 */     throw new UnsupportedOperationException("Removal not suported");
/* 68:   */   }
/* 69:   */   
/* 70:   */   public void close()
/* 71:   */   {
/* 72:68 */     DbUtils.close(new AutoCloseable[] { this.rs, this.pstmt, this.con });
/* 73:   */   }
/* 74:   */   
/* 75:   */   public Iterator<T> iterator()
/* 76:   */   {
/* 77:73 */     if (this.iterated) {
/* 78:74 */       throw new IllegalStateException("Already iterated");
/* 79:   */     }
/* 80:76 */     this.iterated = true;
/* 81:77 */     return this;
/* 82:   */   }
/* 83:   */   
/* 84:   */   public static abstract interface ResultSetReader<T>
/* 85:   */   {
/* 86:   */     public abstract T get(Connection paramConnection, ResultSet paramResultSet)
/* 87:   */       throws Exception;
/* 88:   */   }
/* 89:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.db.DbIterator
 * JD-Core Version:    0.7.1
 */