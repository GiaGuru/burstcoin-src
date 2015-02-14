/*   1:    */ package nxt.user;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.PrintWriter;
/*   5:    */ import java.io.Writer;
/*   6:    */ import java.util.concurrent.ConcurrentLinkedQueue;
/*   7:    */ import javax.servlet.AsyncContext;
/*   8:    */ import javax.servlet.AsyncEvent;
/*   9:    */ import javax.servlet.AsyncListener;
/*  10:    */ import javax.servlet.ServletResponse;
/*  11:    */ import javax.servlet.http.HttpServletRequest;
/*  12:    */ import javax.servlet.http.HttpServletResponse;
/*  13:    */ import nxt.Generator;
/*  14:    */ import nxt.crypto.Crypto;
/*  15:    */ import nxt.util.JSON;
/*  16:    */ import nxt.util.Logger;
/*  17:    */ import org.json.simple.JSONArray;
/*  18:    */ import org.json.simple.JSONObject;
/*  19:    */ import org.json.simple.JSONStreamAware;
/*  20:    */ 
/*  21:    */ final class User
/*  22:    */ {
/*  23:    */   private volatile String secretPhrase;
/*  24:    */   private volatile byte[] publicKey;
/*  25:    */   private volatile boolean isInactive;
/*  26:    */   private final String userId;
/*  27: 26 */   private final ConcurrentLinkedQueue<JSONStreamAware> pendingResponses = new ConcurrentLinkedQueue();
/*  28:    */   private AsyncContext asyncContext;
/*  29:    */   
/*  30:    */   User(String paramString)
/*  31:    */   {
/*  32: 30 */     this.userId = paramString;
/*  33:    */   }
/*  34:    */   
/*  35:    */   String getUserId()
/*  36:    */   {
/*  37: 34 */     return this.userId;
/*  38:    */   }
/*  39:    */   
/*  40:    */   byte[] getPublicKey()
/*  41:    */   {
/*  42: 38 */     return this.publicKey;
/*  43:    */   }
/*  44:    */   
/*  45:    */   String getSecretPhrase()
/*  46:    */   {
/*  47: 42 */     return this.secretPhrase;
/*  48:    */   }
/*  49:    */   
/*  50:    */   boolean isInactive()
/*  51:    */   {
/*  52: 46 */     return this.isInactive;
/*  53:    */   }
/*  54:    */   
/*  55:    */   void setInactive(boolean paramBoolean)
/*  56:    */   {
/*  57: 50 */     this.isInactive = paramBoolean;
/*  58:    */   }
/*  59:    */   
/*  60:    */   void enqueue(JSONStreamAware paramJSONStreamAware)
/*  61:    */   {
/*  62: 54 */     this.pendingResponses.offer(paramJSONStreamAware);
/*  63:    */   }
/*  64:    */   
/*  65:    */   void lockAccount()
/*  66:    */   {
/*  67: 58 */     Generator.stopForging(this.secretPhrase);
/*  68: 59 */     this.secretPhrase = null;
/*  69:    */   }
/*  70:    */   
/*  71:    */   long unlockAccount(String paramString)
/*  72:    */   {
/*  73: 63 */     this.publicKey = Crypto.getPublicKey(paramString);
/*  74: 64 */     this.secretPhrase = paramString;
/*  75: 65 */     return Generator.startForging(paramString).getAccountId().longValue();
/*  76:    */   }
/*  77:    */   
/*  78:    */   synchronized void processPendingResponses(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
/*  79:    */     throws IOException
/*  80:    */   {
/*  81: 69 */     JSONArray localJSONArray = new JSONArray();
/*  82:    */     JSONStreamAware localJSONStreamAware;
/*  83: 71 */     while ((localJSONStreamAware = (JSONStreamAware)this.pendingResponses.poll()) != null) {
/*  84: 72 */       localJSONArray.add(localJSONStreamAware);
/*  85:    */     }
/*  86:    */     Object localObject1;
/*  87:    */     Object localObject2;
/*  88: 74 */     if (localJSONArray.size() > 0)
/*  89:    */     {
/*  90: 75 */       localObject1 = new JSONObject();
/*  91: 76 */       ((JSONObject)localObject1).put("responses", localJSONArray);
/*  92:    */       Object localObject3;
/*  93: 77 */       if (this.asyncContext != null)
/*  94:    */       {
/*  95: 78 */         this.asyncContext.getResponse().setContentType("text/plain; charset=UTF-8");
/*  96: 79 */         localObject2 = this.asyncContext.getResponse().getWriter();localObject3 = null;
/*  97:    */         try
/*  98:    */         {
/*  99: 80 */           ((JSONObject)localObject1).writeJSONString((Writer)localObject2);
/* 100:    */         }
/* 101:    */         catch (Throwable localThrowable4)
/* 102:    */         {
/* 103: 79 */           localObject3 = localThrowable4;throw localThrowable4;
/* 104:    */         }
/* 105:    */         finally
/* 106:    */         {
/* 107: 81 */           if (localObject2 != null) {
/* 108: 81 */             if (localObject3 != null) {
/* 109:    */               try
/* 110:    */               {
/* 111: 81 */                 ((Writer)localObject2).close();
/* 112:    */               }
/* 113:    */               catch (Throwable localThrowable7)
/* 114:    */               {
/* 115: 81 */                 ((Throwable)localObject3).addSuppressed(localThrowable7);
/* 116:    */               }
/* 117:    */             } else {
/* 118: 81 */               ((Writer)localObject2).close();
/* 119:    */             }
/* 120:    */           }
/* 121:    */         }
/* 122: 82 */         this.asyncContext.complete();
/* 123: 83 */         this.asyncContext = paramHttpServletRequest.startAsync();
/* 124: 84 */         this.asyncContext.addListener(new UserAsyncListener(null));
/* 125: 85 */         this.asyncContext.setTimeout(5000L);
/* 126:    */       }
/* 127:    */       else
/* 128:    */       {
/* 129: 87 */         paramHttpServletResponse.setContentType("text/plain; charset=UTF-8");
/* 130: 88 */         localObject2 = paramHttpServletResponse.getWriter();localObject3 = null;
/* 131:    */         try
/* 132:    */         {
/* 133: 89 */           ((JSONObject)localObject1).writeJSONString((Writer)localObject2);
/* 134:    */         }
/* 135:    */         catch (Throwable localThrowable6)
/* 136:    */         {
/* 137: 88 */           localObject3 = localThrowable6;throw localThrowable6;
/* 138:    */         }
/* 139:    */         finally
/* 140:    */         {
/* 141: 90 */           if (localObject2 != null) {
/* 142: 90 */             if (localObject3 != null) {
/* 143:    */               try
/* 144:    */               {
/* 145: 90 */                 ((Writer)localObject2).close();
/* 146:    */               }
/* 147:    */               catch (Throwable localThrowable8)
/* 148:    */               {
/* 149: 90 */                 ((Throwable)localObject3).addSuppressed(localThrowable8);
/* 150:    */               }
/* 151:    */             } else {
/* 152: 90 */               ((Writer)localObject2).close();
/* 153:    */             }
/* 154:    */           }
/* 155:    */         }
/* 156:    */       }
/* 157:    */     }
/* 158:    */     else
/* 159:    */     {
/* 160: 93 */       if (this.asyncContext != null)
/* 161:    */       {
/* 162: 94 */         this.asyncContext.getResponse().setContentType("text/plain; charset=UTF-8");
/* 163: 95 */         localObject1 = this.asyncContext.getResponse().getWriter();localObject2 = null;
/* 164:    */         try
/* 165:    */         {
/* 166: 96 */           JSON.emptyJSON.writeJSONString((Writer)localObject1);
/* 167:    */         }
/* 168:    */         catch (Throwable localThrowable2)
/* 169:    */         {
/* 170: 95 */           localObject2 = localThrowable2;throw localThrowable2;
/* 171:    */         }
/* 172:    */         finally
/* 173:    */         {
/* 174: 97 */           if (localObject1 != null) {
/* 175: 97 */             if (localObject2 != null) {
/* 176:    */               try
/* 177:    */               {
/* 178: 97 */                 ((Writer)localObject1).close();
/* 179:    */               }
/* 180:    */               catch (Throwable localThrowable9)
/* 181:    */               {
/* 182: 97 */                 ((Throwable)localObject2).addSuppressed(localThrowable9);
/* 183:    */               }
/* 184:    */             } else {
/* 185: 97 */               ((Writer)localObject1).close();
/* 186:    */             }
/* 187:    */           }
/* 188:    */         }
/* 189: 98 */         this.asyncContext.complete();
/* 190:    */       }
/* 191:100 */       this.asyncContext = paramHttpServletRequest.startAsync();
/* 192:101 */       this.asyncContext.addListener(new UserAsyncListener(null));
/* 193:102 */       this.asyncContext.setTimeout(5000L);
/* 194:    */     }
/* 195:    */   }
/* 196:    */   
/* 197:    */   synchronized void send(JSONStreamAware paramJSONStreamAware)
/* 198:    */   {
/* 199:107 */     if (this.asyncContext == null)
/* 200:    */     {
/* 201:109 */       if (this.isInactive) {
/* 202:111 */         return;
/* 203:    */       }
/* 204:113 */       if (this.pendingResponses.size() > 1000)
/* 205:    */       {
/* 206:114 */         this.pendingResponses.clear();
/* 207:    */         
/* 208:116 */         this.isInactive = true;
/* 209:117 */         if (this.secretPhrase == null) {
/* 210:119 */           Users.remove(this);
/* 211:    */         }
/* 212:121 */         return;
/* 213:    */       }
/* 214:124 */       this.pendingResponses.offer(paramJSONStreamAware);
/* 215:    */     }
/* 216:    */     else
/* 217:    */     {
/* 218:128 */       JSONArray localJSONArray = new JSONArray();
/* 219:    */       JSONStreamAware localJSONStreamAware;
/* 220:130 */       while ((localJSONStreamAware = (JSONStreamAware)this.pendingResponses.poll()) != null) {
/* 221:132 */         localJSONArray.add(localJSONStreamAware);
/* 222:    */       }
/* 223:135 */       localJSONArray.add(paramJSONStreamAware);
/* 224:    */       
/* 225:137 */       JSONObject localJSONObject = new JSONObject();
/* 226:138 */       localJSONObject.put("responses", localJSONArray);
/* 227:    */       
/* 228:140 */       this.asyncContext.getResponse().setContentType("text/plain; charset=UTF-8");
/* 229:    */       try
/* 230:    */       {
/* 231:142 */         PrintWriter localPrintWriter = this.asyncContext.getResponse().getWriter();Object localObject1 = null;
/* 232:    */         try
/* 233:    */         {
/* 234:143 */           localJSONObject.writeJSONString(localPrintWriter);
/* 235:    */         }
/* 236:    */         catch (Throwable localThrowable2)
/* 237:    */         {
/* 238:142 */           localObject1 = localThrowable2;throw localThrowable2;
/* 239:    */         }
/* 240:    */         finally
/* 241:    */         {
/* 242:144 */           if (localPrintWriter != null) {
/* 243:144 */             if (localObject1 != null) {
/* 244:    */               try
/* 245:    */               {
/* 246:144 */                 localPrintWriter.close();
/* 247:    */               }
/* 248:    */               catch (Throwable localThrowable3)
/* 249:    */               {
/* 250:144 */                 ((Throwable)localObject1).addSuppressed(localThrowable3);
/* 251:    */               }
/* 252:    */             } else {
/* 253:144 */               localPrintWriter.close();
/* 254:    */             }
/* 255:    */           }
/* 256:    */         }
/* 257:    */       }
/* 258:    */       catch (IOException localIOException)
/* 259:    */       {
/* 260:145 */         Logger.logMessage("Error sending response to user", localIOException);
/* 261:    */       }
/* 262:148 */       this.asyncContext.complete();
/* 263:149 */       this.asyncContext = null;
/* 264:    */     }
/* 265:    */   }
/* 266:    */   
/* 267:    */   private final class UserAsyncListener
/* 268:    */     implements AsyncListener
/* 269:    */   {
/* 270:    */     private UserAsyncListener() {}
/* 271:    */     
/* 272:    */     public void onComplete(AsyncEvent paramAsyncEvent)
/* 273:    */       throws IOException
/* 274:    */     {}
/* 275:    */     
/* 276:    */     public void onError(AsyncEvent paramAsyncEvent)
/* 277:    */       throws IOException
/* 278:    */     {
/* 279:164 */       synchronized (User.this)
/* 280:    */       {
/* 281:165 */         User.this.asyncContext.getResponse().setContentType("text/plain; charset=UTF-8");
/* 282:    */         
/* 283:167 */         PrintWriter localPrintWriter = User.this.asyncContext.getResponse().getWriter();Object localObject1 = null;
/* 284:    */         try
/* 285:    */         {
/* 286:168 */           JSON.emptyJSON.writeJSONString(localPrintWriter);
/* 287:    */         }
/* 288:    */         catch (Throwable localThrowable2)
/* 289:    */         {
/* 290:167 */           localObject1 = localThrowable2;throw localThrowable2;
/* 291:    */         }
/* 292:    */         finally
/* 293:    */         {
/* 294:169 */           if (localPrintWriter != null) {
/* 295:169 */             if (localObject1 != null) {
/* 296:    */               try
/* 297:    */               {
/* 298:169 */                 localPrintWriter.close();
/* 299:    */               }
/* 300:    */               catch (Throwable localThrowable3)
/* 301:    */               {
/* 302:169 */                 ((Throwable)localObject1).addSuppressed(localThrowable3);
/* 303:    */               }
/* 304:    */             } else {
/* 305:169 */               localPrintWriter.close();
/* 306:    */             }
/* 307:    */           }
/* 308:    */         }
/* 309:171 */         User.this.asyncContext.complete();
/* 310:172 */         User.this.asyncContext = null;
/* 311:    */       }
/* 312:    */     }
/* 313:    */     
/* 314:    */     public void onStartAsync(AsyncEvent paramAsyncEvent)
/* 315:    */       throws IOException
/* 316:    */     {}
/* 317:    */     
/* 318:    */     public void onTimeout(AsyncEvent paramAsyncEvent)
/* 319:    */       throws IOException
/* 320:    */     {
/* 321:183 */       synchronized (User.this)
/* 322:    */       {
/* 323:184 */         User.this.asyncContext.getResponse().setContentType("text/plain; charset=UTF-8");
/* 324:    */         
/* 325:186 */         PrintWriter localPrintWriter = User.this.asyncContext.getResponse().getWriter();Object localObject1 = null;
/* 326:    */         try
/* 327:    */         {
/* 328:187 */           JSON.emptyJSON.writeJSONString(localPrintWriter);
/* 329:    */         }
/* 330:    */         catch (Throwable localThrowable2)
/* 331:    */         {
/* 332:186 */           localObject1 = localThrowable2;throw localThrowable2;
/* 333:    */         }
/* 334:    */         finally
/* 335:    */         {
/* 336:188 */           if (localPrintWriter != null) {
/* 337:188 */             if (localObject1 != null) {
/* 338:    */               try
/* 339:    */               {
/* 340:188 */                 localPrintWriter.close();
/* 341:    */               }
/* 342:    */               catch (Throwable localThrowable3)
/* 343:    */               {
/* 344:188 */                 ((Throwable)localObject1).addSuppressed(localThrowable3);
/* 345:    */               }
/* 346:    */             } else {
/* 347:188 */               localPrintWriter.close();
/* 348:    */             }
/* 349:    */           }
/* 350:    */         }
/* 351:190 */         User.this.asyncContext.complete();
/* 352:191 */         User.this.asyncContext = null;
/* 353:    */       }
/* 354:    */     }
/* 355:    */   }
/* 356:    */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.user.User
 * JD-Core Version:    0.7.1
 */