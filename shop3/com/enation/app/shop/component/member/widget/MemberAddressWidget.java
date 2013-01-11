/*     */ package com.enation.app.shop.component.member.widget;
/*     */ 
/*     */ import com.enation.app.base.core.model.MemberAddress;
/*     */ import com.enation.app.base.core.service.IRegionsManager;
/*     */ import com.enation.app.shop.core.service.IMemberAddressManager;
/*     */ import com.enation.eop.sdk.widget.AbstractMemberWidget;
/*     */ import com.enation.framework.context.webcontext.ThreadContextHolder;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.springframework.context.annotation.Scope;
/*     */ import org.springframework.stereotype.Component;
/*     */ 
/*     */ @Component("member_address")
/*     */ @Scope("prototype")
/*     */ public class MemberAddressWidget extends AbstractMemberWidget
/*     */ {
/*     */   private IMemberAddressManager memberAddressManager;
/*     */   private IRegionsManager regionsManager;
/*     */ 
/*     */   protected void config(Map<String, String> params)
/*     */   {
/*     */   }
/*     */ 
/*     */   protected void display(Map<String, String> params)
/*     */   {
/*  39 */     setMenu("address");
/*  40 */     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
/*     */ 
/*  42 */     this.action = (this.action == null ? "" : this.action);
/*  43 */     if (this.action.equals("")) {
/*  44 */       showMenu(true);
/*  45 */       setPageName("myaddress");
/*  46 */       List addressList = this.memberAddressManager.listAddress();
/*  47 */       addressList = addressList == null ? new ArrayList() : addressList;
/*  48 */       putData("addressList", addressList);
/*  49 */     } else if (this.action.equals("edit")) {
/*  50 */       showMenu(true);
/*  51 */       setPageName("myaddress_edit");
/*  52 */       String addr_id = request.getParameter("addr_id");
/*  53 */       MemberAddress address = this.memberAddressManager.getAddress(Integer.valueOf(addr_id).intValue());
/*     */ 
/*  56 */       putData("address", address);
/*     */     }
/*  58 */     else if (this.action.equals("editsave")) {
/*  59 */       showMenu(false);
/*  60 */       String addr_id = request.getParameter("address.addr_id");
/*  61 */       MemberAddress address = this.memberAddressManager.getAddress(Integer.valueOf(addr_id).intValue());
/*     */ 
/*  64 */       String def_addr = request.getParameter("address.def_addr");
/*  65 */       address.setDef_addr(Integer.valueOf(def_addr));
/*     */ 
/*  67 */       String name = request.getParameter("address.name");
/*  68 */       address.setName(name);
/*  69 */       if ((name == null) || (name.equals(""))) {
/*  70 */         showError("姓名不能为空！");
/*  71 */         return;
/*     */       }
/*  73 */       Pattern p = Pattern.compile("^[0-9A-Za-z一-龥]{0,20}$");
/*  74 */       Matcher m = p.matcher(name);
/*     */ 
/*  76 */       if (!m.matches()) {
/*  77 */         showError("收货人格式不正确！");
/*  78 */         return;
/*     */       }
/*     */ 
/*  81 */       String tel = request.getParameter("address.tel");
/*  82 */       address.setTel(tel);
/*     */ 
/*  84 */       String mobile = request.getParameter("address.mobile");
/*  85 */       address.setMobile(mobile);
/*     */ 
/*  87 */       if (((tel == null) || (tel.equals(""))) && ((mobile == null) || (mobile.equals(""))))
/*     */       {
/*  89 */         showError("联系电话和联系手机必须填写一项！");
/*  90 */         return;
/*     */       }
/*     */ 
/*  93 */       String province_id = request.getParameter("province_id");
/*  94 */       address.setProvince_id(Integer.valueOf(province_id));
/*  95 */       if ((province_id == null) || (province_id.equals(""))) {
/*  96 */         showError("请选择地区中的省！");
/*  97 */         return;
/*     */       }
/*     */ 
/* 100 */       String city_id = request.getParameter("city_id");
/* 101 */       address.setCity_id(Integer.valueOf(city_id));
/* 102 */       if ((city_id == null) || (city_id.equals(""))) {
/* 103 */         showError("请选择地区中的市！");
/* 104 */         return;
/*     */       }
/*     */ 
/* 107 */       String region_id = request.getParameter("region_id");
/* 108 */       address.setRegion_id(Integer.valueOf(region_id));
/* 109 */       if ((region_id == null) || (region_id.equals(""))) {
/* 110 */         showError("请选择地区中的县！");
/* 111 */         return;
/*     */       }
/*     */ 
/* 114 */       String province = request.getParameter("province");
/* 115 */       address.setProvince(province);
/*     */ 
/* 117 */       String city = request.getParameter("city");
/* 118 */       address.setCity(city);
/*     */ 
/* 120 */       String region = request.getParameter("region");
/* 121 */       address.setRegion(region);
/*     */ 
/* 123 */       String addr = request.getParameter("address.addr");
/* 124 */       address.setAddr(addr);
/* 125 */       if ((addr == null) || (addr.equals(""))) {
/* 126 */         showError("地址不能为空！");
/* 127 */         return;
/*     */       }
/* 129 */       Pattern p1 = Pattern.compile("^[0-9A-Za-z一-龥]{0,50}$");
/* 130 */       Matcher m1 = p1.matcher(addr);
/* 131 */       if (!m1.matches()) {
/* 132 */         showError("地址格式不正确！");
/* 133 */         return;
/*     */       }
/*     */ 
/* 136 */       String zip = request.getParameter("address.zip");
/* 137 */       address.setZip(zip);
/* 138 */       if ((zip == null) || (zip.equals(""))) {
/* 139 */         showError("邮编不能为空！");
/* 140 */         return;
/*     */       }
/*     */       try {
/* 143 */         this.memberAddressManager.updateAddressDefult();
/* 144 */         this.memberAddressManager.updateAddress(address);
/* 145 */         showSuccess("修改成功", "收货地址", "member_address.html");
/*     */       } catch (Exception e) {
/* 147 */         if (this.logger.isDebugEnabled()) {
/* 148 */           this.logger.error(e.getStackTrace());
/*     */         }
/* 150 */         showError("修改失败", "收货地址", "member_address.html");
/*     */       }
/* 152 */     } else if (this.action.equals("add")) {
/* 153 */       showMenu(true);
/* 154 */       setPageName("myaddress_add");
/* 155 */     } else if (this.action.equals("addsave")) {
/* 156 */       showMenu(false);
/* 157 */       MemberAddress address = new MemberAddress();
/*     */ 
/* 159 */       String def_addr = request.getParameter("address.def_addr");
/* 160 */       address.setDef_addr(Integer.valueOf(def_addr));
/*     */ 
/* 162 */       String name = request.getParameter("address.name");
/* 163 */       address.setName(name);
/* 164 */       if ((name == null) || (name.equals(""))) {
/* 165 */         showError("姓名不能为空！");
/* 166 */         return;
/*     */       }
/* 168 */       Pattern p = Pattern.compile("^[0-9A-Za-z一-龥]{0,20}$");
/* 169 */       Matcher m = p.matcher(name);
/*     */ 
/* 171 */       if (!m.matches()) {
/* 172 */         showError("收货人格式不正确！");
/* 173 */         return;
/*     */       }
/*     */ 
/* 176 */       String tel = request.getParameter("address.tel");
/* 177 */       address.setTel(tel);
/*     */ 
/* 179 */       String mobile = request.getParameter("address.mobile");
/* 180 */       address.setMobile(mobile);
/*     */ 
/* 182 */       if (((tel == null) || (tel.equals(""))) && ((mobile == null) || (mobile.equals(""))))
/*     */       {
/* 184 */         showError("联系电话和联系手机必须填写一项！");
/* 185 */         return;
/*     */       }
/*     */ 
/* 188 */       String province_id = request.getParameter("province_id");
/* 189 */       if ((province_id == null) || (province_id.equals(""))) {
/* 190 */         showError("请选择地区中的省！");
/* 191 */         return;
/*     */       }
/* 193 */       address.setProvince_id(Integer.valueOf(province_id));
/*     */ 
/* 195 */       String city_id = request.getParameter("city_id");
/* 196 */       if ((city_id == null) || (city_id.equals(""))) {
/* 197 */         showError("请选择地区中的市！");
/* 198 */         return;
/*     */       }
/* 200 */       address.setCity_id(Integer.valueOf(city_id));
/*     */ 
/* 202 */       String region_id = request.getParameter("region_id");
/* 203 */       if ((region_id == null) || (region_id.equals(""))) {
/* 204 */         showError("请选择地区中的县！");
/* 205 */         return;
/*     */       }
/* 207 */       address.setRegion_id(Integer.valueOf(region_id));
/*     */ 
/* 209 */       String province = request.getParameter("province");
/* 210 */       address.setProvince(province);
/*     */ 
/* 212 */       String city = request.getParameter("city");
/* 213 */       address.setCity(city);
/*     */ 
/* 215 */       String region = request.getParameter("region");
/* 216 */       address.setRegion(region);
/*     */ 
/* 218 */       String addr = request.getParameter("address.addr");
/* 219 */       if ((addr == null) || (addr.equals(""))) {
/* 220 */         showError("地址不能为空！");
/* 221 */         return;
/*     */       }
/* 223 */       Pattern p1 = Pattern.compile("^[0-9A-Za-z一-龥]{0,50}$");
/* 224 */       Matcher m1 = p1.matcher(addr);
/* 225 */       if (!m1.matches()) {
/* 226 */         showError("地址格式不正确！");
/* 227 */         return;
/*     */       }
/* 229 */       address.setAddr(addr);
/*     */ 
/* 231 */       String zip = request.getParameter("address.zip");
/* 232 */       if ((zip == null) || (zip.equals(""))) {
/* 233 */         showError("邮编不能为空！");
/* 234 */         return;
/*     */       }
/* 236 */       address.setZip(zip);
/*     */       try {
/* 238 */         this.memberAddressManager.updateAddressDefult();
/* 239 */         this.memberAddressManager.addAddress(address);
/* 240 */         showSuccess("添加成功", "收货地址", "member_address.html");
/*     */       } catch (Exception e) {
/* 242 */         if (this.logger.isDebugEnabled()) {
/* 243 */           this.logger.error(e.getStackTrace());
/*     */         }
/* 245 */         showError("添加失败", "收货地址", "member_address.html");
/*     */       }
/* 247 */     } else if (this.action.equals("delete")) {
/* 248 */       showMenu(false);
/* 249 */       String addr_id = request.getParameter("addr_id");
/*     */       try {
/* 251 */         this.memberAddressManager.deleteAddress(Integer.valueOf(addr_id).intValue());
/* 252 */         showSuccess("删除成功", "收货地址", "member_address.html");
/*     */       } catch (Exception e) {
/* 254 */         if (this.logger.isDebugEnabled()) {
/* 255 */           this.logger.error(e.getStackTrace());
/*     */         }
/* 257 */         showError("删除失败", "收货地址", "member_address.html");
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public IMemberAddressManager getMemberAddressManager() {
/* 263 */     return this.memberAddressManager;
/*     */   }
/*     */ 
/*     */   public void setMemberAddressManager(IMemberAddressManager memberAddressManager)
/*     */   {
/* 268 */     this.memberAddressManager = memberAddressManager;
/*     */   }
/*     */ 
/*     */   public IRegionsManager getRegionsManager() {
/* 272 */     return this.regionsManager;
/*     */   }
/*     */ 
/*     */   public void setRegionsManager(IRegionsManager regionsManager) {
/* 276 */     this.regionsManager = regionsManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.member.widget.MemberAddressWidget
 * JD-Core Version:    0.6.0
 */