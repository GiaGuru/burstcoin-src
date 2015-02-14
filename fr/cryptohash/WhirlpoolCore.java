/*   1:    */ package fr.cryptohash;
/*   2:    */ 
/*   3:    */ abstract class WhirlpoolCore
/*   4:    */   extends MDHelper
/*   5:    */ {
/*   6:    */   private final long[] T0;
/*   7:    */   private final long[] T1;
/*   8:    */   private final long[] T2;
/*   9:    */   private final long[] T3;
/*  10:    */   private final long[] T4;
/*  11:    */   private final long[] T5;
/*  12:    */   private final long[] T6;
/*  13:    */   private final long[] T7;
/*  14:    */   private final long[] RC;
/*  15:    */   private long state0;
/*  16:    */   private long state1;
/*  17:    */   private long state2;
/*  18:    */   private long state3;
/*  19:    */   private long state4;
/*  20:    */   private long state5;
/*  21:    */   private long state6;
/*  22:    */   private long state7;
/*  23:    */   
/*  24:    */   WhirlpoolCore(long[] paramArrayOfLong1, long[] paramArrayOfLong2, long[] paramArrayOfLong3, long[] paramArrayOfLong4, long[] paramArrayOfLong5, long[] paramArrayOfLong6, long[] paramArrayOfLong7, long[] paramArrayOfLong8, long[] paramArrayOfLong9)
/*  25:    */   {
/*  26: 49 */     super(false, 32);
/*  27: 50 */     this.T0 = paramArrayOfLong1;
/*  28: 51 */     this.T1 = paramArrayOfLong2;
/*  29: 52 */     this.T2 = paramArrayOfLong3;
/*  30: 53 */     this.T3 = paramArrayOfLong4;
/*  31: 54 */     this.T4 = paramArrayOfLong5;
/*  32: 55 */     this.T5 = paramArrayOfLong6;
/*  33: 56 */     this.T6 = paramArrayOfLong7;
/*  34: 57 */     this.T7 = paramArrayOfLong8;
/*  35: 58 */     this.RC = paramArrayOfLong9;
/*  36:    */   }
/*  37:    */   
/*  38:    */   protected Digest copyState(WhirlpoolCore paramWhirlpoolCore)
/*  39:    */   {
/*  40: 69 */     paramWhirlpoolCore.state0 = this.state0;
/*  41: 70 */     paramWhirlpoolCore.state1 = this.state1;
/*  42: 71 */     paramWhirlpoolCore.state2 = this.state2;
/*  43: 72 */     paramWhirlpoolCore.state3 = this.state3;
/*  44: 73 */     paramWhirlpoolCore.state4 = this.state4;
/*  45: 74 */     paramWhirlpoolCore.state5 = this.state5;
/*  46: 75 */     paramWhirlpoolCore.state6 = this.state6;
/*  47: 76 */     paramWhirlpoolCore.state7 = this.state7;
/*  48: 77 */     return super.copyState(paramWhirlpoolCore);
/*  49:    */   }
/*  50:    */   
/*  51:    */   public int getDigestLength()
/*  52:    */   {
/*  53: 83 */     return 64;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public int getBlockLength()
/*  57:    */   {
/*  58: 89 */     return 64;
/*  59:    */   }
/*  60:    */   
/*  61:    */   protected void engineReset()
/*  62:    */   {
/*  63: 95 */     this.state0 = 0L;
/*  64: 96 */     this.state1 = 0L;
/*  65: 97 */     this.state2 = 0L;
/*  66: 98 */     this.state3 = 0L;
/*  67: 99 */     this.state4 = 0L;
/*  68:100 */     this.state5 = 0L;
/*  69:101 */     this.state6 = 0L;
/*  70:102 */     this.state7 = 0L;
/*  71:    */   }
/*  72:    */   
/*  73:    */   protected void doPadding(byte[] paramArrayOfByte, int paramInt)
/*  74:    */   {
/*  75:108 */     makeMDPadding();
/*  76:109 */     encodeLELong(this.state0, paramArrayOfByte, paramInt);
/*  77:110 */     encodeLELong(this.state1, paramArrayOfByte, paramInt + 8);
/*  78:111 */     encodeLELong(this.state2, paramArrayOfByte, paramInt + 16);
/*  79:112 */     encodeLELong(this.state3, paramArrayOfByte, paramInt + 24);
/*  80:113 */     encodeLELong(this.state4, paramArrayOfByte, paramInt + 32);
/*  81:114 */     encodeLELong(this.state5, paramArrayOfByte, paramInt + 40);
/*  82:115 */     encodeLELong(this.state6, paramArrayOfByte, paramInt + 48);
/*  83:116 */     encodeLELong(this.state7, paramArrayOfByte, paramInt + 56);
/*  84:    */   }
/*  85:    */   
/*  86:    */   protected void doInit()
/*  87:    */   {
/*  88:122 */     engineReset();
/*  89:    */   }
/*  90:    */   
/*  91:    */   private static final long decodeLELong(byte[] paramArrayOfByte, int paramInt)
/*  92:    */   {
/*  93:134 */     return paramArrayOfByte[(paramInt + 0)] & 0xFF | (paramArrayOfByte[(paramInt + 1)] & 0xFF) << 8 | (paramArrayOfByte[(paramInt + 2)] & 0xFF) << 16 | (paramArrayOfByte[(paramInt + 3)] & 0xFF) << 24 | (paramArrayOfByte[(paramInt + 4)] & 0xFF) << 32 | (paramArrayOfByte[(paramInt + 5)] & 0xFF) << 40 | (paramArrayOfByte[(paramInt + 6)] & 0xFF) << 48 | (paramArrayOfByte[(paramInt + 7)] & 0xFF) << 56;
/*  94:    */   }
/*  95:    */   
/*  96:    */   private static final void encodeLELong(long paramLong, byte[] paramArrayOfByte, int paramInt)
/*  97:    */   {
/*  98:153 */     paramArrayOfByte[(paramInt + 0)] = ((byte)(int)paramLong);
/*  99:154 */     paramArrayOfByte[(paramInt + 1)] = ((byte)((int)paramLong >>> 8));
/* 100:155 */     paramArrayOfByte[(paramInt + 2)] = ((byte)((int)paramLong >>> 16));
/* 101:156 */     paramArrayOfByte[(paramInt + 3)] = ((byte)((int)paramLong >>> 24));
/* 102:157 */     paramArrayOfByte[(paramInt + 4)] = ((byte)(int)(paramLong >>> 32));
/* 103:158 */     paramArrayOfByte[(paramInt + 5)] = ((byte)(int)(paramLong >>> 40));
/* 104:159 */     paramArrayOfByte[(paramInt + 6)] = ((byte)(int)(paramLong >>> 48));
/* 105:160 */     paramArrayOfByte[(paramInt + 7)] = ((byte)(int)(paramLong >>> 56));
/* 106:    */   }
/* 107:    */   
/* 108:    */   protected void processBlock(byte[] paramArrayOfByte)
/* 109:    */   {
/* 110:166 */     long l1 = decodeLELong(paramArrayOfByte, 0);long l2 = l1;
/* 111:167 */     long l3 = decodeLELong(paramArrayOfByte, 8);long l4 = l3;
/* 112:168 */     long l5 = decodeLELong(paramArrayOfByte, 16);long l6 = l5;
/* 113:169 */     long l7 = decodeLELong(paramArrayOfByte, 24);long l8 = l7;
/* 114:170 */     long l9 = decodeLELong(paramArrayOfByte, 32);long l10 = l9;
/* 115:171 */     long l11 = decodeLELong(paramArrayOfByte, 40);long l12 = l11;
/* 116:172 */     long l13 = decodeLELong(paramArrayOfByte, 48);long l14 = l13;
/* 117:173 */     long l15 = decodeLELong(paramArrayOfByte, 56);long l16 = l15;
/* 118:174 */     long l17 = this.state0;long l18 = this.state1;long l19 = this.state2;long l20 = this.state3;
/* 119:175 */     long l21 = this.state4;long l22 = this.state5;long l23 = this.state6;long l24 = this.state7;
/* 120:    */     
/* 121:    */ 
/* 122:178 */     l1 ^= l17;
/* 123:179 */     l3 ^= l18;
/* 124:180 */     l5 ^= l19;
/* 125:181 */     l7 ^= l20;
/* 126:182 */     l9 ^= l21;
/* 127:183 */     l11 ^= l22;
/* 128:184 */     l13 ^= l23;
/* 129:185 */     l15 ^= l24;
/* 130:186 */     for (int i = 0; i < 10; i++)
/* 131:    */     {
/* 132:189 */       long l25 = this.T0[((int)l17 & 0xFF)] ^ this.T1[((int)l24 >> 8 & 0xFF)] ^ this.T2[((int)l23 >> 16 & 0xFF)] ^ this.T3[((int)l22 >> 24 & 0xFF)] ^ this.T4[((int)(l21 >> 32) & 0xFF)] ^ this.T5[((int)(l20 >> 40) & 0xFF)] ^ this.T6[((int)(l19 >> 48) & 0xFF)] ^ this.T7[((int)(l18 >> 56) & 0xFF)] ^ this.RC[i];
/* 133:    */       
/* 134:    */ 
/* 135:    */ 
/* 136:    */ 
/* 137:    */ 
/* 138:    */ 
/* 139:    */ 
/* 140:    */ 
/* 141:198 */       long l26 = this.T0[((int)l18 & 0xFF)] ^ this.T1[((int)l17 >> 8 & 0xFF)] ^ this.T2[((int)l24 >> 16 & 0xFF)] ^ this.T3[((int)l23 >> 24 & 0xFF)] ^ this.T4[((int)(l22 >> 32) & 0xFF)] ^ this.T5[((int)(l21 >> 40) & 0xFF)] ^ this.T6[((int)(l20 >> 48) & 0xFF)] ^ this.T7[((int)(l19 >> 56) & 0xFF)];
/* 142:    */       
/* 143:    */ 
/* 144:    */ 
/* 145:    */ 
/* 146:    */ 
/* 147:    */ 
/* 148:    */ 
/* 149:206 */       long l27 = this.T0[((int)l19 & 0xFF)] ^ this.T1[((int)l18 >> 8 & 0xFF)] ^ this.T2[((int)l17 >> 16 & 0xFF)] ^ this.T3[((int)l24 >> 24 & 0xFF)] ^ this.T4[((int)(l23 >> 32) & 0xFF)] ^ this.T5[((int)(l22 >> 40) & 0xFF)] ^ this.T6[((int)(l21 >> 48) & 0xFF)] ^ this.T7[((int)(l20 >> 56) & 0xFF)];
/* 150:    */       
/* 151:    */ 
/* 152:    */ 
/* 153:    */ 
/* 154:    */ 
/* 155:    */ 
/* 156:    */ 
/* 157:214 */       long l28 = this.T0[((int)l20 & 0xFF)] ^ this.T1[((int)l19 >> 8 & 0xFF)] ^ this.T2[((int)l18 >> 16 & 0xFF)] ^ this.T3[((int)l17 >> 24 & 0xFF)] ^ this.T4[((int)(l24 >> 32) & 0xFF)] ^ this.T5[((int)(l23 >> 40) & 0xFF)] ^ this.T6[((int)(l22 >> 48) & 0xFF)] ^ this.T7[((int)(l21 >> 56) & 0xFF)];
/* 158:    */       
/* 159:    */ 
/* 160:    */ 
/* 161:    */ 
/* 162:    */ 
/* 163:    */ 
/* 164:    */ 
/* 165:222 */       long l29 = this.T0[((int)l21 & 0xFF)] ^ this.T1[((int)l20 >> 8 & 0xFF)] ^ this.T2[((int)l19 >> 16 & 0xFF)] ^ this.T3[((int)l18 >> 24 & 0xFF)] ^ this.T4[((int)(l17 >> 32) & 0xFF)] ^ this.T5[((int)(l24 >> 40) & 0xFF)] ^ this.T6[((int)(l23 >> 48) & 0xFF)] ^ this.T7[((int)(l22 >> 56) & 0xFF)];
/* 166:    */       
/* 167:    */ 
/* 168:    */ 
/* 169:    */ 
/* 170:    */ 
/* 171:    */ 
/* 172:    */ 
/* 173:230 */       long l30 = this.T0[((int)l22 & 0xFF)] ^ this.T1[((int)l21 >> 8 & 0xFF)] ^ this.T2[((int)l20 >> 16 & 0xFF)] ^ this.T3[((int)l19 >> 24 & 0xFF)] ^ this.T4[((int)(l18 >> 32) & 0xFF)] ^ this.T5[((int)(l17 >> 40) & 0xFF)] ^ this.T6[((int)(l24 >> 48) & 0xFF)] ^ this.T7[((int)(l23 >> 56) & 0xFF)];
/* 174:    */       
/* 175:    */ 
/* 176:    */ 
/* 177:    */ 
/* 178:    */ 
/* 179:    */ 
/* 180:    */ 
/* 181:238 */       long l31 = this.T0[((int)l23 & 0xFF)] ^ this.T1[((int)l22 >> 8 & 0xFF)] ^ this.T2[((int)l21 >> 16 & 0xFF)] ^ this.T3[((int)l20 >> 24 & 0xFF)] ^ this.T4[((int)(l19 >> 32) & 0xFF)] ^ this.T5[((int)(l18 >> 40) & 0xFF)] ^ this.T6[((int)(l17 >> 48) & 0xFF)] ^ this.T7[((int)(l24 >> 56) & 0xFF)];
/* 182:    */       
/* 183:    */ 
/* 184:    */ 
/* 185:    */ 
/* 186:    */ 
/* 187:    */ 
/* 188:    */ 
/* 189:246 */       long l32 = this.T0[((int)l24 & 0xFF)] ^ this.T1[((int)l23 >> 8 & 0xFF)] ^ this.T2[((int)l22 >> 16 & 0xFF)] ^ this.T3[((int)l21 >> 24 & 0xFF)] ^ this.T4[((int)(l20 >> 32) & 0xFF)] ^ this.T5[((int)(l19 >> 40) & 0xFF)] ^ this.T6[((int)(l18 >> 48) & 0xFF)] ^ this.T7[((int)(l17 >> 56) & 0xFF)];
/* 190:    */       
/* 191:    */ 
/* 192:    */ 
/* 193:    */ 
/* 194:    */ 
/* 195:    */ 
/* 196:    */ 
/* 197:254 */       l17 = l25;
/* 198:255 */       l18 = l26;
/* 199:256 */       l19 = l27;
/* 200:257 */       l20 = l28;
/* 201:258 */       l21 = l29;
/* 202:259 */       l22 = l30;
/* 203:260 */       l23 = l31;
/* 204:261 */       l24 = l32;
/* 205:262 */       l25 = this.T0[((int)l1 & 0xFF)] ^ this.T1[((int)l15 >> 8 & 0xFF)] ^ this.T2[((int)l13 >> 16 & 0xFF)] ^ this.T3[((int)l11 >> 24 & 0xFF)] ^ this.T4[((int)(l9 >> 32) & 0xFF)] ^ this.T5[((int)(l7 >> 40) & 0xFF)] ^ this.T6[((int)(l5 >> 48) & 0xFF)] ^ this.T7[((int)(l3 >> 56) & 0xFF)] ^ l17;
/* 206:    */       
/* 207:    */ 
/* 208:    */ 
/* 209:    */ 
/* 210:    */ 
/* 211:    */ 
/* 212:    */ 
/* 213:    */ 
/* 214:271 */       l26 = this.T0[((int)l3 & 0xFF)] ^ this.T1[((int)l1 >> 8 & 0xFF)] ^ this.T2[((int)l15 >> 16 & 0xFF)] ^ this.T3[((int)l13 >> 24 & 0xFF)] ^ this.T4[((int)(l11 >> 32) & 0xFF)] ^ this.T5[((int)(l9 >> 40) & 0xFF)] ^ this.T6[((int)(l7 >> 48) & 0xFF)] ^ this.T7[((int)(l5 >> 56) & 0xFF)] ^ l18;
/* 215:    */       
/* 216:    */ 
/* 217:    */ 
/* 218:    */ 
/* 219:    */ 
/* 220:    */ 
/* 221:    */ 
/* 222:    */ 
/* 223:280 */       l27 = this.T0[((int)l5 & 0xFF)] ^ this.T1[((int)l3 >> 8 & 0xFF)] ^ this.T2[((int)l1 >> 16 & 0xFF)] ^ this.T3[((int)l15 >> 24 & 0xFF)] ^ this.T4[((int)(l13 >> 32) & 0xFF)] ^ this.T5[((int)(l11 >> 40) & 0xFF)] ^ this.T6[((int)(l9 >> 48) & 0xFF)] ^ this.T7[((int)(l7 >> 56) & 0xFF)] ^ l19;
/* 224:    */       
/* 225:    */ 
/* 226:    */ 
/* 227:    */ 
/* 228:    */ 
/* 229:    */ 
/* 230:    */ 
/* 231:    */ 
/* 232:289 */       l28 = this.T0[((int)l7 & 0xFF)] ^ this.T1[((int)l5 >> 8 & 0xFF)] ^ this.T2[((int)l3 >> 16 & 0xFF)] ^ this.T3[((int)l1 >> 24 & 0xFF)] ^ this.T4[((int)(l15 >> 32) & 0xFF)] ^ this.T5[((int)(l13 >> 40) & 0xFF)] ^ this.T6[((int)(l11 >> 48) & 0xFF)] ^ this.T7[((int)(l9 >> 56) & 0xFF)] ^ l20;
/* 233:    */       
/* 234:    */ 
/* 235:    */ 
/* 236:    */ 
/* 237:    */ 
/* 238:    */ 
/* 239:    */ 
/* 240:    */ 
/* 241:298 */       l29 = this.T0[((int)l9 & 0xFF)] ^ this.T1[((int)l7 >> 8 & 0xFF)] ^ this.T2[((int)l5 >> 16 & 0xFF)] ^ this.T3[((int)l3 >> 24 & 0xFF)] ^ this.T4[((int)(l1 >> 32) & 0xFF)] ^ this.T5[((int)(l15 >> 40) & 0xFF)] ^ this.T6[((int)(l13 >> 48) & 0xFF)] ^ this.T7[((int)(l11 >> 56) & 0xFF)] ^ l21;
/* 242:    */       
/* 243:    */ 
/* 244:    */ 
/* 245:    */ 
/* 246:    */ 
/* 247:    */ 
/* 248:    */ 
/* 249:    */ 
/* 250:307 */       l30 = this.T0[((int)l11 & 0xFF)] ^ this.T1[((int)l9 >> 8 & 0xFF)] ^ this.T2[((int)l7 >> 16 & 0xFF)] ^ this.T3[((int)l5 >> 24 & 0xFF)] ^ this.T4[((int)(l3 >> 32) & 0xFF)] ^ this.T5[((int)(l1 >> 40) & 0xFF)] ^ this.T6[((int)(l15 >> 48) & 0xFF)] ^ this.T7[((int)(l13 >> 56) & 0xFF)] ^ l22;
/* 251:    */       
/* 252:    */ 
/* 253:    */ 
/* 254:    */ 
/* 255:    */ 
/* 256:    */ 
/* 257:    */ 
/* 258:    */ 
/* 259:316 */       l31 = this.T0[((int)l13 & 0xFF)] ^ this.T1[((int)l11 >> 8 & 0xFF)] ^ this.T2[((int)l9 >> 16 & 0xFF)] ^ this.T3[((int)l7 >> 24 & 0xFF)] ^ this.T4[((int)(l5 >> 32) & 0xFF)] ^ this.T5[((int)(l3 >> 40) & 0xFF)] ^ this.T6[((int)(l1 >> 48) & 0xFF)] ^ this.T7[((int)(l15 >> 56) & 0xFF)] ^ l23;
/* 260:    */       
/* 261:    */ 
/* 262:    */ 
/* 263:    */ 
/* 264:    */ 
/* 265:    */ 
/* 266:    */ 
/* 267:    */ 
/* 268:325 */       l32 = this.T0[((int)l15 & 0xFF)] ^ this.T1[((int)l13 >> 8 & 0xFF)] ^ this.T2[((int)l11 >> 16 & 0xFF)] ^ this.T3[((int)l9 >> 24 & 0xFF)] ^ this.T4[((int)(l7 >> 32) & 0xFF)] ^ this.T5[((int)(l5 >> 40) & 0xFF)] ^ this.T6[((int)(l3 >> 48) & 0xFF)] ^ this.T7[((int)(l1 >> 56) & 0xFF)] ^ l24;
/* 269:    */       
/* 270:    */ 
/* 271:    */ 
/* 272:    */ 
/* 273:    */ 
/* 274:    */ 
/* 275:    */ 
/* 276:    */ 
/* 277:334 */       l1 = l25;
/* 278:335 */       l3 = l26;
/* 279:336 */       l5 = l27;
/* 280:337 */       l7 = l28;
/* 281:338 */       l9 = l29;
/* 282:339 */       l11 = l30;
/* 283:340 */       l13 = l31;
/* 284:341 */       l15 = l32;
/* 285:    */     }
/* 286:343 */     this.state0 ^= l1 ^ l2;
/* 287:344 */     this.state1 ^= l3 ^ l4;
/* 288:345 */     this.state2 ^= l5 ^ l6;
/* 289:346 */     this.state3 ^= l7 ^ l8;
/* 290:347 */     this.state4 ^= l9 ^ l10;
/* 291:348 */     this.state5 ^= l11 ^ l12;
/* 292:349 */     this.state6 ^= l13 ^ l14;
/* 293:350 */     this.state7 ^= l15 ^ l16;
/* 294:    */   }
/* 295:    */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     fr.cryptohash.WhirlpoolCore
 * JD-Core Version:    0.7.1
 */