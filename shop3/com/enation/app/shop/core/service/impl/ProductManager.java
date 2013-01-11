/*     */ package com.enation.app.shop.core.service.impl;
/*     */ 
/*     */ import com.enation.app.base.core.model.Member;
/*     */ import com.enation.app.base.core.model.MemberLv;
/*     */ import com.enation.app.shop.core.model.GoodsLvPrice;
/*     */ import com.enation.app.shop.core.model.Product;
/*     */ import com.enation.app.shop.core.model.SpecValue;
/*     */ import com.enation.app.shop.core.model.Specification;
/*     */ import com.enation.app.shop.core.service.IMemberLvManager;
/*     */ import com.enation.app.shop.core.service.IMemberPriceManager;
/*     */ import com.enation.app.shop.core.service.IProductManager;
/*     */ import com.enation.eop.sdk.database.BaseSupport;
/*     */ import com.enation.eop.sdk.user.IUserService;
/*     */ import com.enation.eop.sdk.user.UserServiceFactory;
/*     */ import com.enation.eop.sdk.utils.UploadUtil;
/*     */ import com.enation.framework.database.IDaoSupport;
/*     */ import com.enation.framework.database.Page;
/*     */ import com.enation.framework.database.StringMapper;
/*     */ import com.enation.framework.util.StringUtil;
/*     */ import java.io.PrintStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.sf.json.JSONArray;
/*     */ import org.springframework.transaction.annotation.Propagation;
/*     */ import org.springframework.transaction.annotation.Transactional;
/*     */ 
/*     */ public class ProductManager extends BaseSupport<Product>
/*     */   implements IProductManager
/*     */ {
/*     */   private IMemberPriceManager memberPriceManager;
/*     */   private IMemberLvManager memberLvManager;
/*     */ 
/*     */   private String getProductidStr(List<Product> productList)
/*     */   {
/*  44 */     StringBuffer str = new StringBuffer();
/*  45 */     for (Product pro : productList)
/*     */     {
/*  47 */       if (str.length() != 0) {
/*  48 */         str.append(",");
/*     */       }
/*  50 */       Integer productid = pro.getProduct_id();
/*  51 */       if (productid != null) {
/*  52 */         str.append(pro.getProduct_id());
/*     */       }
/*     */     }
/*  55 */     return str.toString();
/*     */   }
/*     */   @Transactional(propagation=Propagation.REQUIRED)
/*     */   public void add(List<Product> productList) {
/*  60 */     if (productList.size() > 0) {
/*  61 */       Integer goodsid = ((Product)productList.get(0)).getGoods_id();
/*     */ 
/*  63 */       this.baseDaoSupport.execute("delete from  goods_spec  where goods_id=?", new Object[] { goodsid });
/*  64 */       this.baseDaoSupport.execute("delete from  goods_lv_price  where goodsid=?", new Object[] { goodsid });
/*     */ 
/*  66 */       String proidstr = getProductidStr(productList);
/*     */ 
/*  68 */       String sql = "delete from product where goods_id=? ";
/*  69 */       if (!StringUtil.isEmpty(proidstr)) {
/*  70 */         sql = sql + " and  product_id  not in(" + proidstr + ")";
/*     */       }
/*  72 */       this.baseDaoSupport.execute(sql, new Object[] { goodsid });
/*     */ 
/*  76 */       sql = "delete from product_store where goodsid=? ";
/*  77 */       if (!StringUtil.isEmpty(proidstr)) {
/*  78 */         sql = sql + " and  productid  not in(" + proidstr + ")";
/*     */       }
/*  80 */       this.baseDaoSupport.execute(sql, new Object[] { goodsid });
/*     */     }
/*     */ 
/*  84 */     for (Product product : productList)
/*     */     {
/*  87 */       product_id = product.getProduct_id();
/*  88 */       if (product_id == null) {
/*  89 */         this.baseDaoSupport.insert("product", product);
/*  90 */         product_id = Integer.valueOf(this.baseDaoSupport.getLastId("product"));
/*  91 */         product.setProduct_id(product_id);
/*     */       } else {
/*  93 */         this.baseDaoSupport.update("product", product, "product_id=" + product_id);
/*     */       }
/*     */ 
/*  97 */       List specList = product.getSpecList();
/*     */ 
/*  99 */       for (SpecValue specvalue : specList) {
/* 100 */         this.daoSupport.execute("insert into " + getTableName("goods_spec") + "(spec_id,spec_value_id,goods_id,product_id)values(?,?,?,?)", new Object[] { specvalue.getSpec_id(), specvalue.getSpec_value_id(), product.getGoods_id(), product_id });
/*     */       }
/*     */ 
/* 106 */       List lvPriceList = product.getGoodsLvPrices();
/* 107 */       if (lvPriceList != null)
/* 108 */         for (GoodsLvPrice lvPrice : lvPriceList) {
/* 109 */           lvPrice.setProductid(product_id.intValue());
/* 110 */           this.baseDaoSupport.insert("goods_lv_price", lvPrice);
/*     */         }
/*     */     }
/*     */     Integer product_id;
/* 116 */     if (productList.size() > 0) {
/* 117 */       Integer goodsid = ((Product)productList.get(0)).getGoods_id();
/*     */ 
/* 119 */       this.baseDaoSupport.execute("update goods set specs=? where goods_id=?", new Object[] { JSONArray.fromObject(productList).toString(), goodsid });
/*     */     }
/*     */   }
/*     */ 
/*     */   public Product get(Integer productid)
/*     */   {
/* 125 */     String sql = "select * from product where product_id=?";
/* 126 */     return (Product)this.baseDaoSupport.queryForObject(sql, Product.class, new Object[] { productid });
/*     */   }
/*     */ 
/*     */   public List<String> listSpecName(int goodsid)
/*     */   {
/* 132 */     StringBuffer sql = new StringBuffer();
/* 133 */     sql.append("select distinct s.spec_name ");
/* 134 */     sql.append(" from ");
/*     */ 
/* 136 */     sql.append(getTableName("specification"));
/* 137 */     sql.append(" s,");
/*     */ 
/* 141 */     sql.append(getTableName("goods_spec"));
/* 142 */     sql.append(" gs ");
/*     */ 
/* 144 */     sql.append("where s.spec_id = gs.spec_id and gs.goods_id=?");
/* 145 */     List list = this.daoSupport.queryForList(sql.toString(), new StringMapper(), new Object[] { Integer.valueOf(goodsid) });
/* 146 */     return list;
/*     */   }
/*     */ 
/*     */   public List<Specification> listSpecs(Integer goodsId)
/*     */   {
/* 155 */     StringBuffer sql = new StringBuffer();
/* 156 */     sql.append("select distinct s.spec_id,s.spec_type,s.spec_name,sv.spec_value_id,sv.spec_value,sv.spec_image ,gs.goods_id as goods_id ");
/* 157 */     sql.append(" from ");
/*     */ 
/* 159 */     sql.append(getTableName("specification"));
/* 160 */     sql.append(" s,");
/*     */ 
/* 162 */     sql.append(getTableName("spec_values"));
/* 163 */     sql.append(" sv,");
/*     */ 
/* 165 */     sql.append(getTableName("goods_spec"));
/* 166 */     sql.append(" gs ");
/*     */ 
/* 168 */     sql.append("where s.spec_id = sv.spec_id  and gs.spec_value_id = sv.spec_value_id and gs.goods_id=?  ORDER BY s.spec_id");
/* 169 */     List list = this.daoSupport.queryForList(sql.toString(), new Object[] { goodsId });
/*     */ 
/* 171 */     List specList = new ArrayList();
/* 172 */     Integer spec_id = Integer.valueOf(0);
/* 173 */     Specification spec = null;
/* 174 */     for (Map map : list) {
/* 175 */       Integer dbspecid = Integer.valueOf(map.get("spec_id").toString());
/*     */       List valueList;
/* 178 */       if (spec_id.intValue() != dbspecid.intValue()) {
/* 179 */         spec_id = dbspecid;
/* 180 */         List valueList = new ArrayList();
/*     */ 
/* 182 */         spec = new Specification();
/* 183 */         spec.setSpec_id(dbspecid);
/* 184 */         spec.setSpec_name(map.get("spec_name").toString());
/* 185 */         spec.setSpec_type((Integer)map.get("spec_type"));
/*     */ 
/* 187 */         specList.add(spec);
/*     */ 
/* 189 */         spec.setValueList(valueList);
/*     */       } else {
/* 191 */         valueList = spec.getValueList();
/*     */       }
/*     */ 
/* 194 */       SpecValue value = new SpecValue();
/* 195 */       value.setSpec_value(map.get("spec_value").toString());
/* 196 */       value.setSpec_value_id(Integer.valueOf(map.get("spec_value_id").toString()));
/* 197 */       String spec_img = (String)map.get("spec_image");
/*     */ 
/* 200 */       if (spec_img != null) {
/* 201 */         spec_img = UploadUtil.replacePath(spec_img);
/*     */       }
/* 203 */       value.setSpec_image(spec_img);
/*     */ 
/* 205 */       valueList.add(value);
/*     */     }
/*     */ 
/* 208 */     return specList;
/*     */   }
/*     */ 
/*     */   public List<Product> list(Integer goodsId)
/*     */   {
/* 218 */     String sql = "select * from product where goods_id=?";
/* 219 */     List prolist = this.baseDaoSupport.queryForList(sql, Product.class, new Object[] { goodsId });
/*     */ 
/* 221 */     sql = "select sv.*,gs.product_id product_id from  " + getTableName("goods_spec") + "  gs inner join " + getTableName("spec_values") + "  sv on gs.spec_value_id = sv.spec_value_id where  gs.goods_id=?";
/*     */ 
/* 225 */     List gsList = this.daoSupport.queryForList(sql, new Object[] { goodsId });
/*     */ 
/* 228 */     List memPriceList = new ArrayList();
/*     */ 
/* 230 */     Member member = UserServiceFactory.getUserService().getCurrentMember();
/* 231 */     double discount = 1.0D;
/* 232 */     if (member != null) {
/* 233 */       memPriceList = this.memberPriceManager.listPriceByGid(goodsId.intValue());
/* 234 */       MemberLv lv = this.memberLvManager.get(member.getLv_id());
/* 235 */       if (lv != null) {
/* 236 */         discount = lv.getDiscount().intValue() / 100.0D;
/*     */       }
/*     */     }
/*     */ 
/* 240 */     for (Iterator i$ = prolist.iterator(); i$.hasNext(); ) { pro = (Product)i$.next();
/*     */ 
/* 242 */       if (member != null) {
/* 243 */         Double price = pro.getPrice();
/* 244 */         if ((memPriceList != null) && (memPriceList.size() > 0))
/* 245 */           price = getMemberPrice(pro.getProduct_id().intValue(), member.getLv_id().intValue(), price, memPriceList, discount);
/* 246 */         pro.setPrice(price);
/*     */       }
/*     */ 
/* 249 */       for (Map gs : gsList)
/*     */       {
/* 252 */         Integer productid = Integer.valueOf(((Integer)gs.get("product_id")).intValue());
/*     */ 
/* 257 */         if (pro.getProduct_id().intValue() == productid.intValue()) {
/* 258 */           SpecValue spec = new SpecValue();
/* 259 */           spec.setSpec_value_id((Integer)gs.get("spec_value_id"));
/* 260 */           spec.setSpec_id((Integer)gs.get("spec_id"));
/* 261 */           String spec_img = (String)gs.get("spec_image");
/*     */ 
/* 264 */           if (spec_img != null) {
/* 265 */             spec_img = UploadUtil.replacePath(spec_img);
/*     */           }
/* 267 */           spec.setSpec_image(spec_img);
/* 268 */           spec.setSpec_value((String)gs.get("spec_value"));
/* 269 */           spec.setSpec_type((Integer)gs.get("spec_type"));
/* 270 */           pro.addSpec(spec);
/*     */         }
/*     */       }
/*     */     }
/*     */     Product pro;
/* 275 */     return prolist;
/*     */   }
/*     */ 
/*     */   private Double getMemberPrice(int productid, int lvid, Double price, List<GoodsLvPrice> memPriceList, double discount)
/*     */   {
/* 286 */     Double memPrice = Double.valueOf(price.doubleValue() * discount);
/*     */ 
/* 289 */     for (GoodsLvPrice lvPrice : memPriceList) {
/* 290 */       if ((lvPrice.getProductid() == productid) && (lvPrice.getLvid() == lvid)) {
/* 291 */         memPrice = lvPrice.getPrice();
/*     */       }
/*     */     }
/* 294 */     return memPrice;
/*     */   }
/*     */ 
/*     */   @Transactional(propagation=Propagation.REQUIRED)
/*     */   public void delete(Integer[] goodsid) {
/* 300 */     String id_str = StringUtil.arrayToString(goodsid, ",");
/* 301 */     String sql = "delete from goods_spec where goods_id in (" + id_str + ")";
/* 302 */     this.baseDaoSupport.execute(sql, new Object[0]);
/*     */ 
/* 304 */     sql = "delete from goods_lv_price where goodsid in (" + id_str + ")";
/* 305 */     this.baseDaoSupport.execute(sql, new Object[0]);
/*     */ 
/* 307 */     sql = "delete from product where goods_id in (" + id_str + ")";
/* 308 */     this.baseDaoSupport.execute(sql, new Object[0]);
/*     */   }
/*     */ 
/*     */   public Page list(String name, String sn, int pageNo, int pageSize, String order)
/*     */   {
/* 314 */     order = order == null ? "product_id asc" : order;
/* 315 */     StringBuffer sql = new StringBuffer();
/* 316 */     sql.append("select p.* from " + getTableName("product") + " p left join " + getTableName("goods") + " g on g.goods_id = p.goods_id ");
/* 317 */     sql.append(" where g.disabled=0");
/* 318 */     if (!StringUtil.isEmpty(name)) {
/* 319 */       sql.append(" and g.name like '%");
/* 320 */       sql.append(name);
/* 321 */       sql.append("%'");
/*     */     }
/* 323 */     if (!StringUtil.isEmpty(sn)) {
/* 324 */       sql.append(" and g.sn = '");
/* 325 */       sql.append(sn);
/* 326 */       sql.append("'");
/*     */     }
/*     */ 
/* 329 */     sql.append(" order by " + order);
/* 330 */     return this.daoSupport.queryForPage(sql.toString(), pageNo, pageSize, new Object[0]);
/*     */   }
/*     */ 
/*     */   public List list(Integer[] productids)
/*     */   {
/* 336 */     if ((productids == null) || (productids.length == 0)) return new ArrayList();
/* 337 */     StringBuffer sql = new StringBuffer();
/* 338 */     sql.append("select p.* from " + getTableName("product") + " p left join " + getTableName("goods") + " g on g.goods_id = p.goods_id ");
/* 339 */     sql.append(" where g.disabled=0");
/* 340 */     sql.append(" and p.product_id in(");
/* 341 */     sql.append(StringUtil.arrayToString(productids, ","));
/* 342 */     sql.append(")");
/*     */ 
/* 344 */     return this.daoSupport.queryForList(sql.toString(), new Object[0]);
/*     */   }
/*     */ 
/*     */   public Product getByGoodsId(Integer goodsid)
/*     */   {
/* 352 */     String sql = "select * from product where goods_id=?";
/* 353 */     List proList = this.baseDaoSupport.queryForList(sql, Product.class, new Object[] { goodsid });
/* 354 */     if ((proList == null) || (proList.isEmpty())) {
/* 355 */       return null;
/*     */     }
/* 357 */     return (Product)proList.get(0);
/*     */   }
/*     */ 
/*     */   public IMemberPriceManager getMemberPriceManager()
/*     */   {
/* 362 */     return this.memberPriceManager;
/*     */   }
/*     */ 
/*     */   public void setMemberPriceManager(IMemberPriceManager memberPriceManager)
/*     */   {
/* 367 */     this.memberPriceManager = memberPriceManager;
/*     */   }
/*     */ 
/*     */   public IMemberLvManager getMemberLvManager()
/*     */   {
/* 372 */     return this.memberLvManager;
/*     */   }
/*     */ 
/*     */   public void setMemberLvManager(IMemberLvManager memberLvManager)
/*     */   {
/* 377 */     this.memberLvManager = memberLvManager;
/*     */   }
/*     */ 
/*     */   public static void main(String[] args) {
/* 381 */     double discount = 0.9D;
/* 382 */     System.out.println(discount);
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.service.impl.ProductManager
 * JD-Core Version:    0.6.0
 */