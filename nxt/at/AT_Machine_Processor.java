/*    1:     */ package nxt.at;
/*    2:     */ 
/*    3:     */ import java.io.PrintStream;
/*    4:     */ import java.nio.ByteBuffer;
/*    5:     */ import java.util.TreeSet;
/*    6:     */ 
/*    7:     */ public class AT_Machine_Processor
/*    8:     */ {
/*    9:     */   protected AT_Machine_State machineData;
/*   10:  14 */   private Fun fun = new Fun(null);
/*   11:     */   
/*   12:     */   private int getFun()
/*   13:     */   {
/*   14:  20 */     if (this.machineData.getMachineState().pc + 2 >= this.machineData.getCsize()) {
/*   15:  21 */       return -1;
/*   16:     */     }
/*   17:  24 */     this.fun.fun = this.machineData.getAp_code().getShort(this.machineData.getMachineState().pc + 1);
/*   18:     */     
/*   19:     */ 
/*   20:  27 */     return 0;
/*   21:     */   }
/*   22:     */   
/*   23:     */   private int getAddr(boolean paramBoolean)
/*   24:     */   {
/*   25:  32 */     if (this.machineData.getMachineState().pc + 4 >= this.machineData.getCsize()) {
/*   26:  33 */       return -1;
/*   27:     */     }
/*   28:  36 */     this.fun.addr1 = this.machineData.getAp_code().getInt(this.machineData.getAp_code().position() + this.machineData.getMachineState().pc + 1);
/*   29:  37 */     if (!validAddr(this.fun.addr1, paramBoolean)) {
/*   30:  38 */       return -1;
/*   31:     */     }
/*   32:  40 */     return 0;
/*   33:     */   }
/*   34:     */   
/*   35:     */   private int getAddrs()
/*   36:     */   {
/*   37:  46 */     if (this.machineData.getMachineState().pc + 4 + 4 >= this.machineData.getCsize()) {
/*   38:  47 */       return -1;
/*   39:     */     }
/*   40:  50 */     this.fun.addr1 = this.machineData.getAp_code().getInt(this.machineData.getMachineState().pc + 1);
/*   41:  51 */     this.fun.addr2 = this.machineData.getAp_code().getInt(this.machineData.getMachineState().pc + 1 + 4);
/*   42:  52 */     if ((!validAddr(this.fun.addr1, false)) || (!validAddr(this.fun.addr2, false))) {
/*   43:  53 */       return -1;
/*   44:     */     }
/*   45:  55 */     return 0;
/*   46:     */   }
/*   47:     */   
/*   48:     */   private int getAddrOff()
/*   49:     */   {
/*   50:  61 */     if (this.machineData.getMachineState().pc + 4 + 1 >= this.machineData.getCsize()) {
/*   51:  62 */       return -1;
/*   52:     */     }
/*   53:  65 */     this.fun.addr1 = this.machineData.getAp_code().getInt(this.machineData.getMachineState().pc + 1);
/*   54:  66 */     this.fun.off = this.machineData.getAp_code().get(this.machineData.getMachineState().pc + 1 + 4);
/*   55:  68 */     if ((!validAddr(this.fun.addr1, false)) || (!validAddr(this.machineData.getMachineState().pc + this.fun.off, true))) {
/*   56:  70 */       return -1;
/*   57:     */     }
/*   58:  72 */     return 0;
/*   59:     */   }
/*   60:     */   
/*   61:     */   private int getAddrsOff()
/*   62:     */   {
/*   63:  79 */     if (this.machineData.getMachineState().pc + 4 + 4 + 1 >= this.machineData.getCsize()) {
/*   64:  80 */       return -1;
/*   65:     */     }
/*   66:  83 */     this.fun.addr1 = this.machineData.getAp_code().getInt(this.machineData.getMachineState().pc + 1);
/*   67:  84 */     this.fun.addr2 = this.machineData.getAp_code().getInt(this.machineData.getMachineState().pc + 1 + 4);
/*   68:  85 */     this.fun.off = this.machineData.getAp_code().get(this.machineData.getMachineState().pc + 1 + 4 + 4);
/*   69:  87 */     if ((!validAddr(this.fun.addr1, false)) || (!validAddr(this.fun.addr2, false)) || (!validAddr(this.machineData.getMachineState().pc + this.fun.off, true))) {
/*   70:  90 */       return -1;
/*   71:     */     }
/*   72:  92 */     return 0;
/*   73:     */   }
/*   74:     */   
/*   75:     */   private int getFunAddr()
/*   76:     */   {
/*   77:  99 */     if (this.machineData.getMachineState().pc + 4 + 4 >= this.machineData.getCsize()) {
/*   78: 100 */       return -1;
/*   79:     */     }
/*   80: 103 */     this.fun.fun = this.machineData.getAp_code().getShort(this.machineData.getMachineState().pc + 1);
/*   81: 104 */     this.fun.addr1 = this.machineData.getAp_code().getInt(this.machineData.getMachineState().pc + 1 + 2);
/*   82: 106 */     if (!validAddr(this.fun.addr1, false)) {
/*   83: 107 */       return -1;
/*   84:     */     }
/*   85: 109 */     return 0;
/*   86:     */   }
/*   87:     */   
/*   88:     */   private int getFunAddrs()
/*   89:     */   {
/*   90: 116 */     if (this.machineData.getMachineState().pc + 4 + 4 + 2 >= this.machineData.getCsize()) {
/*   91: 117 */       return -1;
/*   92:     */     }
/*   93: 120 */     this.fun.fun = this.machineData.getAp_code().getShort(this.machineData.getMachineState().pc + 1);
/*   94:     */     
/*   95: 122 */     this.fun.addr3 = this.machineData.getAp_code().getInt(this.machineData.getMachineState().pc + 1 + 2);
/*   96: 123 */     this.fun.addr2 = this.machineData.getAp_code().getInt(this.machineData.getMachineState().pc + 1 + 2 + 4);
/*   97: 125 */     if ((!validAddr(this.fun.addr3, false)) || (!validAddr(this.fun.addr2, false))) {
/*   98: 127 */       return -1;
/*   99:     */     }
/*  100: 129 */     return 0;
/*  101:     */   }
/*  102:     */   
/*  103:     */   private int getAddressVal()
/*  104:     */   {
/*  105: 135 */     if (this.machineData.getMachineState().pc + 4 + 8 >= this.machineData.getCsize()) {
/*  106: 136 */       return -1;
/*  107:     */     }
/*  108: 139 */     this.fun.addr1 = this.machineData.getAp_code().getInt(this.machineData.getMachineState().pc + 1);
/*  109: 140 */     this.fun.val = this.machineData.getAp_code().getLong(this.machineData.getMachineState().pc + 1 + 4);
/*  110: 142 */     if (!validAddr(this.fun.addr1, false)) {
/*  111: 143 */       return -1;
/*  112:     */     }
/*  113: 145 */     return 0;
/*  114:     */   }
/*  115:     */   
/*  116:     */   private boolean validAddr(int paramInt, boolean paramBoolean)
/*  117:     */   {
/*  118: 151 */     if (paramInt < 0) {
/*  119: 152 */       return false;
/*  120:     */     }
/*  121: 154 */     if ((!paramBoolean) && ((paramInt * 8L + 8L > 2147483647L) || (paramInt * 8 + 8 > this.machineData.getDsize()))) {
/*  122: 156 */       return false;
/*  123:     */     }
/*  124: 158 */     if ((paramBoolean) && (paramInt >= this.machineData.getCsize())) {
/*  125: 159 */       return false;
/*  126:     */     }
/*  127: 161 */     return true;
/*  128:     */   }
/*  129:     */   
/*  130:     */   public AT_Machine_Processor(AT_Machine_State paramAT_Machine_State)
/*  131:     */   {
/*  132: 176 */     this.machineData = paramAT_Machine_State;
/*  133:     */   }
/*  134:     */   
/*  135:     */   protected int processOp(boolean paramBoolean1, boolean paramBoolean2)
/*  136:     */   {
/*  137: 182 */     int i = 0;
/*  138: 184 */     if ((this.machineData.getCsize() < 1) || (this.machineData.getMachineState().pc >= this.machineData.getCsize())) {
/*  139: 185 */       return 0;
/*  140:     */     }
/*  141: 187 */     if (paramBoolean2) {
/*  142: 189 */       this.machineData.getMachineState().jumps.add(Integer.valueOf(this.machineData.getMachineState().pc));
/*  143:     */     }
/*  144: 192 */     int j = this.machineData.getAp_code().get(this.machineData.getMachineState().pc);
/*  145: 194 */     if ((j > 0) && (paramBoolean1) && (!paramBoolean2))
/*  146:     */     {
/*  147: 196 */       System.out.print(String.format("%8x", new Object[] { Integer.valueOf(this.machineData.getMachineState().pc) }).replace(' ', '0'));
/*  148: 197 */       if (this.machineData.getMachineState().pc == this.machineData.getMachineState().opc) {
/*  149: 198 */         System.out.print("* ");
/*  150:     */       } else {
/*  151: 200 */         System.out.print("  ");
/*  152:     */       }
/*  153:     */     }
/*  154: 203 */     if (j == OpCode.e_op_code_NOP)
/*  155:     */     {
/*  156: 204 */       if (paramBoolean1)
/*  157:     */       {
/*  158: 205 */         if (!paramBoolean2) {
/*  159: 206 */           System.out.println("NOP");
/*  160:     */         }
/*  161: 207 */         i++;
/*  162:     */       }
/*  163:     */       else
/*  164:     */       {
/*  165: 210 */         i++;
/*  166: 211 */         this.machineData.getMachineState().pc += 1;
/*  167:     */       }
/*  168:     */     }
/*  169: 214 */     else if (j == OpCode.e_op_code_SET_VAL)
/*  170:     */     {
/*  171: 216 */       i = getAddressVal();
/*  172: 218 */       if ((i == 0) || (paramBoolean1))
/*  173:     */       {
/*  174: 220 */         i = 13;
/*  175: 221 */         if (paramBoolean1)
/*  176:     */         {
/*  177: 223 */           if (!paramBoolean2) {
/*  178: 224 */             System.out.println("SET @" + String.format("%8s", new Object[] { Integer.valueOf(this.fun.addr1) }).replace(' ', '0') + " " + String.format("#%16s", new Object[] { Long.toHexString(this.fun.val) }).replace(' ', '0'));
/*  179:     */           }
/*  180:     */         }
/*  181:     */         else
/*  182:     */         {
/*  183: 228 */           this.machineData.getMachineState().pc += i;
/*  184: 229 */           this.machineData.getAp_data().putLong(this.fun.addr1 * 8, this.fun.val);
/*  185: 230 */           this.machineData.getAp_data().clear();
/*  186:     */         }
/*  187:     */       }
/*  188:     */     }
/*  189: 236 */     else if (j == OpCode.e_op_code_SET_DAT)
/*  190:     */     {
/*  191: 238 */       i = getAddrs();
/*  192: 240 */       if ((i == 0) || (paramBoolean1))
/*  193:     */       {
/*  194: 242 */         i = 9;
/*  195: 244 */         if (paramBoolean1)
/*  196:     */         {
/*  197: 246 */           if (!paramBoolean2) {
/*  198: 247 */             System.out.println("SET @" + String.format("%8s", new Object[] { Integer.valueOf(this.fun.addr1) }).replace(' ', '0') + " $" + String.format("%8s", new Object[] { Integer.valueOf(this.fun.addr2) }).replace(' ', '0'));
/*  199:     */           }
/*  200:     */         }
/*  201:     */         else
/*  202:     */         {
/*  203: 252 */           this.machineData.getMachineState().pc += i;
/*  204: 253 */           this.machineData.getAp_data().putLong(this.fun.addr1 * 8, this.machineData.getAp_data().getLong(this.fun.addr2 * 8));
/*  205: 254 */           this.machineData.getAp_data().clear();
/*  206:     */         }
/*  207:     */       }
/*  208:     */     }
/*  209: 259 */     else if (j == OpCode.e_op_code_CLR_DAT)
/*  210:     */     {
/*  211: 261 */       i = getAddr(false);
/*  212: 263 */       if ((i == 0) || (paramBoolean1))
/*  213:     */       {
/*  214: 265 */         i = 5;
/*  215: 267 */         if (paramBoolean1)
/*  216:     */         {
/*  217: 269 */           if (!paramBoolean2) {
/*  218: 270 */             System.out.println("CLR @" + String.format("%8s", new Object[] { Integer.valueOf(this.fun.addr1) }));
/*  219:     */           }
/*  220:     */         }
/*  221:     */         else
/*  222:     */         {
/*  223: 274 */           this.machineData.getMachineState().pc += i;
/*  224: 275 */           this.machineData.getAp_data().putLong(this.fun.addr1 * 8, 0L);
/*  225: 276 */           this.machineData.getAp_data().clear();
/*  226:     */         }
/*  227:     */       }
/*  228:     */     }
/*  229:     */     else
/*  230:     */     {
/*  231:     */       long l1;
/*  232: 280 */       if ((j == OpCode.e_op_code_INC_DAT) || (j == OpCode.e_op_code_DEC_DAT) || (j == OpCode.e_op_code_NOT_DAT))
/*  233:     */       {
/*  234: 282 */         i = getAddr(false);
/*  235: 283 */         if ((i == 0) || (paramBoolean1))
/*  236:     */         {
/*  237: 285 */           i = 5;
/*  238: 286 */           if (paramBoolean1)
/*  239:     */           {
/*  240: 288 */             if (!paramBoolean2)
/*  241:     */             {
/*  242: 290 */               if (j == OpCode.e_op_code_INC_DAT) {
/*  243: 292 */                 System.out.print("INC @");
/*  244: 294 */               } else if (j == OpCode.e_op_code_DEC_DAT) {
/*  245: 296 */                 System.out.print("DEC @");
/*  246: 298 */               } else if (j == OpCode.e_op_code_NOT_DAT) {
/*  247: 300 */                 System.out.print("NOT @");
/*  248:     */               }
/*  249: 302 */               System.out.println(String.format("%8", new Object[] { Integer.valueOf(this.fun.addr1) }).replace(' ', '0'));
/*  250:     */             }
/*  251:     */           }
/*  252:     */           else
/*  253:     */           {
/*  254: 307 */             this.machineData.getMachineState().pc += i;
/*  255: 308 */             if (j == OpCode.e_op_code_INC_DAT)
/*  256:     */             {
/*  257: 310 */               l1 = this.machineData.getAp_data().getLong(this.fun.addr1 * 8);
/*  258: 311 */               l1 += 1L;
/*  259: 312 */               this.machineData.getAp_data().putLong(this.fun.addr1 * 8, l1);
/*  260: 313 */               this.machineData.getAp_data().clear();
/*  261:     */             }
/*  262: 315 */             else if (j == OpCode.e_op_code_DEC_DAT)
/*  263:     */             {
/*  264: 317 */               l1 = this.machineData.getAp_data().getLong(this.fun.addr1 * 8);
/*  265: 318 */               l1 -= 1L;
/*  266: 319 */               this.machineData.getAp_data().putLong(this.fun.addr1 * 8, l1);
/*  267: 320 */               this.machineData.getAp_data().clear();
/*  268:     */             }
/*  269: 322 */             else if (j == OpCode.e_op_code_NOT_DAT)
/*  270:     */             {
/*  271: 324 */               l1 = this.machineData.getAp_data().getLong(this.fun.addr1 * 8);
/*  272: 325 */               this.machineData.getAp_data().putLong(this.fun.addr1 * 8, l1 ^ 0xFFFFFFFF);
/*  273: 326 */               this.machineData.getAp_data().clear();
/*  274:     */             }
/*  275:     */           }
/*  276:     */         }
/*  277:     */       }
/*  278:     */       else
/*  279:     */       {
/*  280:     */         long l6;
/*  281: 331 */         if ((j == OpCode.e_op_code_ADD_DAT) || (j == OpCode.e_op_code_SUB_DAT) || (j == OpCode.e_op_code_MUL_DAT) || (j == OpCode.e_op_code_DIV_DAT))
/*  282:     */         {
/*  283: 333 */           i = getAddrs();
/*  284: 335 */           if ((i == 0) || (paramBoolean1))
/*  285:     */           {
/*  286: 337 */             i = 9;
/*  287: 338 */             if (paramBoolean1)
/*  288:     */             {
/*  289: 340 */               if (!paramBoolean2)
/*  290:     */               {
/*  291: 342 */                 if (j == OpCode.e_op_code_ADD_DAT) {
/*  292: 344 */                   System.out.print("ADD @");
/*  293: 346 */                 } else if (j == OpCode.e_op_code_SUB_DAT) {
/*  294: 348 */                   System.out.print("SUB @");
/*  295: 350 */                 } else if (j == OpCode.e_op_code_MUL_DAT) {
/*  296: 352 */                   System.out.print("MUL @");
/*  297: 354 */                 } else if (j == OpCode.e_op_code_DIV_DAT) {
/*  298: 356 */                   System.out.print("DIV @");
/*  299:     */                 }
/*  300: 358 */                 System.out.println(String.format("%8x", new Object[] { Integer.valueOf(this.fun.addr1) }).replace(' ', '0') + " $" + String.format("%8s", new Object[] { Integer.valueOf(this.fun.addr2) }).replace(' ', '0'));
/*  301:     */               }
/*  302:     */             }
/*  303:     */             else
/*  304:     */             {
/*  305: 363 */               l1 = this.machineData.getAp_data().getLong(this.fun.addr2 * 8);
/*  306: 364 */               if ((j == OpCode.e_op_code_DIV_DAT) && (l1 == 0L))
/*  307:     */               {
/*  308: 365 */                 i = -2;
/*  309:     */               }
/*  310:     */               else
/*  311:     */               {
/*  312: 368 */                 this.machineData.getMachineState().pc += i;
/*  313:     */                 long l9;
/*  314: 369 */                 if (j == OpCode.e_op_code_ADD_DAT)
/*  315:     */                 {
/*  316: 371 */                   l6 = this.machineData.getAp_data().getLong(this.fun.addr1 * 8);
/*  317: 372 */                   l9 = this.machineData.getAp_data().getLong(this.fun.addr2 * 8);
/*  318: 373 */                   this.machineData.getAp_data().putLong(this.fun.addr1 * 8, l6 + l9);
/*  319: 374 */                   this.machineData.getAp_data().clear();
/*  320:     */                 }
/*  321: 376 */                 else if (j == OpCode.e_op_code_SUB_DAT)
/*  322:     */                 {
/*  323: 378 */                   l6 = this.machineData.getAp_data().getLong(this.fun.addr1 * 8);
/*  324: 379 */                   l9 = this.machineData.getAp_data().getLong(this.fun.addr2 * 8);
/*  325: 380 */                   this.machineData.getAp_data().putLong(this.fun.addr1 * 8, l6 - l9);
/*  326: 381 */                   this.machineData.getAp_data().clear();
/*  327:     */                 }
/*  328: 383 */                 else if (j == OpCode.e_op_code_MUL_DAT)
/*  329:     */                 {
/*  330: 385 */                   l6 = this.machineData.getAp_data().getLong(this.fun.addr1 * 8);
/*  331: 386 */                   l9 = this.machineData.getAp_data().getLong(this.fun.addr2 * 8);
/*  332: 387 */                   this.machineData.getAp_data().putLong(this.fun.addr1 * 8, l6 * l9);
/*  333: 388 */                   this.machineData.getAp_data().clear();
/*  334:     */                 }
/*  335: 390 */                 else if (j == OpCode.e_op_code_DIV_DAT)
/*  336:     */                 {
/*  337: 392 */                   l6 = this.machineData.getAp_data().getLong(this.fun.addr1 * 8);
/*  338: 393 */                   l9 = this.machineData.getAp_data().getLong(this.fun.addr2 * 8);
/*  339: 394 */                   this.machineData.getAp_data().putLong(this.fun.addr1 * 8, l6 / l9);
/*  340: 395 */                   this.machineData.getAp_data().clear();
/*  341:     */                 }
/*  342:     */               }
/*  343:     */             }
/*  344:     */           }
/*  345:     */         }
/*  346: 401 */         else if ((j == OpCode.e_op_code_BOR_DAT) || (j == OpCode.e_op_code_AND_DAT) || (j == OpCode.e_op_code_XOR_DAT))
/*  347:     */         {
/*  348: 403 */           i = getAddrs();
/*  349: 405 */           if ((i == 0) || (paramBoolean1))
/*  350:     */           {
/*  351: 407 */             i = 9;
/*  352: 408 */             if (paramBoolean1)
/*  353:     */             {
/*  354: 410 */               if (!paramBoolean2)
/*  355:     */               {
/*  356: 412 */                 if (j == OpCode.e_op_code_BOR_DAT) {
/*  357: 414 */                   System.out.print("BOR @");
/*  358: 416 */                 } else if (j == OpCode.e_op_code_AND_DAT) {
/*  359: 418 */                   System.out.print("AND @");
/*  360: 420 */                 } else if (j == OpCode.e_op_code_XOR_DAT) {
/*  361: 422 */                   System.out.print("XOR @");
/*  362:     */                 }
/*  363: 424 */                 System.out.println(String.format("%16s $%16s", new Object[] { Integer.valueOf(this.fun.addr1), Integer.valueOf(this.fun.addr2) }).replace(' ', '0'));
/*  364:     */               }
/*  365:     */             }
/*  366:     */             else
/*  367:     */             {
/*  368: 429 */               this.machineData.getMachineState().pc += i;
/*  369: 430 */               l1 = this.machineData.getAp_data().getLong(this.fun.addr2 * 8);
/*  370: 432 */               if (j == OpCode.e_op_code_BOR_DAT)
/*  371:     */               {
/*  372: 434 */                 l6 = this.machineData.getAp_data().getLong(this.fun.addr1 * 8);
/*  373: 435 */                 this.machineData.getAp_data().putLong(this.fun.addr1 * 8, l6 | l1);
/*  374: 436 */                 this.machineData.getAp_data().clear();
/*  375:     */               }
/*  376: 438 */               else if (j == OpCode.e_op_code_AND_DAT)
/*  377:     */               {
/*  378: 440 */                 l6 = this.machineData.getAp_data().getLong(this.fun.addr1 * 8);
/*  379: 441 */                 this.machineData.getAp_data().putLong(this.fun.addr1 * 8, l6 & l1);
/*  380: 442 */                 this.machineData.getAp_data().clear();
/*  381:     */               }
/*  382: 444 */               else if (j == OpCode.e_op_code_XOR_DAT)
/*  383:     */               {
/*  384: 446 */                 l6 = this.machineData.getAp_data().getLong(this.fun.addr1 * 8);
/*  385: 447 */                 this.machineData.getAp_data().putLong(this.fun.addr1 * 8, l6 ^ l1);
/*  386: 448 */                 this.machineData.getAp_data().clear();
/*  387:     */               }
/*  388:     */             }
/*  389:     */           }
/*  390:     */         }
/*  391: 453 */         else if (j == OpCode.e_op_code_SET_IND)
/*  392:     */         {
/*  393: 455 */           i = getAddrs();
/*  394: 457 */           if (i == 0)
/*  395:     */           {
/*  396: 459 */             i = 9;
/*  397: 461 */             if (paramBoolean1)
/*  398:     */             {
/*  399: 463 */               if (!paramBoolean2) {
/*  400: 464 */                 System.out.println("SET @" + String.format("%8s", new Object[] { Integer.valueOf(this.fun.addr1) }).replace(' ', '0') + " " + String.format("$($%8s", new Object[] { Integer.valueOf(this.fun.addr2) }).replace(' ', '0'));
/*  401:     */               }
/*  402:     */             }
/*  403:     */             else
/*  404:     */             {
/*  405: 470 */               l1 = this.machineData.getAp_data().getLong(this.fun.addr2 * 8);
/*  406: 472 */               if (!validAddr((int)l1, false))
/*  407:     */               {
/*  408: 473 */                 i = -1;
/*  409:     */               }
/*  410:     */               else
/*  411:     */               {
/*  412: 476 */                 this.machineData.getMachineState().pc += i;
/*  413: 477 */                 l6 = this.machineData.getAp_data().getLong((int)l1 * 8);
/*  414: 478 */                 this.machineData.getAp_data().putLong(this.fun.addr1 * 8, l6);
/*  415: 479 */                 this.machineData.getAp_data().clear();
/*  416:     */               }
/*  417:     */             }
/*  418:     */           }
/*  419:     */         }
/*  420:     */         else
/*  421:     */         {
/*  422:     */           int i2;
/*  423:     */           int i3;
/*  424:     */           long l8;
/*  425: 484 */           if (j == OpCode.e_op_code_SET_IDX)
/*  426:     */           {
/*  427: 486 */             i = getAddrs();
/*  428: 487 */             int k = this.fun.addr1;
/*  429: 488 */             i2 = this.fun.addr2;
/*  430: 489 */             i3 = 8;
/*  431: 490 */             if ((i == 0) || (paramBoolean1))
/*  432:     */             {
/*  433: 491 */               this.machineData.getAp_code().position(i3);
/*  434: 492 */               i = getAddr(false);
/*  435: 493 */               this.machineData.getAp_code().position(this.machineData.getAp_code().position() - i3);
/*  436: 496 */               if ((i == 0) || (paramBoolean1))
/*  437:     */               {
/*  438: 497 */                 i = 13;
/*  439: 499 */                 if (paramBoolean1)
/*  440:     */                 {
/*  441: 500 */                   if (!paramBoolean2) {
/*  442: 501 */                     System.out.println("");
/*  443:     */                   }
/*  444:     */                 }
/*  445:     */                 else
/*  446:     */                 {
/*  447: 505 */                   l8 = this.machineData.getAp_data().getLong(i2 * 8);
/*  448: 506 */                   long l10 = this.machineData.getAp_data().getLong(this.fun.addr1 * 8);
/*  449:     */                   
/*  450: 508 */                   long l11 = l8 + l10;
/*  451:     */                   
/*  452: 510 */                   System.out.println(this.fun.addr1);
/*  453: 511 */                   if (!validAddr((int)l11, false))
/*  454:     */                   {
/*  455: 512 */                     i = -1;
/*  456:     */                   }
/*  457:     */                   else
/*  458:     */                   {
/*  459: 515 */                     this.machineData.getMachineState().pc += i;
/*  460: 516 */                     this.machineData.getAp_data().putLong(k * 8, this.machineData.getAp_data().getLong((int)l11 * 8));
/*  461: 517 */                     this.machineData.getAp_data().clear();
/*  462:     */                   }
/*  463:     */                 }
/*  464:     */               }
/*  465:     */             }
/*  466:     */           }
/*  467:     */           else
/*  468:     */           {
/*  469:     */             long l2;
/*  470: 524 */             if ((j == OpCode.e_op_code_PSH_DAT) || (j == OpCode.e_op_code_POP_DAT))
/*  471:     */             {
/*  472: 526 */               i = getAddr(false);
/*  473: 527 */               if ((i == 0) || (paramBoolean1))
/*  474:     */               {
/*  475: 529 */                 i = 5;
/*  476: 530 */                 if (paramBoolean1)
/*  477:     */                 {
/*  478: 532 */                   if (!paramBoolean2)
/*  479:     */                   {
/*  480: 534 */                     if (j == OpCode.e_op_code_PSH_DAT) {
/*  481: 535 */                       System.out.print("PSH $");
/*  482:     */                     } else {
/*  483: 537 */                       System.out.print("POP @");
/*  484:     */                     }
/*  485: 539 */                     System.out.println(String.format("%8s", new Object[] { Integer.valueOf(this.fun.addr1) }).replace(' ', '0'));
/*  486:     */                   }
/*  487:     */                 }
/*  488: 543 */                 else if (((j == OpCode.e_op_code_PSH_DAT) && (this.machineData.getMachineState().us == this.machineData.getC_user_stack_bytes() / 8)) || ((j == OpCode.e_op_code_POP_DAT) && (this.machineData.getMachineState().us == 0)))
/*  489:     */                 {
/*  490: 546 */                   i = -1;
/*  491:     */                 }
/*  492:     */                 else
/*  493:     */                 {
/*  494: 550 */                   this.machineData.getMachineState().pc += i;
/*  495: 551 */                   if (j == OpCode.e_op_code_PSH_DAT)
/*  496:     */                   {
/*  497: 553 */                     l2 = this.machineData.getAp_data().getLong(this.fun.addr1 * 8);
/*  498: 554 */                     this.machineData.getMachineState().us += 1;
/*  499: 555 */                     this.machineData.getAp_data().putLong(this.machineData.getDsize() + this.machineData.getC_call_stack_bytes() + this.machineData.getC_user_stack_bytes() - this.machineData.getMachineState().us * 8, l2);
/*  500: 556 */                     this.machineData.getAp_data().clear();
/*  501:     */                   }
/*  502:     */                   else
/*  503:     */                   {
/*  504: 560 */                     l2 = this.machineData.getAp_data().getLong(this.machineData.getDsize() + this.machineData.getC_call_stack_bytes() + this.machineData.getC_user_stack_bytes() - this.machineData.getMachineState().us * 8);
/*  505: 561 */                     this.machineData.getMachineState().us -= 1;
/*  506: 562 */                     this.machineData.getAp_data().putLong(this.fun.addr1 * 8, l2);
/*  507: 563 */                     this.machineData.getAp_data().clear();
/*  508:     */                   }
/*  509:     */                 }
/*  510:     */               }
/*  511:     */             }
/*  512: 568 */             else if (j == OpCode.e_op_code_JMP_SUB)
/*  513:     */             {
/*  514: 570 */               i = getAddr(true);
/*  515: 572 */               if ((i == 0) || (paramBoolean1))
/*  516:     */               {
/*  517: 574 */                 i = 5;
/*  518: 576 */                 if (paramBoolean1)
/*  519:     */                 {
/*  520: 578 */                   if (!paramBoolean2) {
/*  521: 579 */                     System.out.println("JSR :" + String.format("%8s", new Object[] { Integer.valueOf(this.fun.addr1) }).replace(' ', '0'));
/*  522:     */                   }
/*  523:     */                 }
/*  524: 583 */                 else if (this.machineData.getMachineState().cs == this.machineData.getC_call_stack_bytes() / 8)
/*  525:     */                 {
/*  526: 584 */                   i = -1;
/*  527:     */                 }
/*  528: 585 */                 else if (this.machineData.getMachineState().jumps.contains(Integer.valueOf(this.fun.addr1)))
/*  529:     */                 {
/*  530: 588 */                   this.machineData.getMachineState().cs += 1;
/*  531: 589 */                   this.machineData.getAp_data().putLong(this.machineData.getDsize() + this.machineData.getC_call_stack_bytes() - this.machineData.getMachineState().cs * 8, this.machineData.getMachineState().pc + i);
/*  532: 590 */                   this.machineData.getAp_data().clear();
/*  533: 591 */                   this.machineData.getMachineState().pc = this.fun.addr1;
/*  534:     */                 }
/*  535:     */                 else
/*  536:     */                 {
/*  537: 594 */                   i = -2;
/*  538:     */                 }
/*  539:     */               }
/*  540:     */             }
/*  541: 598 */             else if (j == OpCode.e_op_code_RET_SUB)
/*  542:     */             {
/*  543: 600 */               i = 1;
/*  544: 602 */               if (paramBoolean1)
/*  545:     */               {
/*  546: 604 */                 if (!paramBoolean2) {
/*  547: 605 */                   System.out.println("RET\n");
/*  548:     */                 }
/*  549:     */               }
/*  550: 609 */               else if (this.machineData.getMachineState().cs == 0)
/*  551:     */               {
/*  552: 610 */                 i = -1;
/*  553:     */               }
/*  554:     */               else
/*  555:     */               {
/*  556: 613 */                 l2 = this.machineData.getAp_data().getLong(this.machineData.getDsize() + this.machineData.getC_call_stack_bytes() - this.machineData.getMachineState().cs * 8);
/*  557: 614 */                 this.machineData.getMachineState().cs -= 1;
/*  558: 615 */                 i3 = (int)l2;
/*  559: 616 */                 if (this.machineData.getMachineState().jumps.contains(Integer.valueOf(i3))) {
/*  560: 617 */                   this.machineData.getMachineState().pc = i3;
/*  561:     */                 } else {
/*  562: 619 */                   i = -2;
/*  563:     */                 }
/*  564:     */               }
/*  565:     */             }
/*  566: 623 */             else if (j == OpCode.e_op_code_IND_DAT)
/*  567:     */             {
/*  568: 625 */               i = getAddrs();
/*  569: 627 */               if (i == 0)
/*  570:     */               {
/*  571: 629 */                 i = 9;
/*  572: 631 */                 if (paramBoolean1)
/*  573:     */                 {
/*  574: 633 */                   if (!paramBoolean2) {
/*  575: 634 */                     System.out.println("SET @" + String.format("($%8s)", new Object[] { Integer.valueOf(this.fun.addr1) }).replace(' ', '0') + " " + String.format("$%8s", new Object[] { Integer.valueOf(this.fun.addr2) }).replace(' ', '0'));
/*  576:     */                   }
/*  577:     */                 }
/*  578:     */                 else
/*  579:     */                 {
/*  580: 640 */                   l2 = this.machineData.getAp_data().getLong(this.fun.addr1 * 8);
/*  581: 642 */                   if (!validAddr((int)l2, false))
/*  582:     */                   {
/*  583: 643 */                     i = -1;
/*  584:     */                   }
/*  585:     */                   else
/*  586:     */                   {
/*  587: 646 */                     this.machineData.getMachineState().pc += i;
/*  588: 647 */                     this.machineData.getAp_data().putLong((int)l2 * 8, this.machineData.getAp_data().getLong(this.fun.addr2 * 8));
/*  589: 648 */                     this.machineData.getAp_data().clear();
/*  590:     */                   }
/*  591:     */                 }
/*  592:     */               }
/*  593:     */             }
/*  594: 653 */             else if (j == OpCode.e_op_code_IDX_DAT)
/*  595:     */             {
/*  596: 656 */               i = getAddrs();
/*  597: 657 */               int m = this.fun.addr1;
/*  598: 658 */               i2 = this.fun.addr2;
/*  599: 659 */               i3 = 8;
/*  600: 660 */               if ((i == 0) || (paramBoolean1))
/*  601:     */               {
/*  602: 662 */                 this.machineData.getAp_code().position(i3);
/*  603: 663 */                 i = getAddr(false);
/*  604: 664 */                 this.machineData.getAp_code().position(this.machineData.getAp_code().position() - i3);
/*  605: 666 */                 if ((i == 0) || (paramBoolean1))
/*  606:     */                 {
/*  607: 668 */                   i = 13;
/*  608: 670 */                   if (paramBoolean1)
/*  609:     */                   {
/*  610: 672 */                     if (!paramBoolean2) {
/*  611: 673 */                       System.out.println("SET @" + String.format("($%8s+$%8s)", new Object[] { Integer.valueOf(m), Integer.valueOf(i2) }).replace(' ', '0') + " " + String.format("$%8s", new Object[] { Integer.valueOf(this.fun.addr1) }).replace(' ', '0'));
/*  612:     */                     }
/*  613:     */                   }
/*  614:     */                   else
/*  615:     */                   {
/*  616: 679 */                     l8 = this.machineData.getAp_data().getLong(m * 8) + this.machineData.getAp_data().getLong(i2 * 8);
/*  617: 682 */                     if (!validAddr((int)l8, false))
/*  618:     */                     {
/*  619: 683 */                       i = -1;
/*  620:     */                     }
/*  621:     */                     else
/*  622:     */                     {
/*  623: 686 */                       this.machineData.getMachineState().pc += i;
/*  624: 687 */                       this.machineData.getAp_data().putLong((int)l8 * 8, this.machineData.getAp_data().getLong(this.fun.addr1 * 8));
/*  625: 688 */                       this.machineData.getAp_data().clear();
/*  626:     */                     }
/*  627:     */                   }
/*  628:     */                 }
/*  629:     */               }
/*  630:     */             }
/*  631:     */             else
/*  632:     */             {
/*  633:     */               long l3;
/*  634:     */               long l7;
/*  635: 694 */               if (j == OpCode.e_op_code_MOD_DAT)
/*  636:     */               {
/*  637: 696 */                 i = getAddrs();
/*  638: 698 */                 if ((i == 0) || (paramBoolean1))
/*  639:     */                 {
/*  640: 700 */                   i = 9;
/*  641: 701 */                   if (paramBoolean1)
/*  642:     */                   {
/*  643: 703 */                     if (!paramBoolean2) {
/*  644: 704 */                       System.out.println("MOD @" + String.format("%8x", new Object[] { Integer.valueOf(this.fun.addr1) }).replace(' ', '0') + " $" + String.format("%8s", new Object[] { Integer.valueOf(this.fun.addr2) }).replace(' ', '0'));
/*  645:     */                     }
/*  646:     */                   }
/*  647:     */                   else
/*  648:     */                   {
/*  649: 709 */                     l3 = this.machineData.getAp_data().getLong(this.fun.addr1 * 8);
/*  650: 710 */                     l7 = this.machineData.getAp_data().getLong(this.fun.addr2 * 8);
/*  651: 712 */                     if (l7 == 0L)
/*  652:     */                     {
/*  653: 713 */                       i = -2;
/*  654:     */                     }
/*  655:     */                     else
/*  656:     */                     {
/*  657: 716 */                       this.machineData.getMachineState().pc += i;
/*  658: 717 */                       this.machineData.getAp_data().putLong(this.fun.addr1 * 8, l3 % l7);
/*  659:     */                     }
/*  660:     */                   }
/*  661:     */                 }
/*  662:     */               }
/*  663: 722 */               else if ((j == OpCode.e_op_code_SHL_DAT) || (j == OpCode.e_op_code_SHR_DAT))
/*  664:     */               {
/*  665: 724 */                 i = getAddrs();
/*  666: 726 */                 if ((i == 0) || (paramBoolean1))
/*  667:     */                 {
/*  668: 728 */                   i = 9;
/*  669: 729 */                   if (paramBoolean1)
/*  670:     */                   {
/*  671: 731 */                     if (!paramBoolean2) {
/*  672: 733 */                       if (j == OpCode.e_op_code_SHL_DAT) {
/*  673: 734 */                         System.out.println("SHL @" + String.format("%8x", new Object[] { Integer.valueOf(this.fun.addr1) }).replace(' ', '0') + " $" + String.format("%8x", new Object[] { Integer.valueOf(this.fun.addr2) }).replace(' ', '0'));
/*  674:     */                       } else {
/*  675: 737 */                         System.out.println("SHR @" + String.format("%8x", new Object[] { Integer.valueOf(this.fun.addr1) }).replace(' ', '0') + " $" + String.format("%8x", new Object[] { Integer.valueOf(this.fun.addr2) }).replace(' ', '0'));
/*  676:     */                       }
/*  677:     */                     }
/*  678:     */                   }
/*  679:     */                   else
/*  680:     */                   {
/*  681: 743 */                     this.machineData.getMachineState().pc += i;
/*  682: 744 */                     l3 = this.machineData.getAp_data().getLong(this.fun.addr1 * 8);
/*  683: 745 */                     l7 = this.machineData.getAp_data().getLong(this.fun.addr2 * 8);
/*  684: 746 */                     if (l7 < 0L) {
/*  685: 747 */                       l7 = 0L;
/*  686: 748 */                     } else if (l7 > 63L) {
/*  687: 749 */                       l7 = 63L;
/*  688:     */                     }
/*  689: 751 */                     if (j == OpCode.e_op_code_SHL_DAT) {
/*  690: 752 */                       this.machineData.getAp_data().putLong(this.fun.addr1 * 8, l3 << (int)l7);
/*  691:     */                     } else {
/*  692: 754 */                       this.machineData.getAp_data().putLong(this.fun.addr1 * 8, l3 >>> (int)l7);
/*  693:     */                     }
/*  694:     */                   }
/*  695:     */                 }
/*  696:     */               }
/*  697: 758 */               else if (j == OpCode.e_op_code_JMP_ADR)
/*  698:     */               {
/*  699: 760 */                 i = getAddr(true);
/*  700: 762 */                 if ((i == 0) || (paramBoolean1))
/*  701:     */                 {
/*  702: 764 */                   i = 5;
/*  703: 766 */                   if (paramBoolean1)
/*  704:     */                   {
/*  705: 768 */                     if (!paramBoolean2) {
/*  706: 769 */                       System.out.println("JMP :" + String.format("%8x", new Object[] { Integer.valueOf(this.fun.addr1) }));
/*  707:     */                     }
/*  708:     */                   }
/*  709: 771 */                   else if (this.machineData.getMachineState().jumps.contains(Integer.valueOf(this.fun.addr1))) {
/*  710: 772 */                     this.machineData.getMachineState().pc = this.fun.addr1;
/*  711:     */                   } else {
/*  712: 774 */                     i = -2;
/*  713:     */                   }
/*  714:     */                 }
/*  715:     */               }
/*  716: 777 */               else if ((j == OpCode.e_op_code_BZR_DAT) || (j == OpCode.e_op_code_BNZ_DAT))
/*  717:     */               {
/*  718: 779 */                 i = getAddrOff();
/*  719: 781 */                 if ((i == 0) || (paramBoolean1))
/*  720:     */                 {
/*  721: 783 */                   i = 6;
/*  722: 785 */                   if (paramBoolean1)
/*  723:     */                   {
/*  724: 787 */                     if (!paramBoolean2)
/*  725:     */                     {
/*  726: 789 */                       if (j == OpCode.e_op_code_BZR_DAT) {
/*  727: 790 */                         System.out.print("BZR $");
/*  728:     */                       } else {
/*  729: 792 */                         System.out.print("BNZ $");
/*  730:     */                       }
/*  731: 794 */                       System.out.println(String.format("%8x", new Object[] { Integer.valueOf(this.fun.addr1) }).replace(' ', '0') + ", :" + String.format("%8x", new Object[] { Integer.valueOf(this.machineData.getMachineState().pc + this.fun.off) }).replace(' ', '0'));
/*  732:     */                     }
/*  733:     */                   }
/*  734:     */                   else
/*  735:     */                   {
/*  736: 800 */                     l3 = this.machineData.getAp_data().getLong(this.fun.addr1 * 8);
/*  737: 801 */                     if (((j == OpCode.e_op_code_BZR_DAT) && (l3 == 0L)) || ((j == OpCode.e_op_code_BNZ_DAT) && (l3 != 0L)))
/*  738:     */                     {
/*  739: 804 */                       if (this.machineData.getMachineState().jumps.contains(Integer.valueOf(this.machineData.getMachineState().pc + this.fun.off))) {
/*  740: 805 */                         this.machineData.getMachineState().pc += this.fun.off;
/*  741:     */                       } else {
/*  742: 807 */                         i = -2;
/*  743:     */                       }
/*  744:     */                     }
/*  745:     */                     else {
/*  746: 810 */                       this.machineData.getMachineState().pc += i;
/*  747:     */                     }
/*  748:     */                   }
/*  749:     */                 }
/*  750:     */               }
/*  751: 814 */               else if ((j == OpCode.e_op_code_BGT_DAT) || (j == OpCode.e_op_code_BLT_DAT) || (j == OpCode.e_op_code_BGE_DAT) || (j == OpCode.e_op_code_BLE_DAT) || (j == OpCode.e_op_code_BEQ_DAT) || (j == OpCode.e_op_code_BNE_DAT))
/*  752:     */               {
/*  753: 818 */                 i = getAddrsOff();
/*  754: 820 */                 if ((i == 0) || (paramBoolean1))
/*  755:     */                 {
/*  756: 822 */                   i = 10;
/*  757: 824 */                   if (paramBoolean1)
/*  758:     */                   {
/*  759: 826 */                     if (!paramBoolean2)
/*  760:     */                     {
/*  761: 828 */                       if (j == OpCode.e_op_code_BGT_DAT) {
/*  762: 829 */                         System.out.print("BGT $");
/*  763: 830 */                       } else if (j == OpCode.e_op_code_BLT_DAT) {
/*  764: 831 */                         System.out.print("BLT $");
/*  765: 832 */                       } else if (j == OpCode.e_op_code_BGE_DAT) {
/*  766: 833 */                         System.out.print("BGE $");
/*  767: 834 */                       } else if (j == OpCode.e_op_code_BLE_DAT) {
/*  768: 835 */                         System.out.print("BLE $");
/*  769: 836 */                       } else if (j == OpCode.e_op_code_BEQ_DAT) {
/*  770: 837 */                         System.out.print("BEQ $");
/*  771:     */                       } else {
/*  772: 839 */                         System.out.print("BNE $");
/*  773:     */                       }
/*  774: 841 */                       System.out.println(String.format("%8x", new Object[] { Integer.valueOf(this.fun.addr1) }).replace(' ', '0') + " $" + String.format("%8x", new Object[] { Integer.valueOf(this.fun.addr2) }).replace(' ', '0') + " :" + String.format("%8x", new Object[] { Integer.valueOf(this.machineData.getMachineState().pc + this.fun.off) }).replace(' ', '0'));
/*  775:     */                     }
/*  776:     */                   }
/*  777:     */                   else
/*  778:     */                   {
/*  779: 848 */                     l3 = this.machineData.getAp_data().getLong(this.fun.addr1 * 8);
/*  780: 849 */                     l7 = this.machineData.getAp_data().getLong(this.fun.addr2 * 8);
/*  781: 851 */                     if (((j == OpCode.e_op_code_BGT_DAT) && (l3 > l7)) || ((j == OpCode.e_op_code_BLT_DAT) && (l3 < l7)) || ((j == OpCode.e_op_code_BGE_DAT) && (l3 >= l7)) || ((j == OpCode.e_op_code_BLE_DAT) && (l3 <= l7)) || ((j == OpCode.e_op_code_BEQ_DAT) && (l3 == l7)) || ((j == OpCode.e_op_code_BNE_DAT) && (l3 != l7)))
/*  782:     */                     {
/*  783: 858 */                       if (this.machineData.getMachineState().jumps.contains(Integer.valueOf(this.machineData.getMachineState().pc + this.fun.off))) {
/*  784: 859 */                         this.machineData.getMachineState().pc += this.fun.off;
/*  785:     */                       } else {
/*  786: 861 */                         i = -2;
/*  787:     */                       }
/*  788:     */                     }
/*  789:     */                     else {
/*  790: 864 */                       this.machineData.getMachineState().pc += i;
/*  791:     */                     }
/*  792:     */                   }
/*  793:     */                 }
/*  794:     */               }
/*  795: 868 */               else if (j == OpCode.e_op_code_SLP_DAT)
/*  796:     */               {
/*  797: 870 */                 i = getAddr(true);
/*  798: 872 */                 if ((i == 0) || (paramBoolean1))
/*  799:     */                 {
/*  800: 874 */                   i = 5;
/*  801: 876 */                   if (paramBoolean1)
/*  802:     */                   {
/*  803: 878 */                     if (!paramBoolean2) {
/*  804: 879 */                       System.out.println("SLP @" + String.format("%8x", new Object[] { Integer.valueOf(this.fun.addr1) }));
/*  805:     */                     }
/*  806:     */                   }
/*  807:     */                   else
/*  808:     */                   {
/*  809: 884 */                     this.machineData.getMachineState().pc += i;
/*  810: 885 */                     int n = (int)this.machineData.getAp_data().getLong(this.fun.addr1 * 8);
/*  811: 886 */                     if (n < 0) {
/*  812: 887 */                       n = 0;
/*  813:     */                     }
/*  814: 888 */                     i2 = (int)AT_Constants.getInstance().get_MAX_WAIT_FOR_NUM_OF_BLOCKS(this.machineData.getCreationBlockHeight());
/*  815: 889 */                     if (n > i2) {
/*  816: 890 */                       n = i2;
/*  817:     */                     }
/*  818: 891 */                     this.machineData.setWaitForNumberOfBlocks(n);
/*  819: 892 */                     this.machineData.getMachineState().stopped = true;
/*  820:     */                   }
/*  821:     */                 }
/*  822:     */               }
/*  823: 942 */               else if ((j == OpCode.e_op_code_FIZ_DAT) || (j == OpCode.e_op_code_STZ_DAT))
/*  824:     */               {
/*  825: 944 */                 i = getAddr(false);
/*  826: 946 */                 if ((i == 0) || (paramBoolean1))
/*  827:     */                 {
/*  828: 948 */                   i = 5;
/*  829: 950 */                   if (paramBoolean1)
/*  830:     */                   {
/*  831: 952 */                     if (!paramBoolean2)
/*  832:     */                     {
/*  833: 954 */                       if (j == OpCode.e_op_code_FIZ_DAT) {
/*  834: 955 */                         System.out.print("FIZ @");
/*  835:     */                       } else {
/*  836: 957 */                         System.out.print("STZ @");
/*  837:     */                       }
/*  838: 959 */                       System.out.println(String.format("%8x", new Object[] { Integer.valueOf(this.fun.addr1) }).replace(' ', '0'));
/*  839:     */                     }
/*  840:     */                   }
/*  841: 964 */                   else if (this.machineData.getAp_data().getLong(this.fun.addr1 * 8) == 0L)
/*  842:     */                   {
/*  843: 966 */                     if (j == OpCode.e_op_code_STZ_DAT)
/*  844:     */                     {
/*  845: 968 */                       this.machineData.getMachineState().pc += i;
/*  846: 969 */                       this.machineData.getMachineState().stopped = true;
/*  847: 970 */                       this.machineData.setFreeze(true);
/*  848:     */                     }
/*  849:     */                     else
/*  850:     */                     {
/*  851: 974 */                       this.machineData.getMachineState().pc = this.machineData.getMachineState().pcs;
/*  852: 975 */                       this.machineData.getMachineState().finished = true;
/*  853: 976 */                       this.machineData.setFreeze(true);
/*  854:     */                     }
/*  855:     */                   }
/*  856:     */                   else
/*  857:     */                   {
/*  858: 981 */                     i = 5;
/*  859: 982 */                     this.machineData.getMachineState().pc += i;
/*  860:     */                   }
/*  861:     */                 }
/*  862:     */               }
/*  863: 987 */               else if ((j == OpCode.e_op_code_FIN_IMD) || (j == OpCode.e_op_code_STP_IMD))
/*  864:     */               {
/*  865: 989 */                 i = 1;
/*  866: 991 */                 if (paramBoolean1)
/*  867:     */                 {
/*  868: 993 */                   if (!paramBoolean2) {
/*  869: 995 */                     if (j == OpCode.e_op_code_FIN_IMD) {
/*  870: 996 */                       System.out.println("FIN\n");
/*  871:     */                     } else {
/*  872: 998 */                       System.out.println("STP");
/*  873:     */                     }
/*  874:     */                   }
/*  875:     */                 }
/*  876:1001 */                 else if (j == OpCode.e_op_code_STP_IMD)
/*  877:     */                 {
/*  878:1003 */                   this.machineData.getMachineState().pc += i;
/*  879:1004 */                   this.machineData.getMachineState().stopped = true;
/*  880:1005 */                   this.machineData.setFreeze(true);
/*  881:     */                 }
/*  882:     */                 else
/*  883:     */                 {
/*  884:1009 */                   this.machineData.getMachineState().pc = this.machineData.getMachineState().pcs;
/*  885:1010 */                   this.machineData.getMachineState().finished = true;
/*  886:1011 */                   this.machineData.setFreeze(true);
/*  887:     */                 }
/*  888:     */               }
/*  889:1014 */               else if (j == OpCode.e_op_code_SLP_IMD)
/*  890:     */               {
/*  891:1016 */                 i = 1;
/*  892:1018 */                 if (paramBoolean1)
/*  893:     */                 {
/*  894:1020 */                   if (!paramBoolean2) {
/*  895:1022 */                     System.out.println("SLP\n");
/*  896:     */                   }
/*  897:     */                 }
/*  898:     */                 else
/*  899:     */                 {
/*  900:1027 */                   this.machineData.getMachineState().pc += i;
/*  901:1028 */                   this.machineData.getMachineState().stopped = true;
/*  902:1029 */                   this.machineData.setFreeze(true);
/*  903:     */                 }
/*  904:     */               }
/*  905:1033 */               else if (j == OpCode.e_op_code_SET_PCS)
/*  906:     */               {
/*  907:1035 */                 i = 1;
/*  908:1037 */                 if (paramBoolean1)
/*  909:     */                 {
/*  910:1039 */                   if (!paramBoolean2) {
/*  911:1040 */                     System.out.println("PCS");
/*  912:     */                   }
/*  913:     */                 }
/*  914:     */                 else
/*  915:     */                 {
/*  916:1044 */                   this.machineData.getMachineState().pc += i;
/*  917:1045 */                   this.machineData.getMachineState().pcs = this.machineData.getMachineState().pc;
/*  918:     */                 }
/*  919:     */               }
/*  920:1048 */               else if (j == OpCode.e_op_code_EXT_FUN)
/*  921:     */               {
/*  922:1050 */                 i = getFun();
/*  923:1052 */                 if ((i == 0) || (paramBoolean1))
/*  924:     */                 {
/*  925:1054 */                   i = 3;
/*  926:1056 */                   if (paramBoolean1)
/*  927:     */                   {
/*  928:1058 */                     if (!paramBoolean2) {
/*  929:1059 */                       System.out.println("FUN " + this.fun.fun);
/*  930:     */                     }
/*  931:     */                   }
/*  932:     */                   else
/*  933:     */                   {
/*  934:1064 */                     this.machineData.getMachineState().pc += i;
/*  935:1065 */                     AT_API_Controller.func(this.fun.fun, this.machineData);
/*  936:     */                   }
/*  937:     */                 }
/*  938:     */               }
/*  939:     */               else
/*  940:     */               {
/*  941:     */                 long l4;
/*  942:1069 */                 if (j == OpCode.e_op_code_EXT_FUN_DAT)
/*  943:     */                 {
/*  944:1071 */                   i = getFunAddr();
/*  945:1072 */                   if (i == 0)
/*  946:     */                   {
/*  947:1074 */                     i = 7;
/*  948:1076 */                     if (paramBoolean1)
/*  949:     */                     {
/*  950:1078 */                       if (!paramBoolean2) {
/*  951:1079 */                         System.out.println("FUN " + this.fun.fun + " $" + String.format("%8x", new Object[] { Integer.valueOf(this.fun.addr1) }).replace(' ', '0'));
/*  952:     */                       }
/*  953:     */                     }
/*  954:     */                     else
/*  955:     */                     {
/*  956:1084 */                       this.machineData.getMachineState().pc += i;
/*  957:1085 */                       l4 = this.machineData.getAp_data().getLong(this.fun.addr1 * 8);
/*  958:1086 */                       AT_API_Controller.func1(this.fun.fun, l4, this.machineData);
/*  959:     */                     }
/*  960:     */                   }
/*  961:     */                 }
/*  962:1090 */                 else if (j == OpCode.e_op_code_EXT_FUN_DAT_2)
/*  963:     */                 {
/*  964:1092 */                   i = getFunAddrs();
/*  965:1094 */                   if ((i == 0) || (paramBoolean1))
/*  966:     */                   {
/*  967:1096 */                     i = 11;
/*  968:1098 */                     if (paramBoolean1)
/*  969:     */                     {
/*  970:1100 */                       if (!paramBoolean2) {
/*  971:1101 */                         System.out.println("FUN " + this.fun.fun + " $" + String.format("%8x", new Object[] { Integer.valueOf(this.fun.addr3) }).replace(' ', '0') + " $" + String.format("%8x", new Object[] { Integer.valueOf(this.fun.addr2) }).replace(' ', '0'));
/*  972:     */                       }
/*  973:     */                     }
/*  974:     */                     else
/*  975:     */                     {
/*  976:1108 */                       this.machineData.getMachineState().pc += i;
/*  977:1109 */                       l4 = this.machineData.getAp_data().getLong(this.fun.addr3 * 8);
/*  978:1110 */                       l7 = this.machineData.getAp_data().getLong(this.fun.addr2 * 8);
/*  979:     */                       
/*  980:1112 */                       AT_API_Controller.func2(this.fun.fun, l4, l7, this.machineData);
/*  981:     */                     }
/*  982:     */                   }
/*  983:     */                 }
/*  984:1116 */                 else if (j == OpCode.e_op_code_EXT_FUN_RET)
/*  985:     */                 {
/*  986:1118 */                   i = getFunAddr();
/*  987:1120 */                   if ((i == 0) || (paramBoolean1))
/*  988:     */                   {
/*  989:1122 */                     i = 7;
/*  990:1124 */                     if (paramBoolean1)
/*  991:     */                     {
/*  992:1126 */                       if (!paramBoolean2) {
/*  993:1127 */                         System.out.println("FUN @" + String.format("%8x", new Object[] { Integer.valueOf(this.fun.addr1) }).replace(' ', '0') + " " + this.fun.fun);
/*  994:     */                       }
/*  995:     */                     }
/*  996:     */                     else
/*  997:     */                     {
/*  998:1132 */                       this.machineData.getMachineState().pc += i;
/*  999:     */                       
/* 1000:1134 */                       this.machineData.getAp_data().putLong(this.fun.addr1 * 8, AT_API_Controller.func(this.fun.fun, this.machineData));
/* 1001:1135 */                       this.machineData.getAp_data().clear();
/* 1002:     */                     }
/* 1003:     */                   }
/* 1004:     */                 }
/* 1005:1139 */                 else if ((j == OpCode.e_op_code_EXT_FUN_RET_DAT) || (j == OpCode.e_op_code_EXT_FUN_RET_DAT_2))
/* 1006:     */                 {
/* 1007:1141 */                   i = getFunAddrs();
/* 1008:1142 */                   int i1 = 10;
/* 1009:1145 */                   if (((i == 0) || (paramBoolean1)) && (j == OpCode.e_op_code_EXT_FUN_RET_DAT_2))
/* 1010:     */                   {
/* 1011:1147 */                     this.machineData.getAp_code().position(i1);
/* 1012:1148 */                     i = getAddr(false);
/* 1013:1149 */                     this.machineData.getAp_code().position(this.machineData.getAp_code().position() - i1);
/* 1014:     */                   }
/* 1015:1152 */                   if (i == 0)
/* 1016:     */                   {
/* 1017:1154 */                     i = 1 + i1 + (j == OpCode.e_op_code_EXT_FUN_RET_DAT_2 ? 4 : 0);
/* 1018:1156 */                     if (paramBoolean1)
/* 1019:     */                     {
/* 1020:1158 */                       if (!paramBoolean2)
/* 1021:     */                       {
/* 1022:1160 */                         System.out.print("FUN @" + String.format("%8x", new Object[] { Integer.valueOf(this.fun.addr3) }).replace(' ', '0') + " " + this.fun.fun + " $" + String.format("%8x", new Object[] { Integer.valueOf(this.fun.addr2) }).replace(' ', '0'));
/* 1023:1162 */                         if (j == OpCode.e_op_code_EXT_FUN_RET_DAT_2) {
/* 1024:1163 */                           System.out.print(" $" + String.format("%8x", new Object[] { Integer.valueOf(this.fun.addr1) }).replace(' ', '0'));
/* 1025:     */                         }
/* 1026:1166 */                         System.out.println("");
/* 1027:     */                       }
/* 1028:     */                     }
/* 1029:     */                     else
/* 1030:     */                     {
/* 1031:1171 */                       this.machineData.getMachineState().pc += i;
/* 1032:1172 */                       long l5 = this.machineData.getAp_data().getLong(this.fun.addr2 * 8);
/* 1033:1174 */                       if (j != OpCode.e_op_code_EXT_FUN_RET_DAT_2)
/* 1034:     */                       {
/* 1035:1175 */                         this.machineData.getAp_data().putLong(this.fun.addr3 * 8, AT_API_Controller.func1(this.fun.fun, l5, this.machineData));
/* 1036:     */                       }
/* 1037:     */                       else
/* 1038:     */                       {
/* 1039:1179 */                         l8 = this.machineData.getAp_data().getLong(this.fun.addr1 * 8);
/* 1040:1180 */                         this.machineData.getAp_data().putLong(this.fun.addr3 * 8, AT_API_Controller.func2(this.fun.fun, l5, l8, this.machineData));
/* 1041:     */                       }
/* 1042:1182 */                       this.machineData.getAp_data().clear();
/* 1043:     */                     }
/* 1044:     */                   }
/* 1045:     */                 }
/* 1046:1186 */                 else if (j == OpCode.e_op_code_ERR_ADR)
/* 1047:     */                 {
/* 1048:1188 */                   i = getAddr(true);
/* 1049:     */                   
/* 1050:     */ 
/* 1051:     */ 
/* 1052:1192 */                   i = 5;
/* 1053:1194 */                   if (paramBoolean1)
/* 1054:     */                   {
/* 1055:1196 */                     if (!paramBoolean2) {
/* 1056:1197 */                       System.out.println("ERR :" + String.format("%8x", new Object[] { Integer.valueOf(this.fun.addr1) }));
/* 1057:     */                     }
/* 1058:     */                   }
/* 1059:1201 */                   else if ((this.fun.addr1 == -1) || (this.machineData.getMachineState().jumps.contains(Integer.valueOf(this.fun.addr1))))
/* 1060:     */                   {
/* 1061:1203 */                     this.machineData.getMachineState().pc += i;
/* 1062:1204 */                     this.machineData.getMachineState().err = this.fun.addr1;
/* 1063:     */                   }
/* 1064:     */                   else
/* 1065:     */                   {
/* 1066:1207 */                     i = -2;
/* 1067:     */                   }
/* 1068:     */                 }
/* 1069:1212 */                 else if (!paramBoolean1)
/* 1070:     */                 {
/* 1071:1213 */                   i = -2;
/* 1072:     */                 }
/* 1073:     */               }
/* 1074:     */             }
/* 1075:     */           }
/* 1076:     */         }
/* 1077:     */       }
/* 1078:     */     }
/* 1079:1216 */     if ((i == -1) && (paramBoolean1) && (!paramBoolean2)) {
/* 1080:1217 */       System.out.println("\n(overflow)");
/* 1081:     */     }
/* 1082:1219 */     if ((i == -2) && (paramBoolean1) && (!paramBoolean2)) {
/* 1083:1220 */       System.out.println("\n(invalid op)");
/* 1084:     */     }
/* 1085:1226 */     return i;
/* 1086:     */   }
/* 1087:     */   
/* 1088:     */   private class Fun
/* 1089:     */   {
/* 1090:     */     short fun;
/* 1091:     */     int addr1;
/* 1092:     */     int addr2;
/* 1093:     */     long val;
/* 1094:     */     byte off;
/* 1095:     */     int addr3;
/* 1096:     */     
/* 1097:     */     private Fun() {}
/* 1098:     */   }
/* 1099:     */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.at.AT_Machine_Processor
 * JD-Core Version:    0.7.1
 */