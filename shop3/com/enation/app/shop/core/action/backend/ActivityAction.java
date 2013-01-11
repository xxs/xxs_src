/*     */ package com.enation.app.shop.core.action.backend;
/*     */ 
/*     */ import com.enation.app.shop.core.model.PromotionActivity;
/*     */ import com.enation.app.shop.core.service.IPromotionActivityManager;
/*     */ import com.enation.framework.action.WWAction;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class ActivityAction extends WWAction
/*     */ {
/*     */   private Date begin_time;
/*     */   private Date end_time;
/*     */   private Integer[] id;
/*     */   private PromotionActivity activity;
/*     */   private IPromotionActivityManager promotionActivityManager;
/*     */   private int activity_id;
/*     */ 
/*     */   public String list()
/*     */   {
/*  26 */     this.webpage = this.promotionActivityManager.list(getPage(), getPageSize());
/*  27 */     return "list";
/*     */   }
/*     */ 
/*     */   public String add() {
/*  31 */     return "add";
/*     */   }
/*     */ 
/*     */   public String edit() {
/*  35 */     this.activity = this.promotionActivityManager.get(Integer.valueOf(this.activity_id));
/*  36 */     return "edit";
/*     */   }
/*     */ 
/*     */   public String saveAdd() {
/*  40 */     this.activity.setBegin_time(Long.valueOf(this.begin_time.getTime()));
/*  41 */     this.activity.setEnd_time(Long.valueOf(this.end_time.getTime()));
/*     */     try {
/*  43 */       this.promotionActivityManager.add(this.activity);
/*  44 */       this.msgs.add("活动添加成功");
/*     */     } catch (Exception e) {
/*  46 */       this.msgs.add("活动添加失败");
/*  47 */       e.printStackTrace();
/*     */     }
/*  49 */     this.urls.put("促销活动列表", "activity!list.do");
/*  50 */     return "message";
/*     */   }
/*     */ 
/*     */   public String saveEdit() {
/*  54 */     this.activity.setBegin_time(Long.valueOf(this.begin_time.getTime()));
/*  55 */     this.activity.setEnd_time(Long.valueOf(this.end_time.getTime()));
/*     */     try {
/*  57 */       this.promotionActivityManager.edit(this.activity);
/*  58 */       this.msgs.add("活动修改成功");
/*     */     } catch (Exception e) {
/*  60 */       this.msgs.add("活动修改失败");
/*  61 */       e.printStackTrace();
/*     */     }
/*  63 */     this.urls.put("促销活动列表", "activity!list.do");
/*  64 */     return "message";
/*     */   }
/*     */ 
/*     */   public String delete() {
/*     */     try {
/*  69 */       this.promotionActivityManager.delete(this.id);
/*  70 */       this.json = "{'result':0,'message':'删除成功'}";
/*     */     } catch (Exception e) {
/*  72 */       this.json = "{'result':1;'message':'删除失败'}";
/*  73 */       e.printStackTrace();
/*     */     }
/*  75 */     return "json_message";
/*     */   }
/*     */ 
/*     */   public PromotionActivity getActivity() {
/*  79 */     return this.activity;
/*     */   }
/*     */ 
/*     */   public void setActivity(PromotionActivity activity) {
/*  83 */     this.activity = activity;
/*     */   }
/*     */ 
/*     */   public IPromotionActivityManager getPromotionActivityManager() {
/*  87 */     return this.promotionActivityManager;
/*     */   }
/*     */ 
/*     */   public void setPromotionActivityManager(IPromotionActivityManager promotionActivityManager)
/*     */   {
/*  92 */     this.promotionActivityManager = promotionActivityManager;
/*     */   }
/*     */ 
/*     */   public Date getBegin_time() {
/*  96 */     return this.begin_time;
/*     */   }
/*     */ 
/*     */   public void setBegin_time(Date begin_time) {
/* 100 */     this.begin_time = begin_time;
/*     */   }
/*     */ 
/*     */   public Date getEnd_time() {
/* 104 */     return this.end_time;
/*     */   }
/*     */ 
/*     */   public void setEnd_time(Date end_time) {
/* 108 */     this.end_time = end_time;
/*     */   }
/*     */ 
/*     */   public Integer[] getId() {
/* 112 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(Integer[] id) {
/* 116 */     this.id = id;
/*     */   }
/*     */ 
/*     */   public int getActivity_id() {
/* 120 */     return this.activity_id;
/*     */   }
/*     */ 
/*     */   public void setActivity_id(int activityId) {
/* 124 */     this.activity_id = activityId;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.action.backend.ActivityAction
 * JD-Core Version:    0.6.0
 */