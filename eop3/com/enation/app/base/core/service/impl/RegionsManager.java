/*     */ package com.enation.app.base.core.service.impl;
/*     */ 
/*     */ import com.enation.app.base.core.model.Regions;
/*     */ import com.enation.app.base.core.service.IRegionsManager;
/*     */ import com.enation.app.base.core.service.dbsolution.DBSolutionFactory;
/*     */ import com.enation.app.base.core.service.dbsolution.IDBSolution;
/*     */ import com.enation.eop.sdk.database.BaseSupport;
/*     */ import com.enation.framework.database.IDaoSupport;
/*     */ import com.enation.framework.database.IntegerMapper;
/*     */ import java.sql.Connection;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Statement;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.sf.json.JSONArray;
/*     */ import org.springframework.transaction.annotation.Propagation;
/*     */ import org.springframework.transaction.annotation.Transactional;
/*     */ 
/*     */ public class RegionsManager extends BaseSupport<Regions>
/*     */   implements IRegionsManager
/*     */ {
/*     */   public List listCity(int province_id)
/*     */   {
/*  28 */     List list = this.baseDaoSupport.queryForList("select * from regions where region_grade = 2 and p_region_id = ?", Regions.class, new Object[] { Integer.valueOf(province_id) });
/*  29 */     list = list == null ? new ArrayList() : list;
/*  30 */     return list;
/*     */   }
/*     */ 
/*     */   public List listProvince() {
/*  34 */     List list = this.baseDaoSupport.queryForList("select * from regions where region_grade = 1", Regions.class, new Object[0]);
/*  35 */     list = list == null ? new ArrayList() : list;
/*  36 */     return list;
/*     */   }
/*     */ 
/*     */   public List listRegion(int city_id) {
/*  40 */     List list = this.baseDaoSupport.queryForList("select * from regions where region_grade = 3 and p_region_id = ?", Regions.class, new Object[] { Integer.valueOf(city_id) });
/*  41 */     list = list == null ? new ArrayList() : list;
/*  42 */     return list;
/*     */   }
/*     */ 
/*     */   public List listChildren(Integer regionid)
/*     */   {
/*  47 */     StringBuffer sql = new StringBuffer();
/*  48 */     sql.append("select c.region_id,c.local_name,c.region_grade,c.p_region_id,count(s.region_id) as childnum,c.zipcode,c.cod  from  ");
/*     */ 
/*  50 */     sql.append(getTableName("regions"));
/*  51 */     sql.append(" c");
/*     */ 
/*  53 */     sql.append(" left join ");
/*  54 */     sql.append(getTableName("regions"));
/*  55 */     sql.append(" s");
/*     */ 
/*  57 */     sql.append(" on s.p_region_id = c.region_id  where c.p_region_id=? group by c.region_id,c.local_name,c.region_grade,c.p_region_id,c.zipcode,c.cod order by region_id");
/*     */ 
/*  59 */     List list = this.daoSupport.queryForList(sql.toString(), new Object[] { regionid });
/*     */ 
/*  62 */     return list;
/*     */   }
/*     */ 
/*     */   public List listChildren(String regionid)
/*     */   {
/*  67 */     if ((regionid == null) || (regionid.equals(""))) return new ArrayList();
/*  68 */     StringBuffer sql = new StringBuffer();
/*  69 */     sql.append("select c.region_id  from  ");
/*  70 */     sql.append(getTableName("regions"));
/*  71 */     sql.append(" as c");
/*  72 */     sql.append("  where c. p_region_id in(" + regionid + ")    order by region_id");
/*  73 */     List list = this.daoSupport.queryForList(sql.toString(), new IntegerMapper(), new Object[0]);
/*  74 */     return list;
/*     */   }
/*     */ 
/*     */   public String getChildrenJson(Integer regionid) {
/*  78 */     List list = listChildren(regionid);
/*  79 */     JSONArray jsonArray = JSONArray.fromObject(list);
/*  80 */     return jsonArray.toString();
/*     */   }
/*     */   @Transactional(propagation=Propagation.REQUIRED)
/*     */   public void add(Regions regions) {
/*  85 */     this.baseDaoSupport.insert("regions", regions);
/*  86 */     String region_path = "";
/*  87 */     int region_id = this.baseDaoSupport.getLastId("regions");
/*  88 */     if (regions.getP_region_id().intValue() != 0) {
/*  89 */       Regions p = get(regions.getP_region_id().intValue());
/*  90 */       region_path = p.getRegion_path() + region_id + ",";
/*     */     } else {
/*  92 */       region_path = "," + region_id + ",";
/*     */     }
/*  94 */     regions = get(region_id);
/*  95 */     regions.setRegion_path(region_path);
/*  96 */     update(regions);
/*     */   }
/*     */ 
/*     */   public void delete(int regionId) {
/* 100 */     this.baseDaoSupport.execute("delete from regions where region_path like '," + regionId + ",%'", new Object[0]);
/*     */   }
/*     */ 
/*     */   public Regions get(int regionId)
/*     */   {
/* 105 */     return (Regions)this.baseDaoSupport.queryForObject("select * from regions where region_id = ?", Regions.class, new Object[] { Integer.valueOf(regionId) });
/*     */   }
/*     */ 
/*     */   public Regions getByName(String name) {
/*     */     try {
/* 110 */       List list = this.baseDaoSupport.queryForList("select * from regions where local_name = ?", Regions.class, new Object[] { name });
/* 111 */       if ((list == null) || (list.isEmpty())) {
/* 112 */         return null;
/*     */       }
/* 114 */       return (Regions)list.get(0); } catch (RuntimeException e) {
/*     */     }
/* 116 */     return null;
/*     */   }
/*     */ 
/*     */   public void update(Regions regions)
/*     */   {
/* 121 */     this.baseDaoSupport.update("regions", regions, "region_id=" + regions.getRegion_id());
/*     */   }
/*     */ 
/*     */   @Transactional(propagation=Propagation.REQUIRED)
/*     */   public void reset() {
/* 127 */     Connection conn = DBSolutionFactory.getConnection(null);
/*     */     try {
/* 129 */       Statement state = conn.createStatement();
/* 130 */       state.execute("truncate table es_regions");
/*     */     } catch (SQLException e) {
/* 132 */       e.printStackTrace();
/*     */     }
/*     */ 
/* 135 */     IDBSolution dbsolution = DBSolutionFactory.getDBSolution();
/* 136 */     dbsolution.setPrefix("es_");
/* 137 */     dbsolution.setConnection(conn);
/* 138 */     boolean result = dbsolution.dbImport("file:com/enation/app/base/city.xml");
/*     */     try {
/* 140 */       conn.close();
/*     */     } catch (SQLException e) {
/* 142 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   public Regions[] get(String area)
/*     */   {
/* 148 */     return null;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.app.base.core.service.impl.RegionsManager
 * JD-Core Version:    0.6.0
 */