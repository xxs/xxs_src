/*    */ package com.enation.app.shop.core.service.impl;
/*    */ 
/*    */ import com.enation.app.shop.core.model.MemberOrderItem;
/*    */ import com.enation.app.shop.core.service.IMemberOrderItemManager;
/*    */ import com.enation.eop.sdk.database.BaseSupport;
/*    */ import com.enation.framework.database.IDaoSupport;
/*    */ import com.enation.framework.database.Page;
/*    */ import java.util.List;
/*    */ 
/*    */ public class MemberOrderItemManager extends BaseSupport
/*    */   implements IMemberOrderItemManager
/*    */ {
/*    */   public void add(MemberOrderItem memberOrderItem)
/*    */   {
/* 14 */     this.baseDaoSupport.execute("INSERT INTO member_order_item(member_id,goods_id,order_id,item_id,commented,comment_time) VALUES(?,?,?,?,0,0)", new Object[] { memberOrderItem.getMember_id(), memberOrderItem.getGoods_id(), memberOrderItem.getOrder_id(), memberOrderItem.getItem_id() });
/*    */   }
/*    */ 
/*    */   public int count(int member_id, int goods_id, int commented)
/*    */   {
/* 20 */     return this.baseDaoSupport.queryForInt("SELECT COUNT(0) FROM member_order_item WHERE member_id=? AND goods_id=? AND commented=?", new Object[] { Integer.valueOf(member_id), Integer.valueOf(goods_id), Integer.valueOf(commented) });
/*    */   }
/*    */ 
/*    */   public MemberOrderItem get(int member_id, int goods_id, int commented)
/*    */   {
/* 25 */     List list = this.baseDaoSupport.queryForList("SELECT * FROM member_order_item WHERE member_id=? AND goods_id=? AND commented=?", MemberOrderItem.class, new Object[] { Integer.valueOf(member_id), Integer.valueOf(goods_id), Integer.valueOf(commented) });
/*    */ 
/* 27 */     if ((list != null) && (list.size() > 0))
/* 28 */       return (MemberOrderItem)list.get(0);
/* 29 */     return null;
/*    */   }
/*    */ 
/*    */   public int count(int member_id, int goods_id) {
/* 33 */     return this.baseDaoSupport.queryForInt("SELECT COUNT(0) FROM member_order_item WHERE member_id=? AND goods_id=?", new Object[] { Integer.valueOf(member_id), Integer.valueOf(goods_id) });
/*    */   }
/*    */ 
/*    */   public void update(MemberOrderItem memberOrderItem)
/*    */   {
/* 41 */     this.baseDaoSupport.update("member_order_item", memberOrderItem, "id=" + memberOrderItem.getId());
/*    */   }
/*    */ 
/*    */   public Page getGoodsList(int member_id, int commented, int pageNo, int pageSize) {
/* 45 */     String sql = "SELECT g.* FROM " + getTableName("member_order_item") + " m LEFT JOIN " + getTableName("goods") + " g ON m.goods_id=g.goods_id WHERE m.member_id=? AND m.commented=? ORDER BY m.id DESC";
/* 46 */     return this.daoSupport.queryForPage(sql, pageNo, pageSize, new Object[] { Integer.valueOf(member_id), Integer.valueOf(commented) });
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.service.impl.MemberOrderItemManager
 * JD-Core Version:    0.6.0
 */