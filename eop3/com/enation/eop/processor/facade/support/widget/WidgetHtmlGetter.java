/*    */ package com.enation.eop.processor.facade.support.widget;
/*    */ 
/*    */ import com.enation.eop.processor.facade.support.LocalWidgetPaser;
/*    */ import com.enation.eop.processor.facade.support.WidgetEditModeWrapper;
/*    */ import com.enation.eop.processor.widget.IWidgetHtmlGetter;
/*    */ import com.enation.eop.processor.widget.IWidgetPaser;
/*    */ import com.enation.eop.sdk.user.IUserService;
/*    */ import com.enation.eop.sdk.user.UserServiceFactory;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class WidgetHtmlGetter
/*    */   implements IWidgetHtmlGetter
/*    */ {
/*    */   public String process(Map<String, String> params, String page)
/*    */   {
/* 35 */     IWidgetPaser widgetPaser = new LocalWidgetPaser();
/* 36 */     widgetPaser = new BorderWrapper(widgetPaser);
/* 37 */     if ((UserServiceFactory.getUserService().isUserLoggedIn()) && ("edit".equals(params.get("mode")))) {
/* 38 */       widgetPaser = new WidgetEditModeWrapper(widgetPaser);
/*    */     }
/* 40 */     String html = widgetPaser.pase(params);
/* 41 */     return html;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.eop.processor.facade.support.widget.WidgetHtmlGetter
 * JD-Core Version:    0.6.0
 */