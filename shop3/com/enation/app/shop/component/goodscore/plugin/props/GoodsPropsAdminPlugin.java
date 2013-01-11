/*     */ package com.enation.app.shop.component.goodscore.plugin.props;
/*     */ 
/*     */ import com.enation.app.shop.core.model.Attribute;
/*     */ import com.enation.app.shop.core.model.Cat;
/*     */ import com.enation.app.shop.core.model.GoodsType;
/*     */ import com.enation.app.shop.core.plugin.goods.AbstractGoodsPlugin;
/*     */ import com.enation.app.shop.core.plugin.goods.IGoodsTabShowEvent;
/*     */ import com.enation.app.shop.core.service.IGoodsCatManager;
/*     */ import com.enation.app.shop.core.service.IGoodsTypeManager;
/*     */ import com.enation.eop.processor.core.freemarker.FreeMarkerPaser;
/*     */ import com.enation.framework.database.IDaoSupport;
/*     */ import com.enation.framework.util.StringUtil;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.springframework.stereotype.Component;
/*     */ 
/*     */ @Component
/*     */ public class GoodsPropsAdminPlugin extends AbstractGoodsPlugin
/*     */   implements IGoodsTabShowEvent
/*     */ {
/*     */   private IDaoSupport<GoodsType> baseDaoSupport;
/*     */   private IGoodsCatManager goodsCatManager;
/*     */   private IGoodsTypeManager goodsTypeManager;
/*     */ 
/*     */   public String getAddHtml(HttpServletRequest request)
/*     */   {
/*  47 */     FreeMarkerPaser freeMarkerPaser = FreeMarkerPaser.getInstance();
/*     */ 
/*  49 */     int catid = StringUtil.toInt(request.getParameter("catid"), true);
/*  50 */     Cat cat = this.goodsCatManager.getById(catid);
/*  51 */     int typeid = cat.getType_id().intValue();
/*  52 */     GoodsType goodsType = this.goodsTypeManager.get(Integer.valueOf(typeid));
/*     */ 
/*  54 */     List attrList = this.goodsTypeManager.getAttrListByTypeId(typeid);
/*     */ 
/*  56 */     if (goodsType.getJoin_brand() == 1) {
/*  57 */       List brandList = this.goodsTypeManager.listByTypeId(Integer.valueOf(typeid));
/*  58 */       freeMarkerPaser.putData("brandList", brandList);
/*     */     }
/*     */ 
/*  61 */     freeMarkerPaser.setPageName("props_input");
/*  62 */     freeMarkerPaser.putData("attrList", attrList);
/*     */ 
/*  65 */     return freeMarkerPaser.proessPageContent();
/*     */   }
/*     */ 
/*     */   public String getEditHtml(Map goods, HttpServletRequest request)
/*     */   {
/*  75 */     FreeMarkerPaser freeMarkerPaser = FreeMarkerPaser.getInstance();
/*     */ 
/*  77 */     if (goods.get("type_id") == null) {
/*  78 */       return "类型id为空";
/*     */     }
/*  80 */     Integer typeid = null;
/*     */     try {
/*  82 */       typeid = Integer.valueOf("" + goods.get("type_id"));
/*     */     } catch (NumberFormatException e) {
/*  84 */       return "类型不为数字";
/*     */     }
/*     */ 
/*  87 */     GoodsType goodsType = this.goodsTypeManager.getById(typeid.intValue());
/*  88 */     if (goodsType.getJoin_brand() == 1) {
/*  89 */       List brandList = this.goodsTypeManager.listByTypeId(typeid);
/*  90 */       freeMarkerPaser.putData("brandList", brandList);
/*     */     }
/*     */ 
/*  93 */     if (goodsType.getHave_prop() == 1)
/*     */     {
/*  95 */       Map propMap = new HashMap();
/*     */ 
/*  97 */       for (int i = 0; i < 20; i++)
/*     */       {
/*  99 */         String value = goods.get("p" + (i + 1)) == null ? "" : goods.get("p" + (i + 1)).toString();
/* 100 */         propMap.put("p" + i, value);
/*     */       }
/*     */ 
/* 103 */       goods.put("propMap", propMap);
/* 104 */       List propList = proessProps(goods, typeid);
/* 105 */       freeMarkerPaser.putData("attrList", propList);
/*     */     }
/*     */ 
/* 108 */     freeMarkerPaser.setPageName("props_input");
/*     */ 
/* 110 */     return freeMarkerPaser.proessPageContent();
/*     */   }
/*     */ 
/*     */   public void onBeforeGoodsAdd(Map goods, HttpServletRequest request)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void onBeforeGoodsEdit(Map goods, HttpServletRequest request)
/*     */   {
/* 141 */     String[] propvalues = request.getParameterValues("propvalues");
/*     */     try
/*     */     {
/* 144 */       Integer goods_id = Integer.valueOf("" + goods.get("goods_id"));
/* 145 */       saveProps(goods_id.intValue(), propvalues);
/*     */     }
/*     */     catch (NumberFormatException e) {
/* 148 */       throw new RuntimeException("商品id格式错误");
/*     */     }
/*     */   }
/*     */ 
/*     */   public void onAfterGoodsEdit(Map goods, HttpServletRequest request)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void onAfterGoodsAdd(Map goods, HttpServletRequest request)
/*     */   {
/* 173 */     String[] propvalues = request.getParameterValues("propvalues");
/*     */     try
/*     */     {
/* 176 */       Integer goods_id = Integer.valueOf("" + goods.get("goods_id"));
/* 177 */       saveProps(goods_id.intValue(), propvalues);
/*     */     }
/*     */     catch (NumberFormatException e) {
/* 180 */       throw new RuntimeException("商品id格式错误");
/*     */     }
/*     */   }
/*     */ 
/*     */   private void saveProps(int goodsid, String[] propvalues)
/*     */   {
/* 205 */     if ((propvalues != null) && (propvalues.length > 0)) {
/* 206 */       HashMap fields = new HashMap();
/* 207 */       int length = propvalues.length;
/* 208 */       length = length > 20 ? 20 : length;
/*     */ 
/* 212 */       for (int i = 0; i < length; i++) {
/* 213 */         String value = propvalues[i];
/* 214 */         fields.put("p" + (i + 1), value);
/*     */       }
/*     */ 
/* 218 */       this.baseDaoSupport.update("goods", fields, "goods_id=" + goodsid);
/*     */     }
/*     */   }
/*     */ 
/*     */   private List proessProps(Map goodsView, Integer typeid)
/*     */   {
/* 232 */     List propList = this.goodsTypeManager.getAttrListByTypeId(typeid.intValue());
/* 233 */     if (propList == null) return propList;
/* 234 */     Map propMap = (Map)goodsView.get("propMap");
/* 235 */     for (int i = 0; i < propList.size(); i++) {
/* 236 */       Attribute attribute = (Attribute)propList.get(i);
/* 237 */       String value = (String)propMap.get("p" + i);
/* 238 */       attribute.setValue(value);
/*     */     }
/* 240 */     return propList;
/*     */   }
/*     */ 
/*     */   public String getTabName()
/*     */   {
/* 248 */     return "属性";
/*     */   }
/*     */ 
/*     */   public int getOrder()
/*     */   {
/* 253 */     return 7;
/*     */   }
/*     */ 
/*     */   public IGoodsCatManager getGoodsCatManager() {
/* 257 */     return this.goodsCatManager;
/*     */   }
/*     */ 
/*     */   public void setGoodsCatManager(IGoodsCatManager goodsCatManager) {
/* 261 */     this.goodsCatManager = goodsCatManager;
/*     */   }
/*     */ 
/*     */   public IGoodsTypeManager getGoodsTypeManager() {
/* 265 */     return this.goodsTypeManager;
/*     */   }
/*     */ 
/*     */   public void setGoodsTypeManager(IGoodsTypeManager goodsTypeManager) {
/* 269 */     this.goodsTypeManager = goodsTypeManager;
/*     */   }
/*     */ 
/*     */   public IDaoSupport<GoodsType> getBaseDaoSupport() {
/* 273 */     return this.baseDaoSupport;
/*     */   }
/*     */ 
/*     */   public void setBaseDaoSupport(IDaoSupport<GoodsType> baseDaoSupport) {
/* 277 */     this.baseDaoSupport = baseDaoSupport;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.goodscore.plugin.props.GoodsPropsAdminPlugin
 * JD-Core Version:    0.6.0
 */