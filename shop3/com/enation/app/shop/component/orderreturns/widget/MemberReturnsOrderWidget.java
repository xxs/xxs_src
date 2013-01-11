/*     */ package com.enation.app.shop.component.orderreturns.widget;
/*     */ 
/*     */ import com.enation.app.base.core.model.Member;
/*     */ import com.enation.app.shop.component.orderreturns.service.IReturnsOrderManager;
/*     */ import com.enation.app.shop.core.model.Goods;
/*     */ import com.enation.app.shop.core.model.Order;
/*     */ import com.enation.app.shop.core.model.OrderLog;
/*     */ import com.enation.app.shop.core.model.ReturnsOrder;
/*     */ import com.enation.app.shop.core.service.IGoodsManager;
/*     */ import com.enation.app.shop.core.service.IOrderManager;
/*     */ import com.enation.eop.sdk.user.IUserService;
/*     */ import com.enation.eop.sdk.user.UserServiceFactory;
/*     */ import com.enation.eop.sdk.utils.UploadUtil;
/*     */ import com.enation.eop.sdk.widget.AbstractMemberWidget;
/*     */ import com.enation.framework.context.webcontext.ThreadContextHolder;
/*     */ import com.enation.framework.database.Page;
/*     */ import com.enation.framework.util.StringUtil;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.apache.commons.lang.xwork.StringUtils;
/*     */ import org.springframework.context.annotation.Scope;
/*     */ import org.springframework.stereotype.Component;
/*     */ 
/*     */ @Component
/*     */ @Scope("prototype")
/*     */ public class MemberReturnsOrderWidget extends AbstractMemberWidget
/*     */ {
/*     */   private IReturnsOrderManager returnsOrderManager;
/*     */   private IOrderManager orderManager;
/*     */   private IGoodsManager goodsManager;
/*     */ 
/*     */   protected void config(Map<String, String> params)
/*     */   {
/*     */   }
/*     */ 
/*     */   protected void display(Map<String, String> params)
/*     */   {
/*  47 */     setMenu("returns_order");
/*  48 */     if (StringUtils.isEmpty(this.action)) {
/*  49 */       showError("非法地址");
/*     */     }
/*  51 */     else if (this.action.equals("goodslist")) {
/*  52 */       listGoods();
/*     */     }
/*  55 */     else if (this.action.equals("list")) {
/*  56 */       list();
/*     */     }
/*  59 */     else if (this.action.equals("apply")) {
/*  60 */       apply();
/*     */     }
/*  64 */     else if (this.action.equals("add"))
/*  65 */       add();
/*     */   }
/*     */ 
/*     */   private void listGoods()
/*     */   {
/*  71 */     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
/*  72 */     String returnOrderSn = request.getParameter("returnOrderSn");
/*  73 */     if ((returnOrderSn == null) || ("".equals(returnOrderSn))) {
/*  74 */       showError("订单号为空！");
/*  75 */       return;
/*     */     }
/*  77 */     Order order = this.orderManager.get(returnOrderSn);
/*  78 */     String goodsSn = this.returnsOrderManager.getSnByOrderSn(order.getSn());
/*  79 */     String[] goodSn = null;
/*  80 */     List goodIdS = new ArrayList();
/*  81 */     if ((goodsSn != null) && (!goodsSn.equals(""))) {
/*  82 */       goodSn = StringUtils.split(goodsSn, ",");
/*  83 */       for (int j = 0; j < goodSn.length; j++) {
/*  84 */         if (goodSn[j].indexOf("-") != -1) {
/*  85 */           goodSn[j] = goodSn[j].substring(0, goodSn[j].indexOf("-"));
/*     */         }
/*     */       }
/*  88 */       for (int i = 0; i < goodSn.length; i++) {
/*  89 */         goodIdS.add(this.goodsManager.getGoodBySn(goodSn[i]).getGoods_id());
/*     */       }
/*     */     }
/*  92 */     List orderItemsList = this.orderManager.listGoodsItems(order.getOrder_id());
/*  93 */     ReturnsOrder tempReturnsOrder = this.returnsOrderManager.getByOrderSn(order.getSn());
/*  94 */     putData("orders", order);
/*  95 */     putData("orderItemsList", orderItemsList);
/*  96 */     putData("goodIdS", goodIdS);
/*  97 */     putData("returnOrder", tempReturnsOrder);
/*  98 */     setActionPageName("returns_order_detail");
/*     */   }
/*     */ 
/*     */   private void apply()
/*     */   {
/* 103 */     setActionPageName("apply");
/*     */   }
/*     */ 
/*     */   private void add()
/*     */   {
/* 110 */     showMenu(false);
/* 111 */     String pic_path = UploadUtil.uploadUseRequest("pic", "order");
/* 112 */     if (StringUtil.isEmpty(pic_path)) {
/* 113 */       showError("请您上传需要退换的商品图片！");
/* 114 */       return;
/*     */     }
/* 116 */     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
/* 117 */     Integer type1 = Integer.valueOf(request.getParameter("type1"));
/* 118 */     String orderSn = request.getParameter("ordersn");
/* 119 */     Order order = this.orderManager.get(orderSn);
/* 120 */     Member member = UserServiceFactory.getUserService().getCurrentMember();
/*     */ 
/* 122 */     if (order == null) {
/* 123 */       showError("此订单不存在！");
/* 124 */       return;
/*     */     }
/* 126 */     if ((order.getStatus().intValue() != 5) && (order.getStatus().intValue() != 6)) {
/* 127 */       showError("您的订单还没有发货！");
/* 128 */       return;
/*     */     }
/*     */ 
/* 131 */     Integer member_id = order.getMember_id();
/* 132 */     if (!member_id.equals(member.getMember_id())) {
/* 133 */       showError("此订单号不是您的订单号！");
/* 134 */       return;
/*     */     }
/* 136 */     Integer memberid = member_id;
/*     */ 
/* 139 */     ReturnsOrder tempReturnsOrder = this.returnsOrderManager.getByOrderSn(orderSn);
/* 140 */     if (tempReturnsOrder != null) {
/* 141 */       showError("此订单已经申请过退货或换货，不能再申请！");
/* 142 */       return;
/*     */     }
/* 144 */     String applyReason = request.getParameter("applyreason");
/* 145 */     String goods = request.getParameter("goodsns");
/*     */     String[] goodsns;
/* 147 */     if ((goods != null) && (!goods.equals(""))) {
/* 148 */       goodsns = StringUtils.split(goods, ",");
/*     */     } else {
/* 150 */       showError("您填写的货号为空！");
/* 151 */       return;
/*     */     }
/*     */     String[] goodsns;
/* 153 */     List items = this.orderManager.getItemsByOrderid(order.getOrder_id());
/* 154 */     if (items == null) {
/* 155 */       showError("您的订单下没有货物！");
/* 156 */       return;
/*     */     }
/* 158 */     List goodSnUnderOrder = new ArrayList();
/* 159 */     for (int i = 0; i < items.size(); i++) {
/* 160 */       goodSnUnderOrder.add(this.goodsManager.getGoods((Integer)((Map)items.get(i)).get("goods_id")).getSn());
/*     */     }
/* 162 */     for (int j = 0; j < goodsns.length; j++) {
/* 163 */       if (goodsns[j].indexOf("-") != -1) {
/* 164 */         goodsns[j] = goodsns[j].substring(0, goodsns[j].indexOf("-"));
/*     */       }
/*     */     }
/* 167 */     for (int j = 0; j < goodsns.length; j++) {
/* 168 */       if (!goodSnUnderOrder.contains(goodsns[j])) {
/* 169 */         showError("您所填写的所有货物号必须属于一个订单中！");
/* 170 */         return;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 175 */     int[] goodids = new int[goodsns.length];
/* 176 */     if (goodsns != null) {
/* 177 */       for (int i = 0; i < goodsns.length; i++) {
/* 178 */         goodids[i] = this.goodsManager.getGoodBySn(goodsns[i]).getGoods_id().intValue();
/*     */       }
/*     */     }
/* 181 */     ReturnsOrder returnOrder = new ReturnsOrder();
/* 182 */     returnOrder.setPhoto(pic_path);
/* 183 */     returnOrder.setGoodsns(goods);
/* 184 */     returnOrder.setMemberid(member_id);
/* 185 */     returnOrder.setOrdersn(orderSn);
/* 186 */     returnOrder.setApply_reason(applyReason);
/* 187 */     returnOrder.setType(type1);
/* 188 */     int orderid = this.orderManager.get(orderSn).getOrder_id().intValue();
/*     */ 
/* 190 */     OrderLog log = new OrderLog();
/* 191 */     if (type1.intValue() == 1) {
/* 192 */       log.setMessage("用户" + member.getUname() + "申请退货");
/* 193 */       log.setOp_name(member.getUname());
/* 194 */       log.setOp_id(member.getMember_id());
/* 195 */       log.setOrder_id(order.getOrder_id());
/* 196 */       this.returnsOrderManager.add(returnOrder, orderid, 1, goodids);
/* 197 */       this.returnsOrderManager.addLog(log);
/*     */     }
/* 199 */     if (type1.intValue() == 2) {
/* 200 */       log.setMessage("用户" + member.getUname() + "申请换货");
/* 201 */       log.setOp_name(member.getUname());
/* 202 */       log.setOp_id(member.getMember_id());
/* 203 */       log.setOrder_id(order.getOrder_id());
/* 204 */       this.returnsOrderManager.add(returnOrder, orderid, 2, goodids);
/* 205 */       this.returnsOrderManager.addLog(log);
/*     */     }
/* 207 */     showSuccess("申请已提交，我们会在2个工作日内处理您的请求！", "退换申请", "member_returns_order.html?action=list");
/*     */   }
/*     */ 
/*     */   private void list()
/*     */   {
/* 215 */     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
/* 216 */     String ropage = request.getParameter("ropage");
/* 217 */     ropage = (ropage == null) || (ropage.equals("")) ? "1" : ropage;
/*     */ 
/* 219 */     int pageSize = 10;
/* 220 */     Page returnOrderPage = this.returnsOrderManager.pageReturnOrder(Integer.valueOf(ropage).intValue(), pageSize);
/* 221 */     Long totalCount = Long.valueOf(returnOrderPage.getTotalCount());
/* 222 */     List returnOrderList = (List)returnOrderPage.getResult();
/* 223 */     returnOrderList = returnOrderList == null ? new ArrayList() : returnOrderList;
/* 224 */     putData("totalCount", totalCount);
/* 225 */     putData("pageSize", Integer.valueOf(pageSize));
/* 226 */     putData("ropage", ropage);
/* 227 */     putData("returnOrderList", returnOrderList);
/* 228 */     setActionPageName("returns_order_list");
/*     */   }
/*     */ 
/*     */   public IGoodsManager getGoodsManager()
/*     */   {
/* 233 */     return this.goodsManager;
/*     */   }
/*     */ 
/*     */   public void setGoodsManager(IGoodsManager goodsManager) {
/* 237 */     this.goodsManager = goodsManager;
/*     */   }
/*     */ 
/*     */   public IReturnsOrderManager getReturnsOrderManager() {
/* 241 */     return this.returnsOrderManager;
/*     */   }
/*     */ 
/*     */   public void setReturnsOrderManager(IReturnsOrderManager returnsOrderManager) {
/* 245 */     this.returnsOrderManager = returnsOrderManager;
/*     */   }
/*     */ 
/*     */   public IOrderManager getOrderManager() {
/* 249 */     return this.orderManager;
/*     */   }
/*     */ 
/*     */   public void setOrderManager(IOrderManager orderManager) {
/* 253 */     this.orderManager = orderManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.orderreturns.widget.MemberReturnsOrderWidget
 * JD-Core Version:    0.6.0
 */