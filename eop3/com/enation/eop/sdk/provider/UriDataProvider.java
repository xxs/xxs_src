/*    */ package com.enation.eop.sdk.provider;
/*    */ 
/*    */ import com.enation.eop.sdk.utils.JspUtil;
/*    */ import java.util.Map;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ 
/*    */ public class UriDataProvider
/*    */   implements DataProvider
/*    */ {
/*    */   private HttpServletRequest httpRequest;
/*    */   private HttpServletResponse httpResponse;
/*    */   private String url;
/*    */ 
/*    */   public UriDataProvider(HttpServletRequest httpRequest, HttpServletResponse httpResponse, String url)
/*    */   {
/* 27 */     this.httpRequest = httpRequest;
/* 28 */     this.httpResponse = httpResponse;
/* 29 */     this.url = url;
/*    */   }
/*    */ 
/*    */   public Object load(Map<String, String> params)
/*    */   {
/* 40 */     String content = JspUtil.getJspOutput(this.url, this.httpRequest, this.httpResponse);
/*    */ 
/* 42 */     return content;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.eop.sdk.provider.UriDataProvider
 * JD-Core Version:    0.6.0
 */