/*   1:    */ package nxt;
/*   2:    */ 
/*   3:    */ import java.sql.Array;
/*   4:    */ import java.sql.Connection;
/*   5:    */ import java.sql.PreparedStatement;
/*   6:    */ import java.sql.ResultSet;
/*   7:    */ import java.sql.SQLException;
/*   8:    */ import java.util.Map;
/*   9:    */ import nxt.db.DbIterator;
/*  10:    */ import nxt.db.DbKey;
/*  11:    */ import nxt.db.DbKey.LongKeyFactory;
/*  12:    */ import nxt.db.EntityDbTable;
/*  13:    */ 
/*  14:    */ public final class Poll
/*  15:    */ {
/*  16: 15 */   private static final DbKey.LongKeyFactory<Poll> pollDbKeyFactory = null;
/*  17: 17 */   private static final EntityDbTable<Poll> pollTable = null;
/*  18:    */   private final long id;
/*  19:    */   private final DbKey dbKey;
/*  20:    */   private final String name;
/*  21:    */   private final String description;
/*  22:    */   private final String[] options;
/*  23:    */   private final byte minNumberOfOptions;
/*  24:    */   private final byte maxNumberOfOptions;
/*  25:    */   private final boolean optionsAreBinary;
/*  26:    */   
/*  27:    */   static void init() {}
/*  28:    */   
/*  29:    */   private Poll(long paramLong, Attachment.MessagingPollCreation paramMessagingPollCreation)
/*  30:    */   {
/*  31: 31 */     this.id = paramLong;
/*  32: 32 */     this.dbKey = pollDbKeyFactory.newKey(this.id);
/*  33: 33 */     this.name = paramMessagingPollCreation.getPollName();
/*  34: 34 */     this.description = paramMessagingPollCreation.getPollDescription();
/*  35: 35 */     this.options = paramMessagingPollCreation.getPollOptions();
/*  36: 36 */     this.minNumberOfOptions = paramMessagingPollCreation.getMinNumberOfOptions();
/*  37: 37 */     this.maxNumberOfOptions = paramMessagingPollCreation.getMaxNumberOfOptions();
/*  38: 38 */     this.optionsAreBinary = paramMessagingPollCreation.isOptionsAreBinary();
/*  39:    */   }
/*  40:    */   
/*  41:    */   private Poll(ResultSet paramResultSet)
/*  42:    */     throws SQLException
/*  43:    */   {
/*  44: 42 */     this.id = paramResultSet.getLong("id");
/*  45: 43 */     this.dbKey = pollDbKeyFactory.newKey(this.id);
/*  46: 44 */     this.name = paramResultSet.getString("name");
/*  47: 45 */     this.description = paramResultSet.getString("description");
/*  48: 46 */     this.options = ((String[])paramResultSet.getArray("options").getArray());
/*  49: 47 */     this.minNumberOfOptions = paramResultSet.getByte("min_num_options");
/*  50: 48 */     this.maxNumberOfOptions = paramResultSet.getByte("max_num_options");
/*  51: 49 */     this.optionsAreBinary = paramResultSet.getBoolean("binary_options");
/*  52:    */   }
/*  53:    */   
/*  54:    */   private void save(Connection paramConnection)
/*  55:    */     throws SQLException
/*  56:    */   {
/*  57: 53 */     PreparedStatement localPreparedStatement = paramConnection.prepareStatement("INSERT INTO poll (id, name, description, options, min_num_options, max_num_options, binary_options, height) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");Object localObject1 = null;
/*  58:    */     try
/*  59:    */     {
/*  60: 55 */       int i = 0;
/*  61: 56 */       localPreparedStatement.setLong(++i, getId());
/*  62: 57 */       localPreparedStatement.setString(++i, getName());
/*  63: 58 */       localPreparedStatement.setString(++i, getDescription());
/*  64: 59 */       localPreparedStatement.setObject(++i, getOptions());
/*  65: 60 */       localPreparedStatement.setByte(++i, getMinNumberOfOptions());
/*  66: 61 */       localPreparedStatement.setByte(++i, getMaxNumberOfOptions());
/*  67: 62 */       localPreparedStatement.setBoolean(++i, isOptionsAreBinary());
/*  68: 63 */       localPreparedStatement.setInt(++i, Nxt.getBlockchain().getHeight());
/*  69: 64 */       localPreparedStatement.executeUpdate();
/*  70:    */     }
/*  71:    */     catch (Throwable localThrowable2)
/*  72:    */     {
/*  73: 53 */       localObject1 = localThrowable2;throw localThrowable2;
/*  74:    */     }
/*  75:    */     finally
/*  76:    */     {
/*  77: 65 */       if (localPreparedStatement != null) {
/*  78: 65 */         if (localObject1 != null) {
/*  79:    */           try
/*  80:    */           {
/*  81: 65 */             localPreparedStatement.close();
/*  82:    */           }
/*  83:    */           catch (Throwable localThrowable3)
/*  84:    */           {
/*  85: 65 */             ((Throwable)localObject1).addSuppressed(localThrowable3);
/*  86:    */           }
/*  87:    */         } else {
/*  88: 65 */           localPreparedStatement.close();
/*  89:    */         }
/*  90:    */       }
/*  91:    */     }
/*  92:    */   }
/*  93:    */   
/*  94:    */   static void addPoll(Transaction paramTransaction, Attachment.MessagingPollCreation paramMessagingPollCreation)
/*  95:    */   {
/*  96: 69 */     pollTable.insert(new Poll(paramTransaction.getId(), paramMessagingPollCreation));
/*  97:    */   }
/*  98:    */   
/*  99:    */   public static Poll getPoll(long paramLong)
/* 100:    */   {
/* 101: 73 */     return (Poll)pollTable.get(pollDbKeyFactory.newKey(paramLong));
/* 102:    */   }
/* 103:    */   
/* 104:    */   public static DbIterator<Poll> getAllPolls(int paramInt1, int paramInt2)
/* 105:    */   {
/* 106: 77 */     return pollTable.getAll(paramInt1, paramInt2);
/* 107:    */   }
/* 108:    */   
/* 109:    */   public static int getCount()
/* 110:    */   {
/* 111: 81 */     return pollTable.getCount();
/* 112:    */   }
/* 113:    */   
/* 114:    */   public long getId()
/* 115:    */   {
/* 116: 86 */     return this.id;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public String getName()
/* 120:    */   {
/* 121: 89 */     return this.name;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public String getDescription()
/* 125:    */   {
/* 126: 91 */     return this.description;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public String[] getOptions()
/* 130:    */   {
/* 131: 93 */     return this.options;
/* 132:    */   }
/* 133:    */   
/* 134:    */   public byte getMinNumberOfOptions()
/* 135:    */   {
/* 136: 95 */     return this.minNumberOfOptions;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public byte getMaxNumberOfOptions()
/* 140:    */   {
/* 141: 97 */     return this.maxNumberOfOptions;
/* 142:    */   }
/* 143:    */   
/* 144:    */   public boolean isOptionsAreBinary()
/* 145:    */   {
/* 146: 99 */     return this.optionsAreBinary;
/* 147:    */   }
/* 148:    */   
/* 149:    */   public Map<Long, Long> getVoters()
/* 150:    */   {
/* 151:102 */     return Vote.getVoters(this);
/* 152:    */   }
/* 153:    */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.Poll
 * JD-Core Version:    0.7.1
 */