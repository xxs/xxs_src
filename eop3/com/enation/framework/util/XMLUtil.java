/*    */ package com.enation.framework.util;
/*    */ 
/*    */ import org.w3c.dom.Document;
/*    */ import org.w3c.dom.Element;
/*    */ import org.w3c.dom.Node;
/*    */ import org.w3c.dom.NodeList;
/*    */ 
/*    */ public abstract class XMLUtil
/*    */ {
/*    */   public static Element getChildByTagName(Node node, String tagName)
/*    */   {
/* 27 */     if (node.getNodeType() != 1) {
/* 28 */       throw new RuntimeException(tagName + "节点格式不正确,非Element类型。");
/*    */     }
/* 30 */     Element el = (Element)node;
/*    */ 
/* 32 */     NodeList nodeList = el.getElementsByTagName(tagName);
/* 33 */     int length = nodeList.getLength();
/*    */ 
/* 35 */     if ((nodeList == null) || (length == 0)) {
/* 36 */       return null;
/*    */     }
/* 38 */     return (Element)nodeList.item(0);
/*    */   }
/*    */ 
/*    */   public static Element getChildByTagName(Document doc, String tagName)
/*    */   {
/* 51 */     NodeList nodeList = doc.getElementsByTagName(tagName);
/* 52 */     int length = nodeList.getLength();
/*    */ 
/* 54 */     if ((nodeList == null) || (length == 0)) {
/* 55 */       return null;
/*    */     }
/* 57 */     return (Element)nodeList.item(0);
/*    */   }
/*    */ 
/*    */   public static Element getChildByAttrName(Node node, String attrName, String attrValue)
/*    */   {
/* 71 */     NodeList nodeList = node.getChildNodes();
/* 72 */     int i = 0; for (int len = nodeList.getLength(); i < len; i++) {
/* 73 */       Node n = nodeList.item(i);
/* 74 */       if (n.getNodeType() == 1) {
/* 75 */         Element el = (Element)n;
/* 76 */         if (attrValue.equals(el.getAttribute(attrName))) {
/* 77 */           return el;
/*    */         }
/*    */       }
/*    */     }
/*    */ 
/* 82 */     return null;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.framework.util.XMLUtil
 * JD-Core Version:    0.6.0
 */