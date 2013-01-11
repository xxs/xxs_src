/*    */ package com.enation.eop.processor.facade.support;
/*    */ 
/*    */ import com.enation.eop.processor.IPagePaser;
/*    */ import com.enation.eop.processor.PageWrapper;
/*    */ import com.enation.eop.resource.model.EopSite;
/*    */ import com.enation.eop.sdk.context.EopContext;
/*    */ import com.enation.eop.sdk.context.EopSetting;
/*    */ import com.enation.eop.sdk.utils.FreeMarkerUtil;
/*    */ import com.enation.eop.sdk.utils.HtmlUtil;
/*    */ import com.sun.xml.messaging.saaj.util.ByteOutputStream;
/*    */ import freemarker.template.Configuration;
/*    */ import freemarker.template.Template;
/*    */ import java.io.OutputStreamWriter;
/*    */ import java.io.Writer;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class PageEditModeWrapper extends PageWrapper
/*    */ {
/*    */   public PageEditModeWrapper(IPagePaser paser)
/*    */   {
/* 28 */     super(paser);
/*    */   }
/*    */ 
/*    */   public String pase(String url)
/*    */   {
/* 33 */     String content = super.pase(url);
/* 34 */     String script = getToolBarScript();
/* 35 */     String html = getToolBarHtml();
/*    */ 
/* 37 */     content = wrapPageMain(content);
/* 38 */     content = HtmlUtil.appendTo(content, "head", script);
/* 39 */     content = HtmlUtil.insertTo(content, "body", html);
/*    */ 
/* 41 */     return content;
/*    */   }
/*    */ 
/*    */   private String getToolBarScript() {
/* 45 */     String eopFld = EopSetting.EOP_PATH + "/eop/";
/*    */     try {
/* 47 */       Map data = new HashMap();
/* 48 */       data.put("staticserver", EopSetting.IMG_SERVER_DOMAIN);
/* 49 */       data.put("ctx", EopSetting.CONTEXT_PATH);
/* 50 */       EopSite site = EopContext.getContext().getCurrentSite();
/* 51 */       data.put("userid", "" + site.getUserid());
/* 52 */       data.put("siteid", "" + site.getId());
/*    */ 
/* 54 */       Configuration cfg = FreeMarkerUtil.getFolderCfg(eopFld);
/* 55 */       Template temp = cfg.getTemplate("widget_tool_script.html");
/* 56 */       ByteOutputStream stream = new ByteOutputStream();
/* 57 */       Writer out = new OutputStreamWriter(stream);
/* 58 */       temp.process(data, out);
/* 59 */       out.flush();
/* 60 */       return stream.toString();
/*    */     } catch (Exception e) {
/* 62 */       e.printStackTrace();
/* 63 */     }return "";
/*    */   }
/*    */ 
/*    */   private String getToolBarHtml()
/*    */   {
/* 70 */     String eopFld = EopSetting.EOP_PATH + "/eop/";
/*    */     try {
/* 72 */       Map data = new HashMap();
/* 73 */       data.put("staticserver", EopSetting.IMG_SERVER_DOMAIN);
/* 74 */       data.put("ctx", EopSetting.CONTEXT_PATH);
/* 75 */       EopSite site = EopContext.getContext().getCurrentSite();
/* 76 */       data.put("userid", "" + site.getUserid());
/* 77 */       data.put("siteid", "" + site.getId());
/* 78 */       Configuration cfg = FreeMarkerUtil.getFolderCfg(eopFld);
/* 79 */       Template temp = cfg.getTemplate("widget_toolbar.html");
/* 80 */       ByteOutputStream stream = new ByteOutputStream();
/* 81 */       Writer out = new OutputStreamWriter(stream);
/* 82 */       temp.process(data, out);
/* 83 */       out.flush();
/* 84 */       return stream.toString();
/*    */     } catch (Exception e) {
/* 86 */       e.printStackTrace();
/* 87 */     }return "";
/*    */   }
/*    */ 
/*    */   private String wrapPageMain(String content)
/*    */   {
/* 92 */     content = HtmlUtil.insertTo(content, "body", "<div id=\"pagemain\">");
/* 93 */     content = HtmlUtil.appendTo(content, "body", "</div>");
/* 94 */     return content;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.eop.processor.facade.support.PageEditModeWrapper
 * JD-Core Version:    0.6.0
 */