/*     */ package com.enation.app.shop.core.model;
/*     */ 
/*     */ import com.enation.framework.database.NotDbField;
/*     */ import com.enation.framework.database.PrimaryKeyField;
/*     */ import java.io.Serializable;
/*     */ import java.util.List;
/*     */ 
/*     */ public class Cat
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 5115916366849703086L;
/*     */   protected Integer cat_id;
/*     */   protected String name;
/*     */   protected Integer parent_id;
/*     */   protected String cat_path;
/*     */   protected Integer goods_count;
/*     */   protected int cat_order;
/*     */   protected Integer type_id;
/*     */   protected String list_show;
/*     */   protected String image;
/*     */   private boolean hasChildren;
/*     */   private List<Cat> children;
/*     */   private String type_name;
/*     */ 
/*     */   public Cat()
/*     */   {
/*  43 */     this.hasChildren = false;
/*     */   }
/*     */ 
/*     */   @PrimaryKeyField
/*     */   public Integer getCat_id() {
/*  49 */     return this.cat_id;
/*     */   }
/*     */ 
/*     */   public void setCat_id(Integer cat_id)
/*     */   {
/*  54 */     this.cat_id = cat_id;
/*     */   }
/*     */ 
/*     */   public int getCat_order()
/*     */   {
/*  59 */     return this.cat_order;
/*     */   }
/*     */ 
/*     */   public void setCat_order(int cat_order)
/*     */   {
/*  64 */     this.cat_order = cat_order;
/*     */   }
/*     */ 
/*     */   public String getCat_path()
/*     */   {
/*  69 */     return this.cat_path;
/*     */   }
/*     */ 
/*     */   public void setCat_path(String cat_path)
/*     */   {
/*  74 */     this.cat_path = cat_path;
/*     */   }
/*     */ 
/*     */   public Integer getGoods_count()
/*     */   {
/*  84 */     return this.goods_count;
/*     */   }
/*     */ 
/*     */   public void setGoods_count(Integer goods_count)
/*     */   {
/*  89 */     this.goods_count = goods_count;
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/*  97 */     return this.name;
/*     */   }
/*     */ 
/*     */   public void setName(String name)
/*     */   {
/* 102 */     this.name = name;
/*     */   }
/*     */ 
/*     */   public Integer getParent_id()
/*     */   {
/* 107 */     return this.parent_id;
/*     */   }
/*     */ 
/*     */   public void setParent_id(Integer parent_id)
/*     */   {
/* 112 */     this.parent_id = parent_id;
/*     */   }
/*     */ 
/*     */   public Integer getType_id()
/*     */   {
/* 118 */     return this.type_id;
/*     */   }
/*     */ 
/*     */   public void setType_id(Integer type_id)
/*     */   {
/* 123 */     this.type_id = type_id;
/*     */   }
/*     */ 
/*     */   public String getList_show()
/*     */   {
/* 128 */     return this.list_show;
/*     */   }
/*     */ 
/*     */   public void setList_show(String listShow)
/*     */   {
/* 133 */     this.list_show = listShow;
/*     */   }
/*     */   @NotDbField
/*     */   public List<Cat> getChildren() {
/* 138 */     return this.children;
/*     */   }
/*     */ 
/*     */   public void setChildren(List<Cat> children)
/*     */   {
/* 143 */     this.children = children;
/*     */   }
/*     */ 
/*     */   @NotDbField
/*     */   public String getType_name()
/*     */   {
/* 150 */     return this.type_name;
/*     */   }
/*     */ 
/*     */   public void setType_name(String typeName)
/*     */   {
/* 155 */     this.type_name = typeName;
/*     */   }
/*     */   @NotDbField
/*     */   public boolean getHasChildren() {
/* 160 */     this.hasChildren = ((this.children != null) && (!this.children.isEmpty()));
/* 161 */     return this.hasChildren;
/*     */   }
/*     */ 
/*     */   public void setHasChildren(boolean hasChildren)
/*     */   {
/* 166 */     this.hasChildren = hasChildren;
/*     */   }
/*     */ 
/*     */   public String getImage()
/*     */   {
/* 171 */     return this.image;
/*     */   }
/*     */ 
/*     */   public void setImage(String image)
/*     */   {
/* 176 */     this.image = image;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.model.Cat
 * JD-Core Version:    0.6.0
 */