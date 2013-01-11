/*    */ package com.enation.eop.processor.facade.support.widget;
/*    */ 
/*    */ import com.enation.eop.processor.widget.IWidgetParamParser;
/*    */ import com.enation.framework.cache.AbstractCacheProxy;
/*    */ import com.enation.framework.cache.ICache;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class XmlWidgetParamParserCacheProxy extends AbstractCacheProxy
/*    */   implements IWidgetParamParser
/*    */ {
/* 10 */   private static String cacheName = "widget_key";
/*    */   private IWidgetParamParser xmlWidgetParamParserImpl;
/*    */ 
/*    */   public XmlWidgetParamParserCacheProxy(IWidgetParamParser _xmlWidgetParamParserImpl)
/*    */   {
/* 14 */     super(cacheName);
/* 15 */     this.xmlWidgetParamParserImpl = _xmlWidgetParamParserImpl;
/*    */   }
/*    */ 
/*    */   public Map<String, Map<String, Map<String, String>>> parse()
/*    */   {
/* 21 */     Object obj = this.cache.get("obj");
/*    */ 
/* 23 */     if (obj == null) {
/* 24 */       obj = this.xmlWidgetParamParserImpl.parse();
/* 25 */       this.cache.put("obj", obj);
/*    */     }
/*    */ 
/* 31 */     return (Map)obj;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.eop.processor.facade.support.widget.XmlWidgetParamParserCacheProxy
 * JD-Core Version:    0.6.0
 */