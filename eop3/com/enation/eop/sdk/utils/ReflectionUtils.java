/*     */ package com.enation.eop.sdk.utils;
/*     */ 
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.lang.reflect.ParameterizedType;
/*     */ import java.lang.reflect.Type;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import org.apache.commons.beanutils.PropertyUtils;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.util.Assert;
/*     */ 
/*     */ public class ReflectionUtils
/*     */ {
/*  34 */   private static Logger logger = LoggerFactory.getLogger(ReflectionUtils.class);
/*     */ 
/*     */   public static Object getFieldValue(Object object, String fieldName)
/*     */   {
/*  40 */     Field field = getDeclaredField(object, fieldName);
/*     */ 
/*  42 */     if (field == null) {
/*  43 */       throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + object + "]");
/*     */     }
/*  45 */     makeAccessible(field);
/*     */ 
/*  47 */     Object result = null;
/*     */     try {
/*  49 */       result = field.get(object);
/*     */     } catch (IllegalAccessException e) {
/*  51 */       logger.error("不可能抛出的异常{}", e.getMessage());
/*     */     }
/*  53 */     return result;
/*     */   }
/*     */ 
/*     */   public static void setFieldValue(Object object, String fieldName, Object value)
/*     */   {
/*  60 */     Field field = getDeclaredField(object, fieldName);
/*     */ 
/*  62 */     if (field == null) {
/*  63 */       throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + object + "]");
/*     */     }
/*  65 */     makeAccessible(field);
/*     */     try
/*     */     {
/*  68 */       field.set(object, value);
/*     */     } catch (IllegalAccessException e) {
/*  70 */       logger.error("不可能抛出的异常:{}", e.getMessage());
/*     */     }
/*     */   }
/*     */ 
/*     */   public static Object invokeMethod(Object object, String methodName, Class<?>[] parameterTypes, Object[] parameters)
/*     */   {
/*  79 */     Method method = getDeclaredMethod(object, methodName, parameterTypes);
/*  80 */     if (method == null) {
/*  81 */       throw new IllegalArgumentException("Could not find method [" + methodName + "] on target [" + object + "]");
/*     */     }
/*  83 */     method.setAccessible(true);
/*     */     try
/*     */     {
/*  86 */       return method.invoke(object, parameters); } catch (Exception e) {
/*     */     }
/*  88 */     throw convertReflectionExceptionToUnchecked(e);
/*     */   }
/*     */ 
/*     */   protected static Field getDeclaredField(Object object, String fieldName)
/*     */   {
/*  98 */     Assert.notNull(object, "object不能为空");
/*  99 */     Assert.hasText(fieldName, "fieldName");
/* 100 */     for (Class superClass = object.getClass(); superClass != Object.class; superClass = superClass.getSuperclass())
/*     */       try
/*     */       {
/* 103 */         return superClass.getDeclaredField(fieldName);
/*     */       }
/*     */       catch (NoSuchFieldException e)
/*     */       {
/*     */       }
/* 108 */     return null;
/*     */   }
/*     */ 
/*     */   protected static void makeAccessible(Field field)
/*     */   {
/* 115 */     if ((!Modifier.isPublic(field.getModifiers())) || (!Modifier.isPublic(field.getDeclaringClass().getModifiers())))
/* 116 */       field.setAccessible(true);
/*     */   }
/*     */ 
/*     */   protected static Method getDeclaredMethod(Object object, String methodName, Class<?>[] parameterTypes)
/*     */   {
/* 126 */     Assert.notNull(object, "object不能为空");
/*     */ 
/* 128 */     for (Class superClass = object.getClass(); superClass != Object.class; superClass = superClass.getSuperclass())
/*     */       try
/*     */       {
/* 131 */         return superClass.getDeclaredMethod(methodName, parameterTypes);
/*     */       }
/*     */       catch (NoSuchMethodException e)
/*     */       {
/*     */       }
/* 136 */     return null;
/*     */   }
/*     */ 
/*     */   public static <T> Class<T> getSuperClassGenricType(Class clazz)
/*     */   {
/* 150 */     return getSuperClassGenricType(clazz, 0);
/*     */   }
/*     */ 
/*     */   public static Class getSuperClassGenricType(Class clazz, int index)
/*     */   {
/* 166 */     Type genType = clazz.getGenericSuperclass();
/*     */ 
/* 168 */     if (!(genType instanceof ParameterizedType)) {
/* 169 */       logger.warn(clazz.getSimpleName() + "'s superclass not ParameterizedType");
/* 170 */       return Object.class;
/*     */     }
/*     */ 
/* 173 */     Type[] params = ((ParameterizedType)genType).getActualTypeArguments();
/*     */ 
/* 175 */     if ((index >= params.length) || (index < 0)) {
/* 176 */       logger.warn("Index: " + index + ", Size of " + clazz.getSimpleName() + "'s Parameterized Type: " + params.length);
/*     */ 
/* 178 */       return Object.class;
/*     */     }
/* 180 */     if (!(params[index] instanceof Class)) {
/* 181 */       logger.warn(clazz.getSimpleName() + " not set the actual class on superclass generic parameter");
/* 182 */       return Object.class;
/*     */     }
/*     */ 
/* 185 */     return (Class)params[index];
/*     */   }
/*     */ 
/*     */   public static Class getInterfaceClassGenricType(Class clazz, int index)
/*     */   {
/* 191 */     Type[] genTypes = clazz.getGenericInterfaces();
/* 192 */     if ((genTypes == null) || (genTypes.length == 0)) {
/* 193 */       logger.warn(clazz.getSimpleName() + "'s not impl interface ");
/* 194 */       return Object.class;
/*     */     }
/*     */ 
/* 198 */     if (!(genTypes[0] instanceof ParameterizedType)) {
/* 199 */       logger.warn(clazz.getSimpleName() + "'s superclass not ParameterizedType");
/* 200 */       return Object.class;
/*     */     }
/*     */ 
/* 203 */     Type[] params = ((ParameterizedType)genTypes[0]).getActualTypeArguments();
/*     */ 
/* 205 */     if ((index >= params.length) || (index < 0)) {
/* 206 */       logger.warn("Index: " + index + ", Size of " + clazz.getSimpleName() + "'s Parameterized Type: " + params.length);
/*     */ 
/* 208 */       return Object.class;
/*     */     }
/* 210 */     if (!(params[index] instanceof Class)) {
/* 211 */       logger.warn(clazz.getSimpleName() + " not set the actual class on superclass generic parameter");
/* 212 */       return Object.class;
/*     */     }
/*     */ 
/* 215 */     return (Class)params[index];
/*     */   }
/*     */ 
/*     */   public static List convertElementPropertyToList(Collection collection, String propertyName)
/*     */   {
/* 226 */     List list = new ArrayList();
/*     */     try
/*     */     {
/* 229 */       for (i$ = collection.iterator(); i$.hasNext(); ) { Object obj = i$.next();
/* 230 */         list.add(PropertyUtils.getProperty(obj, propertyName));
/*     */       }
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*     */       Iterator i$;
/* 233 */       throw convertReflectionExceptionToUnchecked(e);
/*     */     }
/*     */ 
/* 236 */     return list;
/*     */   }
/*     */ 
/*     */   public static RuntimeException convertReflectionExceptionToUnchecked(Exception e)
/*     */   {
/* 276 */     if (((e instanceof IllegalAccessException)) || ((e instanceof IllegalArgumentException)) || ((e instanceof NoSuchMethodException)))
/*     */     {
/* 278 */       return new IllegalArgumentException("Reflection Exception.", e);
/* 279 */     }if ((e instanceof InvocationTargetException))
/* 280 */       return new RuntimeException("Reflection Exception.", ((InvocationTargetException)e).getTargetException());
/* 281 */     if ((e instanceof RuntimeException)) {
/* 282 */       return (RuntimeException)e;
/*     */     }
/* 284 */     return new RuntimeException("Unexpected Checked Exception.", e);
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.eop.sdk.utils.ReflectionUtils
 * JD-Core Version:    0.6.0
 */