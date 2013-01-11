/*    */ package com.enation.eop.resource.impl;
/*    */ 
/*    */ import com.enation.eop.resource.IThemeUriManager;
/*    */ import com.enation.eop.resource.model.ThemeUri;
/*    */ import com.enation.eop.sdk.database.BaseSupport;
/*    */ import com.enation.framework.database.IDaoSupport;
/*    */ import java.util.List;
/*    */ import org.springframework.transaction.annotation.Propagation;
/*    */ import org.springframework.transaction.annotation.Transactional;
/*    */ 
/*    */ public class ThemeUriManagerImpl extends BaseSupport<ThemeUri>
/*    */   implements IThemeUriManager
/*    */ {
/*    */   public void clean()
/*    */   {
/* 17 */     this.baseDaoSupport.execute("truncate table themeuri", new Object[0]);
/*    */   }
/*    */ 
/*    */   public ThemeUri get(Integer id) {
/* 21 */     return (ThemeUri)this.baseDaoSupport.queryForObject("select * from themeuri where id=?", ThemeUri.class, new Object[] { id });
/*    */   }
/*    */   @Transactional(propagation=Propagation.REQUIRED)
/*    */   public void edit(List<ThemeUri> uriList) {
/* 26 */     for (ThemeUri uri : uriList)
/* 27 */       this.baseDaoSupport.update("themeuri", uri, "id=" + uri.getId());
/*    */   }
/*    */ 
/*    */   public List<ThemeUri> list()
/*    */   {
/* 32 */     String sql = "select * from themeuri";
/* 33 */     return this.baseDaoSupport.queryForList(sql, ThemeUri.class, new Object[0]);
/*    */   }
/*    */ 
/*    */   public ThemeUri getPath(String uri)
/*    */   {
/* 38 */     List list = list();
/*    */ 
/* 40 */     for (ThemeUri themeUri : list) {
/* 41 */       if (themeUri.getUri().equals(uri)) {
/* 42 */         return themeUri;
/*    */       }
/*    */     }
/* 45 */     return null;
/*    */   }
/*    */ 
/*    */   public void add(ThemeUri uri)
/*    */   {
/* 50 */     this.baseDaoSupport.insert("themeuri", uri);
/*    */   }
/*    */ 
/*    */   public void delete(int id)
/*    */   {
/* 55 */     this.baseDaoSupport.execute("delete from themeuri where id=? ", new Object[] { Integer.valueOf(id) });
/*    */   }
/*    */ 
/*    */   public void edit(ThemeUri themeUri) {
/* 59 */     this.baseDaoSupport.update("themeuri", themeUri, "id=" + themeUri.getId());
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.eop.resource.impl.ThemeUriManagerImpl
 * JD-Core Version:    0.6.0
 */