/*    */ package com.enation.eop.processor.widget;
/*    */ 
/*    */ import java.util.Map;
/*    */ 
/*    */ public class WidgetWrapper
/*    */   implements IWidgetPaser
/*    */ {
/*    */   protected IWidgetPaser widgetPaser;
/*    */ 
/*    */   public WidgetWrapper(IWidgetPaser paser)
/*    */   {
/* 15 */     this.widgetPaser = paser;
/*    */   }
/*    */ 
/*    */   public String pase(Map<String, String> params)
/*    */   {
/* 22 */     return this.widgetPaser.pase(params);
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.eop.processor.widget.WidgetWrapper
 * JD-Core Version:    0.6.0
 */