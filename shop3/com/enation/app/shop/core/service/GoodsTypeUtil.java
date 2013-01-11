/*    */ package com.enation.app.shop.core.service;
/*    */ 
/*    */ import com.enation.app.shop.core.model.Attribute;
/*    */ import com.enation.app.shop.core.model.GoodsParam;
/*    */ import com.enation.app.shop.core.model.support.ParamGroup;
/*    */ import java.util.ArrayList;
/*    */ import java.util.HashMap;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import net.sf.json.JSONArray;
/*    */ 
/*    */ public class GoodsTypeUtil
/*    */ {
/*    */   public static ParamGroup[] converFormString(String params)
/*    */   {
/* 23 */     if ((params == null) || (params.equals("")) || (params.equals("[]")))
/* 24 */       return null;
/* 25 */     Map classMap = new HashMap();
/*    */ 
/* 27 */     classMap.put("paramList", GoodsParam.class);
/* 28 */     JSONArray jsonArray = JSONArray.fromObject(params);
/*    */ 
/* 30 */     Object obj = JSONArray.toArray(jsonArray, ParamGroup.class, classMap);
/*    */ 
/* 32 */     if (obj == null) {
/* 33 */       return null;
/*    */     }
/* 35 */     return (ParamGroup[])(ParamGroup[])obj;
/*    */   }
/*    */ 
/*    */   public static List<Attribute> converAttrFormString(String props)
/*    */   {
/* 44 */     if ((props == null) || (props.equals(""))) {
/* 45 */       return new ArrayList();
/*    */     }
/* 47 */     JSONArray jsonArray = JSONArray.fromObject(props);
/* 48 */     List list = (List)JSONArray.toCollection(jsonArray, Attribute.class);
/*    */ 
/* 50 */     return list;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.service.GoodsTypeUtil
 * JD-Core Version:    0.6.0
 */