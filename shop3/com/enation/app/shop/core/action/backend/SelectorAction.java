/*     */ package com.enation.app.shop.core.action.backend;
/*     */ 
/*     */ import com.enation.app.shop.core.service.IFreeOfferManager;
/*     */ import com.enation.app.shop.core.service.IGoodsCatManager;
/*     */ import com.enation.app.shop.core.service.IGoodsManager;
/*     */ import com.enation.app.shop.core.service.IProductManager;
/*     */ import com.enation.app.shop.core.service.ITagManager;
/*     */ import com.enation.eop.sdk.context.EopSetting;
/*     */ import com.enation.framework.action.WWAction;
/*     */ import com.enation.framework.util.StringUtil;
/*     */ import java.io.PrintStream;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.net.URLDecoder;
/*     */ import java.util.List;
/*     */ import net.sf.json.JSONArray;
/*     */ import net.sf.json.JSONObject;
/*     */ 
/*     */ public class SelectorAction extends WWAction
/*     */ {
/*     */   private IGoodsManager goodsManager;
/*     */   private IProductManager productManager;
/*     */   private IFreeOfferManager freeOfferManager;
/*     */   private String sType;
/*     */   private String keyword;
/*     */   private String order;
/*     */   private Integer catid;
/*     */   protected List catList;
/*     */   protected IGoodsCatManager goodsCatManager;
/*     */   private Integer[] goodsid;
/*     */   private Integer[] productid;
/*     */   private Integer[] giftid;
/*     */   private Integer[] tagid;
/*     */   private List tagList;
/*     */   private ITagManager tagManager;
/*     */   private int isSingle;
/*     */ 
/*     */   public String goods()
/*     */   {
/*  54 */     if ((this.keyword != null) && 
/*  55 */       (!StringUtil.isEmpty(EopSetting.ENCODING))) {
/*  56 */       this.keyword = StringUtil.to(this.keyword, EopSetting.ENCODING);
/*     */     }
/*     */ 
/*  60 */     String name = "name".equals(this.sType) ? this.keyword : null;
/*  61 */     String sn = "sn".equals(this.sType) ? this.keyword : null;
/*     */ 
/*  63 */     this.webpage = this.goodsManager.searchGoods(null, null, this.catid, name, sn, null, null, this.order, getPage(), 5);
/*  64 */     return "goods";
/*     */   }
/*     */ 
/*     */   public String cat() {
/*  68 */     this.catList = this.goodsCatManager.listAllChildren(Integer.valueOf(0));
/*  69 */     return "cat";
/*     */   }
/*     */ 
/*     */   public String tag() {
/*  73 */     this.tagList = this.tagManager.list();
/*  74 */     return "tag";
/*     */   }
/*     */ 
/*     */   public String product()
/*     */   {
/*  83 */     if ((this.keyword != null) && 
/*  84 */       (!StringUtil.isEmpty(EopSetting.ENCODING))) {
/*  85 */       this.keyword = StringUtil.to(this.keyword, EopSetting.ENCODING);
/*     */     }
/*     */ 
/*  88 */     String name = "name".equals(this.sType) ? this.keyword : null;
/*  89 */     String sn = "sn".equals(this.sType) ? this.keyword : null;
/*  90 */     this.webpage = this.productManager.list(name, sn, getPage(), 5, this.order);
/*  91 */     return "product";
/*     */   }
/*     */ 
/*     */   public String gift()
/*     */   {
/* 101 */     if (this.keyword != null) this.keyword = StringUtil.toUTF8(this.keyword);
/* 102 */     this.webpage = this.freeOfferManager.list(this.keyword, this.order, getPage(), 3);
/* 103 */     return "gift";
/*     */   }
/*     */ 
/*     */   public String listGoods()
/*     */   {
/* 113 */     List goodsList = this.goodsManager.list(this.goodsid);
/*     */ 
/* 115 */     if (this.isSingle == 0) {
/* 116 */       this.json = JSONArray.fromObject(goodsList).toString();
/*     */     }
/*     */     else {
/* 119 */       Object goods = goodsList.get(0);
/* 120 */       this.json = JSONObject.fromObject(goods).toString();
/*     */     }
/*     */ 
/* 124 */     return "json_message";
/*     */   }
/*     */ 
/*     */   public String listGoodsByCat() {
/* 128 */     List goodsList = this.goodsManager.listByCat(this.catid);
/* 129 */     this.json = JSONArray.fromObject(goodsList).toString();
/*     */ 
/* 131 */     return "json_message";
/*     */   }
/*     */ 
/*     */   public String listGoodsByTag() {
/* 135 */     List goodsList = this.goodsManager.listByTag(this.tagid);
/* 136 */     this.json = JSONArray.fromObject(goodsList).toString();
/*     */ 
/* 138 */     return "json_message";
/*     */   }
/*     */ 
/*     */   public String listProduct()
/*     */   {
/* 147 */     List productlist = this.productManager.list(this.productid);
/* 148 */     this.json = JSONArray.fromObject(productlist).toString();
/* 149 */     return "json_message";
/*     */   }
/*     */ 
/*     */   public String listGift()
/*     */   {
/* 158 */     List giftList = this.freeOfferManager.list(this.giftid);
/* 159 */     this.json = JSONArray.fromObject(giftList).toString();
/* 160 */     return "json_message";
/*     */   }
/*     */ 
/*     */   public IGoodsManager getGoodsManager()
/*     */   {
/* 166 */     return this.goodsManager;
/*     */   }
/*     */ 
/*     */   public void setGoodsManager(IGoodsManager goodsManager) {
/* 170 */     this.goodsManager = goodsManager;
/*     */   }
/*     */ 
/*     */   public IProductManager getProductManager() {
/* 174 */     return this.productManager;
/*     */   }
/*     */ 
/*     */   public void setProductManager(IProductManager productManager) {
/* 178 */     this.productManager = productManager;
/*     */   }
/*     */ 
/*     */   public String getKeyword() {
/* 182 */     return this.keyword;
/*     */   }
/*     */ 
/*     */   public void setKeyword(String keyword) {
/* 186 */     this.keyword = keyword;
/*     */   }
/*     */ 
/*     */   public String getOrder() {
/* 190 */     return this.order;
/*     */   }
/*     */ 
/*     */   public void setOrder(String order) {
/* 194 */     this.order = order;
/*     */   }
/*     */ 
/*     */   public String getsType() {
/* 198 */     return this.sType;
/*     */   }
/*     */ 
/*     */   public void setsType(String sType) {
/* 202 */     this.sType = sType;
/*     */   }
/*     */ 
/*     */   public Integer[] getGoodsid() {
/* 206 */     return this.goodsid;
/*     */   }
/*     */ 
/*     */   public void setGoodsid(Integer[] goodsid) {
/* 210 */     this.goodsid = goodsid;
/*     */   }
/*     */ 
/*     */   public IFreeOfferManager getFreeOfferManager()
/*     */   {
/* 215 */     return this.freeOfferManager;
/*     */   }
/*     */ 
/*     */   public void setFreeOfferManager(IFreeOfferManager freeOfferManager)
/*     */   {
/* 220 */     this.freeOfferManager = freeOfferManager;
/*     */   }
/*     */ 
/*     */   public Integer[] getGiftid()
/*     */   {
/* 225 */     return this.giftid;
/*     */   }
/*     */ 
/*     */   public void setGiftid(Integer[] giftid)
/*     */   {
/* 230 */     this.giftid = giftid;
/*     */   }
/*     */ 
/*     */   public Integer[] getProductid()
/*     */   {
/* 235 */     return this.productid;
/*     */   }
/*     */ 
/*     */   public void setProductid(Integer[] productid)
/*     */   {
/* 240 */     this.productid = productid;
/*     */   }
/*     */ 
/*     */   public Integer getCatid()
/*     */   {
/* 245 */     return this.catid;
/*     */   }
/*     */ 
/*     */   public void setCatid(Integer catid)
/*     */   {
/* 250 */     this.catid = catid;
/*     */   }
/*     */ 
/*     */   public List getCatList() {
/* 254 */     return this.catList;
/*     */   }
/*     */ 
/*     */   public void setCatList(List catList) {
/* 258 */     this.catList = catList;
/*     */   }
/*     */ 
/*     */   public IGoodsCatManager getGoodsCatManager() {
/* 262 */     return this.goodsCatManager;
/*     */   }
/*     */ 
/*     */   public void setGoodsCatManager(IGoodsCatManager goodsCatManager) {
/* 266 */     this.goodsCatManager = goodsCatManager;
/*     */   }
/*     */ 
/*     */   public Integer[] getTagid() {
/* 270 */     return this.tagid;
/*     */   }
/*     */ 
/*     */   public void setTagid(Integer[] tagid) {
/* 274 */     this.tagid = tagid;
/*     */   }
/*     */ 
/*     */   public List getTagList() {
/* 278 */     return this.tagList;
/*     */   }
/*     */ 
/*     */   public void setTagList(List tagList) {
/* 282 */     this.tagList = tagList;
/*     */   }
/*     */ 
/*     */   public ITagManager getTagManager() {
/* 286 */     return this.tagManager;
/*     */   }
/*     */ 
/*     */   public void setTagManager(ITagManager tagManager) {
/* 290 */     this.tagManager = tagManager;
/*     */   }
/*     */ 
/*     */   public static void main(String[] args) throws UnsupportedEncodingException {
/* 294 */     System.out.println(URLDecoder.decode("测试", "UTF-8"));
/*     */   }
/*     */ 
/*     */   public int getIsSingle()
/*     */   {
/* 299 */     return this.isSingle;
/*     */   }
/*     */ 
/*     */   public void setIsSingle(int isSingle) {
/* 303 */     this.isSingle = isSingle;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.action.backend.SelectorAction
 * JD-Core Version:    0.6.0
 */