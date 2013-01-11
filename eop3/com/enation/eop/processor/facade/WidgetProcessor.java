/*    */ package com.enation.eop.processor.facade;
/*    */ 
/*    */ import com.enation.eop.processor.Processor;
/*    */ import com.enation.eop.processor.core.Response;
/*    */ import com.enation.eop.processor.core.StringResponse;
/*    */ import com.enation.eop.processor.facade.support.LocalWidgetPaser;
/*    */ import com.enation.eop.processor.widget.IWidgetPaser;
/*    */ import com.enation.framework.util.RequestUtil;
/*    */ import java.util.Map;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ 
/*    */ public class WidgetProcessor
/*    */   implements Processor
/*    */ {
/*    */   public Response process(int mode, HttpServletResponse httpResponse, HttpServletRequest httpRequest)
/*    */   {
/* 39 */     Map widgetParams = RequestUtil.paramToMap(httpRequest);
/*    */ 
/* 46 */     IWidgetPaser widgetPaser = new LocalWidgetPaser();
/* 47 */     String content = widgetPaser.pase(widgetParams);
/* 48 */     Response response = new StringResponse();
/*    */ 
/* 50 */     response.setContent(content);
/* 51 */     return response;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.eop.processor.facade.WidgetProcessor
 * JD-Core Version:    0.6.0
 */