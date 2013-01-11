/*    */ package com.enation.framework.database;
/*    */ 
/*    */ import java.sql.ResultSet;
/*    */ import java.sql.SQLException;
/*    */ import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
/*    */ 
/*    */ public class StringMapper
/*    */   implements ParameterizedRowMapper
/*    */ {
/*    */   public Object mapRow(ResultSet rs, int rowNum)
/*    */     throws SQLException
/*    */   {
/* 12 */     String str = rs.getString(1);
/* 13 */     return str;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.framework.database.StringMapper
 * JD-Core Version:    0.6.0
 */