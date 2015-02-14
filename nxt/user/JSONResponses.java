/*  1:   */ package nxt.user;
/*  2:   */ 
/*  3:   */ import nxt.util.JSON;
/*  4:   */ import org.json.simple.JSONObject;
/*  5:   */ import org.json.simple.JSONStreamAware;
/*  6:   */ 
/*  7:   */ public final class JSONResponses
/*  8:   */ {
/*  9:   */   public static final JSONStreamAware INVALID_SECRET_PHRASE;
/* 10:   */   public static final JSONStreamAware LOCK_ACCOUNT;
/* 11:   */   public static final JSONStreamAware LOCAL_USERS_ONLY;
/* 12:   */   public static final JSONStreamAware NOTIFY_OF_ACCEPTED_TRANSACTION;
/* 13:   */   public static final JSONStreamAware DENY_ACCESS;
/* 14:   */   public static final JSONStreamAware INCORRECT_REQUEST;
/* 15:   */   public static final JSONStreamAware POST_REQUIRED;
/* 16:   */   
/* 17:   */   static
/* 18:   */   {
/* 19:11 */     JSONObject localJSONObject = new JSONObject();
/* 20:12 */     localJSONObject.put("response", "showMessage");
/* 21:13 */     localJSONObject.put("message", "Invalid secret phrase!");
/* 22:14 */     INVALID_SECRET_PHRASE = JSON.prepare(localJSONObject);
/* 23:   */     
/* 24:   */ 
/* 25:   */ 
/* 26:   */ 
/* 27:19 */     localJSONObject = new JSONObject();
/* 28:20 */     localJSONObject.put("response", "lockAccount");
/* 29:21 */     LOCK_ACCOUNT = JSON.prepare(localJSONObject);
/* 30:   */     
/* 31:   */ 
/* 32:   */ 
/* 33:   */ 
/* 34:26 */     localJSONObject = new JSONObject();
/* 35:27 */     localJSONObject.put("response", "showMessage");
/* 36:28 */     localJSONObject.put("message", "This operation is allowed to local host users only!");
/* 37:29 */     LOCAL_USERS_ONLY = JSON.prepare(localJSONObject);
/* 38:   */     
/* 39:   */ 
/* 40:   */ 
/* 41:   */ 
/* 42:34 */     localJSONObject = new JSONObject();
/* 43:35 */     localJSONObject.put("response", "notifyOfAcceptedTransaction");
/* 44:36 */     NOTIFY_OF_ACCEPTED_TRANSACTION = JSON.prepare(localJSONObject);
/* 45:   */     
/* 46:   */ 
/* 47:   */ 
/* 48:   */ 
/* 49:41 */     localJSONObject = new JSONObject();
/* 50:42 */     localJSONObject.put("response", "denyAccess");
/* 51:43 */     DENY_ACCESS = JSON.prepare(localJSONObject);
/* 52:   */     
/* 53:   */ 
/* 54:   */ 
/* 55:   */ 
/* 56:48 */     localJSONObject = new JSONObject();
/* 57:49 */     localJSONObject.put("response", "showMessage");
/* 58:50 */     localJSONObject.put("message", "Incorrect request!");
/* 59:51 */     INCORRECT_REQUEST = JSON.prepare(localJSONObject);
/* 60:   */     
/* 61:   */ 
/* 62:   */ 
/* 63:   */ 
/* 64:56 */     localJSONObject = new JSONObject();
/* 65:57 */     localJSONObject.put("response", "showMessage");
/* 66:58 */     localJSONObject.put("message", "This request is only accepted using POST!");
/* 67:59 */     POST_REQUIRED = JSON.prepare(localJSONObject);
/* 68:   */   }
/* 69:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.user.JSONResponses
 * JD-Core Version:    0.7.1
 */