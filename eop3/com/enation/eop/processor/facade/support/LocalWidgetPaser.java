/*    */ package com.enation.eop.processor.facade.support;
/*    */ 
/*    */ import com.enation.eop.processor.core.EopException;
/*    */ import com.enation.eop.processor.core.UrlNotFoundException;
/*    */ import com.enation.eop.processor.widget.IWidgetPaser;
/*    */ import com.enation.eop.sdk.widget.IWidget;
/*    */ import com.enation.framework.component.context.WidgetContext;
/*    */ import com.enation.framework.context.spring.SpringContextHolder;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class LocalWidgetPaser
/*    */   implements IWidgetPaser
/*    */ {
/*    */   public String pase(Map<String, String> params)
/*    */   {
/* 22 */     if (params == null) throw new EopException("挂件参数不能为空");
/*    */ 
/* 24 */     String widgetType = (String)params.get("type");
/* 25 */     if (widgetType == null) throw new EopException("挂件类型不能为空");
/*    */ 
/* 27 */     if (!WidgetContext.getWidgetState(widgetType).booleanValue()) return "此挂件已停用";
/*    */ 
/*    */     try
/*    */     {
/* 32 */       IWidget widget = (IWidget)SpringContextHolder.getBean(widgetType);
/*    */       String content;
/*    */       String content;
/* 35 */       if (widget == null) { content = "widget[" + widgetType + "]not found";
/*    */       } else {
/* 37 */         content = widget.process(params);
/* 38 */         widget.update(params);
/*    */       }
/*    */ 
/* 41 */       return content;
/*    */     }
/*    */     catch (UrlNotFoundException e) {
/* 44 */       throw e;
/*    */     } catch (Exception e) {
/* 46 */       e.printStackTrace();
/* 47 */     }return "widget[" + widgetType + "]pase error ";
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.eop.processor.facade.support.LocalWidgetPaser
 * JD-Core Version:    0.6.0
 */