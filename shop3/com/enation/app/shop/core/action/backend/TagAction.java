/*     */ package com.enation.app.shop.core.action.backend;
/*     */ 
/*     */ import com.enation.app.shop.core.model.Tag;
/*     */ import com.enation.app.shop.core.service.ITagManager;
/*     */ import com.enation.framework.action.WWAction;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class TagAction extends WWAction
/*     */ {
/*     */   private ITagManager tagManager;
/*     */   private Tag tag;
/*     */   private Integer[] tag_ids;
/*     */   private Integer tag_id;
/*     */ 
/*     */   public String checkJoinGoods()
/*     */   {
/*  20 */     if (this.tagManager.checkJoinGoods(this.tag_ids))
/*  21 */       this.json = "{result:1}";
/*     */     else {
/*  23 */       this.json = "{result:0}";
/*     */     }
/*  25 */     return "json_message";
/*     */   }
/*     */ 
/*     */   public String checkname() {
/*  29 */     if (this.tagManager.checkname(this.tag.getTag_name(), this.tag.getTag_id()))
/*  30 */       this.json = "{result:1}";
/*     */     else {
/*  32 */       this.json = "{result:0}";
/*     */     }
/*  34 */     return "json_message";
/*     */   }
/*     */ 
/*     */   public String add()
/*     */   {
/*  39 */     return "add";
/*     */   }
/*     */ 
/*     */   public String edit() {
/*  43 */     this.tag = this.tagManager.getById(this.tag_id);
/*  44 */     return "edit";
/*     */   }
/*     */ 
/*     */   public String saveAdd()
/*     */   {
/*  50 */     this.tagManager.add(this.tag);
/*  51 */     this.msgs.add("标签添加成功");
/*  52 */     this.urls.put("标签列表", "tag!list.do");
/*  53 */     return "message";
/*     */   }
/*     */ 
/*     */   public String saveEdit()
/*     */   {
/*  60 */     this.tagManager.update(this.tag);
/*     */ 
/*  62 */     this.msgs.add("标签修改成功");
/*  63 */     this.urls.put("标签列表", "tag!list.do");
/*     */ 
/*  65 */     return "message";
/*     */   }
/*     */ 
/*     */   public String delete()
/*     */   {
/*  70 */     this.tagManager.delete(this.tag_ids);
/*  71 */     this.json = "{'result':0,'message':'删除成功'}";
/*     */ 
/*  73 */     return "json_message";
/*     */   }
/*     */ 
/*     */   public String list() {
/*  77 */     this.webpage = this.tagManager.list(getPage(), getPageSize());
/*  78 */     return "list";
/*     */   }
/*     */ 
/*     */   public ITagManager getTagManager() {
/*  82 */     return this.tagManager;
/*     */   }
/*     */ 
/*     */   public void setTagManager(ITagManager tagManager) {
/*  86 */     this.tagManager = tagManager;
/*     */   }
/*     */ 
/*     */   public Tag getTag() {
/*  90 */     return this.tag;
/*     */   }
/*     */ 
/*     */   public void setTag(Tag tag) {
/*  94 */     this.tag = tag;
/*     */   }
/*     */ 
/*     */   public Integer[] getTag_ids() {
/*  98 */     return this.tag_ids;
/*     */   }
/*     */ 
/*     */   public void setTag_ids(Integer[] tagIds) {
/* 102 */     this.tag_ids = tagIds;
/*     */   }
/*     */ 
/*     */   public Integer getTag_id() {
/* 106 */     return this.tag_id;
/*     */   }
/*     */ 
/*     */   public void setTag_id(Integer tagId) {
/* 110 */     this.tag_id = tagId;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.action.backend.TagAction
 * JD-Core Version:    0.6.0
 */