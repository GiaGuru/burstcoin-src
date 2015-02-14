/*   1:    */ package nxt;
/*   2:    */ 
/*   3:    */ import java.sql.Connection;
/*   4:    */ import java.sql.PreparedStatement;
/*   5:    */ import java.sql.ResultSet;
/*   6:    */ import java.sql.SQLException;
/*   7:    */ import nxt.db.DbClause.LongClause;
/*   8:    */ import nxt.db.DbClause.StringClause;
/*   9:    */ import nxt.db.DbIterator;
/*  10:    */ import nxt.db.DbKey;
/*  11:    */ import nxt.db.DbKey.Factory;
/*  12:    */ import nxt.db.DbKey.LongKeyFactory;
/*  13:    */ import nxt.db.DbUtils;
/*  14:    */ import nxt.db.VersionedEntityDbTable;
/*  15:    */ 
/*  16:    */ public final class Alias
/*  17:    */ {
/*  18:    */   public static class Offer
/*  19:    */   {
/*  20:    */     private long priceNQT;
/*  21:    */     private long buyerId;
/*  22:    */     private final long aliasId;
/*  23:    */     private final DbKey dbKey;
/*  24:    */     
/*  25:    */     private Offer(long paramLong1, long paramLong2, long paramLong3)
/*  26:    */     {
/*  27: 24 */       this.priceNQT = paramLong2;
/*  28: 25 */       this.buyerId = paramLong3;
/*  29: 26 */       this.aliasId = paramLong1;
/*  30: 27 */       this.dbKey = Alias.offerDbKeyFactory.newKey(this.aliasId);
/*  31:    */     }
/*  32:    */     
/*  33:    */     private Offer(ResultSet paramResultSet)
/*  34:    */       throws SQLException
/*  35:    */     {
/*  36: 31 */       this.aliasId = paramResultSet.getLong("id");
/*  37: 32 */       this.dbKey = Alias.offerDbKeyFactory.newKey(this.aliasId);
/*  38: 33 */       this.priceNQT = paramResultSet.getLong("price");
/*  39: 34 */       this.buyerId = paramResultSet.getLong("buyer_id");
/*  40:    */     }
/*  41:    */     
/*  42:    */     private void save(Connection paramConnection)
/*  43:    */       throws SQLException
/*  44:    */     {
/*  45: 38 */       PreparedStatement localPreparedStatement = paramConnection.prepareStatement("INSERT INTO alias_offer (id, price, buyer_id, height) VALUES (?, ?, ?, ?)");Object localObject1 = null;
/*  46:    */       try
/*  47:    */       {
/*  48: 40 */         int i = 0;
/*  49: 41 */         localPreparedStatement.setLong(++i, getId());
/*  50: 42 */         localPreparedStatement.setLong(++i, getPriceNQT());
/*  51: 43 */         DbUtils.setLongZeroToNull(localPreparedStatement, ++i, getBuyerId());
/*  52: 44 */         localPreparedStatement.setInt(++i, Nxt.getBlockchain().getHeight());
/*  53: 45 */         localPreparedStatement.executeUpdate();
/*  54:    */       }
/*  55:    */       catch (Throwable localThrowable2)
/*  56:    */       {
/*  57: 38 */         localObject1 = localThrowable2;throw localThrowable2;
/*  58:    */       }
/*  59:    */       finally
/*  60:    */       {
/*  61: 46 */         if (localPreparedStatement != null) {
/*  62: 46 */           if (localObject1 != null) {
/*  63:    */             try
/*  64:    */             {
/*  65: 46 */               localPreparedStatement.close();
/*  66:    */             }
/*  67:    */             catch (Throwable localThrowable3)
/*  68:    */             {
/*  69: 46 */               ((Throwable)localObject1).addSuppressed(localThrowable3);
/*  70:    */             }
/*  71:    */           } else {
/*  72: 46 */             localPreparedStatement.close();
/*  73:    */           }
/*  74:    */         }
/*  75:    */       }
/*  76:    */     }
/*  77:    */     
/*  78:    */     public long getId()
/*  79:    */     {
/*  80: 50 */       return this.aliasId;
/*  81:    */     }
/*  82:    */     
/*  83:    */     public long getPriceNQT()
/*  84:    */     {
/*  85: 54 */       return this.priceNQT;
/*  86:    */     }
/*  87:    */     
/*  88:    */     public long getBuyerId()
/*  89:    */     {
/*  90: 58 */       return this.buyerId;
/*  91:    */     }
/*  92:    */   }
/*  93:    */   
/*  94: 63 */   private static final DbKey.LongKeyFactory<Alias> aliasDbKeyFactory = new DbKey.LongKeyFactory("id")
/*  95:    */   {
/*  96:    */     public DbKey newKey(Alias paramAnonymousAlias)
/*  97:    */     {
/*  98: 67 */       return paramAnonymousAlias.dbKey;
/*  99:    */     }
/* 100:    */   };
/* 101: 72 */   private static final VersionedEntityDbTable<Alias> aliasTable = new VersionedEntityDbTable("alias", aliasDbKeyFactory)
/* 102:    */   {
/* 103:    */     protected Alias load(Connection paramAnonymousConnection, ResultSet paramAnonymousResultSet)
/* 104:    */       throws SQLException
/* 105:    */     {
/* 106: 76 */       return new Alias(paramAnonymousResultSet, null);
/* 107:    */     }
/* 108:    */     
/* 109:    */     protected void save(Connection paramAnonymousConnection, Alias paramAnonymousAlias)
/* 110:    */       throws SQLException
/* 111:    */     {
/* 112: 81 */       paramAnonymousAlias.save(paramAnonymousConnection);
/* 113:    */     }
/* 114:    */     
/* 115:    */     protected String defaultSort()
/* 116:    */     {
/* 117: 86 */       return " ORDER BY alias_name_lower ";
/* 118:    */     }
/* 119:    */   };
/* 120: 91 */   private static final DbKey.LongKeyFactory<Offer> offerDbKeyFactory = new DbKey.LongKeyFactory("id")
/* 121:    */   {
/* 122:    */     public DbKey newKey(Alias.Offer paramAnonymousOffer)
/* 123:    */     {
/* 124: 95 */       return paramAnonymousOffer.dbKey;
/* 125:    */     }
/* 126:    */   };
/* 127:100 */   private static final VersionedEntityDbTable<Offer> offerTable = new VersionedEntityDbTable("alias_offer", offerDbKeyFactory)
/* 128:    */   {
/* 129:    */     protected Alias.Offer load(Connection paramAnonymousConnection, ResultSet paramAnonymousResultSet)
/* 130:    */       throws SQLException
/* 131:    */     {
/* 132:104 */       return new Alias.Offer(paramAnonymousResultSet, null);
/* 133:    */     }
/* 134:    */     
/* 135:    */     protected void save(Connection paramAnonymousConnection, Alias.Offer paramAnonymousOffer)
/* 136:    */       throws SQLException
/* 137:    */     {
/* 138:109 */       paramAnonymousOffer.save(paramAnonymousConnection);
/* 139:    */     }
/* 140:    */   };
/* 141:    */   private long accountId;
/* 142:    */   private final long id;
/* 143:    */   private final DbKey dbKey;
/* 144:    */   private final String aliasName;
/* 145:    */   private String aliasURI;
/* 146:    */   private int timestamp;
/* 147:    */   
/* 148:    */   public static int getCount()
/* 149:    */   {
/* 150:115 */     return aliasTable.getCount();
/* 151:    */   }
/* 152:    */   
/* 153:    */   public static DbIterator<Alias> getAliasesByOwner(long paramLong, int paramInt1, int paramInt2)
/* 154:    */   {
/* 155:119 */     return aliasTable.getManyBy(new DbClause.LongClause("account_id", paramLong), paramInt1, paramInt2);
/* 156:    */   }
/* 157:    */   
/* 158:    */   public static Alias getAlias(String paramString)
/* 159:    */   {
/* 160:123 */     return (Alias)aliasTable.getBy(new DbClause.StringClause("alias_name_lower", paramString.toLowerCase()));
/* 161:    */   }
/* 162:    */   
/* 163:    */   public static Alias getAlias(long paramLong)
/* 164:    */   {
/* 165:127 */     return (Alias)aliasTable.get(aliasDbKeyFactory.newKey(paramLong));
/* 166:    */   }
/* 167:    */   
/* 168:    */   public static Offer getOffer(Alias paramAlias)
/* 169:    */   {
/* 170:131 */     return (Offer)offerTable.get(offerDbKeyFactory.newKey(paramAlias.getId()));
/* 171:    */   }
/* 172:    */   
/* 173:    */   static void addOrUpdateAlias(Transaction paramTransaction, Attachment.MessagingAliasAssignment paramMessagingAliasAssignment)
/* 174:    */   {
/* 175:135 */     Alias localAlias = getAlias(paramMessagingAliasAssignment.getAliasName());
/* 176:136 */     if (localAlias == null)
/* 177:    */     {
/* 178:137 */       localAlias = new Alias(paramTransaction.getId(), paramTransaction, paramMessagingAliasAssignment);
/* 179:    */     }
/* 180:    */     else
/* 181:    */     {
/* 182:139 */       localAlias.accountId = paramTransaction.getSenderId();
/* 183:140 */       localAlias.aliasURI = paramMessagingAliasAssignment.getAliasURI();
/* 184:141 */       localAlias.timestamp = paramTransaction.getBlockTimestamp();
/* 185:    */     }
/* 186:143 */     aliasTable.insert(localAlias);
/* 187:    */   }
/* 188:    */   
/* 189:    */   static void sellAlias(Transaction paramTransaction, Attachment.MessagingAliasSell paramMessagingAliasSell)
/* 190:    */   {
/* 191:147 */     String str = paramMessagingAliasSell.getAliasName();
/* 192:148 */     long l1 = paramMessagingAliasSell.getPriceNQT();
/* 193:149 */     long l2 = paramTransaction.getRecipientId();
/* 194:150 */     if (l1 > 0L)
/* 195:    */     {
/* 196:151 */       Alias localAlias = getAlias(str);
/* 197:152 */       Offer localOffer = getOffer(localAlias);
/* 198:153 */       if (localOffer == null)
/* 199:    */       {
/* 200:154 */         offerTable.insert(new Offer(localAlias.id, l1, l2, null));
/* 201:    */       }
/* 202:    */       else
/* 203:    */       {
/* 204:156 */         localOffer.priceNQT = l1;
/* 205:157 */         localOffer.buyerId = l2;
/* 206:158 */         offerTable.insert(localOffer);
/* 207:    */       }
/* 208:    */     }
/* 209:    */     else
/* 210:    */     {
/* 211:161 */       changeOwner(l2, str, paramTransaction.getBlockTimestamp());
/* 212:    */     }
/* 213:    */   }
/* 214:    */   
/* 215:    */   static void changeOwner(long paramLong, String paramString, int paramInt)
/* 216:    */   {
/* 217:167 */     Alias localAlias = getAlias(paramString);
/* 218:168 */     localAlias.accountId = paramLong;
/* 219:169 */     localAlias.timestamp = paramInt;
/* 220:170 */     aliasTable.insert(localAlias);
/* 221:171 */     Offer localOffer = getOffer(localAlias);
/* 222:172 */     offerTable.delete(localOffer);
/* 223:    */   }
/* 224:    */   
/* 225:    */   static void init() {}
/* 226:    */   
/* 227:    */   private Alias(long paramLong1, long paramLong2, String paramString1, String paramString2, int paramInt)
/* 228:    */   {
/* 229:186 */     this.id = paramLong1;
/* 230:187 */     this.dbKey = aliasDbKeyFactory.newKey(this.id);
/* 231:188 */     this.accountId = paramLong2;
/* 232:189 */     this.aliasName = paramString1;
/* 233:190 */     this.aliasURI = paramString2;
/* 234:191 */     this.timestamp = paramInt;
/* 235:    */   }
/* 236:    */   
/* 237:    */   private Alias(long paramLong, Transaction paramTransaction, Attachment.MessagingAliasAssignment paramMessagingAliasAssignment)
/* 238:    */   {
/* 239:195 */     this(paramLong, paramTransaction.getSenderId(), paramMessagingAliasAssignment.getAliasName(), paramMessagingAliasAssignment.getAliasURI(), paramTransaction.getBlockTimestamp());
/* 240:    */   }
/* 241:    */   
/* 242:    */   private Alias(ResultSet paramResultSet)
/* 243:    */     throws SQLException
/* 244:    */   {
/* 245:200 */     this.id = paramResultSet.getLong("id");
/* 246:201 */     this.dbKey = aliasDbKeyFactory.newKey(this.id);
/* 247:202 */     this.accountId = paramResultSet.getLong("account_id");
/* 248:203 */     this.aliasName = paramResultSet.getString("alias_name");
/* 249:204 */     this.aliasURI = paramResultSet.getString("alias_uri");
/* 250:205 */     this.timestamp = paramResultSet.getInt("timestamp");
/* 251:    */   }
/* 252:    */   
/* 253:    */   private void save(Connection paramConnection)
/* 254:    */     throws SQLException
/* 255:    */   {
/* 256:209 */     PreparedStatement localPreparedStatement = paramConnection.prepareStatement("INSERT INTO alias (id, account_id, alias_name, alias_uri, timestamp, height) VALUES (?, ?, ?, ?, ?, ?)");Object localObject1 = null;
/* 257:    */     try
/* 258:    */     {
/* 259:212 */       int i = 0;
/* 260:213 */       localPreparedStatement.setLong(++i, getId());
/* 261:214 */       localPreparedStatement.setLong(++i, getAccountId());
/* 262:215 */       localPreparedStatement.setString(++i, getAliasName());
/* 263:216 */       localPreparedStatement.setString(++i, getAliasURI());
/* 264:217 */       localPreparedStatement.setInt(++i, getTimestamp());
/* 265:218 */       localPreparedStatement.setInt(++i, Nxt.getBlockchain().getHeight());
/* 266:219 */       localPreparedStatement.executeUpdate();
/* 267:    */     }
/* 268:    */     catch (Throwable localThrowable2)
/* 269:    */     {
/* 270:209 */       localObject1 = localThrowable2;throw localThrowable2;
/* 271:    */     }
/* 272:    */     finally
/* 273:    */     {
/* 274:220 */       if (localPreparedStatement != null) {
/* 275:220 */         if (localObject1 != null) {
/* 276:    */           try
/* 277:    */           {
/* 278:220 */             localPreparedStatement.close();
/* 279:    */           }
/* 280:    */           catch (Throwable localThrowable3)
/* 281:    */           {
/* 282:220 */             ((Throwable)localObject1).addSuppressed(localThrowable3);
/* 283:    */           }
/* 284:    */         } else {
/* 285:220 */           localPreparedStatement.close();
/* 286:    */         }
/* 287:    */       }
/* 288:    */     }
/* 289:    */   }
/* 290:    */   
/* 291:    */   public long getId()
/* 292:    */   {
/* 293:224 */     return this.id;
/* 294:    */   }
/* 295:    */   
/* 296:    */   public String getAliasName()
/* 297:    */   {
/* 298:228 */     return this.aliasName;
/* 299:    */   }
/* 300:    */   
/* 301:    */   public String getAliasURI()
/* 302:    */   {
/* 303:232 */     return this.aliasURI;
/* 304:    */   }
/* 305:    */   
/* 306:    */   public int getTimestamp()
/* 307:    */   {
/* 308:236 */     return this.timestamp;
/* 309:    */   }
/* 310:    */   
/* 311:    */   public long getAccountId()
/* 312:    */   {
/* 313:240 */     return this.accountId;
/* 314:    */   }
/* 315:    */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.Alias
 * JD-Core Version:    0.7.1
 */