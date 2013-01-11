/*    */ package com.enation.app.shop.core.model.mapper;
/*    */ 
/*    */ import com.enation.app.shop.core.model.support.TypeArea;
/*    */ import com.enation.app.shop.core.model.support.TypeAreaConfig;
/*    */ import java.sql.ResultSet;
/*    */ import java.sql.SQLException;
/*    */ import net.sf.json.JSONObject;
/*    */ import org.springframework.jdbc.core.RowMapper;
/*    */ 
/*    */ public class TypeAreaMapper
/*    */   implements RowMapper
/*    */ {
/*    */   public Object mapRow(ResultSet rs, int rowNum)
/*    */     throws SQLException
/*    */   {
/* 23 */     TypeArea typeArea = new TypeArea();
/* 24 */     typeArea.setArea_id_group(rs.getString("area_id_group"));
/* 25 */     typeArea.setArea_name_group(rs.getString("area_name_group"));
/* 26 */     typeArea.setConfig(rs.getString("config"));
/* 27 */     typeArea.setExpressions(rs.getString("expressions"));
/* 28 */     typeArea.setHas_cod(Integer.valueOf(rs.getInt("has_cod")));
/* 29 */     typeArea.setType_id(Integer.valueOf(rs.getInt("type_id")));
/* 30 */     JSONObject configJsonObject = JSONObject.fromObject(typeArea.getConfig());
/* 31 */     typeArea.setTypeAreaConfig((TypeAreaConfig)JSONObject.toBean(configJsonObject, TypeAreaConfig.class));
/* 32 */     return typeArea;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.model.mapper.TypeAreaMapper
 * JD-Core Version:    0.6.0
 */