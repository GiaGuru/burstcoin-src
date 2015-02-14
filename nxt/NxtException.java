/*  1:   */ package nxt;
/*  2:   */ 
/*  3:   */ public abstract class NxtException
/*  4:   */   extends Exception
/*  5:   */ {
/*  6:   */   protected NxtException() {}
/*  7:   */   
/*  8:   */   protected NxtException(String paramString)
/*  9:   */   {
/* 10:10 */     super(paramString);
/* 11:   */   }
/* 12:   */   
/* 13:   */   protected NxtException(String paramString, Throwable paramThrowable)
/* 14:   */   {
/* 15:14 */     super(paramString, paramThrowable);
/* 16:   */   }
/* 17:   */   
/* 18:   */   protected NxtException(Throwable paramThrowable)
/* 19:   */   {
/* 20:18 */     super(paramThrowable);
/* 21:   */   }
/* 22:   */   
/* 23:   */   public static abstract class ValidationException
/* 24:   */     extends NxtException
/* 25:   */   {
/* 26:   */     private ValidationException(String paramString)
/* 27:   */     {
/* 28:24 */       super();
/* 29:   */     }
/* 30:   */     
/* 31:   */     private ValidationException(String paramString, Throwable paramThrowable)
/* 32:   */     {
/* 33:28 */       super(paramThrowable);
/* 34:   */     }
/* 35:   */   }
/* 36:   */   
/* 37:   */   public static class NotCurrentlyValidException
/* 38:   */     extends NxtException.ValidationException
/* 39:   */   {
/* 40:   */     public NotCurrentlyValidException(String paramString)
/* 41:   */     {
/* 42:36 */       super(null);
/* 43:   */     }
/* 44:   */     
/* 45:   */     public NotCurrentlyValidException(String paramString, Throwable paramThrowable)
/* 46:   */     {
/* 47:40 */       super(paramThrowable, null);
/* 48:   */     }
/* 49:   */   }
/* 50:   */   
/* 51:   */   public static final class NotYetEnabledException
/* 52:   */     extends NxtException.NotCurrentlyValidException
/* 53:   */   {
/* 54:   */     public NotYetEnabledException(String paramString)
/* 55:   */     {
/* 56:48 */       super();
/* 57:   */     }
/* 58:   */     
/* 59:   */     public NotYetEnabledException(String paramString, Throwable paramThrowable)
/* 60:   */     {
/* 61:52 */       super(paramThrowable);
/* 62:   */     }
/* 63:   */   }
/* 64:   */   
/* 65:   */   public static final class NotValidException
/* 66:   */     extends NxtException.ValidationException
/* 67:   */   {
/* 68:   */     public NotValidException(String paramString)
/* 69:   */     {
/* 70:60 */       super(null);
/* 71:   */     }
/* 72:   */     
/* 73:   */     public NotValidException(String paramString, Throwable paramThrowable)
/* 74:   */     {
/* 75:64 */       super(paramThrowable, null);
/* 76:   */     }
/* 77:   */   }
/* 78:   */   
/* 79:   */   public static final class StopException
/* 80:   */     extends RuntimeException
/* 81:   */   {
/* 82:   */     public StopException(String paramString)
/* 83:   */     {
/* 84:72 */       super();
/* 85:   */     }
/* 86:   */     
/* 87:   */     public StopException(String paramString, Throwable paramThrowable)
/* 88:   */     {
/* 89:76 */       super(paramThrowable);
/* 90:   */     }
/* 91:   */   }
/* 92:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.NxtException
 * JD-Core Version:    0.7.1
 */