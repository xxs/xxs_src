/*    */ package com.enation.app.shop.core.service.impl;
/*    */ 
/*    */ import com.enation.app.shop.core.model.StoreLog;
/*    */ import com.enation.app.shop.core.service.IStoreLogManager;
/*    */ import com.enation.eop.sdk.database.BaseSupport;
/*    */ import com.enation.framework.database.IDaoSupport;
/*    */ 
/*    */ public class StoreLogManager extends BaseSupport<StoreLog>
/*    */   implements IStoreLogManager
/*    */ {
/*    */   public void add(StoreLog storeLog)
/*    */   {
/* 17 */     this.baseDaoSupport.insert("store_log", storeLog);
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.service.impl.StoreLogManager
 * JD-Core Version:    0.6.0
 */