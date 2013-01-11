/*    */ package com.enation.app.base.core.action;
/*    */ 
/*    */ import com.enation.app.base.core.model.VersionState;
/*    */ import com.enation.app.base.core.service.IUpdateManager;
/*    */ import com.enation.framework.action.WWAction;
/*    */ import net.sf.json.JSONObject;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ public class UpdateAction extends WWAction
/*    */ {
/*    */   private IUpdateManager updateManager;
/*    */ 
/*    */   public String checkNewVersion()
/*    */   {
/* 27 */     VersionState versionState = null;
/*    */     try {
/* 29 */       versionState = this.updateManager.checkNewVersion();
/*    */     } catch (Exception e) {
/* 31 */       versionState = new VersionState();
/*    */     }
/* 33 */     this.json = JSONObject.fromObject(versionState).toString();
/* 34 */     return "json_message";
/*    */   }
/*    */ 
/*    */   public String update()
/*    */   {
/*    */     try {
/* 40 */       this.updateManager.update();
/* 41 */       this.json = "{result:1}";
/*    */     } catch (RuntimeException e) {
/* 43 */       this.logger.error(e);
/* 44 */       e.printStackTrace();
/* 45 */       this.json = ("{result:0,message:'" + e.getMessage() + "'}");
/*    */     }
/* 47 */     return "json_message";
/*    */   }
/*    */ 
/*    */   public IUpdateManager getUpdateManager()
/*    */   {
/* 53 */     return this.updateManager;
/*    */   }
/*    */   public void setUpdateManager(IUpdateManager updateManager) {
/* 56 */     this.updateManager = updateManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.app.base.core.action.UpdateAction
 * JD-Core Version:    0.6.0
 */