/*     */ package com.enation.app.shop.component.ordercore.plugin.statecheck;
/*     */ 
/*     */ import com.enation.app.base.core.plugin.job.IEveryDayExecuteEvent;
/*     */ import com.enation.app.shop.core.model.Delivery;
/*     */ import com.enation.app.shop.core.model.OrderLog;
/*     */ import com.enation.app.shop.core.service.IOrderFlowManager;
/*     */ import com.enation.eop.processor.core.RemoteRequest;
/*     */ import com.enation.eop.processor.core.Request;
/*     */ import com.enation.eop.processor.core.Response;
/*     */ import com.enation.framework.database.IDBRouter;
/*     */ import com.enation.framework.database.IDaoSupport;
/*     */ import com.enation.framework.plugin.AutoRegisterPlugin;
/*     */ import com.enation.framework.util.DateUtil;
/*     */ import java.io.PrintStream;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.sf.json.JSONObject;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.springframework.stereotype.Component;
/*     */ 
/*     */ @Component
/*     */ public class OrderStateCheckPlugin extends AutoRegisterPlugin
/*     */   implements IEveryDayExecuteEvent
/*     */ {
/*     */   private IDaoSupport baseDaoSupport;
/*     */   private IDaoSupport daoSupport;
/*     */   private IDBRouter baseDBRouter;
/*     */   private IOrderFlowManager orderFlowManager;
/*     */ 
/*     */   public void everyDay()
/*     */   {
/*  41 */     checkRog();
/*  42 */     checkcmpl();
/*     */   }
/*     */ 
/*     */   private void checkcmpl()
/*     */   {
/*  52 */     long unix_timestamp = DateUtil.getDatelineLong();
/*  53 */     String sql = "select order_id from order where  " + unix_timestamp + " >=  signing_time+30*24*60*60 and  signing_time is not null and   signing_time>0  and status!=" + 7;
/*  54 */     List list = this.baseDaoSupport.queryForList(sql, new Object[0]);
/*  55 */     if ((list != null) && (list.size() > 0)) {
/*  56 */       String orderids = "";
/*  57 */       for (int i = 0; i < list.size(); i++) {
/*  58 */         String orderid = ((Map)list.get(i)).get("order_id").toString();
/*  59 */         orderids = orderids + Integer.parseInt(orderid);
/*  60 */         if (i < list.size() - 1)
/*  61 */           orderids = orderids + ",";
/*  62 */         OrderLog orderLog = new OrderLog();
/*  63 */         orderLog.setMessage("系统检测到订单[" + orderid + "]为完成状态");
/*  64 */         orderLog.setOp_id(Integer.valueOf(0));
/*  65 */         orderLog.setOp_name("系统检测");
/*  66 */         orderLog.setOp_time(Long.valueOf(System.currentTimeMillis()));
/*  67 */         orderLog.setOrder_id(Integer.valueOf(Integer.parseInt(orderid)));
/*  68 */         this.baseDaoSupport.insert("order_log", orderLog);
/*     */       }
/*     */ 
/*  71 */       sql = "update order set status =7,complete_time=" + unix_timestamp + " where order_id in (" + orderids + ")";
/*     */ 
/*  73 */       this.baseDaoSupport.execute(sql, new Object[0]);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void checkRog()
/*     */   {
/*  85 */     String sql = "select d.* from " + this.baseDBRouter.getTableName("order") + " o ," + this.baseDBRouter.getTableName("delivery") + " d where o.order_id=d.order_id and (o.status=5  or ( o.status=2 and o.payment_id=2))";
/*     */ 
/*  87 */     List deliList = this.daoSupport.queryForList(sql, Delivery.class, new Object[0]);
/*  88 */     for (Delivery delivery : deliList)
/*  89 */       checkRogState(delivery);
/*     */   }
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/*  95 */     String content = "{\"message\":\"ok\",\"status\":\"1\",\"link\":\"http://kuaidi100.com/chaxun?com=yuantong&nu=7000711004\",\"state\":\"3\",\"data\":[{\"time\":\"2011-10-15 13:15:29\",\"context\":\"河南郑州市石化路/正常签收录入扫描 /签收人:草签 \"}]}";
/*  96 */     Map result = (Map)JSONObject.toBean(JSONObject.fromObject(content), Map.class);
/*  97 */     String data = result.get("data").toString();
/*     */ 
/*  99 */     System.out.println(data);
/* 100 */     String context = data.substring(data.indexOf("context=") + 8, data.indexOf("}"));
/* 101 */     String time = data.substring(data.indexOf("time=") + 5, data.indexOf(","));
/* 102 */     System.out.println("context=" + context);
/* 103 */     System.out.println("time" + time);
/* 104 */     String uname = context.substring(context.indexOf("签收人:") + 4, context.length());
/* 105 */     System.out.println("uname=" + uname);
/*     */   }
/*     */ 
/*     */   private void checkRogState(Delivery delivery)
/*     */   {
/*     */     try {
/* 111 */       Request request = new RemoteRequest();
/* 112 */       Response response = request.execute("http://api.kuaidi100.com/apione?com=" + delivery.getLogi_code() + "&nu=" + delivery.getLogi_no() + "&show=0");
/* 113 */       String content = response.getContent();
/* 114 */       this.logger.debug("dingdangchaxun:" + content);
/* 115 */       Map result = (Map)JSONObject.toBean(JSONObject.fromObject(content), Map.class);
/*     */ 
/* 118 */       if ("1".equals(result.get("status"))) {
/* 119 */         String data = result.get("data").toString();
/* 120 */         String context = data.substring(data.indexOf("context=") + 8, data.indexOf("}"));
/*     */ 
/* 122 */         if ("3".equals(result.get("state"))) {
/* 123 */           String uname = context.substring(context.indexOf("签收人:") + 4, context.length());
/* 124 */           String time = data.substring(data.indexOf("time=") + 5, data.indexOf(","));
/* 125 */           System.out.println(time);
/* 126 */           int utime = 0;
/* 127 */           if ((time != null) && (!"".equals(time))) {
/* 128 */             utime = DateUtil.getDateline(time, "yyyy-MM-dd HH:mm:ss");
/*     */           }
/*     */ 
/* 141 */           this.orderFlowManager.rogConfirm(delivery.getOrder_id().intValue(), Integer.valueOf(0), "系统检测", uname, utime);
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (RuntimeException e)
/*     */     {
/* 147 */       this.logger.error("检测订单收货状态出错", e);
/* 148 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   public IDaoSupport getBaseDaoSupport()
/*     */   {
/* 154 */     return this.baseDaoSupport;
/*     */   }
/*     */ 
/*     */   public void setBaseDaoSupport(IDaoSupport baseDaoSupport) {
/* 158 */     this.baseDaoSupport = baseDaoSupport;
/*     */   }
/*     */ 
/*     */   public IOrderFlowManager getOrderFlowManager() {
/* 162 */     return this.orderFlowManager;
/*     */   }
/*     */ 
/*     */   public void setOrderFlowManager(IOrderFlowManager orderFlowManager) {
/* 166 */     this.orderFlowManager = orderFlowManager;
/*     */   }
/*     */ 
/*     */   public IDaoSupport getDaoSupport() {
/* 170 */     return this.daoSupport;
/*     */   }
/*     */ 
/*     */   public void setDaoSupport(IDaoSupport daoSupport) {
/* 174 */     this.daoSupport = daoSupport;
/*     */   }
/*     */ 
/*     */   public IDBRouter getBaseDBRouter() {
/* 178 */     return this.baseDBRouter;
/*     */   }
/*     */ 
/*     */   public void setBaseDBRouter(IDBRouter baseDBRouter) {
/* 182 */     this.baseDBRouter = baseDBRouter;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.ordercore.plugin.statecheck.OrderStateCheckPlugin
 * JD-Core Version:    0.6.0
 */