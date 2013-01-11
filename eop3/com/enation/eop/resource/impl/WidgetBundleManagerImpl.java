/*    */ package com.enation.eop.resource.impl;
/*    */ 
/*    */ import com.enation.eop.resource.IWidgetBundleManager;
/*    */ import com.enation.eop.resource.model.WidgetBundle;
/*    */ import com.enation.eop.sdk.database.BaseSupport;
/*    */ import com.enation.framework.database.IDaoSupport;
/*    */ import java.util.List;
/*    */ 
/*    */ public class WidgetBundleManagerImpl extends BaseSupport<WidgetBundle>
/*    */   implements IWidgetBundleManager
/*    */ {
/*    */   public WidgetBundle getWidgetBundle(String widgetType)
/*    */   {
/* 14 */     String sql = "select * from widgetbundle where widgettype=?";
/* 15 */     return (WidgetBundle)this.baseDaoSupport.queryForObject(sql, WidgetBundle.class, new Object[] { widgetType });
/*    */   }
/*    */ 
/*    */   public List<WidgetBundle> getWidgetbundleList()
/*    */   {
/* 20 */     String sql = "select * from widgetbundle";
/* 21 */     return this.baseDaoSupport.queryForList(sql, WidgetBundle.class, new Object[0]);
/*    */   }
/*    */ 
/*    */   public void add(WidgetBundle widgetBundle)
/*    */   {
/* 26 */     this.baseDaoSupport.insert("widgetbundle", widgetBundle);
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.eop.resource.impl.WidgetBundleManagerImpl
 * JD-Core Version:    0.6.0
 */