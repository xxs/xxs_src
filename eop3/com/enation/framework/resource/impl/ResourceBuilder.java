/*     */ package com.enation.framework.resource.impl;
/*     */ 
/*     */ import com.enation.eop.resource.model.EopSite;
/*     */ import com.enation.eop.sdk.context.EopContext;
/*     */ import com.enation.eop.sdk.context.EopSetting;
/*     */ import com.enation.framework.context.webcontext.ThreadContextHolder;
/*     */ import com.enation.framework.resource.Resource;
/*     */ import com.enation.framework.resource.ResourceStateManager;
/*     */ import com.enation.framework.util.FileUtil;
/*     */ import com.enation.framework.util.StringUtil;
/*     */ import com.yahoo.platform.yui.compressor.CssCompressor;
/*     */ import com.yahoo.platform.yui.compressor.JavaScriptCompressor;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.io.StringReader;
/*     */ import java.io.StringWriter;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.mozilla.javascript.EvaluatorException;
/*     */ 
/*     */ public class ResourceBuilder
/*     */ {
/*  37 */   protected static final Logger logger = Logger.getLogger(ResourceBuilder.class);
/*     */ 
/* 102 */   private static Map<String, String> scriptMap = new HashMap();
/* 103 */   private static Map<String, String> cssMap = new HashMap();
/* 104 */   private static Map<String, String> imageMap = new HashMap();
/*     */ 
/* 106 */   private static Map<String, String> commonScriptStateMap = new HashMap();
/* 107 */   private static Map<String, String> commonCssStateMap = new HashMap();
/*     */ 
/* 109 */   private static boolean imageCreated = false;
/* 110 */   private static boolean commonScriptCreated = false;
/* 111 */   private static boolean commonCssCreated = false;
/*     */ 
/*     */   public static String getScript(String tplFileName)
/*     */   {
/* 122 */     return (String)scriptMap.get(tplFileName);
/*     */   }
/*     */ 
/*     */   public static String getCss(String tplFileName)
/*     */   {
/* 128 */     return (String)cssMap.get(tplFileName);
/*     */   }
/*     */ 
/*     */   public static void reCreate(String pageid, String tplFileName)
/*     */     throws EvaluatorException, IOException
/*     */   {
/* 143 */     if ((EopSetting.DEVELOPMENT_MODEL) || ("2".equals(EopSetting.RESOURCEMODE)))
/*     */     {
/* 145 */       String scriptContent = null;
/* 146 */       scriptContent = getScriptFromCache(pageid);
/*     */ 
/* 149 */       if ((StringUtil.isEmpty(scriptContent)) || (EopSetting.DEVELOPMENT_MODEL))
/*     */       {
/* 151 */         StringBuffer content = new StringBuffer();
/* 152 */         content.append(readFromDisk("headerScriptList"));
/* 153 */         content.append(readFromDisk("headerScriptCommonList"));
/*     */ 
/* 155 */         scriptMap.put(pageid, content.toString());
/*     */       }
/*     */ 
/* 159 */       String cssContent = getCssFromCache(pageid);
/*     */ 
/* 161 */       if ((StringUtil.isEmpty(cssContent)) || (EopSetting.DEVELOPMENT_MODEL))
/*     */       {
/* 163 */         StringBuffer content = new StringBuffer();
/* 164 */         content.append(readFromDisk("headerCssList"));
/* 165 */         content.append(readFromDisk("headerCssCommonList"));
/*     */ 
/* 167 */         cssMap.put(pageid, content.toString());
/*     */       }
/*     */ 
/*     */     }
/*     */     else
/*     */     {
/* 173 */       createCommonScript();
/* 174 */       createCommonCss();
/*     */ 
/* 176 */       if ((!isScriptCreated(pageid)) || (!isCssCreated(pageid)) || (!isImageCreated()) || (ResourceStateManager.getHaveNewDisploy()))
/*     */       {
/* 178 */         dispatchRes(pageid, tplFileName);
/*     */ 
/* 181 */         if (ResourceStateManager.getHaveNewDisploy())
/* 182 */           ResourceStateManager.setDisplayState(0);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private static void createCommonScript()
/*     */     throws EvaluatorException, IOException
/*     */   {
/* 195 */     if ((!isCommonScriptCreated()) || (ResourceStateManager.getHaveNewDisploy()))
/*     */     {
/* 197 */       EopContext ectx = EopContext.getContext();
/* 198 */       String target = ectx.getResPath();
/* 199 */       String themepath = ectx.getCurrentSite().getThemepath();
/*     */ 
/* 201 */       String scriptContent = readFromDisk("headerScriptCommonList");
/*     */ 
/* 204 */       if (StringUtil.isEmpty(scriptContent)) {
/* 205 */         return;
/*     */       }
/*     */ 
/* 209 */       String jspath = target + "/js/";
/* 210 */       FileUtil.createFolder(jspath);
/*     */ 
/* 212 */       jspath = jspath + themepath + "_common.js";
/*     */ 
/* 215 */       if (logger.isDebugEnabled()) {
/* 216 */         logger.debug(" create common script to->[" + jspath + "]");
/*     */       }
/*     */ 
/* 220 */       FileUtil.write(jspath, scriptContent);
/*     */ 
/* 223 */       setCommonScriptCreated();
/*     */     }
/*     */   }
/*     */ 
/*     */   private static void createCommonCss()
/*     */     throws EvaluatorException, IOException
/*     */   {
/* 232 */     if ((!isCommonCssCreated()) || (ResourceStateManager.getHaveNewDisploy())) {
/* 233 */       EopContext ectx = EopContext.getContext();
/* 234 */       String target = ectx.getResPath();
/* 235 */       String themepath = ectx.getCurrentSite().getThemepath();
/*     */ 
/* 237 */       String cssContent = readFromDisk("headerCssCommonList");
/*     */ 
/* 239 */       if (StringUtil.isEmpty(cssContent)) {
/* 240 */         return;
/*     */       }
/*     */ 
/* 243 */       String csspath = target + "/css/";
/* 244 */       FileUtil.createFolder(csspath);
/*     */ 
/* 246 */       csspath = csspath + themepath + "_common.css";
/*     */ 
/* 248 */       if (logger.isDebugEnabled()) {
/* 249 */         logger.debug("create common css to->[" + csspath + "]");
/*     */       }
/*     */ 
/* 252 */       FileUtil.write(csspath, cssContent);
/* 253 */       setCommonCssCreated();
/*     */     }
/*     */   }
/*     */ 
/*     */   public static boolean haveScript()
/*     */   {
/* 268 */     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
/* 269 */     List scriptList = (List)request.getAttribute("headerScriptList");
/* 270 */     return (scriptList != null) && (!scriptList.isEmpty());
/*     */   }
/*     */ 
/*     */   public static boolean haveCommonScript()
/*     */   {
/* 279 */     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
/* 280 */     List scriptList = (List)request.getAttribute("headerScriptCommonList");
/* 281 */     return (scriptList != null) && (!scriptList.isEmpty());
/*     */   }
/*     */ 
/*     */   public static boolean haveCommonCss()
/*     */   {
/* 292 */     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
/* 293 */     List cssList = (List)request.getAttribute("headerCssCommonList");
/* 294 */     return (cssList != null) && (!cssList.isEmpty());
/*     */   }
/*     */ 
/*     */   public static boolean haveCss()
/*     */   {
/* 305 */     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
/* 306 */     List cssList = (List)request.getAttribute("headerCssList");
/* 307 */     return (cssList != null) && (!cssList.isEmpty());
/*     */   }
/*     */ 
/*     */   private static String getScriptFromCache(String pageid)
/*     */   {
/* 320 */     String key = getKey(pageid);
/* 321 */     String scriptContent = (String)scriptMap.get(key);
/*     */ 
/* 323 */     return scriptContent;
/*     */   }
/*     */ 
/*     */   private static String getCssFromCache(String pageid)
/*     */   {
/* 335 */     String key = getKey(pageid);
/* 336 */     String scriptContent = (String)cssMap.get(key);
/*     */ 
/* 338 */     return scriptContent;
/*     */   }
/*     */ 
/*     */   private static boolean isScriptCreated(String pageid)
/*     */   {
/* 349 */     String key = getKey(pageid);
/* 350 */     return scriptMap.get(key) != null;
/*     */   }
/*     */ 
/*     */   private static boolean isCommonScriptCreated()
/*     */   {
/* 365 */     if ("2".equals(EopSetting.RUNMODE)) {
/* 366 */       EopSite site = EopContext.getContext().getCurrentSite();
/* 367 */       String key = "script_created_" + site.getUserid() + "_" + site.getId();
/* 368 */       return !StringUtil.isEmpty((String)commonScriptStateMap.get(key));
/*     */     }
/* 370 */     return commonScriptCreated;
/*     */   }
/*     */ 
/*     */   private static boolean isCommonCssCreated()
/*     */   {
/* 384 */     if ("2".equals(EopSetting.RUNMODE)) {
/* 385 */       EopSite site = EopContext.getContext().getCurrentSite();
/* 386 */       String key = "script_created_" + site.getUserid() + "_" + site.getId();
/* 387 */       return !StringUtil.isEmpty((String)commonCssStateMap.get(key));
/*     */     }
/* 389 */     return commonCssCreated;
/*     */   }
/*     */ 
/*     */   private static boolean isCssCreated(String pageid)
/*     */   {
/* 407 */     String key = getKey(pageid);
/* 408 */     return cssMap.get(key) != null;
/*     */   }
/*     */ 
/*     */   private static boolean isImageCreated()
/*     */   {
/* 422 */     if ("2".equals(EopSetting.RUNMODE))
/*     */     {
/* 424 */       EopSite site = EopContext.getContext().getCurrentSite();
/* 425 */       String key = "image_created_" + site.getUserid() + "_" + site.getId();
/* 426 */       return imageMap.get(key) != null;
/*     */     }
/*     */ 
/* 429 */     return imageCreated;
/*     */   }
/*     */ 
/*     */   private static void setScriptCreated(String pageid)
/*     */   {
/* 443 */     String key = getKey(pageid);
/* 444 */     scriptMap.put(key, "created");
/*     */   }
/*     */ 
/*     */   private static void setCommonScriptCreated()
/*     */   {
/* 455 */     if ("2".equals(EopSetting.RUNMODE)) {
/* 456 */       EopSite site = EopContext.getContext().getCurrentSite();
/* 457 */       String key = "script_created_" + site.getUserid() + "_" + site.getId();
/* 458 */       commonScriptStateMap.put(key, "created");
/*     */     }
/*     */     else {
/* 461 */       commonScriptCreated = true;
/*     */     }
/*     */   }
/*     */ 
/*     */   private static void setCommonCssCreated()
/*     */   {
/* 472 */     if ("2".equals(EopSetting.RUNMODE)) {
/* 473 */       EopSite site = EopContext.getContext().getCurrentSite();
/* 474 */       String key = "script_created_" + site.getUserid() + "_" + site.getId();
/* 475 */       commonCssStateMap.put(key, "created");
/*     */     } else {
/* 477 */       commonCssCreated = true;
/*     */     }
/*     */   }
/*     */ 
/*     */   private static void setCssCreated(String pageid)
/*     */   {
/* 488 */     String key = getKey(pageid);
/* 489 */     cssMap.put(key, "created");
/*     */   }
/*     */ 
/*     */   private static void setImageCreated(String pageid)
/*     */   {
/* 498 */     if ("2".equals(EopSetting.RUNMODE))
/*     */     {
/* 500 */       EopSite site = EopContext.getContext().getCurrentSite();
/* 501 */       String key = "image_created_" + site.getUserid() + "_" + site.getId();
/* 502 */       imageMap.put(key, "created");
/*     */     }
/*     */     else {
/* 505 */       imageCreated = true;
/*     */     }
/*     */   }
/*     */ 
/*     */   private static String getKey(String pageid)
/*     */   {
/* 513 */     String key = pageid;
/* 514 */     EopSite site = EopContext.getContext().getCurrentSite();
/*     */ 
/* 517 */     if ("2".equals(EopSetting.RUNMODE)) {
/* 518 */       key = pageid + "_" + site.getUserid() + "_" + site.getId();
/*     */     }
/*     */ 
/* 521 */     return key;
/*     */   }
/*     */ 
/*     */   private static void dispatchRes(String pageid, String tplFileName)
/*     */     throws EvaluatorException, IOException
/*     */   {
/* 535 */     EopContext ectx = EopContext.getContext();
/*     */ 
/* 542 */     String target = ectx.getResPath();
/* 543 */     String themepath = ectx.getCurrentSite().getThemepath();
/*     */ 
/* 545 */     boolean newDisploy = false;
/* 546 */     newDisploy = ResourceStateManager.getHaveNewDisploy();
/*     */ 
/* 556 */     if ((haveScript()) && (
/* 557 */       (!isScriptCreated(pageid)) || (newDisploy))) {
/* 558 */       String scriptContent = readFromDisk("headerScriptList");
/*     */ 
/* 560 */       String jspath = target + "/js/";
/* 561 */       FileUtil.createFolder(jspath);
/*     */ 
/* 563 */       jspath = jspath + themepath + "_" + pageid + ".js";
/*     */ 
/* 566 */       if (logger.isDebugEnabled()) {
/* 567 */         logger.debug("create script to -> [" + jspath + "]");
/*     */       }
/*     */ 
/* 570 */       FileUtil.write(jspath, scriptContent);
/*     */ 
/* 572 */       setScriptCreated(pageid);
/*     */     }
/*     */ 
/* 582 */     if ((haveCss()) && (
/* 583 */       (!isCssCreated(tplFileName)) || (newDisploy))) {
/* 584 */       String cssContent = readFromDisk("headerCssList");
/* 585 */       String csspath = target + "/css/";
/* 586 */       FileUtil.createFolder(csspath);
/*     */ 
/* 588 */       csspath = csspath + themepath + "_" + tplFileName + ".css";
/*     */ 
/* 590 */       FileUtil.write(csspath, cssContent);
/*     */ 
/* 592 */       if (logger.isDebugEnabled()) {
/* 593 */         logger.debug("create css to -> [" + csspath + "]");
/*     */       }
/*     */ 
/* 597 */       setCssCreated(tplFileName);
/*     */     }
/*     */ 
/* 607 */     if ((!isImageCreated()) || (newDisploy)) {
/* 608 */       String src = getResSourcePath() + "/themes/" + themepath + "/images/";
/* 609 */       String disk = target + "/images/";
/*     */ 
/* 612 */       if (logger.isDebugEnabled()) {
/* 613 */         logger.debug("copy images to -> [" + disk + "]");
/*     */       }
/*     */ 
/* 616 */       FileUtil.copyNewFile(src, disk);
/* 617 */       setImageCreated(pageid);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static String getResSourcePath()
/*     */   {
/* 630 */     String path = EopSetting.EOP_PATH;
/*     */ 
/* 632 */     if (path.endsWith("/")) path = path.substring(0, path.length() - 1);
/*     */ 
/* 636 */     path = path + EopContext.getContext().getContextPath();
/* 637 */     return path;
/*     */   }
/*     */ 
/*     */   public static String getNotMergeResource()
/*     */   {
/* 649 */     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
/* 650 */     EopContext ectx = EopContext.getContext();
/* 651 */     String domain = ectx.getResDomain();
/*     */ 
/* 653 */     List scriptList = (List)request.getAttribute("headerScriptList");
/* 654 */     StringBuffer sb = new StringBuffer();
/*     */ 
/* 656 */     if (scriptList != null) {
/* 657 */       for (Resource script : scriptList) {
/* 658 */         if (script.getMerge() == 0) {
/* 659 */           String src = script.getSrc();
/* 660 */           if (!src.startsWith("/")) {
/* 661 */             src = "/" + src;
/*     */           }
/* 663 */           String path = domain + "/themes/" + ectx.getCurrentSite().getThemepath() + src;
/* 664 */           sb.append("<script src=\"" + path + "\"  type=\"text/javascript\"></script>");
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 669 */     scriptList = (List)request.getAttribute("headerCssList");
/* 670 */     if (scriptList != null) {
/* 671 */       for (Resource script : scriptList) {
/* 672 */         if (script.getMerge() == 0) {
/* 673 */           String src = script.getSrc();
/* 674 */           if (!src.startsWith("/")) {
/* 675 */             src = "/" + src;
/*     */           }
/* 677 */           String path = domain + "/themes/" + ectx.getCurrentSite().getThemepath() + src;
/* 678 */           sb.append("<link  href=\"" + path + "\"  rel=\"stylesheet\" type=\"text/css\" />");
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 683 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   private static String readresource(Resource resource)
/*     */     throws EvaluatorException, IOException
/*     */   {
/* 690 */     StringWriter result = new StringWriter();
/* 691 */     String src = resource.getSrc();
/* 692 */     if (!src.startsWith("/")) src = "/" + src;
/* 693 */     EopContext ctx = EopContext.getContext();
/* 694 */     String respath = getResSourcePath();
/* 695 */     src = respath + "/themes/" + ctx.getCurrentSite().getThemepath() + src;
/*     */ 
/* 699 */     String content = FileUtil.read(src, "UTF-8");
/*     */ 
/* 703 */     if ((EopSetting.SCRIPT_COMPRESS) && (resource.getCompress() == 1)) {
/* 704 */       SystemOutErrorReporter reporter = new SystemOutErrorReporter();
/* 705 */       if ("javascript".equals(resource.getType()))
/*     */       {
/* 707 */         JavaScriptCompressor compressor = new JavaScriptCompressor(new StringReader(content), reporter);
/* 708 */         compressor.compress(result, -1, true, false, false, false);
/*     */ 
/* 710 */         return result.toString();
/*     */       }
/*     */ 
/* 714 */       if ("css".equals(resource.getType()))
/*     */       {
/* 716 */         CssCompressor compressor = new CssCompressor(new StringReader(content));
/* 717 */         compressor.compress(result, -1);
/*     */ 
/* 719 */         return result.toString();
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 725 */     return content;
/*     */   }
/*     */ 
/*     */   private static String readFromDisk(String type)
/*     */     throws EvaluatorException, IOException
/*     */   {
/* 741 */     StringBuffer sb = new StringBuffer();
/* 742 */     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
/* 743 */     List scriptList = (List)request.getAttribute(type);
/* 744 */     if (scriptList != null) {
/* 745 */       for (Resource script : scriptList) {
/* 746 */         String src = script.getSrc();
/* 747 */         if (script.getMerge() == 1)
/*     */         {
/* 749 */           sb.append(readresource(script));
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 755 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static void putScript(Resource resource)
/*     */   {
/* 760 */     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
/* 761 */     if (resource.isIscommon()) {
/* 762 */       List scriptList = (List)request.getAttribute("headerScriptCommonList");
/* 763 */       scriptList = scriptList == null ? new ArrayList() : scriptList;
/* 764 */       scriptList.add(resource);
/* 765 */       request.setAttribute("headerScriptCommonList", scriptList);
/*     */     } else {
/* 767 */       List scriptList = (List)request.getAttribute("headerScriptList");
/* 768 */       scriptList = scriptList == null ? new ArrayList() : scriptList;
/* 769 */       scriptList.add(resource);
/* 770 */       request.setAttribute("headerScriptList", scriptList);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void putCss(Resource resource) {
/* 775 */     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
/* 776 */     if (resource.isIscommon()) {
/* 777 */       List cssList = (List)request.getAttribute("headerCssCommonList");
/* 778 */       cssList = cssList == null ? new ArrayList() : cssList;
/* 779 */       cssList.add(resource);
/* 780 */       request.setAttribute("headerCssCommonList", cssList);
/*     */     } else {
/* 782 */       List cssList = (List)request.getAttribute("headerCssList");
/* 783 */       cssList = cssList == null ? new ArrayList() : cssList;
/* 784 */       cssList.add(resource);
/* 785 */       request.setAttribute("headerCssList", cssList);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void main(String[] args)
/*     */     throws IOException
/*     */   {
/* 792 */     String content = FileUtil.read("d:/style.css", "UTF-8");
/* 793 */     StringWriter result = new StringWriter();
/* 794 */     SystemOutErrorReporter reporter = new SystemOutErrorReporter();
/* 795 */     CssCompressor compressor = new CssCompressor(new StringReader(content));
/* 796 */     compressor.compress(result, -1);
/* 797 */     System.out.println(result.toString());
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.framework.resource.impl.ResourceBuilder
 * JD-Core Version:    0.6.0
 */