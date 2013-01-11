/*     */ package com.enation.framework.util;
/*     */ 
/*     */ import com.enation.framework.database.DynamicField;
/*     */ import com.enation.framework.database.NotDbField;
/*     */ import com.enation.framework.database.PrimaryKeyField;
/*     */ import java.io.PrintStream;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.apache.commons.beanutils.BeanUtils;
/*     */ 
/*     */ public class ReflectionUtil
/*     */ {
/*     */   public static Object invokeMethod(String className, String methodName, Object[] args)
/*     */   {
/*     */     try
/*     */     {
/*  22 */       Class serviceClass = Class.forName(className);
/*  23 */       Object service = serviceClass.newInstance();
/*     */ 
/*  25 */       Class[] argsClass = new Class[args.length];
/*  26 */       int i = 0; for (int j = args.length; i < j; i++) {
/*  27 */         argsClass[i] = args[i].getClass();
/*     */       }
/*     */ 
/*  30 */       Method method = serviceClass.getMethod(methodName, argsClass);
/*  31 */       return method.invoke(service, args);
/*     */     }
/*     */     catch (SecurityException e) {
/*  34 */       e.printStackTrace();
/*     */     } catch (NoSuchMethodException e) {
/*  36 */       e.printStackTrace();
/*     */     } catch (IllegalArgumentException e) {
/*  38 */       e.printStackTrace();
/*     */     } catch (IllegalAccessException e) {
/*  40 */       e.printStackTrace();
/*     */     } catch (InvocationTargetException e) {
/*  42 */       e.printStackTrace();
/*     */     } catch (ClassNotFoundException e) {
/*  44 */       e.printStackTrace();
/*     */     } catch (InstantiationException e) {
/*  46 */       e.printStackTrace();
/*     */     }
/*     */ 
/*  49 */     return null;
/*     */   }
/*     */ 
/*     */   public static Object newInstance(String className, Object[] _args)
/*     */   {
/*     */     try {
/*  55 */       Class[] argsClass = new Class[_args.length];
/*     */ 
/*  57 */       int i = 0; for (int j = _args.length; i < j; i++)
/*     */       {
/*  59 */         if (_args[i] == null) {
/*  60 */           argsClass[i] = null;
/*     */         }
/*     */         else
/*     */         {
/*  64 */           argsClass[i] = _args[i].getClass();
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*  69 */       Class newoneClass = Class.forName(className);
/*  70 */       Constructor cons = newoneClass.getConstructor(argsClass);
/*     */ 
/*  72 */       Object obj = cons.newInstance(_args);
/*  73 */       return obj;
/*     */     } catch (ClassNotFoundException e) {
/*  75 */       e.printStackTrace();
/*     */     } catch (SecurityException e) {
/*  77 */       e.printStackTrace();
/*     */     } catch (NoSuchMethodException e) {
/*  79 */       e.printStackTrace();
/*     */     } catch (IllegalArgumentException e) {
/*  81 */       e.printStackTrace();
/*     */     } catch (InstantiationException e) {
/*  83 */       e.printStackTrace();
/*     */     } catch (IllegalAccessException e) {
/*  85 */       e.printStackTrace();
/*     */     } catch (InvocationTargetException e) {
/*  87 */       e.printStackTrace();
/*     */     }
/*     */ 
/*  91 */     return null;
/*     */   }
/*     */ 
/*     */   public static Map po2Map(Object po)
/*     */   {
/* 102 */     Map poMap = new HashMap();
/* 103 */     Map map = new HashMap();
/*     */     try {
/* 105 */       map = BeanUtils.describe(po);
/*     */     } catch (Exception ex) {
/*     */     }
/* 108 */     Object[] keyArray = map.keySet().toArray();
/* 109 */     for (int i = 0; i < keyArray.length; i++) {
/* 110 */       String str = keyArray[i].toString();
/* 111 */       if ((str == null) || (str.equals("class")) || 
/* 112 */         (map.get(str) == null)) continue;
/* 113 */       poMap.put(str, map.get(str));
/*     */     }
/*     */ 
/* 118 */     Method[] ms = po.getClass().getMethods();
/* 119 */     for (Method m : ms) {
/* 120 */       String name = m.getName();
/*     */ 
/* 122 */       if (((!name.startsWith("get")) && (!name.startsWith("is"))) || (
/* 123 */         (m.getAnnotation(NotDbField.class) == null) && (m.getAnnotation(PrimaryKeyField.class) == null))) continue;
/* 124 */       poMap.remove(getFieldName(name));
/*     */     }
/*     */ 
/* 133 */     if ((po instanceof DynamicField)) {
/* 134 */       DynamicField dynamicField = (DynamicField)po;
/* 135 */       Map fields = dynamicField.getFields();
/* 136 */       poMap.putAll(fields);
/*     */     }
/* 138 */     return poMap;
/*     */   }
/*     */ 
/*     */   private static String getFieldName(String methodName)
/*     */   {
/* 143 */     methodName = methodName.substring(3);
/* 144 */     methodName = methodName.substring(0, 1).toLowerCase() + methodName.substring(1);
/* 145 */     return methodName;
/*     */   }
/*     */ 
/*     */   public static void main(String[] args) {
/* 149 */     String methodName = "getWidgetList";
/* 150 */     methodName = methodName.substring(3);
/* 151 */     methodName = methodName.substring(0, 1).toLowerCase() + methodName.substring(1);
/* 152 */     System.out.println(methodName);
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.framework.util.ReflectionUtil
 * JD-Core Version:    0.6.0
 */