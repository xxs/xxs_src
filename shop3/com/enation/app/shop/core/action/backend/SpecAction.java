/*     */ package com.enation.app.shop.core.action.backend;
/*     */ 
/*     */ import com.enation.app.shop.component.spec.service.ISpecManager;
/*     */ import com.enation.app.shop.component.spec.service.ISpecValueManager;
/*     */ import com.enation.app.shop.core.model.SpecValue;
/*     */ import com.enation.app.shop.core.model.Specification;
/*     */ import com.enation.eop.sdk.utils.UploadUtil;
/*     */ import com.enation.framework.action.WWAction;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class SpecAction extends WWAction
/*     */ {
/*     */   private ISpecManager specManager;
/*     */   private ISpecValueManager specValueManager;
/*     */   private Integer spec_id;
/*     */   private Map specView;
/*     */   private List specList;
/*     */   private List valueList;
/*     */   private Specification spec;
/*     */   private String[] valueArray;
/*     */   private String[] imageArray;
/*     */   private Integer[] valueIdArray;
/*     */   private Integer[] id;
/*     */   private int valueid;
/*     */ 
/*     */   public String checkUsed()
/*     */   {
/*  38 */     if (this.specManager.checkUsed(this.id))
/*  39 */       this.json = "{result:1}";
/*     */     else {
/*  41 */       this.json = "{result:0}";
/*     */     }
/*     */ 
/*  44 */     return "json_message";
/*     */   }
/*     */ 
/*     */   public String checkValueUsed()
/*     */   {
/*  55 */     boolean isused = this.specManager.checkUsed(Integer.valueOf(this.valueid));
/*     */ 
/*  57 */     if (isused)
/*  58 */       this.json = "{result:1}";
/*     */     else {
/*  60 */       this.json = "{result:0}";
/*     */     }
/*     */ 
/*  63 */     return "json_message";
/*     */   }
/*     */ 
/*     */   public String list()
/*     */   {
/*  70 */     this.specList = this.specManager.list();
/*  71 */     return "list";
/*     */   }
/*     */ 
/*     */   public String add() {
/*  75 */     return "input";
/*     */   }
/*     */ 
/*     */   public String saveAdd() {
/*  79 */     fillSpecValueList();
/*  80 */     this.specManager.add(this.spec, this.valueList);
/*  81 */     this.msgs.add("规格添加成功");
/*  82 */     this.urls.put("规格列表", "spec!list.do");
/*  83 */     return "message";
/*     */   }
/*     */ 
/*     */   public String edit() {
/*  87 */     this.specView = this.specManager.get(this.spec_id);
/*  88 */     this.valueList = this.specValueManager.list(this.spec_id);
/*  89 */     return "input";
/*     */   }
/*     */ 
/*     */   public String saveEdit() {
/*  93 */     fillSpecValueList();
/*  94 */     this.specManager.edit(this.spec, this.valueList);
/*  95 */     this.msgs.add("规格修改成功");
/*  96 */     this.urls.put("规格列表", "spec!list.do");
/*  97 */     return "message";
/*     */   }
/*     */ 
/*     */   private List<SpecValue> fillSpecValueList() {
/* 101 */     this.valueList = new ArrayList();
/*     */ 
/* 103 */     if (this.valueArray != null) {
/* 104 */       for (int i = 0; i < this.valueArray.length; i++) {
/* 105 */         String value = this.valueArray[i];
/*     */ 
/* 107 */         SpecValue specValue = new SpecValue();
/* 108 */         specValue.setSpec_value_id(this.valueIdArray[i]);
/* 109 */         specValue.setSpec_value(value);
/* 110 */         if (this.imageArray != null) {
/* 111 */           String image = this.imageArray[i];
/* 112 */           if ((image == null) || (image.equals(""))) image = "../shop/admin/spec/image/spec_def.gif";
/*     */           else
/* 114 */             image = UploadUtil.replacePath(image);
/* 115 */           specValue.setSpec_image(image);
/*     */         } else {
/* 117 */           specValue.setSpec_image("../shop/admin/spec/image/spec_def.gif");
/*     */         }
/* 119 */         this.valueList.add(specValue);
/*     */       }
/*     */     }
/* 122 */     return this.valueList;
/*     */   }
/*     */ 
/*     */   public String delete()
/*     */   {
/* 127 */     this.specManager.delete(this.id);
/* 128 */     this.json = "{'result':0,'message':'规格删除成功'}";
/* 129 */     return "json_message";
/*     */   }
/*     */ 
/*     */   public ISpecManager getSpecManager()
/*     */   {
/* 135 */     return this.specManager;
/*     */   }
/*     */ 
/*     */   public void setSpecManager(ISpecManager specManager) {
/* 139 */     this.specManager = specManager;
/*     */   }
/*     */ 
/*     */   public ISpecValueManager getSpecValueManager() {
/* 143 */     return this.specValueManager;
/*     */   }
/*     */ 
/*     */   public void setSpecValueManager(ISpecValueManager specValueManager) {
/* 147 */     this.specValueManager = specValueManager;
/*     */   }
/*     */ 
/*     */   public List getSpecList() {
/* 151 */     return this.specList;
/*     */   }
/*     */ 
/*     */   public void setSpecList(List specList) {
/* 155 */     this.specList = specList;
/*     */   }
/*     */ 
/*     */   public Specification getSpec() {
/* 159 */     return this.spec;
/*     */   }
/*     */ 
/*     */   public void setSpec(Specification spec) {
/* 163 */     this.spec = spec;
/*     */   }
/*     */ 
/*     */   public String[] getValueArray() {
/* 167 */     return this.valueArray;
/*     */   }
/*     */ 
/*     */   public void setValueArray(String[] valueArray) {
/* 171 */     this.valueArray = valueArray;
/*     */   }
/*     */ 
/*     */   public String[] getImageArray() {
/* 175 */     return this.imageArray;
/*     */   }
/*     */ 
/*     */   public void setImageArray(String[] imageArray) {
/* 179 */     this.imageArray = imageArray;
/*     */   }
/*     */ 
/*     */   public Integer getSpec_id() {
/* 183 */     return this.spec_id;
/*     */   }
/*     */ 
/*     */   public void setSpec_id(Integer specId) {
/* 187 */     this.spec_id = specId;
/*     */   }
/*     */ 
/*     */   public Map getSpecView() {
/* 191 */     return this.specView;
/*     */   }
/*     */ 
/*     */   public void setSpecView(Map specView) {
/* 195 */     this.specView = specView;
/*     */   }
/*     */ 
/*     */   public List getValueList() {
/* 199 */     return this.valueList;
/*     */   }
/*     */ 
/*     */   public void setValueList(List valueList) {
/* 203 */     this.valueList = valueList;
/*     */   }
/*     */ 
/*     */   public Integer[] getId() {
/* 207 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(Integer[] id) {
/* 211 */     this.id = id;
/*     */   }
/*     */ 
/*     */   public Integer[] getValueIdArray() {
/* 215 */     return this.valueIdArray;
/*     */   }
/*     */ 
/*     */   public void setValueIdArray(Integer[] valueIdArray) {
/* 219 */     this.valueIdArray = valueIdArray;
/*     */   }
/*     */ 
/*     */   public int getValueid()
/*     */   {
/* 225 */     return this.valueid;
/*     */   }
/*     */ 
/*     */   public void setValueid(int valueid)
/*     */   {
/* 231 */     this.valueid = valueid;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.action.backend.SpecAction
 * JD-Core Version:    0.6.0
 */