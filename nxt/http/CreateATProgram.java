/*   1:    */ package nxt.http;
/*   2:    */ 
/*   3:    */ import java.io.PrintStream;
/*   4:    */ import java.nio.ByteBuffer;
/*   5:    */ import java.nio.ByteOrder;
/*   6:    */ import javax.servlet.http.HttpServletRequest;
/*   7:    */ import nxt.Account;
/*   8:    */ import nxt.Attachment;
/*   9:    */ import nxt.Attachment.AutomatedTransactionsCreation;
/*  10:    */ import nxt.Blockchain;
/*  11:    */ import nxt.Nxt;
/*  12:    */ import nxt.NxtException;
/*  13:    */ import nxt.at.AT_Constants;
/*  14:    */ import nxt.util.Convert;
/*  15:    */ import org.json.simple.JSONObject;
/*  16:    */ import org.json.simple.JSONStreamAware;
/*  17:    */ 
/*  18:    */ public final class CreateATProgram
/*  19:    */   extends CreateTransaction
/*  20:    */ {
/*  21: 26 */   static final CreateATProgram instance = new CreateATProgram();
/*  22:    */   
/*  23:    */   private CreateATProgram()
/*  24:    */   {
/*  25: 29 */     super(new APITag[] { APITag.AT, APITag.CREATE_TRANSACTION }, new String[] { "name", "description", "creationBytes", "code", "data", "dpages", "cspages", "uspages", "minActivationAmountNQT" });
/*  26:    */   }
/*  27:    */   
/*  28:    */   JSONStreamAware processRequest(HttpServletRequest paramHttpServletRequest)
/*  29:    */     throws NxtException
/*  30:    */   {
/*  31: 35 */     String str1 = paramHttpServletRequest.getParameter("name");
/*  32: 36 */     String str2 = paramHttpServletRequest.getParameter("description");
/*  33: 38 */     if (str1 == null) {
/*  34: 39 */       return JSONResponses.MISSING_NAME;
/*  35:    */     }
/*  36: 42 */     str1 = str1.trim();
/*  37: 43 */     if (str1.length() > 30) {
/*  38: 44 */       return JSONResponses.INCORRECT_AUTOMATED_TRANSACTION_NAME_LENGTH;
/*  39:    */     }
/*  40: 46 */     String str3 = str1.toLowerCase();
/*  41: 47 */     for (int i = 0; i < str3.length(); i++) {
/*  42: 48 */       if ("0123456789abcdefghijklmnopqrstuvwxyz".indexOf(str3.charAt(i)) < 0) {
/*  43: 49 */         return JSONResponses.INCORRECT_AUTOMATED_TRANSACTION_NAME;
/*  44:    */       }
/*  45:    */     }
/*  46: 53 */     if ((str2 != null) && (str2.length() > 1000)) {
/*  47: 54 */       return JSONResponses.INCORRECT_AUTOMATED_TRANSACTION_DESCRIPTION;
/*  48:    */     }
/*  49: 57 */     byte[] arrayOfByte1 = null;
/*  50: 59 */     if (paramHttpServletRequest.getParameter("code") != null) {
/*  51:    */       try
/*  52:    */       {
/*  53: 61 */         String str4 = paramHttpServletRequest.getParameter("code");
/*  54: 62 */         if ((str4.length() & 0x1) != 0) {
/*  55: 63 */           throw new IllegalArgumentException();
/*  56:    */         }
/*  57: 65 */         localObject = paramHttpServletRequest.getParameter("data");
/*  58: 66 */         if (localObject == null) {
/*  59: 67 */           localObject = "";
/*  60:    */         }
/*  61: 68 */         if ((((String)localObject).length() & 0x1) != 0) {
/*  62: 69 */           throw new IllegalArgumentException();
/*  63:    */         }
/*  64: 71 */         int j = str4.length() / 2 / 256 + (str4.length() / 2 % 256 != 0 ? 1 : 0);
/*  65: 72 */         int k = Integer.parseInt(paramHttpServletRequest.getParameter("dpages"));
/*  66: 73 */         int m = Integer.parseInt(paramHttpServletRequest.getParameter("cspages"));
/*  67: 74 */         int n = Integer.parseInt(paramHttpServletRequest.getParameter("uspages"));
/*  68: 76 */         if ((k < 0) || (m < 0) || (n < 0)) {
/*  69: 77 */           throw new IllegalArgumentException();
/*  70:    */         }
/*  71: 79 */         long l = Convert.parseUnsignedLong(paramHttpServletRequest.getParameter("minActivationAmountNQT"));
/*  72:    */         
/*  73: 81 */         int i1 = 4;
/*  74: 82 */         i1 += 8;
/*  75: 83 */         i1 += 8;
/*  76: 84 */         i1 += (j * 256 <= 32767 ? 2 : j * 256 <= 256 ? 1 : 4);
/*  77: 85 */         i1 += str4.length() / 2;
/*  78: 86 */         i1 += (k * 256 <= 32767 ? 2 : k * 256 <= 256 ? 1 : 4);
/*  79: 87 */         i1 += ((String)localObject).length() / 2;
/*  80:    */         
/*  81: 89 */         ByteBuffer localByteBuffer = ByteBuffer.allocate(i1);
/*  82: 90 */         localByteBuffer.order(ByteOrder.LITTLE_ENDIAN);
/*  83: 91 */         localByteBuffer.putShort(AT_Constants.getInstance().AT_VERSION(Nxt.getBlockchain().getHeight()));
/*  84: 92 */         localByteBuffer.putShort((short)0);
/*  85: 93 */         localByteBuffer.putShort((short)j);
/*  86: 94 */         localByteBuffer.putShort((short)k);
/*  87: 95 */         localByteBuffer.putShort((short)m);
/*  88: 96 */         localByteBuffer.putShort((short)n);
/*  89: 97 */         localByteBuffer.putLong(l);
/*  90: 98 */         if (j * 256 <= 256) {
/*  91: 99 */           localByteBuffer.put((byte)(str4.length() / 2));
/*  92:100 */         } else if (j * 256 <= 32767) {
/*  93:101 */           localByteBuffer.putShort((short)(str4.length() / 2));
/*  94:    */         } else {
/*  95:103 */           localByteBuffer.putInt(str4.length() / 2);
/*  96:    */         }
/*  97:104 */         byte[] arrayOfByte2 = Convert.parseHexString(str4);
/*  98:105 */         if (arrayOfByte2 != null) {
/*  99:106 */           localByteBuffer.put(arrayOfByte2);
/* 100:    */         }
/* 101:107 */         if (k * 256 <= 256) {
/* 102:108 */           localByteBuffer.put((byte)(((String)localObject).length() / 2));
/* 103:109 */         } else if (k * 256 <= 32767) {
/* 104:110 */           localByteBuffer.putShort((short)(((String)localObject).length() / 2));
/* 105:    */         } else {
/* 106:112 */           localByteBuffer.putInt(((String)localObject).length() / 2);
/* 107:    */         }
/* 108:113 */         byte[] arrayOfByte3 = Convert.parseHexString((String)localObject);
/* 109:114 */         if (arrayOfByte3 != null) {
/* 110:115 */           localByteBuffer.put(arrayOfByte3);
/* 111:    */         }
/* 112:117 */         arrayOfByte1 = localByteBuffer.array();
/* 113:    */       }
/* 114:    */       catch (Exception localException)
/* 115:    */       {
/* 116:120 */         localException.printStackTrace(System.out);
/* 117:121 */         localObject = new JSONObject();
/* 118:122 */         ((JSONObject)localObject).put("errorCode", Integer.valueOf(5));
/* 119:123 */         ((JSONObject)localObject).put("errorDescription", "Invalid or not specified parameters");
/* 120:124 */         return (JSONStreamAware)localObject;
/* 121:    */       }
/* 122:    */     }
/* 123:128 */     if (arrayOfByte1 == null) {
/* 124:129 */       arrayOfByte1 = ParameterParser.getCreationBytes(paramHttpServletRequest);
/* 125:    */     }
/* 126:131 */     Account localAccount = ParameterParser.getSenderAccount(paramHttpServletRequest);
/* 127:132 */     Object localObject = new Attachment.AutomatedTransactionsCreation(str1, str2, arrayOfByte1);
/* 128:    */     
/* 129:134 */     System.out.println("AT " + str1 + " added succesfully ..");
/* 130:135 */     System.out.println();
/* 131:136 */     System.out.println();
/* 132:137 */     return createTransaction(paramHttpServletRequest, localAccount, (Attachment)localObject);
/* 133:    */   }
/* 134:    */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.http.CreateATProgram
 * JD-Core Version:    0.7.1
 */