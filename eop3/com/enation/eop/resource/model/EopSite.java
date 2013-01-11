/*     */ package com.enation.eop.resource.model;
/*     */ 
/*     */ import com.enation.framework.database.PrimaryKeyField;
/*     */ import java.io.PrintStream;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class EopSite
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 7525130003L;
/*     */   private Integer id;
/*     */   private Integer userid;
/*     */   private String sitename;
/*     */   private String title;
/*     */   private String keywords;
/*     */   private String descript;
/*     */   private String username;
/*     */   private String usertel;
/*     */   private String usermobile;
/*     */   private String usertel1;
/*     */   private Integer usersex;
/*     */   private String useremail;
/*     */   private Integer state;
/*     */   private String qqlist;
/*     */   private String msnlist;
/*     */   private String wwlist;
/*     */   private String tellist;
/*     */   private String worktime;
/*     */   private Integer qq;
/*     */   private Integer msn;
/*     */   private Integer ww;
/*     */   private Integer tel;
/*     */   private Integer wt;
/*     */   private Integer siteon;
/*     */   private String closereson;
/*     */   private String copyright;
/*     */   private String icp;
/*     */   private String address;
/*     */   private String zipcode;
/*     */   private String linkman;
/*     */   private String linktel;
/*     */   private String email;
/*     */   private String productid;
/*     */   private Integer themeid;
/*     */   private String themepath;
/*     */   private Integer adminthemeid;
/*     */   private String icofile;
/*     */   private String logofile;
/*     */   private Long createtime;
/*     */   private Long lastlogin;
/*     */   private long lastgetpoint;
/*     */   private String bklogofile;
/*     */   private String bkloginpicfile;
/*     */   private int logincount;
/*     */   private int point;
/*     */   private String relid;
/*     */   private int sitestate;
/*     */   private int isimported;
/*     */   private int imptype;
/*     */   private Integer multi_site;
/*     */ 
/*     */   public String getKeywords()
/*     */   {
/*  92 */     return this.keywords;
/*     */   }
/*     */ 
/*     */   public void setKeywords(String keywords) {
/*  96 */     this.keywords = keywords;
/*     */   }
/*     */   @PrimaryKeyField
/*     */   public Integer getId() {
/* 101 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(Integer id) {
/* 105 */     this.id = id;
/*     */   }
/*     */ 
/*     */   public Integer getUserid() {
/* 109 */     return this.userid;
/*     */   }
/*     */ 
/*     */   public void setUserid(Integer userid) {
/* 113 */     this.userid = userid;
/*     */   }
/*     */ 
/*     */   public String getSitename() {
/* 117 */     return this.sitename;
/*     */   }
/*     */ 
/*     */   public void setSitename(String sitename) {
/* 121 */     this.sitename = sitename;
/*     */   }
/*     */ 
/*     */   public String getDescript() {
/* 125 */     return this.descript;
/*     */   }
/*     */ 
/*     */   public void setDescript(String descript) {
/* 129 */     this.descript = descript;
/*     */   }
/*     */ 
/*     */   public Integer getThemeid() {
/* 133 */     return this.themeid;
/*     */   }
/*     */ 
/*     */   public void setThemeid(Integer themeid) {
/* 137 */     this.themeid = themeid;
/*     */   }
/*     */ 
/*     */   public Integer getAdminthemeid() {
/* 141 */     return this.adminthemeid;
/*     */   }
/*     */ 
/*     */   public void setAdminthemeid(Integer adminthemeid) {
/* 145 */     this.adminthemeid = adminthemeid;
/*     */   }
/*     */ 
/*     */   public String getIcofile() {
/* 149 */     return this.icofile;
/*     */   }
/*     */ 
/*     */   public void setIcofile(String icofile) {
/* 153 */     this.icofile = icofile;
/*     */   }
/*     */ 
/*     */   public String getLogofile() {
/* 157 */     return this.logofile;
/*     */   }
/*     */ 
/*     */   public void setLogofile(String logofile) {
/* 161 */     this.logofile = logofile;
/*     */   }
/*     */ 
/*     */   public String getProductid() {
/* 165 */     return this.productid;
/*     */   }
/*     */ 
/*     */   public void setProductid(String productid) {
/* 169 */     this.productid = productid;
/*     */   }
/*     */ 
/*     */   public String getThemepath()
/*     */   {
/* 174 */     return this.themepath;
/*     */   }
/*     */ 
/*     */   public void setThemepath(String themepath) {
/* 178 */     this.themepath = themepath;
/*     */   }
/*     */ 
/*     */   public int getPoint() {
/* 182 */     return this.point;
/*     */   }
/*     */ 
/*     */   public void setPoint(int point) {
/* 186 */     this.point = point;
/*     */   }
/*     */   public String getBklogofile() {
/* 189 */     return this.bklogofile;
/*     */   }
/*     */ 
/*     */   public void setBklogofile(String bklogofile) {
/* 193 */     this.bklogofile = bklogofile;
/*     */   }
/*     */ 
/*     */   public String getBkloginpicfile() {
/* 197 */     return this.bkloginpicfile;
/*     */   }
/*     */ 
/*     */   public void setBkloginpicfile(String bkloginpicfile) {
/* 201 */     this.bkloginpicfile = bkloginpicfile;
/*     */   }
/*     */ 
/*     */   public EopSite clone() {
/* 205 */     EopSite site = new EopSite();
/* 206 */     site.setAdminthemeid(this.adminthemeid);
/* 207 */     site.setDescript(this.descript);
/* 208 */     site.setIcofile(this.icofile);
/* 209 */     site.setLogofile(this.logofile);
/* 210 */     site.setId(this.id);
/* 211 */     site.setKeywords(this.keywords);
/* 212 */     site.setPoint(this.point);
/* 213 */     site.setProductid(this.productid);
/* 214 */     site.setThemeid(this.themeid);
/* 215 */     site.setThemepath(this.themepath);
/* 216 */     site.setSitename(this.sitename);
/* 217 */     site.setUserid(this.userid);
/*     */ 
/* 219 */     return site;
/*     */   }
/*     */ 
/*     */   public Long getCreatetime()
/*     */   {
/* 224 */     return this.createtime;
/*     */   }
/*     */ 
/*     */   public void setCreatetime(Long createtime) {
/* 228 */     this.createtime = createtime;
/*     */   }
/*     */ 
/*     */   public Long getLastlogin() {
/* 232 */     return this.lastlogin;
/*     */   }
/*     */ 
/*     */   public void setLastlogin(Long lastlogin) {
/* 236 */     this.lastlogin = lastlogin;
/*     */   }
/*     */ 
/*     */   public long getLastgetpoint() {
/* 240 */     return this.lastgetpoint;
/*     */   }
/*     */ 
/*     */   public void setLastgetpoint(long lastgetpoint) {
/* 244 */     this.lastgetpoint = lastgetpoint;
/*     */   }
/*     */ 
/*     */   public static void main(String[] args) {
/* 248 */     System.out.println(System.currentTimeMillis() / 1000L);
/*     */   }
/*     */ 
/*     */   public int getLogincount() {
/* 252 */     return this.logincount;
/*     */   }
/*     */ 
/*     */   public void setLogincount(int logincount) {
/* 256 */     this.logincount = logincount;
/*     */   }
/*     */ 
/*     */   public String getUsername() {
/* 260 */     return this.username;
/*     */   }
/*     */ 
/*     */   public void setUsername(String username) {
/* 264 */     this.username = username;
/*     */   }
/*     */ 
/*     */   public String getUsertel() {
/* 268 */     return this.usertel;
/*     */   }
/*     */ 
/*     */   public void setUsertel(String usertel) {
/* 272 */     this.usertel = usertel;
/*     */   }
/*     */ 
/*     */   public String getUsermobile() {
/* 276 */     return this.usermobile;
/*     */   }
/*     */ 
/*     */   public void setUsermobile(String usermobile) {
/* 280 */     this.usermobile = usermobile;
/*     */   }
/*     */ 
/*     */   public String getUsertel1() {
/* 284 */     return this.usertel1;
/*     */   }
/*     */ 
/*     */   public void setUsertel1(String usertel1) {
/* 288 */     this.usertel1 = usertel1;
/*     */   }
/*     */ 
/*     */   public Integer getUsersex() {
/* 292 */     return this.usersex;
/*     */   }
/*     */ 
/*     */   public void setUsersex(Integer usersex) {
/* 296 */     this.usersex = usersex;
/*     */   }
/*     */ 
/*     */   public String getUseremail() {
/* 300 */     return this.useremail;
/*     */   }
/*     */ 
/*     */   public void setUseremail(String useremail) {
/* 304 */     this.useremail = useremail;
/*     */   }
/*     */ 
/*     */   public String getTitle() {
/* 308 */     return this.title;
/*     */   }
/*     */ 
/*     */   public void setTitle(String title) {
/* 312 */     this.title = title;
/*     */   }
/*     */ 
/*     */   public Integer getSiteon() {
/* 316 */     return this.siteon;
/*     */   }
/*     */ 
/*     */   public void setSiteon(Integer siteon) {
/* 320 */     this.siteon = siteon;
/*     */   }
/*     */ 
/*     */   public String getCopyright() {
/* 324 */     return this.copyright;
/*     */   }
/*     */ 
/*     */   public void setCopyright(String copyright) {
/* 328 */     this.copyright = copyright;
/*     */   }
/*     */ 
/*     */   public String getIcp() {
/* 332 */     return this.icp;
/*     */   }
/*     */ 
/*     */   public void setIcp(String icp) {
/* 336 */     this.icp = icp;
/*     */   }
/*     */ 
/*     */   public String getAddress() {
/* 340 */     return this.address;
/*     */   }
/*     */ 
/*     */   public void setAddress(String address) {
/* 344 */     this.address = address;
/*     */   }
/*     */ 
/*     */   public String getZipcode() {
/* 348 */     return this.zipcode;
/*     */   }
/*     */ 
/*     */   public void setZipcode(String zipcode) {
/* 352 */     this.zipcode = zipcode;
/*     */   }
/*     */ 
/*     */   public Integer getState() {
/* 356 */     return this.state;
/*     */   }
/*     */ 
/*     */   public void setState(Integer state) {
/* 360 */     this.state = state;
/*     */   }
/*     */ 
/*     */   public String getQqlist() {
/* 364 */     return this.qqlist;
/*     */   }
/*     */ 
/*     */   public void setQqlist(String qqlist) {
/* 368 */     this.qqlist = qqlist;
/*     */   }
/*     */ 
/*     */   public String getMsnlist() {
/* 372 */     return this.msnlist;
/*     */   }
/*     */ 
/*     */   public void setMsnlist(String msnlist) {
/* 376 */     this.msnlist = msnlist;
/*     */   }
/*     */ 
/*     */   public String getWwlist() {
/* 380 */     return this.wwlist;
/*     */   }
/*     */ 
/*     */   public void setWwlist(String wwlist) {
/* 384 */     this.wwlist = wwlist;
/*     */   }
/*     */ 
/*     */   public String getTellist() {
/* 388 */     return this.tellist;
/*     */   }
/*     */ 
/*     */   public void setTellist(String tellist) {
/* 392 */     this.tellist = tellist;
/*     */   }
/*     */ 
/*     */   public String getWorktime() {
/* 396 */     return this.worktime;
/*     */   }
/*     */ 
/*     */   public void setWorktime(String worktime) {
/* 400 */     this.worktime = worktime;
/*     */   }
/*     */ 
/*     */   public Integer getQq() {
/* 404 */     return this.qq;
/*     */   }
/*     */ 
/*     */   public void setQq(Integer qq) {
/* 408 */     this.qq = qq;
/*     */   }
/*     */ 
/*     */   public Integer getMsn() {
/* 412 */     return this.msn;
/*     */   }
/*     */ 
/*     */   public void setMsn(Integer msn) {
/* 416 */     this.msn = msn;
/*     */   }
/*     */ 
/*     */   public Integer getWw() {
/* 420 */     return this.ww;
/*     */   }
/*     */ 
/*     */   public void setWw(Integer ww) {
/* 424 */     this.ww = ww;
/*     */   }
/*     */ 
/*     */   public Integer getTel() {
/* 428 */     return this.tel;
/*     */   }
/*     */ 
/*     */   public void setTel(Integer tel) {
/* 432 */     this.tel = tel;
/*     */   }
/*     */ 
/*     */   public Integer getWt() {
/* 436 */     return this.wt;
/*     */   }
/*     */ 
/*     */   public void setWt(Integer wt) {
/* 440 */     this.wt = wt;
/*     */   }
/*     */ 
/*     */   public String getLinkman() {
/* 444 */     return this.linkman;
/*     */   }
/*     */ 
/*     */   public void setLinkman(String linkman) {
/* 448 */     this.linkman = linkman;
/*     */   }
/*     */ 
/*     */   public String getLinktel() {
/* 452 */     return this.linktel;
/*     */   }
/*     */ 
/*     */   public void setLinktel(String linktel) {
/* 456 */     this.linktel = linktel;
/*     */   }
/*     */ 
/*     */   public String getEmail() {
/* 460 */     return this.email;
/*     */   }
/*     */ 
/*     */   public void setEmail(String email) {
/* 464 */     this.email = email;
/*     */   }
/*     */ 
/*     */   public String getClosereson() {
/* 468 */     return this.closereson;
/*     */   }
/*     */ 
/*     */   public void setClosereson(String closereson) {
/* 472 */     this.closereson = closereson;
/*     */   }
/*     */ 
/*     */   public Integer getMulti_site() {
/* 476 */     this.multi_site = (this.multi_site == null ? (this.multi_site = Integer.valueOf(0)) : this.multi_site);
/* 477 */     return this.multi_site;
/*     */   }
/*     */ 
/*     */   public void setMulti_site(Integer multiSite) {
/* 481 */     this.multi_site = multiSite;
/*     */   }
/*     */ 
/*     */   public String getRelid() {
/* 485 */     return this.relid;
/*     */   }
/*     */ 
/*     */   public void setRelid(String relid) {
/* 489 */     this.relid = relid;
/*     */   }
/*     */ 
/*     */   public int getSitestate() {
/* 493 */     return this.sitestate;
/*     */   }
/*     */ 
/*     */   public void setSitestate(int sitestate) {
/* 497 */     this.sitestate = sitestate;
/*     */   }
/*     */ 
/*     */   public int getIsimported() {
/* 501 */     return this.isimported;
/*     */   }
/*     */ 
/*     */   public void setIsimported(int isimported) {
/* 505 */     this.isimported = isimported;
/*     */   }
/*     */ 
/*     */   public int getImptype() {
/* 509 */     return this.imptype;
/*     */   }
/*     */ 
/*     */   public void setImptype(int imptype) {
/* 513 */     this.imptype = imptype;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.eop.resource.model.EopSite
 * JD-Core Version:    0.6.0
 */