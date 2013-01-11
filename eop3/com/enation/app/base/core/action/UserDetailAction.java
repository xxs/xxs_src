/*    */ package com.enation.app.base.core.action;
/*    */ 
/*    */ import com.enation.eop.resource.IUserDetailManager;
/*    */ import com.enation.eop.resource.model.EopSite;
/*    */ import com.enation.eop.resource.model.EopUserDetail;
/*    */ import com.enation.eop.sdk.context.EopContext;
/*    */ import com.enation.framework.action.WWAction;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class UserDetailAction extends WWAction
/*    */ {
/*    */   private EopUserDetail eopUserDetail;
/*    */   private IUserDetailManager userDetailManager;
/*    */   private Integer userid;
/*    */ 
/*    */   public String execute()
/*    */     throws Exception
/*    */   {
/* 19 */     this.userid = EopContext.getContext().getCurrentSite().getUserid();
/* 20 */     this.eopUserDetail = this.userDetailManager.get(this.userid);
/* 21 */     return "input";
/*    */   }
/*    */ 
/*    */   public String save() throws Exception {
/*    */     try {
/* 26 */       this.userDetailManager.edit(this.eopUserDetail);
/* 27 */       this.msgs.add("修改成功");
/*    */     } catch (RuntimeException e) {
/* 29 */       this.msgs.add(e.getMessage());
/*    */     }
/*    */ 
/* 32 */     this.urls.put("用户信息页面", "userDetail.do");
/*    */ 
/* 34 */     return "message";
/*    */   }
/*    */ 
/*    */   public EopUserDetail getEopUserDetail() {
/* 38 */     return this.eopUserDetail;
/*    */   }
/*    */ 
/*    */   public void setEopUserDetail(EopUserDetail eopUserDetail) {
/* 42 */     this.eopUserDetail = eopUserDetail;
/*    */   }
/*    */ 
/*    */   public IUserDetailManager getUserDetailManager() {
/* 46 */     return this.userDetailManager;
/*    */   }
/*    */ 
/*    */   public void setUserDetailManager(IUserDetailManager userDetailManager) {
/* 50 */     this.userDetailManager = userDetailManager;
/*    */   }
/*    */ 
/*    */   public Integer getUserid() {
/* 54 */     return this.userid;
/*    */   }
/*    */ 
/*    */   public void setUserid(Integer userid) {
/* 58 */     this.userid = userid;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.app.base.core.action.UserDetailAction
 * JD-Core Version:    0.6.0
 */