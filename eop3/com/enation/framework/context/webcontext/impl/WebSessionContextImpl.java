/*     */ package com.enation.framework.context.webcontext.impl;
/*     */ 
/*     */ import com.enation.framework.context.webcontext.WebSessionContext;
/*     */ import java.io.Externalizable;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInput;
/*     */ import java.io.ObjectOutput;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Set;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.logging.LogFactory;
/*     */ 
/*     */ public class WebSessionContextImpl
/*     */   implements WebSessionContext, Externalizable
/*     */ {
/*     */   private HttpSession session;
/*  24 */   private final Log logger = LogFactory.getLog(getClass());
/*     */   private Hashtable attributes;
/*     */ 
/*     */   public HttpSession getSession()
/*     */   {
/*  38 */     return this.session;
/*     */   }
/*     */ 
/*     */   public void setSession(HttpSession session)
/*     */   {
/*  52 */     this.session = session;
/*  53 */     this.attributes = ((Hashtable)this.session.getAttribute("EOPSessionKey"));
/*     */ 
/*  55 */     if (this.attributes == null) {
/*  56 */       this.attributes = new Hashtable();
/*  57 */       onSaveSessionAttribute();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void invalidateSession()
/*     */   {
/*  68 */     this.session.invalidate();
/*     */   }
/*     */ 
/*     */   private void onSaveSessionAttribute()
/*     */   {
/*  86 */     this.session.setAttribute("EOPSessionKey", this.attributes);
/*     */   }
/*     */ 
/*     */   public void setAttribute(String name, Object value)
/*     */   {
/*  97 */     if (this.attributes != null)
/*     */     {
/* 105 */       this.attributes.put(name, value);
/* 106 */       onSaveSessionAttribute();
/*     */     }
/*     */   }
/*     */ 
/*     */   public Object getAttribute(String name)
/*     */   {
/* 116 */     if (this.attributes != null)
/* 117 */       return this.attributes.get(name);
/* 118 */     return null;
/*     */   }
/*     */ 
/*     */   public Set getAttributeNames()
/*     */   {
/* 127 */     return this.attributes.keySet();
/*     */   }
/*     */ 
/*     */   public void removeAttribute(String name)
/*     */   {
/* 136 */     this.attributes.remove(name);
/* 137 */     onSaveSessionAttribute();
/*     */   }
/*     */ 
/*     */   public void readExternal(ObjectInput input)
/*     */     throws IOException, ClassNotFoundException
/*     */   {
/* 151 */     this.attributes = ((Hashtable)input.readObject());
/*     */   }
/*     */ 
/*     */   public void writeExternal(ObjectOutput output) throws IOException {
/* 155 */     output.writeObject(this.attributes);
/*     */   }
/*     */ 
/*     */   public void destory() {
/* 159 */     this.attributes = null;
/* 160 */     this.session = null;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.framework.context.webcontext.impl.WebSessionContextImpl
 * JD-Core Version:    0.6.0
 */