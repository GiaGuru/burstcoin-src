/*  1:   */ package nxt.db;
/*  2:   */ 
/*  3:   */ import java.util.Iterator;
/*  4:   */ import java.util.NoSuchElementException;
/*  5:   */ 
/*  6:   */ public final class FilteringIterator<T>
/*  7:   */   implements Iterator<T>, Iterable<T>, AutoCloseable
/*  8:   */ {
/*  9:   */   private final DbIterator<T> dbIterator;
/* 10:   */   private final Filter<T> filter;
/* 11:   */   private final int from;
/* 12:   */   private final int to;
/* 13:   */   private T next;
/* 14:   */   private boolean hasNext;
/* 15:   */   private boolean iterated;
/* 16:   */   private int count;
/* 17:   */   
/* 18:   */   public FilteringIterator(DbIterator<T> paramDbIterator, Filter<T> paramFilter)
/* 19:   */   {
/* 20:22 */     this(paramDbIterator, paramFilter, 0, Integer.MAX_VALUE);
/* 21:   */   }
/* 22:   */   
/* 23:   */   public FilteringIterator(DbIterator<T> paramDbIterator, int paramInt1, int paramInt2)
/* 24:   */   {
/* 25:26 */     this(paramDbIterator, new Filter()
/* 26:   */     {
/* 27:   */       public boolean ok(T paramAnonymousT)
/* 28:   */       {
/* 29:29 */         return true;
/* 30:   */       }
/* 31:29 */     }, paramInt1, paramInt2);
/* 32:   */   }
/* 33:   */   
/* 34:   */   public FilteringIterator(DbIterator<T> paramDbIterator, Filter<T> paramFilter, int paramInt1, int paramInt2)
/* 35:   */   {
/* 36:35 */     this.dbIterator = paramDbIterator;
/* 37:36 */     this.filter = paramFilter;
/* 38:37 */     this.from = paramInt1;
/* 39:38 */     this.to = paramInt2;
/* 40:   */   }
/* 41:   */   
/* 42:   */   public boolean hasNext()
/* 43:   */   {
/* 44:43 */     if (this.hasNext) {
/* 45:44 */       return true;
/* 46:   */     }
/* 47:46 */     while ((this.dbIterator.hasNext()) && (this.count <= this.to))
/* 48:   */     {
/* 49:47 */       this.next = this.dbIterator.next();
/* 50:48 */       if (this.filter.ok(this.next))
/* 51:   */       {
/* 52:49 */         if (this.count >= this.from)
/* 53:   */         {
/* 54:50 */           this.count += 1;
/* 55:51 */           this.hasNext = true;
/* 56:52 */           return true;
/* 57:   */         }
/* 58:54 */         this.count += 1;
/* 59:   */       }
/* 60:   */     }
/* 61:57 */     this.hasNext = false;
/* 62:58 */     return false;
/* 63:   */   }
/* 64:   */   
/* 65:   */   public T next()
/* 66:   */   {
/* 67:63 */     if (this.hasNext)
/* 68:   */     {
/* 69:64 */       this.hasNext = false;
/* 70:65 */       return (T)this.next;
/* 71:   */     }
/* 72:67 */     while ((this.dbIterator.hasNext()) && (this.count <= this.to))
/* 73:   */     {
/* 74:68 */       this.next = this.dbIterator.next();
/* 75:69 */       if (this.filter.ok(this.next))
/* 76:   */       {
/* 77:70 */         if (this.count >= this.from)
/* 78:   */         {
/* 79:71 */           this.count += 1;
/* 80:72 */           this.hasNext = false;
/* 81:73 */           return (T)this.next;
/* 82:   */         }
/* 83:75 */         this.count += 1;
/* 84:   */       }
/* 85:   */     }
/* 86:78 */     throw new NoSuchElementException();
/* 87:   */   }
/* 88:   */   
/* 89:   */   public void close()
/* 90:   */   {
/* 91:83 */     this.dbIterator.close();
/* 92:   */   }
/* 93:   */   
/* 94:   */   public void remove()
/* 95:   */   {
/* 96:88 */     throw new UnsupportedOperationException();
/* 97:   */   }
/* 98:   */   
/* 99:   */   public Iterator<T> iterator()
/* :0:   */   {
/* :1:93 */     if (this.iterated) {
/* :2:94 */       throw new IllegalStateException("Already iterated");
/* :3:   */     }
/* :4:96 */     this.iterated = true;
/* :5:97 */     return this;
/* :6:   */   }
/* :7:   */   
/* :8:   */   public static abstract interface Filter<T>
/* :9:   */   {
/* ;0:   */     public abstract boolean ok(T paramT);
/* ;1:   */   }
/* ;2:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.db.FilteringIterator
 * JD-Core Version:    0.7.1
 */