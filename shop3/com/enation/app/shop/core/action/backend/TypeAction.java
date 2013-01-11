/*     */ package com.enation.app.shop.core.action.backend;
/*     */ 
/*     */ import com.enation.app.shop.core.model.GoodsType;
/*     */ import com.enation.app.shop.core.model.support.GoodsTypeDTO;
/*     */ import com.enation.app.shop.core.model.support.ParamGroup;
/*     */ import com.enation.app.shop.core.model.support.TypeSaveState;
/*     */ import com.enation.app.shop.core.service.IBrandManager;
/*     */ import com.enation.app.shop.core.service.IGoodsTypeManager;
/*     */ import com.enation.framework.action.WWAction;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class TypeAction extends WWAction
/*     */ {
/*     */   private IGoodsTypeManager goodsTypeManager;
/*     */   private IBrandManager brandManager;
/*     */   private List brandlist;
/*     */   private GoodsTypeDTO goodsType;
/*     */   private String[] propnames;
/*     */   private int[] proptypes;
/*     */   private String[] options;
/*     */   private String[] datatype;
/*     */   private int[] required;
/*     */   private String[] unit;
/*     */   private String paramnum;
/*     */   private String[] groupnames;
/*     */   private String[] paramnames;
/*     */   private Integer type_id;
/*     */   private int is_edit;
/*     */   private Integer[] id;
/*     */   private Integer[] chhoose_brands;
/*  56 */   private static String GOODSTYPE_SESSION_KEY = "goods_type_in_session";
/*     */ 
/*  58 */   private static String GOODSTYPE_STATE_SESSION_KEY = "goods_type_state_in_session";
/*     */   private String order;
/*     */   private List attrList;
/*     */   private ParamGroup[] paramAr;
/*     */ 
/*     */   public String getOrder()
/*     */   {
/*  63 */     return this.order;
/*     */   }
/*     */ 
/*     */   public void setOrder(String order) {
/*  67 */     this.order = order;
/*     */   }
/*     */ 
/*     */   public String checkname()
/*     */   {
/*  72 */     if (this.goodsTypeManager.checkname(this.goodsType.getName(), this.goodsType.getType_id()))
/*  73 */       this.json = "{result:1}";
/*     */     else {
/*  75 */       this.json = "{result:0}";
/*     */     }
/*  77 */     return "json_message";
/*     */   }
/*     */ 
/*     */   public String list()
/*     */   {
/*  82 */     this.webpage = this.goodsTypeManager.pageType(this.order, getPage(), getPageSize());
/*     */ 
/*  84 */     return "list";
/*     */   }
/*     */ 
/*     */   public String trash_list()
/*     */   {
/*  90 */     this.webpage = this.goodsTypeManager.pageTrashType(this.order, getPage(), getPageSize());
/*     */ 
/*  92 */     return "trash_list";
/*     */   }
/*     */ 
/*     */   public String step1()
/*     */   {
/* 102 */     return "step1";
/*     */   }
/*     */ 
/*     */   public String step2()
/*     */   {
/* 109 */     TypeSaveState saveState = new TypeSaveState();
/* 110 */     this.session.put(GOODSTYPE_STATE_SESSION_KEY, saveState);
/*     */ 
/* 112 */     GoodsType tempType = getTypeFromSession();
/* 113 */     if (tempType == null)
/*     */     {
/* 115 */       this.session.put(GOODSTYPE_SESSION_KEY, this.goodsType);
/*     */     }
/* 118 */     else if (this.is_edit == 1) {
/* 119 */       tempType.setHave_parm(this.goodsType.getHave_parm());
/* 120 */       tempType.setHave_prop(this.goodsType.getHave_prop());
/* 121 */       tempType.setJoin_brand(this.goodsType.getJoin_brand());
/* 122 */       tempType.setIs_physical(this.goodsType.getIs_physical());
/* 123 */       tempType.setHave_field(this.goodsType.getHave_field());
/* 124 */       tempType.setName(this.goodsType.getName());
/*     */     } else {
/* 126 */       this.session.put(GOODSTYPE_SESSION_KEY, this.goodsType);
/*     */     }
/*     */ 
/* 130 */     String result = getResult();
/* 131 */     if (result == null) {
/* 132 */       renderText("参数不正确！");
/*     */     }
/* 134 */     return result;
/*     */   }
/*     */ 
/*     */   public String edit()
/*     */   {
/* 144 */     this.goodsType = this.goodsTypeManager.get(this.type_id);
/* 145 */     this.session.put(GOODSTYPE_SESSION_KEY, this.goodsType);
/* 146 */     this.is_edit = 1;
/* 147 */     return "edit";
/*     */   }
/*     */ 
/*     */   public String saveParams()
/*     */   {
/* 156 */     String[] paramnums = new String[0];
/* 157 */     if (this.paramnum != null) {
/* 158 */       if (this.paramnum.indexOf(",-1") >= 0) {
/* 159 */         this.paramnum = this.paramnum.replaceAll(",-1", "");
/*     */       }
/* 161 */       paramnums = this.paramnum.split(",");
/*     */     }
/*     */ 
/* 164 */     String params = this.goodsTypeManager.getParamString(paramnums, this.groupnames, this.paramnames, null);
/*     */ 
/* 166 */     GoodsType tempType = getTypeFromSession();
/* 167 */     TypeSaveState saveState = getSaveStateFromSession();
/* 168 */     tempType.setParams(params);
/* 169 */     saveState.setDo_save_params(1);
/* 170 */     return getResult();
/*     */   }
/*     */ 
/*     */   public String saveProps()
/*     */   {
/* 180 */     String props = this.goodsTypeManager.getPropsString(this.propnames, this.proptypes, this.options, this.unit, this.required, this.datatype);
/*     */ 
/* 182 */     GoodsType tempType = getTypeFromSession();
/* 183 */     tempType.setProps(props);
/*     */ 
/* 186 */     TypeSaveState saveState = getSaveStateFromSession();
/* 187 */     saveState.setDo_save_props(1);
/* 188 */     return getResult();
/*     */   }
/*     */ 
/*     */   public String saveBrand()
/*     */   {
/* 197 */     GoodsType tempType = getTypeFromSession();
/* 198 */     tempType.setBrand_ids(this.chhoose_brands);
/*     */ 
/* 201 */     TypeSaveState saveState = getSaveStateFromSession();
/* 202 */     saveState.setDo_save_brand(1);
/* 203 */     return getResult();
/*     */   }
/*     */ 
/*     */   public String save()
/*     */   {
/* 210 */     GoodsTypeDTO tempType = getTypeFromSession();
/* 211 */     tempType.setDisabled(0);
/* 212 */     tempType.setBrandList(null);
/* 213 */     tempType.setPropList(null);
/* 214 */     tempType.setParamGroups(null);
/*     */ 
/* 216 */     this.type_id = this.goodsTypeManager.save(tempType);
/* 217 */     this.session.remove(GOODSTYPE_SESSION_KEY);
/*     */ 
/* 220 */     if (tempType.getHave_field() == 0) {
/* 221 */       this.msgs.add("商品类型保存成功");
/* 222 */       this.urls.put("商品类型列表", "type!list.do");
/* 223 */       return "message";
/*     */     }
/* 225 */     return "field";
/*     */   }
/*     */ 
/*     */   private GoodsTypeDTO getTypeFromSession()
/*     */   {
/* 234 */     Object obj = this.session.get(GOODSTYPE_SESSION_KEY);
/*     */ 
/* 236 */     if (obj == null)
/*     */     {
/* 238 */       return null;
/*     */     }
/*     */ 
/* 241 */     GoodsTypeDTO tempType = (GoodsTypeDTO)obj;
/*     */ 
/* 243 */     return tempType;
/*     */   }
/*     */ 
/*     */   private TypeSaveState getSaveStateFromSession()
/*     */   {
/* 255 */     Object obj = this.session.get(GOODSTYPE_STATE_SESSION_KEY);
/* 256 */     if (obj == null) {
/* 257 */       renderText("参数不正确");
/* 258 */       return null;
/*     */     }
/* 260 */     TypeSaveState tempType = (TypeSaveState)obj;
/* 261 */     return tempType;
/*     */   }
/*     */ 
/*     */   private String getResult()
/*     */   {
/* 272 */     GoodsType tempType = getTypeFromSession();
/* 273 */     this.goodsType = getTypeFromSession();
/* 274 */     TypeSaveState saveState = getSaveStateFromSession();
/* 275 */     String result = null;
/*     */ 
/* 277 */     if ((tempType.getHave_prop() != 0) && (saveState.getDo_save_props() == 0)) {
/* 278 */       result = "add_props";
/* 279 */     } else if ((tempType.getHave_parm() != 0) && (saveState.getDo_save_params() == 0))
/*     */     {
/* 281 */       result = "add_parms";
/* 282 */     } else if ((tempType.getJoin_brand() != 0) && (saveState.getDo_save_brand() == 0))
/*     */     {
/* 284 */       this.brandlist = this.brandManager.list();
/* 285 */       result = "join_brand";
/*     */     }
/*     */     else {
/* 288 */       result = save();
/*     */     }
/*     */ 
/* 291 */     return result;
/*     */   }
/*     */ 
/*     */   public String delete()
/*     */   {
/*     */     try
/*     */     {
/* 301 */       int result = this.goodsTypeManager.delete(this.id);
/* 302 */       if (result == 1)
/* 303 */         this.json = "{'result':0,'message':'删除成功'}";
/*     */       else
/* 305 */         this.json = "{'result':1,'message':'此类型存在与之关联的商品或类别，不能删除。'}";
/*     */     } catch (RuntimeException e) {
/* 307 */       e.printStackTrace();
/* 308 */       this.json = "{'result':1,'message':'删除失败'}";
/*     */     }
/* 310 */     return "json_message";
/*     */   }
/*     */ 
/*     */   public String clean()
/*     */   {
/*     */     try
/*     */     {
/* 320 */       this.goodsTypeManager.clean(this.id);
/* 321 */       this.json = "{'result':0,'message':'清除成功'}";
/*     */     } catch (RuntimeException e) {
/* 323 */       this.json = "{'result':1,'message':'清除失败'}";
/*     */     }
/* 325 */     return "json_message";
/*     */   }
/*     */ 
/*     */   public String revert()
/*     */   {
/*     */     try
/*     */     {
/* 335 */       this.goodsTypeManager.revert(this.id);
/* 336 */       this.json = "{'result':0,'message':'还原成功'}";
/*     */     } catch (RuntimeException e) {
/* 338 */       this.json = "{'result':1,'message':'还原失败'}";
/*     */     }
/* 340 */     return "json_message";
/*     */   }
/*     */ 
/*     */   public String disPropsInput()
/*     */   {
/* 350 */     this.attrList = this.goodsTypeManager.getAttrListByTypeId(this.type_id.intValue());
/* 351 */     this.attrList = ((this.attrList == null) || (this.attrList.isEmpty()) ? null : this.attrList);
/* 352 */     return "props_input";
/*     */   }
/*     */ 
/*     */   public String disParamsInput()
/*     */   {
/* 359 */     this.paramAr = this.goodsTypeManager.getParamArByTypeId(this.type_id.intValue());
/* 360 */     return "params_input";
/*     */   }
/*     */ 
/*     */   public String listBrand()
/*     */   {
/* 366 */     this.brandlist = this.goodsTypeManager.listByTypeId(this.type_id);
/* 367 */     return "brand_list";
/*     */   }
/*     */ 
/*     */   public List getAttrList() {
/* 371 */     return this.attrList;
/*     */   }
/*     */ 
/*     */   public void setAttrList(List attrList) {
/* 375 */     this.attrList = attrList;
/*     */   }
/*     */ 
/*     */   public ParamGroup[] getParamAr() {
/* 379 */     return this.paramAr;
/*     */   }
/*     */ 
/*     */   public void setParamAr(ParamGroup[] paramAr) {
/* 383 */     this.paramAr = paramAr;
/*     */   }
/*     */ 
/*     */   public GoodsTypeDTO getGoodsType() {
/* 387 */     return this.goodsType;
/*     */   }
/*     */ 
/*     */   public void setGoodsType(GoodsTypeDTO goodsType) {
/* 391 */     this.goodsType = goodsType;
/*     */   }
/*     */ 
/*     */   public String[] getPropnames() {
/* 395 */     return this.propnames;
/*     */   }
/*     */ 
/*     */   public void setPropnames(String[] propnames) {
/* 399 */     this.propnames = propnames;
/*     */   }
/*     */ 
/*     */   public int[] getProptypes() {
/* 403 */     return this.proptypes;
/*     */   }
/*     */ 
/*     */   public void setProptypes(int[] proptypes) {
/* 407 */     this.proptypes = proptypes;
/*     */   }
/*     */ 
/*     */   public String[] getOptions() {
/* 411 */     return this.options;
/*     */   }
/*     */ 
/*     */   public void setOptions(String[] options) {
/* 415 */     this.options = options;
/*     */   }
/*     */ 
/*     */   public IGoodsTypeManager getGoodsTypeManager() {
/* 419 */     return this.goodsTypeManager;
/*     */   }
/*     */ 
/*     */   public void setGoodsTypeManager(IGoodsTypeManager goodsTypeManager) {
/* 423 */     this.goodsTypeManager = goodsTypeManager;
/*     */   }
/*     */ 
/*     */   public String[] getGroupnames() {
/* 427 */     return this.groupnames;
/*     */   }
/*     */ 
/*     */   public void setGroupnames(String[] groupnames) {
/* 431 */     this.groupnames = groupnames;
/*     */   }
/*     */ 
/*     */   public String[] getParamnames() {
/* 435 */     return this.paramnames;
/*     */   }
/*     */ 
/*     */   public void setParamnames(String[] paramnames) {
/* 439 */     this.paramnames = paramnames;
/*     */   }
/*     */ 
/*     */   public String getParamnum() {
/* 443 */     return this.paramnum;
/*     */   }
/*     */ 
/*     */   public void setParamnum(String paramnum) {
/* 447 */     this.paramnum = paramnum;
/*     */   }
/*     */ 
/*     */   public Integer[] getChhoose_brands() {
/* 451 */     return this.chhoose_brands;
/*     */   }
/*     */ 
/*     */   public void setChhoose_brands(Integer[] chhoose_brands) {
/* 455 */     this.chhoose_brands = chhoose_brands;
/*     */   }
/*     */ 
/*     */   public Integer getType_id() {
/* 459 */     return this.type_id;
/*     */   }
/*     */ 
/*     */   public void setType_id(Integer type_id) {
/* 463 */     this.type_id = type_id;
/*     */   }
/*     */ 
/*     */   public int getIs_edit() {
/* 467 */     return this.is_edit;
/*     */   }
/*     */ 
/*     */   public void setIs_edit(int is_edit) {
/* 471 */     this.is_edit = is_edit;
/*     */   }
/*     */ 
/*     */   public Integer[] getId() {
/* 475 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(Integer[] id) {
/* 479 */     this.id = id;
/*     */   }
/*     */ 
/*     */   public void setBrandManager(IBrandManager brandManager) {
/* 483 */     this.brandManager = brandManager;
/*     */   }
/*     */ 
/*     */   public List getBrandlist() {
/* 487 */     return this.brandlist;
/*     */   }
/*     */ 
/*     */   public void setBrandlist(List brandlist) {
/* 491 */     this.brandlist = brandlist;
/*     */   }
/*     */ 
/*     */   public String[] getDatatype() {
/* 495 */     return this.datatype;
/*     */   }
/*     */ 
/*     */   public void setDatatype(String[] datatype) {
/* 499 */     this.datatype = datatype;
/*     */   }
/*     */ 
/*     */   public int[] getRequired() {
/* 503 */     return this.required;
/*     */   }
/*     */ 
/*     */   public void setRequired(int[] required) {
/* 507 */     this.required = required;
/*     */   }
/*     */ 
/*     */   public String[] getUnit() {
/* 511 */     return this.unit;
/*     */   }
/*     */ 
/*     */   public void setUnit(String[] unit) {
/* 515 */     this.unit = unit;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.action.backend.TypeAction
 * JD-Core Version:    0.6.0
 */