/*    */ package com.enation.app.shop.component.member.plugin.comments;
/*    */ 
/*    */ import com.enation.app.base.core.model.Member;
/*    */ import com.enation.app.shop.core.plugin.member.IMemberTabShowEvent;
/*    */ import com.enation.app.shop.core.service.IMemberCommentManager;
/*    */ import com.enation.eop.processor.core.freemarker.FreeMarkerPaser;
/*    */ import com.enation.framework.database.Page;
/*    */ import com.enation.framework.plugin.AutoRegisterPlugin;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import org.springframework.stereotype.Component;
/*    */ 
/*    */ @Component
/*    */ public class MemberAskPlugin extends AutoRegisterPlugin
/*    */   implements IMemberTabShowEvent
/*    */ {
/*    */   private IMemberCommentManager memberCommentManager;
/*    */ 
/*    */   public boolean canBeExecute(Member member)
/*    */   {
/* 28 */     return true;
/*    */   }
/*    */ 
/*    */   public int getOrder()
/*    */   {
/* 33 */     return 17;
/*    */   }
/*    */ 
/*    */   public String getTabName(Member member)
/*    */   {
/* 38 */     return "他的咨询";
/*    */   }
/*    */ 
/*    */   public String onShowMemberDetailHtml(Member member)
/*    */   {
/* 43 */     Page page = this.memberCommentManager.getMemberComments(1, 100, 2, member.getMember_id().intValue());
/* 44 */     List listComments = new ArrayList();
/* 45 */     if (page != null) {
/* 46 */       listComments = (List)page.getResult();
/*    */     }
/* 48 */     FreeMarkerPaser freeMarkerPaser = FreeMarkerPaser.getInstance();
/* 49 */     freeMarkerPaser.setClz(getClass());
/* 50 */     freeMarkerPaser.putData("listComments", listComments);
/* 51 */     freeMarkerPaser.setPageName("comments");
/* 52 */     return freeMarkerPaser.proessPageContent();
/*    */   }
/*    */ 
/*    */   public IMemberCommentManager getMemberCommentManager() {
/* 56 */     return this.memberCommentManager;
/*    */   }
/*    */ 
/*    */   public void setMemberCommentManager(IMemberCommentManager memberCommentManager) {
/* 60 */     this.memberCommentManager = memberCommentManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.member.plugin.comments.MemberAskPlugin
 * JD-Core Version:    0.6.0
 */