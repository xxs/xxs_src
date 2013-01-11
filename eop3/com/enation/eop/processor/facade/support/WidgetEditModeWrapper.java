/*    */ package com.enation.eop.processor.facade.support;
/*    */ 
/*    */ import com.enation.eop.processor.widget.IWidgetPaser;
/*    */ import com.enation.eop.processor.widget.WidgetWrapper;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class WidgetEditModeWrapper extends WidgetWrapper
/*    */ {
/*    */   public WidgetEditModeWrapper(IWidgetPaser paser)
/*    */   {
/* 23 */     super(paser);
/*    */   }
/*    */ 
/*    */   public String pase(Map<String, String> params)
/*    */   {
/* 30 */     String content = super.pase(params);
/* 31 */     return wrap(content, params);
/*    */   }
/*    */ 
/*    */   private String wrap(String content, Map<String, String> params)
/*    */   {
/* 39 */     content = "<div  class=\"handle\" ><span><a href=\"javascript:;\"  class=\"edit\">设置</a></span><span><a href=\"javascript:;\" class=\"delete\">删除</a></span><span class=\"adjust\"><input type=\"checkbox\"  />微调</span><span class=\"lockwidth\"><input type=\"checkbox\"  />锁定宽</span></div><div class=\"wrapHelper\"></div>" + content;
/*    */ 
/* 41 */     content = "<div class=\"widget\" eop_type=\"widget\" id=\"" + (String)params.get("id") + "\">" + content + "</div>";
/* 42 */     return content;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.eop.processor.facade.support.WidgetEditModeWrapper
 * JD-Core Version:    0.6.0
 */