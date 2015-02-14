/*  1:   */ package nxt.http;
/*  2:   */ 
/*  3:   */ import java.util.Collection;
/*  4:   */ import java.util.Iterator;
/*  5:   */ import javax.servlet.http.HttpServletRequest;
/*  6:   */ import nxt.AT;
/*  7:   */ import nxt.util.Convert;
/*  8:   */ import org.json.simple.JSONArray;
/*  9:   */ import org.json.simple.JSONObject;
/* 10:   */ import org.json.simple.JSONStreamAware;
/* 11:   */ 
/* 12:   */ public final class GetATIds
/* 13:   */   extends APIServlet.APIRequestHandler
/* 14:   */ {
/* 15:19 */   static final GetATIds instance = new GetATIds();
/* 16:   */   
/* 17:   */   private GetATIds()
/* 18:   */   {
/* 19:22 */     super(new APITag[] { APITag.AT }, new String[0]);
/* 20:   */   }
/* 21:   */   
/* 22:   */   JSONStreamAware processRequest(HttpServletRequest paramHttpServletRequest)
/* 23:   */   {
/* 24:28 */     JSONArray localJSONArray = new JSONArray();
/* 25:29 */     for (Object localObject = AT.getAllATIds().iterator(); ((Iterator)localObject).hasNext();)
/* 26:   */     {
/* 27:29 */       Long localLong = (Long)((Iterator)localObject).next();
/* 28:30 */       localJSONArray.add(Convert.toUnsignedLong(localLong.longValue()));
/* 29:   */     }
/* 30:33 */     localObject = new JSONObject();
/* 31:34 */     ((JSONObject)localObject).put("atIds", localJSONArray);
/* 32:35 */     return (JSONStreamAware)localObject;
/* 33:   */   }
/* 34:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.http.GetATIds
 * JD-Core Version:    0.7.1
 */