/*     */ package com.enation.app.shop.core.service.impl;
/*     */ 
/*     */ import com.enation.app.shop.core.model.Tag;
/*     */ import com.enation.app.shop.core.service.ITagManager;
/*     */ import com.enation.eop.sdk.database.BaseSupport;
/*     */ import com.enation.framework.database.IDaoSupport;
/*     */ import com.enation.framework.database.Page;
/*     */ import com.enation.framework.util.StringUtil;
/*     */ import java.util.List;
/*     */ import org.springframework.jdbc.core.JdbcTemplate;
/*     */ import org.springframework.transaction.annotation.Propagation;
/*     */ import org.springframework.transaction.annotation.Transactional;
/*     */ 
/*     */ public class TagManager extends BaseSupport<Tag>
/*     */   implements ITagManager
/*     */ {
/*     */   private IDaoSupport daoSupport;
/*     */   private JdbcTemplate jdbcTemplate;
/*     */ 
/*     */   public boolean checkname(String name, Integer tagid)
/*     */   {
/*  29 */     if (name != null) name = name.trim();
/*  30 */     if (tagid == null) tagid = Integer.valueOf(0);
/*  31 */     String sql = "select count(0) from tags where tag_name=? and tag_id!=?";
/*  32 */     int count = this.baseDaoSupport.queryForInt(sql, new Object[] { name, tagid });
/*     */ 
/*  34 */     return count > 0;
/*     */   }
/*     */ 
/*     */   public void add(Tag tag)
/*     */   {
/*  41 */     tag.setRel_count(Integer.valueOf(0));
/*  42 */     this.baseDaoSupport.insert("tags", tag);
/*     */   }
/*     */ 
/*     */   public boolean checkJoinGoods(Integer[] tagids)
/*     */   {
/*  47 */     if (tagids == null) return false;
/*  48 */     String ids = StringUtil.implode(",", tagids);
/*  49 */     String sql = "select count(0)  from tag_rel where tag_id in(" + ids + ")";
/*  50 */     int count = this.baseDaoSupport.queryForInt(sql, new Object[0]);
/*     */ 
/*  52 */     return count > 0;
/*     */   }
/*     */ 
/*     */   @Transactional(propagation=Propagation.REQUIRED)
/*     */   public void delete(Integer[] tagids)
/*     */   {
/*  59 */     String ids = StringUtil.implode(",", tagids);
/*  60 */     if ((ids == null) || (ids.equals(""))) return;
/*     */ 
/*  62 */     this.baseDaoSupport.execute("delete from tags where tag_id in(" + ids + ")", new Object[0]);
/*  63 */     this.daoSupport.execute("delete from " + getTableName("tag_rel") + " where tag_id in(" + ids + ")", new Object[0]);
/*     */   }
/*     */ 
/*     */   public Page list(int pageNo, int pageSize)
/*     */   {
/*  69 */     return this.baseDaoSupport.queryForPage("select * from tags order by tag_id", pageNo, pageSize, new Object[0]);
/*     */   }
/*     */ 
/*     */   public void update(Tag tag)
/*     */   {
/*  75 */     this.baseDaoSupport.update("tags", tag, "tag_id=" + tag.getTag_id());
/*     */   }
/*     */ 
/*     */   public IDaoSupport<Tag> getDaoSupport()
/*     */   {
/*  80 */     return this.daoSupport;
/*     */   }
/*     */ 
/*     */   public void setDaoSupport(IDaoSupport<Tag> daoSupport) {
/*  84 */     this.daoSupport = daoSupport;
/*     */   }
/*     */ 
/*     */   public Tag getById(Integer tagid)
/*     */   {
/*  89 */     String sql = "select * from tags where tag_id=?";
/*  90 */     Tag tag = (Tag)this.baseDaoSupport.queryForObject(sql, Tag.class, new Object[] { tagid });
/*  91 */     return tag;
/*     */   }
/*     */ 
/*     */   public List<Tag> list()
/*     */   {
/*  96 */     String sql = "select * from tags";
/*  97 */     return this.baseDaoSupport.queryForList(sql, Tag.class, new Object[0]);
/*     */   }
/*     */ 
/*     */   @Transactional(propagation=Propagation.REQUIRED)
/*     */   public void saveRels(Integer relid, Integer[] tagids)
/*     */   {
/* 104 */     String sql = "delete from " + getTableName("tag_rel") + " where rel_id=?";
/* 105 */     this.daoSupport.execute(sql, new Object[] { relid });
/* 106 */     if (tagids != null)
/*     */     {
/* 109 */       sql = "insert into " + getTableName("tag_rel") + "(tag_id,rel_id)values(?,?)";
/* 110 */       for (Integer tagid : tagids)
/* 111 */         this.daoSupport.execute(sql, new Object[] { tagid, relid });
/*     */     }
/*     */   }
/*     */ 
/*     */   public List<Integer> list(Integer relid)
/*     */   {
/* 118 */     String sql = "select tag_id from " + getTableName("tag_rel") + " where rel_id=" + relid;
/* 119 */     List tagids = this.jdbcTemplate.queryForList(sql, Integer.class);
/* 120 */     return tagids;
/*     */   }
/*     */ 
/*     */   public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
/* 124 */     this.jdbcTemplate = jdbcTemplate;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.service.impl.TagManager
 * JD-Core Version:    0.6.0
 */