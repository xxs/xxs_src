/*    */ package com.enation.app.base.core.model;
/*    */ 
/*    */ import com.enation.framework.database.PrimaryKeyField;
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public class FriendsLink
/*    */   implements Serializable
/*    */ {
/*    */   private Integer link_id;
/*    */   private String name;
/*    */   private String url;
/*    */   private String logo;
/*    */   private Integer sort;
/*    */ 
/*    */   @PrimaryKeyField
/*    */   public Integer getLink_id()
/*    */   {
/* 18 */     return this.link_id;
/*    */   }
/*    */ 
/*    */   public void setLink_id(Integer link_id) {
/* 22 */     this.link_id = link_id;
/*    */   }
/*    */ 
/*    */   public String getLogo() {
/* 26 */     return this.logo;
/*    */   }
/*    */ 
/*    */   public void setLogo(String logo) {
/* 30 */     this.logo = logo;
/*    */   }
/*    */ 
/*    */   public String getName() {
/* 34 */     return this.name;
/*    */   }
/*    */ 
/*    */   public void setName(String name) {
/* 38 */     this.name = name;
/*    */   }
/*    */ 
/*    */   public Integer getSort() {
/* 42 */     return this.sort;
/*    */   }
/*    */ 
/*    */   public void setSort(Integer sort) {
/* 46 */     this.sort = sort;
/*    */   }
/*    */ 
/*    */   public String getUrl() {
/* 50 */     return this.url;
/*    */   }
/*    */ 
/*    */   public void setUrl(String url) {
/* 54 */     this.url = url;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.app.base.core.model.FriendsLink
 * JD-Core Version:    0.6.0
 */