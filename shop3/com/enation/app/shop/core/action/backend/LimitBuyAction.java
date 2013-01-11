/*     */ package com.enation.app.shop.core.action.backend;
/*     */ 
/*     */ import com.enation.app.shop.core.model.LimitBuy;
/*     */ import com.enation.app.shop.core.model.LimitBuyGoods;
/*     */ import com.enation.app.shop.core.service.ILimitBuyManager;
/*     */ import com.enation.eop.sdk.utils.UploadUtil;
/*     */ import com.enation.framework.action.WWAction;
/*     */ import com.enation.framework.util.DateUtil;
/*     */ import java.io.File;
/*     */ import java.io.PrintStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class LimitBuyAction extends WWAction
/*     */ {
/*     */   private ILimitBuyManager limitBuyManager;
/*     */   private LimitBuy limitBuy;
/*     */   private Integer id;
/*     */   private boolean isEdit;
/*     */   private Integer[] goodsid;
/*     */   private Double[] price;
/*     */   private String start_time;
/*     */   private String end_time;
/*     */   private int start_hour;
/*     */   private int end_hour;
/*     */   private File imgFile;
/*     */   private String imgFileFileName;
/*     */ 
/*     */   public String list()
/*     */   {
/*  31 */     this.webpage = this.limitBuyManager.list(getPage(), getPageSize());
/*     */ 
/*  33 */     return "list";
/*     */   }
/*     */ 
/*     */   public String add() {
/*  37 */     this.isEdit = false;
/*  38 */     return "input";
/*     */   }
/*     */ 
/*     */   public String edit() {
/*  42 */     this.isEdit = true;
/*  43 */     this.limitBuy = this.limitBuyManager.get(this.id);
/*  44 */     this.start_hour = new Date(this.limitBuy.getStart_time() * 1000L).getHours();
/*  45 */     this.end_hour = new Date(this.limitBuy.getEnd_time() * 1000L).getHours();
/*  46 */     return "input";
/*     */   }
/*     */ 
/*     */   private int getDatelineLong(String date) {
/*  50 */     return (int)(DateUtil.toDate(date, "yyyy-MM-dd HH").getTime() / 1000L);
/*     */   }
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/*  55 */     int date_int = (int)(DateUtil.toDate("2010-11-11 18", "yyyy-MM-dd HH").getTime() / 1000L);
/*  56 */     System.out.println("date int :" + date_int);
/*  57 */     long d = date_int * 1000L;
/*  58 */     System.out.println(d);
/*  59 */     System.out.println(new Date(d).getHours());
/*     */   }
/*     */ 
/*     */   public String saveAdd()
/*     */   {
/*     */     try
/*     */     {
/*  67 */       if ((this.imgFile != null) && (this.imgFileFileName != null)) {
/*  68 */         String img = UploadUtil.upload(this.imgFile, this.imgFileFileName, "goods");
/*  69 */         this.limitBuy.setImg(img);
/*     */       }
/*     */ 
/*  73 */       this.limitBuy.setStart_time(getDatelineLong(this.start_time + " " + this.start_hour));
/*  74 */       this.limitBuy.setEnd_time(getDatelineLong(this.end_time + " " + this.end_hour));
/*     */ 
/*  76 */       this.limitBuy.setLimitBuyGoodsList(createLimitBuyGoods());
/*  77 */       this.limitBuyManager.add(this.limitBuy);
/*  78 */       this.msgs.add("限时购买添加成功");
/*  79 */       this.urls.put("限时购买列表", "limitBuy!list.do");
/*     */     } catch (RuntimeException e) {
/*  81 */       this.logger.error(e.fillInStackTrace());
/*  82 */       this.msgs.add(e.getMessage());
/*  83 */       this.urls.put("返回", "javascript:back();;");
/*     */     }
/*  85 */     return "message";
/*     */   }
/*     */ 
/*     */   public String saveEdit()
/*     */   {
/*     */     try {
/*  91 */       if ((this.imgFile != null) && (this.imgFileFileName != null)) {
/*  92 */         String img = UploadUtil.upload(this.imgFile, this.imgFileFileName, "goods");
/*  93 */         this.limitBuy.setImg(img);
/*     */       }
/*     */ 
/*  98 */       this.limitBuy.setStart_time(getDatelineLong(this.start_time + " " + this.start_hour));
/*  99 */       this.limitBuy.setEnd_time(getDatelineLong(this.end_time + " " + this.end_hour));
/*     */ 
/* 101 */       this.limitBuy.setLimitBuyGoodsList(createLimitBuyGoods());
/* 102 */       this.limitBuyManager.edit(this.limitBuy);
/* 103 */       this.msgs.add("限时购买修改成功");
/* 104 */       this.urls.put("限时购买列表", "limitBuy!list.do");
/*     */     } catch (RuntimeException e) {
/* 106 */       this.logger.error(e);
/* 107 */       this.msgs.add(e.getMessage());
/* 108 */       this.urls.put("返回", "javascript:back();;");
/*     */     }
/* 110 */     return "message";
/*     */   }
/*     */ 
/*     */   public String delete() {
/* 114 */     this.limitBuyManager.delete(this.id);
/* 115 */     this.msgs.add("限时购买删除成功");
/* 116 */     this.urls.put("限时购买列表", "limitBuy!list.do");
/* 117 */     return "message";
/*     */   }
/*     */ 
/*     */   private List<LimitBuyGoods> createLimitBuyGoods() {
/* 121 */     if (this.goodsid == null)
/* 122 */       throw new RuntimeException("必须选择一个或更多商品");
/* 123 */     if (this.price == null)
/* 124 */       throw new RuntimeException("必须选择一个或更多商品");
/* 125 */     if (this.goodsid.length != this.price.length) {
/* 126 */       throw new RuntimeException("商品价格不正确");
/*     */     }
/* 128 */     List goodsList = new ArrayList();
/* 129 */     for (int i = 0; i < this.goodsid.length; i++) {
/* 130 */       LimitBuyGoods buyGoods = new LimitBuyGoods();
/* 131 */       buyGoods.setGoodsid(this.goodsid[i]);
/* 132 */       buyGoods.setPrice(this.price[i]);
/* 133 */       goodsList.add(buyGoods);
/*     */     }
/*     */ 
/* 136 */     return goodsList;
/*     */   }
/*     */ 
/*     */   public ILimitBuyManager getLimitBuyManager() {
/* 140 */     return this.limitBuyManager;
/*     */   }
/*     */ 
/*     */   public void setLimitBuyManager(ILimitBuyManager limitBuyManager) {
/* 144 */     this.limitBuyManager = limitBuyManager;
/*     */   }
/*     */ 
/*     */   public LimitBuy getLimitBuy() {
/* 148 */     return this.limitBuy;
/*     */   }
/*     */ 
/*     */   public void setLimitBuy(LimitBuy limitBuy) {
/* 152 */     this.limitBuy = limitBuy;
/*     */   }
/*     */ 
/*     */   public Integer getId() {
/* 156 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(Integer id) {
/* 160 */     this.id = id;
/*     */   }
/*     */ 
/*     */   public boolean getIsEdit() {
/* 164 */     return this.isEdit;
/*     */   }
/*     */ 
/*     */   public void setIsEdit(boolean isEdit) {
/* 168 */     this.isEdit = isEdit;
/*     */   }
/*     */ 
/*     */   public Integer[] getGoodsid() {
/* 172 */     return this.goodsid;
/*     */   }
/*     */ 
/*     */   public void setGoodsid(Integer[] goodsid) {
/* 176 */     this.goodsid = goodsid;
/*     */   }
/*     */ 
/*     */   public Double[] getPrice() {
/* 180 */     return this.price;
/*     */   }
/*     */ 
/*     */   public void setPrice(Double[] price) {
/* 184 */     this.price = price;
/*     */   }
/*     */ 
/*     */   public String getStart_time() {
/* 188 */     return this.start_time;
/*     */   }
/*     */ 
/*     */   public void setStart_time(String startTime) {
/* 192 */     this.start_time = startTime;
/*     */   }
/*     */ 
/*     */   public String getEnd_time() {
/* 196 */     return this.end_time;
/*     */   }
/*     */ 
/*     */   public void setEnd_time(String endTime) {
/* 200 */     this.end_time = endTime;
/*     */   }
/*     */ 
/*     */   public void setEdit(boolean isEdit) {
/* 204 */     this.isEdit = isEdit;
/*     */   }
/*     */ 
/*     */   public int getStart_hour() {
/* 208 */     return this.start_hour;
/*     */   }
/*     */ 
/*     */   public void setStart_hour(int startHour) {
/* 212 */     this.start_hour = startHour;
/*     */   }
/*     */ 
/*     */   public int getEnd_hour() {
/* 216 */     return this.end_hour;
/*     */   }
/*     */ 
/*     */   public void setEnd_hour(int endHour) {
/* 220 */     this.end_hour = endHour;
/*     */   }
/*     */ 
/*     */   public File getImgFile() {
/* 224 */     return this.imgFile;
/*     */   }
/*     */ 
/*     */   public void setImgFile(File imgFile) {
/* 228 */     this.imgFile = imgFile;
/*     */   }
/*     */ 
/*     */   public String getImgFileFileName() {
/* 232 */     return this.imgFileFileName;
/*     */   }
/*     */ 
/*     */   public void setImgFileFileName(String imgFileFileName) {
/* 236 */     this.imgFileFileName = imgFileFileName;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.action.backend.LimitBuyAction
 * JD-Core Version:    0.6.0
 */