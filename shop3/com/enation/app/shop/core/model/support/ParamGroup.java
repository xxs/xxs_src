/*    */ package com.enation.app.shop.core.model.support;
/*    */ 
/*    */ import com.enation.app.shop.core.model.GoodsParam;
/*    */ import java.util.List;
/*    */ 
/*    */ public class ParamGroup
/*    */ {
/*    */   private String name;
/*    */   private List<GoodsParam> paramList;
/*    */ 
/*    */   public String getName()
/*    */   {
/* 22 */     return this.name;
/*    */   }
/*    */   public void setName(String name) {
/* 25 */     this.name = name;
/*    */   }
/*    */   public List<GoodsParam> getParamList() {
/* 28 */     return this.paramList;
/*    */   }
/*    */   public void setParamList(List<GoodsParam> paramList) {
/* 31 */     this.paramList = paramList;
/*    */   }
/*    */ 
/*    */   public int getParamNum()
/*    */   {
/* 41 */     if (this.paramList == null) return 0;
/* 42 */     return this.paramList.size();
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.model.support.ParamGroup
 * JD-Core Version:    0.6.0
 */