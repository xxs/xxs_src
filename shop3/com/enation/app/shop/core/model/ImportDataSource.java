/*    */ package com.enation.app.shop.core.model;
/*    */ 
/*    */ import java.util.List;
/*    */ import org.apache.poi.ss.usermodel.Row;
/*    */ 
/*    */ public class ImportDataSource
/*    */ {
/*    */   private List<Brand> brandList;
/*    */   private List<Attribute> propList;
/*    */   private String datafolder;
/*    */   private int goodsNum;
/*    */   private boolean isNewGoods;
/*    */   private Row rowData;
/*    */ 
/*    */   public List<Brand> getBrandList()
/*    */   {
/* 16 */     return this.brandList;
/*    */   }
/*    */   public void setBrandList(List<Brand> brandList) {
/* 19 */     this.brandList = brandList;
/*    */   }
/*    */   public List<Attribute> getPropList() {
/* 22 */     return this.propList;
/*    */   }
/*    */   public void setPropList(List<Attribute> propList) {
/* 25 */     this.propList = propList;
/*    */   }
/*    */   public String getDatafolder() {
/* 28 */     return this.datafolder;
/*    */   }
/*    */   public void setDatafolder(String datafolder) {
/* 31 */     this.datafolder = datafolder;
/*    */   }
/*    */   public int getGoodsNum() {
/* 34 */     return this.goodsNum;
/*    */   }
/*    */   public void setGoodsNum(int goodsNum) {
/* 37 */     this.goodsNum = goodsNum;
/*    */   }
/*    */   public boolean isNewGoods() {
/* 40 */     return this.isNewGoods;
/*    */   }
/*    */   public void setNewGoods(boolean isNewGoods) {
/* 43 */     this.isNewGoods = isNewGoods;
/*    */   }
/*    */   public Row getRowData() {
/* 46 */     return this.rowData;
/*    */   }
/*    */   public void setRowData(Row rowData) {
/* 49 */     this.rowData = rowData;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.model.ImportDataSource
 * JD-Core Version:    0.6.0
 */