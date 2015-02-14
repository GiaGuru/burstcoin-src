/*   1:    */ package fr.cryptohash;
/*   2:    */ 
/*   3:    */ abstract class HAVALCore
/*   4:    */   extends DigestEngine
/*   5:    */ {
/*   6:    */   private int olen;
/*   7:    */   private int passes;
/*   8:    */   private byte[] padBuf;
/*   9:    */   private int s0;
/*  10:    */   private int s1;
/*  11:    */   private int s2;
/*  12:    */   private int s3;
/*  13:    */   private int s4;
/*  14:    */   private int s5;
/*  15:    */   private int s6;
/*  16:    */   private int s7;
/*  17:    */   private int[] inw;
/*  18:    */   
/*  19:    */   HAVALCore(int paramInt1, int paramInt2)
/*  20:    */   {
/*  21: 50 */     this.olen = (paramInt1 >> 5);
/*  22: 51 */     this.passes = paramInt2;
/*  23:    */   }
/*  24:    */   
/*  25:    */   protected Digest copyState(HAVALCore paramHAVALCore)
/*  26:    */   {
/*  27: 82 */     paramHAVALCore.olen = this.olen;
/*  28: 83 */     paramHAVALCore.passes = this.passes;
/*  29: 84 */     paramHAVALCore.s0 = this.s0;
/*  30: 85 */     paramHAVALCore.s1 = this.s1;
/*  31: 86 */     paramHAVALCore.s2 = this.s2;
/*  32: 87 */     paramHAVALCore.s3 = this.s3;
/*  33: 88 */     paramHAVALCore.s4 = this.s4;
/*  34: 89 */     paramHAVALCore.s5 = this.s5;
/*  35: 90 */     paramHAVALCore.s6 = this.s6;
/*  36: 91 */     paramHAVALCore.s7 = this.s7;
/*  37: 92 */     return super.copyState(paramHAVALCore);
/*  38:    */   }
/*  39:    */   
/*  40:    */   public int getBlockLength()
/*  41:    */   {
/*  42: 98 */     return 128;
/*  43:    */   }
/*  44:    */   
/*  45:    */   protected void engineReset()
/*  46:    */   {
/*  47:104 */     this.s0 = 608135816;
/*  48:105 */     this.s1 = -2052912941;
/*  49:106 */     this.s2 = 320440878;
/*  50:107 */     this.s3 = 57701188;
/*  51:108 */     this.s4 = -1542899678;
/*  52:109 */     this.s5 = 698298832;
/*  53:110 */     this.s6 = 137296536;
/*  54:111 */     this.s7 = -330404727;
/*  55:    */   }
/*  56:    */   
/*  57:    */   protected void doPadding(byte[] paramArrayOfByte, int paramInt)
/*  58:    */   {
/*  59:117 */     int i = flush();
/*  60:118 */     long l = (getBlockCount() << 7) + i << 3;
/*  61:    */     
/*  62:120 */     this.padBuf[0] = ((byte)(0x1 | this.passes << 3));
/*  63:121 */     this.padBuf[1] = ((byte)(this.olen << 3));
/*  64:122 */     encodeLEInt((int)l, this.padBuf, 2);
/*  65:123 */     encodeLEInt((int)(l >>> 32), this.padBuf, 6);
/*  66:124 */     int j = i + 138 & 0xFFFFFF80;
/*  67:125 */     update((byte)1);
/*  68:126 */     for (int k = i + 1; k < j - 10; k++) {
/*  69:127 */       update((byte)0);
/*  70:    */     }
/*  71:128 */     update(this.padBuf);
/*  72:    */     
/*  73:    */ 
/*  74:    */ 
/*  75:    */ 
/*  76:    */ 
/*  77:    */ 
/*  78:    */ 
/*  79:    */ 
/*  80:    */ 
/*  81:138 */     writeOutput(paramArrayOfByte, paramInt);
/*  82:    */   }
/*  83:    */   
/*  84:    */   protected void doInit()
/*  85:    */   {
/*  86:144 */     this.padBuf = new byte[10];
/*  87:145 */     this.inw = new int[32];
/*  88:146 */     engineReset();
/*  89:    */   }
/*  90:    */   
/*  91:149 */   private static final int[] K2 = { 1160258022, 953160567, -1101764913, 887688300, -1062458953, -914599715, 1065670069, -1253635817, -1843997223, -1988494565, -785314906, -1730169428, 805139163, -803545161, -1193168915, 1780907670, -1166241723, -248741991, 614570311, -1282315017, 134345442, -2054226922, 1667834072, 1901547113, -1537671517, -191677058, 227898511, 1921955416, 1904987480, -2112533778, 2069144605, -1034266187 };
/*  92:160 */   private static final int[] K3 = { -1674521287, 720527379, -976113629, 677414384, -901678824, -1193592593, -1904616272, 1614419982, 1822297739, -1340175810, -686458943, -1120842969, 2024746970, 1432378464, -430627341, -1437226092, 1464375394, 1676153920, 1439316330, 715854006, -1261675468, 289532110, -1588296017, 2087905683, -1276242927, 1668267050, 732546397, 1947742710, -832815594, -1685613794, -1344882125, 1814351708 };
/*  93:171 */   private static final int[] K4 = { 2050118529, 680887927, 999245976, 1800124847, -994056165, 1713906067, 1641548236, -81679983, 1216130144, 1575780402, -276538019, -377129551, -601480446, -345695352, 596196993, -745100091, 258830323, -2081144263, 772490370, -1534844924, 1774776394, -1642095778, 566650946, -152474470, 1728879713, -1412200208, 1783734482, -665571480, -1777359064, -1420741725, 1861159788, 326777828 };
/*  94:182 */   private static final int[] K5 = { -1170476976, 2130389656, -1578015459, 967770486, 1724537150, -2109534584, -1930525159, 1164943284, 2105845187, 998989502, -529566248, -2050940813, 1075463327, 1455516326, 1322494562, 910128902, 469688178, 1117454909, 936433444, -804646328, -619713837, 1240580251, 122909385, -2137449605, 634681816, -152510729, -469872614, -1233564613, -1754472259, 79693498, -1045868618, 1084186820 };
/*  95:193 */   private static final int[] wp2 = { 5, 14, 26, 18, 11, 28, 7, 16, 0, 23, 20, 22, 1, 10, 4, 8, 30, 3, 21, 9, 17, 24, 29, 6, 19, 12, 15, 13, 2, 25, 31, 27 };
/*  96:198 */   private static final int[] wp3 = { 19, 9, 4, 20, 28, 17, 8, 22, 29, 14, 25, 12, 24, 30, 16, 26, 31, 15, 7, 3, 1, 0, 18, 27, 13, 6, 21, 10, 23, 11, 5, 2 };
/*  97:203 */   private static final int[] wp4 = { 24, 4, 0, 14, 2, 7, 28, 23, 26, 6, 30, 20, 18, 25, 19, 3, 22, 11, 31, 21, 8, 27, 12, 9, 1, 29, 5, 15, 17, 10, 16, 13 };
/*  98:208 */   private static final int[] wp5 = { 27, 3, 21, 26, 17, 11, 20, 29, 19, 0, 12, 7, 13, 8, 31, 10, 5, 9, 14, 30, 18, 6, 28, 24, 2, 23, 16, 22, 4, 1, 25, 15 };
/*  99:    */   
/* 100:    */   private static final void encodeLEInt(int paramInt1, byte[] paramArrayOfByte, int paramInt2)
/* 101:    */   {
/* 102:224 */     paramArrayOfByte[(paramInt2 + 3)] = ((byte)(paramInt1 >> 24 & 0xFF));
/* 103:225 */     paramArrayOfByte[(paramInt2 + 2)] = ((byte)(paramInt1 >> 16 & 0xFF));
/* 104:226 */     paramArrayOfByte[(paramInt2 + 1)] = ((byte)(paramInt1 >> 8 & 0xFF));
/* 105:227 */     paramArrayOfByte[(paramInt2 + 0)] = ((byte)(paramInt1 & 0xFF));
/* 106:    */   }
/* 107:    */   
/* 108:    */   private static final int decodeLEInt(byte[] paramArrayOfByte, int paramInt)
/* 109:    */   {
/* 110:240 */     return paramArrayOfByte[paramInt] & 0xFF | (paramArrayOfByte[(paramInt + 1)] & 0xFF) << 8 | (paramArrayOfByte[(paramInt + 2)] & 0xFF) << 16 | (paramArrayOfByte[(paramInt + 3)] & 0xFF) << 24;
/* 111:    */   }
/* 112:    */   
/* 113:    */   private static final int circularLeft(int paramInt1, int paramInt2)
/* 114:    */   {
/* 115:256 */     return paramInt1 << paramInt2 | paramInt1 >>> 32 - paramInt2;
/* 116:    */   }
/* 117:    */   
/* 118:    */   protected void processBlock(byte[] paramArrayOfByte)
/* 119:    */   {
/* 120:262 */     for (int i = 0; i < 32; i++) {
/* 121:263 */       this.inw[i] = decodeLEInt(paramArrayOfByte, 4 * i);
/* 122:    */     }
/* 123:265 */     i = this.s0;
/* 124:266 */     int j = this.s1;
/* 125:267 */     int k = this.s2;
/* 126:268 */     int m = this.s3;
/* 127:269 */     int n = this.s4;
/* 128:270 */     int i1 = this.s5;
/* 129:271 */     int i2 = this.s6;
/* 130:272 */     int i3 = this.s7;
/* 131:273 */     switch (this.passes)
/* 132:    */     {
/* 133:    */     case 3: 
/* 134:275 */       pass31(this.inw);
/* 135:276 */       pass32(this.inw);
/* 136:277 */       pass33(this.inw);
/* 137:278 */       break;
/* 138:    */     case 4: 
/* 139:280 */       pass41(this.inw);
/* 140:281 */       pass42(this.inw);
/* 141:282 */       pass43(this.inw);
/* 142:283 */       pass44(this.inw);
/* 143:284 */       break;
/* 144:    */     case 5: 
/* 145:286 */       pass51(this.inw);
/* 146:287 */       pass52(this.inw);
/* 147:288 */       pass53(this.inw);
/* 148:289 */       pass54(this.inw);
/* 149:290 */       pass55(this.inw);
/* 150:    */     }
/* 151:293 */     this.s0 += i;
/* 152:294 */     this.s1 += j;
/* 153:295 */     this.s2 += k;
/* 154:296 */     this.s3 += m;
/* 155:297 */     this.s4 += n;
/* 156:298 */     this.s5 += i1;
/* 157:299 */     this.s6 += i2;
/* 158:300 */     this.s7 += i3;
/* 159:    */   }
/* 160:    */   
/* 161:    */   private static final int F1(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7)
/* 162:    */   {
/* 163:306 */     return paramInt6 & paramInt3 ^ paramInt5 & paramInt2 ^ paramInt4 & paramInt1 ^ paramInt7 & paramInt6 ^ paramInt7;
/* 164:    */   }
/* 165:    */   
/* 166:    */   private static final int F2(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7)
/* 167:    */   {
/* 168:312 */     return paramInt5 & (paramInt6 & (paramInt4 ^ 0xFFFFFFFF) ^ paramInt3 & paramInt2 ^ paramInt1 ^ paramInt7) ^ paramInt3 & (paramInt6 ^ paramInt2) ^ paramInt4 & paramInt2 ^ paramInt7;
/* 169:    */   }
/* 170:    */   
/* 171:    */   private static final int F3(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7)
/* 172:    */   {
/* 173:319 */     return paramInt4 & (paramInt6 & paramInt5 ^ paramInt1 ^ paramInt7) ^ paramInt6 & paramInt3 ^ paramInt5 & paramInt2 ^ paramInt7;
/* 174:    */   }
/* 175:    */   
/* 176:    */   private static final int F4(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7)
/* 177:    */   {
/* 178:326 */     return paramInt4 & (paramInt6 & paramInt5 ^ (paramInt3 | paramInt1) ^ paramInt2) ^ paramInt3 & ((paramInt5 ^ 0xFFFFFFFF) & paramInt2 ^ paramInt6 ^ paramInt1 ^ paramInt7) ^ paramInt5 & paramInt1 ^ paramInt7;
/* 179:    */   }
/* 180:    */   
/* 181:    */   private static final int F5(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7)
/* 182:    */   {
/* 183:333 */     return paramInt7 & (paramInt6 & paramInt5 & paramInt4 ^ paramInt2 ^ 0xFFFFFFFF) ^ paramInt6 & paramInt3 ^ paramInt5 & paramInt2 ^ paramInt4 & paramInt1;
/* 184:    */   }
/* 185:    */   
/* 186:    */   private final void pass31(int[] paramArrayOfInt)
/* 187:    */   {
/* 188:339 */     int i = this.s0;int j = this.s1;int k = this.s2;int m = this.s3;
/* 189:340 */     int n = this.s4;int i1 = this.s5;int i2 = this.s6;int i3 = this.s7;
/* 190:341 */     for (int i4 = 0; i4 < 32; i4 += 8)
/* 191:    */     {
/* 192:342 */       i3 = circularLeft(F1(j, i, m, i1, i2, k, n), 25) + circularLeft(i3, 21) + paramArrayOfInt[(i4 + 0)];
/* 193:    */       
/* 194:344 */       i2 = circularLeft(F1(i, i3, k, n, i1, j, m), 25) + circularLeft(i2, 21) + paramArrayOfInt[(i4 + 1)];
/* 195:    */       
/* 196:346 */       i1 = circularLeft(F1(i3, i2, j, m, n, i, k), 25) + circularLeft(i1, 21) + paramArrayOfInt[(i4 + 2)];
/* 197:    */       
/* 198:348 */       n = circularLeft(F1(i2, i1, i, k, m, i3, j), 25) + circularLeft(n, 21) + paramArrayOfInt[(i4 + 3)];
/* 199:    */       
/* 200:350 */       m = circularLeft(F1(i1, n, i3, j, k, i2, i), 25) + circularLeft(m, 21) + paramArrayOfInt[(i4 + 4)];
/* 201:    */       
/* 202:352 */       k = circularLeft(F1(n, m, i2, i, j, i1, i3), 25) + circularLeft(k, 21) + paramArrayOfInt[(i4 + 5)];
/* 203:    */       
/* 204:354 */       j = circularLeft(F1(m, k, i1, i3, i, n, i2), 25) + circularLeft(j, 21) + paramArrayOfInt[(i4 + 6)];
/* 205:    */       
/* 206:356 */       i = circularLeft(F1(k, j, n, i2, i3, m, i1), 25) + circularLeft(i, 21) + paramArrayOfInt[(i4 + 7)];
/* 207:    */     }
/* 208:359 */     this.s0 = i;this.s1 = j;this.s2 = k;this.s3 = m;
/* 209:360 */     this.s4 = n;this.s5 = i1;this.s6 = i2;this.s7 = i3;
/* 210:    */   }
/* 211:    */   
/* 212:    */   private final void pass32(int[] paramArrayOfInt)
/* 213:    */   {
/* 214:365 */     int i = this.s0;int j = this.s1;int k = this.s2;int m = this.s3;
/* 215:366 */     int n = this.s4;int i1 = this.s5;int i2 = this.s6;int i3 = this.s7;
/* 216:367 */     for (int i4 = 0; i4 < 32; i4 += 8)
/* 217:    */     {
/* 218:368 */       i3 = circularLeft(F2(n, k, j, i, i1, m, i2), 25) + circularLeft(i3, 21) + paramArrayOfInt[wp2[(i4 + 0)]] + K2[(i4 + 0)];
/* 219:    */       
/* 220:    */ 
/* 221:371 */       i2 = circularLeft(F2(m, j, i, i3, n, k, i1), 25) + circularLeft(i2, 21) + paramArrayOfInt[wp2[(i4 + 1)]] + K2[(i4 + 1)];
/* 222:    */       
/* 223:    */ 
/* 224:374 */       i1 = circularLeft(F2(k, i, i3, i2, m, j, n), 25) + circularLeft(i1, 21) + paramArrayOfInt[wp2[(i4 + 2)]] + K2[(i4 + 2)];
/* 225:    */       
/* 226:    */ 
/* 227:377 */       n = circularLeft(F2(j, i3, i2, i1, k, i, m), 25) + circularLeft(n, 21) + paramArrayOfInt[wp2[(i4 + 3)]] + K2[(i4 + 3)];
/* 228:    */       
/* 229:    */ 
/* 230:380 */       m = circularLeft(F2(i, i2, i1, n, j, i3, k), 25) + circularLeft(m, 21) + paramArrayOfInt[wp2[(i4 + 4)]] + K2[(i4 + 4)];
/* 231:    */       
/* 232:    */ 
/* 233:383 */       k = circularLeft(F2(i3, i1, n, m, i, i2, j), 25) + circularLeft(k, 21) + paramArrayOfInt[wp2[(i4 + 5)]] + K2[(i4 + 5)];
/* 234:    */       
/* 235:    */ 
/* 236:386 */       j = circularLeft(F2(i2, n, m, k, i3, i1, i), 25) + circularLeft(j, 21) + paramArrayOfInt[wp2[(i4 + 6)]] + K2[(i4 + 6)];
/* 237:    */       
/* 238:    */ 
/* 239:389 */       i = circularLeft(F2(i1, m, k, j, i2, n, i3), 25) + circularLeft(i, 21) + paramArrayOfInt[wp2[(i4 + 7)]] + K2[(i4 + 7)];
/* 240:    */     }
/* 241:393 */     this.s0 = i;this.s1 = j;this.s2 = k;this.s3 = m;
/* 242:394 */     this.s4 = n;this.s5 = i1;this.s6 = i2;this.s7 = i3;
/* 243:    */   }
/* 244:    */   
/* 245:    */   private final void pass33(int[] paramArrayOfInt)
/* 246:    */   {
/* 247:399 */     int i = this.s0;int j = this.s1;int k = this.s2;int m = this.s3;
/* 248:400 */     int n = this.s4;int i1 = this.s5;int i2 = this.s6;int i3 = this.s7;
/* 249:401 */     for (int i4 = 0; i4 < 32; i4 += 8)
/* 250:    */     {
/* 251:402 */       i3 = circularLeft(F3(i2, j, k, m, n, i1, i), 25) + circularLeft(i3, 21) + paramArrayOfInt[wp3[(i4 + 0)]] + K3[(i4 + 0)];
/* 252:    */       
/* 253:    */ 
/* 254:405 */       i2 = circularLeft(F3(i1, i, j, k, m, n, i3), 25) + circularLeft(i2, 21) + paramArrayOfInt[wp3[(i4 + 1)]] + K3[(i4 + 1)];
/* 255:    */       
/* 256:    */ 
/* 257:408 */       i1 = circularLeft(F3(n, i3, i, j, k, m, i2), 25) + circularLeft(i1, 21) + paramArrayOfInt[wp3[(i4 + 2)]] + K3[(i4 + 2)];
/* 258:    */       
/* 259:    */ 
/* 260:411 */       n = circularLeft(F3(m, i2, i3, i, j, k, i1), 25) + circularLeft(n, 21) + paramArrayOfInt[wp3[(i4 + 3)]] + K3[(i4 + 3)];
/* 261:    */       
/* 262:    */ 
/* 263:414 */       m = circularLeft(F3(k, i1, i2, i3, i, j, n), 25) + circularLeft(m, 21) + paramArrayOfInt[wp3[(i4 + 4)]] + K3[(i4 + 4)];
/* 264:    */       
/* 265:    */ 
/* 266:417 */       k = circularLeft(F3(j, n, i1, i2, i3, i, m), 25) + circularLeft(k, 21) + paramArrayOfInt[wp3[(i4 + 5)]] + K3[(i4 + 5)];
/* 267:    */       
/* 268:    */ 
/* 269:420 */       j = circularLeft(F3(i, m, n, i1, i2, i3, k), 25) + circularLeft(j, 21) + paramArrayOfInt[wp3[(i4 + 6)]] + K3[(i4 + 6)];
/* 270:    */       
/* 271:    */ 
/* 272:423 */       i = circularLeft(F3(i3, k, m, n, i1, i2, j), 25) + circularLeft(i, 21) + paramArrayOfInt[wp3[(i4 + 7)]] + K3[(i4 + 7)];
/* 273:    */     }
/* 274:427 */     this.s0 = i;this.s1 = j;this.s2 = k;this.s3 = m;
/* 275:428 */     this.s4 = n;this.s5 = i1;this.s6 = i2;this.s7 = i3;
/* 276:    */   }
/* 277:    */   
/* 278:    */   private final void pass41(int[] paramArrayOfInt)
/* 279:    */   {
/* 280:433 */     int i = this.s0;int j = this.s1;int k = this.s2;int m = this.s3;
/* 281:434 */     int n = this.s4;int i1 = this.s5;int i2 = this.s6;int i3 = this.s7;
/* 282:435 */     for (int i4 = 0; i4 < 32; i4 += 8)
/* 283:    */     {
/* 284:436 */       i3 = circularLeft(F1(k, i2, j, n, i1, m, i), 25) + circularLeft(i3, 21) + paramArrayOfInt[(i4 + 0)];
/* 285:    */       
/* 286:438 */       i2 = circularLeft(F1(j, i1, i, m, n, k, i3), 25) + circularLeft(i2, 21) + paramArrayOfInt[(i4 + 1)];
/* 287:    */       
/* 288:440 */       i1 = circularLeft(F1(i, n, i3, k, m, j, i2), 25) + circularLeft(i1, 21) + paramArrayOfInt[(i4 + 2)];
/* 289:    */       
/* 290:442 */       n = circularLeft(F1(i3, m, i2, j, k, i, i1), 25) + circularLeft(n, 21) + paramArrayOfInt[(i4 + 3)];
/* 291:    */       
/* 292:444 */       m = circularLeft(F1(i2, k, i1, i, j, i3, n), 25) + circularLeft(m, 21) + paramArrayOfInt[(i4 + 4)];
/* 293:    */       
/* 294:446 */       k = circularLeft(F1(i1, j, n, i3, i, i2, m), 25) + circularLeft(k, 21) + paramArrayOfInt[(i4 + 5)];
/* 295:    */       
/* 296:448 */       j = circularLeft(F1(n, i, m, i2, i3, i1, k), 25) + circularLeft(j, 21) + paramArrayOfInt[(i4 + 6)];
/* 297:    */       
/* 298:450 */       i = circularLeft(F1(m, i3, k, i1, i2, n, j), 25) + circularLeft(i, 21) + paramArrayOfInt[(i4 + 7)];
/* 299:    */     }
/* 300:453 */     this.s0 = i;this.s1 = j;this.s2 = k;this.s3 = m;
/* 301:454 */     this.s4 = n;this.s5 = i1;this.s6 = i2;this.s7 = i3;
/* 302:    */   }
/* 303:    */   
/* 304:    */   private final void pass42(int[] paramArrayOfInt)
/* 305:    */   {
/* 306:459 */     int i = this.s0;int j = this.s1;int k = this.s2;int m = this.s3;
/* 307:460 */     int n = this.s4;int i1 = this.s5;int i2 = this.s6;int i3 = this.s7;
/* 308:461 */     for (int i4 = 0; i4 < 32; i4 += 8)
/* 309:    */     {
/* 310:462 */       i3 = circularLeft(F2(m, i1, k, i, j, i2, n), 25) + circularLeft(i3, 21) + paramArrayOfInt[wp2[(i4 + 0)]] + K2[(i4 + 0)];
/* 311:    */       
/* 312:    */ 
/* 313:465 */       i2 = circularLeft(F2(k, n, j, i3, i, i1, m), 25) + circularLeft(i2, 21) + paramArrayOfInt[wp2[(i4 + 1)]] + K2[(i4 + 1)];
/* 314:    */       
/* 315:    */ 
/* 316:468 */       i1 = circularLeft(F2(j, m, i, i2, i3, n, k), 25) + circularLeft(i1, 21) + paramArrayOfInt[wp2[(i4 + 2)]] + K2[(i4 + 2)];
/* 317:    */       
/* 318:    */ 
/* 319:471 */       n = circularLeft(F2(i, k, i3, i1, i2, m, j), 25) + circularLeft(n, 21) + paramArrayOfInt[wp2[(i4 + 3)]] + K2[(i4 + 3)];
/* 320:    */       
/* 321:    */ 
/* 322:474 */       m = circularLeft(F2(i3, j, i2, n, i1, k, i), 25) + circularLeft(m, 21) + paramArrayOfInt[wp2[(i4 + 4)]] + K2[(i4 + 4)];
/* 323:    */       
/* 324:    */ 
/* 325:477 */       k = circularLeft(F2(i2, i, i1, m, n, j, i3), 25) + circularLeft(k, 21) + paramArrayOfInt[wp2[(i4 + 5)]] + K2[(i4 + 5)];
/* 326:    */       
/* 327:    */ 
/* 328:480 */       j = circularLeft(F2(i1, i3, n, k, m, i, i2), 25) + circularLeft(j, 21) + paramArrayOfInt[wp2[(i4 + 6)]] + K2[(i4 + 6)];
/* 329:    */       
/* 330:    */ 
/* 331:483 */       i = circularLeft(F2(n, i2, m, j, k, i3, i1), 25) + circularLeft(i, 21) + paramArrayOfInt[wp2[(i4 + 7)]] + K2[(i4 + 7)];
/* 332:    */     }
/* 333:487 */     this.s0 = i;this.s1 = j;this.s2 = k;this.s3 = m;
/* 334:488 */     this.s4 = n;this.s5 = i1;this.s6 = i2;this.s7 = i3;
/* 335:    */   }
/* 336:    */   
/* 337:    */   private final void pass43(int[] paramArrayOfInt)
/* 338:    */   {
/* 339:493 */     int i = this.s0;int j = this.s1;int k = this.s2;int m = this.s3;
/* 340:494 */     int n = this.s4;int i1 = this.s5;int i2 = this.s6;int i3 = this.s7;
/* 341:495 */     for (int i4 = 0; i4 < 32; i4 += 8)
/* 342:    */     {
/* 343:496 */       i3 = circularLeft(F3(j, n, m, i2, i, k, i1), 25) + circularLeft(i3, 21) + paramArrayOfInt[wp3[(i4 + 0)]] + K3[(i4 + 0)];
/* 344:    */       
/* 345:    */ 
/* 346:499 */       i2 = circularLeft(F3(i, m, k, i1, i3, j, n), 25) + circularLeft(i2, 21) + paramArrayOfInt[wp3[(i4 + 1)]] + K3[(i4 + 1)];
/* 347:    */       
/* 348:    */ 
/* 349:502 */       i1 = circularLeft(F3(i3, k, j, n, i2, i, m), 25) + circularLeft(i1, 21) + paramArrayOfInt[wp3[(i4 + 2)]] + K3[(i4 + 2)];
/* 350:    */       
/* 351:    */ 
/* 352:505 */       n = circularLeft(F3(i2, j, i, m, i1, i3, k), 25) + circularLeft(n, 21) + paramArrayOfInt[wp3[(i4 + 3)]] + K3[(i4 + 3)];
/* 353:    */       
/* 354:    */ 
/* 355:508 */       m = circularLeft(F3(i1, i, i3, k, n, i2, j), 25) + circularLeft(m, 21) + paramArrayOfInt[wp3[(i4 + 4)]] + K3[(i4 + 4)];
/* 356:    */       
/* 357:    */ 
/* 358:511 */       k = circularLeft(F3(n, i3, i2, j, m, i1, i), 25) + circularLeft(k, 21) + paramArrayOfInt[wp3[(i4 + 5)]] + K3[(i4 + 5)];
/* 359:    */       
/* 360:    */ 
/* 361:514 */       j = circularLeft(F3(m, i2, i1, i, k, n, i3), 25) + circularLeft(j, 21) + paramArrayOfInt[wp3[(i4 + 6)]] + K3[(i4 + 6)];
/* 362:    */       
/* 363:    */ 
/* 364:517 */       i = circularLeft(F3(k, i1, n, i3, j, m, i2), 25) + circularLeft(i, 21) + paramArrayOfInt[wp3[(i4 + 7)]] + K3[(i4 + 7)];
/* 365:    */     }
/* 366:521 */     this.s0 = i;this.s1 = j;this.s2 = k;this.s3 = m;
/* 367:522 */     this.s4 = n;this.s5 = i1;this.s6 = i2;this.s7 = i3;
/* 368:    */   }
/* 369:    */   
/* 370:    */   private final void pass44(int[] paramArrayOfInt)
/* 371:    */   {
/* 372:527 */     int i = this.s0;int j = this.s1;int k = this.s2;int m = this.s3;
/* 373:528 */     int n = this.s4;int i1 = this.s5;int i2 = this.s6;int i3 = this.s7;
/* 374:529 */     for (int i4 = 0; i4 < 32; i4 += 8)
/* 375:    */     {
/* 376:530 */       i3 = circularLeft(F4(i2, n, i, i1, k, j, m), 25) + circularLeft(i3, 21) + paramArrayOfInt[wp4[(i4 + 0)]] + K4[(i4 + 0)];
/* 377:    */       
/* 378:    */ 
/* 379:533 */       i2 = circularLeft(F4(i1, m, i3, n, j, i, k), 25) + circularLeft(i2, 21) + paramArrayOfInt[wp4[(i4 + 1)]] + K4[(i4 + 1)];
/* 380:    */       
/* 381:    */ 
/* 382:536 */       i1 = circularLeft(F4(n, k, i2, m, i, i3, j), 25) + circularLeft(i1, 21) + paramArrayOfInt[wp4[(i4 + 2)]] + K4[(i4 + 2)];
/* 383:    */       
/* 384:    */ 
/* 385:539 */       n = circularLeft(F4(m, j, i1, k, i3, i2, i), 25) + circularLeft(n, 21) + paramArrayOfInt[wp4[(i4 + 3)]] + K4[(i4 + 3)];
/* 386:    */       
/* 387:    */ 
/* 388:542 */       m = circularLeft(F4(k, i, n, j, i2, i1, i3), 25) + circularLeft(m, 21) + paramArrayOfInt[wp4[(i4 + 4)]] + K4[(i4 + 4)];
/* 389:    */       
/* 390:    */ 
/* 391:545 */       k = circularLeft(F4(j, i3, m, i, i1, n, i2), 25) + circularLeft(k, 21) + paramArrayOfInt[wp4[(i4 + 5)]] + K4[(i4 + 5)];
/* 392:    */       
/* 393:    */ 
/* 394:548 */       j = circularLeft(F4(i, i2, k, i3, n, m, i1), 25) + circularLeft(j, 21) + paramArrayOfInt[wp4[(i4 + 6)]] + K4[(i4 + 6)];
/* 395:    */       
/* 396:    */ 
/* 397:551 */       i = circularLeft(F4(i3, i1, j, i2, m, k, n), 25) + circularLeft(i, 21) + paramArrayOfInt[wp4[(i4 + 7)]] + K4[(i4 + 7)];
/* 398:    */     }
/* 399:555 */     this.s0 = i;this.s1 = j;this.s2 = k;this.s3 = m;
/* 400:556 */     this.s4 = n;this.s5 = i1;this.s6 = i2;this.s7 = i3;
/* 401:    */   }
/* 402:    */   
/* 403:    */   private final void pass51(int[] paramArrayOfInt)
/* 404:    */   {
/* 405:561 */     int i = this.s0;int j = this.s1;int k = this.s2;int m = this.s3;
/* 406:562 */     int n = this.s4;int i1 = this.s5;int i2 = this.s6;int i3 = this.s7;
/* 407:563 */     for (int i4 = 0; i4 < 32; i4 += 8)
/* 408:    */     {
/* 409:564 */       i3 = circularLeft(F1(m, n, j, i, i1, k, i2), 25) + circularLeft(i3, 21) + paramArrayOfInt[(i4 + 0)];
/* 410:    */       
/* 411:566 */       i2 = circularLeft(F1(k, m, i, i3, n, j, i1), 25) + circularLeft(i2, 21) + paramArrayOfInt[(i4 + 1)];
/* 412:    */       
/* 413:568 */       i1 = circularLeft(F1(j, k, i3, i2, m, i, n), 25) + circularLeft(i1, 21) + paramArrayOfInt[(i4 + 2)];
/* 414:    */       
/* 415:570 */       n = circularLeft(F1(i, j, i2, i1, k, i3, m), 25) + circularLeft(n, 21) + paramArrayOfInt[(i4 + 3)];
/* 416:    */       
/* 417:572 */       m = circularLeft(F1(i3, i, i1, n, j, i2, k), 25) + circularLeft(m, 21) + paramArrayOfInt[(i4 + 4)];
/* 418:    */       
/* 419:574 */       k = circularLeft(F1(i2, i3, n, m, i, i1, j), 25) + circularLeft(k, 21) + paramArrayOfInt[(i4 + 5)];
/* 420:    */       
/* 421:576 */       j = circularLeft(F1(i1, i2, m, k, i3, n, i), 25) + circularLeft(j, 21) + paramArrayOfInt[(i4 + 6)];
/* 422:    */       
/* 423:578 */       i = circularLeft(F1(n, i1, k, j, i2, m, i3), 25) + circularLeft(i, 21) + paramArrayOfInt[(i4 + 7)];
/* 424:    */     }
/* 425:581 */     this.s0 = i;this.s1 = j;this.s2 = k;this.s3 = m;
/* 426:582 */     this.s4 = n;this.s5 = i1;this.s6 = i2;this.s7 = i3;
/* 427:    */   }
/* 428:    */   
/* 429:    */   private final void pass52(int[] paramArrayOfInt)
/* 430:    */   {
/* 431:587 */     int i = this.s0;int j = this.s1;int k = this.s2;int m = this.s3;
/* 432:588 */     int n = this.s4;int i1 = this.s5;int i2 = this.s6;int i3 = this.s7;
/* 433:589 */     for (int i4 = 0; i4 < 32; i4 += 8)
/* 434:    */     {
/* 435:590 */       i3 = circularLeft(F2(i2, k, j, i, m, n, i1), 25) + circularLeft(i3, 21) + paramArrayOfInt[wp2[(i4 + 0)]] + K2[(i4 + 0)];
/* 436:    */       
/* 437:    */ 
/* 438:593 */       i2 = circularLeft(F2(i1, j, i, i3, k, m, n), 25) + circularLeft(i2, 21) + paramArrayOfInt[wp2[(i4 + 1)]] + K2[(i4 + 1)];
/* 439:    */       
/* 440:    */ 
/* 441:596 */       i1 = circularLeft(F2(n, i, i3, i2, j, k, m), 25) + circularLeft(i1, 21) + paramArrayOfInt[wp2[(i4 + 2)]] + K2[(i4 + 2)];
/* 442:    */       
/* 443:    */ 
/* 444:599 */       n = circularLeft(F2(m, i3, i2, i1, i, j, k), 25) + circularLeft(n, 21) + paramArrayOfInt[wp2[(i4 + 3)]] + K2[(i4 + 3)];
/* 445:    */       
/* 446:    */ 
/* 447:602 */       m = circularLeft(F2(k, i2, i1, n, i3, i, j), 25) + circularLeft(m, 21) + paramArrayOfInt[wp2[(i4 + 4)]] + K2[(i4 + 4)];
/* 448:    */       
/* 449:    */ 
/* 450:605 */       k = circularLeft(F2(j, i1, n, m, i2, i3, i), 25) + circularLeft(k, 21) + paramArrayOfInt[wp2[(i4 + 5)]] + K2[(i4 + 5)];
/* 451:    */       
/* 452:    */ 
/* 453:608 */       j = circularLeft(F2(i, n, m, k, i1, i2, i3), 25) + circularLeft(j, 21) + paramArrayOfInt[wp2[(i4 + 6)]] + K2[(i4 + 6)];
/* 454:    */       
/* 455:    */ 
/* 456:611 */       i = circularLeft(F2(i3, m, k, j, n, i1, i2), 25) + circularLeft(i, 21) + paramArrayOfInt[wp2[(i4 + 7)]] + K2[(i4 + 7)];
/* 457:    */     }
/* 458:615 */     this.s0 = i;this.s1 = j;this.s2 = k;this.s3 = m;
/* 459:616 */     this.s4 = n;this.s5 = i1;this.s6 = i2;this.s7 = i3;
/* 460:    */   }
/* 461:    */   
/* 462:    */   private final void pass53(int[] paramArrayOfInt)
/* 463:    */   {
/* 464:621 */     int i = this.s0;int j = this.s1;int k = this.s2;int m = this.s3;
/* 465:622 */     int n = this.s4;int i1 = this.s5;int i2 = this.s6;int i3 = this.s7;
/* 466:623 */     for (int i4 = 0; i4 < 32; i4 += 8)
/* 467:    */     {
/* 468:624 */       i3 = circularLeft(F3(k, i2, i, n, m, j, i1), 25) + circularLeft(i3, 21) + paramArrayOfInt[wp3[(i4 + 0)]] + K3[(i4 + 0)];
/* 469:    */       
/* 470:    */ 
/* 471:627 */       i2 = circularLeft(F3(j, i1, i3, m, k, i, n), 25) + circularLeft(i2, 21) + paramArrayOfInt[wp3[(i4 + 1)]] + K3[(i4 + 1)];
/* 472:    */       
/* 473:    */ 
/* 474:630 */       i1 = circularLeft(F3(i, n, i2, k, j, i3, m), 25) + circularLeft(i1, 21) + paramArrayOfInt[wp3[(i4 + 2)]] + K3[(i4 + 2)];
/* 475:    */       
/* 476:    */ 
/* 477:633 */       n = circularLeft(F3(i3, m, i1, j, i, i2, k), 25) + circularLeft(n, 21) + paramArrayOfInt[wp3[(i4 + 3)]] + K3[(i4 + 3)];
/* 478:    */       
/* 479:    */ 
/* 480:636 */       m = circularLeft(F3(i2, k, n, i, i3, i1, j), 25) + circularLeft(m, 21) + paramArrayOfInt[wp3[(i4 + 4)]] + K3[(i4 + 4)];
/* 481:    */       
/* 482:    */ 
/* 483:639 */       k = circularLeft(F3(i1, j, m, i3, i2, n, i), 25) + circularLeft(k, 21) + paramArrayOfInt[wp3[(i4 + 5)]] + K3[(i4 + 5)];
/* 484:    */       
/* 485:    */ 
/* 486:642 */       j = circularLeft(F3(n, i, k, i2, i1, m, i3), 25) + circularLeft(j, 21) + paramArrayOfInt[wp3[(i4 + 6)]] + K3[(i4 + 6)];
/* 487:    */       
/* 488:    */ 
/* 489:645 */       i = circularLeft(F3(m, i3, j, i1, n, k, i2), 25) + circularLeft(i, 21) + paramArrayOfInt[wp3[(i4 + 7)]] + K3[(i4 + 7)];
/* 490:    */     }
/* 491:649 */     this.s0 = i;this.s1 = j;this.s2 = k;this.s3 = m;
/* 492:650 */     this.s4 = n;this.s5 = i1;this.s6 = i2;this.s7 = i3;
/* 493:    */   }
/* 494:    */   
/* 495:    */   private final void pass54(int[] paramArrayOfInt)
/* 496:    */   {
/* 497:655 */     int i = this.s0;int j = this.s1;int k = this.s2;int m = this.s3;
/* 498:656 */     int n = this.s4;int i1 = this.s5;int i2 = this.s6;int i3 = this.s7;
/* 499:657 */     for (int i4 = 0; i4 < 32; i4 += 8)
/* 500:    */     {
/* 501:658 */       i3 = circularLeft(F4(j, i1, m, k, i, n, i2), 25) + circularLeft(i3, 21) + paramArrayOfInt[wp4[(i4 + 0)]] + K4[(i4 + 0)];
/* 502:    */       
/* 503:    */ 
/* 504:661 */       i2 = circularLeft(F4(i, n, k, j, i3, m, i1), 25) + circularLeft(i2, 21) + paramArrayOfInt[wp4[(i4 + 1)]] + K4[(i4 + 1)];
/* 505:    */       
/* 506:    */ 
/* 507:664 */       i1 = circularLeft(F4(i3, m, j, i, i2, k, n), 25) + circularLeft(i1, 21) + paramArrayOfInt[wp4[(i4 + 2)]] + K4[(i4 + 2)];
/* 508:    */       
/* 509:    */ 
/* 510:667 */       n = circularLeft(F4(i2, k, i, i3, i1, j, m), 25) + circularLeft(n, 21) + paramArrayOfInt[wp4[(i4 + 3)]] + K4[(i4 + 3)];
/* 511:    */       
/* 512:    */ 
/* 513:670 */       m = circularLeft(F4(i1, j, i3, i2, n, i, k), 25) + circularLeft(m, 21) + paramArrayOfInt[wp4[(i4 + 4)]] + K4[(i4 + 4)];
/* 514:    */       
/* 515:    */ 
/* 516:673 */       k = circularLeft(F4(n, i, i2, i1, m, i3, j), 25) + circularLeft(k, 21) + paramArrayOfInt[wp4[(i4 + 5)]] + K4[(i4 + 5)];
/* 517:    */       
/* 518:    */ 
/* 519:676 */       j = circularLeft(F4(m, i3, i1, n, k, i2, i), 25) + circularLeft(j, 21) + paramArrayOfInt[wp4[(i4 + 6)]] + K4[(i4 + 6)];
/* 520:    */       
/* 521:    */ 
/* 522:679 */       i = circularLeft(F4(k, i2, n, m, j, i1, i3), 25) + circularLeft(i, 21) + paramArrayOfInt[wp4[(i4 + 7)]] + K4[(i4 + 7)];
/* 523:    */     }
/* 524:683 */     this.s0 = i;this.s1 = j;this.s2 = k;this.s3 = m;
/* 525:684 */     this.s4 = n;this.s5 = i1;this.s6 = i2;this.s7 = i3;
/* 526:    */   }
/* 527:    */   
/* 528:    */   private final void pass55(int[] paramArrayOfInt)
/* 529:    */   {
/* 530:689 */     int i = this.s0;int j = this.s1;int k = this.s2;int m = this.s3;
/* 531:690 */     int n = this.s4;int i1 = this.s5;int i2 = this.s6;int i3 = this.s7;
/* 532:691 */     for (int i4 = 0; i4 < 32; i4 += 8)
/* 533:    */     {
/* 534:692 */       i3 = circularLeft(F5(k, i1, i, i2, n, m, j), 25) + circularLeft(i3, 21) + paramArrayOfInt[wp5[(i4 + 0)]] + K5[(i4 + 0)];
/* 535:    */       
/* 536:    */ 
/* 537:695 */       i2 = circularLeft(F5(j, n, i3, i1, m, k, i), 25) + circularLeft(i2, 21) + paramArrayOfInt[wp5[(i4 + 1)]] + K5[(i4 + 1)];
/* 538:    */       
/* 539:    */ 
/* 540:698 */       i1 = circularLeft(F5(i, m, i2, n, k, j, i3), 25) + circularLeft(i1, 21) + paramArrayOfInt[wp5[(i4 + 2)]] + K5[(i4 + 2)];
/* 541:    */       
/* 542:    */ 
/* 543:701 */       n = circularLeft(F5(i3, k, i1, m, j, i, i2), 25) + circularLeft(n, 21) + paramArrayOfInt[wp5[(i4 + 3)]] + K5[(i4 + 3)];
/* 544:    */       
/* 545:    */ 
/* 546:704 */       m = circularLeft(F5(i2, j, n, k, i, i3, i1), 25) + circularLeft(m, 21) + paramArrayOfInt[wp5[(i4 + 4)]] + K5[(i4 + 4)];
/* 547:    */       
/* 548:    */ 
/* 549:707 */       k = circularLeft(F5(i1, i, m, j, i3, i2, n), 25) + circularLeft(k, 21) + paramArrayOfInt[wp5[(i4 + 5)]] + K5[(i4 + 5)];
/* 550:    */       
/* 551:    */ 
/* 552:710 */       j = circularLeft(F5(n, i3, k, i, i2, i1, m), 25) + circularLeft(j, 21) + paramArrayOfInt[wp5[(i4 + 6)]] + K5[(i4 + 6)];
/* 553:    */       
/* 554:    */ 
/* 555:713 */       i = circularLeft(F5(m, i2, j, i3, i1, n, k), 25) + circularLeft(i, 21) + paramArrayOfInt[wp5[(i4 + 7)]] + K5[(i4 + 7)];
/* 556:    */     }
/* 557:717 */     this.s0 = i;this.s1 = j;this.s2 = k;this.s3 = m;
/* 558:718 */     this.s4 = n;this.s5 = i1;this.s6 = i2;this.s7 = i3;
/* 559:    */   }
/* 560:    */   
/* 561:    */   private static final int mix128(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
/* 562:    */   {
/* 563:723 */     int i = paramInt1 & 0xFF | paramInt2 & 0xFF00 | paramInt3 & 0xFF0000 | paramInt4 & 0xFF000000;
/* 564:727 */     if (paramInt5 > 0) {
/* 565:728 */       i = circularLeft(i, paramInt5);
/* 566:    */     }
/* 567:729 */     return i;
/* 568:    */   }
/* 569:    */   
/* 570:    */   private static final int mix160_0(int paramInt1, int paramInt2, int paramInt3)
/* 571:    */   {
/* 572:734 */     return circularLeft(paramInt1 & 0x1F80000 | paramInt2 & 0xFE000000 | paramInt3 & 0x3F, 13);
/* 573:    */   }
/* 574:    */   
/* 575:    */   private static final int mix160_1(int paramInt1, int paramInt2, int paramInt3)
/* 576:    */   {
/* 577:740 */     return circularLeft(paramInt1 & 0xFE000000 | paramInt2 & 0x3F | paramInt3 & 0xFC0, 7);
/* 578:    */   }
/* 579:    */   
/* 580:    */   private static final int mix160_2(int paramInt1, int paramInt2, int paramInt3)
/* 581:    */   {
/* 582:746 */     return paramInt1 & 0x3F | paramInt2 & 0xFC0 | paramInt3 & 0x7F000;
/* 583:    */   }
/* 584:    */   
/* 585:    */   private static final int mix160_3(int paramInt1, int paramInt2, int paramInt3)
/* 586:    */   {
/* 587:753 */     return (paramInt1 & 0xFC0 | paramInt2 & 0x7F000 | paramInt3 & 0x1F80000) >>> 6;
/* 588:    */   }
/* 589:    */   
/* 590:    */   private static final int mix160_4(int paramInt1, int paramInt2, int paramInt3)
/* 591:    */   {
/* 592:760 */     return (paramInt1 & 0x7F000 | paramInt2 & 0x1F80000 | paramInt3 & 0xFE000000) >>> 12;
/* 593:    */   }
/* 594:    */   
/* 595:    */   private static final int mix192_0(int paramInt1, int paramInt2)
/* 596:    */   {
/* 597:767 */     return circularLeft(paramInt1 & 0xFC000000 | paramInt2 & 0x1F, 6);
/* 598:    */   }
/* 599:    */   
/* 600:    */   private static final int mix192_1(int paramInt1, int paramInt2)
/* 601:    */   {
/* 602:772 */     return paramInt1 & 0x1F | paramInt2 & 0x3E0;
/* 603:    */   }
/* 604:    */   
/* 605:    */   private static final int mix192_2(int paramInt1, int paramInt2)
/* 606:    */   {
/* 607:777 */     return (paramInt1 & 0x3E0 | paramInt2 & 0xFC00) >>> 5;
/* 608:    */   }
/* 609:    */   
/* 610:    */   private static final int mix192_3(int paramInt1, int paramInt2)
/* 611:    */   {
/* 612:782 */     return (paramInt1 & 0xFC00 | paramInt2 & 0x1F0000) >>> 10;
/* 613:    */   }
/* 614:    */   
/* 615:    */   private static final int mix192_4(int paramInt1, int paramInt2)
/* 616:    */   {
/* 617:787 */     return (paramInt1 & 0x1F0000 | paramInt2 & 0x3E00000) >>> 16;
/* 618:    */   }
/* 619:    */   
/* 620:    */   private static final int mix192_5(int paramInt1, int paramInt2)
/* 621:    */   {
/* 622:792 */     return (paramInt1 & 0x3E00000 | paramInt2 & 0xFC000000) >>> 21;
/* 623:    */   }
/* 624:    */   
/* 625:    */   private final void write128(byte[] paramArrayOfByte, int paramInt)
/* 626:    */   {
/* 627:797 */     encodeLEInt(this.s0 + mix128(this.s7, this.s4, this.s5, this.s6, 24), paramArrayOfByte, paramInt);
/* 628:798 */     encodeLEInt(this.s1 + mix128(this.s6, this.s7, this.s4, this.s5, 16), paramArrayOfByte, paramInt + 4);
/* 629:799 */     encodeLEInt(this.s2 + mix128(this.s5, this.s6, this.s7, this.s4, 8), paramArrayOfByte, paramInt + 8);
/* 630:800 */     encodeLEInt(this.s3 + mix128(this.s4, this.s5, this.s6, this.s7, 0), paramArrayOfByte, paramInt + 12);
/* 631:    */   }
/* 632:    */   
/* 633:    */   private final void write160(byte[] paramArrayOfByte, int paramInt)
/* 634:    */   {
/* 635:805 */     encodeLEInt(this.s0 + mix160_0(this.s5, this.s6, this.s7), paramArrayOfByte, paramInt);
/* 636:806 */     encodeLEInt(this.s1 + mix160_1(this.s5, this.s6, this.s7), paramArrayOfByte, paramInt + 4);
/* 637:807 */     encodeLEInt(this.s2 + mix160_2(this.s5, this.s6, this.s7), paramArrayOfByte, paramInt + 8);
/* 638:808 */     encodeLEInt(this.s3 + mix160_3(this.s5, this.s6, this.s7), paramArrayOfByte, paramInt + 12);
/* 639:809 */     encodeLEInt(this.s4 + mix160_4(this.s5, this.s6, this.s7), paramArrayOfByte, paramInt + 16);
/* 640:    */   }
/* 641:    */   
/* 642:    */   private final void write192(byte[] paramArrayOfByte, int paramInt)
/* 643:    */   {
/* 644:814 */     encodeLEInt(this.s0 + mix192_0(this.s6, this.s7), paramArrayOfByte, paramInt);
/* 645:815 */     encodeLEInt(this.s1 + mix192_1(this.s6, this.s7), paramArrayOfByte, paramInt + 4);
/* 646:816 */     encodeLEInt(this.s2 + mix192_2(this.s6, this.s7), paramArrayOfByte, paramInt + 8);
/* 647:817 */     encodeLEInt(this.s3 + mix192_3(this.s6, this.s7), paramArrayOfByte, paramInt + 12);
/* 648:818 */     encodeLEInt(this.s4 + mix192_4(this.s6, this.s7), paramArrayOfByte, paramInt + 16);
/* 649:819 */     encodeLEInt(this.s5 + mix192_5(this.s6, this.s7), paramArrayOfByte, paramInt + 20);
/* 650:    */   }
/* 651:    */   
/* 652:    */   private final void write224(byte[] paramArrayOfByte, int paramInt)
/* 653:    */   {
/* 654:824 */     encodeLEInt(this.s0 + (this.s7 >>> 27 & 0x1F), paramArrayOfByte, paramInt);
/* 655:825 */     encodeLEInt(this.s1 + (this.s7 >>> 22 & 0x1F), paramArrayOfByte, paramInt + 4);
/* 656:826 */     encodeLEInt(this.s2 + (this.s7 >>> 18 & 0xF), paramArrayOfByte, paramInt + 8);
/* 657:827 */     encodeLEInt(this.s3 + (this.s7 >>> 13 & 0x1F), paramArrayOfByte, paramInt + 12);
/* 658:828 */     encodeLEInt(this.s4 + (this.s7 >>> 9 & 0xF), paramArrayOfByte, paramInt + 16);
/* 659:829 */     encodeLEInt(this.s5 + (this.s7 >>> 4 & 0x1F), paramArrayOfByte, paramInt + 20);
/* 660:830 */     encodeLEInt(this.s6 + (this.s7 & 0xF), paramArrayOfByte, paramInt + 24);
/* 661:    */   }
/* 662:    */   
/* 663:    */   private final void write256(byte[] paramArrayOfByte, int paramInt)
/* 664:    */   {
/* 665:835 */     encodeLEInt(this.s0, paramArrayOfByte, paramInt);
/* 666:836 */     encodeLEInt(this.s1, paramArrayOfByte, paramInt + 4);
/* 667:837 */     encodeLEInt(this.s2, paramArrayOfByte, paramInt + 8);
/* 668:838 */     encodeLEInt(this.s3, paramArrayOfByte, paramInt + 12);
/* 669:839 */     encodeLEInt(this.s4, paramArrayOfByte, paramInt + 16);
/* 670:840 */     encodeLEInt(this.s5, paramArrayOfByte, paramInt + 20);
/* 671:841 */     encodeLEInt(this.s6, paramArrayOfByte, paramInt + 24);
/* 672:842 */     encodeLEInt(this.s7, paramArrayOfByte, paramInt + 28);
/* 673:    */   }
/* 674:    */   
/* 675:    */   private final void writeOutput(byte[] paramArrayOfByte, int paramInt)
/* 676:    */   {
/* 677:847 */     switch (this.olen)
/* 678:    */     {
/* 679:    */     case 4: 
/* 680:849 */       write128(paramArrayOfByte, paramInt);
/* 681:850 */       break;
/* 682:    */     case 5: 
/* 683:852 */       write160(paramArrayOfByte, paramInt);
/* 684:853 */       break;
/* 685:    */     case 6: 
/* 686:855 */       write192(paramArrayOfByte, paramInt);
/* 687:856 */       break;
/* 688:    */     case 7: 
/* 689:858 */       write224(paramArrayOfByte, paramInt);
/* 690:859 */       break;
/* 691:    */     case 8: 
/* 692:861 */       write256(paramArrayOfByte, paramInt);
/* 693:    */     }
/* 694:    */   }
/* 695:    */   
/* 696:    */   public String toString()
/* 697:    */   {
/* 698:869 */     return "HAVAL-" + this.passes + "-" + (this.olen << 5);
/* 699:    */   }
/* 700:    */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     fr.cryptohash.HAVALCore
 * JD-Core Version:    0.7.1
 */