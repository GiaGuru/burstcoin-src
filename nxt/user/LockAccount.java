/*  1:   */ package nxt.user;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import javax.servlet.http.HttpServletRequest;
/*  5:   */ import org.json.simple.JSONStreamAware;
/*  6:   */ 
/*  7:   */ public final class LockAccount
/*  8:   */   extends UserServlet.UserRequestHandler
/*  9:   */ {
/* 10:12 */   static final LockAccount instance = new LockAccount();
/* 11:   */   
/* 12:   */   JSONStreamAware processRequest(HttpServletRequest paramHttpServletRequest, User paramUser)
/* 13:   */     throws IOException
/* 14:   */   {
/* 15:19 */     paramUser.lockAccount();
/* 16:   */     
/* 17:21 */     return JSONResponses.LOCK_ACCOUNT;
/* 18:   */   }
/* 19:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.user.LockAccount
 * JD-Core Version:    0.7.1
 */