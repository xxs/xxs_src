/*     */ package com.enation.eop.processor.widget;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import javax.xml.parsers.DocumentBuilder;
/*     */ import javax.xml.parsers.DocumentBuilderFactory;
/*     */ import javax.xml.transform.Transformer;
/*     */ import javax.xml.transform.TransformerFactory;
/*     */ import javax.xml.transform.dom.DOMSource;
/*     */ import javax.xml.transform.stream.StreamResult;
/*     */ import net.sf.json.JSONArray;
/*     */ import net.sf.json.JSONObject;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.NodeList;
/*     */ 
/*     */ public class WidgetXmlUtil
/*     */ {
/*     */   public static Map<String, Map<String, Map<String, String>>> parse(String path)
/*     */   {
/*     */     try
/*     */     {
/*  48 */       Document document = paseParamDoc(path);
/*  49 */       return parsedoc(document);
/*     */     }
/*     */     catch (Exception e) {
/*  52 */       e.printStackTrace();
/*  53 */     }throw new RuntimeException("load [" + path + "] widget file error");
/*     */   }
/*     */ 
/*     */   public static List<Map<String, String>> jsonToMapList(String paramJson)
/*     */   {
/*  63 */     JSONArray tempArray = JSONArray.fromObject(paramJson);
/*  64 */     List paramList = new ArrayList();
/*  65 */     Object[] paramArray = tempArray.toArray();
/*  66 */     for (Object param : paramArray) {
/*  67 */       JSONObject paramObj = JSONObject.fromObject(param);
/*  68 */       paramList.add((Map)JSONObject.toBean(paramObj, Map.class));
/*     */     }
/*     */ 
/*  71 */     return paramList;
/*     */   }
/*     */ 
/*     */   public static String mapToJson(Map<String, Map<String, String>> params)
/*     */   {
/*  81 */     if (params == null) {
/*  82 */       return "[]";
/*     */     }
/*  84 */     Set widgetIdSet = params.keySet();
/*  85 */     List mapList = new ArrayList();
/*  86 */     for (String widgetId : widgetIdSet) {
/*  87 */       Map widgetParams = (Map)params.get(widgetId);
/*  88 */       widgetParams.put("id", widgetId);
/*  89 */       mapList.add(widgetParams);
/*     */     }
/*  91 */     JSONArray array = JSONArray.fromObject(mapList);
/*     */ 
/*  93 */     return array.toString();
/*     */   }
/*     */ 
/*     */   public static void save(String path, String pageId, List<Map<String, String>> params)
/*     */   {
/*     */     try
/*     */     {
/* 107 */       Document document = paseParamDoc(path);
/*     */ 
/* 110 */       Node newPageNode = createPageNode(document, pageId, params);
/*     */ 
/* 112 */       Node widgets = document.getFirstChild();
/* 113 */       NodeList nodeList = widgets.getChildNodes();
/* 114 */       int i = 0; for (int len = nodeList.getLength(); i < len; i++) {
/* 115 */         Node page = nodeList.item(i);
/* 116 */         if (page.getNodeType() == 1) {
/* 117 */           Element pageEl = (Element)page;
/* 118 */           String id = pageEl.getAttribute("id");
/* 119 */           if (id.equals(pageId)) {
/* 120 */             widgets.replaceChild(newPageNode, page);
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/* 125 */       TransformerFactory tfactory = TransformerFactory.newInstance();
/* 126 */       Transformer t = tfactory.newTransformer();
/* 127 */       t.setOutputProperty("encoding", "UTF-8");
/* 128 */       t.setOutputProperty("indent", "yes");
/* 129 */       t.setOutputProperty("method", "xml");
/*     */ 
/* 131 */       FileOutputStream stream = new FileOutputStream(new File(path));
/* 132 */       DOMSource source = new DOMSource(document);
/* 133 */       t.transform(source, new StreamResult(stream));
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 137 */       e.printStackTrace();
/* 138 */       throw new RuntimeException("save [" + path + "] widget file error");
/*     */     }
/*     */   }
/*     */ 
/*     */   private static Node createPageNode(Document doc, String pageId, List<Map<String, String>> params)
/*     */   {
/* 152 */     Element pageEl = doc.createElement("page");
/* 153 */     pageEl.setAttribute("id", pageId);
/*     */ 
/* 156 */     for (Map paramMap : params) {
/* 157 */       Element widgetEl = doc.createElement("widget");
/* 158 */       widgetEl.setAttribute("id", (String)paramMap.get("id"));
/* 159 */       Set paramSet = paramMap.keySet();
/* 160 */       for (String name : paramSet)
/*     */       {
/* 162 */         if (!"id".equals(name)) {
/* 163 */           Element paramEl = doc.createElement(name);
/* 164 */           paramEl.setTextContent((String)paramMap.get(name));
/* 165 */           widgetEl.appendChild(paramEl);
/*     */         }
/*     */       }
/*     */ 
/* 169 */       pageEl.appendChild(widgetEl);
/*     */     }
/*     */ 
/* 172 */     return pageEl;
/*     */   }
/*     */ 
/*     */   private static Map<String, Map<String, Map<String, String>>> parsedoc(Document doc)
/*     */   {
/* 178 */     Map params = new LinkedHashMap();
/* 179 */     Node widgets = doc.getFirstChild();
/* 180 */     if (widgets == null) throw new RuntimeException("widget xml error[page node is null]");
/* 181 */     NodeList nodeList = widgets.getChildNodes();
/* 182 */     int i = 0; for (int len = nodeList.getLength(); i < len; i++) {
/* 183 */       Node page = nodeList.item(i);
/* 184 */       if (page.getNodeType() == 1) {
/* 185 */         Map widgetParams = parse(page);
/* 186 */         params.put(((Element)page).getAttribute("id"), widgetParams);
/*     */       }
/*     */     }
/*     */ 
/* 190 */     return params;
/*     */   }
/*     */ 
/*     */   private static Map<String, Map<String, String>> parse(Node page)
/*     */   {
/* 201 */     Map params = new LinkedHashMap();
/*     */ 
/* 203 */     if (page == null) throw new RuntimeException("widget xml error[page node is null]");
/*     */ 
/* 205 */     NodeList nodeList = page.getChildNodes();
/* 206 */     int i = 0; for (int len = nodeList.getLength(); i < len; i++) {
/* 207 */       Node node = nodeList.item(i);
/* 208 */       if (node.getNodeType() == 1) {
/* 209 */         Element widgetEl = (Element)node;
/* 210 */         String main = widgetEl.getAttribute("main");
/*     */ 
/* 212 */         Map param = parae(widgetEl);
/* 213 */         if ("yes".equals(main))
/* 214 */           params.put("main", param);
/*     */         else {
/* 216 */           params.put(widgetEl.getAttribute("id"), param);
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 222 */     return params;
/*     */   }
/*     */ 
/*     */   private static Map<String, String> parae(Element element)
/*     */   {
/* 233 */     NodeList nodeList = element.getChildNodes();
/* 234 */     Map param = new LinkedHashMap();
/* 235 */     param.put("widgetid", element.getAttribute("id"));
/*     */ 
/* 237 */     int i = 0; for (int len = nodeList.getLength(); i < len; i++) {
/* 238 */       Node node = nodeList.item(i);
/*     */ 
/* 240 */       if (node.getNodeType() == 1) {
/* 241 */         Element attr = (Element)node;
/* 242 */         String name = attr.getNodeName();
/* 243 */         String value = attr.getTextContent();
/* 244 */         if ("action".equals(name)) {
/* 245 */           String actionname = attr.getAttribute("name");
/* 246 */           param.put("action_" + actionname, value);
/*     */         } else {
/* 248 */           param.put(name, value);
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 254 */     return param;
/*     */   }
/*     */ 
/*     */   private static Document paseParamDoc(String path)
/*     */   {
/*     */     try
/*     */     {
/* 264 */       DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
/*     */ 
/* 266 */       DocumentBuilder builder = factory.newDocumentBuilder();
/* 267 */       Document document = builder.parse(path);
/* 268 */       return document;
/*     */     }
/*     */     catch (Exception e) {
/* 271 */       e.printStackTrace();
/* 272 */     }throw new RuntimeException("load [" + path + "] widget file error");
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.eop.processor.widget.WidgetXmlUtil
 * JD-Core Version:    0.6.0
 */