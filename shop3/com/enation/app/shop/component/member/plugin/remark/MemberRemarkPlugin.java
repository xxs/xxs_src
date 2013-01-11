/*     */ package com.enation.app.shop.component.member.plugin.remark;
/*     */ 
/*     */ import com.enation.app.base.core.model.Member;
/*     */ import com.enation.app.shop.core.plugin.member.IMemberTabShowEvent;
/*     */ import com.enation.app.shop.core.service.IMemberManager;
/*     */ import com.enation.eop.processor.core.freemarker.FreeMarkerPaser;
/*     */ import com.enation.framework.context.webcontext.ThreadContextHolder;
/*     */ import com.enation.framework.plugin.AutoRegisterPlugin;
/*     */ import com.enation.framework.plugin.IAjaxExecuteEnable;
/*     */ import com.enation.framework.util.JsonMessageUtil;
/*     */ import com.enation.framework.util.StringUtil;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.springframework.stereotype.Component;
/*     */ 
/*     */ @Component
/*     */ public class MemberRemarkPlugin extends AutoRegisterPlugin
/*     */   implements IMemberTabShowEvent, IAjaxExecuteEnable
/*     */ {
/*     */   private IMemberManager memberManager;
/*     */ 
/*     */   public String execute()
/*     */   {
/*  33 */     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
/*  34 */     String modify_memo = request.getParameter("modify_memo");
/*  35 */     int memberid = StringUtil.toInt(request.getParameter("memberid"), true);
/*  36 */     Member member = this.memberManager.get(Integer.valueOf(memberid));
/*  37 */     member.setRemark(modify_memo);
/*     */     try {
/*  39 */       this.memberManager.edit(member);
/*  40 */       return JsonMessageUtil.getSuccessJson("会员备注修改成功");
/*     */     } catch (Exception e) {
/*  42 */       this.logger.error("修改会员备注", e);
/*  43 */     }return JsonMessageUtil.getErrorJson("会员备注修改失败");
/*     */   }
/*     */ 
/*     */   public String getTabName(Member member)
/*     */   {
/*  56 */     return "会员备注";
/*     */   }
/*     */ 
/*     */   public int getOrder()
/*     */   {
/*  66 */     return 27;
/*     */   }
/*     */ 
/*     */   public boolean canBeExecute(Member member)
/*     */   {
/*  76 */     return true;
/*     */   }
/*     */ 
/*     */   public String onShowMemberDetailHtml(Member member)
/*     */   {
/*  87 */     FreeMarkerPaser freeMarkerPaser = FreeMarkerPaser.getInstance();
/*  88 */     freeMarkerPaser.setClz(getClass());
/*  89 */     freeMarkerPaser.putData("memberid", member.getMember_id());
/*  90 */     freeMarkerPaser.setPageName("remark");
/*  91 */     return freeMarkerPaser.proessPageContent();
/*     */   }
/*     */ 
/*     */   public IMemberManager getMemberManager()
/*     */   {
/*  99 */     return this.memberManager;
/*     */   }
/*     */ 
/*     */   public void setMemberManager(IMemberManager memberManager)
/*     */   {
/* 106 */     this.memberManager = memberManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.member.plugin.remark.MemberRemarkPlugin
 * JD-Core Version:    0.6.0
 */