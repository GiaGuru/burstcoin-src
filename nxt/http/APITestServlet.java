/*   1:    */ package nxt.http;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.PrintWriter;
/*   5:    */ import java.util.ArrayList;
/*   6:    */ import java.util.Collections;
/*   7:    */ import java.util.List;
/*   8:    */ import java.util.Map;
/*   9:    */ import java.util.Map.Entry;
/*  10:    */ import java.util.Set;
/*  11:    */ import java.util.SortedMap;
/*  12:    */ import java.util.SortedSet;
/*  13:    */ import java.util.TreeMap;
/*  14:    */ import java.util.TreeSet;
/*  15:    */ import javax.servlet.ServletException;
/*  16:    */ import javax.servlet.http.HttpServlet;
/*  17:    */ import javax.servlet.http.HttpServletRequest;
/*  18:    */ import javax.servlet.http.HttpServletResponse;
/*  19:    */ import nxt.util.Convert;
/*  20:    */ 
/*  21:    */ public class APITestServlet
/*  22:    */   extends HttpServlet
/*  23:    */ {
/*  24:    */   private static final String header1 = "<!DOCTYPE html>\n<html>\n<head>\n    <meta charset=\"UTF-8\"/>\n    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">    <title>Nxt http API</title>\n    <link href=\"css/bootstrap.min.css\" rel=\"stylesheet\" type=\"text/css\" />    <style type=\"text/css\">\n        table {border-collapse: collapse;}\n        td {padding: 10px;}\n        .result {white-space: pre; font-family: monospace; overflow: auto;}\n    </style>\n    <script type=\"text/javascript\">\n        var apiCalls;\n        function performSearch(searchStr) {\n            if (searchStr == '') {\n              $('.api-call-All').show();\n            } else {\n              $('.api-call-All').hide();\n              $('.topic-link').css('font-weight', 'normal');\n              for(var i=0; i<apiCalls.length; i++) {\n                var apiCall = apiCalls[i];\n                if (new RegExp(searchStr.toLowerCase()).test(apiCall.toLowerCase())) {\n                  $('#api-call-' + apiCall).show();\n                }\n              }\n            }\n        }\n        function submitForm(form) {\n            var url = '/burst';\n            var params = {};\n            for (i = 0; i < form.elements.length; i++) {\n                if (form.elements[i].type != 'button' && form.elements[i].value && form.elements[i].value != 'submit') {\n                    params[form.elements[i].name] = form.elements[i].value;\n                }\n            }\n            $.ajax({\n                url: url,\n                type: 'POST',\n                data: params\n            })\n            .done(function(result) {\n                var resultStr = JSON.stringify(JSON.parse(result), null, 4);\n                form.getElementsByClassName(\"result\")[0].textContent = resultStr;\n            })\n            .error(function() {\n                alert('API not available, check if Nxt Server is running!');\n            });\n            if ($(form).has('.uri-link').length > 0) {\n                  var uri = '/burst?' + jQuery.param(params);\n                  var html = '<a href=\"' + uri + '\" target=\"_blank\" style=\"font-size:12px;font-weight:normal;\">Open GET URL</a>';                  form.getElementsByClassName(\"uri-link\")[0].innerHTML = html;\n            }            return false;\n        }\n    </script>\n</head>\n<body>\n<div class=\"navbar navbar-default\" role=\"navigation\">   <div class=\"container\" style=\"min-width: 90%;\">       <div class=\"navbar-header\">           <a class=\"navbar-brand\" href=\"/test\">Nxt http API</a>       </div>       <div class=\"navbar-collapse collapse\">           <ul class=\"nav navbar-nav navbar-right\">               <li><input type=\"text\" class=\"form-control\" id=\"search\"                     placeholder=\"Search\" style=\"margin-top:8px;\"></li>\n               <li><a href=\"https://wiki.nxtcrypto.org/wiki/Nxt_API\" target=\"_blank\" style=\"margin-left:20px;\">Wiki Docs</a></li>           </ul>       </div>   </div></div><div class=\"container\" style=\"min-width: 90%;\"><div class=\"row\">  <div class=\"col-xs-12\" style=\"margin-bottom:15px;\">    <div class=\"pull-right\">      <a href=\"#\" id=\"navi-show-open\">Show Open</a>       |       <a href=\"#\" id=\"navi-show-all\" style=\"font-weight:bold;\">Show All</a>    </div>  </div></div><div class=\"row\" style=\"margin-bottom:15px;\">  <div class=\"col-xs-4 col-sm-3 col-md-2\">    <ul class=\"nav nav-pills nav-stacked\">";
/*  25:    */   private static final String header2 = "    </ul>  </div> <!-- col -->  <div  class=\"col-xs-8 col-sm-9 col-md-10\">    <div class=\"panel-group\" id=\"accordion\">";
/*  26:    */   private static final String footer1 = "    </div> <!-- panel-group -->  </div> <!-- col --></div> <!-- row --></div> <!-- container --><script src=\"js/3rdparty/jquery.js\"></script><script src=\"js/3rdparty/bootstrap.js\" type=\"text/javascript\"></script><script>  $(document).ready(function() {    apiCalls = [];\n";
/*  27:    */   private static final String footer2 = "    $(\".collapse-link\").click(function(event) {       event.preventDefault();    });    $('#search').keyup(function(e) {\n      if (e.keyCode == 13) {\n        performSearch($(this).val());\n      }\n    });\n    $('#navi-show-open').click(function(e) {      $('.api-call-All').each(function() {        if($(this).find('.panel-collapse.in').length != 0) {          $(this).show();        } else {          $(this).hide();        }      });      $('#navi-show-all').css('font-weight', 'normal');      $(this).css('font-weight', 'bold');      e.preventDefault();    });    $('#navi-show-all').click(function(e) {      $('.api-call-All').show();      $('#navi-show-open').css('font-weight', 'normal');      $(this).css('font-weight', 'bold');      e.preventDefault();    });  });</script></body>\n</html>\n";
/*  28:159 */   private static final List<String> allRequestTypes = new ArrayList(APIServlet.apiRequestHandlers.keySet());
/*  29:    */   private static final SortedMap<String, SortedSet<String>> requestTags;
/*  30:    */   
/*  31:    */   static
/*  32:    */   {
/*  33:161 */     Collections.sort(allRequestTypes);
/*  34:    */     
/*  35:    */ 
/*  36:164 */     requestTags = new TreeMap();
/*  37:166 */     for (Map.Entry localEntry : APIServlet.apiRequestHandlers.entrySet())
/*  38:    */     {
/*  39:167 */       str = (String)localEntry.getKey();
/*  40:168 */       Set localSet = ((APIServlet.APIRequestHandler)localEntry.getValue()).getAPITags();
/*  41:169 */       for (APITag localAPITag : localSet)
/*  42:    */       {
/*  43:170 */         Object localObject = (SortedSet)requestTags.get(localAPITag.name());
/*  44:171 */         if (localObject == null)
/*  45:    */         {
/*  46:172 */           localObject = new TreeSet();
/*  47:173 */           requestTags.put(localAPITag.name(), localObject);
/*  48:    */         }
/*  49:175 */         ((SortedSet)localObject).add(str);
/*  50:    */       }
/*  51:    */     }
/*  52:    */     String str;
/*  53:    */   }
/*  54:    */   
/*  55:    */   private static String buildLinks(HttpServletRequest paramHttpServletRequest)
/*  56:    */   {
/*  57:181 */     StringBuilder localStringBuilder = new StringBuilder();
/*  58:182 */     String str = Convert.nullToEmpty(paramHttpServletRequest.getParameter("requestTag"));
/*  59:183 */     localStringBuilder.append("<li");
/*  60:184 */     if (str.equals("")) {
/*  61:185 */       localStringBuilder.append(" class=\"active\"");
/*  62:    */     }
/*  63:187 */     localStringBuilder.append("><a href=\"/test\">All</a></li>");
/*  64:188 */     for (APITag localAPITag : APITag.values()) {
/*  65:189 */       if (requestTags.get(localAPITag.name()) != null)
/*  66:    */       {
/*  67:190 */         localStringBuilder.append("<li");
/*  68:191 */         if (str.equals(localAPITag.name())) {
/*  69:192 */           localStringBuilder.append(" class=\"active\"");
/*  70:    */         }
/*  71:194 */         localStringBuilder.append("><a href=\"/test?requestTag=").append(localAPITag.name()).append("\">");
/*  72:195 */         localStringBuilder.append(localAPITag.getDisplayName()).append("</a></li>").append(" ");
/*  73:    */       }
/*  74:    */     }
/*  75:198 */     return localStringBuilder.toString();
/*  76:    */   }
/*  77:    */   
/*  78:    */   protected void doGet(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
/*  79:    */     throws ServletException, IOException
/*  80:    */   {
/*  81:203 */     paramHttpServletResponse.setHeader("Cache-Control", "no-cache, no-store, must-revalidate, private");
/*  82:204 */     paramHttpServletResponse.setHeader("Pragma", "no-cache");
/*  83:205 */     paramHttpServletResponse.setDateHeader("Expires", 0L);
/*  84:206 */     paramHttpServletResponse.setContentType("text/html; charset=UTF-8");
/*  85:208 */     if ((API.allowedBotHosts != null) && (!API.allowedBotHosts.contains(paramHttpServletRequest.getRemoteHost())))
/*  86:    */     {
/*  87:209 */       paramHttpServletResponse.sendError(403);
/*  88:210 */       return;
/*  89:    */     }
/*  90:213 */     PrintWriter localPrintWriter = paramHttpServletResponse.getWriter();Object localObject1 = null;
/*  91:    */     try
/*  92:    */     {
/*  93:214 */       localPrintWriter.print("<!DOCTYPE html>\n<html>\n<head>\n    <meta charset=\"UTF-8\"/>\n    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">    <title>Nxt http API</title>\n    <link href=\"css/bootstrap.min.css\" rel=\"stylesheet\" type=\"text/css\" />    <style type=\"text/css\">\n        table {border-collapse: collapse;}\n        td {padding: 10px;}\n        .result {white-space: pre; font-family: monospace; overflow: auto;}\n    </style>\n    <script type=\"text/javascript\">\n        var apiCalls;\n        function performSearch(searchStr) {\n            if (searchStr == '') {\n              $('.api-call-All').show();\n            } else {\n              $('.api-call-All').hide();\n              $('.topic-link').css('font-weight', 'normal');\n              for(var i=0; i<apiCalls.length; i++) {\n                var apiCall = apiCalls[i];\n                if (new RegExp(searchStr.toLowerCase()).test(apiCall.toLowerCase())) {\n                  $('#api-call-' + apiCall).show();\n                }\n              }\n            }\n        }\n        function submitForm(form) {\n            var url = '/burst';\n            var params = {};\n            for (i = 0; i < form.elements.length; i++) {\n                if (form.elements[i].type != 'button' && form.elements[i].value && form.elements[i].value != 'submit') {\n                    params[form.elements[i].name] = form.elements[i].value;\n                }\n            }\n            $.ajax({\n                url: url,\n                type: 'POST',\n                data: params\n            })\n            .done(function(result) {\n                var resultStr = JSON.stringify(JSON.parse(result), null, 4);\n                form.getElementsByClassName(\"result\")[0].textContent = resultStr;\n            })\n            .error(function() {\n                alert('API not available, check if Nxt Server is running!');\n            });\n            if ($(form).has('.uri-link').length > 0) {\n                  var uri = '/burst?' + jQuery.param(params);\n                  var html = '<a href=\"' + uri + '\" target=\"_blank\" style=\"font-size:12px;font-weight:normal;\">Open GET URL</a>';                  form.getElementsByClassName(\"uri-link\")[0].innerHTML = html;\n            }            return false;\n        }\n    </script>\n</head>\n<body>\n<div class=\"navbar navbar-default\" role=\"navigation\">   <div class=\"container\" style=\"min-width: 90%;\">       <div class=\"navbar-header\">           <a class=\"navbar-brand\" href=\"/test\">Nxt http API</a>       </div>       <div class=\"navbar-collapse collapse\">           <ul class=\"nav navbar-nav navbar-right\">               <li><input type=\"text\" class=\"form-control\" id=\"search\"                     placeholder=\"Search\" style=\"margin-top:8px;\"></li>\n               <li><a href=\"https://wiki.nxtcrypto.org/wiki/Nxt_API\" target=\"_blank\" style=\"margin-left:20px;\">Wiki Docs</a></li>           </ul>       </div>   </div></div><div class=\"container\" style=\"min-width: 90%;\"><div class=\"row\">  <div class=\"col-xs-12\" style=\"margin-bottom:15px;\">    <div class=\"pull-right\">      <a href=\"#\" id=\"navi-show-open\">Show Open</a>       |       <a href=\"#\" id=\"navi-show-all\" style=\"font-weight:bold;\">Show All</a>    </div>  </div></div><div class=\"row\" style=\"margin-bottom:15px;\">  <div class=\"col-xs-4 col-sm-3 col-md-2\">    <ul class=\"nav nav-pills nav-stacked\">");
/*  94:215 */       localPrintWriter.print(buildLinks(paramHttpServletRequest));
/*  95:216 */       localPrintWriter.print("    </ul>  </div> <!-- col -->  <div  class=\"col-xs-8 col-sm-9 col-md-10\">    <div class=\"panel-group\" id=\"accordion\">");
/*  96:217 */       String str1 = Convert.nullToEmpty(paramHttpServletRequest.getParameter("requestType"));
/*  97:218 */       APIServlet.APIRequestHandler localAPIRequestHandler = (APIServlet.APIRequestHandler)APIServlet.apiRequestHandlers.get(str1);
/*  98:219 */       StringBuilder localStringBuilder = new StringBuilder();
/*  99:220 */       if (localAPIRequestHandler != null)
/* 100:    */       {
/* 101:221 */         localPrintWriter.print(form(str1, true, localAPIRequestHandler.getClass().getName(), localAPIRequestHandler.getParameters(), localAPIRequestHandler.requirePost()));
/* 102:222 */         localStringBuilder.append("apiCalls.push(\"").append(str1).append("\");\n");
/* 103:    */       }
/* 104:    */       else
/* 105:    */       {
/* 106:224 */         String str2 = Convert.nullToEmpty(paramHttpServletRequest.getParameter("requestTag"));
/* 107:225 */         Set localSet = (Set)requestTags.get(str2);
/* 108:226 */         for (String str3 : localSet != null ? localSet : allRequestTypes)
/* 109:    */         {
/* 110:227 */           localAPIRequestHandler = (APIServlet.APIRequestHandler)APIServlet.apiRequestHandlers.get(str3);
/* 111:228 */           localPrintWriter.print(form(str3, false, localAPIRequestHandler.getClass().getName(), ((APIServlet.APIRequestHandler)APIServlet.apiRequestHandlers.get(str3)).getParameters(), ((APIServlet.APIRequestHandler)APIServlet.apiRequestHandlers.get(str3)).requirePost()));
/* 112:    */           
/* 113:230 */           localStringBuilder.append("apiCalls.push(\"").append(str3).append("\");\n");
/* 114:    */         }
/* 115:    */       }
/* 116:233 */       localPrintWriter.print("    </div> <!-- panel-group -->  </div> <!-- col --></div> <!-- row --></div> <!-- container --><script src=\"js/3rdparty/jquery.js\"></script><script src=\"js/3rdparty/bootstrap.js\" type=\"text/javascript\"></script><script>  $(document).ready(function() {    apiCalls = [];\n");
/* 117:234 */       localPrintWriter.print(localStringBuilder.toString());
/* 118:235 */       localPrintWriter.print("    $(\".collapse-link\").click(function(event) {       event.preventDefault();    });    $('#search').keyup(function(e) {\n      if (e.keyCode == 13) {\n        performSearch($(this).val());\n      }\n    });\n    $('#navi-show-open').click(function(e) {      $('.api-call-All').each(function() {        if($(this).find('.panel-collapse.in').length != 0) {          $(this).show();        } else {          $(this).hide();        }      });      $('#navi-show-all').css('font-weight', 'normal');      $(this).css('font-weight', 'bold');      e.preventDefault();    });    $('#navi-show-all').click(function(e) {      $('.api-call-All').show();      $('#navi-show-open').css('font-weight', 'normal');      $(this).css('font-weight', 'bold');      e.preventDefault();    });  });</script></body>\n</html>\n");
/* 119:    */     }
/* 120:    */     catch (Throwable localThrowable2)
/* 121:    */     {
/* 122:213 */       localObject1 = localThrowable2;throw localThrowable2;
/* 123:    */     }
/* 124:    */     finally
/* 125:    */     {
/* 126:236 */       if (localPrintWriter != null) {
/* 127:236 */         if (localObject1 != null) {
/* 128:    */           try
/* 129:    */           {
/* 130:236 */             localPrintWriter.close();
/* 131:    */           }
/* 132:    */           catch (Throwable localThrowable3)
/* 133:    */           {
/* 134:236 */             ((Throwable)localObject1).addSuppressed(localThrowable3);
/* 135:    */           }
/* 136:    */         } else {
/* 137:236 */           localPrintWriter.close();
/* 138:    */         }
/* 139:    */       }
/* 140:    */     }
/* 141:    */   }
/* 142:    */   
/* 143:    */   private static String form(String paramString1, boolean paramBoolean1, String paramString2, List<String> paramList, boolean paramBoolean2)
/* 144:    */   {
/* 145:241 */     StringBuilder localStringBuilder = new StringBuilder();
/* 146:242 */     localStringBuilder.append("<div class=\"panel panel-default api-call-All\" ");
/* 147:243 */     localStringBuilder.append("id=\"api-call-").append(paramString1).append("\">");
/* 148:244 */     localStringBuilder.append("<div class=\"panel-heading\">");
/* 149:245 */     localStringBuilder.append("<h4 class=\"panel-title\">");
/* 150:246 */     localStringBuilder.append("<a data-toggle=\"collapse\" class=\"collapse-link\" data-target=\"#collapse").append(paramString1).append("\" href=\"#\">");
/* 151:247 */     localStringBuilder.append(paramString1);
/* 152:248 */     localStringBuilder.append("</a>");
/* 153:249 */     localStringBuilder.append("<span style=\"float:right;font-weight:normal;font-size:14px;\">");
/* 154:250 */     if (!paramBoolean1)
/* 155:    */     {
/* 156:251 */       localStringBuilder.append("<a href=\"/test?requestType=").append(paramString1);
/* 157:252 */       localStringBuilder.append("\" target=\"_blank\" style=\"font-weight:normal;font-size:14px;color:#777;\"><span class=\"glyphicon glyphicon-new-window\"></span></a>");
/* 158:253 */       localStringBuilder.append(" &nbsp;&nbsp;");
/* 159:    */     }
/* 160:255 */     localStringBuilder.append("<a style=\"font-weight:normal;font-size:14px;color:#777;\" href=\"/doc/");
/* 161:256 */     localStringBuilder.append(paramString2.replace('.', '/')).append(".html\" target=\"_blank\">javadoc</a>");
/* 162:257 */     localStringBuilder.append("</span>");
/* 163:258 */     localStringBuilder.append("</h4>");
/* 164:259 */     localStringBuilder.append("</div> <!-- panel-heading -->");
/* 165:260 */     localStringBuilder.append("<div id=\"collapse").append(paramString1).append("\" class=\"panel-collapse collapse");
/* 166:261 */     if (paramBoolean1) {
/* 167:262 */       localStringBuilder.append(" in");
/* 168:    */     }
/* 169:264 */     localStringBuilder.append("\">");
/* 170:265 */     localStringBuilder.append("<div class=\"panel-body\">");
/* 171:266 */     localStringBuilder.append("<form action=\"/burst\" method=\"POST\" onsubmit=\"return submitForm(this);\">");
/* 172:267 */     localStringBuilder.append("<input type=\"hidden\" name=\"requestType\" value=\"").append(paramString1).append("\"/>");
/* 173:268 */     localStringBuilder.append("<div class=\"col-xs-12 col-lg-6\" style=\"width: 40%;\">");
/* 174:269 */     localStringBuilder.append("<table class=\"table\">");
/* 175:270 */     for (String str : paramList)
/* 176:    */     {
/* 177:271 */       localStringBuilder.append("<tr>");
/* 178:272 */       localStringBuilder.append("<td>").append(str).append(":</td>");
/* 179:273 */       localStringBuilder.append("<td><input type=\"");
/* 180:274 */       localStringBuilder.append("secretPhrase".equals(str) ? "password" : "text");
/* 181:275 */       localStringBuilder.append("\" name=\"").append(str).append("\" style=\"width:100%;min-width:200px;\"/></td>");
/* 182:276 */       localStringBuilder.append("</tr>");
/* 183:    */     }
/* 184:278 */     localStringBuilder.append("<tr>");
/* 185:279 */     localStringBuilder.append("<td colspan=\"2\"><input type=\"submit\" class=\"btn btn-default\" value=\"submit\"/></td>");
/* 186:280 */     localStringBuilder.append("</tr>");
/* 187:281 */     localStringBuilder.append("</table>");
/* 188:282 */     localStringBuilder.append("</div>");
/* 189:283 */     localStringBuilder.append("<div class=\"col-xs-12 col-lg-6\" style=\"min-width: 60%;\">");
/* 190:284 */     localStringBuilder.append("<h5 style=\"margin-top:0px;\">");
/* 191:285 */     if (!paramBoolean2)
/* 192:    */     {
/* 193:286 */       localStringBuilder.append("<span style=\"float:right;\" class=\"uri-link\">");
/* 194:287 */       localStringBuilder.append("</span>");
/* 195:    */     }
/* 196:    */     else
/* 197:    */     {
/* 198:289 */       localStringBuilder.append("<span style=\"float:right;font-size:12px;font-weight:normal;\">POST only</span>");
/* 199:    */     }
/* 200:291 */     localStringBuilder.append("Response</h5>");
/* 201:292 */     localStringBuilder.append("<pre class=\"result\">JSON response</pre>");
/* 202:293 */     localStringBuilder.append("</div>");
/* 203:294 */     localStringBuilder.append("</form>");
/* 204:295 */     localStringBuilder.append("</div> <!-- panel-body -->");
/* 205:296 */     localStringBuilder.append("</div> <!-- panel-collapse -->");
/* 206:297 */     localStringBuilder.append("</div> <!-- panel -->");
/* 207:298 */     return localStringBuilder.toString();
/* 208:    */   }
/* 209:    */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.http.APITestServlet
 * JD-Core Version:    0.7.1
 */