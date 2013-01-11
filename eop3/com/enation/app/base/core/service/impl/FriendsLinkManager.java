/*    */ package com.enation.app.base.core.service.impl;
/*    */ 
/*    */ import com.enation.app.base.core.model.FriendsLink;
/*    */ import com.enation.app.base.core.model.FriendsLinkMapper;
/*    */ import com.enation.app.base.core.service.IFriendsLinkManager;
/*    */ import com.enation.eop.sdk.database.BaseSupport;
/*    */ import com.enation.eop.sdk.utils.UploadUtil;
/*    */ import com.enation.framework.database.IDaoSupport;
/*    */ import java.util.List;
/*    */ 
/*    */ public class FriendsLinkManager extends BaseSupport<FriendsLink>
/*    */   implements IFriendsLinkManager
/*    */ {
/*    */   public void add(FriendsLink friendsLink)
/*    */   {
/* 23 */     this.baseDaoSupport.insert("friends_link", friendsLink);
/*    */   }
/*    */ 
/*    */   public void delete(String id)
/*    */   {
/* 29 */     this.baseDaoSupport.execute("delete from friends_link where link_id in (" + id + ")", new Object[0]);
/*    */   }
/*    */ 
/*    */   public List listLink()
/*    */   {
/* 36 */     return this.baseDaoSupport.queryForList("select * from friends_link order by sort", new FriendsLinkMapper(), new Object[0]);
/*    */   }
/*    */ 
/*    */   public void update(FriendsLink friendsLink)
/*    */   {
/* 41 */     this.baseDaoSupport.update("friends_link", friendsLink, "link_id = " + friendsLink.getLink_id());
/*    */   }
/*    */ 
/*    */   public FriendsLink get(int link_id)
/*    */   {
/* 47 */     FriendsLink friendsLink = (FriendsLink)this.baseDaoSupport.queryForObject("select * from friends_link where link_id = ?", FriendsLink.class, new Object[] { Integer.valueOf(link_id) });
/* 48 */     String logo = friendsLink.getLogo();
/* 49 */     if (logo != null) {
/* 50 */       logo = UploadUtil.replacePath(logo);
/* 51 */       friendsLink.setLogo(logo);
/*    */     }
/* 53 */     return friendsLink;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.app.base.core.service.impl.FriendsLinkManager
 * JD-Core Version:    0.6.0
 */