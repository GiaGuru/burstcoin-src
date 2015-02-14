/*   1:    */ package nxt.db;
/*   2:    */ 
/*   3:    */ import java.sql.PreparedStatement;
/*   4:    */ import java.sql.ResultSet;
/*   5:    */ import java.sql.SQLException;
/*   6:    */ 
/*   7:    */ public abstract interface DbKey
/*   8:    */ {
/*   9:    */   public abstract int setPK(PreparedStatement paramPreparedStatement)
/*  10:    */     throws SQLException;
/*  11:    */   
/*  12:    */   public abstract int setPK(PreparedStatement paramPreparedStatement, int paramInt)
/*  13:    */     throws SQLException;
/*  14:    */   
/*  15:    */   public static abstract class Factory<T>
/*  16:    */   {
/*  17:    */     private final String pkClause;
/*  18:    */     private final String pkColumns;
/*  19:    */     private final String selfJoinClause;
/*  20:    */     
/*  21:    */     protected Factory(String paramString1, String paramString2, String paramString3)
/*  22:    */     {
/*  23: 16 */       this.pkClause = paramString1;
/*  24: 17 */       this.pkColumns = paramString2;
/*  25: 18 */       this.selfJoinClause = paramString3;
/*  26:    */     }
/*  27:    */     
/*  28:    */     public abstract DbKey newKey(T paramT);
/*  29:    */     
/*  30:    */     public abstract DbKey newKey(ResultSet paramResultSet)
/*  31:    */       throws SQLException;
/*  32:    */     
/*  33:    */     public final String getPKClause()
/*  34:    */     {
/*  35: 26 */       return this.pkClause;
/*  36:    */     }
/*  37:    */     
/*  38:    */     public final String getPKColumns()
/*  39:    */     {
/*  40: 30 */       return this.pkColumns;
/*  41:    */     }
/*  42:    */     
/*  43:    */     public final String getSelfJoinClause()
/*  44:    */     {
/*  45: 35 */       return this.selfJoinClause;
/*  46:    */     }
/*  47:    */   }
/*  48:    */   
/*  49:    */   public static abstract class LongKeyFactory<T>
/*  50:    */     extends DbKey.Factory<T>
/*  51:    */   {
/*  52:    */     private final String idColumn;
/*  53:    */     
/*  54:    */     public LongKeyFactory(String paramString)
/*  55:    */     {
/*  56: 50 */       super(paramString, " a." + paramString + " = b." + paramString + " ");
/*  57:    */       
/*  58:    */ 
/*  59: 53 */       this.idColumn = paramString;
/*  60:    */     }
/*  61:    */     
/*  62:    */     public DbKey newKey(ResultSet paramResultSet)
/*  63:    */       throws SQLException
/*  64:    */     {
/*  65: 58 */       return new DbKey.LongKey(paramResultSet.getLong(this.idColumn), null);
/*  66:    */     }
/*  67:    */     
/*  68:    */     public DbKey newKey(long paramLong)
/*  69:    */     {
/*  70: 62 */       return new DbKey.LongKey(paramLong, null);
/*  71:    */     }
/*  72:    */   }
/*  73:    */   
/*  74:    */   public static abstract class LinkKeyFactory<T>
/*  75:    */     extends DbKey.Factory<T>
/*  76:    */   {
/*  77:    */     private final String idColumnA;
/*  78:    */     private final String idColumnB;
/*  79:    */     
/*  80:    */     public LinkKeyFactory(String paramString1, String paramString2)
/*  81:    */     {
/*  82: 73 */       super(paramString1 + ", " + paramString2, " a." + paramString1 + " = b." + paramString1 + " AND a." + paramString2 + " = b." + paramString2 + " ");
/*  83:    */       
/*  84:    */ 
/*  85: 76 */       this.idColumnA = paramString1;
/*  86: 77 */       this.idColumnB = paramString2;
/*  87:    */     }
/*  88:    */     
/*  89:    */     public DbKey newKey(ResultSet paramResultSet)
/*  90:    */       throws SQLException
/*  91:    */     {
/*  92: 82 */       return new DbKey.LinkKey(paramResultSet.getLong(this.idColumnA), paramResultSet.getLong(this.idColumnB), null);
/*  93:    */     }
/*  94:    */     
/*  95:    */     public DbKey newKey(long paramLong1, long paramLong2)
/*  96:    */     {
/*  97: 86 */       return new DbKey.LinkKey(paramLong1, paramLong2, null);
/*  98:    */     }
/*  99:    */   }
/* 100:    */   
/* 101:    */   public static final class LongKey
/* 102:    */     implements DbKey
/* 103:    */   {
/* 104:    */     private final long id;
/* 105:    */     
/* 106:    */     private LongKey(long paramLong)
/* 107:    */     {
/* 108: 96 */       this.id = paramLong;
/* 109:    */     }
/* 110:    */     
/* 111:    */     public int setPK(PreparedStatement paramPreparedStatement)
/* 112:    */       throws SQLException
/* 113:    */     {
/* 114:101 */       return setPK(paramPreparedStatement, 1);
/* 115:    */     }
/* 116:    */     
/* 117:    */     public int setPK(PreparedStatement paramPreparedStatement, int paramInt)
/* 118:    */       throws SQLException
/* 119:    */     {
/* 120:106 */       paramPreparedStatement.setLong(paramInt, this.id);
/* 121:107 */       return paramInt + 1;
/* 122:    */     }
/* 123:    */     
/* 124:    */     public boolean equals(Object paramObject)
/* 125:    */     {
/* 126:112 */       return ((paramObject instanceof LongKey)) && (((LongKey)paramObject).id == this.id);
/* 127:    */     }
/* 128:    */     
/* 129:    */     public int hashCode()
/* 130:    */     {
/* 131:117 */       return (int)(this.id ^ this.id >>> 32);
/* 132:    */     }
/* 133:    */   }
/* 134:    */   
/* 135:    */   public static final class LinkKey
/* 136:    */     implements DbKey
/* 137:    */   {
/* 138:    */     private final long idA;
/* 139:    */     private final long idB;
/* 140:    */     
/* 141:    */     private LinkKey(long paramLong1, long paramLong2)
/* 142:    */     {
/* 143:128 */       this.idA = paramLong1;
/* 144:129 */       this.idB = paramLong2;
/* 145:    */     }
/* 146:    */     
/* 147:    */     public int setPK(PreparedStatement paramPreparedStatement)
/* 148:    */       throws SQLException
/* 149:    */     {
/* 150:134 */       return setPK(paramPreparedStatement, 1);
/* 151:    */     }
/* 152:    */     
/* 153:    */     public int setPK(PreparedStatement paramPreparedStatement, int paramInt)
/* 154:    */       throws SQLException
/* 155:    */     {
/* 156:139 */       paramPreparedStatement.setLong(paramInt, this.idA);
/* 157:140 */       paramPreparedStatement.setLong(paramInt + 1, this.idB);
/* 158:141 */       return paramInt + 2;
/* 159:    */     }
/* 160:    */     
/* 161:    */     public boolean equals(Object paramObject)
/* 162:    */     {
/* 163:146 */       return ((paramObject instanceof LinkKey)) && (((LinkKey)paramObject).idA == this.idA) && (((LinkKey)paramObject).idB == this.idB);
/* 164:    */     }
/* 165:    */     
/* 166:    */     public int hashCode()
/* 167:    */     {
/* 168:151 */       return (int)(this.idA ^ this.idA >>> 32) ^ (int)(this.idB ^ this.idB >>> 32);
/* 169:    */     }
/* 170:    */   }
/* 171:    */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.db.DbKey
 * JD-Core Version:    0.7.1
 */