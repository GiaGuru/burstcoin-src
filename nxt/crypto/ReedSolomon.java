/*   1:    */ package nxt.crypto;
/*   2:    */ 
/*   3:    */ import java.math.BigInteger;
/*   4:    */ import nxt.util.Convert;
/*   5:    */ 
/*   6:    */ final class ReedSolomon
/*   7:    */ {
/*   8: 15 */   private static final int[] initial_codeword = { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
/*   9: 16 */   private static final int[] gexp = { 1, 2, 4, 8, 16, 5, 10, 20, 13, 26, 17, 7, 14, 28, 29, 31, 27, 19, 3, 6, 12, 24, 21, 15, 30, 25, 23, 11, 22, 9, 18, 1 };
/*  10: 17 */   private static final int[] glog = { 0, 0, 1, 18, 2, 5, 19, 11, 3, 29, 6, 27, 20, 8, 12, 23, 4, 10, 30, 17, 7, 22, 28, 26, 21, 25, 9, 16, 13, 14, 24, 15 };
/*  11: 18 */   private static final int[] codeword_map = { 3, 2, 1, 0, 7, 6, 5, 4, 13, 14, 15, 16, 12, 8, 9, 10, 11 };
/*  12:    */   private static final String alphabet = "23456789ABCDEFGHJKLMNPQRSTUVWXYZ";
/*  13:    */   private static final int base_32_length = 13;
/*  14:    */   private static final int base_10_length = 20;
/*  15:    */   
/*  16:    */   static String encode(long paramLong)
/*  17:    */   {
/*  18: 26 */     String str = Convert.toUnsignedLong(paramLong);
/*  19: 27 */     int i = str.length();
/*  20: 28 */     int[] arrayOfInt1 = new int[20];
/*  21: 29 */     for (int j = 0; j < i; j++) {
/*  22: 30 */       arrayOfInt1[j] = (str.charAt(j) - '0');
/*  23:    */     }
/*  24: 33 */     j = 0;
/*  25: 34 */     int[] arrayOfInt2 = new int[initial_codeword.length];
/*  26:    */     do
/*  27:    */     {
/*  28: 37 */       int k = 0;
/*  29: 38 */       m = 0;
/*  30: 39 */       for (n = 0; n < i; n++)
/*  31:    */       {
/*  32: 40 */         m = m * 10 + arrayOfInt1[n];
/*  33: 41 */         if (m >= 32)
/*  34:    */         {
/*  35: 42 */           arrayOfInt1[k] = (m >> 5);
/*  36: 43 */           m &= 0x1F;
/*  37: 44 */           k++;
/*  38:    */         }
/*  39: 45 */         else if (k > 0)
/*  40:    */         {
/*  41: 46 */           arrayOfInt1[k] = 0;
/*  42: 47 */           k++;
/*  43:    */         }
/*  44:    */       }
/*  45: 50 */       i = k;
/*  46: 51 */       arrayOfInt2[j] = m;
/*  47: 52 */       j++;
/*  48: 53 */     } while (i > 0);
/*  49: 55 */     int[] arrayOfInt3 = { 0, 0, 0, 0 };
/*  50: 56 */     for (int m = 12; m >= 0; m--)
/*  51:    */     {
/*  52: 57 */       n = arrayOfInt2[m] ^ arrayOfInt3[3];
/*  53: 58 */       arrayOfInt3[3] = (arrayOfInt3[2] ^ gmult(30, n));
/*  54: 59 */       arrayOfInt3[2] = (arrayOfInt3[1] ^ gmult(6, n));
/*  55: 60 */       arrayOfInt3[1] = (arrayOfInt3[0] ^ gmult(9, n));
/*  56: 61 */       arrayOfInt3[0] = gmult(17, n);
/*  57:    */     }
/*  58: 64 */     System.arraycopy(arrayOfInt3, 0, arrayOfInt2, 13, initial_codeword.length - 13);
/*  59:    */     
/*  60: 66 */     StringBuilder localStringBuilder = new StringBuilder();
/*  61: 67 */     for (int n = 0; n < 17; n++)
/*  62:    */     {
/*  63: 68 */       int i1 = codeword_map[n];
/*  64: 69 */       int i2 = arrayOfInt2[i1];
/*  65: 70 */       localStringBuilder.append("23456789ABCDEFGHJKLMNPQRSTUVWXYZ".charAt(i2));
/*  66: 72 */       if (((n & 0x3) == 3) && (n < 13)) {
/*  67: 73 */         localStringBuilder.append('-');
/*  68:    */       }
/*  69:    */     }
/*  70: 76 */     return localStringBuilder.toString();
/*  71:    */   }
/*  72:    */   
/*  73:    */   static long decode(String paramString)
/*  74:    */     throws ReedSolomon.DecodeException
/*  75:    */   {
/*  76: 81 */     int[] arrayOfInt1 = new int[initial_codeword.length];
/*  77: 82 */     System.arraycopy(initial_codeword, 0, arrayOfInt1, 0, initial_codeword.length);
/*  78:    */     
/*  79: 84 */     int i = 0;
/*  80: 85 */     for (int j = 0; j < paramString.length(); j++)
/*  81:    */     {
/*  82: 86 */       int k = "23456789ABCDEFGHJKLMNPQRSTUVWXYZ".indexOf(paramString.charAt(j));
/*  83: 88 */       if ((k > -1) && (k <= "23456789ABCDEFGHJKLMNPQRSTUVWXYZ".length()))
/*  84:    */       {
/*  85: 92 */         if (i > 16) {
/*  86: 93 */           throw new CodewordTooLongException();
/*  87:    */         }
/*  88: 96 */         m = codeword_map[i];
/*  89: 97 */         arrayOfInt1[m] = k;
/*  90: 98 */         i++;
/*  91:    */       }
/*  92:    */     }
/*  93:101 */     if (((i == 17) && (!is_codeword_valid(arrayOfInt1))) || (i != 17)) {
/*  94:102 */       throw new CodewordInvalidException();
/*  95:    */     }
/*  96:105 */     j = 13;
/*  97:106 */     int[] arrayOfInt2 = new int[j];
/*  98:107 */     for (int m = 0; m < j; m++) {
/*  99:108 */       arrayOfInt2[m] = arrayOfInt1[(j - m - 1)];
/* 100:    */     }
/* 101:111 */     StringBuilder localStringBuilder = new StringBuilder();
/* 102:    */     do
/* 103:    */     {
/* 104:113 */       int n = 0;
/* 105:114 */       int i1 = 0;
/* 106:116 */       for (int i2 = 0; i2 < j; i2++)
/* 107:    */       {
/* 108:117 */         i1 = i1 * 32 + arrayOfInt2[i2];
/* 109:119 */         if (i1 >= 10)
/* 110:    */         {
/* 111:120 */           arrayOfInt2[n] = (i1 / 10);
/* 112:121 */           i1 %= 10;
/* 113:122 */           n++;
/* 114:    */         }
/* 115:123 */         else if (n > 0)
/* 116:    */         {
/* 117:124 */           arrayOfInt2[n] = 0;
/* 118:125 */           n++;
/* 119:    */         }
/* 120:    */       }
/* 121:128 */       j = n;
/* 122:129 */       localStringBuilder.append((char)(i1 + 48));
/* 123:130 */     } while (j > 0);
/* 124:132 */     BigInteger localBigInteger = new BigInteger(localStringBuilder.reverse().toString());
/* 125:133 */     return localBigInteger.longValue();
/* 126:    */   }
/* 127:    */   
/* 128:    */   private static int gmult(int paramInt1, int paramInt2)
/* 129:    */   {
/* 130:137 */     if ((paramInt1 == 0) || (paramInt2 == 0)) {
/* 131:138 */       return 0;
/* 132:    */     }
/* 133:141 */     int i = (glog[paramInt1] + glog[paramInt2]) % 31;
/* 134:    */     
/* 135:143 */     return gexp[i];
/* 136:    */   }
/* 137:    */   
/* 138:    */   private static boolean is_codeword_valid(int[] paramArrayOfInt)
/* 139:    */   {
/* 140:147 */     int i = 0;
/* 141:149 */     for (int j = 1; j < 5; j++)
/* 142:    */     {
/* 143:150 */       int k = 0;
/* 144:152 */       for (int m = 0; m < 31; m++) {
/* 145:153 */         if ((m <= 12) || (m >= 27))
/* 146:    */         {
/* 147:157 */           int n = m;
/* 148:158 */           if (m > 26) {
/* 149:159 */             n -= 14;
/* 150:    */           }
/* 151:162 */           k ^= gmult(paramArrayOfInt[n], gexp[(j * m % 31)]);
/* 152:    */         }
/* 153:    */       }
/* 154:165 */       i |= k;
/* 155:    */     }
/* 156:168 */     return i == 0;
/* 157:    */   }
/* 158:    */   
/* 159:    */   static final class CodewordInvalidException
/* 160:    */     extends ReedSolomon.DecodeException
/* 161:    */   {}
/* 162:    */   
/* 163:    */   static final class CodewordTooLongException
/* 164:    */     extends ReedSolomon.DecodeException
/* 165:    */   {}
/* 166:    */   
/* 167:    */   static abstract class DecodeException
/* 168:    */     extends Exception
/* 169:    */   {}
/* 170:    */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.crypto.ReedSolomon
 * JD-Core Version:    0.7.1
 */