/*   1:    */ package fr.cryptohash;
/*   2:    */ 
/*   3:    */ abstract class SHA2BigCore
/*   4:    */   extends MDHelper
/*   5:    */ {
/*   6:    */   SHA2BigCore()
/*   7:    */   {
/*   8: 47 */     super(false, 16);
/*   9:    */   }
/*  10:    */   
/*  11: 51 */   private static final long[] K = { 4794697086780616226L, 8158064640168781261L, -5349999486874862801L, -1606136188198331460L, 4131703408338449720L, 6480981068601479193L, -7908458776815382629L, -6116909921290321640L, -2880145864133508542L, 1334009975649890238L, 2608012711638119052L, 6128411473006802146L, 8268148722764581231L, -9160688886553864527L, -7215885187991268811L, -4495734319001033068L, -1973867731355612462L, -1171420211273849373L, 1135362057144423861L, 2597628984639134821L, 3308224258029322869L, 5365058923640841347L, 6679025012923562964L, 8573033837759648693L, -7476448914759557205L, -6327057829258317296L, -5763719355590565569L, -4658551843659510044L, -4116276920077217854L, -3051310485924567259L, 489312712824947311L, 1452737877330783856L, 2861767655752347644L, 3322285676063803686L, 5560940570517711597L, 5996557281743188959L, 7280758554555802590L, 8532644243296465576L, -9096487096722542874L, -7894198246740708037L, -6719396339535248540L, -6333637450476146687L, -4446306890439682159L, -4076793802049405392L, -3345356375505022440L, -2983346525034927856L, -860691631967231958L, 1182934255886127544L, 1847814050463011016L, 2177327727835720531L, 2830643537854262169L, 3796741975233480872L, 4115178125766777443L, 5681478168544905931L, 6601373596472566643L, 7507060721942968483L, 8399075790359081724L, 8693463985226723168L, -8878714635349349518L, -8302665154208450068L, -8016688836872298968L, -6606660893046293015L, -4685533653050689259L, -4147400797238176981L, -3880063495543823972L, -3348786107499101689L, -1523767162380948706L, -757361751448694408L, 500013540394364858L, 748580250866718886L, 1242879168328830382L, 1977374033974150939L, 2944078676154940804L, 3659926193048069267L, 4368137639120453308L, 4836135668995329356L, 5532061633213252278L, 6448918945643986474L, 6902733635092675308L, 7801388544844847127L };
/*  12:    */   private long[] currentVal;
/*  13:    */   private long[] W;
/*  14:    */   
/*  15:    */   protected Digest copyState(SHA2BigCore paramSHA2BigCore)
/*  16:    */   {
/*  17: 86 */     System.arraycopy(this.currentVal, 0, paramSHA2BigCore.currentVal, 0, this.currentVal.length);
/*  18:    */     
/*  19: 88 */     return super.copyState(paramSHA2BigCore);
/*  20:    */   }
/*  21:    */   
/*  22:    */   public int getBlockLength()
/*  23:    */   {
/*  24: 94 */     return 128;
/*  25:    */   }
/*  26:    */   
/*  27:    */   protected void engineReset()
/*  28:    */   {
/*  29:100 */     System.arraycopy(getInitVal(), 0, this.currentVal, 0, 8);
/*  30:    */   }
/*  31:    */   
/*  32:    */   abstract long[] getInitVal();
/*  33:    */   
/*  34:    */   protected void doPadding(byte[] paramArrayOfByte, int paramInt)
/*  35:    */   {
/*  36:113 */     makeMDPadding();
/*  37:114 */     int i = getDigestLength();
/*  38:115 */     int j = 0;
/*  39:115 */     for (int k = 0; k < i; k += 8)
/*  40:    */     {
/*  41:116 */       encodeBELong(this.currentVal[j], paramArrayOfByte, paramInt + k);j++;
/*  42:    */     }
/*  43:    */   }
/*  44:    */   
/*  45:    */   protected void doInit()
/*  46:    */   {
/*  47:122 */     this.currentVal = new long[8];
/*  48:123 */     this.W = new long[80];
/*  49:124 */     engineReset();
/*  50:    */   }
/*  51:    */   
/*  52:    */   private static final void encodeBELong(long paramLong, byte[] paramArrayOfByte, int paramInt)
/*  53:    */   {
/*  54:138 */     paramArrayOfByte[(paramInt + 0)] = ((byte)(int)(paramLong >>> 56));
/*  55:139 */     paramArrayOfByte[(paramInt + 1)] = ((byte)(int)(paramLong >>> 48));
/*  56:140 */     paramArrayOfByte[(paramInt + 2)] = ((byte)(int)(paramLong >>> 40));
/*  57:141 */     paramArrayOfByte[(paramInt + 3)] = ((byte)(int)(paramLong >>> 32));
/*  58:142 */     paramArrayOfByte[(paramInt + 4)] = ((byte)(int)(paramLong >>> 24));
/*  59:143 */     paramArrayOfByte[(paramInt + 5)] = ((byte)(int)(paramLong >>> 16));
/*  60:144 */     paramArrayOfByte[(paramInt + 6)] = ((byte)(int)(paramLong >>> 8));
/*  61:145 */     paramArrayOfByte[(paramInt + 7)] = ((byte)(int)paramLong);
/*  62:    */   }
/*  63:    */   
/*  64:    */   private static final long decodeBELong(byte[] paramArrayOfByte, int paramInt)
/*  65:    */   {
/*  66:158 */     return (paramArrayOfByte[paramInt] & 0xFF) << 56 | (paramArrayOfByte[(paramInt + 1)] & 0xFF) << 48 | (paramArrayOfByte[(paramInt + 2)] & 0xFF) << 40 | (paramArrayOfByte[(paramInt + 3)] & 0xFF) << 32 | (paramArrayOfByte[(paramInt + 4)] & 0xFF) << 24 | (paramArrayOfByte[(paramInt + 5)] & 0xFF) << 16 | (paramArrayOfByte[(paramInt + 6)] & 0xFF) << 8 | paramArrayOfByte[(paramInt + 7)] & 0xFF;
/*  67:    */   }
/*  68:    */   
/*  69:    */   private static long circularLeft(long paramLong, int paramInt)
/*  70:    */   {
/*  71:179 */     return paramLong << paramInt | paramLong >>> 64 - paramInt;
/*  72:    */   }
/*  73:    */   
/*  74:    */   protected void processBlock(byte[] paramArrayOfByte)
/*  75:    */   {
/*  76:185 */     long l1 = this.currentVal[0];
/*  77:186 */     long l2 = this.currentVal[1];
/*  78:187 */     long l3 = this.currentVal[2];
/*  79:188 */     long l4 = this.currentVal[3];
/*  80:189 */     long l5 = this.currentVal[4];
/*  81:190 */     long l6 = this.currentVal[5];
/*  82:191 */     long l7 = this.currentVal[6];
/*  83:192 */     long l8 = this.currentVal[7];
/*  84:194 */     for (int i = 0; i < 16; i++) {
/*  85:195 */       this.W[i] = decodeBELong(paramArrayOfByte, 8 * i);
/*  86:    */     }
/*  87:196 */     for (i = 16; i < 80; i++) {
/*  88:197 */       this.W[i] = ((circularLeft(this.W[(i - 2)], 45) ^ circularLeft(this.W[(i - 2)], 3) ^ this.W[(i - 2)] >>> 6) + this.W[(i - 7)] + (circularLeft(this.W[(i - 15)], 63) ^ circularLeft(this.W[(i - 15)], 56) ^ this.W[(i - 15)] >>> 7) + this.W[(i - 16)]);
/*  89:    */     }
/*  90:206 */     for (i = 0; i < 80; i++)
/*  91:    */     {
/*  92:215 */       long l9 = circularLeft(l5, 50);
/*  93:216 */       l9 ^= circularLeft(l5, 46);
/*  94:217 */       l9 ^= circularLeft(l5, 23);
/*  95:218 */       l9 += l8;
/*  96:219 */       l9 += (l6 & l5 ^ l7 & (l5 ^ 0xFFFFFFFF));
/*  97:220 */       l9 += K[i];
/*  98:221 */       l9 += this.W[i];
/*  99:    */       
/* 100:223 */       long l10 = circularLeft(l1, 36);
/* 101:224 */       l10 ^= circularLeft(l1, 30);
/* 102:225 */       l10 ^= circularLeft(l1, 25);
/* 103:226 */       l10 += (l1 & l2 ^ l1 & l3 ^ l2 & l3);
/* 104:    */       
/* 105:228 */       l8 = l7;l7 = l6;l6 = l5;l5 = l4 + l9;
/* 106:229 */       l4 = l3;l3 = l2;l2 = l1;l1 = l9 + l10;
/* 107:    */     }
/* 108:231 */     this.currentVal[0] += l1;
/* 109:232 */     this.currentVal[1] += l2;
/* 110:233 */     this.currentVal[2] += l3;
/* 111:234 */     this.currentVal[3] += l4;
/* 112:235 */     this.currentVal[4] += l5;
/* 113:236 */     this.currentVal[5] += l6;
/* 114:237 */     this.currentVal[6] += l7;
/* 115:238 */     this.currentVal[7] += l8;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public String toString()
/* 119:    */   {
/* 120:244 */     return "SHA-" + (getDigestLength() << 3);
/* 121:    */   }
/* 122:    */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     fr.cryptohash.SHA2BigCore
 * JD-Core Version:    0.7.1
 */