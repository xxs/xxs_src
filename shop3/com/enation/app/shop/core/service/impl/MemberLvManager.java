/*     */ package com.enation.app.shop.core.service.impl;
/*     */ 
/*     */ import com.enation.app.base.core.model.MemberLv;
/*     */ import com.enation.app.shop.core.service.IMemberLvManager;
/*     */ import com.enation.eop.sdk.database.BaseSupport;
/*     */ import com.enation.framework.database.IDaoSupport;
/*     */ import com.enation.framework.database.Page;
/*     */ import com.enation.framework.util.StringUtil;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ public class MemberLvManager extends BaseSupport<MemberLv>
/*     */   implements IMemberLvManager
/*     */ {
/*     */   public void add(MemberLv lv)
/*     */   {
/*  21 */     this.baseDaoSupport.insert("member_lv", lv);
/*     */   }
/*     */ 
/*     */   public void edit(MemberLv lv)
/*     */   {
/*  26 */     this.baseDaoSupport.update("member_lv", lv, "lv_id=" + lv.getLv_id());
/*     */   }
/*     */ 
/*     */   public Integer getDefaultLv()
/*     */   {
/*  31 */     String sql = "select * from member_lv where default_lv=1";
/*  32 */     List lvList = this.baseDaoSupport.queryForList(sql, MemberLv.class, new Object[0]);
/*  33 */     if ((lvList == null) || (lvList.isEmpty())) {
/*  34 */       return null;
/*     */     }
/*     */ 
/*  37 */     return ((MemberLv)lvList.get(0)).getLv_id();
/*     */   }
/*     */ 
/*     */   public Page list(String order, int page, int pageSize)
/*     */   {
/*  42 */     order = order == null ? " lv_id desc" : order;
/*  43 */     String sql = "select * from member_lv ";
/*  44 */     sql = sql + " order by  " + order;
/*  45 */     Page webpage = this.baseDaoSupport.queryForPage(sql, page, pageSize, new Object[0]);
/*  46 */     return webpage;
/*     */   }
/*     */ 
/*     */   public MemberLv get(Integer lvId)
/*     */   {
/*  51 */     if ((lvId != null) && (lvId.intValue() != 0)) {
/*  52 */       String sql = "select * from member_lv where lv_id=?";
/*  53 */       MemberLv lv = (MemberLv)this.baseDaoSupport.queryForObject(sql, MemberLv.class, new Object[] { lvId });
/*     */ 
/*  55 */       return lv;
/*     */     }
/*  57 */     return null;
/*     */   }
/*     */ 
/*     */   public void delete(String id)
/*     */   {
/*  63 */     if ((id == null) || (id.equals("")))
/*  64 */       return;
/*  65 */     String sql = "delete from member_lv where lv_id in (" + id + ")";
/*  66 */     this.baseDaoSupport.execute(sql, new Object[0]);
/*     */   }
/*     */ 
/*     */   public List<MemberLv> list()
/*     */   {
/*  71 */     String sql = "select * from member_lv order by lv_id";
/*  72 */     List lvlist = this.baseDaoSupport.queryForList(sql, MemberLv.class, new Object[0]);
/*  73 */     return lvlist;
/*     */   }
/*     */ 
/*     */   public List<MemberLv> list(String ids)
/*     */   {
/*  79 */     if (StringUtil.isEmpty(ids)) return new ArrayList();
/*     */ 
/*  81 */     String sql = "select * from member_lv where  lv_id in(" + ids + ") order by lv_id";
/*  82 */     List lvlist = this.baseDaoSupport.queryForList(sql, MemberLv.class, new Object[0]);
/*  83 */     return lvlist;
/*     */   }
/*     */ 
/*     */   public MemberLv getByPoint(int point)
/*     */   {
/*  92 */     String sql = "select * from member_lv where  point<=? order by point desc";
/*  93 */     List list = this.baseDaoSupport.queryForList(sql, MemberLv.class, new Object[] { Integer.valueOf(point) });
/*  94 */     if ((list == null) || (list.isEmpty())) {
/*  95 */       return null;
/*     */     }
/*  97 */     return (MemberLv)list.get(0);
/*     */   }
/*     */ 
/*     */   public MemberLv getNextLv(int point) {
/* 101 */     String sql = "select * from member_lv where  point>? order by point ASC";
/* 102 */     List list = this.baseDaoSupport.queryForList(sql, MemberLv.class, new Object[] { Integer.valueOf(point) });
/* 103 */     if ((list == null) || (list.isEmpty())) {
/* 104 */       return null;
/*     */     }
/* 106 */     return (MemberLv)list.get(0);
/*     */   }
/*     */ 
/*     */   public List getCatDiscountByLv(int lv_id)
/*     */   {
/* 115 */     String sql = "select d.*,c.name from " + getTableName("member_lv_discount") + " d inner join " + getTableName("goods_cat") + " c on d.cat_id=c.cat_id where d.lv_id=" + lv_id;
/* 116 */     return this.daoSupport.queryForList(sql, new Object[0]);
/*     */   }
/*     */ 
/*     */   public List getHaveCatDiscountByLv(int lv_id)
/*     */   {
/* 125 */     String sql = "select * from member_lv_discount where discount>0 and lv_id=" + lv_id;
/* 126 */     return this.baseDaoSupport.queryForList(sql, new Object[0]);
/*     */   }
/*     */ 
/*     */   public void saveCatDiscountByLv(int[] cat_ids, int[] discounts, int lv_id)
/*     */   {
/* 136 */     if (cat_ids.length != discounts.length) {
/* 137 */       throw new RuntimeException("非法参数");
/*     */     }
/* 139 */     for (int i = 0; i < discounts.length; i++) {
/* 140 */       if (discounts[i] == 0)
/*     */         continue;
/* 142 */       String sql = "update member_lv_discount set discount=" + discounts[i] + " where cat_id=" + cat_ids[i] + " and lv_id=" + lv_id;
/*     */ 
/* 144 */       this.baseDaoSupport.execute(sql, new Object[0]);
/*     */     }
/*     */   }
/*     */ 
/*     */   public List getHaveLvDiscountByCat(int cat_id)
/*     */   {
/* 155 */     String sql = "select * from member_lv_discount where discount>0 and cat_id=" + cat_id;
/* 156 */     return this.baseDaoSupport.queryForList(sql, new Object[0]);
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.service.impl.MemberLvManager
 * JD-Core Version:    0.6.0
 */