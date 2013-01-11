/*     */ package com.enation.app.base.core.service.impl.cache;
/*     */ 
/*     */ import com.enation.app.base.core.model.Regions;
/*     */ import com.enation.app.base.core.service.IRegionsManager;
/*     */ import com.enation.eop.sdk.database.BaseSupport;
/*     */ import com.enation.framework.cache.CacheFactory;
/*     */ import com.enation.framework.cache.ICache;
/*     */ import com.enation.framework.database.IDaoSupport;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class RegionsCacheProxy extends BaseSupport<Regions>
/*     */   implements IRegionsManager
/*     */ {
/*  19 */   protected final Logger logger = Logger.getLogger(getClass());
/*     */   protected ICache<List<Regions>> cache;
/*     */   private IRegionsManager regionsManager;
/*     */   private static final String REGION_LIST_CACHE_KEY = "regionCache";
/*     */ 
/*     */   public RegionsCacheProxy(IRegionsManager regionsManager)
/*     */   {
/*  25 */     this.cache = CacheFactory.getCache("regionCache");
/*  26 */     this.regionsManager = regionsManager;
/*     */   }
/*     */ 
/*     */   public List listCity(int province_id)
/*     */   {
/*  32 */     return this.regionsManager.listCity(province_id);
/*     */   }
/*     */ 
/*     */   public List listProvince()
/*     */   {
/*  38 */     return this.regionsManager.listProvince();
/*     */   }
/*     */ 
/*     */   public List listRegion(int city_id)
/*     */   {
/*  44 */     return this.regionsManager.listRegion(city_id);
/*     */   }
/*     */ 
/*     */   public List listChildren(Integer regionid)
/*     */   {
/*  50 */     return this.regionsManager.listChildren(regionid);
/*     */   }
/*     */ 
/*     */   private List<Regions> listAll() {
/*  54 */     String sql = "select * from regions order by region_id";
/*  55 */     return this.baseDaoSupport.queryForList(sql, Regions.class, new Object[0]);
/*     */   }
/*     */ 
/*     */   public List listChildren(String regionid)
/*     */   {
/*  61 */     if (this.logger.isDebugEnabled()) {
/*  62 */       this.logger.info("find parents is " + regionid);
/*     */     }
/*  64 */     if ((regionid == null) || ("".equals(regionid))) return new ArrayList();
/*     */ 
/*  66 */     List regionsList = (List)this.cache.get("regionCache");
/*  67 */     if (regionsList == null) {
/*  68 */       if (this.logger.isDebugEnabled()) {
/*  69 */         this.logger.info("load regions list from db");
/*     */       }
/*  71 */       regionsList = listAll();
/*  72 */       this.cache.put("regionCache", regionsList);
/*     */     }
/*  74 */     else if (this.logger.isDebugEnabled()) {
/*  75 */       this.logger.info("load regions list from cache");
/*     */     }
/*     */ 
/*  78 */     List list = new ArrayList();
/*  79 */     String[] regionsArray = StringUtils.split(regionid, ",");
/*     */     String id;
/*  80 */     for (id : regionsArray)
/*     */     {
/*  82 */       for (Regions region : regionsList) {
/*  83 */         if (region.getRegion_path().indexOf("," + id + ",") >= 0) {
/*  84 */           list.add(region.getRegion_id());
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/*  89 */     return list;
/*     */   }
/*     */ 
/*     */   public String getChildrenJson(Integer regionid)
/*     */   {
/*  96 */     return this.regionsManager.getChildrenJson(regionid);
/*     */   }
/*     */ 
/*     */   public void add(Regions regions)
/*     */   {
/* 101 */     this.regionsManager.add(regions);
/*     */   }
/*     */ 
/*     */   public void delete(int regionId)
/*     */   {
/* 107 */     this.regionsManager.delete(regionId);
/*     */   }
/*     */ 
/*     */   public Regions get(int regionId)
/*     */   {
/* 113 */     return this.regionsManager.get(regionId);
/*     */   }
/*     */ 
/*     */   public void update(Regions regions)
/*     */   {
/* 118 */     this.regionsManager.update(regions);
/*     */   }
/*     */ 
/*     */   public void reset()
/*     */   {
/* 124 */     this.regionsManager.reset();
/*     */   }
/*     */ 
/*     */   public Regions[] get(String area)
/*     */   {
/* 129 */     String[] areas = StringUtils.split(area, "-");
/* 130 */     List list = listAll();
/* 131 */     Regions[] result = new Regions[3];
/* 132 */     for (Regions regions : list) {
/* 133 */       if (regions.getLocal_name().equals(areas[0])) result[0] = regions;
/* 134 */       if (regions.getLocal_name().equals(areas[1])) result[1] = regions;
/* 135 */       if (regions.getLocal_name().equals(areas[2])) result[2] = regions;
/*     */     }
/* 137 */     return result;
/*     */   }
/*     */ 
/*     */   public Regions getByName(String name)
/*     */   {
/* 142 */     return this.regionsManager.getByName(name);
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.app.base.core.service.impl.cache.RegionsCacheProxy
 * JD-Core Version:    0.6.0
 */