/*    */ package com.enation.app.shop.component.search.plugin;
/*    */ 
/*    */ import com.enation.app.shop.core.plugin.search.IGoodsDataFilter;
/*    */ import com.enation.framework.plugin.AutoRegisterPlugin;
/*    */ import java.sql.ResultSet;
/*    */ import java.sql.SQLException;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import org.springframework.stereotype.Component;
/*    */ 
/*    */ @Component
/*    */ public class GoodsPropertyDataFilter extends AutoRegisterPlugin
/*    */   implements IGoodsDataFilter
/*    */ {
/*    */   public void filter(Map<String, Object> goods, ResultSet rs)
/*    */   {
/* 25 */     Map propMap = new HashMap();
/*    */ 
/* 27 */     for (int i = 0; i < 20; i++) {
/*    */       try
/*    */       {
/* 30 */         String value = rs.getString("p" + (i + 1));
/* 31 */         propMap.put("p" + (i + 1), value);
/*    */       } catch (SQLException e) {
/* 33 */         e.printStackTrace();
/*    */       }
/*    */     }
/*    */ 
/* 37 */     goods.put("propMap", propMap);
/*    */   }
/*    */ 
/*    */   public String getAuthor()
/*    */   {
/* 43 */     return "kingapex";
/*    */   }
/*    */ 
/*    */   public String getId()
/*    */   {
/* 49 */     return "goodsPropertyDataFilter";
/*    */   }
/*    */ 
/*    */   public String getName()
/*    */   {
/* 55 */     return "商品属性数据过滤器";
/*    */   }
/*    */ 
/*    */   public String getType()
/*    */   {
/* 61 */     return "searchFilter";
/*    */   }
/*    */ 
/*    */   public String getVersion()
/*    */   {
/* 67 */     return "1.0";
/*    */   }
/*    */ 
/*    */   public void perform(Object[] params)
/*    */   {
/*    */   }
/*    */ 
/*    */   public void register()
/*    */   {
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.search.plugin.GoodsPropertyDataFilter
 * JD-Core Version:    0.6.0
 */