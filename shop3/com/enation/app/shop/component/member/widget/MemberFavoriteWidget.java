/*     */ package com.enation.app.shop.component.member.widget;
/*     */ 
/*     */ import com.enation.app.base.core.model.Member;
/*     */ import com.enation.app.shop.core.service.IFavoriteManager;
/*     */ import com.enation.eop.sdk.user.IUserService;
/*     */ import com.enation.eop.sdk.user.UserServiceFactory;
/*     */ import com.enation.eop.sdk.widget.AbstractMemberWidget;
/*     */ import com.enation.framework.context.webcontext.ThreadContextHolder;
/*     */ import com.enation.framework.database.Page;
/*     */ import com.enation.framework.directive.ImageUrlDirectiveModel;
/*     */ import com.enation.framework.util.StringUtil;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.springframework.context.annotation.Scope;
/*     */ import org.springframework.stereotype.Component;
/*     */ 
/*     */ @Component("member_favorite")
/*     */ @Scope("prototype")
/*     */ public class MemberFavoriteWidget extends AbstractMemberWidget
/*     */ {
/*     */   private IFavoriteManager favoriteManager;
/*     */ 
/*     */   protected void config(Map<String, String> params)
/*     */   {
/*     */   }
/*     */ 
/*     */   protected void display(Map<String, String> params)
/*     */   {
/*  39 */     setMenu("favorite");
/*  40 */     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
/*  41 */     String action = request.getParameter("action");
/*  42 */     action = action == null ? "" : action;
/*     */ 
/*  44 */     if (action.equals("")) {
/*  45 */       setPageName("member_favorite");
/*  46 */       String page = request.getParameter("page");
/*  47 */       page = page == null ? "1" : page;
/*  48 */       int pageSize = 20;
/*  49 */       Page favoritePage = this.favoriteManager.list(Integer.valueOf(page).intValue(), pageSize);
/*     */ 
/*  51 */       List favoriteList = (List)favoritePage.getResult();
/*  52 */       favoriteList = favoriteList == null ? new ArrayList() : favoriteList;
/*     */ 
/*  54 */       Long pageCount = Long.valueOf(favoritePage.getTotalPageCount());
/*  55 */       putData("pageSize", Integer.valueOf(pageSize));
/*  56 */       putData("page", page);
/*  57 */       putData("favoriteList", favoriteList);
/*  58 */       putData("totalCount", Long.valueOf(favoritePage.getTotalCount()));
/*  59 */       putData("pageCount", pageCount);
/*  60 */       putData("GoodsPic", new ImageUrlDirectiveModel());
/*  61 */     } else if ("favorite".equals(action)) {
/*  62 */       favorite();
/*  63 */     } else if (action.equals("delete")) {
/*  64 */       showMenu(false);
/*     */ 
/*  66 */       String favorite_id = request.getParameter("favorite_id");
/*     */       try {
/*  68 */         this.favoriteManager.delete(Integer.valueOf(favorite_id).intValue());
/*  69 */         showSuccess("删除成功", "商品收藏", "member_favorite.html");
/*     */       } catch (Exception e) {
/*  71 */         if (this.logger.isDebugEnabled()) {
/*  72 */           this.logger.error(e.getStackTrace());
/*     */         }
/*  74 */         showError("删除失败", "商品收藏", "member_favorite.html");
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void favorite() {
/*  80 */     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
/*     */ 
/*  82 */     int goodsid = StringUtil.toInt(request.getParameter("goodsid"), true);
/*     */ 
/*  84 */     Member member = UserServiceFactory.getUserService().getCurrentMember();
/*  85 */     if (member != null) {
/*  86 */       if (this.favoriteManager.getCount(Integer.valueOf(goodsid), member.getMember_id()) <= 0) {
/*  87 */         this.favoriteManager.add(Integer.valueOf(goodsid));
/*  88 */         showSuccessJson("收藏成功");
/*     */       } else {
/*  90 */         showSuccessJson("该商品已添加到您的收藏夹！");
/*     */       }
/*     */     }
/*  93 */     else showSuccessJson("您尚未登陆，不能使用收藏功能");
/*     */   }
/*     */ 
/*     */   public IFavoriteManager getFavoriteManager()
/*     */   {
/*  99 */     return this.favoriteManager;
/*     */   }
/*     */ 
/*     */   public void setFavoriteManager(IFavoriteManager favoriteManager) {
/* 103 */     this.favoriteManager = favoriteManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.member.widget.MemberFavoriteWidget
 * JD-Core Version:    0.6.0
 */