/*    */ package com.enation.app.shop.component.goodscore.widget.goods.sns;
/*    */ 
/*    */ import com.enation.eop.sdk.context.EopSetting;
/*    */ import com.enation.eop.sdk.utils.UploadUtil;
/*    */ import com.enation.eop.sdk.widget.AbstractWidget;
/*    */ import com.enation.framework.context.webcontext.ThreadContextHolder;
/*    */ import com.enation.framework.util.RequestUtil;
/*    */ import com.enation.framework.util.StringUtil;
/*    */ import java.util.Map;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import org.springframework.context.annotation.Scope;
/*    */ import org.springframework.stereotype.Component;
/*    */ 
/*    */ @Component("goods_sns_share")
/*    */ @Scope("prototype")
/*    */ public class GoodsSnsShareWidget extends AbstractWidget
/*    */ {
/*    */   protected void config(Map<String, String> params)
/*    */   {
/*    */   }
/*    */ 
/*    */   protected void display(Map<String, String> params)
/*    */   {
/* 33 */     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
/* 34 */     Map goods = (Map)request.getAttribute("goods");
/* 35 */     if (goods == null) throw new RuntimeException("参数显示挂件必须和商品详细显示挂件同时存在");
/* 36 */     String default_img = (String)goods.get("image_default");
/* 37 */     if (StringUtil.isEmpty(default_img)) {
/* 38 */       default_img = EopSetting.IMG_SERVER_DOMAIN + "/images/no_picture.jpg";
/*    */     } else {
/* 40 */       default_img = UploadUtil.getThumbPath(default_img, "_small");
/* 41 */       default_img = UploadUtil.replacePath(default_img);
/*    */     }
/*    */ 
/* 44 */     String title = (String)goods.get("name");
/* 45 */     String url = RequestUtil.getWholeUrl(request);
/*    */ 
/* 47 */     title = title.replaceAll("\r", "").replaceAll("\n", "");
/*    */ 
/* 49 */     putData("title", title);
/* 50 */     putData("url", url);
/* 51 */     putData("img", default_img);
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.goodscore.widget.goods.sns.GoodsSnsShareWidget
 * JD-Core Version:    0.6.0
 */