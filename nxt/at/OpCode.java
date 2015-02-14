/*  1:   */ package nxt.at;
/*  2:   */ 
/*  3:   */ public final class OpCode
/*  4:   */ {
/*  5:13 */   static byte e_op_code_NOP = Byte.parseByte("7f", 16);
/*  6:14 */   static byte e_op_code_SET_VAL = Byte.parseByte("01", 16);
/*  7:15 */   static byte e_op_code_SET_DAT = Byte.parseByte("02", 16);
/*  8:16 */   static byte e_op_code_CLR_DAT = Byte.parseByte("03", 16);
/*  9:17 */   static byte e_op_code_INC_DAT = Byte.parseByte("04", 16);
/* 10:18 */   static byte e_op_code_DEC_DAT = Byte.parseByte("05", 16);
/* 11:19 */   static byte e_op_code_ADD_DAT = Byte.parseByte("06", 16);
/* 12:20 */   static byte e_op_code_SUB_DAT = Byte.parseByte("07", 16);
/* 13:21 */   static byte e_op_code_MUL_DAT = Byte.parseByte("08", 16);
/* 14:22 */   static byte e_op_code_DIV_DAT = Byte.parseByte("09", 16);
/* 15:23 */   static byte e_op_code_BOR_DAT = Byte.parseByte("0a", 16);
/* 16:24 */   static byte e_op_code_AND_DAT = Byte.parseByte("0b", 16);
/* 17:25 */   static byte e_op_code_XOR_DAT = Byte.parseByte("0c", 16);
/* 18:26 */   static byte e_op_code_NOT_DAT = Byte.parseByte("0d", 16);
/* 19:27 */   static byte e_op_code_SET_IND = Byte.parseByte("0e", 16);
/* 20:28 */   static byte e_op_code_SET_IDX = Byte.parseByte("0f", 16);
/* 21:29 */   static byte e_op_code_PSH_DAT = Byte.parseByte("10", 16);
/* 22:30 */   static byte e_op_code_POP_DAT = Byte.parseByte("11", 16);
/* 23:31 */   static byte e_op_code_JMP_SUB = Byte.parseByte("12", 16);
/* 24:32 */   static byte e_op_code_RET_SUB = Byte.parseByte("13", 16);
/* 25:33 */   static byte e_op_code_IND_DAT = Byte.parseByte("14", 16);
/* 26:34 */   static byte e_op_code_IDX_DAT = Byte.parseByte("15", 16);
/* 27:35 */   static byte e_op_code_MOD_DAT = Byte.parseByte("16", 16);
/* 28:36 */   static byte e_op_code_SHL_DAT = Byte.parseByte("17", 16);
/* 29:37 */   static byte e_op_code_SHR_DAT = Byte.parseByte("18", 16);
/* 30:38 */   static byte e_op_code_JMP_ADR = Byte.parseByte("1a", 16);
/* 31:39 */   static byte e_op_code_BZR_DAT = Byte.parseByte("1b", 16);
/* 32:40 */   static byte e_op_code_BNZ_DAT = Byte.parseByte("1e", 16);
/* 33:41 */   static byte e_op_code_BGT_DAT = Byte.parseByte("1f", 16);
/* 34:42 */   static byte e_op_code_BLT_DAT = Byte.parseByte("20", 16);
/* 35:43 */   static byte e_op_code_BGE_DAT = Byte.parseByte("21", 16);
/* 36:44 */   static byte e_op_code_BLE_DAT = Byte.parseByte("22", 16);
/* 37:45 */   static byte e_op_code_BEQ_DAT = Byte.parseByte("23", 16);
/* 38:46 */   static byte e_op_code_BNE_DAT = Byte.parseByte("24", 16);
/* 39:47 */   static byte e_op_code_SLP_DAT = Byte.parseByte("25", 16);
/* 40:48 */   static byte e_op_code_FIZ_DAT = Byte.parseByte("26", 16);
/* 41:49 */   static byte e_op_code_STZ_DAT = Byte.parseByte("27", 16);
/* 42:50 */   static byte e_op_code_FIN_IMD = Byte.parseByte("28", 16);
/* 43:51 */   static byte e_op_code_STP_IMD = Byte.parseByte("29", 16);
/* 44:52 */   static byte e_op_code_SLP_IMD = Byte.parseByte("2a", 16);
/* 45:53 */   static byte e_op_code_ERR_ADR = Byte.parseByte("2b", 16);
/* 46:54 */   static byte e_op_code_SET_PCS = Byte.parseByte("30", 16);
/* 47:55 */   static byte e_op_code_EXT_FUN = Byte.parseByte("32", 16);
/* 48:56 */   static byte e_op_code_EXT_FUN_DAT = Byte.parseByte("33", 16);
/* 49:57 */   static byte e_op_code_EXT_FUN_DAT_2 = Byte.parseByte("34", 16);
/* 50:58 */   static byte e_op_code_EXT_FUN_RET = Byte.parseByte("35", 16);
/* 51:59 */   static byte e_op_code_EXT_FUN_RET_DAT = Byte.parseByte("36", 16);
/* 52:60 */   static byte e_op_code_EXT_FUN_RET_DAT_2 = Byte.parseByte("37", 16);
/* 53:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.at.OpCode
 * JD-Core Version:    0.7.1
 */