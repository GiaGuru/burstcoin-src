/*  1:   */ package nxt.db;
/*  2:   */ 
/*  3:   */ import java.sql.Connection;
/*  4:   */ import java.sql.PreparedStatement;
/*  5:   */ import java.sql.SQLException;
/*  6:   */ import java.sql.Statement;
/*  7:   */ import nxt.BlockchainProcessor;
/*  8:   */ import nxt.Nxt;
/*  9:   */ 
/* 10:   */ public abstract class DerivedDbTable
/* 11:   */ {
/* 12:   */   protected final String table;
/* 13:   */   
/* 14:   */   protected DerivedDbTable(String paramString)
/* 15:   */   {
/* 16:15 */     this.table = paramString;
/* 17:16 */     Nxt.getBlockchainProcessor().registerDerivedTable(this);
/* 18:   */   }
/* 19:   */   
/* 20:   */   public void rollback(int paramInt)
/* 21:   */   {
/* 22:20 */     if (!Db.isInTransaction()) {
/* 23:21 */       throw new IllegalStateException("Not in transaction");
/* 24:   */     }
/* 25:   */     try
/* 26:   */     {
/* 27:23 */       Connection localConnection = Db.getConnection();Object localObject1 = null;
/* 28:   */       try
/* 29:   */       {
/* 30:24 */         PreparedStatement localPreparedStatement = localConnection.prepareStatement("DELETE FROM " + this.table + " WHERE height > ?");Object localObject2 = null;
/* 31:   */         try
/* 32:   */         {
/* 33:25 */           localPreparedStatement.setInt(1, paramInt);
/* 34:26 */           localPreparedStatement.executeUpdate();
/* 35:   */         }
/* 36:   */         catch (Throwable localThrowable4)
/* 37:   */         {
/* 38:23 */           localObject2 = localThrowable4;throw localThrowable4;
/* 39:   */         }
/* 40:   */         finally {}
/* 41:   */       }
/* 42:   */       catch (Throwable localThrowable2)
/* 43:   */       {
/* 44:23 */         localObject1 = localThrowable2;throw localThrowable2;
/* 45:   */       }
/* 46:   */       finally
/* 47:   */       {
/* 48:27 */         if (localConnection != null) {
/* 49:27 */           if (localObject1 != null) {
/* 50:   */             try
/* 51:   */             {
/* 52:27 */               localConnection.close();
/* 53:   */             }
/* 54:   */             catch (Throwable localThrowable6)
/* 55:   */             {
/* 56:27 */               ((Throwable)localObject1).addSuppressed(localThrowable6);
/* 57:   */             }
/* 58:   */           } else {
/* 59:27 */             localConnection.close();
/* 60:   */           }
/* 61:   */         }
/* 62:   */       }
/* 63:   */     }
/* 64:   */     catch (SQLException localSQLException)
/* 65:   */     {
/* 66:28 */       throw new RuntimeException(localSQLException.toString(), localSQLException);
/* 67:   */     }
/* 68:   */   }
/* 69:   */   
/* 70:   */   public void truncate()
/* 71:   */   {
/* 72:33 */     if (!Db.isInTransaction()) {
/* 73:34 */       throw new IllegalStateException("Not in transaction");
/* 74:   */     }
/* 75:   */     try
/* 76:   */     {
/* 77:36 */       Connection localConnection = Db.getConnection();Object localObject1 = null;
/* 78:   */       try
/* 79:   */       {
/* 80:37 */         Statement localStatement = localConnection.createStatement();Object localObject2 = null;
/* 81:   */         try
/* 82:   */         {
/* 83:38 */           localStatement.executeUpdate("TRUNCATE TABLE " + this.table);
/* 84:   */         }
/* 85:   */         catch (Throwable localThrowable4)
/* 86:   */         {
/* 87:36 */           localObject2 = localThrowable4;throw localThrowable4;
/* 88:   */         }
/* 89:   */         finally {}
/* 90:   */       }
/* 91:   */       catch (Throwable localThrowable2)
/* 92:   */       {
/* 93:36 */         localObject1 = localThrowable2;throw localThrowable2;
/* 94:   */       }
/* 95:   */       finally
/* 96:   */       {
/* 97:39 */         if (localConnection != null) {
/* 98:39 */           if (localObject1 != null) {
/* 99:   */             try
/* :0:   */             {
/* :1:39 */               localConnection.close();
/* :2:   */             }
/* :3:   */             catch (Throwable localThrowable6)
/* :4:   */             {
/* :5:39 */               ((Throwable)localObject1).addSuppressed(localThrowable6);
/* :6:   */             }
/* :7:   */           } else {
/* :8:39 */             localConnection.close();
/* :9:   */           }
/* ;0:   */         }
/* ;1:   */       }
/* ;2:   */     }
/* ;3:   */     catch (SQLException localSQLException)
/* ;4:   */     {
/* ;5:40 */       throw new RuntimeException(localSQLException.toString(), localSQLException);
/* ;6:   */     }
/* ;7:   */   }
/* ;8:   */   
/* ;9:   */   public void trim(int paramInt) {}
/* <0:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.db.DerivedDbTable
 * JD-Core Version:    0.7.1
 */