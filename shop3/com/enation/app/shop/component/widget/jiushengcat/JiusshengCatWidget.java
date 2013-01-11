/*    */ package com.enation.app.shop.component.widget.jiushengcat;
/*    */ 
/*    */ import com.enation.app.shop.core.service.IGoodsCatManager;
/*    */ import com.enation.app.shop.core.utils.UrlUtils;
/*    */ import com.enation.eop.sdk.widget.AbstractWidget;
/*    */ import com.enation.framework.context.webcontext.ThreadContextHolder;
/*    */ import com.enation.framework.util.StringUtil;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ 
/*    */ public class JiusshengCatWidget extends AbstractWidget
/*    */ {
/*    */   private IGoodsCatManager goodsCatManager;
/*    */ 
/*    */   protected void display(Map<String, String> params)
/*    */   {
/* 21 */     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
/* 22 */     String uri = request.getServletPath();
/*    */ 
/* 24 */     String catidstr = UrlUtils.getParamStringValue(uri, "cat");
/* 25 */     if (StringUtil.isEmpty(catidstr)) {
/* 26 */       catidstr = "0";
/*    */     }
/* 28 */     List cat_tree = this.goodsCatManager.listAllChildren(Integer.valueOf(0));
/*    */ 
/* 30 */     String catimage = (String)params.get("catimage");
/* 31 */     boolean showimage = (catimage != null) && (catimage.equals("on"));
/* 32 */     putData("showimg", Boolean.valueOf(showimage));
/* 33 */     putData("cat_tree", cat_tree);
/* 34 */     putData("curr_cat_id", Integer.valueOf(catidstr));
/*    */   }
/*    */ 
/*    */   protected void config(Map<String, String> params)
/*    */   {
/*    */   }
/*    */ 
/*    */   public IGoodsCatManager getGoodsCatManager()
/*    */   {
/* 45 */     return this.goodsCatManager;
/*    */   }
/*    */ 
/*    */   public void setGoodsCatManager(IGoodsCatManager goodsCatManager) {
/* 49 */     this.goodsCatManager = goodsCatManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.widget.jiushengcat.JiusshengCatWidget
 * JD-Core Version:    0.6.0
 */