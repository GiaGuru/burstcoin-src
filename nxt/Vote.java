/*  1:   */ package nxt;
/*  2:   */ 
/*  3:   */ import java.sql.Connection;
/*  4:   */ import java.sql.PreparedStatement;
/*  5:   */ import java.sql.ResultSet;
/*  6:   */ import java.sql.SQLException;
/*  7:   */ import java.util.HashMap;
/*  8:   */ import java.util.Map;
/*  9:   */ import nxt.db.DbClause.LongClause;
/* 10:   */ import nxt.db.DbIterator;
/* 11:   */ import nxt.db.DbKey;
/* 12:   */ import nxt.db.DbKey.LongKeyFactory;
/* 13:   */ import nxt.db.EntityDbTable;
/* 14:   */ 
/* 15:   */ public final class Vote
/* 16:   */ {
/* 17:17 */   private static final DbKey.LongKeyFactory<Vote> voteDbKeyFactory = null;
/* 18:19 */   private static final EntityDbTable<Vote> voteTable = null;
/* 19:   */   private final long id;
/* 20:   */   private final DbKey dbKey;
/* 21:   */   private final long pollId;
/* 22:   */   private final long voterId;
/* 23:   */   private final byte[] voteBytes;
/* 24:   */   
/* 25:   */   static Vote addVote(Transaction paramTransaction, Attachment.MessagingVoteCasting paramMessagingVoteCasting)
/* 26:   */   {
/* 27:22 */     Vote localVote = new Vote(paramTransaction, paramMessagingVoteCasting);
/* 28:23 */     voteTable.insert(localVote);
/* 29:24 */     return localVote;
/* 30:   */   }
/* 31:   */   
/* 32:   */   public static int getCount()
/* 33:   */   {
/* 34:28 */     return voteTable.getCount();
/* 35:   */   }
/* 36:   */   
/* 37:   */   public static Vote getVote(long paramLong)
/* 38:   */   {
/* 39:32 */     return (Vote)voteTable.get(voteDbKeyFactory.newKey(paramLong));
/* 40:   */   }
/* 41:   */   
/* 42:   */   public static Map<Long, Long> getVoters(Poll paramPoll)
/* 43:   */   {
/* 44:36 */     HashMap localHashMap = new HashMap();
/* 45:37 */     DbIterator localDbIterator = voteTable.getManyBy(new DbClause.LongClause("poll_id", paramPoll.getId()), 0, -1);Object localObject1 = null;
/* 46:   */     try
/* 47:   */     {
/* 48:38 */       while (localDbIterator.hasNext())
/* 49:   */       {
/* 50:39 */         Vote localVote = (Vote)localDbIterator.next();
/* 51:40 */         localHashMap.put(Long.valueOf(localVote.getVoterId()), Long.valueOf(localVote.getId()));
/* 52:   */       }
/* 53:   */     }
/* 54:   */     catch (Throwable localThrowable2)
/* 55:   */     {
/* 56:37 */       localObject1 = localThrowable2;throw localThrowable2;
/* 57:   */     }
/* 58:   */     finally
/* 59:   */     {
/* 60:42 */       if (localDbIterator != null) {
/* 61:42 */         if (localObject1 != null) {
/* 62:   */           try
/* 63:   */           {
/* 64:42 */             localDbIterator.close();
/* 65:   */           }
/* 66:   */           catch (Throwable localThrowable3)
/* 67:   */           {
/* 68:42 */             ((Throwable)localObject1).addSuppressed(localThrowable3);
/* 69:   */           }
/* 70:   */         } else {
/* 71:42 */           localDbIterator.close();
/* 72:   */         }
/* 73:   */       }
/* 74:   */     }
/* 75:43 */     return localHashMap;
/* 76:   */   }
/* 77:   */   
/* 78:   */   static void init() {}
/* 79:   */   
/* 80:   */   private Vote(Transaction paramTransaction, Attachment.MessagingVoteCasting paramMessagingVoteCasting)
/* 81:   */   {
/* 82:56 */     this.id = paramTransaction.getId();
/* 83:57 */     this.dbKey = voteDbKeyFactory.newKey(this.id);
/* 84:58 */     this.pollId = paramMessagingVoteCasting.getPollId();
/* 85:59 */     this.voterId = paramTransaction.getSenderId();
/* 86:60 */     this.voteBytes = paramMessagingVoteCasting.getPollVote();
/* 87:   */   }
/* 88:   */   
/* 89:   */   private Vote(ResultSet paramResultSet)
/* 90:   */     throws SQLException
/* 91:   */   {
/* 92:64 */     this.id = paramResultSet.getLong("id");
/* 93:65 */     this.dbKey = voteDbKeyFactory.newKey(this.id);
/* 94:66 */     this.pollId = paramResultSet.getLong("poll_id");
/* 95:67 */     this.voterId = paramResultSet.getLong("voter_id");
/* 96:68 */     this.voteBytes = paramResultSet.getBytes("vote_bytes");
/* 97:   */   }
/* 98:   */   
/* 99:   */   private void save(Connection paramConnection)
/* :0:   */     throws SQLException
/* :1:   */   {
/* :2:72 */     PreparedStatement localPreparedStatement = paramConnection.prepareStatement("INSERT INTO vote (id, poll_id, voter_id, vote_bytes, height) VALUES (?, ?, ?, ?, ?)");Object localObject1 = null;
/* :3:   */     try
/* :4:   */     {
/* :5:74 */       int i = 0;
/* :6:75 */       localPreparedStatement.setLong(++i, getId());
/* :7:76 */       localPreparedStatement.setLong(++i, getPollId());
/* :8:77 */       localPreparedStatement.setLong(++i, getVoterId());
/* :9:78 */       localPreparedStatement.setBytes(++i, getVote());
/* ;0:79 */       localPreparedStatement.setInt(++i, Nxt.getBlockchain().getHeight());
/* ;1:80 */       localPreparedStatement.executeUpdate();
/* ;2:   */     }
/* ;3:   */     catch (Throwable localThrowable2)
/* ;4:   */     {
/* ;5:72 */       localObject1 = localThrowable2;throw localThrowable2;
/* ;6:   */     }
/* ;7:   */     finally
/* ;8:   */     {
/* ;9:81 */       if (localPreparedStatement != null) {
/* <0:81 */         if (localObject1 != null) {
/* <1:   */           try
/* <2:   */           {
/* <3:81 */             localPreparedStatement.close();
/* <4:   */           }
/* <5:   */           catch (Throwable localThrowable3)
/* <6:   */           {
/* <7:81 */             ((Throwable)localObject1).addSuppressed(localThrowable3);
/* <8:   */           }
/* <9:   */         } else {
/* =0:81 */           localPreparedStatement.close();
/* =1:   */         }
/* =2:   */       }
/* =3:   */     }
/* =4:   */   }
/* =5:   */   
/* =6:   */   public long getId()
/* =7:   */   {
/* =8:85 */     return this.id;
/* =9:   */   }
/* >0:   */   
/* >1:   */   public long getPollId()
/* >2:   */   {
/* >3:88 */     return this.pollId;
/* >4:   */   }
/* >5:   */   
/* >6:   */   public long getVoterId()
/* >7:   */   {
/* >8:90 */     return this.voterId;
/* >9:   */   }
/* ?0:   */   
/* ?1:   */   public byte[] getVote()
/* ?2:   */   {
/* ?3:92 */     return this.voteBytes;
/* ?4:   */   }
/* ?5:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.Vote
 * JD-Core Version:    0.7.1
 */