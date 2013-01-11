/*    */ package com.enation.app.base.core.action;
/*    */ 
/*    */ import com.enation.eop.sdk.utils.UploadUtil;
/*    */ import com.enation.framework.action.WWAction;
/*    */ import java.io.File;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ 
/*    */ public class CkEditorUploadAction extends WWAction
/*    */ {
/*    */   private File upload;
/*    */   private String uploadFileName;
/*    */   private String path;
/*    */   private String funcNum;
/*    */ 
/*    */   public String execute()
/*    */   {
/* 19 */     this.funcNum = getRequest().getParameter("CKEditorFuncNum");
/* 20 */     if ((this.upload != null) && (this.uploadFileName != null)) {
/* 21 */       this.path = UploadUtil.upload(this.upload, this.uploadFileName, "ckeditor");
/* 22 */       this.path = UploadUtil.replacePath(this.path);
/*    */     }
/* 24 */     return "success";
/*    */   }
/*    */ 
/*    */   public File getUpload()
/*    */   {
/* 29 */     return this.upload;
/*    */   }
/*    */ 
/*    */   public void setUpload(File upload) {
/* 33 */     this.upload = upload;
/*    */   }
/*    */ 
/*    */   public String getUploadFileName() {
/* 37 */     return this.uploadFileName;
/*    */   }
/*    */ 
/*    */   public void setUploadFileName(String uploadFileName) {
/* 41 */     this.uploadFileName = uploadFileName;
/*    */   }
/*    */ 
/*    */   public String getPath() {
/* 45 */     return this.path;
/*    */   }
/*    */ 
/*    */   public void setPath(String path) {
/* 49 */     this.path = path;
/*    */   }
/*    */ 
/*    */   public String getFuncNum() {
/* 53 */     return this.funcNum;
/*    */   }
/*    */ 
/*    */   public void setFuncNum(String funcNum) {
/* 57 */     this.funcNum = funcNum;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.app.base.core.action.CkEditorUploadAction
 * JD-Core Version:    0.6.0
 */