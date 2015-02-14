package fr.cryptohash;

public abstract interface Digest
{
  public abstract void update(byte paramByte);
  
  public abstract void update(byte[] paramArrayOfByte);
  
  public abstract void update(byte[] paramArrayOfByte, int paramInt1, int paramInt2);
  
  public abstract byte[] digest();
  
  public abstract byte[] digest(byte[] paramArrayOfByte);
  
  public abstract int digest(byte[] paramArrayOfByte, int paramInt1, int paramInt2);
  
  public abstract int getDigestLength();
  
  public abstract void reset();
  
  public abstract Digest copy();
  
  public abstract int getBlockLength();
  
  public abstract String toString();
}


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     fr.cryptohash.Digest
 * JD-Core Version:    0.7.1
 */