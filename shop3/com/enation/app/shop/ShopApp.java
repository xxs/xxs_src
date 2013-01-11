/*     */ package com.enation.app.shop;
/*     */ 
/*     */ import com.enation.app.base.core.service.solution.impl.SqlExportService;
/*     */ import com.enation.app.shop.core.service.ICartManager;
/*     */ import com.enation.eop.resource.model.EopSite;
/*     */ import com.enation.eop.sdk.App;
/*     */ import com.enation.framework.cache.CacheFactory;
/*     */ import com.enation.framework.cache.ICache;
/*     */ import com.enation.framework.database.IDBRouter;
/*     */ import com.enation.framework.database.ISqlFileExecutor;
/*     */ import java.util.List;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.dom4j.Document;
/*     */ 
/*     */ public class ShopApp extends App
/*     */ {
/*  26 */   protected final Logger logger = Logger.getLogger(getClass());
/*     */   private IDBRouter baseDBRouter;
/*     */   private ISqlFileExecutor sqlFileExecutor;
/*     */   private SqlExportService sqlExportService;
/*     */   private ICartManager cartManager;
/*     */ 
/*     */   public ShopApp()
/*     */   {
/*  35 */     this.tables.add("goods");
/*  36 */     this.tables.add("goods_spec");
/*  37 */     this.tables.add("product");
/*     */ 
/*  40 */     this.tables.add("goods_cat");
/*     */ 
/*  43 */     this.tables.add("brand");
/*     */ 
/*  46 */     this.tables.add("goods_type");
/*  47 */     this.tables.add("type_brand");
/*     */ 
/*  50 */     this.tables.add("freeoffer");
/*  51 */     this.tables.add("freeoffer_category");
/*     */ 
/*  54 */     this.tables.add("tags");
/*  55 */     this.tables.add("tag_rel");
/*     */ 
/*  58 */     this.tables.add("member");
/*  59 */     this.tables.add("member_lv");
/*  60 */     this.tables.add("goods_lv_price");
/*     */ 
/*  63 */     this.tables.add("agent");
/*  64 */     this.tables.add("agent_transfer");
/*     */ 
/*  67 */     this.tables.add("dly_type");
/*  68 */     this.tables.add("dly_area");
/*  69 */     this.tables.add("dly_type_area");
/*     */ 
/*  72 */     this.tables.add("logi_company");
/*     */ 
/*  75 */     this.tables.add("comments");
/*     */ 
/*  78 */     this.tables.add("specification");
/*  79 */     this.tables.add("spec_values");
/*     */ 
/*  82 */     this.tables.add("cart");
/*  83 */     this.tables.add("order");
/*  84 */     this.tables.add("order_items");
/*  85 */     this.tables.add("order_log");
/*     */ 
/*  87 */     this.tables.add("delivery");
/*  88 */     this.tables.add("delivery_item");
/*  89 */     this.tables.add("payment_cfg");
/*  90 */     this.tables.add("payment_logs");
/*  91 */     this.tables.add("refund_logs");
/*  92 */     this.tables.add("member_address");
/*  93 */     this.tables.add("message");
/*  94 */     this.tables.add("order_gift");
/*     */ 
/*  96 */     this.tables.add("gnotify");
/*  97 */     this.tables.add("point_history");
/*  98 */     this.tables.add("coupons");
/*  99 */     this.tables.add("promotion");
/* 100 */     this.tables.add("member_coupon");
/* 101 */     this.tables.add("pmt_member_lv");
/* 102 */     this.tables.add("pmt_goods");
/* 103 */     this.tables.add("favorite");
/* 104 */     this.tables.add("advance_logs");
/* 105 */     this.tables.add("promotion_activity");
/* 106 */     this.tables.add("goods_complex");
/*     */ 
/* 108 */     this.tables.add("goods_articles");
/* 109 */     this.tables.add("goods_field");
/* 110 */     this.tables.add("group_buy_count");
/* 111 */     this.tables.add("limitbuy");
/* 112 */     this.tables.add("limitbuy_goods");
/* 113 */     this.tables.add("article");
/* 114 */     this.tables.add("article_cat");
/* 115 */     this.tables.add("package_product");
/* 116 */     this.tables.add("dly_center");
/* 117 */     this.tables.add("print_tmpl");
/* 118 */     this.tables.add("order_pmt");
/* 119 */     this.tables.add("group_buy");
/*     */ 
/* 121 */     this.tables.add("member_comment");
/* 122 */     this.tables.add("warn_num");
/* 123 */     this.tables.add("freeze_point");
/* 124 */     this.tables.add("member_lv_discount");
/*     */ 
/* 126 */     this.tables.add("order_meta");
/* 127 */     this.tables.add("coupons");
/* 128 */     this.tables.add("member_coupon");
/* 129 */     this.tables.add("member_order_item");
/* 130 */     this.tables.add("store_log");
/* 131 */     this.tables.add("depot_user");
/* 132 */     this.tables.add("product_store");
/* 133 */     this.tables.add("depot");
/* 134 */     this.tables.add("goods_depot");
/* 135 */     this.tables.add("allocation_item");
/* 136 */     this.tables.add("returns_order");
/*     */   }
/*     */ 
/*     */   public String getId()
/*     */   {
/* 143 */     return "shop";
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/* 148 */     return "shop应用";
/*     */   }
/*     */ 
/*     */   public String getNameSpace()
/*     */   {
/* 153 */     return "/shop";
/*     */   }
/*     */ 
/*     */   public void install()
/*     */   {
/* 160 */     doInstall("file:com/enation/app/shop/shop.xml");
/*     */   }
/*     */ 
/*     */   protected void cleanCache()
/*     */   {
/* 166 */     super.cleanCache();
/*     */ 
/* 169 */     CacheFactory.getCache("goods_cat").remove("goods_cat_" + this.userid + "_" + this.siteid + "_0");
/*     */ 
/* 172 */     CacheFactory.getCache("article_cat").remove("article_cat_" + this.userid + "_" + this.siteid);
/*     */   }
/*     */ 
/*     */   public void saasInstall()
/*     */   {
/* 180 */     this.baseDBRouter.doSaasInstall("file:com/enation/app/shop/shop.xml");
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public String dumpSql()
/*     */   {
/* 191 */     return this.sqlExportService.dumpSql(this.tables);
/*     */   }
/*     */ 
/*     */   public String dumpSql(Document setup) {
/* 195 */     return this.sqlExportService.dumpSql(this.tables, "clean", setup);
/*     */   }
/*     */ 
/*     */   public void sessionDestroyed(String sessionid, EopSite site)
/*     */   {
/* 203 */     if (this.logger.isDebugEnabled()) {
/* 204 */       this.logger.debug("clean cart...");
/*     */     }
/*     */ 
/* 208 */     if (site != null) {
/* 209 */       if (this.logger.isDebugEnabled()) {
/* 210 */         this.logger.debug("site get from session is userid[" + site.getUserid() + "]-siteid[" + site.getId() + "]");
/*     */       }
/* 212 */       this.cartManager.clean(sessionid, site.getUserid(), site.getId());
/*     */     }
/* 215 */     else if (this.logger.isDebugEnabled()) {
/* 216 */       this.logger.debug("site get from session is null");
/*     */     }
/*     */   }
/*     */ 
/*     */   public IDBRouter getBaseDBRouter()
/*     */   {
/* 222 */     return this.baseDBRouter;
/*     */   }
/*     */ 
/*     */   public void setBaseDBRouter(IDBRouter baseDBRouter) {
/* 226 */     this.baseDBRouter = baseDBRouter;
/*     */   }
/*     */ 
/*     */   public ISqlFileExecutor getSqlFileExecutor() {
/* 230 */     return this.sqlFileExecutor;
/*     */   }
/*     */ 
/*     */   public void setSqlFileExecutor(ISqlFileExecutor sqlFileExecutor) {
/* 234 */     this.sqlFileExecutor = sqlFileExecutor;
/*     */   }
/*     */ 
/*     */   public SqlExportService getSqlExportService() {
/* 238 */     return this.sqlExportService;
/*     */   }
/*     */ 
/*     */   public void setSqlExportService(SqlExportService sqlExportService) {
/* 242 */     this.sqlExportService = sqlExportService;
/*     */   }
/*     */ 
/*     */   public ICartManager getCartManager() {
/* 246 */     return this.cartManager;
/*     */   }
/*     */ 
/*     */   public void setCartManager(ICartManager cartManager) {
/* 250 */     this.cartManager = cartManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.ShopApp
 * JD-Core Version:    0.6.0
 */