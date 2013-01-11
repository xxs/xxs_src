/*    */ package com.enation.app.shop.core.service.impl.batchimport;
/*    */ 
/*    */ import com.enation.app.shop.core.model.Attribute;
/*    */ import com.enation.app.shop.core.model.ImportDataSource;
/*    */ import com.enation.app.shop.core.service.batchimport.IGoodsDataImporter;
/*    */ import com.enation.framework.util.StringUtil;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import org.apache.log4j.Logger;
/*    */ import org.w3c.dom.Element;
/*    */ 
/*    */ public class GoodsPropImporter
/*    */   implements IGoodsDataImporter
/*    */ {
/* 20 */   protected final Logger logger = Logger.getLogger(getClass());
/*    */ 
/*    */   public void imported(Object value, Element node, ImportDataSource importConfig, Map goods)
/*    */   {
/* 24 */     List list = importConfig.getPropList();
/* 25 */     if (value == null) value = "";
/* 26 */     if ("null".equals(value)) value = "";
/*    */ 
/* 28 */     String dataType = node.getAttribute("dataType");
/* 29 */     if ((!StringUtil.isEmpty(dataType)) && 
/* 30 */       ("int".equals(dataType)) && 
/* 31 */       (!StringUtil.isEmpty(value.toString()))) {
/* 32 */       value = Integer.valueOf(Double.valueOf(value.toString()).intValue());
/*    */     }
/*    */ 
/* 36 */     int propindex = Integer.valueOf(node.getAttribute("propindex")).intValue();
/*    */ 
/* 38 */     if (this.logger.isDebugEnabled()) {
/* 39 */       this.logger.debug("开始导入商品属性[" + propindex + "]...");
/*    */     }
/* 41 */     int i = 1;
/* 42 */     for (Attribute attr : list) {
/* 43 */       if (propindex == i)
/*    */       {
/* 45 */         if (this.logger.isDebugEnabled()) {
/* 46 */           this.logger.debug("属性名为[" + attr.getName() + "],值为[" + value + "]");
/*    */         }
/*    */ 
/* 52 */         if (attr.getType() >= 3) {
/* 53 */           String[] options = attr.getOptionAr();
/* 54 */           if (options != null) {
/* 55 */             int index = 0;
/* 56 */             for (String op : options) {
/* 57 */               if (value.equals(op)) {
/* 58 */                 goods.put("p" + i, Integer.valueOf(index));
/* 59 */                 if (!this.logger.isDebugEnabled()) break;
/* 60 */                 this.logger.debug("找到商品属性[" + propindex + "]值为[" + index + "]..."); break;
/*    */               }
/*    */ 
/* 64 */               index++;
/*    */             }
/* 66 */             break;
/*    */           }
/* 67 */         }if (this.logger.isDebugEnabled()) {
/* 68 */           this.logger.debug("找到商品属性[" + propindex + "]值为[" + value + "]...");
/*    */         }
/*    */ 
/* 71 */         goods.put("p" + i, value);
/*    */ 
/* 73 */         break;
/*    */       }
/*    */ 
/* 76 */       i++;
/*    */     }
/*    */ 
/* 79 */     if (this.logger.isDebugEnabled())
/* 80 */       this.logger.debug("导入商品属性[" + propindex + "]完成");
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.service.impl.batchimport.GoodsPropImporter
 * JD-Core Version:    0.6.0
 */