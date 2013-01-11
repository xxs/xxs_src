/*     */ package com.enation.app.shop.core.action.backend;
/*     */ 
/*     */ import com.enation.app.base.core.service.IRegionsManager;
/*     */ import com.enation.app.shop.core.model.Allocation;
/*     */ import com.enation.app.shop.core.model.AllocationItem;
/*     */ import com.enation.app.shop.core.model.Delivery;
/*     */ import com.enation.app.shop.core.model.DeliveryItem;
/*     */ import com.enation.app.shop.core.model.Order;
/*     */ import com.enation.app.shop.core.service.IDepotManager;
/*     */ import com.enation.app.shop.core.service.IDlyTypeManager;
/*     */ import com.enation.app.shop.core.service.IGoodsStoreManager;
/*     */ import com.enation.app.shop.core.service.ILogiManager;
/*     */ import com.enation.app.shop.core.service.IOrderFlowManager;
/*     */ import com.enation.app.shop.core.service.IOrderManager;
/*     */ import com.enation.framework.action.WWAction;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class DeliveryAction extends WWAction
/*     */ {
/*     */   private IDepotManager depotManager;
/*     */   private ILogiManager logiManager;
/*     */   private IDlyTypeManager dlyTypeManager;
/*     */   private IOrderManager orderManager;
/*     */   private IOrderFlowManager orderFlowManager;
/*     */   private IRegionsManager regionsManager;
/*     */   private IGoodsStoreManager goodsStoreManager;
/*     */   private Integer orderId;
/*     */   private Order ord;
/*     */   private List logiList;
/*     */   private List dlyTypeList;
/*     */   private List itemList;
/*     */   private List giftList;
/*     */   private List depotList;
/*     */   private String allocationHtml;
/*     */   private Integer productid;
/*     */   private Integer depotid;
/*     */   private Delivery delivery;
/*     */   private String[] goods_nameArray;
/*     */   private String[] goods_snArray;
/*     */   private Integer[] goods_idArray;
/*     */   private Integer[] product_idArray;
/*     */   private Integer[] numArray;
/*     */   private Integer[] depot_idArray;
/*     */   private Integer[] cat_idArray;
/*     */   private Integer[] item_idArray;
/*     */   private int province_id;
/*     */   private int city_id;
/*     */   private int region_id;
/*     */   private String province;
/*     */   private String city;
/*     */   private String region;
/*     */   private int itemid;
/*     */   private int id;
/*     */ 
/*     */   public String showAllocationDialog()
/*     */   {
/*  85 */     this.ord = this.orderManager.get(this.orderId);
/*  86 */     this.itemList = this.orderManager.listGoodsItems(this.orderId);
/*  87 */     this.depotList = this.depotManager.list();
/*  88 */     return "allocation_dialog";
/*     */   }
/*     */ 
/*     */   public String getProductStore()
/*     */   {
/*  96 */     this.allocationHtml = this.orderFlowManager.getAllocationHtml(this.itemid);
/*  97 */     return "product_store";
/*     */   }
/*     */ 
/*     */   public String viewProductStore()
/*     */   {
/* 105 */     this.allocationHtml = this.orderFlowManager.getAllocationViewHtml(this.itemid);
/* 106 */     return "view_product_store";
/*     */   }
/*     */ 
/*     */   public String allocation()
/*     */   {
/*     */     try
/*     */     {
/* 117 */       Allocation allocation = new Allocation();
/* 118 */       allocation.setOrderid(this.orderId.intValue());
/* 119 */       allocation.setShipDepotId(this.depotid.intValue());
/*     */ 
/* 121 */       List aitemList = new ArrayList();
/* 122 */       int i = 0;
/* 123 */       for (Integer goods_id : this.goods_idArray)
/*     */       {
/* 125 */         AllocationItem item = new AllocationItem();
/* 126 */         item.setDepotid(this.depot_idArray[i].intValue());
/* 127 */         item.setGoodsid(goods_id.intValue());
/* 128 */         item.setNum(this.numArray[i].intValue());
/* 129 */         item.setProductid(this.product_idArray[i].intValue());
/* 130 */         item.setCat_id(this.cat_idArray[i].intValue());
/* 131 */         item.setItemid(this.item_idArray[i].intValue());
/* 132 */         aitemList.add(item);
/* 133 */         i++;
/*     */       }
/*     */ 
/* 136 */       allocation.setItemList(aitemList);
/* 137 */       this.orderFlowManager.allocation(allocation);
/* 138 */       Order order = this.orderManager.get(this.orderId);
/* 139 */       this.json = ("{result:1,message:'订单[" + order.getSn() + "]配货成功',orderStatus:" + order.getStatus() + ",payStatus:" + order.getPay_status() + ",shipStatus:" + order.getShip_status() + "}");
/*     */     } catch (RuntimeException e) {
/* 141 */       this.logger.error("配货失败", e);
/* 142 */       showErrorJson("配货失败，错误信息：" + e.getMessage());
/*     */     }
/*     */ 
/* 145 */     return "json_message";
/*     */   }
/*     */ 
/*     */   public String allocationFinish()
/*     */   {
/*     */     try
/*     */     {
/* 154 */       this.orderFlowManager.updateAllocation(this.id, this.orderId.intValue());
/* 155 */       this.json = "{result:1,message:'确认配货成功'}";
/*     */     } catch (RuntimeException e) {
/* 157 */       if (this.logger.isDebugEnabled()) {
/* 158 */         this.logger.debug(e);
/*     */       }
/* 160 */       this.json = ("{result:0,message:\"确认配货失败：" + e.getMessage() + "\"}");
/*     */     }
/*     */ 
/* 163 */     return "json_message";
/*     */   }
/*     */ 
/*     */   public String showShipDialog()
/*     */   {
/* 171 */     fillShipData();
/*     */ 
/* 173 */     this.itemList = this.orderFlowManager.listNotShipGoodsItem(this.orderId);
/*     */ 
/* 177 */     return "ship_dialog";
/*     */   }
/*     */ 
/*     */   public String showReturnDialog()
/*     */   {
/* 187 */     fillShipData();
/*     */ 
/* 189 */     this.itemList = this.orderFlowManager.listShipGoodsItem(this.orderId);
/*     */ 
/* 191 */     return "return_dialog";
/*     */   }
/*     */ 
/*     */   public String showChangeDialog()
/*     */   {
/* 200 */     fillShipData();
/*     */ 
/* 202 */     this.itemList = this.orderFlowManager.listShipGoodsItem(this.orderId);
/*     */ 
/* 205 */     return "change_dialog";
/*     */   }
/*     */ 
/*     */   private void fillShipData()
/*     */   {
/* 211 */     this.logiList = this.logiManager.list();
/* 212 */     this.dlyTypeList = this.dlyTypeManager.list();
/* 213 */     this.ord = this.orderManager.get(this.orderId);
/*     */   }
/*     */ 
/*     */   public String ship()
/*     */   {
/*     */     try
/*     */     {
/* 227 */       this.delivery.setProvince(this.province);
/* 228 */       this.delivery.setCity(this.city);
/* 229 */       this.delivery.setRegion(this.region);
/*     */ 
/* 231 */       this.delivery.setProvince_id(this.province_id);
/* 232 */       this.delivery.setCity_id(this.city_id);
/* 233 */       this.delivery.setRegion_id(this.region_id);
/*     */ 
/* 235 */       List itemList = new ArrayList();
/* 236 */       int i = 0;
/* 237 */       for (Integer goods_id : this.goods_idArray)
/*     */       {
/* 239 */         DeliveryItem item = new DeliveryItem();
/* 240 */         item.setGoods_id(goods_id);
/* 241 */         item.setName(this.goods_nameArray[i]);
/* 242 */         item.setNum(this.numArray[i]);
/* 243 */         item.setProduct_id(this.product_idArray[i]);
/* 244 */         item.setSn(this.goods_snArray[i]);
/* 245 */         item.setOrder_itemid(this.item_idArray[i].intValue());
/* 246 */         item.setItemtype(Integer.valueOf(0));
/* 247 */         itemList.add(item);
/* 248 */         i++;
/*     */       }
/*     */ 
/* 251 */       this.delivery.setOrder_id(this.orderId);
/* 252 */       this.orderFlowManager.shipping(this.delivery, itemList);
/* 253 */       Order order = this.orderManager.get(this.orderId);
/* 254 */       this.json = ("{result:1,message:'订单[" + order.getSn() + "]发货成功',orderStatus:" + order.getStatus() + ",payStatus:" + order.getPay_status() + ",shipStatus:" + order.getShip_status() + "}");
/*     */     } catch (RuntimeException e) {
/* 256 */       if (this.logger.isDebugEnabled()) {
/* 257 */         this.logger.debug(e.getStackTrace());
/* 258 */         this.json = ("{result:0,message:\"发货失败：" + e.getLocalizedMessage() + "\"}");
/*     */       }
/*     */     }
/*     */ 
/* 262 */     return "json_message";
/*     */   }
/*     */ 
/*     */   public String returned()
/*     */   {
/*     */     try
/*     */     {
/* 271 */       List itemList = new ArrayList();
/* 272 */       int i = 0;
/* 273 */       for (Integer goods_id : this.goods_idArray)
/*     */       {
/* 275 */         DeliveryItem item = new DeliveryItem();
/* 276 */         item.setGoods_id(goods_id);
/* 277 */         item.setName(this.goods_nameArray[i]);
/* 278 */         item.setNum(this.numArray[i]);
/* 279 */         item.setProduct_id(this.product_idArray[i]);
/* 280 */         item.setSn(this.goods_snArray[i]);
/* 281 */         itemList.add(item);
/* 282 */         i++;
/*     */       }
/*     */ 
/* 285 */       i = 0;
/* 286 */       List giftitemList = new ArrayList();
/*     */ 
/* 288 */       this.delivery.setProvince(this.province);
/* 289 */       this.delivery.setCity(this.city);
/* 290 */       this.delivery.setRegion(this.region);
/*     */ 
/* 292 */       this.delivery.setProvince_id(this.province_id);
/* 293 */       this.delivery.setCity_id(this.city_id);
/* 294 */       this.delivery.setRegion_id(this.region_id);
/*     */ 
/* 296 */       this.delivery.setOrder_id(this.orderId);
/* 297 */       this.orderFlowManager.returned(this.delivery, itemList, giftitemList);
/* 298 */       Order order = this.orderManager.get(this.orderId);
/* 299 */       this.json = ("{result:1,message:'订单[" + order.getSn() + "]退货成功',shipStatus:" + order.getShip_status() + "}");
/*     */     } catch (RuntimeException e) {
/* 301 */       if (this.logger.isDebugEnabled()) {
/* 302 */         this.logger.debug(e.getStackTrace());
/* 303 */         this.json = ("{result:0,message:\"退货失败：" + e.getLocalizedMessage() + "\"}");
/*     */       }
/*     */     }
/*     */ 
/* 307 */     return "json_message";
/*     */   }
/*     */ 
/*     */   public String change()
/*     */   {
/*     */     try
/*     */     {
/* 318 */       List itemList = new ArrayList();
/* 319 */       int i = 0;
/* 320 */       for (Integer goods_id : this.goods_idArray)
/*     */       {
/* 322 */         DeliveryItem item = new DeliveryItem();
/* 323 */         item.setGoods_id(goods_id);
/* 324 */         item.setName(this.goods_nameArray[i]);
/* 325 */         item.setNum(this.numArray[i]);
/* 326 */         item.setProduct_id(this.product_idArray[i]);
/* 327 */         item.setSn(this.goods_snArray[i]);
/* 328 */         itemList.add(item);
/* 329 */         i++;
/*     */       }
/*     */ 
/* 332 */       this.delivery.setOrder_id(this.orderId);
/* 333 */       this.orderFlowManager.change(this.delivery, itemList, null);
/* 334 */       Order order = this.orderManager.get(this.orderId);
/* 335 */       this.json = ("{result:1,message:'订单[" + order.getSn() + "]换货成功',shipStatus:" + order.getShip_status() + "}");
/*     */     }
/*     */     catch (RuntimeException e)
/*     */     {
/* 339 */       this.logger.error(e.getMessage(), e);
/*     */ 
/* 341 */       this.json = ("{result:0,message:\"换货失败：" + e.getLocalizedMessage() + "\"}");
/*     */     }
/*     */ 
/* 346 */     return "json_message";
/*     */   }
/*     */ 
/*     */   public ILogiManager getLogiManager() {
/* 350 */     return this.logiManager;
/*     */   }
/*     */ 
/*     */   public void setLogiManager(ILogiManager logiManager)
/*     */   {
/* 356 */     this.logiManager = logiManager;
/*     */   }
/*     */ 
/*     */   public IDlyTypeManager getDlyTypeManager()
/*     */   {
/* 362 */     return this.dlyTypeManager;
/*     */   }
/*     */ 
/*     */   public void setDlyTypeManager(IDlyTypeManager dlyTypeManager)
/*     */   {
/* 368 */     this.dlyTypeManager = dlyTypeManager;
/*     */   }
/*     */ 
/*     */   public List getLogiList()
/*     */   {
/* 374 */     return this.logiList;
/*     */   }
/*     */ 
/*     */   public void setLogiList(List logiList)
/*     */   {
/* 380 */     this.logiList = logiList;
/*     */   }
/*     */ 
/*     */   public List getDlyTypeList()
/*     */   {
/* 386 */     return this.dlyTypeList;
/*     */   }
/*     */ 
/*     */   public void setDlyTypeList(List dlyTypeList)
/*     */   {
/* 392 */     this.dlyTypeList = dlyTypeList;
/*     */   }
/*     */ 
/*     */   public IOrderManager getOrderManager()
/*     */   {
/* 398 */     return this.orderManager;
/*     */   }
/*     */ 
/*     */   public void setOrderManager(IOrderManager orderManager)
/*     */   {
/* 404 */     this.orderManager = orderManager;
/*     */   }
/*     */ 
/*     */   public Integer getOrderId()
/*     */   {
/* 410 */     return this.orderId;
/*     */   }
/*     */ 
/*     */   public void setOrderId(Integer orderId)
/*     */   {
/* 416 */     this.orderId = orderId;
/*     */   }
/*     */ 
/*     */   public Order getOrd()
/*     */   {
/* 422 */     return this.ord;
/*     */   }
/*     */ 
/*     */   public void setOrd(Order ord)
/*     */   {
/* 428 */     this.ord = ord;
/*     */   }
/*     */ 
/*     */   public IOrderFlowManager getOrderFlowManager()
/*     */   {
/* 434 */     return this.orderFlowManager;
/*     */   }
/*     */ 
/*     */   public void setOrderFlowManager(IOrderFlowManager orderFlowManager)
/*     */   {
/* 440 */     this.orderFlowManager = orderFlowManager;
/*     */   }
/*     */ 
/*     */   public List getItemList()
/*     */   {
/* 446 */     return this.itemList;
/*     */   }
/*     */ 
/*     */   public void setItemList(List itemList)
/*     */   {
/* 452 */     this.itemList = itemList;
/*     */   }
/*     */ 
/*     */   public Delivery getDelivery()
/*     */   {
/* 458 */     return this.delivery;
/*     */   }
/*     */ 
/*     */   public void setDelivery(Delivery delivery)
/*     */   {
/* 464 */     this.delivery = delivery;
/*     */   }
/*     */ 
/*     */   public String[] getGoods_nameArray()
/*     */   {
/* 470 */     return this.goods_nameArray;
/*     */   }
/*     */ 
/*     */   public void setGoods_nameArray(String[] goodsNameArray)
/*     */   {
/* 476 */     this.goods_nameArray = goodsNameArray;
/*     */   }
/*     */ 
/*     */   public Integer[] getGoods_idArray()
/*     */   {
/* 482 */     return this.goods_idArray;
/*     */   }
/*     */ 
/*     */   public void setGoods_idArray(Integer[] goodsIdArray)
/*     */   {
/* 488 */     this.goods_idArray = goodsIdArray;
/*     */   }
/*     */ 
/*     */   public Integer[] getProduct_idArray()
/*     */   {
/* 494 */     return this.product_idArray;
/*     */   }
/*     */ 
/*     */   public void setProduct_idArray(Integer[] productIdArray)
/*     */   {
/* 500 */     this.product_idArray = productIdArray;
/*     */   }
/*     */ 
/*     */   public Integer[] getNumArray()
/*     */   {
/* 506 */     return this.numArray;
/*     */   }
/*     */ 
/*     */   public void setNumArray(Integer[] numArray)
/*     */   {
/* 512 */     this.numArray = numArray;
/*     */   }
/*     */ 
/*     */   public List getGiftList()
/*     */   {
/* 518 */     return this.giftList;
/*     */   }
/*     */ 
/*     */   public void setGiftList(List giftList)
/*     */   {
/* 524 */     this.giftList = giftList;
/*     */   }
/*     */ 
/*     */   public String[] getGoods_snArray()
/*     */   {
/* 529 */     return this.goods_snArray;
/*     */   }
/*     */ 
/*     */   public void setGoods_snArray(String[] goodsSnArray)
/*     */   {
/* 535 */     this.goods_snArray = goodsSnArray;
/*     */   }
/*     */   public IDepotManager getDepotManager() {
/* 538 */     return this.depotManager;
/*     */   }
/*     */   public void setDepotManager(IDepotManager depotManager) {
/* 541 */     this.depotManager = depotManager;
/*     */   }
/*     */   public List getDepotList() {
/* 544 */     return this.depotList;
/*     */   }
/*     */   public void setDepotList(List depotList) {
/* 547 */     this.depotList = depotList;
/*     */   }
/*     */ 
/*     */   public IRegionsManager getRegionsManager() {
/* 551 */     return this.regionsManager;
/*     */   }
/*     */   public void setRegionsManager(IRegionsManager regionsManager) {
/* 554 */     this.regionsManager = regionsManager;
/*     */   }
/*     */ 
/*     */   public Integer getProductid() {
/* 558 */     return this.productid;
/*     */   }
/*     */ 
/*     */   public void setProductid(Integer productid) {
/* 562 */     this.productid = productid;
/*     */   }
/*     */ 
/*     */   public IGoodsStoreManager getGoodsStoreManager() {
/* 566 */     return this.goodsStoreManager;
/*     */   }
/*     */ 
/*     */   public void setGoodsStoreManager(IGoodsStoreManager goodsStoreManager) {
/* 570 */     this.goodsStoreManager = goodsStoreManager;
/*     */   }
/*     */ 
/*     */   public Integer getDepotid() {
/* 574 */     return this.depotid;
/*     */   }
/*     */ 
/*     */   public void setDepotid(Integer depotid)
/*     */   {
/* 579 */     this.depotid = depotid;
/*     */   }
/*     */ 
/*     */   public Integer[] getDepot_idArray() {
/* 583 */     return this.depot_idArray;
/*     */   }
/*     */ 
/*     */   public void setDepot_idArray(Integer[] depot_idArray) {
/* 587 */     this.depot_idArray = depot_idArray;
/*     */   }
/*     */ 
/*     */   public int getItemid() {
/* 591 */     return this.itemid;
/*     */   }
/*     */ 
/*     */   public void setItemid(int itemid) {
/* 595 */     this.itemid = itemid;
/*     */   }
/*     */ 
/*     */   public Integer[] getCat_idArray() {
/* 599 */     return this.cat_idArray;
/*     */   }
/*     */ 
/*     */   public void setCat_idArray(Integer[] cat_idArray) {
/* 603 */     this.cat_idArray = cat_idArray;
/*     */   }
/*     */ 
/*     */   public String getAllocationHtml()
/*     */   {
/* 608 */     return this.allocationHtml;
/*     */   }
/*     */ 
/*     */   public void setAllocationHtml(String allocationHtml) {
/* 612 */     this.allocationHtml = allocationHtml;
/*     */   }
/*     */ 
/*     */   public Integer[] getItem_idArray() {
/* 616 */     return this.item_idArray;
/*     */   }
/*     */ 
/*     */   public void setItem_idArray(Integer[] item_idArray) {
/* 620 */     this.item_idArray = item_idArray;
/*     */   }
/*     */ 
/*     */   public int getId() {
/* 624 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(int id) {
/* 628 */     this.id = id;
/*     */   }
/*     */ 
/*     */   public int getProvince_id() {
/* 632 */     return this.province_id;
/*     */   }
/*     */ 
/*     */   public void setProvince_id(int province_id) {
/* 636 */     this.province_id = province_id;
/*     */   }
/*     */ 
/*     */   public int getCity_id() {
/* 640 */     return this.city_id;
/*     */   }
/*     */ 
/*     */   public void setCity_id(int city_id) {
/* 644 */     this.city_id = city_id;
/*     */   }
/*     */ 
/*     */   public int getRegion_id() {
/* 648 */     return this.region_id;
/*     */   }
/*     */ 
/*     */   public void setRegion_id(int region_id) {
/* 652 */     this.region_id = region_id;
/*     */   }
/*     */ 
/*     */   public String getProvince() {
/* 656 */     return this.province;
/*     */   }
/*     */ 
/*     */   public void setProvince(String province) {
/* 660 */     this.province = province;
/*     */   }
/*     */ 
/*     */   public String getCity() {
/* 664 */     return this.city;
/*     */   }
/*     */ 
/*     */   public void setCity(String city) {
/* 668 */     this.city = city;
/*     */   }
/*     */ 
/*     */   public String getRegion() {
/* 672 */     return this.region;
/*     */   }
/*     */ 
/*     */   public void setRegion(String region) {
/* 676 */     this.region = region;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.action.backend.DeliveryAction
 * JD-Core Version:    0.6.0
 */