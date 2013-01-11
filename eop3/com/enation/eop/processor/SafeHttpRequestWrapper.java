/*    */ package com.enation.eop.processor;
/*    */ 
/*    */ import java.util.Iterator;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletRequestWrapper;
/*    */ 
/*    */ public class SafeHttpRequestWrapper extends HttpServletRequestWrapper
/*    */ {
/*    */   public SafeHttpRequestWrapper(HttpServletRequest request)
/*    */   {
/* 31 */     super(request);
/*    */   }
/*    */ 
/*    */   private String safeFilter(String value)
/*    */   {
/* 40 */     if (value == null) return null;
/*    */ 
/* 42 */     value = value.replaceAll("\\'", "‘");
/* 43 */     value = value.replaceAll("--", "－－");
/* 44 */     value = value.replaceAll("\\*", "×");
/*    */ 
/* 48 */     value = value.replaceAll("<", "&lt;");
/* 49 */     return value;
/*    */   }
/*    */ 
/*    */   private void safeFilter(String[] values)
/*    */   {
/* 57 */     if (values != null)
/* 58 */       for (int i = 0; i < values.length; i++)
/* 59 */         values[i] = safeFilter(values[i]);
/*    */   }
/*    */ 
/*    */   public String getParameter(String name)
/*    */   {
/* 65 */     String value = super.getParameter(name);
/* 66 */     value = safeFilter(value);
/* 67 */     return value;
/*    */   }
/*    */ 
/*    */   public Map getParameterMap()
/*    */   {
/* 72 */     Map map = super.getParameterMap();
/* 73 */     Iterator keiter = map.keySet().iterator();
/* 74 */     while (keiter.hasNext()) {
/* 75 */       String name = keiter.next().toString();
/* 76 */       Object value = map.get(name);
/* 77 */       if ((value instanceof String)) {
/* 78 */         value = safeFilter((String)value);
/*    */       }
/* 80 */       if ((value instanceof String[])) {
/* 81 */         String[] values = (String[])(String[])value;
/* 82 */         safeFilter(values);
/*    */       }
/*    */     }
/*    */ 
/* 86 */     return map;
/*    */   }
/*    */   public String[] getParameterValues(String arg0) {
/* 89 */     String[] values = super.getParameterValues(arg0);
/* 90 */     safeFilter(values);
/* 91 */     return values;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.eop.processor.SafeHttpRequestWrapper
 * JD-Core Version:    0.6.0
 */