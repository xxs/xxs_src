/*     */ package com.enation.app.shop.core.action.backend;
/*     */ 
/*     */ import com.enation.app.shop.core.model.Brand;
/*     */ import com.enation.app.shop.core.service.IBrandManager;
/*     */ import com.enation.framework.action.WWAction;
/*     */ import com.enation.framework.util.FileUtil;
/*     */ import java.io.File;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class BrandAction extends WWAction
/*     */ {
/*     */   private IBrandManager brandManager;
/*     */   private Brand brand;
/*     */   private File logo;
/*     */   private String logoFileName;
/*     */   private String oldlogo;
/*     */   private String filePath;
/*     */   private String order;
/*     */   private Integer brand_id;
/*     */   private String id;
/*     */   private List<Map> brand_types;
/*     */   private int type_id;
/*     */   private String brandname;
/*     */ 
/*     */   public String getBrandname()
/*     */   {
/*  49 */     return this.brandname;
/*     */   }
/*     */ 
/*     */   public void setBrandname(String brandname) {
/*  53 */     this.brandname = brandname;
/*     */   }
/*     */ 
/*     */   public int getType_id() {
/*  57 */     return this.type_id;
/*     */   }
/*     */ 
/*     */   public void setType_id(int type_id) {
/*  61 */     this.type_id = type_id;
/*     */   }
/*     */ 
/*     */   public List<Map> getBrand_types() {
/*  65 */     return this.brand_types;
/*     */   }
/*     */ 
/*     */   public void setBrand_types(List<Map> brand_types) {
/*  69 */     this.brand_types = brand_types;
/*     */   }
/*     */ 
/*     */   public IBrandManager getBrandManager() {
/*  73 */     return this.brandManager;
/*     */   }
/*     */ 
/*     */   public String getId() {
/*  77 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(String id) {
/*  81 */     this.id = id;
/*     */   }
/*     */ 
/*     */   public String checkUsed() {
/*  85 */     if (this.brandManager.checkUsed(this.id))
/*  86 */       this.json = "{result:1}";
/*     */     else {
/*  88 */       this.json = "{result:0}";
/*     */     }
/*  90 */     return "json_message";
/*     */   }
/*     */ 
/*     */   public String checkname() {
/*  94 */     if (this.brandManager.checkname(this.brand.getName(), this.brand.getBrand_id()))
/*  95 */       this.json = "{result:1}";
/*     */     else {
/*  97 */       this.json = "{result:0}";
/*     */     }
/*  99 */     return "json_message";
/*     */   }
/*     */ 
/*     */   public String add() {
/* 103 */     return "add";
/*     */   }
/*     */ 
/*     */   public String edit() {
/* 107 */     this.brand = this.brandManager.get(this.brand_id);
/* 108 */     return "edit";
/*     */   }
/*     */ 
/*     */   public String list()
/*     */   {
/* 113 */     this.brand_types = this.brandManager.queryAllTypeNameAndId();
/* 114 */     this.webpage = this.brandManager.list(this.order, getPage(), getPageSize());
/* 115 */     return "list";
/*     */   }
/*     */ 
/*     */   public String search()
/*     */   {
/* 120 */     this.brand_types = this.brandManager.queryAllTypeNameAndId();
/* 121 */     this.webpage = this.brandManager.search(getPage(), getPageSize(), this.brandname, Integer.valueOf(this.type_id));
/* 122 */     return "list";
/*     */   }
/*     */ 
/*     */   public String trash_list()
/*     */   {
/* 128 */     this.webpage = this.brandManager.listTrash(this.order, getPage(), getPageSize());
/* 129 */     return "trash_list";
/*     */   }
/*     */ 
/*     */   public String save()
/*     */   {
/* 135 */     if ((this.logo != null) && 
/* 136 */       (!FileUtil.isAllowUp(this.logoFileName)))
/*     */     {
/* 139 */       this.msgs.add("不允许上传的文件格式，请上传gif,jpg,bmp格式文件。");
/* 140 */       return "message";
/*     */     }
/*     */ 
/* 143 */     this.brand.setDisabled(Integer.valueOf(0));
/* 144 */     this.brand.setFile(this.logo);
/* 145 */     this.brand.setFileFileName(this.logoFileName);
/* 146 */     this.brandManager.add(this.brand);
/* 147 */     this.msgs.add("品牌添加成功");
/* 148 */     this.urls.put("品牌列表", "brand!list.do");
/* 149 */     return "message";
/*     */   }
/*     */ 
/*     */   public String saveEdit()
/*     */   {
/* 154 */     if ((this.logo != null) && 
/* 155 */       (!FileUtil.isAllowUp(this.logoFileName)))
/*     */     {
/* 159 */       this.msgs.add("不允许上传的文件格式，请上传gif,jpg,bmp格式文件。");
/* 160 */       return "message";
/*     */     }
/*     */ 
/* 164 */     this.brand.setFile(this.logo);
/* 165 */     this.brand.setFileFileName(this.logoFileName);
/* 166 */     this.brandManager.update(this.brand);
/* 167 */     this.msgs.add("品牌修改成功");
/* 168 */     this.urls.put("品牌列表", "brand!list.do");
/* 169 */     return "message";
/*     */   }
/*     */ 
/*     */   public String delete()
/*     */   {
/*     */     try
/*     */     {
/* 179 */       this.brandManager.delete(this.id);
/* 180 */       this.json = "{'result':0,'message':'删除成功'}";
/*     */     } catch (RuntimeException e) {
/* 182 */       this.json = "{'result':1,'message':'删除失败'}";
/*     */     }
/* 184 */     return "json_message";
/*     */   }
/*     */ 
/*     */   public String revert()
/*     */   {
/*     */     try
/*     */     {
/* 194 */       this.brandManager.revert(this.id);
/* 195 */       this.json = "{'result':0,'message':'还原成功'}";
/*     */     } catch (RuntimeException e) {
/* 197 */       this.json = "{'result':1,'message':'删除失败'}";
/*     */     }
/* 199 */     return "json_message";
/*     */   }
/*     */ 
/*     */   public String clean()
/*     */   {
/*     */     try
/*     */     {
/* 209 */       this.brandManager.clean(this.id);
/* 210 */       this.json = "{'result':0,'message':'删除成功'}";
/*     */     } catch (RuntimeException e) {
/* 212 */       this.json = "{'result':1,'message':'删除失败'}";
/*     */     }
/* 214 */     return "json_message";
/*     */   }
/*     */ 
/*     */   public Brand getBrand() {
/* 218 */     return this.brand;
/*     */   }
/*     */ 
/*     */   public void setBrand(Brand brand) {
/* 222 */     this.brand = brand;
/*     */   }
/*     */ 
/*     */   public File getLogo() {
/* 226 */     return this.logo;
/*     */   }
/*     */ 
/*     */   public void setLogo(File logo) {
/* 230 */     this.logo = logo;
/*     */   }
/*     */ 
/*     */   public String getLogoFileName() {
/* 234 */     return this.logoFileName;
/*     */   }
/*     */ 
/*     */   public void setLogoFileName(String logoFileName) {
/* 238 */     this.logoFileName = logoFileName;
/*     */   }
/*     */ 
/*     */   public void setBrandManager(IBrandManager brandManager)
/*     */   {
/* 243 */     this.brandManager = brandManager;
/*     */   }
/*     */ 
/*     */   public String getOldlogo() {
/* 247 */     return this.oldlogo;
/*     */   }
/*     */ 
/*     */   public void setOldlogo(String oldlogo) {
/* 251 */     this.oldlogo = oldlogo;
/*     */   }
/*     */ 
/*     */   public String getFilePath() {
/* 255 */     return this.filePath;
/*     */   }
/*     */ 
/*     */   public void setFilePath(String filePath) {
/* 259 */     this.filePath = filePath;
/*     */   }
/*     */ 
/*     */   public String getOrder() {
/* 263 */     return this.order;
/*     */   }
/*     */ 
/*     */   public void setOrder(String order) {
/* 267 */     this.order = order;
/*     */   }
/*     */ 
/*     */   public Integer getBrand_id() {
/* 271 */     return this.brand_id;
/*     */   }
/*     */ 
/*     */   public void setBrand_id(Integer brand_id) {
/* 275 */     this.brand_id = brand_id;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.action.backend.BrandAction
 * JD-Core Version:    0.6.0
 */