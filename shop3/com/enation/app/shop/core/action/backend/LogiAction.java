/*     */ package com.enation.app.shop.core.action.backend;
/*     */ 
/*     */ import com.enation.app.shop.core.model.Logi;
/*     */ import com.enation.app.shop.core.service.ILogiManager;
/*     */ import com.enation.framework.action.WWAction;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class LogiAction extends WWAction
/*     */ {
/*     */   private ILogiManager logiManager;
/*     */   private String name;
/*     */   private Integer cid;
/*     */   private String id;
/*     */   private String order;
/*     */   private Logi logi;
/*     */   private String code;
/*     */ 
/*     */   public String add_logi()
/*     */   {
/*  18 */     return "add_logi";
/*     */   }
/*     */ 
/*     */   public String edit_logi() {
/*  22 */     this.logi = this.logiManager.getLogiById(this.cid);
/*  23 */     return "edit_logi";
/*     */   }
/*     */ 
/*     */   public String list_logi() {
/*  27 */     this.webpage = this.logiManager.pageLogi(this.order, Integer.valueOf(getPage()), Integer.valueOf(getPageSize()));
/*  28 */     return "list_logi";
/*     */   }
/*     */ 
/*     */   public String delete() {
/*     */     try {
/*  33 */       this.logiManager.delete(this.id);
/*  34 */       this.json = "{'result':0,'message':'删除成功'}";
/*     */     } catch (RuntimeException e) {
/*  36 */       this.json = "{'result':1,'message':'删除失败'}";
/*     */     }
/*  38 */     return "json_message";
/*     */   }
/*     */ 
/*     */   public String saveAdd() {
/*  42 */     Logi logi = new Logi();
/*  43 */     logi.setCode(this.code);
/*  44 */     logi.setName(this.name);
/*  45 */     this.logiManager.saveAdd(logi);
/*  46 */     this.msgs.add("添加成功");
/*  47 */     this.urls.put("物流公司列表", "logi!list_logi.do");
/*  48 */     return "message";
/*     */   }
/*     */ 
/*     */   public String saveEdit() {
/*  52 */     Logi logi = new Logi();
/*  53 */     logi.setId(this.cid);
/*  54 */     logi.setCode(this.code);
/*  55 */     logi.setName(this.name);
/*  56 */     this.logiManager.saveEdit(logi);
/*     */ 
/*  58 */     this.msgs.add("修改成功");
/*  59 */     this.urls.put("物流公司列表", "logi!list_logi.do");
/*  60 */     return "message";
/*     */   }
/*     */   public String getName() {
/*  63 */     return this.name;
/*     */   }
/*     */ 
/*     */   public void setName(String name) {
/*  67 */     this.name = name;
/*     */   }
/*     */ 
/*     */   public ILogiManager getLogiManager() {
/*  71 */     return this.logiManager;
/*     */   }
/*     */ 
/*     */   public void setLogiManager(ILogiManager logiManager) {
/*  75 */     this.logiManager = logiManager;
/*     */   }
/*     */ 
/*     */   public Integer getCid() {
/*  79 */     return this.cid;
/*     */   }
/*     */   public void setCid(Integer cid) {
/*  82 */     this.cid = cid;
/*     */   }
/*     */   public String getId() {
/*  85 */     return this.id;
/*     */   }
/*     */   public void setId(String id) {
/*  88 */     this.id = id;
/*     */   }
/*     */ 
/*     */   public String getOrder() {
/*  92 */     return this.order;
/*     */   }
/*     */ 
/*     */   public void setOrder(String order) {
/*  96 */     this.order = order;
/*     */   }
/*     */ 
/*     */   public Logi getLogi() {
/* 100 */     return this.logi;
/*     */   }
/*     */ 
/*     */   public void setLogi(Logi logi) {
/* 104 */     this.logi = logi;
/*     */   }
/*     */ 
/*     */   public String getCode() {
/* 108 */     return this.code;
/*     */   }
/*     */ 
/*     */   public void setCode(String code) {
/* 112 */     this.code = code;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.action.backend.LogiAction
 * JD-Core Version:    0.6.0
 */