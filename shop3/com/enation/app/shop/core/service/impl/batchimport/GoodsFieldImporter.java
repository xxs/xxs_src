/*    */ package com.enation.app.shop.core.service.impl.batchimport;
/*    */ 
/*    */ import com.enation.app.shop.core.model.ImportDataSource;
/*    */ import com.enation.app.shop.core.service.batchimport.IGoodsDataImporter;
/*    */ import java.util.Map;
/*    */ import org.w3c.dom.Element;
/*    */ 
/*    */ public class GoodsFieldImporter
/*    */   implements IGoodsDataImporter
/*    */ {
/*    */   public void imported(Object value, Element node, ImportDataSource importConfig, Map goods)
/*    */   {
/* 21 */     String fieldname = node.getAttribute("fieldname");
/* 22 */     if (importConfig.isNewGoods())
/* 23 */       goods.put(fieldname, value);
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.service.impl.batchimport.GoodsFieldImporter
 * JD-Core Version:    0.6.0
 */