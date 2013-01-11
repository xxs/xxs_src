/*    */ package com.enation.app.shop.core.service.impl;
/*    */ 
/*    */ import com.enation.app.base.core.model.Member;
/*    */ import com.enation.app.base.core.model.Message;
/*    */ import com.enation.app.shop.core.service.IMessageManager;
/*    */ import com.enation.eop.sdk.database.BaseSupport;
/*    */ import com.enation.eop.sdk.user.IUserService;
/*    */ import com.enation.eop.sdk.user.UserServiceFactory;
/*    */ import com.enation.framework.database.IDaoSupport;
/*    */ import com.enation.framework.database.Page;
/*    */ import java.util.List;
/*    */ 
/*    */ public class MessageManager extends BaseSupport<Message>
/*    */   implements IMessageManager
/*    */ {
/*    */   public Page pageMessage(int pageNo, int pageSize, String folder)
/*    */   {
/* 19 */     IUserService userService = UserServiceFactory.getUserService();
/* 20 */     Member member = userService.getCurrentMember();
/* 21 */     String sql = "select * from message where folder = ? ";
/* 22 */     if (folder.equals("inbox"))
/* 23 */       sql = sql + " and to_id = ? and del_status != '1'";
/*    */     else {
/* 25 */       sql = sql + " and from_id = ? and del_status != '2'";
/*    */     }
/* 27 */     sql = sql + " order by date_line desc";
/* 28 */     Page webpage = this.baseDaoSupport.queryForPage(sql, pageNo, pageSize, new Object[] { folder, member.getMember_id() });
/* 29 */     List list = (List)(List)webpage.getResult();
/*    */ 
/* 34 */     return webpage;
/*    */   }
/*    */ 
/*    */   public void addMessage(Message message)
/*    */   {
/* 39 */     this.baseDaoSupport.insert("message", message);
/*    */   }
/*    */ 
/*    */   public void delinbox(String ids)
/*    */   {
/* 45 */     this.baseDaoSupport.execute("delete from message where msg_id in (" + ids + ") and del_status = '2'", new Object[0]);
/* 46 */     this.baseDaoSupport.execute("update message set del_status = '1' where msg_id in (" + ids + ")", new Object[0]);
/*    */   }
/*    */ 
/*    */   public void deloutbox(String ids)
/*    */   {
/* 51 */     this.baseDaoSupport.execute("delete from message where msg_id in (" + ids + ") and del_status = '1'", new Object[0]);
/* 52 */     this.baseDaoSupport.execute("update message set del_status = '2' where msg_id in (" + ids + ")", new Object[0]);
/*    */   }
/*    */ 
/*    */   public void setMessage_read(int msg_id)
/*    */   {
/* 57 */     this.daoSupport.execute("update " + getTableName("message") + " set unread = '1' where msg_id = ?", new Object[] { Integer.valueOf(msg_id) });
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.service.impl.MessageManager
 * JD-Core Version:    0.6.0
 */