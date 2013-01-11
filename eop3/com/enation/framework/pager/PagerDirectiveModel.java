/*    */ package com.enation.framework.pager;
/*    */ 
/*    */ import com.enation.framework.pager.impl.SimplePageHtmlBuilder;
/*    */ import freemarker.core.Environment;
/*    */ import freemarker.template.TemplateDirectiveBody;
/*    */ import freemarker.template.TemplateDirectiveModel;
/*    */ import freemarker.template.TemplateException;
/*    */ import freemarker.template.TemplateModel;
/*    */ import java.io.IOException;
/*    */ import java.io.Writer;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class PagerDirectiveModel
/*    */   implements TemplateDirectiveModel
/*    */ {
/*    */   public void execute(Environment env, Map params, TemplateModel[] arg2, TemplateDirectiveBody arg3)
/*    */     throws TemplateException, IOException
/*    */   {
/* 23 */     String pageno = params.get("pageno").toString();
/* 24 */     String pagesize = params.get("pagesize").toString();
/* 25 */     String totalcount = params.get("totalcount").toString();
/* 26 */     int _pageNum = Integer.valueOf(pageno).intValue();
/* 27 */     int _totalCount = Integer.valueOf(totalcount).intValue();
/* 28 */     int _pageSize = Integer.valueOf(pagesize).intValue();
/* 29 */     SimplePageHtmlBuilder pageHtmlBuilder = new SimplePageHtmlBuilder(_pageNum, _totalCount, _pageSize);
/* 30 */     String html = pageHtmlBuilder.buildPageHtml();
/* 31 */     env.getOut().write(html);
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.framework.pager.PagerDirectiveModel
 * JD-Core Version:    0.6.0
 */