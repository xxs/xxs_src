/*    */ package com.enation.app.base.core.service.solution.impl;
/*    */ 
/*    */ import com.enation.app.base.core.service.solution.ISetupLoader;
/*    */ import com.enation.eop.sdk.context.EopSetting;
/*    */ import com.enation.framework.util.FileUtil;
/*    */ import java.io.File;
/*    */ import java.io.PrintStream;
/*    */ import org.dom4j.Document;
/*    */ import org.dom4j.DocumentException;
/*    */ import org.dom4j.io.SAXReader;
/*    */ 
/*    */ public class SetupLoaderImpl
/*    */   implements ISetupLoader
/*    */ {
/*    */   public Document load(String productId)
/*    */   {
/* 20 */     String xmlFile = EopSetting.PRODUCTS_STORAGE_PATH + "/" + productId + "/setup.xml";
/* 21 */     Document document = null;
/* 22 */     SAXReader saxReader = new SAXReader();
/*    */     try {
/* 24 */       if (FileUtil.exist(xmlFile))
/* 25 */         document = saxReader.read(new File(xmlFile));
/*    */     }
/*    */     catch (DocumentException e)
/*    */     {
/* 29 */       System.out.println(e.getMessage());
/*    */     }
/* 31 */     return document;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.app.base.core.service.solution.impl.SetupLoaderImpl
 * JD-Core Version:    0.6.0
 */