/*    */ package com.enation.app.base.core.plugin.shortmsg;
/*    */ 
/*    */ import com.enation.app.base.core.model.ShortMsg;
/*    */ import com.enation.framework.plugin.AutoRegisterPluginsBundle;
/*    */ import com.enation.framework.plugin.IPlugin;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ public class ShortMsgPluginBundle extends AutoRegisterPluginsBundle
/*    */ {
/*    */   public String getName()
/*    */   {
/* 19 */     return "短消息插件桩";
/*    */   }
/*    */ 
/*    */   public List<ShortMsg> getMessageList() {
/* 23 */     List msgList = new ArrayList();
/* 24 */     List plugins = getPlugins();
/*    */ 
/* 26 */     if (plugins != null) {
/* 27 */       for (IPlugin plugin : plugins) {
/* 28 */         if ((plugin instanceof IShortMessageEvent)) {
/* 29 */           IShortMessageEvent event = (IShortMessageEvent)plugin;
/* 30 */           List somemsgList = event.getMessage();
/* 31 */           msgList.addAll(somemsgList);
/*    */         }
/*    */       }
/*    */     }
/* 35 */     return msgList;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.app.base.core.plugin.shortmsg.ShortMsgPluginBundle
 * JD-Core Version:    0.6.0
 */