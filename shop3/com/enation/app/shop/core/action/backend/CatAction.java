/*     */ package com.enation.app.shop.core.action.backend;
/*     */ 
/*     */ import com.enation.app.shop.core.model.Cat;
/*     */ import com.enation.app.shop.core.service.IGoodsCatManager;
/*     */ import com.enation.app.shop.core.service.IGoodsTypeManager;
/*     */ import com.enation.eop.sdk.database.PermssionRuntimeException;
/*     */ import com.enation.eop.sdk.utils.UploadUtil;
/*     */ import com.enation.framework.action.WWAction;
/*     */ import com.enation.framework.database.DBRuntimeException;
/*     */ import com.enation.framework.util.FileUtil;
/*     */ import com.enation.framework.util.JsonMessageUtil;
/*     */ import java.io.File;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class CatAction extends WWAction
/*     */ {
/*     */   private IGoodsCatManager goodsCatManager;
/*     */   private IGoodsTypeManager goodsTypeManager;
/*     */   protected List catList;
/*     */   private List typeList;
/*     */   private Cat cat;
/*     */   private File image;
/*     */   private String imageFileName;
/*     */   protected int cat_id;
/*     */   private int[] cat_ids;
/*     */   private int[] cat_sorts;
/*     */ 
/*     */   public String checkname()
/*     */   {
/*  38 */     if (this.goodsCatManager.checkname(this.cat.getName(), this.cat.getCat_id()))
/*  39 */       this.json = "{result:1}";
/*     */     else {
/*  41 */       this.json = "{result:0}";
/*     */     }
/*  43 */     return "json_message";
/*     */   }
/*     */ 
/*     */   public String list()
/*     */   {
/*  48 */     this.catList = this.goodsCatManager.listAllChildren(Integer.valueOf(0));
/*  49 */     return "cat_list";
/*     */   }
/*     */ 
/*     */   public String add()
/*     */   {
/*  55 */     this.typeList = this.goodsTypeManager.listAll();
/*  56 */     this.catList = this.goodsCatManager.listAllChildren(Integer.valueOf(0));
/*     */ 
/*  58 */     return "cat_add";
/*     */   }
/*     */ 
/*     */   public String edit()
/*     */   {
/*     */     try
/*     */     {
/*  65 */       this.typeList = this.goodsTypeManager.listAll();
/*  66 */       this.catList = this.goodsCatManager.listAllChildren(Integer.valueOf(0));
/*  67 */       this.cat = this.goodsCatManager.getById(this.cat_id);
/*  68 */       return "cat_edit";
/*     */     } catch (DBRuntimeException ex) {
/*  70 */       this.msgs.add("您查询的商品不存在");
/*  71 */     }return "message";
/*     */   }
/*     */ 
/*     */   public String saveAdd()
/*     */   {
/*  78 */     if (this.image != null) {
/*  79 */       if (FileUtil.isAllowUp(this.imageFileName)) {
/*  80 */         this.cat.setImage(UploadUtil.upload(this.image, this.imageFileName, "goodscat"));
/*     */       }
/*     */       else {
/*  83 */         this.msgs.add("不允许上传的文件格式，请上传gif,jpg,bmp格式文件。");
/*  84 */         return "message";
/*     */       }
/*     */     }
/*  87 */     this.cat.setGoods_count(Integer.valueOf(0));
/*  88 */     this.goodsCatManager.saveAdd(this.cat);
/*     */ 
/*  90 */     this.msgs.add("商品分类添加成功");
/*  91 */     this.urls.put("分类列表", "cat!list.do");
/*  92 */     return "message";
/*     */   }
/*     */ 
/*     */   public String saveEdit()
/*     */   {
/*  99 */     if (this.image != null)
/* 100 */       if (FileUtil.isAllowUp(this.imageFileName)) {
/* 101 */         this.cat.setImage(UploadUtil.upload(this.image, this.imageFileName, "goodscat"));
/*     */       }
/*     */       else {
/* 104 */         this.msgs.add("不允许上传的文件格式，请上传gif,jpg,bmp格式文件。");
/* 105 */         return "message";
/*     */       }
/*     */     try
/*     */     {
/* 109 */       if (this.cat.getParent_id().intValue() == 0) {
/* 110 */         this.goodsCatManager.update(this.cat);
/* 111 */         this.msgs.add("商品分类修改成功");
/* 112 */         this.urls.put("分类列表", "cat!list.do");
/* 113 */         return "message";
/*     */       }
/* 115 */       Cat targetCat = this.goodsCatManager.getById(this.cat.getParent_id().intValue());
/* 116 */       if ((this.cat.getParent_id().intValue() == this.cat.getCat_id().intValue()) || (targetCat.getParent_id().intValue() == this.cat.getCat_id().intValue())) {
/* 117 */         this.msgs.add("保存失败：上级分类不能选择当前分类或其子分类");
/* 118 */         this.urls.put("分类列表", "cat!list.do");
/* 119 */         return "message";
/*     */       }
/* 121 */       this.goodsCatManager.update(this.cat);
/* 122 */       this.msgs.add("商品分类修改成功");
/* 123 */       this.urls.put("分类列表", "cat!list.do");
/* 124 */       return "message";
/*     */     }
/*     */     catch (PermssionRuntimeException ex) {
/* 127 */       this.msgs.add("非法操作");
/* 128 */     }return "message";
/*     */   }
/*     */ 
/*     */   public String delete()
/*     */   {
/*     */     try
/*     */     {
/* 139 */       int r = this.goodsCatManager.delete(this.cat_id);
/*     */ 
/* 141 */       if (r == 0)
/* 142 */         this.json = "{'result':0,'message':'删除成功'}";
/* 143 */       else if (r == 1)
/* 144 */         this.json = "{'result':1,'message':'此类别下存在子类别不能删除!'}";
/* 145 */       else if (r == 2)
/* 146 */         this.json = "{'result':1,'message':'此类别下存在商品不能删除!'}";
/*     */     }
/*     */     catch (PermssionRuntimeException ex) {
/* 149 */       this.json = "{'result':1,'message':'非法操作!'}";
/* 150 */       return "json_message";
/*     */     }
/* 152 */     return "json_message";
/*     */   }
/*     */ 
/*     */   public String getChildJson()
/*     */   {
/*     */     try
/*     */     {
/* 162 */       this.catList = this.goodsCatManager.listChildren(Integer.valueOf(this.cat_id));
/* 163 */       this.json = JsonMessageUtil.getListJson(this.catList);
/*     */     } catch (RuntimeException e) {
/* 165 */       showErrorJson(e.getMessage());
/*     */     }
/*     */ 
/* 169 */     return "json_message";
/*     */   }
/*     */ 
/*     */   public String saveSort() {
/* 173 */     this.goodsCatManager.saveSort(this.cat_ids, this.cat_sorts);
/* 174 */     this.json = "{'result':0,'message':'保存成功'}";
/* 175 */     return "json_message";
/*     */   }
/*     */ 
/*     */   public List getCatList()
/*     */   {
/* 181 */     return this.catList;
/*     */   }
/*     */ 
/*     */   public void setCatList(List catList)
/*     */   {
/* 186 */     this.catList = catList;
/*     */   }
/*     */ 
/*     */   public Cat getCat()
/*     */   {
/* 191 */     return this.cat;
/*     */   }
/*     */ 
/*     */   public void setCat(Cat cat)
/*     */   {
/* 196 */     this.cat = cat;
/*     */   }
/*     */ 
/*     */   public int getCat_id()
/*     */   {
/* 201 */     return this.cat_id;
/*     */   }
/*     */ 
/*     */   public void setCat_id(int cat_id)
/*     */   {
/* 206 */     this.cat_id = cat_id;
/*     */   }
/*     */ 
/*     */   public int[] getCat_ids()
/*     */   {
/* 211 */     return this.cat_ids;
/*     */   }
/*     */ 
/*     */   public void setCat_ids(int[] cat_ids)
/*     */   {
/* 216 */     this.cat_ids = cat_ids;
/*     */   }
/*     */ 
/*     */   public int[] getCat_sorts()
/*     */   {
/* 221 */     return this.cat_sorts;
/*     */   }
/*     */ 
/*     */   public void setCat_sorts(int[] cat_sorts)
/*     */   {
/* 226 */     this.cat_sorts = cat_sorts;
/*     */   }
/*     */ 
/*     */   public void setGoodsCatManager(IGoodsCatManager goodsCatManager) {
/* 230 */     this.goodsCatManager = goodsCatManager;
/*     */   }
/*     */ 
/*     */   public void setGoodsTypeManager(IGoodsTypeManager goodsTypeManager)
/*     */   {
/* 235 */     this.goodsTypeManager = goodsTypeManager;
/*     */   }
/*     */ 
/*     */   public List getTypeList()
/*     */   {
/* 241 */     return this.typeList;
/*     */   }
/*     */ 
/*     */   public void setTypeList(List typeList)
/*     */   {
/* 247 */     this.typeList = typeList;
/*     */   }
/*     */ 
/*     */   public File getImage()
/*     */   {
/* 253 */     return this.image;
/*     */   }
/*     */ 
/*     */   public void setImage(File image)
/*     */   {
/* 259 */     this.image = image;
/*     */   }
/*     */ 
/*     */   public String getImageFileName() {
/* 263 */     return this.imageFileName;
/*     */   }
/*     */   public void setImageFileName(String imageFileName) {
/* 266 */     this.imageFileName = imageFileName;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.action.backend.CatAction
 * JD-Core Version:    0.6.0
 */