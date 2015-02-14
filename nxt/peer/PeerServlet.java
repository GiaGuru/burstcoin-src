/*   1:    */ package nxt.peer;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.InputStream;
/*   5:    */ import java.io.InputStreamReader;
/*   6:    */ import java.io.OutputStream;
/*   7:    */ import java.io.OutputStreamWriter;
/*   8:    */ import java.io.Reader;
/*   9:    */ import java.io.Writer;
/*  10:    */ import java.util.Collections;
/*  11:    */ import java.util.HashMap;
/*  12:    */ import java.util.Map;
/*  13:    */ import javax.servlet.ServletConfig;
/*  14:    */ import javax.servlet.ServletException;
/*  15:    */ import javax.servlet.http.HttpServlet;
/*  16:    */ import javax.servlet.http.HttpServletRequest;
/*  17:    */ import javax.servlet.http.HttpServletResponse;
/*  18:    */ import nxt.util.CountingInputStream;
/*  19:    */ import nxt.util.CountingOutputStream;
/*  20:    */ import nxt.util.JSON;
/*  21:    */ import nxt.util.Logger;
/*  22:    */ import org.eclipse.jetty.server.Response;
/*  23:    */ import org.eclipse.jetty.servlets.gzip.CompressedResponseWrapper;
/*  24:    */ import org.json.simple.JSONObject;
/*  25:    */ import org.json.simple.JSONStreamAware;
/*  26:    */ import org.json.simple.JSONValue;
/*  27:    */ 
/*  28:    */ public final class PeerServlet
/*  29:    */   extends HttpServlet
/*  30:    */ {
/*  31:    */   private static final Map<String, PeerRequestHandler> peerRequestHandlers;
/*  32:    */   private static final JSONStreamAware UNSUPPORTED_REQUEST_TYPE;
/*  33:    */   private static final JSONStreamAware UNSUPPORTED_PROTOCOL;
/*  34:    */   private boolean isGzipEnabled;
/*  35:    */   
/*  36:    */   static
/*  37:    */   {
/*  38: 36 */     Object localObject = new HashMap();
/*  39: 37 */     ((Map)localObject).put("addPeers", AddPeers.instance);
/*  40: 38 */     ((Map)localObject).put("getCumulativeDifficulty", GetCumulativeDifficulty.instance);
/*  41: 39 */     ((Map)localObject).put("getInfo", GetInfo.instance);
/*  42: 40 */     ((Map)localObject).put("getMilestoneBlockIds", GetMilestoneBlockIds.instance);
/*  43: 41 */     ((Map)localObject).put("getNextBlockIds", GetNextBlockIds.instance);
/*  44: 42 */     ((Map)localObject).put("getNextBlocks", GetNextBlocks.instance);
/*  45: 43 */     ((Map)localObject).put("getPeers", GetPeers.instance);
/*  46: 44 */     ((Map)localObject).put("getUnconfirmedTransactions", GetUnconfirmedTransactions.instance);
/*  47: 45 */     ((Map)localObject).put("processBlock", ProcessBlock.instance);
/*  48: 46 */     ((Map)localObject).put("processTransactions", ProcessTransactions.instance);
/*  49: 47 */     ((Map)localObject).put("getAccountBalance", GetAccountBalance.instance);
/*  50: 48 */     ((Map)localObject).put("getAccountRecentTransactions", GetAccountRecentTransactions.instance);
/*  51: 49 */     peerRequestHandlers = Collections.unmodifiableMap((Map)localObject);
/*  52:    */     
/*  53:    */ 
/*  54:    */ 
/*  55:    */ 
/*  56: 54 */     localObject = new JSONObject();
/*  57: 55 */     ((JSONObject)localObject).put("error", "Unsupported request type!");
/*  58: 56 */     UNSUPPORTED_REQUEST_TYPE = JSON.prepare((JSONObject)localObject);
/*  59:    */     
/*  60:    */ 
/*  61:    */ 
/*  62:    */ 
/*  63: 61 */     localObject = new JSONObject();
/*  64: 62 */     ((JSONObject)localObject).put("error", "Unsupported protocol!");
/*  65: 63 */     UNSUPPORTED_PROTOCOL = JSON.prepare((JSONObject)localObject);
/*  66:    */   }
/*  67:    */   
/*  68:    */   public void init(ServletConfig paramServletConfig)
/*  69:    */     throws ServletException
/*  70:    */   {
/*  71: 70 */     super.init(paramServletConfig);
/*  72: 71 */     this.isGzipEnabled = Boolean.parseBoolean(paramServletConfig.getInitParameter("isGzipEnabled"));
/*  73:    */   }
/*  74:    */   
/*  75:    */   protected void doPost(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
/*  76:    */     throws ServletException, IOException
/*  77:    */   {
/*  78: 77 */     PeerImpl localPeerImpl = null;
/*  79:    */     Object localObject3;
/*  80:    */     Object localObject4;
/*  81:    */     Object localObject1;
/*  82:    */     try
/*  83:    */     {
/*  84: 81 */       localPeerImpl = Peers.addPeer(paramHttpServletRequest.getRemoteAddr(), null);
/*  85: 82 */       if (localPeerImpl == null) {
/*  86: 83 */         return;
/*  87:    */       }
/*  88: 85 */       if (localPeerImpl.isBlacklisted()) {
/*  89: 86 */         return;
/*  90:    */       }
/*  91: 90 */       localObject2 = new CountingInputStream(paramHttpServletRequest.getInputStream());
/*  92: 91 */       localObject3 = new InputStreamReader((InputStream)localObject2, "UTF-8");localObject4 = null;
/*  93:    */       JSONObject localJSONObject;
/*  94:    */       try
/*  95:    */       {
/*  96: 92 */         localJSONObject = (JSONObject)JSONValue.parse((Reader)localObject3);
/*  97:    */       }
/*  98:    */       catch (Throwable localThrowable2)
/*  99:    */       {
/* 100: 91 */         localObject4 = localThrowable2;throw localThrowable2;
/* 101:    */       }
/* 102:    */       finally
/* 103:    */       {
/* 104: 93 */         if (localObject3 != null) {
/* 105: 93 */           if (localObject4 != null) {
/* 106:    */             try
/* 107:    */             {
/* 108: 93 */               ((Reader)localObject3).close();
/* 109:    */             }
/* 110:    */             catch (Throwable localThrowable7)
/* 111:    */             {
/* 112: 93 */               ((Throwable)localObject4).addSuppressed(localThrowable7);
/* 113:    */             }
/* 114:    */           } else {
/* 115: 93 */             ((Reader)localObject3).close();
/* 116:    */           }
/* 117:    */         }
/* 118:    */       }
/* 119: 94 */       if (localJSONObject == null) {
/* 120: 95 */         return;
/* 121:    */       }
/* 122: 98 */       if (localPeerImpl.getState() == Peer.State.DISCONNECTED)
/* 123:    */       {
/* 124: 99 */         localPeerImpl.setState(Peer.State.CONNECTED);
/* 125:100 */         if (localPeerImpl.getAnnouncedAddress() != null) {
/* 126:101 */           Peers.updateAddress(localPeerImpl);
/* 127:    */         }
/* 128:    */       }
/* 129:104 */       localPeerImpl.updateDownloadedVolume(((CountingInputStream)localObject2).getCount());
/* 130:105 */       if (!localPeerImpl.analyzeHallmark(localPeerImpl.getPeerAddress(), (String)localJSONObject.get("hallmark")))
/* 131:    */       {
/* 132:106 */         localPeerImpl.blacklist();
/* 133:107 */         return;
/* 134:    */       }
/* 135:110 */       if ((localJSONObject.get("protocol") != null) && (((String)localJSONObject.get("protocol")).equals("B1")))
/* 136:    */       {
/* 137:111 */         localObject3 = (PeerRequestHandler)peerRequestHandlers.get(localJSONObject.get("requestType"));
/* 138:112 */         if (localObject3 != null) {
/* 139:113 */           localObject1 = ((PeerRequestHandler)localObject3).processRequest(localJSONObject, localPeerImpl);
/* 140:    */         } else {
/* 141:115 */           localObject1 = UNSUPPORTED_REQUEST_TYPE;
/* 142:    */         }
/* 143:    */       }
/* 144:    */       else
/* 145:    */       {
/* 146:118 */         Logger.logDebugMessage("Unsupported protocol " + localJSONObject.get("protocol"));
/* 147:119 */         localObject1 = UNSUPPORTED_PROTOCOL;
/* 148:    */       }
/* 149:    */     }
/* 150:    */     catch (RuntimeException localRuntimeException)
/* 151:    */     {
/* 152:123 */       Logger.logDebugMessage("Error processing POST request", localRuntimeException);
/* 153:124 */       Object localObject2 = new JSONObject();
/* 154:125 */       ((JSONObject)localObject2).put("error", localRuntimeException.toString());
/* 155:126 */       localObject1 = localObject2;
/* 156:    */     }
/* 157:129 */     paramHttpServletResponse.setContentType("text/plain; charset=UTF-8");
/* 158:    */     try
/* 159:    */     {
/* 160:    */       long l;
/* 161:132 */       if (this.isGzipEnabled)
/* 162:    */       {
/* 163:133 */         localObject3 = new OutputStreamWriter(paramHttpServletResponse.getOutputStream(), "UTF-8");localObject4 = null;
/* 164:    */         try
/* 165:    */         {
/* 166:134 */           ((JSONStreamAware)localObject1).writeJSONString((Writer)localObject3);
/* 167:    */         }
/* 168:    */         catch (Throwable localThrowable4)
/* 169:    */         {
/* 170:133 */           localObject4 = localThrowable4;throw localThrowable4;
/* 171:    */         }
/* 172:    */         finally
/* 173:    */         {
/* 174:135 */           if (localObject3 != null) {
/* 175:135 */             if (localObject4 != null) {
/* 176:    */               try
/* 177:    */               {
/* 178:135 */                 ((Writer)localObject3).close();
/* 179:    */               }
/* 180:    */               catch (Throwable localThrowable8)
/* 181:    */               {
/* 182:135 */                 ((Throwable)localObject4).addSuppressed(localThrowable8);
/* 183:    */               }
/* 184:    */             } else {
/* 185:135 */               ((Writer)localObject3).close();
/* 186:    */             }
/* 187:    */           }
/* 188:    */         }
/* 189:136 */         l = ((Response)((CompressedResponseWrapper)paramHttpServletResponse).getResponse()).getContentCount();
/* 190:    */       }
/* 191:    */       else
/* 192:    */       {
/* 193:138 */         localObject3 = new CountingOutputStream(paramHttpServletResponse.getOutputStream());
/* 194:139 */         localObject4 = new OutputStreamWriter((OutputStream)localObject3, "UTF-8");Object localObject5 = null;
/* 195:    */         try
/* 196:    */         {
/* 197:140 */           ((JSONStreamAware)localObject1).writeJSONString((Writer)localObject4);
/* 198:    */         }
/* 199:    */         catch (Throwable localThrowable6)
/* 200:    */         {
/* 201:139 */           localObject5 = localThrowable6;throw localThrowable6;
/* 202:    */         }
/* 203:    */         finally
/* 204:    */         {
/* 205:141 */           if (localObject4 != null) {
/* 206:141 */             if (localObject5 != null) {
/* 207:    */               try
/* 208:    */               {
/* 209:141 */                 ((Writer)localObject4).close();
/* 210:    */               }
/* 211:    */               catch (Throwable localThrowable9)
/* 212:    */               {
/* 213:141 */                 ((Throwable)localObject5).addSuppressed(localThrowable9);
/* 214:    */               }
/* 215:    */             } else {
/* 216:141 */               ((Writer)localObject4).close();
/* 217:    */             }
/* 218:    */           }
/* 219:    */         }
/* 220:142 */         l = ((CountingOutputStream)localObject3).getCount();
/* 221:    */       }
/* 222:144 */       if (localPeerImpl != null) {
/* 223:145 */         localPeerImpl.updateUploadedVolume(l);
/* 224:    */       }
/* 225:    */     }
/* 226:    */     catch (Exception localException)
/* 227:    */     {
/* 228:148 */       if (localPeerImpl != null) {
/* 229:149 */         localPeerImpl.blacklist(localException);
/* 230:    */       }
/* 231:151 */       throw localException;
/* 232:    */     }
/* 233:    */   }
/* 234:    */   
/* 235:    */   static abstract class PeerRequestHandler
/* 236:    */   {
/* 237:    */     abstract JSONStreamAware processRequest(JSONObject paramJSONObject, Peer paramPeer);
/* 238:    */   }
/* 239:    */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.peer.PeerServlet
 * JD-Core Version:    0.7.1
 */