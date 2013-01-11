/*     */ package com.enation.app.shop.component.member.widget;
/*     */ 
/*     */ import com.enation.app.base.core.model.Member;
/*     */ import com.enation.app.shop.core.service.IGnotifyManager;
/*     */ import com.enation.eop.sdk.user.IUserService;
/*     */ import com.enation.eop.sdk.user.UserServiceFactory;
/*     */ import com.enation.eop.sdk.widget.AbstractMemberWidget;
/*     */ import com.enation.framework.context.webcontext.ThreadContextHolder;
/*     */ import com.enation.framework.database.Page;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.springframework.context.annotation.Scope;
/*     */ import org.springframework.stereotype.Component;
/*     */ 
/*     */ @Component("member_gnotify")
/*     */ @Scope("prototype")
/*     */ public class MemberGnotifyWidget extends AbstractMemberWidget
/*     */ {
/*     */   private IGnotifyManager gnotifyManager;
/*     */ 
/*     */   protected void config(Map<String, String> params)
/*     */   {
/*     */   }
/*     */ 
/*     */   protected void display(Map<String, String> params)
/*     */   {
/*  39 */     setPageName("member_gnotify");
/*  40 */     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
/*  41 */     String action = request.getParameter("action");
/*  42 */     action = action == null ? "" : action;
/*  43 */     if (action.equals("")) {
/*  44 */       String page = request.getParameter("page");
/*  45 */       page = page == null ? "1" : page;
/*  46 */       int pageSize = 20;
/*  47 */       Page gnotifyPage = this.gnotifyManager.pageGnotify(Integer.valueOf(page).intValue(), pageSize);
/*     */ 
/*  49 */       Long totalCount = Long.valueOf(gnotifyPage.getTotalCount());
/*  50 */       Long pageCount = Long.valueOf(gnotifyPage.getTotalPageCount());
/*  51 */       List gnotifyList = (List)gnotifyPage.getResult();
/*  52 */       gnotifyList = gnotifyList == null ? new ArrayList() : gnotifyList;
/*  53 */       putData("totalCount", totalCount);
/*  54 */       putData("pageSize", Integer.valueOf(pageSize));
/*  55 */       putData("page", page);
/*     */ 
/*  57 */       putData("gnotifyList", gnotifyList);
/*  58 */       putData("pageCount", pageCount);
/*  59 */     } else if (action.equals("delete")) {
/*  60 */       showMenu(false);
/*  61 */       String gnotify_id = request.getParameter("gnotify_id");
/*     */       try {
/*  63 */         this.gnotifyManager.deleteGnotify(Integer.valueOf(gnotify_id).intValue());
/*     */ 
/*  65 */         showSuccess("删除成功", "缺货登记", "member_gnotify.html");
/*     */       } catch (Exception e) {
/*  67 */         if (this.logger.isDebugEnabled()) {
/*  68 */           this.logger.error(e.getStackTrace());
/*     */         }
/*  70 */         showError("删除失败", "缺货登记", "member_favorite.html");
/*     */       }
/*  72 */     } else if (action.equals("add")) {
/*  73 */       add();
/*     */     }
/*     */   }
/*     */ 
/*     */   private void add()
/*     */   {
/*     */     try
/*     */     {
/*  81 */       HttpServletRequest request = ThreadContextHolder.getHttpRequest();
/*  82 */       String productid = request.getParameter("goodsid");
/*  83 */       if ((productid == null) || ("".equals(productid))) {
/*  84 */         showErrorJson("商品id不能为空");
/*     */       }
/*  86 */       Member member = UserServiceFactory.getUserService().getCurrentMember();
/*     */ 
/*  88 */       if (member != null) {
/*  89 */         this.gnotifyManager.addGnotify(Integer.valueOf(productid).intValue());
/*  90 */         showSuccessJson("登记成功");
/*     */       } else {
/*  92 */         showErrorJson("您尚未登陆，不能进行缺货登记");
/*     */       }
/*     */     } catch (RuntimeException e) {
/*  95 */       this.logger.error("缺货登记发生错误", e);
/*  96 */       showErrorJson(e.getMessage());
/*     */     }
/*     */   }
/*     */ 
/*     */   public IGnotifyManager getGnotifyManager()
/*     */   {
/* 102 */     return this.gnotifyManager;
/*     */   }
/*     */ 
/*     */   public void setGnotifyManager(IGnotifyManager gnotifyManager) {
/* 106 */     this.gnotifyManager = gnotifyManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.member.widget.MemberGnotifyWidget
 * JD-Core Version:    0.6.0
 */