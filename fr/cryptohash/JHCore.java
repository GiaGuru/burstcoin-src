/*   1:    */ package fr.cryptohash;
/*   2:    */ 
/*   3:    */ abstract class JHCore
/*   4:    */   extends DigestEngine
/*   5:    */ {
/*   6:    */   private long[] h;
/*   7:    */   private byte[] tmpBuf;
/*   8: 49 */   private static final long[] C = { 8274764681471981671L, 8900261899357328727L, -9102946306537912330L, 5665334567067790144L, -1542418137079219556L, 271854908820593050L, 7350603245732713354L, -5380090600649678287L, 262152557086445275L, 4629614700257166352L, 2019048047717029149L, 8013007291876363740L, 1205297368107281217L, -2560185243135170608L, 733102922101586000L, 4547157683538530771L, 4768566596476894864L, -2494255748784023602L, 1755461623795631276L, -3323826028504744925L, -7534966363738032102L, 2379384386751360168L, -1229197120032054790L, -173784742355566185L, 5372377880443695151L, 3055566100828744341L, -8431174450330179220L, -8446656927907233260L, -4182694446811881788L, 8450001566304418688L, 5971339587713534140L, -9027465321654082572L, -8145728373954223339L, -6170118248269874807L, -5264165765638332163L, 3948224596204989494L, -3591280664646870979L, 5997380132348251047L, -3389768675546463866L, 1053188660387142303L, -6411711451044224406L, -8427597479327230169L, 5148748137111752767L, -2260667994403354501L, 960685800557159616L, -7934243445868138082L, -8757686525650498092L, 9123829134410494050L, -243440755302719366L, -2025465341635409532L, 5753230831627441624L, 6137981954248889509L, 6620135496569744936L, 8716013262747321522L, 398987411935843118L, 4935544445029865855L, 8097306342985052490L, 6811176502497305701L, -4099552175356043033L, 8430250216796751754L, 9018995518020687090L, -2464742189378250303L, 2097323907607174112L, -2727974866558090561L, -6564073209225756673L, 950897263334964296L, 4300651987268784953L, 5016943747370945440L, -1888971980364969713L, -7750637705673772748L, -6741405948461126251L, -1640561275338516406L, -8628864572311407083L, 5170488938381619199L, 9131975072163743280L, 5075757740701158630L, -6569163788999734784L, -2693851541080762194L, 8311338433895518637L, -1682430464362797721L, -6639839726187650794L, 3740527943128226202L, 2261002866710774907L, -3328533410421308136L, 3628031724251584979L, 5807986283269960774L, -6972307350241383494L, -663689772663378306L, 2283740534820839796L, -1603854084275285522L, 2114348054589002614L, 4199258101489954817L, -633387212651308990L, 8713880478872725369L, 4720725780189600680L, -377392741023011709L, -6395046466063742989L, 6431404377087243930L, -1783902173103304707L, -5116778321973650961L, -8242981184573150246L, 6603076713971937293L, 9023998488799434116L, -745932942417031579L, -6865558462163567789L, 911987301695810650L, -7968956184387876785L, 6606882177667480652L, -2036968874053233541L, 8492369249240292445L, 7254925341233558381L, 7797038620990823281L, -548908150274830038L, -2027917035486480900L, 2066234073466945442L, 7474798062228489136L, 3677852971127382389L, 9165978963130340188L, -7909564755063780066L, 8751749545235230527L, -1537745112235624159L, 6698117124203947857L, 2784108936669838546L, 4945822424217605549L, -5396087892882568952L, -8982080256329696170L, 5546319973959753805L, 5107713973427292150L, 3429547963116713566L, 3158825051525277226L, 501226569294009398L, 8425814982732489059L, -161762289767954071L, -6377841699320211418L, 9133603604121319958L, 9119981922630737215L, -3257805798644133364L, 6893548888281844784L, 8370592016427397784L, 1002949050113128602L, 5459944377427261842L, -49004760727656237L, -7267880333719738575L, -3682072082334976763L, -5659193112414491442L, 4330020297790300770L, -852363732779068089L, -52261427748836792L, 4098090974333265253L, -937737690310881138L, 3562398178333149830L, -1134896513894824114L, 7323114926207613059L, -621803272269972803L, 2420599201031518319L, -6681954461186793753L, -8385457222894382375L, 4361164450428633790L, -2835037420214833026L, 6020606654976796894L, -6186311379196941864L, 6554501062033869049L, -4584682175667234395L, -7544261430254689942L, 3869885319717393776L, -1576805519710137189L, -7291218252203081614L, -687469922002440222L };
/*   9:    */   
/*  10:    */   private static final void encodeBELong(long paramLong, byte[] paramArrayOfByte, int paramInt)
/*  11:    */   {
/*  12:147 */     paramArrayOfByte[(paramInt + 0)] = ((byte)(int)(paramLong >>> 56));
/*  13:148 */     paramArrayOfByte[(paramInt + 1)] = ((byte)(int)(paramLong >>> 48));
/*  14:149 */     paramArrayOfByte[(paramInt + 2)] = ((byte)(int)(paramLong >>> 40));
/*  15:150 */     paramArrayOfByte[(paramInt + 3)] = ((byte)(int)(paramLong >>> 32));
/*  16:151 */     paramArrayOfByte[(paramInt + 4)] = ((byte)(int)(paramLong >>> 24));
/*  17:152 */     paramArrayOfByte[(paramInt + 5)] = ((byte)(int)(paramLong >>> 16));
/*  18:153 */     paramArrayOfByte[(paramInt + 6)] = ((byte)(int)(paramLong >>> 8));
/*  19:154 */     paramArrayOfByte[(paramInt + 7)] = ((byte)(int)paramLong);
/*  20:    */   }
/*  21:    */   
/*  22:    */   private static final long decodeBELong(byte[] paramArrayOfByte, int paramInt)
/*  23:    */   {
/*  24:167 */     return (paramArrayOfByte[(paramInt + 0)] & 0xFF) << 56 | (paramArrayOfByte[(paramInt + 1)] & 0xFF) << 48 | (paramArrayOfByte[(paramInt + 2)] & 0xFF) << 40 | (paramArrayOfByte[(paramInt + 3)] & 0xFF) << 32 | (paramArrayOfByte[(paramInt + 4)] & 0xFF) << 24 | (paramArrayOfByte[(paramInt + 5)] & 0xFF) << 16 | (paramArrayOfByte[(paramInt + 6)] & 0xFF) << 8 | paramArrayOfByte[(paramInt + 7)] & 0xFF;
/*  25:    */   }
/*  26:    */   
/*  27:    */   protected void engineReset()
/*  28:    */   {
/*  29:180 */     doReset();
/*  30:    */   }
/*  31:    */   
/*  32:    */   private final void doS(int paramInt)
/*  33:    */   {
/*  34:187 */     long l5 = C[((paramInt << 2) + 0)];
/*  35:188 */     long l1 = this.h[0];
/*  36:189 */     long l2 = this.h[4];
/*  37:190 */     long l3 = this.h[8];
/*  38:191 */     long l4 = this.h[12];
/*  39:192 */     l4 ^= 0xFFFFFFFF;
/*  40:193 */     l1 ^= l5 & (l3 ^ 0xFFFFFFFF);
/*  41:194 */     long l6 = l5 ^ l1 & l2;
/*  42:195 */     l1 ^= l3 & l4;
/*  43:196 */     l4 ^= (l2 ^ 0xFFFFFFFF) & l3;
/*  44:197 */     l2 ^= l1 & l3;
/*  45:198 */     l3 ^= l1 & (l4 ^ 0xFFFFFFFF);
/*  46:199 */     l1 ^= (l2 | l4);
/*  47:200 */     l4 ^= l2 & l3;
/*  48:201 */     l2 ^= l6 & l1;
/*  49:202 */     l3 ^= l6;
/*  50:203 */     this.h[0] = l1;
/*  51:204 */     this.h[4] = l2;
/*  52:205 */     this.h[8] = l3;
/*  53:206 */     this.h[12] = l4;
/*  54:    */     
/*  55:208 */     l5 = C[((paramInt << 2) + 1)];
/*  56:209 */     l1 = this.h[1];
/*  57:210 */     l2 = this.h[5];
/*  58:211 */     l3 = this.h[9];
/*  59:212 */     l4 = this.h[13];
/*  60:213 */     l4 ^= 0xFFFFFFFF;
/*  61:214 */     l1 ^= l5 & (l3 ^ 0xFFFFFFFF);
/*  62:215 */     l6 = l5 ^ l1 & l2;
/*  63:216 */     l1 ^= l3 & l4;
/*  64:217 */     l4 ^= (l2 ^ 0xFFFFFFFF) & l3;
/*  65:218 */     l2 ^= l1 & l3;
/*  66:219 */     l3 ^= l1 & (l4 ^ 0xFFFFFFFF);
/*  67:220 */     l1 ^= (l2 | l4);
/*  68:221 */     l4 ^= l2 & l3;
/*  69:222 */     l2 ^= l6 & l1;
/*  70:223 */     l3 ^= l6;
/*  71:224 */     this.h[1] = l1;
/*  72:225 */     this.h[5] = l2;
/*  73:226 */     this.h[9] = l3;
/*  74:227 */     this.h[13] = l4;
/*  75:    */     
/*  76:229 */     l5 = C[((paramInt << 2) + 2)];
/*  77:230 */     l1 = this.h[2];
/*  78:231 */     l2 = this.h[6];
/*  79:232 */     l3 = this.h[10];
/*  80:233 */     l4 = this.h[14];
/*  81:234 */     l4 ^= 0xFFFFFFFF;
/*  82:235 */     l1 ^= l5 & (l3 ^ 0xFFFFFFFF);
/*  83:236 */     l6 = l5 ^ l1 & l2;
/*  84:237 */     l1 ^= l3 & l4;
/*  85:238 */     l4 ^= (l2 ^ 0xFFFFFFFF) & l3;
/*  86:239 */     l2 ^= l1 & l3;
/*  87:240 */     l3 ^= l1 & (l4 ^ 0xFFFFFFFF);
/*  88:241 */     l1 ^= (l2 | l4);
/*  89:242 */     l4 ^= l2 & l3;
/*  90:243 */     l2 ^= l6 & l1;
/*  91:244 */     l3 ^= l6;
/*  92:245 */     this.h[2] = l1;
/*  93:246 */     this.h[6] = l2;
/*  94:247 */     this.h[10] = l3;
/*  95:248 */     this.h[14] = l4;
/*  96:    */     
/*  97:250 */     l5 = C[((paramInt << 2) + 3)];
/*  98:251 */     l1 = this.h[3];
/*  99:252 */     l2 = this.h[7];
/* 100:253 */     l3 = this.h[11];
/* 101:254 */     l4 = this.h[15];
/* 102:255 */     l4 ^= 0xFFFFFFFF;
/* 103:256 */     l1 ^= l5 & (l3 ^ 0xFFFFFFFF);
/* 104:257 */     l6 = l5 ^ l1 & l2;
/* 105:258 */     l1 ^= l3 & l4;
/* 106:259 */     l4 ^= (l2 ^ 0xFFFFFFFF) & l3;
/* 107:260 */     l2 ^= l1 & l3;
/* 108:261 */     l3 ^= l1 & (l4 ^ 0xFFFFFFFF);
/* 109:262 */     l1 ^= (l2 | l4);
/* 110:263 */     l4 ^= l2 & l3;
/* 111:264 */     l2 ^= l6 & l1;
/* 112:265 */     l3 ^= l6;
/* 113:266 */     this.h[3] = l1;
/* 114:267 */     this.h[7] = l2;
/* 115:268 */     this.h[11] = l3;
/* 116:269 */     this.h[15] = l4;
/* 117:    */   }
/* 118:    */   
/* 119:    */   private final void doL()
/* 120:    */   {
/* 121:275 */     long l1 = this.h[0];
/* 122:276 */     long l2 = this.h[4];
/* 123:277 */     long l3 = this.h[8];
/* 124:278 */     long l4 = this.h[12];
/* 125:279 */     long l5 = this.h[2];
/* 126:280 */     long l6 = this.h[6];
/* 127:281 */     long l7 = this.h[10];
/* 128:282 */     long l8 = this.h[14];
/* 129:283 */     l5 ^= l2;
/* 130:284 */     l6 ^= l3;
/* 131:285 */     l7 ^= l4 ^ l1;
/* 132:286 */     l8 ^= l1;
/* 133:287 */     l1 ^= l6;
/* 134:288 */     l2 ^= l7;
/* 135:289 */     l3 ^= l8 ^ l5;
/* 136:290 */     l4 ^= l5;
/* 137:291 */     this.h[0] = l1;
/* 138:292 */     this.h[4] = l2;
/* 139:293 */     this.h[8] = l3;
/* 140:294 */     this.h[12] = l4;
/* 141:295 */     this.h[2] = l5;
/* 142:296 */     this.h[6] = l6;
/* 143:297 */     this.h[10] = l7;
/* 144:298 */     this.h[14] = l8;
/* 145:    */     
/* 146:300 */     l1 = this.h[1];
/* 147:301 */     l2 = this.h[5];
/* 148:302 */     l3 = this.h[9];
/* 149:303 */     l4 = this.h[13];
/* 150:304 */     l5 = this.h[3];
/* 151:305 */     l6 = this.h[7];
/* 152:306 */     l7 = this.h[11];
/* 153:307 */     l8 = this.h[15];
/* 154:308 */     l5 ^= l2;
/* 155:309 */     l6 ^= l3;
/* 156:310 */     l7 ^= l4 ^ l1;
/* 157:311 */     l8 ^= l1;
/* 158:312 */     l1 ^= l6;
/* 159:313 */     l2 ^= l7;
/* 160:314 */     l3 ^= l8 ^ l5;
/* 161:315 */     l4 ^= l5;
/* 162:316 */     this.h[1] = l1;
/* 163:317 */     this.h[5] = l2;
/* 164:318 */     this.h[9] = l3;
/* 165:319 */     this.h[13] = l4;
/* 166:320 */     this.h[3] = l5;
/* 167:321 */     this.h[7] = l6;
/* 168:322 */     this.h[11] = l7;
/* 169:323 */     this.h[15] = l8;
/* 170:    */   }
/* 171:    */   
/* 172:    */   private final void doWgen(long paramLong, int paramInt)
/* 173:    */   {
/* 174:328 */     this.h[2] = ((this.h[2] & paramLong) << paramInt | this.h[2] >>> paramInt & paramLong);
/* 175:329 */     this.h[3] = ((this.h[3] & paramLong) << paramInt | this.h[3] >>> paramInt & paramLong);
/* 176:330 */     this.h[6] = ((this.h[6] & paramLong) << paramInt | this.h[6] >>> paramInt & paramLong);
/* 177:331 */     this.h[7] = ((this.h[7] & paramLong) << paramInt | this.h[7] >>> paramInt & paramLong);
/* 178:332 */     this.h[10] = ((this.h[10] & paramLong) << paramInt | this.h[10] >>> paramInt & paramLong);
/* 179:333 */     this.h[11] = ((this.h[11] & paramLong) << paramInt | this.h[11] >>> paramInt & paramLong);
/* 180:334 */     this.h[14] = ((this.h[14] & paramLong) << paramInt | this.h[14] >>> paramInt & paramLong);
/* 181:335 */     this.h[15] = ((this.h[15] & paramLong) << paramInt | this.h[15] >>> paramInt & paramLong);
/* 182:    */   }
/* 183:    */   
/* 184:    */   private final void doW6()
/* 185:    */   {
/* 186:341 */     long l = this.h[2];this.h[2] = this.h[3];this.h[3] = l;
/* 187:342 */     l = this.h[6];this.h[6] = this.h[7];this.h[7] = l;
/* 188:343 */     l = this.h[10];this.h[10] = this.h[11];this.h[11] = l;
/* 189:344 */     l = this.h[14];this.h[14] = this.h[15];this.h[15] = l;
/* 190:    */   }
/* 191:    */   
/* 192:    */   protected void processBlock(byte[] paramArrayOfByte)
/* 193:    */   {
/* 194:350 */     long l1 = decodeBELong(paramArrayOfByte, 0);
/* 195:351 */     long l2 = decodeBELong(paramArrayOfByte, 8);
/* 196:352 */     long l3 = decodeBELong(paramArrayOfByte, 16);
/* 197:353 */     long l4 = decodeBELong(paramArrayOfByte, 24);
/* 198:354 */     long l5 = decodeBELong(paramArrayOfByte, 32);
/* 199:355 */     long l6 = decodeBELong(paramArrayOfByte, 40);
/* 200:356 */     long l7 = decodeBELong(paramArrayOfByte, 48);
/* 201:357 */     long l8 = decodeBELong(paramArrayOfByte, 56);
/* 202:358 */     this.h[0] ^= l1;
/* 203:359 */     this.h[1] ^= l2;
/* 204:360 */     this.h[2] ^= l3;
/* 205:361 */     this.h[3] ^= l4;
/* 206:362 */     this.h[4] ^= l5;
/* 207:363 */     this.h[5] ^= l6;
/* 208:364 */     this.h[6] ^= l7;
/* 209:365 */     this.h[7] ^= l8;
/* 210:366 */     for (int i = 0; i < 42; i += 7)
/* 211:    */     {
/* 212:367 */       doS(i + 0);
/* 213:368 */       doL();
/* 214:369 */       doWgen(6148914691236517205L, 1);
/* 215:370 */       doS(i + 1);
/* 216:371 */       doL();
/* 217:372 */       doWgen(3689348814741910323L, 2);
/* 218:373 */       doS(i + 2);
/* 219:374 */       doL();
/* 220:375 */       doWgen(1085102592571150095L, 4);
/* 221:376 */       doS(i + 3);
/* 222:377 */       doL();
/* 223:378 */       doWgen(71777214294589695L, 8);
/* 224:379 */       doS(i + 4);
/* 225:380 */       doL();
/* 226:381 */       doWgen(281470681808895L, 16);
/* 227:382 */       doS(i + 5);
/* 228:383 */       doL();
/* 229:384 */       doWgen(4294967295L, 32);
/* 230:385 */       doS(i + 6);
/* 231:386 */       doL();
/* 232:387 */       doW6();
/* 233:    */     }
/* 234:389 */     this.h[8] ^= l1;
/* 235:390 */     this.h[9] ^= l2;
/* 236:391 */     this.h[10] ^= l3;
/* 237:392 */     this.h[11] ^= l4;
/* 238:393 */     this.h[12] ^= l5;
/* 239:394 */     this.h[13] ^= l6;
/* 240:395 */     this.h[14] ^= l7;
/* 241:396 */     this.h[15] ^= l8;
/* 242:    */   }
/* 243:    */   
/* 244:    */   protected void doPadding(byte[] paramArrayOfByte, int paramInt)
/* 245:    */   {
/* 246:402 */     int i = flush();
/* 247:403 */     long l = getBlockCount();
/* 248:404 */     int j = i == 0 ? 47 : 111 - i;
/* 249:405 */     this.tmpBuf[0] = Byte.MIN_VALUE;
/* 250:406 */     for (int k = 1; k <= j; k++) {
/* 251:407 */       this.tmpBuf[k] = 0;
/* 252:    */     }
/* 253:408 */     encodeBELong(l >>> 55, this.tmpBuf, j + 1);
/* 254:409 */     encodeBELong((l << 9) + (i << 3), this.tmpBuf, j + 9);
/* 255:410 */     update(this.tmpBuf, 0, j + 17);
/* 256:411 */     for (k = 0; k < 8; k++) {
/* 257:412 */       encodeBELong(this.h[(k + 8)], this.tmpBuf, k << 3);
/* 258:    */     }
/* 259:413 */     k = getDigestLength();
/* 260:414 */     System.arraycopy(this.tmpBuf, 64 - k, paramArrayOfByte, paramInt, k);
/* 261:    */   }
/* 262:    */   
/* 263:    */   protected void doInit()
/* 264:    */   {
/* 265:420 */     this.h = new long[16];
/* 266:421 */     this.tmpBuf = new byte[''];
/* 267:422 */     doReset();
/* 268:    */   }
/* 269:    */   
/* 270:    */   abstract long[] getIV();
/* 271:    */   
/* 272:    */   public int getBlockLength()
/* 273:    */   {
/* 274:435 */     return 64;
/* 275:    */   }
/* 276:    */   
/* 277:    */   private final void doReset()
/* 278:    */   {
/* 279:440 */     System.arraycopy(getIV(), 0, this.h, 0, 16);
/* 280:    */   }
/* 281:    */   
/* 282:    */   protected Digest copyState(JHCore paramJHCore)
/* 283:    */   {
/* 284:446 */     System.arraycopy(this.h, 0, paramJHCore.h, 0, 16);
/* 285:447 */     return super.copyState(paramJHCore);
/* 286:    */   }
/* 287:    */   
/* 288:    */   public String toString()
/* 289:    */   {
/* 290:453 */     return "JH-" + (getDigestLength() << 3);
/* 291:    */   }
/* 292:    */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     fr.cryptohash.JHCore
 * JD-Core Version:    0.7.1
 */