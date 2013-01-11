/*     */ package com.enation.app.shop.component.point.plugin;
/*     */ 
/*     */ import com.enation.app.base.core.model.Member;
/*     */ import com.enation.app.shop.core.plugin.member.IMemberEmailCheckEvent;
/*     */ import com.enation.app.shop.core.plugin.member.IMemberLoginEvent;
/*     */ import com.enation.app.shop.core.plugin.member.IMemberRegisterEvent;
/*     */ import com.enation.app.shop.core.service.IMemberPointManger;
/*     */ import com.enation.framework.database.IDaoSupport;
/*     */ import com.enation.framework.plugin.AutoRegisterPlugin;
/*     */ import com.enation.framework.util.DateUtil;
/*     */ import java.util.Date;
/*     */ import org.springframework.stereotype.Component;
/*     */ 
/*     */ @Component
/*     */ public class MemberPointPlugin extends AutoRegisterPlugin
/*     */   implements IMemberRegisterEvent, IMemberLoginEvent, IMemberEmailCheckEvent
/*     */ {
/*     */   private IDaoSupport baseDaoSupport;
/*     */   private IMemberPointManger memberPointManger;
/*     */ 
/*     */   public void onRegister(Member member)
/*     */   {
/*  33 */     int memberid = member.getMember_id().intValue();
/*     */ 
/*  39 */     if (this.memberPointManger.checkIsOpen("register")) {
/*  40 */       int point = this.memberPointManger.getItemPoint("register_num");
/*  41 */       int mp = this.memberPointManger.getItemPoint("register_num_mp");
/*  42 */       this.memberPointManger.add(memberid, point, "成功注册", null, mp);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void onEmailCheck(Member member)
/*     */   {
/*  55 */     if (this.memberPointManger.checkIsOpen("email_check")) {
/*  56 */       int point = this.memberPointManger.getItemPoint("email_check_num");
/*  57 */       int mp = this.memberPointManger.getItemPoint("email_check_num_mp");
/*  58 */       this.memberPointManger.add(member.getMember_id().intValue(), point, "完成邮箱验证", null, mp);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void onLogin(Member member, Long upLogintime)
/*     */   {
/*  66 */     if ((upLogintime == null) || (upLogintime.longValue() == 0L))
/*  67 */       upLogintime = member.getLastlogin();
/*  68 */     long ldate = upLogintime.longValue() * 1000L;
/*  69 */     Date date = new Date(ldate);
/*  70 */     Date today = new Date();
/*     */ 
/*  77 */     if ((!DateUtil.toString(date, "yyyy-MM-dd").equals(DateUtil.toString(today, "yyyy-MM-dd"))) && 
/*  78 */       (this.memberPointManger.checkIsOpen("login"))) {
/*  79 */       int point = this.memberPointManger.getItemPoint("login_num");
/*  80 */       int mp = this.memberPointManger.getItemPoint("login_num_mp");
/*  81 */       this.memberPointManger.add(member.getMember_id().intValue(), point, DateUtil.toString(today, "yyyy年MM月dd日") + "登录", null, mp);
/*     */     }
/*     */   }
/*     */ 
/*     */   private boolean isRepeatedIp(String ip, int parentid)
/*     */   {
/*  90 */     String sql = "select count(0) from member where parentid=? and registerip=?";
/*  91 */     int count = this.baseDaoSupport.queryForInt(sql, new Object[] { Integer.valueOf(parentid), ip });
/*  92 */     return count > 1;
/*     */   }
/*     */ 
/*     */   public IMemberPointManger getMemberPointManger()
/*     */   {
/*  99 */     return this.memberPointManger;
/*     */   }
/*     */ 
/*     */   public void setMemberPointManger(IMemberPointManger memberPointManger)
/*     */   {
/* 104 */     this.memberPointManger = memberPointManger;
/*     */   }
/*     */ 
/*     */   public IDaoSupport getBaseDaoSupport()
/*     */   {
/* 109 */     return this.baseDaoSupport;
/*     */   }
/*     */ 
/*     */   public void setBaseDaoSupport(IDaoSupport baseDaoSupport)
/*     */   {
/* 114 */     this.baseDaoSupport = baseDaoSupport;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.point.plugin.MemberPointPlugin
 * JD-Core Version:    0.6.0
 */