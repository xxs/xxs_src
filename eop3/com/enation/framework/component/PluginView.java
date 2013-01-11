/*    */ package com.enation.framework.component;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ public class PluginView
/*    */ {
/*    */   private String id;
/*    */   private String name;
/*    */   private List<String> bundleList;
/*    */ 
/*    */   public PluginView()
/*    */   {
/* 12 */     this.bundleList = new ArrayList();
/*    */   }
/*    */ 
/*    */   public void addBundle(String beanid) {
/* 16 */     this.bundleList.add(beanid);
/*    */   }
/*    */ 
/*    */   public String getId()
/*    */   {
/* 21 */     return this.id;
/*    */   }
/*    */   public void setId(String id) {
/* 24 */     this.id = id;
/*    */   }
/*    */   public String getName() {
/* 27 */     return this.name;
/*    */   }
/*    */   public void setName(String name) {
/* 30 */     this.name = name;
/*    */   }
/*    */   public List<String> getBundleList() {
/* 33 */     return this.bundleList;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.framework.component.PluginView
 * JD-Core Version:    0.6.0
 */