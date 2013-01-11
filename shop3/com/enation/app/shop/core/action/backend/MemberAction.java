/*     */ package com.enation.app.shop.core.action.backend;
/*     */ 
/*     */ import com.enation.app.base.core.model.Member;
/*     */ import com.enation.app.base.core.model.MemberLv;
/*     */ import com.enation.app.base.core.service.IRegionsManager;
/*     */ import com.enation.app.shop.core.model.AdvanceLogs;
/*     */ import com.enation.app.shop.core.model.PointHistory;
/*     */ import com.enation.app.shop.core.plugin.member.MemberPluginBundle;
/*     */ import com.enation.app.shop.core.service.IAdvanceLogsManager;
/*     */ import com.enation.app.shop.core.service.IMemberCommentManager;
/*     */ import com.enation.app.shop.core.service.IMemberLvManager;
/*     */ import com.enation.app.shop.core.service.IMemberManager;
/*     */ import com.enation.app.shop.core.service.IPointHistoryManager;
/*     */ import com.enation.eop.resource.IUserManager;
/*     */ import com.enation.eop.resource.model.EopUser;
/*     */ import com.enation.framework.action.WWAction;
/*     */ import com.enation.framework.context.webcontext.ThreadContextHolder;
/*     */ import com.enation.framework.context.webcontext.WebSessionContext;
/*     */ import com.enation.framework.database.Page;
/*     */ import com.enation.framework.util.DateUtil;
/*     */ import com.enation.framework.util.StringUtil;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ 
/*     */ public class MemberAction extends WWAction
/*     */ {
/*     */   private IMemberManager memberManager;
/*     */   private IMemberLvManager memberLvManager;
/*     */   private IRegionsManager regionsManager;
/*     */   private IPointHistoryManager pointHistoryManager;
/*     */   private IAdvanceLogsManager advanceLogsManager;
/*     */   private IMemberCommentManager memberCommentManager;
/*     */   private MemberPluginBundle memberPluginBundle;
/*     */   private IUserManager userManager;
/*     */   private Member member;
/*     */   private MemberLv lv;
/*     */   private String birthday;
/*     */   private String order;
/*     */   private Integer lv_id;
/*     */   private Integer member_id;
/*     */   private String id;
/*     */   private List lvlist;
/*     */   private List provinceList;
/*     */   private List cityList;
/*     */   private List regionList;
/*     */   private List listPointHistory;
/*     */   private List listAdvanceLogs;
/*     */   private List listComments;
/*     */   private int point;
/*     */   private int pointtype;
/*     */   private Double modify_advance;
/*     */   private String modify_memo;
/*     */   private String object_type;
/*     */   private String name;
/*     */   private String uname;
/*     */   private List catDiscountList;
/*     */   private int[] cat_ids;
/*     */   private int[] cat_discounts;
/*     */   protected Map<Integer, String> pluginTabs;
/*     */   protected Map<Integer, String> pluginHtmls;
/*     */   private String message;
/*     */ 
/*     */   public String add_lv()
/*     */   {
/*  77 */     return "add_lv";
/*     */   }
/*     */ 
/*     */   public String edit_lv() {
/*  81 */     this.lv = this.memberLvManager.get(this.lv_id);
/*     */ 
/*  83 */     return "edit_lv";
/*     */   }
/*     */ 
/*     */   public String list_lv() {
/*  87 */     this.webpage = this.memberLvManager.list(this.order, getPage(), getPageSize());
/*     */ 
/*  89 */     return "list_lv";
/*     */   }
/*     */ 
/*     */   public String saveAddLv() {
/*  93 */     this.memberLvManager.add(this.lv);
/*  94 */     this.msgs.add("会员等级添加成功");
/*  95 */     this.urls.put("会员等级列表", "member!list_lv.do");
/*  96 */     return "message";
/*     */   }
/*     */ 
/*     */   public String saveEditLv()
/*     */   {
/*     */     try {
/* 102 */       this.memberLvManager.edit(this.lv);
/*     */ 
/* 104 */       this.msgs.add("会员等级修改成功");
/* 105 */       this.urls.put("会员等级列表", "member!list_lv.do");
/*     */     } catch (Exception e) {
/* 107 */       this.msgs.add("非法参数");
/*     */     }
/* 109 */     return "message";
/*     */   }
/*     */ 
/*     */   public String deletelv() {
/*     */     try {
/* 114 */       this.memberLvManager.delete(this.id);
/* 115 */       this.json = "{'result':0,'message':'删除成功'}";
/*     */     } catch (RuntimeException e) {
/* 117 */       this.json = "{'result':1,'message':'删除失败'}";
/*     */     }
/* 119 */     return "json_message";
/*     */   }
/*     */ 
/*     */   public String add_member() {
/* 123 */     this.lvlist = this.memberLvManager.list();
/* 124 */     this.provinceList = this.regionsManager.listProvince();
/* 125 */     return "add_member";
/*     */   }
/*     */ 
/*     */   public String edit_member() {
/* 129 */     this.member = this.memberManager.get(this.member_id);
/* 130 */     this.lvlist = this.memberLvManager.list();
/* 131 */     return "edit_member";
/*     */   }
/*     */ 
/*     */   public String memberlist() {
/* 135 */     this.webpage = this.memberManager.list(this.order, this.name, this.uname, getPage(), getPageSize());
/*     */ 
/* 137 */     return "list_member";
/*     */   }
/*     */ 
/*     */   public String saveEditMember() {
/* 141 */     if (!StringUtil.isEmpty(this.birthday)) {
/* 142 */       Date br = DateUtil.toDate(this.birthday, "yyyy-MM-dd");
/* 143 */       this.member.setBirthday(Long.valueOf(br.getTime()));
/*     */     }
/*     */     try {
/* 146 */       Member oldMember = this.memberManager.get(this.member.getMember_id());
/*     */ 
/* 148 */       HttpServletRequest request = ThreadContextHolder.getHttpRequest();
/* 149 */       String province = request.getParameter("province");
/* 150 */       String city = request.getParameter("city");
/* 151 */       String region = request.getParameter("region");
/*     */ 
/* 153 */       String province_id = request.getParameter("province_id");
/* 154 */       String city_id = request.getParameter("city_id");
/* 155 */       String region_id = request.getParameter("region_id");
/*     */ 
/* 158 */       oldMember.setProvince(province);
/* 159 */       oldMember.setCity(city);
/* 160 */       oldMember.setRegion(region);
/*     */ 
/* 162 */       if (!StringUtil.isEmpty(province_id)) {
/* 163 */         oldMember.setProvince_id(Integer.valueOf(StringUtil.toInt(province_id, true)));
/*     */       }
/*     */ 
/* 166 */       if (!StringUtil.isEmpty(city_id)) {
/* 167 */         oldMember.setCity_id(Integer.valueOf(StringUtil.toInt(city_id, true)));
/*     */       }
/*     */ 
/* 170 */       if (!StringUtil.isEmpty(province_id)) {
/* 171 */         oldMember.setRegion_id(Integer.valueOf(StringUtil.toInt(region_id, true)));
/*     */       }
/*     */ 
/* 176 */       oldMember.setName(this.member.getName());
/* 177 */       oldMember.setSex(this.member.getSex());
/* 178 */       oldMember.setBirthday(this.member.getBirthday());
/* 179 */       oldMember.setEmail(this.member.getEmail());
/* 180 */       oldMember.setTel(this.member.getTel());
/* 181 */       oldMember.setMobile(this.member.getMobile());
/* 182 */       oldMember.setLv_id(this.member.getLv_id());
/* 183 */       oldMember.setZip(this.member.getZip());
/* 184 */       oldMember.setAddress(this.member.getAddress());
/* 185 */       oldMember.setQq(this.member.getQq());
/* 186 */       oldMember.setMsn(this.member.getMsn());
/* 187 */       oldMember.setPw_answer(this.member.getPw_answer());
/* 188 */       oldMember.setPw_question(this.member.getPw_question());
/* 189 */       this.memberManager.edit(oldMember);
/* 190 */       this.json = "{'result':0,'message':'修改成功'}";
/*     */     } catch (Exception e) {
/* 192 */       this.json = "{'result':1,'message':'修改失败'}";
/* 193 */       e.printStackTrace();
/*     */     }
/* 195 */     return "json_message";
/*     */   }
/*     */ 
/*     */   public String delete()
/*     */   {
/*     */     try {
/* 201 */       this.memberManager.delete(this.id);
/* 202 */       this.json = "{'result':0,'message':'删除成功'}";
/*     */     } catch (RuntimeException e) {
/* 204 */       this.json = "{'result':1,'message':'删除失败'}";
/*     */     }
/* 206 */     return "json_message";
/*     */   }
/*     */ 
/*     */   public String detail()
/*     */   {
/* 213 */     this.member = this.memberManager.get(this.member_id);
/*     */ 
/* 215 */     this.pluginTabs = this.memberPluginBundle.getTabList(this.member);
/* 216 */     this.pluginHtmls = this.memberPluginBundle.getDetailHtml(this.member);
/*     */ 
/* 218 */     return "detail";
/*     */   }
/*     */ 
/*     */   public String editPoint()
/*     */   {
/* 224 */     this.member = this.memberManager.get(this.member_id);
/* 225 */     return "editPoint";
/*     */   }
/*     */ 
/*     */   public String editSavePoint() {
/* 229 */     this.member = this.memberManager.get(this.member_id);
/* 230 */     this.member.setPoint(Integer.valueOf(this.member.getPoint().intValue() + this.point));
/* 231 */     PointHistory pointHistory = new PointHistory();
/* 232 */     pointHistory.setMember_id(this.member_id.intValue());
/* 233 */     pointHistory.setOperator("管理员");
/* 234 */     pointHistory.setPoint(this.point);
/* 235 */     pointHistory.setReason("operator_adjust");
/* 236 */     pointHistory.setTime(Long.valueOf(System.currentTimeMillis()));
/*     */     try {
/* 238 */       this.memberManager.edit(this.member);
/* 239 */       this.pointHistoryManager.addPointHistory(pointHistory);
/* 240 */       this.json = "{'result':0,'message':'会员积分修改成功'}";
/*     */     } catch (Exception e) {
/* 242 */       this.json = "{'result':1,'message':'修改失败'}";
/* 243 */       e.printStackTrace();
/*     */     }
/* 245 */     return "json_message";
/*     */   }
/*     */ 
/*     */   public String pointLog() {
/* 249 */     this.listPointHistory = this.pointHistoryManager.listPointHistory(this.member_id.intValue(), this.pointtype);
/* 250 */     this.member = this.memberManager.get(this.member_id);
/* 251 */     return "pointLog";
/*     */   }
/*     */ 
/*     */   public String advance() {
/* 255 */     this.member = this.memberManager.get(this.member_id);
/* 256 */     this.listAdvanceLogs = this.advanceLogsManager.listAdvanceLogsByMemberId(this.member_id.intValue());
/*     */ 
/* 258 */     return "advance";
/*     */   }
/*     */ 
/*     */   public String comments() {
/* 262 */     Page page = this.memberCommentManager.getMemberComments(1, 100, StringUtil.toInt(this.object_type), this.member_id.intValue());
/* 263 */     if (page != null) {
/* 264 */       this.listComments = ((List)page.getResult());
/*     */     }
/* 266 */     return "comments";
/*     */   }
/*     */ 
/*     */   public String editSaveAdvance()
/*     */   {
/* 277 */     this.member = this.memberManager.get(this.member_id);
/* 278 */     this.member.setAdvance(Double.valueOf(this.member.getAdvance().doubleValue() + this.modify_advance.doubleValue()));
/*     */     try {
/* 280 */       this.memberManager.edit(this.member);
/*     */     } catch (Exception e) {
/* 282 */       this.json = "{'result':1, 'message':'操作失败'}";
/* 283 */       e.printStackTrace();
/*     */     }
/*     */ 
/* 286 */     AdvanceLogs advanceLogs = new AdvanceLogs();
/* 287 */     advanceLogs.setMember_id(this.member_id.intValue());
/* 288 */     advanceLogs.setDisabled("false");
/* 289 */     advanceLogs.setMtime(Long.valueOf(System.currentTimeMillis()));
/* 290 */     advanceLogs.setImport_money(this.modify_advance);
/* 291 */     advanceLogs.setMember_advance(this.member.getAdvance());
/* 292 */     advanceLogs.setShop_advance(this.member.getAdvance());
/* 293 */     advanceLogs.setMoney(this.modify_advance);
/* 294 */     advanceLogs.setMessage(this.modify_memo);
/* 295 */     advanceLogs.setMemo("admin代充值");
/*     */     try {
/* 297 */       this.advanceLogsManager.add(advanceLogs);
/*     */     } catch (Exception e) {
/* 299 */       this.json = "{'result':1, 'message':'操作失败'}";
/* 300 */       e.printStackTrace();
/*     */     }
/* 302 */     this.json = "{'result':0,'message':'操作成功'}";
/*     */ 
/* 304 */     return "json_message";
/*     */   }
/*     */ 
/*     */   public String saveMember()
/*     */   {
/* 328 */     int result = this.memberManager.checkname(this.member.getUname());
/* 329 */     if (result == 1) {
/* 330 */       this.msgs.add("用户名已经存在");
/* 331 */       this.urls.put("查看会员列表", "member!memberlist.do");
/* 332 */       return "message";
/*     */     }
/* 334 */     if (this.member != null) {
/* 335 */       Date br = DateUtil.toDate(this.birthday, "yyyy-MM-dd");
/* 336 */       this.member.setBirthday(Long.valueOf(br.getTime()));
/* 337 */       this.member.setPassword(this.member.getPassword());
/* 338 */       this.member.setRegtime(Long.valueOf(System.currentTimeMillis()));
/* 339 */       this.memberManager.add(this.member);
/* 340 */       this.msgs.add("保存添加会员成功");
/* 341 */       this.urls.put("查看会员列表", "member!memberlist.do");
/*     */     }
/*     */ 
/* 344 */     return "message";
/*     */   }
/*     */ 
/*     */   public String sysLogin() {
/*     */     try {
/* 349 */       this.name = StringUtil.toUTF8(this.name);
/* 350 */       int r = this.memberManager.loginbysys(this.name);
/* 351 */       if (r == 1) {
/* 352 */         EopUser user = this.userManager.get(this.name);
/* 353 */         if (user != null) {
/* 354 */           WebSessionContext sessonContext = ThreadContextHolder.getSessionContext();
/*     */ 
/* 356 */           sessonContext.setAttribute("eop_user_key", user);
/*     */         }
/* 358 */         return "syslogin";
/*     */       }
/* 360 */       this.msgs.add("登录失败");
/* 361 */       return "message";
/*     */     } catch (RuntimeException e) {
/* 363 */       this.msgs.add(e.getMessage());
/* 364 */     }return "message";
/*     */   }
/*     */ 
/*     */   public MemberLv getLv()
/*     */   {
/* 369 */     return this.lv;
/*     */   }
/*     */ 
/*     */   public void setLv(MemberLv lv) {
/* 373 */     this.lv = lv;
/*     */   }
/*     */ 
/*     */   public Member getMember() {
/* 377 */     return this.member;
/*     */   }
/*     */ 
/*     */   public void setMember(Member member) {
/* 381 */     this.member = member;
/*     */   }
/*     */ 
/*     */   public IMemberManager getMemberManager() {
/* 385 */     return this.memberManager;
/*     */   }
/*     */ 
/*     */   public void setMemberManager(IMemberManager memberManager) {
/* 389 */     this.memberManager = memberManager;
/*     */   }
/*     */ 
/*     */   public String getMessage() {
/* 393 */     return this.message;
/*     */   }
/*     */ 
/*     */   public void setMessage(String message) {
/* 397 */     this.message = message;
/*     */   }
/*     */ 
/*     */   public String getBirthday() {
/* 401 */     return this.birthday;
/*     */   }
/*     */ 
/*     */   public void setBirthday(String birthday) {
/* 405 */     this.birthday = birthday;
/*     */   }
/*     */ 
/*     */   public IMemberLvManager getMemberLvManager() {
/* 409 */     return this.memberLvManager;
/*     */   }
/*     */ 
/*     */   public void setMemberLvManager(IMemberLvManager memberLvManager) {
/* 413 */     this.memberLvManager = memberLvManager;
/*     */   }
/*     */ 
/*     */   public String getOrder() {
/* 417 */     return this.order;
/*     */   }
/*     */ 
/*     */   public void setOrder(String order) {
/* 421 */     this.order = order;
/*     */   }
/*     */ 
/*     */   public Integer getLv_id() {
/* 425 */     return this.lv_id;
/*     */   }
/*     */ 
/*     */   public void setLv_id(Integer lvId) {
/* 429 */     this.lv_id = lvId;
/*     */   }
/*     */ 
/*     */   public String getId() {
/* 433 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(String id) {
/* 437 */     this.id = id;
/*     */   }
/*     */ 
/*     */   public Integer getMember_id() {
/* 441 */     return this.member_id;
/*     */   }
/*     */ 
/*     */   public void setMember_id(Integer memberId) {
/* 445 */     this.member_id = memberId;
/*     */   }
/*     */ 
/*     */   public List getLvlist() {
/* 449 */     return this.lvlist;
/*     */   }
/*     */ 
/*     */   public void setLvlist(List lvlist) {
/* 453 */     this.lvlist = lvlist;
/*     */   }
/*     */ 
/*     */   public IRegionsManager getRegionsManager() {
/* 457 */     return this.regionsManager;
/*     */   }
/*     */ 
/*     */   public void setRegionsManager(IRegionsManager regionsManager) {
/* 461 */     this.regionsManager = regionsManager;
/*     */   }
/*     */ 
/*     */   public List getProvinceList() {
/* 465 */     return this.provinceList;
/*     */   }
/*     */ 
/*     */   public void setProvinceList(List provinceList) {
/* 469 */     this.provinceList = provinceList;
/*     */   }
/*     */ 
/*     */   public List getCityList() {
/* 473 */     return this.cityList;
/*     */   }
/*     */ 
/*     */   public void setCityList(List cityList) {
/* 477 */     this.cityList = cityList;
/*     */   }
/*     */ 
/*     */   public List getRegionList() {
/* 481 */     return this.regionList;
/*     */   }
/*     */ 
/*     */   public void setRegionList(List regionList) {
/* 485 */     this.regionList = regionList;
/*     */   }
/*     */ 
/*     */   public int getPoint()
/*     */   {
/* 491 */     return this.point;
/*     */   }
/*     */ 
/*     */   public void setPoint(int point) {
/* 495 */     this.point = point;
/*     */   }
/*     */ 
/*     */   public IPointHistoryManager getPointHistoryManager() {
/* 499 */     return this.pointHistoryManager;
/*     */   }
/*     */ 
/*     */   public void setPointHistoryManager(IPointHistoryManager pointHistoryManager) {
/* 503 */     this.pointHistoryManager = pointHistoryManager;
/*     */   }
/*     */ 
/*     */   public List getListPointHistory() {
/* 507 */     return this.listPointHistory;
/*     */   }
/*     */ 
/*     */   public void setListPointHistory(List listPointHistory) {
/* 511 */     this.listPointHistory = listPointHistory;
/*     */   }
/*     */ 
/*     */   public IAdvanceLogsManager getAdvanceLogsManager() {
/* 515 */     return this.advanceLogsManager;
/*     */   }
/*     */ 
/*     */   public void setAdvanceLogsManager(IAdvanceLogsManager advanceLogsManager) {
/* 519 */     this.advanceLogsManager = advanceLogsManager;
/*     */   }
/*     */ 
/*     */   public List getListAdvanceLogs() {
/* 523 */     return this.listAdvanceLogs;
/*     */   }
/*     */ 
/*     */   public void setListAdvanceLogs(List listAdvanceLogs) {
/* 527 */     this.listAdvanceLogs = listAdvanceLogs;
/*     */   }
/*     */ 
/*     */   public Double getModify_advance() {
/* 531 */     return this.modify_advance;
/*     */   }
/*     */ 
/*     */   public void setModify_advance(Double modifyAdvance) {
/* 535 */     this.modify_advance = modifyAdvance;
/*     */   }
/*     */ 
/*     */   public String getModify_memo() {
/* 539 */     return this.modify_memo;
/*     */   }
/*     */ 
/*     */   public void setModify_memo(String modifyMemo) {
/* 543 */     this.modify_memo = modifyMemo;
/*     */   }
/*     */ 
/*     */   public List getListComments() {
/* 547 */     return this.listComments;
/*     */   }
/*     */ 
/*     */   public void setListComments(List listComments) {
/* 551 */     this.listComments = listComments;
/*     */   }
/*     */ 
/*     */   public String getObject_type() {
/* 555 */     return this.object_type;
/*     */   }
/*     */ 
/*     */   public void setObject_type(String objectType) {
/* 559 */     this.object_type = objectType;
/*     */   }
/*     */ 
/*     */   public String getName() {
/* 563 */     return this.name;
/*     */   }
/*     */ 
/*     */   public void setName(String name) {
/* 567 */     this.name = name;
/*     */   }
/*     */ 
/*     */   public String getUname() {
/* 571 */     return this.uname;
/*     */   }
/*     */ 
/*     */   public void setUname(String uname) {
/* 575 */     this.uname = uname;
/*     */   }
/*     */ 
/*     */   public IUserManager getUserManager() {
/* 579 */     return this.userManager;
/*     */   }
/*     */ 
/*     */   public void setUserManager(IUserManager userManager) {
/* 583 */     this.userManager = userManager;
/*     */   }
/*     */ 
/*     */   public List getCatDiscountList() {
/* 587 */     return this.catDiscountList;
/*     */   }
/*     */ 
/*     */   public void setCatDiscountList(List catDiscountList) {
/* 591 */     this.catDiscountList = catDiscountList;
/*     */   }
/*     */ 
/*     */   public int[] getCat_ids() {
/* 595 */     return this.cat_ids;
/*     */   }
/*     */ 
/*     */   public void setCat_ids(int[] cat_ids) {
/* 599 */     this.cat_ids = cat_ids;
/*     */   }
/*     */ 
/*     */   public int[] getCat_discounts() {
/* 603 */     return this.cat_discounts;
/*     */   }
/*     */ 
/*     */   public void setCat_discounts(int[] cat_discounts) {
/* 607 */     this.cat_discounts = cat_discounts;
/*     */   }
/*     */ 
/*     */   public void setMemberCommentManager(IMemberCommentManager memberCommentManager) {
/* 611 */     this.memberCommentManager = memberCommentManager;
/*     */   }
/*     */ 
/*     */   public int getPointtype() {
/* 615 */     return this.pointtype;
/*     */   }
/*     */ 
/*     */   public void setPointtype(int pointtype) {
/* 619 */     this.pointtype = pointtype;
/*     */   }
/*     */ 
/*     */   public Map<Integer, String> getPluginTabs() {
/* 623 */     return this.pluginTabs;
/*     */   }
/*     */ 
/*     */   public void setPluginTabs(Map<Integer, String> pluginTabs) {
/* 627 */     this.pluginTabs = pluginTabs;
/*     */   }
/*     */ 
/*     */   public Map<Integer, String> getPluginHtmls() {
/* 631 */     return this.pluginHtmls;
/*     */   }
/*     */ 
/*     */   public void setPluginHtmls(Map<Integer, String> pluginHtmls) {
/* 635 */     this.pluginHtmls = pluginHtmls;
/*     */   }
/*     */ 
/*     */   public MemberPluginBundle getMemberPluginBundle() {
/* 639 */     return this.memberPluginBundle;
/*     */   }
/*     */ 
/*     */   public void setMemberPluginBundle(MemberPluginBundle memberPluginBundle) {
/* 643 */     this.memberPluginBundle = memberPluginBundle;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.action.backend.MemberAction
 * JD-Core Version:    0.6.0
 */