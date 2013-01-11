/*     */ package com.enation.app.shop.core.action.backend;
/*     */ 
/*     */ import com.enation.app.base.core.model.Regions;
/*     */ import com.enation.app.base.core.service.IRegionsManager;
/*     */ import com.enation.app.shop.core.model.DlyArea;
/*     */ import com.enation.app.shop.core.service.IAreaManager;
/*     */ import com.enation.framework.action.WWAction;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class AreaAction extends WWAction
/*     */ {
/*     */   private IRegionsManager regionsManager;
/*     */   private Regions regions;
/*     */   private List provinceList;
/*     */   private List cityList;
/*     */   private List regionList;
/*     */   private int province_id;
/*     */   private int city_id;
/*     */   private Integer regionid;
/*     */   private Integer area_id;
/*     */   private String name;
/*     */   private IAreaManager areaManager;
/*     */   private String order;
/*     */   private String id;
/*     */   private DlyArea area;
/*     */ 
/*     */   public String add_region()
/*     */   {
/*  33 */     return "add_region";
/*     */   }
/*     */ 
/*     */   public String edit_region() {
/*  37 */     return "eidt_region";
/*     */   }
/*     */ 
/*     */   public String list_province() {
/*  41 */     this.provinceList = this.regionsManager.listProvince();
/*  42 */     return "list_province";
/*     */   }
/*     */ 
/*     */   public String list_city() {
/*  46 */     this.cityList = this.regionsManager.listCity(this.province_id);
/*  47 */     return "list_city";
/*     */   }
/*     */ 
/*     */   public String list_region() {
/*  51 */     this.regionList = this.regionsManager.listRegion(this.city_id);
/*  52 */     return "list_region";
/*     */   }
/*     */ 
/*     */   public String listChildren() {
/*  56 */     this.json = this.regionsManager.getChildrenJson(this.regionid);
/*  57 */     return "json_message";
/*     */   }
/*     */ 
/*     */   public IRegionsManager getRegionsManager()
/*     */   {
/*  63 */     return this.regionsManager;
/*     */   }
/*     */ 
/*     */   public void setRegionsManager(IRegionsManager regionsManager) {
/*  67 */     this.regionsManager = regionsManager;
/*     */   }
/*     */ 
/*     */   public Regions getRegions() {
/*  71 */     return this.regions;
/*     */   }
/*     */ 
/*     */   public void setRegions(Regions regions) {
/*  75 */     this.regions = regions;
/*     */   }
/*     */ 
/*     */   public List getProvinceList() {
/*  79 */     return this.provinceList;
/*     */   }
/*     */ 
/*     */   public void setProvinceList(List provinceList) {
/*  83 */     this.provinceList = provinceList;
/*     */   }
/*     */ 
/*     */   public List getCityList() {
/*  87 */     return this.cityList;
/*     */   }
/*     */ 
/*     */   public void setCityList(List cityList) {
/*  91 */     this.cityList = cityList;
/*     */   }
/*     */ 
/*     */   public List getRegionList() {
/*  95 */     return this.regionList;
/*     */   }
/*     */ 
/*     */   public void setRegionList(List regionList) {
/*  99 */     this.regionList = regionList;
/*     */   }
/*     */ 
/*     */   public int getProvince_id() {
/* 103 */     return this.province_id;
/*     */   }
/*     */ 
/*     */   public void setProvince_id(int provinceId) {
/* 107 */     this.province_id = provinceId;
/*     */   }
/*     */ 
/*     */   public int getCity_id() {
/* 111 */     return this.city_id;
/*     */   }
/*     */ 
/*     */   public void setCity_id(int cityId) {
/* 115 */     this.city_id = cityId;
/*     */   }
/*     */ 
/*     */   public String add_area()
/*     */   {
/* 129 */     return "add_area";
/*     */   }
/*     */ 
/*     */   public String edit_area() {
/* 133 */     this.area = this.areaManager.getDlyAreaById(this.area_id);
/* 134 */     return "edit_area";
/*     */   }
/*     */ 
/*     */   public String list() {
/* 138 */     this.webpage = this.areaManager.pageArea(this.order, getPage(), getPageSize());
/* 139 */     return "list_area";
/*     */   }
/*     */ 
/*     */   public String delete() {
/*     */     try {
/* 144 */       this.areaManager.delete(this.id);
/* 145 */       this.json = "{'result':0,'message':'删除成功'}";
/*     */     } catch (RuntimeException e) {
/* 147 */       this.json = "{'result':1,'message':'删除失败'}";
/*     */     }
/* 149 */     return "json_message";
/*     */   }
/*     */ 
/*     */   public String saveAdd() {
/* 153 */     this.areaManager.saveAdd(this.name);
/* 154 */     this.msgs.add("地区保存成功");
/* 155 */     this.urls.put("地区列表", "area!list.do");
/* 156 */     return "message";
/*     */   }
/*     */ 
/*     */   public String saveEdit()
/*     */   {
/* 161 */     this.areaManager.saveEdit(this.area_id, this.name);
/* 162 */     this.msgs.add("地区保存成功");
/* 163 */     this.urls.put("地区列表", "area!list.do");
/* 164 */     return "message";
/*     */   }
/*     */ 
/*     */   public String getName() {
/* 168 */     return this.name;
/*     */   }
/*     */ 
/*     */   public void setName(String name) {
/* 172 */     this.name = name;
/*     */   }
/*     */ 
/*     */   public IAreaManager getAreaManager()
/*     */   {
/* 178 */     return this.areaManager;
/*     */   }
/*     */ 
/*     */   public void setAreaManager(IAreaManager areaManager)
/*     */   {
/* 183 */     this.areaManager = areaManager;
/*     */   }
/*     */ 
/*     */   public Integer getArea_id()
/*     */   {
/* 188 */     return this.area_id;
/*     */   }
/*     */ 
/*     */   public void setArea_id(Integer area_id) {
/* 192 */     this.area_id = area_id;
/*     */   }
/*     */ 
/*     */   public String getOrder() {
/* 196 */     return this.order;
/*     */   }
/*     */ 
/*     */   public void setOrder(String order) {
/* 200 */     this.order = order;
/*     */   }
/*     */ 
/*     */   public String getId() {
/* 204 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(String id) {
/* 208 */     this.id = id;
/*     */   }
/*     */ 
/*     */   public DlyArea getArea() {
/* 212 */     return this.area;
/*     */   }
/*     */ 
/*     */   public void setArea(DlyArea area) {
/* 216 */     this.area = area;
/*     */   }
/*     */ 
/*     */   public Integer getRegionid() {
/* 220 */     return this.regionid;
/*     */   }
/*     */ 
/*     */   public void setRegionid(Integer regionid) {
/* 224 */     this.regionid = regionid;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.action.backend.AreaAction
 * JD-Core Version:    0.6.0
 */