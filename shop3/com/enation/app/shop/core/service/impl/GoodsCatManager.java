/*     */ package com.enation.app.shop.core.service.impl;
/*     */ 
/*     */ import com.enation.app.shop.core.model.Cat;
/*     */ import com.enation.app.shop.core.model.mapper.CatMapper;
/*     */ import com.enation.app.shop.core.service.IGoodsCatManager;
/*     */ import com.enation.eop.sdk.database.BaseSupport;
/*     */ import com.enation.eop.sdk.utils.UploadUtil;
/*     */ import com.enation.framework.database.IDaoSupport;
/*     */ import com.enation.framework.util.StringUtil;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.springframework.transaction.annotation.Propagation;
/*     */ import org.springframework.transaction.annotation.Transactional;
/*     */ 
/*     */ public class GoodsCatManager extends BaseSupport<Cat>
/*     */   implements IGoodsCatManager
/*     */ {
/*     */   public boolean checkname(String name, Integer catid)
/*     */   {
/*  21 */     if (name != null) name = name.trim();
/*  22 */     String sql = "select count(0) from goods_cat where name=? and cat_id!=?";
/*  23 */     if (catid == null) {
/*  24 */       catid = Integer.valueOf(0);
/*     */     }
/*     */ 
/*  27 */     int count = this.baseDaoSupport.queryForInt(sql, new Object[] { name, catid });
/*  28 */     return count > 0;
/*     */   }
/*     */ 
/*     */   public int delete(int catId)
/*     */   {
/*  33 */     String sql = "select count(0) from goods_cat where parent_id = ?";
/*  34 */     int count = this.baseDaoSupport.queryForInt(sql, new Object[] { Integer.valueOf(catId) });
/*  35 */     if (count > 0) {
/*  36 */       return 1;
/*     */     }
/*     */ 
/*  39 */     sql = "select count(0) from goods where cat_id = ?";
/*  40 */     count = this.baseDaoSupport.queryForInt(sql, new Object[] { Integer.valueOf(catId) });
/*  41 */     if (count > 0) {
/*  42 */       return 2;
/*     */     }
/*  44 */     sql = "delete from  goods_cat   where cat_id=?";
/*  45 */     this.baseDaoSupport.execute(sql, new Object[] { Integer.valueOf(catId) });
/*     */ 
/*  47 */     return 0;
/*     */   }
/*     */ 
/*     */   public Cat getById(int catId)
/*     */   {
/*  54 */     String sql = "select * from goods_cat  where cat_id=?";
/*  55 */     Cat cat = (Cat)this.baseDaoSupport.queryForObject(sql, Cat.class, new Object[] { Integer.valueOf(catId) });
/*  56 */     if (cat != null) {
/*  57 */       String image = cat.getImage();
/*  58 */       if (image != null) {
/*  59 */         image = UploadUtil.replacePath(image);
/*  60 */         cat.setImage(image);
/*     */       }
/*     */     }
/*  63 */     return cat;
/*     */   }
/*     */ 
/*     */   public List<Cat> listChildren(Integer catId)
/*     */   {
/*  69 */     String sql = "select c.*,'' type_name from goods_cat c where parent_id=?";
/*  70 */     return this.baseDaoSupport.queryForList(sql, new CatMapper(), new Object[] { catId });
/*     */   }
/*     */ 
/*     */   public List<Cat> listAllChildren(Integer catId)
/*     */   {
/*  77 */     String tableName = getTableName("goods_cat");
/*  78 */     String sql = "select c.*,t.name as type_name  from  " + tableName + " c  left join " + getTableName("goods_type") + " t on c.type_id = t.type_id  " + " order by parent_id,cat_order";
/*     */ 
/*  83 */     List allCatList = this.daoSupport.queryForList(sql, new CatMapper(), new Object[0]);
/*  84 */     List topCatList = new ArrayList();
/*  85 */     if (catId.intValue() != 0) {
/*  86 */       Cat cat = getById(catId.intValue());
/*  87 */       topCatList.add(cat);
/*     */     }
/*  89 */     for (Cat cat : allCatList) {
/*  90 */       if (cat.getParent_id().compareTo(catId) == 0) {
/*  91 */         if (this.logger.isDebugEnabled()) {
/*  92 */           this.logger.debug("发现子[" + cat.getName() + "-" + cat.getCat_id() + "]" + cat.getImage());
/*     */         }
/*  94 */         List children = getChildren(allCatList, cat.getCat_id());
/*  95 */         cat.setChildren(children);
/*  96 */         topCatList.add(cat);
/*     */       }
/*     */     }
/*  99 */     return topCatList;
/*     */   }
/*     */ 
/*     */   private List<Cat> getChildren(List<Cat> catList, Integer parentid)
/*     */   {
/* 104 */     if (this.logger.isDebugEnabled()) {
/* 105 */       this.logger.debug("查找[" + parentid + "]的子");
/*     */     }
/* 107 */     List children = new ArrayList();
/* 108 */     for (Cat cat : catList) {
/* 109 */       if (cat.getParent_id().compareTo(parentid) == 0) {
/* 110 */         if (this.logger.isDebugEnabled()) {
/* 111 */           this.logger.debug(cat.getName() + "-" + cat.getCat_id() + "是子");
/*     */         }
/* 113 */         cat.setChildren(getChildren(catList, cat.getCat_id()));
/* 114 */         children.add(cat);
/*     */       }
/*     */     }
/* 117 */     return children;
/*     */   }
/*     */ 
/*     */   @Transactional(propagation=Propagation.REQUIRED)
/*     */   public void saveAdd(Cat cat)
/*     */   {
/* 125 */     this.baseDaoSupport.insert("goods_cat", cat);
/* 126 */     int cat_id = this.baseDaoSupport.getLastId("goods_cat");
/* 127 */     String sql = "";
/*     */ 
/* 130 */     if ((cat.getParent_id() != null) && (cat.getParent_id().intValue() != 0)) {
/* 131 */       sql = "select * from goods_cat  where cat_id=?";
/* 132 */       Cat parent = (Cat)this.baseDaoSupport.queryForObject(sql, Cat.class, new Object[] { cat.getParent_id() });
/*     */ 
/* 134 */       cat.setCat_path(parent.getCat_path() + cat_id + "|");
/*     */     } else {
/* 136 */       cat.setCat_path(cat.getParent_id() + "|" + cat_id + "|");
/*     */     }
/*     */ 
/* 139 */     sql = "update goods_cat set  cat_path=? where  cat_id=?";
/* 140 */     this.baseDaoSupport.execute(sql, new Object[] { cat.getCat_path(), Integer.valueOf(cat_id) });
/*     */   }
/*     */ 
/*     */   public void update(Cat cat)
/*     */   {
/* 146 */     checkIsOwner(cat.getCat_id());
/*     */ 
/* 148 */     if ((cat.getParent_id() != null) && (cat.getParent_id().intValue() != 0))
/*     */     {
/* 150 */       String sql = "select * from goods_cat where cat_id=?";
/* 151 */       Cat parent = (Cat)this.baseDaoSupport.queryForObject(sql, Cat.class, new Object[] { cat.getParent_id() });
/*     */ 
/* 153 */       cat.setCat_path(parent.getCat_path() + cat.getCat_id() + "|");
/*     */     }
/*     */     else {
/* 156 */       cat.setCat_path(cat.getParent_id() + "|" + cat.getCat_id() + "|");
/*     */     }
/*     */ 
/* 159 */     HashMap map = new HashMap();
/* 160 */     map.put("name", cat.getName());
/* 161 */     map.put("parent_id", cat.getParent_id());
/* 162 */     map.put("cat_order", Integer.valueOf(cat.getCat_order()));
/* 163 */     map.put("type_id", cat.getType_id());
/* 164 */     map.put("cat_path", cat.getCat_path());
/* 165 */     map.put("list_show", cat.getList_show());
/* 166 */     map.put("image", StringUtil.isEmpty(cat.getImage().trim()) ? null : cat.getImage());
/* 167 */     this.baseDaoSupport.update("goods_cat", map, "cat_id=" + cat.getCat_id());
/*     */   }
/*     */ 
/*     */   protected void checkIsOwner(Integer catId)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void saveSort(int[] cat_ids, int[] cat_sorts)
/*     */   {
/* 186 */     String sql = "";
/* 187 */     if (cat_ids != null)
/* 188 */       for (int i = 0; i < cat_ids.length; i++) {
/* 189 */         sql = "update  goods_cat  set cat_order=? where cat_id=?";
/* 190 */         this.baseDaoSupport.execute(sql, new Object[] { Integer.valueOf(cat_sorts[i]), Integer.valueOf(cat_ids[i]) });
/*     */       }
/*     */   }
/*     */ 
/*     */   public List getNavpath(int catId)
/*     */   {
/* 197 */     return null;
/*     */   }
/*     */ 
/*     */   public List<Cat> getParents(int catid)
/*     */   {
/* 202 */     Cat cat = getById(catid);
/* 203 */     String path = cat.getCat_path();
/* 204 */     path = path.substring(0, path.length() - 1);
/* 205 */     path = path.replace("|", ",");
/*     */ 
/* 207 */     String sql = "select * from goods_cat where cat_id in(" + path + ") order by cat_id asc";
/*     */ 
/* 209 */     return this.baseDaoSupport.queryForList(sql, Cat.class, new Object[0]);
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.service.impl.GoodsCatManager
 * JD-Core Version:    0.6.0
 */