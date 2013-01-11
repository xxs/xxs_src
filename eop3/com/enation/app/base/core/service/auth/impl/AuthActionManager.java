/*     */ package com.enation.app.base.core.service.auth.impl;
/*     */ 
/*     */ import com.enation.app.base.core.model.AuthAction;
/*     */ import com.enation.app.base.core.service.auth.IAuthActionManager;
/*     */ import com.enation.eop.sdk.database.BaseSupport;
/*     */ import com.enation.framework.database.IDaoSupport;
/*     */ import com.enation.framework.util.StringUtil;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.springframework.transaction.annotation.Propagation;
/*     */ import org.springframework.transaction.annotation.Transactional;
/*     */ 
/*     */ public class AuthActionManager extends BaseSupport<AuthAction>
/*     */   implements IAuthActionManager
/*     */ {
/*     */   @Transactional(propagation=Propagation.REQUIRED)
/*     */   public int add(AuthAction act)
/*     */   {
/*  24 */     this.baseDaoSupport.insert("auth_action", act);
/*  25 */     return this.baseDaoSupport.getLastId("auth_action");
/*     */   }
/*     */ 
/*     */   @Transactional(propagation=Propagation.REQUIRED)
/*     */   public void delete(int actid) {
/*  31 */     this.baseDaoSupport.execute("delete from role_auth where authid=?", new Object[] { Integer.valueOf(actid) });
/*     */ 
/*  33 */     this.baseDaoSupport.execute("delete from auth_action where actid=?", new Object[] { Integer.valueOf(actid) });
/*     */   }
/*     */ 
/*     */   public void edit(AuthAction act)
/*     */   {
/*  38 */     this.baseDaoSupport.update("auth_action", act, "actid=" + act.getActid());
/*     */   }
/*     */ 
/*     */   public List<AuthAction> list()
/*     */   {
/*  44 */     return this.baseDaoSupport.queryForList("select * from auth_action", AuthAction.class, new Object[0]);
/*     */   }
/*     */ 
/*     */   public AuthAction get(int authid)
/*     */   {
/*  51 */     List list = this.baseDaoSupport.queryForList("select * from auth_action where actid=?", AuthAction.class, new Object[] { Integer.valueOf(authid) });
/*  52 */     AuthAction result = null;
/*  53 */     if (list.size() > 0)
/*  54 */       result = (AuthAction)list.get(0);
/*  55 */     return result;
/*     */   }
/*     */ 
/*     */   public void addMenu(int actid, Integer[] menuidAr)
/*     */   {
/*  60 */     if (menuidAr == null) return;
/*     */ 
/*  62 */     AuthAction authAction = get(actid);
/*  63 */     String menuStr = authAction.getObjvalue();
/*  64 */     if (StringUtil.isEmpty(menuStr)) {
/*  65 */       menuStr = StringUtil.arrayToString(menuidAr, ",");
/*  66 */       authAction.setObjvalue(menuStr);
/*     */     } else {
/*  68 */       String[] oldMenuAr = StringUtils.split(menuStr, ",");
/*  69 */       oldMenuAr = merge(menuidAr, oldMenuAr);
/*  70 */       menuStr = StringUtil.arrayToString(oldMenuAr, ",");
/*  71 */       authAction.setObjvalue(menuStr);
/*     */     }
/*     */ 
/*  74 */     edit(authAction);
/*     */   }
/*     */ 
/*     */   public void deleteMenu(int actid, Integer[] menuidAr)
/*     */   {
/*  81 */     if (menuidAr == null) return;
/*  82 */     AuthAction authAction = get(actid);
/*  83 */     String menuStr = authAction.getObjvalue();
/*  84 */     if (StringUtil.isEmpty(menuStr)) {
/*  85 */       return;
/*     */     }
/*     */ 
/*  88 */     String[] oldMenuAr = StringUtils.split(menuStr, ","); menuStr.split(",");
/*  89 */     oldMenuAr = delete(menuidAr, oldMenuAr);
/*  90 */     menuStr = StringUtil.arrayToString(oldMenuAr, ",");
/*  91 */     authAction.setObjvalue(menuStr);
/*  92 */     edit(authAction);
/*     */   }
/*     */ 
/*     */   private static String[] merge(Integer[] ar1, String[] ar2)
/*     */   {
/* 104 */     List newList = new ArrayList();
/* 105 */     for (String num : ar2) {
/* 106 */       newList.add(num);
/*     */     }
/*     */ 
/* 110 */     boolean flag = false;
/* 111 */     for (Integer num1 : ar1) {
/* 112 */       flag = false;
/*     */ 
/* 114 */       for (String num2 : ar2) {
/* 115 */         if (num1.intValue() == Integer.valueOf(num2).intValue()) {
/* 116 */           flag = true;
/* 117 */           break;
/*     */         }
/*     */       }
/*     */ 
/* 121 */       if (!flag) {
/* 122 */         newList.add(String.valueOf(num1));
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 127 */     return (String[])(String[])newList.toArray(new String[newList.size()]);
/*     */   }
/*     */ 
/*     */   public static String[] delete(Integer[] ar1, String[] ar2)
/*     */   {
/* 138 */     List newList = new ArrayList();
/* 139 */     boolean flag = false;
/* 140 */     for (String num2 : ar2) {
/* 141 */       flag = false;
/* 142 */       for (Integer num1 : ar1) {
/* 143 */         if (num1.intValue() == Integer.valueOf(num2).intValue()) {
/* 144 */           flag = true;
/* 145 */           break;
/*     */         }
/*     */       }
/*     */ 
/* 149 */       if (!flag) {
/* 150 */         newList.add(num2);
/*     */       }
/*     */     }
/*     */ 
/* 154 */     return (String[])(String[])newList.toArray(new String[newList.size()]);
/*     */   }
/*     */ 
/*     */   public static void main(String[] args) {
/* 158 */     Integer[] ar1 = { Integer.valueOf(15), Integer.valueOf(29), Integer.valueOf(23) };
/* 159 */     String[] ar2 = { "1", "2", "7", "0", "23", "4" };
/*     */ 
/* 161 */     String[] newar = delete(ar1, ar2);
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.app.base.core.service.auth.impl.AuthActionManager
 * JD-Core Version:    0.6.0
 */