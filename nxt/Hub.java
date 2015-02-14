/*   1:    */ package nxt;
/*   2:    */ 
/*   3:    */ import java.sql.Connection;
/*   4:    */ import java.sql.PreparedStatement;
/*   5:    */ import java.sql.ResultSet;
/*   6:    */ import java.sql.SQLException;
/*   7:    */ import java.util.Arrays;
/*   8:    */ import java.util.Collections;
/*   9:    */ import java.util.List;
/*  10:    */ import nxt.db.DbKey;
/*  11:    */ import nxt.db.DbKey.LongKeyFactory;
/*  12:    */ import nxt.db.VersionedEntityDbTable;
/*  13:    */ 
/*  14:    */ public class Hub
/*  15:    */ {
/*  16:    */   public static class Hit
/*  17:    */     implements Comparable<Hit>
/*  18:    */   {
/*  19:    */     public final Hub hub;
/*  20:    */     public final long hitTime;
/*  21:    */     
/*  22:    */     private Hit(Hub paramHub, long paramLong)
/*  23:    */     {
/*  24: 24 */       this.hub = paramHub;
/*  25: 25 */       this.hitTime = paramLong;
/*  26:    */     }
/*  27:    */     
/*  28:    */     public int compareTo(Hit paramHit)
/*  29:    */     {
/*  30: 30 */       if (this.hitTime < paramHit.hitTime) {
/*  31: 31 */         return -1;
/*  32:    */       }
/*  33: 32 */       if (this.hitTime > paramHit.hitTime) {
/*  34: 33 */         return 1;
/*  35:    */       }
/*  36: 35 */       return Long.compare(this.hub.accountId, paramHit.hub.accountId);
/*  37:    */     }
/*  38:    */   }
/*  39:    */   
/*  40: 41 */   private static final DbKey.LongKeyFactory<Hub> hubDbKeyFactory = null;
/*  41: 43 */   private static final VersionedEntityDbTable<Hub> hubTable = null;
/*  42:    */   private static long lastBlockId;
/*  43:    */   private static List<Hit> lastHits;
/*  44:    */   private final long accountId;
/*  45:    */   private final DbKey dbKey;
/*  46:    */   private final long minFeePerByteNQT;
/*  47:    */   private final List<String> uris;
/*  48:    */   
/*  49:    */   static void addOrUpdateHub(Transaction paramTransaction, Attachment.MessagingHubAnnouncement paramMessagingHubAnnouncement)
/*  50:    */   {
/*  51: 46 */     hubTable.insert(new Hub(paramTransaction, paramMessagingHubAnnouncement));
/*  52:    */   }
/*  53:    */   
/*  54:    */   public static List<Hit> getHubHits(Block paramBlock)
/*  55:    */   {
/*  56: 83 */     return null;
/*  57:    */   }
/*  58:    */   
/*  59:    */   static void init() {}
/*  60:    */   
/*  61:    */   private Hub(Transaction paramTransaction, Attachment.MessagingHubAnnouncement paramMessagingHubAnnouncement)
/*  62:    */   {
/*  63: 95 */     this.accountId = paramTransaction.getSenderId();
/*  64: 96 */     this.dbKey = hubDbKeyFactory.newKey(this.accountId);
/*  65: 97 */     this.minFeePerByteNQT = paramMessagingHubAnnouncement.getMinFeePerByteNQT();
/*  66: 98 */     this.uris = Collections.unmodifiableList(Arrays.asList(paramMessagingHubAnnouncement.getUris()));
/*  67:    */   }
/*  68:    */   
/*  69:    */   private Hub(ResultSet paramResultSet)
/*  70:    */     throws SQLException
/*  71:    */   {
/*  72:102 */     this.accountId = paramResultSet.getLong("account_id");
/*  73:103 */     this.dbKey = hubDbKeyFactory.newKey(this.accountId);
/*  74:104 */     this.minFeePerByteNQT = paramResultSet.getLong("min_fee_per_byte");
/*  75:105 */     this.uris = Collections.unmodifiableList(Arrays.asList((String[])paramResultSet.getObject("uris")));
/*  76:    */   }
/*  77:    */   
/*  78:    */   private void save(Connection paramConnection)
/*  79:    */     throws SQLException
/*  80:    */   {
/*  81:109 */     PreparedStatement localPreparedStatement = paramConnection.prepareStatement("MERGE INTO hub (account_id, min_fee_per_byte, uris, height) KEY (account_id, height) VALUES (?, ?, ?, ?)");Object localObject1 = null;
/*  82:    */     try
/*  83:    */     {
/*  84:111 */       int i = 0;
/*  85:112 */       localPreparedStatement.setLong(++i, getAccountId());
/*  86:113 */       localPreparedStatement.setLong(++i, getMinFeePerByteNQT());
/*  87:114 */       localPreparedStatement.setObject(++i, getUris().toArray(new String[getUris().size()]));
/*  88:115 */       localPreparedStatement.setInt(++i, Nxt.getBlockchain().getHeight());
/*  89:116 */       localPreparedStatement.executeUpdate();
/*  90:    */     }
/*  91:    */     catch (Throwable localThrowable2)
/*  92:    */     {
/*  93:109 */       localObject1 = localThrowable2;throw localThrowable2;
/*  94:    */     }
/*  95:    */     finally
/*  96:    */     {
/*  97:117 */       if (localPreparedStatement != null) {
/*  98:117 */         if (localObject1 != null) {
/*  99:    */           try
/* 100:    */           {
/* 101:117 */             localPreparedStatement.close();
/* 102:    */           }
/* 103:    */           catch (Throwable localThrowable3)
/* 104:    */           {
/* 105:117 */             ((Throwable)localObject1).addSuppressed(localThrowable3);
/* 106:    */           }
/* 107:    */         } else {
/* 108:117 */           localPreparedStatement.close();
/* 109:    */         }
/* 110:    */       }
/* 111:    */     }
/* 112:    */   }
/* 113:    */   
/* 114:    */   public long getAccountId()
/* 115:    */   {
/* 116:121 */     return this.accountId;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public long getMinFeePerByteNQT()
/* 120:    */   {
/* 121:125 */     return this.minFeePerByteNQT;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public List<String> getUris()
/* 125:    */   {
/* 126:129 */     return this.uris;
/* 127:    */   }
/* 128:    */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.Hub
 * JD-Core Version:    0.7.1
 */