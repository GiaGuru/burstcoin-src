/*   1:    */ package nxt;
/*   2:    */ 
/*   3:    */ import java.io.BufferedReader;
/*   4:    */ import java.io.FileReader;
/*   5:    */ import java.io.IOException;
/*   6:    */ import java.io.PrintStream;
/*   7:    */ import java.util.Arrays;
/*   8:    */ import java.util.HashMap;
/*   9:    */ import java.util.HashSet;
/*  10:    */ import java.util.Iterator;
/*  11:    */ import java.util.List;
/*  12:    */ import java.util.Map;
/*  13:    */ import java.util.Map.Entry;
/*  14:    */ import java.util.Set;
/*  15:    */ import nxt.util.Convert;
/*  16:    */ import nxt.util.Logger;
/*  17:    */ 
/*  18:    */ public final class VerifyTrace
/*  19:    */ {
/*  20: 18 */   private static final List<String> balanceHeaders = Arrays.asList(new String[] { "balance", "unconfirmed balance" });
/*  21: 19 */   private static final List<String> deltaHeaders = Arrays.asList(new String[] { "transaction amount", "transaction fee", "generation fee", "trade cost", "purchase cost", "discount", "refund" });
/*  22: 21 */   private static final List<String> assetQuantityHeaders = Arrays.asList(new String[] { "asset balance", "unconfirmed asset balance" });
/*  23: 22 */   private static final List<String> deltaAssetQuantityHeaders = Arrays.asList(new String[] { "asset quantity", "trade quantity" });
/*  24:    */   
/*  25:    */   private static boolean isBalance(String paramString)
/*  26:    */   {
/*  27: 25 */     return balanceHeaders.contains(paramString);
/*  28:    */   }
/*  29:    */   
/*  30:    */   private static boolean isDelta(String paramString)
/*  31:    */   {
/*  32: 29 */     return deltaHeaders.contains(paramString);
/*  33:    */   }
/*  34:    */   
/*  35:    */   private static boolean isAssetQuantity(String paramString)
/*  36:    */   {
/*  37: 33 */     return assetQuantityHeaders.contains(paramString);
/*  38:    */   }
/*  39:    */   
/*  40:    */   private static boolean isDeltaAssetQuantity(String paramString)
/*  41:    */   {
/*  42: 37 */     return deltaAssetQuantityHeaders.contains(paramString);
/*  43:    */   }
/*  44:    */   
/*  45:    */   public static void main(String[] paramArrayOfString)
/*  46:    */   {
/*  47: 41 */     String str1 = paramArrayOfString.length == 1 ? paramArrayOfString[0] : "nxt-trace.csv";
/*  48:    */     try
/*  49:    */     {
/*  50: 42 */       BufferedReader localBufferedReader = new BufferedReader(new FileReader(str1));Object localObject1 = null;
/*  51:    */       try
/*  52:    */       {
/*  53: 43 */         String str2 = localBufferedReader.readLine();
/*  54: 44 */         String[] arrayOfString = unquote(str2.split(DebugTrace.SEPARATOR));
/*  55:    */         
/*  56: 46 */         HashMap localHashMap1 = new HashMap();
/*  57: 47 */         HashMap localHashMap2 = new HashMap();
/*  58: 48 */         HashMap localHashMap3 = new HashMap();
/*  59: 49 */         HashMap localHashMap4 = new HashMap();
/*  60:    */         Object localObject5;
/*  61:    */         Object localObject6;
/*  62:    */         Object localObject7;
/*  63:    */         Object localObject8;
/*  64:    */         Object localObject9;
/*  65:    */         String str3;
/*  66:    */         Object localObject10;
/*  67: 51 */         while ((str2 = localBufferedReader.readLine()) != null)
/*  68:    */         {
/*  69: 52 */           localObject2 = unquote(str2.split(DebugTrace.SEPARATOR));
/*  70: 53 */           localObject3 = new HashMap();
/*  71: 54 */           for (int i = 0; i < arrayOfString.length; i++) {
/*  72: 55 */             ((Map)localObject3).put(arrayOfString[i], localObject2[i]);
/*  73:    */           }
/*  74: 57 */           localObject4 = (String)((Map)localObject3).get("account");
/*  75: 58 */           localObject5 = (Map)localHashMap1.get(localObject4);
/*  76: 59 */           if (localObject5 == null)
/*  77:    */           {
/*  78: 60 */             localObject5 = new HashMap();
/*  79: 61 */             localHashMap1.put(localObject4, localObject5);
/*  80:    */           }
/*  81: 63 */           localObject6 = (Map)localHashMap2.get(localObject4);
/*  82: 64 */           if (localObject6 == null)
/*  83:    */           {
/*  84: 65 */             localObject6 = new HashMap();
/*  85: 66 */             localHashMap2.put(localObject4, localObject6);
/*  86:    */           }
/*  87: 68 */           if ("asset issuance".equals(((Map)localObject3).get("event")))
/*  88:    */           {
/*  89: 69 */             localObject7 = (String)((Map)localObject3).get("asset");
/*  90: 70 */             localHashMap3.put(localObject7, Long.valueOf(Long.parseLong((String)((Map)localObject3).get("asset quantity"))));
/*  91:    */           }
/*  92: 72 */           for (localObject7 = ((Map)localObject3).entrySet().iterator(); ((Iterator)localObject7).hasNext();)
/*  93:    */           {
/*  94: 72 */             localObject8 = (Map.Entry)((Iterator)localObject7).next();
/*  95: 73 */             localObject9 = (String)((Map.Entry)localObject8).getKey();
/*  96: 74 */             str3 = (String)((Map.Entry)localObject8).getValue();
/*  97: 75 */             if ((str3 != null) && (!"".equals(str3.trim()))) {
/*  98: 78 */               if (isBalance((String)localObject9))
/*  99:    */               {
/* 100: 79 */                 ((Map)localObject5).put(localObject9, Long.valueOf(Long.parseLong(str3)));
/* 101:    */               }
/* 102: 80 */               else if (isDelta((String)localObject9))
/* 103:    */               {
/* 104: 81 */                 long l3 = nullToZero((Long)((Map)localObject5).get(localObject9));
/* 105: 82 */                 ((Map)localObject5).put(localObject9, Long.valueOf(Convert.safeAdd(l3, Long.parseLong(str3))));
/* 106:    */               }
/* 107:    */               else
/* 108:    */               {
/* 109:    */                 String str4;
/* 110: 83 */                 if (isAssetQuantity((String)localObject9))
/* 111:    */                 {
/* 112: 84 */                   str4 = (String)((Map)localObject3).get("asset");
/* 113: 85 */                   localObject10 = (Map)((Map)localObject6).get(str4);
/* 114: 86 */                   if (localObject10 == null)
/* 115:    */                   {
/* 116: 87 */                     localObject10 = new HashMap();
/* 117: 88 */                     ((Map)localObject6).put(str4, localObject10);
/* 118:    */                   }
/* 119: 90 */                   ((Map)localObject10).put(localObject9, Long.valueOf(Long.parseLong(str3)));
/* 120:    */                 }
/* 121: 91 */                 else if (isDeltaAssetQuantity((String)localObject9))
/* 122:    */                 {
/* 123: 92 */                   str4 = (String)((Map)localObject3).get("asset");
/* 124: 93 */                   localObject10 = (Map)((Map)localObject6).get(str4);
/* 125: 94 */                   if (localObject10 == null)
/* 126:    */                   {
/* 127: 95 */                     localObject10 = new HashMap();
/* 128: 96 */                     ((Map)localObject6).put(str4, localObject10);
/* 129:    */                   }
/* 130: 98 */                   long l5 = nullToZero((Long)((Map)localObject10).get(localObject9));
/* 131: 99 */                   ((Map)localObject10).put(localObject9, Long.valueOf(Convert.safeAdd(l5, Long.parseLong(str3))));
/* 132:    */                 }
/* 133:    */               }
/* 134:    */             }
/* 135:    */           }
/* 136:    */         }
/* 137:104 */         Object localObject2 = new HashSet();
/* 138:105 */         for (Object localObject3 = localHashMap1.entrySet().iterator(); ((Iterator)localObject3).hasNext();)
/* 139:    */         {
/* 140:105 */           localObject4 = (Map.Entry)((Iterator)localObject3).next();
/* 141:106 */           localObject5 = (String)((Map.Entry)localObject4).getKey();
/* 142:107 */           localObject6 = (Map)((Map.Entry)localObject4).getValue();
/* 143:108 */           System.out.println("account: " + (String)localObject5);
/* 144:109 */           for (localObject7 = balanceHeaders.iterator(); ((Iterator)localObject7).hasNext();)
/* 145:    */           {
/* 146:109 */             localObject8 = (String)((Iterator)localObject7).next();
/* 147:110 */             System.out.println((String)localObject8 + ": " + nullToZero((Long)((Map)localObject6).get(localObject8)));
/* 148:    */           }
/* 149:112 */           System.out.println("totals:");
/* 150:113 */           l1 = 0L;
/* 151:114 */           for (localObject9 = deltaHeaders.iterator(); ((Iterator)localObject9).hasNext();)
/* 152:    */           {
/* 153:114 */             str3 = (String)((Iterator)localObject9).next();
/* 154:115 */             long l4 = nullToZero((Long)((Map)localObject6).get(str3));
/* 155:116 */             l1 = Convert.safeAdd(l1, l4);
/* 156:117 */             System.out.println(str3 + ": " + l4);
/* 157:    */           }
/* 158:119 */           System.out.println("total confirmed balance change: " + l1);
/* 159:120 */           long l2 = nullToZero((Long)((Map)localObject6).get("balance"));
/* 160:121 */           if (l2 != l1)
/* 161:    */           {
/* 162:122 */             System.out.println("ERROR: balance doesn't match total change!!!");
/* 163:123 */             ((Set)localObject2).add(localObject5);
/* 164:    */           }
/* 165:125 */           Map localMap1 = (Map)localHashMap2.get(localObject5);
/* 166:126 */           for (localObject10 = localMap1.entrySet().iterator(); ((Iterator)localObject10).hasNext();)
/* 167:    */           {
/* 168:126 */             Map.Entry localEntry1 = (Map.Entry)((Iterator)localObject10).next();
/* 169:127 */             String str5 = (String)localEntry1.getKey();
/* 170:128 */             Map localMap2 = (Map)localEntry1.getValue();
/* 171:129 */             System.out.println("asset: " + str5);
/* 172:130 */             for (Map.Entry localEntry2 : localMap2.entrySet()) {
/* 173:131 */               System.out.println((String)localEntry2.getKey() + ": " + localEntry2.getValue());
/* 174:    */             }
/* 175:133 */             long l6 = 0L;
/* 176:134 */             for (String str6 : deltaAssetQuantityHeaders)
/* 177:    */             {
/* 178:135 */               l8 = nullToZero((Long)localMap2.get(str6));
/* 179:136 */               l6 = Convert.safeAdd(l6, l8);
/* 180:    */             }
/* 181:138 */             System.out.println("total confirmed asset quantity change: " + l6);
/* 182:139 */             long l7 = ((Long)localMap2.get("asset balance")).longValue();
/* 183:140 */             if (l7 != l6)
/* 184:    */             {
/* 185:141 */               System.out.println("ERROR: asset balance doesn't match total asset quantity change!!!");
/* 186:142 */               ((Set)localObject2).add(localObject5);
/* 187:    */             }
/* 188:144 */             long l8 = nullToZero((Long)localHashMap4.get(str5));
/* 189:145 */             localHashMap4.put(str5, Long.valueOf(Convert.safeAdd(l8, l7)));
/* 190:    */           }
/* 191:147 */           System.out.println();
/* 192:    */         }
/* 193:    */         long l1;
/* 194:149 */         localObject3 = new HashSet();
/* 195:150 */         for (Object localObject4 = localHashMap3.entrySet().iterator(); ((Iterator)localObject4).hasNext();)
/* 196:    */         {
/* 197:150 */           localObject5 = (Map.Entry)((Iterator)localObject4).next();
/* 198:151 */           localObject6 = (String)((Map.Entry)localObject5).getKey();
/* 199:152 */           l1 = ((Long)((Map.Entry)localObject5).getValue()).longValue();
/* 200:153 */           if (l1 != nullToZero((Long)localHashMap4.get(localObject6)))
/* 201:    */           {
/* 202:154 */             System.out.println("ERROR: asset " + (String)localObject6 + " balances don't match, issued: " + l1 + ", total of account balances: " + localHashMap4.get(localObject6));
/* 203:    */             
/* 204:    */ 
/* 205:157 */             ((Set)localObject3).add(localObject6);
/* 206:    */           }
/* 207:    */         }
/* 208:160 */         if (((Set)localObject2).size() > 0)
/* 209:    */         {
/* 210:161 */           System.out.println("ERROR: " + ((Set)localObject2).size() + " accounts have incorrect balances");
/* 211:162 */           System.out.println(localObject2);
/* 212:    */         }
/* 213:    */         else
/* 214:    */         {
/* 215:164 */           System.out.println("SUCCESS: all " + localHashMap1.size() + " account balances and asset balances match the transaction and trade totals!");
/* 216:    */         }
/* 217:166 */         if (((Set)localObject3).size() > 0)
/* 218:    */         {
/* 219:167 */           System.out.println("ERROR: " + ((Set)localObject3).size() + " assets have incorrect balances");
/* 220:168 */           System.out.println(localObject3);
/* 221:    */         }
/* 222:    */         else
/* 223:    */         {
/* 224:170 */           System.out.println("SUCCESS: all " + localHashMap3.size() + " assets quantities are correct!");
/* 225:    */         }
/* 226:    */       }
/* 227:    */       catch (Throwable localThrowable2)
/* 228:    */       {
/* 229: 42 */         localObject1 = localThrowable2;throw localThrowable2;
/* 230:    */       }
/* 231:    */       finally
/* 232:    */       {
/* 233:173 */         if (localBufferedReader != null) {
/* 234:173 */           if (localObject1 != null) {
/* 235:    */             try
/* 236:    */             {
/* 237:173 */               localBufferedReader.close();
/* 238:    */             }
/* 239:    */             catch (Throwable localThrowable3)
/* 240:    */             {
/* 241:173 */               ((Throwable)localObject1).addSuppressed(localThrowable3);
/* 242:    */             }
/* 243:    */           } else {
/* 244:173 */             localBufferedReader.close();
/* 245:    */           }
/* 246:    */         }
/* 247:    */       }
/* 248:    */     }
/* 249:    */     catch (IOException localIOException)
/* 250:    */     {
/* 251:174 */       System.out.println(localIOException.toString());
/* 252:175 */       throw new RuntimeException(localIOException);
/* 253:    */     }
/* 254:    */   }
/* 255:    */   
/* 256:    */   static
/* 257:    */   {
/* 258:180 */     Logger.init();
/* 259:    */   }
/* 260:    */   
/* 261:183 */   private static final String beginQuote = "^" + DebugTrace.QUOTE;
/* 262:184 */   private static final String endQuote = DebugTrace.QUOTE + "$";
/* 263:    */   
/* 264:    */   private static String[] unquote(String[] paramArrayOfString)
/* 265:    */   {
/* 266:187 */     String[] arrayOfString = new String[paramArrayOfString.length];
/* 267:188 */     for (int i = 0; i < paramArrayOfString.length; i++) {
/* 268:189 */       arrayOfString[i] = paramArrayOfString[i].replaceFirst(beginQuote, "").replaceFirst(endQuote, "");
/* 269:    */     }
/* 270:191 */     return arrayOfString;
/* 271:    */   }
/* 272:    */   
/* 273:    */   private static long nullToZero(Long paramLong)
/* 274:    */   {
/* 275:195 */     return paramLong == null ? 0L : paramLong.longValue();
/* 276:    */   }
/* 277:    */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.VerifyTrace
 * JD-Core Version:    0.7.1
 */