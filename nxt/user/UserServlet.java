/*  1:   */ package nxt.user;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import java.util.Collections;
/*  5:   */ import java.util.HashMap;
/*  6:   */ import java.util.Map;
/*  7:   */ import javax.servlet.ServletException;
/*  8:   */ import javax.servlet.http.HttpServlet;
/*  9:   */ import javax.servlet.http.HttpServletRequest;
/* 10:   */ import javax.servlet.http.HttpServletResponse;
/* 11:   */ import nxt.Nxt;
/* 12:   */ import nxt.NxtException;
/* 13:   */ import org.json.simple.JSONStreamAware;
/* 14:   */ 
/* 15:   */ public final class UserServlet
/* 16:   */   extends HttpServlet
/* 17:   */ {
/* 18:   */   static abstract class UserRequestHandler
/* 19:   */   {
/* 20:   */     abstract JSONStreamAware processRequest(HttpServletRequest paramHttpServletRequest, User paramUser)
/* 21:   */       throws NxtException, IOException;
/* 22:   */     
/* 23:   */     boolean requirePost()
/* 24:   */     {
/* 25:27 */       return false;
/* 26:   */     }
/* 27:   */   }
/* 28:   */   
/* 29:31 */   private static final boolean enforcePost = Nxt.getBooleanProperty("nxt.uiServerEnforcePOST").booleanValue();
/* 30:   */   private static final Map<String, UserRequestHandler> userRequestHandlers;
/* 31:   */   
/* 32:   */   static
/* 33:   */   {
/* 34:36 */     HashMap localHashMap = new HashMap();
/* 35:37 */     localHashMap.put("generateAuthorizationToken", GenerateAuthorizationToken.instance);
/* 36:38 */     localHashMap.put("getInitialData", GetInitialData.instance);
/* 37:39 */     localHashMap.put("getNewData", GetNewData.instance);
/* 38:40 */     localHashMap.put("lockAccount", LockAccount.instance);
/* 39:41 */     localHashMap.put("removeActivePeer", RemoveActivePeer.instance);
/* 40:42 */     localHashMap.put("removeBlacklistedPeer", RemoveBlacklistedPeer.instance);
/* 41:43 */     localHashMap.put("removeKnownPeer", RemoveKnownPeer.instance);
/* 42:44 */     localHashMap.put("sendMoney", SendMoney.instance);
/* 43:45 */     localHashMap.put("unlockAccount", UnlockAccount.instance);
/* 44:46 */     userRequestHandlers = Collections.unmodifiableMap(localHashMap);
/* 45:   */   }
/* 46:   */   
/* 47:   */   protected void doGet(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
/* 48:   */     throws ServletException, IOException
/* 49:   */   {
/* 50:51 */     process(paramHttpServletRequest, paramHttpServletResponse);
/* 51:   */   }
/* 52:   */   
/* 53:   */   protected void doPost(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
/* 54:   */     throws ServletException, IOException
/* 55:   */   {
/* 56:56 */     process(paramHttpServletRequest, paramHttpServletResponse);
/* 57:   */   }
/* 58:   */   
/* 59:   */   /* Error */
/* 60:   */   private void process(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
/* 61:   */     throws IOException
/* 62:   */   {
/* 63:   */     // Byte code:
/* 64:   */     //   0: aload_2
/* 65:   */     //   1: ldc 3
/* 66:   */     //   3: ldc 4
/* 67:   */     //   5: invokeinterface 5 3 0
/* 68:   */     //   10: aload_2
/* 69:   */     //   11: ldc 6
/* 70:   */     //   13: ldc 7
/* 71:   */     //   15: invokeinterface 5 3 0
/* 72:   */     //   20: aload_2
/* 73:   */     //   21: ldc 8
/* 74:   */     //   23: lconst_0
/* 75:   */     //   24: invokeinterface 9 4 0
/* 76:   */     //   29: aconst_null
/* 77:   */     //   30: astore_3
/* 78:   */     //   31: aload_1
/* 79:   */     //   32: ldc 10
/* 80:   */     //   34: invokeinterface 11 2 0
/* 81:   */     //   39: astore 4
/* 82:   */     //   41: aload 4
/* 83:   */     //   43: ifnonnull +14 -> 57
/* 84:   */     //   46: aload_3
/* 85:   */     //   47: ifnull +9 -> 56
/* 86:   */     //   50: aload_3
/* 87:   */     //   51: aload_1
/* 88:   */     //   52: aload_2
/* 89:   */     //   53: invokevirtual 12	nxt/user/User:processPendingResponses	(Ljavax/servlet/http/HttpServletRequest;Ljavax/servlet/http/HttpServletResponse;)V
/* 90:   */     //   56: return
/* 91:   */     //   57: aload 4
/* 92:   */     //   59: invokestatic 13	nxt/user/Users:getUser	(Ljava/lang/String;)Lnxt/user/User;
/* 93:   */     //   62: astore_3
/* 94:   */     //   63: getstatic 14	nxt/user/Users:allowedUserHosts	Ljava/util/Set;
/* 95:   */     //   66: ifnull +38 -> 104
/* 96:   */     //   69: getstatic 14	nxt/user/Users:allowedUserHosts	Ljava/util/Set;
/* 97:   */     //   72: aload_1
/* 98:   */     //   73: invokeinterface 15 1 0
/* 99:   */     //   78: invokeinterface 16 2 0
/* :0:   */     //   83: ifne +21 -> 104
/* :1:   */     //   86: aload_3
/* :2:   */     //   87: getstatic 17	nxt/user/JSONResponses:DENY_ACCESS	Lorg/json/simple/JSONStreamAware;
/* :3:   */     //   90: invokevirtual 18	nxt/user/User:enqueue	(Lorg/json/simple/JSONStreamAware;)V
/* :4:   */     //   93: aload_3
/* :5:   */     //   94: ifnull +9 -> 103
/* :6:   */     //   97: aload_3
/* :7:   */     //   98: aload_1
/* :8:   */     //   99: aload_2
/* :9:   */     //   100: invokevirtual 12	nxt/user/User:processPendingResponses	(Ljavax/servlet/http/HttpServletRequest;Ljavax/servlet/http/HttpServletResponse;)V
/* ;0:   */     //   103: return
/* ;1:   */     //   104: aload_1
/* ;2:   */     //   105: ldc 19
/* ;3:   */     //   107: invokeinterface 11 2 0
/* ;4:   */     //   112: astore 5
/* ;5:   */     //   114: aload 5
/* ;6:   */     //   116: ifnonnull +21 -> 137
/* ;7:   */     //   119: aload_3
/* ;8:   */     //   120: getstatic 20	nxt/user/JSONResponses:INCORRECT_REQUEST	Lorg/json/simple/JSONStreamAware;
/* ;9:   */     //   123: invokevirtual 18	nxt/user/User:enqueue	(Lorg/json/simple/JSONStreamAware;)V
/* <0:   */     //   126: aload_3
/* <1:   */     //   127: ifnull +9 -> 136
/* <2:   */     //   130: aload_3
/* <3:   */     //   131: aload_1
/* <4:   */     //   132: aload_2
/* <5:   */     //   133: invokevirtual 12	nxt/user/User:processPendingResponses	(Ljavax/servlet/http/HttpServletRequest;Ljavax/servlet/http/HttpServletResponse;)V
/* <6:   */     //   136: return
/* <7:   */     //   137: getstatic 21	nxt/user/UserServlet:userRequestHandlers	Ljava/util/Map;
/* <8:   */     //   140: aload 5
/* <9:   */     //   142: invokeinterface 22 2 0
/* =0:   */     //   147: checkcast 23	nxt/user/UserServlet$UserRequestHandler
/* =1:   */     //   150: astore 6
/* =2:   */     //   152: aload 6
/* =3:   */     //   154: ifnonnull +21 -> 175
/* =4:   */     //   157: aload_3
/* =5:   */     //   158: getstatic 20	nxt/user/JSONResponses:INCORRECT_REQUEST	Lorg/json/simple/JSONStreamAware;
/* =6:   */     //   161: invokevirtual 18	nxt/user/User:enqueue	(Lorg/json/simple/JSONStreamAware;)V
/* =7:   */     //   164: aload_3
/* =8:   */     //   165: ifnull +9 -> 174
/* =9:   */     //   168: aload_3
/* >0:   */     //   169: aload_1
/* >1:   */     //   170: aload_2
/* >2:   */     //   171: invokevirtual 12	nxt/user/User:processPendingResponses	(Ljavax/servlet/http/HttpServletRequest;Ljavax/servlet/http/HttpServletResponse;)V
/* >3:   */     //   174: return
/* >4:   */     //   175: getstatic 24	nxt/user/UserServlet:enforcePost	Z
/* >5:   */     //   178: ifeq +43 -> 221
/* >6:   */     //   181: aload 6
/* >7:   */     //   183: invokevirtual 25	nxt/user/UserServlet$UserRequestHandler:requirePost	()Z
/* >8:   */     //   186: ifeq +35 -> 221
/* >9:   */     //   189: ldc 26
/* ?0:   */     //   191: aload_1
/* ?1:   */     //   192: invokeinterface 27 1 0
/* ?2:   */     //   197: invokevirtual 28	java/lang/String:equals	(Ljava/lang/Object;)Z
/* ?3:   */     //   200: ifne +21 -> 221
/* ?4:   */     //   203: aload_3
/* ?5:   */     //   204: getstatic 29	nxt/user/JSONResponses:POST_REQUIRED	Lorg/json/simple/JSONStreamAware;
/* ?6:   */     //   207: invokevirtual 18	nxt/user/User:enqueue	(Lorg/json/simple/JSONStreamAware;)V
/* ?7:   */     //   210: aload_3
/* ?8:   */     //   211: ifnull +9 -> 220
/* ?9:   */     //   214: aload_3
/* @0:   */     //   215: aload_1
/* @1:   */     //   216: aload_2
/* @2:   */     //   217: invokevirtual 12	nxt/user/User:processPendingResponses	(Ljavax/servlet/http/HttpServletRequest;Ljavax/servlet/http/HttpServletResponse;)V
/* @3:   */     //   220: return
/* @4:   */     //   221: aload 6
/* @5:   */     //   223: aload_1
/* @6:   */     //   224: aload_3
/* @7:   */     //   225: invokevirtual 30	nxt/user/UserServlet$UserRequestHandler:processRequest	(Ljavax/servlet/http/HttpServletRequest;Lnxt/user/User;)Lorg/json/simple/JSONStreamAware;
/* @8:   */     //   228: astore 7
/* @9:   */     //   230: aload 7
/* A0:   */     //   232: ifnull +9 -> 241
/* A1:   */     //   235: aload_3
/* A2:   */     //   236: aload 7
/* A3:   */     //   238: invokevirtual 18	nxt/user/User:enqueue	(Lorg/json/simple/JSONStreamAware;)V
/* A4:   */     //   241: aload_3
/* A5:   */     //   242: ifnull +91 -> 333
/* A6:   */     //   245: aload_3
/* A7:   */     //   246: aload_1
/* A8:   */     //   247: aload_2
/* A9:   */     //   248: invokevirtual 12	nxt/user/User:processPendingResponses	(Ljavax/servlet/http/HttpServletRequest;Ljavax/servlet/http/HttpServletResponse;)V
/* B0:   */     //   251: goto +82 -> 333
/* B1:   */     //   254: astore 4
/* B2:   */     //   256: ldc 33
/* B3:   */     //   258: aload 4
/* B4:   */     //   260: invokestatic 34	nxt/util/Logger:logMessage	(Ljava/lang/String;Ljava/lang/Exception;)V
/* B5:   */     //   263: aload_3
/* B6:   */     //   264: ifnull +41 -> 305
/* B7:   */     //   267: new 35	org/json/simple/JSONObject
/* B8:   */     //   270: dup
/* B9:   */     //   271: invokespecial 36	org/json/simple/JSONObject:<init>	()V
/* C0:   */     //   274: astore 5
/* C1:   */     //   276: aload 5
/* C2:   */     //   278: ldc 37
/* C3:   */     //   280: ldc 38
/* C4:   */     //   282: invokevirtual 39	org/json/simple/JSONObject:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
/* C5:   */     //   285: pop
/* C6:   */     //   286: aload 5
/* C7:   */     //   288: ldc 40
/* C8:   */     //   290: aload 4
/* C9:   */     //   292: invokevirtual 41	java/lang/Exception:toString	()Ljava/lang/String;
/* D0:   */     //   295: invokevirtual 39	org/json/simple/JSONObject:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
/* D1:   */     //   298: pop
/* D2:   */     //   299: aload_3
/* D3:   */     //   300: aload 5
/* D4:   */     //   302: invokevirtual 18	nxt/user/User:enqueue	(Lorg/json/simple/JSONStreamAware;)V
/* D5:   */     //   305: aload_3
/* D6:   */     //   306: ifnull +27 -> 333
/* D7:   */     //   309: aload_3
/* D8:   */     //   310: aload_1
/* D9:   */     //   311: aload_2
/* E0:   */     //   312: invokevirtual 12	nxt/user/User:processPendingResponses	(Ljavax/servlet/http/HttpServletRequest;Ljavax/servlet/http/HttpServletResponse;)V
/* E1:   */     //   315: goto +18 -> 333
/* E2:   */     //   318: astore 8
/* E3:   */     //   320: aload_3
/* E4:   */     //   321: ifnull +9 -> 330
/* E5:   */     //   324: aload_3
/* E6:   */     //   325: aload_1
/* E7:   */     //   326: aload_2
/* E8:   */     //   327: invokevirtual 12	nxt/user/User:processPendingResponses	(Ljavax/servlet/http/HttpServletRequest;Ljavax/servlet/http/HttpServletResponse;)V
/* E9:   */     //   330: aload 8
/* F0:   */     //   332: athrow
/* F1:   */     //   333: return
/* F2:   */     // Line number table:
/* F3:   */     //   Java source line #61	-> byte code offset #0
/* F4:   */     //   Java source line #62	-> byte code offset #10
/* F5:   */     //   Java source line #63	-> byte code offset #20
/* F6:   */     //   Java source line #65	-> byte code offset #29
/* F7:   */     //   Java source line #69	-> byte code offset #31
/* F8:   */     //   Java source line #70	-> byte code offset #41
/* F9:   */     //   Java source line #114	-> byte code offset #46
/* G0:   */     //   Java source line #115	-> byte code offset #50
/* G1:   */     //   Java source line #73	-> byte code offset #57
/* G2:   */     //   Java source line #75	-> byte code offset #63
/* G3:   */     //   Java source line #76	-> byte code offset #86
/* G4:   */     //   Java source line #114	-> byte code offset #93
/* G5:   */     //   Java source line #115	-> byte code offset #97
/* G6:   */     //   Java source line #80	-> byte code offset #104
/* G7:   */     //   Java source line #81	-> byte code offset #114
/* G8:   */     //   Java source line #82	-> byte code offset #119
/* G9:   */     //   Java source line #114	-> byte code offset #126
/* H0:   */     //   Java source line #115	-> byte code offset #130
/* H1:   */     //   Java source line #86	-> byte code offset #137
/* H2:   */     //   Java source line #87	-> byte code offset #152
/* H3:   */     //   Java source line #88	-> byte code offset #157
/* H4:   */     //   Java source line #114	-> byte code offset #164
/* H5:   */     //   Java source line #115	-> byte code offset #168
/* H6:   */     //   Java source line #92	-> byte code offset #175
/* H7:   */     //   Java source line #93	-> byte code offset #203
/* H8:   */     //   Java source line #114	-> byte code offset #210
/* H9:   */     //   Java source line #115	-> byte code offset #214
/* I0:   */     //   Java source line #97	-> byte code offset #221
/* I1:   */     //   Java source line #98	-> byte code offset #230
/* I2:   */     //   Java source line #99	-> byte code offset #235
/* I3:   */     //   Java source line #114	-> byte code offset #241
/* I4:   */     //   Java source line #115	-> byte code offset #245
/* I5:   */     //   Java source line #102	-> byte code offset #254
/* I6:   */     //   Java source line #104	-> byte code offset #256
/* I7:   */     //   Java source line #105	-> byte code offset #263
/* I8:   */     //   Java source line #106	-> byte code offset #267
/* I9:   */     //   Java source line #107	-> byte code offset #276
/* J0:   */     //   Java source line #108	-> byte code offset #286
/* J1:   */     //   Java source line #109	-> byte code offset #299
/* J2:   */     //   Java source line #114	-> byte code offset #305
/* J3:   */     //   Java source line #115	-> byte code offset #309
/* J4:   */     //   Java source line #114	-> byte code offset #318
/* J5:   */     //   Java source line #115	-> byte code offset #324
/* J6:   */     //   Java source line #120	-> byte code offset #333
/* J7:   */     // Local variable table:
/* J8:   */     //   start	length	slot	name	signature
/* J9:   */     //   0	334	0	this	UserServlet
/* K0:   */     //   0	334	1	paramHttpServletRequest	HttpServletRequest
/* K1:   */     //   0	334	2	paramHttpServletResponse	HttpServletResponse
/* K2:   */     //   30	295	3	localUser	User
/* K3:   */     //   39	19	4	str	String
/* K4:   */     //   254	37	4	localRuntimeException	RuntimeException
/* K5:   */     //   112	189	5	localObject1	Object
/* K6:   */     //   150	72	6	localUserRequestHandler	UserRequestHandler
/* K7:   */     //   228	9	7	localJSONStreamAware	JSONStreamAware
/* K8:   */     //   318	13	8	localObject2	Object
/* K9:   */     // Exception table:
/* L0:   */     //   from	to	target	type
/* L1:   */     //   31	46	254	java/lang/RuntimeException
/* L2:   */     //   31	46	254	nxt/NxtException
/* L3:   */     //   57	93	254	java/lang/RuntimeException
/* L4:   */     //   57	93	254	nxt/NxtException
/* L5:   */     //   104	126	254	java/lang/RuntimeException
/* L6:   */     //   104	126	254	nxt/NxtException
/* L7:   */     //   137	164	254	java/lang/RuntimeException
/* L8:   */     //   137	164	254	nxt/NxtException
/* L9:   */     //   175	210	254	java/lang/RuntimeException
/* M0:   */     //   175	210	254	nxt/NxtException
/* M1:   */     //   221	241	254	java/lang/RuntimeException
/* M2:   */     //   221	241	254	nxt/NxtException
/* M3:   */     //   31	46	318	finally
/* M4:   */     //   57	93	318	finally
/* M5:   */     //   104	126	318	finally
/* M6:   */     //   137	164	318	finally
/* M7:   */     //   175	210	318	finally
/* M8:   */     //   221	241	318	finally
/* M9:   */     //   254	305	318	finally
/* N0:   */     //   318	320	318	finally
/* N1:   */   }
/* N2:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.user.UserServlet
 * JD-Core Version:    0.7.1
 */