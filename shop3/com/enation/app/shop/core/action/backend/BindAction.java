/*     */ package com.enation.app.shop.core.action.backend;
/*     */ 
/*     */ import com.enation.app.shop.core.model.Goods;
/*     */ import com.enation.app.shop.core.service.IGoodsManager;
/*     */ import com.enation.app.shop.core.service.IPackageProductManager;
/*     */ import com.enation.app.shop.core.service.IProductManager;
/*     */ import com.enation.framework.action.WWAction;
/*     */ import com.enation.framework.database.Page;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class BindAction extends WWAction
/*     */ {
/*     */   protected String name;
/*     */   protected String sn;
/*     */   protected String order;
/*     */   protected IGoodsManager goodsManager;
/*     */   protected int goods_id;
/*     */   protected Page productPage;
/*     */   protected IProductManager productManager;
/*     */   protected IPackageProductManager packageProductManager;
/*     */   protected int[] product_id;
/*     */   protected int[] pkgnum;
/*     */   protected Goods bind;
/*     */   protected List packageList;
/*     */ 
/*     */   public String bindlist()
/*     */   {
/*  34 */     this.webpage = this.goodsManager.searchBindGoods(this.name, this.sn, this.order, getPage(), getPageSize());
/*     */ 
/*  36 */     return "bindlist";
/*     */   }
/*     */ 
/*     */   public String delete() {
/*     */     try {
/*  41 */       this.goodsManager.delete(new Integer[] { Integer.valueOf(this.goods_id) });
/*  42 */       this.json = "{'result':0,'message':'删除成功'}";
/*     */     } catch (RuntimeException e) {
/*  44 */       this.json = "{'result':1,'message':'删除失败'}";
/*     */     }
/*  46 */     return "json_message";
/*     */   }
/*     */ 
/*     */   public String add()
/*     */   {
/*  51 */     return "add";
/*     */   }
/*     */ 
/*     */   public String edit()
/*     */   {
/*  56 */     this.bind = this.goodsManager.getGoods(Integer.valueOf(this.goods_id));
/*  57 */     this.packageList = this.packageProductManager.list(this.goods_id);
/*  58 */     return "edit";
/*     */   }
/*     */ 
/*     */   public String addSave() {
/*     */     try {
/*  63 */       this.bind.setCreate_time(Long.valueOf(System.currentTimeMillis()));
/*  64 */       this.bind.setLast_modify(Long.valueOf(System.currentTimeMillis()));
/*  65 */       this.bind.setView_count(Integer.valueOf(0));
/*  66 */       this.bind.setBuy_count(Integer.valueOf(0));
/*  67 */       this.bind.setGoods_type("bind");
/*  68 */       this.bind.setDisabled(Integer.valueOf(0));
/*  69 */       this.packageProductManager.add(this.bind, this.product_id, this.pkgnum);
/*  70 */       this.msgs.add("捆绑商品添加成功");
/*  71 */       this.urls.put("捆绑商品列表", "bind!bindlist.do");
/*     */     } catch (RuntimeException e) {
/*  73 */       this.msgs.add("捆绑商品添加失败");
/*  74 */       this.urls.put("捆绑商品列表", "bind!bindlist.do");
/*     */     }
/*  76 */     return "message";
/*     */   }
/*     */ 
/*     */   public String editSave() {
/*     */     try {
/*  81 */       this.bind.setLast_modify(Long.valueOf(System.currentTimeMillis()));
/*  82 */       this.packageProductManager.edit(this.bind, this.product_id, this.pkgnum);
/*  83 */       this.msgs.add("捆绑商品修改成功");
/*  84 */       this.urls.put("捆绑商品列表", "bind!bindlist.do");
/*     */     } catch (RuntimeException e) {
/*  86 */       this.msgs.add("捆绑商品修改失败");
/*  87 */       this.urls.put("捆绑商品列表", "bind!bindlist.do");
/*     */     }
/*  89 */     return "message";
/*     */   }
/*     */ 
/*     */   public String getName() {
/*  93 */     return this.name;
/*     */   }
/*     */ 
/*     */   public void setName(String name) {
/*  97 */     this.name = name;
/*     */   }
/*     */ 
/*     */   public String getSn() {
/* 101 */     return this.sn;
/*     */   }
/*     */ 
/*     */   public void setSn(String sn) {
/* 105 */     this.sn = sn;
/*     */   }
/*     */ 
/*     */   public String getOrder() {
/* 109 */     return this.order;
/*     */   }
/*     */ 
/*     */   public void setOrder(String order) {
/* 113 */     this.order = order;
/*     */   }
/*     */ 
/*     */   public IGoodsManager getGoodsManager() {
/* 117 */     return this.goodsManager;
/*     */   }
/*     */ 
/*     */   public void setGoodsManager(IGoodsManager goodsManager) {
/* 121 */     this.goodsManager = goodsManager;
/*     */   }
/*     */ 
/*     */   public int getGoods_id() {
/* 125 */     return this.goods_id;
/*     */   }
/*     */ 
/*     */   public void setGoods_id(int goodsId) {
/* 129 */     this.goods_id = goodsId;
/*     */   }
/*     */ 
/*     */   public Page getProductPage() {
/* 133 */     return this.productPage;
/*     */   }
/*     */ 
/*     */   public void setProductPage(Page productPage) {
/* 137 */     this.productPage = productPage;
/*     */   }
/*     */ 
/*     */   public IProductManager getProductManager() {
/* 141 */     return this.productManager;
/*     */   }
/*     */ 
/*     */   public void setProductManager(IProductManager productManager) {
/* 145 */     this.productManager = productManager;
/*     */   }
/*     */ 
/*     */   public int[] getProduct_id() {
/* 149 */     return this.product_id;
/*     */   }
/*     */ 
/*     */   public void setProduct_id(int[] productId) {
/* 153 */     this.product_id = productId;
/*     */   }
/*     */ 
/*     */   public int[] getPkgnum() {
/* 157 */     return this.pkgnum;
/*     */   }
/*     */ 
/*     */   public void setPkgnum(int[] pkgnum) {
/* 161 */     this.pkgnum = pkgnum;
/*     */   }
/*     */ 
/*     */   public Goods getBind() {
/* 165 */     return this.bind;
/*     */   }
/*     */ 
/*     */   public void setBind(Goods bind) {
/* 169 */     this.bind = bind;
/*     */   }
/*     */ 
/*     */   public IPackageProductManager getPackageProductManager() {
/* 173 */     return this.packageProductManager;
/*     */   }
/*     */ 
/*     */   public void setPackageProductManager(IPackageProductManager packageProductManager)
/*     */   {
/* 178 */     this.packageProductManager = packageProductManager;
/*     */   }
/*     */ 
/*     */   public List getPackageList() {
/* 182 */     return this.packageList;
/*     */   }
/*     */ 
/*     */   public void setPackageList(List packageList) {
/* 186 */     this.packageList = packageList;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.action.backend.BindAction
 * JD-Core Version:    0.6.0
 */