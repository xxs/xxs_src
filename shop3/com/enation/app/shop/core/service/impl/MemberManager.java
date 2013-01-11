/*     */ package com.enation.app.shop.core.service.impl;
/*     */ 
/*     */ import com.enation.app.base.core.model.Member;
/*     */ import com.enation.app.shop.core.plugin.member.MemberPluginBundle;
/*     */ import com.enation.app.shop.core.service.IMemberLvManager;
/*     */ import com.enation.app.shop.core.service.IMemberManager;
/*     */ import com.enation.app.shop.core.service.IMemberPointManger;
/*     */ import com.enation.eop.processor.httpcache.HttpCacheManager;
/*     */ import com.enation.eop.sdk.database.BaseSupport;
/*     */ import com.enation.eop.sdk.user.IUserService;
/*     */ import com.enation.eop.sdk.user.UserServiceFactory;
/*     */ import com.enation.framework.context.webcontext.ThreadContextHolder;
/*     */ import com.enation.framework.context.webcontext.WebSessionContext;
/*     */ import com.enation.framework.database.IDaoSupport;
/*     */ import com.enation.framework.database.Page;
/*     */ import com.enation.framework.util.DateUtil;
/*     */ import com.enation.framework.util.StringUtil;
/*     */ import java.io.PrintStream;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import org.springframework.transaction.annotation.Propagation;
/*     */ import org.springframework.transaction.annotation.Transactional;
/*     */ 
/*     */ public class MemberManager extends BaseSupport<Member>
/*     */   implements IMemberManager
/*     */ {
/*     */   protected IMemberLvManager memberLvManager;
/*     */   private IMemberPointManger memberPointManger;
/*     */   private MemberPluginBundle memberPluginBundle;
/*     */ 
/*     */   @Transactional(propagation=Propagation.REQUIRED)
/*     */   public void logout()
/*     */   {
/*  40 */     Member member = UserServiceFactory.getUserService().getCurrentMember();
/*  41 */     member = get(member.getMember_id());
/*  42 */     ThreadContextHolder.getSessionContext().removeAttribute("curr_member");
/*  43 */     this.memberPluginBundle.onLogout(member);
/*     */   }
/*     */ 
/*     */   @Transactional(propagation=Propagation.REQUIRED)
/*     */   public int register(Member member)
/*     */   {
/*  51 */     int result = add(member);
/*  52 */     this.memberPluginBundle.onRegister(member);
/*     */ 
/*  54 */     return result;
/*     */   }
/*     */   @Transactional(propagation=Propagation.REQUIRED)
/*     */   public int add(Member member) {
/*  59 */     if (member == null) throw new IllegalArgumentException("member is null");
/*  60 */     if (member.getUname() == null) throw new IllegalArgumentException("member' uname is null");
/*  61 */     if (member.getPassword() == null) throw new IllegalArgumentException("member' password is null");
/*  62 */     if (member.getEmail() == null) throw new IllegalArgumentException("member'email is null");
/*     */ 
/*  64 */     if (checkname(member.getUname()) == 1) {
/*  65 */       return 0;
/*     */     }
/*     */ 
/*  68 */     Integer lvid = this.memberLvManager.getDefaultLv();
/*  69 */     member.setLv_id(lvid);
/*     */ 
/*  71 */     member.setName(member.getUname());
/*     */ 
/*  77 */     member.setPoint(Integer.valueOf(0));
/*  78 */     member.setAdvance(Double.valueOf(0.0D));
/*  79 */     member.setRegtime(Long.valueOf(System.currentTimeMillis()));
/*  80 */     member.setLastlogin(Long.valueOf(DateUtil.getDatelineLong()));
/*  81 */     member.setLogincount(Integer.valueOf(1));
/*  82 */     member.setPassword(StringUtil.md5(member.getPassword()));
/*     */ 
/*  86 */     member.setMp(Integer.valueOf(0));
/*  87 */     member.setFace("");
/*  88 */     member.setMidentity(Integer.valueOf(0));
/*  89 */     member.setNickname(member.getEmail().split("@")[0]);
/*     */ 
/*  92 */     this.baseDaoSupport.insert("member", member);
/*  93 */     int memberid = this.baseDaoSupport.getLastId("member");
/*  94 */     member.setMember_id(Integer.valueOf(memberid));
/*     */ 
/*  97 */     return 1;
/*     */   }
/*     */ 
/*     */   public void checkEmailSuccess(Member member)
/*     */   {
/* 107 */     int memberid = member.getMember_id().intValue();
/*     */ 
/* 109 */     String sql = "update member set is_cheked = 1 where member_id =  " + memberid;
/* 110 */     this.baseDaoSupport.execute(sql, new Object[0]);
/* 111 */     this.memberPluginBundle.onEmailCheck(member);
/*     */   }
/*     */ 
/*     */   public Member get(Integer memberId)
/*     */   {
/* 118 */     String sql = "select m.*,l.name as lvname from " + getTableName("member") + " m left join " + getTableName("member_lv") + " l on m.lv_id = l.lv_id where member_id=?";
/* 119 */     Member m = (Member)this.daoSupport.queryForObject(sql, Member.class, new Object[] { memberId });
/* 120 */     return m;
/*     */   }
/*     */ 
/*     */   public Member getMemberByUname(String uname)
/*     */   {
/* 125 */     String sql = "select * from member where uname=?";
/* 126 */     List list = this.baseDaoSupport.queryForList(sql, Member.class, new Object[] { uname });
/* 127 */     Member m = null;
/* 128 */     if ((list != null) && (list.size() > 0)) {
/* 129 */       m = (Member)list.get(0);
/*     */     }
/* 131 */     return m;
/*     */   }
/*     */ 
/*     */   public Member getMemberByEmail(String email) {
/* 135 */     String sql = "select * from member where email=?";
/* 136 */     List list = this.baseDaoSupport.queryForList(sql, Member.class, new Object[] { email });
/* 137 */     Member m = null;
/* 138 */     if ((list != null) && (list.size() > 0)) {
/* 139 */       m = (Member)list.get(0);
/*     */     }
/* 141 */     return m;
/*     */   }
/*     */ 
/*     */   public Member edit(Member member)
/*     */   {
/* 147 */     this.baseDaoSupport.update("member", member, "member_id=" + member.getMember_id());
/*     */ 
/* 149 */     ThreadContextHolder.getSessionContext().setAttribute("curr_member", member);
/* 150 */     return null;
/*     */   }
/*     */ 
/*     */   public int checkname(String name)
/*     */   {
/* 157 */     String sql = "select count(0) from member where uname=?";
/* 158 */     int count = this.baseDaoSupport.queryForInt(sql, new Object[] { name });
/* 159 */     count = count > 0 ? 1 : 0;
/* 160 */     return count;
/*     */   }
/*     */ 
/*     */   public int checkemail(String email) {
/* 164 */     String sql = "select count(0) from member where email=?";
/* 165 */     int count = this.baseDaoSupport.queryForInt(sql, new Object[] { email });
/* 166 */     count = count > 0 ? 1 : 0;
/* 167 */     return count;
/*     */   }
/*     */ 
/*     */   public Page list(String order, int page, int pageSize)
/*     */   {
/* 172 */     order = order == null ? " m.member_id desc" : order;
/* 173 */     String sql = "select m.*,lv.name as lv_name from " + getTableName("member") + " m left join " + getTableName("member_lv") + " lv on m.lv_id = lv.lv_id ";
/*     */ 
/* 175 */     sql = sql + " order by  " + order;
/* 176 */     Page webpage = this.daoSupport.queryForPage(sql, page, pageSize, new Object[0]);
/* 177 */     return webpage;
/*     */   }
/*     */ 
/*     */   public void delete(String id)
/*     */   {
/* 182 */     if ((id == null) || (id.equals("")))
/* 183 */       return;
/* 184 */     String sql = "delete from member where member_id in (" + id + ")";
/* 185 */     this.baseDaoSupport.execute(sql, new Object[0]);
/*     */   }
/*     */ 
/*     */   public void updatePassword(String password)
/*     */   {
/* 194 */     IUserService userService = UserServiceFactory.getUserService();
/* 195 */     Member member = userService.getCurrentMember();
/* 196 */     updatePassword(member.getMember_id(), password);
/* 197 */     member.setPassword(StringUtil.md5(password));
/* 198 */     ThreadContextHolder.getSessionContext().setAttribute("curr_member", member);
/*     */   }
/*     */ 
/*     */   public void updatePassword(Integer memberid, String password)
/*     */   {
/* 207 */     String md5password = password == null ? StringUtil.md5("") : StringUtil.md5(password);
/*     */ 
/* 209 */     String sql = "update member set password = ? where member_id =? ";
/* 210 */     this.baseDaoSupport.execute(sql, new Object[] { md5password, memberid });
/*     */ 
/* 212 */     this.memberPluginBundle.onUpdatePassword(password, memberid.intValue());
/*     */   }
/*     */ 
/*     */   public void updateFindCode(Integer memberid, String code)
/*     */   {
/* 217 */     String sql = "update member set find_code = ? where member_id =? ";
/* 218 */     this.baseDaoSupport.execute(sql, new Object[] { code, memberid });
/*     */   }
/*     */   @Transactional(propagation=Propagation.REQUIRED)
/*     */   public int login(String username, String password) {
/* 223 */     String sql = "select m.*,l.name as lvname from " + getTableName("member") + " m left join " + getTableName("member_lv") + " l on m.lv_id = l.lv_id where m.uname=? and password=?";
/*     */ 
/* 225 */     if (username.contains("@")) {
/* 226 */       sql = "select m.*,l.name as lvname from " + getTableName("member") + " m left join " + getTableName("member_lv") + " l on m.lv_id = l.lv_id where m.email=? and password=?";
/*     */     }
/*     */ 
/* 229 */     List list = this.daoSupport.queryForList(sql, Member.class, new Object[] { username, StringUtil.md5(password) });
/* 230 */     if ((list == null) || (list.isEmpty())) {
/* 231 */       return 0;
/*     */     }
/*     */ 
/* 234 */     Member member = (Member)list.get(0);
/* 235 */     long ldate = member.getLastlogin().longValue() * 1000L;
/* 236 */     Date date = new Date(ldate);
/* 237 */     Date today = new Date();
/* 238 */     int logincount = member.getLogincount().intValue();
/* 239 */     if (DateUtil.toString(date, "yyyy-MM").equals(DateUtil.toString(today, "yyyy-MM")))
/* 240 */       logincount++;
/*     */     else {
/* 242 */       logincount = 1;
/*     */     }
/* 244 */     Long upLogintime = member.getLastlogin();
/* 245 */     member.setLastlogin(Long.valueOf(DateUtil.getDatelineLong()));
/* 246 */     member.setLogincount(Integer.valueOf(logincount));
/* 247 */     edit(member);
/* 248 */     ThreadContextHolder.getSessionContext().setAttribute("curr_member", member);
/*     */ 
/* 251 */     HttpCacheManager.sessionChange();
/*     */ 
/* 253 */     this.memberPluginBundle.onLogin(member, upLogintime);
/*     */ 
/* 258 */     return 1;
/*     */   }
/*     */   @Transactional(propagation=Propagation.REQUIRED)
/*     */   public int loginWithCookie(String username, String password) {
/* 263 */     String sql = "select m.*,l.name as lvname from " + getTableName("member") + " m left join " + getTableName("member_lv") + " l on m.lv_id = l.lv_id where m.uname=? and password=?";
/*     */ 
/* 265 */     if (username.contains("@")) {
/* 266 */       sql = "select m.*,l.name as lvname from " + getTableName("member") + " m left join " + getTableName("member_lv") + " l on m.lv_id = l.lv_id where m.email=? and password=?";
/*     */     }
/* 268 */     List list = this.daoSupport.queryForList(sql, Member.class, new Object[] { username, password });
/* 269 */     if ((list == null) || (list.isEmpty())) {
/* 270 */       return 0;
/*     */     }
/*     */ 
/* 273 */     Member member = (Member)list.get(0);
/* 274 */     long ldate = member.getLastlogin().longValue() * 1000L;
/* 275 */     Date date = new Date(ldate);
/* 276 */     Date today = new Date();
/* 277 */     int logincount = member.getLogincount().intValue();
/* 278 */     if (DateUtil.toString(date, "yyyy-MM").equals(DateUtil.toString(today, "yyyy-MM")))
/* 279 */       logincount++;
/*     */     else {
/* 281 */       logincount = 1;
/*     */     }
/* 283 */     Long upLogintime = member.getLastlogin();
/* 284 */     member.setLastlogin(Long.valueOf(DateUtil.getDatelineLong()));
/* 285 */     member.setLogincount(Integer.valueOf(logincount));
/* 286 */     edit(member);
/* 287 */     ThreadContextHolder.getSessionContext().setAttribute("curr_member", member);
/*     */ 
/* 290 */     this.memberPluginBundle.onLogin(member, upLogintime);
/*     */ 
/* 294 */     return 1;
/*     */   }
/*     */ 
/*     */   public int loginbysys(String username)
/*     */   {
/* 304 */     IUserService userService = UserServiceFactory.getUserService();
/* 305 */     if (!userService.isUserLoggedIn()) {
/* 306 */       throw new RuntimeException("您无权进行此操作，或者您的登录已经超时");
/*     */     }
/*     */ 
/* 309 */     String sql = "select m.*,l.name as lvname from " + getTableName("member") + " m left join " + getTableName("member_lv") + " l on m.lv_id = l.lv_id where m.uname=?";
/* 310 */     List list = this.daoSupport.queryForList(sql, Member.class, new Object[] { username });
/* 311 */     if ((list == null) || (list.isEmpty())) {
/* 312 */       return 0;
/*     */     }
/*     */ 
/* 315 */     Member member = (Member)list.get(0);
/* 316 */     ThreadContextHolder.getSessionContext().setAttribute("curr_member", member);
/* 317 */     HttpCacheManager.sessionChange();
/* 318 */     return 1;
/*     */   }
/*     */ 
/*     */   public IMemberLvManager getMemberLvManager()
/*     */   {
/* 324 */     return this.memberLvManager;
/*     */   }
/*     */ 
/*     */   public void setMemberLvManager(IMemberLvManager memberLvManager) {
/* 328 */     this.memberLvManager = memberLvManager;
/*     */   }
/*     */ 
/*     */   public void addMoney(Integer memberid, Double num)
/*     */   {
/* 333 */     String sql = "update member set advance=advance+? where member_id=?";
/* 334 */     this.baseDaoSupport.execute(sql, new Object[] { num, memberid });
/*     */   }
/*     */ 
/*     */   public void cutMoney(Integer memberid, Double num)
/*     */   {
/* 342 */     Member member = get(memberid);
/* 343 */     if (member.getAdvance().doubleValue() < num.doubleValue()) {
/* 344 */       throw new RuntimeException("预存款不足:需要[" + num + "],剩余[" + member.getAdvance() + "]");
/*     */     }
/* 346 */     String sql = "update member set advance=advance-? where member_id=?";
/* 347 */     this.baseDaoSupport.execute(sql, new Object[] { num, memberid });
/*     */   }
/*     */ 
/*     */   public Page list(String order, String name, String uname, int page, int pageSize)
/*     */   {
/* 353 */     order = order == null ? " m.member_id desc" : order;
/* 354 */     String sql = "select m.*,lv.name as lv_name from " + getTableName("member") + " m left join " + getTableName("member_lv") + " lv on m.lv_id = lv.lv_id where 1=1";
/* 355 */     if ((name != null) && (!name.equals(""))) {
/* 356 */       sql = sql + " and m.name like '%" + name + "%'";
/*     */     }
/* 358 */     if ((uname != null) && (!uname.equals(""))) {
/* 359 */       sql = sql + " and m.uname like '%" + uname + "%'";
/*     */     }
/* 361 */     sql = sql + " order by  " + order;
/* 362 */     Page webpage = this.daoSupport.queryForPage(sql, page, pageSize, new Object[0]);
/* 363 */     return webpage;
/*     */   }
/*     */ 
/*     */   public IMemberPointManger getMemberPointManger()
/*     */   {
/* 368 */     return this.memberPointManger;
/*     */   }
/*     */ 
/*     */   public void setMemberPointManger(IMemberPointManger memberPointManger)
/*     */   {
/* 373 */     this.memberPointManger = memberPointManger;
/*     */   }
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/* 378 */     System.out.println(DateUtil.toDate("2011-05-27", "yyyy-MM-dd").getTime());
/*     */   }
/*     */ 
/*     */   public void updateLv(int memberid, int lvid)
/*     */   {
/* 383 */     String sql = "update member set lv_id=? where member_id=?";
/* 384 */     this.baseDaoSupport.execute(sql, new Object[] { Integer.valueOf(lvid), Integer.valueOf(memberid) });
/*     */   }
/*     */   public MemberPluginBundle getMemberPluginBundle() {
/* 387 */     return this.memberPluginBundle;
/*     */   }
/*     */   public void setMemberPluginBundle(MemberPluginBundle memberPluginBundle) {
/* 390 */     this.memberPluginBundle = memberPluginBundle;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.service.impl.MemberManager
 * JD-Core Version:    0.6.0
 */