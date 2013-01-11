/*    */ package com.enation.app.shop.core.utils;
/*    */ 
/*    */ import com.enation.app.shop.core.model.support.Adjunct;
/*    */ import com.enation.app.shop.core.model.support.AdjunctGroup;
/*    */ import com.enation.app.shop.core.model.support.SpecJson;
/*    */ import java.util.ArrayList;
/*    */ import java.util.HashMap;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import net.sf.json.JSONArray;
/*    */ import net.sf.json.JSONObject;
/*    */ 
/*    */ public abstract class GoodsUtils
/*    */ {
/*    */   public static List getSpecList(String specString)
/*    */   {
/* 21 */     if ((specString == null) || (specString.equals("[]")) || (specString.equals(""))) {
/* 22 */       return new ArrayList();
/*    */     }
/* 24 */     JSONArray j1 = JSONArray.fromObject(specString);
/* 25 */     List list = (List)JSONArray.toCollection(j1, SpecJson.class);
/* 26 */     return list;
/*    */   }
/*    */ 
/*    */   public static AdjunctGroup converAdjFormString(String adjString)
/*    */   {
/* 37 */     if (adjString == null) {
/* 38 */       return null;
/*    */     }
/* 40 */     Map classMap = new HashMap();
/*    */ 
/* 42 */     classMap.put("adjList", Adjunct.class);
/* 43 */     JSONObject j1 = JSONObject.fromObject(adjString);
/* 44 */     AdjunctGroup adjunct = (AdjunctGroup)JSONObject.toBean(j1, AdjunctGroup.class, classMap);
/*    */ 
/* 46 */     return adjunct;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.utils.GoodsUtils
 * JD-Core Version:    0.6.0
 */