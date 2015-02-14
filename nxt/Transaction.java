package nxt;

import java.util.List;
import org.json.simple.JSONObject;

public abstract interface Transaction
  extends Comparable<Transaction>
{
  public abstract long getId();
  
  public abstract String getStringId();
  
  public abstract long getSenderId();
  
  public abstract byte[] getSenderPublicKey();
  
  public abstract long getRecipientId();
  
  public abstract int getHeight();
  
  public abstract long getBlockId();
  
  public abstract Block getBlock();
  
  public abstract int getTimestamp();
  
  public abstract int getBlockTimestamp();
  
  public abstract short getDeadline();
  
  public abstract int getExpiration();
  
  public abstract long getAmountNQT();
  
  public abstract long getFeeNQT();
  
  public abstract String getReferencedTransactionFullHash();
  
  public abstract byte[] getSignature();
  
  public abstract String getFullHash();
  
  public abstract TransactionType getType();
  
  public abstract Attachment getAttachment();
  
  public abstract void sign(String paramString);
  
  public abstract boolean verifySignature();
  
  public abstract void validate()
    throws NxtException.ValidationException;
  
  public abstract byte[] getBytes();
  
  public abstract byte[] getUnsignedBytes();
  
  public abstract JSONObject getJSONObject();
  
  public abstract byte getVersion();
  
  public abstract Appendix.Message getMessage();
  
  public abstract Appendix.EncryptedMessage getEncryptedMessage();
  
  public abstract Appendix.EncryptToSelfMessage getEncryptToSelfMessage();
  
  public abstract List<? extends Appendix> getAppendages();
  
  public abstract int getECBlockHeight();
  
  public abstract long getECBlockId();
  
  public static abstract interface Builder
  {
    public abstract Builder recipientId(long paramLong);
    
    public abstract Builder referencedTransactionFullHash(String paramString);
    
    public abstract Builder message(Appendix.Message paramMessage);
    
    public abstract Builder encryptedMessage(Appendix.EncryptedMessage paramEncryptedMessage);
    
    public abstract Builder encryptToSelfMessage(Appendix.EncryptToSelfMessage paramEncryptToSelfMessage);
    
    public abstract Builder publicKeyAnnouncement(Appendix.PublicKeyAnnouncement paramPublicKeyAnnouncement);
    
    public abstract Transaction build()
      throws NxtException.NotValidException;
  }
}


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.Transaction
 * JD-Core Version:    0.7.1
 */