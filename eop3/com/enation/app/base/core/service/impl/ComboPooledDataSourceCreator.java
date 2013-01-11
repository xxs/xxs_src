/*    */ package com.enation.app.base.core.service.impl;
/*    */ 
/*    */ import com.enation.app.base.core.service.IDataSourceCreator;
/*    */ import com.mchange.v2.c3p0.ComboPooledDataSource;
/*    */ import java.beans.PropertyVetoException;
/*    */ import java.sql.Connection;
/*    */ import java.sql.SQLException;
/*    */ import java.sql.Statement;
/*    */ import javax.sql.DataSource;
/*    */ 
/*    */ public class ComboPooledDataSourceCreator
/*    */   implements IDataSourceCreator
/*    */ {
/*    */   private DataSource dataSource;
/*    */ 
/*    */   public DataSource createDataSource(String driver, String url, String username, String password)
/*    */   {
/*    */     try
/*    */     {
/* 17 */       ComboPooledDataSource comboPooledDataSource = (ComboPooledDataSource)this.dataSource;
/*    */ 
/* 19 */       comboPooledDataSource.setUser(username);
/* 20 */       comboPooledDataSource.setPassword(password);
/* 21 */       comboPooledDataSource.setJdbcUrl(url);
/* 22 */       comboPooledDataSource.setDriverClass(driver);
/*    */ 
/* 24 */       return comboPooledDataSource;
/*    */     } catch (PropertyVetoException e) {
/* 26 */       e.printStackTrace();
/*    */     }
/*    */ 
/* 29 */     return null;
/*    */   }
/*    */ 
/*    */   public static void main(String[] args) throws SQLException
/*    */   {
/* 34 */     IDataSourceCreator creator = new ComboPooledDataSourceCreator();
/* 35 */     DataSource dataSource = creator.createDataSource("oracle.jdbc.driver.OracleDriver", "jdbc:oracle:thin:@localhost:1521:XE", "javashop", "javashop");
/* 36 */     Connection con = dataSource.getConnection();
/* 37 */     Statement st = con.createStatement();
/* 38 */     st.execute("delete from test");
/*    */   }
/*    */ 
/*    */   public DataSource getDataSource()
/*    */   {
/* 43 */     return this.dataSource;
/*    */   }
/*    */ 
/*    */   public void setDataSource(DataSource dataSource)
/*    */   {
/* 48 */     this.dataSource = dataSource;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.app.base.core.service.impl.ComboPooledDataSourceCreator
 * JD-Core Version:    0.6.0
 */