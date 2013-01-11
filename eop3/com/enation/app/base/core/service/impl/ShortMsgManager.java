/*    */ package com.enation.app.base.core.service.impl;
/*    */ 
/*    */ import com.enation.app.base.core.model.ShortMsg;
/*    */ import com.enation.app.base.core.plugin.shortmsg.ShortMsgPluginBundle;
/*    */ import com.enation.app.base.core.service.IShortMsgManager;
/*    */ import com.enation.eop.sdk.database.BaseSupport;
/*    */ import java.util.List;
/*    */ 
/*    */ public class ShortMsgManager extends BaseSupport<ShortMsg>
/*    */   implements IShortMsgManager
/*    */ {
/*    */   private ShortMsgPluginBundle shortMsgPluginBundle;
/*    */ 
/*    */   public List<ShortMsg> listNotReadMessage()
/*    */   {
/* 25 */     return this.shortMsgPluginBundle.getMessageList();
/*    */   }
/*    */ 
/*    */   public ShortMsgPluginBundle getShortMsgPluginBundle() {
/* 29 */     return this.shortMsgPluginBundle;
/*    */   }
/*    */ 
/*    */   public void setShortMsgPluginBundle(ShortMsgPluginBundle shortMsgPluginBundle) {
/* 33 */     this.shortMsgPluginBundle = shortMsgPluginBundle;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.app.base.core.service.impl.ShortMsgManager
 * JD-Core Version:    0.6.0
 */