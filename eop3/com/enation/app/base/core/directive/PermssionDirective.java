/*    */ package com.enation.app.base.core.directive;
/*    */ 
/*    */ import com.enation.app.base.core.service.auth.IPermissionManager;
/*    */ import com.enation.app.base.core.service.auth.impl.PermissionConfig;
/*    */ import com.enation.framework.context.spring.SpringContextHolder;
/*    */ import freemarker.core.Environment;
/*    */ import freemarker.template.TemplateDirectiveBody;
/*    */ import freemarker.template.TemplateDirectiveModel;
/*    */ import freemarker.template.TemplateException;
/*    */ import freemarker.template.TemplateModel;
/*    */ import java.io.IOException;
/*    */ import java.util.Map;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ 
/*    */ public class PermssionDirective
/*    */   implements TemplateDirectiveModel
/*    */ {
/*    */   public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
/*    */     throws TemplateException, IOException
/*    */   {
/* 23 */     IPermissionManager permissionManager = (IPermissionManager)SpringContextHolder.getBean("permissionManager");
/* 24 */     String actid = params.get("actid").toString();
/* 25 */     String[] arr = StringUtils.split(actid, ",");
/* 26 */     boolean result = false;
/* 27 */     for (String item : arr) {
/* 28 */       result = permissionManager.checkHaveAuth(PermissionConfig.getAuthId(item));
/* 29 */       if (result)
/*    */       {
/*    */         break;
/*    */       }
/*    */     }
/* 34 */     if (result)
/* 35 */       body.render(env.getOut());
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.app.base.core.directive.PermssionDirective
 * JD-Core Version:    0.6.0
 */