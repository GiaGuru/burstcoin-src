/*  1:   */ package nxt;
/*  2:   */ 
/*  3:   */ import java.util.List;
/*  4:   */ import nxt.db.DbIterator;
/*  5:   */ import nxt.util.Observable;
/*  6:   */ import org.json.simple.JSONObject;
/*  7:   */ 
/*  8:   */ public abstract interface TransactionProcessor
/*  9:   */   extends Observable<List<? extends Transaction>, Event>
/* 10:   */ {
/* 11:   */   public abstract DbIterator<? extends Transaction> getAllUnconfirmedTransactions();
/* 12:   */   
/* 13:   */   public abstract Transaction getUnconfirmedTransaction(long paramLong);
/* 14:   */   
/* 15:   */   public abstract void clearUnconfirmedTransactions();
/* 16:   */   
/* 17:   */   public abstract void broadcast(Transaction paramTransaction)
/* 18:   */     throws NxtException.ValidationException;
/* 19:   */   
/* 20:   */   public abstract void processPeerTransactions(JSONObject paramJSONObject)
/* 21:   */     throws NxtException.ValidationException;
/* 22:   */   
/* 23:   */   public abstract Transaction parseTransaction(byte[] paramArrayOfByte)
/* 24:   */     throws NxtException.ValidationException;
/* 25:   */   
/* 26:   */   public abstract Transaction parseTransaction(JSONObject paramJSONObject)
/* 27:   */     throws NxtException.ValidationException;
/* 28:   */   
/* 29:   */   public abstract Transaction.Builder newTransactionBuilder(byte[] paramArrayOfByte, long paramLong1, long paramLong2, short paramShort, Attachment paramAttachment);
/* 30:   */   
/* 31:   */   public static enum Event
/* 32:   */   {
/* 33:12 */     REMOVED_UNCONFIRMED_TRANSACTIONS,  ADDED_UNCONFIRMED_TRANSACTIONS,  ADDED_CONFIRMED_TRANSACTIONS,  ADDED_DOUBLESPENDING_TRANSACTIONS;
/* 34:   */     
/* 35:   */     private Event() {}
/* 36:   */   }
/* 37:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.TransactionProcessor
 * JD-Core Version:    0.7.1
 */