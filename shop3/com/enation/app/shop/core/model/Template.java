/*     */ package com.enation.app.shop.core.model;
/*     */ 
/*     */ import com.enation.framework.database.PrimaryKeyField;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class Template
/*     */   implements Serializable
/*     */ {
/*     */   private Integer template_id;
/*     */   private String name;
/*     */   private String theme;
/*     */   private String author;
/*     */   private String site;
/*     */   private Long create_time;
/*     */   private Integer is_active;
/*     */   private String version;
/*     */ 
/*     */   public String getAuthor()
/*     */   {
/*  34 */     return this.author;
/*     */   }
/*     */ 
/*     */   public void setAuthor(String author)
/*     */   {
/*  39 */     this.author = author;
/*     */   }
/*     */ 
/*     */   public Long getCreate_time()
/*     */   {
/*  44 */     return this.create_time;
/*     */   }
/*     */ 
/*     */   public void setCreate_time(Long create_time)
/*     */   {
/*  49 */     this.create_time = create_time;
/*     */   }
/*     */ 
/*     */   public Integer getIs_active()
/*     */   {
/*  54 */     return this.is_active;
/*     */   }
/*     */ 
/*     */   public void setIs_active(Integer is_active)
/*     */   {
/*  59 */     this.is_active = is_active;
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/*  64 */     return this.name;
/*     */   }
/*     */ 
/*     */   public void setName(String name)
/*     */   {
/*  69 */     this.name = name;
/*     */   }
/*     */ 
/*     */   public String getSite()
/*     */   {
/*  74 */     return this.site;
/*     */   }
/*     */ 
/*     */   public void setSite(String site)
/*     */   {
/*  79 */     this.site = site;
/*     */   }
/*     */ 
/*     */   @PrimaryKeyField
/*     */   public Integer getTemplate_id() {
/*  85 */     return this.template_id;
/*     */   }
/*     */ 
/*     */   public void setTemplate_id(Integer template_id)
/*     */   {
/*  90 */     this.template_id = template_id;
/*     */   }
/*     */ 
/*     */   public String getTheme()
/*     */   {
/*  95 */     return this.theme;
/*     */   }
/*     */ 
/*     */   public void setTheme(String theme)
/*     */   {
/* 100 */     this.theme = theme;
/*     */   }
/*     */ 
/*     */   public String getVersion()
/*     */   {
/* 105 */     return this.version;
/*     */   }
/*     */ 
/*     */   public void setVersion(String version)
/*     */   {
/* 110 */     this.version = version;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.model.Template
 * JD-Core Version:    0.6.0
 */