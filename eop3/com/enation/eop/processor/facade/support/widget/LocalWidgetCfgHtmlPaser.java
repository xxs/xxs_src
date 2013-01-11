/*    */ package com.enation.eop.processor.facade.support.widget;
/*    */ 
/*    */ import com.enation.eop.processor.widget.IWidgetCfgHtmlParser;
/*    */ import com.enation.eop.sdk.context.EopSetting;
/*    */ import com.enation.eop.sdk.utils.FreeMarkerUtil;
/*    */ import com.enation.eop.sdk.widget.IWidget;
/*    */ import com.enation.framework.context.spring.SpringContextHolder;
/*    */ import com.sun.xml.messaging.saaj.util.ByteOutputStream;
/*    */ import freemarker.template.Configuration;
/*    */ import freemarker.template.Template;
/*    */ import java.io.OutputStreamWriter;
/*    */ import java.io.Writer;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class LocalWidgetCfgHtmlPaser
/*    */   implements IWidgetCfgHtmlParser
/*    */ {
/*    */   public String pase(Map<String, String> widgetParams)
/*    */   {
/* 27 */     String type = (String)widgetParams.get("type");
/*    */ 
/* 30 */     IWidget widget = (IWidget)SpringContextHolder.getBean(type);
/* 31 */     if (widget == null) throw new RuntimeException("widget[" + type + "]not found");
/*    */ 
/* 33 */     String content = widget.setting(widgetParams);
/* 34 */     widgetParams.put("content", content);
/*    */     try
/*    */     {
/* 37 */       String fld = EopSetting.EOP_PATH + "/eop/";
/* 38 */       Configuration cfg = FreeMarkerUtil.getFolderCfg(fld);
/* 39 */       Template temp = cfg.getTemplate("widget_setting.html");
/* 40 */       ByteOutputStream stream = new ByteOutputStream();
/* 41 */       Writer out = new OutputStreamWriter(stream);
/* 42 */       temp.process(widgetParams, out);
/* 43 */       out.flush();
/* 44 */       content = stream.toString();
/*    */     } catch (Exception e) {
/* 46 */       e.printStackTrace();
/*    */     }
/* 48 */     return content;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.eop.processor.facade.support.widget.LocalWidgetCfgHtmlPaser
 * JD-Core Version:    0.6.0
 */