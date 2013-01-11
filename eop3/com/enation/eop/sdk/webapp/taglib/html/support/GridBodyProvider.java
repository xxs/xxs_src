/*    */ package com.enation.eop.sdk.webapp.taglib.html.support;
/*    */ 
/*    */ import com.enation.eop.sdk.webapp.bean.Grid;
/*    */ import com.enation.eop.sdk.webapp.taglib.IListTaglibParam;
/*    */ import com.enation.eop.sdk.webapp.taglib.IListTaglibProvider;
/*    */ import com.enation.framework.database.Page;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import javax.servlet.ServletRequest;
/*    */ import javax.servlet.jsp.PageContext;
/*    */ 
/*    */ public class GridBodyProvider
/*    */   implements IListTaglibProvider
/*    */ {
/*    */   public List getData(IListTaglibParam _param, PageContext pageContext)
/*    */   {
/* 15 */     GridBodyParam param = (GridBodyParam)_param;
/* 16 */     String from = param.getFrom();
/*    */ 
/* 18 */     Object obj = pageContext.getAttribute(from);
/* 19 */     if (obj == null) {
/* 20 */       obj = pageContext.getRequest().getAttribute(from);
/* 21 */       if (obj == null) {
/* 22 */         return new ArrayList();
/*    */       }
/*    */     }
/*    */ 
/* 26 */     Page page = null;
/* 27 */     List list = null;
/* 28 */     if ((obj instanceof Page)) {
/* 29 */       page = (Page)obj;
/* 30 */       list = (List)page.getResult();
/*    */     }
/* 32 */     else if ((obj instanceof Grid)) {
/* 33 */       page = ((Grid)obj).getWebpage();
/* 34 */       list = (List)page.getResult();
/* 35 */     } else if ((obj instanceof List)) {
/* 36 */       list = (List)obj;
/*    */     }
/*    */ 
/* 39 */     return list;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.eop.sdk.webapp.taglib.html.support.GridBodyProvider
 * JD-Core Version:    0.6.0
 */