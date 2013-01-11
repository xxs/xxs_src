/*     */ package com.enation.app.base.core.action;
/*     */ 
/*     */ import com.enation.eop.resource.IMenuManager;
/*     */ import com.enation.eop.resource.model.Menu;
/*     */ import com.enation.framework.action.WWAction;
/*     */ import java.util.List;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class MenuAction extends WWAction
/*     */ {
/*     */   private IMenuManager menuManager;
/*     */   private List<Menu> menuList;
/*     */   private Menu menu;
/*     */   private Integer parentid;
/*     */   private Integer id;
/*     */   private Integer[] menu_ids;
/*     */   private Integer[] menu_sorts;
/*     */ 
/*     */   public String list()
/*     */   {
/*  23 */     this.menuList = this.menuManager.getMenuTree(Integer.valueOf(0));
/*  24 */     return "list";
/*     */   }
/*     */ 
/*     */   public String add() {
/*  28 */     this.menuList = this.menuManager.getMenuTree(Integer.valueOf(0));
/*  29 */     return "add";
/*     */   }
/*     */ 
/*     */   public String saveAdd() {
/*     */     try {
/*  34 */       this.menuManager.add(this.menu);
/*  35 */       this.json = "{result:1}";
/*     */     } catch (RuntimeException e) {
/*  37 */       this.logger.error(e.getMessage(), e);
/*  38 */       this.json = ("{result:0,message:'" + e.getMessage() + "'}");
/*     */     }
/*  40 */     return "json_message";
/*     */   }
/*     */ 
/*     */   public String edit()
/*     */   {
/*  45 */     this.menuList = this.menuManager.getMenuTree(Integer.valueOf(0));
/*  46 */     this.menu = this.menuManager.get(this.id);
/*  47 */     return "edit";
/*     */   }
/*     */ 
/*     */   public String saveEdit()
/*     */   {
/*     */     try {
/*  53 */       this.menuManager.edit(this.menu);
/*  54 */       this.json = "{result:1}";
/*     */     } catch (RuntimeException e) {
/*  56 */       this.logger.error(e.getMessage(), e);
/*  57 */       this.json = ("{result:0,message:'" + e.getMessage() + "'}");
/*     */     }
/*  59 */     return "json_message";
/*     */   }
/*     */ 
/*     */   public String updateSort()
/*     */   {
/*     */     try
/*     */     {
/*  67 */       this.menuManager.updateSort(this.menu_ids, this.menu_sorts);
/*  68 */       this.json = "{result:1}";
/*     */     } catch (RuntimeException e) {
/*  70 */       this.logger.error(e.getMessage(), e);
/*  71 */       this.json = ("{result:0,message:'" + e.getMessage() + "'}");
/*     */     }
/*  73 */     return "json_message";
/*     */   }
/*     */ 
/*     */   public String delete() {
/*     */     try {
/*  78 */       this.menuManager.delete(this.id);
/*  79 */       this.json = "{result:1}";
/*     */     } catch (RuntimeException e) {
/*  81 */       this.logger.error(e.getMessage(), e);
/*  82 */       this.json = ("{result:0,message:'" + e.getMessage() + "'}");
/*     */     }
/*  84 */     return "json_message";
/*     */   }
/*     */ 
/*     */   public IMenuManager getMenuManager() {
/*  88 */     return this.menuManager;
/*     */   }
/*     */ 
/*     */   public void setMenuManager(IMenuManager menuManager) {
/*  92 */     this.menuManager = menuManager;
/*     */   }
/*     */ 
/*     */   public List<Menu> getMenuList() {
/*  96 */     return this.menuList;
/*     */   }
/*     */ 
/*     */   public void setMenuList(List<Menu> menuList) {
/* 100 */     this.menuList = menuList;
/*     */   }
/*     */ 
/*     */   public Integer getParentid() {
/* 104 */     return this.parentid;
/*     */   }
/*     */ 
/*     */   public void setParentid(Integer parentid) {
/* 108 */     this.parentid = parentid;
/*     */   }
/*     */ 
/*     */   public Integer getId() {
/* 112 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(Integer id) {
/* 116 */     this.id = id;
/*     */   }
/*     */ 
/*     */   public Menu getMenu() {
/* 120 */     return this.menu;
/*     */   }
/*     */ 
/*     */   public void setMenu(Menu menu) {
/* 124 */     this.menu = menu;
/*     */   }
/*     */ 
/*     */   public Integer[] getMenu_ids() {
/* 128 */     return this.menu_ids;
/*     */   }
/*     */ 
/*     */   public void setMenu_ids(Integer[] menuIds) {
/* 132 */     this.menu_ids = menuIds;
/*     */   }
/*     */ 
/*     */   public Integer[] getMenu_sorts() {
/* 136 */     return this.menu_sorts;
/*     */   }
/*     */ 
/*     */   public void setMenu_sorts(Integer[] menuSorts) {
/* 140 */     this.menu_sorts = menuSorts;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.app.base.core.action.MenuAction
 * JD-Core Version:    0.6.0
 */