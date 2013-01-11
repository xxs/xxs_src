/*     */ package com.enation.app.shop.core.action.backend;
/*     */ 
/*     */ import com.enation.app.base.core.service.auth.IAdminUserManager;
/*     */ import com.enation.app.base.core.service.auth.IPermissionManager;
/*     */ import com.enation.app.shop.core.model.Goods;
/*     */ import com.enation.app.shop.core.model.Tag;
/*     */ import com.enation.app.shop.core.model.support.GoodsEditDTO;
/*     */ import com.enation.app.shop.core.plugin.goods.GoodsPluginBundle;
/*     */ import com.enation.app.shop.core.service.IBrandManager;
/*     */ import com.enation.app.shop.core.service.IGoodsCatManager;
/*     */ import com.enation.app.shop.core.service.IGoodsManager;
/*     */ import com.enation.app.shop.core.service.IProductManager;
/*     */ import com.enation.app.shop.core.service.ITagManager;
/*     */ import com.enation.eop.sdk.context.EopSetting;
/*     */ import com.enation.framework.action.WWAction;
/*     */ import com.enation.framework.util.StringUtil;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class GoodsAction extends WWAction
/*     */ {
/*     */   protected String name;
/*     */   protected String sn;
/*     */   protected String order;
/*     */   private Integer catid;
/*     */   protected Integer[] id;
/*     */   protected List brandList;
/*     */   protected Integer brand_id;
/*     */   protected Integer is_market;
/*     */   protected Goods goods;
/*     */   protected Map goodsView;
/*     */   protected Integer goods_id;
/*     */   protected List catList;
/*     */   protected IGoodsCatManager goodsCatManager;
/*     */   protected IBrandManager brandManager;
/*     */   protected IGoodsManager goodsManager;
/*     */   private IProductManager productManager;
/*     */   protected Boolean is_edit;
/*     */   protected String actionName;
/*     */   protected Integer market_enable;
/*     */   private Integer[] tagids;
/*     */   private GoodsPluginBundle goodsPluginBundle;
/*     */   private IPermissionManager permissionManager;
/*     */   private IAdminUserManager adminUserManager;
/*     */   private ITagManager tagManager;
/*     */   protected Map<Integer, String> pluginTabs;
/*     */   protected Map<Integer, String> pluginHtmls;
/*     */   private List<Tag> tagList;
/*     */   private String optype;
/*     */   private int depotid;
/*     */   private String is_other;
/*     */   private List<String> tagHtmlList;
/*     */ 
/*     */   public String selectCat()
/*     */   {
/*  67 */     return "select_cat";
/*     */   }
/*     */ 
/*     */   public String list()
/*     */   {
/*  74 */     if (this.name != null) {
/*  75 */       String encoding = EopSetting.ENCODING;
/*  76 */       if (!StringUtil.isEmpty(encoding)) {
/*  77 */         this.name = StringUtil.to(this.name, encoding);
/*     */       }
/*     */     }
/*     */ 
/*  81 */     this.brandList = this.brandManager.list();
/*  82 */     this.tagList = this.tagManager.list();
/*  83 */     this.webpage = this.goodsManager.searchGoods(this.brand_id, this.is_market, this.catid, this.name, this.sn, this.market_enable, this.tagids, this.order, getPage(), getPageSize(), this.is_other);
/*     */ 
/*  85 */     this.is_edit = (this.is_edit == null ? Boolean.FALSE : Boolean.TRUE);
/*  86 */     if (!this.is_edit.booleanValue()) {
/*  87 */       return "list";
/*     */     }
/*  89 */     return "edit_list";
/*     */   }
/*     */ 
/*     */   public String batchEdit()
/*     */   {
/*     */     try {
/*  95 */       this.goodsManager.batchEdit();
/*  96 */       this.json = "{result:1}";
/*     */     } catch (RuntimeException e) {
/*  98 */       e.printStackTrace();
/*  99 */       this.json = "{result:0}";
/*     */     }
/*     */ 
/* 102 */     return "json_message";
/*     */   }
/*     */ 
/*     */   public String getCatTree() {
/* 106 */     this.catList = this.goodsCatManager.listAllChildren(Integer.valueOf(0));
/* 107 */     return "cat_tree";
/*     */   }
/*     */ 
/*     */   public String trash_list()
/*     */   {
/* 112 */     this.webpage = this.goodsManager.pageTrash(this.name, this.sn, this.order, getPage(), getPageSize());
/* 113 */     return "trash_list";
/*     */   }
/*     */ 
/*     */   public String delete()
/*     */   {
/*     */     try {
/* 119 */       this.goodsManager.delete(this.id);
/* 120 */       this.json = "{'result':0,'message':'删除成功'}";
/*     */     } catch (RuntimeException e) {
/* 122 */       this.json = "{'result':1,'message':'删除失败'}";
/*     */     }
/* 124 */     return "json_message";
/*     */   }
/*     */ 
/*     */   public String revert()
/*     */   {
/*     */     try {
/* 130 */       this.goodsManager.revert(this.id);
/* 131 */       this.json = "{'result':0,'message':'清除成功'}";
/*     */     } catch (RuntimeException e) {
/* 133 */       this.json = "{'result':1,'message':'清除失败'}";
/*     */     }
/* 135 */     return "json_message";
/*     */   }
/*     */ 
/*     */   public String clean()
/*     */   {
/*     */     try {
/* 141 */       this.goodsManager.clean(this.id);
/* 142 */       this.json = "{'result':0,'message':'清除成功'}";
/*     */     } catch (RuntimeException e) {
/* 144 */       e.printStackTrace();
/* 145 */       this.json = "{'result':1,'message':'清除失败'}";
/*     */     }
/* 147 */     return "json_message";
/*     */   }
/*     */ 
/*     */   public String selector_list_ajax()
/*     */   {
/* 154 */     return "selector";
/*     */   }
/*     */ 
/*     */   public String add()
/*     */   {
/* 163 */     this.actionName = "goods!saveAdd.do";
/* 164 */     this.is_edit = Boolean.valueOf(false);
/*     */ 
/* 166 */     this.pluginTabs = this.goodsPluginBundle.getTabList();
/* 167 */     this.pluginHtmls = this.goodsPluginBundle.onFillAddInputData();
/*     */ 
/* 171 */     return "input";
/*     */   }
/*     */ 
/*     */   public String addBind()
/*     */   {
/* 178 */     this.actionName = "goods!saveBindAdd.do";
/* 179 */     return "bind_goods_input";
/*     */   }
/*     */ 
/*     */   public String edit()
/*     */   {
/* 188 */     this.actionName = "goods!saveEdit.do";
/* 189 */     this.is_edit = Boolean.valueOf(true);
/*     */ 
/* 192 */     this.catList = this.goodsCatManager.listAllChildren(Integer.valueOf(0));
/* 193 */     GoodsEditDTO editDTO = this.goodsManager.getGoodsEditData(this.goods_id);
/* 194 */     this.goodsView = editDTO.getGoods();
/*     */ 
/* 196 */     this.pluginTabs = this.goodsPluginBundle.getTabList();
/* 197 */     this.pluginHtmls = editDTO.getHtmlMap();
/*     */ 
/* 199 */     return "input";
/*     */   }
/*     */ 
/*     */   public String saveAdd()
/*     */   {
/*     */     try
/*     */     {
/* 210 */       this.goodsManager.add(this.goods);
/* 211 */       this.catid = this.goods.getCat_id();
/* 212 */       this.msgs.add("商品添加成功");
/*     */ 
/* 216 */       this.blankUrls.put("查看此商品", "../../goods-" + this.goods.getGoods_id() + ".html");
/* 217 */       this.urls.put("修改此商品", "goods!edit.do?goods_id=" + this.goods.getGoods_id());
/* 218 */       this.urls.put("继续添加此分类商品", "goods!add.do?catid=" + this.catid);
/* 219 */       this.urls.put("此分类商品列表", "goods!list.do?catid=" + this.catid);
/*     */     }
/*     */     catch (RuntimeException e)
/*     */     {
/* 223 */       this.logger.error("添加商品出错", e);
/* 224 */       this.msgs.add(e.getMessage());
/*     */     }
/*     */ 
/* 227 */     return "message";
/*     */   }
/*     */ 
/*     */   public String saveEdit()
/*     */   {
/*     */     try {
/* 233 */       this.goodsManager.edit(this.goods);
/* 234 */       this.msgs.add("商品修改成功");
/*     */ 
/* 238 */       this.blankUrls.put("查看此商品", "../../goods-" + this.goods.getGoods_id() + ".html");
/* 239 */       this.urls.put("修改此商品", "goods!edit.do?goods_id=" + this.goods.getGoods_id());
/* 240 */       this.urls.put("继续添加此分类商品", "goods!add.do?catid=" + this.goods.getCat_id());
/* 241 */       this.urls.put("此分类商品列表", "goods!list.do?catid=" + this.goods.getCat_id());
/*     */     }
/*     */     catch (RuntimeException e)
/*     */     {
/* 246 */       this.logger.error("修改商品出错", e);
/* 247 */       this.msgs.add(e.getMessage());
/*     */     }
/* 249 */     return "message";
/*     */   }
/*     */ 
/*     */   public String updateMarketEnable()
/*     */   {
/*     */     try {
/* 255 */       this.goodsManager.updateField("market_enable", Integer.valueOf(1), this.goods_id);
/* 256 */       showSuccessJson("更新上架状态成功");
/*     */     } catch (RuntimeException e) {
/* 258 */       showErrorJson("更新上架状态失败");
/*     */     }
/* 260 */     return "json_message";
/*     */   }
/*     */ 
/*     */   public String selector()
/*     */   {
/* 266 */     return "selector";
/*     */   }
/*     */ 
/*     */   public List getCatList() {
/* 270 */     return this.catList;
/*     */   }
/*     */ 
/*     */   public void setCatList(List catList) {
/* 274 */     this.catList = catList;
/*     */   }
/*     */ 
/*     */   public void setGoodsCatManager(IGoodsCatManager goodsCatManager)
/*     */   {
/* 280 */     this.goodsCatManager = goodsCatManager;
/*     */   }
/*     */ 
/*     */   public void setGoods(Goods goods) {
/* 284 */     this.goods = goods;
/*     */   }
/*     */ 
/*     */   public IGoodsManager getGoodsManager()
/*     */   {
/* 289 */     return this.goodsManager;
/*     */   }
/*     */ 
/*     */   public void setGoodsManager(IGoodsManager goodsManager)
/*     */   {
/* 294 */     this.goodsManager = goodsManager;
/*     */   }
/*     */ 
/*     */   public Goods getGoods()
/*     */   {
/* 300 */     return this.goods;
/*     */   }
/*     */ 
/*     */   public Integer getGoods_id()
/*     */   {
/* 305 */     return this.goods_id;
/*     */   }
/*     */ 
/*     */   public void setGoods_id(Integer goods_id)
/*     */   {
/* 310 */     this.goods_id = goods_id;
/*     */   }
/*     */ 
/*     */   public Map getGoodsView()
/*     */   {
/* 315 */     return this.goodsView;
/*     */   }
/*     */ 
/*     */   public void setGoodsView(Map goodsView)
/*     */   {
/* 320 */     this.goodsView = goodsView;
/*     */   }
/*     */ 
/*     */   public Boolean getIs_edit()
/*     */   {
/* 325 */     return this.is_edit;
/*     */   }
/*     */ 
/*     */   public void setIs_edit(Boolean is_edit)
/*     */   {
/* 330 */     this.is_edit = is_edit;
/*     */   }
/*     */ 
/*     */   public String getActionName()
/*     */   {
/* 335 */     return this.actionName;
/*     */   }
/*     */ 
/*     */   public void setActionName(String actionName)
/*     */   {
/* 340 */     this.actionName = actionName;
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/* 348 */     return this.name;
/*     */   }
/*     */   public void setName(String name) {
/* 351 */     this.name = name;
/*     */   }
/*     */   public String getOrder() {
/* 354 */     return this.order;
/*     */   }
/*     */   public void setOrder(String order) {
/* 357 */     this.order = order;
/*     */   }
/*     */   public String getSn() {
/* 360 */     return this.sn;
/*     */   }
/*     */   public void setSn(String sn) {
/* 363 */     this.sn = sn;
/*     */   }
/*     */ 
/*     */   public Integer[] getId()
/*     */   {
/* 368 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(Integer[] id)
/*     */   {
/* 373 */     this.id = id;
/*     */   }
/*     */ 
/*     */   public GoodsPluginBundle getGoodsPluginBundle() {
/* 377 */     return this.goodsPluginBundle;
/*     */   }
/*     */ 
/*     */   public void setGoodsPluginBundle(GoodsPluginBundle goodsPluginBundle) {
/* 381 */     this.goodsPluginBundle = goodsPluginBundle;
/*     */   }
/*     */ 
/*     */   public List<String> getTagHtmlList() {
/* 385 */     return this.tagHtmlList;
/*     */   }
/*     */ 
/*     */   public void setTagHtmlList(List<String> tagHtmlList) {
/* 389 */     this.tagHtmlList = tagHtmlList;
/*     */   }
/*     */ 
/*     */   public Integer getCatid() {
/* 393 */     return this.catid;
/*     */   }
/*     */ 
/*     */   public void setCatid(Integer catid) {
/* 397 */     this.catid = catid;
/*     */   }
/*     */ 
/*     */   public Integer getMarket_enable() {
/* 401 */     return this.market_enable;
/*     */   }
/*     */ 
/*     */   public void setMarket_enable(Integer marketEnable) {
/* 405 */     this.market_enable = marketEnable;
/*     */   }
/*     */ 
/*     */   public Integer[] getTagids() {
/* 409 */     return this.tagids;
/*     */   }
/*     */ 
/*     */   public void setTagids(Integer[] tagids) {
/* 413 */     this.tagids = tagids;
/*     */   }
/*     */ 
/*     */   public ITagManager getTagManager() {
/* 417 */     return this.tagManager;
/*     */   }
/*     */ 
/*     */   public void setTagManager(ITagManager tagManager) {
/* 421 */     this.tagManager = tagManager;
/*     */   }
/*     */ 
/*     */   public IProductManager getProductManager()
/*     */   {
/* 427 */     return this.productManager;
/*     */   }
/*     */ 
/*     */   public void setProductManager(IProductManager productManager) {
/* 431 */     this.productManager = productManager;
/*     */   }
/*     */ 
/*     */   public IPermissionManager getPermissionManager() {
/* 435 */     return this.permissionManager;
/*     */   }
/*     */ 
/*     */   public void setPermissionManager(IPermissionManager permissionManager) {
/* 439 */     this.permissionManager = permissionManager;
/*     */   }
/*     */ 
/*     */   public IAdminUserManager getAdminUserManager() {
/* 443 */     return this.adminUserManager;
/*     */   }
/*     */ 
/*     */   public void setAdminUserManager(IAdminUserManager adminUserManager) {
/* 447 */     this.adminUserManager = adminUserManager;
/*     */   }
/*     */ 
/*     */   public String getOptype()
/*     */   {
/* 453 */     return this.optype;
/*     */   }
/*     */ 
/*     */   public void setOptype(String optype) {
/* 457 */     this.optype = optype;
/*     */   }
/*     */ 
/*     */   public int getDepotid() {
/* 461 */     return this.depotid;
/*     */   }
/*     */ 
/*     */   public void setDepotid(int depotid) {
/* 465 */     this.depotid = depotid;
/*     */   }
/*     */ 
/*     */   public List getBrandList() {
/* 469 */     return this.brandList;
/*     */   }
/*     */ 
/*     */   public void setBrandList(List brandList) {
/* 473 */     this.brandList = brandList;
/*     */   }
/*     */ 
/*     */   public IBrandManager getBrandManager() {
/* 477 */     return this.brandManager;
/*     */   }
/*     */ 
/*     */   public void setBrandManager(IBrandManager brandManager) {
/* 481 */     this.brandManager = brandManager;
/*     */   }
/*     */ 
/*     */   public IGoodsCatManager getGoodsCatManager() {
/* 485 */     return this.goodsCatManager;
/*     */   }
/*     */ 
/*     */   public Integer getBrand_id()
/*     */   {
/* 490 */     return this.brand_id;
/*     */   }
/*     */ 
/*     */   public void setBrand_id(Integer brand_id) {
/* 494 */     this.brand_id = brand_id;
/*     */   }
/*     */ 
/*     */   public Integer getIs_market() {
/* 498 */     return this.is_market;
/*     */   }
/*     */ 
/*     */   public void setIs_market(Integer is_market) {
/* 502 */     this.is_market = is_market;
/*     */   }
/*     */ 
/*     */   public String getIs_other() {
/* 506 */     return this.is_other;
/*     */   }
/*     */ 
/*     */   public void setIs_other(String is_other) {
/* 510 */     this.is_other = is_other;
/*     */   }
/*     */ 
/*     */   public List<Tag> getTagList()
/*     */   {
/* 516 */     return this.tagList;
/*     */   }
/*     */ 
/*     */   public void setTagList(List<Tag> tagList) {
/* 520 */     this.tagList = tagList;
/*     */   }
/*     */ 
/*     */   public Map<Integer, String> getPluginTabs() {
/* 524 */     return this.pluginTabs;
/*     */   }
/*     */ 
/*     */   public void setPluginTabs(Map<Integer, String> pluginTabs) {
/* 528 */     this.pluginTabs = pluginTabs;
/*     */   }
/*     */ 
/*     */   public Map<Integer, String> getPluginHtmls() {
/* 532 */     return this.pluginHtmls;
/*     */   }
/*     */ 
/*     */   public void setPluginHtmls(Map<Integer, String> pluginHtmls) {
/* 536 */     this.pluginHtmls = pluginHtmls;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.action.backend.GoodsAction
 * JD-Core Version:    0.6.0
 */