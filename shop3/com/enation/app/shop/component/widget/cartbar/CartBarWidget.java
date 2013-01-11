/*    */ package com.enation.app.shop.component.widget.cartbar;
/*    */ 
/*    */ import com.enation.app.base.core.model.Member;
/*    */ import com.enation.app.shop.core.service.ICartManager;
/*    */ import com.enation.eop.sdk.user.IUserService;
/*    */ import com.enation.eop.sdk.user.UserServiceFactory;
/*    */ import com.enation.eop.sdk.widget.AbstractWidget;
/*    */ import com.enation.framework.context.webcontext.ThreadContextHolder;
/*    */ import com.enation.framework.directive.ImageUrlDirectiveModel;
/*    */ import com.enation.framework.util.JsonMessageUtil;
/*    */ import java.util.HashMap;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpSession;
/*    */ import org.springframework.context.annotation.Scope;
/*    */ import org.springframework.stereotype.Component;
/*    */ 
/*    */ @Component("cart_bar")
/*    */ @Scope("prototype")
/*    */ public class CartBarWidget extends AbstractWidget
/*    */ {
/*    */   private ICartManager cartManager;
/*    */ 
/*    */   protected void config(Map<String, String> params)
/*    */   {
/*    */   }
/*    */ 
/*    */   protected void display(Map<String, String> params)
/*    */   {
/* 38 */     Member member = UserServiceFactory.getUserService().getCurrentMember();
/* 39 */     if (member == null)
/* 40 */       putData("isLogin", Boolean.valueOf(false));
/*    */     else {
/* 42 */       putData("isLogin", Boolean.valueOf(true));
/*    */     }
/*    */ 
/* 45 */     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
/* 46 */     String sessionid = request.getSession().getId();
/*    */ 
/* 50 */     if ("showList".equals(this.action)) {
/* 51 */       List goodsList = this.cartManager.listGoods(sessionid);
/* 52 */       putData("goodsList", goodsList);
/* 53 */       putData("GoodsPic", new ImageUrlDirectiveModel());
/* 54 */       setActionPageName("show_list");
/*    */     }
/*    */ 
/* 58 */     if ("showCount".equals(this.action)) {
/* 59 */       Double goodsTotal = this.cartManager.countGoodsTotal(sessionid);
/* 60 */       putData("total", goodsTotal);
/* 61 */       int count = this.cartManager.countItemNum(sessionid).intValue();
/* 62 */       putData("count", Integer.valueOf(count));
/* 63 */       Map data = new HashMap(2);
/* 64 */       data.put("total", goodsTotal);
/* 65 */       data.put("count", Integer.valueOf(count));
/* 66 */       String json = JsonMessageUtil.getObjectJson(data);
/* 67 */       showJson(json);
/*    */     }
/*    */   }
/*    */ 
/*    */   public ICartManager getCartManager()
/*    */   {
/* 76 */     return this.cartManager;
/*    */   }
/*    */ 
/*    */   public void setCartManager(ICartManager cartManager) {
/* 80 */     this.cartManager = cartManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.widget.cartbar.CartBarWidget
 * JD-Core Version:    0.6.0
 */