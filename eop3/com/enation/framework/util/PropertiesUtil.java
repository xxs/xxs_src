/*    */ package com.enation.framework.util;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.util.Enumeration;
/*    */ import java.util.HashMap;
/*    */ import java.util.Properties;
/*    */ 
/*    */ public class PropertiesUtil
/*    */ {
/*    */   private HashMap propertiesMap;
/*    */ 
/*    */   private PropertiesUtil()
/*    */   {
/*    */   }
/*    */ 
/*    */   public PropertiesUtil(String filePath)
/*    */   {
/* 22 */     InputStream in = FileUtil.getResourceAsStream(filePath);
/* 23 */     load(in);
/*    */   }
/*    */ 
/*    */   public void load(InputStream in) {
/*    */     try {
/* 28 */       Properties props = new Properties();
/* 29 */       this.propertiesMap = new HashMap();
/* 30 */       props.load(in);
/* 31 */       Enumeration en = props.propertyNames();
/* 32 */       while (en.hasMoreElements()) {
/* 33 */         String key = (String)en.nextElement();
/* 34 */         String Property = props.getProperty(key);
/* 35 */         this.propertiesMap.put(key, Property);
/*    */       }
/*    */     } catch (Exception e) {
/* 38 */       e.printStackTrace();
/*    */     }
/*    */   }
/*    */ 
/*    */   public String getProperties(String key)
/*    */   {
/* 49 */     Object value = this.propertiesMap.get(key);
/* 50 */     if (value != null) {
/* 51 */       return value.toString();
/*    */     }
/* 53 */     return null;
/*    */   }
/*    */ 
/*    */   public HashMap getPropertiesMap()
/*    */   {
/* 62 */     return this.propertiesMap;
/*    */   }
/*    */ 
/*    */   public static void main(String[] args)
/*    */   {
/* 71 */     PropertiesUtil pu = new PropertiesUtil("E:\\ProductSpace\\EOA\\resources\\config\\info.properties");
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.framework.util.PropertiesUtil
 * JD-Core Version:    0.6.0
 */