/*    */ package com.enation.app.base.core.service.solution.impl;
/*    */ 
/*    */ import com.enation.app.base.core.service.solution.IAdminThemeInfoFileLoader;
/*    */ import com.enation.eop.sdk.context.EopSetting;
/*    */ import javax.xml.parsers.DocumentBuilder;
/*    */ import javax.xml.parsers.DocumentBuilderFactory;
/*    */ import org.w3c.dom.Document;
/*    */ 
/*    */ public class AdminThemeInfoLoaderImpl
/*    */   implements IAdminThemeInfoFileLoader
/*    */ {
/*    */   public Document load(String productId, String path, Boolean isCommonTheme)
/*    */   {
/* 16 */     String xmlFile = null;
/* 17 */     if (isCommonTheme.booleanValue())
/* 18 */       xmlFile = EopSetting.EOP_PATH + "/adminthemes/" + path + "/themeini.xml";
/*    */     else {
/* 20 */       xmlFile = EopSetting.PRODUCTS_STORAGE_PATH + "/" + productId + "/adminthemes/" + path + "/themeini.xml";
/*    */     }
/*    */     try
/*    */     {
/* 24 */       DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
/*    */ 
/* 26 */       DocumentBuilder builder = factory.newDocumentBuilder();
/* 27 */       Document document = builder.parse(xmlFile);
/* 28 */       return document; } catch (Exception e) {
/*    */     }
/* 30 */     throw new RuntimeException("load [" + productId + " , " + path + "] themeini error!FileName:" + xmlFile);
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.app.base.core.service.solution.impl.AdminThemeInfoLoaderImpl
 * JD-Core Version:    0.6.0
 */