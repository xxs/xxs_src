/*    */ package com.enation.app.base.core.service.impl;
/*    */ 
/*    */ import com.enation.app.base.core.model.SiteMenu;
/*    */ import com.enation.app.base.core.service.ISiteMenuManager;
/*    */ import com.enation.eop.sdk.database.BaseSupport;
/*    */ import com.enation.framework.database.IDaoSupport;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import org.apache.log4j.Logger;
/*    */ import org.springframework.transaction.annotation.Propagation;
/*    */ import org.springframework.transaction.annotation.Transactional;
/*    */ 
/*    */ public class SiteMenuManager extends BaseSupport<SiteMenu>
/*    */   implements ISiteMenuManager
/*    */ {
/*    */   public void add(SiteMenu siteMenu)
/*    */   {
/* 17 */     this.baseDaoSupport.insert("site_menu", siteMenu);
/*    */   }
/*    */   @Transactional(propagation=Propagation.REQUIRED)
/*    */   public void delete(Integer id) {
/* 22 */     String sql = "delete from site_menu where parentid =?";
/* 23 */     this.baseDaoSupport.execute(sql, new Object[] { id });
/* 24 */     sql = "delete from  site_menu   where menuid=?";
/* 25 */     this.baseDaoSupport.execute(sql, new Object[] { id });
/*    */   }
/*    */ 
/*    */   public void edit(SiteMenu siteMenu) {
/* 29 */     this.baseDaoSupport.update("site_menu", siteMenu, "menuid=" + siteMenu.getMenuid());
/*    */   }
/*    */ 
/*    */   public List<SiteMenu> list(Integer parentid) {
/* 33 */     String sql = "select * from site_menu order by parentid,sort";
/* 34 */     List menuList = this.baseDaoSupport.queryForList(sql, SiteMenu.class, new Object[0]);
/* 35 */     List topMenuList = new ArrayList();
/* 36 */     if (this.logger.isDebugEnabled()) {
/* 37 */       this.logger.debug("查找" + parentid + "的子...");
/*    */     }
/* 39 */     for (SiteMenu menu : menuList) {
/* 40 */       if (menu.getParentid().compareTo(parentid) == 0) {
/* 41 */         if (this.logger.isDebugEnabled()) {
/* 42 */           this.logger.debug("发现子[" + menu.getName() + "-" + menu.getMenuid() + "]");
/*    */         }
/* 44 */         List children = getChildren(menuList, menu.getMenuid());
/* 45 */         menu.setChildren(children);
/* 46 */         topMenuList.add(menu);
/*    */       }
/*    */     }
/*    */ 
/* 50 */     return topMenuList;
/*    */   }
/*    */ 
/*    */   private List<SiteMenu> getChildren(List<SiteMenu> menuList, Integer parentid) {
/* 54 */     if (this.logger.isDebugEnabled()) {
/* 55 */       this.logger.debug("查找[" + parentid + "]的子");
/*    */     }
/* 57 */     List children = new ArrayList();
/* 58 */     for (SiteMenu menu : menuList) {
/* 59 */       if (menu.getParentid().compareTo(parentid) == 0) {
/* 60 */         if (this.logger.isDebugEnabled()) {
/* 61 */           this.logger.debug(menu.getName() + "-" + menu.getMenuid() + "是子");
/*    */         }
/* 63 */         menu.setChildren(getChildren(menuList, menu.getMenuid()));
/* 64 */         children.add(menu);
/*    */       }
/*    */     }
/* 67 */     return children;
/*    */   }
/*    */ 
/*    */   @Transactional(propagation=Propagation.REQUIRED)
/*    */   public void updateSort(Integer[] menuid, Integer[] sort)
/*    */   {
/* 74 */     if ((menuid == null) || (sort == null)) throw new IllegalArgumentException("menuid or sort is NULL");
/* 75 */     if (menuid.length != sort.length) throw new IllegalArgumentException("menuid or sort length not same");
/* 76 */     int i = 0; for (int len = menuid.length; i < len; i++)
/* 77 */       this.baseDaoSupport.execute("update site_menu set sort=? where menuid=?", new Object[] { sort[i], menuid[i] });
/*    */   }
/*    */ 
/*    */   public SiteMenu get(Integer menuid)
/*    */   {
/* 83 */     return (SiteMenu)this.baseDaoSupport.queryForObject("select * from site_menu where menuid=?", SiteMenu.class, new Object[] { menuid });
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.app.base.core.service.impl.SiteMenuManager
 * JD-Core Version:    0.6.0
 */