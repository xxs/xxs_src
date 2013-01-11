package com.enation.app.base.core.plugin.user;

import com.enation.eop.resource.model.AdminUser;

public abstract interface IAdminUserLoginEvent
{
  public abstract void onLogin(AdminUser paramAdminUser);
}

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.app.base.core.plugin.user.IAdminUserLoginEvent
 * JD-Core Version:    0.6.0
 */