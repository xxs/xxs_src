/*    */ package com.enation.app.shop.component.goodscore.widget.goods.list;
/*    */ 
/*    */ import java.util.List;
/*    */ 
/*    */ public class Tab
/*    */ {
/*    */   private String id;
/*    */   private String title;
/*    */   private Term term;
/*    */   private List goodsList;
/*    */   private boolean selected;
/*    */ 
/*    */   public String getTitle()
/*    */   {
/* 13 */     return this.title;
/*    */   }
/*    */   public void setTitle(String title) {
/* 16 */     this.title = title;
/*    */   }
/*    */   public Term getTerm() {
/* 19 */     return this.term;
/*    */   }
/*    */   public void setTerm(Term term) {
/* 22 */     this.term = term;
/*    */   }
/*    */   public List getGoodsList() {
/* 25 */     return this.goodsList;
/*    */   }
/*    */   public void setGoodsList(List goodsList) {
/* 28 */     this.goodsList = goodsList;
/*    */   }
/*    */   public boolean isSelected() {
/* 31 */     return this.selected;
/*    */   }
/*    */   public void setSelected(boolean selected) {
/* 34 */     this.selected = selected;
/*    */   }
/*    */   public String getId() {
/* 37 */     return this.id;
/*    */   }
/*    */   public void setId(String id) {
/* 40 */     this.id = id;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.goodscore.widget.goods.list.Tab
 * JD-Core Version:    0.6.0
 */