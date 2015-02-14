/*   1:    */ package fr.cryptohash;
/*   2:    */ 
/*   3:    */ abstract class GroestlSmallCore
/*   4:    */   extends DigestEngine
/*   5:    */ {
/*   6:    */   private long[] H;
/*   7:    */   private long[] G;
/*   8:    */   private long[] M;
/*   9: 50 */   private static final long[] T0 = { -4164997711753927226L, -545050434148268808L, -1270383870729545234L, -686081453637267978L, -6730054438154753L, -3023361491815907882L, -2443545081900715554L, -7967434723809471343L, 6958325654714601568L, 146091023440020226L, -3589684832746624562L, 6255930330611219798L, -1743971568589661721L, -5398788585657638219L, 5583392502777046605L, -1415900931752224020L, -8124265829043452529L, 2279873222033710367L, -8554094632881274743L, -402911055666247686L, -1166362942992214545L, -5578791247000114254L, -8156510642014533234L, -295516791962268677L, 4714758551359384641L, -5540930128835680333L, 6864362130477350239L, 4999037273963686469L, 2592343578064109347L, 6003583038186256211L, -1998013191336782108L, -7244341971501884517L, 8442100590886699637L, -2178295141467874079L, 4455442868826975805L, 5544703456919972428L, 7819073977390226028L, 9132670306605613438L, -724228509300358411L, -8983888165909737597L, 7533647364310981736L, 5860880709750813777L, -3346917568102714159L, -441044865448867591L, -2140143773590514718L, -6107279721009155157L, 7104416678154621794L, 3056608512035798826L, 584364093760080904L, -7682025703185755499L, 5109808103500309830L, -7097705590563971427L, 3479162827357300784L, 4021706366806565175L, 728194521159175946L, 3453069824272282927L, 1014747016965392654L, 2629638421920364068L, 1994450989888215835L, -2335038078271013409L, -3627814261595494707L, 5689659767046039886L, 9165754111272865151L, -1562543875500826646L, 1314819210960182034L, 2136036214644055581L, 6396409395431896152L, 3766823682155490868L, 3909536988695113014L, -2589062142923394340L, -5432139472664531276L, 6578939898331855707L, -6582853124236249436L, 8548350297060953462L, -5254395225484992073L, 9021917103883210365L, 5971647192713427794L, -2477188434857214243L, 6831278325810098526L, 1419094129742616339L, -6439579066800015962L, -5115604941806147399L, 0L, -4488553719321252671L, 4674912750080647232L, -2035027664022200349L, 8735368954651003001L, -5288865415228297802L, -3166635549252141356L, -8266416185629653363L, 7428530189573740903L, 8264067159163161458L, -7715376590192648556L, -7422099498921634664L, -5723191221523060560L, -8837225430649845115L, -4970076868319548485L, -4198623506615686459L, 5727229510166701391L, -1308513299578415379L, -8731823521021835898L, -7279960120439613542L, 7393221008134264166L, 1276391801307173905L, -8448670646785814646L, -1596191746830036759L, 292182046880040452L, -115237058067856898L, -6869383681012928352L, 8697801376227411064L, 2733919972232968741L, 5437321270848316235L, -6724983706489982046L, 6720525123087695453L, -9161628135234748288L, 396463597192645125L, 4599279876216630591L, 2449641249628666913L, 8117976135723141232L, -1009637529924074255L, 7140873784428781411L, 8588180601862799735L, -5822978990657931857L, 4821003773520667458L, 2337456375040323616L, -1888364928762307867L, -148880411024355587L, -4681290130795434561L, -9127155643355411327L, 1739581413678650392L, 2772351728459986214L, -4345286241875578941L, -4718021019658886722L, 3875626355830465077L, -8590810025267835768L, 3343161007842015534L, -7824167246363797613L, 6149680624436965973L, -260754119090535684L, 8841631803626506106L, -4023984215999927096L, -5005695017257277510L, 3625253850797321010L, -1854739133900548634L, -4594802325966839616L, 1849488065411848217L, -6992286122841222754L, -6691643711687917661L, 4967094796960687684L, 6113217024071597652L, 4313857644071136059L, 834747594077274891L, -8302027703037212020L, -4054230146443040313L, 7711691791318569835L, 2912778084636703784L, -6400587616255379033L, -4863538080681565508L, 1599102348857974038L, -5965129347244132691L, -2623824815795127333L, 7250507701594642020L, 8405636990521331316L, 1456389042318351892L, -7862028364528231534L, 869790706839325196L, 5259276843840728136L, -5147834395739298632L, -6955555233977770593L, -4823440487381635395L, 4857460879794827075L, -4308271769190160700L, 4168894719594768441L, 3586826441144312881L, -3203650090657040429L, -976007285409084430L, -3061508547478998315L, -8408566559394675829L, 7964030287516293486L, -2731219079499106342L, 112184874588343297L, -5684197606281354063L, -7137803183863901540L, 5292358346371948617L, -2873358457981127464L, -6000740864651691348L, -866370052478400525L, -3485663905009293873L, -3881844837517905974L, -829355511073501452L, 5145117284939786567L, 1168728187520161808L, 8001600030636954991L, -1120407259932030736L, 5403107271239823178L, 6686322015684031068L, 4058988067861570616L, 6295760635413066071L, 8296003004635989875L, -7537632343013109353L, -3769964635360517173L, -6834911189133591391L, -1704683253982847768L, 4493856998239772990L, -7572102532756415082L, 6998171455993338977L, 980818826006005261L, 1124655833395660047L, -2284543748113461024L, 8987713996479545980L, 8153300676200547441L, -3735201893769303348L, -8006428339051177840L, 434895353419662598L, -579835149127712265L, 2029494033930785308L, -4450402351443893310L, 7677477791710076778L, -5855223803629012562L, 7566728866842202217L, 1711271726969426199L, -7389870044988483431L, 4202818495260665658L, 2879999983209068839L, -2769352889281726247L, -1450663673343437845L, 3163161584953897771L, 2483547398480343842L, -3313287323587724334L, -6252807794495754071L, 542543608168745223L, 3729528769579755315L, 3309232816882628141L, 4348900688113705532L, 1565191715993326101L, -3915492708847116087L, -8692832070477198969L, -6147383808400293974L, 5825556169273407568L, -6544980976428025179L, 254887203023785731L, 6433976973855488089L, 689784669600907273L, 1883411841077745434L, 7282450178597640805L, -2917115187306352169L, -8875097578458069372L, -3457687298110670640L, -9017228160711801982L, 3018198660477530153L, 6540239822830991194L, 2174450344056852766L, 8880331879127370619L, -6289523186882315096L, 7857763023247300205L, 3198204697715948076L };
/*  10:181 */   private static final long[] T1 = new long[T0.length];
/*  11:182 */   private static final long[] T2 = new long[T0.length];
/*  12:183 */   private static final long[] T3 = new long[T0.length];
/*  13:184 */   private static final long[] T4 = new long[T0.length];
/*  14:185 */   private static final long[] T5 = new long[T0.length];
/*  15:186 */   private static final long[] T6 = new long[T0.length];
/*  16:187 */   private static final long[] T7 = new long[T0.length];
/*  17:    */   
/*  18:    */   static
/*  19:    */   {
/*  20:190 */     for (int i = 0; i < T0.length; i++)
/*  21:    */     {
/*  22:191 */       long l = T0[i];
/*  23:192 */       T1[i] = circularLeft(l, 56);
/*  24:193 */       T2[i] = circularLeft(l, 48);
/*  25:194 */       T3[i] = circularLeft(l, 40);
/*  26:195 */       T4[i] = circularLeft(l, 32);
/*  27:196 */       T5[i] = circularLeft(l, 24);
/*  28:197 */       T6[i] = circularLeft(l, 16);
/*  29:198 */       T7[i] = circularLeft(l, 8);
/*  30:    */     }
/*  31:    */   }
/*  32:    */   
/*  33:    */   public int getBlockLength()
/*  34:    */   {
/*  35:227 */     return 64;
/*  36:    */   }
/*  37:    */   
/*  38:    */   protected Digest copyState(GroestlSmallCore paramGroestlSmallCore)
/*  39:    */   {
/*  40:233 */     System.arraycopy(this.H, 0, paramGroestlSmallCore.H, 0, this.H.length);
/*  41:234 */     return super.copyState(paramGroestlSmallCore);
/*  42:    */   }
/*  43:    */   
/*  44:    */   protected void engineReset()
/*  45:    */   {
/*  46:240 */     for (int i = 0; i < 7; i++) {
/*  47:241 */       this.H[i] = 0L;
/*  48:    */     }
/*  49:242 */     this.H[7] = (getDigestLength() << 3);
/*  50:    */   }
/*  51:    */   
/*  52:    */   protected void doPadding(byte[] paramArrayOfByte, int paramInt)
/*  53:    */   {
/*  54:248 */     byte[] arrayOfByte = getBlockBuffer();
/*  55:249 */     int i = flush();
/*  56:250 */     arrayOfByte[(i++)] = Byte.MIN_VALUE;
/*  57:251 */     long l = getBlockCount();
/*  58:252 */     if (i <= 56)
/*  59:    */     {
/*  60:253 */       for (j = i; j < 56; j++) {
/*  61:254 */         arrayOfByte[j] = 0;
/*  62:    */       }
/*  63:255 */       l += 1L;
/*  64:    */     }
/*  65:    */     else
/*  66:    */     {
/*  67:257 */       for (j = i; j < 64; j++) {
/*  68:258 */         arrayOfByte[j] = 0;
/*  69:    */       }
/*  70:259 */       processBlock(arrayOfByte);
/*  71:260 */       for (j = 0; j < 56; j++) {
/*  72:261 */         arrayOfByte[j] = 0;
/*  73:    */       }
/*  74:262 */       l += 2L;
/*  75:    */     }
/*  76:264 */     encodeBELong(l, arrayOfByte, 56);
/*  77:265 */     processBlock(arrayOfByte);
/*  78:266 */     System.arraycopy(this.H, 0, this.G, 0, this.H.length);
/*  79:267 */     doPermP(this.G);
/*  80:268 */     for (int j = 0; j < 4; j++) {
/*  81:269 */       encodeBELong(this.H[(j + 4)] ^ this.G[(j + 4)], arrayOfByte, 8 * j);
/*  82:    */     }
/*  83:270 */     j = getDigestLength();
/*  84:271 */     System.arraycopy(arrayOfByte, 32 - j, paramArrayOfByte, paramInt, j);
/*  85:    */   }
/*  86:    */   
/*  87:    */   protected void doInit()
/*  88:    */   {
/*  89:278 */     this.H = new long[8];
/*  90:279 */     this.G = new long[8];
/*  91:280 */     this.M = new long[8];
/*  92:281 */     engineReset();
/*  93:    */   }
/*  94:    */   
/*  95:    */   private static final void encodeBELong(long paramLong, byte[] paramArrayOfByte, int paramInt)
/*  96:    */   {
/*  97:295 */     paramArrayOfByte[(paramInt + 0)] = ((byte)(int)(paramLong >>> 56));
/*  98:296 */     paramArrayOfByte[(paramInt + 1)] = ((byte)(int)(paramLong >>> 48));
/*  99:297 */     paramArrayOfByte[(paramInt + 2)] = ((byte)(int)(paramLong >>> 40));
/* 100:298 */     paramArrayOfByte[(paramInt + 3)] = ((byte)(int)(paramLong >>> 32));
/* 101:299 */     paramArrayOfByte[(paramInt + 4)] = ((byte)(int)(paramLong >>> 24));
/* 102:300 */     paramArrayOfByte[(paramInt + 5)] = ((byte)(int)(paramLong >>> 16));
/* 103:301 */     paramArrayOfByte[(paramInt + 6)] = ((byte)(int)(paramLong >>> 8));
/* 104:302 */     paramArrayOfByte[(paramInt + 7)] = ((byte)(int)paramLong);
/* 105:    */   }
/* 106:    */   
/* 107:    */   private static final long decodeBELong(byte[] paramArrayOfByte, int paramInt)
/* 108:    */   {
/* 109:315 */     return (paramArrayOfByte[paramInt] & 0xFF) << 56 | (paramArrayOfByte[(paramInt + 1)] & 0xFF) << 48 | (paramArrayOfByte[(paramInt + 2)] & 0xFF) << 40 | (paramArrayOfByte[(paramInt + 3)] & 0xFF) << 32 | (paramArrayOfByte[(paramInt + 4)] & 0xFF) << 24 | (paramArrayOfByte[(paramInt + 5)] & 0xFF) << 16 | (paramArrayOfByte[(paramInt + 6)] & 0xFF) << 8 | paramArrayOfByte[(paramInt + 7)] & 0xFF;
/* 110:    */   }
/* 111:    */   
/* 112:    */   private static long circularLeft(long paramLong, int paramInt)
/* 113:    */   {
/* 114:336 */     return paramLong << paramInt | paramLong >>> 64 - paramInt;
/* 115:    */   }
/* 116:    */   
/* 117:    */   private void doPermP(long[] paramArrayOfLong)
/* 118:    */   {
/* 119:341 */     for (int i = 0; i < 10; i += 2)
/* 120:    */     {
/* 121:342 */       paramArrayOfLong[0] ^= i << 56;
/* 122:343 */       paramArrayOfLong[1] ^= 16 + i << 56;
/* 123:344 */       paramArrayOfLong[2] ^= 32 + i << 56;
/* 124:345 */       paramArrayOfLong[3] ^= 48 + i << 56;
/* 125:346 */       paramArrayOfLong[4] ^= 64 + i << 56;
/* 126:347 */       paramArrayOfLong[5] ^= 80 + i << 56;
/* 127:348 */       paramArrayOfLong[6] ^= 96 + i << 56;
/* 128:349 */       paramArrayOfLong[7] ^= 112 + i << 56;
/* 129:350 */       long l1 = T0[((int)(paramArrayOfLong[0] >>> 56))] ^ T1[((int)(paramArrayOfLong[1] >>> 48) & 0xFF)] ^ T2[((int)(paramArrayOfLong[2] >>> 40) & 0xFF)] ^ T3[((int)(paramArrayOfLong[3] >>> 32) & 0xFF)] ^ T4[((int)paramArrayOfLong[4] >>> 24)] ^ T5[((int)paramArrayOfLong[5] >>> 16 & 0xFF)] ^ T6[((int)paramArrayOfLong[6] >>> 8 & 0xFF)] ^ T7[((int)paramArrayOfLong[7] & 0xFF)];
/* 130:    */       
/* 131:    */ 
/* 132:    */ 
/* 133:    */ 
/* 134:    */ 
/* 135:    */ 
/* 136:    */ 
/* 137:358 */       long l2 = T0[((int)(paramArrayOfLong[1] >>> 56))] ^ T1[((int)(paramArrayOfLong[2] >>> 48) & 0xFF)] ^ T2[((int)(paramArrayOfLong[3] >>> 40) & 0xFF)] ^ T3[((int)(paramArrayOfLong[4] >>> 32) & 0xFF)] ^ T4[((int)paramArrayOfLong[5] >>> 24)] ^ T5[((int)paramArrayOfLong[6] >>> 16 & 0xFF)] ^ T6[((int)paramArrayOfLong[7] >>> 8 & 0xFF)] ^ T7[((int)paramArrayOfLong[0] & 0xFF)];
/* 138:    */       
/* 139:    */ 
/* 140:    */ 
/* 141:    */ 
/* 142:    */ 
/* 143:    */ 
/* 144:    */ 
/* 145:366 */       long l3 = T0[((int)(paramArrayOfLong[2] >>> 56))] ^ T1[((int)(paramArrayOfLong[3] >>> 48) & 0xFF)] ^ T2[((int)(paramArrayOfLong[4] >>> 40) & 0xFF)] ^ T3[((int)(paramArrayOfLong[5] >>> 32) & 0xFF)] ^ T4[((int)paramArrayOfLong[6] >>> 24)] ^ T5[((int)paramArrayOfLong[7] >>> 16 & 0xFF)] ^ T6[((int)paramArrayOfLong[0] >>> 8 & 0xFF)] ^ T7[((int)paramArrayOfLong[1] & 0xFF)];
/* 146:    */       
/* 147:    */ 
/* 148:    */ 
/* 149:    */ 
/* 150:    */ 
/* 151:    */ 
/* 152:    */ 
/* 153:374 */       long l4 = T0[((int)(paramArrayOfLong[3] >>> 56))] ^ T1[((int)(paramArrayOfLong[4] >>> 48) & 0xFF)] ^ T2[((int)(paramArrayOfLong[5] >>> 40) & 0xFF)] ^ T3[((int)(paramArrayOfLong[6] >>> 32) & 0xFF)] ^ T4[((int)paramArrayOfLong[7] >>> 24)] ^ T5[((int)paramArrayOfLong[0] >>> 16 & 0xFF)] ^ T6[((int)paramArrayOfLong[1] >>> 8 & 0xFF)] ^ T7[((int)paramArrayOfLong[2] & 0xFF)];
/* 154:    */       
/* 155:    */ 
/* 156:    */ 
/* 157:    */ 
/* 158:    */ 
/* 159:    */ 
/* 160:    */ 
/* 161:382 */       long l5 = T0[((int)(paramArrayOfLong[4] >>> 56))] ^ T1[((int)(paramArrayOfLong[5] >>> 48) & 0xFF)] ^ T2[((int)(paramArrayOfLong[6] >>> 40) & 0xFF)] ^ T3[((int)(paramArrayOfLong[7] >>> 32) & 0xFF)] ^ T4[((int)paramArrayOfLong[0] >>> 24)] ^ T5[((int)paramArrayOfLong[1] >>> 16 & 0xFF)] ^ T6[((int)paramArrayOfLong[2] >>> 8 & 0xFF)] ^ T7[((int)paramArrayOfLong[3] & 0xFF)];
/* 162:    */       
/* 163:    */ 
/* 164:    */ 
/* 165:    */ 
/* 166:    */ 
/* 167:    */ 
/* 168:    */ 
/* 169:390 */       long l6 = T0[((int)(paramArrayOfLong[5] >>> 56))] ^ T1[((int)(paramArrayOfLong[6] >>> 48) & 0xFF)] ^ T2[((int)(paramArrayOfLong[7] >>> 40) & 0xFF)] ^ T3[((int)(paramArrayOfLong[0] >>> 32) & 0xFF)] ^ T4[((int)paramArrayOfLong[1] >>> 24)] ^ T5[((int)paramArrayOfLong[2] >>> 16 & 0xFF)] ^ T6[((int)paramArrayOfLong[3] >>> 8 & 0xFF)] ^ T7[((int)paramArrayOfLong[4] & 0xFF)];
/* 170:    */       
/* 171:    */ 
/* 172:    */ 
/* 173:    */ 
/* 174:    */ 
/* 175:    */ 
/* 176:    */ 
/* 177:398 */       long l7 = T0[((int)(paramArrayOfLong[6] >>> 56))] ^ T1[((int)(paramArrayOfLong[7] >>> 48) & 0xFF)] ^ T2[((int)(paramArrayOfLong[0] >>> 40) & 0xFF)] ^ T3[((int)(paramArrayOfLong[1] >>> 32) & 0xFF)] ^ T4[((int)paramArrayOfLong[2] >>> 24)] ^ T5[((int)paramArrayOfLong[3] >>> 16 & 0xFF)] ^ T6[((int)paramArrayOfLong[4] >>> 8 & 0xFF)] ^ T7[((int)paramArrayOfLong[5] & 0xFF)];
/* 178:    */       
/* 179:    */ 
/* 180:    */ 
/* 181:    */ 
/* 182:    */ 
/* 183:    */ 
/* 184:    */ 
/* 185:406 */       long l8 = T0[((int)(paramArrayOfLong[7] >>> 56))] ^ T1[((int)(paramArrayOfLong[0] >>> 48) & 0xFF)] ^ T2[((int)(paramArrayOfLong[1] >>> 40) & 0xFF)] ^ T3[((int)(paramArrayOfLong[2] >>> 32) & 0xFF)] ^ T4[((int)paramArrayOfLong[3] >>> 24)] ^ T5[((int)paramArrayOfLong[4] >>> 16 & 0xFF)] ^ T6[((int)paramArrayOfLong[5] >>> 8 & 0xFF)] ^ T7[((int)paramArrayOfLong[6] & 0xFF)];
/* 186:    */       
/* 187:    */ 
/* 188:    */ 
/* 189:    */ 
/* 190:    */ 
/* 191:    */ 
/* 192:    */ 
/* 193:414 */       l1 ^= i + 1 << 56;
/* 194:415 */       l2 ^= 16 + (i + 1) << 56;
/* 195:416 */       l3 ^= 32 + (i + 1) << 56;
/* 196:417 */       l4 ^= 48 + (i + 1) << 56;
/* 197:418 */       l5 ^= 64 + (i + 1) << 56;
/* 198:419 */       l6 ^= 80 + (i + 1) << 56;
/* 199:420 */       l7 ^= 96 + (i + 1) << 56;
/* 200:421 */       l8 ^= 112 + (i + 1) << 56;
/* 201:422 */       paramArrayOfLong[0] = (T0[((int)(l1 >>> 56))] ^ T1[((int)(l2 >>> 48) & 0xFF)] ^ T2[((int)(l3 >>> 40) & 0xFF)] ^ T3[((int)(l4 >>> 32) & 0xFF)] ^ T4[((int)l5 >>> 24)] ^ T5[((int)l6 >>> 16 & 0xFF)] ^ T6[((int)l7 >>> 8 & 0xFF)] ^ T7[((int)l8 & 0xFF)]);
/* 202:    */       
/* 203:    */ 
/* 204:    */ 
/* 205:    */ 
/* 206:    */ 
/* 207:    */ 
/* 208:    */ 
/* 209:430 */       paramArrayOfLong[1] = (T0[((int)(l2 >>> 56))] ^ T1[((int)(l3 >>> 48) & 0xFF)] ^ T2[((int)(l4 >>> 40) & 0xFF)] ^ T3[((int)(l5 >>> 32) & 0xFF)] ^ T4[((int)l6 >>> 24)] ^ T5[((int)l7 >>> 16 & 0xFF)] ^ T6[((int)l8 >>> 8 & 0xFF)] ^ T7[((int)l1 & 0xFF)]);
/* 210:    */       
/* 211:    */ 
/* 212:    */ 
/* 213:    */ 
/* 214:    */ 
/* 215:    */ 
/* 216:    */ 
/* 217:438 */       paramArrayOfLong[2] = (T0[((int)(l3 >>> 56))] ^ T1[((int)(l4 >>> 48) & 0xFF)] ^ T2[((int)(l5 >>> 40) & 0xFF)] ^ T3[((int)(l6 >>> 32) & 0xFF)] ^ T4[((int)l7 >>> 24)] ^ T5[((int)l8 >>> 16 & 0xFF)] ^ T6[((int)l1 >>> 8 & 0xFF)] ^ T7[((int)l2 & 0xFF)]);
/* 218:    */       
/* 219:    */ 
/* 220:    */ 
/* 221:    */ 
/* 222:    */ 
/* 223:    */ 
/* 224:    */ 
/* 225:446 */       paramArrayOfLong[3] = (T0[((int)(l4 >>> 56))] ^ T1[((int)(l5 >>> 48) & 0xFF)] ^ T2[((int)(l6 >>> 40) & 0xFF)] ^ T3[((int)(l7 >>> 32) & 0xFF)] ^ T4[((int)l8 >>> 24)] ^ T5[((int)l1 >>> 16 & 0xFF)] ^ T6[((int)l2 >>> 8 & 0xFF)] ^ T7[((int)l3 & 0xFF)]);
/* 226:    */       
/* 227:    */ 
/* 228:    */ 
/* 229:    */ 
/* 230:    */ 
/* 231:    */ 
/* 232:    */ 
/* 233:454 */       paramArrayOfLong[4] = (T0[((int)(l5 >>> 56))] ^ T1[((int)(l6 >>> 48) & 0xFF)] ^ T2[((int)(l7 >>> 40) & 0xFF)] ^ T3[((int)(l8 >>> 32) & 0xFF)] ^ T4[((int)l1 >>> 24)] ^ T5[((int)l2 >>> 16 & 0xFF)] ^ T6[((int)l3 >>> 8 & 0xFF)] ^ T7[((int)l4 & 0xFF)]);
/* 234:    */       
/* 235:    */ 
/* 236:    */ 
/* 237:    */ 
/* 238:    */ 
/* 239:    */ 
/* 240:    */ 
/* 241:462 */       paramArrayOfLong[5] = (T0[((int)(l6 >>> 56))] ^ T1[((int)(l7 >>> 48) & 0xFF)] ^ T2[((int)(l8 >>> 40) & 0xFF)] ^ T3[((int)(l1 >>> 32) & 0xFF)] ^ T4[((int)l2 >>> 24)] ^ T5[((int)l3 >>> 16 & 0xFF)] ^ T6[((int)l4 >>> 8 & 0xFF)] ^ T7[((int)l5 & 0xFF)]);
/* 242:    */       
/* 243:    */ 
/* 244:    */ 
/* 245:    */ 
/* 246:    */ 
/* 247:    */ 
/* 248:    */ 
/* 249:470 */       paramArrayOfLong[6] = (T0[((int)(l7 >>> 56))] ^ T1[((int)(l8 >>> 48) & 0xFF)] ^ T2[((int)(l1 >>> 40) & 0xFF)] ^ T3[((int)(l2 >>> 32) & 0xFF)] ^ T4[((int)l3 >>> 24)] ^ T5[((int)l4 >>> 16 & 0xFF)] ^ T6[((int)l5 >>> 8 & 0xFF)] ^ T7[((int)l6 & 0xFF)]);
/* 250:    */       
/* 251:    */ 
/* 252:    */ 
/* 253:    */ 
/* 254:    */ 
/* 255:    */ 
/* 256:    */ 
/* 257:478 */       paramArrayOfLong[7] = (T0[((int)(l8 >>> 56))] ^ T1[((int)(l1 >>> 48) & 0xFF)] ^ T2[((int)(l2 >>> 40) & 0xFF)] ^ T3[((int)(l3 >>> 32) & 0xFF)] ^ T4[((int)l4 >>> 24)] ^ T5[((int)l5 >>> 16 & 0xFF)] ^ T6[((int)l6 >>> 8 & 0xFF)] ^ T7[((int)l7 & 0xFF)]);
/* 258:    */     }
/* 259:    */   }
/* 260:    */   
/* 261:    */   private void doPermQ(long[] paramArrayOfLong)
/* 262:    */   {
/* 263:491 */     for (int i = 0; i < 10; i += 2)
/* 264:    */     {
/* 265:492 */       paramArrayOfLong[0] ^= i ^ 0xFFFFFFFF;
/* 266:493 */       paramArrayOfLong[1] ^= i ^ 0xFFFFFFEF;
/* 267:494 */       paramArrayOfLong[2] ^= i ^ 0xFFFFFFDF;
/* 268:495 */       paramArrayOfLong[3] ^= i ^ 0xFFFFFFCF;
/* 269:496 */       paramArrayOfLong[4] ^= i ^ 0xFFFFFFBF;
/* 270:497 */       paramArrayOfLong[5] ^= i ^ 0xFFFFFFAF;
/* 271:498 */       paramArrayOfLong[6] ^= i ^ 0xFFFFFF9F;
/* 272:499 */       paramArrayOfLong[7] ^= i ^ 0xFFFFFF8F;
/* 273:500 */       long l1 = T0[((int)(paramArrayOfLong[1] >>> 56))] ^ T1[((int)(paramArrayOfLong[3] >>> 48) & 0xFF)] ^ T2[((int)(paramArrayOfLong[5] >>> 40) & 0xFF)] ^ T3[((int)(paramArrayOfLong[7] >>> 32) & 0xFF)] ^ T4[((int)paramArrayOfLong[0] >>> 24)] ^ T5[((int)paramArrayOfLong[2] >>> 16 & 0xFF)] ^ T6[((int)paramArrayOfLong[4] >>> 8 & 0xFF)] ^ T7[((int)paramArrayOfLong[6] & 0xFF)];
/* 274:    */       
/* 275:    */ 
/* 276:    */ 
/* 277:    */ 
/* 278:    */ 
/* 279:    */ 
/* 280:    */ 
/* 281:508 */       long l2 = T0[((int)(paramArrayOfLong[2] >>> 56))] ^ T1[((int)(paramArrayOfLong[4] >>> 48) & 0xFF)] ^ T2[((int)(paramArrayOfLong[6] >>> 40) & 0xFF)] ^ T3[((int)(paramArrayOfLong[0] >>> 32) & 0xFF)] ^ T4[((int)paramArrayOfLong[1] >>> 24)] ^ T5[((int)paramArrayOfLong[3] >>> 16 & 0xFF)] ^ T6[((int)paramArrayOfLong[5] >>> 8 & 0xFF)] ^ T7[((int)paramArrayOfLong[7] & 0xFF)];
/* 282:    */       
/* 283:    */ 
/* 284:    */ 
/* 285:    */ 
/* 286:    */ 
/* 287:    */ 
/* 288:    */ 
/* 289:516 */       long l3 = T0[((int)(paramArrayOfLong[3] >>> 56))] ^ T1[((int)(paramArrayOfLong[5] >>> 48) & 0xFF)] ^ T2[((int)(paramArrayOfLong[7] >>> 40) & 0xFF)] ^ T3[((int)(paramArrayOfLong[1] >>> 32) & 0xFF)] ^ T4[((int)paramArrayOfLong[2] >>> 24)] ^ T5[((int)paramArrayOfLong[4] >>> 16 & 0xFF)] ^ T6[((int)paramArrayOfLong[6] >>> 8 & 0xFF)] ^ T7[((int)paramArrayOfLong[0] & 0xFF)];
/* 290:    */       
/* 291:    */ 
/* 292:    */ 
/* 293:    */ 
/* 294:    */ 
/* 295:    */ 
/* 296:    */ 
/* 297:524 */       long l4 = T0[((int)(paramArrayOfLong[4] >>> 56))] ^ T1[((int)(paramArrayOfLong[6] >>> 48) & 0xFF)] ^ T2[((int)(paramArrayOfLong[0] >>> 40) & 0xFF)] ^ T3[((int)(paramArrayOfLong[2] >>> 32) & 0xFF)] ^ T4[((int)paramArrayOfLong[3] >>> 24)] ^ T5[((int)paramArrayOfLong[5] >>> 16 & 0xFF)] ^ T6[((int)paramArrayOfLong[7] >>> 8 & 0xFF)] ^ T7[((int)paramArrayOfLong[1] & 0xFF)];
/* 298:    */       
/* 299:    */ 
/* 300:    */ 
/* 301:    */ 
/* 302:    */ 
/* 303:    */ 
/* 304:    */ 
/* 305:532 */       long l5 = T0[((int)(paramArrayOfLong[5] >>> 56))] ^ T1[((int)(paramArrayOfLong[7] >>> 48) & 0xFF)] ^ T2[((int)(paramArrayOfLong[1] >>> 40) & 0xFF)] ^ T3[((int)(paramArrayOfLong[3] >>> 32) & 0xFF)] ^ T4[((int)paramArrayOfLong[4] >>> 24)] ^ T5[((int)paramArrayOfLong[6] >>> 16 & 0xFF)] ^ T6[((int)paramArrayOfLong[0] >>> 8 & 0xFF)] ^ T7[((int)paramArrayOfLong[2] & 0xFF)];
/* 306:    */       
/* 307:    */ 
/* 308:    */ 
/* 309:    */ 
/* 310:    */ 
/* 311:    */ 
/* 312:    */ 
/* 313:540 */       long l6 = T0[((int)(paramArrayOfLong[6] >>> 56))] ^ T1[((int)(paramArrayOfLong[0] >>> 48) & 0xFF)] ^ T2[((int)(paramArrayOfLong[2] >>> 40) & 0xFF)] ^ T3[((int)(paramArrayOfLong[4] >>> 32) & 0xFF)] ^ T4[((int)paramArrayOfLong[5] >>> 24)] ^ T5[((int)paramArrayOfLong[7] >>> 16 & 0xFF)] ^ T6[((int)paramArrayOfLong[1] >>> 8 & 0xFF)] ^ T7[((int)paramArrayOfLong[3] & 0xFF)];
/* 314:    */       
/* 315:    */ 
/* 316:    */ 
/* 317:    */ 
/* 318:    */ 
/* 319:    */ 
/* 320:    */ 
/* 321:548 */       long l7 = T0[((int)(paramArrayOfLong[7] >>> 56))] ^ T1[((int)(paramArrayOfLong[1] >>> 48) & 0xFF)] ^ T2[((int)(paramArrayOfLong[3] >>> 40) & 0xFF)] ^ T3[((int)(paramArrayOfLong[5] >>> 32) & 0xFF)] ^ T4[((int)paramArrayOfLong[6] >>> 24)] ^ T5[((int)paramArrayOfLong[0] >>> 16 & 0xFF)] ^ T6[((int)paramArrayOfLong[2] >>> 8 & 0xFF)] ^ T7[((int)paramArrayOfLong[4] & 0xFF)];
/* 322:    */       
/* 323:    */ 
/* 324:    */ 
/* 325:    */ 
/* 326:    */ 
/* 327:    */ 
/* 328:    */ 
/* 329:556 */       long l8 = T0[((int)(paramArrayOfLong[0] >>> 56))] ^ T1[((int)(paramArrayOfLong[2] >>> 48) & 0xFF)] ^ T2[((int)(paramArrayOfLong[4] >>> 40) & 0xFF)] ^ T3[((int)(paramArrayOfLong[6] >>> 32) & 0xFF)] ^ T4[((int)paramArrayOfLong[7] >>> 24)] ^ T5[((int)paramArrayOfLong[1] >>> 16 & 0xFF)] ^ T6[((int)paramArrayOfLong[3] >>> 8 & 0xFF)] ^ T7[((int)paramArrayOfLong[5] & 0xFF)];
/* 330:    */       
/* 331:    */ 
/* 332:    */ 
/* 333:    */ 
/* 334:    */ 
/* 335:    */ 
/* 336:    */ 
/* 337:564 */       l1 ^= i + 1 ^ 0xFFFFFFFF;
/* 338:565 */       l2 ^= i + 1 ^ 0xFFFFFFEF;
/* 339:566 */       l3 ^= i + 1 ^ 0xFFFFFFDF;
/* 340:567 */       l4 ^= i + 1 ^ 0xFFFFFFCF;
/* 341:568 */       l5 ^= i + 1 ^ 0xFFFFFFBF;
/* 342:569 */       l6 ^= i + 1 ^ 0xFFFFFFAF;
/* 343:570 */       l7 ^= i + 1 ^ 0xFFFFFF9F;
/* 344:571 */       l8 ^= i + 1 ^ 0xFFFFFF8F;
/* 345:572 */       paramArrayOfLong[0] = (T0[((int)(l2 >>> 56))] ^ T1[((int)(l4 >>> 48) & 0xFF)] ^ T2[((int)(l6 >>> 40) & 0xFF)] ^ T3[((int)(l8 >>> 32) & 0xFF)] ^ T4[((int)l1 >>> 24)] ^ T5[((int)l3 >>> 16 & 0xFF)] ^ T6[((int)l5 >>> 8 & 0xFF)] ^ T7[((int)l7 & 0xFF)]);
/* 346:    */       
/* 347:    */ 
/* 348:    */ 
/* 349:    */ 
/* 350:    */ 
/* 351:    */ 
/* 352:    */ 
/* 353:580 */       paramArrayOfLong[1] = (T0[((int)(l3 >>> 56))] ^ T1[((int)(l5 >>> 48) & 0xFF)] ^ T2[((int)(l7 >>> 40) & 0xFF)] ^ T3[((int)(l1 >>> 32) & 0xFF)] ^ T4[((int)l2 >>> 24)] ^ T5[((int)l4 >>> 16 & 0xFF)] ^ T6[((int)l6 >>> 8 & 0xFF)] ^ T7[((int)l8 & 0xFF)]);
/* 354:    */       
/* 355:    */ 
/* 356:    */ 
/* 357:    */ 
/* 358:    */ 
/* 359:    */ 
/* 360:    */ 
/* 361:588 */       paramArrayOfLong[2] = (T0[((int)(l4 >>> 56))] ^ T1[((int)(l6 >>> 48) & 0xFF)] ^ T2[((int)(l8 >>> 40) & 0xFF)] ^ T3[((int)(l2 >>> 32) & 0xFF)] ^ T4[((int)l3 >>> 24)] ^ T5[((int)l5 >>> 16 & 0xFF)] ^ T6[((int)l7 >>> 8 & 0xFF)] ^ T7[((int)l1 & 0xFF)]);
/* 362:    */       
/* 363:    */ 
/* 364:    */ 
/* 365:    */ 
/* 366:    */ 
/* 367:    */ 
/* 368:    */ 
/* 369:596 */       paramArrayOfLong[3] = (T0[((int)(l5 >>> 56))] ^ T1[((int)(l7 >>> 48) & 0xFF)] ^ T2[((int)(l1 >>> 40) & 0xFF)] ^ T3[((int)(l3 >>> 32) & 0xFF)] ^ T4[((int)l4 >>> 24)] ^ T5[((int)l6 >>> 16 & 0xFF)] ^ T6[((int)l8 >>> 8 & 0xFF)] ^ T7[((int)l2 & 0xFF)]);
/* 370:    */       
/* 371:    */ 
/* 372:    */ 
/* 373:    */ 
/* 374:    */ 
/* 375:    */ 
/* 376:    */ 
/* 377:604 */       paramArrayOfLong[4] = (T0[((int)(l6 >>> 56))] ^ T1[((int)(l8 >>> 48) & 0xFF)] ^ T2[((int)(l2 >>> 40) & 0xFF)] ^ T3[((int)(l4 >>> 32) & 0xFF)] ^ T4[((int)l5 >>> 24)] ^ T5[((int)l7 >>> 16 & 0xFF)] ^ T6[((int)l1 >>> 8 & 0xFF)] ^ T7[((int)l3 & 0xFF)]);
/* 378:    */       
/* 379:    */ 
/* 380:    */ 
/* 381:    */ 
/* 382:    */ 
/* 383:    */ 
/* 384:    */ 
/* 385:612 */       paramArrayOfLong[5] = (T0[((int)(l7 >>> 56))] ^ T1[((int)(l1 >>> 48) & 0xFF)] ^ T2[((int)(l3 >>> 40) & 0xFF)] ^ T3[((int)(l5 >>> 32) & 0xFF)] ^ T4[((int)l6 >>> 24)] ^ T5[((int)l8 >>> 16 & 0xFF)] ^ T6[((int)l2 >>> 8 & 0xFF)] ^ T7[((int)l4 & 0xFF)]);
/* 386:    */       
/* 387:    */ 
/* 388:    */ 
/* 389:    */ 
/* 390:    */ 
/* 391:    */ 
/* 392:    */ 
/* 393:620 */       paramArrayOfLong[6] = (T0[((int)(l8 >>> 56))] ^ T1[((int)(l2 >>> 48) & 0xFF)] ^ T2[((int)(l4 >>> 40) & 0xFF)] ^ T3[((int)(l6 >>> 32) & 0xFF)] ^ T4[((int)l7 >>> 24)] ^ T5[((int)l1 >>> 16 & 0xFF)] ^ T6[((int)l3 >>> 8 & 0xFF)] ^ T7[((int)l5 & 0xFF)]);
/* 394:    */       
/* 395:    */ 
/* 396:    */ 
/* 397:    */ 
/* 398:    */ 
/* 399:    */ 
/* 400:    */ 
/* 401:628 */       paramArrayOfLong[7] = (T0[((int)(l1 >>> 56))] ^ T1[((int)(l3 >>> 48) & 0xFF)] ^ T2[((int)(l5 >>> 40) & 0xFF)] ^ T3[((int)(l7 >>> 32) & 0xFF)] ^ T4[((int)l8 >>> 24)] ^ T5[((int)l2 >>> 16 & 0xFF)] ^ T6[((int)l4 >>> 8 & 0xFF)] ^ T7[((int)l6 & 0xFF)]);
/* 402:    */     }
/* 403:    */   }
/* 404:    */   
/* 405:    */   protected void processBlock(byte[] paramArrayOfByte)
/* 406:    */   {
/* 407:642 */     for (int i = 0; i < 8; i++)
/* 408:    */     {
/* 409:643 */       this.M[i] = decodeBELong(paramArrayOfByte, i * 8);
/* 410:644 */       this.G[i] = (this.M[i] ^ this.H[i]);
/* 411:    */     }
/* 412:646 */     doPermP(this.G);
/* 413:647 */     doPermQ(this.M);
/* 414:648 */     for (i = 0; i < 8; i++) {
/* 415:649 */       this.H[i] ^= this.G[i] ^ this.M[i];
/* 416:    */     }
/* 417:    */   }
/* 418:    */   
/* 419:    */   public String toString()
/* 420:    */   {
/* 421:655 */     return "Groestl-" + (getDigestLength() << 3);
/* 422:    */   }
/* 423:    */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     fr.cryptohash.GroestlSmallCore
 * JD-Core Version:    0.7.1
 */