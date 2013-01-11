/*     */ package com.enation.app.base.core.service.solution.impl;
/*     */ 
/*     */ import com.enation.app.base.core.service.solution.IInstaller;
/*     */ import com.enation.eop.resource.IMenuManager;
/*     */ import com.enation.eop.resource.model.Menu;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.NodeList;
/*     */ 
/*     */ public class MenuInstaller
/*     */   implements IInstaller
/*     */ {
/*     */   private IMenuManager menuManager;
/*     */ 
/*     */   public void install(String productId, Node fragment)
/*     */   {
/*  22 */     NodeList menuList = fragment.getChildNodes();
/*  23 */     addMenu(menuList, Integer.valueOf(0));
/*     */   }
/*     */ 
/*     */   private void addMenu(NodeList nodeList, Integer parentId)
/*     */   {
/*  33 */     for (int i = 0; i < nodeList.getLength(); i++) {
/*  34 */       Node node = nodeList.item(i);
/*  35 */       if (node.getNodeType() == 1)
/*  36 */         addMenu((Element)node, parentId);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void addMenu(Element ele, Integer parentId)
/*     */   {
/*     */     try
/*     */     {
/*  49 */       Menu menu = new Menu();
/*  50 */       menu.setPid(parentId);
/*     */ 
/*  52 */       String text = ele.getAttribute("text");
/*  53 */       String url = ele.getAttribute("url");
/*  54 */       String type = ele.getAttribute("type");
/*  55 */       String selected = ele.getAttribute("selected");
/*  56 */       String target = ele.getAttribute("target");
/*  57 */       String mode = ele.getAttribute("mode");
/*  58 */       if (type == null) {
/*  59 */         type = ((Element)ele.getParentNode()).getAttribute("type");
/*     */       }
/*     */ 
/*  62 */       if (target != null) {
/*  63 */         menu.setTarget(target);
/*     */       }
/*     */ 
/*  66 */       int menuType = 2;
/*  67 */       if ("sys".equals(type))
/*  68 */         menuType = 1;
/*  69 */       if ("app".equals(type))
/*  70 */         menuType = 2;
/*  71 */       if ("ext".equals(type)) {
/*  72 */         menuType = 3;
/*     */       }
/*  74 */       menu.setMenutype(Integer.valueOf(menuType));
/*  75 */       menu.setTitle(text);
/*  76 */       if ((selected != null) && (!selected.equals(""))) {
/*  77 */         menu.setSelected(Integer.valueOf(selected));
/*     */       }
/*  79 */       if (url != null)
/*  80 */         menu.setUrl(url);
/*  81 */       menu.setSorder(Integer.valueOf(50));
/*  82 */       if (mode != null) {
/*  83 */         menu.setAppid(mode);
/*     */       }
/*  85 */       Integer menuid = this.menuManager.add(menu);
/*  86 */       NodeList children = ele.getChildNodes();
/*     */ 
/*  88 */       if (children != null)
/*  89 */         addMenu(children, menuid);
/*     */     }
/*     */     catch (Exception e) {
/*  92 */       e.printStackTrace();
/*  93 */       throw new RuntimeException("install menu error");
/*     */     }
/*     */   }
/*     */ 
/*     */   public IMenuManager getMenuManager() {
/*  98 */     return this.menuManager;
/*     */   }
/*     */ 
/*     */   public void setMenuManager(IMenuManager menuManager) {
/* 102 */     this.menuManager = menuManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.app.base.core.service.solution.impl.MenuInstaller
 * JD-Core Version:    0.6.0
 */