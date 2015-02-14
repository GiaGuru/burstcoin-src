/*  1:   */ package nxt.util;
/*  2:   */ 
/*  3:   */ import fr.cryptohash.Shabal256;
/*  4:   */ import java.nio.ByteBuffer;
/*  5:   */ import java.util.Arrays;
/*  6:   */ 
/*  7:   */ public class MiningPlot
/*  8:   */ {
/*  9:10 */   public static int HASH_SIZE = 32;
/* 10:11 */   public static int HASHES_PER_SCOOP = 2;
/* 11:12 */   public static int SCOOP_SIZE = HASHES_PER_SCOOP * HASH_SIZE;
/* 12:13 */   public static int SCOOPS_PER_PLOT = 4096;
/* 13:14 */   public static int PLOT_SIZE = SCOOPS_PER_PLOT * SCOOP_SIZE;
/* 14:16 */   public static int HASH_CAP = 4096;
/* 15:18 */   public byte[] data = new byte[PLOT_SIZE];
/* 16:   */   
/* 17:   */   public MiningPlot(long paramLong1, long paramLong2)
/* 18:   */   {
/* 19:21 */     ByteBuffer localByteBuffer = ByteBuffer.allocate(16);
/* 20:22 */     localByteBuffer.putLong(paramLong1);
/* 21:23 */     localByteBuffer.putLong(paramLong2);
/* 22:24 */     byte[] arrayOfByte1 = localByteBuffer.array();
/* 23:25 */     Shabal256 localShabal256 = new Shabal256();
/* 24:26 */     byte[] arrayOfByte2 = new byte[PLOT_SIZE + arrayOfByte1.length];
/* 25:27 */     System.arraycopy(arrayOfByte1, 0, arrayOfByte2, PLOT_SIZE, arrayOfByte1.length);
/* 26:28 */     for (int i = PLOT_SIZE; i > 0; i -= HASH_SIZE)
/* 27:   */     {
/* 28:29 */       localShabal256.reset();
/* 29:30 */       j = PLOT_SIZE + arrayOfByte1.length - i;
/* 30:31 */       if (j > HASH_CAP) {
/* 31:32 */         j = HASH_CAP;
/* 32:   */       }
/* 33:34 */       localShabal256.update(arrayOfByte2, i, j);
/* 34:35 */       localShabal256.digest(arrayOfByte2, i - HASH_SIZE, HASH_SIZE);
/* 35:   */     }
/* 36:37 */     localShabal256.reset();
/* 37:38 */     localShabal256.update(arrayOfByte2);
/* 38:39 */     byte[] arrayOfByte3 = localShabal256.digest();
/* 39:40 */     for (int j = 0; j < PLOT_SIZE; j++) {
/* 40:41 */       this.data[j] = ((byte)(arrayOfByte2[j] ^ arrayOfByte3[(j % HASH_SIZE)]));
/* 41:   */     }
/* 42:   */   }
/* 43:   */   
/* 44:   */   public byte[] getScoop(int paramInt)
/* 45:   */   {
/* 46:46 */     return Arrays.copyOfRange(this.data, paramInt * SCOOP_SIZE, (paramInt + 1) * SCOOP_SIZE);
/* 47:   */   }
/* 48:   */   
/* 49:   */   public void hashScoop(Shabal256 paramShabal256, int paramInt)
/* 50:   */   {
/* 51:50 */     paramShabal256.update(this.data, paramInt * SCOOP_SIZE, SCOOP_SIZE);
/* 52:   */   }
/* 53:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.util.MiningPlot
 * JD-Core Version:    0.7.1
 */