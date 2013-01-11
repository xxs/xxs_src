/*    */ package com.enation.app.shop.core.service.impl.promotion;
/*    */ 
/*    */ import com.enation.app.shop.core.model.FreeOffer;
/*    */ import com.enation.app.shop.core.model.Promotion;
/*    */ import com.enation.app.shop.core.service.IFreeOfferManager;
/*    */ import com.enation.app.shop.core.service.promotion.IGiveGiftBehavior;
/*    */ import com.enation.app.shop.core.service.promotion.IPromotionMethod;
/*    */ import com.enation.eop.processor.core.freemarker.FreeMarkerPaser;
/*    */ import com.enation.eop.sdk.database.BaseSupport;
/*    */ import com.enation.framework.context.webcontext.ThreadContextHolder;
/*    */ import com.enation.framework.database.IDaoSupport;
/*    */ import com.enation.framework.util.StringUtil;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import net.sf.json.JSONArray;
/*    */ import org.springframework.transaction.annotation.Propagation;
/*    */ import org.springframework.transaction.annotation.Transactional;
/*    */ 
/*    */ public class GiveGiftMethod extends BaseSupport<FreeOffer>
/*    */   implements IPromotionMethod, IGiveGiftBehavior
/*    */ {
/*    */   private IFreeOfferManager freeOfferManager;
/*    */ 
/*    */   public String getInputHtml(Integer pmtid, String solution)
/*    */   {
/* 33 */     FreeMarkerPaser freeMarkerPaser = FreeMarkerPaser.getInstance();
/* 34 */     freeMarkerPaser.setClz(getClass());
/* 35 */     if (solution != null) {
/* 36 */       Object[] giftIdArray = JSONArray.fromObject(solution).toArray();
/* 37 */       if (giftIdArray != null) {
/* 38 */         Integer[] giftIds = new Integer[giftIdArray.length];
/* 39 */         int i = 0;
/* 40 */         for (Object giftId : giftIdArray) {
/* 41 */           giftIds[i] = Integer.valueOf(giftId.toString());
/* 42 */           i++;
/*    */         }
/* 44 */         List giftList = this.freeOfferManager.list(giftIds);
/* 45 */         freeMarkerPaser.putData("giftList", giftList);
/*    */       }
/*    */ 
/*    */     }
/*    */ 
/* 50 */     return freeMarkerPaser.proessPageContent();
/*    */   }
/*    */ 
/*    */   public String getName()
/*    */   {
/* 56 */     return "giveGift";
/*    */   }
/*    */ 
/*    */   public String onPromotionSave(Integer pmtid)
/*    */   {
/* 61 */     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
/* 62 */     String[] giveGift = request.getParameterValues("giftidArray");
/* 63 */     if (giveGift == null) throw new IllegalArgumentException("失败，请添加赠品！");
/* 64 */     return JSONArray.fromObject(giveGift).toString();
/*    */   }
/*    */ 
/*    */   @Transactional(propagation=Propagation.REQUIRED)
/*    */   public void giveGift(Promotion promotion, Integer orderId) {
/* 70 */     List giftList = getGiftList(promotion);
/*    */ 
/* 72 */     for (Map gift : giftList)
/* 73 */       this.baseDaoSupport.execute("insert into order_gift(order_id,gift_id,gift_name,point,num,shipnum,getmethod)values(?,?,?,?,?,?,?)", new Object[] { orderId, gift.get("fo_id"), gift.get("fo_name"), gift.get("score"), Integer.valueOf(1), Integer.valueOf(0), "present" });
/*    */   }
/*    */ 
/*    */   public List getGiftList(Promotion promotion)
/*    */   {
/* 81 */     String solution = promotion.getPmt_solution();
/* 82 */     if ((solution == null) || ("".equals(solution))) return null;
/*    */ 
/* 84 */     JSONArray jsonArray = JSONArray.fromObject(solution);
/* 85 */     Object[] giftIdArray = jsonArray.toArray();
/*    */ 
/* 87 */     String sql = "select * from freeoffer where fo_id in(" + StringUtil.arrayToString(giftIdArray, ",") + ") ";
/* 88 */     return this.baseDaoSupport.queryForList(sql, new Object[0]);
/*    */   }
/*    */ 
/*    */   public IFreeOfferManager getFreeOfferManager() {
/* 92 */     return this.freeOfferManager;
/*    */   }
/*    */ 
/*    */   public void setFreeOfferManager(IFreeOfferManager freeOfferManager) {
/* 96 */     this.freeOfferManager = freeOfferManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.service.impl.promotion.GiveGiftMethod
 * JD-Core Version:    0.6.0
 */