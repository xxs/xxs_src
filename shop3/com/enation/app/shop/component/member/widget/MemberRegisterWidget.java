/*     */ package com.enation.app.shop.component.member.widget;
/*     */ 
/*     */ import com.enation.app.base.core.model.Member;
/*     */ import com.enation.app.base.core.service.IRegionsManager;
/*     */ import com.enation.app.shop.core.service.IMemberManager;
/*     */ import com.enation.eop.resource.model.EopSite;
/*     */ import com.enation.eop.sdk.context.EopContext;
/*     */ import com.enation.eop.sdk.user.IUserService;
/*     */ import com.enation.eop.sdk.user.UserServiceFactory;
/*     */ import com.enation.eop.sdk.widget.AbstractMemberWidget;
/*     */ import com.enation.framework.context.webcontext.ThreadContextHolder;
/*     */ import com.enation.framework.context.webcontext.WebSessionContext;
/*     */ import com.enation.framework.util.DateUtil;
/*     */ import com.enation.framework.util.StringUtil;
/*     */ import java.util.Date;
/*     */ import java.util.Map;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.springframework.context.annotation.Scope;
/*     */ import org.springframework.stereotype.Component;
/*     */ 
/*     */ @Component("member_register")
/*     */ @Scope("prototype")
/*     */ public class MemberRegisterWidget extends AbstractMemberWidget
/*     */ {
/*     */   private HttpServletRequest request;
/*     */   private IMemberManager memberManager;
/*     */   private IRegionsManager regionsManager;
/*     */ 
/*     */   protected void config(Map<String, String> params)
/*     */   {
/*     */   }
/*     */ 
/*     */   protected void display(Map<String, String> params)
/*     */   {
/*  45 */     EopSite site = EopContext.getContext().getCurrentSite();
/*  46 */     putData("pagetitle", "用户注册 -" + site.getSitename());
/*  47 */     putData("keywords", "用户注册," + site.getSitename());
/*  48 */     putData("description", "欢迎注册" + site.getSitename() + "会员！");
/*     */ 
/*  50 */     this.request = ThreadContextHolder.getHttpRequest();
/*  51 */     String action = this.request.getParameter("action");
/*  52 */     showMenu(false);
/*  53 */     if ("register".equals(action)) {
/*  54 */       register();
/*  55 */     } else if ("supply".equals(action)) {
/*  56 */       supply();
/*  57 */     } else if ("checkname".equals(action)) {
/*  58 */       checkname();
/*  59 */     } else if ("checkemail".equals(action)) {
/*  60 */       checkemail();
/*     */     } else {
/*  62 */       Member member = UserServiceFactory.getUserService().getCurrentMember();
/*  63 */       if (member != null) {
/*  64 */         showSuccess("您已经登录,请退出后再注册", "会员中心首页", "member_index.html");
/*  65 */         return;
/*     */       }
/*  67 */       String forward = this.request.getParameter("forward");
/*  68 */       String friendid = this.request.getParameter("friendid");
/*  69 */       putData("forward", forward);
/*  70 */       putData("friendid", friendid);
/*  71 */       setPageName("register");
/*     */     }
/*     */   }
/*     */ 
/*     */   private void register()
/*     */   {
/*  78 */     String license = this.request.getParameter("license");
/*  79 */     String validcode = this.request.getParameter("validcode");
/*  80 */     if (validcode(validcode) == 0) {
/*  81 */       showError("验证码输入错误!");
/*  82 */       return;
/*     */     }
/*  84 */     if (!"agree".equals(license)) {
/*  85 */       showError("同意注册协议才可以注册!");
/*  86 */       return;
/*     */     }
/*     */ 
/*  89 */     Member member = new Member();
/*  90 */     String username = this.request.getParameter("username");
/*  91 */     String password = this.request.getParameter("password");
/*  92 */     String email = this.request.getParameter("email");
/*  93 */     String friendid = this.request.getParameter("friendid");
/*  94 */     String registerip = this.request.getRemoteAddr();
/*     */ 
/*  96 */     if (StringUtil.isEmpty(username)) {
/*  97 */       showError("用户名不能为空！");
/*  98 */       return;
/*     */     }
/* 100 */     if ((username.length() < 4) || (username.length() > 20)) {
/* 101 */       showError("用户名的长度为4-20个字符！");
/* 102 */       return;
/*     */     }
/* 104 */     if (username.contains("@")) {
/* 105 */       showError("用户名中不能包含@等特殊字符！");
/* 106 */       return;
/*     */     }
/* 108 */     if (StringUtil.isEmpty(email)) {
/* 109 */       showError("注册邮箱不能为空！");
/* 110 */       return;
/*     */     }
/* 112 */     if (!StringUtil.validEmail(email)) {
/* 113 */       showError("注册邮箱格式不正确！");
/* 114 */       return;
/*     */     }
/* 116 */     if (StringUtil.isEmpty(password)) {
/* 117 */       showError("密码不能为空！");
/* 118 */       return;
/*     */     }
/* 120 */     if (this.memberManager.checkname(username) > 0) {
/* 121 */       showError("此用户名已经存在，请您选择另外的用户名!");
/* 122 */       return;
/*     */     }
/* 124 */     if (this.memberManager.checkemail(email) > 0) {
/* 125 */       showError("此邮箱已经注册过，请您选择另外的邮箱!");
/* 126 */       return;
/*     */     }
/*     */ 
/* 129 */     member.setMobile("");
/* 130 */     member.setUname(username);
/* 131 */     member.setPassword(password);
/* 132 */     member.setEmail(email);
/* 133 */     member.setRegisterip(registerip);
/* 134 */     if (!StringUtil.isEmpty(friendid)) {
/* 135 */       Member parentMember = this.memberManager.get(Integer.valueOf(Integer.parseInt(friendid)));
/* 136 */       if (parentMember != null) {
/* 137 */         member.setParentid(parentMember.getMember_id());
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 142 */       String reg_Recomm = this.request.getParameter("reg_Recomm");
/* 143 */       if ((!StringUtil.isEmpty(reg_Recomm)) && (reg_Recomm.trim().equals(email.trim()))) {
/* 144 */         showError("推荐人的邮箱请不要填写自己的邮箱!");
/* 145 */         return;
/*     */       }
/* 147 */       if ((!StringUtil.isEmpty(reg_Recomm)) && (StringUtil.validEmail(reg_Recomm))) {
/* 148 */         Member parentMember = this.memberManager.getMemberByEmail(reg_Recomm);
/* 149 */         if (parentMember == null) {
/* 150 */           showError("您填写的推荐人不存在!");
/* 151 */           return;
/*     */         }
/* 153 */         member.setParentid(parentMember.getMember_id());
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 158 */     int result = this.memberManager.register(member);
/* 159 */     if (result == 1) {
/* 160 */       this.memberManager.login(username, password);
/* 161 */       String forward = this.request.getParameter("forward");
/*     */ 
/* 163 */       setActionPageName("register_success");
/* 164 */       putData("member", member);
/* 165 */       putData("mailurl", "http://mail." + org.apache.commons.lang.StringUtils.split(member.getEmail(), "@")[1]);
/* 166 */       if ((forward != null) && (!forward.equals(""))) {
/* 167 */         String message = this.request.getParameter("message");
/* 168 */         setMsg(message);
/*     */       } else {
/* 170 */         setMsg("注册成功");
/*     */       }
/*     */     } else {
/* 173 */       showError("用户名[" + member.getUname() + "]已存在!");
/*     */     }
/*     */   }
/*     */ 
/*     */   private void checkname()
/*     */   {
/* 182 */     String username = this.request.getParameter("username");
/* 183 */     int result = this.memberManager.checkname(username);
/* 184 */     String json = "{result:" + result + "}";
/* 185 */     showJson(json);
/*     */   }
/*     */ 
/*     */   private void checkemail()
/*     */   {
/* 192 */     String email = this.request.getParameter("email");
/* 193 */     int result = this.memberManager.checkemail(email);
/* 194 */     String json = "{result:" + result + "}";
/* 195 */     showJson(json);
/*     */   }
/*     */ 
/*     */   private void supply()
/*     */   {
/* 201 */     String memberid = this.request.getParameter("memberid");
/* 202 */     String name = this.request.getParameter("name");
/* 203 */     String sex = this.request.getParameter("sex");
/* 204 */     String birthday = this.request.getParameter("birthday");
/* 205 */     String address = this.request.getParameter("address");
/* 206 */     String zip = this.request.getParameter("zip");
/* 207 */     String mobile = this.request.getParameter("mobile");
/* 208 */     String tel = this.request.getParameter("tel");
/* 209 */     String pw_question = this.request.getParameter("pw_question");
/* 210 */     String pw_answer = this.request.getParameter("pw_answer");
/*     */ 
/* 213 */     String province_id = this.request.getParameter("province_id");
/* 214 */     String province = this.request.getParameter("province");
/* 215 */     String city_id = this.request.getParameter("city_id");
/* 216 */     String city = this.request.getParameter("city");
/* 217 */     String region_id = this.request.getParameter("region_id");
/* 218 */     String region = this.request.getParameter("region");
/*     */ 
/* 220 */     Member member = this.memberManager.get(Integer.valueOf(memberid));
/* 221 */     member.setName(name);
/* 222 */     member.setSex(Integer.valueOf(sex));
/* 223 */     member.setBirthday(Long.valueOf(DateUtil.toDate(birthday, "yyyy-MM-dd").getTime()));
/* 224 */     member.setAddress(address);
/* 225 */     member.setZip(zip);
/* 226 */     member.setMobile(mobile);
/* 227 */     member.setTel(tel);
/* 228 */     member.setPw_question(pw_question);
/* 229 */     member.setPw_answer(pw_answer);
/*     */ 
/* 231 */     if (!StringUtil.isEmpty(province_id))
/* 232 */       member.setProvince_id(Integer.valueOf(province_id));
/* 233 */     member.setProvince(province);
/*     */ 
/* 235 */     if (!StringUtil.isEmpty(city_id))
/* 236 */       member.setCity_id(Integer.valueOf(city_id));
/* 237 */     member.setCity(city);
/*     */ 
/* 239 */     if (!StringUtil.isEmpty(region_id))
/* 240 */       member.setRegion_id(Integer.valueOf(region_id));
/* 241 */     member.setRegion(region);
/* 242 */     this.memberManager.edit(member);
/* 243 */     ThreadContextHolder.getSessionContext().setAttribute("curr_member", member);
/* 244 */     showSuccess("资料更新成功", "会员中心首页", "member_index.html");
/*     */   }
/*     */ 
/*     */   private int validcode(String validcode)
/*     */   {
/* 255 */     if (validcode == null) {
/* 256 */       return 0;
/*     */     }
/*     */ 
/* 259 */     String code = (String)ThreadContextHolder.getSessionContext().getAttribute("valid_codememberreg");
/* 260 */     if (code == null) {
/* 261 */       return 0;
/*     */     }
/* 263 */     if (!code.equals(validcode)) {
/* 264 */       return 0;
/*     */     }
/*     */ 
/* 268 */     return 1;
/*     */   }
/*     */ 
/*     */   public IMemberManager getMemberManager() {
/* 272 */     return this.memberManager;
/*     */   }
/*     */ 
/*     */   public void setMemberManager(IMemberManager memberManager) {
/* 276 */     this.memberManager = memberManager;
/*     */   }
/*     */ 
/*     */   public IRegionsManager getRegionsManager() {
/* 280 */     return this.regionsManager;
/*     */   }
/*     */ 
/*     */   public void setRegionsManager(IRegionsManager regionsManager) {
/* 284 */     this.regionsManager = regionsManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.member.widget.MemberRegisterWidget
 * JD-Core Version:    0.6.0
 */