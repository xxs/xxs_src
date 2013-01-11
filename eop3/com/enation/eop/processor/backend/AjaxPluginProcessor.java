/*    */ package com.enation.eop.processor.backend;
/*    */ 
/*    */ import com.enation.eop.processor.Processor;
/*    */ import com.enation.eop.processor.core.Response;
/*    */ import com.enation.eop.processor.core.StringResponse;
/*    */ import com.enation.framework.context.spring.SpringContextHolder;
/*    */ import com.enation.framework.plugin.IAjaxExecuteEnable;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ 
/*    */ public class AjaxPluginProcessor
/*    */   implements Processor
/*    */ {
/*    */   public Response process(int mode, HttpServletResponse httpResponse, HttpServletRequest httpRequest)
/*    */   {
/* 24 */     Response response = new StringResponse();
/* 25 */     String beanid = httpRequest.getParameter("beanid");
/* 26 */     Object plugin = SpringContextHolder.getBean(beanid);
/*    */ 
/* 31 */     if (plugin == null) {
/* 32 */       response.setContent("error:plugin is null");
/* 33 */       return response;
/*    */     }
/*    */ 
/* 36 */     if (!(plugin instanceof IAjaxExecuteEnable)) {
/* 37 */       response.setContent("error:plugin is not a IAjaxExecuteEnable");
/* 38 */       return response;
/*    */     }
/*    */ 
/* 41 */     IAjaxExecuteEnable ajaxPlugin = (IAjaxExecuteEnable)plugin;
/*    */ 
/* 43 */     String content = ajaxPlugin.execute();
/* 44 */     response.setContent(content);
/* 45 */     return response;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.eop.processor.backend.AjaxPluginProcessor
 * JD-Core Version:    0.6.0
 */