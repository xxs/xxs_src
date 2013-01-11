/*    */ package com.enation.app.shop.core.service.impl;
/*    */ 
/*    */ import com.enation.app.base.core.model.Member;
/*    */ import com.enation.app.shop.core.model.Favorite;
/*    */ import com.enation.app.shop.core.service.IFavoriteManager;
/*    */ import com.enation.eop.sdk.database.BaseSupport;
/*    */ import com.enation.eop.sdk.user.IUserService;
/*    */ import com.enation.eop.sdk.user.UserServiceFactory;
/*    */ import com.enation.framework.database.IDaoSupport;
/*    */ import com.enation.framework.database.Page;
/*    */ import java.util.List;
/*    */ 
/*    */ public class FavoriteManager extends BaseSupport
/*    */   implements IFavoriteManager
/*    */ {
/*    */   public Page list(int pageNo, int pageSize)
/*    */   {
/* 26 */     IUserService userService = UserServiceFactory.getUserService();
/* 27 */     Member member = userService.getCurrentMember();
/* 28 */     String sql = "select g.*, f.favorite_id from " + getTableName("favorite") + " f left join " + getTableName("goods") + " g on g.goods_id = f.goods_id";
/*    */ 
/* 31 */     sql = sql + " where f.member_id = ?";
/* 32 */     Page page = this.daoSupport.queryForPage(sql, pageNo, pageSize, new Object[] { member.getMember_id() });
/*    */ 
/* 34 */     return page;
/*    */   }
/*    */ 
/*    */   public Page list(int memberid, int pageNo, int pageSize)
/*    */   {
/* 40 */     String sql = "select g.*, f.favorite_id from " + getTableName("favorite") + " f left join " + getTableName("goods") + " g on g.goods_id = f.goods_id";
/*    */ 
/* 43 */     sql = sql + " where f.member_id = ?";
/* 44 */     Page page = this.daoSupport.queryForPage(sql, pageNo, pageSize, new Object[] { Integer.valueOf(memberid) });
/* 45 */     return page;
/*    */   }
/*    */ 
/*    */   public void delete(int favorite_id)
/*    */   {
/* 51 */     this.baseDaoSupport.execute("delete from favorite where favorite_id = ?", new Object[] { Integer.valueOf(favorite_id) });
/*    */   }
/*    */ 
/*    */   public void add(Integer goodsid)
/*    */   {
/* 56 */     IUserService userService = UserServiceFactory.getUserService();
/* 57 */     Member member = userService.getCurrentMember();
/* 58 */     Favorite favorite = new Favorite();
/* 59 */     favorite.setGoods_id(goodsid.intValue());
/* 60 */     favorite.setMember_id(member.getMember_id().intValue());
/* 61 */     this.baseDaoSupport.insert("favorite", favorite);
/*    */   }
/*    */ 
/*    */   public int getCount(Integer goodsid, Integer memeberid)
/*    */   {
/* 68 */     return this.baseDaoSupport.queryForInt("SELECT COUNT(0) FROM favorite WHERE goods_id=? AND member_id=?", new Object[] { goodsid, memeberid });
/*    */   }
/*    */ 
/*    */   public int getCount(Integer memberId) {
/* 72 */     return this.baseDaoSupport.queryForInt("SELECT COUNT(0) FROM favorite WHERE  member_id=?", new Object[] { memberId });
/*    */   }
/*    */ 
/*    */   public List list()
/*    */   {
/* 77 */     IUserService userService = UserServiceFactory.getUserService();
/* 78 */     Member member = userService.getCurrentMember();
/*    */ 
/* 80 */     return this.baseDaoSupport.queryForList("select * from favorite where member_id=?", Favorite.class, new Object[] { member.getMember_id() });
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.service.impl.FavoriteManager
 * JD-Core Version:    0.6.0
 */