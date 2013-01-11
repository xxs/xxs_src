/*     */ package com.enation.app.shop.core.action.backend;
/*     */ 
/*     */ import com.enation.app.shop.core.service.IGoodsStoreManager;
/*     */ import com.enation.framework.action.WWAction;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class GoodsStoreAction extends WWAction
/*     */ {
/*     */   private IGoodsStoreManager goodsStoreManager;
/*     */   private int goodsid;
/*     */   private String html;
/*     */ 
/*     */   public String getStoreDialogHtml()
/*     */   {
/*  22 */     this.html = this.goodsStoreManager.getStoreHtml(Integer.valueOf(this.goodsid));
/*  23 */     return "dialog_html";
/*     */   }
/*     */ 
/*     */   public String getStockDialogHtml()
/*     */   {
/*  30 */     this.html = this.goodsStoreManager.getStockHtml(Integer.valueOf(this.goodsid));
/*  31 */     return "dialog_html";
/*     */   }
/*     */ 
/*     */   public String getShipDialogHtml()
/*     */   {
/*  40 */     this.html = this.goodsStoreManager.getShipHtml(Integer.valueOf(this.goodsid));
/*  41 */     return "dialog_html";
/*     */   }
/*     */ 
/*     */   public String saveStore()
/*     */   {
/*     */     try
/*     */     {
/*  53 */       this.goodsStoreManager.saveStore(this.goodsid);
/*  54 */       showSuccessJson("保存商品库存成功");
/*     */     } catch (RuntimeException e) {
/*  56 */       this.logger.error("保存商品库存出错", e);
/*  57 */       showErrorJson(e.getMessage());
/*     */     }
/*     */ 
/*  61 */     return "json_message";
/*     */   }
/*     */ 
/*     */   public String saveStock()
/*     */   {
/*     */     try
/*     */     {
/*  71 */       this.goodsStoreManager.saveStock(this.goodsid);
/*  72 */       showSuccessJson("保存进货成功");
/*     */     } catch (RuntimeException e) {
/*  74 */       this.logger.error("保存进货出错", e);
/*  75 */       showErrorJson(e.getMessage());
/*     */     }
/*  77 */     return "json_message";
/*     */   }
/*     */ 
/*     */   public String getWarnDialogHtml()
/*     */   {
/*  84 */     this.html = this.goodsStoreManager.getWarnHtml(Integer.valueOf(this.goodsid));
/*  85 */     return "dialog_html";
/*     */   }
/*     */ 
/*     */   public String saveWarn()
/*     */   {
/*     */     try
/*     */     {
/*  93 */       this.goodsStoreManager.saveWarn(this.goodsid);
/*  94 */       showSuccessJson("保存报警成功");
/*     */     } catch (RuntimeException e) {
/*  96 */       this.logger.error("保存报警出错", e);
/*  97 */       showErrorJson(e.getMessage());
/*     */     }
/*  99 */     return "json_message";
/*     */   }
/*     */ 
/*     */   public String saveShip()
/*     */   {
/*     */     try
/*     */     {
/* 108 */       this.goodsStoreManager.saveShip(this.goodsid);
/* 109 */       showSuccessJson("保存出货成功");
/*     */     } catch (RuntimeException e) {
/* 111 */       this.logger.error("保存出货出错", e);
/* 112 */       showErrorJson(e.getMessage());
/*     */     }
/* 114 */     return "json_message";
/*     */   }
/*     */ 
/*     */   public String saveCmpl()
/*     */   {
/*     */     try {
/* 120 */       this.goodsStoreManager.saveCmpl(this.goodsid);
/* 121 */       showSuccessJson("更新状态成功");
/*     */     } catch (RuntimeException e) {
/* 123 */       this.logger.error("保更新状态出错", e);
/* 124 */       showErrorJson(e.getMessage());
/*     */     }
/* 126 */     return "json_message";
/*     */   }
/*     */ 
/*     */   public IGoodsStoreManager getGoodsStoreManager() {
/* 130 */     return this.goodsStoreManager;
/*     */   }
/*     */   public void setGoodsStoreManager(IGoodsStoreManager goodsStoreManager) {
/* 133 */     this.goodsStoreManager = goodsStoreManager;
/*     */   }
/*     */   public int getGoodsid() {
/* 136 */     return this.goodsid;
/*     */   }
/*     */   public void setGoodsid(int goodsid) {
/* 139 */     this.goodsid = goodsid;
/*     */   }
/*     */ 
/*     */   public String getHtml()
/*     */   {
/* 145 */     return this.html;
/*     */   }
/*     */ 
/*     */   public void setHtml(String html)
/*     */   {
/* 151 */     this.html = html;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.action.backend.GoodsStoreAction
 * JD-Core Version:    0.6.0
 */