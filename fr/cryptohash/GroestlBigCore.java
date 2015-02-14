/*   1:    */ package fr.cryptohash;
/*   2:    */ 
/*   3:    */ abstract class GroestlBigCore
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
/*  35:227 */     return 128;
/*  36:    */   }
/*  37:    */   
/*  38:    */   protected Digest copyState(GroestlBigCore paramGroestlBigCore)
/*  39:    */   {
/*  40:233 */     System.arraycopy(this.H, 0, paramGroestlBigCore.H, 0, this.H.length);
/*  41:234 */     return super.copyState(paramGroestlBigCore);
/*  42:    */   }
/*  43:    */   
/*  44:    */   protected void engineReset()
/*  45:    */   {
/*  46:240 */     for (int i = 0; i < 15; i++) {
/*  47:241 */       this.H[i] = 0L;
/*  48:    */     }
/*  49:242 */     this.H[15] = (getDigestLength() << 3);
/*  50:    */   }
/*  51:    */   
/*  52:    */   protected void doPadding(byte[] paramArrayOfByte, int paramInt)
/*  53:    */   {
/*  54:248 */     byte[] arrayOfByte = getBlockBuffer();
/*  55:249 */     int i = flush();
/*  56:250 */     arrayOfByte[(i++)] = Byte.MIN_VALUE;
/*  57:251 */     long l = getBlockCount();
/*  58:252 */     if (i <= 120)
/*  59:    */     {
/*  60:253 */       for (j = i; j < 120; j++) {
/*  61:254 */         arrayOfByte[j] = 0;
/*  62:    */       }
/*  63:255 */       l += 1L;
/*  64:    */     }
/*  65:    */     else
/*  66:    */     {
/*  67:257 */       for (j = i; j < 128; j++) {
/*  68:258 */         arrayOfByte[j] = 0;
/*  69:    */       }
/*  70:259 */       processBlock(arrayOfByte);
/*  71:260 */       for (j = 0; j < 120; j++) {
/*  72:261 */         arrayOfByte[j] = 0;
/*  73:    */       }
/*  74:262 */       l += 2L;
/*  75:    */     }
/*  76:264 */     encodeBELong(l, arrayOfByte, 120);
/*  77:265 */     processBlock(arrayOfByte);
/*  78:266 */     System.arraycopy(this.H, 0, this.G, 0, this.H.length);
/*  79:267 */     doPermP(this.G);
/*  80:268 */     for (int j = 0; j < 8; j++) {
/*  81:269 */       encodeBELong(this.H[(j + 8)] ^ this.G[(j + 8)], arrayOfByte, 8 * j);
/*  82:    */     }
/*  83:270 */     j = getDigestLength();
/*  84:271 */     System.arraycopy(arrayOfByte, 64 - j, paramArrayOfByte, paramInt, j);
/*  85:    */   }
/*  86:    */   
/*  87:    */   protected void doInit()
/*  88:    */   {
/*  89:278 */     this.H = new long[16];
/*  90:279 */     this.G = new long[16];
/*  91:280 */     this.M = new long[16];
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
/* 119:341 */     for (int i = 0; i < 14; i++)
/* 120:    */     {
/* 121:342 */       paramArrayOfLong[0] ^= i << 56;
/* 122:343 */       paramArrayOfLong[1] ^= 16 + i << 56;
/* 123:344 */       paramArrayOfLong[2] ^= 32 + i << 56;
/* 124:345 */       paramArrayOfLong[3] ^= 48 + i << 56;
/* 125:346 */       paramArrayOfLong[4] ^= 64 + i << 56;
/* 126:347 */       paramArrayOfLong[5] ^= 80 + i << 56;
/* 127:348 */       paramArrayOfLong[6] ^= 96 + i << 56;
/* 128:349 */       paramArrayOfLong[7] ^= 112 + i << 56;
/* 129:350 */       paramArrayOfLong[8] ^= 128 + i << 56;
/* 130:351 */       paramArrayOfLong[9] ^= 144 + i << 56;
/* 131:352 */       paramArrayOfLong[10] ^= 160 + i << 56;
/* 132:353 */       paramArrayOfLong[11] ^= 176 + i << 56;
/* 133:354 */       paramArrayOfLong[12] ^= 192 + i << 56;
/* 134:355 */       paramArrayOfLong[13] ^= 208 + i << 56;
/* 135:356 */       paramArrayOfLong[14] ^= 224 + i << 56;
/* 136:357 */       paramArrayOfLong[15] ^= 240 + i << 56;
/* 137:358 */       long l1 = T0[((int)(paramArrayOfLong[0] >>> 56))] ^ T1[((int)(paramArrayOfLong[1] >>> 48) & 0xFF)] ^ T2[((int)(paramArrayOfLong[2] >>> 40) & 0xFF)] ^ T3[((int)(paramArrayOfLong[3] >>> 32) & 0xFF)] ^ T4[((int)paramArrayOfLong[4] >>> 24)] ^ T5[((int)paramArrayOfLong[5] >>> 16 & 0xFF)] ^ T6[((int)paramArrayOfLong[6] >>> 8 & 0xFF)] ^ T7[((int)paramArrayOfLong[11] & 0xFF)];
/* 138:    */       
/* 139:    */ 
/* 140:    */ 
/* 141:    */ 
/* 142:    */ 
/* 143:    */ 
/* 144:    */ 
/* 145:366 */       long l2 = T0[((int)(paramArrayOfLong[1] >>> 56))] ^ T1[((int)(paramArrayOfLong[2] >>> 48) & 0xFF)] ^ T2[((int)(paramArrayOfLong[3] >>> 40) & 0xFF)] ^ T3[((int)(paramArrayOfLong[4] >>> 32) & 0xFF)] ^ T4[((int)paramArrayOfLong[5] >>> 24)] ^ T5[((int)paramArrayOfLong[6] >>> 16 & 0xFF)] ^ T6[((int)paramArrayOfLong[7] >>> 8 & 0xFF)] ^ T7[((int)paramArrayOfLong[12] & 0xFF)];
/* 146:    */       
/* 147:    */ 
/* 148:    */ 
/* 149:    */ 
/* 150:    */ 
/* 151:    */ 
/* 152:    */ 
/* 153:374 */       long l3 = T0[((int)(paramArrayOfLong[2] >>> 56))] ^ T1[((int)(paramArrayOfLong[3] >>> 48) & 0xFF)] ^ T2[((int)(paramArrayOfLong[4] >>> 40) & 0xFF)] ^ T3[((int)(paramArrayOfLong[5] >>> 32) & 0xFF)] ^ T4[((int)paramArrayOfLong[6] >>> 24)] ^ T5[((int)paramArrayOfLong[7] >>> 16 & 0xFF)] ^ T6[((int)paramArrayOfLong[8] >>> 8 & 0xFF)] ^ T7[((int)paramArrayOfLong[13] & 0xFF)];
/* 154:    */       
/* 155:    */ 
/* 156:    */ 
/* 157:    */ 
/* 158:    */ 
/* 159:    */ 
/* 160:    */ 
/* 161:382 */       long l4 = T0[((int)(paramArrayOfLong[3] >>> 56))] ^ T1[((int)(paramArrayOfLong[4] >>> 48) & 0xFF)] ^ T2[((int)(paramArrayOfLong[5] >>> 40) & 0xFF)] ^ T3[((int)(paramArrayOfLong[6] >>> 32) & 0xFF)] ^ T4[((int)paramArrayOfLong[7] >>> 24)] ^ T5[((int)paramArrayOfLong[8] >>> 16 & 0xFF)] ^ T6[((int)paramArrayOfLong[9] >>> 8 & 0xFF)] ^ T7[((int)paramArrayOfLong[14] & 0xFF)];
/* 162:    */       
/* 163:    */ 
/* 164:    */ 
/* 165:    */ 
/* 166:    */ 
/* 167:    */ 
/* 168:    */ 
/* 169:390 */       long l5 = T0[((int)(paramArrayOfLong[4] >>> 56))] ^ T1[((int)(paramArrayOfLong[5] >>> 48) & 0xFF)] ^ T2[((int)(paramArrayOfLong[6] >>> 40) & 0xFF)] ^ T3[((int)(paramArrayOfLong[7] >>> 32) & 0xFF)] ^ T4[((int)paramArrayOfLong[8] >>> 24)] ^ T5[((int)paramArrayOfLong[9] >>> 16 & 0xFF)] ^ T6[((int)paramArrayOfLong[10] >>> 8 & 0xFF)] ^ T7[((int)paramArrayOfLong[15] & 0xFF)];
/* 170:    */       
/* 171:    */ 
/* 172:    */ 
/* 173:    */ 
/* 174:    */ 
/* 175:    */ 
/* 176:    */ 
/* 177:398 */       long l6 = T0[((int)(paramArrayOfLong[5] >>> 56))] ^ T1[((int)(paramArrayOfLong[6] >>> 48) & 0xFF)] ^ T2[((int)(paramArrayOfLong[7] >>> 40) & 0xFF)] ^ T3[((int)(paramArrayOfLong[8] >>> 32) & 0xFF)] ^ T4[((int)paramArrayOfLong[9] >>> 24)] ^ T5[((int)paramArrayOfLong[10] >>> 16 & 0xFF)] ^ T6[((int)paramArrayOfLong[11] >>> 8 & 0xFF)] ^ T7[((int)paramArrayOfLong[0] & 0xFF)];
/* 178:    */       
/* 179:    */ 
/* 180:    */ 
/* 181:    */ 
/* 182:    */ 
/* 183:    */ 
/* 184:    */ 
/* 185:406 */       long l7 = T0[((int)(paramArrayOfLong[6] >>> 56))] ^ T1[((int)(paramArrayOfLong[7] >>> 48) & 0xFF)] ^ T2[((int)(paramArrayOfLong[8] >>> 40) & 0xFF)] ^ T3[((int)(paramArrayOfLong[9] >>> 32) & 0xFF)] ^ T4[((int)paramArrayOfLong[10] >>> 24)] ^ T5[((int)paramArrayOfLong[11] >>> 16 & 0xFF)] ^ T6[((int)paramArrayOfLong[12] >>> 8 & 0xFF)] ^ T7[((int)paramArrayOfLong[1] & 0xFF)];
/* 186:    */       
/* 187:    */ 
/* 188:    */ 
/* 189:    */ 
/* 190:    */ 
/* 191:    */ 
/* 192:    */ 
/* 193:414 */       long l8 = T0[((int)(paramArrayOfLong[7] >>> 56))] ^ T1[((int)(paramArrayOfLong[8] >>> 48) & 0xFF)] ^ T2[((int)(paramArrayOfLong[9] >>> 40) & 0xFF)] ^ T3[((int)(paramArrayOfLong[10] >>> 32) & 0xFF)] ^ T4[((int)paramArrayOfLong[11] >>> 24)] ^ T5[((int)paramArrayOfLong[12] >>> 16 & 0xFF)] ^ T6[((int)paramArrayOfLong[13] >>> 8 & 0xFF)] ^ T7[((int)paramArrayOfLong[2] & 0xFF)];
/* 194:    */       
/* 195:    */ 
/* 196:    */ 
/* 197:    */ 
/* 198:    */ 
/* 199:    */ 
/* 200:    */ 
/* 201:422 */       long l9 = T0[((int)(paramArrayOfLong[8] >>> 56))] ^ T1[((int)(paramArrayOfLong[9] >>> 48) & 0xFF)] ^ T2[((int)(paramArrayOfLong[10] >>> 40) & 0xFF)] ^ T3[((int)(paramArrayOfLong[11] >>> 32) & 0xFF)] ^ T4[((int)paramArrayOfLong[12] >>> 24)] ^ T5[((int)paramArrayOfLong[13] >>> 16 & 0xFF)] ^ T6[((int)paramArrayOfLong[14] >>> 8 & 0xFF)] ^ T7[((int)paramArrayOfLong[3] & 0xFF)];
/* 202:    */       
/* 203:    */ 
/* 204:    */ 
/* 205:    */ 
/* 206:    */ 
/* 207:    */ 
/* 208:    */ 
/* 209:430 */       long l10 = T0[((int)(paramArrayOfLong[9] >>> 56))] ^ T1[((int)(paramArrayOfLong[10] >>> 48) & 0xFF)] ^ T2[((int)(paramArrayOfLong[11] >>> 40) & 0xFF)] ^ T3[((int)(paramArrayOfLong[12] >>> 32) & 0xFF)] ^ T4[((int)paramArrayOfLong[13] >>> 24)] ^ T5[((int)paramArrayOfLong[14] >>> 16 & 0xFF)] ^ T6[((int)paramArrayOfLong[15] >>> 8 & 0xFF)] ^ T7[((int)paramArrayOfLong[4] & 0xFF)];
/* 210:    */       
/* 211:    */ 
/* 212:    */ 
/* 213:    */ 
/* 214:    */ 
/* 215:    */ 
/* 216:    */ 
/* 217:438 */       long l11 = T0[((int)(paramArrayOfLong[10] >>> 56))] ^ T1[((int)(paramArrayOfLong[11] >>> 48) & 0xFF)] ^ T2[((int)(paramArrayOfLong[12] >>> 40) & 0xFF)] ^ T3[((int)(paramArrayOfLong[13] >>> 32) & 0xFF)] ^ T4[((int)paramArrayOfLong[14] >>> 24)] ^ T5[((int)paramArrayOfLong[15] >>> 16 & 0xFF)] ^ T6[((int)paramArrayOfLong[0] >>> 8 & 0xFF)] ^ T7[((int)paramArrayOfLong[5] & 0xFF)];
/* 218:    */       
/* 219:    */ 
/* 220:    */ 
/* 221:    */ 
/* 222:    */ 
/* 223:    */ 
/* 224:    */ 
/* 225:446 */       long l12 = T0[((int)(paramArrayOfLong[11] >>> 56))] ^ T1[((int)(paramArrayOfLong[12] >>> 48) & 0xFF)] ^ T2[((int)(paramArrayOfLong[13] >>> 40) & 0xFF)] ^ T3[((int)(paramArrayOfLong[14] >>> 32) & 0xFF)] ^ T4[((int)paramArrayOfLong[15] >>> 24)] ^ T5[((int)paramArrayOfLong[0] >>> 16 & 0xFF)] ^ T6[((int)paramArrayOfLong[1] >>> 8 & 0xFF)] ^ T7[((int)paramArrayOfLong[6] & 0xFF)];
/* 226:    */       
/* 227:    */ 
/* 228:    */ 
/* 229:    */ 
/* 230:    */ 
/* 231:    */ 
/* 232:    */ 
/* 233:454 */       long l13 = T0[((int)(paramArrayOfLong[12] >>> 56))] ^ T1[((int)(paramArrayOfLong[13] >>> 48) & 0xFF)] ^ T2[((int)(paramArrayOfLong[14] >>> 40) & 0xFF)] ^ T3[((int)(paramArrayOfLong[15] >>> 32) & 0xFF)] ^ T4[((int)paramArrayOfLong[0] >>> 24)] ^ T5[((int)paramArrayOfLong[1] >>> 16 & 0xFF)] ^ T6[((int)paramArrayOfLong[2] >>> 8 & 0xFF)] ^ T7[((int)paramArrayOfLong[7] & 0xFF)];
/* 234:    */       
/* 235:    */ 
/* 236:    */ 
/* 237:    */ 
/* 238:    */ 
/* 239:    */ 
/* 240:    */ 
/* 241:462 */       long l14 = T0[((int)(paramArrayOfLong[13] >>> 56))] ^ T1[((int)(paramArrayOfLong[14] >>> 48) & 0xFF)] ^ T2[((int)(paramArrayOfLong[15] >>> 40) & 0xFF)] ^ T3[((int)(paramArrayOfLong[0] >>> 32) & 0xFF)] ^ T4[((int)paramArrayOfLong[1] >>> 24)] ^ T5[((int)paramArrayOfLong[2] >>> 16 & 0xFF)] ^ T6[((int)paramArrayOfLong[3] >>> 8 & 0xFF)] ^ T7[((int)paramArrayOfLong[8] & 0xFF)];
/* 242:    */       
/* 243:    */ 
/* 244:    */ 
/* 245:    */ 
/* 246:    */ 
/* 247:    */ 
/* 248:    */ 
/* 249:470 */       long l15 = T0[((int)(paramArrayOfLong[14] >>> 56))] ^ T1[((int)(paramArrayOfLong[15] >>> 48) & 0xFF)] ^ T2[((int)(paramArrayOfLong[0] >>> 40) & 0xFF)] ^ T3[((int)(paramArrayOfLong[1] >>> 32) & 0xFF)] ^ T4[((int)paramArrayOfLong[2] >>> 24)] ^ T5[((int)paramArrayOfLong[3] >>> 16 & 0xFF)] ^ T6[((int)paramArrayOfLong[4] >>> 8 & 0xFF)] ^ T7[((int)paramArrayOfLong[9] & 0xFF)];
/* 250:    */       
/* 251:    */ 
/* 252:    */ 
/* 253:    */ 
/* 254:    */ 
/* 255:    */ 
/* 256:    */ 
/* 257:478 */       long l16 = T0[((int)(paramArrayOfLong[15] >>> 56))] ^ T1[((int)(paramArrayOfLong[0] >>> 48) & 0xFF)] ^ T2[((int)(paramArrayOfLong[1] >>> 40) & 0xFF)] ^ T3[((int)(paramArrayOfLong[2] >>> 32) & 0xFF)] ^ T4[((int)paramArrayOfLong[3] >>> 24)] ^ T5[((int)paramArrayOfLong[4] >>> 16 & 0xFF)] ^ T6[((int)paramArrayOfLong[5] >>> 8 & 0xFF)] ^ T7[((int)paramArrayOfLong[10] & 0xFF)];
/* 258:    */       
/* 259:    */ 
/* 260:    */ 
/* 261:    */ 
/* 262:    */ 
/* 263:    */ 
/* 264:    */ 
/* 265:486 */       paramArrayOfLong[0] = l1;
/* 266:487 */       paramArrayOfLong[1] = l2;
/* 267:488 */       paramArrayOfLong[2] = l3;
/* 268:489 */       paramArrayOfLong[3] = l4;
/* 269:490 */       paramArrayOfLong[4] = l5;
/* 270:491 */       paramArrayOfLong[5] = l6;
/* 271:492 */       paramArrayOfLong[6] = l7;
/* 272:493 */       paramArrayOfLong[7] = l8;
/* 273:494 */       paramArrayOfLong[8] = l9;
/* 274:495 */       paramArrayOfLong[9] = l10;
/* 275:496 */       paramArrayOfLong[10] = l11;
/* 276:497 */       paramArrayOfLong[11] = l12;
/* 277:498 */       paramArrayOfLong[12] = l13;
/* 278:499 */       paramArrayOfLong[13] = l14;
/* 279:500 */       paramArrayOfLong[14] = l15;
/* 280:501 */       paramArrayOfLong[15] = l16;
/* 281:    */     }
/* 282:    */   }
/* 283:    */   
/* 284:    */   private void doPermQ(long[] paramArrayOfLong)
/* 285:    */   {
/* 286:507 */     for (int i = 0; i < 14; i++)
/* 287:    */     {
/* 288:508 */       paramArrayOfLong[0] ^= i ^ 0xFFFFFFFF;
/* 289:509 */       paramArrayOfLong[1] ^= i ^ 0xFFFFFFEF;
/* 290:510 */       paramArrayOfLong[2] ^= i ^ 0xFFFFFFDF;
/* 291:511 */       paramArrayOfLong[3] ^= i ^ 0xFFFFFFCF;
/* 292:512 */       paramArrayOfLong[4] ^= i ^ 0xFFFFFFBF;
/* 293:513 */       paramArrayOfLong[5] ^= i ^ 0xFFFFFFAF;
/* 294:514 */       paramArrayOfLong[6] ^= i ^ 0xFFFFFF9F;
/* 295:515 */       paramArrayOfLong[7] ^= i ^ 0xFFFFFF8F;
/* 296:516 */       paramArrayOfLong[8] ^= i ^ 0xFFFFFF7F;
/* 297:517 */       paramArrayOfLong[9] ^= i ^ 0xFFFFFF6F;
/* 298:518 */       paramArrayOfLong[10] ^= i ^ 0xFFFFFF5F;
/* 299:519 */       paramArrayOfLong[11] ^= i ^ 0xFFFFFF4F;
/* 300:520 */       paramArrayOfLong[12] ^= i ^ 0xFFFFFF3F;
/* 301:521 */       paramArrayOfLong[13] ^= i ^ 0xFFFFFF2F;
/* 302:522 */       paramArrayOfLong[14] ^= i ^ 0xFFFFFF1F;
/* 303:523 */       paramArrayOfLong[15] ^= i ^ 0xFFFFFF0F;
/* 304:524 */       long l1 = T0[((int)(paramArrayOfLong[1] >>> 56))] ^ T1[((int)(paramArrayOfLong[3] >>> 48) & 0xFF)] ^ T2[((int)(paramArrayOfLong[5] >>> 40) & 0xFF)] ^ T3[((int)(paramArrayOfLong[11] >>> 32) & 0xFF)] ^ T4[((int)paramArrayOfLong[0] >>> 24)] ^ T5[((int)paramArrayOfLong[2] >>> 16 & 0xFF)] ^ T6[((int)paramArrayOfLong[4] >>> 8 & 0xFF)] ^ T7[((int)paramArrayOfLong[6] & 0xFF)];
/* 305:    */       
/* 306:    */ 
/* 307:    */ 
/* 308:    */ 
/* 309:    */ 
/* 310:    */ 
/* 311:    */ 
/* 312:532 */       long l2 = T0[((int)(paramArrayOfLong[2] >>> 56))] ^ T1[((int)(paramArrayOfLong[4] >>> 48) & 0xFF)] ^ T2[((int)(paramArrayOfLong[6] >>> 40) & 0xFF)] ^ T3[((int)(paramArrayOfLong[12] >>> 32) & 0xFF)] ^ T4[((int)paramArrayOfLong[1] >>> 24)] ^ T5[((int)paramArrayOfLong[3] >>> 16 & 0xFF)] ^ T6[((int)paramArrayOfLong[5] >>> 8 & 0xFF)] ^ T7[((int)paramArrayOfLong[7] & 0xFF)];
/* 313:    */       
/* 314:    */ 
/* 315:    */ 
/* 316:    */ 
/* 317:    */ 
/* 318:    */ 
/* 319:    */ 
/* 320:540 */       long l3 = T0[((int)(paramArrayOfLong[3] >>> 56))] ^ T1[((int)(paramArrayOfLong[5] >>> 48) & 0xFF)] ^ T2[((int)(paramArrayOfLong[7] >>> 40) & 0xFF)] ^ T3[((int)(paramArrayOfLong[13] >>> 32) & 0xFF)] ^ T4[((int)paramArrayOfLong[2] >>> 24)] ^ T5[((int)paramArrayOfLong[4] >>> 16 & 0xFF)] ^ T6[((int)paramArrayOfLong[6] >>> 8 & 0xFF)] ^ T7[((int)paramArrayOfLong[8] & 0xFF)];
/* 321:    */       
/* 322:    */ 
/* 323:    */ 
/* 324:    */ 
/* 325:    */ 
/* 326:    */ 
/* 327:    */ 
/* 328:548 */       long l4 = T0[((int)(paramArrayOfLong[4] >>> 56))] ^ T1[((int)(paramArrayOfLong[6] >>> 48) & 0xFF)] ^ T2[((int)(paramArrayOfLong[8] >>> 40) & 0xFF)] ^ T3[((int)(paramArrayOfLong[14] >>> 32) & 0xFF)] ^ T4[((int)paramArrayOfLong[3] >>> 24)] ^ T5[((int)paramArrayOfLong[5] >>> 16 & 0xFF)] ^ T6[((int)paramArrayOfLong[7] >>> 8 & 0xFF)] ^ T7[((int)paramArrayOfLong[9] & 0xFF)];
/* 329:    */       
/* 330:    */ 
/* 331:    */ 
/* 332:    */ 
/* 333:    */ 
/* 334:    */ 
/* 335:    */ 
/* 336:556 */       long l5 = T0[((int)(paramArrayOfLong[5] >>> 56))] ^ T1[((int)(paramArrayOfLong[7] >>> 48) & 0xFF)] ^ T2[((int)(paramArrayOfLong[9] >>> 40) & 0xFF)] ^ T3[((int)(paramArrayOfLong[15] >>> 32) & 0xFF)] ^ T4[((int)paramArrayOfLong[4] >>> 24)] ^ T5[((int)paramArrayOfLong[6] >>> 16 & 0xFF)] ^ T6[((int)paramArrayOfLong[8] >>> 8 & 0xFF)] ^ T7[((int)paramArrayOfLong[10] & 0xFF)];
/* 337:    */       
/* 338:    */ 
/* 339:    */ 
/* 340:    */ 
/* 341:    */ 
/* 342:    */ 
/* 343:    */ 
/* 344:564 */       long l6 = T0[((int)(paramArrayOfLong[6] >>> 56))] ^ T1[((int)(paramArrayOfLong[8] >>> 48) & 0xFF)] ^ T2[((int)(paramArrayOfLong[10] >>> 40) & 0xFF)] ^ T3[((int)(paramArrayOfLong[0] >>> 32) & 0xFF)] ^ T4[((int)paramArrayOfLong[5] >>> 24)] ^ T5[((int)paramArrayOfLong[7] >>> 16 & 0xFF)] ^ T6[((int)paramArrayOfLong[9] >>> 8 & 0xFF)] ^ T7[((int)paramArrayOfLong[11] & 0xFF)];
/* 345:    */       
/* 346:    */ 
/* 347:    */ 
/* 348:    */ 
/* 349:    */ 
/* 350:    */ 
/* 351:    */ 
/* 352:572 */       long l7 = T0[((int)(paramArrayOfLong[7] >>> 56))] ^ T1[((int)(paramArrayOfLong[9] >>> 48) & 0xFF)] ^ T2[((int)(paramArrayOfLong[11] >>> 40) & 0xFF)] ^ T3[((int)(paramArrayOfLong[1] >>> 32) & 0xFF)] ^ T4[((int)paramArrayOfLong[6] >>> 24)] ^ T5[((int)paramArrayOfLong[8] >>> 16 & 0xFF)] ^ T6[((int)paramArrayOfLong[10] >>> 8 & 0xFF)] ^ T7[((int)paramArrayOfLong[12] & 0xFF)];
/* 353:    */       
/* 354:    */ 
/* 355:    */ 
/* 356:    */ 
/* 357:    */ 
/* 358:    */ 
/* 359:    */ 
/* 360:580 */       long l8 = T0[((int)(paramArrayOfLong[8] >>> 56))] ^ T1[((int)(paramArrayOfLong[10] >>> 48) & 0xFF)] ^ T2[((int)(paramArrayOfLong[12] >>> 40) & 0xFF)] ^ T3[((int)(paramArrayOfLong[2] >>> 32) & 0xFF)] ^ T4[((int)paramArrayOfLong[7] >>> 24)] ^ T5[((int)paramArrayOfLong[9] >>> 16 & 0xFF)] ^ T6[((int)paramArrayOfLong[11] >>> 8 & 0xFF)] ^ T7[((int)paramArrayOfLong[13] & 0xFF)];
/* 361:    */       
/* 362:    */ 
/* 363:    */ 
/* 364:    */ 
/* 365:    */ 
/* 366:    */ 
/* 367:    */ 
/* 368:588 */       long l9 = T0[((int)(paramArrayOfLong[9] >>> 56))] ^ T1[((int)(paramArrayOfLong[11] >>> 48) & 0xFF)] ^ T2[((int)(paramArrayOfLong[13] >>> 40) & 0xFF)] ^ T3[((int)(paramArrayOfLong[3] >>> 32) & 0xFF)] ^ T4[((int)paramArrayOfLong[8] >>> 24)] ^ T5[((int)paramArrayOfLong[10] >>> 16 & 0xFF)] ^ T6[((int)paramArrayOfLong[12] >>> 8 & 0xFF)] ^ T7[((int)paramArrayOfLong[14] & 0xFF)];
/* 369:    */       
/* 370:    */ 
/* 371:    */ 
/* 372:    */ 
/* 373:    */ 
/* 374:    */ 
/* 375:    */ 
/* 376:596 */       long l10 = T0[((int)(paramArrayOfLong[10] >>> 56))] ^ T1[((int)(paramArrayOfLong[12] >>> 48) & 0xFF)] ^ T2[((int)(paramArrayOfLong[14] >>> 40) & 0xFF)] ^ T3[((int)(paramArrayOfLong[4] >>> 32) & 0xFF)] ^ T4[((int)paramArrayOfLong[9] >>> 24)] ^ T5[((int)paramArrayOfLong[11] >>> 16 & 0xFF)] ^ T6[((int)paramArrayOfLong[13] >>> 8 & 0xFF)] ^ T7[((int)paramArrayOfLong[15] & 0xFF)];
/* 377:    */       
/* 378:    */ 
/* 379:    */ 
/* 380:    */ 
/* 381:    */ 
/* 382:    */ 
/* 383:    */ 
/* 384:604 */       long l11 = T0[((int)(paramArrayOfLong[11] >>> 56))] ^ T1[((int)(paramArrayOfLong[13] >>> 48) & 0xFF)] ^ T2[((int)(paramArrayOfLong[15] >>> 40) & 0xFF)] ^ T3[((int)(paramArrayOfLong[5] >>> 32) & 0xFF)] ^ T4[((int)paramArrayOfLong[10] >>> 24)] ^ T5[((int)paramArrayOfLong[12] >>> 16 & 0xFF)] ^ T6[((int)paramArrayOfLong[14] >>> 8 & 0xFF)] ^ T7[((int)paramArrayOfLong[0] & 0xFF)];
/* 385:    */       
/* 386:    */ 
/* 387:    */ 
/* 388:    */ 
/* 389:    */ 
/* 390:    */ 
/* 391:    */ 
/* 392:612 */       long l12 = T0[((int)(paramArrayOfLong[12] >>> 56))] ^ T1[((int)(paramArrayOfLong[14] >>> 48) & 0xFF)] ^ T2[((int)(paramArrayOfLong[0] >>> 40) & 0xFF)] ^ T3[((int)(paramArrayOfLong[6] >>> 32) & 0xFF)] ^ T4[((int)paramArrayOfLong[11] >>> 24)] ^ T5[((int)paramArrayOfLong[13] >>> 16 & 0xFF)] ^ T6[((int)paramArrayOfLong[15] >>> 8 & 0xFF)] ^ T7[((int)paramArrayOfLong[1] & 0xFF)];
/* 393:    */       
/* 394:    */ 
/* 395:    */ 
/* 396:    */ 
/* 397:    */ 
/* 398:    */ 
/* 399:    */ 
/* 400:620 */       long l13 = T0[((int)(paramArrayOfLong[13] >>> 56))] ^ T1[((int)(paramArrayOfLong[15] >>> 48) & 0xFF)] ^ T2[((int)(paramArrayOfLong[1] >>> 40) & 0xFF)] ^ T3[((int)(paramArrayOfLong[7] >>> 32) & 0xFF)] ^ T4[((int)paramArrayOfLong[12] >>> 24)] ^ T5[((int)paramArrayOfLong[14] >>> 16 & 0xFF)] ^ T6[((int)paramArrayOfLong[0] >>> 8 & 0xFF)] ^ T7[((int)paramArrayOfLong[2] & 0xFF)];
/* 401:    */       
/* 402:    */ 
/* 403:    */ 
/* 404:    */ 
/* 405:    */ 
/* 406:    */ 
/* 407:    */ 
/* 408:628 */       long l14 = T0[((int)(paramArrayOfLong[14] >>> 56))] ^ T1[((int)(paramArrayOfLong[0] >>> 48) & 0xFF)] ^ T2[((int)(paramArrayOfLong[2] >>> 40) & 0xFF)] ^ T3[((int)(paramArrayOfLong[8] >>> 32) & 0xFF)] ^ T4[((int)paramArrayOfLong[13] >>> 24)] ^ T5[((int)paramArrayOfLong[15] >>> 16 & 0xFF)] ^ T6[((int)paramArrayOfLong[1] >>> 8 & 0xFF)] ^ T7[((int)paramArrayOfLong[3] & 0xFF)];
/* 409:    */       
/* 410:    */ 
/* 411:    */ 
/* 412:    */ 
/* 413:    */ 
/* 414:    */ 
/* 415:    */ 
/* 416:636 */       long l15 = T0[((int)(paramArrayOfLong[15] >>> 56))] ^ T1[((int)(paramArrayOfLong[1] >>> 48) & 0xFF)] ^ T2[((int)(paramArrayOfLong[3] >>> 40) & 0xFF)] ^ T3[((int)(paramArrayOfLong[9] >>> 32) & 0xFF)] ^ T4[((int)paramArrayOfLong[14] >>> 24)] ^ T5[((int)paramArrayOfLong[0] >>> 16 & 0xFF)] ^ T6[((int)paramArrayOfLong[2] >>> 8 & 0xFF)] ^ T7[((int)paramArrayOfLong[4] & 0xFF)];
/* 417:    */       
/* 418:    */ 
/* 419:    */ 
/* 420:    */ 
/* 421:    */ 
/* 422:    */ 
/* 423:    */ 
/* 424:644 */       long l16 = T0[((int)(paramArrayOfLong[0] >>> 56))] ^ T1[((int)(paramArrayOfLong[2] >>> 48) & 0xFF)] ^ T2[((int)(paramArrayOfLong[4] >>> 40) & 0xFF)] ^ T3[((int)(paramArrayOfLong[10] >>> 32) & 0xFF)] ^ T4[((int)paramArrayOfLong[15] >>> 24)] ^ T5[((int)paramArrayOfLong[1] >>> 16 & 0xFF)] ^ T6[((int)paramArrayOfLong[3] >>> 8 & 0xFF)] ^ T7[((int)paramArrayOfLong[5] & 0xFF)];
/* 425:    */       
/* 426:    */ 
/* 427:    */ 
/* 428:    */ 
/* 429:    */ 
/* 430:    */ 
/* 431:    */ 
/* 432:652 */       paramArrayOfLong[0] = l1;
/* 433:653 */       paramArrayOfLong[1] = l2;
/* 434:654 */       paramArrayOfLong[2] = l3;
/* 435:655 */       paramArrayOfLong[3] = l4;
/* 436:656 */       paramArrayOfLong[4] = l5;
/* 437:657 */       paramArrayOfLong[5] = l6;
/* 438:658 */       paramArrayOfLong[6] = l7;
/* 439:659 */       paramArrayOfLong[7] = l8;
/* 440:660 */       paramArrayOfLong[8] = l9;
/* 441:661 */       paramArrayOfLong[9] = l10;
/* 442:662 */       paramArrayOfLong[10] = l11;
/* 443:663 */       paramArrayOfLong[11] = l12;
/* 444:664 */       paramArrayOfLong[12] = l13;
/* 445:665 */       paramArrayOfLong[13] = l14;
/* 446:666 */       paramArrayOfLong[14] = l15;
/* 447:667 */       paramArrayOfLong[15] = l16;
/* 448:    */     }
/* 449:    */   }
/* 450:    */   
/* 451:    */   protected void processBlock(byte[] paramArrayOfByte)
/* 452:    */   {
/* 453:674 */     for (int i = 0; i < 16; i++)
/* 454:    */     {
/* 455:675 */       this.M[i] = decodeBELong(paramArrayOfByte, i * 8);
/* 456:676 */       this.G[i] = (this.M[i] ^ this.H[i]);
/* 457:    */     }
/* 458:678 */     doPermP(this.G);
/* 459:679 */     doPermQ(this.M);
/* 460:680 */     for (i = 0; i < 16; i++) {
/* 461:681 */       this.H[i] ^= this.G[i] ^ this.M[i];
/* 462:    */     }
/* 463:    */   }
/* 464:    */   
/* 465:    */   public String toString()
/* 466:    */   {
/* 467:687 */     return "Groestl-" + (getDigestLength() << 3);
/* 468:    */   }
/* 469:    */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     fr.cryptohash.GroestlBigCore
 * JD-Core Version:    0.7.1
 */