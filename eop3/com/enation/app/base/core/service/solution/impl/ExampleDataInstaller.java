/*     */ package com.enation.app.base.core.service.solution.impl;
/*     */ 
/*     */ import com.enation.app.base.core.service.dbsolution.DBSolutionFactory;
/*     */ import com.enation.app.base.core.service.solution.IInstaller;
/*     */ import com.enation.app.base.core.service.solution.InstallUtil;
/*     */ import com.enation.eop.resource.model.EopSite;
/*     */ import com.enation.eop.sdk.context.EopContext;
/*     */ import com.enation.eop.sdk.context.EopSetting;
/*     */ import com.enation.framework.database.ISqlFileExecutor;
/*     */ import com.enation.framework.util.FileUtil;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.CharArrayReader;
/*     */ import java.io.File;
/*     */ import java.io.PrintStream;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.springframework.transaction.annotation.Propagation;
/*     */ import org.springframework.transaction.annotation.Transactional;
/*     */ import org.w3c.dom.Node;
/*     */ 
/*     */ public class ExampleDataInstaller
/*     */   implements IInstaller
/*     */ {
/*     */   private ISqlFileExecutor sqlFileExecutor;
/*  38 */   protected final Logger logger = Logger.getLogger(getClass());
/*     */   protected String eopsiteSQL;
/*     */ 
/*     */   public String parseConst(String content)
/*     */   {
/*  42 */     this.eopsiteSQL = "";
/*  43 */     Map constMap = new HashMap();
/*  44 */     char[] buf = new char[content.length()];
/*  45 */     content.getChars(0, content.length(), buf, 0);
/*  46 */     CharArrayReader in = new CharArrayReader(buf);
/*  47 */     BufferedReader reader = new BufferedReader(in);
/*     */     Iterator it;
/*     */     try
/*     */     {
/*     */       while (true)
/*     */       {
/*  50 */         String line = reader.readLine();
/*  51 */         if (line == null)
/*     */           break;
/*  53 */         if (line.startsWith("CONST")) {
/*  54 */           String[] value = StringUtils.split(line.substring(line.indexOf(33)), "=");
/*  55 */           constMap.put(value[0], value[1]);
/*  56 */           content = content.replaceFirst(line + "\n", "");
/*  57 */         } else if (line.startsWith("EOPSITE")) {
/*  58 */           this.eopsiteSQL = ("\n" + line.replaceFirst("EOPSITE", "update eop_site"));
/*  59 */           content = content.replaceFirst(line + "\n", "");
/*     */         }
/*     */         else {
/*  62 */           break;
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (Exception keys) {
/*  67 */       Set keys = constMap.keySet();
/*  68 */       for (it = keys.iterator(); it.hasNext(); ) {
/*  69 */         String key = (String)it.next();
/*  70 */         String value = (String)constMap.get(key);
/*  71 */         content = content.replaceAll(key, value);
/*  72 */         this.eopsiteSQL = this.eopsiteSQL.replaceAll(key, value);
/*     */       }
/*     */     }
/*  74 */     return content;
/*     */   }
/*     */ 
/*     */   protected String parseUserSiteID(String content) {
/*  78 */     if ("2".equals(EopSetting.RUNMODE)) {
/*  79 */       EopSite site = EopContext.getContext().getCurrentSite();
/*  80 */       content = content.replaceAll("<userid>", String.valueOf(site.getUserid()));
/*  81 */       content = content.replaceAll("<siteid>", String.valueOf(site.getId()));
/*     */     } else {
/*  83 */       content = content.replaceAll("_<userid>", "");
/*  84 */       content = content.replaceAll("_<siteid>", "");
/*  85 */       content = content.replaceAll("/user/<userid>/<siteid>", "");
/*  86 */       content = content.replaceAll("<userid>", "1");
/*  87 */       content = content.replaceAll("<siteid>", "1");
/*     */     }
/*  89 */     return content;
/*     */   }
/*     */ 
/*     */   private void anyDBInstall(String dataSqlPath)
/*     */     throws Exception
/*     */   {
/*  99 */     if (!DBSolutionFactory.dbImport(dataSqlPath, ""))
/* 100 */       throw new RuntimeException("安装示例数据出错...");
/*     */   }
/*     */ 
/*     */   private String getDataSqlPath(String productId) {
/* 104 */     String dataSqlPath = "";
/* 105 */     if (EopSetting.DBTYPE.equals("1"))
/* 106 */       dataSqlPath = EopSetting.PRODUCTS_STORAGE_PATH + "/" + productId + "/example_data_mysql.sql";
/* 107 */     else if (EopSetting.DBTYPE.equals("2"))
/* 108 */       dataSqlPath = EopSetting.PRODUCTS_STORAGE_PATH + "/" + productId + "/example_data_oracle.sql";
/* 109 */     else if (EopSetting.DBTYPE.equals("3")) {
/* 110 */       dataSqlPath = EopSetting.PRODUCTS_STORAGE_PATH + "/" + productId + "/example_data_mssql.sql";
/*     */     }
/* 112 */     return dataSqlPath;
/*     */   }
/*     */ 
/*     */   private void loggerPrint(String text) {
/* 116 */     if (this.logger.isDebugEnabled())
/* 117 */       this.logger.debug(text);
/*     */   }
/*     */ 
/*     */   @Transactional(propagation=Propagation.REQUIRED)
/*     */   public void install(String productId, Node fragment) {
/* 123 */     boolean xmlData = true;
/* 124 */     String dataSqlPath = EopSetting.PRODUCTS_STORAGE_PATH + "/" + productId + "/example_data.xml";
/*     */     try {
/* 126 */       File xmlFile = new File(dataSqlPath);
/* 127 */       if (!xmlFile.exists()) {
/* 128 */         xmlData = false;
/* 129 */         dataSqlPath = getDataSqlPath(productId);
/*     */       }
/*     */ 
/* 132 */       loggerPrint("安装datasqlPath:" + dataSqlPath);
/*     */ 
/* 134 */       if (!"base".equals(productId))
/* 135 */         InstallUtil.putMessaage("正在安装示例数据，可能要花费较长时间，请稍后 <img src='resource/com/enation/app/saas/component/widget/product/loading.gif'");
/*     */       else {
/* 137 */         InstallUtil.putMessaage("正在安装基础数据...");
/*     */       }
/*     */ 
/* 140 */       if (xmlData) {
/* 141 */         anyDBInstall(dataSqlPath);
/*     */       }
/* 143 */       else if (new File(dataSqlPath).exists())
/*     */       {
/* 145 */         String content = FileUtil.read(dataSqlPath, "UTF-8");
/*     */ 
/* 147 */         content = parseConst(content);
/*     */ 
/* 149 */         content = parseUserSiteID(content);
/* 150 */         content = filter(content);
/* 151 */         content = content + parseUserSiteID(this.eopsiteSQL);
/*     */ 
/* 153 */         this.sqlFileExecutor.execute(content);
/*     */       }
/*     */       else {
/* 156 */         System.out.println(dataSqlPath + " not exist");
/*     */       }
/*     */ 
/* 159 */       loggerPrint("示例数据安装完毕");
/*     */ 
/* 161 */       FileUtil.copyFolder(EopSetting.PRODUCTS_STORAGE_PATH + "/" + productId + "/attachment/", EopSetting.IMG_SERVER_PATH + EopContext.getContext().getContextPath() + "/attachment/");
/*     */ 
/* 166 */       if (!"base".equals(productId))
/* 167 */         FileUtil.copyFile(EopSetting.PRODUCTS_STORAGE_PATH + "/" + productId + "/init.sql", EopSetting.EOP_PATH + EopContext.getContext().getContextPath() + "/init.sql");
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 171 */       e.printStackTrace();
/* 172 */       this.logger.debug(e.getMessage(), e);
/* 173 */       throw new RuntimeException("安装示例数据出错...");
/*     */     }
/*     */   }
/*     */ 
/*     */   private String filter(String input)
/*     */   {
/* 183 */     Pattern pattern = Pattern.compile("delete\\s?.*?\\s?;", 2);
/* 184 */     Matcher matcher = pattern.matcher(input);
/* 185 */     input = matcher.replaceAll("");
/* 186 */     pattern = Pattern.compile("truncate\\s?.*?\\s?;", 2);
/* 187 */     matcher = pattern.matcher(input);
/* 188 */     input = matcher.replaceAll("");
/* 189 */     pattern = Pattern.compile("drop\\s?.*?\\s?;", 2);
/* 190 */     matcher = pattern.matcher(input);
/* 191 */     input = matcher.replaceAll("");
/* 192 */     pattern = Pattern.compile("(delete|drop|truncate|insert|update)\\s?eop_.*?;", 2);
/* 193 */     matcher = pattern.matcher(input);
/* 194 */     input = matcher.replaceAll("");
/* 195 */     return input;
/*     */   }
/*     */ 
/*     */   public void setSqlFileExecutor(ISqlFileExecutor sqlFileExecutor) {
/* 199 */     this.sqlFileExecutor = sqlFileExecutor;
/*     */   }
/*     */ 
/*     */   public static void main(String[] args) {
/* 203 */     String content = FileUtil.read("D:/Works/Trunk/javamall/WebContent/products/eopsaler/example_data_mysql.sql", "UTF-8");
/*     */ 
/* 205 */     System.out.println(content.substring(0, 50));
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.app.base.core.service.solution.impl.ExampleDataInstaller
 * JD-Core Version:    0.6.0
 */