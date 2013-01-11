/*    */ package com.enation.app.shop.core.service.impl.batchimport;
/*    */ 
/*    */ import com.enation.app.shop.core.model.ImportDataSource;
/*    */ import com.enation.app.shop.core.service.batchimport.IGoodsDataImporter;
/*    */ import com.enation.app.shop.core.service.impl.batchimport.util.GoodsImageReader;
/*    */ import com.enation.framework.database.IDaoSupport;
/*    */ import java.util.Map;
/*    */ import org.apache.log4j.Logger;
/*    */ import org.w3c.dom.Element;
/*    */ 
/*    */ public class GoodsImageImporter
/*    */   implements IGoodsDataImporter
/*    */ {
/* 28 */   protected final Logger logger = Logger.getLogger(getClass());
/*    */   private IDaoSupport baseDaoSupport;
/*    */   private GoodsImageReader goodsImageReader;
/*    */ 
/*    */   public void imported(Object value, Element node, ImportDataSource importDs, Map goods)
/*    */   {
/* 35 */     Integer goodsid = (Integer)goods.get("goods_id");
/* 36 */     String excludeStr = node.getAttribute("exclude");
/*    */ 
/* 38 */     if (this.logger.isDebugEnabled()) {
/* 39 */       this.logger.debug("开始导入商品[" + goodsid + "]图片...");
/*    */     }
/*    */ 
/* 42 */     String[] images = this.goodsImageReader.read(importDs.getDatafolder(), goodsid.toString(), excludeStr);
/* 43 */     if (images != null) {
/* 44 */       this.baseDaoSupport.execute("update goods set image_file=? ,image_default=? where goods_id=?", new Object[] { images[0], images[1], goodsid });
/*    */     }
/* 46 */     if (this.logger.isDebugEnabled())
/* 47 */       this.logger.debug(" 商品[" + goodsid + "]图片导入完成");
/*    */   }
/*    */ 
/*    */   public IDaoSupport getBaseDaoSupport()
/*    */   {
/* 52 */     return this.baseDaoSupport;
/*    */   }
/*    */ 
/*    */   public void setBaseDaoSupport(IDaoSupport baseDaoSupport) {
/* 56 */     this.baseDaoSupport = baseDaoSupport;
/*    */   }
/*    */ 
/*    */   public GoodsImageReader getGoodsImageReader() {
/* 60 */     return this.goodsImageReader;
/*    */   }
/*    */ 
/*    */   public void setGoodsImageReader(GoodsImageReader goodsImageReader) {
/* 64 */     this.goodsImageReader = goodsImageReader;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.service.impl.batchimport.GoodsImageImporter
 * JD-Core Version:    0.6.0
 */