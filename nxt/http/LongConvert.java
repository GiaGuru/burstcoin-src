/*  1:   */ package nxt.http;
/*  2:   */ 
/*  3:   */ import java.math.BigInteger;
/*  4:   */ import javax.servlet.http.HttpServletRequest;
/*  5:   */ import nxt.util.Convert;
/*  6:   */ import nxt.util.JSON;
/*  7:   */ import org.json.simple.JSONObject;
/*  8:   */ import org.json.simple.JSONStreamAware;
/*  9:   */ 
/* 10:   */ public final class LongConvert
/* 11:   */   extends APIServlet.APIRequestHandler
/* 12:   */ {
/* 13:13 */   static final LongConvert instance = new LongConvert();
/* 14:   */   
/* 15:   */   private LongConvert()
/* 16:   */   {
/* 17:16 */     super(new APITag[] { APITag.UTILS }, new String[] { "id" });
/* 18:   */   }
/* 19:   */   
/* 20:   */   JSONStreamAware processRequest(HttpServletRequest paramHttpServletRequest)
/* 21:   */   {
/* 22:21 */     String str = Convert.emptyToNull(paramHttpServletRequest.getParameter("id"));
/* 23:22 */     if (str == null) {
/* 24:23 */       return JSON.emptyJSON;
/* 25:   */     }
/* 26:25 */     JSONObject localJSONObject = new JSONObject();
/* 27:26 */     BigInteger localBigInteger = new BigInteger(str);
/* 28:27 */     if (localBigInteger.signum() < 0)
/* 29:   */     {
/* 30:28 */       if (localBigInteger.negate().compareTo(Convert.two64) > 0)
/* 31:   */       {
/* 32:29 */         localJSONObject.put("error", "overflow");
/* 33:   */       }
/* 34:   */       else
/* 35:   */       {
/* 36:31 */         localJSONObject.put("stringId", localBigInteger.add(Convert.two64).toString());
/* 37:32 */         localJSONObject.put("longId", String.valueOf(localBigInteger.longValue()));
/* 38:   */       }
/* 39:   */     }
/* 40:35 */     else if (localBigInteger.compareTo(Convert.two64) >= 0)
/* 41:   */     {
/* 42:36 */       localJSONObject.put("error", "overflow");
/* 43:   */     }
/* 44:   */     else
/* 45:   */     {
/* 46:38 */       localJSONObject.put("stringId", localBigInteger.toString());
/* 47:39 */       localJSONObject.put("longId", String.valueOf(localBigInteger.longValue()));
/* 48:   */     }
/* 49:42 */     return localJSONObject;
/* 50:   */   }
/* 51:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.http.LongConvert
 * JD-Core Version:    0.7.1
 */