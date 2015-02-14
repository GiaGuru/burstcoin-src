/*  1:   */ package nxt.http;
/*  2:   */ 
/*  3:   */ import nxt.NxtException;
/*  4:   */ import org.json.simple.JSONStreamAware;
/*  5:   */ 
/*  6:   */ final class ParameterException
/*  7:   */   extends NxtException
/*  8:   */ {
/*  9:   */   private final JSONStreamAware errorResponse;
/* 10:   */   
/* 11:   */   ParameterException(JSONStreamAware paramJSONStreamAware)
/* 12:   */   {
/* 13:11 */     this.errorResponse = paramJSONStreamAware;
/* 14:   */   }
/* 15:   */   
/* 16:   */   JSONStreamAware getErrorResponse()
/* 17:   */   {
/* 18:15 */     return this.errorResponse;
/* 19:   */   }
/* 20:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.http.ParameterException
 * JD-Core Version:    0.7.1
 */