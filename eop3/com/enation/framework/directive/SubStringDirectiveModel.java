/*     */ package com.enation.framework.directive;
/*     */ 
/*     */ import com.enation.framework.util.StringUtil;
/*     */ import freemarker.core.Environment;
/*     */ import freemarker.template.TemplateDirectiveBody;
/*     */ import freemarker.template.TemplateDirectiveModel;
/*     */ import freemarker.template.TemplateException;
/*     */ import freemarker.template.TemplateModel;
/*     */ import java.io.IOException;
/*     */ import java.io.Writer;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class SubStringDirectiveModel
/*     */   implements TemplateDirectiveModel
/*     */ {
/*     */   public void execute(Environment env, Map params, TemplateModel[] arg2, TemplateDirectiveBody arg3)
/*     */     throws TemplateException, IOException
/*     */   {
/*  23 */     String title = params.get("title").toString();
/*  24 */     int length = StringUtil.toInt(params.get("length").toString());
/*  25 */     String dot = params.get("dot").toString();
/*  26 */     String link = "";
/*     */ 
/*  28 */     int titleLength = getLength(title);
/*     */ 
/*  30 */     if (titleLength > 0) {
/*  31 */       if (length > titleLength)
/*  32 */         link = title;
/*     */       else {
/*  34 */         link = getSubString(title, 0, length) + dot;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/*  39 */     env.getOut().write(link);
/*     */   }
/*     */ 
/*     */   private int getLength(String str)
/*     */   {
/*  48 */     int count1 = 0;
/*  49 */     if ((str == null) || (str.equals("")))
/*  50 */       return count1;
/*  51 */     char[] temp = new char[str.length()];
/*  52 */     str.getChars(0, str.length(), temp, 0);
/*  53 */     boolean[] bol = new boolean[str.length()];
/*  54 */     for (int i = 0; i < temp.length; i++) {
/*  55 */       bol[i] = false;
/*  56 */       if (temp[i] > 'ÿ') {
/*  57 */         count1++;
/*  58 */         bol[i] = true;
/*     */       }
/*     */     }
/*  61 */     return count1 + str.length();
/*     */   }
/*     */ 
/*     */   private String getSubString(String str, int pstart, int pend)
/*     */   {
/*  72 */     String resu = "";
/*  73 */     int beg = 0;
/*  74 */     int end = 0;
/*  75 */     int count1 = 0;
/*  76 */     char[] temp = new char[str.length()];
/*  77 */     str.getChars(0, str.length(), temp, 0);
/*  78 */     boolean[] bol = new boolean[str.length()];
/*  79 */     for (int i = 0; i < temp.length; i++) {
/*  80 */       bol[i] = false;
/*  81 */       if (temp[i] > 'ÿ') {
/*  82 */         count1++;
/*  83 */         bol[i] = true;
/*     */       }
/*     */     }
/*     */ 
/*  87 */     if (pstart > str.length() + count1) {
/*  88 */       resu = null;
/*     */     }
/*  90 */     if (pstart > pend) {
/*  91 */       resu = null;
/*     */     }
/*  93 */     if (pstart < 1)
/*  94 */       beg = 0;
/*     */     else {
/*  96 */       beg = pstart - 1;
/*     */     }
/*  98 */     if (pend > str.length() + count1)
/*  99 */       end = str.length() + count1;
/*     */     else {
/* 101 */       end = pend;
/*     */     }
/*     */ 
/* 104 */     if (resu != null) {
/* 105 */       if (beg == end) {
/* 106 */         int count = 0;
/* 107 */         if (beg == 0) {
/* 108 */           if (bol[0] == 1)
/* 109 */             resu = null;
/*     */           else
/* 111 */             resu = new String(temp, 0, 1);
/*     */         } else {
/* 113 */           int len = beg;
/* 114 */           for (int y = 0; y < len; y++) {
/* 115 */             if (bol[y] == 1)
/* 116 */               count++;
/* 117 */             len--;
/*     */           }
/*     */ 
/* 120 */           if (count == 0) {
/* 121 */             if (temp[beg] > 'ÿ')
/* 122 */               resu = null;
/*     */             else
/* 124 */               resu = new String(temp, beg, 1);
/*     */           }
/* 126 */           else if (temp[(len + 1)] > 'ÿ')
/* 127 */             resu = null;
/*     */           else
/* 129 */             resu = new String(temp, len + 1, 1);
/*     */         }
/*     */       }
/*     */       else {
/* 133 */         int temSt = beg;
/* 134 */         int temEd = end - 1;
/* 135 */         for (int i = 0; i < temSt; i++) {
/* 136 */           if (bol[i] == 1)
/* 137 */             temSt--;
/*     */         }
/* 139 */         for (int j = 0; j < temEd; j++) {
/* 140 */           if (bol[j] == 1)
/* 141 */             temEd--;
/*     */         }
/* 143 */         if (bol[temSt] == 1)
/*     */         {
/* 145 */           int cont = 0;
/* 146 */           for (int i = 0; i <= temSt; i++) {
/* 147 */             cont++;
/* 148 */             if (bol[i] == 1)
/* 149 */               cont++;
/*     */           }
/* 151 */           if (pstart == cont)
/* 152 */             temSt++;
/*     */         }
/* 154 */         if (bol[temEd] == 1)
/*     */         {
/* 156 */           int cont = 0;
/* 157 */           for (int i = 0; i <= temEd; i++) {
/* 158 */             cont++;
/* 159 */             if (bol[i] == 1)
/* 160 */               cont++;
/*     */           }
/* 162 */           if (pend < cont)
/* 163 */             temEd--;
/*     */         }
/* 165 */         if (temSt == temEd)
/* 166 */           resu = new String(temp, temSt, 1);
/* 167 */         else if (temSt > temEd)
/* 168 */           resu = null;
/*     */         else {
/* 170 */           resu = str.substring(temSt, temEd + 1);
/*     */         }
/*     */       }
/*     */     }
/* 174 */     return resu;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.framework.directive.SubStringDirectiveModel
 * JD-Core Version:    0.6.0
 */