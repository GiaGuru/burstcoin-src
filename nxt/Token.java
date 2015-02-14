/*   1:    */ package nxt;
/*   2:    */ 
/*   3:    */ import nxt.crypto.Crypto;
/*   4:    */ import nxt.util.Convert;
/*   5:    */ 
/*   6:    */ public final class Token
/*   7:    */ {
/*   8:    */   private final byte[] publicKey;
/*   9:    */   private final int timestamp;
/*  10:    */   private final boolean isValid;
/*  11:    */   
/*  12:    */   public static String generateToken(String paramString1, String paramString2)
/*  13:    */   {
/*  14: 10 */     byte[] arrayOfByte1 = Convert.toBytes(paramString2);
/*  15: 11 */     byte[] arrayOfByte2 = new byte[arrayOfByte1.length + 32 + 4];
/*  16: 12 */     System.arraycopy(arrayOfByte1, 0, arrayOfByte2, 0, arrayOfByte1.length);
/*  17: 13 */     System.arraycopy(Crypto.getPublicKey(paramString1), 0, arrayOfByte2, arrayOfByte1.length, 32);
/*  18: 14 */     int i = Nxt.getEpochTime();
/*  19: 15 */     arrayOfByte2[(arrayOfByte1.length + 32)] = ((byte)i);
/*  20: 16 */     arrayOfByte2[(arrayOfByte1.length + 32 + 1)] = ((byte)(i >> 8));
/*  21: 17 */     arrayOfByte2[(arrayOfByte1.length + 32 + 2)] = ((byte)(i >> 16));
/*  22: 18 */     arrayOfByte2[(arrayOfByte1.length + 32 + 3)] = ((byte)(i >> 24));
/*  23:    */     
/*  24: 20 */     byte[] arrayOfByte3 = new byte[100];
/*  25: 21 */     System.arraycopy(arrayOfByte2, arrayOfByte1.length, arrayOfByte3, 0, 36);
/*  26: 22 */     System.arraycopy(Crypto.sign(arrayOfByte2, paramString1), 0, arrayOfByte3, 36, 64);
/*  27:    */     
/*  28: 24 */     StringBuilder localStringBuilder = new StringBuilder();
/*  29: 25 */     for (int j = 0; j < 100; j += 5)
/*  30:    */     {
/*  31: 27 */       long l = arrayOfByte3[j] & 0xFF | (arrayOfByte3[(j + 1)] & 0xFF) << 8 | (arrayOfByte3[(j + 2)] & 0xFF) << 16 | (arrayOfByte3[(j + 3)] & 0xFF) << 24 | (arrayOfByte3[(j + 4)] & 0xFF) << 32;
/*  32: 30 */       if (l < 32L) {
/*  33: 31 */         localStringBuilder.append("0000000");
/*  34: 32 */       } else if (l < 1024L) {
/*  35: 33 */         localStringBuilder.append("000000");
/*  36: 34 */       } else if (l < 32768L) {
/*  37: 35 */         localStringBuilder.append("00000");
/*  38: 36 */       } else if (l < 1048576L) {
/*  39: 37 */         localStringBuilder.append("0000");
/*  40: 38 */       } else if (l < 33554432L) {
/*  41: 39 */         localStringBuilder.append("000");
/*  42: 40 */       } else if (l < 1073741824L) {
/*  43: 41 */         localStringBuilder.append("00");
/*  44: 42 */       } else if (l < 34359738368L) {
/*  45: 43 */         localStringBuilder.append("0");
/*  46:    */       }
/*  47: 45 */       localStringBuilder.append(Long.toString(l, 32));
/*  48:    */     }
/*  49: 49 */     return localStringBuilder.toString();
/*  50:    */   }
/*  51:    */   
/*  52:    */   public static Token parseToken(String paramString1, String paramString2)
/*  53:    */   {
/*  54: 55 */     byte[] arrayOfByte1 = Convert.toBytes(paramString2);
/*  55: 56 */     byte[] arrayOfByte2 = new byte[100];
/*  56: 57 */     int i = 0;
/*  57: 57 */     for (int j = 0; i < paramString1.length(); j += 5)
/*  58:    */     {
/*  59: 61 */       long l = Long.parseLong(paramString1.substring(i, i + 8), 32);
/*  60: 62 */       arrayOfByte2[j] = ((byte)(int)l);
/*  61: 63 */       arrayOfByte2[(j + 1)] = ((byte)(int)(l >> 8));
/*  62: 64 */       arrayOfByte2[(j + 2)] = ((byte)(int)(l >> 16));
/*  63: 65 */       arrayOfByte2[(j + 3)] = ((byte)(int)(l >> 24));
/*  64: 66 */       arrayOfByte2[(j + 4)] = ((byte)(int)(l >> 32));i += 8;
/*  65:    */     }
/*  66: 70 */     if (i != 160) {
/*  67: 71 */       throw new IllegalArgumentException("Invalid token string: " + paramString1);
/*  68:    */     }
/*  69: 73 */     byte[] arrayOfByte3 = new byte[32];
/*  70: 74 */     System.arraycopy(arrayOfByte2, 0, arrayOfByte3, 0, 32);
/*  71: 75 */     int k = arrayOfByte2[32] & 0xFF | (arrayOfByte2[33] & 0xFF) << 8 | (arrayOfByte2[34] & 0xFF) << 16 | (arrayOfByte2[35] & 0xFF) << 24;
/*  72: 76 */     byte[] arrayOfByte4 = new byte[64];
/*  73: 77 */     System.arraycopy(arrayOfByte2, 36, arrayOfByte4, 0, 64);
/*  74:    */     
/*  75: 79 */     byte[] arrayOfByte5 = new byte[arrayOfByte1.length + 36];
/*  76: 80 */     System.arraycopy(arrayOfByte1, 0, arrayOfByte5, 0, arrayOfByte1.length);
/*  77: 81 */     System.arraycopy(arrayOfByte2, 0, arrayOfByte5, arrayOfByte1.length, 36);
/*  78: 82 */     boolean bool = Crypto.verify(arrayOfByte4, arrayOfByte5, arrayOfByte3, true);
/*  79:    */     
/*  80: 84 */     return new Token(arrayOfByte3, k, bool);
/*  81:    */   }
/*  82:    */   
/*  83:    */   private Token(byte[] paramArrayOfByte, int paramInt, boolean paramBoolean)
/*  84:    */   {
/*  85: 93 */     this.publicKey = paramArrayOfByte;
/*  86: 94 */     this.timestamp = paramInt;
/*  87: 95 */     this.isValid = paramBoolean;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public byte[] getPublicKey()
/*  91:    */   {
/*  92: 99 */     return this.publicKey;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public int getTimestamp()
/*  96:    */   {
/*  97:103 */     return this.timestamp;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public boolean isValid()
/* 101:    */   {
/* 102:107 */     return this.isValid;
/* 103:    */   }
/* 104:    */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.Token
 * JD-Core Version:    0.7.1
 */