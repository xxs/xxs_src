/*     */ package com.enation.app.shop.core.action.backend;
/*     */ 
/*     */ import com.enation.app.base.core.model.Member;
/*     */ import com.enation.app.shop.core.model.Depot;
/*     */ import com.enation.app.shop.core.model.DlyCenter;
/*     */ import com.enation.app.shop.core.model.Order;
/*     */ import com.enation.app.shop.core.model.PrintTmpl;
/*     */ import com.enation.app.shop.core.service.IDepotManager;
/*     */ import com.enation.app.shop.core.service.IDlyCenterManager;
/*     */ import com.enation.app.shop.core.service.IFreeOfferManager;
/*     */ import com.enation.app.shop.core.service.IMemberManager;
/*     */ import com.enation.app.shop.core.service.IOrderAllocationManager;
/*     */ import com.enation.app.shop.core.service.IOrderManager;
/*     */ import com.enation.app.shop.core.service.IPrintTmplManager;
/*     */ import com.enation.eop.resource.IUserManager;
/*     */ import com.enation.eop.resource.model.EopSite;
/*     */ import com.enation.eop.resource.model.EopUser;
/*     */ import com.enation.eop.sdk.context.EopContext;
/*     */ import com.enation.framework.action.WWAction;
/*     */ import com.enation.framework.util.StringUtil;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class OrderPrintAction extends WWAction
/*     */ {
/*     */   private IOrderManager orderManager;
/*     */   private IFreeOfferManager freeOfferManager;
/*     */   private IMemberManager memberManager;
/*     */   private IDlyCenterManager dlyCenterManager;
/*     */   private IPrintTmplManager printTmplManager;
/*     */   private Member member;
/*     */   private Integer orderId;
/*     */   private Order ord;
/*     */   private List itemList;
/*     */   private Map ordermap;
/*     */   private List dlyCenterList;
/*     */   private Integer dly_center_id;
/*     */   private DlyCenter dlyCenter;
/*     */   private List printTmplList;
/*     */   private Integer prt_tmpl_id;
/*     */   private PrintTmpl printTmpl;
/*     */   private String saveAddr;
/*     */   private EopSite site;
/*     */   private EopUser user;
/*     */   private IUserManager userManager;
/*     */   private long all_time;
/*     */   private String all_depotname;
/*     */   private IOrderAllocationManager orderAllocationManager;
/*     */   private IDepotManager depotManager;
/*     */   private List allocationList;
/*     */   private String printXml;
/*     */   private String printData;
/*     */   private String printBg;
/*     */ 
/*     */   public List getAllocationList()
/*     */   {
/*  63 */     return this.allocationList;
/*     */   }
/*     */ 
/*     */   public void setAllocationList(List allocationList) {
/*  67 */     this.allocationList = allocationList;
/*     */   }
/*     */ 
/*     */   public long getAll_time() {
/*  71 */     return this.all_time;
/*     */   }
/*     */ 
/*     */   public void setAll_time(long all_time) {
/*  75 */     this.all_time = all_time;
/*     */   }
/*     */ 
/*     */   public String getAll_depotname() {
/*  79 */     return this.all_depotname;
/*     */   }
/*     */ 
/*     */   public void setAll_depotname(String all_depotname) {
/*  83 */     this.all_depotname = all_depotname;
/*     */   }
/*     */ 
/*     */   public IOrderAllocationManager getOrderAllocationManager() {
/*  87 */     return this.orderAllocationManager;
/*     */   }
/*     */ 
/*     */   public void setOrderAllocationManager(IOrderAllocationManager orderAllocationManager)
/*     */   {
/*  92 */     this.orderAllocationManager = orderAllocationManager;
/*     */   }
/*     */ 
/*     */   public IDepotManager getDepotManager() {
/*  96 */     return this.depotManager;
/*     */   }
/*     */ 
/*     */   public void setDepotManager(IDepotManager depotManager) {
/* 100 */     this.depotManager = depotManager;
/*     */   }
/*     */ 
/*     */   public String order_prnt() {
/* 104 */     this.site = EopContext.getContext().getCurrentSite();
/* 105 */     this.user = this.userManager.get(this.site.getUserid());
/* 106 */     this.itemList = this.orderManager.listGoodsItems(this.orderId);
/* 107 */     this.ord = this.orderManager.get(this.orderId);
/*     */ 
/* 109 */     if (this.ord.getMember_id() != null) {
/* 110 */       this.member = this.memberManager.get(this.ord.getMember_id());
/* 111 */       this.ordermap = this.orderManager.mapOrderByMemberId(this.ord.getMember_id().intValue());
/*     */     }
/*     */ 
/* 114 */     return "order_prnt";
/*     */   }
/*     */ 
/*     */   public String delivery_prnt() {
/* 118 */     this.site = EopContext.getContext().getCurrentSite();
/* 119 */     this.user = this.userManager.get(this.site.getUserid());
/*     */ 
/* 122 */     this.all_time = this.orderManager.get(this.orderId).getAllocation_time().longValue();
/* 123 */     this.all_depotname = this.depotManager.get(this.orderManager.get(this.orderId).getDepotid().intValue()).getName();
/* 124 */     this.allocationList = this.orderAllocationManager.listAllocation(this.orderId.intValue());
/*     */ 
/* 126 */     this.ord = this.orderManager.get(this.orderId);
/*     */ 
/* 128 */     if (this.ord.getMember_id() != null) {
/* 129 */       this.member = this.memberManager.get(this.ord.getMember_id());
/*     */     }
/* 131 */     return "delivery_prnt";
/*     */   }
/*     */ 
/*     */   public String global_prnt() {
/* 135 */     this.site = EopContext.getContext().getCurrentSite();
/* 136 */     this.user = this.userManager.get(this.site.getUserid());
/*     */ 
/* 138 */     this.itemList = this.orderAllocationManager.listAllocation(this.orderId.intValue());
/* 139 */     this.ord = this.orderManager.get(this.orderId);
/* 140 */     if (this.ord.getMember_id() != null) {
/* 141 */       this.member = this.memberManager.get(this.ord.getMember_id());
/* 142 */       this.ordermap = this.orderManager.mapOrderByMemberId(this.ord.getMember_id().intValue());
/*     */     }
/*     */ 
/* 145 */     return "global_prnt";
/*     */   }
/*     */ 
/*     */   public String ship_prnt_step1() {
/* 149 */     this.ord = this.orderManager.get(this.orderId);
/* 150 */     this.dlyCenterList = this.dlyCenterManager.list();
/* 151 */     this.printTmplList = this.printTmplManager.listCanUse();
/* 152 */     return "ship_prnt_step1";
/*     */   }
/*     */ 
/*     */   public String ship_prnt_step2() throws UnsupportedEncodingException {
/* 156 */     this.site = EopContext.getContext().getCurrentSite();
/* 157 */     this.user = this.userManager.get(this.site.getUserid());
/* 158 */     Order order = this.orderManager.get(this.orderId);
/* 159 */     order.setShip_addr(this.ord.getShip_addr());
/* 160 */     order.setShip_name(this.ord.getShip_name());
/* 161 */     order.setShipping_area(this.ord.getShipping_area());
/* 162 */     order.setShip_zip(this.ord.getShip_zip());
/* 163 */     order.setShip_mobile(this.ord.getShip_mobile());
/* 164 */     order.setShip_tel(this.ord.getShip_tel());
/* 165 */     order.setRemark(this.ord.getRemark());
/* 166 */     if (StringUtil.equals(this.saveAddr, "1")) {
/* 167 */       this.orderManager.edit(order);
/*     */     }
/* 169 */     this.dlyCenter = this.dlyCenterManager.get(this.dly_center_id);
/* 170 */     this.printTmpl = this.printTmplManager.get(this.prt_tmpl_id.intValue());
/*     */ 
/* 172 */     this.printXml = this.printTmpl.getPrt_tmpl_data();
/* 173 */     if (this.printXml != null) {
/* 174 */       this.printXml = this.printXml.replaceAll("picpos", "picposition");
/* 175 */       this.printXml = this.printXml.replaceAll("letterspace", "fontspace");
/* 176 */       this.printXml = this.printXml.replaceAll("items", "item");
/* 177 */       this.printXml = this.printXml.replaceAll("bold", "border");
/*     */     }
/*     */ 
/* 180 */     this.printBg = this.printTmpl.getBgimage();
/* 181 */     this.printData = processTmplData(order, this.dlyCenter);
/*     */ 
/* 184 */     return "ship_prnt_step2";
/*     */   }
/*     */ 
/*     */   private String processTmplData(Order order, DlyCenter dlyCenter) {
/* 188 */     Date date = new Date();
/* 189 */     Calendar cal = Calendar.getInstance();
/* 190 */     cal.setTime(date);
/* 191 */     int year = cal.get(1);
/* 192 */     int month = cal.get(2) + 1;
/* 193 */     int day = cal.get(5);
/* 194 */     String ship_area = order.getShipping_area();
/* 195 */     String ship_province = "";
/* 196 */     String ship_city = "";
/* 197 */     String ship_region = "";
/* 198 */     if (!StringUtil.isEmpty(ship_area)) {
/* 199 */       String[] area_ar = ship_area.split("-");
/* 200 */       if (area_ar.length >= 1) {
/* 201 */         ship_province = area_ar[0];
/*     */       }
/* 203 */       if (area_ar.length >= 2) {
/* 204 */         ship_city = area_ar[1];
/*     */       }
/* 206 */       if (area_ar.length >= 3) {
/* 207 */         ship_region = area_ar[2];
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 212 */     StringBuffer result = new StringBuffer();
/* 213 */     result.append("<data>");
/* 214 */     result.append("<order_id>" + order.getSn() + "</order_id>");
/* 215 */     result.append("<order_count>0</order_count>");
/* 216 */     result.append("<ship_time><![CDATA[" + order.getShip_day() + order.getShip_time() + " ]]></ship_time>");
/* 217 */     result.append("<order_price>" + order.getOrder_amount() + "</order_price>");
/* 218 */     result.append("<order_weight>" + order.getWeight() + "</order_weight>");
/* 219 */     result.append("<ship_name><![CDATA[" + order.getShip_name() + "]]></ship_name>");
/* 220 */     result.append("<ship_zip>" + order.getShip_zip() + "</ship_zip>");
/* 221 */     result.append("<ship_addr><![CDATA[" + order.getShip_addr() + "]]></ship_addr>");
/* 222 */     result.append("<ship_mobile>" + order.getShip_mobile() + "</ship_mobile>");
/* 223 */     result.append("<ship_tel><![CDATA[" + order.getShip_tel() + "]]></ship_tel>");
/* 224 */     result.append("<order_memo><![CDATA[" + order.getRemark() + "]]></order_memo>");
/* 225 */     result.append("<shop_name><![CDATA[" + EopContext.getContext().getCurrentSite().getSitename() + "]]></shop_name>");
/* 226 */     result.append("<dly_name><![CDATA[" + dlyCenter.getUname() + "]]></dly_name>");
/* 227 */     result.append("<ship_province><![CDATA[" + ship_province + "]]></ship_province>");
/* 228 */     result.append("<ship_city><![CDATA[" + ship_city + "]]></ship_city>");
/* 229 */     result.append("<ship_region><![CDATA[" + ship_region + "]]></ship_region>");
/* 230 */     result.append("<dly_province><![CDATA[" + dlyCenter.getProvince() + "]]></dly_province>");
/* 231 */     result.append("<dly_city><![CDATA[" + dlyCenter.getCity() + "]]></dly_city>");
/* 232 */     result.append("<dly_region><![CDATA[" + dlyCenter.getRegion() + "]]></dly_region>");
/* 233 */     result.append("<dly_address><![CDATA[" + dlyCenter.getAddress() + "]]></dly_address>");
/* 234 */     result.append("<dly_tel><![CDATA[" + dlyCenter.getPhone() + "]]></dly_tel>");
/* 235 */     result.append("<dly_mobile>" + dlyCenter.getCellphone() + "</dly_mobile>");
/* 236 */     result.append("<dly_zip>" + dlyCenter.getZip() + "</dly_zip>");
/* 237 */     result.append("<date_y>" + String.valueOf(year) + "</date_y>");
/* 238 */     result.append("<date_m>" + String.valueOf(month) + "</date_m>");
/* 239 */     result.append("<date_d>" + String.valueOf(day) + "</date_d>");
/* 240 */     result.append("</data>");
/*     */ 
/* 243 */     return result.toString();
/*     */   }
/*     */ 
/*     */   public IOrderManager getOrderManager() {
/* 247 */     return this.orderManager;
/*     */   }
/*     */ 
/*     */   public void setOrderManager(IOrderManager orderManager) {
/* 251 */     this.orderManager = orderManager;
/*     */   }
/*     */ 
/*     */   public IFreeOfferManager getFreeOfferManager() {
/* 255 */     return this.freeOfferManager;
/*     */   }
/*     */ 
/*     */   public void setFreeOfferManager(IFreeOfferManager freeOfferManager) {
/* 259 */     this.freeOfferManager = freeOfferManager;
/*     */   }
/*     */ 
/*     */   public IMemberManager getMemberManager() {
/* 263 */     return this.memberManager;
/*     */   }
/*     */ 
/*     */   public void setMemberManager(IMemberManager memberManager) {
/* 267 */     this.memberManager = memberManager;
/*     */   }
/*     */ 
/*     */   public Member getMember() {
/* 271 */     return this.member;
/*     */   }
/*     */ 
/*     */   public void setMember(Member member) {
/* 275 */     this.member = member;
/*     */   }
/*     */ 
/*     */   public Integer getOrderId() {
/* 279 */     return this.orderId;
/*     */   }
/*     */ 
/*     */   public void setOrderId(Integer orderId) {
/* 283 */     this.orderId = orderId;
/*     */   }
/*     */ 
/*     */   public Order getOrd() {
/* 287 */     return this.ord;
/*     */   }
/*     */ 
/*     */   public void setOrd(Order ord) {
/* 291 */     this.ord = ord;
/*     */   }
/*     */ 
/*     */   public List getItemList() {
/* 295 */     return this.itemList;
/*     */   }
/*     */ 
/*     */   public void setItemList(List itemList) {
/* 299 */     this.itemList = itemList;
/*     */   }
/*     */ 
/*     */   public Map getOrdermap()
/*     */   {
/* 305 */     return this.ordermap;
/*     */   }
/*     */ 
/*     */   public void setOrdermap(Map ordermap) {
/* 309 */     this.ordermap = ordermap;
/*     */   }
/*     */ 
/*     */   public List getDlyCenterList() {
/* 313 */     return this.dlyCenterList;
/*     */   }
/*     */ 
/*     */   public void setDlyCenterList(List dlyCenterList) {
/* 317 */     this.dlyCenterList = dlyCenterList;
/*     */   }
/*     */ 
/*     */   public IDlyCenterManager getDlyCenterManager() {
/* 321 */     return this.dlyCenterManager;
/*     */   }
/*     */ 
/*     */   public void setDlyCenterManager(IDlyCenterManager dlyCenterManager) {
/* 325 */     this.dlyCenterManager = dlyCenterManager;
/*     */   }
/*     */ 
/*     */   public IPrintTmplManager getPrintTmplManager() {
/* 329 */     return this.printTmplManager;
/*     */   }
/*     */ 
/*     */   public void setPrintTmplManager(IPrintTmplManager printTmplManager) {
/* 333 */     this.printTmplManager = printTmplManager;
/*     */   }
/*     */ 
/*     */   public List getPrintTmplList() {
/* 337 */     return this.printTmplList;
/*     */   }
/*     */ 
/*     */   public void setPrintTmplList(List printTmplList) {
/* 341 */     this.printTmplList = printTmplList;
/*     */   }
/*     */ 
/*     */   public PrintTmpl getPrintTmpl() {
/* 345 */     return this.printTmpl;
/*     */   }
/*     */ 
/*     */   public void setPrintTmpl(PrintTmpl printTmpl) {
/* 349 */     this.printTmpl = printTmpl;
/*     */   }
/*     */ 
/*     */   public Integer getDly_center_id() {
/* 353 */     return this.dly_center_id;
/*     */   }
/*     */ 
/*     */   public void setDly_center_id(Integer dlyCenterId) {
/* 357 */     this.dly_center_id = dlyCenterId;
/*     */   }
/*     */ 
/*     */   public DlyCenter getDlyCenter() {
/* 361 */     return this.dlyCenter;
/*     */   }
/*     */ 
/*     */   public void setDlyCenter(DlyCenter dlyCenter) {
/* 365 */     this.dlyCenter = dlyCenter;
/*     */   }
/*     */ 
/*     */   public Integer getPrt_tmpl_id() {
/* 369 */     return this.prt_tmpl_id;
/*     */   }
/*     */ 
/*     */   public void setPrt_tmpl_id(Integer prtTmplId) {
/* 373 */     this.prt_tmpl_id = prtTmplId;
/*     */   }
/*     */ 
/*     */   public String getSaveAddr() {
/* 377 */     return this.saveAddr;
/*     */   }
/*     */ 
/*     */   public void setSaveAddr(String saveAddr) {
/* 381 */     this.saveAddr = saveAddr;
/*     */   }
/*     */ 
/*     */   public EopSite getSite() {
/* 385 */     return this.site;
/*     */   }
/*     */ 
/*     */   public void setSite(EopSite site) {
/* 389 */     this.site = site;
/*     */   }
/*     */ 
/*     */   public EopUser getUser() {
/* 393 */     return this.user;
/*     */   }
/*     */ 
/*     */   public void setUser(EopUser user) {
/* 397 */     this.user = user;
/*     */   }
/*     */ 
/*     */   public IUserManager getUserManager() {
/* 401 */     return this.userManager;
/*     */   }
/*     */ 
/*     */   public void setUserManager(IUserManager userManager) {
/* 405 */     this.userManager = userManager;
/*     */   }
/*     */ 
/*     */   public String getPrintXml() {
/* 409 */     return this.printXml;
/*     */   }
/*     */ 
/*     */   public void setPrintXml(String printXml) {
/* 413 */     this.printXml = printXml;
/*     */   }
/*     */ 
/*     */   public String getPrintData() {
/* 417 */     return this.printData;
/*     */   }
/*     */ 
/*     */   public void setPrintData(String printData) {
/* 421 */     this.printData = printData;
/*     */   }
/*     */ 
/*     */   public String getPrintBg() {
/* 425 */     return this.printBg;
/*     */   }
/*     */ 
/*     */   public void setPrintBg(String printBg) {
/* 429 */     this.printBg = printBg;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.action.backend.OrderPrintAction
 * JD-Core Version:    0.6.0
 */