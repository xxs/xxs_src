/*    */ package com.enation.app.shop.component.widget.search;
/*    */ 
/*    */ import com.enation.eop.sdk.context.EopSetting;
/*    */ import com.enation.eop.sdk.widget.AbstractWidget;
/*    */ import com.enation.framework.context.webcontext.ThreadContextHolder;
/*    */ import com.enation.framework.util.StringUtil;
/*    */ import java.util.Map;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import org.springframework.context.annotation.Scope;
/*    */ import org.springframework.stereotype.Component;
/*    */ 
/*    */ @Component("searchBar")
/*    */ @Scope("prototype")
/*    */ public class SearchBarWidget extends AbstractWidget
/*    */ {
/*    */   protected void config(Map<String, String> params)
/*    */   {
/*    */   }
/*    */ 
/*    */   protected void display(Map<String, String> params)
/*    */   {
/* 31 */     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
/* 32 */     String keyword = (String)request.getAttribute("keyword");
/*    */ 
/* 34 */     String encoding = EopSetting.ENCODING;
/* 35 */     if (!StringUtil.isEmpty(encoding)) {
/* 36 */       keyword = StringUtil.to(keyword, encoding);
/*    */     }
/*    */ 
/* 39 */     putData("keyword", keyword);
/* 40 */     setPageName("search_bar");
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.widget.search.SearchBarWidget
 * JD-Core Version:    0.6.0
 */