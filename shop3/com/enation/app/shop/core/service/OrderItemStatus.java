/*    */ package com.enation.app.shop.core.service;
/*    */ 
/*    */ public abstract class OrderItemStatus
/*    */ {
/*    */   public static final int NORMAL = 0;
/*    */   public static final int APPLY_RETURN = 1;
/*    */   public static final int APPLY_CHANGE = 2;
/*    */   public static final int REFUSE_RETURN = 3;
/*    */   public static final int REFUSE_CHANGE = 4;
/*    */   public static final int RETURN_PASSED = 5;
/*    */   public static final int CHANGE_PASSED = 6;
/*    */   public static final int RETURN_REC = 7;
/*    */   public static final int CHANGE_REC = 8;
/*    */   public static final int RETURN_END = 9;
/*    */   public static final int CHANGE_END = 10;
/*    */ 
/*    */   public static String getOrderStatusText(int status)
/*    */   {
/* 48 */     String text = "";
/* 49 */     switch (status) {
/*    */     case 0:
/* 51 */       text = "正常";
/* 52 */       break;
/*    */     case 1:
/* 54 */       text = "申请退货";
/* 55 */       break;
/*    */     case 2:
/* 57 */       text = "申请换货 ";
/* 58 */       break;
/*    */     case 3:
/* 60 */       text = "退货已拒绝";
/* 61 */       break;
/*    */     case 4:
/* 63 */       text = "换货已拒绝";
/* 64 */       break;
/*    */     case 5:
/* 66 */       text = "退货已通过审核";
/* 67 */       break;
/*    */     case 6:
/* 69 */       text = "换货已通过审核";
/* 70 */       break;
/*    */     case 7:
/* 72 */       text = "退货已收货";
/* 73 */       break;
/*    */     case 8:
/* 75 */       text = "已收货,换货已发出";
/* 76 */       break;
/*    */     case 9:
/* 78 */       text = "退货完成";
/* 79 */       break;
/*    */     case 10:
/* 81 */       text = "换货完成";
/*    */     }
/*    */ 
/* 84 */     return text;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.service.OrderItemStatus
 * JD-Core Version:    0.6.0
 */