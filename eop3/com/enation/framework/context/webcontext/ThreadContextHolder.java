/*    */ package com.enation.framework.context.webcontext;
/*    */ 
/*    */ import com.enation.framework.context.webcontext.impl.WebSessionContextImpl;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ public class ThreadContextHolder
/*    */ {
/* 19 */   protected static final Logger logger = Logger.getLogger(ThreadContextHolder.class);
/*    */ 
/* 21 */   private static ThreadLocal<WebSessionContext> SessionContextThreadLocalHolder = new ThreadLocal();
/* 22 */   private static ThreadLocal<HttpServletRequest> HttpRequestThreadLocalHolder = new ThreadLocal();
/* 23 */   private static ThreadLocal<HttpServletResponse> HttpResponseThreadLocalHolder = new ThreadLocal();
/*    */ 
/*    */   public static void setHttpRequest(HttpServletRequest request)
/*    */   {
/* 27 */     HttpRequestThreadLocalHolder.set(request);
/*    */   }
/*    */ 
/*    */   public static HttpServletRequest getHttpRequest() {
/* 31 */     return (HttpServletRequest)HttpRequestThreadLocalHolder.get();
/*    */   }
/*    */ 
/*    */   public static void remove()
/*    */   {
/* 37 */     SessionContextThreadLocalHolder.remove();
/* 38 */     HttpRequestThreadLocalHolder.remove();
/* 39 */     HttpResponseThreadLocalHolder.remove();
/*    */   }
/*    */ 
/*    */   public static void setHttpResponse(HttpServletResponse response)
/*    */   {
/* 44 */     HttpResponseThreadLocalHolder.set(response);
/*    */   }
/*    */ 
/*    */   public static HttpServletResponse getHttpResponse()
/*    */   {
/* 49 */     return (HttpServletResponse)HttpResponseThreadLocalHolder.get();
/*    */   }
/*    */ 
/*    */   public static void setSessionContext(WebSessionContext context)
/*    */   {
/* 55 */     SessionContextThreadLocalHolder.set(context);
/*    */   }
/*    */ 
/*    */   public static void destorySessionContext() {
/* 59 */     WebSessionContext context = (WebSessionContext)SessionContextThreadLocalHolder.get();
/* 60 */     if (context != null)
/* 61 */       context.destory();
/*    */   }
/*    */ 
/*    */   public static WebSessionContext getSessionContext()
/*    */   {
/* 66 */     if (SessionContextThreadLocalHolder.get() == null)
/*    */     {
/* 69 */       SessionContextThreadLocalHolder.set(new WebSessionContextImpl());
/*    */     }
/*    */ 
/* 74 */     return (WebSessionContext)SessionContextThreadLocalHolder.get();
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.framework.context.webcontext.ThreadContextHolder
 * JD-Core Version:    0.6.0
 */