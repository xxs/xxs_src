/*    */ package com.enation.app.base.core.service.auth.impl;
/*    */ 
/*    */ import com.enation.framework.util.FileUtil;
/*    */ import com.enation.framework.util.StringUtil;
/*    */ import java.io.InputStream;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import java.util.Properties;
/*    */ 
/*    */ public class PermissionConfig
/*    */ {
/* 12 */   private static Map<String, Integer> authMap = new HashMap();
/*    */ 
/*    */   public static int getAuthId(String type)
/*    */   {
/* 53 */     return ((Integer)authMap.get(type)).intValue();
/*    */   }
/*    */ 
/*    */   static
/*    */   {
/*    */     try
/*    */     {
/* 16 */       InputStream in = FileUtil.getResourceAsStream("auth.properties");
/* 17 */       Properties props = new Properties();
/* 18 */       props.load(in);
/*    */ 
/* 21 */       int allocation = StringUtil.toInt(props.getProperty("auth.allocation"), true);
/* 22 */       int depot_allocation = StringUtil.toInt(props.getProperty("auth.depot_allocation"), true);
/* 23 */       int depot_ship = StringUtil.toInt(props.getProperty("auth.depot_ship"), true);
/* 24 */       int depot_admin = StringUtil.toInt(props.getProperty("auth.depot_admin"), true);
/* 25 */       int finance = StringUtil.toInt(props.getProperty("auth.finance"), true);
/* 26 */       int order = StringUtil.toInt(props.getProperty("auth.order"), true);
/* 27 */       int goods = StringUtil.toInt(props.getProperty("auth.goods"), true);
/* 28 */       int depot_supper = StringUtil.toInt(props.getProperty("auth.depot_supper"), true);
/* 29 */       int customer_service = StringUtil.toInt(props.getProperty("auth.customer_service"), true);
/* 30 */       int add_goods = StringUtil.toInt(props.getProperty("auth.add_goods"), true);
/* 31 */       int goods_warn = StringUtil.toInt(props.getProperty("auth.goods_warn"), true);
/* 32 */       int super_admin = StringUtil.toInt(props.getProperty("auth.super_admin"), true);
/*    */ 
/* 34 */       authMap.put("allocation", Integer.valueOf(allocation));
/* 35 */       authMap.put("depot_allocation", Integer.valueOf(depot_allocation));
/* 36 */       authMap.put("depot_ship", Integer.valueOf(depot_ship));
/* 37 */       authMap.put("depot_admin", Integer.valueOf(depot_admin));
/* 38 */       authMap.put("finance", Integer.valueOf(finance));
/* 39 */       authMap.put("order", Integer.valueOf(order));
/* 40 */       authMap.put("goods", Integer.valueOf(goods));
/* 41 */       authMap.put("depot_supper", Integer.valueOf(depot_supper));
/* 42 */       authMap.put("customer_service", Integer.valueOf(customer_service));
/* 43 */       authMap.put("add_goods", Integer.valueOf(add_goods));
/* 44 */       authMap.put("goods_warn", Integer.valueOf(goods_warn));
/* 45 */       authMap.put("super_admin", Integer.valueOf(super_admin));
/*    */     }
/*    */     catch (Exception e) {
/* 48 */       e.printStackTrace();
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.app.base.core.service.auth.impl.PermissionConfig
 * JD-Core Version:    0.6.0
 */