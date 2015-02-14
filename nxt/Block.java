package nxt;

import java.math.BigInteger;
import java.util.List;
import org.json.simple.JSONObject;

public abstract interface Block
{
  public abstract int getVersion();
  
  public abstract long getId();
  
  public abstract String getStringId();
  
  public abstract int getHeight();
  
  public abstract int getTimestamp();
  
  public abstract long getGeneratorId();
  
  public abstract Long getNonce();
  
  public abstract int getScoopNum();
  
  public abstract byte[] getGeneratorPublicKey();
  
  public abstract byte[] getBlockHash();
  
  public abstract long getPreviousBlockId();
  
  public abstract byte[] getPreviousBlockHash();
  
  public abstract long getNextBlockId();
  
  public abstract long getTotalAmountNQT();
  
  public abstract long getTotalFeeNQT();
  
  public abstract int getPayloadLength();
  
  public abstract byte[] getPayloadHash();
  
  public abstract List<? extends Transaction> getTransactions();
  
  public abstract byte[] getGenerationSignature();
  
  public abstract byte[] getBlockSignature();
  
  public abstract long getBaseTarget();
  
  public abstract long getBlockReward();
  
  public abstract BigInteger getCumulativeDifficulty();
  
  public abstract JSONObject getJSONObject();
  
  public abstract byte[] getBlockATs();
}


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.Block
 * JD-Core Version:    0.7.1
 */