/*    */ package com.enation.eop.processor.facade;
/*    */ 
/*    */ import com.enation.eop.processor.Processor;
/*    */ import com.enation.eop.processor.core.Response;
/*    */ import com.enation.eop.processor.core.StringResponse;
/*    */ import com.enation.eop.processor.widget.IWidgetCfgHtmlParser;
/*    */ import com.enation.framework.context.spring.SpringContextHolder;
/*    */ import com.enation.framework.util.RequestUtil;
/*    */ import java.util.Map;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ public class WidgetSettingProcessor
/*    */   implements Processor
/*    */ {
/* 25 */   protected final Logger logger = Logger.getLogger(getClass());
/*    */ 
/*    */   public Response process(int mode, HttpServletResponse httpResponse, HttpServletRequest httpRequest)
/*    */   {
/* 42 */     Map params = RequestUtil.paramToMap(httpRequest);
/* 43 */     IWidgetCfgHtmlParser widgetCfgParser = (IWidgetCfgHtmlParser)SpringContextHolder.getBean("localWidgetCfgPaser");
/*    */ 
/* 46 */     String content = widgetCfgParser.pase(params);
/* 47 */     Response response = new StringResponse();
/* 48 */     response.setContent(content);
/* 49 */     return response;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.eop.processor.facade.WidgetSettingProcessor
 * JD-Core Version:    0.6.0
 */