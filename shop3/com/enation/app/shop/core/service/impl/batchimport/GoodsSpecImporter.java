/*    */ package com.enation.app.shop.core.service.impl.batchimport;
/*    */ 
/*    */ import com.enation.app.shop.core.model.Depot;
/*    */ import com.enation.app.shop.core.model.ImportDataSource;
/*    */ import com.enation.app.shop.core.model.Product;
/*    */ import com.enation.app.shop.core.service.IDepotManager;
/*    */ import com.enation.app.shop.core.service.IProductManager;
/*    */ import com.enation.app.shop.core.service.batchimport.IGoodsDataImporter;
/*    */ import com.enation.framework.database.IDaoSupport;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import org.w3c.dom.Element;
/*    */ 
/*    */ public class GoodsSpecImporter
/*    */   implements IGoodsDataImporter
/*    */ {
/*    */   protected IProductManager productManager;
/*    */   protected IDaoSupport baseDaoSupport;
/*    */   private IDepotManager depotManager;
/*    */ 
/*    */   public void imported(Object value, Element node, ImportDataSource importDs, Map goods)
/*    */   {
/* 30 */     Integer goodsid = (Integer)goods.get("goods_id");
/* 31 */     Product product = new Product();
/* 32 */     product.setGoods_id(goodsid);
/* 33 */     product.setCost(Double.valueOf("" + goods.get("cost")));
/* 34 */     product.setPrice(Double.valueOf("" + goods.get("price")));
/* 35 */     product.setSn((String)goods.get("sn"));
/* 36 */     product.setStore(Integer.valueOf(100));
/* 37 */     product.setWeight(Double.valueOf("" + goods.get("weight")));
/* 38 */     product.setName((String)goods.get("name"));
/*    */ 
/* 40 */     List productList = new ArrayList();
/* 41 */     productList.add(product);
/* 42 */     this.productManager.add(productList);
/*    */ 
/* 44 */     List depotList = this.depotManager.list();
/* 45 */     for (Depot depot : depotList)
/* 46 */       this.baseDaoSupport.execute("insert into goods_depot(goodsid,depotid,iscmpl)values(?,?,?)", new Object[] { goodsid, depot.getId(), Integer.valueOf(0) });
/*    */   }
/*    */ 
/*    */   public IProductManager getProductManager()
/*    */   {
/* 51 */     return this.productManager;
/*    */   }
/*    */   public void setProductManager(IProductManager productManager) {
/* 54 */     this.productManager = productManager;
/*    */   }
/*    */   public IDaoSupport getBaseDaoSupport() {
/* 57 */     return this.baseDaoSupport;
/*    */   }
/*    */   public void setBaseDaoSupport(IDaoSupport baseDaoSupport) {
/* 60 */     this.baseDaoSupport = baseDaoSupport;
/*    */   }
/*    */   public IDepotManager getDepotManager() {
/* 63 */     return this.depotManager;
/*    */   }
/*    */   public void setDepotManager(IDepotManager depotManager) {
/* 66 */     this.depotManager = depotManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.service.impl.batchimport.GoodsSpecImporter
 * JD-Core Version:    0.6.0
 */