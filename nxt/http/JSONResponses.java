/*   1:    */ package nxt.http;
/*   2:    */ 
/*   3:    */ import java.util.Arrays;
/*   4:    */ import nxt.util.JSON;
/*   5:    */ import org.json.simple.JSONObject;
/*   6:    */ import org.json.simple.JSONStreamAware;
/*   7:    */ 
/*   8:    */ public final class JSONResponses
/*   9:    */ {
/*  10: 12 */   public static final JSONStreamAware INCORRECT_ALIAS = incorrect("alias");
/*  11: 13 */   public static final JSONStreamAware INCORRECT_ALIAS_OWNER = incorrect("alias", "(invalid alias owner)");
/*  12: 14 */   public static final JSONStreamAware INCORRECT_ALIAS_LENGTH = incorrect("alias", "(length must be in [1..100] range)");
/*  13: 15 */   public static final JSONStreamAware INCORRECT_ALIAS_NAME = incorrect("alias", "(must contain only digits and latin letters)");
/*  14: 16 */   public static final JSONStreamAware INCORRECT_ALIAS_NOTFORSALE = incorrect("alias", "(alias is not for sale at the moment)");
/*  15: 17 */   public static final JSONStreamAware INCORRECT_URI_LENGTH = incorrect("uri", "(length must be not longer than 1000 characters)");
/*  16: 18 */   public static final JSONStreamAware MISSING_SECRET_PHRASE = missing(new String[] { "secretPhrase" });
/*  17: 19 */   public static final JSONStreamAware INCORRECT_PUBLIC_KEY = incorrect("publicKey");
/*  18: 20 */   public static final JSONStreamAware MISSING_ALIAS_NAME = missing(new String[] { "aliasName" });
/*  19: 21 */   public static final JSONStreamAware MISSING_ALIAS_OR_ALIAS_NAME = missing(new String[] { "alias", "aliasName" });
/*  20: 22 */   public static final JSONStreamAware MISSING_FEE = missing(new String[] { "feeNQT" });
/*  21: 23 */   public static final JSONStreamAware MISSING_DEADLINE = missing(new String[] { "deadline" });
/*  22: 24 */   public static final JSONStreamAware INCORRECT_DEADLINE = incorrect("deadline");
/*  23: 25 */   public static final JSONStreamAware INCORRECT_FEE = incorrect("fee");
/*  24: 26 */   public static final JSONStreamAware MISSING_TRANSACTION_BYTES_OR_JSON = missing(new String[] { "transactionBytes", "transactionJSON" });
/*  25: 27 */   public static final JSONStreamAware INCORRECT_TRANSACTION_BYTES_OR_JSON = incorrect("transactionBytes or transactionJSON");
/*  26: 28 */   public static final JSONStreamAware MISSING_ORDER = missing(new String[] { "order" });
/*  27: 29 */   public static final JSONStreamAware INCORRECT_ORDER = incorrect("order");
/*  28: 30 */   public static final JSONStreamAware UNKNOWN_ORDER = unknown("order");
/*  29: 31 */   public static final JSONStreamAware MISSING_HALLMARK = missing(new String[] { "hallmark" });
/*  30: 32 */   public static final JSONStreamAware INCORRECT_HALLMARK = incorrect("hallmark");
/*  31: 33 */   public static final JSONStreamAware MISSING_WEBSITE = missing(new String[] { "website" });
/*  32: 34 */   public static final JSONStreamAware INCORRECT_WEBSITE = incorrect("website");
/*  33: 35 */   public static final JSONStreamAware MISSING_TOKEN = missing(new String[] { "token" });
/*  34: 36 */   public static final JSONStreamAware INCORRECT_TOKEN = incorrect("token");
/*  35: 37 */   public static final JSONStreamAware MISSING_ACCOUNT = missing(new String[] { "account" });
/*  36: 38 */   public static final JSONStreamAware INCORRECT_ACCOUNT = incorrect("account");
/*  37: 39 */   public static final JSONStreamAware MISSING_TIMESTAMP = missing(new String[] { "timestamp" });
/*  38: 40 */   public static final JSONStreamAware INCORRECT_TIMESTAMP = incorrect("timestamp");
/*  39: 41 */   public static final JSONStreamAware UNKNOWN_ACCOUNT = unknown("account");
/*  40: 42 */   public static final JSONStreamAware UNKNOWN_ALIAS = unknown("alias");
/*  41: 43 */   public static final JSONStreamAware MISSING_ASSET = missing(new String[] { "asset" });
/*  42: 44 */   public static final JSONStreamAware UNKNOWN_ASSET = unknown("asset");
/*  43: 45 */   public static final JSONStreamAware INCORRECT_ASSET = incorrect("asset");
/*  44: 46 */   public static final JSONStreamAware MISSING_ASSET_NAME = missing(new String[] { "assetName" });
/*  45: 47 */   public static final JSONStreamAware MISSING_BLOCK = missing(new String[] { "block" });
/*  46: 48 */   public static final JSONStreamAware UNKNOWN_BLOCK = unknown("block");
/*  47: 49 */   public static final JSONStreamAware INCORRECT_BLOCK = incorrect("block");
/*  48: 50 */   public static final JSONStreamAware MISSING_NUMBER_OF_CONFIRMATIONS = missing(new String[] { "numberOfConfirmations" });
/*  49: 51 */   public static final JSONStreamAware INCORRECT_NUMBER_OF_CONFIRMATIONS = incorrect("numberOfConfirmations");
/*  50: 52 */   public static final JSONStreamAware MISSING_PEER = missing(new String[] { "peer" });
/*  51: 53 */   public static final JSONStreamAware UNKNOWN_PEER = unknown("peer");
/*  52: 54 */   public static final JSONStreamAware MISSING_TRANSACTION = missing(new String[] { "transaction" });
/*  53: 55 */   public static final JSONStreamAware UNKNOWN_TRANSACTION = unknown("transaction");
/*  54: 56 */   public static final JSONStreamAware INCORRECT_TRANSACTION = incorrect("transaction");
/*  55: 57 */   public static final JSONStreamAware INCORRECT_ASSET_DESCRIPTION = incorrect("description", "(length must not exceed 1000 characters)");
/*  56: 58 */   public static final JSONStreamAware INCORRECT_ASSET_NAME = incorrect("name", "(must contain only digits and latin letters)");
/*  57: 59 */   public static final JSONStreamAware INCORRECT_ASSET_NAME_LENGTH = incorrect("name", "(length must be in [3..10] range)");
/*  58: 60 */   public static final JSONStreamAware INCORRECT_ASSET_TRANSFER_COMMENT = incorrect("comment", "(length must not exceed 1000 characters)");
/*  59: 61 */   public static final JSONStreamAware MISSING_NAME = missing(new String[] { "name" });
/*  60: 62 */   public static final JSONStreamAware MISSING_QUANTITY = missing(new String[] { "quantityQNT" });
/*  61: 63 */   public static final JSONStreamAware INCORRECT_QUANTITY = incorrect("quantity");
/*  62: 64 */   public static final JSONStreamAware INCORRECT_ASSET_QUANTITY = incorrect("quantity", "(must be in [1..100000000000000000] range)");
/*  63: 65 */   public static final JSONStreamAware INCORRECT_DECIMALS = incorrect("decimals");
/*  64: 66 */   public static final JSONStreamAware MISSING_HOST = missing(new String[] { "host" });
/*  65: 67 */   public static final JSONStreamAware MISSING_DATE = missing(new String[] { "date" });
/*  66: 68 */   public static final JSONStreamAware MISSING_WEIGHT = missing(new String[] { "weight" });
/*  67: 69 */   public static final JSONStreamAware INCORRECT_HOST = incorrect("host", "(the length exceeds 100 chars limit)");
/*  68: 70 */   public static final JSONStreamAware INCORRECT_WEIGHT = incorrect("weight");
/*  69: 71 */   public static final JSONStreamAware INCORRECT_DATE = incorrect("date");
/*  70: 72 */   public static final JSONStreamAware MISSING_PRICE = missing(new String[] { "priceNQT" });
/*  71: 73 */   public static final JSONStreamAware INCORRECT_PRICE = incorrect("price");
/*  72: 74 */   public static final JSONStreamAware INCORRECT_REFERENCED_TRANSACTION = incorrect("referencedTransactionFullHash");
/*  73: 75 */   public static final JSONStreamAware MISSING_MESSAGE = missing(new String[] { "message" });
/*  74: 76 */   public static final JSONStreamAware MISSING_RECIPIENT = missing(new String[] { "recipient" });
/*  75: 77 */   public static final JSONStreamAware INCORRECT_RECIPIENT = incorrect("recipient");
/*  76: 78 */   public static final JSONStreamAware INCORRECT_ARBITRARY_MESSAGE = incorrect("message");
/*  77: 79 */   public static final JSONStreamAware MISSING_AMOUNT = missing(new String[] { "amountNQT" });
/*  78: 80 */   public static final JSONStreamAware INCORRECT_AMOUNT = incorrect("amount");
/*  79: 81 */   public static final JSONStreamAware MISSING_DESCRIPTION = missing(new String[] { "description" });
/*  80: 82 */   public static final JSONStreamAware MISSING_MINNUMBEROFOPTIONS = missing(new String[] { "minNumberOfOptions" });
/*  81: 83 */   public static final JSONStreamAware MISSING_MAXNUMBEROFOPTIONS = missing(new String[] { "maxNumberOfOptions" });
/*  82: 84 */   public static final JSONStreamAware MISSING_OPTIONSAREBINARY = missing(new String[] { "optionsAreBinary" });
/*  83: 85 */   public static final JSONStreamAware MISSING_POLL = missing(new String[] { "poll" });
/*  84: 86 */   public static final JSONStreamAware INCORRECT_POLL_NAME_LENGTH = incorrect("name", "(length must be not longer than 100 characters)");
/*  85: 87 */   public static final JSONStreamAware INCORRECT_POLL_DESCRIPTION_LENGTH = incorrect("description", "(length must be not longer than 1000 characters)");
/*  86: 88 */   public static final JSONStreamAware INCORRECT_POLL_OPTION_LENGTH = incorrect("option", "(length must be not longer than 100 characters)");
/*  87: 89 */   public static final JSONStreamAware INCORRECT_MINNUMBEROFOPTIONS = incorrect("minNumberOfOptions");
/*  88: 90 */   public static final JSONStreamAware INCORRECT_MAXNUMBEROFOPTIONS = incorrect("maxNumberOfOptions");
/*  89: 91 */   public static final JSONStreamAware INCORRECT_OPTIONSAREBINARY = incorrect("optionsAreBinary");
/*  90: 92 */   public static final JSONStreamAware INCORRECT_POLL = incorrect("poll");
/*  91: 93 */   public static final JSONStreamAware INCORRECT_VOTE = incorrect("vote");
/*  92: 94 */   public static final JSONStreamAware UNKNOWN_POLL = unknown("poll");
/*  93: 95 */   public static final JSONStreamAware INCORRECT_ACCOUNT_NAME_LENGTH = incorrect("name", "(length must be less than 100 characters)");
/*  94: 96 */   public static final JSONStreamAware INCORRECT_ACCOUNT_DESCRIPTION_LENGTH = incorrect("description", "(length must be less than 1000 characters)");
/*  95: 97 */   public static final JSONStreamAware MISSING_PERIOD = missing(new String[] { "period" });
/*  96: 98 */   public static final JSONStreamAware INCORRECT_PERIOD = incorrect("period", "(period must be at least 1440 blocks)");
/*  97: 99 */   public static final JSONStreamAware INCORRECT_UNSIGNED_BYTES = incorrect("unsignedTransactionBytes");
/*  98:100 */   public static final JSONStreamAware MISSING_UNSIGNED_BYTES = missing(new String[] { "unsignedTransactionBytes" });
/*  99:101 */   public static final JSONStreamAware MISSING_SIGNATURE_HASH = missing(new String[] { "signatureHash" });
/* 100:102 */   public static final JSONStreamAware INCORRECT_DGS_LISTING_NAME = incorrect("name", "(length must be not longer than 100 characters)");
/* 101:103 */   public static final JSONStreamAware INCORRECT_DGS_LISTING_DESCRIPTION = incorrect("description", "(length must be not longer than 1000 characters)");
/* 102:104 */   public static final JSONStreamAware INCORRECT_DGS_LISTING_TAGS = incorrect("tags", "(length must be not longer than 100 characters)");
/* 103:105 */   public static final JSONStreamAware MISSING_GOODS = missing(new String[] { "goods" });
/* 104:106 */   public static final JSONStreamAware INCORRECT_GOODS = incorrect("goods");
/* 105:107 */   public static final JSONStreamAware UNKNOWN_GOODS = unknown("goods");
/* 106:108 */   public static final JSONStreamAware INCORRECT_DELTA_QUANTITY = incorrect("deltaQuantity");
/* 107:109 */   public static final JSONStreamAware MISSING_DELTA_QUANTITY = missing(new String[] { "deltaQuantity" });
/* 108:110 */   public static final JSONStreamAware MISSING_DELIVERY_DEADLINE_TIMESTAMP = missing(new String[] { "deliveryDeadlineTimestamp" });
/* 109:111 */   public static final JSONStreamAware INCORRECT_DELIVERY_DEADLINE_TIMESTAMP = incorrect("deliveryDeadlineTimestamp");
/* 110:112 */   public static final JSONStreamAware INCORRECT_PURCHASE_QUANTITY = incorrect("quantity", "(quantity exceeds available goods quantity)");
/* 111:113 */   public static final JSONStreamAware INCORRECT_PURCHASE_PRICE = incorrect("priceNQT", "(purchase price doesn't match goods price)");
/* 112:114 */   public static final JSONStreamAware INCORRECT_PURCHASE = incorrect("purchase");
/* 113:115 */   public static final JSONStreamAware MISSING_PURCHASE = missing(new String[] { "purchase" });
/* 114:116 */   public static final JSONStreamAware INCORRECT_DGS_GOODS = incorrect("goodsToEncrypt");
/* 115:117 */   public static final JSONStreamAware INCORRECT_DGS_DISCOUNT = incorrect("discountNQT");
/* 116:118 */   public static final JSONStreamAware INCORRECT_DGS_REFUND = incorrect("refundNQT");
/* 117:119 */   public static final JSONStreamAware MISSING_SELLER = missing(new String[] { "seller" });
/* 118:120 */   public static final JSONStreamAware INCORRECT_ENCRYPTED_MESSAGE = incorrect("encryptedMessageData");
/* 119:121 */   public static final JSONStreamAware INCORRECT_DGS_ENCRYPTED_GOODS = incorrect("goodsData");
/* 120:122 */   public static final JSONStreamAware MISSING_SECRET_PHRASE_OR_PUBLIC_KEY = missing(new String[] { "secretPhrase", "publicKey" });
/* 121:123 */   public static final JSONStreamAware INCORRECT_HEIGHT = incorrect("height");
/* 122:124 */   public static final JSONStreamAware MISSING_HEIGHT = missing(new String[] { "height" });
/* 123:125 */   public static final JSONStreamAware INCORRECT_PLAIN_MESSAGE = incorrect("messageToEncrypt");
/* 124:127 */   public static final JSONStreamAware INCORRECT_AUTOMATED_TRANSACTION_NAME_LENGTH = incorrect("description", "(length must not exceed 30 characters)");
/* 125:128 */   public static final JSONStreamAware INCORRECT_AUTOMATED_TRANSACTION_DESCRIPTION_LENGTH = incorrect("description", "(length must not exceed 1000 characters)");
/* 126:129 */   public static final JSONStreamAware INCORRECT_AUTOMATED_TRANSACTION_NAME = incorrect("name", "(must contain only digits and latin letters)");
/* 127:130 */   public static final JSONStreamAware INCORRECT_AUTOMATED_TRANSACTION_DESCRIPTION = incorrect("description", "(length must not exceed 1000 characters)");
/* 128:131 */   public static final JSONStreamAware MISSING_AT = missing(new String[] { "at" });
/* 129:132 */   public static final JSONStreamAware UNKNOWN_AT = unknown("at");
/* 130:133 */   public static final JSONStreamAware INCORRECT_AT = incorrect("at");
/* 131:134 */   public static final JSONStreamAware INCORRECT_CREATION_BYTES = incorrect("incorrect creation bytes");
/* 132:    */   public static final JSONStreamAware NOT_ENOUGH_FUNDS;
/* 133:    */   public static final JSONStreamAware NOT_ENOUGH_ASSETS;
/* 134:    */   public static final JSONStreamAware ERROR_NOT_ALLOWED;
/* 135:    */   public static final JSONStreamAware ERROR_INCORRECT_REQUEST;
/* 136:    */   public static final JSONStreamAware NOT_FORGING;
/* 137:    */   public static final JSONStreamAware POST_REQUIRED;
/* 138:    */   public static final JSONStreamAware FEATURE_NOT_AVAILABLE;
/* 139:    */   public static final JSONStreamAware DECRYPTION_FAILED;
/* 140:    */   public static final JSONStreamAware ALREADY_DELIVERED;
/* 141:    */   public static final JSONStreamAware DUPLICATE_REFUND;
/* 142:    */   public static final JSONStreamAware GOODS_NOT_DELIVERED;
/* 143:    */   public static final JSONStreamAware NO_MESSAGE;
/* 144:    */   public static final JSONStreamAware HEIGHT_NOT_AVAILABLE;
/* 145:    */   
/* 146:    */   static
/* 147:    */   {
/* 148:139 */     JSONObject localJSONObject = new JSONObject();
/* 149:140 */     localJSONObject.put("errorCode", Integer.valueOf(6));
/* 150:141 */     localJSONObject.put("errorDescription", "Not enough funds");
/* 151:142 */     NOT_ENOUGH_FUNDS = JSON.prepare(localJSONObject);
/* 152:    */     
/* 153:    */ 
/* 154:    */ 
/* 155:    */ 
/* 156:147 */     localJSONObject = new JSONObject();
/* 157:148 */     localJSONObject.put("errorCode", Integer.valueOf(6));
/* 158:149 */     localJSONObject.put("errorDescription", "Not enough assets");
/* 159:150 */     NOT_ENOUGH_ASSETS = JSON.prepare(localJSONObject);
/* 160:    */     
/* 161:    */ 
/* 162:    */ 
/* 163:    */ 
/* 164:155 */     localJSONObject = new JSONObject();
/* 165:156 */     localJSONObject.put("errorCode", Integer.valueOf(7));
/* 166:157 */     localJSONObject.put("errorDescription", "Not allowed");
/* 167:158 */     ERROR_NOT_ALLOWED = JSON.prepare(localJSONObject);
/* 168:    */     
/* 169:    */ 
/* 170:    */ 
/* 171:    */ 
/* 172:163 */     localJSONObject = new JSONObject();
/* 173:164 */     localJSONObject.put("errorCode", Integer.valueOf(1));
/* 174:165 */     localJSONObject.put("errorDescription", "Incorrect request");
/* 175:166 */     ERROR_INCORRECT_REQUEST = JSON.prepare(localJSONObject);
/* 176:    */     
/* 177:    */ 
/* 178:    */ 
/* 179:    */ 
/* 180:171 */     localJSONObject = new JSONObject();
/* 181:172 */     localJSONObject.put("errorCode", Integer.valueOf(5));
/* 182:173 */     localJSONObject.put("errorDescription", "Account is not forging");
/* 183:174 */     NOT_FORGING = JSON.prepare(localJSONObject);
/* 184:    */     
/* 185:    */ 
/* 186:    */ 
/* 187:    */ 
/* 188:179 */     localJSONObject = new JSONObject();
/* 189:180 */     localJSONObject.put("errorCode", Integer.valueOf(1));
/* 190:181 */     localJSONObject.put("errorDescription", "This request is only accepted using POST!");
/* 191:182 */     POST_REQUIRED = JSON.prepare(localJSONObject);
/* 192:    */     
/* 193:    */ 
/* 194:    */ 
/* 195:    */ 
/* 196:187 */     localJSONObject = new JSONObject();
/* 197:188 */     localJSONObject.put("errorCode", Integer.valueOf(9));
/* 198:189 */     localJSONObject.put("errorDescription", "Feature not available");
/* 199:190 */     FEATURE_NOT_AVAILABLE = JSON.prepare(localJSONObject);
/* 200:    */     
/* 201:    */ 
/* 202:    */ 
/* 203:    */ 
/* 204:195 */     localJSONObject = new JSONObject();
/* 205:196 */     localJSONObject.put("errorCode", Integer.valueOf(8));
/* 206:197 */     localJSONObject.put("errorDescription", "Decryption failed");
/* 207:198 */     DECRYPTION_FAILED = JSON.prepare(localJSONObject);
/* 208:    */     
/* 209:    */ 
/* 210:    */ 
/* 211:    */ 
/* 212:203 */     localJSONObject = new JSONObject();
/* 213:204 */     localJSONObject.put("errorCode", Integer.valueOf(8));
/* 214:205 */     localJSONObject.put("errorDescription", "Purchase already delivered");
/* 215:206 */     ALREADY_DELIVERED = JSON.prepare(localJSONObject);
/* 216:    */     
/* 217:    */ 
/* 218:    */ 
/* 219:    */ 
/* 220:211 */     localJSONObject = new JSONObject();
/* 221:212 */     localJSONObject.put("errorCode", Integer.valueOf(8));
/* 222:213 */     localJSONObject.put("errorDescription", "Refund already sent");
/* 223:214 */     DUPLICATE_REFUND = JSON.prepare(localJSONObject);
/* 224:    */     
/* 225:    */ 
/* 226:    */ 
/* 227:    */ 
/* 228:219 */     localJSONObject = new JSONObject();
/* 229:220 */     localJSONObject.put("errorCode", Integer.valueOf(8));
/* 230:221 */     localJSONObject.put("errorDescription", "Goods have not been delivered yet");
/* 231:222 */     GOODS_NOT_DELIVERED = JSON.prepare(localJSONObject);
/* 232:    */     
/* 233:    */ 
/* 234:    */ 
/* 235:    */ 
/* 236:227 */     localJSONObject = new JSONObject();
/* 237:228 */     localJSONObject.put("errorCode", Integer.valueOf(8));
/* 238:229 */     localJSONObject.put("errorDescription", "No attached message found");
/* 239:230 */     NO_MESSAGE = JSON.prepare(localJSONObject);
/* 240:    */     
/* 241:    */ 
/* 242:    */ 
/* 243:    */ 
/* 244:235 */     localJSONObject = new JSONObject();
/* 245:236 */     localJSONObject.put("errorCode", Integer.valueOf(8));
/* 246:237 */     localJSONObject.put("errorDescription", "Requested height not available");
/* 247:238 */     HEIGHT_NOT_AVAILABLE = JSON.prepare(localJSONObject);
/* 248:    */   }
/* 249:    */   
/* 250:    */   private static JSONStreamAware missing(String... paramVarArgs)
/* 251:    */   {
/* 252:242 */     JSONObject localJSONObject = new JSONObject();
/* 253:243 */     localJSONObject.put("errorCode", Integer.valueOf(3));
/* 254:244 */     if (paramVarArgs.length == 1) {
/* 255:245 */       localJSONObject.put("errorDescription", "\"" + paramVarArgs[0] + "\"" + " not specified");
/* 256:    */     } else {
/* 257:247 */       localJSONObject.put("errorDescription", "At least one of " + Arrays.toString(paramVarArgs) + " must be specified");
/* 258:    */     }
/* 259:249 */     return JSON.prepare(localJSONObject);
/* 260:    */   }
/* 261:    */   
/* 262:    */   private static JSONStreamAware incorrect(String paramString)
/* 263:    */   {
/* 264:253 */     return incorrect(paramString, null);
/* 265:    */   }
/* 266:    */   
/* 267:    */   private static JSONStreamAware incorrect(String paramString1, String paramString2)
/* 268:    */   {
/* 269:257 */     JSONObject localJSONObject = new JSONObject();
/* 270:258 */     localJSONObject.put("errorCode", Integer.valueOf(4));
/* 271:259 */     localJSONObject.put("errorDescription", "Incorrect \"" + paramString1 + (paramString2 != null ? "\" " + paramString2 : "\""));
/* 272:260 */     return JSON.prepare(localJSONObject);
/* 273:    */   }
/* 274:    */   
/* 275:    */   private static JSONStreamAware unknown(String paramString)
/* 276:    */   {
/* 277:264 */     JSONObject localJSONObject = new JSONObject();
/* 278:265 */     localJSONObject.put("errorCode", Integer.valueOf(5));
/* 279:266 */     localJSONObject.put("errorDescription", "Unknown " + paramString);
/* 280:267 */     return JSON.prepare(localJSONObject);
/* 281:    */   }
/* 282:    */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.http.JSONResponses
 * JD-Core Version:    0.7.1
 */