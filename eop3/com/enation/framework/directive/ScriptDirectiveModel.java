/*    */ package com.enation.framework.directive;
/*    */ 
/*    */ import com.enation.framework.resource.Resource;
/*    */ import com.enation.framework.resource.impl.ResourceBuilder;
/*    */ import freemarker.core.Environment;
/*    */ import freemarker.template.TemplateDirectiveBody;
/*    */ import freemarker.template.TemplateDirectiveModel;
/*    */ import freemarker.template.TemplateException;
/*    */ import freemarker.template.TemplateModel;
/*    */ import java.io.IOException;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class ScriptDirectiveModel extends AbstractResourceDirectiveModel
/*    */   implements TemplateDirectiveModel
/*    */ {
/*    */   public void execute(Environment env, Map params, TemplateModel[] arg2, TemplateDirectiveBody arg3)
/*    */     throws TemplateException, IOException
/*    */   {
/* 28 */     Resource resource = createResource(params);
/* 29 */     resource.setType("javascript");
/* 30 */     ResourceBuilder.putScript(resource);
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.framework.directive.ScriptDirectiveModel
 * JD-Core Version:    0.6.0
 */