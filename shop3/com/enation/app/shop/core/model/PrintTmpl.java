/*    */ package com.enation.app.shop.core.model;
/*    */ 
/*    */ import com.enation.framework.database.PrimaryKeyField;
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public class PrintTmpl
/*    */   implements Serializable
/*    */ {
/*    */   private Integer prt_tmpl_id;
/*    */   private String prt_tmpl_title;
/*    */   private String shortcut;
/*    */   private String disabled;
/*    */   private Integer prt_tmpl_width;
/*    */   private Integer prt_tmpl_height;
/*    */   private String prt_tmpl_data;
/*    */   private String bgimage;
/*    */ 
/*    */   @PrimaryKeyField
/*    */   public Integer getPrt_tmpl_id()
/*    */   {
/* 24 */     return this.prt_tmpl_id;
/*    */   }
/*    */ 
/*    */   public void setPrt_tmpl_id(Integer prtTmplId) {
/* 28 */     this.prt_tmpl_id = prtTmplId;
/*    */   }
/*    */ 
/*    */   public String getPrt_tmpl_title() {
/* 32 */     return this.prt_tmpl_title;
/*    */   }
/*    */ 
/*    */   public void setPrt_tmpl_title(String prtTmplTitle) {
/* 36 */     this.prt_tmpl_title = prtTmplTitle;
/*    */   }
/*    */ 
/*    */   public String getShortcut() {
/* 40 */     return this.shortcut;
/*    */   }
/*    */ 
/*    */   public void setShortcut(String shortcut) {
/* 44 */     this.shortcut = shortcut;
/*    */   }
/*    */ 
/*    */   public String getDisabled() {
/* 48 */     return this.disabled;
/*    */   }
/*    */ 
/*    */   public void setDisabled(String disabled) {
/* 52 */     this.disabled = disabled;
/*    */   }
/*    */ 
/*    */   public Integer getPrt_tmpl_width() {
/* 56 */     return this.prt_tmpl_width;
/*    */   }
/*    */ 
/*    */   public void setPrt_tmpl_width(Integer prtTmplWidth) {
/* 60 */     this.prt_tmpl_width = prtTmplWidth;
/*    */   }
/*    */ 
/*    */   public Integer getPrt_tmpl_height() {
/* 64 */     return this.prt_tmpl_height;
/*    */   }
/*    */ 
/*    */   public void setPrt_tmpl_height(Integer prtTmplHeight) {
/* 68 */     this.prt_tmpl_height = prtTmplHeight;
/*    */   }
/*    */ 
/*    */   public String getPrt_tmpl_data() {
/* 72 */     return this.prt_tmpl_data;
/*    */   }
/*    */ 
/*    */   public void setPrt_tmpl_data(String prtTmplData) {
/* 76 */     this.prt_tmpl_data = prtTmplData;
/*    */   }
/*    */ 
/*    */   public String getBgimage() {
/* 80 */     return this.bgimage;
/*    */   }
/*    */ 
/*    */   public void setBgimage(String bgimage) {
/* 84 */     this.bgimage = bgimage;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.model.PrintTmpl
 * JD-Core Version:    0.6.0
 */