/*    */ package com.enation.app.shop.component.spec.plugin.order;
/*    */ 
/*    */ import com.enation.app.shop.component.spec.service.ISpecManager;
/*    */ import com.enation.app.shop.core.model.Cart;
/*    */ import com.enation.app.shop.core.model.support.CartItem;
/*    */ import com.enation.app.shop.core.plugin.cart.ICartAddEvent;
/*    */ import com.enation.app.shop.core.plugin.cart.ICartItemFilter;
/*    */ import com.enation.framework.context.webcontext.ThreadContextHolder;
/*    */ import com.enation.framework.plugin.AutoRegisterPlugin;
/*    */ import com.enation.framework.util.StringUtil;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import net.sf.json.JSONArray;
/*    */ import org.springframework.stereotype.Component;
/*    */ 
/*    */ @Component
/*    */ public class GoodsSpecCartPlugin extends AutoRegisterPlugin
/*    */   implements ICartAddEvent, ICartItemFilter
/*    */ {
/*    */   private ISpecManager specManager;
/*    */ 
/*    */   public void filter(List<CartItem> itemlist, String sessionid)
/*    */   {
/* 43 */     for (CartItem cartItem : itemlist) {
/* 44 */       String addon = cartItem.getAddon();
/* 45 */       if (!StringUtil.isEmpty(addon)) {
/* 46 */         JSONArray specArray = JSONArray.fromObject(addon);
/* 47 */         List list = (List)JSONArray.toCollection(specArray, Map.class);
/* 48 */         cartItem.getOthers().put("specList", list);
/*    */       }
/*    */     }
/*    */   }
/*    */ 
/*    */   public void add(Cart cart)
/*    */   {
/* 64 */     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
/* 65 */     String havespec = request.getParameter("havespec");
/*    */ 
/* 67 */     if ("1".equals(havespec)) {
/* 68 */       int productid = StringUtil.toInt(request.getParameter("productid"), true);
/* 69 */       List specList = this.specManager.getProSpecList(productid);
/* 70 */       if ((specList != null) && (!specList.isEmpty()))
/*    */       {
/* 72 */         String specstr = JSONArray.fromObject(specList).toString();
/* 73 */         cart.setAddon(specstr);
/*    */       }
/*    */     }
/*    */   }
/*    */ 
/*    */   public ISpecManager getSpecManager()
/*    */   {
/* 88 */     return this.specManager;
/*    */   }
/*    */ 
/*    */   public void setSpecManager(ISpecManager specManager) {
/* 92 */     this.specManager = specManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.spec.plugin.order.GoodsSpecCartPlugin
 * JD-Core Version:    0.6.0
 */