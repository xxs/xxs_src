/*     */ package com.enation.app.base.core.service.impl;
/*     */ 
/*     */ import com.enation.app.base.core.model.VersionState;
/*     */ import com.enation.app.base.core.service.IBatchSqlExecutor;
/*     */ import com.enation.app.base.core.service.IUpdateManager;
/*     */ import com.enation.eop.processor.core.RemoteRequest;
/*     */ import com.enation.eop.processor.core.Request;
/*     */ import com.enation.eop.processor.core.Response;
/*     */ import com.enation.eop.sdk.context.EopSetting;
/*     */ import com.enation.framework.database.ISqlFileExecutor;
/*     */ import com.enation.framework.util.FileUtil;
/*     */ import com.enation.framework.util.StringUtil;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.PrintStream;
/*     */ import java.net.URL;
/*     */ import java.net.URLConnection;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Properties;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ 
/*     */ public class UpdateManager
/*     */   implements IUpdateManager
/*     */ {
/*     */   private ISqlFileExecutor sqlFileExecutor;
/*     */   private IBatchSqlExecutor batchSqlExecutor;
/*     */ 
/*     */   public VersionState checkNewVersion()
/*     */   {
/*  45 */     VersionState versionState = new VersionState();
/*     */ 
/*  48 */     String serviceDomain = EopSetting.SERVICE_DOMAIN_NAME;
/*     */ 
/*  51 */     Request request = new RemoteRequest();
/*  52 */     Response response = request.execute(serviceDomain + "/lastVersion.txt");
/*  53 */     if (response != null) {
/*  54 */       String currentVersion = EopSetting.VERSION;
/*  55 */       String newVersion = response.getContent();
/*  56 */       if (!StringUtil.isEmpty(newVersion))
/*     */       {
/*  58 */         if (versionLargerThen(newVersion, currentVersion)) {
/*  59 */           versionState.setHaveNewVersion(true);
/*  60 */           versionState.setNewVersion(newVersion);
/*  61 */           response = request.execute(serviceDomain + "/lastUpdate.txt");
/*  62 */           versionState.setUpdateLog(response.getContent());
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/*  68 */     return versionState;
/*     */   }
/*     */ 
/*     */   public void update()
/*     */   {
/*  78 */     VersionState versionState = checkNewVersion();
/*  79 */     if (versionState.getHaveNewVersion())
/*     */     {
/*  81 */       String serviceDomain = EopSetting.SERVICE_DOMAIN_NAME;
/*  82 */       Request request = new RemoteRequest();
/*  83 */       Response response = request.execute(serviceDomain + "/patch/update/");
/*  84 */       String content = response.getContent();
/*  85 */       List zipList = findPatchZipList(content);
/*  86 */       update(zipList);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void update(List<String> zipList)
/*     */   {
/*     */     try
/*     */     {
/*  98 */       for (String version : zipList) {
/*  99 */         String zipName = version + ".zip";
/*     */ 
/* 101 */         download(zipName);
/*     */ 
/* 103 */         unZip(zipName);
/*     */ 
/* 105 */         update(version);
/*     */       }
/*     */     } catch (Exception e) {
/* 108 */       e.printStackTrace();
/* 109 */       throw new RuntimeException("更新版本文件出错", e);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void update(String version)
/*     */     throws Exception
/*     */   {
/* 117 */     String patchPath = EopSetting.EOP_PATH + "/patch/" + version;
/* 118 */     String updateSql = patchPath + "/sql/update.sql";
/* 119 */     String cmsUpdateSql = patchPath + "/sql/cms_update.sql";
/*     */ 
/* 124 */     if (new File(updateSql).exists()) {
/* 125 */       String content = FileUtil.read(updateSql, "UTF-8");
/* 126 */       executeSql(content);
/*     */     }
/*     */ 
/* 131 */     if (new File(cmsUpdateSql).exists()) {
/* 132 */       String content = FileUtil.read(cmsUpdateSql, "UTF-8");
/* 133 */       this.batchSqlExecutor.executeForCms(content);
/*     */     }
/*     */ 
/* 136 */     FileUtil.copyFolder(patchPath + "/web", EopSetting.EOP_PATH);
/*     */ 
/* 138 */     updateVersion(version);
/*     */   }
/*     */ 
/*     */   private void updateVersion(String version)
/*     */   {
/* 145 */     EopSetting.VERSION = version;
/* 146 */     InputStream in = FileUtil.getResourceAsStream("eop.properties");
/* 147 */     Properties props = new Properties();
/*     */     try {
/* 149 */       props.load(in);
/* 150 */       props.setProperty("version", version);
/* 151 */       String path = StringUtil.getRootPath("eop.properties");
/* 152 */       File file = new File(path);
/* 153 */       props.store(new FileOutputStream(file), "eop.properties");
/*     */     }
/*     */     catch (IOException e) {
/* 156 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   private void executeSql(String content)
/*     */   {
/* 166 */     if ("2".equals(EopSetting.RUNMODE)) {
/* 167 */       this.batchSqlExecutor.exeucte(content);
/*     */     } else {
/* 169 */       content = content.replaceAll("_<userid>", "");
/* 170 */       content = content.replaceAll("_<siteid>", "");
/* 171 */       content = content.replaceAll("/user/<userid>/<siteid>", "");
/* 172 */       content = content.replaceAll("<userid>", "1");
/* 173 */       content = content.replaceAll("<siteid>", "1");
/* 174 */       this.sqlFileExecutor.execute(content);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void download(String zipName)
/*     */     throws Exception
/*     */   {
/* 187 */     URL url = new URL(EopSetting.SERVICE_DOMAIN_NAME + "/patch/update/" + zipName);
/* 188 */     String outPath = EopSetting.EOP_PATH + "/patch";
/* 189 */     URLConnection conn = url.openConnection();
/* 190 */     InputStream is = conn.getInputStream();
/* 191 */     byte[] bts = new byte[2048];
/* 192 */     File file = new File(outPath);
/* 193 */     if (!file.exists()) {
/* 194 */       file.mkdirs();
/*     */     }
/* 196 */     FileOutputStream fout = new FileOutputStream(outPath + "/" + zipName);
/*     */     int n;
/* 198 */     while ((n = is.read(bts)) != -1) {
/* 199 */       fout.write(bts, 0, n);
/* 200 */       fout.flush();
/* 201 */       bts = new byte[2048];
/*     */     }
/*     */   }
/*     */ 
/*     */   private void unZip(String zipName)
/*     */   {
/* 212 */     String zipPath = EopSetting.EOP_PATH + "/patch/" + zipName;
/* 213 */     FileUtil.unZip(zipPath, EopSetting.EOP_PATH + "/patch/" + zipName.replaceAll(".zip", ""), true);
/*     */   }
/*     */ 
/*     */   private List<String> findPatchZipList(String content)
/*     */   {
/* 223 */     List zipList = new ArrayList();
/* 224 */     String pattern = "<a href=\"(.*?).zip\">(.*?).zip<\\/a>";
/* 225 */     Pattern p = Pattern.compile(pattern, 34);
/* 226 */     Matcher m = p.matcher(content);
/* 227 */     while (m.find()) {
/* 228 */       String item = m.group();
/* 229 */       zipList.add(findZipName(item));
/*     */     }
/* 231 */     String currentVersion = EopSetting.VERSION;
/* 232 */     zipList = sortVersionList(currentVersion, zipList);
/* 233 */     return zipList;
/*     */   }
/*     */ 
/*     */   private String findZipName(String item)
/*     */   {
/* 244 */     String pattern = "<a href=\"(.*?).zip\">(.*?).zip<\\/a>";
/* 245 */     Pattern p = Pattern.compile(pattern, 34);
/* 246 */     Matcher m = p.matcher(item);
/* 247 */     if (m.find()) {
/* 248 */       return m.replaceAll("$2").trim();
/*     */     }
/* 250 */     return null;
/*     */   }
/*     */ 
/*     */   public List<String> sortVersionList(String currentVersion, List<String> zipList)
/*     */   {
/* 263 */     List lst = new ArrayList();
/*     */ 
/* 265 */     for (String n : zipList) {
/* 266 */       String[] num = n.split("\\.");
/* 267 */       Integer result = Integer.valueOf(Integer.valueOf(num[0]).intValue() * 1000000 + Integer.valueOf(num[1]).intValue() * 1000 + Integer.valueOf(num[2]).intValue());
/* 268 */       Map m = new HashMap();
/* 269 */       m.put("string", n);
/* 270 */       m.put("result", result);
/* 271 */       lst.add(m);
/*     */     }
/*     */ 
/* 274 */     List list = new ArrayList();
/*     */ 
/* 276 */     int i = 0; for (int len = lst.size(); i < len; i++) {
/* 277 */       for (int j = i; j < lst.size(); j++) {
/* 278 */         if (Integer.valueOf(((Map)lst.get(i)).get("result").toString()).intValue() > Integer.valueOf(((Map)lst.get(j)).get("result").toString()).intValue()) {
/* 279 */           Map m = new HashMap();
/* 280 */           m.put("string", ((Map)lst.get(i)).get("string").toString());
/* 281 */           m.put("result", ((Map)lst.get(i)).get("result").toString());
/* 282 */           ((Map)lst.get(i)).put("string", ((Map)lst.get(j)).get("string"));
/* 283 */           ((Map)lst.get(i)).put("result", ((Map)lst.get(j)).get("result"));
/* 284 */           ((Map)lst.get(j)).put("string", m.get("string"));
/* 285 */           ((Map)lst.get(j)).put("result", m.get("result"));
/*     */         }
/*     */       }
/* 288 */       if (versionLargerThen(((Map)lst.get(i)).get("string").toString(), currentVersion)) {
/* 289 */         list.add(((Map)lst.get(i)).get("string").toString());
/*     */       }
/*     */     }
/* 292 */     return list;
/*     */   }
/*     */ 
/*     */   public boolean versionLargerThen(String ver1, String ver2)
/*     */   {
/* 302 */     String[] ver1a = ver1.split("\\.");
/* 303 */     Integer ver1i = Integer.valueOf(Integer.valueOf(ver1a[0]).intValue() * 1000000 + Integer.valueOf(ver1a[1]).intValue() * 1000 + Integer.valueOf(ver1a[2]).intValue());
/* 304 */     String[] ver2a = ver2.split("\\.");
/* 305 */     Integer ver2i = Integer.valueOf(Integer.valueOf(ver2a[0]).intValue() * 1000000 + Integer.valueOf(ver2a[1]).intValue() * 1000 + Integer.valueOf(ver2a[2]).intValue());
/* 306 */     return ver1i.intValue() > ver2i.intValue();
/*     */   }
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/* 311 */     List zipList = new ArrayList();
/* 312 */     zipList.add("2.1.6");
/* 313 */     zipList.add("2.2.0");
/* 314 */     zipList.add("2.1.3");
/* 315 */     zipList.add("2.1.2");
/* 316 */     zipList.add("2.2.6");
/* 317 */     zipList.add("2.3.14");
/* 318 */     zipList.add("2.0.6");
/* 319 */     zipList.add("1.1.6");
/* 320 */     zipList.add("2.1.16");
/* 321 */     UpdateManager manager = new UpdateManager();
/* 322 */     zipList = manager.sortVersionList("2.1.6", zipList);
/* 323 */     for (String zipName : zipList)
/* 324 */       System.out.println(zipName);
/*     */   }
/*     */ 
/*     */   public ISqlFileExecutor getSqlFileExecutor()
/*     */   {
/* 345 */     return this.sqlFileExecutor;
/*     */   }
/*     */ 
/*     */   public void setSqlFileExecutor(ISqlFileExecutor sqlFileExecutor)
/*     */   {
/* 350 */     this.sqlFileExecutor = sqlFileExecutor;
/*     */   }
/*     */ 
/*     */   public IBatchSqlExecutor getBatchSqlExecutor()
/*     */   {
/* 355 */     return this.batchSqlExecutor;
/*     */   }
/*     */ 
/*     */   public void setBatchSqlExecutor(IBatchSqlExecutor batchSqlExecutor)
/*     */   {
/* 360 */     this.batchSqlExecutor = batchSqlExecutor;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.app.base.core.service.impl.UpdateManager
 * JD-Core Version:    0.6.0
 */