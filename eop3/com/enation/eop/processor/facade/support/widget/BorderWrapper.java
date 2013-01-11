/*    */ package com.enation.eop.processor.facade.support.widget;
/*    */ 
/*    */ import com.enation.eop.processor.widget.IWidgetPaser;
/*    */ import com.enation.eop.processor.widget.WidgetWrapper;
/*    */ import com.enation.eop.resource.model.EopSite;
/*    */ import com.enation.eop.sdk.context.EopContext;
/*    */ import com.enation.eop.sdk.context.EopSetting;
/*    */ import com.enation.eop.sdk.utils.FreeMarkerUtil;
/*    */ import com.enation.framework.context.webcontext.ThreadContextHolder;
/*    */ import com.sun.xml.messaging.saaj.util.ByteOutputStream;
/*    */ import freemarker.template.Configuration;
/*    */ import freemarker.template.Template;
/*    */ import java.io.OutputStreamWriter;
/*    */ import java.io.Writer;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ 
/*    */ public class BorderWrapper extends WidgetWrapper
/*    */ {
/*    */   public BorderWrapper(IWidgetPaser paser)
/*    */   {
/* 29 */     super(paser);
/*    */   }
/*    */ 
/*    */   public String pase(Map<String, String> params)
/*    */   {
/* 35 */     String content = super.pase(params);
/* 36 */     String border = (String)params.get("border");
/* 37 */     String widgetid = "widget_" + (String)params.get("widgetid");
/* 38 */     if ((border == null) || (border.equals("")) || (border.equals("none")))
/*    */     {
/* 40 */       if (("yes".equals(ThreadContextHolder.getHttpRequest().getParameter("ajax"))) || ("widget_header".equals(widgetid)))
/*    */       {
/* 42 */         return content;
/*    */       }
/* 44 */       return content;
/*    */     }
/*    */ 
/* 49 */     EopSite site = EopContext.getContext().getCurrentSite();
/* 50 */     String contextPath = EopContext.getContext().getContextPath();
/* 51 */     String borderPath = contextPath + "/themes/" + site.getThemepath() + "/borders/";
/* 52 */     borderPath = EopSetting.EOP_PATH + borderPath;
/*    */     try
/*    */     {
/* 55 */       Map data = new HashMap();
/*    */ 
/* 57 */       data.put("widgetid", widgetid);
/* 58 */       data.put("body", content);
/* 59 */       data.put("title", params.get("bordertitle"));
/* 60 */       data.putAll(params);
/* 61 */       Configuration cfg = FreeMarkerUtil.getFolderCfg(borderPath);
/* 62 */       Template temp = cfg.getTemplate(border + ".html");
/* 63 */       ByteOutputStream stream = new ByteOutputStream();
/* 64 */       Writer out = new OutputStreamWriter(stream);
/* 65 */       temp.process(data, out);
/* 66 */       out.flush();
/* 67 */       return stream.toString();
/*    */     } catch (Exception e) {
/* 69 */       e.printStackTrace();
/* 70 */     }return content;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.eop.processor.facade.support.widget.BorderWrapper
 * JD-Core Version:    0.6.0
 */