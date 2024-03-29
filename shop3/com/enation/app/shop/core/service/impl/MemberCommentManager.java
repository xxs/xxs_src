/*    */ package com.enation.app.shop.core.service.impl;
/*    */ 
/*    */ import com.enation.app.shop.core.model.MemberComment;
/*    */ import com.enation.app.shop.core.service.IMemberCommentManager;
/*    */ import com.enation.eop.sdk.database.BaseSupport;
/*    */ import com.enation.framework.database.IDaoSupport;
/*    */ import com.enation.framework.database.Page;
/*    */ 
/*    */ public class MemberCommentManager extends BaseSupport<MemberComment>
/*    */   implements IMemberCommentManager
/*    */ {
/*    */   public void add(MemberComment memberComment)
/*    */   {
/* 15 */     this.baseDaoSupport.insert("member_comment", memberComment);
/*    */   }
/*    */ 
/*    */   public Page getGoodsComments(int goods_id, int page, int pageSize, int type)
/*    */   {
/* 20 */     return this.daoSupport.queryForPage("SELECT m.lv_id,m.sex,m.uname,m.face,l.name as levelname,c.* FROM " + getTableName("member_comment") + " c LEFT JOIN " + getTableName("member") + " m ON c.member_id=m.member_id LEFT JOIN " + getTableName("member_lv") + " l ON m.lv_id=l.lv_id " + "WHERE c.goods_id=? AND c.type=? AND c.status=1 ORDER BY c.comment_id DESC", page, pageSize, new Object[] { Integer.valueOf(goods_id), Integer.valueOf(type) });
/*    */   }
/*    */ 
/*    */   public int getGoodsGrade(int goods_id)
/*    */   {
/* 25 */     int sumGrade = this.baseDaoSupport.queryForInt("SELECT SUM(grade) FROM member_comment WHERE status=1 AND goods_id=? AND type=1", new Object[] { Integer.valueOf(goods_id) });
/* 26 */     int total = this.baseDaoSupport.queryForInt("SELECT COUNT(0) FROM member_comment WHERE status=1 AND goods_id=? AND type=1", new Object[] { Integer.valueOf(goods_id) });
/* 27 */     if ((sumGrade != 0) && (total != 0)) {
/* 28 */       return sumGrade / total;
/*    */     }
/* 30 */     return 0;
/*    */   }
/*    */ 
/*    */   public Page getAllComments(int page, int pageSize, int type)
/*    */   {
/* 35 */     return this.daoSupport.queryForPage("SELECT m.uname,g.name,c.* FROM " + getTableName("member_comment") + " c LEFT JOIN " + getTableName("goods") + " g ON c.goods_id=g.goods_id LEFT JOIN " + getTableName("member") + " m ON c.member_id=m.member_id " + "WHERE c.type=? ORDER BY c.comment_id DESC", page, pageSize, new Object[] { Integer.valueOf(type) });
/*    */   }
/*    */ 
/*    */   public MemberComment get(int comment_id)
/*    */   {
/* 41 */     return (MemberComment)this.baseDaoSupport.queryForObject("SELECT * FROM member_comment WHERE comment_id=?", MemberComment.class, new Object[] { Integer.valueOf(comment_id) });
/*    */   }
/*    */ 
/*    */   public void update(MemberComment memberComment)
/*    */   {
/* 49 */     this.baseDaoSupport.update("member_comment", memberComment, "comment_id=" + memberComment.getComment_id());
/*    */   }
/*    */ 
/*    */   public int getGoodsCommentsCount(int goods_id)
/*    */   {
/* 54 */     return this.baseDaoSupport.queryForInt("SELECT COUNT(0) FROM member_comment WHERE goods_id=? AND status=1 AND type=1", new Object[] { Integer.valueOf(goods_id) });
/*    */   }
/*    */ 
/*    */   public void delete(int comment_id)
/*    */   {
/* 59 */     this.baseDaoSupport.execute("DELETE FROM member_comment WHERE comment_id=?", new Object[] { Integer.valueOf(comment_id) });
/*    */   }
/*    */ 
/*    */   public Page getMemberComments(int page, int pageSize, int type, int member_id)
/*    */   {
/* 65 */     return this.daoSupport.queryForPage("SELECT g.name,g.cat_id, c.* FROM " + getTableName("member_comment") + " c LEFT JOIN " + getTableName("goods") + " g ON c.goods_id=g.goods_id " + "WHERE c.type=? AND c.member_id=? ORDER BY c.comment_id DESC", page, pageSize, new Object[] { Integer.valueOf(type), Integer.valueOf(member_id) });
/*    */   }
/*    */ 
/*    */   public int getMemberCommentTotal(int member_id, int type)
/*    */   {
/* 71 */     return this.baseDaoSupport.queryForInt("SELECT COUNT(0) FROM member_comment WHERE member_id=? AND type=?", new Object[] { Integer.valueOf(member_id), Integer.valueOf(type) });
/*    */   }
/*    */ 
/*    */   public Page getCommentsByStatus(int page, int pageSize, int type, int status)
/*    */   {
/* 76 */     return this.daoSupport.queryForPage("SELECT m.uname,g.name,c.* FROM " + getTableName("member_comment") + " c LEFT JOIN " + getTableName("goods") + " g ON c.goods_id=g.goods_id LEFT JOIN " + getTableName("member") + " m ON c.member_id=m.member_id " + "WHERE c.type=? and c.status = ? ORDER BY c.comment_id DESC", page, pageSize, new Object[] { Integer.valueOf(type), Integer.valueOf(status) });
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.service.impl.MemberCommentManager
 * JD-Core Version:    0.6.0
 */