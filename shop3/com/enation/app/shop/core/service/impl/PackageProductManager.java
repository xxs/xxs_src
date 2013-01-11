/*     */ package com.enation.app.shop.core.service.impl;
/*     */ 
/*     */ import com.enation.app.shop.core.model.Goods;
/*     */ import com.enation.app.shop.core.model.PackageProduct;
/*     */ import com.enation.app.shop.core.model.Product;
/*     */ import com.enation.app.shop.core.service.IGoodsManager;
/*     */ import com.enation.app.shop.core.service.IPackageProductManager;
/*     */ import com.enation.app.shop.core.service.IProductManager;
/*     */ import com.enation.eop.sdk.database.BaseSupport;
/*     */ import com.enation.framework.database.IDaoSupport;
/*     */ import com.enation.framework.util.DateUtil;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import org.springframework.transaction.annotation.Propagation;
/*     */ import org.springframework.transaction.annotation.Transactional;
/*     */ 
/*     */ public class PackageProductManager extends BaseSupport
/*     */   implements IPackageProductManager
/*     */ {
/*     */   private IGoodsManager goodsManager;
/*     */   private IProductManager productManager;
/*     */ 
/*     */   public void add(PackageProduct packageProduct)
/*     */   {
/*  34 */     this.baseDaoSupport.insert("package_product", packageProduct);
/*     */   }
/*     */ 
/*     */   @Transactional(propagation=Propagation.REQUIRED)
/*     */   public void add(Goods goods, int[] product_id, int[] pkgnum)
/*     */   {
/*  41 */     String sn = "P" + DateUtil.toString(new Date(System.currentTimeMillis()), "yyyyMMddhhmmss");
/*  42 */     goods.setSn(sn);
/*  43 */     goods.setPoint(Integer.valueOf(0));
/*  44 */     this.baseDaoSupport.insert("goods", goods);
/*  45 */     Integer goods_id = Integer.valueOf(this.baseDaoSupport.getLastId("goods"));
/*  46 */     for (int i = 0; i < product_id.length; i++) {
/*  47 */       PackageProduct product = new PackageProduct();
/*  48 */       product.setGoods_id(goods_id.intValue());
/*  49 */       product.setProduct_id(product_id[i]);
/*  50 */       product.setPkgnum(pkgnum[i]);
/*  51 */       add(product);
/*     */     }
/*     */ 
/*  54 */     Product product = new Product();
/*  55 */     product.setGoods_id(goods_id);
/*     */ 
/*  57 */     product.setName(goods.getName());
/*  58 */     product.setPrice(goods.getPrice());
/*  59 */     product.setSn(sn);
/*  60 */     product.setStore(goods.getStore());
/*  61 */     product.setWeight(goods.getWeight());
/*     */ 
/*  63 */     List productList = new ArrayList();
/*  64 */     productList.add(product);
/*  65 */     this.productManager.add(productList);
/*     */   }
/*     */ 
/*     */   @Transactional(propagation=Propagation.REQUIRED)
/*     */   public void edit(Goods goods, int[] product_id, int[] pkgnum)
/*     */   {
/*  73 */     this.baseDaoSupport.update("goods", goods, "goods_id=" + goods.getGoods_id());
/*  74 */     this.baseDaoSupport.execute("delete from package_product where goods_id = ?", new Object[] { goods.getGoods_id() });
/*  75 */     for (int i = 0; i < product_id.length; i++) {
/*  76 */       PackageProduct product = new PackageProduct();
/*  77 */       product.setGoods_id(goods.getGoods_id().intValue());
/*  78 */       product.setProduct_id(product_id[i]);
/*  79 */       product.setPkgnum(pkgnum[i]);
/*  80 */       add(product);
/*     */     }
/*     */ 
/*  84 */     Product product = new Product();
/*  85 */     product.setGoods_id(goods.getGoods_id());
/*  86 */     product.setName(goods.getName());
/*  87 */     product.setPrice(goods.getPrice());
/*  88 */     product.setStore(goods.getStore());
/*  89 */     product.setWeight(goods.getWeight());
/*  90 */     product.setSn(goods.getSn());
/*  91 */     List productList = new ArrayList();
/*  92 */     productList.add(product);
/*  93 */     this.productManager.add(productList);
/*     */   }
/*     */ 
/*     */   public List list(int goods_id)
/*     */   {
/*  99 */     String sql = "select pp.*, p.product_id, p.sn, p.price, p.goods_id as pgoods_id, p.weight, g.name from " + getTableName("package_product") + " pp left join " + getTableName("product") + " p on p.product_id = pp.product_id left join " + getTableName("goods") + " g on g.goods_id = p.goods_id";
/* 100 */     sql = sql + " where pp.goods_id = " + goods_id;
/* 101 */     List list = this.daoSupport.queryForList(sql, new Object[0]);
/* 102 */     return list;
/*     */   }
/*     */ 
/*     */   public IGoodsManager getGoodsManager() {
/* 106 */     return this.goodsManager;
/*     */   }
/*     */ 
/*     */   public void setGoodsManager(IGoodsManager goodsManager) {
/* 110 */     this.goodsManager = goodsManager;
/*     */   }
/*     */ 
/*     */   public IProductManager getProductManager() {
/* 114 */     return this.productManager;
/*     */   }
/*     */ 
/*     */   public void setProductManager(IProductManager productManager) {
/* 118 */     this.productManager = productManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.service.impl.PackageProductManager
 * JD-Core Version:    0.6.0
 */