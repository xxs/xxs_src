/*    */ package com.enation.app.base.core.service.solution.impl;
/*    */ 
/*    */ import com.enation.app.base.core.service.solution.IInstaller;
/*    */ import com.enation.eop.resource.ISiteManager;
/*    */ import com.enation.eop.resource.model.EopSite;
/*    */ import com.enation.eop.sdk.context.EopContext;
/*    */ import org.apache.commons.beanutils.BeanUtils;
/*    */ import org.w3c.dom.Element;
/*    */ import org.w3c.dom.Node;
/*    */ import org.w3c.dom.NodeList;
/*    */ 
/*    */ public class SiteInstaller
/*    */   implements IInstaller
/*    */ {
/*    */   private ISiteManager siteManager;
/*    */ 
/*    */   private boolean setProperty(EopSite site, String name, String value)
/*    */   {
/*    */     try
/*    */     {
/* 18 */       BeanUtils.setProperty(site, name, value);
/* 19 */       return true; } catch (Exception e) {
/*    */     }
/* 21 */     return false;
/*    */   }
/*    */ 
/*    */   public void install(String productId, Node fragment)
/*    */   {
/* 27 */     EopSite site = EopContext.getContext().getCurrentSite();
/*    */ 
/* 29 */     NodeList nodeList = fragment.getChildNodes();
/* 30 */     int i = 0; for (int len = nodeList.getLength(); i < len; i++) {
/* 31 */       Node node = nodeList.item(i);
/* 32 */       if (node.getNodeType() == 1) {
/* 33 */         Element element = (Element)node;
/* 34 */         String name = element.getAttribute("name");
/* 35 */         String value = element.getAttribute("value");
/* 36 */         setProperty(site, name, value);
/*    */       }
/*    */     }
/* 39 */     this.siteManager.edit(site);
/*    */   }
/*    */ 
/*    */   public ISiteManager getSiteManager() {
/* 43 */     return this.siteManager;
/*    */   }
/*    */ 
/*    */   public void setSiteManager(ISiteManager siteManager) {
/* 47 */     this.siteManager = siteManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.app.base.core.service.solution.impl.SiteInstaller
 * JD-Core Version:    0.6.0
 */