/*     */ package com.enation.eop.resource.model;
/*     */ 
/*     */ import com.enation.framework.database.NotDbField;
/*     */ import java.util.List;
/*     */ 
/*     */ public class Menu extends Resource
/*     */ {
/*     */   private Integer pid;
/*     */   private String title;
/*     */   private String url;
/*     */   private String target;
/*     */   private Integer sorder;
/*     */   private Integer menutype;
/*     */   private String datatype;
/*     */   private String appid;
/*     */   private List<Menu> children;
/*     */   private boolean hasChildren;
/*     */   public static final int MENU_TYPE_SYS = 1;
/*     */   public static final int MENU_TYPE_APP = 2;
/*     */   public static final int MENU_TYPE_EXT = 3;
/*     */   private Integer selected;
/*     */ 
/*     */   @NotDbField
/*     */   public boolean getHasChildren()
/*     */   {
/*  33 */     this.hasChildren = ((this.children != null) && (!this.children.isEmpty()));
/*  34 */     return this.hasChildren;
/*     */   }
/*     */ 
/*     */   public String getDatatype()
/*     */   {
/*  43 */     return this.datatype;
/*     */   }
/*     */   public void setDatatype(String datatype) {
/*  46 */     this.datatype = datatype;
/*     */   }
/*     */ 
/*     */   public Integer getSelected()
/*     */   {
/*  51 */     return this.selected;
/*     */   }
/*     */   public void setSelected(Integer selected) {
/*  54 */     this.selected = selected;
/*     */   }
/*     */   public Integer getPid() {
/*  57 */     return this.pid;
/*     */   }
/*     */   public void setPid(Integer pid) {
/*  60 */     this.pid = pid;
/*     */   }
/*     */   public String getTitle() {
/*  63 */     return this.title;
/*     */   }
/*     */   public void setTitle(String title) {
/*  66 */     this.title = title;
/*     */   }
/*     */   public String getUrl() {
/*  69 */     return this.url;
/*     */   }
/*     */   public void setUrl(String url) {
/*  72 */     this.url = url;
/*     */   }
/*     */   public String getTarget() {
/*  75 */     return this.target;
/*     */   }
/*     */   public void setTarget(String target) {
/*  78 */     this.target = target;
/*     */   }
/*     */   public Integer getSorder() {
/*  81 */     return this.sorder;
/*     */   }
/*     */   public void setSorder(Integer sorder) {
/*  84 */     this.sorder = sorder;
/*     */   }
/*     */   public Integer getMenutype() {
/*  87 */     return this.menutype;
/*     */   }
/*     */   public void setMenutype(Integer menutype) {
/*  90 */     this.menutype = menutype;
/*     */   }
/*     */   public List<Menu> getChildren() {
/*  93 */     return this.children;
/*     */   }
/*     */   public void setChildren(List<Menu> children) {
/*  96 */     this.children = children;
/*     */   }
/*     */   public String getAppid() {
/*  99 */     return this.appid;
/*     */   }
/*     */   public void setAppid(String appid) {
/* 102 */     this.appid = appid;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.eop.resource.model.Menu
 * JD-Core Version:    0.6.0
 */