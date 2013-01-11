/*     */ package com.enation.app.shop.core.service.impl;
/*     */ 
/*     */ import com.enation.app.shop.core.model.Attribute;
/*     */ import com.enation.app.shop.core.model.GoodsParam;
/*     */ import com.enation.app.shop.core.model.GoodsType;
/*     */ import com.enation.app.shop.core.model.support.GoodsTypeDTO;
/*     */ import com.enation.app.shop.core.model.support.ParamGroup;
/*     */ import com.enation.app.shop.core.service.GoodsTypeUtil;
/*     */ import com.enation.app.shop.core.service.IGoodsTypeManager;
/*     */ import com.enation.eop.sdk.database.BaseSupport;
/*     */ import com.enation.framework.database.IDaoSupport;
/*     */ import com.enation.framework.database.Page;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.sf.json.JSONArray;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.logging.LogFactory;
/*     */ import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
/*     */ import org.springframework.transaction.annotation.Propagation;
/*     */ import org.springframework.transaction.annotation.Transactional;
/*     */ 
/*     */ public class GoodsTypeManager extends BaseSupport<GoodsType>
/*     */   implements IGoodsTypeManager
/*     */ {
/*  31 */   private static final Log loger = LogFactory.getLog(GoodsTypeManager.class);
/*     */   private SimpleJdbcTemplate simpleJdbcTemplate;
/*     */ 
/*     */   public List listAll()
/*     */   {
/*  35 */     String sql = "select * from goods_type where disabled=0";
/*  36 */     List typeList = this.baseDaoSupport.queryForList(sql, GoodsType.class, new Object[0]);
/*     */ 
/*  38 */     return typeList;
/*     */   }
/*     */ 
/*     */   public Page pageType(String order, int page, int pageSize)
/*     */   {
/*  49 */     order = order == null ? " type_id desc" : order;
/*     */ 
/*  51 */     String sql = "select * from goods_type where disabled=0";
/*  52 */     sql = sql + "  order by ";
/*  53 */     sql = sql + order;
/*     */ 
/*  55 */     Page webpage = this.baseDaoSupport.queryForPage(sql, page, pageSize, new Object[0]);
/*  56 */     return webpage;
/*     */   }
/*     */ 
/*     */   public Page pageTrashType(String order, int page, int pageSize)
/*     */   {
/*  68 */     order = order == null ? " type_id desc" : order;
/*     */ 
/*  70 */     String sql = "select * from goods_type where disabled=1";
/*  71 */     sql = sql + "  order by ";
/*  72 */     sql = sql + order;
/*     */ 
/*  74 */     Page webpage = this.baseDaoSupport.queryForPage(sql, page, pageSize, new Object[0]);
/*  75 */     return webpage;
/*     */   }
/*     */ 
/*     */   public GoodsTypeDTO get(Integer type_id)
/*     */   {
/*  88 */     String sql = "select * from goods_type where type_id=?";
/*  89 */     GoodsTypeDTO type = (GoodsTypeDTO)this.baseDaoSupport.queryForObject(sql, GoodsTypeDTO.class, new Object[] { type_id });
/*  90 */     if (type == null) {
/*  91 */       return null;
/*     */     }
/*  93 */     List propList = GoodsTypeUtil.converAttrFormString(type.getProps());
/*  94 */     ParamGroup[] paramGroups = GoodsTypeUtil.converFormString(type.getParams());
/*  95 */     List brandList = getBrandListByTypeId(type_id.intValue());
/*  96 */     type.setPropList(propList);
/*  97 */     type.setParamGroups(paramGroups);
/*  98 */     type.setBrandList(brandList);
/*  99 */     return type;
/*     */   }
/*     */ 
/*     */   public GoodsType getById(int typeid)
/*     */   {
/* 104 */     String sql = "select * from goods_type where type_id=?";
/* 105 */     return (GoodsType)this.baseDaoSupport.queryForObject(sql, GoodsType.class, new Object[] { Integer.valueOf(typeid) });
/*     */   }
/*     */ 
/*     */   public List getBrandListByTypeId(int type_id)
/*     */   {
/* 116 */     String sql = "select b.name name ,b.brand_id brand_id,0 as num from " + getTableName("type_brand") + " tb inner join " + getTableName("brand") + " b  on    b.brand_id = tb.brand_id where tb.type_id=? and b.disabled=0";
/*     */ 
/* 118 */     List list = this.daoSupport.queryForList(sql, new Object[] { Integer.valueOf(type_id) });
/* 119 */     return list;
/*     */   }
/*     */ 
/*     */   public List listByTypeId(Integer typeid)
/*     */   {
/* 125 */     String sql = "select b.* from " + getTableName("type_brand") + " tb inner join " + getTableName("brand") + " b  on    b.brand_id = tb.brand_id where tb.type_id=? and b.disabled=0";
/*     */ 
/* 127 */     List list = this.daoSupport.queryForList(sql, new Object[] { typeid });
/*     */ 
/* 129 */     return list;
/*     */   }
/*     */ 
/*     */   public static List<Attribute> converAttrFormString(String props)
/*     */   {
/* 142 */     if ((props == null) || (props.equals(""))) {
/* 143 */       return new ArrayList();
/*     */     }
/* 145 */     JSONArray jsonArray = JSONArray.fromObject(props);
/* 146 */     List list = (List)JSONArray.toCollection(jsonArray, Attribute.class);
/*     */ 
/* 149 */     return list;
/*     */   }
/*     */ 
/*     */   public List<Attribute> getAttrListByTypeId(int type_id)
/*     */   {
/* 161 */     GoodsTypeDTO type = get(Integer.valueOf(type_id));
/* 162 */     if (type.getHave_prop() == 0) return new ArrayList();
/* 163 */     return type.getPropList();
/*     */   }
/*     */ 
/*     */   public ParamGroup[] getParamArByTypeId(int type_id)
/*     */   {
/* 176 */     String params = getParamsByTypeId(type_id);
/* 177 */     return GoodsTypeUtil.converFormString(params);
/*     */   }
/*     */ 
/*     */   private String getParamsByTypeId(int type_id)
/*     */   {
/* 191 */     String sql = "select params from goods_type where disabled=0 and type_id=" + type_id;
/* 192 */     IDaoSupport strDaoSuport = this.baseDaoSupport;
/*     */ 
/* 194 */     String props = strDaoSuport.queryForString(sql);
/*     */ 
/* 196 */     return props;
/*     */   }
/*     */ 
/*     */   public String getPropsString(String[] propnames, int[] proptypes, String[] options, String[] unit, int[] required, String[] datatype)
/*     */   {
/* 211 */     List list = toAttrList(propnames, proptypes, options, unit, required, datatype);
/* 212 */     JSONArray jsonarray = JSONArray.fromObject(list);
/*     */ 
/* 214 */     return jsonarray.toString();
/*     */   }
/*     */ 
/*     */   public String getParamString(String[] paramnums, String[] groupnames, String[] paramnames, String[] paramvalues)
/*     */   {
/* 230 */     List list = toParamList(paramnums, groupnames, paramnames, paramvalues);
/* 231 */     JSONArray jsonarray = JSONArray.fromObject(list);
/* 232 */     return jsonarray.toString();
/*     */   }
/*     */ 
/*     */   @Transactional(propagation=Propagation.REQUIRED)
/*     */   public Integer save(GoodsType type)
/*     */   {
/* 240 */     String typeTableName = "goods_type";
/* 241 */     String tbTableName = "type_brand";
/* 242 */     Integer[] brand_id = type.getBrand_ids();
/*     */ 
/* 244 */     type.setBrand_ids(null);
/* 245 */     if ((type.getParams() != null) && (type.getParams().equals("[]"))) {
/* 246 */       type.setParams(null);
/*     */     }
/* 248 */     Integer type_id = null;
/* 249 */     if (type.getType_id() != null) {
/* 250 */       type_id = type.getType_id();
/* 251 */       if (type.getHave_prop() == 0) {
/* 252 */         type.setProps(null);
/*     */       }
/* 254 */       if (type.getHave_parm() == 0) {
/* 255 */         type.setParams(null);
/*     */       }
/* 257 */       this.baseDaoSupport.update(typeTableName, type, "type_id=" + type_id);
/* 258 */       String sql = "delete from " + getTableName("type_brand") + " where type_id = ?";
/* 259 */       this.simpleJdbcTemplate.update(sql, new Object[] { type_id });
/*     */     }
/*     */     else {
/* 262 */       this.baseDaoSupport.insert(typeTableName, type);
/* 263 */       type_id = Integer.valueOf(this.baseDaoSupport.getLastId(typeTableName));
/* 264 */       if (loger.isDebugEnabled()) {
/* 265 */         loger.debug("增加商品类型成功 , id is " + type_id);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 270 */     if (brand_id != null) {
/* 271 */       for (int i = 0; i < brand_id.length; i++)
/*     */       {
/* 273 */         String sql = "insert into  " + getTableName("type_brand") + "(type_id,brand_id) values(?,?)";
/* 274 */         this.simpleJdbcTemplate.update(sql, new Object[] { type_id, brand_id[i] });
/*     */       }
/*     */     }
/*     */ 
/* 278 */     return type_id;
/*     */   }
/*     */ 
/*     */   private List<Attribute> toAttrList(String[] propnames, int[] proptypes, String[] options, String[] unit, int[] required, String[] datatype)
/*     */   {
/* 292 */     List attrList = new ArrayList();
/*     */ 
/* 294 */     if ((propnames != null) && (proptypes != null) && (options != null)) {
/* 295 */       for (int i = 0; i < propnames.length; i++)
/*     */       {
/* 297 */         Attribute attribute = new Attribute();
/* 298 */         String name = propnames[i];
/* 299 */         String option = options[i];
/* 300 */         int type = proptypes[i];
/*     */ 
/* 302 */         attribute.setName(name);
/* 303 */         attribute.setOptions(option);
/* 304 */         attribute.setType(type);
/* 305 */         attribute.setDatatype(datatype[i]);
/* 306 */         attribute.setRequired(required[i]);
/* 307 */         attribute.setUnit(unit[i]);
/* 308 */         attrList.add(attribute);
/*     */       }
/*     */     }
/*     */ 
/* 312 */     return attrList;
/*     */   }
/*     */ 
/*     */   private List<ParamGroup> toParamList(String[] ar_paramnum, String[] groupnames, String[] paramnames, String[] paramvalues)
/*     */   {
/* 326 */     List list = new ArrayList();
/* 327 */     if (groupnames != null) {
/* 328 */       for (int i = 0; i < groupnames.length; i++) {
/* 329 */         ParamGroup paramGroup = new ParamGroup();
/* 330 */         paramGroup.setName(groupnames[i]);
/* 331 */         List paramList = getParamList(ar_paramnum, paramnames, paramvalues, i);
/*     */ 
/* 333 */         paramGroup.setParamList(paramList);
/* 334 */         list.add(paramGroup);
/*     */       }
/*     */     }
/* 337 */     return list;
/*     */   }
/*     */ 
/*     */   private List<GoodsParam> getParamList(String[] ar_paramnum, String[] paramnames, String[] paramvalues, int groupindex)
/*     */   {
/* 351 */     int[] pos = countPos(groupindex, ar_paramnum);
/* 352 */     List list = new ArrayList();
/* 353 */     for (int i = pos[0]; i < pos[1]; i++) {
/* 354 */       GoodsParam goodsParam = new GoodsParam();
/*     */ 
/* 356 */       goodsParam.setName(paramnames[i]);
/*     */ 
/* 358 */       if (paramvalues != null) {
/* 359 */         String value = paramvalues[i];
/* 360 */         goodsParam.setValue(value);
/*     */       }
/*     */ 
/* 363 */       list.add(goodsParam);
/*     */     }
/* 365 */     return list;
/*     */   }
/*     */ 
/*     */   private int[] countPos(int groupindex, String[] ar_paramnum)
/*     */   {
/* 377 */     int index = 0;
/* 378 */     int start = 0;
/* 379 */     int end = 0;
/* 380 */     int[] r = new int[2];
/* 381 */     for (int i = 0; i <= groupindex; i++) {
/* 382 */       int p_num = Integer.valueOf(ar_paramnum[i]).intValue();
/*     */ 
/* 384 */       index += p_num;
/* 385 */       if (i == groupindex - 1) {
/* 386 */         start = index;
/*     */       }
/*     */ 
/* 389 */       if (i == groupindex) {
/* 390 */         end = index;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 395 */     r[0] = start;
/* 396 */     r[1] = end;
/*     */ 
/* 398 */     return r;
/*     */   }
/*     */ 
/*     */   private boolean checkUsed(Integer[] type_ids)
/*     */   {
/* 409 */     String sql = "select count(0) from goods_cat where type_id in";
/* 410 */     return false;
/*     */   }
/*     */ 
/*     */   public int delete(Integer[] type_ids)
/*     */   {
/* 419 */     if (type_ids == null) return 1;
/*     */ 
/* 421 */     String ids = "";
/* 422 */     for (int i = 0; i < type_ids.length; i++) {
/* 423 */       if (i != 0)
/* 424 */         ids = ids + ",";
/* 425 */       ids = ids + type_ids[i];
/*     */     }
/* 427 */     String sql = "select count(0) from " + getTableName("goods") + " where type_id in (" + ids + ")";
/* 428 */     int count = this.daoSupport.queryForInt(sql, new Object[0]);
/*     */ 
/* 430 */     sql = "select count(0) from goods_cat where type_id in (" + ids + ")";
/* 431 */     int catcout = this.baseDaoSupport.queryForInt(sql, new Object[0]);
/* 432 */     if (catcout > 0) {
/* 433 */       return 0;
/*     */     }
/*     */ 
/* 436 */     if (count == 0) {
/* 437 */       sql = "update  goods_type set disabled=1  where type_id in (" + ids + ")";
/* 438 */       this.baseDaoSupport.execute(sql, new Object[0]);
/* 439 */       return 1;
/*     */     }
/* 441 */     return 0;
/*     */   }
/*     */ 
/*     */   @Transactional(propagation=Propagation.REQUIRED)
/*     */   public void clean(Integer[] type_ids)
/*     */   {
/* 454 */     if (type_ids == null) return;
/* 455 */     String ids = "";
/* 456 */     for (int i = 0; i < type_ids.length; i++) {
/* 457 */       if (i != 0)
/* 458 */         ids = ids + ",";
/* 459 */       ids = ids + type_ids[i];
/*     */     }
/* 461 */     String sql = "delete from goods_type where type_id in(" + ids + ")";
/* 462 */     this.baseDaoSupport.execute(sql, new Object[0]);
/*     */ 
/* 464 */     sql = "delete from " + getTableName("type_brand") + " where type_id in(" + ids + ")";
/* 465 */     this.simpleJdbcTemplate.update(sql, new Object[0]);
/*     */   }
/*     */ 
/*     */   public void revert(Integer[] type_ids)
/*     */   {
/* 478 */     if (type_ids == null) return;
/* 479 */     String ids = "";
/* 480 */     for (int i = 0; i < type_ids.length; i++) {
/* 481 */       if (i != 0)
/* 482 */         ids = ids + ",";
/* 483 */       ids = ids + type_ids[i];
/*     */     }
/* 485 */     String sql = "update  goods_type set disabled=0  where type_id in (" + ids + ")";
/* 486 */     this.baseDaoSupport.execute(sql, new Object[0]);
/*     */   }
/*     */ 
/*     */   public void setSimpleJdbcTemplate(SimpleJdbcTemplate simpleJdbcTemplate)
/*     */   {
/* 491 */     this.simpleJdbcTemplate = simpleJdbcTemplate;
/*     */   }
/*     */   public boolean checkname(String name, Integer typeid) {
/* 494 */     if (name != null) name = name.trim();
/* 495 */     String sql = "select count(0) from goods_type where name=? and type_id!=? and disabled=0";
/* 496 */     if (typeid == null) typeid = Integer.valueOf(0);
/* 497 */     int count = this.baseDaoSupport.queryForInt(sql, new Object[] { name, typeid });
/*     */ 
/* 499 */     return count > 0;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.service.impl.GoodsTypeManager
 * JD-Core Version:    0.6.0
 */