/*     */ package com.enation.eop.processor.facade.support;
/*     */ 
/*     */ import com.enation.eop.processor.IPagePaser;
/*     */ import com.enation.eop.processor.PageWrapper;
/*     */ import com.enation.eop.resource.model.EopSite;
/*     */ import com.enation.eop.sdk.context.EopContext;
/*     */ import com.enation.eop.sdk.context.EopSetting;
/*     */ import com.enation.framework.context.webcontext.ThreadContextHolder;
/*     */ import com.enation.framework.resource.impl.ResourceBuilder;
/*     */ import com.enation.framework.util.StringUtil;
/*     */ import java.io.IOException;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.mozilla.javascript.EvaluatorException;
/*     */ 
/*     */ public class HeaderResourcePageWrapper extends PageWrapper
/*     */ {
/*     */   public HeaderResourcePageWrapper(IPagePaser paser)
/*     */   {
/*  21 */     super(paser);
/*     */   }
/*     */ 
/*     */   public String pase(String url)
/*     */   {
/*  26 */     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
/*  27 */     String content = super.pase(url);
/*  28 */     String pageid = (String)request.getAttribute("pageid");
/*  29 */     String tplFileName = (String)request.getAttribute("tplFileName");
/*     */ 
/*  31 */     if ((StringUtil.isEmpty(pageid)) || (StringUtil.isEmpty(tplFileName))) {
/*  32 */       return content;
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/*  43 */       ResourceBuilder.reCreate(pageid, tplFileName);
/*     */     } catch (EvaluatorException e) {
/*  45 */       e.printStackTrace();
/*     */     } catch (IOException e) {
/*  47 */       e.printStackTrace();
/*     */     }
/*     */ 
/*  58 */     StringBuffer headerhtml = new StringBuffer();
/*  59 */     EopContext ectx = EopContext.getContext();
/*  60 */     EopSite site = ectx.getCurrentSite();
/*     */ 
/*  74 */     String resdomain = ectx.getResDomain();
/*     */ 
/*  76 */     String scriptpath = "";
/*  77 */     String csspath = "";
/*     */ 
/*  79 */     if (!EopSetting.DEVELOPMENT_MODEL) {
/*  80 */       if ("2".equals(EopSetting.RESOURCEMODE))
/*     */       {
/*  90 */         resdomain = resdomain + "/themes/" + site.getThemepath();
/*  91 */         scriptpath = resdomain + "/js/headerresource?type=javascript&id=" + pageid;
/*  92 */         csspath = resdomain + "/css/headerresource?type=css&id=" + pageid;
/*  93 */         headerhtml.append("<script src=\"" + scriptpath + "\" type=\"text/javascript\"></script>");
/*  94 */         headerhtml.append("<link href=\"" + csspath + "\" rel=\"stylesheet\" type=\"text/css\" />");
/*     */       }
/*     */       else
/*     */       {
/* 106 */         if (ResourceBuilder.haveCommonScript()) {
/* 107 */           String commonjs = resdomain + "/js/" + site.getThemepath() + "_common.js";
/* 108 */           headerhtml.append("<script src=\"" + commonjs + "\" type=\"text/javascript\"></script>");
/*     */         }
/*     */ 
/* 113 */         if (ResourceBuilder.haveCommonCss()) {
/* 114 */           String commoncss = resdomain + "/css/" + site.getThemepath() + "_common.css";
/* 115 */           headerhtml.append("<link href=\"" + commoncss + "\" rel=\"stylesheet\" type=\"text/css\" />");
/*     */         }
/*     */ 
/* 121 */         if (ResourceBuilder.haveScript()) {
/* 122 */           scriptpath = resdomain + "/js/" + site.getThemepath() + "_" + pageid + ".js";
/* 123 */           headerhtml.append("<script src=\"" + scriptpath + "\" type=\"text/javascript\"></script>");
/*     */         }
/*     */ 
/* 128 */         if (ResourceBuilder.haveCss()) {
/* 129 */           csspath = resdomain + "/css/" + site.getThemepath() + "_" + tplFileName + ".css";
/* 130 */           headerhtml.append("<link href=\"" + csspath + "\" rel=\"stylesheet\" type=\"text/css\" />");
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 141 */     headerhtml.append(ResourceBuilder.getNotMergeResource());
/* 142 */     content = content.replaceAll("#headerscript#", headerhtml.toString());
/* 143 */     return content;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.eop.processor.facade.support.HeaderResourcePageWrapper
 * JD-Core Version:    0.6.0
 */