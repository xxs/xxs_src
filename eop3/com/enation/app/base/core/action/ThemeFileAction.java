/*     */ package com.enation.app.base.core.action;
/*     */ 
/*     */ import com.enation.app.base.core.model.FileNode;
/*     */ import com.enation.app.base.core.service.IExplorerManager;
/*     */ import com.enation.app.base.core.service.impl.ExplorerManager;
/*     */ import com.enation.eop.resource.IThemeManager;
/*     */ import com.enation.eop.resource.model.Theme;
/*     */ import com.enation.eop.sdk.context.EopContext;
/*     */ import com.enation.eop.sdk.context.EopSetting;
/*     */ import com.enation.framework.action.WWAction;
/*     */ import com.enation.framework.util.StringUtil;
/*     */ import java.io.File;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class ThemeFileAction extends WWAction
/*     */ {
/*     */   private IExplorerManager explorerManager;
/*     */   private IThemeManager themeManager;
/*     */   private int themeid;
/*     */   private String name;
/*     */   private String folderName;
/*     */   private String content;
/*     */   private String newName;
/*     */   private String parent;
/*     */   private File file;
/*     */   private String fileFileName;
/*     */   private String newPath;
/*     */   private String type;
/*     */   private List<FileNode> fileList;
/*     */ 
/*     */   private void initExplorer()
/*     */   {
/*  37 */     this.folderName = (StringUtil.isEmpty(this.folderName) ? "" : this.folderName);
/*  38 */     String themePath = this.themeManager.getTheme(Integer.valueOf(this.themeid)).getPath();
/*  39 */     String respath = "";
/*  40 */     if (EopSetting.RESOURCEMODE.equals("1"))
/*  41 */       respath = EopSetting.IMG_SERVER_PATH;
/*     */     else {
/*  43 */       respath = EopSetting.EOP_PATH;
/*     */     }
/*  45 */     String folderPath = EopSetting.EOP_PATH + EopContext.getContext().getContextPath() + "/themes/" + themePath + this.folderName;
/*     */ 
/*  48 */     String styleFldPath = EopSetting.IMG_SERVER_PATH + EopContext.getContext().getContextPath() + "/themes/" + themePath + this.folderName;
/*     */ 
/*  51 */     if ("style".equals(this.type))
/*  52 */       this.explorerManager = new ExplorerManager(styleFldPath);
/*     */     else {
/*  54 */       this.explorerManager = new ExplorerManager(folderPath);
/*     */     }
/*  56 */     if ((this.folderName.equals("")) || (this.folderName.equals("/"))) {
/*  57 */       this.parent = "/";
/*     */     } else {
/*  59 */       this.parent = this.folderName.substring(0, this.folderName.lastIndexOf('/') - 1);
/*  60 */       this.parent = (this.parent.substring(0, this.parent.lastIndexOf('/')) + "/");
/*     */     }
/*     */   }
/*     */ 
/*     */   public String list() {
/*  65 */     initExplorer();
/*     */ 
/*  73 */     this.fileList = this.explorerManager.list(null);
/*  74 */     return "list";
/*     */   }
/*     */ 
/*     */   public String delete() {
/*     */     try {
/*  79 */       initExplorer();
/*  80 */       this.explorerManager.delete(this.name);
/*  81 */       this.msgs.add("文件删除成功");
/*  82 */       this.urls.put("返回目录", "themeFile!list.do?themeid=" + this.themeid + "&folderName=" + this.folderName + "&type=" + this.type);
/*     */     } catch (Exception e) {
/*  84 */       this.urls.put("返回目录", "themeFile!list.do?themeid=" + this.themeid + "&folderName=" + this.folderName + "&type=" + this.type);
/*  85 */       this.msgs.add(e.getMessage());
/*     */     }
/*     */ 
/*  88 */     return "message";
/*     */   }
/*     */ 
/*     */   public String toNewFile()
/*     */   {
/*  93 */     return "new_file";
/*     */   }
/*     */ 
/*     */   public String addFile() {
/*  97 */     if (StringUtil.isEmpty(this.folderName)) this.folderName = "/"; try
/*     */     {
/*  99 */       initExplorer();
/* 100 */       FileNode node = new FileNode();
/* 101 */       node.setContent(this.content);
/* 102 */       node.setName(this.name);
/* 103 */       node.setIsfolder(Integer.valueOf(0));
/* 104 */       this.explorerManager.add(node);
/* 105 */       this.msgs.add("文件创建成功");
/* 106 */       this.urls.put("返回目录", "themeFile!list.do?themeid=" + this.themeid + "&folderName=" + this.folderName + "&type=" + this.type);
/*     */     } catch (Exception e) {
/* 108 */       this.urls.put("返回目录", "themeFile!list.do?themeid=" + this.themeid + "&folderName=" + this.folderName + "&type=" + this.type);
/* 109 */       this.msgs.add(e.getMessage());
/*     */     }
/*     */ 
/* 112 */     return "message";
/*     */   }
/*     */ 
/*     */   public String addFolder() {
/*     */     try {
/* 117 */       initExplorer();
/* 118 */       FileNode node = new FileNode();
/* 119 */       node.setName(this.name);
/* 120 */       node.setIsfolder(Integer.valueOf(1));
/* 121 */       this.explorerManager.add(node);
/* 122 */       this.json = "{result:1}";
/*     */     } catch (Exception e) {
/* 124 */       this.json = ("{result:0,message:'" + e.getMessage() + "'}");
/*     */     }
/* 126 */     return "json_message";
/*     */   }
/*     */ 
/*     */   public String toEdit()
/*     */   {
/* 131 */     initExplorer();
/* 132 */     FileNode node = this.explorerManager.get(this.name);
/* 133 */     this.name = node.getName();
/* 134 */     this.content = node.getContent();
/* 135 */     return "edit_file";
/*     */   }
/*     */ 
/*     */   public String edit()
/*     */   {
/* 140 */     if (StringUtil.isEmpty(this.folderName)) this.folderName = "/";
/*     */     try
/*     */     {
/* 143 */       initExplorer();
/* 144 */       FileNode node = new FileNode();
/* 145 */       node.setContent(this.content);
/* 146 */       node.setName(this.name);
/* 147 */       this.explorerManager.edit(node);
/* 148 */       this.json = "{result:1}";
/* 149 */       this.msgs.add("文件修改成功!");
/* 150 */       this.urls.put("目录", "themeFile!list.do?themeid=" + this.themeid + "&folderName=" + this.folderName + "&type=" + this.type);
/*     */     } catch (Exception e) {
/* 152 */       this.urls.put("目录", "themeFile!list.do?themeid=" + this.themeid + "&folderName=" + this.folderName + "&type=" + this.type);
/* 153 */       this.msgs.add(e.getMessage());
/*     */     }
/*     */ 
/* 156 */     return "message";
/*     */   }
/*     */ 
/*     */   public String rename() {
/*     */     try {
/* 161 */       initExplorer();
/* 162 */       this.explorerManager.rename(this.name, this.newName);
/* 163 */       this.json = "{result:1}";
/*     */     } catch (Exception e) {
/* 165 */       this.json = ("{result:0,message:'" + e.getMessage() + "'}");
/*     */     }
/* 167 */     return "json_message";
/*     */   }
/*     */ 
/*     */   public String upload()
/*     */   {
/*     */     try {
/* 173 */       initExplorer();
/* 174 */       this.explorerManager.upload(this.file, this.fileFileName);
/* 175 */       this.json = "{result:1}";
/*     */     } catch (Exception e) {
/* 177 */       this.json = ("{result:0,message:'" + e.getMessage() + "'}");
/*     */     }
/* 179 */     return "json_message";
/*     */   }
/*     */ 
/*     */   public String move() {
/*     */     try {
/* 184 */       initExplorer();
/* 185 */       this.explorerManager.move(this.name, this.folderName, this.newPath);
/* 186 */       this.json = "{result:1}";
/*     */     } catch (Exception e) {
/* 188 */       this.json = ("{result:0,message:'" + e.getMessage() + "'}");
/*     */     }
/* 190 */     return "json_message";
/*     */   }
/*     */ 
/*     */   public String getName() {
/* 194 */     return this.name;
/*     */   }
/*     */ 
/*     */   public void setName(String name) {
/* 198 */     this.name = name;
/*     */   }
/*     */ 
/*     */   public String getFolderName() {
/* 202 */     if (StringUtil.isEmpty(this.folderName)) this.folderName = "/";
/* 203 */     return this.folderName;
/*     */   }
/*     */ 
/*     */   public void setFolderName(String folderName) {
/* 207 */     this.folderName = folderName;
/*     */   }
/*     */ 
/*     */   public String getContent() {
/* 211 */     return this.content;
/*     */   }
/*     */ 
/*     */   public void setContent(String content) {
/* 215 */     this.content = content;
/*     */   }
/*     */ 
/*     */   public String getNewName() {
/* 219 */     return this.newName;
/*     */   }
/*     */ 
/*     */   public void setNewName(String newName) {
/* 223 */     this.newName = newName;
/*     */   }
/*     */ 
/*     */   public List<FileNode> getFileList() {
/* 227 */     return this.fileList;
/*     */   }
/*     */ 
/*     */   public void setFileList(List<FileNode> fileList) {
/* 231 */     this.fileList = fileList;
/*     */   }
/*     */ 
/*     */   public IThemeManager getThemeManager() {
/* 235 */     return this.themeManager;
/*     */   }
/*     */ 
/*     */   public void setThemeManager(IThemeManager themeManager) {
/* 239 */     this.themeManager = themeManager;
/*     */   }
/*     */ 
/*     */   public int getThemeid() {
/* 243 */     return this.themeid;
/*     */   }
/*     */ 
/*     */   public void setThemeid(int themeid) {
/* 247 */     this.themeid = themeid;
/*     */   }
/*     */ 
/*     */   public String getParent() {
/* 251 */     return this.parent;
/*     */   }
/*     */ 
/*     */   public void setParent(String parent) {
/* 255 */     this.parent = parent;
/*     */   }
/*     */ 
/*     */   public File getFile() {
/* 259 */     return this.file;
/*     */   }
/*     */ 
/*     */   public void setFile(File file) {
/* 263 */     this.file = file;
/*     */   }
/*     */ 
/*     */   public String getFileFileName() {
/* 267 */     return this.fileFileName;
/*     */   }
/*     */ 
/*     */   public void setFileFileName(String fileFileName) {
/* 271 */     this.fileFileName = fileFileName;
/*     */   }
/*     */ 
/*     */   public String getNewPath() {
/* 275 */     return this.newPath;
/*     */   }
/*     */ 
/*     */   public void setNewPath(String newPath) {
/* 279 */     this.newPath = newPath;
/*     */   }
/*     */ 
/*     */   public IExplorerManager getExplorerManager() {
/* 283 */     return this.explorerManager;
/*     */   }
/*     */ 
/*     */   public void setExplorerManager(IExplorerManager explorerManager) {
/* 287 */     this.explorerManager = explorerManager;
/*     */   }
/*     */ 
/*     */   public String getType() {
/* 291 */     return this.type;
/*     */   }
/*     */ 
/*     */   public void setType(String type) {
/* 295 */     this.type = type;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.app.base.core.action.ThemeFileAction
 * JD-Core Version:    0.6.0
 */