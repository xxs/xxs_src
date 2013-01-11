/*    */ package com.enation.app.shop.core.service.impl.batchimport;
/*    */ 
/*    */ import com.enation.app.shop.core.model.ImportDataSource;
/*    */ import com.enation.app.shop.core.service.batchimport.IGoodsDataImporter;
/*    */ import com.enation.app.shop.core.service.impl.batchimport.util.GoodsDescReader;
/*    */ import com.enation.framework.database.IDaoSupport;
/*    */ import com.enation.framework.util.FileUtil;
/*    */ import java.io.PrintStream;
/*    */ import java.util.Map;
/*    */ import org.apache.log4j.Logger;
/*    */ import org.w3c.dom.Element;
/*    */ 
/*    */ public class GoodsDescImporter
/*    */   implements IGoodsDataImporter
/*    */ {
/* 25 */   protected final Logger logger = Logger.getLogger(getClass());
/*    */   private IDaoSupport daoSupport;
/*    */   private IDaoSupport baseDaoSupport;
/*    */   private GoodsDescReader goodsDescReader;
/*    */ 
/*    */   public void imported(Object value, Element node, ImportDataSource importDs, Map goods)
/*    */   {
/* 34 */     Integer goodsid = (Integer)goods.get("goods_id");
/*    */ 
/* 37 */     if (this.logger.isDebugEnabled()) {
/* 38 */       this.logger.debug("开始导入商品[" + goodsid + "]描述...");
/*    */     }
/*    */ 
/* 41 */     String bodyhtml = this.goodsDescReader.read(importDs.getDatafolder(), goodsid.toString());
/*    */ 
/* 43 */     if (bodyhtml != null) {
/* 44 */       this.baseDaoSupport.execute("update goods set intro=? where goods_id=?", new Object[] { bodyhtml, goodsid });
/*    */     }
/*    */ 
/* 48 */     if (this.logger.isDebugEnabled())
/* 49 */       this.logger.debug("导入商品[" + goodsid + "]描述 完成");
/*    */   }
/*    */ 
/*    */   public IDaoSupport getDaoSupport()
/*    */   {
/* 55 */     return this.daoSupport;
/*    */   }
/*    */   public void setDaoSupport(IDaoSupport daoSupport) {
/* 58 */     this.daoSupport = daoSupport;
/*    */   }
/*    */ 
/*    */   public IDaoSupport getBaseDaoSupport()
/*    */   {
/* 63 */     return this.baseDaoSupport;
/*    */   }
/*    */ 
/*    */   public void setBaseDaoSupport(IDaoSupport baseDaoSupport) {
/* 67 */     this.baseDaoSupport = baseDaoSupport;
/*    */   }
/*    */ 
/*    */   public GoodsDescReader getGoodsDescReader() {
/* 71 */     return this.goodsDescReader;
/*    */   }
/*    */ 
/*    */   public void setGoodsDescReader(GoodsDescReader goodsDescReader) {
/* 75 */     this.goodsDescReader = goodsDescReader;
/*    */   }
/*    */ 
/*    */   public static void main(String[] args) {
/* 79 */     System.out.println(FileUtil.read("D:/goodsimport/goods/彩片/3/desc.htm", "GBK"));
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.service.impl.batchimport.GoodsDescImporter
 * JD-Core Version:    0.6.0
 */