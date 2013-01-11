/*    */ package com.enation.framework.plugin;
/*    */ 
/*    */ import java.util.List;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ public abstract class AutoRegisterPlugin
/*    */   implements IPlugin
/*    */ {
/*  8 */   protected final Logger logger = Logger.getLogger(getClass());
/*    */   protected List<IPluginBundle> bundleList;
/* 10 */   private boolean isEnable = false;
/*    */ 
/*    */   public List<IPluginBundle> getBundleList() {
/* 13 */     return this.bundleList;
/*    */   }
/*    */ 
/*    */   public void setBundleList(List<IPluginBundle> bundleList) {
/* 17 */     this.bundleList = bundleList;
/*    */   }
/*    */ 
/*    */   public void disable() {
/* 21 */     this.isEnable = false;
/*    */   }
/*    */ 
/*    */   public void enable() {
/* 25 */     this.isEnable = true;
/*    */   }
/*    */ 
/*    */   public boolean getIsEnable() {
/* 29 */     return this.isEnable;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.framework.plugin.AutoRegisterPlugin
 * JD-Core Version:    0.6.0
 */