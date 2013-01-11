/*    */ package com.enation.app.shop.component.receipt;
/*    */ 
/*    */ import com.enation.app.base.core.service.dbsolution.DBSolutionFactory;
/*    */ import com.enation.framework.component.IComponent;
/*    */ import org.springframework.stereotype.Component;
/*    */ 
/*    */ @Component
/*    */ public class ReceiptComponent
/*    */   implements IComponent
/*    */ {
/*    */   public void install()
/*    */   {
/* 18 */     DBSolutionFactory.dbImport("file:com/enation/app/shop/component/receipt/receipt_install.xml", "es_");
/*    */   }
/*    */ 
/*    */   public void unInstall()
/*    */   {
/* 23 */     DBSolutionFactory.dbImport("file:com/enation/app/shop/component/receipt/receipt_uninstall.xml", "es_");
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.receipt.ReceiptComponent
 * JD-Core Version:    0.6.0
 */