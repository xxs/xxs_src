/*     */ package com.enation.app.base.core.model;
/*     */ 
/*     */ import com.enation.framework.database.NotDbField;
/*     */ import com.enation.framework.database.PrimaryKeyField;
/*     */ import java.io.Serializable;
/*     */ import java.util.List;
/*     */ 
/*     */ public class SiteMenu
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 6671862044724283307L;
/*     */   private Integer menuid;
/*     */   private Integer parentid;
/*     */   private String name;
/*     */   private String url;
/*     */   private String target;
/*     */   private Integer sort;
/*     */   private List<SiteMenu> children;
/*     */   private boolean hasChildren;
/*     */ 
/*     */   public SiteMenu()
/*     */   {
/*  28 */     this.hasChildren = false;
/*     */   }
/*     */ 
/*     */   @PrimaryKeyField
/*     */   public Integer getMenuid()
/*     */   {
/*  39 */     return this.menuid;
/*     */   }
/*     */ 
/*     */   public void setMenuid(Integer menuid) {
/*  43 */     this.menuid = menuid;
/*     */   }
/*     */ 
/*     */   public Integer getParentid() {
/*  47 */     return this.parentid;
/*     */   }
/*     */ 
/*     */   public void setParentid(Integer parentid) {
/*  51 */     this.parentid = parentid;
/*     */   }
/*     */ 
/*     */   public String getName() {
/*  55 */     return this.name;
/*     */   }
/*     */ 
/*     */   public void setName(String name) {
/*  59 */     this.name = name;
/*     */   }
/*     */ 
/*     */   public String getUrl() {
/*  63 */     return this.url;
/*     */   }
/*     */ 
/*     */   public void setUrl(String url) {
/*  67 */     this.url = url;
/*     */   }
/*     */ 
/*     */   public String getTarget() {
/*  71 */     return this.target;
/*     */   }
/*     */ 
/*     */   public void setTarget(String target) {
/*  75 */     this.target = target;
/*     */   }
/*     */   @NotDbField
/*     */   public List<SiteMenu> getChildren() {
/*  80 */     return this.children;
/*     */   }
/*     */ 
/*     */   public void setChildren(List<SiteMenu> children) {
/*  84 */     this.children = children;
/*     */   }
/*     */ 
/*     */   public Integer getSort() {
/*  88 */     return this.sort;
/*     */   }
/*     */ 
/*     */   public void setSort(Integer sort) {
/*  92 */     this.sort = sort;
/*     */   }
/*     */   @NotDbField
/*     */   public boolean getHasChildren() {
/*  97 */     this.hasChildren = ((this.children != null) && (!this.children.isEmpty()));
/*     */ 
/*  99 */     return this.hasChildren;
/*     */   }
/*     */ 
/*     */   public void setHasChildren(boolean hasChildren) {
/* 103 */     this.hasChildren = hasChildren;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.app.base.core.model.SiteMenu
 * JD-Core Version:    0.6.0
 */