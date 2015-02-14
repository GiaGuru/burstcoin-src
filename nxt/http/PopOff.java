/*  1:   */ package nxt.http;
/*  2:   */ 
/*  3:   */ import java.util.List;
/*  4:   */ import javax.servlet.http.HttpServletRequest;
/*  5:   */ import nxt.Block;
/*  6:   */ import nxt.Blockchain;
/*  7:   */ import nxt.BlockchainProcessor;
/*  8:   */ import nxt.Nxt;
/*  9:   */ import org.json.simple.JSONArray;
/* 10:   */ import org.json.simple.JSONObject;
/* 11:   */ import org.json.simple.JSONStreamAware;
/* 12:   */ 
/* 13:   */ public final class PopOff
/* 14:   */   extends APIServlet.APIRequestHandler
/* 15:   */ {
/* 16:14 */   static final PopOff instance = new PopOff();
/* 17:   */   
/* 18:   */   private PopOff()
/* 19:   */   {
/* 20:17 */     super(new APITag[] { APITag.DEBUG }, new String[] { "numBlocks", "height" });
/* 21:   */   }
/* 22:   */   
/* 23:   */   JSONStreamAware processRequest(HttpServletRequest paramHttpServletRequest)
/* 24:   */   {
/* 25:23 */     JSONObject localJSONObject = new JSONObject();
/* 26:24 */     int i = 0;
/* 27:   */     try
/* 28:   */     {
/* 29:26 */       i = Integer.parseInt(paramHttpServletRequest.getParameter("numBlocks"));
/* 30:   */     }
/* 31:   */     catch (NumberFormatException localNumberFormatException1) {}
/* 32:28 */     int j = 0;
/* 33:   */     try
/* 34:   */     {
/* 35:30 */       j = Integer.parseInt(paramHttpServletRequest.getParameter("height"));
/* 36:   */     }
/* 37:   */     catch (NumberFormatException localNumberFormatException2) {}
/* 38:34 */     JSONArray localJSONArray = new JSONArray();
/* 39:   */     List localList;
/* 40:35 */     if (i > 0)
/* 41:   */     {
/* 42:36 */       localList = Nxt.getBlockchainProcessor().popOffTo(Nxt.getBlockchain().getHeight() - i);
/* 43:   */     }
/* 44:37 */     else if (j > 0)
/* 45:   */     {
/* 46:38 */       localList = Nxt.getBlockchainProcessor().popOffTo(j);
/* 47:   */     }
/* 48:   */     else
/* 49:   */     {
/* 50:40 */       localJSONObject.put("error", "invalid numBlocks or height");
/* 51:41 */       return localJSONObject;
/* 52:   */     }
/* 53:43 */     for (Block localBlock : localList) {
/* 54:44 */       localJSONArray.add(JSONData.block(localBlock, true));
/* 55:   */     }
/* 56:46 */     localJSONObject.put("blocks", localJSONArray);
/* 57:47 */     return localJSONObject;
/* 58:   */   }
/* 59:   */   
/* 60:   */   final boolean requirePost()
/* 61:   */   {
/* 62:52 */     return true;
/* 63:   */   }
/* 64:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.http.PopOff
 * JD-Core Version:    0.7.1
 */