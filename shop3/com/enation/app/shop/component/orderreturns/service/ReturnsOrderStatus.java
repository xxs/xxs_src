/*    */ package com.enation.app.shop.component.orderreturns.service;
/*    */ 
/*    */ public abstract class ReturnsOrderStatus
/*    */ {
/*    */   public static final int APPLY_SUB = 0;
/*    */   public static final int APPLY_REFUSE = 1;
/*    */   public static final int APPLY_PASSED = 2;
/*    */   public static final int GOODS_REC = 3;
/*    */   public static final int APPLY_END = 4;
/*    */ 
/*    */   public static String getOrderStatusText(int status)
/*    */   {
/* 37 */     String text = "";
/* 38 */     switch (status) {
/*    */     case 0:
/* 40 */       text = "申请已提交";
/* 41 */       break;
/*    */     case 1:
/* 43 */       text = "已拒绝 ";
/* 44 */       break;
/*    */     case 2:
/* 46 */       text = "已通过审核";
/* 47 */       break;
/*    */     case 3:
/* 49 */       text = "已收货(换货已发货)";
/* 50 */       break;
/*    */     case 4:
/* 52 */       text = "完成";
/*    */     }
/*    */ 
/* 56 */     return text;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.orderreturns.service.ReturnsOrderStatus
 * JD-Core Version:    0.6.0
 */