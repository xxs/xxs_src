/*     */ package com.enation.app.base.core.service.dbsolution.impl;
/*     */ 
/*     */ import com.enation.eop.sdk.context.EopSetting;
/*     */ import com.enation.framework.util.FileUtil;
/*     */ import com.enation.framework.util.StringUtil;
/*     */ import java.io.File;
/*     */ import java.io.PrintStream;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Statement;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.dom4j.Document;
/*     */ import org.dom4j.DocumentException;
/*     */ import org.dom4j.DocumentHelper;
/*     */ import org.dom4j.Element;
/*     */ import org.dom4j.io.SAXReader;
/*     */ 
/*     */ public class DBImporter extends DBPorter
/*     */ {
/*     */   private Document xmlDoc;
/*     */ 
/*     */   public DBImporter(DBSolution solution)
/*     */   {
/*  30 */     super(solution);
/*     */   }
/*     */ 
/*     */   private Document loadDocument(String xmlFile)
/*     */     throws DocumentException
/*     */   {
/*  40 */     Document document = null;
/*  41 */     SAXReader saxReader = new SAXReader();
/*  42 */     File file = new File(xmlFile);
/*  43 */     if (file.exists())
/*  44 */       document = saxReader.read(new File(xmlFile));
/*  45 */     return document;
/*     */   }
/*     */ 
/*     */   private List<Object> prepareValue(String values) {
/*  49 */     List objects = new ArrayList();
/*  50 */     String[] value = values.split(",");
/*  51 */     for (int i = 0; i < value.length; i++) {
/*  52 */       objects.add(this.solution.getFuncValue(this.solution.decodeValue(value[i].replaceAll("'", ""))));
/*     */     }
/*     */ 
/*  56 */     return objects;
/*     */   }
/*     */ 
/*     */   private void doExecute(Statement state, String sql) {
/*     */     try {
/*  61 */       if (sql != null)
/*  62 */         state.execute(sql);
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/*     */     }
/*     */   }
/*     */ 
/*     */   private boolean doInsert(Element action) {
/*  70 */     String table = this.solution.getTableName(action.elementText("table"));
/*  71 */     String fields = action.elementText("fields");
/*  72 */     String values = action.elementText("values");
/*     */ 
/*  74 */     List objects = prepareValue(values);
/*     */ 
/*  76 */     String sql = "insert into " + table + " (" + fields + ") values (";
/*  77 */     String[] value = values.split(",");
/*  78 */     for (int i = 0; i < value.length; i++) {
/*  79 */       sql = sql + "?,";
/*     */     }
/*  81 */     sql = sql.substring(0, sql.length() - 1) + ")";
/*     */     try
/*     */     {
/*  84 */       Statement state = this.solution.conn.createStatement();
/*  85 */       PreparedStatement ps = this.solution.conn.prepareStatement(sql);
/*     */ 
/*  87 */       for (int i = 1; i <= value.length; i++) {
/*  88 */         ps.setObject(i, objects.get(i - 1));
/*     */       }
/*     */ 
/*  91 */       if (this.solution.beforeInsert(table, fields, values)) {
/*  92 */         doExecute(state, this.solution.getSqlExchange());
/*  93 */         ps.execute();
/*  94 */         this.solution.afterInsert(table, fields, values);
/*  95 */         doExecute(state, this.solution.getSqlExchange());
/*  96 */         ps.close();
/*  97 */         state.close();
/*     */       } else {
/*  99 */         System.out.println("beforeInsert返回false，insert被阻止！");
/* 100 */         return false;
/*     */       }
/*     */     } catch (SQLException e) {
/* 103 */       e.printStackTrace();
/* 104 */       System.out.println("出错语句：" + sql);
/* 105 */       System.out.println("出错值：" + values);
/* 106 */       return false;
/*     */     }
/* 108 */     return true;
/*     */   }
/*     */ 
/*     */   private boolean doDrop(Element action) {
/* 112 */     String table = this.solution.getTableName(action.elementText("table"));
/* 113 */     String sql = this.solution.getDropSQL(table);
/* 114 */     return this.solution.executeSqls(sql);
/*     */   }
/*     */ 
/*     */   private boolean doCreate(Element action) {
/* 118 */     String sql = this.solution.getCreateSQL(action);
/*     */ 
/* 120 */     return this.solution.executeSqls(sql);
/*     */   }
/*     */ 
/*     */   private boolean doIndex(Element action) {
/* 124 */     return doIndex(action, 0, 0);
/*     */   }
/*     */   private boolean doUnindex(Element action) {
/* 127 */     return doUnindex(action, 0, 0);
/*     */   }
/*     */ 
/*     */   private boolean doInsert(Element action, int userid, int siteid) {
/* 131 */     String table = this.solution.getSaasTableName(action.elementText("table"), userid, siteid);
/* 132 */     String fields = action.elementText("fields");
/* 133 */     String values = action.elementText("values");
/*     */ 
/* 135 */     List objects = prepareValue(values);
/*     */ 
/* 137 */     String sql = "insert into " + table + " (" + fields + ") values (";
/* 138 */     String[] value = values.split(",");
/* 139 */     for (int i = 0; i < value.length; i++) {
/* 140 */       sql = sql + "?,";
/*     */     }
/* 142 */     sql = sql.substring(0, sql.length() - 1) + ")";
/*     */     try
/*     */     {
/* 145 */       Statement state = this.solution.conn.createStatement();
/* 146 */       PreparedStatement ps = this.solution.conn.prepareStatement(sql);
/*     */ 
/* 148 */       for (int i = 1; i <= value.length; i++) {
/* 149 */         ps.setObject(i, objects.get(i - 1));
/*     */       }
/*     */ 
/* 152 */       if (this.solution.beforeInsert(table, fields, values)) {
/* 153 */         doExecute(state, this.solution.getSqlExchange());
/* 154 */         ps.execute();
/* 155 */         this.solution.afterInsert(table, fields, values);
/* 156 */         doExecute(state, this.solution.getSqlExchange());
/* 157 */         ps.close();
/* 158 */         state.close();
/*     */       } else {
/* 160 */         System.out.println("beforeInsert返回false，insert被阻止！");
/* 161 */         return false;
/*     */       }
/*     */     } catch (SQLException e) {
/* 164 */       e.printStackTrace();
/* 165 */       System.out.println("出错语句：" + sql);
/* 166 */       System.out.println("出错值：" + values);
/* 167 */       return false;
/*     */     }
/* 169 */     return true;
/*     */   }
/*     */ 
/*     */   private boolean doDrop(Element action, int userid, int siteid) {
/* 173 */     String table = this.solution.getSaasTableName(action.elementText("table"), userid, siteid);
/* 174 */     String sql = this.solution.getDropSQL(table);
/* 175 */     return this.solution.executeSqls(sql);
/*     */   }
/*     */ 
/*     */   private boolean doCreate(Element action, int userid, int siteid) {
/* 179 */     String sql = this.solution.getSaasCreateSQL(action, userid, siteid);
/*     */ 
/* 181 */     return this.solution.executeSqls(sql);
/*     */   }
/*     */ 
/*     */   private boolean doIndex(Element action, int userid, int siteid)
/*     */   {
/* 189 */     String sql = "create index ";
/*     */     String table;
/*     */     String table;
/* 191 */     if ((userid == 0) && (siteid == 0))
/* 192 */       table = this.solution.getTableName(action.elementText("table"));
/*     */     else
/* 194 */       table = this.solution.getSaasTableName(action.elementText("table"), userid, siteid);
/* 195 */     List fields = action.elements("field");
/* 196 */     String field = " (";
/* 197 */     String name = "_";
/* 198 */     int i = 0; for (int len = fields.size(); i < len; i++) {
/* 199 */       Element element = (Element)fields.get(i);
/* 200 */       field = field + element.elementText("name") + ",";
/* 201 */       name = name + element.elementText("name") + "_";
/*     */     }
/* 203 */     field = field.substring(0, field.length() - 1) + ")";
/* 204 */     name = name.substring(0, name.length() - 1);
/*     */ 
/* 206 */     sql = sql + "idx" + name + " on " + table + field;
/* 207 */     return this.solution.executeSqls(sql);
/*     */   }
/*     */ 
/*     */   private boolean doAlter(Element action, int userid, int siteid)
/*     */   {
/*     */     try
/*     */     {
/* 215 */       String sql = "";
/*     */       String table;
/*     */       String table;
/* 216 */       if ((userid == 0) && (siteid == 0))
/* 217 */         table = this.solution.getTableName(action.elementText("table"));
/*     */       else {
/* 219 */         table = this.solution.getSaasTableName(action.elementText("table"), userid, siteid);
/*     */       }
/* 221 */       List fields = action.elements("field");
/* 222 */       int i = 0; for (int len = fields.size(); i < len; i++) {
/* 223 */         Element element = (Element)fields.get(i);
/* 224 */         String type = element.attributeValue("type");
/* 225 */         String name = element.elementText("name");
/* 226 */         String size = element.elementText("size");
/*     */ 
/* 229 */         if (i != 0) {
/* 230 */           sql = sql + ",";
/*     */         }
/* 232 */         if ("add".equals(type)) {
/* 233 */           String datatype = element.elementText("type");
/*     */ 
/* 235 */           if (EopSetting.DBTYPE.equals("2"))
/* 236 */             sql = sql + " add  " + name + " ";
/*     */           else {
/* 238 */             sql = sql + " add column " + name + " ";
/*     */           }
/*     */ 
/* 241 */           sql = sql + this.solution.toLocalType(datatype, size);
/*     */ 
/* 243 */           String def = element.elementText("default");
/* 244 */           if (!StringUtil.isEmpty(def)) {
/* 245 */             sql = sql + " default " + def;
/*     */           }
/*     */         }
/*     */ 
/* 249 */         if ("drop".equals(type)) {
/* 250 */           sql = sql + " drop column " + name;
/*     */         }
/*     */       }
/*     */ 
/* 254 */       sql = "alter table " + table + " " + sql;
/* 255 */       this.solution.executeSqls(sql);
/* 256 */       return true;
/*     */     } catch (RuntimeException e) {
/* 258 */       e.printStackTrace();
/* 259 */     }return false;
/*     */   }
/*     */ 
/*     */   private boolean doUnindex(Element action, int userid, int siteid)
/*     */   {
/* 285 */     System.out.println("unindex command,support later");
/* 286 */     return true;
/*     */   }
/*     */ 
/*     */   private boolean doAction(Element action)
/*     */   {
/* 295 */     String command = action.elementText("command").toLowerCase();
/* 296 */     if ("create".equals(command))
/* 297 */       return doCreate(action);
/* 298 */     if ("insert".equals(command))
/* 299 */       return doInsert(action);
/* 300 */     if ("drop".equals(command))
/* 301 */       return doDrop(action);
/* 302 */     if ("index".equals(command))
/* 303 */       return doIndex(action);
/* 304 */     if ("unindex".equals(command))
/* 305 */       return doUnindex(action);
/* 306 */     if ("alter".equals(command)) {
/* 307 */       return doAlter(action, 0, 0);
/*     */     }
/* 309 */     return true;
/*     */   }
/*     */ 
/*     */   private boolean doSaasAction(Element action, int userid, int siteid) {
/* 313 */     String command = action.elementText("command").toLowerCase();
/* 314 */     if ("create".equals(command))
/* 315 */       return doCreate(action, userid, siteid);
/* 316 */     if ("insert".equals(command))
/* 317 */       return doInsert(action, userid, siteid);
/* 318 */     if ("drop".equals(command))
/* 319 */       return doDrop(action, userid, siteid);
/* 320 */     if ("index".equals(command))
/* 321 */       return doIndex(action, userid, siteid);
/* 322 */     if ("unindex".equals(command))
/* 323 */       return doUnindex(action, userid, siteid);
/* 324 */     if ("alter".equals(command)) {
/* 325 */       return doAlter(action, userid, siteid);
/*     */     }
/* 327 */     return true;
/*     */   }
/*     */ 
/*     */   public boolean doImport(String xml)
/*     */   {
/* 338 */     this.solution.beforeImport();
/*     */     try {
/* 340 */       if (xml.startsWith("file:")) {
/* 341 */         xml = FileUtil.readFile(xml.replaceAll("file:", ""));
/* 342 */         this.xmlDoc = DocumentHelper.parseText(xml);
/* 343 */       } else if (xml.startsWith("<?xml version")) {
/* 344 */         this.xmlDoc = DocumentHelper.parseText(xml);
/*     */       }
/*     */       else {
/* 347 */         this.xmlDoc = loadDocument(xml);
/*     */       }
/*     */     } catch (DocumentException e) {
/* 350 */       e.printStackTrace();
/* 351 */       return false;
/*     */     }
/* 353 */     List actions = this.xmlDoc.getRootElement().elements("action");
/* 354 */     for (Element action : actions) {
/* 355 */       if (!doAction(action))
/* 356 */         return false;
/*     */     }
/* 358 */     this.solution.afterImport();
/* 359 */     return true;
/*     */   }
/*     */ 
/*     */   public boolean doSaasImport(String xml, int userid, int siteid)
/*     */   {
/* 364 */     this.solution.beforeImport();
/*     */     try {
/* 366 */       if (xml.startsWith("file:")) {
/* 367 */         xml = FileUtil.readFile(xml.replaceAll("file:", ""));
/* 368 */         this.xmlDoc = DocumentHelper.parseText(xml);
/* 369 */       } else if (xml.startsWith("<?xml version")) {
/* 370 */         this.xmlDoc = DocumentHelper.parseText(xml);
/*     */       }
/*     */       else {
/* 373 */         this.xmlDoc = loadDocument(xml);
/*     */       }
/*     */     } catch (DocumentException e) {
/* 376 */       e.printStackTrace();
/* 377 */       return false;
/*     */     }
/* 379 */     List actions = this.xmlDoc.getRootElement().elements("action");
/* 380 */     for (Element action : actions) {
/* 381 */       if (!doSaasAction(action, userid, siteid))
/* 382 */         return false;
/*     */     }
/* 384 */     this.solution.afterImport();
/* 385 */     return true;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.app.base.core.service.dbsolution.impl.DBImporter
 * JD-Core Version:    0.6.0
 */