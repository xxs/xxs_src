/*    */ package com.enation.app.base.core.service.impl;
/*    */ 
/*    */ import com.enation.app.base.core.model.Smtp;
/*    */ import com.enation.app.base.core.service.ISmtpManager;
/*    */ import com.enation.eop.sdk.database.BaseSupport;
/*    */ import com.enation.framework.database.IDaoSupport;
/*    */ import com.enation.framework.util.StringUtil;
/*    */ import java.util.List;
/*    */ 
/*    */ public class SmtpManager extends BaseSupport<Smtp>
/*    */   implements ISmtpManager
/*    */ {
/*    */   public void add(Smtp smtp)
/*    */   {
/* 21 */     this.baseDaoSupport.insert("smtp", smtp);
/*    */   }
/*    */ 
/*    */   public void edit(Smtp smtp)
/*    */   {
/* 26 */     this.baseDaoSupport.update("smtp", smtp, "id=" + smtp.getId());
/*    */   }
/*    */ 
/*    */   public void delete(Integer[] idAr)
/*    */   {
/* 32 */     if ((idAr == null) || (idAr.length == 0)) return;
/* 33 */     String idstr = StringUtil.arrayToString(idAr, ",");
/*    */ 
/* 35 */     this.baseDaoSupport.execute("delete from smtp where id in(" + idstr + ")", new Object[0]);
/*    */   }
/*    */ 
/*    */   public List<Smtp> list()
/*    */   {
/* 42 */     return this.baseDaoSupport.queryForList("select * from smtp", Smtp.class, new Object[0]);
/*    */   }
/*    */ 
/*    */   public void sendOneMail(Smtp currSmtp)
/*    */   {
/* 47 */     this.baseDaoSupport.update("smtp", currSmtp, "id=" + currSmtp.getId());
/*    */   }
/*    */ 
/*    */   public Smtp get(int id)
/*    */   {
/* 53 */     return (Smtp)this.baseDaoSupport.queryForObject("select * from smtp where id=?", Smtp.class, new Object[] { Integer.valueOf(id) });
/*    */   }
/*    */ 
/*    */   public Smtp getCurrentSmtp()
/*    */   {
/* 59 */     return null;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.app.base.core.service.impl.SmtpManager
 * JD-Core Version:    0.6.0
 */