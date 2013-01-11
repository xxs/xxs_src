/*    */ package com.enation.app.base.core.service.impl;
/*    */ 
/*    */ import com.enation.app.base.core.model.Adv;
/*    */ import com.enation.app.base.core.model.AdvMapper;
/*    */ import com.enation.app.base.core.service.IAdvManager;
/*    */ import com.enation.eop.sdk.database.BaseSupport;
/*    */ import com.enation.eop.sdk.utils.UploadUtil;
/*    */ import com.enation.framework.database.IDaoSupport;
/*    */ import com.enation.framework.database.Page;
/*    */ import com.enation.framework.util.StringUtil;
/*    */ import java.util.Date;
/*    */ import java.util.List;
/*    */ 
/*    */ public class AdvManager extends BaseSupport<Adv>
/*    */   implements IAdvManager
/*    */ {
/*    */   public void addAdv(Adv adv)
/*    */   {
/* 26 */     this.baseDaoSupport.insert("adv", adv);
/*    */   }
/*    */ 
/*    */   public void delAdvs(String ids)
/*    */   {
/* 32 */     if ((ids == null) || (ids.equals("")))
/* 33 */       return;
/* 34 */     String sql = "delete from adv where aid in (" + ids + ")";
/*    */ 
/* 36 */     this.baseDaoSupport.execute(sql, new Object[0]);
/*    */   }
/*    */ 
/*    */   public Adv getAdvDetail(Long advid)
/*    */   {
/* 41 */     Adv adv = (Adv)this.baseDaoSupport.queryForObject("select * from adv where aid = ?", Adv.class, new Object[] { advid });
/* 42 */     String pic = adv.getAtturl();
/* 43 */     if (pic != null) {
/* 44 */       pic = UploadUtil.replacePath(pic);
/* 45 */       adv.setAtturl(pic);
/*    */     }
/* 47 */     return adv;
/*    */   }
/*    */ 
/*    */   public Page pageAdv(String order, int page, int pageSize)
/*    */   {
/* 52 */     order = order == null ? " aid desc" : order;
/* 53 */     String sql = "select v.*, c.cname   cname from " + getTableName("adv") + " v left join " + getTableName("adcolumn") + " c on c.acid = v.acid";
/* 54 */     sql = sql + " order by " + order;
/* 55 */     Page rpage = this.daoSupport.queryForPage(sql, page, pageSize, new AdvMapper(), new Object[0]);
/* 56 */     return rpage;
/*    */   }
/*    */ 
/*    */   public void updateAdv(Adv adv)
/*    */   {
/* 61 */     this.baseDaoSupport.update("adv", adv, "aid = " + adv.getAid());
/*    */   }
/*    */ 
/*    */   public List listAdv(Long acid)
/*    */   {
/* 67 */     Long nowtime = Long.valueOf(new Date().getTime());
/*    */ 
/* 69 */     List list = this.baseDaoSupport.queryForList("select a.*,'' cname from adv a where acid = ? and begintime<=? and endtime>=? and isclose = 0", new AdvMapper(), new Object[] { acid, nowtime, nowtime });
/* 70 */     return list;
/*    */   }
/*    */ 
/*    */   public Page search(Long acid, String cname, int pageNo, int pageSize, String order)
/*    */   {
/* 76 */     StringBuffer term = new StringBuffer();
/* 77 */     StringBuffer sql = new StringBuffer("select v.*, c.cname   cname from " + getTableName("adv") + " v left join " + getTableName("adcolumn") + " c on c.acid = v.acid");
/*    */ 
/* 79 */     if (acid != null) {
/* 80 */       term.append(" where  c.acid=" + acid);
/*    */     }
/*    */ 
/* 83 */     if (!StringUtil.isEmpty(cname)) {
/* 84 */       if (term.length() > 0) {
/* 85 */         term.append(" and ");
/*    */       }
/*    */       else
/*    */       {
/* 89 */         term.append(" where ");
/*    */       }
/*    */ 
/* 92 */       term.append(" aname like'%" + cname + "%'");
/*    */     }
/* 94 */     sql.append(term);
/*    */ 
/* 96 */     order = order == null ? " aid desc" : order;
/* 97 */     sql.append(" order by " + order);
/* 98 */     Page page = this.daoSupport.queryForPage(sql.toString(), pageNo, pageSize, new Object[0]);
/* 99 */     return page;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.app.base.core.service.impl.AdvManager
 * JD-Core Version:    0.6.0
 */