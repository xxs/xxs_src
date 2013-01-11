/*    */ package com.enation.app.shop.component.member.plugin.point;
/*    */ 
/*    */ import com.enation.app.base.core.model.Member;
/*    */ import com.enation.app.shop.core.plugin.member.IMemberTabShowEvent;
/*    */ import com.enation.app.shop.core.service.IPointHistoryManager;
/*    */ import com.enation.eop.processor.core.freemarker.FreeMarkerPaser;
/*    */ import com.enation.framework.plugin.AutoRegisterPlugin;
/*    */ import java.util.List;
/*    */ import org.springframework.stereotype.Component;
/*    */ 
/*    */ @Component
/*    */ public class MemberMkPointHistoryPlugin extends AutoRegisterPlugin
/*    */   implements IMemberTabShowEvent
/*    */ {
/*    */   private IPointHistoryManager pointHistoryManager;
/*    */ 
/*    */   public boolean canBeExecute(Member member)
/*    */   {
/* 26 */     return true;
/*    */   }
/*    */ 
/*    */   public int getOrder()
/*    */   {
/* 31 */     return 13;
/*    */   }
/*    */ 
/*    */   public String getTabName(Member member)
/*    */   {
/* 36 */     return "消费积分";
/*    */   }
/*    */ 
/*    */   public String onShowMemberDetailHtml(Member member)
/*    */   {
/* 41 */     List listPointHistory = this.pointHistoryManager.listPointHistory(member.getMember_id().intValue(), 1);
/* 42 */     FreeMarkerPaser freeMarkerPaser = FreeMarkerPaser.getInstance();
/* 43 */     freeMarkerPaser.setClz(getClass());
/* 44 */     freeMarkerPaser.putData("listPointHistory", listPointHistory);
/* 45 */     freeMarkerPaser.setPageName("point_history");
/* 46 */     return freeMarkerPaser.proessPageContent();
/*    */   }
/*    */ 
/*    */   public IPointHistoryManager getPointHistoryManager() {
/* 50 */     return this.pointHistoryManager;
/*    */   }
/*    */ 
/*    */   public void setPointHistoryManager(IPointHistoryManager pointHistoryManager) {
/* 54 */     this.pointHistoryManager = pointHistoryManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.member.plugin.point.MemberMkPointHistoryPlugin
 * JD-Core Version:    0.6.0
 */