/*     */ package com.enation.app.shop.core.action.backend;
/*     */ 
/*     */ import com.enation.app.shop.core.model.PrintTmpl;
/*     */ import com.enation.app.shop.core.service.IPrintTmplManager;
/*     */ import com.enation.framework.action.WWAction;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class PrintTmplAction extends WWAction
/*     */ {
/*     */   private IPrintTmplManager printTmplManager;
/*     */   private List list;
/*     */   private List trash;
/*     */   private List listCanUse;
/*     */   private Integer[] id;
/*     */   private Integer prt_tmpl_id;
/*     */   private PrintTmpl printTmpl;
/*     */ 
/*     */   public String list()
/*     */   {
/*  27 */     this.list = this.printTmplManager.list();
/*  28 */     return "list";
/*     */   }
/*     */ 
/*     */   public String trash() {
/*  32 */     this.trash = this.printTmplManager.trash();
/*  33 */     return "trash";
/*     */   }
/*     */ 
/*     */   public String listCanUse() {
/*  37 */     this.listCanUse = this.printTmplManager.listCanUse();
/*  38 */     return "listCanUse";
/*     */   }
/*     */ 
/*     */   public String add() {
/*  42 */     return "add";
/*     */   }
/*     */ 
/*     */   public String saveAdd() {
/*     */     try {
/*  47 */       this.printTmplManager.add(this.printTmpl);
/*  48 */       this.msgs.add("模板添加成功");
/*     */     } catch (Exception e) {
/*  50 */       this.msgs.add("模板添加失败");
/*  51 */       e.printStackTrace();
/*     */     }
/*  53 */     this.urls.put("打印模板列表", "printTmpl!list.do");
/*  54 */     return "message";
/*     */   }
/*     */ 
/*     */   public String edit() {
/*  58 */     this.printTmpl = this.printTmplManager.get(this.prt_tmpl_id.intValue());
/*  59 */     return "edit";
/*     */   }
/*     */ 
/*     */   public String saveEdit() {
/*     */     try {
/*  64 */       this.printTmplManager.edit(this.printTmpl);
/*  65 */       this.msgs.add("模板修改成功");
/*     */     } catch (Exception e) {
/*  67 */       this.msgs.add("模板修改失败");
/*  68 */       e.printStackTrace();
/*     */     }
/*  70 */     this.urls.put("打印模板列表", "printTmpl!list.do");
/*  71 */     return "message";
/*     */   }
/*     */ 
/*     */   public String delete() {
/*     */     try {
/*  76 */       this.printTmplManager.delete(this.id);
/*  77 */       this.json = "{'result':0,'message':'删除成功'}";
/*     */     } catch (Exception e) {
/*  79 */       this.json = "{'result':1;'message':'删除失败'}";
/*  80 */       e.printStackTrace();
/*     */     }
/*  82 */     return "json_message";
/*     */   }
/*     */ 
/*     */   public String revert() {
/*     */     try {
/*  87 */       this.printTmplManager.revert(this.id);
/*  88 */       this.json = "{'result':0,'message':'还原成功'}";
/*     */     } catch (Exception e) {
/*  90 */       this.json = "{'result':1;'message':'还原失败'}";
/*  91 */       e.printStackTrace();
/*     */     }
/*  93 */     return "json_message";
/*     */   }
/*     */ 
/*     */   public String clean() {
/*     */     try {
/*  98 */       this.printTmplManager.clean(this.id);
/*  99 */       this.json = "{'result':0,'message':'清除成功'}";
/*     */     } catch (Exception e) {
/* 101 */       this.json = "{'result':1;'message':'清除失败'}";
/* 102 */       e.printStackTrace();
/*     */     }
/* 104 */     return "json_message";
/*     */   }
/*     */ 
/*     */   public IPrintTmplManager getPrintTmplManager() {
/* 108 */     return this.printTmplManager;
/*     */   }
/*     */ 
/*     */   public void setPrintTmplManager(IPrintTmplManager printTmplManager) {
/* 112 */     this.printTmplManager = printTmplManager;
/*     */   }
/*     */ 
/*     */   public List getList() {
/* 116 */     return this.list;
/*     */   }
/*     */ 
/*     */   public void setList(List list) {
/* 120 */     this.list = list;
/*     */   }
/*     */ 
/*     */   public List getTrash() {
/* 124 */     return this.trash;
/*     */   }
/*     */ 
/*     */   public void setTrash(List trash) {
/* 128 */     this.trash = trash;
/*     */   }
/*     */ 
/*     */   public List getListCanUse() {
/* 132 */     return this.listCanUse;
/*     */   }
/*     */ 
/*     */   public void setListCanUse(List listCanUse) {
/* 136 */     this.listCanUse = listCanUse;
/*     */   }
/*     */ 
/*     */   public Integer[] getId() {
/* 140 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(Integer[] id) {
/* 144 */     this.id = id;
/*     */   }
/*     */ 
/*     */   public Integer getPrt_tmpl_id() {
/* 148 */     return this.prt_tmpl_id;
/*     */   }
/*     */ 
/*     */   public void setPrt_tmpl_id(Integer prtTmplId) {
/* 152 */     this.prt_tmpl_id = prtTmplId;
/*     */   }
/*     */ 
/*     */   public PrintTmpl getPrintTmpl() {
/* 156 */     return this.printTmpl;
/*     */   }
/*     */ 
/*     */   public void setPrintTmpl(PrintTmpl printTmpl) {
/* 160 */     this.printTmpl = printTmpl;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.action.backend.PrintTmplAction
 * JD-Core Version:    0.6.0
 */