/*  1:   */ package nxt.util;
/*  2:   */ 
/*  3:   */ import java.io.PrintWriter;
/*  4:   */ import java.io.StringWriter;
/*  5:   */ import java.text.MessageFormat;
/*  6:   */ import java.util.Date;
/*  7:   */ import java.util.logging.Formatter;
/*  8:   */ import java.util.logging.Handler;
/*  9:   */ import java.util.logging.Level;
/* 10:   */ import java.util.logging.LogRecord;
/* 11:   */ import java.util.logging.Logger;
/* 12:   */ 
/* 13:   */ public class BriefLogFormatter
/* 14:   */   extends Formatter
/* 15:   */ {
/* 16:19 */   private static final ThreadLocal<MessageFormat> messageFormat = new ThreadLocal()
/* 17:   */   {
/* 18:   */     protected MessageFormat initialValue()
/* 19:   */     {
/* 20:22 */       return new MessageFormat("{0,date,yyyy-MM-dd HH:mm:ss} {1}: {2}\n{3}");
/* 21:   */     }
/* 22:   */   };
/* 23:27 */   private static final Logger logger = Logger.getLogger("");
/* 24:30 */   private static final BriefLogFormatter briefLogFormatter = new BriefLogFormatter();
/* 25:   */   
/* 26:   */   static void init()
/* 27:   */   {
/* 28:36 */     Handler[] arrayOfHandler1 = logger.getHandlers();
/* 29:37 */     for (Handler localHandler : arrayOfHandler1) {
/* 30:38 */       localHandler.setFormatter(briefLogFormatter);
/* 31:   */     }
/* 32:   */   }
/* 33:   */   
/* 34:   */   public String format(LogRecord paramLogRecord)
/* 35:   */   {
/* 36:51 */     Object[] arrayOfObject = new Object[4];
/* 37:52 */     arrayOfObject[0] = new Date(paramLogRecord.getMillis());
/* 38:53 */     arrayOfObject[1] = paramLogRecord.getLevel().getName();
/* 39:54 */     arrayOfObject[2] = paramLogRecord.getMessage();
/* 40:55 */     Throwable localThrowable = paramLogRecord.getThrown();
/* 41:56 */     if (localThrowable != null)
/* 42:   */     {
/* 43:57 */       StringWriter localStringWriter = new StringWriter();
/* 44:58 */       localThrowable.printStackTrace(new PrintWriter(localStringWriter));
/* 45:59 */       arrayOfObject[3] = localStringWriter.toString();
/* 46:   */     }
/* 47:   */     else
/* 48:   */     {
/* 49:61 */       arrayOfObject[3] = "";
/* 50:   */     }
/* 51:63 */     return ((MessageFormat)messageFormat.get()).format(arrayOfObject);
/* 52:   */   }
/* 53:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.util.BriefLogFormatter
 * JD-Core Version:    0.7.1
 */