/*  1:   */ package nxt.http;
/*  2:   */ 
/*  3:   */ import javax.servlet.http.HttpServletRequest;
/*  4:   */ import nxt.Blockchain;
/*  5:   */ import nxt.BlockchainProcessor;
/*  6:   */ import nxt.Nxt;
/*  7:   */ import org.json.simple.JSONObject;
/*  8:   */ import org.json.simple.JSONStreamAware;
/*  9:   */ 
/* 10:   */ public final class Scan
/* 11:   */   extends APIServlet.APIRequestHandler
/* 12:   */ {
/* 13:11 */   static final Scan instance = new Scan();
/* 14:   */   
/* 15:   */   private Scan()
/* 16:   */   {
/* 17:14 */     super(new APITag[] { APITag.DEBUG }, new String[] { "numBlocks", "height", "validate" });
/* 18:   */   }
/* 19:   */   
/* 20:   */   JSONStreamAware processRequest(HttpServletRequest paramHttpServletRequest)
/* 21:   */   {
/* 22:19 */     JSONObject localJSONObject = new JSONObject();
/* 23:   */     try
/* 24:   */     {
/* 25:21 */       if ("true".equalsIgnoreCase(paramHttpServletRequest.getParameter("validate"))) {
/* 26:22 */         Nxt.getBlockchainProcessor().validateAtNextScan();
/* 27:   */       }
/* 28:24 */       int i = 0;
/* 29:   */       try
/* 30:   */       {
/* 31:26 */         i = Integer.parseInt(paramHttpServletRequest.getParameter("numBlocks"));
/* 32:   */       }
/* 33:   */       catch (NumberFormatException localNumberFormatException1) {}
/* 34:28 */       int j = -1;
/* 35:   */       try
/* 36:   */       {
/* 37:30 */         j = Integer.parseInt(paramHttpServletRequest.getParameter("height"));
/* 38:   */       }
/* 39:   */       catch (NumberFormatException localNumberFormatException2) {}
/* 40:32 */       long l1 = System.currentTimeMillis();
/* 41:33 */       if (i > 0)
/* 42:   */       {
/* 43:34 */         Nxt.getBlockchainProcessor().scan(Nxt.getBlockchain().getHeight() - i + 1);
/* 44:   */       }
/* 45:35 */       else if (j >= 0)
/* 46:   */       {
/* 47:36 */         Nxt.getBlockchainProcessor().scan(j);
/* 48:   */       }
/* 49:   */       else
/* 50:   */       {
/* 51:38 */         localJSONObject.put("error", "invalid numBlocks or height");
/* 52:39 */         return localJSONObject;
/* 53:   */       }
/* 54:41 */       long l2 = System.currentTimeMillis();
/* 55:42 */       localJSONObject.put("done", Boolean.valueOf(true));
/* 56:43 */       localJSONObject.put("scanTime", Long.valueOf((l2 - l1) / 1000L));
/* 57:   */     }
/* 58:   */     catch (RuntimeException localRuntimeException)
/* 59:   */     {
/* 60:45 */       localJSONObject.put("error", localRuntimeException.toString());
/* 61:   */     }
/* 62:47 */     return localJSONObject;
/* 63:   */   }
/* 64:   */   
/* 65:   */   final boolean requirePost()
/* 66:   */   {
/* 67:52 */     return true;
/* 68:   */   }
/* 69:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.http.Scan
 * JD-Core Version:    0.7.1
 */