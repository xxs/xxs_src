/*    */ package com.enation.framework.directive;
/*    */ 
/*    */ import com.enation.eop.resource.model.EopSite;
/*    */ import com.enation.eop.sdk.context.EopContext;
/*    */ import com.enation.eop.sdk.context.EopSetting;
/*    */ import com.enation.eop.sdk.utils.UploadUtil;
/*    */ import com.enation.framework.util.StringUtil;
/*    */ import freemarker.core.Environment;
/*    */ import freemarker.template.TemplateDirectiveBody;
/*    */ import freemarker.template.TemplateDirectiveModel;
/*    */ import freemarker.template.TemplateException;
/*    */ import freemarker.template.TemplateModel;
/*    */ import java.io.IOException;
/*    */ import java.io.Writer;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class ImageUrlDirectiveModel
/*    */   implements TemplateDirectiveModel
/*    */ {
/*    */   public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
/*    */     throws TemplateException, IOException
/*    */   {
/* 29 */     String pic = params.get("pic").toString();
/* 30 */     String postfix = null;
/* 31 */     if (params.get("postfix") != null) {
/* 32 */       postfix = params.get("postfix").toString();
/*    */     }
/* 34 */     pic = getImageUrl(pic, postfix);
/* 35 */     env.getOut().write(pic);
/*    */   }
/*    */ 
/*    */   private String getImageUrl(String pic, String postfix)
/*    */   {
/* 41 */     if (StringUtil.isEmpty(pic))
/* 42 */       pic = EopSetting.DEFAULT_IMG_URL;
/* 43 */     if (pic.toUpperCase().startsWith("HTTP"))
/* 44 */       return pic;
/* 45 */     if (pic.startsWith("fs:")) {
/* 46 */       pic = UploadUtil.replacePath(pic);
/*    */     }
/*    */     else {
/* 49 */       EopContext ectx = EopContext.getContext();
/*    */ 
/* 52 */       if (!pic.startsWith("/")) {
/* 53 */         pic = "/" + pic;
/*    */       }
/*    */ 
/* 63 */       if (("2".equals(EopSetting.RESOURCEMODE)) || (EopSetting.DEVELOPMENT_MODEL))
/*    */       {
/* 65 */         EopSite site = ectx.getCurrentSite();
/*    */ 
/* 67 */         pic = ectx.getResDomain() + "/themes/" + site.getThemepath() + pic;
/*    */       }
/*    */       else
/*    */       {
/* 71 */         pic = ectx.getResDomain() + pic;
/*    */       }
/*    */ 
/*    */     }
/*    */ 
/* 76 */     if (!StringUtil.isEmpty(postfix)) {
/* 77 */       return UploadUtil.getThumbPath(pic, postfix);
/*    */     }
/* 79 */     return pic;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.framework.directive.ImageUrlDirectiveModel
 * JD-Core Version:    0.6.0
 */