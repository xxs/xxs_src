/*    */ package com.enation.app.shop.component.goodscore.widget.goods.search2.pager;
/*    */ 
/*    */ import freemarker.core.Environment;
/*    */ import freemarker.template.TemplateDirectiveBody;
/*    */ import freemarker.template.TemplateDirectiveModel;
/*    */ import freemarker.template.TemplateException;
/*    */ import freemarker.template.TemplateModel;
/*    */ import java.io.IOException;
/*    */ import java.io.Writer;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class SearchPagerDirectiveModel
/*    */   implements TemplateDirectiveModel
/*    */ {
/*    */   public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
/*    */     throws TemplateException, IOException
/*    */   {
/* 34 */     String total = params.get("total").toString();
/* 35 */     String pagesize = params.get("pagesize").toString();
/* 36 */     String pageNo = params.get("pageno").toString();
/* 37 */     SearchPagerHtmlBuilder pagerHtmlBuilder = new SearchPagerHtmlBuilder(Integer.valueOf(pageNo).intValue(), Integer.valueOf(total).intValue(), Integer.valueOf(pagesize).intValue());
/* 38 */     String page_html = pagerHtmlBuilder.buildPageHtml();
/* 39 */     env.getOut().write(page_html);
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.goodscore.widget.goods.search2.pager.SearchPagerDirectiveModel
 * JD-Core Version:    0.6.0
 */