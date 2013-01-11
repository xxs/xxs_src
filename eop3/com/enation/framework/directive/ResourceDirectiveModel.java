/*    */ package com.enation.framework.directive;
/*    */ 
/*    */ import com.enation.eop.resource.model.EopSite;
/*    */ import com.enation.eop.sdk.context.EopContext;
/*    */ import com.enation.eop.sdk.context.EopSetting;
/*    */ import com.enation.eop.sdk.utils.UploadUtil;
/*    */ import com.enation.framework.resource.Resource;
/*    */ import com.enation.framework.resource.impl.ResourceBuilder;
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
/*    */ public class ResourceDirectiveModel extends AbstractResourceDirectiveModel
/*    */   implements TemplateDirectiveModel
/*    */ {
/*    */   public void execute(Environment env, Map params, TemplateModel[] arg2, TemplateDirectiveBody arg3)
/*    */     throws TemplateException, IOException
/*    */   {
/* 32 */     String type = getValue(params, "type");
/*    */ 
/* 34 */     if (StringUtil.isEmpty(type)) type = "javascript";
/*    */ 
/* 36 */     if (type.equals("script")) type = "javascript";
/*    */ 
/* 38 */     Resource resource = createResource(params);
/* 39 */     resource.setType(type);
/*    */ 
/* 41 */     if ("javascript".equals(type)) {
/* 42 */       ResourceBuilder.putScript(resource);
/*    */     }
/*    */ 
/* 46 */     if ("css".equals(type)) {
/* 47 */       ResourceBuilder.putCss(resource);
/*    */     }
/*    */ 
/* 50 */     if ("image".equals(type)) {
/* 51 */       String src = params.get("src").toString();
/* 52 */       String postfix = getValue(params, "postfix");
/* 53 */       String imageurl = getImageUrl(src, postfix);
/* 54 */       env.getOut().write(imageurl);
/*    */     }
/*    */   }
/*    */ 
/*    */   private String getImageUrl(String pic, String postfix)
/*    */   {
/* 60 */     if (StringUtil.isEmpty(pic))
/* 61 */       pic = EopSetting.DEFAULT_IMG_URL;
/* 62 */     if (pic.toUpperCase().startsWith("HTTP"))
/* 63 */       return pic;
/* 64 */     if (pic.startsWith("fs:")) {
/* 65 */       pic = UploadUtil.replacePath(pic);
/*    */     }
/*    */     else {
/* 68 */       EopContext ectx = EopContext.getContext();
/*    */ 
/* 71 */       if (!pic.startsWith("/")) {
/* 72 */         pic = "/" + pic;
/*    */       }
/*    */ 
/* 82 */       if (("2".equals(EopSetting.RESOURCEMODE)) || (EopSetting.DEVELOPMENT_MODEL))
/*    */       {
/* 84 */         EopSite site = ectx.getCurrentSite();
/*    */ 
/* 86 */         pic = ectx.getResDomain() + "/themes/" + site.getThemepath() + pic;
/*    */       }
/*    */       else
/*    */       {
/* 90 */         pic = ectx.getResDomain() + pic;
/*    */       }
/*    */ 
/*    */     }
/*    */ 
/* 95 */     if (!StringUtil.isEmpty(postfix)) {
/* 96 */       return UploadUtil.getThumbPath(pic, postfix);
/*    */     }
/* 98 */     return pic;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.framework.directive.ResourceDirectiveModel
 * JD-Core Version:    0.6.0
 */