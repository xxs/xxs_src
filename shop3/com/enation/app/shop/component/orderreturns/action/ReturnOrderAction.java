/*     */ package com.enation.app.shop.component.orderreturns.action;
/*     */ 
/*     */ import com.enation.app.base.core.service.IRegionsManager;
/*     */ import com.enation.app.base.core.service.auth.IAdminUserManager;
/*     */ import com.enation.app.shop.component.orderreturns.service.IReturnsOrderManager;
/*     */ import com.enation.app.shop.core.model.Goods;
/*     */ import com.enation.app.shop.core.model.Order;
/*     */ import com.enation.app.shop.core.model.OrderLog;
/*     */ import com.enation.app.shop.core.model.ReturnsOrder;
/*     */ import com.enation.app.shop.core.service.IGoodsManager;
/*     */ import com.enation.app.shop.core.service.IOrderManager;
/*     */ import com.enation.eop.resource.model.AdminUser;
/*     */ import com.enation.framework.action.WWAction;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.apache.struts2.convention.annotation.Action;
/*     */ import org.apache.struts2.convention.annotation.Namespace;
/*     */ import org.apache.struts2.convention.annotation.ParentPackage;
/*     */ import org.apache.struts2.convention.annotation.Results;
/*     */ 
/*     */ @ParentPackage("shop_default")
/*     */ @Namespace("/shop/admin")
/*     */ @Results({@org.apache.struts2.convention.annotation.Result(name="return_list", type="freemarker", location="/com/enation/app/shop/component/orderreturns/action/return_list.html"), @org.apache.struts2.convention.annotation.Result(name="return_detail", type="freemarker", location="/com/enation/app/shop/component/orderreturns/action/return_detail.html"), @org.apache.struts2.convention.annotation.Result(name="changeitem", type="freemarker", location="/com/enation/app/shop/component/orderreturns/action/item_change.html")})
/*     */ @Action("returnOrder")
/*     */ public class ReturnOrderAction extends WWAction
/*     */ {
/*     */   private IAdminUserManager adminUserManager;
/*     */   private IReturnsOrderManager returnsOrderManager;
/*     */   private IGoodsManager goodsManager;
/*     */   private Integer returnOrderId;
/*     */   private ReturnsOrder rorder;
/*     */   private List itemList;
/*     */   private IOrderManager orderManager;
/*     */   private List goodIdS;
/*     */   private String refuse_reason;
/*     */   private IRegionsManager regionsManager;
/*     */   private Order ord;
/*     */   private List provinceList;
/*     */   private Integer[] itemid_array;
/*     */   private String[] targetsn_array;
/*     */   private Integer state;
/*     */ 
/*     */   public IAdminUserManager getAdminUserManager()
/*     */   {
/*  62 */     return this.adminUserManager;
/*     */   }
/*     */ 
/*     */   public void setAdminUserManager(IAdminUserManager adminUserManager) {
/*  66 */     this.adminUserManager = adminUserManager;
/*     */   }
/*     */ 
/*     */   public Integer getState() {
/*  70 */     return this.state;
/*     */   }
/*     */ 
/*     */   public void setState(Integer state) {
/*  74 */     this.state = state;
/*     */   }
/*     */ 
/*     */   public String[] getTargetsn_array() {
/*  78 */     return this.targetsn_array;
/*     */   }
/*     */ 
/*     */   public void setTargetsn_array(String[] targetsn_array) {
/*  82 */     this.targetsn_array = targetsn_array;
/*     */   }
/*     */ 
/*     */   public String getRefuse_reason() {
/*  86 */     return this.refuse_reason;
/*     */   }
/*     */ 
/*     */   public Integer[] getItemid_array() {
/*  90 */     return this.itemid_array;
/*     */   }
/*     */ 
/*     */   public void setItemid_array(Integer[] itemid_array) {
/*  94 */     this.itemid_array = itemid_array;
/*     */   }
/*     */ 
/*     */   public void setRefuse_reason(String refuse_reason) {
/*  98 */     this.refuse_reason = refuse_reason;
/*     */   }
/*     */ 
/*     */   public IRegionsManager getRegionsManager() {
/* 102 */     return this.regionsManager;
/*     */   }
/*     */ 
/*     */   public void setRegionsManager(IRegionsManager regionsManager) {
/* 106 */     this.regionsManager = regionsManager;
/*     */   }
/*     */ 
/*     */   public Order getOrd() {
/* 110 */     return this.ord;
/*     */   }
/*     */ 
/*     */   public void setOrd(Order ord) {
/* 114 */     this.ord = ord;
/*     */   }
/*     */ 
/*     */   public List getProvinceList() {
/* 118 */     return this.provinceList;
/*     */   }
/*     */ 
/*     */   public void setProvinceList(List provinceList) {
/* 122 */     this.provinceList = provinceList;
/*     */   }
/*     */ 
/*     */   public ReturnsOrder getRorder() {
/* 126 */     return this.rorder;
/*     */   }
/*     */ 
/*     */   public void setRorder(ReturnsOrder rorder) {
/* 130 */     this.rorder = rorder;
/*     */   }
/*     */ 
/*     */   public Integer getReturnOrderId() {
/* 134 */     return this.returnOrderId;
/*     */   }
/*     */ 
/*     */   public void setReturnOrderId(Integer returnOrderId) {
/* 138 */     this.returnOrderId = returnOrderId;
/*     */   }
/*     */ 
/*     */   public IReturnsOrderManager getReturnsOrderManager() {
/* 142 */     return this.returnsOrderManager;
/*     */   }
/*     */ 
/*     */   public void setReturnsOrderManager(IReturnsOrderManager returnsOrderManager) {
/* 146 */     this.returnsOrderManager = returnsOrderManager;
/*     */   }
/*     */ 
/*     */   public IGoodsManager getGoodsManager() {
/* 150 */     return this.goodsManager;
/*     */   }
/*     */ 
/*     */   public void setGoodsManager(IGoodsManager goodsManager) {
/* 154 */     this.goodsManager = goodsManager;
/*     */   }
/*     */ 
/*     */   public List getItemList() {
/* 158 */     return this.itemList;
/*     */   }
/*     */ 
/*     */   public void setItemList(List itemList) {
/* 162 */     this.itemList = itemList;
/*     */   }
/*     */ 
/*     */   public IOrderManager getOrderManager() {
/* 166 */     return this.orderManager;
/*     */   }
/*     */ 
/*     */   public void setOrderManager(IOrderManager orderManager) {
/* 170 */     this.orderManager = orderManager;
/*     */   }
/*     */ 
/*     */   public List getGoodIdS() {
/* 174 */     return this.goodIdS;
/*     */   }
/*     */ 
/*     */   public void setGoodIdS(List goodIdS) {
/* 178 */     this.goodIdS = goodIdS;
/*     */   }
/*     */ 
/*     */   public String refuseReturn()
/*     */   {
/*     */     try
/*     */     {
/* 188 */       this.returnsOrderManager.refuse(this.returnOrderId, this.refuse_reason, 1);
/*     */ 
/* 190 */       int orderid = this.returnsOrderManager.queryOrderidByReturnorderid(this.returnOrderId.intValue());
/*     */ 
/* 192 */       this.returnsOrderManager.updateItemStatusByOrderidAndStatus(3, 1, orderid);
/*     */ 
/* 195 */       AdminUser adminUser = this.adminUserManager.getCurrentUser();
/* 196 */       OrderLog log = new OrderLog();
/* 197 */       log.setMessage("管理员" + adminUser.getUsername() + "拒绝退货");
/* 198 */       log.setOp_id(adminUser.getUserid());
/* 199 */       log.setOp_name(adminUser.getUsername());
/* 200 */       log.setOrder_id(this.returnsOrderManager.getOrderidByReturnid(this.returnOrderId));
/*     */ 
/* 202 */       this.returnsOrderManager.addLog(log);
/* 203 */       showSuccessJson("");
/*     */     } catch (RuntimeException e) {
/* 205 */       e.printStackTrace();
/* 206 */       this.logger.error(e.fillInStackTrace());
/* 207 */       showErrorJson(e.getMessage());
/*     */     }
/* 209 */     return "json_message";
/*     */   }
/*     */ 
/*     */   public String agreeReturn()
/*     */   {
/* 218 */     Integer orderid = this.returnsOrderManager.getOrderidByReturnid(this.returnOrderId);
/*     */     try {
/* 220 */       this.returnsOrderManager.updateState(this.returnOrderId, 2);
/*     */ 
/* 222 */       if ((this.itemid_array != null) && (this.itemid_array.length > 0)) {
/* 223 */         for (int i = 0; i < this.itemid_array.length; i++) {
/* 224 */           this.returnsOrderManager.updateOrderItemsState(this.itemid_array[i], 5);
/*     */         }
/*     */ 
/* 227 */         this.returnsOrderManager.updateItemsState(orderid, 3, 1);
/*     */       }
/* 229 */       AdminUser adminUser = this.adminUserManager.getCurrentUser();
/* 230 */       OrderLog log = new OrderLog();
/* 231 */       log.setMessage("管理员" + adminUser.getUsername() + "同意退货");
/* 232 */       log.setOp_id(adminUser.getUserid());
/* 233 */       log.setOp_name(adminUser.getUsername());
/* 234 */       log.setOrder_id(orderid);
/* 235 */       this.returnsOrderManager.addLog(log);
/* 236 */       showSuccessJson("");
/*     */     } catch (RuntimeException e) {
/* 238 */       e.printStackTrace();
/* 239 */       this.logger.error(e.fillInStackTrace());
/* 240 */       showErrorJson(e.getMessage());
/*     */     }
/* 242 */     return "json_message";
/*     */   }
/*     */ 
/*     */   public String confirmReturnReceive()
/*     */   {
/*     */     try
/*     */     {
/* 252 */       this.returnsOrderManager.updateState(this.returnOrderId, 3);
/*     */ 
/* 254 */       int orderid = this.returnsOrderManager.queryOrderidByReturnorderid(this.returnOrderId.intValue());
/*     */ 
/* 256 */       this.returnsOrderManager.updateItemStatusByOrderidAndStatus(7, 5, orderid);
/*     */ 
/* 259 */       OrderLog log = new OrderLog();
/* 260 */       AdminUser adminUser = this.adminUserManager.getCurrentUser();
/* 261 */       log.setMessage("管理员" + adminUser.getUsername() + "已收到退货,正在执行退款");
/* 262 */       log.setOp_id(adminUser.getUserid());
/* 263 */       log.setOp_name(adminUser.getUsername());
/* 264 */       log.setOrder_id(this.returnsOrderManager.getOrderidByReturnid(this.returnOrderId));
/*     */ 
/* 266 */       this.returnsOrderManager.addLog(log);
/* 267 */       showSuccessJson("");
/*     */     } catch (Exception e) {
/* 269 */       e.printStackTrace();
/* 270 */       this.logger.error(e.fillInStackTrace());
/* 271 */       showErrorJson(e.getMessage());
/*     */     }
/* 273 */     return "json_message";
/*     */   }
/*     */ 
/*     */   public String returned()
/*     */   {
/*     */     try
/*     */     {
/* 281 */       this.returnsOrderManager.updateState(this.returnOrderId, 4);
/*     */ 
/* 283 */       int orderid = this.returnsOrderManager.queryOrderidByReturnorderid(this.returnOrderId.intValue());
/*     */ 
/* 285 */       double prices = this.returnsOrderManager.queryItemPrice(Integer.valueOf(orderid), Integer.valueOf(7)).doubleValue();
/*     */ 
/* 287 */       this.returnsOrderManager.updateItemStatusByOrderidAndStatus(9, 7, orderid);
/*     */ 
/* 290 */       this.orderManager.updateOrderPrice(prices, orderid);
/* 291 */       OrderLog log = new OrderLog();
/* 292 */       AdminUser adminUser = this.adminUserManager.getCurrentUser();
/* 293 */       log.setMessage("管理员" + adminUser.getUsername() + "完成退货");
/* 294 */       log.setOp_id(adminUser.getUserid());
/* 295 */       log.setOp_name(adminUser.getUsername());
/* 296 */       log.setOrder_id(this.returnsOrderManager.getOrderidByReturnid(this.returnOrderId));
/*     */ 
/* 298 */       this.returnsOrderManager.addLog(log);
/* 299 */       showSuccessJson("");
/*     */     } catch (Exception e) {
/* 301 */       e.printStackTrace();
/* 302 */       this.logger.error(e.fillInStackTrace());
/* 303 */       showErrorJson(e.getMessage());
/*     */     }
/* 305 */     return "json_message";
/*     */   }
/*     */ 
/*     */   public String refuseChange()
/*     */   {
/*     */     try
/*     */     {
/* 313 */       this.returnsOrderManager.refuse(this.returnOrderId, this.refuse_reason, 1);
/*     */ 
/* 315 */       int orderid = this.returnsOrderManager.queryOrderidByReturnorderid(this.returnOrderId.intValue());
/*     */ 
/* 317 */       this.returnsOrderManager.updateItemStatusByOrderidAndStatus(4, 2, orderid);
/*     */ 
/* 320 */       AdminUser adminUser = this.adminUserManager.getCurrentUser();
/* 321 */       OrderLog log = new OrderLog();
/* 322 */       log.setMessage("管理员" + adminUser.getUsername() + "拒绝换货");
/* 323 */       log.setOp_id(adminUser.getUserid());
/* 324 */       log.setOp_name(adminUser.getUsername());
/* 325 */       log.setOrder_id(this.returnsOrderManager.getOrderidByReturnid(this.returnOrderId));
/*     */ 
/* 327 */       this.returnsOrderManager.addLog(log);
/* 328 */       showSuccessJson("");
/*     */     } catch (RuntimeException e) {
/* 330 */       e.printStackTrace();
/* 331 */       this.logger.error(e.fillInStackTrace());
/* 332 */       showErrorJson(e.getMessage());
/*     */     }
/* 334 */     return "json_message";
/*     */   }
/*     */ 
/*     */   public String agreeChange()
/*     */   {
/* 341 */     Integer orderid = this.returnsOrderManager.getOrderidByReturnid(this.returnOrderId);
/*     */     try {
/* 343 */       this.returnsOrderManager.updateState(this.returnOrderId, 2);
/*     */ 
/* 345 */       if ((this.itemid_array != null) && (this.itemid_array.length > 0)) {
/* 346 */         for (int i = 0; i < this.itemid_array.length; i++) {
/* 347 */           this.returnsOrderManager.updateOrderItemsState(this.itemid_array[i], 6);
/*     */         }
/*     */ 
/* 350 */         this.returnsOrderManager.updateItemsState(orderid, 4, 2);
/*     */       }
/* 352 */       AdminUser adminUser = this.adminUserManager.getCurrentUser();
/* 353 */       OrderLog log = new OrderLog();
/* 354 */       log.setMessage("管理员" + adminUser.getUsername() + "同意换货");
/* 355 */       log.setOp_id(adminUser.getUserid());
/* 356 */       log.setOp_name(adminUser.getUsername());
/* 357 */       log.setOrder_id(orderid);
/* 358 */       this.returnsOrderManager.addLog(log);
/* 359 */       showSuccessJson("");
/*     */     } catch (RuntimeException e) {
/* 361 */       e.printStackTrace();
/* 362 */       this.logger.error(e.fillInStackTrace());
/* 363 */       showErrorJson(e.getMessage());
/*     */     }
/* 365 */     return "json_message";
/*     */   }
/*     */ 
/*     */   public String confirmChangeReceive()
/*     */   {
/* 372 */     int orderid = this.returnsOrderManager.queryOrderidByReturnorderid(this.returnOrderId.intValue());
/*     */ 
/* 374 */     double prices = this.returnsOrderManager.queryItemPrice(Integer.valueOf(orderid), Integer.valueOf(6)).doubleValue();
/*     */     try
/*     */     {
/* 377 */       if ((this.targetsn_array != null) && (this.targetsn_array.length > 0)) {
/* 378 */         this.targetsn_array = dealwithhyphen(this.targetsn_array);
/* 379 */         for (int i = 0; i < this.targetsn_array.length; i++) {
/* 380 */           Goods g = this.goodsManager.getGoodBySn(this.targetsn_array[i]);
/* 381 */           if (g == null) {
/* 382 */             showErrorJson("目标货号必须都存在！");
/* 383 */             return "json_message";
/*     */           }
/*     */         }
/* 386 */         for (int i = 0; i < this.targetsn_array.length; i++) {
/* 387 */           this.targetsn_array = dealwithhyphen(this.targetsn_array);
/* 388 */           double temp_price = this.goodsManager.getGoodBySn(this.targetsn_array[i]).getPrice().doubleValue();
/*     */ 
/* 391 */           this.returnsOrderManager.updatePriceByItemid(this.itemid_array[i].intValue(), temp_price);
/*     */         }
/*     */       }
/*     */ 
/* 395 */       if ((this.targetsn_array != null) && (this.targetsn_array.length > 0)) {
/* 396 */         for (int i = 0; i < this.targetsn_array.length; i++) {
/* 397 */           this.targetsn_array = dealwithhyphen(this.targetsn_array);
/* 398 */           Goods g = this.goodsManager.getGoodBySn(this.targetsn_array[i]);
/* 399 */           prices -= g.getPrice().doubleValue();
/* 400 */           this.returnsOrderManager.updateItemChange(g.getName(), g.getGoods_id().intValue(), this.itemid_array[i].intValue());
/*     */         }
/*     */       }
/*     */ 
/* 404 */       this.orderManager.updateOrderPrice(prices, orderid);
/* 405 */       this.returnsOrderManager.updateState(this.returnOrderId, 3);
/*     */ 
/* 407 */       this.returnsOrderManager.updateItemStatusByOrderidAndStatus(8, 6, orderid);
/*     */ 
/* 410 */       OrderLog log = new OrderLog();
/* 411 */       AdminUser adminUser = this.adminUserManager.getCurrentUser();
/* 412 */       log.setMessage("管理员" + adminUser.getUsername() + "已收到换货,换货已发出");
/* 413 */       log.setOp_id(adminUser.getUserid());
/* 414 */       log.setOp_name(adminUser.getUsername());
/* 415 */       log.setOrder_id(this.returnsOrderManager.getOrderidByReturnid(this.returnOrderId));
/*     */ 
/* 417 */       this.returnsOrderManager.addLog(log);
/* 418 */       showSuccessJson("");
/*     */     } catch (Exception e) {
/* 420 */       e.printStackTrace();
/* 421 */       this.logger.error(e.fillInStackTrace());
/* 422 */       showErrorJson(e.getMessage());
/*     */     }
/* 424 */     return "json_message";
/*     */   }
/*     */ 
/*     */   public String changed()
/*     */   {
/*     */     try
/*     */     {
/* 432 */       this.returnsOrderManager.updateState(this.returnOrderId, 4);
/*     */ 
/* 434 */       int orderid = this.returnsOrderManager.queryOrderidByReturnorderid(this.returnOrderId.intValue());
/*     */ 
/* 436 */       this.returnsOrderManager.updateItemStatusByOrderidAndStatus(10, 8, orderid);
/*     */ 
/* 439 */       OrderLog log = new OrderLog();
/* 440 */       AdminUser adminUser = this.adminUserManager.getCurrentUser();
/* 441 */       log.setMessage("管理员" + adminUser.getUsername() + "完成换货");
/* 442 */       log.setOp_id(adminUser.getUserid());
/* 443 */       log.setOp_name(adminUser.getUsername());
/* 444 */       log.setOrder_id(this.returnsOrderManager.getOrderidByReturnid(this.returnOrderId));
/*     */ 
/* 446 */       this.returnsOrderManager.addLog(log);
/* 447 */       showSuccessJson("");
/*     */     } catch (RuntimeException e) {
/* 449 */       e.printStackTrace();
/* 450 */       this.logger.error(e.fillInStackTrace());
/* 451 */       showErrorJson(e.getMessage());
/*     */     }
/* 453 */     return "json_message";
/*     */   }
/*     */ 
/*     */   public String returnsList()
/*     */   {
/* 462 */     this.webpage = this.returnsOrderManager.listAll(getPage(), getPageSize());
/*     */ 
/* 464 */     return "return_list";
/*     */   }
/*     */ 
/*     */   public String returnsApplyList()
/*     */   {
/* 473 */     this.webpage = this.returnsOrderManager.listAll(getPage(), getPageSize(), this.state.intValue());
/*     */ 
/* 475 */     return "return_list";
/*     */   }
/*     */ 
/*     */   public String returnDetail()
/*     */   {
/* 484 */     this.rorder = this.returnsOrderManager.get(this.returnOrderId);
/* 485 */     this.itemList = this.orderManager.listGoodsItems(this.orderManager.get(this.rorder.getOrdersn()).getOrder_id());
/*     */ 
/* 487 */     String goodsSn = this.returnsOrderManager.getSnByOrderSn(this.rorder.getOrdersn());
/*     */ 
/* 489 */     String[] goodSn = null;
/* 490 */     this.goodIdS = new ArrayList();
/* 491 */     if ((goodsSn != null) && (!goodsSn.equals(""))) {
/* 492 */       goodSn = StringUtils.split(goodsSn, ",");
/* 493 */       goodSn = dealwithhyphen(goodSn);
/* 494 */       for (int i = 0; i < goodSn.length; i++) {
/* 495 */         this.goodIdS.add(this.goodsManager.getGoodBySn(goodSn[i]).getGoods_id());
/*     */       }
/*     */     }
/* 498 */     return "return_detail";
/*     */   }
/*     */ 
/*     */   public String returnD() {
/* 502 */     this.rorder = this.returnsOrderManager.get(this.returnOrderId);
/* 503 */     this.itemList = this.orderManager.listGoodsItems(this.orderManager.get(this.rorder.getOrdersn()).getOrder_id());
/*     */ 
/* 505 */     return "changeitem";
/*     */   }
/*     */ 
/*     */   public String[] dealwithhyphen(String[] targetsn_array) {
/* 509 */     for (int j = 0; j < targetsn_array.length; j++) {
/* 510 */       if (targetsn_array[j].indexOf("-") != -1) {
/* 511 */         targetsn_array[j] = targetsn_array[j].substring(0, targetsn_array[j].indexOf("-"));
/*     */       }
/*     */     }
/* 514 */     return targetsn_array;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.orderreturns.action.ReturnOrderAction
 * JD-Core Version:    0.6.0
 */