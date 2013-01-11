/*     */ package com.enation.app.shop.core.service.impl;
/*     */ 
/*     */ import com.enation.app.base.core.model.Member;
/*     */ import com.enation.app.base.core.model.MemberLv;
/*     */ import com.enation.app.shop.core.model.Attribute;
/*     */ import com.enation.app.shop.core.model.GoodsLvPrice;
/*     */ import com.enation.app.shop.core.model.support.GoodsView;
/*     */ import com.enation.app.shop.core.service.IGoodsSearchManager;
/*     */ import com.enation.app.shop.core.service.IGoodsTypeManager;
/*     */ import com.enation.app.shop.core.service.IMemberLvManager;
/*     */ import com.enation.app.shop.core.service.IMemberPriceManager;
/*     */ import com.enation.eop.sdk.database.BaseSupport;
/*     */ import com.enation.eop.sdk.user.IUserService;
/*     */ import com.enation.eop.sdk.user.UserServiceFactory;
/*     */ import com.enation.eop.sdk.utils.UploadUtil;
/*     */ import com.enation.framework.database.IDaoSupport;
/*     */ import com.enation.framework.database.Page;
/*     */ import com.enation.framework.database.StringMapper;
/*     */ import com.enation.framework.util.StringUtil;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.springframework.jdbc.core.RowMapper;
/*     */ 
/*     */ public class GoodsSearchManager extends BaseSupport
/*     */   implements IGoodsSearchManager
/*     */ {
/*     */   private IMemberPriceManager memberPriceManager;
/*     */   private IMemberLvManager memberLvManager;
/*     */   private IGoodsTypeManager goodsTypeManager;
/*     */ 
/*     */   public Page search(int page, int pageSize, Map<String, String> params)
/*     */   {
/*  37 */     String cat_path = (String)params.get("cat_path");
/*  38 */     String order = (String)params.get("order");
/*  39 */     String brandStr = (String)params.get("brandStr");
/*  40 */     String propStr = (String)params.get("propStr");
/*  41 */     String keyword = (String)params.get("keyword");
/*  42 */     String minPrice = (String)params.get("minPrice");
/*  43 */     String maxPrice = (String)params.get("maxPrice");
/*  44 */     String tagids = (String)params.get("tagids");
/*  45 */     String attrStr = (String)params.get("attrStr");
/*     */ 
/*  47 */     int typeid = Integer.valueOf((String)params.get("typeid")).intValue();
/*     */ 
/*  49 */     List list = listByCatId(typeid, cat_path, page, pageSize, order, brandStr, propStr, keyword, minPrice, maxPrice, tagids, attrStr);
/*     */ 
/*  51 */     long count = countByCatId(typeid, cat_path, brandStr, propStr, keyword, minPrice, maxPrice, tagids, attrStr);
/*     */ 
/*  54 */     Page webPage = new Page(0L, count, pageSize, list);
/*  55 */     return webPage;
/*     */   }
/*     */ 
/*     */   public List[] getPropListByCat(int type_id, String cat_path, String brand_str, String propStr, String attrStr)
/*     */   {
/*  63 */     List temp_prop_list = this.goodsTypeManager.getAttrListByTypeId(type_id);
/*     */ 
/*  65 */     temp_prop_list = temp_prop_list == null ? new ArrayList() : temp_prop_list;
/*     */ 
/*  67 */     if ((propStr != null) && (!propStr.equals(""))) {
/*  68 */       String[] s_ar = propStr.split(",");
/*     */ 
/*  70 */       for (int i = 0; i < s_ar.length; i++) {
/*  71 */         String[] value = s_ar[i].split("\\_");
/*  72 */         int index = Integer.valueOf(value[0]).intValue();
/*  73 */         Attribute attr = (Attribute)temp_prop_list.get(index);
/*  74 */         attr.getOptionMap()[Integer.valueOf(value[1]).intValue()].put("selected", Integer.valueOf(1));
/*  75 */         if (attr.getType() == 3)
/*     */         {
/*  77 */           attr.setHidden(1);
/*     */         }
/*  79 */         else attr.setHidden(0);
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/*  86 */     List temp_brand_list = null;
/*     */ 
/*  88 */     temp_brand_list = this.goodsTypeManager.getBrandListByTypeId(type_id);
/*     */ 
/*  91 */     List propList = temp_prop_list;
/*  92 */     List brandList = temp_brand_list;
/*     */ 
/*  94 */     String sql = "select g.* from " + getTableName("goods") + " g where g.disabled=0 and g.market_enable=1 and g.cat_id in(";
/*  95 */     sql = sql + "select c.cat_id from " + getTableName("goods_cat") + " c where c.cat_path like '" + cat_path + "%')";
/*     */ 
/*  98 */     sql = sql + buildTermForByCat(type_id, brand_str, propStr, attrStr);
/*     */ 
/* 100 */     RowMapper mapper = new Object(type_id, propList, brandList)
/*     */     {
/*     */       public Object mapRow(ResultSet rs, int arg1) throws SQLException
/*     */       {
/* 104 */         GoodsView goods = new GoodsView();
/*     */ 
/* 106 */         if (rs.getInt("type_id") == this.val$type_id) {
/* 107 */           for (int i = 0; i < 20; i++)
/*     */           {
/* 109 */             if (i >= this.val$propList.size()) {
/*     */               break;
/*     */             }
/* 112 */             String value = rs.getString("p" + (i + 1));
/*     */ 
/* 114 */             Attribute prop = (Attribute)this.val$propList.get(i);
/*     */ 
/* 116 */             if ((prop.getType() != 3) || (value == null) || (value.toString().equals("")))
/*     */               continue;
/* 118 */             int[] nums = prop.getNums();
/* 119 */             int pos = Integer.valueOf(value).intValue();
/* 120 */             nums[pos] += 1;
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 126 */         for (int i = 0; i < this.val$brandList.size(); i++) {
/* 127 */           Map brand = (Map)this.val$brandList.get(i);
/* 128 */           if (rs.getInt("brand_id") != ((Integer)brand.get("brand_id")).intValue()) {
/*     */             continue;
/*     */           }
/* 131 */           Object obj_num = brand.get("num");
/* 132 */           if (obj_num == null) {
/* 133 */             obj_num = Integer.valueOf(0);
/*     */           }
/*     */ 
/* 136 */           int num = Integer.valueOf(obj_num.toString()).intValue();
/* 137 */           num++;
/* 138 */           brand.put("num", Integer.valueOf(num));
/*     */         }
/*     */ 
/* 142 */         return goods;
/*     */       }
/*     */     };
/* 147 */     this.daoSupport.queryForList(sql, mapper, new Object[0]);
/*     */ 
/* 150 */     List[] props = new List[2];
/* 151 */     props[0] = propList;
/* 152 */     props[1] = brandList;
/*     */ 
/* 154 */     return props;
/*     */   }
/*     */ 
/*     */   private List getSpecListByCatId(String cat_path)
/*     */   {
/* 165 */     String sql = "select s.* from " + getTableName("product") + " s," + getTableName("goods") + " g  where s.goods_id=g.goods_id  ";
/*     */ 
/* 169 */     if (!StringUtil.isEmpty(cat_path)) {
/* 170 */       sql = sql + " and g.cat_id in(";
/* 171 */       sql = sql + "select c.cat_id from " + getTableName("goods_cat") + " c where c.cat_path like '" + cat_path + "%')";
/*     */     }
/*     */ 
/* 175 */     List specList = this.daoSupport.queryForList(sql, new Object[0]);
/*     */ 
/* 177 */     return specList;
/*     */   }
/*     */ 
/*     */   private List listByCatId(int typeid, String cat_path, int page, int pageSize, String order, String brand_str, String propStr, String keyword, String minPrice, String maxPrice, String tagids, String attrStr)
/*     */   {
/* 201 */     List goods_spec_list = getSpecListByCatId(cat_path);
/*     */ 
/* 203 */     if ("1".equals(order))
/* 204 */       order = "last_modify desc";
/* 205 */     else if ("1".equals(order))
/* 206 */       order = "last_modify asc";
/* 207 */     else if ("2".equals(order))
/* 208 */       order = "last_modify asc";
/* 209 */     else if ("3".equals(order))
/* 210 */       order = "price desc";
/* 211 */     else if ("4".equals(order))
/* 212 */       order = "price asc";
/* 213 */     else if ("5".equals(order))
/* 214 */       order = "view_count desc";
/* 215 */     else if ("6".equals(order))
/* 216 */       order = "buy_count asc";
/* 217 */     else if ((order == null) || (order.equals("")) || (order.equals("0"))) {
/* 218 */       order = "sord desc";
/*     */     }
/*     */ 
/* 221 */     String sql = "select g.* from " + getTableName("goods") + " g where g.goods_type = 'normal' and g.disabled=0 and market_enable=1 ";
/*     */ 
/* 224 */     if (cat_path != null) {
/* 225 */       sql = sql + " and  g.cat_id in(";
/* 226 */       sql = sql + "select c.cat_id from " + getTableName("goods_cat") + " c where c.cat_path like '" + cat_path + "%')  ";
/*     */     }
/*     */ 
/* 230 */     sql = sql + buildTermForByCat(typeid, brand_str, propStr, keyword, minPrice, maxPrice, tagids, attrStr);
/*     */ 
/* 233 */     sql = sql + " order by " + order;
/*     */ 
/* 237 */     IUserService userService = UserServiceFactory.getUserService();
/* 238 */     Member member = userService.getCurrentMember();
/* 239 */     List memPriceList = new ArrayList();
/* 240 */     double discount = 1.0D;
/* 241 */     if ((member != null) && (member.getLv_id() != null)) {
/* 242 */       memPriceList = this.memberPriceManager.listPriceByLvid(member.getLv_id().intValue());
/* 243 */       MemberLv lv = this.memberLvManager.get(member.getLv_id());
/* 244 */       discount = lv.getDiscount().intValue() / 100.0D;
/* 245 */       applyMemPrice(goods_spec_list, memPriceList, discount);
/*     */     }
/* 247 */     List priceList = memPriceList;
/* 248 */     double final_discount = discount;
/*     */ 
/* 251 */     RowMapper mapper = new RowMapper(goods_spec_list, member, final_discount, priceList)
/*     */     {
/*     */       public Object mapRow(ResultSet rs, int arg1) throws SQLException
/*     */       {
/* 255 */         GoodsView goods = new GoodsView();
/* 256 */         goods.setName(rs.getString("name"));
/* 257 */         goods.setGoods_id(Integer.valueOf(rs.getInt("goods_id")));
/* 258 */         goods.setImage_default(rs.getString("image_default"));
/* 259 */         goods.setMktprice(Double.valueOf(rs.getDouble("mktprice")));
/* 260 */         goods.setPrice(Double.valueOf(rs.getDouble("price")));
/*     */ 
/* 263 */         goods.setCreate_time(Long.valueOf(rs.getLong("create_time")));
/* 264 */         goods.setLast_modify(Long.valueOf(rs.getLong("last_modify")));
/* 265 */         goods.setType_id(Integer.valueOf(rs.getInt("type_id")));
/* 266 */         goods.setStore(Integer.valueOf(rs.getInt("store")));
/* 267 */         List specList = GoodsSearchManager.this.getSpecList(goods.getGoods_id().intValue(), this.val$goods_spec_list);
/*     */ 
/* 269 */         goods.setSpecList(specList);
/* 270 */         goods.setHasSpec(rs.getInt("have_spec"));
/*     */ 
/* 275 */         goods.setSn(rs.getString("sn"));
/* 276 */         goods.setIntro(rs.getString("intro"));
/* 277 */         String image_file = rs.getString("image_file");
/* 278 */         if (image_file != null) {
/* 279 */           image_file = UploadUtil.replacePath(image_file);
/* 280 */           goods.setImage_file(image_file);
/*     */         }
/* 282 */         goods.setCat_id(Integer.valueOf(rs.getInt("cat_id")));
/*     */ 
/* 285 */         if ((goods.getHasSpec() == 0) && (specList != null) && (!specList.isEmpty())) {
/* 286 */           goods.setProductid((Integer)((Map)specList.get(0)).get("product_id"));
/*     */         }
/* 288 */         if ((this.val$member != null) && (goods.getProductid() != null)) {
/* 289 */           goods.setPrice(Double.valueOf(goods.getPrice().doubleValue() * this.val$final_discount));
/* 290 */           for (GoodsLvPrice lvPrice : this.val$priceList) {
/* 291 */             if (goods.getProductid().intValue() == lvPrice.getProductid()) {
/* 292 */               goods.setPrice(lvPrice.getPrice());
/*     */             }
/*     */ 
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 299 */         Map propMap = new HashMap();
/*     */ 
/* 301 */         for (int i = 0; i < 20; i++) {
/* 302 */           String value = rs.getString("p" + (i + 1));
/* 303 */           propMap.put("p" + (i + 1), value);
/*     */         }
/* 305 */         goods.setPropMap(propMap);
/*     */ 
/* 307 */         return goods;
/*     */       }
/*     */     };
/* 312 */     List goodslist = this.daoSupport.queryForList(sql, page, pageSize, mapper);
/*     */ 
/* 315 */     return goodslist;
/*     */   }
/*     */ 
/*     */   private void applyMemPrice(List<Map> proList, List<GoodsLvPrice> memPriceList, double discount)
/*     */   {
/* 322 */     for (Map pro : proList) {
/* 323 */       double price = Double.valueOf(pro.get("price").toString()).doubleValue() * discount;
/*     */ 
/* 325 */       for (GoodsLvPrice lvPrice : memPriceList) {
/* 326 */         if (((Integer)pro.get("product_id")).intValue() == lvPrice.getProductid()) {
/* 327 */           price = lvPrice.getPrice().doubleValue();
/*     */         }
/*     */       }
/*     */ 
/* 331 */       pro.put("price", Double.valueOf(price));
/*     */     }
/*     */   }
/*     */ 
/*     */   private long countByCatId(int typeid, String cat_path, String brand_str, String propStr, String keyword, String minPrice, String maxPrice, String tagids, String attrStr)
/*     */   {
/* 351 */     String sql = "select count(0) from " + getTableName("goods") + " g where g.disabled=0 and market_enable=1 ";
/*     */ 
/* 354 */     if (cat_path != null) {
/* 355 */       sql = sql + " and g.cat_id in(";
/* 356 */       sql = sql + "select c.cat_id from " + getTableName("goods_cat") + " c where c.cat_path like '" + cat_path + "%')";
/*     */     }
/*     */ 
/* 360 */     sql = sql + buildTermForByCat(typeid, brand_str, propStr, keyword, minPrice, maxPrice, tagids, attrStr);
/*     */ 
/* 363 */     long count = this.daoSupport.queryForLong(sql, new Object[0]);
/* 364 */     return count;
/*     */   }
/*     */ 
/*     */   private List getSpecList(int goods_id, List specList)
/*     */   {
/* 376 */     List list = new ArrayList();
/*     */ 
/* 378 */     for (int i = 0; i < specList.size(); i++) {
/* 379 */       Map spec = (Map)specList.get(i);
/* 380 */       Integer temp_id = (Integer)spec.get("goods_id");
/* 381 */       if (temp_id.intValue() == goods_id) {
/* 382 */         list.add(spec);
/*     */       }
/*     */     }
/*     */ 
/* 386 */     return list;
/*     */   }
/*     */ 
/*     */   private String buildTerm(int typeid, String brand_str, String propStr, String keyword, String minPrice, String maxPrice, String attrStr)
/*     */   {
/* 398 */     StringBuffer sql = new StringBuffer();
/*     */ 
/* 400 */     sql.append(buildTermForByCat(typeid, brand_str, propStr, attrStr));
/*     */ 
/* 402 */     if (keyword != null) {
/* 403 */       sql.append(" and g.name like '%");
/* 404 */       sql.append(keyword);
/* 405 */       sql.append("%'");
/*     */     }
/*     */ 
/* 408 */     if (minPrice != null) {
/* 409 */       sql.append(" and  g.price>=");
/* 410 */       sql.append(minPrice);
/*     */     }
/*     */ 
/* 413 */     if (maxPrice != null) {
/* 414 */       sql.append(" and g.price<=");
/* 415 */       sql.append(maxPrice);
/*     */     }
/*     */ 
/* 418 */     return sql.toString();
/*     */   }
/*     */ 
/*     */   private String buildTermForByCat(int typeid, String brand_str, String propStr, String keyword, String minPrice, String maxPrice, String tagids, String attrStr)
/*     */   {
/* 423 */     StringBuffer sql = new StringBuffer(buildTerm(typeid, brand_str, propStr, keyword, minPrice, maxPrice, attrStr));
/*     */ 
/* 425 */     if (tagids != null) {
/* 426 */       String filter = goodsIdInTags(tagids);
/* 427 */       filter = filter.equals("") ? "-1" : filter;
/* 428 */       sql.append(" and goods_id in(" + filter + ")");
/*     */     }
/* 430 */     return sql.toString();
/*     */   }
/*     */ 
/*     */   private String goodsIdInTags(String tags) {
/* 434 */     String sql = "select rel_id from tag_rel where tag_id in (" + tags + ")";
/* 435 */     List goodsIdList = this.baseDaoSupport.queryForList(sql, new StringMapper(), new Object[0]);
/* 436 */     return StringUtil.listToString(goodsIdList, ",");
/*     */   }
/*     */ 
/*     */   private String buildTermForByCat(int typeid, String brand_str, String propStr, String attrStr)
/*     */   {
/* 442 */     StringBuffer sql = new StringBuffer();
/*     */ 
/* 444 */     if ((brand_str != null) && (!brand_str.equals(""))) {
/* 445 */       brand_str = "-1," + brand_str.replaceAll("\\_", ",");
/* 446 */       sql.append(" and g.brand_id in(");
/* 447 */       sql.append(brand_str);
/* 448 */       sql.append(")");
/*     */     }
/*     */ 
/* 453 */     if (!StringUtil.isEmpty(attrStr))
/*     */     {
/* 455 */       String[] attrAr = attrStr.split(",");
/* 456 */       for (String attrTerm : attrAr) {
/* 457 */         String[] term = attrTerm.split("\\_");
/* 458 */         if (term.length == 2) {
/* 459 */           sql.append(" and " + term[0] + "=" + term[1]);
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 466 */     if ((propStr != null) && (!propStr.equals(""))) {
/* 467 */       List prop_list = this.goodsTypeManager.getAttrListByTypeId(typeid);
/* 468 */       prop_list = prop_list == null ? new ArrayList() : prop_list;
/* 469 */       String[] s_ar = propStr.split(",");
/*     */ 
/* 471 */       for (int i = 0; i < s_ar.length; i++) {
/* 472 */         String[] value = s_ar[i].split("\\_");
/* 473 */         int index = Integer.valueOf(value[0]).intValue();
/* 474 */         Attribute attr = (Attribute)prop_list.get(index);
/* 475 */         int type = attr.getType();
/* 476 */         if ((type == 2) || (type == 5))
/*     */           continue;
/* 478 */         sql.append(" and g.p" + (index + 1));
/*     */ 
/* 480 */         if (type == 1) {
/* 481 */           sql.append(" like'%");
/* 482 */           sql.append(value[1]);
/* 483 */           sql.append("%'");
/*     */         }
/* 485 */         if ((type == 3) || (type == 4)) {
/* 486 */           sql.append("='");
/* 487 */           sql.append(value[1]);
/* 488 */           sql.append("'");
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 494 */     return sql.toString();
/*     */   }
/*     */ 
/*     */   public IGoodsTypeManager getGoodsTypeManager()
/*     */   {
/* 500 */     return this.goodsTypeManager;
/*     */   }
/*     */ 
/*     */   public void setGoodsTypeManager(IGoodsTypeManager goodsTypeManager) {
/* 504 */     this.goodsTypeManager = goodsTypeManager;
/*     */   }
/*     */ 
/*     */   public IMemberPriceManager getMemberPriceManager()
/*     */   {
/* 509 */     return this.memberPriceManager;
/*     */   }
/*     */ 
/*     */   public void setMemberPriceManager(IMemberPriceManager memberPriceManager)
/*     */   {
/* 514 */     this.memberPriceManager = memberPriceManager;
/*     */   }
/*     */ 
/*     */   public IMemberLvManager getMemberLvManager()
/*     */   {
/* 519 */     return this.memberLvManager;
/*     */   }
/*     */ 
/*     */   public void setMemberLvManager(IMemberLvManager memberLvManager)
/*     */   {
/* 524 */     this.memberLvManager = memberLvManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.service.impl.GoodsSearchManager
 * JD-Core Version:    0.6.0
 */