/*    */ package com.enation.app.base.core.action;
/*    */ 
/*    */ import com.enation.app.base.core.model.Help;
/*    */ import com.enation.app.base.core.service.IHelpManager;
/*    */ import com.enation.framework.action.WWAction;
/*    */ import com.enation.framework.database.ObjectNotFoundException;
/*    */ 
/*    */ public class HelpAction extends WWAction
/*    */ {
/*    */   private IHelpManager helpManager;
/*    */   private String helpid;
/*    */   private Help help;
/*    */ 
/*    */   public String execute()
/*    */   {
/*    */     try
/*    */     {
/* 22 */       this.help = this.helpManager.get(this.helpid);
/* 23 */       if (this.help == null) {
/* 24 */         this.help = new Help();
/* 25 */         this.help.setContent("此帮助未定义");
/*    */       }
/*    */     } catch (ObjectNotFoundException e) {
/* 28 */       this.help = new Help();
/* 29 */       this.help.setContent("此帮助未定义");
/*    */     }
/* 31 */     return "content";
/*    */   }
/*    */ 
/*    */   public String getHelpid()
/*    */   {
/* 36 */     return this.helpid;
/*    */   }
/*    */   public void setHelpid(String helpid) {
/* 39 */     this.helpid = helpid;
/*    */   }
/*    */   public IHelpManager getHelpManager() {
/* 42 */     return this.helpManager;
/*    */   }
/*    */   public void setHelpManager(IHelpManager helpManager) {
/* 45 */     this.helpManager = helpManager;
/*    */   }
/*    */   public Help getHelp() {
/* 48 */     return this.help;
/*    */   }
/*    */   public void setHelp(Help help) {
/* 51 */     this.help = help;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.app.base.core.action.HelpAction
 * JD-Core Version:    0.6.0
 */