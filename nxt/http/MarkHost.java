/*  1:   */ package nxt.http;
/*  2:   */ 
/*  3:   */ import javax.servlet.http.HttpServletRequest;
/*  4:   */ import nxt.peer.Hallmark;
/*  5:   */ import org.json.simple.JSONObject;
/*  6:   */ import org.json.simple.JSONStreamAware;
/*  7:   */ 
/*  8:   */ public final class MarkHost
/*  9:   */   extends APIServlet.APIRequestHandler
/* 10:   */ {
/* 11:21 */   static final MarkHost instance = new MarkHost();
/* 12:   */   
/* 13:   */   private MarkHost()
/* 14:   */   {
/* 15:24 */     super(new APITag[] { APITag.TOKENS }, new String[] { "secretPhrase", "host", "weight", "date" });
/* 16:   */   }
/* 17:   */   
/* 18:   */   JSONStreamAware processRequest(HttpServletRequest paramHttpServletRequest)
/* 19:   */   {
/* 20:30 */     String str1 = paramHttpServletRequest.getParameter("secretPhrase");
/* 21:31 */     String str2 = paramHttpServletRequest.getParameter("host");
/* 22:32 */     String str3 = paramHttpServletRequest.getParameter("weight");
/* 23:33 */     String str4 = paramHttpServletRequest.getParameter("date");
/* 24:34 */     if (str1 == null) {
/* 25:35 */       return JSONResponses.MISSING_SECRET_PHRASE;
/* 26:   */     }
/* 27:36 */     if (str2 == null) {
/* 28:37 */       return JSONResponses.MISSING_HOST;
/* 29:   */     }
/* 30:38 */     if (str3 == null) {
/* 31:39 */       return JSONResponses.MISSING_WEIGHT;
/* 32:   */     }
/* 33:40 */     if (str4 == null) {
/* 34:41 */       return JSONResponses.MISSING_DATE;
/* 35:   */     }
/* 36:44 */     if (str2.length() > 100) {
/* 37:45 */       return JSONResponses.INCORRECT_HOST;
/* 38:   */     }
/* 39:   */     int i;
/* 40:   */     try
/* 41:   */     {
/* 42:50 */       i = Integer.parseInt(str3);
/* 43:51 */       if ((i <= 0) || (i > 2158812800L)) {
/* 44:52 */         return JSONResponses.INCORRECT_WEIGHT;
/* 45:   */       }
/* 46:   */     }
/* 47:   */     catch (NumberFormatException localNumberFormatException)
/* 48:   */     {
/* 49:55 */       return JSONResponses.INCORRECT_WEIGHT;
/* 50:   */     }
/* 51:   */     try
/* 52:   */     {
/* 53:60 */       String str5 = Hallmark.generateHallmark(str1, str2, i, Hallmark.parseDate(str4));
/* 54:   */       
/* 55:62 */       JSONObject localJSONObject = new JSONObject();
/* 56:63 */       localJSONObject.put("hallmark", str5);
/* 57:64 */       return localJSONObject;
/* 58:   */     }
/* 59:   */     catch (RuntimeException localRuntimeException) {}
/* 60:67 */     return JSONResponses.INCORRECT_DATE;
/* 61:   */   }
/* 62:   */   
/* 63:   */   boolean requirePost()
/* 64:   */   {
/* 65:74 */     return true;
/* 66:   */   }
/* 67:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.http.MarkHost
 * JD-Core Version:    0.7.1
 */