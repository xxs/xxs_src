/*    */ package com.enation.framework.database.impl;
/*    */ 
/*    */ import java.sql.ResultSet;
/*    */ import java.sql.ResultSetMetaData;
/*    */ import java.sql.SQLException;
/*    */ import java.util.Map;
/*    */ import org.springframework.jdbc.core.ColumnMapRowMapper;
/*    */ import org.springframework.jdbc.support.JdbcUtils;
/*    */ 
/*    */ public class FilterColumnMapRowMapper extends ColumnMapRowMapper
/*    */ {
/*    */   private IRowMapperColumnFilter filter;
/*    */ 
/*    */   public FilterColumnMapRowMapper(IRowMapperColumnFilter _filter)
/*    */   {
/* 14 */     this.filter = _filter;
/*    */   }
/*    */ 
/*    */   public Object mapRow(ResultSet rs, int rowNum) throws SQLException
/*    */   {
/* 19 */     ResultSetMetaData rsmd = rs.getMetaData();
/* 20 */     int columnCount = rsmd.getColumnCount();
/* 21 */     Map mapOfColValues = createColumnMap(columnCount);
/* 22 */     for (int i = 1; i <= columnCount; i++) {
/* 23 */       String key = getColumnKey(JdbcUtils.lookupColumnName(rsmd, i));
/*    */ 
/* 25 */       key = key.toLowerCase();
/* 26 */       Object obj = getColumnValue(rs, i);
/* 27 */       mapOfColValues.put(key, obj);
/* 28 */       this.filter.filter(mapOfColValues, rs);
/*    */     }
/* 30 */     return mapOfColValues;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.framework.database.impl.FilterColumnMapRowMapper
 * JD-Core Version:    0.6.0
 */