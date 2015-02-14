/*  1:   */ package nxt.util;
/*  2:   */ 
/*  3:   */ import java.util.List;
/*  4:   */ import java.util.concurrent.ConcurrentHashMap;
/*  5:   */ import java.util.concurrent.CopyOnWriteArrayList;
/*  6:   */ 
/*  7:   */ public final class Listeners<T, E extends Enum<E>>
/*  8:   */ {
/*  9: 9 */   private final ConcurrentHashMap<Enum<E>, List<Listener<T>>> listenersMap = new ConcurrentHashMap();
/* 10:   */   
/* 11:   */   public boolean addListener(Listener<T> paramListener, Enum<E> paramEnum)
/* 12:   */   {
/* 13:12 */     synchronized (paramEnum)
/* 14:   */     {
/* 15:13 */       Object localObject1 = (List)this.listenersMap.get(paramEnum);
/* 16:14 */       if (localObject1 == null)
/* 17:   */       {
/* 18:15 */         localObject1 = new CopyOnWriteArrayList();
/* 19:16 */         this.listenersMap.put(paramEnum, localObject1);
/* 20:   */       }
/* 21:18 */       return ((List)localObject1).add(paramListener);
/* 22:   */     }
/* 23:   */   }
/* 24:   */   
/* 25:   */   public boolean removeListener(Listener<T> paramListener, Enum<E> paramEnum)
/* 26:   */   {
/* 27:23 */     synchronized (paramEnum)
/* 28:   */     {
/* 29:24 */       List localList = (List)this.listenersMap.get(paramEnum);
/* 30:25 */       if (localList != null) {
/* 31:26 */         return localList.remove(paramListener);
/* 32:   */       }
/* 33:   */     }
/* 34:29 */     return false;
/* 35:   */   }
/* 36:   */   
/* 37:   */   public void notify(T paramT, Enum<E> paramEnum)
/* 38:   */   {
/* 39:33 */     List localList = (List)this.listenersMap.get(paramEnum);
/* 40:34 */     if (localList != null) {
/* 41:35 */       for (Listener localListener : localList) {
/* 42:36 */         localListener.notify(paramT);
/* 43:   */       }
/* 44:   */     }
/* 45:   */   }
/* 46:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.util.Listeners
 * JD-Core Version:    0.7.1
 */