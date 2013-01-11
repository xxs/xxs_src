/*    */ package com.enation.app.base.core.service.solution.impl;
/*    */ 
/*    */ import com.enation.app.base.core.service.solution.IInstaller;
/*    */ import com.enation.eop.resource.IWidgetBundleManager;
/*    */ import com.enation.eop.resource.model.WidgetBundle;
/*    */ import org.w3c.dom.Element;
/*    */ import org.w3c.dom.Node;
/*    */ import org.w3c.dom.NodeList;
/*    */ 
/*    */ public class WidgetInstaller
/*    */   implements IInstaller
/*    */ {
/*    */   private IWidgetBundleManager widgetBundleManager;
/*    */ 
/*    */   public void install(String productId, Node fragment)
/*    */   {
/* 22 */     NodeList widgetList = fragment.getChildNodes();
/* 23 */     install(widgetList);
/*    */   }
/*    */ 
/*    */   private void install(Element ele)
/*    */   {
/* 29 */     WidgetBundle widgetBundle = new WidgetBundle();
/* 30 */     widgetBundle.setWidgettype(ele.getAttribute("type"));
/* 31 */     widgetBundle.setWidgetname(ele.getAttribute("name"));
/* 32 */     this.widgetBundleManager.add(widgetBundle);
/*    */   }
/*    */ 
/*    */   private void install(NodeList nodeList)
/*    */   {
/* 38 */     int i = 0; for (int len = nodeList.getLength(); i < len; i++) {
/* 39 */       Node node = nodeList.item(i);
/* 40 */       if (node.getNodeType() == 1)
/* 41 */         install((Element)node);
/*    */     }
/*    */   }
/*    */ 
/*    */   public IWidgetBundleManager getWidgetBundleManager()
/*    */   {
/* 48 */     return this.widgetBundleManager;
/*    */   }
/*    */ 
/*    */   public void setWidgetBundleManager(IWidgetBundleManager widgetBundleManager)
/*    */   {
/* 53 */     this.widgetBundleManager = widgetBundleManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.app.base.core.service.solution.impl.WidgetInstaller
 * JD-Core Version:    0.6.0
 */