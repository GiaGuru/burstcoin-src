/*  1:   */ package nxt.at;
/*  2:   */ 
/*  3:   */ import java.math.BigInteger;
/*  4:   */ import java.nio.ByteBuffer;
/*  5:   */ import java.nio.ByteOrder;
/*  6:   */ 
/*  7:   */ public class AT_API_Helper
/*  8:   */ {
/*  9:   */   public static int longToHeight(long paramLong)
/* 10:   */   {
/* 11:19 */     ByteBuffer localByteBuffer = ByteBuffer.allocate(8);
/* 12:20 */     localByteBuffer.order(ByteOrder.LITTLE_ENDIAN);
/* 13:21 */     localByteBuffer.putLong(0, paramLong);
/* 14:22 */     return localByteBuffer.getInt(4);
/* 15:   */   }
/* 16:   */   
/* 17:   */   public static long getLong(byte[] paramArrayOfByte)
/* 18:   */   {
/* 19:26 */     ByteBuffer localByteBuffer = ByteBuffer.allocate(8);
/* 20:27 */     localByteBuffer.order(ByteOrder.LITTLE_ENDIAN);
/* 21:28 */     localByteBuffer.position(0);
/* 22:29 */     localByteBuffer.put(paramArrayOfByte);
/* 23:30 */     return localByteBuffer.getLong(0);
/* 24:   */   }
/* 25:   */   
/* 26:   */   public static byte[] getByteArray(long paramLong)
/* 27:   */   {
/* 28:34 */     ByteBuffer localByteBuffer = ByteBuffer.allocate(8);
/* 29:35 */     localByteBuffer.order(ByteOrder.LITTLE_ENDIAN);
/* 30:36 */     localByteBuffer.clear();
/* 31:37 */     localByteBuffer.putLong(paramLong);
/* 32:38 */     return localByteBuffer.array();
/* 33:   */   }
/* 34:   */   
/* 35:   */   public static int longToNumOfTx(long paramLong)
/* 36:   */   {
/* 37:42 */     ByteBuffer localByteBuffer = ByteBuffer.allocate(8);
/* 38:43 */     localByteBuffer.order(ByteOrder.LITTLE_ENDIAN);
/* 39:44 */     localByteBuffer.putLong(0, paramLong);
/* 40:45 */     return localByteBuffer.getInt(0);
/* 41:   */   }
/* 42:   */   
/* 43:   */   protected static long getLongTimestamp(int paramInt1, int paramInt2)
/* 44:   */   {
/* 45:49 */     ByteBuffer localByteBuffer = ByteBuffer.allocate(8);
/* 46:50 */     localByteBuffer.order(ByteOrder.LITTLE_ENDIAN);
/* 47:51 */     localByteBuffer.putInt(4, paramInt1);
/* 48:52 */     localByteBuffer.putInt(0, paramInt2);
/* 49:53 */     return localByteBuffer.getLong(0);
/* 50:   */   }
/* 51:   */   
/* 52:   */   public static BigInteger getBigInteger(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, byte[] paramArrayOfByte3, byte[] paramArrayOfByte4)
/* 53:   */   {
/* 54:57 */     ByteBuffer localByteBuffer = ByteBuffer.allocate(32);
/* 55:58 */     localByteBuffer.order(ByteOrder.LITTLE_ENDIAN);
/* 56:59 */     localByteBuffer.put(paramArrayOfByte1);
/* 57:60 */     localByteBuffer.put(paramArrayOfByte2);
/* 58:61 */     localByteBuffer.put(paramArrayOfByte3);
/* 59:62 */     localByteBuffer.put(paramArrayOfByte4);
/* 60:   */     
/* 61:64 */     byte[] arrayOfByte = localByteBuffer.array();
/* 62:   */     
/* 63:66 */     return new BigInteger(new byte[] { arrayOfByte[31], arrayOfByte[30], arrayOfByte[29], arrayOfByte[28], arrayOfByte[27], arrayOfByte[26], arrayOfByte[25], arrayOfByte[24], arrayOfByte[23], arrayOfByte[22], arrayOfByte[21], arrayOfByte[20], arrayOfByte[19], arrayOfByte[18], arrayOfByte[17], arrayOfByte[16], arrayOfByte[15], arrayOfByte[14], arrayOfByte[13], arrayOfByte[12], arrayOfByte[11], arrayOfByte[10], arrayOfByte[9], arrayOfByte[8], arrayOfByte[7], arrayOfByte[6], arrayOfByte[5], arrayOfByte[4], arrayOfByte[3], arrayOfByte[2], arrayOfByte[1], arrayOfByte[0] });
/* 64:   */   }
/* 65:   */   
/* 66:   */   public static byte[] getByteArray(BigInteger paramBigInteger)
/* 67:   */   {
/* 68:75 */     byte[] arrayOfByte1 = paramBigInteger.toByteArray();
/* 69:76 */     ByteBuffer localByteBuffer = ByteBuffer.allocate(32);
/* 70:77 */     byte b = 0;
/* 71:78 */     if (arrayOfByte1.length < 32)
/* 72:   */     {
/* 73:79 */       b = (byte)((byte)(arrayOfByte1[0] & 0xFFFFFF80) >> 7);
/* 74:80 */       for (int i = 0; i < 32 - arrayOfByte1.length; i++) {
/* 75:81 */         localByteBuffer.put(b);
/* 76:   */       }
/* 77:   */     }
/* 78:84 */     localByteBuffer.put(arrayOfByte1, 32 >= arrayOfByte1.length ? 0 : arrayOfByte1.length - 32, 32 > arrayOfByte1.length ? arrayOfByte1.length : 32);
/* 79:85 */     localByteBuffer.clear();
/* 80:86 */     byte[] arrayOfByte2 = localByteBuffer.array();
/* 81:87 */     return new byte[] { arrayOfByte2[31], arrayOfByte2[30], arrayOfByte2[29], arrayOfByte2[28], arrayOfByte2[27], arrayOfByte2[26], arrayOfByte2[25], arrayOfByte2[24], arrayOfByte2[23], arrayOfByte2[22], arrayOfByte2[21], arrayOfByte2[20], arrayOfByte2[19], arrayOfByte2[18], arrayOfByte2[17], arrayOfByte2[16], arrayOfByte2[15], arrayOfByte2[14], arrayOfByte2[13], arrayOfByte2[12], arrayOfByte2[11], arrayOfByte2[10], arrayOfByte2[9], arrayOfByte2[8], arrayOfByte2[7], arrayOfByte2[6], arrayOfByte2[5], arrayOfByte2[4], arrayOfByte2[3], arrayOfByte2[2], arrayOfByte2[1], arrayOfByte2[0] };
/* 82:   */   }
/* 83:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.at.AT_API_Helper
 * JD-Core Version:    0.7.1
 */