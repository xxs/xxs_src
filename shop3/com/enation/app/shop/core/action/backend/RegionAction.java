/*     */ package com.enation.app.shop.core.action.backend;
/*     */ 
/*     */ import com.enation.app.base.core.model.Regions;
/*     */ import com.enation.app.base.core.service.IRegionsManager;
/*     */ import com.enation.framework.action.WWAction;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class RegionAction extends WWAction
/*     */ {
/*     */   private IRegionsManager regionsManager;
/*     */   private List listRegion;
/*     */   private Integer parentid;
/*     */   private Regions regions;
/*     */   private Integer region_id;
/*     */   private Integer regiongrade;
/*     */ 
/*     */   public String list()
/*     */   {
/*  25 */     return "list";
/*     */   }
/*     */ 
/*     */   public String listChildren() {
/*  29 */     this.listRegion = this.regionsManager.listChildren(this.parentid);
/*  30 */     return "listChildren";
/*     */   }
/*     */ 
/*     */   public String add() {
/*  34 */     return "add";
/*     */   }
/*     */ 
/*     */   public String saveAdd() {
/*     */     try {
/*  39 */       this.regionsManager.add(this.regions);
/*  40 */       this.msgs.add("地区添加成功");
/*     */     }
/*     */     catch (Exception e) {
/*  43 */       e.printStackTrace();
/*  44 */       this.msgs.add("地区添加失败");
/*     */     }
/*  46 */     this.urls.put("地区管理", "region!list.do");
/*  47 */     return "message";
/*     */   }
/*     */ 
/*     */   public String edit() {
/*  51 */     this.regions = this.regionsManager.get(this.region_id.intValue());
/*  52 */     return "edit";
/*     */   }
/*     */ 
/*     */   public String saveEdit() {
/*     */     try {
/*  57 */       this.regionsManager.update(this.regions);
/*  58 */       this.msgs.add("地区修改成功");
/*     */     }
/*     */     catch (Exception e) {
/*  61 */       e.printStackTrace();
/*  62 */       this.msgs.add("地区修改失败");
/*     */     }
/*  64 */     this.urls.put("地区管理", "region!list.do");
/*  65 */     return "message";
/*     */   }
/*     */ 
/*     */   public String delete() {
/*     */     try {
/*  70 */       this.regionsManager.delete(this.region_id.intValue());
/*  71 */       this.json = "{'result':0,'message':'删除成功'}";
/*     */     } catch (RuntimeException e) {
/*  73 */       this.json = "{'result':1,'message':'删除失败'}";
/*  74 */       e.printStackTrace();
/*     */     }
/*  76 */     return "json_message";
/*     */   }
/*     */ 
/*     */   public String reset() {
/*     */     try {
/*  81 */       this.regionsManager.reset();
/*  82 */       this.json = "{'result':0,'message':'成功'}";
/*     */     } catch (RuntimeException e) {
/*  84 */       this.json = "{'result':1,'message':'失败'}";
/*  85 */       e.printStackTrace();
/*     */     }
/*  87 */     return "json_message";
/*     */   }
/*     */ 
/*     */   public IRegionsManager getRegionsManager() {
/*  91 */     return this.regionsManager;
/*     */   }
/*     */ 
/*     */   public void setRegionsManager(IRegionsManager regionsManager) {
/*  95 */     this.regionsManager = regionsManager;
/*     */   }
/*     */ 
/*     */   public List getListRegion() {
/*  99 */     return this.listRegion;
/*     */   }
/*     */ 
/*     */   public void setListRegion(List listRegion) {
/* 103 */     this.listRegion = listRegion;
/*     */   }
/*     */ 
/*     */   public Integer getParentid() {
/* 107 */     return this.parentid;
/*     */   }
/*     */ 
/*     */   public void setParentid(Integer parentid) {
/* 111 */     this.parentid = parentid;
/*     */   }
/*     */ 
/*     */   public Regions getRegions() {
/* 115 */     return this.regions;
/*     */   }
/*     */ 
/*     */   public void setRegions(Regions regions) {
/* 119 */     this.regions = regions;
/*     */   }
/*     */ 
/*     */   public Integer getRegion_id() {
/* 123 */     return this.region_id;
/*     */   }
/*     */ 
/*     */   public void setRegion_id(Integer regionId) {
/* 127 */     this.region_id = regionId;
/*     */   }
/*     */ 
/*     */   public Integer getRegiongrade() {
/* 131 */     return this.regiongrade;
/*     */   }
/*     */ 
/*     */   public void setRegiongrade(Integer regiongrade) {
/* 135 */     this.regiongrade = regiongrade;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.action.backend.RegionAction
 * JD-Core Version:    0.6.0
 */