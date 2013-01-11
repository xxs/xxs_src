/*    */ package com.enation.app.base.core.service.solution.impl;
/*    */ 
/*    */ import com.enation.app.base.core.service.solution.ISetupCreator;
/*    */ import com.enation.framework.util.FileUtil;
/*    */ import java.io.File;
/*    */ import java.io.FileWriter;
/*    */ import java.io.IOException;
/*    */ import java.io.PrintStream;
/*    */ import org.dom4j.Document;
/*    */ import org.dom4j.DocumentException;
/*    */ import org.dom4j.DocumentHelper;
/*    */ import org.dom4j.Element;
/*    */ import org.dom4j.io.SAXReader;
/*    */ import org.dom4j.io.XMLWriter;
/*    */ 
/*    */ public class SetupCreator
/*    */   implements ISetupCreator
/*    */ {
/*    */   public void addTable(Document document, String target, String table)
/*    */   {
/* 19 */     document.getRootElement().element(target).addElement("table").setText(table);
/*    */   }
/*    */ 
/*    */   public Document createSetup(String path)
/*    */   {
/* 25 */     Document document = null;
/* 26 */     SAXReader saxReader = new SAXReader();
/*    */     try {
/* 28 */       if (FileUtil.exist(path))
/* 29 */         document = saxReader.read(new File(path));
/*    */     }
/*    */     catch (DocumentException e)
/*    */     {
/* 33 */       System.out.println(e.getMessage());
/*    */     }
/* 35 */     if (null == document) {
/* 36 */       String docStr = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
/* 37 */       docStr = docStr + "<setup><clean/><recreate/></setup>";
/*    */       try {
/* 39 */         document = DocumentHelper.parseText(docStr);
/*    */       } catch (DocumentException e) {
/* 41 */         e.printStackTrace();
/*    */       }
/* 43 */       save(document, path);
/*    */     }
/* 45 */     return document;
/*    */   }
/*    */ 
/*    */   public void save(Document document, String path) {
/*    */     try {
/* 50 */       XMLWriter output = new XMLWriter(new FileWriter(new File(path)));
/* 51 */       output.write(document);
/* 52 */       output.close();
/*    */     } catch (IOException e) {
/* 54 */       System.out.println(e.getMessage());
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.app.base.core.service.solution.impl.SetupCreator
 * JD-Core Version:    0.6.0
 */