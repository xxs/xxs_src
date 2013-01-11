/*    */ package com.enation.framework.database.impl;
/*    */ 
/*    */ import java.sql.ResultSet;
/*    */ import java.sql.ResultSetMetaData;
/*    */ import java.sql.SQLException;
/*    */ import java.util.Map;
/*    */ import org.springframework.jdbc.core.ColumnMapRowMapper;
/*    */ import org.springframework.jdbc.support.JdbcUtils;
/*    */ 
/*    */ public class OracleColumnMapRowMapper extends ColumnMapRowMapper
/*    */ {
/*    */   public Object mapRow(ResultSet rs, int rowNum)
/*    */     throws SQLException
/*    */   {
/* 21 */     ResultSetMetaData rsmd = rs.getMetaData();
/* 22 */     int columnCount = rsmd.getColumnCount();
/* 23 */     Map mapOfColValues = createColumnMap(columnCount);
/* 24 */     for (int i = 1; i <= columnCount; i++) {
/* 25 */       String key = getColumnKey(JdbcUtils.lookupColumnName(rsmd, i));
/* 26 */       key = key.toLowerCase();
/* 27 */       Object obj = null;
/* 28 */       String typename = rsmd.getColumnTypeName(i);
/* 29 */       if ("NUMBER".equals(typename)) {
/* 30 */         int scale = rsmd.getScale(i);
/* 31 */         int precision = rsmd.getPrecision(i);
/* 32 */         if (scale == 0) {
/* 33 */           if (precision < 10)
/* 34 */             obj = Integer.valueOf(rs.getInt(i));
/*    */           else
/* 36 */             obj = Long.valueOf(rs.getLong(i));
/* 37 */         } else if (scale > 0)
/* 38 */           obj = Double.valueOf(rs.getDouble(i));
/*    */         else
/* 40 */           obj = Long.valueOf(rs.getLong(i));
/*    */       } else {
/* 42 */         obj = getColumnValue(rs, i);
/*    */       }
/*    */ 
/* 45 */       mapOfColValues.put(key, obj);
/*    */     }
/* 47 */     return mapOfColValues;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.framework.database.impl.OracleColumnMapRowMapper
 * JD-Core Version:    0.6.0
 */