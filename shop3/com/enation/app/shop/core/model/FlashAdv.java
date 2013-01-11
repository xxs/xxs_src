/*    */ package com.enation.app.shop.core.model;
/*    */ 
/*    */ import com.enation.framework.database.PrimaryKeyField;
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public class FlashAdv
/*    */   implements Serializable
/*    */ {
/*    */   private Integer id;
/*    */   private String remark;
/*    */   private String url;
/*    */   private String pic;
/*    */   private Integer sort;
/*    */ 
/*    */   @PrimaryKeyField
/*    */   public Integer getId()
/*    */   {
/* 15 */     return this.id;
/*    */   }
/*    */   public void setId(Integer id) {
/* 18 */     this.id = id;
/*    */   }
/*    */   public String getPic() {
/* 21 */     return this.pic;
/*    */   }
/*    */   public void setPic(String pic) {
/* 24 */     this.pic = pic;
/*    */   }
/*    */   public String getRemark() {
/* 27 */     return this.remark;
/*    */   }
/*    */   public void setRemark(String remark) {
/* 30 */     this.remark = remark;
/*    */   }
/*    */   public Integer getSort() {
/* 33 */     return this.sort;
/*    */   }
/*    */   public void setSort(Integer sort) {
/* 36 */     this.sort = sort;
/*    */   }
/*    */   public String getUrl() {
/* 39 */     return this.url;
/*    */   }
/*    */   public void setUrl(String url) {
/* 42 */     this.url = url;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.model.FlashAdv
 * JD-Core Version:    0.6.0
 */