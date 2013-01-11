/*     */ package com.enation.app.shop.component.member.widget;
/*     */ 
/*     */ import com.enation.app.base.core.model.Member;
/*     */ import com.enation.app.shop.core.service.IMemberManager;
/*     */ import com.enation.eop.sdk.widget.AbstractWidget;
/*     */ import com.enation.framework.context.webcontext.ThreadContextHolder;
/*     */ import com.enation.framework.util.DateUtil;
/*     */ import com.enation.framework.util.EncryptionUtil1;
/*     */ import com.enation.framework.util.StringUtil;
/*     */ import java.util.Map;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.springframework.context.annotation.Scope;
/*     */ import org.springframework.stereotype.Component;
/*     */ 
/*     */ @Component("find_modify_password")
/*     */ @Scope("prototype")
/*     */ public class ModifyPasswordWidget extends AbstractWidget
/*     */ {
/*     */   private IMemberManager memberManager;
/*     */ 
/*     */   protected void display(Map<String, String> params)
/*     */   {
/*  33 */     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
/*     */ 
/*  35 */     String s = request.getParameter("s");
/*     */ 
/*  37 */     if (s == null) {
/*  38 */       showError("非法链接地址,请重新找回", "忘记密码", "findpassword.html");
/*  39 */       return;
/*     */     }
/*     */ 
/*  42 */     if ("modify".equals(this.action)) {
/*  43 */       modify(s);
/*  44 */       return;
/*     */     }
/*  46 */     String str = EncryptionUtil1.authcode(s, "DECODE", "", 0);
/*  47 */     String[] array = StringUtils.split(str, ",");
/*  48 */     if (array.length != 2) {
/*  49 */       showError("验证字串不正确,请重新找回", "忘记密码", "findpassword.html");
/*  50 */       return;
/*     */     }
/*  52 */     int memberid = Integer.valueOf(array[0]).intValue();
/*  53 */     long regtime = Long.valueOf(array[1]).longValue();
/*     */ 
/*  55 */     Member member = this.memberManager.get(Integer.valueOf(memberid));
/*  56 */     if ((member == null) || (member.getRegtime().longValue() != regtime)) {
/*  57 */       showError("验证字串不正确,请重新找回", "忘记密码", "findpassword.html");
/*  58 */       return;
/*     */     }
/*  60 */     if ((member.getFind_code() == null) || ("".equals(member.getFind_code())) || (member.getFind_code().length() != 10)) {
/*  61 */       showError("地址已经过期,请重新找回", "忘记密码", "findpassword.html");
/*  62 */       return;
/*     */     }
/*  64 */     int time = Integer.parseInt(member.getFind_code()) + 3600;
/*  65 */     if (DateUtil.getDateline() > time) {
/*  66 */       showError("地址已经过期,请重新找回", "忘记密码", "findpassword.html");
/*  67 */       return;
/*     */     }
/*  69 */     putData("s", s);
/*     */   }
/*     */ 
/*     */   protected void config(Map<String, String> params)
/*     */   {
/*     */   }
/*     */ 
/*     */   private void modify(String code)
/*     */   {
/*  80 */     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
/*  81 */     String passwd = request.getParameter("passwd");
/*  82 */     String conpasswd = request.getParameter("conpasswd");
/*  83 */     if (StringUtil.isEmpty(passwd)) {
/*  84 */       showError("新密码不能为空");
/*  85 */       return;
/*     */     }
/*  87 */     if (StringUtil.isEmpty(conpasswd)) {
/*  88 */       showError("确认新密码不能为空");
/*  89 */       return;
/*     */     }
/*  91 */     if (!passwd.equals(conpasswd)) {
/*  92 */       showError("两次密码输入不一致");
/*  93 */       return;
/*     */     }
/*     */ 
/*  96 */     String str = EncryptionUtil1.authcode(code, "DECODE", "", 0);
/*  97 */     String[] array = StringUtils.split(str, ",");
/*  98 */     if (array.length != 2) {
/*  99 */       showError("验证字串不正确,请重新找回", "忘记密码", "findpassword.html");
/* 100 */       return;
/*     */     }
/* 102 */     int memberid = Integer.valueOf(array[0]).intValue();
/* 103 */     long regtime = Long.valueOf(array[1]).longValue();
/*     */ 
/* 105 */     Member member = this.memberManager.get(Integer.valueOf(memberid));
/* 106 */     if ((member == null) || (member.getRegtime().longValue() != regtime)) {
/* 107 */       showError("验证字串不正确,请重新找回", "忘记密码", "findpassword.html");
/* 108 */       return;
/*     */     }
/* 110 */     if ((member.getFind_code() == null) || ("".equals(member.getFind_code())) || (member.getFind_code().length() != 10)) {
/* 111 */       showError("地址已经过期,请重新找回", "忘记密码", "findpassword.html");
/* 112 */       return;
/*     */     }
/* 114 */     int time = Integer.parseInt(member.getFind_code()) + 3600;
/* 115 */     if (DateUtil.getDateline() > time) {
/* 116 */       showError("地址已经过期,请重新找回", "忘记密码", "findpassword.html");
/* 117 */       return;
/*     */     }
/* 119 */     this.memberManager.updatePassword(Integer.valueOf(memberid), passwd);
/* 120 */     this.memberManager.updateFindCode(Integer.valueOf(memberid), "");
/* 121 */     showSuccess("修改密码成功", "登录页面", "login.html");
/*     */   }
/*     */ 
/*     */   public IMemberManager getMemberManager()
/*     */   {
/* 126 */     return this.memberManager;
/*     */   }
/*     */ 
/*     */   public void setMemberManager(IMemberManager memberManager) {
/* 130 */     this.memberManager = memberManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.member.widget.ModifyPasswordWidget
 * JD-Core Version:    0.6.0
 */