/*     */ package com.enation.app.shop.component.goodscore.widget.goods.list;
/*     */ 
/*     */ import com.enation.app.base.core.service.ISettingService;
/*     */ import com.enation.app.shop.core.model.Attribute;
/*     */ import com.enation.app.shop.core.model.support.GoodsView;
/*     */ import com.enation.app.shop.core.service.IGoodsTypeManager;
/*     */ import com.enation.app.shop.core.service.ITagManager;
/*     */ import com.enation.eop.sdk.widget.AbstractWidget;
/*     */ import com.enation.framework.context.webcontext.ThreadContextHolder;
/*     */ import com.enation.framework.util.RequestUtil;
/*     */ import com.enation.framework.util.StringUtil;
/*     */ import com.sun.xml.messaging.saaj.util.ByteOutputStream;
/*     */ import freemarker.template.Configuration;
/*     */ import freemarker.template.DefaultObjectWrapper;
/*     */ import freemarker.template.Template;
/*     */ import freemarker.template.TemplateException;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.io.Writer;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import net.sf.json.JSONArray;
/*     */ import net.sf.json.JSONObject;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.springframework.context.annotation.Scope;
/*     */ import org.springframework.stereotype.Component;
/*     */ 
/*     */ @Component("goods_list")
/*     */ @Scope("prototype")
/*     */ public class GoodsListWidget extends AbstractWidget
/*     */ {
/*     */   private GoodsDataProvider goodsDataProvider;
/*     */   private ITagManager tagManager;
/*     */   private IGoodsTypeManager goodsTypeManager;
/*     */   private ISettingService settingService;
/*     */ 
/*     */   protected String getSplitParam(int index)
/*     */   {
/*  58 */     HttpServletRequest httpRequest = ThreadContextHolder.getHttpRequest();
/*  59 */     String url = RequestUtil.getRequestUrl(httpRequest);
/*  60 */     String[] params = StringUtils.split(url, "-");
/*  61 */     url = params[(params.length - 1)];
/*  62 */     int pos = url.indexOf(46);
/*  63 */     if (pos > 0)
/*  64 */       params[(params.length - 1)] = url.substring(0, pos);
/*  65 */     if (params.length > index)
/*  66 */       return params[index];
/*  67 */     return null;
/*     */   }
/*     */ 
/*     */   protected void display(Map<String, String> params)
/*     */   {
/*  72 */     String widgetid = "widget_" + (String)params.get("widgetid");
/*  73 */     putData("widgetid", widgetid);
/*  74 */     String settingJson = (String)params.get("setting");
/*     */ 
/*  76 */     String catid = (String)params.get("catid");
/*     */ 
/*  78 */     Settings setting = (Settings)JSONObject.toBean(JSONObject.fromObject(settingJson), Settings.class);
/*  79 */     putData("setting", null);
/*  80 */     if (setting != null)
/*     */     {
/*  82 */       if (setting.getThumbnail_pic_width() == null) {
/*  83 */         String defaultWidth = this.settingService.getSetting("photo", "thumbnail_pic_width");
/*  84 */         defaultWidth = defaultWidth == null ? "100" : defaultWidth;
/*  85 */         setting.setThumbnail_pic_width(defaultWidth + "px");
/*     */       }
/*     */ 
/*  88 */       if (setting.getThumbnail_pic_height() == null) {
/*  89 */         String defaultHeight = this.settingService.getSetting("photo", "thumbnail_pic_height");
/*  90 */         defaultHeight = defaultHeight == null ? "100" : defaultHeight;
/*  91 */         setting.setThumbnail_pic_height(defaultHeight + "px");
/*     */       }
/*     */ 
/*  95 */       setting.setColumnwidth(Integer.valueOf(99 / Integer.valueOf(setting.getColumnnum().intValue()).intValue()));
/*     */ 
/*  97 */       putData("setting", setting);
/*     */ 
/*  99 */       String distype = setting.getType();
/*     */ 
/* 101 */       if ((distype == null) || ("default".equals(distype))) {
/* 102 */         setPageName("GoodsList");
/* 103 */         String termJson = (String)params.get("term");
/* 104 */         putData("goodsList", null);
/* 105 */         Term term = (Term)JSONObject.toBean(JSONObject.fromObject(termJson), Term.class);
/*     */ 
/* 107 */         if (term != null)
/*     */         {
/* 109 */           if (!StringUtil.isEmpty(catid)) {
/* 110 */             term.setCatid(catid);
/*     */           }
/* 112 */           List goodsList = this.goodsDataProvider.list(term, setting.getGoodsNum().intValue());
/* 113 */           putAttrText(goodsList);
/* 114 */           putData("goodsList", goodsList);
/*     */         }
/*     */         else
/*     */         {
/* 119 */           term = new Term();
/* 120 */           term.setCatid(getSplitParam(1));
/* 121 */           term.setTagid(getSplitParam(2));
/* 122 */           if (!StringUtil.isEmpty(catid)) {
/* 123 */             term.setCatid(catid);
/*     */           }
/* 125 */           List goodsList = this.goodsDataProvider.list(term, setting.getGoodsNum().intValue());
/* 126 */           putAttrText(goodsList);
/* 127 */           putData("goodsList", goodsList);
/* 128 */           params.put("catid", term.getCatid());
/* 129 */           params.put("tagid", term.getTagid());
/* 130 */           putData("catid", term.getCatid());
/* 131 */           putData("tagid", term.getTagid());
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 136 */       if ((distype == null) || ("morediv".equals(distype))) {
/* 137 */         String tabsjson = (String)params.get("tabs");
/* 138 */         JSONArray configArray = JSONArray.fromObject(tabsjson);
/* 139 */         Collection configList = JSONArray.toCollection(configArray, Tab.class);
/* 140 */         Iterator tabIterator = configList.iterator();
/* 141 */         int i = 0;
/* 142 */         while (tabIterator.hasNext()) {
/* 143 */           Tab tab = (Tab)tabIterator.next();
/* 144 */           if (i == 0) tab.setSelected(true); else {
/* 145 */             tab.setSelected(false);
/*     */           }
/* 147 */           tab.setId(widgetid + "_" + i);
/*     */ 
/* 149 */           List goodsList = this.goodsDataProvider.list(tab.getTerm(), setting.getGoodsNum().intValue());
/* 150 */           tab.setGoodsList(goodsList);
/* 151 */           i++;
/*     */         }
/* 153 */         putData("tabList", configList);
/* 154 */         setPageName("MoreDivList");
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void putAttrText(List<GoodsView> goodsList)
/*     */   {
/* 171 */     if ((goodsList == null) || (goodsList.size() == 0)) return;
/* 172 */     GoodsView tempgoods = (GoodsView)goodsList.get(0);
/* 173 */     List attrList = this.goodsTypeManager.getAttrListByTypeId(tempgoods.getType_id().intValue());
/*     */ 
/* 175 */     for (GoodsView goods : goodsList)
/* 176 */       putGoodsAttrText(goods, attrList);
/*     */   }
/*     */ 
/*     */   private void putGoodsAttrText(GoodsView goods, List<Attribute> attrList)
/*     */   {
/* 182 */     int i = 1;
/*     */ 
/* 184 */     for (Attribute attr : attrList) {
/* 185 */       String value = "" + goods.getPropMap().get(new StringBuilder().append("p").append(i).toString());
/* 186 */       attr.setValue(value);
/* 187 */       goods.getPropMap().put("p" + i + "_text", attr.getValStr());
/* 188 */       i++;
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void config(Map<String, String> params) {
/* 193 */     List tagList = this.tagManager.list();
/* 194 */     putData("tagList", tagList);
/*     */   }
/*     */ 
/*     */   public GoodsDataProvider getGoodsDataProvider() {
/* 198 */     return this.goodsDataProvider;
/*     */   }
/*     */ 
/*     */   public void setGoodsDataProvider(GoodsDataProvider goodsDataProvider) {
/* 202 */     this.goodsDataProvider = goodsDataProvider;
/*     */   }
/*     */ 
/*     */   public void test() {
/* 206 */     Configuration cfg = new Configuration();
/* 207 */     cfg.setClassForTemplateLoading(getClass(), "");
/* 208 */     cfg.setObjectWrapper(new DefaultObjectWrapper());
/* 209 */     Map goods = new HashMap();
/*     */ 
/* 211 */     List list = new ArrayList();
/* 212 */     list.add("1");
/* 213 */     list.add("2");
/* 214 */     list.add("3");
/* 215 */     goods.put("nlist", list);
/*     */     try
/*     */     {
/* 218 */       Template temp = cfg.getTemplate("goodslist.html");
/* 219 */       ByteOutputStream stream = new ByteOutputStream();
/* 220 */       Writer out = new OutputStreamWriter(stream);
/* 221 */       temp.process(goods, out);
/* 222 */       out.flush();
/* 223 */       String name = getClass().getName();
/* 224 */       name = name.substring(name.lastIndexOf(46) + 1);
/*     */     } catch (IOException e) {
/* 226 */       e.printStackTrace();
/*     */     } catch (TemplateException e) {
/* 228 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setTagManager(ITagManager tagManager) {
/* 233 */     this.tagManager = tagManager;
/*     */   }
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/* 240 */     String config_str = "[{ title:'类别1',term:{tag_id:1}},{ title:'类别2',term:{tag_id:1}}]";
/* 241 */     JSONArray array = JSONArray.fromObject(config_str);
/* 242 */     Collection list = JSONArray.toCollection(array, Map.class);
/*     */ 
/* 244 */     Iterator iterator = list.iterator();
/*     */     Map map;
/* 245 */     while (iterator.hasNext())
/* 246 */       map = (Map)iterator.next();
/*     */   }
/*     */ 
/*     */   public ISettingService getSettingService()
/*     */   {
/* 251 */     return this.settingService;
/*     */   }
/*     */ 
/*     */   public void setSettingService(ISettingService settingService) {
/* 255 */     this.settingService = settingService;
/*     */   }
/*     */ 
/*     */   public ITagManager getTagManager() {
/* 259 */     return this.tagManager;
/*     */   }
/*     */ 
/*     */   public IGoodsTypeManager getGoodsTypeManager() {
/* 263 */     return this.goodsTypeManager;
/*     */   }
/*     */ 
/*     */   public void setGoodsTypeManager(IGoodsTypeManager goodsTypeManager) {
/* 267 */     this.goodsTypeManager = goodsTypeManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.goodscore.widget.goods.list.GoodsListWidget
 * JD-Core Version:    0.6.0
 */