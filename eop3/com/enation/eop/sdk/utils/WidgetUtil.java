/*    */ package com.enation.eop.sdk.utils;
/*    */ 
/*    */ import com.enation.eop.processor.Widget;
/*    */ import com.enation.eop.resource.model.EopApp;
/*    */ 
/*    */ public class WidgetUtil
/*    */ {
/*    */   public static String toHtml(Widget widget, String content)
/*    */   {
/*  9 */     StringBuffer htmlBuffer = new StringBuffer();
/*    */ 
/* 12 */     htmlBuffer.append("<div ");
/*    */ 
/* 14 */     htmlBuffer.append("class=\"widget\"");
/* 15 */     htmlBuffer.append(" ");
/*    */ 
/* 18 */     htmlBuffer.append("eop_type=\"widget\"");
/* 19 */     htmlBuffer.append(" ");
/*    */ 
/* 27 */     htmlBuffer.append("appId=\"");
/* 28 */     htmlBuffer.append(widget.getApp().getId());
/* 29 */     htmlBuffer.append("\"");
/* 30 */     htmlBuffer.append(" ");
/*    */ 
/* 32 */     htmlBuffer.append("widgetType=\"");
/* 33 */     htmlBuffer.append(widget.getWidgetType());
/* 34 */     htmlBuffer.append("\"");
/* 35 */     htmlBuffer.append(" ");
/*    */ 
/* 37 */     htmlBuffer.append(">");
/*    */ 
/* 39 */     htmlBuffer.append(content);
/*    */ 
/* 41 */     htmlBuffer.append("</div>");
/*    */ 
/* 43 */     return htmlBuffer.toString();
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.eop.sdk.utils.WidgetUtil
 * JD-Core Version:    0.6.0
 */