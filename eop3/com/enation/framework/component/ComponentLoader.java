/*    */ package com.enation.framework.component;
/*    */ 
/*    */ import com.enation.framework.component.context.ComponentContext;
/*    */ import com.enation.framework.plugin.AutoRegisterPlugin;
/*    */ import com.enation.framework.plugin.IPluginBundle;
/*    */ import java.util.List;
/*    */ import org.springframework.beans.BeansException;
/*    */ import org.springframework.beans.factory.config.BeanPostProcessor;
/*    */ 
/*    */ public class ComponentLoader
/*    */   implements BeanPostProcessor
/*    */ {
/*    */   public Object postProcessAfterInitialization(Object bean, String arg1)
/*    */     throws BeansException
/*    */   {
/* 18 */     return bean;
/*    */   }
/*    */ 
/*    */   public Object postProcessBeforeInitialization(Object bean, String beanName)
/*    */     throws BeansException
/*    */   {
/*    */     AutoRegisterPlugin plugin;
/* 25 */     if ((bean instanceof AutoRegisterPlugin)) {
/* 26 */       plugin = (AutoRegisterPlugin)bean;
/* 27 */       if (plugin.getBundleList() != null)
/*    */       {
/* 32 */         List pluginBundelList = plugin.getBundleList();
/* 33 */         for (IPluginBundle bundle : pluginBundelList) {
/* 34 */           bundle.registerPlugin(plugin);
/*    */         }
/*    */ 
/*    */       }
/*    */ 
/*    */     }
/*    */ 
/* 42 */     if ((bean instanceof IComponent))
/*    */     {
/* 44 */       IComponent component = (IComponent)bean;
/* 45 */       ComponentView componentView = new ComponentView();
/* 46 */       componentView.setComponent(component);
/* 47 */       componentView.setComponentid(beanName);
/* 48 */       ComponentContext.registerComponent(componentView);
/*    */     }
/*    */ 
/* 51 */     return bean;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.framework.component.ComponentLoader
 * JD-Core Version:    0.6.0
 */