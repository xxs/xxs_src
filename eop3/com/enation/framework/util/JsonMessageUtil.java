/*    */ package com.enation.framework.util;
/*    */ 
/*    */ import java.util.List;
/*    */ import net.sf.json.JSONArray;
/*    */ import net.sf.json.JSONObject;
/*    */ 
/*    */ public class JsonMessageUtil
/*    */ {
/*    */   public static String getObjectJson(Object object)
/*    */   {
/* 19 */     if (object == null) {
/* 20 */       return getErrorJson("object is null");
/*    */     }
/* 22 */     String objStr = JSONObject.fromObject(object).toString();
/*    */ 
/* 24 */     return "{\"result\":1,\"data\":" + objStr + "}";
/*    */   }
/*    */ 
/*    */   public static String getNumberJson(String name, Object value)
/*    */   {
/* 29 */     return "{\"result\":1,\"" + name + "\":" + value + "}";
/*    */   }
/*    */ 
/*    */   public static String getListJson(List list) {
/* 33 */     if (list == null) {
/* 34 */       return getErrorJson("list is null");
/*    */     }
/* 36 */     String listStr = JSONArray.fromObject(list).toString();
/* 37 */     return "{\"result\":1,\"data\":" + listStr + "}";
/*    */   }
/*    */ 
/*    */   public static String getErrorJson(String message)
/*    */   {
/* 42 */     return "{\"result\":0,\"message\":\"" + message + "\"}";
/*    */   }
/*    */ 
/*    */   public static String getSuccessJson(String message)
/*    */   {
/* 48 */     return "{\"result\":1,\"message\":\"" + message + "\"}";
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.framework.util.JsonMessageUtil
 * JD-Core Version:    0.6.0
 */