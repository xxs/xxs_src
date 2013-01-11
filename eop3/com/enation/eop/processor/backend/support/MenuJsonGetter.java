/*     */ package com.enation.eop.processor.backend.support;
/*     */ 
/*     */ import com.enation.app.base.core.model.AuthAction;
/*     */ import com.enation.app.base.core.service.auth.IAdminUserManager;
/*     */ import com.enation.app.base.core.service.auth.IAuthActionManager;
/*     */ import com.enation.app.base.core.service.auth.IPermissionManager;
/*     */ import com.enation.app.base.core.service.auth.impl.PermissionConfig;
/*     */ import com.enation.eop.processor.AbstractFacadeProcessor;
/*     */ import com.enation.eop.processor.FacadePage;
/*     */ import com.enation.eop.processor.core.Response;
/*     */ import com.enation.eop.processor.core.StringResponse;
/*     */ import com.enation.eop.resource.IMenuManager;
/*     */ import com.enation.eop.resource.model.AdminUser;
/*     */ import com.enation.eop.resource.model.Menu;
/*     */ import com.enation.eop.sdk.context.EopSetting;
/*     */ import com.enation.framework.context.spring.SpringContextHolder;
/*     */ import com.enation.framework.context.webcontext.ThreadContextHolder;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class MenuJsonGetter extends AbstractFacadeProcessor
/*     */ {
/*     */   public MenuJsonGetter(FacadePage page)
/*     */   {
/*  28 */     super(page);
/*     */   }
/*     */ 
/*     */   protected Response process()
/*     */   {
/*  34 */     Response response = new StringResponse();
/*  35 */     String menu = getMenuJson();
/*  36 */     response.setContent(menu);
/*  37 */     return response;
/*     */   }
/*     */ 
/*     */   public String getMenuJson() {
/*  41 */     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
/*  42 */     String showall = request.getParameter("showall");
/*  43 */     StringBuffer json = new StringBuffer();
/*     */ 
/*  48 */     IMenuManager menuManager = (IMenuManager)SpringContextHolder.getBean("menuManager");
/*  49 */     List tempMenuList = menuManager.getMenuList();
/*  50 */     List menuList = new ArrayList();
/*  51 */     IPermissionManager permissionManager = (IPermissionManager)SpringContextHolder.getBean("permissionManager");
/*  52 */     IAdminUserManager adminUserManager = (IAdminUserManager)SpringContextHolder.getBean("adminUserManager");
/*  53 */     IAuthActionManager authActionManager = (IAuthActionManager)SpringContextHolder.getBean("authActionManager");
/*  54 */     AdminUser user = adminUserManager.getCurrentUser();
/*  55 */     user = adminUserManager.get(user.getUserid());
/*  56 */     List authList = permissionManager.getUesrAct(user.getUserid().intValue(), "menu");
/*     */ 
/*  58 */     for (Menu menu : tempMenuList) {
/*  59 */       if (menu.getMenutype().intValue() == 2)
/*     */       {
/*  61 */         if (!"yes".equals(showall))
/*     */         {
/*  63 */           if (user.getFounder() != 1) {
/*  64 */             if (!checkPermssion(menu, authList))
/*  65 */               continue;
/*     */           }
/*     */           else {
/*  68 */             int superAdminAuthId = PermissionConfig.getAuthId("super_admin");
/*  69 */             AuthAction superAuth = authActionManager.get(superAdminAuthId);
/*  70 */             if ((superAuth != null) && (!checkPermssion(menu, superAuth)))
/*     */             {
/*     */               continue;
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*  77 */       menuList.add(menu);
/*     */     }
/*  79 */     List syslist = getMenuList(1, menuList);
/*  80 */     List applist = getMenuList(2, menuList);
/*  81 */     List extlist = getMenuList(3, menuList);
/*     */ 
/*  83 */     json.append("var menu ={");
/*  84 */     json.append("'sys':[");
/*  85 */     json.append(toJson(syslist, menuList));
/*  86 */     json.append("]");
/*     */ 
/*  88 */     json.append(",'app':[");
/*  89 */     json.append(toJson(applist, menuList));
/*  90 */     json.append("]");
/*     */ 
/*  92 */     json.append(",'ext':[");
/*  93 */     json.append(toJson(extlist, menuList));
/*  94 */     json.append("]");
/*  95 */     json.append("};");
/*     */ 
/*  97 */     json.append("var mainpage=true;");
/*  98 */     json.append("var domain='" + request.getServerName() + "';");
/*  99 */     json.append("var runmode=" + EopSetting.RUNMODE + ";");
/* 100 */     json.append("var app_path='" + request.getContextPath() + "';");
/*     */ 
/* 102 */     return json.toString();
/*     */   }
/*     */ 
/*     */   public String toJson(List<Menu> menuList, List<Menu> allList)
/*     */   {
/* 113 */     StringBuffer menuItem = new StringBuffer();
/* 114 */     int i = 0;
/*     */ 
/* 116 */     for (Menu menu : menuList)
/*     */     {
/* 118 */       if (i != 0) menuItem.append(",");
/* 119 */       menuItem.append(toJson(menu, allList));
/* 120 */       i++;
/*     */     }
/*     */ 
/* 124 */     return menuItem.toString();
/*     */   }
/*     */ 
/*     */   private boolean checkPermssion(Menu menu, List<AuthAction> authList)
/*     */   {
/* 129 */     for (AuthAction auth : authList) {
/* 130 */       if (checkPermssion(menu, auth)) {
/* 131 */         return true;
/*     */       }
/*     */     }
/* 134 */     return false;
/*     */   }
/*     */ 
/*     */   private boolean checkPermssion(Menu menu, AuthAction auth)
/*     */   {
/* 139 */     String values = auth.getObjvalue();
/* 140 */     if (values != null) {
/* 141 */       String[] value_ar = StringUtils.split(values, ",");
/* 142 */       for (String v : value_ar) {
/* 143 */         if (v.equals("" + menu.getId().intValue())) {
/* 144 */           return true;
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 149 */     return false;
/*     */   }
/*     */ 
/*     */   private String toJson(Menu menu, List<Menu> menuList)
/*     */   {
/* 161 */     String title = menu.getTitle();
/* 162 */     String url = menu.getUrl();
/* 163 */     Integer selected = menu.getSelected();
/* 164 */     String type = menu.getDatatype();
/* 165 */     String target = menu.getTarget();
/*     */ 
/* 167 */     if (!"_blank".equals(target)) {
/* 168 */       String ctx = EopSetting.CONTEXT_PATH;
/* 169 */       ctx = ctx.equals("/") ? "" : ctx;
/* 170 */       url = ctx + url;
/*     */     }
/*     */ 
/* 174 */     StringBuffer menuItem = new StringBuffer();
/*     */ 
/* 176 */     menuItem.append("{");
/*     */ 
/* 178 */     menuItem.append("id:");
/* 179 */     menuItem.append(menu.getId());
/*     */ 
/* 182 */     menuItem.append(",text:'");
/* 183 */     menuItem.append(title);
/* 184 */     menuItem.append("'");
/*     */ 
/* 186 */     menuItem.append(",url:'");
/* 187 */     menuItem.append(url);
/* 188 */     menuItem.append("'");
/*     */ 
/* 191 */     menuItem.append(",'default':");
/* 192 */     menuItem.append(selected);
/*     */ 
/* 194 */     menuItem.append(",children:");
/* 195 */     menuItem.append(getChildrenJson(menu.getId(), menuList));
/*     */ 
/* 197 */     menuItem.append(",type:'");
/* 198 */     menuItem.append(type);
/* 199 */     menuItem.append("'");
/*     */ 
/* 201 */     menuItem.append(",target:'");
/* 202 */     menuItem.append(target);
/* 203 */     menuItem.append("'");
/*     */ 
/* 205 */     menuItem.append("}");
/*     */ 
/* 207 */     return menuItem.toString();
/*     */   }
/*     */ 
/*     */   public List<Menu> getMenuList(int menuType, List<Menu> menuList)
/*     */   {
/* 218 */     List mlist = new ArrayList();
/*     */ 
/* 220 */     for (Menu menu : menuList) {
/* 221 */       if ((menu.getMenutype().intValue() == menuType) && (menu.getPid().intValue() == 0)) {
/* 222 */         mlist.add(menu);
/*     */       }
/*     */     }
/*     */ 
/* 226 */     return mlist;
/*     */   }
/*     */ 
/*     */   private String getChildrenJson(Integer menuId, List<Menu> menuList)
/*     */   {
/* 237 */     StringBuffer json = new StringBuffer();
/* 238 */     json.append("[");
/* 239 */     int i = 0;
/* 240 */     for (Menu menu : menuList)
/*     */     {
/* 242 */       if (menuId.intValue() == menu.getPid().intValue()) {
/* 243 */         if (i != 0)
/* 244 */           json.append(",");
/* 245 */         json.append(toJson(menu, menuList));
/* 246 */         i++;
/*     */       }
/*     */     }
/* 249 */     json.append("]");
/* 250 */     return json.toString();
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.eop.processor.backend.support.MenuJsonGetter
 * JD-Core Version:    0.6.0
 */