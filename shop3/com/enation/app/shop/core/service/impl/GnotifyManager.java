/*    */ package com.enation.app.shop.core.service.impl;
/*    */ 
/*    */ import com.enation.app.base.core.model.Member;
/*    */ import com.enation.app.shop.core.model.Gnotify;
/*    */ import com.enation.app.shop.core.service.IGnotifyManager;
/*    */ import com.enation.eop.sdk.database.BaseSupport;
/*    */ import com.enation.eop.sdk.user.IUserService;
/*    */ import com.enation.eop.sdk.user.UserServiceFactory;
/*    */ import com.enation.framework.database.IDaoSupport;
/*    */ import com.enation.framework.database.Page;
/*    */ 
/*    */ public class GnotifyManager extends BaseSupport
/*    */   implements IGnotifyManager
/*    */ {
/*    */   public Page pageGnotify(int pageNo, int pageSize)
/*    */   {
/* 21 */     IUserService userService = UserServiceFactory.getUserService();
/* 22 */     Member member = userService.getCurrentMember();
/*    */ 
/* 24 */     String sql = "select a.*,b.sn, b.image_default image,b.store store, b.name name, b.price price, b.mktprice mktprice,b.cat_id cat_id from " + getTableName("gnotify") + " a inner join " + getTableName("goods") + " b on b.goods_id = a.goods_id";
/*    */ 
/* 26 */     sql = sql + " and a.member_id = " + member.getMember_id();
/* 27 */     Page webpage = this.daoSupport.queryForPage(sql, pageNo, pageSize, new Object[0]);
/* 28 */     return webpage;
/*    */   }
/*    */ 
/*    */   public void deleteGnotify(int gnotify_id)
/*    */   {
/* 33 */     this.baseDaoSupport.execute("delete from gnotify where gnotify_id = ?", new Object[] { Integer.valueOf(gnotify_id) });
/*    */   }
/*    */ 
/*    */   public void addGnotify(int goodsid)
/*    */   {
/* 38 */     String sql = "select count(0) from gnotify where goods_id=?";
/* 39 */     int count = this.baseDaoSupport.queryForInt(sql, new Object[] { Integer.valueOf(goodsid) });
/* 40 */     if (count > 0) return;
/*    */ 
/* 43 */     IUserService userService = UserServiceFactory.getUserService();
/* 44 */     Member member = null;
/* 45 */     if (userService != null) {
/* 46 */       member = userService.getCurrentMember();
/*    */     }
/* 48 */     Gnotify gnotify = new Gnotify();
/* 49 */     gnotify.setCreate_time(Long.valueOf(System.currentTimeMillis()));
/* 50 */     gnotify.setGoods_id(goodsid);
/* 51 */     if (member != null) {
/* 52 */       gnotify.setMember_id(member.getMember_id().intValue());
/* 53 */       gnotify.setEmail(member.getEmail());
/*    */     }
/* 55 */     this.baseDaoSupport.insert("gnotify", gnotify);
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.service.impl.GnotifyManager
 * JD-Core Version:    0.6.0
 */