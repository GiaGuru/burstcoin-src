/*   1:    */ package nxt.http;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.List;
/*   5:    */ import javax.servlet.http.HttpServletRequest;
/*   6:    */ import nxt.Account;
/*   7:    */ import nxt.Attachment.MessagingPollCreation;
/*   8:    */ import nxt.NxtException;
/*   9:    */ import org.json.simple.JSONStreamAware;
/*  10:    */ 
/*  11:    */ public final class CreatePoll
/*  12:    */   extends CreateTransaction
/*  13:    */ {
/*  14: 27 */   static final CreatePoll instance = new CreatePoll();
/*  15:    */   
/*  16:    */   private CreatePoll()
/*  17:    */   {
/*  18: 30 */     super(new APITag[] { APITag.VS, APITag.CREATE_TRANSACTION }, new String[] { "name", "description", "minNumberOfOptions", "maxNumberOfOptions", "optionsAreBinary", "option1", "option2", "option3" });
/*  19:    */   }
/*  20:    */   
/*  21:    */   JSONStreamAware processRequest(HttpServletRequest paramHttpServletRequest)
/*  22:    */     throws NxtException
/*  23:    */   {
/*  24: 37 */     String str1 = paramHttpServletRequest.getParameter("name");
/*  25: 38 */     String str2 = paramHttpServletRequest.getParameter("description");
/*  26: 39 */     String str3 = paramHttpServletRequest.getParameter("minNumberOfOptions");
/*  27: 40 */     String str4 = paramHttpServletRequest.getParameter("maxNumberOfOptions");
/*  28: 41 */     String str5 = paramHttpServletRequest.getParameter("optionsAreBinary");
/*  29: 43 */     if (str1 == null) {
/*  30: 44 */       return JSONResponses.MISSING_NAME;
/*  31:    */     }
/*  32: 45 */     if (str2 == null) {
/*  33: 46 */       return JSONResponses.MISSING_DESCRIPTION;
/*  34:    */     }
/*  35: 47 */     if (str3 == null) {
/*  36: 48 */       return JSONResponses.MISSING_MINNUMBEROFOPTIONS;
/*  37:    */     }
/*  38: 49 */     if (str4 == null) {
/*  39: 50 */       return JSONResponses.MISSING_MAXNUMBEROFOPTIONS;
/*  40:    */     }
/*  41: 51 */     if (str5 == null) {
/*  42: 52 */       return JSONResponses.MISSING_OPTIONSAREBINARY;
/*  43:    */     }
/*  44: 55 */     if (str1.length() > 100) {
/*  45: 56 */       return JSONResponses.INCORRECT_POLL_NAME_LENGTH;
/*  46:    */     }
/*  47: 59 */     if (str2.length() > 1000) {
/*  48: 60 */       return JSONResponses.INCORRECT_POLL_DESCRIPTION_LENGTH;
/*  49:    */     }
/*  50: 63 */     ArrayList localArrayList = new ArrayList();
/*  51: 64 */     while (localArrayList.size() < 100)
/*  52:    */     {
/*  53: 65 */       String str6 = paramHttpServletRequest.getParameter("option" + localArrayList.size());
/*  54: 66 */       if (str6 == null) {
/*  55:    */         break;
/*  56:    */       }
/*  57: 69 */       if (str6.length() > 100) {
/*  58: 70 */         return JSONResponses.INCORRECT_POLL_OPTION_LENGTH;
/*  59:    */       }
/*  60: 72 */       localArrayList.add(str6.trim());
/*  61:    */     }
/*  62:    */     byte b1;
/*  63:    */     try
/*  64:    */     {
/*  65: 77 */       b1 = Byte.parseByte(str3);
/*  66:    */     }
/*  67:    */     catch (NumberFormatException localNumberFormatException1)
/*  68:    */     {
/*  69: 79 */       return JSONResponses.INCORRECT_MINNUMBEROFOPTIONS;
/*  70:    */     }
/*  71:    */     byte b2;
/*  72:    */     try
/*  73:    */     {
/*  74: 84 */       b2 = Byte.parseByte(str4);
/*  75:    */     }
/*  76:    */     catch (NumberFormatException localNumberFormatException2)
/*  77:    */     {
/*  78: 86 */       return JSONResponses.INCORRECT_MAXNUMBEROFOPTIONS;
/*  79:    */     }
/*  80:    */     boolean bool;
/*  81:    */     try
/*  82:    */     {
/*  83: 91 */       bool = Boolean.parseBoolean(str5);
/*  84:    */     }
/*  85:    */     catch (NumberFormatException localNumberFormatException3)
/*  86:    */     {
/*  87: 93 */       return JSONResponses.INCORRECT_OPTIONSAREBINARY;
/*  88:    */     }
/*  89: 96 */     Account localAccount = ParameterParser.getSenderAccount(paramHttpServletRequest);
/*  90:    */     
/*  91: 98 */     Attachment.MessagingPollCreation localMessagingPollCreation = new Attachment.MessagingPollCreation(str1.trim(), str2.trim(), (String[])localArrayList.toArray(new String[localArrayList.size()]), b1, b2, bool);
/*  92:    */     
/*  93:100 */     return createTransaction(paramHttpServletRequest, localAccount, localMessagingPollCreation);
/*  94:    */   }
/*  95:    */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.http.CreatePoll
 * JD-Core Version:    0.7.1
 */