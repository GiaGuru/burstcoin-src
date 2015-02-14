/*   1:    */ package nxt.peer;
/*   2:    */ 
/*   3:    */ import java.nio.ByteBuffer;
/*   4:    */ import java.nio.ByteOrder;
/*   5:    */ import java.util.concurrent.ThreadLocalRandom;
/*   6:    */ import nxt.Account;
/*   7:    */ import nxt.crypto.Crypto;
/*   8:    */ import nxt.util.Convert;
/*   9:    */ 
/*  10:    */ public final class Hallmark
/*  11:    */ {
/*  12:    */   private final String hallmarkString;
/*  13:    */   private final String host;
/*  14:    */   private final int weight;
/*  15:    */   private final int date;
/*  16:    */   private final byte[] publicKey;
/*  17:    */   private final long accountId;
/*  18:    */   private final byte[] signature;
/*  19:    */   private final boolean isValid;
/*  20:    */   
/*  21:    */   public static int parseDate(String paramString)
/*  22:    */   {
/*  23: 15 */     return Integer.parseInt(paramString.substring(0, 4)) * 10000 + Integer.parseInt(paramString.substring(5, 7)) * 100 + Integer.parseInt(paramString.substring(8, 10));
/*  24:    */   }
/*  25:    */   
/*  26:    */   public static String formatDate(int paramInt)
/*  27:    */   {
/*  28: 21 */     int i = paramInt / 10000;
/*  29: 22 */     int j = paramInt % 10000 / 100;
/*  30: 23 */     int k = paramInt % 100;
/*  31: 24 */     return (i < 1000 ? "0" : i < 100 ? "00" : i < 10 ? "000" : "") + i + "-" + (j < 10 ? "0" : "") + j + "-" + (k < 10 ? "0" : "") + k;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public static String generateHallmark(String paramString1, String paramString2, int paramInt1, int paramInt2)
/*  35:    */   {
/*  36: 29 */     if ((paramString2.length() == 0) || (paramString2.length() > 100)) {
/*  37: 30 */       throw new IllegalArgumentException("Hostname length should be between 1 and 100");
/*  38:    */     }
/*  39: 32 */     if ((paramInt1 <= 0) || (paramInt1 > 2158812800L)) {
/*  40: 33 */       throw new IllegalArgumentException("Weight should be between 1 and 2158812800");
/*  41:    */     }
/*  42: 36 */     byte[] arrayOfByte1 = Crypto.getPublicKey(paramString1);
/*  43: 37 */     byte[] arrayOfByte2 = Convert.toBytes(paramString2);
/*  44:    */     
/*  45: 39 */     ByteBuffer localByteBuffer = ByteBuffer.allocate(34 + arrayOfByte2.length + 4 + 4 + 1);
/*  46: 40 */     localByteBuffer.order(ByteOrder.LITTLE_ENDIAN);
/*  47: 41 */     localByteBuffer.put(arrayOfByte1);
/*  48: 42 */     localByteBuffer.putShort((short)arrayOfByte2.length);
/*  49: 43 */     localByteBuffer.put(arrayOfByte2);
/*  50: 44 */     localByteBuffer.putInt(paramInt1);
/*  51: 45 */     localByteBuffer.putInt(paramInt2);
/*  52:    */     
/*  53: 47 */     byte[] arrayOfByte3 = localByteBuffer.array();
/*  54: 48 */     arrayOfByte3[(arrayOfByte3.length - 1)] = ((byte)ThreadLocalRandom.current().nextInt());
/*  55: 49 */     byte[] arrayOfByte4 = Crypto.sign(arrayOfByte3, paramString1);
/*  56:    */     
/*  57: 51 */     return Convert.toHexString(arrayOfByte3) + Convert.toHexString(arrayOfByte4);
/*  58:    */   }
/*  59:    */   
/*  60:    */   public static Hallmark parseHallmark(String paramString)
/*  61:    */   {
/*  62: 57 */     byte[] arrayOfByte1 = Convert.parseHexString(paramString);
/*  63:    */     
/*  64: 59 */     ByteBuffer localByteBuffer = ByteBuffer.wrap(arrayOfByte1);
/*  65: 60 */     localByteBuffer.order(ByteOrder.LITTLE_ENDIAN);
/*  66:    */     
/*  67: 62 */     byte[] arrayOfByte2 = new byte[32];
/*  68: 63 */     localByteBuffer.get(arrayOfByte2);
/*  69: 64 */     int i = localByteBuffer.getShort();
/*  70: 65 */     if (i > 300) {
/*  71: 66 */       throw new IllegalArgumentException("Invalid host length");
/*  72:    */     }
/*  73: 68 */     byte[] arrayOfByte3 = new byte[i];
/*  74: 69 */     localByteBuffer.get(arrayOfByte3);
/*  75: 70 */     String str = Convert.toString(arrayOfByte3);
/*  76: 71 */     int j = localByteBuffer.getInt();
/*  77: 72 */     int k = localByteBuffer.getInt();
/*  78: 73 */     localByteBuffer.get();
/*  79: 74 */     byte[] arrayOfByte4 = new byte[64];
/*  80: 75 */     localByteBuffer.get(arrayOfByte4);
/*  81:    */     
/*  82: 77 */     byte[] arrayOfByte5 = new byte[arrayOfByte1.length - 64];
/*  83: 78 */     System.arraycopy(arrayOfByte1, 0, arrayOfByte5, 0, arrayOfByte5.length);
/*  84:    */     
/*  85: 80 */     boolean bool = (str.length() < 100) && (j > 0) && (j <= 2158812800L) && (Crypto.verify(arrayOfByte4, arrayOfByte5, arrayOfByte2, true));
/*  86:    */     
/*  87:    */ 
/*  88: 83 */     return new Hallmark(paramString, arrayOfByte2, arrayOfByte4, str, j, k, bool);
/*  89:    */   }
/*  90:    */   
/*  91:    */   private Hallmark(String paramString1, byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, String paramString2, int paramInt1, int paramInt2, boolean paramBoolean)
/*  92:    */   {
/*  93: 97 */     this.hallmarkString = paramString1;
/*  94: 98 */     this.host = paramString2;
/*  95: 99 */     this.publicKey = paramArrayOfByte1;
/*  96:100 */     this.accountId = Account.getId(paramArrayOfByte1);
/*  97:101 */     this.signature = paramArrayOfByte2;
/*  98:102 */     this.weight = paramInt1;
/*  99:103 */     this.date = paramInt2;
/* 100:104 */     this.isValid = paramBoolean;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public String getHallmarkString()
/* 104:    */   {
/* 105:108 */     return this.hallmarkString;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public String getHost()
/* 109:    */   {
/* 110:112 */     return this.host;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public int getWeight()
/* 114:    */   {
/* 115:116 */     return this.weight;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public int getDate()
/* 119:    */   {
/* 120:120 */     return this.date;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public byte[] getSignature()
/* 124:    */   {
/* 125:124 */     return this.signature;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public byte[] getPublicKey()
/* 129:    */   {
/* 130:128 */     return this.publicKey;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public long getAccountId()
/* 134:    */   {
/* 135:132 */     return this.accountId;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public boolean isValid()
/* 139:    */   {
/* 140:136 */     return this.isValid;
/* 141:    */   }
/* 142:    */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.peer.Hallmark
 * JD-Core Version:    0.7.1
 */