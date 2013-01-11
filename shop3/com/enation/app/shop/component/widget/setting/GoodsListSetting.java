/*    */ package com.enation.app.shop.component.widget.setting;
/*    */ 
/*    */ import com.enation.app.shop.core.model.Tag;
/*    */ import com.enation.app.shop.core.service.ITagManager;
/*    */ import com.opensymphony.xwork2.ActionSupport;
/*    */ import java.util.List;
/*    */ 
/*    */ public class GoodsListSetting extends ActionSupport
/*    */ {
/*    */   private ITagManager tagManager;
/*    */   private List<Tag> tagList;
/*    */ 
/*    */   public String execute()
/*    */   {
/* 16 */     this.tagList = this.tagManager.list();
/* 17 */     return "success";
/*    */   }
/*    */   public void setTagManager(ITagManager tagManager) {
/* 20 */     this.tagManager = tagManager;
/*    */   }
/*    */   public List<Tag> getTagList() {
/* 23 */     return this.tagList;
/*    */   }
/*    */   public void setTagList(List<Tag> tagList) {
/* 26 */     this.tagList = tagList;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.widget.setting.GoodsListSetting
 * JD-Core Version:    0.6.0
 */