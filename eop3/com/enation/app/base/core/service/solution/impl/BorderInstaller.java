/*    */ package com.enation.app.base.core.service.solution.impl;
/*    */ 
/*    */ import com.enation.app.base.core.service.solution.IInstaller;
/*    */ import com.enation.eop.resource.IBorderManager;
/*    */ import com.enation.eop.resource.model.Border;
/*    */ import com.enation.eop.sdk.context.EopContext;
/*    */ import com.enation.eop.sdk.context.EopSetting;
/*    */ import com.enation.framework.util.FileUtil;
/*    */ import org.w3c.dom.Element;
/*    */ import org.w3c.dom.Node;
/*    */ import org.w3c.dom.NodeList;
/*    */ 
/*    */ public class BorderInstaller
/*    */   implements IInstaller
/*    */ {
/*    */   private IBorderManager borderManager;
/*    */ 
/*    */   public void install(String productId, Node fragment)
/*    */   {
/*    */     try
/*    */     {
/* 26 */       FileUtil.copyFolder(EopSetting.PRODUCTS_STORAGE_PATH + "/" + productId + "/borders/", EopSetting.EOP_PATH + EopContext.getContext().getContextPath() + "/borders/");
/*    */     }
/*    */     catch (Exception e)
/*    */     {
/* 30 */       e.printStackTrace();
/* 31 */       throw new RuntimeException("安装borders出错...");
/*    */     }
/*    */ 
/* 35 */     if (fragment.getNodeType() == 1) {
/* 36 */       Element themeNode = (Element)fragment;
/* 37 */       NodeList nodeList = themeNode.getChildNodes();
/* 38 */       for (int i = 0; i < nodeList.getLength(); i++) {
/* 39 */         Node node = nodeList.item(i);
/* 40 */         if (node.getNodeType() == 1) {
/* 41 */           Element el = (Element)node;
/* 42 */           String id = el.getAttribute("id");
/* 43 */           String name = el.getAttribute("name");
/* 44 */           Border border = new Border();
/* 45 */           border.setBorderid(id);
/* 46 */           border.setBordername(name);
/* 47 */           border.setThemepath(themeNode.getAttribute("id"));
/* 48 */           this.borderManager.add(border);
/*    */         }
/*    */       }
/*    */     }
/*    */   }
/*    */ 
/*    */   public void setBorderManager(IBorderManager borderManager) {
/* 55 */     this.borderManager = borderManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.app.base.core.service.solution.impl.BorderInstaller
 * JD-Core Version:    0.6.0
 */