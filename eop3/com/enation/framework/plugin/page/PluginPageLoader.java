/*    */ package com.enation.framework.plugin.page;
/*    */ 
/*    */ import com.enation.framework.util.StringUtil;
/*    */ import java.io.File;
/*    */ import java.io.PrintStream;
/*    */ 
/*    */ public class PluginPageLoader
/*    */ {
/*    */   private String path;
/*    */   private String type;
/*    */ 
/*    */   public PluginPageLoader(String type, String path)
/*    */   {
/* 28 */     path = path.endsWith("/") ? (path = path.substring(0, path.length() - 1)) : path;
/* 29 */     this.type = type;
/* 30 */     this.path = path;
/* 31 */     initPages();
/*    */   }
/*    */ 
/*    */   private void initPages()
/*    */   {
/* 39 */     String root_path = StringUtil.getRootPath();
/* 40 */     System.out.println("加载插件文件夹：" + root_path + this.path);
/* 41 */     File file = new File(root_path + this.path);
/* 42 */     File[] files = file.listFiles();
/* 43 */     for (File f : files)
/*    */     {
/* 46 */       PluginPageContext.addPage(this.type, this.path + "/" + f.getName());
/*    */     }
/*    */   }
/*    */ 
/*    */   public static void main(String[] args)
/*    */   {
/* 54 */     PluginPageLoader pluginPageLoader = new PluginPageLoader("userlist", "/admin/user/plugin");
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.framework.plugin.page.PluginPageLoader
 * JD-Core Version:    0.6.0
 */