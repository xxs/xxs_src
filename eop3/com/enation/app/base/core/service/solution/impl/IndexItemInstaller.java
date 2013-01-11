/*    */ package com.enation.app.base.core.service.solution.impl;
/*    */ 
/*    */ import com.enation.app.base.core.service.solution.IInstaller;
/*    */ import com.enation.eop.resource.IIndexItemManager;
/*    */ import com.enation.eop.resource.model.IndexItem;
/*    */ import org.springframework.transaction.annotation.Propagation;
/*    */ import org.springframework.transaction.annotation.Transactional;
/*    */ import org.w3c.dom.Element;
/*    */ import org.w3c.dom.Node;
/*    */ import org.w3c.dom.NodeList;
/*    */ 
/*    */ public class IndexItemInstaller
/*    */   implements IInstaller
/*    */ {
/*    */   private IIndexItemManager indexItemManager;
/*    */ 
/*    */   @Transactional(propagation=Propagation.REQUIRED)
/*    */   public void install(String productId, Node fragment)
/*    */   {
/* 30 */     NodeList nodeList = fragment.getChildNodes();
/* 31 */     int sort = 1;
/* 32 */     for (int i = 0; i < nodeList.getLength(); i++) {
/* 33 */       Node node = nodeList.item(i);
/*    */ 
/* 35 */       if (node.getNodeType() == 1) {
/* 36 */         install((Element)node, sort);
/* 37 */         sort++;
/*    */       }
/*    */     }
/*    */   }
/*    */ 
/*    */   public void install(Element ele, int sort)
/*    */   {
/* 48 */     Element titleEl = getChild(ele, "title");
/* 49 */     Element urlEl = getChild(ele, "url");
/*    */ 
/* 51 */     IndexItem item = new IndexItem();
/* 52 */     item.setTitle(titleEl.getTextContent());
/* 53 */     item.setUrl(urlEl.getTextContent());
/* 54 */     item.setSort(sort);
/*    */ 
/* 56 */     this.indexItemManager.add(item);
/*    */   }
/*    */ 
/*    */   private Element getChild(Element ele, String name)
/*    */   {
/* 67 */     NodeList childList = ele.getElementsByTagName(name);
/* 68 */     Element child = (Element)childList.item(0);
/* 69 */     return child;
/*    */   }
/*    */ 
/*    */   public IIndexItemManager getIndexItemManager() {
/* 73 */     return this.indexItemManager;
/*    */   }
/*    */ 
/*    */   public void setIndexItemManager(IIndexItemManager indexItemManager) {
/* 77 */     this.indexItemManager = indexItemManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.app.base.core.service.solution.impl.IndexItemInstaller
 * JD-Core Version:    0.6.0
 */