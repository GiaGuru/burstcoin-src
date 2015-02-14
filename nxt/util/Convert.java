/*   1:    */ package nxt.util;
/*   2:    */ 
/*   3:    */ import java.io.UnsupportedEncodingException;
/*   4:    */ import java.math.BigInteger;
/*   5:    */ import java.nio.ByteBuffer;
/*   6:    */ import java.util.Arrays;
/*   7:    */ import java.util.Date;
/*   8:    */ import nxt.Constants;
/*   9:    */ import nxt.NxtException.NotValidException;
/*  10:    */ import nxt.crypto.Crypto;
/*  11:    */ 
/*  12:    */ public final class Convert
/*  13:    */ {
/*  14: 15 */   private static final char[] hexChars = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
/*  15: 16 */   private static final long[] multipliers = { 1L, 10L, 100L, 1000L, 10000L, 100000L, 1000000L, 10000000L, 100000000L };
/*  16: 18 */   public static final BigInteger two64 = new BigInteger("18446744073709551616");
/*  17:    */   
/*  18:    */   public static byte[] parseHexString(String paramString)
/*  19:    */   {
/*  20: 23 */     if (paramString == null) {
/*  21: 24 */       return null;
/*  22:    */     }
/*  23: 26 */     byte[] arrayOfByte = new byte[paramString.length() / 2];
/*  24: 27 */     for (int i = 0; i < arrayOfByte.length; i++)
/*  25:    */     {
/*  26: 28 */       int j = paramString.charAt(i * 2);
/*  27: 29 */       j = j > 96 ? j - 87 : j - 48;
/*  28: 30 */       int k = paramString.charAt(i * 2 + 1);
/*  29: 31 */       k = k > 96 ? k - 87 : k - 48;
/*  30: 32 */       if ((j < 0) || (k < 0) || (j > 15) || (k > 15)) {
/*  31: 33 */         throw new NumberFormatException("Invalid hex number: " + paramString);
/*  32:    */       }
/*  33: 35 */       arrayOfByte[i] = ((byte)((j << 4) + k));
/*  34:    */     }
/*  35: 37 */     return arrayOfByte;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public static String toHexString(byte[] paramArrayOfByte)
/*  39:    */   {
/*  40: 41 */     if (paramArrayOfByte == null) {
/*  41: 42 */       return null;
/*  42:    */     }
/*  43: 44 */     char[] arrayOfChar = new char[paramArrayOfByte.length * 2];
/*  44: 45 */     for (int i = 0; i < paramArrayOfByte.length; i++)
/*  45:    */     {
/*  46: 46 */       arrayOfChar[(i * 2)] = hexChars[(paramArrayOfByte[i] >> 4 & 0xF)];
/*  47: 47 */       arrayOfChar[(i * 2 + 1)] = hexChars[(paramArrayOfByte[i] & 0xF)];
/*  48:    */     }
/*  49: 49 */     return String.valueOf(arrayOfChar);
/*  50:    */   }
/*  51:    */   
/*  52:    */   public static String toUnsignedLong(long paramLong)
/*  53:    */   {
/*  54: 53 */     if (paramLong >= 0L) {
/*  55: 54 */       return String.valueOf(paramLong);
/*  56:    */     }
/*  57: 56 */     BigInteger localBigInteger = BigInteger.valueOf(paramLong).add(two64);
/*  58: 57 */     return localBigInteger.toString();
/*  59:    */   }
/*  60:    */   
/*  61:    */   public static long parseUnsignedLong(String paramString)
/*  62:    */   {
/*  63: 61 */     if (paramString == null) {
/*  64: 62 */       return 0L;
/*  65:    */     }
/*  66: 64 */     BigInteger localBigInteger = new BigInteger(paramString.trim());
/*  67: 65 */     if ((localBigInteger.signum() < 0) || (localBigInteger.compareTo(two64) != -1)) {
/*  68: 66 */       throw new IllegalArgumentException("overflow: " + paramString);
/*  69:    */     }
/*  70: 68 */     return localBigInteger.longValue();
/*  71:    */   }
/*  72:    */   
/*  73:    */   public static long parseLong(Object paramObject)
/*  74:    */   {
/*  75: 72 */     if (paramObject == null) {
/*  76: 73 */       return 0L;
/*  77:    */     }
/*  78: 74 */     if ((paramObject instanceof Long)) {
/*  79: 75 */       return ((Long)paramObject).longValue();
/*  80:    */     }
/*  81: 76 */     if ((paramObject instanceof String)) {
/*  82: 77 */       return Long.parseLong((String)paramObject);
/*  83:    */     }
/*  84: 79 */     throw new IllegalArgumentException("Not a long: " + paramObject);
/*  85:    */   }
/*  86:    */   
/*  87:    */   public static long parseAccountId(String paramString)
/*  88:    */   {
/*  89: 84 */     if (paramString == null) {
/*  90: 85 */       return 0L;
/*  91:    */     }
/*  92: 87 */     paramString = paramString.toUpperCase();
/*  93: 88 */     if (paramString.startsWith("BURST-")) {
/*  94: 89 */       return Crypto.rsDecode(paramString.substring(6));
/*  95:    */     }
/*  96: 91 */     return parseUnsignedLong(paramString);
/*  97:    */   }
/*  98:    */   
/*  99:    */   public static String rsAccount(long paramLong)
/* 100:    */   {
/* 101: 96 */     return "BURST-" + Crypto.rsEncode(paramLong);
/* 102:    */   }
/* 103:    */   
/* 104:    */   public static long fullHashToId(byte[] paramArrayOfByte)
/* 105:    */   {
/* 106:100 */     if ((paramArrayOfByte == null) || (paramArrayOfByte.length < 8)) {
/* 107:101 */       throw new IllegalArgumentException("Invalid hash: " + Arrays.toString(paramArrayOfByte));
/* 108:    */     }
/* 109:103 */     BigInteger localBigInteger = new BigInteger(1, new byte[] { paramArrayOfByte[7], paramArrayOfByte[6], paramArrayOfByte[5], paramArrayOfByte[4], paramArrayOfByte[3], paramArrayOfByte[2], paramArrayOfByte[1], paramArrayOfByte[0] });
/* 110:104 */     return localBigInteger.longValue();
/* 111:    */   }
/* 112:    */   
/* 113:    */   public static long fullHashToId(String paramString)
/* 114:    */   {
/* 115:108 */     if (paramString == null) {
/* 116:109 */       return 0L;
/* 117:    */     }
/* 118:111 */     return fullHashToId(parseHexString(paramString));
/* 119:    */   }
/* 120:    */   
/* 121:    */   public static Date fromEpochTime(int paramInt)
/* 122:    */   {
/* 123:115 */     return new Date(paramInt * 1000L + Constants.EPOCH_BEGINNING - 500L);
/* 124:    */   }
/* 125:    */   
/* 126:    */   public static String emptyToNull(String paramString)
/* 127:    */   {
/* 128:119 */     return (paramString == null) || (paramString.length() == 0) ? null : paramString;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public static String nullToEmpty(String paramString)
/* 132:    */   {
/* 133:123 */     return paramString == null ? "" : paramString;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public static byte[] emptyToNull(byte[] paramArrayOfByte)
/* 137:    */   {
/* 138:127 */     if (paramArrayOfByte == null) {
/* 139:128 */       return null;
/* 140:    */     }
/* 141:130 */     for (int k : paramArrayOfByte) {
/* 142:131 */       if (k != 0) {
/* 143:132 */         return paramArrayOfByte;
/* 144:    */       }
/* 145:    */     }
/* 146:135 */     return null;
/* 147:    */   }
/* 148:    */   
/* 149:    */   public static byte[] toBytes(String paramString)
/* 150:    */   {
/* 151:    */     try
/* 152:    */     {
/* 153:140 */       return paramString.getBytes("UTF-8");
/* 154:    */     }
/* 155:    */     catch (UnsupportedEncodingException localUnsupportedEncodingException)
/* 156:    */     {
/* 157:142 */       throw new RuntimeException(localUnsupportedEncodingException.toString(), localUnsupportedEncodingException);
/* 158:    */     }
/* 159:    */   }
/* 160:    */   
/* 161:    */   public static String toString(byte[] paramArrayOfByte)
/* 162:    */   {
/* 163:    */     try
/* 164:    */     {
/* 165:148 */       return new String(paramArrayOfByte, "UTF-8").trim();
/* 166:    */     }
/* 167:    */     catch (UnsupportedEncodingException localUnsupportedEncodingException)
/* 168:    */     {
/* 169:150 */       throw new RuntimeException(localUnsupportedEncodingException.toString(), localUnsupportedEncodingException);
/* 170:    */     }
/* 171:    */   }
/* 172:    */   
/* 173:    */   public static String readString(ByteBuffer paramByteBuffer, int paramInt1, int paramInt2)
/* 174:    */     throws NxtException.NotValidException
/* 175:    */   {
/* 176:155 */     if (paramInt1 > 3 * paramInt2) {
/* 177:156 */       throw new NxtException.NotValidException("Max parameter length exceeded");
/* 178:    */     }
/* 179:158 */     byte[] arrayOfByte = new byte[paramInt1];
/* 180:159 */     paramByteBuffer.get(arrayOfByte);
/* 181:160 */     return toString(arrayOfByte);
/* 182:    */   }
/* 183:    */   
/* 184:    */   public static String truncate(String paramString1, String paramString2, int paramInt, boolean paramBoolean)
/* 185:    */   {
/* 186:164 */     return paramString1.length() > paramInt ? paramString1.substring(0, paramBoolean ? paramInt - 3 : paramInt) + (paramBoolean ? "..." : "") : paramString1 == null ? paramString2 : paramString1;
/* 187:    */   }
/* 188:    */   
/* 189:    */   public static long parseNXT(String paramString)
/* 190:    */   {
/* 191:168 */     return parseStringFraction(paramString, 8, 2158812800L);
/* 192:    */   }
/* 193:    */   
/* 194:    */   private static long parseStringFraction(String paramString, int paramInt, long paramLong)
/* 195:    */   {
/* 196:172 */     String[] arrayOfString = paramString.trim().split("\\.");
/* 197:173 */     if ((arrayOfString.length == 0) || (arrayOfString.length > 2)) {
/* 198:174 */       throw new NumberFormatException("Invalid number: " + paramString);
/* 199:    */     }
/* 200:176 */     long l1 = Long.parseLong(arrayOfString[0]);
/* 201:177 */     if (l1 > paramLong) {
/* 202:178 */       throw new IllegalArgumentException("Whole part of value exceeds maximum possible");
/* 203:    */     }
/* 204:180 */     if (arrayOfString.length == 1) {
/* 205:181 */       return l1 * multipliers[paramInt];
/* 206:    */     }
/* 207:183 */     long l2 = Long.parseLong(arrayOfString[1]);
/* 208:184 */     if ((l2 >= multipliers[paramInt]) || (arrayOfString[1].length() > paramInt)) {
/* 209:185 */       throw new IllegalArgumentException("Fractional part exceeds maximum allowed divisibility");
/* 210:    */     }
/* 211:187 */     for (int i = arrayOfString[1].length(); i < paramInt; i++) {
/* 212:188 */       l2 *= 10L;
/* 213:    */     }
/* 214:190 */     return l1 * multipliers[paramInt] + l2;
/* 215:    */   }
/* 216:    */   
/* 217:    */   public static long safeAdd(long paramLong1, long paramLong2)
/* 218:    */     throws ArithmeticException
/* 219:    */   {
/* 220:196 */     if (paramLong2 > 0L ? paramLong1 > Long.MAX_VALUE - paramLong2 : paramLong1 < Long.MIN_VALUE - paramLong2) {
/* 221:198 */       throw new ArithmeticException("Integer overflow");
/* 222:    */     }
/* 223:200 */     return paramLong1 + paramLong2;
/* 224:    */   }
/* 225:    */   
/* 226:    */   public static long safeSubtract(long paramLong1, long paramLong2)
/* 227:    */     throws ArithmeticException
/* 228:    */   {
/* 229:205 */     if (paramLong2 > 0L ? paramLong1 < Long.MIN_VALUE + paramLong2 : paramLong1 > Long.MAX_VALUE + paramLong2) {
/* 230:207 */       throw new ArithmeticException("Integer overflow");
/* 231:    */     }
/* 232:209 */     return paramLong1 - paramLong2;
/* 233:    */   }
/* 234:    */   
/* 235:    */   public static long safeMultiply(long paramLong1, long paramLong2)
/* 236:    */     throws ArithmeticException
/* 237:    */   {
/* 238:214 */     if (paramLong2 > 0L ? (paramLong1 > Long.MAX_VALUE / paramLong2) || (paramLong1 < Long.MIN_VALUE / paramLong2) : paramLong2 < -1L ? (paramLong1 > Long.MIN_VALUE / paramLong2) || (paramLong1 < Long.MAX_VALUE / paramLong2) : (paramLong2 == -1L) && (paramLong1 == Long.MIN_VALUE)) {
/* 239:220 */       throw new ArithmeticException("Integer overflow");
/* 240:    */     }
/* 241:222 */     return paramLong1 * paramLong2;
/* 242:    */   }
/* 243:    */   
/* 244:    */   public static long safeDivide(long paramLong1, long paramLong2)
/* 245:    */     throws ArithmeticException
/* 246:    */   {
/* 247:227 */     if ((paramLong1 == Long.MIN_VALUE) && (paramLong2 == -1L)) {
/* 248:228 */       throw new ArithmeticException("Integer overflow");
/* 249:    */     }
/* 250:230 */     return paramLong1 / paramLong2;
/* 251:    */   }
/* 252:    */   
/* 253:    */   public static long safeNegate(long paramLong)
/* 254:    */     throws ArithmeticException
/* 255:    */   {
/* 256:234 */     if (paramLong == Long.MIN_VALUE) {
/* 257:235 */       throw new ArithmeticException("Integer overflow");
/* 258:    */     }
/* 259:237 */     return -paramLong;
/* 260:    */   }
/* 261:    */   
/* 262:    */   public static long safeAbs(long paramLong)
/* 263:    */     throws ArithmeticException
/* 264:    */   {
/* 265:241 */     if (paramLong == Long.MIN_VALUE) {
/* 266:242 */       throw new ArithmeticException("Integer overflow");
/* 267:    */     }
/* 268:244 */     return Math.abs(paramLong);
/* 269:    */   }
/* 270:    */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.util.Convert
 * JD-Core Version:    0.7.1
 */