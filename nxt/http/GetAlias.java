/*  1:   */ package nxt.http;
/*  2:   */ 
/*  3:   */ import javax.servlet.http.HttpServletRequest;
/*  4:   */ import nxt.Alias;
/*  5:   */ import org.json.simple.JSONStreamAware;
/*  6:   */ 
/*  7:   */ public final class GetAlias
/*  8:   */   extends APIServlet.APIRequestHandler
/*  9:   */ {
/* 10:10 */   static final GetAlias instance = new GetAlias();
/* 11:   */   
/* 12:   */   private GetAlias()
/* 13:   */   {
/* 14:13 */     super(new APITag[] { APITag.ALIASES }, new String[] { "alias", "aliasName" });
/* 15:   */   }
/* 16:   */   
/* 17:   */   JSONStreamAware processRequest(HttpServletRequest paramHttpServletRequest)
/* 18:   */     throws ParameterException
/* 19:   */   {
/* 20:18 */     Alias localAlias = ParameterParser.getAlias(paramHttpServletRequest);
/* 21:19 */     return JSONData.alias(localAlias);
/* 22:   */   }
/* 23:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.http.GetAlias
 * JD-Core Version:    0.7.1
 */