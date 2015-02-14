/*  1:   */ package nxt.util;
/*  2:   */ 
/*  3:   */ import java.util.concurrent.atomic.AtomicInteger;
/*  4:   */ import nxt.Constants;
/*  5:   */ 
/*  6:   */ public abstract interface Time
/*  7:   */ {
/*  8:   */   public abstract int getTime();
/*  9:   */   
/* 10:   */   public static final class EpochTime
/* 11:   */     implements Time
/* 12:   */   {
/* 13:   */     public int getTime()
/* 14:   */     {
/* 15:14 */       return (int)((System.currentTimeMillis() - Constants.EPOCH_BEGINNING + 500L) / 1000L);
/* 16:   */     }
/* 17:   */   }
/* 18:   */   
/* 19:   */   public static final class ConstantTime
/* 20:   */     implements Time
/* 21:   */   {
/* 22:   */     private final int time;
/* 23:   */     
/* 24:   */     public ConstantTime(int paramInt)
/* 25:   */     {
/* 26:24 */       this.time = paramInt;
/* 27:   */     }
/* 28:   */     
/* 29:   */     public int getTime()
/* 30:   */     {
/* 31:28 */       return this.time;
/* 32:   */     }
/* 33:   */   }
/* 34:   */   
/* 35:   */   public static final class FasterTime
/* 36:   */     implements Time
/* 37:   */   {
/* 38:   */     private final int multiplier;
/* 39:   */     private final long systemStartTime;
/* 40:   */     private final int time;
/* 41:   */     
/* 42:   */     public FasterTime(int paramInt1, int paramInt2)
/* 43:   */     {
/* 44:40 */       if ((paramInt2 > 1000) || (paramInt2 <= 0)) {
/* 45:41 */         throw new IllegalArgumentException("Time multiplier must be between 1 and 1000");
/* 46:   */       }
/* 47:43 */       this.multiplier = paramInt2;
/* 48:44 */       this.time = paramInt1;
/* 49:45 */       this.systemStartTime = System.currentTimeMillis();
/* 50:   */     }
/* 51:   */     
/* 52:   */     public int getTime()
/* 53:   */     {
/* 54:49 */       return this.time + (int)((System.currentTimeMillis() - this.systemStartTime) / (1000 / this.multiplier));
/* 55:   */     }
/* 56:   */   }
/* 57:   */   
/* 58:   */   public static final class CounterTime
/* 59:   */     implements Time
/* 60:   */   {
/* 61:   */     private final AtomicInteger counter;
/* 62:   */     
/* 63:   */     public CounterTime(int paramInt)
/* 64:   */     {
/* 65:59 */       this.counter = new AtomicInteger(paramInt);
/* 66:   */     }
/* 67:   */     
/* 68:   */     public int getTime()
/* 69:   */     {
/* 70:63 */       return this.counter.incrementAndGet();
/* 71:   */     }
/* 72:   */   }
/* 73:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.util.Time
 * JD-Core Version:    0.7.1
 */