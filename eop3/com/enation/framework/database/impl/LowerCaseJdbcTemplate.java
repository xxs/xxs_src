/*    */ package com.enation.framework.database.impl;
/*    */ 
/*    */ import com.enation.eop.sdk.context.EopSetting;
/*    */ import org.springframework.jdbc.core.ColumnMapRowMapper;
/*    */ import org.springframework.jdbc.core.JdbcTemplate;
/*    */ import org.springframework.jdbc.core.RowMapper;
/*    */ 
/*    */ public class LowerCaseJdbcTemplate extends JdbcTemplate
/*    */ {
/*    */   protected RowMapper getColumnMapRowMapper()
/*    */   {
/* 18 */     if ("2".equals(EopSetting.DBTYPE))
/* 19 */       return new OracleColumnMapRowMapper();
/* 20 */     if ("1".equals(EopSetting.DBTYPE)) {
/* 21 */       return new MySqlColumnMapRowMapper();
/*    */     }
/* 23 */     return new ColumnMapRowMapper();
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.framework.database.impl.LowerCaseJdbcTemplate
 * JD-Core Version:    0.6.0
 */