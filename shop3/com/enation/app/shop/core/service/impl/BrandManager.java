/*     */ package com.enation.app.shop.core.service.impl;
/*     */ 
/*     */ import com.enation.app.shop.core.model.Brand;
/*     */ import com.enation.app.shop.core.model.mapper.BrandMapper;
/*     */ import com.enation.app.shop.core.service.IBrandManager;
/*     */ import com.enation.app.shop.core.service.IGoodsCatManager;
/*     */ import com.enation.eop.sdk.database.BaseSupport;
/*     */ import com.enation.eop.sdk.utils.UploadUtil;
/*     */ import com.enation.framework.database.IDaoSupport;
/*     */ import com.enation.framework.database.Page;
/*     */ import com.enation.framework.database.StringMapper;
/*     */ import com.enation.framework.util.FileUtil;
/*     */ import com.enation.framework.util.StringUtil;
/*     */ import java.io.File;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class BrandManager extends BaseSupport<Brand>
/*     */   implements IBrandManager
/*     */ {
/*     */   private IGoodsCatManager goodsCatManager;
/*     */ 
/*     */   public Page list(String order, int page, int pageSize)
/*     */   {
/*  35 */     order = order == null ? " brand_id desc" : order;
/*  36 */     String sql = "select * from brand where disabled=0";
/*  37 */     sql = sql + " order by  " + order;
/*  38 */     Page webpage = this.baseDaoSupport.queryForPage(sql, page, pageSize, new Object[0]);
/*  39 */     return webpage;
/*     */   }
/*     */ 
/*     */   public List<Map> queryAllTypeNameAndId()
/*     */   {
/*  50 */     String sql = "SELECT type_id,name FROM goods_type";
/*  51 */     return this.baseDaoSupport.queryForList(sql, new Object[0]);
/*     */   }
/*     */ 
/*     */   public Page search(int page, int pageSize, String brandname, Integer type_id)
/*     */   {
/*  64 */     Page webpage = null;
/*  65 */     String sql = "SELECT distinct b.* FROM " + getTableName("brand") + " b left  join " + getTableName("type_brand") + " tb on b.brand_id=tb.brand_id left  join " + getTableName("goods_type") + " gt on tb.type_id = gt.type_id where b.name  like '%" + brandname + "%' ";
/*  66 */     if (type_id.intValue() != -100) {
/*  67 */       sql = sql + "  and gt.type_id = ? ";
/*  68 */       webpage = this.daoSupport.queryForPage(sql, page, pageSize, new Object[] { type_id });
/*     */     } else {
/*  70 */       webpage = this.daoSupport.queryForPage(sql, page, pageSize, new Object[0]);
/*     */     }
/*  72 */     return webpage;
/*     */   }
/*     */ 
/*     */   public Page listTrash(String order, int page, int pageSize)
/*     */   {
/*  84 */     order = order == null ? " brand_id desc" : order;
/*  85 */     String sql = "select * from brand where disabled=1";
/*  86 */     sql = sql + " order by  " + order;
/*  87 */     Page webpage = this.baseDaoSupport.queryForPage(sql, page, pageSize, new Object[0]);
/*  88 */     return webpage;
/*     */   }
/*     */ 
/*     */   public void revert(String bid)
/*     */   {
/*  98 */     if ((bid == null) || (bid.equals(""))) {
/*  99 */       return;
/*     */     }
/* 101 */     String sql = "update brand set disabled=0  where brand_id in (" + bid + ")";
/*     */ 
/* 103 */     this.baseDaoSupport.execute(sql, new Object[0]);
/*     */   }
/*     */ 
/*     */   public boolean checkUsed(String ids) {
/* 107 */     if ((ids == null) || (ids.equals(""))) return false;
/* 108 */     String sql = "select count(0) from goods where brand_id in (" + ids + ")";
/* 109 */     int count = this.baseDaoSupport.queryForInt(sql, new Object[0]);
/*     */ 
/* 111 */     return count > 0;
/*     */   }
/*     */ 
/*     */   public void delete(String bid)
/*     */   {
/* 124 */     if ((bid == null) || (bid.equals("")))
/* 125 */       return;
/* 126 */     String sql = "update brand set disabled=1  where brand_id in (" + bid + ")";
/*     */ 
/* 128 */     this.baseDaoSupport.execute(sql, new Object[0]);
/*     */   }
/*     */ 
/*     */   public void clean(String bid)
/*     */   {
/* 139 */     if ((bid == null) || (bid.equals("")))
/* 140 */       return;
/* 141 */     String[] bids = bid.split(",");
/*     */ 
/* 143 */     for (int i = 0; i < bids.length; i++) {
/* 144 */       int brand_id = Integer.parseInt(bids[i].trim());
/* 145 */       Brand brand = get(Integer.valueOf(brand_id));
/* 146 */       if (brand != null) {
/* 147 */         String f = brand.getLogo();
/* 148 */         if ((f != null) && (!f.trim().equals(""))) {
/* 149 */           File file = new File(StringUtil.getRootPath() + "/" + f);
/* 150 */           file.delete();
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 155 */     String sql = "delete  from  brand   where brand_id in (" + bid + ")";
/* 156 */     this.baseDaoSupport.execute(sql, new Object[0]);
/*     */   }
/*     */ 
/*     */   private String getThumbpath(String file) {
/* 160 */     String fStr = "";
/* 161 */     if (!file.trim().equals("")) {
/* 162 */       String[] arr = file.split("/");
/* 163 */       fStr = "/" + arr[0] + "/" + arr[1] + "/thumb/" + arr[2];
/*     */     }
/* 165 */     return fStr;
/*     */   }
/*     */ 
/*     */   public List<Brand> list()
/*     */   {
/* 175 */     String sql = "select * from brand where disabled=0";
/* 176 */     List list = this.baseDaoSupport.queryForList(sql, new BrandMapper(), new Object[0]);
/* 177 */     return list;
/*     */   }
/*     */ 
/*     */   public List<Brand> listByTypeId(Integer typeid)
/*     */   {
/* 182 */     String sql = "select b.* from " + getTableName("type_brand") + " tb inner join " + getTableName("brand") + " b  on    b.brand_id = tb.brand_id where tb.type_id=? and b.disabled=0";
/* 183 */     List list = this.daoSupport.queryForList(sql, new BrandMapper(), new Object[] { typeid });
/* 184 */     return list;
/*     */   }
/*     */ 
/*     */   public Brand get(Integer brand_id)
/*     */   {
/* 197 */     String sql = "select * from brand where brand_id=?";
/* 198 */     Brand brand = (Brand)this.baseDaoSupport.queryForObject(sql, Brand.class, new Object[] { brand_id });
/*     */ 
/* 200 */     String logo = brand.getLogo();
/* 201 */     if (logo != null) {
/* 202 */       logo = UploadUtil.replacePath(logo);
/*     */     }
/* 204 */     brand.setLogo(logo);
/* 205 */     return brand;
/*     */   }
/*     */ 
/*     */   public Page getGoods(Integer brand_id, int pageNo, int pageSize)
/*     */   {
/* 216 */     String sql = "select * from goods where brand_id=? and disabled=0";
/* 217 */     Page page = this.baseDaoSupport.queryForPage(sql, pageNo, pageSize, new Object[] { brand_id });
/*     */ 
/* 219 */     return page;
/*     */   }
/*     */ 
/*     */   public void add(Brand brand)
/*     */   {
/* 225 */     if ((brand.getFile() != null) && (brand.getFileFileName() != null)) {
/* 226 */       brand.setLogo(UploadUtil.upload(brand.getFile(), brand.getFileFileName(), "brand"));
/*     */     }
/* 228 */     this.baseDaoSupport.insert("brand", brand);
/*     */   }
/*     */ 
/*     */   private void deleteOldLogo(String logo)
/*     */   {
/* 235 */     if (!logo.equals("http://static.enationsfot.com")) {
/* 236 */       logo = UploadUtil.replacePath(logo);
/* 237 */       FileUtil.delete(logo);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void update(Brand brand)
/*     */   {
/* 245 */     if ((brand.getLogo() != null) && ("".equals(brand.getLogo()))) {
/* 246 */       deleteOldLogo(brand.getLogo());
/*     */     }
/* 248 */     if ((brand.getFile() != null) && (brand.getFileFileName() != null)) {
/* 249 */       brand.setLogo(UploadUtil.upload(brand.getFile(), brand.getFileFileName(), "brand"));
/*     */     }
/* 251 */     this.baseDaoSupport.update("brand", brand, "brand_id=" + brand.getBrand_id());
/*     */   }
/*     */ 
/*     */   public List<Brand> listByCatId(Integer catid)
/*     */   {
/* 257 */     String sql = "select b.* from " + getTableName("brand") + " b ," + getTableName("type_brand") + " tb," + getTableName("goods_cat") + " c where tb.brand_id=b.brand_id and c.type_id=tb.type_id and c.cat_id=?";
/* 258 */     return this.daoSupport.queryForList(sql, Brand.class, new Object[] { catid });
/*     */   }
/*     */ 
/*     */   public List groupByCat()
/*     */   {
/* 263 */     List listCat = this.baseDaoSupport.queryForList("select * from goods_cat where parent_id = 0 order by cat_order", new Object[0]);
/* 264 */     for (Map map : listCat) {
/* 265 */       List list = this.baseDaoSupport.queryForList("select type_id from goods_cat where cat_path like '" + map.get("cat_path").toString() + "%'", new StringMapper(), new Object[0]);
/* 266 */       String types = StringUtil.listToString(list, ",");
/* 267 */       List listid = this.baseDaoSupport.queryForList("select brand_id from type_brand where type_id in (" + types + ")", new StringMapper(), new Object[0]);
/* 268 */       String ids = StringUtil.listToString(listid, ",");
/* 269 */       List listBrand = this.baseDaoSupport.queryForList("select * from brand where brand_id in (" + ids + ")", Brand.class, new Object[0]);
/* 270 */       map.put("listBrand", listBrand);
/*     */     }
/* 272 */     return listCat;
/*     */   }
/*     */ 
/*     */   public boolean checkname(String name, Integer brandid) {
/* 276 */     if (name != null) name = name.trim();
/* 277 */     String sql = "select count(0) from brand where name=? and brand_id!=?";
/* 278 */     if (brandid == null) brandid = Integer.valueOf(0);
/*     */ 
/* 280 */     int count = this.baseDaoSupport.queryForInt(sql, new Object[] { name, brandid });
/*     */ 
/* 282 */     return count > 0;
/*     */   }
/*     */ 
/*     */   public IGoodsCatManager getGoodsCatManager()
/*     */   {
/* 288 */     return this.goodsCatManager;
/*     */   }
/*     */ 
/*     */   public void setGoodsCatManager(IGoodsCatManager goodsCatManager) {
/* 292 */     this.goodsCatManager = goodsCatManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.service.impl.BrandManager
 * JD-Core Version:    0.6.0
 */