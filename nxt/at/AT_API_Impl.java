/*   1:    */ package nxt.at;
/*   2:    */ 
/*   3:    */ import fr.cryptohash.RIPEMD160;
/*   4:    */ import java.math.BigInteger;
/*   5:    */ import java.nio.ByteBuffer;
/*   6:    */ import java.nio.ByteOrder;
/*   7:    */ import java.security.MessageDigest;
/*   8:    */ import java.security.NoSuchAlgorithmException;
/*   9:    */ import java.util.Arrays;
/*  10:    */ 
/*  11:    */ public class AT_API_Impl
/*  12:    */   implements AT_API
/*  13:    */ {
/*  14: 25 */   AT_API_Platform_Impl platform = AT_API_Platform_Impl.getInstance();
/*  15:    */   
/*  16:    */   public long get_A1(AT_Machine_State paramAT_Machine_State)
/*  17:    */   {
/*  18: 30 */     return AT_API_Helper.getLong(paramAT_Machine_State.get_A1());
/*  19:    */   }
/*  20:    */   
/*  21:    */   public long get_A2(AT_Machine_State paramAT_Machine_State)
/*  22:    */   {
/*  23: 35 */     return AT_API_Helper.getLong(paramAT_Machine_State.get_A2());
/*  24:    */   }
/*  25:    */   
/*  26:    */   public long get_A3(AT_Machine_State paramAT_Machine_State)
/*  27:    */   {
/*  28: 40 */     return AT_API_Helper.getLong(paramAT_Machine_State.get_A3());
/*  29:    */   }
/*  30:    */   
/*  31:    */   public long get_A4(AT_Machine_State paramAT_Machine_State)
/*  32:    */   {
/*  33: 45 */     return AT_API_Helper.getLong(paramAT_Machine_State.get_A4());
/*  34:    */   }
/*  35:    */   
/*  36:    */   public long get_B1(AT_Machine_State paramAT_Machine_State)
/*  37:    */   {
/*  38: 50 */     return AT_API_Helper.getLong(paramAT_Machine_State.get_B1());
/*  39:    */   }
/*  40:    */   
/*  41:    */   public long get_B2(AT_Machine_State paramAT_Machine_State)
/*  42:    */   {
/*  43: 55 */     return AT_API_Helper.getLong(paramAT_Machine_State.get_B2());
/*  44:    */   }
/*  45:    */   
/*  46:    */   public long get_B3(AT_Machine_State paramAT_Machine_State)
/*  47:    */   {
/*  48: 60 */     return AT_API_Helper.getLong(paramAT_Machine_State.get_B3());
/*  49:    */   }
/*  50:    */   
/*  51:    */   public long get_B4(AT_Machine_State paramAT_Machine_State)
/*  52:    */   {
/*  53: 65 */     return AT_API_Helper.getLong(paramAT_Machine_State.get_B4());
/*  54:    */   }
/*  55:    */   
/*  56:    */   public void set_A1(long paramLong, AT_Machine_State paramAT_Machine_State)
/*  57:    */   {
/*  58: 70 */     paramAT_Machine_State.set_A1(AT_API_Helper.getByteArray(paramLong));
/*  59:    */   }
/*  60:    */   
/*  61:    */   public void set_A2(long paramLong, AT_Machine_State paramAT_Machine_State)
/*  62:    */   {
/*  63: 75 */     paramAT_Machine_State.set_A2(AT_API_Helper.getByteArray(paramLong));
/*  64:    */   }
/*  65:    */   
/*  66:    */   public void set_A3(long paramLong, AT_Machine_State paramAT_Machine_State)
/*  67:    */   {
/*  68: 80 */     paramAT_Machine_State.set_A3(AT_API_Helper.getByteArray(paramLong));
/*  69:    */   }
/*  70:    */   
/*  71:    */   public void set_A4(long paramLong, AT_Machine_State paramAT_Machine_State)
/*  72:    */   {
/*  73: 85 */     paramAT_Machine_State.set_A4(AT_API_Helper.getByteArray(paramLong));
/*  74:    */   }
/*  75:    */   
/*  76:    */   public void set_A1_A2(long paramLong1, long paramLong2, AT_Machine_State paramAT_Machine_State)
/*  77:    */   {
/*  78: 90 */     paramAT_Machine_State.set_A1(AT_API_Helper.getByteArray(paramLong1));
/*  79: 91 */     paramAT_Machine_State.set_A2(AT_API_Helper.getByteArray(paramLong2));
/*  80:    */   }
/*  81:    */   
/*  82:    */   public void set_A3_A4(long paramLong1, long paramLong2, AT_Machine_State paramAT_Machine_State)
/*  83:    */   {
/*  84: 96 */     paramAT_Machine_State.set_A3(AT_API_Helper.getByteArray(paramLong1));
/*  85: 97 */     paramAT_Machine_State.set_A4(AT_API_Helper.getByteArray(paramLong2));
/*  86:    */   }
/*  87:    */   
/*  88:    */   public void set_B1(long paramLong, AT_Machine_State paramAT_Machine_State)
/*  89:    */   {
/*  90:103 */     paramAT_Machine_State.set_B1(AT_API_Helper.getByteArray(paramLong));
/*  91:    */   }
/*  92:    */   
/*  93:    */   public void set_B2(long paramLong, AT_Machine_State paramAT_Machine_State)
/*  94:    */   {
/*  95:108 */     paramAT_Machine_State.set_B2(AT_API_Helper.getByteArray(paramLong));
/*  96:    */   }
/*  97:    */   
/*  98:    */   public void set_B3(long paramLong, AT_Machine_State paramAT_Machine_State)
/*  99:    */   {
/* 100:113 */     paramAT_Machine_State.set_B3(AT_API_Helper.getByteArray(paramLong));
/* 101:    */   }
/* 102:    */   
/* 103:    */   public void set_B4(long paramLong, AT_Machine_State paramAT_Machine_State)
/* 104:    */   {
/* 105:118 */     paramAT_Machine_State.set_B4(AT_API_Helper.getByteArray(paramLong));
/* 106:    */   }
/* 107:    */   
/* 108:    */   public void set_B1_B2(long paramLong1, long paramLong2, AT_Machine_State paramAT_Machine_State)
/* 109:    */   {
/* 110:123 */     paramAT_Machine_State.set_B1(AT_API_Helper.getByteArray(paramLong1));
/* 111:124 */     paramAT_Machine_State.set_B2(AT_API_Helper.getByteArray(paramLong2));
/* 112:    */   }
/* 113:    */   
/* 114:    */   public void set_B3_B4(long paramLong1, long paramLong2, AT_Machine_State paramAT_Machine_State)
/* 115:    */   {
/* 116:129 */     paramAT_Machine_State.set_B3(AT_API_Helper.getByteArray(paramLong1));
/* 117:130 */     paramAT_Machine_State.set_B4(AT_API_Helper.getByteArray(paramLong2));
/* 118:    */   }
/* 119:    */   
/* 120:    */   public void clear_A(AT_Machine_State paramAT_Machine_State)
/* 121:    */   {
/* 122:135 */     byte[] arrayOfByte = new byte[8];
/* 123:136 */     paramAT_Machine_State.set_A1(arrayOfByte);
/* 124:137 */     paramAT_Machine_State.set_A2(arrayOfByte);
/* 125:138 */     paramAT_Machine_State.set_A3(arrayOfByte);
/* 126:139 */     paramAT_Machine_State.set_A4(arrayOfByte);
/* 127:    */   }
/* 128:    */   
/* 129:    */   public void clear_B(AT_Machine_State paramAT_Machine_State)
/* 130:    */   {
/* 131:144 */     byte[] arrayOfByte = new byte[8];
/* 132:145 */     paramAT_Machine_State.set_B1(arrayOfByte);
/* 133:146 */     paramAT_Machine_State.set_B2(arrayOfByte);
/* 134:147 */     paramAT_Machine_State.set_B3(arrayOfByte);
/* 135:148 */     paramAT_Machine_State.set_B4(arrayOfByte);
/* 136:    */   }
/* 137:    */   
/* 138:    */   public void copy_A_From_B(AT_Machine_State paramAT_Machine_State)
/* 139:    */   {
/* 140:153 */     paramAT_Machine_State.set_A1(paramAT_Machine_State.get_B1());
/* 141:154 */     paramAT_Machine_State.set_A2(paramAT_Machine_State.get_B2());
/* 142:155 */     paramAT_Machine_State.set_A3(paramAT_Machine_State.get_B3());
/* 143:156 */     paramAT_Machine_State.set_A4(paramAT_Machine_State.get_B4());
/* 144:    */   }
/* 145:    */   
/* 146:    */   public void copy_B_From_A(AT_Machine_State paramAT_Machine_State)
/* 147:    */   {
/* 148:161 */     paramAT_Machine_State.set_B1(paramAT_Machine_State.get_A1());
/* 149:162 */     paramAT_Machine_State.set_B2(paramAT_Machine_State.get_A2());
/* 150:163 */     paramAT_Machine_State.set_B3(paramAT_Machine_State.get_A3());
/* 151:164 */     paramAT_Machine_State.set_B4(paramAT_Machine_State.get_A4());
/* 152:    */   }
/* 153:    */   
/* 154:    */   public long check_A_Is_Zero(AT_Machine_State paramAT_Machine_State)
/* 155:    */   {
/* 156:169 */     byte[] arrayOfByte = new byte[8];
/* 157:170 */     return (Arrays.equals(paramAT_Machine_State.get_A1(), arrayOfByte)) && (Arrays.equals(paramAT_Machine_State.get_A2(), arrayOfByte)) && (Arrays.equals(paramAT_Machine_State.get_A3(), arrayOfByte)) && (Arrays.equals(paramAT_Machine_State.get_A4(), arrayOfByte)) ? 0L : 1L;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public long check_B_Is_Zero(AT_Machine_State paramAT_Machine_State)
/* 161:    */   {
/* 162:178 */     byte[] arrayOfByte = new byte[8];
/* 163:179 */     return (Arrays.equals(paramAT_Machine_State.get_B1(), arrayOfByte)) && (Arrays.equals(paramAT_Machine_State.get_B2(), arrayOfByte)) && (Arrays.equals(paramAT_Machine_State.get_B3(), arrayOfByte)) && (Arrays.equals(paramAT_Machine_State.get_B4(), arrayOfByte)) ? 0L : 1L;
/* 164:    */   }
/* 165:    */   
/* 166:    */   public long check_A_equals_B(AT_Machine_State paramAT_Machine_State)
/* 167:    */   {
/* 168:186 */     return (Arrays.equals(paramAT_Machine_State.get_A1(), paramAT_Machine_State.get_B1())) && (Arrays.equals(paramAT_Machine_State.get_A2(), paramAT_Machine_State.get_B2())) && (Arrays.equals(paramAT_Machine_State.get_A3(), paramAT_Machine_State.get_B3())) && (Arrays.equals(paramAT_Machine_State.get_A4(), paramAT_Machine_State.get_B4())) ? 1L : 0L;
/* 169:    */   }
/* 170:    */   
/* 171:    */   public void swap_A_and_B(AT_Machine_State paramAT_Machine_State)
/* 172:    */   {
/* 173:194 */     byte[] arrayOfByte = new byte[8];
/* 174:    */     
/* 175:196 */     arrayOfByte = (byte[])paramAT_Machine_State.get_A1().clone();
/* 176:197 */     paramAT_Machine_State.set_A1(paramAT_Machine_State.get_B1());
/* 177:198 */     paramAT_Machine_State.set_B1(arrayOfByte);
/* 178:    */     
/* 179:200 */     arrayOfByte = (byte[])paramAT_Machine_State.get_A2().clone();
/* 180:201 */     paramAT_Machine_State.set_A2(paramAT_Machine_State.get_B2());
/* 181:202 */     paramAT_Machine_State.set_B2(arrayOfByte);
/* 182:    */     
/* 183:204 */     arrayOfByte = (byte[])paramAT_Machine_State.get_A3().clone();
/* 184:205 */     paramAT_Machine_State.set_A3(paramAT_Machine_State.get_B3());
/* 185:206 */     paramAT_Machine_State.set_B3(arrayOfByte);
/* 186:    */     
/* 187:208 */     arrayOfByte = (byte[])paramAT_Machine_State.get_A4().clone();
/* 188:209 */     paramAT_Machine_State.set_A4(paramAT_Machine_State.get_B4());
/* 189:210 */     paramAT_Machine_State.set_B4(arrayOfByte);
/* 190:    */   }
/* 191:    */   
/* 192:    */   public void add_A_to_B(AT_Machine_State paramAT_Machine_State)
/* 193:    */   {
/* 194:216 */     BigInteger localBigInteger1 = AT_API_Helper.getBigInteger(paramAT_Machine_State.get_A1(), paramAT_Machine_State.get_A2(), paramAT_Machine_State.get_A3(), paramAT_Machine_State.get_A4());
/* 195:217 */     BigInteger localBigInteger2 = AT_API_Helper.getBigInteger(paramAT_Machine_State.get_B1(), paramAT_Machine_State.get_B2(), paramAT_Machine_State.get_B3(), paramAT_Machine_State.get_B4());
/* 196:218 */     BigInteger localBigInteger3 = localBigInteger1.add(localBigInteger2);
/* 197:219 */     ByteBuffer localByteBuffer = ByteBuffer.wrap(AT_API_Helper.getByteArray(localBigInteger3));
/* 198:220 */     localByteBuffer.order(ByteOrder.LITTLE_ENDIAN);
/* 199:    */     
/* 200:222 */     byte[] arrayOfByte = new byte[8];
/* 201:223 */     localByteBuffer.get(arrayOfByte, 0, 8);
/* 202:224 */     paramAT_Machine_State.set_B1(arrayOfByte);
/* 203:225 */     localByteBuffer.get(arrayOfByte, 0, 8);
/* 204:226 */     paramAT_Machine_State.set_B2(arrayOfByte);
/* 205:227 */     localByteBuffer.get(arrayOfByte, 0, 8);
/* 206:228 */     paramAT_Machine_State.set_B3(arrayOfByte);
/* 207:229 */     localByteBuffer.get(arrayOfByte, 0, 8);
/* 208:230 */     paramAT_Machine_State.set_B4(arrayOfByte);
/* 209:    */   }
/* 210:    */   
/* 211:    */   public void add_B_to_A(AT_Machine_State paramAT_Machine_State)
/* 212:    */   {
/* 213:235 */     BigInteger localBigInteger1 = AT_API_Helper.getBigInteger(paramAT_Machine_State.get_A1(), paramAT_Machine_State.get_A2(), paramAT_Machine_State.get_A3(), paramAT_Machine_State.get_A4());
/* 214:236 */     BigInteger localBigInteger2 = AT_API_Helper.getBigInteger(paramAT_Machine_State.get_B1(), paramAT_Machine_State.get_B2(), paramAT_Machine_State.get_B3(), paramAT_Machine_State.get_B4());
/* 215:237 */     BigInteger localBigInteger3 = localBigInteger1.add(localBigInteger2);
/* 216:238 */     ByteBuffer localByteBuffer = ByteBuffer.wrap(AT_API_Helper.getByteArray(localBigInteger3));
/* 217:239 */     localByteBuffer.order(ByteOrder.LITTLE_ENDIAN);
/* 218:    */     
/* 219:241 */     byte[] arrayOfByte = new byte[8];
/* 220:242 */     localByteBuffer.get(arrayOfByte, 0, 8);
/* 221:243 */     paramAT_Machine_State.set_A1(arrayOfByte);
/* 222:244 */     localByteBuffer.get(arrayOfByte, 0, 8);
/* 223:245 */     paramAT_Machine_State.set_A2(arrayOfByte);
/* 224:246 */     localByteBuffer.get(arrayOfByte, 0, 8);
/* 225:247 */     paramAT_Machine_State.set_A3(arrayOfByte);
/* 226:248 */     localByteBuffer.get(arrayOfByte, 0, 8);
/* 227:249 */     paramAT_Machine_State.set_A4(arrayOfByte);
/* 228:    */   }
/* 229:    */   
/* 230:    */   public void sub_A_from_B(AT_Machine_State paramAT_Machine_State)
/* 231:    */   {
/* 232:254 */     BigInteger localBigInteger1 = AT_API_Helper.getBigInteger(paramAT_Machine_State.get_A1(), paramAT_Machine_State.get_A2(), paramAT_Machine_State.get_A3(), paramAT_Machine_State.get_A4());
/* 233:255 */     BigInteger localBigInteger2 = AT_API_Helper.getBigInteger(paramAT_Machine_State.get_B1(), paramAT_Machine_State.get_B2(), paramAT_Machine_State.get_B3(), paramAT_Machine_State.get_B4());
/* 234:256 */     BigInteger localBigInteger3 = localBigInteger2.subtract(localBigInteger1);
/* 235:257 */     ByteBuffer localByteBuffer = ByteBuffer.wrap(AT_API_Helper.getByteArray(localBigInteger3));
/* 236:258 */     localByteBuffer.order(ByteOrder.LITTLE_ENDIAN);
/* 237:    */     
/* 238:260 */     byte[] arrayOfByte = new byte[8];
/* 239:261 */     localByteBuffer.get(arrayOfByte, 0, 8);
/* 240:262 */     paramAT_Machine_State.set_B1(arrayOfByte);
/* 241:263 */     localByteBuffer.get(arrayOfByte, 0, 8);
/* 242:264 */     paramAT_Machine_State.set_B2(arrayOfByte);
/* 243:265 */     localByteBuffer.get(arrayOfByte, 0, 8);
/* 244:266 */     paramAT_Machine_State.set_B3(arrayOfByte);
/* 245:267 */     localByteBuffer.get(arrayOfByte, 0, 8);
/* 246:268 */     paramAT_Machine_State.set_B4(arrayOfByte);
/* 247:    */   }
/* 248:    */   
/* 249:    */   public void sub_B_from_A(AT_Machine_State paramAT_Machine_State)
/* 250:    */   {
/* 251:273 */     BigInteger localBigInteger1 = AT_API_Helper.getBigInteger(paramAT_Machine_State.get_A1(), paramAT_Machine_State.get_A2(), paramAT_Machine_State.get_A3(), paramAT_Machine_State.get_A4());
/* 252:274 */     BigInteger localBigInteger2 = AT_API_Helper.getBigInteger(paramAT_Machine_State.get_B1(), paramAT_Machine_State.get_B2(), paramAT_Machine_State.get_B3(), paramAT_Machine_State.get_B4());
/* 253:275 */     BigInteger localBigInteger3 = localBigInteger1.subtract(localBigInteger2);
/* 254:276 */     ByteBuffer localByteBuffer = ByteBuffer.wrap(AT_API_Helper.getByteArray(localBigInteger3));
/* 255:277 */     localByteBuffer.order(ByteOrder.LITTLE_ENDIAN);
/* 256:    */     
/* 257:279 */     byte[] arrayOfByte = new byte[8];
/* 258:280 */     localByteBuffer.get(arrayOfByte, 0, 8);
/* 259:281 */     paramAT_Machine_State.set_A1(arrayOfByte);
/* 260:282 */     localByteBuffer.get(arrayOfByte, 0, 8);
/* 261:283 */     paramAT_Machine_State.set_A2(arrayOfByte);
/* 262:284 */     localByteBuffer.get(arrayOfByte, 0, 8);
/* 263:285 */     paramAT_Machine_State.set_A3(arrayOfByte);
/* 264:286 */     localByteBuffer.get(arrayOfByte, 0, 8);
/* 265:287 */     paramAT_Machine_State.set_A4(arrayOfByte);
/* 266:    */   }
/* 267:    */   
/* 268:    */   public void mul_A_by_B(AT_Machine_State paramAT_Machine_State)
/* 269:    */   {
/* 270:292 */     BigInteger localBigInteger1 = AT_API_Helper.getBigInteger(paramAT_Machine_State.get_A1(), paramAT_Machine_State.get_A2(), paramAT_Machine_State.get_A3(), paramAT_Machine_State.get_A4());
/* 271:293 */     BigInteger localBigInteger2 = AT_API_Helper.getBigInteger(paramAT_Machine_State.get_B1(), paramAT_Machine_State.get_B2(), paramAT_Machine_State.get_B3(), paramAT_Machine_State.get_B4());
/* 272:294 */     BigInteger localBigInteger3 = localBigInteger1.multiply(localBigInteger2);
/* 273:295 */     ByteBuffer localByteBuffer = ByteBuffer.wrap(AT_API_Helper.getByteArray(localBigInteger3));
/* 274:296 */     localByteBuffer.order(ByteOrder.LITTLE_ENDIAN);
/* 275:    */     
/* 276:298 */     byte[] arrayOfByte = new byte[8];
/* 277:299 */     localByteBuffer.get(arrayOfByte, 0, 8);
/* 278:300 */     paramAT_Machine_State.set_B1(arrayOfByte);
/* 279:301 */     localByteBuffer.get(arrayOfByte, 0, 8);
/* 280:302 */     paramAT_Machine_State.set_B2(arrayOfByte);
/* 281:303 */     localByteBuffer.get(arrayOfByte, 0, 8);
/* 282:304 */     paramAT_Machine_State.set_B3(arrayOfByte);
/* 283:305 */     localByteBuffer.get(arrayOfByte, 0, 8);
/* 284:306 */     paramAT_Machine_State.set_B4(arrayOfByte);
/* 285:    */   }
/* 286:    */   
/* 287:    */   public void mul_B_by_A(AT_Machine_State paramAT_Machine_State)
/* 288:    */   {
/* 289:311 */     BigInteger localBigInteger1 = AT_API_Helper.getBigInteger(paramAT_Machine_State.get_A1(), paramAT_Machine_State.get_A2(), paramAT_Machine_State.get_A3(), paramAT_Machine_State.get_A4());
/* 290:312 */     BigInteger localBigInteger2 = AT_API_Helper.getBigInteger(paramAT_Machine_State.get_B1(), paramAT_Machine_State.get_B2(), paramAT_Machine_State.get_B3(), paramAT_Machine_State.get_B4());
/* 291:313 */     BigInteger localBigInteger3 = localBigInteger1.multiply(localBigInteger2);
/* 292:314 */     ByteBuffer localByteBuffer = ByteBuffer.wrap(AT_API_Helper.getByteArray(localBigInteger3));
/* 293:315 */     localByteBuffer.order(ByteOrder.LITTLE_ENDIAN);
/* 294:    */     
/* 295:317 */     byte[] arrayOfByte = new byte[8];
/* 296:318 */     localByteBuffer.get(arrayOfByte, 0, 8);
/* 297:319 */     paramAT_Machine_State.set_A1(arrayOfByte);
/* 298:320 */     localByteBuffer.get(arrayOfByte, 0, 8);
/* 299:321 */     paramAT_Machine_State.set_A2(arrayOfByte);
/* 300:322 */     localByteBuffer.get(arrayOfByte, 0, 8);
/* 301:323 */     paramAT_Machine_State.set_A3(arrayOfByte);
/* 302:324 */     localByteBuffer.get(arrayOfByte, 0, 8);
/* 303:325 */     paramAT_Machine_State.set_A4(arrayOfByte);
/* 304:    */   }
/* 305:    */   
/* 306:    */   public void div_A_by_B(AT_Machine_State paramAT_Machine_State)
/* 307:    */   {
/* 308:330 */     BigInteger localBigInteger1 = AT_API_Helper.getBigInteger(paramAT_Machine_State.get_A1(), paramAT_Machine_State.get_A2(), paramAT_Machine_State.get_A3(), paramAT_Machine_State.get_A4());
/* 309:331 */     BigInteger localBigInteger2 = AT_API_Helper.getBigInteger(paramAT_Machine_State.get_B1(), paramAT_Machine_State.get_B2(), paramAT_Machine_State.get_B3(), paramAT_Machine_State.get_B4());
/* 310:332 */     if (localBigInteger2.compareTo(BigInteger.ZERO) == 0) {
/* 311:333 */       return;
/* 312:    */     }
/* 313:334 */     BigInteger localBigInteger3 = localBigInteger1.divide(localBigInteger2);
/* 314:335 */     ByteBuffer localByteBuffer = ByteBuffer.wrap(AT_API_Helper.getByteArray(localBigInteger3));
/* 315:336 */     localByteBuffer.order(ByteOrder.LITTLE_ENDIAN);
/* 316:    */     
/* 317:338 */     byte[] arrayOfByte = new byte[8];
/* 318:339 */     localByteBuffer.get(arrayOfByte, 0, 8);
/* 319:340 */     paramAT_Machine_State.set_B1(arrayOfByte);
/* 320:341 */     localByteBuffer.get(arrayOfByte, 0, 8);
/* 321:342 */     paramAT_Machine_State.set_B2(arrayOfByte);
/* 322:343 */     localByteBuffer.get(arrayOfByte, 0, 8);
/* 323:344 */     paramAT_Machine_State.set_B3(arrayOfByte);
/* 324:345 */     localByteBuffer.get(arrayOfByte, 0, 8);
/* 325:346 */     paramAT_Machine_State.set_B4(arrayOfByte);
/* 326:    */   }
/* 327:    */   
/* 328:    */   public void div_B_by_A(AT_Machine_State paramAT_Machine_State)
/* 329:    */   {
/* 330:351 */     BigInteger localBigInteger1 = AT_API_Helper.getBigInteger(paramAT_Machine_State.get_A1(), paramAT_Machine_State.get_A2(), paramAT_Machine_State.get_A3(), paramAT_Machine_State.get_A4());
/* 331:352 */     BigInteger localBigInteger2 = AT_API_Helper.getBigInteger(paramAT_Machine_State.get_B1(), paramAT_Machine_State.get_B2(), paramAT_Machine_State.get_B3(), paramAT_Machine_State.get_B4());
/* 332:353 */     if (localBigInteger1.compareTo(BigInteger.ZERO) == 0) {
/* 333:354 */       return;
/* 334:    */     }
/* 335:355 */     BigInteger localBigInteger3 = localBigInteger2.divide(localBigInteger1);
/* 336:356 */     ByteBuffer localByteBuffer = ByteBuffer.wrap(AT_API_Helper.getByteArray(localBigInteger3));
/* 337:357 */     localByteBuffer.order(ByteOrder.LITTLE_ENDIAN);
/* 338:    */     
/* 339:359 */     byte[] arrayOfByte = new byte[8];
/* 340:360 */     localByteBuffer.get(arrayOfByte, 0, 8);
/* 341:361 */     paramAT_Machine_State.set_A1(arrayOfByte);
/* 342:362 */     localByteBuffer.get(arrayOfByte, 0, 8);
/* 343:363 */     paramAT_Machine_State.set_A2(arrayOfByte);
/* 344:364 */     localByteBuffer.get(arrayOfByte, 0, 8);
/* 345:365 */     paramAT_Machine_State.set_A3(arrayOfByte);
/* 346:366 */     localByteBuffer.get(arrayOfByte, 0, 8);
/* 347:367 */     paramAT_Machine_State.set_A4(arrayOfByte);
/* 348:    */   }
/* 349:    */   
/* 350:    */   public void or_A_with_B(AT_Machine_State paramAT_Machine_State)
/* 351:    */   {
/* 352:372 */     ByteBuffer localByteBuffer1 = ByteBuffer.allocate(32);
/* 353:373 */     localByteBuffer1.order(ByteOrder.LITTLE_ENDIAN);
/* 354:374 */     localByteBuffer1.put(paramAT_Machine_State.get_A1());
/* 355:375 */     localByteBuffer1.put(paramAT_Machine_State.get_A2());
/* 356:376 */     localByteBuffer1.put(paramAT_Machine_State.get_A3());
/* 357:377 */     localByteBuffer1.put(paramAT_Machine_State.get_A4());
/* 358:378 */     localByteBuffer1.clear();
/* 359:    */     
/* 360:380 */     ByteBuffer localByteBuffer2 = ByteBuffer.allocate(32);
/* 361:381 */     localByteBuffer2.order(ByteOrder.LITTLE_ENDIAN);
/* 362:382 */     localByteBuffer2.put(paramAT_Machine_State.get_B1());
/* 363:383 */     localByteBuffer2.put(paramAT_Machine_State.get_B2());
/* 364:384 */     localByteBuffer2.put(paramAT_Machine_State.get_B3());
/* 365:385 */     localByteBuffer2.put(paramAT_Machine_State.get_B4());
/* 366:386 */     localByteBuffer2.clear();
/* 367:    */     
/* 368:388 */     paramAT_Machine_State.set_A1(AT_API_Helper.getByteArray(localByteBuffer1.getLong(0) | localByteBuffer2.getLong(0)));
/* 369:389 */     paramAT_Machine_State.set_A2(AT_API_Helper.getByteArray(localByteBuffer1.getLong(8) | localByteBuffer2.getLong(8)));
/* 370:390 */     paramAT_Machine_State.set_A3(AT_API_Helper.getByteArray(localByteBuffer1.getLong(16) | localByteBuffer2.getLong(16)));
/* 371:391 */     paramAT_Machine_State.set_A4(AT_API_Helper.getByteArray(localByteBuffer1.getLong(24) | localByteBuffer2.getLong(24)));
/* 372:    */   }
/* 373:    */   
/* 374:    */   public void or_B_with_A(AT_Machine_State paramAT_Machine_State)
/* 375:    */   {
/* 376:396 */     ByteBuffer localByteBuffer1 = ByteBuffer.allocate(32);
/* 377:397 */     localByteBuffer1.order(ByteOrder.LITTLE_ENDIAN);
/* 378:398 */     localByteBuffer1.put(paramAT_Machine_State.get_A1());
/* 379:399 */     localByteBuffer1.put(paramAT_Machine_State.get_A2());
/* 380:400 */     localByteBuffer1.put(paramAT_Machine_State.get_A3());
/* 381:401 */     localByteBuffer1.put(paramAT_Machine_State.get_A4());
/* 382:402 */     localByteBuffer1.clear();
/* 383:    */     
/* 384:404 */     ByteBuffer localByteBuffer2 = ByteBuffer.allocate(32);
/* 385:405 */     localByteBuffer2.order(ByteOrder.LITTLE_ENDIAN);
/* 386:406 */     localByteBuffer2.put(paramAT_Machine_State.get_B1());
/* 387:407 */     localByteBuffer2.put(paramAT_Machine_State.get_B2());
/* 388:408 */     localByteBuffer2.put(paramAT_Machine_State.get_B3());
/* 389:409 */     localByteBuffer2.put(paramAT_Machine_State.get_B4());
/* 390:410 */     localByteBuffer2.clear();
/* 391:    */     
/* 392:412 */     paramAT_Machine_State.set_B1(AT_API_Helper.getByteArray(localByteBuffer1.getLong(0) | localByteBuffer2.getLong(0)));
/* 393:413 */     paramAT_Machine_State.set_B2(AT_API_Helper.getByteArray(localByteBuffer1.getLong(8) | localByteBuffer2.getLong(8)));
/* 394:414 */     paramAT_Machine_State.set_B3(AT_API_Helper.getByteArray(localByteBuffer1.getLong(16) | localByteBuffer2.getLong(16)));
/* 395:415 */     paramAT_Machine_State.set_B4(AT_API_Helper.getByteArray(localByteBuffer1.getLong(24) | localByteBuffer2.getLong(24)));
/* 396:    */   }
/* 397:    */   
/* 398:    */   public void and_A_with_B(AT_Machine_State paramAT_Machine_State)
/* 399:    */   {
/* 400:420 */     ByteBuffer localByteBuffer1 = ByteBuffer.allocate(32);
/* 401:421 */     localByteBuffer1.order(ByteOrder.LITTLE_ENDIAN);
/* 402:422 */     localByteBuffer1.put(paramAT_Machine_State.get_A1());
/* 403:423 */     localByteBuffer1.put(paramAT_Machine_State.get_A2());
/* 404:424 */     localByteBuffer1.put(paramAT_Machine_State.get_A3());
/* 405:425 */     localByteBuffer1.put(paramAT_Machine_State.get_A4());
/* 406:426 */     localByteBuffer1.clear();
/* 407:    */     
/* 408:428 */     ByteBuffer localByteBuffer2 = ByteBuffer.allocate(32);
/* 409:429 */     localByteBuffer2.order(ByteOrder.LITTLE_ENDIAN);
/* 410:430 */     localByteBuffer2.put(paramAT_Machine_State.get_B1());
/* 411:431 */     localByteBuffer2.put(paramAT_Machine_State.get_B2());
/* 412:432 */     localByteBuffer2.put(paramAT_Machine_State.get_B3());
/* 413:433 */     localByteBuffer2.put(paramAT_Machine_State.get_B4());
/* 414:434 */     localByteBuffer2.clear();
/* 415:    */     
/* 416:436 */     paramAT_Machine_State.set_A1(AT_API_Helper.getByteArray(localByteBuffer1.getLong(0) & localByteBuffer2.getLong(0)));
/* 417:437 */     paramAT_Machine_State.set_A2(AT_API_Helper.getByteArray(localByteBuffer1.getLong(8) & localByteBuffer2.getLong(8)));
/* 418:438 */     paramAT_Machine_State.set_A3(AT_API_Helper.getByteArray(localByteBuffer1.getLong(16) & localByteBuffer2.getLong(16)));
/* 419:439 */     paramAT_Machine_State.set_A4(AT_API_Helper.getByteArray(localByteBuffer1.getLong(24) & localByteBuffer2.getLong(24)));
/* 420:    */   }
/* 421:    */   
/* 422:    */   public void and_B_with_A(AT_Machine_State paramAT_Machine_State)
/* 423:    */   {
/* 424:444 */     ByteBuffer localByteBuffer1 = ByteBuffer.allocate(32);
/* 425:445 */     localByteBuffer1.order(ByteOrder.LITTLE_ENDIAN);
/* 426:446 */     localByteBuffer1.put(paramAT_Machine_State.get_A1());
/* 427:447 */     localByteBuffer1.put(paramAT_Machine_State.get_A2());
/* 428:448 */     localByteBuffer1.put(paramAT_Machine_State.get_A3());
/* 429:449 */     localByteBuffer1.put(paramAT_Machine_State.get_A4());
/* 430:450 */     localByteBuffer1.clear();
/* 431:    */     
/* 432:452 */     ByteBuffer localByteBuffer2 = ByteBuffer.allocate(32);
/* 433:453 */     localByteBuffer2.order(ByteOrder.LITTLE_ENDIAN);
/* 434:454 */     localByteBuffer2.put(paramAT_Machine_State.get_B1());
/* 435:455 */     localByteBuffer2.put(paramAT_Machine_State.get_B2());
/* 436:456 */     localByteBuffer2.put(paramAT_Machine_State.get_B3());
/* 437:457 */     localByteBuffer2.put(paramAT_Machine_State.get_B4());
/* 438:458 */     localByteBuffer2.clear();
/* 439:    */     
/* 440:460 */     paramAT_Machine_State.set_B1(AT_API_Helper.getByteArray(localByteBuffer1.getLong(0) & localByteBuffer2.getLong(0)));
/* 441:461 */     paramAT_Machine_State.set_B2(AT_API_Helper.getByteArray(localByteBuffer1.getLong(8) & localByteBuffer2.getLong(8)));
/* 442:462 */     paramAT_Machine_State.set_B3(AT_API_Helper.getByteArray(localByteBuffer1.getLong(16) & localByteBuffer2.getLong(16)));
/* 443:463 */     paramAT_Machine_State.set_B4(AT_API_Helper.getByteArray(localByteBuffer1.getLong(24) & localByteBuffer2.getLong(24)));
/* 444:    */   }
/* 445:    */   
/* 446:    */   public void xor_A_with_B(AT_Machine_State paramAT_Machine_State)
/* 447:    */   {
/* 448:468 */     ByteBuffer localByteBuffer1 = ByteBuffer.allocate(32);
/* 449:469 */     localByteBuffer1.order(ByteOrder.LITTLE_ENDIAN);
/* 450:470 */     localByteBuffer1.put(paramAT_Machine_State.get_A1());
/* 451:471 */     localByteBuffer1.put(paramAT_Machine_State.get_A2());
/* 452:472 */     localByteBuffer1.put(paramAT_Machine_State.get_A3());
/* 453:473 */     localByteBuffer1.put(paramAT_Machine_State.get_A4());
/* 454:474 */     localByteBuffer1.clear();
/* 455:    */     
/* 456:476 */     ByteBuffer localByteBuffer2 = ByteBuffer.allocate(32);
/* 457:477 */     localByteBuffer2.order(ByteOrder.LITTLE_ENDIAN);
/* 458:478 */     localByteBuffer2.put(paramAT_Machine_State.get_B1());
/* 459:479 */     localByteBuffer2.put(paramAT_Machine_State.get_B2());
/* 460:480 */     localByteBuffer2.put(paramAT_Machine_State.get_B3());
/* 461:481 */     localByteBuffer2.put(paramAT_Machine_State.get_B4());
/* 462:482 */     localByteBuffer2.clear();
/* 463:    */     
/* 464:484 */     paramAT_Machine_State.set_A1(AT_API_Helper.getByteArray(localByteBuffer1.getLong(0) ^ localByteBuffer2.getLong(0)));
/* 465:485 */     paramAT_Machine_State.set_A2(AT_API_Helper.getByteArray(localByteBuffer1.getLong(8) ^ localByteBuffer2.getLong(8)));
/* 466:486 */     paramAT_Machine_State.set_A3(AT_API_Helper.getByteArray(localByteBuffer1.getLong(16) ^ localByteBuffer2.getLong(16)));
/* 467:487 */     paramAT_Machine_State.set_A4(AT_API_Helper.getByteArray(localByteBuffer1.getLong(24) ^ localByteBuffer2.getLong(24)));
/* 468:    */   }
/* 469:    */   
/* 470:    */   public void xor_B_with_A(AT_Machine_State paramAT_Machine_State)
/* 471:    */   {
/* 472:492 */     ByteBuffer localByteBuffer1 = ByteBuffer.allocate(32);
/* 473:493 */     localByteBuffer1.order(ByteOrder.LITTLE_ENDIAN);
/* 474:494 */     localByteBuffer1.put(paramAT_Machine_State.get_A1());
/* 475:495 */     localByteBuffer1.put(paramAT_Machine_State.get_A2());
/* 476:496 */     localByteBuffer1.put(paramAT_Machine_State.get_A3());
/* 477:497 */     localByteBuffer1.put(paramAT_Machine_State.get_A4());
/* 478:498 */     localByteBuffer1.clear();
/* 479:    */     
/* 480:500 */     ByteBuffer localByteBuffer2 = ByteBuffer.allocate(32);
/* 481:501 */     localByteBuffer2.order(ByteOrder.LITTLE_ENDIAN);
/* 482:502 */     localByteBuffer2.put(paramAT_Machine_State.get_B1());
/* 483:503 */     localByteBuffer2.put(paramAT_Machine_State.get_B2());
/* 484:504 */     localByteBuffer2.put(paramAT_Machine_State.get_B3());
/* 485:505 */     localByteBuffer2.put(paramAT_Machine_State.get_B4());
/* 486:506 */     localByteBuffer2.clear();
/* 487:    */     
/* 488:508 */     paramAT_Machine_State.set_B1(AT_API_Helper.getByteArray(localByteBuffer1.getLong(0) ^ localByteBuffer2.getLong(0)));
/* 489:509 */     paramAT_Machine_State.set_B2(AT_API_Helper.getByteArray(localByteBuffer1.getLong(8) ^ localByteBuffer2.getLong(8)));
/* 490:510 */     paramAT_Machine_State.set_B3(AT_API_Helper.getByteArray(localByteBuffer1.getLong(16) ^ localByteBuffer2.getLong(16)));
/* 491:511 */     paramAT_Machine_State.set_B4(AT_API_Helper.getByteArray(localByteBuffer1.getLong(24) ^ localByteBuffer2.getLong(24)));
/* 492:    */   }
/* 493:    */   
/* 494:    */   public void MD5_A_to_B(AT_Machine_State paramAT_Machine_State)
/* 495:    */   {
/* 496:516 */     ByteBuffer localByteBuffer1 = ByteBuffer.allocate(16);
/* 497:517 */     localByteBuffer1.order(ByteOrder.LITTLE_ENDIAN);
/* 498:    */     
/* 499:519 */     localByteBuffer1.put(paramAT_Machine_State.get_A1());
/* 500:520 */     localByteBuffer1.put(paramAT_Machine_State.get_A2());
/* 501:    */     try
/* 502:    */     {
/* 503:523 */       MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
/* 504:524 */       ByteBuffer localByteBuffer2 = ByteBuffer.wrap(localMessageDigest.digest(localByteBuffer1.array()));
/* 505:525 */       localByteBuffer2.order(ByteOrder.LITTLE_ENDIAN);
/* 506:    */       
/* 507:527 */       paramAT_Machine_State.set_B1(AT_API_Helper.getByteArray(localByteBuffer2.getLong(0)));
/* 508:528 */       paramAT_Machine_State.set_B1(AT_API_Helper.getByteArray(localByteBuffer2.getLong(8)));
/* 509:    */     }
/* 510:    */     catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
/* 511:    */     {
/* 512:532 */       localNoSuchAlgorithmException.printStackTrace();
/* 513:    */     }
/* 514:    */   }
/* 515:    */   
/* 516:    */   public long check_MD5_A_with_B(AT_Machine_State paramAT_Machine_State)
/* 517:    */   {
/* 518:540 */     return (Arrays.equals(paramAT_Machine_State.get_A1(), paramAT_Machine_State.get_B1())) && (Arrays.equals(paramAT_Machine_State.get_A2(), paramAT_Machine_State.get_B2())) ? 1L : 0L;
/* 519:    */   }
/* 520:    */   
/* 521:    */   public void HASH160_A_to_B(AT_Machine_State paramAT_Machine_State)
/* 522:    */   {
/* 523:546 */     ByteBuffer localByteBuffer1 = ByteBuffer.allocate(32);
/* 524:547 */     localByteBuffer1.order(ByteOrder.LITTLE_ENDIAN);
/* 525:    */     
/* 526:549 */     localByteBuffer1.put(paramAT_Machine_State.get_A1());
/* 527:550 */     localByteBuffer1.put(paramAT_Machine_State.get_A2());
/* 528:551 */     localByteBuffer1.put(paramAT_Machine_State.get_A3());
/* 529:552 */     localByteBuffer1.put(paramAT_Machine_State.get_A4());
/* 530:    */     
/* 531:554 */     RIPEMD160 localRIPEMD160 = new RIPEMD160();
/* 532:555 */     ByteBuffer localByteBuffer2 = ByteBuffer.wrap(localRIPEMD160.digest(localByteBuffer1.array()));
/* 533:556 */     localByteBuffer2.order(ByteOrder.LITTLE_ENDIAN);
/* 534:    */     
/* 535:558 */     paramAT_Machine_State.set_B1(AT_API_Helper.getByteArray(localByteBuffer2.getLong(0)));
/* 536:559 */     paramAT_Machine_State.set_B2(AT_API_Helper.getByteArray(localByteBuffer2.getLong(8)));
/* 537:560 */     paramAT_Machine_State.set_B3(AT_API_Helper.getByteArray(localByteBuffer2.getInt(16)));
/* 538:    */   }
/* 539:    */   
/* 540:    */   public long check_HASH160_A_with_B(AT_Machine_State paramAT_Machine_State)
/* 541:    */   {
/* 542:566 */     return (Arrays.equals(paramAT_Machine_State.get_A1(), paramAT_Machine_State.get_B1())) && (Arrays.equals(paramAT_Machine_State.get_A2(), paramAT_Machine_State.get_B2())) && ((AT_API_Helper.getLong(paramAT_Machine_State.get_A3()) & 0xFFFFFFFF) == (AT_API_Helper.getLong(paramAT_Machine_State.get_B3()) & 0xFFFFFFFF)) ? 1L : 0L;
/* 543:    */   }
/* 544:    */   
/* 545:    */   public void SHA256_A_to_B(AT_Machine_State paramAT_Machine_State)
/* 546:    */   {
/* 547:573 */     ByteBuffer localByteBuffer1 = ByteBuffer.allocate(32);
/* 548:574 */     localByteBuffer1.order(ByteOrder.LITTLE_ENDIAN);
/* 549:    */     
/* 550:576 */     localByteBuffer1.put(paramAT_Machine_State.get_A1());
/* 551:577 */     localByteBuffer1.put(paramAT_Machine_State.get_A2());
/* 552:578 */     localByteBuffer1.put(paramAT_Machine_State.get_A3());
/* 553:579 */     localByteBuffer1.put(paramAT_Machine_State.get_A4());
/* 554:    */     try
/* 555:    */     {
/* 556:582 */       MessageDigest localMessageDigest = MessageDigest.getInstance("SHA-256");
/* 557:583 */       ByteBuffer localByteBuffer2 = ByteBuffer.wrap(localMessageDigest.digest(localByteBuffer1.array()));
/* 558:584 */       localByteBuffer2.order(ByteOrder.LITTLE_ENDIAN);
/* 559:    */       
/* 560:586 */       paramAT_Machine_State.set_B1(AT_API_Helper.getByteArray(localByteBuffer2.getLong(0)));
/* 561:587 */       paramAT_Machine_State.set_B2(AT_API_Helper.getByteArray(localByteBuffer2.getLong(8)));
/* 562:588 */       paramAT_Machine_State.set_B3(AT_API_Helper.getByteArray(localByteBuffer2.getLong(16)));
/* 563:589 */       paramAT_Machine_State.set_B4(AT_API_Helper.getByteArray(localByteBuffer2.getLong(24)));
/* 564:    */     }
/* 565:    */     catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
/* 566:    */     {
/* 567:593 */       localNoSuchAlgorithmException.printStackTrace();
/* 568:    */     }
/* 569:    */   }
/* 570:    */   
/* 571:    */   public long check_SHA256_A_with_B(AT_Machine_State paramAT_Machine_State)
/* 572:    */   {
/* 573:600 */     return (Arrays.equals(paramAT_Machine_State.get_A1(), paramAT_Machine_State.get_B1())) && (Arrays.equals(paramAT_Machine_State.get_A2(), paramAT_Machine_State.get_B2())) && (Arrays.equals(paramAT_Machine_State.get_A3(), paramAT_Machine_State.get_B3())) && (Arrays.equals(paramAT_Machine_State.get_A4(), paramAT_Machine_State.get_B4())) ? 1L : 0L;
/* 574:    */   }
/* 575:    */   
/* 576:    */   public long get_Block_Timestamp(AT_Machine_State paramAT_Machine_State)
/* 577:    */   {
/* 578:608 */     return this.platform.get_Block_Timestamp(paramAT_Machine_State);
/* 579:    */   }
/* 580:    */   
/* 581:    */   public long get_Creation_Timestamp(AT_Machine_State paramAT_Machine_State)
/* 582:    */   {
/* 583:614 */     return this.platform.get_Creation_Timestamp(paramAT_Machine_State);
/* 584:    */   }
/* 585:    */   
/* 586:    */   public long get_Last_Block_Timestamp(AT_Machine_State paramAT_Machine_State)
/* 587:    */   {
/* 588:619 */     return this.platform.get_Last_Block_Timestamp(paramAT_Machine_State);
/* 589:    */   }
/* 590:    */   
/* 591:    */   public void put_Last_Block_Hash_In_A(AT_Machine_State paramAT_Machine_State)
/* 592:    */   {
/* 593:624 */     this.platform.put_Last_Block_Hash_In_A(paramAT_Machine_State);
/* 594:    */   }
/* 595:    */   
/* 596:    */   public void A_to_Tx_after_Timestamp(long paramLong, AT_Machine_State paramAT_Machine_State)
/* 597:    */   {
/* 598:630 */     this.platform.A_to_Tx_after_Timestamp(paramLong, paramAT_Machine_State);
/* 599:    */   }
/* 600:    */   
/* 601:    */   public long get_Type_for_Tx_in_A(AT_Machine_State paramAT_Machine_State)
/* 602:    */   {
/* 603:636 */     return this.platform.get_Type_for_Tx_in_A(paramAT_Machine_State);
/* 604:    */   }
/* 605:    */   
/* 606:    */   public long get_Amount_for_Tx_in_A(AT_Machine_State paramAT_Machine_State)
/* 607:    */   {
/* 608:641 */     return this.platform.get_Amount_for_Tx_in_A(paramAT_Machine_State);
/* 609:    */   }
/* 610:    */   
/* 611:    */   public long get_Timestamp_for_Tx_in_A(AT_Machine_State paramAT_Machine_State)
/* 612:    */   {
/* 613:646 */     return this.platform.get_Timestamp_for_Tx_in_A(paramAT_Machine_State);
/* 614:    */   }
/* 615:    */   
/* 616:    */   public long get_Random_Id_for_Tx_in_A(AT_Machine_State paramAT_Machine_State)
/* 617:    */   {
/* 618:651 */     return this.platform.get_Random_Id_for_Tx_in_A(paramAT_Machine_State);
/* 619:    */   }
/* 620:    */   
/* 621:    */   public void message_from_Tx_in_A_to_B(AT_Machine_State paramAT_Machine_State)
/* 622:    */   {
/* 623:656 */     this.platform.message_from_Tx_in_A_to_B(paramAT_Machine_State);
/* 624:    */   }
/* 625:    */   
/* 626:    */   public void B_to_Address_of_Tx_in_A(AT_Machine_State paramAT_Machine_State)
/* 627:    */   {
/* 628:662 */     this.platform.B_to_Address_of_Tx_in_A(paramAT_Machine_State);
/* 629:    */   }
/* 630:    */   
/* 631:    */   public void B_to_Address_of_Creator(AT_Machine_State paramAT_Machine_State)
/* 632:    */   {
/* 633:667 */     this.platform.B_to_Address_of_Creator(paramAT_Machine_State);
/* 634:    */   }
/* 635:    */   
/* 636:    */   public long get_Current_Balance(AT_Machine_State paramAT_Machine_State)
/* 637:    */   {
/* 638:673 */     return this.platform.get_Current_Balance(paramAT_Machine_State);
/* 639:    */   }
/* 640:    */   
/* 641:    */   public long get_Previous_Balance(AT_Machine_State paramAT_Machine_State)
/* 642:    */   {
/* 643:678 */     return this.platform.get_Previous_Balance(paramAT_Machine_State);
/* 644:    */   }
/* 645:    */   
/* 646:    */   public void send_to_Address_in_B(long paramLong, AT_Machine_State paramAT_Machine_State)
/* 647:    */   {
/* 648:683 */     this.platform.send_to_Address_in_B(paramLong, paramAT_Machine_State);
/* 649:    */   }
/* 650:    */   
/* 651:    */   public void send_All_to_Address_in_B(AT_Machine_State paramAT_Machine_State)
/* 652:    */   {
/* 653:688 */     this.platform.send_All_to_Address_in_B(paramAT_Machine_State);
/* 654:    */   }
/* 655:    */   
/* 656:    */   public void send_Old_to_Address_in_B(AT_Machine_State paramAT_Machine_State)
/* 657:    */   {
/* 658:693 */     this.platform.send_Old_to_Address_in_B(paramAT_Machine_State);
/* 659:    */   }
/* 660:    */   
/* 661:    */   public void send_A_to_Address_in_B(AT_Machine_State paramAT_Machine_State)
/* 662:    */   {
/* 663:698 */     this.platform.send_A_to_Address_in_B(paramAT_Machine_State);
/* 664:    */   }
/* 665:    */   
/* 666:    */   public long add_Minutes_to_Timestamp(long paramLong1, long paramLong2, AT_Machine_State paramAT_Machine_State)
/* 667:    */   {
/* 668:703 */     return this.platform.add_Minutes_to_Timestamp(paramLong1, paramLong2, paramAT_Machine_State);
/* 669:    */   }
/* 670:    */   
/* 671:    */   public void set_Min_Activation_Amount(long paramLong, AT_Machine_State paramAT_Machine_State)
/* 672:    */   {
/* 673:708 */     paramAT_Machine_State.setMinActivationAmount(paramLong);
/* 674:    */   }
/* 675:    */   
/* 676:    */   public void put_Last_Block_Generation_Signature_In_A(AT_Machine_State paramAT_Machine_State)
/* 677:    */   {
/* 678:713 */     this.platform.put_Last_Block_Generation_Signature_In_A(paramAT_Machine_State);
/* 679:    */   }
/* 680:    */   
/* 681:    */   public void SHA256_to_B(long paramLong1, long paramLong2, AT_Machine_State paramAT_Machine_State)
/* 682:    */   {
/* 683:718 */     if ((paramLong1 < 0L) || (paramLong2 < 0L) || (paramLong1 + paramLong2 - 1L < 0L) || (paramLong1 * 8L + 8L > 2147483647L) || (paramLong1 * 8L + 8L > paramAT_Machine_State.getDsize()) || ((paramLong1 + paramLong2 - 1L) * 8L + 8L > 2147483647L) || ((paramLong1 + paramLong2 - 1L) * 8L + 8L > paramAT_Machine_State.getDsize())) {
/* 684:725 */       return;
/* 685:    */     }
/* 686:    */     try
/* 687:    */     {
/* 688:729 */       MessageDigest localMessageDigest = MessageDigest.getInstance("SHA-256");
/* 689:730 */       localMessageDigest.update(paramAT_Machine_State.getAp_data().array(), (int)paramLong1, (int)(paramLong2 > 256L ? 256L : paramLong2));
/* 690:731 */       ByteBuffer localByteBuffer = ByteBuffer.wrap(localMessageDigest.digest());
/* 691:732 */       localByteBuffer.order(ByteOrder.LITTLE_ENDIAN);
/* 692:    */       
/* 693:734 */       paramAT_Machine_State.set_B1(AT_API_Helper.getByteArray(localByteBuffer.getLong(0)));
/* 694:735 */       paramAT_Machine_State.set_B2(AT_API_Helper.getByteArray(localByteBuffer.getLong(8)));
/* 695:736 */       paramAT_Machine_State.set_B3(AT_API_Helper.getByteArray(localByteBuffer.getLong(16)));
/* 696:737 */       paramAT_Machine_State.set_B4(AT_API_Helper.getByteArray(localByteBuffer.getLong(24)));
/* 697:    */     }
/* 698:    */     catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
/* 699:    */     {
/* 700:741 */       localNoSuchAlgorithmException.printStackTrace();
/* 701:    */     }
/* 702:    */   }
/* 703:    */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.at.AT_API_Impl
 * JD-Core Version:    0.7.1
 */