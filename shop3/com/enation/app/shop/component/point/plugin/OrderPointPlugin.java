/*     */ package com.enation.app.shop.component.point.plugin;
/*     */ 
/*     */ import com.enation.app.base.core.model.Member;
/*     */ import com.enation.app.base.core.plugin.job.IEveryDayExecuteEvent;
/*     */ import com.enation.app.shop.core.model.FreezePoint;
/*     */ import com.enation.app.shop.core.model.Order;
/*     */ import com.enation.app.shop.core.model.support.CartItem;
/*     */ import com.enation.app.shop.core.plugin.order.IAfterOrderCreateEvent;
/*     */ import com.enation.app.shop.core.plugin.order.IOrderCanelEvent;
/*     */ import com.enation.app.shop.core.plugin.order.IOrderPayEvent;
/*     */ import com.enation.app.shop.core.plugin.order.IOrderRestoreEvent;
/*     */ import com.enation.app.shop.core.service.IMemberManager;
/*     */ import com.enation.app.shop.core.service.IMemberPointManger;
/*     */ import com.enation.framework.database.IDaoSupport;
/*     */ import com.enation.framework.plugin.AutoRegisterPlugin;
/*     */ import com.enation.framework.util.DateUtil;
/*     */ import java.io.PrintStream;
/*     */ import java.util.List;
/*     */ import org.springframework.stereotype.Component;
/*     */ 
/*     */ @Component
/*     */ public class OrderPointPlugin extends AutoRegisterPlugin
/*     */   implements IAfterOrderCreateEvent, IEveryDayExecuteEvent, IOrderPayEvent, IOrderCanelEvent, IOrderRestoreEvent
/*     */ {
/*     */   private IDaoSupport baseDaoSupport;
/*     */   private IMemberPointManger memberPointManger;
/*     */   private IMemberManager memberManager;
/*     */ 
/*     */   public void onAfterOrderCreate(Order order, List<CartItem> itemList, String sessionid)
/*     */   {
/*  46 */     if (order.getMember_id() != null) {
/*  47 */       Member member = this.memberManager.get(order.getMember_id());
/*     */ 
/*  53 */       if (this.memberPointManger.checkIsOpen("buygoods")) {
/*  54 */         int point = this.memberPointManger.getItemPoint("buygoods_num");
/*  55 */         int mp = this.memberPointManger.getItemPoint("buygoods_num_mp");
/*  56 */         point = order.getGoods_amount().intValue() * point;
/*  57 */         mp = order.getGoods_amount().intValue() * mp;
/*  58 */         FreezePoint freezePoint = new FreezePoint();
/*  59 */         freezePoint.setMemberid(order.getMember_id().intValue());
/*  60 */         freezePoint.setPoint(point);
/*  61 */         freezePoint.setMp(mp);
/*  62 */         freezePoint.setType("buygoods");
/*  63 */         freezePoint.setOrderid(order.getOrder_id());
/*  64 */         this.memberPointManger.addFreezePoint(freezePoint, member.getName());
/*     */       }
/*     */ 
/*  76 */       if ((this.memberPointManger.checkIsOpen("register_link")) && 
/*  77 */         (member.getParentid().intValue() != 0) && (member.getRecommend_point_state() == 0)) {
/*  78 */         int point = this.memberPointManger.getItemPoint("register_link_num");
/*  79 */         int mp = this.memberPointManger.getItemPoint("register_link_num_mp");
/*  80 */         FreezePoint freezePoint = new FreezePoint();
/*  81 */         freezePoint.setMemberid(member.getParentid().intValue());
/*  82 */         freezePoint.setChildid(order.getMember_id());
/*  83 */         freezePoint.setPoint(point);
/*  84 */         freezePoint.setMp(mp);
/*  85 */         freezePoint.setType("register_link");
/*  86 */         freezePoint.setOrderid(order.getOrder_id());
/*  87 */         this.memberPointManger.addFreezePoint(freezePoint, member.getName());
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void everyDay()
/*     */   {
/* 104 */     List list = this.memberPointManger.listByBeforeDay(15);
/* 105 */     for (FreezePoint fp : list)
/*     */     {
/* 108 */       if ((fp.getOrder_status() == 6) || (fp.getOrder_status() == 7))
/* 109 */         this.memberPointManger.thaw(fp, false);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void pay(Order order, boolean isOnline)
/*     */   {
/* 122 */     if (order.getMember_id() != null) {
/* 123 */       Member member = this.memberManager.get(order.getMember_id());
/* 124 */       if (isOnline)
/*     */       {
/* 126 */         if (this.memberPointManger.checkIsOpen("onlinepay")) {
/* 127 */           int point = this.memberPointManger.getItemPoint("onlinepay_num");
/* 128 */           int mp = this.memberPointManger.getItemPoint("onlinepay_num_mp");
/* 129 */           FreezePoint freezePoint = new FreezePoint();
/* 130 */           freezePoint.setMemberid(order.getMember_id().intValue());
/* 131 */           freezePoint.setPoint(point);
/* 132 */           freezePoint.setMp(mp);
/* 133 */           freezePoint.setType("onlinepay");
/* 134 */           freezePoint.setOrderid(order.getOrder_id());
/* 135 */           this.memberPointManger.addFreezePoint(freezePoint, member.getName());
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void canel(Order order)
/*     */   {
/* 147 */     if ((order != null) && (order.getOrder_id() != null))
/* 148 */       this.memberPointManger.deleteByOrderId(order.getOrder_id());
/*     */   }
/*     */ 
/*     */   public IMemberPointManger getMemberPointManger()
/*     */   {
/* 154 */     return this.memberPointManger;
/*     */   }
/*     */ 
/*     */   public void setMemberPointManger(IMemberPointManger memberPointManger)
/*     */   {
/* 159 */     this.memberPointManger = memberPointManger;
/*     */   }
/*     */ 
/*     */   public IMemberManager getMemberManager()
/*     */   {
/* 164 */     return this.memberManager;
/*     */   }
/*     */ 
/*     */   public void setMemberManager(IMemberManager memberManager)
/*     */   {
/* 169 */     this.memberManager = memberManager;
/*     */   }
/*     */ 
/*     */   public IDaoSupport getBaseDaoSupport() {
/* 173 */     return this.baseDaoSupport;
/*     */   }
/*     */ 
/*     */   public void setBaseDaoSupport(IDaoSupport baseDaoSupport) {
/* 177 */     this.baseDaoSupport = baseDaoSupport;
/*     */   }
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/* 186 */     System.out.println(DateUtil.getDateline());
/*     */   }
/*     */ 
/*     */   public void restore(Order order)
/*     */   {
/* 193 */     if (order.getMember_id() != null) {
/* 194 */       Member member = this.memberManager.get(order.getMember_id());
/*     */ 
/* 200 */       if (this.memberPointManger.checkIsOpen("buygoods")) {
/* 201 */         int point = this.memberPointManger.getItemPoint("buygoods_num");
/* 202 */         int mp = this.memberPointManger.getItemPoint("buygoods_num_mp");
/* 203 */         point = order.getGoods_amount().intValue() * point;
/* 204 */         mp = order.getGoods_amount().intValue() * mp;
/* 205 */         FreezePoint freezePoint = new FreezePoint();
/* 206 */         freezePoint.setMemberid(order.getMember_id().intValue());
/* 207 */         freezePoint.setPoint(point);
/* 208 */         freezePoint.setMp(mp);
/* 209 */         freezePoint.setType("buygoods");
/* 210 */         freezePoint.setOrderid(order.getOrder_id());
/* 211 */         this.memberPointManger.addFreezePoint(freezePoint, member.getName());
/*     */       }
/*     */ 
/* 223 */       if ((this.memberPointManger.checkIsOpen("register_link")) && 
/* 224 */         (member.getParentid().intValue() != 0) && (member.getRecommend_point_state() == 0)) {
/* 225 */         int point = this.memberPointManger.getItemPoint("register_link_num");
/* 226 */         int mp = this.memberPointManger.getItemPoint("register_link_num_mp");
/* 227 */         FreezePoint freezePoint = new FreezePoint();
/* 228 */         freezePoint.setMemberid(member.getParentid().intValue());
/* 229 */         freezePoint.setChildid(order.getMember_id());
/* 230 */         freezePoint.setPoint(point);
/* 231 */         freezePoint.setMp(mp);
/* 232 */         freezePoint.setType("register_link");
/* 233 */         freezePoint.setOrderid(order.getOrder_id());
/* 234 */         this.memberPointManger.addFreezePoint(freezePoint, member.getName());
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.point.plugin.OrderPointPlugin
 * JD-Core Version:    0.6.0
 */