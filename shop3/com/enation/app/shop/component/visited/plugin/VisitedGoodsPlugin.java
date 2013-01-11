/*    */ package com.enation.app.shop.component.visited.plugin;
/*    */ 
/*    */ import com.enation.app.shop.core.plugin.goods.IGoodsVisitEvent;
/*    */ import com.enation.app.shop.core.service.IGoodsManager;
/*    */ import com.enation.framework.context.webcontext.ThreadContextHolder;
/*    */ import com.enation.framework.context.webcontext.WebSessionContext;
/*    */ import com.enation.framework.plugin.AutoRegisterPlugin;
/*    */ import com.enation.framework.util.StringUtil;
/*    */ import java.util.ArrayList;
/*    */ import java.util.HashMap;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import org.springframework.stereotype.Component;
/*    */ 
/*    */ @Component
/*    */ public class VisitedGoodsPlugin extends AutoRegisterPlugin
/*    */   implements IGoodsVisitEvent
/*    */ {
/*    */   private IGoodsManager goodsManager;
/*    */ 
/*    */   public void onVisit(Map goods)
/*    */   {
/* 36 */     WebSessionContext sessionContext = ThreadContextHolder.getSessionContext();
/* 37 */     List visitedGoods = (List)sessionContext.getAttribute("visitedGoods");
/* 38 */     Integer goods_id = Integer.valueOf(goods.get("goods_id").toString());
/* 39 */     boolean visited = false;
/* 40 */     if (visitedGoods == null) visitedGoods = new ArrayList();
/* 41 */     for (Map map : visitedGoods) {
/* 42 */       if (map.get("goods_id").toString().equals(StringUtil.toString(goods_id))) {
/* 43 */         visitedGoods.remove(map);
/* 44 */         visited = true;
/* 45 */         break;
/*    */       }
/*    */     }
/* 48 */     Map newmap = new HashMap();
/* 49 */     newmap.put("goods_id", goods_id);
/* 50 */     newmap.put("image_default", goods.get("image_default"));
/* 51 */     newmap.put("name", goods.get("name"));
/* 52 */     newmap.put("price", goods.get("price"));
/* 53 */     visitedGoods.add(0, newmap);
/* 54 */     sessionContext.setAttribute("visitedGoods", visitedGoods);
/* 55 */     if (!visited)
/* 56 */       this.goodsManager.incViewCount(goods_id);
/*    */   }
/*    */ 
/*    */   public IGoodsManager getGoodsManager()
/*    */   {
/* 61 */     return this.goodsManager;
/*    */   }
/*    */ 
/*    */   public void setGoodsManager(IGoodsManager goodsManager) {
/* 65 */     this.goodsManager = goodsManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.visited.plugin.VisitedGoodsPlugin
 * JD-Core Version:    0.6.0
 */