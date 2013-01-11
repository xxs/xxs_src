/*    */ package com.enation.framework.database.impl;
/*    */ 
/*    */ import java.sql.ResultSet;
/*    */ import java.sql.ResultSetMetaData;
/*    */ import java.sql.SQLException;
/*    */ import java.util.Map;
/*    */ import org.springframework.jdbc.core.ColumnMapRowMapper;
/*    */ import org.springframework.jdbc.support.JdbcUtils;
/*    */ 
/*    */ public class MySqlColumnMapRowMapper extends ColumnMapRowMapper
/*    */ {
/*    */   public Object mapRow(ResultSet rs, int rowNum)
/*    */     throws SQLException
/*    */   {
/* 14 */     ResultSetMetaData rsmd = rs.getMetaData();
/* 15 */     int columnCount = rsmd.getColumnCount();
/* 16 */     Map mapOfColValues = createColumnMap(columnCount);
/* 17 */     for (int i = 1; i <= columnCount; i++) {
/* 18 */       String key = getColumnKey(JdbcUtils.lookupColumnName(rsmd, i));
/* 19 */       key = key.toLowerCase();
/* 20 */       Object obj = null;
/* 21 */       String typename = rsmd.getColumnTypeName(i).toUpperCase();
/* 22 */       if ("DECIMAL".equals(typename))
/* 23 */         obj = Double.valueOf(rs.getDouble(i));
/*    */       else {
/* 25 */         obj = getColumnValue(rs, i);
/*    */       }
/*    */ 
/* 28 */       mapOfColValues.put(key, obj);
/*    */     }
/* 30 */     return mapOfColValues;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.framework.database.impl.MySqlColumnMapRowMapper
 * JD-Core Version:    0.6.0
 */