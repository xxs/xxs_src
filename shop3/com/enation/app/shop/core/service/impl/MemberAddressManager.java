/*    */ package com.enation.app.shop.core.service.impl;
/*    */ 
/*    */ import com.enation.app.base.core.model.Member;
/*    */ import com.enation.app.base.core.model.MemberAddress;
/*    */ import com.enation.app.shop.core.service.IMemberAddressManager;
/*    */ import com.enation.eop.sdk.database.BaseSupport;
/*    */ import com.enation.eop.sdk.user.IUserService;
/*    */ import com.enation.eop.sdk.user.UserServiceFactory;
/*    */ import com.enation.framework.database.IDaoSupport;
/*    */ import java.util.List;
/*    */ 
/*    */ public class MemberAddressManager extends BaseSupport<MemberAddress>
/*    */   implements IMemberAddressManager
/*    */ {
/*    */   public void addAddress(MemberAddress address)
/*    */   {
/* 25 */     IUserService userService = UserServiceFactory.getUserService();
/* 26 */     Member member = userService.getCurrentMember();
/* 27 */     address.setMember_id(member.getMember_id());
/* 28 */     this.baseDaoSupport.insert("member_address", address);
/*    */   }
/*    */ 
/*    */   public void deleteAddress(int addr_id)
/*    */   {
/* 33 */     this.baseDaoSupport.execute("delete from member_address where addr_id = ?", new Object[] { Integer.valueOf(addr_id) });
/*    */   }
/*    */ 
/*    */   public MemberAddress getAddress(int addr_id)
/*    */   {
/* 39 */     MemberAddress address = (MemberAddress)this.baseDaoSupport.queryForObject("select * from member_address where addr_id = ?", MemberAddress.class, new Object[] { Integer.valueOf(addr_id) });
/*    */ 
/* 42 */     return address;
/*    */   }
/*    */ 
/*    */   public List<MemberAddress> listAddress()
/*    */   {
/* 47 */     IUserService userService = UserServiceFactory.getUserService();
/* 48 */     Member member = userService.getCurrentMember();
/* 49 */     List list = this.baseDaoSupport.queryForList("select * from member_address where member_id = ?", MemberAddress.class, new Object[] { member.getMember_id() });
/*    */ 
/* 52 */     return list;
/*    */   }
/*    */ 
/*    */   public void updateAddress(MemberAddress address)
/*    */   {
/* 57 */     this.baseDaoSupport.update("member_address", address, "addr_id=" + address.getAddr_id());
/*    */   }
/*    */ 
/*    */   public void updateAddressDefult()
/*    */   {
/* 65 */     IUserService userService = UserServiceFactory.getUserService();
/* 66 */     Member member = userService.getCurrentMember();
/* 67 */     this.baseDaoSupport.execute("update member_address set def_addr = 0 where member_id = ?", new Object[] { member.getMember_id() });
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.service.impl.MemberAddressManager
 * JD-Core Version:    0.6.0
 */