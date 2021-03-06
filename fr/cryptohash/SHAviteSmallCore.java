/*   1:    */ package fr.cryptohash;
/*   2:    */ 
/*   3:    */ abstract class SHAviteSmallCore
/*   4:    */   extends DigestEngine
/*   5:    */ {
/*   6:    */   private int[] h;
/*   7:    */   private int[] rk;
/*   8: 50 */   private static final int[] AES0 = { -1520213050, -2072216328, -1720223762, -1921287178, 234025727, -1117033514, -1318096930, 1422247313, 1345335392, 50397442, -1452841010, 2099981142, 436141799, 1658312629, -424957107, -1703512340, 1170918031, -1652391393, 1086966153, -2021818886, 368769775, -346465870, -918075506, 200339707, -324162239, 1742001331, -39673249, -357585083, -1080255453, -140204973, -1770884380, 1539358875, -1028147339, 486407649, -1366060227, 1780885068, 1513502316, 1094664062, 49805301, 1338821763, 1546925160, -190470831, 887481809, 150073849, -1821281822, 1943591083, 1395732834, 1058346282, 201589768, 1388824469, 1696801606, 1589887901, 672667696, -1583966665, 251987210, -1248159185, 151455502, 907153956, -1686077413, 1038279391, 652995533, 1764173646, -843926913, -1619692054, 453576978, -1635548387, 1949051992, 773462580, 756751158, -1301385508, -296068428, -73359269, -162377052, 1295727478, 1641469623, -827083907, 2066295122, 1055122397, 1898917726, -1752923117, -179088474, 1758581177, 0, 753790401, 1612718144, 536673507, -927878791, -312779850, -1100322092, 1187761037, -641810841, 1262041458, -565556588, -733197160, -396863312, 1255133061, 1808847035, 720367557, -441800113, 385612781, -985447546, -682799718, 1429418854, -1803188975, -817543798, 284817897, 100794884, -2122350594, -263171936, 1144798328, -1163944155, -475486133, -212774494, -22830243, -1069531008, -1970303227, -1382903233, -1130521311, 1211644016, 83228145, -541279133, -1044990345, 1977277103, 1663115586, 806359072, 452984805, 250868733, 1842533055, 1288555905, 336333848, 890442534, 804056259, -513843266, -1567123659, -867941240, 957814574, 1472513171, -223893675, -2105639172, 1195195770, -1402706744, -413311558, 723065138, -1787595802, -1604296512, -1736343271, -783331426, 2145180835, 1713513028, 2116692564, -1416589253, -2088204277, -901364084, 703524551, -742868885, 1007948840, 2044649127, -497131844, 487262998, 1994120109, 1004593371, 1446130276, 1312438900, 503974420, -615954030, 168166924, 1814307912, -463709000, 1573044895, 1859376061, -273896381, -1503501628, -1466855111, -1533700815, 937747667, -1954973198, 854058965, 1137232011, 1496790894, -1217565222, -1936880383, 1691735473, -766620004, -525751991, -1267962664, -95005012, 133494003, 636152527, -1352309302, -1904575756, -374428089, 403179536, -709182865, -2005370640, 1864705354, 1915629148, 605822008, -240736681, -944458637, 1371981463, 602466507, 2094914977, -1670089496, 555687742, -582268010, -591544991, -2037675251, -2054518257, -1871679264, 1111375484, -994724495, -1436129588, -666351472, 84083462, 32962295, 302911004, -1553899070, 1597322602, -111716434, -793134743, -1853454825, 1489093017, 656219450, -1180787161, 954327513, 335083755, -1281845205, 856756514, -1150719534, 1893325225, -1987146233, -1483434957, -1231316179, 572399164, -1836611819, 552200649, 1238290055, -11184726, 2015897680, 2061492133, -1886614525, -123625127, -2138470135, 386731290, -624967835, 837215959, -968736124, -1201116976, -1019133566, -1332111063, 1999449434, 286199582, -877612933, -61582168, -692339859, 974525996 };
/*   9:117 */   private static final int[] AES1 = { 1667483301, 2088564868, 2004348569, 2071721613, -218956019, 1802229437, 1869602481, -976907948, 808476752, 16843267, 1734856361, 724260477, -16849127, -673729182, -1414836762, 1987505306, -892694715, -2105401443, -909539008, 2105408135, -84218091, 1499050731, 1195871945, -252642549, -1381154324, -724257945, -1566416899, -1347467798, -1667488833, -1532734473, 1920132246, -1061119141, -1212713534, -33693412, -1819066962, 640044138, 909536346, 1061125697, -134744830, -859012273, 875849820, -1515892236, -437923532, -235800312, 1903288979, -656888973, 825320019, 353708607, 67373068, -943221422, 589514341, -1010590370, 404238376, -1768540255, 84216335, -1701171275, 117902857, 303178806, -2139087973, -488448195, -336868058, 656887401, -1296924723, 1970662047, 151589403, -2088559202, 741103732, 437924910, 454768173, 1852759218, 1515893998, -1600103429, 1381147894, 993752653, -690571423, -1280082482, 690573947, -471605954, 791633521, -2071719017, 1397991157, -774784664, 0, -303185620, 538984544, -50535649, -1313769016, 1532737261, 1785386174, -875852474, -1094817831, 960066123, 1246401758, 1280088276, 1482207464, -808483510, -791626901, -269499094, -1431679003, -67375850, 1128498885, 1296931543, 859006549, -2054876780, 1162185423, -101062384, 33686534, 2139094657, 1347461360, 1010595908, -1616960070, -1465365533, 1364304627, -1549574658, 1077969088, -1886452342, -1835909203, -1650646596, 943222856, -168431356, -1128504353, -1229555775, -623202443, 555827811, 269492272, -6886, -202113778, -757940371, -842170036, 202119188, 320022069, -320027857, 1600110305, -1751698014, 1145342156, 387395129, -993750185, -1482205710, 2122251394, 1027439175, 1684326572, 1566423783, 421081643, 1936975509, 1616953504, -2122245736, 1330618065, -589520001, 572671078, 707417214, -1869595733, -2004350077, 1179028682, -286341335, -1195873325, 336865340, -555833479, 1583267042, 185275933, -606360202, -522134725, 842163286, 976909390, 168432670, 1229558491, 101059594, 606357612, 1549580516, -1027432611, -741098130, -1397996561, 1650640038, -1852753496, -1785384540, -454765769, 2038035083, -404237006, -926381245, 926379609, 1835915959, -1920138868, -707415708, 1313774802, -1448523296, 1819072692, 1448520954, -185273593, -353710299, 1701169839, 2054878350, -1364310039, 134746136, -1162186795, 2021191816, 623200879, 774790258, 471611428, -1499047951, -1263242297, -960063663, -387396829, -572677764, 1953818780, 522141217, 1263245021, -1111662116, -1953821306, -1970663547, 1886445712, 1044282434, -1246400060, 1718013098, 1212715224, 50529797, -151587071, 235805714, 1633796771, 892693087, 1465364217, -1179031088, -2038032495, -1044276904, 488454695, -1633802311, -505292488, -117904621, -1734857805, 286335539, 1768542907, -640046736, -1903294583, -1802226777, -1684329034, 505297954, -2021190254, -370554592, -825325751, 1431677695, 673730680, -538991238, -1936981105, -1583261192, -1987507840, 218962455, -1077975590, -421079247, 1111655622, 1751699640, 1094812355, -1718015568, 757946999, 252648977, -1330611253, 1414834428, -1145344554, 370551866 };
/*  10:184 */   private static final int[] AES2 = { 1673962851, 2096661628, 2012125559, 2079755643, -218165774, 1809235307, 1876865391, -980331323, 811618352, 16909057, 1741597031, 727088427, -18408962, -675978537, -1420958037, 1995217526, -896580150, -2111857278, -913751863, 2113570685, -84994566, 1504897881, 1200539975, -251982864, -1388188499, -726439980, -1570767454, -1354372433, -1675378788, -1538000988, 1927583346, -1063560256, -1217019209, -35578627, -1824674157, 642542118, 913070646, 1065238847, -134937865, -863809588, 879254580, -1521355611, -439274267, -235337487, 1910674289, -659852328, 828527409, 355090197, 67636228, -946515257, 591815971, -1013096765, 405809176, -1774739050, 84545285, -1708149350, 118360327, 304363026, -2145674368, -488686110, -338876693, 659450151, -1300247118, 1978310517, 152181513, -2095210877, 743994412, 439627290, 456535323, 1859957358, 1521806938, -1604584544, 1386542674, 997608763, -692624938, -1283600717, 693271337, -472039709, 794718511, -2079090812, 1403450707, -776378159, 0, -306107155, 541089824, -52224004, -1317418831, 1538714971, 1792327274, -879933749, -1100490306, 963791673, 1251270218, 1285084236, 1487988824, -813348145, -793023536, -272291089, -1437604438, -68348165, 1132905795, 1301993293, 862344499, -2062445435, 1166724933, -102166279, 33818114, 2147385727, 1352724560, 1014514748, -1624917345, -1471421528, 1369633617, -1554121053, 1082179648, -1895462257, -1841320558, -1658733411, 946882616, -168753931, -1134305348, -1233665610, -626035238, 557998881, 270544912, -1762561, -201519373, -759206446, -847164211, 202904588, 321271059, -322752532, 1606345055, -1758092649, 1149815876, 388905239, -996976700, -1487539545, 2130477694, 1031423805, 1690872932, 1572530013, 422718233, 1944491379, 1623236704, -2129028991, 1335808335, -593264676, 574907938, 710180394, -1875137648, -2012511352, 1183631942, -288937490, -1200893000, 338181140, -559449634, 1589437022, 185998603, -609388837, -522503200, 845436466, 980700730, 169090570, 1234361161, 101452294, 608726052, 1555620956, -1029743166, -742560045, -1404833876, 1657054818, -1858492271, -1791908715, -455919644, 2045938553, -405458201, -930397240, 929978679, 1843050349, -1929278323, -709794603, 1318900302, -1454776151, 1826141292, 1454176854, -185399308, -355523094, 1707781989, 2062847610, -1371018834, 135272456, -1167075910, 2029029496, 625635109, 777810478, 473441308, -1504185946, -1267480652, -963161658, -389340184, -576619299, 1961401460, 524165407, 1268178251, -1117659971, -1962047861, -1978694262, 1893765232, 1048330814, -1250835275, 1724688998, 1217452104, 50726147, -151584266, 236720654, 1640145761, 896163637, 1471084887, -1184247623, -2045275770, -1046914879, 490350365, -1641563746, -505857823, -118811656, -1741966440, 287453969, 1775418217, -643206951, -1912108658, -1808554092, -1691502949, 507257374, -2028629369, -372694807, -829994546, 1437269845, 676362280, -542803233, -1945923700, -1587939167, -1995865975, 219813645, -1083843905, -422104602, 1115997762, 1758509160, 1099088705, -1725321063, 760903469, 253628687, -1334064208, 1420360788, -1150429509, 371997206 };
/*  11:251 */   private static final int[] AES3 = { -962239645, -125535108, -291932297, -158499973, -15863054, -692229269, -558796945, -1856715323, 1615867952, 33751297, -827758745, 1451043627, -417726722, -1251813417, 1306962859, -325421450, -1891251510, 530416258, -1992242743, -91783811, -283772166, -1293199015, -1899411641, -83103504, 1106029997, -1285040940, 1610457762, 1173008303, 599760028, 1408738468, -459902350, -1688485696, 1975695287, -518193667, 1034851219, 1282024998, 1817851446, 2118205247, -184354825, -2091922228, 1750873140, 1374987685, -785062427, -116854287, -493653647, -1418471208, 1649619249, 708777237, 135005188, -1789737017, 1181033251, -1654733885, 807933976, 933336726, 168756485, 800430746, 235472647, 607523346, 463175808, -549592350, -853087253, 1315514151, 2144187058, -358648459, 303761673, 496927619, 1484008492, 875436570, 908925723, -592286098, -1259447718, 1543217312, -1527360942, 1984772923, -1218324778, 2110698419, 1383803177, -583080989, 1584475951, 328696964, -1493871789, -1184312879, 0, -1054020115, 1080041504, -484442884, 2043195825, -1225958565, -725718422, -1924740149, 1742323390, 1917532473, -1797371318, -1730917300, -1326950312, -2058694705, -1150562096, -987041809, 1340451498, -317260805, -2033892541, -1697166003, 1716859699, 294946181, -1966127803, -384763399, 67502594, -25067649, -1594863536, 2017737788, 632987551, 1273211048, -1561112239, 1576969123, -2134884288, 92966799, 1068339858, 566009245, 1883781176, -251333131, 1675607228, 2009183926, -1351230758, 1113792801, 540020752, -451215361, -49351693, -1083321646, -2125673011, 403966988, 641012499, -1020269332, -1092526241, 899848087, -1999879100, 775493399, -1822964540, 1441965991, -58556802, 2051489085, -928226204, -1159242403, 841685273, -426413197, -1063231392, 429425025, -1630449841, -1551901476, 1147544098, 1417554474, 1001099408, 193169544, -1932900794, -953553170, 1809037496, 675025940, -1485185314, -1126015394, 371002123, -1384719397, -616832800, 1683370546, 1951283770, 337512970, -1831122615, 201983494, 1215046692, -1192993700, -1621245246, -1116810285, 1139780780, -995728798, 967348625, 832869781, -751311644, -225740423, -718084121, -1958491960, 1851340599, -625513107, 25988493, -1318791723, -1663938994, 1239460265, -659264404, -1392880042, -217582348, -819598614, -894474907, -191989126, 1206496942, 270010376, 1876277946, -259491720, 1248797989, 1550986798, 941890588, 1475454630, 1942467764, -1756248378, -886839064, -1585652259, -392399756, 1042358047, -1763882165, 1641856445, 226921355, 260409994, -527404944, 2084716094, 1908716981, -861247898, -1864873912, 100991747, -150866186, 470945294, -1029480095, 1784624437, -1359390889, 1775286713, 395413126, -1722236479, 975641885, 666476190, -650583583, -351012616, 733190296, 573772049, -759469719, -1452221991, 126455438, 866620564, 766942107, 1008868894, 361924487, -920589847, -2025206066, -1426107051, 1350051880, -1518673953, 59739276, 1509466529, 159418761, 437718285, 1708834751, -684595482, -2067381694, -793221016, -2101132991, 699439513, 1517759789, 504434447, 2076946608, -1459858348, 1842789307, 742004246 };
/*  12:    */   
/*  13:    */   public int getBlockLength()
/*  14:    */   {
/*  15:321 */     return 64;
/*  16:    */   }
/*  17:    */   
/*  18:    */   protected Digest copyState(SHAviteSmallCore paramSHAviteSmallCore)
/*  19:    */   {
/*  20:327 */     System.arraycopy(this.h, 0, paramSHAviteSmallCore.h, 0, this.h.length);
/*  21:328 */     return super.copyState(paramSHAviteSmallCore);
/*  22:    */   }
/*  23:    */   
/*  24:    */   protected void engineReset()
/*  25:    */   {
/*  26:334 */     System.arraycopy(getInitVal(), 0, this.h, 0, this.h.length);
/*  27:    */   }
/*  28:    */   
/*  29:    */   abstract int[] getInitVal();
/*  30:    */   
/*  31:    */   protected void doPadding(byte[] paramArrayOfByte, int paramInt)
/*  32:    */   {
/*  33:347 */     int i = flush();
/*  34:348 */     long l1 = getBlockCount();
/*  35:349 */     long l2 = (l1 << 9) + (i << 3);
/*  36:350 */     int j = (int)l2;
/*  37:351 */     int k = (int)(l2 >>> 32);
/*  38:352 */     byte[] arrayOfByte = getBlockBuffer();
/*  39:353 */     if (i == 0)
/*  40:    */     {
/*  41:354 */       arrayOfByte[0] = Byte.MIN_VALUE;
/*  42:355 */       for (m = 1; m < 54; m++) {
/*  43:356 */         arrayOfByte[m] = 0;
/*  44:    */       }
/*  45:357 */       j = k = 0;
/*  46:    */     }
/*  47:    */     else
/*  48:    */     {
/*  49:358 */       if (i < 54)
/*  50:    */       {
/*  51:359 */         arrayOfByte[(i++)] = Byte.MIN_VALUE;
/*  52:360 */         while (i < 54) {
/*  53:361 */           arrayOfByte[(i++)] = 0;
/*  54:    */         }
/*  55:    */       }
/*  56:363 */       arrayOfByte[(i++)] = Byte.MIN_VALUE;
/*  57:364 */       while (i < 64) {
/*  58:365 */         arrayOfByte[(i++)] = 0;
/*  59:    */       }
/*  60:366 */       process(arrayOfByte, j, k);
/*  61:367 */       for (m = 0; m < 54; m++) {
/*  62:368 */         arrayOfByte[m] = 0;
/*  63:    */       }
/*  64:369 */       j = k = 0;
/*  65:    */     }
/*  66:371 */     encodeLEInt((int)l2, arrayOfByte, 54);
/*  67:372 */     encodeLEInt((int)(l2 >>> 32), arrayOfByte, 58);
/*  68:373 */     int m = getDigestLength();
/*  69:374 */     arrayOfByte[62] = ((byte)(m << 3));
/*  70:375 */     arrayOfByte[63] = ((byte)(m >>> 5));
/*  71:376 */     process(arrayOfByte, j, k);
/*  72:377 */     for (int n = 0; n < m; n += 4) {
/*  73:378 */       encodeLEInt(this.h[(n >>> 2)], paramArrayOfByte, paramInt + n);
/*  74:    */     }
/*  75:    */   }
/*  76:    */   
/*  77:    */   protected void doInit()
/*  78:    */   {
/*  79:384 */     this.h = new int[8];
/*  80:385 */     this.rk = new int[''];
/*  81:386 */     engineReset();
/*  82:    */   }
/*  83:    */   
/*  84:    */   private static final void encodeLEInt(int paramInt1, byte[] paramArrayOfByte, int paramInt2)
/*  85:    */   {
/*  86:400 */     paramArrayOfByte[(paramInt2 + 0)] = ((byte)paramInt1);
/*  87:401 */     paramArrayOfByte[(paramInt2 + 1)] = ((byte)(paramInt1 >>> 8));
/*  88:402 */     paramArrayOfByte[(paramInt2 + 2)] = ((byte)(paramInt1 >>> 16));
/*  89:403 */     paramArrayOfByte[(paramInt2 + 3)] = ((byte)(paramInt1 >>> 24));
/*  90:    */   }
/*  91:    */   
/*  92:    */   private static final int decodeLEInt(byte[] paramArrayOfByte, int paramInt)
/*  93:    */   {
/*  94:416 */     return paramArrayOfByte[paramInt] & 0xFF | (paramArrayOfByte[(paramInt + 1)] & 0xFF) << 8 | (paramArrayOfByte[(paramInt + 2)] & 0xFF) << 16 | (paramArrayOfByte[(paramInt + 3)] & 0xFF) << 24;
/*  95:    */   }
/*  96:    */   
/*  97:    */   protected void processBlock(byte[] paramArrayOfByte)
/*  98:    */   {
/*  99:425 */     long l = getBlockCount() + 1L << 9;
/* 100:426 */     process(paramArrayOfByte, (int)l, (int)(l >>> 32));
/* 101:    */   }
/* 102:    */   
/* 103:    */   private void process(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/* 104:    */   {
/* 105:434 */     for (int i4 = 0; i4 < 16; i4 += 4)
/* 106:    */     {
/* 107:435 */       this.rk[(i4 + 0)] = decodeLEInt(paramArrayOfByte, (i4 << 2) + 0);
/* 108:436 */       this.rk[(i4 + 1)] = decodeLEInt(paramArrayOfByte, (i4 << 2) + 4);
/* 109:437 */       this.rk[(i4 + 2)] = decodeLEInt(paramArrayOfByte, (i4 << 2) + 8);
/* 110:438 */       this.rk[(i4 + 3)] = decodeLEInt(paramArrayOfByte, (i4 << 2) + 12);
/* 111:    */     }
/* 112:    */     int i6;
/* 113:    */     int i7;
/* 114:    */     int i8;
/* 115:    */     int i9;
/* 116:    */     int i10;
/* 117:    */     int i11;
/* 118:    */     int i12;
/* 119:    */     int i13;
/* 120:440 */     for (int i5 = 0; i5 < 4; i5++)
/* 121:    */     {
/* 122:441 */       for (i6 = 0; i6 < 2; i6++)
/* 123:    */       {
/* 124:445 */         i7 = this.rk[(i4 - 15)];
/* 125:446 */         i8 = this.rk[(i4 - 14)];
/* 126:447 */         i9 = this.rk[(i4 - 13)];
/* 127:448 */         i10 = this.rk[(i4 - 16)];
/* 128:449 */         i11 = AES0[(i7 & 0xFF)] ^ AES1[(i8 >>> 8 & 0xFF)] ^ AES2[(i9 >>> 16 & 0xFF)] ^ AES3[(i10 >>> 24)];
/* 129:    */         
/* 130:    */ 
/* 131:    */ 
/* 132:453 */         i12 = AES0[(i8 & 0xFF)] ^ AES1[(i9 >>> 8 & 0xFF)] ^ AES2[(i10 >>> 16 & 0xFF)] ^ AES3[(i7 >>> 24)];
/* 133:    */         
/* 134:    */ 
/* 135:    */ 
/* 136:457 */         i13 = AES0[(i9 & 0xFF)] ^ AES1[(i10 >>> 8 & 0xFF)] ^ AES2[(i7 >>> 16 & 0xFF)] ^ AES3[(i8 >>> 24)];
/* 137:    */         
/* 138:    */ 
/* 139:    */ 
/* 140:461 */         int i14 = AES0[(i10 & 0xFF)] ^ AES1[(i7 >>> 8 & 0xFF)] ^ AES2[(i8 >>> 16 & 0xFF)] ^ AES3[(i9 >>> 24)];
/* 141:    */         
/* 142:    */ 
/* 143:    */ 
/* 144:465 */         this.rk[(i4 + 0)] = (i11 ^ this.rk[(i4 - 4)]);
/* 145:466 */         this.rk[(i4 + 1)] = (i12 ^ this.rk[(i4 - 3)]);
/* 146:467 */         this.rk[(i4 + 2)] = (i13 ^ this.rk[(i4 - 2)]);
/* 147:468 */         this.rk[(i4 + 3)] = (i14 ^ this.rk[(i4 - 1)]);
/* 148:469 */         if (i4 == 16)
/* 149:    */         {
/* 150:470 */           this.rk[16] ^= paramInt1;
/* 151:471 */           this.rk[17] ^= paramInt2 ^ 0xFFFFFFFF;
/* 152:    */         }
/* 153:472 */         else if (i4 == 56)
/* 154:    */         {
/* 155:473 */           this.rk[57] ^= paramInt2;
/* 156:474 */           this.rk[58] ^= paramInt1 ^ 0xFFFFFFFF;
/* 157:    */         }
/* 158:476 */         i4 += 4;
/* 159:    */         
/* 160:478 */         i7 = this.rk[(i4 - 15)];
/* 161:479 */         i8 = this.rk[(i4 - 14)];
/* 162:480 */         i9 = this.rk[(i4 - 13)];
/* 163:481 */         i10 = this.rk[(i4 - 16)];
/* 164:482 */         i11 = AES0[(i7 & 0xFF)] ^ AES1[(i8 >>> 8 & 0xFF)] ^ AES2[(i9 >>> 16 & 0xFF)] ^ AES3[(i10 >>> 24)];
/* 165:    */         
/* 166:    */ 
/* 167:    */ 
/* 168:486 */         i12 = AES0[(i8 & 0xFF)] ^ AES1[(i9 >>> 8 & 0xFF)] ^ AES2[(i10 >>> 16 & 0xFF)] ^ AES3[(i7 >>> 24)];
/* 169:    */         
/* 170:    */ 
/* 171:    */ 
/* 172:490 */         i13 = AES0[(i9 & 0xFF)] ^ AES1[(i10 >>> 8 & 0xFF)] ^ AES2[(i7 >>> 16 & 0xFF)] ^ AES3[(i8 >>> 24)];
/* 173:    */         
/* 174:    */ 
/* 175:    */ 
/* 176:494 */         i14 = AES0[(i10 & 0xFF)] ^ AES1[(i7 >>> 8 & 0xFF)] ^ AES2[(i8 >>> 16 & 0xFF)] ^ AES3[(i9 >>> 24)];
/* 177:    */         
/* 178:    */ 
/* 179:    */ 
/* 180:498 */         this.rk[(i4 + 0)] = (i11 ^ this.rk[(i4 - 4)]);
/* 181:499 */         this.rk[(i4 + 1)] = (i12 ^ this.rk[(i4 - 3)]);
/* 182:500 */         this.rk[(i4 + 2)] = (i13 ^ this.rk[(i4 - 2)]);
/* 183:501 */         this.rk[(i4 + 3)] = (i14 ^ this.rk[(i4 - 1)]);
/* 184:502 */         if (i4 == 84)
/* 185:    */         {
/* 186:503 */           this.rk[86] ^= paramInt2;
/* 187:504 */           this.rk[87] ^= paramInt1 ^ 0xFFFFFFFF;
/* 188:    */         }
/* 189:505 */         else if (i4 == 124)
/* 190:    */         {
/* 191:506 */           this.rk[124] ^= paramInt1;
/* 192:507 */           this.rk[127] ^= paramInt2 ^ 0xFFFFFFFF;
/* 193:    */         }
/* 194:509 */         i4 += 4;
/* 195:    */       }
/* 196:511 */       for (i6 = 0; i6 < 4; i6++)
/* 197:    */       {
/* 198:512 */         this.rk[(i4 + 0)] = (this.rk[(i4 - 16)] ^ this.rk[(i4 - 3)]);
/* 199:513 */         this.rk[(i4 + 1)] = (this.rk[(i4 - 15)] ^ this.rk[(i4 - 2)]);
/* 200:514 */         this.rk[(i4 + 2)] = (this.rk[(i4 - 14)] ^ this.rk[(i4 - 1)]);
/* 201:515 */         this.rk[(i4 + 3)] = (this.rk[(i4 - 13)] ^ this.rk[(i4 - 0)]);
/* 202:516 */         i4 += 4;
/* 203:    */       }
/* 204:    */     }
/* 205:520 */     int i = this.h[0];
/* 206:521 */     int j = this.h[1];
/* 207:522 */     int k = this.h[2];
/* 208:523 */     int m = this.h[3];
/* 209:524 */     int n = this.h[4];
/* 210:525 */     int i1 = this.h[5];
/* 211:526 */     int i2 = this.h[6];
/* 212:527 */     int i3 = this.h[7];
/* 213:528 */     i4 = 0;
/* 214:529 */     for (i5 = 0; i5 < 6; i5++)
/* 215:    */     {
/* 216:533 */       i6 = n ^ this.rk[(i4++)];
/* 217:534 */       i7 = i1 ^ this.rk[(i4++)];
/* 218:535 */       i8 = i2 ^ this.rk[(i4++)];
/* 219:536 */       i9 = i3 ^ this.rk[(i4++)];
/* 220:537 */       i10 = AES0[(i6 & 0xFF)] ^ AES1[(i7 >>> 8 & 0xFF)] ^ AES2[(i8 >>> 16 & 0xFF)] ^ AES3[(i9 >>> 24)];
/* 221:    */       
/* 222:    */ 
/* 223:    */ 
/* 224:541 */       i11 = AES0[(i7 & 0xFF)] ^ AES1[(i8 >>> 8 & 0xFF)] ^ AES2[(i9 >>> 16 & 0xFF)] ^ AES3[(i6 >>> 24)];
/* 225:    */       
/* 226:    */ 
/* 227:    */ 
/* 228:545 */       i12 = AES0[(i8 & 0xFF)] ^ AES1[(i9 >>> 8 & 0xFF)] ^ AES2[(i6 >>> 16 & 0xFF)] ^ AES3[(i7 >>> 24)];
/* 229:    */       
/* 230:    */ 
/* 231:    */ 
/* 232:549 */       i13 = AES0[(i9 & 0xFF)] ^ AES1[(i6 >>> 8 & 0xFF)] ^ AES2[(i7 >>> 16 & 0xFF)] ^ AES3[(i8 >>> 24)];
/* 233:    */       
/* 234:    */ 
/* 235:    */ 
/* 236:553 */       i6 = i10 ^ this.rk[(i4++)];
/* 237:554 */       i7 = i11 ^ this.rk[(i4++)];
/* 238:555 */       i8 = i12 ^ this.rk[(i4++)];
/* 239:556 */       i9 = i13 ^ this.rk[(i4++)];
/* 240:557 */       i10 = AES0[(i6 & 0xFF)] ^ AES1[(i7 >>> 8 & 0xFF)] ^ AES2[(i8 >>> 16 & 0xFF)] ^ AES3[(i9 >>> 24)];
/* 241:    */       
/* 242:    */ 
/* 243:    */ 
/* 244:561 */       i11 = AES0[(i7 & 0xFF)] ^ AES1[(i8 >>> 8 & 0xFF)] ^ AES2[(i9 >>> 16 & 0xFF)] ^ AES3[(i6 >>> 24)];
/* 245:    */       
/* 246:    */ 
/* 247:    */ 
/* 248:565 */       i12 = AES0[(i8 & 0xFF)] ^ AES1[(i9 >>> 8 & 0xFF)] ^ AES2[(i6 >>> 16 & 0xFF)] ^ AES3[(i7 >>> 24)];
/* 249:    */       
/* 250:    */ 
/* 251:    */ 
/* 252:569 */       i13 = AES0[(i9 & 0xFF)] ^ AES1[(i6 >>> 8 & 0xFF)] ^ AES2[(i7 >>> 16 & 0xFF)] ^ AES3[(i8 >>> 24)];
/* 253:    */       
/* 254:    */ 
/* 255:    */ 
/* 256:573 */       i6 = i10 ^ this.rk[(i4++)];
/* 257:574 */       i7 = i11 ^ this.rk[(i4++)];
/* 258:575 */       i8 = i12 ^ this.rk[(i4++)];
/* 259:576 */       i9 = i13 ^ this.rk[(i4++)];
/* 260:577 */       i10 = AES0[(i6 & 0xFF)] ^ AES1[(i7 >>> 8 & 0xFF)] ^ AES2[(i8 >>> 16 & 0xFF)] ^ AES3[(i9 >>> 24)];
/* 261:    */       
/* 262:    */ 
/* 263:    */ 
/* 264:581 */       i11 = AES0[(i7 & 0xFF)] ^ AES1[(i8 >>> 8 & 0xFF)] ^ AES2[(i9 >>> 16 & 0xFF)] ^ AES3[(i6 >>> 24)];
/* 265:    */       
/* 266:    */ 
/* 267:    */ 
/* 268:585 */       i12 = AES0[(i8 & 0xFF)] ^ AES1[(i9 >>> 8 & 0xFF)] ^ AES2[(i6 >>> 16 & 0xFF)] ^ AES3[(i7 >>> 24)];
/* 269:    */       
/* 270:    */ 
/* 271:    */ 
/* 272:589 */       i13 = AES0[(i9 & 0xFF)] ^ AES1[(i6 >>> 8 & 0xFF)] ^ AES2[(i7 >>> 16 & 0xFF)] ^ AES3[(i8 >>> 24)];
/* 273:    */       
/* 274:    */ 
/* 275:    */ 
/* 276:593 */       i ^= i10;
/* 277:594 */       j ^= i11;
/* 278:595 */       k ^= i12;
/* 279:596 */       m ^= i13;
/* 280:    */       
/* 281:598 */       i6 = i ^ this.rk[(i4++)];
/* 282:599 */       i7 = j ^ this.rk[(i4++)];
/* 283:600 */       i8 = k ^ this.rk[(i4++)];
/* 284:601 */       i9 = m ^ this.rk[(i4++)];
/* 285:602 */       i10 = AES0[(i6 & 0xFF)] ^ AES1[(i7 >>> 8 & 0xFF)] ^ AES2[(i8 >>> 16 & 0xFF)] ^ AES3[(i9 >>> 24)];
/* 286:    */       
/* 287:    */ 
/* 288:    */ 
/* 289:606 */       i11 = AES0[(i7 & 0xFF)] ^ AES1[(i8 >>> 8 & 0xFF)] ^ AES2[(i9 >>> 16 & 0xFF)] ^ AES3[(i6 >>> 24)];
/* 290:    */       
/* 291:    */ 
/* 292:    */ 
/* 293:610 */       i12 = AES0[(i8 & 0xFF)] ^ AES1[(i9 >>> 8 & 0xFF)] ^ AES2[(i6 >>> 16 & 0xFF)] ^ AES3[(i7 >>> 24)];
/* 294:    */       
/* 295:    */ 
/* 296:    */ 
/* 297:614 */       i13 = AES0[(i9 & 0xFF)] ^ AES1[(i6 >>> 8 & 0xFF)] ^ AES2[(i7 >>> 16 & 0xFF)] ^ AES3[(i8 >>> 24)];
/* 298:    */       
/* 299:    */ 
/* 300:    */ 
/* 301:618 */       i6 = i10 ^ this.rk[(i4++)];
/* 302:619 */       i7 = i11 ^ this.rk[(i4++)];
/* 303:620 */       i8 = i12 ^ this.rk[(i4++)];
/* 304:621 */       i9 = i13 ^ this.rk[(i4++)];
/* 305:622 */       i10 = AES0[(i6 & 0xFF)] ^ AES1[(i7 >>> 8 & 0xFF)] ^ AES2[(i8 >>> 16 & 0xFF)] ^ AES3[(i9 >>> 24)];
/* 306:    */       
/* 307:    */ 
/* 308:    */ 
/* 309:626 */       i11 = AES0[(i7 & 0xFF)] ^ AES1[(i8 >>> 8 & 0xFF)] ^ AES2[(i9 >>> 16 & 0xFF)] ^ AES3[(i6 >>> 24)];
/* 310:    */       
/* 311:    */ 
/* 312:    */ 
/* 313:630 */       i12 = AES0[(i8 & 0xFF)] ^ AES1[(i9 >>> 8 & 0xFF)] ^ AES2[(i6 >>> 16 & 0xFF)] ^ AES3[(i7 >>> 24)];
/* 314:    */       
/* 315:    */ 
/* 316:    */ 
/* 317:634 */       i13 = AES0[(i9 & 0xFF)] ^ AES1[(i6 >>> 8 & 0xFF)] ^ AES2[(i7 >>> 16 & 0xFF)] ^ AES3[(i8 >>> 24)];
/* 318:    */       
/* 319:    */ 
/* 320:    */ 
/* 321:638 */       i6 = i10 ^ this.rk[(i4++)];
/* 322:639 */       i7 = i11 ^ this.rk[(i4++)];
/* 323:640 */       i8 = i12 ^ this.rk[(i4++)];
/* 324:641 */       i9 = i13 ^ this.rk[(i4++)];
/* 325:642 */       i10 = AES0[(i6 & 0xFF)] ^ AES1[(i7 >>> 8 & 0xFF)] ^ AES2[(i8 >>> 16 & 0xFF)] ^ AES3[(i9 >>> 24)];
/* 326:    */       
/* 327:    */ 
/* 328:    */ 
/* 329:646 */       i11 = AES0[(i7 & 0xFF)] ^ AES1[(i8 >>> 8 & 0xFF)] ^ AES2[(i9 >>> 16 & 0xFF)] ^ AES3[(i6 >>> 24)];
/* 330:    */       
/* 331:    */ 
/* 332:    */ 
/* 333:650 */       i12 = AES0[(i8 & 0xFF)] ^ AES1[(i9 >>> 8 & 0xFF)] ^ AES2[(i6 >>> 16 & 0xFF)] ^ AES3[(i7 >>> 24)];
/* 334:    */       
/* 335:    */ 
/* 336:    */ 
/* 337:654 */       i13 = AES0[(i9 & 0xFF)] ^ AES1[(i6 >>> 8 & 0xFF)] ^ AES2[(i7 >>> 16 & 0xFF)] ^ AES3[(i8 >>> 24)];
/* 338:    */       
/* 339:    */ 
/* 340:    */ 
/* 341:658 */       n ^= i10;
/* 342:659 */       i1 ^= i11;
/* 343:660 */       i2 ^= i12;
/* 344:661 */       i3 ^= i13;
/* 345:    */     }
/* 346:663 */     this.h[0] ^= i;
/* 347:664 */     this.h[1] ^= j;
/* 348:665 */     this.h[2] ^= k;
/* 349:666 */     this.h[3] ^= m;
/* 350:667 */     this.h[4] ^= n;
/* 351:668 */     this.h[5] ^= i1;
/* 352:669 */     this.h[6] ^= i2;
/* 353:670 */     this.h[7] ^= i3;
/* 354:    */   }
/* 355:    */   
/* 356:    */   public String toString()
/* 357:    */   {
/* 358:676 */     return "SHAvite-" + (getDigestLength() << 3);
/* 359:    */   }
/* 360:    */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     fr.cryptohash.SHAviteSmallCore
 * JD-Core Version:    0.7.1
 */