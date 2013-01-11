/*     */ package com.enation.app.shop.component.member.widget;
/*     */ 
/*     */ import com.enation.app.base.core.model.Member;
/*     */ import com.enation.app.base.core.model.MemberLv;
/*     */ import com.enation.app.shop.core.service.IMemberLvManager;
/*     */ import com.enation.app.shop.core.service.IMemberManager;
/*     */ import com.enation.app.shop.core.service.IMemberPointManger;
/*     */ import com.enation.app.shop.core.service.IOrderManager;
/*     */ import com.enation.eop.processor.core.freemarker.FreeMarkerPaser;
/*     */ import com.enation.eop.resource.model.EopSite;
/*     */ import com.enation.eop.sdk.context.EopContext;
/*     */ import com.enation.eop.sdk.user.IUserService;
/*     */ import com.enation.eop.sdk.user.UserServiceFactory;
/*     */ import com.enation.eop.sdk.widget.AbstractMemberWidget;
/*     */ import com.enation.eop.sdk.widget.AbstractWidget;
/*     */ import com.enation.framework.context.spring.SpringContextHolder;
/*     */ import com.enation.framework.context.webcontext.ThreadContextHolder;
/*     */ import com.enation.framework.database.Page;
/*     */ import com.enation.framework.util.RequestUtil;
/*     */ import com.enation.framework.util.StringUtil;
/*     */ import com.sun.xml.messaging.saaj.util.ByteOutputStream;
/*     */ import freemarker.template.Configuration;
/*     */ import freemarker.template.DefaultObjectWrapper;
/*     */ import freemarker.template.Template;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.io.Writer;
/*     */ import java.util.HashMap;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpSession;
/*     */ 
/*     */ public class MemberMainWidget extends AbstractWidget
/*     */ {
/*     */   private IMemberLvManager memberLvManager;
/*     */   private IMemberManager memberManager;
/*     */   private IOrderManager orderManager;
/*     */   private IMemberPointManger memberPointManger;
/*     */ 
/*     */   public boolean cacheAble()
/*     */   {
/*  51 */     return false;
/*     */   }
/*     */ 
/*     */   protected void config(Map<String, String> params)
/*     */   {
/*     */   }
/*     */ 
/*     */   public String process(Map<String, String> params)
/*     */   {
/*  60 */     String servletPath = ThreadContextHolder.getHttpRequest().getServletPath();
/*  61 */     this.freeMarkerPaser = FreeMarkerPaser.getInstance();
/*  62 */     this.freeMarkerPaser.setClz(getClass());
/*  63 */     Member member = UserServiceFactory.getUserService().getCurrentMember();
/*     */ 
/*  66 */     if ((!servletPath.equals("/member_login.html")) && (!servletPath.equals("/member_register.html")) && (!servletPath.equals("/member_logout.html")) && (!servletPath.equals("/member_findpassword.html")) && (!servletPath.equals("/member_modifypassword.html")) && (member == null))
/*     */     {
/*  74 */       String loginpage = (String)params.get("page_login");
/*  75 */       if (!StringUtil.isEmpty(loginpage)) {
/*  76 */         this.freeMarkerPaser.setPageFolder(getThemePath() + "/" + (String)params.get("folder"));
/*  77 */         this.freeMarkerPaser.setPageName((String)params.get("page_login"));
/*     */       } else {
/*  79 */         this.freeMarkerPaser.setPageName("login");
/*     */       }
/*  81 */       return this.freeMarkerPaser.proessPageContent();
/*     */     }
/*     */ 
/*  85 */     this.freeMarkerPaser.setPageFolder(null);
/*  86 */     this.freeMarkerPaser.setPageName(null);
/*     */ 
/*  90 */     servletPath = servletPath.substring(1, servletPath.indexOf(46));
/*     */ 
/*  92 */     if (servletPath.startsWith("member_orderdetail_")) {
/*  93 */       servletPath = "member_orderdetail";
/*     */     }
/*     */ 
/*  96 */     String url = RequestUtil.getRequestUrl(ThreadContextHolder.getHttpRequest());
/*  97 */     url = url.substring(1, url.length());
/*  98 */     putData("menuUrl", url);
/*  99 */     AbstractMemberWidget widget = (AbstractMemberWidget)SpringContextHolder.getBean(servletPath);
/*     */ 
/* 103 */     putData("showMenu", Boolean.valueOf(widget.getShowMenu()));
/*     */ 
/* 111 */     String pagename = servletPath.replaceAll("member_", "");
/* 112 */     if (pagename != null) {
/* 113 */       pagename = (String)params.get("page_" + pagename);
/*     */ 
/* 115 */       if (pagename != null) {
/* 116 */         params.put("custom_page", pagename);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 121 */     if (member != null)
/*     */     {
/* 123 */       putData("freezepoint", Integer.valueOf(this.memberPointManger.getFreezeMpByMemberId(member.getMember_id().intValue())));
/*     */ 
/* 125 */       Page orderPage = this.orderManager.listByStatus(1, 100, 0, member.getMember_id().intValue());
/* 126 */       putData("orderPage", orderPage);
/* 127 */       putData("orderTotal", Integer.valueOf(this.orderManager.getMemberOrderNum(member.getMember_id().intValue())));
/*     */     }
/*     */ 
/* 132 */     String subHtml = widget.process(params);
/*     */ 
/* 135 */     if ("yes".equals(ThreadContextHolder.getHttpRequest().getParameter("ajax"))) {
/* 136 */       return subHtml;
/*     */     }
/*     */ 
/* 139 */     if (!widget.getShowMenu()) return subHtml;
/*     */ 
/* 141 */     execute(subHtml, params);
/* 142 */     if (this.showHtml) {
/* 143 */       this.pageName = ((String)params.get("custom_page"));
/* 144 */       this.folder = ((String)params.get("folder"));
/* 145 */       if ((!this.disableCustomPage) && (!StringUtil.isEmpty(this.pageName))) {
/* 146 */         EopSite site = EopContext.getContext().getCurrentSite();
/* 147 */         String contextPath = EopContext.getContext().getContextPath();
/* 148 */         if (StringUtil.isEmpty(this.folder))
/* 149 */           this.freeMarkerPaser.setPageFolder(contextPath + "/themes/" + site.getThemepath());
/*     */         else {
/* 151 */           this.freeMarkerPaser.setPageFolder(contextPath + "/themes/" + site.getThemepath() + "/" + this.folder);
/*     */         }
/*     */       }
/*     */ 
/* 155 */       return this.freeMarkerPaser.proessPageContent();
/*     */     }
/* 157 */     return "";
/*     */   }
/*     */ 
/*     */   protected void execute(String mainHtml, Map<String, String> params)
/*     */   {
/* 167 */     IUserService userService = UserServiceFactory.getUserService();
/* 168 */     Member member = userService.getCurrentMember();
/* 169 */     if (member != null) {
/* 170 */       putData("member", this.memberManager.get(member.getMember_id()));
/* 171 */       if (member.getLv_id() != null) {
/* 172 */         MemberLv memberLv = this.memberLvManager.get(member.getLv_id());
/*     */ 
/* 174 */         putData("memberLv", memberLv.getName());
/*     */       } else {
/* 176 */         putData("memberLv", "无等级");
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 181 */     this.freeMarkerPaser.setClz(MemberMainWidget.class);
/* 182 */     this.freeMarkerPaser.setWrapPath(false);
/*     */ 
/* 184 */     setPageFolder(null);
/* 185 */     setPageFolder(null);
/* 186 */     setPageName(null);
/* 187 */     setPathPrefix(null);
/*     */ 
/* 189 */     setPageExt(".html");
/* 190 */     String index = (String)params.get("index");
/* 191 */     if (StringUtil.isEmpty(index))
/* 192 */       makeSureSetPageName("main");
/*     */     else {
/* 194 */       makeSureSetPageName("mainindex");
/*     */     }
/*     */ 
/* 210 */     putData("main", mainHtml);
/*     */ 
/* 213 */     int needPayOrderNum = this.orderManager.getMemberOrderNum(member.getMember_id().intValue(), 0);
/* 214 */     putData("needPayOrderNum", Integer.valueOf(needPayOrderNum));
/*     */   }
/*     */ 
/*     */   private String parseMenuHtml(String folder, String menuPageName)
/*     */   {
/*     */     try
/*     */     {
/* 227 */       EopSite site = EopContext.getContext().getCurrentSite();
/* 228 */       Map data = new HashMap();
/* 229 */       Configuration cfg = new Configuration();
/* 230 */       cfg.setObjectWrapper(new DefaultObjectWrapper());
/* 231 */       cfg.setDefaultEncoding("UTF-8");
/* 232 */       cfg.setLocale(Locale.CHINA);
/* 233 */       cfg.setEncoding(Locale.CHINA, "UTF-8");
/* 234 */       String pageFolder = EopContext.getContext().getContextPath() + "/themes/" + site.getThemepath() + "/" + folder;
/*     */ 
/* 236 */       cfg.setServletContextForTemplateLoading(ThreadContextHolder.getHttpRequest().getSession().getServletContext(), pageFolder);
/*     */ 
/* 239 */       Template temp = cfg.getTemplate(menuPageName + ".html");
/* 240 */       ByteOutputStream stream = new ByteOutputStream();
/*     */ 
/* 242 */       Writer out = new OutputStreamWriter(stream);
/* 243 */       temp.process(data, out);
/*     */ 
/* 245 */       out.flush();
/* 246 */       String html = stream.toString();
/* 247 */       return html;
/*     */     } catch (Exception e) {
/* 249 */       e.printStackTrace();
/*     */     }
/*     */ 
/* 252 */     return "";
/*     */   }
/*     */ 
/*     */   private String parseDefaultMenu()
/*     */   {
/*     */     try
/*     */     {
/* 264 */       Map data = new HashMap();
/* 265 */       Configuration cfg = new Configuration();
/* 266 */       cfg.setObjectWrapper(new DefaultObjectWrapper());
/* 267 */       cfg.setDefaultEncoding("UTF-8");
/* 268 */       cfg.setLocale(Locale.CHINA);
/* 269 */       cfg.setEncoding(Locale.CHINA, "UTF-8");
/*     */ 
/* 272 */       cfg.setClassForTemplateLoading(getClass(), "");
/* 273 */       Template temp = cfg.getTemplate("member_menu.html");
/* 274 */       ByteOutputStream stream = new ByteOutputStream();
/*     */ 
/* 276 */       Writer out = new OutputStreamWriter(stream);
/* 277 */       temp.process(data, out);
/*     */ 
/* 279 */       out.flush();
/* 280 */       String html = stream.toString();
/* 281 */       return html;
/*     */     } catch (Exception e) {
/* 283 */       e.printStackTrace();
/*     */     }
/* 285 */     return "";
/*     */   }
/*     */ 
/*     */   public IMemberLvManager getMemberLvManager() {
/* 289 */     return this.memberLvManager;
/*     */   }
/*     */   public void setMemberLvManager(IMemberLvManager memberLvManager) {
/* 292 */     this.memberLvManager = memberLvManager;
/*     */   }
/*     */ 
/*     */   protected void display(Map<String, String> params)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void setOrderManager(IOrderManager orderManager)
/*     */   {
/* 301 */     this.orderManager = orderManager;
/*     */   }
/*     */ 
/*     */   public void setMemberManager(IMemberManager memberManager)
/*     */   {
/* 306 */     this.memberManager = memberManager;
/*     */   }
/*     */ 
/*     */   public void setMemberPointManger(IMemberPointManger memberPointManger)
/*     */   {
/* 311 */     this.memberPointManger = memberPointManger;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.member.widget.MemberMainWidget
 * JD-Core Version:    0.6.0
 */