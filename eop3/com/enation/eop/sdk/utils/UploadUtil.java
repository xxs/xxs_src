/*     */ package com.enation.eop.sdk.utils;
/*     */ 
/*     */ import com.enation.eop.processor.MultipartRequestWrapper;
/*     */ import com.enation.eop.sdk.context.EopContext;
/*     */ import com.enation.eop.sdk.context.EopSetting;
/*     */ import com.enation.framework.context.webcontext.ThreadContextHolder;
/*     */ import com.enation.framework.image.IThumbnailCreator;
/*     */ import com.enation.framework.image.ThumbnailCreatorFactory;
/*     */ import com.enation.framework.util.DateUtil;
/*     */ import com.enation.framework.util.FileUtil;
/*     */ import com.enation.framework.util.StringUtil;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.util.Date;
/*     */ import java.util.Hashtable;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javazoom.upload.MultipartFormDataRequest;
/*     */ import javazoom.upload.UploadException;
/*     */ import javazoom.upload.UploadFile;
/*     */ 
/*     */ public class UploadUtil
/*     */ {
/*     */   public static String uploadUseRequest(String name, String subFolder, String allowext)
/*     */   {
/*  33 */     if (name == null) throw new IllegalArgumentException("file or filename object is null");
/*  34 */     if (subFolder == null) throw new IllegalArgumentException("subFolder is null");
/*  35 */     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
/*  36 */     if (!MultipartFormDataRequest.isMultipartFormData(request)) throw new RuntimeException("request data is not MultipartFormData");
/*     */     try
/*     */     {
/*  39 */       String encoding = EopSetting.ENCODING;
/*  40 */       if (StringUtil.isEmpty(encoding)) {
/*  41 */         encoding = "UTF-8";
/*     */       }
/*     */ 
/*  44 */       MultipartFormDataRequest mrequest = new MultipartFormDataRequest(request, null, 1048576000, MultipartFormDataRequest.COSPARSER, encoding);
/*  45 */       request = new MultipartRequestWrapper(request, mrequest);
/*  46 */       ThreadContextHolder.setHttpRequest(request);
/*     */ 
/*  48 */       Hashtable files = mrequest.getFiles();
/*  49 */       UploadFile file = (UploadFile)files.get(name);
/*  50 */       if (file.getInpuStream() == null) return null;
/*     */ 
/*  52 */       String fileFileName = file.getFileName();
/*     */ 
/*  56 */       String fileName = null;
/*  57 */       String filePath = "";
/*     */ 
/*  59 */       String ext = FileUtil.getFileExt(fileFileName);
/*     */ 
/*  61 */       if (!allowext.equals(ext)) {
/*  62 */         throw new IllegalArgumentException("不被允许的上传文件类型");
/*     */       }
/*     */ 
/*  65 */       fileName = new StringBuilder().append(DateUtil.toString(new Date(), "yyyyMMddHHmmss")).append(StringUtil.getRandStr(4)).append(".").append(ext).toString();
/*     */ 
/*  67 */       filePath = new StringBuilder().append(EopSetting.IMG_SERVER_PATH).append(EopContext.getContext().getContextPath()).append("/attachment/").toString();
/*  68 */       if (subFolder != null) {
/*  69 */         filePath = new StringBuilder().append(filePath).append(subFolder).append("/").toString();
/*     */       }
/*     */ 
/*  72 */       String path = new StringBuilder().append(EopSetting.FILE_STORE_PREFIX).append("/attachment/").append(subFolder == null ? "" : subFolder).append("/").append(fileName).toString();
/*     */ 
/*  74 */       filePath = new StringBuilder().append(filePath).append(fileName).toString();
/*  75 */       FileUtil.createFile(file.getInpuStream(), filePath);
/*     */ 
/*  77 */       return path;
/*     */     }
/*     */     catch (UploadException e)
/*     */     {
/*  82 */       e.printStackTrace();
/*     */     }
/*     */     catch (IOException e) {
/*  85 */       e.printStackTrace();
/*     */     }
/*     */ 
/*  93 */     return null;
/*     */   }
/*     */ 
/*     */   public static String uploadUseRequest(String name, String subFolder)
/*     */   {
/* 103 */     if (name == null) throw new IllegalArgumentException("file or filename object is null");
/* 104 */     if (subFolder == null) throw new IllegalArgumentException("subFolder is null");
/* 105 */     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
/* 106 */     if (!MultipartFormDataRequest.isMultipartFormData(request)) throw new RuntimeException("request data is not MultipartFormData");
/*     */     try
/*     */     {
/* 109 */       String encoding = EopSetting.ENCODING;
/* 110 */       if (StringUtil.isEmpty(encoding)) {
/* 111 */         encoding = "UTF-8";
/*     */       }
/*     */ 
/* 114 */       MultipartFormDataRequest mrequest = new MultipartFormDataRequest(request, null, 1048576000, MultipartFormDataRequest.COSPARSER, encoding);
/* 115 */       request = new MultipartRequestWrapper(request, mrequest);
/* 116 */       ThreadContextHolder.setHttpRequest(request);
/*     */ 
/* 118 */       Hashtable files = mrequest.getFiles();
/* 119 */       UploadFile file = (UploadFile)files.get(name);
/* 120 */       if (file.getInpuStream() == null) return null;
/*     */ 
/* 122 */       String fileFileName = file.getFileName();
/*     */ 
/* 124 */       String fileName = null;
/* 125 */       String filePath = "";
/*     */ 
/* 127 */       String ext = FileUtil.getFileExt(fileFileName);
/* 128 */       fileName = new StringBuilder().append(DateUtil.toString(new Date(), "yyyyMMddHHmmss")).append(StringUtil.getRandStr(4)).append(".").append(ext).toString();
/*     */ 
/* 130 */       filePath = new StringBuilder().append(EopSetting.IMG_SERVER_PATH).append(EopContext.getContext().getContextPath()).append("/attachment/").toString();
/* 131 */       if (subFolder != null) {
/* 132 */         filePath = new StringBuilder().append(filePath).append(subFolder).append("/").toString();
/*     */       }
/*     */ 
/* 135 */       String path = new StringBuilder().append(EopSetting.FILE_STORE_PREFIX).append("/attachment/").append(subFolder == null ? "" : subFolder).append("/").append(fileName).toString();
/*     */ 
/* 137 */       filePath = new StringBuilder().append(filePath).append(fileName).toString();
/* 138 */       FileUtil.createFile(file.getInpuStream(), filePath);
/*     */ 
/* 140 */       return path;
/*     */     }
/*     */     catch (UploadException e)
/*     */     {
/* 144 */       e.printStackTrace();
/*     */     } catch (IOException e) {
/* 146 */       e.printStackTrace();
/*     */     }
/*     */ 
/* 154 */     return null;
/*     */   }
/*     */ 
/*     */   public static String upload(File file, String fileFileName, String subFolder)
/*     */   {
/* 170 */     if ((file == null) || (fileFileName == null)) throw new IllegalArgumentException("file or filename object is null");
/* 171 */     if (subFolder == null) throw new IllegalArgumentException("subFolder is null");
/*     */ 
/* 173 */     if (!FileUtil.isAllowUp(fileFileName)) {
/* 174 */       throw new IllegalArgumentException("不被允许的上传文件类型");
/*     */     }
/*     */ 
/* 177 */     String fileName = null;
/* 178 */     String filePath = "";
/*     */ 
/* 180 */     String ext = FileUtil.getFileExt(fileFileName);
/* 181 */     fileName = new StringBuilder().append(DateUtil.toString(new Date(), "yyyyMMddHHmmss")).append(StringUtil.getRandStr(4)).append(".").append(ext).toString();
/*     */ 
/* 183 */     filePath = new StringBuilder().append(EopSetting.IMG_SERVER_PATH).append(EopContext.getContext().getContextPath()).append("/attachment/").toString();
/* 184 */     if (subFolder != null) {
/* 185 */       filePath = new StringBuilder().append(filePath).append(subFolder).append("/").toString();
/*     */     }
/*     */ 
/* 188 */     String path = new StringBuilder().append(EopSetting.FILE_STORE_PREFIX).append("/attachment/").append(subFolder == null ? "" : subFolder).append("/").append(fileName).toString();
/*     */ 
/* 190 */     filePath = new StringBuilder().append(filePath).append(fileName).toString();
/* 191 */     FileUtil.createFile(file, filePath);
/*     */ 
/* 193 */     return path;
/*     */   }
/*     */ 
/*     */   public static String replacePath(String path)
/*     */   {
/* 199 */     if (StringUtil.isEmpty(path)) return path;
/*     */ 
/* 202 */     return path.replaceAll(EopSetting.FILE_STORE_PREFIX, new StringBuilder().append(EopSetting.IMG_SERVER_DOMAIN).append(EopContext.getContext().getContextPath()).toString());
/*     */   }
/*     */ 
/*     */   public static String[] upload(File file, String fileFileName, String subFolder, int width, int height)
/*     */   {
/* 218 */     if ((file == null) || (fileFileName == null)) throw new IllegalArgumentException("file or filename object is null");
/* 219 */     if (subFolder == null) throw new IllegalArgumentException("subFolder is null");
/* 220 */     String fileName = null;
/* 221 */     String filePath = "";
/* 222 */     String[] path = new String[2];
/* 223 */     if (!FileUtil.isAllowUp(fileFileName)) {
/* 224 */       throw new IllegalArgumentException("不被允许的上传文件类型");
/*     */     }
/* 226 */     String ext = FileUtil.getFileExt(fileFileName);
/* 227 */     fileName = new StringBuilder().append(DateUtil.toString(new Date(), "yyyyMMddHHmmss")).append(StringUtil.getRandStr(4)).append(".").append(ext).toString();
/*     */ 
/* 229 */     filePath = new StringBuilder().append(EopSetting.IMG_SERVER_PATH).append(getContextFolder()).append("/attachment/").toString();
/* 230 */     if (subFolder != null) {
/* 231 */       filePath = new StringBuilder().append(filePath).append(subFolder).append("/").toString();
/*     */     }
/*     */ 
/* 234 */     path[0] = new StringBuilder().append(EopSetting.IMG_SERVER_DOMAIN).append(getContextFolder()).append("/attachment/").append(subFolder == null ? "" : subFolder).append("/").append(fileName).toString();
/* 235 */     filePath = new StringBuilder().append(filePath).append(fileName).toString();
/* 236 */     FileUtil.createFile(file, filePath);
/* 237 */     String thumbName = getThumbPath(filePath, "_thumbnail");
/*     */ 
/* 239 */     IThumbnailCreator thumbnailCreator = ThumbnailCreatorFactory.getCreator(filePath, thumbName);
/* 240 */     thumbnailCreator.resize(width, height);
/* 241 */     path[1] = getThumbPath(path[0], "_thumbnail");
/* 242 */     return path;
/*     */   }
/*     */ 
/*     */   public static void deleteFile(String filePath)
/*     */   {
/* 253 */     filePath = filePath.replaceAll(EopSetting.IMG_SERVER_DOMAIN, EopSetting.IMG_SERVER_PATH);
/* 254 */     FileUtil.delete(filePath);
/*     */   }
/*     */ 
/*     */   public static void deleteFileAndThumb(String filePath)
/*     */   {
/* 264 */     filePath = filePath.replaceAll(EopSetting.IMG_SERVER_DOMAIN, EopSetting.IMG_SERVER_PATH);
/* 265 */     FileUtil.delete(filePath);
/* 266 */     FileUtil.delete(getThumbPath(filePath, "_thumbnail"));
/*     */   }
/*     */ 
/*     */   private static String getContextFolder()
/*     */   {
/* 272 */     return EopContext.getContext().getContextPath();
/*     */   }
/*     */ 
/*     */   public static String getThumbPath(String filePath, String shortName)
/*     */   {
/* 282 */     String pattern = "(.*)([\\.])(.*)";
/* 283 */     String thumbPath = new StringBuilder().append("$1").append(shortName).append("$2$3").toString();
/*     */ 
/* 285 */     Pattern p = Pattern.compile(pattern, 34);
/* 286 */     Matcher m = p.matcher(filePath);
/* 287 */     if (m.find()) {
/* 288 */       String s = m.replaceAll(thumbPath);
/*     */ 
/* 290 */       return s;
/*     */     }
/* 292 */     return null;
/*     */   }
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/* 297 */     System.out.println(getThumbPath("http://static.eop.com/user/1/1/attachment/goods/2001010101030.jpg", "_thumbnail"));
/* 298 */     System.out.println(getThumbPath("/user/1/1/attachment/goods/2001010101030.jpg", "_thumbnail"));
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.eop.sdk.utils.UploadUtil
 * JD-Core Version:    0.6.0
 */