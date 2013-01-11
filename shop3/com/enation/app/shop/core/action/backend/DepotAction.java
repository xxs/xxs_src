/*    */ package com.enation.app.shop.core.action.backend;
/*    */ 
/*    */ import com.enation.app.shop.core.model.Depot;
/*    */ import com.enation.app.shop.core.service.IDepotManager;
/*    */ import com.enation.framework.action.WWAction;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class DepotAction extends WWAction
/*    */ {
/*    */   private IDepotManager depotManager;
/*    */   private Depot room;
/*    */   private int id;
/*    */   private List depotList;
/*    */ 
/*    */   public String list()
/*    */   {
/* 17 */     this.depotList = this.depotManager.list();
/* 18 */     return "list";
/*    */   }
/*    */ 
/*    */   public String add() {
/* 22 */     return "add";
/*    */   }
/*    */ 
/*    */   public String edit() {
/* 26 */     this.room = this.depotManager.get(this.id);
/* 27 */     return "edit";
/*    */   }
/*    */ 
/*    */   public String saveAdd() {
/* 31 */     this.depotManager.add(this.room);
/* 32 */     this.msgs.add("仓库新增成功");
/* 33 */     this.urls.put("仓库列表", "depot!list.do");
/* 34 */     return "message";
/*    */   }
/*    */ 
/*    */   public String saveEdit() {
/* 38 */     this.depotManager.update(this.room);
/* 39 */     this.msgs.add("仓库修改成功");
/* 40 */     this.urls.put("仓库列表", "depot!list.do");
/* 41 */     return "message";
/*    */   }
/*    */ 
/*    */   public String delete() {
/* 45 */     this.depotManager.delete(this.id);
/* 46 */     this.json = "{'result':0,'message':'操作成功'}";
/* 47 */     return "json_message";
/*    */   }
/*    */ 
/*    */   public IDepotManager getDepotManager() {
/* 51 */     return this.depotManager;
/*    */   }
/*    */ 
/*    */   public void setDepotManager(IDepotManager depotManager) {
/* 55 */     this.depotManager = depotManager;
/*    */   }
/*    */ 
/*    */   public Depot getRoom() {
/* 59 */     return this.room;
/*    */   }
/*    */ 
/*    */   public void setRoom(Depot room) {
/* 63 */     this.room = room;
/*    */   }
/*    */ 
/*    */   public int getId() {
/* 67 */     return this.id;
/*    */   }
/*    */ 
/*    */   public void setId(int id) {
/* 71 */     this.id = id;
/*    */   }
/*    */ 
/*    */   public List getDepotList() {
/* 75 */     return this.depotList;
/*    */   }
/*    */ 
/*    */   public void setDepotList(List depotList) {
/* 79 */     this.depotList = depotList;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.action.backend.DepotAction
 * JD-Core Version:    0.6.0
 */