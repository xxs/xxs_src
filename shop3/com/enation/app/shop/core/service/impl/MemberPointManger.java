/*     */ package com.enation.app.shop.core.service.impl;
/*     */ 
/*     */ import com.enation.app.base.core.model.Member;
/*     */ import com.enation.app.base.core.model.MemberLv;
/*     */ import com.enation.app.base.core.service.ISettingService;
/*     */ import com.enation.app.shop.core.model.FreezePoint;
/*     */ import com.enation.app.shop.core.model.Order;
/*     */ import com.enation.app.shop.core.model.OrderLog;
/*     */ import com.enation.app.shop.core.model.PointHistory;
/*     */ import com.enation.app.shop.core.service.IMemberLvManager;
/*     */ import com.enation.app.shop.core.service.IMemberManager;
/*     */ import com.enation.app.shop.core.service.IMemberPointManger;
/*     */ import com.enation.app.shop.core.service.IOrderManager;
/*     */ import com.enation.app.shop.core.service.IPointHistoryManager;
/*     */ import com.enation.eop.sdk.database.BaseSupport;
/*     */ import com.enation.eop.sdk.user.IUserService;
/*     */ import com.enation.eop.sdk.user.UserServiceFactory;
/*     */ import com.enation.framework.database.IDaoSupport;
/*     */ import com.enation.framework.util.DateUtil;
/*     */ import com.enation.framework.util.StringUtil;
/*     */ import java.io.PrintStream;
/*     */ import java.util.List;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.springframework.transaction.annotation.Propagation;
/*     */ import org.springframework.transaction.annotation.Transactional;
/*     */ 
/*     */ public class MemberPointManger extends BaseSupport
/*     */   implements IMemberPointManger
/*     */ {
/*     */   private IPointHistoryManager pointHistoryManager;
/*     */   private IMemberManager memberManager;
/*     */   private IMemberLvManager memberLvManager;
/*     */   private ISettingService settingService;
/*     */   private IOrderManager orderManager;
/*     */ 
/*     */   public void thaw(FreezePoint fp, boolean ismanual)
/*     */   {
/*  44 */     String reson = "";
/*  45 */     if ("register_link".equals(fp.getType())) {
/*  46 */       reson = "推荐会员购买商品完成，积分解冻";
/*     */     }
/*     */ 
/*  49 */     if ("buygoods".equals(fp.getType())) {
/*  50 */       if (ismanual)
/*  51 */         reson = "购买商品,用户提前解冻积分";
/*     */       else {
/*  53 */         reson = "购买商品满15天积分解冻";
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/*  58 */     if ("onlinepay".equals(fp.getType())) {
/*  59 */       if (ismanual)
/*  60 */         reson = "在线支付购买商品,用户提前解冻积分";
/*     */       else {
/*  62 */         reson = "在线支付购买商品满15天积分解冻";
/*     */       }
/*     */     }
/*     */ 
/*  66 */     add(fp.getMemberid(), fp.getPoint(), reson, fp.getOrderid(), fp.getMp());
/*  67 */     this.baseDaoSupport.execute("delete from freeze_point where id=?", new Object[] { Integer.valueOf(fp.getId()) });
/*     */ 
/*  70 */     if ("register_link".equals(fp.getType()))
/*     */     {
/*  72 */       this.baseDaoSupport.execute("update member set recommend_point_state=1 where member_id=?", new Object[] { fp.getChildid() });
/*     */     }
/*     */   }
/*     */ 
/*     */   public void thaw(Integer orderId)
/*     */   {
/*  85 */     Order order = this.orderManager.get(orderId);
/*  86 */     if (order == null) {
/*  87 */       throw new RuntimeException("对不起，此订单不存在！");
/*     */     }
/*  89 */     if ((order.getStatus() == null) || (order.getStatus().intValue() != 6)) {
/*  90 */       throw new RuntimeException("对不起，此订单不能解冻！");
/*     */     }
/*  92 */     IUserService userService = UserServiceFactory.getUserService();
/*  93 */     if (order.getMember_id().intValue() != userService.getCurrentMember().getMember_id().intValue()) {
/*  94 */       throw new RuntimeException("对不起，您没有权限进行此项操作！");
/*     */     }
/*     */ 
/*  97 */     List list = listByOrderId(orderId);
/*  98 */     for (FreezePoint fp : list)
/*     */     {
/* 101 */       if (fp.getOrder_status() == 6) {
/* 102 */         thaw(fp, true);
/*     */       }
/*     */     }
/*     */ 
/* 106 */     OrderLog orderLog = new OrderLog();
/* 107 */     orderLog.setMessage("用户[" + userService.getCurrentMember().getUname() + "]解冻订单[" + orderId + "]积分，并将订单置为完成状态");
/* 108 */     orderLog.setOp_id(Integer.valueOf(0));
/* 109 */     orderLog.setOp_name("用户提前解冻积分");
/* 110 */     orderLog.setOp_time(Long.valueOf(System.currentTimeMillis()));
/* 111 */     orderLog.setOrder_id(orderId);
/* 112 */     this.baseDaoSupport.insert("order_log", orderLog);
/* 113 */     long unix_timestamp = DateUtil.getDatelineLong();
/* 114 */     String sql = "update order set status =7,complete_time=" + unix_timestamp + " where order_id =" + orderId;
/*     */ 
/* 116 */     this.baseDaoSupport.execute(sql, new Object[0]);
/*     */   }
/*     */ 
/*     */   private List<FreezePoint> listByOrderId(Integer orderId)
/*     */   {
/* 123 */     String sql = "select fp.*,o.status as order_status from " + getTableName("freeze_point") + "  fp inner join " + getTableName("order") + " o on fp.orderid= o.order_id  where o.order_id=?";
/*     */ 
/* 125 */     return this.daoSupport.queryForList(sql, FreezePoint.class, new Object[] { orderId });
/*     */   }
/*     */ 
/*     */   public List<FreezePoint> listByBeforeDay(int beforeDayNum)
/*     */   {
/* 136 */     int f = beforeDayNum * 24 * 60 * 60;
/*     */ 
/* 138 */     int now = DateUtil.getDateline();
/* 139 */     String sql = "select fp.*,o.status as order_status from " + getTableName("freeze_point") + "  fp inner join " + getTableName("order") + " o on fp.orderid= o.order_id  where  " + now + "-dateline>=" + f;
/*     */ 
/* 141 */     return this.daoSupport.queryForList(sql, FreezePoint.class, new Object[0]);
/*     */   }
/*     */ 
/*     */   public void addFreezePoint(FreezePoint freezePoint, String memberName)
/*     */   {
/* 150 */     if (freezePoint == null) throw new IllegalArgumentException("param freezePoint is NULL");
/* 151 */     if (freezePoint.getMemberid() == 0) throw new IllegalArgumentException("param freezePoint.memberid is zero");
/*     */ 
/* 155 */     String reson = "";
/* 156 */     if ("register_link".equals(freezePoint.getType()))
/*     */     {
/* 158 */       reson = "register_link";
/*     */     }
/*     */ 
/* 161 */     if ("buygoods".equals(freezePoint.getType()))
/*     */     {
/* 163 */       reson = "buygoods";
/*     */     }
/*     */ 
/* 166 */     if ("onlinepay".equals(freezePoint.getType()))
/*     */     {
/* 168 */       reson = "onlinepay";
/*     */     }
/*     */ 
/* 183 */     freezePoint.setDateline(DateUtil.getDateline());
/* 184 */     this.baseDaoSupport.insert("freeze_point", freezePoint);
/*     */   }
/*     */ 
/*     */   public int getFreezeMpByMemberId(int memberid)
/*     */   {
/* 196 */     return this.baseDaoSupport.queryForInt("SELECT SUM(mp) FROM freeze_point WHERE memberid=?", new Object[] { Integer.valueOf(memberid) });
/*     */   }
/*     */ 
/*     */   public int getFreezePointByMemberId(int memberid) {
/* 200 */     return this.baseDaoSupport.queryForInt("SELECT SUM(point) FROM freeze_point WHERE memberid=?", new Object[] { Integer.valueOf(memberid) });
/*     */   }
/*     */ 
/*     */   public boolean checkIsOpen(String itemname) {
/* 204 */     String value = this.settingService.getSetting("point", itemname);
/*     */ 
/* 206 */     return "1".equals(value);
/*     */   }
/*     */ 
/*     */   public int getItemPoint(String itemname)
/*     */   {
/* 211 */     String value = this.settingService.getSetting("point", itemname);
/* 212 */     if (StringUtil.isEmpty(value)) {
/* 213 */       return 0;
/*     */     }
/* 215 */     return Integer.valueOf(value).intValue();
/*     */   }
/*     */ 
/*     */   @Transactional(propagation=Propagation.REQUIRED)
/*     */   public void add(int memberid, int point, String itemname, Integer relatedId, int mp)
/*     */   {
/* 222 */     PointHistory pointHistory = new PointHistory();
/* 223 */     pointHistory.setMember_id(memberid);
/* 224 */     pointHistory.setOperator("member");
/* 225 */     pointHistory.setPoint(point);
/* 226 */     pointHistory.setReason(itemname);
/* 227 */     pointHistory.setType(1);
/* 228 */     pointHistory.setTime(Long.valueOf(System.currentTimeMillis()));
/* 229 */     pointHistory.setRelated_id(relatedId);
/* 230 */     pointHistory.setMp(Integer.valueOf(mp));
/*     */ 
/* 232 */     this.pointHistoryManager.addPointHistory(pointHistory);
/*     */ 
/* 234 */     Member member = this.memberManager.get(Integer.valueOf(memberid));
/* 235 */     if (member == null) {
/* 236 */       this.logger.debug("获取用户失败 memberid is " + memberid);
/* 237 */       System.out.println("获取用户失败memberid is" + memberid);
/* 238 */       this.baseDaoSupport.execute("delete from freeze_point where memberid=?", new Object[] { Integer.valueOf(memberid) });
/*     */     } else {
/* 240 */       Integer memberpoint = member.getPoint();
/*     */ 
/* 243 */       this.baseDaoSupport.execute("update member set point=point+?,mp=mp+? where member_id=?", new Object[] { Integer.valueOf(point), Integer.valueOf(mp), Integer.valueOf(memberid) });
/*     */ 
/* 246 */       if (memberpoint != null) {
/* 247 */         MemberLv lv = this.memberLvManager.getByPoint(memberpoint.intValue() + point);
/* 248 */         if ((lv != null) && (
/* 249 */           (member.getLv_id() == null) || (lv.getLv_id().intValue() > member.getLv_id().intValue())))
/*     */         {
/* 254 */           this.memberManager.updateLv(memberid, lv.getLv_id().intValue());
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void deleteByOrderId(Integer orderId)
/*     */   {
/* 267 */     String sql = "delete from freeze_point where orderid=" + orderId;
/* 268 */     this.baseDaoSupport.execute(sql, new Object[0]);
/*     */   }
/*     */ 
/*     */   public IPointHistoryManager getPointHistoryManager()
/*     */   {
/* 273 */     return this.pointHistoryManager;
/*     */   }
/*     */ 
/*     */   public void setPointHistoryManager(IPointHistoryManager pointHistoryManager)
/*     */   {
/* 278 */     this.pointHistoryManager = pointHistoryManager;
/*     */   }
/*     */ 
/*     */   public ISettingService getSettingService()
/*     */   {
/* 283 */     return this.settingService;
/*     */   }
/*     */ 
/*     */   public void setSettingService(ISettingService settingService)
/*     */   {
/* 288 */     this.settingService = settingService;
/*     */   }
/*     */ 
/*     */   public IMemberManager getMemberManager()
/*     */   {
/* 293 */     return this.memberManager;
/*     */   }
/*     */ 
/*     */   public void setMemberManager(IMemberManager memberManager)
/*     */   {
/* 298 */     this.memberManager = memberManager;
/*     */   }
/*     */ 
/*     */   public IMemberLvManager getMemberLvManager()
/*     */   {
/* 303 */     return this.memberLvManager;
/*     */   }
/*     */ 
/*     */   public void setMemberLvManager(IMemberLvManager memberLvManager)
/*     */   {
/* 308 */     this.memberLvManager = memberLvManager;
/*     */   }
/*     */ 
/*     */   @Transactional(propagation=Propagation.REQUIRED)
/*     */   public void useMarketPoint(int memberid, int point, String reson, Integer relatedId)
/*     */   {
/* 315 */     PointHistory pointHistory = new PointHistory();
/* 316 */     pointHistory.setMember_id(memberid);
/* 317 */     pointHistory.setOperator("member");
/* 318 */     pointHistory.setPoint(point);
/* 319 */     pointHistory.setReason(reson);
/* 320 */     pointHistory.setType(0);
/* 321 */     pointHistory.setPoint_type(1);
/* 322 */     pointHistory.setTime(Long.valueOf(System.currentTimeMillis()));
/* 323 */     pointHistory.setRelated_id(relatedId);
/*     */ 
/* 325 */     this.pointHistoryManager.addPointHistory(pointHistory);
/* 326 */     this.baseDaoSupport.execute("update member set mp=mp-? where member_id=?", new Object[] { Integer.valueOf(point), Integer.valueOf(memberid) });
/*     */   }
/*     */ 
/*     */   public Double pointToPrice(int point)
/*     */   {
/* 333 */     return Double.valueOf(point);
/*     */   }
/*     */ 
/*     */   public int priceToPoint(Double price)
/*     */   {
/* 338 */     if (price == null) return 0;
/* 339 */     return price.intValue();
/*     */   }
/*     */ 
/*     */   public IOrderManager getOrderManager() {
/* 343 */     return this.orderManager;
/*     */   }
/*     */ 
/*     */   public void setOrderManager(IOrderManager orderManager) {
/* 347 */     this.orderManager = orderManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.service.impl.MemberPointManger
 * JD-Core Version:    0.6.0
 */