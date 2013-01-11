/*     */ package com.enation.app.base.core.action;
/*     */ 
/*     */ import com.enation.app.base.core.model.SiteMenu;
/*     */ import com.enation.app.base.core.service.ISiteMenuManager;
/*     */ import com.enation.framework.action.WWAction;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class SiteMenuAction extends WWAction
/*     */ {
/*     */   private ISiteMenuManager siteMenuManager;
/*     */   private List menuList;
/*     */   private Integer[] sortArray;
/*     */   private Integer[] menuidArray;
/*     */   private Integer menuid;
/*     */   private SiteMenu siteMenu;
/*     */   private boolean isEdit;
/*     */ 
/*     */   public String list()
/*     */   {
/*  26 */     this.menuList = this.siteMenuManager.list(Integer.valueOf(0));
/*  27 */     return "list";
/*     */   }
/*     */ 
/*     */   public String updateSort() {
/*  31 */     this.siteMenuManager.updateSort(this.menuidArray, this.sortArray);
/*  32 */     return list();
/*     */   }
/*     */ 
/*     */   public String add()
/*     */   {
/*  37 */     this.isEdit = false;
/*  38 */     this.menuList = this.siteMenuManager.list(Integer.valueOf(0));
/*  39 */     this.siteMenu = new SiteMenu();
/*  40 */     return "input";
/*     */   }
/*     */ 
/*     */   public String edit() {
/*  44 */     this.isEdit = true;
/*  45 */     this.menuList = this.siteMenuManager.list(Integer.valueOf(0));
/*  46 */     this.siteMenu = this.siteMenuManager.get(this.menuid);
/*  47 */     return "input";
/*     */   }
/*     */ 
/*     */   public String save() {
/*  51 */     if (this.menuid == null) {
/*  52 */       this.siteMenuManager.add(this.siteMenu);
/*  53 */       this.msgs.add("菜单添加成功");
/*     */     } else {
/*  55 */       this.siteMenu.setMenuid(this.menuid);
/*  56 */       this.siteMenuManager.edit(this.siteMenu);
/*  57 */       this.msgs.add("菜单修改成功");
/*     */     }
/*     */ 
/*  60 */     this.urls.put("菜单列表", "siteMenu!list.do");
/*  61 */     return "message";
/*     */   }
/*     */ 
/*     */   public String delete()
/*     */   {
/*  66 */     this.siteMenuManager.delete(this.menuid);
/*  67 */     this.msgs.add("菜单删除成功");
/*  68 */     this.urls.put("菜单列表", "siteMenu!list.do");
/*  69 */     return "message";
/*     */   }
/*     */ 
/*     */   public ISiteMenuManager getSiteMenuManager() {
/*  73 */     return this.siteMenuManager;
/*     */   }
/*     */   public void setSiteMenuManager(ISiteMenuManager siteMenuManager) {
/*  76 */     this.siteMenuManager = siteMenuManager;
/*     */   }
/*     */   public List getMenuList() {
/*  79 */     return this.menuList;
/*     */   }
/*     */   public void setMenuList(List menuList) {
/*  82 */     this.menuList = menuList;
/*     */   }
/*     */ 
/*     */   public Integer[] getSortArray() {
/*  86 */     return this.sortArray;
/*     */   }
/*     */ 
/*     */   public void setSortArray(Integer[] sortArray) {
/*  90 */     this.sortArray = sortArray;
/*     */   }
/*     */ 
/*     */   public Integer[] getMenuidArray() {
/*  94 */     return this.menuidArray;
/*     */   }
/*     */ 
/*     */   public void setMenuidArray(Integer[] menuidArray) {
/*  98 */     this.menuidArray = menuidArray;
/*     */   }
/*     */ 
/*     */   public Integer getMenuid() {
/* 102 */     return this.menuid;
/*     */   }
/*     */ 
/*     */   public void setMenuid(Integer menuid) {
/* 106 */     this.menuid = menuid;
/*     */   }
/*     */ 
/*     */   public SiteMenu getSiteMenu() {
/* 110 */     return this.siteMenu;
/*     */   }
/*     */ 
/*     */   public void setSiteMenu(SiteMenu siteMenu) {
/* 114 */     this.siteMenu = siteMenu;
/*     */   }
/*     */ 
/*     */   public boolean getIsEdit() {
/* 118 */     return this.isEdit;
/*     */   }
/*     */ 
/*     */   public void setEdit(boolean isEdit) {
/* 122 */     this.isEdit = isEdit;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.app.base.core.action.SiteMenuAction
 * JD-Core Version:    0.6.0
 */