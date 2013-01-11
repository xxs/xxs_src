/*    */ package com.enation.app.shop.core.action.backend;
/*    */ 
/*    */ import com.enation.app.shop.core.service.IGoodsAlbumManager;
/*    */ import com.enation.framework.action.WWAction;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ public class GalleryAction extends WWAction
/*    */ {
/*    */   private IGoodsAlbumManager goodsAlbumManager;
/*    */   private int total;
/*    */   private int start;
/*    */   private int end;
/*    */ 
/*    */   public int getStart()
/*    */   {
/* 18 */     return this.start;
/*    */   }
/*    */ 
/*    */   public void setStart(int start) {
/* 22 */     this.start = start;
/*    */   }
/*    */ 
/*    */   public int getEnd() {
/* 26 */     return this.end;
/*    */   }
/*    */ 
/*    */   public void setEnd(int end) {
/* 30 */     this.end = end;
/*    */   }
/*    */ 
/*    */   public int getTotal() {
/* 34 */     return this.total;
/*    */   }
/*    */ 
/*    */   public void setTotal(int total) {
/* 38 */     this.total = total;
/*    */   }
/*    */ 
/*    */   public String recreate() {
/*    */     try {
/* 43 */       this.goodsAlbumManager.recreate(this.start, this.end);
/* 44 */       showSuccessJson("生成商品相册缩略图成功 ");
/*    */     } catch (RuntimeException e) {
/* 46 */       this.logger.error("生成商品相册缩略图错误", e);
/* 47 */       showErrorJson("生成商品相册缩略图错误" + e.getMessage());
/*    */     }
/* 49 */     return "json_message";
/*    */   }
/*    */ 
/*    */   public String execute() {
/* 53 */     this.total = this.goodsAlbumManager.getTotal();
/* 54 */     return "input";
/*    */   }
/*    */ 
/*    */   public IGoodsAlbumManager getGoodsAlbumManager() {
/* 58 */     return this.goodsAlbumManager;
/*    */   }
/*    */   public void setGoodsAlbumManager(IGoodsAlbumManager goodsAlbumManager) {
/* 61 */     this.goodsAlbumManager = goodsAlbumManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.action.backend.GalleryAction
 * JD-Core Version:    0.6.0
 */