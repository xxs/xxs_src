/*    */ package com.enation.app.base.core.model;
/*    */ 
/*    */ import com.enation.eop.sdk.context.EopSetting;
/*    */ import java.util.List;
/*    */ 
/*    */ public class VersionState
/*    */ {
/*    */   private String productid;
/*    */   private boolean haveNewVersion;
/*    */   private String newVersion;
/*    */   private List<UpdateLog> updateLogList;
/*    */ 
/*    */   @Deprecated
/*    */   private String updateLog;
/*    */ 
/*    */   public VersionState()
/*    */   {
/* 16 */     this.haveNewVersion = false;
/* 17 */     this.productid = EopSetting.PRODUCTID;
/*    */   }
/*    */ 
/*    */   public boolean getHaveNewVersion()
/*    */   {
/* 38 */     return this.haveNewVersion;
/*    */   }
/*    */ 
/*    */   public void setHaveNewVersion(boolean haveNewVersion) {
/* 42 */     this.haveNewVersion = haveNewVersion;
/*    */   }
/*    */ 
/*    */   public String getNewVersion() {
/* 46 */     return this.newVersion;
/*    */   }
/*    */ 
/*    */   public void setNewVersion(String newVersion) {
/* 50 */     this.newVersion = newVersion;
/*    */   }
/*    */ 
/*    */   public List<UpdateLog> getUpdateLogList() {
/* 54 */     return this.updateLogList;
/*    */   }
/*    */ 
/*    */   public void setUpdateLogList(List<UpdateLog> updateLogList) {
/* 58 */     this.updateLogList = updateLogList;
/*    */   }
/*    */ 
/*    */   public String getProductid() {
/* 62 */     return this.productid;
/*    */   }
/*    */ 
/*    */   public void setProductid(String productid) {
/* 66 */     this.productid = productid;
/*    */   }
/*    */ 
/*    */   @Deprecated
/*    */   public String getUpdateLog()
/*    */   {
/* 81 */     return this.updateLog;
/*    */   }
/*    */   @Deprecated
/*    */   public void setUpdateLog(String updateLog) {
/* 86 */     this.updateLog = updateLog;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.app.base.core.model.VersionState
 * JD-Core Version:    0.6.0
 */