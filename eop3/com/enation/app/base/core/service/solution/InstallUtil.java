/*    */ package com.enation.app.base.core.service.solution;
/*    */ 
/*    */ import com.enation.framework.context.webcontext.ThreadContextHolder;
/*    */ import com.enation.framework.context.webcontext.WebSessionContext;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ public class InstallUtil
/*    */ {
/*    */   public static void putMessaage(String msg)
/*    */   {
/* 10 */     if (ThreadContextHolder.getSessionContext() != null) {
/* 11 */       List msgList = (List)ThreadContextHolder.getSessionContext().getAttribute("installMsg");
/* 12 */       if (msgList == null) {
/* 13 */         msgList = new ArrayList();
/*    */       }
/* 15 */       msgList.add(msg);
/* 16 */       ThreadContextHolder.getSessionContext().setAttribute("installMsg", msgList);
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.app.base.core.service.solution.InstallUtil
 * JD-Core Version:    0.6.0
 */