/*     */ package com.enation.app.shop.core.service.impl;
/*     */ 
/*     */ import com.enation.app.shop.core.model.Goods;
/*     */ import com.enation.app.shop.core.service.IWarnTaskManager;
/*     */ import com.enation.eop.sdk.database.BaseSupport;
/*     */ import com.enation.framework.database.IDaoSupport;
/*     */ import com.enation.framework.database.Page;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class WarnTaskManager extends BaseSupport
/*     */   implements IWarnTaskManager
/*     */ {
/*     */   public List<Map> listColor(Integer goodsId)
/*     */   {
/*  23 */     String sql = new StringBuilder().append("select  pc.productid,pc.color  from ").append(getTableName("product")).append(" p left join ").append(getTableName("product_color")).append(" pc  on p.product_id=pc.productid where p.goods_id=").append(goodsId).toString();
/*  24 */     return this.daoSupport.queryForList(sql, new Object[0]);
/*     */   }
/*     */ 
/*     */   public void saveTask(Map map)
/*     */   {
/*  32 */     Goods goods = (Goods)map.get("goods");
/*  33 */     String depotval = map.get("depotval").toString();
/*  34 */     String sphereval = map.get("sphereval").toString();
/*  35 */     String cylinderval = map.get("cylinderval").toString();
/*  36 */     String productval = map.get("productval").toString();
/*  37 */     String[] deptArr = depotval.split(",");
/*  38 */     for (int i = 0; i < deptArr.length; i++) {
/*  39 */       Map tempMap = new HashMap();
/*  40 */       tempMap.put("goodsid", goods.getGoods_id());
/*  41 */       tempMap.put("catid", goods.getCat_id());
/*  42 */       tempMap.put("depotid", deptArr[i]);
/*  43 */       tempMap.put("sphere", sphereval);
/*  44 */       tempMap.put("cylinder", cylinderval);
/*  45 */       tempMap.put("flag", Integer.valueOf(1));
/*  46 */       tempMap.put("productids", productval);
/*  47 */       this.baseDaoSupport.insert("warn_task", tempMap);
/*     */     }
/*     */   }
/*     */ 
/*     */   public Page listAll(Integer page, Integer pageSize) {
/*  52 */     String sql = new StringBuilder().append("SELECT d.name as depotname,g.sn,g.name,gc.name as catname,w.* FROM ").append(getTableName("warn_task")).append(" w  ").append(" left join ").append(getTableName("goods")).append(" g on w.goodsid = g.goods_id ").append(" left join ").append(getTableName("goods_cat")).append(" gc on w.catid = gc.cat_id ").append(" left join ").append(getTableName("depot")).append(" d on d.id = w.depotid ").toString();
/*     */ 
/*  56 */     Page webpage = this.daoSupport.queryForPage(sql, page.intValue(), pageSize.intValue(), new Object[0]);
/*     */ 
/*  58 */     List list = (List)webpage.getResult();
/*  59 */     for (Map map : list) {
/*  60 */       StringBuilder product_color = new StringBuilder("");
/*  61 */       if ((map.get("catid").toString().equals("3")) || (map.get("catid").toString().equals("4")) || (map.get("catid").toString().equals("12")) || (map.get("catid").toString().equals("18"))) {
/*  62 */         String[] productids = map.get("productids").toString().split(",");
/*  63 */         int flag = 0;
/*  64 */         for (String productid : productids) {
/*  65 */           if (flag != 0) {
/*  66 */             product_color.append(",");
/*     */           }
/*  68 */           product_color.append(this.baseDaoSupport.queryForString(new StringBuilder().append("select color from product_color where productid  =").append(productid).toString()));
/*  69 */           flag++;
/*     */         }
/*  71 */         map.put("color", product_color.toString());
/*     */       }
/*     */ 
/*  74 */       StringBuilder glasphere = new StringBuilder("");
/*  75 */       if ((map.get("catid").toString().equals("6")) && (!map.get("sphere").equals(""))) {
/*  76 */         String[] spheres = map.get("sphere").toString().split(",");
/*  77 */         String[] sylinderes = map.get("cylinder").toString().split("\\|");
/*  78 */         if ((sylinderes != null) && (sylinderes.length > 0)) {
/*  79 */           for (int i = 0; i < spheres.length; i++) {
/*  80 */             sylinderes[i] = sylinderes[i].substring(0, sylinderes[i].lastIndexOf(","));
/*  81 */             glasphere.append(new StringBuilder().append("[度数：").append(spheres[i]).append(",散光：").append(sylinderes[i]).append("]").toString());
/*     */           }
/*     */         }
/*  84 */         map.put("glasses_sphere", glasphere.toString());
/*     */       }
/*     */     }
/*  87 */     return webpage;
/*     */   }
/*     */ 
/*     */   public Page listdepot(Integer depotId, String name, String sn, int page, int pageSize)
/*     */   {
/*  93 */     String sql = new StringBuilder().append("select g.*,b.name as brand_name ,t.name as type_name,c.name as cat_name,wt.id as task_id,wt.productids,wt.sphere,wt.cylinder from ").append(getTableName("goods")).append(" g left join ").append(getTableName("goods_cat")).append(" c on g.cat_id=c.cat_id left join ").append(getTableName("brand")).append(" b on g.brand_id = b.brand_id and b.disabled=0 left join ").append(getTableName("goods_type")).append(" t on g.type_id =t.type_id  left join ").append(getTableName("warn_task")).append(" wt  on g.goods_id=wt.goodsid ").append(" where wt.flag=1 and g.goods_type = 'normal' and g.disabled=0 and wt.depotid=").append(depotId).toString();
/*     */ 
/* 101 */     if ((name != null) && (!name.equals(""))) {
/* 102 */       name = name.trim();
/* 103 */       String[] keys = name.split("\\s");
/*     */ 
/* 105 */       for (String key : keys) {
/* 106 */         sql = new StringBuilder().append(sql).append(" and g.name like '%").toString();
/* 107 */         sql = new StringBuilder().append(sql).append(key).toString();
/* 108 */         sql = new StringBuilder().append(sql).append("%'").toString();
/*     */       }
/*     */     }
/*     */ 
/* 112 */     if ((sn != null) && (!sn.equals(""))) {
/* 113 */       sql = new StringBuilder().append(sql).append("   and g.sn = '").append(sn).append("'").toString();
/*     */     }
/*     */ 
/* 116 */     Page webpage = this.daoSupport.queryForPage(sql.toString(), page, pageSize, new Object[0]);
/* 117 */     List list = (List)webpage.getResult();
/* 118 */     for (Map map : list) {
/* 119 */       StringBuilder product_color = new StringBuilder("");
/* 120 */       if ((map.get("cat_id").toString().equals("3")) || (map.get("cat_id").toString().equals("4")) || (map.get("cat_id").toString().equals("12")) || (map.get("cat_id").toString().equals("18"))) {
/* 121 */         String[] productids = map.get("productids").toString().split(",");
/* 122 */         int flag = 0;
/* 123 */         for (String productid : productids) {
/* 124 */           if (flag != 0) {
/* 125 */             product_color.append(",");
/*     */           }
/* 127 */           product_color.append(this.baseDaoSupport.queryForString(new StringBuilder().append("select color from product_color where productid  =").append(productid).toString()));
/* 128 */           flag++;
/*     */         }
/* 130 */         map.put("color", product_color.toString());
/*     */       }
/*     */ 
/* 133 */       StringBuilder glasphere = new StringBuilder("");
/* 134 */       if ((map.get("cat_id").toString().equals("6")) && (!map.get("sphere").equals(""))) {
/* 135 */         String[] spheres = map.get("sphere").toString().split(",");
/* 136 */         String[] sylinderes = map.get("cylinder").toString().split("\\|");
/* 137 */         for (int i = 0; i < spheres.length; i++) {
/* 138 */           sylinderes[i] = sylinderes[i].substring(0, sylinderes[i].lastIndexOf(","));
/* 139 */           glasphere.append(new StringBuilder().append("[度数：").append(spheres[i]).append(",散光：").append(sylinderes[i]).append("]").toString());
/*     */         }
/* 141 */         map.put("glasses_sphere", glasphere.toString());
/*     */       }
/*     */     }
/* 144 */     return webpage;
/*     */   }
/*     */ 
/*     */   public Map listTask(Integer taskId)
/*     */   {
/* 149 */     String sql = "select wt.* from warn_task wt where wt.id=?";
/* 150 */     return this.baseDaoSupport.queryForMap(sql, new Object[] { taskId });
/*     */   }
/*     */ 
/*     */   public Integer getProductId(Integer goodsid) {
/* 154 */     String sql = "select product_id from product where goods_id = ?";
/* 155 */     Integer productid = Integer.valueOf(this.baseDaoSupport.queryForInt(sql, new Object[] { goodsid }));
/* 156 */     return productid;
/*     */   }
/*     */ 
/*     */   public void updateTask(Integer taskId) {
/* 160 */     this.baseDaoSupport.execute("update warn_task set flag=2 where id=? ", new Object[] { taskId });
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.service.impl.WarnTaskManager
 * JD-Core Version:    0.6.0
 */