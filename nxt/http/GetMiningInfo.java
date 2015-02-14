/*  1:   */ package nxt.http;
/*  2:   */ 
/*  3:   */ import fr.cryptohash.Shabal256;
/*  4:   */ import java.nio.ByteBuffer;
/*  5:   */ import javax.servlet.http.HttpServletRequest;
/*  6:   */ import nxt.Block;
/*  7:   */ import nxt.Blockchain;
/*  8:   */ import nxt.Nxt;
/*  9:   */ import nxt.util.Convert;
/* 10:   */ import org.json.simple.JSONObject;
/* 11:   */ import org.json.simple.JSONStreamAware;
/* 12:   */ 
/* 13:   */ public final class GetMiningInfo
/* 14:   */   extends APIServlet.APIRequestHandler
/* 15:   */ {
/* 16:16 */   static final GetMiningInfo instance = new GetMiningInfo();
/* 17:   */   
/* 18:   */   private GetMiningInfo()
/* 19:   */   {
/* 20:19 */     super(new APITag[] { APITag.MINING, APITag.INFO }, new String[0]);
/* 21:   */   }
/* 22:   */   
/* 23:   */   JSONStreamAware processRequest(HttpServletRequest paramHttpServletRequest)
/* 24:   */   {
/* 25:24 */     JSONObject localJSONObject = new JSONObject();
/* 26:   */     
/* 27:26 */     localJSONObject.put("height", Long.toString(Nxt.getBlockchain().getHeight() + 1));
/* 28:   */     
/* 29:28 */     Block localBlock = Nxt.getBlockchain().getLastBlock();
/* 30:29 */     byte[] arrayOfByte1 = localBlock.getGenerationSignature();
/* 31:30 */     Long localLong = Long.valueOf(localBlock.getGeneratorId());
/* 32:   */     
/* 33:32 */     ByteBuffer localByteBuffer = ByteBuffer.allocate(40);
/* 34:33 */     localByteBuffer.put(arrayOfByte1);
/* 35:34 */     localByteBuffer.putLong(localLong.longValue());
/* 36:   */     
/* 37:36 */     Shabal256 localShabal256 = new Shabal256();
/* 38:37 */     localShabal256.update(localByteBuffer.array());
/* 39:38 */     byte[] arrayOfByte2 = localShabal256.digest();
/* 40:   */     
/* 41:40 */     localJSONObject.put("generationSignature", Convert.toHexString(arrayOfByte2));
/* 42:41 */     localJSONObject.put("baseTarget", Long.toString(localBlock.getBaseTarget()));
/* 43:   */     
/* 44:43 */     return localJSONObject;
/* 45:   */   }
/* 46:   */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.http.GetMiningInfo
 * JD-Core Version:    0.7.1
 */