/*     */ package com.enation.app.shop.component.member.plugin.advance;
/*     */ 
/*     */ import com.enation.app.base.core.model.Member;
/*     */ import com.enation.app.shop.core.model.AdvanceLogs;
/*     */ import com.enation.app.shop.core.plugin.member.IMemberTabShowEvent;
/*     */ import com.enation.app.shop.core.service.IAdvanceLogsManager;
/*     */ import com.enation.app.shop.core.service.IMemberManager;
/*     */ import com.enation.eop.processor.core.freemarker.FreeMarkerPaser;
/*     */ import com.enation.framework.context.webcontext.ThreadContextHolder;
/*     */ import com.enation.framework.plugin.AutoRegisterPlugin;
/*     */ import com.enation.framework.plugin.IAjaxExecuteEnable;
/*     */ import com.enation.framework.util.JsonMessageUtil;
/*     */ import com.enation.framework.util.StringUtil;
/*     */ import java.util.List;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.springframework.stereotype.Component;
/*     */ import org.springframework.transaction.annotation.Propagation;
/*     */ import org.springframework.transaction.annotation.Transactional;
/*     */ 
/*     */ @Component
/*     */ public class MemberEditAdvancePlugin extends AutoRegisterPlugin
/*     */   implements IMemberTabShowEvent, IAjaxExecuteEnable
/*     */ {
/*     */   private IMemberManager memberManager;
/*     */   private IAdvanceLogsManager advanceLogsManager;
/*     */ 
/*     */   public boolean canBeExecute(Member member)
/*     */   {
/*  37 */     return true;
/*     */   }
/*     */ 
/*     */   public int getOrder()
/*     */   {
/*  42 */     return 15;
/*     */   }
/*     */ 
/*     */   public String getTabName(Member member)
/*     */   {
/*  47 */     return "他的预存款";
/*     */   }
/*     */ 
/*     */   public String onShowMemberDetailHtml(Member member)
/*     */   {
/*  52 */     FreeMarkerPaser freeMarkerPaser = FreeMarkerPaser.getInstance();
/*  53 */     freeMarkerPaser.setClz(getClass());
/*  54 */     freeMarkerPaser.putData("memberid", member.getMember_id());
/*  55 */     List listAdvanceLogs = this.advanceLogsManager.listAdvanceLogsByMemberId(member.getMember_id().intValue());
/*     */ 
/*  57 */     freeMarkerPaser.putData("listAdvanceLogs", listAdvanceLogs);
/*  58 */     freeMarkerPaser.setPageName("advance");
/*  59 */     return freeMarkerPaser.proessPageContent();
/*     */   }
/*     */ 
/*     */   @Transactional(propagation=Propagation.REQUIRED)
/*     */   public String execute() {
/*  65 */     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
/*  66 */     double modify_advance = StringUtil.toDouble(request.getParameter("modify_advance"), true).doubleValue();
/*  67 */     String modify_memo = request.getParameter("modify_memo");
/*  68 */     int memberid = StringUtil.toInt(request.getParameter("memberid"), true);
/*  69 */     Member member = this.memberManager.get(Integer.valueOf(memberid));
/*  70 */     member.setAdvance(Double.valueOf(member.getAdvance().doubleValue() + modify_advance));
/*  71 */     AdvanceLogs advanceLogs = new AdvanceLogs();
/*  72 */     advanceLogs.setMember_id(memberid);
/*  73 */     advanceLogs.setDisabled("false");
/*  74 */     advanceLogs.setMtime(Long.valueOf(System.currentTimeMillis()));
/*  75 */     advanceLogs.setImport_money(Double.valueOf(modify_advance));
/*  76 */     advanceLogs.setMember_advance(member.getAdvance());
/*  77 */     advanceLogs.setShop_advance(member.getAdvance());
/*  78 */     advanceLogs.setMoney(Double.valueOf(modify_advance));
/*  79 */     advanceLogs.setMessage(modify_memo);
/*  80 */     advanceLogs.setMemo("admin代充值");
/*     */     try {
/*  82 */       this.memberManager.edit(member);
/*  83 */       this.advanceLogsManager.add(advanceLogs);
/*  84 */       return JsonMessageUtil.getSuccessJson("会员预存款修改成功");
/*     */     } catch (Exception e) {
/*  86 */       this.logger.error("会员预存款修改失败", e);
/*  87 */     }return JsonMessageUtil.getErrorJson("修改失败");
/*     */   }
/*     */ 
/*     */   public IMemberManager getMemberManager()
/*     */   {
/*  92 */     return this.memberManager;
/*     */   }
/*     */ 
/*     */   public void setMemberManager(IMemberManager memberManager) {
/*  96 */     this.memberManager = memberManager;
/*     */   }
/*     */ 
/*     */   public IAdvanceLogsManager getAdvanceLogsManager() {
/* 100 */     return this.advanceLogsManager;
/*     */   }
/*     */ 
/*     */   public void setAdvanceLogsManager(IAdvanceLogsManager advanceLogsManager) {
/* 104 */     this.advanceLogsManager = advanceLogsManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.member.plugin.advance.MemberEditAdvancePlugin
 * JD-Core Version:    0.6.0
 */