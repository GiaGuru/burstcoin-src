/*   1:    */ package nxt.at;
/*   2:    */ 
/*   3:    */ public class AT_API_Controller
/*   4:    */ {
/*   5:  5 */   static AT_API_Impl atApi = new AT_API_Impl();
/*   6:    */   
/*   7:    */   public static long func(int paramInt, AT_Machine_State paramAT_Machine_State)
/*   8:    */   {
/*   9:  9 */     long l = 0L;
/*  10: 11 */     if (paramInt == 256) {
/*  11: 13 */       return atApi.get_A1(paramAT_Machine_State);
/*  12:    */     }
/*  13: 15 */     if (paramInt == 257) {
/*  14: 17 */       return atApi.get_A2(paramAT_Machine_State);
/*  15:    */     }
/*  16: 19 */     if (paramInt == 258) {
/*  17: 21 */       return atApi.get_A3(paramAT_Machine_State);
/*  18:    */     }
/*  19: 23 */     if (paramInt == 259) {
/*  20: 25 */       return atApi.get_A4(paramAT_Machine_State);
/*  21:    */     }
/*  22: 27 */     if (paramInt == 260) {
/*  23: 29 */       return atApi.get_B1(paramAT_Machine_State);
/*  24:    */     }
/*  25: 31 */     if (paramInt == 261) {
/*  26: 33 */       return atApi.get_B2(paramAT_Machine_State);
/*  27:    */     }
/*  28: 35 */     if (paramInt == 262) {
/*  29: 37 */       return atApi.get_B3(paramAT_Machine_State);
/*  30:    */     }
/*  31: 39 */     if (paramInt == 263) {
/*  32: 41 */       return atApi.get_B4(paramAT_Machine_State);
/*  33:    */     }
/*  34: 43 */     if (paramInt == 288)
/*  35:    */     {
/*  36: 45 */       atApi.clear_A(paramAT_Machine_State);
/*  37:    */     }
/*  38: 47 */     else if (paramInt == 289)
/*  39:    */     {
/*  40: 49 */       atApi.clear_B(paramAT_Machine_State);
/*  41:    */     }
/*  42: 51 */     else if (paramInt == 290)
/*  43:    */     {
/*  44: 53 */       atApi.clear_A(paramAT_Machine_State);
/*  45: 54 */       atApi.clear_B(paramAT_Machine_State);
/*  46:    */     }
/*  47: 56 */     else if (paramInt == 291)
/*  48:    */     {
/*  49: 58 */       atApi.copy_A_From_B(paramAT_Machine_State);
/*  50:    */     }
/*  51: 60 */     else if (paramInt == 292)
/*  52:    */     {
/*  53: 62 */       atApi.copy_B_From_A(paramAT_Machine_State);
/*  54:    */     }
/*  55:    */     else
/*  56:    */     {
/*  57: 64 */       if (paramInt == 293) {
/*  58: 66 */         return atApi.check_A_Is_Zero(paramAT_Machine_State);
/*  59:    */       }
/*  60: 68 */       if (paramInt == 294) {
/*  61: 70 */         return atApi.check_B_Is_Zero(paramAT_Machine_State);
/*  62:    */       }
/*  63: 72 */       if (paramInt == 295) {
/*  64: 74 */         return atApi.check_A_equals_B(paramAT_Machine_State);
/*  65:    */       }
/*  66: 76 */       if (paramInt == 296)
/*  67:    */       {
/*  68: 78 */         atApi.swap_A_and_B(paramAT_Machine_State);
/*  69:    */       }
/*  70: 80 */       else if (paramInt == 297)
/*  71:    */       {
/*  72: 82 */         atApi.or_A_with_B(paramAT_Machine_State);
/*  73:    */       }
/*  74: 84 */       else if (paramInt == 298)
/*  75:    */       {
/*  76: 86 */         atApi.or_B_with_A(paramAT_Machine_State);
/*  77:    */       }
/*  78: 88 */       else if (paramInt == 299)
/*  79:    */       {
/*  80: 90 */         atApi.and_A_with_B(paramAT_Machine_State);
/*  81:    */       }
/*  82: 92 */       else if (paramInt == 300)
/*  83:    */       {
/*  84: 94 */         atApi.and_B_with_A(paramAT_Machine_State);
/*  85:    */       }
/*  86: 96 */       else if (paramInt == 301)
/*  87:    */       {
/*  88: 98 */         atApi.xor_A_with_B(paramAT_Machine_State);
/*  89:    */       }
/*  90:100 */       else if (paramInt == 302)
/*  91:    */       {
/*  92:102 */         atApi.xor_B_with_A(paramAT_Machine_State);
/*  93:    */       }
/*  94:104 */       else if (paramInt == 320)
/*  95:    */       {
/*  96:106 */         atApi.add_A_to_B(paramAT_Machine_State);
/*  97:    */       }
/*  98:108 */       else if (paramInt == 321)
/*  99:    */       {
/* 100:110 */         atApi.add_B_to_A(paramAT_Machine_State);
/* 101:    */       }
/* 102:112 */       else if (paramInt == 322)
/* 103:    */       {
/* 104:114 */         atApi.sub_A_from_B(paramAT_Machine_State);
/* 105:    */       }
/* 106:116 */       else if (paramInt == 323)
/* 107:    */       {
/* 108:118 */         atApi.sub_B_from_A(paramAT_Machine_State);
/* 109:    */       }
/* 110:120 */       else if (paramInt == 324)
/* 111:    */       {
/* 112:122 */         atApi.mul_A_by_B(paramAT_Machine_State);
/* 113:    */       }
/* 114:124 */       else if (paramInt == 325)
/* 115:    */       {
/* 116:126 */         atApi.mul_B_by_A(paramAT_Machine_State);
/* 117:    */       }
/* 118:128 */       else if (paramInt == 326)
/* 119:    */       {
/* 120:130 */         atApi.div_A_by_B(paramAT_Machine_State);
/* 121:    */       }
/* 122:132 */       else if (paramInt == 327)
/* 123:    */       {
/* 124:134 */         atApi.div_B_by_A(paramAT_Machine_State);
/* 125:    */       }
/* 126:136 */       else if (paramInt == 512)
/* 127:    */       {
/* 128:138 */         atApi.MD5_A_to_B(paramAT_Machine_State);
/* 129:    */       }
/* 130:    */       else
/* 131:    */       {
/* 132:140 */         if (paramInt == 513) {
/* 133:142 */           return atApi.check_MD5_A_with_B(paramAT_Machine_State);
/* 134:    */         }
/* 135:144 */         if (paramInt == 514)
/* 136:    */         {
/* 137:146 */           atApi.HASH160_A_to_B(paramAT_Machine_State);
/* 138:    */         }
/* 139:    */         else
/* 140:    */         {
/* 141:148 */           if (paramInt == 515) {
/* 142:150 */             return atApi.check_HASH160_A_with_B(paramAT_Machine_State);
/* 143:    */           }
/* 144:152 */           if (paramInt == 516)
/* 145:    */           {
/* 146:154 */             atApi.SHA256_A_to_B(paramAT_Machine_State);
/* 147:    */           }
/* 148:    */           else
/* 149:    */           {
/* 150:156 */             if (paramInt == 517) {
/* 151:158 */               return atApi.check_SHA256_A_with_B(paramAT_Machine_State);
/* 152:    */             }
/* 153:160 */             if (paramInt == 768) {
/* 154:162 */               return atApi.get_Block_Timestamp(paramAT_Machine_State);
/* 155:    */             }
/* 156:164 */             if (paramInt == 769) {
/* 157:166 */               return atApi.get_Creation_Timestamp(paramAT_Machine_State);
/* 158:    */             }
/* 159:168 */             if (paramInt == 770) {
/* 160:170 */               return atApi.get_Last_Block_Timestamp(paramAT_Machine_State);
/* 161:    */             }
/* 162:172 */             if (paramInt == 771)
/* 163:    */             {
/* 164:174 */               atApi.put_Last_Block_Hash_In_A(paramAT_Machine_State);
/* 165:    */             }
/* 166:    */             else
/* 167:    */             {
/* 168:176 */               if (paramInt == 773) {
/* 169:178 */                 return atApi.get_Type_for_Tx_in_A(paramAT_Machine_State);
/* 170:    */               }
/* 171:180 */               if (paramInt == 774) {
/* 172:182 */                 return atApi.get_Amount_for_Tx_in_A(paramAT_Machine_State);
/* 173:    */               }
/* 174:184 */               if (paramInt == 775) {
/* 175:186 */                 return atApi.get_Timestamp_for_Tx_in_A(paramAT_Machine_State);
/* 176:    */               }
/* 177:188 */               if (paramInt == 776) {
/* 178:190 */                 return atApi.get_Random_Id_for_Tx_in_A(paramAT_Machine_State);
/* 179:    */               }
/* 180:192 */               if (paramInt == 777)
/* 181:    */               {
/* 182:194 */                 atApi.message_from_Tx_in_A_to_B(paramAT_Machine_State);
/* 183:    */               }
/* 184:196 */               else if (paramInt == 778)
/* 185:    */               {
/* 186:198 */                 atApi.B_to_Address_of_Tx_in_A(paramAT_Machine_State);
/* 187:    */               }
/* 188:200 */               else if (paramInt == 779)
/* 189:    */               {
/* 190:202 */                 atApi.B_to_Address_of_Creator(paramAT_Machine_State);
/* 191:    */               }
/* 192:    */               else
/* 193:    */               {
/* 194:204 */                 if (paramInt == 1024) {
/* 195:206 */                   return atApi.get_Current_Balance(paramAT_Machine_State);
/* 196:    */                 }
/* 197:208 */                 if (paramInt == 1025) {
/* 198:210 */                   return atApi.get_Previous_Balance(paramAT_Machine_State);
/* 199:    */                 }
/* 200:212 */                 if (paramInt == 1027) {
/* 201:214 */                   atApi.send_All_to_Address_in_B(paramAT_Machine_State);
/* 202:216 */                 } else if (paramInt == 1028) {
/* 203:218 */                   atApi.send_Old_to_Address_in_B(paramAT_Machine_State);
/* 204:220 */                 } else if (paramInt == 1029) {
/* 205:222 */                   atApi.send_A_to_Address_in_B(paramAT_Machine_State);
/* 206:    */                 }
/* 207:    */               }
/* 208:    */             }
/* 209:    */           }
/* 210:    */         }
/* 211:    */       }
/* 212:    */     }
/* 213:230 */     return l;
/* 214:    */   }
/* 215:    */   
/* 216:    */   public static long func1(int paramInt, long paramLong, AT_Machine_State paramAT_Machine_State)
/* 217:    */   {
/* 218:235 */     long l = 0L;
/* 219:237 */     if (paramInt == 272) {
/* 220:239 */       atApi.set_A1(paramLong, paramAT_Machine_State);
/* 221:241 */     } else if (paramInt == 273) {
/* 222:243 */       atApi.set_A2(paramLong, paramAT_Machine_State);
/* 223:245 */     } else if (paramInt == 274) {
/* 224:247 */       atApi.set_A3(paramLong, paramAT_Machine_State);
/* 225:249 */     } else if (paramInt == 275) {
/* 226:251 */       atApi.set_A4(paramLong, paramAT_Machine_State);
/* 227:253 */     } else if (paramInt == 278) {
/* 228:255 */       atApi.set_B1(paramLong, paramAT_Machine_State);
/* 229:257 */     } else if (paramInt == 279) {
/* 230:259 */       atApi.set_B2(paramLong, paramAT_Machine_State);
/* 231:261 */     } else if (paramInt == 280) {
/* 232:263 */       atApi.set_B3(paramLong, paramAT_Machine_State);
/* 233:265 */     } else if (paramInt == 281) {
/* 234:267 */       atApi.set_B4(paramLong, paramAT_Machine_State);
/* 235:269 */     } else if (paramInt == 772) {
/* 236:271 */       atApi.A_to_Tx_after_Timestamp(paramLong, paramAT_Machine_State);
/* 237:273 */     } else if (paramInt == 1026) {
/* 238:275 */       atApi.send_to_Address_in_B(paramLong, paramAT_Machine_State);
/* 239:    */     }
/* 240:282 */     return l;
/* 241:    */   }
/* 242:    */   
/* 243:    */   public static long func2(int paramInt, long paramLong1, long paramLong2, AT_Machine_State paramAT_Machine_State)
/* 244:    */   {
/* 245:287 */     long l = 0L;
/* 246:289 */     if (paramInt == 276) {
/* 247:291 */       atApi.set_A1_A2(paramLong1, paramLong2, paramAT_Machine_State);
/* 248:293 */     } else if (paramInt == 277) {
/* 249:295 */       atApi.set_A3_A4(paramLong1, paramLong2, paramAT_Machine_State);
/* 250:297 */     } else if (paramInt == 282) {
/* 251:299 */       atApi.set_B1_B2(paramLong1, paramLong2, paramAT_Machine_State);
/* 252:301 */     } else if (paramInt == 283) {
/* 253:303 */       atApi.set_B3_B4(paramLong1, paramLong2, paramAT_Machine_State);
/* 254:305 */     } else if (paramInt == 1030) {
/* 255:307 */       return atApi.add_Minutes_to_Timestamp(paramLong1, paramLong2, paramAT_Machine_State);
/* 256:    */     }
/* 257:314 */     return l;
/* 258:    */   }
/* 259:    */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.at.AT_API_Controller
 * JD-Core Version:    0.7.1
 */