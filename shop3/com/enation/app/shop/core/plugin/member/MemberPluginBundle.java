/*     */ package com.enation.app.shop.core.plugin.member;
/*     */ 
/*     */ import com.enation.app.base.core.model.Member;
/*     */ import com.enation.eop.processor.core.freemarker.FreeMarkerPaser;
/*     */ import com.enation.framework.plugin.AutoRegisterPluginsBundle;
/*     */ import com.enation.framework.plugin.IPlugin;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.TreeMap;
/*     */ import org.apache.commons.logging.Log;
/*     */ 
/*     */ public class MemberPluginBundle extends AutoRegisterPluginsBundle
/*     */ {
/*     */   public void onLogout(Member member)
/*     */   {
/*     */     try
/*     */     {
/*  32 */       List plugins = getPlugins();
/*     */ 
/*  34 */       if (plugins != null) {
/*  35 */         for (IPlugin plugin : plugins)
/*  36 */           if ((plugin instanceof IMemberLogoutEvent)) {
/*  37 */             if (loger.isDebugEnabled()) {
/*  38 */               loger.debug("调用插件 : " + plugin.getClass() + " onLogout 开始...");
/*     */             }
/*  40 */             IMemberLogoutEvent event = (IMemberLogoutEvent)plugin;
/*  41 */             event.onLogout(member);
/*  42 */             if (loger.isDebugEnabled())
/*  43 */               loger.debug("调用插件 : " + plugin.getClass() + " onLogout 结束.");
/*     */           }
/*     */       }
/*     */     }
/*     */     catch (RuntimeException e)
/*     */     {
/*  49 */       if (loger.isErrorEnabled())
/*  50 */         loger.error("调用会员插件注销事件错误", e);
/*  51 */       throw e;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void onLogin(Member member, Long upLogintime)
/*     */   {
/*     */     try
/*     */     {
/*  62 */       List plugins = getPlugins();
/*     */ 
/*  64 */       if (plugins != null) {
/*  65 */         for (IPlugin plugin : plugins)
/*  66 */           if ((plugin instanceof IMemberLoginEvent)) {
/*  67 */             if (loger.isDebugEnabled()) {
/*  68 */               loger.debug("调用插件 : " + plugin.getClass() + " onLogin 开始...");
/*     */             }
/*  70 */             IMemberLoginEvent event = (IMemberLoginEvent)plugin;
/*  71 */             event.onLogin(member, upLogintime);
/*  72 */             if (loger.isDebugEnabled())
/*  73 */               loger.debug("调用插件 : " + plugin.getClass() + " onLogin 结束.");
/*     */           }
/*     */       }
/*     */     }
/*     */     catch (RuntimeException e)
/*     */     {
/*  79 */       if (loger.isErrorEnabled())
/*  80 */         loger.error("调用会员插件登录事件错误", e);
/*  81 */       throw e;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void onRegister(Member member)
/*     */   {
/*     */     try
/*     */     {
/*  93 */       List plugins = getPlugins();
/*     */ 
/*  95 */       if (plugins != null) {
/*  96 */         for (IPlugin plugin : plugins)
/*  97 */           if ((plugin instanceof IMemberRegisterEvent)) {
/*  98 */             if (loger.isDebugEnabled()) {
/*  99 */               loger.debug("调用插件 : " + plugin.getClass() + " onRegister 开始...");
/*     */             }
/* 101 */             IMemberRegisterEvent event = (IMemberRegisterEvent)plugin;
/* 102 */             event.onRegister(member);
/* 103 */             if (loger.isDebugEnabled())
/* 104 */               loger.debug("调用插件 : " + plugin.getClass() + " onRegister 结束.");
/*     */           }
/*     */       }
/*     */     }
/*     */     catch (RuntimeException e)
/*     */     {
/* 110 */       if (loger.isErrorEnabled())
/* 111 */         loger.error("调用会员插件注册事件错误", e);
/* 112 */       throw e;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void onEmailCheck(Member member)
/*     */   {
/*     */     try
/*     */     {
/* 124 */       List plugins = getPlugins();
/*     */ 
/* 126 */       if (plugins != null) {
/* 127 */         for (IPlugin plugin : plugins)
/* 128 */           if ((plugin instanceof IMemberEmailCheckEvent)) {
/* 129 */             if (loger.isDebugEnabled()) {
/* 130 */               loger.debug("调用插件 : " + plugin.getClass() + " onRegister 开始...");
/*     */             }
/* 132 */             IMemberEmailCheckEvent event = (IMemberEmailCheckEvent)plugin;
/* 133 */             event.onEmailCheck(member);
/* 134 */             if (loger.isDebugEnabled())
/* 135 */               loger.debug("调用插件 : " + plugin.getClass() + " onRegister 结束.");
/*     */           }
/*     */       }
/*     */     }
/*     */     catch (RuntimeException e)
/*     */     {
/* 141 */       if (loger.isErrorEnabled())
/* 142 */         loger.error("调用会员插件邮件验证事件错误", e);
/* 143 */       throw e;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void onUpdatePassword(String password, int memberid)
/*     */   {
/*     */     try
/*     */     {
/* 156 */       List plugins = getPlugins();
/*     */ 
/* 158 */       if (plugins != null) {
/* 159 */         for (IPlugin plugin : plugins)
/* 160 */           if ((plugin instanceof IMemberUpdatePasswordEvent)) {
/* 161 */             if (loger.isDebugEnabled()) {
/* 162 */               loger.debug("调用插件 : " + plugin.getClass() + " onUpdatePassword 开始...");
/*     */             }
/* 164 */             IMemberUpdatePasswordEvent event = (IMemberUpdatePasswordEvent)plugin;
/* 165 */             event.updatePassword(password, memberid);
/* 166 */             if (loger.isDebugEnabled())
/* 167 */               loger.debug("调用插件 : " + plugin.getClass() + " onUpdatePassword 结束.");
/*     */           }
/*     */       }
/*     */     }
/*     */     catch (RuntimeException e)
/*     */     {
/* 173 */       if (loger.isErrorEnabled())
/* 174 */         loger.error("调用会员更新密码事件错误", e);
/* 175 */       throw e;
/*     */     }
/*     */   }
/*     */ 
/*     */   public Map<Integer, String> getTabList(Member member)
/*     */   {
/* 188 */     List plugins = getPlugins();
/*     */ 
/* 190 */     Map tabMap = new TreeMap();
/* 191 */     if (plugins != null)
/*     */     {
/* 194 */       for (IPlugin plugin : plugins) {
/* 195 */         if ((plugin instanceof IMemberTabShowEvent))
/*     */         {
/* 198 */           IMemberTabShowEvent event = (IMemberTabShowEvent)plugin;
/*     */ 
/* 203 */           if (!event.canBeExecute(member))
/*     */           {
/*     */             continue;
/*     */           }
/*     */ 
/* 208 */           String name = event.getTabName(member);
/* 209 */           tabMap.put(Integer.valueOf(event.getOrder()), name);
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 217 */     return tabMap;
/*     */   }
/*     */ 
/*     */   public Map<Integer, String> getDetailHtml(Member member)
/*     */   {
/* 227 */     Map htmlMap = new TreeMap();
/* 228 */     FreeMarkerPaser freeMarkerPaser = FreeMarkerPaser.getInstance();
/* 229 */     freeMarkerPaser.putData("member", member);
/* 230 */     List plugins = getPlugins();
/*     */ 
/* 232 */     if (plugins != null) {
/* 233 */       for (IPlugin plugin : plugins)
/*     */       {
/* 236 */         if ((plugin instanceof IMemberTabShowEvent)) {
/* 237 */           IMemberTabShowEvent event = (IMemberTabShowEvent)plugin;
/* 238 */           freeMarkerPaser.setClz(event.getClass());
/*     */ 
/* 243 */           if (!event.canBeExecute(member)) {
/*     */             continue;
/*     */           }
/* 246 */           String html = event.onShowMemberDetailHtml(member);
/* 247 */           htmlMap.put(Integer.valueOf(event.getOrder()), html);
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 254 */     return htmlMap;
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/* 262 */     return "会员插件桩";
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.plugin.member.MemberPluginBundle
 * JD-Core Version:    0.6.0
 */