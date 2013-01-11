/*     */ package com.enation.app.shop.component.member.widget;
/*     */ 
/*     */ import com.enation.app.base.core.model.Member;
/*     */ import com.enation.app.base.core.service.IRegionsManager;
/*     */ import com.enation.app.shop.core.service.IMemberManager;
/*     */ import com.enation.app.shop.core.service.IMemberPointManger;
/*     */ import com.enation.eop.processor.MultipartRequestWrapper;
/*     */ import com.enation.eop.sdk.context.EopContext;
/*     */ import com.enation.eop.sdk.context.EopSetting;
/*     */ import com.enation.eop.sdk.user.IUserService;
/*     */ import com.enation.eop.sdk.user.UserServiceFactory;
/*     */ import com.enation.eop.sdk.widget.AbstractMemberWidget;
/*     */ import com.enation.framework.context.webcontext.ThreadContextHolder;
/*     */ import com.enation.framework.image.IThumbnailCreator;
/*     */ import com.enation.framework.image.ThumbnailCreatorFactory;
/*     */ import com.enation.framework.util.DateUtil;
/*     */ import com.enation.framework.util.FileUtil;
/*     */ import com.enation.framework.util.StringUtil;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.Hashtable;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javazoom.upload.MultipartFormDataRequest;
/*     */ import javazoom.upload.UploadFile;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class MemberInfoOldWidget extends AbstractMemberWidget
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
/*  50 */     setMenu("info");
/*     */ 
/*  52 */     setPageName("myinfo");
/*  53 */     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
/*     */ 
/*  55 */     IUserService userService = UserServiceFactory.getUserService();
/*  56 */     Member member = userService.getCurrentMember();
/*     */ 
/*  58 */     member = this.memberManager.get(member.getMember_id());
/*  59 */     if ("save".equals(this.action))
/*     */     {
/*  62 */       String faceField = "faceFile";
/*  63 */       String subFolder = "face";
/*  64 */       HttpServletRequest requestUpload = ThreadContextHolder.getHttpRequest();
/*  65 */       if (!MultipartFormDataRequest.isMultipartFormData(requestUpload)) throw new RuntimeException("request data is not MultipartFormData"); try
/*     */       {
/*  67 */         String encoding = EopSetting.ENCODING;
/*  68 */         if (StringUtil.isEmpty(encoding)) {
/*  69 */           encoding = "UTF-8";
/*     */         }
/*     */ 
/*  72 */         MultipartFormDataRequest mrequest = new MultipartFormDataRequest(requestUpload, null, 1048576000, MultipartFormDataRequest.COSPARSER, encoding);
/*  73 */         requestUpload = new MultipartRequestWrapper(requestUpload, mrequest);
/*  74 */         ThreadContextHolder.setHttpRequest(requestUpload);
/*     */ 
/*  76 */         Hashtable files = mrequest.getFiles();
/*  77 */         UploadFile file = (UploadFile)files.get(faceField);
/*  78 */         if (file.getInpuStream() != null) {
/*  79 */           String fileFileName = file.getFileName();
/*     */ 
/*  82 */           String allowTYpe = "gif,jpg,bmp,png";
/*  83 */           if ((!fileFileName.trim().equals("")) && (fileFileName.length() > 0)) {
/*  84 */             String ex = fileFileName.substring(fileFileName.lastIndexOf(".") + 1, fileFileName.length());
/*  85 */             if (allowTYpe.toString().indexOf(ex) < 0) {
/*  86 */               showMenu(false);
/*  87 */               showError("对不起,只能上传gif,jpg,bmp,png格式的头像！");
/*  88 */               return;
/*     */             }
/*     */ 
/*     */           }
/*     */ 
/*  93 */           if (file.getFileSize() > 204800L) {
/*  94 */             showMenu(false);
/*  95 */             showError("对不起,头像图片不能大于200K！");
/*  96 */             return;
/*     */           }
/*     */ 
/*  99 */           String fileName = null;
/* 100 */           String filePath = "";
/*     */ 
/* 102 */           String ext = FileUtil.getFileExt(fileFileName);
/* 103 */           fileName = new StringBuilder().append(DateUtil.toString(new Date(), "yyyyMMddHHmmss")).append(StringUtil.getRandStr(4)).append(".").append(ext).toString();
/*     */ 
/* 105 */           filePath = new StringBuilder().append(EopSetting.IMG_SERVER_PATH).append(EopContext.getContext().getContextPath()).append("/attachment/").toString();
/* 106 */           if (subFolder != null) {
/* 107 */             filePath = new StringBuilder().append(filePath).append(subFolder).append("/").toString();
/*     */           }
/*     */ 
/* 110 */           String path = new StringBuilder().append(EopSetting.FILE_STORE_PREFIX).append("/attachment/").append(subFolder == null ? "" : subFolder).append("/").append(fileName).toString();
/*     */ 
/* 112 */           filePath = new StringBuilder().append(filePath).append(fileName).toString();
/* 113 */           FileUtil.createFile(file.getInpuStream(), filePath);
/* 114 */           IThumbnailCreator thumbnailCreator = ThumbnailCreatorFactory.getCreator(filePath, filePath);
/* 115 */           thumbnailCreator.resize(120, 120);
/* 116 */           member.setFace(path);
/*     */         }
/*     */ 
/*     */       }
/*     */       catch (Exception ex)
/*     */       {
/*     */       }
/*     */ 
/* 127 */       String name = request.getParameter("member.name");
/* 128 */       if (StringUtil.isEmpty(name)) {
/* 129 */         showMenu(false);
/* 130 */         showError("真实姓名不能为空！");
/* 131 */         return;
/*     */       }
/* 133 */       member.setName(name);
/* 134 */       String sex = request.getParameter("member.sex");
/* 135 */       member.setSex(Integer.valueOf(sex));
/* 136 */       String birthday = request.getParameter("mybirthday");
/* 137 */       if (!StringUtil.isEmpty(birthday)) {
/* 138 */         if (DateUtil.toDate(birthday, "yyyy-MM-dd").getTime() > System.currentTimeMillis()) {
/* 139 */           showMenu(false);
/* 140 */           showError("请您填写正确的生日！");
/* 141 */           return;
/*     */         }
/* 143 */         member.setBirthday(Long.valueOf(DateUtil.toDate(birthday, "yyyy-MM-dd").getTime()));
/*     */       }
/*     */ 
/* 146 */       String province_id = request.getParameter("member.province_id");
/* 147 */       member.setProvince_id(Integer.valueOf(province_id));
/* 148 */       String city_id = request.getParameter("member.city_id");
/* 149 */       member.setCity_id(Integer.valueOf(city_id));
/* 150 */       String region_id = request.getParameter("member.region_id");
/* 151 */       member.setRegion_id(Integer.valueOf(region_id));
/* 152 */       String province = request.getParameter("member.province");
/* 153 */       member.setProvince(province);
/* 154 */       String city = request.getParameter("member.city");
/* 155 */       member.setCity(city);
/* 156 */       String region = request.getParameter("member.region");
/* 157 */       member.setRegion(region);
/* 158 */       String address = request.getParameter("member.address");
/* 159 */       member.setAddress(address);
/* 160 */       String zip = request.getParameter("member.zip");
/* 161 */       member.setZip(zip);
/* 162 */       String mobile = request.getParameter("member.mobile");
/* 163 */       member.setMobile(mobile);
/* 164 */       String tel = request.getParameter("member.tel");
/* 165 */       member.setTel(tel);
/* 166 */       member.setNickname(member.getUname());
/*     */ 
/* 168 */       String midentity = request.getParameter("member.midentity");
/* 169 */       if (!StringUtil.isEmpty(midentity))
/* 170 */         member.setMidentity(Integer.valueOf(StringUtil.toInt(midentity)));
/*     */       else {
/* 172 */         member.setMidentity(Integer.valueOf(0));
/*     */       }
/*     */ 
/*     */       try
/*     */       {
/* 182 */         boolean addPoint = false;
/* 183 */         if ((member.getInfo_full() == 0) && (!StringUtil.isEmpty(member.getName())) && (!StringUtil.isEmpty(member.getNickname())) && (!StringUtil.isEmpty(member.getProvince())) && (!StringUtil.isEmpty(member.getCity())) && (!StringUtil.isEmpty(member.getRegion())) && ((!StringUtil.isEmpty(member.getMobile())) || (!StringUtil.isEmpty(member.getTel()))))
/*     */         {
/* 190 */           addPoint = true;
/*     */         }
/*     */ 
/* 193 */         if (addPoint) {
/* 194 */           member.setInfo_full(1);
/* 195 */           this.memberManager.edit(member);
/* 196 */           if (this.memberPointManger.checkIsOpen("finish_profile")) {
/* 197 */             int point = this.memberPointManger.getItemPoint("finish_profile_num");
/* 198 */             int mp = this.memberPointManger.getItemPoint("finish_profile_num_mp");
/* 199 */             this.memberPointManger.add(member.getMember_id().intValue(), point, "完善个人资料", null, mp);
/*     */           }
/*     */         } else {
/* 202 */           this.memberManager.edit(member);
/*     */         }
/*     */ 
/* 206 */         showMenu(false);
/* 207 */         showSuccess("编辑个人资料成功！", "完善基本资料", "member_info.html");
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 211 */         if (this.logger.isDebugEnabled()) {
/* 212 */           this.logger.error(e.getStackTrace());
/*     */         }
/*     */ 
/* 215 */         showMenu(false);
/* 216 */         showError("编辑个人资料失败！", "完善基本资料", "member_info.html");
/*     */       }
/*     */     } else {
/* 219 */       Long mybirthday = member.getBirthday();
/* 220 */       List provinceList = this.regionsManager.listProvince();
/* 221 */       List cityList = this.regionsManager.listCity(member.getProvince_id().intValue());
/* 222 */       cityList = cityList == null ? new ArrayList() : cityList;
/* 223 */       List regionList = this.regionsManager.listRegion(member.getCity_id().intValue());
/* 224 */       regionList = regionList == null ? new ArrayList() : regionList;
/* 225 */       putData("member", member);
/* 226 */       putData("provinceList", provinceList);
/* 227 */       putData("cityList", cityList);
/* 228 */       putData("regionList", regionList);
/* 229 */       if (mybirthday == null)
/* 230 */         putData("mybirthday", DateUtil.toDate("1970-01-01", "yyyy-MM-dd"));
/*     */       else
/* 232 */         putData("mybirthday", new Date(mybirthday.longValue()));
/*     */     }
/*     */   }
/*     */ 
/*     */   public IRegionsManager getRegionsManager()
/*     */   {
/* 238 */     return this.regionsManager;
/*     */   }
/*     */ 
/*     */   public void setRegionsManager(IRegionsManager regionsManager) {
/* 242 */     this.regionsManager = regionsManager;
/*     */   }
/*     */ 
/*     */   public IMemberManager getMemberManager() {
/* 246 */     return this.memberManager;
/*     */   }
/*     */ 
/*     */   public void setMemberManager(IMemberManager memberManager) {
/* 250 */     this.memberManager = memberManager;
/*     */   }
/*     */ 
/*     */   public void setMemberPointManger(IMemberPointManger memberPointManger)
/*     */   {
/* 255 */     this.memberPointManger = memberPointManger;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.member.widget.MemberInfoOldWidget
 * JD-Core Version:    0.6.0
 */