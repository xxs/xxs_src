/*     */ package com.enation.app.shop.core.action.backend;
/*     */ 
/*     */ import com.enation.app.base.core.service.IRegionsManager;
/*     */ import com.enation.app.shop.core.model.DlyCenter;
/*     */ import com.enation.app.shop.core.service.IDlyCenterManager;
/*     */ import com.enation.framework.action.WWAction;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class DlyCenterAction extends WWAction
/*     */ {
/*     */   private IDlyCenterManager dlyCenterManager;
/*     */   private IRegionsManager regionsManager;
/*     */   private DlyCenter dlyCenter;
/*     */   private Integer dlyCenterId;
/*     */   private Integer[] id;
/*     */   private List<DlyCenter> list;
/*     */   private List provinceList;
/*     */   private List cityList;
/*     */   private List regionList;
/*     */ 
/*     */   public String add()
/*     */   {
/*  24 */     this.provinceList = this.regionsManager.listProvince();
/*  25 */     return "add";
/*     */   }
/*     */ 
/*     */   public String edit() {
/*  29 */     this.dlyCenter = this.dlyCenterManager.get(this.dlyCenterId);
/*  30 */     this.provinceList = this.regionsManager.listProvince();
/*  31 */     if (this.dlyCenter.getProvince_id() != null) {
/*  32 */       this.cityList = this.regionsManager.listCity(this.dlyCenter.getProvince_id().intValue());
/*     */     }
/*  34 */     if (this.dlyCenter.getCity_id() != null) {
/*  35 */       this.regionList = this.regionsManager.listRegion(this.dlyCenter.getCity_id().intValue());
/*     */     }
/*  37 */     return "edit";
/*     */   }
/*     */ 
/*     */   public String list() {
/*  41 */     this.list = this.dlyCenterManager.list();
/*  42 */     return "list";
/*     */   }
/*     */ 
/*     */   public String delete() {
/*     */     try {
/*  47 */       this.dlyCenterManager.delete(this.id);
/*  48 */       this.json = "{result:0,message:'发货信息删除成功'}";
/*     */     } catch (RuntimeException e) {
/*  50 */       if (this.logger.isDebugEnabled()) {
/*  51 */         this.logger.debug(e);
/*     */       }
/*  53 */       this.json = ("{result:1,message:\"发货信息删除失败：" + e.getMessage() + "\"}");
/*     */     }
/*     */ 
/*  56 */     return "json_message";
/*     */   }
/*     */ 
/*     */   public String saveAdd() {
/*     */     try {
/*  61 */       this.dlyCenterManager.add(this.dlyCenter);
/*  62 */       this.msgs.add("发货信息添加成功");
/*     */     }
/*     */     catch (Exception e) {
/*  65 */       e.printStackTrace();
/*  66 */       this.msgs.add("发货信息添加失败");
/*     */     }
/*  68 */     this.urls.put("发货信息管理", "dlyCenter!list.do");
/*  69 */     return "message";
/*     */   }
/*     */ 
/*     */   public String saveEdit() {
/*     */     try {
/*  74 */       this.dlyCenterManager.edit(this.dlyCenter);
/*  75 */       this.msgs.add("发货信息修改成功");
/*     */     }
/*     */     catch (Exception e) {
/*  78 */       e.printStackTrace();
/*  79 */       this.msgs.add("发货信息修改失败");
/*     */     }
/*  81 */     this.urls.put("发货信息管理", "dlyCenter!list.do");
/*  82 */     return "message";
/*     */   }
/*     */ 
/*     */   public IDlyCenterManager getDlyCenterManager() {
/*  86 */     return this.dlyCenterManager;
/*     */   }
/*     */ 
/*     */   public void setDlyCenterManager(IDlyCenterManager dlyCenterManager) {
/*  90 */     this.dlyCenterManager = dlyCenterManager;
/*     */   }
/*     */ 
/*     */   public DlyCenter getDlyCenter() {
/*  94 */     return this.dlyCenter;
/*     */   }
/*     */ 
/*     */   public void setDlyCenter(DlyCenter dlyCenter) {
/*  98 */     this.dlyCenter = dlyCenter;
/*     */   }
/*     */ 
/*     */   public Integer getDlyCenterId() {
/* 102 */     return this.dlyCenterId;
/*     */   }
/*     */ 
/*     */   public void setDlyCenterId(Integer dlyCenterId) {
/* 106 */     this.dlyCenterId = dlyCenterId;
/*     */   }
/*     */ 
/*     */   public Integer[] getId() {
/* 110 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(Integer[] id) {
/* 114 */     this.id = id;
/*     */   }
/*     */ 
/*     */   public List<DlyCenter> getList() {
/* 118 */     return this.list;
/*     */   }
/*     */ 
/*     */   public void setList(List<DlyCenter> list) {
/* 122 */     this.list = list;
/*     */   }
/*     */ 
/*     */   public IRegionsManager getRegionsManager() {
/* 126 */     return this.regionsManager;
/*     */   }
/*     */ 
/*     */   public void setRegionsManager(IRegionsManager regionsManager) {
/* 130 */     this.regionsManager = regionsManager;
/*     */   }
/*     */ 
/*     */   public List getProvinceList() {
/* 134 */     return this.provinceList;
/*     */   }
/*     */ 
/*     */   public void setProvinceList(List provinceList) {
/* 138 */     this.provinceList = provinceList;
/*     */   }
/*     */ 
/*     */   public List getCityList() {
/* 142 */     return this.cityList;
/*     */   }
/*     */ 
/*     */   public void setCityList(List cityList) {
/* 146 */     this.cityList = cityList;
/*     */   }
/*     */ 
/*     */   public List getRegionList() {
/* 150 */     return this.regionList;
/*     */   }
/*     */ 
/*     */   public void setRegionList(List regionList) {
/* 154 */     this.regionList = regionList;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.action.backend.DlyCenterAction
 * JD-Core Version:    0.6.0
 */