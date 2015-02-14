/*  1:   */ package nxt.http;
/*  2:   */ 
/*  3:   */ import java.math.BigInteger;
/*  4:   */ import java.util.Collection;
/*  5:   */ import java.util.Iterator;
/*  6:   */ import javax.servlet.http.HttpServletRequest;
/*  7:   */ import nxt.Account;
/*  8:   */ import nxt.Alias;
/*  9:   */ import nxt.Asset;
/* 10:   */ import nxt.AssetTransfer;
/* 11:   */ import nxt.Block;
/* 12:   */ import nxt.Blockchain;
/* 13:   */ import nxt.BlockchainProcessor;
/* 14:   */ import nxt.Escrow;
/* 15:   */ import nxt.Generator;
/* 16:   */ import nxt.Nxt;
/* 17:   */ import nxt.Order.Ask;
/* 18:   */ import nxt.Order.Bid;
/* 19:   */ import nxt.Trade;
/* 20:   */ import nxt.db.DbIterator;
/* 21:   */ import nxt.peer.Peer;
/* 22:   */ import nxt.peer.Peers;
/* 23:   */ import org.json.simple.JSONObject;
/* 24:   */ import org.json.simple.JSONStreamAware;
/* 25:   */ 
/* 26:   */ public final class GetState
/* 27:   */   extends APIServlet.APIRequestHandler
/* 28:   */ {
/* 29:23 */   static final GetState instance = new GetState();
/* 30:   */   
/* 31:   */   private GetState()
/* 32:   */   {
/* 33:26 */     super(new APITag[] { APITag.INFO }, new String[] { "includeCounts" });
/* 34:   */   }
/* 35:   */   
/* 36:   */   JSONStreamAware processRequest(HttpServletRequest paramHttpServletRequest)
/* 37:   */   {
/* 38:32 */     JSONObject localJSONObject = new JSONObject();
/* 39:   */     
/* 40:34 */     localJSONObject.put("application", "NRS");
/* 41:35 */     localJSONObject.put("version", "1.2.2");
/* 42:36 */     localJSONObject.put("time", Integer.valueOf(Nxt.getEpochTime()));
/* 43:37 */     localJSONObject.put("lastBlock", Nxt.getBlockchain().getLastBlock().getStringId());
/* 44:38 */     localJSONObject.put("cumulativeDifficulty", Nxt.getBlockchain().getLastBlock().getCumulativeDifficulty().toString());
/* 45:   */     
/* 46:   */ 
/* 47:41 */     long l1 = 0L;
/* 48:42 */     DbIterator localDbIterator = Account.getAllAccounts(0, -1);Object localObject1 = null;
/* 49:   */     Object localObject2;
/* 50:   */     try
/* 51:   */     {
/* 52:43 */       for (localIterator1 = localDbIterator.iterator(); localIterator1.hasNext();)
/* 53:   */       {
/* 54:43 */         localObject2 = (Account)localIterator1.next();
/* 55:44 */         long l2 = ((Account)localObject2).getBalanceNQT();
/* 56:45 */         if (l2 > 0L) {
/* 57:46 */           l1 += l2;
/* 58:   */         }
/* 59:   */       }
/* 60:   */     }
/* 61:   */     catch (Throwable localThrowable2)
/* 62:   */     {
/* 63:   */       Iterator localIterator1;
/* 64:42 */       localObject1 = localThrowable2;throw localThrowable2;
/* 65:   */     }
/* 66:   */     finally
/* 67:   */     {
/* 68:49 */       if (localDbIterator != null) {
/* 69:49 */         if (localObject1 != null) {
/* 70:   */           try
/* 71:   */           {
/* 72:49 */             localDbIterator.close();
/* 73:   */           }
/* 74:   */           catch (Throwable localThrowable5)
/* 75:   */           {
/* 76:49 */             ((Throwable)localObject1).addSuppressed(localThrowable5);
/* 77:   */           }
/* 78:   */         } else {
/* 79:49 */           localDbIterator.close();
/* 80:   */         }
/* 81:   */       }
/* 82:   */     }
/* 83:50 */     localDbIterator = Escrow.getAllEscrowTransactions();localObject1 = null;
/* 84:   */     try
/* 85:   */     {
/* 86:51 */       for (localIterator2 = localDbIterator.iterator(); localIterator2.hasNext();)
/* 87:   */       {
/* 88:51 */         localObject2 = (Escrow)localIterator2.next();
/* 89:52 */         l1 += ((Escrow)localObject2).getAmountNQT().longValue();
/* 90:   */       }
/* 91:   */     }
/* 92:   */     catch (Throwable localThrowable4)
/* 93:   */     {
/* 94:   */       Iterator localIterator2;
/* 95:50 */       localObject1 = localThrowable4;throw localThrowable4;
/* 96:   */     }
/* 97:   */     finally
/* 98:   */     {
/* 99:54 */       if (localDbIterator != null) {
/* :0:54 */         if (localObject1 != null) {
/* :1:   */           try
/* :2:   */           {
/* :3:54 */             localDbIterator.close();
/* :4:   */           }
/* :5:   */           catch (Throwable localThrowable6)
/* :6:   */           {
/* :7:54 */             ((Throwable)localObject1).addSuppressed(localThrowable6);
/* :8:   */           }
/* :9:   */         } else {
/* ;0:54 */           localDbIterator.close();
/* ;1:   */         }
/* ;2:   */       }
/* ;3:   */     }
/* ;4:55 */     localJSONObject.put("totalEffectiveBalanceNXT", Long.valueOf(l1 / 100000000L));
/* ;5:58 */     if (!"false".equalsIgnoreCase(paramHttpServletRequest.getParameter("includeCounts")))
/* ;6:   */     {
/* ;7:59 */       localJSONObject.put("numberOfBlocks", Integer.valueOf(Nxt.getBlockchain().getHeight() + 1));
/* ;8:60 */       localJSONObject.put("numberOfTransactions", Integer.valueOf(Nxt.getBlockchain().getTransactionCount()));
/* ;9:61 */       localJSONObject.put("numberOfAccounts", Integer.valueOf(Account.getCount()));
/* <0:62 */       localJSONObject.put("numberOfAssets", Integer.valueOf(Asset.getCount()));
/* <1:63 */       int i = Order.Ask.getCount();
/* <2:64 */       int j = Order.Bid.getCount();
/* <3:65 */       localJSONObject.put("numberOfOrders", Integer.valueOf(i + j));
/* <4:66 */       localJSONObject.put("numberOfAskOrders", Integer.valueOf(i));
/* <5:67 */       localJSONObject.put("numberOfBidOrders", Integer.valueOf(j));
/* <6:68 */       localJSONObject.put("numberOfTrades", Integer.valueOf(Trade.getCount()));
/* <7:69 */       localJSONObject.put("numberOfTransfers", Integer.valueOf(AssetTransfer.getCount()));
/* <8:70 */       localJSONObject.put("numberOfAliases", Integer.valueOf(Alias.getCount()));
/* <9:   */     }
/* =0:74 */     localJSONObject.put("numberOfPeers", Integer.valueOf(Peers.getAllPeers().size()));
/* =1:75 */     localJSONObject.put("numberOfUnlockedAccounts", Integer.valueOf(Generator.getAllGenerators().size()));
/* =2:76 */     Peer localPeer = Nxt.getBlockchainProcessor().getLastBlockchainFeeder();
/* =3:77 */     localJSONObject.put("lastBlockchainFeeder", localPeer == null ? null : localPeer.getAnnouncedAddress());
/* =4:78 */     localJSONObject.put("lastBlockchainFeederHeight", Integer.valueOf(Nxt.getBlockchainProcessor().getLastBlockchainFeederHeight()));
/* =5:79 */     localJSONObject.put("isScanning", Boolean.valueOf(Nxt.getBlockchainProcessor().isScanning()));
/* =6:80 */     localJSONObject.put("availableProcessors", Integer.valueOf(Runtime.getRuntime().availableProcessors()));
/* =7:81 */     localJSONObject.put("maxMemory", Long.valueOf(Runtime.getRuntime().maxMemory()));
/* =8:82 */     localJSONObject.put("totalMemory", Long.valueOf(Runtime.getRuntime().totalMemory()));
/* =9:83 */     localJSONObject.put("freeMemory", Long.valueOf(Runtime.getRuntime().freeMemory()));
/* >0:   */     
/* >1:85 */     return localJSONObject;
/* >2:   */   }
/* >3:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.http.GetState
 * JD-Core Version:    0.7.1
 */