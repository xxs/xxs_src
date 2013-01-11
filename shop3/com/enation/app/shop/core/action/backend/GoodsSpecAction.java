/*    */ package com.enation.app.shop.core.action.backend;
/*    */ 
/*    */ import com.enation.app.shop.component.spec.service.ISpecManager;
/*    */ import com.enation.app.shop.component.spec.service.ISpecValueManager;
/*    */ import com.enation.framework.action.WWAction;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class GoodsSpecAction extends WWAction
/*    */ {
/*    */   private ISpecManager specManager;
/*    */   private ISpecValueManager specValueManager;
/*    */   private List specList;
/*    */   private Integer spec_id;
/*    */   private Integer value_id;
/*    */   private Map spec;
/*    */   private List valueList;
/*    */ 
/*    */   public String execute()
/*    */   {
/* 25 */     this.specList = this.specManager.list();
/* 26 */     return "select";
/*    */   }
/*    */ 
/*    */   public String getValues() {
/* 30 */     this.spec = this.specManager.get(this.spec_id);
/* 31 */     this.valueList = this.specValueManager.list(this.spec_id);
/* 32 */     return "values";
/*    */   }
/*    */ 
/*    */   public String addOne() {
/* 36 */     this.spec = this.specValueManager.get(this.value_id);
/* 37 */     return "add_one";
/*    */   }
/*    */ 
/*    */   public String addAll()
/*    */   {
/* 42 */     return "add_all";
/*    */   }
/*    */ 
/*    */   public ISpecManager getSpecManager() {
/* 46 */     return this.specManager;
/*    */   }
/*    */ 
/*    */   public void setSpecManager(ISpecManager specManager) {
/* 50 */     this.specManager = specManager;
/*    */   }
/*    */ 
/*    */   public List getSpecList() {
/* 54 */     return this.specList;
/*    */   }
/*    */ 
/*    */   public void setSpecList(List specList) {
/* 58 */     this.specList = specList;
/*    */   }
/*    */ 
/*    */   public ISpecValueManager getSpecValueManager() {
/* 62 */     return this.specValueManager;
/*    */   }
/*    */ 
/*    */   public void setSpecValueManager(ISpecValueManager specValueManager) {
/* 66 */     this.specValueManager = specValueManager;
/*    */   }
/*    */ 
/*    */   public Map getSpec() {
/* 70 */     return this.spec;
/*    */   }
/*    */ 
/*    */   public void setSpec(Map spec) {
/* 74 */     this.spec = spec;
/*    */   }
/*    */ 
/*    */   public List getValueList() {
/* 78 */     return this.valueList;
/*    */   }
/*    */ 
/*    */   public void setValueList(List valueList) {
/* 82 */     this.valueList = valueList;
/*    */   }
/*    */ 
/*    */   public Integer getSpec_id() {
/* 86 */     return this.spec_id;
/*    */   }
/*    */ 
/*    */   public void setSpec_id(Integer specId) {
/* 90 */     this.spec_id = specId;
/*    */   }
/*    */ 
/*    */   public Integer getValue_id() {
/* 94 */     return this.value_id;
/*    */   }
/*    */ 
/*    */   public void setValue_id(Integer valueId) {
/* 98 */     this.value_id = valueId;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.action.backend.GoodsSpecAction
 * JD-Core Version:    0.6.0
 */