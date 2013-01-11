/*    */ package com.enation.framework.directive;
/*    */ 
/*    */ import com.enation.framework.util.DateUtil;
/*    */ import freemarker.core.Environment;
/*    */ import freemarker.template.TemplateDirectiveBody;
/*    */ import freemarker.template.TemplateDirectiveModel;
/*    */ import freemarker.template.TemplateException;
/*    */ import freemarker.template.TemplateModel;
/*    */ import java.io.IOException;
/*    */ import java.io.Writer;
/*    */ import java.util.Date;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class DateformateDirective
/*    */   implements TemplateDirectiveModel
/*    */ {
/*    */   public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
/*    */     throws TemplateException, IOException
/*    */   {
/* 21 */     String timeStr = params.get("time").toString();
/* 22 */     String pattern = params.get("pattern").toString();
/* 23 */     long time = Long.valueOf(timeStr).longValue();
/* 24 */     if (time > 0L)
/*    */     {
/* 26 */       Date date = new Date(time);
/* 27 */       String str = DateUtil.toString(date, pattern);
/* 28 */       env.getOut().write(str);
/*    */     } else {
/* 30 */       env.getOut().write("");
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.framework.directive.DateformateDirective
 * JD-Core Version:    0.6.0
 */