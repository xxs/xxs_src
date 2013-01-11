/*     */ package com.enation.app.shop.core.service.impl;
/*     */ 
/*     */ import com.enation.app.shop.core.service.IDlyTypeAreaManager;
/*     */ import com.enation.eop.sdk.database.BaseSupport;
/*     */ import com.enation.framework.database.IDaoSupport;
/*     */ import com.enation.framework.util.StringUtil;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.sf.json.JSONObject;
/*     */ import org.springframework.jdbc.core.RowMapper;
/*     */ 
/*     */ public class DlyTypeAreaManager extends BaseSupport
/*     */   implements IDlyTypeAreaManager
/*     */ {
/*     */   public Map listAllByRegion(String regionid)
/*     */   {
/*  23 */     String sql = "select dta.type_id,dta.area_id_group,dta.area_name_group,dta.has_cod,dta.config,dt.name,dt.is_same,dt.config as dt_config  from " + getTableName("dly_type_area") + " dta left join " + getTableName("dly_type") + " dt on dta.type_id = dt.type_id";
/*  24 */     List typeAreaList = null;
/*  25 */     if (regionid != null) {
/*  26 */       typeAreaList = this.daoSupport.queryForList(sql, new RowMapper()
/*     */       {
/*     */         public Object mapRow(ResultSet rs, int arg1) throws SQLException
/*     */         {
/*  30 */           Map map = new HashMap();
/*  31 */           map.put("type_id", Integer.valueOf(rs.getInt("type_id")));
/*  32 */           map.put("area_name_group", rs.getString("area_name_group"));
/*  33 */           map.put("area_id_group", rs.getString("area_id_group"));
/*  34 */           map.put("has_cod", Integer.valueOf(rs.getInt("has_cod")));
/*  35 */           map.put("dta_config", JSONObject.fromObject(rs.getString("config")));
/*     */ 
/*  37 */           map.put("name", rs.getString("name"));
/*  38 */           map.put("is_same", Integer.valueOf(rs.getInt("is_same")));
/*  39 */           map.put("config", JSONObject.fromObject(rs.getString("dt_config")));
/*     */ 
/*  41 */           return map;
/*     */         }
/*     */       }
/*     */       , new Object[0]);
/*     */ 
/*  44 */       if ((typeAreaList != null) && (typeAreaList.size() != 0)) {
/*  45 */         Map area = null;
/*  46 */         for (Map map : typeAreaList) {
/*  47 */           if (map.get("area_id_group").toString().indexOf("," + regionid.trim() + ",") != -1) {
/*  48 */             int cod = queryByrdgion(regionid);
/*  49 */             map.put("has_cod", Integer.valueOf(cod));
/*  50 */             area = map;
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/*  56 */         if (area == null) {
/*  57 */           return queryOtherRegions(Integer.valueOf(1), regionid);
/*     */         }
/*  59 */         return area;
/*     */       }
/*     */ 
/*  63 */       return null;
/*     */     }
/*     */ 
/*  66 */     return null;
/*     */   }
/*     */ 
/*     */   public int queryByrdgion(String regionid)
/*     */   {
/*  72 */     int cod = 0;
/*  73 */     if (!StringUtil.isEmpty(regionid)) {
/*  74 */       cod = this.baseDaoSupport.queryForInt("select cod from regions where region_id = " + regionid, new Object[0]);
/*     */     }
/*  76 */     return cod;
/*     */   }
/*     */ 
/*     */   public Map queryOtherRegions(Integer type_id, String regionid)
/*     */   {
/*  81 */     String sql = "select * from dly_type where type_id = " + type_id;
/*  82 */     List typeAreaList = this.baseDaoSupport.queryForList(sql, new RowMapper()
/*     */     {
/*     */       public Object mapRow(ResultSet rs, int arg1) throws SQLException
/*     */       {
/*  86 */         Map map = new HashMap();
/*  87 */         map.put("type_id", Integer.valueOf(rs.getInt("type_id")));
/*  88 */         map.put("area_name_group", null);
/*  89 */         map.put("area_id_group", null);
/*  90 */         map.put("has_cod", Integer.valueOf(rs.getInt("has_cod")));
/*  91 */         map.put("dta_config", JSONObject.fromObject(rs.getString("config")));
/*  92 */         map.put("name", rs.getString("name"));
/*  93 */         map.put("is_same", Integer.valueOf(rs.getInt("is_same")));
/*  94 */         return map;
/*     */       }
/*     */     }
/*     */     , new Object[0]);
/*     */ 
/*  97 */     Map area = null;
/*  98 */     if ((typeAreaList != null) && (!typeAreaList.isEmpty())) {
/*  99 */       area = (Map)typeAreaList.get(0);
/* 100 */       int cod = queryByrdgion(regionid);
/* 101 */       area.put("has_cod", Integer.valueOf(cod));
/*     */     }
/* 103 */     return area;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.service.impl.DlyTypeAreaManager
 * JD-Core Version:    0.6.0
 */