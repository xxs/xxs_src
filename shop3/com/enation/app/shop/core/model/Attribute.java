/*     */ package com.enation.app.shop.core.model;
/*     */ 
/*     */ import com.enation.framework.util.StringUtil;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class Attribute
/*     */ {
/*     */   private String name;
/*     */   private String options;
/*     */   private int type;
/*     */   private String value;
/*     */   private List valueList;
/*  30 */   private int[] nums = null;
/*     */   private int hidden;
/*     */   private int required;
/*     */   private String datatype;
/*     */   private String unit;
/*     */   private Map[] maps;
/*     */ 
/*     */   public Attribute()
/*     */   {
/*  37 */     this.valueList = new ArrayList();
/*  38 */     this.hidden = 0;
/*     */   }
/*     */ 
/*     */   public void addValue(String _value) {
/*  42 */     this.valueList.add(_value);
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/*  47 */     return this.name;
/*     */   }
/*     */   public void setName(String name) {
/*  50 */     this.name = name;
/*     */   }
/*     */   public int getType() {
/*  53 */     return this.type;
/*     */   }
/*     */   public void setType(int type) {
/*  56 */     this.type = type;
/*     */   }
/*     */   public String getValue() {
/*  59 */     return this.value;
/*     */   }
/*     */   public void setValue(String value) {
/*  62 */     this.value = value;
/*     */   }
/*     */ 
/*     */   public String getValStr()
/*     */   {
/*  67 */     if (this.type >= 3)
/*     */     {
/*  69 */       if ((this.type >= 3) && (this.type <= 5) && 
/*  70 */         (this.value != null) && (!this.value.equals("")) && (!this.value.equals("null"))) {
/*  71 */         int index1 = StringUtil.toInt(this.value, false);
/*  72 */         if (getOptionAr().length > index1) {
/*  73 */           return getOptionAr()[StringUtil.toInt(this.value, false)];
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/*  78 */     return this.value;
/*     */   }
/*     */ 
/*     */   public String getOptions() {
/*  82 */     return this.options;
/*     */   }
/*     */   public void setOptions(String options) {
/*  85 */     this.options = options;
/*     */   }
/*     */ 
/*     */   public String[] getOptionAr()
/*     */   {
/*  90 */     if ((this.options == null) || (this.options.equals(""))) {
/*  91 */       return new String[0];
/*     */     }
/*     */ 
/*  94 */     String[] ar_options = this.options.split(",");
/*     */ 
/*  96 */     return ar_options;
/*     */   }
/*     */ 
/*     */   public Map[] getOptionMap()
/*     */   {
/* 108 */     String[] optionAr = getOptionAr();
/*     */ 
/* 110 */     if (this.maps == null) {
/* 111 */       this.maps = new Map[optionAr.length];
/*     */ 
/* 113 */       for (int i = 0; i < optionAr.length; i++) {
/* 114 */         Map m = new HashMap(4);
/* 115 */         m.put("name", optionAr[i]);
/* 116 */         m.put("num", Integer.valueOf(getNums()[i]));
/* 117 */         m.put("url", "");
/* 118 */         m.put("selected", Integer.valueOf(0));
/* 119 */         this.maps[i] = m;
/*     */       }
/*     */     }
/* 122 */     return this.maps;
/*     */   }
/*     */ 
/*     */   public List getValueList()
/*     */   {
/* 129 */     return this.valueList;
/*     */   }
/*     */   public void setValueList(List valueList) {
/* 132 */     this.valueList = valueList;
/*     */   }
/*     */ 
/*     */   public int[] getNums()
/*     */   {
/* 137 */     if (this.nums == null) this.nums = new int[getOptionAr().length];
/*     */ 
/* 139 */     return this.nums;
/*     */   }
/*     */ 
/*     */   public void setNums(int[] nums) {
/* 143 */     this.nums = nums;
/*     */   }
/*     */ 
/*     */   public int getHidden()
/*     */   {
/* 149 */     return this.hidden;
/*     */   }
/*     */ 
/*     */   public void setHidden(int hidden) {
/* 153 */     this.hidden = hidden;
/*     */   }
/*     */ 
/*     */   public int getRequired() {
/* 157 */     return this.required;
/*     */   }
/*     */ 
/*     */   public void setRequired(int required) {
/* 161 */     this.required = required;
/*     */   }
/*     */ 
/*     */   public String getDatatype() {
/* 165 */     return this.datatype;
/*     */   }
/*     */ 
/*     */   public void setDatatype(String datatype) {
/* 169 */     this.datatype = datatype;
/*     */   }
/*     */ 
/*     */   public String getUnit() {
/* 173 */     return this.unit;
/*     */   }
/*     */ 
/*     */   public void setUnit(String unit) {
/* 177 */     this.unit = unit;
/*     */   }
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.model.Attribute
 * JD-Core Version:    0.6.0
 */