/*  1:   */ package nxt.db;
/*  2:   */ 
/*  3:   */ import java.sql.Connection;
/*  4:   */ import java.sql.PreparedStatement;
/*  5:   */ import java.sql.ResultSet;
/*  6:   */ import java.sql.SQLException;
/*  7:   */ import java.util.ArrayList;
/*  8:   */ import java.util.Iterator;
/*  9:   */ import java.util.List;
/* 10:   */ import java.util.Map;
/* 11:   */ 
/* 12:   */ public abstract class ValuesDbTable<T, V>
/* 13:   */   extends DerivedDbTable
/* 14:   */ {
/* 15:   */   private final boolean multiversion;
/* 16:   */   protected final DbKey.Factory<T> dbKeyFactory;
/* 17:   */   
/* 18:   */   protected ValuesDbTable(String paramString, DbKey.Factory<T> paramFactory)
/* 19:   */   {
/* 20:16 */     this(paramString, paramFactory, false);
/* 21:   */   }
/* 22:   */   
/* 23:   */   ValuesDbTable(String paramString, DbKey.Factory<T> paramFactory, boolean paramBoolean)
/* 24:   */   {
/* 25:20 */     super(paramString);
/* 26:21 */     this.dbKeyFactory = paramFactory;
/* 27:22 */     this.multiversion = paramBoolean;
/* 28:   */   }
/* 29:   */   
/* 30:   */   protected abstract V load(Connection paramConnection, ResultSet paramResultSet)
/* 31:   */     throws SQLException;
/* 32:   */   
/* 33:   */   protected abstract void save(Connection paramConnection, T paramT, V paramV)
/* 34:   */     throws SQLException;
/* 35:   */   
/* 36:   */   /* Error */
/* 37:   */   public final List<V> get(DbKey paramDbKey)
/* 38:   */   {
/* 39:   */     // Byte code:
/* 40:   */     //   0: invokestatic 5	nxt/db/Db:isInTransaction	()Z
/* 41:   */     //   3: ifeq +26 -> 29
/* 42:   */     //   6: aload_0
/* 43:   */     //   7: getfield 6	nxt/db/ValuesDbTable:table	Ljava/lang/String;
/* 44:   */     //   10: invokestatic 7	nxt/db/Db:getCache	(Ljava/lang/String;)Ljava/util/Map;
/* 45:   */     //   13: aload_1
/* 46:   */     //   14: invokeinterface 8 2 0
/* 47:   */     //   19: checkcast 9	java/util/List
/* 48:   */     //   22: astore_2
/* 49:   */     //   23: aload_2
/* 50:   */     //   24: ifnull +5 -> 29
/* 51:   */     //   27: aload_2
/* 52:   */     //   28: areturn
/* 53:   */     //   29: invokestatic 10	nxt/db/Db:getConnection	()Ljava/sql/Connection;
/* 54:   */     //   32: astore_3
/* 55:   */     //   33: aconst_null
/* 56:   */     //   34: astore 4
/* 57:   */     //   36: aload_3
/* 58:   */     //   37: new 11	java/lang/StringBuilder
/* 59:   */     //   40: dup
/* 60:   */     //   41: invokespecial 12	java/lang/StringBuilder:<init>	()V
/* 61:   */     //   44: ldc 13
/* 62:   */     //   46: invokevirtual 14	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 63:   */     //   49: aload_0
/* 64:   */     //   50: getfield 6	nxt/db/ValuesDbTable:table	Ljava/lang/String;
/* 65:   */     //   53: invokevirtual 14	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 66:   */     //   56: aload_0
/* 67:   */     //   57: getfield 3	nxt/db/ValuesDbTable:dbKeyFactory	Lnxt/db/DbKey$Factory;
/* 68:   */     //   60: invokevirtual 15	nxt/db/DbKey$Factory:getPKClause	()Ljava/lang/String;
/* 69:   */     //   63: invokevirtual 14	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 70:   */     //   66: aload_0
/* 71:   */     //   67: getfield 4	nxt/db/ValuesDbTable:multiversion	Z
/* 72:   */     //   70: ifeq +8 -> 78
/* 73:   */     //   73: ldc 16
/* 74:   */     //   75: goto +5 -> 80
/* 75:   */     //   78: ldc 17
/* 76:   */     //   80: invokevirtual 14	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 77:   */     //   83: ldc 18
/* 78:   */     //   85: invokevirtual 14	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 79:   */     //   88: invokevirtual 19	java/lang/StringBuilder:toString	()Ljava/lang/String;
/* 80:   */     //   91: invokeinterface 20 2 0
/* 81:   */     //   96: astore 5
/* 82:   */     //   98: aconst_null
/* 83:   */     //   99: astore 6
/* 84:   */     //   101: aload_1
/* 85:   */     //   102: aload 5
/* 86:   */     //   104: invokeinterface 21 2 0
/* 87:   */     //   109: pop
/* 88:   */     //   110: aload_0
/* 89:   */     //   111: aload_3
/* 90:   */     //   112: aload 5
/* 91:   */     //   114: invokespecial 22	nxt/db/ValuesDbTable:get	(Ljava/sql/Connection;Ljava/sql/PreparedStatement;)Ljava/util/List;
/* 92:   */     //   117: astore_2
/* 93:   */     //   118: invokestatic 5	nxt/db/Db:isInTransaction	()Z
/* 94:   */     //   121: ifeq +18 -> 139
/* 95:   */     //   124: aload_0
/* 96:   */     //   125: getfield 6	nxt/db/ValuesDbTable:table	Ljava/lang/String;
/* 97:   */     //   128: invokestatic 7	nxt/db/Db:getCache	(Ljava/lang/String;)Ljava/util/Map;
/* 98:   */     //   131: aload_1
/* 99:   */     //   132: aload_2
/* :0:   */     //   133: invokeinterface 23 3 0
/* :1:   */     //   138: pop
/* :2:   */     //   139: aload_2
/* :3:   */     //   140: astore 7
/* :4:   */     //   142: aload 5
/* :5:   */     //   144: ifnull +37 -> 181
/* :6:   */     //   147: aload 6
/* :7:   */     //   149: ifnull +25 -> 174
/* :8:   */     //   152: aload 5
/* :9:   */     //   154: invokeinterface 24 1 0
/* ;0:   */     //   159: goto +22 -> 181
/* ;1:   */     //   162: astore 8
/* ;2:   */     //   164: aload 6
/* ;3:   */     //   166: aload 8
/* ;4:   */     //   168: invokevirtual 26	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* ;5:   */     //   171: goto +10 -> 181
/* ;6:   */     //   174: aload 5
/* ;7:   */     //   176: invokeinterface 24 1 0
/* ;8:   */     //   181: aload_3
/* ;9:   */     //   182: ifnull +35 -> 217
/* <0:   */     //   185: aload 4
/* <1:   */     //   187: ifnull +24 -> 211
/* <2:   */     //   190: aload_3
/* <3:   */     //   191: invokeinterface 27 1 0
/* <4:   */     //   196: goto +21 -> 217
/* <5:   */     //   199: astore 8
/* <6:   */     //   201: aload 4
/* <7:   */     //   203: aload 8
/* <8:   */     //   205: invokevirtual 26	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* <9:   */     //   208: goto +9 -> 217
/* =0:   */     //   211: aload_3
/* =1:   */     //   212: invokeinterface 27 1 0
/* =2:   */     //   217: aload 7
/* =3:   */     //   219: areturn
/* =4:   */     //   220: astore 7
/* =5:   */     //   222: aload 7
/* =6:   */     //   224: astore 6
/* =7:   */     //   226: aload 7
/* =8:   */     //   228: athrow
/* =9:   */     //   229: astore 9
/* >0:   */     //   231: aload 5
/* >1:   */     //   233: ifnull +37 -> 270
/* >2:   */     //   236: aload 6
/* >3:   */     //   238: ifnull +25 -> 263
/* >4:   */     //   241: aload 5
/* >5:   */     //   243: invokeinterface 24 1 0
/* >6:   */     //   248: goto +22 -> 270
/* >7:   */     //   251: astore 10
/* >8:   */     //   253: aload 6
/* >9:   */     //   255: aload 10
/* ?0:   */     //   257: invokevirtual 26	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* ?1:   */     //   260: goto +10 -> 270
/* ?2:   */     //   263: aload 5
/* ?3:   */     //   265: invokeinterface 24 1 0
/* ?4:   */     //   270: aload 9
/* ?5:   */     //   272: athrow
/* ?6:   */     //   273: astore 5
/* ?7:   */     //   275: aload 5
/* ?8:   */     //   277: astore 4
/* ?9:   */     //   279: aload 5
/* @0:   */     //   281: athrow
/* @1:   */     //   282: astore 11
/* @2:   */     //   284: aload_3
/* @3:   */     //   285: ifnull +35 -> 320
/* @4:   */     //   288: aload 4
/* @5:   */     //   290: ifnull +24 -> 314
/* @6:   */     //   293: aload_3
/* @7:   */     //   294: invokeinterface 27 1 0
/* @8:   */     //   299: goto +21 -> 320
/* @9:   */     //   302: astore 12
/* A0:   */     //   304: aload 4
/* A1:   */     //   306: aload 12
/* A2:   */     //   308: invokevirtual 26	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/* A3:   */     //   311: goto +9 -> 320
/* A4:   */     //   314: aload_3
/* A5:   */     //   315: invokeinterface 27 1 0
/* A6:   */     //   320: aload 11
/* A7:   */     //   322: athrow
/* A8:   */     //   323: astore_3
/* A9:   */     //   324: new 29	java/lang/RuntimeException
/* B0:   */     //   327: dup
/* B1:   */     //   328: aload_3
/* B2:   */     //   329: invokevirtual 30	java/sql/SQLException:toString	()Ljava/lang/String;
/* B3:   */     //   332: aload_3
/* B4:   */     //   333: invokespecial 31	java/lang/RuntimeException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
/* B5:   */     //   336: athrow
/* B6:   */     // Line number table:
/* B7:   */     //   Java source line #31	-> byte code offset #0
/* B8:   */     //   Java source line #32	-> byte code offset #6
/* B9:   */     //   Java source line #33	-> byte code offset #23
/* C0:   */     //   Java source line #34	-> byte code offset #27
/* C1:   */     //   Java source line #37	-> byte code offset #29
/* C2:   */     //   Java source line #38	-> byte code offset #36
/* C3:   */     //   Java source line #37	-> byte code offset #98
/* C4:   */     //   Java source line #40	-> byte code offset #101
/* C5:   */     //   Java source line #41	-> byte code offset #110
/* C6:   */     //   Java source line #42	-> byte code offset #118
/* C7:   */     //   Java source line #43	-> byte code offset #124
/* C8:   */     //   Java source line #45	-> byte code offset #139
/* C9:   */     //   Java source line #46	-> byte code offset #142
/* D0:   */     //   Java source line #37	-> byte code offset #220
/* D1:   */     //   Java source line #46	-> byte code offset #229
/* D2:   */     //   Java source line #37	-> byte code offset #273
/* D3:   */     //   Java source line #46	-> byte code offset #282
/* D4:   */     //   Java source line #47	-> byte code offset #324
/* D5:   */     // Local variable table:
/* D6:   */     //   start	length	slot	name	signature
/* D7:   */     //   0	337	0	this	ValuesDbTable
/* D8:   */     //   0	337	1	paramDbKey	DbKey
/* D9:   */     //   22	118	2	localList1	List
/* E0:   */     //   32	283	3	localConnection	Connection
/* E1:   */     //   323	10	3	localSQLException	SQLException
/* E2:   */     //   34	271	4	localObject1	Object
/* E3:   */     //   96	168	5	localPreparedStatement	PreparedStatement
/* E4:   */     //   273	7	5	localThrowable1	Throwable
/* E5:   */     //   99	155	6	localObject2	Object
/* E6:   */     //   220	7	7	localThrowable2	Throwable
/* E7:   */     //   162	5	8	localThrowable3	Throwable
/* E8:   */     //   199	5	8	localThrowable4	Throwable
/* E9:   */     //   229	42	9	localObject3	Object
/* F0:   */     //   251	5	10	localThrowable5	Throwable
/* F1:   */     //   282	39	11	localObject4	Object
/* F2:   */     //   302	5	12	localThrowable6	Throwable
/* F3:   */     // Exception table:
/* F4:   */     //   from	to	target	type
/* F5:   */     //   152	159	162	java/lang/Throwable
/* F6:   */     //   190	196	199	java/lang/Throwable
/* F7:   */     //   101	142	220	java/lang/Throwable
/* F8:   */     //   101	142	229	finally
/* F9:   */     //   220	231	229	finally
/* G0:   */     //   241	248	251	java/lang/Throwable
/* G1:   */     //   36	181	273	java/lang/Throwable
/* G2:   */     //   220	273	273	java/lang/Throwable
/* G3:   */     //   36	181	282	finally
/* G4:   */     //   220	284	282	finally
/* G5:   */     //   293	299	302	java/lang/Throwable
/* G6:   */     //   29	217	323	java/sql/SQLException
/* G7:   */     //   220	323	323	java/sql/SQLException
/* G8:   */   }
/* G9:   */   
/* H0:   */   private List<V> get(Connection paramConnection, PreparedStatement paramPreparedStatement)
/* H1:   */   {
/* H2:   */     try
/* H3:   */     {
/* H4:53 */       ArrayList localArrayList = new ArrayList();
/* H5:54 */       ResultSet localResultSet = paramPreparedStatement.executeQuery();Object localObject1 = null;
/* H6:   */       try
/* H7:   */       {
/* H8:55 */         while (localResultSet.next()) {
/* H9:56 */           localArrayList.add(load(paramConnection, localResultSet));
/* I0:   */         }
/* I1:   */       }
/* I2:   */       catch (Throwable localThrowable2)
/* I3:   */       {
/* I4:54 */         localObject1 = localThrowable2;throw localThrowable2;
/* I5:   */       }
/* I6:   */       finally
/* I7:   */       {
/* I8:58 */         if (localResultSet != null) {
/* I9:58 */           if (localObject1 != null) {
/* J0:   */             try
/* J1:   */             {
/* J2:58 */               localResultSet.close();
/* J3:   */             }
/* J4:   */             catch (Throwable localThrowable3)
/* J5:   */             {
/* J6:58 */               ((Throwable)localObject1).addSuppressed(localThrowable3);
/* J7:   */             }
/* J8:   */           } else {
/* J9:58 */             localResultSet.close();
/* K0:   */           }
/* K1:   */         }
/* K2:   */       }
/* K3:59 */       return localArrayList;
/* K4:   */     }
/* K5:   */     catch (SQLException localSQLException)
/* K6:   */     {
/* K7:61 */       throw new RuntimeException(localSQLException.toString(), localSQLException);
/* K8:   */     }
/* K9:   */   }
/* L0:   */   
/* L1:   */   public final void insert(T paramT, List<V> paramList)
/* L2:   */   {
/* L3:66 */     if (!Db.isInTransaction()) {
/* L4:67 */       throw new IllegalStateException("Not in transaction");
/* L5:   */     }
/* L6:69 */     DbKey localDbKey = this.dbKeyFactory.newKey(paramT);
/* L7:70 */     Db.getCache(this.table).put(localDbKey, paramList);
/* L8:   */     try
/* L9:   */     {
/* M0:71 */       Connection localConnection = Db.getConnection();Object localObject1 = null;
/* M1:   */       try
/* M2:   */       {
/* M3:72 */         if (this.multiversion)
/* M4:   */         {
/* M5:73 */           localObject2 = localConnection.prepareStatement("UPDATE " + this.table + " SET latest = FALSE " + this.dbKeyFactory.getPKClause() + " AND latest = TRUE");localObject3 = null;
/* M6:   */           try
/* M7:   */           {
/* M8:75 */             localDbKey.setPK((PreparedStatement)localObject2);
/* M9:76 */             ((PreparedStatement)localObject2).executeUpdate();
/* N0:   */           }
/* N1:   */           catch (Throwable localThrowable4)
/* N2:   */           {
/* N3:73 */             localObject3 = localThrowable4;throw localThrowable4;
/* N4:   */           }
/* N5:   */           finally
/* N6:   */           {
/* N7:77 */             if (localObject2 != null) {
/* N8:77 */               if (localObject3 != null) {
/* N9:   */                 try
/* O0:   */                 {
/* O1:77 */                   ((PreparedStatement)localObject2).close();
/* O2:   */                 }
/* O3:   */                 catch (Throwable localThrowable5)
/* O4:   */                 {
/* O5:77 */                   ((Throwable)localObject3).addSuppressed(localThrowable5);
/* O6:   */                 }
/* O7:   */               } else {
/* O8:77 */                 ((PreparedStatement)localObject2).close();
/* O9:   */               }
/* P0:   */             }
/* P1:   */           }
/* P2:   */         }
/* P3:79 */         for (localObject2 = paramList.iterator(); ((Iterator)localObject2).hasNext();)
/* P4:   */         {
/* P5:79 */           localObject3 = ((Iterator)localObject2).next();
/* P6:80 */           save(localConnection, paramT, localObject3);
/* P7:   */         }
/* P8:   */       }
/* P9:   */       catch (Throwable localThrowable2)
/* Q0:   */       {
/* Q1:   */         Object localObject2;
/* Q2:   */         Object localObject3;
/* Q3:71 */         localObject1 = localThrowable2;throw localThrowable2;
/* Q4:   */       }
/* Q5:   */       finally
/* Q6:   */       {
/* Q7:82 */         if (localConnection != null) {
/* Q8:82 */           if (localObject1 != null) {
/* Q9:   */             try
/* R0:   */             {
/* R1:82 */               localConnection.close();
/* R2:   */             }
/* R3:   */             catch (Throwable localThrowable6)
/* R4:   */             {
/* R5:82 */               ((Throwable)localObject1).addSuppressed(localThrowable6);
/* R6:   */             }
/* R7:   */           } else {
/* R8:82 */             localConnection.close();
/* R9:   */           }
/* S0:   */         }
/* S1:   */       }
/* S2:   */     }
/* S3:   */     catch (SQLException localSQLException)
/* S4:   */     {
/* S5:83 */       throw new RuntimeException(localSQLException.toString(), localSQLException);
/* S6:   */     }
/* S7:   */   }
/* S8:   */   
/* S9:   */   public void rollback(int paramInt)
/* T0:   */   {
/* T1:89 */     super.rollback(paramInt);
/* T2:90 */     Db.getCache(this.table).clear();
/* T3:   */   }
/* T4:   */   
/* T5:   */   public final void truncate()
/* T6:   */   {
/* T7:95 */     super.truncate();
/* T8:96 */     Db.getCache(this.table).clear();
/* T9:   */   }
/* U0:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.db.ValuesDbTable
 * JD-Core Version:    0.7.1
 */