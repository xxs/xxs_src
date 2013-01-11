/*    */ package com.enation.app.base.core.service.solution.impl;
/*    */ 
/*    */ import com.enation.app.base.core.service.solution.IProfileLoader;
/*    */ import com.enation.eop.sdk.context.EopSetting;
/*    */ import javax.xml.parsers.DocumentBuilder;
/*    */ import javax.xml.parsers.DocumentBuilderFactory;
/*    */ import org.apache.log4j.Logger;
/*    */ import org.w3c.dom.Document;
/*    */ 
/*    */ public class ProfileLoaderImpl
/*    */   implements IProfileLoader
/*    */ {
/* 14 */   protected final Logger logger = Logger.getLogger(getClass());
/*    */ 
/* 16 */   public Document load(String productId) { String xmlFile = EopSetting.PRODUCTS_STORAGE_PATH + "/" + productId + "/profile.xml";
/*    */     try {
/* 18 */       DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
/*    */ 
/* 20 */       DocumentBuilder builder = factory.newDocumentBuilder();
/* 21 */       Document document = builder.parse(xmlFile);
/* 22 */       return document;
/*    */     }
/*    */     catch (Exception e) {
/* 25 */       this.logger.error(e);
/* 26 */       e.printStackTrace();
/* 27 */     }throw new RuntimeException("load [" + productId + "] profile error");
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.app.base.core.service.solution.impl.ProfileLoaderImpl
 * JD-Core Version:    0.6.0
 */