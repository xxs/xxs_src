/*     */ package com.enation.framework.component.context;
/*     */ 
/*     */ import com.enation.eop.sdk.context.EopSetting;
/*     */ import com.enation.framework.component.ComponentView;
/*     */ import com.enation.framework.component.IComponent;
/*     */ import com.enation.framework.component.PluginView;
/*     */ import com.enation.framework.component.WidgetView;
/*     */ import com.enation.framework.util.FileUtil;
/*     */ import com.enation.framework.util.StringUtil;
/*     */ import com.enation.framework.util.XMLUtil;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.xml.parsers.DocumentBuilder;
/*     */ import javax.xml.parsers.DocumentBuilderFactory;
/*     */ import javax.xml.parsers.ParserConfigurationException;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.NodeList;
/*     */ import org.xml.sax.SAXException;
/*     */ 
/*     */ public class ComponentContext
/*     */ {
/*  40 */   private static List<ComponentView> componentList = new ArrayList();
/*  41 */   private static Map<String, Boolean> siteComponentState = new HashMap();
/*     */ 
/*     */   public static void siteComponentStart(int userid, int siteid)
/*     */   {
/*  50 */     siteComponentState.put(userid + "_" + siteid, Boolean.TRUE);
/*     */   }
/*     */ 
/*     */   public static boolean getSiteComponentState(int userid, int siteid)
/*     */   {
/*  63 */     Boolean state = (Boolean)siteComponentState.get(userid + "_" + siteid);
/*  64 */     return state == null ? false : state.booleanValue();
/*     */   }
/*     */ 
/*     */   public static void registerComponent(ComponentView componentView)
/*     */   {
/*     */     try
/*     */     {
/*  72 */       loadComponent(componentView);
/*  73 */       componentList.add(componentView);
/*     */     }
/*     */     catch (SAXException e) {
/*  76 */       e.printStackTrace();
/*     */     }
/*     */     catch (IOException e) {
/*  79 */       e.printStackTrace();
/*     */     }
/*     */     catch (ParserConfigurationException e) {
/*  82 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   public static List<ComponentView> getComponents()
/*     */   {
/*  90 */     return componentList;
/*     */   }
/*     */ 
/*     */   private static void loadComponent(ComponentView componentView)
/*     */     throws SAXException, IOException, ParserConfigurationException
/*     */   {
/* 106 */     IComponent component = componentView.getComponent();
/*     */ 
/* 108 */     String path = component.getClass().getPackage().getName();
/* 109 */     path = path.replace('.', '/') + "/component.xml";
/*     */ 
/* 112 */     DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
/*     */ 
/* 114 */     DocumentBuilder builder = factory.newDocumentBuilder();
/* 115 */     Document doc = builder.parse(FileUtil.getResourceAsStream(path));
/* 116 */     Element componentEl = (Element)doc.getFirstChild();
/*     */ 
/* 118 */     String needVersion = componentEl.getAttribute("javashop_version");
/* 119 */     String currentVersion = EopSetting.VERSION;
/*     */ 
/* 122 */     componentView.setName(componentEl.getAttribute("name"));
/* 123 */     componentView.setAuthor(componentEl.getAttribute("author"));
/* 124 */     componentView.setVersion(componentEl.getAttribute("version"));
/* 125 */     componentView.setJavashop_version(needVersion);
/* 126 */     componentView.setDescription(componentEl.getAttribute("description"));
/*     */ 
/* 128 */     if (!versionLargerThen(currentVersion, needVersion))
/*     */     {
/* 130 */       componentView.setInstall_state(2);
/* 131 */       componentView.setError_message("当前的Javashop版本无法安装此组件，需要的Javashop版本[" + needVersion + "] ，当前版本[" + currentVersion + "]");
/*     */     }
/*     */ 
/* 136 */     Element pluginsEl = XMLUtil.getChildByTagName(componentEl, "plugins");
/* 137 */     Element widgetsEl = XMLUtil.getChildByTagName(componentEl, "widgets");
/*     */ 
/* 140 */     if (pluginsEl != null)
/*     */     {
/* 146 */       NodeList pluginNodeList = pluginsEl.getElementsByTagName("plugin");
/*     */ 
/* 148 */       if (pluginNodeList != null)
/*     */       {
/* 150 */         int length = pluginNodeList.getLength();
/* 151 */         for (int i = 0; i < length; i++) {
/* 152 */           Element pluginEl = (Element)pluginNodeList.item(i);
/* 153 */           String name = pluginEl.getAttribute("name");
/* 154 */           String pluginBeanid = pluginEl.getAttribute("id");
/*     */ 
/* 157 */           PluginView pluginView = new PluginView();
/* 158 */           pluginView.setId(pluginBeanid);
/* 159 */           pluginView.setName(name);
/*     */ 
/* 164 */           NodeList bundleList = pluginEl.getElementsByTagName("bundle");
/* 165 */           if (bundleList != null) {
/* 166 */             int bundleLength = bundleList.getLength();
/* 167 */             for (int j = 0; j < bundleLength; j++) {
/* 168 */               Element bundleEl = (Element)bundleList.item(j);
/* 169 */               String beanid = bundleEl.getAttribute("id");
/* 170 */               pluginView.addBundle(beanid);
/*     */             }
/*     */ 
/*     */           }
/*     */ 
/* 175 */           componentView.addPlugin(pluginView);
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 182 */     if (widgetsEl != null)
/*     */     {
/* 189 */       NodeList widgetNodeList = widgetsEl.getElementsByTagName("widget");
/* 190 */       if (widgetNodeList != null) {
/* 191 */         int length = widgetNodeList.getLength();
/* 192 */         for (int i = 0; i < length; i++) {
/* 193 */           Element widgetEl = (Element)widgetNodeList.item(i);
/* 194 */           String name = widgetEl.getAttribute("name");
/* 195 */           String id = widgetEl.getAttribute("id");
/* 196 */           WidgetView widgetView = new WidgetView();
/* 197 */           widgetView.setName(name);
/* 198 */           widgetView.setId(id);
/* 199 */           componentView.addWidget(widgetView);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private static boolean versionLargerThen(String ver1, String ver2)
/*     */   {
/* 216 */     if (StringUtil.isEmpty(ver1)) throw new IllegalArgumentException("ver1版本不能为空");
/* 217 */     if (StringUtil.isEmpty(ver2)) throw new IllegalArgumentException("ver2版本不能为空");
/* 218 */     if (ver1.length() != ver2.length()) throw new IllegalArgumentException("ver2与ver2版本号格式不相同");
/* 219 */     if (ver1.length() != 5) throw new IllegalArgumentException("版本号格式不正确，应为如：2.1.0");
/*     */ 
/* 221 */     String[] ver1a = ver1.split("\\.");
/* 222 */     Integer ver1i = Integer.valueOf(Integer.valueOf(ver1a[0]).intValue() * 1000000 + Integer.valueOf(ver1a[1]).intValue() * 1000 + Integer.valueOf(ver1a[2]).intValue());
/* 223 */     String[] ver2a = ver2.split("\\.");
/* 224 */     Integer ver2i = Integer.valueOf(Integer.valueOf(ver2a[0]).intValue() * 1000000 + Integer.valueOf(ver2a[1]).intValue() * 1000 + Integer.valueOf(ver2a[2]).intValue());
/*     */ 
/* 226 */     return ver1i.intValue() >= ver2i.intValue();
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.framework.component.context.ComponentContext
 * JD-Core Version:    0.6.0
 */