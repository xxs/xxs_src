/*     */ package com.enation.app.shop.component.member.widget;
/*     */ 
/*     */ import com.enation.app.base.core.model.Member;
/*     */ import com.enation.app.base.core.service.IRegionsManager;
/*     */ import com.enation.app.shop.core.service.IMemberManager;
/*     */ import com.enation.app.shop.core.service.IMemberPointManger;
/*     */ import com.enation.eop.processor.MultipartRequestWrapper;
/*     */ import com.enation.eop.sdk.context.EopContext;
/*     */ import com.enation.eop.sdk.context.EopSetting;
/*     */ import com.enation.eop.sdk.widget.AbstractMemberWidget;
/*     */ import com.enation.framework.context.webcontext.ThreadContextHolder;
/*     */ import com.enation.framework.image.IThumbnailCreator;
/*     */ import com.enation.framework.image.ThumbnailCreatorFactory;
/*     */ import com.enation.framework.util.DateUtil;
/*     */ import com.enation.framework.util.FileUtil;
/*     */ import com.enation.framework.util.StringUtil;
/*     */ import java.util.Date;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Map;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javazoom.upload.MultipartFormDataRequest;
/*     */ import javazoom.upload.UploadFile;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.springframework.context.annotation.Scope;
/*     */ import org.springframework.stereotype.Component;
/*     */ 
/*     */ @Component("member_info")
/*     */ @Scope("prototype")
/*     */ public class MemberInfoWidget extends AbstractMemberWidget
/*     */ {
/*     */   private IRegionsManager regionsManager;
/*     */   private IMemberManager memberManager;
/*     */   private IMemberPointManger memberPointManger;
/*     */ 
/*     */   protected void config(Map<String, String> params)
/*     */   {
/*     */   }
/*     */ 
/*     */   protected void display(Map<String, String> params)
/*     */   {
/*  53 */     setMenu("info");
/*     */ 
/*  55 */     setPageName("myinfo");
/*     */ 
/*  58 */     Member member = getCurrentMember();
/*     */ 
/*  60 */     member = this.memberManager.get(member.getMember_id());
/*  61 */     if ("save".equals(this.action))
/*     */     {
/*  64 */       String faceField = "faceFile";
/*  65 */       String subFolder = "face";
/*  66 */       HttpServletRequest requestUpload = ThreadContextHolder.getHttpRequest();
/*  67 */       if (!MultipartFormDataRequest.isMultipartFormData(requestUpload)) throw new RuntimeException("request data is not MultipartFormData"); try
/*     */       {
/*  69 */         String encoding = EopSetting.ENCODING;
/*  70 */         if (StringUtil.isEmpty(encoding)) {
/*  71 */           encoding = "UTF-8";
/*     */         }
/*     */ 
/*  74 */         MultipartFormDataRequest mrequest = new MultipartFormDataRequest(requestUpload, null, 1048576000, MultipartFormDataRequest.COSPARSER, encoding);
/*  75 */         requestUpload = new MultipartRequestWrapper(requestUpload, mrequest);
/*  76 */         ThreadContextHolder.setHttpRequest(requestUpload);
/*     */ 
/*  78 */         Hashtable files = mrequest.getFiles();
/*  79 */         UploadFile file = (UploadFile)files.get(faceField);
/*  80 */         if (file.getInpuStream() != null) {
/*  81 */           String fileFileName = file.getFileName();
/*     */ 
/*  84 */           String allowTYpe = "gif,jpg,bmp,png";
/*  85 */           if ((!fileFileName.trim().equals("")) && (fileFileName.length() > 0)) {
/*  86 */             String ex = fileFileName.substring(fileFileName.lastIndexOf(".") + 1, fileFileName.length());
/*  87 */             if (allowTYpe.toString().indexOf(ex) < 0) {
/*  88 */               showMenu(false);
/*  89 */               showError("对不起,只能上传gif,jpg,bmp,png格式的头像！");
/*  90 */               return;
/*     */             }
/*     */ 
/*     */           }
/*     */ 
/*  95 */           if (file.getFileSize() > 204800L) {
/*  96 */             showMenu(false);
/*  97 */             showError("对不起,头像图片不能大于200K！");
/*  98 */             return;
/*     */           }
/*     */ 
/* 101 */           String fileName = null;
/* 102 */           String filePath = "";
/*     */ 
/* 104 */           String ext = FileUtil.getFileExt(fileFileName);
/* 105 */           fileName = new StringBuilder().append(DateUtil.toString(new Date(), "yyyyMMddHHmmss")).append(StringUtil.getRandStr(4)).append(".").append(ext).toString();
/*     */ 
/* 107 */           filePath = new StringBuilder().append(EopSetting.IMG_SERVER_PATH).append(EopContext.getContext().getContextPath()).append("/attachment/").toString();
/* 108 */           if (subFolder != null) {
/* 109 */             filePath = new StringBuilder().append(filePath).append(subFolder).append("/").toString();
/*     */           }
/*     */ 
/* 112 */           String path = new StringBuilder().append(EopSetting.FILE_STORE_PREFIX).append("/attachment/").append(subFolder == null ? "" : subFolder).append("/").append(fileName).toString();
/*     */ 
/* 114 */           filePath = new StringBuilder().append(filePath).append(fileName).toString();
/* 115 */           FileUtil.createFile(file.getInpuStream(), filePath);
/* 116 */           IThumbnailCreator thumbnailCreator = ThumbnailCreatorFactory.getCreator(filePath, filePath);
/* 117 */           thumbnailCreator.resize(120, 120);
/* 118 */           member.setFace(path);
/*     */         }
/*     */       }
/*     */       catch (Exception ex)
/*     */       {
/*     */       }
/*     */ 
/* 125 */       HttpServletRequest request = ThreadContextHolder.getHttpRequest();
/* 126 */       String name = request.getParameter("member.name");
/* 127 */       if (StringUtil.isEmpty(name)) {
/* 128 */         showMenu(false);
/* 129 */         showError("真实姓名不能为空！");
/* 130 */         return;
/*     */       }
/* 132 */       member.setName(name);
/* 133 */       String sex = request.getParameter("member.sex");
/* 134 */       member.setSex(Integer.valueOf(sex));
/* 135 */       String birthday = request.getParameter("mybirthday");
/* 136 */       if (!StringUtil.isEmpty(birthday)) {
/* 137 */         if (DateUtil.toDate(birthday, "yyyy-MM-dd").getTime() > System.currentTimeMillis()) {
/* 138 */           showMenu(false);
/* 139 */           showError("请您填写正确的生日！");
/* 140 */           return;
/*     */         }
/* 142 */         member.setBirthday(Long.valueOf(DateUtil.toDate(birthday, "yyyy-MM-dd").getTime()));
/*     */       }
/*     */ 
/* 145 */       String province_id = request.getParameter("province_id");
/* 146 */       member.setProvince_id(Integer.valueOf(province_id));
/* 147 */       String city_id = request.getParameter("city_id");
/* 148 */       member.setCity_id(Integer.valueOf(city_id));
/* 149 */       String region_id = request.getParameter("region_id");
/* 150 */       member.setRegion_id(Integer.valueOf(region_id));
/* 151 */       String province = request.getParameter("province");
/* 152 */       member.setProvince(province);
/* 153 */       String city = request.getParameter("city");
/* 154 */       member.setCity(city);
/* 155 */       String region = request.getParameter("region");
/* 156 */       member.setRegion(region);
/* 157 */       String address = request.getParameter("member.address");
/* 158 */       member.setAddress(address);
/* 159 */       String zip = request.getParameter("member.zip");
/* 160 */       member.setZip(zip);
/* 161 */       String mobile = request.getParameter("member.mobile");
/* 162 */       member.setMobile(mobile);
/* 163 */       String tel = request.getParameter("member.tel");
/* 164 */       member.setTel(tel);
/* 165 */       member.setNickname(member.getUname());
/*     */ 
/* 167 */       String midentity = request.getParameter("member.midentity");
/* 168 */       if (!StringUtil.isEmpty(midentity))
/* 169 */         member.setMidentity(Integer.valueOf(StringUtil.toInt(midentity)));
/*     */       else {
/* 171 */         member.setMidentity(Integer.valueOf(0));
/*     */       }
/*     */ 
/*     */       try
/*     */       {
/* 181 */         boolean addPoint = false;
/* 182 */         if ((member.getInfo_full() == 0) && (!StringUtil.isEmpty(member.getName())) && (!StringUtil.isEmpty(member.getNickname())) && (!StringUtil.isEmpty(member.getProvince())) && (!StringUtil.isEmpty(member.getCity())) && (!StringUtil.isEmpty(member.getRegion())) && ((!StringUtil.isEmpty(member.getMobile())) || (!StringUtil.isEmpty(member.getTel()))))
/*     */         {
/* 189 */           addPoint = true;
/*     */         }
/*     */ 
/* 192 */         if (addPoint) {
/* 193 */           member.setInfo_full(1);
/* 194 */           this.memberManager.edit(member);
/* 195 */           if (this.memberPointManger.checkIsOpen("finish_profile")) {
/* 196 */             int point = this.memberPointManger.getItemPoint("finish_profile_num");
/* 197 */             int mp = this.memberPointManger.getItemPoint("finish_profile_num_mp");
/* 198 */             this.memberPointManger.add(member.getMember_id().intValue(), point, "完善个人资料", null, mp);
/*     */           }
/*     */         } else {
/* 201 */           this.memberManager.edit(member);
/*     */         }
/*     */ 
/* 205 */         showMenu(false);
/* 206 */         showSuccess("编辑个人资料成功！", "完善基本资料", "member_info.html");
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 210 */         if (this.logger.isDebugEnabled()) {
/* 211 */           this.logger.error(e.getStackTrace());
/*     */         }
/*     */ 
/* 214 */         showMenu(false);
/* 215 */         showError("编辑个人资料失败！", "完善基本资料", "member_info.html");
/*     */       }
/*     */     } else {
/* 218 */       Long mybirthday = member.getBirthday();
/*     */ 
/* 220 */       putData("member", member);
/*     */ 
/* 222 */       if (mybirthday == null)
/* 223 */         putData("mybirthday", DateUtil.toDate("1970-01-01", "yyyy-MM-dd"));
/*     */       else
/* 225 */         putData("mybirthday", new Date(mybirthday.longValue()));
/*     */     }
/*     */   }
/*     */ 
/*     */   public IRegionsManager getRegionsManager()
/*     */   {
/* 231 */     return this.regionsManager;
/*     */   }
/*     */ 
/*     */   public void setRegionsManager(IRegionsManager regionsManager) {
/* 235 */     this.regionsManager = regionsManager;
/*     */   }
/*     */ 
/*     */   public IMemberManager getMemberManager() {
/* 239 */     return this.memberManager;
/*     */   }
/*     */ 
/*     */   public void setMemberManager(IMemberManager memberManager) {
/* 243 */     this.memberManager = memberManager;
/*     */   }
/*     */ 
/*     */   public void setMemberPointManger(IMemberPointManger memberPointManger)
/*     */   {
/* 248 */     this.memberPointManger = memberPointManger;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.member.widget.MemberInfoWidget
 * JD-Core Version:    0.6.0
 */