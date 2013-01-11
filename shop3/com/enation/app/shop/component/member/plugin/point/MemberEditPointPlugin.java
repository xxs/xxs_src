/*    */ package com.enation.app.shop.component.member.plugin.point;
/*    */ 
/*    */ import com.enation.app.base.core.model.Member;
/*    */ import com.enation.app.shop.core.model.PointHistory;
/*    */ import com.enation.app.shop.core.plugin.member.IMemberTabShowEvent;
/*    */ import com.enation.app.shop.core.service.IMemberManager;
/*    */ import com.enation.app.shop.core.service.IPointHistoryManager;
/*    */ import com.enation.eop.processor.core.freemarker.FreeMarkerPaser;
/*    */ import com.enation.framework.context.webcontext.ThreadContextHolder;
/*    */ import com.enation.framework.plugin.AutoRegisterPlugin;
/*    */ import com.enation.framework.plugin.IAjaxExecuteEnable;
/*    */ import com.enation.framework.util.JsonMessageUtil;
/*    */ import com.enation.framework.util.StringUtil;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import org.apache.log4j.Logger;
/*    */ import org.springframework.stereotype.Component;
/*    */ import org.springframework.transaction.annotation.Propagation;
/*    */ import org.springframework.transaction.annotation.Transactional;
/*    */ 
/*    */ @Component
/*    */ public class MemberEditPointPlugin extends AutoRegisterPlugin
/*    */   implements IMemberTabShowEvent, IAjaxExecuteEnable
/*    */ {
/*    */   private IMemberManager memberManager;
/*    */   private IPointHistoryManager pointHistoryManager;
/*    */ 
/*    */   public boolean canBeExecute(Member member)
/*    */   {
/* 34 */     return true;
/*    */   }
/*    */ 
/*    */   public int getOrder() {
/* 38 */     return 9;
/*    */   }
/*    */ 
/*    */   public String getTabName(Member member) {
/* 42 */     return "他的积分";
/*    */   }
/*    */ 
/*    */   public String onShowMemberDetailHtml(Member member) {
/* 46 */     FreeMarkerPaser freeMarkerPaser = FreeMarkerPaser.getInstance();
/* 47 */     freeMarkerPaser.setClz(getClass());
/* 48 */     freeMarkerPaser.putData("memberid", member.getMember_id());
/* 49 */     freeMarkerPaser.setPageName("point");
/* 50 */     return freeMarkerPaser.proessPageContent();
/*    */   }
/*    */   @Transactional(propagation=Propagation.REQUIRED)
/*    */   public String execute() {
/* 55 */     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
/* 56 */     int point = StringUtil.toInt(request.getParameter("modi_point"), true);
/* 57 */     int memberid = StringUtil.toInt(request.getParameter("memberid"), true);
/* 58 */     Member member = this.memberManager.get(Integer.valueOf(memberid));
/* 59 */     member.setPoint(Integer.valueOf(member.getPoint().intValue() + point));
/* 60 */     PointHistory pointHistory = new PointHistory();
/* 61 */     pointHistory.setMember_id(memberid);
/* 62 */     pointHistory.setOperator("管理员");
/* 63 */     pointHistory.setPoint(point);
/* 64 */     pointHistory.setReason("operator_adjust");
/* 65 */     pointHistory.setTime(Long.valueOf(System.currentTimeMillis()));
/*    */     try {
/* 67 */       this.memberManager.edit(member);
/* 68 */       this.pointHistoryManager.addPointHistory(pointHistory);
/* 69 */       return JsonMessageUtil.getSuccessJson("会员积分修改成功");
/*    */     } catch (Exception e) {
/* 71 */       this.logger.error("会员积分修改失败", e);
/* 72 */     }return JsonMessageUtil.getErrorJson("修改失败");
/*    */   }
/*    */ 
/*    */   public IMemberManager getMemberManager()
/*    */   {
/* 77 */     return this.memberManager;
/*    */   }
/*    */ 
/*    */   public void setMemberManager(IMemberManager memberManager) {
/* 81 */     this.memberManager = memberManager;
/*    */   }
/*    */ 
/*    */   public IPointHistoryManager getPointHistoryManager() {
/* 85 */     return this.pointHistoryManager;
/*    */   }
/*    */ 
/*    */   public void setPointHistoryManager(IPointHistoryManager pointHistoryManager) {
/* 89 */     this.pointHistoryManager = pointHistoryManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.member.plugin.point.MemberEditPointPlugin
 * JD-Core Version:    0.6.0
 */