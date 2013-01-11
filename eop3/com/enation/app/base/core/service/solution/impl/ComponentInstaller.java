/*    */ package com.enation.app.base.core.service.solution.impl;
/*    */ 
/*    */ import com.enation.app.base.core.service.solution.IInstaller;
/*    */ import com.enation.framework.component.IComponentManager;
/*    */ import org.springframework.transaction.annotation.Propagation;
/*    */ import org.springframework.transaction.annotation.Transactional;
/*    */ import org.w3c.dom.Element;
/*    */ import org.w3c.dom.Node;
/*    */ import org.w3c.dom.NodeList;
/*    */ 
/*    */ public class ComponentInstaller
/*    */   implements IInstaller
/*    */ {
/*    */   private IComponentManager componentManager;
/*    */ 
/*    */   @Transactional(propagation=Propagation.REQUIRED)
/*    */   public void install(String productId, Node fragment)
/*    */   {
/* 25 */     NodeList componentList = fragment.getChildNodes();
/* 26 */     int length = componentList.getLength();
/*    */ 
/* 28 */     for (int i = 0; i < length; i++) {
/* 29 */       Node node = componentList.item(i);
/*    */ 
/* 31 */       if (node.getNodeType() == 1) {
/* 32 */         Element componentEl = (Element)node;
/* 33 */         String componentid = componentEl.getAttribute("id");
/* 34 */         this.componentManager.install(componentid);
/* 35 */         this.componentManager.start(componentid);
/*    */       }
/*    */     }
/*    */   }
/*    */ 
/*    */   public IComponentManager getComponentManager() {
/* 41 */     return this.componentManager;
/*    */   }
/*    */ 
/*    */   public void setComponentManager(IComponentManager componentManager) {
/* 45 */     this.componentManager = componentManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.app.base.core.service.solution.impl.ComponentInstaller
 * JD-Core Version:    0.6.0
 */