/*  1:   */ package nxt.util;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import java.io.InputStream;
/*  5:   */ import java.util.logging.LogManager;
/*  6:   */ 
/*  7:   */ public class NxtLogManager
/*  8:   */   extends LogManager
/*  9:   */ {
/* 10:13 */   private volatile boolean loggingReconfiguration = false;
/* 11:   */   
/* 12:   */   public void readConfiguration(InputStream paramInputStream)
/* 13:   */     throws IOException, SecurityException
/* 14:   */   {
/* 15:36 */     this.loggingReconfiguration = true;
/* 16:37 */     super.readConfiguration(paramInputStream);
/* 17:38 */     this.loggingReconfiguration = false;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public void reset()
/* 21:   */   {
/* 22:50 */     if (this.loggingReconfiguration) {
/* 23:51 */       super.reset();
/* 24:   */     }
/* 25:   */   }
/* 26:   */   
/* 27:   */   void nxtShutdown()
/* 28:   */   {
/* 29:59 */     super.reset();
/* 30:   */   }
/* 31:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.util.NxtLogManager
 * JD-Core Version:    0.7.1
 */