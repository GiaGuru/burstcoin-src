/*   1:    */ package nxt.http;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.PrintStream;
/*   5:    */ import java.sql.SQLException;
/*   6:    */ import java.util.Set;
/*   7:    */ import javax.servlet.ServletException;
/*   8:    */ import javax.servlet.http.HttpServlet;
/*   9:    */ import javax.servlet.http.HttpServletRequest;
/*  10:    */ import javax.servlet.http.HttpServletResponse;
/*  11:    */ import nxt.db.Db;
/*  12:    */ import nxt.util.Convert;
/*  13:    */ import org.h2.tools.Shell;
/*  14:    */ 
/*  15:    */ public final class DbShellServlet
/*  16:    */   extends HttpServlet
/*  17:    */ {
/*  18:    */   private static final String header = "<!DOCTYPE html>\n<html>\n<head>\n    <meta charset=\"UTF-8\"/>\n    <title>Nxt H2 Database Shell</title>\n    <script type=\"text/javascript\">\n        function submitForm(form) {\n            var url = '/dbshell';\n            var params = '';\n            for (i = 0; i < form.elements.length; i++) {\n                if (! form.elements[i].name) {\n                    continue;\n                }\n                if (i > 0) {\n                    params += '&';\n                }\n                params += encodeURIComponent(form.elements[i].name);\n                params += '=';\n                params += encodeURIComponent(form.elements[i].value);\n            }\n            var request = new XMLHttpRequest();\n            request.open(\"POST\", url, false);\n            request.setRequestHeader(\"Content-type\", \"application/x-www-form-urlencoded\");\n            request.send(params);\n            form.getElementsByClassName(\"result\")[0].textContent += request.responseText;\n            return false;\n        }\n    </script>\n</head>\n<body>\n";
/*  19:    */   private static final String footer = "</body>\n</html>\n";
/*  20:    */   private static final String form = "<form action=\"/dbshell\" method=\"POST\" onsubmit=\"return submitForm(this);\"><table class=\"table\" style=\"width:90%;\"><tr><td><pre class=\"result\" style=\"float:top;width:90%;\">This is a database shell. Enter SQL to be evaluated, or \"help\" for help:</pre></td></tr><tr><td><b>&gt;</b> <input type=\"text\" name=\"line\" style=\"width:90%;\"/></td></tr></table></form>";
/*  21:    */   
/*  22:    */   protected void doGet(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
/*  23:    */     throws ServletException, IOException
/*  24:    */   {
/*  25: 65 */     paramHttpServletResponse.setHeader("Cache-Control", "no-cache, no-store, must-revalidate, private");
/*  26: 66 */     paramHttpServletResponse.setHeader("Pragma", "no-cache");
/*  27: 67 */     paramHttpServletResponse.setDateHeader("Expires", 0L);
/*  28: 68 */     if ((API.allowedBotHosts != null) && (!API.allowedBotHosts.contains(paramHttpServletRequest.getRemoteHost())))
/*  29:    */     {
/*  30: 69 */       paramHttpServletResponse.sendError(403);
/*  31: 70 */       return;
/*  32:    */     }
/*  33: 73 */     PrintStream localPrintStream = new PrintStream(paramHttpServletResponse.getOutputStream());Object localObject1 = null;
/*  34:    */     try
/*  35:    */     {
/*  36: 74 */       localPrintStream.print("<!DOCTYPE html>\n<html>\n<head>\n    <meta charset=\"UTF-8\"/>\n    <title>Nxt H2 Database Shell</title>\n    <script type=\"text/javascript\">\n        function submitForm(form) {\n            var url = '/dbshell';\n            var params = '';\n            for (i = 0; i < form.elements.length; i++) {\n                if (! form.elements[i].name) {\n                    continue;\n                }\n                if (i > 0) {\n                    params += '&';\n                }\n                params += encodeURIComponent(form.elements[i].name);\n                params += '=';\n                params += encodeURIComponent(form.elements[i].value);\n            }\n            var request = new XMLHttpRequest();\n            request.open(\"POST\", url, false);\n            request.setRequestHeader(\"Content-type\", \"application/x-www-form-urlencoded\");\n            request.send(params);\n            form.getElementsByClassName(\"result\")[0].textContent += request.responseText;\n            return false;\n        }\n    </script>\n</head>\n<body>\n");
/*  37: 75 */       localPrintStream.print("<form action=\"/dbshell\" method=\"POST\" onsubmit=\"return submitForm(this);\"><table class=\"table\" style=\"width:90%;\"><tr><td><pre class=\"result\" style=\"float:top;width:90%;\">This is a database shell. Enter SQL to be evaluated, or \"help\" for help:</pre></td></tr><tr><td><b>&gt;</b> <input type=\"text\" name=\"line\" style=\"width:90%;\"/></td></tr></table></form>");
/*  38: 76 */       localPrintStream.print("</body>\n</html>\n");
/*  39:    */     }
/*  40:    */     catch (Throwable localThrowable2)
/*  41:    */     {
/*  42: 73 */       localObject1 = localThrowable2;throw localThrowable2;
/*  43:    */     }
/*  44:    */     finally
/*  45:    */     {
/*  46: 77 */       if (localPrintStream != null) {
/*  47: 77 */         if (localObject1 != null) {
/*  48:    */           try
/*  49:    */           {
/*  50: 77 */             localPrintStream.close();
/*  51:    */           }
/*  52:    */           catch (Throwable localThrowable3)
/*  53:    */           {
/*  54: 77 */             ((Throwable)localObject1).addSuppressed(localThrowable3);
/*  55:    */           }
/*  56:    */         } else {
/*  57: 77 */           localPrintStream.close();
/*  58:    */         }
/*  59:    */       }
/*  60:    */     }
/*  61:    */   }
/*  62:    */   
/*  63:    */   protected void doPost(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
/*  64:    */     throws ServletException, IOException
/*  65:    */   {
/*  66: 82 */     paramHttpServletResponse.setHeader("Cache-Control", "no-cache, no-store, must-revalidate, private");
/*  67: 83 */     paramHttpServletResponse.setHeader("Pragma", "no-cache");
/*  68: 84 */     paramHttpServletResponse.setDateHeader("Expires", 0L);
/*  69: 85 */     if ((API.allowedBotHosts != null) && (!API.allowedBotHosts.contains(paramHttpServletRequest.getRemoteHost())))
/*  70:    */     {
/*  71: 86 */       paramHttpServletResponse.sendError(403);
/*  72: 87 */       return;
/*  73:    */     }
/*  74: 90 */     String str = Convert.nullToEmpty(paramHttpServletRequest.getParameter("line"));
/*  75: 91 */     PrintStream localPrintStream = new PrintStream(paramHttpServletResponse.getOutputStream());Object localObject1 = null;
/*  76:    */     try
/*  77:    */     {
/*  78: 92 */       localPrintStream.println("\n> " + str);
/*  79:    */       try
/*  80:    */       {
/*  81: 94 */         Shell localShell = new Shell();
/*  82: 95 */         localShell.setErr(localPrintStream);
/*  83: 96 */         localShell.setOut(localPrintStream);
/*  84: 97 */         localShell.runTool(Db.getConnection(), new String[] { "-sql", str });
/*  85:    */       }
/*  86:    */       catch (SQLException localSQLException)
/*  87:    */       {
/*  88: 99 */         localPrintStream.println(localSQLException.toString());
/*  89:    */       }
/*  90:    */     }
/*  91:    */     catch (Throwable localThrowable2)
/*  92:    */     {
/*  93: 91 */       localObject1 = localThrowable2;throw localThrowable2;
/*  94:    */     }
/*  95:    */     finally
/*  96:    */     {
/*  97:101 */       if (localPrintStream != null) {
/*  98:101 */         if (localObject1 != null) {
/*  99:    */           try
/* 100:    */           {
/* 101:101 */             localPrintStream.close();
/* 102:    */           }
/* 103:    */           catch (Throwable localThrowable3)
/* 104:    */           {
/* 105:101 */             ((Throwable)localObject1).addSuppressed(localThrowable3);
/* 106:    */           }
/* 107:    */         } else {
/* 108:101 */           localPrintStream.close();
/* 109:    */         }
/* 110:    */       }
/* 111:    */     }
/* 112:    */   }
/* 113:    */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.http.DbShellServlet
 * JD-Core Version:    0.7.1
 */