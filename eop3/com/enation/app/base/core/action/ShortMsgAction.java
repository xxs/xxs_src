/*    */ package com.enation.app.base.core.action;
/*    */ 
/*    */ import com.enation.app.base.core.model.ShortMsg;
/*    */ import com.enation.app.base.core.service.IShortMsgManager;
/*    */ import com.enation.framework.action.WWAction;
/*    */ import java.util.List;
/*    */ import net.sf.json.JSONArray;
/*    */ 
/*    */ public class ShortMsgAction extends WWAction
/*    */ {
/*    */   private IShortMsgManager shortMsgManager;
/*    */   private List<ShortMsg> msgList;
/*    */ 
/*    */   public String listNew()
/*    */   {
/* 27 */     this.msgList = this.shortMsgManager.listNotReadMessage();
/* 28 */     this.json = JSONArray.fromObject(JSONArray.fromObject(this.msgList)).toString();
/* 29 */     return "json_message";
/*    */   }
/*    */ 
/*    */   public IShortMsgManager getShortMsgManager()
/*    */   {
/* 34 */     return this.shortMsgManager;
/*    */   }
/*    */ 
/*    */   public void setShortMsgManager(IShortMsgManager shortMsgManager)
/*    */   {
/* 41 */     this.shortMsgManager = shortMsgManager;
/*    */   }
/*    */ 
/*    */   public List<ShortMsg> getMsgList()
/*    */   {
/* 48 */     return this.msgList;
/*    */   }
/*    */ 
/*    */   public void setMsgList(List<ShortMsg> msgList)
/*    */   {
/* 55 */     this.msgList = msgList;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.app.base.core.action.ShortMsgAction
 * JD-Core Version:    0.6.0
 */