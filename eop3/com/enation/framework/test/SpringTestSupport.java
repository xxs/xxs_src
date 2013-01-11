/*    */ package com.enation.framework.test;
/*    */ 
/*    */ import org.junit.BeforeClass;
/*    */ import org.springframework.context.ApplicationContext;
/*    */ import org.springframework.context.support.ClassPathXmlApplicationContext;
/*    */ import org.springframework.jdbc.core.JdbcTemplate;
/*    */ import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
/*    */ 
/*    */ public class SpringTestSupport
/*    */ {
/*    */   private static ApplicationContext context;
/*    */   protected static SimpleJdbcTemplate simpleJdbcTemplate;
/*    */   protected static JdbcTemplate jdbcTemplate;
/*    */ 
/*    */   @BeforeClass
/*    */   public static void setup()
/*    */   {
/* 26 */     context = new ClassPathXmlApplicationContext(new String[] { "classpath*:spring/*.xml", "classpath*:testspring/*.xml" });
/*    */ 
/* 28 */     simpleJdbcTemplate = (SimpleJdbcTemplate)getBean("simpleJdbcTemplate");
/* 29 */     jdbcTemplate = (JdbcTemplate)getBean("jdbcTemplate");
/*    */   }
/*    */ 
/*    */   protected static <T> T getBean(String name)
/*    */   {
/* 35 */     return context.getBean(name);
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.framework.test.SpringTestSupport
 * JD-Core Version:    0.6.0
 */