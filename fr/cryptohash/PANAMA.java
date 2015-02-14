/*   1:    */ package fr.cryptohash;
/*   2:    */ 
/*   3:    */ public class PANAMA
/*   4:    */   extends DigestEngine
/*   5:    */ {
/*   6:    */   private int[] buffer;
/*   7:    */   private int bufferPtr;
/*   8:    */   private int state0;
/*   9:    */   private int state1;
/*  10:    */   private int state2;
/*  11:    */   private int state3;
/*  12:    */   private int state4;
/*  13:    */   private int state5;
/*  14:    */   private int state6;
/*  15:    */   private int state7;
/*  16:    */   private int state8;
/*  17:    */   private int state9;
/*  18:    */   private int state10;
/*  19:    */   private int state11;
/*  20:    */   private int state12;
/*  21:    */   private int state13;
/*  22:    */   private int state14;
/*  23:    */   private int state15;
/*  24:    */   private int state16;
/*  25:    */   private int inData0;
/*  26:    */   private int inData1;
/*  27:    */   private int inData2;
/*  28:    */   private int inData3;
/*  29:    */   private int inData4;
/*  30:    */   private int inData5;
/*  31:    */   private int inData6;
/*  32:    */   private int inData7;
/*  33:    */   
/*  34:    */   public Digest copy()
/*  35:    */   {
/*  36: 60 */     PANAMA localPANAMA = new PANAMA();
/*  37: 61 */     System.arraycopy(this.buffer, 0, localPANAMA.buffer, 0, this.buffer.length);
/*  38: 62 */     localPANAMA.bufferPtr = this.bufferPtr;
/*  39: 63 */     localPANAMA.state0 = this.state0;
/*  40: 64 */     localPANAMA.state1 = this.state1;
/*  41: 65 */     localPANAMA.state2 = this.state2;
/*  42: 66 */     localPANAMA.state3 = this.state3;
/*  43: 67 */     localPANAMA.state4 = this.state4;
/*  44: 68 */     localPANAMA.state5 = this.state5;
/*  45: 69 */     localPANAMA.state6 = this.state6;
/*  46: 70 */     localPANAMA.state7 = this.state7;
/*  47: 71 */     localPANAMA.state8 = this.state8;
/*  48: 72 */     localPANAMA.state9 = this.state9;
/*  49: 73 */     localPANAMA.state10 = this.state10;
/*  50: 74 */     localPANAMA.state11 = this.state11;
/*  51: 75 */     localPANAMA.state12 = this.state12;
/*  52: 76 */     localPANAMA.state13 = this.state13;
/*  53: 77 */     localPANAMA.state14 = this.state14;
/*  54: 78 */     localPANAMA.state15 = this.state15;
/*  55: 79 */     localPANAMA.state16 = this.state16;
/*  56: 80 */     return copyState(localPANAMA);
/*  57:    */   }
/*  58:    */   
/*  59:    */   public int getDigestLength()
/*  60:    */   {
/*  61: 86 */     return 32;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public int getBlockLength()
/*  65:    */   {
/*  66: 92 */     return 32;
/*  67:    */   }
/*  68:    */   
/*  69:    */   protected void engineReset()
/*  70:    */   {
/*  71: 98 */     for (int i = 0; i < this.buffer.length; i++) {
/*  72: 99 */       this.buffer[i] = 0;
/*  73:    */     }
/*  74:100 */     this.bufferPtr = 0;
/*  75:101 */     this.state0 = 0;
/*  76:102 */     this.state1 = 0;
/*  77:103 */     this.state2 = 0;
/*  78:104 */     this.state3 = 0;
/*  79:105 */     this.state4 = 0;
/*  80:106 */     this.state5 = 0;
/*  81:107 */     this.state6 = 0;
/*  82:108 */     this.state7 = 0;
/*  83:109 */     this.state8 = 0;
/*  84:110 */     this.state9 = 0;
/*  85:111 */     this.state10 = 0;
/*  86:112 */     this.state11 = 0;
/*  87:113 */     this.state12 = 0;
/*  88:114 */     this.state13 = 0;
/*  89:115 */     this.state14 = 0;
/*  90:116 */     this.state15 = 0;
/*  91:117 */     this.state16 = 0;
/*  92:    */   }
/*  93:    */   
/*  94:    */   protected void doPadding(byte[] paramArrayOfByte, int paramInt)
/*  95:    */   {
/*  96:123 */     int i = flush();
/*  97:124 */     update((byte)1);
/*  98:125 */     for (int j = i + 1; j < 32; j++) {
/*  99:126 */       update((byte)0);
/* 100:    */     }
/* 101:127 */     flush();
/* 102:128 */     for (j = 0; j < 32; j++) {
/* 103:129 */       oneStep(false);
/* 104:    */     }
/* 105:130 */     encodeLEInt(this.state9, paramArrayOfByte, paramInt + 0);
/* 106:131 */     encodeLEInt(this.state10, paramArrayOfByte, paramInt + 4);
/* 107:132 */     encodeLEInt(this.state11, paramArrayOfByte, paramInt + 8);
/* 108:133 */     encodeLEInt(this.state12, paramArrayOfByte, paramInt + 12);
/* 109:134 */     encodeLEInt(this.state13, paramArrayOfByte, paramInt + 16);
/* 110:135 */     encodeLEInt(this.state14, paramArrayOfByte, paramInt + 20);
/* 111:136 */     encodeLEInt(this.state15, paramArrayOfByte, paramInt + 24);
/* 112:137 */     encodeLEInt(this.state16, paramArrayOfByte, paramInt + 28);
/* 113:    */   }
/* 114:    */   
/* 115:    */   protected void doInit()
/* 116:    */   {
/* 117:143 */     this.buffer = new int[256];
/* 118:    */   }
/* 119:    */   
/* 120:    */   private static final void encodeLEInt(int paramInt1, byte[] paramArrayOfByte, int paramInt2)
/* 121:    */   {
/* 122:162 */     paramArrayOfByte[(paramInt2 + 3)] = ((byte)(paramInt1 >> 24 & 0xFF));
/* 123:163 */     paramArrayOfByte[(paramInt2 + 2)] = ((byte)(paramInt1 >> 16 & 0xFF));
/* 124:164 */     paramArrayOfByte[(paramInt2 + 1)] = ((byte)(paramInt1 >> 8 & 0xFF));
/* 125:165 */     paramArrayOfByte[(paramInt2 + 0)] = ((byte)(paramInt1 & 0xFF));
/* 126:    */   }
/* 127:    */   
/* 128:    */   private static final int decodeLEInt(byte[] paramArrayOfByte, int paramInt)
/* 129:    */   {
/* 130:178 */     return paramArrayOfByte[paramInt] & 0xFF | (paramArrayOfByte[(paramInt + 1)] & 0xFF) << 8 | (paramArrayOfByte[(paramInt + 2)] & 0xFF) << 16 | (paramArrayOfByte[(paramInt + 3)] & 0xFF) << 24;
/* 131:    */   }
/* 132:    */   
/* 133:    */   protected void processBlock(byte[] paramArrayOfByte)
/* 134:    */   {
/* 135:187 */     this.inData0 = decodeLEInt(paramArrayOfByte, 0);
/* 136:188 */     this.inData1 = decodeLEInt(paramArrayOfByte, 4);
/* 137:189 */     this.inData2 = decodeLEInt(paramArrayOfByte, 8);
/* 138:190 */     this.inData3 = decodeLEInt(paramArrayOfByte, 12);
/* 139:191 */     this.inData4 = decodeLEInt(paramArrayOfByte, 16);
/* 140:192 */     this.inData5 = decodeLEInt(paramArrayOfByte, 20);
/* 141:193 */     this.inData6 = decodeLEInt(paramArrayOfByte, 24);
/* 142:194 */     this.inData7 = decodeLEInt(paramArrayOfByte, 28);
/* 143:195 */     oneStep(true);
/* 144:    */   }
/* 145:    */   
/* 146:    */   private final void oneStep(boolean paramBoolean)
/* 147:    */   {
/* 148:203 */     int i = this.bufferPtr;
/* 149:204 */     int j = i - 64 & 0xF8;
/* 150:205 */     int k = i - 8 & 0xF8;
/* 151:206 */     if (paramBoolean)
/* 152:    */     {
/* 153:207 */       this.buffer[(j + 0)] ^= this.buffer[(k + 2)];
/* 154:208 */       this.buffer[(k + 2)] ^= this.inData2;
/* 155:209 */       this.buffer[(j + 1)] ^= this.buffer[(k + 3)];
/* 156:210 */       this.buffer[(k + 3)] ^= this.inData3;
/* 157:211 */       this.buffer[(j + 2)] ^= this.buffer[(k + 4)];
/* 158:212 */       this.buffer[(k + 4)] ^= this.inData4;
/* 159:213 */       this.buffer[(j + 3)] ^= this.buffer[(k + 5)];
/* 160:214 */       this.buffer[(k + 5)] ^= this.inData5;
/* 161:215 */       this.buffer[(j + 4)] ^= this.buffer[(k + 6)];
/* 162:216 */       this.buffer[(k + 6)] ^= this.inData6;
/* 163:217 */       this.buffer[(j + 5)] ^= this.buffer[(k + 7)];
/* 164:218 */       this.buffer[(k + 7)] ^= this.inData7;
/* 165:219 */       this.buffer[(j + 6)] ^= this.buffer[(k + 0)];
/* 166:220 */       this.buffer[(k + 0)] ^= this.inData0;
/* 167:221 */       this.buffer[(j + 7)] ^= this.buffer[(k + 1)];
/* 168:222 */       this.buffer[(k + 1)] ^= this.inData1;
/* 169:    */     }
/* 170:    */     else
/* 171:    */     {
/* 172:224 */       this.buffer[(j + 0)] ^= this.buffer[(k + 2)];
/* 173:225 */       this.buffer[(k + 2)] ^= this.state3;
/* 174:226 */       this.buffer[(j + 1)] ^= this.buffer[(k + 3)];
/* 175:227 */       this.buffer[(k + 3)] ^= this.state4;
/* 176:228 */       this.buffer[(j + 2)] ^= this.buffer[(k + 4)];
/* 177:229 */       this.buffer[(k + 4)] ^= this.state5;
/* 178:230 */       this.buffer[(j + 3)] ^= this.buffer[(k + 5)];
/* 179:231 */       this.buffer[(k + 5)] ^= this.state6;
/* 180:232 */       this.buffer[(j + 4)] ^= this.buffer[(k + 6)];
/* 181:233 */       this.buffer[(k + 6)] ^= this.state7;
/* 182:234 */       this.buffer[(j + 5)] ^= this.buffer[(k + 7)];
/* 183:235 */       this.buffer[(k + 7)] ^= this.state8;
/* 184:236 */       this.buffer[(j + 6)] ^= this.buffer[(k + 0)];
/* 185:237 */       this.buffer[(k + 0)] ^= this.state1;
/* 186:238 */       this.buffer[(j + 7)] ^= this.buffer[(k + 1)];
/* 187:239 */       this.buffer[(k + 1)] ^= this.state2;
/* 188:    */     }
/* 189:241 */     this.bufferPtr = k;
/* 190:    */     
/* 191:    */ 
/* 192:    */ 
/* 193:    */ 
/* 194:    */ 
/* 195:    */ 
/* 196:248 */     int m = this.state0 ^ (this.state1 | this.state2 ^ 0xFFFFFFFF);
/* 197:249 */     int n = this.state1 ^ (this.state2 | this.state3 ^ 0xFFFFFFFF);
/* 198:250 */     int i1 = this.state2 ^ (this.state3 | this.state4 ^ 0xFFFFFFFF);
/* 199:251 */     int i2 = this.state3 ^ (this.state4 | this.state5 ^ 0xFFFFFFFF);
/* 200:252 */     int i3 = this.state4 ^ (this.state5 | this.state6 ^ 0xFFFFFFFF);
/* 201:253 */     int i4 = this.state5 ^ (this.state6 | this.state7 ^ 0xFFFFFFFF);
/* 202:254 */     int i5 = this.state6 ^ (this.state7 | this.state8 ^ 0xFFFFFFFF);
/* 203:255 */     int i6 = this.state7 ^ (this.state8 | this.state9 ^ 0xFFFFFFFF);
/* 204:256 */     int i7 = this.state8 ^ (this.state9 | this.state10 ^ 0xFFFFFFFF);
/* 205:257 */     int i8 = this.state9 ^ (this.state10 | this.state11 ^ 0xFFFFFFFF);
/* 206:258 */     int i9 = this.state10 ^ (this.state11 | this.state12 ^ 0xFFFFFFFF);
/* 207:259 */     int i10 = this.state11 ^ (this.state12 | this.state13 ^ 0xFFFFFFFF);
/* 208:260 */     int i11 = this.state12 ^ (this.state13 | this.state14 ^ 0xFFFFFFFF);
/* 209:261 */     int i12 = this.state13 ^ (this.state14 | this.state15 ^ 0xFFFFFFFF);
/* 210:262 */     int i13 = this.state14 ^ (this.state15 | this.state16 ^ 0xFFFFFFFF);
/* 211:263 */     int i14 = this.state15 ^ (this.state16 | this.state0 ^ 0xFFFFFFFF);
/* 212:264 */     int i15 = this.state16 ^ (this.state0 | this.state1 ^ 0xFFFFFFFF);
/* 213:    */     
/* 214:    */ 
/* 215:    */ 
/* 216:    */ 
/* 217:    */ 
/* 218:    */ 
/* 219:271 */     int i16 = m;
/* 220:272 */     int i17 = i6 << 1 | i6 >>> 31;
/* 221:273 */     int i18 = i13 << 3 | i13 >>> 29;
/* 222:274 */     int i19 = i3 << 6 | i3 >>> 26;
/* 223:275 */     int i20 = i10 << 10 | i10 >>> 22;
/* 224:276 */     int i21 = n << 15 | n >>> 17;
/* 225:277 */     int i22 = i7 << 21 | i7 >>> 11;
/* 226:278 */     int i23 = i14 << 28 | i14 >>> 4;
/* 227:279 */     int i24 = i4 << 4 | i4 >>> 28;
/* 228:280 */     int i25 = i11 << 13 | i11 >>> 19;
/* 229:281 */     int i26 = i1 << 23 | i1 >>> 9;
/* 230:282 */     int i27 = i8 << 2 | i8 >>> 30;
/* 231:283 */     int i28 = i15 << 14 | i15 >>> 18;
/* 232:284 */     int i29 = i5 << 27 | i5 >>> 5;
/* 233:285 */     int i30 = i12 << 9 | i12 >>> 23;
/* 234:286 */     int i31 = i2 << 24 | i2 >>> 8;
/* 235:287 */     int i32 = i9 << 8 | i9 >>> 24;
/* 236:    */     
/* 237:    */ 
/* 238:    */ 
/* 239:    */ 
/* 240:    */ 
/* 241:    */ 
/* 242:294 */     int i33 = i16 ^ i17 ^ i20;
/* 243:295 */     int i34 = i17 ^ i18 ^ i21;
/* 244:296 */     int i35 = i18 ^ i19 ^ i22;
/* 245:297 */     int i36 = i19 ^ i20 ^ i23;
/* 246:298 */     int i37 = i20 ^ i21 ^ i24;
/* 247:299 */     int i38 = i21 ^ i22 ^ i25;
/* 248:300 */     int i39 = i22 ^ i23 ^ i26;
/* 249:301 */     int i40 = i23 ^ i24 ^ i27;
/* 250:302 */     int i41 = i24 ^ i25 ^ i28;
/* 251:303 */     int i42 = i25 ^ i26 ^ i29;
/* 252:304 */     int i43 = i26 ^ i27 ^ i30;
/* 253:305 */     int i44 = i27 ^ i28 ^ i31;
/* 254:306 */     int i45 = i28 ^ i29 ^ i32;
/* 255:307 */     int i46 = i29 ^ i30 ^ i16;
/* 256:308 */     int i47 = i30 ^ i31 ^ i17;
/* 257:309 */     int i48 = i31 ^ i32 ^ i18;
/* 258:310 */     int i49 = i32 ^ i16 ^ i19;
/* 259:    */     
/* 260:    */ 
/* 261:    */ 
/* 262:    */ 
/* 263:315 */     int i50 = i ^ 0x80;
/* 264:316 */     this.state0 = (i33 ^ 0x1);
/* 265:317 */     if (paramBoolean)
/* 266:    */     {
/* 267:318 */       this.state1 = (i34 ^ this.inData0);
/* 268:319 */       this.state2 = (i35 ^ this.inData1);
/* 269:320 */       this.state3 = (i36 ^ this.inData2);
/* 270:321 */       this.state4 = (i37 ^ this.inData3);
/* 271:322 */       this.state5 = (i38 ^ this.inData4);
/* 272:323 */       this.state6 = (i39 ^ this.inData5);
/* 273:324 */       this.state7 = (i40 ^ this.inData6);
/* 274:325 */       this.state8 = (i41 ^ this.inData7);
/* 275:    */     }
/* 276:    */     else
/* 277:    */     {
/* 278:327 */       int i51 = i + 32 & 0xF8;
/* 279:328 */       this.state1 = (i34 ^ this.buffer[(i51 + 0)]);
/* 280:329 */       this.state2 = (i35 ^ this.buffer[(i51 + 1)]);
/* 281:330 */       this.state3 = (i36 ^ this.buffer[(i51 + 2)]);
/* 282:331 */       this.state4 = (i37 ^ this.buffer[(i51 + 3)]);
/* 283:332 */       this.state5 = (i38 ^ this.buffer[(i51 + 4)]);
/* 284:333 */       this.state6 = (i39 ^ this.buffer[(i51 + 5)]);
/* 285:334 */       this.state7 = (i40 ^ this.buffer[(i51 + 6)]);
/* 286:335 */       this.state8 = (i41 ^ this.buffer[(i51 + 7)]);
/* 287:    */     }
/* 288:337 */     this.state9 = (i42 ^ this.buffer[(i50 + 0)]);
/* 289:338 */     this.state10 = (i43 ^ this.buffer[(i50 + 1)]);
/* 290:339 */     this.state11 = (i44 ^ this.buffer[(i50 + 2)]);
/* 291:340 */     this.state12 = (i45 ^ this.buffer[(i50 + 3)]);
/* 292:341 */     this.state13 = (i46 ^ this.buffer[(i50 + 4)]);
/* 293:342 */     this.state14 = (i47 ^ this.buffer[(i50 + 5)]);
/* 294:343 */     this.state15 = (i48 ^ this.buffer[(i50 + 6)]);
/* 295:344 */     this.state16 = (i49 ^ this.buffer[(i50 + 7)]);
/* 296:    */   }
/* 297:    */   
/* 298:    */   public String toString()
/* 299:    */   {
/* 300:350 */     return "PANAMA";
/* 301:    */   }
/* 302:    */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     fr.cryptohash.PANAMA
 * JD-Core Version:    0.7.1
 */