/*     */ package com.enation.app.base.core.action;
/*     */ 
/*     */ import com.enation.app.base.core.model.FriendsLink;
/*     */ import com.enation.app.base.core.service.IFriendsLinkManager;
/*     */ import com.enation.eop.sdk.utils.UploadUtil;
/*     */ import com.enation.framework.action.WWAction;
/*     */ import com.enation.framework.util.FileUtil;
/*     */ import java.io.File;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class FriendsLinkAction extends WWAction
/*     */ {
/*     */   private String id;
/*     */   private FriendsLink friendsLink;
/*     */   private IFriendsLinkManager friendsLinkManager;
/*     */   private List listLink;
/*     */   private File pic;
/*     */   private String picFileName;
/*     */   private String oldpic;
/*     */   private int link_id;
/*     */ 
/*     */   public String list()
/*     */   {
/*  31 */     this.listLink = this.friendsLinkManager.listLink();
/*  32 */     return "list";
/*     */   }
/*     */ 
/*     */   public String add() {
/*  36 */     return "add";
/*     */   }
/*     */ 
/*     */   public String delete() {
/*     */     try {
/*  41 */       this.friendsLinkManager.delete(this.id);
/*  42 */       this.json = "{'result':0,'message':'删除成功'}";
/*     */     } catch (RuntimeException e) {
/*  44 */       this.json = "{'result':1,'message':'删除失败'}";
/*     */     }
/*  46 */     return "json_message";
/*     */   }
/*     */ 
/*     */   public String addSave() {
/*  50 */     if (this.pic != null)
/*  51 */       if (FileUtil.isAllowUp(this.picFileName)) {
/*  52 */         this.friendsLink.setLogo(UploadUtil.upload(this.pic, this.picFileName, "friendslink"));
/*     */       } else {
/*  54 */         this.msgs.add("不允许上传的文件格式，请上传gif,jpg,bmp格式文件。");
/*  55 */         return "message";
/*     */       }
/*     */     try
/*     */     {
/*  59 */       this.friendsLinkManager.add(this.friendsLink);
/*  60 */       this.msgs.add("友情链接添加成功");
/*  61 */       this.urls.put("友情链接列表", "friendsLink!list.do");
/*     */     } catch (Exception e) {
/*  63 */       e.printStackTrace();
/*  64 */       this.msgs.add("友情链接添加失败");
/*  65 */       this.urls.put("友情链接列表", "friendsLink!list.do");
/*     */     }
/*  67 */     return "message";
/*     */   }
/*     */ 
/*     */   public String edit() {
/*  71 */     this.friendsLink = this.friendsLinkManager.get(this.link_id);
/*  72 */     return "edit";
/*     */   }
/*     */   public String editSave() {
/*  75 */     if (this.pic != null)
/*  76 */       if (FileUtil.isAllowUp(this.picFileName)) {
/*  77 */         this.friendsLink.setLogo(UploadUtil.upload(this.pic, this.picFileName, "friendslink"));
/*     */       } else {
/*  79 */         this.msgs.add("不允许上传的文件格式，请上传gif,jpg,bmp格式文件。");
/*  80 */         return "message";
/*     */       }
/*     */     try
/*     */     {
/*  84 */       this.friendsLinkManager.update(this.friendsLink);
/*  85 */       this.msgs.add("友情链接修改成功");
/*  86 */       this.urls.put("友情链接列表", "friendsLink!list.do");
/*     */     } catch (Exception e) {
/*  88 */       e.printStackTrace();
/*  89 */       this.msgs.add("友情链接修改失败");
/*  90 */       this.urls.put("友情链接列表", "!list.do");
/*     */     }
/*  92 */     return "message";
/*     */   }
/*     */ 
/*     */   public String getId() {
/*  96 */     return this.id;
/*     */   }
/*     */   public void setId(String id) {
/*  99 */     this.id = id;
/*     */   }
/*     */   public FriendsLink getFriendsLink() {
/* 102 */     return this.friendsLink;
/*     */   }
/*     */   public void setFriendsLink(FriendsLink friendsLink) {
/* 105 */     this.friendsLink = friendsLink;
/*     */   }
/*     */   public IFriendsLinkManager getFriendsLinkManager() {
/* 108 */     return this.friendsLinkManager;
/*     */   }
/*     */   public void setFriendsLinkManager(IFriendsLinkManager friendsLinkManager) {
/* 111 */     this.friendsLinkManager = friendsLinkManager;
/*     */   }
/*     */   public List getListLink() {
/* 114 */     return this.listLink;
/*     */   }
/*     */   public void setListLink(List listLink) {
/* 117 */     this.listLink = listLink;
/*     */   }
/*     */ 
/*     */   public File getPic() {
/* 121 */     return this.pic;
/*     */   }
/*     */ 
/*     */   public void setPic(File pic) {
/* 125 */     this.pic = pic;
/*     */   }
/*     */ 
/*     */   public String getPicFileName() {
/* 129 */     return this.picFileName;
/*     */   }
/*     */ 
/*     */   public void setPicFileName(String picFileName) {
/* 133 */     this.picFileName = picFileName;
/*     */   }
/*     */ 
/*     */   public String getOldpic() {
/* 137 */     return this.oldpic;
/*     */   }
/*     */ 
/*     */   public void setOldpic(String oldpic) {
/* 141 */     this.oldpic = oldpic;
/*     */   }
/*     */ 
/*     */   public int getLink_id() {
/* 145 */     return this.link_id;
/*     */   }
/*     */ 
/*     */   public void setLink_id(int linkId) {
/* 149 */     this.link_id = linkId;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.app.base.core.action.FriendsLinkAction
 * JD-Core Version:    0.6.0
 */