/*  1:   */ package nxt;
/*  2:   */ 
/*  3:   */ import java.util.List;
/*  4:   */ import nxt.db.DerivedDbTable;
/*  5:   */ import nxt.peer.Peer;
/*  6:   */ import nxt.util.Observable;
/*  7:   */ import org.json.simple.JSONObject;
/*  8:   */ 
/*  9:   */ public abstract interface BlockchainProcessor
/* 10:   */   extends Observable<Block, Event>
/* 11:   */ {
/* 12:   */   public abstract Peer getLastBlockchainFeeder();
/* 13:   */   
/* 14:   */   public abstract int getLastBlockchainFeederHeight();
/* 15:   */   
/* 16:   */   public abstract boolean isScanning();
/* 17:   */   
/* 18:   */   public abstract int getMinRollbackHeight();
/* 19:   */   
/* 20:   */   public abstract void processPeerBlock(JSONObject paramJSONObject)
/* 21:   */     throws NxtException;
/* 22:   */   
/* 23:   */   public abstract void fullReset();
/* 24:   */   
/* 25:   */   public abstract void scan(int paramInt);
/* 26:   */   
/* 27:   */   public abstract void forceScanAtStart();
/* 28:   */   
/* 29:   */   public abstract void validateAtNextScan();
/* 30:   */   
/* 31:   */   public abstract List<? extends Block> popOffTo(int paramInt);
/* 32:   */   
/* 33:   */   public abstract void registerDerivedTable(DerivedDbTable paramDerivedDbTable);
/* 34:   */   
/* 35:   */   public static enum Event
/* 36:   */   {
/* 37:13 */     BLOCK_PUSHED,  BLOCK_POPPED,  BLOCK_GENERATED,  BLOCK_SCANNED,  RESCAN_BEGIN,  RESCAN_END,  BEFORE_BLOCK_ACCEPT,  BEFORE_BLOCK_APPLY,  AFTER_BLOCK_APPLY;
/* 38:   */     
/* 39:   */     private Event() {}
/* 40:   */   }
/* 41:   */   
/* 42:   */   public static class BlockNotAcceptedException
/* 43:   */     extends NxtException
/* 44:   */   {
/* 45:   */     BlockNotAcceptedException(String paramString)
/* 46:   */     {
/* 47:45 */       super();
/* 48:   */     }
/* 49:   */   }
/* 50:   */   
/* 51:   */   public static class TransactionNotAcceptedException
/* 52:   */     extends BlockchainProcessor.BlockNotAcceptedException
/* 53:   */   {
/* 54:   */     private final TransactionImpl transaction;
/* 55:   */     
/* 56:   */     TransactionNotAcceptedException(String paramString, TransactionImpl paramTransactionImpl)
/* 57:   */     {
/* 58:55 */       super();
/* 59:56 */       this.transaction = paramTransactionImpl;
/* 60:   */     }
/* 61:   */     
/* 62:   */     public Transaction getTransaction()
/* 63:   */     {
/* 64:60 */       return this.transaction;
/* 65:   */     }
/* 66:   */   }
/* 67:   */   
/* 68:   */   public static class BlockOutOfOrderException
/* 69:   */     extends BlockchainProcessor.BlockNotAcceptedException
/* 70:   */   {
/* 71:   */     BlockOutOfOrderException(String paramString)
/* 72:   */     {
/* 73:68 */       super();
/* 74:   */     }
/* 75:   */   }
/* 76:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.BlockchainProcessor
 * JD-Core Version:    0.7.1
 */