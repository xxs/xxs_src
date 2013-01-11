/*    */ package com.enation.app.base.taglib;
/*    */ 
/*    */ import com.enation.app.base.core.service.auth.IPermissionManager;
/*    */ import com.enation.app.base.core.service.auth.impl.PermissionConfig;
/*    */ import com.enation.framework.context.spring.SpringContextHolder;
/*    */ import com.enation.framework.taglib.EnationTagSupport;
/*    */ import javax.servlet.jsp.JspException;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ 
/*    */ public class PermissionTablib extends EnationTagSupport
/*    */ {
/*    */   private String actid;
/*    */ 
/*    */   public int doStartTag()
/*    */     throws JspException
/*    */   {
/* 27 */     IPermissionManager permissionManager = (IPermissionManager)SpringContextHolder.getBean("permissionManager");
/* 28 */     String[] arr = StringUtils.split(this.actid, ",");
/* 29 */     boolean result = false;
/* 30 */     for (String item : arr) {
/* 31 */       result = permissionManager.checkHaveAuth(PermissionConfig.getAuthId(item));
/* 32 */       if (result)
/*    */       {
/*    */         break;
/*    */       }
/*    */     }
/* 37 */     if (result) {
/* 38 */       return 1;
/*    */     }
/* 40 */     return 0;
/*    */   }
/*    */ 
/*    */   public String getActid() {
/* 44 */     return this.actid;
/*    */   }
/*    */ 
/*    */   public void setActid(String actid) {
/* 48 */     this.actid = actid;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.app.base.taglib.PermissionTablib
 * JD-Core Version:    0.6.0
 */