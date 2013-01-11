/*    */ package com.enation.app.shop.component.receipt;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public class Receipt
/*    */   implements Serializable
/*    */ {
/*    */   private Integer id;
/*    */   private Integer order_id;
/*    */   private String title;
/*    */   private Long add_time;
/*    */   private String content;
/*    */   private int status;
/*    */ 
/*    */   public Integer getId()
/*    */   {
/* 18 */     return this.id;
/*    */   }
/*    */   public void setId(Integer id) {
/* 21 */     this.id = id;
/*    */   }
/*    */   public Integer getOrder_id() {
/* 24 */     return this.order_id;
/*    */   }
/*    */   public void setOrder_id(Integer order_id) {
/* 27 */     this.order_id = order_id;
/*    */   }
/*    */   public String getTitle() {
/* 30 */     return this.title;
/*    */   }
/*    */   public void setTitle(String title) {
/* 33 */     this.title = title;
/*    */   }
/*    */   public Long getAdd_time() {
/* 36 */     return this.add_time;
/*    */   }
/*    */   public void setAdd_time(Long add_time) {
/* 39 */     this.add_time = add_time;
/*    */   }
/*    */   public String getContent() {
/* 42 */     return this.content;
/*    */   }
/*    */   public void setContent(String content) {
/* 45 */     this.content = content;
/*    */   }
/*    */   public int getStatus() {
/* 48 */     return this.status;
/*    */   }
/*    */   public void setStatus(int status) {
/* 51 */     this.status = status;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.receipt.Receipt
 * JD-Core Version:    0.6.0
 */