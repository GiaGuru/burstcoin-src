/* 1:  */ package nxt.peer;
/* 2:  */ 
/* 3:  */ import org.json.simple.JSONObject;
/* 4:  */ import org.json.simple.JSONStreamAware;
/* 5:  */ 
/* 6:  */ public abstract interface Peer
/* 7:  */   extends Comparable<Peer>
/* 8:  */ {
/* 9:  */   public abstract String getPeerAddress();
/* ::  */   
/* ;:  */   public abstract String getAnnouncedAddress();
/* <:  */   
/* =:  */   public abstract State getState();
/* >:  */   
/* ?:  */   public abstract String getVersion();
/* @:  */   
/* A:  */   public abstract String getApplication();
/* B:  */   
/* C:  */   public abstract String getPlatform();
/* D:  */   
/* E:  */   public abstract String getSoftware();
/* F:  */   
/* G:  */   public abstract Hallmark getHallmark();
/* H:  */   
/* I:  */   public abstract int getWeight();
/* J:  */   
/* K:  */   public abstract boolean shareAddress();
/* L:  */   
/* M:  */   public abstract boolean isWellKnown();
/* N:  */   
/* O:  */   public abstract boolean isBlacklisted();
/* P:  */   
/* Q:  */   public abstract void blacklist(Exception paramException);
/* R:  */   
/* S:  */   public abstract void blacklist();
/* T:  */   
/* U:  */   public abstract void unBlacklist();
/* V:  */   
/* W:  */   public abstract void deactivate();
/* X:  */   
/* Y:  */   public abstract void remove();
/* Z:  */   
/* [:  */   public abstract long getDownloadedVolume();
/* \:  */   
/* ]:  */   public abstract long getUploadedVolume();
/* ^:  */   
/* _:  */   public abstract int getLastUpdated();
/* `:  */   
/* a:  */   public abstract JSONObject send(JSONStreamAware paramJSONStreamAware);
/* b:  */   
/* c:  */   public static enum State
/* d:  */   {
/* e:9 */     NON_CONNECTED,  CONNECTED,  DISCONNECTED;
/* f:  */     
/* g:  */     private State() {}
/* h:  */   }
/* i:  */ }


/* Location:           E:\java decompi;er\burst.jar
 * Qualified Name:     nxt.peer.Peer
 * JD-Core Version:    0.7.1
 */