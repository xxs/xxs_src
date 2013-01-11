/*    */ package com.enation.app.shop.component.member.plugin.base;
/*    */ 
/*    */ import com.enation.app.base.core.model.Member;
/*    */ import com.enation.app.shop.core.plugin.member.IMemberTabShowEvent;
/*    */ import com.enation.eop.processor.core.freemarker.FreeMarkerPaser;
/*    */ import com.enation.framework.plugin.AutoRegisterPlugin;
/*    */ import org.springframework.stereotype.Component;
/*    */ 
/*    */ @Component
/*    */ public class MemberDetailBasePlugin extends AutoRegisterPlugin
/*    */   implements IMemberTabShowEvent
/*    */ {
/*    */   public String onShowMemberDetailHtml(Member member)
/*    */   {
/* 21 */     FreeMarkerPaser freeMarkerPaser = FreeMarkerPaser.getInstance();
/* 22 */     freeMarkerPaser.putData("member", member);
/* 23 */     freeMarkerPaser.setPageName("base");
/* 24 */     return freeMarkerPaser.proessPageContent();
/*    */   }
/*    */ 
/*    */   public String getTabName(Member member)
/*    */   {
/* 31 */     return "基本信息";
/*    */   }
/*    */ 
/*    */   public int getOrder()
/*    */   {
/* 37 */     return 1;
/*    */   }
/*    */ 
/*    */   public boolean canBeExecute(Member member)
/*    */   {
/* 43 */     return true;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.member.plugin.base.MemberDetailBasePlugin
 * JD-Core Version:    0.6.0
 */