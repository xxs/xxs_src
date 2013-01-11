/*    */ package com.enation.app.shop.component.receipt.service;
/*    */ 
/*    */ import com.enation.app.shop.component.receipt.Receipt;
/*    */ import com.enation.eop.sdk.database.BaseSupport;
/*    */ import com.enation.framework.database.IDaoSupport;
/*    */ import org.springframework.stereotype.Component;
/*    */ import org.springframework.transaction.annotation.Propagation;
/*    */ import org.springframework.transaction.annotation.Transactional;
/*    */ 
/*    */ @Component
/*    */ public class ReceiptManager extends BaseSupport<Receipt>
/*    */   implements IReceiptManager
/*    */ {
/*    */   @Transactional(propagation=Propagation.REQUIRED)
/*    */   public void add(Receipt invoice)
/*    */   {
/* 24 */     invoice.setAdd_time(Long.valueOf(System.currentTimeMillis()));
/* 25 */     this.baseDaoSupport.insert("receipt", invoice);
/*    */   }
/*    */ 
/*    */   public Receipt getByOrderid(Integer orderid)
/*    */   {
/* 32 */     return (Receipt)this.baseDaoSupport.queryForObject("select * from receipt where order_id = ? ", Receipt.class, new Object[] { orderid });
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.receipt.service.ReceiptManager
 * JD-Core Version:    0.6.0
 */