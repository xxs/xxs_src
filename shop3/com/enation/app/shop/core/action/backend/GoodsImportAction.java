/*     */ package com.enation.app.shop.core.action.backend;
/*     */ 
/*     */ import com.enation.app.shop.core.service.IGoodsCatManager;
/*     */ import com.enation.app.shop.core.service.batchimport.IGoodsDataBatchManager;
/*     */ import com.enation.framework.action.WWAction;
/*     */ import java.util.List;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class GoodsImportAction extends WWAction
/*     */ {
/*     */   private IGoodsDataBatchManager goodsDataBatchManager;
/*     */   private IGoodsCatManager goodsCatManager;
/*     */   private String path;
/*     */   private List catList;
/*     */   private int imptype;
/*     */   private int catid;
/*     */   private Integer startNum;
/*     */   private Integer endNum;
/*     */ 
/*     */   public String execute()
/*     */   {
/*  24 */     this.catList = this.goodsCatManager.listAllChildren(Integer.valueOf(0));
/*  25 */     return "input";
/*     */   }
/*     */ 
/*     */   public String imported() {
/*     */     try {
/*  30 */       this.logger.debug("startNum[" + this.startNum + "]endNum[" + this.endNum + "]");
/*  31 */       this.goodsDataBatchManager.batchImport(this.path, this.imptype, this.catid, this.startNum, this.endNum);
/*  32 */       this.json = "{result:1}";
/*     */     }
/*     */     catch (RuntimeException e) {
/*  35 */       this.logger.error("商品导入", e);
/*  36 */       this.json = ("{result:0,message:'" + e.getMessage() + "'}");
/*     */     }
/*  38 */     return "json_message";
/*     */   }
/*     */ 
/*     */   public IGoodsDataBatchManager getGoodsDataBatchManager() {
/*  42 */     return this.goodsDataBatchManager;
/*     */   }
/*     */ 
/*     */   public void setGoodsDataBatchManager(IGoodsDataBatchManager goodsDataBatchManager)
/*     */   {
/*  47 */     this.goodsDataBatchManager = goodsDataBatchManager;
/*     */   }
/*     */ 
/*     */   public String getPath() {
/*  51 */     return this.path;
/*     */   }
/*     */ 
/*     */   public void setPath(String path) {
/*  55 */     this.path = path;
/*     */   }
/*     */ 
/*     */   public List getCatList() {
/*  59 */     return this.catList;
/*     */   }
/*     */ 
/*     */   public void setCatList(List catList) {
/*  63 */     this.catList = catList;
/*     */   }
/*     */ 
/*     */   public IGoodsCatManager getGoodsCatManager() {
/*  67 */     return this.goodsCatManager;
/*     */   }
/*     */ 
/*     */   public void setGoodsCatManager(IGoodsCatManager goodsCatManager) {
/*  71 */     this.goodsCatManager = goodsCatManager;
/*     */   }
/*     */ 
/*     */   public int getImptype() {
/*  75 */     return this.imptype;
/*     */   }
/*     */ 
/*     */   public void setImptype(int imptype) {
/*  79 */     this.imptype = imptype;
/*     */   }
/*     */ 
/*     */   public int getCatid() {
/*  83 */     return this.catid;
/*     */   }
/*     */ 
/*     */   public void setCatid(int catid) {
/*  87 */     this.catid = catid;
/*     */   }
/*     */ 
/*     */   public Integer getStartNum() {
/*  91 */     return this.startNum;
/*     */   }
/*     */ 
/*     */   public void setStartNum(Integer startNum) {
/*  95 */     this.startNum = startNum;
/*     */   }
/*     */ 
/*     */   public Integer getEndNum() {
/*  99 */     return this.endNum;
/*     */   }
/*     */ 
/*     */   public void setEndNum(Integer endNum) {
/* 103 */     this.endNum = endNum;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.action.backend.GoodsImportAction
 * JD-Core Version:    0.6.0
 */