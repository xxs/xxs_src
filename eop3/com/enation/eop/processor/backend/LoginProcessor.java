/*     */ package com.enation.eop.processor.backend;
/*     */ 
/*     */ import com.enation.app.base.core.service.auth.IAdminUserManager;
/*     */ import com.enation.eop.processor.Processor;
/*     */ import com.enation.eop.processor.core.EopException;
/*     */ import com.enation.eop.processor.core.Response;
/*     */ import com.enation.eop.processor.core.StringResponse;
/*     */ import com.enation.framework.context.spring.SpringContextHolder;
/*     */ import com.enation.framework.context.webcontext.ThreadContextHolder;
/*     */ import com.enation.framework.context.webcontext.WebSessionContext;
/*     */ import com.enation.framework.util.EncryptionUtil;
/*     */ import com.enation.framework.util.HttpUtil;
/*     */ import com.enation.framework.util.StringUtil;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class LoginProcessor
/*     */   implements Processor
/*     */ {
/*  34 */   protected final Logger logger = Logger.getLogger(getClass());
/*     */ 
/*     */   public Response process(int mode, HttpServletResponse httpResponse, HttpServletRequest httpRequest)
/*     */   {
/*  44 */     String type = httpRequest.getParameter("type");
/*  45 */     if ((type == null) || ("".equals(type))) {
/*  46 */       return userLogin(httpResponse, httpRequest);
/*     */     }
/*  48 */     return sysLogin(httpResponse, httpRequest);
/*     */   }
/*     */ 
/*     */   public Response sysLogin(HttpServletResponse httpResponse, HttpServletRequest httpRequest)
/*     */   {
/*  63 */     Response response = new StringResponse();
/*     */ 
/*  65 */     String endata = httpRequest.getParameter("s");
/*  66 */     if (endata == null) { response.setContent("非法数据"); return response;
/*     */     }
/*  68 */     endata = EncryptionUtil.authCode(endata, "DECODE");
/*  69 */     String[] ar = StringUtils.split(endata, ",");
/*     */ 
/*  72 */     String username = ar[0];
/*  73 */     String password = ar[1];
/*  74 */     Long dateline = Long.valueOf(ar[2]);
/*     */     try
/*     */     {
/*  85 */       IAdminUserManager userManager = (IAdminUserManager)SpringContextHolder.getBean("adminUserManager");
/*  86 */       int result = userManager.loginBySys(username, password);
/*  87 */       if (result == 1)
/*  88 */         response.setContent("<script>location.href='main.jsp'</script>正在转向后台...");
/*     */       else {
/*  90 */         response.setContent("{'result':1,'message':'登录失败：用户名或密码错误'}");
/*     */       }
/*  92 */       return response;
/*     */     }
/*     */     catch (EopException exception)
/*     */     {
/*  96 */       response.setContent("{'result':1,'message':'" + exception.getMessage() + "'}");
/*  97 */     }return response;
/*     */   }
/*     */ 
/*     */   public Response userLogin(HttpServletResponse httpResponse, HttpServletRequest httpRequest)
/*     */   {
/* 110 */     String username = httpRequest.getParameter("username");
/* 111 */     String password = httpRequest.getParameter("password");
/* 112 */     String valid_code = httpRequest.getParameter("valid_code");
/*     */     Response response;
/*     */     try {
/* 119 */       if (valid_code == null) throw new EopException("验证码输入错误");
/* 120 */       WebSessionContext sessonContext = ThreadContextHolder.getSessionContext();
/* 121 */       Object realCode = sessonContext.getAttribute("valid_codeadmin");
/*     */ 
/* 123 */       if (!valid_code.equals(realCode)) {
/* 124 */         throw new EopException("验证码输入错误");
/*     */       }
/*     */ 
/* 130 */       IAdminUserManager userManager = (IAdminUserManager)SpringContextHolder.getBean("adminUserManager");
/* 131 */       userManager.login(username, password);
/*     */ 
/* 133 */       StringBuffer json = new StringBuffer();
/* 134 */       json.append("{'result':0}");
/*     */ 
/* 137 */       Response response = new StringResponse();
/* 138 */       response.setContent(json.toString());
/*     */ 
/* 140 */       String remember_login_name = httpRequest.getParameter("remember_login_name");
/* 141 */       if (!StringUtil.isEmpty(remember_login_name))
/* 142 */         HttpUtil.addCookie(httpResponse, "loginname", username, 31536000);
/*     */       else {
/* 144 */         HttpUtil.addCookie(httpResponse, "loginname", "", 0);
/*     */       }
/*     */ 
/* 148 */       return response;
/*     */     }
/*     */     catch (RuntimeException exception) {
/* 151 */       exception.printStackTrace();
/* 152 */       this.logger.error(exception.getMessage(), exception.fillInStackTrace());
/* 153 */       response = new StringResponse();
/* 154 */       response.setContent("{'result':1,'message':'" + exception.getMessage() + "'}");
/* 155 */     }return response;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.eop.processor.backend.LoginProcessor
 * JD-Core Version:    0.6.0
 */