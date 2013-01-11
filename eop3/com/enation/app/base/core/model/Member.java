/*     */ package com.enation.app.base.core.model;
/*     */ 
/*     */ import com.enation.framework.database.NotDbField;
/*     */ import com.enation.framework.database.PrimaryKeyField;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class Member
/*     */   implements Serializable
/*     */ {
/*     */   private Integer member_id;
/*     */   private Integer lv_id;
/*     */   private String uname;
/*     */   private String email;
/*     */   private String password;
/*     */   private Long regtime;
/*     */   private String pw_answer;
/*     */   private String pw_question;
/*     */   private String name;
/*     */   private Integer sex;
/*     */   private Long birthday;
/*     */   private Double advance;
/*     */   private Integer province_id;
/*     */   private Integer city_id;
/*     */   private Integer region_id;
/*     */   private String province;
/*     */   private String city;
/*     */   private String region;
/*     */   private String address;
/*     */   private String zip;
/*     */   private String mobile;
/*     */   private String tel;
/*     */   private int info_full;
/*     */   private int recommend_point_state;
/*     */   private Integer point;
/*     */   private String qq;
/*     */   private String msn;
/*     */   private String remark;
/*     */   private Long lastlogin;
/*     */   private Integer logincount;
/*     */   private Integer mp;
/*     */   private String lvname;
/*     */   private Integer parentid;
/*     */   private Integer agentid;
/*     */   private Integer is_cheked;
/*     */   private String registerip;
/*     */   private String nickname;
/*     */   private String face;
/*     */   private Integer midentity;
/*     */   private Integer last_send_email;
/*     */   private String find_code;
/*     */ 
/*     */   @PrimaryKeyField
/*     */   public Integer getMember_id()
/*     */   {
/*  61 */     return this.member_id;
/*     */   }
/*     */ 
/*     */   public void setMember_id(Integer memberId) {
/*  65 */     this.member_id = memberId;
/*     */   }
/*     */ 
/*     */   public Integer getLv_id()
/*     */   {
/*  70 */     return this.lv_id;
/*     */   }
/*     */ 
/*     */   public void setLv_id(Integer lvId) {
/*  74 */     this.lv_id = lvId;
/*     */   }
/*     */ 
/*     */   public String getUname() {
/*  78 */     return this.uname;
/*     */   }
/*     */ 
/*     */   public void setUname(String uname) {
/*  82 */     this.uname = uname;
/*     */   }
/*     */ 
/*     */   public String getEmail() {
/*  86 */     return this.email;
/*     */   }
/*     */ 
/*     */   public void setEmail(String email) {
/*  90 */     this.email = email;
/*     */   }
/*     */ 
/*     */   public String getPassword() {
/*  94 */     return this.password;
/*     */   }
/*     */ 
/*     */   public void setPassword(String password) {
/*  98 */     this.password = password;
/*     */   }
/*     */ 
/*     */   public Long getRegtime() {
/* 102 */     return this.regtime;
/*     */   }
/*     */ 
/*     */   public void setRegtime(Long regtime) {
/* 106 */     this.regtime = regtime;
/*     */   }
/*     */ 
/*     */   public String getPw_answer() {
/* 110 */     return this.pw_answer;
/*     */   }
/*     */ 
/*     */   public void setPw_answer(String pwAnswer) {
/* 114 */     this.pw_answer = pwAnswer;
/*     */   }
/*     */ 
/*     */   public String getPw_question() {
/* 118 */     return this.pw_question;
/*     */   }
/*     */ 
/*     */   public void setPw_question(String pwQuestion) {
/* 122 */     this.pw_question = pwQuestion;
/*     */   }
/*     */ 
/*     */   public String getName() {
/* 126 */     return this.name;
/*     */   }
/*     */ 
/*     */   public void setName(String name) {
/* 130 */     this.name = name;
/*     */   }
/*     */ 
/*     */   public Integer getSex() {
/* 134 */     this.sex = Integer.valueOf(this.sex == null ? 0 : this.sex.intValue());
/* 135 */     return this.sex;
/*     */   }
/*     */ 
/*     */   public void setSex(Integer sex) {
/* 139 */     this.sex = sex;
/*     */   }
/*     */ 
/*     */   public Long getBirthday() {
/* 143 */     return this.birthday;
/*     */   }
/*     */ 
/*     */   public void setBirthday(Long birthday) {
/* 147 */     this.birthday = birthday;
/*     */   }
/*     */ 
/*     */   public Double getAdvance() {
/* 151 */     return this.advance;
/*     */   }
/*     */ 
/*     */   public void setAdvance(Double advance) {
/* 155 */     this.advance = advance;
/*     */   }
/*     */ 
/*     */   public Integer getProvince_id()
/*     */   {
/* 160 */     return this.province_id;
/*     */   }
/*     */ 
/*     */   public void setProvince_id(Integer provinceId) {
/* 164 */     this.province_id = provinceId;
/*     */   }
/*     */ 
/*     */   public Integer getCity_id() {
/* 168 */     return this.city_id;
/*     */   }
/*     */ 
/*     */   public void setCity_id(Integer cityId) {
/* 172 */     this.city_id = cityId;
/*     */   }
/*     */ 
/*     */   public Integer getRegion_id() {
/* 176 */     return this.region_id;
/*     */   }
/*     */ 
/*     */   public void setRegion_id(Integer regionId) {
/* 180 */     this.region_id = regionId;
/*     */   }
/*     */ 
/*     */   public String getProvince() {
/* 184 */     return this.province;
/*     */   }
/*     */ 
/*     */   public void setProvince(String province) {
/* 188 */     this.province = province;
/*     */   }
/*     */ 
/*     */   public String getCity() {
/* 192 */     return this.city;
/*     */   }
/*     */ 
/*     */   public void setCity(String city) {
/* 196 */     this.city = city;
/*     */   }
/*     */ 
/*     */   public String getRegion() {
/* 200 */     return this.region;
/*     */   }
/*     */ 
/*     */   public void setRegion(String region) {
/* 204 */     this.region = region;
/*     */   }
/*     */ 
/*     */   public String getAddress() {
/* 208 */     return this.address;
/*     */   }
/*     */ 
/*     */   public void setAddress(String address) {
/* 212 */     this.address = address;
/*     */   }
/*     */ 
/*     */   public String getZip() {
/* 216 */     return this.zip;
/*     */   }
/*     */ 
/*     */   public void setZip(String zip) {
/* 220 */     this.zip = zip;
/*     */   }
/*     */ 
/*     */   public String getMobile() {
/* 224 */     return this.mobile;
/*     */   }
/*     */ 
/*     */   public void setMobile(String mobile) {
/* 228 */     this.mobile = mobile;
/*     */   }
/*     */ 
/*     */   public String getTel() {
/* 232 */     return this.tel;
/*     */   }
/*     */ 
/*     */   public void setTel(String tel) {
/* 236 */     this.tel = tel;
/*     */   }
/*     */ 
/*     */   public Integer getPoint() {
/* 240 */     if (this.point == null)
/* 241 */       this.point = Integer.valueOf(0);
/* 242 */     return this.point;
/*     */   }
/*     */ 
/*     */   public void setPoint(Integer point) {
/* 246 */     this.point = point;
/*     */   }
/*     */ 
/*     */   public String getQq() {
/* 250 */     return this.qq;
/*     */   }
/*     */ 
/*     */   public void setQq(String qq) {
/* 254 */     this.qq = qq;
/*     */   }
/*     */ 
/*     */   public String getMsn() {
/* 258 */     return this.msn;
/*     */   }
/*     */ 
/*     */   public void setMsn(String msn) {
/* 262 */     this.msn = msn;
/*     */   }
/*     */ 
/*     */   public Integer getMidentity() {
/* 266 */     return this.midentity;
/*     */   }
/*     */ 
/*     */   public void setMidentity(Integer midentity) {
/* 270 */     this.midentity = midentity;
/*     */   }
/*     */ 
/*     */   public String getRemark() {
/* 274 */     return this.remark;
/*     */   }
/*     */ 
/*     */   public void setRemark(String remark) {
/* 278 */     this.remark = remark;
/*     */   }
/*     */   @NotDbField
/*     */   public String getLvname() {
/* 283 */     return this.lvname;
/*     */   }
/*     */ 
/*     */   public void setLvname(String lvname) {
/* 287 */     this.lvname = lvname;
/*     */   }
/*     */ 
/*     */   public Long getLastlogin() {
/* 291 */     return this.lastlogin;
/*     */   }
/*     */ 
/*     */   public void setLastlogin(Long lastlogin) {
/* 295 */     this.lastlogin = lastlogin;
/*     */   }
/*     */ 
/*     */   public Integer getMp() {
/* 299 */     return this.mp;
/*     */   }
/*     */ 
/*     */   public void setMp(Integer mp) {
/* 303 */     this.mp = mp;
/*     */   }
/*     */ 
/*     */   public Integer getParentid() {
/* 307 */     return this.parentid;
/*     */   }
/*     */ 
/*     */   public void setParentid(Integer parentid) {
/* 311 */     this.parentid = parentid;
/*     */   }
/*     */ 
/*     */   public Integer getIs_cheked() {
/* 315 */     return this.is_cheked;
/*     */   }
/*     */ 
/*     */   public void setIs_cheked(Integer is_cheked) {
/* 319 */     this.is_cheked = is_cheked;
/*     */   }
/*     */ 
/*     */   public Integer getLogincount() {
/* 323 */     return this.logincount;
/*     */   }
/*     */ 
/*     */   public void setLogincount(Integer logincount) {
/* 327 */     this.logincount = logincount;
/*     */   }
/*     */ 
/*     */   public Integer getAgentid() {
/* 331 */     return this.agentid;
/*     */   }
/*     */ 
/*     */   public void setAgentid(Integer agentid) {
/* 335 */     this.agentid = agentid;
/*     */   }
/*     */ 
/*     */   public String getRegisterip() {
/* 339 */     return this.registerip;
/*     */   }
/*     */ 
/*     */   public void setRegisterip(String registerip) {
/* 343 */     this.registerip = registerip;
/*     */   }
/*     */ 
/*     */   public String getNickname() {
/* 347 */     return this.nickname;
/*     */   }
/*     */ 
/*     */   public void setNickname(String nickname) {
/* 351 */     this.nickname = nickname;
/*     */   }
/*     */ 
/*     */   public String getFace() {
/* 355 */     return this.face;
/*     */   }
/*     */ 
/*     */   public void setFace(String face) {
/* 359 */     this.face = face;
/*     */   }
/*     */ 
/*     */   public String getFind_code()
/*     */   {
/* 364 */     return this.find_code;
/*     */   }
/*     */ 
/*     */   public void setFind_code(String find_code) {
/* 368 */     this.find_code = find_code;
/*     */   }
/*     */ 
/*     */   public Integer getLast_send_email() {
/* 372 */     return this.last_send_email;
/*     */   }
/*     */ 
/*     */   public void setLast_send_email(Integer last_send_email) {
/* 376 */     this.last_send_email = last_send_email;
/*     */   }
/*     */ 
/*     */   public int getInfo_full() {
/* 380 */     return this.info_full;
/*     */   }
/*     */ 
/*     */   public void setInfo_full(int info_full) {
/* 384 */     this.info_full = info_full;
/*     */   }
/*     */ 
/*     */   public int getRecommend_point_state() {
/* 388 */     return this.recommend_point_state;
/*     */   }
/*     */ 
/*     */   public void setRecommend_point_state(int recommend_point_state) {
/* 392 */     this.recommend_point_state = recommend_point_state;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.app.base.core.model.Member
 * JD-Core Version:    0.6.0
 */