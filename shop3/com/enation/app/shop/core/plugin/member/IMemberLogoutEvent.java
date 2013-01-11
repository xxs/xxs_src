package com.enation.app.shop.core.plugin.member;

import com.enation.app.base.core.model.Member;

public abstract interface IMemberLogoutEvent
{
  public abstract void onLogout(Member paramMember);
}

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.plugin.member.IMemberLogoutEvent
 * JD-Core Version:    0.6.0
 */