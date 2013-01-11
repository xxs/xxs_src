/*     */ package com.enation.app.shop.core.action.backend;
/*     */ 
/*     */ import com.enation.app.shop.core.model.FreeOfferCategory;
/*     */ import com.enation.app.shop.core.service.IFreeOfferCategoryManager;
/*     */ import com.enation.framework.action.WWAction;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class FreeOfferCategoryAction extends WWAction
/*     */ {
/*     */   private String name;
/*     */   private String order;
/*     */   private FreeOfferCategory freeOfferCategory;
/*     */   private Integer cat_id;
/*     */   private String id;
/*     */   private IFreeOfferCategoryManager freeOfferCategoryManager;
/*     */ 
/*     */   public String list()
/*     */     throws Exception
/*     */   {
/*  26 */     this.webpage = this.freeOfferCategoryManager.searchFreeOfferCategory(this.name, this.order, getPage(), getPageSize());
/*     */ 
/*  28 */     return "list";
/*     */   }
/*     */ 
/*     */   public String trash_list()
/*     */   {
/*  33 */     this.webpage = this.freeOfferCategoryManager.pageTrash(this.name, this.order, getPage(), getPageSize());
/*     */ 
/*  35 */     return "trash_list";
/*     */   }
/*     */ 
/*     */   public String add()
/*     */   {
/*  40 */     return "add";
/*     */   }
/*     */ 
/*     */   public String edit() {
/*  44 */     this.freeOfferCategory = this.freeOfferCategoryManager.get(this.cat_id.intValue());
/*  45 */     return "edit";
/*     */   }
/*     */ 
/*     */   public String save()
/*     */   {
/*  50 */     this.freeOfferCategory.setDisabled(Integer.valueOf(0));
/*  51 */     this.freeOfferCategoryManager.saveAdd(this.freeOfferCategory);
/*  52 */     this.msgs.add("赠品分类添加成功");
/*  53 */     this.urls.put("赠品分类列表", "freeOfferCategory!list.do");
/*  54 */     return "message";
/*     */   }
/*     */ 
/*     */   public String saveEdit()
/*     */   {
/*  60 */     this.freeOfferCategoryManager.update(this.freeOfferCategory);
/*  61 */     this.msgs.add("赠品分类修改成功");
/*  62 */     this.urls.put("赠品分类列表", "freeOfferCategory!list.do");
/*  63 */     return "message";
/*     */   }
/*     */ 
/*     */   public String delete()
/*     */   {
/*     */     try
/*     */     {
/*  73 */       this.freeOfferCategoryManager.delete(this.id);
/*  74 */       this.json = "{'result':0,'message':'删除成功'}";
/*     */     } catch (RuntimeException e) {
/*  76 */       this.json = "{'result':1,'message':'删除失败'}";
/*     */     }
/*  78 */     return "json_message";
/*     */   }
/*     */ 
/*     */   public String revert()
/*     */   {
/*     */     try
/*     */     {
/*  88 */       this.freeOfferCategoryManager.revert(this.id);
/*  89 */       this.json = "{'result':0,'message':'还原成功'}";
/*     */     } catch (RuntimeException e) {
/*  91 */       this.json = "{'result':1,'message':'还原失败'}";
/*     */     }
/*  93 */     return "json_message";
/*     */   }
/*     */ 
/*     */   public String clean()
/*     */   {
/*     */     try
/*     */     {
/* 103 */       this.freeOfferCategoryManager.clean(this.id);
/* 104 */       this.json = "{'result':0,'message':'清除成功'}";
/*     */     } catch (RuntimeException e) {
/* 106 */       this.json = "{'result':1,'message':'清除失败'}";
/*     */     }
/* 108 */     return "json_message";
/*     */   }
/*     */ 
/*     */   public String getName() {
/* 112 */     return this.name;
/*     */   }
/*     */ 
/*     */   public void setName(String name) {
/* 116 */     this.name = name;
/*     */   }
/*     */ 
/*     */   public String getOrder() {
/* 120 */     return this.order;
/*     */   }
/*     */ 
/*     */   public void setOrder(String order) {
/* 124 */     this.order = order;
/*     */   }
/*     */ 
/*     */   public String getId() {
/* 128 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(String id) {
/* 132 */     this.id = id;
/*     */   }
/*     */ 
/*     */   public FreeOfferCategory getFreeOfferCategory() {
/* 136 */     return this.freeOfferCategory;
/*     */   }
/*     */ 
/*     */   public void setFreeOfferCategory(FreeOfferCategory freeOfferCategory) {
/* 140 */     this.freeOfferCategory = freeOfferCategory;
/*     */   }
/*     */ 
/*     */   public Integer getCat_id() {
/* 144 */     return this.cat_id;
/*     */   }
/*     */ 
/*     */   public void setCat_id(Integer catId) {
/* 148 */     this.cat_id = catId;
/*     */   }
/*     */ 
/*     */   public IFreeOfferCategoryManager getFreeOfferCategoryManager() {
/* 152 */     return this.freeOfferCategoryManager;
/*     */   }
/*     */ 
/*     */   public void setFreeOfferCategoryManager(IFreeOfferCategoryManager freeOfferCategoryManager)
/*     */   {
/* 157 */     this.freeOfferCategoryManager = freeOfferCategoryManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.action.backend.FreeOfferCategoryAction
 * JD-Core Version:    0.6.0
 */