/*    */ package com.enation.app.shop.component.spec.service;
/*    */ 
/*    */ import com.enation.app.shop.core.model.SpecValue;
/*    */ import com.enation.app.shop.core.model.mapper.SpecValueMapper;
/*    */ import com.enation.eop.sdk.database.BaseSupport;
/*    */ import com.enation.eop.sdk.utils.UploadUtil;
/*    */ import com.enation.framework.database.IDaoSupport;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import org.springframework.stereotype.Component;
/*    */ 
/*    */ @Component
/*    */ public class SpecValueManager extends BaseSupport<SpecValue>
/*    */   implements ISpecValueManager
/*    */ {
/*    */   public void add(SpecValue value)
/*    */   {
/* 24 */     this.baseDaoSupport.insert("spec_values", value);
/*    */   }
/*    */ 
/*    */   public void update(SpecValue value)
/*    */   {
/* 31 */     this.baseDaoSupport.update("spec_values", value, "spec_value_id=" + value.getSpec_value_id());
/*    */   }
/*    */ 
/*    */   public List<SpecValue> list(Integer specId)
/*    */   {
/* 36 */     String sql = "select * from spec_values where spec_id =?";
/* 37 */     List valueList = this.baseDaoSupport.queryForList(sql, new SpecValueMapper(), new Object[] { specId });
/* 38 */     return valueList;
/*    */   }
/*    */ 
/*    */   public Map get(Integer valueId)
/*    */   {
/* 44 */     String sql = "select sv.*,s.spec_type from " + getTableName("spec_values") + " sv," + getTableName("specification") + " s  where sv.spec_id=s.spec_id and sv.spec_value_id =?";
/* 45 */     Map temp = this.daoSupport.queryForMap(sql, new Object[] { valueId });
/* 46 */     String spec_image = (String)temp.get("spec_image");
/* 47 */     if (spec_image != null) {
/* 48 */       spec_image = UploadUtil.replacePath(spec_image);
/*    */     }
/* 50 */     temp.put("spec_image", spec_image);
/* 51 */     return temp;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.spec.service.SpecValueManager
 * JD-Core Version:    0.6.0
 */