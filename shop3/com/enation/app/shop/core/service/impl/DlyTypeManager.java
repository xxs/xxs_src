/*     */ package com.enation.app.shop.core.service.impl;
/*     */ 
/*     */ import com.enation.app.base.core.service.IRegionsManager;
/*     */ import com.enation.app.shop.core.model.DlyType;
/*     */ import com.enation.app.shop.core.model.mapper.TypeAreaMapper;
/*     */ import com.enation.app.shop.core.model.support.DlyTypeConfig;
/*     */ import com.enation.app.shop.core.model.support.TypeArea;
/*     */ import com.enation.app.shop.core.model.support.TypeAreaConfig;
/*     */ import com.enation.app.shop.core.service.IDlyTypeManager;
/*     */ import com.enation.eop.sdk.database.BaseSupport;
/*     */ import com.enation.framework.database.IDaoSupport;
/*     */ import com.enation.framework.database.Page;
/*     */ import com.enation.framework.util.StringUtil;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.sf.json.JSONObject;
/*     */ import org.mozilla.javascript.Context;
/*     */ import org.mozilla.javascript.Scriptable;
/*     */ import org.springframework.transaction.annotation.Propagation;
/*     */ import org.springframework.transaction.annotation.Transactional;
/*     */ 
/*     */ public class DlyTypeManager extends BaseSupport<DlyType>
/*     */   implements IDlyTypeManager
/*     */ {
/*     */   private IRegionsManager regionsManager;
/*     */ 
/*     */   public void delete(String id)
/*     */   {
/*  34 */     if ((id == null) || (id.equals("")))
/*  35 */       return;
/*  36 */     String sql = "delete from dly_type_area where type_id in (" + id + ")";
/*  37 */     this.baseDaoSupport.execute(sql, new Object[0]);
/*  38 */     sql = "delete from dly_type where type_id in (" + id + ")";
/*  39 */     this.baseDaoSupport.execute(sql, new Object[0]);
/*     */   }
/*     */ 
/*     */   public DlyType getDlyTypeById(Integer typeId)
/*     */   {
/*  45 */     String sql = "select * from dly_type where type_id=?";
/*  46 */     DlyType dlyType = (DlyType)this.baseDaoSupport.queryForObject(sql, DlyType.class, new Object[] { typeId });
/*     */ 
/*  48 */     if (dlyType.getIs_same().intValue() == 0) {
/*  49 */       dlyType.setTypeAreaList(listAreabyTypeId(dlyType.getType_id()));
/*     */     }
/*  51 */     convertTypeJson(dlyType);
/*  52 */     return dlyType;
/*     */   }
/*     */ 
/*     */   private List listAreabyTypeId(Integer typeid) {
/*  56 */     String sql = "select * from dly_type_area where type_id=?";
/*  57 */     List typeAreaList = this.baseDaoSupport.queryForList(sql, new TypeAreaMapper(), new Object[] { typeid });
/*  58 */     return typeAreaList;
/*     */   }
/*     */ 
/*     */   public List listByAreaId(Integer areaId)
/*     */   {
/*  64 */     String sql = "select a.* ,ta.price price from   " + getTableName("dly_area") + " a left join (select  * from    " + getTableName("dly_type_area") + " where type_id=?)  ta     on ( a.area_id  = ta.area_id  )";
/*  65 */     List l = this.daoSupport.queryForList(sql, new Object[] { areaId });
/*  66 */     return l;
/*     */   }
/*     */ 
/*     */   public List<DlyType> list()
/*     */   {
/*  71 */     String sql = "select * from dly_type order by ordernum";
/*  72 */     return this.baseDaoSupport.queryForList(sql, DlyType.class, new Object[0]);
/*     */   }
/*     */ 
/*     */   public List<DlyType> list(Double weight, Double orderPrice, String regoinId)
/*     */   {
/*  78 */     String sql = "select * from dly_type order by ordernum";
/*  79 */     List typeList = this.baseDaoSupport.queryForList(sql, DlyType.class, new Object[0]);
/*     */ 
/*  81 */     sql = "select * from dly_type_area ";
/*  82 */     List typeAreaList = this.baseDaoSupport.queryForList(sql, new TypeAreaMapper(), new Object[0]);
/*     */ 
/*  84 */     List resultList = new ArrayList();
/*     */ 
/*  86 */     for (DlyType dlyType : typeList)
/*     */     {
/*  88 */       convertTypeJson(dlyType);
/*     */ 
/*  90 */       if (dlyType.getIs_same().intValue() == 0) {
/*  91 */         List areaList = filterTypeArea(dlyType.getType_id(), typeAreaList);
/*  92 */         Double price = countPrice(areaList, weight, orderPrice, regoinId);
/*     */ 
/*  95 */         if ((price == null) && (dlyType.getTypeConfig().getDefAreaFee() != null) && (dlyType.getTypeConfig().getDefAreaFee().compareTo(Integer.valueOf(1)) == 0)) {
/*  96 */           price = countExp(dlyType.getExpressions(), weight, orderPrice);
/*  97 */           if (price.compareTo(Double.valueOf(-1.0D)) != 0) {
/*  98 */             price = null;
/*     */           }
/*     */         }
/*     */ 
/* 102 */         if (price != null) {
/* 103 */           dlyType.setPrice(price);
/* 104 */           resultList.add(dlyType);
/*     */         }
/*     */       }
/*     */       else {
/* 108 */         Double price = countExp(dlyType.getExpressions(), weight, orderPrice);
/* 109 */         if (price.compareTo(Double.valueOf(-1.0D)) != 0) {
/* 110 */           dlyType.setPrice(price);
/* 111 */           resultList.add(dlyType);
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 116 */     return resultList;
/*     */   }
/*     */ 
/*     */   private Double countPrice(List<TypeArea> areaList, Double weight, Double orderPrice, String regoinId)
/*     */   {
/* 131 */     Double price = null;
/* 132 */     for (TypeArea typeArea : areaList) {
/* 133 */       String idGroup = typeArea.getArea_id_group();
/*     */ 
/* 135 */       if ((idGroup == null) || (idGroup.equals("")))
/*     */       {
/*     */         continue;
/*     */       }
/* 139 */       idGroup = idGroup == null ? "" : idGroup;
/* 140 */       String[] idArray = idGroup.split(",");
/*     */ 
/* 143 */       if (StringUtil.isInArray(regoinId, idArray)) {
/* 144 */         Double thePrice = countExp(typeArea.getExpressions(), weight, orderPrice);
/*     */ 
/* 146 */         if (price != null)
/* 147 */           price = thePrice.compareTo(price) > 0 ? thePrice : price;
/*     */         else {
/* 149 */           price = thePrice;
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 154 */     return price;
/*     */   }
/*     */ 
/*     */   private List<TypeArea> filterTypeArea(Integer type_id, List typeAreaList)
/*     */   {
/* 166 */     List areaList = new ArrayList();
/* 167 */     int i = 0; for (int len = typeAreaList.size(); i < len; i++) {
/* 168 */       TypeArea typeArea = (TypeArea)typeAreaList.get(i);
/* 169 */       if (typeArea.getType_id().compareTo(type_id) == 0) {
/* 170 */         areaList.add(typeArea);
/*     */       }
/*     */     }
/*     */ 
/* 174 */     return areaList;
/*     */   }
/*     */ 
/*     */   private void convertTypeJson(DlyType dlyType)
/*     */   {
/* 186 */     String config = dlyType.getConfig();
/* 187 */     JSONObject typeJsonObject = JSONObject.fromObject(config);
/* 188 */     DlyTypeConfig typeConfig = (DlyTypeConfig)JSONObject.toBean(typeJsonObject, DlyTypeConfig.class);
/* 189 */     dlyType.setTypeConfig(typeConfig);
/* 190 */     dlyType.setJson(JSONObject.fromObject(dlyType).toString());
/*     */   }
/*     */ 
/*     */   public Page pageDlyType(int page, int pageSize)
/*     */   {
/* 197 */     String sql = "select  * from  dly_type order by ordernum";
/*     */ 
/* 199 */     Page webpage = this.baseDaoSupport.queryForPage(sql, page, pageSize, new Object[0]);
/* 200 */     return webpage;
/*     */   }
/*     */ 
/*     */   public void add(DlyType type, DlyTypeConfig config)
/*     */   {
/* 210 */     if (type.getIs_same().intValue() == 1) {
/* 211 */       type = fillType(type, config);
/* 212 */       this.baseDaoSupport.insert("dly_type", type);
/*     */     } else {
/* 214 */       throw new RuntimeException("type not is same config,cant'add");
/*     */     }
/*     */   }
/*     */ 
/*     */   @Transactional(propagation=Propagation.REQUIRED)
/*     */   public void add(DlyType type, DlyTypeConfig config, TypeAreaConfig[] configArray)
/*     */   {
/* 227 */     type = fillType(type, config);
/*     */ 
/* 229 */     this.baseDaoSupport.insert("dly_type", type);
/* 230 */     Integer typeId = Integer.valueOf(this.baseDaoSupport.getLastId("dly_type"));
/* 231 */     addTypeArea(typeId, configArray);
/*     */   }
/*     */ 
/*     */   public void edit(DlyType type, DlyTypeConfig config)
/*     */   {
/* 238 */     if (type.getType_id() == null) {
/* 239 */       throw new RuntimeException("type id is null ,can't edit");
/*     */     }
/*     */ 
/* 242 */     if (type.getIs_same().intValue() == 1) {
/* 243 */       Integer typeId = type.getType_id();
/* 244 */       this.baseDaoSupport.execute("delete from dly_type_area where type_id=?", new Object[] { typeId });
/* 245 */       type = fillType(type, config);
/* 246 */       this.baseDaoSupport.update("dly_type", type, "type_id=" + type.getType_id());
/*     */     } else {
/* 248 */       throw new RuntimeException("type  is not same config,cant'edit");
/*     */     }
/*     */   }
/*     */ 
/*     */   private DlyType fillType(DlyType type, DlyTypeConfig config) {
/* 253 */     Double firstprice = config.getFirstprice();
/* 254 */     Double continueprice = config.getContinueprice();
/* 255 */     Integer firstunit = config.getFirstunit();
/* 256 */     Integer continueunit = config.getContinueunit();
/*     */ 
/* 261 */     String expressions = null;
/*     */ 
/* 263 */     if (config.getUseexp().intValue() == 0)
/* 264 */       expressions = createExpression(firstprice, continueprice, firstunit, continueunit);
/*     */     else {
/* 266 */       expressions = config.getExpression();
/*     */     }
/*     */ 
/* 269 */     type.setExpressions(expressions);
/* 270 */     type.setConfig(JSONObject.fromObject(config).toString());
/* 271 */     return type;
/*     */   }
/*     */ 
/*     */   @Transactional(propagation=Propagation.REQUIRED)
/*     */   public void edit(DlyType type, DlyTypeConfig config, TypeAreaConfig[] configArray)
/*     */   {
/* 279 */     if (type.getType_id() == null) {
/* 280 */       throw new RuntimeException("type id is null ,can't edit");
/*     */     }
/*     */ 
/* 283 */     type = fillType(type, config);
/*     */ 
/* 285 */     Integer typeId = type.getType_id();
/* 286 */     this.baseDaoSupport.execute("delete from dly_type_area where type_id=?", new Object[] { typeId });
/*     */ 
/* 288 */     addTypeArea(typeId, configArray);
/*     */ 
/* 290 */     this.baseDaoSupport.update("dly_type", type, "type_id=" + type.getType_id());
/*     */   }
/*     */ 
/*     */   private void addTypeArea(Integer typeId, TypeAreaConfig[] configArray)
/*     */   {
/* 295 */     for (TypeAreaConfig areaConfig : configArray) {
/* 296 */       String[] idArray = areaConfig.getAreaId().split(",");
/* 297 */       String closeAreaId = "";
/* 298 */       String checkedAreaId = "";
/*     */ 
/* 302 */       for (String id : idArray) {
/* 303 */         if (!checkedAreaId.equals("")) checkedAreaId = checkedAreaId + ",";
/* 304 */         String[] idarray = id.split("\\|");
/* 305 */         if (idarray.length > 1) {
/* 306 */           if (!closeAreaId.equals("")) closeAreaId = closeAreaId + ",";
/* 307 */           closeAreaId = closeAreaId + idarray[0];
/* 308 */           checkedAreaId = checkedAreaId + idarray[0];
/*     */         } else {
/* 310 */           checkedAreaId = checkedAreaId + id;
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 316 */       List areaIdList = this.regionsManager.listChildren(closeAreaId);
/*     */ 
/* 318 */       for (Integer childId : areaIdList) {
/* 319 */         checkedAreaId = checkedAreaId + "," + childId;
/*     */       }
/* 321 */       TypeArea typeArea = new TypeArea();
/* 322 */       typeArea.setArea_id_group(checkedAreaId);
/* 323 */       typeArea.setArea_name_group(areaConfig.getAreaName());
/* 324 */       typeArea.setType_id(typeId);
/* 325 */       typeArea.setHas_cod(areaConfig.getHave_cod());
/*     */ 
/* 327 */       typeArea.setConfig(JSONObject.fromObject(areaConfig).toString());
/* 328 */       String expressions = "";
/* 329 */       if (areaConfig.getUseexp().intValue() == 1) {
/* 330 */         expressions = areaConfig.getExpression();
/*     */       }
/*     */       else {
/* 333 */         expressions = createExpression(areaConfig);
/*     */       }
/* 335 */       typeArea.setExpressions(expressions);
/* 336 */       this.baseDaoSupport.insert("dly_type_area", typeArea);
/*     */     }
/*     */   }
/*     */ 
/*     */   private String createExpression(TypeAreaConfig areaConfig)
/*     */   {
/* 347 */     return createExpression(areaConfig.getFirstprice(), areaConfig.getContinueprice(), areaConfig.getFirstunit(), areaConfig.getContinueunit());
/*     */   }
/*     */ 
/*     */   private String createExpression(Double firstprice, Double continueprice, Integer firstunit, Integer continueunit)
/*     */   {
/* 359 */     return firstprice + "+tint(w-" + firstunit + ")/" + continueunit + "*" + continueprice;
/*     */   }
/*     */ 
/*     */   public IRegionsManager getRegionsManager()
/*     */   {
/* 365 */     return this.regionsManager;
/*     */   }
/*     */ 
/*     */   public void setRegionsManager(IRegionsManager regionsManager) {
/* 369 */     this.regionsManager = regionsManager;
/*     */   }
/*     */ 
/*     */   public Double countExp(String exp, Double weight, Double orderprice)
/*     */   {
/* 374 */     Context cx = Context.enter();
/*     */     try
/*     */     {
/* 377 */       Scriptable scope = cx.initStandardObjects();
/* 378 */       String str = "var w=" + weight + ";";
/* 379 */       str = str + "p=" + orderprice + ";";
/* 380 */       str = str + "function tint(value){return value<0?0:value;}";
/* 381 */       str = str + exp;
/* 382 */       Object result = cx.evaluateString(scope, str, null, 1, null);
/* 383 */       Double res = Double.valueOf(Context.toNumber(result));
/*     */ 
/* 385 */       Double localDouble1 = res;
/*     */       return localDouble1;
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 387 */       e.printStackTrace();
/*     */     }
/*     */     finally
/*     */     {
/* 391 */       Context.exit();
/*     */     }
/* 393 */     return Double.valueOf(-1.0D);
/*     */   }
/*     */ 
/*     */   public Double[] countPrice(Integer typeId, Double weight, Double orderPrice, String regionId, boolean isProtected)
/*     */   {
/* 399 */     DlyType dlyType = getDlyTypeById(typeId);
/* 400 */     Double dlyPrice = null;
/* 401 */     if (dlyType.getIs_same().compareTo(Integer.valueOf(1)) == 0)
/* 402 */       dlyPrice = countExp(dlyType.getExpressions(), weight, orderPrice);
/*     */     else {
/* 404 */       dlyPrice = countPrice(dlyType.getTypeAreaList(), weight, orderPrice, regionId);
/*     */     }
/* 406 */     Double protectPrice = null;
/* 407 */     if (isProtected) {
/* 408 */       Float protectRate = dlyType.getProtect_rate();
/* 409 */       protectPrice = Double.valueOf(orderPrice.doubleValue() * protectRate.floatValue() / 100.0D);
/* 410 */       protectPrice = Double.valueOf(dlyType.getMin_price().floatValue() > protectPrice.doubleValue() ? dlyType.getMin_price().floatValue() : protectPrice.doubleValue());
/*     */     }
/* 412 */     Double[] priceArray = { dlyPrice, protectPrice };
/* 413 */     return priceArray;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.service.impl.DlyTypeManager
 * JD-Core Version:    0.6.0
 */