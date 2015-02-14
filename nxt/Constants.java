/*   1:    */ package nxt;
/*   2:    */ 
/*   3:    */ import java.util.Calendar;
/*   4:    */ import java.util.TimeZone;
/*   5:    */ 
/*   6:    */ public final class Constants
/*   7:    */ {
/*   8:  9 */   public static int BURST_DIFF_ADJUST_CHANGE_BLOCK = 2700;
/*   9: 11 */   public static long BURST_REWARD_RECIPIENT_ASSIGNMENT_START_BLOCK = 6500L;
/*  10: 12 */   public static long BURST_REWARD_RECIPIENT_ASSIGNMENT_WAIT_TIME = 4L;
/*  11: 14 */   public static long BURST_ESCROW_START_BLOCK = 2147483647L;
/*  12: 15 */   public static long BURST_SUBSCRIPTION_START_BLOCK = 2147483647L;
/*  13: 16 */   public static int BURST_SUBSCRIPTION_MIN_FREQ = 3600;
/*  14: 17 */   public static int BURST_SUBSCRIPTION_MAX_FREQ = 31536000;
/*  15:    */   public static final int BLOCK_HEADER_LENGTH = 232;
/*  16:    */   public static final int MAX_NUMBER_OF_TRANSACTIONS = 255;
/*  17:    */   public static final int MAX_PAYLOAD_LENGTH = 44880;
/*  18:    */   public static final long MAX_BALANCE_NXT = 2158812800L;
/*  19:    */   public static final long ONE_NXT = 100000000L;
/*  20:    */   public static final long MAX_BALANCE_NQT = 215881280000000000L;
/*  21:    */   public static final long INITIAL_BASE_TARGET = 18325193796L;
/*  22:    */   public static final long MAX_BASE_TARGET = 18325193796L;
/*  23: 27 */   public static final int MAX_ROLLBACK = Nxt.getIntProperty("nxt.maxRollback");
/*  24:    */   public static final int MAX_ALIAS_URI_LENGTH = 1000;
/*  25:    */   public static final int MAX_ALIAS_LENGTH = 100;
/*  26:    */   public static final int MAX_ARBITRARY_MESSAGE_LENGTH = 1000;
/*  27:    */   public static final int MAX_ENCRYPTED_MESSAGE_LENGTH = 1000;
/*  28:    */   public static final int MAX_ACCOUNT_NAME_LENGTH = 100;
/*  29:    */   public static final int MAX_ACCOUNT_DESCRIPTION_LENGTH = 1000;
/*  30:    */   public static final long MAX_ASSET_QUANTITY_QNT = 100000000000000000L;
/*  31:    */   public static final long ASSET_ISSUANCE_FEE_NQT = 100000000000L;
/*  32:    */   public static final int MIN_ASSET_NAME_LENGTH = 3;
/*  33:    */   public static final int MAX_ASSET_NAME_LENGTH = 10;
/*  34:    */   public static final int MAX_ASSET_DESCRIPTION_LENGTH = 1000;
/*  35:    */   public static final int MAX_ASSET_TRANSFER_COMMENT_LENGTH = 1000;
/*  36:    */   public static final int MAX_POLL_NAME_LENGTH = 100;
/*  37:    */   public static final int MAX_POLL_DESCRIPTION_LENGTH = 1000;
/*  38:    */   public static final int MAX_POLL_OPTION_LENGTH = 100;
/*  39:    */   public static final int MAX_POLL_OPTION_COUNT = 100;
/*  40:    */   public static final int MAX_DGS_LISTING_QUANTITY = 1000000000;
/*  41:    */   public static final int MAX_DGS_LISTING_NAME_LENGTH = 100;
/*  42:    */   public static final int MAX_DGS_LISTING_DESCRIPTION_LENGTH = 1000;
/*  43:    */   public static final int MAX_DGS_LISTING_TAGS_LENGTH = 100;
/*  44:    */   public static final int MAX_DGS_GOODS_LENGTH = 10240;
/*  45:    */   public static final int MAX_HUB_ANNOUNCEMENT_URIS = 100;
/*  46:    */   public static final int MAX_HUB_ANNOUNCEMENT_URI_LENGTH = 1000;
/*  47:    */   public static final long MIN_HUB_EFFECTIVE_BALANCE = 100000L;
/*  48:    */   public static final boolean isTestnet;
/*  49:    */   public static final boolean isOffline;
/*  50:    */   public static final int ALIAS_SYSTEM_BLOCK = 0;
/*  51:    */   public static final int TRANSPARENT_FORGING_BLOCK = 0;
/*  52:    */   public static final int ARBITRARY_MESSAGES_BLOCK = 0;
/*  53:    */   public static final int TRANSPARENT_FORGING_BLOCK_2 = 0;
/*  54:    */   public static final int TRANSPARENT_FORGING_BLOCK_3 = 0;
/*  55:    */   public static final int TRANSPARENT_FORGING_BLOCK_4 = 0;
/*  56:    */   public static final int TRANSPARENT_FORGING_BLOCK_5 = 0;
/*  57:    */   public static final int TRANSPARENT_FORGING_BLOCK_6 = 0;
/*  58:    */   public static final int TRANSPARENT_FORGING_BLOCK_7 = Integer.MAX_VALUE;
/*  59:    */   public static final int TRANSPARENT_FORGING_BLOCK_8 = 0;
/*  60:    */   public static final int NQT_BLOCK = 0;
/*  61:    */   public static final int FRACTIONAL_BLOCK = 0;
/*  62:    */   public static final int ASSET_EXCHANGE_BLOCK = 0;
/*  63:    */   public static final int REFERENCED_TRANSACTION_FULL_HASH_BLOCK = 0;
/*  64:    */   public static final int REFERENCED_TRANSACTION_FULL_HASH_BLOCK_TIMESTAMP = 0;
/*  65:    */   public static final int VOTING_SYSTEM_BLOCK;
/*  66:    */   public static final int DIGITAL_GOODS_STORE_BLOCK = 11800;
/*  67:    */   public static final int PUBLIC_KEY_ANNOUNCEMENT_BLOCK = Integer.MAX_VALUE;
/*  68:    */   public static final int MAX_AUTOMATED_TRANSACTION_NAME_LENGTH = 30;
/*  69:    */   public static final int MAX_AUTOMATED_TRANSACTION_DESCRIPTION_LENGTH = 1000;
/*  70:    */   protected static final int AUTOMATED_TRANSACTION_BLOCK = 49200;
/*  71:    */   public static final int AT_BLOCK_PAYLOAD = 22440;
/*  72:    */   public static final int AT_FIX_BLOCK_2 = 67000;
/*  73:    */   static final long UNCONFIRMED_POOL_DEPOSIT_NQT;
/*  74:    */   public static final long EPOCH_BEGINNING;
/*  75:    */   public static final String ALPHABET = "0123456789abcdefghijklmnopqrstuvwxyz";
/*  76:    */   public static final int EC_RULE_TERMINATOR = 2400;
/*  77:    */   public static final int EC_BLOCK_DISTANCE_LIMIT = 60;
/*  78:    */   public static final int EC_CHANGE_BLOCK_1 = 67000;
/*  79:    */   
/*  80:    */   static
/*  81:    */   {
/*  82: 29 */     if (MAX_ROLLBACK < 1440) {
/*  83: 30 */       throw new RuntimeException("nxt.maxRollback must be at least 1440");
/*  84:    */     }
/*  85: 65 */     isTestnet = Nxt.getBooleanProperty("nxt.isTestnet").booleanValue();
/*  86: 66 */     isOffline = Nxt.getBooleanProperty("nxt.isOffline").booleanValue();
/*  87:    */     
/*  88:    */ 
/*  89:    */ 
/*  90:    */ 
/*  91:    */ 
/*  92:    */ 
/*  93:    */ 
/*  94:    */ 
/*  95:    */ 
/*  96:    */ 
/*  97:    */ 
/*  98:    */ 
/*  99:    */ 
/* 100:    */ 
/* 101:    */ 
/* 102:    */ 
/* 103: 83 */     VOTING_SYSTEM_BLOCK = isTestnet ? 0 : Integer.MAX_VALUE;
/* 104:    */     
/* 105:    */ 
/* 106:    */ 
/* 107:    */ 
/* 108:    */ 
/* 109:    */ 
/* 110:    */ 
/* 111:    */ 
/* 112:    */ 
/* 113: 93 */     UNCONFIRMED_POOL_DEPOSIT_NQT = (isTestnet ? 50 : 100) * 100000000L;
/* 114:    */     
/* 115:    */ 
/* 116:    */ 
/* 117: 97 */     Calendar localCalendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
/* 118: 98 */     localCalendar.set(1, 2014);
/* 119: 99 */     localCalendar.set(2, 7);
/* 120:100 */     localCalendar.set(5, 11);
/* 121:101 */     localCalendar.set(11, 2);
/* 122:102 */     localCalendar.set(12, 0);
/* 123:103 */     localCalendar.set(13, 0);
/* 124:104 */     localCalendar.set(14, 0);
/* 125:105 */     EPOCH_BEGINNING = localCalendar.getTimeInMillis();
/* 126:    */   }
/* 127:    */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.Constants
 * JD-Core Version:    0.7.1
 */