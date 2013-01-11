/*    */ package com.enation.eop.sdk.webapp.taglib.html;
/*    */ 
/*    */ import com.enation.eop.sdk.webapp.taglib.ListTaglibSupport;
/*    */ import com.enation.eop.sdk.webapp.taglib.html.support.GridBodyParam;
/*    */ import com.enation.eop.sdk.webapp.taglib.html.support.GridBodyProvider;
/*    */ import javax.servlet.jsp.tagext.Tag;
/*    */ 
/*    */ public class GridBodyTaglib extends ListTaglibSupport
/*    */ {
/*    */   private GridBodyProvider gridBodyProvider;
/*    */ 
/*    */   public GridBodyTaglib()
/*    */   {
/* 14 */     this.gridBodyProvider = new GridBodyProvider();
/*    */   }
/*    */ 
/*    */   protected void loadProvider()
/*    */   {
/* 19 */     Tag tag = getParent();
/*    */ 
/* 21 */     if (tag != null) {
/* 22 */       GridTaglib gridTaglib = (GridTaglib)tag;
/* 23 */       GridBodyParam bodyparm = new GridBodyParam();
/* 24 */       bodyparm.setFrom(gridTaglib.getFrom());
/* 25 */       this.param = bodyparm;
/*    */ 
/* 27 */       this.tagProvider = this.gridBodyProvider;
/*    */     } else {
/* 29 */       print("body tag must be included in grid tag");
/*    */     }
/*    */   }
/*    */ 
/*    */   protected String postStart()
/*    */   {
/* 35 */     return "<tr>";
/*    */   }
/*    */ 
/*    */   protected String postEnd()
/*    */   {
/* 40 */     return "</tr>";
/*    */   }
/*    */ 
/*    */   protected String postStartOnce()
/*    */   {
/* 45 */     return "<tbody>";
/*    */   }
/*    */ 
/*    */   protected String postEndOnce()
/*    */   {
/* 50 */     return "</tbody>";
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.eop.sdk.webapp.taglib.html.GridBodyTaglib
 * JD-Core Version:    0.6.0
 */