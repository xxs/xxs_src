/*     */ package com.enation.app.shop.core.action.backend;
/*     */ 
/*     */ import com.enation.app.base.core.service.ISettingService;
/*     */ import com.enation.app.shop.core.model.Goods;
/*     */ import com.enation.app.shop.core.model.GoodsStores;
/*     */ import com.enation.app.shop.core.service.IDepotManager;
/*     */ import com.enation.app.shop.core.service.IDepotMonitorManager;
/*     */ import com.enation.app.shop.core.service.IGoodsManager;
/*     */ import com.enation.framework.action.WWAction;
/*     */ import com.enation.framework.util.DateUtil;
/*     */ import com.enation.framework.util.StringUtil;
/*     */ import java.io.PrintStream;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ 
/*     */ public class DepotMonitorAction extends WWAction
/*     */ {
/*     */   private IDepotMonitorManager depotMonitorManager;
/*     */   private List listTask;
/*     */   private int payTotal;
/*     */   private int pildTotal;
/*     */   private List<GoodsStores> storeList;
/*     */   private String name;
/*     */   private String sn;
/*     */   private Integer catid;
/*     */   private IGoodsManager goodsManager;
/*     */   private Goods goods;
/*     */   private int goodsid;
/*     */   private int totalCount;
/*     */   private String startDate;
/*     */   private String endDate;
/*     */   private int depotid;
/*  44 */   private int depotType = -1;
/*  45 */   private int opType = -1;
/*     */   private IDepotManager depotManager;
/*     */   private List list;
/*     */   private ISettingService settingService;
/*     */ 
/*     */   public String listTask()
/*     */   {
/*  56 */     this.listTask = this.depotMonitorManager.getTaskList();
/*  57 */     return "listtask";
/*     */   }
/*     */ 
/*     */   public String listAllocation()
/*     */   {
/*  64 */     this.listTask = this.depotMonitorManager.getAllocationList();
/*  65 */     return "listallocation";
/*     */   }
/*     */ 
/*     */   public String listSend()
/*     */   {
/*  72 */     this.listTask = this.depotMonitorManager.getSendList();
/*  73 */     return "listsend";
/*     */   }
/*     */ 
/*     */   public String listOrder()
/*     */   {
/*  80 */     this.payTotal = this.depotMonitorManager.getTotalByStatus(1);
/*  81 */     this.pildTotal = this.depotMonitorManager.getTotalByStatus(2);
/*  82 */     return "listorder";
/*     */   }
/*     */ 
/*     */   public String listStock()
/*     */   {
/*  89 */     if (this.name != null) this.name = StringUtil.toUTF8(this.name);
/*  90 */     this.webpage = this.goodsManager.searchGoods(null, null, this.catid, this.name, this.sn, null, null, null, getPage(), getPageSize());
/*  91 */     return "liststock";
/*     */   }
/*     */ 
/*     */   public String depotStock()
/*     */   {
/*  98 */     this.goods = this.goodsManager.getGoods(Integer.valueOf(this.goodsid));
/*  99 */     this.listTask = this.depotMonitorManager.depotidDepotByGoodsid(this.goodsid, this.goods.getCat_id().intValue());
/* 100 */     return "depotstock";
/*     */   }
/*     */ 
/*     */   public String storeWarn()
/*     */   {
/* 107 */     String value = this.settingService.getSetting("shop", "goods_alerm_num");
/* 108 */     int num = 10;
/* 109 */     if (!StringUtil.isEmpty(value)) {
/* 110 */       num = StringUtil.toInt(value, true);
/*     */     }
/* 112 */     this.storeList = this.goodsManager.storeWarnGoods(num, getPage(), getPageSize());
/* 113 */     this.totalCount = this.storeList.size();
/* 114 */     return "storewarn";
/*     */   }
/*     */ 
/*     */   public String listSell()
/*     */   {
/* 121 */     int start = 0; int end = 0;
/* 122 */     if ((this.startDate != null) && (!"".equals(this.startDate)) && (this.endDate != null) && (!"".equals(this.endDate))) {
/* 123 */       start = DateUtil.getDateline(this.startDate);
/* 124 */       end = DateUtil.getDateline(this.endDate) + 86400;
/*     */     }
/*     */ 
/* 127 */     this.listTask = this.depotMonitorManager.searchOrderSalesAmout(start, end);
/* 128 */     return "listsell";
/*     */   }
/*     */ 
/*     */   public String listSellNum()
/*     */   {
/* 135 */     if (this.catid == null)
/* 136 */       this.catid = Integer.valueOf(0);
/* 137 */     int start = 0; int end = 0;
/* 138 */     if ((this.startDate != null) && (!"".equals(this.startDate)) && (this.endDate != null) && (!"".equals(this.endDate))) {
/* 139 */       start = DateUtil.getDateline(this.startDate);
/* 140 */       end = DateUtil.getDateline(this.endDate) + 86400;
/*     */     }
/*     */ 
/* 143 */     this.listTask = this.depotMonitorManager.searchOrderSaleNumber(start, end, this.catid.intValue());
/* 144 */     return "listsellnum";
/*     */   }
/*     */ 
/*     */   public String listStockLog()
/*     */   {
/* 151 */     this.list = this.depotManager.list();
/* 152 */     int start = 0; int end = 0;
/* 153 */     if ((this.startDate != null) && (!"".equals(this.startDate)) && (this.endDate != null) && (!"".equals(this.endDate))) {
/* 154 */       start = DateUtil.getDateline(this.startDate);
/* 155 */       end = DateUtil.getDateline(this.endDate) + 86400;
/*     */     }
/* 157 */     this.webpage = this.depotMonitorManager.searchStoreLog(start, end, this.depotid, this.depotType, this.opType, getPage(), getPageSize());
/* 158 */     return "liststocklog";
/*     */   }
/*     */ 
/*     */   public IDepotMonitorManager getDepotMonitorManager() {
/* 162 */     return this.depotMonitorManager;
/*     */   }
/*     */   public void setDepotMonitorManager(IDepotMonitorManager depotMonitorManager) {
/* 165 */     this.depotMonitorManager = depotMonitorManager;
/*     */   }
/*     */ 
/*     */   public List getListTask() {
/* 169 */     return this.listTask;
/*     */   }
/*     */ 
/*     */   public void setListTask(List listTask) {
/* 173 */     this.listTask = listTask;
/*     */   }
/*     */   public int getPayTotal() {
/* 176 */     return this.payTotal;
/*     */   }
/*     */   public void setPayTotal(int payTotal) {
/* 179 */     this.payTotal = payTotal;
/*     */   }
/*     */   public int getPildTotal() {
/* 182 */     return this.pildTotal;
/*     */   }
/*     */   public void setPildTotal(int pildTotal) {
/* 185 */     this.pildTotal = pildTotal;
/*     */   }
/*     */   public String getName() {
/* 188 */     return this.name;
/*     */   }
/*     */   public void setName(String name) {
/* 191 */     this.name = name;
/*     */   }
/*     */   public String getSn() {
/* 194 */     return this.sn;
/*     */   }
/*     */   public void setSn(String sn) {
/* 197 */     this.sn = sn;
/*     */   }
/*     */   public Integer getCatid() {
/* 200 */     return this.catid;
/*     */   }
/*     */   public void setCatid(Integer catid) {
/* 203 */     this.catid = catid;
/*     */   }
/*     */   public IGoodsManager getGoodsManager() {
/* 206 */     return this.goodsManager;
/*     */   }
/*     */   public void setGoodsManager(IGoodsManager goodsManager) {
/* 209 */     this.goodsManager = goodsManager;
/*     */   }
/*     */   public Goods getGoods() {
/* 212 */     return this.goods;
/*     */   }
/*     */   public void setGoods(Goods goods) {
/* 215 */     this.goods = goods;
/*     */   }
/*     */   public int getGoodsid() {
/* 218 */     return this.goodsid;
/*     */   }
/*     */   public void setGoodsid(int goodsid) {
/* 221 */     this.goodsid = goodsid;
/*     */   }
/*     */ 
/*     */   public static void main(String[] args) {
/* 225 */     Date d1 = DateUtil.toDate("2011-10-01 00:10", "yyyy-MM-dd HH:mm");
/* 226 */     Date d1_1 = DateUtil.toDate("2011-10-01 23:01", "yyyy-MM-dd HH:mm");
/* 227 */     Date d2 = DateUtil.toDate("2011-10-02 00:01", "yyyy-MM-dd HH:mm");
/* 228 */     Date d2_1 = DateUtil.toDate("2011-10-02 19:01", "yyyy-MM-dd HH:mm");
/* 229 */     int t = 86400;
/* 230 */     System.out.println(d1.getTime() / 1000L / t + "---" + d1_1.getTime() / 1000L / t);
/* 231 */     System.out.println(d2.getTime() / 1000L / t + "---" + d2_1.getTime() / 1000L / t);
/*     */ 
/* 233 */     t = 86400000;
/* 234 */     System.out.println(d1.getTime() / t + "---" + d1_1.getTime() / t);
/* 235 */     System.out.println(d2.getTime() / t + "---" + d2_1.getTime() / t);
/*     */   }
/*     */   public String getStartDate() {
/* 238 */     return this.startDate;
/*     */   }
/*     */   public void setStartDate(String startDate) {
/* 241 */     this.startDate = startDate;
/*     */   }
/*     */   public String getEndDate() {
/* 244 */     return this.endDate;
/*     */   }
/*     */   public void setEndDate(String endDate) {
/* 247 */     this.endDate = endDate;
/*     */   }
/*     */   public int getDepotid() {
/* 250 */     return this.depotid;
/*     */   }
/*     */   public void setDepotid(int depotid) {
/* 253 */     this.depotid = depotid;
/*     */   }
/*     */   public int getDepotType() {
/* 256 */     return this.depotType;
/*     */   }
/*     */   public void setDepotType(int depotType) {
/* 259 */     this.depotType = depotType;
/*     */   }
/*     */   public int getOpType() {
/* 262 */     return this.opType;
/*     */   }
/*     */   public void setOpType(int opType) {
/* 265 */     this.opType = opType;
/*     */   }
/*     */   public IDepotManager getDepotManager() {
/* 268 */     return this.depotManager;
/*     */   }
/*     */   public void setDepotManager(IDepotManager depotManager) {
/* 271 */     this.depotManager = depotManager;
/*     */   }
/*     */   public List getList() {
/* 274 */     return this.list;
/*     */   }
/*     */   public void setList(List list) {
/* 277 */     this.list = list;
/*     */   }
/*     */   public ISettingService getSettingService() {
/* 280 */     return this.settingService;
/*     */   }
/*     */   public void setSettingService(ISettingService settingService) {
/* 283 */     this.settingService = settingService;
/*     */   }
/*     */   public List<GoodsStores> getStoreList() {
/* 286 */     return this.storeList;
/*     */   }
/*     */   public void setStoreList(List<GoodsStores> storeList) {
/* 289 */     this.storeList = storeList;
/*     */   }
/*     */   public int getTotalCount() {
/* 292 */     return this.totalCount;
/*     */   }
/*     */   public void setTotalCount(int totalCount) {
/* 295 */     this.totalCount = totalCount;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.action.backend.DepotMonitorAction
 * JD-Core Version:    0.6.0
 */