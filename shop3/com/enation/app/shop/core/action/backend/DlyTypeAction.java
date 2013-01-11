/*     */ package com.enation.app.shop.core.action.backend;
/*     */ 
/*     */ import com.enation.app.shop.core.model.DlyType;
/*     */ import com.enation.app.shop.core.model.support.DlyTypeConfig;
/*     */ import com.enation.app.shop.core.model.support.TypeAreaConfig;
/*     */ import com.enation.app.shop.core.service.IDlyTypeManager;
/*     */ import com.enation.app.shop.core.service.ILogiManager;
/*     */ import com.enation.framework.action.WWAction;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class DlyTypeAction extends WWAction
/*     */ {
/*     */   private Integer type_id;
/*     */   private String id;
/*     */   private List logiList;
/*     */   private DlyType type;
/*     */   private IDlyTypeManager dlyTypeManager;
/*     */   private ILogiManager logiManager;
/*     */   private Integer firstunit;
/*     */   private Integer continueunit;
/*     */   private Double[] firstprice;
/*     */   private Double[] continueprice;
/*     */   private String[] areaGroupName;
/*     */   private String[] areaGroupId;
/*     */   private Integer[] has_cod;
/*     */   private Integer[] useexp;
/*     */   private String[] expressions;
/*     */   private String exp;
/*     */   private Integer defAreaFee;
/*     */   private Boolean isEdit;
/*     */ 
/*     */   public String add_type()
/*     */   {
/*  47 */     this.isEdit = Boolean.valueOf(false);
/*  48 */     this.logiList = this.logiManager.list();
/*  49 */     return "add";
/*     */   }
/*     */ 
/*     */   public String edit() {
/*  53 */     this.isEdit = Boolean.valueOf(true);
/*  54 */     this.type = this.dlyTypeManager.getDlyTypeById(this.type_id);
/*  55 */     this.logiList = this.logiManager.list();
/*  56 */     return "edit";
/*     */   }
/*     */ 
/*     */   public String list() {
/*  60 */     this.webpage = this.dlyTypeManager.pageDlyType(getPage(), getPageSize());
/*  61 */     return "list";
/*     */   }
/*     */ 
/*     */   public String vaildateExp()
/*     */   {
/*  70 */     return null;
/*     */   }
/*     */ 
/*     */   public String saveAdd()
/*     */   {
/*  76 */     if (this.type.getIs_same().intValue() == 1) {
/*  77 */       saveSame(false);
/*     */     }
/*     */ 
/*  80 */     if (this.type.getIs_same().intValue() == 0) {
/*  81 */       saveDiff(false);
/*     */     }
/*     */ 
/*  84 */     this.msgs.add("配送方式添加成功");
/*  85 */     this.urls.put("配送方式列表", "dlyType!list.do");
/*  86 */     return "message";
/*     */   }
/*     */ 
/*     */   private void saveSame(boolean isUpdate)
/*     */   {
/*  96 */     DlyTypeConfig config = new DlyTypeConfig();
/*  97 */     config.setFirstunit(this.firstunit);
/*  98 */     config.setContinueunit(this.continueunit);
/*     */ 
/* 100 */     config.setFirstprice(this.firstprice[0]);
/* 101 */     config.setContinueprice(this.continueprice[0]);
/*     */ 
/* 104 */     if (this.useexp[0].intValue() == 1) {
/* 105 */       config.setExpression(this.expressions[0]);
/* 106 */       config.setUseexp(Integer.valueOf(1));
/*     */     } else {
/* 108 */       config.setUseexp(Integer.valueOf(0));
/*     */     }
/*     */ 
/* 111 */     this.type.setHas_cod(this.has_cod[0]);
/* 112 */     config.setHave_cod(this.type.getHas_cod());
/*     */ 
/* 114 */     if (isUpdate)
/* 115 */       this.dlyTypeManager.edit(this.type, config);
/*     */     else
/* 117 */       this.dlyTypeManager.add(this.type, config);
/*     */   }
/*     */ 
/*     */   private void saveDiff(boolean isUpdate)
/*     */   {
/* 123 */     DlyTypeConfig config = new DlyTypeConfig();
/*     */ 
/* 125 */     config.setFirstunit(this.firstunit);
/* 126 */     config.setContinueunit(this.continueunit);
/* 127 */     config.setDefAreaFee(this.defAreaFee);
/*     */ 
/* 130 */     if ((this.defAreaFee != null) && (this.defAreaFee.intValue() == 1)) {
/* 131 */       config.setFirstprice(this.firstprice[0]);
/* 132 */       config.setContinueprice(this.continueprice[0]);
/* 133 */       if (this.useexp[0].intValue() == 1) {
/* 134 */         config.setExpression(this.expressions[0]);
/* 135 */         config.setUseexp(Integer.valueOf(1));
/*     */       } else {
/* 137 */         config.setUseexp(Integer.valueOf(0));
/*     */       }
/*     */     }
/*     */ 
/* 141 */     TypeAreaConfig[] configArray = new TypeAreaConfig[this.areaGroupId.length];
/* 142 */     int price_index = 0;
/*     */ 
/* 144 */     for (int i = 0; i < this.areaGroupId.length; i++)
/*     */     {
/* 147 */       if ((this.defAreaFee != null) && (this.defAreaFee.intValue() == 1))
/* 148 */         price_index = i + 1;
/*     */       else {
/* 150 */         price_index = i;
/*     */       }
/*     */ 
/* 153 */       TypeAreaConfig areaConfig = new TypeAreaConfig();
/*     */ 
/* 156 */       areaConfig.setContinueunit(config.getContinueunit());
/* 157 */       areaConfig.setFirstunit(config.getFirstunit());
/* 158 */       areaConfig.setUseexp(this.useexp[price_index]);
/*     */ 
/* 160 */       areaConfig.setAreaId(this.areaGroupId[i]);
/* 161 */       areaConfig.setAreaName(this.areaGroupName[i]);
/*     */ 
/* 164 */       if (this.useexp[price_index].intValue() == 1) {
/* 165 */         areaConfig.setExpression(this.expressions[price_index]);
/*     */       } else {
/* 167 */         areaConfig.setFirstprice(this.firstprice[price_index]);
/* 168 */         areaConfig.setContinueprice(this.continueprice[price_index]);
/*     */       }
/* 170 */       areaConfig.setHave_cod(this.has_cod[i]);
/*     */ 
/* 172 */       configArray[i] = areaConfig;
/*     */     }
/* 174 */     if (isUpdate)
/* 175 */       this.dlyTypeManager.edit(this.type, config, configArray);
/*     */     else
/* 177 */       this.dlyTypeManager.add(this.type, config, configArray);
/*     */   }
/*     */ 
/*     */   public String saveEdit()
/*     */   {
/* 185 */     if (this.type.getIs_same().intValue() == 1) {
/* 186 */       saveSame(true);
/*     */     }
/*     */ 
/* 189 */     if (this.type.getIs_same().intValue() == 0) {
/* 190 */       saveDiff(true);
/*     */     }
/*     */ 
/* 194 */     this.msgs.add("配送方式修改成功");
/* 195 */     this.urls.put("配送方式列表", "dlyType!list.do");
/* 196 */     return "message";
/*     */   }
/*     */ 
/*     */   public String delete() {
/* 200 */     this.dlyTypeManager.delete(this.id);
/* 201 */     this.json = "{'result':0,'message':'删除成功'}";
/* 202 */     return "json_message";
/*     */   }
/*     */ 
/*     */   public IDlyTypeManager getDlyTypeManager()
/*     */   {
/* 208 */     return this.dlyTypeManager;
/*     */   }
/*     */ 
/*     */   public void setDlyTypeManager(IDlyTypeManager dlyTypeManager) {
/* 212 */     this.dlyTypeManager = dlyTypeManager;
/*     */   }
/*     */ 
/*     */   public DlyType getType() {
/* 216 */     return this.type;
/*     */   }
/*     */ 
/*     */   public void setType(DlyType type) {
/* 220 */     this.type = type;
/*     */   }
/*     */ 
/*     */   public Integer getType_id() {
/* 224 */     return this.type_id;
/*     */   }
/*     */ 
/*     */   public void setType_id(Integer type_id) {
/* 228 */     this.type_id = type_id;
/*     */   }
/*     */ 
/*     */   public List getLogiList()
/*     */   {
/* 233 */     return this.logiList;
/*     */   }
/*     */ 
/*     */   public void setLogiList(List logiList) {
/* 237 */     this.logiList = logiList;
/*     */   }
/*     */ 
/*     */   public String getId() {
/* 241 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(String id) {
/* 245 */     this.id = id;
/*     */   }
/*     */ 
/*     */   public ILogiManager getLogiManager()
/*     */   {
/* 250 */     return this.logiManager;
/*     */   }
/*     */ 
/*     */   public void setLogiManager(ILogiManager logiManager) {
/* 254 */     this.logiManager = logiManager;
/*     */   }
/*     */ 
/*     */   public Integer getFirstunit() {
/* 258 */     return this.firstunit;
/*     */   }
/*     */ 
/*     */   public void setFirstunit(Integer firstunit) {
/* 262 */     this.firstunit = firstunit;
/*     */   }
/*     */ 
/*     */   public Integer getContinueunit() {
/* 266 */     return this.continueunit;
/*     */   }
/*     */ 
/*     */   public void setContinueunit(Integer continueunit) {
/* 270 */     this.continueunit = continueunit;
/*     */   }
/*     */ 
/*     */   public Double[] getFirstprice() {
/* 274 */     return this.firstprice;
/*     */   }
/*     */ 
/*     */   public void setFirstprice(Double[] firstprice) {
/* 278 */     this.firstprice = firstprice;
/*     */   }
/*     */ 
/*     */   public Double[] getContinueprice() {
/* 282 */     return this.continueprice;
/*     */   }
/*     */ 
/*     */   public void setContinueprice(Double[] continueprice) {
/* 286 */     this.continueprice = continueprice;
/*     */   }
/*     */ 
/*     */   public Integer getDefAreaFee() {
/* 290 */     return this.defAreaFee;
/*     */   }
/*     */ 
/*     */   public void setDefAreaFee(Integer defAreaFee) {
/* 294 */     this.defAreaFee = defAreaFee;
/*     */   }
/*     */ 
/*     */   public String[] getAreaGroupName() {
/* 298 */     return this.areaGroupName;
/*     */   }
/*     */ 
/*     */   public void setAreaGroupName(String[] areaGroupName) {
/* 302 */     this.areaGroupName = areaGroupName;
/*     */   }
/*     */ 
/*     */   public String[] getAreaGroupId() {
/* 306 */     return this.areaGroupId;
/*     */   }
/*     */ 
/*     */   public void setAreaGroupId(String[] areaGroupId) {
/* 310 */     this.areaGroupId = areaGroupId;
/*     */   }
/*     */ 
/*     */   public Integer[] getUseexp() {
/* 314 */     return this.useexp;
/*     */   }
/*     */ 
/*     */   public void setUseexp(Integer[] useexp) {
/* 318 */     this.useexp = useexp;
/*     */   }
/*     */ 
/*     */   public String[] getExpressions() {
/* 322 */     return this.expressions;
/*     */   }
/*     */ 
/*     */   public void setExpressions(String[] expressions) {
/* 326 */     this.expressions = expressions;
/*     */   }
/*     */ 
/*     */   public Integer[] getHas_cod() {
/* 330 */     return this.has_cod;
/*     */   }
/*     */ 
/*     */   public void setHas_cod(Integer[] hasCod) {
/* 334 */     this.has_cod = hasCod;
/*     */   }
/*     */ 
/*     */   public Boolean getIsEdit() {
/* 338 */     return this.isEdit;
/*     */   }
/*     */ 
/*     */   public void setIsEdit(Boolean isEdit) {
/* 342 */     this.isEdit = isEdit;
/*     */   }
/*     */ 
/*     */   public String getExp() {
/* 346 */     return this.exp;
/*     */   }
/*     */ 
/*     */   public void setExp(String exp) {
/* 350 */     this.exp = exp;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.action.backend.DlyTypeAction
 * JD-Core Version:    0.6.0
 */