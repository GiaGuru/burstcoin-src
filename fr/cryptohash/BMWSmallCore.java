/*   1:    */ package fr.cryptohash;
/*   2:    */ 
/*   3:    */ abstract class BMWSmallCore
/*   4:    */   extends DigestEngine
/*   5:    */ {
/*   6:    */   private int[] M;
/*   7:    */   private int[] H;
/*   8:    */   private int[] H2;
/*   9:    */   private int[] Q;
/*  10:    */   
/*  11:    */   public int getBlockLength()
/*  12:    */   {
/*  13: 53 */     return 64;
/*  14:    */   }
/*  15:    */   
/*  16:    */   protected Digest copyState(BMWSmallCore paramBMWSmallCore)
/*  17:    */   {
/*  18: 59 */     System.arraycopy(this.H, 0, paramBMWSmallCore.H, 0, this.H.length);
/*  19: 60 */     return super.copyState(paramBMWSmallCore);
/*  20:    */   }
/*  21:    */   
/*  22:    */   protected void engineReset()
/*  23:    */   {
/*  24: 66 */     int[] arrayOfInt = getInitVal();
/*  25: 67 */     System.arraycopy(arrayOfInt, 0, this.H, 0, arrayOfInt.length);
/*  26:    */   }
/*  27:    */   
/*  28: 72 */   private static final int[] FINAL = { -1431655776, -1431655775, -1431655774, -1431655773, -1431655772, -1431655771, -1431655770, -1431655769, -1431655768, -1431655767, -1431655766, -1431655765, -1431655764, -1431655763, -1431655762, -1431655761 };
/*  29:    */   
/*  30:    */   abstract int[] getInitVal();
/*  31:    */   
/*  32:    */   private void compress(int[] paramArrayOfInt)
/*  33:    */   {
/*  34: 81 */     int[] arrayOfInt1 = this.H;
/*  35: 82 */     int[] arrayOfInt2 = this.Q;
/*  36: 83 */     arrayOfInt2[0] = (((paramArrayOfInt[5] ^ arrayOfInt1[5]) - (paramArrayOfInt[7] ^ arrayOfInt1[7]) + (paramArrayOfInt[10] ^ arrayOfInt1[10]) + (paramArrayOfInt[13] ^ arrayOfInt1[13]) + (paramArrayOfInt[14] ^ arrayOfInt1[14]) >>> 1 ^ (paramArrayOfInt[5] ^ arrayOfInt1[5]) - (paramArrayOfInt[7] ^ arrayOfInt1[7]) + (paramArrayOfInt[10] ^ arrayOfInt1[10]) + (paramArrayOfInt[13] ^ arrayOfInt1[13]) + (paramArrayOfInt[14] ^ arrayOfInt1[14]) << 3 ^ circularLeft((paramArrayOfInt[5] ^ arrayOfInt1[5]) - (paramArrayOfInt[7] ^ arrayOfInt1[7]) + (paramArrayOfInt[10] ^ arrayOfInt1[10]) + (paramArrayOfInt[13] ^ arrayOfInt1[13]) + (paramArrayOfInt[14] ^ arrayOfInt1[14]), 4) ^ circularLeft((paramArrayOfInt[5] ^ arrayOfInt1[5]) - (paramArrayOfInt[7] ^ arrayOfInt1[7]) + (paramArrayOfInt[10] ^ arrayOfInt1[10]) + (paramArrayOfInt[13] ^ arrayOfInt1[13]) + (paramArrayOfInt[14] ^ arrayOfInt1[14]), 19)) + arrayOfInt1[1]);
/*  37:    */     
/*  38:    */ 
/*  39:    */ 
/*  40:    */ 
/*  41:    */ 
/*  42:    */ 
/*  43:    */ 
/*  44:    */ 
/*  45:    */ 
/*  46:    */ 
/*  47: 94 */     arrayOfInt2[1] = (((paramArrayOfInt[6] ^ arrayOfInt1[6]) - (paramArrayOfInt[8] ^ arrayOfInt1[8]) + (paramArrayOfInt[11] ^ arrayOfInt1[11]) + (paramArrayOfInt[14] ^ arrayOfInt1[14]) - (paramArrayOfInt[15] ^ arrayOfInt1[15]) >>> 1 ^ (paramArrayOfInt[6] ^ arrayOfInt1[6]) - (paramArrayOfInt[8] ^ arrayOfInt1[8]) + (paramArrayOfInt[11] ^ arrayOfInt1[11]) + (paramArrayOfInt[14] ^ arrayOfInt1[14]) - (paramArrayOfInt[15] ^ arrayOfInt1[15]) << 2 ^ circularLeft((paramArrayOfInt[6] ^ arrayOfInt1[6]) - (paramArrayOfInt[8] ^ arrayOfInt1[8]) + (paramArrayOfInt[11] ^ arrayOfInt1[11]) + (paramArrayOfInt[14] ^ arrayOfInt1[14]) - (paramArrayOfInt[15] ^ arrayOfInt1[15]), 8) ^ circularLeft((paramArrayOfInt[6] ^ arrayOfInt1[6]) - (paramArrayOfInt[8] ^ arrayOfInt1[8]) + (paramArrayOfInt[11] ^ arrayOfInt1[11]) + (paramArrayOfInt[14] ^ arrayOfInt1[14]) - (paramArrayOfInt[15] ^ arrayOfInt1[15]), 23)) + arrayOfInt1[2]);
/*  48:    */     
/*  49:    */ 
/*  50:    */ 
/*  51:    */ 
/*  52:    */ 
/*  53:    */ 
/*  54:    */ 
/*  55:    */ 
/*  56:    */ 
/*  57:    */ 
/*  58:105 */     arrayOfInt2[2] = (((paramArrayOfInt[0] ^ arrayOfInt1[0]) + (paramArrayOfInt[7] ^ arrayOfInt1[7]) + (paramArrayOfInt[9] ^ arrayOfInt1[9]) - (paramArrayOfInt[12] ^ arrayOfInt1[12]) + (paramArrayOfInt[15] ^ arrayOfInt1[15]) >>> 2 ^ (paramArrayOfInt[0] ^ arrayOfInt1[0]) + (paramArrayOfInt[7] ^ arrayOfInt1[7]) + (paramArrayOfInt[9] ^ arrayOfInt1[9]) - (paramArrayOfInt[12] ^ arrayOfInt1[12]) + (paramArrayOfInt[15] ^ arrayOfInt1[15]) << 1 ^ circularLeft((paramArrayOfInt[0] ^ arrayOfInt1[0]) + (paramArrayOfInt[7] ^ arrayOfInt1[7]) + (paramArrayOfInt[9] ^ arrayOfInt1[9]) - (paramArrayOfInt[12] ^ arrayOfInt1[12]) + (paramArrayOfInt[15] ^ arrayOfInt1[15]), 12) ^ circularLeft((paramArrayOfInt[0] ^ arrayOfInt1[0]) + (paramArrayOfInt[7] ^ arrayOfInt1[7]) + (paramArrayOfInt[9] ^ arrayOfInt1[9]) - (paramArrayOfInt[12] ^ arrayOfInt1[12]) + (paramArrayOfInt[15] ^ arrayOfInt1[15]), 25)) + arrayOfInt1[3]);
/*  59:    */     
/*  60:    */ 
/*  61:    */ 
/*  62:    */ 
/*  63:    */ 
/*  64:    */ 
/*  65:    */ 
/*  66:    */ 
/*  67:    */ 
/*  68:    */ 
/*  69:116 */     arrayOfInt2[3] = (((paramArrayOfInt[0] ^ arrayOfInt1[0]) - (paramArrayOfInt[1] ^ arrayOfInt1[1]) + (paramArrayOfInt[8] ^ arrayOfInt1[8]) - (paramArrayOfInt[10] ^ arrayOfInt1[10]) + (paramArrayOfInt[13] ^ arrayOfInt1[13]) >>> 2 ^ (paramArrayOfInt[0] ^ arrayOfInt1[0]) - (paramArrayOfInt[1] ^ arrayOfInt1[1]) + (paramArrayOfInt[8] ^ arrayOfInt1[8]) - (paramArrayOfInt[10] ^ arrayOfInt1[10]) + (paramArrayOfInt[13] ^ arrayOfInt1[13]) << 2 ^ circularLeft((paramArrayOfInt[0] ^ arrayOfInt1[0]) - (paramArrayOfInt[1] ^ arrayOfInt1[1]) + (paramArrayOfInt[8] ^ arrayOfInt1[8]) - (paramArrayOfInt[10] ^ arrayOfInt1[10]) + (paramArrayOfInt[13] ^ arrayOfInt1[13]), 15) ^ circularLeft((paramArrayOfInt[0] ^ arrayOfInt1[0]) - (paramArrayOfInt[1] ^ arrayOfInt1[1]) + (paramArrayOfInt[8] ^ arrayOfInt1[8]) - (paramArrayOfInt[10] ^ arrayOfInt1[10]) + (paramArrayOfInt[13] ^ arrayOfInt1[13]), 29)) + arrayOfInt1[4]);
/*  70:    */     
/*  71:    */ 
/*  72:    */ 
/*  73:    */ 
/*  74:    */ 
/*  75:    */ 
/*  76:    */ 
/*  77:    */ 
/*  78:    */ 
/*  79:    */ 
/*  80:127 */     arrayOfInt2[4] = (((paramArrayOfInt[1] ^ arrayOfInt1[1]) + (paramArrayOfInt[2] ^ arrayOfInt1[2]) + (paramArrayOfInt[9] ^ arrayOfInt1[9]) - (paramArrayOfInt[11] ^ arrayOfInt1[11]) - (paramArrayOfInt[14] ^ arrayOfInt1[14]) >>> 1 ^ (paramArrayOfInt[1] ^ arrayOfInt1[1]) + (paramArrayOfInt[2] ^ arrayOfInt1[2]) + (paramArrayOfInt[9] ^ arrayOfInt1[9]) - (paramArrayOfInt[11] ^ arrayOfInt1[11]) - (paramArrayOfInt[14] ^ arrayOfInt1[14])) + arrayOfInt1[5]);
/*  81:    */     
/*  82:    */ 
/*  83:    */ 
/*  84:131 */     arrayOfInt2[5] = (((paramArrayOfInt[3] ^ arrayOfInt1[3]) - (paramArrayOfInt[2] ^ arrayOfInt1[2]) + (paramArrayOfInt[10] ^ arrayOfInt1[10]) - (paramArrayOfInt[12] ^ arrayOfInt1[12]) + (paramArrayOfInt[15] ^ arrayOfInt1[15]) >>> 1 ^ (paramArrayOfInt[3] ^ arrayOfInt1[3]) - (paramArrayOfInt[2] ^ arrayOfInt1[2]) + (paramArrayOfInt[10] ^ arrayOfInt1[10]) - (paramArrayOfInt[12] ^ arrayOfInt1[12]) + (paramArrayOfInt[15] ^ arrayOfInt1[15]) << 3 ^ circularLeft((paramArrayOfInt[3] ^ arrayOfInt1[3]) - (paramArrayOfInt[2] ^ arrayOfInt1[2]) + (paramArrayOfInt[10] ^ arrayOfInt1[10]) - (paramArrayOfInt[12] ^ arrayOfInt1[12]) + (paramArrayOfInt[15] ^ arrayOfInt1[15]), 4) ^ circularLeft((paramArrayOfInt[3] ^ arrayOfInt1[3]) - (paramArrayOfInt[2] ^ arrayOfInt1[2]) + (paramArrayOfInt[10] ^ arrayOfInt1[10]) - (paramArrayOfInt[12] ^ arrayOfInt1[12]) + (paramArrayOfInt[15] ^ arrayOfInt1[15]), 19)) + arrayOfInt1[6]);
/*  85:    */     
/*  86:    */ 
/*  87:    */ 
/*  88:    */ 
/*  89:    */ 
/*  90:    */ 
/*  91:    */ 
/*  92:    */ 
/*  93:    */ 
/*  94:    */ 
/*  95:142 */     arrayOfInt2[6] = (((paramArrayOfInt[4] ^ arrayOfInt1[4]) - (paramArrayOfInt[0] ^ arrayOfInt1[0]) - (paramArrayOfInt[3] ^ arrayOfInt1[3]) - (paramArrayOfInt[11] ^ arrayOfInt1[11]) + (paramArrayOfInt[13] ^ arrayOfInt1[13]) >>> 1 ^ (paramArrayOfInt[4] ^ arrayOfInt1[4]) - (paramArrayOfInt[0] ^ arrayOfInt1[0]) - (paramArrayOfInt[3] ^ arrayOfInt1[3]) - (paramArrayOfInt[11] ^ arrayOfInt1[11]) + (paramArrayOfInt[13] ^ arrayOfInt1[13]) << 2 ^ circularLeft((paramArrayOfInt[4] ^ arrayOfInt1[4]) - (paramArrayOfInt[0] ^ arrayOfInt1[0]) - (paramArrayOfInt[3] ^ arrayOfInt1[3]) - (paramArrayOfInt[11] ^ arrayOfInt1[11]) + (paramArrayOfInt[13] ^ arrayOfInt1[13]), 8) ^ circularLeft((paramArrayOfInt[4] ^ arrayOfInt1[4]) - (paramArrayOfInt[0] ^ arrayOfInt1[0]) - (paramArrayOfInt[3] ^ arrayOfInt1[3]) - (paramArrayOfInt[11] ^ arrayOfInt1[11]) + (paramArrayOfInt[13] ^ arrayOfInt1[13]), 23)) + arrayOfInt1[7]);
/*  96:    */     
/*  97:    */ 
/*  98:    */ 
/*  99:    */ 
/* 100:    */ 
/* 101:    */ 
/* 102:    */ 
/* 103:    */ 
/* 104:    */ 
/* 105:    */ 
/* 106:153 */     arrayOfInt2[7] = (((paramArrayOfInt[1] ^ arrayOfInt1[1]) - (paramArrayOfInt[4] ^ arrayOfInt1[4]) - (paramArrayOfInt[5] ^ arrayOfInt1[5]) - (paramArrayOfInt[12] ^ arrayOfInt1[12]) - (paramArrayOfInt[14] ^ arrayOfInt1[14]) >>> 2 ^ (paramArrayOfInt[1] ^ arrayOfInt1[1]) - (paramArrayOfInt[4] ^ arrayOfInt1[4]) - (paramArrayOfInt[5] ^ arrayOfInt1[5]) - (paramArrayOfInt[12] ^ arrayOfInt1[12]) - (paramArrayOfInt[14] ^ arrayOfInt1[14]) << 1 ^ circularLeft((paramArrayOfInt[1] ^ arrayOfInt1[1]) - (paramArrayOfInt[4] ^ arrayOfInt1[4]) - (paramArrayOfInt[5] ^ arrayOfInt1[5]) - (paramArrayOfInt[12] ^ arrayOfInt1[12]) - (paramArrayOfInt[14] ^ arrayOfInt1[14]), 12) ^ circularLeft((paramArrayOfInt[1] ^ arrayOfInt1[1]) - (paramArrayOfInt[4] ^ arrayOfInt1[4]) - (paramArrayOfInt[5] ^ arrayOfInt1[5]) - (paramArrayOfInt[12] ^ arrayOfInt1[12]) - (paramArrayOfInt[14] ^ arrayOfInt1[14]), 25)) + arrayOfInt1[8]);
/* 107:    */     
/* 108:    */ 
/* 109:    */ 
/* 110:    */ 
/* 111:    */ 
/* 112:    */ 
/* 113:    */ 
/* 114:    */ 
/* 115:    */ 
/* 116:    */ 
/* 117:164 */     arrayOfInt2[8] = (((paramArrayOfInt[2] ^ arrayOfInt1[2]) - (paramArrayOfInt[5] ^ arrayOfInt1[5]) - (paramArrayOfInt[6] ^ arrayOfInt1[6]) + (paramArrayOfInt[13] ^ arrayOfInt1[13]) - (paramArrayOfInt[15] ^ arrayOfInt1[15]) >>> 2 ^ (paramArrayOfInt[2] ^ arrayOfInt1[2]) - (paramArrayOfInt[5] ^ arrayOfInt1[5]) - (paramArrayOfInt[6] ^ arrayOfInt1[6]) + (paramArrayOfInt[13] ^ arrayOfInt1[13]) - (paramArrayOfInt[15] ^ arrayOfInt1[15]) << 2 ^ circularLeft((paramArrayOfInt[2] ^ arrayOfInt1[2]) - (paramArrayOfInt[5] ^ arrayOfInt1[5]) - (paramArrayOfInt[6] ^ arrayOfInt1[6]) + (paramArrayOfInt[13] ^ arrayOfInt1[13]) - (paramArrayOfInt[15] ^ arrayOfInt1[15]), 15) ^ circularLeft((paramArrayOfInt[2] ^ arrayOfInt1[2]) - (paramArrayOfInt[5] ^ arrayOfInt1[5]) - (paramArrayOfInt[6] ^ arrayOfInt1[6]) + (paramArrayOfInt[13] ^ arrayOfInt1[13]) - (paramArrayOfInt[15] ^ arrayOfInt1[15]), 29)) + arrayOfInt1[9]);
/* 118:    */     
/* 119:    */ 
/* 120:    */ 
/* 121:    */ 
/* 122:    */ 
/* 123:    */ 
/* 124:    */ 
/* 125:    */ 
/* 126:    */ 
/* 127:    */ 
/* 128:175 */     arrayOfInt2[9] = (((paramArrayOfInt[0] ^ arrayOfInt1[0]) - (paramArrayOfInt[3] ^ arrayOfInt1[3]) + (paramArrayOfInt[6] ^ arrayOfInt1[6]) - (paramArrayOfInt[7] ^ arrayOfInt1[7]) + (paramArrayOfInt[14] ^ arrayOfInt1[14]) >>> 1 ^ (paramArrayOfInt[0] ^ arrayOfInt1[0]) - (paramArrayOfInt[3] ^ arrayOfInt1[3]) + (paramArrayOfInt[6] ^ arrayOfInt1[6]) - (paramArrayOfInt[7] ^ arrayOfInt1[7]) + (paramArrayOfInt[14] ^ arrayOfInt1[14])) + arrayOfInt1[10]);
/* 129:    */     
/* 130:    */ 
/* 131:    */ 
/* 132:179 */     arrayOfInt2[10] = (((paramArrayOfInt[8] ^ arrayOfInt1[8]) - (paramArrayOfInt[1] ^ arrayOfInt1[1]) - (paramArrayOfInt[4] ^ arrayOfInt1[4]) - (paramArrayOfInt[7] ^ arrayOfInt1[7]) + (paramArrayOfInt[15] ^ arrayOfInt1[15]) >>> 1 ^ (paramArrayOfInt[8] ^ arrayOfInt1[8]) - (paramArrayOfInt[1] ^ arrayOfInt1[1]) - (paramArrayOfInt[4] ^ arrayOfInt1[4]) - (paramArrayOfInt[7] ^ arrayOfInt1[7]) + (paramArrayOfInt[15] ^ arrayOfInt1[15]) << 3 ^ circularLeft((paramArrayOfInt[8] ^ arrayOfInt1[8]) - (paramArrayOfInt[1] ^ arrayOfInt1[1]) - (paramArrayOfInt[4] ^ arrayOfInt1[4]) - (paramArrayOfInt[7] ^ arrayOfInt1[7]) + (paramArrayOfInt[15] ^ arrayOfInt1[15]), 4) ^ circularLeft((paramArrayOfInt[8] ^ arrayOfInt1[8]) - (paramArrayOfInt[1] ^ arrayOfInt1[1]) - (paramArrayOfInt[4] ^ arrayOfInt1[4]) - (paramArrayOfInt[7] ^ arrayOfInt1[7]) + (paramArrayOfInt[15] ^ arrayOfInt1[15]), 19)) + arrayOfInt1[11]);
/* 133:    */     
/* 134:    */ 
/* 135:    */ 
/* 136:    */ 
/* 137:    */ 
/* 138:    */ 
/* 139:    */ 
/* 140:    */ 
/* 141:    */ 
/* 142:    */ 
/* 143:190 */     arrayOfInt2[11] = (((paramArrayOfInt[8] ^ arrayOfInt1[8]) - (paramArrayOfInt[0] ^ arrayOfInt1[0]) - (paramArrayOfInt[2] ^ arrayOfInt1[2]) - (paramArrayOfInt[5] ^ arrayOfInt1[5]) + (paramArrayOfInt[9] ^ arrayOfInt1[9]) >>> 1 ^ (paramArrayOfInt[8] ^ arrayOfInt1[8]) - (paramArrayOfInt[0] ^ arrayOfInt1[0]) - (paramArrayOfInt[2] ^ arrayOfInt1[2]) - (paramArrayOfInt[5] ^ arrayOfInt1[5]) + (paramArrayOfInt[9] ^ arrayOfInt1[9]) << 2 ^ circularLeft((paramArrayOfInt[8] ^ arrayOfInt1[8]) - (paramArrayOfInt[0] ^ arrayOfInt1[0]) - (paramArrayOfInt[2] ^ arrayOfInt1[2]) - (paramArrayOfInt[5] ^ arrayOfInt1[5]) + (paramArrayOfInt[9] ^ arrayOfInt1[9]), 8) ^ circularLeft((paramArrayOfInt[8] ^ arrayOfInt1[8]) - (paramArrayOfInt[0] ^ arrayOfInt1[0]) - (paramArrayOfInt[2] ^ arrayOfInt1[2]) - (paramArrayOfInt[5] ^ arrayOfInt1[5]) + (paramArrayOfInt[9] ^ arrayOfInt1[9]), 23)) + arrayOfInt1[12]);
/* 144:    */     
/* 145:    */ 
/* 146:    */ 
/* 147:    */ 
/* 148:    */ 
/* 149:    */ 
/* 150:    */ 
/* 151:    */ 
/* 152:    */ 
/* 153:    */ 
/* 154:201 */     arrayOfInt2[12] = (((paramArrayOfInt[1] ^ arrayOfInt1[1]) + (paramArrayOfInt[3] ^ arrayOfInt1[3]) - (paramArrayOfInt[6] ^ arrayOfInt1[6]) - (paramArrayOfInt[9] ^ arrayOfInt1[9]) + (paramArrayOfInt[10] ^ arrayOfInt1[10]) >>> 2 ^ (paramArrayOfInt[1] ^ arrayOfInt1[1]) + (paramArrayOfInt[3] ^ arrayOfInt1[3]) - (paramArrayOfInt[6] ^ arrayOfInt1[6]) - (paramArrayOfInt[9] ^ arrayOfInt1[9]) + (paramArrayOfInt[10] ^ arrayOfInt1[10]) << 1 ^ circularLeft((paramArrayOfInt[1] ^ arrayOfInt1[1]) + (paramArrayOfInt[3] ^ arrayOfInt1[3]) - (paramArrayOfInt[6] ^ arrayOfInt1[6]) - (paramArrayOfInt[9] ^ arrayOfInt1[9]) + (paramArrayOfInt[10] ^ arrayOfInt1[10]), 12) ^ circularLeft((paramArrayOfInt[1] ^ arrayOfInt1[1]) + (paramArrayOfInt[3] ^ arrayOfInt1[3]) - (paramArrayOfInt[6] ^ arrayOfInt1[6]) - (paramArrayOfInt[9] ^ arrayOfInt1[9]) + (paramArrayOfInt[10] ^ arrayOfInt1[10]), 25)) + arrayOfInt1[13]);
/* 155:    */     
/* 156:    */ 
/* 157:    */ 
/* 158:    */ 
/* 159:    */ 
/* 160:    */ 
/* 161:    */ 
/* 162:    */ 
/* 163:    */ 
/* 164:    */ 
/* 165:212 */     arrayOfInt2[13] = (((paramArrayOfInt[2] ^ arrayOfInt1[2]) + (paramArrayOfInt[4] ^ arrayOfInt1[4]) + (paramArrayOfInt[7] ^ arrayOfInt1[7]) + (paramArrayOfInt[10] ^ arrayOfInt1[10]) + (paramArrayOfInt[11] ^ arrayOfInt1[11]) >>> 2 ^ (paramArrayOfInt[2] ^ arrayOfInt1[2]) + (paramArrayOfInt[4] ^ arrayOfInt1[4]) + (paramArrayOfInt[7] ^ arrayOfInt1[7]) + (paramArrayOfInt[10] ^ arrayOfInt1[10]) + (paramArrayOfInt[11] ^ arrayOfInt1[11]) << 2 ^ circularLeft((paramArrayOfInt[2] ^ arrayOfInt1[2]) + (paramArrayOfInt[4] ^ arrayOfInt1[4]) + (paramArrayOfInt[7] ^ arrayOfInt1[7]) + (paramArrayOfInt[10] ^ arrayOfInt1[10]) + (paramArrayOfInt[11] ^ arrayOfInt1[11]), 15) ^ circularLeft((paramArrayOfInt[2] ^ arrayOfInt1[2]) + (paramArrayOfInt[4] ^ arrayOfInt1[4]) + (paramArrayOfInt[7] ^ arrayOfInt1[7]) + (paramArrayOfInt[10] ^ arrayOfInt1[10]) + (paramArrayOfInt[11] ^ arrayOfInt1[11]), 29)) + arrayOfInt1[14]);
/* 166:    */     
/* 167:    */ 
/* 168:    */ 
/* 169:    */ 
/* 170:    */ 
/* 171:    */ 
/* 172:    */ 
/* 173:    */ 
/* 174:    */ 
/* 175:    */ 
/* 176:223 */     arrayOfInt2[14] = (((paramArrayOfInt[3] ^ arrayOfInt1[3]) - (paramArrayOfInt[5] ^ arrayOfInt1[5]) + (paramArrayOfInt[8] ^ arrayOfInt1[8]) - (paramArrayOfInt[11] ^ arrayOfInt1[11]) - (paramArrayOfInt[12] ^ arrayOfInt1[12]) >>> 1 ^ (paramArrayOfInt[3] ^ arrayOfInt1[3]) - (paramArrayOfInt[5] ^ arrayOfInt1[5]) + (paramArrayOfInt[8] ^ arrayOfInt1[8]) - (paramArrayOfInt[11] ^ arrayOfInt1[11]) - (paramArrayOfInt[12] ^ arrayOfInt1[12])) + arrayOfInt1[15]);
/* 177:    */     
/* 178:    */ 
/* 179:    */ 
/* 180:227 */     arrayOfInt2[15] = (((paramArrayOfInt[12] ^ arrayOfInt1[12]) - (paramArrayOfInt[4] ^ arrayOfInt1[4]) - (paramArrayOfInt[6] ^ arrayOfInt1[6]) - (paramArrayOfInt[9] ^ arrayOfInt1[9]) + (paramArrayOfInt[13] ^ arrayOfInt1[13]) >>> 1 ^ (paramArrayOfInt[12] ^ arrayOfInt1[12]) - (paramArrayOfInt[4] ^ arrayOfInt1[4]) - (paramArrayOfInt[6] ^ arrayOfInt1[6]) - (paramArrayOfInt[9] ^ arrayOfInt1[9]) + (paramArrayOfInt[13] ^ arrayOfInt1[13]) << 3 ^ circularLeft((paramArrayOfInt[12] ^ arrayOfInt1[12]) - (paramArrayOfInt[4] ^ arrayOfInt1[4]) - (paramArrayOfInt[6] ^ arrayOfInt1[6]) - (paramArrayOfInt[9] ^ arrayOfInt1[9]) + (paramArrayOfInt[13] ^ arrayOfInt1[13]), 4) ^ circularLeft((paramArrayOfInt[12] ^ arrayOfInt1[12]) - (paramArrayOfInt[4] ^ arrayOfInt1[4]) - (paramArrayOfInt[6] ^ arrayOfInt1[6]) - (paramArrayOfInt[9] ^ arrayOfInt1[9]) + (paramArrayOfInt[13] ^ arrayOfInt1[13]), 19)) + arrayOfInt1[0]);
/* 181:    */     
/* 182:    */ 
/* 183:    */ 
/* 184:    */ 
/* 185:    */ 
/* 186:    */ 
/* 187:    */ 
/* 188:    */ 
/* 189:    */ 
/* 190:    */ 
/* 191:238 */     arrayOfInt2[16] = ((arrayOfInt2[0] >>> 1 ^ arrayOfInt2[0] << 2 ^ circularLeft(arrayOfInt2[0], 8) ^ circularLeft(arrayOfInt2[0], 23)) + (arrayOfInt2[1] >>> 2 ^ arrayOfInt2[1] << 1 ^ circularLeft(arrayOfInt2[1], 12) ^ circularLeft(arrayOfInt2[1], 25)) + (arrayOfInt2[2] >>> 2 ^ arrayOfInt2[2] << 2 ^ circularLeft(arrayOfInt2[2], 15) ^ circularLeft(arrayOfInt2[2], 29)) + (arrayOfInt2[3] >>> 1 ^ arrayOfInt2[3] << 3 ^ circularLeft(arrayOfInt2[3], 4) ^ circularLeft(arrayOfInt2[3], 19)) + (arrayOfInt2[4] >>> 1 ^ arrayOfInt2[4] << 2 ^ circularLeft(arrayOfInt2[4], 8) ^ circularLeft(arrayOfInt2[4], 23)) + (arrayOfInt2[5] >>> 2 ^ arrayOfInt2[5] << 1 ^ circularLeft(arrayOfInt2[5], 12) ^ circularLeft(arrayOfInt2[5], 25)) + (arrayOfInt2[6] >>> 2 ^ arrayOfInt2[6] << 2 ^ circularLeft(arrayOfInt2[6], 15) ^ circularLeft(arrayOfInt2[6], 29)) + (arrayOfInt2[7] >>> 1 ^ arrayOfInt2[7] << 3 ^ circularLeft(arrayOfInt2[7], 4) ^ circularLeft(arrayOfInt2[7], 19)) + (arrayOfInt2[8] >>> 1 ^ arrayOfInt2[8] << 2 ^ circularLeft(arrayOfInt2[8], 8) ^ circularLeft(arrayOfInt2[8], 23)) + (arrayOfInt2[9] >>> 2 ^ arrayOfInt2[9] << 1 ^ circularLeft(arrayOfInt2[9], 12) ^ circularLeft(arrayOfInt2[9], 25)) + (arrayOfInt2[10] >>> 2 ^ arrayOfInt2[10] << 2 ^ circularLeft(arrayOfInt2[10], 15) ^ circularLeft(arrayOfInt2[10], 29)) + (arrayOfInt2[11] >>> 1 ^ arrayOfInt2[11] << 3 ^ circularLeft(arrayOfInt2[11], 4) ^ circularLeft(arrayOfInt2[11], 19)) + (arrayOfInt2[12] >>> 1 ^ arrayOfInt2[12] << 2 ^ circularLeft(arrayOfInt2[12], 8) ^ circularLeft(arrayOfInt2[12], 23)) + (arrayOfInt2[13] >>> 2 ^ arrayOfInt2[13] << 1 ^ circularLeft(arrayOfInt2[13], 12) ^ circularLeft(arrayOfInt2[13], 25)) + (arrayOfInt2[14] >>> 2 ^ arrayOfInt2[14] << 2 ^ circularLeft(arrayOfInt2[14], 15) ^ circularLeft(arrayOfInt2[14], 29)) + (arrayOfInt2[15] >>> 1 ^ arrayOfInt2[15] << 3 ^ circularLeft(arrayOfInt2[15], 4) ^ circularLeft(arrayOfInt2[15], 19)) + (circularLeft(paramArrayOfInt[0], 1) + circularLeft(paramArrayOfInt[3], 4) - circularLeft(paramArrayOfInt[10], 11) + 1431655760 ^ arrayOfInt1[7]));
/* 192:    */     
/* 193:    */ 
/* 194:    */ 
/* 195:    */ 
/* 196:    */ 
/* 197:    */ 
/* 198:    */ 
/* 199:    */ 
/* 200:    */ 
/* 201:    */ 
/* 202:    */ 
/* 203:    */ 
/* 204:    */ 
/* 205:    */ 
/* 206:    */ 
/* 207:    */ 
/* 208:    */ 
/* 209:    */ 
/* 210:    */ 
/* 211:    */ 
/* 212:    */ 
/* 213:    */ 
/* 214:    */ 
/* 215:    */ 
/* 216:    */ 
/* 217:    */ 
/* 218:    */ 
/* 219:    */ 
/* 220:    */ 
/* 221:    */ 
/* 222:    */ 
/* 223:    */ 
/* 224:    */ 
/* 225:272 */     arrayOfInt2[17] = ((arrayOfInt2[1] >>> 1 ^ arrayOfInt2[1] << 2 ^ circularLeft(arrayOfInt2[1], 8) ^ circularLeft(arrayOfInt2[1], 23)) + (arrayOfInt2[2] >>> 2 ^ arrayOfInt2[2] << 1 ^ circularLeft(arrayOfInt2[2], 12) ^ circularLeft(arrayOfInt2[2], 25)) + (arrayOfInt2[3] >>> 2 ^ arrayOfInt2[3] << 2 ^ circularLeft(arrayOfInt2[3], 15) ^ circularLeft(arrayOfInt2[3], 29)) + (arrayOfInt2[4] >>> 1 ^ arrayOfInt2[4] << 3 ^ circularLeft(arrayOfInt2[4], 4) ^ circularLeft(arrayOfInt2[4], 19)) + (arrayOfInt2[5] >>> 1 ^ arrayOfInt2[5] << 2 ^ circularLeft(arrayOfInt2[5], 8) ^ circularLeft(arrayOfInt2[5], 23)) + (arrayOfInt2[6] >>> 2 ^ arrayOfInt2[6] << 1 ^ circularLeft(arrayOfInt2[6], 12) ^ circularLeft(arrayOfInt2[6], 25)) + (arrayOfInt2[7] >>> 2 ^ arrayOfInt2[7] << 2 ^ circularLeft(arrayOfInt2[7], 15) ^ circularLeft(arrayOfInt2[7], 29)) + (arrayOfInt2[8] >>> 1 ^ arrayOfInt2[8] << 3 ^ circularLeft(arrayOfInt2[8], 4) ^ circularLeft(arrayOfInt2[8], 19)) + (arrayOfInt2[9] >>> 1 ^ arrayOfInt2[9] << 2 ^ circularLeft(arrayOfInt2[9], 8) ^ circularLeft(arrayOfInt2[9], 23)) + (arrayOfInt2[10] >>> 2 ^ arrayOfInt2[10] << 1 ^ circularLeft(arrayOfInt2[10], 12) ^ circularLeft(arrayOfInt2[10], 25)) + (arrayOfInt2[11] >>> 2 ^ arrayOfInt2[11] << 2 ^ circularLeft(arrayOfInt2[11], 15) ^ circularLeft(arrayOfInt2[11], 29)) + (arrayOfInt2[12] >>> 1 ^ arrayOfInt2[12] << 3 ^ circularLeft(arrayOfInt2[12], 4) ^ circularLeft(arrayOfInt2[12], 19)) + (arrayOfInt2[13] >>> 1 ^ arrayOfInt2[13] << 2 ^ circularLeft(arrayOfInt2[13], 8) ^ circularLeft(arrayOfInt2[13], 23)) + (arrayOfInt2[14] >>> 2 ^ arrayOfInt2[14] << 1 ^ circularLeft(arrayOfInt2[14], 12) ^ circularLeft(arrayOfInt2[14], 25)) + (arrayOfInt2[15] >>> 2 ^ arrayOfInt2[15] << 2 ^ circularLeft(arrayOfInt2[15], 15) ^ circularLeft(arrayOfInt2[15], 29)) + (arrayOfInt2[16] >>> 1 ^ arrayOfInt2[16] << 3 ^ circularLeft(arrayOfInt2[16], 4) ^ circularLeft(arrayOfInt2[16], 19)) + (circularLeft(paramArrayOfInt[1], 2) + circularLeft(paramArrayOfInt[4], 5) - circularLeft(paramArrayOfInt[11], 12) + 1521134245 ^ arrayOfInt1[8]));
/* 226:    */     
/* 227:    */ 
/* 228:    */ 
/* 229:    */ 
/* 230:    */ 
/* 231:    */ 
/* 232:    */ 
/* 233:    */ 
/* 234:    */ 
/* 235:    */ 
/* 236:    */ 
/* 237:    */ 
/* 238:    */ 
/* 239:    */ 
/* 240:    */ 
/* 241:    */ 
/* 242:    */ 
/* 243:    */ 
/* 244:    */ 
/* 245:    */ 
/* 246:    */ 
/* 247:    */ 
/* 248:    */ 
/* 249:    */ 
/* 250:    */ 
/* 251:    */ 
/* 252:    */ 
/* 253:    */ 
/* 254:    */ 
/* 255:    */ 
/* 256:    */ 
/* 257:    */ 
/* 258:    */ 
/* 259:306 */     arrayOfInt2[18] = (arrayOfInt2[2] + circularLeft(arrayOfInt2[3], 3) + arrayOfInt2[4] + circularLeft(arrayOfInt2[5], 7) + arrayOfInt2[6] + circularLeft(arrayOfInt2[7], 13) + arrayOfInt2[8] + circularLeft(arrayOfInt2[9], 16) + arrayOfInt2[10] + circularLeft(arrayOfInt2[11], 19) + arrayOfInt2[12] + circularLeft(arrayOfInt2[13], 23) + arrayOfInt2[14] + circularLeft(arrayOfInt2[15], 27) + (arrayOfInt2[16] >>> 1 ^ arrayOfInt2[16]) + (arrayOfInt2[17] >>> 2 ^ arrayOfInt2[17]) + (circularLeft(paramArrayOfInt[2], 3) + circularLeft(paramArrayOfInt[5], 6) - circularLeft(paramArrayOfInt[12], 13) + 1610612730 ^ arrayOfInt1[9]));
/* 260:    */     
/* 261:    */ 
/* 262:    */ 
/* 263:    */ 
/* 264:    */ 
/* 265:    */ 
/* 266:    */ 
/* 267:    */ 
/* 268:    */ 
/* 269:    */ 
/* 270:317 */     arrayOfInt2[19] = (arrayOfInt2[3] + circularLeft(arrayOfInt2[4], 3) + arrayOfInt2[5] + circularLeft(arrayOfInt2[6], 7) + arrayOfInt2[7] + circularLeft(arrayOfInt2[8], 13) + arrayOfInt2[9] + circularLeft(arrayOfInt2[10], 16) + arrayOfInt2[11] + circularLeft(arrayOfInt2[12], 19) + arrayOfInt2[13] + circularLeft(arrayOfInt2[14], 23) + arrayOfInt2[15] + circularLeft(arrayOfInt2[16], 27) + (arrayOfInt2[17] >>> 1 ^ arrayOfInt2[17]) + (arrayOfInt2[18] >>> 2 ^ arrayOfInt2[18]) + (circularLeft(paramArrayOfInt[3], 4) + circularLeft(paramArrayOfInt[6], 7) - circularLeft(paramArrayOfInt[13], 14) + 1700091215 ^ arrayOfInt1[10]));
/* 271:    */     
/* 272:    */ 
/* 273:    */ 
/* 274:    */ 
/* 275:    */ 
/* 276:    */ 
/* 277:    */ 
/* 278:    */ 
/* 279:    */ 
/* 280:    */ 
/* 281:328 */     arrayOfInt2[20] = (arrayOfInt2[4] + circularLeft(arrayOfInt2[5], 3) + arrayOfInt2[6] + circularLeft(arrayOfInt2[7], 7) + arrayOfInt2[8] + circularLeft(arrayOfInt2[9], 13) + arrayOfInt2[10] + circularLeft(arrayOfInt2[11], 16) + arrayOfInt2[12] + circularLeft(arrayOfInt2[13], 19) + arrayOfInt2[14] + circularLeft(arrayOfInt2[15], 23) + arrayOfInt2[16] + circularLeft(arrayOfInt2[17], 27) + (arrayOfInt2[18] >>> 1 ^ arrayOfInt2[18]) + (arrayOfInt2[19] >>> 2 ^ arrayOfInt2[19]) + (circularLeft(paramArrayOfInt[4], 5) + circularLeft(paramArrayOfInt[7], 8) - circularLeft(paramArrayOfInt[14], 15) + 1789569700 ^ arrayOfInt1[11]));
/* 282:    */     
/* 283:    */ 
/* 284:    */ 
/* 285:    */ 
/* 286:    */ 
/* 287:    */ 
/* 288:    */ 
/* 289:    */ 
/* 290:    */ 
/* 291:    */ 
/* 292:339 */     arrayOfInt2[21] = (arrayOfInt2[5] + circularLeft(arrayOfInt2[6], 3) + arrayOfInt2[7] + circularLeft(arrayOfInt2[8], 7) + arrayOfInt2[9] + circularLeft(arrayOfInt2[10], 13) + arrayOfInt2[11] + circularLeft(arrayOfInt2[12], 16) + arrayOfInt2[13] + circularLeft(arrayOfInt2[14], 19) + arrayOfInt2[15] + circularLeft(arrayOfInt2[16], 23) + arrayOfInt2[17] + circularLeft(arrayOfInt2[18], 27) + (arrayOfInt2[19] >>> 1 ^ arrayOfInt2[19]) + (arrayOfInt2[20] >>> 2 ^ arrayOfInt2[20]) + (circularLeft(paramArrayOfInt[5], 6) + circularLeft(paramArrayOfInt[8], 9) - circularLeft(paramArrayOfInt[15], 16) + 1879048185 ^ arrayOfInt1[12]));
/* 293:    */     
/* 294:    */ 
/* 295:    */ 
/* 296:    */ 
/* 297:    */ 
/* 298:    */ 
/* 299:    */ 
/* 300:    */ 
/* 301:    */ 
/* 302:    */ 
/* 303:350 */     arrayOfInt2[22] = (arrayOfInt2[6] + circularLeft(arrayOfInt2[7], 3) + arrayOfInt2[8] + circularLeft(arrayOfInt2[9], 7) + arrayOfInt2[10] + circularLeft(arrayOfInt2[11], 13) + arrayOfInt2[12] + circularLeft(arrayOfInt2[13], 16) + arrayOfInt2[14] + circularLeft(arrayOfInt2[15], 19) + arrayOfInt2[16] + circularLeft(arrayOfInt2[17], 23) + arrayOfInt2[18] + circularLeft(arrayOfInt2[19], 27) + (arrayOfInt2[20] >>> 1 ^ arrayOfInt2[20]) + (arrayOfInt2[21] >>> 2 ^ arrayOfInt2[21]) + (circularLeft(paramArrayOfInt[6], 7) + circularLeft(paramArrayOfInt[9], 10) - circularLeft(paramArrayOfInt[0], 1) + 1968526670 ^ arrayOfInt1[13]));
/* 304:    */     
/* 305:    */ 
/* 306:    */ 
/* 307:    */ 
/* 308:    */ 
/* 309:    */ 
/* 310:    */ 
/* 311:    */ 
/* 312:    */ 
/* 313:    */ 
/* 314:361 */     arrayOfInt2[23] = (arrayOfInt2[7] + circularLeft(arrayOfInt2[8], 3) + arrayOfInt2[9] + circularLeft(arrayOfInt2[10], 7) + arrayOfInt2[11] + circularLeft(arrayOfInt2[12], 13) + arrayOfInt2[13] + circularLeft(arrayOfInt2[14], 16) + arrayOfInt2[15] + circularLeft(arrayOfInt2[16], 19) + arrayOfInt2[17] + circularLeft(arrayOfInt2[18], 23) + arrayOfInt2[19] + circularLeft(arrayOfInt2[20], 27) + (arrayOfInt2[21] >>> 1 ^ arrayOfInt2[21]) + (arrayOfInt2[22] >>> 2 ^ arrayOfInt2[22]) + (circularLeft(paramArrayOfInt[7], 8) + circularLeft(paramArrayOfInt[10], 11) - circularLeft(paramArrayOfInt[1], 2) + 2058005155 ^ arrayOfInt1[14]));
/* 315:    */     
/* 316:    */ 
/* 317:    */ 
/* 318:    */ 
/* 319:    */ 
/* 320:    */ 
/* 321:    */ 
/* 322:    */ 
/* 323:    */ 
/* 324:    */ 
/* 325:372 */     arrayOfInt2[24] = (arrayOfInt2[8] + circularLeft(arrayOfInt2[9], 3) + arrayOfInt2[10] + circularLeft(arrayOfInt2[11], 7) + arrayOfInt2[12] + circularLeft(arrayOfInt2[13], 13) + arrayOfInt2[14] + circularLeft(arrayOfInt2[15], 16) + arrayOfInt2[16] + circularLeft(arrayOfInt2[17], 19) + arrayOfInt2[18] + circularLeft(arrayOfInt2[19], 23) + arrayOfInt2[20] + circularLeft(arrayOfInt2[21], 27) + (arrayOfInt2[22] >>> 1 ^ arrayOfInt2[22]) + (arrayOfInt2[23] >>> 2 ^ arrayOfInt2[23]) + (circularLeft(paramArrayOfInt[8], 9) + circularLeft(paramArrayOfInt[11], 12) - circularLeft(paramArrayOfInt[2], 3) + 2147483640 ^ arrayOfInt1[15]));
/* 326:    */     
/* 327:    */ 
/* 328:    */ 
/* 329:    */ 
/* 330:    */ 
/* 331:    */ 
/* 332:    */ 
/* 333:    */ 
/* 334:    */ 
/* 335:    */ 
/* 336:383 */     arrayOfInt2[25] = (arrayOfInt2[9] + circularLeft(arrayOfInt2[10], 3) + arrayOfInt2[11] + circularLeft(arrayOfInt2[12], 7) + arrayOfInt2[13] + circularLeft(arrayOfInt2[14], 13) + arrayOfInt2[15] + circularLeft(arrayOfInt2[16], 16) + arrayOfInt2[17] + circularLeft(arrayOfInt2[18], 19) + arrayOfInt2[19] + circularLeft(arrayOfInt2[20], 23) + arrayOfInt2[21] + circularLeft(arrayOfInt2[22], 27) + (arrayOfInt2[23] >>> 1 ^ arrayOfInt2[23]) + (arrayOfInt2[24] >>> 2 ^ arrayOfInt2[24]) + (circularLeft(paramArrayOfInt[9], 10) + circularLeft(paramArrayOfInt[12], 13) - circularLeft(paramArrayOfInt[3], 4) + -2058005171 ^ arrayOfInt1[0]));
/* 337:    */     
/* 338:    */ 
/* 339:    */ 
/* 340:    */ 
/* 341:    */ 
/* 342:    */ 
/* 343:    */ 
/* 344:    */ 
/* 345:    */ 
/* 346:    */ 
/* 347:394 */     arrayOfInt2[26] = (arrayOfInt2[10] + circularLeft(arrayOfInt2[11], 3) + arrayOfInt2[12] + circularLeft(arrayOfInt2[13], 7) + arrayOfInt2[14] + circularLeft(arrayOfInt2[15], 13) + arrayOfInt2[16] + circularLeft(arrayOfInt2[17], 16) + arrayOfInt2[18] + circularLeft(arrayOfInt2[19], 19) + arrayOfInt2[20] + circularLeft(arrayOfInt2[21], 23) + arrayOfInt2[22] + circularLeft(arrayOfInt2[23], 27) + (arrayOfInt2[24] >>> 1 ^ arrayOfInt2[24]) + (arrayOfInt2[25] >>> 2 ^ arrayOfInt2[25]) + (circularLeft(paramArrayOfInt[10], 11) + circularLeft(paramArrayOfInt[13], 14) - circularLeft(paramArrayOfInt[4], 5) + -1968526686 ^ arrayOfInt1[1]));
/* 348:    */     
/* 349:    */ 
/* 350:    */ 
/* 351:    */ 
/* 352:    */ 
/* 353:    */ 
/* 354:    */ 
/* 355:    */ 
/* 356:    */ 
/* 357:    */ 
/* 358:405 */     arrayOfInt2[27] = (arrayOfInt2[11] + circularLeft(arrayOfInt2[12], 3) + arrayOfInt2[13] + circularLeft(arrayOfInt2[14], 7) + arrayOfInt2[15] + circularLeft(arrayOfInt2[16], 13) + arrayOfInt2[17] + circularLeft(arrayOfInt2[18], 16) + arrayOfInt2[19] + circularLeft(arrayOfInt2[20], 19) + arrayOfInt2[21] + circularLeft(arrayOfInt2[22], 23) + arrayOfInt2[23] + circularLeft(arrayOfInt2[24], 27) + (arrayOfInt2[25] >>> 1 ^ arrayOfInt2[25]) + (arrayOfInt2[26] >>> 2 ^ arrayOfInt2[26]) + (circularLeft(paramArrayOfInt[11], 12) + circularLeft(paramArrayOfInt[14], 15) - circularLeft(paramArrayOfInt[5], 6) + -1879048201 ^ arrayOfInt1[2]));
/* 359:    */     
/* 360:    */ 
/* 361:    */ 
/* 362:    */ 
/* 363:    */ 
/* 364:    */ 
/* 365:    */ 
/* 366:    */ 
/* 367:    */ 
/* 368:    */ 
/* 369:416 */     arrayOfInt2[28] = (arrayOfInt2[12] + circularLeft(arrayOfInt2[13], 3) + arrayOfInt2[14] + circularLeft(arrayOfInt2[15], 7) + arrayOfInt2[16] + circularLeft(arrayOfInt2[17], 13) + arrayOfInt2[18] + circularLeft(arrayOfInt2[19], 16) + arrayOfInt2[20] + circularLeft(arrayOfInt2[21], 19) + arrayOfInt2[22] + circularLeft(arrayOfInt2[23], 23) + arrayOfInt2[24] + circularLeft(arrayOfInt2[25], 27) + (arrayOfInt2[26] >>> 1 ^ arrayOfInt2[26]) + (arrayOfInt2[27] >>> 2 ^ arrayOfInt2[27]) + (circularLeft(paramArrayOfInt[12], 13) + circularLeft(paramArrayOfInt[15], 16) - circularLeft(paramArrayOfInt[6], 7) + -1789569716 ^ arrayOfInt1[3]));
/* 370:    */     
/* 371:    */ 
/* 372:    */ 
/* 373:    */ 
/* 374:    */ 
/* 375:    */ 
/* 376:    */ 
/* 377:    */ 
/* 378:    */ 
/* 379:    */ 
/* 380:427 */     arrayOfInt2[29] = (arrayOfInt2[13] + circularLeft(arrayOfInt2[14], 3) + arrayOfInt2[15] + circularLeft(arrayOfInt2[16], 7) + arrayOfInt2[17] + circularLeft(arrayOfInt2[18], 13) + arrayOfInt2[19] + circularLeft(arrayOfInt2[20], 16) + arrayOfInt2[21] + circularLeft(arrayOfInt2[22], 19) + arrayOfInt2[23] + circularLeft(arrayOfInt2[24], 23) + arrayOfInt2[25] + circularLeft(arrayOfInt2[26], 27) + (arrayOfInt2[27] >>> 1 ^ arrayOfInt2[27]) + (arrayOfInt2[28] >>> 2 ^ arrayOfInt2[28]) + (circularLeft(paramArrayOfInt[13], 14) + circularLeft(paramArrayOfInt[0], 1) - circularLeft(paramArrayOfInt[7], 8) + -1700091231 ^ arrayOfInt1[4]));
/* 381:    */     
/* 382:    */ 
/* 383:    */ 
/* 384:    */ 
/* 385:    */ 
/* 386:    */ 
/* 387:    */ 
/* 388:    */ 
/* 389:    */ 
/* 390:    */ 
/* 391:438 */     arrayOfInt2[30] = (arrayOfInt2[14] + circularLeft(arrayOfInt2[15], 3) + arrayOfInt2[16] + circularLeft(arrayOfInt2[17], 7) + arrayOfInt2[18] + circularLeft(arrayOfInt2[19], 13) + arrayOfInt2[20] + circularLeft(arrayOfInt2[21], 16) + arrayOfInt2[22] + circularLeft(arrayOfInt2[23], 19) + arrayOfInt2[24] + circularLeft(arrayOfInt2[25], 23) + arrayOfInt2[26] + circularLeft(arrayOfInt2[27], 27) + (arrayOfInt2[28] >>> 1 ^ arrayOfInt2[28]) + (arrayOfInt2[29] >>> 2 ^ arrayOfInt2[29]) + (circularLeft(paramArrayOfInt[14], 15) + circularLeft(paramArrayOfInt[1], 2) - circularLeft(paramArrayOfInt[8], 9) + -1610612746 ^ arrayOfInt1[5]));
/* 392:    */     
/* 393:    */ 
/* 394:    */ 
/* 395:    */ 
/* 396:    */ 
/* 397:    */ 
/* 398:    */ 
/* 399:    */ 
/* 400:    */ 
/* 401:    */ 
/* 402:449 */     arrayOfInt2[31] = (arrayOfInt2[15] + circularLeft(arrayOfInt2[16], 3) + arrayOfInt2[17] + circularLeft(arrayOfInt2[18], 7) + arrayOfInt2[19] + circularLeft(arrayOfInt2[20], 13) + arrayOfInt2[21] + circularLeft(arrayOfInt2[22], 16) + arrayOfInt2[23] + circularLeft(arrayOfInt2[24], 19) + arrayOfInt2[25] + circularLeft(arrayOfInt2[26], 23) + arrayOfInt2[27] + circularLeft(arrayOfInt2[28], 27) + (arrayOfInt2[29] >>> 1 ^ arrayOfInt2[29]) + (arrayOfInt2[30] >>> 2 ^ arrayOfInt2[30]) + (circularLeft(paramArrayOfInt[15], 16) + circularLeft(paramArrayOfInt[2], 3) - circularLeft(paramArrayOfInt[9], 10) + -1521134261 ^ arrayOfInt1[6]));
/* 403:    */     
/* 404:    */ 
/* 405:    */ 
/* 406:    */ 
/* 407:    */ 
/* 408:    */ 
/* 409:    */ 
/* 410:    */ 
/* 411:    */ 
/* 412:    */ 
/* 413:460 */     int i = arrayOfInt2[16] ^ arrayOfInt2[17] ^ arrayOfInt2[18] ^ arrayOfInt2[19] ^ arrayOfInt2[20] ^ arrayOfInt2[21] ^ arrayOfInt2[22] ^ arrayOfInt2[23];
/* 414:    */     
/* 415:462 */     int j = i ^ arrayOfInt2[24] ^ arrayOfInt2[25] ^ arrayOfInt2[26] ^ arrayOfInt2[27] ^ arrayOfInt2[28] ^ arrayOfInt2[29] ^ arrayOfInt2[30] ^ arrayOfInt2[31];
/* 416:    */     
/* 417:464 */     arrayOfInt1[0] = ((j << 5 ^ arrayOfInt2[16] >>> 5 ^ paramArrayOfInt[0]) + (i ^ arrayOfInt2[24] ^ arrayOfInt2[0]));
/* 418:465 */     arrayOfInt1[1] = ((j >>> 7 ^ arrayOfInt2[17] << 8 ^ paramArrayOfInt[1]) + (i ^ arrayOfInt2[25] ^ arrayOfInt2[1]));
/* 419:466 */     arrayOfInt1[2] = ((j >>> 5 ^ arrayOfInt2[18] << 5 ^ paramArrayOfInt[2]) + (i ^ arrayOfInt2[26] ^ arrayOfInt2[2]));
/* 420:467 */     arrayOfInt1[3] = ((j >>> 1 ^ arrayOfInt2[19] << 5 ^ paramArrayOfInt[3]) + (i ^ arrayOfInt2[27] ^ arrayOfInt2[3]));
/* 421:468 */     arrayOfInt1[4] = ((j >>> 3 ^ arrayOfInt2[20] << 0 ^ paramArrayOfInt[4]) + (i ^ arrayOfInt2[28] ^ arrayOfInt2[4]));
/* 422:469 */     arrayOfInt1[5] = ((j << 6 ^ arrayOfInt2[21] >>> 6 ^ paramArrayOfInt[5]) + (i ^ arrayOfInt2[29] ^ arrayOfInt2[5]));
/* 423:470 */     arrayOfInt1[6] = ((j >>> 4 ^ arrayOfInt2[22] << 6 ^ paramArrayOfInt[6]) + (i ^ arrayOfInt2[30] ^ arrayOfInt2[6]));
/* 424:471 */     arrayOfInt1[7] = ((j >>> 11 ^ arrayOfInt2[23] << 2 ^ paramArrayOfInt[7]) + (i ^ arrayOfInt2[31] ^ arrayOfInt2[7]));
/* 425:    */     
/* 426:473 */     arrayOfInt1[8] = (circularLeft(arrayOfInt1[4], 9) + (j ^ arrayOfInt2[24] ^ paramArrayOfInt[8]) + (i << 8 ^ arrayOfInt2[23] ^ arrayOfInt2[8]));
/* 427:    */     
/* 428:475 */     arrayOfInt1[9] = (circularLeft(arrayOfInt1[5], 10) + (j ^ arrayOfInt2[25] ^ paramArrayOfInt[9]) + (i >>> 6 ^ arrayOfInt2[16] ^ arrayOfInt2[9]));
/* 429:    */     
/* 430:477 */     arrayOfInt1[10] = (circularLeft(arrayOfInt1[6], 11) + (j ^ arrayOfInt2[26] ^ paramArrayOfInt[10]) + (i << 6 ^ arrayOfInt2[17] ^ arrayOfInt2[10]));
/* 431:    */     
/* 432:479 */     arrayOfInt1[11] = (circularLeft(arrayOfInt1[7], 12) + (j ^ arrayOfInt2[27] ^ paramArrayOfInt[11]) + (i << 4 ^ arrayOfInt2[18] ^ arrayOfInt2[11]));
/* 433:    */     
/* 434:481 */     arrayOfInt1[12] = (circularLeft(arrayOfInt1[0], 13) + (j ^ arrayOfInt2[28] ^ paramArrayOfInt[12]) + (i >>> 3 ^ arrayOfInt2[19] ^ arrayOfInt2[12]));
/* 435:    */     
/* 436:483 */     arrayOfInt1[13] = (circularLeft(arrayOfInt1[1], 14) + (j ^ arrayOfInt2[29] ^ paramArrayOfInt[13]) + (i >>> 4 ^ arrayOfInt2[20] ^ arrayOfInt2[13]));
/* 437:    */     
/* 438:485 */     arrayOfInt1[14] = (circularLeft(arrayOfInt1[2], 15) + (j ^ arrayOfInt2[30] ^ paramArrayOfInt[14]) + (i >>> 7 ^ arrayOfInt2[21] ^ arrayOfInt2[14]));
/* 439:    */     
/* 440:487 */     arrayOfInt1[15] = (circularLeft(arrayOfInt1[3], 16) + (j ^ arrayOfInt2[31] ^ paramArrayOfInt[15]) + (i >>> 2 ^ arrayOfInt2[22] ^ arrayOfInt2[15]));
/* 441:    */   }
/* 442:    */   
/* 443:    */   protected void doPadding(byte[] paramArrayOfByte, int paramInt)
/* 444:    */   {
/* 445:494 */     byte[] arrayOfByte = getBlockBuffer();
/* 446:495 */     int i = flush();
/* 447:496 */     long l = (getBlockCount() << 9) + (i << 3);
/* 448:497 */     arrayOfByte[(i++)] = Byte.MIN_VALUE;
/* 449:498 */     if (i > 56)
/* 450:    */     {
/* 451:499 */       for (j = i; j < 64; j++) {
/* 452:500 */         arrayOfByte[j] = 0;
/* 453:    */       }
/* 454:501 */       processBlock(arrayOfByte);
/* 455:502 */       i = 0;
/* 456:    */     }
/* 457:504 */     for (int j = i; j < 56; j++) {
/* 458:505 */       arrayOfByte[j] = 0;
/* 459:    */     }
/* 460:506 */     encodeLEInt((int)l, arrayOfByte, 56);
/* 461:507 */     encodeLEInt((int)(l >>> 32), arrayOfByte, 60);
/* 462:508 */     processBlock(arrayOfByte);
/* 463:509 */     int[] arrayOfInt = this.H;
/* 464:510 */     this.H = this.H2;
/* 465:511 */     this.H2 = arrayOfInt;
/* 466:512 */     System.arraycopy(FINAL, 0, this.H, 0, 16);
/* 467:513 */     compress(this.H2);
/* 468:514 */     int k = getDigestLength() >>> 2;
/* 469:515 */     int m = 0;
/* 470:515 */     for (int n = 16 - k; m < k; n++)
/* 471:    */     {
/* 472:516 */       encodeLEInt(this.H[n], paramArrayOfByte, paramInt + 4 * m);m++;
/* 473:    */     }
/* 474:    */   }
/* 475:    */   
/* 476:    */   protected void doInit()
/* 477:    */   {
/* 478:522 */     this.M = new int[16];
/* 479:523 */     this.H = new int[16];
/* 480:524 */     this.H2 = new int[16];
/* 481:525 */     this.Q = new int[32];
/* 482:526 */     engineReset();
/* 483:    */   }
/* 484:    */   
/* 485:    */   private static final void encodeLEInt(int paramInt1, byte[] paramArrayOfByte, int paramInt2)
/* 486:    */   {
/* 487:540 */     paramArrayOfByte[(paramInt2 + 0)] = ((byte)paramInt1);
/* 488:541 */     paramArrayOfByte[(paramInt2 + 1)] = ((byte)(paramInt1 >>> 8));
/* 489:542 */     paramArrayOfByte[(paramInt2 + 2)] = ((byte)(paramInt1 >>> 16));
/* 490:543 */     paramArrayOfByte[(paramInt2 + 3)] = ((byte)(paramInt1 >>> 24));
/* 491:    */   }
/* 492:    */   
/* 493:    */   private static final int decodeLEInt(byte[] paramArrayOfByte, int paramInt)
/* 494:    */   {
/* 495:556 */     return (paramArrayOfByte[(paramInt + 3)] & 0xFF) << 24 | (paramArrayOfByte[(paramInt + 2)] & 0xFF) << 16 | (paramArrayOfByte[(paramInt + 1)] & 0xFF) << 8 | paramArrayOfByte[(paramInt + 0)] & 0xFF;
/* 496:    */   }
/* 497:    */   
/* 498:    */   private static final int circularLeft(int paramInt1, int paramInt2)
/* 499:    */   {
/* 500:573 */     return paramInt1 << paramInt2 | paramInt1 >>> 32 - paramInt2;
/* 501:    */   }
/* 502:    */   
/* 503:    */   protected void processBlock(byte[] paramArrayOfByte)
/* 504:    */   {
/* 505:579 */     for (int i = 0; i < 16; i++) {
/* 506:580 */       this.M[i] = decodeLEInt(paramArrayOfByte, i * 4);
/* 507:    */     }
/* 508:581 */     compress(this.M);
/* 509:    */   }
/* 510:    */   
/* 511:    */   public String toString()
/* 512:    */   {
/* 513:587 */     return "BMW-" + (getDigestLength() << 3);
/* 514:    */   }
/* 515:    */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     fr.cryptohash.BMWSmallCore
 * JD-Core Version:    0.7.1
 */