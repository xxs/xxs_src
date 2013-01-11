/*     */ package com.enation.app.shop.core.action.backend;
/*     */ 
/*     */ import com.enation.app.shop.core.model.ArticleCat;
/*     */ import com.enation.app.shop.core.service.IArticleCatManager;
/*     */ import com.enation.app.shop.core.service.impl.ArticleCatRuntimeException;
/*     */ import com.enation.framework.action.WWAction;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class ArticleCatAction extends WWAction
/*     */ {
/*     */   private IArticleCatManager articleCatManager;
/*     */   private List catList;
/*     */   private ArticleCat cat;
/*     */   private int cat_id;
/*     */   private int[] cat_ids;
/*     */   private int[] cat_sorts;
/*     */ 
/*     */   public String cat_list()
/*     */   {
/*  26 */     this.catList = this.articleCatManager.listChildById(Integer.valueOf(0));
/*  27 */     return "cat_list";
/*     */   }
/*     */ 
/*     */   public String add()
/*     */   {
/*  33 */     this.catList = this.articleCatManager.listChildById(Integer.valueOf(0));
/*  34 */     return "cat_add";
/*     */   }
/*     */ 
/*     */   public String edit()
/*     */   {
/*  40 */     this.catList = this.articleCatManager.listChildById(Integer.valueOf(0));
/*  41 */     this.cat = this.articleCatManager.getById(this.cat_id);
/*  42 */     return "cat_edit";
/*     */   }
/*     */ 
/*     */   public String saveAdd()
/*     */   {
/*     */     try
/*     */     {
/*  49 */       this.articleCatManager.saveAdd(this.cat);
/*     */     } catch (ArticleCatRuntimeException ex) {
/*  51 */       this.msgs.add("同级文章栏目不能同名");
/*  52 */       this.urls.put("分类列表", "articleCat!cat_list.do");
/*  53 */       return "message";
/*     */     }
/*  55 */     this.msgs.add("文章栏目添加成功");
/*  56 */     this.urls.put("分类列表", "articleCat!cat_list.do");
/*  57 */     return "message";
/*     */   }
/*     */ 
/*     */   public String saveEdit()
/*     */   {
/*     */     try
/*     */     {
/*  65 */       this.articleCatManager.update(this.cat);
/*     */     } catch (ArticleCatRuntimeException ex) {
/*  67 */       this.msgs.add("同级文章栏目不能同名");
/*  68 */       this.urls.put("分类列表", "articleCat!cat_list.do");
/*  69 */       return "message";
/*     */     }
/*  71 */     this.msgs.add("文章栏目修改成功");
/*  72 */     this.urls.put("分类列表", "articleCat!cat_list.do");
/*  73 */     return "message";
/*     */   }
/*     */ 
/*     */   public String delete()
/*     */   {
/*  80 */     int r = this.articleCatManager.delete(this.cat_id);
/*     */ 
/*  82 */     if (r == 0)
/*  83 */       this.json = "{'result':0,'message':'删除成功'}";
/*  84 */     else if (r == 1) {
/*  85 */       this.json = "{'result':1,'message':'此类别下存在子类别或者文章不能删除!'}";
/*     */     }
/*     */ 
/*  88 */     return "json_message";
/*     */   }
/*     */ 
/*     */   public String saveSort()
/*     */   {
/*  93 */     this.articleCatManager.saveSort(this.cat_ids, this.cat_sorts);
/*  94 */     this.json = "{'result':0,'message':'保存成功'}";
/*  95 */     return "json_message";
/*     */   }
/*     */ 
/*     */   public List getCatList()
/*     */   {
/* 100 */     return this.catList;
/*     */   }
/*     */ 
/*     */   public void setCatList(List catList)
/*     */   {
/* 105 */     this.catList = catList;
/*     */   }
/*     */ 
/*     */   public ArticleCat getCat()
/*     */   {
/* 111 */     return this.cat;
/*     */   }
/*     */ 
/*     */   public void setCat(ArticleCat cat)
/*     */   {
/* 116 */     this.cat = cat;
/*     */   }
/*     */ 
/*     */   public IArticleCatManager getArticleCatManager()
/*     */   {
/* 122 */     return this.articleCatManager;
/*     */   }
/*     */ 
/*     */   public void setArticleCatManager(IArticleCatManager articleCatManager)
/*     */   {
/* 127 */     this.articleCatManager = articleCatManager;
/*     */   }
/*     */ 
/*     */   public int getCat_id()
/*     */   {
/* 132 */     return this.cat_id;
/*     */   }
/*     */ 
/*     */   public void setCat_id(int cat_id)
/*     */   {
/* 137 */     this.cat_id = cat_id;
/*     */   }
/*     */ 
/*     */   public int[] getCat_ids()
/*     */   {
/* 142 */     return this.cat_ids;
/*     */   }
/*     */ 
/*     */   public void setCat_ids(int[] cat_ids)
/*     */   {
/* 147 */     this.cat_ids = cat_ids;
/*     */   }
/*     */ 
/*     */   public int[] getCat_sorts()
/*     */   {
/* 152 */     return this.cat_sorts;
/*     */   }
/*     */ 
/*     */   public void setCat_sorts(int[] cat_sorts)
/*     */   {
/* 157 */     this.cat_sorts = cat_sorts;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.action.backend.ArticleCatAction
 * JD-Core Version:    0.6.0
 */