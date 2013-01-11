/*     */ package com.enation.app.shop.component.goodscore.widget.goods.compare;
/*     */ 
/*     */ import com.enation.app.shop.core.model.Attribute;
/*     */ import com.enation.app.shop.core.model.Goods;
/*     */ import com.enation.app.shop.core.model.GoodsParam;
/*     */ import com.enation.app.shop.core.model.support.GoodsView;
/*     */ import com.enation.app.shop.core.model.support.ParamGroup;
/*     */ import com.enation.app.shop.core.service.IGoodsTypeManager;
/*     */ import com.enation.app.shop.core.utils.GoodsUtils;
/*     */ import com.enation.eop.sdk.widget.AbstractWidget;
/*     */ import com.enation.framework.context.webcontext.ThreadContextHolder;
/*     */ import com.enation.framework.database.IDaoSupport;
/*     */ import com.enation.framework.directive.ImageUrlDirectiveModel;
/*     */ import com.enation.framework.util.HttpUtil;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.net.URLDecoder;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.sf.json.JSONArray;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.springframework.context.annotation.Scope;
/*     */ import org.springframework.jdbc.core.RowMapper;
/*     */ import org.springframework.stereotype.Component;
/*     */ 
/*     */ @Component("goods_compare")
/*     */ @Scope("prototype")
/*     */ public class CompareGoodsWidget extends AbstractWidget
/*     */ {
/*     */   private IGoodsTypeManager goodsTypeManager;
/*     */ 
/*     */   protected void config(Map<String, String> params)
/*     */   {
/*     */   }
/*     */ 
/*     */   protected void display(Map<String, String> params)
/*     */   {
/*  48 */     String goodsStr = HttpUtil.getCookieValue(ThreadContextHolder.getHttpRequest(), "cmpgoods");
/*     */     try
/*     */     {
/*  51 */       if (goodsStr != null) {
/*  52 */         goodsStr = URLDecoder.decode(goodsStr, "UTF-8");
/*  53 */         String[] goodsArray = StringUtils.split(goodsStr, ",");
/*  54 */         String goodsidstr = "";
/*  55 */         for (String one : goodsArray) {
/*  56 */           if (!goodsidstr.equals("")) {
/*  57 */             goodsidstr = goodsidstr + ",";
/*     */           }
/*  59 */           goodsidstr = goodsidstr + one.split("\\|")[0];
/*     */         }
/*  61 */         List goodsList = getDiffGoodsList(goodsidstr);
/*  62 */         if (goodsList != null) {
/*  63 */           int goodsNum = goodsList.size();
/*  64 */           int width = 80 / goodsNum;
/*  65 */           putData("width", Integer.valueOf(width));
/*  66 */           putData("goodsNum", Integer.valueOf(goodsNum));
/*     */         }
/*     */ 
/*  69 */         ParamGroup[] paramGroups = fillDiffParam(goodsList);
/*  70 */         List propList = fillDiffProps(goodsList);
/*     */ 
/*  72 */         putData("goodsList", goodsList);
/*  73 */         putData("propList", propList);
/*  74 */         putData("paramGroups", paramGroups);
/*  75 */         putData("GoodsPic", new ImageUrlDirectiveModel());
/*     */       }
/*     */     }
/*     */     catch (UnsupportedEncodingException e)
/*     */     {
/*  80 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   private List getDiffGoodsList(String goods_ids)
/*     */   {
/*  86 */     String sql = "select * from goods where goods_id in(" + goods_ids + ")";
/*     */ 
/*  88 */     RowMapper mapper = new RowMapper()
/*     */     {
/*     */       public Object mapRow(ResultSet rs, int arg1) throws SQLException {
/*  91 */         GoodsView goods = new GoodsView();
/*  92 */         goods.setName(rs.getString("name"));
/*  93 */         goods.setSn(rs.getString("sn"));
/*  94 */         goods.setWeight(Double.valueOf(rs.getDouble("weight")));
/*  95 */         goods.setGoods_id(Integer.valueOf(rs.getInt("goods_id")));
/*  96 */         goods.setImage_default(rs.getString("image_default"));
/*  97 */         goods.setImage_file(rs.getString("image_file"));
/*  98 */         goods.setMktprice(Double.valueOf(rs.getDouble("mktprice")));
/*  99 */         goods.setPrice(Double.valueOf(rs.getDouble("price")));
/* 100 */         goods.setCreate_time(Long.valueOf(rs.getLong("create_time")));
/* 101 */         goods.setLast_modify(Long.valueOf(rs.getLong("last_modify")));
/* 102 */         goods.setParams(rs.getString("params"));
/* 103 */         goods.setIntro(rs.getString("intro"));
/* 104 */         goods.setBrief(rs.getString("brief"));
/* 105 */         goods.setSpecs(rs.getString("specs"));
/* 106 */         List specList = GoodsUtils.getSpecList(goods.getSpecs());
/* 107 */         goods.setSpecList(specList);
/* 108 */         goods.setType_id(Integer.valueOf(rs.getInt("type_id")));
/* 109 */         goods.setAdjuncts(rs.getString("adjuncts"));
/* 110 */         goods.setStore(Integer.valueOf(rs.getInt("store")));
/*     */ 
/* 112 */         Map propMap = new HashMap();
/*     */ 
/* 114 */         for (int i = 0; i < 20; i++) {
/* 115 */           String value = rs.getString("p" + (i + 1));
/* 116 */           propMap.put("p" + i, value);
/*     */         }
/* 118 */         goods.setPropMap(propMap);
/* 119 */         return goods;
/*     */       }
/*     */     };
/* 122 */     List list = this.baseDaoSupport.queryForList(sql, mapper, new Object[0]);
/* 123 */     return list;
/*     */   }
/*     */ 
/*     */   public ParamGroup[] converFormString(String params)
/*     */   {
/* 128 */     if ((params == null) || (params.equals("")) || (params.equals("[]")))
/* 129 */       return null;
/* 130 */     Map classMap = new HashMap();
/*     */ 
/* 132 */     classMap.put("paramList", GoodsParam.class);
/* 133 */     JSONArray jsonArray = JSONArray.fromObject(params);
/*     */ 
/* 135 */     Object obj = JSONArray.toArray(jsonArray, ParamGroup.class, classMap);
/*     */ 
/* 137 */     if (obj == null) {
/* 138 */       return null;
/*     */     }
/* 140 */     return (ParamGroup[])(ParamGroup[])obj;
/*     */   }
/*     */ 
/*     */   public ParamGroup[] fillDiffParam(List goodsList)
/*     */   {
/* 150 */     ParamGroup[] grou_params = null;
/*     */ 
/* 152 */     for (int i = 0; i < goodsList.size(); i++) {
/* 153 */       Goods goods = (Goods)goodsList.get(i);
/* 154 */       String paramString = goods.getParams();
/* 155 */       ParamGroup[] temp_params = converFormString(paramString);
/* 156 */       if (temp_params == null) {
/*     */         continue;
/*     */       }
/* 159 */       if (i == 0) {
/* 160 */         grou_params = temp_params;
/*     */       }
/*     */ 
/* 163 */       addValueToParams(temp_params, grou_params);
/*     */     }
/*     */ 
/* 167 */     return grou_params;
/*     */   }
/*     */ 
/*     */   private void addValueToParams(ParamGroup[] temp_params, ParamGroup[] grou_params)
/*     */   {
/* 174 */     for (int i = 0; i < grou_params.length; i++)
/*     */     {
/* 176 */       ParamGroup pg = grou_params[i];
/* 177 */       ParamGroup temp_pg = temp_params[i];
/*     */ 
/* 179 */       List paramList = pg.getParamList();
/* 180 */       List temp_paramList = temp_pg.getParamList();
/*     */ 
/* 183 */       for (int j = 0; j < paramList.size(); j++) {
/* 184 */         GoodsParam goodsParam = (GoodsParam)paramList.get(j);
/* 185 */         GoodsParam temp_goodsParam = (GoodsParam)temp_paramList.get(j);
/* 186 */         goodsParam.addValue(temp_goodsParam.getValue());
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public List fillDiffProps(List<GoodsView> goodsList)
/*     */   {
/* 200 */     if (goodsList == null) {
/* 201 */       return null;
/*     */     }
/*     */ 
/* 204 */     List propList = this.goodsTypeManager.getAttrListByTypeId(((GoodsView)goodsList.get(0)).getType_id().intValue());
/*     */ 
/* 206 */     for (int i = 0; i < goodsList.size(); i++) {
/* 207 */       GoodsView goods = (GoodsView)goodsList.get(i);
/* 208 */       addValueToProps(goods, propList);
/*     */     }
/*     */ 
/* 211 */     return propList;
/*     */   }
/*     */ 
/*     */   private void addValueToProps(GoodsView goods, List propList)
/*     */   {
/* 224 */     Map propMap = goods.getPropMap();
/* 225 */     for (int i = 0; i < propList.size(); i++) {
/* 226 */       Attribute attribute = (Attribute)propList.get(i);
/* 227 */       String value = (String)propMap.get("p" + i);
/* 228 */       attribute.setValue(value);
/* 229 */       attribute.addValue(attribute.getValStr());
/*     */     }
/*     */   }
/*     */ 
/*     */   public IGoodsTypeManager getGoodsTypeManager()
/*     */   {
/* 235 */     return this.goodsTypeManager;
/*     */   }
/*     */ 
/*     */   public void setGoodsTypeManager(IGoodsTypeManager goodsTypeManager) {
/* 239 */     this.goodsTypeManager = goodsTypeManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.goodscore.widget.goods.compare.CompareGoodsWidget
 * JD-Core Version:    0.6.0
 */