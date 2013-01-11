/*     */ package com.enation.app.shop.core.action.backend;
/*     */ 
/*     */ import com.enation.app.shop.core.model.Tag;
/*     */ import com.enation.app.shop.core.service.IGoodsManager;
/*     */ import com.enation.app.shop.core.service.IGoodsTagManager;
/*     */ import com.enation.app.shop.core.service.ITagManager;
/*     */ import com.enation.eop.processor.httpcache.HttpCacheManager;
/*     */ import com.enation.eop.sdk.context.EopSetting;
/*     */ import com.enation.framework.action.WWAction;
/*     */ import com.enation.framework.util.StringUtil;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class GoodsShowAction extends WWAction
/*     */ {
/*     */   protected String name;
/*     */   protected String sn;
/*     */   protected String order;
/*     */   private Integer catid;
/*     */   protected Integer[] id;
/*     */   private Integer[] tagids;
/*     */   private Integer[] ordernum;
/*  29 */   protected Integer market_enable = new Integer(1);
/*     */   protected IGoodsManager goodsManager;
/*     */   protected ITagManager tagManager;
/*     */   private Tag tag;
/*     */   private int tagid;
/*     */   private int goodsid;
/*     */   private List<Tag> taglist;
/*     */   private IGoodsTagManager goodsTagManager;
/*     */ 
/*     */   public String taglist()
/*     */   {
/*  42 */     this.taglist = this.tagManager.list();
/*  43 */     return "taglist";
/*     */   }
/*     */ 
/*     */   public String execute()
/*     */   {
/*  49 */     this.tag = this.tagManager.getById(Integer.valueOf(this.tagid));
/*  50 */     if ((this.catid == null) || (this.catid.intValue() == 0)) {
/*  51 */       this.webpage = this.goodsTagManager.getGoodsList(this.tagid, getPage(), getPageSize());
/*     */     }
/*     */     else {
/*  54 */       this.webpage = this.goodsTagManager.getGoodsList(this.tagid, this.catid.intValue(), getPage(), getPageSize());
/*     */     }
/*     */ 
/*  57 */     return "list";
/*     */   }
/*     */ 
/*     */   public String add()
/*     */   {
/*  66 */     this.tag = this.tagManager.getById(Integer.valueOf(this.tagid));
/*  67 */     return "add";
/*     */   }
/*     */ 
/*     */   public String search()
/*     */   {
/*  76 */     this.tag = this.tagManager.getById(Integer.valueOf(this.tagid));
/*  77 */     if (this.name != null) {
/*  78 */       String encoding = EopSetting.ENCODING;
/*  79 */       if (!StringUtil.isEmpty(encoding)) {
/*  80 */         this.name = StringUtil.to(this.name, encoding);
/*     */       }
/*     */     }
/*  83 */     this.webpage = this.goodsManager.searchGoods(null, null, this.catid, this.name, this.sn, this.market_enable, null, this.order, getPage(), getPageSize());
/*     */ 
/*  85 */     return "search_list";
/*     */   }
/*     */ 
/*     */   public String batchAdd()
/*     */   {
/*     */     try
/*     */     {
/*  95 */       if ((this.id != null) && (this.id.length > 0)) {
/*  96 */         for (Integer goodsId : this.id) {
/*  97 */           this.goodsTagManager.addTag(this.tagid, goodsId.intValue());
/*     */         }
/*     */       }
/* 100 */       updateHttpCache();
/*     */ 
/* 102 */       this.json = "{'result':0,'message':'添加成功！'}";
/*     */     } catch (RuntimeException e) {
/* 104 */       this.json = "{'result':1,'message':'添加失败！'}";
/*     */     }
/* 106 */     return "json_message";
/*     */   }
/*     */ 
/*     */   public String delete()
/*     */   {
/*     */     try
/*     */     {
/* 116 */       this.goodsTagManager.removeTag(this.tagid, this.goodsid);
/* 117 */       int tempCatId = this.catid == null ? 0 : this.catid.intValue();
/* 118 */       updateHttpCache();
/* 119 */       this.msgs.add("删除成功");
/* 120 */       this.urls.put("列表页面", "goodsShow.do?tagid=" + this.tagid + "&catid=" + tempCatId);
/*     */     }
/*     */     catch (RuntimeException e) {
/* 123 */       e.printStackTrace();
/* 124 */       this.msgs.add(e.getMessage());
/*     */     }
/* 126 */     return "message";
/*     */   }
/*     */ 
/*     */   public String saveOrdernum()
/*     */   {
/*     */     try
/*     */     {
/* 136 */       this.goodsTagManager.updateOrderNum(this.id, this.tagids, this.ordernum);
/* 137 */       int tempCatId = this.catid == null ? 0 : this.catid.intValue();
/* 138 */       updateHttpCache();
/* 139 */       this.msgs.add("保存排序成功");
/* 140 */       this.urls.put("列表页面", "goodsShow.do?tagid=" + this.tagid + "&catid=" + tempCatId);
/*     */     }
/*     */     catch (RuntimeException e) {
/* 143 */       e.printStackTrace();
/* 144 */       this.msgs.add(e.getMessage());
/*     */     }
/* 146 */     return "message";
/*     */   }
/*     */ 
/*     */   private void updateHttpCache()
/*     */   {
/* 151 */     HttpCacheManager.updateUriModified("/");
/* 152 */     HttpCacheManager.updateUriModified("/index.html");
/* 153 */     HttpCacheManager.updateUriModified("/search-(.*).html");
/*     */   }
/*     */ 
/*     */   public void setGoodsTagManager(IGoodsTagManager goodsTagManager) {
/* 157 */     this.goodsTagManager = goodsTagManager;
/*     */   }
/*     */ 
/*     */   public String getName() {
/* 161 */     return this.name;
/*     */   }
/*     */ 
/*     */   public void setName(String name) {
/* 165 */     this.name = name;
/*     */   }
/*     */ 
/*     */   public String getSn() {
/* 169 */     return this.sn;
/*     */   }
/*     */ 
/*     */   public void setSn(String sn) {
/* 173 */     this.sn = sn;
/*     */   }
/*     */ 
/*     */   public String getOrder() {
/* 177 */     return this.order;
/*     */   }
/*     */ 
/*     */   public void setOrder(String order) {
/* 181 */     this.order = order;
/*     */   }
/*     */ 
/*     */   public Integer getCatid() {
/* 185 */     return this.catid;
/*     */   }
/*     */ 
/*     */   public void setCatid(Integer catid) {
/* 189 */     this.catid = catid;
/*     */   }
/*     */ 
/*     */   public Integer[] getId() {
/* 193 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(Integer[] id) {
/* 197 */     this.id = id;
/*     */   }
/*     */ 
/*     */   public Integer[] getTagids() {
/* 201 */     return this.tagids;
/*     */   }
/*     */ 
/*     */   public void setTagids(Integer[] tagids) {
/* 205 */     this.tagids = tagids;
/*     */   }
/*     */ 
/*     */   public int getTagid() {
/* 209 */     return this.tagid;
/*     */   }
/*     */ 
/*     */   public void setTagid(int tagid) {
/* 213 */     this.tagid = tagid;
/*     */   }
/*     */ 
/*     */   public void setGoodsManager(IGoodsManager goodsManager) {
/* 217 */     this.goodsManager = goodsManager;
/*     */   }
/*     */ 
/*     */   public void setTagManager(ITagManager tagManager) {
/* 221 */     this.tagManager = tagManager;
/*     */   }
/*     */ 
/*     */   public Tag getTag() {
/* 225 */     return this.tag;
/*     */   }
/*     */ 
/*     */   public void setTag(Tag tag) {
/* 229 */     this.tag = tag;
/*     */   }
/*     */ 
/*     */   public Integer[] getOrdernum() {
/* 233 */     return this.ordernum;
/*     */   }
/*     */ 
/*     */   public void setOrdernum(Integer[] ordernum) {
/* 237 */     this.ordernum = ordernum;
/*     */   }
/*     */ 
/*     */   public int getGoodsid() {
/* 241 */     return this.goodsid;
/*     */   }
/*     */ 
/*     */   public void setGoodsid(int goodsid) {
/* 245 */     this.goodsid = goodsid;
/*     */   }
/*     */   public List<Tag> getTaglist() {
/* 248 */     return this.taglist;
/*     */   }
/*     */   public void setTaglist(List<Tag> taglist) {
/* 251 */     this.taglist = taglist;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.action.backend.GoodsShowAction
 * JD-Core Version:    0.6.0
 */