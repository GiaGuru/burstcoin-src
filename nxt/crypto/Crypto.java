/*   1:    */ package nxt.crypto;
/*   2:    */ 
/*   3:    */ import java.security.MessageDigest;
/*   4:    */ import java.security.NoSuchAlgorithmException;
/*   5:    */ import java.security.SecureRandom;
/*   6:    */ import java.util.Arrays;
/*   7:    */ import nxt.util.Convert;
/*   8:    */ import nxt.util.Logger;
/*   9:    */ import org.bouncycastle.crypto.InvalidCipherTextException;
/*  10:    */ import org.bouncycastle.crypto.engines.AESEngine;
/*  11:    */ import org.bouncycastle.crypto.modes.CBCBlockCipher;
/*  12:    */ import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
/*  13:    */ import org.bouncycastle.crypto.params.KeyParameter;
/*  14:    */ import org.bouncycastle.crypto.params.ParametersWithIV;
/*  15:    */ 
/*  16:    */ public final class Crypto
/*  17:    */ {
/*  18: 20 */   private static final ThreadLocal<SecureRandom> secureRandom = new ThreadLocal()
/*  19:    */   {
/*  20:    */     protected SecureRandom initialValue()
/*  21:    */     {
/*  22: 23 */       return new SecureRandom();
/*  23:    */     }
/*  24:    */   };
/*  25:    */   
/*  26:    */   public static MessageDigest getMessageDigest(String paramString)
/*  27:    */   {
/*  28:    */     try
/*  29:    */     {
/*  30: 31 */       return MessageDigest.getInstance(paramString);
/*  31:    */     }
/*  32:    */     catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
/*  33:    */     {
/*  34: 33 */       Logger.logMessage("Missing message digest algorithm: " + paramString);
/*  35: 34 */       throw new RuntimeException(localNoSuchAlgorithmException.getMessage(), localNoSuchAlgorithmException);
/*  36:    */     }
/*  37:    */   }
/*  38:    */   
/*  39:    */   public static MessageDigest sha256()
/*  40:    */   {
/*  41: 39 */     return getMessageDigest("SHA-256");
/*  42:    */   }
/*  43:    */   
/*  44:    */   public static byte[] getPublicKey(String paramString)
/*  45:    */   {
/*  46: 43 */     byte[] arrayOfByte = new byte[32];
/*  47: 44 */     Curve25519.keygen(arrayOfByte, null, sha256().digest(Convert.toBytes(paramString)));
/*  48:    */     
/*  49:    */ 
/*  50:    */ 
/*  51:    */ 
/*  52:    */ 
/*  53: 50 */     return arrayOfByte;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public static byte[] getPrivateKey(String paramString)
/*  57:    */   {
/*  58: 54 */     byte[] arrayOfByte = sha256().digest(Convert.toBytes(paramString));
/*  59: 55 */     Curve25519.clamp(arrayOfByte);
/*  60: 56 */     return arrayOfByte;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public static void curve(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, byte[] paramArrayOfByte3)
/*  64:    */   {
/*  65: 60 */     Curve25519.curve(paramArrayOfByte1, paramArrayOfByte2, paramArrayOfByte3);
/*  66:    */   }
/*  67:    */   
/*  68:    */   public static byte[] sign(byte[] paramArrayOfByte, String paramString)
/*  69:    */   {
/*  70: 65 */     byte[] arrayOfByte1 = new byte[32];
/*  71: 66 */     byte[] arrayOfByte2 = new byte[32];
/*  72: 67 */     MessageDigest localMessageDigest = sha256();
/*  73: 68 */     Curve25519.keygen(arrayOfByte1, arrayOfByte2, localMessageDigest.digest(Convert.toBytes(paramString)));
/*  74:    */     
/*  75: 70 */     byte[] arrayOfByte3 = localMessageDigest.digest(paramArrayOfByte);
/*  76:    */     
/*  77: 72 */     localMessageDigest.update(arrayOfByte3);
/*  78: 73 */     byte[] arrayOfByte4 = localMessageDigest.digest(arrayOfByte2);
/*  79:    */     
/*  80: 75 */     byte[] arrayOfByte5 = new byte[32];
/*  81: 76 */     Curve25519.keygen(arrayOfByte5, null, arrayOfByte4);
/*  82:    */     
/*  83: 78 */     localMessageDigest.update(arrayOfByte3);
/*  84: 79 */     byte[] arrayOfByte6 = localMessageDigest.digest(arrayOfByte5);
/*  85:    */     
/*  86: 81 */     byte[] arrayOfByte7 = new byte[32];
/*  87: 82 */     Curve25519.sign(arrayOfByte7, arrayOfByte6, arrayOfByte4, arrayOfByte2);
/*  88:    */     
/*  89: 84 */     byte[] arrayOfByte8 = new byte[64];
/*  90: 85 */     System.arraycopy(arrayOfByte7, 0, arrayOfByte8, 0, 32);
/*  91: 86 */     System.arraycopy(arrayOfByte6, 0, arrayOfByte8, 32, 32);
/*  92:    */     
/*  93:    */ 
/*  94:    */ 
/*  95:    */ 
/*  96:    */ 
/*  97:    */ 
/*  98: 93 */     return arrayOfByte8;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public static boolean verify(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, byte[] paramArrayOfByte3, boolean paramBoolean)
/* 102:    */   {
/* 103: 99 */     if ((paramBoolean) && (!Curve25519.isCanonicalSignature(paramArrayOfByte1)))
/* 104:    */     {
/* 105:100 */       Logger.logDebugMessage("Rejecting non-canonical signature");
/* 106:101 */       return false;
/* 107:    */     }
/* 108:104 */     if ((paramBoolean) && (!Curve25519.isCanonicalPublicKey(paramArrayOfByte3)))
/* 109:    */     {
/* 110:105 */       Logger.logDebugMessage("Rejecting non-canonical public key");
/* 111:106 */       return false;
/* 112:    */     }
/* 113:109 */     byte[] arrayOfByte1 = new byte[32];
/* 114:110 */     byte[] arrayOfByte2 = new byte[32];
/* 115:111 */     System.arraycopy(paramArrayOfByte1, 0, arrayOfByte2, 0, 32);
/* 116:112 */     byte[] arrayOfByte3 = new byte[32];
/* 117:113 */     System.arraycopy(paramArrayOfByte1, 32, arrayOfByte3, 0, 32);
/* 118:114 */     Curve25519.verify(arrayOfByte1, arrayOfByte2, arrayOfByte3, paramArrayOfByte3);
/* 119:    */     
/* 120:116 */     MessageDigest localMessageDigest = sha256();
/* 121:117 */     byte[] arrayOfByte4 = localMessageDigest.digest(paramArrayOfByte2);
/* 122:118 */     localMessageDigest.update(arrayOfByte4);
/* 123:119 */     byte[] arrayOfByte5 = localMessageDigest.digest(arrayOfByte1);
/* 124:    */     
/* 125:121 */     return Arrays.equals(arrayOfByte3, arrayOfByte5);
/* 126:    */   }
/* 127:    */   
/* 128:    */   public static byte[] aesEncrypt(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, byte[] paramArrayOfByte3)
/* 129:    */   {
/* 130:125 */     return aesEncrypt(paramArrayOfByte1, paramArrayOfByte2, paramArrayOfByte3, new byte[32]);
/* 131:    */   }
/* 132:    */   
/* 133:    */   public static byte[] aesEncrypt(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, byte[] paramArrayOfByte3, byte[] paramArrayOfByte4)
/* 134:    */   {
/* 135:    */     try
/* 136:    */     {
/* 137:130 */       byte[] arrayOfByte1 = new byte[32];
/* 138:131 */       Curve25519.curve(arrayOfByte1, paramArrayOfByte2, paramArrayOfByte3);
/* 139:132 */       for (int i = 0; i < 32; i++)
/* 140:    */       {
/* 141:133 */         int tmp27_25 = i; byte[] tmp27_23 = arrayOfByte1;tmp27_23[tmp27_25] = ((byte)(tmp27_23[tmp27_25] ^ paramArrayOfByte4[i]));
/* 142:    */       }
/* 143:135 */       byte[] arrayOfByte2 = sha256().digest(arrayOfByte1);
/* 144:136 */       byte[] arrayOfByte3 = new byte[16];
/* 145:137 */       ((SecureRandom)secureRandom.get()).nextBytes(arrayOfByte3);
/* 146:138 */       PaddedBufferedBlockCipher localPaddedBufferedBlockCipher = new PaddedBufferedBlockCipher(new CBCBlockCipher(new AESEngine()));
/* 147:    */       
/* 148:140 */       ParametersWithIV localParametersWithIV = new ParametersWithIV(new KeyParameter(arrayOfByte2), arrayOfByte3);
/* 149:141 */       localPaddedBufferedBlockCipher.init(true, localParametersWithIV);
/* 150:142 */       byte[] arrayOfByte4 = new byte[localPaddedBufferedBlockCipher.getOutputSize(paramArrayOfByte1.length)];
/* 151:143 */       int j = localPaddedBufferedBlockCipher.processBytes(paramArrayOfByte1, 0, paramArrayOfByte1.length, arrayOfByte4, 0);
/* 152:144 */       j += localPaddedBufferedBlockCipher.doFinal(arrayOfByte4, j);
/* 153:145 */       byte[] arrayOfByte5 = new byte[arrayOfByte3.length + j];
/* 154:146 */       System.arraycopy(arrayOfByte3, 0, arrayOfByte5, 0, arrayOfByte3.length);
/* 155:147 */       System.arraycopy(arrayOfByte4, 0, arrayOfByte5, arrayOfByte3.length, j);
/* 156:148 */       return arrayOfByte5;
/* 157:    */     }
/* 158:    */     catch (InvalidCipherTextException localInvalidCipherTextException)
/* 159:    */     {
/* 160:150 */       throw new RuntimeException(localInvalidCipherTextException.getMessage(), localInvalidCipherTextException);
/* 161:    */     }
/* 162:    */   }
/* 163:    */   
/* 164:    */   public static byte[] aesDecrypt(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, byte[] paramArrayOfByte3)
/* 165:    */   {
/* 166:174 */     return aesDecrypt(paramArrayOfByte1, paramArrayOfByte2, paramArrayOfByte3, new byte[32]);
/* 167:    */   }
/* 168:    */   
/* 169:    */   public static byte[] aesDecrypt(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, byte[] paramArrayOfByte3, byte[] paramArrayOfByte4)
/* 170:    */   {
/* 171:    */     try
/* 172:    */     {
/* 173:179 */       if ((paramArrayOfByte1.length < 16) || (paramArrayOfByte1.length % 16 != 0)) {
/* 174:180 */         throw new InvalidCipherTextException("invalid ciphertext");
/* 175:    */       }
/* 176:182 */       byte[] arrayOfByte1 = Arrays.copyOfRange(paramArrayOfByte1, 0, 16);
/* 177:183 */       byte[] arrayOfByte2 = Arrays.copyOfRange(paramArrayOfByte1, 16, paramArrayOfByte1.length);
/* 178:184 */       byte[] arrayOfByte3 = new byte[32];
/* 179:185 */       Curve25519.curve(arrayOfByte3, paramArrayOfByte2, paramArrayOfByte3);
/* 180:186 */       for (int i = 0; i < 32; i++)
/* 181:    */       {
/* 182:187 */         int tmp71_69 = i; byte[] tmp71_67 = arrayOfByte3;tmp71_67[tmp71_69] = ((byte)(tmp71_67[tmp71_69] ^ paramArrayOfByte4[i]));
/* 183:    */       }
/* 184:189 */       byte[] arrayOfByte4 = sha256().digest(arrayOfByte3);
/* 185:190 */       PaddedBufferedBlockCipher localPaddedBufferedBlockCipher = new PaddedBufferedBlockCipher(new CBCBlockCipher(new AESEngine()));
/* 186:    */       
/* 187:192 */       ParametersWithIV localParametersWithIV = new ParametersWithIV(new KeyParameter(arrayOfByte4), arrayOfByte1);
/* 188:193 */       localPaddedBufferedBlockCipher.init(false, localParametersWithIV);
/* 189:194 */       byte[] arrayOfByte5 = new byte[localPaddedBufferedBlockCipher.getOutputSize(arrayOfByte2.length)];
/* 190:195 */       int j = localPaddedBufferedBlockCipher.processBytes(arrayOfByte2, 0, arrayOfByte2.length, arrayOfByte5, 0);
/* 191:196 */       j += localPaddedBufferedBlockCipher.doFinal(arrayOfByte5, j);
/* 192:197 */       byte[] arrayOfByte6 = new byte[j];
/* 193:198 */       System.arraycopy(arrayOfByte5, 0, arrayOfByte6, 0, arrayOfByte6.length);
/* 194:199 */       return arrayOfByte6;
/* 195:    */     }
/* 196:    */     catch (InvalidCipherTextException localInvalidCipherTextException)
/* 197:    */     {
/* 198:201 */       throw new RuntimeException(localInvalidCipherTextException.getMessage(), localInvalidCipherTextException);
/* 199:    */     }
/* 200:    */   }
/* 201:    */   
/* 202:    */   private static void xorProcess(byte[] paramArrayOfByte1, int paramInt1, int paramInt2, byte[] paramArrayOfByte2, byte[] paramArrayOfByte3, byte[] paramArrayOfByte4)
/* 203:    */   {
/* 204:227 */     byte[] arrayOfByte1 = new byte[32];
/* 205:228 */     Curve25519.curve(arrayOfByte1, paramArrayOfByte2, paramArrayOfByte3);
/* 206:229 */     for (int i = 0; i < 32; i++)
/* 207:    */     {
/* 208:230 */       int tmp28_26 = i; byte[] tmp28_24 = arrayOfByte1;tmp28_24[tmp28_26] = ((byte)(tmp28_24[tmp28_26] ^ paramArrayOfByte4[i]));
/* 209:    */     }
/* 210:233 */     MessageDigest localMessageDigest = sha256();
/* 211:234 */     arrayOfByte1 = localMessageDigest.digest(arrayOfByte1);
/* 212:236 */     for (int j = 0; j < paramInt2 / 32; j++)
/* 213:    */     {
/* 214:237 */       byte[] arrayOfByte3 = localMessageDigest.digest(arrayOfByte1);
/* 215:238 */       for (int m = 0; m < 32; m++)
/* 216:    */       {
/* 217:239 */         int tmp94_91 = (paramInt1++);paramArrayOfByte1[tmp94_91] = ((byte)(paramArrayOfByte1[tmp94_91] ^ arrayOfByte3[m]));
/* 218:240 */         arrayOfByte1[m] = ((byte)(arrayOfByte1[m] ^ 0xFFFFFFFF));
/* 219:    */       }
/* 220:242 */       arrayOfByte1 = localMessageDigest.digest(arrayOfByte1);
/* 221:    */     }
/* 222:244 */     byte[] arrayOfByte2 = localMessageDigest.digest(arrayOfByte1);
/* 223:245 */     for (int k = 0; k < paramInt2 % 32; k++)
/* 224:    */     {
/* 225:246 */       int tmp164_161 = (paramInt1++);paramArrayOfByte1[tmp164_161] = ((byte)(paramArrayOfByte1[tmp164_161] ^ arrayOfByte2[k]));
/* 226:    */     }
/* 227:    */   }
/* 228:    */   
/* 229:    */   @Deprecated
/* 230:    */   public static byte[] xorEncrypt(byte[] paramArrayOfByte1, int paramInt1, int paramInt2, byte[] paramArrayOfByte2, byte[] paramArrayOfByte3)
/* 231:    */   {
/* 232:253 */     byte[] arrayOfByte = new byte[32];
/* 233:254 */     ((SecureRandom)secureRandom.get()).nextBytes(arrayOfByte);
/* 234:255 */     xorProcess(paramArrayOfByte1, paramInt1, paramInt2, paramArrayOfByte2, paramArrayOfByte3, arrayOfByte);
/* 235:256 */     return arrayOfByte;
/* 236:    */   }
/* 237:    */   
/* 238:    */   @Deprecated
/* 239:    */   public static void xorDecrypt(byte[] paramArrayOfByte1, int paramInt1, int paramInt2, byte[] paramArrayOfByte2, byte[] paramArrayOfByte3, byte[] paramArrayOfByte4)
/* 240:    */   {
/* 241:262 */     xorProcess(paramArrayOfByte1, paramInt1, paramInt2, paramArrayOfByte2, paramArrayOfByte3, paramArrayOfByte4);
/* 242:    */   }
/* 243:    */   
/* 244:    */   public static byte[] getSharedSecret(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
/* 245:    */   {
/* 246:    */     try
/* 247:    */     {
/* 248:267 */       byte[] arrayOfByte = new byte[32];
/* 249:268 */       Curve25519.curve(arrayOfByte, paramArrayOfByte1, paramArrayOfByte2);
/* 250:269 */       return arrayOfByte;
/* 251:    */     }
/* 252:    */     catch (RuntimeException localRuntimeException)
/* 253:    */     {
/* 254:271 */       Logger.logMessage("Error getting shared secret", localRuntimeException);
/* 255:272 */       throw localRuntimeException;
/* 256:    */     }
/* 257:    */   }
/* 258:    */   
/* 259:    */   public static String rsEncode(long paramLong)
/* 260:    */   {
/* 261:277 */     return ReedSolomon.encode(paramLong);
/* 262:    */   }
/* 263:    */   
/* 264:    */   public static long rsDecode(String paramString)
/* 265:    */   {
/* 266:281 */     paramString = paramString.toUpperCase();
/* 267:    */     try
/* 268:    */     {
/* 269:283 */       long l = ReedSolomon.decode(paramString);
/* 270:284 */       if (!paramString.equals(ReedSolomon.encode(l))) {
/* 271:285 */         throw new RuntimeException("ERROR: Reed-Solomon decoding of " + paramString + " not reversible, decoded to " + l);
/* 272:    */       }
/* 273:288 */       return l;
/* 274:    */     }
/* 275:    */     catch (ReedSolomon.DecodeException localDecodeException)
/* 276:    */     {
/* 277:290 */       Logger.logDebugMessage("Reed-Solomon decoding failed for " + paramString + ": " + localDecodeException.toString());
/* 278:291 */       throw new RuntimeException(localDecodeException.toString(), localDecodeException);
/* 279:    */     }
/* 280:    */   }
/* 281:    */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.crypto.Crypto
 * JD-Core Version:    0.7.1
 */