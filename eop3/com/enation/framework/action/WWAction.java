/*     */ package com.enation.framework.action;
/*     */ 
/*     */ import com.enation.eop.sdk.context.EopSetting;
/*     */ import com.enation.framework.database.Page;
/*     */ import com.enation.framework.util.StringUtil;
/*     */ import com.opensymphony.xwork2.ActionSupport;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.io.PrintWriter;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.apache.struts2.ServletActionContext;
/*     */ import org.apache.struts2.interceptor.SessionAware;
/*     */ 
/*     */ public class WWAction extends ActionSupport
/*     */   implements SessionAware
/*     */ {
/*  22 */   protected final Logger logger = Logger.getLogger(getClass());
/*     */   protected Page webpage;
/*  25 */   protected Map session = null;
/*     */ 
/*  27 */   protected List msgs = new ArrayList();
/*     */ 
/*  29 */   protected Map urls = new HashMap();
/*     */ 
/*  32 */   protected Map blankUrls = new HashMap();
/*     */   protected static final String MESSAGE = "message";
/*     */   protected static final String JSON_MESSAGE = "json_message";
/*  40 */   protected String script = "";
/*     */   protected String json;
/*     */   protected int page;
/*     */   protected int pageSize;
/*     */   protected String pageId;
/*     */ 
/*     */   public String list_ajax()
/*     */   {
/*  57 */     return "list";
/*     */   }
/*     */ 
/*     */   public String add_input()
/*     */   {
/*  62 */     return "add";
/*     */   }
/*     */ 
/*     */   public String edit_input()
/*     */   {
/*  67 */     return "edit";
/*     */   }
/*     */ 
/*     */   public int getPageSize() {
/*  71 */     return Integer.valueOf(EopSetting.BACKEND_PAGESIZE).intValue();
/*     */   }
/*     */ 
/*     */   public void setPageSize(int pageSize) {
/*  75 */     this.pageSize = pageSize;
/*     */   }
/*     */ 
/*     */   public int getPage() {
/*  79 */     this.page = (this.page < 1 ? 1 : this.page);
/*  80 */     return this.page;
/*     */   }
/*     */ 
/*     */   public void setPage(int page) {
/*  84 */     this.page = page;
/*     */   }
/*     */ 
/*     */   public Map getSession()
/*     */   {
/*  90 */     return this.session;
/*     */   }
/*     */ 
/*     */   public void setSession(Map session) {
/*  94 */     this.session = session;
/*     */   }
/*     */ 
/*     */   protected HttpServletRequest getRequest() {
/*  98 */     return ServletActionContext.getRequest();
/*     */   }
/*     */ 
/*     */   protected HttpServletResponse getResponse() {
/* 102 */     return ServletActionContext.getResponse();
/*     */   }
/*     */ 
/*     */   protected void render(String text, String contentType)
/*     */   {
/*     */     try
/*     */     {
/* 113 */       HttpServletResponse response = getResponse();
/* 114 */       response.setContentType(contentType);
/* 115 */       response.getWriter().write(text);
/*     */     } catch (IOException e) {
/* 117 */       System.err.println(e.getMessage());
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void renderText(String text)
/*     */   {
/* 125 */     render(text, "text/plain;charset=UTF-8");
/*     */   }
/*     */ 
/*     */   protected void renderHtml(String text)
/*     */   {
/* 132 */     render(text, "text/html;charset=UTF-8");
/*     */   }
/*     */ 
/*     */   protected void renderXML(String text)
/*     */   {
/* 139 */     render(text, "text/xml;charset=UTF-8");
/*     */   }
/*     */ 
/*     */   protected void showSuccessJson(String message)
/*     */   {
/* 144 */     if (StringUtil.isEmpty(message))
/* 145 */       this.json = "{result:1}";
/*     */     else
/* 147 */       this.json = ("{result:1,message:\"" + message + "\"}");
/*     */   }
/*     */ 
/*     */   protected void showErrorJson(String message)
/*     */   {
/* 152 */     if (StringUtil.isEmpty(message))
/* 153 */       this.json = "{result:0}";
/*     */     else
/* 155 */       this.json = ("{result:0,message:\"" + message + "\"}");
/*     */   }
/*     */ 
/*     */   public List getMsgs()
/*     */   {
/* 161 */     return this.msgs;
/*     */   }
/*     */ 
/*     */   public void setMsgs(List msgs) {
/* 165 */     this.msgs = msgs;
/*     */   }
/*     */ 
/*     */   public Map getUrls() {
/* 169 */     return this.urls;
/*     */   }
/*     */ 
/*     */   public void setUrls(Map urls) {
/* 173 */     this.urls = urls;
/*     */   }
/*     */ 
/*     */   public Page getWebpage() {
/* 177 */     return this.webpage;
/*     */   }
/*     */ 
/*     */   public void setWebpage(Page webpage) {
/* 181 */     this.webpage = webpage;
/*     */   }
/*     */ 
/*     */   public String getScript() {
/* 185 */     return this.script;
/*     */   }
/*     */ 
/*     */   public void setScript(String script) {
/* 189 */     this.script = script;
/*     */   }
/*     */ 
/*     */   public String getJson() {
/* 193 */     return this.json;
/*     */   }
/*     */ 
/*     */   public void setJson(String json) {
/* 197 */     this.json = json;
/*     */   }
/*     */ 
/*     */   public Map getBlankUrls() {
/* 201 */     return this.blankUrls;
/*     */   }
/*     */ 
/*     */   public void setBlankUrls(Map blankUrls) {
/* 205 */     this.blankUrls = blankUrls;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.framework.action.WWAction
 * JD-Core Version:    0.6.0
 */