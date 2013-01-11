/*     */ package com.enation.app.shop.core.model;
/*     */ 
/*     */ import com.enation.framework.database.NotDbField;
/*     */ import com.enation.framework.database.PrimaryKeyField;
/*     */ import java.util.List;
/*     */ 
/*     */ public class ArticleCat
/*     */ {
/*     */   protected Integer cat_id;
/*     */   protected String name;
/*     */   protected Integer parent_id;
/*     */   protected String cat_path;
/*     */   protected int cat_order;
/*     */   private boolean hasChildren;
/*     */   private List<ArticleCat> children;
/*     */ 
/*     */   @PrimaryKeyField
/*     */   public Integer getCat_id()
/*     */   {
/*  35 */     return this.cat_id;
/*     */   }
/*     */ 
/*     */   public void setCat_id(Integer cat_id)
/*     */   {
/*  40 */     this.cat_id = cat_id;
/*     */   }
/*     */ 
/*     */   public int getCat_order()
/*     */   {
/*  45 */     return this.cat_order;
/*     */   }
/*     */ 
/*     */   public void setCat_order(int cat_order)
/*     */   {
/*  50 */     this.cat_order = cat_order;
/*     */   }
/*     */ 
/*     */   public String getCat_path()
/*     */   {
/*  55 */     return this.cat_path;
/*     */   }
/*     */ 
/*     */   public void setCat_path(String cat_path)
/*     */   {
/*  60 */     this.cat_path = cat_path;
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/*  66 */     return this.name;
/*     */   }
/*     */ 
/*     */   public void setName(String name)
/*     */   {
/*  71 */     this.name = name;
/*     */   }
/*     */ 
/*     */   public Integer getParent_id()
/*     */   {
/*  76 */     return this.parent_id;
/*     */   }
/*     */ 
/*     */   public void setParent_id(Integer parent_id)
/*     */   {
/*  81 */     this.parent_id = parent_id;
/*     */   }
/*     */ 
/*     */   @NotDbField
/*     */   public boolean getHasChildren() {
/*  87 */     this.hasChildren = ((this.children != null) && (!this.children.isEmpty()));
/*  88 */     return this.hasChildren;
/*     */   }
/*     */ 
/*     */   public void setHasChildren(boolean hasChildren)
/*     */   {
/*  93 */     this.hasChildren = hasChildren;
/*     */   }
/*     */ 
/*     */   @NotDbField
/*     */   public List<ArticleCat> getChildren() {
/*  99 */     return this.children;
/*     */   }
/*     */ 
/*     */   public void setChildren(List<ArticleCat> children)
/*     */   {
/* 104 */     this.children = children;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.model.ArticleCat
 * JD-Core Version:    0.6.0
 */