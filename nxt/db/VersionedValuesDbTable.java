/*  1:   */ package nxt.db;
/*  2:   */ 
/*  3:   */ public abstract class VersionedValuesDbTable<T, V>
/*  4:   */   extends ValuesDbTable<T, V>
/*  5:   */ {
/*  6:   */   protected VersionedValuesDbTable(String paramString, DbKey.Factory<T> paramFactory)
/*  7:   */   {
/*  8: 6 */     super(paramString, paramFactory, true);
/*  9:   */   }
/* 10:   */   
/* 11:   */   public final void rollback(int paramInt)
/* 12:   */   {
/* 13:11 */     VersionedEntityDbTable.rollback(this.table, paramInt, this.dbKeyFactory);
/* 14:   */   }
/* 15:   */   
/* 16:   */   public final void trim(int paramInt)
/* 17:   */   {
/* 18:16 */     VersionedEntityDbTable.trim(this.table, paramInt, this.dbKeyFactory);
/* 19:   */   }
/* 20:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.db.VersionedValuesDbTable
 * JD-Core Version:    0.7.1
 */