/*  1:   */ package nxt.http;
/*  2:   */ 
/*  3:   */ import java.security.MessageDigest;
/*  4:   */ import javax.servlet.http.HttpServletRequest;
/*  5:   */ import nxt.crypto.Crypto;
/*  6:   */ import nxt.util.Convert;
/*  7:   */ import org.json.simple.JSONObject;
/*  8:   */ import org.json.simple.JSONStreamAware;
/*  9:   */ 
/* 10:   */ public final class CalculateFullHash
/* 11:   */   extends APIServlet.APIRequestHandler
/* 12:   */ {
/* 13:16 */   static final CalculateFullHash instance = new CalculateFullHash();
/* 14:   */   
/* 15:   */   private CalculateFullHash()
/* 16:   */   {
/* 17:19 */     super(new APITag[] { APITag.TRANSACTIONS }, new String[] { "unsignedTransactionBytes", "signatureHash" });
/* 18:   */   }
/* 19:   */   
/* 20:   */   JSONStreamAware processRequest(HttpServletRequest paramHttpServletRequest)
/* 21:   */   {
/* 22:25 */     String str1 = Convert.emptyToNull(paramHttpServletRequest.getParameter("unsignedTransactionBytes"));
/* 23:26 */     String str2 = Convert.emptyToNull(paramHttpServletRequest.getParameter("signatureHash"));
/* 24:28 */     if (str1 == null) {
/* 25:29 */       return JSONResponses.MISSING_UNSIGNED_BYTES;
/* 26:   */     }
/* 27:30 */     if (str2 == null) {
/* 28:31 */       return JSONResponses.MISSING_SIGNATURE_HASH;
/* 29:   */     }
/* 30:34 */     MessageDigest localMessageDigest = Crypto.sha256();
/* 31:35 */     localMessageDigest.update(Convert.parseHexString(str1));
/* 32:36 */     byte[] arrayOfByte = localMessageDigest.digest(Convert.parseHexString(str2));
/* 33:37 */     JSONObject localJSONObject = new JSONObject();
/* 34:38 */     localJSONObject.put("fullHash", Convert.toHexString(arrayOfByte));
/* 35:   */     
/* 36:40 */     return localJSONObject;
/* 37:   */   }
/* 38:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.http.CalculateFullHash
 * JD-Core Version:    0.7.1
 */