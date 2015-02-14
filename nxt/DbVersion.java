/*   1:    */ package nxt;
/*   2:    */ 
/*   3:    */ import java.sql.Connection;
/*   4:    */ import java.sql.ResultSet;
/*   5:    */ import java.sql.SQLException;
/*   6:    */ import java.sql.Statement;
/*   7:    */ import nxt.db.Db;
/*   8:    */ import nxt.util.Logger;
/*   9:    */ 
/*  10:    */ final class DbVersion
/*  11:    */ {
/*  12:    */   static void init()
/*  13:    */   {
/*  14:    */     try
/*  15:    */     {
/*  16: 14 */       Connection localConnection = Db.beginTransaction();Object localObject1 = null;
/*  17:    */       try
/*  18:    */       {
/*  19: 14 */         Statement localStatement = localConnection.createStatement();Object localObject2 = null;
/*  20:    */         try
/*  21:    */         {
/*  22: 15 */           int i = 1;
/*  23:    */           try
/*  24:    */           {
/*  25: 17 */             ResultSet localResultSet = localStatement.executeQuery("SELECT next_update FROM version");
/*  26: 18 */             if (!localResultSet.next()) {
/*  27: 19 */               throw new RuntimeException("Invalid version table");
/*  28:    */             }
/*  29: 21 */             i = localResultSet.getInt("next_update");
/*  30: 22 */             if (!localResultSet.isLast()) {
/*  31: 23 */               throw new RuntimeException("Invalid version table");
/*  32:    */             }
/*  33: 25 */             localResultSet.close();
/*  34: 26 */             Logger.logMessage("Database update may take a while if needed, current db version " + (i - 1) + "...");
/*  35:    */           }
/*  36:    */           catch (SQLException localSQLException2)
/*  37:    */           {
/*  38: 28 */             Logger.logMessage("Initializing an empty database");
/*  39: 29 */             localStatement.executeUpdate("CREATE TABLE version (next_update INT NOT NULL)");
/*  40: 30 */             localStatement.executeUpdate("INSERT INTO version VALUES (1)");
/*  41:    */           }
/*  42: 33 */           update(i);
/*  43:    */         }
/*  44:    */         catch (Throwable localThrowable4)
/*  45:    */         {
/*  46: 14 */           localObject2 = localThrowable4;throw localThrowable4;
/*  47:    */         }
/*  48:    */         finally {}
/*  49:    */       }
/*  50:    */       catch (Throwable localThrowable2)
/*  51:    */       {
/*  52: 14 */         localObject1 = localThrowable2;throw localThrowable2;
/*  53:    */       }
/*  54:    */       finally
/*  55:    */       {
/*  56: 34 */         if (localConnection != null) {
/*  57: 34 */           if (localObject1 != null) {
/*  58:    */             try
/*  59:    */             {
/*  60: 34 */               localConnection.close();
/*  61:    */             }
/*  62:    */             catch (Throwable localThrowable6)
/*  63:    */             {
/*  64: 34 */               ((Throwable)localObject1).addSuppressed(localThrowable6);
/*  65:    */             }
/*  66:    */           } else {
/*  67: 34 */             localConnection.close();
/*  68:    */           }
/*  69:    */         }
/*  70:    */       }
/*  71:    */     }
/*  72:    */     catch (SQLException localSQLException1)
/*  73:    */     {
/*  74: 36 */       throw new RuntimeException(localSQLException1.toString(), localSQLException1);
/*  75:    */     }
/*  76:    */     finally
/*  77:    */     {
/*  78: 38 */       Db.endTransaction();
/*  79:    */     }
/*  80:    */   }
/*  81:    */   
/*  82:    */   private static void apply(String paramString)
/*  83:    */   {
/*  84:    */     try
/*  85:    */     {
/*  86: 44 */       Connection localConnection = Db.getConnection();Object localObject1 = null;
/*  87:    */       try
/*  88:    */       {
/*  89: 44 */         Statement localStatement = localConnection.createStatement();Object localObject2 = null;
/*  90:    */         try
/*  91:    */         {
/*  92:    */           try
/*  93:    */           {
/*  94: 46 */             if (paramString != null)
/*  95:    */             {
/*  96: 47 */               Logger.logDebugMessage("Will apply sql:\n" + paramString);
/*  97: 48 */               localStatement.executeUpdate(paramString);
/*  98:    */             }
/*  99: 50 */             localStatement.executeUpdate("UPDATE version SET next_update = next_update + 1");
/* 100: 51 */             Db.commitTransaction();
/* 101:    */           }
/* 102:    */           catch (Exception localException)
/* 103:    */           {
/* 104: 53 */             Db.rollbackTransaction();
/* 105: 54 */             throw localException;
/* 106:    */           }
/* 107:    */         }
/* 108:    */         catch (Throwable localThrowable4)
/* 109:    */         {
/* 110: 44 */           localObject2 = localThrowable4;throw localThrowable4;
/* 111:    */         }
/* 112:    */         finally {}
/* 113:    */       }
/* 114:    */       catch (Throwable localThrowable2)
/* 115:    */       {
/* 116: 44 */         localObject1 = localThrowable2;throw localThrowable2;
/* 117:    */       }
/* 118:    */       finally
/* 119:    */       {
/* 120: 56 */         if (localConnection != null) {
/* 121: 56 */           if (localObject1 != null) {
/* 122:    */             try
/* 123:    */             {
/* 124: 56 */               localConnection.close();
/* 125:    */             }
/* 126:    */             catch (Throwable localThrowable6)
/* 127:    */             {
/* 128: 56 */               ((Throwable)localObject1).addSuppressed(localThrowable6);
/* 129:    */             }
/* 130:    */           } else {
/* 131: 56 */             localConnection.close();
/* 132:    */           }
/* 133:    */         }
/* 134:    */       }
/* 135:    */     }
/* 136:    */     catch (SQLException localSQLException)
/* 137:    */     {
/* 138: 57 */       throw new RuntimeException("Database error executing " + paramString, localSQLException);
/* 139:    */     }
/* 140:    */   }
/* 141:    */   
/* 142:    */   private static void update(int paramInt)
/* 143:    */   {
/* 144: 62 */     switch (paramInt)
/* 145:    */     {
/* 146:    */     case 1: 
/* 147: 64 */       apply("CREATE TABLE IF NOT EXISTS block (db_id IDENTITY, id BIGINT NOT NULL, version INT NOT NULL, timestamp INT NOT NULL, previous_block_id BIGINT, FOREIGN KEY (previous_block_id) REFERENCES block (id) ON DELETE CASCADE, total_amount INT NOT NULL, total_fee INT NOT NULL, payload_length INT NOT NULL, generator_public_key BINARY(32) NOT NULL, previous_block_hash BINARY(32), cumulative_difficulty VARBINARY NOT NULL, base_target BIGINT NOT NULL, next_block_id BIGINT, FOREIGN KEY (next_block_id) REFERENCES block (id) ON DELETE SET NULL, index INT NOT NULL, height INT NOT NULL, generation_signature BINARY(64) NOT NULL, block_signature BINARY(64) NOT NULL, payload_hash BINARY(32) NOT NULL, generator_account_id BIGINT NOT NULL, nonce BIGINT NOT NULL)");
/* 148:    */     case 2: 
/* 149: 73 */       apply("CREATE UNIQUE INDEX IF NOT EXISTS block_id_idx ON block (id)");
/* 150:    */     case 3: 
/* 151: 75 */       apply("CREATE TABLE IF NOT EXISTS transaction (db_id IDENTITY, id BIGINT NOT NULL, deadline SMALLINT NOT NULL, sender_public_key BINARY(32) NOT NULL, recipient_id BIGINT NOT NULL, amount INT NOT NULL, fee INT NOT NULL, referenced_transaction_id BIGINT, index INT NOT NULL, height INT NOT NULL, block_id BIGINT NOT NULL, FOREIGN KEY (block_id) REFERENCES block (id) ON DELETE CASCADE, signature BINARY(64) NOT NULL, timestamp INT NOT NULL, type TINYINT NOT NULL, subtype TINYINT NOT NULL, sender_account_id BIGINT NOT NULL, attachment OTHER)");
/* 152:    */     case 4: 
/* 153: 82 */       apply("CREATE UNIQUE INDEX IF NOT EXISTS transaction_id_idx ON transaction (id)");
/* 154:    */     case 5: 
/* 155: 84 */       apply("CREATE UNIQUE INDEX IF NOT EXISTS block_height_idx ON block (height)");
/* 156:    */     case 6: 
/* 157: 86 */       apply("CREATE INDEX IF NOT EXISTS transaction_timestamp_idx ON transaction (timestamp)");
/* 158:    */     case 7: 
/* 159: 88 */       apply("CREATE INDEX IF NOT EXISTS block_generator_account_id_idx ON block (generator_account_id)");
/* 160:    */     case 8: 
/* 161: 90 */       apply("CREATE INDEX IF NOT EXISTS transaction_sender_account_id_idx ON transaction (sender_account_id)");
/* 162:    */     case 9: 
/* 163: 92 */       apply("CREATE INDEX IF NOT EXISTS transaction_recipient_id_idx ON transaction (recipient_id)");
/* 164:    */     case 10: 
/* 165: 94 */       apply("ALTER TABLE block ALTER COLUMN generator_account_id RENAME TO generator_id");
/* 166:    */     case 11: 
/* 167: 96 */       apply("ALTER TABLE transaction ALTER COLUMN sender_account_id RENAME TO sender_id");
/* 168:    */     case 12: 
/* 169: 98 */       apply("ALTER INDEX block_generator_account_id_idx RENAME TO block_generator_id_idx");
/* 170:    */     case 13: 
/* 171:100 */       apply("ALTER INDEX transaction_sender_account_id_idx RENAME TO transaction_sender_id_idx");
/* 172:    */     case 14: 
/* 173:102 */       apply("ALTER TABLE block DROP COLUMN IF EXISTS index");
/* 174:    */     case 15: 
/* 175:104 */       apply("ALTER TABLE transaction DROP COLUMN IF EXISTS index");
/* 176:    */     case 16: 
/* 177:106 */       apply("ALTER TABLE transaction ADD COLUMN IF NOT EXISTS block_timestamp INT");
/* 178:    */     case 17: 
/* 179:108 */       apply(null);
/* 180:    */     case 18: 
/* 181:110 */       apply("ALTER TABLE transaction ALTER COLUMN block_timestamp SET NOT NULL");
/* 182:    */     case 19: 
/* 183:112 */       apply("ALTER TABLE transaction ADD COLUMN IF NOT EXISTS hash BINARY(32)");
/* 184:    */     case 20: 
/* 185:114 */       apply(null);
/* 186:    */     case 21: 
/* 187:116 */       apply(null);
/* 188:    */     case 22: 
/* 189:118 */       apply("CREATE INDEX IF NOT EXISTS transaction_hash_idx ON transaction (hash)");
/* 190:    */     case 23: 
/* 191:120 */       apply(null);
/* 192:    */     case 24: 
/* 193:122 */       apply("ALTER TABLE block ALTER COLUMN total_amount BIGINT");
/* 194:    */     case 25: 
/* 195:124 */       apply("ALTER TABLE block ALTER COLUMN total_fee BIGINT");
/* 196:    */     case 26: 
/* 197:126 */       apply("ALTER TABLE transaction ALTER COLUMN amount BIGINT");
/* 198:    */     case 27: 
/* 199:128 */       apply("ALTER TABLE transaction ALTER COLUMN fee BIGINT");
/* 200:    */     case 28: 
/* 201:130 */       apply(null);
/* 202:    */     case 29: 
/* 203:132 */       apply(null);
/* 204:    */     case 30: 
/* 205:134 */       apply(null);
/* 206:    */     case 31: 
/* 207:136 */       apply(null);
/* 208:    */     case 32: 
/* 209:138 */       apply(null);
/* 210:    */     case 33: 
/* 211:140 */       apply(null);
/* 212:    */     case 34: 
/* 213:142 */       apply(null);
/* 214:    */     case 35: 
/* 215:144 */       apply(null);
/* 216:    */     case 36: 
/* 217:146 */       apply("CREATE TABLE IF NOT EXISTS peer (address VARCHAR PRIMARY KEY)");
/* 218:    */     case 37: 
/* 219:182 */       apply(null);
/* 220:    */     case 38: 
/* 221:184 */       apply("ALTER TABLE transaction ADD COLUMN IF NOT EXISTS full_hash BINARY(32)");
/* 222:    */     case 39: 
/* 223:186 */       apply("ALTER TABLE transaction ADD COLUMN IF NOT EXISTS referenced_transaction_full_hash BINARY(32)");
/* 224:    */     case 40: 
/* 225:188 */       apply(null);
/* 226:    */     case 41: 
/* 227:190 */       apply("ALTER TABLE transaction ALTER COLUMN full_hash SET NOT NULL");
/* 228:    */     case 42: 
/* 229:192 */       apply("CREATE UNIQUE INDEX IF NOT EXISTS transaction_full_hash_idx ON transaction (full_hash)");
/* 230:    */     case 43: 
/* 231:194 */       apply(null);
/* 232:    */     case 44: 
/* 233:196 */       apply(null);
/* 234:    */     case 45: 
/* 235:198 */       apply(null);
/* 236:    */     case 46: 
/* 237:200 */       apply("ALTER TABLE transaction ADD COLUMN IF NOT EXISTS attachment_bytes VARBINARY");
/* 238:    */     case 47: 
/* 239:202 */       apply(null);
/* 240:    */     case 48: 
/* 241:204 */       apply("ALTER TABLE transaction DROP COLUMN attachment");
/* 242:    */     case 49: 
/* 243:206 */       apply(null);
/* 244:    */     case 50: 
/* 245:208 */       apply("ALTER TABLE transaction DROP COLUMN referenced_transaction_id");
/* 246:    */     case 51: 
/* 247:210 */       apply("ALTER TABLE transaction DROP COLUMN hash");
/* 248:    */     case 52: 
/* 249:212 */       apply(null);
/* 250:    */     case 53: 
/* 251:214 */       apply("DROP INDEX transaction_recipient_id_idx");
/* 252:    */     case 54: 
/* 253:216 */       apply("ALTER TABLE transaction ALTER COLUMN recipient_id SET NULL");
/* 254:    */     case 55: 
/* 255:218 */       BlockDb.deleteAll();
/* 256:219 */       apply(null);
/* 257:    */     case 56: 
/* 258:221 */       apply("CREATE INDEX IF NOT EXISTS transaction_recipient_id_idx ON transaction (recipient_id)");
/* 259:    */     case 57: 
/* 260:223 */       apply(null);
/* 261:    */     case 58: 
/* 262:225 */       apply(null);
/* 263:    */     case 59: 
/* 264:227 */       apply("ALTER TABLE transaction ADD COLUMN IF NOT EXISTS version TINYINT");
/* 265:    */     case 60: 
/* 266:229 */       apply("UPDATE transaction SET version = 0");
/* 267:    */     case 61: 
/* 268:231 */       apply("ALTER TABLE transaction ALTER COLUMN version SET NOT NULL");
/* 269:    */     case 62: 
/* 270:233 */       apply("ALTER TABLE transaction ADD COLUMN IF NOT EXISTS has_message BOOLEAN NOT NULL DEFAULT FALSE");
/* 271:    */     case 63: 
/* 272:235 */       apply("ALTER TABLE transaction ADD COLUMN IF NOT EXISTS has_encrypted_message BOOLEAN NOT NULL DEFAULT FALSE");
/* 273:    */     case 64: 
/* 274:237 */       apply("UPDATE transaction SET has_message = TRUE WHERE type = 1 AND subtype = 0");
/* 275:    */     case 65: 
/* 276:239 */       apply("ALTER TABLE transaction ADD COLUMN IF NOT EXISTS has_public_key_announcement BOOLEAN NOT NULL DEFAULT FALSE");
/* 277:    */     case 66: 
/* 278:241 */       apply("ALTER TABLE transaction ADD COLUMN IF NOT EXISTS ec_block_height INT DEFAULT NULL");
/* 279:    */     case 67: 
/* 280:243 */       apply("ALTER TABLE transaction ADD COLUMN IF NOT EXISTS ec_block_id BIGINT DEFAULT NULL");
/* 281:    */     case 68: 
/* 282:245 */       apply("ALTER TABLE transaction ADD COLUMN IF NOT EXISTS has_encrypttoself_message BOOLEAN NOT NULL DEFAULT FALSE");
/* 283:    */     case 69: 
/* 284:247 */       apply("CREATE INDEX IF NOT EXISTS transaction_block_timestamp_idx ON transaction (block_timestamp DESC)");
/* 285:    */     case 70: 
/* 286:249 */       apply("DROP INDEX transaction_timestamp_idx");
/* 287:    */     case 71: 
/* 288:251 */       apply("CREATE TABLE IF NOT EXISTS alias (db_id IDENTITY, id BIGINT NOT NULL, account_id BIGINT NOT NULL, alias_name VARCHAR NOT NULL, alias_name_lower VARCHAR AS LOWER (alias_name) NOT NULL, alias_uri VARCHAR NOT NULL, timestamp INT NOT NULL, height INT NOT NULL, latest BOOLEAN NOT NULL DEFAULT TRUE)");
/* 289:    */     case 72: 
/* 290:257 */       apply("CREATE UNIQUE INDEX IF NOT EXISTS alias_id_height_idx ON alias (id, height DESC)");
/* 291:    */     case 73: 
/* 292:259 */       apply("CREATE INDEX IF NOT EXISTS alias_account_id_idx ON alias (account_id, height DESC)");
/* 293:    */     case 74: 
/* 294:261 */       apply("CREATE INDEX IF NOT EXISTS alias_name_lower_idx ON alias (alias_name_lower)");
/* 295:    */     case 75: 
/* 296:263 */       apply("CREATE TABLE IF NOT EXISTS alias_offer (db_id IDENTITY, id BIGINT NOT NULL, price BIGINT NOT NULL, buyer_id BIGINT, height INT NOT NULL, latest BOOLEAN DEFAULT TRUE NOT NULL)");
/* 297:    */     case 76: 
/* 298:267 */       apply("CREATE UNIQUE INDEX IF NOT EXISTS alias_offer_id_height_idx ON alias_offer (id, height DESC)");
/* 299:    */     case 77: 
/* 300:269 */       apply("CREATE TABLE IF NOT EXISTS asset (db_id IDENTITY, id BIGINT NOT NULL, account_id BIGINT NOT NULL, name VARCHAR NOT NULL, description VARCHAR, quantity BIGINT NOT NULL, decimals TINYINT NOT NULL, height INT NOT NULL)");
/* 301:    */     case 78: 
/* 302:273 */       apply("CREATE UNIQUE INDEX IF NOT EXISTS asset_id_idx ON asset (id)");
/* 303:    */     case 79: 
/* 304:275 */       apply("CREATE INDEX IF NOT EXISTS asset_account_id_idx ON asset (account_id)");
/* 305:    */     case 80: 
/* 306:277 */       apply("CREATE TABLE IF NOT EXISTS trade (db_id IDENTITY, asset_id BIGINT NOT NULL, block_id BIGINT NOT NULL, ask_order_id BIGINT NOT NULL, bid_order_id BIGINT NOT NULL, ask_order_height INT NOT NULL, bid_order_height INT NOT NULL, seller_id BIGINT NOT NULL, buyer_id BIGINT NOT NULL, quantity BIGINT NOT NULL, price BIGINT NOT NULL, timestamp INT NOT NULL, height INT NOT NULL)");
/* 307:    */     case 81: 
/* 308:282 */       apply("CREATE UNIQUE INDEX IF NOT EXISTS trade_ask_bid_idx ON trade (ask_order_id, bid_order_id)");
/* 309:    */     case 82: 
/* 310:284 */       apply("CREATE INDEX IF NOT EXISTS trade_asset_id_idx ON trade (asset_id, height DESC)");
/* 311:    */     case 83: 
/* 312:286 */       apply("CREATE INDEX IF NOT EXISTS trade_seller_id_idx ON trade (seller_id, height DESC)");
/* 313:    */     case 84: 
/* 314:288 */       apply("CREATE INDEX IF NOT EXISTS trade_buyer_id_idx ON trade (buyer_id, height DESC)");
/* 315:    */     case 85: 
/* 316:290 */       apply("CREATE TABLE IF NOT EXISTS ask_order (db_id IDENTITY, id BIGINT NOT NULL, account_id BIGINT NOT NULL, asset_id BIGINT NOT NULL, price BIGINT NOT NULL, quantity BIGINT NOT NULL, creation_height INT NOT NULL, height INT NOT NULL, latest BOOLEAN NOT NULL DEFAULT TRUE)");
/* 317:    */     case 86: 
/* 318:295 */       apply("CREATE UNIQUE INDEX IF NOT EXISTS ask_order_id_height_idx ON ask_order (id, height DESC)");
/* 319:    */     case 87: 
/* 320:297 */       apply("CREATE INDEX IF NOT EXISTS ask_order_account_id_idx ON ask_order (account_id, height DESC)");
/* 321:    */     case 88: 
/* 322:299 */       apply("CREATE INDEX IF NOT EXISTS ask_order_asset_id_price_idx ON ask_order (asset_id, price)");
/* 323:    */     case 89: 
/* 324:301 */       apply("CREATE TABLE IF NOT EXISTS bid_order (db_id IDENTITY, id BIGINT NOT NULL, account_id BIGINT NOT NULL, asset_id BIGINT NOT NULL, price BIGINT NOT NULL, quantity BIGINT NOT NULL, creation_height INT NOT NULL, height INT NOT NULL, latest BOOLEAN NOT NULL DEFAULT TRUE)");
/* 325:    */     case 90: 
/* 326:306 */       apply("CREATE UNIQUE INDEX IF NOT EXISTS bid_order_id_height_idx ON bid_order (id, height DESC)");
/* 327:    */     case 91: 
/* 328:308 */       apply("CREATE INDEX IF NOT EXISTS bid_order_account_id_idx ON bid_order (account_id, height DESC)");
/* 329:    */     case 92: 
/* 330:310 */       apply("CREATE INDEX IF NOT EXISTS bid_order_asset_id_price_idx ON bid_order (asset_id, price DESC)");
/* 331:    */     case 93: 
/* 332:312 */       apply("CREATE TABLE IF NOT EXISTS goods (db_id IDENTITY, id BIGINT NOT NULL, seller_id BIGINT NOT NULL, name VARCHAR NOT NULL, description VARCHAR, tags VARCHAR, timestamp INT NOT NULL, quantity INT NOT NULL, price BIGINT NOT NULL, delisted BOOLEAN NOT NULL, height INT NOT NULL, latest BOOLEAN NOT NULL DEFAULT TRUE)");
/* 333:    */     case 94: 
/* 334:317 */       apply("CREATE UNIQUE INDEX IF NOT EXISTS goods_id_height_idx ON goods (id, height DESC)");
/* 335:    */     case 95: 
/* 336:319 */       apply("CREATE INDEX IF NOT EXISTS goods_seller_id_name_idx ON goods (seller_id, name)");
/* 337:    */     case 96: 
/* 338:321 */       apply("CREATE INDEX IF NOT EXISTS goods_timestamp_idx ON goods (timestamp DESC, height DESC)");
/* 339:    */     case 97: 
/* 340:323 */       apply("CREATE TABLE IF NOT EXISTS purchase (db_id IDENTITY, id BIGINT NOT NULL, buyer_id BIGINT NOT NULL, goods_id BIGINT NOT NULL, seller_id BIGINT NOT NULL, quantity INT NOT NULL, price BIGINT NOT NULL, deadline INT NOT NULL, note VARBINARY, nonce BINARY(32), timestamp INT NOT NULL, pending BOOLEAN NOT NULL, goods VARBINARY, goods_nonce BINARY(32), refund_note VARBINARY, refund_nonce BINARY(32), has_feedback_notes BOOLEAN NOT NULL DEFAULT FALSE, has_public_feedbacks BOOLEAN NOT NULL DEFAULT FALSE, discount BIGINT NOT NULL, refund BIGINT NOT NULL, height INT NOT NULL, latest BOOLEAN NOT NULL DEFAULT TRUE)");
/* 341:    */     case 98: 
/* 342:332 */       apply("CREATE UNIQUE INDEX IF NOT EXISTS purchase_id_height_idx ON purchase (id, height DESC)");
/* 343:    */     case 99: 
/* 344:334 */       apply("CREATE INDEX IF NOT EXISTS purchase_buyer_id_height_idx ON purchase (buyer_id, height DESC)");
/* 345:    */     case 100: 
/* 346:336 */       apply("CREATE INDEX IF NOT EXISTS purchase_seller_id_height_idx ON purchase (seller_id, height DESC)");
/* 347:    */     case 101: 
/* 348:338 */       apply("CREATE INDEX IF NOT EXISTS purchase_deadline_idx ON purchase (deadline DESC, height DESC)");
/* 349:    */     case 102: 
/* 350:340 */       apply("CREATE TABLE IF NOT EXISTS account (db_id IDENTITY, id BIGINT NOT NULL, creation_height INT NOT NULL, public_key BINARY(32), key_height INT, balance BIGINT NOT NULL, unconfirmed_balance BIGINT NOT NULL, forged_balance BIGINT NOT NULL, name VARCHAR, description VARCHAR, current_leasing_height_from INT, current_leasing_height_to INT, current_lessee_id BIGINT NULL, next_leasing_height_from INT, next_leasing_height_to INT, next_lessee_id BIGINT NULL, height INT NOT NULL, latest BOOLEAN NOT NULL DEFAULT TRUE)");
/* 351:    */     case 103: 
/* 352:347 */       apply("CREATE UNIQUE INDEX IF NOT EXISTS account_id_height_idx ON account (id, height DESC)");
/* 353:    */     case 104: 
/* 354:349 */       apply("CREATE INDEX IF NOT EXISTS account_current_lessee_id_leasing_height_idx ON account (current_lessee_id, current_leasing_height_to DESC)");
/* 355:    */     case 105: 
/* 356:352 */       apply("CREATE TABLE IF NOT EXISTS account_asset (db_id IDENTITY, account_id BIGINT NOT NULL, asset_id BIGINT NOT NULL, quantity BIGINT NOT NULL, unconfirmed_quantity BIGINT NOT NULL, height INT NOT NULL, latest BOOLEAN NOT NULL DEFAULT TRUE)");
/* 357:    */     case 106: 
/* 358:356 */       apply("CREATE UNIQUE INDEX IF NOT EXISTS account_asset_id_height_idx ON account_asset (account_id, asset_id, height DESC)");
/* 359:    */     case 107: 
/* 360:358 */       apply("CREATE TABLE IF NOT EXISTS account_guaranteed_balance (db_id IDENTITY, account_id BIGINT NOT NULL, additions BIGINT NOT NULL, height INT NOT NULL)");
/* 361:    */     case 108: 
/* 362:361 */       apply("CREATE UNIQUE INDEX IF NOT EXISTS account_guaranteed_balance_id_height_idx ON account_guaranteed_balance (account_id, height DESC)");
/* 363:    */     case 109: 
/* 364:364 */       apply("CREATE TABLE IF NOT EXISTS purchase_feedback (db_id IDENTITY, id BIGINT NOT NULL, feedback_data VARBINARY NOT NULL, feedback_nonce BINARY(32) NOT NULL, height INT NOT NULL, latest BOOLEAN NOT NULL DEFAULT TRUE)");
/* 365:    */     case 110: 
/* 366:367 */       apply("CREATE INDEX IF NOT EXISTS purchase_feedback_id_height_idx ON purchase_feedback (id, height DESC)");
/* 367:    */     case 111: 
/* 368:369 */       apply("CREATE TABLE IF NOT EXISTS purchase_public_feedback (db_id IDENTITY, id BIGINT NOT NULL, public_feedback VARCHAR NOT NULL, height INT NOT NULL, latest BOOLEAN NOT NULL DEFAULT TRUE)");
/* 369:    */     case 112: 
/* 370:372 */       apply("CREATE INDEX IF NOT EXISTS purchase_public_feedback_id_height_idx ON purchase_public_feedback (id, height DESC)");
/* 371:    */     case 113: 
/* 372:374 */       apply("CREATE TABLE IF NOT EXISTS unconfirmed_transaction (db_id IDENTITY, id BIGINT NOT NULL, expiration INT NOT NULL, transaction_height INT NOT NULL, fee_per_byte BIGINT NOT NULL, timestamp INT NOT NULL, transaction_bytes VARBINARY NOT NULL, height INT NOT NULL)");
/* 373:    */     case 114: 
/* 374:378 */       apply("CREATE UNIQUE INDEX IF NOT EXISTS unconfirmed_transaction_id_idx ON unconfirmed_transaction (id)");
/* 375:    */     case 115: 
/* 376:380 */       apply("CREATE INDEX IF NOT EXISTS unconfirmed_transaction_height_fee_timestamp_idx ON unconfirmed_transaction (transaction_height ASC, fee_per_byte DESC, timestamp ASC)");
/* 377:    */     case 116: 
/* 378:383 */       apply("CREATE TABLE IF NOT EXISTS asset_transfer (db_id IDENTITY, id BIGINT NOT NULL, asset_id BIGINT NOT NULL, sender_id BIGINT NOT NULL, recipient_id BIGINT NOT NULL, quantity BIGINT NOT NULL, timestamp INT NOT NULL, height INT NOT NULL)");
/* 379:    */     case 117: 
/* 380:387 */       apply("CREATE UNIQUE INDEX IF NOT EXISTS asset_transfer_id_idx ON asset_transfer (id)");
/* 381:    */     case 118: 
/* 382:389 */       apply("CREATE INDEX IF NOT EXISTS asset_transfer_asset_id_idx ON asset_transfer (asset_id, height DESC)");
/* 383:    */     case 119: 
/* 384:391 */       apply("CREATE INDEX IF NOT EXISTS asset_transfer_sender_id_idx ON asset_transfer (sender_id, height DESC)");
/* 385:    */     case 120: 
/* 386:393 */       apply("CREATE INDEX IF NOT EXISTS asset_transfer_recipient_id_idx ON asset_transfer (recipient_id, height DESC)");
/* 387:    */     case 121: 
/* 388:395 */       BlockchainProcessorImpl.getInstance().forceScanAtStart();
/* 389:396 */       apply(null);
/* 390:    */     case 122: 
/* 391:398 */       apply("CREATE INDEX IF NOT EXISTS account_asset_quantity_idx ON account_asset (quantity DESC)");
/* 392:    */     case 123: 
/* 393:400 */       apply("CREATE INDEX IF NOT EXISTS purchase_timestamp_idx ON purchase (timestamp DESC, id)");
/* 394:    */     case 124: 
/* 395:402 */       apply("CREATE INDEX IF NOT EXISTS ask_order_creation_idx ON ask_order (creation_height DESC)");
/* 396:    */     case 125: 
/* 397:404 */       apply("CREATE INDEX IF NOT EXISTS bid_order_creation_idx ON bid_order (creation_height DESC)");
/* 398:    */     case 126: 
/* 399:406 */       apply("CREATE TABLE IF NOT EXISTS reward_recip_assign (db_id IDENTITY, account_id BIGINT NOT NULL, prev_recip_id BIGINT NOT NULL, recip_id BIGINT NOT NULL, from_height INT NOT NULL, height INT NOT NULL, latest BOOLEAN NOT NULL DEFAULT TRUE)");
/* 400:    */     case 127: 
/* 401:410 */       apply("CREATE UNIQUE INDEX IF NOT EXISTS reward_recip_assign_account_id_height_idx ON reward_recip_assign (account_id, height DESC)");
/* 402:    */     case 128: 
/* 403:412 */       apply("CREATE INDEX IF NOT EXISTS reward_recip_assign_recip_id_height_idx ON reward_recip_assign (recip_id, height DESC)");
/* 404:    */     case 129: 
/* 405:414 */       apply("CREATE TABLE IF NOT EXISTS escrow (db_id IDENTITY, id BIGINT NOT NULL, sender_id BIGINT NOT NULL, recipient_id BIGINT NOT NULL, amount BIGINT NOT NULL, required_signers INT, deadline INT NOT NULL, deadline_action INT NOT NULL, height INT NOT NULL, latest BOOLEAN NOT NULL DEFAULT TRUE)");
/* 406:    */     case 130: 
/* 407:418 */       apply("CREATE UNIQUE INDEX IF NOT EXISTS escrow_id_height_idx ON escrow (id, height DESC)");
/* 408:    */     case 131: 
/* 409:420 */       apply("CREATE INDEX IF NOT EXISTS escrow_sender_id_height_idx ON escrow (sender_id, height DESC)");
/* 410:    */     case 132: 
/* 411:422 */       apply("CREATE INDEX IF NOT EXISTS escrow_recipient_id_height_idx ON escrow (recipient_id, height DESC)");
/* 412:    */     case 133: 
/* 413:424 */       apply("CREATE INDEX IF NOT EXISTS escrow_deadline_height_idx ON escrow (deadline, height DESC)");
/* 414:    */     case 134: 
/* 415:426 */       apply("CREATE TABLE IF NOT EXISTS escrow_decision (db_id IDENTITY, escrow_id BIGINT NOT NULL, account_id BIGINT NOT NULL, decision INT NOT NULL, height INT NOT NULL, latest BOOLEAN NOT NULL DEFAULT TRUE)");
/* 416:    */     case 135: 
/* 417:429 */       apply("CREATE UNIQUE INDEX IF NOT EXISTS escrow_decision_escrow_id_account_id_height_idx ON escrow_decision (escrow_id, account_id, height DESC)");
/* 418:    */     case 136: 
/* 419:431 */       apply("CREATE INDEX IF NOT EXISTS escrow_decision_escrow_id_height_idx ON escrow_decision (escrow_id, height DESC)");
/* 420:    */     case 137: 
/* 421:433 */       apply("CREATE INDEX IF NOT EXISTS escrow_decision_account_id_height_idx ON escrow_decision (account_id, height DESC)");
/* 422:    */     case 138: 
/* 423:435 */       apply("ALTER TABLE transaction ALTER COLUMN signature SET NULL");
/* 424:    */     case 139: 
/* 425:437 */       apply("CREATE TABLE IF NOT EXISTS subscription (db_id IDENTITY, id BIGINT NOT NULL, sender_id BIGINT NOT NULL, recipient_id BIGINT NOT NULL, amount BIGINT NOT NULL, frequency INT NOT NULL, time_next INT NOT NULL, height INT NOT NULL, latest BOOLEAN NOT NULL DEFAULT TRUE)");
/* 426:    */     case 140: 
/* 427:440 */       apply("CREATE UNIQUE INDEX IF NOT EXISTS subscription_id_height_idx ON subscription (id, height DESC)");
/* 428:    */     case 141: 
/* 429:442 */       apply("CREATE INDEX IF NOT EXISTS subscription_sender_id_height_idx ON subscription (sender_id, height DESC)");
/* 430:    */     case 142: 
/* 431:444 */       apply("CREATE INDEX IF NOT EXISTS subscription_recipient_id_height_idx ON subscription (recipient_id, height DESC)");
/* 432:    */     case 143: 
/* 433:446 */       apply("CREATE UNIQUE INDEX IF NOT EXISTS block_timestamp_idx ON block (timestamp DESC)");
/* 434:    */     case 144: 
/* 435:448 */       apply("CREATE TABLE IF NOT EXISTS at (db_id IDENTITY, id BIGINT NOT NULL, creator_id BIGINT NOT NULL, name VARCHAR, description VARCHAR, version SMALLINT NOT NULL, csize INT NOT NULL, dsize INT NOT NULL, c_user_stack_bytes INT NOT NULL, c_call_stack_bytes INT NOT NULL, creation_height INT NOT NULL, ap_code BINARY NOT NULL, height INT NOT NULL, latest BOOLEAN NOT NULL DEFAULT TRUE)");
/* 436:    */     case 145: 
/* 437:453 */       apply("CREATE UNIQUE INDEX IF NOT EXISTS at_id_height_idx ON at (id, height DESC)");
/* 438:    */     case 146: 
/* 439:455 */       apply("CREATE INDEX IF NOT EXISTS at_creator_id_height_idx ON at (creator_id, height DESC)");
/* 440:    */     case 147: 
/* 441:457 */       apply("CREATE TABLE IF NOT EXISTS at_state (db_id IDENTITY, at_id BIGINT NOT NULL, state BINARY NOT NULL, prev_height INT NOT NULL, next_height INT NOT NULL, sleep_between INT NOT NULL, prev_balance BIGINT NOT NULL, freeze_when_same_balance BOOLEAN NOT NULL, min_activate_amount BIGINT NOT NULL, height INT NOT NULL, latest BOOLEAN NOT NULL DEFAULT TRUE)");
/* 442:    */     case 148: 
/* 443:461 */       apply("CREATE UNIQUE INDEX IF NOT EXISTS at_state_at_id_height_idx ON at_state (at_id, height DESC)");
/* 444:    */     case 149: 
/* 445:463 */       apply("CREATE INDEX IF NOT EXISTS at_state_id_next_height_height_idx ON at_state (at_id, next_height, height DESC)");
/* 446:    */     case 150: 
/* 447:465 */       apply("ALTER TABLE block ADD COLUMN IF NOT EXISTS ats BINARY");
/* 448:    */     case 151: 
/* 449:467 */       apply("CREATE INDEX IF NOT EXISTS account_id_balance_height_idx ON account (id, balance, height DESC)");
/* 450:    */     case 152: 
/* 451:469 */       apply("CREATE INDEX IF NOT EXISTS transaction_recipient_id_amount_height_idx ON transaction (recipient_id, amount, height)");
/* 452:    */     case 153: 
/* 453:471 */       BlockchainProcessorImpl.getInstance().forceScanAtStart();
/* 454:472 */       apply(null);
/* 455:    */     case 154: 
/* 456:474 */       return;
/* 457:    */     }
/* 458:476 */     throw new RuntimeException("Database inconsistent with code, probably trying to run older code on newer database");
/* 459:    */   }
/* 460:    */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.DbVersion
 * JD-Core Version:    0.7.1
 */