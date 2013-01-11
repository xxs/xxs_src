/*    */ package com.enation.app.shop.component.widget.nav;
/*    */ 
/*    */ import com.enation.app.shop.core.model.Cat;
/*    */ import com.enation.app.shop.core.service.IGoodsCatManager;
/*    */ import com.enation.app.shop.core.utils.UrlUtils;
/*    */ import com.enation.eop.sdk.widget.AbstractWidget;
/*    */ import com.enation.framework.context.webcontext.ThreadContextHolder;
/*    */ import com.enation.framework.util.StringUtil;
/*    */ import java.util.Map;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import org.springframework.context.annotation.Scope;
/*    */ import org.springframework.stereotype.Component;
/*    */ 
/*    */ @Component("search_nav")
/*    */ @Scope("prototype")
/*    */ public class SearchNavWidget extends AbstractWidget
/*    */ {
/*    */   private IGoodsCatManager goodsCatManager;
/*    */ 
/*    */   protected void config(Map<String, String> params)
/*    */   {
/*    */   }
/*    */ 
/*    */   protected void display(Map<String, String> params)
/*    */   {
/* 34 */     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
/* 35 */     String url = request.getServletPath();
/* 36 */     Cat cat = null;
/* 37 */     String catidstr = UrlUtils.getParamStringValue(url, "cat");
/* 38 */     if ((!StringUtil.isEmpty(catidstr)) && (!"0".equals(catidstr))) {
/* 39 */       Integer catid = Integer.valueOf(catidstr);
/* 40 */       cat = this.goodsCatManager.getById(catid.intValue());
/* 41 */       putData("cat", cat);
/*    */     }
/*    */   }
/*    */ 
/*    */   public IGoodsCatManager getGoodsCatManager() {
/* 46 */     return this.goodsCatManager;
/*    */   }
/*    */ 
/*    */   public void setGoodsCatManager(IGoodsCatManager goodsCatManager) {
/* 50 */     this.goodsCatManager = goodsCatManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.widget.nav.SearchNavWidget
 * JD-Core Version:    0.6.0
 */