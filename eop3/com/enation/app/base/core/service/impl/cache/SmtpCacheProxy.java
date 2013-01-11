/*     */ package com.enation.app.base.core.service.impl.cache;
/*     */ 
/*     */ import com.enation.app.base.core.model.Smtp;
/*     */ import com.enation.app.base.core.service.ISmtpManager;
/*     */ import com.enation.eop.resource.model.EopSite;
/*     */ import com.enation.eop.sdk.context.EopContext;
/*     */ import com.enation.eop.sdk.context.EopSetting;
/*     */ import com.enation.framework.cache.AbstractCacheProxy;
/*     */ import com.enation.framework.cache.ICache;
/*     */ import com.enation.framework.util.DateUtil;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class SmtpCacheProxy extends AbstractCacheProxy<List<Smtp>>
/*     */   implements ISmtpManager
/*     */ {
/*     */   private static final String cacheName = "smtp_cache";
/*     */   private ISmtpManager smtpManager;
/*     */ 
/*     */   public SmtpCacheProxy(ISmtpManager _smtpManager)
/*     */   {
/*  21 */     super("smtp_cache");
/*  22 */     this.smtpManager = _smtpManager;
/*     */   }
/*     */ 
/*     */   private String getKey()
/*     */   {
/*  27 */     if ("2".equals(EopSetting.RUNMODE)) {
/*  28 */       EopSite site = EopContext.getContext().getCurrentSite();
/*  29 */       return "smtp_cache_" + site.getUserid() + "_" + site.getId();
/*     */     }
/*  31 */     return "smtp_cache";
/*     */   }
/*     */ 
/*     */   private void cleanCache()
/*     */   {
/*  38 */     String mainkey = getKey();
/*  39 */     this.cache.remove(mainkey);
/*     */   }
/*     */ 
/*     */   private void put(List<Smtp> smtpList)
/*     */   {
/*  44 */     String mainkey = getKey();
/*  45 */     this.cache.put(mainkey, smtpList);
/*     */   }
/*     */ 
/*     */   private List<Smtp> get() {
/*  49 */     String mainkey = getKey();
/*  50 */     return (List)this.cache.get(mainkey);
/*     */   }
/*     */ 
/*     */   public void add(Smtp smtp)
/*     */   {
/*  56 */     this.smtpManager.add(smtp);
/*  57 */     cleanCache();
/*     */   }
/*     */ 
/*     */   public void edit(Smtp smtp)
/*     */   {
/*  62 */     this.smtpManager.edit(smtp);
/*  63 */     cleanCache();
/*     */   }
/*     */ 
/*     */   public void delete(Integer[] idAr)
/*     */   {
/*  68 */     this.smtpManager.delete(idAr);
/*  69 */     cleanCache();
/*     */   }
/*     */ 
/*     */   public List<Smtp> list()
/*     */   {
/*  74 */     List smtpList = get();
/*  75 */     if (smtpList == null) {
/*  76 */       smtpList = this.smtpManager.list();
/*  77 */       put(smtpList);
/*     */     }
/*  79 */     return smtpList;
/*     */   }
/*     */ 
/*     */   public void sendOneMail(Smtp currSmtp)
/*     */   {
/*  86 */     currSmtp.setLast_send_time(DateUtil.getDatelineLong());
/*  87 */     currSmtp.setSend_count(currSmtp.getSend_count() + 1);
/*     */ 
/*  89 */     this.smtpManager.sendOneMail(currSmtp);
/*     */   }
/*     */ 
/*     */   public Smtp get(int id)
/*     */   {
/*  94 */     return this.smtpManager.get(id);
/*     */   }
/*     */ 
/*     */   public Smtp getCurrentSmtp()
/*     */   {
/* 101 */     List smtpList = list();
/* 102 */     if ((smtpList == null) || (smtpList == null)) throw new RuntimeException("没有可用的smtp服务器");
/*     */ 
/* 104 */     Smtp currentSmtp = null;
/*     */ 
/* 107 */     for (Smtp smtp : smtpList) {
/* 108 */       if (checkCount(smtp)) {
/* 109 */         currentSmtp = smtp;
/* 110 */         break;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 116 */     if (currentSmtp == null) {
/* 117 */       this.logger.error("未寻找可用smtp");
/* 118 */       throw new RuntimeException("未找到可用smtp，都已达到最大发信数 ");
/*     */     }
/*     */ 
/* 121 */     if (this.logger.isDebugEnabled()) {
/* 122 */       this.logger.debug("找到smtp->host[" + currentSmtp.getHost() + "],username[" + currentSmtp.getUsername() + "]");
/*     */     }
/*     */ 
/* 125 */     return currentSmtp;
/*     */   }
/*     */ 
/*     */   private boolean checkCount(Smtp smtp)
/*     */   {
/* 130 */     long last_send_time = smtp.getLast_send_time();
/*     */ 
/* 132 */     if (!DateUtil.toString(new Date(last_send_time * 1000L), "yyyy-MM-dd").equals(DateUtil.toString(new Date(), "yyyy-MM-dd"))) {
/* 133 */       smtp.setSend_count(0);
/*     */ 
/* 135 */       if (this.logger.isDebugEnabled()) {
/* 136 */         this.logger.debug("host[" + smtp.getHost() + "]不是今天,此smtp发信数置为0");
/*     */       }
/*     */     }
/*     */ 
/* 140 */     return smtp.getSend_count() < smtp.getMax_count();
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.app.base.core.service.impl.cache.SmtpCacheProxy
 * JD-Core Version:    0.6.0
 */