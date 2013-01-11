/*    */ package com.enation.framework.pager;
/*    */ 
/*    */ import com.enation.framework.pager.impl.AjaxPagerHtmlBuilder;
/*    */ import freemarker.core.Environment;
/*    */ import freemarker.template.TemplateDirectiveBody;
/*    */ import freemarker.template.TemplateDirectiveModel;
/*    */ import freemarker.template.TemplateException;
/*    */ import freemarker.template.TemplateModel;
/*    */ import java.io.IOException;
/*    */ import java.io.Writer;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class AjaxPagerDirectiveModel
/*    */   implements TemplateDirectiveModel
/*    */ {
/*    */   public void execute(Environment env, Map params, TemplateModel[] arg2, TemplateDirectiveBody arg3)
/*    */     throws TemplateException, IOException
/*    */   {
/* 20 */     String pageno = params.get("pageno").toString();
/* 21 */     String pagesize = params.get("pagesize").toString();
/* 22 */     String totalcount = params.get("totalcount").toString();
/* 23 */     int _pageNum = Integer.valueOf(pageno).intValue();
/* 24 */     int _totalCount = Integer.valueOf(totalcount).intValue();
/* 25 */     int _pageSize = Integer.valueOf(pagesize).intValue();
/* 26 */     AjaxPagerHtmlBuilder pageHtmlBuilder = new AjaxPagerHtmlBuilder(_pageNum, _totalCount, _pageSize);
/* 27 */     String html = pageHtmlBuilder.buildPageHtml();
/* 28 */     env.getOut().write(html);
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.framework.pager.AjaxPagerDirectiveModel
 * JD-Core Version:    0.6.0
 */