/*     */ package com.enation.app.shop.component.widget.comments2;
/*     */ 
/*     */ import com.enation.app.base.core.model.Member;
/*     */ import com.enation.app.shop.core.model.MemberComment;
/*     */ import com.enation.app.shop.core.model.MemberOrderItem;
/*     */ import com.enation.app.shop.core.service.IGoodsManager;
/*     */ import com.enation.app.shop.core.service.IMemberCommentManager;
/*     */ import com.enation.app.shop.core.service.IMemberOrderItemManager;
/*     */ import com.enation.eop.processor.MultipartRequestWrapper;
/*     */ import com.enation.eop.sdk.context.EopContext;
/*     */ import com.enation.eop.sdk.context.EopSetting;
/*     */ import com.enation.eop.sdk.user.IUserService;
/*     */ import com.enation.eop.sdk.user.UserServiceFactory;
/*     */ import com.enation.eop.sdk.widget.AbstractWidget;
/*     */ import com.enation.framework.context.webcontext.ThreadContextHolder;
/*     */ import com.enation.framework.database.Page;
/*     */ import com.enation.framework.util.DateUtil;
/*     */ import com.enation.framework.util.FileUtil;
/*     */ import com.enation.framework.util.StringUtil;
/*     */ import java.util.Date;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Map;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javazoom.upload.MultipartFormDataRequest;
/*     */ import javazoom.upload.UploadFile;
/*     */ import org.springframework.context.annotation.Scope;
/*     */ import org.springframework.stereotype.Component;
/*     */ 
/*     */ @Component("comment")
/*     */ @Scope("prototype")
/*     */ public class CommentWidget extends AbstractWidget
/*     */ {
/*     */   private IMemberCommentManager memberCommentManager;
/*     */   private IMemberOrderItemManager memberOrderItemManager;
/*     */   private IGoodsManager goodsManager;
/*     */ 
/*     */   protected void display(Map<String, String> params)
/*     */   {
/*  54 */     if ("discuss_list".equals(this.action))
/*  55 */       list(1);
/*  56 */     else if ("ask_list".equals(this.action))
/*  57 */       list(2);
/*  58 */     else if ("discuss_form".equals(this.action))
/*  59 */       discuss_form();
/*  60 */     else if ("ask_form".equals(this.action))
/*  61 */       ask_form();
/*  62 */     else if ("add".equals(this.action))
/*  63 */       add();
/*     */   }
/*     */ 
/*     */   private void list(int type)
/*     */   {
/*  72 */     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
/*  73 */     int goods_id = StringUtil.toInt(request.getParameter("goods_id"));
/*     */ 
/*  75 */     if (goods_id == 0) {
/*  76 */       Map goods = (Map)ThreadContextHolder.getHttpRequest().getAttribute("goods");
/*  77 */       if (goods != null) {
/*  78 */         goods_id = Integer.valueOf(goods.get("goods_id").toString()).intValue();
/*     */       }
/*     */     }
/*     */ 
/*  82 */     String pageNo = request.getParameter("page");
/*  83 */     pageNo = pageNo == null ? "1" : pageNo;
/*  84 */     int pageSize = 2;
/*     */ 
/*  88 */     Page page = this.memberCommentManager.getGoodsComments(goods_id, StringUtil.toInt(pageNo), pageSize, type);
/*  89 */     putData("page", page);
/*  90 */     putData("pageNo", pageNo);
/*  91 */     putData("pageSize", Integer.valueOf(pageSize));
/*     */ 
/*  94 */     if (type == 1) {
/*  95 */       putData("grade", Integer.valueOf(this.memberCommentManager.getGoodsGrade(goods_id)));
/*  96 */       setActionPageName("discuss_list");
/*     */     }
/*     */ 
/*  99 */     if (type == 2)
/* 100 */       setActionPageName("ask_list");
/*     */   }
/*     */ 
/*     */   private void discuss_form()
/*     */   {
/* 108 */     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
/* 109 */     Integer goodsid = Integer.valueOf(StringUtil.toInt(request.getParameter("goodsid")));
/* 110 */     putData("goodsid", goodsid);
/* 111 */     Member member = UserServiceFactory.getUserService().getCurrentMember();
/* 112 */     if (member == null) {
/* 113 */       putData("isLogin", Boolean.valueOf(false));
/*     */     } else {
/* 115 */       putData("isLogin", Boolean.valueOf(true));
/* 116 */       int buyCount = this.memberOrderItemManager.count(member.getMember_id().intValue(), goodsid.intValue());
/* 117 */       int commentCount = this.memberOrderItemManager.count(member.getMember_id().intValue(), goodsid.intValue(), 1);
/* 118 */       putData("isBuy", Boolean.valueOf(buyCount > 0));
/* 119 */       putData("isCommented", Boolean.valueOf(commentCount >= buyCount));
/*     */     }
/* 121 */     setActionPageName("discuss_form");
/*     */   }
/*     */ 
/*     */   private void ask_form()
/*     */   {
/* 128 */     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
/* 129 */     Integer goodsid = Integer.valueOf(StringUtil.toInt(request.getParameter("goodsid")));
/* 130 */     putData("goodsid", goodsid);
/* 131 */     Member member = UserServiceFactory.getUserService().getCurrentMember();
/* 132 */     if (member == null)
/* 133 */       putData("isLogin", Boolean.valueOf(false));
/*     */     else {
/* 135 */       putData("isLogin", Boolean.valueOf(true));
/*     */     }
/* 137 */     setActionPageName("ask_form");
/*     */   }
/*     */ 
/*     */   private void add()
/*     */   {
/* 144 */     MemberComment memberComment = new MemberComment();
/*     */ 
/* 146 */     String faceField = "img";
/* 147 */     String subFolder = "comment";
/* 148 */     HttpServletRequest requestUpload = ThreadContextHolder.getHttpRequest();
/* 149 */     if (MultipartFormDataRequest.isMultipartFormData(requestUpload)) {
/*     */       try
/*     */       {
/* 152 */         String encoding = EopSetting.ENCODING;
/* 153 */         if (StringUtil.isEmpty(encoding)) {
/* 154 */           encoding = "UTF-8";
/*     */         }
/*     */ 
/* 157 */         MultipartFormDataRequest mrequest = new MultipartFormDataRequest(requestUpload, null, 1048576000, MultipartFormDataRequest.COSPARSER, encoding);
/* 158 */         requestUpload = new MultipartRequestWrapper(requestUpload, mrequest);
/* 159 */         ThreadContextHolder.setHttpRequest(requestUpload);
/*     */ 
/* 161 */         Hashtable files = mrequest.getFiles();
/* 162 */         UploadFile file = (UploadFile)files.get(faceField);
/* 163 */         if (file.getInpuStream() != null) {
/* 164 */           String fileFileName = file.getFileName();
/*     */ 
/* 167 */           String allowTYpe = "gif,jpg,bmp,png";
/* 168 */           if ((!fileFileName.trim().equals("")) && (fileFileName.length() > 0)) {
/* 169 */             String ex = fileFileName.substring(fileFileName.lastIndexOf(".") + 1, fileFileName.length());
/* 170 */             if (allowTYpe.toString().indexOf(ex.toLowerCase()) < 0) {
/* 171 */               showJson("{'result':1,'message':'对不起,只能上传gif,jpg,bmp,png格式的图片！'}");
/* 172 */               return;
/*     */             }
/*     */ 
/*     */           }
/*     */ 
/* 177 */           if (file.getFileSize() > 204800L) {
/* 178 */             showJson("{'result':1,'message':'对不起,图片不能大于200K！'}");
/* 179 */             return;
/*     */           }
/*     */ 
/* 182 */           String fileName = null;
/* 183 */           String filePath = "";
/*     */ 
/* 185 */           String ext = FileUtil.getFileExt(fileFileName);
/* 186 */           fileName = new StringBuilder().append(DateUtil.toString(new Date(), "yyyyMMddHHmmss")).append(StringUtil.getRandStr(4)).append(".").append(ext).toString();
/*     */ 
/* 188 */           filePath = new StringBuilder().append(EopSetting.IMG_SERVER_PATH).append(EopContext.getContext().getContextPath()).append("/attachment/").toString();
/* 189 */           if (subFolder != null) {
/* 190 */             filePath = new StringBuilder().append(filePath).append(subFolder).append("/").toString();
/*     */           }
/*     */ 
/* 193 */           String path = new StringBuilder().append(EopSetting.FILE_STORE_PREFIX).append("/attachment/").append(subFolder == null ? "" : subFolder).append("/").append(fileName).toString();
/*     */ 
/* 195 */           filePath = new StringBuilder().append(filePath).append(fileName).toString();
/* 196 */           FileUtil.createFile(file.getInpuStream(), filePath);
/* 197 */           memberComment.setImg(path);
/*     */         }
/*     */       }
/*     */       catch (Exception ex)
/*     */       {
/*     */       }
/*     */     }
/*     */ 
/* 205 */     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
/* 206 */     int type = StringUtil.toInt(request.getParameter("commenttype"));
/*     */ 
/* 208 */     if ((type != 1) && (type != 2)) {
/* 209 */       showJson("{'result':1,'message':'系统参数错误！'}");
/* 210 */       return;
/*     */     }
/* 212 */     memberComment.setType(type);
/*     */ 
/* 214 */     int goods_id = StringUtil.toInt(request.getParameter("goods_id"));
/* 215 */     if (this.goodsManager.get(Integer.valueOf(goods_id)) == null) {
/* 216 */       showJson("{'result':1,'message':'此商品不存在！'}");
/* 217 */       return;
/*     */     }
/* 219 */     memberComment.setGoods_id(goods_id);
/*     */ 
/* 221 */     String content = request.getParameter("content");
/* 222 */     if (StringUtil.isEmpty(content)) {
/* 223 */       showJson("{'result':1,'message':'评论或咨询内容不能为空！'}");
/* 224 */       return;
/* 225 */     }if (content.length() > 1000) {
/* 226 */       showJson("{'result':1,'message':'请输入1000以内的内容！'}");
/* 227 */       return;
/*     */     }
/* 229 */     content = StringUtil.htmlDecode(content);
/* 230 */     memberComment.setContent(content);
/*     */ 
/* 232 */     Member member = UserServiceFactory.getUserService().getCurrentMember();
/* 233 */     if (type == 1) {
/* 234 */       if (member == null) {
/* 235 */         showJson("{'result':1,'message':'只有登录且成功购买过此商品的用户才能发表评论！'}");
/* 236 */         return;
/*     */       }
/* 238 */       int buyCount = this.memberOrderItemManager.count(member.getMember_id().intValue(), goods_id);
/* 239 */       int commentCount = this.memberOrderItemManager.count(member.getMember_id().intValue(), goods_id, 1);
/* 240 */       if (buyCount <= 0) {
/* 241 */         showJson("{'result':1,'message':'只有成功购买过此商品的用户才能发表评论！'}");
/* 242 */         return;
/*     */       }
/* 244 */       if (commentCount >= buyCount) {
/* 245 */         showJson("{'result':1,'message':'对不起，您已经评论过此商品！'}");
/* 246 */         return;
/*     */       }
/* 248 */       int grade = StringUtil.toInt(request.getParameter("grade"));
/* 249 */       if ((grade < 0) || (grade > 5))
/* 250 */         memberComment.setGrade(5);
/*     */       else
/* 252 */         memberComment.setGrade(grade);
/*     */     }
/*     */     else {
/* 255 */       memberComment.setImg(null);
/* 256 */       memberComment.setGrade(0);
/*     */     }
/* 258 */     memberComment.setMember_id(Integer.valueOf(member == null ? 0 : member.getMember_id().intValue()));
/* 259 */     memberComment.setDateline(System.currentTimeMillis());
/* 260 */     memberComment.setIp(request.getRemoteHost());
/*     */     try
/*     */     {
/* 263 */       this.memberCommentManager.add(memberComment);
/*     */ 
/* 265 */       if (type == 1) {
/* 266 */         MemberOrderItem memberOrderItem = this.memberOrderItemManager.get(member.getMember_id().intValue(), goods_id, 0);
/* 267 */         if (memberOrderItem != null) {
/* 268 */           memberOrderItem.setComment_time(Long.valueOf(System.currentTimeMillis()));
/* 269 */           memberOrderItem.setCommented(Integer.valueOf(1));
/* 270 */           this.memberOrderItemManager.update(memberOrderItem);
/*     */         }
/*     */       }
/*     */ 
/* 274 */       showJson("{'state':0,'message':'发表成功，请等待管理员审核！'}");
/*     */     } catch (RuntimeException e) {
/* 276 */       e.printStackTrace();
/* 277 */       showJson("{'state':1,'message':'发生未知异常'}");
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void config(Map<String, String> params)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void setMemberCommentManager(IMemberCommentManager memberCommentManager)
/*     */   {
/* 288 */     this.memberCommentManager = memberCommentManager;
/*     */   }
/*     */ 
/*     */   public void setMemberOrderItemManager(IMemberOrderItemManager memberOrderItemManager)
/*     */   {
/* 293 */     this.memberOrderItemManager = memberOrderItemManager;
/*     */   }
/*     */ 
/*     */   public void setGoodsManager(IGoodsManager goodsManager) {
/* 297 */     this.goodsManager = goodsManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.widget.comments2.CommentWidget
 * JD-Core Version:    0.6.0
 */