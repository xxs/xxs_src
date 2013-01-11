/*      */ package com.enation.app.shop.core.service.impl;
/*      */ 
/*      */ import com.enation.app.base.core.model.Member;
/*      */ import com.enation.app.base.core.model.MemberLv;
/*      */ import com.enation.app.shop.core.model.Cat;
/*      */ import com.enation.app.shop.core.model.Goods;
/*      */ import com.enation.app.shop.core.model.GoodsLvPrice;
/*      */ import com.enation.app.shop.core.model.GoodsStores;
/*      */ import com.enation.app.shop.core.model.support.GoodsEditDTO;
/*      */ import com.enation.app.shop.core.plugin.goods.GoodsPluginBundle;
/*      */ import com.enation.app.shop.core.service.IDepotMonitorManager;
/*      */ import com.enation.app.shop.core.service.IGoodsCatManager;
/*      */ import com.enation.app.shop.core.service.IGoodsManager;
/*      */ import com.enation.app.shop.core.service.IMemberLvManager;
/*      */ import com.enation.app.shop.core.service.IMemberPriceManager;
/*      */ import com.enation.app.shop.core.service.IPackageProductManager;
/*      */ import com.enation.app.shop.core.service.ITagManager;
/*      */ import com.enation.app.shop.core.service.SnDuplicateException;
/*      */ import com.enation.eop.sdk.database.BaseSupport;
/*      */ import com.enation.eop.sdk.user.IUserService;
/*      */ import com.enation.eop.sdk.user.UserServiceFactory;
/*      */ import com.enation.eop.sdk.utils.UploadUtil;
/*      */ import com.enation.framework.context.webcontext.ThreadContextHolder;
/*      */ import com.enation.framework.database.IDaoSupport;
/*      */ import com.enation.framework.database.Page;
/*      */ import com.enation.framework.util.StringUtil;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.util.ArrayList;
/*      */ import java.util.HashMap;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import org.apache.commons.beanutils.BeanUtils;
/*      */ import org.apache.log4j.Logger;
/*      */ import org.springframework.jdbc.core.RowMapper;
/*      */ import org.springframework.transaction.annotation.Propagation;
/*      */ import org.springframework.transaction.annotation.Transactional;
/*      */ 
/*      */ public class GoodsManager extends BaseSupport
/*      */   implements IGoodsManager
/*      */ {
/*      */   private ITagManager tagManager;
/*      */   private GoodsPluginBundle goodsPluginBundle;
/*      */   private IPackageProductManager packageProductManager;
/*      */   private IGoodsCatManager goodsCatManager;
/*      */   private IMemberPriceManager memberPriceManager;
/*      */   private IMemberLvManager memberLvManager;
/*      */   private IDepotMonitorManager depotMonitorManager;
/*      */ 
/*      */   @Transactional(propagation=Propagation.REQUIRED)
/*      */   public void add(Goods goods)
/*      */   {
/*      */     try
/*      */     {
/*   62 */       Map goodsMap = po2Map(goods);
/*      */ 
/*   65 */       this.goodsPluginBundle.onBeforeAdd(goodsMap);
/*      */ 
/*   67 */       goodsMap.put("disabled", Integer.valueOf(0));
/*   68 */       goodsMap.put("create_time", Long.valueOf(System.currentTimeMillis()));
/*   69 */       goodsMap.put("view_count", Integer.valueOf(0));
/*   70 */       goodsMap.put("buy_count", Integer.valueOf(0));
/*   71 */       goodsMap.put("last_modify", Long.valueOf(System.currentTimeMillis()));
/*      */ 
/*   73 */       this.baseDaoSupport.insert("goods", goodsMap);
/*   74 */       Integer goods_id = Integer.valueOf(this.baseDaoSupport.getLastId("goods"));
/*      */ 
/*   76 */       goods.setGoods_id(goods_id);
/*   77 */       goodsMap.put("goods_id", goods_id);
/*      */ 
/*   81 */       this.goodsPluginBundle.onAfterAdd(goodsMap);
/*      */     } catch (RuntimeException e) {
/*   83 */       if ((e instanceof SnDuplicateException)) {
/*   84 */         throw e;
/*      */       }
/*      */ 
/*   87 */       e.printStackTrace();
/*      */     }
/*      */   }
/*      */ 
/*      */   @Transactional(propagation=Propagation.REQUIRED)
/*      */   public void edit(Goods goods)
/*      */   {
/*      */     try
/*      */     {
/*  109 */       if (this.logger.isDebugEnabled()) {
/*  110 */         this.logger.debug("开始保存商品数据...");
/*      */       }
/*  112 */       Map goodsMap = po2Map(goods);
/*  113 */       this.goodsPluginBundle.onBeforeEdit(goodsMap);
/*  114 */       this.baseDaoSupport.update("goods", goodsMap, "goods_id=" + goods.getGoods_id());
/*      */ 
/*  116 */       this.goodsPluginBundle.onAfterEdit(goodsMap);
/*  117 */       if (this.logger.isDebugEnabled())
/*  118 */         this.logger.debug("保存商品数据完成.");
/*      */     }
/*      */     catch (RuntimeException e) {
/*  121 */       if ((e instanceof SnDuplicateException)) {
/*  122 */         throw e;
/*      */       }
/*  124 */       e.printStackTrace();
/*      */     }
/*      */   }
/*      */ 
/*      */   public GoodsEditDTO getGoodsEditData(Integer goods_id)
/*      */   {
/*  135 */     GoodsEditDTO editDTO = new GoodsEditDTO();
/*  136 */     String sql = "select * from goods  where goods_id=?";
/*  137 */     Map goods = this.baseDaoSupport.queryForMap(sql, new Object[] { goods_id });
/*      */ 
/*  140 */     String image_file = (String)goods.get("image_file");
/*  141 */     if (!StringUtil.isEmpty(image_file)) {
/*  142 */       image_file = UploadUtil.replacePath(image_file);
/*  143 */       goods.put("image_file", image_file);
/*      */     }
/*      */ 
/*  146 */     String intro = (String)goods.get("intro");
/*  147 */     if (intro != null) {
/*  148 */       intro = UploadUtil.replacePath(intro);
/*  149 */       goods.put("intro", intro);
/*      */     }
/*      */ 
/*  152 */     Map htmlMap = this.goodsPluginBundle.onFillEditInputData(goods);
/*      */ 
/*  154 */     editDTO.setGoods(goods);
/*  155 */     editDTO.setHtmlMap(htmlMap);
/*      */ 
/*  157 */     return editDTO;
/*      */   }
/*      */ 
/*      */   public Map get(Integer goods_id)
/*      */   {
/*  167 */     String sql = "select g.*,b.name as brand_name from " + getTableName("goods") + " g left join " + getTableName("brand") + " b on g.brand_id=b.brand_id ";
/*      */ 
/*  170 */     sql = sql + "  where goods_id=?";
/*      */ 
/*  172 */     Map goods = this.daoSupport.queryForMap(sql, new Object[] { goods_id });
/*      */ 
/*  177 */     String image_file = (String)goods.get("image_file");
/*  178 */     if (image_file != null) {
/*  179 */       image_file = UploadUtil.replacePath(image_file);
/*      */ 
/*  181 */       goods.put("image_file", image_file);
/*      */     }
/*      */ 
/*  184 */     String image_default = (String)goods.get("image_default");
/*  185 */     if (image_default != null) {
/*  186 */       image_default = UploadUtil.replacePath(image_default);
/*  187 */       goods.put("image_default", image_default);
/*      */     }
/*      */ 
/*  194 */     if ((goods.get("have_spec") == null) || (((Integer)goods.get("have_spec")).intValue() == 0))
/*      */     {
/*  198 */       IUserService userService = UserServiceFactory.getUserService();
/*  199 */       Member member = userService.getCurrentMember();
/*  200 */       List memPriceList = new ArrayList();
/*      */ 
/*  202 */       double discount = 1.0D;
/*  203 */       if (member != null) {
/*  204 */         memPriceList = this.memberPriceManager.listPriceByGid(((Integer)goods.get("goods_id")).intValue());
/*      */ 
/*  206 */         MemberLv lv = this.memberLvManager.get(member.getLv_id());
/*      */ 
/*  208 */         if ((lv != null) && (lv.getDiscount() != null)) {
/*  209 */           discount = lv.getDiscount().intValue() / 100.0D;
/*      */         }
/*      */ 
/*  213 */         Double price = Double.valueOf(Double.valueOf(goods.get("price").toString()).doubleValue() * discount);
/*      */ 
/*  217 */         for (GoodsLvPrice lvPrice : memPriceList) {
/*  218 */           if ((lvPrice.getGoodsid() == ((Integer)goods.get("goods_id")).intValue()) && (lvPrice.getLvid() == member.getLv_id().intValue()))
/*      */           {
/*  223 */             price = lvPrice.getPrice();
/*      */           }
/*      */         }
/*      */ 
/*  227 */         goods.put("member_price", price);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  232 */     return goods;
/*      */   }
/*      */ 
/*      */   public void getNavdata(Map goods)
/*      */   {
/*  238 */     int catid = ((Integer)goods.get("cat_id")).intValue();
/*  239 */     List list = this.goodsCatManager.getNavpath(catid);
/*  240 */     goods.put("navdata", list);
/*      */   }
/*      */ 
/*      */   private String getListSql(int disabled)
/*      */   {
/*  249 */     String selectSql = this.goodsPluginBundle.onGetSelector();
/*  250 */     String fromSql = this.goodsPluginBundle.onGetFrom();
/*      */ 
/*  252 */     String sql = "select g.*,b.name as brand_name ,t.name as type_name,c.name as cat_name " + selectSql + " from " + getTableName("goods") + " g left join " + getTableName("goods_cat") + " c on g.cat_id=c.cat_id left join " + getTableName("brand") + " b on g.brand_id = b.brand_id and b.disabled=0 left join " + getTableName("goods_type") + " t on g.type_id =t.type_id " + fromSql + " where g.goods_type = 'normal' and g.disabled=" + disabled;
/*      */ 
/*  266 */     return sql;
/*      */   }
/*      */ 
/*      */   private String getBindListSql(int disabled)
/*      */   {
/*  276 */     String sql = "select g.*,b.name as brand_name ,t.name as type_name,c.name as cat_name from " + getTableName("goods") + " g left join " + getTableName("goods_cat") + " c on g.cat_id=c.cat_id left join " + getTableName("brand") + " b on g.brand_id = b.brand_id left join " + getTableName("goods_type") + " t on g.type_id =t.type_id" + " where g.goods_type = 'bind' and g.disabled=" + disabled;
/*      */ 
/*  286 */     return sql;
/*      */   }
/*      */ 
/*      */   public Page searchGoods(Integer brand_id, Integer is_market, Integer catid, String name, String sn, Integer market_enable, Integer[] tagid, String order, int page, int pageSize)
/*      */   {
/*  302 */     return searchGoods(brand_id, is_market, catid, name, sn, market_enable, tagid, order, page, pageSize, null);
/*      */   }
/*      */ 
/*      */   public Page searchGoods(Integer brand_id, Integer is_market, Integer catid, String name, String sn, Integer market_enable, Integer[] tagid, String order, int page, int pageSize, String other)
/*      */   {
/*  308 */     other = other == null ? "" : other;
/*      */ 
/*  310 */     String sql = getListSql(0);
/*  311 */     if ((brand_id != null) && (brand_id.intValue() != 0)) {
/*  312 */       sql = sql + " and g.brand_id = " + brand_id + " ";
/*      */     }
/*  314 */     if ((is_market == null) || (is_market.intValue() == -1))
/*  315 */       sql = sql + "  ";
/*  316 */     else if (is_market.intValue() == 1)
/*  317 */       sql = sql + " and g.market_enable = 1 ";
/*  318 */     else if (is_market.intValue() == 0) {
/*  319 */       sql = sql + " and g.market_enable = 0 ";
/*      */     }
/*      */ 
/*  322 */     if ("1".equals(other))
/*      */     {
/*  324 */       sql = sql + " and g.no_discount=1";
/*      */     }
/*  326 */     if ("2".equals(other))
/*      */     {
/*  328 */       sql = sql + " and (select count(0) from " + getTableName("goods_lv_price") + " glp where glp.goodsid=g.goods_id) >0";
/*      */     }
/*      */ 
/*  331 */     if (order == null) {
/*  332 */       order = "goods_id desc";
/*      */     }
/*      */ 
/*  335 */     if ((name != null) && (!name.equals(""))) {
/*  336 */       name = name.trim();
/*  337 */       String[] keys = name.split("\\s");
/*      */ 
/*  339 */       for (String key : keys) {
/*  340 */         sql = sql + " and g.name like '%";
/*  341 */         sql = sql + key;
/*  342 */         sql = sql + "%'";
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  347 */     if ((sn != null) && (!sn.equals(""))) {
/*  348 */       sql = sql + "   and g.sn = '" + sn + "'";
/*      */     }
/*      */ 
/*  351 */     if (market_enable != null) {
/*  352 */       sql = sql + " and g.market_enable=" + market_enable;
/*      */     }
/*      */ 
/*  355 */     if (catid != null) {
/*  356 */       Cat cat = this.goodsCatManager.getById(catid.intValue());
/*  357 */       sql = sql + " and  g.cat_id in(";
/*  358 */       sql = sql + "select c.cat_id from " + getTableName("goods_cat") + " c where c.cat_path like '" + cat.getCat_path() + "%')  ";
/*      */     }
/*      */ 
/*  364 */     if ((tagid != null) && (tagid.length > 0)) {
/*  365 */       String tagidstr = StringUtil.arrayToString(tagid, ",");
/*  366 */       sql = sql + " and g.goods_id in(select rel_id from " + getTableName("tag_rel") + " where tag_id in(" + tagidstr + "))";
/*      */     }
/*      */ 
/*  371 */     StringBuffer _sql = new StringBuffer(sql);
/*  372 */     this.goodsPluginBundle.onSearchFilter(_sql);
/*  373 */     _sql.append(" order by g." + order);
/*      */ 
/*  375 */     Page webpage = this.daoSupport.queryForPage(_sql.toString(), page, pageSize, new Object[0]);
/*      */ 
/*  395 */     return webpage;
/*      */   }
/*      */ 
/*      */   public Page searchBindGoods(String name, String sn, String order, int page, int pageSize)
/*      */   {
/*  410 */     String sql = getBindListSql(0);
/*      */ 
/*  412 */     if (order == null) {
/*  413 */       order = "goods_id desc";
/*      */     }
/*      */ 
/*  416 */     if ((name != null) && (!name.equals(""))) {
/*  417 */       sql = sql + "  and g.name like '%" + name + "%'";
/*      */     }
/*      */ 
/*  420 */     if ((sn != null) && (!sn.equals(""))) {
/*  421 */       sql = sql + "   and g.sn = '" + sn + "'";
/*      */     }
/*      */ 
/*  424 */     sql = sql + " order by g." + order;
/*  425 */     Page webpage = this.daoSupport.queryForPage(sql, page, pageSize, new Object[0]);
/*      */ 
/*  427 */     List list = (List)(List)webpage.getResult();
/*      */ 
/*  429 */     for (Map map : list) {
/*  430 */       List productList = this.packageProductManager.list(Integer.valueOf(map.get("goods_id").toString()).intValue());
/*      */ 
/*  432 */       productList = productList == null ? new ArrayList() : productList;
/*  433 */       map.put("productList", productList);
/*      */     }
/*      */ 
/*  436 */     return webpage;
/*      */   }
/*      */ 
/*      */   public Page pageTrash(String name, String sn, String order, int page, int pageSize)
/*      */   {
/*  452 */     String sql = getListSql(1);
/*  453 */     if (order == null) {
/*  454 */       order = "goods_id desc";
/*      */     }
/*      */ 
/*  457 */     if ((name != null) && (!name.equals(""))) {
/*  458 */       sql = sql + "  and g.name like '%" + name + "%'";
/*      */     }
/*      */ 
/*  461 */     if ((sn != null) && (!sn.equals(""))) {
/*  462 */       sql = sql + "   and g.sn = '" + sn + "'";
/*      */     }
/*      */ 
/*  465 */     sql = sql + " order by g." + order;
/*      */ 
/*  467 */     Page webpage = this.daoSupport.queryForPage(sql, page, pageSize, new Object[0]);
/*      */ 
/*  469 */     return webpage;
/*      */   }
/*      */ 
/*      */   public List<GoodsStores> storeWarnGoods(int warnTotal, int page, int pageSize)
/*      */   {
/*  484 */     String select_sql = "select gc.name as gc_name,b.name as b_name,g.cat_id,g.goods_id,g.name,g.sn,g.price,g.last_modify,g.market_enable,s.sumstore ";
/*  485 */     String left_sql = " left join " + getTableName("goods") + " g  on s.goodsid = g.goods_id  left join " + getTableName("goods_cat") + " gc on gc.cat_id = g.cat_id left join " + getTableName("brand") + " b on b.brand_id = g.brand_id ";
/*  486 */     List list = new ArrayList();
/*  487 */     String sql_1 = select_sql + ",s.s_sphere from (select ss.* from (select goodsid,sphere as s_sphere,sum(store) sumstore from " + getTableName("conactlens_store") + " group by goodsid,sphere )  ss " + "  left join " + getTableName("warn_num") + " wn on wn.goods_id = ss.goodsid and wn.sphere = ss.s_sphere  where ss.sumstore <=  (case when (wn.warn_num is not null or wn.warn_num <> 0) then wn.warn_num else ?  end ) )  s  " + left_sql;
/*      */ 
/*  491 */     List list_1 = this.daoSupport.queryForList(sql_1, new RowMapper()
/*      */     {
/*      */       public Object mapRow(ResultSet rs, int arg1)
/*      */         throws SQLException
/*      */       {
/*  496 */         GoodsStores gs = new GoodsStores();
/*  497 */         gs.setGoods_id(Integer.valueOf(rs.getInt("goods_id")));
/*  498 */         gs.setName(rs.getString("name"));
/*  499 */         gs.setSn(rs.getString("sn"));
/*  500 */         gs.setRealstore(Integer.valueOf(rs.getInt("sumstore")));
/*  501 */         gs.setPrice(Double.valueOf(rs.getDouble("price")));
/*  502 */         gs.setLast_modify(Long.valueOf(rs.getLong("last_modify")));
/*  503 */         gs.setBrandname(rs.getString("b_name"));
/*  504 */         gs.setCatname(rs.getString("gc_name"));
/*  505 */         gs.setMarket_enable(Integer.valueOf(rs.getInt("market_enable")));
/*  506 */         gs.setCat_id(Integer.valueOf(rs.getInt("cat_id")));
/*  507 */         gs.setSphere(Double.valueOf(rs.getDouble("s_sphere")));
/*  508 */         return gs;
/*      */       }
/*      */     }
/*      */     , new Object[] { Integer.valueOf(warnTotal) });
/*      */ 
/*  511 */     list.addAll(list_1);
/*      */ 
/*  513 */     String sql_2 = select_sql + " from  (select ss.* from (select goodsid,productid,sum(store) sumstore from " + getTableName("product_store") + "  group by goodsid,productid   ) ss " + "  left join " + getTableName("warn_num") + " wn on wn.goods_id = ss.goodsid  where ss.sumstore <=  (case when (wn.warn_num is not null or wn.warn_num <> 0) then wn.warn_num else ?  end )  ) s  " + left_sql;
/*      */ 
/*  517 */     List list_2 = this.daoSupport.queryForList(sql_2, new RowMapper()
/*      */     {
/*      */       public Object mapRow(ResultSet rs, int arg1)
/*      */         throws SQLException
/*      */       {
/*  522 */         GoodsStores gs = new GoodsStores();
/*  523 */         gs.setGoods_id(Integer.valueOf(rs.getInt("goods_id")));
/*  524 */         gs.setName(rs.getString("name"));
/*  525 */         gs.setSn(rs.getString("sn"));
/*  526 */         gs.setRealstore(Integer.valueOf(rs.getInt("sumstore")));
/*  527 */         gs.setPrice(Double.valueOf(rs.getDouble("price")));
/*  528 */         gs.setLast_modify(Long.valueOf(rs.getLong("last_modify")));
/*  529 */         gs.setBrandname(rs.getString("b_name"));
/*  530 */         gs.setCatname(rs.getString("gc_name"));
/*  531 */         gs.setMarket_enable(Integer.valueOf(rs.getInt("market_enable")));
/*  532 */         gs.setCat_id(Integer.valueOf(rs.getInt("cat_id")));
/*  533 */         return gs;
/*      */       }
/*      */     }
/*      */     , new Object[] { Integer.valueOf(warnTotal) });
/*      */ 
/*  536 */     list.addAll(list_2);
/*      */ 
/*  538 */     String sql_3 = select_sql + ",s.s_sphere from  (select ss.* from (select goodsid,sphere as s_sphere,sum(store) sumstore from " + getTableName("presbyopic_store") + "  group by goodsid,sphere   ) ss " + "  left join " + getTableName("warn_num") + " wn on wn.goods_id = ss.goodsid and wn.sphere = ss.s_sphere  where ss.sumstore <=  (case when (wn.warn_num is not null or wn.warn_num <> 0) then wn.warn_num else ?  end ) ) s  " + left_sql;
/*      */ 
/*  542 */     List list_3 = this.daoSupport.queryForList(sql_3, new RowMapper()
/*      */     {
/*      */       public Object mapRow(ResultSet rs, int arg1)
/*      */         throws SQLException
/*      */       {
/*  547 */         GoodsStores gs = new GoodsStores();
/*  548 */         gs.setGoods_id(Integer.valueOf(rs.getInt("goods_id")));
/*  549 */         gs.setName(rs.getString("name"));
/*  550 */         gs.setSn(rs.getString("sn"));
/*  551 */         gs.setRealstore(Integer.valueOf(rs.getInt("sumstore")));
/*  552 */         gs.setPrice(Double.valueOf(rs.getDouble("price")));
/*  553 */         gs.setLast_modify(Long.valueOf(rs.getLong("last_modify")));
/*  554 */         gs.setBrandname(rs.getString("b_name"));
/*  555 */         gs.setCatname(rs.getString("gc_name"));
/*  556 */         gs.setMarket_enable(Integer.valueOf(rs.getInt("market_enable")));
/*  557 */         gs.setCat_id(Integer.valueOf(rs.getInt("cat_id")));
/*  558 */         gs.setSphere(Double.valueOf(rs.getDouble("s_sphere")));
/*  559 */         return gs;
/*      */       }
/*      */     }
/*      */     , new Object[] { Integer.valueOf(warnTotal) });
/*      */ 
/*  562 */     list.addAll(list_3);
/*      */ 
/*  564 */     String sql_4 = select_sql + ",s.s_cylinder,s.s_sphere  from  (select ss.* from (select goodsid,sphere as s_sphere,cylinder as s_cylinder,sum(store) sumstore from " + getTableName("glasses_store") + "  group by goodsid,sphere,cylinder  ) ss " + " left join " + getTableName("warn_num") + " wn on wn.goods_id = ss.goodsid and wn.sphere = ss.s_sphere and wn.cylinder = ss.s_cylinder  where ss.sumstore <=  (case when (wn.warn_num is not null or wn.warn_num <> 0) then wn.warn_num else ?  end )  ) s  " + left_sql;
/*      */ 
/*  568 */     List list_4 = this.daoSupport.queryForList(sql_4, new RowMapper()
/*      */     {
/*      */       public Object mapRow(ResultSet rs, int arg1)
/*      */         throws SQLException
/*      */       {
/*  573 */         GoodsStores gs = new GoodsStores();
/*  574 */         gs.setGoods_id(Integer.valueOf(rs.getInt("goods_id")));
/*  575 */         gs.setName(rs.getString("name"));
/*  576 */         gs.setSn(rs.getString("sn"));
/*  577 */         gs.setRealstore(Integer.valueOf(rs.getInt("sumstore")));
/*  578 */         gs.setPrice(Double.valueOf(rs.getDouble("price")));
/*  579 */         gs.setLast_modify(Long.valueOf(rs.getLong("last_modify")));
/*  580 */         gs.setBrandname(rs.getString("b_name"));
/*  581 */         gs.setCatname(rs.getString("gc_name"));
/*  582 */         gs.setMarket_enable(Integer.valueOf(rs.getInt("market_enable")));
/*  583 */         gs.setCat_id(Integer.valueOf(rs.getInt("cat_id")));
/*  584 */         gs.setCylinder(Double.valueOf(rs.getDouble("s_cylinder")));
/*  585 */         gs.setSphere(Double.valueOf(rs.getDouble("s_sphere")));
/*  586 */         return gs;
/*      */       }
/*      */     }
/*      */     , new Object[] { Integer.valueOf(warnTotal) });
/*      */ 
/*  589 */     list.addAll(list_4);
/*      */ 
/*  592 */     String sql_5 = select_sql + ",p.color from  (select ss.* from (select goodsid,productid,sum(store) sumstore from " + getTableName("frame_store") + " group by goodsid,productid  ) ss  " + "\tleft join " + getTableName("warn_num") + " wn on wn.goods_id = ss.goodsid and wn.product_id = ss.productid  where ss.sumstore <=  (case when (wn.warn_num is not null or wn.warn_num <> 0) then wn.warn_num else ?  end )  ) s " + "left join " + getTableName("product_color") + " p on p.productid = s.productid" + left_sql;
/*      */ 
/*  597 */     List list_5 = this.daoSupport.queryForList(sql_5, new RowMapper()
/*      */     {
/*      */       public Object mapRow(ResultSet rs, int arg1)
/*      */         throws SQLException
/*      */       {
/*  602 */         GoodsStores gs = new GoodsStores();
/*  603 */         gs.setGoods_id(Integer.valueOf(rs.getInt("goods_id")));
/*  604 */         gs.setName(rs.getString("name"));
/*  605 */         gs.setSn(rs.getString("sn"));
/*  606 */         gs.setRealstore(Integer.valueOf(rs.getInt("sumstore")));
/*  607 */         gs.setPrice(Double.valueOf(rs.getDouble("price")));
/*  608 */         gs.setLast_modify(Long.valueOf(rs.getLong("last_modify")));
/*  609 */         gs.setBrandname(rs.getString("b_name"));
/*  610 */         gs.setCatname(rs.getString("gc_name"));
/*  611 */         gs.setMarket_enable(Integer.valueOf(rs.getInt("market_enable")));
/*  612 */         gs.setCat_id(Integer.valueOf(rs.getInt("cat_id")));
/*  613 */         gs.setColor(rs.getString("color"));
/*  614 */         return gs;
/*      */       }
/*      */     }
/*      */     , new Object[] { Integer.valueOf(warnTotal) });
/*      */ 
/*  617 */     list.addAll(list_5);
/*      */ 
/*  619 */     return list;
/*      */   }
/*      */ 
/*      */   @Transactional(propagation=Propagation.REQUIRED)
/*      */   public void delete(Integer[] ids)
/*      */   {
/*  629 */     if (ids == null) {
/*  630 */       return;
/*      */     }
/*  632 */     for (Integer id : ids) {
/*  633 */       this.tagManager.saveRels(id, null);
/*      */     }
/*  635 */     String id_str = StringUtil.arrayToString(ids, ",");
/*  636 */     String sql = "update  goods set disabled=1  where goods_id in (" + id_str + ")";
/*      */ 
/*  639 */     this.baseDaoSupport.execute(sql, new Object[0]);
/*      */   }
/*      */ 
/*      */   public void revert(Integer[] ids)
/*      */   {
/*  648 */     if (ids == null)
/*  649 */       return;
/*  650 */     String id_str = StringUtil.arrayToString(ids, ",");
/*  651 */     String sql = "update  goods set disabled=0  where goods_id in (" + id_str + ")";
/*      */ 
/*  653 */     this.baseDaoSupport.execute(sql, new Object[0]);
/*      */   }
/*      */ 
/*      */   @Transactional(propagation=Propagation.REQUIRED)
/*      */   public void clean(Integer[] ids)
/*      */   {
/*  663 */     if (ids == null)
/*  664 */       return;
/*  665 */     for (Integer id : ids) {
/*  666 */       this.tagManager.saveRels(id, null);
/*      */     }
/*  668 */     this.goodsPluginBundle.onGoodsDelete(ids);
/*  669 */     String id_str = StringUtil.arrayToString(ids, ",");
/*  670 */     String sql = "delete  from goods  where goods_id in (" + id_str + ")";
/*  671 */     this.baseDaoSupport.execute(sql, new Object[0]);
/*      */   }
/*      */ 
/*      */   public List list(Integer[] ids)
/*      */   {
/*  676 */     if ((ids == null) || (ids.length == 0))
/*  677 */       return new ArrayList();
/*  678 */     String idstr = StringUtil.arrayToString(ids, ",");
/*  679 */     String sql = "select * from goods where goods_id in(" + idstr + ")";
/*  680 */     return this.baseDaoSupport.queryForList(sql, new Object[0]);
/*      */   }
/*      */ 
/*      */   public GoodsPluginBundle getGoodsPluginBundle()
/*      */   {
/*  686 */     return this.goodsPluginBundle;
/*      */   }
/*      */ 
/*      */   public void setGoodsPluginBundle(GoodsPluginBundle goodsPluginBundle) {
/*  690 */     this.goodsPluginBundle = goodsPluginBundle;
/*      */   }
/*      */ 
/*      */   protected Map po2Map(Object po)
/*      */   {
/*  700 */     Map poMap = new HashMap();
/*  701 */     Map map = new HashMap();
/*      */     try {
/*  703 */       map = BeanUtils.describe(po);
/*      */     } catch (Exception ex) {
/*      */     }
/*  706 */     Object[] keyArray = map.keySet().toArray();
/*  707 */     for (int i = 0; i < keyArray.length; i++) {
/*  708 */       String str = keyArray[i].toString();
/*  709 */       if ((str == null) || (str.equals("class")) || 
/*  710 */         (map.get(str) == null)) continue;
/*  711 */       poMap.put(str, map.get(str));
/*      */     }
/*      */ 
/*  715 */     return poMap;
/*      */   }
/*      */ 
/*      */   public IPackageProductManager getPackageProductManager() {
/*  719 */     return this.packageProductManager;
/*      */   }
/*      */ 
/*      */   public void setPackageProductManager(IPackageProductManager packageProductManager)
/*      */   {
/*  724 */     this.packageProductManager = packageProductManager;
/*      */   }
/*      */ 
/*      */   public Goods getGoods(Integer goods_id) {
/*  728 */     Goods goods = (Goods)this.baseDaoSupport.queryForObject("select * from goods where goods_id=?", Goods.class, new Object[] { goods_id });
/*      */ 
/*  730 */     return goods;
/*      */   }
/*      */ 
/*      */   public IGoodsCatManager getGoodsCatManager() {
/*  734 */     return this.goodsCatManager;
/*      */   }
/*      */ 
/*      */   public void setGoodsCatManager(IGoodsCatManager goodsCatManager) {
/*  738 */     this.goodsCatManager = goodsCatManager;
/*      */   }
/*      */   @Transactional(propagation=Propagation.REQUIRED)
/*      */   public void batchEdit() {
/*  743 */     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
/*  744 */     String[] ids = request.getParameterValues("goodsidArray");
/*  745 */     String[] names = request.getParameterValues("name");
/*  746 */     String[] prices = request.getParameterValues("price");
/*  747 */     String[] cats = request.getParameterValues("catidArray");
/*  748 */     String[] market_enable = request.getParameterValues("market_enables");
/*  749 */     String[] store = request.getParameterValues("store");
/*  750 */     String[] sord = request.getParameterValues("sord");
/*      */ 
/*  752 */     String sql = "";
/*      */ 
/*  754 */     for (int i = 0; i < ids.length; i++) {
/*  755 */       sql = "";
/*  756 */       if ((names != null) && (names.length > 0) && 
/*  757 */         (!StringUtil.isEmpty(names[i]))) {
/*  758 */         if (!sql.equals(""))
/*  759 */           sql = sql + ",";
/*  760 */         sql = sql + " name='" + names[i] + "'";
/*      */       }
/*      */ 
/*  764 */       if ((prices != null) && (prices.length > 0) && 
/*  765 */         (!StringUtil.isEmpty(prices[i]))) {
/*  766 */         if (!sql.equals(""))
/*  767 */           sql = sql + ",";
/*  768 */         sql = sql + " price=" + prices[i];
/*      */       }
/*      */ 
/*  771 */       if ((cats != null) && (cats.length > 0) && 
/*  772 */         (!StringUtil.isEmpty(cats[i]))) {
/*  773 */         if (!sql.equals(""))
/*  774 */           sql = sql + ",";
/*  775 */         sql = sql + " cat_id=" + cats[i];
/*      */       }
/*      */ 
/*  778 */       if ((store != null) && (store.length > 0) && 
/*  779 */         (!StringUtil.isEmpty(store[i]))) {
/*  780 */         if (!sql.equals(""))
/*  781 */           sql = sql + ",";
/*  782 */         sql = sql + " store=" + store[i];
/*      */       }
/*      */ 
/*  785 */       if ((market_enable != null) && (market_enable.length > 0) && 
/*  786 */         (!StringUtil.isEmpty(market_enable[i]))) {
/*  787 */         if (!sql.equals(""))
/*  788 */           sql = sql + ",";
/*  789 */         sql = sql + " market_enable=" + market_enable[i];
/*      */       }
/*      */ 
/*  792 */       if ((sord != null) && (sord.length > 0) && 
/*  793 */         (!StringUtil.isEmpty(sord[i]))) {
/*  794 */         if (!sql.equals(""))
/*  795 */           sql = sql + ",";
/*  796 */         sql = sql + " sord=" + sord[i];
/*      */       }
/*      */ 
/*  799 */       sql = "update  goods set " + sql + " where goods_id=?";
/*  800 */       this.baseDaoSupport.execute(sql, new Object[] { ids[i] });
/*      */     }
/*      */   }
/*      */ 
/*      */   public Map census()
/*      */   {
/*  806 */     String sql = "select count(0) from goods where market_enable=1 and  disabled = 0";
/*  807 */     int salecount = this.baseDaoSupport.queryForInt(sql, new Object[0]);
/*      */ 
/*  810 */     sql = "select count(0) from goods where market_enable=0 and  disabled = 0";
/*  811 */     int unsalecount = this.baseDaoSupport.queryForInt(sql, new Object[0]);
/*      */ 
/*  814 */     sql = "select count(0) from goods where market_enable=0 and  disabled = 1";
/*  815 */     int disabledcount = this.baseDaoSupport.queryForInt(sql, new Object[0]);
/*      */ 
/*  818 */     sql = "select count(0) from comments where   for_comment_id is null  and commenttype='goods' and object_type='discuss'";
/*  819 */     int discusscount = this.baseDaoSupport.queryForInt(sql, new Object[0]);
/*      */ 
/*  822 */     sql = "select count(0) from comments where for_comment_id is null  and  commenttype='goods' and object_type='ask'";
/*  823 */     int askcount = this.baseDaoSupport.queryForInt(sql, new Object[0]);
/*      */ 
/*  825 */     Map map = new HashMap(2);
/*  826 */     map.put("salecount", Integer.valueOf(salecount));
/*  827 */     map.put("unsalecount", Integer.valueOf(unsalecount));
/*  828 */     map.put("disabledcount", Integer.valueOf(disabledcount));
/*  829 */     map.put("allcount", Integer.valueOf(unsalecount + salecount));
/*  830 */     map.put("discuss", Integer.valueOf(discusscount));
/*  831 */     map.put("ask", Integer.valueOf(askcount));
/*  832 */     return map;
/*      */   }
/*      */ 
/*      */   public List getRecommentList(int goods_id, int cat_id, int brand_id, int num)
/*      */   {
/*  839 */     int newNum = num;
/*  840 */     if (newNum % 2 != 0)
/*  841 */       newNum++;
/*  842 */     if (newNum == 0) {
/*  843 */       return null;
/*      */     }
/*  845 */     List recommentList = new ArrayList();
/*  846 */     String sql = " SELECT g.* FROM goods g WHERE g.disabled=0 AND g.market_enable=1 ";
/*      */ 
/*  848 */     if ((cat_id == 1) || (cat_id == 2))
/*      */     {
/*  850 */       List list1 = this.baseDaoSupport.queryForList(sql + "AND g.cat_id in (7,8) AND g.brand_id=? ORDER BY g.buy_count DESC LIMIT ?", new Object[] { Integer.valueOf(brand_id), Integer.valueOf(newNum / 2) });
/*      */ 
/*  855 */       if ((list1 != null) && (list1.size() >= 0)) {
/*  856 */         int max = list1.size() >= newNum / 2 ? newNum / 2 : list1.size();
/*      */ 
/*  858 */         for (int i = 0; i < max; i++) {
/*  859 */           recommentList.add(list1.get(i));
/*      */         }
/*      */       }
/*      */ 
/*  863 */       if (recommentList.size() < newNum / 2) {
/*  864 */         list1 = this.baseDaoSupport.queryForList(sql + "AND g.cat_id in (7,8) ORDER BY g.buy_count DESC LIMIT ?", new Object[] { Integer.valueOf(newNum / 2) });
/*      */ 
/*  869 */         if ((list1 != null) && (list1.size() > 0)) {
/*  870 */           int max = list1.size() >= newNum / 2 - recommentList.size() ? newNum / 2 - recommentList.size() : list1.size();
/*      */ 
/*  873 */           for (int i = 0; i < max; i++) {
/*  874 */             recommentList.add(list1.get(i));
/*      */           }
/*      */         }
/*      */       }
/*      */ 
/*  879 */       int tempCatId = cat_id == 1 ? 2 : 1;
/*  880 */       int max = num - recommentList.size();
/*  881 */       list1 = this.baseDaoSupport.queryForList(sql + "AND g.cat_id =? ORDER BY g.buy_count DESC LIMIT ?", new Object[] { Integer.valueOf(tempCatId), Integer.valueOf(max) });
/*      */ 
/*  884 */       if ((list1 != null) && (list1.size() > 0)) {
/*  885 */         for (int i = 0; i < max; i++) {
/*  886 */           recommentList.add(list1.get(i));
/*      */         }
/*      */       }
/*      */ 
/*      */     }
/*  891 */     else if ((cat_id == 7) || (cat_id == 8))
/*      */     {
/*  893 */       List list1 = this.baseDaoSupport.queryForList(sql + "AND g.cat_id = 1 AND g.brand_id=? ORDER BY g.buy_count DESC LIMIT ?", new Object[] { Integer.valueOf(brand_id), Integer.valueOf(newNum / 2) });
/*      */ 
/*  898 */       if ((list1 != null) && (list1.size() >= 0)) {
/*  899 */         int max = list1.size() >= newNum / 2 ? newNum / 2 : list1.size();
/*      */ 
/*  901 */         for (int i = 0; i < max; i++) {
/*  902 */           recommentList.add(list1.get(i));
/*      */         }
/*      */       }
/*      */ 
/*  906 */       if (recommentList.size() < newNum / 2) {
/*  907 */         list1 = this.baseDaoSupport.queryForList(sql + "AND g.cat_id = 1 ORDER BY g.buy_count DESC LIMIT ?", new Object[] { Integer.valueOf(newNum / 2) });
/*      */ 
/*  910 */         if ((list1 != null) && (list1.size() > 0)) {
/*  911 */           int max = list1.size() >= newNum / 2 - recommentList.size() ? newNum / 2 - recommentList.size() : list1.size();
/*      */ 
/*  914 */           for (int i = 0; i < max; i++) {
/*  915 */             recommentList.add(list1.get(i));
/*      */           }
/*      */         }
/*      */       }
/*      */ 
/*  920 */       int max = num - recommentList.size();
/*  921 */       list1 = this.baseDaoSupport.queryForList(sql + "AND g.cat_id =? ORDER BY g.buy_count DESC LIMIT ?", new Object[] { Integer.valueOf(1), Integer.valueOf(max) });
/*      */ 
/*  924 */       if ((list1 != null) && (list1.size() > 0)) {
/*  925 */         for (int i = 0; i < max; i++) {
/*  926 */           recommentList.add(list1.get(i));
/*      */         }
/*      */       }
/*      */ 
/*      */     }
/*  931 */     else if ((cat_id == 18) || (cat_id == 3) || (cat_id == 4))
/*      */     {
/*  933 */       List list1 = this.baseDaoSupport.queryForList(sql + "AND g.cat_id = 1 ORDER BY g.buy_count DESC LIMIT ?", new Object[] { Integer.valueOf(newNum / 2) });
/*      */ 
/*  936 */       if ((list1 != null) && (list1.size() > 0)) {
/*  937 */         for (int i = 0; i < list1.size(); i++) {
/*  938 */           recommentList.add(list1.get(i));
/*      */         }
/*      */       }
/*      */ 
/*  942 */       int max = num - recommentList.size();
/*  943 */       list1 = this.baseDaoSupport.queryForList(sql + "AND g.cat_id =? ORDER BY g.buy_count DESC LIMIT ?", new Object[] { Integer.valueOf(12), Integer.valueOf(max) });
/*      */ 
/*  946 */       if ((list1 != null) && (list1.size() > 0)) {
/*  947 */         for (int i = 0; i < max; i++) {
/*  948 */           recommentList.add(list1.get(i));
/*      */         }
/*      */       }
/*      */ 
/*      */     }
/*  953 */     else if (cat_id == 6) {
/*  954 */       recommentList = this.baseDaoSupport.queryForList(sql + "AND g.cat_id in (18,3,4) ORDER BY g.buy_count DESC LIMIT ?", new Object[] { Integer.valueOf(num) });
/*      */     }
/*  961 */     else if (cat_id == 12) {
/*  962 */       recommentList = this.baseDaoSupport.queryForList(sql + "AND g.cat_id = 12 AND g.goods_id != ? ORDER BY g.buy_count DESC LIMIT ?", new Object[] { Integer.valueOf(goods_id), Integer.valueOf(num) });
/*      */     }
/*  969 */     else if ((cat_id == 14) || (cat_id == 15) || (cat_id == 16) || (cat_id == 17)) {
/*  970 */       recommentList = this.baseDaoSupport.queryForList(sql + "AND g.cat_id in (14,15,16,17) AND g.goods_id != ? ORDER BY g.buy_count DESC LIMIT ?", new Object[] { Integer.valueOf(goods_id), Integer.valueOf(num) });
/*      */     }
/*  977 */     else if (cat_id == 19) {
/*  978 */       recommentList = this.baseDaoSupport.queryForList(sql + "AND g.cat_id = 19 AND g.goods_id != ? ORDER BY g.buy_count DESC LIMIT ?", new Object[] { Integer.valueOf(goods_id), Integer.valueOf(num) });
/*      */     }
/*  985 */     else if (cat_id == 9)
/*      */     {
/*  987 */       List list1 = this.baseDaoSupport.queryForList(sql + "AND g.cat_id = 1 ORDER BY g.buy_count DESC LIMIT ?", new Object[] { Integer.valueOf(newNum / 2) });
/*      */ 
/*  990 */       if ((list1 != null) && (list1.size() > 0)) {
/*  991 */         for (int i = 0; i < list1.size(); i++) {
/*  992 */           recommentList.add(list1.get(i));
/*      */         }
/*      */       }
/*      */ 
/*  996 */       int max = num - recommentList.size();
/*  997 */       list1 = this.baseDaoSupport.queryForList(sql + "AND g.cat_id =? ORDER BY g.buy_count DESC LIMIT ?", new Object[] { Integer.valueOf(12), Integer.valueOf(max) });
/*      */ 
/* 1000 */       if ((list1 != null) && (list1.size() > 0)) {
/* 1001 */         for (int i = 0; i < max; i++) {
/* 1002 */           recommentList.add(list1.get(i));
/*      */         }
/*      */       }
/*      */ 
/*      */     }
/* 1007 */     else if (cat_id == 10) {
/* 1008 */       recommentList = this.baseDaoSupport.queryForList(sql + "AND g.cat_id in (3,4,18) ORDER BY g.buy_count DESC LIMIT ?", new Object[] { Integer.valueOf(num) });
/*      */     }
/*      */ 
/* 1014 */     return recommentList;
/*      */   }
/*      */ 
/*      */   public List list()
/*      */   {
/* 1019 */     String sql = "select * from goods where disabled = 0";
/* 1020 */     return this.baseDaoSupport.queryForList(sql, new Object[0]);
/*      */   }
/*      */ 
/*      */   public IMemberPriceManager getMemberPriceManager() {
/* 1024 */     return this.memberPriceManager;
/*      */   }
/*      */ 
/*      */   public ITagManager getTagManager() {
/* 1028 */     return this.tagManager;
/*      */   }
/*      */ 
/*      */   public void setTagManager(ITagManager tagManager) {
/* 1032 */     this.tagManager = tagManager;
/*      */   }
/*      */ 
/*      */   public void setMemberPriceManager(IMemberPriceManager memberPriceManager) {
/* 1036 */     this.memberPriceManager = memberPriceManager;
/*      */   }
/*      */ 
/*      */   public IMemberLvManager getMemberLvManager() {
/* 1040 */     return this.memberLvManager;
/*      */   }
/*      */ 
/*      */   public void setMemberLvManager(IMemberLvManager memberLvManager) {
/* 1044 */     this.memberLvManager = memberLvManager;
/*      */   }
/*      */ 
/*      */   public void updateField(String filedname, Object value, Integer goodsid)
/*      */   {
/* 1050 */     this.baseDaoSupport.execute("update goods set " + filedname + "=? where goods_id=?", new Object[] { value, goodsid });
/*      */   }
/*      */ 
/*      */   public Goods getGoodBySn(String goodSn)
/*      */   {
/* 1056 */     Goods goods = (Goods)this.baseDaoSupport.queryForObject("select * from goods where sn=?", Goods.class, new Object[] { goodSn });
/*      */ 
/* 1058 */     return goods;
/*      */   }
/*      */ 
/*      */   public IDepotMonitorManager getDepotMonitorManager() {
/* 1062 */     return this.depotMonitorManager;
/*      */   }
/*      */ 
/*      */   public void setDepotMonitorManager(IDepotMonitorManager depotMonitorManager) {
/* 1066 */     this.depotMonitorManager = depotMonitorManager;
/*      */   }
/*      */ 
/*      */   public Page searchGoods(Integer catid, String name, String sn, Integer market_enable, Integer[] tagid, String order, int page, int pageSize)
/*      */   {
/* 1072 */     String sql = getListSql(0);
/*      */ 
/* 1074 */     if (order == null) {
/* 1075 */       order = "goods_id desc";
/*      */     }
/*      */ 
/* 1078 */     if ((name != null) && (!name.equals(""))) {
/* 1079 */       sql = sql + "  and g.name like '%" + name + "%'";
/*      */     }
/*      */ 
/* 1082 */     if ((sn != null) && (!sn.equals(""))) {
/* 1083 */       sql = sql + "   and g.sn = '" + sn + "'";
/*      */     }
/*      */ 
/* 1086 */     if (market_enable != null) {
/* 1087 */       sql = sql + " and g.market_enable=" + market_enable;
/*      */     }
/*      */ 
/* 1090 */     if (catid != null) {
/* 1091 */       Cat cat = this.goodsCatManager.getById(catid.intValue());
/* 1092 */       sql = sql + " and  g.cat_id in(";
/* 1093 */       sql = sql + "select c.cat_id from " + getTableName("goods_cat") + " c where c.cat_path like '" + cat.getCat_path() + "%')  ";
/*      */     }
/*      */ 
/* 1097 */     if ((tagid != null) && (tagid.length > 0)) {
/* 1098 */       String tagidstr = StringUtil.arrayToString(tagid, ",");
/* 1099 */       sql = sql + " and g.goods_id in(select rel_id from " + getTableName("tag_rel") + " where tag_id in(" + tagidstr + "))";
/*      */     }
/*      */ 
/* 1102 */     sql = sql + " order by g." + order;
/*      */ 
/* 1104 */     Page webpage = this.daoSupport.queryForPage(sql, page, pageSize, new Object[0]);
/*      */ 
/* 1106 */     return webpage;
/*      */   }
/*      */ 
/*      */   public List listByCat(Integer catid)
/*      */   {
/* 1112 */     String sql = getListSql(0);
/* 1113 */     if (catid.intValue() != 0) {
/* 1114 */       Cat cat = this.goodsCatManager.getById(catid.intValue());
/* 1115 */       sql = sql + " and  g.cat_id in(";
/* 1116 */       sql = sql + "select c.cat_id from " + getTableName("goods_cat") + " c where c.cat_path like '" + cat.getCat_path() + "%')  ";
/*      */     }
/*      */ 
/* 1119 */     return this.daoSupport.queryForList(sql, new Object[0]);
/*      */   }
/*      */ 
/*      */   public List listByTag(Integer[] tagid)
/*      */   {
/* 1126 */     String sql = getListSql(0);
/* 1127 */     if ((tagid != null) && (tagid.length > 0)) {
/* 1128 */       String tagidstr = StringUtil.arrayToString(tagid, ",");
/* 1129 */       sql = sql + " and g.goods_id in(select rel_id from " + getTableName("tag_rel") + " where tag_id in(" + tagidstr + "))";
/*      */     }
/* 1131 */     return this.daoSupport.queryForList(sql, new Object[0]);
/*      */   }
/*      */ 
/*      */   public void incViewCount(Integer goods_id)
/*      */   {
/* 1136 */     this.baseDaoSupport.execute("update goods set view_count = view_count + 1 where goods_id = ?", new Object[] { goods_id });
/*      */   }
/*      */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.service.impl.GoodsManager
 * JD-Core Version:    0.6.0
 */