/*     */ package com.enation.app.shop.core.action.backend;
/*     */ 
/*     */ import com.enation.app.shop.core.model.FreeOffer;
/*     */ import com.enation.app.shop.core.service.IFreeOfferCategoryManager;
/*     */ import com.enation.app.shop.core.service.IFreeOfferManager;
/*     */ import com.enation.app.shop.core.service.IMemberLvManager;
/*     */ import com.enation.eop.sdk.utils.UploadUtil;
/*     */ import com.enation.framework.action.WWAction;
/*     */ import com.enation.framework.util.FileUtil;
/*     */ import com.enation.framework.util.StringUtil;
/*     */ import java.io.File;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class FreeOfferAction extends WWAction
/*     */ {
/*     */   private String name;
/*     */   private String order;
/*     */   private FreeOffer freeOffer;
/*     */   private Integer fo_id;
/*     */   private String id;
/*     */   private Date mstartdate;
/*     */   private Date menddate;
/*     */   private File thumb;
/*     */   private String thumbFileName;
/*     */   private File pic;
/*     */   private String picFileName;
/*     */   private String oldthumb;
/*     */   private String oldpic;
/*     */   private Integer[] lv_ids;
/*     */   private List categoryList;
/*     */   private List member_lvList;
/*     */   private IFreeOfferManager freeOfferManager;
/*     */   private IFreeOfferCategoryManager freeOfferCategoryManager;
/*     */   private IMemberLvManager memberLvManager;
/*     */ 
/*     */   public String list()
/*     */     throws Exception
/*     */   {
/*  49 */     this.webpage = this.freeOfferManager.list(this.name, this.order, getPage(), getPageSize());
/*     */ 
/*  51 */     return "list";
/*     */   }
/*     */ 
/*     */   public String trash_list()
/*     */   {
/*  57 */     this.webpage = this.freeOfferManager.pageTrash(this.name, this.order, getPage(), getPageSize());
/*     */ 
/*  59 */     return "trash_list";
/*     */   }
/*     */ 
/*     */   public String add() {
/*  63 */     this.categoryList = this.freeOfferCategoryManager.getFreeOfferCategoryList();
/*  64 */     this.member_lvList = this.memberLvManager.list();
/*     */ 
/*  66 */     return "add";
/*     */   }
/*     */ 
/*     */   public String edit() {
/*  70 */     this.categoryList = this.freeOfferCategoryManager.getFreeOfferCategoryList();
/*  71 */     this.member_lvList = this.memberLvManager.list();
/*  72 */     this.freeOffer = this.freeOfferManager.get(this.fo_id.intValue());
/*  73 */     return "edit";
/*     */   }
/*     */ 
/*     */   public String save()
/*     */   {
/*  78 */     if (this.pic != null) {
/*  79 */       if (FileUtil.isAllowUp(this.picFileName)) {
/*  80 */         String[] path = UploadUtil.upload(this.pic, this.picFileName, "gift", 120, 120);
/*  81 */         this.freeOffer.setPic(path[0]);
/*  82 */         this.freeOffer.setList_thumb(path[1]);
/*     */       } else {
/*  84 */         this.msgs.add("不允许上传的文件格式，请上传gif,jpg,bmp格式文件。");
/*  85 */         return "message";
/*     */       }
/*     */     }
/*     */ 
/*  89 */     this.freeOffer.setLv_ids(StringUtil.arrayToString(this.lv_ids, ","));
/*  90 */     this.freeOffer.setStartdate(Long.valueOf(this.mstartdate.getTime()));
/*  91 */     this.freeOffer.setEnddate(Long.valueOf(this.menddate.getTime()));
/*  92 */     this.freeOffer.setDisabled(Integer.valueOf(0));
/*  93 */     this.freeOfferManager.saveAdd(this.freeOffer);
/*  94 */     this.msgs.add("赠品添加成功");
/*  95 */     this.urls.put("赠品列表", "freeOffer!list.do");
/*  96 */     return "message";
/*     */   }
/*     */ 
/*     */   public String saveEdit()
/*     */   {
/* 102 */     if (this.pic != null) {
/* 103 */       if (FileUtil.isAllowUp(this.picFileName))
/*     */       {
/* 105 */         String[] path = UploadUtil.upload(this.pic, this.picFileName, "gift", 120, 120);
/* 106 */         this.freeOffer.setPic(path[0]);
/* 107 */         this.freeOffer.setList_thumb(path[1]);
/* 108 */         UploadUtil.deleteFileAndThumb(this.oldpic);
/*     */       }
/*     */       else {
/* 111 */         this.msgs.add("不允许上传的文件格式，请上传gif,jpg,bmp格式文件。");
/* 112 */         return "message";
/*     */       }
/*     */     }
/* 115 */     this.freeOffer.setLv_ids(StringUtil.arrayToString(this.lv_ids, ","));
/* 116 */     this.freeOffer.setStartdate(Long.valueOf(this.mstartdate.getTime()));
/* 117 */     this.freeOffer.setEnddate(Long.valueOf(this.menddate.getTime()));
/* 118 */     this.freeOfferManager.update(this.freeOffer);
/* 119 */     this.msgs.add("赠品修改成功");
/* 120 */     this.urls.put("赠品列表", "freeOffer!list.do");
/* 121 */     return "message";
/*     */   }
/*     */ 
/*     */   public String delete()
/*     */   {
/*     */     try
/*     */     {
/* 131 */       this.freeOfferManager.delete(this.id);
/* 132 */       this.json = "{'result':0,'message':'删除成功'}";
/*     */     } catch (RuntimeException e) {
/* 134 */       this.json = "{'result':1,'message':'删除失败'}";
/*     */     }
/* 136 */     return "json_message";
/*     */   }
/*     */ 
/*     */   public String revert()
/*     */   {
/*     */     try
/*     */     {
/* 146 */       this.freeOfferManager.revert(this.id);
/* 147 */       this.json = "{'result':0,'message':'还原成功'}";
/*     */     } catch (RuntimeException e) {
/* 149 */       this.json = "{'result':1,'message':'还原失败'}";
/*     */     }
/* 151 */     return "json_message";
/*     */   }
/*     */ 
/*     */   public String clean()
/*     */   {
/*     */     try
/*     */     {
/* 161 */       this.freeOfferManager.clean(this.id);
/* 162 */       this.json = "{'result':0,'message':'清除成功'}";
/*     */     } catch (RuntimeException e) {
/* 164 */       this.json = "{'result':1,'message':'清除失败'}";
/*     */     }
/* 166 */     return "json_message";
/*     */   }
/*     */ 
/*     */   public String getName() {
/* 170 */     return this.name;
/*     */   }
/*     */ 
/*     */   public void setName(String name) {
/* 174 */     this.name = name;
/*     */   }
/*     */ 
/*     */   public String getOrder() {
/* 178 */     return this.order;
/*     */   }
/*     */ 
/*     */   public void setOrder(String order) {
/* 182 */     this.order = order;
/*     */   }
/*     */ 
/*     */   public IFreeOfferManager getFreeOfferManager() {
/* 186 */     return this.freeOfferManager;
/*     */   }
/*     */ 
/*     */   public void setFreeOfferManager(IFreeOfferManager freeOfferManager) {
/* 190 */     this.freeOfferManager = freeOfferManager;
/*     */   }
/*     */ 
/*     */   public FreeOffer getFreeOffer() {
/* 194 */     return this.freeOffer;
/*     */   }
/*     */ 
/*     */   public void setFreeOffer(FreeOffer freeOffer) {
/* 198 */     this.freeOffer = freeOffer;
/*     */   }
/*     */ 
/*     */   public Integer getFo_id() {
/* 202 */     return this.fo_id;
/*     */   }
/*     */ 
/*     */   public void setFo_id(Integer foId) {
/* 206 */     this.fo_id = foId;
/*     */   }
/*     */ 
/*     */   public String getId() {
/* 210 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(String id) {
/* 214 */     this.id = id;
/*     */   }
/*     */ 
/*     */   public Date getMstartdate() {
/* 218 */     return this.mstartdate;
/*     */   }
/*     */ 
/*     */   public void setMstartdate(Date mstartdate) {
/* 222 */     this.mstartdate = mstartdate;
/*     */   }
/*     */ 
/*     */   public Date getMenddate() {
/* 226 */     return this.menddate;
/*     */   }
/*     */ 
/*     */   public void setMenddate(Date menddate) {
/* 230 */     this.menddate = menddate;
/*     */   }
/*     */ 
/*     */   public File getThumb() {
/* 234 */     return this.thumb;
/*     */   }
/*     */ 
/*     */   public void setThumb(File thumb) {
/* 238 */     this.thumb = thumb;
/*     */   }
/*     */ 
/*     */   public String getThumbFileName() {
/* 242 */     return this.thumbFileName;
/*     */   }
/*     */ 
/*     */   public void setThumbFileName(String thumbFileName) {
/* 246 */     this.thumbFileName = thumbFileName;
/*     */   }
/*     */ 
/*     */   public File getPic() {
/* 250 */     return this.pic;
/*     */   }
/*     */ 
/*     */   public void setPic(File pic) {
/* 254 */     this.pic = pic;
/*     */   }
/*     */ 
/*     */   public String getPicFileName() {
/* 258 */     return this.picFileName;
/*     */   }
/*     */ 
/*     */   public void setPicFileName(String picFileName) {
/* 262 */     this.picFileName = picFileName;
/*     */   }
/*     */ 
/*     */   public String getOldthumb() {
/* 266 */     return this.oldthumb;
/*     */   }
/*     */ 
/*     */   public void setOldthumb(String oldthumb) {
/* 270 */     this.oldthumb = oldthumb;
/*     */   }
/*     */ 
/*     */   public String getOldpic() {
/* 274 */     return this.oldpic;
/*     */   }
/*     */ 
/*     */   public void setOldpic(String oldpic) {
/* 278 */     this.oldpic = oldpic;
/*     */   }
/*     */ 
/*     */   public IFreeOfferCategoryManager getFreeOfferCategoryManager() {
/* 282 */     return this.freeOfferCategoryManager;
/*     */   }
/*     */ 
/*     */   public void setFreeOfferCategoryManager(IFreeOfferCategoryManager freeOfferCategoryManager)
/*     */   {
/* 287 */     this.freeOfferCategoryManager = freeOfferCategoryManager;
/*     */   }
/*     */ 
/*     */   public List getCategoryList() {
/* 291 */     return this.categoryList;
/*     */   }
/*     */ 
/*     */   public void setCategoryList(List categoryList) {
/* 295 */     this.categoryList = categoryList;
/*     */   }
/*     */ 
/*     */   public List getMember_lvList() {
/* 299 */     return this.member_lvList;
/*     */   }
/*     */ 
/*     */   public void setMember_lvList(List memberLvList) {
/* 303 */     this.member_lvList = memberLvList;
/*     */   }
/*     */ 
/*     */   public Integer[] getLv_ids() {
/* 307 */     return this.lv_ids;
/*     */   }
/*     */ 
/*     */   public void setLv_ids(Integer[] lvIds) {
/* 311 */     this.lv_ids = lvIds;
/*     */   }
/*     */ 
/*     */   public IMemberLvManager getMemberLvManager() {
/* 315 */     return this.memberLvManager;
/*     */   }
/*     */ 
/*     */   public void setMemberLvManager(IMemberLvManager memberLvManager) {
/* 319 */     this.memberLvManager = memberLvManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.action.backend.FreeOfferAction
 * JD-Core Version:    0.6.0
 */