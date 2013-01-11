/*     */ package com.enation.app.shop.component.goodscore.plugin.sn;
/*     */ 
/*     */ import com.enation.app.shop.core.plugin.goods.AbstractGoodsSnCreator;
/*     */ import com.enation.app.shop.core.service.SnDuplicateException;
/*     */ import com.enation.framework.database.IDaoSupport;
/*     */ import com.enation.framework.util.DateUtil;
/*     */ import com.enation.framework.util.StringUtil;
/*     */ import java.util.Date;
/*     */ import java.util.Map;
/*     */ import org.springframework.stereotype.Component;
/*     */ 
/*     */ @Component
/*     */ public class DefaultGoodsSnCreatePlugin extends AbstractGoodsSnCreator
/*     */ {
/*     */   private IDaoSupport baseDaoSupport;
/*     */ 
/*     */   public IDaoSupport getBaseDaoSupport()
/*     */   {
/*  34 */     return this.baseDaoSupport;
/*     */   }
/*     */ 
/*     */   public void setBaseDaoSupport(IDaoSupport baseDaoSupport)
/*     */   {
/*  39 */     this.baseDaoSupport = baseDaoSupport;
/*     */   }
/*     */ 
/*     */   public void register()
/*     */   {
/*     */   }
/*     */ 
/*     */   public String createSn(Map goods)
/*     */   {
/*  55 */     if ((goods.get("sn") != null) && (!goods.get("sn").equals("")))
/*     */     {
/*  57 */       if (goods.get("goods_id") == null)
/*     */       {
/*  59 */         if (checkSn(goods.get("sn").toString()) == 1) {
/*  60 */           throw new SnDuplicateException(goods.get("sn").toString());
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/*     */         try
/*     */         {
/*  67 */           Integer goods_id = Integer.valueOf("" + goods.get("goods_id"));
/*     */ 
/*  69 */           if (checkSn(goods.get("sn").toString(), goods_id.intValue()) == 1) {
/*  70 */             throw new SnDuplicateException(goods.get("sn").toString());
/*     */           }
/*     */         }
/*     */         catch (NumberFormatException e)
/*     */         {
/*  75 */           throw new RuntimeException("商品id格式错误");
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*  80 */       return goods.get("sn").toString();
/*     */     }
/*     */ 
/*  85 */     String sn = "G" + DateUtil.toString(new Date(System.currentTimeMillis()), "yyyyMMddhhmmss") + StringUtil.getRandStr(4);
/*  86 */     return sn;
/*     */   }
/*     */ 
/*     */   private int checkSn(String sn)
/*     */   {
/*  97 */     String sql = "select count(0) num from goods where sn='" + sn + "'";
/*  98 */     int count = this.baseDaoSupport.queryForInt(sql, new Object[0]);
/*  99 */     count = count > 0 ? 1 : 0;
/* 100 */     return count;
/*     */   }
/*     */ 
/*     */   private int checkSn(String sn, int goods_id)
/*     */   {
/* 111 */     String sql = "select count(0) num from goods where sn='" + sn + "' and goods_id!=" + goods_id;
/*     */ 
/* 113 */     int count = this.baseDaoSupport.queryForInt(sql, new Object[0]);
/* 114 */     count = count > 0 ? 1 : 0;
/* 115 */     return count;
/*     */   }
/*     */ 
/*     */   public String getAuthor() {
/* 119 */     return "kingapex";
/*     */   }
/*     */ 
/*     */   public String getId() {
/* 123 */     return "goods.sn_creator";
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/* 128 */     return "默认商品货号生成插件";
/*     */   }
/*     */ 
/*     */   public String getType() {
/* 132 */     return "";
/*     */   }
/*     */ 
/*     */   public String getVersion() {
/* 136 */     return "1.0";
/*     */   }
/*     */ 
/*     */   public void perform(Object[] params)
/*     */   {
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.goodscore.plugin.sn.DefaultGoodsSnCreatePlugin
 * JD-Core Version:    0.6.0
 */