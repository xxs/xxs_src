/*     */ package com.enation.app.shop.core.action.backend;
/*     */ 
/*     */ import com.enation.app.base.core.model.MemberLv;
/*     */ import com.enation.app.shop.core.model.Promotion;
/*     */ import com.enation.app.shop.core.plugin.promotion.IPromotionPlugin;
/*     */ import com.enation.app.shop.core.service.IMemberLvManager;
/*     */ import com.enation.app.shop.core.service.IPromotionManager;
/*     */ import com.enation.app.shop.core.service.promotion.IPromotionMethod;
/*     */ import com.enation.app.shop.core.service.promotion.PromotionConditions;
/*     */ import com.enation.framework.action.WWAction;
/*     */ import com.enation.framework.context.spring.SpringContextHolder;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class PromotionAction extends WWAction
/*     */ {
/*     */   private Integer activityid;
/*     */   private Integer pmtid;
/*     */   private IPromotionManager promotionManager;
/*     */   private IMemberLvManager memberLvManager;
/*     */   private List pmtList;
/*     */   private List pluginList;
/*     */   private List lvList;
/*     */   private List goodsList;
/*     */   private String pluginid;
/*     */   private PromotionConditions conditions;
/*     */   private String solutionHtml;
/*     */   private Double money_from;
/*     */   private Double money_to;
/*     */   private Date time_begin;
/*     */   private Date time_end;
/*     */   private Integer[] lvidArray;
/*     */   private Integer[] goodsidArray;
/*     */   private Integer[] pmtidArray;
/*     */   private int ifcoupon;
/*     */   private String describe;
/*     */   private Promotion promotion;
/*     */ 
/*     */   public String list()
/*     */   {
/*  58 */     this.pmtList = this.promotionManager.listByActivityId(this.activityid);
/*  59 */     return "list";
/*     */   }
/*     */ 
/*     */   public String select_plugin()
/*     */   {
/*  68 */     if (this.pmtid != null) {
/*  69 */       this.promotion = this.promotionManager.get(this.pmtid);
/*  70 */       this.pluginid = this.promotion.getPmts_id();
/*     */     }
/*  72 */     this.pluginList = this.promotionManager.listPmtPlugins();
/*  73 */     return "select_plugin";
/*     */   }
/*     */ 
/*     */   public String select_condition()
/*     */   {
/*     */     try
/*     */     {
/*  84 */       this.lvList = this.memberLvManager.list();
/*     */ 
/*  86 */       String solution = null;
/*  87 */       if (this.pmtid != null) {
/*  88 */         this.promotion = this.promotionManager.get(this.pmtid);
/*  89 */         this.time_begin = new Date(this.promotion.getPmt_time_begin().longValue());
/*  90 */         this.time_end = new Date(this.promotion.getPmt_time_end().longValue());
/*  91 */         solution = this.promotion.getPmt_solution();
/*  92 */         this.describe = this.promotion.getPmt_describe();
/*  93 */         this.ifcoupon = this.promotion.getPmt_ifcoupon();
/*  94 */         this.money_from = this.promotion.getOrder_money_from();
/*  95 */         this.money_to = this.promotion.getOrder_money_to();
/*     */ 
/*  98 */         List dbLvList = this.promotionManager.listMemberLvId(this.pmtid);
/*  99 */         int i = 0;
/*     */         MemberLv lv;
/*  99 */         for (int len = this.lvList.size(); i < len; i++) {
/* 100 */           lv = (MemberLv)this.lvList.get(i);
/* 101 */           for (Integer dbLvid : dbLvList) {
/* 102 */             if (lv.getLv_id().intValue() == dbLvid.intValue()) {
/* 103 */               lv.setSelected(true);
/*     */             }
/*     */           }
/*     */         }
/*     */ 
/* 108 */         this.goodsList = this.promotionManager.listGoodsId(this.pmtid);
/*     */       }
/*     */       else
/*     */       {
/* 112 */         this.time_begin = new Date();
/* 113 */         Calendar cal = Calendar.getInstance();
/* 114 */         cal.add(5, 10);
/* 115 */         this.time_end = cal.getTime();
/*     */       }
/* 117 */       IPromotionPlugin plugin = this.promotionManager.getPlugin(this.pluginid);
/* 118 */       this.conditions = new PromotionConditions(plugin.getConditions());
/* 119 */       String methodBeanName = plugin.getMethods();
/* 120 */       IPromotionMethod pmtMethod = (IPromotionMethod)SpringContextHolder.getBean(methodBeanName);
/* 121 */       this.solutionHtml = pmtMethod.getInputHtml(this.pmtid, solution);
/*     */ 
/* 123 */       return "select_condition";
/*     */     } catch (RuntimeException e) {
/* 125 */       this.logger.error(e);
/* 126 */       this.msgs.add(e.getMessage());
/* 127 */     }return "message";
/*     */   }
/*     */ 
/*     */   public String save()
/*     */   {
/*     */     try
/*     */     {
/* 143 */       IPromotionPlugin plugin = this.promotionManager.getPlugin(this.pluginid);
/* 144 */       this.conditions = new PromotionConditions(plugin.getConditions());
/*     */ 
/* 146 */       if (this.pmtid != null)
/* 147 */         this.promotion = this.promotionManager.get(this.pmtid);
/*     */       else {
/* 149 */         this.promotion = new Promotion();
/*     */       }
/*     */ 
/* 153 */       this.promotion.setOrder_money_from(this.money_from);
/* 154 */       this.promotion.setOrder_money_to(this.money_to);
/* 155 */       this.promotion.setPmt_time_begin(Long.valueOf(this.time_begin.getTime()));
/* 156 */       this.promotion.setPmt_time_end(Long.valueOf(this.time_end.getTime()));
/* 157 */       this.promotion.setPmt_ifcoupon(this.ifcoupon);
/* 158 */       this.promotion.setPmt_describe(this.describe);
/*     */ 
/* 162 */       if (this.activityid != null) {
/* 163 */         this.promotion.setPmta_id(this.activityid.intValue());
/* 164 */         this.promotion.setPmt_type(0);
/*     */       } else {
/* 166 */         this.promotion.setPmt_type(1);
/*     */       }
/*     */ 
/* 169 */       this.promotion.setPmts_id(this.pluginid);
/*     */ 
/* 172 */       if (this.pmtid != null) {
/* 173 */         this.promotionManager.edit(this.promotion, this.lvidArray, this.goodsidArray);
/*     */ 
/* 175 */         if (this.activityid != null)
/*     */         {
/* 181 */           this.msgs.add("促销规则修改成功");
/*     */         }
/*     */ 
/*     */       }
/*     */       else
/*     */       {
/* 193 */         this.pmtid = this.promotionManager.add(this.promotion, this.lvidArray, this.goodsidArray);
/* 194 */         this.msgs.add("促销规则添加成功");
/*     */       }
/*     */ 
/* 206 */       if (this.activityid == null)
/* 207 */         this.urls.put("优惠券列表", "coupon!list.do");
/*     */       else {
/* 209 */         this.urls.put("返回此活动促销规则列表", "promotion!list.do?activityid=" + this.promotion.getPmta_id());
/*     */       }
/*     */ 
/* 213 */       return "message";
/*     */     }
/*     */     catch (RuntimeException e)
/*     */     {
/* 217 */       e.printStackTrace();
/* 218 */       this.logger.error(e.getStackTrace());
/* 219 */       this.msgs.add(e.getMessage());
/* 220 */     }return "message";
/*     */   }
/*     */ 
/*     */   public String delete()
/*     */   {
/*     */     try
/*     */     {
/* 234 */       this.promotionManager.delete(this.pmtidArray);
/* 235 */       this.json = "{result:0,message:'删除成功'}";
/*     */     } catch (RuntimeException e) {
/* 237 */       this.json = ("{result:1,message:'" + e.getMessage() + "'}");
/*     */     }
/* 239 */     return "json_message";
/*     */   }
/*     */ 
/*     */   public Integer getActivityid() {
/* 243 */     return this.activityid;
/*     */   }
/*     */ 
/*     */   public void setActivityid(Integer activityid) {
/* 247 */     this.activityid = activityid;
/*     */   }
/*     */ 
/*     */   public IPromotionManager getPromotionManager()
/*     */   {
/* 253 */     return this.promotionManager;
/*     */   }
/*     */ 
/*     */   public void setPromotionManager(IPromotionManager promotionManager)
/*     */   {
/* 259 */     this.promotionManager = promotionManager;
/*     */   }
/*     */ 
/*     */   public List getPmtList()
/*     */   {
/* 265 */     return this.pmtList;
/*     */   }
/*     */ 
/*     */   public void setPmtList(List pmtList)
/*     */   {
/* 271 */     this.pmtList = pmtList;
/*     */   }
/*     */ 
/*     */   public List getPluginList()
/*     */   {
/* 276 */     return this.pluginList;
/*     */   }
/*     */ 
/*     */   public void setPluginList(List pluginList)
/*     */   {
/* 281 */     this.pluginList = pluginList;
/*     */   }
/*     */ 
/*     */   public String getPluginid()
/*     */   {
/* 286 */     return this.pluginid;
/*     */   }
/*     */ 
/*     */   public void setPluginid(String pluginid)
/*     */   {
/* 291 */     this.pluginid = pluginid;
/*     */   }
/*     */ 
/*     */   public PromotionConditions getConditions()
/*     */   {
/* 296 */     return this.conditions;
/*     */   }
/*     */ 
/*     */   public void setConditions(PromotionConditions conditions)
/*     */   {
/* 301 */     this.conditions = conditions;
/*     */   }
/*     */ 
/*     */   public String getSolutionHtml()
/*     */   {
/* 306 */     return this.solutionHtml;
/*     */   }
/*     */ 
/*     */   public void setSolutionHtml(String solutionHtml)
/*     */   {
/* 311 */     this.solutionHtml = solutionHtml;
/*     */   }
/*     */ 
/*     */   public Double getMoney_from()
/*     */   {
/* 316 */     return this.money_from;
/*     */   }
/*     */ 
/*     */   public void setMoney_from(Double moneyFrom)
/*     */   {
/* 321 */     this.money_from = moneyFrom;
/*     */   }
/*     */ 
/*     */   public Double getMoney_to()
/*     */   {
/* 326 */     return this.money_to;
/*     */   }
/*     */ 
/*     */   public void setMoney_to(Double moneyTo)
/*     */   {
/* 331 */     this.money_to = moneyTo;
/*     */   }
/*     */ 
/*     */   public Date getTime_begin()
/*     */   {
/* 336 */     return this.time_begin;
/*     */   }
/*     */ 
/*     */   public void setTime_begin(Date timeBegin)
/*     */   {
/* 341 */     this.time_begin = timeBegin;
/*     */   }
/*     */ 
/*     */   public Date getTime_end()
/*     */   {
/* 346 */     return this.time_end;
/*     */   }
/*     */ 
/*     */   public void setTime_end(Date timeEnd)
/*     */   {
/* 351 */     this.time_end = timeEnd;
/*     */   }
/*     */ 
/*     */   public Integer[] getLvidArray()
/*     */   {
/* 356 */     return this.lvidArray;
/*     */   }
/*     */ 
/*     */   public void setLvidArray(Integer[] lvidArray)
/*     */   {
/* 361 */     this.lvidArray = lvidArray;
/*     */   }
/*     */ 
/*     */   public Integer[] getGoodsidArray()
/*     */   {
/* 366 */     return this.goodsidArray;
/*     */   }
/*     */ 
/*     */   public void setGoodsidArray(Integer[] goodsidArray)
/*     */   {
/* 371 */     this.goodsidArray = goodsidArray;
/*     */   }
/*     */ 
/*     */   public Promotion getPromotion()
/*     */   {
/* 377 */     return this.promotion;
/*     */   }
/*     */ 
/*     */   public void setPromotion(Promotion promotion)
/*     */   {
/* 382 */     this.promotion = promotion;
/*     */   }
/*     */ 
/*     */   public int getIfcoupon()
/*     */   {
/* 387 */     return this.ifcoupon;
/*     */   }
/*     */ 
/*     */   public void setIfcoupon(int ifcoupon)
/*     */   {
/* 392 */     this.ifcoupon = ifcoupon;
/*     */   }
/*     */ 
/*     */   public String getDescribe()
/*     */   {
/* 397 */     return this.describe;
/*     */   }
/*     */ 
/*     */   public void setDescribe(String describe)
/*     */   {
/* 402 */     this.describe = describe;
/*     */   }
/*     */ 
/*     */   public IMemberLvManager getMemberLvManager()
/*     */   {
/* 407 */     return this.memberLvManager;
/*     */   }
/*     */ 
/*     */   public void setMemberLvManager(IMemberLvManager memberLvManager)
/*     */   {
/* 412 */     this.memberLvManager = memberLvManager;
/*     */   }
/*     */ 
/*     */   public List getLvList()
/*     */   {
/* 417 */     return this.lvList;
/*     */   }
/*     */ 
/*     */   public void setLvList(List lvList)
/*     */   {
/* 422 */     this.lvList = lvList;
/*     */   }
/*     */ 
/*     */   public Integer[] getPmtidArray()
/*     */   {
/* 427 */     return this.pmtidArray;
/*     */   }
/*     */ 
/*     */   public void setPmtidArray(Integer[] pmtidArray)
/*     */   {
/* 432 */     this.pmtidArray = pmtidArray;
/*     */   }
/*     */ 
/*     */   public Integer getPmtid()
/*     */   {
/* 437 */     return this.pmtid;
/*     */   }
/*     */ 
/*     */   public void setPmtid(Integer pmtid)
/*     */   {
/* 442 */     this.pmtid = pmtid;
/*     */   }
/*     */ 
/*     */   public List getGoodsList()
/*     */   {
/* 447 */     return this.goodsList;
/*     */   }
/*     */ 
/*     */   public void setGoodsList(List goodsList)
/*     */   {
/* 452 */     this.goodsList = goodsList;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.action.backend.PromotionAction
 * JD-Core Version:    0.6.0
 */