/*  1:   */ package nxt.crypto;
/*  2:   */ 
/*  3:   */ import java.nio.ByteBuffer;
/*  4:   */ import java.security.SecureRandom;
/*  5:   */ import nxt.NxtException.NotValidException;
/*  6:   */ 
/*  7:   */ public final class EncryptedData
/*  8:   */ {
/*  9:15 */   private static final ThreadLocal<SecureRandom> secureRandom = new ThreadLocal()
/* 10:   */   {
/* 11:   */     protected SecureRandom initialValue()
/* 12:   */     {
/* 13:18 */       return new SecureRandom();
/* 14:   */     }
/* 15:   */   };
/* 16:22 */   public static final EncryptedData EMPTY_DATA = new EncryptedData(new byte[0], new byte[0]);
/* 17:   */   private final byte[] data;
/* 18:   */   private final byte[] nonce;
/* 19:   */   
/* 20:   */   /* Error */
/* 21:   */   public static EncryptedData encrypt(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, byte[] paramArrayOfByte3)
/* 22:   */   {
/* 23:   */     // Byte code:
/* 24:   */     //   0: aload_0
/* 25:   */     //   1: arraylength
/* 26:   */     //   2: ifne +7 -> 9
/* 27:   */     //   5: getstatic 1	nxt/crypto/EncryptedData:EMPTY_DATA	Lnxt/crypto/EncryptedData;
/* 28:   */     //   8: areturn
/* 29:   */     //   9: new 2	java/io/ByteArrayOutputStream
/* 30:   */     //   12: dup
/* 31:   */     //   13: invokespecial 3	java/io/ByteArrayOutputStream:<init>	()V
/* 32:   */     //   16: astore_3
/* 33:   */     //   17: aconst_null
/* 34:   */     //   18: astore 4
/* 35:   */     //   20: new 4	java/util/zip/GZIPOutputStream
/* 36:   */     //   23: dup
/* 37:   */     //   24: aload_3
/* 38:   */     //   25: invokespecial 5	java/util/zip/GZIPOutputStream:<init>	(Ljava/io/OutputStream;)V
/* 39:   */     //   28: astore 5
/* 40:   */     //   30: aconst_null
/* 41:   */     //   31: astore 6
/* 42:   */     //   33: aload 5
/* 43:   */     //   35: aload_0
/* 44:   */     //   36: invokevirtual 6	java/util/zip/GZIPOutputStream:write	([B)V
/* 45:   */     //   39: aload 5
/* 46:   */     //   41: invokevirtual 7	java/util/zip/GZIPOutputStream:flush	()V
/* 47:   */     //   44: aload 5
/* 48:   */     //   46: invokevirtual 8	java/util/zip/GZIPOutputStream:close	()V
/* 49:   */     //   49: aload_3
/* 50:   */     //   50: invokevirtual 9	java/io/ByteArrayOutputStream:toByteArray	()[B
/* 51:   */     //   53: astore 7
/* 52:   */     //   55: bipush 32
/* 53:   */     //   57: newarray 慢⁤污潬慣楴湯
/* 54:   */     //   60: iconst_5
/* 55:   */     //   61: getstatic 10	nxt/crypto/EncryptedData:secureRandom	Ljava/lang/ThreadLocal;
/* 56:   */     //   64: invokevirtual 11	java/lang/ThreadLocal:get	()Ljava/lang/Object;
/* 57:   */     //   67: checkcast 12	java/security/SecureRandom
/* 58:   */     //   70: aload 8
/* 59:   */     //   72: invokevirtual 13	java/security/SecureRandom:nextBytes	([B)V
/* 60:   */     //   75: aload 7
/* 61:   */     //   77: aload_1
/* 62:   */     //   78: aload_2
/* 63:   */     //   79: aload 8
/* 64:   */     //   81: invokestatic 14	nxt/crypto/Crypto:aesEncrypt	([B[B[B[B)[B
/* 65:   */     //   84: astore 9
/* 66:   */     //   86: new 15	nxt/crypto/EncryptedData
/* 67:   */     //   89: dup
/* 68:   */     //   90: aload 9
/* 69:   */     //   92: aload 8
/* 70:   */     //   94: invokespecial 16	nxt/crypto/EncryptedData:<init>	([B[B)V
/* 71:   */     //   97: astore 10
/* 72:   */     //   99: aload 5
/* 73:   */     //   101: ifnull +33 -> 134
/* 74:   */     //   104: aload 6
/* 75:   */     //   106: ifnull +23 -> 129
/* 76:   */     //   109: aload 5
/* 77:   */     //   111: invokevirtual 8	java/util/zip/GZIPOutputStream:close	()V
/* 78:   */     //   114: goto +20 -> 134
/* 79:   */     //   117: astore 11
/* 80:   */     //   119: aload 6
/* 81:   */     //   121: aload 11
/* 82:   */     //   123: invokevirtual 18	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 83:   */     //   126: goto +8 -> 134
/* 84:   */     //   129: aload 5
/* 85:   */     //   131: invokevirtual 8	java/util/zip/GZIPOutputStream:close	()V
/* 86:   */     //   134: aload_3
/* 87:   */     //   135: ifnull +31 -> 166
/* 88:   */     //   138: aload 4
/* 89:   */     //   140: ifnull +22 -> 162
/* 90:   */     //   143: aload_3
/* 91:   */     //   144: invokevirtual 19	java/io/ByteArrayOutputStream:close	()V
/* 92:   */     //   147: goto +19 -> 166
/* 93:   */     //   150: astore 11
/* 94:   */     //   152: aload 4
/* 95:   */     //   154: aload 11
/* 96:   */     //   156: invokevirtual 18	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 97:   */     //   159: goto +7 -> 166
/* 98:   */     //   162: aload_3
/* 99:   */     //   163: invokevirtual 19	java/io/ByteArrayOutputStream:close	()V
/* :0:   */     //   166: aload 10
/* :1:   */     //   168: areturn
/* :2:   */     //   169: astore 7
/* :3:   */     //   171: aload 7
/* :4:   */     //   173: astore 6
/* :5:   */     //   175: aload 7
/* :6:   */     //   177: athrow
/* :7:   */     //   178: astore 12
/* :8:   */     //   180: aload 5
/* :9:   */     //   182: ifnull +33 -> 215
/* ;0:   */     //   185: aload 6
/* ;1:   */     //   187: ifnull +23 -> 210
/* ;2:   */     //   190: aload 5
/* ;3:   */     //   192: invokevirtual 8	java/util/zip/GZIPOutputStream:close	()V
/* ;4:   */     //   195: goto +20 -> 215
/* ;5:   */     //   198: astore 13
/* ;6:   */     //   200: aload 6
/* ;7:   */     //   202: aload 13
/* ;8:   */     //   204: invokevirtual 18	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* ;9:   */     //   207: goto +8 -> 215
/* <0:   */     //   210: aload 5
/* <1:   */     //   212: invokevirtual 8	java/util/zip/GZIPOutputStream:close	()V
/* <2:   */     //   215: aload 12
/* <3:   */     //   217: athrow
/* <4:   */     //   218: astore 5
/* <5:   */     //   220: aload 5
/* <6:   */     //   222: astore 4
/* <7:   */     //   224: aload 5
/* <8:   */     //   226: athrow
/* <9:   */     //   227: astore 14
/* =0:   */     //   229: aload_3
/* =1:   */     //   230: ifnull +31 -> 261
/* =2:   */     //   233: aload 4
/* =3:   */     //   235: ifnull +22 -> 257
/* =4:   */     //   238: aload_3
/* =5:   */     //   239: invokevirtual 19	java/io/ByteArrayOutputStream:close	()V
/* =6:   */     //   242: goto +19 -> 261
/* =7:   */     //   245: astore 15
/* =8:   */     //   247: aload 4
/* =9:   */     //   249: aload 15
/* >0:   */     //   251: invokevirtual 18	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* >1:   */     //   254: goto +7 -> 261
/* >2:   */     //   257: aload_3
/* >3:   */     //   258: invokevirtual 19	java/io/ByteArrayOutputStream:close	()V
/* >4:   */     //   261: aload 14
/* >5:   */     //   263: athrow
/* >6:   */     //   264: astore_3
/* >7:   */     //   265: new 21	java/lang/RuntimeException
/* >8:   */     //   268: dup
/* >9:   */     //   269: aload_3
/* ?0:   */     //   270: invokevirtual 22	java/io/IOException:getMessage	()Ljava/lang/String;
/* ?1:   */     //   273: aload_3
/* ?2:   */     //   274: invokespecial 23	java/lang/RuntimeException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
/* ?3:   */     //   277: athrow
/* ?4:   */     // Line number table:
/* ?5:   */     //   Java source line #25	-> byte code offset #0
/* ?6:   */     //   Java source line #26	-> byte code offset #5
/* ?7:   */     //   Java source line #28	-> byte code offset #9
/* ?8:   */     //   Java source line #29	-> byte code offset #20
/* ?9:   */     //   Java source line #28	-> byte code offset #30
/* @0:   */     //   Java source line #30	-> byte code offset #33
/* @1:   */     //   Java source line #31	-> byte code offset #39
/* @2:   */     //   Java source line #32	-> byte code offset #44
/* @3:   */     //   Java source line #33	-> byte code offset #49
/* @4:   */     //   Java source line #34	-> byte code offset #55
/* @5:   */     //   Java source line #35	-> byte code offset #61
/* @6:   */     //   Java source line #36	-> byte code offset #75
/* @7:   */     //   Java source line #37	-> byte code offset #86
/* @8:   */     //   Java source line #38	-> byte code offset #99
/* @9:   */     //   Java source line #28	-> byte code offset #169
/* A0:   */     //   Java source line #38	-> byte code offset #178
/* A1:   */     //   Java source line #28	-> byte code offset #218
/* A2:   */     //   Java source line #38	-> byte code offset #227
/* A3:   */     //   Java source line #39	-> byte code offset #265
/* A4:   */     // Local variable table:
/* A5:   */     //   start	length	slot	name	signature
/* A6:   */     //   0	278	0	paramArrayOfByte1	byte[]
/* A7:   */     //   0	278	1	paramArrayOfByte2	byte[]
/* A8:   */     //   0	278	2	paramArrayOfByte3	byte[]
/* A9:   */     //   16	242	3	localByteArrayOutputStream	java.io.ByteArrayOutputStream
/* B0:   */     //   264	10	3	localIOException	java.io.IOException
/* B1:   */     //   18	230	4	localObject1	Object
/* B2:   */     //   28	183	5	localGZIPOutputStream	java.util.zip.GZIPOutputStream
/* B3:   */     //   218	7	5	localThrowable1	Throwable
/* B4:   */     //   31	170	6	localObject2	Object
/* B5:   */     //   53	23	7	arrayOfByte1	byte[]
/* B6:   */     //   169	7	7	localThrowable2	Throwable
/* B7:   */     //   59	34	8	arrayOfByte2	byte[]
/* B8:   */     //   84	7	9	arrayOfByte3	byte[]
/* B9:   */     //   117	5	11	localThrowable3	Throwable
/* C0:   */     //   150	5	11	localThrowable4	Throwable
/* C1:   */     //   178	38	12	localObject3	Object
/* C2:   */     //   198	5	13	localThrowable5	Throwable
/* C3:   */     //   227	35	14	localObject4	Object
/* C4:   */     //   245	5	15	localThrowable6	Throwable
/* C5:   */     // Exception table:
/* C6:   */     //   from	to	target	type
/* C7:   */     //   109	114	117	java/lang/Throwable
/* C8:   */     //   143	147	150	java/lang/Throwable
/* C9:   */     //   33	99	169	java/lang/Throwable
/* D0:   */     //   33	99	178	finally
/* D1:   */     //   169	180	178	finally
/* D2:   */     //   190	195	198	java/lang/Throwable
/* D3:   */     //   20	134	218	java/lang/Throwable
/* D4:   */     //   169	218	218	java/lang/Throwable
/* D5:   */     //   20	134	227	finally
/* D6:   */     //   169	229	227	finally
/* D7:   */     //   238	242	245	java/lang/Throwable
/* D8:   */     //   9	166	264	java/io/IOException
/* D9:   */     //   169	264	264	java/io/IOException
/* E0:   */   }
/* E1:   */   
/* E2:   */   public static EncryptedData readEncryptedData(ByteBuffer paramByteBuffer, int paramInt1, int paramInt2)
/* E3:   */     throws NxtException.NotValidException
/* E4:   */   {
/* E5:45 */     if (paramInt1 == 0) {
/* E6:46 */       return EMPTY_DATA;
/* E7:   */     }
/* E8:48 */     if (paramInt1 > paramInt2) {
/* E9:49 */       throw new NxtException.NotValidException("Max encrypted data length exceeded: " + paramInt1);
/* F0:   */     }
/* F1:51 */     byte[] arrayOfByte1 = new byte[paramInt1];
/* F2:52 */     paramByteBuffer.get(arrayOfByte1);
/* F3:53 */     byte[] arrayOfByte2 = new byte[32];
/* F4:54 */     paramByteBuffer.get(arrayOfByte2);
/* F5:55 */     return new EncryptedData(arrayOfByte1, arrayOfByte2);
/* F6:   */   }
/* F7:   */   
/* F8:   */   public EncryptedData(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
/* F9:   */   {
/* G0:62 */     this.data = paramArrayOfByte1;
/* G1:63 */     this.nonce = paramArrayOfByte2;
/* G2:   */   }
/* G3:   */   
/* G4:   */   /* Error */
/* G5:   */   public byte[] decrypt(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
/* G6:   */   {
/* G7:   */     // Byte code:
/* G8:   */     //   0: aload_0
/* G9:   */     //   1: getfield 34	nxt/crypto/EncryptedData:data	[B
/* H0:   */     //   4: arraylength
/* H1:   */     //   5: ifne +8 -> 13
/* H2:   */     //   8: aload_0
/* H3:   */     //   9: getfield 34	nxt/crypto/EncryptedData:data	[B
/* H4:   */     //   12: areturn
/* H5:   */     //   13: aload_0
/* H6:   */     //   14: getfield 34	nxt/crypto/EncryptedData:data	[B
/* H7:   */     //   17: aload_1
/* H8:   */     //   18: aload_2
/* H9:   */     //   19: aload_0
/* I0:   */     //   20: getfield 35	nxt/crypto/EncryptedData:nonce	[B
/* I1:   */     //   23: invokestatic 36	nxt/crypto/Crypto:aesDecrypt	([B[B[B[B)[B
/* I2:   */     //   26: astore_3
/* I3:   */     //   27: new 37	java/io/ByteArrayInputStream
/* I4:   */     //   30: dup
/* I5:   */     //   31: aload_3
/* I6:   */     //   32: invokespecial 38	java/io/ByteArrayInputStream:<init>	([B)V
/* I7:   */     //   35: astore 4
/* I8:   */     //   37: aconst_null
/* I9:   */     //   38: astore 5
/* J0:   */     //   40: new 39	java/util/zip/GZIPInputStream
/* J1:   */     //   43: dup
/* J2:   */     //   44: aload 4
/* J3:   */     //   46: invokespecial 40	java/util/zip/GZIPInputStream:<init>	(Ljava/io/InputStream;)V
/* J4:   */     //   49: astore 6
/* J5:   */     //   51: aconst_null
/* J6:   */     //   52: astore 7
/* J7:   */     //   54: new 2	java/io/ByteArrayOutputStream
/* J8:   */     //   57: dup
/* J9:   */     //   58: invokespecial 3	java/io/ByteArrayOutputStream:<init>	()V
/* K0:   */     //   61: astore 8
/* K1:   */     //   63: aconst_null
/* K2:   */     //   64: astore 9
/* K3:   */     //   66: sipush 1024
/* K4:   */     //   69: newarray 慢⁤污潬慣楴湯
/* K5:   */     //   72: lconst_1
/* K6:   */     //   73: aload 6
/* K7:   */     //   75: aload 10
/* K8:   */     //   77: iconst_0
/* K9:   */     //   78: aload 10
/* L0:   */     //   80: arraylength
/* L1:   */     //   81: invokevirtual 41	java/util/zip/GZIPInputStream:read	([BII)I
/* L2:   */     //   84: dup
/* L3:   */     //   85: istore 11
/* L4:   */     //   87: ifle +16 -> 103
/* L5:   */     //   90: aload 8
/* L6:   */     //   92: aload 10
/* L7:   */     //   94: iconst_0
/* L8:   */     //   95: iload 11
/* L9:   */     //   97: invokevirtual 42	java/io/ByteArrayOutputStream:write	([BII)V
/* M0:   */     //   100: goto -27 -> 73
/* M1:   */     //   103: aload 8
/* M2:   */     //   105: invokevirtual 43	java/io/ByteArrayOutputStream:flush	()V
/* M3:   */     //   108: aload 8
/* M4:   */     //   110: invokevirtual 9	java/io/ByteArrayOutputStream:toByteArray	()[B
/* M5:   */     //   113: astore 12
/* M6:   */     //   115: aload 8
/* M7:   */     //   117: ifnull +33 -> 150
/* M8:   */     //   120: aload 9
/* M9:   */     //   122: ifnull +23 -> 145
/* N0:   */     //   125: aload 8
/* N1:   */     //   127: invokevirtual 19	java/io/ByteArrayOutputStream:close	()V
/* N2:   */     //   130: goto +20 -> 150
/* N3:   */     //   133: astore 13
/* N4:   */     //   135: aload 9
/* N5:   */     //   137: aload 13
/* N6:   */     //   139: invokevirtual 18	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* N7:   */     //   142: goto +8 -> 150
/* N8:   */     //   145: aload 8
/* N9:   */     //   147: invokevirtual 19	java/io/ByteArrayOutputStream:close	()V
/* O0:   */     //   150: aload 6
/* O1:   */     //   152: ifnull +33 -> 185
/* O2:   */     //   155: aload 7
/* O3:   */     //   157: ifnull +23 -> 180
/* O4:   */     //   160: aload 6
/* O5:   */     //   162: invokevirtual 44	java/util/zip/GZIPInputStream:close	()V
/* O6:   */     //   165: goto +20 -> 185
/* O7:   */     //   168: astore 13
/* O8:   */     //   170: aload 7
/* O9:   */     //   172: aload 13
/* P0:   */     //   174: invokevirtual 18	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* P1:   */     //   177: goto +8 -> 185
/* P2:   */     //   180: aload 6
/* P3:   */     //   182: invokevirtual 44	java/util/zip/GZIPInputStream:close	()V
/* P4:   */     //   185: aload 4
/* P5:   */     //   187: ifnull +33 -> 220
/* P6:   */     //   190: aload 5
/* P7:   */     //   192: ifnull +23 -> 215
/* P8:   */     //   195: aload 4
/* P9:   */     //   197: invokevirtual 45	java/io/ByteArrayInputStream:close	()V
/* Q0:   */     //   200: goto +20 -> 220
/* Q1:   */     //   203: astore 13
/* Q2:   */     //   205: aload 5
/* Q3:   */     //   207: aload 13
/* Q4:   */     //   209: invokevirtual 18	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* Q5:   */     //   212: goto +8 -> 220
/* Q6:   */     //   215: aload 4
/* Q7:   */     //   217: invokevirtual 45	java/io/ByteArrayInputStream:close	()V
/* Q8:   */     //   220: aload 12
/* Q9:   */     //   222: areturn
/* R0:   */     //   223: astore 10
/* R1:   */     //   225: aload 10
/* R2:   */     //   227: astore 9
/* R3:   */     //   229: aload 10
/* R4:   */     //   231: athrow
/* R5:   */     //   232: astore 14
/* R6:   */     //   234: aload 8
/* R7:   */     //   236: ifnull +33 -> 269
/* R8:   */     //   239: aload 9
/* R9:   */     //   241: ifnull +23 -> 264
/* S0:   */     //   244: aload 8
/* S1:   */     //   246: invokevirtual 19	java/io/ByteArrayOutputStream:close	()V
/* S2:   */     //   249: goto +20 -> 269
/* S3:   */     //   252: astore 15
/* S4:   */     //   254: aload 9
/* S5:   */     //   256: aload 15
/* S6:   */     //   258: invokevirtual 18	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* S7:   */     //   261: goto +8 -> 269
/* S8:   */     //   264: aload 8
/* S9:   */     //   266: invokevirtual 19	java/io/ByteArrayOutputStream:close	()V
/* T0:   */     //   269: aload 14
/* T1:   */     //   271: athrow
/* T2:   */     //   272: astore 8
/* T3:   */     //   274: aload 8
/* T4:   */     //   276: astore 7
/* T5:   */     //   278: aload 8
/* T6:   */     //   280: athrow
/* T7:   */     //   281: astore 16
/* T8:   */     //   283: aload 6
/* T9:   */     //   285: ifnull +33 -> 318
/* U0:   */     //   288: aload 7
/* U1:   */     //   290: ifnull +23 -> 313
/* U2:   */     //   293: aload 6
/* U3:   */     //   295: invokevirtual 44	java/util/zip/GZIPInputStream:close	()V
/* U4:   */     //   298: goto +20 -> 318
/* U5:   */     //   301: astore 17
/* U6:   */     //   303: aload 7
/* U7:   */     //   305: aload 17
/* U8:   */     //   307: invokevirtual 18	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* U9:   */     //   310: goto +8 -> 318
/* V0:   */     //   313: aload 6
/* V1:   */     //   315: invokevirtual 44	java/util/zip/GZIPInputStream:close	()V
/* V2:   */     //   318: aload 16
/* V3:   */     //   320: athrow
/* V4:   */     //   321: astore 6
/* V5:   */     //   323: aload 6
/* V6:   */     //   325: astore 5
/* V7:   */     //   327: aload 6
/* V8:   */     //   329: athrow
/* V9:   */     //   330: astore 18
/* W0:   */     //   332: aload 4
/* W1:   */     //   334: ifnull +33 -> 367
/* W2:   */     //   337: aload 5
/* W3:   */     //   339: ifnull +23 -> 362
/* W4:   */     //   342: aload 4
/* W5:   */     //   344: invokevirtual 45	java/io/ByteArrayInputStream:close	()V
/* W6:   */     //   347: goto +20 -> 367
/* W7:   */     //   350: astore 19
/* W8:   */     //   352: aload 5
/* W9:   */     //   354: aload 19
/* X0:   */     //   356: invokevirtual 18	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* X1:   */     //   359: goto +8 -> 367
/* X2:   */     //   362: aload 4
/* X3:   */     //   364: invokevirtual 45	java/io/ByteArrayInputStream:close	()V
/* X4:   */     //   367: aload 18
/* X5:   */     //   369: athrow
/* X6:   */     //   370: astore 4
/* X7:   */     //   372: new 21	java/lang/RuntimeException
/* X8:   */     //   375: dup
/* X9:   */     //   376: aload 4
/* Y0:   */     //   378: invokevirtual 22	java/io/IOException:getMessage	()Ljava/lang/String;
/* Y1:   */     //   381: aload 4
/* Y2:   */     //   383: invokespecial 23	java/lang/RuntimeException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
/* Y3:   */     //   386: athrow
/* Y4:   */     // Line number table:
/* Y5:   */     //   Java source line #67	-> byte code offset #0
/* Y6:   */     //   Java source line #68	-> byte code offset #8
/* Y7:   */     //   Java source line #70	-> byte code offset #13
/* Y8:   */     //   Java source line #71	-> byte code offset #27
/* Y9:   */     //   Java source line #72	-> byte code offset #40
/* Z0:   */     //   Java source line #71	-> byte code offset #51
/* Z1:   */     //   Java source line #73	-> byte code offset #54
/* Z2:   */     //   Java source line #71	-> byte code offset #63
/* Z3:   */     //   Java source line #74	-> byte code offset #66
/* Z4:   */     //   Java source line #76	-> byte code offset #73
/* Z5:   */     //   Java source line #77	-> byte code offset #90
/* Z6:   */     //   Java source line #79	-> byte code offset #103
/* Z7:   */     //   Java source line #80	-> byte code offset #108
/* Z8:   */     //   Java source line #81	-> byte code offset #115
/* Z9:   */     //   Java source line #71	-> byte code offset #223
/* [0:   */     //   Java source line #81	-> byte code offset #232
/* [1:   */     //   Java source line #71	-> byte code offset #272
/* [2:   */     //   Java source line #81	-> byte code offset #281
/* [3:   */     //   Java source line #71	-> byte code offset #321
/* [4:   */     //   Java source line #81	-> byte code offset #330
/* [5:   */     //   Java source line #82	-> byte code offset #372
/* [6:   */     // Local variable table:
/* [7:   */     //   start	length	slot	name	signature
/* [8:   */     //   0	387	0	this	EncryptedData
/* [9:   */     //   0	387	1	paramArrayOfByte1	byte[]
/* \0:   */     //   0	387	2	paramArrayOfByte2	byte[]
/* \1:   */     //   26	6	3	arrayOfByte1	byte[]
/* \2:   */     //   35	328	4	localByteArrayInputStream	java.io.ByteArrayInputStream
/* \3:   */     //   370	12	4	localIOException	java.io.IOException
/* \4:   */     //   38	315	5	localObject1	Object
/* \5:   */     //   49	265	6	localGZIPInputStream	java.util.zip.GZIPInputStream
/* \6:   */     //   321	7	6	localThrowable1	Throwable
/* \7:   */     //   52	252	7	localObject2	Object
/* \8:   */     //   61	204	8	localByteArrayOutputStream	java.io.ByteArrayOutputStream
/* \9:   */     //   272	7	8	localThrowable2	Throwable
/* ]0:   */     //   64	191	9	localObject3	Object
/* ]1:   */     //   71	22	10	arrayOfByte2	byte[]
/* ]2:   */     //   223	7	10	localThrowable3	Throwable
/* ]3:   */     //   85	11	11	i	int
/* ]4:   */     //   113	108	12	arrayOfByte3	byte[]
/* ]5:   */     //   133	5	13	localThrowable4	Throwable
/* ]6:   */     //   168	5	13	localThrowable5	Throwable
/* ]7:   */     //   203	5	13	localThrowable6	Throwable
/* ]8:   */     //   232	38	14	localObject4	Object
/* ]9:   */     //   252	5	15	localThrowable7	Throwable
/* ^0:   */     //   281	38	16	localObject5	Object
/* ^1:   */     //   301	5	17	localThrowable8	Throwable
/* ^2:   */     //   330	38	18	localObject6	Object
/* ^3:   */     //   350	5	19	localThrowable9	Throwable
/* ^4:   */     // Exception table:
/* ^5:   */     //   from	to	target	type
/* ^6:   */     //   125	130	133	java/lang/Throwable
/* ^7:   */     //   160	165	168	java/lang/Throwable
/* ^8:   */     //   195	200	203	java/lang/Throwable
/* ^9:   */     //   66	115	223	java/lang/Throwable
/* _0:   */     //   66	115	232	finally
/* _1:   */     //   223	234	232	finally
/* _2:   */     //   244	249	252	java/lang/Throwable
/* _3:   */     //   54	150	272	java/lang/Throwable
/* _4:   */     //   223	272	272	java/lang/Throwable
/* _5:   */     //   54	150	281	finally
/* _6:   */     //   223	283	281	finally
/* _7:   */     //   293	298	301	java/lang/Throwable
/* _8:   */     //   40	185	321	java/lang/Throwable
/* _9:   */     //   223	321	321	java/lang/Throwable
/* `0:   */     //   40	185	330	finally
/* `1:   */     //   223	332	330	finally
/* `2:   */     //   342	347	350	java/lang/Throwable
/* `3:   */     //   27	220	370	java/io/IOException
/* `4:   */     //   223	370	370	java/io/IOException
/* `5:   */   }
/* `6:   */   
/* `7:   */   public byte[] getData()
/* `8:   */   {
/* `9:87 */     return this.data;
/* a0:   */   }
/* a1:   */   
/* a2:   */   public byte[] getNonce()
/* a3:   */   {
/* a4:91 */     return this.nonce;
/* a5:   */   }
/* a6:   */   
/* a7:   */   public int getSize()
/* a8:   */   {
/* a9:95 */     return this.data.length + this.nonce.length;
/* b0:   */   }
/* b1:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.crypto.EncryptedData
 * JD-Core Version:    0.7.1
 */