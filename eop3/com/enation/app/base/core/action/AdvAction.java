/*     */ package com.enation.app.base.core.action;
/*     */ 
/*     */ import com.enation.app.base.core.model.AdColumn;
/*     */ import com.enation.app.base.core.model.Adv;
/*     */ import com.enation.app.base.core.service.IAdColumnManager;
/*     */ import com.enation.app.base.core.service.IAdvManager;
/*     */ import com.enation.eop.sdk.utils.UploadUtil;
/*     */ import com.enation.framework.action.WWAction;
/*     */ import com.enation.framework.util.FileUtil;
/*     */ import java.io.File;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpSession;
/*     */ 
/*     */ public class AdvAction extends WWAction
/*     */ {
/*     */   private IAdColumnManager adColumnManager;
/*     */   private IAdvManager advManager;
/*     */   private List<AdColumn> adColumnList;
/*     */   private Adv adv;
/*     */   private Long acid;
/*     */   private String advname;
/*     */   private Long advid;
/*     */   private String id;
/*     */   private File pic;
/*     */   private String picFileName;
/*     */   private Date mstartdate;
/*     */   private Date menddate;
/*     */   private String order;
/*     */ 
/*     */   public String list()
/*     */   {
/*  36 */     this.adColumnList = this.adColumnManager.listAllAdvPos();
/*  37 */     this.webpage = this.advManager.search(this.acid, this.advname, getPage(), getPageSize(), this.order);
/*  38 */     return "list";
/*     */   }
/*     */ 
/*     */   public String detail() {
/*  42 */     this.adv = this.advManager.getAdvDetail(this.advid);
/*  43 */     return "detail";
/*     */   }
/*     */ 
/*     */   public String click() {
/*  47 */     if (this.advid.longValue() == 0L) {
/*  48 */       getRequest().setAttribute("gourl", "/eop/shop/adv/zhaozu.jsp");
/*     */     } else {
/*  50 */       this.adv = this.advManager.getAdvDetail(this.advid);
/*     */ 
/*  53 */       if (getRequest().getSession().getAttribute("AD" + this.advid.toString()) == null)
/*     */       {
/*  55 */         getRequest().getSession().setAttribute("AD" + this.advid.toString(), "clicked");
/*     */ 
/*  57 */         this.adv.setClickcount(Integer.valueOf(this.adv.getClickcount().intValue() + 1));
/*  58 */         this.advManager.updateAdv(this.adv);
/*     */       }
/*     */ 
/*  61 */       getRequest().setAttribute("gourl", this.adv.getUrl());
/*     */     }
/*  63 */     return "go";
/*     */   }
/*     */ 
/*     */   public String delete() {
/*     */     try {
/*  68 */       this.advManager.delAdvs(this.id);
/*  69 */       this.json = "{'result':0,'message':'删除成功'}";
/*     */     } catch (RuntimeException e) {
/*  71 */       this.json = "{'result':1,'message':'删除失败'}";
/*     */     }
/*  73 */     return "json_message";
/*     */   }
/*     */ 
/*     */   public String add() {
/*  77 */     this.adColumnList = this.adColumnManager.listAllAdvPos();
/*  78 */     return "add";
/*     */   }
/*     */ 
/*     */   public String addSave() {
/*  82 */     if (this.pic != null)
/*     */     {
/*  84 */       if (FileUtil.isAllowUp(this.picFileName)) {
/*  85 */         String path = UploadUtil.upload(this.pic, this.picFileName, "adv");
/*  86 */         this.adv.setAtturl(path);
/*     */       } else {
/*  88 */         this.msgs.add("不允许上传的文件格式，请上传gif,jpg,bmp,swf格式文件。");
/*  89 */         return "message";
/*     */       }
/*     */     }
/*  92 */     this.adv.setBegintime(Long.valueOf(this.mstartdate.getTime()));
/*  93 */     this.adv.setEndtime(Long.valueOf(this.menddate.getTime()));
/*  94 */     this.adv.setDisabled("false");
/*     */     try {
/*  96 */       this.advManager.addAdv(this.adv);
/*  97 */       this.msgs.add("新增广告成功");
/*  98 */       this.urls.put("广告列表", "adv!list.do");
/*     */     } catch (RuntimeException e) {
/* 100 */       this.msgs.add("新增广告失败");
/* 101 */       this.urls.put("广告列表", "adv!list.do");
/*     */     }
/* 103 */     return "message";
/*     */   }
/*     */ 
/*     */   public String edit() {
/* 107 */     this.adColumnList = this.adColumnManager.listAllAdvPos();
/* 108 */     this.adv = this.advManager.getAdvDetail(this.advid);
/* 109 */     return "edit";
/*     */   }
/*     */ 
/*     */   public String editSave() {
/* 113 */     if (this.pic != null) {
/* 114 */       if (FileUtil.isAllowUp(this.picFileName)) {
/* 115 */         String path = UploadUtil.upload(this.pic, this.picFileName, "adv");
/* 116 */         this.adv.setAtturl(path);
/*     */       } else {
/* 118 */         this.msgs.add("不允许上传的文件格式，请上传gif,jpg,bmp,swf格式文件。");
/* 119 */         return "message";
/*     */       }
/*     */     }
/* 122 */     this.adv.setBegintime(Long.valueOf(this.mstartdate.getTime()));
/* 123 */     this.adv.setEndtime(Long.valueOf(this.menddate.getTime()));
/* 124 */     this.advManager.updateAdv(this.adv);
/* 125 */     this.msgs.add("修改广告成功");
/* 126 */     this.urls.put("广告列表", "adv!list.do");
/* 127 */     return "message";
/*     */   }
/*     */ 
/*     */   public String stop() {
/* 131 */     this.adv = this.advManager.getAdvDetail(this.advid);
/* 132 */     this.adv.setIsclose(Integer.valueOf(1));
/*     */     try {
/* 134 */       this.advManager.updateAdv(this.adv);
/* 135 */       this.json = "{'result':0,'message':'操作成功'}";
/*     */     } catch (RuntimeException e) {
/* 137 */       this.json = "{'result':1,'message':'操作失败'}";
/*     */     }
/* 139 */     return "json_message";
/*     */   }
/*     */ 
/*     */   public String start() {
/* 143 */     this.adv = this.advManager.getAdvDetail(this.advid);
/* 144 */     this.adv.setIsclose(Integer.valueOf(0));
/*     */     try {
/* 146 */       this.advManager.updateAdv(this.adv);
/* 147 */       this.json = "{'result':0,'message':'操作成功'}";
/*     */     } catch (RuntimeException e) {
/* 149 */       this.json = "{'result':1,'message':'操作失败'}";
/*     */     }
/* 151 */     return "json_message";
/*     */   }
/*     */ 
/*     */   public IAdColumnManager getAdColumnManager() {
/* 155 */     return this.adColumnManager;
/*     */   }
/*     */ 
/*     */   public void setAdColumnManager(IAdColumnManager adColumnManager) {
/* 159 */     this.adColumnManager = adColumnManager;
/*     */   }
/*     */ 
/*     */   public IAdvManager getAdvManager() {
/* 163 */     return this.advManager;
/*     */   }
/*     */ 
/*     */   public void setAdvManager(IAdvManager advManager) {
/* 167 */     this.advManager = advManager;
/*     */   }
/*     */ 
/*     */   public Adv getAdv() {
/* 171 */     return this.adv;
/*     */   }
/*     */ 
/*     */   public void setAdv(Adv adv) {
/* 175 */     this.adv = adv;
/*     */   }
/*     */ 
/*     */   public Long getAcid() {
/* 179 */     return this.acid;
/*     */   }
/*     */ 
/*     */   public void setAcid(Long acid) {
/* 183 */     this.acid = acid;
/*     */   }
/*     */ 
/*     */   public Long getAdvid() {
/* 187 */     return this.advid;
/*     */   }
/*     */ 
/*     */   public void setAdvid(Long advid) {
/* 191 */     this.advid = advid;
/*     */   }
/*     */ 
/*     */   public String getId() {
/* 195 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(String id) {
/* 199 */     this.id = id;
/*     */   }
/*     */ 
/*     */   public List<AdColumn> getAdColumnList() {
/* 203 */     return this.adColumnList;
/*     */   }
/*     */ 
/*     */   public void setAdColumnList(List<AdColumn> adColumnList) {
/* 207 */     this.adColumnList = adColumnList;
/*     */   }
/*     */ 
/*     */   public File getPic() {
/* 211 */     return this.pic;
/*     */   }
/*     */ 
/*     */   public void setPic(File pic) {
/* 215 */     this.pic = pic;
/*     */   }
/*     */ 
/*     */   public String getPicFileName() {
/* 219 */     return this.picFileName;
/*     */   }
/*     */ 
/*     */   public void setPicFileName(String picFileName) {
/* 223 */     this.picFileName = picFileName;
/*     */   }
/*     */ 
/*     */   public Date getMstartdate() {
/* 227 */     return this.mstartdate;
/*     */   }
/*     */ 
/*     */   public void setMstartdate(Date mstartdate) {
/* 231 */     this.mstartdate = mstartdate;
/*     */   }
/*     */ 
/*     */   public Date getMenddate() {
/* 235 */     return this.menddate;
/*     */   }
/*     */ 
/*     */   public void setMenddate(Date menddate) {
/* 239 */     this.menddate = menddate;
/*     */   }
/*     */ 
/*     */   public String getOrder() {
/* 243 */     return this.order;
/*     */   }
/*     */ 
/*     */   public void setOrder(String order) {
/* 247 */     this.order = order;
/*     */   }
/*     */ 
/*     */   public String getAdvname() {
/* 251 */     return this.advname;
/*     */   }
/*     */ 
/*     */   public void setAdvname(String advname) {
/* 255 */     this.advname = advname;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.app.base.core.action.AdvAction
 * JD-Core Version:    0.6.0
 */