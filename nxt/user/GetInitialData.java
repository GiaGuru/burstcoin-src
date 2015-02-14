/*   1:    */ package nxt.user;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.math.BigInteger;
/*   5:    */ import java.util.Collection;
/*   6:    */ import java.util.Iterator;
/*   7:    */ import java.util.List;
/*   8:    */ import javax.servlet.http.HttpServletRequest;
/*   9:    */ import nxt.Block;
/*  10:    */ import nxt.Blockchain;
/*  11:    */ import nxt.Nxt;
/*  12:    */ import nxt.Transaction;
/*  13:    */ import nxt.TransactionProcessor;
/*  14:    */ import nxt.db.DbIterator;
/*  15:    */ import nxt.peer.Peer;
/*  16:    */ import nxt.peer.Peer.State;
/*  17:    */ import nxt.peer.Peers;
/*  18:    */ import nxt.util.Convert;
/*  19:    */ import org.json.simple.JSONArray;
/*  20:    */ import org.json.simple.JSONObject;
/*  21:    */ import org.json.simple.JSONStreamAware;
/*  22:    */ 
/*  23:    */ public final class GetInitialData
/*  24:    */   extends UserServlet.UserRequestHandler
/*  25:    */ {
/*  26: 21 */   static final GetInitialData instance = new GetInitialData();
/*  27:    */   
/*  28:    */   JSONStreamAware processRequest(HttpServletRequest paramHttpServletRequest, User paramUser)
/*  29:    */     throws IOException
/*  30:    */   {
/*  31: 28 */     JSONArray localJSONArray1 = new JSONArray();
/*  32: 29 */     JSONArray localJSONArray2 = new JSONArray();JSONArray localJSONArray3 = new JSONArray();JSONArray localJSONArray4 = new JSONArray();
/*  33: 30 */     JSONArray localJSONArray5 = new JSONArray();
/*  34:    */     
/*  35: 32 */     Object localObject1 = Nxt.getTransactionProcessor().getAllUnconfirmedTransactions();Object localObject2 = null;
/*  36:    */     Object localObject4;
/*  37:    */     try
/*  38:    */     {
/*  39: 33 */       while (((DbIterator)localObject1).hasNext())
/*  40:    */       {
/*  41: 34 */         Transaction localTransaction = (Transaction)((DbIterator)localObject1).next();
/*  42:    */         
/*  43: 36 */         localObject4 = new JSONObject();
/*  44: 37 */         ((JSONObject)localObject4).put("index", Integer.valueOf(Users.getIndex(localTransaction)));
/*  45: 38 */         ((JSONObject)localObject4).put("timestamp", Integer.valueOf(localTransaction.getTimestamp()));
/*  46: 39 */         ((JSONObject)localObject4).put("deadline", Short.valueOf(localTransaction.getDeadline()));
/*  47: 40 */         ((JSONObject)localObject4).put("recipient", Convert.toUnsignedLong(localTransaction.getRecipientId()));
/*  48: 41 */         ((JSONObject)localObject4).put("amountNQT", Long.valueOf(localTransaction.getAmountNQT()));
/*  49: 42 */         ((JSONObject)localObject4).put("feeNQT", Long.valueOf(localTransaction.getFeeNQT()));
/*  50: 43 */         ((JSONObject)localObject4).put("sender", Convert.toUnsignedLong(localTransaction.getSenderId()));
/*  51: 44 */         ((JSONObject)localObject4).put("id", localTransaction.getStringId());
/*  52:    */         
/*  53: 46 */         localJSONArray1.add(localObject4);
/*  54:    */       }
/*  55:    */     }
/*  56:    */     catch (Throwable localThrowable2)
/*  57:    */     {
/*  58: 32 */       localObject2 = localThrowable2;throw localThrowable2;
/*  59:    */     }
/*  60:    */     finally
/*  61:    */     {
/*  62: 48 */       if (localObject1 != null) {
/*  63: 48 */         if (localObject2 != null) {
/*  64:    */           try
/*  65:    */           {
/*  66: 48 */             ((DbIterator)localObject1).close();
/*  67:    */           }
/*  68:    */           catch (Throwable localThrowable5)
/*  69:    */           {
/*  70: 48 */             ((Throwable)localObject2).addSuppressed(localThrowable5);
/*  71:    */           }
/*  72:    */         } else {
/*  73: 48 */           ((DbIterator)localObject1).close();
/*  74:    */         }
/*  75:    */       }
/*  76:    */     }
/*  77: 50 */     for (localObject1 = Peers.getAllPeers().iterator(); ((Iterator)localObject1).hasNext();)
/*  78:    */     {
/*  79: 50 */       localObject2 = (Peer)((Iterator)localObject1).next();
/*  80: 52 */       if (((Peer)localObject2).isBlacklisted())
/*  81:    */       {
/*  82: 54 */         localObject3 = new JSONObject();
/*  83: 55 */         ((JSONObject)localObject3).put("index", Integer.valueOf(Users.getIndex((Peer)localObject2)));
/*  84: 56 */         ((JSONObject)localObject3).put("address", ((Peer)localObject2).getPeerAddress());
/*  85: 57 */         ((JSONObject)localObject3).put("announcedAddress", Convert.truncate(((Peer)localObject2).getAnnouncedAddress(), "-", 25, true));
/*  86: 58 */         ((JSONObject)localObject3).put("software", ((Peer)localObject2).getSoftware());
/*  87: 59 */         if (((Peer)localObject2).isWellKnown()) {
/*  88: 60 */           ((JSONObject)localObject3).put("wellKnown", Boolean.valueOf(true));
/*  89:    */         }
/*  90: 62 */         localJSONArray4.add(localObject3);
/*  91:    */       }
/*  92: 64 */       else if (((Peer)localObject2).getState() == Peer.State.NON_CONNECTED)
/*  93:    */       {
/*  94: 66 */         localObject3 = new JSONObject();
/*  95: 67 */         ((JSONObject)localObject3).put("index", Integer.valueOf(Users.getIndex((Peer)localObject2)));
/*  96: 68 */         ((JSONObject)localObject3).put("address", ((Peer)localObject2).getPeerAddress());
/*  97: 69 */         ((JSONObject)localObject3).put("announcedAddress", Convert.truncate(((Peer)localObject2).getAnnouncedAddress(), "-", 25, true));
/*  98: 70 */         ((JSONObject)localObject3).put("software", ((Peer)localObject2).getSoftware());
/*  99: 71 */         if (((Peer)localObject2).isWellKnown()) {
/* 100: 72 */           ((JSONObject)localObject3).put("wellKnown", Boolean.valueOf(true));
/* 101:    */         }
/* 102: 74 */         localJSONArray3.add(localObject3);
/* 103:    */       }
/* 104:    */       else
/* 105:    */       {
/* 106: 78 */         localObject3 = new JSONObject();
/* 107: 79 */         ((JSONObject)localObject3).put("index", Integer.valueOf(Users.getIndex((Peer)localObject2)));
/* 108: 80 */         if (((Peer)localObject2).getState() == Peer.State.DISCONNECTED) {
/* 109: 81 */           ((JSONObject)localObject3).put("disconnected", Boolean.valueOf(true));
/* 110:    */         }
/* 111: 83 */         ((JSONObject)localObject3).put("address", ((Peer)localObject2).getPeerAddress());
/* 112: 84 */         ((JSONObject)localObject3).put("announcedAddress", Convert.truncate(((Peer)localObject2).getAnnouncedAddress(), "-", 25, true));
/* 113: 85 */         ((JSONObject)localObject3).put("weight", Integer.valueOf(((Peer)localObject2).getWeight()));
/* 114: 86 */         ((JSONObject)localObject3).put("downloaded", Long.valueOf(((Peer)localObject2).getDownloadedVolume()));
/* 115: 87 */         ((JSONObject)localObject3).put("uploaded", Long.valueOf(((Peer)localObject2).getUploadedVolume()));
/* 116: 88 */         ((JSONObject)localObject3).put("software", ((Peer)localObject2).getSoftware());
/* 117: 89 */         if (((Peer)localObject2).isWellKnown()) {
/* 118: 90 */           ((JSONObject)localObject3).put("wellKnown", Boolean.valueOf(true));
/* 119:    */         }
/* 120: 92 */         localJSONArray2.add(localObject3);
/* 121:    */       }
/* 122:    */     }
/* 123:    */     Object localObject3;
/* 124: 96 */     localObject1 = Nxt.getBlockchain().getBlocks(0, 59);localObject2 = null;
/* 125:    */     try
/* 126:    */     {
/* 127: 97 */       for (localObject3 = ((DbIterator)localObject1).iterator(); ((Iterator)localObject3).hasNext();)
/* 128:    */       {
/* 129: 97 */         localObject4 = (Block)((Iterator)localObject3).next();
/* 130: 98 */         JSONObject localJSONObject = new JSONObject();
/* 131: 99 */         localJSONObject.put("index", Integer.valueOf(Users.getIndex((Block)localObject4)));
/* 132:100 */         localJSONObject.put("timestamp", Integer.valueOf(((Block)localObject4).getTimestamp()));
/* 133:101 */         localJSONObject.put("numberOfTransactions", Integer.valueOf(((Block)localObject4).getTransactions().size()));
/* 134:102 */         localJSONObject.put("totalAmountNQT", Long.valueOf(((Block)localObject4).getTotalAmountNQT()));
/* 135:103 */         localJSONObject.put("totalFeeNQT", Long.valueOf(((Block)localObject4).getTotalFeeNQT()));
/* 136:104 */         localJSONObject.put("payloadLength", Integer.valueOf(((Block)localObject4).getPayloadLength()));
/* 137:105 */         localJSONObject.put("generator", Convert.toUnsignedLong(((Block)localObject4).getGeneratorId()));
/* 138:106 */         localJSONObject.put("height", Integer.valueOf(((Block)localObject4).getHeight()));
/* 139:107 */         localJSONObject.put("version", Integer.valueOf(((Block)localObject4).getVersion()));
/* 140:108 */         localJSONObject.put("block", ((Block)localObject4).getStringId());
/* 141:109 */         localJSONObject.put("baseTarget", BigInteger.valueOf(((Block)localObject4).getBaseTarget()).multiply(BigInteger.valueOf(100000L)).divide(BigInteger.valueOf(18325193796L)));
/* 142:    */         
/* 143:    */ 
/* 144:112 */         localJSONArray5.add(localJSONObject);
/* 145:    */       }
/* 146:    */     }
/* 147:    */     catch (Throwable localThrowable4)
/* 148:    */     {
/* 149: 96 */       localObject2 = localThrowable4;throw localThrowable4;
/* 150:    */     }
/* 151:    */     finally
/* 152:    */     {
/* 153:114 */       if (localObject1 != null) {
/* 154:114 */         if (localObject2 != null) {
/* 155:    */           try
/* 156:    */           {
/* 157:114 */             ((DbIterator)localObject1).close();
/* 158:    */           }
/* 159:    */           catch (Throwable localThrowable6)
/* 160:    */           {
/* 161:114 */             ((Throwable)localObject2).addSuppressed(localThrowable6);
/* 162:    */           }
/* 163:    */         } else {
/* 164:114 */           ((DbIterator)localObject1).close();
/* 165:    */         }
/* 166:    */       }
/* 167:    */     }
/* 168:116 */     localObject1 = new JSONObject();
/* 169:117 */     ((JSONObject)localObject1).put("response", "processInitialData");
/* 170:118 */     ((JSONObject)localObject1).put("version", "1.2.2");
/* 171:119 */     if (localJSONArray1.size() > 0) {
/* 172:120 */       ((JSONObject)localObject1).put("unconfirmedTransactions", localJSONArray1);
/* 173:    */     }
/* 174:122 */     if (localJSONArray2.size() > 0) {
/* 175:123 */       ((JSONObject)localObject1).put("activePeers", localJSONArray2);
/* 176:    */     }
/* 177:125 */     if (localJSONArray3.size() > 0) {
/* 178:126 */       ((JSONObject)localObject1).put("knownPeers", localJSONArray3);
/* 179:    */     }
/* 180:128 */     if (localJSONArray4.size() > 0) {
/* 181:129 */       ((JSONObject)localObject1).put("blacklistedPeers", localJSONArray4);
/* 182:    */     }
/* 183:131 */     if (localJSONArray5.size() > 0) {
/* 184:132 */       ((JSONObject)localObject1).put("recentBlocks", localJSONArray5);
/* 185:    */     }
/* 186:135 */     return (JSONStreamAware)localObject1;
/* 187:    */   }
/* 188:    */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.user.GetInitialData
 * JD-Core Version:    0.7.1
 */