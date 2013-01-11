/*     */ package com.enation.eop.resource.impl;
/*     */ 
/*     */ import com.enation.eop.resource.IMenuManager;
/*     */ import com.enation.eop.resource.model.Menu;
/*     */ import com.enation.eop.sdk.database.BaseSupport;
/*     */ import com.enation.framework.database.IDaoSupport;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.springframework.transaction.annotation.Propagation;
/*     */ import org.springframework.transaction.annotation.Transactional;
/*     */ 
/*     */ public class MenuManagerImpl extends BaseSupport<Menu>
/*     */   implements IMenuManager
/*     */ {
/*     */   public void clean()
/*     */   {
/*  22 */     this.baseDaoSupport.execute("truncate table menu", new Object[0]);
/*     */   }
/*     */   public List<Menu> getMenuList() {
/*  25 */     return this.baseDaoSupport.queryForList("select * from menu where deleteflag = '0' order by sorder asc", Menu.class, new Object[0]);
/*     */   }
/*     */ 
/*     */   public Integer add(Menu menu)
/*     */   {
/*  30 */     if (menu.getTitle() == null) throw new IllegalArgumentException("title argument is null");
/*  31 */     if (menu.getPid() == null) throw new IllegalArgumentException("pid argument is null");
/*  32 */     if (menu.getUrl() == null) throw new IllegalArgumentException("url argument is null");
/*  33 */     if (menu.getSorder() == null) throw new IllegalArgumentException("sorder argument is null");
/*  34 */     menu.setDeleteflag(Integer.valueOf(0));
/*  35 */     this.baseDaoSupport.insert("menu", menu);
/*  36 */     return Integer.valueOf(this.baseDaoSupport.getLastId("menu"));
/*     */   }
/*     */ 
/*     */   public List<Menu> getMenuTree(Integer menuid)
/*     */   {
/*  41 */     if (menuid == null) throw new IllegalArgumentException("menuid argument is null");
/*  42 */     List menuList = getMenuList();
/*  43 */     List topMenuList = new ArrayList();
/*  44 */     for (Menu menu : menuList) {
/*  45 */       if (menu.getPid().compareTo(menuid) == 0) {
/*  46 */         List children = getChildren(menuList, menu.getId());
/*  47 */         menu.setChildren(children);
/*  48 */         topMenuList.add(menu);
/*     */       }
/*     */     }
/*  51 */     return topMenuList;
/*     */   }
/*     */ 
/*     */   private List<Menu> getChildren(List<Menu> menuList, Integer parentid)
/*     */   {
/*  61 */     List children = new ArrayList();
/*  62 */     for (Menu menu : menuList) {
/*  63 */       if (menu.getPid().compareTo(parentid) == 0) {
/*  64 */         menu.setChildren(getChildren(menuList, menu.getId()));
/*  65 */         children.add(menu);
/*     */       }
/*     */     }
/*  68 */     return children;
/*     */   }
/*     */ 
/*     */   public Menu get(Integer id)
/*     */   {
/*  73 */     if (id == null) throw new IllegalArgumentException("ids argument is null");
/*  74 */     String sql = "select * from menu where id=?";
/*  75 */     return (Menu)this.baseDaoSupport.queryForObject(sql, Menu.class, new Object[] { id });
/*     */   }
/*     */ 
/*     */   public Menu get(String title) {
/*  79 */     String sql = "select * from menu where title=?";
/*  80 */     List menuList = this.baseDaoSupport.queryForList(sql, Menu.class, new Object[] { title });
/*     */ 
/*  82 */     if (menuList.isEmpty()) return null;
/*  83 */     return (Menu)menuList.get(0);
/*     */   }
/*     */ 
/*     */   public void edit(Menu menu)
/*     */   {
/*  88 */     if (menu.getId() == null) throw new IllegalArgumentException("id argument is null");
/*  89 */     if (menu.getTitle() == null) throw new IllegalArgumentException("title argument is null");
/*  90 */     if (menu.getPid() == null) throw new IllegalArgumentException("pid argument is null");
/*  91 */     if (menu.getUrl() == null) throw new IllegalArgumentException("url argument is null");
/*  92 */     if (menu.getSorder() == null) throw new IllegalArgumentException("sorder argument is null");
/*  93 */     menu.setDeleteflag(Integer.valueOf(0));
/*  94 */     this.baseDaoSupport.update("menu", menu, "id=" + menu.getId());
/*     */   }
/*     */   @Transactional(propagation=Propagation.REQUIRED)
/*     */   public void updateSort(Integer[] ids, Integer[] sorts) {
/*  99 */     if (ids == null) throw new IllegalArgumentException("ids argument is null");
/* 100 */     if (sorts == null) throw new IllegalArgumentException("sorts argument is null");
/* 101 */     if (sorts.length != ids.length) throw new IllegalArgumentException("ids's length and sorts's length not same");
/* 102 */     for (int i = 0; i < ids.length; i++) {
/* 103 */       String sql = "update menu set sorder=? where id=?";
/* 104 */       this.baseDaoSupport.execute(sql, new Object[] { sorts[i], ids[i] });
/*     */     }
/*     */   }
/*     */ 
/*     */   public void delete(Integer id) throws RuntimeException
/*     */   {
/* 110 */     if (id == null) throw new IllegalArgumentException("ids argument is null");
/* 111 */     String sql = "select count(0) from menu where pid=?";
/* 112 */     int count = this.baseDaoSupport.queryForInt(sql, new Object[] { id });
/* 113 */     if (count > 0) throw new RuntimeException("菜单" + id + "存在子类别,不能直接删除，请先删除其子类别。");
/* 114 */     sql = "delete from menu where id=?";
/* 115 */     this.baseDaoSupport.execute(sql, new Object[] { id });
/*     */   }
/*     */ 
/*     */   public void delete(String title)
/*     */   {
/* 120 */     String sql = "delete from menu where title=?";
/* 121 */     this.baseDaoSupport.execute(sql, new Object[] { title });
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.eop.resource.impl.MenuManagerImpl
 * JD-Core Version:    0.6.0
 */