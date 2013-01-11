/*    */ package com.enation.app.shop.component.goodscore.widget.goods.search;
/*    */ 
/*    */ import com.enation.app.shop.core.utils.UrlUtils;
/*    */ import com.enation.framework.context.webcontext.ThreadContextHolder;
/*    */ import com.enation.framework.util.StringUtil;
/*    */ import freemarker.core.Environment;
/*    */ import freemarker.template.TemplateDirectiveBody;
/*    */ import freemarker.template.TemplateDirectiveModel;
/*    */ import freemarker.template.TemplateException;
/*    */ import freemarker.template.TemplateModel;
/*    */ import java.io.IOException;
/*    */ import java.io.Writer;
/*    */ import java.util.Map;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ 
/*    */ public class SearchUrlDirectiveModel
/*    */   implements TemplateDirectiveModel
/*    */ {
/*    */   public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
/*    */     throws TemplateException, IOException
/*    */   {
/* 24 */     String type = params.get("type").toString();
/* 25 */     String value = params.get("value").toString();
/*    */ 
/* 28 */     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
/* 29 */     String url = request.getServletPath();
/* 30 */     String encode = request.getCharacterEncoding();
/* 31 */     if (!"UTF-8".equals(encode)) {
/* 32 */       url = StringUtil.toUTF8(url);
/*    */     }
/* 34 */     url = UrlUtils.getParamStr(url);
/*    */ 
/* 36 */     if ("all".equals(value)) {
/* 37 */       if ("brand".equals(type)) {
/* 38 */         url = UrlUtils.getExParamUrl(url, type);
/* 39 */       } else if ("prop".equals(type)) {
/* 40 */         String index = params.get("index").toString();
/* 41 */         url = UrlUtils.getPropExSelf(Integer.valueOf(index).intValue(), url);
/*    */       }
/*    */     } else {
/* 44 */       if (!type.equals("prop"))
/* 45 */         url = UrlUtils.getExParamUrl(url, type);
/* 46 */       url = UrlUtils.appendParamValue(url, type, value);
/*    */     }
/* 48 */     env.getOut().write("search-" + url + ".html");
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.goodscore.widget.goods.search.SearchUrlDirectiveModel
 * JD-Core Version:    0.6.0
 */