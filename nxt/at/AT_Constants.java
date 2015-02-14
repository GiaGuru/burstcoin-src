/*   1:    */ package nxt.at;
/*   2:    */ 
/*   3:    */ import java.util.HashMap;
/*   4:    */ import java.util.Map.Entry;
/*   5:    */ import java.util.NavigableMap;
/*   6:    */ import java.util.TreeMap;
/*   7:    */ 
/*   8:    */ public class AT_Constants
/*   9:    */ {
/*  10: 14 */   private static final NavigableMap<Integer, Short> AT_VERSION = new TreeMap();
/*  11: 16 */   private static final HashMap<Short, Long> MIN_FEE = new HashMap();
/*  12: 17 */   private static final HashMap<Short, Long> STEP_FEE = new HashMap();
/*  13: 18 */   private static final HashMap<Short, Long> MAX_STEPS = new HashMap();
/*  14: 19 */   private static final HashMap<Short, Long> API_STEP_MULTIPLIER = new HashMap();
/*  15: 21 */   private static final HashMap<Short, Long> COST_PER_PAGE = new HashMap();
/*  16: 23 */   private static final HashMap<Short, Long> MAX_WAIT_FOR_NUM_OF_BLOCKS = new HashMap();
/*  17: 24 */   private static final HashMap<Short, Long> MAX_SLEEP_BETWEEN_BLOCKS = new HashMap();
/*  18: 26 */   private static final HashMap<Short, Long> PAGE_SIZE = new HashMap();
/*  19: 28 */   private static final HashMap<Short, Long> MAX_MACHINE_CODE_PAGES = new HashMap();
/*  20: 29 */   private static final HashMap<Short, Long> MAX_MACHINE_DATA_PAGES = new HashMap();
/*  21: 30 */   private static final HashMap<Short, Long> MAX_MACHINE_USER_STACK_PAGES = new HashMap();
/*  22: 31 */   private static final HashMap<Short, Long> MAX_MACHINE_CALL_STACK_PAGES = new HashMap();
/*  23: 33 */   private static final HashMap<Short, Long> BLOCKS_FOR_RANDOM = new HashMap();
/*  24: 35 */   private static final HashMap<Short, Long> MAX_PAYLOAD_FOR_BLOCK = new HashMap();
/*  25: 37 */   private static final HashMap<Short, Long> AVERAGE_BLOCK_MINUTES = new HashMap();
/*  26:    */   public static final int AT_ID_SIZE = 8;
/*  27: 42 */   private static final AT_Constants instance = new AT_Constants();
/*  28:    */   
/*  29:    */   private AT_Constants()
/*  30:    */   {
/*  31: 47 */     AT_VERSION.put(Integer.valueOf(0), Short.valueOf((short)1));
/*  32:    */     
/*  33:    */ 
/*  34: 50 */     MIN_FEE.put(Short.valueOf((short)1), Long.valueOf(1000L));
/*  35: 51 */     STEP_FEE.put(Short.valueOf((short)1), Long.valueOf(10000000L));
/*  36: 52 */     MAX_STEPS.put(Short.valueOf((short)1), Long.valueOf(2000L));
/*  37: 53 */     API_STEP_MULTIPLIER.put(Short.valueOf((short)1), Long.valueOf(10L));
/*  38:    */     
/*  39: 55 */     COST_PER_PAGE.put(Short.valueOf((short)1), Long.valueOf(100000000L));
/*  40:    */     
/*  41: 57 */     MAX_WAIT_FOR_NUM_OF_BLOCKS.put(Short.valueOf((short)1), Long.valueOf(31536000L));
/*  42: 58 */     MAX_SLEEP_BETWEEN_BLOCKS.put(Short.valueOf((short)1), Long.valueOf(31536000L));
/*  43:    */     
/*  44: 60 */     PAGE_SIZE.put(Short.valueOf((short)1), Long.valueOf(256L));
/*  45:    */     
/*  46: 62 */     MAX_MACHINE_CODE_PAGES.put(Short.valueOf((short)1), Long.valueOf(10L));
/*  47: 63 */     MAX_MACHINE_DATA_PAGES.put(Short.valueOf((short)1), Long.valueOf(10L));
/*  48: 64 */     MAX_MACHINE_USER_STACK_PAGES.put(Short.valueOf((short)1), Long.valueOf(10L));
/*  49: 65 */     MAX_MACHINE_CALL_STACK_PAGES.put(Short.valueOf((short)1), Long.valueOf(10L));
/*  50:    */     
/*  51: 67 */     BLOCKS_FOR_RANDOM.put(Short.valueOf((short)1), Long.valueOf(15L));
/*  52: 68 */     MAX_PAYLOAD_FOR_BLOCK.put(Short.valueOf((short)1), Long.valueOf(22440L));
/*  53: 69 */     AVERAGE_BLOCK_MINUTES.put(Short.valueOf((short)1), Long.valueOf(4L));
/*  54:    */   }
/*  55:    */   
/*  56:    */   public static AT_Constants getInstance()
/*  57:    */   {
/*  58: 75 */     return instance;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public short AT_VERSION(int paramInt)
/*  62:    */   {
/*  63: 79 */     return ((Short)AT_VERSION.floorEntry(Integer.valueOf(paramInt)).getValue()).shortValue();
/*  64:    */   }
/*  65:    */   
/*  66:    */   public long STEP_FEE(int paramInt)
/*  67:    */   {
/*  68: 83 */     return ((Long)STEP_FEE.get(Short.valueOf(AT_VERSION(paramInt)))).longValue();
/*  69:    */   }
/*  70:    */   
/*  71:    */   public long MAX_STEPS(int paramInt)
/*  72:    */   {
/*  73: 87 */     return ((Long)MAX_STEPS.get(Short.valueOf(AT_VERSION(paramInt)))).longValue();
/*  74:    */   }
/*  75:    */   
/*  76:    */   public long API_STEP_MULTIPLIER(int paramInt)
/*  77:    */   {
/*  78: 91 */     return ((Long)API_STEP_MULTIPLIER.get(Short.valueOf(AT_VERSION(paramInt)))).longValue();
/*  79:    */   }
/*  80:    */   
/*  81:    */   public long COST_PER_PAGE(int paramInt)
/*  82:    */   {
/*  83: 95 */     return ((Long)COST_PER_PAGE.get(Short.valueOf(AT_VERSION(paramInt)))).longValue();
/*  84:    */   }
/*  85:    */   
/*  86:    */   public long get_MAX_WAIT_FOR_NUM_OF_BLOCKS(int paramInt)
/*  87:    */   {
/*  88: 99 */     return ((Long)MAX_WAIT_FOR_NUM_OF_BLOCKS.get(Short.valueOf(AT_VERSION(paramInt)))).longValue();
/*  89:    */   }
/*  90:    */   
/*  91:    */   public long MAX_SLEEP_BETWEEN_BLOCKS(int paramInt)
/*  92:    */   {
/*  93:103 */     return ((Long)MAX_SLEEP_BETWEEN_BLOCKS.get(Short.valueOf(AT_VERSION(paramInt)))).longValue();
/*  94:    */   }
/*  95:    */   
/*  96:    */   public long PAGE_SIZE(int paramInt)
/*  97:    */   {
/*  98:107 */     return ((Long)PAGE_SIZE.get(Short.valueOf(AT_VERSION(paramInt)))).longValue();
/*  99:    */   }
/* 100:    */   
/* 101:    */   public long MAX_MACHINE_CODE_PAGES(int paramInt)
/* 102:    */   {
/* 103:111 */     return ((Long)MAX_MACHINE_CODE_PAGES.get(Short.valueOf(AT_VERSION(paramInt)))).longValue();
/* 104:    */   }
/* 105:    */   
/* 106:    */   public long MAX_MACHINE_DATA_PAGES(int paramInt)
/* 107:    */   {
/* 108:115 */     return ((Long)MAX_MACHINE_DATA_PAGES.get(Short.valueOf(AT_VERSION(paramInt)))).longValue();
/* 109:    */   }
/* 110:    */   
/* 111:    */   public long MAX_MACHINE_USER_STACK_PAGES(int paramInt)
/* 112:    */   {
/* 113:119 */     return ((Long)MAX_MACHINE_USER_STACK_PAGES.get(Short.valueOf(AT_VERSION(paramInt)))).longValue();
/* 114:    */   }
/* 115:    */   
/* 116:    */   public long MAX_MACHINE_CALL_STACK_PAGES(int paramInt)
/* 117:    */   {
/* 118:123 */     return ((Long)MAX_MACHINE_CALL_STACK_PAGES.get(Short.valueOf(AT_VERSION(paramInt)))).longValue();
/* 119:    */   }
/* 120:    */   
/* 121:    */   public long BLOCKS_FOR_RANDOM(int paramInt)
/* 122:    */   {
/* 123:127 */     return ((Long)BLOCKS_FOR_RANDOM.get(Short.valueOf(AT_VERSION(paramInt)))).longValue();
/* 124:    */   }
/* 125:    */   
/* 126:    */   public long MAX_PAYLOAD_FOR_BLOCK(int paramInt)
/* 127:    */   {
/* 128:131 */     return ((Long)MAX_PAYLOAD_FOR_BLOCK.get(Short.valueOf(AT_VERSION(paramInt)))).longValue();
/* 129:    */   }
/* 130:    */   
/* 131:    */   public long AVERAGE_BLOCK_MINUTES(int paramInt)
/* 132:    */   {
/* 133:135 */     return ((Long)AVERAGE_BLOCK_MINUTES.get(Short.valueOf(AT_VERSION(paramInt)))).longValue();
/* 134:    */   }
/* 135:    */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.at.AT_Constants
 * JD-Core Version:    0.7.1
 */