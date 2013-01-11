/*     */ package com.enation.app.shop.component.spec.service;
/*     */ 
/*     */ import com.enation.app.shop.core.model.SpecValue;
/*     */ import com.enation.app.shop.core.model.Specification;
/*     */ import com.enation.eop.sdk.context.EopContext;
/*     */ import com.enation.eop.sdk.context.EopSetting;
/*     */ import com.enation.eop.sdk.database.BaseSupport;
/*     */ import com.enation.framework.database.IDaoSupport;
/*     */ import com.enation.framework.util.StringUtil;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.springframework.stereotype.Component;
/*     */ import org.springframework.transaction.annotation.Propagation;
/*     */ import org.springframework.transaction.annotation.Transactional;
/*     */ 
/*     */ @Component
/*     */ public class SpecManager extends BaseSupport<Specification>
/*     */   implements ISpecManager
/*     */ {
/*     */   private ISpecValueManager specValueManager;
/*     */ 
/*     */   @Transactional(propagation=Propagation.REQUIRED)
/*     */   public void add(Specification spec, List<SpecValue> valueList)
/*     */   {
/*  34 */     this.baseDaoSupport.insert("specification", spec);
/*  35 */     Integer specId = Integer.valueOf(this.baseDaoSupport.getLastId("specification"));
/*  36 */     for (SpecValue value : valueList) {
/*  37 */       value.setSpec_id(specId);
/*  38 */       value.setSpec_type(spec.getSpec_type());
/*  39 */       String path = value.getSpec_image();
/*  40 */       if (path != null) {
/*  41 */         path = path.replaceAll(EopSetting.IMG_SERVER_DOMAIN + EopContext.getContext().getContextPath(), EopSetting.FILE_STORE_PREFIX);
/*     */       }
/*  43 */       value.setSpec_image(path);
/*  44 */       this.specValueManager.add(value);
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean checkUsed(Integer[] idArray)
/*     */   {
/*  55 */     if (idArray == null) return false;
/*     */ 
/*  57 */     String idStr = StringUtil.arrayToString(idArray, ",");
/*  58 */     String sql = "select count(0)  from  goods_spec where spec_id in (-1," + idStr + ")";
/*     */ 
/*  60 */     int count = this.baseDaoSupport.queryForInt(sql, new Object[0]);
/*     */ 
/*  62 */     return count > 0;
/*     */   }
/*     */ 
/*     */   public boolean checkUsed(Integer valueid)
/*     */   {
/*  75 */     String sql = "select count(0) from goods_spec where spec_value_id=?";
/*     */ 
/*  77 */     return this.baseDaoSupport.queryForInt(sql, new Object[] { valueid }) > 0;
/*     */   }
/*     */ 
/*     */   @Transactional(propagation=Propagation.REQUIRED)
/*     */   public void delete(Integer[] idArray)
/*     */   {
/*  84 */     String idStr = StringUtil.arrayToString(idArray, ",");
/*  85 */     String sql = "delete from specification where spec_id in (-1," + idStr + ")";
/*  86 */     this.baseDaoSupport.execute(sql, new Object[0]);
/*     */ 
/*  88 */     sql = "delete from spec_values where spec_id in (-1," + idStr + ")";
/*  89 */     this.baseDaoSupport.execute(sql, new Object[0]);
/*     */ 
/*  91 */     sql = "delete from goods_spec where spec_id in (-1," + idStr + ")";
/*  92 */     this.baseDaoSupport.execute(sql, new Object[0]);
/*     */   }
/*     */ 
/*     */   @Transactional(propagation=Propagation.REQUIRED)
/*     */   public void edit(Specification spec, List<SpecValue> valueList)
/*     */   {
/* 100 */     if ((valueList == null) || (valueList.isEmpty()))
/*     */     {
/* 102 */       String sql = "delete from spec_values where spec_id=?";
/* 103 */       this.baseDaoSupport.execute(sql, new Object[] { spec.getSpec_id() });
/* 104 */       this.baseDaoSupport.update("specification", spec, "spec_id=" + spec.getSpec_id());
/*     */     }
/*     */     else
/*     */     {
/* 109 */       String valuidstr = "";
/* 110 */       for (SpecValue value : valueList) {
/* 111 */         int valueid = value.getSpec_value_id().intValue();
/* 112 */         if (!StringUtil.isEmpty(valuidstr)) {
/* 113 */           valuidstr = valuidstr + ",";
/*     */         }
/* 115 */         valuidstr = valuidstr + valueid;
/*     */       }
/*     */ 
/* 118 */       String sql = "delete from spec_values where  spec_id=? and spec_value_id not in(" + valuidstr + ")";
/* 119 */       this.baseDaoSupport.execute(sql, new Object[] { spec.getSpec_id() });
/* 120 */       this.baseDaoSupport.update("specification", spec, "spec_id=" + spec.getSpec_id());
/* 121 */       for (SpecValue value : valueList) {
/* 122 */         value.setSpec_id(spec.getSpec_id());
/* 123 */         value.setSpec_type(spec.getSpec_type());
/* 124 */         String path = value.getSpec_image();
/* 125 */         if (path != null) {
/* 126 */           path = path.replaceAll(EopSetting.IMG_SERVER_DOMAIN + EopContext.getContext().getContextPath(), EopSetting.FILE_STORE_PREFIX);
/*     */         }
/* 128 */         value.setSpec_image(path);
/* 129 */         if (value.getSpec_value_id().intValue() == 0)
/* 130 */           this.specValueManager.add(value);
/*     */         else
/* 132 */           this.specValueManager.update(value);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   @Transactional(propagation=Propagation.REQUIRED)
/*     */   public List list()
/*     */   {
/* 148 */     String sql = "select * from specification";
/* 149 */     return this.baseDaoSupport.queryForList(sql, new Object[0]);
/*     */   }
/*     */ 
/*     */   public Map get(Integer spec_id) {
/* 153 */     String sql = "select * from specification where spec_id=?";
/* 154 */     return this.baseDaoSupport.queryForMap(sql, new Object[] { spec_id });
/*     */   }
/*     */ 
/*     */   public List getProSpecList(int productid)
/*     */   {
/* 163 */     String sql = "select s.spec_name name ,sv.spec_value value  from   " + getTableName("specification") + " s ," + getTableName("spec_values") + " sv ," + getTableName("goods_spec") + " gs where gs.product_id=? and gs.spec_id=s.spec_id and gs.spec_value_id = sv.spec_value_id";
/* 164 */     return this.daoSupport.queryForList(sql, new Object[] { Integer.valueOf(productid) });
/*     */   }
/*     */ 
/*     */   public ISpecValueManager getSpecValueManager()
/*     */   {
/* 169 */     return this.specValueManager;
/*     */   }
/*     */ 
/*     */   public void setSpecValueManager(ISpecValueManager specValueManager) {
/* 173 */     this.specValueManager = specValueManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.spec.service.SpecManager
 * JD-Core Version:    0.6.0
 */