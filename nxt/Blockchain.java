package nxt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;
import nxt.db.DbIterator;

public abstract interface Blockchain
{
  public abstract Block getLastBlock();
  
  public abstract Block getLastBlock(int paramInt);
  
  public abstract int getHeight();
  
  public abstract Block getBlock(long paramLong);
  
  public abstract Block getBlockAtHeight(int paramInt);
  
  public abstract boolean hasBlock(long paramLong);
  
  public abstract DbIterator<? extends Block> getAllBlocks();
  
  public abstract DbIterator<? extends Block> getBlocks(int paramInt1, int paramInt2);
  
  public abstract DbIterator<? extends Block> getBlocks(Account paramAccount, int paramInt);
  
  public abstract DbIterator<? extends Block> getBlocks(Account paramAccount, int paramInt1, int paramInt2, int paramInt3);
  
  public abstract DbIterator<? extends Block> getBlocks(Connection paramConnection, PreparedStatement paramPreparedStatement);
  
  public abstract List<Long> getBlockIdsAfter(long paramLong, int paramInt);
  
  public abstract List<? extends Block> getBlocksAfter(long paramLong, int paramInt);
  
  public abstract long getBlockIdAtHeight(int paramInt);
  
  public abstract Transaction getTransaction(long paramLong);
  
  public abstract Transaction getTransactionByFullHash(String paramString);
  
  public abstract boolean hasTransaction(long paramLong);
  
  public abstract boolean hasTransactionByFullHash(String paramString);
  
  public abstract int getTransactionCount();
  
  public abstract DbIterator<? extends Transaction> getAllTransactions();
  
  public abstract DbIterator<? extends Transaction> getTransactions(Account paramAccount, byte paramByte1, byte paramByte2, int paramInt);
  
  public abstract DbIterator<? extends Transaction> getTransactions(Account paramAccount, int paramInt1, byte paramByte1, byte paramByte2, int paramInt2, int paramInt3, int paramInt4);
  
  public abstract DbIterator<? extends Transaction> getTransactions(Connection paramConnection, PreparedStatement paramPreparedStatement);
}


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.Blockchain
 * JD-Core Version:    0.7.1
 */