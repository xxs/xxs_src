package com.enation.app.shop.core.service;

import com.enation.app.base.core.model.MemberAddress;
import java.util.List;

public abstract interface IMemberAddressManager
{
  public abstract List<MemberAddress> listAddress();

  public abstract MemberAddress getAddress(int paramInt);

  public abstract void addAddress(MemberAddress paramMemberAddress);

  public abstract void updateAddress(MemberAddress paramMemberAddress);

  public abstract void updateAddressDefult();

  public abstract void deleteAddress(int paramInt);
}

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.service.IMemberAddressManager
 * JD-Core Version:    0.6.0
 */