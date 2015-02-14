/*   1:    */ package nxt.http;
/*   2:    */ 
/*   3:    */ import javax.servlet.http.HttpServletRequest;
/*   4:    */ import nxt.TransactionType;
/*   5:    */ import nxt.TransactionType.AccountControl;
/*   6:    */ import nxt.TransactionType.ColoredCoins;
/*   7:    */ import nxt.TransactionType.DigitalGoods;
/*   8:    */ import nxt.TransactionType.Messaging;
/*   9:    */ import nxt.TransactionType.Payment;
/*  10:    */ import nxt.util.Convert;
/*  11:    */ import nxt.util.JSON;
/*  12:    */ import org.json.simple.JSONArray;
/*  13:    */ import org.json.simple.JSONObject;
/*  14:    */ import org.json.simple.JSONStreamAware;
/*  15:    */ 
/*  16:    */ public final class GetConstants
/*  17:    */   extends APIServlet.APIRequestHandler
/*  18:    */ {
/*  19: 16 */   static final GetConstants instance = new GetConstants();
/*  20:    */   private static final JSONStreamAware CONSTANTS;
/*  21:    */   
/*  22:    */   static
/*  23:    */   {
/*  24: 22 */     JSONObject localJSONObject1 = new JSONObject();
/*  25: 23 */     localJSONObject1.put("genesisBlockId", Convert.toUnsignedLong(3444294670862540038L));
/*  26: 24 */     localJSONObject1.put("genesisAccountId", Convert.toUnsignedLong(0L));
/*  27: 25 */     localJSONObject1.put("maxBlockPayloadLength", Integer.valueOf(44880));
/*  28: 26 */     localJSONObject1.put("maxArbitraryMessageLength", Integer.valueOf(1000));
/*  29:    */     
/*  30: 28 */     JSONArray localJSONArray1 = new JSONArray();
/*  31: 29 */     JSONObject localJSONObject2 = new JSONObject();
/*  32: 30 */     localJSONObject2.put("value", Byte.valueOf(TransactionType.Payment.ORDINARY.getType()));
/*  33: 31 */     localJSONObject2.put("description", "Payment");
/*  34: 32 */     JSONArray localJSONArray2 = new JSONArray();
/*  35: 33 */     JSONObject localJSONObject3 = new JSONObject();
/*  36: 34 */     localJSONObject3.put("value", Byte.valueOf(TransactionType.Payment.ORDINARY.getSubtype()));
/*  37: 35 */     localJSONObject3.put("description", "Ordinary payment");
/*  38: 36 */     localJSONArray2.add(localJSONObject3);
/*  39: 37 */     localJSONObject2.put("subtypes", localJSONArray2);
/*  40: 38 */     localJSONArray1.add(localJSONObject2);
/*  41: 39 */     localJSONObject2 = new JSONObject();
/*  42: 40 */     localJSONObject2.put("value", Byte.valueOf(TransactionType.Messaging.ARBITRARY_MESSAGE.getType()));
/*  43: 41 */     localJSONObject2.put("description", "Messaging");
/*  44: 42 */     localJSONArray2 = new JSONArray();
/*  45: 43 */     localJSONObject3 = new JSONObject();
/*  46: 44 */     localJSONObject3.put("value", Byte.valueOf(TransactionType.Messaging.ARBITRARY_MESSAGE.getSubtype()));
/*  47: 45 */     localJSONObject3.put("description", "Arbitrary message");
/*  48: 46 */     localJSONArray2.add(localJSONObject3);
/*  49: 47 */     localJSONObject3 = new JSONObject();
/*  50: 48 */     localJSONObject3.put("value", Byte.valueOf(TransactionType.Messaging.ALIAS_ASSIGNMENT.getSubtype()));
/*  51: 49 */     localJSONObject3.put("description", "Alias assignment");
/*  52: 50 */     localJSONArray2.add(localJSONObject3);
/*  53: 51 */     localJSONObject3 = new JSONObject();
/*  54: 52 */     localJSONObject3.put("value", Byte.valueOf(TransactionType.Messaging.ALIAS_SELL.getSubtype()));
/*  55: 53 */     localJSONObject3.put("description", "Alias sell");
/*  56: 54 */     localJSONArray2.add(localJSONObject3);
/*  57: 55 */     localJSONObject3 = new JSONObject();
/*  58: 56 */     localJSONObject3.put("value", Byte.valueOf(TransactionType.Messaging.ALIAS_BUY.getSubtype()));
/*  59: 57 */     localJSONObject3.put("description", "Alias buy");
/*  60: 58 */     localJSONArray2.add(localJSONObject3);
/*  61: 59 */     localJSONObject3 = new JSONObject();
/*  62: 60 */     localJSONObject3.put("value", Byte.valueOf(TransactionType.Messaging.POLL_CREATION.getSubtype()));
/*  63: 61 */     localJSONObject3.put("description", "Poll creation");
/*  64: 62 */     localJSONArray2.add(localJSONObject3);
/*  65: 63 */     localJSONObject3 = new JSONObject();
/*  66: 64 */     localJSONObject3.put("value", Byte.valueOf(TransactionType.Messaging.VOTE_CASTING.getSubtype()));
/*  67: 65 */     localJSONObject3.put("description", "Vote casting");
/*  68: 66 */     localJSONArray2.add(localJSONObject3);
/*  69: 67 */     localJSONObject3 = new JSONObject();
/*  70: 68 */     localJSONObject3.put("value", Byte.valueOf(TransactionType.Messaging.HUB_ANNOUNCEMENT.getSubtype()));
/*  71: 69 */     localJSONObject3.put("description", "Hub terminal announcement");
/*  72: 70 */     localJSONArray2.add(localJSONObject3);
/*  73: 71 */     localJSONObject3 = new JSONObject();
/*  74: 72 */     localJSONObject3.put("value", Byte.valueOf(TransactionType.Messaging.ACCOUNT_INFO.getSubtype()));
/*  75: 73 */     localJSONObject3.put("description", "Account info");
/*  76: 74 */     localJSONArray2.add(localJSONObject3);
/*  77: 75 */     localJSONObject2.put("subtypes", localJSONArray2);
/*  78: 76 */     localJSONArray1.add(localJSONObject2);
/*  79: 77 */     localJSONObject2 = new JSONObject();
/*  80: 78 */     localJSONObject2.put("value", Byte.valueOf(TransactionType.ColoredCoins.ASSET_ISSUANCE.getType()));
/*  81: 79 */     localJSONObject2.put("description", "Colored coins");
/*  82: 80 */     localJSONArray2 = new JSONArray();
/*  83: 81 */     localJSONObject3 = new JSONObject();
/*  84: 82 */     localJSONObject3.put("value", Byte.valueOf(TransactionType.ColoredCoins.ASSET_ISSUANCE.getSubtype()));
/*  85: 83 */     localJSONObject3.put("description", "Asset issuance");
/*  86: 84 */     localJSONArray2.add(localJSONObject3);
/*  87: 85 */     localJSONObject3 = new JSONObject();
/*  88: 86 */     localJSONObject3.put("value", Byte.valueOf(TransactionType.ColoredCoins.ASSET_TRANSFER.getSubtype()));
/*  89: 87 */     localJSONObject3.put("description", "Asset transfer");
/*  90: 88 */     localJSONArray2.add(localJSONObject3);
/*  91: 89 */     localJSONObject3 = new JSONObject();
/*  92: 90 */     localJSONObject3.put("value", Byte.valueOf(TransactionType.ColoredCoins.ASK_ORDER_PLACEMENT.getSubtype()));
/*  93: 91 */     localJSONObject3.put("description", "Ask order placement");
/*  94: 92 */     localJSONArray2.add(localJSONObject3);
/*  95: 93 */     localJSONObject3 = new JSONObject();
/*  96: 94 */     localJSONObject3.put("value", Byte.valueOf(TransactionType.ColoredCoins.BID_ORDER_PLACEMENT.getSubtype()));
/*  97: 95 */     localJSONObject3.put("description", "Bid order placement");
/*  98: 96 */     localJSONArray2.add(localJSONObject3);
/*  99: 97 */     localJSONObject3 = new JSONObject();
/* 100: 98 */     localJSONObject3.put("value", Byte.valueOf(TransactionType.ColoredCoins.ASK_ORDER_CANCELLATION.getSubtype()));
/* 101: 99 */     localJSONObject3.put("description", "Ask order cancellation");
/* 102:100 */     localJSONArray2.add(localJSONObject3);
/* 103:101 */     localJSONObject3 = new JSONObject();
/* 104:102 */     localJSONObject3.put("value", Byte.valueOf(TransactionType.ColoredCoins.BID_ORDER_CANCELLATION.getSubtype()));
/* 105:103 */     localJSONObject3.put("description", "Bid order cancellation");
/* 106:104 */     localJSONArray2.add(localJSONObject3);
/* 107:105 */     localJSONObject2.put("subtypes", localJSONArray2);
/* 108:106 */     localJSONArray1.add(localJSONObject2);
/* 109:107 */     localJSONObject2 = new JSONObject();
/* 110:108 */     localJSONObject2.put("value", Byte.valueOf(TransactionType.DigitalGoods.LISTING.getType()));
/* 111:109 */     localJSONObject2.put("description", "Digital goods");
/* 112:110 */     localJSONArray2 = new JSONArray();
/* 113:111 */     localJSONObject3 = new JSONObject();
/* 114:112 */     localJSONObject3.put("value", Byte.valueOf(TransactionType.DigitalGoods.LISTING.getSubtype()));
/* 115:113 */     localJSONObject3.put("description", "Listing");
/* 116:114 */     localJSONArray2.add(localJSONObject3);
/* 117:115 */     localJSONObject3 = new JSONObject();
/* 118:116 */     localJSONObject3.put("value", Byte.valueOf(TransactionType.DigitalGoods.DELISTING.getSubtype()));
/* 119:117 */     localJSONObject3.put("description", "Delisting");
/* 120:118 */     localJSONArray2.add(localJSONObject3);
/* 121:119 */     localJSONObject3 = new JSONObject();
/* 122:120 */     localJSONObject3.put("value", Byte.valueOf(TransactionType.DigitalGoods.PRICE_CHANGE.getSubtype()));
/* 123:121 */     localJSONObject3.put("description", "Price change");
/* 124:122 */     localJSONArray2.add(localJSONObject3);
/* 125:123 */     localJSONObject3 = new JSONObject();
/* 126:124 */     localJSONObject3.put("value", Byte.valueOf(TransactionType.DigitalGoods.QUANTITY_CHANGE.getSubtype()));
/* 127:125 */     localJSONObject3.put("description", "Quantity change");
/* 128:126 */     localJSONArray2.add(localJSONObject3);
/* 129:127 */     localJSONObject3 = new JSONObject();
/* 130:128 */     localJSONObject3.put("value", Byte.valueOf(TransactionType.DigitalGoods.PURCHASE.getSubtype()));
/* 131:129 */     localJSONObject3.put("description", "Purchase");
/* 132:130 */     localJSONArray2.add(localJSONObject3);
/* 133:131 */     localJSONObject3 = new JSONObject();
/* 134:132 */     localJSONObject3.put("value", Byte.valueOf(TransactionType.DigitalGoods.DELIVERY.getSubtype()));
/* 135:133 */     localJSONObject3.put("description", "Delivery");
/* 136:134 */     localJSONArray2.add(localJSONObject3);
/* 137:135 */     localJSONObject3 = new JSONObject();
/* 138:136 */     localJSONObject3.put("value", Byte.valueOf(TransactionType.DigitalGoods.FEEDBACK.getSubtype()));
/* 139:137 */     localJSONObject3.put("description", "Feedback");
/* 140:138 */     localJSONArray2.add(localJSONObject3);
/* 141:139 */     localJSONObject3 = new JSONObject();
/* 142:140 */     localJSONObject3.put("value", Byte.valueOf(TransactionType.DigitalGoods.REFUND.getSubtype()));
/* 143:141 */     localJSONObject3.put("description", "Refund");
/* 144:142 */     localJSONArray2.add(localJSONObject3);
/* 145:143 */     localJSONObject2.put("subtypes", localJSONArray2);
/* 146:144 */     localJSONArray1.add(localJSONObject2);
/* 147:145 */     localJSONObject2 = new JSONObject();
/* 148:146 */     localJSONObject2.put("value", Byte.valueOf(TransactionType.AccountControl.EFFECTIVE_BALANCE_LEASING.getType()));
/* 149:147 */     localJSONObject2.put("description", "Account Control");
/* 150:148 */     localJSONArray2 = new JSONArray();
/* 151:149 */     localJSONObject3 = new JSONObject();
/* 152:150 */     localJSONObject3.put("value", Byte.valueOf(TransactionType.AccountControl.EFFECTIVE_BALANCE_LEASING.getSubtype()));
/* 153:151 */     localJSONObject3.put("description", "Effective balance leasing");
/* 154:152 */     localJSONArray2.add(localJSONObject3);
/* 155:153 */     localJSONObject2.put("subtypes", localJSONArray2);
/* 156:154 */     localJSONArray1.add(localJSONObject2);
/* 157:155 */     localJSONObject1.put("transactionTypes", localJSONArray1);
/* 158:    */     
/* 159:157 */     JSONArray localJSONArray3 = new JSONArray();
/* 160:158 */     JSONObject localJSONObject4 = new JSONObject();
/* 161:159 */     localJSONObject4.put("value", Integer.valueOf(0));
/* 162:160 */     localJSONObject4.put("description", "Non-connected");
/* 163:161 */     localJSONArray3.add(localJSONObject4);
/* 164:162 */     localJSONObject4 = new JSONObject();
/* 165:163 */     localJSONObject4.put("value", Integer.valueOf(1));
/* 166:164 */     localJSONObject4.put("description", "Connected");
/* 167:165 */     localJSONArray3.add(localJSONObject4);
/* 168:166 */     localJSONObject4 = new JSONObject();
/* 169:167 */     localJSONObject4.put("value", Integer.valueOf(2));
/* 170:168 */     localJSONObject4.put("description", "Disconnected");
/* 171:169 */     localJSONArray3.add(localJSONObject4);
/* 172:170 */     localJSONObject1.put("peerStates", localJSONArray3);
/* 173:    */     
/* 174:172 */     CONSTANTS = JSON.prepare(localJSONObject1);
/* 175:    */   }
/* 176:    */   
/* 177:    */   private GetConstants()
/* 178:    */   {
/* 179:177 */     super(new APITag[] { APITag.INFO }, new String[0]);
/* 180:    */   }
/* 181:    */   
/* 182:    */   JSONStreamAware processRequest(HttpServletRequest paramHttpServletRequest)
/* 183:    */   {
/* 184:182 */     return CONSTANTS;
/* 185:    */   }
/* 186:    */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.http.GetConstants
 * JD-Core Version:    0.7.1
 */