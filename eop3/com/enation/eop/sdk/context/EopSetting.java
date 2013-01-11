/*     */ package com.enation.eop.sdk.context;
/*     */ 
/*     */ import com.enation.framework.util.FileUtil;
/*     */ import com.enation.framework.util.StringUtil;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Properties;
/*     */ import javax.xml.parsers.DocumentBuilder;
/*     */ import javax.xml.parsers.DocumentBuilderFactory;
/*     */ import javax.xml.parsers.ParserConfigurationException;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.NodeList;
/*     */ import org.xml.sax.SAXException;
/*     */ 
/*     */ public class EopSetting
/*     */ {
/*  26 */   public static boolean TEST_MODE = false;
/*     */ 
/*  30 */   public static boolean DEVELOPMENT_MODEL = true;
/*     */ 
/*  35 */   public static String EOP_PATH = "";
/*     */ 
/*  41 */   public static String IMG_SERVER_DOMAIN = "static.eop.com";
/*     */ 
/*  46 */   public static String IMG_SERVER_PATH = "";
/*     */ 
/*  51 */   public static String APP_DATA_STORAGE_PATH = "e:/eop";
/*     */ 
/*  56 */   public static String PRODUCTS_STORAGE_PATH = "E:/workspace/eop3/eop/src/products";
/*     */ 
/*  62 */   public static String APP_DOMAIN_NAME = "eop.com";
/*     */ 
/*  66 */   public static String SERVICE_DOMAIN_NAME = "service.enationsoft.com";
/*     */ 
/*  73 */   public static String USERDATA_STORAGE_PATH = "/user";
/*     */ 
/*  82 */   public static String ADMINTHEMES_STORAGE_PATH = "/adminthemes";
/*     */ 
/*  92 */   public static String THEMES_STORAGE_PATH = "/themes";
/*     */ 
/*  99 */   public static String CONTEXT_PATH = "/";
/*     */ 
/* 102 */   public static String RESOURCEMODE = "1";
/*     */ 
/* 105 */   public static String RUNMODE = "2";
/*     */ 
/* 108 */   public static String DBTYPE = "1";
/*     */ 
/* 111 */   public static String EXTENSION = "do";
/*     */ 
/* 114 */   public static String TEMPLATEENGINE = "on";
/*     */ 
/* 116 */   public static String THUMBNAILCREATOR = "javaimageio";
/*     */ 
/* 118 */   public static String FILE_STORE_PREFIX = "fs:";
/*     */ 
/* 120 */   public static String VERSION = "";
/* 121 */   public static String PRODUCTID = "";
/*     */ 
/* 123 */   public static String INSTALL_LOCK = "NO";
/*     */   public static List<String> safeUrlList;
/* 126 */   public static String BACKEND_PAGESIZE = "15";
/*     */   public static String ENCODING;
/* 128 */   public static boolean SCRIPT_COMPRESS = true;
/* 129 */   public static boolean SCRIPT_CACHE = true;
/*     */ 
/* 132 */   public static int HTTPCACHE = 0;
/* 133 */   public static String DEFAULT_IMG_URL = IMG_SERVER_DOMAIN + "/images/no_picture.jpg";
/*     */ 
/*     */   public static void init(Properties props)
/*     */   {
/* 154 */     String script_compress = props.getProperty("script_compress");
/* 155 */     String script_cache = props.getProperty("script_cache");
/* 156 */     String http_cache = props.getProperty("http_cache");
/*     */ 
/* 158 */     String development_model = props.getProperty("development_model");
/*     */ 
/* 160 */     DEVELOPMENT_MODEL = "true".equals(development_model);
/*     */ 
/* 163 */     if ("false".equals(script_compress))
/* 164 */       SCRIPT_COMPRESS = false;
/*     */     else {
/* 166 */       SCRIPT_COMPRESS = true;
/*     */     }
/*     */ 
/* 169 */     if ("false".equals(script_cache))
/* 170 */       SCRIPT_CACHE = false;
/*     */     else {
/* 172 */       SCRIPT_CACHE = true;
/*     */     }
/*     */ 
/* 175 */     if ("1".equals(http_cache))
/* 176 */       HTTPCACHE = 1;
/*     */     else {
/* 178 */       HTTPCACHE = 0;
/*     */     }
/*     */ 
/* 182 */     String encoding = props.getProperty("encoding");
/* 183 */     ENCODING = StringUtil.isEmpty(encoding) ? "" : encoding;
/*     */ 
/* 186 */     String domain = props.getProperty("domain.imageserver");
/* 187 */     IMG_SERVER_DOMAIN = StringUtil.isEmpty(domain) ? IMG_SERVER_DOMAIN : domain;
/* 188 */     IMG_SERVER_DOMAIN = "http://" + IMG_SERVER_DOMAIN;
/*     */ 
/* 191 */     String userdata_storage_path = props.getProperty("storage.userdata");
/* 192 */     USERDATA_STORAGE_PATH = StringUtil.isEmpty(userdata_storage_path) ? USERDATA_STORAGE_PATH : userdata_storage_path;
/*     */ 
/* 195 */     String adminthemes_storage_path = props.getProperty("storage.adminthemes");
/* 196 */     ADMINTHEMES_STORAGE_PATH = StringUtil.isEmpty(adminthemes_storage_path) ? ADMINTHEMES_STORAGE_PATH : adminthemes_storage_path;
/*     */ 
/* 198 */     String themes_storage_path = props.getProperty("storage.themes");
/* 199 */     THEMES_STORAGE_PATH = StringUtil.isEmpty(themes_storage_path) ? THEMES_STORAGE_PATH : themes_storage_path;
/*     */ 
/* 202 */     String eop_path = props.getProperty("storage.EOPServer");
/* 203 */     EOP_PATH = StringUtil.isEmpty(eop_path) ? EOP_PATH : eop_path;
/*     */ 
/* 206 */     String imageserver_path = props.getProperty("path.imageserver");
/* 207 */     IMG_SERVER_PATH = StringUtil.isEmpty(imageserver_path) ? IMG_SERVER_PATH : imageserver_path;
/*     */ 
/* 210 */     String app_data = props.getProperty("storage.app_data");
/* 211 */     APP_DATA_STORAGE_PATH = StringUtil.isEmpty(app_data) ? APP_DATA_STORAGE_PATH : app_data;
/*     */ 
/* 214 */     String context_path = props.getProperty("path.context_path");
/* 215 */     CONTEXT_PATH = StringUtil.isEmpty(context_path) ? CONTEXT_PATH : context_path;
/*     */ 
/* 218 */     String products_path = props.getProperty("storage.products");
/* 219 */     PRODUCTS_STORAGE_PATH = StringUtil.isEmpty(products_path) ? PRODUCTS_STORAGE_PATH : products_path;
/*     */ 
/* 222 */     String resoucemode = props.getProperty("resourcemode");
/* 223 */     RESOURCEMODE = StringUtil.isEmpty(resoucemode) ? RESOURCEMODE : resoucemode;
/*     */ 
/* 226 */     String runmode = props.getProperty("runmode");
/* 227 */     RUNMODE = StringUtil.isEmpty(runmode) ? RUNMODE : runmode;
/*     */ 
/* 230 */     String dbtype = props.getProperty("dbtype");
/* 231 */     DBTYPE = StringUtil.isEmpty(dbtype) ? DBTYPE : dbtype;
/*     */ 
/* 234 */     String extension = props.getProperty("extension");
/* 235 */     EXTENSION = StringUtil.isEmpty(extension) ? EXTENSION : extension;
/*     */ 
/* 238 */     String templateengine = props.getProperty("templateengine");
/* 239 */     TEMPLATEENGINE = StringUtil.isEmpty(templateengine) ? TEMPLATEENGINE : templateengine;
/*     */ 
/* 241 */     String thumbnailcreator = props.getProperty("thumbnailcreator");
/* 242 */     THUMBNAILCREATOR = StringUtil.isEmpty(thumbnailcreator) ? THUMBNAILCREATOR : thumbnailcreator;
/* 243 */     com.enation.framework.image.ThumbnailCreatorFactory.CREATORTYPE = THUMBNAILCREATOR;
/*     */ 
/* 245 */     VERSION = props.getProperty("version");
/* 246 */     if (VERSION == null) VERSION = "";
/*     */ 
/* 248 */     PRODUCTID = props.getProperty("productid");
/* 249 */     if (PRODUCTID == null) PRODUCTID = "";
/*     */ 
/* 251 */     File installLockFile = new File(StringUtil.getRootPath() + "/install/install.lock");
/* 252 */     if (installLockFile.exists())
/* 253 */       INSTALL_LOCK = "YES";
/*     */     else {
/* 255 */       INSTALL_LOCK = "NO";
/*     */     }
/*     */ 
/* 258 */     String servicedomain = props.getProperty("domain.service");
/* 259 */     SERVICE_DOMAIN_NAME = StringUtil.isEmpty(servicedomain) ? SERVICE_DOMAIN_NAME : servicedomain;
/*     */ 
/* 263 */     String backend_pagesize = props.getProperty("backend.pagesize");
/* 264 */     BACKEND_PAGESIZE = StringUtil.isEmpty(backend_pagesize) ? BACKEND_PAGESIZE : backend_pagesize;
/* 265 */     initSafeUrl();
/*     */   }
/*     */ 
/*     */   private static void initSafeUrl()
/*     */   {
/*     */     try
/*     */     {
/* 278 */       DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
/*     */ 
/* 280 */       DocumentBuilder builder = factory.newDocumentBuilder();
/* 281 */       Document document = builder.parse(FileUtil.getResourceAsStream("safeurl.xml"));
/* 282 */       NodeList urlNodeList = document.getElementsByTagName("urls").item(0).getChildNodes();
/* 283 */       safeUrlList = new ArrayList();
/* 284 */       for (int i = 0; i < urlNodeList.getLength(); i++) {
/* 285 */         Node node = urlNodeList.item(i);
/* 286 */         safeUrlList.add(node.getTextContent());
/*     */       }
/*     */     }
/*     */     catch (IOException e) {
/* 290 */       e.printStackTrace();
/* 291 */       throw new RuntimeException(e);
/*     */     } catch (SAXException e) {
/* 293 */       e.printStackTrace();
/* 294 */       throw new RuntimeException(e);
/*     */     } catch (ParserConfigurationException e) {
/* 296 */       e.printStackTrace();
/* 297 */       throw new RuntimeException(e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static int getDepotType(int depotid)
/*     */   {
/* 307 */     if (depotid == 6)
/* 308 */       return 0;
/* 309 */     return 1;
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/*     */     try
/*     */     {
/* 142 */       InputStream in = FileUtil.getResourceAsStream("eop.properties");
/* 143 */       Properties props = new Properties();
/* 144 */       props.load(in);
/* 145 */       init(props);
/*     */     } catch (Exception e) {
/* 147 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.eop.sdk.context.EopSetting
 * JD-Core Version:    0.6.0
 */