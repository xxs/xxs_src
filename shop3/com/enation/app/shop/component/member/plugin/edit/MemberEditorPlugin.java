/*    */ package com.enation.app.shop.component.member.plugin.edit;
/*    */ 
/*    */ import com.enation.app.base.core.model.Member;
/*    */ import com.enation.app.shop.core.plugin.member.IMemberTabShowEvent;
/*    */ import com.enation.app.shop.core.service.IMemberLvManager;
/*    */ import com.enation.app.shop.core.service.IMemberManager;
/*    */ import com.enation.eop.processor.core.freemarker.FreeMarkerPaser;
/*    */ import com.enation.framework.context.webcontext.ThreadContextHolder;
/*    */ import com.enation.framework.plugin.AutoRegisterPlugin;
/*    */ import com.enation.framework.plugin.IAjaxExecuteEnable;
/*    */ import java.util.List;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import org.springframework.stereotype.Component;
/*    */ 
/*    */ @Component
/*    */ public class MemberEditorPlugin extends AutoRegisterPlugin
/*    */   implements IMemberTabShowEvent, IAjaxExecuteEnable
/*    */ {
/*    */   private IMemberManager memberManager;
/*    */   private IMemberLvManager memberLvManager;
/*    */ 
/*    */   public String execute()
/*    */   {
/* 28 */     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
/* 29 */     String action = request.getParameter("action");
/*    */ 
/* 31 */     return null;
/*    */   }
/*    */ 
/*    */   public String onShowMemberDetailHtml(Member member)
/*    */   {
/* 43 */     FreeMarkerPaser freeMarkerPaser = FreeMarkerPaser.getInstance();
/* 44 */     List lvlist = this.memberLvManager.list();
/* 45 */     freeMarkerPaser.putData("memberid", member.getMember_id());
/* 46 */     freeMarkerPaser.putData("lvlist", lvlist);
/* 47 */     freeMarkerPaser.setPageName("editor");
/*    */ 
/* 49 */     return freeMarkerPaser.proessPageContent();
/*    */   }
/*    */ 
/*    */   public String getTabName(Member member)
/*    */   {
/* 57 */     return "编辑会员";
/*    */   }
/*    */ 
/*    */   public int getOrder()
/*    */   {
/* 63 */     return 3;
/*    */   }
/*    */ 
/*    */   public boolean canBeExecute(Member member)
/*    */   {
/* 69 */     return true;
/*    */   }
/*    */ 
/*    */   public IMemberManager getMemberManager() {
/* 73 */     return this.memberManager;
/*    */   }
/*    */ 
/*    */   public void setMemberManager(IMemberManager memberManager) {
/* 77 */     this.memberManager = memberManager;
/*    */   }
/*    */ 
/*    */   public IMemberLvManager getMemberLvManager() {
/* 81 */     return this.memberLvManager;
/*    */   }
/*    */ 
/*    */   public void setMemberLvManager(IMemberLvManager memberLvManager) {
/* 85 */     this.memberLvManager = memberLvManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.member.plugin.edit.MemberEditorPlugin
 * JD-Core Version:    0.6.0
 */