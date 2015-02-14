/*  1:   */ package nxt.http;
/*  2:   */ 
/*  3:   */ import javax.servlet.http.HttpServletRequest;
/*  4:   */ import nxt.Block;
/*  5:   */ import nxt.Blockchain;
/*  6:   */ import nxt.Nxt;
/*  7:   */ import nxt.util.Convert;
/*  8:   */ import org.json.simple.JSONStreamAware;
/*  9:   */ 
/* 10:   */ public final class GetBlock
/* 11:   */   extends APIServlet.APIRequestHandler
/* 12:   */ {
/* 13:17 */   static final GetBlock instance = new GetBlock();
/* 14:   */   
/* 15:   */   private GetBlock()
/* 16:   */   {
/* 17:20 */     super(new APITag[] { APITag.BLOCKS }, new String[] { "block", "height", "timestamp", "includeTransactions" });
/* 18:   */   }
/* 19:   */   
/* 20:   */   JSONStreamAware processRequest(HttpServletRequest paramHttpServletRequest)
/* 21:   */   {
/* 22:27 */     String str1 = Convert.emptyToNull(paramHttpServletRequest.getParameter("block"));
/* 23:28 */     String str2 = Convert.emptyToNull(paramHttpServletRequest.getParameter("height"));
/* 24:29 */     String str3 = Convert.emptyToNull(paramHttpServletRequest.getParameter("timestamp"));
/* 25:   */     Block localBlock;
/* 26:30 */     if (str1 != null) {
/* 27:   */       try
/* 28:   */       {
/* 29:32 */         localBlock = Nxt.getBlockchain().getBlock(Convert.parseUnsignedLong(str1));
/* 30:   */       }
/* 31:   */       catch (RuntimeException localRuntimeException1)
/* 32:   */       {
/* 33:34 */         return JSONResponses.INCORRECT_BLOCK;
/* 34:   */       }
/* 35:36 */     } else if (str2 != null) {
/* 36:   */       try
/* 37:   */       {
/* 38:38 */         int i = Integer.parseInt(str2);
/* 39:39 */         if ((i < 0) || (i > Nxt.getBlockchain().getHeight())) {
/* 40:40 */           return JSONResponses.INCORRECT_HEIGHT;
/* 41:   */         }
/* 42:42 */         localBlock = Nxt.getBlockchain().getBlockAtHeight(i);
/* 43:   */       }
/* 44:   */       catch (RuntimeException localRuntimeException2)
/* 45:   */       {
/* 46:44 */         return JSONResponses.INCORRECT_HEIGHT;
/* 47:   */       }
/* 48:46 */     } else if (str3 != null) {
/* 49:   */       try
/* 50:   */       {
/* 51:48 */         int j = Integer.parseInt(str3);
/* 52:49 */         if (j < 0) {
/* 53:50 */           return JSONResponses.INCORRECT_TIMESTAMP;
/* 54:   */         }
/* 55:52 */         localBlock = Nxt.getBlockchain().getLastBlock(j);
/* 56:   */       }
/* 57:   */       catch (RuntimeException localRuntimeException3)
/* 58:   */       {
/* 59:54 */         return JSONResponses.INCORRECT_TIMESTAMP;
/* 60:   */       }
/* 61:   */     } else {
/* 62:57 */       localBlock = Nxt.getBlockchain().getLastBlock();
/* 63:   */     }
/* 64:60 */     if (localBlock == null) {
/* 65:61 */       return JSONResponses.UNKNOWN_BLOCK;
/* 66:   */     }
/* 67:64 */     boolean bool = "true".equalsIgnoreCase(paramHttpServletRequest.getParameter("includeTransactions"));
/* 68:   */     
/* 69:66 */     return JSONData.block(localBlock, bool);
/* 70:   */   }
/* 71:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.http.GetBlock
 * JD-Core Version:    0.7.1
 */