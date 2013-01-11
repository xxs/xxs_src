/*    */ package com.enation.eop.processor.facade;
/*    */ 
/*    */ import com.enation.app.base.core.service.ISitemapManager;
/*    */ import com.enation.eop.processor.Processor;
/*    */ import com.enation.eop.processor.core.Response;
/*    */ import com.enation.eop.processor.core.StringResponse;
/*    */ import com.enation.framework.context.spring.SpringContextHolder;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ 
/*    */ public class SiteMapProcessor
/*    */   implements Processor
/*    */ {
/*    */   public Response process(int mode, HttpServletResponse httpResponse, HttpServletRequest httpRequest)
/*    */   {
/* 17 */     ISitemapManager siteMapManager = (ISitemapManager)SpringContextHolder.getBean("sitemapManager");
/* 18 */     String siteMapStr = siteMapManager.getsitemap();
/* 19 */     Response response = new StringResponse();
/* 20 */     response.setContent(siteMapStr);
/* 21 */     response.setContentType("text/xml");
/* 22 */     return response;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.eop.processor.facade.SiteMapProcessor
 * JD-Core Version:    0.6.0
 */