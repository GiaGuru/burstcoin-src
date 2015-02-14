/*  1:   */ package nxt.http;
/*  2:   */ 
/*  3:   */ import javax.servlet.http.HttpServletRequest;
/*  4:   */ import nxt.Blockchain;
/*  5:   */ import nxt.Nxt;
/*  6:   */ import nxt.util.Convert;
/*  7:   */ import org.json.simple.JSONObject;
/*  8:   */ import org.json.simple.JSONStreamAware;
/*  9:   */ 
/* 10:   */ public final class GetBlockId
/* 11:   */   extends APIServlet.APIRequestHandler
/* 12:   */ {
/* 13:15 */   static final GetBlockId instance = new GetBlockId();
/* 14:   */   
/* 15:   */   private GetBlockId()
/* 16:   */   {
/* 17:18 */     super(new APITag[] { APITag.BLOCKS }, new String[] { "height" });
/* 18:   */   }
/* 19:   */   
/* 20:   */   JSONStreamAware processRequest(HttpServletRequest paramHttpServletRequest)
/* 21:   */   {
/* 22:   */     int i;
/* 23:   */     try
/* 24:   */     {
/* 25:26 */       String str = Convert.emptyToNull(paramHttpServletRequest.getParameter("height"));
/* 26:27 */       if (str == null) {
/* 27:28 */         return JSONResponses.MISSING_HEIGHT;
/* 28:   */       }
/* 29:30 */       i = Integer.parseInt(str);
/* 30:   */     }
/* 31:   */     catch (RuntimeException localRuntimeException1)
/* 32:   */     {
/* 33:32 */       return JSONResponses.INCORRECT_HEIGHT;
/* 34:   */     }
/* 35:   */     try
/* 36:   */     {
/* 37:36 */       JSONObject localJSONObject = new JSONObject();
/* 38:37 */       localJSONObject.put("block", Convert.toUnsignedLong(Nxt.getBlockchain().getBlockIdAtHeight(i)));
/* 39:38 */       return localJSONObject;
/* 40:   */     }
/* 41:   */     catch (RuntimeException localRuntimeException2) {}
/* 42:40 */     return JSONResponses.INCORRECT_HEIGHT;
/* 43:   */   }
/* 44:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.http.GetBlockId
 * JD-Core Version:    0.7.1
 */