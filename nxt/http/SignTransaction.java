/*  1:   */ package nxt.http;
/*  2:   */ 
/*  3:   */ public final class SignTransaction
/*  4:   */   extends APIServlet.APIRequestHandler
/*  5:   */ {
/*  6:18 */   static final SignTransaction instance = new SignTransaction();
/*  7:   */   
/*  8:   */   private SignTransaction()
/*  9:   */   {
/* 10:21 */     super(new APITag[] { APITag.TRANSACTIONS }, new String[] { "unsignedTransactionBytes", "unsignedTransactionJSON", "secretPhrase" });
/* 11:   */   }
/* 12:   */   
/* 13:   */   /* Error */
/* 14:   */   org.json.simple.JSONStreamAware processRequest(javax.servlet.http.HttpServletRequest paramHttpServletRequest)
/* 15:   */     throws nxt.NxtException
/* 16:   */   {
/* 17:   */     // Byte code:
/* 18:   */     //   0: aload_1
/* 19:   */     //   1: ldc 4
/* 20:   */     //   3: invokeinterface 8 2 0
/* 21:   */     //   8: invokestatic 9	nxt/util/Convert:emptyToNull	(Ljava/lang/String;)Ljava/lang/String;
/* 22:   */     //   11: astore_2
/* 23:   */     //   12: aload_1
/* 24:   */     //   13: ldc 5
/* 25:   */     //   15: invokeinterface 8 2 0
/* 26:   */     //   20: invokestatic 9	nxt/util/Convert:emptyToNull	(Ljava/lang/String;)Ljava/lang/String;
/* 27:   */     //   23: astore_3
/* 28:   */     //   24: aload_2
/* 29:   */     //   25: aload_3
/* 30:   */     //   26: invokestatic 10	nxt/http/ParameterParser:parseTransaction	(Ljava/lang/String;Ljava/lang/String;)Lnxt/Transaction;
/* 31:   */     //   29: astore 4
/* 32:   */     //   31: aload_1
/* 33:   */     //   32: ldc 6
/* 34:   */     //   34: invokeinterface 8 2 0
/* 35:   */     //   39: invokestatic 9	nxt/util/Convert:emptyToNull	(Ljava/lang/String;)Ljava/lang/String;
/* 36:   */     //   42: astore 5
/* 37:   */     //   44: aload 5
/* 38:   */     //   46: ifnonnull +7 -> 53
/* 39:   */     //   49: getstatic 11	nxt/http/JSONResponses:MISSING_SECRET_PHRASE	Lorg/json/simple/JSONStreamAware;
/* 40:   */     //   52: areturn
/* 41:   */     //   53: new 12	org/json/simple/JSONObject
/* 42:   */     //   56: dup
/* 43:   */     //   57: invokespecial 13	org/json/simple/JSONObject:<init>	()V
/* 44:   */     //   60: astore 6
/* 45:   */     //   62: aload 4
/* 46:   */     //   64: invokeinterface 14 1 0
/* 47:   */     //   69: aload 4
/* 48:   */     //   71: invokeinterface 15 1 0
/* 49:   */     //   76: ifnull +28 -> 104
/* 50:   */     //   79: aload 6
/* 51:   */     //   81: ldc 16
/* 52:   */     //   83: iconst_4
/* 53:   */     //   84: invokestatic 17	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
/* 54:   */     //   87: invokevirtual 18	org/json/simple/JSONObject:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
/* 55:   */     //   90: pop
/* 56:   */     //   91: aload 6
/* 57:   */     //   93: ldc 19
/* 58:   */     //   95: ldc 20
/* 59:   */     //   97: invokevirtual 18	org/json/simple/JSONObject:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
/* 60:   */     //   100: pop
/* 61:   */     //   101: aload 6
/* 62:   */     //   103: areturn
/* 63:   */     //   104: aload 5
/* 64:   */     //   106: invokestatic 21	nxt/crypto/Crypto:getPublicKey	(Ljava/lang/String;)[B
/* 65:   */     //   109: aload 4
/* 66:   */     //   111: invokeinterface 22 1 0
/* 67:   */     //   116: invokestatic 23	java/util/Arrays:equals	([B[B)Z
/* 68:   */     //   119: ifne +28 -> 147
/* 69:   */     //   122: aload 6
/* 70:   */     //   124: ldc 16
/* 71:   */     //   126: iconst_4
/* 72:   */     //   127: invokestatic 17	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
/* 73:   */     //   130: invokevirtual 18	org/json/simple/JSONObject:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
/* 74:   */     //   133: pop
/* 75:   */     //   134: aload 6
/* 76:   */     //   136: ldc 19
/* 77:   */     //   138: ldc 24
/* 78:   */     //   140: invokevirtual 18	org/json/simple/JSONObject:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
/* 79:   */     //   143: pop
/* 80:   */     //   144: aload 6
/* 81:   */     //   146: areturn
/* 82:   */     //   147: aload 4
/* 83:   */     //   149: aload 5
/* 84:   */     //   151: invokeinterface 25 2 0
/* 85:   */     //   156: aload 6
/* 86:   */     //   158: ldc 26
/* 87:   */     //   160: aload 4
/* 88:   */     //   162: invokeinterface 27 1 0
/* 89:   */     //   167: invokevirtual 18	org/json/simple/JSONObject:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
/* 90:   */     //   170: pop
/* 91:   */     //   171: aload 6
/* 92:   */     //   173: ldc 28
/* 93:   */     //   175: aload 4
/* 94:   */     //   177: invokeinterface 29 1 0
/* 95:   */     //   182: invokevirtual 18	org/json/simple/JSONObject:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
/* 96:   */     //   185: pop
/* 97:   */     //   186: aload 6
/* 98:   */     //   188: ldc 30
/* 99:   */     //   190: aload 4
/* :0:   */     //   192: invokeinterface 31 1 0
/* :1:   */     //   197: invokestatic 32	nxt/util/Convert:toHexString	([B)Ljava/lang/String;
/* :2:   */     //   200: invokevirtual 18	org/json/simple/JSONObject:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
/* :3:   */     //   203: pop
/* :4:   */     //   204: aload 6
/* :5:   */     //   206: ldc 33
/* :6:   */     //   208: invokestatic 34	nxt/crypto/Crypto:sha256	()Ljava/security/MessageDigest;
/* :7:   */     //   211: aload 4
/* :8:   */     //   213: invokeinterface 15 1 0
/* :9:   */     //   218: invokevirtual 35	java/security/MessageDigest:digest	([B)[B
/* ;0:   */     //   221: invokestatic 32	nxt/util/Convert:toHexString	([B)Ljava/lang/String;
/* ;1:   */     //   224: invokevirtual 18	org/json/simple/JSONObject:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
/* ;2:   */     //   227: pop
/* ;3:   */     //   228: aload 6
/* ;4:   */     //   230: ldc 36
/* ;5:   */     //   232: aload 4
/* ;6:   */     //   234: invokeinterface 37 1 0
/* ;7:   */     //   239: invokestatic 38	java/lang/Boolean:valueOf	(Z)Ljava/lang/Boolean;
/* ;8:   */     //   242: invokevirtual 18	org/json/simple/JSONObject:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
/* ;9:   */     //   245: pop
/* <0:   */     //   246: goto +74 -> 320
/* <1:   */     //   249: astore 7
/* <2:   */     //   251: aload 7
/* <3:   */     //   253: invokevirtual 41	java/lang/Exception:getMessage	()Ljava/lang/String;
/* <4:   */     //   256: aload 7
/* <5:   */     //   258: invokestatic 42	nxt/util/Logger:logDebugMessage	(Ljava/lang/String;Ljava/lang/Exception;)V
/* <6:   */     //   261: aload 6
/* <7:   */     //   263: ldc 16
/* <8:   */     //   265: iconst_4
/* <9:   */     //   266: invokestatic 17	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
/* =0:   */     //   269: invokevirtual 18	org/json/simple/JSONObject:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
/* =1:   */     //   272: pop
/* =2:   */     //   273: aload 6
/* =3:   */     //   275: ldc 19
/* =4:   */     //   277: new 43	java/lang/StringBuilder
/* =5:   */     //   280: dup
/* =6:   */     //   281: invokespecial 44	java/lang/StringBuilder:<init>	()V
/* =7:   */     //   284: ldc 45
/* =8:   */     //   286: invokevirtual 46	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* =9:   */     //   289: aload 7
/* >0:   */     //   291: invokevirtual 47	java/lang/Exception:toString	()Ljava/lang/String;
/* >1:   */     //   294: invokevirtual 46	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* >2:   */     //   297: invokevirtual 48	java/lang/StringBuilder:toString	()Ljava/lang/String;
/* >3:   */     //   300: invokevirtual 18	org/json/simple/JSONObject:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
/* >4:   */     //   303: pop
/* >5:   */     //   304: aload 6
/* >6:   */     //   306: ldc 49
/* >7:   */     //   308: aload 7
/* >8:   */     //   310: invokevirtual 41	java/lang/Exception:getMessage	()Ljava/lang/String;
/* >9:   */     //   313: invokevirtual 18	org/json/simple/JSONObject:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
/* ?0:   */     //   316: pop
/* ?1:   */     //   317: aload 6
/* ?2:   */     //   319: areturn
/* ?3:   */     //   320: aload 6
/* ?4:   */     //   322: areturn
/* ?5:   */     // Line number table:
/* ?6:   */     //   Java source line #27	-> byte code offset #0
/* ?7:   */     //   Java source line #28	-> byte code offset #12
/* ?8:   */     //   Java source line #29	-> byte code offset #24
/* ?9:   */     //   Java source line #31	-> byte code offset #31
/* @0:   */     //   Java source line #32	-> byte code offset #44
/* @1:   */     //   Java source line #33	-> byte code offset #49
/* @2:   */     //   Java source line #36	-> byte code offset #53
/* @3:   */     //   Java source line #38	-> byte code offset #62
/* @4:   */     //   Java source line #39	-> byte code offset #69
/* @5:   */     //   Java source line #40	-> byte code offset #79
/* @6:   */     //   Java source line #41	-> byte code offset #91
/* @7:   */     //   Java source line #42	-> byte code offset #101
/* @8:   */     //   Java source line #44	-> byte code offset #104
/* @9:   */     //   Java source line #45	-> byte code offset #122
/* A0:   */     //   Java source line #46	-> byte code offset #134
/* A1:   */     //   Java source line #47	-> byte code offset #144
/* A2:   */     //   Java source line #49	-> byte code offset #147
/* A3:   */     //   Java source line #50	-> byte code offset #156
/* A4:   */     //   Java source line #51	-> byte code offset #171
/* A5:   */     //   Java source line #52	-> byte code offset #186
/* A6:   */     //   Java source line #53	-> byte code offset #204
/* A7:   */     //   Java source line #54	-> byte code offset #228
/* A8:   */     //   Java source line #61	-> byte code offset #246
/* A9:   */     //   Java source line #55	-> byte code offset #249
/* B0:   */     //   Java source line #56	-> byte code offset #251
/* B1:   */     //   Java source line #57	-> byte code offset #261
/* B2:   */     //   Java source line #58	-> byte code offset #273
/* B3:   */     //   Java source line #59	-> byte code offset #304
/* B4:   */     //   Java source line #60	-> byte code offset #317
/* B5:   */     //   Java source line #62	-> byte code offset #320
/* B6:   */     // Local variable table:
/* B7:   */     //   start	length	slot	name	signature
/* B8:   */     //   0	323	0	this	SignTransaction
/* B9:   */     //   0	323	1	paramHttpServletRequest	javax.servlet.http.HttpServletRequest
/* C0:   */     //   11	14	2	str1	String
/* C1:   */     //   23	3	3	str2	String
/* C2:   */     //   29	204	4	localTransaction	nxt.Transaction
/* C3:   */     //   42	108	5	str3	String
/* C4:   */     //   60	261	6	localJSONObject	org.json.simple.JSONObject
/* C5:   */     //   249	60	7	localValidationException	nxt.NxtException.ValidationException
/* C6:   */     // Exception table:
/* C7:   */     //   from	to	target	type
/* C8:   */     //   62	103	249	nxt/NxtException$ValidationException
/* C9:   */     //   62	103	249	java/lang/RuntimeException
/* D0:   */     //   104	146	249	nxt/NxtException$ValidationException
/* D1:   */     //   104	146	249	java/lang/RuntimeException
/* D2:   */     //   147	246	249	nxt/NxtException$ValidationException
/* D3:   */     //   147	246	249	java/lang/RuntimeException
/* D4:   */   }
/* D5:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.http.SignTransaction
 * JD-Core Version:    0.7.1
 */