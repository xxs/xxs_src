/*    */ package com.enation.framework.context.spring;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import org.springframework.beans.BeansException;
/*    */ import org.springframework.beans.factory.config.BeanPostProcessor;
/*    */ import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
/*    */ import org.springframework.beans.factory.support.BeanDefinitionRegistry;
/*    */ import org.springframework.beans.factory.xml.ResourceEntityResolver;
/*    */ import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
/*    */ import org.springframework.context.ApplicationContext;
/*    */ import org.springframework.context.ApplicationContextAware;
/*    */ import org.springframework.context.ConfigurableApplicationContext;
/*    */ 
/*    */ public class SpringContextHolder
/*    */   implements ApplicationContextAware
/*    */ {
/*    */   private static ConfigurableApplicationContext applicationContext;
/*    */ 
/*    */   public void setApplicationContext(ApplicationContext applicationContext)
/*    */   {
/* 27 */     applicationContext = (ConfigurableApplicationContext)applicationContext;
/*    */   }
/*    */ 
/*    */   public static ApplicationContext getApplicationContext()
/*    */   {
/* 36 */     checkApplicationContext();
/* 37 */     return applicationContext;
/*    */   }
/*    */ 
/*    */   public static <T> T getBean(String name)
/*    */   {
/* 45 */     checkApplicationContext();
/* 46 */     return applicationContext.getBean(name);
/*    */   }
/*    */ 
/*    */   public static <T> T getBean(Class<T> clazz)
/*    */   {
/* 54 */     checkApplicationContext();
/* 55 */     return applicationContext.getBeansOfType(clazz);
/*    */   }
/*    */ 
/*    */   private static void checkApplicationContext() {
/* 59 */     if (applicationContext == null)
/* 60 */       throw new IllegalStateException("applicaitonContext未注入,请在applicationContext.xml中定义SpringContextUtil");
/*    */   }
/*    */ 
/*    */   public static void loadbean() {
/* 64 */     XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader((BeanDefinitionRegistry)applicationContext.getBeanFactory());
/*    */ 
/* 66 */     beanDefinitionReader.setResourceLoader(applicationContext);
/* 67 */     beanDefinitionReader.setEntityResolver(new ResourceEntityResolver(applicationContext));
/*    */     try
/*    */     {
/* 70 */       beanDefinitionReader.loadBeanDefinitions(applicationContext.getResources("classpath:newspring/newApplicationContext.xml"));
/* 71 */       addBeanPostProcessor();
/*    */     } catch (BeansException e) {
/* 73 */       e.printStackTrace();
/*    */     } catch (IOException e) {
/* 75 */       e.printStackTrace();
/*    */     }
/*    */   }
/*    */ 
/*    */   private static void addBeanPostProcessor() {
/* 79 */     String[] postProcessorNames = applicationContext.getBeanFactory().getBeanNamesForType(BeanPostProcessor.class, true, false);
/*    */ 
/* 81 */     for (String postProcessorName : postProcessorNames)
/* 82 */       applicationContext.getBeanFactory().addBeanPostProcessor((BeanPostProcessor)applicationContext.getBean(postProcessorName));
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.framework.context.spring.SpringContextHolder
 * JD-Core Version:    0.6.0
 */