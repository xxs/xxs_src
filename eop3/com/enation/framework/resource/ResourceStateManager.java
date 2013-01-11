/*    */ package com.enation.framework.resource;
/*    */ 
/*    */ import com.enation.eop.resource.model.EopSite;
/*    */ import com.enation.eop.sdk.context.EopContext;
/*    */ import com.enation.eop.sdk.context.EopSetting;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class ResourceStateManager
/*    */ {
/*    */   private static int DISPLOY_STATE;
/* 31 */   private static Map<String, String> disployStateMap = new HashMap();
/*    */ 
/*    */   public static boolean getHaveNewDisploy()
/*    */   {
/* 39 */     if ("2".equals(EopSetting.RUNMODE)) {
/* 40 */       EopSite site = EopContext.getContext().getCurrentSite();
/* 41 */       return "1".equals(disployStateMap.get(site.getUserid() + "_" + site.getId()));
/*    */     }
/* 43 */     return DISPLOY_STATE == 1;
/*    */   }
/*    */ 
/*    */   public static void setDisplayState(int state)
/*    */   {
/* 54 */     if ("2".equals(EopSetting.RUNMODE)) {
/* 55 */       EopSite site = EopContext.getContext().getCurrentSite();
/* 56 */       disployStateMap.put(site.getUserid() + "_" + site.getId(), "" + state);
/*    */     } else {
/* 58 */       DISPLOY_STATE = state;
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.framework.resource.ResourceStateManager
 * JD-Core Version:    0.6.0
 */