/*   1:    */ package nxt;
/*   2:    */ 
/*   3:    */ import java.sql.Connection;
/*   4:    */ import java.sql.PreparedStatement;
/*   5:    */ import java.sql.ResultSet;
/*   6:    */ import java.sql.SQLException;
/*   7:    */ import nxt.db.DbClause.LongClause;
/*   8:    */ import nxt.db.DbIterator;
/*   9:    */ import nxt.db.DbKey;
/*  10:    */ import nxt.db.DbKey.Factory;
/*  11:    */ import nxt.db.DbKey.LongKeyFactory;
/*  12:    */ import nxt.db.EntityDbTable;
/*  13:    */ 
/*  14:    */ public final class Asset
/*  15:    */ {
/*  16: 15 */   private static final DbKey.LongKeyFactory<Asset> assetDbKeyFactory = new DbKey.LongKeyFactory("id")
/*  17:    */   {
/*  18:    */     public DbKey newKey(Asset paramAnonymousAsset)
/*  19:    */     {
/*  20: 19 */       return paramAnonymousAsset.dbKey;
/*  21:    */     }
/*  22:    */   };
/*  23: 24 */   private static final EntityDbTable<Asset> assetTable = new EntityDbTable("asset", assetDbKeyFactory)
/*  24:    */   {
/*  25:    */     protected Asset load(Connection paramAnonymousConnection, ResultSet paramAnonymousResultSet)
/*  26:    */       throws SQLException
/*  27:    */     {
/*  28: 28 */       return new Asset(paramAnonymousResultSet, null);
/*  29:    */     }
/*  30:    */     
/*  31:    */     protected void save(Connection paramAnonymousConnection, Asset paramAnonymousAsset)
/*  32:    */       throws SQLException
/*  33:    */     {
/*  34: 33 */       paramAnonymousAsset.save(paramAnonymousConnection);
/*  35:    */     }
/*  36:    */   };
/*  37:    */   private final long assetId;
/*  38:    */   private final DbKey dbKey;
/*  39:    */   private final long accountId;
/*  40:    */   private final String name;
/*  41:    */   private final String description;
/*  42:    */   private final long quantityQNT;
/*  43:    */   private final byte decimals;
/*  44:    */   
/*  45:    */   public static DbIterator<Asset> getAllAssets(int paramInt1, int paramInt2)
/*  46:    */   {
/*  47: 39 */     return assetTable.getAll(paramInt1, paramInt2);
/*  48:    */   }
/*  49:    */   
/*  50:    */   public static int getCount()
/*  51:    */   {
/*  52: 43 */     return assetTable.getCount();
/*  53:    */   }
/*  54:    */   
/*  55:    */   public static Asset getAsset(long paramLong)
/*  56:    */   {
/*  57: 47 */     return (Asset)assetTable.get(assetDbKeyFactory.newKey(paramLong));
/*  58:    */   }
/*  59:    */   
/*  60:    */   public static DbIterator<Asset> getAssetsIssuedBy(long paramLong, int paramInt1, int paramInt2)
/*  61:    */   {
/*  62: 51 */     return assetTable.getManyBy(new DbClause.LongClause("account_id", paramLong), paramInt1, paramInt2);
/*  63:    */   }
/*  64:    */   
/*  65:    */   static void addAsset(Transaction paramTransaction, Attachment.ColoredCoinsAssetIssuance paramColoredCoinsAssetIssuance)
/*  66:    */   {
/*  67: 55 */     assetTable.insert(new Asset(paramTransaction, paramColoredCoinsAssetIssuance));
/*  68:    */   }
/*  69:    */   
/*  70:    */   static void init() {}
/*  71:    */   
/*  72:    */   private Asset(Transaction paramTransaction, Attachment.ColoredCoinsAssetIssuance paramColoredCoinsAssetIssuance)
/*  73:    */   {
/*  74: 70 */     this.assetId = paramTransaction.getId();
/*  75: 71 */     this.dbKey = assetDbKeyFactory.newKey(this.assetId);
/*  76: 72 */     this.accountId = paramTransaction.getSenderId();
/*  77: 73 */     this.name = paramColoredCoinsAssetIssuance.getName();
/*  78: 74 */     this.description = paramColoredCoinsAssetIssuance.getDescription();
/*  79: 75 */     this.quantityQNT = paramColoredCoinsAssetIssuance.getQuantityQNT();
/*  80: 76 */     this.decimals = paramColoredCoinsAssetIssuance.getDecimals();
/*  81:    */   }
/*  82:    */   
/*  83:    */   private Asset(ResultSet paramResultSet)
/*  84:    */     throws SQLException
/*  85:    */   {
/*  86: 80 */     this.assetId = paramResultSet.getLong("id");
/*  87: 81 */     this.dbKey = assetDbKeyFactory.newKey(this.assetId);
/*  88: 82 */     this.accountId = paramResultSet.getLong("account_id");
/*  89: 83 */     this.name = paramResultSet.getString("name");
/*  90: 84 */     this.description = paramResultSet.getString("description");
/*  91: 85 */     this.quantityQNT = paramResultSet.getLong("quantity");
/*  92: 86 */     this.decimals = paramResultSet.getByte("decimals");
/*  93:    */   }
/*  94:    */   
/*  95:    */   private void save(Connection paramConnection)
/*  96:    */     throws SQLException
/*  97:    */   {
/*  98: 90 */     PreparedStatement localPreparedStatement = paramConnection.prepareStatement("INSERT INTO asset (id, account_id, name, description, quantity, decimals, height) VALUES (?, ?, ?, ?, ?, ?, ?)");Object localObject1 = null;
/*  99:    */     try
/* 100:    */     {
/* 101: 92 */       int i = 0;
/* 102: 93 */       localPreparedStatement.setLong(++i, getId());
/* 103: 94 */       localPreparedStatement.setLong(++i, getAccountId());
/* 104: 95 */       localPreparedStatement.setString(++i, getName());
/* 105: 96 */       localPreparedStatement.setString(++i, getDescription());
/* 106: 97 */       localPreparedStatement.setLong(++i, getQuantityQNT());
/* 107: 98 */       localPreparedStatement.setByte(++i, getDecimals());
/* 108: 99 */       localPreparedStatement.setInt(++i, Nxt.getBlockchain().getHeight());
/* 109:100 */       localPreparedStatement.executeUpdate();
/* 110:    */     }
/* 111:    */     catch (Throwable localThrowable2)
/* 112:    */     {
/* 113: 90 */       localObject1 = localThrowable2;throw localThrowable2;
/* 114:    */     }
/* 115:    */     finally
/* 116:    */     {
/* 117:101 */       if (localPreparedStatement != null) {
/* 118:101 */         if (localObject1 != null) {
/* 119:    */           try
/* 120:    */           {
/* 121:101 */             localPreparedStatement.close();
/* 122:    */           }
/* 123:    */           catch (Throwable localThrowable3)
/* 124:    */           {
/* 125:101 */             ((Throwable)localObject1).addSuppressed(localThrowable3);
/* 126:    */           }
/* 127:    */         } else {
/* 128:101 */           localPreparedStatement.close();
/* 129:    */         }
/* 130:    */       }
/* 131:    */     }
/* 132:    */   }
/* 133:    */   
/* 134:    */   public long getId()
/* 135:    */   {
/* 136:105 */     return this.assetId;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public long getAccountId()
/* 140:    */   {
/* 141:109 */     return this.accountId;
/* 142:    */   }
/* 143:    */   
/* 144:    */   public String getName()
/* 145:    */   {
/* 146:113 */     return this.name;
/* 147:    */   }
/* 148:    */   
/* 149:    */   public String getDescription()
/* 150:    */   {
/* 151:117 */     return this.description;
/* 152:    */   }
/* 153:    */   
/* 154:    */   public long getQuantityQNT()
/* 155:    */   {
/* 156:121 */     return this.quantityQNT;
/* 157:    */   }
/* 158:    */   
/* 159:    */   public byte getDecimals()
/* 160:    */   {
/* 161:125 */     return this.decimals;
/* 162:    */   }
/* 163:    */   
/* 164:    */   public DbIterator<Account.AccountAsset> getAccounts(int paramInt1, int paramInt2)
/* 165:    */   {
/* 166:129 */     return Account.getAssetAccounts(this.assetId, paramInt1, paramInt2);
/* 167:    */   }
/* 168:    */   
/* 169:    */   public DbIterator<Account.AccountAsset> getAccounts(int paramInt1, int paramInt2, int paramInt3)
/* 170:    */   {
/* 171:133 */     if (paramInt1 < 0) {
/* 172:134 */       return getAccounts(paramInt2, paramInt3);
/* 173:    */     }
/* 174:136 */     return Account.getAssetAccounts(this.assetId, paramInt1, paramInt2, paramInt3);
/* 175:    */   }
/* 176:    */   
/* 177:    */   public DbIterator<Trade> getTrades(int paramInt1, int paramInt2)
/* 178:    */   {
/* 179:140 */     return Trade.getAssetTrades(this.assetId, paramInt1, paramInt2);
/* 180:    */   }
/* 181:    */   
/* 182:    */   public DbIterator<AssetTransfer> getAssetTransfers(int paramInt1, int paramInt2)
/* 183:    */   {
/* 184:144 */     return AssetTransfer.getAssetTransfers(this.assetId, paramInt1, paramInt2);
/* 185:    */   }
/* 186:    */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.Asset
 * JD-Core Version:    0.7.1
 */