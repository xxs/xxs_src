/*     */ package com.enation.app.shop.component.product.plugin.goods;
/*     */ 
/*     */ import com.enation.app.shop.core.plugin.goods.AbstractGoodsPlugin;
/*     */ import com.enation.app.shop.core.plugin.goods.IGoodsTabShowEvent;
/*     */ import com.enation.app.shop.core.service.IMemberLvManager;
/*     */ import com.enation.app.shop.core.service.IMemberPriceManager;
/*     */ import com.enation.eop.processor.core.freemarker.FreeMarkerPaser;
/*     */ import com.enation.framework.context.webcontext.ThreadContextHolder;
/*     */ import com.enation.framework.util.StringUtil;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.springframework.stereotype.Component;
/*     */ 
/*     */ @Component
/*     */ public class StandardGoodsPricePlugin extends AbstractGoodsPlugin
/*     */   implements IGoodsTabShowEvent
/*     */ {
/*     */   private IMemberLvManager memberLvManager;
/*     */   private IMemberPriceManager memberPriceManager;
/*     */ 
/*     */   public String getAddHtml(HttpServletRequest request)
/*     */   {
/*  33 */     FreeMarkerPaser freeMarkerPaser = FreeMarkerPaser.getInstance();
/*     */ 
/*  35 */     freeMarkerPaser.setPageName("goods_price_input");
/*  36 */     return freeMarkerPaser.proessPageContent();
/*     */   }
/*     */ 
/*     */   public String getEditHtml(Map goods, HttpServletRequest request)
/*     */   {
/*  42 */     FreeMarkerPaser freeMarkerPaser = FreeMarkerPaser.getInstance();
/*     */ 
/*  44 */     List lvList = this.memberLvManager.list();
/*  45 */     freeMarkerPaser.putData("lvList", lvList);
/*     */ 
/*  47 */     freeMarkerPaser.setPageName("goods_price_input");
/*  48 */     return freeMarkerPaser.proessPageContent();
/*     */   }
/*     */ 
/*     */   public void onBeforeGoodsAdd(Map goods, HttpServletRequest request) {
/*  52 */     HttpServletRequest httpRequest = ThreadContextHolder.getHttpRequest();
/*  53 */     goods.put("cost", httpRequest.getParameter("cost"));
/*  54 */     goods.put("price", httpRequest.getParameter("price"));
/*  55 */     goods.put("weight", httpRequest.getParameter("weight"));
/*  56 */     goods.put("store", httpRequest.getParameter("store"));
/*     */ 
/*  58 */     if (StringUtil.isEmpty((String)goods.get("cost"))) goods.put("cost", Integer.valueOf(0));
/*  59 */     if (StringUtil.isEmpty((String)goods.get("price"))) goods.put("price", Integer.valueOf(0));
/*  60 */     if (StringUtil.isEmpty((String)goods.get("weight"))) goods.put("weight", Integer.valueOf(0));
/*  61 */     if (StringUtil.isEmpty((String)goods.get("store"))) goods.put("store", Integer.valueOf(0));
/*     */   }
/*     */ 
/*     */   public void onAfterGoodsAdd(Map goods, HttpServletRequest request)
/*     */     throws RuntimeException
/*     */   {
/*     */   }
/*     */ 
/*     */   public void onAfterGoodsEdit(Map goods, HttpServletRequest request)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void onBeforeGoodsEdit(Map goods, HttpServletRequest request)
/*     */   {
/*  79 */     HttpServletRequest httpRequest = ThreadContextHolder.getHttpRequest();
/*  80 */     goods.put("cost", httpRequest.getParameter("cost"));
/*  81 */     goods.put("price", httpRequest.getParameter("price"));
/*  82 */     goods.put("weight", httpRequest.getParameter("weight"));
/*  83 */     goods.put("store", httpRequest.getParameter("store"));
/*     */ 
/*  85 */     if (StringUtil.isEmpty((String)goods.get("cost"))) goods.put("cost", Integer.valueOf(0));
/*  86 */     if (StringUtil.isEmpty((String)goods.get("price"))) goods.put("price", Integer.valueOf(0));
/*  87 */     if (StringUtil.isEmpty((String)goods.get("weight"))) goods.put("weight", Integer.valueOf(0));
/*  88 */     if (StringUtil.isEmpty((String)goods.get("store"))) goods.put("store", Integer.valueOf(0));
/*     */   }
/*     */ 
/*     */   public String getAuthor()
/*     */   {
/*  94 */     return "kingapex";
/*     */   }
/*     */ 
/*     */   public String getId()
/*     */   {
/* 100 */     return "goods_price";
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/* 106 */     return "标准商品价格插件";
/*     */   }
/*     */ 
/*     */   public String getType()
/*     */   {
/* 112 */     return "goods";
/*     */   }
/*     */ 
/*     */   public String getVersion()
/*     */   {
/* 118 */     return "1.0";
/*     */   }
/*     */ 
/*     */   public void perform(Object[] params)
/*     */   {
/*     */   }
/*     */ 
/*     */   public IMemberLvManager getMemberLvManager()
/*     */   {
/* 129 */     return this.memberLvManager;
/*     */   }
/*     */ 
/*     */   public void setMemberLvManager(IMemberLvManager memberLvManager)
/*     */   {
/* 134 */     this.memberLvManager = memberLvManager;
/*     */   }
/*     */ 
/*     */   public IMemberPriceManager getMemberPriceManager()
/*     */   {
/* 139 */     return this.memberPriceManager;
/*     */   }
/*     */ 
/*     */   public void setMemberPriceManager(IMemberPriceManager memberPriceManager)
/*     */   {
/* 144 */     this.memberPriceManager = memberPriceManager;
/*     */   }
/*     */ 
/*     */   public String getTabName()
/*     */   {
/* 151 */     return "价格";
/*     */   }
/*     */ 
/*     */   public int getOrder()
/*     */   {
/* 158 */     return 5;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.product.plugin.goods.StandardGoodsPricePlugin
 * JD-Core Version:    0.6.0
 */