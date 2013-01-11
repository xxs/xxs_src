/*    */ package com.enation.eop.processor.core.freemarker;
/*    */ 
/*    */ import freemarker.core.Environment;
/*    */ import freemarker.template.TemplateDirectiveBody;
/*    */ import freemarker.template.TemplateDirectiveModel;
/*    */ import freemarker.template.TemplateException;
/*    */ import freemarker.template.TemplateModel;
/*    */ import java.io.IOException;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class RepeatDirective
/*    */   implements TemplateDirectiveModel
/*    */ {
/*    */   public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
/*    */     throws TemplateException, IOException
/*    */   {
/* 17 */     Integer count = Integer.valueOf(params.get("count").toString());
/* 18 */     int i = 0; for (int len = count.intValue(); i < len; i++)
/* 19 */       body.render(env.getOut());
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.eop.processor.core.freemarker.RepeatDirective
 * JD-Core Version:    0.6.0
 */