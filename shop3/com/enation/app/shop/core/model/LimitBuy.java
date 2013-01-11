/*     */ package com.enation.app.shop.core.model;
/*     */ 
/*     */ import com.enation.eop.sdk.utils.UploadUtil;
/*     */ import com.enation.framework.database.NotDbField;
/*     */ import com.enation.framework.database.PrimaryKeyField;
/*     */ import com.enation.framework.util.DateUtil;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class LimitBuy
/*     */ {
/*     */   private Integer id;
/*     */   private String name;
/*     */   private long start_time;
/*     */   private long end_time;
/*     */   private long add_time;
/*     */   private String img;
/*     */   private int is_index;
/*     */   private List<LimitBuyGoods> limitBuyGoodsList;
/*     */   private List<Map> goodsList;
/*     */ 
/*     */   @NotDbField
/*     */   public String getEndTime()
/*     */   {
/*  34 */     return DateUtil.toString(new Date(this.end_time * 1000L), "yyyy/MM/dd,HH:00:00");
/*     */   }
/*     */   @NotDbField
/*     */   public List<Map> getGoodsList() {
/*  39 */     return this.goodsList;
/*     */   }
/*     */ 
/*     */   public void setGoodsList(List<Map> goodsList) {
/*  43 */     this.goodsList = goodsList;
/*     */   }
/*     */   @NotDbField
/*     */   public List<LimitBuyGoods> getLimitBuyGoodsList() {
/*  48 */     return this.limitBuyGoodsList;
/*     */   }
/*     */ 
/*     */   public void setLimitBuyGoodsList(List<LimitBuyGoods> limitBuyGoodsList) {
/*  52 */     this.limitBuyGoodsList = limitBuyGoodsList;
/*     */   }
/*     */   @PrimaryKeyField
/*     */   public Integer getId() {
/*  57 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(Integer id) {
/*  61 */     this.id = id;
/*     */   }
/*     */ 
/*     */   public String getName() {
/*  65 */     return this.name;
/*     */   }
/*     */ 
/*     */   public void setName(String name) {
/*  69 */     this.name = name;
/*     */   }
/*     */ 
/*     */   public long getStart_time() {
/*  73 */     return this.start_time;
/*     */   }
/*     */ 
/*     */   public void setStart_time(long startTime) {
/*  77 */     this.start_time = startTime;
/*     */   }
/*     */ 
/*     */   public long getEnd_time() {
/*  81 */     return this.end_time;
/*     */   }
/*     */ 
/*     */   public void setEnd_time(long endTime) {
/*  85 */     this.end_time = endTime;
/*     */   }
/*     */ 
/*     */   public long getAdd_time() {
/*  89 */     return this.add_time;
/*     */   }
/*     */ 
/*     */   public void setAdd_time(long addTime) {
/*  93 */     this.add_time = addTime;
/*     */   }
/*     */ 
/*     */   public String getImg() {
/*  97 */     return this.img;
/*     */   }
/*     */ 
/*     */   public void setImg(String img) {
/* 101 */     this.img = img;
/*     */   }
/*     */ 
/*     */   @NotDbField
/*     */   public String getImage() {
/* 107 */     return UploadUtil.replacePath(this.img);
/*     */   }
/*     */ 
/*     */   public int getIs_index() {
/* 111 */     return this.is_index;
/*     */   }
/*     */ 
/*     */   public void setIs_index(int isIndex) {
/* 115 */     this.is_index = isIndex;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.model.LimitBuy
 * JD-Core Version:    0.6.0
 */