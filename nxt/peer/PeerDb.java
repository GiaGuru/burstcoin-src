/*  1:   */ package nxt.peer;
/*  2:   */ 
/*  3:   */ import java.sql.Connection;
/*  4:   */ import java.sql.PreparedStatement;
/*  5:   */ import java.sql.SQLException;
/*  6:   */ import java.util.Collection;
/*  7:   */ import nxt.db.Db;
/*  8:   */ 
/*  9:   */ final class PeerDb
/* 10:   */ {
/* 11:   */   /* Error */
/* 12:   */   static java.util.List<String> loadPeers()
/* 13:   */   {
/* 14:   */     // Byte code:
/* 15:   */     //   0: invokestatic 2	nxt/db/Db:getConnection	()Ljava/sql/Connection;
/* 16:   */     //   3: astore_0
/* 17:   */     //   4: aconst_null
/* 18:   */     //   5: astore_1
/* 19:   */     //   6: aload_0
/* 20:   */     //   7: ldc 3
/* 21:   */     //   9: invokeinterface 4 2 0
/* 22:   */     //   14: astore_2
/* 23:   */     //   15: aconst_null
/* 24:   */     //   16: astore_3
/* 25:   */     //   17: new 5	java/util/ArrayList
/* 26:   */     //   20: dup
/* 27:   */     //   21: invokespecial 6	java/util/ArrayList:<init>	()V
/* 28:   */     //   24: astore 4
/* 29:   */     //   26: aload_2
/* 30:   */     //   27: invokeinterface 7 1 0
/* 31:   */     //   32: astore 5
/* 32:   */     //   34: aconst_null
/* 33:   */     //   35: astore 6
/* 34:   */     //   37: aload 5
/* 35:   */     //   39: invokeinterface 8 1 0
/* 36:   */     //   44: ifeq +23 -> 67
/* 37:   */     //   47: aload 4
/* 38:   */     //   49: aload 5
/* 39:   */     //   51: ldc 9
/* 40:   */     //   53: invokeinterface 10 2 0
/* 41:   */     //   58: invokeinterface 11 2 0
/* 42:   */     //   63: pop
/* 43:   */     //   64: goto -27 -> 37
/* 44:   */     //   67: aload 5
/* 45:   */     //   69: ifnull +93 -> 162
/* 46:   */     //   72: aload 6
/* 47:   */     //   74: ifnull +25 -> 99
/* 48:   */     //   77: aload 5
/* 49:   */     //   79: invokeinterface 12 1 0
/* 50:   */     //   84: goto +78 -> 162
/* 51:   */     //   87: astore 7
/* 52:   */     //   89: aload 6
/* 53:   */     //   91: aload 7
/* 54:   */     //   93: invokevirtual 14	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 55:   */     //   96: goto +66 -> 162
/* 56:   */     //   99: aload 5
/* 57:   */     //   101: invokeinterface 12 1 0
/* 58:   */     //   106: goto +56 -> 162
/* 59:   */     //   109: astore 7
/* 60:   */     //   111: aload 7
/* 61:   */     //   113: astore 6
/* 62:   */     //   115: aload 7
/* 63:   */     //   117: athrow
/* 64:   */     //   118: astore 8
/* 65:   */     //   120: aload 5
/* 66:   */     //   122: ifnull +37 -> 159
/* 67:   */     //   125: aload 6
/* 68:   */     //   127: ifnull +25 -> 152
/* 69:   */     //   130: aload 5
/* 70:   */     //   132: invokeinterface 12 1 0
/* 71:   */     //   137: goto +22 -> 159
/* 72:   */     //   140: astore 9
/* 73:   */     //   142: aload 6
/* 74:   */     //   144: aload 9
/* 75:   */     //   146: invokevirtual 14	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 76:   */     //   149: goto +10 -> 159
/* 77:   */     //   152: aload 5
/* 78:   */     //   154: invokeinterface 12 1 0
/* 79:   */     //   159: aload 8
/* 80:   */     //   161: athrow
/* 81:   */     //   162: aload 4
/* 82:   */     //   164: astore 5
/* 83:   */     //   166: aload_2
/* 84:   */     //   167: ifnull +33 -> 200
/* 85:   */     //   170: aload_3
/* 86:   */     //   171: ifnull +23 -> 194
/* 87:   */     //   174: aload_2
/* 88:   */     //   175: invokeinterface 15 1 0
/* 89:   */     //   180: goto +20 -> 200
/* 90:   */     //   183: astore 6
/* 91:   */     //   185: aload_3
/* 92:   */     //   186: aload 6
/* 93:   */     //   188: invokevirtual 14	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* 94:   */     //   191: goto +9 -> 200
/* 95:   */     //   194: aload_2
/* 96:   */     //   195: invokeinterface 15 1 0
/* 97:   */     //   200: aload_0
/* 98:   */     //   201: ifnull +33 -> 234
/* 99:   */     //   204: aload_1
/* :0:   */     //   205: ifnull +23 -> 228
/* :1:   */     //   208: aload_0
/* :2:   */     //   209: invokeinterface 16 1 0
/* :3:   */     //   214: goto +20 -> 234
/* :4:   */     //   217: astore 6
/* :5:   */     //   219: aload_1
/* :6:   */     //   220: aload 6
/* :7:   */     //   222: invokevirtual 14	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* :8:   */     //   225: goto +9 -> 234
/* :9:   */     //   228: aload_0
/* ;0:   */     //   229: invokeinterface 16 1 0
/* ;1:   */     //   234: aload 5
/* ;2:   */     //   236: areturn
/* ;3:   */     //   237: astore 4
/* ;4:   */     //   239: aload 4
/* ;5:   */     //   241: astore_3
/* ;6:   */     //   242: aload 4
/* ;7:   */     //   244: athrow
/* ;8:   */     //   245: astore 10
/* ;9:   */     //   247: aload_2
/* <0:   */     //   248: ifnull +33 -> 281
/* <1:   */     //   251: aload_3
/* <2:   */     //   252: ifnull +23 -> 275
/* <3:   */     //   255: aload_2
/* <4:   */     //   256: invokeinterface 15 1 0
/* <5:   */     //   261: goto +20 -> 281
/* <6:   */     //   264: astore 11
/* <7:   */     //   266: aload_3
/* <8:   */     //   267: aload 11
/* <9:   */     //   269: invokevirtual 14	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* =0:   */     //   272: goto +9 -> 281
/* =1:   */     //   275: aload_2
/* =2:   */     //   276: invokeinterface 15 1 0
/* =3:   */     //   281: aload 10
/* =4:   */     //   283: athrow
/* =5:   */     //   284: astore_2
/* =6:   */     //   285: aload_2
/* =7:   */     //   286: astore_1
/* =8:   */     //   287: aload_2
/* =9:   */     //   288: athrow
/* >0:   */     //   289: astore 12
/* >1:   */     //   291: aload_0
/* >2:   */     //   292: ifnull +33 -> 325
/* >3:   */     //   295: aload_1
/* >4:   */     //   296: ifnull +23 -> 319
/* >5:   */     //   299: aload_0
/* >6:   */     //   300: invokeinterface 16 1 0
/* >7:   */     //   305: goto +20 -> 325
/* >8:   */     //   308: astore 13
/* >9:   */     //   310: aload_1
/* ?0:   */     //   311: aload 13
/* ?1:   */     //   313: invokevirtual 14	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* ?2:   */     //   316: goto +9 -> 325
/* ?3:   */     //   319: aload_0
/* ?4:   */     //   320: invokeinterface 16 1 0
/* ?5:   */     //   325: aload 12
/* ?6:   */     //   327: athrow
/* ?7:   */     //   328: astore_0
/* ?8:   */     //   329: new 18	java/lang/RuntimeException
/* ?9:   */     //   332: dup
/* @0:   */     //   333: aload_0
/* @1:   */     //   334: invokevirtual 19	java/sql/SQLException:toString	()Ljava/lang/String;
/* @2:   */     //   337: aload_0
/* @3:   */     //   338: invokespecial 20	java/lang/RuntimeException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
/* @4:   */     //   341: athrow
/* @5:   */     // Line number table:
/* @6:   */     //   Java source line #16	-> byte code offset #0
/* @7:   */     //   Java source line #17	-> byte code offset #6
/* @8:   */     //   Java source line #16	-> byte code offset #15
/* @9:   */     //   Java source line #18	-> byte code offset #17
/* A0:   */     //   Java source line #19	-> byte code offset #26
/* A1:   */     //   Java source line #20	-> byte code offset #37
/* A2:   */     //   Java source line #21	-> byte code offset #47
/* A3:   */     //   Java source line #23	-> byte code offset #67
/* A4:   */     //   Java source line #19	-> byte code offset #109
/* A5:   */     //   Java source line #23	-> byte code offset #118
/* A6:   */     //   Java source line #24	-> byte code offset #162
/* A7:   */     //   Java source line #25	-> byte code offset #166
/* A8:   */     //   Java source line #16	-> byte code offset #237
/* A9:   */     //   Java source line #25	-> byte code offset #245
/* B0:   */     //   Java source line #16	-> byte code offset #284
/* B1:   */     //   Java source line #25	-> byte code offset #289
/* B2:   */     //   Java source line #26	-> byte code offset #329
/* B3:   */     // Local variable table:
/* B4:   */     //   start	length	slot	name	signature
/* B5:   */     //   3	317	0	localConnection	Connection
/* B6:   */     //   328	10	0	localSQLException	SQLException
/* B7:   */     //   5	306	1	localObject1	Object
/* B8:   */     //   14	262	2	localPreparedStatement	PreparedStatement
/* B9:   */     //   284	4	2	localThrowable1	Throwable
/* C0:   */     //   16	251	3	localObject2	Object
/* C1:   */     //   24	139	4	localArrayList	java.util.ArrayList
/* C2:   */     //   237	6	4	localThrowable2	Throwable
/* C3:   */     //   32	203	5	localObject3	Object
/* C4:   */     //   35	108	6	localObject4	Object
/* C5:   */     //   183	4	6	localThrowable3	Throwable
/* C6:   */     //   217	4	6	localThrowable4	Throwable
/* C7:   */     //   87	5	7	localThrowable5	Throwable
/* C8:   */     //   109	7	7	localThrowable6	Throwable
/* C9:   */     //   118	42	8	localObject5	Object
/* D0:   */     //   140	5	9	localThrowable7	Throwable
/* D1:   */     //   245	37	10	localObject6	Object
/* D2:   */     //   264	4	11	localThrowable8	Throwable
/* D3:   */     //   289	37	12	localObject7	Object
/* D4:   */     //   308	4	13	localThrowable9	Throwable
/* D5:   */     // Exception table:
/* D6:   */     //   from	to	target	type
/* D7:   */     //   77	84	87	java/lang/Throwable
/* D8:   */     //   37	67	109	java/lang/Throwable
/* D9:   */     //   37	67	118	finally
/* E0:   */     //   109	120	118	finally
/* E1:   */     //   130	137	140	java/lang/Throwable
/* E2:   */     //   174	180	183	java/lang/Throwable
/* E3:   */     //   208	214	217	java/lang/Throwable
/* E4:   */     //   17	166	237	java/lang/Throwable
/* E5:   */     //   17	166	245	finally
/* E6:   */     //   237	247	245	finally
/* E7:   */     //   255	261	264	java/lang/Throwable
/* E8:   */     //   6	200	284	java/lang/Throwable
/* E9:   */     //   237	284	284	java/lang/Throwable
/* F0:   */     //   6	200	289	finally
/* F1:   */     //   237	291	289	finally
/* F2:   */     //   299	305	308	java/lang/Throwable
/* F3:   */     //   0	234	328	java/sql/SQLException
/* F4:   */     //   237	328	328	java/sql/SQLException
/* F5:   */   }
/* F6:   */   
/* F7:   */   static void deletePeers(Collection<String> paramCollection)
/* F8:   */   {
/* F9:   */     try
/* G0:   */     {
/* G1:31 */       Connection localConnection = Db.getConnection();Object localObject1 = null;
/* G2:   */       try
/* G3:   */       {
/* G4:32 */         PreparedStatement localPreparedStatement = localConnection.prepareStatement("DELETE FROM peer WHERE address = ?");Object localObject2 = null;
/* G5:   */         try
/* G6:   */         {
/* G7:33 */           for (String str : paramCollection)
/* G8:   */           {
/* G9:34 */             localPreparedStatement.setString(1, str);
/* H0:35 */             localPreparedStatement.executeUpdate();
/* H1:   */           }
/* H2:   */         }
/* H3:   */         catch (Throwable localThrowable4)
/* H4:   */         {
/* H5:31 */           localObject2 = localThrowable4;throw localThrowable4;
/* H6:   */         }
/* H7:   */         finally {}
/* H8:   */       }
/* H9:   */       catch (Throwable localThrowable2)
/* I0:   */       {
/* I1:31 */         localObject1 = localThrowable2;throw localThrowable2;
/* I2:   */       }
/* I3:   */       finally
/* I4:   */       {
/* I5:37 */         if (localConnection != null) {
/* I6:37 */           if (localObject1 != null) {
/* I7:   */             try
/* I8:   */             {
/* I9:37 */               localConnection.close();
/* J0:   */             }
/* J1:   */             catch (Throwable localThrowable6)
/* J2:   */             {
/* J3:37 */               ((Throwable)localObject1).addSuppressed(localThrowable6);
/* J4:   */             }
/* J5:   */           } else {
/* J6:37 */             localConnection.close();
/* J7:   */           }
/* J8:   */         }
/* J9:   */       }
/* K0:   */     }
/* K1:   */     catch (SQLException localSQLException)
/* K2:   */     {
/* K3:38 */       throw new RuntimeException(localSQLException.toString(), localSQLException);
/* K4:   */     }
/* K5:   */   }
/* K6:   */   
/* K7:   */   static void addPeers(Collection<String> paramCollection)
/* K8:   */   {
/* K9:   */     try
/* L0:   */     {
/* L1:43 */       Connection localConnection = Db.getConnection();Object localObject1 = null;
/* L2:   */       try
/* L3:   */       {
/* L4:44 */         PreparedStatement localPreparedStatement = localConnection.prepareStatement("INSERT INTO peer (address) values (?)");Object localObject2 = null;
/* L5:   */         try
/* L6:   */         {
/* L7:45 */           for (String str : paramCollection)
/* L8:   */           {
/* L9:46 */             localPreparedStatement.setString(1, str);
/* M0:47 */             localPreparedStatement.executeUpdate();
/* M1:   */           }
/* M2:   */         }
/* M3:   */         catch (Throwable localThrowable4)
/* M4:   */         {
/* M5:43 */           localObject2 = localThrowable4;throw localThrowable4;
/* M6:   */         }
/* M7:   */         finally {}
/* M8:   */       }
/* M9:   */       catch (Throwable localThrowable2)
/* N0:   */       {
/* N1:43 */         localObject1 = localThrowable2;throw localThrowable2;
/* N2:   */       }
/* N3:   */       finally
/* N4:   */       {
/* N5:49 */         if (localConnection != null) {
/* N6:49 */           if (localObject1 != null) {
/* N7:   */             try
/* N8:   */             {
/* N9:49 */               localConnection.close();
/* O0:   */             }
/* O1:   */             catch (Throwable localThrowable6)
/* O2:   */             {
/* O3:49 */               ((Throwable)localObject1).addSuppressed(localThrowable6);
/* O4:   */             }
/* O5:   */           } else {
/* O6:49 */             localConnection.close();
/* O7:   */           }
/* O8:   */         }
/* O9:   */       }
/* P0:   */     }
/* P1:   */     catch (SQLException localSQLException)
/* P2:   */     {
/* P3:50 */       throw new RuntimeException(localSQLException.toString(), localSQLException);
/* P4:   */     }
/* P5:   */   }
/* P6:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.peer.PeerDb
 * JD-Core Version:    0.7.1
 */