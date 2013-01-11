/*    */ package com.enation.framework.database;
/*    */ 
/*    */ import java.sql.ResultSet;
/*    */ import java.sql.SQLException;
/*    */ import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
/*    */ 
/*    */ public class DoubleMapper
/*    */   implements ParameterizedRowMapper
/*    */ {
/*    */   public Object mapRow(ResultSet rs, int rowNum)
/*    */     throws SQLException
/*    */   {
/* 12 */     Double dobule = new Double(rs.getDouble(1));
/* 13 */     return dobule;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.framework.database.DoubleMapper
 * JD-Core Version:    0.6.0
 */