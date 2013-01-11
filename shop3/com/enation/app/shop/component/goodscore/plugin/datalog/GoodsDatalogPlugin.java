/*     */ package com.enation.app.shop.component.goodscore.plugin.datalog;
/*     */ 
/*     */ import com.enation.app.base.core.model.DataLog;
/*     */ import com.enation.app.shop.core.plugin.goods.IGoodsAfterAddEvent;
/*     */ import com.enation.app.shop.core.plugin.goods.IGoodsAfterEditEvent;
/*     */ import com.enation.eop.resource.IDataLogManager;
/*     */ import com.enation.eop.sdk.utils.UploadUtil;
/*     */ import com.enation.framework.plugin.AutoRegisterPlugin;
/*     */ import com.enation.framework.util.StringUtil;
/*     */ import java.util.Map;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.springframework.stereotype.Component;
/*     */ 
/*     */ @Component
/*     */ public class GoodsDatalogPlugin extends AutoRegisterPlugin
/*     */   implements IGoodsAfterAddEvent, IGoodsAfterEditEvent
/*     */ {
/*     */   private IDataLogManager dataLogManager;
/*     */ 
/*     */   public void register()
/*     */   {
/*     */   }
/*     */ 
/*     */   public void onAfterGoodsAdd(Map goods, HttpServletRequest request)
/*     */     throws RuntimeException
/*     */   {
/*  36 */     DataLog datalog = createDataLog(goods);
/*  37 */     datalog.setOptype("添加");
/*  38 */     this.dataLogManager.add(datalog);
/*     */   }
/*     */ 
/*     */   public void onAfterGoodsEdit(Map goods, HttpServletRequest request)
/*     */   {
/*  44 */     DataLog datalog = createDataLog(goods);
/*  45 */     datalog.setOptype("修改");
/*  46 */     this.dataLogManager.add(datalog);
/*     */   }
/*     */ 
/*     */   private DataLog createDataLog(Map goods)
/*     */   {
/*  52 */     DataLog datalog = new DataLog();
/*  53 */     datalog.setContent("商品名:" + goods.get("name") + "<br>" + "描述:" + goods.get("intro"));
/*  54 */     String image_file = (String)goods.get("image_file");
/*     */ 
/*  56 */     StringBuffer pics = new StringBuffer();
/*  57 */     if (!StringUtil.isEmpty(image_file)) {
/*  58 */       String[] files = StringUtils.split(image_file, ",");
/*  59 */       for (String file : files) {
/*  60 */         if (pics.length() > 0)
/*  61 */           pics.append(",");
/*  62 */         pics.append(UploadUtil.getThumbPath(file, "_thumbnail"));
/*  63 */         pics.append("|");
/*  64 */         pics.append(file);
/*     */       }
/*     */     }
/*     */ 
/*  68 */     datalog.setPics(pics.toString());
/*  69 */     datalog.setLogtype("商品");
/*  70 */     datalog.setOptype("添加");
/*  71 */     datalog.setUrl("/goods-" + goods.get("goods_id") + ".html");
/*     */ 
/*  73 */     return datalog;
/*     */   }
/*     */ 
/*     */   public String getAuthor()
/*     */   {
/*  81 */     return "kingapex";
/*     */   }
/*     */ 
/*     */   public String getId()
/*     */   {
/*  87 */     return "goodsdatalog";
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/*  93 */     return "商品数据日志记录插件";
/*     */   }
/*     */ 
/*     */   public String getType()
/*     */   {
/*  99 */     return "datalog";
/*     */   }
/*     */ 
/*     */   public String getVersion()
/*     */   {
/* 105 */     return "1.0";
/*     */   }
/*     */ 
/*     */   public void perform(Object[] params)
/*     */   {
/*     */   }
/*     */ 
/*     */   public IDataLogManager getDataLogManager()
/*     */   {
/* 116 */     return this.dataLogManager;
/*     */   }
/*     */ 
/*     */   public void setDataLogManager(IDataLogManager dataLogManager)
/*     */   {
/* 121 */     this.dataLogManager = dataLogManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.goodscore.plugin.datalog.GoodsDatalogPlugin
 * JD-Core Version:    0.6.0
 */