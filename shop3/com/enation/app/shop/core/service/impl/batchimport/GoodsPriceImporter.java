/*    */ package com.enation.app.shop.core.service.impl.batchimport;
/*    */ 
/*    */ import com.enation.app.shop.core.model.ImportDataSource;
/*    */ import com.enation.app.shop.core.service.batchimport.IGoodsDataImporter;
/*    */ import java.util.Map;
/*    */ import org.w3c.dom.Element;
/*    */ 
/*    */ public class GoodsPriceImporter
/*    */   implements IGoodsDataImporter
/*    */ {
/*    */   public void imported(Object value, Element node, ImportDataSource importDs, Map goods)
/*    */   {
/* 14 */     if ((value == null) || (value.equals(""))) value = Integer.valueOf(0);
/*    */ 
/* 16 */     if (importDs.isNewGoods())
/* 17 */       if ("mkprice".equals(node.getAttribute("type")))
/* 18 */         goods.put("mktprice", value);
/*    */       else
/* 20 */         goods.put("price", value);
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.service.impl.batchimport.GoodsPriceImporter
 * JD-Core Version:    0.6.0
 */