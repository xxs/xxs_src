/*    */ package com.enation.framework.directive;
/*    */ 
/*    */ import com.enation.framework.pager.AjaxPagerDirectiveModel;
/*    */ import com.enation.framework.pager.PagerDirectiveModel;
/*    */ import freemarker.template.TemplateDirectiveModel;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class DirectiveFactory
/*    */ {
/*    */   private static Map<String, TemplateDirectiveModel> directiveMap;
/*    */ 
/*    */   public static Map<String, TemplateDirectiveModel> getCommonDirective()
/*    */   {
/* 24 */     if (directiveMap == null)
/*    */     {
/* 26 */       directiveMap = new HashMap(9);
/*    */ 
/* 32 */       TemplateDirectiveModel dateformate = new DateformateDirective();
/* 33 */       directiveMap.put("dateformat", dateformate);
/*    */ 
/* 40 */       TemplateDirectiveModel resource = new ResourceDirectiveModel();
/* 41 */       directiveMap.put("resource", resource);
/*    */ 
/* 47 */       TemplateDirectiveModel script = new ScriptDirectiveModel();
/* 48 */       directiveMap.put("script", script);
/*    */ 
/* 54 */       TemplateDirectiveModel css = new CssDirectiveModel();
/* 55 */       directiveMap.put("css", css);
/*    */ 
/* 61 */       TemplateDirectiveModel image = new ImageDirectiveModel();
/* 62 */       directiveMap.put("image", image);
/*    */ 
/* 68 */       TemplateDirectiveModel ajaxpager = new AjaxPagerDirectiveModel();
/* 69 */       directiveMap.put("ajaxpager", ajaxpager);
/*    */ 
/* 76 */       TemplateDirectiveModel pager = new PagerDirectiveModel();
/* 77 */       directiveMap.put("pager", pager);
/*    */ 
/* 83 */       TemplateDirectiveModel imgurl = new ImageUrlDirectiveModel();
/* 84 */       directiveMap.put("imgurl", imgurl);
/*    */ 
/* 91 */       TemplateDirectiveModel substring = new SubStringDirectiveModel();
/* 92 */       directiveMap.put("substring", substring);
/*    */     }
/*    */ 
/* 98 */     return directiveMap;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.framework.directive.DirectiveFactory
 * JD-Core Version:    0.6.0
 */