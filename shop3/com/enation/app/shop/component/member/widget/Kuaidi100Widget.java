/*    */ package com.enation.app.shop.component.member.widget;
/*    */ 
/*    */ import com.enation.app.shop.core.service.IOrderManager;
/*    */ import com.enation.eop.processor.core.RemoteRequest;
/*    */ import com.enation.eop.processor.core.Request;
/*    */ import com.enation.eop.processor.core.Response;
/*    */ import com.enation.eop.sdk.widget.AbstractWidget;
/*    */ import com.enation.framework.context.webcontext.ThreadContextHolder;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import net.sf.json.JSONObject;
/*    */ import org.springframework.context.annotation.Scope;
/*    */ import org.springframework.stereotype.Component;
/*    */ 
/*    */ @Component("kuaidi")
/*    */ @Scope("prototype")
/*    */ public class Kuaidi100Widget extends AbstractWidget
/*    */ {
/*    */   private IOrderManager orderManager;
/*    */ 
/*    */   protected void display(Map<String, String> params)
/*    */   {
/* 33 */     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
/* 34 */     String logino = request.getParameter("logino");
/* 35 */     String code = request.getParameter("code");
/* 36 */     if ((logino == null) || (logino.length() < 5))
/*    */     {
/* 38 */       Map result = new HashMap();
/* 39 */       result.put("status", "-1");
/* 40 */       putData("result", result);
/* 41 */       return;
/*    */     }
/* 43 */     if ((code == null) || (code.equals(""))) {
/* 44 */       code = "yuantong";
/*    */     }
/* 46 */     Request remoteRequest = new RemoteRequest();
/*    */ 
/* 48 */     Response remoteResponse = remoteRequest.execute("http://api.kuaidi100.com/api?id=c24581986993585e&com=" + code + "&nu=" + logino + "&show=0&muti=1&order=asc");
/* 49 */     String content = remoteResponse.getContent();
/* 50 */     Map result = (Map)JSONObject.toBean(JSONObject.fromObject(content), Map.class);
/* 51 */     putData("result", result);
/*    */   }
/*    */ 
/*    */   protected void config(Map<String, String> params)
/*    */   {
/*    */   }
/*    */ 
/*    */   public IOrderManager getOrderManager()
/*    */   {
/* 62 */     return this.orderManager;
/*    */   }
/*    */ 
/*    */   public void setOrderManager(IOrderManager orderManager) {
/* 66 */     this.orderManager = orderManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.member.widget.Kuaidi100Widget
 * JD-Core Version:    0.6.0
 */