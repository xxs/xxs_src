/*    */ package com.enation.app.shop.core.model.support;
/*    */ 
/*    */ import com.enation.app.shop.core.model.Comments;
/*    */ import java.util.List;
/*    */ 
/*    */ public class CommentDTO
/*    */ {
/*    */   private Comments comments;
/*    */   private List<Comments> list;
/*    */ 
/*    */   public Comments getComments()
/*    */   {
/* 20 */     return this.comments;
/*    */   }
/*    */ 
/*    */   public void setComments(Comments comments) {
/* 24 */     this.comments = comments;
/*    */   }
/*    */ 
/*    */   public List<Comments> getList() {
/* 28 */     return this.list;
/*    */   }
/*    */ 
/*    */   public void setList(List<Comments> list) {
/* 32 */     this.list = list;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.model.support.CommentDTO
 * JD-Core Version:    0.6.0
 */