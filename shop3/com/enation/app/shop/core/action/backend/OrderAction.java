/*     */ package com.enation.app.shop.core.action.backend;
/*     */ 
/*     */ import com.enation.app.base.core.model.Member;
/*     */ import com.enation.app.base.core.service.IRegionsManager;
/*     */ import com.enation.app.shop.core.model.Delivery;
/*     */ import com.enation.app.shop.core.model.Order;
/*     */ import com.enation.app.shop.core.plugin.order.OrderPluginBundle;
/*     */ import com.enation.app.shop.core.service.IFreeOfferManager;
/*     */ import com.enation.app.shop.core.service.IMemberManager;
/*     */ import com.enation.app.shop.core.service.IOrderFlowManager;
/*     */ import com.enation.app.shop.core.service.IOrderManager;
/*     */ import com.enation.app.shop.core.service.IOrderReportManager;
/*     */ import com.enation.app.shop.core.service.IPromotionManager;
/*     */ import com.enation.eop.sdk.context.EopSetting;
/*     */ import com.enation.framework.action.WWAction;
/*     */ import com.enation.framework.context.webcontext.ThreadContextHolder;
/*     */ import com.enation.framework.util.DateUtil;
/*     */ import com.enation.framework.util.StringUtil;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class OrderAction extends WWAction
/*     */ {
/*     */   private String sn;
/*     */   private String logi_no;
/*     */   private String uname;
/*     */   private String ship_name;
/*     */   private int status;
/*     */   private int shipping_id;
/*     */   private Integer orderId;
/*     */   private String searchKey;
/*     */   private String searchValue;
/*     */   private String order;
/*     */   private IOrderManager orderManager;
/*     */   private IMemberManager memberManager;
/*     */   private IRegionsManager regionsManager;
/*     */   private IOrderFlowManager orderFlowManager;
/*     */   private IFreeOfferManager freeOfferManager;
/*     */   private IOrderReportManager orderReportManager;
/*     */   private IPromotionManager promotionManager;
/*     */   private OrderPluginBundle orderPluginBundle;
/*     */   protected Map<Integer, String> pluginTabs;
/*     */   protected Map<Integer, String> pluginHtmls;
/*     */   private Order ord;
/*     */   private List itemList;
/*     */   private List payLogList;
/*     */   private List refundList;
/*     */   private List shipLogList;
/*     */   private List reshipLogList;
/*     */   private List chshipLogList;
/*     */   private List logList;
/*     */   private List provinceList;
/*     */   private List orderGiftList;
/*     */   private List pmtList;
/*     */   private Integer[] id;
/*     */   private int giftCount;
/*     */   private double price;
/*     */   private double shipmoney;
/*     */   private String remark;
/*     */   private Integer state;
/*     */   private Map ordermap;
/*     */   private List goodIdS;
/*     */   private Member member;
/*     */   private String refuse_reson;
/*     */   private String start;
/*     */   private String end;
/*     */   private String next;
/*     */   private String alert_null;
/*     */   private List<Delivery> deliveryList;
/*     */   private String addr;
/*     */   private String ship_day;
/*     */   private String ship_tel;
/*     */   private String ship_mobile;
/*     */   private String ship_zip;
/*     */ 
/*     */   public String getShip_day()
/*     */   {
/*  93 */     return this.ship_day;
/*     */   }
/*     */ 
/*     */   public void setShip_day(String ship_day) {
/*  97 */     this.ship_day = ship_day;
/*     */   }
/*     */ 
/*     */   public String getShip_tel() {
/* 101 */     return this.ship_tel;
/*     */   }
/*     */ 
/*     */   public void setShip_tel(String ship_tel) {
/* 105 */     this.ship_tel = ship_tel;
/*     */   }
/*     */ 
/*     */   public String getShip_mobile() {
/* 109 */     return this.ship_mobile;
/*     */   }
/*     */ 
/*     */   public void setShip_mobile(String ship_mobile) {
/* 113 */     this.ship_mobile = ship_mobile;
/*     */   }
/*     */ 
/*     */   public String getShip_zip() {
/* 117 */     return this.ship_zip;
/*     */   }
/*     */ 
/*     */   public void setShip_zip(String ship_zip) {
/* 121 */     this.ship_zip = ship_zip;
/*     */   }
/*     */ 
/*     */   public String getAddr() {
/* 125 */     return this.addr;
/*     */   }
/*     */ 
/*     */   public void setAddr(String addr) {
/* 129 */     this.addr = addr;
/*     */   }
/*     */ 
/*     */   public double getShipmoney() {
/* 133 */     return this.shipmoney;
/*     */   }
/*     */ 
/*     */   public void setShipmoney(double shipmoney) {
/* 137 */     this.shipmoney = shipmoney;
/*     */   }
/*     */ 
/*     */   public String getAlert_null() {
/* 141 */     return this.alert_null;
/*     */   }
/*     */ 
/*     */   public void setAlert_null(String alert_null) {
/* 145 */     this.alert_null = alert_null;
/*     */   }
/*     */ 
/*     */   public String getNext() {
/* 149 */     return this.next;
/*     */   }
/*     */ 
/*     */   public void setNext(String next) {
/* 153 */     this.next = next;
/*     */   }
/*     */ 
/*     */   public List getGoodIdS()
/*     */   {
/* 160 */     return this.goodIdS;
/*     */   }
/*     */ 
/*     */   public void setGoodIdS(List goodIdS) {
/* 164 */     this.goodIdS = goodIdS;
/*     */   }
/*     */ 
/*     */   public int getShipping_id()
/*     */   {
/* 170 */     return this.shipping_id;
/*     */   }
/*     */ 
/*     */   public void setShipping_id(int shipping_id) {
/* 174 */     this.shipping_id = shipping_id;
/*     */   }
/*     */ 
/*     */   public int getStatus() {
/* 178 */     return this.status;
/*     */   }
/*     */ 
/*     */   public void setStatus(int status) {
/* 182 */     this.status = status;
/*     */   }
/*     */ 
/*     */   public String getSn() {
/* 186 */     return this.sn;
/*     */   }
/*     */ 
/*     */   public void setSn(String sn) {
/* 190 */     this.sn = sn;
/*     */   }
/*     */ 
/*     */   public String getLogi_no() {
/* 194 */     return this.logi_no;
/*     */   }
/*     */ 
/*     */   public void setLogi_no(String logi_no) {
/* 198 */     this.logi_no = logi_no;
/*     */   }
/*     */ 
/*     */   public String getUname() {
/* 202 */     return this.uname;
/*     */   }
/*     */ 
/*     */   public void setUname(String uname) {
/* 206 */     this.uname = uname;
/*     */   }
/*     */ 
/*     */   public String getShip_name() {
/* 210 */     return this.ship_name;
/*     */   }
/*     */ 
/*     */   public void setShip_name(String ship_name) {
/* 214 */     this.ship_name = ship_name;
/*     */   }
/*     */ 
/*     */   public String savePrice() {
/*     */     try {
/* 219 */       this.orderManager.savePrice(this.price, this.orderId.intValue());
/* 220 */       this.json = "{result:1}";
/*     */     } catch (RuntimeException e) {
/* 222 */       this.logger.error(e.getMessage(), e);
/* 223 */       this.json = "{result:0}";
/*     */     }
/* 225 */     return "json_message";
/*     */   }
/*     */ 
/*     */   public String saveShipMoney()
/*     */   {
/*     */     try {
/* 231 */       double price = this.orderManager.saveShipmoney(getShipmoney(), this.orderId.intValue());
/* 232 */       this.json = ("{result:1,price:" + price + "}");
/*     */     } catch (RuntimeException e) {
/* 234 */       this.logger.error(e.getMessage(), e);
/* 235 */       this.json = "{result:0}";
/*     */     }
/* 237 */     return "json_message";
/*     */   }
/*     */ 
/*     */   public String saveAddrDetail() {
/*     */     try {
/* 242 */       boolean addr = this.orderManager.saveAddrDetail(getAddr(), getOrderId().intValue());
/* 243 */       if (addr)
/* 244 */         this.json = "{result:1,message:'成功！'}";
/*     */       else
/* 246 */         this.json = "{result:0,message:'失败！'}";
/*     */     }
/*     */     catch (RuntimeException e)
/*     */     {
/* 250 */       this.logger.error(e.getMessage(), e);
/* 251 */       this.json = "{result:0}";
/*     */     }
/* 253 */     return "json_message";
/*     */   }
/*     */ 
/*     */   public String saveShipInfo() {
/*     */     try {
/* 258 */       boolean addr = this.orderManager.saveShipInfo(this.remark, this.ship_day, this.ship_name, this.ship_tel, this.ship_mobile, this.ship_zip, getOrderId().intValue());
/* 259 */       if (addr)
/* 260 */         this.json = "{result:1,message:'成功！'}";
/*     */       else
/* 262 */         this.json = "{result:0,message:'失败！'}";
/*     */     }
/*     */     catch (RuntimeException e) {
/* 265 */       this.logger.error(e.getMessage(), e);
/* 266 */       this.json = "{result:0}";
/*     */     }
/* 268 */     return "json_message";
/*     */   }
/*     */ 
/*     */   public String list()
/*     */   {
/* 276 */     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
/* 277 */     setStatus(-100);
/* 278 */     String optype = request.getParameter("optype");
/* 279 */     if ((optype != null) && ("monitor".equals(optype)))
/*     */     {
/* 281 */       String depotid = request.getParameter("depotid");
/* 282 */       depotid = StringUtil.isEmpty(depotid) ? "0" : depotid;
/* 283 */       String status = request.getParameter("status");
/* 284 */       this.webpage = this.orderManager.list(getPage(), getPageSize(), Integer.parseInt(status), Integer.parseInt(depotid), this.order);
/* 285 */       return "list";
/*     */     }
/* 287 */     if (this.state != null) {
/* 288 */       this.searchKey = "status";
/* 289 */       this.searchValue = ("" + this.state);
/*     */     }
/* 291 */     this.webpage = this.orderManager.list(getPage(), getPageSize(), 0, this.searchKey, this.searchValue, this.order);
/*     */ 
/* 293 */     return "list";
/*     */   }
/*     */ 
/*     */   public String trash_list() {
/* 297 */     this.webpage = this.orderManager.list(getPage(), getPageSize(), 1, this.searchKey, this.searchValue, this.order);
/*     */ 
/* 300 */     return "trash_list";
/*     */   }
/*     */ 
/*     */   public String listbyship() {
/* 304 */     this.webpage = this.orderManager.listbyshipid(getPage(), getPageSize(), 0, this.shipping_id, this.order);
/*     */ 
/* 306 */     return "list";
/*     */   }
/*     */ 
/*     */   public String listConfirmPay()
/*     */   {
/* 314 */     this.webpage = this.orderManager.listConfirmPay(getPage(), getPageSize(), this.order);
/* 315 */     return "list";
/*     */   }
/*     */ 
/*     */   public String saveRemark()
/*     */   {
/* 321 */     this.ord = this.orderManager.get(this.orderId);
/* 322 */     this.ord.setRemark(this.remark);
/*     */     try {
/* 324 */       this.orderManager.edit(this.ord);
/* 325 */       this.json = "{result:0,message:'修改成功'}";
/*     */     } catch (RuntimeException e) {
/* 327 */       if (this.logger.isDebugEnabled()) {
/* 328 */         this.logger.debug(e);
/*     */       }
/* 330 */       this.json = ("{result:1,message:\"修改失败：" + e.getMessage() + "\"}");
/*     */     }
/* 332 */     return "json_message";
/*     */   }
/*     */ 
/*     */   public String delete() {
/*     */     try {
/* 337 */       this.orderManager.delete(this.id);
/* 338 */       this.json = "{result:0,message:'订单删除成功'}";
/*     */     } catch (RuntimeException e) {
/* 340 */       if (this.logger.isDebugEnabled()) {
/* 341 */         this.logger.debug(e);
/*     */       }
/* 343 */       this.json = ("{result:1,message:\"订单删除失败：" + e.getMessage() + "\"}");
/*     */     }
/*     */ 
/* 346 */     return "json_message";
/*     */   }
/*     */ 
/*     */   public String revert()
/*     */   {
/*     */     try {
/* 352 */       this.orderManager.revert(this.id);
/* 353 */       this.json = "{result:0,message:'订单还原成功'}";
/*     */     } catch (RuntimeException e) {
/* 355 */       if (this.logger.isDebugEnabled()) {
/* 356 */         this.logger.debug(e);
/*     */       }
/* 358 */       this.json = ("{result:1,message:\"订单还原失败：" + e.getMessage() + "\"}");
/*     */     }
/*     */ 
/* 361 */     return "json_message";
/*     */   }
/*     */ 
/*     */   public String clean()
/*     */   {
/*     */     try {
/* 367 */       this.orderManager.clean(this.id);
/* 368 */       this.json = "{result:0,message:'订单清除成功'}";
/*     */     } catch (RuntimeException e) {
/* 370 */       if (this.logger.isDebugEnabled()) {
/* 371 */         this.logger.debug(e);
/*     */       }
/* 373 */       this.json = ("{result:1,message:\"订单清除失败：" + e.getMessage() + "\"}");
/*     */     }
/*     */ 
/* 376 */     return "json_message";
/*     */   }
/*     */ 
/*     */   public String pmt()
/*     */   {
/* 386 */     this.pmtList = this.promotionManager.listOrderPmt(this.orderId);
/* 387 */     return "pmt";
/*     */   }
/*     */ 
/*     */   public String complete()
/*     */   {
/*     */     try
/*     */     {
/* 397 */       this.orderFlowManager.complete(this.orderId);
/* 398 */       Order order = this.orderManager.get(this.orderId);
/* 399 */       this.json = ("{result:1,message:'订单[" + order.getSn() + "]成功标记为完成状态',orderStatus:" + order.getStatus() + "}");
/*     */     }
/*     */     catch (RuntimeException e) {
/* 402 */       if (this.logger.isDebugEnabled()) {
/* 403 */         this.logger.debug(e);
/*     */       }
/* 405 */       this.json = ("{result:0,message:\"订单完成失败：" + e.getMessage() + "\"}");
/*     */     }
/*     */ 
/* 408 */     return "json_message";
/*     */   }
/*     */ 
/*     */   public String cancel()
/*     */   {
/*     */     try
/*     */     {
/* 418 */       this.orderFlowManager.cancel(this.orderId);
/* 419 */       Order order = this.orderManager.get(this.orderId);
/* 420 */       this.json = ("{result:1,message:'订单[" + order.getSn() + "]成功作废',orderStatus:" + order.getStatus() + "}");
/*     */     }
/*     */     catch (RuntimeException e) {
/* 423 */       if (this.logger.isDebugEnabled()) {
/* 424 */         this.logger.debug(e);
/*     */       }
/* 426 */       this.json = ("{result:0,message:\"订单作废失败：" + e.getMessage() + "\"}");
/*     */     }
/*     */ 
/* 429 */     return "json_message";
/*     */   }
/*     */ 
/*     */   public String confirmOrder()
/*     */   {
/*     */     try
/*     */     {
/* 437 */       this.orderFlowManager.confirmOrder(this.orderId);
/* 438 */       Order order = this.orderManager.get(this.orderId);
/* 439 */       this.json = ("{result:1,message:'订单[" + order.getSn() + "]成功确认',orderStatus:" + order.getStatus() + "}");
/*     */     }
/*     */     catch (RuntimeException e) {
/* 442 */       if (this.logger.isDebugEnabled()) {
/* 443 */         this.logger.debug(e);
/*     */       }
/* 445 */       this.json = ("{result:0,message:\"订单确认失败：" + e.getMessage() + "\"}");
/*     */     }
/* 447 */     return "json_message";
/*     */   }
/*     */ 
/*     */   public String toExport()
/*     */   {
/* 454 */     return "export";
/*     */   }
/*     */ 
/*     */   public String export()
/*     */   {
/*     */     try {
/* 460 */       Date startDate = null;
/* 461 */       Date endDate = null;
/*     */ 
/* 463 */       if (!StringUtil.isEmpty(this.start)) {
/* 464 */         startDate = DateUtil.toDate(this.start, "yyyy-MM-dd");
/*     */       }
/* 466 */       if (!StringUtil.isEmpty(this.end)) {
/* 467 */         endDate = DateUtil.toDate(this.end, "yyyy-MM-dd");
/*     */       }
/* 469 */       String url = this.orderManager.export(startDate, endDate);
/* 470 */       this.json = ("{result:1,url:'" + url + "'}");
/*     */     } catch (RuntimeException e) {
/* 472 */       this.logger.error("导出订单出错", e);
/* 473 */       showErrorJson(e.getMessage());
/*     */     }
/* 475 */     return "json_message";
/*     */   }
/*     */ 
/*     */   public String detail()
/*     */   {
/* 495 */     if (this.ship_name != null) this.ship_name = StringUtil.toUTF8(this.ship_name);
/* 496 */     if (this.uname != null) this.uname = StringUtil.toUTF8(this.uname);
/* 497 */     this.ord = this.orderManager.get(this.orderId);
/* 498 */     this.provinceList = this.regionsManager.listProvince();
/*     */ 
/* 500 */     this.pluginTabs = this.orderPluginBundle.getTabList(this.ord);
/* 501 */     this.pluginHtmls = this.orderPluginBundle.getDetailHtml(this.ord);
/*     */ 
/* 504 */     return "detail";
/*     */   }
/*     */ 
/*     */   public String nextDetail()
/*     */   {
/* 514 */     if (this.orderManager.getNext(this.next, this.orderId, Integer.valueOf(this.status), 0, this.sn, this.logi_no, this.uname, this.ship_name) == null) {
/* 515 */       this.alert_null = "kong";
/*     */     }
/* 517 */     this.ord = (this.orderManager.getNext(this.next, this.orderId, Integer.valueOf(this.status), 0, this.sn, this.logi_no, this.uname, this.ship_name) == null ? this.orderManager.get(this.orderId) : this.orderManager.getNext(this.next, this.orderId, Integer.valueOf(this.status), 0, this.sn, this.logi_no, this.uname, this.ship_name));
/* 518 */     this.orderId = (this.ord == null ? this.orderId : this.ord.getOrder_id());
/* 519 */     this.provinceList = this.regionsManager.listProvince();
/*     */ 
/* 522 */     this.pluginTabs = this.orderPluginBundle.getTabList(this.ord);
/* 523 */     this.pluginHtmls = this.orderPluginBundle.getDetailHtml(this.ord);
/*     */ 
/* 526 */     return "detail";
/*     */   }
/*     */ 
/*     */   public String search()
/*     */   {
/* 534 */     this.webpage = this.orderManager.search(getPage(), getPageSize(), 0, this.sn, this.logi_no, this.uname, this.ship_name, this.status);
/* 535 */     return "list";
/*     */   }
/*     */ 
/*     */   public String saveAdminRemark() {
/* 539 */     String encoding = EopSetting.ENCODING;
/* 540 */     if ((this.remark != null) && (!StringUtil.isEmpty(encoding))) {
/* 541 */       this.remark = StringUtil.to(this.remark, encoding);
/*     */     }
/* 543 */     this.ord = this.orderManager.get(this.orderId);
/* 544 */     this.ord.setAdmin_remark(this.remark);
/*     */     try {
/* 546 */       this.orderManager.edit(this.ord);
/* 547 */       this.json = "{result:0,message:'修改成功'}";
/*     */     } catch (RuntimeException e) {
/* 549 */       if (this.logger.isDebugEnabled()) {
/* 550 */         this.logger.debug(e);
/*     */       }
/* 552 */       this.json = ("{result:1,message:\"修改失败：" + e.getMessage() + "\"}");
/*     */     }
/* 554 */     return "json_message";
/*     */   }
/*     */ 
/*     */   public IOrderManager getOrderManager()
/*     */   {
/* 559 */     return this.orderManager;
/*     */   }
/*     */ 
/*     */   public void setOrderManager(IOrderManager orderManager) {
/* 563 */     this.orderManager = orderManager;
/*     */   }
/*     */ 
/*     */   public String getSearchKey() {
/* 567 */     return this.searchKey;
/*     */   }
/*     */ 
/*     */   public void setSearchKey(String searchKey) {
/* 571 */     this.searchKey = searchKey;
/*     */   }
/*     */ 
/*     */   public String getSearchValue() {
/* 575 */     return this.searchValue;
/*     */   }
/*     */ 
/*     */   public void setSearchValue(String searchValue) {
/* 579 */     this.searchValue = searchValue;
/*     */   }
/*     */ 
/*     */   public String getOrder() {
/* 583 */     return this.order;
/*     */   }
/*     */ 
/*     */   public void setOrder(String order) {
/* 587 */     this.order = order;
/*     */   }
/*     */ 
/*     */   public Order getOrd() {
/* 591 */     return this.ord;
/*     */   }
/*     */ 
/*     */   public void setOrd(Order ord) {
/* 595 */     this.ord = ord;
/*     */   }
/*     */ 
/*     */   public Integer getOrderId() {
/* 599 */     return this.orderId;
/*     */   }
/*     */ 
/*     */   public void setOrderId(Integer orderId) {
/* 603 */     this.orderId = orderId;
/*     */   }
/*     */ 
/*     */   public List getItemList() {
/* 607 */     return this.itemList;
/*     */   }
/*     */ 
/*     */   public void setItemList(List itemList) {
/* 611 */     this.itemList = itemList;
/*     */   }
/*     */ 
/*     */   public IMemberManager getMemberManager() {
/* 615 */     return this.memberManager;
/*     */   }
/*     */ 
/*     */   public void setMemberManager(IMemberManager memberManager) {
/* 619 */     this.memberManager = memberManager;
/*     */   }
/*     */ 
/*     */   public Member getMember() {
/* 623 */     return this.member;
/*     */   }
/*     */ 
/*     */   public void setMember(Member member) {
/* 627 */     this.member = member;
/*     */   }
/*     */ 
/*     */   public List getLogList() {
/* 631 */     return this.logList;
/*     */   }
/*     */ 
/*     */   public void setLogList(List logList) {
/* 635 */     this.logList = logList;
/*     */   }
/*     */ 
/*     */   public IRegionsManager getRegionsManager() {
/* 639 */     return this.regionsManager;
/*     */   }
/*     */ 
/*     */   public void setRegionsManager(IRegionsManager regionsManager) {
/* 643 */     this.regionsManager = regionsManager;
/*     */   }
/*     */ 
/*     */   public List getProvinceList() {
/* 647 */     return this.provinceList;
/*     */   }
/*     */ 
/*     */   public void setProvinceList(List provinceList) {
/* 651 */     this.provinceList = provinceList;
/*     */   }
/*     */ 
/*     */   public IOrderFlowManager getOrderFlowManager() {
/* 655 */     return this.orderFlowManager;
/*     */   }
/*     */ 
/*     */   public void setOrderFlowManager(IOrderFlowManager orderFlowManager) {
/* 659 */     this.orderFlowManager = orderFlowManager;
/*     */   }
/*     */ 
/*     */   public Integer[] getId() {
/* 663 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(Integer[] id) {
/* 667 */     this.id = id;
/*     */   }
/*     */ 
/*     */   public Map getOrdermap() {
/* 671 */     return this.ordermap;
/*     */   }
/*     */ 
/*     */   public void setOrdermap(Map ordermap) {
/* 675 */     this.ordermap = ordermap;
/*     */   }
/*     */ 
/*     */   public IFreeOfferManager getFreeOfferManager() {
/* 679 */     return this.freeOfferManager;
/*     */   }
/*     */ 
/*     */   public void setFreeOfferManager(IFreeOfferManager freeOfferManager) {
/* 683 */     this.freeOfferManager = freeOfferManager;
/*     */   }
/*     */ 
/*     */   public List getOrderGiftList() {
/* 687 */     return this.orderGiftList;
/*     */   }
/*     */ 
/*     */   public void setOrderGiftList(List orderGiftList) {
/* 691 */     this.orderGiftList = orderGiftList;
/*     */   }
/*     */ 
/*     */   public int getGiftCount() {
/* 695 */     return this.giftCount;
/*     */   }
/*     */ 
/*     */   public void setGiftCount(int giftCount) {
/* 699 */     this.giftCount = giftCount;
/*     */   }
/*     */ 
/*     */   public IOrderReportManager getOrderReportManager() {
/* 703 */     return this.orderReportManager;
/*     */   }
/*     */ 
/*     */   public void setOrderReportManager(IOrderReportManager orderReportManager) {
/* 707 */     this.orderReportManager = orderReportManager;
/*     */   }
/*     */ 
/*     */   public List getPayLogList() {
/* 711 */     return this.payLogList;
/*     */   }
/*     */ 
/*     */   public void setPayLogList(List payLogList) {
/* 715 */     this.payLogList = payLogList;
/*     */   }
/*     */ 
/*     */   public List getRefundList() {
/* 719 */     return this.refundList;
/*     */   }
/*     */ 
/*     */   public void setRefundList(List refundList) {
/* 723 */     this.refundList = refundList;
/*     */   }
/*     */ 
/*     */   public List getShipLogList() {
/* 727 */     return this.shipLogList;
/*     */   }
/*     */ 
/*     */   public void setShipLogList(List shipLogList) {
/* 731 */     this.shipLogList = shipLogList;
/*     */   }
/*     */ 
/*     */   public List getReshipLogList() {
/* 735 */     return this.reshipLogList;
/*     */   }
/*     */ 
/*     */   public void setReshipLogList(List reshipLogList) {
/* 739 */     this.reshipLogList = reshipLogList;
/*     */   }
/*     */ 
/*     */   public IPromotionManager getPromotionManager() {
/* 743 */     return this.promotionManager;
/*     */   }
/*     */ 
/*     */   public void setPromotionManager(IPromotionManager promotionManager) {
/* 747 */     this.promotionManager = promotionManager;
/*     */   }
/*     */ 
/*     */   public List getPmtList() {
/* 751 */     return this.pmtList;
/*     */   }
/*     */ 
/*     */   public void setPmtList(List pmtList) {
/* 755 */     this.pmtList = pmtList;
/*     */   }
/*     */ 
/*     */   public String getRemark() {
/* 759 */     return this.remark;
/*     */   }
/*     */ 
/*     */   public void setRemark(String remark) {
/* 763 */     this.remark = remark;
/*     */   }
/*     */ 
/*     */   public double getPrice() {
/* 767 */     return this.price;
/*     */   }
/*     */ 
/*     */   public void setPrice(double price) {
/* 771 */     this.price = price;
/*     */   }
/*     */ 
/*     */   public Integer getState() {
/* 775 */     return this.state;
/*     */   }
/*     */ 
/*     */   public void setState(Integer state) {
/* 779 */     this.state = state;
/*     */   }
/*     */ 
/*     */   public String getRefuse_reson()
/*     */   {
/* 784 */     return this.refuse_reson;
/*     */   }
/*     */ 
/*     */   public void setRefuse_reson(String refuseReson) {
/* 788 */     this.refuse_reson = refuseReson;
/*     */   }
/*     */ 
/*     */   public List getChshipLogList() {
/* 792 */     return this.chshipLogList;
/*     */   }
/*     */ 
/*     */   public void setChshipLogList(List chshipLogList) {
/* 796 */     this.chshipLogList = chshipLogList;
/*     */   }
/*     */ 
/*     */   public String getStart() {
/* 800 */     return this.start;
/*     */   }
/*     */ 
/*     */   public void setStart(String start) {
/* 804 */     this.start = start;
/*     */   }
/*     */ 
/*     */   public String getEnd() {
/* 808 */     return this.end;
/*     */   }
/*     */ 
/*     */   public void setEnd(String end) {
/* 812 */     this.end = end;
/*     */   }
/*     */ 
/*     */   public List<Delivery> getDeliveryList()
/*     */   {
/* 818 */     return this.deliveryList;
/*     */   }
/*     */ 
/*     */   public void setDeliveryList(List<Delivery> deliveryList) {
/* 822 */     this.deliveryList = deliveryList;
/*     */   }
/*     */ 
/*     */   public OrderPluginBundle getOrderPluginBundle() {
/* 826 */     return this.orderPluginBundle;
/*     */   }
/*     */ 
/*     */   public void setOrderPluginBundle(OrderPluginBundle orderPluginBundle) {
/* 830 */     this.orderPluginBundle = orderPluginBundle;
/*     */   }
/*     */ 
/*     */   public Map<Integer, String> getPluginTabs() {
/* 834 */     return this.pluginTabs;
/*     */   }
/*     */ 
/*     */   public void setPluginTabs(Map<Integer, String> pluginTabs) {
/* 838 */     this.pluginTabs = pluginTabs;
/*     */   }
/*     */ 
/*     */   public Map<Integer, String> getPluginHtmls() {
/* 842 */     return this.pluginHtmls;
/*     */   }
/*     */ 
/*     */   public void setPluginHtmls(Map<Integer, String> pluginHtmls) {
/* 846 */     this.pluginHtmls = pluginHtmls;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.action.backend.OrderAction
 * JD-Core Version:    0.6.0
 */