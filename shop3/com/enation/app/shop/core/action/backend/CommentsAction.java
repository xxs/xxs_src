/*     */ package com.enation.app.shop.core.action.backend;
/*     */ 
/*     */ import com.enation.app.base.core.model.Member;
/*     */ import com.enation.app.shop.core.model.MemberComment;
/*     */ import com.enation.app.shop.core.service.IGoodsManager;
/*     */ import com.enation.app.shop.core.service.IMemberCommentManager;
/*     */ import com.enation.app.shop.core.service.IMemberManager;
/*     */ import com.enation.app.shop.core.service.IMemberOrderItemManager;
/*     */ import com.enation.app.shop.core.service.IMemberPointManger;
/*     */ import com.enation.eop.processor.httpcache.HttpCacheManager;
/*     */ import com.enation.eop.sdk.user.IUserService;
/*     */ import com.enation.eop.sdk.user.UserServiceFactory;
/*     */ import com.enation.eop.sdk.utils.UploadUtil;
/*     */ import com.enation.framework.action.WWAction;
/*     */ import com.enation.framework.util.StringUtil;
/*     */ import java.io.PrintStream;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class CommentsAction extends WWAction
/*     */ {
/*     */   private IMemberCommentManager memberCommentManager;
/*     */   private IMemberPointManger memberPointManger;
/*     */   private IMemberOrderItemManager memberOrderItemManager;
/*     */   private IMemberManager memberManager;
/*     */   private IGoodsManager goodsManager;
/*     */   private int type;
/*     */   private int status;
/*     */   private int comment_id;
/*     */   private MemberComment memberComment;
/*     */   private Member member;
/*     */   private String reply;
/*     */   private String id;
/*     */ 
/*     */   public int getStatus()
/*     */   {
/*  37 */     return this.status;
/*     */   }
/*     */ 
/*     */   public void setStatus(int status) {
/*  41 */     this.status = status;
/*     */   }
/*     */ 
/*     */   public String execute()
/*     */   {
/*  55 */     System.out.println("execute");
/*  56 */     return null;
/*     */   }
/*     */ 
/*     */   public String list()
/*     */   {
/*  65 */     setPageSize(10);
/*  66 */     this.webpage = this.memberCommentManager.getAllComments(getPage(), this.pageSize, this.type);
/*     */ 
/*  68 */     return "bglist";
/*     */   }
/*     */ 
/*     */   public String msgList() {
/*  72 */     this.webpage = this.memberCommentManager.getCommentsByStatus(getPage(), getPageSize(), this.type, this.status);
/*  73 */     return "bglist";
/*     */   }
/*     */ 
/*     */   public String detail() {
/*  77 */     IUserService userService = UserServiceFactory.getUserService();
/*  78 */     int managerid = userService.getCurrentManagerId().intValue();
/*  79 */     this.memberComment = this.memberCommentManager.get(this.comment_id);
/*  80 */     if ((this.memberComment.getMember_id() != null) && (this.memberComment.getMember_id().intValue() != 0))
/*     */     {
/*  82 */       this.member = this.memberManager.get(this.memberComment.getMember_id());
/*     */     }
/*  84 */     if ((this.memberComment != null) && (!StringUtil.isEmpty(this.memberComment.getImg())))
/*     */     {
/*  86 */       this.memberComment.setImgPath(UploadUtil.replacePath(this.memberComment.getImg()));
/*     */     }
/*     */ 
/*  89 */     return "detail";
/*     */   }
/*     */ 
/*     */   public String add()
/*     */   {
/*  98 */     if (StringUtil.isEmpty(this.reply)) {
/*  99 */       this.json = "{'result':1,'message':'回复不能为空！'}";
/* 100 */       return "json_message";
/*     */     }
/* 102 */     MemberComment dbMemberComment = this.memberCommentManager.get(this.comment_id);
/* 103 */     if (dbMemberComment == null) {
/* 104 */       this.json = "{'result':1,'message':'此评论或咨询不存在！'}";
/* 105 */       return "json_message";
/*     */     }
/* 107 */     dbMemberComment.setReply(this.reply);
/* 108 */     dbMemberComment.setReplystatus(1);
/* 109 */     dbMemberComment.setReplytime(System.currentTimeMillis());
/* 110 */     this.memberCommentManager.update(dbMemberComment);
/* 111 */     this.json = "{'result':0,'message':'回复成功！'}";
/* 112 */     return "json_message";
/*     */   }
/*     */ 
/*     */   public String delete() {
/*     */     try {
/* 117 */       if (!StringUtil.isEmpty(this.id)) {
/* 118 */         String[] ids = this.id.split(",");
/* 119 */         for (String tempId : ids) {
/* 120 */           if (!StringUtil.isEmpty(tempId)) {
/* 121 */             this.memberCommentManager.delete(StringUtil.toInt(tempId.replaceAll(" ", "")));
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/* 126 */       this.json = "{'result':0,'message':'操作成功'}";
/*     */     } catch (RuntimeException e) {
/* 128 */       e.printStackTrace();
/* 129 */       this.json = "{'result':1,'message':'操作失败'}";
/*     */     }
/* 131 */     return "json_message";
/*     */   }
/*     */ 
/*     */   public String hide() {
/*     */     try {
/* 136 */       this.memberComment = this.memberCommentManager.get(this.comment_id);
/* 137 */       this.memberComment.setStatus(2);
/* 138 */       this.memberCommentManager.update(this.memberComment);
/* 139 */       this.json = "{'result':0,'message':'操作成功'}";
/*     */     } catch (RuntimeException e) {
/* 141 */       e.printStackTrace();
/* 142 */       this.json = "{'result':1,'message':'操作失败'}";
/*     */     }
/* 144 */     return "json_message";
/*     */   }
/*     */ 
/*     */   public String show() {
/*     */     try {
/* 149 */       this.memberComment = this.memberCommentManager.get(this.comment_id);
/* 150 */       boolean isFirst = false;
/*     */ 
/* 153 */       if ((this.memberCommentManager.getGoodsCommentsCount(this.memberComment.getGoods_id()) == 0) && (this.memberComment.getType() != 2))
/*     */       {
/* 155 */         isFirst = true;
/*     */       }
/*     */ 
/* 158 */       this.memberComment.setStatus(1);
/* 159 */       this.memberCommentManager.update(this.memberComment);
/*     */ 
/* 162 */       Map goods = this.goodsManager.get(Integer.valueOf(this.memberComment.getGoods_id()));
/* 163 */       if (goods != null) {
/* 164 */         String url = "";
/* 165 */         if (goods.get("cat_id") != null)
/* 166 */           switch (StringUtil.toInt(goods.get("cat_id").toString())) {
/*     */           case 1:
/*     */           case 2:
/* 169 */             url = "yxgoods";
/* 170 */             break;
/*     */           case 3:
/*     */           case 4:
/*     */           case 12:
/*     */           case 18:
/* 175 */             url = "jkgoods";
/* 176 */             break;
/*     */           case 6:
/* 178 */             url = "jpgoods";
/* 179 */             break;
/*     */           case 5:
/*     */           case 7:
/*     */           case 8:
/*     */           case 9:
/* 184 */             url = "goods";
/* 185 */             break;
/*     */           case 19:
/* 187 */             url = "gngoods";
/*     */           case 10:
/*     */           case 11:
/*     */           case 13:
/*     */           case 14:
/*     */           case 15:
/*     */           case 16:
/* 191 */           case 17: }  HttpCacheManager.updateUrlModified("/" + url + "-" + this.memberComment.getGoods_id() + ".html");
/*     */       }
/*     */ 
/* 199 */       String reson = "文字评论";
/* 200 */       String type = "comment";
/* 201 */       if (!StringUtil.isEmpty(this.memberComment.getImg())) {
/* 202 */         type = "comment_img";
/* 203 */         reson = "上传图片评论";
/*     */       }
/*     */ 
/* 206 */       if ((this.memberPointManger.checkIsOpen(type)) && 
/* 207 */         (this.memberComment.getMember_id() != null) && (this.memberComment.getMember_id().intValue() != 0) && (this.memberComment.getType() != 2))
/*     */       {
/* 209 */         int point = this.memberPointManger.getItemPoint(type + "_num");
/* 210 */         int mp = this.memberPointManger.getItemPoint(type + "_num_mp");
/* 211 */         this.memberPointManger.add(this.memberComment.getMember_id().intValue(), point, reson, null, mp);
/*     */       }
/*     */ 
/* 218 */       if ((isFirst) && 
/* 219 */         (this.memberPointManger.checkIsOpen("first_comment")))
/*     */       {
/* 221 */         int point = this.memberPointManger.getItemPoint("first_comment_num");
/*     */ 
/* 224 */         int mp = this.memberPointManger.getItemPoint("first_comment_num_mp");
/*     */ 
/* 227 */         this.memberPointManger.add(this.memberComment.getMember_id().intValue(), point, "首次评论", null, mp);
/*     */       }
/*     */ 
/* 232 */       this.json = "{'result':0,'message':'操作成功'}";
/*     */     } catch (RuntimeException e) {
/* 234 */       e.printStackTrace();
/* 235 */       this.json = "{'result':1,'message':'操作失败'}";
/*     */     }
/* 237 */     return "json_message";
/*     */   }
/*     */ 
/*     */   public String deletealone()
/*     */   {
/* 246 */     this.memberComment = this.memberCommentManager.get(this.comment_id);
/* 247 */     if (this.memberComment != null) {
/* 248 */       this.memberCommentManager.delete(this.comment_id);
/*     */     }
/* 250 */     this.msgs.add("删除成功");
/* 251 */     if (this.memberComment != null) {
/* 252 */       this.urls.put("评论列表", "comments!list.do?type=" + this.memberComment.getType());
/*     */     }
/*     */     else {
/* 255 */       this.urls.put("评论列表", "comments!list.do?type=1");
/*     */     }
/* 257 */     return "message";
/*     */   }
/*     */ 
/*     */   public IMemberPointManger getMemberPointManger() {
/* 261 */     return this.memberPointManger;
/*     */   }
/*     */ 
/*     */   public void setMemberPointManger(IMemberPointManger memberPointManger) {
/* 265 */     this.memberPointManger = memberPointManger;
/*     */   }
/*     */ 
/*     */   public void setMemberOrderItemManager(IMemberOrderItemManager memberOrderItemManager)
/*     */   {
/* 270 */     this.memberOrderItemManager = memberOrderItemManager;
/*     */   }
/*     */ 
/*     */   public void setMemberCommentManager(IMemberCommentManager memberCommentManager)
/*     */   {
/* 275 */     this.memberCommentManager = memberCommentManager;
/*     */   }
/*     */ 
/*     */   public int getType() {
/* 279 */     return this.type;
/*     */   }
/*     */ 
/*     */   public void setType(int type) {
/* 283 */     this.type = type;
/*     */   }
/*     */ 
/*     */   public int getComment_id() {
/* 287 */     return this.comment_id;
/*     */   }
/*     */ 
/*     */   public void setComment_id(int comment_id) {
/* 291 */     this.comment_id = comment_id;
/*     */   }
/*     */ 
/*     */   public MemberComment getMemberComment() {
/* 295 */     return this.memberComment;
/*     */   }
/*     */ 
/*     */   public void setMemberManager(IMemberManager memberManager) {
/* 299 */     this.memberManager = memberManager;
/*     */   }
/*     */ 
/*     */   public Member getMember() {
/* 303 */     return this.member;
/*     */   }
/*     */ 
/*     */   public void setMember(Member member) {
/* 307 */     this.member = member;
/*     */   }
/*     */ 
/*     */   public String getReply() {
/* 311 */     return this.reply;
/*     */   }
/*     */ 
/*     */   public void setReply(String reply) {
/* 315 */     this.reply = reply;
/*     */   }
/*     */ 
/*     */   public String getId() {
/* 319 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(String id) {
/* 323 */     this.id = id;
/*     */   }
/*     */ 
/*     */   public void setGoodsManager(IGoodsManager goodsManager) {
/* 327 */     this.goodsManager = goodsManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.action.backend.CommentsAction
 * JD-Core Version:    0.6.0
 */