/*    */ package com.enation.app.shop.core.service.impl.batchimport;
/*    */ 
/*    */ import com.enation.app.shop.core.model.Brand;
/*    */ import com.enation.app.shop.core.model.ImportDataSource;
/*    */ import com.enation.app.shop.core.service.batchimport.IGoodsDataImporter;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import org.w3c.dom.Element;
/*    */ 
/*    */ public class GoodsBrandImporter
/*    */   implements IGoodsDataImporter
/*    */ {
/*    */   public void imported(Object name, Element node, ImportDataSource importDs, Map goods)
/*    */   {
/* 22 */     if (!importDs.isNewGoods()) return;
/*    */ 
/* 24 */     String brandname = (String)name;
/* 25 */     if (brandname == null) brandname = "";
/* 26 */     brandname = brandname.trim();
/*    */ 
/* 28 */     List brandList = importDs.getBrandList();
/* 29 */     for (Brand brand : brandList)
/* 30 */       if (brand.getName().equals(brandname))
/* 31 */         goods.put("brand_id", brand.getBrand_id());
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.service.impl.batchimport.GoodsBrandImporter
 * JD-Core Version:    0.6.0
 */