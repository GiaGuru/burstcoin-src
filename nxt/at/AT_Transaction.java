/*  1:   */ package nxt.at;
/*  2:   */ 
/*  3:   */ import java.util.SortedMap;
/*  4:   */ import java.util.TreeMap;
/*  5:   */ 
/*  6:   */ public class AT_Transaction
/*  7:   */ {
/*  8:17 */   private static SortedMap<Long, SortedMap<Long, AT_Transaction>> all_AT_Txs = new TreeMap();
/*  9:19 */   private byte[] senderId = new byte[8];
/* 10:20 */   private byte[] recipientId = new byte[8];
/* 11:   */   private long amount;
/* 12:   */   private byte[] message;
/* 13:   */   
/* 14:   */   AT_Transaction(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, long paramLong, byte[] paramArrayOfByte3)
/* 15:   */   {
/* 16:25 */     this.senderId = ((byte[])paramArrayOfByte1.clone());
/* 17:26 */     this.recipientId = ((byte[])paramArrayOfByte2.clone());
/* 18:27 */     this.amount = paramLong;
/* 19:28 */     this.message = (paramArrayOfByte3 != null ? (byte[])paramArrayOfByte3.clone() : null);
/* 20:   */   }
/* 21:   */   
/* 22:   */   public Long getAmount()
/* 23:   */   {
/* 24:32 */     return Long.valueOf(this.amount);
/* 25:   */   }
/* 26:   */   
/* 27:   */   public byte[] getSenderId()
/* 28:   */   {
/* 29:36 */     return this.senderId;
/* 30:   */   }
/* 31:   */   
/* 32:   */   public byte[] getRecipientId()
/* 33:   */   {
/* 34:40 */     return this.recipientId;
/* 35:   */   }
/* 36:   */   
/* 37:   */   public byte[] getMessage()
/* 38:   */   {
/* 39:44 */     return this.message;
/* 40:   */   }
/* 41:   */   
/* 42:   */   public void addTransaction(long paramLong, Long paramLong1)
/* 43:   */   {
/* 44:50 */     if (all_AT_Txs.containsKey(Long.valueOf(paramLong)))
/* 45:   */     {
/* 46:51 */       ((SortedMap)all_AT_Txs.get(Long.valueOf(paramLong))).put(paramLong1, this);
/* 47:   */     }
/* 48:   */     else
/* 49:   */     {
/* 50:55 */       TreeMap localTreeMap = new TreeMap();
/* 51:56 */       localTreeMap.put(paramLong1, this);
/* 52:57 */       all_AT_Txs.put(Long.valueOf(paramLong), localTreeMap);
/* 53:   */     }
/* 54:   */   }
/* 55:   */   
/* 56:   */   public static AT_Transaction getATTransaction(Long paramLong1, Long paramLong2)
/* 57:   */   {
/* 58:63 */     if (all_AT_Txs.containsKey(paramLong1)) {
/* 59:64 */       return (AT_Transaction)((SortedMap)all_AT_Txs.get(paramLong1)).get(paramLong2);
/* 60:   */     }
/* 61:66 */     return null;
/* 62:   */   }
/* 63:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.at.AT_Transaction
 * JD-Core Version:    0.7.1
 */